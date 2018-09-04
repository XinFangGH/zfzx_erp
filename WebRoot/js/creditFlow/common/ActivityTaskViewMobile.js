/**
 * @author lisl
 * @extends Ext.Panel
 * @description 待处理任务
 * @company 智维软件
 * @createtime:
 */
ActivityTaskViewMobile = Ext.extend(Ext.Panel, {
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		// 初始化组件
		this.initUIComponents();

		Date.prototype.format = function(format) {
			var o = {
				"M+" : this.getMonth() + 1, // month
				"d+" : this.getDate(), // day
				"h+" : this.getHours(), // hour
				"m+" : this.getMinutes(), // minute
				"s+" : this.getSeconds(), // second
				"q+" : Math.floor((this.getMonth() + 3) / 3), // quarter
				"S" : this.getMilliseconds()
				// millisecond
			}
			if (/(y+)/.test(format))
				format = format.replace(RegExp.$1, (this.getFullYear() + "")
								.substr(4 - RegExp.$1.length));
			for (var k in o)
				if (new RegExp("(" + k + ")").test(format))
					format = format.replace(RegExp.$1, RegExp.$1.length == 1
									? o[k]
									: ("00" + o[k]).substr(("" + o[k]).length));
			return format;
		};

		compareDateTime = function(dueDate) {
			var dateNow = new Date();
			var now = new Date();
			var limit = new Date(dueDate);
			var nowFormat = now.format('yyyy-MM-dd');
			var limitFormat = limit.format('yyyy-MM-dd');
			var s1 = nowFormat.replace(/-/g, "/");
			var s2 = limitFormat.replace(/-/g, "/");
			s1 = new Date(s1);
			s2 = new Date(s2);
			if (s1 > s2) {
				return true;
			} else {
				return false;
			}
		}
		// 调用父类构造
		ActivityTaskViewMobile.superclass.constructor.call(this, {
					id : 'ActivityTaskViewMobile',
					title : '待办任务',
					region : 'center',
					layout : 'border',
					iconCls : 'btn-tree-team2',
					items : [this.gridPanel]
				});
	},// end of constructor
	// 初始化组件
	initUIComponents : function() {
		this.topbar = new Ext.Toolbar({
					items : [{
								iconCls : 'menu-flowWaitc',
								text : '处理任务',
								xtype : 'button',
								scope : this,
								// hidden : isGranted('_detailPro') ? false :
								// true,
								handler : this.detailPro
							}]
				});

		this.searchPanel = new Ext.form.FormPanel({
					height : 40,
					border : false,
					region : 'north',
					layout : 'hbox',
					layoutConfig : {
						align : 'middle',
						pack : 'left'
					},
					style : 'background-color:white;padding:5px;',
					defaults : {
						margins : '0px 8px 0px 4px'
					},
					items : [{
								xtype : 'label',
								text : '项目名称:'
							}, {
								xtype : 'textfield',
								name : 'projectName',
								width : 200
							}, {
								xtype : 'label',
								text : '项目编号:'
							}, {
								xtype : 'textfield',
								name : 'projectNumber',
								width : 200
							}, {
								xtype : 'button',
								text : '查询',
								iconCls : 'btn-search',
								scope : this,
								handler : this.search
							}, {
								xtype : 'button',
								text : '重置',
								iconCls : 'btn-reset',
								scope : this,
								handler : this.reset
							}]
				});

		this.gridPanel = new HT.GridPanel({
					region : 'center',
					tbar : this.topbar,
					// 使用RowActions
					rowActions : false,
					notmask : true,
					// loadMask :true,
					root : 'result',
					totalProperty : 'totalCounts',
					id : 'ActivityTaskGrid',
					url : __ctxPath
							+ "/flow/userActivityAllTask.do?processName="
							+ processNameFlowKey,
					/*
					 * baseParams:{ 'Q_runStatus_SN_EQ':1 },
					 */
					fields : ['taskName', 'activityName', 'assignee',
							'createTime', 'dueDate', 'executionId', 'pdId',
							'taskId', 'isMultipleTask'/*
														 * { name :
														 * 'task.taskId', type :
														 * 'int' },
														 * 'task.dueDate',
														 * 'task.activityName',
														 * 'task.createTime',
														 * 'vCommonProjectFlow.runId',
														 * 'vCommonProjectFlow.taskCreateTime',
														 * 'vCommonProjectFlow.projectId',
														 * 'vCommonProjectFlow.subject',
														 * 'vCommonProjectFlow.customerName',
														 * 'vCommonProjectFlow.projectNumber', //
														 * 'vCommonProjectFlow.operationTypeValue',//视图没有，删除掉。
														 * 'vCommonProjectFlow.activityName',
														 * 'vCommonProjectFlow.businessType',
														 * 'vCommonProjectFlow.projectName'
														 */],// 'vCommonProjectFlow.creator',
					columns : [/*
								 * { header : '项目Id', dataIndex :
								 * 'vCommonProjectFlow.projectId', hidden : true }, {
								 * header : 'runId', dataIndex :
								 * 'vCommonProjectFlow.runId', hidden : true }, {
								 * header : 'projectNumber', dataIndex :
								 * 'task.processRun.projectNumber', width : 120,
								 * hidden : true, renderer : function(value,
								 * metadata, record,rowIndex, colIndex) {
								 * alert("runId==="+value); return value; } }, {
								 * header : '项目名称', dataIndex :
								 * 'vCommonProjectFlow.projectName', width :
								 * 615, renderer : function(value, metadata,
								 * record,rowIndex, colIndex) { var
								 * dueDate=record.get('task.dueDate');
								 * if(dueDate!=null&&dueDate!=''){
								 * if(compareDateTime(dueDate)){ return '<span
								 * style="color:red;">'+value+'</span>'; }else{
								 * return value; } } return value; } }, { header :
								 * '项目编号', dataIndex :
								 * 'vCommonProjectFlow.projectNumber', width :
								 * 125, renderer : function(value, metadata,
								 * record,rowIndex, colIndex) { var
								 * dueDate=record.get('task.dueDate');
								 * if(dueDate!=null&&dueDate!=''){
								 * if(compareDateTime(dueDate)){ return '<span
								 * style="color:red;">'+value+'</span>'; }else{
								 * return value; } } return value; } },
								 */{
								header : '任务ID',
								dataIndex : 'taskId',
								hidden : true
							}, {
								header : 'executionId',
								dataIndex : 'executionId',
								hidden : true
							}, {
								// header : '项目名称'+'<span
								// style="color:red;">'+"(红色代表任务为超时未处理)"+'</span>',
								header : '项目名称',
								dataIndex : 'taskName',
								width : 400
/*
 * , renderer : function(value, metadata, record,rowIndex, colIndex) { var
 * dueDate=record.get('dueDate'); if(dueDate!=null&&dueDate!=''){
 * if(compareDateTime(dueDate)){ return '<span style="color:red;">'+value+'</span>';
 * }else{ return value; } } return value; }
 */
						}	, {
								header : '任务名称',
								dataIndex : 'activityName',
								width : 100
/*
 * , renderer : function(value, metadata, record,rowIndex, colIndex) { var
 * dueDate=record.get('dueDate'); if(dueDate!=null&&dueDate!=''){
 * if(compareDateTime(dueDate)){ return '<span style="color:red;">'+value+'</span>';
 * }else{ return value; } } return value; }
 */
						}	, {
								header : '任务分配时间',
								dataIndex : 'createTime',
								width : 90
/*
 * , renderer : function(value, metadata, record,rowIndex, colIndex) { var
 * dueDate=record.get('dueDate'); if(dueDate!=null&&dueDate!=''){
 * if(compareDateTime(dueDate)){ return '<span style="color:red;">'+value+'</span>';
 * }else{ return value; } } return value; }
 */
						}	, {
								header : '完成时限',
								dataIndex : 'dueDate',
								width : 90
/*
 * , renderer : function(value, metadata, record,rowIndex, colIndex) { var
 * dueDate=record.get('dueDate'); if(dueDate!=null&&dueDate!=''){
 * if(compareDateTime(dueDate)){ return '<span style="color:red;">'+value+'</span>';
 * }else{ return value; } } return value; }
 */
						}],// end of columns
					listeners : {
						scope : this,
						rowdblclick : function(grid, rowIndex, e) {

							/*
							 * if (true == isGranted('_detailPro') ? false :
							 * true) {
							 * 
							 * return; }
							 */
							this.detailPro.call(this);

						},
						afterrender : function() {
							this.loadMask1 = new Ext.LoadMask(this.gridPanel
											.getEl(), {
										msg : '正在加载数据中······,请稍候······',
										store : this.gridPanel.store,
										removeMask : true
										// 完成后移除
								});
							this.loadMask1.show(); // 显示

						}
					}
				});

	},// end of the initComponents()

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
	/*
	 * reset : function(){ this.searchPanel.getForm().reset(); }, //按条件搜索 search :
	 * function() { $search({ searchPanel:this.searchPanel,
	 * gridPanel:this.gridPanel }); },
	 */
	// 处理任务
	detailPro : function() {
		var ActivityTaskGrid = Ext.getCmp('ActivityTaskGrid');
		var rs = ActivityTaskGrid.getSelectionModel().getSelections();
		if (rs.length == 0) {
			Ext.ux.Toast.msg('操作信息', '请选择任务记录!');
			return;
		}
		if (rs.length > 1) {
			Ext.ux.Toast.msg('操作信息', '只能选择一条任务记录!');
			return;
		}
		var record = rs[0];
		var contentPanel = App.getContentPanel();
		var formView = contentPanel.getItem('ProcessNextForm'
				+ record.get('taskId'));
		if (formView == null) {
			formView = new ProcessNextForm({
						taskId : record.get('taskId'),
						activityName : record.get('activityName'),
						isReturn : true,
						agentTask : true
					});
			contentPanel.add(formView);
			contentPanel.remove(this);
		}
		contentPanel.activate(formView);
	},
	// 查看任务流转记录
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
						if (obj.success) {
							new SlProcessRunView({
										runId : runId,
										businessType : businessType
									}).show();
						} else {
							Ext.ux.Toast.msg('操作信息',
									'查询runId、businessType操作失败!');
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
						if (obj.success) {
							var runId = obj.data.runId;
							var businessType = obj.data.businessType;
							var flowImagePanel = new Ext.Panel({
										autoHeight : true,
										border : false,
										html : '<img src="' + __ctxPath
												+ '/jbpmImage?runId=' + runId
												+ '&rand=' + Math.random()
												+ '"/>'
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
											html : '<img src="' + __ctxPath
													+ '/jbpmImage?runId='
													+ runId
													+ '&isSubFlow=true&rand='
													+ Math.random() + '"/>'
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
						} else {
							Ext.ux.Toast.msg('操作信息',
									'查询runId、businessType操作失败!');
						}
					},
					failure : function() {
						Ext.ux.Toast.msg('信息提示', '出错，请联系管理员！');
					}
				})
	},
	// 行的Action
	onRowAction : function(grid, record, action, row, col) {
		switch (action) {
			case 'btn-detail' :
				this.detailPro.call(this);
				break;
			case 'btn-close' :
				this.stopPro.call(this);
				break;
			case 'btn-flow-chart' :
				this.showFlowImg.call(this);
				break;
			default :
				break;
		}
	}
});
