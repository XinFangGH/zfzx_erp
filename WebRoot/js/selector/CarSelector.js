/**
 * 车辆选择器
 */
var CarSelector = {
	/**
	 * @param callback
	 *            回调函数
	 * @param isSingle
	 *            是否单选
	 * @param status
	 *            状态：1,可用；2,维修中，3,报废
	 */
	getView : function(callback, isSingle, status) {
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
								header : 'carId',
								dataIndex : 'carId',
								hidden : true
							}, {
								header : "车辆车牌号",
								dataIndex : 'carNo',
								width : 60
							}]
				});

		var store = new Ext.data.Store({
					proxy : new Ext.data.HttpProxy({
								url : __ctxPath + '/admin/listCar.do'
							}),
					reader : new Ext.data.JsonReader({
								root : 'result',
								totalProperty : 'totalCounts',
								id : 'carId',
								fields : [{
											name : 'carId',
											type : 'int'
										}, 'carNo']
							}),
					remoteSort : true
				});

		var gridPanel = new Ext.grid.GridPanel({
					id : 'CarSelectorGrid',
					width : 400,
					height : 300,
					region : 'center',
					title : '车辆列表',
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
						limit : 10,
						'Q_status_SN_EQ' : status
					}
				});
		// --------------------------------end grid
		// panel-------------------------------------

		var formPanel = new Ext.FormPanel({
			width : 400,
			region : 'north',
			id : 'CarSearchForm',
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
						text : '车辆车牌号'
					}, {
						xtype : 'textfield',
						name : 'Q_carNo_S_LK'
					}, {
						xtype : 'button',
						text : '查询',
						iconCls : 'search',
						handler : function() {
							var searchPanel = Ext.getCmp('CarSearchForm');
							var grid = Ext.getCmp('CarSelectorGrid');
							if (searchPanel.getForm().isValid()) {
								searchPanel.getForm().submit({
									waitMsg : '正在提交查询',
									url : __ctxPath + '/admin/listCar.do',
									params : {
										start : 0,
										limit : 10,
										'Q_status_SN_EQ' : status
									},
									method : 'post',
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
			title : '车辆选择',
			iconCls : 'menu-car',
			width : 630,
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
							var grid = Ext.getCmp('CarSelectorGrid');
							var rows = grid.getSelectionModel().getSelections();
							var carIds = '';
							var carNos = '';
							for (var i = 0; i < rows.length; i++) {

								if (i > 0) {
									carIds += ',';
									carNos += ',';
								}

								carIds += rows[i].data.carId;
								carNos += rows[i].data.carNo;

							}

							if (callback != null) {
								callback.call(this, carIds, carNos);
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