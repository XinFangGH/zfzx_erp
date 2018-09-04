Ext.ns('BookTypeView');
/**
 * 图书类别列表
 */
var BookTypeView = function() {
	return new Ext.Panel({
		id : 'BookTypeView',
		title : '图书类别列表',
		iconCls : 'menu-book-type',
		layout:'border',
		autoScroll : true,
		items : [new Ext.FormPanel({
			region:'north',
			id : 'BookTypeSearchForm',
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
						text : '图书类别'
					}, {
						xtype : 'textfield',
						name : 'Q_typeName_S_LK'
					}, {
						xtype : 'button',
						text : '查询',
						iconCls : 'search',
						handler : function() {
							var searchPanel = Ext.getCmp('BookTypeSearchForm');
							var gridPanel = Ext.getCmp('BookTypeGrid');
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
BookTypeView.prototype.setup = function() {
	return this.grid();
};
/**
 * 建立DataGrid
 */
BookTypeView.prototype.grid = function() {
	var sm = new Ext.grid.CheckboxSelectionModel();
	var cm = new Ext.grid.ColumnModel({
		columns : [sm, new Ext.grid.RowNumberer(), {
					header : 'typeId',
					dataIndex : 'typeId',
					hidden : true
				}, {
					header : '图书类别',
					dataIndex : 'typeName'
				}, {
					header : '管理',
					dataIndex : 'typeId',
					sortable : false,
					width : 50,
					renderer : function(value, metadata, record, rowIndex,
							colIndex) {
						var editId = record.data.typeId;
						var str = '';
						if (isGranted('_BookTypeDel')) {
							str = '<button title="删除" value=" " class="btn-del" onclick="BookTypeView.remove('
									+ editId + ')"></button>';
						}
						if (isGranted('_BookTypeEdit')) {
							str += '&nbsp;<button title="编辑" value=" " class="btn-edit" onclick="BookTypeView.edit('
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
				id : 'BookTypeGrid',
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
							if (isGranted('_BookTypeEdit')) {
								BookTypeView.edit(rec.data.typeId);
							}
						});
			});
	return grid;

};

/**
 * 初始化数据
 */
BookTypeView.prototype.store = function() {
	var store = new Ext.data.Store({
				proxy : new Ext.data.HttpProxy({
							url : __ctxPath + '/admin/listBookType.do'
						}),
				reader : new Ext.data.JsonReader({
							root : 'result',
							totalProperty : 'totalCounts',
							id : 'id',
							fields : [{
										name : 'typeId',
										type : 'int'
									}, 'typeName']
						}),
				remoteSort : true
			});
	store.setDefaultSort('typeId', 'desc');
	return store;
};

/**
 * 建立操作的Toolbar
 */
BookTypeView.prototype.topbar = function() {
	var toolbar = new Ext.Toolbar({
				id : 'BookTypeFootBar',
				height : 30,
				bodyStyle : 'text-align:left',
				items : []
			});
	if (isGranted('_BookTypeAdd')) {
		toolbar.add(new Ext.Button({
					iconCls : 'btn-add',
					text : '添加图书类别',
					handler : function() {
						new BookTypeForm();
					}
				}));
	}
	if (isGranted('_BookTypeDel')) {
		toolbar.add(new Ext.Button({
					iconCls : 'btn-del',
					text : '删除图书类别',
					handler : function() {

						var grid = Ext.getCmp("BookTypeGrid");

						var selectRecords = grid.getSelectionModel()
								.getSelections();

						if (selectRecords.length == 0) {
							Ext.ux.Toast.msg("信息", "请选择要删除的记录！");
							return;
						}
						var ids = Array();
						for (var i = 0; i < selectRecords.length; i++) {
							ids.push(selectRecords[i].data.typeId);
						}
						BookTypeView.remove(ids);
					}
				}));
	}
	return toolbar;
};

/**
 * 删除单个记录
 */
BookTypeView.remove = function(id) {
	var grid = Ext.getCmp("BookTypeGrid");
	Ext.Msg.confirm('信息确认', '您确认要删除该记录吗？', function(btn) {
		if (btn == 'yes') {
			Ext.Ajax.request({
						url : __ctxPath + '/admin/multiDelBookType.do',
						params : {
							ids : id
						},
						method : 'post',
						success : function(result, request) {
							var res = Ext.util.JSON.decode(result.responseText);
							if (res.success == false) {
								Ext.ux.Toast.msg("操作信息", res.message);
							} else {
								Ext.ux.Toast.msg("信息提示", "成功删除所选记录！");
								grid.getStore().reload({
											params : {
												start : 0,
												limit : 25
											}
										});
							}
						}
					});
		}
	});
};

/**
 * 
 */
BookTypeView.edit = function(id) {
	new BookTypeForm(id);
}
