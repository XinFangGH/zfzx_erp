//YeePayQueryView


YeePayQueryView = Ext.extend(Ext.Panel, {
	// 构造函数
	titlePrefix : "",
	mode : "",
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		// 初始化组件
		this.initUIComponents();
		// 调用父类构造
		YeePayQueryView.superclass.constructor.call(this, {
					id : 'YeePayQueryView',
					title : "总览记录查询",
					region : 'center',
					layout : 'border',
					iconCls :'btn-tree-team39',
					items : [this.searchPanel, this.gridPanel]
				});
	},// end of constructor
	// 初始化组件
	initUIComponents : function() {
		// 初始化搜索条件Panel
		var itemwidth = 0.2;
		var isShow=false;
		this.searchPanel = new HT.SearchPanel({
					frame : false,
					region : 'north',
					height : 35,
					monitorValid : true,
					layout : 'column',
					bodyStyle : 'padding:0px 0px 0px 0px',
					border : false,
					defaults : {
						layout : 'form',
						border : false,
						labelWidth : 60,
						bodyStyle : 'padding:5px 0px 0px 5px'
					},
					labelAlign : "right",
					items : [{
								columnWidth : 0.2,
								items : [{
											fieldLabel : '日期',
											allowBlank : false,
											name:'dateName',
											xtype : 'datefield',
											format:'Y-m-d'
											
										}]
							},{
								columnWidth : 0.1,
								items : [{
											xtype : 'button',
											text : '查询',
											iconCls : 'btn-tree-team25',
											width : 60,
											formBind : true,
											labelWidth : 20,
											bodyStyle : 'padding:5px 0px 0px 0px',
											scope : this,
											handler : this.searchByCondition
										}]
							}]
				});// end of searchPanel
		var summary = new Ext.ux.grid.GridSummary();
		function totalMoney(v, params, data) {
			return '总计(元)';
		}
		this.gridPanel = new HT.GridPanel({
			region : 'center',
			isautoLoad:false,
			rowActions : false,
			 plugins : [summary],
			url : __ctxPath +"/pay/querydatePay.do",
			fields : [ {
						name : 'id',
						type : 'int'
						},
					'status',  'amount',
					'bizType','fee','balance','time','requestNo','platformNo'],
					columns : [ {
							header : '交易账号',
							summaryRenderer : totalMoney,
							dataIndex : 'platformNo'
						},{
						header : '交易类型',
						dataIndex : 'bizType'
					}, {
						header : '流水号',
						dataIndex : 'requestNo'
					},{
						header : '金额',
						summaryType : 'sum',
						dataIndex : 'amount'
					},{
						header : '平台手续费',
						summaryType : 'sum',
						dataIndex : 'fee'
					},{
						header : '平台分成',
						dataIndex : 'balance'
					}/*,{
						header : '交易时间',
						dataIndex : 'time'
					}*/]
				// end of columns
			});

	},// end of the initComponents()
	// 重置查询表单
	reset : function() {
		this.searchPanel.getForm().reset();
	},
	// 按条件搜索
	searchByCondition : function() {
		$search({
					searchPanel : this.searchPanel,
					gridPanel : this.gridPanel
				});
	}
});