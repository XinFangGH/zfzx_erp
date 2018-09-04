/**
 * @author:
 * @class OutMailUserSetingForm
 * @extends Ext.Panel
 * @description [OutMailUserSeting]管理
 * @company 北京互融时代软件有限公司
 * @createtime:2010-01-16
 */
OutMailUserSetingForm = Ext.extend(Ext.Panel, {
	// 内嵌FormPanel
	formPanel : null,
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		// 必须先初始化组件
		this.initUIComponents();
		OutMailUserSetingForm.superclass.constructor.call(this, {
					id : 'OutMailUserSetingFormWin',
					layout : 'hbox',
					layoutConfig : {
						padding : '5',
						pack : 'center',
						align : 'middle'
					},
					items : this.formPanel,
					autoScroll : false,
					maximizable : true,
					bodyBorder : true,
					border : true,
					title : '邮箱配置'
				});
	},// end of the constructor
	// 初始化组件

	initUIComponents : function() {
		var formPanel = new Ext.FormPanel({
					title:'邮箱配置',
					buttonAlign : 'center',
					id : 'OutMailUserSetingFormPanel',
					layout : 'form',
					defaults:{
						anchor:'98%,98%'
					},
					bodyStyle : 'padding:10px',
					width:450,
					autoScroll : false,
					url : __ctxPath + '/communicate/saveOutMailUserSeting.do',
					id : 'OutMailUserSetingForm',
					defaultType : 'textfield',
					items : [{
								name : 'outMailUserSeting.id',
								id : 'id',
								xtype : 'hidden',
								value : this.id == null ? '' : this.id
							},
							/**
							 * { fieldLabel : '用户ID', name :
							 * 'outMailUserSeting.userId', id : 'userId' },
							 */
							{
								fieldLabel : '用户名称',
								name : 'outMailUserSeting.userName',
								id : 'userName',
								allowBlank : false
							}, {
								fieldLabel : '外部邮件地址',
								allowBlank : false,
								name : 'outMailUserSeting.mailAddress',
								vtype : 'email',
								id : 'mailAddress'
							}, {
								fieldLabel : '外部邮件密码',
								name : 'outMailUserSeting.mailPass',
								allowBlank : false,
								inputType : 'password',
								id : 'mailPass'
							}, {
								fieldLabel : 'smtp主机',
								allowBlank : false,
								name : 'outMailUserSeting.smtpHost',
								id : 'smtpHost'
							}, {
								fieldLabel : 'smtp端口',
								allowBlank : false,
								name : 'outMailUserSeting.smtpPort',
								vtype : 'alphanum',
								id : 'smtpPort'
							}, {
								fieldLabel : 'pop主机',
								allowBlank : false,
								name : 'outMailUserSeting.popHost',
								id : 'popHost'
							}, {
								fieldLabel : 'pop端口',
								vtype : 'alphanum',
								allowBlank : false,
								name : 'outMailUserSeting.popPort',
								id : 'popPort'
							}

					],
					listeners : {
						render : function(t) {
							t.getForm().load({
								deferredRender : false,
								url : __ctxPath
										+ '/communicate/getOutMailUserSeting.do',
								waitMsg : '正在载入数据...',
								success : function(form, action) {},
								failure : function(form, action) {}
							});

						}
					},
					buttons:[{
						text : '保存',
						scope:this,
						iconCls : 'btn-save',
						handler : function(){
							if (formPanel.getForm().isValid()) {
								formPanel.getForm().submit({
											method : 'POST',
											waitMsg : '正在提交数据...',
											success : function(fp, action) {
												Ext.ux.Toast.msg('操作信息', '成功保存信息！');
												formPanel.getForm().load({
													deferredRender : false,
													url : __ctxPath
															+ '/communicate/getOutMailUserSeting.do',
													waitMsg : '正在载入数据...',
													success : function(form, action) {},
													failure : function(form, action) {}
												});
											},
											failure : function(fp, action) {
												if (action.result != null
														&& action.result != undefined) {
													Ext.MessageBox.show({
																title : '操作信息',
																msg : action.result.msg,
																buttons : Ext.MessageBox.OK,
																icon : Ext.MessageBox.ERROR
															});
					
													Ext.ux.Toast.msg('错误信息', action.result.msg);
												} else {
													Ext.MessageBox.show({
																title : '操作信息',
																msg : '保存信息出错，请联系管理员!',
																buttons : Ext.MessageBox.OK,
																icon : Ext.MessageBox.ERROR
															});
												}
												
												formPanel.getForm().load({
													deferredRender : false,
													url : __ctxPath
															+ '/communicate/getOutMailUserSeting.do',
													waitMsg : '正在载入数据...',
													success : function(form, action) {},
													failure : function(form, action) {}
												});
					
											}
										});
							}
							
						}//end of save
					}, {
						text : '重置',
						iconCls : 'btn-reset',
						scope:this,
						handler : function(){
							formPanel.getForm().reset();
						}
					}]

				});
		this.formPanel=formPanel;
		// 初始化功能按钮
		

	}// end of the initcomponents

	

});
