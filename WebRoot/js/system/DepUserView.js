/**
 * 公司部门人员管理
 * @class DepUserView
 * @extends Ext.Panel
 */
DepUserView=Ext.extend(Ext.Panel,{
	constructor:function(conf){
		Ext.apply(this,conf);
		this.initUI();
		DepUserView.superclass.constructor.call(this,{
			id:'DepUserView',
			title:'部门人员管理',
			layout:'border',
			iconCls:'menu-department',
			items:[
				this.depTreePanel,
				this.userGridPanel
			]
		});
	},
	initUI:function(){
		this.depTreePanel=new zhiwei.ux.TreePanelEditor({
			width:230,
			region:'west',
			title:'部门',
			url : __ctxPath+'/system/treeOrganization.do?demId=1',
			scope : this,
			split:true,
			collapsible : true,
			autoScroll:true,
			contextMenuItems:[{
				text:'新建公司或组织',
				iconCls:'btn-add',
				scope:this,
				handler:this.addDep
			},{
				text:'编辑新建公司或组织',
				iconCls:'btn-edit',
				scope:this,
				handler:this.editDep
			},'-',{
				text:'加入员工',
				iconCls:'btn-add',
				scope:this,
				handler:this.addDepUser
			}
//			,{
//				text:'删除新建公司或组织',
//				iconCls:'btn-del',
//				scope:this,
//				handler:this.delDep
//			}
			],
			onclick:this.depTreeClick
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
				},'-',{
					xtype:'button',
					iconCls:'btn-del',
					text:'删除部门的员工',
					scope:this,
					handler:this.removeUserOrg
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
					actions : [
//						{
//						iconCls : 'btn-del',
//						qtip : '删除',
//						style : 'margin:0 3px 0 3px',
//						fn:function(rs){
//							if(rs.data.userId==-1){
//								return false;
//							}
//							return true;
//						}
//					}, 
					{
						iconCls : 'btn-edit',
						qtip : '编辑',
						style : 'margin:0 3px 0 3px'
					}, {
						iconCls : 'btn-add',
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
		var departId=null;
		if(null!=this.depTreePanel.selectedNode){
			departId=this.depTreePanel.selectedNode.id;
		}
		var baseParam = {'Q_username_S_LK':username,'Q_fullname_S_LK':fullname,'depId':departId};
		
		baseParam.start = 0;
		baseParam.limit = store.baseParams.limit;
		store.baseParams = baseParam;
		this.userGridPanel.getBottomToolbar().moveFirst();
	},
	//添加部门
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
	//重新加载树
	reloadDep:function(){
		this.depTreePanel.root.reload();
	},
	//编辑职位
	editDep:function(){
		var depId=this.depTreePanel.selectedNode.id;
		if(depId==0) return;
		new DepForm({
			depId:depId,
			scope:this,
			callback:this.reloadDep
		}).show();
	},
	//删除职位
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
		App.clickTopTab('UserFormPanel_'+record.data.userId,{userId:record.data.userId,username:record.data.username});
	},
	addUser:function(){
		App.clickTopTab('UserFormPanel');
	},
	// 删除部门员工
	removeUserOrg : function() {
		var grid = this.userGridPanel;
		var selectRecords = grid.getSelectionModel().getSelections();
		if (selectRecords.length == 0) {
			Ext.ux.Toast.msg("信息", "请选择要删除的记录！");
			return;
		}
		if(this.depTreePanel.selectedNode==null){
			Ext.ux.Toast.msg("信息", "请选择组织！");
			return;
		}
		var ids = Array();
		for (var i = 0; i < selectRecords.length; i++) {
			ids.push(selectRecords[i].data.userId);
		}
		Ext.Msg.confirm('删除操作', '注意：要删除该员工与部门的关系吗？', function(btn) {
			if (btn == 'yes') {
				Ext.Ajax.request({
					url : __ctxPath + '/system/multiDelUserOrg.do',
					method : 'post',
					params : {
						ids : ids.toString(),
						orgId:this.depTreePanel.selectedNode.id
					},
					success : function(response) {
						var result = Ext.util.JSON.decode(response.responseText);
						if(result.msg == null){
							Ext.ux.Toast.msg("操作信息", "员工删除成功");
						}
						grid.getStore().reload();
					},
					failure : function() {
						Ext.ux.Toast.msg("操作信息", "员工删除失败");
					}
				});
			}
		},this);
	},
	//添加组织的员工
	addDepUser:function(){
		var orgId=0;
		var selNode=this.depTreePanel.selectedNode;
		if(selNode){
			orgId=selNode.id;
		}
		if(orgId==0){
			Ext.ux.Toast.msg('操作信息','请选择对应的部门!');
			return;
		}
		//弹出用户选择器，根据当前选择的节点，把用户加入该组织或部门
		new UserDialog({
			title:'加入新用户',
			scope:this,
			single:false,
			callback:function(ids,names){
				Ext.Ajax.request({
					method:'POST',
					scope:this,
					url:__ctxPath+'/system/addOrgsUserOrg.do',
					params:{
						userIds:ids,
						orgId:orgId
					},
					success:function(resp,options){
						var store= this.userGridPanel.getStore();
						store.baseParams={depId:orgId};
						this.userGridPanel.getBottomToolbar().moveFirst();
					}
				});
			}
		}).show();
	}
});