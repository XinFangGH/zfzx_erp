//PerInvestFundIntentWindow.js
PerInvestFundIntentWindow = Ext.extend(Ext.Window, {
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		// 初始化组件
		this.initUIComponents();
		// 调用父类构造
		PerInvestFundIntentWindow.superclass.constructor.call(this, {
					title : '收益清单',
					region : 'center',
					layout : 'border',
					height : 500,
					width : 1000,
					items : [ this.gridPanel]
				});
	},// end of constructor
	// 初始化组件
	initUIComponents : function() {
		// 初始化搜索条件Panel
		this.topbar = new Ext.Toolbar({
			items : [	{
								iconCls : 'btn-print',
								text : '打印',
								xtype : 'button',
								scope : this,
								hidden:this.isPrint,
								handler : this.createRs
							},{
								iconCls : 'btn-user-paixi',
								text : '派息',
								xtype : 'button',
								scope : this,
								hidden:this.isAssignInterRest,
								handler : this.assignInterest
							},"->",{xtype:'label',html : '【<font style="line-height:20px" color=red>投资人：</font>'+this.investPersonName},
						{xtype:'label',html : '<font color=red>&nbsp;&nbsp&nbsp;&nbsp投资项目名称：</font>'+this.mmName},
						{xtype:'label',html : '<font color=red>&nbsp;&nbsp&nbsp;&nbsp 购买金额：</font>'+Ext.util.Format.number(this.buyMoney,',000,000,000.00')+"元"},
						{xtype:'label',html : '<font color=red>&nbsp;&nbsp&nbsp;&nbsp订单期限：</font>'+this.orderlimit+"天"},
						this.promisIncomeSum==null?'':{xtype:'label',html : '<font color=red>&nbsp;&nbsp&nbsp;&nbsp 承诺总收益：</font>'+Ext.util.Format.number(this.promisIncomeSum,',000,000,000.00')+"元"}
						,this.currentGetedMoney==null?'':{xtype:'label',html : '&nbsp;&nbsp&nbsp;&nbsp<font color=red>当前已实现收益：</font>'+Ext.util.Format.number(this.currentGetedMoney,',000,000,000.00')+'元'}
						,{xtype:'label',html : ' 】'}
							]
		});
	var summary = new Ext.ux.grid.GridSummary();
		function totalMoney(v, params, data) {
			return '总计(元)';
		}
		this.gridPanel = new HT.GridPanel({
			region : 'center',
			tbar : this.topbar,
			plugins : [summary],
	//		hiddenCm :true,
			singleSelect : true,
			url : __ctxPath + "/creditFlow/finance/list3BpFundIntent.do?investpersonId="+this.investpersonId+"&planId="+this.planId,
			fields : [{
						name : 'fundIntentId'
					}, {
						name : 'fundType'
					}, {
						name : 'fundTypeName'
					}, {
						name : 'incomeMoney'
					}, {
						name : 'payMoney'
					}, {
						name : 'intentDate'
					}, {
						name : 'investPersonId'
					}, {
						name : 'investPersonName'
					}, {
						name : 'remark'
					}, {
						name : 'payintentPeriod'
					}, {
						name : 'isValid'
					}, {
						name : 'factDate'
					}, {
						name : 'afterMoney'
					}, {
						name : 'notMoney'
					}, {
						name : 'flatMoney'
					}, {
						name : 'fundResource'
					}, {
						name : 'preceptId'
					}, {
						name : 'bidPlanId'
					}, {
						name : 'projectName'
					}],
			columns : [{
						header : 'fundIntentId',
						dataIndex : 'fundIntentId',
						hidden : true
					}, {
						header : '投资人姓名',
						dataIndex : 'investPersonName',
						readOnly : this.isHidden,
						sortable : true,
						width : 100,
						scope : this,
						summaryType : 'count',
						summaryRenderer : totalMoney
					},{
						header : '资金类型',
						dataIndex : 'fundTypeName',
						width : 107,
						summaryType : 'count',
						summaryRenderer : totalMoney
					}, {
						header : '计划到账日',
						dataIndex : 'intentDate',
						format : 'Y-m-d',
						fixed : true,
						width : 80,
						renderer : function(value, metaData, record, rowIndex,
								colIndex, store) {
							var v;
							try {
								if (typeof(value) == "string") {
									v = value;
								} else {
									v = Ext.util.Format.date(value, 'Y-m-d');
								}
							} catch (err) {
								v = value;
							}
							if (record.data.isValid == 1) {
								return '<font style="font-style:italic;text-decoration: line-through;color:gray">'
										+ v + "</font>";
							} else {
								return v;
							}
						}
					}, {
						header : '计划收入金额',
						dataIndex : 'incomeMoney',
						summaryType : 'sum',
						align : 'right',
						renderer : function(value, metaData, record, rowIndex,
								colIndex, store) {
							if (record.data.isValid == 1) {
								return '<font style="font-style:italic;text-decoration: line-through;color:gray">'
										+ Ext.util.Format.number(value,
												',000,000,000.00') + "元</font>"
							} else {
								return Ext.util.Format.number(value,
										',000,000,000.00')
										+ "元";
							}
						}
					}, {
						header : '实际到账日',
						dataIndex : 'factDate',
						format : 'Y-m-d',
						hidden : false,
						editor : this.datafield,
						fixed : true,
						width : 80,
						renderer : function(value, metaData, record, rowIndex,
								colIndex, store) {
							var v;
							try {
								if (typeof(value) == "string") {
									v = value;
								} else {
									v = Ext.util.Format.date(value, 'Y-m-d');
								}
							} catch (err) {
								v = value;
							}
							if (record.data.isValid == 1) {
								return '<font style="font-style:italic;text-decoration: line-through;color:gray">'
										+ v + "</font>";
							} else {
								return v;
							}
						}
					}, {
						header : '已对账金额(元)',
						summaryType : 'sum',
						hidden : false,
						dataIndex : 'afterMoney',
						renderer : function(value, metaData, record, rowIndex,
								colIndex, store) {
							if (record.data.isValid == 1) {
								return '<font style="font-style:italic;text-decoration: line-through;color:gray">'
										+ Ext.util.Format.number(value,
												',000,000,000.00') + "元</font>"
							} else {
								return Ext.util.Format.number(value,
										',000,000,000.00')
										+ "元";
							}
						}
					}, {
						header : '计划支出金额',
						dataIndex : 'payMoney',
						align : 'right',
						summaryType : 'sum',
						renderer : function(value, metaData, record, rowIndex,
								colIndex, store) {
							if (record.data.isValid == 1) {
								return '<font style="font-style:italic;text-decoration: line-through;color:gray">'
										+ Ext.util.Format.number(value,
												',000,000,000.00') + "元</font>"
							} else {
								return Ext.util.Format.number(value,
										',000,000,000.00')
										+ "元";
							}
						}
					}, {
						header : '期数',
						dataIndex : 'payintentPeriod',
						renderer : function(value, metaData, record, rowIndex,
								colIndex, store) {
							if (null != value) {
								if (record.data.isValid == 1) {
									return '<font style="font-style:italic;text-decoration: line-through;color:gray">'
											+ '第' + value + '期' + "</font>"
								} else {
									return '第' + value + '期';
								}
							}
						}
					}, {
						header : '未对账金额(元)',
						summaryType : 'sum',
						hidden : true,
						dataIndex : 'notMoney',
						renderer : function(value, metaData, record, rowIndex,
								colIndex, store) {
							if (record.data.isValid == 1) {
								return '<font style="font-style:italic;text-decoration: line-through;color:gray">'
										+ Ext.util.Format.number(value,
												',000,000,000.00') + "元</font>"
							} else {
								return Ext.util.Format.number(value,
										',000,000,000.00')
										+ "元";
							}
						}
					}, {
						header : '平账金额',
						width : 150,
						hidden : true,
						dataIndex : 'flatMoney',
						renderer : function(value, metaData, record, rowIndex,
								colIndex, store) {
							if (record.data.isValid == 1) {
								return '<font style="font-style:italic;text-decoration: line-through;color:gray">'
										+ Ext.util.Format.number(value,
												',000,000,000.00') + "元</font>"
							} else {
								return Ext.util.Format.number(value,
										',000,000,000.00')
										+ "元";
							}
						}
					}]
				// end of columns
		});

		this.gridPanel.addListener('rowdblclick', this.rowClick);

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
	}
});