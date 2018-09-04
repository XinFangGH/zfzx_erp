Ext.ns('BookBorrowView');
/**
 * 图书借阅列表
 */
var BookBorrowView = function() {
	return new Ext.Panel({
		id : 'BookBorrowView',
		title : '图书借阅列表',
		iconCls : 'menu-book-borrow',
		layout:'border',
		autoScroll : true,
		items : [new Ext.FormPanel({
			id : 'BookBorrowSearchForm',
			region:'north',
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
						text : '借出图书名称'
					}, {
						xtype : 'textfield',
						name : 'Q_bookName_S_LK'
					}, {
						text : '借出图书的ISBN'
					}, {
						xtype : 'textfield',
						name : 'Q_borrowIsbn_S_LK'
					}, {
						xtype : 'button',
						text : '查询',
						iconCls : 'search',
						handler : function() {
							var searchPanel = Ext
									.getCmp('BookBorrowSearchForm');
							var grid = Ext.getCmp('BookBorrowGrid');
							if (searchPanel.getForm().isValid()) {
								searchPanel.getForm().submit({
									waitMsg : '正在提交查询',
									url : __ctxPath
											+ '/admin/listBookBorRet.do',
									success : function(formPanel, action) {
										var result = Ext.util.JSON
												.decode(action.response.responseText);
										grid.getStore().loadData(result);
									}
								});
							}
						}
					}]
		}), this.setup()]
	});
};
/**
 * 建立视图
 */
BookBorrowView.prototype.setup = function() {
	return this.grid();
};
/**
 * 建立DataGrid
 */
BookBorrowView.prototype.grid = function() {
	var sm = new Ext.grid.CheckboxSelectionModel();
	var cm = new Ext.grid.ColumnModel({
		columns : [sm, new Ext.grid.RowNumberer(), {
					header : 'recordId',
					dataIndex : 'recordId',
					hidden : true
				}, {
					header : '登记人',
					dataIndex : 'registerName'
				}, {
					header : '借阅人',
					dataIndex : 'fullname'
				}, {
					header : '借出图书名称',
					dataIndex : 'bookName'
				}, {
					header : '借出图书ISBN',
					dataIndex : 'borrowIsbn'
				}, {
					header : '借出时间',
					dataIndex : 'borrowTime'
				}, {
					header : '应还时间',
					dataIndex : 'returnTime',
					renderer:function(value){
					  return value.substring(0,10);
					}
				}, {
					header : '管理',
					dataIndex : 'recordId',
					sortable : false,
					width : 50,
					renderer : function(value, metadata, record, rowIndex,
							colIndex) {
						var editId = record.data.recordId;
						var str = '';
						if (isGranted('_BookBorrowEdit')) {
							str = '<button title="编辑" value=" " class="btn-edit" onclick="BookBorrowView.edit('
									+ editId + ')"></button>';
						}
						if (isGranted('_BookReturn')) {
							str += '<button title="归还" value=" " class="menu-book-return" onclick="BookBorrowView.returnB('
									+ editId + ')"></button>';
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
				id : 'BookBorrowGrid',
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
							if (isGranted('_BookBorrowEdit')) {
								BookBorrowView.edit(rec.data.recordId);
							}
						});
			});
	return grid;

};

/**
 * 初始化数据
 */
BookBorrowView.prototype.store = function() {
	var store = new Ext.data.Store({
				proxy : new Ext.data.HttpProxy({
							url : __ctxPath + '/admin/listBorrowBookBorRet.do'
						}),
				reader : new Ext.data.JsonReader({
							root : 'result',
							totalProperty : 'totalCounts',
							id : 'id',
							fields : [{
										name : 'recordId',
										type : 'int'
									}

									, {
										name : 'bookSnId',
										mapping : 'bookSn.bookSnId'
									}, 'borrowTime', 'returnTime',
									'lastReturnTime', 'borrowIsbn', 'bookName',
									'registerName', 'fullname']
						}),
				remoteSort : true
			});
	store.setDefaultSort('recordId', 'desc');
	return store;
};

/**
 * 建立操作的Toolbar
 */
BookBorrowView.prototype.topbar = function() {
	var toolbar = new Ext.Toolbar({
				id : 'BookBorrowFootBar',
				height : 30,
				bodyStyle : 'text-align:left',
				items : []
			});
	if (isGranted('_BookBorrowAdd')) {
		toolbar.add(new Ext.Button({
					iconCls : 'btn-add',
					text : '添加借出记录 ',
					handler : function() {
						new BookBorrowForm();
					}
				}));
	}
	return toolbar;
};

/**
 * 
 */
BookBorrowView.edit = function(id) {
	new BookBorrowForm(id);
}

BookBorrowView.returnB = function(id) {
	new BookReturnForm(id);
	Ext.getCmp('ReturnFormButtonSel').hide();
	Ext.getCmp('ReturnFormButtonCle').hide();
}
