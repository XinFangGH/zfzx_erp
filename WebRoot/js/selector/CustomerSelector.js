/**
 * 客户选择器
 */
var CustomerSelector = {
	/**
	 * @param callback
	 *            回调函数
	 * @param isSingle
	 *            是否单选
	 */
	getView : function(callback, isSingle) {
		var treeCustomer = new Ext.tree.TreePanel({
					title : '客户地区',
					region : 'west',
					width : 180,
					height : 300,
					split : true,
					collapsible : true,
					autoScroll : true,
					bbar : new Ext.Toolbar({
								items : [{
											xtype : 'button',
											text : '展开',
											iconCls : 'btn-expand',
											handler : function() {
												treeCustomer.expandAll();
											}
										}, {
											xtype : 'button',
											text : '收起',
											iconCls : 'btn-collapse1',
											handler : function() {
												treeCustomer.collapseAll();
											}
										}]
							}),
					loader : new Ext.tree.TreeLoader({
								url : __ctxPath + '/system/treeRegion.do'
							}),
					root : new Ext.tree.AsyncTreeNode({
								expanded : true
							}),
					rootVisible : false,
					listeners : {
						'click' : function(node) {
							if (node != null) {
								var Customers = Ext
										.getCmp('CustomerSelectorGrid');
								var store = Customers.getStore();
								store.proxy.conn.url = __ctxPath
										+ '/customer/listCustomer.do';
								if (node.leaf && node.id > 4) {
									store.baseParams = {
										'Q_city_S_EQ' : node.text
									};
								} else {
									if (node.id != 0) {
										store.baseParams = {
											'Q_state_S_EQ' : node.text
										};
									} else {
										store.baseParams = {
											'Q_state_S_EQ' : null,
											'Q_city_S_EQ' : null
										}
									}
								}

								store.load({
											params : {
												start : 0,
												limit : 12
											}
										});
							}
						}
					}
				});
		// ---------------------------------start grid
		// -------------------------------- panel start
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
								header : 'customerId',
								dataIndex : 'customerId',
								hidden : true
							}, {
								header : "客户号",
								dataIndex : 'customerNo',
								width : 60
							}, {
								header : '客户名称',
								dataIndex : 'customerName',
								width : 60
							}]
				});

		var store = new Ext.data.Store({
			proxy : new Ext.data.HttpProxy({
						url : __ctxPath + '/customer/listCustomer.do'
					}),
			reader : new Ext.data.JsonReader({
						root : 'result',
						totalProperty : 'totalCounts',
						id : 'customerId',
						fields : [{
									name : 'customerId',
									type : 'int'
								}, 'customerNo', 'customerName']
					})
				// remoteSort : true
			});

		var gridPanel = new Ext.grid.GridPanel({
					id : 'CustomerSelectorGrid',
					width : 400,
					height : 300,
					region : 'center',
					title : '客户列表',
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
		// --------------------------------end panel

		// --------------------------------form panel start

		var formPanel = new Ext.FormPanel({
			width : 400,
			region : 'north',
			id : 'CustomerForm',
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
						text : '客户号'
					}, {
						xtype : 'textfield',
						name : 'Q_customerNo_S_LK'
					}, {
						text : '客户名称'
					}, {
						xtype : 'textfield',
						name : 'Q_customerName_S_LK'
					}, {
						xtype : 'button',
						text : '查询',
						iconCls : 'search',
						handler : function() {
							var searchPanel = Ext.getCmp('CustomerForm');
							var grid = Ext.getCmp('CustomerSelectorGrid');
							if (searchPanel.getForm().isValid()) {
								searchPanel.getForm().submit({
									waitMsg : '正在提交查询',
									url : __ctxPath
											+ '/customer/listCustomer.do',
									success : function(formPanel, action) {
										var result = Ext.util.JSON
												.decode(action.response.responseText);
										grid.getStore().loadData(result);
									}
								});
							}

						}
					}]
		});
		// --------------------------------form panel end

		// --------------------------------window start
		var window = new Ext.Window({
			title : '客户选择器',
			iconCls : 'menu-customerView',
			width : 630,
			height : 380,
			layout : 'border',
			border : false,
			items : [treeCustomer, formPanel, gridPanel],
			modal : true,
			buttonAlign : 'center',
			buttons : [{
						iconCls : 'btn-ok',
						text : '确定',
						handler : function() {
							var grid = Ext.getCmp('CustomerSelectorGrid');
							var rows = grid.getSelectionModel().getSelections();
							var CustomerIds = '';
							var CustomerNames = '';
							for (var i = 0; i < rows.length; i++) {
								if (i > 0) {
									CustomerIds += ',';
									CustomerNames += ',';
								}
								CustomerIds += rows[i].data.customerId;
								CustomerNames += rows[i].data.customerName;
							}
							if (callback != null) {
								callback.call(this, CustomerIds, CustomerNames);
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

		// --------------------------------window end
		return window;
	}

};