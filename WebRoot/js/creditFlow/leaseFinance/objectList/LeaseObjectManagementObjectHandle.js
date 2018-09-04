LeaseObjectManagementObjectHandle = Ext.extend(Ext.Window, {
	layout : 'anchor',
	anchor : '100%',
	isManageWin: false,
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		if(typeof(_cfg.isManageWin)!="undifined"){
			this.isManageWin = _cfg.isManageWin;
		}
		this.initUIComponents();
		LeaseObjectManagementObjectHandle.superclass.constructor.call(this, {
					buttonAlign : 'center',
					title : '租赁物处置信息',
					iconCls : 'btn-add',
					width : (screen.width - 180) * 0.6,
					height : 460,
					constrainHeader : true,
					collapsible : true,
					frame : true,
					border : false,
					resizable : true,
					layout : 'fit',
					//autoScroll : true,
					//bodyStyle : 'overflowX:hidden',
					constrain : true,
					closable : true,
					modal : true,
					maximizable : true,
					items : [this.formPanel],
					buttons : [{
								text : '保存',
								iconCls : 'btn-add',
								scope : this,
								hidden:!this.editable,
								handler : this.save
							}, {
								text : '提交',
								iconCls : 'btn-save',
								scope : this,
								hidden:!this.editable,
								handler : this.submit
							}, {
								text : '关闭',
								iconCls : 'close',
								scope : this,
								handler : function() {
									this.close();
								}
							}]

				})
	},
	initUIComponents : function() {
		this.formPanel = new Ext.FormPanel({
			url : __ctxPath	+ '/creditFlow/leaseFinance/project/saveFlLeaseobjectInfo.do',
			monitorValid : true,
			bodyStyle : 'padding:10px',
			autoScroll : true,
			id:'leaseObjectForm',
			labelAlign : 'right',
			buttonAlign : 'center',
			layout : 'column',
			frame : true,
			border : false,
			items : 
				 [{
							columnWidth : .45,
							layout : 'form',
							labelWidth : 105,
							defaults : {
								anchor : '100%'
							},
							items : [{
										name : 'flLeaseobjectInfo.id',
										xtype : 'hidden'
									},{
										xtype : 'textfield',
										fieldLabel : '租赁物名称',
										name : 'flLeaseobjectInfo.name',
										readOnly:true,
										allowBlank : false
									}]
						}, {
								columnWidth : .05, // 该列在整行中所占的百分比
								layout : "form", // 从上往下的布局
								labelWidth : 20,
								items : [{
											fieldLabel : " ",
											labelSeparator : '',
											anchor : "100%"
										}]
							},{
								columnWidth : .45,
								layout : 'form',
								labelWidth : 105,
								defaults : {
									anchor : '100%'
								},
								items:{
										xtype:'textfield',
										fieldLabel : '规格型号',
										name : 'flLeaseobjectInfo.standardSize',
										readOnly:true,
										allowBlank : false,
										maxLength : 255
									}
							},{
								columnWidth : .45,
								layout : 'form',
								labelWidth : 105,
								defaults : {
									anchor : '100%'
								},
								items:{
											xtype : 'numberfield',
											fieldLabel : '残值估价',
											readOnly:!this.editable,
											name : 'flLeaseobjectInfo.remnantEvaluatedPrice',
											allowBlank : false
								}
							},{
								columnWidth : .05, // 该列在整行中所占的百分比
								layout : "form", // 从上往下的布局
								labelWidth : 20,
								items : [{
											fieldLabel : "元 ",
											labelSeparator : '',
											anchor : "100%"
										}]
							},{
								columnWidth : .45,
								layout : 'form',
								labelWidth : 105,
								defaults : {
									anchor : '100%'
								},
								items:{
										xtype : 'numberfield',
										fieldLabel : '实际价格',	
										readOnly:!this.editable,
								 		name : 'flLeaseobjectInfo.remnantActualPrice',
										allowBlank : false
										}
							},{
								columnWidth : .05, // 该列在整行中所占的百分比
								layout : "form", // 从上往下的布局
								labelWidth : 20,
								items : [{
											fieldLabel : "元 ",
											labelSeparator : '',
											anchor : "100%"
										}]
							},{
								columnWidth : .5, // 该列在整行中所占的百分比
								layout : "form", // 从上往下的布局
								labelWidth : 105,
								items : [{xtype:"hidden",name:"flLeaseobjectInfo.handlePersonId"},{
									fieldLabel : "经办人",
									xtype : "combo",
									allowBlank : false,
									editable : false,
									triggerClass : 'x-form-search-trigger',
									itemVale : creditkindDicId, // xx代表分类名称
									hiddenName : "appUsersOfD",
									readOnly:!this.editable,
								    anchor : "100%",
								    onTriggerClick : function(cc) {
									     var obj = this;
									     var appuerIdObj=obj.previousSibling();
										 new UserDialog({
										 	userIds:appuerIdObj.getValue(),
										 	userName:obj.getValue(),
											single : true, //暂定不支持  多个办理人 ，后台代码没有区分，  但是  字段为string类型，可拓展 add by gao
											title:"经办人",
											callback : function(uId, uname) {
												obj.setRawValue(uname);
												appuerIdObj.setValue(uId);
											}
										}).show();
									}
							}]
						},{
							columnWidth : .5,
							labelWidth : 105,
							layout : 'form',
							defaults : {
								anchor : '100%'
							},
							items : [{
								xtype : 'datefield',
								fieldLabel : '经办时间',
								readOnly:!this.editable,
								format : 'Y-m-d',
								name : 'flLeaseobjectInfo.handleDate'
							}]},{
								
							columnWidth : 1,
							layout : 'form',
							labelWidth : 105,
							defaults : {
								anchor : '100%'
							},
							items : [{
										xtype : 'textarea',
										fieldLabel : '备注说明',
										maxLength : 100,
										readOnly:!this.editable,
										maxLengthText : '最大输入长度100',
										name : 'flLeaseobjectInfo.handleComment'
									}]
						
							}]
		})
		
		
		//加载表单对应的数据	   -----test   ok  by gaoqingrui
		if (this.objectId != null && this.objectId != 'undefined') {
			this.formPanel.loadData({
				url : __ctxPath	+ '/creditFlow/leaseFinance/project/getFlLeaseobjectInfo.do?id='
						+ this.objectId,
				root : 'data',
				success:function(response, options){
					var respText = response.responseText;  
					var alarm_fields = Ext.util.JSON.decode(respText); 
					var handlePersonName= alarm_fields.data.flLeaseobjectInfo.handlePersonName;
					if(""!=handlePersonName &&  null!=handlePersonName){
						this.getCmpByName('appUsersOfD').setValue(handlePersonName);
					}
				},
				preName : ['flLeaseobjectInfo','flObjectSupplior']
			});
		}
	this.save = function() {
		win = this;
		var gridPanel = this.gridPanel;
			this.formPanel.getForm().submit({
						method : 'POST',
						waitTitle : '连接',
						waitMsg : '消息发送中...',
						success : function(form, action) {
							Ext.ux.Toast.msg('操作信息', '保存成功!');
							win.destroy();
							gridPanel.getStore().reload()
						},
						params : {
							"flLeaseobjectInfo.isHandled" : false//租赁物状态改变
						},
						failure : function(form, action) {
							Ext.ux.Toast.msg('操作信息', '保存失败,请正确填写表单信息!');
						}
					});
	};
	this.submit = function() {
		win = this;
		var gridPanel = this.gridPanel;
			this.formPanel.getForm().submit({
						method : 'POST',
						waitTitle : '连接',
						waitMsg : '消息发送中...',
						success : function(form, action) {
							Ext.ux.Toast.msg('操作信息', '保存成功!');
							win.destroy();
							gridPanel.getStore().reload()
						},
						params : {
							"flLeaseobjectInfo.isHandled" : true//租赁物状态改变
						},
						failure : function(form, action) {
							Ext.ux.Toast.msg('操作信息', '保存失败,请正确填写表单信息!');
						}
					});
	};
}
});
