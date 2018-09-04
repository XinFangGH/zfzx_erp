Ext.ns('ResumeView');
/**
 * [Resume]列表
 */
var ResumeView = function() {
	return new Ext.Panel({
		id : 'ResumeView',
		title : '简历列表',
		iconCls : 'menu-resume',
		layout:'border',
		autoScroll : true,
		items : [new Ext.FormPanel({
			id : 'ResumeSearchForm',
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
						text : '姓名'
					}, {
						xtype : 'textfield',
						name : 'Q_fullname_S_LK'
					}, {
						text : '应聘职位'
					}, {
						xtype : 'textfield',
						name : 'Q_position_S_LK'
					}, {
						text : '状态'
					}, {
						xtype : 'textfield',
						name : 'Q_status_S_EQ',
						xtype : 'combo',
						editable : false,
						mode : 'local',
						triggerAction : 'all',
						store : ['通过', '未通过', '准备安排面试', '通过面试']
					}, {
						xtype : 'button',
						text : '查询',
						iconCls : 'search',
						handler : function() {
							var searchPanel = Ext.getCmp('ResumeSearchForm');
							var gridPanel = Ext.getCmp('ResumeGrid');
							if (searchPanel.getForm().isValid()) {
								$search({
									searchPanel :searchPanel,
									gridPanel : gridPanel
								});
							}

						}
					}, {
						xtype : 'button',
						text : '重置',
						iconCls : 'btn-reseted',
						handler : function() {
							var searchPanel = Ext.getCmp('ResumeSearchForm');
							searchPanel.getForm().reset();
						}
					}]
		}), this.setup()]
	});
};
/**
 * 建立视图
 */
ResumeView.prototype.setup = function() {
	return this.grid();
};
/**
 * 建立DataGrid
 */
ResumeView.prototype.grid = function() {
	var sm = new Ext.grid.CheckboxSelectionModel();
	var cm = new Ext.grid.ColumnModel({
		columns : [sm, new Ext.grid.RowNumberer(), {
					header : 'resumeId',
					dataIndex : 'resumeId',
					hidden : true
				}, {
					header : '姓名',
					dataIndex : 'fullname'
				}, {
					header : '年龄',
					dataIndex : 'age'
				}, {
					header : '性别',
					dataIndex : 'sex'
				}, {
					header : '应聘职位',
					dataIndex : 'position'
				}, {
					header : '电话',
					dataIndex : 'phone'
				}, {
					header : '手机',
					dataIndex : 'mobile'
				}, {
					header : '邮箱',
					dataIndex : 'email'
				}, {
					header : '专业',
					dataIndex : 'eduMajor'
				}, {
					header : '状态',
					dataIndex : 'status'
				}, {
					header : '登记人',
					dataIndex : 'registor'
				}, {
					header : '登记时间',
					dataIndex : 'regTime'
				}, {
					header : '管理',
					dataIndex : 'resumeId',
					width : 80,
					sortable : false,
					renderer : function(value, metadata, record, rowIndex,
							colIndex) {
						var editId = record.data.resumeId;
						var str='';
						if(isGranted('_ResumeDel')){
						str += '<button title="删除" value=" " class="btn-del" onclick="ResumeView.remove('
								+ editId + ')">&nbsp;&nbsp;</button>';
						}
						if(isGranted('_ResumeEdit')){
						str += '&nbsp;<button title="编辑" value=" " class="btn-edit" onclick="ResumeView.edit('
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
				id : 'ResumeGrid',
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
							if(isGranted('_ResumeEdit')){
							  ResumeView.edit(rec.data.resumeId);
							}
						});
			});
	return grid;

};

/**
 * 初始化数据
 */
ResumeView.prototype.store = function() {
	var store = new Ext.data.Store({
				proxy : new Ext.data.HttpProxy({
							url : __ctxPath + '/hrm/listResume.do'
						}),
				reader : new Ext.data.JsonReader({
							root : 'result',
							totalProperty : 'totalCounts',
							id : 'id',
							fields : [{
										name : 'resumeId',
										type : 'int'
									}

									, 'fullname', 'age', 'birthday', 'address',
									'zip', 'sex', 'position', 'phone',
									'mobile', 'email', 'hobby', 'religion',
									'party', 'nationality', 'race',
									'birthPlace', 'eduCollege', 'eduDegree',
									'eduMajor', 'startWorkDate', 'idNo',
									'photo', 'status', 'memo', 'registor',
									'regTime', 'workCase', 'trainCase',
									'projectCase']
						}),
				remoteSort : true
			});
	store.setDefaultSort('resumeId', 'desc');
	return store;
};

/**
 * 建立操作的Toolbar
 */
ResumeView.prototype.topbar = function() {
	var toolbar = new Ext.Toolbar({
				id : 'ResumeFootBar',
				height : 30,
				bodyStyle : 'text-align:left',
				items : []
			});
	if (isGranted('_ResumeAdd')) {
		toolbar.add(new Ext.Button({
					iconCls : 'btn-add',
					text : '添加简历',
					handler : function() {
						var tabs = Ext.getCmp('centerTabPanel');
						var ResumeFormPanel = Ext.getCmp('ResumeFormPanel');
						if (ResumeFormPanel != null) {
							tabs.remove('ResumeFormPanel');
						}
						ResumeFormPanel = new ResumeForm();
						tabs.add(ResumeFormPanel);
						tabs.activate(ResumeFormPanel);

					}
				}));

	}
	if (isGranted('_ResumeDel')) {
		toolbar.add(new Ext.Button({
					iconCls : 'btn-del',
					text : '删除简历',
					handler : function() {

						var grid = Ext.getCmp("ResumeGrid");

						var selectRecords = grid.getSelectionModel()
								.getSelections();

						if (selectRecords.length == 0) {
							Ext.ux.Toast.msg("信息", "请选择要删除的记录！");
							return;
						}
						var ids = Array();
						for (var i = 0; i < selectRecords.length; i++) {
							ids.push(selectRecords[i].data.resumeId);
						}

						ResumeView.remove(ids);
					}
				}));
	}
	return toolbar;
};

/**
 * 删除单个记录
 */
ResumeView.remove = function(id) {
	var grid = Ext.getCmp("ResumeGrid");
	Ext.Msg.confirm('信息确认', '您确认要删除该记录吗？', function(btn) {
				if (btn == 'yes') {
					Ext.Ajax.request({
								url : __ctxPath + '/hrm/multiDelResume.do',
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
ResumeView.edit = function(id) {
	var tabs = Ext.getCmp('centerTabPanel');
	var ResumeFormPanel = Ext.getCmp('ResumeFormPanel');
	if (ResumeFormPanel != null) {
		tabs.remove('ResumeFormPanel');
	}
	ResumeFormPanel = new ResumeForm(id);
	tabs.add(ResumeFormPanel);
	tabs.activate(ResumeFormPanel);
}
