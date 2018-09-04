/**
 * 岗位人员管理
 * @class PositionUserView
 * @extends Ext.Panel
 */
PositionUserView=Ext.extend(Ext.Panel,{
	constructor:function(conf){
		Ext.apply(this,conf);
		this.initUI();
		PositionUserView.superclass.constructor.call(this,{
			id:'PositionUserView',
			iconCls : 'menu-position',
			title:'岗位人员管理',
			layout:'border',
			items:[
				this.posTreePanel,
				this.userGridPanel
			]
		});
	},
	initUI:function(){
		this.posTreePanel=new zhiwei.ux.TreePanelEditor({
			width:230,
			region:'west',
			title:'岗位',
			url : __ctxPath+'/system/treePosition.do',
			scope : this,
			split:true,
			collapsible : true,
			autoScroll:true,
			contextMenuItems:[{
				text:'新建岗位',
				iconCls:'btn-add',
				scope:this,
				isShowSameLevel:false,
				handler:this.addPosition
			},{
				text:'编辑岗位',
				iconCls:'btn-edit',
				scope:this,
				isShowSameLevel:true,
				handler:this.editPosition
			},{
				text:'删除岗位',
				iconCls:'btn-del',
				scope:this,
				handler:this.delPosition
			},'-',{
				text:'加入员工',
				iconCls:'btn-add',
				scope:this,
				handler:this.addPosUser
			}
			],
			onclick:this.posTreeClick
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
					text:'删除岗位的员工',
					scope:this,
					handler:this.removePosUser
				}
			],
			title:'岗位人员列表',
			url:__ctxPath+'/system/posUsersAppUser.do',
			fields:[
				'userId','fullname','username','posNames'
			],
			columns:[
				{header:'账号',dataIndex:'username'},
				{header:'姓名',dataIndex:'fullname'},
				{header:'岗位',dataIndex:'posNames'},
				new Ext.ux.grid.RowActions({
					header : '管理',
					width : 100,
					actions : [
//							{
//								iconCls : 'btn-del',
//								qtip : '删除',
//								style : 'margin:0 3px 0 3px',
//								fn:function(rs){
//									if(rs.data.userId==-1){
//										return false;
//									}
//									return true;
//								}}, 
							{
								iconCls : 'btn-edit',
								qtip : '编辑',
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
		var baseParam = {'Q_username_S_LK':username,'Q_fullname_S_LK':fullname,'posId':this.posTreePanel.selectedNode.id};
		
		baseParam.start = 0;
		baseParam.limit = store.baseParams.limit;
		store.baseParams = baseParam;
		this.userGridPanel.getBottomToolbar().moveFirst();
	},
	//添加职位
	addPosition:function(){
		var posId=this.posTreePanel.selectedNode.id;
		new PositionForm({
			posSupId:posId,
			scope:this,
			callback:this.reloadPosition
		}).show();
	},
	//重新加zai樹菜單
	reloadPosition:function(){
		this.posTreePanel.root.reload();
	},
	//编辑职位
	editPosition:function(){
		var posId=this.posTreePanel.selectedNode.id;
		if(posId==0) return;
		new PositionForm({
			posId:posId,
			scope:this,
			callback:this.reloadPosition
		}).show();
	},
	//删除职位
	delPosition:function(){
		var posId=this.posTreePanel.selectedNode.id;
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
	//职位树点击
	posTreeClick:function(){
		var posId=this.posTreePanel.selectedNode.id;
		var store= this.userGridPanel.getStore();
		store.baseParams={posId:posId};
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
			default :
				break;
		}
	},
	//编辑用户信息
	editRs:function(record){
		App.clickTopTab('UserFormPanel_'+record.data.userId,{userId:record.data.userId,username:record.data.username});
	},
	addUser:function(){
		App.clickTopTab('UserFormPanel');
	},
	// 删除岗位的员工
	removePosUser : function() {
		var grid = this.userGridPanel;
		var selectRecords = grid.getSelectionModel().getSelections();
		if (selectRecords.length == 0) {
			Ext.ux.Toast.msg("信息", "请选择要删除的记录！");
			return;
		}
		if(this.posTreePanel.selectedNode==null){
			Ext.ux.Toast.msg("信息", "请选择岗位！");
			return;
		}
		var ids = Array();
		for (var i = 0; i < selectRecords.length; i++) {
			ids.push(selectRecords[i].data.userId);
		}
		Ext.Msg.confirm('删除操作', '注意：要删除该员工与岗位的关系吗？', function(btn) {
			if (btn == 'yes') {
				Ext.Ajax.request({
					url : __ctxPath + '/system/multiDelUserPosition.do',
					method : 'post',
					params : {
						ids : ids.toString(),
						posId:this.posTreePanel.selectedNode.id
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
	// 为岗位加入员工
	addPosUser: function(){
		var posId=0;
		var selNode=this.posTreePanel.selectedNode;
		if(selNode){
			posId=selNode.id;
		}
		if(posId==0){
			Ext.ux.Toast.msg('操作信息','请选择对应的岗位!');
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
					url:__ctxPath+'/system/saveUserPosition.do',
					params:{
						userIds:ids,
						posId:posId
					},
					success:function(resp,options){
						var store= this.userGridPanel.getStore();
						store.baseParams={posId:posId};
						this.userGridPanel.getBottomToolbar().moveFirst();
					}
				});
			}
		}).show();
	}
	
});