/**
 * @author
 * @createtime
 * @class RecommendCodeCreateForm
 * @extends Ext.Window
 * @description RecommendCodeCreateForm表单
 * @company 智维软件
 */
RecommendCodeCreateForm = Ext.extend(Ext.Window, {
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		// 必须先初始化组件
		this.initUIComponents();
		RecommendCodeCreateForm.superclass.constructor.call(this, {
			id : 'RecommendCodeCreateFormWin',
			layout : 'fit',
			items : this.formPanel,
			modal : true,
			height : this.type==1?160:250,
			width : 500,
			frame:true,
			maximizable : true,
			title : this.type==1?'绑定P2P账号':'开通P2P账号',
			buttonAlign : 'center',
			buttons : [{
				text : this.type==1?'绑定':'开通',
				id:'openP2PBtn',
				iconCls : 'btn-save',
				scope : this,
				handler : this.save
			},{
				text : '关闭',
				iconCls : 'close',
				scope : this,
				handler : this.cancel
			}]
		});
	},
	// 初始化组件
	initUIComponents : function() {
		var flag1=false;//标识p2p账号是否存在(绑定)
		var flag2=false;//标识推荐码是否存在
//		var mobile=this.mobile;
		var type=this.type;
		this.formPanel = new Ext.FormPanel({
			layout : 'form',
			bodyStyle : 'padding:10px',
			monitorValid : true,
			border : false,
			autoScroll : true,
			defaults : {
				anchor : '96%,96%'
			},
			defaultType : 'textfield',
			items : [{
				name : 'memberId',
				xtype : 'hidden'
			},{
				name : 'userId',
				xtype : 'hidden'
			},{
				fieldLabel : '姓名',
				xtype : "combo",
				triggerClass : 'x-form-search-trigger',
				hiddenName:'fullname',
				editable : false,
				allowBlank : false,
				onTriggerClick : function(cc) {
					var op=this.getOriginalContainer();
						op.getCmpByName('mobile').setValue("");
						op.getCmpByName('plainpassword').setValue("");
						op.getCmpByName('email').setValue("");
						op.getCmpByName('userId').setValue("");
					var temp = function(obj) {
						if (obj.mobile){
							op.getCmpByName('mobile').setValue(obj.mobile);
							op.getCmpByName('plainpassword').setValue(obj.mobile);
						}
						if (obj.email){
							op.getCmpByName('email').setValue(obj.email);
						}
						if (obj.fullname){
							op.getCmpByName('fullname').setValue(obj.fullname);
						}
						if (obj.userId){
							op.getCmpByName('userId').setValue(obj.userId);
						}
					}
					selectAppUserGrid(temp);
				}
			},{
				fieldLabel : '登录密码',
				name : 'password',
				hidden:this.type==1?true:false,
				hideLabel:this.type==1?true:false,
				disabled:this.type==1?true:false,
				inputType: 'password',
				allowBlank :false,
				minLength:6
			},{
				fieldLabel : 'P2P账号',
				name : 'p2pAccount',
				allowBlank :false,
				listeners:{
					'blur':function(v){
						if(v.getValue()){
							var memberId=this.getOriginalContainer().getCmpByName('memberId');
							Ext.Ajax.request({
								url :__ctxPath + '/customer/isExistBpCustRelation.do',
								params : {
									Q_loginname_S_EQ:v.getValue()
								},
								scope:this,
								method : 'post',
								success : function(response) {
									var result = Ext.util.JSON.decode(response.responseText);
									if(type==1){
										if (result.success) {
											memberId.setValue(result.memberId);
										} else {
											Ext.ux.Toast.msg('提示信息','P2P账号不存在,请重新填写!!!');
											v.setValue(null);
										}
									}else{
									   	if (result.success) {
									   		Ext.ux.Toast.msg('提示信息','P2P账号已存在,请重新填写!!!');
									   		v.setValue(null);
										}
									}
								}
							})
						}
					}
				}
			},{
				fieldLabel : '手机号码',
				name : 'mobile',
				allowBlank : this.type==1?true:false,
				hidden:this.type==1?true:false,
				hideLabel:this.type==1?true:false,
                readOnly:true,
				listeners : {
					scope : this,
					'blur':function(v){
						if(v.getValue()){
							var reg=/^[1][34578][0-9]{9}$/;
							var flag=reg.test(v.getValue());
							if(!flag){
								Ext.ux.Toast.msg('手机号验证', '手机号不正确,请重新填写!!!');
							}else{
								var telphone=v.getValue();
								var plainpassword=this.getCmpByName('plainpassword');
								Ext.Ajax.request({
									url :__ctxPath + '/customer/isExistBpCustRelation.do',
									params : {
										Q_telphone_S_EQ:telphone
									},
									scope:this,
									method : 'post',
									success : function(response) {
										var result = Ext.util.JSON.decode(response.responseText);
										if (result.success) {
											Ext.ux.Toast.msg('提示信息','手机号已存在,请重新填写!!!');
											v.setValue("");
											Ext.getCmp('openP2PBtn').setDisabled(true);
										} else {
											plainpassword.setValue(telphone);
											Ext.getCmp('openP2PBtn').setDisabled(false);
										}
									}
								})
							}
						}
					}
				}
			},{
				fieldLabel : '邮箱',
				name : 'email',
				readOnly:true,
				allowBlank : this.type==1?true:false,
				hidden:this.type==1?true:false,
				hideLabel:this.type==1?true:false
			},{
				fieldLabel : '推荐码',
				name : 'plainpassword',
				maxLength:11,
				minLength:11,
				readOnly:true,
				allowBlank : this.type==1?true:false,
				hidden:this.type==1?true:false,
				hideLabel:this.type==1?true:false,
				emptyText:'推荐码只能是手机号'
			}]
		});
	},
	/**
	 * 取消
	 * @param {}
	 * window
	 */
	cancel : function() {
		this.close();
	},
	/**
	 * 保存记录
	 */
	save : function() {
		var password=this.getCmpByName('password').getValue();
		if(this.type!=1 && (""==password || password.length<6)){
			Ext.ux.Toast.msg('提示信息','密码至少是6位任意字符,请重新输入!!!');
			return;
		}

		if(this.formPanel.getForm().isValid()){
			var p2pAccount=this.getCmpByName('p2pAccount').getValue();
			var plainpassword=this.getCmpByName('plainpassword').getValue();
			var memberId=this.getCmpByName('memberId').getValue();
			var userId=this.getCmpByName('userId').getValue();
			var objectGridPanel=Ext.getCmp('RecommendCodeCreateView').centerPanel;
			var email=this.getCmpByName('email').getValue();
			var mobile=this.getCmpByName('mobile').getValue();
			var fullname=this.getCmpByName('fullname').getValue();
			if(this.type==1){
				Ext.Ajax.request({
								url :__ctxPath + '/customer/isExistBpCustRelation.do',
								params : {
									Q_loginname_S_EQ:p2pAccount
								},
								scope:this,
								async :  false,
								method : 'post',
								success : function(response) {
									var result = Ext.util.JSON.decode(response.responseText);
										if (result.success) {
											memberId=result.memberId;
										} else {
											Ext.ux.Toast.msg('提示信息','P2P账号不存在,请重新填写!!!');
								}
							}
			})
			}
			Ext.Ajax.request({
				url :__ctxPath + '/customer/saveP2PBpCustRelation.do',
				params : {
					password : password,
					p2pAccount:p2pAccount,
					fullname:fullname,
					userId:userId,
					email:email,
					mobile:mobile,
					plainpassword:plainpassword,
					memberId:memberId,
					type:this.type
				},
				scope:this,
				method : 'post',
				success : function(response) {
					var result = Ext.util.JSON.decode(response.responseText);
					if (result.success) {
						Ext.ux.Toast.msg('提示信息', result.msg);
						objectGridPanel.getStore().reload();
						this.close();
					} else {
						Ext.ux.Toast.msg('提示信息', result.msg);
					}
				}
			})
		}
	}
});