SuperviseLeaseFinanceInfoPanel = Ext.extend(Ext.Panel, {
	layout : "form",
	autoHeight : true,
	labelAlign : 'right',
	isAllReadOnly : false,
	isDiligenceReadOnly : false,
	idDefinition : 'liucheng',
	constructor : function(_cfg) {
		if (_cfg == null) {
			_cfg = {};
		}
		if (_cfg.isAllReadOnly) {
			this.isAllReadOnly = _cfg.isAllReadOnly;
			this.isDiligenceReadOnly = true;
		}
		if (_cfg.isDiligenceReadOnly) {
			this.isDiligenceReadOnly = _cfg.isDiligenceReadOnly;
		}
		if (typeof(_cfg.isStartDateReadOnly) != "undefined") {
			this.isStartDateReadOnly = _cfg.isStartDateReadOnly;
		} else {
			this.isStartDateReadOnly = true
		}
		if (_cfg.idDefinition) {
			this.idDefinition = _cfg.idDefinition;
		}
		var idDefinition1 = this.idDefinition;
		Ext.applyIf(this, _cfg);
		this.initComponents();
		this.idDefinition = this.projectId + this.idDefinition;
		var storepayintentPeriod = "[";
		for (var i = 1; i < 31; i++) {
			storepayintentPeriod = storepayintentPeriod + "[" + i + ", " + i
					+ "],";
		}
		storepayintentPeriod = storepayintentPeriod.substring(0,
				storepayintentPeriod.length - 1);
		storepayintentPeriod = storepayintentPeriod + "]";
		var obstorepayintentPeriod = Ext.decode(storepayintentPeriod);
		var setIntentDate = function(payAccrualType, dayOfEveryPeriod,
				payintentPeriod, startDate, intentDatePanel) {
			Ext.Ajax.request({
				url : __ctxPath + "/creditFlow/leaseFinance/project/getIntentDateFlLeaseFinanceProject.do",
				method : 'POST',
				scope : this,
				params : {
					payAccrualType : payAccrualType,
					dayOfEveryPeriod : dayOfEveryPeriod,
					payintentPeriod : payintentPeriod,
					startDate : startDate
				},
				success : function(response, request) {
					obj = Ext.util.JSON.decode(response.responseText);
					intentDatePanel.setValue(obj.intentDate);
				},
				failure : function(response, request) {
					Ext.ux.Toast.msg('操作信息', '查询任务完成时限操作失败!');
				}
			});
		}
		SuperviseLeaseFinanceInfoPanel.superclass.constructor.call(this, {
			items : [{
				layout : "column",
				border : false,
				scope : this,

				items : [{
					columnWidth : .2, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : 100,
					items : [{
						xtype : 'hidden',
						name : 'slSuperviseRecord.id'
					},{
						xtype : 'hidden',
						name : 'slSuperviseRecord.isPreposePayAccrualsupervise',
						value :0
					},{
						xtype : 'hidden',
						name : 'slSuperviseRecord.isInterestByOneTime',
						value :0
					},{
						xtype : "textfield",
						fieldLabel : "租赁成本",
						fieldClass : 'field-align',
						name : "projectMoney1",
						readOnly : this.isAllReadOnly,
						allowNegative : false, // 允许负数
						style : {
							imeMode : 'disabled'
						},
						blankText : "租赁成本不能为空，请正确填写!",
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
									nf.setValue(Ext.util.Format.number(value,
											'0,000.00'))
									this
											.getCmpByName("slSuperviseRecord.continuationMoney")
											.setValue(value);
								} else {

									if (value.indexOf(",") <= 0) {
										var ke = Ext.util.Format.number(value,
												'0,000.00')
										nf.setValue(ke);
										this
												.getCmpByName("slSuperviseRecord.continuationMoney")
												.setValue(value);
									} else {
										var last = value.substring(index + 1,
												value.length);
										if (last == 0) {
											var temp = value
													.substring(0, index);
											temp = temp.replace(/,/g, "");
											this
													.getCmpByName("slSuperviseRecord.continuationMoney")
													.setValue(temp);
											nf.setValue(Ext.util.Format.number(
													temp, '0,000.00'))
										} else {
											var temp = value.replace(/,/g, "");
											this
													.getCmpByName("slSuperviseRecord.continuationMoney")
													.setValue(temp);
											nf.setValue(Ext.util.Format.number(
													temp, '0,000.00'))
										}
									}

								}
								
							}
						}
					}, {
						xtype : "hidden",
						name : "slSuperviseRecord.continuationMoney"
					}]
				}, {
					columnWidth : .05, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : 20,
					items : [{
								fieldLabel : "元 ",
								labelSeparator : '',
								anchor : "100%"
							}]
				}, {
					columnWidth : .2, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : 80,
					items : [{
							xtype : 'numberfield',
							fieldLabel : '租赁手续费率',
							allowBlank : false,
							anchor : '100%',
							fieldClass : 'field-align',
							decimalPrecision : 8,
							readOnly:this.isAllReadOnly,//add by gao
							name : 'slSuperviseRecord.managementConsultingOfRate'
						}]
				}, {
					columnWidth : .03, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : 30,
					items : [{
								fieldLabel : "% ",
								labelSeparator : '',
								anchor : "100%"
							}]
				}, {
					columnWidth : .2, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : 70,
					items : [{
							xtype : 'numberfield',
							fieldLabel : '租赁费率',
							allowBlank : false,
							anchor : '100%',
							fieldClass : 'field-align',
							decimalPrecision : 8,
							readOnly:this.isAllReadOnly,//add by gao
							name : 'slSuperviseRecord.continuationRate',
							listeners : {
								scope : this,
								'change' : function(nf){
									this.getCmpByName('slSuperviseRecord.continuationRateNew').setValue(nf.getValue())
								}
							}
						},{
							xtype : 'hidden',
							name : 'slSuperviseRecord.continuationRateNew'
						}]
				}, {
					columnWidth : .03, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : 30,
					items : [{
								fieldLabel : "%",
								labelSeparator : '',
								anchor : "100%"
							}]
				}, {
					columnWidth : 1,
					layout : 'column',
					items : [{
								columnWidth : .1, // 该列在整行中所占的百分比
								layout : "form", // 从上往下的布局
								labelWidth : 100,
								labelAlign : 'right',
								items : [{
											fieldLabel : "还款方式 ",
											allowBlank : false,
											fieldClass : 'field-align',
											// html :
											// "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;计息周期:",
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
									name : 'f1',
									id : "jixifs1" + this.idDefinition,
									inputValue : false,
									anchor : "100%",
									disabled : this.isAllReadOnly,
									listeners : {
										scope : this,
										check : function(obj, checked) {
											var flag = Ext.getCmp("jixifs1"
													+ this.idDefinition)
													.getValue();
											if (flag == true) {
												this
														.getCmpByName('slSuperviseRecord.accrualtype')
														.setValue("sameprincipal");
												Ext.getCmp("jixizq1"
														+ this.idDefinition)
														.setDisabled(true);
												Ext.getCmp("jixizq2"
														+ this.idDefinition)
														.setDisabled(true);
												Ext.getCmp("jixizq3"
														+ this.idDefinition)
														.setDisabled(true);
												Ext.getCmp("jixizq4"
														+ this.idDefinition)
														.setDisabled(true);

												Ext.getCmp("jixizq6"
														+ this.idDefinition)
														.setDisabled(true);
												this
														.getCmpByName('slSuperviseRecord.dayOfEveryPeriod')
														.setDisabled(true);
												// this.getCmpByName('slSmallloanProject.dayOfEveryPeriod').setValue("");

												Ext.getCmp("jixizq2"
														+ this.idDefinition)
														.setValue(true);
												Ext.getCmp("jixizq1"
														+ this.idDefinition)
														.setValue(false);
												this
														.getCmpByName('slSuperviseRecord.payaccrualType')
														.setValue("monthPay");
												this
														.getCmpByName('slSuperviseRecord.payintentPeriod')
														.setDisabled(false);

												this.getCmpByName('mqyhzj1')
														.hide()
												this.getCmpByName('mqyhzj2')
														.hide()
												Ext.getCmp("jixifs2"
														+ this.idDefinition)
														.setValue(false);
												Ext.getCmp("jixifs5"
														+ this.idDefinition)
														.setValue(false);
												Ext.getCmp("jixifs3"
														+ this.idDefinition)
														.setValue(false);
												Ext.getCmp("jixifs6"
														+ this.idDefinition)
														.setValue(false);
												Ext.getCmp("jixifs7"
														+ this.idDefinition)
														.setValue(false);
												/*Ext.getCmp("jixifs8"
														+ this.idDefinition)
														.setValue(false);*/
												Ext.getCmp("meiqihkrq1"
														+ this.idDefinition)
														.setDisabled(false);
												Ext.getCmp("meiqihkrq2"
														+ this.idDefinition)
														.setDisabled(false);
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
									id : "jixifs2" + this.idDefinition,
									inputValue : false,
									disabled : this.isAllReadOnly,
									listeners : {
										scope : this,
										check : function(obj, checked) {
											var flag = Ext.getCmp("jixifs2"
													+ this.idDefinition)
													.getValue();
											if (flag == true) {
												this
														.getCmpByName('slSuperviseRecord.accrualtype')
														.setValue("sameprincipalandInterest");
												Ext.getCmp("jixizq1"
														+ this.idDefinition)
														.setDisabled(true);
												Ext.getCmp("jixizq2"
														+ this.idDefinition)
														.setDisabled(true);
												Ext.getCmp("jixizq3"
														+ this.idDefinition)
														.setDisabled(true);
												Ext.getCmp("jixizq4"
														+ this.idDefinition)
														.setDisabled(true);

												Ext.getCmp("jixizq6"
														+ this.idDefinition)
														.setDisabled(true);
												this
														.getCmpByName('slSuperviseRecord.dayOfEveryPeriod')
														.setDisabled(true);
												// this.getCmpByName('slSmallloanProject.dayOfEveryPeriod').setValue("");

												Ext.getCmp("jixizq2"
														+ this.idDefinition)
														.setValue(true);
												Ext.getCmp("jixizq1"
														+ this.idDefinition)
														.setValue(false);
												this
														.getCmpByName('slSuperviseRecord.payaccrualType')
														.setValue("monthPay");

												this
														.getCmpByName('slSuperviseRecord.payintentPeriod')
														.setDisabled(false);
												this.getCmpByName('mqyhzj1')
														.hide()
												this.getCmpByName('mqyhzj2')
														.hide()
												Ext.getCmp("jixifs1"
														+ this.idDefinition)
														.setValue(false);
												Ext.getCmp("jixifs5"
														+ this.idDefinition)
														.setValue(false);
												Ext.getCmp("jixifs3"
														+ this.idDefinition)
														.setValue(false);
												Ext.getCmp("jixifs6"
														+ this.idDefinition)
														.setValue(false);
												Ext.getCmp("jixifs7"
														+ this.idDefinition)
														.setValue(false);
												/*Ext.getCmp("jixifs8"
														+ this.idDefinition)
														.setValue(false);*/
												Ext.getCmp("meiqihkrq1"
														+ this.idDefinition)
														.setDisabled(false);
												Ext.getCmp("meiqihkrq2"
														+ this.idDefinition)
														.setDisabled(false);
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
									id : "jixifs5" + this.idDefinition,
									inputValue : false,
									disabled : this.isAllReadOnly,
									listeners : {
										scope : this,
										check : function(obj, checked) {
											var flag = Ext.getCmp("jixifs5"
													+ this.idDefinition)
													.getValue();
											if (flag == true) {
												this
														.getCmpByName('slSuperviseRecord.accrualtype')
														.setValue("sameprincipalsameInterest");
												if (this.isAllReadOnly == true) {
													Ext
															.getCmp("jixizq1"
																	+ this.idDefinition)
															.setDisabled(true);
													Ext
															.getCmp("jixizq2"
																	+ this.idDefinition)
															.setDisabled(true);
													Ext
															.getCmp("jixizq3"
																	+ this.idDefinition)
															.setDisabled(true);
													Ext
															.getCmp("jixizq4"
																	+ this.idDefinition)
															.setDisabled(true);
													Ext
															.getCmp("jixizq6"
																	+ this.idDefinition)
															.setDisabled(true);
												} else {
													Ext
															.getCmp("jixizq1"
																	+ this.idDefinition)
															.setDisabled(false);
													Ext
															.getCmp("jixizq2"
																	+ this.idDefinition)
															.setDisabled(false);
													Ext
															.getCmp("jixizq3"
																	+ this.idDefinition)
															.setDisabled(false);
													Ext
															.getCmp("jixizq4"
																	+ this.idDefinition)
															.setDisabled(false);
													Ext
															.getCmp("jixizq6"
																	+ this.idDefinition)
															.setDisabled(false);
												}
												this
														.getCmpByName('slSuperviseRecord.dayOfEveryPeriod')
														.setDisabled(true);

												this
														.getCmpByName('slSuperviseRecord.payintentPeriod')
														.setDisabled(false);
												this
														.getCmpByName('slSuperviseRecord.payintentPerioDate')
														.setDisabled(true);
												this.getCmpByName('mqyhzj1')
														.hide()
												this.getCmpByName('mqyhzj2')
														.hide()
												Ext.getCmp("jixifs2"
														+ this.idDefinition)
														.setValue(false);
												Ext.getCmp("jixifs1"
														+ this.idDefinition)
														.setValue(false);
												Ext.getCmp("jixifs3"
														+ this.idDefinition)
														.setValue(false);
												Ext.getCmp("jixifs6"
														+ this.idDefinition)
														.setValue(false);
												Ext.getCmp("jixifs7"
														+ this.idDefinition)
														.setValue(false);
												/*Ext.getCmp("jixifs8"
														+ this.idDefinition)
														.setValue(false);*/
												Ext.getCmp("meiqihkrq1"
														+ this.idDefinition)
														.setDisabled(true);
												Ext.getCmp("meiqihkrq1"
														+ this.idDefinition)
														.setValue(false);
												Ext.getCmp("meiqihkrq2"
														+ this.idDefinition)
														.setDisabled(true);
												Ext.getCmp("meiqihkrq2"
														+ this.idDefinition)
														.setValue(true)

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
									boxLabel : '等额还款',
									anchor : "100%",
									name : 'f1',
									id : "jixifs7" + this.idDefinition,
									inputValue : false,
									disabled : this.isAllReadOnly,
									listeners : {
										scope : this,
										check : function(obj, checked) {
											var flag = Ext.getCmp("jixifs7"
													+ this.idDefinition)
													.getValue();
											if (flag == true) {
												this
														.getCmpByName('slSuperviseRecord.accrualtype')
														.setValue("matchingRepayment");
												if (this.isAllReadOnly == true) {
													Ext
															.getCmp("jixizq1"
																	+ this.idDefinition)
															.setDisabled(true);
													Ext
															.getCmp("jixizq2"
																	+ this.idDefinition)
															.setDisabled(true);
													Ext
															.getCmp("jixizq3"
																	+ this.idDefinition)
															.setDisabled(true);
													Ext
															.getCmp("jixizq4"
																	+ this.idDefinition)
															.setDisabled(true);
													Ext
															.getCmp("jixizq6"
																	+ this.idDefinition)
															.setDisabled(true);
												} else {
													Ext
															.getCmp("jixizq1"
																	+ this.idDefinition)
															.setDisabled(false);
													Ext
															.getCmp("jixizq2"
																	+ this.idDefinition)
															.setDisabled(false);
													Ext
															.getCmp("jixizq3"
																	+ this.idDefinition)
															.setDisabled(false);
													Ext
															.getCmp("jixizq4"
																	+ this.idDefinition)
															.setDisabled(false);
													Ext
															.getCmp("jixizq6"
																	+ this.idDefinition)
															.setDisabled(false);
												}
												this
														.getCmpByName('slSuperviseRecord.dayOfEveryPeriod')
														.setDisabled(true);
												// this.getCmpByName('slSmallloanProject.dayOfEveryPeriod').setValue("");

												this
														.getCmpByName('slSuperviseRecord.payintentPeriod')
														.setDisabled(false);
												this
														.getCmpByName('slSuperviseRecord.payintentPerioDate')
														.setDisabled(true);
												this.getCmpByName('mqyhzj1')
														.show()
												this.getCmpByName('mqyhzj2')
														.show()
												Ext.getCmp("jixifs2"
														+ this.idDefinition)
														.setValue(false);
												Ext.getCmp("jixifs1"
														+ this.idDefinition)
														.setValue(false);
												Ext.getCmp("jixifs3"
														+ this.idDefinition)
														.setValue(false);
												Ext.getCmp("jixifs6"
														+ this.idDefinition)
														.setValue(false);
												Ext.getCmp("jixifs7"
														+ this.idDefinition)
														.setValue(true);
												/*Ext.getCmp("jixifs8"
														+ this.idDefinition)
														.setValue(false);*/
												Ext.getCmp("meiqihkrq1"
														+ this.idDefinition)
														.setDisabled(false);
												Ext.getCmp("meiqihkrq2"
														+ this.idDefinition)
														.setDisabled(false);

											}
										}
									}
								}]
							}/*, {
								columnWidth : 0.08,
								labelWidth : 1,
								layout : 'form',
								items : [{
									xtype : 'radio',
									boxLabel : '等额年金',
									anchor : "100%",
									name : 'f1',
									id : "jixifs8" + this.idDefinition,
									inputValue : false,
									disabled : this.isAllReadOnly,
									listeners : {
										scope : this,
										check : function(obj, checked) {
											var flag = Ext.getCmp("jixifs8"
													+ this.idDefinition)
													.getValue();
											if (flag == true) {
												this
														.getCmpByName('flLeaseFinanceProject.accrualtype')
														.setValue("equalAnnuity");
												if (this.isAllReadOnly == true) {
													Ext
															.getCmp("jixizq1"
																	+ this.idDefinition)
															.setDisabled(true);
													Ext
															.getCmp("jixizq2"
																	+ this.idDefinition)
															.setDisabled(true);
													Ext
															.getCmp("jixizq3"
																	+ this.idDefinition)
															.setDisabled(true);
													Ext
															.getCmp("jixizq4"
																	+ this.idDefinition)
															.setDisabled(true);
													Ext
															.getCmp("jixizq6"
																	+ this.idDefinition)
															.setDisabled(true);
												} else {
													Ext
															.getCmp("jixizq1"
																	+ this.idDefinition)
															.setDisabled(false);
													Ext
															.getCmp("jixizq2"
																	+ this.idDefinition)
															.setDisabled(false);
													Ext
															.getCmp("jixizq3"
																	+ this.idDefinition)
															.setDisabled(false);
													Ext
															.getCmp("jixizq4"
																	+ this.idDefinition)
															.setDisabled(false);
													Ext
															.getCmp("jixizq6"
																	+ this.idDefinition)
															.setDisabled(false);
												}
												this
														.getCmpByName('flLeaseFinanceProject.dayOfEveryPeriod')
														.setDisabled(true);

												this
														.getCmpByName('flLeaseFinanceProject.payintentPeriod')
														.setDisabled(false);
												this
														.getCmpByName('flLeaseFinanceProject.payintentPerioDate')
														.setDisabled(true);
												this.getCmpByName('mqyhzj1')
														.hide()
												this.getCmpByName('mqyhzj2')
														.hide()
												Ext.getCmp("jixifs2"
														+ this.idDefinition)
														.setValue(false);
												Ext.getCmp("jixifs1"
														+ this.idDefinition)
														.setValue(false);
												Ext.getCmp("jixifs3"
														+ this.idDefinition)
														.setValue(false);
												Ext.getCmp("jixifs6"
														+ this.idDefinition)
														.setValue(false);
												Ext.getCmp("jixifs7"
														+ this.idDefinition)
														.setValue(false);
												Ext.getCmp("jixifs8"
														+ this.idDefinition)
														.setValue(true);
												Ext.getCmp("meiqihkrq1"
														+ this.idDefinition)
														.setDisabled(false);
												Ext.getCmp("meiqihkrq2"
														+ this.idDefinition)
														.setDisabled(false);

											}
										}
									}
								}]
							}*/, {
								columnWidth : 0.12,
								labelWidth : 1,
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
											var flag = Ext.getCmp("jixifs3"
													+ this.idDefinition)
													.getValue();
											if (flag == true) {
												this
														.getCmpByName('slSuperviseRecord.accrualtype')
														.setValue("singleInterest");
												Ext.getCmp("jixizq1"
														+ this.idDefinition)
														.setDisabled(false);
												Ext.getCmp("jixizq2"
														+ this.idDefinition)
														.setDisabled(false);
												Ext.getCmp("jixizq3"
														+ this.idDefinition)
														.setDisabled(false);
												Ext.getCmp("jixizq4"
														+ this.idDefinition)
														.setDisabled(false);
												Ext.getCmp("jixizq6"
														+ this.idDefinition)
														.setDisabled(false);
												this
														.getCmpByName('slSuperviseRecord.dayOfEveryPeriod')
														.setDisabled(false);
												this.getCmpByName('mqyhzj1')
														.hide()
												this.getCmpByName('mqyhzj2')
														.hide()
												Ext.getCmp("jixifs2"
														+ this.idDefinition)
														.setValue(false);
												Ext.getCmp("jixifs5"
														+ this.idDefinition)
														.setValue(false);
												Ext.getCmp("jixifs1"
														+ this.idDefinition)
														.setValue(false);
												Ext.getCmp("jixifs6"
														+ this.idDefinition)
														.setValue(false);
												Ext.getCmp("jixifs7"
														+ this.idDefinition)
														.setValue(false);
												/*Ext.getCmp("jixifs8"
														+ this.idDefinition)
														.setValue(false);*/
												Ext.getCmp("meiqihkrq1"
														+ this.idDefinition)
														.setDisabled(false);
												Ext.getCmp("meiqihkrq2"
														+ this.idDefinition)
														.setDisabled(false);

											}
										}
									}
								}, {
									xtype : "hidden",
									name : "slSuperviseRecord.accrualtype",
									value : "singleInterest"

								}]
							}, {
								columnWidth : 0.12,
								labelWidth : 1,
								layout : 'form',
								items : [{

									xtype : 'radio',
									boxLabel : '自定义方式',
									anchor : "100%",
									name : 'f1',
									id : "jixifs6" + this.idDefinition,
									inputValue : false,
									disabled : this.isAllReadOnly,
									listeners : {
										scope : this,
										check : function(obj, checked) {
											var flag = Ext.getCmp("jixifs6"
													+ this.idDefinition)
													.getValue();
											if (flag == true) {
												this
														.getCmpByName('slSuperviseRecord.accrualtype')
														.setValue("otherMothod");
												if (this.isAllReadOnly == true) {
													Ext
															.getCmp("jixizq1"
																	+ this.idDefinition)
															.setDisabled(true);
													Ext
															.getCmp("jixizq2"
																	+ this.idDefinition)
															.setDisabled(true);
													Ext
															.getCmp("jixizq3"
																	+ this.idDefinition)
															.setDisabled(true);
													Ext
															.getCmp("jixizq4"
																	+ this.idDefinition)
															.setDisabled(true);

													Ext
															.getCmp("jixizq6"
																	+ this.idDefinition)
															.setDisabled(true);
												} else {
													Ext
															.getCmp("jixizq1"
																	+ this.idDefinition)
															.setDisabled(false);
													Ext
															.getCmp("jixizq2"
																	+ this.idDefinition)
															.setDisabled(false);
													Ext
															.getCmp("jixizq3"
																	+ this.idDefinition)
															.setDisabled(false);
													Ext
															.getCmp("jixizq4"
																	+ this.idDefinition)
															.setDisabled(false);

													Ext
															.getCmp("jixizq6"
																	+ this.idDefinition)
															.setDisabled(false);
												}
												this
														.getCmpByName('slSuperviseRecord.dayOfEveryPeriod')
														.setDisabled(true);
												// this.getCmpByName('slSmallloanProject.dayOfEveryPeriod').setValue("");

												Ext.getCmp("jixizq2"
														+ this.idDefinition)
														.setValue(true);
												Ext.getCmp("jixizq1"
														+ this.idDefinition)
														.setValue(false);
												this
														.getCmpByName('slSuperviseRecord.payaccrualType')
														.setValue("monthPay");

												this
														.getCmpByName('slSuperviseRecord.payintentPeriod')
														.setDisabled(false);
												this.getCmpByName('mqyhzj1')
														.hide()
												this.getCmpByName('mqyhzj2')
														.hide()
												Ext.getCmp("jixifs2"
														+ this.idDefinition)
														.setValue(false);
												Ext.getCmp("jixifs5"
														+ this.idDefinition)
														.setValue(false);
												Ext.getCmp("jixifs3"
														+ this.idDefinition)
														.setValue(false);
												Ext.getCmp("jixifs1"
														+ this.idDefinition)
														.setValue(false);
												Ext.getCmp("jixifs7"
														+ this.idDefinition)
														.setValue(false);
												/*Ext.getCmp("jixifs8"
														+ this.idDefinition)
														.setValue(false);*/
												Ext.getCmp("meiqihkrq1"
														+ this.idDefinition)
														.setDisabled(false);
												Ext.getCmp("meiqihkrq2"
														+ this.idDefinition)
														.setDisabled(false);
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
								labelWidth : 100,
								labelAlign : 'right',
								items : [{
											allowBlank : false,
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
									id : "jixizq1" + this.idDefinition,
									inputValue : true,
									anchor : "100%",
									disabled : this.isAllReadOnly,
									listeners : {
										scope : this,
										check : function(obj, checked) {
											var flag = Ext.getCmp("jixizq1"
													+ this.idDefinition)
													.getValue();
											if (flag == true) {
												this
														.getCmpByName('slSuperviseRecord.payaccrualType')
														.setValue("dayPay");

												Ext.getCmp("meiqihkrq1"
														+ this.idDefinition)
														.setDisabled(true);
												Ext.getCmp("meiqihkrq1"
														+ this.idDefinition)
														.setValue(false);
												Ext.getCmp("meiqihkrq2"
														+ this.idDefinition)
														.setDisabled(true);
												Ext.getCmp("meiqihkrq2"
														+ this.idDefinition)
														.setValue(true);
												Ext.getCmp("jixizq2"
														+ this.idDefinition)
														.setValue(false);
												Ext.getCmp("jixizq3"
														+ this.idDefinition)
														.setValue(false);
												Ext.getCmp("jixizq4"
														+ this.idDefinition)
														.setValue(false);
												Ext.getCmp("jixizq6"
														+ this.idDefinition)
														.setValue(false);
												this
														.getCmpByName('slSuperviseRecord.isStartDatePay')
														.setValue("2");
												var payAccrualType = this
														.getCmpByName('slSuperviseRecord.payaccrualType')
														.getValue();
												var dayOfEveryPeriod = this
														.getCmpByName('slSuperviseRecord.dayOfEveryPeriod')
														.getValue();
												var payintentPeriod = this
														.getCmpByName('slSuperviseRecord.payintentPeriod')
														.getValue();
												var startDate = this
														.getCmpByName('slSuperviseRecord.startDate')
														.getValue();
												var intentDatePanel = this
														.getCmpByName('slSuperviseRecord.endDate');
												setIntentDate(payAccrualType,
														dayOfEveryPeriod,
														payintentPeriod,
														startDate,
														intentDatePanel)

											} else {

												Ext.getCmp("meiqihkrq1"
														+ this.idDefinition)
														.setDisabled(false);
												Ext.getCmp("meiqihkrq2"
														+ this.idDefinition)
														.setDisabled(false);
												if (Ext.getCmp("meiqihkrq1"
														+ this.idDefinition)
														.getValue() == true) {
													this
															.getCmpByName('slSuperviseRecord.payintentPerioDate')
															.setDisabled(false);
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
									name : 'z1',
									id : "jixizq2" + this.idDefinition,
									inputValue : false,
									checked : true,
									anchor : "100%",
									disabled : this.isAllReadOnly,
									listeners : {
										scope : this,
										check : function(obj, checked) {
											var flag = Ext.getCmp("jixizq2"
													+ this.idDefinition)
													.getValue();
											if (flag == true) {
												this
														.getCmpByName('slSuperviseRecord.payaccrualType')
														.setValue("monthPay");
												Ext.getCmp("jixizq1"
														+ this.idDefinition)
														.setValue(false);
												Ext.getCmp("jixizq3"
														+ this.idDefinition)
														.setValue(false);
												Ext.getCmp("jixizq4"
														+ this.idDefinition)
														.setValue(false);
												Ext.getCmp("jixizq6"
														+ this.idDefinition)
														.setValue(false);
												var payAccrualType = this
														.getCmpByName('slSuperviseRecord.payaccrualType')
														.getValue();
												var dayOfEveryPeriod = this
														.getCmpByName('slSuperviseRecord.dayOfEveryPeriod')
														.getValue();
												var payintentPeriod = this
														.getCmpByName('slSuperviseRecord.payintentPeriod')
														.getValue();
												var startDate = this
														.getCmpByName('slSuperviseRecord.startDate')
														.getValue();
												var intentDatePanel = this
														.getCmpByName('slSuperviseRecord.endDate');
												setIntentDate(payAccrualType,
														dayOfEveryPeriod,
														payintentPeriod,
														startDate,
														intentDatePanel)
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
												this
														.getCmpByName('slSuperviseRecord.payaccrualType')
														.setValue("seasonPay");
												Ext.getCmp("jixizq1"
														+ this.idDefinition)
														.setValue(false);
												Ext.getCmp("jixizq2"
														+ this.idDefinition)
														.setValue(false);
												Ext.getCmp("jixizq4"
														+ this.idDefinition)
														.setValue(false);
												Ext.getCmp("jixizq6"
														+ this.idDefinition)
														.setValue(false);
												var payAccrualType = this
														.getCmpByName('slSuperviseRecord.payaccrualType')
														.getValue();
												var dayOfEveryPeriod = this
														.getCmpByName('slSuperviseRecord.dayOfEveryPeriod')
														.getValue();
												var payintentPeriod = this
														.getCmpByName('slSuperviseRecord.payintentPeriod')
														.getValue();
												var startDate = this
														.getCmpByName('slSuperviseRecord.startDate')
														.getValue();
												var intentDatePanel = this
														.getCmpByName('slSuperviseRecord.endDate');
												setIntentDate(payAccrualType,
														dayOfEveryPeriod,
														payintentPeriod,
														startDate,
														intentDatePanel)
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
									id : "jixizq4" + this.idDefinition,
									inputValue : false,
									anchor : "100%",
									disabled : this.isAllReadOnly,
									listeners : {
										scope : this,
										check : function(obj, checked) {
											var flag = Ext.getCmp("jixizq4"
													+ this.idDefinition)
													.getValue();
											if (flag == true) {
												this
														.getCmpByName('slSuperviseRecord.payaccrualType')
														.setValue("yearPay");
												Ext.getCmp("jixizq1"
														+ this.idDefinition)
														.setValue(false);
												Ext.getCmp("jixizq3"
														+ this.idDefinition)
														.setValue(false);
												Ext.getCmp("jixizq2"
														+ this.idDefinition)
														.setValue(false);
												Ext.getCmp("jixizq6"
														+ this.idDefinition)
														.setValue(false);
												var payAccrualType = this
														.getCmpByName('slSuperviseRecord.payaccrualType')
														.getValue();
												var dayOfEveryPeriod = this
														.getCmpByName('slSuperviseRecord.dayOfEveryPeriod')
														.getValue();
												var payintentPeriod = this
														.getCmpByName('slSuperviseRecord.payintentPeriod')
														.getValue();
												var startDate = this
														.getCmpByName('slSuperviseRecord.startDate')
														.getValue();
												var intentDatePanel = this
														.getCmpByName('slSuperviseRecord.endDate');
												setIntentDate(payAccrualType,
														dayOfEveryPeriod,
														payintentPeriod,
														startDate,
														intentDatePanel)
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
									boxLabel : '自定义周期',
									name : 'z1',
									id : "jixizq6" + this.idDefinition,
									inputValue : false,
									anchor : "100%",
									disabled : this.isAllReadOnly,
									listeners : {
										scope : this,
										check : function(obj, checked) {
											var flag = Ext.getCmp("jixizq6"
													+ this.idDefinition)
													.getValue();
											if (flag == true) {
												this
														.getCmpByName('slSuperviseRecord.payaccrualType')
														.setValue("owerPay");

												this
														.getCmpByName('slSuperviseRecord.dayOfEveryPeriod')
														.setDisabled(false);

												Ext.getCmp("meiqihkrq1"
														+ this.idDefinition)
														.setDisabled(true);
												Ext.getCmp("meiqihkrq1"
														+ this.idDefinition)
														.setValue(false);
												Ext.getCmp("meiqihkrq2"
														+ this.idDefinition)
														.setDisabled(true);
												Ext.getCmp("meiqihkrq2"
														+ this.idDefinition)
														.setValue(true);
												this
														.getCmpByName('slSuperviseRecord.payintentPerioDate')
														.setDisabled(false);
												this
														.getCmpByName('slSuperviseRecord.payintentPerioDate')
														.setValue(null)
												Ext.getCmp("jixizq1"
														+ this.idDefinition)
														.setValue(false);
												Ext.getCmp("jixizq3"
														+ this.idDefinition)
														.setValue(false);
												Ext.getCmp("jixizq4"
														+ this.idDefinition)
														.setValue(false);
												Ext.getCmp("jixizq2"
														+ this.idDefinition)
														.setValue(false);

											} else {
												this
														.getCmpByName('slSuperviseRecord.dayOfEveryPeriod')
														.setDisabled(true);
												this
														.getCmpByName('slSuperviseRecord.dayOfEveryPeriod')
														.setValue("");
												if (Ext.getCmp("jixizq1"
														+ this.idDefinition)
														.getValue() == false) {
													Ext
															.getCmp("meiqihkrq1"
																	+ this.idDefinition)
															.setDisabled(false);
													Ext
															.getCmp("meiqihkrq2"
																	+ this.idDefinition)
															.setDisabled(false);
													if (Ext
															.getCmp("meiqihkrq1"
																	+ this.idDefinition)
															.getValue() == true) {
														this
																.getCmpByName('slSuperviseRecord.payintentPerioDate')
																.setDisabled(false);
													}
												}

											}
										}
									}
								}, {
									xtype : 'hidden',
									name : 'slSuperviseRecord.payaccrualType',
									value : 'monthPay'
								}]
							}, {
								columnWidth : 0.06,
								labelWidth : 1,
								layout : 'form',
								items : [{
									xtype : 'textfield',
									anchor : "100%",
									readOnly : this.isAllReadOnly,
									fieldClass : 'field-align',
									name : 'slSuperviseRecord.dayOfEveryPeriod',
									listeners : {
										scope : this,
										'change' : function() {
											var payAccrualType = this
													.getCmpByName('slSuperviseRecord.payaccrualType')
													.getValue();
											var dayOfEveryPeriod = this
													.getCmpByName('slSuperviseRecord.dayOfEveryPeriod')
													.getValue();
											var payintentPeriod = this
													.getCmpByName('slSuperviseRecord.payintentPeriod')
													.getValue();
											var startDate = this
													.getCmpByName('slSuperviseRecord.startDate')
													.getValue();
											var intentDatePanel = this
													.getCmpByName('slSuperviseRecord.endDate');
											setIntentDate(payAccrualType,
													dayOfEveryPeriod,
													payintentPeriod, startDate,
													intentDatePanel)
										}
									}
								}]
							}, {
								columnWidth : 0.07,
								labelWidth : 40,
								layout : 'form',
								items : [{
											fieldLabel : "日/期",
											labelSeparator : '',
											anchor : "100%"
										}]
							}

					]
				},{
						columnWidth : .5,
						name : "mqhkri",
						layout : "column",
						items : [{
							columnWidth : 0.278,
							labelWidth : 100,
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
										var flag = Ext.getCmp("meiqihkrq1"
												+ this.idDefinition).getValue();
										if (flag == true) {
											this
													.getCmpByName('slSuperviseRecord.isStartDatePay')
													.setValue("1");
											this
													.getCmpByName('slSuperviseRecord.payintentPerioDate')
													.setDisabled(false);
										}
									}
								}

							}, {
								xtype : "hidden",
								name : "slSuperviseRecord.isStartDatePay"

							}]
						}, {
							columnWidth : 0.132,
							labelWidth : 1,
							layout : 'form',
							items : [{
								xtype : 'textfield',
								readOnly : this.isAllReadOnly,
								name : "slSuperviseRecord.payintentPerioDate",
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
								hiddenName : "slSuperviseRecord.payintentPerioDate",
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
								boxLabel : '按实际放款日对日还款',
								name : 'q1',
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
											this
													.getCmpByName('slSuperviseRecord.isStartDatePay')
													.setValue("2");
											this
													.getCmpByName('slSuperviseRecord.payintentPerioDate')
													.setValue(null);
											this
													.getCmpByName('slSuperviseRecord.payintentPerioDate')
													.setDisabled(true);
										}
									}
								}

							}]
						}]
				
				}, {
								columnWidth : .11, // 该列在整行中所占的百分比
								layout : "form", // 从上往下的布局
								labelWidth : 120,
								labelAlign : 'right',
								items : [{
											allowBlank : false,
											fieldLabel : "租赁手续费还款周期"
										}]
							}, {
								columnWidth : 0.1,
								labelWidth : 1,
								layout : 'form',
								items : [{
									xtype : 'radio',
									boxLabel : '年',
									name : 'zl1',
									id : "zljixizq1" + this.idDefinition,
									inputValue : false,
									anchor : "100%",
									disabled : this.isAllReadOnly,
									listeners : {
										scope : this,
										check : function(obj, checked) {
											var flag = Ext.getCmp("zljixizq1"
													+ this.idDefinition)
													.getValue();
											if (flag == true) {
												this
														.getCmpByName('slSuperviseRecord.feePayaccrualType')
														.setValue("yearPay");
												Ext.getCmp("zljixizq2"
														+ this.idDefinition)
														.setValue(false);
												Ext.getCmp("zljixizq3"
														+ this.idDefinition)
														.setValue(false);

											}
										}
									}
								}]
							}, {
								columnWidth : 0.1,
								labelWidth : 1,
								layout : 'form',
								items : [{
									xtype : 'radio',
									boxLabel : '一次性收取',
									name : 'zl1',
									id : "zljixizq2" + this.idDefinition,
									inputValue : false,
									anchor : "100%",
									disabled : this.isAllReadOnly,
									checked : true,
									listeners : {
										scope : this,
										check : function(obj, checked) {
											var flag = Ext.getCmp("zljixizq2"
													+ this.idDefinition)
													.getValue();
											if (flag == true) {
												this
														.getCmpByName('slSuperviseRecord.feePayaccrualType')
														.setValue("onetime");
												Ext.getCmp("zljixizq1"
														+ this.idDefinition)
														.setValue(false);
												Ext.getCmp("zljixizq3"
														+ this.idDefinition)
														.setValue(false);

											}
										}
									}
								}]
							}, {
								columnWidth : 0.1,
								labelWidth : 1,
								layout : 'form',
								items : [{
									xtype : 'radio',
									boxLabel : '同租赁费',
									name : 'zl1',
									id : "zljixizq3" + this.idDefinition,
									inputValue : false,
									anchor : "100%",
									disabled : this.isAllReadOnly,
									listeners : {
										scope : this,
										check : function(obj, checked) {
											var flag = Ext.getCmp("zljixizq3"
													+ this.idDefinition)
													.getValue();
											if (flag == true) {
												this
														.getCmpByName('slSuperviseRecord.feePayaccrualType')
														.setValue("withRentalFee");
												Ext.getCmp("zljixizq2"
														+ this.idDefinition)
														.setValue(false);
												Ext.getCmp("zljixizq1"
														+ this.idDefinition)
														.setValue(false);

											}
										}
									}
								},{
									xtype : 'hidden',
									name : 'slSuperviseRecord.feePayaccrualType',
									value : 'onetime'
								}]
							},{
				
				}, {
					columnWidth : 1,
					layout : 'column',
					items : [{
						columnWidth : .2, // 该列在整行中所占的百分比
						layout : "form", // 从上往下的布局
						labelWidth : 100,
						items : [{
							fieldLabel : "租金期限",
							xtype : "textfield",
							fieldClass : 'field-align',
							allowBlank : false,
							readOnly : this.isAllReadOnly,
							name : "slSuperviseRecord.payintentPeriod",
							anchor : "100%",
							listeners : {
								scope : this,
								'change' : function() {
									var payAccrualType = this
											.getCmpByName('slSuperviseRecord.payaccrualType')
											.getValue();
									var dayOfEveryPeriod = this
											.getCmpByName('slSuperviseRecord.dayOfEveryPeriod')
											.getValue();
									var payintentPeriod = this
											.getCmpByName('slSuperviseRecord.payintentPeriod')
											.getValue();
									var startDate = this
											.getCmpByName('slSuperviseRecord.startDate')
											.getValue();
									var intentDatePanel = this
											.getCmpByName('slSuperviseRecord.endDate');
									setIntentDate(payAccrualType,
											dayOfEveryPeriod, payintentPeriod,
											startDate, intentDatePanel)
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
						columnWidth : .23,
						layout : 'form',
						labelWidth : 80,
						items : [{
							fieldLabel : "租赁起始日",
							xtype : "datefield",
							style : {
								imeMode : 'disabled'
							},
							name : "slSuperviseRecord.startDate",
							allowBlank : false,
							readOnly : this.isStartDateReadOnly,
							blankText : "租赁起始日不能为空，请正确填写!",
							anchor : "89%",
							format : 'Y-m-d',
							listeners : {
								scope : this,
								'change' : function(nf) {
									var payAccrualType = this
											.getCmpByName('slSuperviseRecord.payaccrualType')
											.getValue();
									var dayOfEveryPeriod = this
											.getCmpByName('slSuperviseRecord.dayOfEveryPeriod')
											.getValue();
									var payintentPeriod = this
											.getCmpByName('slSuperviseRecord.payintentPeriod')
											.getValue();
									var startDate = this
											.getCmpByName('slSuperviseRecord.startDate')
											.getValue();
									var intentDatePanel = this
											.getCmpByName('slSuperviseRecord.endDate');
									setIntentDate(payAccrualType,
											dayOfEveryPeriod, payintentPeriod,
											startDate, intentDatePanel)
								}
							}
						}]
					}, {
						columnWidth : .23,
						layout : 'form',
						labelWidth : 70,
						labelAlign : 'right',
						items : [{
									fieldLabel : "<font color='red'>*</font>租赁截止日",
									xtype : "datefield",
									style : {
										imeMode : 'disabled'
									},
									name : "slSuperviseRecord.endDate",
									readOnly : true,
									blankText : "租赁截止日不能为空，请正确填写!",
									anchor : "89%",
									format : 'Y-m-d'
								}]
					}, {
						columnWidth : .2, // 该列在整行中所占的百分比
						layout : "form", // 从上往下的布局
						labelWidth : 85,
						name : 'mqyhzj1',
						hidden : true,
						items : [{
							fieldLabel : "每期应收租金",
							xtype : "textfield",
							fieldClass : 'field-align',
//							allowBlank : true,
							readOnly : this.isAllReadOnly,
							name : "slSuperviseRecord.eachRentalReceivable",
							anchor : "100%",
							value:0
						}]
					}, {
						columnWidth : .05, // 该列在整行中所占的百分比
						layout : "form", // 从上往下的布局
						labelWidth : 20,
						name : 'mqyhzj2',
						hidden : true,
						items : [{
									fieldLabel : "元",
									labelSeparator : '',
									anchor : "100%"
								}]
					}]
				}]
			}]
		});

	},
	initComponents : function() {
	},
	cc : function() {

		// alert('ddd');
	}
});