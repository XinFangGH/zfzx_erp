Ext.ns('PhoneGroupView');
/**
 * TODO: add class/table comments列表
 */
var PhoneGroupView = function() {
	return new Ext.Panel({
		id : 'PhoneGroupView',
		title : '通讯组列表',
		layout : 'border',
		region : 'center',
		autoScroll : true,
		items : [new Ext.FormPanel({
			region:'north',
			height : 35,
			frame : true,
			id : 'PhoneGroupSearchForm',
			layout : 'column',
			defaults : {
				xtype : 'label'
			},
			items : [{
						text : '请输入查询条件:'
					}, {
						text : '分组名称'
					}, {
						xtype : 'textfield',
						name : 'Q_groupName_S_LK'
					}, {
						text : '1=共享0=私有'
					}, {
						xtype : 'textfield',
						name : 'Q_isShared_S_LK'
					}, {
						text : ''
					}, {
						xtype : 'textfield',
						name : 'Q_SN_S_LK'
					}, {
						text : ''
					}, {
						xtype : 'textfield',
						name : 'Q_userId_S_LK'
					}, {
						xtype : 'button',
						text : '查询',
						iconCls : 'search',
						handler : function() {
							var searchPanel = Ext
									.getCmp('PhoneGroupSearchForm');
							var gridPanel = Ext.getCmp('PhoneGroupGrid');
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
PhoneGroupView.prototype.setup = function() {
	return this.grid();
};
/**
 * 建立DataGrid
 */
PhoneGroupView.prototype.grid = function() {
	var sm = new Ext.grid.CheckboxSelectionModel();
	var cm = new Ext.grid.ColumnModel({
		columns : [sm, new Ext.grid.RowNumberer(), {
					header : 'groupId',
					dataIndex : 'groupId',
					hidden : true
				}, {
					header : '分组名称',
					dataIndex : 'groupName'
				}, {
					header : '1=共享0=私有',
					dataIndex : 'isShared'
				}, {
					header : '',
					dataIndex : 'SN'
				}, {
					header : '',
					dataIndex : 'userId'
				}, {
					header : '管理',
					dataIndex : 'groupId',
					width : 50,
					renderer : function(value, metadata, record, rowIndex,
							colIndex) {
						var editId = record.data.groupId;
						var str = '<button title="删除" value=" " class="btn-del" onclick="PhoneGroupView.remove('
								+ editId + ')">&nbsp;</button>';
						str += '&nbsp;<button title="编辑" value=" " class="btn-edit" onclick="PhoneGroupView.edit('
								+ editId + ')">&nbsp;</button>';
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
				id : 'PhoneGroupGrid',
				tbar : this.topbar(),
				store : store,
				trackMouseOver : true,
				disableSelection : false,
				loadMask : true,
				region : 'center',
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
							PhoneGroupView.edit(rec.data.groupId);
						});
			});
	return grid;

};

/**
 * 初始化数据
 */
PhoneGroupView.prototype.store = function() {
	var store = new Ext.data.Store({
				proxy : new Ext.data.HttpProxy({
							url : __ctxPath + '/communicate/listPhoneGroup.do'
						}),
				reader : new Ext.data.JsonReader({
							root : 'result',
							totalProperty : 'totalCounts',
							id : 'id',
							fields : [{
										name : 'groupId',
										type : 'int'
									}

									, 'groupName', 'isShared', 'SN', 'userId']
						}),
				remoteSort : true
			});
	store.setDefaultSort('groupId', 'desc');
	return store;
};

/**
 * 建立操作的Toolbar
 */
PhoneGroupView.prototype.topbar = function() {
	var toolbar = new Ext.Toolbar({
				id : 'PhoneGroupFootBar',
				height : 30,
				bodyStyle : 'text-align:left',
				items : [{
							iconCls : 'btn-add',
							text : '添加PhoneGroup',
							xtype : 'button',
							handler : function() {
								new PhoneGroupForm();
							}
						}, {
							iconCls : 'btn-del',
							text : '删除PhoneGroup',
							xtype : 'button',
							handler : function() {

								var grid = Ext.getCmp("PhoneGroupGrid");

								var selectRecords = grid.getSelectionModel()
										.getSelections();

								if (selectRecords.length == 0) {
									Ext.ux.Toast.msg("信息", "请选择要删除的记录！");
									return;
								}
								var ids = Array();
								for (var i = 0; i < selectRecords.length; i++) {
									ids.push(selectRecords[i].data.groupId);
								}

								PhoneGroupView.remove(ids);
							}
						}]
			});
	return toolbar;
};

/**
 * 删除单个记录
 */
PhoneGroupView.remove = function(id) {
	var grid = Ext.getCmp("PhoneGroupGrid");
	Ext.Msg.confirm('信息确认', '您确认要删除该记录吗？', function(btn) {
				if (btn == 'yes') {
					Ext.Ajax.request({
								url : __ctxPath
										+ '/communicate/multiDelPhoneGroup.do',
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
PhoneGroupView.edit = function(id) {
	new PhoneGroupForm(id);
}
