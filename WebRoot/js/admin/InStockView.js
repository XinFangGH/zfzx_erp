Ext.ns('InStockView');
/**
 * @author:
 * @class InStockView
 * @extends Ext.Panel
 * @description 入库用品列表
 * @company 北京互融时代软件有限公司
 * @createtime:2010-04-12
 */
InStockView = Ext.extend(Ext.Panel, {
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
		InStockView.superclass.constructor.call(this, {
			id : 'InStockView',
			title : '入库用品列表',
			iconCls : 'menu-instock',
			region : 'center',
			layout : 'border',
			items : [this.searchPanel, this.gridPanel]
		});
	},// end of constructor

	// 初始化组件
	initUIComponents : function() {
		// 初始化搜索条件Panel
		this.searchPanel = new Ext.FormPanel({
			id : 'InStockSearchForm',
			height : 40,
			region : 'north',
			frame : false,
			border : false,
			layout : 'hbox',
			keys : {
				key : Ext.EventObject.ENTER,
				fn : this.search,
				scope : this
			},
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
				text : '商品名称'
			}, {
				xtype : 'textfield',
				name : 'Q_officeGoods.goodsName_S_LK'
			}, {
				text : '供应商'
			}, {
				xtype : 'textfield',
				name : 'Q_providerName_S_LK'
			}, {
				text : '购买人'
			}, {
				xtype : 'textfield',
				name : 'Q_buyer_S_LK'
			}, {
				xtype : 'button',
				text : '查询',
				iconCls : 'search',
				handler : this.search,
				scope : this
			}, {
				xtype : 'button',
				text : '清空',
				iconCls : 'reset',
				handler : this.clear,
				scope : this
			}]
		});//end of the searchPanel
		
		this.store = new Ext.data.Store({
			proxy : new Ext.data.HttpProxy({
				url : __ctxPath + '/admin/listInStock.do'
			}),
			reader : new Ext.data.JsonReader({
				root : 'result',
				totalProperty : 'totalCounts',
				id : 'id',
				fields : [{
					name : 'buyId',
					type : 'int'
				}, {
					name : 'goodsName',
					mapping : 'officeGoods.goodsName'
				}, 'providerName', 'stockNo', 'price',
				'inCounts', 'amount', 'inDate', 'buyer']
			}),
			remoteSort : true
		});
	this.store.setDefaultSort('buyId', 'desc');
	this.store.load({
		params : {
			start : 0,
			limit : 25
		}
	});
	
	var sm = new Ext.grid.CheckboxSelectionModel();
	var cm = new Ext.grid.ColumnModel({
		columns : [sm, new Ext.grid.RowNumberer(), {
					header : 'buyId',
					dataIndex : 'buyId',
					hidden : true
				}, {
					header : '商品名称',
					dataIndex : 'goodsName'
				}, {
					header : '供应商',
					dataIndex : 'providerName'
				}, {
					header : '库存号',
					dataIndex : 'stockNo'
				}, {
					header : '价格',
					dataIndex : 'price'
				}, {
					header : '入货数量',
					dataIndex : 'inCounts'
				}, {
					header : '总额',
					dataIndex : 'amount'
				}, {
					header : '入库日期',
					dataIndex : 'inDate',
					renderer:function(value){
					  return value.substring(0,10);
					}
				}, {
					header : '购买人',
					dataIndex : 'buyer'
				}, {
					header : '管理',
					dataIndex : 'buyId',
					sortable : false,
					width : 50,
					renderer : function(value, metadata, record, rowIndex,
							colIndex) {
						var editId = record.data.buyId;
						var str = '';
						if (isGranted('_InStockDel')) {
							str = '<button title="删除" value=" " class="btn-del" onclick="InStockView.remove('
									+ editId + ')">&nbsp;</button>';
						}
						if (isGranted('_InStockEdit')) {
							str += '&nbsp;<button title="编辑" value=" " class="btn-edit" onclick="InStockView.edit('
									+ editId + ')">&nbsp;</button>';
						}
						return str;
					}
				}],
		defaults : {
			sortable : true,
			menuDisabled : true,
			width : 100
		}
	});//end of the cm
	
	this.topbar = new Ext.Toolbar({
				id : 'InStockFootBar',
				height : 30,
				bodyStyle : 'text-align:left',
				items : []
			});
	if (isGranted('_InStockAdd')) {
		this.topbar.add(new Ext.Button({
			iconCls : 'btn-add',
			text : '添加入库单',
			handler : function() {
				new InStockForm().show();
				Ext.getCmp('InStockFormContainer').remove('stockNo');
				Ext.getCmp('InStockFormContainer').remove('amount');
			}
		}));
	};
	if (isGranted('_InStockDel')) {
		this.topbar.add(new Ext.Button({
			iconCls : 'btn-del',
			text : '删除入库单',
			handler : function() {
	
				var grid = Ext.getCmp("InStockGrid");
	
				var selectRecords = grid.getSelectionModel()
						.getSelections();
	
				if (selectRecords.length == 0) {
					Ext.ux.Toast.msg("信息", "请选择要删除的记录！");
					return;
				}
				var ids = Array();
				for (var i = 0; i < selectRecords.length; i++) {
					ids.push(selectRecords[i].data.buyId);
				}
	
				InStockView.remove(ids);
			}
		}));
	};//end of the topbar
	if (isGranted('_InStockDel')) {
		this.topbar.add(new Ext.Button({
			iconCls : 'btn-xls',
			text : '导出Excel',
			handler : function() {
				window.open(__ctxPath + '/admin/toExcelInStock.do','_blank');
			}
		}));
	};
	this.gridPanel = new Ext.grid.GridPanel({
		id : 'InStockGrid',
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
		if (isGranted('_InStockEdit')) {
			grid.getSelectionModel().each(function(rec) {
				InStockView.edit(rec.data.buyId);
			});
		}
	});
	},//end of the initUIComponents
	
	/**
	 * 搜索
	 */
	search : function() {
		var searchPanel = Ext.getCmp('InStockSearchForm');
		var gridPanel = Ext.getCmp('InStockGrid');
		if (searchPanel.getForm().isValid()) {
			$search({
				searchPanel :searchPanel,
				gridPanel : gridPanel
			});
		}
	},
	/**
	 * 清空
	 */
	clear : function(){
		Ext.getCmp('InStockSearchForm').getForm().reset();
	}
});
/**
 * 删除单个记录
 */
InStockView.remove = function(id) {
	var grid = Ext.getCmp("InStockGrid");
	Ext.Msg.confirm('信息确认', '您确认要删除该记录吗？', function(btn) {
		if (btn == 'yes') {
			Ext.Ajax.request({
				url : __ctxPath + '/admin/multiDelInStock.do',
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
InStockView.edit = function(id) {
	new InStockForm({
		buyId : id
	}).show();
};
