Ext.ns('BookSnView');
/**
 * TODO: add class/table comments列表
 */
var BookSnView = function() {
	return new Ext.Panel({
		id : 'BookSnView',
		title : '图书SN列表',
		layout:'border',
		autoScroll : true,
		items : [new Ext.FormPanel({
			region:'north',
			id : 'BookSnSearchForm',
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
						text : '图书ID'
					}, {
						xtype : 'textfield',
						name : 'Q_bookId_S_LK'
					}, {
						text : '图书SN号'
					}, {
						xtype : 'textfield',
						name : 'Q_bookSN_S_LK'
					}, {
						text : '借阅状态'
					}, {
						xtype : 'textfield',
						name : 'Q_status_S_LK'
					}, {
						xtype : 'button',
						text : '查询',
						iconCls : 'search',
						handler : function() {
							var searchPanel = Ext.getCmp('BookSnSearchForm');
							var gridPanel = Ext.getCmp('BookSnGrid');
							if (searchPanel.getForm().isValid()) {
								$search({
									searchPanel :searchPanel,
									gridPanel : gridPanel
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
BookSnView.prototype.setup = function() {
	return this.grid();
};
/**
 * 建立DataGrid
 */
BookSnView.prototype.grid = function() {
	var sm = new Ext.grid.CheckboxSelectionModel();
	var cm = new Ext.grid.ColumnModel({
		columns : [sm, new Ext.grid.RowNumberer(), {
					header : 'bookSnId',
					dataIndex : 'bookSnId',
					hidden : true
				}, {
					header : '书名',
					dataIndex : 'bookName'
				}, {
					header : '图书SN号',
					dataIndex : 'bookSN'
				}, {
					header : '借阅状态',
					dataIndex : 'status',
					// 格式化输出借阅状态
					renderer : function(value) {
						if (value == '0') {
							return "未借出";
						} else if (value == '1') {
							return "已借出";
						} else if (value == '2') {
							return "预订";
						} else {
							return "注销";
						}

					}
				}, {
					header : '管理',
					dataIndex : 'bookSnId',
					width : 50,
					renderer : function(value, metadata, record, rowIndex,
							colIndex) {
						var editId = record.data.bookSnId;
						var str = '<input type="button" title="删除" value=" " class="btn-del" onclick="BookSnView.remove('
								+ editId + ')"/>';
						str += '&nbsp;<input type="button" title="编辑" value=" " class="btn-edit" onclick="BookSnView.edit('
								+ editId + ')"/>';
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
				id : 'BookSnGrid',
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
							BookSnView.edit(rec.data.bookSnId);
						});
			});
	return grid;

};

/**
 * 初始化数据
 */
BookSnView.prototype.store = function() {
	var store = new Ext.data.Store({
				proxy : new Ext.data.HttpProxy({
							url : __ctxPath + '/admin/listBookSn.do'
						}),
				reader : new Ext.data.JsonReader({
							root : 'result',
							totalProperty : 'totalCounts',
							id : 'id',
							fields : [{
										name : 'bookSnId',
										type : 'int'
									}

									, {
										name : 'bookName',
										mapping : 'book.bookName'
									}, 'bookSN', 'status']
						}),
				remoteSort : true
			});
	store.setDefaultSort('bookSnId', 'desc');
	return store;
};

/**
 * 建立操作的Toolbar
 */
BookSnView.prototype.topbar = function() {
	var toolbar = new Ext.Toolbar({
				id : 'BookSnFootBar',
				height : 30,
				bodyStyle : 'text-align:left',
				items : [{
							iconCls : 'btn-add',
							text : '添加图书Sn',
							xtype : 'button',
							handler : function() {
								new BookSnForm();
							}
						}, {
							iconCls : 'btn-del',
							text : '删除图书Sn',
							xtype : 'button',
							handler : function() {

								var grid = Ext.getCmp("BookSnGrid");

								var selectRecords = grid.getSelectionModel()
										.getSelections();

								if (selectRecords.length == 0) {
									Ext.ux.Toast.msg("信息", "请选择要删除的记录！");
									return;
								}
								var ids = Array();
								for (var i = 0; i < selectRecords.length; i++) {
									ids.push(selectRecords[i].data.bookSnId);
								}

								BookSnView.remove(ids);
							}
						}]
			});
	return toolbar;
};

/**
 * 删除单个记录
 */
BookSnView.remove = function(id) {
	var grid = Ext.getCmp("BookSnGrid");
	Ext.Msg.confirm('信息确认', '您确认要删除该记录吗？', function(btn) {
				if (btn == 'yes') {
					Ext.Ajax.request({
								url : __ctxPath + '/admin/multiDelBookSn.do',
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
BookSnView.edit = function(id) {
	new BookSnForm(id);
}
