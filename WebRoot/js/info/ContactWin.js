var ContactWin = function() {
	var panel = this.initWin();
	var window = new Ext.Window({
		title : '选择联系人',
		width : 440,
		height : 500,
		items : [panel],
		buttonAlign:'center',
		buttons : [{
					text : '确认',
					handler : function() {
						var grid = Ext.getCmp('contactGrid');
						var rows = grid.getSelectionModel().getSelections();
						if (rows.length == 0) {
							Ext.ux.Toast.msg("信息", "请选择要添加的联系人！");
							return;
						}
						var strId = '';
						var strName = '';

						var checkName = Ext.getCmp('userFullname');
						var checkId = Ext.getCmp('userId');
						for (var i = 0; i < rows.length; i++) {
							var ids = checkId.getValue().split(',');
							var id = rows[i].data.userId;
							var bool = false;
							for (var j = 0; j < ids.length; j++) {
								if (ids[j] == id) {
									bool = true;
									Ext.ux.Toast.msg('警告', '已经存在了');
									return bool;
								}
							}
							if (!bool) {
								strId = strId + id + ',';
								strName = strName + rows[i].data.fullname + ',';
							}
						}

						checkId.setValue(checkId.getValue() + strId);
						checkName.setValue(checkName.getValue() + strName);
					}
				}, {
					text : '关闭',
					handler : function() {
						window.close();
					}
				}]
	});
	window.show();
}

ContactWin.prototype.initWin = function() {

	var store = this.initData();
	store.load({
				params : {
					start : 0,
					limit : 10
				}
			});
	var sm = new Ext.grid.CheckboxSelectionModel();
	var cm = new Ext.grid.ColumnModel({
				columns : [sm, new Ext.grid.RowNumberer(), {
							header : "用户名",
							dataIndex : 'fullname',
							width : 60
						}],
				defaults : {
					sortable : true,
					menuDisabled : true,
					width : 100
				},
				listeners : {
					hiddenchange : function(cm, colIndex, hidden) {
						saveConfig(colIndex, hidden);
					}
				}
			});

	var treePanel = new Ext.tree.TreePanel({
				id : 'treePanels',
				title : '按部门分类 ',
				loader : new Ext.tree.TreeLoader({
							url : __ctxPath + '/system/listDepartment.do'
						}),
				root : new Ext.tree.AsyncTreeNode({
							expanded : true
						}),
				rootVisible : false,
				listeners : {
					'click' : ContactWin.clickNode
				}
			});

	var rolePanel = new Ext.tree.TreePanel({
				id : 'rolePanel',
				title : '按角色分类 ',
				loader : new Ext.tree.TreeLoader({
							url : __ctxPath + '/system/treeAppRole.do'
						}),
				root : new Ext.tree.AsyncTreeNode({
							expanded : true
						}),
				rootVisible : false,
				listeners : {
					'click' : ContactWin.clickRoleNode
				}
			});
	var onlinePanel = new Ext.Panel({
				id : 'onlinePanel',
				title : '在线人员  '
			});

	var contactGrid = new Ext.grid.GridPanel({
				id : 'contactGrid',
				height : 400,
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
				bbar : new HT.PagingBar({store : store,pageSize:10})
			});

	var contactPanel = new Ext.Panel({
				id : 'contactPanel',
				width : 400,
				height : 420,
				layout : 'border',
				border:false,
				items : [{
							region : 'west',
							split : true,
							collapsible : true,
							width : 150,
			                margins:'5 0 5 5',
			                layout:'accordion',
			                items: [treePanel, rolePanel, onlinePanel]
						}, {
							region : 'center',
							margins:'5 0 5 5',
							width : 220,
							items : [contactGrid]
						}]
			});
	return contactPanel;
};

ContactWin.prototype.initData = function() {
	var store = new Ext.data.Store({
				proxy : new Ext.data.HttpProxy({
							url : __ctxPath + '/system/selectAppUser.do'
						}),
				reader : new Ext.data.JsonReader({
							root : 'result',
							totalProperty : 'totalCounts',
							id : 'id',
							fields : [{
										name : 'userId',
										type : 'int'
									}, 'fullname']
						}),
				remoteSort : true
			});
	store.setDefaultSort('id', 'desc');
	return store;
};

ContactWin.clickNode = function(node) {
	if (node != null) {
		var users = Ext.getCmp('contactGrid');
		var store = users.getStore();
		store.url = __ctxPath + '/system/selectAppUser.do';
		store.baseParams = {
			depId : node.id
		};
		store.params = {
			start : 0,
			limit : 10
		};
		store.reload({
					params : {
						start : 0,
						limit : 10
					}
				});
	}
}

ContactWin.clickRoleNode = function(node) {
	if (node != null) {
		var users = Ext.getCmp('contactGrid');
		var store = users.getStore();
		store.url = __ctxPath + '/system/findAppUser.do';
		store.baseParams = {
			roleId : node.id
		};
		store.params = {
			start : 0,
			limit : 10
		};
		store.reload({
					params : {
						start : 0,
						limit : 10
					}
				});
	}
}