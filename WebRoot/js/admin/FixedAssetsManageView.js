Ext.ns('FixedAssetsManageView');

var FixedAssetsManageView = function() {

	var selectedNode;

	var fixedAssetsView = new FixedAssetsView();

	var treePanel = new Ext.tree.TreePanel({
				region : 'west',
				id : 'leftFixedAssetsManagePanel',
				title : '资产类型',
				collapsible : true,
				split : true,
				width : 160,
				height : 800,
				tbar : new Ext.Toolbar({
							items : [{
										xtype : 'button',
										iconCls : 'btn-refresh',
										text : '刷新',
										handler : function() {
											treePanel.root.reload();
										}
									}, {
										xtype : 'button',
										text : '展开',
										iconCls : 'btn-expand',
										handler : function() {
											treePanel.expandAll();
										}
									}, {
										xtype : 'button',
										text : '收起',
										iconCls : 'btn-collapse1',
										handler : function() {
											treePanel.collapseAll();
										}
									}]
						}),
				loader : new Ext.tree.TreeLoader({
							url : __ctxPath + '/admin/treeAssetsType.do'
						}),
				root : new Ext.tree.AsyncTreeNode({
							expanded : true
						}),
				rootVisible : false,
				listeners : {
					'click' : function(node) {
						if (node != null) {

							fixedAssetsView.setTypeId(node.id);

							var fixedView = Ext.getCmp('FixedAssetsView');
							if (node.id == 0) {
								fixedView.setTitle('所有固定资产列表');
							} else {
								fixedView.setTitle('[' + node.text
										+ ']类型固定资产列表');
							}
							var fixedAssetsGrid = Ext.getCmp('FixedAssetsGrid');
							var store = fixedAssetsGrid.getStore();

							store.url = __ctxPath + '/admin/listFixedAssets.do';
							store.baseParams = {
								'Q_assetsType.assetsTypeId_L_EQ' : node.id == 0
										? null
										: node.id
							};
							store.params = {
								start : 0,
								limit : 25
							};
							store.reload({
										params : {
											start : 0,
											limit : 25
										}
									});
						}
					}
				}
			});

	function contextmenu(node, e) {
		selectedNode = new Ext.tree.TreeNode({
					id : node.id,
					text : node.text
				});
		treeMenu.showAt(e.getXY());
	}
	if (isGranted('_AssetsTypeManage')) {
		// 树的右键菜单的
		treePanel.on('contextmenu', contextmenu, treePanel);
	}
	// 创建右键菜单
	var treeMenu = new Ext.menu.Menu({
				id : 'FixedAssetsTreeMenu',
				items : [{
							text : '新建类型',
							scope : this,
							iconCls : 'btn-add',
							handler : createNode
						}, {
							text : '修改类型',
							scope : this,
							iconCls : 'btn-edit',
							handler : editNode
						}, {
							text : '删除类型',
							scope : this,
							iconCls : 'btn-delete',
							handler : deleteNode
						}]
			});

	// 新建目录
	function createNode() {
		new AssetsTypeForm(null);

	};
	// 编辑目录
	function editNode() {
		var typeId = selectedNode.id;
		if (typeId > 0) {
			new AssetsTypeForm(typeId);
		} else {
			Ext.MessageBox.show({
						title : '操作信息',
						msg : '该处不能被修改',
						buttons : Ext.MessageBox.OK,
						icon : 'ext-mb-error'
					});
		}
	};
	// 删除目录，子目录也一并删除
	function deleteNode() {
		var typeId = selectedNode.id;
		Ext.Msg.confirm('信息确认', '您确认要删除该记录吗？', function(btn) {
				if (btn == 'yes') {
		Ext.Ajax.request({
					url : __ctxPath + '/admin/multiDelAssetsType.do',
					params : {
						ids : typeId
					},
					method : 'post',
					success : function(result, request) {
						var res = Ext.util.JSON.decode(result.responseText);
						if(res.success==false){
						  Ext.ux.Toast.msg('操作信息', res.message);
						}else{
							Ext.ux.Toast.msg('操作信息', '成功删除目录！');
							treePanel.root.reload();
						}
					},

					failure : function(result, request) {
						Ext.MessageBox.show({
									title : '操作信息',
									msg : '信息保存出错，请联系管理员！',
									buttons : Ext.MessageBox.OK,
									icon : 'ext-mb-error'
								});
					}

				});
				}});
	};

	var panel = new Ext.Panel({
				id : 'FixedAssetsManageView',
				title : '固定资产管理',
				iconCls : 'menu-assets',
				layout : 'border',
				height : 800,
				items : [treePanel, fixedAssetsView.getView()]
			});

	return panel;
};
