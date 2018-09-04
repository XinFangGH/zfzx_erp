var DocFolderSelector = {
	getView : function(callback,isOnline) {
		var nodeValue;
		var clickNode = function(node) {
			if (node != null) {
				if(!node.disabled){
					nodeValue=node;
				  return nodeValue;
				}
			}
	    };
	    var _url=__ctxPath + '/document/knowledgeTreeDocFolder.do'
		if(isOnline){
		   _url=__ctxPath + '/document/onlineTreeDocFolder.do';
		}
		var treePanel = new Ext.tree.TreePanel({
					id : 'docFolderTreePanel',
					title : '目录树',
					loader : new Ext.tree.TreeLoader({
								url : _url
							}),
					root : new Ext.tree.AsyncTreeNode({
								expanded : true
							}),
					rootVisible : false,
					autoScroll:true,
					listeners : {
						'click' : clickNode
					}
		});
						
		var window = new Ext.Window({
			title : '请选择目录',
			width : 440,
			iconCls:'menu-mail_folder',
			height : 420,
			layout:'fit',
			items : [treePanel],
			modal:true,
			buttonAlign : 'center',
			buttons : [{
						text : '确认',
						iconCls:'btn-ok',
						scope:'true',
						handler : function(){
						  if(nodeValue!=null&&nodeValue.id>0){
							if (callback != null) {
								callback.call(this, nodeValue.id, nodeValue.text);
							}
							window.close();
						  }else{
						        Ext.ux.Toast.msg('提示','你无权插文档入该目录！');
						  }
						}
					}, {
						text : '关闭',
						iconCls:'close',
						handler : function() {
							window.close();
						}
					}]
		});
		return window;
	}
};
