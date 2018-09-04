/**
 * @author
 * @class PlManageMoneyTypeView
 * @extends Ext.Panel
 * @description [PlManageMoneyType]管理
 * @company 智维软件
 * @createtime:
 */
PlManageMoneyProduceTypeView = Ext.extend(Ext.Panel, {
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		// 初始化组件
		this.initUIComponents();
		// 调用父类构造
		PlManageMoneyProduceTypeView.superclass.constructor.call(this, {
					id : 'PlManageMoneyProduceTypeView',
					title : '理财产品类别管理',
					region : 'center',
					layout : 'border',
					iconCls:"btn-tree-team43",
					items : [this.searchPanel, this.gridPanel]
				});
	},// end of constructor
	// 初始化组件
	
	initUIComponents : function() {
		// 初始化搜索条件Panel
		this.searchPanel = new HT.SearchPanel({
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
							columnWidth : .3,
							labelAlign : 'right',
							xtype : 'container',
							layout : 'form',
							labelWidth : 120,
							defaults : {
								anchor : '100%'
							},
							items : [ {
										fieldLabel : '理财产品类型名称',
										name : 'Q_name_S_LK',
										xtype : 'textfield'
										

									}]
						}				, {
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
										}]},	
											{columnWidth : .07,
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
								iconCls : 'btn-add',
								text : '添加',
								xtype : 'button',
								scope : this,
								handler : this.createRs
							}, new Ext.Toolbar.Separator({
							}), {
								iconCls : 'btn-del',
								text : '删除',
								xtype : 'button',
								scope : this,
								handler : this.removeSelRs
							}]
				});

		this.gridPanel = new HT.GridPanel({
			region : 'center',
			tbar : this.topbar,
			// 使用RowActions
			id : 'PlManageMoneyProduceTypegrid',
			url : __ctxPath
					+ "/creditFlow/financingAgency/listPlManageMoneyType.do?Q_keystr_S_EQ=mmproduce&Q_state_N_EQ=0",
			fields : [{
						name : 'manageMoneyTypeId',
						type : 'int'
					}, 'name', 'keystr', 'remark'],
			columns : [{
						header : 'manageMoneyTypeId',
						dataIndex : 'manageMoneyTypeId',
						hidden : true
					}, {
						header : '理财产品类型名称',
						width:130,
						dataIndex : 'name'
					}, {
						header : '说明',
						dataIndex : 'remark'
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
	// GridPanel行点击处理事件
	rowClick : function(grid, rowindex, e) {
		grid.getSelectionModel().each(function(rec) {
					new PlManageMoneyTypeForm({
								manageMoneyTypeId : rec.data.manageMoneyTypeId
							}).show();
				});
	},
	// 创建记录
	createRs : function() {
		new PlManageMoneyProduceTypeForm().show();
	},
	// 按ID删除记录
	removeRs : function(id) {
		$postDel({
			url : __ctxPath
					+ '/creditFlow/financingAgency/multiDelPlManageMoneyType.do',
			ids : id,
			grid : this.gridPanel
		});
	},
	// 把选中ID删除
	removeSelRs : function() {
		$delGridRs({
			url : __ctxPath
					+ '/creditFlow/financingAgency/multiDelPlManageMoneyType.do',
			grid : this.gridPanel,
			idName : 'manageMoneyTypeId'
		});
	},
	// 编辑Rs
	editRs : function(record) {
		new PlManageMoneyTypeForm({
					manageMoneyTypeId : record.data.manageMoneyTypeId
				}).show();
	},
	// 行的Action
	onRowAction : function(grid, record, action, row, col) {
		switch (action) {
			case 'btn-del' :
				this.removeRs.call(this, record.data.manageMoneyTypeId);
				break;
			case 'btn-edit' :
				this.editRs.call(this, record);
				break;
			default :
				break;
		}
	}
});
