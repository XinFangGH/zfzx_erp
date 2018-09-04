/**
 * @author hcy
 * @createtime 2010-11-01
 * @class OutMailSetForm
 * @extends Ext.Window
 * @description OutMailSet表单
 * @company 智维软件
 */
OutMailSetForm=Ext.extend(Ext.Window,{
	//内嵌FormPanel
	formPanel:null,
	//构造函数
	constructor:function(_cfg){
		Ext.applyIf(this,_cfg);
		//必须先初始化组件
		this.initUIComponents();
		OutMailSetForm.superclass.constructor.call(this,{
			id:'OutMailSetFormWin',
			layout:'fit',
			items:this.formPanel,
			modal:true,
			height:280,
			width:400,
			maximizable:true,
			title:'添加邮箱账号',
			buttonAlign : 'center',
			buttons:this.buttons
		});
	},//end of the constructor
	//初始化组件
	initUIComponents:function(){
		this.formPanel=new Ext.FormPanel({
				layout : 'form',
				bodyStyle: 'padding:10px 10px 10px 10px',
				border:false,
				url : __ctxPath + '/system/saveOutMailSet.do',
				id : 'OutMailSetForm',
				defaults : {
					anchor : '98%,98%'
				},
				defaultType : 'textfield',
				items : [{
					name : 'outMailUserSeting.id',
					id : 'id',
					xtype : 'hidden',
					value : this.id == null ? '' : this.id
				},{
					xtype : 'compositefield',
					fieldLabel : '用户名称',
					items : [ {
							xtype : 'textfield',
							width : 170,
							readOnly : true,
							allowBlank:false,
							blankText:'用户名称不能为空!',
							name : 'outMailUserSeting.userName',
							id : 'userName'
						}, {
							xtype : 'button',
							text : '选择用户',
							iconCls:'btn-mail_recipient',
							handler : function() {
								UserSelector.getView(
										function(userIds, fullnames) {
											var userName = Ext
													.getCmp('userName');
											userName
											        .setValue(fullnames);
										}, true).show();
							}
						}]
				},{
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
					blankText : '端口必须是数字',
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
					blankText : '端口必须是填写数字',
					name : 'outMailUserSeting.popPort',
					id : 'popPort'
				}]
			});
		//加载表单对应的数据	
		if (this.id != null && this.id != 'undefined') {
			this.formPanel.getForm().load({
				deferredRender : false,
				url : __ctxPath + '/system/getOutMailSet.do?id='+ this.id,
				waitMsg : '正在载入数据...',
				success : function(form, action) {
				},
				failure : function(form, action) {
				}
			});
		}
		//初始化功能按钮
		this.buttons=[{
				text : '保存',
				iconCls : 'btn-save',
				handler :this.save.createCallback(this.formPanel,this)
			}, {
				text : '重置',
				iconCls : 'btn-reset',
				handler :this.reset.createCallback(this.formPanel)
			},{
				text : '取消',
				iconCls : 'btn-cancel',
				handler : this.cancel.createCallback(this)
			}];
	},//end of the initcomponents
	
	/**
	 * 重置
	 * @param {} formPanel
	 */
	reset:function(formPanel){
		formPanel.getForm().reset();
	},
	/**
	 * 取消
	 * @param {} window
	 */
	cancel:function(window){
		window.close();
	},
	/**
	 * 保存记录
	 */
	save:function(formPanel,window){
		if (formPanel.getForm().isValid()) {
			formPanel.getForm().submit({
				method : 'POST',
				waitMsg : '正在提交数据...',
				success : function(fp, action) {
					Ext.ux.Toast.msg('操作信息', '成功保存信息！');
					var gridPanel=Ext.getCmp('OutMailSetGrid');
					if(gridPanel!=null){
						gridPanel.getStore().reload();
					}
					window.close();
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
					window.close();
				}
			});
		}
	}//end of save
	
});