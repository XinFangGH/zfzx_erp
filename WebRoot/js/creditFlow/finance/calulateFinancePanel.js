
/**
 * 获取浏览器类型 by shendexuan
 * 
 * @return {}
 */

calulateFinancePanel = Ext.extend(Ext.Panel, {
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
		calulateFinancePanel.superclass.constructor.call(this, {
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
						fieldLabel : "贷款金额",
						fieldClass : 'field-align',
						name : "projectMoney1",
						readOnly : this.isAllReadOnly,
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
									nf.setValue(Ext.util.Format.number(value,
											'0,000.00'))
									this
											.getCmpByName("slSmallloanProject.projectMoney")
											.setValue(value);
								} else {

									if (value.indexOf(",") <= 0) {
										var ke = Ext.util.Format.number(value,
												'0,000.00')
										nf.setValue(ke);
										this
												.getCmpByName("slSmallloanProject.projectMoney")
												.setValue(value);
									} else {
										var last = value.substring(index + 1,
												value.length);
										if (last == 0) {
											var temp = value
													.substring(0, index);
											temp = temp.replace(/,/g, "");
											this
													.getCmpByName("slSmallloanProject.projectMoney")
													.setValue(temp);
													nf.setValue(Ext.util.Format.number(temp,
											            '0,000.00'))
										} else {
											var temp = value.replace(/,/g, "");
											this
													.getCmpByName("slSmallloanProject.projectMoney")
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
						name : "slSmallloanProject.projectMoney",
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
								hiddenName : 'slSmallloanProject.currency',
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
						hiddenName : 'slSmallloanProject.dateMode'
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
											.getCmpByName('slSmallloanProject.accrualtype')
											.setValue("sameprincipal");
											  Ext.getCmp("jixizq1"+this.idDefinition).setDisabled(true);
								      Ext.getCmp("jixizq2"+ this.idDefinition).setDisabled(true);
								        Ext.getCmp("jixizq3"+ this.idDefinition).setDisabled(true);
								          Ext.getCmp("jixizq4"+ this.idDefinition).setDisabled(true);  
								          
								           Ext.getCmp("jixizq6"+ this.idDefinition).setDisabled(true);
								           this.getCmpByName('slSmallloanProject.dayOfEveryPeriod').setDisabled(true);
							 	            this.getCmpByName('slSmallloanProject.dayOfEveryPeriod').setValue("");
							 	    
								              Ext.getCmp("jixizq2"+ this.idDefinition).setValue(true);
								             Ext.getCmp("jixizq1" +this.idDefinition).setValue(false);
								            this.getCmpByName('slSmallloanProject.payaccrualType').setValue("monthPay");
								            
								             this.getCmpByName('slSmallloanProject.payintentPeriod').setDisabled(false);
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
									this.getCmpByName('slSmallloanProject.accrualtype').setValue("sameprincipalandInterest");
								    Ext.getCmp("jixizq1"+ this.idDefinition).setDisabled(true);
								      Ext.getCmp("jixizq2"+ this.idDefinition).setDisabled(true);
								        Ext.getCmp("jixizq3"+ this.idDefinition).setDisabled(true);
								          Ext.getCmp("jixizq4"+ this.idDefinition).setDisabled(true);  
								           
								           Ext.getCmp("jixizq6"+ this.idDefinition).setDisabled(true);
								           this.getCmpByName('slSmallloanProject.dayOfEveryPeriod').setDisabled(true);
							 	           this.getCmpByName('slSmallloanProject.dayOfEveryPeriod').setValue("");
								          
							 	           Ext.getCmp("jixizq2"+ this.idDefinition).setValue(true);
								             Ext.getCmp("jixizq1"+ this.idDefinition).setValue(false);
								            this.getCmpByName('slSmallloanProject.payaccrualType').setValue("monthPay");
								            
								             this.getCmpByName('slSmallloanProject.payintentPeriod').setDisabled(false);
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
									this.getCmpByName('slSmallloanProject.accrualtype').setValue("sameprincipalsameInterest");
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
								           this.getCmpByName('slSmallloanProject.dayOfEveryPeriod').setDisabled(true);
							 	           this.getCmpByName('slSmallloanProject.dayOfEveryPeriod').setValue("");
								             this.getCmpByName('slSmallloanProject.payintentPeriod').setDisabled(false);
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
									this.getCmpByName('slSmallloanProject.accrualtype').setValue("singleInterest");
									  Ext.getCmp("jixizq1"+ this.idDefinition).setDisabled(false);
								      Ext.getCmp("jixizq2"+ this.idDefinition).setDisabled(false);
								        Ext.getCmp("jixizq3"+ this.idDefinition).setDisabled(false);
								          Ext.getCmp("jixizq4"+ this.idDefinition).setDisabled(false);  
								           Ext.getCmp("jixizq6"+ this.idDefinition).setDisabled(false);
								           this.getCmpByName('slSmallloanProject.dayOfEveryPeriod').setDisabled(false);
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
						name : "slSmallloanProject.accrualtype",
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
									this.getCmpByName('slSmallloanProject.accrualtype').setValue("otherMothod");
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
								           this.getCmpByName('slSmallloanProject.dayOfEveryPeriod').setDisabled(true);
							 	           this.getCmpByName('slSmallloanProject.dayOfEveryPeriod').setValue("");
								          
							 	           Ext.getCmp("jixizq2"+ this.idDefinition).setValue(true);
								             Ext.getCmp("jixizq1"+ this.idDefinition).setValue(false);
								            this.getCmpByName('slSmallloanProject.payaccrualType').setValue("monthPay");
								            
								             this.getCmpByName('slSmallloanProject.payintentPeriod').setDisabled(false);
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
						name : "slSmallloanProject.payaccrualType",
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
									this.getCmpByName('slSmallloanProject.payaccrualType').setValue("dayPay");
									
									 Ext.getCmp("meiqihkrq1"+this.idDefinition).setDisabled(true);
									 Ext.getCmp("meiqihkrq1"+ this.idDefinition).setValue(false);
									 Ext.getCmp("meiqihkrq2"+ this.idDefinition).setDisabled(true);
									 Ext.getCmp("meiqihkrq2"+ this.idDefinition).setValue(true);
									Ext.getCmp("jixizq2"+this.idDefinition).setValue(false);
									 Ext.getCmp("jixizq3"+ this.idDefinition).setValue(false);
									 Ext.getCmp("jixizq4"+ this.idDefinition).setValue(false);
									 Ext.getCmp("jixizq6"+ this.idDefinition).setValue(false);
									 	this.getCmpByName('slSmallloanProject.isStartDatePay').setValue("2");
								var payAccrualType=this.getCmpByName('slSmallloanProject.payaccrualType').getValue();
										var dayOfEveryPeriod=this.getCmpByName('slSmallloanProject.dayOfEveryPeriod').getValue();
										var payintentPeriod=this.getCmpByName('slSmallloanProject.payintentPeriod').getValue();
										var startDate=this.getCmpByName('slSmallloanProject.startDate').getValue();
										var intentDatePanel=this.getCmpByName('slSmallloanProject.intentDate');
										setIntentDate(payAccrualType,dayOfEveryPeriod,payintentPeriod,startDate,intentDatePanel)
								     
								     
								}else {
								       
								       
								        Ext.getCmp("meiqihkrq1"+this.idDefinition).setDisabled(false);
									 Ext.getCmp("meiqihkrq2"+ this.idDefinition).setDisabled(false);
									 if(Ext.getCmp("meiqihkrq1"+ this.idDefinition).getValue()==true){
									  this.getCmpByName('slSmallloanProject.payintentPerioDate').setDisabled(false);
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
									this.getCmpByName('slSmallloanProject.payaccrualType').setValue("monthPay");
									Ext.getCmp("jixizq1"+this.idDefinition).setValue(false);
									 Ext.getCmp("jixizq3"+ this.idDefinition).setValue(false);
									 Ext.getCmp("jixizq4"+ this.idDefinition).setValue(false);
									 Ext.getCmp("jixizq6"+ this.idDefinition).setValue(false);
									var payAccrualType=this.getCmpByName('slSmallloanProject.payaccrualType').getValue();
										var dayOfEveryPeriod=this.getCmpByName('slSmallloanProject.dayOfEveryPeriod').getValue();
										var payintentPeriod=this.getCmpByName('slSmallloanProject.payintentPeriod').getValue();
										var startDate=this.getCmpByName('slSmallloanProject.startDate').getValue();
										var intentDatePanel=this.getCmpByName('slSmallloanProject.intentDate');
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
									this.getCmpByName('slSmallloanProject.payaccrualType').setValue("seasonPay");
									Ext.getCmp("jixizq1"+this.idDefinition).setValue(false);
									 Ext.getCmp("jixizq2"+ this.idDefinition).setValue(false);
									 Ext.getCmp("jixizq4"+ this.idDefinition).setValue(false);
									 Ext.getCmp("jixizq6"+ this.idDefinition).setValue(false);
								var payAccrualType=this.getCmpByName('slSmallloanProject.payaccrualType').getValue();
										var dayOfEveryPeriod=this.getCmpByName('slSmallloanProject.dayOfEveryPeriod').getValue();
										var payintentPeriod=this.getCmpByName('slSmallloanProject.payintentPeriod').getValue();
										var startDate=this.getCmpByName('slSmallloanProject.startDate').getValue();
										var intentDatePanel=this.getCmpByName('slSmallloanProject.intentDate');
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
									this.getCmpByName('slSmallloanProject.payaccrualType').setValue("yearPay");
									Ext.getCmp("jixizq1"+this.idDefinition).setValue(false);
									 Ext.getCmp("jixizq3"+ this.idDefinition).setValue(false);
									 Ext.getCmp("jixizq2"+ this.idDefinition).setValue(false);
									 Ext.getCmp("jixizq6"+ this.idDefinition).setValue(false);
									var payAccrualType=this.getCmpByName('slSmallloanProject.payaccrualType').getValue();
										var dayOfEveryPeriod=this.getCmpByName('slSmallloanProject.dayOfEveryPeriod').getValue();
										var payintentPeriod=this.getCmpByName('slSmallloanProject.payintentPeriod').getValue();
										var startDate=this.getCmpByName('slSmallloanProject.startDate').getValue();
										var intentDatePanel=this.getCmpByName('slSmallloanProject.intentDate');
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
									this.getCmpByName('slSmallloanProject.payaccrualType').setValue("owerPay");
									
								   this.getCmpByName('slSmallloanProject.dayOfEveryPeriod').setDisabled(false);
								   
								    Ext.getCmp("meiqihkrq1"+this.idDefinition).setDisabled(true);
									 Ext.getCmp("meiqihkrq1"+ this.idDefinition).setValue(false);
									 Ext.getCmp("meiqihkrq2"+ this.idDefinition).setDisabled(true);
									 Ext.getCmp("meiqihkrq2"+ this.idDefinition).setValue(true);
									 Ext.getCmp("jixizq1"+this.idDefinition).setValue(false);
									 Ext.getCmp("jixizq3"+ this.idDefinition).setValue(false);
									 Ext.getCmp("jixizq4"+ this.idDefinition).setValue(false);
									 Ext.getCmp("jixizq2"+ this.idDefinition).setValue(false);
									 
								}else{
								 this.getCmpByName('slSmallloanProject.dayOfEveryPeriod').setDisabled(true);
							 	    this.getCmpByName('slSmallloanProject.dayOfEveryPeriod').setValue("");
							 	    if(Ext.getCmp("jixizq1"+ this.idDefinition).getValue()==false){
								 	     Ext.getCmp("meiqihkrq1"+this.idDefinition).setDisabled(false);
										 Ext.getCmp("meiqihkrq2"+ this.idDefinition).setDisabled(false);
										 if(Ext.getCmp("meiqihkrq1"+ this.idDefinition).getValue()==true){
										  this.getCmpByName('slSmallloanProject.payintentPerioDate').setDisabled(false);
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
					 name:'slSmallloanProject.dayOfEveryPeriod',
					 listeners : {
					 	scope : this,
					 	'change' : function(){
					 		var payAccrualType=this.getCmpByName('slSmallloanProject.payaccrualType').getValue();
							var dayOfEveryPeriod=this.getCmpByName('slSmallloanProject.dayOfEveryPeriod').getValue();
							var payintentPeriod=this.getCmpByName('slSmallloanProject.payintentPeriod').getValue();
							var startDate=this.getCmpByName('slSmallloanProject.startDate').getValue();
							var intentDatePanel=this.getCmpByName('slSmallloanProject.intentDate');
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
								fieldLabel : "贷款期限",
								xtype : "textfield",
								fieldClass : 'field-align',
								allowBlank : false,
								readOnly : this.isAllReadOnly,
								name : "slSmallloanProject.payintentPeriod",
								anchor : "100%",
								value : this.payintentPeriod,
								listeners : {
								 	scope : this,
								 	'change' : function(){
								 		var payAccrualType=this.getCmpByName('slSmallloanProject.payaccrualType').getValue();
										var dayOfEveryPeriod=this.getCmpByName('slSmallloanProject.dayOfEveryPeriod').getValue();
										var payintentPeriod=this.getCmpByName('slSmallloanProject.payintentPeriod').getValue();
										var startDate=this.getCmpByName('slSmallloanProject.startDate').getValue();
										var intentDatePanel=this.getCmpByName('slSmallloanProject.intentDate');
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
					labelWidth : 60,
					items : [{
						fieldLabel : "放款日期",
						xtype : "datefield",
						style : {
							imeMode : 'disabled'
						},
						name : "slSmallloanProject.startDate",
						allowBlank : false,
						readOnly : this.isStartDateReadOnly,
						blankText : "放款日期不能为空，请正确填写!",
						anchor : "100%",
						format : 'Y-m-d',
						value : this.startDate,
						listeners : {
							scope : this,
							'change' : function(nf){
								var payAccrualType=this.getCmpByName('slSmallloanProject.payaccrualType').getValue();
								var dayOfEveryPeriod=this.getCmpByName('slSmallloanProject.dayOfEveryPeriod').getValue();
								var payintentPeriod=this.getCmpByName('slSmallloanProject.payintentPeriod').getValue();
								var startDate=this.getCmpByName('slSmallloanProject.startDate').getValue();
								var intentDatePanel=this.getCmpByName('slSmallloanProject.intentDate');
								setIntentDate(payAccrualType,dayOfEveryPeriod,payintentPeriod,startDate,intentDatePanel)
							}
						}
					}]
				},{
					columnWidth : .24,
					layout : 'form',
					labelWidth : 85,
					items : [{
						fieldLabel : "<font color='red'>*</font>还款日期",
						xtype : "datefield",
						style : {
							imeMode : 'disabled'
						},
						name : "slSmallloanProject.intentDate",
						readOnly : true,
						blankText : "还款日期不能为空，请正确填写!",
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
										this.getCmpByName('slSmallloanProject.isPreposePayAccrual').setValue(1);
									}else{
										this.getCmpByName('slSmallloanProject.isPreposePayAccrual').setValue(0);
									}
								}
							}
						},{
							xtype :'hidden',
							name : 'slSmallloanProject.isPreposePayAccrual',
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
										this.getCmpByName('slSmallloanProject.isInterestByOneTime').setValue(1);
									}else{
										this.getCmpByName('slSmallloanProject.isInterestByOneTime').setValue(0);
									}
								}
							}
						},{
							xtype :'hidden',
							name : 'slSmallloanProject.isInterestByOneTime',
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
												this.getCmpByName('slSmallloanProject.isStartDatePay').setValue("1");
												this.getCmpByName('slSmallloanProject.payintentPerioDate').setDisabled(false);
											}
										}
									}

									}, {
										xtype : "hidden",
										name : "slSmallloanProject.isStartDatePay"

									}]
						}, {
							columnWidth : 0.132,
							labelWidth : 1,
							layout : 'form',
							items : [{
										xtype : 'textfield',
										readOnly : this.isAllReadOnly,
										name : "slSmallloanProject.payintentPerioDate",
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
										hiddenName : "slSmallloanProject.payintentPerioDate",
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
												this.getCmpByName('slSmallloanProject.isStartDatePay').setValue("2");
												this.getCmpByName('slSmallloanProject.payintentPerioDate').setValue(null);
														this.getCmpByName('slSmallloanProject.payintentPerioDate').setDisabled(true);
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
							fieldLabel : '贷款利率',
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
							name : "slSmallloanProject.yearAccrualRate",
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
									var dateModel=this.getCmpByName('slSmallloanProject.dateMode').getValue()
									var accrualnf=this.getCmpByName('slSmallloanProject.accrual')
									var dayAccrualRatenf=this.getCmpByName('slSmallloanProject.dayAccrualRate')
									var sumAccrualRatenf=this.getCmpByName('slSmallloanProject.sumAccrualRate')
									var startDate=this.getCmpByName('slSmallloanProject.startDate').getValue()
									var intentDate=this.getCmpByName('slSmallloanProject.intentDate').getValue()
									accrualnf.setValue(nf.getValue()/12)
									if(null!=dateModel && dateModel=='dateModel_360'){
										dayAccrualRatenf.setValue(nf.getValue()/360)
										this.getCmpByName('slSmallloanProject.accrualnew').setValue(nf.getValue()/360)
										if(startDate!='' && intentDate!=''){
											intentDate=Ext.util.Format.date(intentDate,'Y-m-d')
											startDate=Ext.util.Format.date(startDate,'Y-m-d')
											var days=((new Date(intentDate.substring(0,4),intentDate.substring(5,intentDate.lastIndexOf('-'))-1,intentDate.substring(intentDate.lastIndexOf('-')+1,intentDate.length))).getTime()-(new Date(startDate.substring(0,4),startDate.substring(5,startDate.lastIndexOf('-'))-1,startDate.substring(startDate.lastIndexOf('-')+1,startDate.length))).getTime())/1000/60/60/24
											sumAccrualRatenf.setValue(nf.getValue()/360*days)
										}
									}else if(null!=dateModel && dateModel=='dateModel_365'){
										dayAccrualRatenf.setValue(nf.getValue()/365)
										this.getCmpByName('slSmallloanProject.accrualnew').setValue(nf.getValue()/365)
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
							name : "slSmallloanProject.accrual",
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
									var dateModel=this.getCmpByName('slSmallloanProject.dateMode').getValue()
									var yearAccrualRatenf=this.getCmpByName('slSmallloanProject.yearAccrualRate')
									var dayAccrualRatenf=this.getCmpByName('slSmallloanProject.dayAccrualRate')
									var sumAccrualRatenf=this.getCmpByName('slSmallloanProject.sumAccrualRate')
									var startDate=this.getCmpByName('slSmallloanProject.startDate').getValue()
									var intentDate=this.getCmpByName('slSmallloanProject.intentDate').getValue()
									yearAccrualRatenf.setValue(nf.getValue()*12)
									dayAccrualRatenf.setValue(nf.getValue()/30)
									if(startDate!='' && intentDate!=''){
										intentDate=Ext.util.Format.date(intentDate,'Y-m-d')
											startDate=Ext.util.Format.date(startDate,'Y-m-d')
											var days=((new Date(intentDate.substring(0,4),intentDate.substring(5,intentDate.lastIndexOf('-'))-1,intentDate.substring(intentDate.lastIndexOf('-')+1,intentDate.length))).getTime()-(new Date(startDate.substring(0,4),startDate.substring(5,startDate.lastIndexOf('-'))-1,startDate.substring(startDate.lastIndexOf('-')+1,startDate.length))).getTime())/1000/60/60/24
										sumAccrualRatenf.setValue(nf.getValue()/30*days)
									}
									this.getCmpByName('slSmallloanProject.accrualnew').setValue(nf.getValue()/30)
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
							name : "slSmallloanProject.dayAccrualRate",
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
									var dateModel=this.getCmpByName('slSmallloanProject.dateMode').getValue()
									var accrualnf=this.getCmpByName('slSmallloanProject.accrual')
									var yearAccrualRatenf=this.getCmpByName('slSmallloanProject.yearAccrualRate')
									var sumAccrualRatenf=this.getCmpByName('slSmallloanProject.sumAccrualRate')
									var startDate=this.getCmpByName('slSmallloanProject.startDate').getValue()
									var intentDate=this.getCmpByName('slSmallloanProject.intentDate').getValue()
									accrualnf.setValue(nf.getValue()*30)
									this.getCmpByName('slSmallloanProject.accrualnew').setValue(nf.getValue())
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
						},{
						 	xtype : 'hidden',
						 	name : 'slSmallloanProject.accrualnew',
						 	value : this.accrualnew
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
							name : "slSmallloanProject.sumAccrualRate",
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
									var dateModel=this.getCmpByName('slSmallloanProject.dateMode').getValue()
									var accrualnf=this.getCmpByName('slSmallloanProject.accrual')
									var yearAccrualRatenf=this.getCmpByName('slSmallloanProject.yearAccrualRate')
									var dayAccrualRatenf=this.getCmpByName('slSmallloanProject.dayAccrualRate')
									var startDate=this.getCmpByName('slSmallloanProject.startDate').getValue()
									var intentDate=this.getCmpByName('slSmallloanProject.intentDate').getValue()
									
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
										this.getCmpByName('slSmallloanProject.accrualnew').setValue(rate*30)
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
						columnWidth :.1,
						layout : 'form',
						labelWidth : 85,
						labelAlign : 'right',
						items : [{
							fieldLabel : '管理咨询费率',
							allowBlank :false
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
							name : "slSmallloanProject.yearManagementConsultingOfRate",
							readOnly : this.isAllReadOnly,
							fieldClass : 'field-align',
							anchor : '100%',
							decimalPrecision : 8,
							style : {
								imeMode : 'disabled'
							},
							value :this.yearManagementConsultingOfRate,
							listeners : {
								scope : this,
								'change' : function(nf){
									var dateModel=this.getCmpByName('slSmallloanProject.dateMode').getValue()
									var accrualnf=this.getCmpByName('slSmallloanProject.managementConsultingOfRate')
									var dayAccrualRatenf=this.getCmpByName('slSmallloanProject.dayManagementConsultingOfRate')
									var sumAccrualRatenf=this.getCmpByName('slSmallloanProject.sumManagementConsultingOfRate')
									var startDate=this.getCmpByName('slSmallloanProject.startDate').getValue()
									var intentDate=this.getCmpByName('slSmallloanProject.intentDate').getValue()
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
						labelAlign : 'right',
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
							name : "slSmallloanProject.managementConsultingOfRate",
							readOnly : this.isAllReadOnly,
							fieldClass : 'field-align',
							anchor : '100%',
							decimalPrecision : 8,
							style : {
								imeMode : 'disabled'
							},
							value :this.managementConsultingOfRate,
							listeners : {
								scope : this,
								'change' : function(nf){
									var dateModel=this.getCmpByName('slSmallloanProject.dateMode').getValue()
									var yearAccrualRatenf=this.getCmpByName('slSmallloanProject.yearManagementConsultingOfRate')
									var dayAccrualRatenf=this.getCmpByName('slSmallloanProject.dayManagementConsultingOfRate')
									var sumAccrualRatenf=this.getCmpByName('slSmallloanProject.sumManagementConsultingOfRate')
									var startDate=this.getCmpByName('slSmallloanProject.startDate').getValue()
									var intentDate=this.getCmpByName('slSmallloanProject.intentDate').getValue()
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
						labelAlign : 'right',
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
							name : "slSmallloanProject.dayManagementConsultingOfRate",
							readOnly : this.isAllReadOnly,
							anchor : '100%',
							decimalPrecision : 8,
							fieldClass : 'field-align',
							style : {
								imeMode : 'disabled'
							},
							value :this.dayManagementConsultingOfRate,
							listeners : {
								scope : this,
								'change' : function(nf){
									var dateModel=this.getCmpByName('slSmallloanProject.dateMode').getValue()
									var accrualnf=this.getCmpByName('slSmallloanProject.managementConsultingOfRate')
									var yearAccrualRatenf=this.getCmpByName('slSmallloanProject.yearManagementConsultingOfRate')
									var sumAccrualRatenf=this.getCmpByName('slSmallloanProject.sumManagementConsultingOfRate')
									var startDate=this.getCmpByName('slSmallloanProject.startDate').getValue()
									var intentDate=this.getCmpByName('slSmallloanProject.intentDate').getValue()
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
						labelAlign : 'right',
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
							name : "slSmallloanProject.sumManagementConsultingOfRate",
							readOnly : this.isAllReadOnly,
							decimalPrecision : 8,
							anchor : '100%',
							fieldClass : 'field-align',
							style : {
								imeMode : 'disabled'
							},
							value :this.sumManagementConsultingOfRate,
							listeners : {
								scope : this,
								'change' : function(nf){
									var dateModel=this.getCmpByName('slSmallloanProject.dateMode').getValue()
									var accrualnf=this.getCmpByName('slSmallloanProject.managementConsultingOfRate')
									var yearAccrualRatenf=this.getCmpByName('slSmallloanProject.yearManagementConsultingOfRate')
									var dayAccrualRatenf=this.getCmpByName('slSmallloanProject.dayManagementConsultingOfRate')
									var startDate=this.getCmpByName('slSmallloanProject.startDate').getValue()
									var intentDate=this.getCmpByName('slSmallloanProject.intentDate').getValue()
									
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
						columnWidth :.1,
						layout : 'form',
						labelWidth : 85,
						labelAlign : 'right',
						items : [{
							fieldLabel : '财务服务费率',
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
							name : "slSmallloanProject.yearFinanceServiceOfRate",
							readOnly : this.isAllReadOnly,
							decimalPrecision : 8,
							anchor : '100%',
							fieldClass : 'field-align',
							style : {
								imeMode : 'disabled'
							},
							value :this.yearFinanceServiceOfRate,
							listeners : {
								scope : this,
								'change' : function(nf){
									var dateModel=this.getCmpByName('slSmallloanProject.dateMode').getValue()
									var accrualnf=this.getCmpByName('slSmallloanProject.financeServiceOfRate')
									var dayAccrualRatenf=this.getCmpByName('slSmallloanProject.dayFinanceServiceOfRate')
									var sumAccrualRatenf=this.getCmpByName('slSmallloanProject.sumFinanceServiceOfRate')
									var startDate=this.getCmpByName('slSmallloanProject.startDate').getValue()
									var intentDate=this.getCmpByName('slSmallloanProject.intentDate').getValue()
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
						labelAlign : 'right',
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
							name : "slSmallloanProject.financeServiceOfRate",
							readOnly : this.isAllReadOnly,
							decimalPrecision : 8,
							anchor : '100%',
							fieldClass : 'field-align',
							style : {
								imeMode : 'disabled'
							},
							value :this.financeServiceOfRate,
							listeners : {
								scope : this,
								'change' : function(nf){
									var dateModel=this.getCmpByName('slSmallloanProject.dateMode').getValue()
									var yearAccrualRatenf=this.getCmpByName('slSmallloanProject.yearFinanceServiceOfRate')
									var dayAccrualRatenf=this.getCmpByName('slSmallloanProject.dayFinanceServiceOfRate')
									var sumAccrualRatenf=this.getCmpByName('slSmallloanProject.sumFinanceServiceOfRate')
									var startDate=this.getCmpByName('slSmallloanProject.startDate').getValue()
									var intentDate=this.getCmpByName('slSmallloanProject.intentDate').getValue()
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
						labelAlign : 'right',
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
							anchor : '100%',
							name : "slSmallloanProject.dayFinanceServiceOfRate",
							readOnly : this.isAllReadOnly,
							decimalPrecision : 8,
							fieldClass : 'field-align',
							style : {
								imeMode : 'disabled'
							},
							value :this.dayFinanceServiceOfRate,
							listeners : {
								scope : this,
								'change' : function(nf){
									var dateModel=this.getCmpByName('slSmallloanProject.dateMode').getValue()
									var accrualnf=this.getCmpByName('slSmallloanProject.financeServiceOfRate')
									var yearAccrualRatenf=this.getCmpByName('slSmallloanProject.yearFinanceServiceOfRate')
									var sumAccrualRatenf=this.getCmpByName('slSmallloanProject.sumFinanceServiceOfRate')
									var startDate=this.getCmpByName('slSmallloanProject.startDate').getValue()
									var intentDate=this.getCmpByName('slSmallloanProject.intentDate').getValue()
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
						labelAlign : 'right',
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
							name : "slSmallloanProject.sumFinanceServiceOfRate",
							readOnly : this.isAllReadOnly,
							decimalPrecision : 8,
							anchor : '100%',
							fieldClass : 'field-align',
							style : {
								imeMode : 'disabled'
							},
							value :this.sumFinanceServiceOfRate,
							listeners : {
								scope : this,
								'change' : function(nf){
									var dateModel=this.getCmpByName('slSmallloanProject.dateMode').getValue()
									var accrualnf=this.getCmpByName('slSmallloanProject.financeServiceOfRate')
									var yearAccrualRatenf=this.getCmpByName('slSmallloanProject.yearFinanceServiceOfRate')
									var dayAccrualRatenf=this.getCmpByName('slSmallloanProject.dayFinanceServiceOfRate')
									var startDate=this.getCmpByName('slSmallloanProject.startDate').getValue()
									var intentDate=this.getCmpByName('slSmallloanProject.intentDate').getValue()
									
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
						columnWidth : .1,
						layout : 'form',
						labelWidth : 85,
						labelAlign : 'right',
						items : [{
							fieldLabel : '违约金比例'
						}]
					},{
						columnWidth : .07,
						layout : 'form',
						labelWidth : 60,
						labelAlign : 'right',
						items : [{
							fieldLabel : "贷款总额",
							labelSeparator : ''
						}]
					},{
						columnWidth : .08,
						layout : 'form',
						items : [{
							xtype : "numberfield",
							name : "slSmallloanProject.breachRate",
							decimalPrecision : 3,
							readOnly : this.isAllReadOnly,
							hideLabel : true,
							fieldClass : 'field-align',
							style : {
								imeMode : 'disabled'
							},
							value :this.breachRate,
							anchor : "100%"
						}, {
							xtype : "hidden",
							name : "slSmallloanProject.projectStatus"
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
						columnWidth : .249,
						layout : 'form',
						labelWidth : 90,
						labelAlign : 'right',
						items : [{
							fieldLabel : "逾期贷款利率",
							xtype : "numberfield",
							fieldClass : 'field-align',
							name : "slSmallloanProject.overdueRateLoan",
							decimalPrecision : 3,
							allowBlank : false,
							style : {
								imeMode : 'disabled'
							},
							readOnly : this.isAllReadOnly,
							blankText : "逾	期费率不能为空，请正确填写!",
							anchor : "100%",
							value :this.overdueRateLoan,
							listeners : {
								afterrender : function(comp) {
									comp.on("keydown", function() {
											})
								}
							}

						}]
					},{
						columnWidth : .05,
						layout : 'form',
						labelWidth : 35,
						labelAlign : 'left',
						items : [{
							fieldLabel : "%/日",
							labelSeparator : '',
							anchor : "100%"
						
						}]
					},{
						columnWidth : .249,
						layout : 'form',
						labelWidth : 90,
						labelAlign : 'right',
						items : [{
							fieldLabel : '逾期罚息利率',
							xtype : "textfield",
							readOnly : this.isAllReadOnly,
							name : "slSmallloanProject.overdueRate",
							fieldClass : 'field-align',
							allowBlank : false,
							blankText : "逾期罚息利率不能为空，请正确填写!",
							decimalPrecision : 3,
							anchor : '100%',
							value :this.overdueRate
						}]
					},{
						columnWidth : .05,
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
								this.getCmpByName("slSmallloanProject.projectMoney").setValue(0);
									 this.getCmpByName("slSmallloanProject.startDate").setValue(null);
									 this.getCmpByName("slSmallloanProject.payintentPeriod").setValue(null);
									 this.getCmpByName("slSmallloanProject.intentDate").setValue(null);
									 this.getCmpByName("slSmallloanProject.accrual").setValue(0);
									 this.getCmpByName("slSmallloanProject.managementConsultingOfRate").setValue(0);
									  this.getCmpByName("slSmallloanProject.isPreposePayAccrual").setValue(0);
									 this.getCmpByName("slSmallloanProject.overdueRate").setValue(0);
									 this.getCmpByName("slSmallloanProject.overdueRateLoan").setValue(0);
									 this.getCmpByName("slSmallloanProject.isInterestByOneTime").setValue(0);
									 this.getCmpByName("slSmallloanProject.yearAccrualRate").setValue(0);
									  this.getCmpByName("slSmallloanProject.dayAccrualRate").setValue(0);
									 this.getCmpByName("slSmallloanProject.sumAccrualRate").setValue(0);
									 this.getCmpByName("slSmallloanProject.yearManagementConsultingOfRate").setValue(0);
									 this.getCmpByName("slSmallloanProject.dayManagementConsultingOfRate").setValue(0);
									 this.getCmpByName("slSmallloanProject.sumManagementConsultingOfRate").setValue(0);
									  this.getCmpByName("slSmallloanProject.yearFinanceServiceOfRate").setValue(0);
									 this.getCmpByName("slSmallloanProject.dayFinanceServiceOfRate").setValue(0);
									 this.getCmpByName("slSmallloanProject.sumFinanceServiceOfRate").setValue(0);
									 this.getCmpByName("slSmallloanProject.accrualnew").setValue(0);
									 this.getCmpByName("slSmallloanProject.financeServiceOfRate").setValue(0);
									  this.getCmpByName("slSmallloanProject.dateMode").setValue(0);
									 this.getCmpByName("slSmallloanProject.breachRate").setValue(0);
									 
									 this.getCmpByName("slSmallloanProject.payaccrualType").setValue("monthPay");
									 Ext.getCmp("jixizq2"+this.idDefinition).setValue(true);
									 Ext.getCmp("jixizq1"+this.idDefinition).setValue(false);
									 Ext.getCmp("jixizq3"+this.idDefinition).setValue(false);
									 Ext.getCmp("jixizq4"+this.idDefinition).setValue(false);
									 Ext.getCmp("jixizq6"+this.idDefinition).setValue(false);
									 
										
									 Ext.getCmp("meiqihkrq1"+this.idDefinition).setValue(false);
									  Ext.getCmp("meiqihkrq2"+this.idDefinition).setValue(true);
									  this.getCmpByName('slSmallloanProject.payintentPerioDate').setDisabled(true);
									  this.getCmpByName('slSmallloanProject.isStartDatePay').setValue("2");
									this.getCmpByName("slSmallloanProject.accrualtype").setValue("singleInterest");
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
					             var this1=Ext.getCmp("calculateFundIntentWin1");
					             
					 			var projectMoney=this.getCmpByName("slSmallloanProject.projectMoney").getValue();
								 var startDate=this.getCmpByName("slSmallloanProject.startDate").getValue();
								 var payaccrualType=this.getCmpByName("slSmallloanProject.payaccrualType").getValue();
								 var dayOfEveryPeriod=this.getCmpByName("slSmallloanProject.dayOfEveryPeriod").getValue();
								 var payintentPeriod=this.getCmpByName("slSmallloanProject.payintentPeriod").getValue();
								 var isStartDatePay=this.getCmpByName("slSmallloanProject.isStartDatePay").getValue();
								 var payintentPerioDate=this.getCmpByName("slSmallloanProject.payintentPerioDate").getValue();
								 var intentDate=this.getCmpByName("slSmallloanProject.intentDate").getValue();
								 var accrual=this.getCmpByName("slSmallloanProject.accrual").getValue();
								 var managementConsultingOfRate=this.getCmpByName("slSmallloanProject.managementConsultingOfRate").getValue();
								 var accrualtype=this.getCmpByName("slSmallloanProject.accrualtype").getValue();
								 var isPreposePayAccrual=this.getCmpByName("slSmallloanProject.isPreposePayAccrual").getValue();
								 var overdueRate=this.getCmpByName("slSmallloanProject.overdueRate").getValue();
								 var overdueRateLoan=this.getCmpByName("slSmallloanProject.overdueRateLoan").getValue();
								 var isInterestByOneTime=this.getCmpByName("slSmallloanProject.isInterestByOneTime").getValue();
								 var yearAccrualRate=this.getCmpByName("slSmallloanProject.yearAccrualRate").getValue();
								 var dayAccrualRate=this.getCmpByName("slSmallloanProject.dayAccrualRate").getValue();
								 var sumAccrualRate=this.getCmpByName("slSmallloanProject.sumAccrualRate").getValue();
								 var yearManagementConsultingOfRate=this.getCmpByName("slSmallloanProject.yearManagementConsultingOfRate").getValue();
								 var dayManagementConsultingOfRate=this.getCmpByName("slSmallloanProject.dayManagementConsultingOfRate").getValue();
								 var sumManagementConsultingOfRate=this.getCmpByName("slSmallloanProject.sumManagementConsultingOfRate").getValue();
								 var yearFinanceServiceOfRate=this.getCmpByName("slSmallloanProject.yearFinanceServiceOfRate").getValue();
								 var dayFinanceServiceOfRate=this.getCmpByName("slSmallloanProject.dayFinanceServiceOfRate").getValue();
								 var sumFinanceServiceOfRate=this.getCmpByName("slSmallloanProject.sumFinanceServiceOfRate").getValue();
								 var accrualnew=this.getCmpByName("slSmallloanProject.accrualnew").getValue();
								 var financeServiceOfRate=this.getCmpByName("slSmallloanProject.financeServiceOfRate").getValue();
								 var dateMode=this.getCmpByName("slSmallloanProject.dateMode").getValue();
								 var breachRate=this.getCmpByName("slSmallloanProject.breachRate").getValue();
								 
					             if(objectfinace !=null){
											 
											 objectfinace.getCmpByName("slSmallloanProject.projectMoney").setValue(projectMoney);
											 objectfinace.getCmpByName("slSmallloanProject.startDate").setValue(startDate);
											objectfinace.getCmpByName("slSmallloanProject.dayOfEveryPeriod").setValue(dayOfEveryPeriod);
											 objectfinace.getCmpByName("slSmallloanProject.payintentPerioDate").setValue(payintentPerioDate);
											objectfinace.getCmpByName("slSmallloanProject.payintentPeriod").setValue(payintentPeriod);
											objectfinace.getCmpByName("slSmallloanProject.accrual").setValue(accrual);
											objectfinace.getCmpByName("slSmallloanProject.intentDate").setValue(intentDate);
											objectfinace.getCmpByName("slSmallloanProject.managementConsultingOfRate").setValue(managementConsultingOfRate);
											 objectfinace.getCmpByName("slSmallloanProject.overdueRate").setValue(overdueRate);
											 objectfinace.getCmpByName("slSmallloanProject.overdueRateLoan").setValue(overdueRateLoan);
											 objectfinace.getCmpByName("slSmallloanProject.yearAccrualRate").setValue(yearAccrualRate);
											objectfinace.getCmpByName("slSmallloanProject.dayAccrualRate").setValue(dayAccrualRate);
											 objectfinace.getCmpByName("slSmallloanProject.sumAccrualRate").setValue(sumAccrualRate);
											objectfinace.getCmpByName("slSmallloanProject.yearManagementConsultingOfRate").setValue(yearManagementConsultingOfRate);
											objectfinace.getCmpByName("slSmallloanProject.dayManagementConsultingOfRate").setValue(dayManagementConsultingOfRate);
											objectfinace.getCmpByName("slSmallloanProject.sumManagementConsultingOfRate").setValue(sumManagementConsultingOfRate);
											objectfinace.getCmpByName("slSmallloanProject.yearFinanceServiceOfRate").setValue(yearFinanceServiceOfRate);
											 objectfinace.getCmpByName("slSmallloanProject.dayFinanceServiceOfRate").setValue(dayFinanceServiceOfRate);
											 objectfinace.getCmpByName("slSmallloanProject.sumFinanceServiceOfRate").setValue(sumFinanceServiceOfRate);
											 objectfinace.getCmpByName("slSmallloanProject.accrualnew").setValue(accrualnew);
											objectfinace.getCmpByName("slSmallloanProject.financeServiceOfRate").setValue(financeServiceOfRate);
											 objectfinace.getCmpByName("slSmallloanProject.breachRate").setValue(breachRate);
											
											fillDatacalulate(objectfinace,this.idDefinition1,projectId,projectMoney,payaccrualType,accrualtype,isStartDatePay,isPreposePayAccrual,isInterestByOneTime,dateMode)
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

