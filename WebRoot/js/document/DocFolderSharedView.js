Ext.ns('DocFolderSharedView');

var DocFolderSharedView = function() {
	var selectedNode;
	var docPrivilegeView = new DocPrivilegeView();
	var treePanel = new Ext.tree.TreePanel({
				region : 'west',
				id : 'leftDocFolderSharedPanel',
				title : '文件夹目录',
				collapsible : true,
				split : true,
				width : 180,
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
							url : __ctxPath + '/document/treeDocFolder.do'
						}),
				root : new Ext.tree.AsyncTreeNode({
							expanded : true
						}),
				rootVisible : false,
				listeners : {
					'click' : function(node) {
						if (node != null) {
							docPrivilegeView.setFolderId(node.id);

							var docPView = Ext.getCmp('DocPrivilegeView');
							if (node.id == 0) {
								docPView.setTitle('文件夹授权');
							} else {
								docPView.setTitle('文件夹[' + node.text + ']授权情况');
							}
							var privilegeGrid = Ext.getCmp('DocPrivilegeGrid');
							var store = privilegeGrid.getStore();
							store.url = __ctxPath
									+ '/document/listDocPrivilege.do';
							store.baseParams = {
								'Q_docFolder.folderId_L_EQ' : node.id
							};
							store.params = {
								start : 0,
								limit : 25
							};
							store.reload();
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
	if (isGranted('_DocFolderSharedManage')) {
		// 树的右键菜单的
		treePanel.on('contextmenu', contextmenu, treePanel);
	}
	// 创建右键菜单
	var treeMenu = new Ext.menu.Menu({
				tbar : new Ext.Toolbar({
							items : [{
										text : '刷新',
										handler : function() {
											alert('refresh');
										}
									}]
						}),
				id : 'DocFolderTreeMenu',
				items : [{
							text : '新建目录',
							scope : this,
							iconCls : 'btn-add',
							handler : createNode
						}, {
							text : '修改目录',
							scope : this,
							iconCls : 'btn-edit',
							handler : editNode
						}, {
							text : '删除目录',
							scope : this,
							iconCls : 'btn-delete',
							handler : deleteNode
						}]
			});

	// 新建目录
	function createNode(nodeId) {
		var parentId = selectedNode.id;
        new DocFolderForm({
					folderId : null,
					parentId : parentId,
					isShared : 1
				}).show();// 表示增加公共文件夹

	};
	// 编辑目录
	function editNode() {
		var folderId = selectedNode.id;
		new DocFolderForm({
					folderId : folderId,
					parentId : null,
					isShared : null
				}).show();
	};
	// 删除目录，子目录也一并删除
	function deleteNode() {
		var folderId = selectedNode.id;
		Ext.Msg.confirm('删除操作', '删除目录会将该目录的权限删除，你确定删除该目录吗?', function(btn) {
			if (btn == 'yes') {
					Ext.Ajax.request({
								url : __ctxPath + '/document/removeDocFolder.do',
								params : {
									folderId : folderId
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
				id : 'DocFolderSharedView',
				title : '公共文件夹管理',
				iconCls : 'menu-public-fol',
				layout : 'border',
				height : 800,
				items : [treePanel, docPrivilegeView.getView()]
			});

	return panel;
};
