/**
 * 流程管理页
 */
 var AssignFlowView=function(){
 	return this.getView();
 };
 var flowListTitle;
 AssignFlowView.prototype.getView=function(){
 	    var selectedNode;
 		var proDefView=new AssignFlowProDefinitionView(true);
 		proDefView.setOrgName("");
 		proDefView.setTypeId("");
 		var treePanel = new Ext.Panel({
 			layout : 'fit',
 			region : 'west',
	 		collapsible : true,
			split : true, 
	 		width : 200,
	 		title:'加盟商列表', 
	 		items : [new zhiwei.ux.TreePanelEditor({
				 id : 'BranchCompanyFlowTree',
	             border:false,
	             autoScroll:true,
	             //url:__ctxPath+'/system/flowTreeGlobalType.do?catKey=FLOW',
	             //url : __ctxPath+'/system/treeOrganization.do?type=branch',
	             url : __ctxPath+'/system/treeGroupOrganization.do?isOnlyShowCompany='+true,
	             onclick:function(node){
	             	proDefView.setTypeId(node.id);
	             	proDefView.setOrgName(node.text);
	             	flowTitle=node.text;
	             	var defGridView=Ext.getCmp('ProDefinitionGridBranch');
					flowListTitle=Ext.getCmp('proDefinitionTitlePanelBranch');
					flowListTitle.setTitle('<font color=\'red\'>【'+flowTitle+'】</font>'+'流程列表');
					flowListTitle="null";
					defGridView.getStore().proxy.conn.url=__ctxPath + '/flow/listByBranchCompanyIdProDefinition.do?branchCompanyId='+node.id;
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
			title : '加盟商业务品种管理',
			layout : 'border',
			iconCls:'menu-flowManager',
			id:'AssignFlowView',
			height : 800,
			items : [treePanel,proDefView.getView()]
	});

	return panel;
 };
