/**
 * 系统组织架构的设置
 * @class OrgSettingView
 * @extends Ext.Panel
 */
 
OrgSettingView=Ext.extend(Ext.Panel,{
	constructor:function(conf){
		Ext.applyIf(this,conf);
		this.initUI();
		OrgSettingView.superclass.constructor.call(this,{
			id:'OrgSettingView',
			title:'系统组织架构设置',
			iconCls:'menu-OrgSetting',
			layout:'border',
			items:[
				this.leftPanel,
				this.centerPanel
			]
		});
	},
	//初始化UI
	initUI:function(){
		this.demStore=new Ext.data.ArrayStore({
			autoLoad:true,
			url:__ctxPath+'/system/comboDemension.do',
			fields:['id','name'],
			listeners:{
 				    	scope:this,
 				       'load':function(store){
 				       }
			}
		});
		//维度选择下拉
		this.demensionCombo=new Ext.form.ComboBox({
			displayField:'name',
			valueField:'id',
			editable : false,
			emptyText:'所有维度',
			mode : 'local',
			width:180,
			triggerAction : 'all',
			store:this.demStore,
			listeners:{
			         scope: this,
			         'select':this.demensionSel
			}
		});
		//组织树Panel
		this.orgPanel=new zhiwei.ux.TreePanelEditor({
			treeType:'org',
			title:'组织',
			url : __ctxPath+'/system/treeOrganization.do',
			scope:this,
			autoScroll:true,
			onclick:this.orgTreeClick,
			contextMenuItems:[{
					text:'新建组织',
					iconCls:'btn-add',
					scope:this,
					handler:this.addOrg
				},{
					text:'编辑组织',
					iconCls:'btn-edit',
					scope:this,
					handler:this.editOrg
				},{
					text:'删除组织',
					iconCls:'btn-del',
					scope:this,
					handler:this.delOrg
				},'-',{
					text:'加入员工',
					iconCls:'btn-add',
					scope:this,
					handler:this.addUser
				}
			]
		});
		//岗位树Panel
		this.posPanel=new zhiwei.ux.TreePanelEditor({
			treeType:'pos',
			id:'posTree',
			title:'岗位',
			url : __ctxPath+'/system/treePosition.do',
			scope : this,
			autoScroll:true,
			contextMenuItems:[{
				text:'新建岗位',
				iconCls:'btn-add',
				scope:this,
				handler:this.addPosition
			},{
				text:'编辑岗位',
				iconCls:'btn-edit',
				scope:this,
				handler:this.editPosition
			},{
				text:'删除岗位',
				iconCls:'btn-del',
				scope:this,
				handler:this.delPosition
			}
			],
			onclick:this.posTreeClick
		});
	    
	    this.cenPanel = this.getOrgDetailPanel();
	    
	    var initFlag = true;
		//放置组织，全部，岗位等panel
		this.demTabPanel=new Ext.TabPanel({
			activeTab:0,
			region:'center',
			border:false,
			items:[
				this.orgPanel,
				this.posPanel
			],
            listeners:{
            	tabchange: function(mainTab, curTab){
	            	if(!initFlag){
	            		if(curTab.treeType=='org'){
							this.centerPanel.remove(this.cenPanel,false);
							this.cenPanel = this.getOrgDetailPanel();
							this.centerPanel.add(this.cenPanel);
							this.centerPanel.doLayout();
	            		}
	            		if(curTab.treeType=='pos'){
							this.centerPanel.remove(this.cenPanel,false);
							this.cenPanel = this.getPosDetailPanel();
							this.centerPanel.add(this.cenPanel);
	            			this.centerPanel.doLayout();
	            		}
	            	}
	            	initFlag=false;
    			},scope:this
            }
		});
		
		this.leftPanel=new Ext.Panel({
			collapsible : true,
			split : true,
			region:'west',
			width:180,
			title:'组织架构设置',
			layout:'fit',
			tbar:[{
				text:'添加维度',
				iconCls:'btn-add',
				scope:this,
				handler:this.addDemension
			}],
			items:[{
					xtype:'panel',
					layout:'border',
					border:false,
					items:[
						{
							xtype:'panel',
							region:'north',
							border:false,
							autoHeight:true,
							layout:'fit',
							items:this.demensionCombo
						},
						this.demTabPanel
					]
				}
			]
		});
		
		//右边Panel
		this.centerPanel=new Ext.Panel({
			id:'centerPanel',
			region:'center',
			title:'组织属性设置',
			layout:'fit',
			items:[this.cenPanel]
		});
		
	},
	//取得某个组织的人员列表
	getOrgUsersGrid:function(depId){
		if(!depId){
			depId='';
		}
		this.userGridPanel=new HT.GridPanel({
			id:'orgUserGrid',
			tbar:[{
				xtype:'button',
				text:'加入员工',
				iconCls:'btn-add',
				scope:this,
				handler:this.addUser
			},'-',{
				xtype:'button',
				text:'删除员工',
				iconCls:'btn-del',
				scope:this,
				handler: this.removeUserOrg
			},'-',{
				text:'添加新员工',
				iconCls:'btn-users',
				scope:this,
				handler:this.addNewUser
			}
			],
			region : 'center',
			rowActions : true,
			title:'组织人员列表',
			url:__ctxPath+'/system/depUsersAppUser.do',
			baseParams:{
				depId:depId
			},
			fields:[
				'userId','fullname','username'
			],
			columns:[{header:'账号',dataIndex:'username'},{header:'姓名',dataIndex:'fullname'},
				new Ext.ux.grid.RowActions({
					header : '管理',
					width : 100,
					actions :[
//						{
//							iconCls : 'btn-del',
//							qtip : '删除',
//							style : 'margin:0 3px 0 3px',
//							fn:function(rs){
//								if(rs.data.userId==-1){
//									return false;
//								}
//								return true;
//						}}, 
						{
							iconCls : 'btn-edit',
							qtip : '编辑',
							style : 'margin:0 3px 0 3px'
						}],
						listeners : {
						scope : this,
							'action' : this.onRowAction
					}})
			]
		});
		return this.userGridPanel;
	},
	//添加组织的员工
	addUser:function(){
		var orgId=0;
		var selNode=this.orgPanel.selectedNode;
		if(selNode){
			orgId=selNode.id;
		}
		if(orgId==0){
			Ext.ux.Toast.msg('操作信息','请选择对应的组织!');
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
						this.userGridPanel.getStore().reload();
					}
				});
			}
		}).show();
	},
	//添加新用户
	addNewUser:function(){
		App.clickTopTab('UserFormPanel');
	},
	
	//行编辑
	onRowAction:function(grid, record, action, row, col){
		switch (action) {
//			case 'btn-del' :
//				this.removeRs.call(this, record.data.userId);
//				break;
			case 'btn-edit' :
				this.editRs.call(this, record);
				break;
			default :
				break;
		}
	},
	//编辑用户信息
	editRs:function(record){
		App.clickTopTab('UserFormPanel_'+record.data.userId,{userId:record.data.userId,username:record.data.username});
	},
	// 删除部门员工
	removeUserOrg : function() {
		var grid = Ext.getCmp("orgUserGrid");
		var selectRecords = grid.getSelectionModel().getSelections();
		if (selectRecords.length == 0) {
			Ext.ux.Toast.msg("信息", "请选择要删除的记录！");
			return;
		}
		if(this.orgPanel.selectedNode==null){
			Ext.ux.Toast.msg("信息", "请选择组织！");
			return;
		}
		var ids = Array();
		for (var i = 0; i < selectRecords.length; i++) {
			ids.push(selectRecords[i].data.userId);
		}
		Ext.Msg.confirm('删除操作', '注意：要删除该员工与组织的关系吗？', function(btn) {
			if (btn == 'yes') {
				Ext.Ajax.request({
					url : __ctxPath + '/system/multiDelUserOrg.do',
					method : 'post',
					params : {
						ids : ids.toString(),
						orgId:this.orgPanel.selectedNode.id
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
	//添加维度
	addDemension:function(){
		new DemensionForm({
				callback:this.reloadDemStore,
				scope:this
			}
		).show();	
	},
	//维度下拉选择
	demensionSel:function(combo,record,index){
		var demId=combo.getValue();
		this.orgPanel.loader=new Ext.tree.TreeLoader(
	    {
	        baseParams:{demId:demId},
	        dataUrl: __ctxPath+'/system/treeOrganization.do',
	        requestMethod:'GET'
	    });
	    this.orgPanel.selectedNode=null;
	    this.orgPanel.root.reload();
	},
	//重新加载维度下接列表
	reloadDemStore:function(){
		this.demStore.reload();
	},
	//重新加载岗位树
	reloadPosition:function(){
		this.posPanel.root.reload();
	},
	//重新加载组织树
	reloadOrganization:function(){
		this.orgPanel.root.reload();
	},
	//添加岗位
	addPosition:function(){
		var posId=this.posPanel.selectedNode.id;
		new PositionForm({
			posSupId:posId,
			isShowSameLevel:false,
			scope:this,
			callback:this.reloadPosition
		}).show();
	},
	//编辑岗位
	editPosition:function(){
		var posId=this.posPanel.selectedNode.id;
		if(posId==0) return;
		new PositionForm({
			posId:posId,
			isShowSameLevel:true,
			scope:this,
			callback:this.reloadPosition
		}).show();
	},
	//删除岗位
	delPosition:function(){
		var posId=this.posPanel.selectedNode.id;
		if(posId==0) return;
		Ext.Msg.confirm('信息确认', '注意：删除该岗位将会删除其下所有的子岗位，您确认要删除所选岗位吗？', function(btn) {
			if (btn == 'yes') {
				Ext.Ajax.request({
					url:__ctxPath+'/system/multiDelPosition.do',
					method:'POST',
					scope:this,
					params:{
						ids:posId
					},
					success:function(response,options){
						Ext.ux.Toast.msg('操作信息','成功删除岗位!');	
						this.reloadPosition.call(this);
					},
					failure : function(response,options) {
						Ext.ux.Toast.msg('操作信息','操作出错，请联系管理员！');
					}
				});
			}
		},this);
	},
	//岗位树节点点击
	posTreeClick:function(){
		var posId=this.posPanel.selectedNode.id;
		this.centerPanel.remove(this.cenPanel,false);
		this.cenPanel = this.getPosDetailPanel.call(this,posId);
		this.centerPanel.add(this.cenPanel);
		this.centerPanel.doLayout();
	},
	//取得岗位明细
	getPosDetailPanel:function(posId){
		if(!posId){posId=0;}
		this.posGridPanel = new HT.GridPanel({
					isShowTbar:false,
					title:'岗位列表',
					region : 'center',
					url : __ctxPath + "/system/underlingListPosition.do?posId="+posId,
					fields : [{name : 'orgId',
								type : 'int'}, 'posName', 'posDesc', 'posSupId', 'path', 'depth', 'orgId'],
					columns : [{
								header : 'posId',
								dataIndex : 'posId',
								hidden : true
							}, {
								header : '岗位名称',
								dataIndex : 'posName'
							}, {
								header : '描述',
								dataIndex : 'posDesc'
							}, {
								header : '上级岗位',
								dataIndex : 'posSupId'
							}, {
								header : '路径',
								dataIndex : 'path'
							}, {
								header : '级别',
								dataIndex : 'depth'
							}, {
								header : '所属组织',
								dataIndex : 'orgId'
							}]
		});
		this.posDetailPanel=new Ext.Panel({
			border:false,
			region:'north',
			title:'岗位详细信息',
			height:180,
			autoScroll:true,
			autoLoad:{
				url:__ctxPath+'/system/detailPosition.do?posId='+posId,
				nocache:true
			}
		});
		var detailPanel=new Ext.Panel({
				id : 'posPanel',
				tbar:[{
						text:'添加角色',
						iconCls:'btn-add',
						scope:this,
						handler:this.addRole.createDelegate(this,[posId])
					},'-',
					{
						text:'添加岗位',
						iconCls:'btn-add',
						scope:this,
						handler:this.addPosition
					}
				],
				posId:posId,
				border:false,
				layout:'border',
				items:[
					this.posDetailPanel,
					this.posGridPanel
				]
			}
		);
		return detailPanel;
	},
	//组织树节点点击
	orgTreeClick:function(){
		var orgId=this.orgPanel.selectedNode.id;
		var orgType=this.orgPanel.selectedNode.attributes.orgType;
		var orgName=this.orgPanel.selectedNode.attributes.text;
		this.centerPanel.remove(this.cenPanel,false);
		this.cenPanel = this.getOrgDetailPanel.call(this,orgId,orgName,orgType);
		this.centerPanel.add(this.cenPanel);
		this.centerPanel.doLayout();
	},
	//取得组织明细
	getOrgDetailPanel:function(orgId,orgName,orgType){
		if(!orgId){orgId=0;}
		this.orgDetailPanel=new Ext.Panel({
			orgId:orgId,
			border:false,
			autoScroll:true,
			height:260,
			region:'north',
			autoLoad:{
				url:__ctxPath+'/system/detailOrganization.do?orgId='+orgId,
				nocache:true
			}
		});
		var orgGridPanel=this.getOrgUsersGrid.call(this,orgId);
		var orgPanel=new Ext.Panel({
				id:'orgPanel',
				border:false,
				layout:'border',
				tbar:[
					{
						text:'新建组织',
						iconCls:'btn-add',
						scope:this,
						handler:this.addOrg
					}
				],
				items:[
					this.orgDetailPanel,
					orgGridPanel
				]
			}
		);
		
		//若为行政维度，可以允许添加公司及部门
		//var demId=this.demensionCombo.getValue();
		
		if(orgType==0){//集团下只能添加公司
			orgPanel.getTopToolbar().add([
					'-',
					{
						text:'添加公司',
						iconCls:'btn-add',
						scope:this,
						handler:this.addCompany.createDelegate(this,[orgId,orgName])
					},
					'-',
					{
						text:'添加部门',
						iconCls:'btn-add',
						scope:this,
						handler:this.addDepartment.createDelegate(this,[orgId,orgName])
					}
			]);
	
		}else if(orgType==1 || orgType==2){//公司或部门下才允许添加部门
			orgPanel.getTopToolbar().add([
					'-',
					{
						text:'添加部门',
						iconCls:'btn-add',
						scope:this,
						handler:this.addDepartment.createDelegate(this,[orgId,orgName])
					}
			]);
		}
		
		
		return orgPanel;
	},
	
	addCompany:function(orgId,orgName){
		var selectedNode=this.orgPanel.selectedNode;
		//var orgId=this.orgPanel.selectedNode.id;
		new CompanyWin({
			orgSupId:orgId,
			supOrgName:orgName,
			scope:this,
			callback:this.addCompanyCallback
		}).show();
	},
	
	/**
	 * 公司添加回调
	 */
	addCompanyCallback:function(){
		this.orgPanel.root.reload();
	},
	/**
	 * 添加部门
	 */
	addDepartment:function(orgId,orgName){
		new DepartmentWin({
			orgSupId:orgId,
			supOrgName:orgName,
			scope:this,
			callback:this.addDepartmentCallback
		}).show();
	},
	/**
	 * 添加部门回调
	 */
	addDepartmentCallback:function(){
		this.orgPanel.root.reload();
	},
	//添加角色
	addRole:function(posId){
		if(posId==0){
			Ext.ux.Toast.msg('操作信息','请选择对应的岗位!');
			return;
		}
		new RoleDialog({
			scope:this,
			callback:this.addRoleCallBack
		}).show();
	},
	
	//添加角色执行的操作
	addRoleCallBack:function(roleIds,roleNames){
		
		var posId=this.posPanel.selectedNode.id;
		
		Ext.Ajax.request({
			url:__ctxPath+'/system/addRolePosition.do',
			params:{
				posId:posId,
				roleIds:roleIds
			},
			scope:this,
			success:function(response,options){
				Ext.ux.Toast.msg('操作信息','成功为该岗位添加角色！');
				this.posDetailPanel.getUpdater().update(__ctxPath+'/system/detailPosition.do?posId='+posId);
			}
		});
	},
	//新建组织
	addOrg:function(){
		if(this.orgPanel.selectedNode==null){
			Ext.ux.Toast.msg('操作信息','请选择左边的组组织树！');
			return;
		}
		var orgId=this.orgPanel.selectedNode.id;
		var orgType=this.orgPanel.selectedNode.attributes.orgType;

		new OrganizationForm({
			orgSupId:orgId,
			demId:this.demId,
			orgType: orgType,
			isAdd:true,
			scope:this,
			callback:this.reloadOrganization
		}).show();
	},
	//编辑组织
	editOrg:function(){
		var orgId=this.orgPanel.selectedNode.id;
		if(orgId==0) return;
		var orgType=this.orgPanel.selectedNode.attributes.orgType;
		new OrganizationForm({
			orgId:orgId,
			orgType: orgType,
			isAdd:false,
			scope:this,
			callback:this.reloadOrganization
		}).show();
	},
	//删除组织
	delOrg:function(){
		var dem = this.orgPanel.selectedNode.attributes.orgDem;
		if(dem==1){
			Ext.ux.Toast.msg('操作信息','行政维度的组织不能删除!');
			return;
		}
		var orgId=this.orgPanel.selectedNode.id;
		if(orgId==0) return;
		Ext.Msg.confirm('信息确认', '注意：删除该组织将会删除其下所有的子组织，您确认要删除所选组织吗？', function(btn) {
			if (btn == 'yes') {
				Ext.Ajax.request({
					url:__ctxPath+'/system/multiDelOrganization.do',
					method:'POST',
					scope:this,
					params:{
						ids:orgId
					},
					success:function(response,options){
						Ext.ux.Toast.msg('操作信息','成功删除该组织!');	
						this.reloadOrganization.call(this);
					},
					failure : function(response,options) {
						Ext.ux.Toast.msg('操作信息','操作出错，请联系管理员！');
					}
				});
			}
		},this);
	}
});