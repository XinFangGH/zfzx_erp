Ext.ns('ProjectView');
/**
 * @author:
 * @class ProjectView
 * @extends Ext.Panel
 * @description 项目列表
 * @company 北京互融时代软件有限公司
 * @createtime:2010-04-12
 */
ProjectView = Ext.extend(Ext.Panel, {
	// 条件搜索Panel
	searchPanel : null,
	// 数据展示Panel
	gridPanel : null,
	// GridPanel的数据Store
	store : null,
	// 头部工具栏
	topbar : null,
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		// 初始化组件
		this.initUIComponents();
		// 调用父类构造
		ProjectView.superclass.constructor.call(this, {
					id : 'ProjectView',
					title : '项目列表',
					iconCls : 'menu-project',
					region : 'center',
					layout : 'border',
					items : [this.searchPanel, this.gridPanel]
				});
	},// end of constructor

	// 初始化组件
	initUIComponents : function() {
		this.searchPanel = new Ext.FormPanel({
			id : 'ProjectSearchForm',
			height : 35,
			region : 'north',
			frame : false,
			border : false,
			layout : 'hbox',
			layoutConfig : {
				padding : '5',
				align : 'middle'
			},
			defaults : {
				style : 'padding:0px 5px 0px 5px;',
				border : false,
				anchor : '98%,98%',
				labelWidth : 75,
				xtype : 'label'
			},
			items : [{
						text : '查询条件:'
					}, {
						text : '项目名称'
					}, {
						xtype : 'textfield',
						width : 80,
						name : 'Q_projectName_S_LK'
					}, {
						text : '项目编号'
					}, {
						xtype : 'textfield',
						width : 80,
						name : 'Q_projectNo_S_LK'
					}, {
						text : '联系人'
					}, {
						xtype : 'textfield',
						width : 80,
						name : 'Q_fullname_S_LK'
					}, {
						text : '客户'
					}, {
						xtype : 'textfield',
						width : 80,
						name : 'Q_customerId_S_LK'
					}, {
						text : '业务员'
					}, {
						xtype : 'textfield',
						width : 80,
						name : 'Q_userId_S_LK'
					}, {
						xtype : 'button',
						text : '查询',
						iconCls : 'search',
						handler : function() {
							var searchPanel = Ext.getCmp('ProjectSearchForm');
							var gridPanel = Ext.getCmp('ProjectGrid');
							if (searchPanel.getForm().isValid()) {
								$search({
									searchPanel :searchPanel,
									gridPanel : gridPanel
								});
							}

						}
					}]
		});//end of the searchPanel
		this.store = new Ext.data.Store({
				proxy : new Ext.data.HttpProxy({
							url : __ctxPath + '/customer/listProject.do'
						}),
				reader : new Ext.data.JsonReader({
							root : 'result',
							totalProperty : 'totalCounts',
							id : 'id',
							fields : [{
										name : 'projectId',
										type : 'int'
									}

									, 'projectName', 'projectNo', 'isContract',
									'fullname', 'mobile', 'phone',
									'otherContacts', 'customer', 'appUser']
						}),
				remoteSort : true
			});
		this.store.setDefaultSort('projectId', 'desc');
		this.store.load({
					params : {
						start : 0,
						limit : 25
					}
				});
		var sm = new Ext.grid.CheckboxSelectionModel();
	var cm = new Ext.grid.ColumnModel({
		columns : [sm, new Ext.grid.RowNumberer(), {
					header : 'projectId',
					dataIndex : 'projectId',
					hidden : true
				}, {
					header : '合同',
					width : 40,
					dataIndex : 'isContract',
					renderer : function(value) {
						if (value == '1') {
							return '<img title="有合同" src="' + __ctxPath
									+ '/images/menus/customer/contract.png"/>';
						}
					}
				}, {
					header : '项目编号',
					width : 80,
					dataIndex : 'projectNo'
				}, {
					header : '项目名称',
					width : 110,
					dataIndex : 'projectName'
				}, {
					header : '联系人',
					width : 60,
					dataIndex : 'fullname'
				}, {
					header : '手机',
					width : 80,
					dataIndex : 'mobile'
				}, {
					header : '电话',
					width : 80,
					dataIndex : 'phone'
				}, {
					header : '所属客户',
					width : 80,
					dataIndex : 'customer',
					renderer : function(value) {
						return value.customerName;
					}
				}, {
					header : '业务员',
					width : 60,
					dataIndex : 'appUser',
					renderer : function(value) {
						return value.fullname;
					}
				}, {
					header : '管理',
					width : 100,
					dataIndex : 'projectId',
					width : 50,
					sortable : false,
					renderer : function(value, metadata, record, rowIndex,
							colIndex) {
						var editId = record.data.projectId;
						var str = '';
						if (isGranted('_ProjectDel')) {
							str = '<button title="删除" value=" " class="btn-del" onclick="ProjectView.remove('
									+ editId + ')">&nbsp</button>';
						}
						if (isGranted('_ProjectEdit')) {
							str += '&nbsp;<button title="编辑" value=" " class="btn-edit" onclick="ProjectView.edit('
									+ editId + ')">&nbsp</button>';
						}
						return str;
					}
				}],
		defaults : {
			sortable : true,
			menuDisabled : false,
			width : 100
		}
	});//end of the cm
	this.topbar = new Ext.Toolbar({
				id : 'ProjectFootBar',
				height : 30,
				bodyStyle : 'text-align:left',
				items : []
			});
	if (isGranted('_ProjectAdd')) {
		this.topbar.add(new Ext.Button({
					iconCls : 'btn-add',
					text : '添加项目',
					handler : function() {
						new ProjectForm().show();
					}
				}));
	}

	if (isGranted('_ProjectDel')) {
		this.topbar.add(new Ext.Button({
					iconCls : 'btn-del',
					text : '删除项目',
					xtype : 'button',
					handler : function() {

						var grid = Ext.getCmp("ProjectGrid");

						var selectRecords = grid.getSelectionModel()
								.getSelections();

						if (selectRecords.length == 0) {
							Ext.ux.Toast.msg("信息", "请选择要删除的记录！");
							return;
						}
						var ids = Array();
						for (var i = 0; i < selectRecords.length; i++) {
							ids.push(selectRecords[i].data.projectId);
						}

						ProjectView.remove(ids);
					}
				}));
	}//end of the topbar
	this.gridPanel =  new Ext.grid.GridPanel({
				id : 'ProjectGrid',
				region : 'center',
				tbar : this.topbar,
				store : this.store,
				trackMouseOver : true,
				disableSelection : false,
				loadMask : true,
				cm : cm,
				sm : sm,
				viewConfig : {
					forceFit : true,
					enableRowBody : false,
					showPreview : false
				},
				bbar : new HT.PagingBar({store : this.store})
			});//end of the gridPanel

	this.gridPanel.addListener('rowdblclick', function(grid, rowindex, e) {
				grid.getSelectionModel().each(function(rec) {
							if (isGranted('_ProjectEdit')) {
								ProjectView.edit(rec.data.projectId);
							}
						});
			});
	}//end of the initUIComponents
});

/**
 * 删除单个记录
 */
ProjectView.remove = function(id) {
	var grid = Ext.getCmp("ProjectGrid");
	Ext.Msg.confirm('信息确认', '删除项目，则项目下的<font color="red">合同信息</font>及<font color="red">附件</font>也删除，您确认要删除该记录吗？', function(btn) {
				if (btn == 'yes') {
					Ext.Ajax.request({
								url : __ctxPath
										+ '/customer/multiDelProject.do',
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
ProjectView.edit = function(id) {
	new ProjectForm({
		projectId : id
	}).show();
}
