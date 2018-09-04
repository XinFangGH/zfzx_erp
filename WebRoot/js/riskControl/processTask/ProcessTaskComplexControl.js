ProcessTaskComplexControl = Ext.extend(Ext.Panel, {
	constructor : function(conf) {
		Ext.applyIf(this, conf);
		this.initUIComponents();
		ProcessTaskComplexControl.superclass.constructor.call(this, {
					id:'ProcessTaskComplexControl',
					title : '流程任务综合监控',
					iconCls:"btn-tree-team36",
					layout : 'border',
					items : [this.searchPanel, this.gridPanel]
				});
	},
	initUIComponents : function() {
				
		this.searchPanel = new HT.SearchPanel({
						layout : 'column',
						style : 'padding-left:5px;padding-right:5px;padding-top:5px;',
						region : 'north',
						height : 20,
						anchor : '96%',
						layoutConfig: {
			               align:'middle',
			               padding : '5px'
			               
			            },
						items : [{  
							columnWidth : 0.18,
							layout : 'form',
							border : false,
							labelWidth : 80,
							labelAlign : 'right',
							items : [{
									fieldLabel : '任务状态',
									hiddenName : 'status',
									readOnly : this.isReadOnly,
									anchor : '100%',
									xtype : 'combo',
									mode : 'local',
									valueField : 'value',
									editable : false,
									displayField : 'item',
									store : new Ext.data.SimpleStore({
												fields : ["item", "value"],
												data : [["等待处理", "0"], ["审批通过", "1"],
														["流程跳转", "2"],["打回重做", "3"], ["任务换人", "4"],
														["项目换人", "5"]]
											}),
									triggerAction : "all"
		
								},{
									fieldLabel : '分配日期',
									name : 'startDate',
									flex : 1,
									editable : false,
									width:105,
									xtype :'datefield',
									format : 'Y-m-d',
									anchor : '100%'
									
							}] 
							      
						},{  
							columnWidth : 0.18,
							layout : 'form',
							border : false,
							labelWidth : 80,
							labelAlign : 'right',
							items : [{
									fieldLabel : '异常状态',
									hiddenName : 'taskstatus',
									readOnly : this.isReadOnly,
									anchor : '100%',
									xtype : 'combo',
									mode : 'local',
									valueField : 'value',
									editable : false,
									displayField : 'item',
									store : new Ext.data.SimpleStore({
												fields : ["item", "value"],
												data : [["正常任务", "1"], ["逾期任务", "2"]]
											}),
									triggerAction : "all"
		
								},{
									fieldLabel : '至',
									name : 'endDate',
									flex : 1,
									editable : false,
									width:105,
									xtype :'datefield',
									format : 'Y-m-d',
									anchor : '100%'
									
							}] 
							      
						},{
							columnWidth : .18,
							layout : 'form',
							border : false,
							labelWidth : 80,
							labelAlign : 'right',
							items : [{
										xtype : "combo",
										triggerClass : 'x-form-search-trigger',
										hiddenName : "followPersonId17",
										editable : false,
										fieldLabel : "处理人",
										anchor : "100%",
										onTriggerClick : function(cc) {
											var obj = this;
											var appuerIdObj = obj.nextSibling();
											var userIds = appuerIdObj.getValue();
											if ("" == obj.getValue()) {
												userIds = "";
											}
											new UserDialog({
												userIds : userIds,
												userName : obj.getValue(),
												single : true,
												title : "选择处理人",
												callback : function(uId, uname) {
													obj.setValue(uId);
													obj.setRawValue(uname);
													appuerIdObj.setValue(uId);
												}
											}).show();
										}
									},{
			                       	 	xtype : 'hidden',
			                        	name : 'userId'
									},{
									fieldLabel : '完成日期',
									name : 'finishStartDate',
									flex : 1,
									editable : false,
									width:105,
									xtype :'datefield',
									format : 'Y-m-d',
									anchor : '100%'
									
							}]
						},{  
							columnWidth : 0.18,
							layout : 'form',
							border : false,
							labelWidth : 80,
							labelAlign : 'right',
							items : [{
									fieldLabel : '项目名称',
									name : 'projectName',
									flex : 1,
									editable : false,
									width:105,
									xtype :'textfield',
									anchor : '100%'
									
							},{
									fieldLabel : '至',
									name : 'finishEndDate',
									flex : 1,
									editable : false,
									width:105,
									xtype :'datefield',
									format : 'Y-m-d',
									anchor : '100%'
									
							}] 
							      
						},{  
							columnWidth : 0.2,
							layout : 'form',
							border : false,
							labelWidth : 80,
							labelAlign : 'right',
							items : [{
									fieldLabel : '任务名称',
									name : 'taskName',
									flex : 1,
									editable : false,
									width:105,
									xtype :'textfield',
									anchor : '100%'
									
							}] 
							      
						}, {
							columnWidth : .08,
							xtype : 'container',
							layout : 'form',
							defaults : {
									xtype : 'button'
							},
							style : 'padding-left:10px;',
							items : [{
										text : '查询',
										scope : this,
										iconCls : 'btn-search',
										handler : this.search
									},{
										text : '重置',
										scope : this,
										iconCls : 'btn-reset',
										handler : this.reset
									}]
						}]
	
					});
		this.store = new Ext.data.JsonStore({
					baseParams:{
						start:0,
						limit:25
					},
					url : __ctxPath + '/flow/getAllProcessTask.do',
					root : 'result',
					totalProperty : 'totalCounts',
					fields : ['projectName', 'activityName', 'createtime',
							'endtime', 'taskLimitTime', 'durtimes', 'status',
							'creatorName', 'formId','processRunId','minTime','businessType']
				});
		this.store.load();
		var sm = new Ext.grid.CheckboxSelectionModel({singleSelect:false});
		var cm = new Ext.grid.ColumnModel({
					columns : [sm,new Ext.grid.RowNumberer(), {
								header : "formId",
								align:'center',
								dataIndex : 'formId',
								hidden:true
							}, {
								header : "processRunId",
								align:'center',
								dataIndex : 'processRunId',
								hidden:true
							},{
								header : '任务状态',
								dataIndex : 'status',
								align:'center',
								width:80,
								renderer : function(value, metadata, record,
										rowIndex, colIndex) {
									var assignee = record.data.status;
									 if(assignee==-1){
										return "驳回";
									}else if(assignee==0){
										return "等待处理";
									}else if(assignee==1){
										return "审批通过";
									}else if(assignee==2){
										return "流程跳转";
									}else if(assignee==3){
										return "打回重做";
									}else if(assignee==4){
										return "追回";
									}else if(assignee==5){
										return "任务换人";
									}else if(assignee==6){
										return "项目换人";
									}else if(assignee==7){
										return "项目终止";
									}
								}
							}, {
								header : '项目名称',
								dataIndex : 'projectName',
								width:300
							}, {
								header : '任务名称',
								dataIndex : 'activityName',
								width:120
							},{
								header : '处理人',
								dataIndex : 'creatorName',
								renderer : function(value, metadata, record,
										rowIndex, colIndex) {
									var assignee = record.data.creatorName;
									if (assignee == null || assignee == '') {
										return '<font color="red">暂无处理人</font>';
									} else {
										return assignee;
									}
								},
								width:80
							}, {
								header : '分配时间',
								align:'center',
								dataIndex : 'createtime',
								width : 120
							}, {
								header : '到期时间',
								align:'center',
								dataIndex : 'taskLimitTime',
								width : 120,
								renderer : function(value) {
									if (value == '') {
										return '---';
									} else {
										return value;
									}
								}
							}, {
								header : '实际完成时间',
								dataIndex : 'endtime',
								align:'center',
								width : 120,
								renderer : function(value) {
									if (value == '') {
										return '';
									} else {
										return value;
									}
								}
							}, {
								header : '耗时',
								dataIndex : 'durtimes',
								width :80,
								renderer : function(value) {
									if (value == '') {
										return '---';
									} else {
										//(value/1000)>60?(value/1000/60)>60?((value/1000/60/60)>24?((value/1000/60/60/24)+"天"):(value/1000/60/60)+"时"):(value/1000/60+"分"):(value/1000+"秒")
										return (value/1000)>60?(value/1000/60)>60?((value/1000/60/60)>24?((value/1000/60/60/24).toFixed(2)+"天"):(value/1000/60/60).toFixed(2)+"时"):((value/1000/60).toFixed(2)+"分"):((value/1000).toFixed(2)+"秒");
									}
								}
							}, {
								header : '异常状态',
								dataIndex : 'minTime',
								align:'center',
								width : 80,
								renderer : function(value, metadata, record,
										rowIndex, colIndex) {
										return value>0?"正常任务":"逾期任务";	
									
								}
							}

							],
					defaults : {
						sortable : false,
						menuDisabled : true,
						width : 150
					}
				});
		
		this.gridPanel = new Ext.grid.GridPanel({
					id:'allTaskGrid',
					region : 'center',
					store : this.store,
					shim : true,
					trackMouseOver : true,
					loadMask : true,
					tbar : new Ext.Toolbar({
								items : [{
											iconCls : 'btn-advice',
											text : '意见与说明记录',
											xtype : 'button',
											scope : this,
											handler : this.flowRecords
										}, '-', {
											iconCls : 'btn-flow-chart',
											text : '流程示意图',
											xtype : 'button',
											scope : this,
											handler : this.showFlowImg
										}, '-', {
											iconCls : 'btn-xls',
											text : '导出excel',
											xtype : 'button',
											scope : this,
											handler : this.toExcel
										}]
							}),
					cm : cm,
					bbar : new HT.PagingBar({
								store : this.store
							}),
									
					viewConfig:{
					 forceFit: true // 注意不要用autoFill:true,那样设置的话当GridPanel的大小变化（比如你resize了它）时不会自动调整column的宽度
					 //scrollOffset: 0 //不加这个的话，会在grid的最右边有个空白，留作滚动条的位置
					  }
					 // multiSelect:true
				});
		
		
			
	},//end of initUIComponents
		// 重置查询表单
	reset : function() {
	this.searchPanel.getForm().reset();
	},

	// 查询条件
	search : function() {
		$search({
					searchPanel : this.searchPanel,
					gridPanel : this.gridPanel
				});
	},
	refresh : function() {
		this.store.reload();
	},
	//导出到Excel
	toExcel:function(){
		var userId=this.searchPanel.getCmpByName('userId').getValue();
		var taskName=this.searchPanel.getCmpByName('taskName').getValue();
		var projectName=this.searchPanel.getCmpByName('projectName').getValue();
		
		var status=this.searchPanel.getCmpByName('status').getValue();
		var taskstatus=this.searchPanel.getCmpByName('taskstatus').getValue();
		
		var startDate=this.searchPanel.getCmpByName('startDate').getValue();
		if(startDate!=null&&startDate!="undefined"&&startDate!=""){
			startDate=startDate.format('Y-m-d');
		}
		var endDate=this.searchPanel.getCmpByName('endDate').getValue();
		if(endDate!=null&&endDate!=""&&endDate!="undefined"){
			endDate=endDate.format('Y-m-d');
		}
		
		var finishStartDate=this.searchPanel.getCmpByName('finishStartDate').getValue();
		if(finishStartDate!=null&&finishStartDate!=""&&finishStartDate!="undefined"){
			finishStartDate=finishStartDate.format('Y-m-d');
		}
		var finishEndDate=this.searchPanel.getCmpByName('finishEndDate').getValue();
		if(finishEndDate!=null&&finishEndDate!=""&&finishEndDate!="undefined"){
			finishEndDate=finishEndDate.format('Y-m-d');
		}
		window.open(__ctxPath + '/flow/allExportExcelTask.do?taskName='+taskName+"&userId="+userId+"&projectName="+projectName+"&taskstatus="+taskstatus+"&startDate="+startDate+"&endDate="+endDate+"&finishStartDate="+finishStartDate+"&finishEndDate="+finishEndDate+"&status="+status,'_blank');
		
	},
	//查看任务流转记录
			flowRecords : function() {
				var selRs = this.gridPanel.getSelectionModel().getSelections();
				if(selRs.length==0){
				   Ext.ux.Toast.msg('操作信息','请选择记录！');
				   return;
				}
				if(selRs.length>1){
				   Ext.ux.Toast.msg('操作信息','只能选择一条记录！');
				   return;
				}
				new SlProcessRunView({runId : selRs[0].get('processRunId'),businessType : selRs[0].get('businessType')}).show();
				//new SlProcessFormView({runId : selRs[0].get('vCommonProjectFlow.runId'),title : '查看任务流转记录',isHiddenComments : true,isHiddenCreatorName : false}).show();
			},
	//显示项目流程图
	showFlowImg : function() {
				var selRs = this.gridPanel.getSelectionModel().getSelections();
				if(selRs.length==0){
				   Ext.ux.Toast.msg('操作信息','请选择记录！');
				   return;
				}
				if(selRs.length>1){
				   Ext.ux.Toast.msg('操作信息','只能选择一条记录！');
				   return;
				}
				var flowImagePanel=new Ext.Panel({
					autoHeight:true,
					border:false,
					html:'<img src="'+__ctxPath+ '/jbpmImage?runId='+selRs[0].get('processRunId')+ '&rand=' + Math.random()+ '"/>'
				});
				
				var panel=new Ext.Panel({
						autoHeight:true,
						layout:'form',
						border:false,
						items:[
							flowImagePanel
						]
					}
				);
				
				//若当前为子流程，则显示子流程
				if(this.isSubFlow){
					panel.add({
						xtype:'panel',
						autoHeight:true,
						border:false,
						html:'<img src="'+__ctxPath+ '/jbpmImage?runId='+selRs[0].get('runId')+ '&isSubFlow=true&rand=' + Math.random()+ '"/>'
					});
					panel.doLayout();
				}
				
				new Ext.Window({
					autoScroll:true,
						iconCls:'btn-flow-chart',
						bodyStyle:'background-color:white',
						maximizable : true,
						title:'流程示意图',
						width:800,
						height:600,
						modal:true,
						layout:'fit',
						items:panel
				}).show();
			}

});
