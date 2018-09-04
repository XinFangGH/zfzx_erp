/**
 *新建流程 
 */
var NewProcess = function(){
	return this.getView();
};

NewProcess.prototype.getView=function(){
		var selectedNode;
 		var proDefView=new ProDefinitionView(false);//不能进行数据的管理
 		var treePanel = new Ext.Panel({
 			layout : 'fit',
 			region : 'west',
	 		collapsible : true,
			split : true, 
	 		width : 200,
	 		title:'流程分类树', 
	 		items : [new zhiwei.ux.TreePanelEditor({
	 					 id : 'FlowProTypeTree',
			             border:false,
			             autoScroll:true,
			             url:__ctxPath+'/system/flowTreeGlobalType.do?catKey=FLOW',
			             onclick:function(node){
			             	proDefView.setTypeId(node.id);
						
							var defGridView=Ext.getCmp('ProDefinitionGrid0');
							defGridView.getStore().proxy.conn.url=__ctxPath + '/flow/listProDefinition.do?typeId='+node.id;
							defGridView.getStore().load({
												params : {
													start : 0,
													limit : 25
												}
											});
			             }
 		})]
 	});

	var panel = new Ext.Panel({
			title : '新建流程',
			layout : 'border',
			iconCls:'menu-flowNew',
			id:'NewProcess',
			height : 800,
			items : [treePanel,proDefView.getView()]
	});
	
	return panel;

}