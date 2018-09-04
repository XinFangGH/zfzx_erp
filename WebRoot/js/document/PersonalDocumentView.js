Ext.ns("DocFolder");
/**
 *　个人文档目录视图
 */
var PersonalDocumentView = function() {
	var selectedNode;
	
	var documentView=new DocumentView();
	
	var treePanel = new Ext.tree.TreePanel({
				region : 'west',
				id : 'leftFolderPanel',
				title : '我的文档目录',
				collapsible : true,
				split : true,
				width : 160,
				height : 800,
				tbar:new Ext.Toolbar({items:[{
						xtype:'button',
						iconCls:'btn-refresh',
						text:'刷新',
						handler:function(){
							treePanel.root.reload();
						}
					},
					{
						xtype:'button',
						text:'展开',
						iconCls:'btn-expand',
						handler:function(){
							treePanel.expandAll();
						}
					},
					{
						xtype:'button',
						text:'收起',
						iconCls:'btn-collapse1',
						handler:function(){
							treePanel.collapseAll();
						}
					}
					]}),
				loader : new Ext.tree.TreeLoader({
							url : __ctxPath + '/document/listDocFolder.do'
						}),
				root : new Ext.tree.AsyncTreeNode({
							expanded : true
						}),
				rootVisible : false,
				listeners : {
					'click' : function(node){
						if (node != null) {
							
							documentView.setFolderId(node.id);
							
							var docView=Ext.getCmp('DocumentView');
							if(node.id==0){
								docView.setTitle('所有文档');
							}else{
								docView.setTitle('['+node.text+']文档列表');
							}
					    	var documentGrid = Ext.getCmp('DocumentGrid');
					    	var store=documentGrid.getStore();
					    	
					    	store.url=__ctxPath+'/system/listDocument.do';
					    	store.baseParams={folderId:node.id};
					    	store.params={start:0,limit:25};
					    	store.reload({params:{start:0,limit:25}});
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
	//树的右键菜单的
	treePanel.on('contextmenu', contextmenu, treePanel);

	// 创建右键菜单
	var treeMenu = new Ext.menu.Menu({
				id : 'PerSonalDocumentTreeMenu',
				items : [{
							text : '新建目录',
							scope : this,
							iconCls:'btn-add',
							handler : createNode
						}, {
							text : '修改目录',
							scope : this,
							iconCls:'btn-edit',
							handler : editNode
						}, {
							text : '删除目录',
							scope : this,
							iconCls:'btn-delete',
							handler : deleteNode
						}]
			});

	//新建目录
	function createNode(nodeId) {
		var parentId=selectedNode.id;
		
		new DocFolderForm(null,parentId,null);
		
	};
	//编辑目录
	function editNode() {
		var folderId=selectedNode.id;
		new DocFolderForm(folderId,null,null);
	};
	//删除目录，子目录也一并删除
	function deleteNode() {
		var folderId=selectedNode.id;
			Ext.Msg.confirm('删除操作', '你确定删除该目录吗?', function(btn) {
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
				title : '我的文档',
				iconCls:'menu-personal-doc',
				layout : 'border',
				id:'PersonalDocumentView',
				height : 800,
				items : [treePanel,documentView.getView()]
	});
	
	return panel;
};


