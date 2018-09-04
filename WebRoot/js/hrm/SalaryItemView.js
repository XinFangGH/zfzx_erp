Ext.ns('SalaryItemView');
/**
 * @author:
 * @class SalaryItemView
 * @extends Ext.Panel
 * @description 工资项列表
 * @company 北京互融时代软件有限公司
 * @createtime:2010-04-12
 */
SalaryItemView = Ext.extend(Ext.Panel, {
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
		SalaryItemView.superclass.constructor.call(this, {
					id : 'SalaryItemView',
					title : '工资项列表',
					iconCls : 'menu-salary',
					region : 'center',
					layout : 'border',
					items : [this.searchPanel, this.gridPanel]
				});
	},// end of constructor

	// 初始化组件
	initUIComponents : function() {
		this.searchPanel = new Ext.FormPanel({
			id : 'SalaryItemSearchForm',
			height : 40,
			region : 'north',
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
						text : '工资项名称'
					}, {
						xtype : 'textfield',
						name : 'Q_itemName_S_LK'
					}, {
						xtype : 'button',
						text : '查询',
						iconCls : 'search',
						handler : function() {
							var searchPanel = Ext
									.getCmp('SalaryItemSearchForm');
							var gridPanel = Ext.getCmp('SalaryItemGrid');
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
							url : __ctxPath + '/hrm/listSalaryItem.do'
						}),
				reader : new Ext.data.JsonReader({
							root : 'result',
							totalProperty : 'totalCounts',
							id : 'id',
							fields : [{
										name : 'salaryItemId',
										type : 'int'
									}

									, 'itemName', 'defaultVal']
						}),
				remoteSort : true
			});
	this.store.setDefaultSort('salaryItemId', 'desc');
	this.store.load({
					params : {
						start : 0,
						limit : 25
					}
				});
	var sm = new Ext.grid.CheckboxSelectionModel();
	var cm = new Ext.grid.ColumnModel({
		columns : [sm, new Ext.grid.RowNumberer(), {
					header : 'salaryItemId',
					dataIndex : 'salaryItemId',
					hidden : true
				}, {
					header : '薪资项名称',
					dataIndex : 'itemName'
				}, {
					header : '缺省值',
					dataIndex : 'defaultVal',
					renderer : function(value) {
						return '<img src="' + __ctxPath
								+ '/images/flag/customer/rmb.png"/>' + value;
					}
				}, {
					header : '管理',
					dataIndex : 'salaryItemId',
					width : 50,
					sortable : false,
					renderer : function(value, metadata, record, rowIndex,
							colIndex) {
						var editId = record.data.salaryItemId;
						var str = '';
						if (isGranted('_SalaryItemDel')) {
							str += '<button title="删除" value=" " class="btn-del" onclick="SalaryItemView.remove('
									+ editId + ')">&nbsp;&nbsp;</button>';
						}
						if (isGranted('_SalaryItemEdit')) {
							str += '&nbsp;<button title="编辑" value=" " class="btn-edit" onclick="SalaryItemView.edit('
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
	});// end of the cm
	
	this.topbar = new Ext.Toolbar({
				id : 'SalaryItemFootBar',
				height : 30,
				bodyStyle : 'text-align:left',
				items : []
			});
	if (isGranted('_SalaryItemAdd')) {
		this.topbar.add(new Ext.Button({
					iconCls : 'btn-add',
					text : '添加薪酬项目',
					handler : function() {
						new SalaryItemForm().show();
					}
				}));
	}
	if (isGranted('_SalaryItemDel')) {
		this.topbar.add(new Ext.Button({
					iconCls : 'btn-del',
					text : '删除薪酬项目',
					xtype : 'button',
					handler : function() {

						var grid = Ext.getCmp("SalaryItemGrid");

						var selectRecords = grid.getSelectionModel()
								.getSelections();

						if (selectRecords.length == 0) {
							Ext.ux.Toast.msg("信息", "请选择要删除的记录！");
							return;
						}
						var ids = Array();
						for (var i = 0; i < selectRecords.length; i++) {
							ids.push(selectRecords[i].data.salaryItemId);
						}

						SalaryItemView.remove(ids);
					}
				}));
	}//end of the topbar
	
	this.gridPanel =  new Ext.grid.GridPanel({
				id : 'SalaryItemGrid',
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
			});

	this.gridPanel.addListener('rowdblclick', function(grid, rowindex, e) {
				grid.getSelectionModel().each(function(rec) {
							SalaryItemView.edit(rec.data.salaryItemId);
						});
			});
	}//end of the initUIComponents
});

/**
 * 删除单个记录
 */
SalaryItemView.remove = function(id) {
	var grid = Ext.getCmp("SalaryItemGrid");
	Ext.Msg.confirm('信息确认', '您确认要删除该记录吗？', function(btn) {
				if (btn == 'yes') {
					Ext.Ajax.request({
								url : __ctxPath + '/hrm/multiDelSalaryItem.do',
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
SalaryItemView.edit = function(id) {
	new SalaryItemForm({salaryItemId:id}).show();
}
