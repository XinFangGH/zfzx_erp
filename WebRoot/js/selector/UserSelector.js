/**
 * @description 用户选择器
 * @class UserSelector
 * @author 智维软件
 * @updater YHZ
 * @createtime 2011-1-19PM
 */
var userId;
var UserSelector = {
		
	/**
	 * 
	 * @param {} callbackOrConf 回调函数或配置选项，若为map类型，则表示为配置选项
	 * @param {} isSingle 是否单选
	 * @param {} isForFlow 是否为工作流的配置选择
	 * @return {}
	 */
	getView : function(callbackOrConf,isSingle,isForFlow,mobileFlag,uid) {
		//单选
		userId = uid;
		if(typeof(callbackOrConf) == 'object'){
			this.scope = callbackOrConf.scope;
			this.callback = callbackOrConf.callback;
		} else {
			this.scope = this;
			this.callback = callbackOrConf;
		}
		this.isSingle=(isSingle!=null)?isSingle:true;
		this.mobileFlag=(mobileFlag!=null)?mobileFlag:false;
		var panel = this.initPanel(isSingle);
		// window
		var window = new Ext.Window({
			id : 'UserSelectorWin',
			title : '选择用户',
			iconCls : 'menu-appuser',
			width : 640,
			minWidth : 640,
			height : 480,
			minHeight : 480,
			layout : 'fit',
			border : false,
			maximizable : true,
			resizable : true,
			modal : true,
			items : [panel],
			buttonAlign : 'center',
			buttons : [{
				text : '确认',
				iconCls : 'btn-ok',
				scope : this,
				handler : this.submit
			}, {
				text : '关闭',
				iconCls:'close',
				scope : this,
				handler : this.close
			}]
		});
		
		if(isForFlow){
			window.addButton(new Ext.Button({
				text : '发起人',
				iconCls : 'menu-subuser',
				scope : this,
				handler : function(){
					this.callback.call(this, '__start', '[发起人]');
					window.close();
				}
			}));
		}
		
		return window;
	},

	/**
	 * 组件初始化
	 * @param isSingle 是否单选,默认单选
	 */
	initPanel : function(isSingle) {
		////////////////store[获取数据] start////////////////////////////
		var store = new Ext.data.Store({
			proxy : new Ext.data.HttpProxy({
					url : __ctxPath + '/system/selectAppUser.do?userId='+userId
				}),
			reader : new Ext.data.JsonReader({
				root : 'result',
				totalProperty : 'totalCounts',
				id : 'id',
				fields : [{
					name : 'userId',
					type : 'int'
				}, 'fullname','title','mobile']
			}),
			remoteSort : true
		});
		store.setDefaultSort('id', 'desc');
		store.load({
			params : {
				start : 0,
				limit : 12
			}
		});
		var sm = null;
		if(isSingle){
			sm = new Ext.grid.CheckboxSelectionModel({singleSelect : true});
		}else{
			sm = new Ext.grid.CheckboxSelectionModel();
		}
		var cm = new Ext.grid.ColumnModel({
			columns : [sm, new Ext.grid.RowNumberer(), {
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
			}],
			defaults : {
				sortable : true,
				menuDisabled : true,
				width : 120
			},
			listeners : {
				hiddenchange : function(cm, colIndex, hidden) {
					saveConfig(colIndex, hidden);
				}
			}
		}); // end of cm
		///////////////////////store end///////////////////////////////////

		////////////////////treePanel[left节点] start/////////////////////////////
		//treePanel
		var treePanel = new Ext.tree.TreePanel({
			// TODO left节点treePanel
			id : 'treePanels',
			autoScroll : true,
			title : '按部门分类 ',
			iconCls : 'dep-user',
			loader : new Ext.tree.TreeLoader({
				url : __ctxPath + '/system/listDepartment.do'
			}),
			root : new Ext.tree.AsyncTreeNode({
				expanded : true
			}),
			rootVisible : false,
			listeners : {
				'click' : this.clickNode
			}
		});
		////////////////////treePanel[left节点] end/////////////////////////////

		///////////////////rolePanel start///////////////////////////
		var rolePanel = new Ext.tree.TreePanel({
			// TODO rolePanel
			id : 'rolePanel',
			autoScroll : true,
			iconCls : 'role-user',
			title : '按角色分类 ',
			loader : new Ext.tree.TreeLoader({
						url : __ctxPath + '/system/treeAppRole.do?type='+this.type
					}),
			root : new Ext.tree.AsyncTreeNode({
						expanded : true
					}),
			rootVisible : false,
			listeners : {
				'click' : this.clickRoleNode
			}
		}); // end of this rolePanel
		//////////////////////rolePanel end////////////////////////////
		
		//////////////////onlinePanel start////////////////////////////////
		var onlinePanel = new Ext.Panel({
			id : 'onlinePanel',
			autoScroll : true,
			iconCls : 'online-user',
			title : '在线人员  ',
			listeners : {
				'expand' : this.clickOnlinePanel
			}
		}); // end of this onlinePanel
		///////////////////onlinePanel end/////////////////////////////

		///////////////////contactGrid[用户列表] start///////////////////
		var contactGrid = new Ext.grid.EditorGridPanel({
			// TODO EditorGridPanel用户列表
			title : '用户列表',
			autoScroll : true,
			id : 'contactGrid',
			region : 'center',
			height : 380,
			autoWidth:false,
			store : store,
			shim : true,
			trackMouseOver : true,
			disableSelection : false,
			loadMask : true,
			cm : cm,
			sm : sm,
			viewConfig : {
				forceFit : true,
				enableRowBody : false,
				showPreview : false
			},
			bbar : new HT.PagingBar({store : store,pageSize : 12})
		}); // end of this contactGrid
		
		contactGrid.on('rowdblclick',function(grid,rowIndex,e){
			var contactGrid = Ext.getCmp('contactGrid');
			var selGrid = Ext.getCmp('selectedUserGrid');
			var selStore = selGrid.getStore();
			var rows = contactGrid.getSelectionModel().getSelections();
			for(var i= 0 ; i<rows.length ; i++){
				var userId = rows[i].data.userId;
				var fullname = rows[i].data.fullname;
				var isExist = false;
				//查找是否存在该记录
				for(var j=0;j<selStore.getCount();j++){
					if(selStore.getAt(j).data.userId == userId){
						isExist = true;
						break;
					}
				}
				if(!isExist){
					var newData = {userId : userId,fullname : fullname};
					var newRecord = new selStore.recordType(newData);
					selGrid.stopEditing();
					selStore.add(newRecord);
				}
			}
		});	//end of contact grid
		//////////////////////contactGrid[用户列表] end///////////////////////
		
		///////////////////searchPanel[搜索面板] start//////////////////////
		var searchPanel = new Ext.FormPanel({
			// TODO searchPanel[搜索面板]
			id : 'userSelectorSearchPanel',
			height : 38,
			region : 'north',
			layout : 'hbox',
			bodyStyle : 'padding:6px 2px 2px 2px',
			layoutConfigs : {
				align : 'middle'
			},
			keys : {
				key : Ext.EventObject.ENTER,
				scope : this,
				fn : this.search
			},
			defaultType : 'label',
			defaults : {
				margins : '0 0 0 4'
			},
			items : [{
					text : '用户姓名'
				}, {
					xtype : 'textfield',
					name : 'Q_fullname_S_LK',
					width : 260,
					maxLength : 256
				}, {
					xtype : 'button',
					text : '查询',
					iconCls : 'btn-search',
					scope : this,
					handler : this.search
				}]
			}
		); // end of this searchPanel
		//////////////////////searchPanel[搜索面板] end//////////////////////////////////
		
		//////////////////////selectedUserGrid[已选用户列表] start/////////////////////
		var csm = new Ext.grid.CheckboxSelectionModel();
		var selectedUserGrid = new Ext.grid.EditorGridPanel({
			// TODO selectedUserGrid[已选用户列表]
			id : 'selectedUserGrid',
			title : '已选用户',
			layout : 'form',
			region : 'center',
			width : '100%',
			autoWidth : true,
			height : '100%',
			autoHeight : true,
			autoScroll : true,
			border : false,
			store : new Ext.data.ArrayStore({
    			fields : ['userId', 'fullname']
			}),
			trackMouseOver : true,
			sm : csm,
			columns : [ csm, new Ext.grid.RowNumberer(), {
				header : "用户名",
				dataIndex : 'fullname'
			}]
		}); // end of this selectedUserGrid
		selectedUserGrid.addListener('rowdblclick',function(grid,e){
			var grid = Ext.getCmp('selectedUserGrid');
			var store = grid.getStore();
			var rows = grid.getSelectionModel().getSelections();
			for(var i =0; i<rows.length; i++){
				grid.stopEditing();
				store.remove(rows[i]);
			}
		});
		/////////////////////selectedUserGrid[已选用户列表] end //////////////
		
		/////////////////////selectedPanel[多选面板] start/////////////////////
		//多选添加的面板
		var selectedPanel = new Ext.Panel({
			layout : 'border',
			region : 'east',
			width : '200',
			height : '100%',
			border : false,
			autoScroll : true,
			items : [new Ext.Panel({
				region : 'west',
				frame : true,
				width : 40,
				layout : {
                    type : 'vbox',
                    pack : 'center',
                    align : 'stretch'
                },
                defaultType : 'button',
                items : [{
                	iconCls : 'add-all',
                	text : '',
                	scope : this,
                	handler : this.addAll
                },{
                	iconCls : 'rem-all',
                	text : '',
                	scope : this,
                	handler : this.removeAll
                }]
			}),{
				region : 'center',
				autoScroll : true,
				items : [selectedUserGrid]
			}]
		}); // selectedPanel
		///////////////////////selectedPanel end//////////////////////////////
		
		/////////////////westPanel start///////////////////////
		var westPanel = new Ext.Panel({
			layout : 'accordion',
			region : 'west',
			width : 200,
			split : true,
			header : false,
			collapsible : true,
			items : [treePanel, rolePanel, onlinePanel]
		}); // end of this westPanel
		/////////////////westPanel end///////////////////////
		
		var panel = new Ext.Panel({
			// TODO panel总面板
			id : 'contactPanel',
			layout : 'border',
			region : 'center',
			border : false,
			anchor : '96%,96%',
			// items : [searchPanel,westPanel,contactGrid]  //树结构目前不用,隐藏掉
			items : [searchPanel,contactGrid]
		}); // end of this contactPanel
		//添加：多选面板
		if(isSingle != null && isSingle == false){
			panel.add(selectedPanel);
			panel.doLayout();
		}
		return panel;
	}, // init

	
	
	
	////////////////###方法###///////////////////////
	
	clickNode : function(node) {
		if (node != null) {
			var users = Ext.getCmp('contactGrid');
			var store = users.getStore();
			store.proxy.conn.url = __ctxPath + '/system/selectAppUser.do';
			store.baseParams = {
				depId : node.id
			};
			store.load({
				params : {
					start : 0,
					limit : 12
				}
			});
		}
	},
	
	/**
	 * 角色查询用户信息
	 */
	clickRoleNode : function(node) {
		if (node != null) {
			var users = Ext.getCmp('contactGrid');
			var store = users.getStore();
			store.baseParams = {
				roleId : node.id
			};
			store.proxy.conn.url =__ctxPath + '/system/findAppUser.do';
			store.load({
				params : {
					start : 0,
					limit : 12
				}
			});
		}
	},
	
	/**
	 * 在线用户
	 */
	clickOnlinePanel:function(){
		var users = Ext.getCmp('contactGrid');
		var store = users.getStore();
		store.proxy.conn.url =__ctxPath + '/system/onlineAppUser.do';
		store.load({
			params : {
				start : 0,
				limit : 200
			}
		});
	},
	
	/**
	 * 添加所有
	 */
	addAll : function(){
		var contactGrid = Ext.getCmp('contactGrid');
		var selGrid = Ext.getCmp('selectedUserGrid');
		var selStore = selGrid.getStore();
		var rows = contactGrid.getSelectionModel().getSelections();
		for(var i = 0; i<rows.length; i++){
			var userId = rows[i].data.userId;
			var fullname = rows[i].data.fullname;
			var isExist = false;
			//查找是否存在该记录
			for(var j=0; j<selStore.getCount(); j++){
				if(selStore.getAt(j).data.userId== userId){
					isExist = true;
					break;
				}
			}
			if(!isExist){
				var newData = {userId:userId,fullname:fullname};
				var newRecord = new selStore.recordType(newData);
				selGrid.stopEditing();
				selStore.add(newRecord);
			}
		}
	},
	
	/**
	 * 移除所有
	 */
	removeAll : function(){
		var selGrid=Ext.getCmp('selectedUserGrid');
		var rows = selGrid.getSelectionModel().getSelections();
		var selStore = selGrid.getStore();
		for(var i=0 ;i<rows.length; i++){
			selGrid.stopEditing();
			selStore.remove(rows[i]);
		}
	},
	
	/**
	 * 搜索
	 */
	search : function(){
		var searchPanel = Ext.getCmp('userSelectorSearchPanel');
		var contactGrid = Ext.getCmp('contactGrid');
		searchPanel.getForm().submit({
			url : __ctxPath+'/system/listAppUser.do',
			method : 'post',
			success : function(formPanel, action) {
				contactGrid.getStore().proxy.conn.url=__ctxPath+'/system/listAppUser.do';
				var result = Ext.util.JSON.decode(action.response.responseText);
				contactGrid.getStore().loadData(result);
			}
		});
	},
	
	/**
	 * 确定，提交
	 * @param isSingle 是否单选
	 * @param callback 回传函数
	 */
	submit : function(){
		var userIds = '';
		var fullnames = '';
		if(this.isSingle == null || this.isSingle){//选择单个用户
			var grid = Ext.getCmp('contactGrid');
			var rows = grid.getSelectionModel().getSelections();
			
			for (var i = 0; i < rows.length; i++) {
				if (i > 0) {
					userIds += ',';
					fullnames += ',';
				}
				userIds += rows[i].data.userId;
				if(this.mobileFlag){
					fullnames += rows[i].data.fullname+'('+rows[i].data.mobile+')';
				}else{
					fullnames += rows[i].data.fullname;
				}
			}
		} else {
			var selStore = Ext.getCmp('selectedUserGrid').getStore();
			for(var i = 0 ; i<selStore.getCount(); i++){
				if (i > 0) {
					userIds += ',';
					fullnames += ',';
				}
				userIds += selStore.getAt(i).data.userId;
				if(this.mobileFlag){
					fullnames += selStore.getAt(i).data.fullname+'('+selStore.getAt(i).data.mobile+')';
				}else{
					fullnames += selStore.getAt(i).data.fullname;
				}
			}
		}

		if (this.callback != null)
			this.callback.call(this.scope, userIds, fullnames);
		Ext.getCmp('UserSelectorWin').close();
	},
	
	/**
	 * 关闭当前窗口
	 */
	close : function(){
		Ext.getCmp('UserSelectorWin').close();
	}
};
