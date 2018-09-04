/**
 * @author
 * @createtime
 * @class DynamicPwdForm
 * @extends Ext.Window
 * @description SmsMobile表单
 * @company 智维软件
 */
DynamicPwdForm = Ext.extend(Ext.Window, {
	// 内嵌FormPanel
	formPanel : null,
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		// 必须先初始化组件
		this.initUIComponents();
		DynamicPwdForm.superclass.constructor.call(this, {
					id : 'DynamicPwdFormWin',
					layout : 'fit',
					items : this.formPanel,
					modal : true,
					height : 226,
					width : 418,
					minHeight : 225,
					minWidth : 417,
					iconCls : 'btn-dynamic-pwd',
					maximizable : true,
					title : '令牌设置',
					buttonAlign : 'center',
					buttons : this.buttons
				});
	},// end of the constructor
	// 初始化组件
	initUIComponents : function() {
		this.formPanel = new Ext.FormPanel({
					layout : 'form',
					bodyStyle : 'padding:10px 10px 10px 10px',
					border : false,
					id : 'DynamicPwdForm',
					defaults : {
						anchor : '98%,98%'
					},
					reader : new Ext.data.JsonReader({
								root : 'data'
							}, [{
										name : 'appUser.dynamicPwd',
										mapping : 'dynamicPwd'
									}]),
					defaultType : 'textfield',
					items : [{
								name : 'appUser.userId',
								id : 'userId',
								xtype : 'hidden',
								value : this.userId == null ? '' : this.userId
							}, {
								fieldLabel : '序列号',
								name : 'appUser.dynamicPwd',
								allowBlank : false,
								id : 'dynamicPwd'
							}, {
								fieldLabel : '动态密码',
								allowBlank : false,
								name : 'curDynamicPwd',
								id : 'curDynamicPwd'
							}

					]
				});
		// 加载表单对应的数据
		if (this.userId != null && this.userId != 'undefined') {
			this.formPanel.getForm().load({
				deferredRender : false,
				url : __ctxPath + '/system/getAppUser.do?userId=' + this.userId,
				waitMsg : '正在载入数据...',
				success : function(form, o) {
					var user = Ext.util.JSON.decode(o.response.responseText).data[0];
					if(user.dyPwdStatus == 1){//已绑定
						Ext.getCmp('dyPwdFormButtonUnbind').show();
					}else{
						Ext.getCmp('dyPwdFormButtonBind').show();
					}
				},
				failure : function(form, action) {
				}
			});
		}
		// 初始化功能按钮
		this.buttons = [{
					text : '绑定',
					id : 'dyPwdFormButtonBind',
					hidden : true,
					iconCls : 'btn-dynamic-bind',
					handler : this.bind.createCallback(this.formPanel, this)
				}, {
					text : '解绑',
					id : 'dyPwdFormButtonUnbind',
					hidden : true,
					iconCls : 'btn-dynamic-unbind',
					handler : this.unbind.createCallback(this.formPanel,this)
				}, {
					text : '取消',
					iconCls : 'btn-cancel',
					handler : this.cancel.createCallback(this)
				}];
	},// end of the initcomponents

	/**
	 * 重置
	 * 
	 * @param {}
	 *            formPanel
	 */
	unbind : function(formPanel, window) {
		if (formPanel.getForm().isValid()) {
			formPanel.getForm().submit({
						url : __ctxPath + '/system/unbindDyPwdAppUser.do',
						method : 'POST',
						waitMsg : '正在提交数据...',
						success : function(fp, action) {
							var msg = action.result.msg;
//							if (msg != null && msg != '' && msg != 'undefined') {
								Ext.ux.Toast.msg('操作信息', msg);
//							} else {
//								Ext.ux.Toast.msg('操作信息', '解绑成功！');
//							}
//							var gridPanel = Ext.getCmp('SmsMobileGrid');
//							if (gridPanel != null) {
//								gridPanel.getStore().reload();
//							}
							window.close();
						},
						failure : function(fp, action) {
							Ext.MessageBox.show({
										title : '操作信息',
										msg : '信息保存出错，请联系管理员！',
										buttons : Ext.MessageBox.OK,
										icon : Ext.MessageBox.ERROR
									});
							window.close();
						}
					});
		}
	},
	/**
	 * 取消
	 * 
	 * @param {}
	 *            window
	 */
	cancel : function(window) {
		window.close();
	},
	/**
	 * 保存记录
	 */
	bind : function(formPanel, window) {
		if (formPanel.getForm().isValid()) {
			formPanel.getForm().submit({
						url : __ctxPath + '/system/bindDyPwdAppUser.do',
						method : 'POST',
						waitMsg : '正在提交数据...',
						success : function(fp, action) {
							var msg = action.result.msg;
//							if (msg != null && msg != '' && msg != 'undefined') {
								Ext.ux.Toast.msg('操作信息', msg);
//							} else {
//								Ext.ux.Toast.msg('操作信息', '绑定成功！');
//							}
//							var gridPanel = Ext.getCmp('SmsMobileGrid');
//							if (gridPanel != null) {
//								gridPanel.getStore().reload();
//							}
							window.close();
						},
						failure : function(fp, action) {
							Ext.MessageBox.show({
										title : '操作信息',
										msg : '信息保存出错，请联系管理员！',
										buttons : Ext.MessageBox.OK,
										icon : Ext.MessageBox.ERROR
									});
							window.close();
						}
					});
		}
	}// end of save

});