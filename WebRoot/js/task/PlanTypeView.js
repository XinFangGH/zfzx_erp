Ext.ns('PlanTypeView');
/**
 * 计划类型列表
 */
var PlanTypeView = function() {
	return new Ext.Panel({
		id : 'PlanTypeView',
		title : '计划类型列表',
		iconCls:'menu-plantype',
		layout:'border',
		region : 'center',
		autoScroll : true,
		items : [new Ext.FormPanel({
			id : 'PlanTypeSearchForm',
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
						text : '请输入查询条件:'
					}, {
						text : '类型名称'
					}, {
						xtype : 'textfield',
						name : 'Q_typeName_S_LK'
					}, {
						xtype : 'button',
						text : '查询',
						iconCls : 'search',
						handler : function() {
							var searchPanel = Ext.getCmp('PlanTypeSearchForm');
							var gridPanel = Ext.getCmp('PlanTypeGrid');
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
						iconCls:'btn-reset',
						handler : function() {
							var searchPanel = Ext.getCmp('PlanTypeSearchForm');
							searchPanel.getForm().reset();
						}
					}]
		}), this.setup()]
	});
};
/**
 * 建立视图
 */
PlanTypeView.prototype.setup = function() {
	return this.grid();
};
/**
 * 建立DataGrid
 */
PlanTypeView.prototype.grid = function() {
	var sm = new Ext.grid.CheckboxSelectionModel();
	var cm = new Ext.grid.ColumnModel({
		columns : [sm, new Ext.grid.RowNumberer(), {
					header : 'typeId',
					dataIndex : 'typeId',
					hidden : true
				}, {
					header : '类型名称',
					dataIndex : 'typeName'
				}, {
					header : '管理',
					dataIndex : 'typeId',
					width : 50,
					sortable : false,
					renderer : function(value, metadata, record, rowIndex,
							colIndex) {
						var editId = record.data.typeId;
						var str = '';
						if (isGranted('_PlanTypeDel')) {
							str = '<button title="删除" value=" " class="btn-del" onclick="PlanTypeView.remove('
									+ editId + ')"></button>';
						}
						if (isGranted('_PlanTypeEdit')) {
							str += '&nbsp;<button title="编辑" value=" " class="btn-edit" onclick="PlanTypeView.edit('
									+ editId + ')"></button>';
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
				id : 'PlanTypeGrid',
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
							if (isGranted('_PlanTypeEdit')) {
								PlanTypeView.edit(rec.data.typeId);
							}
						});
			});
	return grid;

};

/**
 * 初始化数据
 */
PlanTypeView.prototype.store = function() {
	var store = new Ext.data.Store({
				proxy : new Ext.data.HttpProxy({
							url : __ctxPath + '/task/listPlanType.do'
						}),
				reader : new Ext.data.JsonReader({
							root : 'result',
							totalProperty : 'totalCounts',
							id : 'id',
							fields : [{
										name : 'typeId',
										type : 'int'
									}

									, 'typeName']
						}),
				remoteSort : true
			});
	store.setDefaultSort('typeId', 'desc');
	return store;
};

/**
 * 建立操作的Toolbar
 */
PlanTypeView.prototype.topbar = function() {
	var toolbar = new Ext.Toolbar({
				id : 'PlanTypeFootBar',
				height : 30,
				bodyStyle : 'text-align:left',
				items : []
			});
	if (isGranted('_PlanTypeAdd')) {
		toolbar.add(new Ext.Button({
					iconCls : 'btn-add',
					text : '添加计划类型',
					handler : function() {
						new PlanTypeForm();
					}
				}));
	}

	if (isGranted('_PlanTypeDel')) {
		toolbar.add(new Ext.Button({
					iconCls : 'btn-del',
					text : '删除计划类型',
					handler : function() {

						var grid = Ext.getCmp("PlanTypeGrid");

						var selectRecords = grid.getSelectionModel()
								.getSelections();

						if (selectRecords.length == 0) {
							Ext.ux.Toast.msg("信息", "请选择要删除的记录！");
							return;
						}
						var ids = Array();
						for (var i = 0; i < selectRecords.length; i++) {
							ids.push(selectRecords[i].data.typeId);
						}

						PlanTypeView.remove(ids);
					}
				}));

	}
	return toolbar;
};

/**
 * 删除单个记录
 */
PlanTypeView.remove = function(id) {
	var grid = Ext.getCmp("PlanTypeGrid");
	Ext.Msg.confirm('信息确认', '您确认要删除该记录吗？', function(btn) {
				if (btn == 'yes') {
					Ext.Ajax.request({
								url : __ctxPath + '/task/multiDelPlanType.do',
								params : {
									ids : id
								},
								method : 'post',
								success : function(result,request) {
									var res = Ext.util.JSON.decode(result.responseText);
									if(res.success==false){
									    Ext.ux.Toast.msg("信息提示", res.message);
									}else{
										Ext.ux.Toast.msg("信息提示", "成功删除所选记录！");
										grid.getStore().reload({
													params : {
														start : 0,
														limit : 25
													}
												});
									}
								}
							});
				}
			});
};

/**
 * 
 */
PlanTypeView.edit = function(id) {
	new PlanTypeForm(id);
}
