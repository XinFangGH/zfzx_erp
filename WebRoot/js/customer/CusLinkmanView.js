Ext.ns('CusLinkmanView');
/**
 * @author:
 * @class CustomerView
 * @extends Ext.Panel
 * @description 联系人信息列表
 * @company 北京互融时代软件有限公司
 * @createtime:2010-04-12
 */
CusLinkmanView = Ext.extend(Ext.Panel, {
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
		CusLinkmanView.superclass.constructor.call(this, {
					id : 'CusLinkmanView',
					title : '联系人信息',
					iconCls : 'menu-cusLinkman',
					region : 'center',
					layout : 'border',
					items : [this.searchPanel, this.gridPanel]
				});
	},// end of constructor

	// 初始化组件
	initUIComponents : function() {
		// 初始化搜索条件Panel
		this.searchPanel = new Ext.FormPanel({
			id : 'CusLinkmanSearchForm',
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
						text : '所属客户'// 下拉列表选择?模糊查找吧
					}, {
						xtype : 'textfield',
						name : 'Q_customer.customerName_S_LK',
						width : 80
					}, {
						text : '姓名'
					}, {
						xtype : 'textfield',
						name : 'Q_fullname_S_LK',
						width : 80
					}, {
						text : '手机'
					}, {
						xtype : 'textfield',
						name : 'Q_mobile_S_LK',
						width : 80
					}, {
						text : 'QQ'
					}, {
						xtype : 'textfield',
						name : 'Q_qq_S_LK',
						width : 80
					}, {
						text : '主要联系人'// 是否
					}, {
						xtype : 'textfield',
						name : 'Q_isPrimary_S_LK',
						width : 40
					}, {
						xtype : 'button',
						text : '查询',
						iconCls : 'search',
						handler : function() {
							var searchPanel = Ext
									.getCmp('CusLinkmanSearchForm');
							var gridPanel = Ext.getCmp('CusLinkmanGrid');
							if (searchPanel.getForm().isValid()) {
								$search({
									searchPanel :searchPanel,
									gridPanel : gridPanel
								});
							}
						}
					}]
		});//end of the searchPanel
		
		this.store = new Ext.data.Store({
				proxy : new Ext.data.HttpProxy({
							url : __ctxPath + '/customer/listCusLinkman.do'
						}),
				reader : new Ext.data.JsonReader({
							root : 'result',
							totalProperty : 'totalCounts',
							id : 'id',
							fields : [{
										name : 'linkmanId',
										type : 'int'
									}

									, 'customer', 'fullname', 'sex',
									'position', 'phone', 'mobile', 'email',
									'msn', 'qq', 'birthday', 'homeAddress',
									'homeZip', 'homePhone', 'hobby',
									'isPrimary', 'notes']
						}),
				remoteSort : true
			});
	this.store.setDefaultSort('linkmanId', 'desc');
	this.store.load({
		params:{
			start : 0,
			limit : 25
		}
	})
	var sm = new Ext.grid.CheckboxSelectionModel();
	var cm = new Ext.grid.ColumnModel({
		columns : [sm, new Ext.grid.RowNumberer(), {
					header : 'linkmanId',
					dataIndex : 'linkmanId',
					hidden : true
				}, {
					header : '主联系人',
					dataIndex : 'isPrimary',
					width : 70,
					renderer : function(value) {
						if (value == '1') {
							return '<img title="主要联系人" src="' + __ctxPath
									+ '/images/flag/mail/important.png"/>';
						}
					}
				}, {
					header : '性别',
					dataIndex : 'sex',
					width : 45,
					renderer : function(value) {
						if (value == '1') {
							return '<img title="先生" src="' + __ctxPath
									+ '/images/flag/customer/male.png"/>';
						} else {
							return '<img title="女士" src="' + __ctxPath
									+ '/images/flag/customer/female.png"/>';
						}
					}
				}, {
					header : '所属客户',
					dataIndex : 'customer',
					renderer : function(value) {
						return value.customerName;
					}
				}, {
					header : '姓名',
					dataIndex : 'fullname'
				}, {
					header : '职位',
					dataIndex : 'position'
				}, {
					header : '电话',
					dataIndex : 'phone'
				}, {
					header : '手机',
					dataIndex : 'mobile'
				}, {
					header : 'Email',
					dataIndex : 'email'
				},{
					header : '管理',
					dataIndex : 'linkmanId',
					width : 100,
					sortable : false,
					renderer : function(value, metadata, record, rowIndex,
							colIndex) {
						var editId = record.data.linkmanId;
						var str = '';
						if (isGranted('_CusLinkmanDel')) {
							str = '<button title="删除" value=" " class="btn-del" onclick="CusLinkmanView.remove('
									+ editId + ')">&nbsp</button>';
						}
						if (isGranted('_CusLinkmanEdit')) {
							str += '&nbsp;<button title="编辑" value=" " class="btn-edit" onclick="CusLinkmanView.edit('
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
				id : 'CusLinkmanFootBar',
				height : 30,
				bodyStyle : 'text-align:left',
				items : []
			});
	if (isGranted('_CusLinkmanAdd')) {
		this.topbar.add(new Ext.Button({
					iconCls : 'btn-add',
					text : '添加联系人',
					handler : function() {
						new CusLinkmanForm().show();
					}
				}));
	}
	if (isGranted('_CusLinkmanDel')) {
		this.topbar.add(new Ext.Button({
					iconCls : 'btn-del',
					text : '删除联系人',
					handler : function() {

						var grid = Ext.getCmp("CusLinkmanGrid");

						var selectRecords = grid.getSelectionModel()
								.getSelections();

						if (selectRecords.length == 0) {
							Ext.ux.Toast.msg("信息", "请选择要删除的记录！");
							return;
						}
						var ids = Array();
						for (var i = 0; i < selectRecords.length; i++) {
							ids.push(selectRecords[i].data.linkmanId);
						}

						CusLinkmanView.remove(ids);
					}
				}));

	}//end of the topbar
	
	this.gridPanel = new Ext.grid.GridPanel({
				id : 'CusLinkmanGrid',
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
							if (isGranted('_CusLinkmanEdit')) {
								CusLinkmanView.edit(rec.data.linkmanId);
							}
						});
			});
	}//end of the initUIComponents
});

/**
 * 删除单个记录
 */
CusLinkmanView.remove = function(id) {
	var grid = Ext.getCmp('CusLinkmanGrid');
	Ext.Msg.confirm('信息确认', '您确认要删除该记录吗？', function(btn) {
				if (btn == 'yes') {
					Ext.Ajax.request({
								url : __ctxPath
										+ '/customer/multiDelCusLinkman.do',
								params : {
									ids : id
								},
								method : 'post',
								success : function() {
									Ext.ux.Toast.msg("信息提示", "成功删除所选记录！");
									if (grid != null) {
										grid.getStore().reload({
													params : {
														start : 0,
														limit : 25
													}
												});
									}
								}
							});
				}
			});
};

/**
 * 
 */
CusLinkmanView.edit = function(id) {
	new CusLinkmanForm({
		linkmanId : id
	}).show();
}
