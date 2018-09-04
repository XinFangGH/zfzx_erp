Ext.onReady(function() {
	  var  loginCode=document.getElementById("loginCode");
	  loginCode.onclick=function(){
	      refeshCode();
	  };
});

Ext.ns("App");
App.LoginWin = function(_isCodeEnabled,_isDyPwdEnabled) {
	this.isCodeEnabled = _isCodeEnabled;
	this.isDyPwdEnabled = _isDyPwdEnabled;
	var formPanel = new Ext.form.FormPanel({
		id : 'LoginFormPanel',
		bodyStyle : 'padding-top:6px',
		defaultType : 'textfield',
		columnWidth : .75,
		labelAlign : 'right',
		labelWidth : 55,
		labelPad : 0,
		border : false,
		layout : 'form',
		defaults : {
			style:'margin:0 0 0 0',
			anchor : '90%,120%',
			selectOnFocus : true
		},
		items : [{
					name : 'username',
					fieldLabel : '账      号',
					cls : 'text-user',
					allowBlank : false,
					blankText : '账号不能为空'
				}, {
					name : 'password',
					fieldLabel : '密      码',
					allowBlank : false,
					blankText : '密码不能为空',
					cls : 'text-lock',
					inputType : 'password'
				}, {
					name : 'checkCode',
					id : 'checkCode',
					fieldLabel : '验证码',
					allowBlank : false,
					hidden : true,
					cls : 'text-code',
					blankText : '验证码不能为空'
				}, {
					name : 'curDynamicPwd',
					hidden : true,
					id : 'curDynamicPwd',
					fieldLabel : '令     牌',
					cls : 'text-dynamic',
					blankText : '令牌不能为空'
				},{
					xtype : 'container',
					id : 'codeContainer',
					layout : 'table',
					defaultType : 'textfield',
					hideLabel : false,
					layoutConfig : {
						columns : 3
					}
				}, {
					xtype : 'container',
					style : 'padding-left:57px',
					layout : 'column',
					items : [{
								xtype : 'checkbox',
								
								name : '_spring_security_remember_me',
								boxLabel : '让系统记住我 '
							}, {
								xtype : 'panel',
								border : false,
								bodyStyle : 'font-size:12px;padding-left:80px;',
								html : '<a href="javascript:toSuggestBox()">意见箱</a>'
							}]
				}]
	});

	
	if (this.isCodeEnabled != 'undefined' && this.isCodeEnabled != '' && this.isCodeEnabled !='1'
			|| this.isCodeEnabled == 'close') {// 不需要验证码
		formPanel.remove(Ext.getCmp('checkCode'));
	} else {
		Ext.getCmp('checkCode').show();
		var items = [{
					width : 55,
					xtype : 'label',
					text : '　　　　'// 这里的排序以后再改
				}, {
					width : 150,
					id : 'loginCode',
					xtype : 'panel',
					border : false,
					html : '<img border="0" height="30" width="150" src="'
							+ __ctxPath + '/CaptchaImg"/>'
				}, {
					width : 55,
					xtype : 'panel',
					border : false,
					bodyStyle : 'font-size:12px;padding-left:12px',
					html : '<a href="javascript:refeshCode()">看不清</a>'
				}];
		var codeContainer = Ext.getCmp('codeContainer');
		codeContainer.add(items);
		codeContainer.doLayout();
	}
	
	if (this.isDyPwdEnabled != 'undefined' && this.isDyPwdEnabled != '' && this.isDyPwdEnabled !='1'
			|| this.isDyPwdEnabled == 'close') {// 不需要动态密码
		formPanel.remove(Ext.getCmp('curDynamicPwd'));
	} else {
		Ext.getCmp('curDynamicPwd').show();
	}
	
	var LoginHandler = function() {
		if (formPanel.form.isValid()) {
			formPanel.form.submit({
						waitTitle : "请稍候",
						waitMsg : '正在登录......',
						url : __ctxPath + '/login.do',
						success : function(form, action) {
							handleLoginResult(action.result);
						},
						failure : function(form, action) {
							handleLoginResult(action.result);
							form.findField("password").setRawValue("");
							form.findField("username").focus(true);
						}
					});
		}
	};

	var loginWin = new Ext.Window({
				id : 'LoginWin',
				title : '用户登录',
				iconCls : 'login-icon',
				bodyStyle : 'background-color: white',
				border : true,
				closable : false,
				resizable : false,
				buttonAlign : 'center',
				height : 275,
				width : 460,
				layout : {
					type : 'vbox',
					align : 'stretch'
				},
				keys : {
					key : Ext.EventObject.ENTER,
					fn : LoginHandler,
					scope : this
				},
				items : [{
							xtype : 'panel',
							border : false,
							bodyStyle : 'padding-left:60px',
							html : '<img src="' + __loginImage + '" />',
							height : 50
						}, {
							xtype : 'panel',
							border : false,
							layout : 'column',
							items : [formPanel, {
								xtype : 'panel',
								border : false,
								columnWidth : .25,
								html : '<img src="' + __ctxPath
										+ '/images/login-user.jpg"/>'
							}]
						}],

				buttons : [{
							text : '登录',
							iconCls : 'btn-login',
							handler : LoginHandler.createDelegate(this)
						}, {
							text : '重置',
							iconCls : 'btn-login-reset',
							handler : function() {
								formPanel.getForm().reset();
							}
						}]
			});
	return loginWin;
};

function handleLoginResult(result) {
	if (result.success) {
		Ext.getCmp('LoginWin').hide();
		var statusBar = new Ext.ProgressBar({
					text : '正在登录...'
				});
		statusBar.show();
		window.location.href = __ctxPath + '/index.jsp';
	} else {
		Ext.MessageBox.show({
					title : '操作信息',
					msg : result.msg,
					buttons : Ext.MessageBox.OK,
					icon : Ext.MessageBox.ERROR
				});
	}
}

function refeshCode() {
	var loginCode = Ext.getCmp('loginCode');
	loginCode.body.update('<img border="0" height="30" width="150" src="'
			+ __ctxPath + '/CaptchaImg?rand=' + Math.random() + '"/>');
}
function toSuggestBox() {
	window.open(__ctxPath + '/info/suggest.do', '_blank');
}
