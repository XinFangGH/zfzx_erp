Ext.ns('CarView');
/**
 * @author:
 * @class CarView
 * @extends Ext.Panel
 * @description 车辆列表
 * @company 北京互融时代软件有限公司
 * @createtime:2010-04-12
 */
CarView = Ext.extend(Ext.Panel, {
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
		CarView.superclass.constructor.call(this, {
			id : 'CarView',
			title : '车辆列表',
			iconCls:'menu-car',
			region : 'center',
			layout : 'border',
			items : [this.searchPanel, this.gridPanel]
		});
	},// end of constructor

	// 初始化组件
	initUIComponents : function() {
		// 初始化搜索条件Panel
		this.searchPanel = new Ext.FormPanel({
			id : 'CarSearchForm',
			height : 40,
			frame : false,
			region : 'north',
			border : false,
			layout : 'form',
			layoutConfig : {
				padding : '5',
				align : 'middle'
			},
			keys : {
				key : Ext.EventObject.ENTER,
				fn : this.search,
				scope : this
			},
			items : [{
				xtype : 'container',
				fieldLabel : '请输入搜索条件',
				layout : 'column',
				style : 'margin-top:8px;',
				defaults : {
					height : 25,
					xtype : 'label',
					border : false,
					labelStyle : 'margin-left : 10px;'
				},
				layoutConfig : {
					align : 'middle'
				},
				items : [{
					style : 'margin : 4px 5px 5px 5px;',
					text : '车牌号码'
				}, {
					columnWidth : .2,
					xtype : 'textfield',
					name : 'Q_carNo_S_LK'
				}, {
					style : 'margin : 4px 5px 5px 5px;',
					text : '车牌类型'
				}, {
					columnWidth : .2,
					xtype : 'textfield',
					name : 'Q_carType_S_LK'
				}, {
					style : 'margin : 4px 5px 5px 5px;',
					text : '当前状态'
				}, {
					columnWidth : .2,
					hiddenName : 'Q_status_SN_EQ',
					xtype : 'combo',
					mode : 'local',
					editable : true,
					triggerAction : 'all',
					store : [['1', '可用'], ['2', '维修中'],['0','已报废']]
				}, {
					style : 'margin-left:5px;',
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
			}]
		});//end of the searchPanel
		
		this.store = new Ext.data.Store({
			proxy : new Ext.data.HttpProxy({
				url : __ctxPath + '/admin/listCar.do'
			}),
			reader : new Ext.data.JsonReader({
				root : 'result',
				totalProperty : 'totalCounts',
				id : 'id',
				fields : [{
					name : 'carId',
					type : 'int'
				}
				, 'carNo', 'carType', 'engineNo',
				'buyInsureTime', 'auditTime', 'notes',
				'factoryModel', 'driver', 'buyDate',
				'status', 'cartImage']
			}),
			remoteSort : true
		});
	this.store.setDefaultSort('carId', 'desc');
	this.store.reload({
		params : {
			start : 0,
			limit : 25
		}
	});
	
	var sm = new Ext.grid.CheckboxSelectionModel();
	var cm = new Ext.grid.ColumnModel({
		columns : [sm, new Ext.grid.RowNumberer(), {
					header : 'carId',
					dataIndex : 'carId',
					hidden : true
				}, {
					header : '车牌号码',
					dataIndex : 'carNo'
				}, {
					header : '车辆类型',
					dataIndex : 'carType'
				}, {
					header : '发动机型号',
					dataIndex : 'engineNo'
				}, {
					header : '购买保险时间',
					dataIndex : 'buyInsureTime'
				}, {
					header : '年审时间',
					dataIndex : 'auditTime'
				},  {
					header : '厂牌型号',
					dataIndex : 'factoryModel'
				}, {
					header : '驾驶员',
					dataIndex : 'driver'
				}, {
					header : '购置日期',
					dataIndex : 'buyDate'
				}, {
					header : '当前状态', // 1=可用2=维修中0=报废
					dataIndex : 'status',
					renderer:function(value){
					      if(value=='1'){
					         return '可用';
					      }
					      if(value=='2'){
					      	 return '维修中';
					      }
					      if(value=='0'){
					        return '已报废';
					      }
					
					}
				},{
					header : '管理',
					dataIndex : 'carId',
					width : 50,
					sortable : false,
					renderer : function(value, metadata, record, rowIndex,
							colIndex) {
						var editId = record.data.carId;
						var str='';
						if(isGranted('_CarDel')){
						str = '<button title="删除" value=" " class="btn-del" onclick="CarView.remove('
								+ editId + ')">&nbsp;</button>';
						}
						if(isGranted('_CarEdit')){
						str += '&nbsp;<button title="编辑" value=" " class="btn-edit" onclick="CarView.edit('
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
				id : 'CarFootBar',
				height : 30,
				bodyStyle : 'text-align:left',
				items : []
			});
			if(isGranted('_CarAdd')){
			this.topbar.add(new Ext.Button({
			                iconCls : 'btn-car_add',
							text : '添加车辆',
							handler : function() {
								new CarForm().show();
							}
			}));
			}
			if(isGranted('_CarDel')){
			  this.topbar.add(new Ext.Button({
			                iconCls : 'btn-car_del',
							text : '删除车辆',
							handler : function() {

								var grid = Ext.getCmp("CarGrid");

								var selectRecords = grid.getSelectionModel()
										.getSelections();

								if (selectRecords.length == 0) {
									Ext.ux.Toast.msg("信息", "请选择要删除的记录！");
									return;
								}
								var ids = Array();
								for (var i = 0; i < selectRecords.length; i++) {
									ids.push(selectRecords[i].data.carId);
								}

								CarView.remove(ids);
							}
			  }));
				
			}//end of the topbar
			
			this.gridPanel = new Ext.grid.GridPanel({
				id : 'CarGrid',
				region : 'center',
				tbar : this.topbar,
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
				if(isGranted('_CarEdit')){
					CarView.edit(rec.data.carId);
				}
			});
		});
	},//end of the initUIComponents
	
	search : function() {
		var searchPanel = Ext.getCmp('CarSearchForm');
		var gridPanel = Ext.getCmp('CarGrid');
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
		Ext.getCmp('CarSearchForm').getForm().reset();
	}
});
/**
 * 删除单个记录
 */
CarView.remove = function(id) {
	var grid = Ext.getCmp("CarGrid");
	Ext.Msg.confirm('信息确认', '删除车辆会把该车辆申请记录和维修记录一起删除，您确认要删除该记录吗？', function(btn) {
		if (btn == 'yes') {
			Ext.Ajax.request({
				url : __ctxPath + '/admin/multiDelCar.do',
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
CarView.edit = function(id) {
	new CarForm({
		carId : id
	}).show();
};
