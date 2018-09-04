Ext.ns('OfficeGoodsManageView');

var OfficeGoodsManageView=function(){
	
	var selectedNode;
	
	var officeGoodsView=new OfficeGoodsView();
	
    var treePanel = new Ext.tree.TreePanel({
				region : 'west',
				id : 'leftOfficeGoodManagePanel',
				title : '办公用品类型',
				collapsible : true,
				split : true,
				width : 180,
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
							url : __ctxPath + '/admin/treeOfficeGoodsType.do'
						}),
				root : new Ext.tree.AsyncTreeNode({
							expanded : true
						}),
				rootVisible : false,
				listeners : {
					'click' : function(node){
						if (node != null) {
							
							officeGoodsView.setTypeId(node.id);
							
							var goodsView=Ext.getCmp('OfficeGoodsView');
							if(node.id==0){
								goodsView.setTitle('所有用品列表');
							}else{
								goodsView.setTitle('['+node.text+']类型用品列表');
							}
					    	var goodsGrid = Ext.getCmp('OfficeGoodsGrid');
					    	var store=goodsGrid.getStore();
					    	
					    	store.url=__ctxPath+'/admin/listOfficeGoods.do';
					    	store.baseParams={'Q_officeGoodsType.typeId_L_EQ':node.id==0?null:node.id};
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
	if(isGranted('_OfficeGoodsTypeManage')){
	treePanel.on('contextmenu', contextmenu, treePanel);
	}
	// 创建右键菜单
	
	var treeMenu = new Ext.menu.Menu({
				id : 'OfficeGoodsManageTreeMenu',
				items : [
				         {
							text : '新建类别',
							scope : this,
							iconCls:'btn-add',
							handler : createNode
						},{
							text : '修改类别',
							scope : this,
							iconCls:'btn-edit',
							handler : editNode
						},{
							text : '删除类别',
							scope : this,
							iconCls:'btn-delete',
							handler : deleteNode
						}
                  ]
			});

	//新建目录
	function createNode() {		
		new OfficeGoodsTypeForm(null);
		
	};
	//编辑目录
	function editNode() {
		var typeId=selectedNode.id;
		if(typeId>0){
			new OfficeGoodsTypeForm(typeId);
		}else{
			Ext.MessageBox.show({
				title : '操作信息',
				msg : '该处不能被修改',
				buttons : Ext.MessageBox.OK,
				icon : 'ext-mb-error'
			});
		}
	};
	//删除目录，子目录也一并删除
	function deleteNode() {
		var typeId=selectedNode.id;
		Ext.Msg.confirm('信息确认', '您确认要删除该记录吗？', function(btn) {
				if (btn == 'yes') {
		Ext.Ajax.request({
			url:__ctxPath+'/admin/multiDelOfficeGoodsType.do',
			params:{ids:typeId},
			method:'post',
			success:function(result,request){
				var res = Ext.util.JSON.decode(result.responseText);
				if(res.success==false){
					Ext.ux.Toast.msg('操作信息',res.message);
				}else{
					Ext.ux.Toast.msg('操作信息','成功删除目录！');
					treePanel.root.reload();
				}
			},
			
			failure:function(result,request){
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
		id:'OfficeGoodsManageView',
		title : '办公用品管理',
		iconCls:'menu-goods',
		layout : 'border',
		height : 800,
		items : [treePanel,officeGoodsView]
	});
	
	return panel;
};

