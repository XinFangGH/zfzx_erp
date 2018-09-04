/**
 * 理财产品购买流程启动页面
 * @class PlManageMoneyProducebuyFlow
 * @extends Ext.Window
 */
PlManageMoneyProducebuyFlow = Ext.extend(Ext.Window, {
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		// 必须先初始化组件
		this.initUIComponents();
		PlManageMoneyProducebuyFlow.superclass.constructor.call(this, {
					id : 'PlManageMoneyProducebuyFlow',
					layout : 'fit',
					border : false,
					items : [
					this.formPanel],
					modal : true,
					height : 460,
					width : 1000,
					// maximizable : true,
					title : '购买详细信息',
					buttonAlign : 'center',
					buttons : [{
								text : '启动理财产品购买流程',
								iconCls : 'btn-save',
								scope : this,
								hidden : this.isAllReadOnly,
								disabled : this.isAllReadOnly,
								handler : this.save
							}, {
								text : '取消',
								iconCls : 'btn-cancel',
								scope : this,
								hidden : this.isAllReadOnly,
								disabled : this.isAllReadOnly,
								handler : this.cancel
							}]
				});
	},// end of the constructor
	// 初始化组件
	initUIComponents : function() {

	var leffwidth=80;
    var investlimit=this.investlimit;
    
    var storepayintentPeriod = "[";
		for (var i = 1; i < 31; i++) {
			storepayintentPeriod = storepayintentPeriod + "[" + i + ", " + i
					+ "],";
		}
		storepayintentPeriod = storepayintentPeriod.substring(0,
				storepayintentPeriod.length - 1);
		storepayintentPeriod = storepayintentPeriod + "]";
		var obstorepayintentPeriod = Ext.decode(storepayintentPeriod);
    	this.formPanel1 = new Ext.FormPanel({
			layout : 'column',
			bodyStyle : 'padding:10px',
			autoScroll : true,
			monitorValid : true,
			frame : true,
			plain : true,
			labelAlign : "right",
			// id : 'SlPersonMainForm',
			defaults : {
				anchor : '96%',
				labelWidth : 80,
				columnWidth : 1,
				layout : 'column'
			},
			// defaultType : 'textfield',
			items : [ {
						columnWidth : .33,
						layout : 'form',
						labelWidth : leffwidth,
						items : [{
									fieldLabel : '产品编号',
									name : 'plManageMoneyPlan.mmNumber',
									allowBlank : false,
									xtype : 'textfield',
									anchor : '100%'
								}]
					}]
		});
		this.formPanel = new Ext.FormPanel({
			layout : 'column',
			bodyStyle : 'padding:10px',
			id : "PlManageMoneyProducebuyfomr",
			autoScroll : true,
			monitorValid : true,
			frame : true,
			plain : true,
			labelAlign : "right",
			// id : 'SlPersonMainForm',
			defaults : {
				anchor : '96%',
				labelWidth : 80,
				columnWidth : 1,
				layout : 'column'
			},
			// defaultType : 'textfield',
			items : [{
						xtype : 'hidden',
						id : 'plManageMoneyPlanBuyinfo.id',
						name : 'plManageMoneyPlanBuyinfo.id'
					}, {
						xtype : 'hidden',
						name : 'plManageMoneyPlanBuyinfo.plManageMoneyPlan.mmplanId',
						value : this.mmplanId
					}, {
						xtype : 'fieldset',
						autoHeight : true,
						collapsible : true,
						bodyStyle : 'padding: 5px',
						title : '产品基本信息',
						columnWidth : 1, // 该列在整行中所占的百分比
						layout : "column", // 从上往下的布局

						items : [{
						columnWidth : .33,
						labelWidth : leffwidth,
						layout : 'form',
						items : [{          xtype : 'hidden',
						                    id:'plManageMoneyPlan.mmplanId',
											name : 'plManageMoneyPlan.mmplanId'
											},
										   {					
											fieldLabel : '理财产品类型',
											xtype : 'textfield',
											hiddenName : 'plManageMoneyPlan.manageMoneyTypeId',
											readOnly:true,
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
										
										
									
								},{
								  xtype:"hidden",
								 name : 'plManageMoneyPlan.mmName'
								
								}]
					}, {
						columnWidth : .33,
						layout : 'form',
						labelWidth : leffwidth,
						items : [{
									fieldLabel : '产品编号',
									name : 'plManageMoneyPlan.mmNumber',
									readOnly:true,
									allowBlank : false,
									xtype : 'textfield',
									anchor : '100%'
								}]
					}, {
						columnWidth : .34,
						labelWidth : leffwidth,
						layout : 'form',
						items : [{
									fieldLabel : '保障方式',
									xtype : 'textfield',
									name : 'plManageMoneyPlan.guaranteeWay',
										readOnly:true,
									anchor : '100%'
								}]
					},  {
						columnWidth : .33,
						layout : 'form',
						labelWidth : leffwidth,
						items : [{
									fieldLabel : '收益方式',
									name : 'plManageMoneyPlan.benefitWay',
									readOnly:true,
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
										readOnly:true,
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
										readOnly:true,
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
										readOnly:true,
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
										readOnly:true,
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
										readOnly:true,
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
								readOnly:true,
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
										readOnly:true,
									anchor : '100%'
								}]
					}, {
						columnWidth : .34,
						labelWidth : leffwidth,
						layout : 'form',
						items : [{
									fieldLabel : '投资范围',
									name : 'plManageMoneyPlan.investScope',
									readOnly:true,
									xtype : 'textfield',
									anchor : '100%'
						}
								]
					}, {
						columnWidth : .3,
						labelWidth : leffwidth,
						layout : 'form',
						items : [{
									fieldLabel : '投资期限',
									name : 'plManageMoneyPlan.investlimit',
									fieldClass : 'field-align',
									allowBlank : false,
									xtype : 'numberfield',
									readOnly:true,
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
						labelWidth : leffwidth,
						layout : 'form',
						items : [{
									fieldLabel : '年化收益率',
									xtype : 'numberfield',
									allowBlank : false,
									readOnly:true,
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
					},{
				columnWidth : .34,
				layout : 'form',
				labelWidth : leffwidth,
				items : [{
						  xtype:'combo',
				          mode : 'local',
			              displayField : 'name',
			              valueField : 'id',
			              id :　'accountTypeid',
			              editable : false,
			            readOnly:true,
			                 width : 70,
			                 store : new Ext.data.SimpleStore({
				       		 fields : ["name", "id"],
			           		 data : [["一次性", "1"],["非一次性", "0"],["循环出借", "2"]]
			              	}),
				             triggerAction : "all",
			                hiddenName:"plManageMoneyPlan.isOne",
		                	fieldLabel : '派息类型',	
		                	anchor : "100%",
		                	allowBlank:false,
				          	name : 'plManageMoneyPlan.isOne'
				          	,
				          	listeners : {
									afterrender : function(combox) {
											var st = combox.getStore();
											combox.setValue(combox.getValue());
										}
	
							} 
						 
							
					}]
			},{
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
								anchor : "100%",
								disabled : this.isAllReadOnly,
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
								boxLabel : '按实际投资日对日派息',
								name : 'm1',
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
					
					
					},{
					
					
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
								anchor : "100%",
								disabled : this.isAllReadOnly,
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
								id:"plManageMoneyPlan.payaccrualType"

							}]
						},  {
							columnWidth : 0.5,
							labelWidth : 10,
							layout : 'form',
							items : [{
								xtype : 'radio',
								boxLabel : '月',
								name : 'q1',
								id : "jixizq2",
								inputValue : true,
								checked : true,
								anchor : "100%",
								disabled : this.isAllReadOnly,
								listeners : {
									scope : this,
									check : function(obj, checked) {
										var flag = Ext.getCmp("jixizq2"
											).getValue();
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
										readOnly:true,
									xtype : 'textarea',
									anchor : '100%'
								}]
					}]}, {
						xtype : 'fieldset',
						autoHeight : true,
						collapsible : true,
						bodyStyle : 'padding: 5px',
						title : '购买信息',
						columnWidth : 1, // 该列在整行中所占的百分比
						layout : "column", // 从上往下的布局

						items : [ {
						columnWidth : .33,
						labelWidth : leffwidth,
						layout : 'form',
						items : [{
							fieldLabel : '投资人',
							readOnly : this.isAllreadOnly,
							editable : false,
							allowBlank : false,
							// allowBlank : false,
							xtype : 'trigger',
							triggerClass : 'x-form-search-trigger',
							blankText : "投资人不能为空，请正确填写!",
							name : 'plManageMoneyPlanBuyinfo.investPersonName',
					//		id : 'plManageMoneyPlanBuyinfo.investPersonName',
							maxLength : 15,
							value : this.pername,
							onTriggerClick : function() {
								var obj=this;
								new selectInvestPersonList({
									formPanel : Ext.getCmp("PlManageMoneyProducebuyFlow"),
										backcall:function(investId,investName){
										  obj.ownerCt.ownerCt.getCmpByName("plManageMoneyPlanBuyinfo.investPersonName").setValue(investName);
										  obj.ownerCt.ownerCt.getCmpByName("plManageMoneyPlanBuyinfo.investPersonId").setValue(investId);
										}
									}).show();
							},
							anchor : '100%'
						}, {
							xtype : "hidden",
							name : 'plManageMoneyPlanBuyinfo.investPersonId'
						//	id : 'plManageMoneyPlanBuyinfo.investPersonId'

						}]
					}, {
						columnWidth : .3,
						layout : 'form',
						labelWidth : leffwidth,
						items : [{
									fieldLabel : '购买金额',
									name : 'plManageMoneyPlanBuyinfo.buyMoney',
									allowBlank : false,
									fieldClass : 'field-align',
									xtype : 'moneyfield',
									anchor : '100%',
									listeners : {
										scope : this/*,
										'blur' : function(bm){
											if(Number(this.getCmpByName('buyMoney1').hiddenField.value)<
											        Number(this.getCmpByName('startMoney1').hiddenField.value))
											{
												Ext.Msg.alert("提示信息",'购买金额不能小于投资起点金额');
												bm.setValue();
											}else if(Number(this.getCmpByName('buyMoney1').hiddenField.value)>
											            Number(this.getCmpByName('limitMoney1').hiddenField.value)){
											    Ext.Msg.alert("提示信息",'购买金额不能大于投资上限金额');     
											    bm.setValue();
											}
										}*/
									}
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
							format : 'Y-m-d',
							allowBlank : false,
							anchor : '100%',
							listeners : {
							scope : this,
							'change' : function(nf){
							//	 Ext.getCmp("plManageMoneyPlanBuyinfo.endinInterestTime");
								 
								
								var dtstr = Ext.util.Format.date(nf.getValue(), 'Y-m-d');
								var n=parseInt(investlimit);
								if(Ext.getCmp("jixizq2").getValue()==true){
									
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
									 Ext.getCmp("plManageMoneyPlanBuyinfo.endinInterestTime").setValue(yesterday);
									 Ext.getCmp("plManageMoneyPlanBuyinfo.orderlimit").setValue(30*n)
									
								}else{
								
								     var t=nf.getValue().getTime()+n*1000*60*60*24;
									 var yesterday=new Date(t);
								
									 Ext.getCmp("plManageMoneyPlanBuyinfo.endinInterestTime").setValue(yesterday);
									 Ext.getCmp("plManageMoneyPlanBuyinfo.orderlimit").setValue(n)
									
								}
								
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
							id : 'plManageMoneyPlanBuyinfo.endinInterestTime',
							readOnly:true,
							xtype : 'datefield',
							allowBlank : false,
							format : 'Y-m-d',
							anchor : '100%'
						}]
					}, {
						columnWidth : .3,
						layout : 'form',
						labelWidth : leffwidth,
						items : [{
									fieldLabel : '订单期限',
									name : 'plManageMoneyPlanBuyinfo.orderlimit',
									id : 'plManageMoneyPlanBuyinfo.orderlimit',
									xtype : 'hidden',
									anchor : '100%'
									
								}]
					}]} ]
		});
	// 加载表单对应的数据
		if (this.mmplanId != null && this.mmplanId != 'undefined') {
			var mainObj=this.formPanel;
			this.formPanel.loadData({
				url : __ctxPath
						+ '/creditFlow/financingAgency/getPlManageMoneyPlan.do?mmplanId='
						+ this.mmplanId,
				root : 'data',
				preName : 'plManageMoneyPlanfsdfsd',
				success : function(response, options) {
					var respText = response.responseText;
					var alarm_fields = Ext.util.JSON.decode(respText);
					var isStartDatePay=alarm_fields.data.plManageMoneyPlan.isStartDatePay;
					if (isStartDatePay == "1") {
						Ext.getCmp("meiqihkrq1").setValue(true);
						Ext.getCmp("meiqihkrq2").setValue(false);
						mainObj.getCmpByName('plManageMoneyPlan.isStartDatePay').setValue("1");
					} else {
						Ext.getCmp("meiqihkrq2").setValue(true);
						Ext.getCmp("meiqihkrq1").setValue(false);
						mainObj.getCmpByName('plManageMoneyPlan.payintentPerioDate').setValue(null);
						mainObj.getCmpByName('plManageMoneyPlan.payintentPerioDate').setDisabled(true);
						mainObj.getCmpByName('plManageMoneyPlan.isStartDatePay').setValue("2");
					}
					
					mainObj.getCmpByName('plManageMoneyPlan.payintentPerioDate').setDisabled(true);
					Ext.getCmp("meiqihkrq2").setDisabled(true);
					Ext.getCmp("meiqihkrq1").setDisabled(true);
					
					
					var payaccrualType=alarm_fields.data.plManageMoneyPlan.payaccrualType;
					if (payaccrualType == "dayPay") {
						Ext.getCmp("jixizq1").setValue(true);
						Ext.getCmp("jixizq2").setValue(false);
					} else if (payaccrualType == "monthPay") {
						Ext.getCmp("jixizq2").setValue(true);
						Ext.getCmp("jixizq1").setValue(false);
					}else{
					    Ext.getCmp("jixizq2").setValue(true);
						Ext.getCmp("jixizq1").setValue(false);
					}
					 
					Ext.getCmp("jixizq1").setDisabled(true);
					Ext.getCmp("jixizq2").setDisabled(true);
					
				}
			});
		}
		/*
		 * // 加载表单对应的数据 if (this.mmplanId != null && this.mmplanId !=
		 * 'undefined') { this.formPanel.loadData({ url : __ctxPath +
		 * '/creditFlow/financingAgency/getPlManageMoneyPlan.do?mmplanId=' +
		 * this.mmplanId, root : 'data', preName : 'plManageMoneyPlanfsdfsd' }); }
		 */

	},// end of the initcomponents
	/**
	 * 取消
	 * 
	 * @param {}
	 *            window
	 */
	cancel : function() {
		this.close();
	},
	/**
	 * 保存记录
	 */
	save : function() {
		Ext.MessageBox.wait('正在提交数据, 请稍侯 ...', '提示');
		this.formPanel.getForm().submit({
			method : 'POST',
			url : __ctxPath + "/flow/startTurnoverCustomerFlowProcessActivity.do",
			success : function(fp, action) {
				Ext.MessageBox.hide();
				var data = action.result.data;
				var tabs = Ext.getCmp('centerTabPanel');
				var contentPanel = App.getContentPanel();
				var formView = contentPanel.getItem('ProcessNextForm' + data.taskId);
				if (formView == null) {
					formView = new ProcessNextForm({
						taskId : data.taskId,
						activityName : data.activityName,
						projectName : data.projectName,
						agentTask : true
					});
					contentPanel.add(formView);
				}
				contentPanel.activate(formView);
				ZW.refreshTaskPanelView();
				var obj=document.getElementById("taskCount");//手动刷新右上角待办任务数目
				ZW.refreshTaskCount(obj);
				Ext.getCmp('PlManageMoneyProducebuyFlow').close();
			},
			failure : function(fp, action) {
				Ext.MessageBox.hide();
				var respText = action.response.responseText;
				var alarm_fields = Ext.util.JSON.decode(respText);
				if(alarm_fields.msg){
					Ext.Msg.alert('提示信息',alarm_fields.msg)
				}else{
					alert('启动项目失败,请联系管理员!');
				}
			}
		});
	}// end of s

});