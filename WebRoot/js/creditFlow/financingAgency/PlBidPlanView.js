/**
 * @author
 * @class PlBidPlanView
 * @extends Ext.Panel
 * @description [PlBidPlan]管理
 * @company 智维软件
 * @createtime:
 */
PlBidPlanView = Ext.extend(Ext.Panel, {
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		// 初始化组件
		this.initUIComponents();
		// 调用父类构造
		PlBidPlanView.superclass.constructor.call(this, {
					id : 'PlBidPlanView',
					title : '已齐标计划',
					region : 'center',
					layout : 'border',
					items : [this.gridPanel]
				});
	},// end of constructor
	// 初始化组件
	initUIComponents : function() {
		// 初始化搜索条件Panel
		this.searchPanel = new HT.SearchPanel({
					layout : 'form',
					region : 'north',
					colNums : 3,
					items : [{
								fieldLabel : '招标项目名称',
								name : 'Q_bidProName_S_EQ',
								flex : 1,
								xtype : 'textfield'
							}, {
								fieldLabel : '招标项目编号',
								name : 'Q_bidProNumber_S_EQ',
								flex : 1,
								xtype : 'textfield'
							}],
					buttons : [{
								text : '查询',
								scope : this,
								iconCls : 'btn-search',
								handler : this.search
							}, {
								text : '重置',
								scope : this,
								iconCls : 'btn-reset',
								handler : this.reset
							}]
				});// end of searchPanel


		this.gridPanel = new HT.GridPanel({
			region : 'center',
			id : 'PlBidPlanGrid',
			url : __ctxPath + "/creditFlow/financingAgency/listPlBidPlan.do?Q_state_N_EQ=2",
			baseParams:{"Q_state_N_EQ":"2"},
			fields : [{
						name : 'bidId',
						type : 'int'
					},{
						name : 'yearInterestRate',
						mapping : 'bpPersionDirPro.yearInterestRate'
					}, {
						name : 'proName',
						mapping : 'bpPersionDirPro.proName'
					}, {
						name : 'biddingTypeId',
						mapping : 'plBiddingType.name'
					}, 'bidProName', 'bidProNumber', 'biddingTypeId',
					'proType', 'proId', 'bidMoney', 'bidMoneyScale',
					'startMoney', 'riseMoney', 'createtime', 'updatetime',
					'state', 'startInterestType', 'bidStartTime',
					'publishSingeTime', 'bidEndTime', 'bidRemark', 'htmlPath'],
			columns : [{
						header : 'bidId',
						dataIndex : 'bidId',
						hidden : true
					}, {
						header : '招标项目名称',
						dataIndex : 'bidProName'
					}, {
						header : '招标项目编号',
						dataIndex : 'bidProNumber'
					}, {
						header : '借款项目名称',
						dataIndex : 'proName'
					}, {
						header : '招标金额(元)',
						dataIndex : 'bidMoney'
					}, {
						header : '起息日类型',
						dataIndex : 'startInterestType',
						renderer : function(data, metadata, record, rowIndex,
								columnIndex, store) {
							if (data == 0)
								return 'T(投标日+1天)';
							if (data == 1)
								return 'T(招标截止日+1天)';
							if (data == 2)
								return 'T(满标日+1天)';
						}
					}, {
						header : '开始投标时间',
						dataIndex : 'publishSingeTime'
					}, {
						header : '招标截至时间',
						dataIndex : 'bidEndTime'
					}]
				//end of columns
			});

		this.gridPanel.addListener('rowdblclick', this.rowClick);

	},// end of the initComponents()
	//重置查询表单
	reset : function() {
		this.searchPanel.getForm().reset();
	},
	//按条件搜索
	search : function() {
		$search({
					searchPanel : this.searchPanel,
					gridPanel : this.gridPanel
				});
	},
	//GridPanel行点击处理事件
	rowClick : function(grid, rowindex, e) {
		grid.getSelectionModel().each(function(rec) {
					new PlBidPlanForm({
								bidId : rec.data.bidId
							}).show();
				});
	},
	//创建记录
	createRs : function() {
		new PlBidPlanForm().show();
	},
	//按ID删除记录
	removeRs : function(id) {
		$postDel({
					url : __ctxPath
							+ '/creditFlow.financingAgency/multiDelPlBidPlan.do',
					ids : id,
					grid : this.gridPanel
				});
	},
	//把选中ID删除
	removeSelRs : function() {
		$delGridRs({
					url : __ctxPath
							+ '/creditFlow.financingAgency/multiDelPlBidPlan.do',
					grid : this.gridPanel,
					idName : 'bidId'
				});
	},
	//编辑Rs
	editRs : function(record) {
		new PlBidPlanForm({
					bidId : record.data.bidId
				}).show();
	},
	//行的Action
	onRowAction : function(grid, record, action, row, col) {
		switch (action) {
			case 'btn-del' :
				this.removeRs.call(this, record.data.bidId);
				break;
			case 'btn-edit' :
				this.editRs.call(this, record);
				break;
			default :
				break;
		}
	}
});
