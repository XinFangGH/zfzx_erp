/**
 * @description 职位信息选择器
 * @class JobSelector
 * @author YHZ
 * @company www.credit-software.com
 * @data 2010-12-2-31AM
 */
var JobSelector = {

	/**
	 * @description 获取JobSelector窗口[window]
	 * @param callback
	 *            回传函数
	 * @param isSingle
	 *            是否单选，默认[单选]
	 */
	getView : function(callback, isSingle) {
		var panel = this.initUIComponent(isSingle);
		var win = new Ext.Window({
			id : 'jobSelectorWin',
			title : '职位选择器',
			iconCls : 'menu-job',
			layout : 'fit',
			region : 'center',
			maximizable : true,
			modal : true,
			width : 600,
			minWidth : 400,
			height : 425,
			minHeight : 425,
			items : [panel],
			buttonAlign : 'center',
			buttons : [{
				text : '确定',
				iconCls : 'btn-ok',
				handler : function(){
					var ids = '';
					var names = '';
					if(isSingle == null || isSingle == true){ //单选
						var node = Ext.getCmp('jobSelectorJobTreePanel').getSelectionModel().getSelectedNode();
						if(node != null && node.id>0){
							ids = node.id;
							names = node.text;
						}
					} else {
						var store = Ext.getCmp('JobSelectorEditorGridPanel').getStore();
						for(var i=0; i<store.getCount(); i++){
							ids += store.getAt(i).data.jobId+',';
							names += store.getAt(i).data.jobName+',';
						}
						ids = ids.substring(0, ids.length-1);
						names = names.substring(0, names.length-1);
					}
					if(callback != null )
						callback.call(this, ids, names);
					win.close();
				},
				scope : this
			},{
				text : '取消',
				iconCls : 'btn-cancel',
				handler : function(){
					win.close();
				}
			}]
			
		}); // end of thie win
		return win;
	}, // end of this getView
	
	//组件初始化,参数：isSingle[是否单选]
	initUIComponent : function(isSingle){
		///////////////jobTreePanel start////////////////////////
		var rg = isSingle != null && isSingle == true ? 'center' : 'west';
		var jobTreePanel = new Ext.tree.TreePanel({
			// TODO jobTreePanel 
			id : 'jobSelectorJobTreePanel',
			layout : 'form',
			region : rg,
			width : 200,
			collapsible : true,
			autoScroll : true,
			split : true,
			title : '岗位信息列表',
			tbar : new Ext.Toolbar({
				defaultType : 'button',
				items : [{
					text : '刷新',
					iconCls : 'btn-refresh',
					handler : function(){
						Ext.getCmp('jobSelectorJobTreePanel').root.reload();
					}
				},{
					text : '展开',
					iconCls : 'btn-expand',
					handler : function(){
						Ext.getCmp('jobSelectorJobTreePanel').expandAll();
					}
				},{
					text : '收起',
					iconCls : 'btn-collapse1',
					handler : function(){
						Ext.getCmp('jobSelectorJobTreePanel').collapseAll();
					}
				}]
			}),
			loader : new Ext.tree.TreeLoader({
				url : __ctxPath + '/hrm/treeLoadJob.do'
			}),
			root : new Ext.tree.AsyncTreeNode({
				expanded : true
			}),
			rootVisible : false,
			listeners : {
				'dblclick' : this.nodeDBClick
			}
		}); // end of this jobTreePanel
		//////////////jobTreePanel end////////////////////////
		
		/////////////////selectGridPanel start////////////////////////////
		var csm = new Ext.grid.CheckboxSelectionModel();
		var selectGridPanel = new Ext.grid.EditorGridPanel({
			// TODO selectGridPanel
			id : 'JobSelectorEditorGridPanel',
			title : '已选中的职位列表',
			layout : 'form',
			region : 'center',
			width : '100%',
			autoWidth : true,
			height : '100%',
			autoHeight : true,
			border : false,
			autoScroll : true,
			viewConfig : {
				forceFit : true,
				enableRowBody : false,
				showPreview : false
			},
			store : new Ext.data.ArrayStore({
				fields : ['jobId','jobName']
			}),
			trackMouseOver : true,
			sm : csm,
			columns : [csm,new Ext.grid.RowNumberer(),{
				header : 'jobId',
				dataIndex : 'jobId',
				hidden : true
			},{
				header : '职位名称',
				dataIndex : 'jobName',
				anchor : '90%'
			}]
		}); // end of this selectGridPanel
		//双击移除数据
		selectGridPanel.addListener('dblclick',function(grid,e){
			var grid = Ext.getCmp('JobSelectorEditorGridPanel');
			var rows = grid.getSelectionModel().getSelections();
			var store = grid.getStore();
			for(var i=0 ; i<rows.length ; i++){
				grid.stopEditing();
				store.remove(rows[i]);
			}
		});
		////////////////selectGridPanel end/////////////////////////////
		
		////////////////selectedPanel[多选面板] start /////////////////////////////////
		//多选添加的面板
		var selectedPanel = new Ext.Panel({
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
				items : [selectGridPanel]
			}]
		}); // selectedPanel
		////////////////selectedPanel[多选面板] end////////////////////////////////////////
		
		//总面板
		var panel = new Ext.Panel({
			// TODO 总面板
			layout : 'border',
			region : 'center',
			autoScroll : true,
			border : false,
			anchor : '98%,98%',
			items : [jobTreePanel]
		}); // end of this panel
		
		//添加：[中间] 多选面板
		if(isSingle != null && isSingle == false){
			panel.add(selectedPanel);
			panel.doLayout();
		}
		return panel;
		
	}, // end of this initUIComponent
	
	/**
	 * 节点双击事件
	 */
	nodeDBClick : function(node){
		if(node != null && node.id > 0){
			var gridPanel = Ext.getCmp('JobSelectorEditorGridPanel');
			var selStore = gridPanel.getStore();
			var rows = gridPanel.getSelectionModel().getSelections();
			var isExists = false; //默认不存在
			//查询是否存在该记录
			for(var i = 0 ; i<selStore.getCount() ; i++){
				if(selStore.getAt(i).data.jobId == node.id){
					isExists = true; //存在
					break;
				}
			}
			if(!isExists){ // 可以添加
				var data = {jobId:node.id,jobName:node.text};
				var record = new selStore.recordType(data);
				gridPanel.stopEditing();
				selStore.add(record);
			}
		}
	},
	
	/**
	 * 添加所有
	 */
	addAll : function(){
		var tree = Ext.getCmp('jobSelectorJobTreePanel');
		var grid = Ext.getCmp('JobSelectorEditorGridPanel');
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
	 * 移除所有
	 */
	removeAll : function(){
		var grid = Ext.getCmp('JobSelectorEditorGridPanel');
		var rows = grid.getSelectionModel().getSelections();
		var store = grid.getStore();
		for(var i=0 ; i<rows.length ; i++){
			grid.stopEditing();
			store.remove(rows[i]);
		}
	}
};