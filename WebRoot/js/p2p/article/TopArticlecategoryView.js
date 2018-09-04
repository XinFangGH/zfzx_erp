/**
 * @author
 * @class TopArticlecategoryView
 * @extends Ext.Panel
 * @description [Articlecategory]管理
 * @company 智维软件
 * @createtime:
 */
TopArticlecategoryView = Ext.extend(Ext.Panel, {
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		// 初始化组件
		this.initUIComponents();
		// 调用父类构造
		TopArticlecategoryView.superclass.constructor.call(this, {
					id : 'TopArticlecategoryView',
					title : '顶级分类管理',
					region : 'center',
					iconCls:"menu-finance",
					layout : 'border',
					items : [/*this.searchPanel,*/ this.gridPanel]
				});
	},// end of constructor
	// 初始化组件
	initUIComponents : function() {
		// 初始化搜索条件Panel
		this.searchPanel = new HT.SearchPanel({
					layout : 'form',
					region : 'north',
					colNums : 3,
					items : [ {
								fieldLabel : '类别名称',
								name : 'Q_name_S_EQ',
								flex : 1,
								xtype : 'textfield'
							},{
								fieldLabel : '创建时间',
								name : 'Q_createDate_D_EQ',
								flex : 1,
								xtype : 'datefield',
								format : 'Y-m-d'
							}, {
								fieldLabel : '更新时间',
								name : 'Q_modifyDate_D_EQ',
								flex : 1,
								xtype : 'datefield',
								format : 'Y-m-d'
							}, {
								fieldLabel : '页面描述',
								name : 'Q_metaDescription_S_EQ',
								flex : 1,
								xtype : 'textfield'
							}, {
								fieldLabel : '页面关键字',
								name : 'Q_metaKeywords_S_EQ',
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
								text : '添加',
								xtype : 'button',
								scope : this,
								handler : this.createRs
							}, {
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
			rowActions : true,
			id : 'ArticlecategoryGrid'+this.type,
			url : __ctxPath + "/p2p/listArticlecategory.do?Q_parentId_L_EQ=0",
			fields : [{
						name : 'id',
						type : 'int'
					}, 'createDate', 'modifyDate', 'metaDescription',
					'metaKeywords', 'name', 'orderList', 'path', 'parentId','webKey','cateKey'],
			columns : [{
						header : 'id',
						dataIndex : 'id',
						hidden : true
					}, {
						header : '站点类别',
						align:'center',
						dataIndex : 'webKey',
						renderer:function(value){
							if(value=="YunGou"){
								return "云购";
							}else if(value=="P2P"){
								return "互联网金融";
							}else if(value=="Crowdfunding"){
								return "众筹";
							}else{
								return value;
							}
						}
					}, {
						header : '分类名称',
						align:'center',
						dataIndex : 'name'
					}, {
						header : '分类key',
						align:'center',
						dataIndex : 'cateKey'
					}, {
						header : '创建时间',
						align:'center',
						dataIndex : 'createDate'
					}, {
						header : '更新时间',
						align:'center',
						dataIndex : 'modifyDate'
					}, {
						header : '关键字',
						align:'center',
						dataIndex : 'metaKeywords'
					},  new Ext.ux.grid.RowActions({
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
					new TopArticlecategoryForm({
								id : rec.data.id,
								isEdit:true
							}).show();
				});
	},
	// 创建记录
	createRs : function() {
		new TopArticlecategoryForm().show();
	},
	
	// 按ID删除记录
	removeRs : function(id) {
		$postDel({
					url : __ctxPath + '/p2p/multiDelArticlecategory.do',
					ids : id,
					grid : this.gridPanel
				});
	},
	// 把选中ID删除
	removeSelRs : function() {
		$delGridRs({
					url : __ctxPath + '/p2p/multiDelArticlecategory.do',
					grid : this.gridPanel,
					idName : 'id'
				});
	},
	
	// 编辑Rs
	editRs : function(record) {
		new TopArticlecategoryForm({
					id : record.data.id,
					isEdit:true
				}).show();
	},
	// 行的Action
	onRowAction : function(grid, record, action, row, col) {
		switch (action) {
			case 'btn-del' :
				this.removeRs.call(this, record.data.id);
				break;
			case 'btn-edit' :
				this.editRs.call(this, record);
				break;
			default :
				break;
		}
	}
});
