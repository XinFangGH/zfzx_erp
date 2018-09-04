/**
 * @author
 * @class PlMmObligatoryRightChildrenView
 * @extends Ext.Panel
 * @description [PlMmObligatoryRightChildren]管理
 * @company 智维软件
 * @createtime:
 */
PlMmChildrenObligatoryRightView = Ext.extend(Ext.Window, {
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		// 初始化组件
		this.initUIComponents();
		// 调用父类构造
		PlMmChildrenObligatoryRightView.superclass.constructor.call(this, {
					id : 'PlMmChildrenObligatoryRightView',
					title : '债权库',
					region : 'center',
					layout : 'border',
					height : 500,
			        width : 1000,
					items : [this.searchPanel, this.gridPanel]
				});
	},// end of constructor
	// 初始化组件
	initUIComponents : function() {
		// 初始化搜索条件Panel
		var currentMatchingMoney=this.currentMatchingMoney;
		this.searchPanel = new HT.SearchPanel({
					layout : 'form',
					border : false,
					region : 'north',
					height : 65,
					anchor : '70%',
					keys : [{
						key : Ext.EventObject.ENTER,
						fn : this.search,
						scope : this
					}, {
						key : Ext.EventObject.ESC,
						fn : this.reset,
						scope : this
					}],
					items : [{
						border : false,
						layout : 'column',
						style : 'padding-left:5px;padding-right:0px;padding-top:5px;',
						layoutConfig : {
							align : 'middle',
							padding : '5px'
						},
						defaults : {
							xtype : 'label',
							anchor : '95%'

						},
						items : [{
							columnWidth : .3,
							labelAlign : 'right',
							xtype : 'container',
							layout : 'form',
							labelWidth : 120,
							defaults : {
								anchor : '100%'
							},
							items : [ {
										fieldLabel : '日期',
										name : 'seachDate',
										xtype : 'datefield',
										format:"Y-m-d",
										value:this.seachDate

											}]
						}				, {
										columnWidth : .07,
										xtype : 'container',
										layout : 'form',
										defaults : {
											xtype : 'button'
										},
										style : 'padding-left:10px;',
										items : [{
											text : '查询',
											scope : this,
											iconCls : 'btn-search',
											handler : this.search
										}]},	
											{columnWidth : .07,
										xtype : 'container',
										layout : 'form',
										defaults : {
											xtype : 'button'
										},
										style : 'padding-left:10px;',
										items : [{
											text : '重置',
											scope : this,
											iconCls : 'btn-reset',
											handler : this.reset
										}]
									}]
					}]
				});// end of searchPanel


		this.gridPanel = new HT.GridPanel({
			region : 'center',
			tbar : this.topbar,
			// 使用RowActions
			id : 'PlMmObligatoryRightChildrenGrid',
			url : __ctxPath
					+ "/creditFlow/financingAgency/listPlMmObligatoryRightChildren.do?childType="+this.childType+"&orderId="+this.orderId+"&keystr="+this.keystr,
			fields : [{
						name : 'childrenorId',
						type : 'int'
					}, 'parentOrBidId', 'parentOrBidName', 'childrenorName',
					'startDate', 'intentDate', 'orlimit', 'principalMoney',
					'dayRate', 'availableMoney', 'surplusValueMoney'],
			columns : [{
						header : 'childrenorId',
						dataIndex : 'childrenorId',
						hidden : true
					}, {
						header : '债权名称',
						dataIndex : 'childrenorName'
					}, {
						header : '债权起日',
						dataIndex : 'startDate'
					}, {
						header : '债权止日',
						dataIndex : 'intentDate'
					}, {
						header : '期限',
						dataIndex : 'orlimit'
					}, {
						header : '本金',
						dataIndex : 'principalMoney'
					}, {
						header : '日化利率',
						dataIndex : 'dayRate',
							align : 'right',
						renderer:function(v){
							return v+"%";
						//	return Ext.util.Format.number(v,',000,000,000.000')+"%";
						}
					}, {
						header : '可转让金额',
						dataIndex : 'availableMoney'
					}, {
						header : '债权剩余价值',
						dataIndex : 'surplusValueMoney'
					}],
					listeners : {
				   'rowdblclick' : function(grid, rowIndex, e) {
					var orderId = this.ownerCt.orderId;
					var keystr=this.ownerCt.keystr
					var s = grid.getSelectionModel().getSelections();
					var childrenorId = s[0].data.childrenorId;
					var seachDate = this.ownerCt.seachDate;
					new PlMmChildrenObligatoryRightForm({
						currentMatchingMoney:currentMatchingMoney,
						availableMoney:s[0].data.availableMoney,
						orderId:orderId,
						childrenorId:childrenorId,
						seachDate:seachDate,
						keystr:keystr
					}).show();
					
				}
		}
				// end of columns
		});


	},// end of the initComponents()
	// 重置查询表单
	reset : function() {
		this.searchPanel.getForm().reset();
	},
	// 按条件搜索
	search : function() {
		$search({
					searchPanel : this.searchPanel,
					gridPanel : this.gridPanel
				});
	},
	// GridPanel行点击处理事件
	rowClick : function(grid, rowindex, e) {
		grid.getSelectionModel().each(function(rec) {
					new PlMmObligatoryRightChildrenForm({
								childrenorId : rec.data.childrenorId
							}).show();
				});
	},
	// 创建记录
	createRs : function() {
		new PlMmObligatoryRightChildrenForm().show();
	},
	// 按ID删除记录
	removeRs : function(id) {
		$postDel({
			url : __ctxPath
					+ '/creditFlow.financingAgency.manageMoney/multiDelPlMmObligatoryRightChildren.do',
			ids : id,
			grid : this.gridPanel
		});
	},
	// 把选中ID删除
	removeSelRs : function() {
		$delGridRs({
			url : __ctxPath
					+ '/creditFlow.financingAgency.manageMoney/multiDelPlMmObligatoryRightChildren.do',
			grid : this.gridPanel,
			idName : 'childrenorId'
		});
	},
	// 编辑Rs
	editRs : function(record) {
		new PlMmObligatoryRightChildrenForm({
					childrenorId : record.data.childrenorId
				}).show();
	},
	// 行的Action
	onRowAction : function(grid, record, action, row, col) {
		switch (action) {
			case 'btn-del' :
				this.removeRs.call(this, record.data.childrenorId);
				break;
			case 'btn-edit' :
				this.editRs.call(this, record);
				break;
			default :
				break;
		}
	}
});
