/**
 * @author
 * @class PlMmOrderChildrenOrView
 * @extends Ext.Panel
 * @description [PlMmOrderChildrenOr]管理
 * @company 智维软件
 * @createtime:
 */
investmentMmView = Ext.extend(Ext.Panel, {
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		// 初始化组件
		this.initUIComponents();
		// 调用父类构造
		investmentMmView.superclass.constructor.call(this, {
					id : 'investmentMmView',
					title : '投资人理财查询',
					region : 'center',
					layout : 'border',
					iconCls:"btn-tree-team43",
					items : [this.searchPanel, this.gridPanel]
				});
	},// end of constructor
	// 初始化组件
	initUIComponents : function() {
		// 初始化搜索条件Panel
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
					columnWidth : .2,
					labelAlign : 'right',
					xtype : 'container',
					layout : 'form',
					labelWidth : 60,
					defaults : {
						anchor : '100%'
					},
					items : [{
						fieldLabel : '投资人',
						editable : false,
						xtype : 'textfield',
						//triggerClass : 'x-form-search-trigger',
						//id : 'plManageMoneyPlanBuyinfo.investPersonName',
						//maxLength : 15,
						name : 'investPersonName',
						/*onTriggerClick : function() {
							new selectInvestPersonList({
										formPanel : Ext.getCmp('investmentMmView')
									}).show();
						},*/
						anchor : '100%'
					}, {
						xtype : "hidden",
						name : 'investPersonId'

					}]
				},/* {

					columnWidth : .2,
					labelAlign : 'right',
					xtype : 'container',
					layout : 'form',
					labelWidth : 120,
					defaults : {
						anchor : '100%'
					},
					items : [{
						xtype : 'combo',
						mode : 'local',
						displayField : 'name',
						valueField : 'id',
						//id : 'accountTypeid',
						editable : false,
						width : 70,
						store : new Ext.data.SimpleStore({
									fields : ["name", "id"],
									data : [["理财计划", "mmplan"],
											["理财产品", "mmproduce"]]
								}),
						triggerAction : "all",
						hiddenName : "keystr",
						fieldLabel : '理财类型',
						anchor : '100%',
						name : 'keystr',
						listeners : {
							afterrender : function(combox) {
								var st = combox.getStore();
								combox.setValue(combox.getValue());
							}

						}
					}]

				},*/ {

					columnWidth : .2,
					labelAlign : 'right',
					xtype : 'container',
					layout : 'form',
					labelWidth : 120,
					defaults : {
						anchor : '100%'
					},
					items : [{
								fieldLabel : '理财计划/产品名称',
								name : 'mmName',
								xtype : 'textfield'

							}]

				}, {
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
							}]
				}, {
					columnWidth : .07,
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
					items : [/*{
								iconCls : 'btn-user-sel',
								text : '预测债权清单',
								xtype : 'button',
								scope : this,
								handler : this.matchForecast
							}, new Ext.Toolbar.Separator({
							}),*/ {
								iconCls : 'btn-user-list',
								text : '实际债权清单',
								xtype : 'button',
								scope : this,
								handler : this.matchinglist
							}, new Ext.Toolbar.Separator({}), {
								iconCls : 'btn-user-profit',
								text : '收益清单',
								xtype : 'button',
								scope : this,
								handler : this.assignLixi
							}]
				});
          var summary = new Ext.ux.grid.GridSummary();
				function totalMoney(v, params, data) {
					return '总计';
				}
		this.gridPanel = new HT.GridPanel({
			region : 'center',
			tbar : this.topbar,
			plugins : [summary],
			singleSelect : true,
			//isautoLoad:false,
			// 使用RowActions
			id : 'PlMmOrderChildrenOrGrid' + this.keystr,
			url : __ctxPath
					+ "/creditFlow/financingAgency/listPlManageMoneyPlanBuyinfo.do?keystr="+this.keystr,
			fields : [{
						name : 'orderId',
						type : 'int'
					}, 'mmplanId', 'buyDatetime', 'investPersonId',
					'investPersonName', 'buyMoney', 'startinInterestTime',
					'endinInterestTime', 'orderlimit', 'promisDayRate',
					'promisIncomeSum', 'currentMatchingMoney',
					'currentGetedMoney', 'optimalDayRate', 'keystr', 'mmName'],
			columns : [{
						header : 'matchId',
						align:'center',
						dataIndex : 'matchId',
						hidden : true
					}, {
						header : '投资人',
						align:'center',
						dataIndex : 'investPersonName'
					}, {
						header : '理财类型',
						align:'center',
						summaryRenderer : totalMoney,
						dataIndex : 'keystr',
						renderer : function(v) {
							if (v == "mmplan") {
								return "理财计划"
							} else {

								return "理财产品"
							}
						}
					}, {
						header : '理财计划/产品名称',
						dataIndex : 'mmName',
						width : 130
					}, {
						header : '购买金额',
						dataIndex : 'buyMoney',
						align : 'right',
						summaryType : 'sum',
						renderer : function(v) {
							return Ext.util.Format.number(v, ',000,000,000.00')
									+ "元";
						}
					}, {
						header : '计息起日',
						align:'center',
						dataIndex : 'startinInterestTime'
					}, {
						header : '计息止日',
						align:'center',
						dataIndex : 'endinInterestTime'
					}, {
						header : '订单期限',
						align:'center',
						dataIndex : 'orderlimit',
						summaryType : 'sum',
						renderer : function(v) {
							return v + "天";
						}
					}, {
						header : '承诺日化利率',
						dataIndex : 'promisDayRate',
						align : 'right',
						renderer : function(v) {
							return Ext.util.Format
									.number(v, ',000,000,000.000')
									+ "%";
						}
					}, {
						header : '承诺总收益',
						dataIndex : 'promisIncomeSum',
						align : 'right',
						summaryType : 'sum',
						renderer : function(v) {
							return Ext.util.Format.number(v, ',000,000,000.00')
									+ "元";
						}
					}, {
						header : '当期已实收益',
						dataIndex : 'currentGetedMoney',
						align : 'right',
						summaryType : 'sum',
						renderer : function(v) {
							return Ext.util.Format.number(v, ',000,000,000.00')
									+ "元";
						}
					}/*, {
						header : '可匹配金额',
						dataIndex : 'currentMatchingMoney',
						align : 'right',
						renderer : function(v) {
							return Ext.util.Format.number(v, ',000,000,000.00')
									+ "元";
						}
					}, {
						header : '当期已实收益',
						dataIndex : 'currentGetedMoney',
						align : 'right',
						renderer : function(v) {
							return Ext.util.Format.number(v, ',000,000,000.00')
									+ "元";
						}
					}, {
						header : '最优日化利率',
						dataIndex : 'optimalDayRate',
						align : 'right',
						renderer : function(v) {
							return Ext.util.Format
									.number(v, ',000,000,000.000')
									+ "%";
						}
					}*/]
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
					new PlMmOrderChildrenOrForm({
								matchId : rec.data.matchId
							}).show();
				});
	},
	// 创建记录
	createRs : function() {
		var seachDate = Ext.getCmp("seachDate" + this.keystr).getValue();
		var s = this.gridPanel.getSelectionModel().getSelections();
		if (s <= 0) {
			Ext.ux.Toast.msg('操作信息', '请选中任何一条记录');
			return false;
		} else if (s.length > 1) {
			Ext.ux.Toast.msg('操作信息', '只能选中一条记录');
			return false;
		} else {
			if (parseInt(s[0].data.currentMatchingMoney) == 0) {

				Ext.ux.Toast.msg('操作信息', '匹配金额已经为0，不能再匹配了');
			} else {
				new PlMmChildrenObligatoryRightView({

							orderId : s[0].data.orderId,
							seachDate : seachDate,
							keystr : this.keystr

						}).show();
			}
		}
	},
	autoMatching : function() {
		var seachDate = Ext.getCmp("seachDate" + this.keystr).getValue();
		var keystr = this.keystr;
		Ext.Ajax.request({
			url : __ctxPath
					+ "/creditFlow/financingAgency/automatchingPlMmOrderChildrenOr.do",
			method : 'post',
			success : function(response, request) {
				Ext.getCmp("PlMmOrderChildrenOrGrid" + keystr).getStore()
						.reload();
				Ext.ux.Toast.msg('操作信息', '系统自动匹配成功');
			},
			params : {
				seachDate : seachDate
			}
		});
	},
	// 按ID删除记录
	removeRs : function(id) {
		$postDel({
			url : __ctxPath
					+ '/creditFlow.financingAgency.manageMoney/multiDelPlMmOrderChildrenOr.do',
			ids : id,
			grid : this.gridPanel
		});
	},
	// 把选中ID删除
	removeSelRs : function() {
		$delGridRs({
			url : __ctxPath
					+ '/creditFlow.financingAgency.manageMoney/multiDelPlMmOrderChildrenOr.do',
			grid : this.gridPanel,
			idName : 'matchId'
		});
	},
	// 编辑Rs
	editRs : function(record) {
		new PlMmOrderChildrenOrForm({
					matchId : record.data.matchId
				}).show();
	},
	matchForecast : function() {
				var s = this.gridPanel.getSelectionModel().getSelections();
				   new PlMmOrderChildrenorTestView({
						orderId : s[0].data.orderId,
						investPersonName : s[0].data.investPersonName,
						mmName : s[0].data.mmName,
						buyMoney : s[0].data.buyMoney,
						orderlimit : s[0].data.orderlimit,
						promisIncomeSum : s[0].data.promisIncomeSum,
						currentGetedMoney : s[0].data.currentGetedMoney

					}).show();
	},
	matchinglist : function() {
		var s = this.gridPanel.getSelectionModel().getSelections();
		if (s <= 0) {
			Ext.ux.Toast.msg('操作信息', '请选中任何一条记录');
			return false;
		} else if (s.length > 1) {
			Ext.ux.Toast.msg('操作信息', '只能选中一条记录');
			return false;
		} else {

			new matchingDetail({
						orderId : s[0].data.orderId,
						investPersonName : s[0].data.investPersonName,
						mmName : s[0].data.mmName,
						buyMoney : s[0].data.buyMoney,
						orderlimit : s[0].data.orderlimit,
						promisIncomeSum : s[0].data.promisIncomeSum,
						currentGetedMoney : s[0].data.currentGetedMoney

					}).show();
		}
	},
	assignLixi : function() {
		var s = this.gridPanel.getSelectionModel().getSelections();
		if (s <= 0) {
			Ext.ux.Toast.msg('操作信息', '请选中任何一条记录');
			return false;
		} else if (s.length > 1) {
			Ext.ux.Toast.msg('操作信息', '只能选中一条记录');
			return false;
		} else {

			new PlMmOrderAssignInterestView({
						orderId : s[0].data.orderId,
						investPersonName : s[0].data.investPersonName,
						mmName : s[0].data.mmName,
						buyMoney : s[0].data.buyMoney,
						orderlimit : s[0].data.orderlimit,
						promisIncomeSum : s[0].data.promisIncomeSum,
						currentGetedMoney : s[0].data.currentGetedMoney

					}).show();
		}
	},
	// 行的Action
	onRowAction : function(grid, record, action, row, col) {
		switch (action) {
			case 'btn-del' :
				this.removeRs.call(this, record.data.matchId);
				break;
			case 'btn-edit' :
				this.editRs.call(this, record);
				break;
			default :
				break;
		}
	}
});
