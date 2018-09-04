Ext.ns('BookManageView');

var BookManageView = function() {

	var selectedNode;

	var bookView = new BookView();

	var treePanel = new Ext.tree.TreePanel({
				region : 'west',
				id : 'leftBookManagePanel',
				title : '图书类别',
				collapsible : true,
				split : true,
				width : 150,
				height : 800,
				tbar : new Ext.Toolbar({
							id : 'BookManageBar',
							items : [{
										xtype : 'button',
										iconCls : 'btn-refresh',
										text : '刷新',
										handler : function() {
											treePanel.root.reload();
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

							bookView.setTypeId(node.id);

							var bView = Ext.getCmp('BookView');
							if (node.id == 0) {
								bView.setTitle('所有图书列表');
							} else {
								bView.setTitle('[' + node.text + ']列表');
							}
							var bGrid = Ext.getCmp('BookGrid');
							var store = bGrid.getStore();

							store.url = __ctxPath + '/admin/listBook.do';
							store.baseParams = {
								'Q_bookType.typeId_L_EQ' : node.id == 0
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
	if (isGranted('_BookTypeAdd') || isGranted('_BookTypeEdit')
			|| isGranted('_BookTypeDel')) {
		// 树的右键菜单的
		treePanel.on('contextmenu', contextmenu, treePanel);
		Ext.getCmp('BookManageBar').add(new Ext.Button({
					text : '添加类别',
					iconCls : 'btn-add',
					handler : function() {
						var bookTypeForm = Ext.getCmp('bookTypeForm');
						if (bookTypeForm == null) {
							new BookTypeForm();
						} else {
							bookTypeForm.getForm().reset();
						}
					}
				}));
	}
	// 创建右键菜单
	var treeMenu = new Ext.menu.Menu({
				id : 'BookManageTreeMenu',
				items : []
			});
	if (isGranted('_BookTypeAdd')) {
		treeMenu.add({
					text : '添加类别',
					scope : this,
					iconCls : 'btn-add',
					handler : createNode
				});

	}
	if (isGranted('_BookTypeEdit')) {
		treeMenu.add({
					text : '修改类别',
					scope : this,
					iconCls : 'btn-edit',
					handler : editNode
				});
	}
	if (isGranted('_BookTypeDel')) {
		treeMenu.add({
					text : '删除类别',
					scope : this,
					iconCls : 'btn-delete',
					handler : deleteNode
				});
	}

	// 新建目录
	function createNode() {
		new BookTypeForm(null);

	};
	// 编辑目录
	function editNode() {
		var typeId = selectedNode.id;
		if (typeId > 0) {
			new BookTypeForm(typeId);
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
									url : __ctxPath
											+ '/admin/multiDelBookType.do',
									params : {
										ids : typeId
									},
									method : 'post',
									success : function(result, request) {
										var res = Ext.util.JSON
												.decode(result.responseText);
										if (res.success == false) {
											Ext.ux.Toast.msg("操作信息",
													res.message);
										} else {
											Ext.ux.Toast.msg("操作信息", "成功删除目录！");
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
					}
				});
	};

	var panel = new Ext.Panel({
				id : 'BookManageView',
				title : '图书管理',
				iconCls : 'menu-book-manage',
				layout : 'border',
				height : 800,
				items : [treePanel, bookView.getView()]
			});

	return panel;
};
