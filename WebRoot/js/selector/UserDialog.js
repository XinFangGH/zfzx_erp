/**
 * 用户选择器
 * @class UserDialog
 * @extends Ext.Window
 */
UserDialog=Ext.extend(Ext.Window,{
	constructor:function(conf){
		Ext.applyIf(this,conf);
		
		this.scope=this.scope?this.scope:this;
		//默认为多单选择用户
		this.single=this.single!=null?this.single:true;
		this.companyId=this.companyId?this.companyId:"";
		this.initUI();
		UserDialog.superclass.constructor.call(this,{
			title:this.title?this.title:'用户选择器',
			height:450,
			width:630,
			maximizable : true,
			modal:true,
			layout:'border',
			items:[
				this.westPanel,
				this.searchPanel,
				this.gridPanel
			],
			buttonAlign:'center',
			buttons:[
			{
				text:'确定',
				iconCls:'btn-ok',
				scope:this,
				handler:this.confirm
			},{
				text:'取消',
				iconCls:'btn-cancel',
				scope:this,
				handler:this.close
			},{
				text : '发起人',
				id:'startUser',
				iconCls : 'menu-subuser',
				scope : this,
				handler : function(){
					this.callback.call(this, '__start', '[发起人]');
					this.close();
				},
				disabled:true
			}
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
		var orgId=this.orgTreePanel.selectedNode.id;//app_user depId字段 add by gaoqingrui 
		var orgType=this.orgTreePanel.selectedNode.attributes.orgType
		var store = this.gridPanel.getStore();
		var baseParams=null;
		if(orgId!=0){	//若orgId==0,则代表为所有用户
			//baseParams = {'Q_orgs.orgId_L_EQ':orgId};
			baseParams={'Q_orgId_L_EQ':orgId,orgType:orgType,searchMethod:'company'}
		}else{
			baseParams={};
		}
		baseParams.start = 0;
		baseParams.limit = store.baseParams.limit;
		store.baseParams=baseParams;
		this.gridPanel.getBottomToolbar().moveFirst();
	},
	//按职位查找用户
	posTreeClick:function(){
	 	var posId=this.posTreePanel.selectedNode.id;
		var store = this.gridPanel.getStore();
		var baseParams=null;
		if(posId!=0){	//若orgId==0,则代表为所有用户
			baseParams = {'Q_positions.posId_L_EQ':posId};
		}else{
			baseParams={};
		}
		baseParams.start = 0;
		baseParams.limit = store.baseParams.limit;
		store.baseParams=baseParams;
		this.gridPanel.getBottomToolbar().moveFirst();
	},
	//按角色查找用户
	roleTreeClick:function(){
		var roleId=this.rolePanel.selectedNode.id;
		var leaf=this.rolePanel.selectedNode.leaf;
		
		var store = this.gridPanel.getStore();
		var baseParams=null;
		if(roleId!=0){	//若orgId==0,则代表为所有用户
			baseParams = {'Q_roles.roleId_L_EQ':roleId,searchMethod:'role',leaf:leaf};
		}else{
			baseParams={};
		}
		baseParams.start = 0;
		baseParams.limit = store.baseParams.limit;
		store.baseParams=baseParams;
		this.gridPanel.getBottomToolbar().moveFirst();
	},
	//查找所有在线用户
	onlineClick:function(){
		
	},
	demensionSel:function(combo,record,index){
		var demId=combo.getValue();
		var companyId=this.companyId;
		this.orgTreePanel.loader=new Ext.tree.TreeLoader(
	    {
	        baseParams:{demId:demId,companyId:companyId},
	        dataUrl: __ctxPath+'/system/treeCompanyOrganization.do',
	        requestMethod:'GET'
	    });
	    this.orgTreePanel.selectedNode=null;
	    this.orgTreePanel.root.reload();
	},
	/**
	 * 初始化UI
	 */
	initUI:function(){
		this.demStore=new Ext.data.ArrayStore({
			autoLoad:true,
			url:__ctxPath+'/system/comboDemension.do',
			fields:['id','name']
		});
		//维度选择下拉
		this.demensionCombo=new Ext.form.ComboBox({
			displayField:'name',
			valueField:'id',
			editable : false,
			emptyText:'所有维度',
			mode : 'local',
			triggerAction : 'all',
			store:this.demStore,
			listeners:{
			         scope: this,
			         'select':this.demensionSel
			}
		});
		//按组织架构创建
		var orgTreePanelUrl=__ctxPath+'/system/treeOrganization.do?type='+this.type+'&branchCompanyId='+this.branchCompanyId;
		if(this.companyId!=""){
			orgTreePanelUrl=__ctxPath+'/system/treeCompanyOrganization.do?companyId='+this.companyId
		}
		//组织树Panel
		this.orgTreePanel=new zhiwei.ux.TreePanelEditor({
			border:false,
			url :orgTreePanelUrl,
			region:'center',
			scope:this,
			autoScroll:true,
			onclick:this.orgTreeClick
		});
		//岗位树Panel
		this.posTreePanel=new zhiwei.ux.TreePanelEditor({
			title:'按岗位查找',
			border:false,
			url : __ctxPath+'/system/treePosition.do',
			scope : this,
			autoScroll:true,
			onclick:this.posTreeClick
		});
		
		this.orgPanel=new Ext.Panel({
			border:false,
			title:'按组织架构查找',
			layout:'fit',
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
						this.orgTreePanel
					]
				}
			]
		});
		//__ctxPath + '/system/treeAppRole.do'
		
		var roleUrl= __ctxPath + '/system/treeAppRole.do?type='+this.type;
		if(this.companyId!=""){
			roleUrl=__ctxPath + '/system/treeCompanyAppRole.do?type='+this.type+"&companyId="+this.companyId;
		}
		this.rolePanel=new zhiwei.ux.TreePanelEditor({
			border:false,
			title:'按角色查找',
			url : roleUrl,
			scope:this,
			autoScroll:true,
			onclick:this.roleTreeClick
		});
		
		this.onlinePanel = new Ext.Panel({
			collapsible:false,
			border:false,
			autoScroll : true,
			iconCls : 'online-user',
			title : '在线人员  ',
			listeners : {
				scope:this,
				'expand' : this.onlineClick
			}
		});
		
		//按逻辑代码创建
		
	 	this.westPanel=new Ext.Panel({
	 		split:true,
	 		collapsible : true,
			split : true,
			region:'west',
			header:false,
	 		//title:'导航',
	 		width:185,
	 		layout : 'accordion',
	 		collapsible : true,
	 		items:[this.orgPanel,/*this.posTreePanel,*/this.rolePanel/*,this.onlinePanel,*/]
	 	});
		//搜索用户列
	 	this.searchPanel=new HT.SearchPanel({
	 		region:'north',
	 		layout : 'form',
			colNums : 2,
			items : [{
				fieldLabel:'姓名',
				xtype:'textfield',
				flex:1,
				name:'Q_fullname_S_LK'
			},{
				xtype:'button',
				iconCls:'btn-search',
				text:'查询',
				scope:this,
				handler:function(){
					$search({
							searchPanel : this.searchPanel,
							gridPanel : this.gridPanel
					});
				}
			}]
	 	});
	 	//可选择的用户列表
	 	this.gridPanel=new HT.GridPanel({
	 		singleSelect:this.single,
	 		title:'可选用户',
	 		region:'center',
	 		isShowTbar:false,
	 		url:__ctxPath + '/system/dialogAppUser.do?curDep=true&type='+this.type+"&companyId="+this.companyId,//默认查找当前用户所在部门的用户
	 		fields :[{name : 'userId',type : 'int'}, 'fullname', 'title', 'mobile','userNumber'],
	 		columns:[
	 			{
					header : "用户名",
					dataIndex : 'fullname',
					renderer : function(value,meta,record){
						var title = record.data.title;
						if(title == 1)
							return '<img src="'+__ctxPath+'/images/flag/man.png"/>&nbsp;' + value;
						else
							return '<img src="'+__ctxPath+'/images/flag/women.png"/>&nbsp;' + value;
					},
					width : 60
				}, {
					width : 50,
						header : '编号',
						dataIndex : 'userNumber'
					}
	 		]
	 	});

	 	if(!this.single){
	 		
	 		this.gridPanel.addListener('rowclick',this.gridPanelRowClick,this);
	 		
	 		//已选的用户列表
		 	this.selGridPanel = new HT.GridPanel({
		 		title:'已选用户(双击行移除)',
		 		split:true,
				isShowTbar:false,
				region : 'east',
				width : 160,
				showPaging:false,
				autoScroll : true,
				store : new Ext.data.ArrayStore({
	    			fields : ['userId', 'fullname','title']
				}),
				trackMouseOver : true,
				columns : [{
					header : "用户名",
					dataIndex : 'fullname',
					renderer : function(value,meta,record){
						var title = record.data.title;
						if(title == 1)
							return '<img src="'+__ctxPath+'/images/flag/man.png"/>&nbsp;' + value;
						else if(title == 0)
							return '<img src="'+__ctxPath+'/images/flag/women.png"/>&nbsp;' + value;
						else
							return value;
					}
				}],
				listeners:{
		 			scope:this,
		 			'rowdblclick':this.selGridPanelRowDbClick
		 		}
			}); // end of this selectedUserGrid
		 	
		 	if(this.userIds&&this.userIds.length>0){
				var selStore = this.selGridPanel.getStore();
				var arrUserIds = this.userIds.split(',');
				var arrUserName = this.userName.split(',');
				for(var index=0;index<arrUserIds.length;index++){
					var newData = {userId: arrUserIds[index],fullname: arrUserName[index],title:3};
					var newRecord = new selStore.recordType(newData);
					this.selGridPanel.stopEditing();
					selStore.add(newRecord);
				}
		 	}
	 	}
	 	
	 	
		
	},//end of initUI function
	selGridPanelRowDbClick:function(){
		var store = this.selGridPanel.getStore();
		var rows = this.selGridPanel.getSelectionModel().getSelections();
		for(var i =0; i<rows.length; i++){
			this.selGridPanel.stopEditing();
			store.remove(rows[i]);
		}
	},
	/**
	 * 用户GridPanel（中间的Grid)行点击
	 * @param {} grid
	 * @param {} rowIndex
	 * @param {} e
	 */
	gridPanelRowClick:function(grid,rowIndex,e){
		var selStore = this.selGridPanel.getStore();
		var rows = this.gridPanel.getSelectionModel().getSelections();
		for(var i= 0 ; i<rows.length ; i++){
			var userId = rows[i].data.userId;
			var fullname = rows[i].data.fullname;
			var title=rows[i].data.title;
			var isExist = false;
			//查找是否存在该记录
			for(var j=0;j<selStore.getCount();j++){
				if(selStore.getAt(j).data.userId == userId){
					isExist = true;
					break;
				}
			}
			if(!isExist){
				var newData = {userId : userId,fullname : fullname,title:title};
				var newRecord = new selStore.recordType(newData);
				this.selGridPanel.stopEditing();
				selStore.add(newRecord);
			}
		}
	},
	/**
	 * 选择用户
	 */
	confirm:function(){
		var userIds = '';
		var fullnames = '';
		//返回的用户集
		var users=[];
		if(this.single){//选择单个用户
			var rows = this.gridPanel.getSelectionModel().getSelections();
			for (var i = 0; i < rows.length; i++) {
				if (i > 0) {
					userIds += ',';
					fullnames += ',';
				}
				userIds += rows[i].data.userId;
				fullnames += rows[i].data.fullname;
				users.push(rows[i].data);
			}
		} else {
			var selStore = this.selGridPanel.getStore();
			for(var i = 0 ; i<selStore.getCount(); i++){
				if (i > 0) {
					userIds += ',';
					fullnames += ',';
				}
				userIds += selStore.getAt(i).data.userId;
				fullnames += selStore.getAt(i).data.fullname;
				users.push(selStore.getAt(i).data);
			}
		}

		if (this.callback){
			this.callback.call(this.scope, userIds, fullnames,users);
		}
		this.close();
	}
});