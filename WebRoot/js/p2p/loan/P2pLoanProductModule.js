P2pLoanProductModule_ProductDiscribe = Ext.extend(Ext.Panel, {
	layout : "form",
	autoHeight : true,
	frame : true,
	labelAlign : 'right',
	isAllReadOnly : false,
	isFlow : true,
	constructor : function(_cfg) {
		if (_cfg == null) {
			_cfg = {};
		}
		if (_cfg.isAllReadOnly) {
			this.isAllReadOnly = _cfg.isAllReadOnly;
		}
		Ext.applyIf(this, _cfg);
		P2pLoanProductModule_ProductDiscribe.superclass.constructor.call(this, {
			layout : 'anchor',
			frame : true,
			border : false,
			scope : this,
			defaults : {
				anchor : '98%',
				columnWidth : 1,
				isFormField : true,
				labelWidth : '115'
			},
			items : [{
				layout : "column",
				border : false,
				scope : this,
				defaults : {
					anchor : '100%',
					columnWidth : 1,
					isFormField : true,
					labelWidth : 90
				},
				items : [{
					columnWidth : 0.5, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : 60,
					items : [{
								name : 'p2pLoanProduct.productId',
								xtype : 'hidden',
								value : this.productId == null ? '' : this.productId
							}, {
								fieldLabel : '业务品种',
								xtype : 'combo',
								mode : 'local',
								displayField : 'name',
								triggerAction : "all",
								hiddenName:'p2pLoanProduct.operationType',
								anchor : '100%',
								valueField : 'id',
								allowBlank : false,
								readOnly : this.isEditReadOnly,
								editable : false,
								store : new Ext.data.SimpleStore({
									fields : ["name", "id"],
									data : [["企业贷", "enterprise"], ["个人贷", "person"]]
								})
						}]
				},{
					columnWidth : 0.5, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : 90,
					items : [ {
								fieldLabel : '产品名称',
								name : 'p2pLoanProduct.productName',
								xtype : 'textfield',
								readOnly : this.isEditReadOnly,
								anchor : '100%',
								maxLength : 255
							}]
				},{
					columnWidth : 1, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : 60,
					items : [{
								xtype : 'textarea',
								anchor : '100%',
								fieldLabel : '适用范围',
								readOnly : this.isEditReadOnly,
								name : 'p2pLoanProduct.userScope'
							}]
				}]
			}]
		});
	}
});

P2pLoanProductModule_ProductFiancial = Ext.extend(Ext.Panel, {
	layout : "form",
	autoHeight : true,
	frame : true,
	labelAlign : 'right',
	isAllReadOnly : false,
	productId : 0,
	defination:"_productDefination_",
	constructor : function(_cfg) {
		if (_cfg == null) {
			_cfg = {};
		}
		if (_cfg.isAllReadOnly) {
			this.isAllReadOnly = _cfg.isAllReadOnly;
		}
		if (_cfg.productId) {
			this.productId = _cfg.productId;
		}
		if (_cfg.defination) {
			this.defination = _cfg.defination;
		}
		Ext.applyIf(this, _cfg);
		// 每期还款日固定在
		var storepayintentPeriod = "[";
		for (var i = 1; i < 31; i++) {
			storepayintentPeriod = storepayintentPeriod + "[" + i + ", " + i
					+ "],";
		}
		storepayintentPeriod = storepayintentPeriod.substring(0,
				storepayintentPeriod.length - 1);
		storepayintentPeriod = storepayintentPeriod + "]";
		var obstorepayintentPeriod = Ext.decode(storepayintentPeriod);
		// 贷款期数
		var payintentPeriods = "[";
		for (var i = 1; i <= 36; i++) {
			payintentPeriods = payintentPeriods + "[" + i + ", " + i + "],";
		}
		payintentPeriods = payintentPeriods.substring(0,
				payintentPeriods.length - 1);
		payintentPeriods = payintentPeriods + "]";
		var obpayintentPeriods = Ext.decode(payintentPeriods);

		P2pLoanProductModule_ProductFiancial.superclass.constructor.call(this, {
			items : [{
				layout : "column",
				border : false,
				scope : this,
				defaults : {
					anchor : '100%',
					columnWidth : 1,
					isFormField : true,
					labelWidth : '110'
				},
				items : [{
					columnWidth : 1,
					layout : 'column',
					items : [{
								columnWidth : .1, // 该列在整行中所占的百分比
								layout : "form", // 从上往下的布局
								labelWidth : 85,
								labelAlign : 'right',
								items : [{
											fieldLabel : "还款方式 ",
											fieldClass : 'field-align',
											anchor : "100%"
										}]
							}, {
								columnWidth : 0.12,
								labelWidth : 1,
								layout : 'form',
								items : [{
									xtype : 'radio',
									boxLabel : '等额本金',
									name : 'f1',
									id : "jixifs1"+this.defination+this.productId,
									inputValue : false,
									anchor : "100%",
									disabled : this.isAllReadOnly,
									listeners : {
										scope : this,
										check : function(obj, checked) {
											var flag = Ext.getCmp("jixifs1"+this.defination+this.productId).getValue();
											if (flag == true) {
												this.getCmpByName('p2pLoanProduct.accrualtype').setValue("sameprincipal");
												Ext.getCmp("jixizq1"+this.defination+this.productId).setDisabled(true);
												Ext.getCmp("jixizq2"+this.defination+this.productId).setDisabled(true);
												Ext.getCmp("jixizq3"+this.defination+this.productId).setDisabled(true);
												Ext.getCmp("jixizq4"+this.defination+this.productId).setDisabled(true);
											//	Ext.getCmp("jixizq6"+this.defination+this.productId).setDisabled(true);
											//	this.getCmpByName('p2pLoanProduct.dayOfEveryPeriod').setDisabled(true);
												Ext.getCmp("jixizq2"+this.defination+this.productId).setValue(true);
												Ext.getCmp("jixizq1"+this.defination+this.productId).setValue(false);
												this.getCmpByName('p2pLoanProduct.payaccrualType').setValue("monthPay");
											//	this.getCmpByName('p2pLoanProduct.payintentPeriod').setDisabled(false);
												Ext.getCmp("jixifs2"+this.defination+this.productId).setValue(false);
												Ext.getCmp("jixifs5"+this.defination+this.productId).setValue(false);
												Ext.getCmp("jixifs3"+this.defination+this.productId).setValue(false);
												Ext.getCmp("jixifs6"+this.defination+this.productId).setValue(false);
									/*			Ext.getCmp("meiqihkrq1"+this.defination+this.productId).setDisabled(false);
												Ext.getCmp("meiqihkrq2"+this.defination+this.productId).setDisabled(false);*/
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
									name : 'f1',
									id : "jixifs2"+this.defination+this.productId,
									inputValue : false,
									disabled : this.isAllReadOnly,
									listeners : {
										scope : this,
										check : function(obj, checked) {
											var flag = Ext.getCmp("jixifs2"+this.defination+this.productId).getValue();
											if (flag == true) {
												this.getCmpByName('p2pLoanProduct.accrualtype').setValue("sameprincipalandInterest");
												Ext.getCmp("jixizq1"+this.defination+this.productId).setDisabled(true);
												Ext.getCmp("jixizq2"+this.defination+this.productId).setDisabled(true);
												Ext.getCmp("jixizq3"+this.defination+this.productId).setDisabled(true);
												Ext.getCmp("jixizq4"+this.defination+this.productId).setDisabled(true);
											//	Ext.getCmp("jixizq6"+this.defination+this.productId).setDisabled(true);
											//	this.getCmpByName('p2pLoanProduct.dayOfEveryPeriod').setDisabled(true);
												Ext.getCmp("jixizq2"+this.defination+this.productId).setValue(true);
												Ext.getCmp("jixizq1"+this.defination+this.productId).setValue(false);
												this.getCmpByName('p2pLoanProduct.payaccrualType').setValue("monthPay");
											//	this.getCmpByName('p2pLoanProduct.payintentPeriod').setDisabled(false);
												Ext.getCmp("jixifs1"+this.defination+this.productId).setValue(false);
												Ext.getCmp("jixifs5"+this.defination+this.productId).setValue(false);
												Ext.getCmp("jixifs3"+this.defination+this.productId).setValue(false);
												Ext.getCmp("jixifs6"+this.defination+this.productId).setValue(false);
											/*	Ext.getCmp("meiqihkrq1"+this.defination+this.productId).setDisabled(false);
												Ext.getCmp("meiqihkrq2"+this.defination+this.productId).setDisabled(false);*/
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
									name : 'f1',
									id : "jixifs5"+this.defination+this.productId,
									inputValue : false,
									disabled : this.isAllReadOnly,
									listeners : {
										scope : this,
										check : function(obj, checked) {
											var flag = Ext.getCmp("jixifs5"+this.defination+this.productId).getValue();
											if (flag == true) {
												this.getCmpByName('p2pLoanProduct.accrualtype').setValue("sameprincipalsameInterest");
												if (this.isAllReadOnly == true) {
													Ext.getCmp("jixizq1"+this.defination+this.productId).setDisabled(true);
													Ext.getCmp("jixizq2"+this.defination+this.productId).setDisabled(true);
													Ext.getCmp("jixizq3"+this.defination+this.productId).setDisabled(true);
													Ext.getCmp("jixizq4"+this.defination+this.productId).setDisabled(true);
												//	Ext.getCmp("jixizq6"+this.defination+this.productId).setDisabled(true);
												} else {
													Ext.getCmp("jixizq1"+this.defination+this.productId).setDisabled(false);
													Ext.getCmp("jixizq2"+this.defination+this.productId).setDisabled(false);
													Ext.getCmp("jixizq3"+this.defination+this.productId).setDisabled(false);
													Ext.getCmp("jixizq4"+this.defination+this.productId).setDisabled(false);
												//	Ext.getCmp("jixizq6"+this.defination+this.productId).setDisabled(false);
												}
											//	this.getCmpByName('p2pLoanProduct.dayOfEveryPeriod').setDisabled(true);
												this.getCmpByName('p2pLoanProduct.payaccrualType').setValue("monthPay");
											//	this.getCmpByName('p2pLoanProduct.payintentPeriod').setDisabled(false);
											//	this.getCmpByName('p2pLoanProduct.payintentPerioDate').setDisabled(true);
												Ext.getCmp("jixifs2"+this.defination+this.productId).setValue(false);
												Ext.getCmp("jixifs1"+this.defination+this.productId).setValue(false);
												Ext.getCmp("jixifs3"+this.defination+this.productId).setValue(false);
												Ext.getCmp("jixifs6"+this.defination+this.productId).setValue(false);
											/*	Ext.getCmp("meiqihkrq1"+this.defination+this.productId).setDisabled(true);
												Ext.getCmp("meiqihkrq1"+this.defination+this.productId).setValue(false);
												Ext.getCmp("meiqihkrq2"+this.defination+this.productId).setDisabled(true);
												Ext.getCmp("meiqihkrq2"+this.defination+this.productId).setValue(true)*/
											}
										}
									}
								}]
							}, {
								columnWidth : 0.15,
								labelWidth : 1,
								layout : 'form',
								items : [{
									xtype : 'radio',
									boxLabel : '按期付息,到期还本',
									name : 'f1',
									id : "jixifs3"+this.defination+this.productId,
									inputValue : false,
									checked : true,
									anchor : "100%",
									disabled : this.isAllReadOnly,
									listeners : {
										scope : this,
										check : function(obj, checked) {
											var flag = Ext.getCmp("jixifs3"+this.defination+this.productId)
													.getValue();
											if (flag == true) {
												this.getCmpByName('p2pLoanProduct.accrualtype').setValue("singleInterest");
												Ext.getCmp("jixizq1"+this.defination+this.productId).setDisabled(false);
												Ext.getCmp("jixizq2"+this.defination+this.productId).setDisabled(false);
												Ext.getCmp("jixizq3"+this.defination+this.productId).setDisabled(false);
												Ext.getCmp("jixizq4"+this.defination+this.productId).setDisabled(false);
											//	Ext.getCmp("jixizq6"+this.defination+this.productId).setDisabled(false);
											//	this.getCmpByName('p2pLoanProduct.dayOfEveryPeriod').setDisabled(false);
												Ext.getCmp("jixifs2"+this.defination+this.productId).setValue(false);
												Ext.getCmp("jixifs5"+this.defination+this.productId).setValue(false);
												Ext.getCmp("jixifs1"+this.defination+this.productId).setValue(false);
												Ext.getCmp("jixifs6"+this.defination+this.productId).setValue(false);
											/*	Ext.getCmp("meiqihkrq1"+this.defination+this.productId).setDisabled(false);
												Ext.getCmp("meiqihkrq2"+this.defination+this.productId).setDisabled(false);*/
											}
										}
									}
								}, {
									xtype : "hidden",
									name : "p2pLoanProduct.accrualtype",
									value : "singleInterest"
								}]
							}, {
								columnWidth : 0.15,
								labelWidth : 1,
								layout : 'form',
								items : [{
									xtype : 'radio',
									boxLabel : '其他还款方式',
									anchor : "100%",
									name : 'f1',
									id : "jixifs6"+this.defination+this.productId,
									inputValue : false,
									disabled : this.isAllReadOnly,
									listeners : {
										scope : this,
										check : function(obj, checked) {
											var flag = Ext.getCmp("jixifs6"+this.defination+this.productId).getValue();
											if (flag == true) {
												this.getCmpByName('p2pLoanProduct.accrualtype').setValue("otherMothod");
												if (this.isAllReadOnly == true) {
													Ext.getCmp("jixizq1"+this.defination+this.productId).setDisabled(true);
													Ext.getCmp("jixizq2"+this.defination+this.productId).setDisabled(true);
													Ext.getCmp("jixizq3"+this.defination+this.productId).setDisabled(true);
													Ext.getCmp("jixizq4"+this.defination+this.productId).setDisabled(true);
												//	Ext.getCmp("jixizq6"+this.defination+this.productId).setDisabled(true);
												} else {
													Ext.getCmp("jixizq1"+this.defination+this.productId).setDisabled(false);
													Ext.getCmp("jixizq2"+this.defination+this.productId).setDisabled(false);
													Ext.getCmp("jixizq3"+this.defination+this.productId).setDisabled(false);
													Ext.getCmp("jixizq4"+this.defination+this.productId).setDisabled(false);
												//	Ext.getCmp("jixizq6"+this.defination+this.productId).setDisabled(false);
												}
											//	this.getCmpByName('p2pLoanProduct.dayOfEveryPeriod').setDisabled(true);
												Ext.getCmp("jixizq2"+this.defination+this.productId).setValue(true);
												Ext.getCmp("jixizq1"+this.defination+this.productId).setValue(false);
												this.getCmpByName('p2pLoanProduct.payaccrualType').setValue("monthPay");
											//	this.getCmpByName('p2pLoanProduct.payintentPeriod').setDisabled(false);
												Ext.getCmp("jixifs2"+this.defination+this.productId).setValue(false);
												Ext.getCmp("jixifs5"+this.defination+this.productId).setValue(false);
												Ext.getCmp("jixifs3"+this.defination+this.productId).setValue(false);
												Ext.getCmp("jixifs1"+this.defination+this.productId).setValue(false);
											/*	Ext.getCmp("meiqihkrq1"+this.defination+this.productId).setDisabled(false);
												Ext.getCmp("meiqihkrq2"+this.defination+this.productId).setDisabled(false);*/
											}
										}
									}

								}]
							}]
				}, {
					columnWidth : 1,
					layout : 'column',
					items : [{
								columnWidth : .1, // 该列在整行中所占的百分比
								layout : "form", // 从上往下的布局
								labelWidth : 85,
								labelAlign : 'right',
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
									name : 'z1',
									id : "jixizq1"+this.defination+this.productId,
									inputValue : true,
									anchor : "100%",
									disabled : this.isAllReadOnly,
									listeners : {
										scope : this,
										check : function(obj, checked) {
											var flag = Ext.getCmp("jixizq1"+this.defination+this.productId).getValue();
											if (flag == true) {
												this.getCmpByName('p2pLoanProduct.payaccrualType').setValue("dayPay");
										/*		Ext.getCmp("meiqihkrq1"+this.defination+this.productId).setDisabled(true);
												Ext.getCmp("meiqihkrq1"+this.defination+this.productId).setValue(false);
												Ext.getCmp("meiqihkrq2"+this.defination+this.productId).setDisabled(true);
												Ext.getCmp("meiqihkrq2"+this.defination+this.productId).setValue(true);*/
												Ext.getCmp("jixizq2"+this.defination+this.productId).setValue(false);
												Ext.getCmp("jixizq3"+this.defination+this.productId).setValue(false);
												Ext.getCmp("jixizq4"+this.defination+this.productId).setValue(false);
											//	Ext.getCmp("jixizq6"+this.defination+this.productId).setValue(false);
											//	this.getCmpByName('p2pLoanProduct.isStartDatePay').setValue("2");
											}/* else {
												Ext.getCmp("meiqihkrq1"+this.defination+this.productId).setDisabled(false);
												Ext.getCmp("meiqihkrq2"+this.defination+this.productId).setDisabled(false);
												if (Ext.getCmp("meiqihkrq1"+this.defination+this.productId).getValue() == true) {
													this.getCmpByName('p2pLoanProduct.payintentPerioDate').setDisabled(false);
												}
											}*/
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
									name : 'z1',
									id : "jixizq2"+this.defination+this.productId,
									inputValue : false,
									checked : true,
									anchor : "100%",
									listeners : {
										scope : this,
										check : function(obj, checked) {
											var flag = Ext.getCmp("jixizq2"+this.defination+this.productId).getValue();
											if (flag == true) {
												this.getCmpByName('p2pLoanProduct.payaccrualType').setValue("monthPay");
												Ext.getCmp("jixizq1"+this.defination+this.productId).setValue(false);
												Ext.getCmp("jixizq3"+this.defination+this.productId).setValue(false);
												Ext.getCmp("jixizq4"+this.defination+this.productId).setValue(false);
											//	Ext.getCmp("jixizq6"+this.defination+this.productId).setValue(false);
											}
										}
									}
								}, {
									xtype : 'hidden',
									name : "p2pLoanProduct.payaccrualType",
									value : "monthPay"
								}]
							}, {
								columnWidth : 0.12,
								labelWidth : 1,
								layout : 'form',
								items : [{
									xtype : 'radio',
									boxLabel : '季',
									name : 'z1',
									id : "jixizq3"+this.defination+this.productId,
									inputValue : false,
									anchor : "100%",
									disabled : this.isAllReadOnly,
									listeners : {
										scope : this,
										check : function(obj, checked) {
											var flag = Ext.getCmp("jixizq3"+this.defination+this.productId).getValue();
											if (flag == true) {
												this.getCmpByName('p2pLoanProduct.payaccrualType').setValue("seasonPay");
												Ext.getCmp("jixizq1"+this.defination+this.productId).setValue(false);
												Ext.getCmp("jixizq2"+this.defination+this.productId).setValue(false);
												Ext.getCmp("jixizq4"+this.defination+this.productId).setValue(false);
											//	Ext.getCmp("jixizq6"+this.defination+this.productId).setValue(false);
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
									name : 'z1',
									id : "jixizq4"+this.defination+this.productId,
									inputValue : false,
									anchor : "100%",
									disabled : this.isAllReadOnly,
									listeners : {
										scope : this,
										check : function(obj, checked) {
											var flag = Ext.getCmp("jixizq4"+this.defination+this.productId).getValue();
											if (flag == true) {
												this.getCmpByName('p2pLoanProduct.payaccrualType').setValue("yearPay");
												Ext.getCmp("jixizq1"+this.defination+this.productId).setValue(false);
												Ext.getCmp("jixizq3"+this.defination+this.productId).setValue(false);
												Ext.getCmp("jixizq2"+this.defination+this.productId).setValue(false);
											//	Ext.getCmp("jixizq6"+this.defination+this.productId).setValue(false);
											}
										}
									}
								}]
							}]
				},{
					columnWidth : 1,
					layout : 'column',
					items : [{
								columnWidth : .1,
								layout : 'form',
								labelWidth : 85,
								labelAlign : 'right',
								items : [{
											fieldLabel : '还款选项'
										}]
							}, {
								columnWidth : .1, // 该列在整行中所占的百分比
								layout : "form", // 从上往下的布局
								labelWidth : 1,
								items : [{
									xtype : "checkbox",
									boxLabel : "前置付息",
									disabled : this.isAllReadOnly,
									anchor : "100%",
									name : "isPreposePayAccrualCheck",
									checked : this.record == null
											|| this.record.data.isPreposePayAccrual == 0
											? null
											: true,
									listeners : {
										scope : this,
										'check' : function(box, value) {
											if (value == true) {
												this.getCmpByName('p2pLoanProduct.isPreposePayAccrual').setValue(1);
											} else {
												this.getCmpByName('p2pLoanProduct.isPreposePayAccrual').setValue(0);
											}
										}
									}
								}, {
									xtype : 'hidden',
									name : 'p2pLoanProduct.isPreposePayAccrual',
									value : 0
								}]
							}, {
								columnWidth : .16, // 该列在整行中所占的百分比
								layout : "form", // 从上往下的布局
								labelWidth : 1,
								items : [{
									xtype : "checkbox",
									boxLabel : "一次性支付全部利息",
									disabled : this.isAllReadOnly,
									anchor : "100%",
									name : "isInterestByOneTimeCheck",
									checked : this.record == null
											|| this.record.data.isInterestByOneTime == 0
											? null
											: true,
									listeners : {
										scope : this,
										'check' : function(box, value) {
											if (value == true) {
												this.getCmpByName('p2pLoanProduct.isInterestByOneTime').setValue(1);
											} else {
												this.getCmpByName('p2pLoanProduct.isInterestByOneTime').setValue(0);
											}
										}
									}
								}, {
									xtype : 'hidden',
									name : 'p2pLoanProduct.isInterestByOneTime',
									value : 0
								}]
							}]
				},{
					columnWidth : 1,
					layout : 'column',
					items : [{
							columnWidth : .25,
							layout : 'form',
							defaults : {
								anchor : '100%'
							},
							labelWidth : 85,
							items : [{
								fieldLabel : "借款额度",
								readOnly :this.isDiligenceReadOnly,
								readOnly : this.isAllReadOnly,
								allowBlank : false,
								xtype : 'numberfield',
								anchor:'100%',
								maxLength : 50,
								name : 'p2pLoanProduct.loanStartMoney'
							}]
						},{
							columnWidth : .05, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : 22,
							items : [{
								fieldLabel : "<span style='margin-left:1px'>元</span> ",
								align :'left',
								labelSeparator : '',
								anchor : "100%"
							}]
						},{
							columnWidth : .22,
							layout : 'form',
							defaults : {
								anchor : '100%'
							},
							labelWidth :  66,
							items : [{
								fieldLabel:'至',
								anchor:'100%',
								readOnly :this.isDiligenceReadOnly,
								readOnly : this.isAllReadOnly,
								allowBlank : false,
								maxLength : 50,
								name : 'p2pLoanProduct.loanEndMoney',
								xtype : 'numberfield'
							}]
						},{
							columnWidth : .05, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : 22,
							items : [{
								fieldLabel : "<span style='margin-left:1px'>元</span> ",
								labelSeparator : '',
								align :'left',
								anchor : "100%"
							}]
						}]
				},{
					columnWidth : 1,
					layout : 'column',
					items : [{
							columnWidth : .25,
							layout : 'form',
							defaults : {
								anchor : '100%'
							},
							labelWidth : 85,
							items : [{
								fieldLabel : "审批时间",
								readOnly :this.isDiligenceReadOnly,
								readOnly : this.isAllReadOnly,
								xtype : 'numberfield',
								allowBlank : false,
								anchor:'100%',
								maxLength : 50,
								name : 'p2pLoanProduct.approveStartTime'
							}]
						},{
							columnWidth : .05, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : 45,
							items : [{
								fieldLabel : "<span style='margin-left:1px'>工作日</span> ",
								labelSeparator : '',
								anchor : "100%"
							}]
						},{
							columnWidth : .22,
							layout : 'form',
							defaults : {
								anchor : '100%'
							},
							labelWidth :  66,
							items : [{
								fieldLabel:'至',
								anchor:'100%',
								readOnly :this.isDiligenceReadOnly,
								readOnly : this.isAllReadOnly,
								allowBlank : false,
								maxLength : 50,
								name : 'p2pLoanProduct.approveEndTime',
								xtype : 'numberfield'
							}]
						},{
							columnWidth : .05, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : 45,
							items : [{
								fieldLabel : "<span style='margin-left:1px'>工作日</span> ",
								labelSeparator : '',
								anchor : "100%"
							}]
						}]
				}]
			
			
			
			}]
		});
	}
});