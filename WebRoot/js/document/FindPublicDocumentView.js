Ext.ns('FindPublicDocumentView');

var FindPublicDocumentView=function(){
	
	var selectedNode;
	
	var publicDocumentView=new PublicDocumentView();
	
    var treePanel = new Ext.tree.TreePanel({
				region : 'west',
				id : 'leftPublicDocumentPanel',
				title : '公共文档目录',
				collapsible : true,
				split : true,
				width : 200,
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
							url : __ctxPath + '/document/treeDocFolder.do'
						}),
				root : new Ext.tree.AsyncTreeNode({
							expanded : true
						}),
				rootVisible : false,
				listeners : {
					'click' : function(node){
						if (node != null) {
							
							publicDocumentView.setFolderId(node.id);
							
							var docView=Ext.getCmp('PublicDocumentView');
							if(node.id==0){
								docView.setTitle('所有文档');
							}else{
								docView.setTitle('['+node.text+']文档列表');
							}
					    	var documentGrid = Ext.getCmp('PublicDocumentGrid');
					    	var store=documentGrid.getStore();
					    	
					    	store.url=__ctxPath+'/document/publicListDocument.do';
					    	store.baseParams={folderId:node.id};
					    	store.reload({params:{start:0,limit:25}});
					    }
					}
				}
			});


	var panel = new Ext.Panel({
		id:'FindPublicDocumentView',
		title : '公共文档',
		iconCls:'menu-find-doc',
		layout : 'border',
		height : 800,
		items : [treePanel,publicDocumentView.getView()]
	});
	
	return panel;
};

