/**
 * 用户选择器
 * @class UserDialog
 * @extends Ext.Window
 */
MemberDialog=Ext.extend(Ext.Window,{
	constructor:function(conf){
		Ext.applyIf(this,conf);
		
		this.scope=this.scope?this.scope:this;
		//默认为多单选择用户
		this.single=this.single!=null?this.single:true;
		this.companyId=this.companyId?this.companyId:"";
		this.initUI();
		MemberDialog.superclass.constructor.call(this,{
			title:this.title?this.title:'用户选择器',
			height:450,
			width:630,
			maximizable : true,
			modal:true,
			layout:'border',
			items:[
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
	
	
	/**
	 * 初始化UI
	 */
	initUI:function(){
	
	
		
		//搜索用户列
	 	this.searchPanel=new HT.SearchPanel({
	 		region:'north',
	 		layout : 'form',
			colNums : 4,
			items : [{
				fieldLabel:'用户名',
				xtype:'textfield',
				flex:1,
				name:'Q_loginname_S_LK'
			},
			{
				fieldLabel:'真实姓名',
				xtype:'textfield',
				flex:1,
				name:'Q_truename_S_LK'
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
			},{
				xtype:'button',
				text : '重置',
				iconCls : 'btn-reset',
				scope : this,
				handler:function(){
				this.searchPanel.getForm().reset();
				$search({
						searchPanel : this.searchPanel,
						gridPanel : this.gridPanel
				});
			}
//				handler : this.reset
			}]
	 	});
	 	//可选择的用户列表
	 	this.gridPanel=new HT.GridPanel({
	 		singleSelect:this.single,
	 		title:'可选用户',
	 		region:'center',
	 		isShowTbar:false,
	 		url:__ctxPath + '/p2p/listBpCustMember.do',//默认查找当前用户所在部门的用户
	 		
	 		fields :[{name : 'id',type : 'int'}, 'loginname', 'telphone', 'sex','truename'],
	 		columns:[
	 			{
					header : "用户名",
					dataIndex : 'loginname',
					renderer : function(value,meta,record){
						var title = record.data.sex;
						if(title ==0)
							return '<img src="'+__ctxPath+'/images/flag/man.png"/>&nbsp;' + value;
						else
							return '<img src="'+__ctxPath+'/images/flag/women.png"/>&nbsp;' + value;
					},
					width : 60
				}, {
					width : 50,
						header : '真实姓名',
						dataIndex : 'truename'
					}
	 		]
	 	});

	 	if(!this.single){
	 		
	 		this.gridPanel.addListener('rowclick',this.gridPanelRowClick,this);//单击任务
	 		
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
	    			fields : ['id', 'loginname','sex']
				}),
				trackMouseOver : true,
				columns : [{
					header : "用户名",
					dataIndex : 'loginname',
					renderer : function(value,meta,record){
						var title = record.data.sex;
						if(title == 0)
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
					var newData = {id: arrUserIds[index],loginname: arrUserName[index],title:3};
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
	 * 重置
	 * @param {} formPanel
	 */
	reset : function() {
		this.searchPanel.getForm().reset();
	},
	/**
	 * 用户GridPanel（中间的Grid)行点击
	 * @param {} grid
	 * @param {} rowIndex
	 * @param {} e
	 */
	gridPanelRowClick:function(grid,rowIndex,e){
		var selStore = this.selGridPanel.getStore();//拿右边已选中的记录
		var rows = this.gridPanel.getSelectionModel().getSelections();//拿左边已选中的记录
		for(var i= 0 ; i<rows.length ; i++){
			var id = rows[i].data.id;
			var loginname = rows[i].data.loginname;
			var sex=rows[i].data.sex;
			var isExist = false;
			//查找是否存在该记录
			for(var j=0;j<selStore.getCount();j++){
				if(selStore.getAt(j).data.id == id){
					isExist = true;
					break;
				}
			}
			if(!isExist){
				var newData = {id : id,loginname : loginname};
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
				userIds += rows[i].data.id;
				fullnames += rows[i].data.loginname;
				users.push(rows[i].data);
			}
		} else {
			var selStore = this.selGridPanel.getStore();
			for(var i = 0 ; i<selStore.getCount(); i++){
				if (i > 0) {
					userIds += ',';
					fullnames += ',';
				}
				userIds += selStore.getAt(i).data.id;
				fullnames += selStore.getAt(i).data.loginname;
				users.push(selStore.getAt(i).data);
			}
		}

		if (this.callback){
			this.callback.call(this.scope, userIds, fullnames,users);
		}
		this.close();
	}
});