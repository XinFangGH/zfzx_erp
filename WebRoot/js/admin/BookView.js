Ext.ns('BookView');
/**
 * 图书列表
 */
var BookView = function() {

}
BookView.prototype.setTypeId = function(typeId) {
	this.typeId = typeId;
	BookView.typeId = typeId;
};
BookView.prototype.getTypeId = function() {
	return this.typeId;
}
/**
 * 显示列表
 * 
 * @return {}
 */
BookView.prototype.getView = function() {
	return new Ext.Panel({
		id : 'BookView',
		title : '图书列表',
		region : 'center',
		layout:'border',
		autoScroll : true,
		items : [new Ext.FormPanel({
			region:'north',
			id : 'BookSearchForm',
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
						text : '书名'
					}, {
						xtype : 'textfield',
						name : 'Q_bookName_S_LK'
					}, {
						text : '作者'
					}, {
						xtype : 'textfield',
						name : 'Q_author_S_LK'
					}, {
						xtype : 'button',
						text : '查询',
						iconCls : 'search',
						handler : function() {
							var searchPanel = Ext.getCmp('BookSearchForm');
							var gridPanel = Ext.getCmp('BookGrid');
							if (searchPanel.getForm().isValid()) {
								$search({
									searchPanel :searchPanel,
									gridPanel : gridPanel
								});
							}

						}
					}]
		}), this.setup()]
	}

	);
};
/**
 * 建立视图
 */
BookView.prototype.setup = function() {
	return this.grid();
};
/**
 * 建立DataGrid
 */
BookView.prototype.grid = function() {
	var sm = new Ext.grid.CheckboxSelectionModel();
	var cm = new Ext.grid.ColumnModel({
		columns : [sm, new Ext.grid.RowNumberer(), {
					header : 'bookId',
					dataIndex : 'bookId',
					hidden : true
				}, {
					header : '类别',
					dataIndex : 'typeName'
				}, {
					header : '书名',
					dataIndex : 'bookName'
				}, {
					header : '作者',
					dataIndex : 'author'
				}, {
					header : 'ISBN号',
					dataIndex : 'isbn'
				},
				{
					header : '存放地点',
					dataIndex : 'location'
				}, {
					header : '所属部门',
					dataIndex : 'department'
				}, {
					header : '数量',
					dataIndex : 'amount',
					width : 50
				}, {
					header : '在库数',
					dataIndex : 'leftAmount',
					width : 60
				}, {
					header : '管理',
					dataIndex : 'bookId',
					sortable : false,
					width : 100,
					renderer : function(value, metadata, record, rowIndex,
							colIndex) {
						var editId = record.data.bookId;
						var leftAmount = record.data.leftAmount;
						var str = '';
						if (isGranted('_BookDel')) {
							str = '<button title="删除" value=" " class="btn-del" onclick="BookView.remove('
									+ editId + ')"></button>';
						}
						if (isGranted('_BookEdit')) {
							str += '&nbsp;<button title="编辑" value=" " class="btn-edit" onclick="BookView.edit('
									+ editId + ')"></button>';
						}
						if (isGranted('_BookBorrowAdd')) {
							// 要是图书剩余数量为0则不显示借阅图标
							if (leftAmount != 0) {
								str += '&nbsp;<button title="借阅该书" value=" " class="menu-book-borrow" onclick="BookView.borrow('
										+ editId + ')"></button>';
							}
						}
						return str;
					}
				}],
		defaults : {
			sortable : true,
			menuDisabled : false,
			width : 100
		}
	});

	var store = this.store();
	store.load({
				params : {
					start : 0,
					limit : 25
				}
			});
	var grid = new Ext.grid.GridPanel({
				id : 'BookGrid',
				tbar : this.topbar(),
				store : store,
				trackMouseOver : true,
				disableSelection : false,
				loadMask : true,
				region:'center',
				cm : cm,
				sm : sm,
				viewConfig : {
					forceFit : true,
					enableRowBody : false,
					showPreview : false
				},
				bbar : new HT.PagingBar({store : store})
			});

	grid.addListener('rowdblclick', function(grid, rowindex, e) {
				grid.getSelectionModel().each(function(rec) {
							if (isGranted('_BookEdit')) {
								BookView.edit(rec.data.bookId);
							}
						});
			});
	return grid;

};

/**
 * 初始化数据
 */
BookView.prototype.store = function() {
	var store = new Ext.data.Store({
				proxy : new Ext.data.HttpProxy({
							url : __ctxPath + '/admin/listBook.do'
						}),
				reader : new Ext.data.JsonReader({
							root : 'result',
							totalProperty : 'totalCounts',
							id : 'id',
							fields : [{
										name : 'bookId',
										type : 'int'
									}, {
										name : 'typeName',
										mapping : 'bookType.typeName'
									}, 'bookName', 'author', 'isbn',
									'publisher', 'price', 'location',
									'department', 'amount', 'leftAmount']
						}),
				remoteSort : true
			});
	store.setDefaultSort('bookId', 'desc');
	return store;
};

/**
 * 建立操作的Toolbar
 */
BookView.prototype.topbar = function() {
	var toolbar = new Ext.Toolbar({
				id : 'BookFootBar',
				height : 30,
				bodyStyle : 'text-align:left',
				items : []
			});
	if (isGranted('_BookAdd')) {
		toolbar.add(new Ext.Button({
					iconCls : 'btn-add',
					text : '添加图书',
					handler : function() {
						new BookForm();
						Ext.getCmp('BookForm').remove('bookSnContainer');
						Ext.getCmp('amoutContainer').remove('bookAmoutButton');

					}
				}));
	}
	if (isGranted('_BookDel')) {
		toolbar.add(new Ext.Button({
					iconCls : 'btn-del',
					text : '删除图书',
					handler : function() {

						var grid = Ext.getCmp("BookGrid");

						var selectRecords = grid.getSelectionModel()
								.getSelections();

						if (selectRecords.length == 0) {
							Ext.ux.Toast.msg("信息", "请选择要删除的记录！");
							return;
						}
						var ids = Array();
						for (var i = 0; i < selectRecords.length; i++) {
							ids.push(selectRecords[i].data.bookId);
						}

						BookView.remove(ids);
					}
				}));
	}
	return toolbar;
};

/**
 * 删除单个记录
 */
BookView.remove = function(id) {
	var grid = Ext.getCmp("BookGrid");
	Ext.Msg.confirm('信息确认', '会把图书的借阅归还记录和ISBN一起删除，您确认要删除该记录吗？', function(btn) {
				if (btn == 'yes') {
					Ext.Ajax.request({
								url : __ctxPath + '/admin/multiDelBook.do',
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
BookView.edit = function(id) {
	new BookForm(id);
}
BookView.borrow = function(id) {
	// 选择借阅该书的时候根据bookId把书名自动赋上，并且把bookSn也重新加载出来
	new BookBorrowForm(null, id);
	Ext.Ajax.request({
				url : __ctxPath + '/admin/getBook.do?bookId=' + id,
				method : 'post',
				success : function(response) {
					var result = Ext.util.JSON.decode(response.responseText);
					var bookName = Ext.getCmp('bookName');
					bookName.setValue(result.data.bookName);
					var store = Ext.getCmp('borrowIsbn').getStore();
					store.reload({
								params : {
									bookId : id
								}
							});
				}
			});
}
