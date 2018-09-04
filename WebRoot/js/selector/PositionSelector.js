/**
 * 职位选择器
 */
var PositionSelector = {
	/**
	 * @param callback　回调函数
	 * @param isSingle　是否单选
	 */
	getView : function(callback,isSingle) {
		//单选
		if(typeof(callback) == 'object'){
			this.scope = callback.scope;
			this.callback = callback.callback;
		} else {
			this.scope = this;
			this.callback = callback;
		}
		
		if(isSingle==null || isSingle==undefined){
			isSingle=false;
		}
		
		var treeDep = new Ext.tree.TreePanel({
			title : '职位信息显示',
			region : 'west',
			width : 180,
			height : 300,
			split : true,
			collapsible : true,
			autoScroll : true,
			tbar:new Ext.Toolbar({items:[{
				xtype:'button',
				iconCls:'btn-refresh',
				text:'刷新',
				handler:function(){
					treeDep.root.reload();
				}
			}, '-', {
				xtype:'button',
				text:'展开',
				iconCls:'btn-expand',
				handler:function(){
					treeDep.expandAll();
				}
			}, '-', {
				xtype:'button',
				text:'收起',
				iconCls:'btn-collapse1',
				handler:function(){
					treeDep.collapseAll();
				}
			}
			]}),
			loader : new Ext.tree.TreeLoader({
				url : __ctxPath + '/system/treePosition.do'
			}),
			root : new Ext.tree.AsyncTreeNode({
				expanded : true
			}),
			rootVisible : false,
			listeners : {
				 'click': function(node) {
						if (node != null) {
							var deps = Ext.getCmp('PositionSelectorGrid');
							var store = deps.getStore();
//							store.proxy.conn.url = __ctxPath + '/system/listPosition.do';
							store.proxy.conn.url = __ctxPath + '/system/custListPosition.do';
							store.baseParams = {
								posId : node.id
							};
							store.load({
								params : {
									start : 0,
									limit : 12
								}
							});
							}
						}
			}
		});
				
		// ---------------------------------start grid
		// panel--------------------------------
		var sm=null;
		if(isSingle){
			var sm=new Ext.grid.CheckboxSelectionModel({singleSelect: true});
		}else{
			sm = new Ext.grid.CheckboxSelectionModel();
		}
		var cm = new Ext.grid.ColumnModel({
			columns : [sm, new Ext.grid.RowNumberer(), {
				header : 'posId',
				dataIndex : 'posId',
				hidden : true
			}, {
				header : "职位名称",
				dataIndex : 'posName',
				width : 60
			}]
		});

		var store = new Ext.data.Store({
			proxy : new Ext.data.HttpProxy({
				url : __ctxPath + '/system/custListPosition.do'
			}),
			reader : new Ext.data.JsonReader({
				root : 'result',
				totalProperty : 'totcalCounts',
				id : 'posId',
				fields : [{
					name : 'posId',
					type : 'int'
				}, 'posName',{name:'posDesc',type:'int'}]
			}),
			remoteSort : true
		});

		var gridPanel = new Ext.grid.GridPanel({
			id : 'PositionSelectorGrid',
			width : 400,
			height : 300,
			region : 'center',
			title : '职位列表',
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
			bbar : new HT.PagingBar({
				store : store, 
				params : {
					start : 0,
					limit : 12
				}
			})
		});
		gridPanel.on('rowdblclick',function(grid,rowIndex,e){
			var contactGrid = Ext.getCmp('PositionSelectorGrid');
			var selGrid = Ext.getCmp('selectedPosGrid');
			var selStore = selGrid.getStore();
			var rows = contactGrid.getSelectionModel().getSelections();
			for(var i= 0 ; i<rows.length ; i++){
				var posId = rows[i].data.posId;
				var posName = rows[i].data.posName;
				var isExist = false;
				//查找是否存在该记录
				for(var j=0;j<selStore.getCount();j++){
					if(selStore.getAt(j).data.posId == posId){
						isExist = true;
						break;
					}
				}
				if(!isExist){
					var newData = {posId : posId,posName : posName};
					var newRecord = new selStore.recordType(newData);
					selGrid.stopEditing();
					selStore.add(newRecord);
				}
			}
		});	//end of contact grid
		store.load({
			params : {
				start : 0,
				limit : 12
			}
		});
		
		var csm = new Ext.grid.CheckboxSelectionModel();
		var selectedPosGrid = new Ext.grid.EditorGridPanel({
			id : 'selectedPosGrid',
			title : '已选职位',
			layout : 'form',
			region : 'center',
			width : '100%',
			autoWidth : true,
			height : '100%',
			autoHeight : true,
			autoScroll : true,
			border : false,
			store : new Ext.data.ArrayStore({
    			fields : ['posId', 'posName']
			}),
			trackMouseOver : true,
			sm : csm,
			columns : [ csm, new Ext.grid.RowNumberer(), {
				header : "职位名称",
				dataIndex : 'posName'
			}]
		}); // end of this selectedUserGrid
		selectedPosGrid.addListener('rowdblclick',function(grid,e){
			var grid = Ext.getCmp('selectedPosGrid');
			var store = grid.getStore();
			var rows = grid.getSelectionModel().getSelections();
			for(var i =0; i<rows.length; i++){
				grid.stopEditing();
				store.remove(rows[i]);
			}
		});
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
                defaults : {
                	height : 20,
                	xtype : 'button'
                },
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
				items : [selectedPosGrid]
			}]
		}); // selectedPanel
		
		var panel = new Ext.Panel({
			// TODO panel总面板
			id : 'contactPanel',
			layout : 'border',
			region : 'center',
			border : false,
			anchor : '96%,96%',
			items : [treeDep, gridPanel]
		}); // end of this contactPanel
		//添加：多选面板
		if(isSingle != null && isSingle == false){
			panel.add(selectedPanel);
			panel.doLayout();
		}

		var window = new Ext.Window({
			id : 'positionSelectWindow',
			title : '职位选择器',
			iconCls : 'menu-position',
			width : 630,
			height : 380,
			layout : 'border',
			border : false,
			items : [panel],
			modal : true,
			buttonAlign : 'center',
			maximizable : true,
			buttons : [{
				iconCls : 'btn-ok',
				text : '确定',
				scope : this,
				handler : this.submit.createCallback(this.callback, isSingle)
			}, {
				text : '取消',
				iconCls : 'btn-cancel',
				handler : this.close,
				scope : this
			}]
		});
		return window;
	},
	
	/**
	 * 添加所有
	 */
	addAll : function(){
		var contactGrid = Ext.getCmp('PositionSelectorGrid');
		var selGrid = Ext.getCmp('selectedPosGrid');
		var selStore = selGrid.getStore();
		var rows = contactGrid.getSelectionModel().getSelections();
		for(var i = 0; i<rows.length; i++){
			var posId = rows[i].data.posId;
			var posName = rows[i].data.posName;
			var isExist = false;
			//查找是否存在该记录
			for(var j=0; j<selStore.getCount(); j++){
				if(selStore.getAt(j).data.posId== posId){
					isExist = true;
					break;
				}
			}
			if(!isExist){
				var newData = {posId:posId,posName:posName};
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
		var selGrid=Ext.getCmp('selectedPosGrid');
		var rows = selGrid.getSelectionModel().getSelections();
		var selStore = selGrid.getStore();
		for(var i=0 ;i<rows.length; i++){
			selGrid.stopEditing();
			selStore.remove(rows[i]);
		}
	},
	
	/**
	 * 确定，提交
	 * @param isSingle 是否单选
	 * @param callback 回传函数
	 */
	submit : function(callback, isSingle){
		var posIds = '';
		var posNames = '';
		if(isSingle == null || isSingle == false){//选择多个
			var selStore = Ext.getCmp('selectedPosGrid').getStore();
			for(var i = 0 ; i<selStore.getCount(); i++){
				if (i > 0) {
					posIds += ',';
					posNames += ',';
				}
				posIds += selStore.getAt(i).data.posId;
				posNames += selStore.getAt(i).data.posName;
			}
		} else {//
			var grid = Ext.getCmp('PositionSelectorGrid');
			var rows = grid.getSelectionModel().getSelections();
			for (var i = 0; i < rows.length; i++) {
				if (i > 0) {
					posIds += ',';
					posNames += ',';
				}
				posIds += rows[i].data.posId;
				posNames += rows[i].data.posName;
			}
		}
		
		if (callback != null) {
			callback.call(this, posIds, posNames);
		}
		Ext.getCmp('positionSelectWindow').close();
	},
	
	/**
	 * 关闭当前窗口
	 */
	close : function(){
		Ext.getCmp('positionSelectWindow').close();
	}

};