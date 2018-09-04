/**
 * @author
 * @class PlManageMoneyPlanView
 * @extends Ext.Panel
 * @description [PlManageMoneyPlan]管理
 * @company 智维软件
 * @createtime:
 */
UPlanInfo = Ext.extend(Ext.Panel, {
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		// 初始化组件
		this.initUIComponents();
		// 调用父类构造
		UPlanInfo.superclass.constructor.call(this, {
					id : 'UPlanInfo',
					title : '投标记录查询',
					region : 'center',
					layout : 'border',
					iconCls : 'btn-tree-team30',
					items : [this.searchPanel, this.gridPanel]
				});
	},// end of constructor
	// 初始化组件
	
	initUIComponents : function() {
		// 初始化搜索条件Panel
		this.searchPanel = new HT.SearchPanel({
					id : 'UPlanInfoSearchPanel',
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
							columnWidth : .15,
							labelAlign : 'right',
							xtype : 'container',
							layout : 'form',
							labelWidth : 60,
							defaults : {
								anchor : '100%'
							},
							items : [{
										fieldLabel : '投资日期',
										name : 'buyDatetime_GE',
										xtype : 'datefield',
										format : "Y-m-d",
										value:new Date()
							}]
						},{
							columnWidth : .11,
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
									anchor : '100%'
							}]
						},{
							columnWidth : .15,
							labelAlign : 'right',
							xtype : 'container',
							layout : 'form',
							labelWidth : 60,
							defaults : {
								anchor : '100%'
							},
							items : [{
								hiddenName : "state",
								fieldLabel : '投资状态',
								anchor : '100%',
								xtype : 'combo',
								mode : 'local',
								valueField : 'value',
								name : 'state',
								editable : false,
								displayField : 'item',
								value:1,
								store : new Ext.data.SimpleStore({
											fields : ["item", "value"],
											data : [["已流标", "-2"],
													["购买中", "1"],
													["持有中", "2"],
													["提前退出申请中","7"],
													["已退出", "8"],
													["已完成", "10"]]
										}),
								triggerAction : "all"
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
								iconCls : 'btn-xls',
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
		this.gridPanel = new HT.GridPanel({
			region : 'center',
			tbar : this.topbar,
			plugins : [summary],
			singleSelect:true,
			// 使用RowActions
			id : 'ExperienceStandardInfoGrid',
			url : __ctxPath
					+ "/creditFlow/financingAgency/listInfoPlManageMoneyPlanBuyinfo.do?keystr="+this.keystr,
			fields : [{
						name : 'mmplanId',
						type : 'Long'
					}, 'plManageMoneyPlan.mmName','keystr','investPersonName','couponsMoney','buyDatetime','endinInterestTime','startinInterestTime'
					,'plManageMoneyPlan.mmNumber','promisIncomeSum','buyMoney','state'],
			columns : [{
						header : 'mmplanId',
						dataIndex : 'mmplanId',
						align:'center',
						hidden : true
					}, {
						header : '账号',
						align:'center',
						summaryRenderer : totalMoney,
						dataIndex : 'investPersonName'
					},{
						header : '投资金额',
						dataIndex : 'buyMoney',
						align:'right',
						 summaryType : 'sum',
						renderer:function(v){
							return Ext.util.Format.number(v,',000,000,000.000')+"元";
						}
					},{
						header : '计划编号',
						 summaryType : 'count',
						 align : 'center',
						dataIndex : 'plManageMoneyPlan.mmNumber'
					}, {
						header : '计划名称',
						dataIndex : 'plManageMoneyPlan.mmName'
					}, {
						header : '投标日期',
						dataIndex : 'buyDatetime',
					    align : 'center'
					}, {
						header : '起息日期',
						dataIndex : 'startinInterestTime',
					    align : 'center'
					}, {
						header : '到期日期',
						dataIndex : 'endinInterestTime',
					    align : 'center'
					},{
						header : '状态',
						align : 'center',
						dataIndex : 'state',
						renderer:function(v){
							if(v == -2){
								return '已流标';
							}else if(v == 1){
								return '购买中';
							}else if(v == 2){
								return '持有中';
							}else if(v == 7){
								return '提前退出申请中';
							}else if(v == 8){
							    return '已退出';
							}else if(v == 10){
							    return '已完成';
							}
						}
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
	    var keystr='UPlan';
		var state=this.searchPanel.getCmpByName('state').getValue();
		var mmNumber=this.searchPanel.getCmpByName('mmNumber').getValue();
		var mmName=this.searchPanel.getCmpByName('mmName').getValue();
		var buyDatetime_GE=this.searchPanel.getCmpByName('buyDatetime_GE').getValue();
		var buyDatetime_LE=this.searchPanel.getCmpByName('buyDatetime_LE').getValue();
		window.open(__ctxPath + "/creditFlow/financingAgency/toExcelUPlanListPlManageMoneyPlanBuyinfo.do?"
		        +"keystr="+keystr
		        +"&state="+state
				+"&mmNumber="+mmNumber
				+"&mmName="+mmName
				+"&buyDatetime_GE="+Ext.util.Format.date(buyDatetime_GE, 'Y-m-d')
				+"&buyDatetime_LE="+Ext.util.Format.date(buyDatetime_LE, 'Y-m-d')
				);
	}
	
});
