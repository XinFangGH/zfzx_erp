
/**
 * 获取浏览器类型 by shendexuan
 * 
 * @return {}
 */

FinanceAlterAccrualPaneledit = Ext.extend(Ext.Panel, {
	layout : "form",
	autoHeight : true,
	labelAlign : 'right',
	idDefinition:'alterAccrual',
	constructor : function(_cfg) {
		if (_cfg == null) {
			_cfg = {};
		}
		if (_cfg.idDefinition) {
			this.idDefinition = _cfg.idDefinition;
		}
		Ext.applyIf(this, _cfg);
		this.initComponents();
		
		this.idDefinition=this.projectId+this.idDefinition;
		var surplusnotMoney=10000;
		var leftlabel = 115;
		var centerlabel = 115;
		var rightlabel = 115;
		var storepayintentPeriod="[";
		  for (var i = 1; i < 31; i++) {
				storepayintentPeriod = storepayintentPeriod + "[" + i
						+ ", " + i + "],";
			}
			storepayintentPeriod = storepayintentPeriod.substring(0,storepayintentPeriod.length - 1);
			storepayintentPeriod = storepayintentPeriod + "]";
			var obstorepayintentPeriod = Ext.decode(storepayintentPeriod);
			
			//第几期	变更
		var payintentPeriod=this.payintentPeriod;
		var obstorepayintentPeriod1 = [[null, null]];
		var	storepayintentPeriod1 = "[";
			for (var i = 1; i <= payintentPeriod; i++) {
				storepayintentPeriod1 = storepayintentPeriod1 + "['第" + i
						+ "期', " + i + "],";
			}
			storepayintentPeriod1 = storepayintentPeriod1.substring(0,
					storepayintentPeriod1.length - 1);
			storepayintentPeriod1 = storepayintentPeriod1 + "]";
			var obstorepayintentPeriod1 = Ext.decode(storepayintentPeriod1);
			FinanceAlterAccrualPaneledit.superclass.constructor.call(this, {
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
					columnWidth : .95, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : leftlabel,
					items : [{
						xtype : "textarea",
						name : "slAlterAccrualRecord.reason",
						allowBlank : false,
						readOnly : this.isAllReadOnly,
						fieldLabel : '申请原因',
						value : this.record != null
								? this.record.data.reason
								: "",
						anchor : '100%'
					}]
				}, {
					columnWidth : .05, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : 20,
					items : [{
								fieldLabel : "",
								labelSeparator : '',
								anchor : "100%"
							}]
				},{
					columnWidth : .3, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : leftlabel,
					items : [{
						xtype : "textfield",
						labelWidth : rightlabel,
						fieldLabel : "剩余本金金额",
						fieldClass : 'field-align',
						name : "projectMoney1",
						readOnly : this.isAllReadOnly,
					    value : surplusnotMoney,
						allowNegative : false, // 允许负数
						style : {
							imeMode : 'disabled'
						},
						blankText : "剩余本金金额不能为空，请正确填写!",
						allowBlank : false,
						anchor : "100%",// 控制文本框的长度
						listeners : {
							scope : this,
							afterrender : function(obj) {
								obj.on("keyup")
							},
							change : function(nf) {
                                 var value = nf.getValue();
								var continuationMoneyObj = nf.nextSibling();
								var continuationMoneyObjintivalue = continuationMoneyObj
										.getValue();
								var index = value.indexOf(".");
								if (index <= 0) { // 如果第一次输入 没有进行格式化
									nf.setValue(Ext.util.Format.number(value,
											'0,000.00'))
									continuationMoneyObj.setValue(value);
								} else {

									if (value.indexOf(",") <= 0) {
										var ke = Ext.util.Format.number(value,
												'0,000.00')
										nf.setValue(ke);
										continuationMoneyObj.setValue(value);
									} else {
										var last = value.substring(index + 1,
												value.length);
										if (last == 0) {
											var temp = value
													.substring(0, index);
											temp = temp.replace(/,/g, "");
											continuationMoneyObj.setValue(temp);
										} else {
											var temp = value.replace(/,/g, "");
											continuationMoneyObj.setValue(temp);
										}
									}

								}
									var continuationMoneyObj1 = nf.nextSibling()
										.nextSibling();
								if (continuationMoneyObj.getValue() > surplusnotMoney) {
									Ext.Msg.alert("", "不能大于未还本金金额");
									continuationMoneyObj1
											.setValue(surplusnotMoney);
									continuationMoneyObj.setValue(0);
									nf.setValue(0);
								} else {

									continuationMoneyObj1
											.setValue(Ext.util.Format
													.number(
															surplusnotMoney
																	- continuationMoneyObj
																			.getValue(),
															'0,000.00'));
								}
							}
						}
					}, {
						xtype : "hidden",
						value : surplusnotMoney,
						name : "slAlterAccrualRecord.alterProjectMoney"
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
					columnWidth : .295, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : centerlabel+21,
					items : [{
						xtype : 'combo',
						mode : 'local',
						displayField : 'name',
						valueField : 'id',
						editable : false,
						width : 70,
						store : new Ext.data.SimpleStore({
									fields : ["name", "id"],
									data : obstorepayintentPeriod1
								}),
						triggerAction : "all",
						fieldLabel : "<font color='red'>*</font>变更开始期数",
						readOnly : this.isAllReadOnly,
						hiddenName : "slAlterAccrualRecord.alterpayintentPeriod",
						name : "slAlterAccrualRecord.alterpayintentPeriod",
						anchor : '100%',
						value : this.record != null
								? this.record.data.prepayintentPeriod
								: null
					}]
				}, {
					columnWidth : .355, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : rightlabel,
					items : [ ]
				},{
				columnWidth : 1,
					layout:'column',
					items : [ {
					columnWidth : .13, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : leftlabel,
					items : [{
						 fieldLabel : "计息周期 ",
						// fieldClass : 'field-align',
						//html : "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;计息周期:",
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
								var flag = Ext.getCmp("jixizq1"	+ this.idDefinition).getValue();
								if (flag == true) {
									this.getCmpByName('slAlterAccrualRecord.payaccrualType').setValue("dayPay");
									
									 Ext.getCmp("meiqihkrq1"+this.idDefinition).setDisabled(true);
									 Ext.getCmp("meiqihkrq1"+ this.idDefinition).setValue(false);
									 Ext.getCmp("meiqihkrq2"+ this.idDefinition).setDisabled(true);
									 Ext.getCmp("meiqihkrq2"+ this.idDefinition).setValue(true);
								
									 	this.getCmpByName('slAlterAccrualRecord.isStartDatePay').setValue("2");
								     
								     
								}else {
								       
								       
								        Ext.getCmp("meiqihkrq1"+this.idDefinition).setDisabled(false);
									 Ext.getCmp("meiqihkrq2"+ this.idDefinition).setDisabled(false);
									 if(Ext.getCmp("meiqihkrq1"+ this.idDefinition).getValue()==true){
									  this.getCmpByName('slAlterAccrualRecord.payintentPerioDate').setDisabled(false);
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
								if (flag == true) {
									this.getCmpByName('slAlterAccrualRecord.payaccrualType').setValue("monthPay");
									
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
										+this.idDefinition).getValue();
								if (flag == true) {
									this.getCmpByName('slAlterAccrualRecord.payaccrualType').setValue("seasonPay");
								
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
								var flag = Ext.getCmp("jixizq4"
										+ this.idDefinition).getValue();
								if (flag == true) {
									this.getCmpByName('slAlterAccrualRecord.payaccrualType').setValue("yearPay");
									
								}
							}
						}
					}, {
						xtype : "hidden",
						name : "slAlterAccrualRecord.payaccrualType",

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
								var flag = Ext.getCmp("jixizq6"
										+ this.idDefinition).getValue();
								if (flag == true) {
									this.getCmpByName('slAlterAccrualRecord.payaccrualType').setValue("owerPay");
									
								   this.getCmpByName('slAlterAccrualRecord.dayOfEveryPeriod').setDisabled(false);
								   
								    Ext.getCmp("meiqihkrq1"+this.idDefinition).setDisabled(true);
									 Ext.getCmp("meiqihkrq1"+ this.idDefinition).setValue(false);
									 Ext.getCmp("meiqihkrq2"+ this.idDefinition).setDisabled(true);
									 Ext.getCmp("meiqihkrq2"+ this.idDefinition).setValue(true);
								}else{
								 this.getCmpByName('slAlterAccrualRecord.dayOfEveryPeriod').setDisabled(true);
							 	    this.getCmpByName('slAlterAccrualRecord.dayOfEveryPeriod').setValue("");
							 	    if(Ext.getCmp("jixizq1"+ this.idDefinition).getValue()==false){
								 	     Ext.getCmp("meiqihkrq1"+this.idDefinition).setDisabled(false);
										 Ext.getCmp("meiqihkrq2"+ this.idDefinition).setDisabled(false);
										 if(Ext.getCmp("meiqihkrq1"+ this.idDefinition).getValue()==true){
										  this.getCmpByName('slAlterAccrualRecord.payintentPerioDate').setDisabled(false);
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
					 xtype:'textfield',
					 anchor : "100%",
				 	readOnly : this.isAllReadOnly,
				 	fieldClass : 'field-align',
					 name:'slAlterAccrualRecord.dayOfEveryPeriod'
					}
					]}, {
						columnWidth : 0.04,
						labelWidth :20,
						layout : 'form',
						items : [{
						fieldLabel : "天",
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
								fieldLabel : "贷款期数",
								xtype : "textfield",
								fieldClass : 'field-align',
								allowBlank : false,
								readOnly : this.isAllReadOnly,
								name : "payintentPeriod1",
								anchor : "100%",
								listeners : {
									scope : this,
									change : function(nf) {
							            var nfvalue=nf.getValue();
							          this.getCmpByName('slAlterAccrualRecord.payintentPeriod').setValue(nfvalue);
							      }
							   }

							},{
								xtype : "hidden",
								name : "slAlterAccrualRecord.payintentPeriod"

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
					name :"mqhkri",
					layout : "column", 
					items : [ {
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
												this.getCmpByName('slAlterAccrualRecord.isStartDatePay').setValue("1");
												this.getCmpByName('slAlterAccrualRecord.payintentPerioDate').setDisabled(false);
											}
										}
									}

									}, {
										xtype : "hidden",
										name : "slAlterAccrualRecord.isStartDatePay"

									}]
						}, {
							columnWidth : 0.132,
							labelWidth : 1,
							layout : 'form',
							items : [{
										xtype : 'textfield',
										readOnly : this.isAllReadOnly,
										name : "slAlterAccrualRecord.payintentPerioDate",
										xtype : 'combo',
										mode : 'local',
										displayField : 'name',
										valueField : 'id',
										editable : true,
										store : new Ext.data.SimpleStore({
													fields : ["name", "id"],
													data : obstorepayintentPeriod
												}),
										triggerAction : "all",
										hiddenName : "slAlterAccrualRecord.payintentPerioDate",
										fieldLabel : "",
										anchor : '100%'
									}]
						}, {
							columnWidth : 0.12,
							labelWidth :45,
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
											var flag = Ext.getCmp("meiqihkrq2"
													+ this.idDefinition).getValue();
											if (flag == true) {
												this.getCmpByName('slAlterAccrualRecord.isStartDatePay').setValue("2");
												this.getCmpByName('slAlterAccrualRecord.payintentPerioDate').setValue(null);
														this.getCmpByName('slAlterAccrualRecord.payintentPerioDate').setDisabled(true);
											}
										}
									}

									}]
						}]}
						, {
							columnWidth : .45, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : 140,
							name :"mqhkri1",
				//			hidden:true,
				//			hideLabel :true,
							items : [{
										fieldLabel : "<font color='red'>*</font>还款日期",
										xtype : "datefield",
										style : {
											imeMode : 'disabled'
										},
										name : "slAlterAccrualRecord.surplusEndDate",
										readOnly : this.isAllReadOnly,
										blankText : "期望还款日期不能为空，请正确填写!",
										anchor : "64.7%",
										format : 'Y-m-d'
									}]
						}, {
							columnWidth : .2, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : 13,
							items : [{
								xtype : "hidden",
								boxLabel : "是否前置付息",
								value : 0
							}]
						}]
				}, {
							columnWidth : .3, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : leftlabel,
							items : [{
										xtype : "numberfield",
										name : "slAlterAccrualRecord.accrual",
										value : slAlterAccrualRecorddata.accrual,
										fieldClass : 'field-align',
										allowBlank : false,
										readOnly : this.isAllReadOnly,
										anchor : "100%",
										fieldLabel : "贷款利率"

									}]
						}, {
							columnWidth : .05, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : 40,
							items : [{
										fieldLabel : "%/期",
										labelSeparator : '',
										anchor : "100%"
									}]
						}, {
							columnWidth : .29, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : 140,
							items : [{
								xtype : "numberfield",
								name : "slAlterAccrualRecord.managementConsultingOfRate",
								value : slAlterAccrualRecorddata.managementConsultingOfRate,
								allowBlank : false,
								fieldLabel : "管理咨询费率",
								decimalPrecision : 10,
								value : 0,
								fieldClass : 'field-align',
								style : {
									imeMode : 'disabled'
								},
								readOnly : this.isAllReadOnly,
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
							columnWidth : .05, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : 40,
							items : [{
										fieldLabel : "%/期",
										labelSeparator : '',
										anchor : "100%"
									}]
						}, {
							columnWidth : .31, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : 126,
							items : [{
								xtype : "hidden",
								name : "slAlterAccrualRecord.financeServiceOfRate",
								value : 0,
							
							}]
						},{
							columnWidth : 1,
							layout:'column',
							items : [
							 {
							columnWidth : .13, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : rightlabel,
							items : [{
								 fieldLabel : "计息方式 ",
								 fieldClass : 'field-align',
								//html : "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;计息周期:",
								anchor : "100%"
							}]
						}, {
							columnWidth : 0.12,
							labelWidth : 5,
							layout : 'form',
							items : [{
								xtype : 'radio',
								boxLabel : '等额本金',
								// fieldLabel : "计息方式",
								name : 'f1',
								id : "jixifs1" +this.idDefinition,
								inputValue : true,
								anchor : "100%",
								disabled : this.isAllReadOnly,
								listeners : {
									scope : this,
									check : function(obj, checked) {
										var flag = Ext.getCmp("jixifs1"
												+ this.idDefinition).getValue();
										if (flag == true) {
											this
													.getCmpByName('slAlterAccrualRecord.accrualtype')
													.setValue("sameprincipal");
													  Ext.getCmp("jixizq1"+this.idDefinition).setDisabled(true);
										      Ext.getCmp("jixizq2"+ this.idDefinition).setDisabled(true);
										        Ext.getCmp("jixizq3"+ this.idDefinition).setDisabled(true);
										          Ext.getCmp("jixizq4"+ this.idDefinition).setDisabled(true);  
										          Ext.getCmp("jixizq6"+ this.idDefinition).setDisabled(true);
										          this.getCmpByName('slAlterAccrualRecord.dayOfEveryPeriod').setDisabled(true);
									 	            this.getCmpByName('slAlterAccrualRecord.dayOfEveryPeriod').setValue("");
										              Ext.getCmp("jixizq2"+ this.idDefinition).setValue(true);
										             Ext.getCmp("jixizq1" +this.idDefinition).setValue(false);
										            this.getCmpByName('slAlterAccrualRecord.payaccrualType').setValue("monthPay");
										            
										             this.getCmpByName('payintentPeriod1').setDisabled(false);
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
								id : "jixifs2" +this.idDefinition,
								inputValue : false,
								disabled : this.isAllReadOnly,
								listeners : {
									scope : this,
									check : function(obj, checked) {
										var flag = Ext.getCmp("jixifs2"+ this.idDefinition).getValue();
										if (flag == true) {
											this.getCmpByName('slAlterAccrualRecord.accrualtype').setValue("sameprincipalandInterest");
										    Ext.getCmp("jixizq1"+ this.idDefinition).setDisabled(true);
										      Ext.getCmp("jixizq2"+ this.idDefinition).setDisabled(true);
										        Ext.getCmp("jixizq3"+ this.idDefinition).setDisabled(true);
										          Ext.getCmp("jixizq4"+ this.idDefinition).setDisabled(true);  
										          Ext.getCmp("jixizq6"+ this.idDefinition).setDisabled(true);
										          this.getCmpByName('slAlterAccrualRecord.dayOfEveryPeriod').setDisabled(true);
									 	           this.getCmpByName('slAlterAccrualRecord.dayOfEveryPeriod').setValue("");
										            Ext.getCmp("jixizq2"+ this.idDefinition).setValue(true);
										             Ext.getCmp("jixizq1"+ this.idDefinition).setValue(false);
										            this.getCmpByName('slAlterAccrualRecord.payaccrualType').setValue("monthPay");
										            
										             this.getCmpByName('payintentPeriod1').setDisabled(false);
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
										if (flag == true) {
											this.getCmpByName('slAlterAccrualRecord.accrualtype').setValue("singleInterest");
											  Ext.getCmp("jixizq1"+ this.idDefinition).setDisabled(false);
										      Ext.getCmp("jixizq2"+ this.idDefinition).setDisabled(false);
										        Ext.getCmp("jixizq3"+ this.idDefinition).setDisabled(false);
										          Ext.getCmp("jixizq4"+ this.idDefinition).setDisabled(false);  
										          Ext.getCmp("jixizq6"+ this.idDefinition).setDisabled(false);
										          this.getCmpByName('slAlterAccrualRecord.dayOfEveryPeriod').setDisabled(false);
										          
										          if(this.getCmpByName('slAlterAccrualRecord.payaccrualType').getValue()==""){
											          Ext.getCmp("jixizq2"+ this.idDefinition).setValue(true);
											             Ext.getCmp("jixizq1"+ this.idDefinition).setValue(false);
											            this.getCmpByName('slAlterAccrualRecord.payaccrualType').setValue("monthPay");
			                                         }
										          
										}
									}
								}
							}, {
								xtype : "hidden",
								name : "slAlterAccrualRecord.accrualtype",
								value : slAlterAccrualRecorddata.accrualtype,
								value : "singleInterest"

							}]
						}, {
							columnWidth : 0.15,
							labelWidth : 5,
							layout : 'form',
							items : [{
								xtype : 'radio',
								boxLabel : '一次性还本付息',
								name : 'f1',
								id : "jixifs4" +this.idDefinition,
								inputValue : true,
								anchor : "100%",
								disabled : this.isAllReadOnly,
								listeners : {
									scope : this,
									check : function(obj, checked) {
										var flag = Ext.getCmp("jixifs4"
												+ this.idDefinition).getValue();
										if (flag == true) {
											this
													.getCmpByName('slAlterAccrualRecord.accrualtype')
													.setValue("ontTimeAccrual");
													  Ext.getCmp("jixizq1"+this.idDefinition).setDisabled(true);
										      Ext.getCmp("jixizq2"+ this.idDefinition).setDisabled(true);
										        Ext.getCmp("jixizq3"+ this.idDefinition).setDisabled(true);
										          Ext.getCmp("jixizq4"+ this.idDefinition).setDisabled(true);  
										          
										           Ext.getCmp("jixizq6"+ this.idDefinition).setDisabled(true);
										           this.getCmpByName('slAlterAccrualRecord.dayOfEveryPeriod').setDisabled(true);
									 	            this.getCmpByName('slAlterAccrualRecord.dayOfEveryPeriod').setValue("");
									 	    
										              Ext.getCmp("jixizq6"+ this.idDefinition).setValue(false);
										             Ext.getCmp("jixizq1" +this.idDefinition).setValue(false);
										             Ext.getCmp("jixizq2" +this.idDefinition).setValue(false);
										            this.getCmpByName('slAlterAccrualRecord.payaccrualType').setValue("");
										            
										             
										            this.getCmpByName('payintentPeriod1').setValue(1);      
											   this.getCmpByName('payintentPeriod1').setDisabled(true);
											  this.getCmpByName('slAlterAccrualRecord.payintentPeriod').setValue(1);
										   this.getCmpByName('mqhkri').hide();
									       this.getCmpByName('mqhkri1').show();
									        
										}else{
											   this.getCmpByName('payintentPeriod1').setDisabled(false);
											
										   this.getCmpByName('mqhkri1').hide();
									       this.getCmpByName('mqhkri').show();
										
										}
									}
								}
							}]
						}]
						
						}

				]
			}]
		});
	},
	initComponents : function() {
	},
	cc : function() {

		// alert('ddd');
	}
});







  Ext.reg('FinanceAlterAccrualPanel', FinanceAlterAccrualPanel);

