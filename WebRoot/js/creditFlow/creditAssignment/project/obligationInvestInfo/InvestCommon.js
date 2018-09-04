InvestCommon = Ext.extend(Ext.Panel, {
	layout : "form",
	autoHeight : true,
	labelAlign : 'right',
	isAllReadOnly : false,
	idDefinition:'liucheng',
	constructor : function(_cfg) {
		if (_cfg == null) {
			_cfg = {};
		}
		if (_cfg.idDefinition) {
			this.idDefinition = _cfg.idDefinition;
		}
		if (_cfg.isAllReadOnly) {
			this.isAllReadOnly = _cfg.isAllReadOnly;
		}
		Ext.applyIf(this, _cfg);
		this.idDefinition=this.projectId+this.idDefinition;
		var leftlabel = 115;
		var centerlabel = 115;
		var rightlabel = 115;
		alert("2222");
		var storepayintentPeriod="[";
		for (var i = 1; i < 31; i++) {
			storepayintentPeriod = storepayintentPeriod + "[" + i+ ", " + i + "],";
		}
		storepayintentPeriod = storepayintentPeriod.substring(0,storepayintentPeriod.length - 1);
		storepayintentPeriod = storepayintentPeriod + "]";
		var obstorepayintentPeriod = Ext.decode(storepayintentPeriod);
		InvestCommon.superclass.constructor.call(this, {
			items : [{
				layout : "column",
				defaults : {
					anchor : '100%',
					columnWidth : 1,
					isFormField : true,
					labelWidth : 70
				},
				items:[{
							xtype : "hidden",
							name : "obObligationInvestInfo.obligationId"
					},{
						columnWidth : .4, // 该列在整行中所占的百分比
						layout : "form", // 从上往下的布局
						labelWidth : 90,
						labelAlign :'right',
						items : [{
							xtype : "textfield",
							name : "obObligationInvestInfo.obligationName",
							allowBlank : false,
							readOnly : true,
							fieldLabel : '产品名称',
							fieldClass : 'field-align',
							blankText :'系统账号不能为空，请正确填写系统账号！',
							onTriggerClick : function() {},
							anchor : '100%'
						}]
					},{
						columnWidth : .3, // 该列在整行中所占的百分比
						layout : "form", // 从上往下的布局
						labelWidth : 90,
						labelAlign :'right',
						items : [{
							xtype : "textfield",
							name : "obObligationProject.obligationNumber",
							allowBlank : false,
							readOnly : true,
							fieldLabel : '产品编号',
							blankText :'系统账号不能为空，请正确填写系统账号！',
							anchor : '100%'
							
						}]
					},{
							columnWidth : .3, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : 100,
							labelAlign :'right',
							items : [{
								xtype : 'datefield',
								fieldLabel : '自动关闭时间',
								allowBlank : false,
								//fieldClass : 'field-align',
								readOnly : true,
								name : "obObligationProject.intentDate",
								anchor : '100%',
								format : 'Y-m-d'
							}]
				
				},{
						columnWidth : .35, // 该列在整行中所占的百分比
						layout : "form", // 从上往下的布局
						labelWidth : 90,
						labelAlign :'right',
						items : [{
							xtype : "textfield",
							name : "projectMoney1",
							allowBlank : false,
							readOnly : true,
							style : {
								imeMode : 'disabled'
							},
							//fieldClass : 'field-align',
							fieldLabel : '债权金额',
							blankText :'债权金额不能为空，请正确填写债权金额！',
							anchor : '100%',
							listeners : {
								change : function(nf) {
									var value = nf.getValue();
									var continuationMoneyObj = nf.nextSibling();
									var index = value.indexOf(".");
									if (index <= 0) { // 如果第一次输入 没有进行格式化
										nf.setValue(Ext.util.Format.number(value,'0,000.00'))
										continuationMoneyObj.setValue(value);
									} else {
										if (value.indexOf(",") <= 0) {
											var ke = Ext.util.Format.number(value,'0,000.00')
											nf.setValue(ke);
											continuationMoneyObj.setValue(value);
										} else {
											var last = value.substring(index + 1,value.length);
											if (last == 0) {
												var temp = value.substring(0, index);
												temp = temp.replace(/,/g, "");
												continuationMoneyObj.setValue(temp);
											} else {
												var temp = value.replace(/,/g, "");
												continuationMoneyObj.setValue(temp);
											}
										}
									}
									
								}
							}
						},{
							xtype : "hidden",
							name : "obObligationProject.projectMoney"
							
						}]
					},{
						columnWidth : .05, // 该列在整行中所占的百分比
						layout : "form", // 从上往下的布局
						labelWidth : 30,
						items : [{
									fieldLabel : "元 ",
									labelSeparator : '',
									anchor : "90%"
								}]
				},{
						columnWidth : .25, // 该列在整行中所占的百分比
						layout : "form", // 从上往下的布局
						labelWidth : 90,
						labelAlign :'right',
						items : [{
							xtype : "textfield",
							name : "mappingMoney1",
							allowBlank : false,
							readOnly : true,
							style : {
								imeMode : 'disabled'
							},
							//fieldClass : 'field-align',
							fieldLabel : '已匹配金额',
							blankText :'债权金额不能为空，请正确填写债权金额！',
							anchor : '100%',
							value:0.00,
							listeners : {
								change : function(nf) {
									var value = nf.getValue();
									var continuationMoneyObj = nf.nextSibling();
									var index = value.indexOf(".");
									if (index <= 0) { // 如果第一次输入 没有进行格式化
										nf.setValue(Ext.util.Format.number(value,'0,000.00'))
										continuationMoneyObj.setValue(value);
									} else {
										if (value.indexOf(",") <= 0) {
											var ke = Ext.util.Format.number(value,'0,000.00')
											nf.setValue(ke);
											continuationMoneyObj.setValue(value);
										} else {
											var last = value.substring(index + 1,value.length);
											if (last == 0) {
												var temp = value.substring(0, index);
												temp = temp.replace(/,/g, "");
												continuationMoneyObj.setValue(temp);
											} else {
												var temp = value.replace(/,/g, "");
												continuationMoneyObj.setValue(temp);
											}
										}
									}
									
								}
							}
						},{
							xtype : "hidden",
							name : "obObligationProject.mappingMoney",
							value:0.00
							
						}]
					},{
						columnWidth : .05, // 该列在整行中所占的百分比
						layout : "form", // 从上往下的布局
						labelWidth : 30,
						items : [{
									fieldLabel : "元 ",
									labelSeparator : '',
									anchor : "90%"
								}]
				},{
						columnWidth : .25, // 该列在整行中所占的百分比
						layout : "form", // 从上往下的布局
						labelWidth : 100,
						labelAlign :'right',
						items : [{
							xtype : "textfield",
							name : "unmappingMoney1",
							allowBlank : false,
							readOnly : true,
							style : {
								imeMode : 'disabled'
							},
							//fieldClass : 'field-align',
							fieldLabel : '未匹配金额',
							blankText :'债权金额不能为空，请正确填写债权金额！',
							anchor : '100%',
							listeners : {
								change : function(nf) {
									var value = nf.getValue();
									var continuationMoneyObj = nf.nextSibling();
									var index = value.indexOf(".");
									if (index <= 0) { // 如果第一次输入 没有进行格式化
										nf.setValue(Ext.util.Format.number(value,'0,000.00'))
										continuationMoneyObj.setValue(value);
									} else {
										if (value.indexOf(",") <= 0) {
											var ke = Ext.util.Format.number(value,'0,000.00')
											nf.setValue(ke);
											continuationMoneyObj.setValue(value);
										} else {
											var last = value.substring(index + 1,value.length);
											if (last == 0) {
												var temp = value.substring(0, index);
												temp = temp.replace(/,/g, "");
												continuationMoneyObj.setValue(temp);
											} else {
												var temp = value.replace(/,/g, "");
												continuationMoneyObj.setValue(temp);
											}
										}
									}
									
								}
							}
						},{
							xtype : "hidden",
							name : "obObligationProject.unmappingMoney"
							
						}]
					},{
						columnWidth : .05, // 该列在整行中所占的百分比
						layout : "form", // 从上往下的布局
						labelWidth : 30,
						items : [{
									fieldLabel : "元 ",
									labelSeparator : '',
									anchor : "90%"
								}]
				},{
							columnWidth : .35, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth :90,
							labelAlign :'right',
							items : [{
								xtype : 'textfield',
								fieldLabel : '债权期限',
								allowBlank : false,
								readOnly : true,
								anchor : '100%',
								name : "obObligationProject.payintentPeriod"
							}]
				
				},{
						columnWidth : .05, // 该列在整行中所占的百分比
						layout : "form", // 从上往下的布局
						labelWidth : 30,
						items : [{
									fieldLabel : "月 ",
									labelSeparator : '',
									anchor : "90%"
								}]
				},{
							columnWidth : .25, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : 90,
							labelAlign :'right',
							items : [{
								xtype : 'textfield',
								fieldLabel : '年化收益率',
								allowBlank : false,
								anchor : '100%',
								readOnly : true,
								name : "obObligationInvestInfo.obligationAccrual"
							}]
				
				},{
						columnWidth : .05, // 该列在整行中所占的百分比
						layout : "form", // 从上往下的布局
						labelWidth : 30,
						items : [{
									fieldLabel : "% ",
									labelSeparator : '',
									anchor : "90%"
								}]
				},{
						columnWidth : .25, // 该列在整行中所占的百分比
						layout : "form", // 从上往下的布局
						labelWidth : 100,
						labelAlign :'right',
						items : [{
							xtype : "textfield",
							name : "minInvestMent1",
							readOnly : true,
							allowBlank : false,
							style : {
								imeMode : 'disabled'
							},
							fieldLabel : '最小投资额',
							blankText :'最小投资额不能为空，请正确填写最小投资额！',
							anchor : '100%',
							scope : this,
							listeners : {
								scope : this,
								change : function(nf) {
									var value = nf.getValue();
									var continuationMoneyObj = nf.nextSibling();
									var index = value.indexOf(".");
									if (index <= 0) { // 如果第一次输入 没有进行格式化
										nf.setValue(Ext.util.Format.number(value,'0,000.00'))
										continuationMoneyObj.setValue(value);
									} else {
										if (value.indexOf(",") <= 0) {
											var ke = Ext.util.Format.number(value,'0,000.00')
											nf.setValue(ke);
											continuationMoneyObj.setValue(value);
										} else {
											var last = value.substring(index + 1,value.length);
											if (last == 0) {
												var temp = value.substring(0, index);
												temp = temp.replace(/,/g, "");
												continuationMoneyObj.setValue(temp);
											} else {
												var temp = value.replace(/,/g, "");
												continuationMoneyObj.setValue(temp);
											}
										}
									}
									
								},
								blur:function(){
									
													var obj1=this.getCmpByName("obObligationProject.projectMoney");
													var obj2=this.getCmpByName("obObligationProject.totalQuotient");
													
													var projectMoney=obj1.getValue(); //贷款金额
													var minInvestMent=this.getCmpByName("obObligationProject.minInvestMent").getValue();//最小投资份额
													obj2.setValue(projectMoney/minInvestMent);
												}
							}
						},{
							xtype : "hidden",
							name : "obObligationProject.minInvestMent"
							
						}]
					},{
						columnWidth : .05, // 该列在整行中所占的百分比
						layout : "form", // 从上往下的布局
						labelWidth : 30,
						items : [{
									fieldLabel : "元 ",
									labelSeparator : '',
									anchor : "90%"
								}]
				},{
							columnWidth : .35, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : 90,
							labelAlign :'right',
							items : [{
								xtype : 'textfield',
								fieldLabel : '最小投资份额',
								allowBlank : false,
								readOnly : true,
								name : "obObligationProject.minQuotient",
								anchor : '100%'
							}]
				
				},{
						columnWidth : .05, // 该列在整行中所占的百分比
						layout : "form", // 从上往下的布局
						labelWidth : 30,
						items : [{
									fieldLabel : "份 ",
									labelSeparator : '',
									anchor : "90%"
								}]
				},{
							columnWidth : .25, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : 90,
							labelAlign :'right',
							items : [{
								xtype : 'textfield',
								fieldLabel : '总份额',
								allowBlank : false,
								readOnly : true,
								name : "obObligationProject.totalQuotient",
								anchor : '100%'
							}]
				
				},{
						columnWidth : .05, // 该列在整行中所占的百分比
						layout : "form", // 从上往下的布局
						labelWidth : 30,
						items : [{
									fieldLabel : "份 ",
									labelSeparator : '',
									anchor : "90%"
								}]
				},{
							columnWidth : .25, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : 100,
							labelAlign :'right',
							items : [{
								xtype : 'textfield',
								fieldLabel : '未匹配份额',
								allowBlank : false,
								readOnly : true,
								name : "obObligationProject.unmappingQuotient",
								anchor : '100%'
							}]
				
				},{
						columnWidth : .05, // 该列在整行中所占的百分比
						layout : "form", // 从上往下的布局
						labelWidth : 30,
						items : [{
									fieldLabel : "份 ",
									labelSeparator : '',
									anchor : "90%"
								}]
				},{
						columnWidth : 1,
						layout:'column',
						items : [
						 {
						columnWidth : .13, // 该列在整行中所占的百分比
						layout : "form", // 从上往下的布局
						labelWidth : 90,
						items : [{
							 fieldLabel : "计息方式 ",
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
							id : "jixifs1" +this.idDefinition,
							inputValue : false,
							anchor : "100%",
							disabled : true,
							listeners : {
								scope : this,
								check : function(obj, checked) {
									var flag = Ext.getCmp("jixifs1"
											+ this.idDefinition).getValue();
									if (flag == true) {
										this.getCmpByName('month').show();
										this.getCmpByName('periord').hide()
										this.getCmpByName('obObligationProject.accrualtype').setValue("sameprincipal");
										Ext.getCmp("jixizq1"+this.idDefinition).setDisabled(true);
									      Ext.getCmp("jixizq2"+ this.idDefinition).setDisabled(true);
									        Ext.getCmp("jixizq3"+ this.idDefinition).setDisabled(true);
									          Ext.getCmp("jixizq4"+ this.idDefinition).setDisabled(true);  
									          
									           Ext.getCmp("jixizq6"+ this.idDefinition).setDisabled(true);
									           this.getCmpByName('obObligationProject.dayOfEveryPeriod').setDisabled(true);
								 	            this.getCmpByName('obObligationProject.dayOfEveryPeriod').setValue("");
								 	    
									              Ext.getCmp("jixizq2"+ this.idDefinition).setValue(true);
									             Ext.getCmp("jixizq1" +this.idDefinition).setValue(false);
									            this.getCmpByName('obObligationProject.payaccrualType').setValue("monthPay");
									            
									             this.getCmpByName('obObligationProject.payintentPeriod').setDisabled(false);  //修改的  zcb
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
							disabled : true,
							listeners : {
								scope : this,
								check : function(obj, checked) {
									var flag = Ext.getCmp("jixifs2"+ this.idDefinition).getValue();
									if (flag == true) {
										this.getCmpByName('month').show();
										this.getCmpByName('periord').hide()
										this.getCmpByName('obObligationProject.accrualtype').setValue("sameprincipalandInterest");
									    Ext.getCmp("jixizq1"+ this.idDefinition).setDisabled(true);
									      Ext.getCmp("jixizq2"+ this.idDefinition).setDisabled(true);
									        Ext.getCmp("jixizq3"+ this.idDefinition).setDisabled(true);
									          Ext.getCmp("jixizq4"+ this.idDefinition).setDisabled(true);  
									           
									           Ext.getCmp("jixizq6"+ this.idDefinition).setDisabled(true);
									           this.getCmpByName('obObligationProject.dayOfEveryPeriod').setDisabled(true);
								 	           this.getCmpByName('obObligationProject.dayOfEveryPeriod').setValue("");
									          
								 	           Ext.getCmp("jixizq2"+ this.idDefinition).setValue(true);
									             Ext.getCmp("jixizq1"+ this.idDefinition).setValue(false);
									            this.getCmpByName('obObligationProject.payaccrualType').setValue("monthPay");
									            
									             this.getCmpByName('obObligationProject.payintentPeriod').setDisabled(false); //修改的  zcb
									              this.getCmpByName('mqhkri1').hide();
								                    this.getCmpByName('mqhkri').show();
									}
								}
							}
						}]
					},{
						columnWidth : 0.12,
						labelWidth : 5,
						layout : 'form',
						items : [{
							xtype : 'radio',
							boxLabel : '等本等息',
							anchor : "100%",
							name : 'f1',
							id : "jixifs5" +this.idDefinition,
							inputValue : false,
							disabled : true,
							listeners : {
								scope : this,
								check : function(obj,checked){
									
									var flag = Ext.getCmp("jixifs5"
											+ this.idDefinition).getValue();
									if (flag == true) {
										this.getCmpByName('month').hide();
										this.getCmpByName('periord').show()
										this.getCmpByName('obObligationProject.accrualtype').setValue("sameprincipalsameInterest");
										  Ext.getCmp("jixizq1"+ this.idDefinition).setDisabled(false);
									      Ext.getCmp("jixizq2"+ this.idDefinition).setDisabled(false);
									        Ext.getCmp("jixizq3"+ this.idDefinition).setDisabled(false);
									          Ext.getCmp("jixizq4"+ this.idDefinition).setDisabled(false);  
									           Ext.getCmp("jixizq6"+ this.idDefinition).setDisabled(false);
									//          this.getCmpByName('slSmallloanProject.dayOfEveryPeriod').setDisabled(false);
	                                         if(this.getCmpByName('obObligationProject.payaccrualType').getValue()==""){
									          Ext.getCmp("jixizq2"+ this.idDefinition).setValue(true);
									             Ext.getCmp("jixizq1"+ this.idDefinition).setValue(false);
									            this.getCmpByName('obObligationProject.payaccrualType').setValue("monthPay");
	                                         }
									}
								
								}
							}
						}]
					}, {
						columnWidth : 0.14,
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
							disabled : true,
							listeners : {
								scope : this,
								check : function(obj, checked) {
									var flag = Ext.getCmp("jixifs3"
											+ this.idDefinition).getValue();
									if (flag == true) {
										this.getCmpByName('month').show();
										this.getCmpByName('periord').hide()
										this.getCmpByName('obObligationProject.accrualtype').setValue("singleInterest");
										  Ext.getCmp("jixizq1"+ this.idDefinition).setDisabled(false);
									      Ext.getCmp("jixizq2"+ this.idDefinition).setDisabled(false);
									        Ext.getCmp("jixizq3"+ this.idDefinition).setDisabled(false);
									          Ext.getCmp("jixizq4"+ this.idDefinition).setDisabled(false);  
									           Ext.getCmp("jixizq6"+ this.idDefinition).setDisabled(false);
									//          this.getCmpByName('slSmallloanProject.dayOfEveryPeriod').setDisabled(false);
	                                         if(this.getCmpByName('obObligationProject.payaccrualType').getValue()==""){
									          Ext.getCmp("jixizq2"+ this.idDefinition).setValue(true);
									             Ext.getCmp("jixizq1"+ this.idDefinition).setValue(false);
									            this.getCmpByName('obObligationProject.payaccrualType').setValue("monthPay");
	                                         }
									}
								}
							}
						}, {
							xtype : "hidden",
							name : "obObligationProject.accrualtype",
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
							disabled : true,
							listeners : {
								scope : this,
								check : function(obj, checked) {
									var flag = Ext.getCmp("jixifs4"+ this.idDefinition).getValue();
									if (flag == true) {
										this.getCmpByName('month').show();
										this.getCmpByName('periord').hide()
										this.getCmpByName('obObligationProject.accrualtype').setValue("ontTimeAccrual");
										Ext.getCmp("jixizq1"+this.idDefinition).setDisabled(true);
									      Ext.getCmp("jixizq2"+ this.idDefinition).setDisabled(true);
									        Ext.getCmp("jixizq3"+ this.idDefinition).setDisabled(true);
									          Ext.getCmp("jixizq4"+ this.idDefinition).setDisabled(true);  
								                
									           Ext.getCmp("jixizq6"+ this.idDefinition).setDisabled(true);
									           this.getCmpByName('obObligationProject.dayOfEveryPeriod').setDisabled(true);
								 	            this.getCmpByName('obObligationProject.dayOfEveryPeriod').setValue("");
								 	    
									              Ext.getCmp("jixizq6"+ this.idDefinition).setValue(false);
									             Ext.getCmp("jixizq1" +this.idDefinition).setValue(false);
									             Ext.getCmp("jixizq2" +this.idDefinition).setValue(false);
									            this.getCmpByName('obObligationProject.payaccrualType').setValue("");
									            
									             
								                   
										 // this.getCmpByName('payintentPeriod1').setDisabled(true);
										  //this.getCmpByName('payintentPeriod1').setValue(1);
									      this.getCmpByName('obObligationProject.payintentPeriod').setDisabled(true); //修改的  zcb
										  this.getCmpByName('obObligationProject.payintentPeriod').setValue(1);
									   this.getCmpByName('mqhkri').hide();
								       this.getCmpByName('mqhkri1').show();
								        
									}else{
									  // this.getCmpByName('payintentPeriod1').setDisabled(false);
									   this.getCmpByName('obObligationProject.payintentPeriod').setDisabled(false); //修改的  zcb
										
									   this.getCmpByName('mqhkri1').hide();
								       this.getCmpByName('mqhkri').show();
									
									}
								}
							}
						}, {
							xtype : "hidden",
							name : "obObligationProject.payaccrualType",
							value : "monthPay"

						}]
					}
					]
				},{
					
				columnWidth : 1,
					layout:'column',
					items : [ {
					columnWidth : .13, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : 90,
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
						disabled : true,
						listeners : {
							scope : this,
							check : function(obj, checked) {
								var flag = Ext.getCmp("jixizq1"	+ this.idDefinition).getValue();
								if (flag == true) {
									this.getCmpByName('obObligationProject.payaccrualType').setValue("dayPay");
									
									 Ext.getCmp("meiqihkrq1"+this.idDefinition).setDisabled(true);
									 Ext.getCmp("meiqihkrq1"+ this.idDefinition).setValue(false);
									 Ext.getCmp("meiqihkrq2"+ this.idDefinition).setDisabled(true);
									 Ext.getCmp("meiqihkrq2"+ this.idDefinition).setValue(true);
								
									 	this.getCmpByName('obObligationProject.isStartDatePay').setValue("2");
									 	
									 
								     
								     
								}else {
								       
								       
								     Ext.getCmp("meiqihkrq1"+this.idDefinition).setDisabled(false);
									 Ext.getCmp("meiqihkrq2"+ this.idDefinition).setDisabled(false);
									 if(Ext.getCmp("meiqihkrq1"+ this.idDefinition).getValue()==true){
									 this.getCmpByName('obObligationProject.payintentPerioDate').setDisabled(false);
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
						disabled : true,
						listeners : {
							scope : this,
							check : function(obj, checked) {
								var flag = Ext.getCmp("jixizq2"
										+this.idDefinition).getValue();
								if (flag == true) {
									this.getCmpByName('obObligationProject.payaccrualType').setValue("monthPay");
									
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
						disabled : true,
						listeners : {
							scope : this,
							check : function(obj, checked) {
								var flag = Ext.getCmp("jixizq3"
										+this.idDefinition).getValue();
								if (flag == true) {
									this.getCmpByName('obObligationProject.payaccrualType').setValue("seasonPay");
								
								}
							}
						}
					}]
				}, {
					columnWidth : 0.14,
					labelWidth : 5,
					layout : 'form',
					items : [{
						xtype : 'radio',
						boxLabel : '年',
						name : 'z1',
						id : "jixizq4" + this.idDefinition,
						inputValue : false,
						anchor : "100%",
						disabled : true,
						listeners : {
							scope : this,
							check : function(obj, checked) {
								var flag = Ext.getCmp("jixizq4"
										+ this.idDefinition).getValue();
								if (flag == true) {
									this.getCmpByName('obObligationProject.payaccrualType').setValue("yearPay");
									
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
						disabled : true,
						listeners : {
							scope : this,
							check : function(obj, checked) {
								var flag = Ext.getCmp("jixizq6"
										+ this.idDefinition).getValue();
								if (flag == true) {
									this.getCmpByName('obObligationProject.payaccrualType').setValue("owerPay");
									
								   this.getCmpByName('obObligationProject.dayOfEveryPeriod').setDisabled(false);
								   
								    Ext.getCmp("meiqihkrq1"+this.idDefinition).setDisabled(true);
									 Ext.getCmp("meiqihkrq1"+ this.idDefinition).setValue(false);
									 Ext.getCmp("meiqihkrq2"+ this.idDefinition).setDisabled(true);
									 Ext.getCmp("meiqihkrq2"+ this.idDefinition).setValue(true);
								}else{
								 this.getCmpByName('obObligationProject.dayOfEveryPeriod').setDisabled(true);
							 	    this.getCmpByName('obObligationProject.dayOfEveryPeriod').setValue("");
							 	    if(Ext.getCmp("jixizq1"+ this.idDefinition).getValue()==false){
								 	     Ext.getCmp("meiqihkrq1"+this.idDefinition).setDisabled(false);
										 Ext.getCmp("meiqihkrq2"+ this.idDefinition).setDisabled(false);
										 if(Ext.getCmp("meiqihkrq1"+ this.idDefinition).getValue()==true){
										  this.getCmpByName('obObligationProject.payintentPerioDate').setDisabled(false);
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
				 	readOnly : true,
				 	//fieldClass : 'field-align',
					 name:'obObligationProject.dayOfEveryPeriod'
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
					
					]
				},{
					
					columnWidth : 1,
					layout:'column',
					items:[ {
					columnWidth : .5, 
					name :"mqhkri",
					layout : "column", 
					items : [ {
							columnWidth : 0.328,
							labelWidth : 69,
							layout : 'form',
							items : [{
										xtype : 'radio',
										fieldLabel : '每期还款日',
										boxLabel : '固定在',
										name : 'q1',
										id : "meiqihkrq1" + this.idDefinition,
										inputValue : true,
										anchor : "100%",
										disabled : true,
										listeners : {
										scope : this,
										check : function(obj, checked) {
											var flag = Ext.getCmp("meiqihkrq1"+ this.idDefinition).getValue();
											if (flag == true) {
												this.getCmpByName('obObligationProject.isStartDatePay').setValue("1");
												this.getCmpByName('obObligationProject.payintentPerioDate').setDisabled(false);
											}
										}
									}

									}, {
										xtype : "hidden",
										name : "obObligationProject.isStartDatePay"
									}]
						}, {
							columnWidth : 0.132,
							labelWidth : 1,
							layout : 'form',
							items : [{
										xtype : 'textfield',
										readOnly : this.isReadOnly,
										id:'payintentPerioDate'+this.idDefinition,
										name : "obObligationProject.payintentPerioDate",
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
										hiddenName : "obObligationProject.payintentPerioDate",
										fieldLabel : "",
										anchor : '100%'
									}]
						}, {
							columnWidth : 0.12,
							labelWidth :45,
							layout : 'form',
							items : [{
							fieldLabel : "日还款",
								labelSeparator : '',
								anchor : "100%"
									}]
						}, {
							columnWidth : 0.42,
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
										disabled : true,
										listeners : {
										scope : this,
										check : function(obj, checked) {
											var flag = Ext.getCmp("meiqihkrq2"
													+ this.idDefinition).getValue();
											if (flag == true) {
												this.getCmpByName('obObligationProject.isStartDatePay').setValue("2");
												this.getCmpByName('obObligationProject.payintentPerioDate').setValue(null);
														this.getCmpByName('obObligationProject.payintentPerioDate').setDisabled(true);
											}
										}
									}

									}]
						}]}
						,  {
							columnWidth : .12, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : 13,
							items : [{
								xtype : "checkbox",
								boxLabel : "是否前置付息",
								disabled : true,
								anchor : "100%",
								id : "isPreposePayAccrual",
								name : "isPreposePayAccrual",
								listeners : {
									scope : this,
									'check' : function(box,value){
										if(value==true){
											this.getCmpByName('obObligationProject.isPreposePayAccrual').setValue(1);
										}else{
											this.getCmpByName('obObligationProject.isPreposePayAccrual').setValue(0);
										}
									}
								}
							},{
								xtype : 'hidden',
								name : 'obObligationProject.isPreposePayAccrual'
							}]
						}]
				
				},{
					
						columnWidth : .4, // 该列在整行中所占的百分比
						layout : "form", // 从上往下的布局
						labelWidth : 90,
						labelAlign :'right',
						items : [{
							xtype : "hidden",
							name : "obObligationInvestInfo.investMentPersonId",
							anchor : '100%'
							
						},{
							xtype : "hidden",
							name : "obObligationInvestInfo.investObligationStatus",
							anchor : '100%'
							
						},{
							xtype : "hidden",
							name : "obObligationInvestInfo.systemInvest",
							anchor : '100%'
							
						},{
							xtype : "textfield",
							name : "obObligationInvestInfo.investPersonName",
							allowBlank : false,
							readOnly : true,
							fieldLabel : '投资人姓名',
							anchor : '100%'
							
						}]
					
				},{
					
						columnWidth : .3, // 该列在整行中所占的百分比
						layout : "form", // 从上往下的布局
						labelWidth : 90,
						labelAlign :'right',
						items : [{
							xtype : "textfield",
							name : "obObligationInvestInfo.investQuotient",
							allowBlank : false,
							readOnly : this.isReadOnly,
							fieldLabel : '投资份额',
							blankText :'系统账号不能为空，请正确填写系统账号！',
							anchor : '100%',
							listeners : {
									scope:this,
								   blur : function(v){
									  if(v.getValue()==""){
									   v.setValue("0.00")
									  }else{
									    var ss=v.getValue()*(this.getCmpByName('obObligationProject.minInvestMent').getValue());
									    this.getCmpByName('investMoney').setValue(ss);
									   	this.getCmpByName('obObligationInvestInfo.investMoney').setValue(ss);
									    var rate =(ss/(this.getCmpByName('obObligationProject.projectMoney').getValue()))*100;
									   	this.getCmpByName('obObligationInvestInfo.investRate').setValue(rate);
									  }
								   }
								}
							
							
						}]
					
				},{
							xtype : "hidden",
							name : "obObligationInvestInfo.investRate"
														  
				},{
					
						columnWidth : .3, // 该列在整行中所占的百分比
						layout : "form", // 从上往下的布局
						labelWidth : 90,
						labelAlign :'right',
						items : [{
							xtype : "textfield",
							name : "investMoney",
							allowBlank : false,
							readOnly : true,
							fieldLabel : '投资金额',
							//name:"systemAccountNumber",
							//fieldClass : 'field-align',
							blankText :'系统账号不能为空，请正确填写系统账号！',
							anchor : '100%',
							listeners : {
								change : function(nf) {
									var value = nf.getValue();
									var continuationMoneyObj = nf.nextSibling();
									var index = value.indexOf(".");
									if (index <= 0) { // 如果第一次输入 没有进行格式化
										nf.setValue(Ext.util.Format.number(value,'0,000.00'))
										continuationMoneyObj.setValue(value);
									} else {
										if (value.indexOf(",") <= 0) {
											var ke = Ext.util.Format.number(value,'0,000.00')
											nf.setValue(ke);
											continuationMoneyObj.setValue(value);
										} else {
											var last = value.substring(index + 1,value.length);
											if (last == 0) {
												var temp = value.substring(0, index);
												temp = temp.replace(/,/g, "");
												continuationMoneyObj.setValue(temp);
											} else {
												var temp = value.replace(/,/g, "");
												continuationMoneyObj.setValue(temp);
											}
										}
									}
									
								}
							}
						},{
							xtype : "hidden",
							name : "obObligationInvestInfo.investMoney"
						}]
					
				},{
					
						columnWidth : .4, // 该列在整行中所占的百分比
						layout : "form", // 从上往下的布局
						labelWidth : 90,
						labelAlign :'right',
						items : [{
							xtype : "datefield",
							name : "obObligationInvestInfo.investStartDate",
							allowBlank : false,
							readOnly : this.isReadOnly,
							fieldLabel : '资金投资日',
							format : 'Y-m-d',
							//name:"systemAccountNumber",
							//fieldClass : 'field-align',
							blankText :'系统账号不能为空，请正确填写系统账号！',
							anchor : '100%',
							listeners : {
								scope : this,
								'change' : function(nf){
									 var startDate=nf.getValue();
									 var period =this.getCmpByName("obObligationProject.payintentPeriod").getValue();
										setIntentDate("monthPay",null,period,startDate,this)
									}
						}
							
						}]
					
				},{
					
						columnWidth : .3, // 该列在整行中所占的百分比
						layout : "form", // 从上往下的布局
						labelWidth : 90,
						labelAlign :'right',
						items : [{
							xtype : "datefield",
							name : "obObligationInvestInfo.investEndDate",
							allowBlank : false,
							readOnly : true,
							fieldLabel : '债权结束日',
							format : 'Y-m-d',
							//name:"systemAccountNumber",
							//fieldClass : 'field-align',
							blankText :'系统账号不能为空，请正确填写系统账号！',
							anchor : '100%'
							
						}]
					
				},{
				            	columnWidth:.3,
				                layout: 'form',
				                labelWidth : 70,
				                labelAlign :'right',
				                defaults : {anchor:anchor},
				                items :[{
										xtype : "combo",
										triggerClass : 'x-form-search-trigger',
										hiddenName : "degree",
										editable : false,
										fieldLabel : "操作人员",
										blankText : "操作人员不能为空，请正确填写!",
										allowBlank : false,
										anchor : "100%",
										onTriggerClick : function(cc) {
											var obj = this;
											var appuerIdObj = obj.nextSibling();
											var userIds = appuerIdObj.getValue();
											if ("" == obj.getValue()) {
												userIds = "";
											}
											new UserDialog({
												userIds : userIds,
												userName : obj.getValue(),
												single : false,
												title : "选择操作人员",
												callback : function(uId, uname) {
													obj.setValue(uId);
													obj.setRawValue(uname);
													appuerIdObj.setValue(uId);
												}
											}).show();
						}
					}, {
						xtype : "hidden",
						value : ""
					}]
				            }]
			}]
		})
	}
});