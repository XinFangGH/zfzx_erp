/**
 * @author
 * @class PlManageMoneyPlanView
 * @extends Ext.Panel
 * @description [PlManageMoneyPlan]管理
 * @company 智维软件
 * @createtime:
 */
ExperienceStandardInfo = Ext.extend(Ext.Panel, {
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		// 初始化组件
		this.initUIComponents();
		// 调用父类构造
		ExperienceStandardInfo.superclass.constructor.call(this, {
					id : 'ExperienceStandardInfo',
					title : '体验标投标记录',
					region : 'center',
					layout : 'border',
					iconCls:"menu-finance",
					items : [this.searchPanel, this.gridPanel]
				});
	},// end of constructor
	// 初始化组件
	
	initUIComponents : function() {
		// 初始化搜索条件Panel
		this.searchPanel = new HT.SearchPanel({
					id : 'ExperienceStandardInfoSearchPanel',
					layout : 'form',
					border : false,
					region : 'north',
					height : 65,
					anchor : '70%',
					keys : [{
						key : Ext.EventObject.ENTER,
						fn : this.search,
						scope : this
					}, {
						key : Ext.EventObject.ESC,
						fn : this.reset,
						scope : this
					}],
					items : [{
						border : false,
						layout : 'column',
						style : 'padding-left:5px;padding-right:0px;padding-top:5px;',
						layoutConfig : {
							align : 'middle',
							padding : '5px'
						},
						defaults : {
							xtype : 'label',
							anchor : '95%'

						},
						items : [{
							columnWidth : .2,
							labelAlign : 'right',
							xtype : 'container',
							layout : 'form',
							labelWidth : 60,
							defaults : {
								anchor : '100%'
							},
							items : [{
										fieldLabel : '招标编号',
										name : 'mmNumber',
										xtype : 'textfield'
							}]
						},{
							columnWidth : .2,
							labelAlign : 'right',
							xtype : 'container',
							layout : 'form',
							labelWidth : 60,
							defaults : {
								anchor : '100%'
							},
							items : [{
										fieldLabel : '招标名称',
										name : 'mmName',
										xtype : 'textfield'
							}]
						},{
							columnWidth : .2,
							labelAlign : 'right',
							xtype : 'container',
							layout : 'form',
							labelWidth : 80,
							defaults : {
								anchor : '100%'
							},
							items : [{
										fieldLabel : '投资日期',
										name : 'buyDatetime_GE',
										xtype : 'datefield',
										format : "Y-m-d"
							}]
						},{
							columnWidth : .2,
							labelAlign : 'right',
							xtype : 'container',
							layout : 'form',
							labelWidth : 20,
							defaults : {
								anchor : '100%'
							},
							items : [{
									fieldLabel : '至',
									name : 'buyDatetime_LE',
									xtype : 'datefield',
									format : "Y-m-d",
									anchor : '75%'
							}]
						},{
							columnWidth : .07,
							xtype : 'container',
							layout : 'form',
							defaults : {
								xtype : 'button'
							},
							style : 'padding-left:10px;',
							items : [{
								text : '查询',
								scope : this,
								iconCls : 'btn-search',
								handler : this.search
						}]},{columnWidth : .07,
							xtype : 'container',
							layout : 'form',
							defaults : {
								xtype : 'button'
							},
							style : 'padding-left:10px;',
							items : [{
								text : '重置',
								scope : this,
								iconCls : 'btn-reset',
								handler : this.reset
							}]
						}]
					}]
				});// end of searchPanel

		this.topbar = new Ext.Toolbar({
					items : [{
								iconCls : 'btn-xls1',
								text : '导出列表',
								xtype : 'button',
								scope : this,
								handler : this.toExcelList
							}]
				});
				var summary = new Ext.ux.grid.GridSummary();
				function totalMoney(v, params, data) {
					return '总计';
				}
				var keystr='experience';
				var state="1,2,10";

		this.gridPanel = new HT.GridPanel({
			region : 'center',
			tbar : this.topbar,
			plugins : [summary],
			singleSelect:true,
			// 使用RowActions
			id : 'ExperienceStandardInfoGrid',
			url : __ctxPath
					+ "/creditFlow/financingAgency/listInfoPlManageMoneyPlanBuyinfo.do?keystr="+keystr+"&state="+state,
			fields : [{
						name : 'mmplanId',
						type : 'Long'
					}, 'plManageMoneyPlan.mmName','keystr','investPersonName','couponsMoney','buyDatetime','endinInterestTime','startinInterestTime'
					,'plManageMoneyPlan.mmNumber','promisIncomeSum'],
			columns : [{
						header : 'mmplanId',
						dataIndex : 'mmplanId',
						hidden : true
					}, {
						header : '账号',
						align:'center',
						summaryRenderer : totalMoney,
						dataIndex : 'investPersonName'
					},{
						header : '优惠券金额',
						align:'right',
						summaryType : 'sum',
						dataIndex : 'couponsMoney',
						renderer:function(v){
							return Ext.util.Format.number(v,',000,000,000.000')+"元";
						}
					},{
						header : '预期收益',
						summaryType : 'sum',
						align:'right',
						dataIndex : 'promisIncomeSum',
						renderer:function(v){
							return Ext.util.Format.number(v,',000,000,000.000')+"元";
						}
					},{
						header : '招标编号',
						align:'center',
						dataIndex : 'plManageMoneyPlan.mmNumber'
					}, {
						header : '招标名称',
						align:'center',
						dataIndex : 'plManageMoneyPlan.mmName'
					}, {
						header : '投标日期',
						align:'center',
						dataIndex : 'buyDatetime',
					    align : 'right'
					}, {
						header : '起息日期',
						dataIndex : 'startinInterestTime',
					    align : 'right'
					}, {
						header : '到期日期',
						dataIndex : 'endinInterestTime',
					    align : 'right'
					}]
				// end of columns
			});


	},// end of the initComponents()
	// 重置查询表单
	reset : function() {
		this.searchPanel.getForm().reset();
	},
	// 按条件搜索
	search : function() {
		$search({
					searchPanel : this.searchPanel,
					gridPanel : this.gridPanel
				});
	},

	
	//导出到Excel
	toExcelList : function(){
	    var keystr='experience';
		var state="1,2,10";
		var mmNumber=this.searchPanel.getCmpByName('mmNumber').getValue();
		var mmName=this.searchPanel.getCmpByName('mmName').getValue();
		var buyDatetime_GE=this.searchPanel.getCmpByName('buyDatetime_GE').getValue();
		var buyDatetime_LE=this.searchPanel.getCmpByName('buyDatetime_LE').getValue();
		window.open(__ctxPath + "/creditFlow/financingAgency/toExcelListPlManageMoneyPlanBuyinfo.do?"
		        +"keystr="+keystr
		        +"&state="+state
				+"&mmNumber="+mmNumber
				+"&mmName="+mmName
				+"&buyDatetime_GE="+Ext.util.Format.date(buyDatetime_GE, 'Y-m-d')
				+"&buyDatetime_LE="+Ext.util.Format.date(buyDatetime_LE, 'Y-m-d')
				);
	}
	
});
