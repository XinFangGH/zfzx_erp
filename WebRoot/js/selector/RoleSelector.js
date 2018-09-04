/**
 * 角色选择器
 */
var RoleSelector = {
	getView : function(callback,isSingle) {
		var gridPanel = this.initGridPanel(isSingle);
		var window = new Ext.Window({
			title : '角色选择',
			width : 630,
			height : 380,
			layout : 'fit',
			border : false,
			items : [gridPanel],
			modal : true,
			buttonAlign : 'center',
			buttons : [{
						iconCls : 'btn-ok',
						text : '确定',
						handler : function() {
							var grid = Ext.getCmp('RoleSelectorGrid');
							var rows = grid.getSelectionModel().getSelections();
							var roleIds = '';
							var roleNames = '';
							for (var i = 0; i < rows.length; i++) {

								if (i > 0) {
									roleIds += ',';
									roleNames += ',';
								}

								roleIds += rows[i].data.roleId;
								roleNames += rows[i].data.roleName;

							}

							if (callback != null) {
								callback.call(this, roleIds, roleNames);
							}
							window.close();
						}
					}, {
						text : '取消',
						iconCls : 'btn-cancel',
						handler : function() {
							window.close();
						}
					}]
		});
		return window;

	},

	initGridPanel : function(isSingle) {
		var sm=null;
		if(isSingle){
			var sm=new Ext.grid.CheckboxSelectionModel({singleSelect: true});
		}else{
			sm = new Ext.grid.CheckboxSelectionModel();
		}
		
		var cm = new Ext.grid.ColumnModel({
					columns : [sm, new Ext.grid.RowNumberer(), {
								header : 'roleId',
								dataIndex : 'roleId',
								hidden : true
							}, {
								header : "角色名称",
								dataIndex : 'roleName',
								width : 60
							}, {
								header : "角色描述",
								dataIndex : 'roleDesc',
								width : 60
							}]
				});

		var store = new Ext.data.Store({
					proxy : new Ext.data.HttpProxy({
								url : __ctxPath + '/system/listAppRole.do'
							}),
					reader : new Ext.data.JsonReader({
								root : 'result',
								totalProperty : 'totalCounts',
								id : 'id',
								fields : [{
											name : 'roleId',
											type : 'int'
										}, 'roleName', 'roleDesc']
							})
				});
		store.load({
					params : {
						start : 0,
						limit : 25
					}
				});
		
		var toolbar = new Ext.Toolbar({
					id : 'AppRoleFootBar',
					height : 30,
					items : ['角色名称：', {
								name : 'Q_roleName_S_LK',
								xtype : 'textfield',
								id:'Q_roleName_S_LK',
								width : 200
							},' ',{
								xtype:'button',
								iconCls:'btn-search',
								text:'查询',
								handler:function(){
									var roleName=Ext.getCmp('Q_roleName_S_LK').getValue();
									Ext.Ajax.request({
										url:__ctxPath+'/system/listAppRole.do',
										params:{Q_roleName_S_LK:roleName},
										method:'post',
										success:function(result,request){
											var data=Ext.util.JSON.decode(result.responseText);
											var grid=Ext.getCmp('RoleSelectorGrid');
											grid.getStore().loadData(data);
										},
										failure:function(result,request){
											//TODO
										}
									});
								}
							}

					]
				});
		var grid = new Ext.grid.GridPanel({
					id : 'RoleSelectorGrid',
					tbar : toolbar,
					store : store,
					trackMouseOver : true,
					disableSelection : false,
					loadMask : true,
					height : 360,
					cm : cm,
					sm : sm,
					viewConfig : {
						forceFit : true,
						enableRowBody : false,
						showPreview : false
					},
					bbar : new HT.PagingBar({store : store})
				});
		return grid;
	}
};