/**
 * @author
 * @createtime
 * @class CusConnectionForm
 * @extends Ext.Window
 * @description CusConnectionForm表单
 * @company 智维软件
 */
CusConnectionForm = Ext.extend(Ext.Window, {
	// 内嵌FormPanel
	formPanel : null,
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		// 必须先初始化组件
		this.initUIComponents();
		CusConnectionForm.superclass.constructor.call(this, {
					layout : 'fit',
					id : 'CusConnectionFormWin',
					iconCls : 'menu-connection',
					title : '交往纪录信息',
					width : 500,
					height : 420,
					minHeight:419,
					minWidth:499,
					items:this.formPanel,
					maximizable : true,
					border : false,
					modal : true,
					plain : true,
					buttonAlign : 'center',
					buttons : this.buttons
				});
	},// end of the constructor
	// 初始化组件
	initUIComponents : function() {
		//初始化form表单
		this.formPanel = new Ext.FormPanel({
				url : __ctxPath + '/customer/saveCusConnection.do',
				layout : 'form',
				id : 'CusConnectionForm',
				bodyStyle : 'padding:5px;',
				frame : false,
				defaults : {
					width : 400,
					anchor : '98%,98%'
				},
				formId : 'CusConnectionFormId',
				defaultType : 'textfield',
				items : [{
							name : 'cusConnection.connId',
							id : 'connId',
							xtype : 'hidden',
							value : this.connId == null ? '' : this.connId
						}, {
							xtype:'hidden',//'客户ID'
							name : 'cusConnection.customer.customerId',
							id : 'customerId'
						}, {
							xtype : 'compositefield',
							fieldLabel : '客户*',
							items : [ {
								xtype:'textfield',
								readOnly:true,
								width:260,
								name:'customerName',
								id: 'customerName',
								allowBlank:false,
								blankText:'交往的客户为必选项!'
							},{
								xtype:'button',
								iconCls:'btn-mail_recipient',
								text:'选择客户',
								handler:function(){
									CustomerSelector.getView(function(customerId,customerName){
										Ext.getCmp('customerId').setValue(customerId);
										Ext.getCmp('customerName').setValue(customerName);
										Ext.getCmp('contactor').getStore().reload({params:{'Q_customer.customerId_L_EQ':customerId}});
									},true).show();
								}
							}]
						},{
							fieldLabel : '联系人*',
							name : 'cusConnection.contactor',
							id : 'contactor',
							xtype : 'combo',
							mode : 'local',
							editable : false,
							allowBlank:false,
							blankText:'联系人为必选项!',
							triggerAction : 'all',
							store: new Ext.data.SimpleStore({
											method:'post',
											url:__ctxPath+'/customer/findCusLinkman.do',
											fields : ['linkmanId', 'fullname']
										}),
							displayField:'fullname',
							valueField:'linkmanId',
							enableKeyEvent:true,
							listeners:{
									focus:function(combo){
										var customerId = Ext.getCmp('customerId').value;
										if(customerId ==null || customerId =='' || customerId =='undefined'){
											Ext.ux.Toast.msg('提示信息','请先选择所属客户!');
										}
									}
							}
						}, {
							xtype : 'compositefield',
							fieldLabel : '开始交往日期*',
							items : [ {
								xtype : 'displayfield',
								width : 20,
								value : '从'
							},{
								//fieldLabel : '开始交往日期    从:',
								xtype:'datefield',
								width: 149,
								format:'Y-m-d',
								allowBlank:false,
								blankText:'交往开始日期为必选项!',
								editable:false,
								name : 'cusConnection.startDate',
								id : 'startDate'
							},{
								value : '至',
								xtype : 'displayfield',
								width : 20
							},{
								//fieldLabel : '结束交往日期',
								xtype:'datefield',
								width: 149,
								format:'Y-m-d',
								allowBlank:false,
								blankText:'交往结束日期为必选项!',
								editable:false,
								name : 'cusConnection.endDate',
								id : 'endDate'
							}]
						},{
							fieldLabel : '交往内容*',
							height:180,
							xtype:'textarea',
							name : 'cusConnection.content',
							id : 'content',
							allowBlank:false,
							blankText:'交往内空不能为空!'
						}, {
							fieldLabel : '备注',
							xtype:'textarea',
							name : 'cusConnection.notes',
							id : 'notes'
						}
				]
			});

	if (this.connId != null && this.connId != 'undefined') {
		this.formPanel.getForm().load({
			deferredRender : false,
			url : __ctxPath + '/customer/getCusConnection.do?connId='
					+ this.connId,
			waitMsg : '正在载入数据...',
			success : function(form, action) {
				var result = action.result.data;
				Ext.getCmp('customerName').setValue(result.customer.customerName);
				var startDate = getDateFromFormat(result.endDate,'yyyy-MM-dd HH:mm:ss');
				var endDate = getDateFromFormat(result.startDate,'yyyy-MM-dd HH:mm:ss');
				Ext.getCmp('startDate').setValue(new Date(startDate));
				Ext.getCmp('endDate').setValue(new Date(endDate));
			},
			failure : function(form, action) {
				Ext.ux.Toast.msg('编辑', '载入失败');
			}
		});
	}
		
		this.buttons = [{
			text : '保存',
			iconCls : 'btn-save',
			handler : function() {
				var fp = Ext.getCmp('CusConnectionForm');
				if (fp.getForm().isValid()) {
					fp.getForm().submit({
						method : 'post',
						waitMsg : '正在提交数据...',
						success : function(fp, action) {
							Ext.ux.Toast.msg('操作信息', '成功保存信息！');
							Ext.getCmp('CusConnectionGrid').getStore().reload();
							Ext.getCmp('CusConnectionFormWin').close();
						},
						failure : function(fp, action) {
							Ext.MessageBox.show({
										title : '操作信息',
										msg : action.result.msg,
										buttons : Ext.MessageBox.OK,
										icon : 'ext-mb-error'
									});
							//window.close();
						}
					});
				}
			}
		}, {
			text : '取消',
			iconCls : 'btn-cancel',
			handler : function() {
				Ext.getCmp('CusConnectionFormWin').close();
			}
		}];//end of the buttons
	}
});
