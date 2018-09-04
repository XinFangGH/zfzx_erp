/**
 * @author 
 * @createtime 
 * @class InvestPersonCareForm
 * @extends Ext.Window
 * @description InvestPersonCare表单
 * @company 智维软件
 */
InvestPersonCareForm = Ext.extend(Ext.Window, {
			//构造函数
			constructor : function(_cfg) {
				Ext.applyIf(this, _cfg);
				if(_cfg.investPerson != null){
					this.investPerson = _cfg.investPerson;
					this.isflag = true;
				};
				if (typeof (_cfg.investPersonId) != "undefined") {
					this.investPersonId = _cfg.investPersonId;
				}
				if (typeof (_cfg.careHidden) != "undefined") {
					this.careHidden = _cfg.careHidden;
				}else{
					this.careHidden = false;
				}
				if (typeof (_cfg.isHidden) != "undefined") {
					this.isHidden = _cfg.isHidden;
				}else{
					this.isHidden = false;
				}
				if(typeof(_cfg.isCloHidden)!="undefined"){
					this.isCloHidden = _cfg.isCloHidden
//					alert(this.isCloHidden)
				}else{
					this.isCloHidden = false;
				}
				if(typeof (_cfg.isHiddenEdit) != "undefined"){
					this.isHiddenEdit = _cfg.isHiddenEdit
				}
				//必须先初始化组件
				this.initUIComponents();
				InvestPersonCareForm.superclass.constructor.call(this, {
							id : 'InvestPersonCareFormWin',
							layout : 'fit',
							items : this.formPanel,
							modal : true,
							height : this.careHidden?400:600,
							width : 750,
							maximizable : true,
//							closeAction : 'hide',
							title : '投资人关怀管理详细信息',
							buttonAlign : 'center',
							buttons : this.careHidden?null:[
										{
											text : '保存',
											iconCls : 'btn-save',
											scope : this,
											handler : this.saveRs
										},/* {
											text : '重置',
											iconCls : 'btn-reset',
											scope : this,
											handler : this.reset
										}, */{
											text : '取消',
											iconCls : 'btn-cancel',
											scope : this,
											handler : this.cancel
										}
							         ],
							         listeners : {  
							            'close' : function() { 
											Ext.getCmp('InvestPersonGrid').getStore().reload();
							            }  
							        } 
				});
			},//end of the constructor
			//初始化组件
			initUIComponents : function() {
			
				var investPerson = this.investPerson;
				var investPersonCare = this.InvestPersonCare;
				this.formPanel = new Ext.FormPanel({
							id : 'InvestPersonCareForm',
							layout : 'form',
							bodyStyle : 'padding:10px',
							autoScroll : true,
							frame : true,
							labelAlign : 'right',
							defaults : {
									anchor : '98%',
									columnWidth : 1,
									labelWidth : 60
								},
							items : [{
								layout : "column",
								xtype : 'fieldset',
								title : '个人基本信息',
								collapsible : true,
								hidden : this.isHidden,
								items : [{
									layout : "column",
									columnWidth : 1,
									items : [{
										layout : 'form',
										columnWidth : .33,
										labelWidth : 70,
										labelAlign : 'right',
										items : [{
													fieldLabel : '姓名',
													readOnly : this.isLook,
													xtype : 'textfield',
													name : 'csInvestmentperson.investName',
													anchor : "100%",
													maxLength : 15
												}, {
													xtype : "dickeycombo",
													nodeKey : 'card_type_key',
													fieldLabel : '证件类型',
													readOnly : this.isLook,
													hiddenName : 'csInvestmentperson.cardtype',
													anchor : "100%",
													listeners : {
														afterrender : function(combox) {
															var st = combox.getStore();
															st.on("load", function() {
																if (combox.getValue() == 0
																		|| combox.getValue() == 1
																		|| combox.getValue() == ""
																		|| combox.getValue() == null) {
																	combox.setValue("");
																} else {
																	combox.setValue(combox
																			.getValue());
																}
																combox.clearInvalid();
															})
														}
													}
												}/*,{
													fieldLabel : '联系人姓名',
													xtype : 'textfield',
													name : 'csInvestmentperson.linkmanName',
													readOnly : this.isLook,
													value : investPerson == null ? '' : investPerson.linkmanName,
													anchor : '100%'
												}*/]
									}, {
										layout : 'form',
										columnWidth : .33,
										labelWidth : 70,
										items : [{
											xtype : "dickeycombo",
											nodeKey : 'sex_key',
											fieldLabel : '性别',
											columnWidth : 3,
											hiddenName : 'csInvestmentperson.sex',
//											allowBlank : false,
											readOnly : this.isLook,
											editable : false,
											blankText : "性别不能为空，请正确填写!",
											anchor : "100%",
											listeners : {
												afterrender : function(combox) {
													var st = combox.getStore();
													st.on("load", function() {
														if (combox.getValue() == 0
																|| combox.getValue() == 1
																|| combox.getValue() == ""
																|| combox.getValue() == null) {
															combox.setValue("");
														} else {
															combox.setValue(combox
																	.getValue());
														}
														combox.clearInvalid();
													})
												}
											}
										}, {
											fieldLabel : '证件号码',
											name : 'csInvestmentperson.cardnumber',
											readOnly : this.isLook,
//											allowBlank : false,
											xtype : 'textfield',
											blankText : "证件号码不能为空，请正确填写!",
											anchor : "100%",
											maxLength : 25,
											listeners : {
												scope:this,
												'beforerender':function(com){
													if(this.getCmpByName('csInvestmentperson.cardtype').getValue()==309){
														if(validateIdCard(com.getValue())==1){
															Ext.ux.Toast.msg('身份证号码验证','证件号码不正确,请仔细核对')
														}else if(validateIdCard(com.getValue())==2){
															Ext.ux.Toast.msg('身份证号码验证','证件号码地区不正确,请仔细核对')
															
														}else if(validateIdCard(com.getValue())==3){
															Ext.ux.Toast.msg('身份证号码验证','证件号码生日日期不正确,请仔细核对')														
														}
													}
												}
											}
										}/*,{
												fieldLabel : '联系人电话',
												xtype : 'textfield',
												name : 'csInvestmentperson.linkmanPhone',
												value : investPerson == null ? '' : investPerson.linkmanPhone,
												readOnly : this.isLook,
												anchor : '100%'
											
										}*/]
									}, {
										layout : 'form',
										columnWidth : .34,
										labelWidth : 60,
										items : [{
													fieldLabel : '手机号码',
													readOnly : this.isLook,
													xtype : 'textfield',
													name : 'csInvestmentperson.cellphone',
//													allowBlank : false,
													blankText : "手机号码不能为空，请正确填写!",
													anchor : "100%",
													maxLength : 11
												}, {
													fieldLabel : '出生日期',
													readOnly : this.isLook,
													name : 'csInvestmentperson.birthDay',
													xtype : 'datefield',
													anchor : "100%",
													format : 'Y-m-d'
													// value : new Date()
											}/*,{
												fieldLabel : '关系',
												xtype : 'textfield',
												name : 'csInvestmentperson.filiation',
												readOnly : this.isLook,
												anchor :'100%'
											}*/]
									}, {
										name : 'csInvestmentperson.investId',
										xtype : 'hidden'
									}]
								}, {
									layout : "column",
									columnWidth : 1,
									items : [{
										layout : 'form',
										columnWidth : .33,
										labelAlign : 'right',
										labelWidth : 70,
										items : [{
												xtype : "dickeycombo",
												hiddenName : "csInvestmentperson.customerLevel",
												nodeKey : 'finaing_customerLevel', // xx代表分类名称
												fieldLabel : "客户级别",
												emptyText : "请选择",
												readOnly : this.isLook,
												anchor : '100%',
												listeners : {
													afterrender : function(combox) {
														var st = combox.getStore();
														st.on("load", function() {
															
															combox.setValue(combox.getValue());
															combox.clearInvalid();
														})
											       }
												}
											/*
											xtype : 'combo',
											fieldLabel : '客户级别',
											anchor : '100%',
											hiddenName : 'investPerson.customerLevel',
											triggerAction : 'all',
											editable :false,
											readOnly : false,
								            store :new Ext.data.ArrayStore({
								                autoLoad : true,
								                baseParams : {
								                    nodeKey : 'finaing_customerLevel'
								                },
								                url : __ctxPath + '/system/loadDicKeyByNodeKeyDictionary.do',
								                fields : ['dicKey', 'itemValue']
								            }),
								            displayField : 'itemValue',
								            valueField : 'dicKey',
								            listeners :{
												afterrender : function(combox) {
												var st = combox.getStore();
											}
											},
//										value : investPerson == null ? '' : investPerson.customerLevel,
									renderer : function(value){
										alert(value)
										if(value == "potentialCustomers"){
											return '潜在客户';
										}else if(value == "formalCustomer"){
											return '正式客户';
										}else if(value == "bigCustomer"){
											return '大客户';
										}else{
											return '';
										}
									}
											
										*/}]
										},{
												layout : 'form',
												columnWidth : .33,
												labelAlign : 'right',
												labelWidth : 70,
												items : [{
															fieldLabel : '通信地址',
															readOnly : this.isLook,
//															colspan : 2,
															name : 'csInvestmentperson.postaddress',
															xtype : 'textfield',
															anchor : "100%"
														}]
											},{
												layout : 'form',
												columnWidth : .34,
												labelWidth : 60,
													items : [{
														fieldLabel : '邮箱',
														readOnly : this.isLook,
//														allowBlank : false,
														name : 'csInvestmentperson.selfemail',
														xtype : 'textfield',
														anchor : "100%"
													}]
												}]
								}]
					},{
						layout : "column",
						xtype : 'fieldset',
						title : '客户关怀记录',
						collapsible : true,
						hidden : this.isCloHidden,
						items :[new CustomerCareRecords({
							peid :this.investPersonId	,
							isEnterprise : 1,
							isHiddenEdit : this.isHiddenEdit
						})]
					},{
						layout : "column",
						xtype : 'fieldset',
						title : '立即关怀',
						collapsible : true,
						hidden : this.careHidden,
						items : [{
							layout : "column",
							columnWidth : 1,
							items : [{
								xtype : "hidden",
								name : 'investPersonCare.id'
							},{
									layout : 'form',
									columnWidth : .50,
									labelWidth : 60,
									labelAlign : 'right',
									xtype : 'container',
									items : [{
										xtype : "dickeycombo",
										hiddenName : "investPersonCare.careWay",
										nodeKey : 'careWay', // xx代表分类名称
										fieldLabel : "关怀方式",
										emptyText : "请选择",
										readOnly : this.isLookCare,
										allowBlank : false,
										anchor : '100%',
										listeners : {
											afterrender : function(combox) {
												var st = combox.getStore();
												st.on("load", function() {
													
													combox.setValue(combox.getValue());
													combox.clearInvalid();
												})
									       }
										}
									}]
							},{
								layout : 'form',
								columnWidth : .50,
								labelWidth : 60,
								labelAlign : 'right',
								items : [{
											fieldLabel : '关怀标题',
											xtype : 'textfield',
											name : 'investPersonCare.careTitle',
											allowBlank : false,
											readOnly : this.isLookCare,
											anchor : '100%'
										}]
								}]
							},{
								layout : "column",
								columnWidth : 1,
								items : [{
									layout : 'form',
									columnWidth : 1,
									labelWidth : 60,
									labelAlign : 'right',
									items : [{
												fieldLabel : '关怀内容',
												xtype : 'textfield',
												allowBlank : false,
												name : 'investPersonCare.careContent',
												readOnly : this.isLookCare,
												anchor : '100%'
											}]
									}]
							},{
								layout : "column",
								columnWidth : 1,
								items : [{
									layout : 'form',
									columnWidth : .50,
									labelWidth : 60,
									items : [{
												fieldLabel : '关怀时间',
												readOnly : this.isLookCare,
												name : 'investPersonCare.careDate',
												allowBlank : false,
//												editor : new Ext.form.DateField({}),
												xtype : 'datefield',
												format : 'Y-m-d',
												anchor : "100%"
										}]
								},{
									layout : 'form',
									columnWidth : .50,
									labelWidth : 60,
									labelAlign : 'right',
									items : [{
											xtype : "combo",
											triggerClass : 'x-form-search-trigger',
											hiddenName : "investPersonCare.careMan",
//											name : "investPersonCare.careMan",
											editable : false,
											fieldLabel : "关怀人",
											blankText : "关怀人不能为空，请正确填写!",
											allowBlank : false,
											readOnly : this.isAllReadOnly,
											anchor : "100%",
											onTriggerClick : function(cc) {
												var obj = this;
												var appuerIdObj = obj.nextSibling();
												var userIds = appuerIdObj.getValue();
												if ("" == obj.getValue()) {
													userIds = "";
												}
												new UserDialog({
													single : false,
													userIds : userIds,
													userName : obj.getValue(),
													callback : function(uId, uname) {
														obj.setValue(uId);
														obj.setRawValue(uname);
														appuerIdObj.setValue(uId);
													}
												}).show();

								}
										},{
											xtype : 'hidden',
											value :""
//											name : 'careMan'
										}/*{
												fieldLabel : '关怀人',
												xtype : 'textfield',
												allowBlank : false,
												name : 'investPersonCare.careMan',
												readOnly : this.isLookCare,
												value : investPersonCare == null ? '' : investPersonCare.careMan,
												anchor : '100%'
											}*/]
									}]
							},{
								layout : "column",
								columnWidth : 1,
								items : [{
									layout : 'form',
									columnWidth : 1,
									labelWidth : 60,
									labelAlign : 'right',
									items : [{
												fieldLabel : '备注',
												xtype : 'textarea',
												name : 'investPersonCare.careMarks',
												readOnly : this.isLookCare,
												anchor : '100%'
											},{
												xtype : 'hidden',
												name : 'investPersonCare.isEnterprise',
												value : 1
											}]
									}]
							}]
					}]
						});
				//加载表单对应的数据	
				if (this.investPersonId != null && this.investPersonId != 'undefined') {
					this.formPanel.loadData({
								url : __ctxPath + '/customer/getInvestPersonInvestPersonCare.do?investPersonId='+ this.investPersonId,
								root : 'data',
								preName : ['investPersonCare','csInvestmentperson'],
								scope : this,
								success : function(resp, options) {
									
									var obj = Ext.decode(resp.responseText);
								  	this.getCmpByName('investPersonCare.careMan').setValue(obj.data.appUserId);
								   	this.getCmpByName('investPersonCare.careMan').setRawValue(obj.data.appUserName);
								   	this.getCmpByName('investPersonCare.careMan').nextSibling().setValue(obj.data.appUserId);
//								   	this.getCmpByName('careMan').setValue(obj.data.userId)
								}
							});
				}
				
			},//end of the initcomponents

			/**
			 * 重置
			 * @param {} formPanel
			 */
			reset : function() {
				this.formPanel.getForm().reset();
			},
			/**
			 * 取消
			 * @param {} window
			 */
			cancel : function() {
				this.close();
			},
			/**
			 * 保存记录
			 */
			saveRs : function(){
				var pannel = this;
				this.formPanel.getForm().submit({
					url:__ctxPath + '/customer/saveInvestPersonCare.do?',
					method : 'POST',
					scope : this,
					//root :'tesult',
					params :{
						investPersonId : this.investPersonId
					},
					success : function(response, options){
						Ext.ux.Toast.msg('状态', '保存成功');
//						alert(this.investPersonId);
						Ext.getCmp('CustomerCareRecords').getStore().reload();
						this.getCmpByName('investPersonCare.careWay').setValue(null);
						this.getCmpByName('investPersonCare.careTitle').setValue(null);
						this.getCmpByName('investPersonCare.careContent').setValue(null);
						this.getCmpByName('investPersonCare.careDate').setValue(null);
//						this.getCmpByName('investPersonCare.careMan').setValue(null);
						this.getCmpByName('investPersonCare.careMarks').setValue(null);
					//	this.formPanel.getForm().reset();
					//	Ext.getCmp('InvestPersonCareForm').getForm().reset();
					}
				});
			},
			save : function() {
				/*$postForm({
						formPanel:this.formPanel,
						scope:this,
						url:__ctxPath + '/customer/saveInvestPersonCare.do',
						callback:function(fp,action){
							var gridPanel = Ext.getCmp('InvestPersonCareGrid');
							if (gridPanel != null) {
								gridPanel.getStore().reload();
							}
							this.close();
						}
					});*/
			}//end of save

		});