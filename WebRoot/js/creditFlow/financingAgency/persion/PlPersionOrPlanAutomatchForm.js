/**
 * @author
 * @createtime
 * @class PlOrReleaseForm
 * @extends Ext.Window
 * @description PlOrRelease表单
 * @company 智维软件
 */
PlPersionOrPlanAutomatchForm = Ext.extend(Ext.Window, {
	initMoney:0,
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		// 必须先初始化组件
		this.initUIComponents();
		PlPersionOrPlanAutomatchForm.superclass.constructor.call(this, {
					id : 'PlPersionOrPlanAutomatchFormWin',
					layout : 'fit',
					items : this.formPanel,
					modal : true,
					width : 1000,
					maximizable : true,
					title : '详细信息',
					buttonAlign : 'center',
					buttons : [{
								text : '保存',
								iconCls : 'btn-save',
								scope : this,
								disabled:true,
								handler : this.save
							}/*, {
								text : '重置',
								iconCls : 'btn-reset',
								scope : this,
								handler : this.reset
							}*/, {
								text : '取消',
								iconCls : 'btn-cancel',
								scope : this,
								handler : this.cancel
							}]
				});
	},// end of the constructor
	// 初始化组件
	initUIComponents : function() {

		// 按 日、月、季、年付息到期还本
		var paymentPeriodComboBox = new Ext.form.ComboBox({
					id : 'paymentPeriodComboBox',
					disabled : true,
					store : new Ext.data.JsonStore({
								fields : ['value', 'name'],
								data : [{
											name : '日',
											value : 1
										}, {
											name : '月',
											value : 2
										}, {
											name : '季',
											value : 3
										}, {
											name : '年',
											value : 4
										}]
							}),
					valueField : 'value',
					displayField : 'name',
					typeAhead : true,
					mode : 'local',
					triggerAction : 'all',
					selectOnFocus : true,
					width : 60
				});

		// 计息周期
		var interestPeriodGroup = new Ext.form.RadioGroup({
					id : 'interestPeriodGroup',
					fieldLabel : "计息周期",
					items : [{
								boxLabel : '按日计息',
								inputValue : 1,
								name : "rg",
								id : "rg",
								checked : true
							}]
				});
				   var leftWidth=110;
		this.formPanel = new Ext.form.FormPanel({
			id : 'plBidPlanFormPanel',
			autoHeight : true,
			autoWidth : true,
			layout : 'column',
			bodyStyle : 'padding:10px',
			border : false,
			autoScroll : true,
			frame : true,
			labelAlign : 'right',
			defaults : {
				anchor : '96%',
				labelWidth : 60
			},

			items : [{
						name : 'plBidPlan.bidId',
						xtype : 'hidden',
						value : this.bidId == null ? '' : this.bidId
					}, {
						xtype : 'hidden',
						name : 'plBidPlan.biddingTypeId',
						value:3 //3 为自动匹配标 的类型id 如果 修改 请修改该值
					}, {
						name : 'plBidPlan.pOrProId',
						id : 'porProId',
						xtype : 'hidden'
					}, {
						name : 'plBidPlan.proType',
						id : 'proType',
						xtype : 'hidden'
					}, {
						name : 'plBidPlan.state',
						xtype : 'hidden',
						value:5
					}, {
						xtype : 'hidden',
						id : 'loanProMoney'
					}, {
						columnWidth : .7, // 该列在整行中所占的百分比
						layout : "form", // 从上往下的布局
						labelWidth : leftWidth,
						labelAlign : 'right',
						items : [{
									xtype : "textfield",
									fieldLabel : '贷款项目名称',
									id : 'loanProName',
									allowBlank : false,
									readOnly : true,
									anchor : '100%'

								}]
					}, {
						columnWidth : .3, // 该列在整行中所占的百分比
						layout : "form", // 从上往下的布局
						labelWidth : 90,
						labelAlign : 'right',
						items : [{
									xtype : "textfield",
									allowBlank : false,
									fieldLabel : '贷款项目编号',
									id : 'loanProNubmer',
									readOnly : true,
									anchor : '100%'

								}]
					}, {
						columnWidth : .7, // 该列在整行中所占的百分比
						layout : "form", // 从上往下的布局
						labelWidth : leftWidth,
						labelAlign : 'right',
						items : [{
									xtype : "textfield",
									fieldLabel : '债权名称',
									name : 'plBidPlan.bidProName',
									allowBlank : false,
									anchor : '100%'

								}]
					}, {
						columnWidth : .3, // 该列在整行中所占的百分比
						layout : "form", // 从上往下的布局
						labelWidth : 90,
						labelAlign : 'right',
						items : [{
									xtype : "textfield",
									fieldLabel : '债权编号',
									name : 'plBidPlan.bidProNumber',
									allowBlank : false,
									anchor : '100%'

								}]
					},{
						columnWidth : 1, // 该列在整行中所占的百分比
						layout : "column", // 从上往下的布局
						labelAlign : 'right',
						items : [{
									columnWidth : .5, // 该列在整行中所占的百分比
									layout : "form", // 从上往下的布局
									labelWidth : leftWidth,
									labelAlign : 'right',
									items : [{
												fieldLabel : '借款开始日',
												id : 'loanStarTime',
												xtype : 'datetimefield',
												format : 'Y-m-d H:i:s',
												readOnly : true,
												allowBlank : false,
												anchor : '100%'

											}]
								}, {
									columnWidth : .5, // 该列在整行中所占的百分比
									layout : "form", // 从上往下的布局
									labelWidth : 70,
									labelAlign : 'right',
									items : [{
												fieldLabel : '借款截至日',
												xtype : 'datetimefield',
												format : 'Y-m-d H:i:s',
												id : 'loanEndTime',
												readOnly : true,
												allowBlank : false,
												anchor : '100%'

											}]
								}]

					}, {

						columnWidth : 1, // 该列在整行中所占的百分比
						layout : "column", // 从上往下的布局
						labelAlign : 'right',
						items : [{
									columnWidth : .12, // 该列在整行中所占的百分比
									layout : "form", // 从上往下的布局
									labelWidth : leftWidth,
									items : [{
												fieldLabel : "贷款利率 ",
												anchor : "100%"
											}]
								}, {
									columnWidth : .16, // 该列在整行中所占的百分比
									layout : "form", // 从上往下的布局
									labelWidth : 90,
									labelAlign : 'right',
									items : [{
												fieldLabel : '年利率',
												xtype : 'numberfield',
												id : 'yearInterestRate',
												allowBlank : false,
												readOnly : true,
												anchor : '100%'

											}]
								}, {
									columnWidth : .03, // 该列在整行中所占的百分比
									layout : "form", // 从上往下的布局
									labelWidth : 20,
									items : [{
												fieldLabel : "%",
												labelSeparator : '',
												anchor : "100%"
											}]
								}, {
									columnWidth : .2, // 该列在整行中所占的百分比
									layout : "form", // 从上往下的布局
									labelWidth : 90,
									labelAlign : 'right',
									items : [{
												fieldLabel : '月利率',
												xtype : 'numberfield',
												id : 'monthInterestRate',
												readOnly : true,
												allowBlank : false,
												anchor : '100%'

											}]
								}, {
									columnWidth : .03, // 该列在整行中所占的百分比
									layout : "form", // 从上往下的布局
									labelWidth : 20,
									items : [{
												fieldLabel : "%",
												labelSeparator : '',
												anchor : "100%"
											}]
								}, {
									columnWidth : .2, // 该列在整行中所占的百分比
									layout : "form", // 从上往下的布局
									labelWidth : 90,
									labelAlign : 'right',
									items : [{
												fieldLabel : '日利率',
												xtype : 'numberfield',
												id : 'dayInterestRate',
												readOnly : true,
												allowBlank : false,
												anchor : '100%'

											}]
								}, {
									columnWidth : .03, // 该列在整行中所占的百分比
									layout : "form", // 从上往下的布局
									labelWidth : 20,
									items : [{
												fieldLabel : "%",
												labelSeparator : '',
												anchor : "100%"
											}]
								}, {
									columnWidth : .2, // 该列在整行中所占的百分比
									layout : "form", // 从上往下的布局
									labelWidth : 90,
									labelAlign : 'right',
									items : [{
												fieldLabel : '合计利率',
												xtype : 'numberfield',
												id : 'totalInterestRate',
												readOnly : true,
												allowBlank : false,
												anchor : '100%'

											}]
								}, {
									columnWidth : .03, // 该列在整行中所占的百分比
									layout : "form", // 从上往下的布局
									labelWidth : 20,
									items : [{
												fieldLabel : "%",
												labelSeparator : '',
												anchor : "100%"
											}]
								}]

					}, {
						columnWidth : 1,
						layout : 'column',
						items : [{
									columnWidth : .3, // 该列在整行中所占的百分比
									layout : "form", // 从上往下的布局
									labelWidth : leftWidth,
									labelAlign : 'right',
									items : [interestPeriodGroup]
								}, {
									columnWidth : .10, // 该列在整行中所占的百分比
									layout : "form", // 从上往下的布局
									labelWidth : 70,
									items : [{
												fieldLabel : "付息方式 ",
												// 付息方式（1 按月等额本息 2按月等额本金
												// 3 按月等本等息 4 按 日/月/年/季
												// 付息到期还本 5期末一次性支付本息）
												anchor : "100%"
											}]
								}, {
									columnWidth : 0.11,
									labelWidth : 5,
									layout : 'form',
									labelAlign : 'right',
									items : [{
										xtype : 'radio',
										boxLabel : '按月等额本息',
										name : 'plOrRelease.paymentWay',
										id : "paymentWay1",
										inputValue : 1,
										anchor : "100%",
										listeners : {
											scope : this,
											check : function(obj, checked) {
												Ext
														.getCmp("paymentPeriodComboBox").readOnly = true;
												Ext
														.getCmp("paymentPeriodComboBox")
														.setValue(null);

											}
										},
										disabled : this.isReadOnly == null
												? false
												: this.isReadOnly
									}]
								}

						]
					}, {

						columnWidth : 1, // 该列在整行中所占的百分比
						layout : "column", // 从上往下的布局
						labelAlign : 'right',
						items : [{
									columnWidth : .5, // 该列在整行中所占的百分比
									layout : "form", // 从上往下的布局
									labelWidth : leftWidth,
									labelAlign : 'right',
									items : [{
												fieldLabel : '当前债权持有人',
												xtype : 'textfield',
												id : 'obligatoryPersion',
												readOnly : true,
												allowBlank : false,
												anchor : '100%'

											}]
								}, {
									columnWidth : .5, // 该列在整行中所占的百分比
									layout : "form", // 从上往下的布局
									labelWidth : 90,
									labelAlign : 'right',
									items : [{
												fieldLabel : '债权买入日',
												xtype : 'datetimefield',
												format : 'Y-m-d H:i:s',
												id : 'payTime',
												readOnly : true,
												allowBlank : false,
												anchor : '100%'

											}]
								}]

					}, {
						columnWidth : .22, // 该列在整行中所占的百分比
						layout : "form", // 从上往下的布局
						labelWidth : leftWidth,
						labelAlign : 'right',
						items : [{
									fieldLabel : '当前可转让金额',
									readOnly : true,
									id : 'residueMoney',
									xtype : 'numberfield',
									allowBlank : false,
									anchor : '100%'

								}]
					}, {
						columnWidth : .03, // 该列在整行中所占的百分比
						layout : "form", // 从上往下的布局
						labelWidth : 20,
						items : [{
									fieldLabel : "元 ",
									labelSeparator : '',
									anchor : "90%"
								}]
					}, {
						columnWidth : .22, // 该列在整行中所占的百分比
						layout : "form", // 从上往下的布局
						labelWidth : 90,
						labelAlign : 'right',
						items : [{
							fieldLabel : '本次转让金额',
							name : 'plBidPlan.bidMoney',
							id : 'orMoney',
							value : 0,
							listeners : {
								scope : this,
								'change' : function(nf) {
									var totalMoney = Ext.getCmp('loanProMoney').getValue();
									var currMoney = Ext.getCmp('orMoney').getValue();
									var residueMoney = Ext.getCmp('residueMoney').getValue();
									if(typeof(this.bidMoney2) == "undefined"){
										this.bidMoney2=residueMoney;
									}
									if (currMoney > this.bidMoney2) {
										if(this.initMoney){
											Ext.getCmp('residueMoney').setValue(this.initMoney);
										}
										alert("本次转让金额不能大于剩余金额！");
										nf.setValue(0);
										this.buttons[0].setDisabled(true);
										Ext.getCmp('orMoneyScale').setValue('');
									}else if(currMoney<0){
										if(this.initMoney){
											Ext.getCmp('residueMoney').setValue(this.initMoney);
										}
										alert("请输入正确的转让金额！");
										nf.setValue(0);
										this.buttons[0].setDisabled(true);
										Ext.getCmp('orMoneyScale').setValue('');
									}else{
										this.initMoney=this.bidMoney2;
										Ext.getCmp('orMoneyScale').setValue(currMoney / totalMoney* 100);
//										Ext.getCmp('residueMoney').setValue(this.bidMoney2- currMoney);
										this.buttons[0].setDisabled(false);
									}
								}
							},
							xtype : 'numberfield',
							allowBlank : false,
							anchor : '100%'

						}]
					}, {
						columnWidth : .03, // 该列在整行中所占的百分比
						layout : "form", // 从上往下的布局
						labelWidth : 20,
						items : [{
									fieldLabel : "元 ",
									labelSeparator : '',
									anchor : "90%"
								}]
					}, {
						columnWidth : .22, // 该列在整行中所占的百分比
						layout : "form", // 从上往下的布局
						labelWidth : 90,
						labelAlign : 'right',
						items : [{
									fieldLabel : '占比',
									name : 'plBidPlan.bidMoneyScale',
									id : 'orMoneyScale',
									readOnly : true,
									xtype : 'numberfield',
									allowBlank : false,
									anchor : '100%'

								}]
					}, {
						columnWidth : .03, // 该列在整行中所占的百分比
						layout : "form", // 从上往下的布局
						labelWidth : 20,
						items : [{
									fieldLabel : "% ",
									labelSeparator : '',
									anchor : "90%"
								}]
					}, {
						columnWidth : .25,//5 该列在整行中所占的百分比
						layout : "form", // 从上往下的布局
						labelWidth : 90,
						labelAlign : 'right',
						items : [{
									fieldLabel : '转让日期',
									name : 'plBidPlan.bidEndTime',
									xtype : 'datefield',
									format:"Y-m-d",
									allowBlank : false,
									anchor : '100%'

								}]
					}]
		});
		// 加载表单对应的数据
		if (this.bidId != null && this.bidId != 'undefined') {
			this.formPanel.loadData({
				url : __ctxPath
					+ '/creditFlow/financingAgency/getPlBidPlan.do?bidId='
						+ this.bidId,
				root : 'data',
				preName : 'plBidPlan',
				success : function(response, options) {
					var respText = response.responseText;
					var result = Ext.util.JSON.decode(respText);
				    Ext.getCmp('ProType').setValue(result.data.plBiddingType.biddingTypeId);
				}
			});
		}
          
		if (this.porProId != null && this.porProId != 'undefined') {
				//获取 剩余招标金额
	    Ext.Ajax.request({
			url : __ctxPath
						+ '/creditFlow/financingAgency/persion/getBidInfoBpPersionOrPro.do?porProId='
						+ this.porProId,
			method:"post",
			success:function(response,opts){
				var res = Ext.util.JSON.decode(response.responseText);
				var data = res.data;
				Ext.getCmp('residueMoney').setValue(data.residueMoney);//剩余招标金额
			}
		});
			Ext.getCmp('porProId').setValue(this.porProId);
			Ext.getCmp('loanProName').setValue(this.proName);
			Ext.getCmp('loanProNubmer').setValue(this.proNumber);

			Ext.getCmp('yearInterestRate').setValue(this.yearInterestRate);
			Ext.getCmp('monthInterestRate').setValue(this.monthInterestRate);
			Ext.getCmp('dayInterestRate').setValue(this.dayInterestRate);
			Ext.getCmp('totalInterestRate').setValue(this.totalInterestRate);
			Ext.getCmp('loanStarTime').setValue(this.loanStarTime);
			Ext.getCmp('loanEndTime').setValue(this.loanEndTime);
			Ext.getCmp('loanProMoney').setValue(this.bidMoney);
			Ext.getCmp('obligatoryPersion').setValue(this.obligatoryPersion);
			Ext.getCmp('payTime').setValue(this.loanStarTime);
			Ext.getCmp('proType').setValue(this.proType);
			
             this.fillPayWay(this.payIntersetWay);
			/*
			 * interestPeriod:this.interestPeriod,
			 * payIntersetWay:this.payIntersetWay,
			 */

		}

	},// end of the initcomponents

	/**
	 * 重置
	 * 
	 * @param {}
	 *            formPanel
	 */
	reset : function() {
		this.formPanel.getForm().reset();
	},
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
			url : __ctxPath+ '/creditFlow/financingAgency/savePlMmObligatoryRightChildren.do',

			callback : function(fp, action) {
				if (this.gp != null) {
					this.gp.getStore().reload();
				}
				this.close();
				Ext.getCmp('PlPersionOrPlanView').gridPanel.getStore().reload();
				var PlPersionOrBidListForm=Ext.getCmp('PlPersionOrBidListForm');
				if(null==PlPersionOrBidListForm.isCreditor){
					PlPersionOrBidListForm.isCreditor=1;
					PlPersionOrBidListForm.topbar.items.items[0].hide();
					PlPersionOrBidListForm.topbar.items.items[1].hide();
					PlPersionOrBidListForm.topbar.items.items[2].hide();
				}
			}
		});
	},// end of save
   fillPayWay:function(payWay){
   	// 付息方式（1 按月等额本息 2按月等额本金
	// 3 按月等本等息 4 按 日/月/年/季
    // 付息到期还本 5期末一次性支付本息）
   	if(payWay==1)
   Ext.getCmp('paymentWay1').boxLabel="按月等额本息";
    	if(payWay==2)
   Ext.getCmp('paymentWay1').boxLabel="按月等额本金";
    	if(payWay==3)
   Ext.getCmp('paymentWay1').boxLabel="按月等本等息 ";
    	if(payWay==4)
   Ext.getCmp('paymentWay1').boxLabel="按";
    	if(payWay==5)
   Ext.getCmp('paymentWay1').boxLabel="期末一次性支付本息";
   }
});