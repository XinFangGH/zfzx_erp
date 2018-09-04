/**
 * 项目选择器
 */
var ProviderSelector = {
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
//		var cm = new Ext.grid.ColumnModel({
//			columns : [sm, new Ext.grid.RowNumberer(), {
//						header : 'providerId',
//						dataIndex : 'providerId',
//						hidden : true
//					},{
//						header : '项目编号',
//						dataIndex : 'providerNo',
//						width : 60
//					}, {
//						header : "项目名称",
//						dataIndex : 'providerName',
//						width : 60
//					}, {
//						header : '所属客户',
//						dataIndex : 'customer',
//						width : 60,
//						renderer:function(value){
//							return value.customerName;
//						}
//					},{
//						header : '联系人',
//						dataIndex : 'fullname',
//						width : 60
//					},{
//						header : '项目描述',
//						dataIndex : 'reqDesc',
//						hidden:true
//					}]
//		});

		var cm = new Ext.grid.ColumnModel({
		columns : [sm, new Ext.grid.RowNumberer(), {
					header : 'providerId',
					dataIndex : 'providerId',
					hidden : true
				}, {
					header : '等级',
					dataIndex : 'rank',
					width:40,
					renderer:function(value){
						if (value == '1') {
							return '<img title="一级供应商" src="' + __ctxPath
									+ '/images/flag/customer/grade_one.png"/>';
						} else if(value == '2') {
							return '<img title="二级供应商" src="' + __ctxPath
									+ '/images/flag/customer/grade_two.png"/>';
						} else if(value == '3') {
							return '<img title="三级供应商" src="' + __ctxPath
									+ '/images/flag/customer/grade_three.png"/>';
						} else {
							return '<img title="四级供应商" src="' + __ctxPath
									+ '/images/flag/customer/grade_four.png"/>';
						}
					}
				}, {
					header : '供应商名字',
					dataIndex : 'providerName',
					width:120
				}, {
					header : '联系人',
					dataIndex : 'contactor',
					width:80
				}, {
					header : '电话',
					dataIndex : 'phone',
					width:80
				},{
					header : '地址',
					dataIndex : 'address',
					width:150
				}, {
					header : '管理',
					dataIndex : 'providerId',
					width : 100,
					sortable : false,
					renderer : function(value, metadata, record, rowIndex,
							colIndex) {
						var editId = record.data.providerId;
						var str = '<button title="删除" value=" " class="btn-del" onclick="ProviderView.remove('
								+ editId + ')">&nbsp;&nbsp;</button>';
						str += '&nbsp;<button title="编辑" value=" " class="btn-edit" onclick="ProviderView.edit('
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
		
		
		var store = new Ext.data.Store({
				proxy : new Ext.data.HttpProxy({
							url : __ctxPath + '/customer/listProvider.do'
						}),
				reader : new Ext.data.JsonReader({
							root : 'result',
							totalProperty : 'totalCounts',
							id : 'id',
							fields : [{
										name : 'providerId',
										type : 'int'
									}

									 ,'rank', 'providerName', 'contactor', 'phone','address'
									/*'fax', 'site', 'email',  'zip',
									'openBank', 'account', 'notes',*/]
						}),
				remoteSort : true
			});

		var gridPanel = new Ext.grid.GridPanel({
					id : 'ProviderSelectorGrid',
					width : 550,
					height : 300,
					region : 'center',
					title : '供应商列表',
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
			width : 550,
			region : 'north',
			id : 'ProviderForm',
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
					}, {
						text : '名称'
					}, {
						xtype : 'textfield',
						name : 'Q_providerName_S_LK',
						width: 80
					}, {
						text : '联系人'
					}, {
						xtype : 'textfield',
						name : 'Q_contactor_S_LK',
						width: 80
					}, {
						text : '等级'
					}, {
						hiddenName : 'Q_rank_N_EQ',
						width: 100,
						xtype : 'combo',
						mode : 'local',
						editable : false,
						triggerAction : 'all',
						store : [['null','　'],['1', '一级供应商'], ['2', '二级供应商'],['3', '三级供应商'],['4', '四级供应商']]
					},{
						xtype : 'button',
						text : '查询',
						iconCls : 'search',
						handler : function() {
							var searchPanel = Ext.getCmp('ProviderForm');
							var grid = Ext.getCmp('ProviderSelectorGrid');
							if (searchPanel.getForm().isValid()) {
								searchPanel.getForm().submit({
									waitMsg : '正在提交查询',
									url : __ctxPath+ '/customer/listProvider.do',
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
			title : '供应商选择器',
			iconCls:'menu-provider',
			width : 580,
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
							var grid = Ext.getCmp('ProviderSelectorGrid');
							var rows = grid.getSelectionModel().getSelections();
							var providerId = '';
							var providerName = '';
							for (var i = 0; i < rows.length; i++) {

								if (i > 0) {
									providerId += ',';
									providerName += ',';
								}

								providerId += rows[i].data.providerId;
								providerName += rows[i].data.providerName;
							}

							if (callback != null) {
								callback.call(this, providerId, providerName);
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