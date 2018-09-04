/**
 * 用户选择器
 * @class UserDialog
 * @extends Ext.Window
 */
department=Ext.extend(Ext.Window,{
	constructor:function(conf){
		Ext.applyIf(this,conf);
		
		this.scope=this.scope?this.scope:this;
		//默认为多单选择用户
		this.single=this.single!=null?this.single:true;
		
		this.initUI();
		department.superclass.constructor.call(this,{
			title:this.title?this.title:'部门列表',
			height:450,
			width:630,
			maximizable : true,
			modal:true,
			layout:'border',
			items:[
                this.orgTreePanel
			]
		});
		if(this.isForFlow){
			Ext.getCmp('startUser').disabled = false;
		}
		if(!this.single){
			this.add(this.selGridPanel);
			this.doLayout();
		}
	},
	//按组织架构查找用户
	orgTreeClick:function(){
		var orgId=this.orgTreePanel.selectedNode.id;
		var orgName=this.orgTreePanel.selectedNode.text;
		alert(orgId);
		alert(orgName)
		if (this.callback){
			this.callback.call(this.scope, orgId, orgName);
		}
		this.close();

	},
	
	/**
	 * 初始化UI
	 */
	initUI:function(){
	
		
		//组织树Panel
		this.orgTreePanel=new zhiwei.ux.TreePanelEditor({
			border:false,
			url : __ctxPath+'/system/treeOrganization.do',
			region:'center',
			scope:this,
			autoScroll:true,
			onclick:this.orgTreeClick
		});
	


	
	}
	
});