Ext.ns('BookTypeTree');
var BookTypeTree=function(){
	return this.setup();
};

BookTypeTree.prototype.setup=function(){
	var selected;//右键菜单所点的树结点
	//图书类别管理树
	var treePanel = new Ext.tree.TreePanel({
		id:'typeTree',
		title:'图书类别',
		region : 'west',
		width : 200,
		height:480,
		autoScroll: true,
		collapsible : true,
		split: true,
		tbar:new Ext.Toolbar({
		    height: 30,
		    items: [
				{
				    text: '添加',
				    iconCls:'add-info',
				    handler: function() {
				    	var bookTypeForm = Ext.getCmp('bookTypeForm');
				    	if(bookTypeForm==null){
				    		new BookTypeForm();
				    	}else{
				    		bookTypeForm.getForm().reset();
				    	}
				    }
				},{
						xtype:'button',
						iconCls:'btn-refresh',
						text:'刷新',
						handler:function(){
							treePanel.root.reload();
						}
					}
				]
		}),
		loader: new Ext.tree.TreeLoader({url:__ctxPath+'/admin/treeBookType.do'}),
	    root:
	    	new Ext.tree.AsyncTreeNode({
	        expanded: true
	    }),
	    rootVisible: false,
	    listeners: {
	        'click': BookTypeTree.clickNode
	    }
	});
	 if(isGranted('_BookTypeAdd')||isGranted('_BookTypeEdit')||isGranted('_BookTypeDel')){
		//树的右键菜单的
		treePanel.on('contextmenu', contextmenu, treePanel);
	 }
		// 创建右键菜单
		var treeMenu = new Ext.menu.Menu( {
		        id : 'BookTypeTreeMenu',
		        items : []
				});
		if(isGranted('_BookTypeAdd')){
		treeMenu.add({
		                text : '新建',
		                scope : this,
		                handler :createNode
		            });
		}
		if(isGranted('_BookTypeEdit')){
		treeMenu.add({  
		                text : '修改',  
		                scope : this,
		                handler : editNode  
		            });
		}
		if(isGranted('_BookTypeDel')){
		treeMenu.add({  
		                text : '删除',  
		                scope : this,
		                handler : deteleNode  
		            });
		}
		 
		function contextmenu(node, e) {
			selected = new Ext.tree.TreeNode({
			    id:node.id,
			    text:node.text
			    });
			    treeMenu.showAt(e.getXY());
		}
		/**
		 * 菜单事件
		 */
		function createNode(){//增加结点
			var bookTypeForm = Ext.getCmp('bookTypeForm');
			if(bookTypeForm==null){
				new BookTypeForm();
			}
			
		}
		function deteleNode(){//删除结点
			var typeId = selected.id;
			var type = Ext.getCmp('typeTree');
			if(typeId>0){
				Ext.Msg.confirm('删除操作','你确定删除图书类别吗?',function(btn){
					if(btn=='yes'){
					Ext.Ajax.request({
						url:__ctxPath+'/admin/removeBookType.do',
						params:{typeId:typeId},
						method:'post',
						success:function(){
							Ext.ux.Toast.msg('操作信息','删除成功!');
							var bookTypeGrid = Ext.getCmp('BookTypeView');
							if(bookTypeGrid!=null){
            					bookTypeGrid.getStore().reload();
            				}
            				if(type!=null){type.root.reload();}
						}
					});
					}
				});
			}
		}
		function editNode(){//修改结点
			var typeId = selected.id;
			var bookTypeForm = Ext.getCmp('bookTypeForm');
			if(bookTypeForm==null){
				new BookTypeForm(null);
				bookTypeForm = Ext.getCmp('bookTypeForm');
			}
			bookTypeForm.form.load({
				url:__ctxPath+'/admin/getBookType.do',
				params:{typeId:typeId},
				method:'post',
				deferredRender :true,
				layoutOnTabChange :true,
				waitMsg : '正在载入数据...',
		        success : function() {
		        },
		        failure : function() {
		            Ext.ux.Toast.msg('编辑', '载入失败');
		        }
			});
		}
	return treePanel;
}
/**
 *按类别查找图书
*/
BookTypeTree.clickNode=function(node){
    if (node != null) {
    	var book = Ext.getCmp('BookGrid');
    	var store=book.getStore();
    	store.proxy =new Ext.data.HttpProxy({url:__ctxPath+'/admin/categoryBook.do'});
    	store.baseParams={typeId:node.id};
    	store.reload({params:{start:0,limit:25}});
    }
}