Ext.ns('CarCheckView');

/**
 * @author:
 * @class CarCheckView
 * @extends Ext.Panel
 * @description 车辆申请列表
 * @company 北京互融时代软件有限公司
 * @createtime:2010-04-12
 */
CarCheckView = Ext.extend(Ext.Panel, {
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
		CarCheckView.superclass.constructor.call(this, {
					id : 'CarCheckView',
					title : '车辆申请审批列表',
					iconCls:'menu-goods-apply',
					region : 'center',
					layout : 'border',
					items : [this.searchPanel, this.gridPanel]
				});
	},// end of constructor

	// 初始化组件
	initUIComponents : function() {
		// 初始化搜索条件Panel
		this.searchPanel = new Ext.FormPanel({
			id : 'CarCheckSearchForm',
			height : 40,
			region : 'north',
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
						text : '车牌号'
					}, {
						xtype : 'textfield',
						name : 'Q_car.carNo_S_LK'
					},{
						text : '审批状态'
					}, {
						xtype : 'textfield',
						hiddenName : 'Q_approvalStatus_SN_EQ',
						xtype : 'combo',
						mode : 'local',
						editable : false,
						triggerAction : 'all',
						store : [['1', '提交审批'], ['2', '通过审批'], ['3', '不通过审批']]
					},{
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
		});// end of the searchPanel
		
		this.store = new Ext.data.Store({
			proxy : new Ext.data.HttpProxy({
				url : __ctxPath + '/admin/listCarApply.do'
			}),
			baseParams:{
			    'Q_approvalStatus_SN_GE':1
			},
			reader : new Ext.data.JsonReader({
				root : 'result',
				totalProperty : 'totalCounts',
				id : 'id',
				fields : [{
					name : 'applyId',
					type : 'int'
				}

				, {name:'carNo',
				   mapping:'car.carNo'
				}, 'department', 'userFullname',
				'applyDate', 'reason', 'startTime',
				'endTime', 'proposer', 'mileage', 'oilUse',
				'notes', 'approvalStatus']
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
				}, {
					header : '车辆车牌号',
					dataIndex : 'carNo'
				}, {
					header : '用车部门',
					dataIndex : 'department'
				}, {
					header : '用车人',
					dataIndex : 'userFullname'
				}, {
					header : '申请日期',
					dataIndex : 'applyDate',
					renderer:function(value){
					  return value.substring(0,10);
					}
				}, {
					header : '原因',
					dataIndex : 'reason'
				}, {
					header : '开始时间',
					dataIndex : 'startTime'
				}, {
					header : '结束时间',
					dataIndex : 'endTime'
				}, {
					header : '申请人',
					dataIndex : 'proposer'
				},{
					header : '审批状态',
					dataIndex : 'approvalStatus',
					renderer:function(value){
					    if(value == 1)
					        return '提交审批';
					    else if(value == 2)
					        return '通过审批';
					    else if(value == 3)
					        return '未通过审批';
					}
				}, {
					header : '管理',
					dataIndex : 'applyId',
					width : 50,
					sortable : false,
					renderer : function(value, metadata, record, rowIndex,
							colIndex) {
						var editId = record.data.applyId;
						var approvalStatus=record.data.approvalStatus;
						var str='';
						if(approvalStatus==1){
						    str += '&nbsp;<button title="审批" value=" " class="btn-check" onclick="CarCheckView.check('
										+ editId + ')">&nbsp;</button>';
						}else{
						    str += '&nbsp;<button title="查看" value=" " class="btn-readdocument" onclick="CarCheckView.detail('
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
				height : 30,
				bodyStyle : 'text-align:left',
				items : []
			});
			if(isGranted('_CarApplyAdd')){
			  this.topbar.add(new Ext.Button({
			     iconCls : 'btn-carapply-add',
				 text : '添加车辆申请单',
				 handler : function() {
					new CarApplyForm().show();
				 }
			  }));
			};
			if(isGranted('_CarApplyEdit')){
			 this.topbar.add(new Ext.Button({
			   iconCls : 'btn-carapply-del',
							text : '删除车辆申请单',
							handler : function() {

								var grid = Ext.getCmp("CarCheckGrid");

								var selectRecords = grid.getSelectionModel()
										.getSelections();

								if (selectRecords.length == 0) {
									Ext.ux.Toast.msg("信息", "请选择要删除的记录！");
									return;
								}
								var ids = Array();
								for (var i = 0; i < selectRecords.length; i++) {
									ids.push(selectRecords[i].data.applyId);
								}

								CarCheckView.remove(ids);
							}
			 }));
			};//end of the topbar
			
			this.gridPanel =  new Ext.grid.GridPanel({
				id : 'CarCheckGrid',
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
								  CarCheckView.detail(rec.data.applyId);
						});
			});
	},//end of the initUIComponents
	
	/**
	 * 搜索
	 */
	search : function() {
		var searchPanel = Ext.getCmp('CarCheckSearchForm');
		var gridPanel = Ext.getCmp('CarCheckGrid');
		if (searchPanel.getForm().isValid()) {
			$search({
				searchPanel : searchPanel,
				gridPanel : gridPanel
			});
		}
	},
	
	/**
	 * 清空数据
	 */
	clear : function(){
		Ext.getCmp('CarCheckSearchForm').getForm().reset();
	}
});
/**
 * 删除单个记录
 */
CarCheckView.remove = function(id) {
	var grid = Ext.getCmp("CarCheckGrid");
	Ext.Msg.confirm('信息确认', '您确认要删除该记录吗？', function(btn) {
				if (btn == 'yes') {
					Ext.Ajax.request({
								url : __ctxPath + '/admin/multiDelCarApply.do',
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
CarCheckView.check = function(id) {
	new CarCheckForm({
		applyId : id
	}).show();
};

CarCheckView.detail=function(id){
   new CarCheckForm({
      applyId : id,
      isView:true
   }).show();
};
