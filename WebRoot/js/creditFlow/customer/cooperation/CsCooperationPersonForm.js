/**
 * @author
 * @createtime
 * @class CsCooperationPersonForm
 * @extends Ext.Window
 * @description CsCooperationPerson表单
 * @company 智维软件
 */
CsCooperationPersonForm = Ext.extend(Ext.Window, {
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		// 必须先初始化组件
		this.initUIComponents();
		CsCooperationPersonForm.superclass.constructor.call(this, {
					id : 'CsCooperationPersonFormWin'+this.type,
					layout : 'fit',
					items : this.formPanel,
					modal : true,
					height : 500,
					width : 800,
					maximizable : true,
					title : '合作个人档案管理',
					buttonAlign : 'center',
					buttons : [{
								text : '保存',
								iconCls : 'btn-save',
								scope : this,
								handler : this.save
							},/* {
								text : '重置',
								iconCls : 'btn-reset',
								scope : this,
								handler : this.reset
							},*/ {
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
					autoScroll : true,
					frame : true,
					anchor : '100%',
					labelAlign : 'right',
					defaults : {
						anchor : '95%',
						labelWidth : 80
					},
					// id : 'CsCooperationPersonForm',
					items : [{
								xtype : 'fieldset',
								title : '个人信息',
								bodyStyle : 'padding-left:10px',
								collapsible : true,
								labelAlign : 'right',
								autoHeight:true,
								items : [{
								layout : "column",
								border : false,
								scope : this,
								items:[
									{
										name : 'csCooperationPerson.id',
										xtype : 'hidden'
									},{
										name : 'csCooperationPerson.type',
										xtype : 'hidden',
										value : this.type
									},
									{
										name : 'csCooperationPerson.isCheckCard',
										xtype : 'hidden'
									},
									{
										name : 'csCooperationPerson.isOpenP2P',
										xtype : 'hidden'
									},
									{
										columnWidth : .5,
										layout : 'form',
										labelWidth : 80,
										labelAlign : 'right',
										border : false,
										items : [{
												fieldLabel : '姓名',
												allowBlank : false,
												readOnly : this.readOnly,
												name : 'csCooperationPerson.name',
												anchor : "100%",
												xtype : 'textfield'
										}]
									},
									{
										columnWidth : .5,
										layout : 'form',
										labelWidth : 80,
										labelAlign : 'right',
										border : false,
										items : [{
												fieldLabel : '性别',
												allowBlank : false,
												readOnly : this.readOnly,
												anchor : "100%",
												xtype : 'combo',
												mode : 'local',
												displayField : 'name',
												valueField : 'id',
												editable : false,
												triggerAction : 'all',
												store : new Ext.data.SimpleStore({
													fields : ["name", "id"],
													data : [["男", "0"],["女", "1"]]
												}),
												hiddenName : 'csCooperationPerson.sex'
												
										}]
									},
									{
										columnWidth : .5,
										layout : 'form',
										labelWidth : 80,
										labelAlign : 'right',
										border : false,
										items : [{
												xtype : "dickeycombo",
												readOnly : this.readOnly,
												allowBlank : false,
												nodeKey : 'card_type_key',
												hiddenName : "csCooperationPerson.cardType",
												fieldLabel : "证件类型",
												anchor : '100%',
												editable : false,
												listeners : {
													scope : this,
													afterrender : function(combox) {
														var st = combox.getStore();
														st.on("load", function() {
																	combox.setValue(combox
																			.getValue());
																	combox.clearInvalid();
																})
													}
												}
												
										}]
									},
									{
										columnWidth : .5,
										layout : 'form',
										labelWidth : 80,
										labelAlign : 'right',
										border : false,
										items : [{
											fieldLabel : '证件号码',
											readOnly : this.readOnly,
											allowBlank : false,
											name : 'csCooperationPerson.cardNumber',
											anchor : "100%",
											xtype : 'textfield',	
											listeners : {
											    scope:this,
											   'blur' : function(f) {
													if(this.getCmpByName('csCooperationPerson.cardType').getValue()==309){
														if(validateIdCard(f.getValue())==1){
															Ext.Msg.alert('身份证号码验证','证件号码不正确,请仔细核对')
															f.setValue("");
															return;
														}else if(validateIdCard(f.getValue())==2){
															Ext.Msg.alert('身份证号码验证','证件号码地区不正确,请仔细核对')
															f.setValue("");
															return;
														}else if(validateIdCard(f.getValue())==3){
															Ext.Msg.alert('身份证号码验证','证件号码生日日期不正确,请仔细核对')
															f.setValue("");
															return;
														}
													}
													 var cardNumber = f.getValue();
													 var pid=this.getCmpByName('csCooperationPerson.id').getValue();
													 var sex=this.getCmpByName('csCooperationPerson.sex');
													 var personId = (pid=="")?0:pid;
													 Ext.Ajax.request({
									                    url:  __ctxPath + '/creditFlow/customer/cooperation/verificationCsCooperationPerson.do',
									                    method : 'POST',
									                    params : {
															cardNum : cardNumber,
															personId: personId
														},
									                    success : function(response,request) {
															var obj=Ext.util.JSON.decode(response.responseText);
						                            		if(obj.msg == false){					                            			
						                            			Ext.ux.Toast.msg('操作信息',"该证件号码已存在，请重新输入");
						                            			f.setValue("");
						                            		}else{
																if(f.getValue().split("").reverse()[1]%2==0){
						                            				sex.setValue(1);
						                            				sex.setRawValue("女")
						                            			}else{
						                            				sex.setValue(0);
						                            				sex.setRawValue("男")
						                            			}
						                            		}
								                      	}
						                             });
														
												}		
											}
										}]
									},{
										columnWidth : .5,
										layout : 'form',
										labelWidth : 80,
										labelAlign : 'right',
										border : false,
										items : [{
												fieldLabel : '手机号码',
												readOnly : this.readOnly,
												allowBlank : false,
												name : 'csCooperationPerson.phoneNumber',
												anchor : "100%",
												xtype : 'textfield'
										}]
									},
									{
										columnWidth : .5,
										layout : 'form',
										labelWidth : 80,
										labelAlign : 'right',
										border : false,
										items : [{
												fieldLabel : '邮箱',
												allowBlank : false,
												readOnly : this.readOnly,
												name : 'csCooperationPerson.email',
												anchor : "100%",
												xtype : 'textfield'
										}]
									},
									{
										columnWidth : .5,
										layout : 'form',
										labelWidth : 80,
										labelAlign : 'right',
										border : false,
										items : [{
												fieldLabel : 'QQ',
												readOnly : this.readOnly,
												name : 'csCooperationPerson.qqNumber',
												anchor : "100%",
												xtype : 'textfield'
										}]
									},
									{
										columnWidth : .5,
										layout : 'form',
										labelWidth : 80,
										labelAlign : 'right',
										border : false,
										items : [{
												fieldLabel : '微信',
												readOnly : this.readOnly,
												name : 'csCooperationPerson.weixinNumber',
												anchor : "100%",
												xtype : 'textfield'
										}]
									},
									{
										columnWidth : 1,
										layout : 'form',
										labelWidth : 80,
										labelAlign : 'right',
										border : false,
										items : [{
												fieldLabel : '备注',
												readOnly : this.readOnly,
												name : 'csCooperationPerson.remark',
												anchor : "100%",
												xtype : 'textarea'
										}]
									}
									]}]
								},{
											xtype : 'fieldset',
											title : '材料清单',
											bodyStyle : 'padding-left:0px',
											collapsible : true,
											labelAlign : 'right',
											autoHeight:true,
											items : [/*new SlReportView({
												projectId : this.projectId,
												businessType : 'SmallLoan',
												Template:'CsCooperationPerson',
												readOnly : this.readOnly,
												isHidden_report : false
											})*/
											new uploads({
												projectId : this.id,
												isHidden : this.readOnly,
												tableName :'csCooperationPerson',
												typeisfile :'csCooperationPerson',
												businessType : 'csCooperationPerson',
												uploadsSize : 15
												
											})
											
											]
									}]
				});
		// 加载表单对应的数据
		if (this.id != null && this.id != 'undefined') {
			var formPanel=this.formPanel;
			var cardNumber=formPanel.getCmpByName('csCooperationPerson.cardNumber');
			var cardType=formPanel.getCmpByName('csCooperationPerson.cardType');
			formPanel.loadData({
				url : __ctxPath+ '/creditFlow/customer/cooperation/getCsCooperationPerson.do?id='+ this.id,
				root : 'data',
				preName : 'csCooperationPerson',
				success : function(response, options) {
					var respText = response.responseText;
					var alarm = Ext.util.JSON.decode(respText);
					//如果该用户已经开通并实名认证了p2p账户则组织机构代码不允许编辑
					if(alarm.data.isCheckCard){
						cardNumber.setReadOnly(true);
						if(cardNumber.getEl().dom.className.indexOf("readOnlyClass")==-1){
							cardNumber.getEl().dom.className+=" readOnlyClass";
						}
						cardType.setReadOnly(true);
						if(cardType.getEl().dom.className.indexOf("readOnlyClass")==-1){
							cardType.getEl().dom.className+=" readOnlyClass";
						}
					}
				}
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
		$postForm({
			formPanel : this.formPanel,
			scope : this,
			url : __ctxPath
					+ '/creditFlow/customer/cooperation/saveCsCooperationPerson.do',
			callback : function(fp, action) {
				var gridPanel = Ext.getCmp('CsCooperationPersonGrid');
				if (gridPanel != null) {
					gridPanel.getStore().reload();
				}
				this.close();
			}
		});
	}// end of save

});