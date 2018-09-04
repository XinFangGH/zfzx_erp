Ext.ns('OfficeGoodsView');
/**
 * @author:
 * @class OfficeGoodsView
 * @extends Ext.Panel
 * @description 办公用品列表
 * @company 北京互融时代软件有限公司
 * @createtime:2010-04-12
 */
OfficeGoodsView = Ext.extend(Ext.Panel, {
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
		OfficeGoodsView.superclass.constructor.call(this, {
			id : 'OfficeGoodsView',
			title : '办公用品列表',
			region : 'center',
			layout : 'border',
			items : [this.searchPanel, this.gridPanel]
		});
	},// end of constructor

	// 初始化组件
	initUIComponents : function() {
		// 初始化搜索条件Panel
		this.searchPanel = new Ext.FormPanel({
			id : 'OfficeGoodsSearchForm',
			height : 40,
			frame : false,
			region : 'north',
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
			keys : {
				key : Ext.EventObject.ENTER,
				fn : this.search,
				scope : this
			},
			items : [{
						text : '请输入查询条件:'
					}, {
						text : '物品名称'
					}, {
						xtype : 'textfield',
						name : 'Q_goodsName_S_LK'
					}, {
						text : '所属分类'
					}, {
						xtype : 'textfield',
						name : 'Q_officeGoodsType.typeName_S_LK'
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
						handler : function(){
							Ext.getCmp('OfficeGoodsSearchForm').getForm().reset();
						}
					}]
		});//end of the searchPanel
		
		this.store =  new Ext.data.Store({
			proxy : new Ext.data.HttpProxy({
				url : __ctxPath + '/admin/listOfficeGoods.do'
			}),
			reader : new Ext.data.JsonReader({
				root : 'result',
				totalProperty : 'totalCounts',
				id : 'id',
				fields : [{
					name : 'goodsId',
					type : 'int'
				}, {
					name : 'typeName',
					mapping : 'officeGoodsType.typeName'
				}, 'goodsName', 'goodsNo',
				'specifications', 'unit', 'isWarning','warnCounts',
				'notes', 'stockCounts']
			}),
			remoteSort : true
		});
	this.store.setDefaultSort('goodsId', 'desc');
	this.store.load({
		params : {
			start : 0,
			limit : 25
		}
	});
	var sm = new Ext.grid.CheckboxSelectionModel();
	var cm = new Ext.grid.ColumnModel({
		columns : [sm, new Ext.grid.RowNumberer(), {
					header : 'goodsId',
					dataIndex : 'goodsId',
					hidden : true
				}, {
					header : '所属分类',
					dataIndex : 'typeName'
				}, {
					header : '物品名称',
					dataIndex : 'goodsName'
				}, {
					header : '编号',
					dataIndex : 'goodsNo'
				}, {
					header : '规格',
					dataIndex : 'specifications'
				}, {
					header : '计量单位',
					dataIndex : 'unit'
				}, {
					header : '是否启用库存警示',
					dataIndex : 'isWarning',
					renderer : function(value) {
						if (value == '0') {
							return '未启动';
						}
						if (value == '1') {
							return '已启动';
						}
					}
				}, {
					header : '备注',
					dataIndex : 'notes'
				}, {
					header : '库存总数',
					dataIndex : 'stockCounts',
					renderer:function(value,metadata,record,rowIndex,colIndex){
					     var warnCounts=record.data.warnCounts;
					     var isWarning=record.data.isWarning;
					     if(value<=warnCounts&&isWarning=='1'){
					         return '<a style="color:red;" title="已少于警报库存！">'+value+'</a>';
					     }else{
					         return value;
					     }
					}
				}, {
					header : '管理',
					dataIndex : 'goodsId',
					width : 50,
					renderer : function(value, metadata, record, rowIndex,
							colIndex) {
						var editId = record.data.goodsId;
						var str='';
						if(isGranted('_OfficeGoodsDel')){
							str = '<button title="删除" value=" " class="btn-del" onclick="OfficeGoodsView.remove('
									+ editId + ')">&nbsp;</button>';
						}
						if(isGranted('_OfficeGoodsEdit')){
							str += '&nbsp;<button title="编辑" value=" " class="btn-edit" onclick="OfficeGoodsView.edit('
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
	
	this.topbar =  new Ext.Toolbar({
		id : 'OfficeGoodsFootBar',
		height : 30,
		bodyStyle : 'text-align:left',
		items : []
	});
	if(isGranted('_OfficeGoodsAdd')){
		this.topbar.add(new Ext.Button({
            iconCls : 'btn-add',
			text : '添加办公用品',
			handler : function() {
				new OfficeGoodsForm().show();
				Ext.getCmp('OfficeGoodsFormItems').remove('goodsNo');
				Ext.getCmp('OfficeGoodsFormS').remove('stockCounts');
			}
		}));
	}
	if(isGranted('_OfficeGoodsDel')){
	   this.topbar.add(new Ext.Button({
            iconCls : 'btn-del',
			text : '删除办公用品',
			handler : function() {
				var grid = Ext.getCmp("OfficeGoodsGrid");
				var selectRecords = grid.getSelectionModel().getSelections();
				if (selectRecords.length == 0) {
					Ext.ux.Toast.msg("信息", "请选择要删除的记录！");
					return;
				}
				var ids = Array();
				for (var i = 0; i < selectRecords.length; i++) {
					ids.push(selectRecords[i].data.goodsId);
				}
				OfficeGoodsView.remove(ids);
			}
	   }));
	};//end of the topbar
	if(isGranted('_OfficeGoodsAdd')){
		this.topbar.add(new Ext.Button({
			iconCls : 'btn-xls',
			text : '导出Excel',
			handler : function() {
				window.open(__ctxPath + '/admin/toExcelOfficeGoods.do','_blank');
			}
		}));
	}
	this.gridPanel =  new Ext.grid.GridPanel({
		id : 'OfficeGoodsGrid',
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
				if(isGranted('_OfficeGoodsEdit')){
					grid.getSelectionModel().each(function(rec) {
								OfficeGoodsView.edit(rec.data.goodsId);
							});
				}
			});
	},//end of the initUIComponents
	
	/**
	 * 搜索
	 */
	search : function() {
		var searchPanel = Ext.getCmp('OfficeGoodsSearchForm');
		var gridPanel = Ext.getCmp('OfficeGoodsGrid');
		if (searchPanel.getForm().isValid()) {
			$search({
				searchPanel :searchPanel,
				gridPanel : gridPanel
			});
		}

	}
});


OfficeGoodsView.prototype.setTypeId = function(typeId) {
	this.typeId = typeId;
	OfficeGoodsView.typeId = typeId;
};
OfficeGoodsView.prototype.getTypeId = function() {
	return this.typeId;
};

/**
 * 删除单个记录
 */
OfficeGoodsView.remove = function(id) {
	var grid = Ext.getCmp("OfficeGoodsGrid");
	Ext.Msg.confirm('信息确认', '会把入库和申请记录一起删除，您确认要删除该记录吗？', function(btn) {
		if (btn == 'yes') {
			Ext.Ajax.request({
				url : __ctxPath + '/admin/multiDelOfficeGoods.do',
				params : {
					ids : id
				},
				method : 'post',
				success : function() {
					Ext.ux.Toast.msg('信息提示', '成功删除所选记录！');
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
OfficeGoodsView.edit = function(id) {
	new OfficeGoodsForm({
		goodsId : id
	}).show();
};
