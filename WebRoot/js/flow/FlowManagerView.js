/**
 * 流程管理页
 */
 var FlowManagerView=function(){
 	return this.getView();
 };
 
 FlowManagerView.prototype.getView=function(){
 	    var selectedNode;
 		var proDefView=new ProDefinitionView(true);
 		var treePanel = new Ext.Panel({
 			layout : 'fit',
 			region : 'west',
	 		collapsible : true,
			split : true, 
	 		width : 200,
	 		title:'流程分类树', 
	 		items : [new zhiwei.ux.TreePanelEditor({
	 		//
	 					 id : 'FlowProTypeTree',
			             border:false,
			             autoScroll:true,
			             url:__ctxPath+'/system/flowTreeGlobalType.do?catKey=FLOW',
			             onclick:function(node){
			             	proDefView.setTypeId(node.id);
						
							var defGridView=Ext.getCmp('ProDefinitionGrid');
							defGridView.getStore().proxy.conn.url=__ctxPath + '/flow/listProDefinition.do?typeId='+node.id;
							defGridView.getStore().load({
												params : {
													start : 0,
													limit : 25
												}
											});
			             },
			             contextMenuItems:[  
			                         {  
			                             text : '新建分类',  
			                             scope : this,  
			                             iconCls:'btn-add',  
			                             handler : function(){
			                             	var globalTypeTree=Ext.getCmp('FlowProTypeTree');
			                             	var parentId=globalTypeTree.selectedNode.id;
			                             	var catKey='FLOW';

			                             	var globalTypeForm=new GlobalTypeForm({
			                             		parentId:parentId,
			                             		catKey:catKey,
			                             		callback:function(){
			                             			Ext.getCmp('FlowProTypeTree').root.reload();
			                             		}
			                             	});
			                             	globalTypeForm.show();
			                               
			                             }  
			                         },{
			                         	text:'修改分类',
			                         	scope:this,
			                         	iconCls:'btn-edit',
			                         	handler:function(){
			                         		var globalTypeTree=Ext.getCmp('FlowProTypeTree');
			                             	var proTypeId=globalTypeTree.selectedNode.id;
			                             	
			                             	var globalTypeForm = new GlobalTypeForm({
			                             		proTypeId:proTypeId,
			                             		callback:function(){
			                             			Ext.getCmp('FlowProTypeTree').root.reload();
			                             		}
			                             	});
			                             	globalTypeForm.show();
			                         	}
			                         }, {
			                         	text : '设置权限',
			                         	scope : this,
			                         	hidden : true,
			                         	iconCls : 'btn-shared',
			                         	handler : function(){
			                         		var globalTypeTree=Ext.getCmp('FlowProTypeTree');
			                             	var proTypeId=globalTypeTree.selectedNode.id;
			                             	if(proTypeId != 0){
			                             		new ProDefRightsForm({
			                         				proTypeId : proTypeId
			                         			}).show();
			                             	}
			                         	}
			                         } 
			                         ]
 		})]
 	});
 	
	var panel = new Ext.Panel({
			title : '流程定义管理',
			layout : 'border',
			iconCls:'menu-flowManager',
			id:'FlowManagerView',
			height : 800,
			items : [treePanel,proDefView.getView()]
	});
	
	return panel;
 };
