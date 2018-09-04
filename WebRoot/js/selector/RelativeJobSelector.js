/**
 * @description 选择相对岗位信息列表
 * @author YHZ
 * @company www.credit-software.com
 */
var RelativeJobSelector = {
		/**
		 * @param callback　回调函数
		 * @param isSingle 是否单选
		 */
		getView : function(callback,isSingle,reUserIds,reUserName) {
			var panel = this.initPanel(isSingle,reUserIds,reUserName);
			var window = new Ext.Window({
				id : 'RelativeJobSelectorWin',
				title : '相对岗位选择器',
				iconCls : 'menu-relativeJob',
				width : 630,
				minWidth : 400,
				height : 430,
				minHeight : 430,
				maximizable : true,
				layout : 'fit',
				border : false,
				items : [panel],
				modal : true,
				buttonAlign : 'center',
				buttons : [{
						iconCls:'btn-ok',
						text : '确定',
						handler : function() {
							var ids = '';
							var names = '';
							if(isSingle == null || isSingle == true){ //单选
								var node = Ext.getCmp('relativeJobSelectorTreePanel').getSelectionModel().getSelectedNode();
								if(node != null && node.id>0){
									ids = node.id;
									names = node.text;
								}
							} else { //多选
								var store = Ext.getCmp('selectedRelativeJobGridPanel').getStore();
								for(var i=0;i<store.getCount();i++){
									ids += store.getAt(i).data.jobId+',';
									names += store.getAt(i).data.jobName+',';
								}
								ids = ids.substring(0, ids.length-1);
								names = names.substring(0, names.length-1);
							}
							if(callback != null)
								callback.call(this, ids, names);
							window.close();
						}
					}, {
						text : '取消',
						iconCls:'btn-cancel',
						handler : function() {
							window.close();
						}
					}
				]
			});
			return window;
	},

	/**
	 * @description 初始化组件
	 * @param isSingle 判断是否单选
	 */
	initPanel : function(isSingle,reUserIds,reUserName){
		////////////////treePanel start///////////////////////////////
		var rg = isSingle != null && isSingle == true ? 'center' : 'west';
		// 实例化treePanel, 加载相对岗位人员管理
		var treePanel = new Ext.tree.TreePanel({
			id : 'relativeJobSelectorTreePanel',
			layout : 'form',
			region : rg,
			collapsible : true,
			autoScroll : true,
			split : true,
			width : 200,
			title : '相对岗位列表',
			tbar : new Ext.Toolbar({
				items : [{
					xtype : 'button',
					iconCls : 'btn-refresh',
					text : '刷新',
					handler : function() {
						treePanel.root.reload();
					}
				}, {
					xtype : 'button',
					text : '展开',
					iconCls : 'btn-expand',
					handler : function() {
						treePanel.expandAll();
					}
				}, {
					xtype : 'button',
					text : '收起',
					iconCls : 'btn-collapse1',
					handler : function() {
						treePanel.collapseAll();
					}
				}]
			}),
			loader : new Ext.tree.TreeLoader({
				url : __ctxPath + '/system/treeLoadRelativeJob.do'
			}),
			root : new Ext.tree.AsyncTreeNode({
				expanded : true
			}),
			rootVisible : false,
			listeners : {
				'dblclick' : this.nodeDBLClick
			}
		}); // end of this treePanel
		//////////////////////treePanel end //////////////////////////////
		
		/////////////EditorGridPanel[展示已选数据] start//////////////////////
		var csm = new Ext.grid.CheckboxSelectionModel();
		var selectedUserGrid = new Ext.grid.EditorGridPanel({
			// TODO selectedUserGrid已选相对岗位列表
			id : 'selectedRelativeJobGridPanel',
			title : '已选相对岗位',
			layout : 'form',
			region : 'center',
			width : '100%',
			autoWidth : '100%',
			height : '100%',
			autoHeight : '100%',
			border : false,
			autoScroll : true,
			viewConfig : {
				forceFit : true,
				enableRowBody : false,
				showPreview : false
			},
			store : new Ext.data.ArrayStore({
    			fields: ['jobId', 'jobName']
			}),
			trackMouseOver : true,
			sm : csm,
			columns : [ csm, new Ext.grid.RowNumberer(), {
					header : 'jobId',
					dataIndex : 'jobId',
					hidden : true
				},{
					header : "岗位名称",
					dataIndex : 'jobName'
				}
			]
		});
		
		// 初始化已选岗位
	 	if(reUserIds&&reUserIds.length>0){
			var selStore = selectedUserGrid.getStore();
			var arrReUserIds = reUserIds.split(',');
			var arrReUserName = reUserName.split(',');
			for(var index=0;index<arrReUserIds.length;index++){
				var newData = {jobId: arrReUserIds[index],jobName: arrReUserName[index]};
				var newRecord = new selStore.recordType(newData);
				selectedUserGrid.stopEditing();
				selStore.add(newRecord);
			}
	 	}
		
		//添加双击事件，移除已选数据
		selectedUserGrid.addListener('dblclick',function(grid){
			var grid = Ext.getCmp('selectedRelativeJobGridPanel');
			var store = grid.getStore();
			var rows = grid.getSelectionModel().getSelections();
			for(var i=0;i<rows.length;i++){
				grid.stopEditing();
				store.remove(rows[i]);
			}
		});
		/////////////EditorGridPanel[展示已选数据] end//////////////////////////
		
		/////////////////multiPanel[多选面板] start//////////////////////
		//多选添加的面板
		var multiPanel = new Ext.Panel({
			layout : 'border',
			region : 'center',
			width : '100%',
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
		}); // multiPanel
		
		/////////////////multiPanel[多选面板] end//////////////////////////////////////
		
		var panel = new Ext.Panel({
			// TODO panel总面板
			layout : 'border',
			region : 'center',
			autoScroll : true,
			border : false,
			anchor : '98%,98%',
			items : [treePanel]
		});
		//添加：[中间] 多选面板
		if(isSingle != null && isSingle == false){
			panel.add(multiPanel);
			panel.doLayout();
		}
		return panel;
	}, // end of this initPanel
	
	/**
	 * 节点双击事件
	 */
	nodeDBLClick : function(node){
		if(node != null && node.id>0){
			var grid = Ext.getCmp('selectedRelativeJobGridPanel');
			var store = grid.getStore();
			var isExist = true;//默认不存在
			for(var i=0; i<store.getCount(); i++){
				if(store.getAt(i).data.jobId == node.id){
					isExist = false;
					break;
				}
			}
			//4.添加数据
			if(isExist){
				var newData={jobId:node.id,jobName:node.text};
				var newRecord=new store.recordType(newData);
				grid.stopEditing();
				store.add(newRecord);
			}
		}
	},
	
	/**
	 * 添加所有
	 */
	addAll : function(){
		var tree = Ext.getCmp('relativeJobSelectorTreePanel');
		var grid = Ext.getCmp('selectedRelativeJobGridPanel');
		//1.获取选中的节点
		var node = tree.getSelectionModel().getSelectedNode();
		if(node != null && node.id>0){
			//2.获取store
			var store = grid.getStore();
			//3.判断是否已经存在
			var isExist = true;//默认不存在
			for(var i=0; i<store.getCount(); i++){
				if(store.getAt(i).data.jobId == node.id){
					isExist = false;
					break;
				}
			}
			//4.添加数据
			if(isExist){
				var newData={jobId:node.id,jobName:node.text};
				var newRecord=new store.recordType(newData);
				grid.stopEditing();
				store.add(newRecord);
			}
		}
	},
	
	/**
	 * 删除所有
	 */
	removeAll : function(){
		var grid = Ext.getCmp('selectedRelativeJobGridPanel');
		var rows = grid.getSelectionModel().getSelections();
		var store=grid.getStore();
		
		for(var i=0;i<rows.length;i++){
			grid.stopEditing();
			store.remove(rows[i]);
		}
	}	
};