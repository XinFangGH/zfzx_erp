TaskManager = Ext.extend(Ext.Panel, {
	title : '流程任务总览',
	isHideTopBar:false,
	tempId:'taskmanageview',
	tempGridId:'allTaskGrid',
	resource:'underline',//默认查询全部线下流程任务
	constructor : function(conf) {
		if (conf && conf.flag) {
			if (conf.subType && conf.subType=='onlineopen') {//查询线上借款项目流程任务
				this.resource='credit';
			}else if (conf.subType && conf.subType=='all') {//查询全部流程任务
				this.resource='underline';
			}
			if(conf.busType){
				this.busType=conf.busType;
			}
			if(1==conf.flag){
				this.title='收费方案制定';
			}else if(2==conf.flag){
				this.title='制作法律文书';
			}else if(3==conf.flag){
				this.title='合同放款确认';
			}else if(4==conf.flag){
				this.title='登记详细借款';
			}else if(5==conf.flag){
				this.title='登记实际债权人';
			}
			var temp=conf.flag;
			var tempType=conf.type;
			this.isHideTopBar=true;
			this.tempId=this.tempId+temp+tempType+conf.flowType+conf.subType;
			this.tempGridId=this.tempGridId+temp+tempType+conf.flowType;
		}
		Ext.applyIf(this, conf);
		this.initUIComponents();
		TaskManager.superclass.constructor.call(this, {
			id:this.tempId,
			title : this.title,
			iconCls:"menu-flowManager",
			layout : 'border',
			items : this.isHideTopBar?[this.gridPanel]:[this.searchPanel,this.gridPanel]
		});
	},
	initUIComponents : function() {
		this.searchPanel = new Ext.form.FormPanel({
			height : 40,
			border:false,
			region : 'north',
			layout : 'hbox',
			layoutConfig:{
				align:'middle',
				pack:'left'
			},
			style:'background-color:white;padding:5px;',
			defaults:{
				margins:'0px 8px 0px 4px'
			},
			items : [{
				xtype:'label',
				text:'项目名称:'
			},{
				xtype:'textfield',
				name:'taskName',
				width:300
				
			},{
				xtype:'button',
				text : '查询',
				iconCls:'btn-search',
				scope : this,
				handler : this.search
			},{
				xtype:'button',
				text:'重置',
				iconCls:'btn-reset',
				scope : this,
				handler : function(){
					this.searchPanel.getForm().reset()
				}
			}]
		});

		var url=__ctxPath + '/flow/allTask.do';
		if(this.flag){
			url=__ctxPath + '/flow/findByNameTask.do';
		}
		this.store = new Ext.data.JsonStore({
			baseParams:{
				start:0,
				limit:25,
				flowType:this.flowType,
				taskSequence:this.taskSequence,
				type:this.type,
				resource:this.resource,
				busType:this.busType
			},
			url : url,
			root : 'result',
			totalProperty : 'totalCounts',
			fields : ['taskName', 'activityName', 'assignee',
					'createTime', 'dueDate','taskId','isSigned']
		});
		this.store.load();
		var sm = new Ext.grid.CheckboxSelectionModel({singleSelect:false});
		var cm = new Ext.grid.ColumnModel({columns : [sm,new Ext.grid.RowNumberer(), {
				header : "taskId",
				dataIndex : 'taskId',
				hidden:true
			}, {
				header : '项目名称',
				dataIndex : 'taskName',
				width:600
			}, {
				header : '任务名称',
				dataIndex : 'activityName',
				width:110
			},{
				header : '执行人',
		        align:'center',
				dataIndex : 'assignee',
				renderer : function(value, metadata, record,
						rowIndex, colIndex) {
					var assignee = record.data.assignee;
					if (assignee == null || assignee == '') {
						return '<font color="red">暂无执行人</font>';
					} else {
						return assignee;
					}
				},
				width:100
			}, {
				header : '开始时间',
				align:'center',
				dataIndex : 'createTime',
				width : 150
			}, {
				header : '到期时间',
				dataIndex : 'dueDate',
				width : 150,
				align:'center',
				renderer : function(value) {
					if (value == '') {
						return '无限制';
					} else {
						return value;
					}
				}
			}],
			defaults : {
				sortable : false,
				menuDisabled : true,
				width : 150
			}
		});
		
		this.gridPanel = new Ext.grid.GridPanel({
			id:this.tempGridId,
			region : 'center',
			store : this.store,
			shim : true,
			trackMouseOver : true,
			loadMask : true,
			tbar :this.isHideTopBar?null:new Ext.Toolbar({
				items : [{
					text : '刷新',
					iconCls : 'btn-refresh',
					scope : this,
					handler : this.refresh
				}, '-', {
					text : '设置到期时间',
					scope : this,
					hidden : isGranted('_szdqsj')?false:true,
					handler : this.setDueDate
				},'-',{
					text:'更改待办人',
					scope:this,
					hidden : isGranted('_ggdbr')?false:true,
					handler:this.setHandler
				},'-',{
					text:'更改执行路径',
					scope:this,
					hidden : isGranted('_ggzxlj')?false:true,
					handler:this.setPath
				},'-',{
				    text:'任务代办',
				    scope:this,
				    hidden : isGranted('_rwdb')?false:true,
				    handler:this.handlerTask
					
				}]
			}),
			cm : cm,
			bbar : new HT.PagingBar({
				store : this.store
			}),
			listeners : {
				scope : this,
				rowdblclick : function(grid,rowIndex, e) {
					this.handlerTask.call(this);
				}
			}
		});
		
		
			
	},//end of initUIComponents
	search : function() {
		
		var taskName=this.searchPanel.getCmpByName('taskName');
		
		this.store.baseParams={
			start:0,
			limit:this.store.baseParams.limit,
			taskName:taskName.getValue()
		};
		this.store.reload();
	},
	refresh : function() {
		this.store.reload();
	},
	//为任务设置过期时间
	setDueDate : function() {
		var taskGrid=this.gridPanel;
		var rs = taskGrid.getSelectionModel().getSelections();
		if(rs.length==0){
			Ext.ux.Toast.msg('操作信息','请选择任务记录!');
			return;
		}
		new TaskDueDateWindow().show();
	},
	//为任务设置待办人
	setHandler:function(){
		var taskGrid=this.gridPanel;
		var rs = taskGrid.getSelectionModel().getSelections();
		if(rs.length==0){
			Ext.ux.Toast.msg('操作信息','请选择任务记录!');
			return;
		}
		new TaskHandlerWindow().show();
	},
	
	setPath:function(){
		var taskGrid=this.gridPanel;
		var rs = taskGrid.getSelectionModel().getSelections();
		if(rs.length==0){
			Ext.ux.Toast.msg('操作信息','请选择任务记录!');
			return;
		}
		if(rs[0].data.isSigned==1){
			Ext.ux.Toast.msg('操作信息','此节点为会签节点，不能更改执行路径！');
			return;
		}
		new PathChangeWindow({taskId:rs[0].data.taskId,taskGrid:taskGrid,activityName:rs[0].data.activityName}).show();
	},
	handlerTask:function(){
		var taskGrid=this.gridPanel;
	    var rs = taskGrid.getSelectionModel().getSelections();
		if(rs.length==0){
			Ext.ux.Toast.msg('操作信息','请选择任务记录!');
			return;
		}
		if(rs.length>1){
			Ext.ux.Toast.msg('操作信息','只能选择一条任务记录!');
			return;
		}
		var record=rs[0];
		var contentPanel=App.getContentPanel();
		var formView=contentPanel.getItem('ProcessNextForm'+record.data.taskId);
		if(formView==null){
			formView=new ProcessNextForm({taskId:record.data.taskId,activityName:record.data.activityName,agentTask:true,projectName:record.data.taskName});
			contentPanel.add(formView);
		}
		contentPanel.activate(formView);
	}

});