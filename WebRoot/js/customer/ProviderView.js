Ext.ns('ProviderView');
/**
 * @author:
 * @class ProviderView
 * @extends Ext.Panel
 * @description 供应商列表
 * @company 北京互融时代软件有限公司
 * @createtime:2010-04-12
 */
ProviderView = Ext.extend(Ext.Panel, {
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
		ProviderView.superclass.constructor.call(this, {
					id : 'ProviderView',
					title : '供应商列表',
					iconCls : 'menu-provider',
					region : 'center',
					layout : 'border',
					items : [this.searchPanel, this.gridPanel]
				});
	},// end of constructor

	// 初始化组件
	initUIComponents : function() {
		// 初始化搜索条件Panel
		this.searchPanel = new Ext.FormPanel({
			id : 'ProviderSearchForm',
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
						text : '请输入查询条件:'
					}, {
						text : '供应商名称'
					}, {
						xtype : 'textfield',
						name : 'Q_providerName_S_LK',
						width : 100
					}, {
						text : '联系人'
					}, {
						xtype : 'textfield',
						name : 'Q_contactor_S_LK',
						width : 100
					}, {
						text : '等级'
					}, {
						hiddenName : 'Q_rank_N_EQ',
						width : 100,
						xtype : 'combo',
						mode : 'local',
						editable : false,
						triggerAction : 'all',
						store : [['null', '　'], ['1', '一级供应商'], ['2', '二级供应商'],
								['3', '三级供应商'], ['4', '四级供应商']]
					}, {
						xtype : 'button',
						text : '查询',
						iconCls : 'search',
						handler : function() {
							var searchPanel = Ext.getCmp('ProviderSearchForm');
							var gridPanel = Ext.getCmp('ProviderGrid');
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

									, 'rank', 'providerName', 'contactor',
									'phone', 'address']
						}),
				remoteSort : true
			});// end of the store
	this.store.setDefaultSort('providerId', 'desc');
	this.store.load({
		params : {
			start : 0,
			limit : 25
		}
	})
	var sm = new Ext.grid.CheckboxSelectionModel();
	var cm = new Ext.grid.ColumnModel({
		columns : [sm, new Ext.grid.RowNumberer(), {
					header : 'providerId',
					dataIndex : 'providerId',
					hidden : true
				}, {
					header : '等级',
					dataIndex : 'rank',
					width : 40,
					renderer : function(value) {
						if (value == '1') {
							return '<img title="一级供应商" src="' + __ctxPath
									+ '/images/flag/customer/grade_one.png"/>';
						} else if (value == '2') {
							return '<img title="二级供应商" src="' + __ctxPath
									+ '/images/flag/customer/grade_two.png"/>';
						} else if (value == '3') {
							return '<img title="三级供应商" src="'
									+ __ctxPath
									+ '/images/flag/customer/grade_three.png"/>';
						} else {
							return '<img title="四级供应商" src="' + __ctxPath
									+ '/images/flag/customer/grade_four.png"/>';
						}
					}
				}, {
					header : '供应商名字',
					dataIndex : 'providerName',
					width : 120
				}, {
					header : '联系人',
					dataIndex : 'contactor',
					width : 80
				}, {
					header : '电话',
					dataIndex : 'phone',
					width : 80
				}, {
					header : '地址',
					dataIndex : 'address',
					width : 150
				}, {
					header : '管理',
					dataIndex : 'providerId',
					width : 100,
					sortable : false,
					renderer : function(value, metadata, record, rowIndex,
							colIndex) {
						var editId = record.data.providerId;
						var str ='';
						if (isGranted('_ProviderDel')) {
							str= '<button title="删除" value=" " class="btn-del" onclick="ProviderView.remove('
									+ editId + ')">&nbsp;&nbsp;</button>';
						}
						if (isGranted('_ProviderEdit')) {
							str += '&nbsp;<button title="编辑" value=" " class="btn-edit" onclick="ProviderView.edit('
									+ editId + ')">&nbsp;&nbsp;</button>';
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
				id : 'ProviderFootBar',
				height : 30,
				bodyStyle : 'text-align:left',
				items : []
			});

	if (isGranted('_ProviderAdd')) {
		this.topbar.add(new Ext.Button({
					iconCls : 'btn-add',
					text : '添加供应商',
					xtype : 'button',
					handler : function() {
						new ProviderForm().show();
					}
				}))
	};
	if (isGranted('_ProviderDel')) {
		this.topbar.add(new Ext.Button({
					iconCls : 'btn-del',
					text : '删除供应商',
					xtype : 'button',
					handler : function() {

						var grid = Ext.getCmp("ProviderGrid");

						var selectRecords = grid.getSelectionModel()
								.getSelections();

						if (selectRecords.length == 0) {
							Ext.ux.Toast.msg("信息", "请选择要删除的记录！");
							return;
						}
						var ids = Array();
						for (var i = 0; i < selectRecords.length; i++) {
							ids.push(selectRecords[i].data.providerId);
						}

						ProviderView.remove(ids);
					}
				}))
	};
	
	this.topbar.add(new Ext.Button({
		    xtype:'button',
		    text:'发送邮件',
		    iconCls:'btn-mail_send',
		    handler:function(){
		    	var grid = Ext.getCmp("ProviderGrid");
	
				var selectRecords = grid.getSelectionModel()
						.getSelections();
	
				if (selectRecords.length == 0) {
					Ext.ux.Toast.msg("信息", "请选择要发送邮件的供应商！");
					return;
				}
				var ids = Array();
				var names=Array();
				for (var i = 0; i < selectRecords.length; i++) {
					ids.push(selectRecords[i].data.providerId);
					names.push(selectRecords[i].data.providerName)
				}
		       new SendMailForm({
		       		ids : ids,
		       		names : names,
		       		type : 1
		       }).show();
		    }
		}
	));//end of the topbar
	
	this.gridPanel = new Ext.grid.GridPanel({
				id : 'ProviderGrid',
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
							if (isGranted('_ProviderEdit')) {
									ProviderView.edit(rec.data.providerId);
							}
						});
			});
	}//end of the initUIComponents
});
/**
 * 删除单个记录
 */
ProviderView.remove = function(id) {
	var grid = Ext.getCmp("ProviderGrid");
	Ext.Msg.confirm('信息确认', '删除供应商，则该供应商下的<font color="red">产品</font>也删除，您确认要删除该记录吗？',function(btn) {
				if (btn == 'yes') {
					Ext.Ajax.request({
								url : __ctxPath
										+ '/customer/multiDelProvider.do',
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
ProviderView.edit = function(id) {
	new ProviderForm({
		providerId : id
	}).show();
}
