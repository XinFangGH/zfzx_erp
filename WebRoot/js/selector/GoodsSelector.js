/**
 * 商品选择器
 */
var GoodsSelector = {
	/**
	 * @param callback
	 *            回调函数
	 * @param isSingle
	 *            是否单选
	 */
	getView : function(callback, isSingle) {
		var treeGoods = new Ext.tree.TreePanel({
					title : '商品显示',
					region : 'west',
					width : 180,
					height : 300,
					split : true,
					collapsible : true,
					autoScroll : true,
					bbar : new Ext.Toolbar({
								items : [{
											xtype : 'button',
											iconCls : 'btn-refresh',
											text : '刷新',
											handler : function() {
												treeGoods.root.reload();
											}
										}, {
											xtype : 'button',
											text : '展开',
											iconCls : 'btn-expand',
											handler : function() {
												treeGoods.expandAll();
											}
										}, {
											xtype : 'button',
											text : '收起',
											iconCls : 'btn-collapse1',
											handler : function() {
												treeGoods.collapseAll();
											}
										}]
							}),
					loader : new Ext.tree.TreeLoader({
								url : __ctxPath
										+ '/admin/treeOfficeGoodsType.do'
							}),
					root : new Ext.tree.AsyncTreeNode({
								expanded : true
							}),
					rootVisible : false,
					listeners : {
						'click' : function(node) {
							if (node != null) {
								var goodss = Ext.getCmp('GoodsSelectorGrid');
								var store = goodss.getStore();
								store.proxy.conn.url = __ctxPath
										+ '/admin/listOfficeGoods.do';
								store.baseParams = {
									'Q_officeGoodsType.typeId_L_EQ' : node.id == 0
											? null
											: node.id
								};
								store.load({
											params : {
												start : 0,
												limit : 12
											}
										});
							}
						}
					}
				});
		// ---------------------------------start grid
		// panel--------------------------------
		var sm = null;
		if (isSingle) {
			var sm = new Ext.grid.CheckboxSelectionModel({
						singleSelect : true
					});
		} else {
			sm = new Ext.grid.CheckboxSelectionModel();
		}
		var cm = new Ext.grid.ColumnModel({
			columns : [sm, new Ext.grid.RowNumberer(), {
						header : 'typeId',
						dataIndex : 'typeId',
						hidden : true
					}, {
						header : "商品名称",
						dataIndex : 'goodsName',
						width : 60
					}, {
						header : '库存数',
						dataIndex : 'stockCounts',
						width : 60,
						renderer : function(value, metadata, record, rowIndex,
								colIndex) {
							var warnCounts = record.data.warnCounts;
							var isWarning = record.data.isWarning;
							if (value <= warnCounts && isWarning == '1') {
								return '<a style="color:red;" title="已少于警报库存！">'
										+ value + '</a>';
							} else {
								return value;
							}
						}

					}]
		});

		var store = new Ext.data.Store({
					proxy : new Ext.data.HttpProxy({
								url : __ctxPath + '/admin/listOfficeGoods.do'
							}),
					reader : new Ext.data.JsonReader({
								root : 'result',
								totalProperty : 'totalCounts',
								id : 'goodsId',
								fields : [{
											name : 'goodsId',
											type : 'int'
										}, 'goodsName', {
											name : 'stockCounts',
											type : 'int'
										}, {
											name : 'warnCounts',
											type : 'int'
										},'isWarning']
							}),
					remoteSort : true
				});

		var gridPanel = new Ext.grid.GridPanel({
					id : 'GoodsSelectorGrid',
					width : 400,
					height : 300,
					region : 'center',
					title : '办公用品列表',
					store : store,
					shim : true,
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
					// paging bar on the bottom
					bbar : new HT.PagingBar({store : store})
				});

		store.load({
					params : {
						start : 0,
						limit : 25
					}
				});
		// --------------------------------end grid
		// panel-------------------------------------

		var formPanel = new Ext.FormPanel({
			width : 400,
			region : 'north',
			id : 'GoodsForm',
			height : 40,
			frame : false,
			border : false,
			layout : 'hbox',
			layoutConfig : {
				padding : '5',
				align : 'middle'
			},
			defaults : {
				xtype : 'label',
				margins : {
					top : 0,
					right : 4,
					bottom : 4,
					left : 4
				}
			},
			items : [{
						text : '请输入查询条件:'
					}, {
						text : '商品名称'
					}, {
						xtype : 'textfield',
						name : 'Q_goodsName_S_LK'
					}, {
						xtype : 'button',
						text : '查询',
						iconCls : 'search',
						handler : function() {
							var searchPanel = Ext.getCmp('GoodsForm');
							var grid = Ext.getCmp('GoodsSelectorGrid');
							if (searchPanel.getForm().isValid()) {
								searchPanel.getForm().submit({
									waitMsg : '正在提交查询',
									url : __ctxPath
											+ '/admin/listOfficeGoods.do',
									success : function(formPanel, action) {
										var result = Ext.util.JSON
												.decode(action.response.responseText);
										grid.getStore().loadData(result);
									}
								});
							}

						}
					}]
		});

		var window = new Ext.Window({
			title : '办公用品选择',
			iconCls:'menu-goods',
			width : 630,
			height : 380,
			layout : 'border',
			border : false,
			items : [treeGoods, formPanel, gridPanel],
			modal : true,
			buttonAlign : 'center',
			buttons : [{
						iconCls : 'btn-ok',
						text : '确定',
						handler : function() {
							var grid = Ext.getCmp('GoodsSelectorGrid');
							var rows = grid.getSelectionModel().getSelections();
							var goodsIds = '';
							var goodsNames = '';
							for (var i = 0; i < rows.length; i++) {

								if (i > 0) {
									goodsIds += ',';
									goodsNames += ',';
								}

								goodsIds += rows[i].data.goodsId;
								goodsNames += rows[i].data.goodsName;

							}

							if (callback != null) {
								callback.call(this, goodsIds, goodsNames);
							}
							window.close();
						}
					}, {
						text : '取消',
						iconCls : 'btn-cancel',
						handler : function() {
							window.close();
						}
					}]
		});
		return window;
	}

};