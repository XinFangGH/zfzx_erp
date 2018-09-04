/**
 * @author
 * @class PlMmOrderChildrenOrView
 * @extends Ext.Panel
 * @description [PlMmOrderChildrenOr]管理
 * @company 智维软件
 * @createtime:
 */
PlMmOrderChildrenorTestView = Ext.extend(Ext.Window, {
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		// 初始化组件
		this.initUIComponents();
		// 调用父类构造
		PlMmOrderChildrenorTestView.superclass.constructor.call(this, {
					id : 'PlMmOrderChildrenorTestView',
					title : '预测债权清单',
					region : 'center',
					layout : 'border',
					height : 500,
					width : 1000,
					items : [this.gridPanel]
				});
	},// end of constructor
	// 初始化组件
	initUIComponents : function() {
		// 初始化搜索条件Panel

		this.topbar = new Ext.Toolbar({
			items : [/*{
								iconCls : 'btn-print',
								text : '打印',
								xtype : 'button',
								scope : this,
								handler : this.createRs
							},*/"->",	{xtype:'label',html : '【<font style="line-height:20px" color=red>投资人：</font>'+this.investPersonName},
						{xtype:'label',html : '<font color=red>&nbsp;&nbsp&nbsp;&nbsp理财产品名称：</font>'+this.mmName},
						{xtype:'label',html : '<font color=red>&nbsp;&nbsp&nbsp;&nbsp 购买金额：</font>'+Ext.util.Format.number(this.buyMoney,',000,000,000.00')+"元"},
						{xtype:'label',html : '<font color=red>&nbsp;&nbsp&nbsp;&nbsp订单期限：</font>'+this.orderlimit+"天"},
						{xtype:'label',html : '<font color=red>&nbsp;&nbsp&nbsp;&nbsp 承诺总收益：</font>'+Ext.util.Format.number(this.promisIncomeSum,',000,000,000.00')+"元"}
						,{xtype:'label',html : '&nbsp;&nbsp&nbsp;&nbsp<font color=red>当前已实现收益：</font>'+Ext.util.Format.number(this.currentGetedMoney,',000,000,000.00')+'元】'}
							]
		});

		this.gridPanel = new HT.GridPanel({
			region : 'center',
			tbar : this.topbar,
			hiddenCm :true,
			// 使用RowActions
			id : 'matchingDetailGrid',
			url : __ctxPath
					+ "/creditFlow/financingAgency/listPlMmOrderChildrenorTest.do?Q_orderId_L_EQ="+this.orderId,
			fields : [{
						name : 'matchId',
						type : 'int'
					}, 'orderId', 'childrenorId', 'investPersonId',
					'investPersonName', 'mmplanId', 'mmName', 'parentOrBidId',
					'parentOrBidName', 'matchingMoney', 'childrenOrDayRate',
					'matchingStartDate', 'matchingEndDate', 'matchingLimit',
					'matchingEndDateType', 'matchingGetMoney', 'matchingState'],
			columns : [{
						header : 'matchId',
						dataIndex : 'matchId',
						hidden : true
					}, {
						header : '债权的名称',
						dataIndex : 'parentOrBidName'
					}, {
						header : '匹配金额',
						dataIndex : 'matchingMoney',
						align : 'right',
						renderer:function(v){
							return Ext.util.Format.number(v,',000,000,000.00')+"元";
						}
					}, {
						header : '债权的日化利率',
						dataIndex : 'childrenOrDayRate',
						renderer:function(v){
								return v+"%";
						}
					}, {
						header : '匹配开始日',
						dataIndex : 'matchingStartDate'
					}, {
						header : '匹配结束日',
						dataIndex : 'matchingEndDate'
					}, {
						header : '匹配期限',
						dataIndex : 'matchingLimit',
						align : 'right',
						renderer:function(v){
								return v+"天";
						}
					}, {
						header : '此匹配获得的收益',
						dataIndex : 'matchingGetMoney',
						align : 'right',
						renderer:function(v){
							return Ext.util.Format.number(v,',000,000,000.00')+"元";
						}
					}/*, {
						header : 'matchingState',
						dataIndex : 'matchingState'
					}*/]
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
	// GridPanel行点击处理事件
	rowClick : function(grid, rowindex, e) {
		grid.getSelectionModel().each(function(rec) {
					new PlMmOrderChildrenOrForm({
								matchId : rec.data.matchId
							}).show();
				});
	},
	// 创建记录
	createRs : function() {
		new PlMmOrderChildrenOrForm().show();
	},
	// 按ID删除记录
	removeRs : function(id) {
		$postDel({
			url : __ctxPath
					+ '/creditFlow.financingAgency.manageMoney/multiDelPlMmOrderChildrenOr.do',
			ids : id,
			grid : this.gridPanel
		});
	},
	// 把选中ID删除
	removeSelRs : function() {
		$delGridRs({
			url : __ctxPath
					+ '/creditFlow.financingAgency.manageMoney/multiDelPlMmOrderChildrenOr.do',
			grid : this.gridPanel,
			idName : 'matchId'
		});
	},
	// 编辑Rs
	editRs : function(record) {
		new PlMmOrderChildrenOrForm({
					matchId : record.data.matchId
				}).show();
	},
	// 行的Action
	onRowAction : function(grid, record, action, row, col) {
		switch (action) {
			case 'btn-del' :
				this.removeRs.call(this, record.data.matchId);
				break;
			case 'btn-edit' :
				this.editRs.call(this, record);
				break;
			default :
				break;
		}
	}
});
