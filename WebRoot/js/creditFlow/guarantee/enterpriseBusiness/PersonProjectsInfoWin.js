/**
 * @author
 * @createtime
 * @class PersonProjectsInfoWin
 * @extends Ext.Window
 * @description PersonProjectsInfoWin我方抵质押物参照表单
 * @company 智维软件
 */
PersonProjectsInfoWin = Ext.extend(Ext.Window, {
	// 构造函数
	constructor : function(_cfg) {
		if(typeof(_cfg.assignId)!="undefined"){
           this.assignId=_cfg.assignId;
        }
        if(typeof(_cfg.userFullName)!="undefined"){
           this.userFullName=_cfg.userFullName;
        }
		Ext.applyIf(this, _cfg);
		// 必须先初始化组件
		this.initUIComponents();
		PersonProjectsInfoWin.superclass.constructor.call(this, {
					id : 'PersonProjectsInfoWinWin',
					layout : 'fit',
					items : this.gridPanel,
					modal : true,
					height : 450,
					width : 900,
					maximizable : true,
					title : '<font color=red><'+this.userFullName+'></font>'+'项目处理情况'
				});
	},// end of the constructor
	// 初始化组件
	initUIComponents : function() {

		this.gridPanel = new HT.GridPanel({
			region : 'center',
			isShowTbar : false,
			border : false,
			hiddenCm : true,
			// 使用RowActions
			//rowActions : true,
			id : 'PersonProjectsInfoWinGrid',
			url : __ctxPath
							+ "/flow/userActivityAllTask.do?processName=" + processNameFlowKey+'&assignId='+this.assignId,
			fields : [{
						name : 'task.taskId',
						type : 'int'
					}, 'task.dueDate', 'task.activityName',
					'vCommonProjectFlow.runId',
					'vCommonProjectFlow.taskCreateTime',
					'vCommonProjectFlow.projectId',
					'vCommonProjectFlow.subject',
					'vCommonProjectFlow.customerName',
					'vCommonProjectFlow.projectNumber',
					//'vCommonProjectFlow.operationTypeValue',//该字段视图没有，去掉。
					'vCommonProjectFlow.activityName',
					'vCommonProjectFlow.businessType',
					'vCommonProjectFlow.projectName'],
			columns : [{
								header : '项目Id',
								dataIndex : 'vCommonProjectFlow.projectId',
								hidden : true
							}, {
								header : 'runId',
								dataIndex : 'vCommonProjectFlow.runId',
								hidden : true
							}, {
								header : '项目名称',
								dataIndex : 'vCommonProjectFlow.projectName',
								width : 460
							}, {
								header : '项目编号',
								dataIndex : 'vCommonProjectFlow.projectNumber',
								width : 180
							}, {
								header : '任务名称',
								dataIndex : 'task.activityName',
								width : 190
							}, {
								header : '任务分配时间',
								dataIndex : 'vCommonProjectFlow.taskCreateTime',
								width : 126
							}, {
								header : '完成时限',
								dataIndex : 'task.dueDate',
								width : 126
							}]
				// end of columns
		});
		this.gridPanel.addListener('rowdblclick', this.rowClick);
	},// end of the initcomponents

	// GridPanel行点击处理事件
	rowClick : function(grid, rowindex, e) {},
	// 行的Action
	onRowAction : function(grid, record, action, row, col) {
		switch (action) {
			case 'btn-del' :
				this.save.call(this, record);
				break;
			case 'btn-edit' :
				this.save.call(this, record);
				break;
			default :
				break;
		}
	}
});