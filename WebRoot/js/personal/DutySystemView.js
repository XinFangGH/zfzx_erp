Ext.ns('DutySystemView');
/**
 * 班制定义列表
 */
var DutySystemView = function() {
	return new Ext.Panel({
		id : 'DutySystemView',
		title : '班制定义列表',
		iconCls:'menu-dutySystem',
		layout:'border',
		region : 'center',
		autoScroll : true,
		items : [new Ext.FormPanel({
			id : 'DutySystemSearchForm',
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
						text : '班制名称'
					}, {
						xtype : 'textfield',
						name : 'Q_systemName_S_LK'
					}, {
						text : '班次'
					}, {
						xtype : 'textfield',
						name : 'Q_systemSetting_S_LK'
					}, {
						text : '班次描述'
					}, {
						xtype : 'textfield',
						name : 'Q_systemDesc_S_LK'
					}, {
						xtype : 'button',
						text : '查询',
						iconCls : 'search',
						handler : function() {
							var searchPanel = Ext
									.getCmp('DutySystemSearchForm');
							var gridPanel = Ext.getCmp('DutySystemGrid');
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
DutySystemView.prototype.setup = function() {
	return this.grid();
};
/**
 * 建立DataGrid
 */
DutySystemView.prototype.grid = function() {
	var sm = new Ext.grid.CheckboxSelectionModel();
	var cm = new Ext.grid.ColumnModel({
		columns : [sm, new Ext.grid.RowNumberer(), {
					header : 'systemId',
					dataIndex : 'systemId',
					hidden : true
				}, {
					header : '班制名称',
					width:100,
					dataIndex : 'systemName'
				}, {
					header : '班次描述',
					dataIndex : 'systemDesc',
					width:500
				},{
					header:'缺省',
					dataIndex:'isDefault',
					renderer:function(val){
						if(val==1){
							return '是';
						}else{
							return '否';
						}
					}
				}, {
					header : '管理',
					dataIndex : 'systemId',
					width : 100,
					sortable : false,
					renderer : function(value, metadata, record, rowIndex,colIndex) {
						var editId = record.data.systemId;
						var str='';
						if(isGranted('_DutySystemDel')){
							str= '<button title="删除" value=" " class="btn-del" onclick="DutySystemView.remove('+ editId + ')">&nbsp;&nbsp;</button>';
						}
						if(isGranted('_DutySystemEdit')){
							str += '&nbsp;<button title="编辑" value=" " class="btn-edit" onclick="DutySystemView.edit('+ editId + ')">&nbsp;&nbsp;</button>';
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
				id : 'DutySystemGrid',
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
					       if(isGranted('_DutySystemEdit')){
							DutySystemView.edit(rec.data.systemId);
					       }
						});
			});
	return grid;

};

/**
 * 初始化数据
 */
DutySystemView.prototype.store = function() {
	var store = new Ext.data.Store({
				proxy : new Ext.data.HttpProxy({
							url : __ctxPath + '/personal/listDutySystem.do'
						}),
				reader : new Ext.data.JsonReader({
							root : 'result',
							totalProperty : 'totalCounts',
							id : 'id',
							fields : [{
										name : 'systemId',
										type : 'int'
									}

									, 'systemName', 'systemSetting',
									'systemDesc','isDefault']
						}),
				remoteSort : true
			});
	store.setDefaultSort('systemId', 'desc');
	return store;
};

/**
 * 建立操作的Toolbar
 */
DutySystemView.prototype.topbar = function() {
	var toolbar = new Ext.Toolbar({
				id : 'DutySystemFootBar',
				height : 30,
				bodyStyle : 'text-align:left',
				items : []
			});
	if(isGranted('_DutySystemAdd')){
	   toolbar.add(new Ext.Button({
	        iconCls : 'btn-add',
			text : '添加班制定义',
			handler : function() {
				new DutySystemForm();
			}
	   }));
	}
	if(isGranted('_DutySystemDel')){
	   toolbar.add(new Ext.Button({
	        iconCls : 'btn-del',
			text : '删除班制定义',
			handler : function() {
				var grid = Ext.getCmp("DutySystemGrid");
				var selectRecords = grid.getSelectionModel()
						.getSelections();
				if (selectRecords.length == 0) {
					Ext.ux.Toast.msg("信息", "请选择要删除的记录！");
					return;
				}
				var ids = Array();
				for (var i = 0; i < selectRecords.length; i++) {
					ids.push(selectRecords[i].data.systemId);
				}
				DutySystemView.remove(ids);
			}
	   }));
	}
	return toolbar;
};

/**
 * 删除单个记录
 */
DutySystemView.remove = function(id) {
	var grid = Ext.getCmp("DutySystemGrid");
	Ext.Msg.confirm('信息确认', '您确认要删除该记录吗？', function(btn) {
				if (btn == 'yes') {
					Ext.Ajax.request({
								url : __ctxPath
										+ '/personal/multiDelDutySystem.do',
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
DutySystemView.edit = function(id) {
	new DutySystemForm(id);
}
