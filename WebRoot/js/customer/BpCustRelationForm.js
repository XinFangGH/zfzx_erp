/**
 * @author
 * @createtime
 * @class BpCustRelationForm
 * @extends Ext.Window
 * @description BpCustRelation表单
 * @company 智维软件
 */
BpCustRelationForm = Ext.extend(Ext.Window, {
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		// 必须先初始化组件
		this.initUIComponents();
		BpCustRelationForm.superclass.constructor.call(this, {
					id : 'BpCustRelationFormWin',
					layout : 'fit',
					items : this.formPanel,
					modal : true,
					height : 150,
					width : 500,
					maximizable : true,
					title : this.buttonType == 'ktzh' ? '开通p2p账户' : '绑定p2p账号',
					buttonAlign : 'center',
					buttons : [{
								text : '保存',
								iconCls : 'btn-save',
								scope : this,
								handler : this.save
							}, {
								text : '重置',
								iconCls : 'btn-reset',
								scope : this,
								handler : this.reset
							}, {
								text : '取消',
								iconCls : 'btn-cancel',
								scope : this,
								handler : this.cancel
							}]
				});
	},// end of the constructor
	// 初始化组件
	initUIComponents : function() {
		this.formPanel = new Ext.FormPanel({
			layout : 'form',
			bodyStyle : 'padding:10px',
			border : false,
			autoScroll : true,
			monitorValid :true,
			defaults : {
				anchor : '96%,96%'
			},
			defaultType : 'textfield',
			items : [{
						name : 'bpCustRelation.relationId',
						xtype : 'hidden',
						value : this.relationId == null ? '' : this.relationId
					}, {
						fieldLabel : '登录账户',
						name : 'username',
						allowBlank : false,
						id:'username'
					},{
						fieldLabel : '登录密码',
						name : 'password',
						inputType: 'password',
						hideLabel :(this.buttonType == 'ktzh' ? false : true),
						hidden : (this.buttonType == 'ktzh' ? false : true),
						allowBlank : (this.buttonType == 'ktzh' ? false : true),
						id:'password'
					}]
		});
		// 加载表单对应的数据
		if (this.relationId != null && this.relationId != 'undefined') {
			this.formPanel.loadData({
				url : __ctxPath
						+ '/customer/getBpCustRelation.do?relationId='
						+ this.relationId,
				root : 'data',
				preName : 'bpCustRelation'
			});
		}

	},// end of the initcomponents

	/**
	 * 重置
	 * 
	 * @param {}
	 *            formPanel
	 */
	reset : function() {
		this.formPanel.getForm().reset();
	},
	/**
	 * 取消
	 * 
	 * @param {}
	 *            window
	 */
	cancel : function() {
		this.close();
	},
	/**
	 * 保存记录
	 */
	save : function() {
		var username=Ext.getCmp('username').getValue();
		var password=Ext.getCmp('password').getValue();
		var buttonType = this.buttonType;
		var grid=this.grid;
		
		var URL;
		if(buttonType == 'ktzh'){//开通p2p账号
			URL='/customer/saveBpCustRelation.do'
		}else if(buttonType == 'bdzh'){//绑定p2p账号
			URL='/customer/bindUserBpCustRelation.do'
		}
		if(this.formPanel.getForm().isValid()){
			/*if (username.length < 6 || username.length > 16 || !username.match(/^[a-zA-Z0-9_]+$/) || username.match(/^\d+$/)) {
				Ext.ux.Toast.msg('提示信息',"登录账户只允许字母、数字、下划线、横线组成、不支持纯数字， 需要 6-16个字符");
			}else */if(buttonType == 'ktzh' && (password.length < 6 || password.length > 16 || !password.match(/^[a-zA-Z0-9]+$/))){
				Ext.ux.Toast.msg('提示信息',"登录密码只能由6-16位字母和数字组成");
				return;
			}else{
				Ext.Ajax.request({
					scope:this,
					method : 'post',
					url :__ctxPath + URL,
					params : {
						username : username,
						password : password,
						customerType : this.customerType,
						userId:this.userId,
						cardnumber:this.cardnumber
					},
					success : function(response) {
						var result = Ext.util.JSON.decode(response.responseText);
						if (result.success) {
							Ext.ux.Toast.msg('提示信息', result.msg);
							if("undefined" != typeof grid){
							  grid.getStore().reload();
							}
							this.close();
						} else {
							Ext.ux.Toast.msg('提示信息', result.msg);
						}
					},
					failure : function() {
					}
				})
			}
		}
	}
});