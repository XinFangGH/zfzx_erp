/**
 * @author:
 * @class EmpProfileView
 * @extends Ext.Panel
 * @description 档案管理
 * @company 北京互融时代软件有限公司
 * @createtime:2010-01-16
 */
Ext.ns('EmpProfileView');
EmpProfileView = Ext.extend(Ext.Panel, {
	// 构造函数
	constructor : function(_cfg) {
		if (_cfg == null) {
			_cfg = {};
		}
		Ext.apply(this, _cfg);
		// 初始化组件
		this.initComponents();
		// 调用父类构造
		EmpProfileView.superclass.constructor.call(this, {
					id : 'EmpProfileView',
					title : '档案管理',
					iconCls : 'menu-profile',
					region : 'center',
					layout : 'border',
					items : [this.searchPanel, this.gridPanel]
				});
	},// end of constructor

	// 档案分类ID
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
								text : '查询条件：档案编号：'
							}, {
								name : 'Q_profileNo_S_LK',
								width : 80,
								xtype : 'textfield'
							}, {
								text : '员工姓名：'
							}, {
								name : 'Q_fullname_S_LK',
								width : 80,
								xtype : 'textfield'
							}, {
								text : '身份证号：'
							}, {
								width : 80,
								name : 'Q_idCard_S_LK',
								xtype : 'textfield'
							}, {
								text : '审核状态：'
							}, {
								hiddenName : 'Q_approvalStatus_SN_EQ',
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
							}, {
								name : 'Q_delFlag_SN_EQ',
								width : 80,
								xtype : 'hidden',
								value : 0
							}]
				});// end of the searchPanel

		// 加载数据至store
		this.store = new Ext.data.JsonStore({
					url : __ctxPath + "/hrm/listEmpProfile.do",
					baseParams : {
						"Q_delFlag_SN_EQ" : 0
					},// 只查询未被删除的档案
					root : 'result',
					totalProperty : 'totalCounts',
					remoteSort : true,
					fields : [{
								name : 'profileId',
								type : 'int'
							}, 'profileNo', 'fullname', 'designation',
							'creator', 'createtime', 'approvalStatus', 'memo',
							'depName']
				});
		this.store.setDefaultSort('profileId', 'desc');
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
						header : 'profileId',
						dataIndex : 'profileId',
						hidden : true
					}, {
						header : '档案编号',
						dataIndex : 'profileNo'
					}, {
						header : '员工姓名',
						dataIndex : 'fullname'
					}, {
						header : '建档人',
						dataIndex : 'creator'
					}, {
						header : '建档时间',
						dataIndex : 'createtime',
						renderer : function(value) {
							return value.substring(0, 10);
						}
					}, {
						header : '部门或公司',
						dataIndex : 'depName'
					}, {
						header : '职称',
						dataIndex : 'designation'
					}, {
						header : '审核状态',// 0=未删除 1=删除',
						dataIndex : 'approvalStatus',
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
							var editId = record.data.profileId;
							var status = record.data.approvalStatus;
							var str = '';
							if (isGranted('_EmpProfileDel')) {
								str += '<button title="删除" value=" " class="btn-del" onclick="EmpProfileView.remove('
										+ editId + ')">&nbsp;&nbsp;</button>';
							}
							if (isGranted('_EmpProfileEdit') && status != 1) {
								str += '<button title="编辑" value=" " class="btn-edit" onclick="EmpProfileView.edit('
										+ editId + ')">&nbsp;&nbsp;</button>';
							}
							if (status != 1 && status != 2) {
								if (isGranted('_EmpProfileCheck')) {
									str += '<button title="审核" value=" " class="btn-empProfile-check" onclick="EmpProfileView.check('
											+ editId
											+ ')">&nbsp;&nbsp;</button>';

								}
							} else {
								if (isGranted('_EmpProfileQuery')) {
									str += '<button title="查看" value=" " class="btn-readdocument" onclick="EmpProfileView.look('
											+ editId
											+ ')">&nbsp;&nbsp;</button>';
								}
							}
							if (isGranted('_EmpProfileQuery')) {
								str += '<button title="查看操作记录" value=" " class="btn-operation" onclick="EmpProfileView.operation('
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

		if (isGranted('_EmpProfileAdd')) {
			this.topbar.add(new Ext.Button({
						iconCls : 'btn-add',
						text : '添加档案',
						handler : this.createRecord
					}));
		}
		if (isGranted('_EmpProfileDel')) {
			this.topbar.add(new Ext.Button({
						iconCls : 'btn-del',
						text : '删除档案',
						handler : this.delRecords,
						scope : this
					}));
		}

		if (isGranted('_EmpProfileRec')) {
			this.topbar.add(new Ext.Button({
						iconCls : 'btn-empProfile-recovery',
						text : '恢复档案',
						handler : this.recovery
					}));
		}

		this.gridPanel = new Ext.grid.GridPanel({
					id : 'EmpProfileGrid',
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
								if (isGranted('_EmpProfileEdit')) {
									var id = rec.data.profileId;
									if (rec.data.approvalStatus == 0) {
										EmpProfileView.edit(id);
									} else {
										EmpProfileView.look(id);
									}
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
		// new EmpProfileForm().show();
		var tabs = Ext.getCmp('centerTabPanel');
		var empProfileForm = Ext.getCmp('EmpProfileForm');
		if (empProfileForm != null) {
			tabs.remove('EmpProfileForm');
		}
		empProfileForm = new EmpProfileForm();
		tabs.add(empProfileForm);
		tabs.activate(empProfileForm);
	},
	/**
	 * 删除记录
	 * 
	 * @param {}
	 *            record
	 */
	delRecords : function(record) {
		var gridPanel = Ext.getCmp('EmpProfileGrid');
		var selectRecords = gridPanel.getSelectionModel().getSelections();
		if (selectRecords.length == 0) {
			Ext.ux.Toast.msg("信息", "请选择要删除的记录！");
			return;
		}
		var ids = Array();
		for (var i = 0; i < selectRecords.length; i++) {
			ids.push(selectRecords[i].data.profileId);
		}
		EmpProfileView.remove(ids);
	},
	/**
	 * 恢复档案
	 */
	recovery : function(record) {
		new RecoveryProfileWin().show();
	}

});

/**
 * 删除单个记录
 */
EmpProfileView.remove = function(id) {
	var grid = Ext.getCmp("EmpProfileGrid");
	Ext.Msg.confirm('信息确认', '您确认要删除该记录吗？', function(btn) {
				if (btn == 'yes') {
					Ext.Ajax.request({
								url : __ctxPath + '/hrm/multiDelEmpProfile.do',
								params : {
									ids : id
								},
								method : 'POST',
								success : function(response, options) {
									Ext.ux.Toast.msg('操作信息', '成功删除该记录！');
									grid.getStore().reload();
								},
								failure : function(response, options) {
									Ext.ux.Toast.msg('操作信息', '操作出错，请联系管理员！');
								}
							});
				}
			});// end of comfirm
};

/**
 * 编辑记录
 * 
 * @param {}
 *            record
 */
EmpProfileView.edit = function(id) {

	// 只允许有一个编辑窗口
	var tabs = Ext.getCmp('centerTabPanel');
	var edit = Ext.getCmp('EmpProfileForm');
	if (edit == null) {
		edit = new EmpProfileForm({
					profileId : id
				});
		tabs.add(edit);
	} else {
		tabs.remove('EmpProfileForm');
		edit = new EmpProfileForm({
					profileId : id
				});
		tabs.add(edit);
	}
	tabs.activate(edit);

}

/**
 * 审核档案
 * 
 * @param {}
 *            record
 */
EmpProfileView.check = function(id) {
	new CheckEmpProfileForm({
				profileId : id
			}).show();

}
/**
 * 查看档案
 * 
 * @param {}
 *            record
 */
EmpProfileView.look = function(id) {
	var win = new CheckEmpProfileForm({
				profileId : id
			}).show();
	win.setTitle('档案详细信息');
	win.remove('CheckEmpProfileForm');
	Ext.getCmp('CheckEmpProfileButY').hide();
	Ext.getCmp('CheckEmpProfileButN').hide();
}

/**
 * 展示操作纪录
 * 
 * @param {}
 *            record
 */
EmpProfileView.operation = function(id) {
	var window = new Ext.Window({
		id : 'EmpProfileViewOperationWin',
		title : '标准操作纪录',
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
								+ '/pages/hrm/empProfileOperation.jsp?profileId='
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