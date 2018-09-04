/**
 * 图书选择器
 */
var BookSelector = {
	/**
	 * @param callback
	 *            回调函数
	 * @param isSingle
	 *            是否单选
	 */
	getView : function(callback, isSingle) {
		var treeBook = new Ext.tree.TreePanel({
			title : '图书显示',
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
										treeBook.root.reload();
									}
								}, {
									xtype : 'button',
									text : '展开',
									iconCls : 'btn-expand',
									handler : function() {
										treeBook.expandAll();
									}
								}, {
									xtype : 'button',
									text : '收起',
									iconCls : 'btn-collapse1',
									handler : function() {
										treeBook.collapseAll();
									}
								}]
					}),
			loader : new Ext.tree.TreeLoader({
						url : __ctxPath + '/admin/treeBookType.do'
					}),
			root : new Ext.tree.AsyncTreeNode({
						expanded : true
					}),
			rootVisible : false,
			listeners : {
				'click' : function(node) {
					if (node != null) {
						var books = Ext.getCmp('BookSelectorGrid');
						var store = books.getStore();
						store.proxy.conn.url = __ctxPath + '/admin/listBook.do';
						store.baseParams = {
							'Q_bookType.typeId_L_EQ' : node.id == 0
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
								header : "图书名称",
								dataIndex : 'bookName',
								width : 60
							}]
				});

		var store = new Ext.data.Store({
					proxy : new Ext.data.HttpProxy({
								url : __ctxPath + '/admin/listBook.do'
							}),
					reader : new Ext.data.JsonReader({
								root : 'result',
								totalProperty : 'totalCounts',
								id : 'bookId',
								fields : [{
											name : 'bookId',
											type : 'int'
										}, 'bookName']
							}),
					remoteSort : true
				});

		var gridPanel = new Ext.grid.GridPanel({
					id : 'BookSelectorGrid',
					width : 400,
					height : 300,
					region : 'center',
					title : '图书列表',
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
			id : 'BookForm',
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
						text : '图书名称'
					}, {
						xtype : 'textfield',
						name : 'Q_bookName_S_LK'
					}, {
						xtype : 'button',
						text : '查询',
						iconCls : 'search',
						handler : function() {
							var searchPanel = Ext.getCmp('BookForm');
							var grid = Ext.getCmp('BookSelectorGrid');
							if (searchPanel.getForm().isValid()) {
								searchPanel.getForm().submit({
									waitMsg : '正在提交查询',
									url : __ctxPath + '/admin/listBook.do',
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
			title : '选择图书',
			iconCls : 'menu-book-manage',
			width : 630,
			height : 380,
			layout : 'border',
			border : false,
			items : [treeBook, formPanel, gridPanel],
			modal : true,
			buttonAlign : 'center',
			buttons : [{
						iconCls : 'btn-ok',
						text : '确定',
						handler : function() {
							var grid = Ext.getCmp('BookSelectorGrid');
							var rows = grid.getSelectionModel().getSelections();
							var bookIds = '';
							var bookNames = '';
							for (var i = 0; i < rows.length; i++) {
								if (i > 0) {
									bookIds += ',';
									bookNames += ',';
								}
								bookIds += rows[i].data.bookId;
								bookNames += rows[i].data.bookName;
							}
							if (callback != null) {
								callback.call(this, bookIds, bookNames);
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