Ext.ns('HireIssueView');
/**
 * [HireIssue]列表
 */
var HireIssueView = function() {
	return new Ext.Panel({
		id : 'HireIssueView',
		title : '招聘信息列表',
		iconCls : 'menu-hireIssue',
		layout:'border',
		autoScroll : true,
		items : [new Ext.FormPanel({
			id : 'HireIssueSearchForm',
			region:'north',
			height : 40,
			frame : false,
			border : false,
			layout : 'hbox',
			layoutConfig : {
				padding : '5',
				align : 'middle'
			},
			defaults : {
				xtype : 'label',
				margins : {
					top : 0,
					right : 4,
					bottom : 4,
					left : 4
				}
			},
			items : [{
						text : '请输入查询条件:'
					}, {
						text : '招聘职位'
					}, {
						xtype : 'textfield',
						name : 'Q_jobName_S_LK'
					}, {
						text : '登记人'
					}, {
						xtype : 'textfield',
						name : 'Q_regFullname_S_LK'
					}, {
						xtype : 'button',
						text : '查询',
						iconCls : 'search',
						handler : function() {
							var searchPanel = Ext.getCmp('HireIssueSearchForm');
							var gridPanel = Ext.getCmp('HireIssueGrid');
							if (searchPanel.getForm().isValid()) {
								$search({
									searchPanel :searchPanel,
									gridPanel : gridPanel
								});
							}

						}
					}]
		}), this.setup()]
	});
};
/**
 * 建立视图
 */
HireIssueView.prototype.setup = function() {
	return this.grid();
};
/**
 * 建立DataGrid
 */
HireIssueView.prototype.grid = function() {
	var sm = new Ext.grid.CheckboxSelectionModel();
	var cm = new Ext.grid.ColumnModel({
		columns : [sm, new Ext.grid.RowNumberer(), {
					header : 'hireId',
					dataIndex : 'hireId',
					hidden : true
				}, {
					header : '标题',
					dataIndex : 'title'
				}, {
					header : '登记人',
					dataIndex : 'regFullname'
				}, {
					header : '招聘职位',
					dataIndex : 'jobName'
				},{
					header : '登记时间',
					dataIndex : 'regDate'
				},
				{
					header : '开始时间',
					dataIndex : 'startDate',
					renderer : function(value) {
						return value.replace(/00:00:00/, '');
					}
				}, {
					header : '结束时间',
					dataIndex : 'endDate',
					renderer : function(value) {
						return value.replace(/00:00:00/, '');
					}
				},   {
					header : '审核状态',
					dataIndex : 'status',
					renderer : function(value) {
						if (value == 0) {
							return '未审核';
						} else if (value == 1) {
							return '<img title="通过审核" src="' + __ctxPath
									+ '/images/flag/customer/effective.png"/>';
						} else {
							return '<img title="没通过审核" src="' + __ctxPath
									+ '/images/flag/customer/invalid.png"/>';
						}
					}
				}, {
					header : '管理',
					dataIndex : 'hireId',
					width : 100,
					sortable : false,
					renderer : function(value, metadata, record, rowIndex,
							colIndex) {
						var editId = record.data.hireId;
						var status = record.data.status;
						var str = '';
						if (status == 0) {
							if (isGranted('_HireIssueCheck')) {
								str = '<button title="审核" value=" " class="menu-goods-apply" onclick="HireIssueView.check('
										+ editId + ')">&nbsp;&nbsp;</button>';
							}
						} else {
							if (isGranted('_HireIssueQuery')) {
								str += '&nbsp;<button title="查看" value=" " class="btn-readdocument" onclick="HireIssueView.display('
										+ editId + ')">&nbsp;&nbsp;</button>';
							}
						}
						if (status != 1) {
							if (isGranted('_HireIssueEdit')) {
								str += '&nbsp;<button title="编辑" value=" " class="btn-edit" onclick="HireIssueView.edit('
										+ editId + ')">&nbsp;&nbsp;</button>';
							}
						}
						if (isGranted('_HireIssueDel')) {
							str += '<button title="删除" value=" " class="btn-del" onclick="HireIssueView.remove('
									+ editId + ')">&nbsp;&nbsp;</button>';
						}
						return str;
					}
				}],
		defaults : {
			sortable : true,
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
				id : 'HireIssueGrid',
				tbar : this.topbar(),
				store : store,
				trackMouseOver : true,
				disableSelection : false,
				region:'center',
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

	grid.addListener('rowdblclick', function(grid, rowindex, e) {
				grid.getSelectionModel().each(function(rec) {
							var status = rec.data.status;
							if (status != 1) {
								if (isGranted('_HireIssueEdit')) {
									HireIssueView.edit(rec.data.hireId);
								}
							} else {
								if (isGranted('_HireIssueQuery')) {
									HireIssueView.display(rec.data.hireId);
								}
							}
						});
			});
	return grid;

};

/**
 * 初始化数据
 */
HireIssueView.prototype.store = function() {
	var store = new Ext.data.Store({
				proxy : new Ext.data.HttpProxy({
							url : __ctxPath + '/hrm/listHireIssue.do'
						}),
				reader : new Ext.data.JsonReader({
							root : 'result',
							totalProperty : 'totalCounts',
							id : 'id',
							fields : [{
										name : 'hireId',
										type : 'int'
									}

									, 'title', 'startDate', 'endDate',
									'hireCount', 'jobName', 'jobCondition',
									'regFullname', 'regDate', 'modifyFullname',
									'modifyDate', 'checkFullname',
									'checkOpinion', 'checkDate', 'status',
									'memo']
						}),
				remoteSort : true
			});
	store.setDefaultSort('hireId', 'desc');
	return store;
};

/**
 * 建立操作的Toolbar
 */
HireIssueView.prototype.topbar = function() {
	var toolbar = new Ext.Toolbar({
				id : 'HireIssueFootBar',
				height : 30,
				bodyStyle : 'text-align:left',
				items : []
			});
	if (isGranted('_HireIssueAdd')) {
		toolbar.add(new Ext.Button({
					iconCls : 'btn-add',
					text : '添加招聘信息',
					handler : function() {
						new HireIssueForm();
					}
				}));
	}
	if (isGranted('_HireIssueDel')) {
		toolbar.add(new Ext.Button({
					iconCls : 'btn-del',
					text : '删除招聘信息',
					handler : function() {

						var grid = Ext.getCmp("HireIssueGrid");

						var selectRecords = grid.getSelectionModel()
								.getSelections();

						if (selectRecords.length == 0) {
							Ext.ux.Toast.msg("信息", "请选择要删除的记录！");
							return;
						}
						var ids = Array();
						for (var i = 0; i < selectRecords.length; i++) {
							ids.push(selectRecords[i].data.hireId);
						}

						HireIssueView.remove(ids);
					}
				}));

	}
	return toolbar;
};

/**
 * 删除单个记录
 */
HireIssueView.remove = function(id) {
	var grid = Ext.getCmp("HireIssueGrid");
	Ext.Msg.confirm('信息确认', '您确认要删除该记录吗？', function(btn) {
				if (btn == 'yes') {
					Ext.Ajax.request({
								url : __ctxPath + '/hrm/multiDelHireIssue.do',
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
 * 修改
 */
HireIssueView.edit = function(id) {
	new HireIssueForm(id);
}

/**
 * 审核
 */
HireIssueView.check = function(id) {
	new HireIssueCheckWin(id);
}

/**
 * 查看详细信息
 */
HireIssueView.display = function(id) {
	new HireIssueCheckWin(id);
	Ext.getCmp('HireIssueChckeWin').remove('HireIssueCheckFormPanel');
	Ext.getCmp('NotPassHireIssueSb').hide();
	Ext.getCmp('PassHireIssueSb').hide();
}
