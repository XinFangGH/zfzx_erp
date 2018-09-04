/**
 * @author
 * @class PlBiddingTypeView
 * @extends Ext.Panel
 * @description [PlBiddingType]管理
 * @company 智维软件
 * @createtime:
 */
PlBiddingTypeView = Ext.extend(Ext.Panel, {
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		// 初始化组件
		this.initUIComponents();
		// 调用父类构造
		PlBiddingTypeView.superclass.constructor.call(this, {
					id : 'PlBiddingTypeView',
					title : '招标类型管理',
					iconCls:"menu-finance",
					region : 'center',
					layout : 'border',
					items : [this.searchPanel, this.gridPanel]
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
								fieldLabel : '名称',
								name : 'Q_name_S_EQ',
								flex : 1,
								xtype : 'textfield'
							}, {
								fieldLabel : 'keyStr',
								name : 'Q_keyStr_S_EQ',
								flex : 1,
								xtype : 'textfield'
							}, {
								fieldLabel : 'remark',
								name : 'Q_remark_S_EQ',
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

		this.topbar = new Ext.Toolbar({
					items : [{
								iconCls : 'btn-add',
								text : '添加招标类型',
								xtype : 'button',
								scope : this,
								handler : this.createRs
							}, {
								iconCls : 'btn-del',
								text : '删除招标类型',
								xtype : 'button',
								scope : this,
								handler : this.removeSelRs
							}]
				});

		this.gridPanel = new HT.GridPanel({
			region : 'center',
			tbar : this.topbar,
			// 使用RowActions
			rowActions : true,
			id : 'PlBiddingTypeGrid',
			url : __ctxPath
					+ "/creditFlow/financingAgency/typeManger/listPlBiddingType.do",
			fields : [{
						name : 'biddingTypeId',
						type : 'int'
					}, 'name', 'keyStr', 'remark'],
			columns : [{
						header : 'biddingTypeId',
						dataIndex : 'biddingTypeId',
						hidden : true
					}, {
						header : '名称',
						align:'center',
						dataIndex : 'name'
					}, {
						header : 'keyStr',
						align:'center',
						dataIndex : 'keyStr'
					}, {
						header : '备注',
						align:'left',
						dataIndex : 'remark'
					}, new Ext.ux.grid.RowActions({
								header : '管理',
								width : 100,
								align:'center',
								actions : [{
											iconCls : 'btn-del',
											qtip : '删除',
											style : 'margin:0 3px 0 3px'
										}, {
											iconCls : 'btn-edit',
											qtip : '编辑',
											style : 'margin:0 3px 0 3px'
										}],
								listeners : {
									scope : this,
									'action' : this.onRowAction
								}
							})]
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
	},
	// GridPanel行点击处理事件
	rowClick : function(grid, rowindex, e) {
		grid.getSelectionModel().each(function(rec) {
					new PlBiddingTypeForm({
								biddingTypeId : rec.data.biddingTypeId
							}).show();
				});
	},
	// 创建记录
	createRs : function() {
		new PlBiddingTypeForm().show();
	},
	// 按ID删除记录
	removeRs : function(id) {
		$postDel({
			url : __ctxPath
					+ '/creditFlow/financingAgency/typeManger/multiDelPlBiddingType.do',
			ids : id,
			grid : this.gridPanel
		});
	},
	// 把选中ID删除
	removeSelRs : function() {
		$delGridRs({
			url : __ctxPath
					+ '/creditFlow/financingAgency/typeManger/multiDelPlBiddingType.do',
			grid : this.gridPanel,
			idName : 'biddingTypeId'
		});
	},
	// 编辑Rs
	editRs : function(record) {
		new PlBiddingTypeForm({
					biddingTypeId : record.data.biddingTypeId
				}).show();
	},
	// 行的Action
	onRowAction : function(grid, record, action, row, col) {
		switch (action) {
			case 'btn-del' :
				this.removeRs.call(this, record.data.biddingTypeId);
				break;
			case 'btn-edit' :
				this.editRs.call(this, record);
				break;
			default :
				break;
		}
	}
});
