
/**
 * 获取浏览器类型 by shendexuan
 * 
 * @return {}
 */

FinanceCalulateFinancePanel = Ext.extend(Ext.Panel, {
	layout : "form",
	autoHeight : true,
	labelAlign : 'right',
	isAllReadOnly : false,
	isDiligenceReadOnly : false,
	idDefinition:'liucheng',
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
		if (_cfg.idDefinition) {
			this.idDefinition = _cfg.idDefinition;
		}
		Ext.applyIf(this, _cfg);
		this.initComponents();
		this.idDefinition=this.projectId+this.idDefinition;
		var leftlabel = 100;
		var centerlabel = 100;
		var rightlabel = 100;
		var storepayintentPeriod="[";
		  for (var i = 1; i < 31; i++) {
				storepayintentPeriod = storepayintentPeriod + "[" + i
						+ ", " + i + "],";
			}
			storepayintentPeriod = storepayintentPeriod.substring(0,storepayintentPeriod.length - 1);
			storepayintentPeriod = storepayintentPeriod + "]";
			var obstorepayintentPeriod = Ext.decode(storepayintentPeriod);
		var setIntentDate=function(payAccrualType,dayOfEveryPeriod,payintentPeriod,startDate,intentDatePanel){
			Ext.Ajax.request({
				url : __ctxPath + "/project/getIntentDateSlSmallloanProject.do",
				method : 'POST',
				scope:this,
				params : {
					payAccrualType:payAccrualType,
					dayOfEveryPeriod:dayOfEveryPeriod,
					payintentPeriod:payintentPeriod,
					startDate:startDate
				},
				success : function(response, request) {
					obj = Ext.util.JSON.decode(response.responseText);
					intentDatePanel.setValue(obj.intentDate);
				},
				failure : function(response,request) {
					Ext.ux.Toast.msg('操作信息', '查询任务完成时限操作失败!');
				}
			});
		}
		FinanceCalulateFinancePanel.superclass.constructor.call(this, {
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
					columnWidth : .24, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : 85,
					items : [{
						xtype : "textfield",
						fieldLabel : "融资金额",
						fieldClass : 'field-align',
						name : "projectMoney1",
						readOnly : this.isAllReadOnly,
						allowNegative : false, // 允许负数
						style : {
							imeMode : 'disabled'
						},
						blankText : "融资金额不能为空，请正确填写!",
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
											.getCmpByName("flFinancingProject.projectMoney")
											.setValue(value);
								} else {

									if (value.indexOf(",") <= 0) {
										var ke = Ext.util.Format.number(value,
												'0,000.00')
										nf.setValue(ke);
										this
												.getCmpByName("flFinancingProject.projectMoney")
												.setValue(value);
									} else {
										var last = value.substring(index + 1,
												value.length);
										if (last == 0) {
											var temp = value
													.substring(0, index);
											temp = temp.replace(/,/g, "");
											this
													.getCmpByName("flFinancingProject.projectMoney")
													.setValue(temp);
													nf.setValue(Ext.util.Format.number(temp,
											            '0,000.00'))
										} else {
											var temp = value.replace(/,/g, "");
											this
													.getCmpByName("flFinancingProject.projectMoney")
													.setValue(temp);
													nf.setValue(Ext.util.Format.number(temp,
											            '0,000.00'))
										}
									}

								}
							}
						}
					}, {
						xtype : "hidden",
						name : "flFinancingProject.projectMoney",
						value : this.projectMoney
					}]
				}, {
					columnWidth : .045, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : 20,
					items : [{
								fieldLabel : "元 ",
								labelSeparator : '',
								anchor : "100%"
							}]
				}, {
					columnWidth : .22, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : 60,
					items : [{
								fieldLabel : "币种",
								xtype : "dickeycombo",
								hiddenName : 'flFinancingProject.currency',
								displayField : 'itemName',
								readOnly : this.isAllReadOnly,
								itemName : '注册资金币种', // xx代表分类名称
								allowBlank : false,
								editable : false,
								blankText : "币种不能为空，请正确填写!",
								nodeKey : 'capitalkind',
								emptyText : "请选择",
								anchor : "100%",
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
					columnWidth : .24, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : 85,
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
						hiddenName : 'flFinancingProject.dateMode'
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
						name : 'f1',
						id : "jixifs1" +this.idDefinition,
						inputValue : false,
						anchor : "100%",
						disabled : this.isAllReadOnly,
						listeners : {
							scope : this,
							check : function(obj, checked) {
								var flag = Ext.getCmp("jixifs1"
										+ this.idDefinition).getValue();
								if (flag == true) {
									this
											.getCmpByName('flFinancingProject.accrualtype')
											.setValue("sameprincipal");
											  Ext.getCmp("jixizq1"+this.idDefinition).setDisabled(true);
								      Ext.getCmp("jixizq2"+ this.idDefinition).setDisabled(true);
								        Ext.getCmp("jixizq3"+ this.idDefinition).setDisabled(true);
								          Ext.getCmp("jixizq4"+ this.idDefinition).setDisabled(true);  
								          
								           Ext.getCmp("jixizq6"+ this.idDefinition).setDisabled(true);
								           this.getCmpByName('flFinancingProject.dayOfEveryPeriod').setDisabled(true);
							 	            this.getCmpByName('flFinancingProject.dayOfEveryPeriod').setValue("");
							 	    
								              Ext.getCmp("jixizq2"+ this.idDefinition).setValue(true);
								             Ext.getCmp("jixizq1" +this.idDefinition).setValue(false);
								            this.getCmpByName('flFinancingProject.payaccrualType').setValue("monthPay");
								            
								             this.getCmpByName('flFinancingProject.payintentPeriod').setDisabled(false);
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
						name : 'f1',
						id : "jixifs2" +this.idDefinition,
						inputValue : false,
						disabled : this.isAllReadOnly,
						listeners : {
							scope : this,
							check : function(obj, checked) {
								var flag = Ext.getCmp("jixifs2"+ this.idDefinition).getValue();
								if (flag == true) {
									this.getCmpByName('flFinancingProject.accrualtype').setValue("sameprincipalandInterest");
								    Ext.getCmp("jixizq1"+ this.idDefinition).setDisabled(true);
								      Ext.getCmp("jixizq2"+ this.idDefinition).setDisabled(true);
								        Ext.getCmp("jixizq3"+ this.idDefinition).setDisabled(true);
								          Ext.getCmp("jixizq4"+ this.idDefinition).setDisabled(true);  
								           
								           Ext.getCmp("jixizq6"+ this.idDefinition).setDisabled(true);
								           this.getCmpByName('flFinancingProject.dayOfEveryPeriod').setDisabled(true);
							 	           this.getCmpByName('flFinancingProject.dayOfEveryPeriod').setValue("");
								          
							 	           Ext.getCmp("jixizq2"+ this.idDefinition).setValue(true);
								             Ext.getCmp("jixizq1"+ this.idDefinition).setValue(false);
								            this.getCmpByName('flFinancingProject.payaccrualType').setValue("monthPay");
								            
								             this.getCmpByName('flFinancingProject.payintentPeriod').setDisabled(false);
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
						name : 'f1',
						id : "jixifs5" +this.idDefinition,
						inputValue : false,
						disabled : this.isAllReadOnly,
						listeners : {
							scope : this,
							check : function(obj, checked) {
								var flag = Ext.getCmp("jixifs5"+ this.idDefinition).getValue();
								if (flag == true) {
									this.getCmpByName('flFinancingProject.accrualtype').setValue("sameprincipalsameInterest");
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
								           this.getCmpByName('flFinancingProject.dayOfEveryPeriod').setDisabled(true);
							 	           this.getCmpByName('flFinancingProject.dayOfEveryPeriod').setValue("");
								            this.getCmpByName('flFinancingProject.payaccrualType').setValue("monthPay");
								            
								             this.getCmpByName('flFinancingProject.payintentPeriod').setDisabled(false);
								             /* this.getCmpByName('mqhkri1').hide();
							                    this.getCmpByName('mqhkri').show();*/
							                     Ext.getCmp("jixifs2"+this.idDefinition).setValue(false);
							                   Ext.getCmp("jixifs1"+this.idDefinition).setValue(false);
							                   Ext.getCmp("jixifs3"+this.idDefinition).setValue(false);
							                   Ext.getCmp("jixifs6"+this.idDefinition).setValue(false);
							                   Ext.getCmp("meiqihkrq1"+this.idDefinition).setDisabled(true);
							                   Ext.getCmp("meiqihkrq2"+this.idDefinition).setDisabled(true);
							                    
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
									this.getCmpByName('flFinancingProject.accrualtype').setValue("singleInterest");
									  Ext.getCmp("jixizq1"+ this.idDefinition).setDisabled(false);
								      Ext.getCmp("jixizq2"+ this.idDefinition).setDisabled(false);
								        Ext.getCmp("jixizq3"+ this.idDefinition).setDisabled(false);
								          Ext.getCmp("jixizq4"+ this.idDefinition).setDisabled(false);  
								           Ext.getCmp("jixizq6"+ this.idDefinition).setDisabled(false);
								           this.getCmpByName('flFinancingProject.dayOfEveryPeriod').setDisabled(false);
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
						name : "flFinancingProject.accrualtype",
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
						name : 'f1',
						id : "jixifs6" +this.idDefinition,
						inputValue : false,
						disabled : this.isAllReadOnly,
						listeners : {
							scope : this,
							check : function(obj, checked) {
								var flag = Ext.getCmp("jixifs6"+ this.idDefinition).getValue();
								if (flag == true) {
									this.getCmpByName('flFinancingProject.accrualtype').setValue("otherMothod");
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
								           this.getCmpByName('flFinancingProject.dayOfEveryPeriod').setDisabled(true);
							 	           this.getCmpByName('flFinancingProject.dayOfEveryPeriod').setValue("");
								          
							 	           Ext.getCmp("jixizq2"+ this.idDefinition).setValue(true);
								             Ext.getCmp("jixizq1"+ this.idDefinition).setValue(false);
								            this.getCmpByName('flFinancingProject.payaccrualType').setValue("monthPay");
								            
								             this.getCmpByName('flFinancingProject.payintentPeriod').setDisabled(false);
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
						name : "flFinancingProject.payaccrualType",
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
									this.getCmpByName('flFinancingProject.payaccrualType').setValue("dayPay");
									
									 Ext.getCmp("meiqihkrq1"+this.idDefinition).setDisabled(true);
									 Ext.getCmp("meiqihkrq1"+ this.idDefinition).setValue(false);
									 Ext.getCmp("meiqihkrq2"+ this.idDefinition).setDisabled(true);
									 Ext.getCmp("meiqihkrq2"+ this.idDefinition).setValue(true);
									Ext.getCmp("jixizq2"+this.idDefinition).setValue(false);
									 Ext.getCmp("jixizq3"+ this.idDefinition).setValue(false);
									 Ext.getCmp("jixizq4"+ this.idDefinition).setValue(false);
									 Ext.getCmp("jixizq6"+ this.idDefinition).setValue(false);
									 	this.getCmpByName('flFinancingProject.isStartDatePay').setValue("2");
								var payAccrualType=this.getCmpByName('flFinancingProject.payaccrualType').getValue();
										var dayOfEveryPeriod=this.getCmpByName('flFinancingProject.dayOfEveryPeriod').getValue();
										var payintentPeriod=this.getCmpByName('flFinancingProject.payintentPeriod').getValue();
										var startDate=this.getCmpByName('flFinancingProject.startDate').getValue();
										var intentDatePanel=this.getCmpByName('flFinancingProject.intentDate');
										setIntentDate(payAccrualType,dayOfEveryPeriod,payintentPeriod,startDate,intentDatePanel)
								     
								     
								}else {
								       
								       
								        Ext.getCmp("meiqihkrq1"+this.idDefinition).setDisabled(false);
									 Ext.getCmp("meiqihkrq2"+ this.idDefinition).setDisabled(false);
									 if(Ext.getCmp("meiqihkrq1"+ this.idDefinition).getValue()==true){
									  this.getCmpByName('flFinancingProject.payintentPerioDate').setDisabled(false);
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
									this.getCmpByName('flFinancingProject.payaccrualType').setValue("monthPay");
									Ext.getCmp("jixizq1"+this.idDefinition).setValue(false);
									 Ext.getCmp("jixizq3"+ this.idDefinition).setValue(false);
									 Ext.getCmp("jixizq4"+ this.idDefinition).setValue(false);
									 Ext.getCmp("jixizq6"+ this.idDefinition).setValue(false);
									var payAccrualType=this.getCmpByName('flFinancingProject.payaccrualType').getValue();
										var dayOfEveryPeriod=this.getCmpByName('flFinancingProject.dayOfEveryPeriod').getValue();
										var payintentPeriod=this.getCmpByName('flFinancingProject.payintentPeriod').getValue();
										var startDate=this.getCmpByName('flFinancingProject.startDate').getValue();
										var intentDatePanel=this.getCmpByName('flFinancingProject.intentDate');
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
									this.getCmpByName('flFinancingProject.payaccrualType').setValue("seasonPay");
									Ext.getCmp("jixizq1"+this.idDefinition).setValue(false);
									 Ext.getCmp("jixizq2"+ this.idDefinition).setValue(false);
									 Ext.getCmp("jixizq4"+ this.idDefinition).setValue(false);
									 Ext.getCmp("jixizq6"+ this.idDefinition).setValue(false);
								var payAccrualType=this.getCmpByName('flFinancingProject.payaccrualType').getValue();
										var dayOfEveryPeriod=this.getCmpByName('flFinancingProject.dayOfEveryPeriod').getValue();
										var payintentPeriod=this.getCmpByName('flFinancingProject.payintentPeriod').getValue();
										var startDate=this.getCmpByName('flFinancingProject.startDate').getValue();
										var intentDatePanel=this.getCmpByName('flFinancingProject.intentDate');
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
									this.getCmpByName('flFinancingProject.payaccrualType').setValue("yearPay");
									Ext.getCmp("jixizq1"+this.idDefinition).setValue(false);
									 Ext.getCmp("jixizq3"+ this.idDefinition).setValue(false);
									 Ext.getCmp("jixizq2"+ this.idDefinition).setValue(false);
									 Ext.getCmp("jixizq6"+ this.idDefinition).setValue(false);
									var payAccrualType=this.getCmpByName('flFinancingProject.payaccrualType').getValue();
										var dayOfEveryPeriod=this.getCmpByName('flFinancingProject.dayOfEveryPeriod').getValue();
										var payintentPeriod=this.getCmpByName('flFinancingProject.payintentPeriod').getValue();
										var startDate=this.getCmpByName('flFinancingProject.startDate').getValue();
										var intentDatePanel=this.getCmpByName('flFinancingProject.intentDate');
										setIntentDate(payAccrualType,dayOfEveryPeriod,payintentPeriod,startDate,intentDatePanel)
								}
							}
						}
					}]
				}, {
					columnWidth : 0.09,
					labelWidth :1,
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
									this.getCmpByName('flFinancingProject.payaccrualType').setValue("owerPay");
									
								   this.getCmpByName('flFinancingProject.dayOfEveryPeriod').setDisabled(false);
								   
								    Ext.getCmp("meiqihkrq1"+this.idDefinition).setDisabled(true);
									 Ext.getCmp("meiqihkrq1"+ this.idDefinition).setValue(false);
									 Ext.getCmp("meiqihkrq2"+ this.idDefinition).setDisabled(true);
									 Ext.getCmp("meiqihkrq2"+ this.idDefinition).setValue(true);
									 Ext.getCmp("jixizq1"+this.idDefinition).setValue(false);
									 Ext.getCmp("jixizq3"+ this.idDefinition).setValue(false);
									 Ext.getCmp("jixizq4"+ this.idDefinition).setValue(false);
									 Ext.getCmp("jixizq2"+ this.idDefinition).setValue(false);
									 
								}else{
								 this.getCmpByName('flFinancingProject.dayOfEveryPeriod').setDisabled(true);
							 	    this.getCmpByName('flFinancingProject.dayOfEveryPeriod').setValue("");
							 	    if(Ext.getCmp("jixizq1"+ this.idDefinition).getValue()==false){
								 	     Ext.getCmp("meiqihkrq1"+this.idDefinition).setDisabled(false);
										 Ext.getCmp("meiqihkrq2"+ this.idDefinition).setDisabled(false);
										 if(Ext.getCmp("meiqihkrq1"+ this.idDefinition).getValue()==true){
										  this.getCmpByName('flFinancingProject.payintentPerioDate').setDisabled(false);
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
					 name:'flFinancingProject.dayOfEveryPeriod',
					 listeners : {
					 	scope : this,
					 	'change' : function(){
					 		var payAccrualType=this.getCmpByName('flFinancingProject.payaccrualType').getValue();
							var dayOfEveryPeriod=this.getCmpByName('flFinancingProject.dayOfEveryPeriod').getValue();
							var payintentPeriod=this.getCmpByName('flFinancingProject.payintentPeriod').getValue();
							var startDate=this.getCmpByName('flFinancingProject.startDate').getValue();
							var intentDatePanel=this.getCmpByName('flFinancingProject.intentDate');
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
					columnWidth : .24, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : 85,
					items : [{
								fieldLabel : "融资期数",
								xtype : "textfield",
								fieldClass : 'field-align',
								allowBlank : false,
								readOnly : this.isAllReadOnly,
								name : "flFinancingProject.payintentPeriod",
								anchor : "100%",
								value : this.payintentPeriod,
								listeners : {
								 	scope : this,
								 	'change' : function(){
								 		var payAccrualType=this.getCmpByName('flFinancingProject.payaccrualType').getValue();
										var dayOfEveryPeriod=this.getCmpByName('flFinancingProject.dayOfEveryPeriod').getValue();
										var payintentPeriod=this.getCmpByName('flFinancingProject.payintentPeriod').getValue();
										var startDate=this.getCmpByName('flFinancingProject.startDate').getValue();
										var intentDatePanel=this.getCmpByName('flFinancingProject.intentDate');
										setIntentDate(payAccrualType,dayOfEveryPeriod,payintentPeriod,startDate,intentDatePanel)
								 	}
								 }

							}]
				}, {
					columnWidth : .045, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : 20,
					items : [{
								fieldLabel : "期",
								labelSeparator : '',
								anchor : "100%"
							}]
				},{
					columnWidth : .22,
					layout : 'form',
					labelWidth : 80,
					items : [{
						fieldLabel : "融资开始日期",
						xtype : "datefield",
						style : {
							imeMode : 'disabled'
						},
						name : "flFinancingProject.startDate",
						allowBlank : false,
						readOnly : this.isStartDateReadOnly,
						blankText : "融资开始日期不能为空，请正确填写!",
						anchor : "100%",
						format : 'Y-m-d',
						value : this.startDate,
						listeners : {
							scope : this,
							'change' : function(nf){
								var payAccrualType=this.getCmpByName('flFinancingProject.payaccrualType').getValue();
								var dayOfEveryPeriod=this.getCmpByName('flFinancingProject.dayOfEveryPeriod').getValue();
								var payintentPeriod=this.getCmpByName('flFinancingProject.payintentPeriod').getValue();
								var startDate=this.getCmpByName('flFinancingProject.startDate').getValue();
								var intentDatePanel=this.getCmpByName('flFinancingProject.intentDate');
								setIntentDate(payAccrualType,dayOfEveryPeriod,payintentPeriod,startDate,intentDatePanel)
							}
						}
					}]
				},{
					columnWidth : .24,
					layout : 'form',
					labelWidth : 85,
					items : [{
						fieldLabel : "<font color='red'>*</font>融资截止日期",
						xtype : "datefield",
						style : {
							imeMode : 'disabled'
						},
						name : "flFinancingProject.intentDate",
						readOnly : true,
						blankText : "融资截止日期不能为空，请正确填写!",
						anchor : "100%",
						value : this.intentDate,
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
							checked : (this.isPreposePayAccrual== 0 || this.isPreposePayAccrual==null)
									? null
									: true,
							listeners : {
								scope : this,
								'check' : function(box,value){
									if(value==true){
										this.getCmpByName('flFinancingProject.isPreposePayAccrual').setValue(1);
									}else{
										this.getCmpByName('flFinancingProject.isPreposePayAccrual').setValue(0);
									}
								}
							}
						},{
							xtype :'hidden',
							name : 'flFinancingProject.isPreposePayAccrual',
							value:this.isPreposePayAccrual
						}]
					},{
						columnWidth : .15, // 该列在整行中所占的百分比
						layout : "form", // 从上往下的布局
						labelWidth : 1,
						items : [{
							xtype : "checkbox",
							boxLabel : "一次性支付全部利息",
							disabled : this.isAllReadOnly,
							anchor : "100%",
							name : "isInterestByOneTimeCheck",
							// value :true
							checked : (this.isInterestByOneTime == null
									|| this.isInterestByOneTime== 0)
									? null
									: true,
							listeners : {
								scope : this,
								'check' : function(box,value){
									if(value==true){
										this.getCmpByName('flFinancingProject.isInterestByOneTime').setValue(1);
									}else{
										this.getCmpByName('flFinancingProject.isInterestByOneTime').setValue(0);
									}
								}
							}
						},{
							xtype :'hidden',
							name : 'flFinancingProject.isInterestByOneTime',
							value:this.isInterestByOneTime
						}]
					},{
					columnWidth : .45, 
					name :"mqhkri",
					layout : "column", 
					items : [ {
							columnWidth : 0.278,
							labelWidth : 76,
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
												this.getCmpByName('flFinancingProject.isStartDatePay').setValue("1");
												this.getCmpByName('flFinancingProject.payintentPerioDate').setDisabled(false);
											}
										}
									}

									}, {
										xtype : "hidden",
										name : "flFinancingProject.isStartDatePay"

									}]
						}, {
							columnWidth : 0.132,
							labelWidth : 1,
							layout : 'form',
							items : [{
										xtype : 'textfield',
										readOnly : this.isAllReadOnly,
										name : "flFinancingProject.payintentPerioDate",
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
										hiddenName : "flFinancingProject.payintentPerioDate",
										value : this.payintentPerioDate,
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
										boxLabel : '按实际融资日还款',
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
												this.getCmpByName('flFinancingProject.isStartDatePay').setValue("2");
												this.getCmpByName('flFinancingProject.payintentPerioDate').setValue(null);
														this.getCmpByName('flFinancingProject.payintentPerioDate').setDisabled(true);
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
						columnWidth :.1,
						layout : 'form',
						labelWidth : 85,
						labelAlign : 'right',
						items : [{
							fieldLabel : '融资利率',
							allowBlank : false
						}]
					},{
						columnWidth : .07,
						layout : 'form',
						labelWidth : 60,
						labelAlign : 'right',
						items : [{
							fieldLabel : "年化利率",
							labelSeparator : ''
						}]
					},{
						columnWidth : .08,
						layout : 'form',
						items : [{
							hideLabel :true,
							xtype : 'numberfield',
							name : "flFinancingProject.yearAccrualRate",
							readOnly : this.isAllReadOnly,
							fieldClass : 'field-align',
							decimalPrecision : 8,
							anchor : '100%',
							style : {
								imeMode : 'disabled'
							},
							value : this.yearAccrualRate,
							listeners : {
								scope : this,
								'change' : function(nf){
									var dateModel=this.getCmpByName('flFinancingProject.dateMode').getValue()
									var accrualnf=this.getCmpByName('flFinancingProject.accrual')
									var dayAccrualRatenf=this.getCmpByName('flFinancingProject.dayAccrualRate')
									var sumAccrualRatenf=this.getCmpByName('flFinancingProject.sumAccrualRate')
									var startDate=this.getCmpByName('flFinancingProject.startDate').getValue()
									var intentDate=this.getCmpByName('flFinancingProject.intentDate').getValue()
									accrualnf.setValue(nf.getValue()/12)
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
						columnWidth : .05,
						layout : 'form',
						labelWidth : 20,
						labelAlign : 'left',
						items : [{
							fieldLabel : "%",
							labelSeparator : '',
							anchor : "100%"
						}]
					},{
						columnWidth : .07,
						layout : 'form',
						labelWidth : 60,
						labelAlgin : 'right',
						items : [{
							fieldLabel : "月化利率",
							labelSeparator : ''
						}]
					},{
						columnWidth : .08,
						layout : 'form',
						items : [{
							hideLabel :true,
							xtype : 'numberfield',
							name : "flFinancingProject.accrual",
							readOnly : this.isAllReadOnly,
							fieldClass : 'field-align',
							style : {
								imeMode : 'disabled'
							},
							anchor : '100%',
							decimalPrecision : 8,
							value :this.accrual,
							listeners : {
								scope : this,
								'change' : function(nf){
									var dateModel=this.getCmpByName('flFinancingProject.dateMode').getValue()
									var yearAccrualRatenf=this.getCmpByName('flFinancingProject.yearAccrualRate')
									var dayAccrualRatenf=this.getCmpByName('flFinancingProject.dayAccrualRate')
									var sumAccrualRatenf=this.getCmpByName('flFinancingProject.sumAccrualRate')
									var startDate=this.getCmpByName('flFinancingProject.startDate').getValue()
									var intentDate=this.getCmpByName('flFinancingProject.intentDate').getValue()
									yearAccrualRatenf.setValue(nf.getValue()*12)
									dayAccrualRatenf.setValue(nf.getValue()/30)
									if(startDate!='' && intentDate!=''){
										intentDate=Ext.util.Format.date(intentDate,'Y-m-d')
											startDate=Ext.util.Format.date(startDate,'Y-m-d')
											var days=((new Date(intentDate.substring(0,4),intentDate.substring(5,intentDate.lastIndexOf('-'))-1,intentDate.substring(intentDate.lastIndexOf('-')+1,intentDate.length))).getTime()-(new Date(startDate.substring(0,4),startDate.substring(5,startDate.lastIndexOf('-'))-1,startDate.substring(startDate.lastIndexOf('-')+1,startDate.length))).getTime())/1000/60/60/24
										sumAccrualRatenf.setValue(nf.getValue()/30*days)
									}
								}
							}
							
						}]
					},{
						columnWidth : .05,
						layout : 'form',
						labelWidth : 20,
						labelAlgin : 'left',
						items : [{
							fieldLabel : "%",
							labelSeparator : '',
							anchor : "100%"
						}]
					},{
						columnWidth : .07,
						layout : 'form',
						labelWidth : 60,
						labelAlgin : 'right',
						items : [{
							fieldLabel : "日化利率",
							labelSeparator : ''
						}]
					},{
						columnWidth : .08,
						layout : 'form',
						items : [{
							hideLabel :true,
							xtype : 'numberfield',
							name : "flFinancingProject.dayAccrualRate",
							readOnly : this.isAllReadOnly,
							fieldClass : 'field-align',
							anchor : '100%',
							decimalPrecision : 8,
							style : {
								imeMode : 'disabled'
							},
							value :this.dayAccrualRate,
							listeners : {
								scope : this,
								'change' : function(nf){
									var dateModel=this.getCmpByName('flFinancingProject.dateMode').getValue()
									var accrualnf=this.getCmpByName('flFinancingProject.accrual')
									var yearAccrualRatenf=this.getCmpByName('flFinancingProject.yearAccrualRate')
									var sumAccrualRatenf=this.getCmpByName('flFinancingProject.sumAccrualRate')
									var startDate=this.getCmpByName('flFinancingProject.startDate').getValue()
									var intentDate=this.getCmpByName('flFinancingProject.intentDate').getValue()
									accrualnf.setValue(nf.getValue()*30)
									if(null!=dateModel && dateModel=='dateModel_360'){
										yearAccrualRatenf.setValue(nf.getValue()*360)
										
									}else if(null!=dateModel && dateModel=='dateModel_365'){
										yearAccrualRatenf.setValue(nf.getValue()*365)
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
						columnWidth : .05,
						layout : 'form',
						labelWidth : 20,
						labelAlgin : 'left',
						items : [{
							fieldLabel : "%",
							labelSeparator : '',
							anchor : "100%"
						}]
					},{
						columnWidth : .07,
						layout : 'form',
						labelWidth : 60,
						labelAlgin : 'right',
						items : [{
							fieldLabel : "合计利率",
							labelSeparator : ''
						}]
					},{
						columnWidth : .08,
						layout : 'form',
						items : [{
							hideLabel :true,
							xtype : 'numberfield',
							name : "flFinancingProject.sumAccrualRate",
							readOnly : this.isAllReadOnly,
							anchor : '100%',
							decimalPrecision : 8,
							fieldClass : 'field-align',
							style : {
								imeMode : 'disabled'
							},
							value :this.sumAccrualRate,
							listeners : {
								scope : this,
								'change' : function(nf){
									var dateModel=this.getCmpByName('flFinancingProject.dateMode').getValue()
									var accrualnf=this.getCmpByName('flFinancingProject.accrual')
									var yearAccrualRatenf=this.getCmpByName('flFinancingProject.yearAccrualRate')
									var dayAccrualRatenf=this.getCmpByName('flFinancingProject.dayAccrualRate')
									var startDate=this.getCmpByName('flFinancingProject.startDate').getValue()
									var intentDate=this.getCmpByName('flFinancingProject.intentDate').getValue()
									
									if(startDate!='' && intentDate!=''){
										intentDate=Ext.util.Format.date(intentDate,'Y-m-d')
											startDate=Ext.util.Format.date(startDate,'Y-m-d')
											var days=((new Date(intentDate.substring(0,4),intentDate.substring(5,intentDate.lastIndexOf('-'))-1,intentDate.substring(intentDate.lastIndexOf('-')+1,intentDate.length))).getTime()-(new Date(startDate.substring(0,4),startDate.substring(5,startDate.lastIndexOf('-'))-1,startDate.substring(startDate.lastIndexOf('-')+1,startDate.length))).getTime())/1000/60/60/24
										var rate=nf.getValue()/days
										dayAccrualRatenf.setValue(rate);
										if(null!=dateModel && dateModel=='dateModel_360'){
											yearAccrualRatenf.setValue(rate*360)
										
										}else if(null!=dateModel && dateModel=='dateModel_365'){
											yearAccrualRatenf.setValue(rate*365)
										}
										accrualnf.setValue(rate*30)
									}
								}
							}
						}]
					},{
						columnWidth : .05,
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
						columnWidth : .35, // 该列在整行中所占的百分比
						layout : "form", // 从上往下的布局
						labelWidth : 20,
						items : [{
							fieldLabel : " ",
							labelSeparator : '',
							anchor : "100%"
						}]
					},{
						columnWidth : .1,
						layout : 'form',
						//style : 'margin-left : 400px',
						items : [{
							xtype : 'button',
							text : '试算',
							anchor : "80%",
							scope : this,
							handler : function() {
								var obj=this.ownerCt;
								var intentobj=obj.get(1);
								intentobj.autocreate();
							}
						}]
					},{
						columnWidth : .1,
						layout : 'form',
						items : [{
							xtype : 'button',
								text : '重置',
								anchor : "80%",
								scope : this,
								handler : function() {
								
								this.getCmpByName("projectMoney1").setValue("");
								this.getCmpByName("flFinancingProject.projectMoney").setValue(0);
									 this.getCmpByName("flFinancingProject.startDate").setValue(null);
									 this.getCmpByName("flFinancingProject.payintentPeriod").setValue(null);
									 this.getCmpByName("flFinancingProject.intentDate").setValue(null);
									 this.getCmpByName("flFinancingProject.accrual").setValue(0);
									  this.getCmpByName("flFinancingProject.isPreposePayAccrual").setValue(0);
									 this.getCmpByName("flFinancingProject.isInterestByOneTime").setValue(0);
									 this.getCmpByName("flFinancingProject.yearAccrualRate").setValue(0);
									  this.getCmpByName("flFinancingProject.dayAccrualRate").setValue(0);
									 this.getCmpByName("flFinancingProject.sumAccrualRate").setValue(0);
									  this.getCmpByName("flFinancingProject.dateMode").setValue(0);
									 
									 this.getCmpByName("flFinancingProject.payaccrualType").setValue("monthPay");
									 Ext.getCmp("jixizq2"+this.idDefinition).setValue(true);
									 Ext.getCmp("jixizq1"+this.idDefinition).setValue(false);
									 Ext.getCmp("jixizq3"+this.idDefinition).setValue(false);
									 Ext.getCmp("jixizq4"+this.idDefinition).setValue(false);
									 Ext.getCmp("jixizq6"+this.idDefinition).setValue(false);
									 
										
									 Ext.getCmp("meiqihkrq1"+this.idDefinition).setValue(false);
									  Ext.getCmp("meiqihkrq2"+this.idDefinition).setValue(true);
									  this.getCmpByName('flFinancingProject.payintentPerioDate').setDisabled(true);
									  this.getCmpByName('flFinancingProject.isStartDatePay').setValue("2");
									this.getCmpByName("flFinancingProject.accrualtype").setValue("singleInterest");
									Ext.getCmp("jixifs3"+this.idDefinition).setValue(true);
									Ext.getCmp("jixifs1"+this.idDefinition).setValue(false);
									Ext.getCmp("jixifs2"+this.idDefinition).setValue(false);
									Ext.getCmp("jixifs4"+this.idDefinition).setValue(false);
							}
						}]
					},{
						columnWidth : .1,
						layout : 'form',
						items : [{
							xtype : 'button',
							text : '返回数据',
							anchor : "80%",
							scope : this,
							hidden : this.isHiddenbackBtn,
							handler : function() {
					            
					             var objectfinace=this.objectfinace;
					             var projectId=this.projectId;
					             var this1=Ext.getCmp("FinanceCalculateFundIntent1");
					             
					 			var projectMoney=this.getCmpByName("flFinancingProject.projectMoney").getValue();
								 var startDate=this.getCmpByName("flFinancingProject.startDate").getValue();
								 var payaccrualType=this.getCmpByName("flFinancingProject.payaccrualType").getValue();
								 var dayOfEveryPeriod=this.getCmpByName("flFinancingProject.dayOfEveryPeriod").getValue();
								 var payintentPeriod=this.getCmpByName("flFinancingProject.payintentPeriod").getValue();
								 var isStartDatePay=this.getCmpByName("flFinancingProject.isStartDatePay").getValue();
								 var payintentPerioDate=this.getCmpByName("flFinancingProject.payintentPerioDate").getValue();
								 var intentDate=this.getCmpByName("flFinancingProject.intentDate").getValue();
								 var accrual=this.getCmpByName("flFinancingProject.accrual").getValue();
								 var accrualtype=this.getCmpByName("flFinancingProject.accrualtype").getValue();
								 var isPreposePayAccrual=this.getCmpByName("flFinancingProject.isPreposePayAccrual").getValue();
								 var isInterestByOneTime=this.getCmpByName("flFinancingProject.isInterestByOneTime").getValue();
								 var yearAccrualRate=this.getCmpByName("flFinancingProject.yearAccrualRate").getValue();
								 var dayAccrualRate=this.getCmpByName("flFinancingProject.dayAccrualRate").getValue();
								 var sumAccrualRate=this.getCmpByName("flFinancingProject.sumAccrualRate").getValue();
								 var dateMode=this.getCmpByName("flFinancingProject.dateMode").getValue();
								 
					             if(objectfinace !=null){
											 
											 objectfinace.getCmpByName("flFinancingProject.projectMoney").setValue(projectMoney);
											 objectfinace.getCmpByName("flFinancingProject.startDate").setValue(startDate);
											objectfinace.getCmpByName("flFinancingProject.dayOfEveryPeriod").setValue(dayOfEveryPeriod);
											 objectfinace.getCmpByName("flFinancingProject.payintentPerioDate").setValue(payintentPerioDate);
											objectfinace.getCmpByName("flFinancingProject.payintentPeriod").setValue(payintentPeriod);
											objectfinace.getCmpByName("flFinancingProject.accrual").setValue(accrual);
											objectfinace.getCmpByName("flFinancingProject.intentDate").setValue(intentDate);
											
											 objectfinace.getCmpByName("flFinancingProject.yearAccrualRate").setValue(yearAccrualRate);
											objectfinace.getCmpByName("flFinancingProject.dayAccrualRate").setValue(dayAccrualRate);
											 objectfinace.getCmpByName("flFinancingProject.sumAccrualRate").setValue(sumAccrualRate);
											
											
											fillDataFinanceCalulate(objectfinace,this.idDefinition1,projectId,projectMoney,payaccrualType,accrualtype,isStartDatePay,isPreposePayAccrual,isInterestByOneTime,dateMode)
											 this1.destroy() ;
					             }else{
					            	 this1.destroy() ;
					            	 
					            	 
					             }
					
												  
									      
									    
							}
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







  Ext.reg('calulateFinancePanel', calulateFinancePanel);

