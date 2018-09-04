/**
 * 用户选择器
 */
var OnlineUserSelector = {
	getView : function(callback,isSingle) {
		var panel=this.initPanel(isSingle);
		var window = new Ext.Window({
			title : '选择在线用户',
			iconCls:'menu-appuser',
			width : 440,
			height : 420,
			border:false,
			layout:'fit',
			items : [panel],
			modal:true,
			buttonAlign : 'center',
			buttons : [{
						text : '确认',
						iconCls:'btn-ok',
						scope:'true',
						handler : function(){
							var grid = Ext.getCmp('contactGrid');
							var rows = grid.getSelectionModel().getSelections();
							var userIds = '';
							var fullnames = '';
							for (var i = 0; i < rows.length; i++) {
								if (i > 0) {
									userIds += ',';
									fullnames += ',';
								}
								userIds += rows[i].data.userId;
								fullnames += rows[i].data.fullname;
							}
					
							if (callback != null) {
								callback.call(this, userIds, fullnames);
							}
							window.close();
						}
					}, {
						text : '关闭',
						iconCls:'close',
						handler : function() {
							window.close();
						}
					}]
		});
		return window;
	},

	initPanel : function(isSingle) {

		var store = new Ext.data.Store({
					proxy : new Ext.data.HttpProxy({
								url : __ctxPath + '/system/onlineAppUser.do'
							}),
					reader : new Ext.data.JsonReader({
								root : 'result',
								totalProperty : 'totalCounts',
								id : 'id',
								fields : [{
											name : 'userId',
											type : 'int'
										}, 'fullname','title']
					}),
					remoteSort : true
				});
		store.setDefaultSort('id', 'desc');

		store.load();
		var sm=null;
		if(isSingle){
			var sm=new Ext.grid.CheckboxSelectionModel({singleSelect: true});
		}else{
			sm = new Ext.grid.CheckboxSelectionModel();
		}
		var cm = new Ext.grid.ColumnModel({
					columns : [sm, new Ext.grid.RowNumberer(), {
								header : "用户名",
								dataIndex : 'fullname',
								renderer:function(value,meta,record){
									var title=record.data.title;
									if(title=='1'){
										return '<img src="'+__ctxPath+'/images/flag/man.png"/>&nbsp;'+value;
									}else{
										return '<img src="'+__ctxPath+'/images/flag/women.png"/>&nbsp;'+value;
									}
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
				});

		var treePanel = new Ext.tree.TreePanel({
					id : 'treePanels',
					title : '按部门分类 ',
					iconCls:'dep-user',
					autoScroll:true,
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

		var rolePanel = new Ext.tree.TreePanel({
					id : 'rolePanel',
					iconCls:'role-user',
					title : '按角色分类 ',
					autoScroll:true,
					loader : new Ext.tree.TreeLoader({
								url : __ctxPath + '/system/treeAppRole.do'
							}),
					root : new Ext.tree.AsyncTreeNode({
								expanded : true
							}),
					rootVisible : false,
					listeners : {
						'click' : this.clickRoleNode
					}
				});
		
		var onlinePanel = new Ext.Panel({
					id : 'onlinePanel',
					autoScroll:true,
					iconCls:'online-user',
					title : '所有在线人员  ',
					listeners:{
						'expand':this.clickOnlinePanel
					}
				});
				
		var contactGrid = new Ext.grid.GridPanel({
					id : 'contactGrid',
					autoScroll:true,
					height : 345,
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
					}
				});

		var contactPanel = new Ext.Panel({
					id : 'contactPanel',
					width : 460,
					height : 400,
					layout : 'border',
					border : false,
					items : [{
								region : 'west',
								split : true,
								header:false,
								collapsible : true,
								width : 160,
								layout : 'accordion',
								items : [treePanel, rolePanel,onlinePanel]
							}, {
								region : 'center',
								layout:'fit',
								width : 250,
								items : [contactGrid]
							}]
				});
		return contactPanel;
	},

	clickNode : function(node) {
		if (node != null) {
			var users = Ext.getCmp('contactGrid');
			var store = users.getStore();
			store.proxy.conn.url = __ctxPath + '/system/onlineAppUser.do';
			store.baseParams = {
				depId : node.id
			};
			store.load();
		}
	},

	clickRoleNode : function(node) {
		if (node != null) {
			var users = Ext.getCmp('contactGrid');
			var store = users.getStore();
			store.baseParams = {
				roleId : node.id
			};
			store.proxy.conn.url =__ctxPath + '/system/onlineAppUser.do';
			store.load();
		}
	},
	clickOnlinePanel:function(){
		var users = Ext.getCmp('contactGrid');
		var store = users.getStore();
		store.baseParams = {depId : null,roleId : null};
		store.proxy.conn.url =__ctxPath + '/system/onlineAppUser.do';
		store.load();
	}
};
