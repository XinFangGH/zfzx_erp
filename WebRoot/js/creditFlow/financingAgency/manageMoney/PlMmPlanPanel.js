/**
 * 投资流程信息
 * 
 * @class plMmOrderInfoPanel
 * @extends Ext.Panel
 */
PlMmPlanPanel = Ext.extend(Ext.Panel, {
	layout : 'anchor',
	anchor : '100%',
	companyHidden : false,
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		var leffwidth = 80;
		var storepayintentPeriod = "[";
		for (var i = 1; i < 31; i++) {
			storepayintentPeriod = storepayintentPeriod + "[" + i + ", " + i+ "],";
		}
		storepayintentPeriod = storepayintentPeriod.substring(0,storepayintentPeriod.length - 1);
		storepayintentPeriod = storepayintentPeriod + "]";
		var obstorepayintentPeriod = Ext.decode(storepayintentPeriod);

		PlMmPlanPanel.superclass.constructor.call(this, {
			layout : 'column',
			defaults : {
				anchor : '96%',
				labelWidth : 80,
				columnWidth : 1,
				layout : 'column'
			},
			items : [{
					xtype : 'hidden',
					id : 'plManageMoneyPlanBuyinfo.id',
					name : 'plManageMoneyPlanBuyinfo.id'
				}, {
					xtype : 'hidden',
					name : 'plManageMoneyPlanBuyinfo.plManageMoneyPlan.mmplanId',
					value : this.mmplanId
				}, {
					autoHeight : true,
					bodyStyle : 'padding: 5px',
					columnWidth : 1, // 该列在整行中所占的百分比
					layout : "column", // 从上往下的布局
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
							xtype : "combo",
							displayField : 'itemName',
							valueField : 'itemId',
							triggerAction : 'all',
							store : new Ext.data.ArrayStore({
								url : __ctxPath+ '/creditFlow/financingAgency/getListbykeystrPlManageMoneyType.do?keystr=mmproduce',
								fields : ['itemId', 'itemName'],
								autoLoad : true
							}),
							mode : 'remote',
							editable : false,
							blankText : "理财计划类型不能为空，请正确填写!",
							anchor : "95%",
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
								anchor : '95%'
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
								anchor : '95%'
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
								anchor : '95%'
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
								value : new Date(),
								anchor : '114%'
							}]
						}, {
							columnWidth : .315,
							layout : 'form',
							labelWidth : leffwidth,
							items : [{
								fieldLabel : '购买结束时间',
								name : 'plManageMoneyPlan.buyEndTime',
								xtype : 'datetimefield',
								format : 'Y-m-d H:i:s',
								value : new Date(),
								readOnly : true,
								anchor : '119%'
							}]
						}, {
							columnWidth : .314,
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
							columnWidth : .02, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : 15,
							items : [{
								fieldLabel : "<span style='margin-left:1px'>元</span> ",
								labelSeparator : '',
								anchor : "100%"
							}]
						}, {
							columnWidth : .31,
							labelWidth : 77,
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
							columnWidth : .02, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : 15,
							items : [{
								fieldLabel : "<span style='margin-left:1px'>元</span> ",
								labelSeparator : '',
								anchor : "100%"
							}]
						}, {
							columnWidth : .31,
							labelWidth : 78,
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
							columnWidth : .025, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : 18,
							items : [{
								fieldLabel : "<span style='margin-left:1px'>元</span> ",
								labelSeparator : '',
								anchor : "100%"
							}]
						}, {
							columnWidth : .315,
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
							labelWidth : 100,
							layout : 'form',
							items : [{
								fieldLabel : '到期赎回方式',
								xtype : 'textfield',
								name : 'plManageMoneyPlan.expireRedemptionWay',
								readOnly : true,
								anchor : '100%'
							}]
						}, {
							columnWidth : .33,
							labelWidth : 100,
							layout : 'form',
							items : [{
								fieldLabel : '投资范围',
								name : 'plManageMoneyPlan.investScope',
								readOnly : true,
								xtype : 'textfield',
								anchor : '100%'
							}]
						}, {
							columnWidth : .315,
							labelWidth : leffwidth,
							layout : 'form',
							items : [{
								fieldLabel : '投资期限',
								name : 'plManageMoneyPlan.investlimit',
								fieldClass : 'field-align',
								allowBlank : false,
								xtype : 'numberfield',
								readOnly : true,
								anchor : '100%'
							}]
						}, {
							columnWidth : .03, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : 18,
							items : [{
								fieldLabel : "<span style='margin-left:1px'>期</span> ",
								labelSeparator : '',
								anchor : "100%"
							}]
						}, {
							columnWidth : .3,
							labelWidth : 69,
							layout : 'form',
							items : [{
								fieldLabel : '年化收益率',
								xtype : 'numberfield',
								allowBlank : false,
								readOnly : true,
								name : 'plManageMoneyPlan.yeaRate',
								fieldClass : 'field-align',
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
							columnWidth : .32,
							layout : 'form',
							labelWidth : 69,
							items : [{
								xtype : 'combo',
								mode : 'local',
								displayField : 'name',
								valueField : 'id',
								id : 'accountTypeid',
								editable : false,
								readOnly : true,
								store : new Ext.data.SimpleStore({
									fields : ["name", "id"],
									data : [["一次性", "1"],
											["非一次性", "0"],
											["循环出借", "2"]]
								}),
								triggerAction : "all",
								hiddenName : "plManageMoneyPlan.isOne",
								fieldLabel : '派息类型',
								anchor : "94%",
								allowBlank : false,
								name : 'plManageMoneyPlan.isOne',
								listeners : {
									afterrender : function(combox) {
										var st = combox.getStore();
										combox.setValue(combox.getValue());
									}
								}
							}]
						}, {
							columnWidth : .66,
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
									name : 'm1',
									id : "meiqihkrq1",
									inputValue : true,
									readOnly : true,
									anchor : "100%",
									disabled : true,
									listeners : {
										scope : this,
										check : function(obj, checked) {
											var flag = Ext.getCmp("meiqihkrq1").getValue();
											if (flag == true && !this.isBuyApply) {
												this.getCmpByName('plManageMoneyPlan.isStartDatePay').setValue("1");
												this.getCmpByName('plManageMoneyPlan.payintentPerioDate').setDisabled(false);
											}
										}
									}

								}, {
									xtype : "hidden",
									name : "plManageMoneyPlan.isStartDatePay",
									id : "plManageMoneyPlan.isStartDatePay"

								}]
							}, {
								columnWidth : 0.132,
								labelWidth : 1,
								layout : 'form',
								items : [{
									xtype : 'textfield',
									readOnly : true,
									name : "plManageMoneyPlan.payintentPerioDate",
									id : "plManageMoneyPlan.payintentPerioDate",
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
									boxLabel : '按实际投资日对日派息',
									name : 'm1',
									id : "meiqihkrq2",
									readOnly : true,
									inputValue : true,
									checked : true,
									anchor : "100%",
									disabled : true,
									listeners : {
										scope : this,
										check : function(obj, checked) {
											var flag = Ext.getCmp("meiqihkrq2")
													.getValue();
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

							columnWidth : .34,
							name : "mqhkri",
							layout : "column",
							items : [{
								columnWidth : 0.5,
								labelWidth : leffwidth,
								layout : 'form',
								items : [{
									xtype : 'radio',
									fieldLabel : '付息周期',
									boxLabel : '日',
									name : 'q1',
									id : "jixizq1",
									inputValue : true,
									readOnly : true,
									anchor : "100%",
									disabled : true,
									listeners : {
										scope : this,
										check : function(obj, checked) {
											var flag = Ext.getCmp("jixizq1").getValue();
											if (flag == true) {
												this.getCmpByName('plManageMoneyPlan.payaccrualType').setValue("dayPay");
											}
										}
									}

								}, {
									xtype : "hidden",
									name : "plManageMoneyPlan.payaccrualType",
									id : "plManageMoneyPlan.payaccrualType"

								}]
							}, {
								columnWidth : 0.5,
								labelWidth : 10,
								layout : 'form',
								items : [{
									xtype : 'radio',
									boxLabel : '月',
									name : 'q1',
									id : "jixizq2",
									inputValue : true,
									readOnly : true,
									checked : true,
									anchor : "100%",
									disabled : true,
									listeners : {
										scope : this,
										check : function(obj, checked) {
											var flag = Ext.getCmp("jixizq2").getValue();
											if (flag == true) {
												this.getCmpByName('plManageMoneyPlan.payaccrualType').setValue("monthPay");
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
		if (this.mmplanId != null && this.mmplanId != 'undefined') {
			var panel = this;
			this.loadData({
				url : __ctxPath
						+ '/creditFlow/financingAgency/getPlManageMoneyPlan.do?mmplanId='
						+ this.mmplanId,
				root : 'data',
				preName : 'plManageMoneyPlanfsdfsd',
				success : function(response, obj) {
					var result = Ext.util.JSON.decode(response.responseText);
					if(result.data.plManageMoneyPlan.isStartDatePay == 1){
						Ext.getCmp("meiqihkrq1").setValue(true);
					}
					if(result.data.plManageMoneyPlan.payaccrualType == 'dayPay'){
						Ext.getCmp("jixizq1").setValue(true);
					}
				}
			});
		}

	}

});

/**
 * 投资购买信息
 */
PlMmPlanBuyPanel = Ext.extend(Ext.Panel, {
	layout : 'anchor',
	anchor : '100%',
	companyHidden : false,
	constructor : function(_cfg) {
		if (typeof(_cfg.spouseHidden) != "undefined") {
		}
		Ext.applyIf(this, _cfg);
		var leffwidth = 80;
		PlMmPlanBuyPanel.superclass.constructor.call(this, {
			layout : 'column',
			/*
			 * bodyStyle : 'padding:10px', id : "PlMmPlanBuyPanelId", autoScroll :
			 * true, monitorValid : true, frame : true, plain : true, labelAlign :
			 * "right",
			 */
			defaults : {
				anchor : '96%',
				labelWidth : 100,
				columnWidth : 1,
				layout : 'column'
			},
			// defaultType : 'textfield',
			items : [{

				columnWidth : 1, // 该列在整行中所占的百分比
				layout : "column", // 从上往下的布局

				items : [/*
							 * { columnWidth : .33, labelWidth : leffwidth,
							 * layout : 'form', items : [{ fieldLabel : '投资人',
							 * name : 'plMmOrderInfo.investPersonName',
							 * allowBlank : false, readOnly : true, fieldClass :
							 * 'field-align', xtype : 'textfield', anchor :
							 * '100%' }, { xtype : "hidden", name :
							 * 'plMmOrderInfo.investPersonId' }, { xtype :
							 * "hidden", name : 'plMmOrderInfo.investDue' }] },
							 */{
					columnWidth : .3,
					layout : 'form',
					labelWidth : leffwidth,
					items : [{
								xtype : "hidden",
								name : 'plMmOrderInfo.investDue'
							},{
								xtype : "hidden",
								name : 'plMmOrderInfo.id'
							},/* {
								fieldLabel : '购买金额',
								readOnly : this.isReadOnly,
								name : 'plMmOrderInfo.buyMoney',
								allowBlank : false,
								fieldClass : 'field-align',
								xtype : 'numberfield',
								anchor : '100%'
							}, */{
								xtype : "textfield",
								fieldLabel : "购买金额",
								fieldClass : 'field-align',
								name : "plMmOrderInfo.buyMoney1",
								readOnly : this.isReadOnly,
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
									change : function(nf, nv, ov) {
										
										var value = nf.getValue();
										var index = value.indexOf(".");
										if (index <= 0) { // 如果第一次输入
											// 没有进行格式化
											nf.setValue(Ext.util.Format.number(value, '0,000.00'))
											this.getCmpByName("plMmOrderInfo.buyMoney").setValue(value);
										} else {
											if (value.indexOf(",") <= 0) {
												var ke = Ext.util.Format.number(value,'0,000.00')
												nf.setValue(ke);
												this.getCmpByName("plMmOrderInfo.buyMoney").setValue(value);
											} else {
												var last = value.substring(index + 1,value.length);
												if (last == 0) {
													var temp = value.substring(0, index);
													temp = temp.replace(/,/g,"");
													this.getCmpByName("plMmOrderInfo.buyMoney").setValue(temp);
													nf.setValue(Ext.util.Format.number(temp,'0,000.00'))
												} else {
													var temp = value.replace(/,/g, "");
													this.getCmpByName("plMmOrderInfo.buyMoney").setValue(temp);
													nf.setValue(Ext.util.Format.number(temp,'0,000.00'))
												}
											}

										}
									}
								}
							}, {
								xtype : "hidden",
								name : "plMmOrderInfo.buyMoney"
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
					layout : 'form',
					labelWidth : leffwidth,
					items : [{
								fieldLabel : '购买时间',
								readOnly : this.isReadOnly,
								name : 'plMmOrderInfo.buyDate',
								xtype : 'datefield',
								format : "Y-m-d",
								allowBlank : false,
								anchor : '100%'
							}]
				}, {
					columnWidth : .34,
					layout : 'form',
					labelWidth : leffwidth,
					items : [{
								fieldLabel : '合同编号',
								readOnly : this.isReadOnly,
								name : 'plMmOrderInfo.orderNumber',
								xtype : 'textfield',
								anchor : '100%'
							}]
				}, {
					columnWidth : .3,
					layout : 'form',
					labelWidth : leffwidth,
					hidden : this.hiddenInterest,
					items : [{
						fieldLabel : '计息起日',
						name : 'plMmOrderInfo.startinInterestTime',
						xtype : 'datefield',
						format : 'Y-m-d',
						readOnly : this.readStartinInterestTime?false:this.isReadOnly,
						allowBlank : this.hiddenInterest ? true : false,
						anchor : '100%',
						listeners : {
							scope : this,
							'change' : function(nf) {
								var dtstr = Ext.util.Format.date(nf.getValue(),
										'Y-m-d');
								var investlimit = this.ownerCt.ownerCt
										.getCmpByName('plMmOrderInfo.investDue')
										.getValue()
                                var payType=this.ownerCt.ownerCt.getCmpByName('plManageMoneyPlan.payaccrualType').getValue();
								var n = parseInt(investlimit);
						/*		var s = dtstr.split("-");
								var yy = parseInt(s[0]);
								var mm = parseInt(s[1]) - 1;
								var dd = parseInt(s[2]);
								var dt = new Date(yy, mm, dd);
								dt.setMonth(dt.getMonth() + n);
								if ((dt.getYear() * 12 + dt.getMonth()) > (yy
										* 12 + mm + n)) {
									dt = new Date(dt.getYear(), dt.getMonth(),
											0);
								}
								Ext.getCmp("plMmOrderInfo.endinInterestTime")
										.setValue(dt);
								Ext.getCmp("plMmOrderInfo.orderlimit")
										.setValue(30 * n)*/
							   if(payType=='monthPay'){
									var s=dtstr.split("-");
								    var yy=parseInt(s[0]);
								    var mm=parseInt(s[1])-1;
								    var dd=parseInt(s[2]);
								    var dt=new Date(yy,mm,dd);
								    dt.setMonth(dt.getMonth()+n);
								    if( (dt.getYear()*12+dt.getMonth()) > (yy*12+mm + n) )
								   	{dt=new Date(dt.getYear(),dt.getMonth(),0);}
								   	
								     if(parseInt(dt.getDate()) !=parseInt(dd)){
								   		dt.setDate(0);
								    	}
								    	
									 var t=dt.getTime()-1000*60*60*24;
									 var yesterday=new Date(t);
									 Ext.getCmp("plMmOrderInfo.endinInterestTime").setValue(yesterday);
									 Ext.getCmp("plMmOrderInfo.orderlimit").setValue(30*n)
									
								} else if(payType=='dayPay'){
								
								     var t=nf.getValue().getTime()+n*1000*60*60*24;
									 var yesterday=new Date(t);
								
									 Ext.getCmp("plMmOrderInfo.endinInterestTime").setValue(yesterday);
									 Ext.getCmp("plMmOrderInfo.orderlimit").setValue(n)
									
								}
							}
						}
					}]
				},{
					columnWidth : .03, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : 18,
					items : [{
								fieldLabel : " ",
								labelSeparator : '',
								anchor : "100%"

							}]
				}, {
					columnWidth : .3,
					layout : 'form',
					labelWidth : leffwidth,
					hidden : this.hiddenInterest,
					items : [{
								fieldLabel : '到期日',
								name : 'plMmOrderInfo.endinInterestTime',
								id : 'plMmOrderInfo.endinInterestTime',
								readOnly : true,
								xtype : 'datefield',
								allowBlank : this.hiddenInterest ? true : false,
								format : 'Y-m-d',
								anchor : '100%'
							}]
				}, {
					columnWidth : .3,
					layout : 'form',
					labelWidth : leffwidth,
					items : [{
								fieldLabel : '订单期限',
								name : 'plMmOrderInfo.orderlimit',
								id : 'plMmOrderInfo.orderlimit',
								xtype : 'hidden',
								anchor : '100%'

							}]
				}]
			}]
		})
		if (this.projectId != null && this.projectId != 'undefined') {
			var panel = this;
			this.loadData({
						url : __ctxPath
								+ '/creditFlow/financingAgency/getPlMmOrderInfo.do?id='
								+ this.projectId,
						root : 'data',
						preName : 'plMmOrderInfo',
						success : function(response, obj) {
							var result = Ext.util.JSON.decode(response.responseText);
							 
							this.getCmpByName('plMmOrderInfo.buyMoney1').setValue(Ext.util.Format.number(result.data.buyMoney, '0,000.00'))
						}
					});
		}

	}

});
