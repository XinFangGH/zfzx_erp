/*ownFund = Ext.extend(Ext.Panel, {
	layout : "form",
	autoHeight : true,
	labelAlign : 'right',
	isAllReadOnly : false,
	isReadOnly:false,
	idDefinition : 'liucheng',
	constructor : function(_cfg) {
		if (_cfg == null) {
			_cfg = {};
		}
		if (_cfg.isAllReadOnly) {
			this.isAllReadOnly = _cfg.isAllReadOnly;
		}
		if (_cfg.isReadOnly) {
			this.isReadOnly = _cfg.isReadOnly;
		}
		if (_cfg.idDefinition) {
			this.idDefinition = _cfg.idDefinition;
		}
		Ext.applyIf(this, _cfg);
		this.idDefinition = this.projectId + this.idDefinition;
		var leftlabel = 85;
		var centerlabel = 115;
		var rightlabel = 115;
		var storepayintentPeriod = "[";
		for (var i = 1; i < 31; i++) {
			storepayintentPeriod = storepayintentPeriod + "[" + i + ", " + i+ "],";
		}
		storepayintentPeriod = storepayintentPeriod.substring(0,storepayintentPeriod.length - 1);
		storepayintentPeriod = storepayintentPeriod + "]";
		var obstorepayintentPeriod = Ext.decode(storepayintentPeriod);
		ownFund.superclass.constructor.call(this, {
			items : [{
				layout : "column",
				border : false,
				scope : this,
				defaults : {
					anchor : '100%',
					columnWidth : 1,
					isFormField : true,
					labelWidth : leftlabel
				},
				items : [{
					columnWidth : .34,
					layout : 'form',
					defaults : {
						anchor : '88%'
					},
					labelWidth : 85,
					items : [ {
						fieldLabel : "资金来源",
						xtype:'combo',
						mode : 'local',
						hiddenName : 'ownBpFundProject.fundResource',
					    displayField : 'name',
					    valueField : 'value',
					    width : 70,
					    store : new Ext.data.SimpleStore({
								fields : ["name", "value"],
								data : [["小贷", "SmallLoan"],["典当", "Pawn"]]
						}),
						triggerAction : "all",
						listeners : {
							scope : this,
							'select' : function(combox,record,index){
								var r=combox.getValue();
								
							}
						}
					}]
				},{
					columnWidth : .66,
					layout : 'form',
					labelWidth : 50,
					defaults : {
						anchor : '39%'
					},
					items:[{
						fieldLabel : "投资方",
						xtype:'combo',
						mode : 'local',
						id:'investName',
						hiddenName : 'ownBpFundProject.investName',
					    triggerClass : 'x-form-search-trigger',
						resizable : true,
						onTriggerClick : function(o) {
							var fundResource=this.ownerCt.ownerCt.getCmpByName('ownBpFundProject.fundResource').value;
							selectInvestEnterPrise(selectInvestEnterpriseObj,fundResource);
						},
						mode : 'remote',
						editable : false,
						lazyInit : false,
						allowBlank : false,
						typeAhead : true,
						minChars : 1,
						width : 100,
						listWidth : 150,
						store : new Ext.data.JsonStore({}),
						triggerAction : 'all'
					},{
						xtype:'hidden',
						id:'investId',
						name:'ownBpFundProject.investorIds'
					}]
				},{
					columnWidth : .3, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : leftlabel,
					items : [{
						xtype : "textfield",
						labelWidth : rightlabel,
						fieldLabel : "对接金额",
						fieldClass : 'field-align',
						name : "ownBpFundProjectMoney",
						readOnly : this.isReadOnly,
						allowNegative : false, // 允许负数
						style : {
							imeMode : 'disabled'
						},
						blankText : "贷款金额不能为空，请正确填写!",
						allowBlank : false,
						anchor : "100%",// 控制文本框的长度
						listeners : {
							scope : this,
							afterrender : function(obj) {
								obj.on("keyup")
							},
							change : function(nf) {
								var value = nf.getValue();
								var index = value.indexOf(".");
								if (index <= 0) { // 如果第一次输入 没有进行格式化
									nf.setValue(Ext.util.Format.number(value,'0,000.00'))
									this.getCmpByName("ownBpFundProject.ownJointMoney").setValue(value);
								} else {
									if (value.indexOf(",") <= 0) {
										var ke = Ext.util.Format.number(value,'0,000.00')
										nf.setValue(ke);
										this.getCmpByName("ownBpFundProject.ownJointMoney").setValue(value);
									} else {
										var last = value.substring(index + 1,value.length);
										if (last == 0) {
											var temp = value	.substring(0, index);
											temp = temp.replace(/,/g, "");
											this.getCmpByName("ownBpFundProject.ownJointMoney").setValue(temp);
											nf.setValue(Ext.util.Format.number(temp, '0,000.00'))
										} else {
											var temp = value.replace(/,/g, "");
											this.getCmpByName("ownBpFundProject.ownJointMoney").setValue(temp);
											nf.setValue(Ext.util.Format.number(temp, '0,000.00'))
										}
									}
								}
							}
						}
					}, {
						xtype : "hidden",
						name : "ownBpFundProject.ownJointMoney"
					}]
				}, {
					columnWidth : .04, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : 20,
					items : [{
						fieldLabel : "元 ",
						labelSeparator : '',
						anchor : "100%"
					}]
				}, {
					columnWidth : .27,
					layout : 'form',
					labelWidth : 50,
					defaults : {
						anchor : '96%'
					},
					items : [{
						fieldLabel : "币种",
						xtype : "dickeycombo",
						hiddenName : 'ownBpFundProject.currency',
						displayField : 'itemName',
						readOnly : this.isAllReadOnly,
						itemName : '注册资金币种', // xx代表分类名称
						allowBlank : false,
						editable : false,
						blankText : "币种不能为空，请正确填写!",
						nodeKey : 'capitalkind',
						emptyText : "请选择",
						listeners : {
							afterrender : function(combox) {
								var st = combox.getStore();
								st.on("load", function() {
									combox.setValue(combox	.getValue());
									combox.clearInvalid();
								})
							}
						}
					}]
				}, {
					columnWidth : .36,
					layout : 'form',
					defaults : {
						anchor : '100%'
					},
					labelWidth:rightlabel,
					defaults : {
						anchor : '100%',
						labelWidth : 85+40
					},
					items : [{
						fieldLabel : "期望资金到位日期",
						xtype : "datefield",
						style : {
							imeMode : 'disabled'
						},
						name : "ownBpFundProject.startDate",
						allowBlank : false,
						readOnly : this.isAllReadOnly,
						blankText : "放款日期不能为空，请正确填写!",
						format : 'Y-m-d',
						listeners : {
							scope : this,
							'change' : function(){
								var payAccrualType=this.getCmpByName('ownBpFundProject.payaccrualType').getValue();
								var dayOfEveryPeriod=this.getCmpByName('ownBpFundProject.dayOfEveryPeriod').getValue();
								var payintentPeriod=this.getCmpByName('ownBpFundProject.payintentPeriod').getValue();
								var startDate=this.getCmpByName('ownBpFundProject.startDate').getValue();
								var intentDatePanel=this.getCmpByName('ownBpFundProject.intentDate');
								setIntentDate(payAccrualType,dayOfEveryPeriod,payintentPeriod,startDate,intentDatePanel)
							}
						}
					}]
				}, {
					columnWidth : 1,
					layout : 'column',
					items : [{
								columnWidth : .06, // 该列在整行中所占的百分比
								layout : "form", // 从上往下的布局
								labelWidth : leftlabel,
								items : [{
									fieldLabel : "计息方式 ",
									fieldClass : 'field-align',
									anchor : "100%"
								}]
							}, {
								columnWidth : 0.12,
								labelWidth : 5,
								layout : 'form',
								items : [{
									xtype : 'radio',
									boxLabel : '等额本金',
									name : 'f1',
									id : "jixifs1" + this.idDefinition,
									inputValue : false,
									anchor : "100%",
									disabled : this.isAllReadOnly,
									listeners : {
										scope : this,
										check : function(obj, checked) {
											var flag = Ext.getCmp("jixifs1"+ this.idDefinition).getValue();
											if (flag == true) {
												this.getCmpByName('ownBpFundProject.accrualtype').setValue("sameprincipal");
												Ext.getCmp("jixizq1"+ this.idDefinition).setDisabled(true);
												Ext.getCmp("jixizq2"+ this.idDefinition).setDisabled(true);
												Ext.getCmp("jixizq3"+ this.idDefinition).setDisabled(true);
												Ext.getCmp("jixizq4"+ this.idDefinition).setDisabled(true);

												Ext.getCmp("jixizq6"+ this.idDefinition).setDisabled(true);
												this.getCmpByName('ownBpFundProject.dayOfEveryPeriod').setDisabled(true);
												this.getCmpByName('ownBpFundProject.dayOfEveryPeriod').setValue("");

												Ext.getCmp("jixizq2"+ this.idDefinition).setValue(true);
												Ext.getCmp("jixizq1"+ this.idDefinition).setValue(false);
												this.getCmpByName('ownBpFundProject.payaccrualType').setValue("monthPay");

												this.getCmpByName('ownBpFundProject.payintentPeriod').setDisabled(false);
												this.getCmpByName('mqhkri1').hide();
												this.getCmpByName('mqhkri').show();
											}
										}
									}
								}]
							}, {
								columnWidth : 0.12,
								labelWidth : 5,
								layout : 'form',
								items : [{
									xtype : 'radio',
									boxLabel : '等额本息',
									anchor : "100%",
									name : 'f1',
									id : "jixifs2" + this.idDefinition,
									inputValue : false,
									disabled : this.isAllReadOnly,
									listeners : {
										scope : this,
										check : function(obj, checked) {
											var flag = Ext.getCmp("jixifs2"+ this.idDefinition).getValue();
											if (flag == true) {
												this.getCmpByName('ownBpFundProject.accrualtype').setValue("sameprincipalandInterest");
												Ext.getCmp("jixizq1"+ this.idDefinition).setDisabled(true);
												Ext.getCmp("jixizq2"+ this.idDefinition).setDisabled(true);
												Ext.getCmp("jixizq3"+ this.idDefinition).setDisabled(true);
												Ext.getCmp("jixizq4"+ this.idDefinition).setDisabled(true);

												Ext.getCmp("jixizq6"+ this.idDefinition).setDisabled(true);
												this.getCmpByName('ownBpFundProject.dayOfEveryPeriod').setDisabled(true);
												this.getCmpByName('ownBpFundProject.dayOfEveryPeriod').setValue("");

												Ext.getCmp("jixizq2"+ this.idDefinition).setValue(true);
												Ext.getCmp("jixizq1"+ this.idDefinition).setValue(false);
												this.getCmpByName('ownBpFundProject.payaccrualType').setValue("monthPay");

												this.getCmpByName('ownBpFundProject.payintentPeriod').setDisabled(false);
												this.getCmpByName('mqhkri1').hide();
												this.getCmpByName('mqhkri').show();
											}
										}
									}
								}]
							}, {
								columnWidth : 0.12,
								labelWidth : 5,
								layout : 'form',
								items : [{
									xtype : 'radio',
									boxLabel : '按期收息,到期还本',
									name : 'f1',
									id : "jixifs3" + this.idDefinition,
									inputValue : false,
									checked : true,
									anchor : "100%",
									disabled : this.isAllReadOnly,
									listeners : {
										scope : this,
										check : function(obj, checked) {
											var flag = Ext.getCmp("jixifs3"+ this.idDefinition).getValue();
											if (flag == true) {
												this.getCmpByName('ownBpFundProject.accrualtype').setValue("singleInterest");
												Ext.getCmp("jixizq1"+ this.idDefinition).setDisabled(false);
												Ext.getCmp("jixizq2"+ this.idDefinition).setDisabled(false);
												Ext.getCmp("jixizq3"+ this.idDefinition).setDisabled(false);
												Ext.getCmp("jixizq4"+ this.idDefinition).setDisabled(false);
												Ext.getCmp("jixizq6"+ this.idDefinition).setDisabled(false);
												Ext.getCmp("meiqihkrq1" + this.idDefinition).setDisabled(false)
												Ext.getCmp("meiqihkrq2" + this.idDefinition).setDisabled(false)
												this.getCmpByName('ownBpFundProject.dayOfEveryPeriod').setDisabled(false);
											}
										}
									}
								}, {
									xtype : "hidden",
									name : "ownBpFundProject.accrualtype",
									value : "singleInterest"
								}]
							}, {
								columnWidth : 0.51,
								labelWidth : 5,
								layout : 'form',
								items : [{
									xtype : 'radio',
									boxLabel : '一次性收取利息',
									name : 'f1',
									id : "jixifs4" + this.idDefinition,
									inputValue : true,
									anchor : "100%",
									disabled : this.isAllReadOnly,
									listeners : {
										scope : this,
										check : function(obj, checked) {
											var flag = Ext.getCmp("jixifs4"+ this.idDefinition).getValue();
											if (flag == true) {
												this.getCmpByName('ownBpFundProject.accrualtype').setValue("ontTimeAccrual");
												Ext.getCmp("jixizq1"+ this.idDefinition).setDisabled(true);
												Ext.getCmp("jixizq2"+ this.idDefinition).setDisabled(true);
												Ext.getCmp("jixizq3"+ this.idDefinition).setDisabled(true);
												Ext.getCmp("jixizq4"+ this.idDefinition).setDisabled(true);
												Ext.getCmp("jixizq6"+ this.idDefinition).setDisabled(true);
												this.getCmpByName('ownBpFundProject.dayOfEveryPeriod').setDisabled(true);
												this.getCmpByName('ownBpFundProject.dayOfEveryPeriod').setValue("");

												Ext.getCmp("jixizq6"+ this.idDefinition).setValue(true);
												Ext.getCmp("jixizq1"+ this.idDefinition).setValue(false);
												this.getCmpByName('ownBpFundProject.payaccrualType').setValue("owerPay");

												this.getCmpByName('ownBpFundProject.payintentPeriod').setDisabled(true);
												this.getCmpByName('ownBpFundProject.payintentPeriod').setValue(1);
												this.getCmpByName('mqhkri').hide();
												this.getCmpByName('mqhkri1').show();
											} else {
												this.getCmpByName('ownBpFundProject.payintentPeriod').setDisabled(false);
												this.getCmpByName('mqhkri1').hide();
												this.getCmpByName('mqhkri').show();
											}
										}
									}
								}, {
									xtype : "hidden",
									name : "ownBpFundProject.payaccrualType",
									value : "monthPay"
								}]
							}]
				}, {
					columnWidth : 1,
					layout : 'column',
					items : [{
								columnWidth : .06, // 该列在整行中所占的百分比
								layout : "form", // 从上往下的布局
								labelWidth : leftlabel,
								items : [{
									fieldLabel : "计息周期 ",
									fieldClass : 'field-align',
									anchor : "100%"
								}]
							}, {
								columnWidth : 0.12,
								labelWidth : 5,
								layout : 'form',
								items : [{
									xtype : 'radio',
									boxLabel : '日',
									name : 'z1',
									id : "jixizq1" + this.idDefinition,
									inputValue : true,
									anchor : "100%",
									disabled : this.isAllReadOnly,
									listeners : {
										scope : this,
										check : function(obj, checked) {
											var flag = Ext.getCmp("jixizq1"+ this.idDefinition).getValue();
											if (flag == true) {
												this.getCmpByName('ownBpFundProject.payaccrualType').setValue("dayPay");

												Ext.getCmp("meiqihkrq1"+ this.idDefinition).setDisabled(true);
												Ext.getCmp("meiqihkrq1"+ this.idDefinition).setValue(false);
												Ext.getCmp("meiqihkrq2"+ this.idDefinition).setDisabled(true);
												Ext.getCmp("meiqihkrq2"	+ this.idDefinition).setValue(true);

												this.getCmpByName('ownBpFundProject.isStartDatePay').setValue("2");
												var payAccrualType=this.getCmpByName('ownBpFundProject.payaccrualType').getValue();
												var dayOfEveryPeriod=this.getCmpByName('ownBpFundProject.dayOfEveryPeriod').getValue();
												var payintentPeriod=this.getCmpByName('ownBpFundProject.payintentPeriod').getValue();
												var startDate=this.getCmpByName('ownBpFundProject.startDate').getValue();
												var intentDatePanel=this.getCmpByName('ownBpFundProject.intentDate');
												setIntentDate(payAccrualType,dayOfEveryPeriod,payintentPeriod,startDate,intentDatePanel)
											} else {
												Ext.getCmp("meiqihkrq1"+ this.idDefinition).setDisabled(false);
												Ext.getCmp("meiqihkrq2"+ this.idDefinition).setDisabled(false);
												if (Ext.getCmp("meiqihkrq1"+ this.idDefinition).getValue() == true) {
													this.getCmpByName('ownBpFundProject.payintentPerioDate').setDisabled(false);
												}
											}
										}
									}
								}]
							}, {
								columnWidth : 0.12,
								labelWidth : 5,
								layout : 'form',
								items : [{
									xtype : 'radio',
									boxLabel : '月',
									name : 'z1',
									id : "jixizq2" + this.idDefinition,
									inputValue : false,
//									checked : true,
									anchor : "100%",
									disabled : this.isAllReadOnly,
									listeners : {
										scope : this,
										check : function(obj, checked) {
											var flag = Ext.getCmp("jixizq2"+ this.idDefinition).getValue();
											if (flag == true) {
												this.getCmpByName('ownBpFundProject.payaccrualType').setValue("monthPay");
												var payAccrualType=this.getCmpByName('ownBpFundProject.payaccrualType').getValue();
												var dayOfEveryPeriod=this.getCmpByName('ownBpFundProject.dayOfEveryPeriod').getValue();
												var payintentPeriod=this.getCmpByName('ownBpFundProject.payintentPeriod').getValue();
												var startDate=this.getCmpByName('ownBpFundProject.startDate').getValue();
												var intentDatePanel=this.getCmpByName('ownBpFundProject.intentDate');
												setIntentDate(payAccrualType,dayOfEveryPeriod,payintentPeriod,startDate,intentDatePanel)
											}
										}
									}
								}]
							}, {
								columnWidth : 0.12,
								labelWidth : 5,
								layout : 'form',
								items : [{
									xtype : 'radio',
									boxLabel : '季',
									name : 'z1',
									id : "jixizq3" + this.idDefinition,
									inputValue : false,
									anchor : "100%",
									disabled : this.isAllReadOnly,
									listeners : {
										scope : this,
										check : function(obj, checked) {
											var flag = Ext.getCmp("jixizq3"
													+ this.idDefinition)
													.getValue();
											if (flag == true) {
												this.getCmpByName('ownBpFundProject.payaccrualType').setValue("seasonPay");
												var payAccrualType=this.getCmpByName('ownBpFundProject.payaccrualType').getValue();
												var dayOfEveryPeriod=this.getCmpByName('ownBpFundProject.dayOfEveryPeriod').getValue();
												var payintentPeriod=this.getCmpByName('ownBpFundProject.payintentPeriod').getValue();
												var startDate=this.getCmpByName('ownBpFundProject.startDate').getValue();
												var intentDatePanel=this.getCmpByName('ownBpFundProject.intentDate');
												setIntentDate(payAccrualType,dayOfEveryPeriod,payintentPeriod,startDate,intentDatePanel)
											}
										}
									}
								}]
							}, {
								columnWidth : 0.12,
								labelWidth : 5,
								layout : 'form',
								items : [{
									xtype : 'radio',
									boxLabel : '年',
									name : 'z1',
									id : "jixizq4" + this.idDefinition,
									inputValue : false,
									anchor : "100%",
									disabled : this.isAllReadOnly,
									listeners : {
										scope : this,
										check : function(obj, checked) {
											var flag = Ext.getCmp("jixizq4"+ this.idDefinition).getValue();
											if (flag == true) {
												this.getCmpByName('ownBpFundProject.payaccrualType').setValue("yearPay");
												var payAccrualType=this.getCmpByName('ownBpFundProject.payaccrualType').getValue();
												var dayOfEveryPeriod=this.getCmpByName('ownBpFundProject.dayOfEveryPeriod').getValue();
												var payintentPeriod=this.getCmpByName('ownBpFundProject.payintentPeriod').getValue();
												var startDate=this.getCmpByName('ownBpFundProject.startDate').getValue();
												var intentDatePanel=this.getCmpByName('ownBpFundProject.intentDate');
												setIntentDate(payAccrualType,dayOfEveryPeriod,payintentPeriod,startDate,intentDatePanel)
											}
										}
									}
								}]
							}, {
								columnWidth : 0.1,
								labelWidth : 5,
								layout : 'form',
								items : [{
									xtype : 'radio',
									boxLabel : '自定义周期',
									name : 'z1',
									id : "jixizq6" + this.idDefinition,
									inputValue : false,
									anchor : "100%",
									disabled : this.isAllReadOnly,
									listeners : {
										scope : this,
										check : function(obj, checked) {
											var flag = Ext.getCmp("jixizq6"+ this.idDefinition).getValue();
											if (flag == true) {
												this.getCmpByName('ownBpFundProject.payaccrualType').setValue("owerPay");

												this.getCmpByName('ownBpFundProject.dayOfEveryPeriod').setDisabled(false);

												Ext.getCmp("meiqihkrq1"+ this.idDefinition).setDisabled(true);
												Ext.getCmp("meiqihkrq1"+ this.idDefinition).setValue(false);
												Ext.getCmp("meiqihkrq2"+ this.idDefinition).setDisabled(true);
												Ext.getCmp("meiqihkrq2"+ this.idDefinition).setValue(true);
											} else {
												this.getCmpByName('ownBpFundProject.dayOfEveryPeriod').setDisabled(true);
												this.getCmpByName('ownBpFundProject.dayOfEveryPeriod').setValue("");
												if (Ext.getCmp("jixizq1"+ this.idDefinition).getValue() == false) {
													Ext.getCmp("meiqihkrq1"+ this.idDefinition).setDisabled(false);
													Ext.getCmp("meiqihkrq2"+ this.idDefinition).setDisabled(false);
													if (Ext.getCmp("meiqihkrq1"+ this.idDefinition).getValue() == true) {
														this.getCmpByName('ownBpFundProject.payintentPerioDate').setDisabled(false);
													}
												}
											}
										}
									}
								}]
							}, {
								columnWidth : 0.06,
								labelWidth : 5,
								layout : 'form',
								items : [{
									xtype : 'textfield',
									anchor : "100%",
									readOnly : this.isAllReadOnly,
									fieldClass : 'field-align',
									name : 'ownBpFundProject.dayOfEveryPeriod',
									listeners : {
										scope : this,
										'change' : function(){
											var payAccrualType=this.getCmpByName('ownBpFundProject.payaccrualType').getValue();
											var dayOfEveryPeriod=this.getCmpByName('ownBpFundProject.dayOfEveryPeriod').getValue();
											var payintentPeriod=this.getCmpByName('ownBpFundProject.payintentPeriod').getValue();
											var startDate=this.getCmpByName('ownBpFundProject.startDate').getValue();
											var intentDatePanel=this.getCmpByName('ownBpFundProject.intentDate');
											setIntentDate(payAccrualType,dayOfEveryPeriod,payintentPeriod,startDate,intentDatePanel)
										}
									}
								}]
							}, {
								columnWidth : 0.04,
								labelWidth : 20,
								layout : 'form',
								items : [{
									fieldLabel : "天",
									labelSeparator : '',
									anchor : "100%"
								}]
							}
					]
				}, {
					columnWidth : 1,
					layout : 'column',
					items : [{
						columnWidth : .3, // 该列在整行中所占的百分比
						layout : "form", // 从上往下的布局
						labelWidth : leftlabel,
						items : [{
									fieldLabel : "贷款期限",
									xtype : "textfield",
									fieldClass : 'field-align',
									allowBlank : false,
									readOnly : this.isAllReadOnly,
									name : "ownBpFundProject.payintentPeriod",
									anchor : "100%",
									listeners : {
										scope : this,
										'change' : function(){
											var payAccrualType=this.getCmpByName('ownBpFundProject.payaccrualType').getValue();
											var dayOfEveryPeriod=this.getCmpByName('ownBpFundProject.dayOfEveryPeriod').getValue();
											var payintentPeriod=this.getCmpByName('ownBpFundProject.payintentPeriod').getValue();
											var startDate=this.getCmpByName('ownBpFundProject.startDate').getValue();
											var intentDatePanel=this.getCmpByName('ownBpFundProject.intentDate');
											setIntentDate(payAccrualType,dayOfEveryPeriod,payintentPeriod,startDate,intentDatePanel)
										}
									}
								}]
					}, {
						columnWidth : .05, // 该列在整行中所占的百分比
						layout : "form", // 从上往下的布局
						labelWidth : 20,
						items : [{
							fieldLabel : "期",
							labelSeparator : '',
							anchor : "100%"
						}]
					}, {
						columnWidth : .45,
						name : "mqhkri",
						layout : "column",
						items : [{
							columnWidth : 0.278,
							labelWidth : 73,
							layout : 'form',
							items : [{
								xtype : 'radio',
								fieldLabel : '每期还款日',
								boxLabel : '固定在',
								name : 'q1',
								id : "meiqihkrq1" + this.idDefinition,
								inputValue : true,
								anchor : "100%",
								disabled : this.isAllReadOnly,
								listeners : {
									scope : this,
									check : function(obj, checked) {
										var flag = Ext.getCmp("meiqihkrq1"+ this.idDefinition).getValue();
										if (flag == true) {
											this.getCmpByName('ownBpFundProject.isStartDatePay').setValue("1");
											this.getCmpByName('ownBpFundProject.payintentPerioDate').setDisabled(false);
										}
									}
								}
							}, {
								xtype : "hidden",
								name : "ownBpFundProject.isStartDatePay"
							}]
						}, {
							columnWidth : 0.132,
							labelWidth : 1,
							layout : 'form',
							items : [{
								xtype : 'textfield',
								readOnly : this.isAllReadOnly,
								name : "ownBpFundProject.payintentPerioDate",
								xtype : 'combo',
								mode : 'local',
								disabled : true,
								displayField : 'name',
								valueField : 'id',
								editable : true,
								store : new Ext.data.SimpleStore({
									fields : ["name", "id"],
									data : obstorepayintentPeriod
								}),
								triggerAction : "all",
								hiddenName : "ownBpFundProject.payintentPerioDate",
								fieldLabel : "",
								anchor : '100%'
							}]
						}, {
							columnWidth : 0.12,
							labelWidth : 45,
							layout : 'form',
							items : [{
								fieldLabel : "号还款",
								labelSeparator : '',
								anchor : "100%"
							}]
						}, {
							columnWidth : 0.47,
							labelWidth : 10,
							layout : 'form',
							items : [{
								xtype : 'radio',
								boxLabel : '按实际放款日还款',
								name : 'q1',
								id : "meiqihkrq2" + this.idDefinition,
								inputValue : true,
								checked : true,
								anchor : "100%",
								disabled : this.isAllReadOnly,
								listeners : {
									scope : this,
									check : function(obj, checked) {
										var flag = Ext.getCmp("meiqihkrq2"+ this.idDefinition).getValue();
										if (flag == true) {
											this.getCmpByName('ownBpFundProject.isStartDatePay').setValue("2");
											this.getCmpByName('ownBpFundProject.payintentPerioDate').setValue(null);
											this.getCmpByName('ownBpFundProject.payintentPerioDate').setDisabled(true);
										}
									}
								}
							}]
						}]
					}, {
						columnWidth : .45, // 该列在整行中所占的百分比
						layout : "form", // 从上往下的布局
						labelWidth : 140,
						name : "mqhkri1",
						hidden : true,
						hideLabel : true,
						items : [{
							fieldLabel : "<font color='red'>*</font>还款日期",
							xtype : "datefield",
							style : {
								imeMode : 'disabled'
							},
							name : "ownBpFundProject.intentDate",
							readOnly : this.isAllReadOnly,
							blankText : "还款日期不能为空，请正确填写!",
							anchor : "58.3%",
							format : 'Y-m-d'
						}]
					}, {
						columnWidth : .2, // 该列在整行中所占的百分比
						layout : "form", // 从上往下的布局
						labelWidth : 13,
						items : [{
							xtype : "checkbox",
							boxLabel : "是否前置付息",
							disabled : this.isAllReadOnly,
							anchor : "100%",
							name : "isPreposePayAccrualCheck",
							checked : this.record == null|| this.record.data.isPreposePayAccrual == 0? null: true
						}]
					}]
				}, {
					columnWidth : .3, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : leftlabel,
					items : [{
						xtype : "numberfield",
						name : "ownBpFundProject.ownAccrual",
						fieldClass : 'field-align',
						allowBlank : false,
						readOnly : this.isReadOnly,
						decimalPrecision : 4,
						anchor : "100%",
						fieldLabel : "贷款利率"
					}]
				}, {
					columnWidth : .03, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : 40,
					items : [{
						fieldLabel : "%/期",
						labelSeparator : '',
						anchor : "100%"
					}]
				}, {
					columnWidth : .27,
					layout : 'form',
					labelWidth : 105,
					items : [{
						xtype : "numberfield",
						name : "ownBpFundProject.ownManagementConsultingOfRate",
						allowBlank : false,
						fieldLabel : "管理咨询费率",
						decimalPrecision : 4,
						value : 0,
						fieldClass : 'field-align',
						style : {
							imeMode : 'disabled'
						},
						readOnly : this.isReadOnly,
						blankText : "管理咨询费率不能为空，请正确填写!",
						anchor : "100%",
						listeners : {
							scope : this,
							change : function(nf) {
								var value = nf.getValue();
								if (value == null || value == "") {
									nf.setValue(0)
								}
							}
						}
					}]
				}, {
					columnWidth : .03, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : 40,
					items : [{
						fieldLabel : "%/期",
						labelSeparator : '',
						anchor : "100%"
					}]
				}, {
					columnWidth : .34, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : 126,
					items : [{
						xtype : "numberfield",
						name : "ownBpFundProject.ownFinanceServiceOfRate",
						allowBlank : false,
						fieldLabel : "财务服务费率",
						decimalPrecision : 4,
						value : 0,
						fieldClass : 'field-align',
						style : {
							imeMode : 'disabled'
						},
						readOnly : this.isReadOnly,
						blankText : "财务服务率不能为空，请正确填写!",
						anchor : "100%",
						listeners : {
							scope : this,
							change : function(nf) {
								var value = nf.getValue();
								if (value == null || value == "") {
									nf.setValue(0)
								}
							}
						}
					}]
				}, {
					columnWidth : .03, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : 40,
					items : [{
						fieldLabel : "%/期",
						labelSeparator : '',
						anchor : "100%"
					}]
				}, {
					columnWidth : .17, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : 50,
					items : [{
						xtype : "checkbox",
						name : "isAheadPay",
						disabled : this.isAllReadOnly,
						boxLabel : "是否允许提前还款",
						anchor : "100%",
						listeners : {
							scope : this,
							check : function(obj, isChecked) {
								if (isChecked) {
									var operateObj = this.getCmpByName('ownBpFundProject.aheadDayNum');
									Ext.apply(operateObj, {
										allowBlank : false,
										blankText : "提前还款通知天数不能为空!"
									});
									operateObj.setDisabled(false)
								} else {
									var operateObj = this.getCmpByName('ownBpFundProject.aheadDayNum');
									operateObj.setValue("");
									Ext.apply(operateObj, {allowBlank : true});
									operateObj.clearInvalid()
									operateObj.setDisabled(true)
								}
							}
						}
					}]
				}, {
					columnWidth : .13, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : 90,
					labelWidth : 40,
					items : [{
						fieldLabel : "提前",
						xtype : "numberfield",
						fieldClass : 'field-align',
						name : "ownBpFundProject.aheadDayNum",
						// allowBlank : false,
						style : {
							imeMode : 'disabled'
						},
						readOnly : this.isAllReadOnly,
						blankText : "提前还款通知天数不能为空，请正确填写!",
						anchor : "100%"

					}]
				}, {
					columnWidth : .03, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : 50,
					items : [{
						fieldLabel : "天通知",
						labelSeparator : '',
						anchor : "100%"
					}]
				}, {
					columnWidth : .27,
					layout : 'form',
					labelWidth : 106,
					items : [{
						xtype : "numberfield",
						name : "ownBpFundProject.breachRate",
						readOnly : this.isAllReadOnly,
						fieldClass : 'field-align',
						fieldLabel : "违约金比例",
						fieldClass : 'field-align',
						style : {
							imeMode : 'disabled'
						},
						anchor : "100%"
					}, {
						xtype : "hidden",
						name : "ownBpFundProject.projectStatus"
					}]
				}, {
					columnWidth : .05, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : 20,
					items : [{
						fieldLabel : "%",
						labelSeparator : '',
						anchor : "100%"
					}]
				}, {
					columnWidth : .35,
					layout : "column",
					items : [{
						columnWidth : 0.45,
						labelWidth : 70,
						layout : 'form',
						items : [{
							xtype : 'radio',
							fieldLabel : '逾期费率',
							boxLabel : '按天计算:每天',
							name : 'yuqifljsfs',
							id : "yuqifljsfs1" + this.idDefinition,
							inputValue : true,
							anchor : "100%",
							disabled : this.isAllReadOnly,
							listeners : {
								scope : this,
								check : function(obj, checked) {
									var flag = Ext.getCmp("yuqifljsfs1"+ this.idDefinition).getValue();
									if (flag == true) {
										this.getCmpByName('ownBpFundProject.overdueRateType').setValue("1");
										this.getCmpByName('ownBpFundProject.overdueRate').setDisabled(false);
									}
								}
							}
						}, {
							xtype : "hidden",
							name : "ownBpFundProject.overdueRateType"
						}]
					}, {
						columnWidth : 0.15,
						labelWidth : 1,
						layout : 'form',
						items : [{
							xtype : 'textfield',
							readOnly : this.isAllReadOnly,
							name : "ownBpFundProject.overdueRate",
							fieldClass : 'field-align',
							disabled : true,
							fieldLabel : "",
							anchor : '100%'
						}]
					}, {
						columnWidth : .06, // 该列在整行中所占的百分比
						layout : "form", // 从上往下的布局
						labelWidth : 15,
						items : [{
							fieldLabel : "‰",
							labelSeparator : '',
							anchor : "100%"
						}]
					}, {
						columnWidth : .09,
						labelWidth : 30,
						layout : 'form',
						items : [{
							fieldLabel : "罚息",
							labelSeparator : '',
							anchor : "100%"
						}]
					}, {
						columnWidth : 0.2,
						labelWidth : 10,
						layout : 'form',
						items : [{
							xtype : 'radio',
							boxLabel : '按期计算',
							name : 'yuqifljsfs',
							id : "yuqifljsfs2" + this.idDefinition,
							inputValue : true,
							anchor : "100%",
							disabled : this.isAllReadOnly,
							listeners : {
								scope : this,
								check : function(obj, checked) {
									var flag = Ext.getCmp("yuqifljsfs2"	+ this.idDefinition).getValue();
									if (flag == true) {
										this.getCmpByName('ownBpFundProject.overdueRateType').setValue("2");
										this.getCmpByName('ownBpFundProject.overdueRate').setValue(null);
										this.getCmpByName('ownBpFundProject.overdueRate').setDisabled(true);
									}
								}
							}
						}]
					}]
				}]
			}]
		});
		var selectInvestEnterpriseObj=function(obj){
			Ext.getCmp('investId').setValue(obj.id);
			Ext.getCmp('investName').setValue(obj.InvestEnterpriseName);
		}
	}
});*/

ownFund = Ext.extend(Ext.Panel, {
	layout : "form",
	autoHeight : true,
	labelAlign : 'right',
	isAllReadOnly : false,
	isReadOnly:false,
	isHideConsultRate:false,
	isHideFinanceRate:false,
	isOwnBpFundProject:false,
	idDefinition:'liucheng',
	constructor : function(_cfg) {
		if (_cfg == null) {
			_cfg = {};
		}
		if (_cfg.isAllReadOnly) {
			this.isAllReadOnly = _cfg.isAllReadOnly;
		}
		if (_cfg.isReadOnly) {
			this.isReadOnly = _cfg.isReadOnly;
		}
		if (typeof(_cfg.isStartDateReadOnly)!="undefined") {
			this.isStartDateReadOnly = _cfg.isStartDateReadOnly;
		}else{
			this.isStartDateReadOnly=true
		}
		if (_cfg.idDefinition) {
			this.idDefinition = _cfg.idDefinition;
		}
		if(typeof(_cfg.isHideConsultRate)){
			this.isHideConsultRate = _cfg.isHideConsultRate
		}
		if(typeof(_cfg.isHideFinanceRate)){
			this.isHideFinanceRate = _cfg.isHideFinanceRate
		}
		if(typeof(_cfg.isOwnBpFundProject)){
			this.isOwnBpFundProject = _cfg.isOwnBpFundProject
		}
		var idDefinition1=this.idDefinition;
		Ext.applyIf(this, _cfg);
		this.idDefinition=this.projectId+this.idDefinition;
		var leftlabel = 85;
		var centerlabel = 115;
		var rightlabel = 115;
		var storepayintentPeriod="[";
		for (var i = 1; i < 31; i++) {
			storepayintentPeriod = storepayintentPeriod + "[" + i+ ", " + i + "],";
		}
		storepayintentPeriod = storepayintentPeriod.substring(0,storepayintentPeriod.length - 1);
		storepayintentPeriod = storepayintentPeriod + "]";
		var obstorepayintentPeriod = Ext.decode(storepayintentPeriod);
		ownFund.superclass.constructor.call(this, {
			name : 'ownFundPanel',
			items : [{
				layout : "column",
				border : false,
				scope : this,
				defaults : {
					anchor : '100%',
					columnWidth : 1,
					isFormField : true,
					labelWidth : leftlabel
				},
				items : [{
						xtype:'hidden',
						name:'ownBpFundProject.id'
					},{
					columnWidth : .34,
					hidden:true,
					disabled:true,
					layout : 'form',
					defaults : {
						anchor : '88%'
					},
					labelWidth : 85,
					items : [ {
						fieldLabel : "资金来源",
						xtype:'combo',
						mode : 'local',
						readOnly : this.isReadOnly,
						hiddenName : 'ownBpFundProject.fundResource',
					    displayField : 'name',
					    valueField : 'value',
					    width : 70,
					    store : new Ext.data.SimpleStore({
								fields : ["name", "value"],
								data : [["小贷", "SmallLoan"],["典当", "Pawn"]]
						}),
						triggerAction : "all",
						listeners : {
							scope : this,
							'select' : function(combox,record,index){
								var r=combox.getValue();
								
							}
						}
					}]
				},{
					columnWidth : .66,
					hidden:true,
					disabled:true,
					layout : 'form',
					labelWidth : 50,
					defaults : {
						anchor : '39%'
					},
					items:[{
						fieldLabel : "投资方",
						xtype:'combo',
						disabled:true,
						mode : 'local',
						id:'investName',
						readOnly : this.isReadOnly,
						hiddenName : 'ownBpFundProject.investName',
					    triggerClass : 'x-form-search-trigger',
						resizable : true,
						onTriggerClick : function(o) {
							var fundResource=this.ownerCt.ownerCt.getCmpByName('ownBpFundProject.fundResource').value;
							selectInvestEnterPrise(selectInvestEnterpriseObj,fundResource,false);
						},
						mode : 'remote',
						editable : false,
						lazyInit : false,
						allowBlank : false,
						typeAhead : true,
						minChars : 1,
						width : 100,
						listWidth : 150,
						store : new Ext.data.JsonStore({}),
						triggerAction : 'all'
					},{
						xtype:'hidden',
						id:'investorIds',
						name:'ownBpFundProject.investorIds'
					}]
				},{
					columnWidth : .3, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : leftlabel,
					items : [{
						xtype : "textfield",
						labelWidth : rightlabel,
						fieldLabel : (this.isSmall?'贷款金额':"对接金额"),
						fieldClass : 'field-align',
						name : "ownBpFundProjectMoney",
						readOnly : this.isReadOnly,
						allowNegative : false, // 允许负数
						style : {
							imeMode : 'disabled'
						},
						blankText : "贷款金额不能为空，请正确填写!",
						allowBlank : false,
						anchor : "100%",// 控制文本框的长度
						listeners : {
							scope : this,
							afterrender : function(obj) {
							    var ownJoint=this.getCmpByName('ownBpFundProject.ownJointMoney');
								var o = this.ownerCt.ownerCt.getCmpByName('slSmallloanProject.projectMoney')
								if(o && (ownJoint==0 || "undefined"==typeof(ownJoint.getValue()))){
									obj.setValue(o.getValue());
									obj.fireEvent('change',obj);
								}
							},
							blur:function(v){
								if(v.getValue().replace(',','')<0){
									Ext.ux.Toast.msg('警告','请输入正确的对接金额!');
									v.setValue('');
									v.focus(true);
									return;
								}
							},
							change : function(nf,nv,ov) {
								var money=this.projectInfoFinance.getCmpByName('slSmallloanProject.projectMoney').getValue();
								var value = nf.getValue();
								var temp=value;
								var index = value.indexOf(".");
								if(this.ownerCt.ownerCt){
									if(this.ownerCt.ownerCt.getCmpByName('slSmallloanProject.projectMoney')){
										var o = this.ownerCt.ownerCt.getCmpByName('slSmallloanProject.projectMoney');
										var last = value.substring(index + 1,value.length);
										if (last == 0 && index!=-1) {
											temp = value.substring(0, index);
											temp = temp.replace(/,/g, "");
										}else{
											temp = value.replace(/,/g, "");
										}
										if(eval(temp)>eval(o.getValue())){
											Ext.ux.Toast.msg('警告','对接金额大于贷款金额!');
											nf.setValue(ov);
											return;
										}
									}
								}
								if (index <= 0) { // 如果第一次输入 没有进行格式化
									nf.setValue(Ext.util.Format.number(value,'0,000.00'))
									this.getCmpByName("ownBpFundProject.ownJointMoney").setValue(value);
								} else {
									if (value.indexOf(",") <= 0) {
										var ke = Ext.util.Format.number(value,'0,000.00')
										nf.setValue(ke);
										this.getCmpByName("ownBpFundProject.ownJointMoney").setValue(value);
									} else {
										var last = value.substring(index + 1,value.length);
										if (last == 0) {
											temp = value.substring(0, index);
											temp = temp.replace(/,/g, "");
											if(Number(temp)>Number(money)){
												Ext.ux.Toast.msg('操作信息', '对接金额已超出贷款金额!');
												nf.setValue(Ext.util.Format.number(money, '0,000.00'));
												this.getOriginalContainer().getCmpByName("platFormBpFundProjectMoney").setValue(0);
											}else{
												this.getCmpByName("ownBpFundProject.ownJointMoney").setValue(temp);
												nf.setValue(Ext.util.Format.number(temp, '0,000.00'));
												this.getOriginalContainer().getCmpByName('platFormBpFundProjectMoney').setValue(Ext.util.Format.number(Number(money)-Number(temp), '0,000.00'))
												this.getOriginalContainer().getCmpByName('platFormBpFundProject.platFormJointMoney').setValue(Number(money)-Number(temp))
											}
										} else {
											temp = value.replace(/,/g, "");
											if(Number(temp)>Number(money)){
												Ext.ux.Toast.msg('操作信息', '对接金额已超出贷款金额!');
												nf.setValue(Ext.util.Format.number(money, '0,000.00'));
												this.getOriginalContainer().getCmpByName("platFormBpFundProjectMoney").setValue(0);
											}else{
												this.getCmpByName("ownBpFundProject.ownJointMoney").setValue(temp);
												nf.setValue(Ext.util.Format.number(temp, '0,000.00'));
												this.getOriginalContainer().getCmpByName('platFormBpFundProjectMoney').setValue(Ext.util.Format.number(Number(money)-Number(temp), '0,000.00'))
												this.getOriginalContainer().getCmpByName('platFormBpFundProject.platFormJointMoney').setValue(Number(money)-Number(temp))
											}
										}
									}
								}
								if(typeof(this.projectInfoFinance)!='undefined' && null!=this.projectInfoFinance){
									if(eval(temp)>0 && eval(temp)<=eval(money)){
										var taskSubmitObj=this.ownerCt.ownerCt.ownerCt.ownerCt.ownerCt.getCmpByName('taskSubmit')
										if(typeof(taskSubmitObj)!='undefined' && null!=taskSubmitObj){
											var taskItems=taskSubmitObj.items;
											for(var i=0;i<taskItems.length;i++){
												if(taskItems.get(i).boxLabel.indexOf('完成')!=-1){
													taskItems.get(i).setValue(false)
												}else{
													taskItems.get(i).setValue(true)
												}
											}
										}
									}else{
										var taskSubmitObj=this.ownerCt.ownerCt.ownerCt.ownerCt.ownerCt.getCmpByName('taskSubmit')
										if(typeof(taskSubmitObj)!='undefined' && null!=taskSubmitObj){
											var taskItems=taskSubmitObj.items;
											for(var i=0;i<taskItems.length;i++){
												if(taskItems.get(i).boxLabel.indexOf('完成')!=-1){
													taskItems.get(i).setValue(true)
												}else{
													taskItems.get(i).setValue(false)
												}
											}
										}
									}
									this.ownerCt.ownerCt.ownerCt.getCmpByName('platFormBpFundProjectMoney').setValue(Ext.util.Format.number(money-temp, '0,000.00'))
									this.ownerCt.ownerCt.ownerCt.getCmpByName('platFormBpFundProject.platFormJointMoney').setValue(money-temp)
								}
							}
						}
					}, {
						xtype : "hidden",
						name : "ownBpFundProject.ownJointMoney"
					}]
				}, {
					columnWidth : .04, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : 20,
					items : [{
								fieldLabel : "元 ",
								labelSeparator : '',
								anchor : "100%"
							}]
				}, {
					columnWidth : .27,
					layout : 'form',
					labelWidth : 50,
					defaults : {
						anchor : '96%'
					},
					items : [{
								fieldLabel : "币种",
								xtype : "dickeycombo",
								hiddenName : 'ownBpFundProject.currency',
								displayField : 'itemName',
								readOnly : this.isAllReadOnly,
								itemName : '注册资金币种', // xx代表分类名称
								allowBlank : false,
								editable : false,
								blankText : "币种不能为空，请正确填写!",
								nodeKey : 'capitalkind',
								emptyText : "请选择",
								value : 449,
								listeners : {
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
				}, {
					columnWidth : .36,
					layout : 'form',
					defaults : {
						anchor : '100%'
					},
					labelWidth:rightlabel,
					items : [{
						xtype:'dicIndepCombo',
						allowBlank : false,
						fieldLabel : '日期模型',
						readOnly : this.isAllReadOnly,
						anchor : '100%',
						editable : false,
						nodeKey : 'dateModel',
						lazyInit : true,
						lazyRender : true,
						hiddenName : 'ownBpFundProject.dateMode',
						listeners : {
							scope : this,
							afterrender :function(combox){
//								var st=com.getStore();
//								st.on('load',function(){
//									com.setValue(st.getAt(0).data.dicKey)
//									com.clearInvalid();
//								})
								var st = combox.getStore();
										st.on("load", function() {
													combox.setValue(combox
															.getValue());
													combox.clearInvalid();
												})
								
							},
							change : function(combo) {
						    	var yearAccrualRate = this.getCmpByName('ownBpFundProject.yearAccrualRate')
								var dayAccrualRaten = this.getCmpByName('ownBpFundProject.dayAccrualRate')
								var yearAccrualnm = this.getCmpByName('ownBpFundProject.yearManagementConsultingOfRate')
								var dayAccrualRatenm = this.getCmpByName('ownBpFundProject.dayManagementConsultingOfRate')
							    var yearAccrualnf = this.getCmpByName('ownBpFundProject.yearFinanceServiceOfRate')
								var dayAccrualRatenf = this.getCmpByName('ownBpFundProject.dayFinanceServiceOfRate')
								 if(combo.value == 'dateModel_365'){
								 	dayAccrualRaten.setValue(yearAccrualRate.getValue()/365);
								 	dayAccrualRatenm.setValue(yearAccrualnm.getValue()/365);
								 	dayAccrualRatenf.setValue(yearAccrualnf.getValue()/365);
								 }
								  if(combo.value == 'dateModel_360'){
								 	dayAccrualRaten.setValue(yearAccrualRate.getValue()/360);
								 	dayAccrualRatenm.setValue(yearAccrualnm.getValue()/360);
								 	dayAccrualRatenf.setValue(yearAccrualnf.getValue()/360);
								 }
							}
						}
					}]
				},{
				columnWidth : 1,
					layout:'column',
					items : [
					 {
					columnWidth : .1, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : 85,
					labelAlign :'right',
					items : [{
						 fieldLabel : "还款方式 ",
						 fieldClass : 'field-align',
						//html : "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;计息周期:",
						anchor : "100%"
					}]
				}, {
					columnWidth : 0.12,
					labelWidth : 1,
					layout : 'form',
					items : [{
						xtype : 'radio',
						boxLabel : '等额本金',
						// fieldLabel : "计息方式",
						name : 'f1'+this.idDefinition,
						id : "jixifs1" +this.idDefinition,
						inputValue : false,
						anchor : "100%",
						disabled : this.isAllReadOnly,
						listeners : {
							scope : this,
							check : function(obj, checked) {
								var flag = Ext.getCmp("jixifs1"+ this.idDefinition).getValue();
								if (flag == true && this.isAllReadOnly!=true) {
									this
											.getCmpByName('ownBpFundProject.accrualtype')
											.setValue("sameprincipal");
											  Ext.getCmp("jixizq1"+this.idDefinition).setDisabled(true);
								      Ext.getCmp("jixizq2"+ this.idDefinition).setDisabled(true);
								        Ext.getCmp("jixizq3"+ this.idDefinition).setDisabled(true);
								          Ext.getCmp("jixizq4"+ this.idDefinition).setDisabled(true);  
								          
								           Ext.getCmp("jixizq6"+ this.idDefinition).setDisabled(true);
								           this.getCmpByName('ownBpFundProject.dayOfEveryPeriod').setDisabled(true);
							 	           // this.getCmpByName('ownBpFundProject.dayOfEveryPeriod').setValue("");
							 	    
								              Ext.getCmp("jixizq2"+ this.idDefinition).setValue(true);
								             Ext.getCmp("jixizq1" +this.idDefinition).setValue(false);
								            this.getCmpByName('ownBpFundProject.payaccrualType').setValue("monthPay");
								            
								             this.getCmpByName('ownBpFundProject.payintentPeriod').setDisabled(false);
								         /*     this.getCmpByName('mqhkri1').hide();
							                   this.getCmpByName('mqhkri').show();*/
							                   Ext.getCmp("jixifs2"+this.idDefinition).setValue(false);
							                   Ext.getCmp("jixifs5"+this.idDefinition).setValue(false);
							                   Ext.getCmp("jixifs3"+this.idDefinition).setValue(false);
							                   Ext.getCmp("jixifs6"+this.idDefinition).setValue(false);
							                   Ext.getCmp("meiqihkrq1"+this.idDefinition).setDisabled(false);
							                   Ext.getCmp("meiqihkrq2"+this.idDefinition).setDisabled(false);
								}
							}
						}
					}]
				}, {
					columnWidth : 0.12,
					labelWidth : 1,
					layout : 'form',
					items : [{
						xtype : 'radio',
						boxLabel : '等额本息',
						anchor : "100%",
						name : 'f1'+this.idDefinition,
						id : "jixifs2" +this.idDefinition,
						inputValue : false,
						disabled : this.isAllReadOnly,
						listeners : {
							scope : this,
							check : function(obj, checked) {
								var flag = Ext.getCmp("jixifs2"+ this.idDefinition).getValue();
								if (flag == true && this.isAllReadOnly!=true) {
									this.getCmpByName('ownBpFundProject.accrualtype').setValue("sameprincipalandInterest");
								    Ext.getCmp("jixizq1"+ this.idDefinition).setDisabled(true);
								      Ext.getCmp("jixizq2"+ this.idDefinition).setDisabled(true);
								        Ext.getCmp("jixizq3"+ this.idDefinition).setDisabled(true);
								          Ext.getCmp("jixizq4"+ this.idDefinition).setDisabled(true);  
								           
								           Ext.getCmp("jixizq6"+ this.idDefinition).setDisabled(true);
								           this.getCmpByName('ownBpFundProject.dayOfEveryPeriod').setDisabled(true);
							 	           //this.getCmpByName('ownBpFundProject.dayOfEveryPeriod').setValue("");
								          
							 	           Ext.getCmp("jixizq2"+ this.idDefinition).setValue(true);
								             Ext.getCmp("jixizq1"+ this.idDefinition).setValue(false);
								            this.getCmpByName('ownBpFundProject.payaccrualType').setValue("monthPay");
								            
								             this.getCmpByName('ownBpFundProject.payintentPeriod').setDisabled(false);
								            /*  this.getCmpByName('mqhkri1').hide();
							                    this.getCmpByName('mqhkri').show();*/
							                     Ext.getCmp("jixifs1"+this.idDefinition).setValue(false);
							                   Ext.getCmp("jixifs5"+this.idDefinition).setValue(false);
							                   Ext.getCmp("jixifs3"+this.idDefinition).setValue(false);
							                   Ext.getCmp("jixifs6"+this.idDefinition).setValue(false);
							                   Ext.getCmp("meiqihkrq1"+this.idDefinition).setDisabled(false);
							                   Ext.getCmp("meiqihkrq2"+this.idDefinition).setDisabled(false);
								}
							}
						}
					}]
				}, {
					columnWidth : 0.12,
					labelWidth : 1,
					layout : 'form',
					items : [{
						xtype : 'radio',
						boxLabel : '等本等息',
						anchor : "100%",
						name : 'f1'+this.idDefinition,
						id : "jixifs5" +this.idDefinition,
						inputValue : false,
						disabled : this.isAllReadOnly,
						listeners : {
							scope : this,
							check : function(obj, checked) {
								var flag = Ext.getCmp("jixifs5"+ this.idDefinition).getValue();
								if (flag == true && this.isAllReadOnly!=true) {
									this.getCmpByName('ownBpFundProject.accrualtype').setValue("sameprincipalsameInterest");
									if(this.isAllReadOnly==true){
										Ext.getCmp("jixizq1"+ this.idDefinition).setDisabled(true);
								      Ext.getCmp("jixizq2"+ this.idDefinition).setDisabled(true);
								        Ext.getCmp("jixizq3"+ this.idDefinition).setDisabled(true);
								          Ext.getCmp("jixizq4"+ this.idDefinition).setDisabled(true);  
								           Ext.getCmp("jixizq6"+ this.idDefinition).setDisabled(true);
									}else{
								    Ext.getCmp("jixizq1"+ this.idDefinition).setDisabled(false);
								      Ext.getCmp("jixizq2"+ this.idDefinition).setDisabled(false);
								        Ext.getCmp("jixizq3"+ this.idDefinition).setDisabled(false);
								          Ext.getCmp("jixizq4"+ this.idDefinition).setDisabled(false);  
								           Ext.getCmp("jixizq6"+ this.idDefinition).setDisabled(false);
									}
								           this.getCmpByName('ownBpFundProject.dayOfEveryPeriod').setDisabled(true);
							 	          // this.getCmpByName('ownBpFundProject.dayOfEveryPeriod').setValue("");
								           // this.getCmpByName('ownBpFundProject.payaccrualType').setValue("monthPay");
								            
								             this.getCmpByName('ownBpFundProject.payintentPeriod').setDisabled(false);
								             this.getCmpByName('ownBpFundProject.payintentPerioDate').setDisabled(true);
							                     Ext.getCmp("jixifs2"+this.idDefinition).setValue(false);
							                   Ext.getCmp("jixifs1"+this.idDefinition).setValue(false);
							                   Ext.getCmp("jixifs3"+this.idDefinition).setValue(false);
							                   Ext.getCmp("jixifs6"+this.idDefinition).setValue(false);
							                   Ext.getCmp("meiqihkrq1"+this.idDefinition).setDisabled(true);
							                   Ext.getCmp("meiqihkrq1"+this.idDefinition).setValue(false);
							                   Ext.getCmp("meiqihkrq2"+this.idDefinition).setDisabled(true);
							                   Ext.getCmp("meiqihkrq2"+this.idDefinition).setValue(true)
							                   
							                    
								}
							}
						}
					}]
				}, {
					columnWidth : 0.12,
					labelWidth : 1,
					layout : 'form',
					items : [{
						xtype : 'radio',
						boxLabel : '按期收息,到期还本',
						name : 'f1'+this.idDefinition,
						id : "jixifs3" +this.idDefinition,
						inputValue : false,
						checked : true,
						anchor : "100%",
						disabled : this.isAllReadOnly,
						listeners : {
							scope : this,
							check : function(obj, checked) {
								var flag = Ext.getCmp("jixifs3"
										+ this.idDefinition).getValue();
								if (flag == true && this.isAllReadOnly!=true) {
									this.getCmpByName('ownBpFundProject.accrualtype').setValue("singleInterest");
									  Ext.getCmp("jixizq1"+ this.idDefinition).setDisabled(false);
								      Ext.getCmp("jixizq2"+ this.idDefinition).setDisabled(false);
								        Ext.getCmp("jixizq3"+ this.idDefinition).setDisabled(false);
								          Ext.getCmp("jixizq4"+ this.idDefinition).setDisabled(false);  
								           Ext.getCmp("jixizq6"+ this.idDefinition).setDisabled(false);
								           this.getCmpByName('ownBpFundProject.dayOfEveryPeriod').setDisabled(false);
								           Ext.getCmp("jixifs2"+this.idDefinition).setValue(false);
							                   Ext.getCmp("jixifs5"+this.idDefinition).setValue(false);
							                   Ext.getCmp("jixifs1"+this.idDefinition).setValue(false);
							                   Ext.getCmp("jixifs6"+this.idDefinition).setValue(false);
							                   Ext.getCmp("meiqihkrq1"+this.idDefinition).setDisabled(false);
							                   Ext.getCmp("meiqihkrq2"+this.idDefinition).setDisabled(false);
							               
								}
							}
						}
					}, {
						xtype : "hidden",
						name : "ownBpFundProject.accrualtype",
						value : "singleInterest"

					}]
				}, {
					columnWidth : 0.12,
					labelWidth : 1,
					layout : 'form',
					items : [{
						
						xtype : 'radio',
						boxLabel : '其他还款方式',
						anchor : "100%",
						name : 'f1'+this.idDefinition,
						id : "jixifs6" +this.idDefinition,
						inputValue : false,
						disabled : this.isAllReadOnly,
						listeners : {
							scope : this,
							check : function(obj, checked) {
								var flag = Ext.getCmp("jixifs6"+ this.idDefinition).getValue();
								if (flag == true && this.isAllReadOnly!=true) {
									this.getCmpByName('ownBpFundProject.accrualtype').setValue("otherMothod");
									if( this.isAllReadOnly==true){
										 Ext.getCmp("jixizq1"+ this.idDefinition).setDisabled(true);
								      Ext.getCmp("jixizq2"+ this.idDefinition).setDisabled(true);
								        Ext.getCmp("jixizq3"+ this.idDefinition).setDisabled(true);
								          Ext.getCmp("jixizq4"+ this.idDefinition).setDisabled(true);  
								           
								           Ext.getCmp("jixizq6"+ this.idDefinition).setDisabled(true);
									}else{
								    Ext.getCmp("jixizq1"+ this.idDefinition).setDisabled(false);
								      Ext.getCmp("jixizq2"+ this.idDefinition).setDisabled(false);
								        Ext.getCmp("jixizq3"+ this.idDefinition).setDisabled(false);
								          Ext.getCmp("jixizq4"+ this.idDefinition).setDisabled(false);  
								           
								           Ext.getCmp("jixizq6"+ this.idDefinition).setDisabled(false);
									}
								           this.getCmpByName('ownBpFundProject.dayOfEveryPeriod').setDisabled(true);
							 	          // this.getCmpByName('ownBpFundProject.dayOfEveryPeriod').setValue("");
								          
							 	           Ext.getCmp("jixizq2"+ this.idDefinition).setValue(true);
								             Ext.getCmp("jixizq1"+ this.idDefinition).setValue(false);
								            this.getCmpByName('ownBpFundProject.payaccrualType').setValue("monthPay");
								            
								             this.getCmpByName('ownBpFundProject.payintentPeriod').setDisabled(false);
								            /*  this.getCmpByName('mqhkri1').hide();
							                    this.getCmpByName('mqhkri').show();*/
							                     Ext.getCmp("jixifs2"+this.idDefinition).setValue(false);
							                   Ext.getCmp("jixifs5"+this.idDefinition).setValue(false);
							                   Ext.getCmp("jixifs3"+this.idDefinition).setValue(false);
							                   Ext.getCmp("jixifs1"+this.idDefinition).setValue(false);
							                   Ext.getCmp("meiqihkrq1"+this.idDefinition).setDisabled(false);
							                   Ext.getCmp("meiqihkrq2"+this.idDefinition).setDisabled(false);
								}
							}
						}
					
					}, {
						xtype : "hidden",
						name : "ownBpFundProject.payaccrualType",
						value : "monthPay"

					}]
				}]
				
				},{
					columnWidth : 1,
					layout:'column',
						items : [ {
						columnWidth : .1, // 该列在整行中所占的百分比
						layout : "form", // 从上往下的布局
						labelWidth : 85,
						labelAlign :'right',
						items : [{
							 fieldLabel : "还款周期 "
						}]
				}, {
					columnWidth : 0.12,
					labelWidth : 1,
					layout : 'form',
					items : [{
						xtype : 'radio',
						boxLabel : '日',
						name : 'z1'+this.idDefinition,
						id : "jixizq1" + this.idDefinition,
						inputValue : true,
						anchor : "100%",
						disabled : this.isAllReadOnly,
						listeners : {
							scope : this,
							check : function(obj, checked) {
								var flag = Ext.getCmp("jixizq1"	+ this.idDefinition).getValue();
								if (flag == true && this.isAllReadOnly!=true) {
									this.getCmpByName('ownBpFundProject.payaccrualType').setValue("dayPay");
									
									 Ext.getCmp("meiqihkrq1"+this.idDefinition).setDisabled(true);
									 Ext.getCmp("meiqihkrq1"+ this.idDefinition).setValue(false);
									 Ext.getCmp("meiqihkrq2"+ this.idDefinition).setDisabled(true);
									 Ext.getCmp("meiqihkrq2"+ this.idDefinition).setValue(true);
									Ext.getCmp("jixizq2"+this.idDefinition).setValue(false);
									 Ext.getCmp("jixizq3"+ this.idDefinition).setValue(false);
									 Ext.getCmp("jixizq4"+ this.idDefinition).setValue(false);
									 Ext.getCmp("jixizq6"+ this.idDefinition).setValue(false);
									 	this.getCmpByName('ownBpFundProject.isStartDatePay').setValue("2");
								var payAccrualType=this.getCmpByName('ownBpFundProject.payaccrualType').getValue();
										var dayOfEveryPeriod=this.getCmpByName('ownBpFundProject.dayOfEveryPeriod').getValue();
										var payintentPeriod=this.getCmpByName('ownBpFundProject.payintentPeriod').getValue();
										var startDate=this.getCmpByName('ownBpFundProject.startDate').getValue();
										var intentDatePanel=this.getCmpByName('ownBpFundProject.intentDate');
										setIntentDate(payAccrualType,dayOfEveryPeriod,payintentPeriod,startDate,intentDatePanel)
								     
								     
								}else {
								       
								       
								        Ext.getCmp("meiqihkrq1"+this.idDefinition).setDisabled(false);
									 Ext.getCmp("meiqihkrq2"+ this.idDefinition).setDisabled(false);
									 if(Ext.getCmp("meiqihkrq1"+ this.idDefinition).getValue()==true){
									  this.getCmpByName('ownBpFundProject.payintentPerioDate').setDisabled(false);
									 }
								       }
								 
							}
						}
					}]
				}, {
					columnWidth : 0.12,
					labelWidth : 1,
					layout : 'form',
					items : [{
						xtype : 'radio',
						boxLabel : '月',
						name : 'z1'+this.idDefinition,
						id : "jixizq2" +this.idDefinition,
						inputValue : false,
						checked : true,
						anchor : "100%",
						disabled : this.isAllReadOnly,
						listeners : {
							scope : this,
							check : function(obj, checked) {
								var flag = Ext.getCmp("jixizq2"
										+this.idDefinition).getValue();
								if (flag == true && this.isAllReadOnly!=true) {
									this.getCmpByName('ownBpFundProject.payaccrualType').setValue("monthPay");
									Ext.getCmp("jixizq1"+this.idDefinition).setValue(false);
									 Ext.getCmp("jixizq3"+ this.idDefinition).setValue(false);
									 Ext.getCmp("jixizq4"+ this.idDefinition).setValue(false);
									 Ext.getCmp("jixizq6"+ this.idDefinition).setValue(false);
									var payAccrualType=this.getCmpByName('ownBpFundProject.payaccrualType').getValue();
										var dayOfEveryPeriod=this.getCmpByName('ownBpFundProject.dayOfEveryPeriod').getValue();
										var payintentPeriod=this.getCmpByName('ownBpFundProject.payintentPeriod').getValue();
										var startDate=this.getCmpByName('ownBpFundProject.startDate').getValue();
										var intentDatePanel=this.getCmpByName('ownBpFundProject.intentDate');
										setIntentDate(payAccrualType,dayOfEveryPeriod,payintentPeriod,startDate,intentDatePanel)
								}
							}
						}
					}]
				}, {
					columnWidth : 0.12,
					labelWidth : 1,
					layout : 'form',
					items : [{
						xtype : 'radio',
						boxLabel : '季',
						name : 'z1'+this.idDefinition,
						id : "jixizq3" + this.idDefinition,
						inputValue : false,
						anchor : "100%",
						disabled : this.isAllReadOnly,
						listeners : {
							scope : this,
							check : function(obj, checked) {
								var flag = Ext.getCmp("jixizq3"
										+this.idDefinition).getValue();
								if (flag == true && this.isAllReadOnly!=true) {
									this.getCmpByName('ownBpFundProject.payaccrualType').setValue("seasonPay");
									Ext.getCmp("jixizq1"+this.idDefinition).setValue(false);
									 Ext.getCmp("jixizq2"+ this.idDefinition).setValue(false);
									 Ext.getCmp("jixizq4"+ this.idDefinition).setValue(false);
									 Ext.getCmp("jixizq6"+ this.idDefinition).setValue(false);
								var payAccrualType=this.getCmpByName('ownBpFundProject.payaccrualType').getValue();
										var dayOfEveryPeriod=this.getCmpByName('ownBpFundProject.dayOfEveryPeriod').getValue();
										var payintentPeriod=this.getCmpByName('ownBpFundProject.payintentPeriod').getValue();
										var startDate=this.getCmpByName('ownBpFundProject.startDate').getValue();
										var intentDatePanel=this.getCmpByName('ownBpFundProject.intentDate');
										setIntentDate(payAccrualType,dayOfEveryPeriod,payintentPeriod,startDate,intentDatePanel)
								}
							}
						}
					}]
				}, {
					columnWidth : 0.12,
					labelWidth : 1,
					layout : 'form',
					items : [{
						xtype : 'radio',
						boxLabel : '年',
						name : 'z1'+this.idDefinition,
						id : "jixizq4" + this.idDefinition,
						inputValue : false,
						anchor : "100%",
						disabled : this.isAllReadOnly,
						listeners : {
							scope : this,
							check : function(obj, checked) {
								var flag = Ext.getCmp("jixizq4"
										+ this.idDefinition).getValue();
								if (flag == true && this.isAllReadOnly!=true) {
									this.getCmpByName('ownBpFundProject.payaccrualType').setValue("yearPay");
									Ext.getCmp("jixizq1"+this.idDefinition).setValue(false);
									 Ext.getCmp("jixizq3"+ this.idDefinition).setValue(false);
									 Ext.getCmp("jixizq2"+ this.idDefinition).setValue(false);
									 Ext.getCmp("jixizq6"+ this.idDefinition).setValue(false);
									var payAccrualType=this.getCmpByName('ownBpFundProject.payaccrualType').getValue();
										var dayOfEveryPeriod=this.getCmpByName('ownBpFundProject.dayOfEveryPeriod').getValue();
										var payintentPeriod=this.getCmpByName('ownBpFundProject.payintentPeriod').getValue();
										var startDate=this.getCmpByName('ownBpFundProject.startDate').getValue();
										var intentDatePanel=this.getCmpByName('ownBpFundProject.intentDate');
										setIntentDate(payAccrualType,dayOfEveryPeriod,payintentPeriod,startDate,intentDatePanel)
								}
							}
						}
					}]
				}, {
					columnWidth : .098,
					labelWidth :1,
					layout : 'form',
					items : [{
						xtype : 'radio',
						boxLabel : '自定义周期',
						name : 'z1'+this.idDefinition,
						id : "jixizq6" + this.idDefinition,
						inputValue : false,
						anchor : "100%",
						disabled : this.isAllReadOnly,
						listeners : {
							scope : this,
							check : function(obj, checked) {
								var flag = Ext.getCmp("jixizq6"
										+ this.idDefinition).getValue();
								if (flag == true && this.isAllReadOnly!=true) {
									this.getCmpByName('ownBpFundProject.payaccrualType').setValue("owerPay");
									
								   this.getCmpByName('ownBpFundProject.dayOfEveryPeriod').setDisabled(false);
								   
								    Ext.getCmp("meiqihkrq1"+this.idDefinition).setDisabled(true);
									 Ext.getCmp("meiqihkrq1"+ this.idDefinition).setValue(false);
									 Ext.getCmp("meiqihkrq2"+ this.idDefinition).setDisabled(true);
									 Ext.getCmp("meiqihkrq2"+ this.idDefinition).setValue(true);
									 Ext.getCmp("jixizq1"+this.idDefinition).setValue(false);
									 Ext.getCmp("jixizq3"+ this.idDefinition).setValue(false);
									 Ext.getCmp("jixizq4"+ this.idDefinition).setValue(false);
									 Ext.getCmp("jixizq2"+ this.idDefinition).setValue(false);
									 
								}else{
								 this.getCmpByName('ownBpFundProject.dayOfEveryPeriod').setDisabled(true);
							 	   // this.getCmpByName('ownBpFundProject.dayOfEveryPeriod').setValue("");
							 	    if(Ext.getCmp("jixizq1"+ this.idDefinition).getValue()==false){
								 	     Ext.getCmp("meiqihkrq1"+this.idDefinition).setDisabled(false);
										 Ext.getCmp("meiqihkrq2"+ this.idDefinition).setDisabled(false);
										 if(Ext.getCmp("meiqihkrq1"+ this.idDefinition).getValue()==true){
										  this.getCmpByName('ownBpFundProject.payintentPerioDate').setDisabled(false);
										 }
									  }
								    
								}
							}
						}
					}]
				}, {
					columnWidth : 0.06,
					labelWidth : 1,
					layout : 'form',
					items : [{
					 xtype:'textfield',
					 anchor : "100%",
				 	readOnly : this.isAllReadOnly,
				 	fieldClass : 'field-align',
					 name:'ownBpFundProject.dayOfEveryPeriod',
					 listeners : {
					 	scope : this,
					 	'change' : function(){
					 		var payAccrualType=this.getCmpByName('ownBpFundProject.payaccrualType').getValue();
							var dayOfEveryPeriod=this.getCmpByName('ownBpFundProject.dayOfEveryPeriod').getValue();
							var payintentPeriod=this.getCmpByName('ownBpFundProject.payintentPeriod').getValue();
							var startDate=this.getCmpByName('ownBpFundProject.startDate').getValue();
							var intentDatePanel=this.getCmpByName('ownBpFundProject.intentDate');
							setIntentDate(payAccrualType,dayOfEveryPeriod,payintentPeriod,startDate,intentDatePanel)
					 	}
					 }
					}
					]}, {
						columnWidth : 0.07,
						labelWidth :40,
						layout : 'form',
						items : [{
						fieldLabel : "日/期",
							labelSeparator : '',
							anchor : "100%"
								}]}
					
					]}, {
					columnWidth : 1,
					layout:'column',
					items:[{
						columnWidth : .3, // 该列在整行中所占的百分比
						layout : "form", // 从上往下的布局
						labelWidth : leftlabel,
						items : [{
								fieldLabel : "贷款期限",
								xtype : "textfield",
								fieldClass : 'field-align',
								allowBlank : false,
								readOnly : this.isAllReadOnly,
								name : "ownBpFundProject.payintentPeriod",
								anchor : "100%",
								listeners : {
								 	scope : this,
								 	'change' : function(){
								 		var payAccrualType=this.getCmpByName('ownBpFundProject.payaccrualType').getValue();
										var dayOfEveryPeriod=this.getCmpByName('ownBpFundProject.dayOfEveryPeriod').getValue();
										var payintentPeriod=this.getCmpByName('ownBpFundProject.payintentPeriod').getValue();
										var startDate=this.getCmpByName('ownBpFundProject.startDate').getValue();
										var intentDatePanel=this.getCmpByName('ownBpFundProject.intentDate');
										setIntentDate(payAccrualType,dayOfEveryPeriod,payintentPeriod,startDate,intentDatePanel)
								 	}
								 }

							}]
				}, {
					columnWidth : .03, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : 20,
					items : [{
								fieldLabel : "期",
								labelSeparator : '',
								anchor : "100%"
							}]
				},{
					columnWidth : .28,
					layout : 'form',
					labelWidth : 60,
					defaults : {
						anchor : '96%'
					},
					items : [{
						fieldLabel : "放款日期",
						xtype : (this.changeType==null||this.changeType==false)?"datefield":'hidden',
						style : {
							imeMode : 'disabled'
						},
						name : "ownBpFundProject.startDate",
						allowBlank : false,
						readOnly : this.isStartDateReadOnly,
						blankText : "放款日期不能为空，请正确填写!",
						format : 'Y-m-d',
						listeners : {
							scope : this,
							'change' : function(nf){
								var payAccrualType=this.getCmpByName('ownBpFundProject.payaccrualType').getValue();
								var dayOfEveryPeriod=this.getCmpByName('ownBpFundProject.dayOfEveryPeriod').getValue();
								var payintentPeriod=this.getCmpByName('ownBpFundProject.payintentPeriod').getValue();
								var startDate=this.getCmpByName('ownBpFundProject.startDate').getValue();
								var intentDatePanel=this.getCmpByName('ownBpFundProject.intentDate');
								setIntentDate(payAccrualType,dayOfEveryPeriod,payintentPeriod,startDate,intentDatePanel)
							}
						}
					},{
						fieldLabel :"起息日期",
						xtype : (this.changeType==null||this.changeType==false)?"hidden":"datefield",
						style : {
							imeMode : 'disabled'
						},
						name : "ownBpFundProject.startInterestDate",
						allowBlank : false,
						//hidden:(this.changeType==null||this.changeType==false)?true:false,
						readOnly : this.isStartDateReadOnly,
						blankText : "放款日期不能为空，请正确填写!",
						format : 'Y-m-d',
						listeners : {
							scope : this,
							'change' : function(nf,nv,ov){
								var projectStartDate = this.getCmpByName("projectStartDate");
								var intentDate = this.getCmpByName("ownBpFundProject.intentDate");
								if(projectStartDate){
									if(null!=nv){
										if(Ext.util.Format.date(nv, "Y-m-d")<projectStartDate.getValue()){
											Ext.ux.Toast.msg('错误', '起息日起不能再放款日期之前!');
											nf.setValue(ov);
											return;
										}
										if(intentDate){
											if(Ext.util.Format.date(nv, "Y-m-d")>Ext.util.Format.date(intentDate.getValue(), "Y-m-d")){
											Ext.ux.Toast.msg('错误', '起息日起不能再还款日期日期之后!');
											nf.setValue(ov);
											return;
										}
										}
										
									}
								}
								var startDate=this.getCmpByName('ownBpFundProject.startDate');
								startDate.setValue(nv);
								var payAccrualType=this.getCmpByName('ownBpFundProject.payaccrualType').getValue();
								var dayOfEveryPeriod=this.getCmpByName('ownBpFundProject.dayOfEveryPeriod').getValue();
								var payintentPeriod=this.getCmpByName('ownBpFundProject.payintentPeriod').getValue();
								if(this.getCmpByName('ownBpFundProject.startDate')){
									var startInterestDate=this.getCmpByName('ownBpFundProject.startInterestDate').getValue();
									var intentDatePanel=this.getCmpByName('ownBpFundProject.intentDate');
									if(this.isOwnBpFundProject){
										var gudingzai = this.getCmpByName( 'q1'+ this.idDefinition);
									}else{
										setIntentDate(payAccrualType,dayOfEveryPeriod,payintentPeriod,startInterestDate,intentDatePanel)
									}
								}
							}
						}
					},{
						xtype:'hidden',
						name:'projectStartDate',
						hidden:true
					}]
				},{
					columnWidth : .36,
					layout : 'form',
					labelWidth:rightlabel,
					items : [{
						fieldLabel : "还款日期",
						xtype : "datefield",
						name : "ownBpFundProject.intentDate",
						allowBlank : false,
						readOnly : true,
						blankText : "还款日期不能为空，请正确填写!",
						anchor : "100%",
						format : 'Y-m-d'
					}]
				}]
				},{
					columnWidth : 1,
					layout : 'column',
					items : [{
						columnWidth : .1,
						layout : 'form',
						labelWidth : 85,
						labelAlign :'right',
						items : [{
							fieldLabel : '还款选项'
						}]
					},{
						columnWidth : .12, // 该列在整行中所占的百分比
						layout : "form", // 从上往下的布局
						labelWidth : 1,
						items : [{
							xtype : "checkbox",
							boxLabel : "前置付息",
							disabled : this.isAllReadOnly,
							anchor : "100%",
							name : "isPreposePayAccrualCheck",
							// value :true
							checked : this.record == null
									|| this.record.data.isPreposePayAccrual== 0
									? null
									: true,
							listeners : {
								scope : this,
								'check' : function(box,value){
									if(value==true){
										this.getCmpByName('ownBpFundProject.isPreposePayAccrual').setValue(1);
									}else{
										this.getCmpByName('ownBpFundProject.isPreposePayAccrual').setValue(0);
									}
								}
							}
						},{
							xtype :'hidden',
							name : 'ownBpFundProject.isPreposePayAccrual',
							value:0
						}]
					},{
						columnWidth : .1, // 该列在整行中所占的百分比
						layout : "form", // 从上往下的布局
						labelWidth : 1,
						items : [{
							xtype : "checkbox",
							boxLabel : "一次性支付利息",
							disabled : this.isAllReadOnly,
							anchor : "100%",
							name : "isInterestByOneTimeCheck",
							// value :true
							checked : this.record == null
									|| this.record.data.isInterestByOneTime== 0
									? null
									: true,
							listeners : {
								scope : this,
								'check' : function(box,value){
									if(value==true){
										this.getCmpByName('ownBpFundProject.isInterestByOneTime').setValue(1);
									}else{
										this.getCmpByName('ownBpFundProject.isInterestByOneTime').setValue(0);
									}
								}
							}
						},{
							xtype :'hidden',
							name : 'ownBpFundProject.isInterestByOneTime',
							value:0
						}]
					},{
					columnWidth : .5, 
					name :"mqhkri",
					layout : "column", 
					items : [ {
							columnWidth : 0.255,
							labelWidth : 76,
							layout : 'form',
							items : [{
										xtype : 'radio',
										fieldLabel : '每期还款日',
										boxLabel : '固定在',
										name : 'q1'+ this.idDefinition,
										id : "meiqihkrq1" + this.idDefinition,
										inputValue : true,
										anchor : "100%",
										disabled : this.isAllReadOnly,
										listeners : {
										scope : this,
										check : function(obj, checked) {
											var flag = Ext.getCmp("meiqihkrq1"+ this.idDefinition).getValue();
											if (flag == true) {
												this.getCmpByName('ownBpFundProject.isStartDatePay').setValue("1");
												this.getCmpByName('ownBpFundProject.payintentPerioDate').setDisabled(false);
											}
										}
									}

									}, {
										xtype : "hidden",
										name : "ownBpFundProject.isStartDatePay"

									}]
						}, {
							columnWidth : 0.3,
							labelWidth : 1,
							layout : 'form',
							items : [{
										xtype : 'textfield',
										readOnly : this.isAllReadOnly,
										name : "ownBpFundProject.payintentPerioDate",
										xtype : 'combo',
										mode : 'local',
										disabled :true,
										displayField : 'name',
										valueField : 'id',
										editable : true,
										store : new Ext.data.SimpleStore({
													fields : ["name", "id"],
													data : obstorepayintentPeriod
												}),
										triggerAction : "all",
										hiddenName : "ownBpFundProject.payintentPerioDate",
										fieldLabel : "",
										anchor : '100%'
									}]
						}, {
							columnWidth : .08,
							labelWidth :45,
							layout : 'form',
							items : [{
							fieldLabel : "号还款",
								labelSeparator : '',
								anchor : "100%"
									}]
						}, {
							columnWidth : 0.25,
							labelWidth : 10,
							layout : 'form',
							items : [{
										xtype : 'radio',
										boxLabel : '按实际放款日对日还款',
										name : 'q1'+ this.idDefinition,
										id : "meiqihkrq2" + this.idDefinition,
										inputValue : true,
										checked : true,
										anchor : "100%",
										disabled : this.isAllReadOnly,
										listeners : {
										scope : this,
										check : function(obj, checked) {
											var flag = Ext.getCmp("meiqihkrq2"
													+ this.idDefinition).getValue();
											if (flag == true) {
												this.getCmpByName('ownBpFundProject.isStartDatePay').setValue("2");
												this.getCmpByName('ownBpFundProject.payintentPerioDate').setValue(null);
														this.getCmpByName('ownBpFundProject.payintentPerioDate').setDisabled(true);
											}
										}
									}
							}]
						}]
					}]
				},{
					columnWidth : 1,
					layout : 'column',
					items : [{
						columnWidth :.08,
						layout : 'form',
						labelWidth : 85,
						labelAlign : 'right',
						items : [{
							fieldLabel : '贷款利率',
							allowBlank : false
						}]
					},{
						columnWidth : .06,
						layout : 'form',
						labelWidth : 60,
						labelAlign : 'right',
						items : [{
							fieldLabel : "年化利率",
							labelSeparator : ''
						}]
					},{
						columnWidth : .16,
						layout : 'form',
						items : [{
							hideLabel :true,
							xtype : 'numberfield',
							name : "ownBpFundProject.yearAccrualRate",
							readOnly : this.isReadOnly,
							fieldClass : 'field-align',
							decimalPrecision : 8,
							anchor : '100%',
							style : {
								imeMode : 'disabled'
							},
							value :0,
							listeners : {
								scope : this,
								'change' : function(nf){
									var dateModel=this.getCmpByName('ownBpFundProject.dateMode').getValue()
									var accrualnf=this.getCmpByName('ownBpFundProject.accrual')
									var dayAccrualRatenf=this.getCmpByName('ownBpFundProject.dayAccrualRate')
									var sumAccrualRatenf=this.getCmpByName('ownBpFundProject.sumAccrualRate')
									var startDate=this.getCmpByName('ownBpFundProject.startDate').getValue()
									var intentDate=this.getCmpByName('ownBpFundProject.intentDate').getValue()
									accrualnf.setValue((nf.getValue()/(12*10))*10)
									if(null!=dateModel && dateModel=='dateModel_360'){
										dayAccrualRatenf.setValue(nf.getValue()/360)
										this.getCmpByName('ownBpFundProject.accrualnew').setValue(nf.getValue()/360)
										if(startDate!='' && intentDate!=''){
											intentDate=Ext.util.Format.date(intentDate,'Y-m-d')
											startDate=Ext.util.Format.date(startDate,'Y-m-d')
											var days=((new Date(intentDate.substring(0,4),intentDate.substring(5,intentDate.lastIndexOf('-'))-1,intentDate.substring(intentDate.lastIndexOf('-')+1,intentDate.length))).getTime()-(new Date(startDate.substring(0,4),startDate.substring(5,startDate.lastIndexOf('-'))-1,startDate.substring(startDate.lastIndexOf('-')+1,startDate.length))).getTime())/1000/60/60/24
											sumAccrualRatenf.setValue(nf.getValue()/360*days)
										}
									}else if(null!=dateModel && dateModel=='dateModel_365'){
										dayAccrualRatenf.setValue(nf.getValue()/365)
										this.getCmpByName('ownBpFundProject.accrualnew').setValue(nf.getValue()/365)
										if(startDate!='' && intentDate!=''){
											intentDate=Ext.util.Format.date(intentDate,'Y-m-d')
											startDate=Ext.util.Format.date(startDate,'Y-m-d')
											var days=((new Date(intentDate.substring(0,4),intentDate.substring(5,intentDate.lastIndexOf('-'))-1,intentDate.substring(intentDate.lastIndexOf('-')+1,intentDate.length))).getTime()-(new Date(startDate.substring(0,4),startDate.substring(5,startDate.lastIndexOf('-'))-1,startDate.substring(startDate.lastIndexOf('-')+1,startDate.length))).getTime())/1000/60/60/24
											sumAccrualRatenf.setValue(nf.getValue()/365*days)
										}
									}
								}
							}
						}]
					},{
						columnWidth : .03,
						layout : 'form',
						labelWidth : 20,
						labelAlign : 'left',
						items : [{
							fieldLabel : "%",
							labelSeparator : '',
							anchor : "100%"
						}]
					},{
						columnWidth : .06,
						layout : 'form',
						labelWidth : 60,
						labelAlgin : 'right',
						items : [{
							fieldLabel : "月化利率:",
							labelSeparator : ''
						}]
					},{
						columnWidth : .06,
						layout : 'form',
						items : [{
							hideLabel :true,
							xtype : 'numberfield',
							name : "ownBpFundProject.accrual",
							readOnly : this.isReadOnly,
							fieldClass : 'field-align',
							style : {
								imeMode : 'disabled'
							},
							anchor : '100%',
							decimalPrecision : 8,
							value :0,
							listeners : {
								scope : this,
								'change' : function(nf){
									var dateModel=this.getCmpByName('ownBpFundProject.dateMode').getValue()
									var yearAccrualRatenf=this.getCmpByName('ownBpFundProject.yearAccrualRate')
									var dayAccrualRatenf=this.getCmpByName('ownBpFundProject.dayAccrualRate')
									var sumAccrualRatenf=this.getCmpByName('ownBpFundProject.sumAccrualRate')
									var startDate=this.getCmpByName('ownBpFundProject.startDate').getValue()
									var intentDate=this.getCmpByName('ownBpFundProject.intentDate').getValue()
									yearAccrualRatenf.setValue(nf.getValue()*12)
									if(null!=dateModel && dateModel=='dateModel_360'){
									  dayAccrualRatenf.setValue(nf.getValue()/30)
									   if(startDate!='' && intentDate!=''){
										    intentDate=Ext.util.Format.date(intentDate,'Y-m-d')
											startDate=Ext.util.Format.date(startDate,'Y-m-d')
											var days=((new Date(intentDate.substring(0,4),intentDate.substring(5,intentDate.lastIndexOf('-'))-1,intentDate.substring(intentDate.lastIndexOf('-')+1,intentDate.length))).getTime()-(new Date(startDate.substring(0,4),startDate.substring(5,startDate.lastIndexOf('-'))-1,startDate.substring(startDate.lastIndexOf('-')+1,startDate.length))).getTime())/1000/60/60/24
										     sumAccrualRatenf.setValue(nf.getValue()/30*days)
									    }
									    this.getCmpByName('ownBpFundProject.accrualnew').setValue(nf.getValue()/30)
									}
							       else if(null!=dateModel && dateModel=='dateModel_365'){
									  dayAccrualRatenf.setValue(nf.getValue()*12/365)
									   if(startDate!='' && intentDate!=''){
										    intentDate=Ext.util.Format.date(intentDate,'Y-m-d')
											startDate=Ext.util.Format.date(startDate,'Y-m-d')
											var days=((new Date(intentDate.substring(0,4),intentDate.substring(5,intentDate.lastIndexOf('-'))-1,intentDate.substring(intentDate.lastIndexOf('-')+1,intentDate.length))).getTime()-(new Date(startDate.substring(0,4),startDate.substring(5,startDate.lastIndexOf('-'))-1,startDate.substring(startDate.lastIndexOf('-')+1,startDate.length))).getTime())/1000/60/60/24
										     sumAccrualRatenf.setValue(nf.getValue()*12/365*days)
									    }
									    this.getCmpByName('ownBpFundProject.accrualnew').setValue(nf.getValue()*12/365);
									}
								}
							}
							
						}]
					},{
						columnWidth : .03,
						layout : 'form',
						labelWidth : 20,
						labelAlgin : 'left',
						items : [{
							fieldLabel : "%",
							labelSeparator : '',
							anchor : "100%"
						}]
					},{
						columnWidth : .06,
						layout : 'form',
						labelWidth : 60,
						labelAlgin : 'right',
						items : [{
							fieldLabel : "日化利率",
							labelSeparator : ''
						}]
					},{
						columnWidth : .06,
						layout : 'form',
						items : [{
							hideLabel :true,
							xtype : 'numberfield',
							name : "ownBpFundProject.dayAccrualRate",
							readOnly : this.isReadOnly,
							fieldClass : 'field-align',
							anchor : '100%',
							decimalPrecision : 8,
							style : {
								imeMode : 'disabled'
							},
							value :0,
							listeners : {
								scope : this,
								'change' : function(nf){
									var dateModel=this.getCmpByName('ownBpFundProject.dateMode').getValue()
									var accrualnf=this.getCmpByName('ownBpFundProject.accrual')
									var yearAccrualRatenf=this.getCmpByName('ownBpFundProject.yearAccrualRate')
									var sumAccrualRatenf=this.getCmpByName('ownBpFundProject.sumAccrualRate')
									var startDate=this.getCmpByName('ownBpFundProject.startDate').getValue()
									var intentDate=this.getCmpByName('ownBpFundProject.intentDate').getValue()
									this.getCmpByName('ownBpFundProject.accrualnew').setValue(nf.getValue())
									if(null!=dateModel && dateModel=='dateModel_360'){
										yearAccrualRatenf.setValue(nf.getValue()*360);
										accrualnf.setValue(nf.getValue()*30)
										
									}else if(null!=dateModel && dateModel=='dateModel_365'){
										yearAccrualRatenf.setValue(nf.getValue()*365)
										accrualnf.setValue(nf.getValue()*365/12)
									}
									if(startDate!='' && intentDate!=''){
										intentDate=Ext.util.Format.date(intentDate,'Y-m-d')
											startDate=Ext.util.Format.date(startDate,'Y-m-d')
											var days=((new Date(intentDate.substring(0,4),intentDate.substring(5,intentDate.lastIndexOf('-'))-1,intentDate.substring(intentDate.lastIndexOf('-')+1,intentDate.length))).getTime()-(new Date(startDate.substring(0,4),startDate.substring(5,startDate.lastIndexOf('-'))-1,startDate.substring(startDate.lastIndexOf('-')+1,startDate.length))).getTime())/1000/60/60/24
										sumAccrualRatenf.setValue(nf.getValue()*days)
									}
								}
							}
						},{
						 	xtype : 'hidden',
						 	name : 'ownBpFundProject.accrualnew',
						 	value : 0
						}]
					},{
						columnWidth : .045,
						layout : 'form',
						labelWidth : 20,
						labelAlgin : 'left',
						items : [{
							fieldLabel : "%",
							labelSeparator : '',
							anchor : "100%"
						}]
					},{
						columnWidth : .06,
						layout : 'form',
						labelWidth : 60,
						labelAlgin : 'right',
						items : [{
							fieldLabel : "合计利率",
							labelSeparator : ''
						}]
					},{
						columnWidth : .27,
						layout : 'form',
						items : [{
							hideLabel :true,
							xtype : 'numberfield',
							name : "ownBpFundProject.sumAccrualRate",
							readOnly : this.isReadOnly,
							anchor : '100%',
							decimalPrecision : 8,
							fieldClass : 'field-align',
							style : {
								imeMode : 'disabled'
							},
							value :0,
							listeners : {
								scope : this,
								'change' : function(nf){
									var dateModel=this.getCmpByName('ownBpFundProject.dateMode').getValue()
									var accrualnf=this.getCmpByName('ownBpFundProject.accrual')
									var yearAccrualRatenf=this.getCmpByName('ownBpFundProject.yearAccrualRate')
									var dayAccrualRatenf=this.getCmpByName('ownBpFundProject.dayAccrualRate')
									var startDate=this.getCmpByName('ownBpFundProject.startDate').getValue()
									var intentDate=this.getCmpByName('ownBpFundProject.intentDate').getValue()
									
									if(startDate!='' && intentDate!=''){
										intentDate=Ext.util.Format.date(intentDate,'Y-m-d')
											startDate=Ext.util.Format.date(startDate,'Y-m-d')
											var days=((new Date(intentDate.substring(0,4),intentDate.substring(5,intentDate.lastIndexOf('-'))-1,intentDate.substring(intentDate.lastIndexOf('-')+1,intentDate.length))).getTime()-(new Date(startDate.substring(0,4),startDate.substring(5,startDate.lastIndexOf('-'))-1,startDate.substring(startDate.lastIndexOf('-')+1,startDate.length))).getTime())/1000/60/60/24
										var rate=nf.getValue()/days
										dayAccrualRatenf.setValue(rate);
										if(null!=dateModel && dateModel=='dateModel_360'){
											yearAccrualRatenf.setValue(rate*360)
											accrualnf.setValue(rate*30)
										
										}else if(null!=dateModel && dateModel=='dateModel_365'){
											yearAccrualRatenf.setValue(rate*365)
											accrualnf.setValue(rate*365/12)
										}
										
										this.getCmpByName('ownBpFundProject.accrualnew').setValue(rate)
									}
								}
							}
						}]
					},{
						columnWidth : .02,
						layout : 'form',
						labelWidth : 20,
						labelAlgin : 'left',
						items : [{
							fieldLabel : "%",
							labelSeparator : '',
							anchor : "100%"
						}]
					}]
				},{
					columnWidth : 1,
					layout : 'column',
					defaults : {
						hidden:this.isHideConsultRate
					},
					items : [{
						columnWidth :.08,
						layout : 'form',
						labelWidth : 85,
						labelAlign : 'right',
						items : [{
							fieldLabel : '管理咨询费率',
							allowBlank :false
						}]
					},{
						columnWidth : .06,
						layout : 'form',
						labelWidth : 60,
						labelAlign : 'right',
						items : [{
							fieldLabel : "年化利率",
							labelSeparator : ''
						}]
					},{
						columnWidth : .16,
						layout : 'form',
						items : [{
							hideLabel :true,
							xtype : 'numberfield',
							name : "ownBpFundProject.yearManagementConsultingOfRate",
							readOnly : this.isReadOnly,
							fieldClass : 'field-align',
							anchor : '100%',
							decimalPrecision : 8,
							style : {
								imeMode : 'disabled'
							},
							value :0,
							listeners : {
								scope : this,
								'change' : function(nf){
									var dateModel=this.getCmpByName('ownBpFundProject.dateMode').getValue()
									var accrualnf=this.getCmpByName('ownBpFundProject.managementConsultingOfRate')
									var dayAccrualRatenf=this.getCmpByName('ownBpFundProject.dayManagementConsultingOfRate')
									var sumAccrualRatenf=this.getCmpByName('ownBpFundProject.sumManagementConsultingOfRate')
									var startDate=this.getCmpByName('ownBpFundProject.startDate').getValue()
									var intentDate=this.getCmpByName('ownBpFundProject.intentDate').getValue()
									accrualnf.setValue((nf.getValue()/(12*10))*10)
									if(null!=dateModel && dateModel=='dateModel_360'){
										dayAccrualRatenf.setValue(nf.getValue()/360)
										if(startDate!='' && intentDate!=''){
											intentDate=Ext.util.Format.date(intentDate,'Y-m-d')
											startDate=Ext.util.Format.date(startDate,'Y-m-d')
											var days=((new Date(intentDate.substring(0,4),intentDate.substring(5,intentDate.lastIndexOf('-'))-1,intentDate.substring(intentDate.lastIndexOf('-')+1,intentDate.length))).getTime()-(new Date(startDate.substring(0,4),startDate.substring(5,startDate.lastIndexOf('-'))-1,startDate.substring(startDate.lastIndexOf('-')+1,startDate.length))).getTime())/1000/60/60/24
											sumAccrualRatenf.setValue(nf.getValue()/360*days)
										}
									}else if(null!=dateModel && dateModel=='dateModel_365'){
										dayAccrualRatenf.setValue(nf.getValue()/365)
										if(startDate!='' && intentDate!=''){
											intentDate=Ext.util.Format.date(intentDate,'Y-m-d')
											startDate=Ext.util.Format.date(startDate,'Y-m-d')
											var days=((new Date(intentDate.substring(0,4),intentDate.substring(5,intentDate.lastIndexOf('-'))-1,intentDate.substring(intentDate.lastIndexOf('-')+1,intentDate.length))).getTime()-(new Date(startDate.substring(0,4),startDate.substring(5,startDate.lastIndexOf('-'))-1,startDate.substring(startDate.lastIndexOf('-')+1,startDate.length))).getTime())/1000/60/60/24
											sumAccrualRatenf.setValue(nf.getValue()/365*days)
										}
									}
								}
							}
						}]
					},{
						columnWidth : .03,
						layout : 'form',
						labelWidth : 20,
						labelAlgin : 'left',
						items : [{
							fieldLabel : "%",
							labelSeparator : '',
							anchor : "100%"
						}]
					},{
						columnWidth : .06,
						layout : 'form',
						labelWidth : 60,
						labelAlign : 'right',
						items : [{
							fieldLabel : "月化利率",
							labelSeparator : ''
						}]
					},{
						columnWidth : .06,
						layout : 'form',
						items : [{
							hideLabel :true,
							xtype : 'numberfield',
							name : "ownBpFundProject.managementConsultingOfRate",
							readOnly : this.isReadOnly,
							fieldClass : 'field-align',
							anchor : '100%',
							decimalPrecision : 8,
							style : {
								imeMode : 'disabled'
							},
							value :0,
							listeners : {
								scope : this,
								'change' : function(nf){
									var dateModel=this.getCmpByName('ownBpFundProject.dateMode').getValue()
									var yearAccrualRatenf=this.getCmpByName('ownBpFundProject.yearManagementConsultingOfRate')
									var dayAccrualRatenf=this.getCmpByName('ownBpFundProject.dayManagementConsultingOfRate')
									var sumAccrualRatenf=this.getCmpByName('ownBpFundProject.sumManagementConsultingOfRate')
									var startDate=this.getCmpByName('ownBpFundProject.startDate').getValue()
									var intentDate=this.getCmpByName('ownBpFundProject.intentDate').getValue()
									yearAccrualRatenf.setValue(nf.getValue()*12)
									
									if(null!=dateModel && dateModel=='dateModel_360'){
										dayAccrualRatenf.setValue(nf.getValue()/30)
									 	if(startDate!='' && intentDate!=''){
										intentDate=Ext.util.Format.date(intentDate,'Y-m-d')
											startDate=Ext.util.Format.date(startDate,'Y-m-d')
											var days=((new Date(intentDate.substring(0,4),intentDate.substring(5,intentDate.lastIndexOf('-'))-1,intentDate.substring(intentDate.lastIndexOf('-')+1,intentDate.length))).getTime()-(new Date(startDate.substring(0,4),startDate.substring(5,startDate.lastIndexOf('-'))-1,startDate.substring(startDate.lastIndexOf('-')+1,startDate.length))).getTime())/1000/60/60/24
										sumAccrualRatenf.setValue(nf.getValue()/30*days)
									}
								  }else if(null!=dateModel && dateModel=='dateModel_365'){
								    	dayAccrualRatenf.setValue(nf.getValue()*12/365)
									 	if(startDate!='' && intentDate!=''){
										intentDate=Ext.util.Format.date(intentDate,'Y-m-d')
											startDate=Ext.util.Format.date(startDate,'Y-m-d')
											var days=((new Date(intentDate.substring(0,4),intentDate.substring(5,intentDate.lastIndexOf('-'))-1,intentDate.substring(intentDate.lastIndexOf('-')+1,intentDate.length))).getTime()-(new Date(startDate.substring(0,4),startDate.substring(5,startDate.lastIndexOf('-'))-1,startDate.substring(startDate.lastIndexOf('-')+1,startDate.length))).getTime())/1000/60/60/24
										sumAccrualRatenf.setValue(nf.getValue()*12/365*days)
									}
								  }
								
								}
							}
							
						}]
					},{
						columnWidth : .03,
						layout : 'form',
						labelWidth : 20,
						labelAlgin : 'left',
						items : [{
							fieldLabel : "%",
							labelSeparator : '',
							anchor : "100%"
						}]
					},{
						columnWidth : .06,
						layout : 'form',
						labelWidth : 60,
						labelAlign : 'right',
						items : [{
							fieldLabel : "日化利率",
							labelSeparator : ''
						}]
					},{
						columnWidth : .06,
						layout : 'form',
						items : [{
							hideLabel :true,
							xtype : 'numberfield',
							name : "ownBpFundProject.dayManagementConsultingOfRate",
							readOnly : this.isReadOnly,
							anchor : '100%',
							decimalPrecision : 8,
							fieldClass : 'field-align',
							style : {
								imeMode : 'disabled'
							},
							value :0,
							listeners : {
								scope : this,
								'change' : function(nf){
									var dateModel=this.getCmpByName('ownBpFundProject.dateMode').getValue()
									var accrualnf=this.getCmpByName('ownBpFundProject.managementConsultingOfRate')
									var yearAccrualRatenf=this.getCmpByName('ownBpFundProject.yearManagementConsultingOfRate')
									var sumAccrualRatenf=this.getCmpByName('ownBpFundProject.sumManagementConsultingOfRate')
									var startDate=this.getCmpByName('ownBpFundProject.startDate').getValue()
									var intentDate=this.getCmpByName('ownBpFundProject.intentDate').getValue()
									
									if(null!=dateModel && dateModel=='dateModel_360'){
										yearAccrualRatenf.setValue(nf.getValue()*360)
										accrualnf.setValue(nf.getValue()*30)
										
									}else if(null!=dateModel && dateModel=='dateModel_365'){
										yearAccrualRatenf.setValue(nf.getValue()*365)
										accrualnf.setValue(nf.getValue()*365/12)
									}
									if(startDate!='' && intentDate!=''){
										intentDate=Ext.util.Format.date(intentDate,'Y-m-d')
											startDate=Ext.util.Format.date(startDate,'Y-m-d')
											var days=((new Date(intentDate.substring(0,4),intentDate.substring(5,intentDate.lastIndexOf('-'))-1,intentDate.substring(intentDate.lastIndexOf('-')+1,intentDate.length))).getTime()-(new Date(startDate.substring(0,4),startDate.substring(5,startDate.lastIndexOf('-'))-1,startDate.substring(startDate.lastIndexOf('-')+1,startDate.length))).getTime())/1000/60/60/24
										sumAccrualRatenf.setValue(nf.getValue()*days)
									}
								}
							}
						}]
					},{
						columnWidth : .045,
						layout : 'form',
						labelWidth : 20,
						labelAlgin : 'left',
						items : [{
							fieldLabel : "%",
							labelSeparator : '',
							anchor : "100%"
						}]
					},{
						columnWidth : .06,
						layout : 'form',
						labelWidth : 60,
						labelAlign : 'right',
						items : [{
							fieldLabel : "合计利率",
							labelSeparator : ''
						}]
					},{
						columnWidth : .27,
						layout : 'form',
						items : [{
							hideLabel :true,
							xtype : 'numberfield',
							name : "ownBpFundProject.sumManagementConsultingOfRate",
							readOnly : this.isReadOnly,
							decimalPrecision : 8,
							anchor : '100%',
							fieldClass : 'field-align',
							style : {
								imeMode : 'disabled'
							},
							value :0,
							listeners : {
								scope : this,
								'change' : function(nf){
									var dateModel=this.getCmpByName('ownBpFundProject.dateMode').getValue()
									var accrualnf=this.getCmpByName('ownBpFundProject.managementConsultingOfRate')
									var yearAccrualRatenf=this.getCmpByName('ownBpFundProject.yearManagementConsultingOfRate')
									var dayAccrualRatenf=this.getCmpByName('ownBpFundProject.dayManagementConsultingOfRate')
									var startDate=this.getCmpByName('ownBpFundProject.startDate').getValue()
									var intentDate=this.getCmpByName('ownBpFundProject.intentDate').getValue()
									
									if(startDate!='' && intentDate!=''){
										intentDate=Ext.util.Format.date(intentDate,'Y-m-d')
											startDate=Ext.util.Format.date(startDate,'Y-m-d')
											var days=((new Date(intentDate.substring(0,4),intentDate.substring(5,intentDate.lastIndexOf('-'))-1,intentDate.substring(intentDate.lastIndexOf('-')+1,intentDate.length))).getTime()-(new Date(startDate.substring(0,4),startDate.substring(5,startDate.lastIndexOf('-'))-1,startDate.substring(startDate.lastIndexOf('-')+1,startDate.length))).getTime())/1000/60/60/24
										var rate=nf.getValue()/days
										dayAccrualRatenf.setValue(rate);
										if(null!=dateModel && dateModel=='dateModel_360'){
											yearAccrualRatenf.setValue(rate*360)
											accrualnf.setValue(rate*30)
										
										}else if(null!=dateModel && dateModel=='dateModel_365'){
											yearAccrualRatenf.setValue(rate*365)
											accrualnf.setValue(rate*365/12)
										}
										
									}
								}
							}
						}]
					},{
						columnWidth : .02,
						layout : 'form',
						labelWidth : 20,
						labelAlgin : 'left',
						items : [{
							fieldLabel : "%",
							labelSeparator : '',
							anchor : "100%"
						}]
					}]
				},{
					columnWidth : 1,
					layout : 'column',
					defaults : {
						hidden:this.isHideFinanceRate
					},
					items : [{
						columnWidth :.08,
						layout : 'form',
						labelWidth : 85,
						labelAlign : 'right',
						items : [{
							fieldLabel : '财务服务费率',
							allowBlank : false
						}]
					},{
						columnWidth : .06,
						layout : 'form',
						labelWidth : 60,
						labelAlign : 'right',
						items : [{
							fieldLabel : "年化利率",
							labelSeparator : ''
						}]
					},{
						columnWidth : .16,
						layout : 'form',
						items : [{
							hideLabel :true,
							xtype : 'numberfield',
							name : "ownBpFundProject.yearFinanceServiceOfRate",
							readOnly : this.isReadOnly,
							decimalPrecision : 8,
							anchor : '100%',
							fieldClass : 'field-align',
							style : {
								imeMode : 'disabled'
							},
							value :0,
							listeners : {
								scope : this,
								'change' : function(nf){
									var dateModel=this.getCmpByName('ownBpFundProject.dateMode').getValue()
									var accrualnf=this.getCmpByName('ownBpFundProject.financeServiceOfRate')
									var dayAccrualRatenf=this.getCmpByName('ownBpFundProject.dayFinanceServiceOfRate')
									var sumAccrualRatenf=this.getCmpByName('ownBpFundProject.sumFinanceServiceOfRate')
									var startDate=this.getCmpByName('ownBpFundProject.startDate').getValue()
									var intentDate=this.getCmpByName('ownBpFundProject.intentDate').getValue()
									accrualnf.setValue((nf.getValue()/(12*10))*10)
									if(null!=dateModel && dateModel=='dateModel_360'){
										dayAccrualRatenf.setValue(nf.getValue()/360)
										if(startDate!='' && intentDate!=''){
											intentDate=Ext.util.Format.date(intentDate,'Y-m-d')
											startDate=Ext.util.Format.date(startDate,'Y-m-d')
											var days=((new Date(intentDate.substring(0,4),intentDate.substring(5,intentDate.lastIndexOf('-'))-1,intentDate.substring(intentDate.lastIndexOf('-')+1,intentDate.length))).getTime()-(new Date(startDate.substring(0,4),startDate.substring(5,startDate.lastIndexOf('-'))-1,startDate.substring(startDate.lastIndexOf('-')+1,startDate.length))).getTime())/1000/60/60/24
											sumAccrualRatenf.setValue(nf.getValue()/360*days)
										}
									}else if(null!=dateModel && dateModel=='dateModel_365'){
										dayAccrualRatenf.setValue(nf.getValue()/365)
										if(startDate!='' && intentDate!=''){
											intentDate=Ext.util.Format.date(intentDate,'Y-m-d')
											startDate=Ext.util.Format.date(startDate,'Y-m-d')
											var days=((new Date(intentDate.substring(0,4),intentDate.substring(5,intentDate.lastIndexOf('-'))-1,intentDate.substring(intentDate.lastIndexOf('-')+1,intentDate.length))).getTime()-(new Date(startDate.substring(0,4),startDate.substring(5,startDate.lastIndexOf('-'))-1,startDate.substring(startDate.lastIndexOf('-')+1,startDate.length))).getTime())/1000/60/60/24
											sumAccrualRatenf.setValue(nf.getValue()/365*days)
										}
									}
								}
							}
						}]
					},{
						columnWidth : .03,
						layout : 'form',
						labelWidth : 20,
						labelAlgin : 'left',
						items : [{
							fieldLabel : "%",
							labelSeparator : '',
							anchor : "100%"
						}]
					},{
						columnWidth : .06,
						layout : 'form',
						labelWidth : 60,
						labelAlign : 'right',
						items : [{
							fieldLabel : "月化利率",
							labelSeparator : ''
						}]
					},{
						columnWidth : .06,
						layout : 'form',
						items : [{
							hideLabel :true,
							xtype : 'numberfield',
							name : "ownBpFundProject.financeServiceOfRate",
							readOnly : this.isReadOnly,
							decimalPrecision : 8,
							anchor : '100%',
							fieldClass : 'field-align',
							style : {
								imeMode : 'disabled'
							},
							value :0,
							listeners : {
								scope : this,
								'change' : function(nf){
									var dateModel=this.getCmpByName('ownBpFundProject.dateMode').getValue()
									var yearAccrualRatenf=this.getCmpByName('ownBpFundProject.yearFinanceServiceOfRate')
									var dayAccrualRatenf=this.getCmpByName('ownBpFundProject.dayFinanceServiceOfRate')
									var sumAccrualRatenf=this.getCmpByName('ownBpFundProject.sumFinanceServiceOfRate')
									var startDate=this.getCmpByName('ownBpFundProject.startDate').getValue()
									var intentDate=this.getCmpByName('ownBpFundProject.intentDate').getValue()
									yearAccrualRatenf.setValue(nf.getValue()*12)
									if(null!=dateModel && dateModel=='dateModel_360'){
									 dayAccrualRatenf.setValue(nf.getValue()/30)
									 if(startDate!='' && intentDate!=''){
										intentDate=Ext.util.Format.date(intentDate,'Y-m-d')
										startDate=Ext.util.Format.date(startDate,'Y-m-d')
										var days=((new Date(intentDate.substring(0,4),intentDate.substring(5,intentDate.lastIndexOf('-'))-1,intentDate.substring(intentDate.lastIndexOf('-')+1,intentDate.length))).getTime()-(new Date(startDate.substring(0,4),startDate.substring(5,startDate.lastIndexOf('-'))-1,startDate.substring(startDate.lastIndexOf('-')+1,startDate.length))).getTime())/1000/60/60/24
										sumAccrualRatenf.setValue(nf.getValue()/30*days)
									}
								  }else if(null!=dateModel && dateModel=='dateModel_365'){
									 dayAccrualRatenf.setValue(nf.getValue()*12/365)
									 if(startDate!='' && intentDate!=''){
										intentDate=Ext.util.Format.date(intentDate,'Y-m-d')
										startDate=Ext.util.Format.date(startDate,'Y-m-d')
										var days=((new Date(intentDate.substring(0,4),intentDate.substring(5,intentDate.lastIndexOf('-'))-1,intentDate.substring(intentDate.lastIndexOf('-')+1,intentDate.length))).getTime()-(new Date(startDate.substring(0,4),startDate.substring(5,startDate.lastIndexOf('-'))-1,startDate.substring(startDate.lastIndexOf('-')+1,startDate.length))).getTime())/1000/60/60/24
										sumAccrualRatenf.setValue(nf.getValue()*12/365*days)
									}
								  }
									
								}
							}
							
						}]
					},{
						columnWidth : .03,
						layout : 'form',
						labelWidth : 20,
						labelAlgin : 'left',
						items : [{
							fieldLabel : "%",
							labelSeparator : '',
							anchor : "100%"
						}]
					},{
						columnWidth : .06,
						layout : 'form',
						labelWidth : 60,
						labelAlign : 'right',
						items : [{
							fieldLabel : "日化利率",
							labelSeparator : ''
						}]
					},{
						columnWidth : .06,
						layout : 'form',
						items : [{
							hideLabel :true,
							xtype : 'numberfield',
							anchor : '100%',
							name : "ownBpFundProject.dayFinanceServiceOfRate",
							readOnly : this.isReadOnly,
							decimalPrecision : 8,
							fieldClass : 'field-align',
							style : {
								imeMode : 'disabled'
							},
							value :0,
							listeners : {
								scope : this,
								'change' : function(nf){
									var dateModel=this.getCmpByName('ownBpFundProject.dateMode').getValue()
									var accrualnf=this.getCmpByName('ownBpFundProject.financeServiceOfRate')
									var yearAccrualRatenf=this.getCmpByName('ownBpFundProject.yearFinanceServiceOfRate')
									var sumAccrualRatenf=this.getCmpByName('ownBpFundProject.sumFinanceServiceOfRate')
									var startDate=this.getCmpByName('ownBpFundProject.startDate').getValue()
									var intentDate=this.getCmpByName('ownBpFundProject.intentDate').getValue()
									
									if(null!=dateModel && dateModel=='dateModel_360'){
										yearAccrualRatenf.setValue(nf.getValue()*360)
										accrualnf.setValue(nf.getValue()*30)
									}else if(null!=dateModel && dateModel=='dateModel_365'){
										yearAccrualRatenf.setValue(nf.getValue()*365)
										accrualnf.setValue(nf.getValue()*365/12)
									}
									if(startDate!='' && intentDate!=''){
										intentDate=Ext.util.Format.date(intentDate,'Y-m-d')
											startDate=Ext.util.Format.date(startDate,'Y-m-d')
											var days=((new Date(intentDate.substring(0,4),intentDate.substring(5,intentDate.lastIndexOf('-'))-1,intentDate.substring(intentDate.lastIndexOf('-')+1,intentDate.length))).getTime()-(new Date(startDate.substring(0,4),startDate.substring(5,startDate.lastIndexOf('-'))-1,startDate.substring(startDate.lastIndexOf('-')+1,startDate.length))).getTime())/1000/60/60/24
										sumAccrualRatenf.setValue(nf.getValue()*days)
									}
								}
							}
						}]
					},{
						columnWidth : .045,
						layout : 'form',
						labelWidth : 20,
						labelAlgin : 'left',
						items : [{
							fieldLabel : "%",
							labelSeparator : '',
							anchor : "100%"
						}]
					},{
						columnWidth : .06,
						layout : 'form',
						labelWidth : 60,
						labelAlign : 'right',
						items : [{
							fieldLabel : "合计利率",
							labelSeparator : ''
						}]
					},{
						columnWidth : .27,
						layout : 'form',
						items : [{
							hideLabel :true,
							xtype : 'numberfield',
							name : "ownBpFundProject.sumFinanceServiceOfRate",
							readOnly : this.isReadOnly,
							decimalPrecision : 8,
							anchor : '100%',
							fieldClass : 'field-align',
							style : {
								imeMode : 'disabled'
							},
							value :0,
							listeners : {
								scope : this,
								'change' : function(nf){
									var dateModel=this.getCmpByName('ownBpFundProject.dateMode').getValue()
									var accrualnf=this.getCmpByName('ownBpFundProject.financeServiceOfRate')
									var yearAccrualRatenf=this.getCmpByName('ownBpFundProject.yearFinanceServiceOfRate')
									var dayAccrualRatenf=this.getCmpByName('ownBpFundProject.dayFinanceServiceOfRate')
									var startDate=this.getCmpByName('ownBpFundProject.startDate').getValue()
									var intentDate=this.getCmpByName('ownBpFundProject.intentDate').getValue()
									
									if(startDate!='' && intentDate!=''){
										intentDate=Ext.util.Format.date(intentDate,'Y-m-d')
											startDate=Ext.util.Format.date(startDate,'Y-m-d')
											var days=((new Date(intentDate.substring(0,4),intentDate.substring(5,intentDate.lastIndexOf('-'))-1,intentDate.substring(intentDate.lastIndexOf('-')+1,intentDate.length))).getTime()-(new Date(startDate.substring(0,4),startDate.substring(5,startDate.lastIndexOf('-'))-1,startDate.substring(startDate.lastIndexOf('-')+1,startDate.length))).getTime())/1000/60/60/24
										var rate=nf.getValue()/days
										dayAccrualRatenf.setValue(rate);
										if(null!=dateModel && dateModel=='dateModel_360'){
											yearAccrualRatenf.setValue(rate*360)
											accrualnf.setValue(rate*30)
										
										}else if(null!=dateModel && dateModel=='dateModel_365'){
											yearAccrualRatenf.setValue(rate*365)
											accrualnf.setValue(rate*365/12)
										}
										
									}
								}
							}
						}]
					},{
						columnWidth : .02,
						layout : 'form',
						labelWidth : 20,
						labelAlgin : 'left',
						items : [{
							fieldLabel : "%",
							labelSeparator : '',
							anchor : "100%"
						}]
					}]
				},{
					columnWidth : 1,
					layout : 'column',
					items : [{
						columnWidth : .08,
						layout : 'form',
						labelWidth : 85,
						labelAlign : 'right',
						items : [{
							fieldLabel : '违约金比例'
						}]
					},{
						columnWidth : .06,
						layout : 'form',
						labelWidth : 60,
						labelAlign : 'right',
						items : [{
							fieldLabel : "贷款总额",
							labelSeparator : ''
						}]
					},{
						columnWidth : .16,
						layout : 'form',
						items : [{
							xtype : "numberfield",
							name : "ownBpFundProject.breachRate",
							decimalPrecision : 3,
							readOnly : this.isAllReadOnly,
							hideLabel : true,
							fieldClass : 'field-align',
							style : {
								imeMode : 'disabled'
							},
							value :0,
							anchor : "100%"
						}]
					},{
						columnWidth : .013,
						layout : 'form',
						labelWidth : 20,
						labelAlgin : 'left',
						items : [{
							fieldLabel : "%",
							labelSeparator : '',
							anchor : "100%"
						
						}]
					},{
						columnWidth : .2,
						layout : 'form',
						labelWidth : 89,
						labelAlign : 'right',
						hidden : true,
						items : [{
							fieldLabel : "保证金比例",
							xtype : "numberfield",
							fieldClass : 'field-align',
							name : "ownBpFundProject.riskRate",
							decimalPrecision : 3,
							//allowBlank : false,
							
							readOnly : this.isAllReadOnly,
							blankText : "保证金比例不能为空，请正确填写!",
							anchor : "100%",
							value :0

						}]
					},{
						columnWidth : .03,
						layout : 'form',
						labelWidth : 20,
						labelAlign : 'right',
						hidden : true,
						items : [{
							fieldLabel : "%",
							labelSeparator : '',
							anchor : "100%"
						
						}]
					},{
						columnWidth : .2,
						layout : 'form',
						labelWidth : 89,
						labelAlign : 'right',
						items : [{
							fieldLabel : "逾期贷款利率",
							xtype : "numberfield",
							fieldClass : 'field-align',
							name : "ownBpFundProject.overdueRateLoan",
							decimalPrecision : 3,
							allowBlank : false,
							style : {
								imeMode : 'disabled'
							},
							readOnly : this.isAllReadOnly,
							blankText : "逾期费率不能为空，请正确填写!",
							anchor : "100%",
							value :0,
							listeners : {
								afterrender : function(comp) {
									comp.on("keydown", function() {
											})
								}
							}

						}]
					},{
						columnWidth : .03,
						layout : 'form',
						labelWidth : 35,
						labelAlign : 'left',
						items : [{
							fieldLabel : "%/日",
							labelSeparator : '',
							anchor : "100%"
						
						}]
					},{
						columnWidth : .2,
						layout : 'form',
						labelWidth : 89,
						labelAlign : 'right',
						items : [{
							fieldLabel : '逾期罚息利率',
							xtype : "textfield",
							readOnly : this.isAllReadOnly,
							name : "ownBpFundProject.overdueRate",
							fieldClass : 'field-align',
							allowBlank : false,
							blankText : "逾期罚息利率不能为空，请正确填写!",
							decimalPrecision : 3,
							anchor : '100%',
							value :0
						}]
					},{
						columnWidth : .03,
						layout : 'form',
						labelWidth : 35,
						labelAlign : 'left',
						items : [{
							fieldLabel : "%/日",
							labelSeparator : '',
							anchor : "100%"
						
						}]
					}]
				},{
					columnWidth:1,
					hidden:true,
					layout:'column',
					items:[{
						layout:'form',
						labelWidth : 89,
						columnWidth:.3,
						items:[{
							xtype:'textfield',
							fieldLabel:'余额托管费率',
							readOnly : this.isAllReadOnly,
							anchor:'100%',
							name:'ownBpFundProject.balanceRate',
							value:'0'
						}]
					},{
						layout:'form',
						columnWidth:.03,
						items:[{
							style:'margin-top:3px;margin-left:3px;',
							html:'‰'
						}]
					}]
				},{
					columnWidth : 1,
					layout : 'form',
					labelWidth : 85,
					labelAlign : 'right',
					hidden : this.isHiddenRemark==false?false:true,
					items : [{
						xtype : 'textarea',
						fieldLabel : '修改备注',
						name : 'ownBpFundProject.remarks',
						readOnly : this.isAllReadOnly,
						anchor : '97%'
					}]
				},{
					columnWidth : 1,
					layout : 'form',
					buttonAlign : 'center',
					style : 'margin-left : 450px',
					items : [{
						xtype : 'button',
						text : '贷款试算',
						iconCls : 'btn-search',
						width : 110,
						hidden : this.isHiddencalculateBtn,
						scope : this,
						handler : function() {
							var isHiddenbackBtn=this.isHiddenbackBtn;
							 var projectMoney=this.getCmpByName("ownBpFundProject.ownJointMoney").getValue();
							 var startDate=this.getCmpByName("ownBpFundProject.startDate").getValue();
							 var payaccrualType=this.getCmpByName("ownBpFundProject.payaccrualType").getValue();
							 var dayOfEveryPeriod=this.getCmpByName("ownBpFundProject.dayOfEveryPeriod").getValue();
							 var payintentPeriod=this.getCmpByName("ownBpFundProject.payintentPeriod").getValue();
							 var isStartDatePay=this.getCmpByName("ownBpFundProject.isStartDatePay").getValue();
							 var payintentPerioDate=this.getCmpByName("ownBpFundProject.payintentPerioDate").getValue();
							 var intentDate=this.getCmpByName("ownBpFundProject.intentDate").getValue();
							 var accrual=this.getCmpByName("ownBpFundProject.accrual").getValue();
							 var managementConsultingOfRate=this.getCmpByName("ownBpFundProject.managementConsultingOfRate").getValue();
							 var accrualtype=this.getCmpByName("ownBpFundProject.accrualtype").getValue();
							 var isPreposePayAccrual=this.getCmpByName("ownBpFundProject.isPreposePayAccrual").getValue();
							 var overdueRate=this.getCmpByName("ownBpFundProject.overdueRate").getValue();
							 var overdueRateLoan=this.getCmpByName("ownBpFundProject.overdueRateLoan").getValue();
							 var isInterestByOneTime=this.getCmpByName("ownBpFundProject.isInterestByOneTime").getValue();
							 var yearAccrualRate=this.getCmpByName("ownBpFundProject.yearAccrualRate").getValue();
							 var dayAccrualRate=this.getCmpByName("ownBpFundProject.dayAccrualRate").getValue();
							 var sumAccrualRate=this.getCmpByName("ownBpFundProject.sumAccrualRate").getValue();
							 var yearManagementConsultingOfRate=this.getCmpByName("ownBpFundProject.yearManagementConsultingOfRate").getValue();
							 var dayManagementConsultingOfRate=this.getCmpByName("ownBpFundProject.dayManagementConsultingOfRate").getValue();
							 var sumManagementConsultingOfRate=this.getCmpByName("ownBpFundProject.sumManagementConsultingOfRate").getValue();
							 var yearFinanceServiceOfRate=this.getCmpByName("ownBpFundProject.yearFinanceServiceOfRate").getValue();
							 var dayFinanceServiceOfRate=this.getCmpByName("ownBpFundProject.dayFinanceServiceOfRate").getValue();
							 var sumFinanceServiceOfRate=this.getCmpByName("ownBpFundProject.sumFinanceServiceOfRate").getValue();
							 var accrualnew=this.getCmpByName("ownBpFundProject.accrualnew").getValue();
							 var financeServiceOfRate=this.getCmpByName("ownBpFundProject.financeServiceOfRate").getValue();
							 var dateMode=this.getCmpByName("ownBpFundProject.dateMode").getValue();
							 var breachRate=this.getCmpByName("ownBpFundProject.breachRate").getValue();
							 new calculateFundIntent({
								 projectMoney:projectMoney,
								 startDate:startDate,
								 payaccrualType:payaccrualType,
								 dayOfEveryPeriod:dayOfEveryPeriod,
								 payintentPeriod:payintentPeriod,
								 isStartDatePay:isStartDatePay,
								 payintentPerioDate:payintentPerioDate,
								 intentDate:intentDate,
								 accrual:accrual,
								 managementConsultingOfRate:managementConsultingOfRate,
								 accrualtype:accrualtype,
								 projectId:this.projectId,
								 isPreposePayAccrual:isPreposePayAccrual,
								 overdueRate:overdueRate,
								 overdueRateLoan:overdueRateLoan,
								 isInterestByOneTime:isInterestByOneTime,
								 yearAccrualRate:yearAccrualRate,
								 dayAccrualRate:dayAccrualRate,
								 sumAccrualRate:sumAccrualRate,
								 yearManagementConsultingOfRate:yearManagementConsultingOfRate,
								 dayManagementConsultingOfRate:dayManagementConsultingOfRate,
								 sumManagementConsultingOfRate:sumManagementConsultingOfRate,
								 yearFinanceServiceOfRate:yearFinanceServiceOfRate,
								 dayFinanceServiceOfRate:dayFinanceServiceOfRate,
								 sumFinanceServiceOfRate:sumFinanceServiceOfRate,
								 accrualnew:accrualnew,
								 financeServiceOfRate:financeServiceOfRate,
								 dateMode:dateMode,
								 breachRate:breachRate,
								 idDefinition1:idDefinition1,
								 objectfinace:this,
								 isHiddenbackBtn:isHiddenbackBtn
								}).show();
						}
					}]
				}]
			}]
		});
		var selectInvestEnterpriseObj=function(obj){
			Ext.getCmp('investorIds').setValue(obj.id);
			Ext.getCmp('investName').setValue(obj.InvestEnterpriseName);
		}
	}
});