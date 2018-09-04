Ext.ns('ContractConfigView');
/**
 * [ContractConfig]列表
 */
var ContractConfigView = function() {
	return this.setup();
};
/**
 * 建立视图
 */
ContractConfigView.prototype.setup = function() {
	return this.grid();
};
/**
 * 建立DataGrid
 */
ContractConfigView.prototype.grid = function() {
	var sm = new Ext.grid.CheckboxSelectionModel();
	var cm = new Ext.grid.ColumnModel({
		columns : [sm, new Ext.grid.RowNumberer(), {
					header : 'configId',
					dataIndex : 'configId',
					hidden : true
				}, {
					header : '设备名称',
					width:100,
					dataIndex : 'itemName',
					editor: new Ext.form.TextField({
						allowBlank: false
					})
				}, {
					header : '设置规格',
					width:100,
					dataIndex : 'itemSpec',
					editor: new Ext.form.TextField({
						allowBlank: false
					})
				}, {
					header : '数量',
					width:60,
					dataIndex : 'amount',
					editor: new Ext.form.NumberField({
						allowBlank: false
					})
				}, {
					header : '备注',
					width:120,
					dataIndex : 'notes',
					editor: new Ext.form.TextField({
					})
				}, {
					header : '管理',
					dataIndex : 'configId',
					width : 65,
					sortable : false,
					renderer : function(value, metadata, record, rowIndex,
							colIndex) {
						//var editId = record.data.configId;
						var str = '<button title="删除" value=" " class="btn-del" onclick="ContractConfigView.remove('
								+ rowIndex + ')">&nbsp</button>';
						return str;
					}
				}],
		defaults : {
			sortable : true,
			menuDisabled : false,
			width : 100
		}
	});

	var store = this.store();//这里不load数据
//	store.load({
//				params : {
//					start : 0,
//					limit : 25
//				}
//			});
	var editor = new Ext.ux.grid.RowEditor({
        saveText: 'Update'
    });
	var grid = new Ext.grid.EditorGridPanel({
				id : 'ContractConfigGrid',
				tbar : this.topbar(),
				store : store,
				plugins: [editor],
				trackMouseOver : true,
				disableSelection : false,
				loadMask : true,
				width: 350,
				height : 180,
				clicksToEdit: 1,
				cm : cm,
				sm : sm,
				viewConfig : {
					forceFit : true,
					enableRowBody : false,
					showPreview : false
				}
			});
	return grid;

};

/**
 * 初始化数据
 */
ContractConfigView.prototype.store = function() {
	var store = new Ext.data.Store({
				proxy : new Ext.data.HttpProxy({
							url : __ctxPath + '/customer/listContractConfig.do'
						}),
				reader : new Ext.data.JsonReader({
							root : 'result',
							totalProperty : 'totalCounts',
							id : 'id',
							fields : [{
										name : 'configId',
										type : 'int'
									}

									, 'itemName','itemSpec',
									'amount', 'notes']
						}),
				remoteSort : true
			});
	store.setDefaultSort('configId', 'desc');
	return store;
};

/**
 * 建立操作的Toolbar
 */
ContractConfigView.prototype.topbar = function() {
	
	var toolbar = new Ext.Toolbar({
				id : 'ContractConfigFootBar',
				height : 28,
				bodyStyle : 'text-align:left',
				items : [{
							iconCls : 'btn-add',
							text : '添加配置',
							xtype : 'button',
							handler : function() {
								var grid = Ext.getCmp('ContractConfigGrid');
								var store = grid.getStore();
								var Plant = grid.getStore().recordType;
				                var p = new Plant();
				                grid.stopEditing();
				                store.insert(store.getCount(), p);
				               
				                store.sort();
				                grid.getView().refresh();
				                grid.startEditing(0, 0);
							}
						}, {
							iconCls : 'btn-del',
							text : '删除配置',
							xtype : 'button',
							handler : function() {

								var grid = Ext.getCmp("ContractConfigGrid");
								grid.stopEditing();
								var selectRecords = grid.getSelectionModel().getSelections();
								var store = grid.getStore();
								if (selectRecords.length == 0) {
									Ext.ux.Toast.msg("信息", "请选择要删除的记录！");
									return;
								}
//								var ids = Array();
//								for (var i = 0; i < selectRecords.length; i++) {
//									ids.push(selectRecords[i].data.configId);
//								}
//
//								ContractConfigView.remove(ids);
								var s = grid.getSelectionModel().getSelections();
				                for(var i = 0, r; r = s[i]; i++){
				                    store.remove(r);
				                }
				                store.sort();
				                grid.getView().refresh();
				                grid.startEditing(0, 0);
							}
						}]
			});
	return toolbar;
};

/**
 * 删除单个记录
 */
ContractConfigView.remove = function(id) {
	var grid = Ext.getCmp("ContractConfigGrid");
//	var store = grid.getStore();
//	var Plant = grid.getStore().recordType;
//    var p = new Plant();
//    p = store.getAt(id);
    grid.stopEditing();
//    store.remove(p);
	var s = grid.getSelectionModel().getSelections();
    for(var i = 0, r; r = s[i]; i++){
        grid.getStore().remove(r);
    }
    grid.getView().refresh();
    grid.startEditing(0, 0);
};

