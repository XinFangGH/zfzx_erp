/**
 * 流程管理页
 */
 var NormalFlowMnagerView=function(){
 	return this.getView();
 };
 NormalFlowMnagerView.prototype.getView=function(){
 	    var selectedNode;
 		var proDefView=new NormalFlowMnagerProDefinitionView(true);
 		var treePanel = new Ext.Panel({
 			layout : 'fit',
 			region : 'west',
	 		collapsible : true,
			split : true, 
	 		width : 200,
	 		title:'流程分类树', 
	 		items : [new zhiwei.ux.TreePanelEditor({
				 /*id : 'NormalFlowMnagerViewTree',
	             border:false,
	             autoScroll:true,
	             //url:__ctxPath+'/system/flowTreeGlobalType.do?catKey=FLOW',
	             url : __ctxPath+'/system/treeOrganization.do?type=branch',
	            // url : __ctxPath+'/system/treeGroupOrganization.do?isOnlyShowCompany='+true,
	             onclick:function(node){
	             	proDefView.setTypeId(node.id);
	             	proDefView.setOrgName(node.text);
	             	flowTitle=node.text;
	             	var defGridView=Ext.getCmp('ProDefinitionGridNormal');
					var flowListTitle=Ext.getCmp('flowListTitle');
					flowListTitle.setTitle('<font color=\'red\'>【'+flowTitle+'】</font>'+'流程列表');
					defGridView.getStore().proxy.conn.url=__ctxPath + '/flow/listByBranchCompanyIdProDefinition.do?branchCompanyId='+node.id;
					defGridView.getStore().load({
						params : {
							start : 0,
							limit : 25
						}
					});
	             }*/
	 			
	 		//
			 id : 'NormalFlowMnagerViewTree',
             border:false,
             autoScroll:true,
             url:__ctxPath+'/system/flowTreeGlobalType.do?catKey=FLOW',
             onclick:function(node){
             	proDefView.setTypeId(node.id);
				var defGridView=Ext.getCmp('ProDefinitionGridNormal');
				defGridView.getStore().proxy.conn.url=__ctxPath + '/flow/listByProTypeIdProDefinition.do?typeId='+node.id;
				defGridView.getStore().load({
					params : {
						start : 0,
						limit : 25
					}
				});
             },
             contextMenuItems:[{  
                 text : '新建分类',  
                 scope : this,  
                 iconCls:'btn-add',  
                 hidden : !isGranted('_FlowCreateUpdateType2'),
                 handler : function(){
                 	var globalTypeTree=Ext.getCmp('NormalFlowMnagerViewTree');
                 	var parentId=globalTypeTree.selectedNode.id;
                 	var catKey='FLOW';
                 	var globalTypeForm=new GlobalTypeForm({
                 		parentId:parentId,
                 		catKey:catKey,
                 		callback:function(){
                 			Ext.getCmp('NormalFlowMnagerViewTree').root.reload();
                 		}});
                 	globalTypeForm.show();
                 }  
             },{
             	text:'修改分类',
             	scope:this,
             	iconCls:'btn-edit',
             	hidden : !isGranted('_FlowCreateUpdateType2'),
             	handler:function(){
             		var globalTypeTree=Ext.getCmp('NormalFlowMnagerViewTree');
                 	var proTypeId=globalTypeTree.selectedNode.id;
             		if(proTypeId!=0){
             			var globalTypeForm = new GlobalTypeForm({
	                 		proTypeId:proTypeId,
	                 		callback:function(){
	                 			Ext.getCmp('NormalFlowMnagerViewTree').root.reload();
	                 		}
                 	});
                 	globalTypeForm.show();
             		}else{
             			Ext.ux.Toast.msg('操作信息', globalTypeTree.selectedNode.text+'不允许修改！');
             		}
             	}
             }, {
             	text : '设置权限',
             	scope : this,
             	hidden : true,
             	iconCls : 'btn-shared',
             	handler : function(){
             		var globalTypeTree=Ext.getCmp('NormalFlowMnagerViewTree');
                 	var proTypeId=globalTypeTree.selectedNode.id;
                 	if(proTypeId != 0){
                 		new ProDefRightsForm({
             				proTypeId : proTypeId
             			}).show();
                 	}else{
             			Ext.ux.Toast.msg('操作信息', "不能给"+globalTypeTree.selectedNode.text+'设置权限！');
             		}
             	}
             }]
 		})]
 	});
	var panel = new Ext.Panel({
			title : '标准业务流程管理',
			layout : 'border',
			iconCls:'menu-flowManager',
			id:'NormalFlowMnagerView',
			height : 800,
			items : [treePanel,proDefView.getView()]
	});
	
	return panel;
 };
