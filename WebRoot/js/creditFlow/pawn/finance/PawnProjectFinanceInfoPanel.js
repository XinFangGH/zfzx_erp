PawnProjectFinanceInfoPanel = Ext.extend(Ext.Panel, {
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
		if (typeof(_cfg.isStartDateReadOnly)!="undefined") {
			this.isStartDateReadOnly = _cfg.isStartDateReadOnly;
		}else{
			this.isStartDateReadOnly=true
		}
		if (_cfg.idDefinition) {
			this.idDefinition = _cfg.idDefinition;
		}
		var idDefinition1=this.idDefinition;
		Ext.applyIf(this, _cfg);
		this.initComponents();
		this.idDefinition=this.projectId+this.idDefinition;
		var storepayintentPeriod="[";
		  for (var i = 1; i < 31; i++) {
				storepayintentPeriod = storepayintentPeriod + "[" + i
						+ ", " + i + "],";
			}
			storepayintentPeriod = storepayintentPeriod.substring(0,storepayintentPeriod.length - 1);
			storepayintentPeriod = storepayintentPeriod + "]";
			var obstorepayintentPeriod = Ext.decode(storepayintentPeriod);
			var setComprehensiveCost=function(objectPanel){
				var projectMoney=objectPanel.getCmpByName('plPawnProject.projectMoney').getValue();
				var monthFeeRate=objectPanel.getCmpByName('plPawnProject.monthFeeRate').getValue();
				var payaccrualType=objectPanel.getCmpByName('plPawnProject.payaccrualType').getValue();
				var payintentPeriod=objectPanel.getCmpByName('plPawnProject.payintentPeriod').getValue();
				var startDate=objectPanel.getCmpByName('plPawnProject.startDate').getValue();
				var intentDate=objectPanel.getCmpByName('plPawnProject.intentDate').getValue();
				var dayOfEveryPeriod=objectPanel.getCmpByName('plPawnProject.dayOfEveryPeriod').getValue();
				var o=objectPanel.getCmpByName('plPawnProject.comprehensiveCost');
				if(null!=projectMoney && projectMoney!=''
				  && null!=monthFeeRate && monthFeeRate!=''
				  && null!=payaccrualType && payaccrualType!=''
				  && null!=payintentPeriod && payintentPeriod!=''
				  && null!=startDate && startDate!=''
				  && null!=intentDate && intentDate!=''){
					Ext.Ajax.request({
						url : __ctxPath + "/creditFlow/pawn/project/getComprehensiveCostPlPawnProject.do",
						method : 'POST',
						scope:this,
						params : {
							'plPawnProject.projectMoney':projectMoney,
							'plPawnProject.monthFeeRate':monthFeeRate,
							'plPawnProject.payaccrualType':payaccrualType,
							'plPawnProject.payintentPeriod':payintentPeriod,
							'plPawnProject.startDate' : startDate,
							'plPawnProject.intentDate' : intentDate,
							'plPawnProject.dayOfEveryPeriod':dayOfEveryPeriod
						},
						success : function(response, request) {
							obj = Ext.util.JSON.decode(response.responseText);
							o.setValue(obj.money);
						},
						failure : function(response,request) {
							Ext.ux.Toast.msg('操作信息', '查询任务完成时限操作失败!');
						}
					});
				  }
			}
			var setIntentDate=function(payAccrualType,dayOfEveryPeriod,payintentPeriod,startDate,intentDatePanel,objectPanel){
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
						setComprehensiveCost(objectPanel)
					},
					failure : function(response,request) {
						Ext.ux.Toast.msg('操作信息', '查询任务完成时限操作失败!');
					}
				});
			}
			
		PawnProjectFinanceInfoPanel.superclass.constructor.call(this, {
			items : [{
				layout : "column",
				border : false,
				scope : this,
				
				items : [{
					columnWidth : .25, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : 85,
					items : [{
						xtype : "textfield",
						fieldLabel : "典当金额",
						fieldClass : 'field-align',
						name : "projectMoney1",
						readOnly : this.isAllReadOnly,
						allowNegative : false, // 允许负数
						style : {
							imeMode : 'disabled'
						},
						blankText : "典当金额不能为空，请正确填写!",
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
											.getCmpByName("plPawnProject.projectMoney")
											.setValue(value);
								} else {

									if (value.indexOf(",") <= 0) {
										var ke = Ext.util.Format.number(value,
												'0,000.00')
										nf.setValue(ke);
										this
												.getCmpByName("plPawnProject.projectMoney")
												.setValue(value);
									} else {
										var last = value.substring(index + 1,
												value.length);
										if (last == 0) {
											var temp = value
													.substring(0, index);
											temp = temp.replace(/,/g, "");
											this
													.getCmpByName("plPawnProject.projectMoney")
													.setValue(temp);
													nf.setValue(Ext.util.Format.number(temp,
											            '0,000.00'))
										} else {
											var temp = value.replace(/,/g, "");
											this
													.getCmpByName("plPawnProject.projectMoney")
													.setValue(temp);
													nf.setValue(Ext.util.Format.number(temp,
											            '0,000.00'))
										}
									}

								}
								setComprehensiveCost(this)
							}
						}
					}, {
						xtype : "hidden",
						name : "plPawnProject.projectMoney"
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
					columnWidth : .25, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : 120,
					items : [{
						xtype : 'numberfield',
						fieldLabel : '月利率',
						readOnly : this.isAllReadOnly,
						allowBlank : false,
						name : 'plPawnProject.accrual',
						anchor : '100%'
					}]
				}, {
					columnWidth : .05, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : 20,
					items : [{
								fieldLabel : "% ",
								labelSeparator : '',
								anchor : "100%"
							}]
				}, {
					columnWidth : .25, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : 120,
					items : [{
						xtype : 'numberfield',
						fieldLabel : '月费率',
						readOnly : this.isAllReadOnly,
						allowBlank : false,
						name : 'plPawnProject.monthFeeRate',
						anchor : '100%',
						listeners : {
							scope : this,
							'change' : function(){
								setComprehensiveCost(this)
							}
						}
					}]
				}, {
					columnWidth : .05, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : 20,
					items : [{
								fieldLabel : "% ",
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
									this.getCmpByName('plPawnProject.payaccrualType').setValue("dayPay");
									
									 Ext.getCmp("meiqihkrq1"+this.idDefinition).setDisabled(true);
									 Ext.getCmp("meiqihkrq1"+ this.idDefinition).setValue(false);
									 Ext.getCmp("meiqihkrq2"+ this.idDefinition).setDisabled(true);
									 Ext.getCmp("meiqihkrq2"+ this.idDefinition).setValue(true);
									Ext.getCmp("jixizq2"+this.idDefinition).setValue(false);
									 Ext.getCmp("jixizq3"+ this.idDefinition).setValue(false);
									 Ext.getCmp("jixizq4"+ this.idDefinition).setValue(false);
									 Ext.getCmp("jixizq6"+ this.idDefinition).setValue(false);
									 	this.getCmpByName('plPawnProject.isStartDatePay').setValue("2");
										var payAccrualType=this.getCmpByName('plPawnProject.payaccrualType').getValue();
										var dayOfEveryPeriod=this.getCmpByName('plPawnProject.dayOfEveryPeriod').getValue();
										var payintentPeriod=this.getCmpByName('plPawnProject.payintentPeriod').getValue();
										var startDate=this.getCmpByName('plPawnProject.startDate').getValue();
										var intentDatePanel=this.getCmpByName('plPawnProject.intentDate');
										setIntentDate(payAccrualType,dayOfEveryPeriod,payintentPeriod,startDate,intentDatePanel,this)
								     
								     
								}else {
								       
								       
								        Ext.getCmp("meiqihkrq1"+this.idDefinition).setDisabled(false);
									 Ext.getCmp("meiqihkrq2"+ this.idDefinition).setDisabled(false);
									 if(Ext.getCmp("meiqihkrq1"+ this.idDefinition).getValue()==true){
									  this.getCmpByName('plPawnProject.payintentPerioDate').setDisabled(false);
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
									this.getCmpByName('plPawnProject.payaccrualType').setValue("monthPay");
									Ext.getCmp("jixizq1"+this.idDefinition).setValue(false);
									 Ext.getCmp("jixizq3"+ this.idDefinition).setValue(false);
									 Ext.getCmp("jixizq4"+ this.idDefinition).setValue(false);
									 Ext.getCmp("jixizq6"+ this.idDefinition).setValue(false);
									var payAccrualType=this.getCmpByName('plPawnProject.payaccrualType').getValue();
										var dayOfEveryPeriod=this.getCmpByName('plPawnProject.dayOfEveryPeriod').getValue();
										var payintentPeriod=this.getCmpByName('plPawnProject.payintentPeriod').getValue();
										var startDate=this.getCmpByName('plPawnProject.startDate').getValue();
										var intentDatePanel=this.getCmpByName('plPawnProject.intentDate');
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
									this.getCmpByName('plPawnProject.payaccrualType').setValue("seasonPay");
									Ext.getCmp("jixizq1"+this.idDefinition).setValue(false);
									 Ext.getCmp("jixizq2"+ this.idDefinition).setValue(false);
									 Ext.getCmp("jixizq4"+ this.idDefinition).setValue(false);
									 Ext.getCmp("jixizq6"+ this.idDefinition).setValue(false);
								var payAccrualType=this.getCmpByName('plPawnProject.payaccrualType').getValue();
										var dayOfEveryPeriod=this.getCmpByName('plPawnProject.dayOfEveryPeriod').getValue();
										var payintentPeriod=this.getCmpByName('plPawnProject.payintentPeriod').getValue();
										var startDate=this.getCmpByName('plPawnProject.startDate').getValue();
										var intentDatePanel=this.getCmpByName('plPawnProject.intentDate');
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
									this.getCmpByName('plPawnProject.payaccrualType').setValue("yearPay");
									Ext.getCmp("jixizq1"+this.idDefinition).setValue(false);
									 Ext.getCmp("jixizq3"+ this.idDefinition).setValue(false);
									 Ext.getCmp("jixizq2"+ this.idDefinition).setValue(false);
									 Ext.getCmp("jixizq6"+ this.idDefinition).setValue(false);
									var payAccrualType=this.getCmpByName('plPawnProject.payaccrualType').getValue();
										var dayOfEveryPeriod=this.getCmpByName('plPawnProject.dayOfEveryPeriod').getValue();
										var payintentPeriod=this.getCmpByName('plPawnProject.payintentPeriod').getValue();
										var startDate=this.getCmpByName('plPawnProject.startDate').getValue();
										var intentDatePanel=this.getCmpByName('plPawnProject.intentDate');
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
									this.getCmpByName('plPawnProject.payaccrualType').setValue("owerPay");
									
								   this.getCmpByName('plPawnProject.dayOfEveryPeriod').setDisabled(false);
								   
								    Ext.getCmp("meiqihkrq1"+this.idDefinition).setDisabled(true);
									 Ext.getCmp("meiqihkrq1"+ this.idDefinition).setValue(false);
									 Ext.getCmp("meiqihkrq2"+ this.idDefinition).setDisabled(true);
									 Ext.getCmp("meiqihkrq2"+ this.idDefinition).setValue(true);
									 this.getCmpByName('plPawnProject.payintentPerioDate').setDisabled(false);
									 this.getCmpByName('plPawnProject.payintentPerioDate').setValue(null)
									 Ext.getCmp("jixizq1"+this.idDefinition).setValue(false);
									 Ext.getCmp("jixizq3"+ this.idDefinition).setValue(false);
									 Ext.getCmp("jixizq4"+ this.idDefinition).setValue(false);
									 Ext.getCmp("jixizq2"+ this.idDefinition).setValue(false);
									 
								}else{
								 this.getCmpByName('plPawnProject.dayOfEveryPeriod').setDisabled(true);
							 	    this.getCmpByName('plPawnProject.dayOfEveryPeriod').setValue("");
							 	    if(Ext.getCmp("jixizq1"+ this.idDefinition).getValue()==false){
								 	     Ext.getCmp("meiqihkrq1"+this.idDefinition).setDisabled(false);
										 Ext.getCmp("meiqihkrq2"+ this.idDefinition).setDisabled(false);
										 if(Ext.getCmp("meiqihkrq1"+ this.idDefinition).getValue()==true){
										  this.getCmpByName('plPawnProject.payintentPerioDate').setDisabled(false);
										 }
									  }
								    
								}
							}
						}
					},{
						xtype : 'hidden',
						name : 'plPawnProject.payaccrualType',
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
					 name:'plPawnProject.dayOfEveryPeriod',
					 listeners : {
					 	scope : this,
					 	'change' : function(){
					 		var payAccrualType=this.getCmpByName('plPawnProject.payaccrualType').getValue();
							var dayOfEveryPeriod=this.getCmpByName('plPawnProject.dayOfEveryPeriod').getValue();
							var payintentPeriod=this.getCmpByName('plPawnProject.payintentPeriod').getValue();
							var startDate=this.getCmpByName('plPawnProject.startDate').getValue();
							var intentDatePanel=this.getCmpByName('plPawnProject.intentDate');
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
					
					]}, {
					columnWidth : 1,
					layout:'column',
					items:[{
					columnWidth : .25, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : 85,
					items : [{
								fieldLabel : "典当期限",
								xtype : "textfield",
								fieldClass : 'field-align',
								allowBlank : false,
								readOnly : this.isAllReadOnly,
								name : "plPawnProject.payintentPeriod",
								anchor : "100%",
								listeners : {
								 	scope : this,
								 	'change' : function(){
								 		var payAccrualType=this.getCmpByName('plPawnProject.payaccrualType').getValue();
										var dayOfEveryPeriod=this.getCmpByName('plPawnProject.dayOfEveryPeriod').getValue();
										var payintentPeriod=this.getCmpByName('plPawnProject.payintentPeriod').getValue();
										var startDate=this.getCmpByName('plPawnProject.startDate').getValue();
										var intentDatePanel=this.getCmpByName('plPawnProject.intentDate');
										setIntentDate(payAccrualType,dayOfEveryPeriod,payintentPeriod,startDate,intentDatePanel,this)
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
				},{
					columnWidth : .25,
					layout : 'form',
					labelWidth : 120,
					items : [{
						fieldLabel : "放款日期",
						xtype : "datefield",
						style : {
							imeMode : 'disabled'
						},
						name : "plPawnProject.startDate",
						allowBlank : false,
						readOnly : this.isStartDateReadOnly,
						blankText : "放款日期不能为空，请正确填写!",
						anchor : "100%",
						format : 'Y-m-d',
						listeners : {
							scope : this,
							'change' : function(nf){
								var payAccrualType=this.getCmpByName('plPawnProject.payaccrualType').getValue();
								var dayOfEveryPeriod=this.getCmpByName('plPawnProject.dayOfEveryPeriod').getValue();
								var payintentPeriod=this.getCmpByName('plPawnProject.payintentPeriod').getValue();
								var startDate=this.getCmpByName('plPawnProject.startDate').getValue();
								var intentDatePanel=this.getCmpByName('plPawnProject.intentDate');
								setIntentDate(payAccrualType,dayOfEveryPeriod,payintentPeriod,startDate,intentDatePanel,this)
								
							}
						}
					}]
				}, {
					columnWidth : .05, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : 20,
					items : [{
								fieldLabel : " ",
								labelSeparator : '',
								anchor : "100%"
							}]
				},{
					columnWidth : .25,
					layout : 'form',
					labelWidth : 120,
					labelAlign : 'right',
					items : [{
						fieldLabel : "<font color='red'>*</font>典当到期日",
						xtype : "datefield",
						style : {
							imeMode : 'disabled'
						},
						name : "plPawnProject.intentDate",
						readOnly : true,
						blankText : "典当到期日不能为空，请正确填写!",
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
										this.getCmpByName('plPawnProject.isPreposePayAccrual').setValue(1);
									}else{
										this.getCmpByName('plPawnProject.isPreposePayAccrual').setValue(0);
									}
								}
							}
						},{
							xtype :'hidden',
							name : 'plPawnProject.isPreposePayAccrual',
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
										this.getCmpByName('plPawnProject.isInterestByOneTime').setValue(1);
									}else{
										this.getCmpByName('plPawnProject.isInterestByOneTime').setValue(0);
									}
								}
							}
						},{
							xtype :'hidden',
							name : 'plPawnProject.isInterestByOneTime',
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
												this.getCmpByName('plPawnProject.isStartDatePay').setValue("1");
												this.getCmpByName('plPawnProject.payintentPerioDate').setDisabled(false);
											}
										}
									}

									}, {
										xtype : "hidden",
										name : "plPawnProject.isStartDatePay"

									}]
						}, {
							columnWidth : 0.132,
							labelWidth : 1,
							layout : 'form',
							items : [{
										xtype : 'textfield',
										readOnly : this.isAllReadOnly,
										name : "plPawnProject.payintentPerioDate",
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
										hiddenName : "plPawnProject.payintentPerioDate",
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
												this.getCmpByName('plPawnProject.isStartDatePay').setValue("2");
												this.getCmpByName('plPawnProject.payintentPerioDate').setValue(null);
												this.getCmpByName('plPawnProject.payintentPerioDate').setDisabled(true);
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
						columnWidth : .25,
						layout : 'form',
						labelWidth : 85,
						labelAlign : 'right',
						items : [{
							xtype : 'numberfield',
							fieldLabel : '综合费用',
							readOnly : this.isAllReadOnly,
							allowBlank : false,
							fieldClass : 'field-align',
							name : 'plPawnProject.comprehensiveCost',
							anchor : '100%'
						}]
					},{
						columnWidth : .05,
						layout : 'form',
						labelWidth : 20,
						labelAlgin : 'left',
						items : [{
							fieldLabel : "元",
							labelSeparator : '',
							anchor : "100%"
						
						}]
					},{
						columnWidth : .25,
						layout : 'form',
						labelWidth : 120,
						labelAlign : 'right',
						items : [{
							fieldLabel : "本金逾期罚息利率",
							xtype : "numberfield",
							fieldClass : 'field-align',
							name : "plPawnProject.overdueRateLoan",
							decimalPrecision : 3,
							allowBlank : false,
							style : {
								imeMode : 'disabled'
							},
							readOnly : this.isAllReadOnly,
							blankText : "本金逾期罚息利率不能为空，请正确填写!",
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
						columnWidth : .25,
						layout : 'form',
						labelWidth : 120,
						labelAlign : 'right',
						items : [{
							fieldLabel : '利息逾期罚息利率',
							xtype : "textfield",
							readOnly : this.isAllReadOnly,
							name : "plPawnProject.overdueRate",
							fieldClass : 'field-align',
							allowBlank : false,
							blankText : "利息逾期罚息利率不能为空，请正确填写!",
							decimalPrecision : 3,
							anchor : '100%',
							value :0
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
					layout : 'form',
					labelWidth : 85,
					labelAlign : 'right',
					items : [{
						xtype : 'textarea',
						fieldLabel : '备注',
						anchor : '85%',
						readOnly : this.isAllReadOnly,
						name : 'plPawnProject.remarks'
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