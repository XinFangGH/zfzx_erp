/**
 * @author
 * @class PlKeepProtypeView
 * @extends Ext.Panel
 * @description [PlKeepProtype]管理
 * @company 智维软件
 * @createtime:
 */
PlKeepProtypeView = Ext.extend(Ext.Panel, {
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		// 初始化组件
		this.initUIComponents();
		// 调用父类构造
		PlKeepProtypeView.superclass.constructor.call(this, {
					id : 'PlKeepProtypeView',
					title : '借款类型管理',
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
								fieldLabel : '备注',
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
								text : '添加借款类型',
								xtype : 'button',
								scope : this,
								handler : this.createRs
							}, {
								iconCls : 'btn-del',
								text : '删除借款类型',
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
			id : 'PlKeepProtypeGrid',
			url : __ctxPath
					+ "/creditFlow/financingAgency/typeManger/listPlKeepProtype.do",
			fields : [{
						name : 'typeId',
						type : 'int'
					}, 'name', 'keyStr', 'remark'],
			columns : [{
						header : 'typeId',
						dataIndex : 'typeId',
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
					new PlKeepProtypeForm({
								typeId : rec.data.typeId
							}).show();
				});
	},
	// 创建记录
	createRs : function() {
		new PlKeepProtypeForm().show();
	},
	// 按ID删除记录
	removeRs : function(id) {
		$postDel({
			url : __ctxPath
					+ '/creditFlow/financingAgency/typeManger/multiDelPlKeepProtype.do',
			ids : id,
			grid : this.gridPanel
		});
	},
	// 把选中ID删除
	removeSelRs : function() {
		$delGridRs({
			url : __ctxPath
					+ '/creditFlow/financingAgency/typeManger/multiDelPlKeepProtype.do',
			grid : this.gridPanel,
			idName : 'typeId'
		});
	},
	// 编辑Rs
	editRs : function(record) {
		new PlKeepProtypeForm({
					typeId : record.data.typeId
				}).show();
	},
	// 行的Action
	onRowAction : function(grid, record, action, row, col) {
		switch (action) {
			case 'btn-del' :
				this.removeRs.call(this, record.data.typeId);
				break;
			case 'btn-edit' :
				this.editRs.call(this, record);
				break;
			default :
				break;
		}
	}
});
