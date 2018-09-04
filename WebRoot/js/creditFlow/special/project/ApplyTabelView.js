 //判断两个日期间天数
 function  DateDiff(sDate1,  sDate2){    //sDate1和sDate2是2006-12-18格式  
	   var  aDate,  oDate1,  oDate2,  iDays  
	   aDate  =  sDate1.split("-")  
	   oDate1  =  new  Date(aDate[1]  +  '-'  +  aDate[2]  +  '-'  +  aDate[0])    //转换为12-18-2006格式  
	   aDate  =  sDate2.split("-")  
	   oDate2  =  new  Date(aDate[1]  +  '-'  +  aDate[2]  +  '-'  +  aDate[0])  
	   iDays  =  parseInt(Math.abs(oDate1  -  oDate2)  /  1000  /  60  /  60  /24)    //把相差的毫秒数转换为天数  
       return  iDays  
 }  

//基本信息
ApplyBaseInfoForm=Ext.extend(Ext.Panel,{
	layout : "form",
	border : false,
	autoHeight : true,
	labelAlign : 'right',
	projectId : null,
	isAllReadOnly : false,
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
		ApplyBaseInfoForm.superclass.constructor.call(this, {
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
					name : 'bpSpecialInterest.id',
					value:this.projectId
				},{
					columnWidth : .35,
					layout : 'form',
					labelWidth : 85,
					items:[{
						fieldLabel : '申请编号',
						xtype : 'textfield',
						readOnly : true,
						anchor : "88%",
						name : 'bpSpecialInterest.applyNumber'
					}]
				},{
					columnWidth : .35,
					layout : 'form',
					labelWidth : 85,
					items:[{
						fieldLabel : '合同编号',
						xtype : 'textfield',
						readOnly : true,
						anchor : "86%",
						name : 'bpSpecialInterest.contractNumber'
					}]
				},{
					columnWidth : .28,
					layout : 'form',
					labelWidth : 85,
					items:[{
						fieldLabel : '合同金额',
						xtype : 'numberfield',
						readOnly : this.isAllReadOnly,
						allowBlank:false,
						anchor : "100%",
						name : 'bpSpecialInterest.contractMoney'
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
					items:[{
						fieldLabel : '理财产品名称',
						xtype : 'textfield',
						readOnly : true,
						anchor : "88%",
						name : 'bpSpecialInterest.mmplanName'
					}]
				},{
					columnWidth : .35,
					layout : 'form',
					labelWidth : 85,
					items:[{
						fieldLabel : '申请人',
						xtype : 'textfield',
						readOnly : true,
						anchor : "86%",
						name : 'bpSpecialInterest.applyUserName'
					}]
				},{
					columnWidth : .3,
					layout : 'form',
					labelWidth : 85,
					items:[{
						xtype : "combo",
						triggerClass : 'x-form-search-trigger',
						hiddenName : "bpSpecialInterest.applyManagerName",
						editable : false,
						fieldLabel : "申请经理",
						readOnly : this.isAllReadOnly,
						anchor : "94%",
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
								title : "选择申请经理",
								callback : function(uId, uname) {
									obj.setValue(uId);
									obj.setRawValue(uname);
									appuerIdObj.setValue(uId);
								}
							}).show();
						}
					}, {
						xtype : "hidden",
						name:'bpSpecialInterest.applyManagerId'
					}]
				}]
			}]
		})
	}
});


//特殊计息申请单
ApplyTabelForm=Ext.extend(Ext.Panel,{
	layout : "form",
	border : false,
	autoHeight : true,
	labelAlign : 'right',
	projectId : null,
	isAllReadOnly : false,
	plManageMoneyPlan:null,
	applyBaseInfoForm:null,
	apply:null,
	constructor : function(_cfg) {
		if (_cfg == null) {
			_cfg = {};
		}
		if (typeof(_cfg.projectId) != "undefined") {
			this.projectId = _cfg.projectId;
		}
		if (typeof(_cfg.bpSpecialInterestApply) != "undefined") {
			this.apply = _cfg.bpSpecialInterestApply;
		}
		if (typeof(_cfg.isAllReadOnly) != "undefined") {
			this.isAllReadOnly = _cfg.isAllReadOnly;
		}
		if (typeof(_cfg.plManageMoneyPlan) != "undefined") {
			this.plManageMoneyPlan = _cfg.plManageMoneyPlan;
		}
		if (typeof(_cfg.applyBaseInfoForm) != "undefined") {
			this.applyBaseInfoForm = _cfg.applyBaseInfoForm;
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
					name : 'bpSpecialInterestApply.applyId',
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
						value:this.apply?this.apply.standardInterest:'',
						listeners : {
							scope:this,
							/*change:function(v){
								var contractMoney=this.getOriginalContainer().getCmpByName('bpSpecialInterest.contractMoney');
								if(typeof(contractMoney.value)=="undefined"){
									Ext.ux.Toast.msg('操作信息','请先填写合同金额!');
									return;
								}else{
									var applyInterest=this.getOriginalContainer().getCmpByName('bpSpecialInterestApply.applyInterest');
									var yieldBalance=this.getOriginalContainer().getCmpByName('bpSpecialInterestApply.yieldBalance');
									var plan=this.getOriginalContainer().plManageMoneyPlan;
									if(applyInterest.value){
										var value=(applyInterest.value-v.value)*plan.investlimit*30*contractMoney.value/360;
										yieldBalance.setValue(value);
									}
								}
							},*/
							afterrender:function(v){
								var plan=this.getOriginalContainer().plManageMoneyPlan;
								if(v.value){
									return v.value;
								}else{
									v.setValue(plan.yeaRate);
								}
							}
						}
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
//						fieldLabel : '申请返还现金金额',
						fieldLabel : '其他损失',
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

//损失承担方式
LossTabelForm=Ext.extend(Ext.Panel,{
	layout : "form",
	border : false,
	autoHeight : true,
	labelAlign : 'right',
	projectId : null,
	isAllReadOnly : false,
	constructor : function(_cfg) {
		if (_cfg == null) {
			_cfg = {};
		}
		if (typeof(_cfg.isAllReadOnly) != "undefined") {
			this.isAllReadOnly = _cfg.isAllReadOnly;
		}
		if (typeof(_cfg.projectId) != "undefined") {
			this.projectId = _cfg.projectId;
		}
		Ext.applyIf(this, _cfg);
		LossTabelForm.superclass.constructor.call(this, {
			items : [{
				layout : "column",
				border : false,
				scope : this,
				defaults : {
					anchor : '100%',
					columnWidth : 1,
					isFormField : true,
					labelWidth : 100,
					labelAlign:'left'
				},
				items : [{
					xtype : 'hidden',
					name : 'bpSpecialInterestLoss.lossId'
				},{
					xtype : 'hidden',
					name : 'bpSpecialInterestLoss.interestProjectId',
					value:this.projectId
				},{
					columnWidth : .35,
					layout : 'form',
					labelWidth : 85,
					items:[{
						xtype : "combo",
						triggerClass : 'x-form-search-trigger',
						hiddenName : "bpSpecialInterestLoss.obligorOneName",
						editable : false,
						fieldLabel : "承担人1",
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
								title : "选择承担人1",
								callback : function(uId, uname) {
									obj.setValue(uId);
									obj.setRawValue(uname);
									appuerIdObj.setValue(uId);
								}
							}).show();
						}
					}, {
						xtype : "hidden",
						name:'bpSpecialInterestLoss.obligorOneId'
					}]
				},{
					columnWidth : .31,
					layout : 'form',
					labelWidth : 85,
					items:[{
						fieldLabel : '承担金额1',
						xtype : 'numberfield',
						readOnly : this.isAllReadOnly,
						anchor : "100%",
						name : 'bpSpecialInterestLoss.obligorOneCash',
						value:0,
						scope:this,
						listeners:{
							'blur':function(v){
								var totalMoney=this.getOriginalContainer().getCmpByName('bpSpecialInterestApply.lossTotal').getValue();
								var value=v.getValue();
								var temp=totalMoney-value;
								this.getOriginalContainer().getCmpByName('balanceMoney1').setValue(temp.toFixed(2));
							}
						}
					}]
				},{
					columnWidth:.04,
					layout:'form',
					labelWidth :10,
					items:[{
						fieldLabel : "<span style='margin-left:1px'>元</span> ",
						labelSeparator : '',
						anchor : "100%"
					}]
				},{
					columnWidth : .3,
					layout : 'form',
					labelWidth : 85,
					items:[{
						xtype : "numberfield",
						name : "balanceMoney1",
						editable : false,
						fieldLabel : "剩余承担金额1",
						readOnly : true,
						anchor : "92%",
						value:0
					}]
				},{
					columnWidth : .35,
					layout : 'form',
					labelWidth : 85,
					items:[{
						xtype : "combo",
						triggerClass : 'x-form-search-trigger',
						hiddenName : "bpSpecialInterestLoss.obligorTwoName",
						editable : false,
						fieldLabel : "承担人2",
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
								title : "选择承担人2",
								callback : function(uId, uname) {
									obj.setValue(uId);
									obj.setRawValue(uname);
									appuerIdObj.setValue(uId);
								}
							}).show();
						}
					}, {
						xtype : "hidden",
						name:'bpSpecialInterestLoss.obligorTwoId'
					}]
				},{
					columnWidth : .31,
					layout : 'form',
					labelWidth : 85,
					items:[{
						fieldLabel : '承担金额2',
						xtype : 'numberfield',
						readOnly : this.isAllReadOnly,
						anchor : "100%",
						name : 'bpSpecialInterestLoss.obligorTwoCash',
						value:0,
						scope:this,
						listeners:{
							'blur':function(v){
								var totalMoney=this.getOriginalContainer().getCmpByName('balanceMoney1').getValue();
								var value=v.getValue();
								var temp=totalMoney-value;
								this.getOriginalContainer().getCmpByName('balanceMoney2').setValue(temp.toFixed(2));
							}
						}
					}]
				},{
					columnWidth:.04,
					layout:'form',
					labelWidth :10,
					items:[{
						fieldLabel : "<span style='margin-left:1px'>元</span> ",
						labelSeparator : '',
						anchor : "100%"
					}]
				},{
					columnWidth : .3,
					layout : 'form',
					labelWidth : 85,
					items:[{
						xtype : "numberfield",
						name : "balanceMoney2",
						editable : false,
						fieldLabel : "剩余承担金额2",
						readOnly : true,
						anchor : "92%",
						value:0
					}]
				},{
					columnWidth : .35,
					layout : 'form',
					labelWidth : 85,
					items:[{
						xtype : "combo",
						triggerClass : 'x-form-search-trigger',
						hiddenName : "bpSpecialInterestLoss.obligorThreeName",
						editable : false,
						fieldLabel : "承担人3",
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
								title : "选择承担人3",
								callback : function(uId, uname) {
									obj.setValue(uId);
									obj.setRawValue(uname);
									appuerIdObj.setValue(uId);
								}
							}).show();
						}
					}, {
						xtype : "hidden",
						name:'bpSpecialInterestLoss.obligorThreeId'
					}]
				},{
					columnWidth : .31,
					layout : 'form',
					labelWidth : 85,
					items:[{
						fieldLabel : '承担金额3',
						xtype : 'numberfield',
						readOnly : this.isAllReadOnly,
						anchor : "100%",
						name : 'bpSpecialInterestLoss.obligorThreeCash',
						value:0,
						scope:this,
						listeners:{
							'blur':function(v){
								var totalMoney=this.getOriginalContainer().getCmpByName('balanceMoney2').getValue();
								var value=v.getValue();
								var temp=totalMoney-value;
								this.getOriginalContainer().getCmpByName('balanceMoney3').setValue(temp.toFixed(2));
							}
						}
					}]
				},{
					columnWidth:.04,
					layout:'form',
					labelWidth :10,
					items:[{
						fieldLabel : "<span style='margin-left:1px'>元</span> ",
						labelSeparator : '',
						anchor : "100%"
					}]
				},{
					columnWidth : .3,
					layout : 'form',
					labelWidth : 85,
					items:[{
						xtype : "numberfield",
						name : "balanceMoney3",
						editable : false,
						fieldLabel : "剩余承担金额3",
						readOnly : true,
						anchor : "92%",
						value:0
					}]
				},{
					columnWidth : .35,
					layout : 'form',
					labelWidth : 85,
					items:[{
						xtype : "combo",
						triggerClass : 'x-form-search-trigger',
						hiddenName : "bpSpecialInterestLoss.obligorDepName",
						editable : false,
						fieldLabel : "承担部门",
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
								title : "选择承担部门",
								callback : function(uId, uname) {
									obj.setValue(uId);
									obj.setRawValue(uname);
									appuerIdObj.setValue(uId);
								}
							}).show();
						}
					}, {
						xtype : "hidden",
						name:'bpSpecialInterestLoss.obligorDepId'
					}]
				},{
					columnWidth : .31,
					layout : 'form',
					labelWidth : 85,
					items:[{
						fieldLabel : '承担金额',
						xtype : 'numberfield',
						readOnly : this.isAllReadOnly,
						anchor : "100%",
						name : 'bpSpecialInterestLoss.obligorDepCash',
						value:0
					}]
				},{
					columnWidth:.04,
					layout:'form',
					labelWidth :10,
					items:[{
						fieldLabel : "<span style='margin-left:1px'>元</span> ",
						labelSeparator : '',
						anchor : "100%"
					}]
				},{
					columnWidth:1,
					layout:'form',
					labelWidth :85,
					items:[{
						fieldLabel : "申请原因",
						xtype : 'textarea',
						readOnly : this.isAllReadOnly,
						anchor : "98%",
						name : 'bpSpecialInterestLoss.obligorApplyReason'
					}]
				}]
			}]
		})
	}
});

//投资客户信息
CsInvestmentPersonInfo=Ext.extend(Ext.Panel,{
	layout : "form",
	border : false,
	autoHeight : true,
	labelAlign : 'right',
	projectId : null,
	isAllReadOnly : false,
	isEdit:false,
	investId:null,
	constructor : function(_cfg) {
		if (_cfg == null) {
			_cfg = {};
		}
		if (typeof(_cfg.investId) != "undefined") {
			this.investId = _cfg.investId;
		}
		if (typeof(_cfg.isAllReadOnly) != "undefined") {
			this.isAllReadOnly = _cfg.isAllReadOnly;
		}
		if (typeof(_cfg.projectId) != "undefined") {
			this.projectId = _cfg.projectId;
		}
		if (typeof(_cfg.isEdit) != "undefined") {
			this.isEdit = _cfg.isEdit;
		}
		Ext.applyIf(this, _cfg);
		CsInvestmentPersonInfo.superclass.constructor.call(this, {
			items : [{
				layout : "column",
				border : false,
				scope : this,
				defaults : {
					anchor : '100%',
					columnWidth : 1,
					isFormField : true,
					labelWidth : 100,
					labelAlign:'left'
				},
				items : [{
					xtype : 'hidden',
					name : 'csInvestmentperson.investId',
					value:this.investId
				},{
					columnWidth : .35,
					layout : 'form',
					labelWidth : 85,
					items:[{
						xtype : 'textfield',
						fieldLabel : '<font color=red>*</font>姓名',
						allowBlank : false,
						anchor : "88%",
						name : 'csInvestmentperson.investName',
						blankText : '姓名为必填内容',
						readOnly : true
					},{
						xtype : 'textfield',
						fieldLabel : '联系电话2',
						name : 'csInvestmentperson.cellphone2',
						allowBlank : true,
						anchor : "88%",
						readOnly : this.isAllReadOnly,
						regex : /^[1][358][0-9]{9}$/,
						regexText : '联系电话2格式不正确'	
					}]
				},{
					columnWidth : .35,
					layout : 'form',
					labelWidth : 85,
					items:[{
						xtype : "dickeycombo",
						nodeKey : 'sex_key',
						hiddenName : 'csInvestmentperson.sex',
						fieldLabel : "性别",
						anchor : "88%",
						allowBlank : false,
						editable : true,
						blankText : "性别不能为空，请正确填写!",
						readOnly : this.isAllReadOnly,
						listeners : {
							afterrender : function(combox) {
								var st = combox.getStore();
								st.on("load", function() {
									if (combox.getValue() == 0|| combox.getValue() == 1
											|| combox.getValue() == ""|| combox.getValue() == null) {
										combox.setValue("");
									} else {
										combox.setValue(combox.getValue());
									}
									combox.clearInvalid();
								})
							}
						}
					},{
						xtype : 'textfield',
						fieldLabel : '固定电话',
						name : 'csInvestmentperson.fixedTelephone',
						allowBlank : true,
						anchor : "88%",
						readOnly : this.isAllReadOnly
					}]
				},{
					columnWidth : .3,
					layout : 'form',
					labelWidth : 85,
					items:[{
						xtype : 'textfield',
						fieldLabel : '联系电话1',
						name : 'csInvestmentperson.cellphone',
						allowBlank : false,
						anchor : "92%",
						readOnly : this.isAllReadOnly,
						//regex : /^[1][358][0-9]{9}$/,
						//regexText : '联系电话1格式不正确'	
						listeners : {
							'blur':function(v){
							    if(!(v.value && v.value.length>=6)){
							    	Ext.ux.Toast.msg('操作信息', '联系电话至少6位!');
							    	v.setValue();
							    }
							}
						}
					},{
						xtype : 'textfield',
						fieldLabel : '阴历生日',
						name : 'csInvestmentperson.lunarCalendar',
						allowBlank : true,
						anchor : "92%",
						readOnly : this.isAllReadOnly
					}]
				},
					{
					columnWidth : .35,
					layout : 'form',
					labelWidth : 85,
					items:[{
						xtype : "dickeycombo",
						nodeKey : 'card_type_key',
						hiddenName : "csInvestmentperson.cardtype",
						itemName : '证件类型', // xx代表分类名称
						fieldLabel : "证件类型",
						allowBlank : false,
						editable : true,
						anchor : "88%",
						readOnly : this.isAllReadOnly,
						blankText : "证件类型不能为空，请正确填写!",
						listeners : {
							scope : this,
							afterrender : function(combox) {
								var st = combox.getStore();
								st.on("load", function() {
									if(combox.getValue()=='' || combox.getValue()==null){												
										combox.setValue(st.getAt(0).data.itemId);
										combox.clearInvalid();
									}else{
										combox.setValue(combox.getValue());
										combox.clearInvalid();
									}
								})
							}
						}
					},{
										xtype:'combo',
										fieldLabel : '客户来源',
										mode : 'local',
										hiddenName:'csInvestmentperson.customerSource',
										displayField : 'name',
										valueField : 'id',
										anchor : "88%",
										allowBlank:false,
										readOnly : this.isAllReadOnly,
										store : new Ext.data.SimpleStore({
												fields : ["name", "id"],
										data : [["柜台", "0"],
										["渠道", "1"]]
										}),
										triggerAction : "all"
										}
					]
				},{
					columnWidth : .35,
					layout : 'form',
					labelWidth : 85,
					items:[{
						xtype : 'textfield',
						fieldLabel : '证件号码',
						name : 'csInvestmentperson.cardnumber',
						allowBlank : false,
						anchor : "88%",
						blankText : '证件号码为必填内容',
						readOnly : this.isAllReadOnly
					},{
						xtype : 'textfield',
						fieldLabel : '年龄',
						name : 'csInvestmentperson.age',
						allowBlank : true,
						anchor : "88%",
						readOnly : this.isAllReadOnly
					}]
				},{
					columnWidth : .3,
					layout : 'form',
					labelWidth : 85,
					items:[{
						xtype : 'datefield',
						labelSeparator : '',
						format : 'Y-m-d',
						anchor : "92%",
						fieldLabel : '出生日期',
						name : 'csInvestmentperson.birthDay',
						readOnly : this.isAllReadOnly,
						allowBlank : false
					},{
						xtype : 'textfield',
						fieldLabel : '电子邮箱',
						name : 'csInvestmentperson.selfemail',
						readOnly : this.isAllReadOnly,
//						allowBlank : false,
						anchor : "92%",
						regex : /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/,
						regexText : '电子邮箱格式不正确',
						listeners : {
							'afterrender':function(com){
							    com.clearInvalid();
							}
						}	
					}
					
					]
				},{
					columnWidth : .35,
					layout : 'form',
					labelWidth : 85,
					items:[{
						xtype : 'textfield',
						fieldLabel : '邮政编码',
						name : 'csInvestmentperson.postcode',
						readOnly : this.isAllReadOnly,
						allowBlank : true,
						anchor : "88%",
						blankText : '邮政编码为必填内容',
						regex : /^[0-9]{6}$/,
						regexText : '邮政编码格式不正确'
					}]
				},{
					columnWidth : .35,
					layout : 'form',
					labelWidth : 85,
					items:[]
				},{
					columnWidth : .35,
					layout : 'form',
					labelWidth : 85,
					items:[{
						xtype : 'textfield',
						fieldLabel : 'QQ',
						name : 'csInvestmentperson.selfqq',
						allowBlank : true,
						anchor : "88%",
						readOnly : this.isAllReadOnly
					}]
				},
				{
					columnWidth : .3,
					layout : 'form',
					labelWidth : 85,
					items:[{
						hiddenName : 'csInvestmentperson.belongedName',
						xtype : 'trigger',
						fieldLabel : '客户授权人',
						submitValue : true,
						triggerClass : 'x-form-search-trigger',
						editable : false,
						anchor : "92%",
						readOnly : this.isAllReadOnly,
						scope : this
					}]
				}
				,{
					columnWidth : 1,
					layout : 'form',
					labelWidth : 85,
					items:[{
						xtype : "combo",
						triggerClass : 'x-form-search-trigger',
						hiddenName : "csInvestmentperson.shopName",
						editable : false,
						anchor : "97.5%",
						fieldLabel : '登记分公司',
						blankText : "登记分公司不能为空，请正确填写!",
						readOnly : true,
						allowBlank : false
					}]
				},{
					columnWidth : 1,
					layout : 'form',
					labelWidth : 85,
					items:[{
						xtype : 'textfield',
						fieldLabel : '通讯地址',
						allowBlank : false,
						anchor : "97.5%",
						readOnly : this.isAllReadOnly,
						name : 'csInvestmentperson.postaddress'
					}]
				},{
					border : false,
					layout : "form",
					scope : this,
					items : [{
						xtype : 'button',
						text : this.isEdit?'编辑客户资料':'查看客户资料',
						iconCls : 'btn-customer',
						width : 110,
						scope : this,
						handler : function() {
							var investId = this.investId;
							if(this.isEdit){
								Ext.Ajax.request({
									url : __ctxPath+ '/creditFlow/creditAssignment/customer/seePersonCsInvestmentperson.do',
									method : 'POST',
									scope : this,
									success : function(response, request) {
										obj = Ext.util.JSON.decode(response.responseText);
										var personData = obj.data;
										var randomId = rand(100000);
										var id = "update_person" + randomId;
										var url = __ctxPath+ '/creditFlow/creditAssignment/customer/updatePersonCsInvestmentperson.do';
										var window_update = new Ext.Window({
											title : '编辑个人客户详细信息',
											height : 460,
											constrainHeader : true,
											collapsible : true,
											frame : true,
											iconCls : 'btn-edit',
											border : false,
											bodyStyle : 'overflowX:hidden',
											buttonAlign : 'right',
											iconCls : 'newIcon',
											width : (screen.width - 180) * 0.7 + 160,
											resizable : true,
											layout : 'fit',
											autoScroll : false,
											constrain : true,
											closable : true,
											modal : true,
											items : [new investmentObj({
														personData : personData,
														url : url,
														id : id
													})],
											tbar : [new Ext.Button({
												text : '更新',
												tooltip : '更新基本信息',
												iconCls : 'btn-refresh',
												hideMode : 'offsets',
												handler : function() {
													var vDates = "";
													var panel_add = window_update.get(0);
													formSaveinvestPersonObj(panel_add,
															window_update, personStore);
												}
											})],
											listeners : {
												'beforeclose' : function(panel) {
													window_update.destroy();
												}
											}
										});
										window_update.show();
									},
									failure : function(response) {
										Ext.ux.Toast.msg('状态', '操作失败，请重试');
									},
									params : {
										investId : investId
									}
								});
							}else{
								Ext.Ajax.request({
									url : __ctxPath+ '/creditFlow/creditAssignment/customer/seePersonCsInvestmentperson.do',
									method : 'POST',
									scope : this,
									success : function(response, request) {
										var obj = Ext.util.JSON.decode(response.responseText);
										var personData = obj.data;
										var randomId = rand(100000);
										var id = "see_person" + randomId;
										var anchor = '100%';
										var window_see = new Ext.Window({
											title : '查看个人客户详细信息',
											layout : 'fit',
											width : (screen.width - 180) * 0.7+ 160,
											maximizable : true,
											height : 460,
											closable : true,
											modal : true,
											plain : true,
											border : false,
											items : [new investmentObj({
														url : null,
														id : id,
														personData : personData,
														isReadOnly : true
													})],
											listeners : {
												'beforeclose' : function(panel) {
													window_see.destroy();
												}
											}
										});
										window_see.show();
									},
									failure : function(response) {
										Ext.ux.Toast.msg('状态', '操作失败，请重试');
									},
									params : {
										investId : investId
									}
								})
							}
						}
					}]
				}]
			}]
		})
	}
});
