/**
 * @author
 * @createtime
 * @class SalaryItemForm
 * @extends Ext.Window
 * @description SalaryItemForm表单
 * @company 智维软件
 */
SalaryItemForm = Ext.extend(Ext.Window,{
	// 内嵌FormPanel
	formPanel : null,
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		// 必须先初始化组件
		this.initUIComponents();
		SalaryItemForm.superclass.constructor.call(this, {
					layout : 'fit',
					id : 'SalaryItemFormWin',
					title : '工资项详细信息',
					iconCls:'menu-salary',
					width : 500,
					height : 150,
					items:this.formPanel,
					border : false,
					modal : true,
					plain : true,
					bodyStyle : 'padding:5px;',
					buttonAlign : 'center',
					buttons : this.buttons
				});
	},// end of the constructor
	// 初始化组件
	initUIComponents : function() {
		this.formPanel = new Ext.FormPanel({
				url : __ctxPath + '/hrm/saveSalaryItem.do',
				layout : 'form',
				id : 'SalaryItemForm',
				frame : false,
				defaults : {
					width : 400,
					anchor : '98%,98%'
				},
				formId : 'SalaryItemFormId',
				defaultType : 'textfield',
				items : [{
							name : 'salaryItem.salaryItemId',
							id : 'salaryItemId',
							xtype : 'hidden',
							value : this.salaryItemId == null
									? ''
									: this.salaryItemId
						}, {
							fieldLabel : '薪酬项名称',
							name : 'salaryItem.itemName',
							allowBlank:false,
							blankText:'薪酬项名称不能为空!',
							id : 'itemName'
						}, {
							fieldLabel : '缺省值',
							name : 'salaryItem.defaultVal',
							allowBlank:false,
							xtype : 'numberfield',
							blankText:'缺省值不能为空!',
							id : 'defaultVal'
						}

				]
			});//end of the formPanel
		if (this.salaryItemId != null && this.salaryItemId != 'undefined') {
		this.formPanel.getForm().load({
			deferredRender : false,
			url : __ctxPath + '/hrm/getSalaryItem.do?salaryItemId='
					+ this.salaryItemId,
			waitMsg : '正在载入数据...',
			success : function(form, action) {
				// Ext.Msg.alert('编辑', '载入成功！');
			},
			failure : function(form, action) {
				// Ext.Msg.alert('编辑', '载入失败');
			}
		});
		}
		this.buttons = [{
					text : '保存',
					iconCls : 'btn-save',
					handler : function() {
						var fp = Ext.getCmp('SalaryItemForm');
						if (fp.getForm().isValid()) {
							fp.getForm().submit({
								method : 'post',
								waitMsg : '正在提交数据...',
								success : function(fp, action) {
									Ext.ux.Toast.msg('操作信息', '成功保存信息！');
									Ext.getCmp('SalaryItemGrid').getStore()
											.reload();
									Ext.getCmp('SalaryItemFormWin').close();
								},
								failure : function(fp, action) {
									Ext.MessageBox.show({
												title : '操作信息',
												msg : '信息保存出错，请联系管理员！',
												buttons : Ext.MessageBox.OK,
												icon : Ext.MessageBox.ERROR
											});
									Ext.getCmp('SalaryItemFormWin').close();
								}
							});
						}
					}
				}, {
					text : '取消',
					iconCls : 'btn-cancel',
					handler : function() {
						Ext.getCmp('SalaryItemFormWin').close();
					}
				}]
	}

});