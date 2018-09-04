Ext.ns('StandSalaryView');
/**
 * [StandSalary]列表
 */
var StandSalaryView = function() {
	return new Ext.Panel( {
		id : 'StandSalaryView',
		iconCls : 'menu-goods-apply',
		title : '薪酬标准列表',
		layout : 'border',
		region : 'center',
		autoScroll : true,
		items : [
				new Ext.FormPanel( {
					id : 'StandSalarySearchForm',
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
					items : [
							{
								text : '请输入查询条件:'
							},
							{
								text : '标准编号'
							},
							{
								xtype : 'textfield',
								width : 80,
								name : 'Q_standardNo_S_LK'
							},
							{
								text : '标准名称'
							},
							{
								xtype : 'textfield',
								width : 80,
								name : 'Q_standardName_S_LK'
							},
							{
								text : '状态'
							},
							{
								xtype : 'combo',
								width : 110,
								hiddenName : 'Q_status_SN_EQ',
								editable : false,
								mode : 'local',
								triggerAction : 'all',
								store : [ [ '', '　' ], [ '0', '未审核' ],
										[ '1', '审核通过' ], [ '2', '审核未通过' ] ]
							},
							{
								xtype : 'button',
								text : '查询',
								iconCls : 'search',
								handler : function() {
									var searchPanel = Ext
											.getCmp('StandSalarySearchForm');
									var gridPanel = Ext
											.getCmp('StandSalaryGrid');
									if (searchPanel.getForm().isValid()) {
										$search( {
											searchPanel : searchPanel,
											gridPanel : gridPanel
										});
									}

								}
							} ]
				}), this.setup() ]
	});
};
/**
 * 建立视图
 */
StandSalaryView.prototype.setup = function() {
	return this.grid();
};
/**
 * 建立DataGrid
 */
StandSalaryView.prototype.grid = function() {
	var sm = new Ext.grid.CheckboxSelectionModel();
	var cm = new Ext.grid.ColumnModel(
			{
				columns : [
						sm,
						new Ext.grid.RowNumberer(),
						{
							header : 'standardId',
							dataIndex : 'standardId',
							hidden : true
						},
						{
							header : '标准编号',
							dataIndex : 'standardNo'
						},
						{
							header : '标准名称',
							dataIndex : 'standardName'
						},
						{
							header : '总金额',
							dataIndex : 'totalMoney',
							renderer : function(value) {
								return '<img src="' + __ctxPath
										+ '/images/flag/customer/rmb.png"/>'
										+ value;
							}
						},
						{
							header : '制定日期',
							dataIndex : 'setdownTime',
							renderer : function(value) {
								return value.substring(0, 10);
							}
						},
						{
							header : '状态',
							dataIndex : 'status',
							renderer : function(value) {
								if (value == '0') {
									return '未审核'
								} else if (value == '1') {
									return '<img title="通过审核" src="' + __ctxPath + '/images/flag/customer/effective.png"/>';
								} else {
									return '<img title="没通过审核" src="' + __ctxPath + '/images/flag/customer/invalid.png"/>';
								}
							}
						},
						{
							header : '管理',
							dataIndex : 'standardId',
							width : 100,
							sortable : false,
							renderer : function(value, metadata, record,
									rowIndex, colIndex) {
								var editId = record.data.standardId;
								var str = '';
								if (isGranted('_StandSalaryDel')) {
									str += '<button title="删除" value=" " class="btn-del" onclick="StandSalaryView.remove(' + editId + ')">&nbsp;&nbsp;</button>';
								}
								if (record.data.status != 1) {//已经审核通过的不再审核,编辑
									if (isGranted('_StandSalaryCheck')) {
										str += '&nbsp;<button title="审核" value=" " class="btn-salary-apply" onclick="StandSalaryView.check(' + editId + ')">&nbsp;&nbsp;</button>';
									}
									if (isGranted('_StandSalaryEdit')) {
										str += '&nbsp;<button title="编辑" value=" " class="btn-edit" onclick="StandSalaryView.edit(' + editId + ')">&nbsp;&nbsp;</button>';
									}
								}
								if (isGranted('_StandSalaryQuery')) {
									str += '&nbsp;<button title="操作纪录" value=" " class="btn-operation" onclick="StandSalaryView.operation(' + editId + ')">&nbsp;&nbsp;</button>';
								}
								return str;
							}
						} ],
				defaults : {
					sortable : true,
					menuDisabled : false,
					width : 100
				}
			});

	var store = this.store();
	store.load( {
		params : {
			start : 0,
			limit : 25
		}
	});
	var grid = new Ext.grid.GridPanel( {
		id : 'StandSalaryGrid',
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
		bbar : new HT.PagingBar( {
			store : store
		})
	});

	grid.addListener('rowdblclick', function(grid, rowindex, e) {
		grid.getSelectionModel().each(function(rec) {
			if(rec.data.status != 1){
				if (isGranted('_StandSalaryEdit')) {
					StandSalaryView.edit(rec.data.standardId);
				}
			}
		});
	});
	return grid;

};

/**
 * 初始化数据
 */
StandSalaryView.prototype.store = function() {
	var store = new Ext.data.Store( {
		proxy : new Ext.data.HttpProxy( {
			url : __ctxPath + '/hrm/listStandSalary.do'
		}),
		reader : new Ext.data.JsonReader( {
			root : 'result',
			totalProperty : 'totalCounts',
			id : 'id',
			fields : [ {
				name : 'standardId',
				type : 'int'
			}, 'standardNo', 'standardName', 'totalMoney', 'setdownTime',
					'status' ]
		}),
		remoteSort : true
	});
	store.setDefaultSort('standardId', 'desc');
	return store;
};

/**
 * 建立操作的Toolbar
 */
StandSalaryView.prototype.topbar = function() {
	var toolbar = new Ext.Toolbar( {
		id : 'StandSalaryFootBar',
		height : 30,
		bodyStyle : 'text-align:left',
		items : []
	});
	if (isGranted('_StandSalaryAdd')) {
		toolbar.add( {
			iconCls : 'btn-add',
			text : '添加标准',
			xtype : 'button',
			handler : function() {
				// 只允许有一个编辑窗口
			var tabs = Ext.getCmp('centerTabPanel');
			var newform = Ext.getCmp('StandSalaryForm');
			if (newform == null) {
				newform = new StandSalaryForm();
				tabs.add(newform);
			} else {
				tabs.remove('StandSalaryForm');
				newform = new StandSalaryForm();
				tabs.add(newform);
			}
			tabs.activate(newform);
		}
		});
	};
	if (isGranted('_StandSalaryDel')) {
		toolbar.add( {
			iconCls : 'btn-del',
			text : '删除标准',
			xtype : 'button',
			handler : function() {

				var grid = Ext.getCmp("StandSalaryGrid");

				var selectRecords = grid.getSelectionModel().getSelections();

				if (selectRecords.length == 0) {
					Ext.ux.Toast.msg("信息", "请选择要删除的记录！");
					return;
				}
				var ids = Array();
				for ( var i = 0; i < selectRecords.length; i++) {
					ids.push(selectRecords[i].data.standardId);
				}
				StandSalaryView.remove(ids);
			}
		});
	}
	return toolbar;
};

/**
 * 删除单个记录
 */
StandSalaryView.remove = function(id) {
	var grid = Ext.getCmp("StandSalaryGrid");
	Ext.Msg.confirm('信息确认', '您确认要删除该记录吗？', function(btn) {
		if (btn == 'yes') {
			Ext.Ajax.request( {
				url : __ctxPath + '/hrm/multiDelStandSalary.do',
				params : {
					ids : id
				},
				method : 'post',
				success : function() {
					Ext.ux.Toast.msg("信息提示", "成功删除所选记录！");
					grid.getStore().reload( {
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
StandSalaryView.edit = function(id) {
	// 只允许有一个编辑窗口
	var tabs = Ext.getCmp('centerTabPanel');
	var edit = Ext.getCmp('StandSalaryForm');
	if (edit == null) {
		edit = new StandSalaryForm(id);
		tabs.add(edit);
	} else {
		tabs.remove('StandSalaryForm');
		edit = new StandSalaryForm(id);
		tabs.add(edit);
	}
	tabs.activate(edit);
};
/**
 * 标准审核
 */
StandSalaryView.check = function(id) {
	new CheckStandSalaryForm(id);
};
/**
 * 显示薪酬标准的操作纪录
 * 
 * @param {}
 *            id
 */
StandSalaryView.operation = function(_id) {

	var window = new Ext.Window( {
		id : 'CheckStandSalaryFormWin',
		title : '标准操作纪录',
		iconCls : 'menu-goods-apply',
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
		items : [ new Ext.Panel( {
			autoScroll : true,
			autoHeight : true,
			border : false,
			autoLoad : {
				url : __ctxPath + '/pages/hrm/standOperation.jsp?standardId='
						+ _id
			}
		}) ],
		buttons : [ {
			text : '关闭',
			iconCls : 'close',
			handler : function() {
				window.close();
			}
		} ]
	});
	window.show();
};