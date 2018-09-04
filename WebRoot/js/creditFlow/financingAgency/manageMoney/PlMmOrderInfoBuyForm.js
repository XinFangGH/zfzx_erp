/**
 * @author
 * @createtime
 * @class PlCarInfoForm
 * @extends Ext.Window
 * @description PlCarInfo表单
 * @company 智维软件
 */
PlMmOrderInfoBuyForm = Ext.extend(Ext.Window, {
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		// 必须先初始化组件
		this.initUIComponents();
		PlMmOrderInfoBuyForm.superclass.constructor.call(this, {
					id : 'PlMmOrderInfoBuyForm',
					layout : 'fit',
					items : this.formPanel,
					modal : true,
					height : 500,
					width : 840,
					resizable:false,
					maximizable : true,
					title : '启动投资流程',
					buttonAlign : 'center',
					buttons : [{
								text : '启动流程',
								iconCls : 'btn-process',
								scope : this,
								handler : this.save
							}, {
								text : '关闭',
								iconCls : 'close',
								scope : this,
								handler : this.cancel
							}]
				});
	},// end of the constructor
	// 初始化组件
	initUIComponents : function() {
		this.formPanel = new Ext.FormPanel({
			layout : 'column',
			bodyStyle : 'padding:10px',
			border : false,
			frame : true,
			autoScroll : true,
			defaults : {
				anchor : '100%'
			},
			items : [{
						columnWidth : 1, // 该列在整行中所占的百分比
						layout : "form", // 从上往下的布局
						labelWidth : 80,
						items : [{
									xtype : 'fieldset',
									title : '理财产品信息',
									bodyStyle : 'padding-left:0px',
									collapsible : true,
									labelAlign : 'right',
									autoHeight : true,
									items : [/*this.plMmPlanPanel*/new PlMmPlanPanel({
			mmplanId : this.mmplanId
			})]
								}]
					}, {
						xtype : 'hidden',
						name : 'mmplanId',
						value : this.mmplanId
					}, {
						xtype : 'hidden',
						name  : 'mmplanName',
						value : this.mmplanName
					},{
						columnWidth : .34, // 该列在整行中所占的百分比
						layout : "form", // 从上往下的布局
						labelWidth : 80,
						items : [{
							fieldLabel : '投资人',
							readOnly : this.isAllreadOnly,
							editable : false,
							allowBlank : false,
							xtype : 'trigger',
							triggerClass : 'x-form-search-trigger',
							blankText : "投资人不能为空，请正确填写!",
							name : 'plManageMoneyPlanBuyinfo.investPersonName',
							maxLength : 15,
							scope:this,
							// value : this.pername,
							onTriggerClick : function() {
								new selectInvestPersonList({
									formPanel : Ext.getCmp("PlMmOrderInfoBuyForm"),
									type:this.getOriginalContainer().type,
									backcall : function(investId, investName) {
										 
										obj.ownerCt.ownerCt
												.getCmpByName("plManageMoneyPlanBuyinfo.investPersonName")
												.setValue(investName);
										obj.ownerCt.ownerCt
												.getCmpByName("plManageMoneyPlanBuyinfo.investPersonId")
												.setValue(investId);
									}
								}).show();
							},
							anchor : '100%'
						}, {
							xtype : "hidden",
							name : 'plManageMoneyPlanBuyinfo.investPersonId'
						}]
					}, {
						columnWidth : .3,
						layout : 'form',
						labelWidth : 80,
						items : [{
									xtype : "hidden",
									name : 'plMmOrderInfo.investDue'
								}, {
									fieldLabel : '购买金额',
									readOnly : this.isReadOnly,
									name : 'plMmOrderInfo.buyMoney',
									allowBlank : false,
									fieldClass : 'field-align',
									xtype : 'numberfield',
									anchor : '100%',
									listeners:{
										scope : this,
										'blur':function(v){
											var startMoney=parseInt(this.getCmpByName('startMoney1').hiddenField.value);//投资起点金额
											var limitMoney=parseInt(this.getCmpByName('limitMoney1').hiddenField.value);//投资上限
											var riseMoney=parseInt(this.getCmpByName('riseMoney1').hiddenField.value);//递增金额
											if(v.getValue()<startMoney){
												Ext.ux.Toast.msg('操作信息', '购买金额必须大于起点金额!');
												v.setValue();
											}
											if(v.getValue()>limitMoney){
												Ext.ux.Toast.msg('操作信息', '购买金额必须小于单笔投资上限金额!');
												v.setValue();
											}
											if(((v.getValue()-startMoney)/riseMoney+'').indexOf('.')==1){
												Ext.ux.Toast.msg('操作信息', '购买金额必须是起点金额加整数倍的递增金额!');
												v.setValue();
											}
										}
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
						columnWidth : .3,
						layout : 'form',
						labelWidth : 80,
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
						labelWidth : 80,
						hidden : this.hiddenInterest,
						items : [{
							fieldLabel : '计息起日',
							name : 'plMmOrderInfo.startinInterestTime',
							xtype : 'datefield',
							format : 'Y-m-d',
							readOnly : this.isReadOnly,
							allowBlank : this.hiddenInterest ? true : false,
							anchor : '100%',
							listeners : {
								scope : this,
								'change' : function(nf) {
									var dtstr = Ext.util.Format.date(nf
													.getValue(), 'Y-m-d');
									var payType=this.getCmpByName('plManageMoneyPlan.payaccrualType').getValue();
									var investlimit = this
											.getCmpByName('plManageMoneyPlan.investlimit')
											.getValue()

									var n = parseInt(investlimit);
						/*			var s = dtstr.split("-");
									var yy = parseInt(s[0]);
									var mm = parseInt(s[1]) - 1;
									var dd = parseInt(s[2]);
									var dt = new Date(yy, mm, dd);
									dt.setMonth(dt.getMonth() + n);
									if ((dt.getYear() * 12 + dt.getMonth()) > (yy
											* 12 + mm + n)) {
										dt = new Date(dt.getYear(), dt
														.getMonth(), 0);
									}
									Ext
											.getCmp("plMmOrderInfo.endinInterestTime")
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
					}, {
						columnWidth : .3,
						layout : 'form',
						labelWidth : 80,
						hidden : this.hiddenInterest,
						items : [{
									fieldLabel : '到期日',
									name : 'plMmOrderInfo.endinInterestTime',
									id : 'plMmOrderInfo.endinInterestTime',
									readOnly : true,
									xtype : 'datefield',
									allowBlank : this.hiddenInterest
											? true
											: false,
									format : 'Y-m-d',
									anchor : '100%'
								}]
					}, {
						columnWidth : .3,
						layout : 'form',
						labelWidth : 80,
						items : [{
									fieldLabel : '订单期限',
									name : 'plMmOrderInfo.orderlimit',
									
									id : 'plMmOrderInfo.orderlimit',
									xtype : 'hidden',
									anchor : '100%'

								}]
					}]
		});

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
		$postForm({
			formPanel : this.formPanel,
			scope : this,
			url : __ctxPath
					+ '/creditFlow/financingAgency/startInvestFlowPlMmOrderInfo.do',
			callback : function(fp, action) {
				// var gridPanel = Ext.getCmp('PlManageMoneyProduceGrid');
				// if (gridPanel != null) {
				// gridPanel.getStore().reload();
				// }
				this.close();
				var data = action.result
				Ext.Ajax.request({
					url : __ctxPath
							+ '/creditFlow/financingAgency/findRunInfoPlMmOrderInfo.do?runId='
							+ data.runId,
					method : 'POST',
					success : function(response) {
						var data = Ext.util.JSON.decode(response.responseText);
						var tabs = Ext.getCmp('centerTabPanel');
						var contentPanel = App.getContentPanel();
						var formView = contentPanel.getItem('ProcessNextForm'
								+ data.taskId);
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
					}
				});

				// var data = action.result
				// var tabs = Ext.getCmp('centerTabPanel');
				// var contentPanel = App.getContentPanel();
				// var formView = contentPanel.getItem('ProcessNextForm' +
				// data.taskId);
				// if (formView == null) {
				// formView = new ProcessNextForm({
				// taskId : data.taskId,
				// activityName : data.activityName,
				// projectName : data.projectName,
				// agentTask : true
				// });
				// contentPanel.add(formView);
				// }
				// contentPanel.activate(formView);
				// ZW.refreshTaskPanelView();

			}
		});
	}// end of save

});