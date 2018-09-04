Ext.ns('CheckStandSalaryItemView');
/**
 * [StandSalaryItem]列表
 */
var CheckStandSalaryItemView = function(_id) {
	return this.setup(_id);
};
/**
 * 建立视图
 */
CheckStandSalaryItemView.prototype.setup = function(_id) {
	return this.grid(_id);
};
/**
 * 建立DataGrid
 */
CheckStandSalaryItemView.prototype.grid = function(_id) {
	var cm = new Ext.grid.ColumnModel({
		columns : [ new Ext.grid.RowNumberer(), {
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
					renderer : function(value) {
						return '<img src="' + __ctxPath
								+ '/images/flag/customer/rmb.png"/>' + value;
					}
				}, {
					header : '所属工资组成ID',
					dataIndex : 'salaryItemId',
					hidden : true
				}],
		defaults : {
			sortable : true,
			menuDisabled : false,
			width : 100
		}
	});

	var store = this.store(_id);

	store.load({
				params : {
					start : 0,
					limit : 25
				}
			});

	var grid = new Ext.grid.GridPanel({
		id : 'CheckStandSalaryItemGrid',
		store : store,
		height : 200,
		trackMouseOver : true,
		autoScroll : true,
		disableSelection : false,
		loadMask : true,
		// autoHeight : true,
		cm : cm,
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
CheckStandSalaryItemView.prototype.store = function(_id) {
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

