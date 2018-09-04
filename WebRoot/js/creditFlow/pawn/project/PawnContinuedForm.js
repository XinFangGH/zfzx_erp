/**
 * @author
 * @createtime
 * @class SlCompanyMainForm
 * @extends Ext.Window
 * @description SlCompanyMain表单
 * @company 北京互融时代软件有限公司
 */
PawnContinuedForm = Ext.extend(Ext.Panel, {
	// 构造函数
	constructor : function(_cfg) {
	
		Ext.applyIf(this, _cfg);
		this.idDefinition=this.projectId+this.idDefinition;
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
		PawnContinuedForm.superclass.constructor.call(this, {
			layout : 'form',
			autoHeight : true,
			items :[{
					layout : 'column',
					border : false,
					items : [{
						xtype : 'hidden',
						name : 'pawnContinuedManagment.projectId',
						value : this.projectId
					},{
						xtype : 'hidden',
						name : 'pawnContinuedManagment.businessType',
						value : this.businessType
					},{
						xtype : 'hidden',
						name : 'pawnContinuedManagment.continueId',
						value : this.continueId
					},{
						columnWidth : .5,
						layout : 'form',
						labelWidth : 85,
						labelAlign : 'right',
						items : [{
							xtype : 'textfield',
							fieldLabel : '当票号',
							anchor : '100%',
							readOnly : true,
							name :'phnumber'
						},{
							xtype : 'textfield',
							fieldLabel : '证件类型',
							readOnly : true,
							anchor : '100%',
							name : 'cardType'
						}]
					},{
						columnWidth : .485,
						layout : 'form',
						labelWidth : 85,
						labelAlign : 'right',
						items : [{
							xtype : 'textfield',
							fieldLabel : '客户名称',
							anchor : '100%',
							readOnly : true,
							name :'customerName'
						},{
							xtype : 'textfield',
							fieldLabel : '证件号码',
							readOnly : true,
							anchor : '100%',
							name : 'cardNumber'
						}]
					},{
						columnWidth : .5,
						layout : 'form',
						labelWidth : 85,
						labelAlign : 'right',
						items : [{
							xtype : 'textfield',
							fieldLabel : '续当凭证号',
							anchor : '100%',
							allowBlank : false,
							readOnly : this.isAllReadOnly,
							name : 'pawnContinuedManagment.continuePawnNum'
						}]
					},{
						columnWidth : .485,
						layout : 'form',
						labelWidth : 85,
						labelAlign : 'right',
						items : [{
							xtype : 'numberfield',
							fieldLabel : '续当期数',
							anchor : '100%',
							allowBlank : false,
							readOnly : this.isAllReadOnly,
							name : 'pawnContinuedManagment.payintentPeriod'
						}]
					},{
						columnWidth : .015, // 该列在整行中所占的百分比
						layout : "form", // 从上往下的布局
						labelWidth : 20,
						items : [{
							fieldLabel : "期",
							labelSeparator : '',
							anchor : "100%"
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
										this.getCmpByName('pawnContinuedManagment.payaccrualType').setValue("dayPay");
										
										 Ext.getCmp("meiqihkrq1"+this.idDefinition).setDisabled(true);
										 Ext.getCmp("meiqihkrq1"+ this.idDefinition).setValue(false);
										 Ext.getCmp("meiqihkrq2"+ this.idDefinition).setDisabled(true);
										 Ext.getCmp("meiqihkrq2"+ this.idDefinition).setValue(true);
										Ext.getCmp("jixizq2"+this.idDefinition).setValue(false);
										 Ext.getCmp("jixizq3"+ this.idDefinition).setValue(false);
										 Ext.getCmp("jixizq4"+ this.idDefinition).setValue(false);
										 Ext.getCmp("jixizq6"+ this.idDefinition).setValue(false);
										 	this.getCmpByName('pawnContinuedManagment.isStartDatePay').setValue("2");
											var payAccrualType=this.getCmpByName('pawnContinuedManagment.payaccrualType').getValue();
											var dayOfEveryPeriod=this.getCmpByName('pawnContinuedManagment.dayOfEveryPeriod').getValue();
											var payintentPeriod=this.getCmpByName('pawnContinuedManagment.payintentPeriod').getValue();
											var startDate=this.getCmpByName('pawnContinuedManagment.startDate').getValue();
											var intentDatePanel=this.getCmpByName('pawnContinuedManagment.intentDate');
											setIntentDate(payAccrualType,dayOfEveryPeriod,payintentPeriod,startDate,intentDatePanel,this)
									     
									     
									}else {
									       
									       
									        Ext.getCmp("meiqihkrq1"+this.idDefinition).setDisabled(false);
										 Ext.getCmp("meiqihkrq2"+ this.idDefinition).setDisabled(false);
										 if(Ext.getCmp("meiqihkrq1"+ this.idDefinition).getValue()==true){
										  this.getCmpByName('pawnContinuedManagment.payintentPerioDate').setDisabled(false);
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
										this.getCmpByName('pawnContinuedManagment.payaccrualType').setValue("monthPay");
										Ext.getCmp("jixizq1"+this.idDefinition).setValue(false);
										 Ext.getCmp("jixizq3"+ this.idDefinition).setValue(false);
										 Ext.getCmp("jixizq4"+ this.idDefinition).setValue(false);
										 Ext.getCmp("jixizq6"+ this.idDefinition).setValue(false);
										var payAccrualType=this.getCmpByName('pawnContinuedManagment.payaccrualType').getValue();
											var dayOfEveryPeriod=this.getCmpByName('pawnContinuedManagment.dayOfEveryPeriod').getValue();
											var payintentPeriod=this.getCmpByName('pawnContinuedManagment.payintentPeriod').getValue();
											var startDate=this.getCmpByName('pawnContinuedManagment.startDate').getValue();
											var intentDatePanel=this.getCmpByName('pawnContinuedManagment.intentDate');
											setIntentDate(payAccrualType,dayOfEveryPeriod,payintentPeriod,startDate,intentDatePanel,this)
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
										this.getCmpByName('pawnContinuedManagment.payaccrualType').setValue("seasonPay");
										Ext.getCmp("jixizq1"+this.idDefinition).setValue(false);
										 Ext.getCmp("jixizq2"+ this.idDefinition).setValue(false);
										 Ext.getCmp("jixizq4"+ this.idDefinition).setValue(false);
										 Ext.getCmp("jixizq6"+ this.idDefinition).setValue(false);
									var payAccrualType=this.getCmpByName('pawnContinuedManagment.payaccrualType').getValue();
											var dayOfEveryPeriod=this.getCmpByName('pawnContinuedManagment.dayOfEveryPeriod').getValue();
											var payintentPeriod=this.getCmpByName('pawnContinuedManagment.payintentPeriod').getValue();
											var startDate=this.getCmpByName('pawnContinuedManagment.startDate').getValue();
											var intentDatePanel=this.getCmpByName('pawnContinuedManagment.intentDate');
											setIntentDate(payAccrualType,dayOfEveryPeriod,payintentPeriod,startDate,intentDatePanel,this)
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
										this.getCmpByName('pawnContinuedManagment.payaccrualType').setValue("yearPay");
										Ext.getCmp("jixizq1"+this.idDefinition).setValue(false);
										 Ext.getCmp("jixizq3"+ this.idDefinition).setValue(false);
										 Ext.getCmp("jixizq2"+ this.idDefinition).setValue(false);
										 Ext.getCmp("jixizq6"+ this.idDefinition).setValue(false);
										var payAccrualType=this.getCmpByName('pawnContinuedManagment.payaccrualType').getValue();
											var dayOfEveryPeriod=this.getCmpByName('pawnContinuedManagment.dayOfEveryPeriod').getValue();
											var payintentPeriod=this.getCmpByName('pawnContinuedManagment.payintentPeriod').getValue();
											var startDate=this.getCmpByName('pawnContinuedManagment.startDate').getValue();
											var intentDatePanel=this.getCmpByName('pawnContinuedManagment.intentDate');
											setIntentDate(payAccrualType,dayOfEveryPeriod,payintentPeriod,startDate,intentDatePanel,this)
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
										this.getCmpByName('pawnContinuedManagment.payaccrualType').setValue("owerPay");
										
									   this.getCmpByName('pawnContinuedManagment.dayOfEveryPeriod').setDisabled(false);
									   
									    Ext.getCmp("meiqihkrq1"+this.idDefinition).setDisabled(true);
										 Ext.getCmp("meiqihkrq1"+ this.idDefinition).setValue(false);
										 Ext.getCmp("meiqihkrq2"+ this.idDefinition).setDisabled(true);
										 Ext.getCmp("meiqihkrq2"+ this.idDefinition).setValue(true);
										 this.getCmpByName('pawnContinuedManagment.payintentPerioDate').setDisabled(false);
										 this.getCmpByName('pawnContinuedManagment.payintentPerioDate').setValue(null)
										 Ext.getCmp("jixizq1"+this.idDefinition).setValue(false);
										 Ext.getCmp("jixizq3"+ this.idDefinition).setValue(false);
										 Ext.getCmp("jixizq4"+ this.idDefinition).setValue(false);
										 Ext.getCmp("jixizq2"+ this.idDefinition).setValue(false);
										 
									}else{
									 this.getCmpByName('pawnContinuedManagment.dayOfEveryPeriod').setDisabled(true);
								 	    this.getCmpByName('pawnContinuedManagment.dayOfEveryPeriod').setValue("");
								 	    if(Ext.getCmp("jixizq1"+ this.idDefinition).getValue()==false){
									 	     Ext.getCmp("meiqihkrq1"+this.idDefinition).setDisabled(false);
											 Ext.getCmp("meiqihkrq2"+ this.idDefinition).setDisabled(false);
											 if(Ext.getCmp("meiqihkrq1"+ this.idDefinition).getValue()==true){
											  this.getCmpByName('pawnContinuedManagment.payintentPerioDate').setDisabled(false);
											 }
										  }
									    
									}
								}
							}
						},{
							xtype : 'hidden',
							name : 'pawnContinuedManagment.payaccrualType',
							value :'monthPay'
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
						 name:'pawnContinuedManagment.dayOfEveryPeriod',
						 listeners : {
						 	scope : this,
						 	'change' : function(){
						 		var payAccrualType=this.getCmpByName('pawnContinuedManagment.payaccrualType').getValue();
								var dayOfEveryPeriod=this.getCmpByName('pawnContinuedManagment.dayOfEveryPeriod').getValue();
								var payintentPeriod=this.getCmpByName('pawnContinuedManagment.payintentPeriod').getValue();
								var startDate=this.getCmpByName('pawnContinuedManagment.startDate').getValue();
								var intentDatePanel=this.getCmpByName('pawnContinuedManagment.intentDate');
								setIntentDate(payAccrualType,dayOfEveryPeriod,payintentPeriod,startDate,intentDatePanel,this)
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
						
						]},{
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
												this.getCmpByName('pawnContinuedManagment.isPreposePayAccrual').setValue(1);
											}else{
												this.getCmpByName('pawnContinuedManagment.isPreposePayAccrual').setValue(0);
											}
										}
									}
								},{
									xtype :'hidden',
									name : 'pawnContinuedManagment.isPreposePayAccrual',
									value:0
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
									checked : this.record == null
											|| this.record.data.isInterestByOneTime== 0
											? null
											: true,
									listeners : {
										scope : this,
										'check' : function(box,value){
											if(value==true){
												this.getCmpByName('pawnContinuedManagment.isInterestByOneTime').setValue(1);
											}else{
												this.getCmpByName('pawnContinuedManagment.isInterestByOneTime').setValue(0);
											}
										}
									}
								},{
									xtype :'hidden',
									name : 'pawnContinuedManagment.isInterestByOneTime',
									value:0
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
														this.getCmpByName('pawnContinuedManagment.isStartDatePay').setValue("1");
														this.getCmpByName('pawnContinuedManagment.payintentPerioDate').setDisabled(false);
													}
												}
											}
		
											}, {
												xtype : "hidden",
												name : "pawnContinuedManagment.isStartDatePay",
												value : 2
		
											}]
								}, {
									columnWidth : 0.132,
									labelWidth : 1,
									layout : 'form',
									items : [{
												xtype : 'textfield',
												readOnly : this.isAllReadOnly,
												name : "pawnContinuedManagment.payintentPerioDate",
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
												hiddenName : "pawnContinuedManagment.payintentPerioDate",
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
														this.getCmpByName('pawnContinuedManagment.isStartDatePay').setValue("2");
														this.getCmpByName('pawnContinuedManagment.payintentPerioDate').setValue(null);
														this.getCmpByName('pawnContinuedManagment.payintentPerioDate').setDisabled(true);
													}
												}
											}
		
											}]
								}]
							}]
						},{
							columnWidth : .5,
							layout : 'form',
							labelWidth : 85,
							labelAlign : 'right',
							items : [{
								xtype : 'datefield',
								fieldLabel : '续当开始日期',
								format : 'Y-m-d',
								anchor : '100%',
								allowBlank : false,
								readOnly : this.isAllReadOnly,
								name : 'pawnContinuedManagment.startDate',
								listeners : {
									scope : this,
									'change' : function(nf){
										var payAccrualType=this.getCmpByName('pawnContinuedManagment.payaccrualType').getValue();
										var dayOfEveryPeriod=this.getCmpByName('pawnContinuedManagment.dayOfEveryPeriod').getValue();
										var payintentPeriod=this.getCmpByName('pawnContinuedManagment.payintentPeriod').getValue();
										var startDate=this.getCmpByName('pawnContinuedManagment.startDate').getValue();
										var intentDatePanel=this.getCmpByName('pawnContinuedManagment.intentDate');
										setIntentDate(payAccrualType,dayOfEveryPeriod,payintentPeriod,startDate,intentDatePanel)
									}
								}
							}]
						},{
							columnWidth : .485,
							layout : 'form',
							labelWidth : 85,
							labelAlign : 'right',
							items : [{
								xtype : 'datefield',
								fieldLabel : '续当到期日期',
								format : 'Y-m-d',
								anchor : '100%',
								allowBlank : false,
								readOnly : true,
								name : 'pawnContinuedManagment.intentDate'
							}]
						},{
							columnWidth : .485,
							layout : 'form',
							labelWidth : 85,
							labelAlign : 'right',
							items : [{
								xtype : 'numberfield',
								fieldLabel : '续当利率',
								anchor : '100%',
								allowBlank : false,
								readOnly : this.isAllReadOnly,
								name : 'pawnContinuedManagment.accrual'
							}]
						
						},{
							columnWidth : .015,
							layout : 'form',
							labelWidth : 20,
							labelAlgin : 'left',
							items : [{
								fieldLabel : "%",
								labelSeparator : '',
								anchor : "100%"
							
							}]
						},{
							columnWidth : .485,
							layout : 'form',
							labelWidth : 85,
							labelAlign : 'right',
							items : [{
								xtype : 'numberfield',
								fieldLabel : '续当费率',
								anchor : '100%',
								allowBlank : false,
								readOnly : this.isAllReadOnly,
								name : 'pawnContinuedManagment.monthFeeRate'
							}]
						
						},{
							columnWidth : .015,
							layout : 'form',
							labelWidth : 20,
							labelAlgin : 'left',
							items : [{
								fieldLabel : "%",
								labelSeparator : '',
								anchor : "100%"
							
							}]
						},{
							columnWidth : .5,
							layout : 'form',
							labelWidth : 85,
							labelAlign : 'right',
							items : [{
								xtype : "combo",
								triggerClass : 'x-form-search-trigger',
								hiddenName : "pawnContinuedManagment.managerId",
								editable : false,
								fieldLabel : "经办人",
								blankText : "经办人不能为空，请正确填写!",
								allowBlank : false,
								readOnly : this.isAllReadOnly,
								anchor : "100%",
								onTriggerClick : function(cc) {
									var obj = this;
									var appuerIdObj = obj.nextSibling();
									var userIds = appuerIdObj.getValue();
									var managerName=obj.nextSibling().nextSibling()
									if ("" == obj.getValue()) {
										userIds = "";
									}
									new UserDialog({
										userIds : userIds,
										userName : obj.getValue(),
										single : false,
										title : "选择经办人",
										callback : function(uId, uname) {
											obj.setValue(uId);
											obj.setRawValue(uname);
											managerName.setValue(uname);
											appuerIdObj.setValue(uId);
										}
									}).show();
								}
							}, {
								xtype : "hidden",
								value : ""
							},{
								xtype : 'hidden',
								name : 'pawnContinuedManagment.managerName'
							}]
						},{
							columnWidth : .485,
							layout : 'form',
							labelWidth : 85,
							labelAlign : 'right',
							items : [{
								xtype : 'datefield',
								fieldLabel : '受理日期',
								format : 'Y-m-d',
								anchor : '100%',
								allowBlank : false,
								readOnly : this.isAllReadOnly,
								name : 'pawnContinuedManagment.manageDate'
							}]
						},{
							columnWidth : 0.985,
							layout : 'form',
							labelWidth : 85,
							labelAlign : 'right',
							items : [{
								xtype : 'textarea',
								fieldLabel : '续当备注',
								anchor : '100%',
								readOnly : this.isAllReadOnly,
								name : 'pawnContinuedManagment.remarks'
							}]
						}]
					}]
				});
	}
	// 初始化组件
	

});
