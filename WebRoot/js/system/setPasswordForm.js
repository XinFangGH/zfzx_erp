/**
 * 重置密码
 */
var setPasswordForm = function(userId){
	var formPanel = new Ext.FormPanel({
				url : __ctxPath+ '/system/createPasswordAppUser.do',
				layout : 'form',
				id:'setPasswordForm',
				bodyStyle:'padding:15px',                                     
				border:false,
				defaults : {
					labelWidth : 75,
					anchor : '100%,100%'
				},
	        	defaultType : 'textfield',
				items : [{
							name : 'appUserUserId',
							id : 'appUserUserId',
							xtype:'hidden',
							value : userId
						}, 
						{
							fieldLabel : '重设密码',
							name:'newpassword',
							id:'newpassword',
							inputType : 'password',
							regex : /^[a-zA-Z\d_]{6,18}$/,
				            regexText : '6-18位以上数字或字符',
							maxLength:18,
							maxLengthText:'密码不能超过6—18位',
							allowBlank : false,
							blankText : '密码不能为空!'
						},
						{
							fieldLabel : '确认密码',
							name:'password',
							id:'password',
							inputType : 'password',
							regex : /^[a-zA-Z\d_]{6,18}$/,
				            regexText : '6-18位以上数字或字符',
							maxLength:18,
							maxLengthText:'密码不能超过6—18位',
							allowBlank : false,
							blankText : '密码不能为空!'
						}
						]
			});
			
	var setPassword = new Ext.Window({
		title:'重置密码',
		iconCls:'btn-password',
		width : 350,
		height : 145,
		modal: true,
		layout : 'fit',
		buttonAlign : 'center',
		items:[formPanel],
		buttons : [{
					text : '保存',
					iconCls:'btn-save',
					handler : function() {
						var fp=Ext.getCmp('setPasswordForm');
						var reg = /^[a-zA-Z\d_]{6,18}$/;
	                    var flag = reg.test(Ext.getCmp('newpassword').getValue());
	                    if (!flag) {
					     Ext.getCmp('newpassword').setValue(null);
					     Ext.ux.Toast.msg('操作信息', '重设密码应为6-18位数字或字符,请重新输入');
			             return;
					   }
					   var flag1 = reg.test(Ext.getCmp('password').getValue());
	                    if (!flag1) {
					     Ext.getCmp('password').setValue(null);
					     Ext.ux.Toast.msg('操作信息', '确认密码应为6-18位数字或字符,请重新输入');
			             return;
					   }
						
						
								if (fp.getForm().isValid()) {
								fp.getForm().submit({
											method: 'post',
											waitMsg : '正在提交数据...',
											success : function(fp,action) {
												Ext.ux.Toast.msg('操作信息', '重置密码成功！');
												setPassword.close();
											},
											failure : function(fp,action) {
												Ext.ux.Toast.msg('错误提示',action.result.msg);
												Ext.getCmp('setPasswordForm').getForm().reset();
											}
								});
							}
					}
				}, {
					text : '取消',
					iconCls:'btn-cancel',
					handler : function() {
						setPassword.close();
					}
				}]
			});
	setPassword.show();
};
