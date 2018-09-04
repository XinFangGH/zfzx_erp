Ext.ns('CalendarPlanView');
/**
 * 今日日程
 */
var TodayPlanView = function() {
	return new Ext.Panel({
		id : 'TodayPlanView',
		title : '今日日程',
		layout:'border',
		region : 'center',
		autoScroll : true,
		items : [this.setup()]
	});
};
/**
 * 建立视图
 */
TodayPlanView.prototype.setup = function() {
	return this.grid();
};
/**
 * 建立DataGrid
 */
TodayPlanView.prototype.grid = function() {
	var sm = new Ext.grid.CheckboxSelectionModel();
	var cm = new Ext.grid.ColumnModel({
		columns : [sm, new Ext.grid.RowNumberer(), {
					header : 'planId',
					dataIndex : 'planId',
					hidden : true
				},  {
					header : '重要程度',
					width:60,
					dataIndex : 'urgent'
				}, {
					header: '时间',
					width: 80 ,
					dataIndex: 'durationTime'
				},{
					width:250,
					header : '内容',
					dataIndex : 'content'
				}, {
					header : '状态',
					dataIndex : 'status'
				}],
		defaults : {
			sortable : false,
			menuDisabled : false,
			width : 100
		}
	});

	var store = this.store();
	store.load({
				params : {
					start : 0,
					limit : 25
				}
			});
	var grid = new Ext.grid.GridPanel({
				id : 'CalendarPlanGrid',
				tbar : this.topbar(),
				store : store,
				trackMouseOver : true,
				disableSelection : false,
				loadMask : true,
				region : 'center',
				cm : cm,
				sm : sm,
				viewConfig : {
					forceFit : true,
					enableRowBody : false,
					showPreview : false
				},
				bbar : new HT.PagingBar({store : store})
			});

	grid.addListener('rowdblclick', function(grid, rowindex, e) {
				grid.getSelectionModel().each(function(rec) {
							TodayPlanView.edit(rec.data.planId);
						});
			});
	return grid;

};

/**
 * 初始化数据
 */
TodayPlanView.prototype.store = function() {
	var store = new Ext.data.Store({
		proxy : new Ext.data.HttpProxy({
					url : __ctxPath + '/task/todayCalendarPlan.do'
				}),
		reader : new Ext.data.JsonReader({
					root : 'result',
					totalProperty : 'totalCounts',
					id : 'id',
					fields : [{
								name : 'planId',
								type : 'int'
							}
							,'urgent', 'durationTime','content',
							'status']
				}),
		remoteSort : true
	});
	return store;
};

/**
 * 建立操作的Toolbar
 */
TodayPlanView.prototype.topbar = function() {
	var toolbar = new Ext.Toolbar({
				height : 30,
				bodyStyle : 'text-align:left',
				items : [{
							iconCls : 'btn-add',
							text : '添加日程',
							xtype : 'button',
							handler : function() {
								new CalendarPlanForm();
							}
						}, {
							iconCls : 'btn-del',
							text : '删除日程',
							xtype : 'button',
							handler : function() {

								var grid = Ext.getCmp("CalendarPlanGrid");

								var selectRecords = grid.getSelectionModel()
										.getSelections();

								if (selectRecords.length == 0) {
									Ext.ux.Toast.msg("信息", "请选择要删除的记录！");
									return;
								}
								var ids = Array();
								for (var i = 0; i < selectRecords.length; i++) {
									ids.push(selectRecords[i].data.planId);
								}

								CalendarPlanView.remove(ids);
							}
						}]
			});
	return toolbar;
};

/**
 * 删除单个记录
 */
TodayPlanView.remove = function(id) {
	var grid = Ext.getCmp("TodayPlanView");
	Ext.Msg.confirm('信息确认', '您确认要删除该记录吗？', function(btn) {
				if (btn == 'yes') {
					Ext.Ajax.request({
								url : __ctxPath
										+ '/task/multiDelCalendarPlan.do',
								params : {
									ids : id
								},
								method : 'post',
								success : function() {
									Ext.ux.Toast.msg("信息提示", "成功删除所选记录！");
									grid.getStore().reload({
												params : {
													start : 0,
													limit : 25
												}
											});
								}
							});
				}
			});
};

/**
 * 
 */
TodayPlanView.edit = function(id) {
	new CalendarPlanForm(id);
}
/**
 * 完成任务，填写反馈意见
 * @param {} id
 */
TodayPlanView.finished=function(id){
	new CalendarPlanFinishForm(id);
}
