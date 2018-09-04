//ProcessTaskWorkbenchControl
ProcessTaskWorkbenchControl = Ext.extend(Ext.Panel, {
	constructor : function(conf) {
		Ext.applyIf(this, conf);
		this.initUIComponents();
		ProcessTaskWorkbenchControl.superclass.constructor.call(this, {
					id:'ProcessTaskWorkbenchControl',
					title : '流程任务工作台监控',
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
							columnWidth : .3,
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
							columnWidth : 0.3,
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
									name : 'endDate',
									flex : 1,
									editable : false,
									width:105,
									xtype :'datefield',
									format : 'Y-m-d',
									anchor : '100%'
									
							}] 
							      
						},{  
							columnWidth : 0.3,
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
							columnWidth : .1,
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
					url : __ctxPath + '/flow/moreFeildListTask.do',
					root : 'result',
					totalProperty : 'totalCounts',
					fields : ['taskName', 'activityName', 'assignee',
							'createTime', 'dueDate', 'executionId', 'pdId',
							'taskId', 'isMultipleTask']
				});
		this.store.load();
		var sm = new Ext.grid.CheckboxSelectionModel({singleSelect:false});
		var cm = new Ext.grid.ColumnModel({
					columns : [sm,new Ext.grid.RowNumberer(), {
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
								width:80
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
								align:'center',
								dataIndex : 'dueDate',
								width : 150,
								renderer : function(value) {
									if (value == '') {
										return '无限制';
									} else {
										return value;
									}
								}
							}
//							, {
//								header : '管理',
//								dataIndex : 'taskdbid',
//								width : 80,
//								renderer : function(value, metadata, record,rowIndex, colIndex) {
//								}
//							}
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
		var startDate=this.searchPanel.getCmpByName('startDate').getValue();
		if(startDate!=null&&startDate!="undefined"&&startDate!=""){
			startDate=startDate.format('Y-m-d');
		}
		var endDate=this.searchPanel.getCmpByName('endDate').getValue();
		if(endDate!=null&&endDate!=""&&endDate!="undefined"){
			endDate=endDate.format('Y-m-d');
		}
		window.open(__ctxPath + '/flow/processExportExcelTask.do?taskName='+taskName+"&projectName="+projectName+"&userId="+userId+"&startDate="+startDate+"&endDate="+endDate,'_blank');
		
	},
		// 查看任务流转记录 taskName
	flowRecords : function() {
		var selRs = this.gridPanel.getSelectionModel().getSelections();
		if (selRs.length == 0) {
			Ext.ux.Toast.msg('操作信息', '请选择记录！');
			return;
		}
		if (selRs.length > 1) {
			Ext.ux.Toast.msg('操作信息', '只能选择一条记录！');
			return;
		}
		
		Ext.Ajax.request({
			url : __ctxPath + '/flow/getByTaskIdProcessRun.do',
			params : {
				taskId : selRs[0].get('taskId')
			},
			method : 'post',
			success : function(response, request) {
				obj = Ext.util.JSON.decode(response.responseText);
				var runId = obj.data.runId;
				var businessType = obj.data.businessType;
				if(obj.success){
					new SlProcessRunView({
						runId : runId,
						businessType : businessType
					}).show();
				}else{
					Ext.ux.Toast.msg('操作信息', '查询runId、businessType操作失败!');
				}
			},
			failure : function() {
				Ext.ux.Toast.msg('信息提示', '出错，请联系管理员！');
			}
		})
	},
	// 显示项目流程图
	showFlowImg : function() {
		var selRs = this.gridPanel.getSelectionModel().getSelections();
		if (selRs.length == 0) {
			Ext.ux.Toast.msg('操作信息', '请选择记录！');
			return;
		}
		if (selRs.length > 1) {
			Ext.ux.Toast.msg('操作信息', '只能选择一条记录！');
			return;
		}
		
		Ext.Ajax.request({
			url : __ctxPath + '/flow/getByTaskIdProcessRun.do',
			params : {
				taskId : selRs[0].get('taskId')
			},
			method : 'post',
			success : function(response, request) {
				obj = Ext.util.JSON.decode(response.responseText);
				if(obj.success){
					var runId = obj.data.runId;
					var businessType = obj.data.businessType;
					var flowImagePanel = new Ext.Panel({
					autoHeight : true,
					border : false,
					html : '<img src="' + __ctxPath + '/jbpmImage?runId='
							+ runId
							+ '&rand=' + Math.random() + '"/>'
				});

		var panel = new Ext.Panel({
					autoHeight : true,
					layout : 'form',
					border : false,
					items : [flowImagePanel]
				});

		// 若当前为子流程，则显示子流程
		if (this.isSubFlow) {
			panel.add({
						xtype : 'panel',
						autoHeight : true,
						border : false,
						html : '<img src="' + __ctxPath + '/jbpmImage?runId='
								+ runId
								+ '&isSubFlow=true&rand=' + Math.random()
								+ '"/>'
					});
			panel.doLayout();
		}

		new Ext.Window({
					autoScroll : true,
					iconCls : 'btn-flow-chart',
					bodyStyle : 'background-color:white',
					maximizable : true,
					title : '流程示意图',
					width : 800,
					height : 600,
					modal : true,
					layout : 'fit',
					items : panel
				}).show();
				}else{
					Ext.ux.Toast.msg('操作信息', '查询runId、businessType操作失败!');
				}
			},
			failure : function() {
				Ext.ux.Toast.msg('信息提示', '出错，请联系管理员！');
			}
		})
	}

});
