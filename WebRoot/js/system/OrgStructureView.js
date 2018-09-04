/**
 * 公司部门人员管理
 * @class OrgStructureView
 * @extends Ext.Panel
 */
OrgStructureView=Ext.extend(Ext.Panel,{
	constructor:function(conf){
		Ext.apply(this,conf);
		this.initUI();
		OrgStructureView.superclass.constructor.call(this,{
			id:'OrgStructureView',
			title:'组织架构查询',
			layout:'border',
			iconCls:'menu-OrgStructure',
			items:[
				this.depTreePanel
				,this.userGridPanel
			]
		});
	},
	initUI:function(){
		this.depTreePanel=new zhiwei.ux.TreePanelEditor({
			width:230,
			region:'west',
			title:'部门',
			url : __ctxPath+'/system/orgStructureOrganization.do',
//			url : __ctxPath+'/system/listDepartment.do',
			scope : this,
			split:true,
			collapsible : true,
			autoScroll:true
//			contextMenuItems:[{
//				text:'新建公司或组织',
//				iconCls:'btn-add',
//				scope:this,
//				handler:this.addDep
//			},{
//				text:'编辑新建公司或组织',
//				iconCls:'btn-edit',
//				scope:this,
//				handler:this.editDep
//			},{
//				text:'删除新建公司或组织',
//				iconCls:'btn-del',
//				scope:this,
//				handler:this.delDep
//			}
//			],
//			onclick:this.depTreeClick
		});
		
		this.userGridPanel=new HT.GridPanel({
			rowActions : true,
			region:'center',
			tbar:[
				'姓名：',
				{xtype:'textfield',name:'fullname'},
				'账号：',
				{xtype:'textfield',name:'username'},
				' ',
				{xtype:'button',text:'查询',iconCls:'btn-query',scope:this,handler:this.query},
				'->',
				{
					xtype:'button',
					iconCls:'btn-add',
					text:'添加账号',
					scope:this,
					handler:this.addUser
				}
			],
			title:'组织人员列表',
			url:__ctxPath+'/system/depUsersAppUser.do',
			fields:[
				'userId','fullname','username','department'
			],
			columns:[{header:'账号',dataIndex:'username'},{header:'姓名',dataIndex:'fullname'},
				new Ext.ux.grid.RowActions({
					header : '管理',
					width : 100,
					actions : [{
						iconCls : 'btn-del',
						qtip : '删除',
						style : 'margin:0 3px 0 3px',
						fn:function(rs){
							if(rs.data.userId==-1){
								return false;
							}
							return true;
						}
					}, {
						iconCls : 'btn-edit',
						qtip : '编辑',
						style : 'margin:0 3px 0 3px'
					}, {
						iconCls : 'btn-relativeJob',
						qtip : '添加上下级',
						style : 'margin:0 3px 0 3px'
					}],
					listeners : {
						scope : this,
						'action' : this.onRowAction
					}
				})
			]
		});
	},
	query:function(){
		var toolbar=this.userGridPanel.getTopToolbar();
		var fullname=toolbar.getCmpByName('fullname').getValue();
		var username=toolbar.getCmpByName('username').getValue();
	
		var store=this.userGridPanel.getStore();
		var baseParam = {'Q_username_S_LK':username,'Q_fullname_S_LK':fullname,'depId':this.depTreePanel.selectedNode.id};
		
		baseParam.start = 0;
		baseParam.limit = store.baseParams.limit;
		store.baseParams = baseParam;
		this.userGridPanel.getBottomToolbar().moveFirst();
	},
	// 添加部门
	addDep:function(){
		var depId=this.depTreePanel.selectedNode.id;
		if(depId==0) return;
		var depName=this.depTreePanel.selectedNode.text;
		var orgType=this.depTreePanel.selectedNode.attributes.orgType;
		if(!orgType){//集团
			orgType=0;
		}else if(orgType<=1){//公司
			orgType=1;
		}else{
			orgType=2;//则为部门
		}
		
		new DepForm({
			parentId:depId,
			parentText:depName,
			scope:this,
			orgType:orgType,
			callback:this.reloadDep
		}).show();
	},
	// 重新加载树
	reloadDep:function(){
		this.depTreePanel.root.reload();
	},
	// 编辑职位
	editDep:function(){
		var depId=this.depTreePanel.selectedNode.id;
		if(depId==0) return;
		new DepForm({
			depId:depId,
			scope:this,
			callback:this.reloadDep
		}).show();
	},
	// 删除职位
	delDep:function(){
		var orgId=this.depTreePanel.selectedNode.id;
		if(orgId==0) return;
		Ext.Msg.confirm('信息确认', '注意：删除该部门将会删除其下所有的部门，您确认要删除所选部门吗？', function(btn) {
			if (btn == 'yes') {
				Ext.Ajax.request({
					url:__ctxPath+'/system/multiDelOrganization.do',
					method:'POST',
					scope:this,
					params:{
						ids:orgId
					},
					success:function(response,options){
						Ext.ux.Toast.msg('操作信息','成功删除部门!');	
						this.reloadDep.call(this);
					},
					failure : function(response,options) {
						Ext.ux.Toast.msg('操作信息','操作出错，请联系管理员！');
					}
				});
			}
		},this);
	},
	//部门树点击
	depTreeClick:function(){
		var depId=this.depTreePanel.selectedNode.id;
		var store= this.userGridPanel.getStore();
		store.baseParams={depId:depId};
		this.userGridPanel.getBottomToolbar().moveFirst();
	},
	//行编辑
	onRowAction:function(grid, record, action, row, col){
		switch (action) {
			case 'btn-del' :
				this.removeRs.call(this, record.data.userId);
				break;
			case 'btn-edit' :
				this.editRs.call(this, record);
				break;
			case 'btn-relativeJob': 
				this.addRelativeUser.call(this, record);
				break;
			default :
				break;
		}
	},
	// 添加上下级
	addRelativeUser : function(record){
		new RelativeUserView({
			userId : record.data.userId, //用户编号
			username : record.data.username,
			depId : record.data.department.depId //部门编号
		}).show();
	},
	//编辑用户信息
	editRs:function(record){
//		App.clickTopTab('UserFormPanel_'+record.data.userId,{userId:record.data.userId,username:record.data.username});
	},
	addUser:function(){
//		App.clickTopTab('UserFormPanel');
	},
	//按ID删除记录
	removeRs : function(id) {
		var orgId=this.depTreePanel.selectedNode.id;
		Ext.Msg.confirm('信息确认', '注意：要删除该用户与组织的关系么？', function(btn) {
			if (btn == 'yes') {
				Ext.Ajax.request({   
			        url:__ctxPath+ '/system/multiDelUserOrg.do',
			        method: 'GET',
			        scope:this,
			        params:{userId:id,orgId:orgId==null?0:orgId},
			        success: function ( result, request )    
			        {
				        var respText = Ext.util.JSON.decode(result.responseText);
			        	if(respText.success){
							Ext.ux.Toast.msg('操作信息','成功删除!');
			        	}
			        },   
			        failure: function ( result, request)
			        { }
				});
			}
		},this);
	}
	
});