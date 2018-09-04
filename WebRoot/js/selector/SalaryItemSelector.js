/**
 * 薪酬项目选择器
 */
var SalaryItemSelector = {
	/**
	 * @param callback
	 *            回调函数
	 * @param isSingle
	 *            是否单选
	 */
	getView : function(callback, _exclude, isSingle) {
		
		// ---------------------------------start grid
		// panel--------------------------------
		var sm = null;
		if (isSingle) {
			var sm = new Ext.grid.CheckboxSelectionModel({
						singleSelect : true
					});
		} else {
			sm = new Ext.grid.CheckboxSelectionModel();
		}
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
					renderer:function(value){
						return '<img src="' + __ctxPath
								+ '/images/flag/customer/rmb.png"/>' + value;
					}
				}]
		});

		var store = new Ext.data.Store({
				proxy : new Ext.data.HttpProxy({
							url : __ctxPath + '/hrm/listSalaryItem.do?exclude='+_exclude
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

		var gridPanel = new Ext.grid.GridPanel({
					id : 'SalaryItemSelectorGrid',
					width : 400,
					height : 300,
					region : 'center',
					title : '薪酬项目列表',
					store : store,
					shim : true,
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
					// paging bar on the bottom
					bbar : new HT.PagingBar({store : store})
				});

		store.load({
					params : {
						start : 0,
						limit : 25
					}
				});
		// --------------------------------end grid
		// panel-------------------------------------

		var formPanel = new Ext.FormPanel({
			width : 400,
			region : 'north',
			id : 'SalaryItemSelectorForm',
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
						text : '查询条件:'
					},{
						text : '工资项名称'
					}, {
						xtype : 'textfield',
						width : 80,
						name : 'Q_itemName_S_LK'
					}, {
						text : '缺省值'
					}, {
						xtype : 'textfield',
						width : 80,
						name : 'Q_defaultVal_S_LK'
					}, {
						xtype : 'button',
						text : '查询',
						iconCls : 'search',
						handler : function() {
							var searchPanel = Ext.getCmp('SalaryItemSelectorForm');
							var grid = Ext.getCmp('SalaryItemSelectorGrid');
							if (searchPanel.getForm().isValid()) {
								searchPanel.getForm().submit({
									waitMsg : '正在提交查询',
									url : __ctxPath+ '/hrm/listSalaryItem.do',
									success : function(formPanel, action) {
										var result = Ext.util.JSON.decode(action.response.responseText);
										grid.getStore().loadData(result);
									}
								});
							}

						}
					}]
		});

		var window = new Ext.Window({
			title : '薪酬项目选择',
			iconCls:'menu-salary',
			width : 430,
			height : 380,
			layout : 'border',
			border : false,
			items : [formPanel, gridPanel],
			modal : true,
			buttonAlign : 'center',
			buttons : [{
						iconCls : 'btn-ok',
						text : '确定',
						handler : function() {
							var grid = Ext.getCmp('SalaryItemSelectorGrid');
							var rows = grid.getSelectionModel().getSelections();
							var salaryItemId = '';
							var itemName = '';
							var defaultVal = ''; 
							for (var i = 0; i < rows.length; i++) {

								if (i > 0) {
									salaryItemId += ',';
									itemName += ',';
									defaultVal +=','; 
								}

								salaryItemId += rows[i].data.salaryItemId;
								itemName += rows[i].data.itemName;
								defaultVal += rows[i].data.defaultVal;

							}

							if (callback != null) {
								callback.call(this, salaryItemId, itemName,defaultVal);
							}
							window.close();
						}
					}, {
						text : '取消',
						iconCls : 'btn-cancel',
						handler : function() {
							window.close();
						}
					}]
		});
		return window;
	}

};