Ext.ns('MyTaskView');
/**
 * 我的任务流程
 */
var MyTaskView = function() {
	var panel = new Ext.Panel({
				id : 'MyTaskView',
				iconCls : 'menu-flowWait',
				bodyStyle : 'padding:2px 2px 2px 2px',
				layout : 'border',
				region : 'center',
				title : '待办事项',
				autoScroll : true,
				items : [this.setup()]
			});
	return panel;
};

MyTaskView.prototype.setup = function() {
	// 显示一个GridPanel先即可
	var store = this.initData();
	store.load({
				params : {
					start : 0,
					limit : 25
				}
			});
	var cm = new Ext.grid.ColumnModel({
		columns : [new Ext.grid.RowNumberer(), {
					header : "userId",
					dataIndex : 'userId',
					width : 20,
					hidden : true,
					sortable : true
				}, {
					header : '事项名称',
					dataIndex : 'taskName',
					width : 120
				}, {
					header : '执行人',
					dataIndex : 'assignee',
					width : 78,
					renderer : function(value, metadata, record, rowIndex,
							colIndex) {
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
					width : 130
				}, {
					header : '到期时间',
					dataIndex : 'dueDate',
					width : 130,
					renderer : function(value) {
						if (value == '') {
							return '无限制';
						} else {
							return value;
						}
					}
				}, {
					hidden : true,
					dataIndex : 'executionId'
				}, {
					hidden : true,
					dataIndex : 'taskId'
				}, {
					hidden : 'true',
					dataIndex : 'isMultipleTask'
				}, {
					header : '管理',
					dataIndex : 'taskdbid',
					width : 66,
					renderer : function(value, metadata, record, rowIndex,
							colIndex) {
						var taskId = record.data.taskId;
						var exeId = record.data.executionId;
						var assignee = record.data.assignee;
						var activityName = record.data.activityName;
						var isMultipleTask = record.data.isMultipleTask;
						var str = '';
						if (assignee == '') {
							str += '<button title="锁定任务" class="btn-lockTask" onclick="MyTaskView.lockTask('
									+ taskId + ')"></button>';
						} else {
							str += '<button title="审核任务" class="btn-approvalTask" onclick="MyTaskView.nextStep(\''
									+ taskId
									+ '\',\''
									+ activityName
									+ '\')"></button>';

							str += '&nbsp;<button title="任务代办" class="btn-changeTask" onclick="MyTaskView.changeTask('
									+ taskId
									+ ',\''
									+ activityName
									+ '\')"></button>';

							if (isMultipleTask == 1) {// 多人的任务，自己可以解锁由其他人来执行
								str += '&nbsp;<button title="解锁任务" class="btn-unlockTask" onclick="MyTaskView.unlockTask('
										+ taskId + ')"></button>';
							}
						}
						return str;
					}
				}],
		defaults : {
			sortable : true,
			menuDisabled : true,
			width : 100
		}
	});
	// 显示任务
	var grid = new Ext.grid.GridPanel({
				id : 'MyTaskGrid',
				closable : true,
				store : store,
				shim : true,
				region : 'center',
				trackMouseOver : true,
				loadMask : true,
				tbar : new Ext.Toolbar({
							height : 28,
							items : [{
								text : '刷新',
								iconCls : 'btn-refresh',
								handler : function() {
									Ext.getCmp('MyTaskGrid').getStore()
											.reload();
								}
							}]
						}),
				cm : cm,
				viewConfig : {
					forceFit : true,
					showPreview : false
				},
				bbar : new HT.PagingBar({
							store : store,
							printable : true
						})
			});

	grid.addListener('rowdblclick', function(grid, rowindex, e) {
		grid.getSelectionModel().each(function(rec) {
					MyTaskView.nextStep(rec.data.taskId, rec.data.activityName);
				});
	});

	return grid;
};

// var
// processNameMore="smallLoanFlow,smallLoanFast,FinancingFlow,guaranteeNormalFlow";
MyTaskView.prototype.initData = function() {
	var store = new Ext.data.Store({
				proxy : new Ext.data.HttpProxy({
							url : __ctxPath + '/flow/listTask.do?processName='
									+ processNameFlowKey
						}),
				// create reader that reads the Topic records
				reader : new Ext.data.JsonReader({
							root : 'result',
							totalProperty : 'totalCounts',
							fields : ['taskName', 'activityName', 'assignee',
									'createTime', 'dueDate', 'executionId',
									'pdId', 'taskId', 'isMultipleTask']
						}),
				remoteSort : true
			});
	store.setDefaultSort('dbId', 'desc');
	return store;
};

/**
 * 锁定任务，则表示申请执行该任务
 * 
 * @param {}
 *            taskdbid
 */
MyTaskView.lockTask = function(taskId) {
	Ext.Ajax.request({
				url : __ctxPath + '/flow/lockTask.do',
				params : {
					taskId : taskId
				},
				method : 'post',
				success : function(result, response) {
					var grid = Ext.getCmp("MyTaskGrid");
					var resultObj = Ext.util.JSON.decode(result.responseText)
					if (resultObj.hasAssigned == true) {
						Ext.ux.Toast.msg("操作提示", "该任务已经被其他用户锁定执行！");
					} else {
						Ext.ux.Toast.msg("操作提示", "该任务已经成功锁定，请执行下一步操作！");
					}
					grid.getStore().reload();
				}
			});
};

/**
 * 任务变更，则转由代办人来处理
 * 
 * @param {}
 *            taskId
 */
MyTaskView.changeTask = function(taskId, taskname) {
	new ChangeTaskView(taskId, taskname);
};

/**
 * 锁定任务，则表示自己退出执行该任务，其他人员可以申请执行该任务
 * 
 * @param {}
 *            taskdbid
 */
MyTaskView.unlockTask = function(taskId) {
	Ext.Ajax.request({
				url : __ctxPath + '/flow/unlockTask.do',
				params : {
					taskId : taskId
				},
				method : 'post',
				success : function(result, response) {
					var grid = Ext.getCmp("MyTaskGrid");
					var resultObj = Ext.util.JSON.decode(result.responseText)

					if (resultObj.unlocked == true) {
						Ext.ux.Toast.msg("操作提示", "该任务已经成功解锁！");
					} else {
						Ext.ux.Toast.msg("操作提示", "该任务解锁失败(任务已经由其他人员执行完成)！");
					}
					grid.getStore().reload();
				}
			});
};
/**
 * 下一步的任务
 * 
 * @param {}
 *            taskdbid
 */
MyTaskView.nextStep = function(taskId, activityName) {
	var contentPanel = App.getContentPanel();
	var formView = contentPanel.getItem('ProcessNextForm' + taskId);
	if (formView == null) {
		formView = new ProcessNextForm({
					taskId : taskId,
					activityName : activityName
				});
		contentPanel.add(formView);
	}
	contentPanel.activate(formView);
};
