Ext.ns('StandSalaryItemView');
/**
 * [StandSalaryItem]列表
 */
var StandSalaryItemView = function(_id) {
	return this.setup(_id);
};
/**
 * 建立视图
 */
StandSalaryItemView.prototype.setup = function(_id) {
	return this.grid(_id);
};
/**
 * 建立DataGrid
 */
StandSalaryItemView.prototype.grid = function(_id) {
	var sm = new Ext.grid.CheckboxSelectionModel();
	var cm = new Ext.grid.ColumnModel({
		columns : [sm, new Ext.grid.RowNumberer(), {
					header : 'itemId',
					dataIndex : 'itemId',
					hidden : true
				}, {
					header : '所属标准',
					dataIndex : 'standardId',
					hidden : true
				}, {
					header : '项目名称',
					dataIndex : 'itemName'
				}, {
					header : '金额',
					dataIndex : 'amount',
					editor : new Ext.form.NumberField({
								allowBlank : false
							}),
					renderer : function(value) {
						return '<img src="' + __ctxPath
								+ '/images/flag/customer/rmb.png"/>' + value;
					}
				}, {
					header : '所属工资组成ID',
					dataIndex : 'salaryItemId',
					hidden : true
				}, {
					header : '管理',
					dataIndex : 'itemId',
					width : 50,
					sortable : false,
					renderer : function(value, metadata, record, rowIndex,
							colIndex) {
						var editId = record.data.itemId;
						var str = '<button title="删除" value=" " class="btn-del" onclick="StandSalaryItemView.remove('
								+ editId + ')">&nbsp;&nbsp;</button>';
						return str;
					}
				}],
		defaults : {
			sortable : true,
			menuDisabled : false,
			width : 100
		}
	});

	var store = this.store(_id);

	if (_id != '' && _id != null && _id != 'undefined') {
		store.load({
					params : {
						start : 0,
						limit : 25
					}
				});
	}

	var grid = new Ext.grid.EditorGridPanel({
		id : 'StandSalaryItemGrid',
		iconCls:'menu-salary',
		title : '薪酬项目金额设置',
		tbar : this.topbar(),
		store : store,
		height : 220,
		trackMouseOver : true,
		autoScroll : true,
		disableSelection : false,
		loadMask : true,
		// autoHeight : true,
		cm : cm,
		sm : sm,
			viewConfig : {
				forceFit : true,
				enableRowBody : false,
				showPreview : false
			}
		});

	// grid.addListener('rowupdated', function(grid, rowindex, record) {
	// StandSalaryItemView.onCalcTotalMoney ()
	// });
	return grid;

};

/**
 * 初始化数据
 */
StandSalaryItemView.prototype.store = function(_id) {
	var store = new Ext.data.Store({
				proxy : new Ext.data.HttpProxy({
							url : __ctxPath
									+ '/hrm/listStandSalaryItem.do?standardId='
									+ _id
						}),
				reader : new Ext.data.JsonReader({
							root : 'result',
							totalProperty : 'totalCounts',
							id : 'id',
							fields : [{
										name : 'itemId',
										type : 'int'
									}, 'standardId', 'itemName', 'amount',
									'salaryItemId']
						}),
				remoteSort : true
			});
	store.setDefaultSort('itemId', 'desc');
	return store;
};

/**
 * 建立操作的Toolbar
 */
StandSalaryItemView.prototype.topbar = function() {
	var toolbar = new Ext.Toolbar({
				id : 'StandSalaryItemFootBar',
				height : 30,
				bodyStyle : 'text-align:left',
				items : [{
					iconCls : 'btn-add',
					text : '添加薪酬项目',
					xtype : 'button',
					handler : function() {
						var _store = Ext.getCmp('StandSalaryItemGrid')
								.getStore();
						var _exclude = '';
						// 拼出已选的薪酬项目ID
						for (var i = 0; i < _store.getCount(); i++) {
							_exclude += _store.getAt(i).get('salaryItemId')
									+ ',';
						}
						SalaryItemSelector.getView(
								function(salaryItemId, itemName, defaultVal) {
									var ids = salaryItemId.split(',');
									var names = itemName.split(',');
									var values = defaultVal.split(',');
									var grid = Ext
											.getCmp('StandSalaryItemGrid');
									var store = grid.getStore();
									var Plant = grid.getStore().recordType;
									grid.stopEditing();

									for (var i = 0; i < ids.length; i++) {
										var p = new Plant();
										p.set('salaryItemId', ids[i]);
										p.set('itemName', names[i]);
										p.set('amount', values[i]);
										p.commit();
										store.insert(store.getCount(), p);
									}

									grid.getView().refresh();
									grid.startEditing(0, 0);
									StandSalaryItemView.onCalcTotalMoney();
								}, _exclude).show();
					}
				}]
			});
	return toolbar;
};

/**
 * 删除单个记录
 */
StandSalaryItemView.remove = function(id) {
	var grid = Ext.getCmp("StandSalaryItemGrid");
	grid.stopEditing();
	var s = grid.getSelectionModel().getSelections();
	for (var i = 0, r; r = s[i]; i++) {
		grid.getStore().remove(r);
	}
	var deleteItemIds = Ext.getCmp('deleteItemIds');
	deleteItemIds.setValue(deleteItemIds.getValue()+','+id)
	grid.getView().refresh();
	StandSalaryItemView.onCalcTotalMoney();
	grid.startEditing(0, 0);
};

/**
 * 
 */
StandSalaryItemView.edit = function(id) {
	new StandSalaryItemForm(id);
}

StandSalaryItemView.onCalcTotalMoney = function() {
	// 获取表格的数据存储器
	var _store = Ext.getCmp("StandSalaryItemGrid").getStore();
	// 提交修改的数值
	// _store.commit();
	// 总金额
	var _totalMoney = 0.00;
	// 计算总金额
	for (var i = 0; i < _store.getCount(); i++) {
		_totalMoney += Number(_store.getAt(i).get("amount"));
	}
	// 将总金额设置到form中
	Ext.getCmp('standSalaryForm.totalMoney').setValue(_totalMoney);
}