Ext.ns('CartRepairView');
/**
 * @author:
 * @class CartRepairView
 * @extends Ext.Panel
 * @description 车辆维修列表
 * @company 北京互融时代软件有限公司
 * @createtime:2010-04-12
 */
CartRepairView = Ext.extend(Ext.Panel, {
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
		CartRepairView.superclass.constructor.call(this, {
					id : 'CartRepairView',
					title : '车辆维修列表',
					iconCls:'menu-car_repair',
					region : 'center',
					layout : 'border',
					items : [this.searchPanel, this.gridPanel]
				});
	},// end of constructor

	// 初始化组件
	initUIComponents : function() {
		// 初始化搜索条件Panel
		this.searchPanel = new Ext.FormPanel({
			id : 'CartRepairSearchForm',
			region : 'north',
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
			keys : {
				key : Ext.EventObject.ENTER,
				fn : this.search,
				scope : this
			},
			items : [{
				text : '请输入查询条件:'
			}, {
				text : '车牌号码'
			}, {
				xtype : 'textfield',
				name : 'Q_car.carNo_S_LK'
			}, {
				text : '维修类型'
			}, {
				xtype : 'textfield',
				name : 'Q_repairType_S_LK'
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
							url : __ctxPath + '/admin/listCartRepair.do'
						}),
				reader : new Ext.data.JsonReader({
							root : 'result',
							totalProperty : 'totalCounts',
							id : 'id',
							fields : [{
										name : 'repairId',
										type : 'int'
									}

									, {
									name:'carNo',
									mapping:'car.carNo'
									}, 'repairDate', 'reason',
									'executant', 'notes', 'repairType', 'fee']
						}),
				remoteSort : true
			});
	this.store.setDefaultSort('repairId', 'desc');
	this.store.load({
		params : {
			start : 0,
			limit : 25
		}
	});
	var sm = new Ext.grid.CheckboxSelectionModel();
	var cm = new Ext.grid.ColumnModel({
		columns : [sm, new Ext.grid.RowNumberer(), {
					header : 'repairId',
					dataIndex : 'repairId',
					hidden : true
				}, {
					header : '车辆车牌号',
					dataIndex : 'carNo'
				}, {
					header : '维护日期',
					dataIndex : 'repairDate',
					renderer:function(value){
					  return value.substring(0,10);
					}
				}, {
					header : '维护原因',
					dataIndex : 'reason'
				}, {
					header : '经办人',
					dataIndex : 'executant'
				}, {
					header : '备注',
					dataIndex : 'notes'
				}, {
					header : '维修类型',
					dataIndex : 'repairType'
				}, {
					header : '费用',
					dataIndex : 'fee'
				}, {
					header : '管理',
					dataIndex : 'repairId',
					width : 50,
					sortable : false,
					renderer : function(value, metadata, record, rowIndex,
							colIndex) {
						var editId = record.data.repairId;
						var str='';
						if(isGranted('_CarRepairDel')){
						str = '<button title="删除" value=" " class="btn-del" onclick="CartRepairView.remove('
								+ editId + ')">&nbsp;</button>';
						}
						if(isGranted('_CarRepairEdit')){
						str += '&nbsp;<button title="编辑" value=" " class="btn-edit" onclick="CartRepairView.edit('
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
				id : 'CartRepairFootBar',
				height : 30,
				bodyStyle : 'text-align:left',
				items : []
			});
			if(isGranted('_CarRepairAdd')){
			   this.topbar.add(new Ext.Button({
			      iconCls : 'btn-add',
							text : '添加维修单',
							handler : function() {
								new CartRepairForm().show();
							}
			   }));
			};
			if(isGranted('_CarRepairDel')){
			   this.topbar.add(new Ext.Button({
			      iconCls : 'btn-del',
							text : '删除维修单',
							handler : function() {

								var grid = Ext.getCmp("CartRepairGrid");

								var selectRecords = grid.getSelectionModel()
										.getSelections();

								if (selectRecords.length == 0) {
									Ext.ux.Toast.msg("信息", "请选择要删除的记录！");
									return;
								}
								var ids = Array();
								for (var i = 0; i < selectRecords.length; i++) {
									ids.push(selectRecords[i].data.repairId);
								}

								CartRepairView.remove(ids);
							}
			   }));
			};//end of the topbar
	this.gridPanel = new Ext.grid.GridPanel({
				id : 'CartRepairGrid',
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
			});//end of the gridPanel

	this.gridPanel.addListener('rowdblclick', function(grid, rowindex, e) {
				grid.getSelectionModel().each(function(rec) {
							if(isGranted('_CarRepairEdit')){
							 CartRepairView.edit(rec.data.repairId);
							}
						});
			});
	},//end of the initUIComponents
	
	/**
	 * 搜索
	 */
	search : function() {
		var searchPanel = Ext.getCmp('CartRepairSearchForm');
		var gridPanel = Ext.getCmp('CartRepairGrid');
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
		Ext.getCmp('CartRepairSearchForm').getForm().reset();
	}
});
/**
 * 删除单个记录
 */
CartRepairView.remove = function(id) {
	var grid = Ext.getCmp("CartRepairGrid");
	Ext.Msg.confirm('信息确认', '您确认要删除该记录吗？', function(btn) {
				if (btn == 'yes') {
					Ext.Ajax.request({
								url : __ctxPath
										+ '/admin/multiDelCartRepair.do',
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
CartRepairView.edit = function(id) {
	new CartRepairForm({
		repairId : id
	}).show();
}
