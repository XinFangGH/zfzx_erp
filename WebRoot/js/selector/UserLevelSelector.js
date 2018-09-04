/**
 * @description 代理人选择器
 * @class UserLevelSelector
 * @author 智维软件
 * @updater ZCB
 * @createtime 2016/4/19
 */
var callback=null;
var scope=null;
var UserLevelSelector = {
	/**
	 * 
	 * @param {} callbackOrConf 回调函数或配置选项，若为map类型，则表示为配置选项
	 * @param {} isSingle 是否单选
	 * @param {} isForFlow 是否为工作流的配置选择
	 * @return {}
	 */
	getView : function(callbackOrConf,principalId) {
		//单选
		if(typeof(callbackOrConf) == 'object'){
			scope = callbackOrConf.scope;
			callback = callbackOrConf.callback;
		} else {
			scope = this;
			callback = callbackOrConf;
		}
		var panel = this.initPanel(principalId);
		// window
		var window = new Ext.Window({
			id : 'UserLevelSelectorWin',
			title : '选择代理人',
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
			items : [panel]
		});
		return window;
	},

	/**
	 * 组件初始化
	 * @param isSingle 是否单选,默认单选
	 */
	initPanel : function(principalId) {
		////////////////store[获取数据] start////////////////////////////
		var store = new Ext.data.Store({
			proxy : new Ext.data.HttpProxy({
				url : __ctxPath + '/system/findLowerAppUser.do?userId='+principalId
			}),
			reader : new Ext.data.JsonReader({
				root : 'result',
				totalProperty : 'totalCounts',
				id : 'id',
				fields : [{
					name : 'userId',
					type : 'int'
				}, 'fullname','title','username']
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
		var sm = new Ext.grid.CheckboxSelectionModel({singleSelect : true});
		var cm = new Ext.grid.ColumnModel({
			columns : [sm, new Ext.grid.RowNumberer(),{
				header : "账号",
				dataIndex : 'username',
				renderer : function(value,meta,record){
					if(value){
						return value.substring(0,value.indexOf('@'));
					}
				},
				width : 60
			}, {
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

		var userLevelGrid = new Ext.grid.EditorGridPanel({
			autoScroll : true,
			id:'userLevelGrid',
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
			bbar : new HT.PagingBar({store : store,pageSize : 12}),
			listeners:{
				'rowdblclick':function(grid,rowIndex,e){
					var userLevelGrid=Ext.getCmp('userLevelGrid');
					var userIds = '';
					var fullnames = '';
					var rows = userLevelGrid.getSelectionModel().getSelections();
					if (rows.length == 0) {
						Ext.ux.Toast.msg('操作信息', '请选择记录!');
						return;
					}else{
						userIds = rows[0].data.userId;
						fullnames += rows[0].data.fullname;
					}
					if (callback != null){
						callback.call(scope, userIds, fullnames);
					}
					Ext.getCmp('UserLevelSelectorWin').close();
				}
			}
		}); // end of this contactGrid
		
		var searchPanel = new Ext.FormPanel({
			// TODO searchPanel[搜索面板]
			id:'UserLevelSearchPanel',
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
					name : 'fullname',
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
		
		var panel = new Ext.Panel({
			// TODO panel总面板
			layout : 'border',
			region : 'center',
			border : false,
			anchor : '96%,96%',
			items : [searchPanel,userLevelGrid]
		});
		return panel;
	}, // init
	
	/**
	 * 搜索
	 */
	search : function(){
		var userLevelGrid=Ext.getCmp('userLevelGrid');
		var searchPanel=Ext.getCmp('UserLevelSearchPanel');
		userLevelGrid.store.baseParams.fullname=searchPanel.getCmpByName('fullname').getValue();
		userLevelGrid.store.reload();
	}
};