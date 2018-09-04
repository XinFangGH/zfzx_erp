/** 
 * 债权转让担保机构选择器
 */
var OrOrzSelector = {
	/**
	 * @param callback
	 *            回调函数
	 * @param isSingle
	 *            是否单选
	 */
	getView : function(callback, isSingle) {
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
								header : 'projGtOrzId',
								dataIndex : 'projGtOrzId',
								hidden : true
							}, {
								header : "机构名称",
								dataIndex : 'name',
								width : 60
							},{
								header : "机构简介",
								dataIndex : 'remark',
								hidden:true,
								width : 60
							}]
				});

		var store = new Ext.data.Store({
					proxy : new Ext.data.HttpProxy({
							url : __ctxPath
					+ "/creditFlow/financingAgency/typeManger/listPlKeepGtorz.do"
							}),
					reader : new Ext.data.JsonReader({
								root : 'result',
								totalProperty : 'totalCounts',
								id : 'projGtOrzId',
								fields : [{
											name : 'projGtOrzId',
											type : 'int'
										}, 'name','remark']
							}),
					remoteSort : true
				});

		var gridPanel = new Ext.grid.GridPanel({
					id : 'GtorzSelectorGrid',
					width : 400,
					height : 300,
					region : 'center',
					title : '担保机构列表',
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
						limit : 10
						
					}
				});
		// --------------------------------end grid
		// panel-------------------------------------

		var formPanel = new Ext.FormPanel({
			width : 400,
			region : 'north',
			id : 'GtOrzSearchForm',
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
						text : '担保机构名称'
					}, {
						xtype : 'textfield',
						name : 'Q_carNo_S_LK'
					}, {
						xtype : 'button',
						text : '查询',
						iconCls : 'search',
						handler : function() {
							var searchPanel = Ext.getCmp('GtOrzSearchForm');
							var grid = Ext.getCmp('GtorzSelectorGrid');
							if (searchPanel.getForm().isValid()) {
								searchPanel.getForm().submit({
									waitMsg : '正在提交查询',
							url : __ctxPath
					+ "/creditFlow/financingAgency/typeManger/listPlKeepGtorz.do",
									params : {
										start : 0,
										limit : 10,
										'Q_status_SN_EQ' : status
									},
									method : 'post',
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

		var window = new Ext.Window({
			title : '担保机构选择',
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
							var grid = Ext.getCmp('GtorzSelectorGrid');
							var rows = grid.getSelectionModel().getSelections();
							var gtorzIds = '';
							var gtorzRemark = '';
							var gtorzName = '';
							for (var i = 0; i < rows.length; i++) {

								if (i > 0) {
									gtorzIds += ',';
									gtorzRemark += ',';
									gtorzName += ',';
								}

								gtorzIds += rows[i].data.projGtOrzId;
								gtorzRemark += rows[i].data.remark;
								gtorzName+=rows[i].data.name;

							}

							if (callback != null) {
								callback.call(this, gtorzIds,gtorzName, gtorzRemark);
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
	},
	getView1 : function(callback, isSingle) {
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
								header : 'projGtOrzId',
								dataIndex : 'projGtOrzId',
								hidden : true
							}, {
								header : "机构名称",
								dataIndex : 'enterprisename',
								width : 60
							},{
								header : "机构简介",
								dataIndex : 'summary',
								hidden:true,
								width : 60
							}]
				});

		var store = new Ext.data.Store({
					proxy : new Ext.data.HttpProxy({
						
				url : __ctxPath + "/customer/listInvestEnterprise.do?businessType=Guarantee"+"&isAll="+this.isAll
							}),
					reader : new Ext.data.JsonReader({
								root : 'result',
								totalProperty : 'totalCounts',
								id : 'projGtOrzId',
								fields : [{
											name : 'id',
											type : 'int'
										}, 'enterprisename','summary']
							}),
					remoteSort : true
				});

		var gridPanel = new Ext.grid.GridPanel({
					id : 'GtorzSelectorGrid',
					width : 400,
					height : 300,
					region : 'center',
					title : '担保机构列表',
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
						limit : 10
						
					}
				});
		// --------------------------------end grid
		// panel-------------------------------------

		var formPanel = new Ext.FormPanel({
			width : 400,
			region : 'north',
			id : 'GtOrzSearchForm',
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
						text : '担保机构名称'
					}, {
						xtype : 'textfield',
						name : 'enterprisename'
					}, {
						xtype : 'button',
						text : '查询',
						iconCls : 'search',
						handler : function() {
							var searchPanel = Ext.getCmp('GtOrzSearchForm');
							var grid = Ext.getCmp('GtorzSelectorGrid');
							if (searchPanel.getForm().isValid()) {
								searchPanel.getForm().submit({
									waitMsg : '正在提交查询',
							url : __ctxPath + "/customer/listInvestEnterprise.do?businessType=Guarantee"+"&isAll="+this.isAll,
									params : {
										start : 0,
										limit : 10
									},
									method : 'post',
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

		var window = new Ext.Window({
			title : '担保机构选择',
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
							var grid = Ext.getCmp('GtorzSelectorGrid');
							var rows = grid.getSelectionModel().getSelections();
							var gtorzIds = '';
							var gtorzRemark = '';
							var gtorzName = '';
							for (var i = 0; i < rows.length; i++) {

								if (i > 0) {
									gtorzIds += ',';
									gtorzRemark += ',';
									gtorzName += ',';
								}

								gtorzIds += rows[i].data.idprojGtOrzId;
								gtorzRemark += rows[i].data.summary;
								gtorzName+=rows[i].data.enterprisename;

							}

							if (callback != null) {
								callback.call(this, gtorzIds,gtorzName, gtorzRemark);
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