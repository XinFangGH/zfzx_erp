//PlMmChildrenObligatoryView.js

/**
 * @author
 * @class PlMmChildrenObligatoryView
 * @extends Ext.Panel
 * @description [PlMmChildrenObligatoryView]管理
 * @company 智维软件
 * @createtime:
 */
PlMmChildrenObligatoryView = Ext.extend(Ext.Panel, {
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		// 初始化组件
		this.initUIComponents();
		// 调用父类构造
		PlMmChildrenObligatoryView.superclass.constructor.call(this, {
					id : 'PlMmChildrenObligatoryView'+this.type,
					title : '债权库',
					region : 'center',
					layout : 'border',
					iconCls : 'btn-tree-team30',
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
							items : [  {
										fieldLabel : '债权名称',
										name : 'parentOrBidName',
										xtype : 'textfield'

									}]
						}/*	,{
							columnWidth : .3,
							labelAlign : 'right',
							xtype : 'container',
							layout : 'form',
							labelWidth : 120,
							defaults : {
								anchor : '100%'
							},
							items : [{
										fieldLabel : '日期',
										name : 'seachDate',
										xtype : 'datefield',
										format:"Y-m-d"

											}]
						}*/		,{
							columnWidth : .3,
							labelAlign : 'right',
							xtype : 'container',
							layout : 'form',
							labelWidth : 120,
							defaults : {
								anchor : '100%'
							},
							items : [{
										fieldLabel : '目前是否可匹配',
										name : 'isMatching',
										hiddenName: 'isMatching',
										xtype:'combo',
										mode : 'local',
									    displayField : 'itemName',
									    valueField : 'itemId',
									    triggerAction : "all",
									    value:"1",
										store:new Ext.data.SimpleStore({
											fields:["itemName","itemId"],
											data:[["否","0"],["是","1"]]
										})
										

											}]
						}			, {
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

		this.topbar = new Ext.Toolbar({
					items : [{
								iconCls : 'btn-off',
								text : '更改债权到期时间',
								xtype : 'button',
								scope : this,
								handler : this.changeObligationEndTime
							}]
				});
				var summary = new Ext.ux.grid.GridSummary();
				function totalMoney(v, params, data) {
					return '总计(元)';
				}
		this.gridPanel = new HT.GridPanel({
			region : 'center',
			tbar : this.topbar,
			plugins : [summary],
			// 使用RowActions
//			id : 'PlMmObligatoryRightChildrenGrid',
			url : __ctxPath
					+ "/creditFlow/financingAgency/listPlMmObligatoryRightChildren.do?childType="+this.childType,
			fields : [{
						name : 'childrenorId',
						type : 'int'
					}, 'parentOrBidId', 'parentOrBidName', 'childrenorName',
					'startDate', 'intentDate', 'orlimit', 'principalMoney',
					'dayRate', 'availableMoney', 'surplusValueMoney'],
			columns : [{
						header : 'childrenorId',
						align:'center',
						dataIndex : 'childrenorId',
						hidden : true
					}, {
						header : '债权名称',
						dataIndex : 'parentOrBidName'
					}, {
						header : '子债权名称',
						dataIndex : 'childrenorName'
					}, {
						header : '债权起日',
						align:'center',
						summaryRenderer : totalMoney,
						dataIndex : 'startDate'
					}, {
						header : '债权止日',
						align:'center',
						dataIndex : 'intentDate'
					}, {
						header : '期限',
						align:'center',
						dataIndex : 'orlimit'
					}, {
						header : '本金',
						align:'right',
                         summaryType : 'sum',
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
						header : '可转让金额(元)',
						align : 'right',
						summaryType : 'sum',
						dataIndex : 'availableMoney'
					}, {
						header : '债权剩余价值(元)',
						summaryType : 'sum',
						align : 'right',
						dataIndex : 'surplusValueMoney'
					}]/*,
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
		}*/
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
	},
	/**
	 * 更改债权到期时间
	 * @return {Boolean}
	 */
	changeObligationEndTime:function(){
		var s = this.gridPanel.getSelectionModel().getSelections();
		if (s <= 0) {
			Ext.ux.Toast.msg('操作信息', '请选中任何一条记录');
			return false;
		} else if (s.length > 1) {
			Ext.ux.Toast.msg('操作信息', '只能选中一条记录');
			return false;
		} else {
			var intentDate=s[0].data.intentDate;
			var date1 = new Date(Date.parse(intentDate.replace(/-/g,   "/")));
            var date2 = new Date();
            if(date1>date2){//债权到期日应该小于当前日期
            	new ChangeObligationEndTime({
						childrenorId : s[0].data.childrenorId,
						childrenorName : s[0].data.childrenorName,
						intentDate : s[0].data.intentDate,
						gridPanel:this.gridPanel

					}).show();
            }else{
            	Ext.ux.Toast.msg('操作信息', '已经到期的债权不允许更改债权到期时间');
				return false;
            }
			
		}
	}
});
