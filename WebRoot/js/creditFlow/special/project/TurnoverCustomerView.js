// 理财产品基本信息
FinancesProductBaseInfoForm = Ext.extend(Ext.Panel, {
	layout : "form",
	border : false,
	autoHeight : true,
	labelAlign : 'right',
	mmPlanId : null,
	isAllReadOnly : false,
	constructor : function(_cfg) {
		if (_cfg == null) {
			_cfg = {};
		}
		if (typeof(_cfg.mmPlanId) != "undefined") {
			this.mmPlanId = _cfg.mmPlanId;
		}
		if (typeof(_cfg.isAllReadOnly) != "undefined") {
			this.isAllReadOnly = _cfg.isAllReadOnly;
		}
		Ext.applyIf(this, _cfg);
		var leffwidth=80;
		 var storepayintentPeriod = "[";
		for (var i = 1; i < 31; i++) {
			storepayintentPeriod = storepayintentPeriod + "[" + i + ", " + i
					+ "],";
		}
		storepayintentPeriod = storepayintentPeriod.substring(0,
				storepayintentPeriod.length - 1);
		storepayintentPeriod = storepayintentPeriod + "]";
		var obstorepayintentPeriod = Ext.decode(storepayintentPeriod);
		
		FinancesProductBaseInfoForm.superclass.constructor.call(this, {
			items : [{
				layout : "column",
				border : false,
				scope : this,
				defaults : {
					anchor : '100%',
					columnWidth : 1,
					isFormField : true,
					labelAlign : 'left'
				},
				items : [{
					columnWidth : .33,
					labelWidth : leffwidth,
					layout : 'form',
					items : [{
						xtype : 'hidden',
						id : 'plManageMoneyPlan.mmplanId',
						name : 'plManageMoneyPlan.mmplanId'
					}, {
						fieldLabel : '理财产品类型',
						xtype : 'textfield',
						hiddenName : 'plManageMoneyPlan.manageMoneyTypeId',
						readOnly : true,
						allowBlank : false,
						anchor : '100%',
						xtype : "combo",
						displayField : 'itemName',
						valueField : 'itemId',
						triggerAction : 'all',
						store : new Ext.data.ArrayStore({
							url : __ctxPath
									+ '/creditFlow/financingAgency/getListbykeystrPlManageMoneyType.do?keystr=mmproduce',
							fields : ['itemId', 'itemName'],
							autoLoad : true
						}),
						mode : 'remote',
						editable : false,
						blankText : "理财计划类型不能为空，请正确填写!",
						anchor : "100%",
						listeners : {
							scope : this,
							afterrender : function(combox) {
								var st = combox.getStore();
								st.on("load", function() {
									combox.setValue(combox.getValue());
								})
								combox.clearInvalid();
							}
						}
					}, {
						xtype : "hidden",
						name : 'plManageMoneyPlan.mmName'
					}]
				}, {
					columnWidth : .33,
					layout : 'form',
					labelWidth : leffwidth,
					items : [{
						fieldLabel : '产品编号',
						name : 'plManageMoneyPlan.mmNumber',
						readOnly : true,
						allowBlank : false,
						xtype : 'textfield',
						anchor : '100%'
					}]
				}, {
					columnWidth : .31,
					labelWidth : leffwidth,
					layout : 'form',
					items : [{
						fieldLabel : '投资期限',
						name : 'plManageMoneyPlan.investlimit',
						readOnly : true,
						fieldClass : 'field-align',
						allowBlank : false,
						xtype : 'numberfield',
						anchor : '100%'
					}]
				}, {
					columnWidth : .03, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : 18,
					items : [{
						fieldLabel : "<span style='margin-left:1px'>月</span> ",
						labelSeparator : '',
						anchor : "100%"
					}]
				}, {
					columnWidth : .33,
					layout : 'form',
					labelWidth : leffwidth,
					items : [{
						fieldLabel : '收益方式',
						name : 'plManageMoneyPlan.benefitWay',
						readOnly : true,
						xtype : 'textfield',
						anchor : '100%'
					}]
				}, {
					columnWidth : .33,
					layout : 'form',
					labelWidth : leffwidth,
					items : [{
						fieldLabel : '购买开放时间',
						name : 'plManageMoneyPlan.buyStartTime',
						readOnly : true,
						xtype : 'datetimefield',
						format : 'Y-m-d H:i:s',
						// format : 'Y-m-d',
						value : new Date(),
						anchor : '100%'
					}]
				}, {
					columnWidth : .34,
					layout : 'form',
					labelWidth : leffwidth,
					items : [{
						fieldLabel : '购买结束时间',
						name : 'plManageMoneyPlan.buyEndTime',
						xtype : 'datetimefield',
						format : 'Y-m-d H:i:s',
						// format : 'Y-m-d',
						value : new Date(),
						readOnly : true,
						anchor : '100%'
					}]
				}, {
					columnWidth : .3,
					labelWidth : leffwidth,
					layout : 'form',
					items : [{
						fieldLabel : '投资起点金额',
						name : 'plManageMoneyPlan.startMoney',
						fieldClass : 'field-align',
						xtype : 'moneyfield',
						readOnly : true,
						anchor : '100%'
					}]
				}, {
					columnWidth : .03, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : 18,
					items : [{
						fieldLabel : "<span style='margin-left:1px'>元</span> ",
						labelSeparator : '',
						anchor : "100%"
					}]
				}, {
					columnWidth : .3,
					labelWidth : leffwidth,
					layout : 'form',
					items : [{
						fieldLabel : '递增金额',
						fieldClass : 'field-align',
						xtype : 'moneyfield',
						readOnly : true,
						name : 'plManageMoneyPlan.riseMoney',
						anchor : '100%'
					}]
				}, {
					columnWidth : .03, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : 18,
					items : [{
						fieldLabel : "<span style='margin-left:1px'>元</span> ",
						labelSeparator : '',
						anchor : "100%"
					}]
				}, {
					columnWidth : .31,
					labelWidth : leffwidth,
					layout : 'form',
					items : [{
						fieldLabel : '单笔投资上限',
						xtype : 'moneyfield',
						name : 'plManageMoneyPlan.limitMoney',
						fieldClass : 'field-align',
						readOnly : true,
						anchor : '100%'
					}]
				}, {
					columnWidth : .03, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : 18,
					items : [{
						fieldLabel : "<span style='margin-left:1px'>元</span> ",
						labelSeparator : '',
						anchor : "100%"
					}]
				}, {
					columnWidth : .33,
					labelWidth : leffwidth,
					layout : 'form',
					items : [{
						fieldLabel : '起息条件',
						xtype : 'textfield',
						name : 'plManageMoneyPlan.startinInterestCondition',
						readOnly : true,
						anchor : '100%'
					}]
				}, {
					columnWidth : .33,
					labelWidth : leffwidth,
					layout : 'form',
					items : [{
						fieldLabel : '到期赎回方式',
						xtype : 'textfield',
						name : 'plManageMoneyPlan.expireRedemptionWay',
						readOnly : true,
						anchor : '100%'
					}]
				}, {
					columnWidth : .34,
					labelWidth : leffwidth,
					layout : 'form',
					items : [{
						fieldLabel : '费用收取方式',
						xtype : 'textfield',
						name : 'plManageMoneyPlan.chargeGetway',
						readOnly : true,
						anchor : '100%'
					}]
				}, {
					columnWidth : .33,
					labelWidth : leffwidth,
					layout : 'form',
					items : [{
						fieldLabel : '保障方式',
						xtype : 'textfield',
						name : 'plManageMoneyPlan.guaranteeWay',
						readOnly : true,
						anchor : '100%'
					}]
				}, {
					columnWidth : .3,
					labelWidth : leffwidth,
					layout : 'form',
					items : [{
						fieldLabel : '年化收益率',
						xtype : 'numberfield',
						allowBlank : false,
						name : 'plManageMoneyPlan.yeaRate',
						fieldClass : 'field-align',
						readOnly : true,
						anchor : '100%'
					}]
				}, {
					columnWidth : .03, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : 18,
					items : [{
						fieldLabel : "<span style='margin-left:1px'>%</span> ",
						labelSeparator : '',
						anchor : "100%"
					}]
				}, {
					columnWidth : .34,
					layout : 'form',
					labelWidth : leffwidth,
					items : [{
						fieldLabel : '是否循环出借',
						xtype : 'combo',
						mode : 'local',
						displayField : 'name',
						valueField : 'id',
						editable : false,
						readOnly : true,
						width : 70,
						store : new Ext.data.SimpleStore({
							fields : ["name", "id"],
							data : [["是", "1"], ["否", "0"]]
						}),
						triggerAction : "all",
						hiddenName : "plManageMoneyPlan.isCyclingLend",
						anchor : '100%',
						name : 'keystr',
						value : "1",
						listeners : {
							afterrender : function(combox) {
								var st = combox.getStore();
								combox.setValue(combox.getValue());
							}
						}
					}]
				}, {
					columnWidth : .34,
					layout : 'form',
					labelWidth : leffwidth,
					items : [{
						fieldLabel : '投资范围',
						name : 'plManageMoneyPlan.investScope',
						readOnly : true,
						xtype : 'textfield',
						anchor : '100%'
					}]
				},{
					columnWidth : .60,
					name : "mqhkri",
					layout : "column",
					items : [{
						columnWidth : 0.278,
						labelWidth : leffwidth,
						layout : 'form',
						items : [{
							xtype : 'radio',
							fieldLabel : '每期派息日',
							boxLabel : '固定在',
							name : 'q1',
							id : "meiqihkrq1",
							inputValue : true,
							anchor : "100%",
							disabled : this.isAllReadOnly,
							listeners : {
								scope : this,
								check : function(obj, checked) {
									var flag = Ext.getCmp("meiqihkrq1"
											).getValue();
									if (flag == true) {
										this.getCmpByName('plManageMoneyPlan.isStartDatePay').setValue("1");
										this.getCmpByName('plManageMoneyPlan.payintentPerioDate').setDisabled(false);
									}
								}
							}

						}, {
							xtype : "hidden",
							name : "plManageMoneyPlan.isStartDatePay",
							id:"plManageMoneyPlan.isStartDatePay"

						}]
					}, {
						columnWidth : 0.132,
						labelWidth : 1,
						layout : 'form',
						items : [{
							xtype : 'textfield',
							readOnly : this.isAllReadOnly,
							name : "plManageMoneyPlan.payintentPerioDate",
							id:"plManageMoneyPlan.payintentPerioDate",
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
							hiddenName : "plManageMoneyPlan.payintentPerioDate",
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
							boxLabel : '按实际投资日派息',
							name : 'q1',
							id : "meiqihkrq2",
							inputValue : true,
							checked : true,
							anchor : "100%",
							disabled : this.isAllReadOnly,
							listeners : {
								scope : this,
								check : function(obj, checked) {
									var flag = Ext.getCmp("meiqihkrq2"
										).getValue();
									if (flag == true) {
										this.getCmpByName('plManageMoneyPlan.isStartDatePay').setValue("2");
										this.getCmpByName('plManageMoneyPlan.payintentPerioDate').setValue(null);
										this.getCmpByName('plManageMoneyPlan.payintentPerioDate').setDisabled(true);
									}
								}
							}

						}]
					}]
				}, {
					columnWidth : 1,
					labelWidth : leffwidth,
					layout : 'form',
					items : [{
						fieldLabel : '产品说明',
						name : 'plManageMoneyPlan.bidRemark',
						readOnly : true,
						xtype : 'textarea',
						anchor : '100%'
					}]
				}]
			}]
		})
	}
});
//特殊计息申请单
ApplyTabelaForm=Ext.extend(Ext.Panel,{
			layout : "form",
			border : false,
			autoHeight : true,
			labelAlign : 'right',
			projectId : null,
			isAllReadOnly : true,
			apply:null,
			constructor : function(_cfg) {
				if (_cfg == null) {
					_cfg = {};
				}
				if (typeof(_cfg.projectId) != "undefined") {
					this.projectId = _cfg.projectId;
				}
				if (typeof(_cfg.isAllReadOnly) != "undefined") {
					this.isAllReadOnly = _cfg.isAllReadOnly;
				}
				Ext.applyIf(this, _cfg);
				ApplyTabelForm.superclass.constructor.call(this, {
					items : [{
						layout : "column",
						border : false,
						scope : this,
						defaults : {
							anchor : '100%',
							columnWidth : 1,
							isFormField : true,
							labelAlign:'left'
						},
						items : [{
							xtype : 'hidden',
							name : 'plManageMoneyPlan.applyId',
							value:this.apply?this.apply.applyId:''
						},{
							xtype : 'hidden',
							name : 'bpSpecialInterestApply.interestProjectId',
							value:this.projectId
						},{
							columnWidth : .31,
							layout : 'form',
							labelWidth : 85,
							items:[{
								fieldLabel : '正常收益率',
								xtype : 'numberfield',
								readOnly : true,
								anchor : "100%",
								name : 'bpSpecialInterestApply.standardInterest',
								value:this.apply?this.apply.standardInterest:''
							}]
						},{
							columnWidth:.04,
							layout:'form',
							labelWidth :30,
							items:[{
								fieldLabel : "<span style='margin-left:1px'>%/年</span> ",
								labelSeparator : '',
								anchor : "100%"
							}]
						},{
							columnWidth : .3,
							layout : 'form',
							labelWidth : 85,
							items:[{
								fieldLabel : '实际收益率',
								xtype : 'numberfield',
								readOnly : this.isAllReadOnly,
								anchor : "100%",
								name : 'bpSpecialInterestApply.applyInterest',
								value:this.apply?this.apply.applyInterest:0,
								listeners : {
									scope:this,
									change:function(v){
										var contractMoney=this.applyBaseInfoForm.getCmpByName('bpSpecialInterest.contractMoney');
										if(typeof(contractMoney.value)=="undefined"){
											Ext.ux.Toast.msg('操作信息','请先填写合同金额!');
											v.setValue(0);
											return;
										}else{
											var standardInterest=this.getOriginalContainer().getCmpByName('bpSpecialInterestApply.standardInterest');
											var yieldBalance=this.getOriginalContainer().getCmpByName('bpSpecialInterestApply.yieldBalance');
											var plan=this.getOriginalContainer().plManageMoneyPlan;
											if(standardInterest.value){
												var value=(v.value-standardInterest.value)*plan.investlimit*30*contractMoney.value/(360*100);
												yieldBalance.setValue(value.toFixed(2));
											}
											yieldBalance.fireEvent('change',yieldBalance);
										}
									}
								}
							}]
						},{
							columnWidth:.05,
							layout:'form',
							labelWidth :30,
							items:[{
								fieldLabel : "<span style='margin-left:1px'>%/年</span> ",
								labelSeparator : '',
								anchor : "100%"
							}]
						},{
							columnWidth : .28,
							layout : 'form',
							labelWidth : 85,
							items:[{
								fieldLabel : '收益率损失',
								xtype : 'numberfield',
								readOnly : true,
								anchor : "100%",
								editable:false,
								name : 'bpSpecialInterestApply.yieldBalance',
								value:this.apply?this.apply.yieldBalance:0,
								listeners : {
									scope:this,
									change:function(v){
										//计息差额
										var interestBalance=this.getOriginalContainer().getCmpByName('bpSpecialInterestApply.interestBalance');
										//返点差额
										var rebateBalance=this.getOriginalContainer().getCmpByName('bpSpecialInterestApply.rebateBalance');
										//申请返还现金金额
										var applyReturnCash=this.getOriginalContainer().getCmpByName('bpSpecialInterestApply.applyReturnCash');
										//损失汇总
										var lossTotal=this.getOriginalContainer().getCmpByName('bpSpecialInterestApply.lossTotal');
										var value1=0;
										var value2=0;
										var value3=0;
										var value4=0;
										var total=0;
										if(interestBalance.value){
											value1=interestBalance.value;
										}
										if(rebateBalance.value){
											value2=rebateBalance.value;
										}
										if(applyReturnCash.value){
											value3=applyReturnCash.value;
										}
										if(v.value){
											value4=v.value;
										}
										total=parseFloat(value1)+parseFloat(value2)+parseFloat(value3)+parseFloat(value4)
										lossTotal.setValue(total.toFixed(2));
									}
								}
							}]
						},{
							columnWidth:.02,
							layout:'form',
							labelWidth :15,
							items:[{
								fieldLabel : "<span style='margin-left:1px'>元</span> ",
								labelSeparator : '',
								anchor : "100%"
							}]
						},{
							columnWidth : .35,
							layout : 'form',
							labelWidth : 85,
							items : [{
								fieldLabel : '正常计息日',
								editable:false,
								xtype : 'datefield',
								style : {
									imeMode : 'disabled'
								},
								format : 'Y-m-d',
								anchor:'88%',
								name : 'bpSpecialInterestApply.normalInterestDay',
								value:this.apply?this.apply.normalInterestDay:'',
								readOnly : this.isAllReadOnly,
								listeners : {
									scope:this,
									change:function(v){
										var contractMoney=this.applyBaseInfoForm.getCmpByName('bpSpecialInterest.contractMoney');
										if(typeof(contractMoney.value)=="undefined"){
											Ext.ux.Toast.msg('操作信息','请先填写合同金额!');
											v.setValue(0);
											return;
										}else{
											var actualInterestDay=this.getOriginalContainer().getCmpByName('bpSpecialInterestApply.actualInterestDay');
											var interestBalance=this.getOriginalContainer().getCmpByName('bpSpecialInterestApply.interestBalance');
											var plan=this.getOriginalContainer().plManageMoneyPlan;
											if(actualInterestDay.value){
												var value=DateDiff(v.value,actualInterestDay.value)*plan.investlimit*contractMoney.value/(360*100);
												interestBalance.setValue(value.toFixed(2));
												interestBalance.fireEvent('change',interestBalance);
											}
										}
									}
								}
							}]
						},{
							columnWidth : .35,
							layout : 'form',
							labelWidth : 85,
							items : [{
								fieldLabel : '实际计息日',
								editable:false,
								xtype : 'datefield',
								style : {
									imeMode : 'disabled'
								},
								format : 'Y-m-d',
								anchor:'86%',
								name : 'bpSpecialInterestApply.actualInterestDay',
								value:this.apply?this.apply.actualInterestDay:'',
								readOnly : this.isAllReadOnly,
								listeners : {
									scope:this,
									change:function(v){
										var contractMoney=this.applyBaseInfoForm.getCmpByName('bpSpecialInterest.contractMoney');
										if(typeof(contractMoney.value)=="undefined"){
											Ext.ux.Toast.msg('操作信息','请先填写合同金额!');
											v.setValue(0);
											return;
										}else{
											var normalInterestDay=this.getOriginalContainer().getCmpByName('bpSpecialInterestApply.normalInterestDay');
											var interestBalance=this.getOriginalContainer().getCmpByName('bpSpecialInterestApply.interestBalance');
											var plan=this.getOriginalContainer().plManageMoneyPlan;
											if(normalInterestDay.value){
												var value=DateDiff(normalInterestDay.value,v.value)*plan.yeaRate*contractMoney.value/(360*100);
												interestBalance.setValue(value.toFixed(2));
												interestBalance.fireEvent('change',interestBalance);
											}
										}
									}
								}
							}]
						},{
							columnWidth : .28,
							layout : 'form',
							labelWidth : 85,
							items:[{
								fieldLabel : '计息损失',
								xtype : 'numberfield',
								readOnly : true,
								anchor : "100%",
								name : 'bpSpecialInterestApply.interestBalance',
								value:this.apply?this.apply.interestBalance:0,
								listeners : {
									scope:this,
									change:function(v){
										//收益率差额
										var interestBalance=this.getOriginalContainer().getCmpByName('bpSpecialInterestApply.yieldBalance');
										//返点差额
										var rebateBalance=this.getOriginalContainer().getCmpByName('bpSpecialInterestApply.rebateBalance');
										//申请返还现金金额
										var applyReturnCash=this.getOriginalContainer().getCmpByName('bpSpecialInterestApply.applyReturnCash');
										//损失汇总
										var lossTotal=this.getOriginalContainer().getCmpByName('bpSpecialInterestApply.lossTotal');
										var value1=0;
										var value2=0;
										var value3=0;
										var value4=0;
										var total=0;
										if(interestBalance.value){
											value1=interestBalance.value;
										}
										if(rebateBalance.value){
											value2=rebateBalance.value;
										}
										if(applyReturnCash.value){
											value3=applyReturnCash.value;
										}
										if(v.value){
											value4=v.value;
										}
										total=parseFloat(value1)+parseFloat(value2)+parseFloat(value3)+parseFloat(value4)
										lossTotal.setValue(total.toFixed(2));
									}
								}
							}]
						},{
							columnWidth:.02,
							layout:'form',
							labelWidth :15,
							items:[{
								fieldLabel : "<span style='margin-left:1px'>元</span> ",
								labelSeparator : '',
								anchor : "100%"
							}]
						},{
							columnWidth : .31,
							layout : 'form',
							labelWidth : 85,
							items:[{
								fieldLabel : '正常返点',
								xtype : 'numberfield',
								readOnly : this.isAllReadOnly,
								anchor : "100%",
								name : 'bpSpecialInterestApply.normalRebate',
								value:this.apply?this.apply.normalRebate:0,
								listeners : {
									scope:this,
									change:function(v){
										var contractMoney=this.applyBaseInfoForm.getCmpByName('bpSpecialInterest.contractMoney');
										if(typeof(contractMoney.value)=="undefined"){
											Ext.ux.Toast.msg('操作信息','请先填写合同金额!');
											v.setValue(0);
											return;
										}else{
											var actualRebate=this.getOriginalContainer().getCmpByName('bpSpecialInterestApply.actualRebate');
											var rebateBalance=this.getOriginalContainer().getCmpByName('bpSpecialInterestApply.rebateBalance');
											var plan=this.getOriginalContainer().plManageMoneyPlan;
											if(actualRebate.value){
												var value=(actualRebate.value-v.value)*plan.investlimit*30*contractMoney.value/(360*100);
												rebateBalance.setValue(value.toFixed(2));
												rebateBalance.fireEvent('change',rebateBalance);
											}
										}
									}
								}
							}]
						},{
							columnWidth:.04,
							layout:'form',
							labelWidth :10,
							items:[{
								fieldLabel : "<span style='margin-left:1px'>%</span> ",
								labelSeparator : '',
								anchor : "100%"
							}]
						},{
							columnWidth : .3,
							layout : 'form',
							labelWidth : 85,
							items:[{
								fieldLabel : '实际返点',
								xtype : 'numberfield',
								readOnly : this.isAllReadOnly,
								anchor : "100%",
								name : 'bpSpecialInterestApply.actualRebate',
								value:this.apply?this.apply.actualRebate:0,
								listeners : {
									scope:this,
									change:function(v){
										var contractMoney=this.applyBaseInfoForm.getCmpByName('bpSpecialInterest.contractMoney');
										if(typeof(contractMoney.value)=="undefined"){
											Ext.ux.Toast.msg('操作信息','请先填写合同金额!');
											v.setValue(0);
											return;
										}else{
											var normalRebate=this.getOriginalContainer().getCmpByName('bpSpecialInterestApply.normalRebate');
											var rebateBalance=this.getOriginalContainer().getCmpByName('bpSpecialInterestApply.rebateBalance');
											var plan=this.getOriginalContainer().plManageMoneyPlan;
											if(normalRebate.value){
												var value=(v.value-normalRebate.value)*plan.investlimit*30*contractMoney.value/(360*100);
												rebateBalance.setValue(value.toFixed(2));
												rebateBalance.fireEvent('change',rebateBalance);
											}
										}
									}
								}
							}]
						},{
							columnWidth:.05,
							layout:'form',
							labelWidth :10,
							items:[{
								fieldLabel : "<span style='margin-left:1px'>%</span> ",
								labelSeparator : '',
								anchor : "100%"
							}]
						},{
							columnWidth : .28,
							layout : 'form',
							labelWidth : 85,
							items:[{
								fieldLabel : '返点损失',
								xtype : 'numberfield',
								readOnly : true,
								anchor : "100%",
								name : 'bpSpecialInterestApply.rebateBalance',
								value:this.apply?this.apply.rebateBalance:0,
								listeners : {
									scope:this,
									change:function(v){
										//收益率差额
										var yieldBalance=this.getOriginalContainer().getCmpByName('bpSpecialInterestApply.yieldBalance');
										//计息差额
										var interestBalance=this.getOriginalContainer().getCmpByName('bpSpecialInterestApply.interestBalance');
										//申请返还现金金额
										var applyReturnCash=this.getOriginalContainer().getCmpByName('bpSpecialInterestApply.applyReturnCash');
										//损失汇总
										var lossTotal=this.getOriginalContainer().getCmpByName('bpSpecialInterestApply.lossTotal');
										var value1=0;
										var value2=0;
										var value3=0;
										var value4=0;
										var total=0;
										if(interestBalance.value){
											value1=interestBalance.value;
										}
										if(yieldBalance.value){
											value2=yieldBalance.value;
										}
										if(applyReturnCash.value){
											value3=applyReturnCash.value;
										}
										if(v.value){
											value4=v.value;
										}
										total=parseFloat(value1)+parseFloat(value2)+parseFloat(value3)+parseFloat(value4)
										lossTotal.setValue(total.toFixed(2));
									}
								}
							}]
						},{
							columnWidth:.02,
							layout:'form',
							labelWidth :15,
							items:[{
								fieldLabel : "<span style='margin-left:1px'>元</span> ",
								labelSeparator : '',
								anchor : "100%"
							}]
						},{
							columnWidth : .31,
							layout : 'form',
							labelWidth : 100,
							items:[{
								fieldLabel : '申请返还现金金额',
								xtype : 'numberfield',
								readOnly : this.isAllReadOnly,
								anchor : "100%",
								name : 'bpSpecialInterestApply.applyReturnCash',
								value:this.apply?this.apply.applyReturnCash:0,
								maxLength : 50,
								listeners : {
									scope:this,
									change:function(v){
										//收益率差额
										var yieldBalance=this.getOriginalContainer().getCmpByName('bpSpecialInterestApply.yieldBalance');
										//计息差额
										var interestBalance=this.getOriginalContainer().getCmpByName('bpSpecialInterestApply.interestBalance');
										//返点差额
										var rebateBalance=this.getOriginalContainer().getCmpByName('bpSpecialInterestApply.rebateBalance');
										//损失汇总
										var lossTotal=this.getOriginalContainer().getCmpByName('bpSpecialInterestApply.lossTotal');
										var value1=0;
										var value2=0;
										var value3=0;
										var value4=0;
										var total=0;
										if(interestBalance.value){
											value1=interestBalance.value;
										}
										if(yieldBalance.value){
											value2=yieldBalance.value;
										}
										if(rebateBalance.value){
											value3=rebateBalance.value;
										}
										if(v.value){
											value4=v.value;
										}
										total=parseFloat(value1)+parseFloat(value2)+parseFloat(value3)+parseFloat(value4)
										lossTotal.setValue(total.toFixed(2));
									}/*,
									afterrender:function(nf){
										var value = nf.getValue();
										var index = value.toString().indexOf(".");
										if(this.isAllReadOnly){
											if (index <= 0) {// 如果第一次输入 没有进行格式化
												nf.setValue(Ext.util.Format.number(value,'0,000.00'))
											} else {
												if (value.indexOf(",") <= 0) {
													var ke = Ext.util.Format.number(value,'0,000.00')
													nf.setValue(ke);
												} else {
													var last = value.substring(index + 1,value.length);
													if (last == 0) {
														var temp = value.substring(0, index);
														temp = temp.replace(/,/g, "");
														nf.setValue(Ext.util.Format.number(temp,'0,000.00'))
													} else {
														var temp = value.replace(/,/g, "");
														nf.setValue(Ext.util.Format.number(temp,'0,000.00'))
													}
												}
											}
										}else{
											return value;
										}
									}*/
								}
							}]
						},{
							columnWidth:.04,
							layout:'form',
							labelWidth :15,
							items:[{
								fieldLabel : "<span style='margin-left:1px'>元</span> ",
								labelSeparator : '',
								anchor : "100%"
							}]
						},{
							columnWidth : .35,
							layout : 'form',
							labelWidth : 85,
							items : [{
								fieldLabel : '预计返现时间',
								editable:false,
								xtype : 'datefield',
								style : {
									imeMode : 'disabled'
								},
								format : 'Y-m-d',
								anchor:'86%',
								name : 'bpSpecialInterestApply.predictReturnDay',
								readOnly : this.isAllReadOnly,
								value:this.apply?this.apply.predictReturnDay:''
							}]
						},{
							columnWidth : .28,
							layout : 'form',
							labelWidth : 85,
							items:[{
								fieldLabel : '损失汇总',
								xtype : 'numberfield',
								readOnly : true,
								anchor : "100%",
								name : 'bpSpecialInterestApply.lossTotal',
								value:this.apply?this.apply.lossTotal:0,
								maxLength : 50
								/*listeners : {
									scope:this,
									afterrender:function(nf){
										var value = nf.getValue();
										var index = value.toString().indexOf(".");
										if(this.isAllReadOnly){
											if (index <= 0) {// 如果第一次输入 没有进行格式化
												nf.setValue(Ext.util.Format.number(value,'0,000.00'))
											} else {
												if (value.indexOf(",") <= 0) {
													var ke = Ext.util.Format.number(value,'0,000.00')
													nf.setValue(ke);
												} else {
													var last = value.substring(index + 1,value.length);
													if (last == 0) {
														var temp = value.substring(0, index);
														temp = temp.replace(/,/g, "");
														nf.setValue(Ext.util.Format.number(temp,'0,000.00'))
													} else {
														var temp = value.replace(/,/g, "");
														nf.setValue(Ext.util.Format.number(temp,'0,000.00'))
													}
												}
											}
										}else{
											return value;
										}
									}
								}*/
							}]
						},{
							columnWidth:.02,
							layout:'form',
							labelWidth :10,
							items:[{
								fieldLabel : "<span style='margin-left:1px'>元</span> ",
								labelSeparator : '',
								anchor : "100%"
							}]
						}]
					}]
				});
			}
		});
//购买信息
FinancesProductBuyInfoForm = Ext.extend(Ext.Panel, {
	layout : "form",
	border : false,
	autoHeight : true,
	labelAlign : 'right',
	isAllreadOnly : false,
	isReadOny:true,
	constructor : function(_cfg) {
		if (_cfg == null) {
			_cfg = {};
		}
		if (typeof(_cfg.isAllReadOnly) != "undefined") {
			this.isAllreadOnly = _cfg.isAllReadOnly;
		}
		if (typeof(_cfg.isReadOny) != "undefined") {
			this.isReadOny = _cfg.isReadOny;
		}
		Ext.applyIf(this, _cfg);
		var leffwidth=80;
		FinancesProductBuyInfoForm.superclass.constructor.call(this, {
			items : [{
				layout : "column",
				border : false,
				scope : this,
				defaults : {
					anchor : '100%',
					columnWidth : 1,
					isFormField : true,
					labelAlign : 'left'
				},
				items:[{
					xtype:'hidden',
					name:'plManageMoneyPlanBuyinfo.orderId'
				},{
					xtype:'hidden',
					name:'plManageMoneyPlanBuyinfo.investPersonId'
				},{
					xtype:'hidden',
					name:'plManageMoneyPlanBuyinfo.currentMatchingMoney'
				},{
						columnWidth : .33,
						labelWidth : leffwidth,
						layout : 'form',
						items : [{
							fieldLabel : '投资人',
							xtype:'textfield',
							readOnly : this.isAllreadOnly,
							anchor:'100%',
							name:'plManageMoneyPlanBuyinfo.investPersonName'
						}]
					}, {
						columnWidth : .3,
						layout : 'form',
						labelWidth : leffwidth,
						items : [{
							fieldLabel : '购买金额',
							name : 'plManageMoneyPlanBuyinfo.buyMoney',
							allowBlank : false,
							readOnly : this.isAllreadOnly && this.isReadOny,
							fieldClass : 'field-align',
							xtype : 'moneyfield',
							anchor : '100%'
						}]
					}, {
						columnWidth : .03, // 该列在整行中所占的百分比
						layout : "form", // 从上往下的布局
						labelWidth : 18,
						items : [{
							fieldLabel : "<span style='margin-left:1px'>元</span> ",
							labelSeparator : '',
							anchor : "100%"
						}]
					}, {
						columnWidth : .34,
						layout : 'form',
						labelWidth : leffwidth,
						items : [{
							fieldLabel : '购买时间',
							name : 'plManageMoneyPlanBuyinfo.buyDatetime',
							xtype : 'datefield',
							readOnly : this.isAllreadOnly && this.isReadOny,
							format : "Y-m-d",
							allowBlank : false,
							anchor : '100%'
						}]
					}, {
						columnWidth : .33,
						layout : 'form',
						labelWidth : leffwidth,
						items : [{
							fieldLabel : '计息起日',
							name : 'plManageMoneyPlanBuyinfo.startinInterestTime',
							xtype : 'datefield',
							readOnly : this.isAllreadOnly && this.isReadOny,
							format : 'Y-m-d',
							allowBlank : false,
							anchor : '100%',
							listeners : {
							scope : this,
							'change' : function(nf){
								var dtstr = Ext.util.Format.date(nf.getValue(), 'Y-m-d');
								var investlimit=this.getOriginalContainer().getCmpByName('projectInfo').items.items[0].getCmpByName('plManageMoneyPlan.investlimit').getValue();
								var n=parseInt(investlimit);
								 var s=dtstr.split("-");
									   var yy=parseInt(s[0]);
									   var mm=parseInt(s[1])-1;
									   var dd=parseInt(s[2]);
									   var dt=new Date(yy,mm,dd);
									   dt.setMonth(dt.getMonth()+n);
									   if( (dt.getYear()*12+dt.getMonth()) > (yy*12+mm + n) )
									   	{dt=new Date(dt.getYear(),dt.getMonth(),0);}
									   	
									   	 var t=dt.getTime()-1000*60*60*24;
 										var yesterday=new Date(t);
								      this.getCmpByName("plManageMoneyPlanBuyinfo.endinInterestTime").setValue(yesterday);
								      this.getCmpByName("plManageMoneyPlanBuyinfo.orderlimit").setValue(30*n)
								}
							}
						}]
					}, {
						columnWidth : .33,
						layout : 'form',
						labelWidth : leffwidth,
						items : [{
							fieldLabel : '计息止日',
							name : 'plManageMoneyPlanBuyinfo.endinInterestTime',
							readOnly : this.isAllreadOnly,
							xtype : 'datefield',
							allowBlank : false,
							format : 'Y-m-d',
							anchor : '91%'
						}]
					}, {
						columnWidth : .3,
						layout : 'form',
						labelWidth : leffwidth,
						items : [{
							fieldLabel : '订单期限',
							readOnly : this.isAllreadOnly,
							name : 'plManageMoneyPlanBuyinfo.orderlimit',
//							id : 'plManageMoneyPlanBuyinfo.orderlimit',
							xtype : 'hidden',
							anchor : '100%'
						}]
					},{
						columnWidth : .34,
						layout : 'form',
						labelWidth : leffwidth,
						items : [{
							fieldLabel : '合同编号',
							name : 'plManageMoneyPlanBuyinfo.contractNumber',
							readOnly : this.isAllreadOnly,
							fieldClass : 'field-align',
							xtype : 'textfield',
							anchor : '100%'
						}]
					},{
						columnWidth : .33,
						layout : 'form',
						labelWidth : leffwidth,
						items : [{
							xtype : 'combo',
							fieldLabel : '有无特殊计息',
							mode : 'local',
							displayField : 'name',
							readOnly : this.isAllreadOnly,
							valueField : 'id',
							editable : false,
							width : 70,
							store : new Ext.data.SimpleStore({
								fields : ["name", "id"],
								data : [["是", "1"],["否", "0"]]
							}),
							triggerAction : "all",
							hiddenName : "plManageMoneyPlanBuyinfo.isHaveSpecialInterest",
							anchor : '100%',
							name : 'specialInterest',
							value:"1",
							listeners : {
								scope:this,
								change:function(v){
									if(0==v.getValue()){
										this.getCmpByName('SPBH').hide();
									}else{
										this.getCmpByName('SPBH').show();
									}
								},
								render:function(v){
									if(0==v.getValue()){
										    Ext.getCmp('ApplyTabelFormd').hide();
										}else{
											Ext.getCmp('ApplyTabelFormd').show();
										}
									}
							}
						}]
					},{
						columnWidth : .33,
						layout : 'form',
						name:'SPBH',
						labelWidth : leffwidth,
						items : [{
							fieldLabel : '审批编号',
							readOnly : this.isAllreadOnly,
							xtype : 'textfield',
							name : 'plManageMoneyPlanBuyinfo.approvalNumber',
							anchor : '91%'
						}]
					}
				]
			}]
		})
	}
})

//其他信息
OtherInfoForm = Ext.extend(Ext.Panel, {
	layout : "form",
	border : false,
	autoHeight : true,
	labelAlign : 'right',
	isAllreadOnly : false,
	constructor : function(_cfg) {
		if (_cfg == null) {
			_cfg = {};
		}
		if (typeof(_cfg.isAllreadOnly) != "undefined") {
			this.isAllreadOnly = _cfg.isAllreadOnly;
		}
		Ext.applyIf(this, _cfg);
		var leffwidth=80;
		OtherInfoForm.superclass.constructor.call(this, {
			items : [{
				layout : "column",
				border : false,
				scope : this,
				defaults : {
					anchor : '100%',
					columnWidth : 1,
					isFormField : true,
					labelAlign : 'left'
				},
				items:[{
					columnWidth:.25,
					layout:'form',
					fieldClass : 'field-align',
					anchor : "100%",
					items:[{
						xtype:'hidden',
						name:'plManageMoneyPlanOtherInfo.id'
					},{
						fieldLabel : "投资方式",
						xtype : "dickeycombo",
						readOnly : this.isAllReadOnly,
						hiddenName : 'plManageMoneyPlanOtherInfo.receiveType',
						nodeKey : 'plManageMoneyPlanOtherInfo_receiveType',
						editable:false,
						emptyText : "请选择",
						anchor : "100%",
						listeners : {
							afterrender : function(combox) {
								var st = combox.getStore();
								st.on("load", function() {
									if (combox.getValue() == null) {
										st.load({
											"callback" : function() {
												if (st.getCount() > 0) {
													var record = st.getAt(0);
													var v = record.data.itemId;
													combox.setValue(v);
												}
											}
										});
									}
									if (combox.getValue() > 0) {
										combox.setValue(combox.getValue());
									}
								})
							}
						}
					}]
				},{
					columnWidth:.25,
					layout:'form',
					fieldClass : 'field-align',
					anchor : "100%",
					items:[{
						xtype : "combo",
						triggerClass : 'x-form-search-trigger',
						hiddenName : "plManageMoneyPlanOtherInfo.customerPrecidentName",
						editable : false,
						fieldLabel : "客户经理",
						readOnly : this.isAllReadOnly,
						anchor : "88%",
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
								title : "选择客户经理",
								callback : function(uId, uname) {
									obj.setValue(uId);
									obj.setRawValue(uname);
									appuerIdObj.setValue(uId);
								}
							}).show();
						}
					}, {
						xtype : "hidden",
						name:'plManageMoneyPlanOtherInfo.customerPrecidentId'
					}]
				},{
					columnWidth:.25,
					layout:'form',
					fieldClass : 'field-align',
					anchor : "100%",
					items:[{
						xtype : "combo",
						triggerClass : 'x-form-search-trigger',
						hiddenName : "plManageMoneyPlanOtherInfo.teamManagerName",
						editable : false,
						fieldLabel : "团队经理",
						readOnly : this.isAllReadOnly,
						anchor : "88%",
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
								title : "选择团队经理",
								callback : function(uId, uname) {
									obj.setValue(uId);
									obj.setRawValue(uname);
									appuerIdObj.setValue(uId);
								}
							}).show();
						}
					},{
						xtype : "hidden",
						name:'plManageMoneyPlanOtherInfo.teamManagerId'
					}]
				},{
					columnWidth:.25,
					layout:'form',
					fieldClass : 'field-align',
					anchor : "100%",
					items:[{
						xtype : "combo",
						triggerClass : 'x-form-search-trigger',
						hiddenName : "plManageMoneyPlanOtherInfo.belongsDepName",
						editable : false,
						fieldLabel : "所属部门",
						readOnly : this.isAllReadOnly,
						anchor : "88%",
						onTriggerClick : function(cc) {
							var obj = this;
							var appuerIdObj = obj.nextSibling();
							var userIds = appuerIdObj.getValue();
							if ("" == obj.getValue()) {
								userIds = "";
							}
							new DepDialog({
								userIds : userIds,
								userName : obj.getValue(),
								single : false,
								title : "选择所属部门",
								callback : function(uId, uname) {
									obj.setValue(uId);
									obj.setRawValue(uname);
									appuerIdObj.setValue(uId);
								}
							}).show();
						}
					},{
						xtype : "hidden",
						name:'plManageMoneyPlanOtherInfo.belongsDepId'
					}]
				},{
					columnWidth:.25,
					layout:'form',
					fieldClass : 'field-align',
					anchor : "100%",
					items:[{
						fieldLabel : "接收债权方式",
						xtype : "dickeycombo",
						readOnly : this.isAllReadOnly,
						hiddenName : 'plManageMoneyPlanOtherInfo.investTypeId',
						nodeKey : 'plManageMoneyPlanOtherInfo_investTypeId',
						editable:false,
						emptyText : "请选择",
						anchor : "100%",
						listeners : {
							scope:this,
							afterrender : function(combox) {
								var st = combox.getStore();
								st.on("load", function() {
									if (combox.getValue() == null) {
										st.load({
											"callback" : function() {
												if (st.getCount() > 0) {
													var record = st.getAt(0);
													var v = record.data.itemId;
													combox.setValue(v);
												}
											}
										});
									}
									if (combox.getValue() > 0) {
										combox.setValue(combox.getValue());
									}
									combox.fireEvent('change',combox);
								})
							}/*,
							change:function(combox){
								var obj = this.getCmpByName('postMoney');
								var obj2 = this.getCmpByName('bankName');
								var text=combox.lastSelectionText;
								if("现金"==text || "Pos机"==text || "委托划扣"==text ){
									obj.fieldLabel=text;
									obj.show();
									obj2.hide();
								}else{
									obj.fieldLabel="付款银行";
									obj.hide();
									obj2.show();
								}
							}*/
						}
					}]
				},{
					columnWidth:.25,
					layout:'form',
					fieldClass : 'field-align',
//					hidden:true,
//					name:'postMoney',
					anchor : "100%",
					items:[{
						fieldLabel : "投资方式说明",
						readOnly : this.isAllReadOnly,
						xtype : "textfield",
						name : 'plManageMoneyPlanOtherInfo.postMoney',
						anchor : "88%"
					}]
				}/*,{
					columnWidth:.25,
					layout:'form',
					fieldClass : 'field-align',
					name:'bankName',
					anchor : "100%",
					items:[{
						fieldLabel : "付款银行",
						xtype : "combo",
						displayField : 'itemName',
						valueField : 'itemId',
						readOnly : this.isAllReadOnly,
						triggerAction : 'all',
						store : new Ext.data.ArrayStore({
							url : __ctxPath+ '/creditFlow/common/getBankListCsBank.do',
							fields : ['itemId', 'itemName'],
							autoLoad : true
						}),
						mode : 'remote',
						hiddenName : "plManageMoneyPlanOtherInfo.bankId",
						editable : false,
						anchor : "88%",
						listeners : {
							scope : this,
							afterrender : function(combox) {
								var st = combox.getStore();
								st.on("load", function() {
									combox.setValue(combox.getValue());
								})
								combox.clearInvalid();
							}
						}
					}]
				}*/,{
					columnWidth:.25,
					layout:'form',
					fieldClass : 'field-align',
					anchor : "100%",
					items:[{
						fieldLabel : "对账单邮寄部门",
						xtype : "dickeycombo",
						hiddenName : 'plManageMoneyPlanOtherInfo.postDepId',
						nodeKey : 'plManageMoneyPlanOtherInfo_postDepId',
						displayField : 'itemName',
						readOnly : this.isAllReadOnly,
						editable : false,
						anchor : "88%",
						listeners : {
							afterrender : function(combox) {
								var st = combox.getStore();
								st.on("load", function() {
									if(combox.getValue()){
										combox.setValue(combox.getValue());
									}else{
										var record = st.getAt(1);
										if(record){
											var v = record.data.itemId;
											combox.setValue(v);
										}
									}
								})
							}
						}
					}
					/*{
						xtype:'textfield',
						name:'plManageMoneyPlanOtherInfo.postDepName',
						fieldLabel : "对账单邮寄部门",
						readOnly : this.isAllReadOnly,
						anchor : "88%"
					}*//*{
						xtype : "combo",
						triggerClass : 'x-form-search-trigger',
						hiddenName : "plManageMoneyPlanOtherInfo.postDepName",
						editable : false,
						fieldLabel : "对账单邮寄部门",
						readOnly : this.isAllReadOnly,
						anchor : "88%",
						onTriggerClick : function(cc) {
							var obj = this;
							var appuerIdObj = obj.nextSibling();
							var userIds = appuerIdObj.getValue();
							if ("" == obj.getValue()) {
								userIds = "";
							}
							new DepDialog({
								userIds : userIds,
								userName : obj.getValue(),
								single : false,
								title : "选择对账单邮寄部门",
								callback : function(uId, uname) {
									obj.setValue(uId);
									obj.setRawValue(uname);
									appuerIdObj.setValue(uId);
								}
							}).show();
						}
					},{
						xtype : "hidden",
						name:'plManageMoneyPlanOtherInfo.postDepId'
					}*/]
				}]
			}]
		})
	}
});

//礼品申领信息
GiftInfoForm = Ext.extend(Ext.Panel, {
	layout : "form",
	border : false,
	autoHeight : true,
	labelAlign : 'right',
	isAllreadOnly : false,
	constructor : function(_cfg) {
		if (_cfg == null) {
			_cfg = {};
		}
		if (typeof(_cfg.isAllreadOnly) != "undefined") {
			this.isAllreadOnly = _cfg.isAllreadOnly;
		}
		Ext.applyIf(this, _cfg);
		GiftInfoForm.superclass.constructor.call(this, {
			items : [{
				layout : "column",
				border : false,
				scope : this,
				defaults : {
					anchor : '100%',
					columnWidth : 1,
					isFormField : true,
					labelAlign : 'left'
				},
				items:[{
					columnWidth:.2,
					layout:'form',
					fieldClass : 'field-align',
					anchor : "100%",
					items:[{
						xtype:'hidden',
						name:'plManageMoneyPlanGiftInfo.giftId'
					},{
						fieldLabel : "申领信息类别",
						xtype : "dickeycombo",
						hiddenName : 'plManageMoneyPlanGiftInfo.giftType',
						nodeKey : 'plManageMoneyPlanGiftInfo_giftType',
						editable:false,
						readOnly : this.isAllReadOnly,
						emptyText : "请选择",
						anchor : "100%",
						listeners : {
							afterrender : function(combox) {
								var st = combox.getStore();
								st.on("load", function() {
									if (combox.getValue() == null) {
										st.load({
											"callback" : function() {
												if (st.getCount() > 0) {
													var record = st.getAt(0);
													var v = record.data.itemId;
													combox.setValue(v);
												}
											}
										});
									}
									if (combox.getValue() > 0) {
										combox.setValue(combox.getValue());
									}
								})
							}
						}
					}]
				},{
					columnWidth:.2,
					layout:'form',
					fieldClass : 'field-align',
					anchor : "100%",
					items:[{
						xtype : 'textfield',
						fieldLabel : "名称 ",
						readOnly : this.isAllReadOnly,
						name:'plManageMoneyPlanGiftInfo.giftName',
						fieldClass : 'field-align',
						anchor : "100%"
					}]
				},{
					columnWidth:.2,
					layout:'form',
					fieldClass : 'field-align',
					anchor : "100%",
					items:[{
						xtype : "combo",
						triggerClass : 'x-form-search-trigger',
						hiddenName : "plManageMoneyPlanGiftInfo.applyDepName",
						editable : false,
						fieldLabel : "申请部门",
						readOnly : this.isAllReadOnly,
						anchor : "88%",
						onTriggerClick : function(cc) {
							var obj = this;
							var appuerIdObj = obj.nextSibling();
							var userIds = appuerIdObj.getValue();
							if ("" == obj.getValue()) {
								userIds = "";
							}
							new DepDialog({
								userIds : userIds,
								userName : obj.getValue(),
								single : false,
								title : "选择申请部门",
								callback : function(uId, uname) {
									obj.setValue(uId);
									obj.setRawValue(uname);
									appuerIdObj.setValue(uId);
								}
							}).show();
						}
					}, {
						xtype : "hidden",
						name:'plManageMoneyPlanGiftInfo.applyDepId'
					}]
				},{
					columnWidth:.2,
					layout:'form',
					fieldClass : 'field-align',
					anchor : "100%",
					items:[{
						xtype : "combo",
						triggerClass : 'x-form-search-trigger',
						hiddenName : "plManageMoneyPlanGiftInfo.applyUserName",
						editable : false,
						fieldLabel : "申请人",
						readOnly : this.isAllReadOnly,
						anchor : "88%",
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
								title : "选择申请人",
								callback : function(uId, uname) {
									obj.setValue(uId);
									obj.setRawValue(uname);
									appuerIdObj.setValue(uId);
								}
							}).show();
						}
					},{
						xtype : "hidden",
						name:'plManageMoneyPlanGiftInfo.applyUserId'
					}]
				},{
					columnWidth:.2,
					layout:'form',
					fieldClass : 'field-align',
					anchor : "100%",
					items:[{
						fieldLabel : '申请日期',
						readOnly : this.isAllReadOnly,
						name : 'plManageMoneyPlanGiftInfo.applyDate',
						xtype : 'datefield',
						format : 'Y-m-d',
						anchor : "90%"
					}]
				},{
					columnWidth:1,
					layout:'form',
					fieldClass : 'field-align',
					anchor : "100%",
					items:[{
						xtype : 'textarea',
						readOnly : this.isAllReadOnly,
						fieldLabel : "申请原因 ",
						name:'plManageMoneyPlanGiftInfo.applyReason',
						anchor : "98%"
					}]
				}]
			}]
		})
	}
});