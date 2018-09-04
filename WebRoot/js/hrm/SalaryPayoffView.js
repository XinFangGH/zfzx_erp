/**
 * @author:
 * @class SalaryPayoffView
 * @extends Ext.Panel
 * @description 薪酬发放管理
 * @company 北京互融时代软件有限公司
 * @createtime:2010-01-16
 */
Ext.ns('SalaryPayoffView');

SalaryPayoffView = Ext.extend(Ext.Panel, {
	// 构造函数
	constructor : function(_cfg) {
		if (_cfg == null) {
			_cfg = {};
		}
		Ext.apply(this, _cfg);
		// 初始化组件
		this.initComponents();
		// 调用父类构造
		SalaryPayoffView.superclass.constructor.call(this, {
					id : 'SalaryPayoffView',
					title : '薪酬发放审核',
					region : 'center',
					iconCls : 'menu-check-salay',
					layout : 'border',
					items : [this.searchPanel, this.gridPanel]
				});
	},// end of constructor

	// [SalaryPayoff]分类ID
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
					items : [{
								text : '查询条件：员工姓名:'
							}, {
								name : 'Q_fullname_S_LK',
								width : 100,
								xtype : 'textfield'
							}, {
								text : '审批状态:'
							}, {
								hiddenName : 'Q_checkStatus_SN_EQ',
								width : 110,
								xtype : 'combo',
								editable : false,
								mode : 'local',
								triggerAction : 'all',
								store : [['', '　'], ['0', '未审核'],
										['1', '审核通过'], ['2', '审核未通过']]
							}, {
								xtype : 'button',
								text : '查询',
								iconCls : 'search',
								handler : this.search.createCallback(this)
							}]
				});// end of the searchPanel

		// 加载数据至store
		this.store = new Ext.data.JsonStore({
					url : __ctxPath + "/hrm/listSalaryPayoff.do",
					root : 'result',
					totalProperty : 'totalCounts',
					remoteSort : true,
					fields : [{
								name : 'recordId',
								type : 'int'
							}, 'fullname', 'userId', 'profileNo', 'idNo',
							'standAmount', 'encourageAmount', 'deductAmount',
							'achieveAmount', 'encourageDesc', 'deductDesc',
							'memo', 'acutalAmount', 'regTime', 'register',
							'checkName', 'checkTime', 'checkStatus',
							'startTime', 'endTime', 'standardId']
				});
		this.store.setDefaultSort('recordId', 'desc');
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
						header : 'recordId',
						dataIndex : 'recordId',
						hidden : true
					}, {
						header : '员工姓名',
						dataIndex : 'fullname'
					}, {
						header : '档案编号',
						dataIndex : 'profileNo'
					}, {
						header : '身份证号',
						dataIndex : 'idNo'
					}, {
						header : '薪标金额',
						dataIndex : 'standAmount',
						renderer : function(value) {
							return '<img src="' + __ctxPath
									+ '/images/flag/customer/rmb.png"/>'
									+ value;
						}
					}, {
						header : '实发金额',
						dataIndex : 'acutalAmount',
						renderer : function(value) {
							return '<img src="' + __ctxPath
									+ '/images/flag/customer/rmb.png"/>'
									+ value;
						}
					}, {
						header : '登记时间',
						dataIndex : 'regTime',
						renderer : function(value) {
							return value.substring(0, 10);
						}
					}, {
						header : '审批状态',
						dataIndex : 'checkStatus',
						renderer : function(value) {
							if (value == '0') {
								return '未审核'
							} else if (value == '1') {
								return '<img title="通过审核" src="'
										+ __ctxPath
										+ '/images/flag/customer/effective.png"/>';
							} else {
								return '<img title="没通过审核" src="'
										+ __ctxPath
										+ '/images/flag/customer/invalid.png"/>';
							}
						}
					}, {
						header : '管理',
						dataIndex : 'profileId',
						width : 100,
						sortable : false,
						renderer : function(value, metadata, record, rowIndex,
								colIndex) {
							var editId = record.data.recordId;
							var status = record.data.checkStatus;
							var str = '';
							if (isGranted('_SalaryPayoffDel')) {
								str += '<button title="删除" value=" " class="btn-del" onclick="SalaryPayoffView.remove('
										+ editId + ')">&nbsp;&nbsp;</button>';
							}
							if (status != 1 && status != 2) {
								if (isGranted('_SalaryPayoffEdit')) {
									str += '<button title="编辑" value=" " class="btn-edit" onclick="SalaryPayoffView.edit('
											+ editId
											+ ')">&nbsp;&nbsp;</button>';
								}
								if (isGranted('_SalaryPayoffCheck')) {
									str += '<button title="审核" value=" " class="btn-empProfile-check" onclick="SalaryPayoffView.check('
											+ editId
											+ ')">&nbsp;&nbsp;</button>';
								}
							} else {
								if (isGranted('_SalaryPayoffQuery')) {
									str += '<button title="查看" value=" " class="btn-readdocument" onclick="SalaryPayoffView.look('
											+ editId
											+ ')">&nbsp;&nbsp;</button>';
								}
							}

							if (isGranted('_SalaryPayoffQuery')) {
								str += '<button title="查看操作记录" value=" " class="btn-operation" onclick="SalaryPayoffView.operation('
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
		if (isGranted('_SalaryPayoffAdd')) {
			this.topbar.add(new Ext.Button({
						iconCls : 'btn-add',
						text : '登记',
						handler : this.createRecord
					}));
		}
		if (isGranted('_SalaryPayoffDel')) {
			this.topbar.add(new Ext.Button({
						iconCls : 'btn-del',
						text : '删除',
						handler : this.delRecords,
						scope : this
					}));
		}
		this.gridPanel = new Ext.grid.GridPanel({
					id : 'SalaryPayoffGrid',
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
						var id = rec.data.recordId;
						if (rec.data.checkStatus == 0) {
							if (isGranted('_SalaryPayoffEdit')) {
								var tabs = Ext.getCmp('centerTabPanel');
								var salaryPayoffForm = Ext
										.getCmp('SalaryPayoffForm');
								if (SalaryPayoffForm != null) {
									tabs.remove('SalaryPayoffForm');
								}
								salaryPayoffForm = new SalaryPayoffForm({
											recordId : id
										});
								tabs.add(salaryPayoffForm);
								tabs.activate(salaryPayoffForm);
							}
						} else {
							SalaryPayoffView.look(id);
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
		var tabs = Ext.getCmp('centerTabPanel');
		var salaryPayoffForm = Ext.getCmp('SalaryPayoffForm');
		if (SalaryPayoffForm != null) {
			tabs.remove('SalaryPayoffForm');
		}
		salaryPayoffForm = new SalaryPayoffForm();
		tabs.add(salaryPayoffForm);
		tabs.activate(salaryPayoffForm);
	},
	/**
	 * 删除多条记录
	 */
	delRecords : function() {
		var gridPanel = Ext.getCmp('SalaryPayoffGrid');
		var selectRecords = gridPanel.getSelectionModel().getSelections();
		if (selectRecords.length == 0) {
			Ext.ux.Toast.msg("信息", "请选择要删除的记录！");
			return;
		}
		var ids = Array();
		for (var i = 0; i < selectRecords.length; i++) {
			ids.push(selectRecords[i].data.recordId);
		}
		SalaryPayoffView.remove(ids);
	}
});
/**
 * 编辑
 * 
 * @param {}
 *            id
 */
SalaryPayoffView.edit = function(id) {
	var tabs = Ext.getCmp('centerTabPanel');
	var salaryPayoffForm = Ext.getCmp('SalaryPayoffForm');
	if (SalaryPayoffForm != null) {
		tabs.remove('SalaryPayoffForm');
	}
	salaryPayoffForm = new SalaryPayoffForm({
				recordId : id
			});
	tabs.add(salaryPayoffForm);
	tabs.activate(salaryPayoffForm);
}
/**
 * 删除
 * 
 * @param {}
 *            id
 */
SalaryPayoffView.remove = function(id) {
	Ext.Msg.confirm('信息确认', '您确认要删除所选记录吗？', function(btn) {
				if (btn == 'yes') {
					Ext.Ajax.request({
								url : __ctxPath
										+ '/hrm/multiDelSalaryPayoff.do',
								params : {
									ids : id
								},
								method : 'POST',
								success : function(response, options) {
									Ext.ux.Toast.msg('操作信息', '成功删除记录！');
									Ext.getCmp('SalaryPayoffGrid').getStore()
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
SalaryPayoffView.check = function(id) {
	new CheckSalaryPayoffForm({
				recordId : id
			}).show();
}
/**
 * 查看操作记录
 * 
 * @param {}
 *            id
 */
SalaryPayoffView.operation = function(id) {
	var window = new Ext.Window({
		id : 'SalaryPayoffViewOperationWin',
		title : '薪酬发放操作纪录',
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
								+ '/pages/hrm/salaryPayoffOperation.jsp?recordId='
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
 * 查看发放信息
 * 
 * @param {}
 *            id
 */
SalaryPayoffView.look = function(id) {
	var win = new CheckSalaryPayoffForm({
				recordId : id
			}).show();
	win.setTitle('发放详细信息');
	win.remove('CheckSalaryPayoffForm');
	Ext.getCmp('salaryPayoffbtnY').hide();
	Ext.getCmp('salaryPayoffbtnN').hide();
}