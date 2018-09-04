/**
 * 岗位人员管理
 * @class PositionUserView
 * @extends Ext.Panel
 */
CompanyOfUesrView=Ext.extend(Ext.Panel,{
	constructor:function(conf){
		Ext.apply(this,conf);
		this.initUI();
		CompanyOfUesrView.superclass.constructor.call(this,{
			iconCls:"menu-flowManager",
			title:'部门人员管理',
			id:'CompanyOfUesrView',
			layout:'border',
			items:[
				this.leftPanel,
				this.userGridPanel
			]
		});
	},
	initUI:function(){
/*		this.posTreePanel=new zhiwei.ux.TreePanelEditor({
			url : __ctxPath+'/system/getBranchCompanyOrganization.do',
			scope : this,
			filter:false,
			height : Ext.getBody().getViewSize().height-115,
			autoScroll : true,
			contextMenuItems:[{
				text:'新建部门',
				iconCls:'btn-add',
				scope:this,
				isShowSameLevel:false,
				handler:this.addPosition
			},{
				text:'编辑部门',
				iconCls:'btn-edit',
				scope:this,
				isShowSameLevel:true,
				handler:this.editPosition
			},{
				text:'删除部门',
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
		});*/
	    this.leftPanel=new Ext.Panel({
	    	title:'部门管理',
	    	region:'west',
	    	layout:'anchor',
	    	collapsible : true,
	    	split:true,
			width:350,
			height : 200,
			border:false,
			tbar:[{
						text:'添加门店',
						iconCls:'btn-add',
						scope:this,
					    handler:this.addShop
					},'-',
			       {
						text:'添加部门',
						iconCls:'btn-add',
						scope:this,
					    handler:this.addPosition
					},'-',
					{
						text:'编辑',
						iconCls:'btn-edit',
						scope:this,
					    handler:this.editPosition
					},'-',
					{
						text:'删除',
						iconCls:'btn-del',
						scope:this,
					    handler:this.delPosition
					}
            ],
			items:[{
				xtype : 'treePanelEditor',
				id : 'OrganizationType',
				split : true,
				rootVisible : false,
				border : false,
				treeType:'org',
				height : Ext.getBody().getViewSize().height-185,
				autoScroll : true,
				scope : this,
				url : __ctxPath+'/system/getBranchCompanyOrganization.do',
				onclick:this.posTreeClick,
   			    contextMenuItems : [{
					text:'新建部门',
					iconCls:'btn-add',
					scope:this,
					isShowSameLevel:false,
					handler:this.addPosition
				},{
					text:'编辑部门',
					iconCls:'btn-edit',
					scope:this,
					isShowSameLevel:true,
					handler:this.editPosition
				},{
					text:'删除部门',
					iconCls:'btn-del',
					scope:this,
					handler:this.delPosition
				},'-',{
					text:'加入员工',
					iconCls:'btn-add',
					scope:this,
					handler:this.addPosUser
				}
			]
			
			}]
		});
		if(this.userGridPanel){
		   this.userGridPanel.getBottomToolbar().moveFirst();
		}
		this.userGridPanel=new HT.GridPanel({
			rowActions : true,
			id:'branch_userGrid',
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
					iconCls:'btn-clear',
					text:'禁用账号',
					scope:this,
					handler:this.forbiddenPosUser
				},'-',{
					xtype:'button',
					iconCls:'btn-userable',
					text:'解除禁用',
					scope:this,
					handler:this.openPosUser
				}
			],
			title:'人员管理',
			url:__ctxPath+'/system/depBranchUsersAppUser.do',
			fields:[
				'userId','fullname','username','posNames','email','status','roleNames','department','key'
			],
			columns:[
			    {
					header : '状态',
					dataIndex : 'status',
					width : 30,
					renderer : function(value) {
						var str = '';
						if(value == '1'){//激活用户
							str += '<img title="激活" src="'+ __ctxPath +'/images/flag/customer/effective.png"/>'
						}else{//禁用用户
							str += '<img title="禁用" src="'+ __ctxPath +'/images/flag/customer/invalid.png"/>'
						}
						return str;
					}
			},{    
			       header:'账号',
			       dataIndex:'username',	
			       renderer : function(value) {
					     if(value.split("@").length>1){
					       return value.split("@")[0];
					     }
					     return value;
						 
				}
			},{
			       header:'姓名',
			       dataIndex:'fullname'
			},{
			       header:'邮箱',
			       dataIndex:'email'
			},{
			       header:'拥有角色',
			       dataIndex:'roleNames'
			},
				new Ext.ux.grid.RowActions({
					header : '管理',
					width : 100,
					actions : [
						 /*{
							iconCls : 'btn-del',
							qtip : '删除',
							style : 'margin:0 3px 0 3px',
							fn:function(rs){
								if(rs.data.userId==-1||(rs.data.key=='systemUser')){
									return false;
								}
								return true;
							}}, */
							{
								iconCls : 'btn-edit',
								qtip : '编辑',
								style : 'margin:0 3px 0 3px',
								fn:function(rs){
								if(rs.data.userId==-1||(rs.data.key=='systemUser')){
									return false;
								}
								return true;
							}
							}, 
							{
								iconCls : 'btn-password',
								qtip : '重置密码',
								style : 'margin:0 3px 0 3px'
						}, {
							iconCls : 'btn-relativeJob',
							qtip : '添加上下级',
							style : 'margin:0 3px 0 3px',
							fn:function(rs){
								if(rs.data.userId==-1||(rs.data.key=='systemUser')){
									return false;
								}
								return true;
							}
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
		var posTreePanel=Ext.getCmp('OrganizationType');
		var toolbar=this.userGridPanel.getTopToolbar();
		var fullname=toolbar.getCmpByName('fullname').getValue();
		var username=toolbar.getCmpByName('username').getValue();
		var store=this.userGridPanel.getStore();
		var departId=0;
		if(posTreePanel.selectedNode){
			departId=posTreePanel.selectedNode.id;
		}
		var baseParam = {'Q_username_S_LK':username,'Q_fullname_S_LK':fullname,'posId':departId};
		baseParam.start = 0;
		baseParam.limit = 25;
		store.baseParams = baseParam;
		this.userGridPanel.getBottomToolbar().moveFirst();
	},
	getAllParentNodeName:function(node){
	          var nonode = node.parentNode;  
		      var names = new Array();//存储id的数组  
		      var parentName="";
		      var depth = node.getDepth();//获得深度  
              for(var i=0; i<(depth-1); i++){ 
              	    names.push(nonode .text);  
		            nonode = nonode.parentNode;  
		      } 
		      for(var j=(names.length-1);j>=0;j--){
		      	  parentName+=names[j]+"_";
		      }
		      return parentName;
	},
	//添加职位
	addPosition:function(){
		var  parentName="";
		var  orgId="";
		var  orgText="";
		var  posTreePanel=Ext.getCmp('OrganizationType');
		var  leftSelectNode=posTreePanel.selectedNode;
		if(null!=leftSelectNode){
		    parentName=this.getAllParentNodeName(leftSelectNode);
		    orgId=posTreePanel.selectedNode.id;
		    orgText=posTreePanel.selectedNode.text;
			var pn="";//
		    pn=parentName+orgText;
			new DepartmentForm({'parentName':pn,'orgSupId':orgId,'callback':reloadData}).show();
		}
		else{
			   var url=__ctxPath+'/system/getLoginCompanyinfoOrganization.do';
		       Ext.Ajax.request({
					url:url,
					method:"post",
					success:function(response,opts){
					     var data = Ext.decode(response.responseText);
					     var isFalg=data.success;
					     if(isFalg){
					          var orgId=data.data.orgId;
					          var orgName=data.data.orgName;
					          pn=orgName;
							  new DepartmentForm({'parentName':pn,'orgSupId':orgId,'callback':reloadData}).show();
					     }     
					     else{
					     }
					}
  				})
		
		}
		var reloadPanel=posTreePanel;
	    function reloadData(){
	    	reloadPanel.root.reload();
		}
	},
	//重新加zai樹菜單
	reloadPosition:function(){
		Ext.getCmp('OrganizationType').root.reload();
	},
	//编辑职位
	editPosition:function(){
		var posTreePanel=Ext.getCmp('OrganizationType');
		var selectedNode=posTreePanel.selectedNode;
		if(null==selectedNode){
		   Ext.ux.Toast.msg('操作信息','请选择要修改的部门!');	
		   return;
		}
		var parentName=this.getAllParentNodeName(selectedNode);
		var orgId=posTreePanel.selectedNode.id;
		var orgText=posTreePanel.selectedNode.text;
		var orgType=posTreePanel.selectedNode.attributes.orgType;
		var orgSupId=1;
		var reloadPanel=posTreePanel;
	    function reloadDate(){
	    	reloadPanel.root.reload();
		}
		var pn=parentName+orgText;
		if(orgType==2){
			new DepartmentForm({'parentName':pn,'orgSupId':orgSupId,'callback':reloadDate,'orgId':orgId}).show();
		}else if(orgType==3){
			new ShopForm({'parentName':pn,'orgSupId':orgId,'callback':reloadDate,'orgId':orgId}).show();
		}
	},
	//删除部门
	delPosition:function(){
		var posTreePanel=Ext.getCmp('OrganizationType');
		var posId=posTreePanel.selectedNode.id;
		if(posId==0) return;
		if(this.userGridPanel.getStore().getTotalCount()>0){
			Ext.Msg.alert('操作信息','该部门下还有员工，请将该部门下的员工移到其他部门')
			return;
		}
		Ext.Msg.confirm('信息确认', '注意：删除该将会删除其下所有的子，您确认要删除所选吗？', function(btn) {
			if (btn == 'yes') {
				Ext.Ajax.request({
					url:__ctxPath+'/system/multiDelOrganization.do',
					method:'POST',
					scope:this,
					params:{
						ids:posId
					},
					success:function(response,options){
						Ext.ux.Toast.msg('操作信息','成功删除!');	
						this.reloadPosition.call(this);
					},
					failure : function(response,options) {
						Ext.ux.Toast.msg('操作信息','操作出错，请联系管理员！');
					}
				});
			}
		},this);
	},
	addShop:function(){
		var  parentName="";
		var  orgId="";
		var  orgText="";
		var  posTreePanel=Ext.getCmp('OrganizationType');
		var  leftSelectNode=posTreePanel.selectedNode;
		if(null!=leftSelectNode){
		    parentName=this.getAllParentNodeName(leftSelectNode);
		    orgId=posTreePanel.selectedNode.id;
		    orgText=posTreePanel.selectedNode.text;
			var pn="";//
		    pn=parentName+orgText;
			new ShopForm({'parentName':pn,'orgSupId':orgId,'callback':reloadData}).show();
		}
		else{
			   var url=__ctxPath+'/system/getLoginCompanyinfoOrganization.do';
		       Ext.Ajax.request({
					url:url,
					method:"post",
					success:function(response,opts){
					     var data = Ext.decode(response.responseText);
					     var isFalg=data.success;
					     if(isFalg){
					          var orgId=data.data.orgId;
					          var orgName=data.data.orgName;
					          pn=orgName;
							  new ShopForm({'parentName':pn,'orgSupId':orgId,'callback':reloadData}).show();
					     }     
					     else{
					     }
					}
  				})
		
		}
		var reloadPanel=posTreePanel;
	    function reloadData(){
	    	reloadPanel.root.reload();
		}
	},
	
	//职位树点击
	posTreeClick:function(){
		var posTreePanel=Ext.getCmp('OrganizationType');
		var  leftSelectNode=posTreePanel.selectedNode;
		var orgId=posTreePanel.selectedNode.id;
		var store= this.userGridPanel.getStore();
		store.baseParams={depId:orgId};
		this.userGridPanel.getBottomToolbar().moveFirst();
		var parentNode=posTreePanel.selectedNode.parentNode;
		var orgType=parentNode.attributes.orgType;
		var disPlayName="";
		if(null!=parentNode){
			  if(orgType==1){
			      disPlayName=parentNode.text+"_"+posTreePanel.selectedNode.text
			  }
			  else if(orgType==2){
			      disPlayName=parentNode.parentNode.text+"_"+parentNode.text+"_"+posTreePanel.selectedNode.text
			  }
			  else{
			     disPlayName=posTreePanel.selectedNode.text;
			  }
		}
		this.userGridPanel.setTitle("【"+disPlayName+"】人员管理");
		
		
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
			case 'btn-password' :
				this.resetPassword.call(this, record.data.userId);
				break;
			case 'btn-relativeJob': 
				this.addRelativeUser.call(this, record);
				
				break;
			default :
				break;
		}
	},
	//编辑用户信息
	editRs:function(record){
		var companyId=1;
		var grid = this.userGridPanel;
		App.clickTopTab('UserFormPanel_'+record.data.userId,{userId:record.data.userId,username:record.data.username,onlyShowDepart:true,gridObj:grid,listeners: {  
		                 'beforeclose': function(p){
		                 	if(p.depGridPanel.store.data.items.length==0){
		                 		Ext.ux.Toast.msg('操作','请为员工分配部门！');
                 				return false;
                 			}
		                 }  
		            }
});
	},
	addUser:function(){
		var  posTreePanel=Ext.getCmp('OrganizationType');
		var  leftSelectNode=posTreePanel.selectedNode;
		if(leftSelectNode){
		    orgType=leftSelectNode.attributes.orgType;
		    if(orgType!=2){
		    	Ext.ux.Toast.msg("操作信息", "请选择部门！");
		    }else{
		    	var parNode=this.getParentNode(leftSelectNode,'0');//第二个参数代表是查门店还是分公司id(0分公司,1门店)
		    	var shopNode=this.getParentNode(leftSelectNode,'1');//门店
		    	var grid = this.userGridPanel;
		    	var shopId="";
		    	if(shopNode){
		    		shopId=shopNode.attributes.id;
		    	}
		    	App.clickTopTab('UserFormPanel',{
		    		gridObj:grid,
		    		shopId:shopId,
		    		companyId:
		    		parNode.attributes.id,
		    		bmID:leftSelectNode.attributes.id,
		    		bmMC:leftSelectNode.attributes.text
		    	});
		    }
		}else{
			Ext.ux.Toast.msg("操作信息", "请选择要添加的部门！");
		}
	},
		getParentNode:function(node,flag){
		var parNode=node.parentNode;
		if(parNode){
			var att=parNode.attributes;
			if((att.orgType==0 || att.orgType==1 || att.orgType==2) && flag==0){//公司级别
				return parNode;
			}if(att.orgType==3 && flag==1){//门店级别
				return parNode;
			}else{
				return this.getParentNode(parNode,flag);
			}
		}else{
			return null;
		}
	},
    resetPassword:function(userId){
	   new setPasswordForm(userId);
    },
	// 删除的员工
	removeRs:function(userId){
		  	Ext.Msg.confirm('删除操作', '你确定要删除该用户吗?', function(btn) {
				if (btn == 'yes') {
					Ext.Ajax.request({
								url : __ctxPath + '/system/multiDelAppUser.do',
								method : 'post',
								params : {
									ids : userId
								},
								success : function(response) {
									var result = Ext.util.JSON.decode(response.responseText);
									if(result.msg == ''){
										Ext.ux.Toast.msg("操作信息", "用户删除成功");
									}else{
										Ext.ux.Toast.msg("操作信息", result.msg);
									}
									Ext.getCmp('branch_userGrid').getStore().reload();
								},
								failure : function() {
									Ext.ux.Toast.msg("操作信息", "用户删除失败");
								}
							});
				}
			});
	},
	forbiddenPosUser : function() {
		var posTreePanel=Ext.getCmp('OrganizationType');
		var grid = this.userGridPanel;
		var selectRecords = grid.getSelectionModel().getSelections();
		if (selectRecords.length == 0) {
			Ext.ux.Toast.msg("信息", "请选择要禁用的记录！");
			return;
		}
		
		var ids = Array();
		for (var i = 0; i < selectRecords.length; i++) {
			ids.push(selectRecords[i].data.userId);
		}
		Ext.Msg.confirm('禁用操作', '你确定要禁用该用户吗?', function(btn) {
			if (btn == 'yes') {
				Ext.Ajax.request({
					url : __ctxPath + '/system/forbiddenAppUser.do',
					method : 'post',
					params : {
						ids : ids
					},
					success : function(response) {
						var result = Ext.util.JSON.decode(response.responseText);
						if(result.msg == null){
							Ext.ux.Toast.msg("操作信息", "员工禁用成功");
						}else{
							Ext.ux.Toast.msg("操作信息", result.msg);
						}
						grid.getStore().reload();
					},
					failure : function() {
						Ext.ux.Toast.msg("操作信息", "员工禁用失败");
					}
				});
			}
		},this);
	},
		openPosUser : function() {
		var posTreePanel=Ext.getCmp('OrganizationType');
		var grid = this.userGridPanel;
		var selectRecords = grid.getSelectionModel().getSelections();
		if (selectRecords.length == 0) {
			Ext.ux.Toast.msg("信息", "请选择要解除禁用的记录！");
			return;
		}
		
		var ids = Array();
		for (var i = 0; i < selectRecords.length; i++) {
			ids.push(selectRecords[i].data.userId);
		}
		Ext.Msg.confirm('禁用操作', '你确定要解除禁用该用户吗?', function(btn) {
			if (btn == 'yes') {
				Ext.Ajax.request({
					url : __ctxPath + '/system/openForbiddenAppUser.do',
					method : 'post',
					params : {
						ids : ids
					},
					success : function(response) {
						var result = Ext.util.JSON.decode(response.responseText);
						if(result.msg == null){
							Ext.ux.Toast.msg("操作信息", "员工解除禁用成功");
						}else{
							Ext.ux.Toast.msg("操作信息", result.msg);
						}
						grid.getStore().reload();
					},
					failure : function() {
						Ext.ux.Toast.msg("操作信息", "员工解除禁用失败");
					}
				});
			}
		},this);
	},
	removePosUser : function() {
		var posTreePanel=Ext.getCmp('OrganizationType');
		var grid = this.userGridPanel;
		var selectRecords = grid.getSelectionModel().getSelections();
		if (selectRecords.length == 0) {
			Ext.ux.Toast.msg("信息", "请选择要删除的记录！");
			return;
		}
		if(posTreePanel.selectedNode==null){
			Ext.ux.Toast.msg("信息", "请选择相应部门！");
			return;
		}
		var ids = Array();
		for (var i = 0; i < selectRecords.length; i++) {
			ids.push(selectRecords[i].data.userId);
		}
		Ext.Msg.confirm('删除操作', '你确定要删除该用户吗?', function(btn) {
			if (btn == 'yes') {
				Ext.Ajax.request({
					url : __ctxPath + '/system/multiDelAppUser.do',
					method : 'post',
					params : {
						ids : ids.toString(),
						posId:posTreePanel.selectedNode.id
					},
					success : function(response) {
						var result = Ext.util.JSON.decode(response.responseText);
						//alert(response.responseText)
						if(result.msg == null){
							Ext.ux.Toast.msg("操作信息", "员工删除成功");
						}else{
							Ext.ux.Toast.msg("操作信息", result.msg);
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
	// 为加入员工
	addPosUser: function(){
		var posTreePanel=Ext.getCmp('OrganizationType');
		var posId=0;
		var selNode=posTreePanel.selectedNode;
		if(selNode){
			posId=selNode.id;
		}
		if(posId==0){
			Ext.ux.Toast.msg('操作信息','请选择对应的!');
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
						var store=Ext.getCmp('branch_userGrid').getStore();
						Ext.getCmp('branch_userGrid').getBottomToolbar().moveFirst();
						store.baseParams={posId:posId};
						store.reload();
						
					}
				});
			}
		  }).show();
		},	// 添加上下级
			addRelativeUser : function(record){
			new RelativeUserView({
				userId : record.data.userId, //用户编号
				username : record.data.username,
				depId : record.data.department.depId //部门编号
		}).show();
	}
	
});