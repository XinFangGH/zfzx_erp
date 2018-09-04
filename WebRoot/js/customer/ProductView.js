Ext.ns('ProductView');
/**
 * @author:
 * @class ProductView
 * @extends Ext.Panel
 * @description 交往纪录
 * @company 北京互融时代软件有限公司
 * @createtime:2010-04-12
 */
ProductView = Ext.extend(Ext.Panel, {
	// 条件搜索Panel
	searchPanel : null,
	// 数据展示Panel
	gridPanel : null,
	// GridPanel的数据Store
	store : null,
	// 头部工具栏
	topbar : null,
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		// 初始化组件
		this.initUIComponents();
		// 调用父类构造
		ProductView.superclass.constructor.call(this, {
					id : 'ProductView',
					title : '产品列表',
					iconCls : 'menu-product',
					region : 'center',
					layout : 'border',
					items : [this.searchPanel, this.gridPanel]
				});
	},// end of constructor

	// 初始化组件
	initUIComponents : function() {
		// 初始化搜索条件Panel
		this.searchPanel = new Ext.FormPanel({
			id : 'ProductSearchForm',
			height : 35,
			region : 'north',
			frame : false,
			border : false,
			layout : 'hbox',
			layoutConfig : {
				padding : '5',
				align : 'middle'
			},
			defaults : {
				style : 'padding:0px 5px 0px 5px;',
				border : false,
				anchor : '98%,98%',
				labelWidth : 75,
				xtype : 'label'
			},
			items : [{
						text : '请输入查询条件:'
					}, {
						text : '产品名称'
					}, {
						xtype : 'textfield',
						name : 'Q_productName_S_LK'
					}, {
						text : '产品型号'
					}, {
						xtype : 'textfield',
						name : 'Q_productModel_S_LK'
					}, {
						text : '供应商'
					}, {
						xtype : 'textfield',
						name : 'Q_provider.providerName_S_LK'
					}, {
						xtype : 'button',
						text : '查询',
						iconCls : 'search',
						handler : function() {
							var searchPanel = Ext.getCmp('ProductSearchForm');
							var gridPanel = Ext.getCmp('ProductGrid');
							if (searchPanel.getForm().isValid()) {
								$search({
									searchPanel :searchPanel,
									gridPanel : gridPanel
								});
							}

						}
					}]
		});//end of the searchPanel
		this.store = new Ext.data.Store({
			proxy : new Ext.data.HttpProxy({
						url : __ctxPath + '/customer/listProduct.do'
					}),
			reader : new Ext.data.JsonReader({
						root : 'result',
						totalProperty : 'totalCounts',
						id : 'id',
						fields : [{
									name : 'productId',
									type : 'int'
								}
	
								, 'productName', 'productModel', 'unit',
								'costPrice', 'salesPrice', 'provider', 'createtime']
					}),
			remoteSort : true
		});
		this.store.setDefaultSort('productId', 'desc');
		this.store.load({
			params : {
				start : 0,
				limit : 25
			}
		});//end of the store
		
		var sm = new Ext.grid.CheckboxSelectionModel();
	var cm = new Ext.grid.ColumnModel({
		columns : [sm, new Ext.grid.RowNumberer(), {
					header : 'productId',
					dataIndex : 'productId',
					hidden : true
				}, {
					header : '名称',
					dataIndex : 'productName'
				}, {
					header : '型号',
					dataIndex : 'productModel'
				}, {
					header : '单位',
					dataIndex : 'unit'
				}, {
					header : '成本价',
					dataIndex : 'costPrice',
					renderer : function(value) {
						return '<img src="' + __ctxPath
								+ '/images/flag/customer/rmb.png"/>' + value;
					}
				}, {
					header : '出售价',
					dataIndex : 'salesPrice',
					renderer : function(value) {
						return '<img src="' + __ctxPath
								+ '/images/flag/customer/rmb.png"/>' + value;
					}
				}, {
					header : '供应商',
					dataIndex : 'provider',
					renderer : function(value) {
						return value.providerName;
					}
				}, {
					header : '收录时间',
					dataIndex : 'createtime'
				}, {
					header : '管理',
					dataIndex : 'productId',
					width : 50,
					sortable : false,
					renderer : function(value, metadata, record, rowIndex,
							colIndex) {
						var editId = record.data.productId;
						var str = '';
						if (isGranted('_ProductDel')) {
							str = '<button title="删除" value=" " class="btn-del" onclick="ProductView.remove('
									+ editId + ')">&nbsp;&nbsp;</button>';
						}
						if (isGranted('_ProductEdit')) {
							str += '&nbsp;<button title="编辑" value=" " class="btn-edit" onclick="ProductView.edit('
									+ editId + ')">&nbsp;&nbsp;</button>';
						}
						return str;
					}
				}],
		defaults : {
			sortable : true,
			menuDisabled : false,
			width : 100
		}
	});//end of the cm
	
	this.topbar = new Ext.Toolbar({
				id : 'ProductFootBar',
				height : 30,
				bodyStyle : 'text-align:left',
				items : []
			});
	if (isGranted('_ProductAdd')) {
		this.topbar.add(new Ext.Button({
					iconCls : 'btn-add',
					text : '添加产品',
					xtype : 'button',
					handler : function() {
						new ProductForm().show();
					}
				}))
	};
	if (isGranted('_ProductDel')) {
		this.topbar.add(new Ext.Button({
					iconCls : 'btn-del',
					text : '删除产品',
					xtype : 'button',
					handler : function() {

						var grid = Ext.getCmp("ProductGrid");

						var selectRecords = grid.getSelectionModel()
								.getSelections();

						if (selectRecords.length == 0) {
							Ext.ux.Toast.msg("信息", "请选择要删除的记录！");
							return;
						}
						var ids = Array();
						for (var i = 0; i < selectRecords.length; i++) {
							ids.push(selectRecords[i].data.productId);
						}

						ProductView.remove(ids);
					}
				}))
	};//end of the topbar
	
	this.gridPanel = new Ext.grid.GridPanel({
				id : 'ProductGrid',
				tbar : this.topbar,
				region : 'center',
				store : this.store,
				trackMouseOver : true,
				disableSelection : false,
				loadMask : true,
				cm : cm,
				sm : sm,
				viewConfig : {
					forceFit : true,
					enableRowBody : false,
					showPreview : false
				},
				bbar : new HT.PagingBar({store : this.store})
			});//end of the gridPaenl

	this.gridPanel.addListener('rowdblclick', function(grid, rowindex, e) {
				grid.getSelectionModel().each(function(rec) {
							if (isGranted('_ProductEdit')) {
									ProductView.edit(rec.data.productId);
							}
						});
			});
	}//end of the initUIComponents
});
/**
 * 删除单个记录
 */
ProductView.remove = function(id) {
	var grid = Ext.getCmp("ProductGrid");
	Ext.Msg.confirm('信息确认', '您确认要删除该记录吗？', function(btn) {
				if (btn == 'yes') {
					Ext.Ajax.request({
								url : __ctxPath
										+ '/customer/multiDelProduct.do',
								params : {
									ids : id
								},
								method : 'post',
								success : function() {
									Ext.ux.Toast.msg("信息提示", "成功删除所选记录！");
									grid.getStore().reload({
												params : {
													start : 0,
													limit : 25
												}
											});
								}
							});
				}
			});
};

/**
 * 
 */
ProductView.edit = function(id) {
	new ProductForm({
		productId : id
	}).show();
}
