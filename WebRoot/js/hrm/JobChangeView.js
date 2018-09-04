/**
 * @author:lyy
 * @class JobChangeView
 * @extends Ext.Panel
 * @description [JobChange]管理
 * @company 北京互融时代软件有限公司
 * @createtime:2010-01-16
 */
Ext.ns('JobChangeView');
JobChangeView = Ext.extend(Ext.Panel, {
	// 构造函数
	constructor : function(_cfg) {
		if (_cfg == null) {
			_cfg = {};
		}
		Ext.apply(this, _cfg);
		// 初始化组件
		this.initComponents();
		// 调用父类构造
		JobChangeView.superclass.constructor.call(this, {
					id : 'JobChangeView',
					title : '职位调动管理',
					region : 'center',
					iconCls : 'menu-jobchange',
					layout : 'border',
					items : [this.searchPanel, this.gridPanel]
				});
	},// end of constructor

	// [JobChange]分类ID
	typeId : null,

	// 条件搜索Panel
	searchPanel : null,

	// 数据展示Panel
	gridPanel : null,

	// GridPanel的数据Store
	store : null,

	// 头部工具栏
	topbar : null,

	// 初始化组件
	initComponents : function() {
		// 初始化搜索条件Panel
		this.searchPanel = new Ext.FormPanel({
					region : 'north',
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
					items : [{text:'查询条件：档案编号：'},{
									width : 100,
									name : 'Q_profileNo_S_LK',
									xtype : 'textfield'
								},{text:'姓名：'},{
									width : 100,
									name : 'Q_userName_S_LK',
									xtype : 'textfield'
								},{text:'原职位：'},{
									width : 100,
									name : 'Q_orgJobName_S_LK',
									xtype : 'textfield'
								},{text:'新职位：'},{
									width : 100,
									name : 'Q_newJobName_S_LK',
									xtype : 'textfield'
								},{
									xtype : 'button',
									text : '查询',
									iconCls : 'search',
									handler : this.search.createCallback(this)
								}
							]
				});// end of the searchPanel

		// 加载数据至store
		this.store = new Ext.data.JsonStore({
					url : __ctxPath + "/hrm/listJobChange.do",
					root : 'result',
					totalProperty : 'totalCounts',
					remoteSort : true,
					fields : [{
								name : 'changeId',
								type : 'int'
							}, 'profileId', 'profileNo', 'userName',
							'orgJobId', 'orgJobName', 'newJobId', 'newJobName',
							'orgStandardNo', 'orgStandardName', 'orgDepId',
							'orgDepName', 'orgTotalMoney', 'newStandardNo',
							'newStandardName', 'newDepId', 'newDepName',
							'newTotalMoney', 'changeReason', 'regName',
							'regTime', 'checkName', 'checkTime',
							'checkOpinion', 'status', 'memo']
				});
		this.store.setDefaultSort('changeId', 'desc');
		// 加载数据
		this.store.load({
					params : {
						start : 0,
						limit : 25
					}
				});

		// 初始化ColumnModel
		var sm = new Ext.grid.CheckboxSelectionModel();
		var cm = new Ext.grid.ColumnModel({
			columns : [sm, new Ext.grid.RowNumberer(), {
						header : 'changeId',
						dataIndex : 'changeId',
						hidden : true
					}, {
						header : '档案编号',
						dataIndex : 'profileNo'
					}, {
						header : '姓名',
						dataIndex : 'userName'
					}, {
						header : '原职位名称',
						dataIndex : 'orgJobName'
					}, {
						header : '新职位名称',
						dataIndex : 'newJobName'
					}, {
						header : '原部门名称',
						dataIndex : 'orgDepName'
					}, {
						header : '新部门名称',
						dataIndex : 'newDepName'
					}, {
						header : '登记人',
						dataIndex : 'regName'
					}, {
						header : '登记时间',
						dataIndex : 'regTime'
					}, {
						header : '状态',
						dataIndex : 'status',
						renderer : function(value) {
							if (value == -1) {
								return '<font color="red">草稿</font>';
							} else if (value == 1) {
								return '<img title="通过审核" src="'
										+ __ctxPath
										+ '/images/flag/customer/effective.png"/>';
							} else if (value == 2) {
								return '<img title="没通过审核" src="'
										+ __ctxPath
										+ '/images/flag/customer/invalid.png"/>';
							} else {
								return '<font color="green">提交审核</font>';

							}
						}
					}, {
						header : '管理',
						dataIndex : 'profileId',
						width : 100,
						sortable : false,
						renderer : function(value, metadata, record, rowIndex,
								colIndex) {
							var editId = record.data.changeId;
							var status = record.data.status;
							var str = '';
							if (isGranted('_JobChangeDel')) {
								str += '<button title="删除" value=" " class="btn-del" onclick="JobChangeView.remove('
										+ editId + ')">&nbsp;&nbsp;</button>';
							}
							if (status != 1 && status != 2) {
								if (isGranted('_JobChangeEdit')) {
									str += '<button title="编辑" value=" " class="btn-edit" onclick="JobChangeView.edit('
											+ editId
											+ ')">&nbsp;&nbsp;</button>';
								}
								if (status != -1) {
									if (isGranted('_JobChangeCheck')) {
										str += '<button title="审核" value=" " class="btn-empProfile-check" onclick="JobChangeView.check('
												+ editId
												+ ')">&nbsp;&nbsp;</button>';
									}
								}
							} else {
								if (isGranted('_JobChangeQuery')) {
									str += '<button title="查看" value=" " class="btn-readdocument" onclick="JobChangeView.look('
											+ editId
											+ ')">&nbsp;&nbsp;</button>';
								}
							}
							if (isGranted('_JobChangeQuery')) {
								str += '<button title="查看操作记录" value=" " class="btn-operation" onclick="JobChangeView.operation('
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
		// 初始化工具栏
		this.topbar = new Ext.Toolbar({
					height : 30,
					bodyStyle : 'text-align:left',
					items : []

				});

		if (isGranted('_JobChangeAdd')) {
			this.topbar.add(new Ext.Button({
						iconCls : 'btn-add',
						text : '登记',
						handler : this.createRecord
					}));
		}
		if (isGranted('_JobChangeDel')) {
			this.topbar.add(new Ext.Button({
						iconCls : 'btn-del',
						text : '删除',
						handler : this.delRecords,
						scope : this
					}));
		}
		this.gridPanel = new Ext.grid.GridPanel({
					id : 'JobChangeGrid',
					region : 'center',
					stripeRows : true,
					tbar : this.topbar,
					store : this.store,
					trackMouseOver : true,
					disableSelection : false,
					loadMask : true,
					cm : cm,
					sm : sm,
					plugins : this.rowActions,
					viewConfig : {
						forceFit : true,
						autoFill : true, // 自动填充
						forceFit : true
						// showPreview : false
					},
					bbar : new HT.PagingBar({store : this.store})
				});

		this.gridPanel.addListener('rowdblclick', function(grid, rowindex, e) {
					grid.getSelectionModel().each(function(rec) {
					    var id=rec.data.changeId;
					    var status=rec.data.status;
					    if(status!=1&&status!=2){
						if (isGranted('_JobChangeEdit')) {
							var tabs = Ext.getCmp('centerTabPanel');
							var JobChangeFormPanel = Ext
									.getCmp('JobChangeForm');
							if (JobChangeFormPanel != null) {
								tabs.remove('JobChangeForm');
							}
							JobChangeFormPanel = new JobChangeForm({
										changeId : id
									});
							tabs.add(JobChangeFormPanel);
							tabs.activate(JobChangeFormPanel);
						}
					    }else{
					       JobChangeView.look(id);
					    }
					});
				});
	},// end of the initComponents()

	/**
	 * 
	 * @param {}
	 *            self 当前窗体对象
	 */
	search : function(self) {
		if (self.searchPanel.getForm().isValid()) {// 如果合法
			$search({
				searchPanel :self.searchPanel,
				gridPanel : self.gridPanel
			});
		}
	},

	/**
	 * 添加记录
	 */
	createRecord : function() {
		// new JobChangeForm().show();
		var tabs = Ext.getCmp('centerTabPanel');
		var JobChangeFormPanel = Ext.getCmp('JobChangeForm');
		if (JobChangeFormPanel != null) {
			tabs.remove('JobChangeForm');
		}
		JobChangeFormPanel = new JobChangeForm();
		tabs.add(JobChangeFormPanel);
		tabs.activate(JobChangeFormPanel);
	},
	/**
	 * 删除多条记录
	 */
	delRecords : function() {
		var gridPanel = Ext.getCmp('JobChangeGrid');
		var selectRecords = gridPanel.getSelectionModel().getSelections();
		if (selectRecords.length == 0) {
			Ext.ux.Toast.msg("信息", "请选择要删除的记录！");
			return;
		}
		var ids = Array();
		for (var i = 0; i < selectRecords.length; i++) {
			ids.push(selectRecords[i].data.changeId);
		}
		JobChangeView.remove(ids);
	}
});
/**
 * 编辑
 * 
 * @param {}
 *            id
 */
JobChangeView.edit = function(id) {
	var tabs = Ext.getCmp('centerTabPanel');
	var JobChangeFormPanel = Ext.getCmp('JobChangeForm');
	if (JobChangeFormPanel != null) {
		tabs.remove('JobChangeForm');
	}
	JobChangeFormPanel = new JobChangeForm({
				changeId : id
			});
	tabs.add(JobChangeFormPanel);
	tabs.activate(JobChangeFormPanel);
}
/**
 * 删除
 * 
 * @param {}
 *            id
 */
JobChangeView.remove = function(id) {
	Ext.Msg.confirm('信息确认', '您确认要删除所选记录吗？', function(btn) {
				if (btn == 'yes') {
					Ext.Ajax.request({
								url : __ctxPath + '/hrm/multiDelJobChange.do',
								params : {
									ids : id
								},
								method : 'POST',
								success : function(response, options) {
									Ext.ux.Toast.msg('操作信息', '成功删除记录！');
									Ext.getCmp('JobChangeGrid').getStore()
											.reload();
								},
								failure : function(response, options) {
									Ext.ux.Toast.msg('操作信息', '操作出错，请联系管理员！');
								}
							});
				}
			});// end of comfirm
}
/**
 * 审核
 * 
 * @param {}
 *            id
 */
JobChangeView.check = function(id) {
	new CheckJobChangeWin({
				changeId : id
			}).show();
}
/**
 * 操作记录
 * 
 * @param {}
 *            id
 */
JobChangeView.operation = function(id) {
	var window = new Ext.Window({
				id : 'JobChangeViewOperationWin',
				title : '职位调动操作纪录',
				iconCls : 'btn-operation',
				width : 500,
				x : 300,
				y : 50,
				autoHeight : true,
				border : false,
				modal : true,
				layout : 'anchor',
				plain : true,
				bodyStyle : 'padding:5px;',
				buttonAlign : 'center',
				items : [new Ext.Panel({
							autoScroll : true,
							autoHeight : true,
							border : false,
							autoLoad : {
								url : __ctxPath
										+ '/pages/hrm/jobChangeOperation.jsp?changeId='
										+ id
							}
						})],
				buttons : [{
							text : '关闭',
							iconCls : 'close',
							handler : function() {
								window.close();
							}
						}]
			});
	window.show();
}

/**
 * 查看详细信息
 * 
 * @param {}
 *            id
 */
JobChangeView.look = function(id) {
	var win = new CheckJobChangeWin({
				changeId : id
			}).show();
	win.setTitle('职位调换详细信息');
	Ext.getCmp('CheckJobChangePanel').setHeight(280);
	win.remove('CheckJobChangeWin');
	Ext.getCmp('JobChangeBtnY').hide();
	Ext.getCmp('JobChangeBtnN').hide();
}
