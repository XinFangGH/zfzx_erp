/**
 * @author YungLocke
 * @class ProInstanceDetail
 * @extends Ext.Window
 */
ProInstanceDetail=Ext.extend(Ext.Window,{
     gridPanel:null,
     constructor:function(_cfg){
         Ext.applyIf(this,_cfg);
         this.initUI();
         ProInstanceDetail.superclass.constructor.call(this,{
               title:this.subject+'--任务列表',
               width:800,
               modal:true,
               maximizable : true,
               height:400,
               iconCls:'menu-instance',
               layout:'border',
               defaults:{
                 padding:'6',
                 anchor:'98%,98%'
               },
               items:[this.showPanel],
               buttonAlign : 'center',
			   buttons : [{
							text : '关闭',
							iconCls : 'close',
							scope : this,
							handler : this.cancel
						}]
         });
     },
     initUI:function(){
     	 this.topbar = new Ext.Toolbar({
			items : [{
						iconCls : 'btn-refresh',
						text : '刷新',
						xtype : 'button',
						scope : this,
						handler : this.refreshRs
					}, '-',  {
						iconCls : 'btn-detail',
						text : '查看',
						xtype : 'button',
						scope : this,
						handler : this.detailRsM
					}, '-', {
						text : '设置到期时间',
						scope : this,
						handler : this.setDueDate
					},'-',{
						text:'更改待办人',
						scope:this,
						handler:this.setHandler
					},'-',{
						text:'更改执行路径',
						scope:this,
						handler:this.setPath
					},'-',{
					    text:'任务代办',
					    scope:this,
					    handler:this.handlerTask
						
					}]
		 });
         this.gridPanel = new HT.GridPanel({
			region : 'center',
			anchor: '100% 100%',
			rowActions : true,
			tbar:this.topbar,
			autoScroll:true,
			showPaging:false,
			url : __ctxPath + '/flow/tasksProcessRun.do',
			baseParams:{runId:this.runId},
			fields : ['taskName', 'activityName', 'assignee',
							'createTime', 'dueDate', 'executionId', 'pdId',
							'taskId', 'isMultipleTask'],
			columns : [new Ext.grid.RowNumberer(), {
								header : "taskId",
								dataIndex : 'taskId',
								hidden:true
							}, {
								header : '任务名称',
								dataIndex : 'taskName',
								width:350
							}, {
								header : '执行人',
								width : 70,
								fixed:true,
								dataIndex : 'assignee',
								renderer : function(value, metadata, record,
										rowIndex, colIndex) {
									var assignee = record.data.assignee;
									if (assignee == null || assignee == '') {
										return '<font color="red">暂无执行人</font>';
									} else {
										return assignee;
									}
								}
							}, {
								header : '开始时间',
								dataIndex : 'createTime',
								width : 125,
								fixed:true,
			    				resizable:false
							}, {
								header : '到期时间',
								fixed:true,
			    				resizable:false,
			    				width:125,
								dataIndex : 'dueDate',
								renderer : function(value) {
									if (value == '') {
										return '无限制';
									} else {
										return value;
									}
								}
							}, new Ext.ux.grid.RowActions({
								header : '管理',
								width : 32,
								fixed:true,
			    				resizable:false,
								actions : [{
											iconCls : 'btn-detail',
											qtip : '查看',
											style : 'margin:0 3px 0 3px'
										}],
								listeners : {
									scope : this,
									'action' : this.onRowAction
								}
							})]
				// end of columns
		});
         this.showPanel=new Ext.FormPanel({
     	    layout:'hbox',
     	    region:'center',
     	    layoutConfig:{
        	    align:'stretch'
        	 },
     	    items:[{
     	       xtype:'fieldset',
     	       title:'任务列表',
     	       flex:1,
     	       layout:'form',
     	       items:[this.gridPanel]
     	    }]
     	 
     	 
     	 });
     },
     refreshRs:function(){
        this.gridPanel.getStore().reload();
     },
     detailRsM:function(){
        var rs = this.gridPanel.getSelectionModel().getSelections();
		if(rs.length==0){
			Ext.ux.Toast.msg('操作信息','请选择任务记录!');
			return;
		}
		if(rs.length>1){
			Ext.ux.Toast.msg('操作信息','只能选择一条任务记录!');
			return;
		}
		this.detailRs(rs[0]);
		
     },
     detailRs:function(record){
        new FlowFormDetail({
           taskId:record.data.taskId,
           activityName:record.data.activityName
        }).show();
     },
     cancel : function() {
				this.close();
	 },
	 //为任务设置过期时间
	setDueDate : function() {
		var rs = this.gridPanel.getSelectionModel().getSelections();
		if(rs.length==0){
			Ext.ux.Toast.msg('操作信息','请选择任务记录!');
			return;
		}
		new TaskDueDateWindow({
		   taskGrid:this.gridPanel
		}).show();
	},
	//为任务设置待办人
	setHandler:function(){
		var rs = this.gridPanel.getSelectionModel().getSelections();
		if(rs.length==0){
			Ext.ux.Toast.msg('操作信息','请选择任务记录!');
			return;
		}
		new TaskHandlerWindow({taskGrid:this.gridPanel}).show();
	},
	
	setPath:function(){
		var rs = this.gridPanel.getSelectionModel().getSelections();
		if(rs.length==0){
			Ext.ux.Toast.msg('操作信息','请选择任务记录!');
			return;
		}
		new PathChangeWindow({taskId:rs[0].data.taskId,taskGrid:this.gridPanel}).show();
	},
	handlerTask:function(){
	    var rs = this.gridPanel.getSelectionModel().getSelections();
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
			formView=new ProcessNextForm({taskId:record.data.taskId,activityName:record.data.activityName});
			contentPanel.add(formView);
		}
		contentPanel.activate(formView);
		
		this.close();
	},
     // 行的Action
	onRowAction : function(grid, record, action, row, col) {
		switch (action) {
			case 'btn-detail' :
				this.detailRs.call(this, record);
				break;
			default :
				break;
		}
	}
});

FlowFormDetail=Ext.extend(Ext.Window,{
	constructor:function(_cfg){
	   Ext.applyIf(this,_cfg);
	   this.initUI();
	   FlowFormDetail.superclass.constructor.call(this,{
	       layout:'form',
	       width:800,
	       height:500,
	       iconCls:'menu-instance',
	       modal:true,
	       maximizable : true,
	       defaults:{
	          padding:'6'
	       },
	       autoScroll:true,
	       title : this.activityName+'--任务详细信息',
	       items:[this.formPanel,this.detailPanel],
	       buttonAlign : 'center',
		   buttons : [{
						text : '关闭',
						iconCls : 'close',
						scope : this,
						handler : this.cancel
					}]
	   });
	},
	initUI:function(){
		this.formPanel=new Ext.FormPanel({
			layout:'form',
			border:false,
			bodyStyle:'padding:8px',
			autoHeight:true,
			items:[
				{
					xtype:'fieldset',
					title:'业务表单',
					layout:'form',
					anchor:'90%,90%',
					autoScroll:true,
					autoLoad:{
						url:__ctxPath+ "/flow/getProcessActivity.do?taskId="+this.taskId,
						nocache: true,
						scope:this,
						callback:this.getFormHtmlCallback
					}
				}
			]
		});
		
		// 显示流程审批的表单
		this.detailPanel=new Ext.Panel({
			title:'审批信息',
			autoHeight:true,
			autoLoad:{
				url:__ctxPath+'/flow/processRunDetail.do?taskId='+this.taskId,
				nocache: true
			}
		});
	},
	cancel:function(){this.close();},
	getFormHtmlCallback:function(){
		//回填数据
		var form=this.formPanel.getForm().getEl().dom;
		var formPanel=this.formPanel;
		var fElements = form.elements || (document.forms[form] || Ext.getDom(form)).elements;
		try{
			var json=document.getElementById('entity_'+this.taskId);
			var name,type,value,xtype;
			if(json!=null){
				if(json.value!=''){
					 var entityJson=Ext.decode(json.value);
				    $converDetail.call(this,entityJson);
				}else{
				   $converDetail.call(this,null);
				}
			}
		}catch(e){
			alert(e);
		}
	}
});
