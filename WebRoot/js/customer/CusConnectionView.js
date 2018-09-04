Ext.ns('CusConnectionView');
/**
 * @author:
 * @class CusConnectionView
 * @extends Ext.Panel
 * @description 交往纪录
 * @company 北京互融时代软件有限公司
 * @createtime:2010-04-12
 */
CusConnectionView = Ext.extend(Ext.Panel, {
	// 条件搜索Panel
	searchPanel : null,
	// 数据展示Panel
	gridPanel : null,
	// GridPanel的数据Store
	store : null,
	// 头部工具栏
	topbar : null,
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		// 初始化组件
		this.initUIComponents();
		// 调用父类构造
		CusConnectionView.superclass.constructor.call(this, {
					id : 'CusConnectionView',
					title : '交往记录',
					iconCls : 'menu-connection',
					region : 'center',
					layout : 'border',
					items : [this.searchPanel, this.gridPanel]
				});
	},// end of constructor

	// 初始化组件
	initUIComponents : function() {
		// 初始化搜索条件Panel
		this.searchPanel = new Ext.FormPanel({
			id : 'CusConnectionSearchForm',
			height : 35,
			region : 'north',
			frame : false,
			border : false,
			layout : 'hbox',
			layoutConfig : {
				padding : '5',
				align : 'middle'
			},
			defaults : {
				style : 'padding:0px 5px 0px 5px;',
				border : false,
				anchor : '98%,98%',
				labelWidth : 75,
				xtype : 'label'
			},
			items : [{
						text : '查询条件:'
					}, {
						text : '客户名称'
					}, {
						xtype : 'textfield',
						name : 'Q_customer.customerName_S_LK',
						width : 80
					}, {
						text : '联系人'
					}, {
						xtype : 'textfield',
						name : 'Q_contactor_S_LK',
						width : 80
					}, {
						text : '交往日期   从'
					}, {
						xtype : 'datefield',
						format : 'Y-m-d',
						editable : false,
						name : 'Q_startDate_S_LK',
						width : 80
					}, {
						text : ' 至  '
					}, {
						xtype : 'datefield',
						format : 'Y-m-d',
						editable : false,
						name : 'Q_endDate_S_LK',
						width : 80
					}, {
						xtype : 'button',
						text : '查询',
						iconCls : 'search',
						handler : function() {
							var searchPanel = Ext
									.getCmp('CusConnectionSearchForm');
							var gridPanel = Ext.getCmp('CusConnectionGrid');
							if (searchPanel.getForm().isValid()) {
								$search({
									searchPanel :searchPanel,
									gridPanel : gridPanel
								});
							}

						}
					}]
		});//end of the searchPanel
		
		this.store = store = new Ext.data.Store({
				proxy : new Ext.data.HttpProxy({
							url : __ctxPath + '/customer/listCusConnection.do'
						}),
				reader : new Ext.data.JsonReader({
							root : 'result',
							totalProperty : 'totalCounts',
							id : 'id',
							fields : [{
										name : 'connId',
										type : 'int'
									}

									, 'customer', 'contactor', 'startDate',
									'endDate', 'content', 'notes', 'creator']
						}),
				remoteSort : true
			});
	this.store.setDefaultSort('connId', 'desc');
	this.store.load({
		params : {
			start : 0,
			limit : 25
		}
	});//end of the store
	
	var sm = new Ext.grid.CheckboxSelectionModel();
	var cm = new Ext.grid.ColumnModel({
		columns : [sm, new Ext.grid.RowNumberer(), {
					header : 'connId',
					dataIndex : 'connId',
					hidden : true
				}, {
					header : '内容',
					dataIndex : 'content'
				}, {
					header : '客户名称',
					dataIndex : 'customer',
					renderer : function(value) {
						return value.customerName;
					}
				}, {
					header : '联系人',
					dataIndex : 'contactor'
				}, {
					header : '交往日期',
					width : 200,
					dataIndex : 'startDate',
					renderer : function(value, metadata, record) {
						return value.substring(0,10) + "——" 
						+ record.data.endDate.substring(0,10);
					}
				}, {
					header : '创建人',
					dataIndex : 'creator'
				}, {
					header : '管理',
					dataIndex : 'connId',
					width : 50,
					sortable : false,
					renderer : function(value, metadata, record, rowIndex,
							colIndex) {
						var editId = record.data.connId;
						var str = '';
						if (isGranted('_CusConnectionDel')) {
							str = '<button title="删除" value=" " class="btn-del" onclick="CusConnectionView.remove('
									+ editId + ')">&nbsp</button>';
						}
						if (isGranted('_CusConnectionEdit')) {
							str += '&nbsp;<button title="编辑" value=" " class="btn-edit" onclick="CusConnectionView.edit('
									+ editId + ')">&nbsp</button>';
						}
						return str;
					}
				}],
		defaults : {
			sortable : true,
			menuDisabled : false,
			width : 100
		}
	});//end of the cm

	this.topbar = new Ext.Toolbar({
				id : 'CusConnectionFootBar',
				height : 30,
				bodyStyle : 'text-align:left',
				items : []
			});
	if (isGranted('_CusConnectionAdd')) {
		this.topbar.add(new Ext.Button({
					iconCls : 'btn-add',
					text : '添加记录',
					handler : function() {
						new CusConnectionForm().show();
					}
				}));
	};
	if (isGranted('_CusConnectionDel')) {
		this.topbar.add(new Ext.Button({
					iconCls : 'btn-del',
					text : '删除记录',
					handler : function() {

						var grid = Ext.getCmp("CusConnectionGrid");

						var selectRecords = grid.getSelectionModel()
								.getSelections();

						if (selectRecords.length == 0) {
							Ext.ux.Toast.msg("信息", "请选择要删除的记录！");
							return;
						}
						var ids = Array();
						for (var i = 0; i < selectRecords.length; i++) {
							ids.push(selectRecords[i].data.connId);
						}

						CusConnectionView.remove(ids);
					}
				}));
	};//end of the topbar
	
	this.gridPanel = new Ext.grid.GridPanel({
				id : 'CusConnectionGrid',
				tbar : this.topbar,
				region : 'center',
				store : this.store,
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
				bbar : new HT.PagingBar({store : this.store})
			});

	this.gridPanel.addListener('rowdblclick', function(grid, rowindex, e) {
				grid.getSelectionModel().each(function(rec) {
							if (isGranted('_CusConnectionEdit')) {
								CusConnectionView.edit(rec.data.connId);
							}
						});
			});
	}//end of the initUIComponents
});
/**
 * 删除单个记录
 */
CusConnectionView.remove = function(id) {
	var grid = Ext.getCmp("CusConnectionGrid");
	Ext.Msg.confirm('信息确认', '您确认要删除该记录吗？', function(btn) {
				if (btn == 'yes') {
					Ext.Ajax.request({
								url : __ctxPath
										+ '/customer/multiDelCusConnection.do',
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
CusConnectionView.edit = function(id) {
	new CusConnectionForm({
		connId : id
	}).show();
}
