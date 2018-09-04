Ext.ns('BookReturnView');
/**
 * 图书归还列表
 */
var BookReturnView = function() {
	return new Ext.Panel({
		id : 'BookReturnView',
		title : '图书归还列表',
		iconCls : 'menu-book-return',
		autoScroll : true,
		layout:'border',
		items : [new Ext.FormPanel({
			region:'north',
			id : 'BookReturnSearchForm',
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
									.getCmp('BookReturnSearchForm');
							var grid = Ext.getCmp('BookReturnGrid');
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
BookReturnView.prototype.setup = function() {
	return this.grid();
};
/**
 * 建立DataGrid
 */
BookReturnView.prototype.grid = function() {
	var sm = new Ext.grid.CheckboxSelectionModel();
	var cm = new Ext.grid.ColumnModel({
		columns : [sm, new Ext.grid.RowNumberer(), {
					header : 'recordId',
					dataIndex : 'recordId',
					hidden : true
				},{
					header : '借出图书名称',
					dataIndex : 'bookName'
				}, {
					header : '借出图书ISBN',
					dataIndex : 'borrowIsbn'
				},{
				    header:'登记人',
				    dataIndex:'registerName'
				},{
				    header:'借阅人',
				    dataIndex:'fullname'
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
					header : '归还时间',
					dataIndex : 'lastReturnTime'
				}, {
					header : '管理',
					dataIndex : 'recordId',
					sortable : false,
					width : 50,
					renderer : function(value, metadata, record, rowIndex,
							colIndex) {
						var editId = record.data.recordId;
						var str = '';
						if (isGranted('_BookReturnEdit')) {
							str = '<button title="编辑" value=" " class="btn-edit" onclick="BookReturnView.edit('
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
				id : 'BookReturnGrid',
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
							if (isGranted('_BookReturnEdit')) {
								BookReturnView.edit(rec.data.recordId);
							}
						});
			});
	return grid;

};

/**
 * 初始化数据
 */
BookReturnView.prototype.store = function() {
	var store = new Ext.data.Store({
				proxy : new Ext.data.HttpProxy({
							url : __ctxPath + '/admin/listReturnBookBorRet.do'
						}),
				reader : new Ext.data.JsonReader({
							root : 'result',
							totalProperty : 'totalCounts',
							id : 'id',
							fields : [{
										name : 'recordId',
										type : 'int'
									}, {
										name : 'bookSnId',
										mapping : 'bookSn.bookSnId'
									}, 'borrowTime', 'returnTime',
									'lastReturnTime', 'borrowIsbn', 'bookName','registerName','fullname']
						}),
				remoteSort : true
			});
	store.setDefaultSort('recordId', 'desc');
	return store;
};

/**
 * 建立操作的Toolbar
 */
BookReturnView.prototype.topbar = function() {
	var toolbar = new Ext.Toolbar({
				id : 'BookReturnFootBar',
				height : 30,
				bodyStyle : 'text-align:left',
				items : []
			});
	if (isGranted('_BookReturnAdd')) {
		toolbar.add(new Ext.Button({
					iconCls : 'btn-add',
					text : '添加归还记录',
					handler : function() {
						new BookReturnForm();
					}
				}));
	}
	return toolbar;
};

/**
 * 
 */
BookReturnView.edit = function(id) {
	new BookReturnForm(id);
}
