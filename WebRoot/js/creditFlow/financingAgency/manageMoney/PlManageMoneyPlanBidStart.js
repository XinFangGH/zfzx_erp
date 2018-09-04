/**
 * @author
 * @createtime
 * @class SlBankAccountForm
 * @extends Ext.Window
 * @description SlBankAccount表单
 * @company 智维软件
 */
PlManageMoneyPlanBidStart = Ext.extend(Ext.Window, {
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		// 必须先初始化组件
		this.initUIComponents();
		PlManageMoneyPlanBidStart.superclass.constructor.call(this, {
					id : 'PlManageMoneyPlanBidStart',
					layout : 'fit',
					border : false,
					items : this.formPanel,
					modal : true,
					height : 460,
					width : 1000,
					// maximizable : true,
					title : '',
					buttonAlign : 'center',
					buttons : [{
								text : '保存',
								iconCls : 'btn-save',
								scope : this,
								hidden : this.isAllReadOnly,
								disabled : this.isAllReadOnly,
								handler : this.save
							}, {
								text : '重置',
								iconCls : 'btn-reset',
								scope : this,
								hidden : this.isAllReadOnly,
								disabled : this.isAllReadOnly,
								handler : this.reset
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
		var leffwidth = 100;
		this.mark=this.mmplanId;
		this.uploads= new uploads({
							    	projectId:this.mark,
							    	isHidden : false,
							    	businessType :"fsdf",
							    	isNotOnlyFile : false,
							    	isHiddenColumn : false,
							    	isDisabledButton : false,
							    	setname :'理财协议',
							    	titleName :'理财协议',
							    	tableName :'typeislcjhxy',
							    	uploadsSize :1,
							    	isHiddenOnlineButton :true,
							    	isDisabledOnlineButton :true
							    })
		this.formPanel = new Ext.FormPanel({
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
									fieldLabel : '理财编号',
									name : 'plManageMoneyPlan.mmNumber',
									allowBlank : false,
									xtype : 'textfield',
									anchor : '100%'
								}]
					}, {
						columnWidth : .31,
						layout : 'form',
						labelWidth : leffwidth,
						items : [{
									fieldLabel : '招标金额',
									name : 'plManageMoneyPlan.sumMoney',
									xtype : 'moneyfield',
									fieldClass : 'field-align',
									allowBlank : false,
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
						layout : 'form',
						labelWidth : leffwidth,
						items : [{
									fieldLabel : '收益方式',
									name : 'plManageMoneyPlan.benefitWay',
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
						columnWidth : .31,
						labelWidth : leffwidth,
						layout : 'form',
						items : [{
									fieldLabel : '投资期限',
									name : 'plManageMoneyPlan.investlimit',
									allowBlank : false,
									fieldClass : 'field-align',
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
						columnWidth : 1,
						layout : 'form',
						labelWidth : leffwidth,
						items : [{
									fieldLabel : '投资范围',
									name : 'plManageMoneyPlan.investScope',
									xtype : 'textfield',
									anchor : '100%'
								}]
					}, {
						columnWidth : 1,
						labelWidth : leffwidth,
						layout : 'form',
						items : [{
									fieldLabel : '招标说明',
									name : 'plManageMoneyPlan.bidRemark',
									xtype : 'textarea',
									anchor : '100%'
								}]
					}, {
						columnWidth : 1,
						labelWidth : leffwidth,
						layout : 'form',
						items : [this.uploads]
					}]
		});

		// 加载表单对应的数据
		if (this.mmplanId != null && this.mmplanId != 'undefined') {
			this.formPanel.loadData({
				url : __ctxPath
						+ '/creditFlow/financingAgency/getPlManageMoneyPlan.do?mmplanId='
						+ this.mmplanId,
				root : 'data',
				preName : 'plManageMoneyPlanfsdfsd'
			});
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
		var fpl= this.formPanel;
		var mark=null;
		$postForm({
					formPanel : this.formPanel,
					scope : this,
					url : __ctxPath
							+ '/creditFlow/financingAgency/savePlManageMoneyPlan.do?keystr=mmproduce',
					callback : function(fp, action) {
							var obj = Ext.util.JSON.decode(action.response.responseText);
						 fpl.getCmpByName("plManageMoneyPlan.mmplanId").setValue(obj.id);
						this.uploads.projId=obj.id;
						var gridPanel = Ext.getCmp('PlManageMoneyProduceGrid');
						if (gridPanel != null) {
							gridPanel.getStore().reload();
						}
					//	this.close();
					}
				});
			
	}// end of save

});