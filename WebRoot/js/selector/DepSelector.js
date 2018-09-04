/**
 * 部门选择器
 */
var DepSelector = {
	/**
	 * @param callback　回调函数
	 * @param isSingle　是否单选
	 */
	getView : function(callback,isSingle,isCharge,depIds,depNames) {
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
			title : '部门信息显示',
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
				url : __ctxPath + '/system/listDepartment.do'
			}),
			root : new Ext.tree.AsyncTreeNode({
				expanded : true
			}),
			rootVisible : false,
			listeners : {
				 'click': function(node) {
						if (node != null) {
							var deps = Ext.getCmp('DepSelectorGrid');
							var store = deps.getStore();
							store.proxy.conn.url = __ctxPath + '/system/selectDepartment.do';
							store.baseParams = {
								depId : node.id
							};
//							store.load({
//								params : {
//									start : 0,
//									limit : 12
//								}
//							});
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
				header : 'depId',
				dataIndex : 'depId',
				hidden : true
			}, {
				header : "部门名称",
				dataIndex : 'depName',
				renderer:function(value,metadata,record){
					var str='';
					var level=record.data.depLevel;
					if(level!=null&& !isNaN(level)){
						for(var i=2;i<=level;i++){
							str+='<img src="' + __ctxPath+ '/images/system/down.gif"/>';
						}
					}
					str+=value;
					return str;
				},
				width : 60
			},{
				dataIndex : 'chargeIds',
				hidden: true
			},{
				dataIndex : 'chargeNames',
				hidden: true
			}
			]
		});

		var store = new Ext.data.Store({
			proxy : new Ext.data.HttpProxy({
				url : __ctxPath + '/system/selectDepartment.do'
			}),
			reader : new Ext.data.JsonReader({
				root : 'result',
				totalProperty : 'totalCounts',
				id : 'depId',
				fields : [{
					name : 'depId',
					type : 'int'
				}, 'depName',{name:'depLevel',type:'int'},'chargeIds','chargeNames']
			}),
			remoteSort : true
		});

		var gridPanel = new Ext.grid.GridPanel({
			id : 'DepSelectorGrid',
			width : 400,
			height : 300,
			region : 'center',
			title : '部门列表(双击选中)',
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
			bbar : new HT.PagingBar({store : store})
		});
		gridPanel.on('rowdblclick',function(grid,rowIndex,e){
			var contactGrid = Ext.getCmp('DepSelectorGrid');
			var selGrid = Ext.getCmp('selectedDepGrid');
			var selStore = selGrid.getStore();
			var rows = contactGrid.getSelectionModel().getSelections();
			for(var i= 0 ; i<rows.length ; i++){
				var depId = rows[i].data.depId;
				var depName = rows[i].data.depName;
				var chargeIds = rows[i].data.chargeIds;
				var chargeNames = rows[i].data.chargeNames;
				var isExist = false;
				//查找是否存在该记录
				for(var j=0;j<selStore.getCount();j++){
					if(selStore.getAt(j).data.depId == depId){
						isExist = true;
						break;
					}
				}
				if(!isExist){
					var newData = {depId : depId,depName : depName, chargeIds:chargeIds, chargeNames:chargeNames};
					var newRecord = new selStore.recordType(newData);
					selGrid.stopEditing();
					selStore.add(newRecord);
				}
			}
		});	//end of contact grid
		store.load({
//			params : {
//				start : 0,
//				limit : 12
//			}
		});
		
		var csm = new Ext.grid.CheckboxSelectionModel();
		var selectedDepGrid = new Ext.grid.EditorGridPanel({
			id : 'selectedDepGrid',
			title : '已选部门(双击删除)',
			layout : 'form',
			region : 'center',
			width : '100%',
			autoWidth : true,
			height : '100%',
			autoHeight : true,
			autoScroll : true,
			border : false,
			store : new Ext.data.ArrayStore({
    			fields : ['depId', 'depName', 'chargeIds', 'chargeNames']
			}),
			trackMouseOver : true,
			sm : csm,
			columns : [ csm, new Ext.grid.RowNumberer(), {
				header : "部门名称",
				dataIndex : 'depName'
			},{
				dataIndex : 'chargeIds',
				hidden: true
			},{
				dataIndex : 'chargeNames',
				hidden: true
			}]
		}); // end of this selectedUserGrid

	 	if(depIds&&depIds.length>0){
			var selStore = selectedDepGrid.getStore();
			var arrDepIds = depIds.split(',');
			var arrDepNames = depNames.split(',');
			for(var index=0;index<arrDepIds.length;index++){
				var newData = {depId: arrDepIds[index],depName: arrDepNames[index]};
				var newRecord = new selStore.recordType(newData);
				selectedDepGrid.stopEditing();
				selStore.add(newRecord);
			}
	 	}
		
		selectedDepGrid.addListener('rowdblclick',function(grid,e){
			var grid = Ext.getCmp('selectedDepGrid');
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
				items : [selectedDepGrid]
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
			id : 'depSelectWindow',
			title : '部门选择器',
			iconCls : 'menu-department',
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
				handler : this.submit.createCallback(this.callback, isSingle, isCharge)
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
		var contactGrid = Ext.getCmp('DepSelectorGrid');
		var selGrid = Ext.getCmp('selectedDepGrid');
		var selStore = selGrid.getStore();
		var rows = contactGrid.getSelectionModel().getSelections();
		for(var i = 0; i<rows.length; i++){
			var depId = rows[i].data.depId;
			var depName = rows[i].data.depName;
			var chargeIds = rows[i].data.chargeIds;
			var chargeNames = rows[i].data.chargeNames;
			var isExist = false;
			//查找是否存在该记录
			for(var j=0; j<selStore.getCount(); j++){
				if(selStore.getAt(j).data.depId== depId){
					isExist = true;
					break;
				}
			}
			if(!isExist){
				var newData = {depId:depId,depName:depName,chargeIds:chargeIds,chargeNames:chargeNames};
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
		var selGrid=Ext.getCmp('selectedDepGrid');
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
	submit : function(callback, isSingle,isCharge){
		var depIds = '';
		var depNames = '';
		var chargeIds = '';
		var chargeNames = '';
		if(isSingle == null || isSingle == false){//选择多个
			var selStore = Ext.getCmp('selectedDepGrid').getStore();
			for(var i = 0 ; i<selStore.getCount(); i++){
				if (i > 0) {
					depIds += ',';
					depNames += ',';
					chargeIds += ',';
					chargeNames += ',';
				}
				depIds += selStore.getAt(i).data.depId;
				depNames += selStore.getAt(i).data.depName;
				chargeIds += selStore.getAt(i).data.chargeIds;
				chargeNames += selStore.getAt(i).data.chargeNames;
			}
		} else {//选择单个
			var grid = Ext.getCmp('DepSelectorGrid');
			var rows = grid.getSelectionModel().getSelections();
			for (var i = 0; i < rows.length; i++) {
				if (i > 0) {
					depIds += ',';
					depNames += ',';
					chargeIds += ',';
					chargeNames += ',';
				}
				depIds += rows[i].data.depId;
				depNames += rows[i].data.depName;
				chargeIds += rows[i].data.chargeIds;
				chargeNames += rows[i].data.chargeNames;
			}
		}
		
		if (callback != null) {
//			if(isCharge){
//				callback.call(this, chargeIds, chargeNames);
//			}else{
				callback.call(this, depIds, depNames);
//			}
		}
		Ext.getCmp('depSelectWindow').close();
	},
	
	/**
	 * 关闭当前窗口
	 */
	close : function(){
		Ext.getCmp('depSelectWindow').close();
	}

};