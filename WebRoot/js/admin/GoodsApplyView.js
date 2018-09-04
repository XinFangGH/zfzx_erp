Ext.ns('GoodsApplyView');
/**
 * @author:
 * @class GoodsApplyView
 * @extends Ext.Panel
 * @description 办公用品申请列表
 * @company 北京互融时代软件有限公司
 * @createtime:2010-04-12
 */
GoodsApplyView = Ext.extend(Ext.Panel, {
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
		GoodsApplyView.superclass.constructor.call(this, {
			id : 'GoodsApplyView',
			title : '办公用品申请',
			iconCls : 'menu-conf-summary',
			region : 'center',
			layout : 'border',
			items : [this.searchPanel, this.gridPanel]
		});
	},// end of constructor

	// 初始化组件
	initUIComponents : function() {
		// 初始化搜索条件Panel
		this.searchPanel = new Ext.FormPanel({
			id : 'GoodsApplySearchForm',
			height : 40,
			region : 'north',
			frame : false,
			border : false,
			layout : 'hbox',
			layoutConfig : {
				padding : '5',
				align : 'middle'
			},
			keys : {
				key : Ext.EventObject.ENTER,
				fn : this.search,
				scope : this
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
						text : '申请号'
					}, {
						xtype : 'textfield',
						name : 'Q_applyNo_S_LK'
					}, {
						text : '申请人'
					}, {
						xtype : 'textfield',
						name : 'Q_proposer_S_LK'
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
		
		this.store =  new Ext.data.Store({
			proxy : new Ext.data.HttpProxy({
				url : __ctxPath + '/admin/listGoodsApply.do'
			}),
			reader : new Ext.data.JsonReader({
				root : 'result',
				totalProperty : 'totalCounts',
				id : 'id',
				fields : [{
					name : 'applyId',
					type : 'int'
				}, {
					name : 'goodsName',
					mapping : 'officeGoods.goodsName'
				}, 'applyDate', 'applyNo', 'useCounts',
				'proposer', 'notes', 'approvalStatus']
		}),
			remoteSort : true
		});
	this.store.setDefaultSort('applyId', 'desc');
	this.store.load({
		params : {
			start : 0,
			limit : 25
		}
	});
	var sm = new Ext.grid.CheckboxSelectionModel();
	var cm = new Ext.grid.ColumnModel({
		columns : [sm, new Ext.grid.RowNumberer(), {
					header : 'applyId',
					dataIndex : 'applyId',
					hidden : true
				},{
					header : '申请号',
					dataIndex : 'applyNo'
				}, {
					header : '商品名称',
					dataIndex : 'goodsName'
				}, {
					header : '申请日期',
					dataIndex : 'applyDate',
					renderer:function(value){
					  return value.substring(0,10);
					}
				},  {
					header : '申请数量',
					dataIndex : 'useCounts'
				}, {
					header : '申请人',
					dataIndex : 'proposer'
				}, {
					header : '备注',
					dataIndex : 'notes'
				}, {
					header : '审批状态',
					dataIndex : 'approvalStatus',
					renderer : function(value) {
						if (value == '0') {
							return '草稿';
						}else if (value == '1') {
							return '提交审批';
						}else if(value==2){
							return '通过审批';
						}else if(value==3){
						   return '未通过审批';
						}
					}
				}, {
					header : '管理',
					dataIndex : 'applyId',
					width : 50,
					renderer : function(value, metadata, record, rowIndex,
							colIndex) {
						var editId = record.data.applyId;
						var approvalStatus=record.data.approvalStatus;
						var str = '';
						if(approvalStatus==0||approvalStatus==3){
							if (isGranted('_GoodsApplyDel')) {
								str = '<button title="删除" value=" " class="btn-del" onclick="GoodsApplyView.remove('
										+ editId + ')">&nbsp;</button>';
							}
							if (isGranted('_GoodsApplyEdit')) {
									str += '&nbsp;<button title="编辑" value=" " class="btn-edit" onclick="GoodsApplyView.edit('
											+ editId + ')">&nbsp;</button>';
							}
						}else{
						   str += '&nbsp;<button title="查看" value=" " class="btn-readdocument" onclick="GoodsApplyView.detail('
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
				id : 'GoodsApplyFootBar',
				height : 30,
				bodyStyle : 'text-align:left',
				items : []
			});
	if (isGranted('_GoodsApplyAdd')) {
		this.topbar.add(new Ext.Button({
					iconCls : 'btn-add',
					text : '添加申请单',
					handler : function() {
						new GoodsApplyForm().show();
						Ext.getCmp('GoodsApplyForm').remove('applyNo');
					}
				}));
	};
	if (isGranted('_GoodsApplyDel')) {
		this.topbar.add(new Ext.Button({
					iconCls : 'btn-del',
					text : '删除申请单',
					handler : function() {
						var grid = Ext.getCmp("GoodsApplyGrid");
						var selectRecords = grid.getSelectionModel().getSelections();
						if (selectRecords.length == 0) {
							Ext.ux.Toast.msg("信息", "请选择要删除的记录！");
							return;
						}
						var ids = Array();
						for (var i = 0; i < selectRecords.length; i++) {
							if(selectRecords[i].data.approvalStatus==1||selectRecords[i].data.approvalStatus==2){
							    Ext.ux.Toast.msg("信息", "所选记录中有记录不能被删除！");
							    return;
							}
							ids.push(selectRecords[i].data.applyId);
						}

						GoodsApplyView.remove(ids);
					}
				}));
	};//end of the topbar
	
	this.gridPanel = new Ext.grid.GridPanel({
		id : 'GoodsApplyGrid',
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
			if (isGranted('_GoodsApplyEdit')) {
				if(rec.data.approvalStatus == 0){
					GoodsApplyView.edit(rec.data.applyId);
				}
			}
		});
	});
	},//end of the initUIComponents
	
	/**
	 * 搜索
	 */
	search :  function() {
		var searchPanel = Ext.getCmp('GoodsApplySearchForm');
		var gridPanel = Ext.getCmp('GoodsApplyGrid');
		if (searchPanel.getForm().isValid()) {
			var store = gridPanel.getStore();
			var baseParam = Ext.Ajax.serializeForm(searchPanel.getForm().getEl());
			var deParams = Ext.urlDecode(baseParam);
			deParams.start = 0;
			deParams.limit = store.baseParams.limit;
			store.baseParams = deParams;
			gridPanel.getBottomToolbar().moveFirst();
		}
	},
	
	/**
	 * 清空
	 */
	clear : function(){
		Ext.getCmp('GoodsApplySearchForm').getForm().reset();
	}
});
/**
 * 删除单个记录
 */
GoodsApplyView.remove = function(id) {
	var grid = Ext.getCmp("GoodsApplyGrid");
	Ext.Msg.confirm('信息确认', '您确认要删除该记录吗？', function(btn) {
		if (btn == 'yes') {
			Ext.Ajax.request({
				url : __ctxPath + '/admin/multiDelGoodsApply.do',
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
GoodsApplyView.edit = function(id) {
	new GoodsApplyForm({
		applyId : id
	}).show();
};

GoodsApplyView.detail=function(id){
    new GoodsCheckForm({applyId:id,isView:true}).show();
};
