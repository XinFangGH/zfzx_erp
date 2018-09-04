/**
 * 用户邮件选择器
 */
var EMailSelector = {
	getView : function(callback,isSingle,isForFlow) {
		var panel=this.initPanel(isSingle);
		var window = new Ext.Window({
			title : '选择联系人',
			iconCls:'menu-appuser',
			width : 460,
			height : 440,
			layout:'fit',
			border:false,
			items : [panel],
			resizable:false,
			modal:true,
			buttonAlign : 'center',
			buttons : [{
						text : '确认',
						iconCls:'btn-ok',
						scope:'true',
						handler : function(){
							var grid = Ext.getCmp('emailContactGrid');
							var rows = grid.getSelectionModel().getSelections();
							var emails='';
							for (var i = 0; i < rows.length; i++) {
								emails+=''+rows[i].data.fullname+''+'<'+rows[i].data.email+'>'+';';							
								
							}
					
							if (callback != null) {
								callback.call(this,emails);
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
								url : __ctxPath + '/communicate/listPhoneBook.do'
							}),
					reader : new Ext.data.JsonReader({
								root : 'result',
								totalProperty : 'totalCounts',
								id : 'id',
								fields : [{
											name : 'phoneId',
											type : 'int'
										}, 'fullname','email','title']
							}),
					remoteSort : true
				});
		store.setDefaultSort('id', 'desc');

		store.load({
					params : {
						start : 0,
						limit : 12,
						'Q_appUser.userId_L_EQ':curUserInfo.userId
					}
				});
		var sm=null;
		if(isSingle){
			sm=new Ext.grid.CheckboxSelectionModel({singleSelect: true});
		}else{
			sm = new Ext.grid.CheckboxSelectionModel();
		}
		var cm = new Ext.grid.ColumnModel({
					columns : [sm, new Ext.grid.RowNumberer(), {
								header : "名称",
								dataIndex : 'fullname',
								renderer:function(value,meta,record){
									var title=record.data.title;
									if(title=='先生'){
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

		var personlPanel = new Ext.tree.TreePanel({
					id : 'treePanels',
					title : '个人录信录',
					iconCls:'menu-personal-phoneBook',
					loader : new Ext.tree.TreeLoader({
								url : __ctxPath + '/communicate/listPhoneGroup.do'
							}),
					root : new Ext.tree.AsyncTreeNode({
								expanded : true
							}),
					rootVisible : false,
					listeners : {
						'click' : this.clickPersonlNode
					}
				});

		var sharedPanel = new Ext.tree.TreePanel({
					id : 'sharedPanel',
					iconCls:'menu-phonebook-shared',
					title : '共享通信录 ',
					loader : new Ext.tree.TreeLoader({
								url : __ctxPath + '/communicate/sharePhoneBook.do'
							}),
					root : new Ext.tree.AsyncTreeNode({
								text:'共享通信录',
								expanded : true
							}),
					rootVisible : true,
					listeners : {
						'click' : this.clickSharedNode
					}
				});


		var emailContactGrid = new Ext.grid.GridPanel({
					id : 'emailContactGrid',
					height : 360,
					autoWidth:false,
					store : store,
					shim : true,
					trackMouseOver : true,
					border:false,
					disableSelection : false,
					loadMask : true,
					cm : cm,
					sm : sm,
					viewConfig : {
						forceFit : true,
						enableRowBody : false,
						showPreview : false
					},

					bbar : new HT.PagingBar({store : store})
				});

		var emailContactPanel = new Ext.Panel({
					id : 'emailContactPanel',
					width : 420,
					height : 410,
					layout : 'border',
					border : false,
					items : [{
								region : 'west',
								split : true,
								collapsible : true,
								width : 120,
								margins : '5 0 5 5',
								layout : 'accordion',
								items : [personlPanel, sharedPanel]
							}, {
								region : 'center',
								margins : '5 0 5 5',
								width : 230,
								items : [emailContactGrid]
							}]
				});
		return emailContactPanel;
	},

	clickPersonlNode : function(node) {
		if (node != null) {
			var users = Ext.getCmp('emailContactGrid');
			var store = users.getStore();
			store.proxy.conn.url = __ctxPath + '/communicate/listPhoneBook.do';
			if(node.id!=0&&node.id!='0'){
				store.load({
							params : {
								start : 0,
								limit : 12,
								'Q_appUser.userId_L_EQ':curUserInfo.userId,
								'Q_phoneGroup.groupId_L_EQ':node.id
							}
						});
			}else{
				store.load({
							params : {
								start : 0,
								limit : 12,
								'Q_appUser.userId_L_EQ':curUserInfo.userId
								
							}
						});
			
			}
			
		}
	},

	clickSharedNode : function(node) {
		if (node != null) {
			var users = Ext.getCmp('emailContactGrid');
			var store = users.getStore();
			store.baseParams = {
					Q_isShared_SN_EQ : 1
			};
			store.proxy.conn.url =__ctxPath + '/communicate/listPhoneBook.do';
			store.load({
					params : {
						start : 0,
						limit : 12,
						Q_isShared_SN_EQ : 1
					}
				});
		}
	}

};
