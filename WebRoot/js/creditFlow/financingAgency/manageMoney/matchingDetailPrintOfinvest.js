/**
 * @author
 * @class PlMmOrderChildrenOrView
 * @extends Ext.Panel
 * @description [PlMmOrderChildrenOr]管理
 * @company 智维软件
 * @createtime:
 */
matchingDetailPrintOfinvest = Ext.extend(Ext.Panel, {
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		// 初始化组件
		this.initUIComponents();
		// 调用父类构造
		matchingDetailPrintOfinvest.superclass.constructor.call(this, {
					id : 'matchingDetailPrintOfinvest',
					title : '投资债权清单',
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
					columnWidth : .15,
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
						blankText : "性名不能为空，请正确填写!",
						name : 'investPersonName',
						maxLength : 15,
						/*onTriggerClick : function() {
							new selectInvestPersonList({
										formPanel : Ext.getCmp("matchingDetailPrintOfinvest")
								}).show();
						},*/
						anchor : '100%'
					}]
				}/*, {

					columnWidth : .15,
					labelAlign : 'right',
					xtype : 'container',
					layout : 'form',
					labelWidth : 80,
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
						fieldLabel : '投资类型',
						anchor : '100%',
						name : 'keystr',
						listeners : {
							afterrender : function(combox) {
								var st = combox.getStore();
								combox.setValue(combox.getValue());
							},
							select : function(cbo, record, index) {
								var orderId = Ext.getCmp('orderId');
								var    investPersonId = Ext.getCmp('plManageMoneyPlanBuyinfo.investPersonId1').getValue();      
							    var keystr=cbo.getValue();
								var arrStore = new Ext.data.ArrayStore({
									url : __ctxPath
											+ "/creditFlow/financingAgency/getorderListPlManageMoneyPlanBuyinfo.do?investPersonId="+investPersonId+"&keystr="+keystr
											,
									fields : ['itemId', 'itemName'],
									autoLoad : true
								});

								orderId.store = arrStore;
								if (orderId.view) { // 刷新视图,避免视图值与实际值不相符
									orderId.view.setStore(orderId.store);
								}
								orderId.clearValue();
							}

						}
					}]

				}*/, {
					columnWidth : .22,
					labelAlign : 'right',
					xtype : 'container',
					layout : 'form',
					labelWidth : 80,
					defaults : {
						anchor : '100%'
					},
					items : [{

						fieldLabel : "订单",
						xtype : "combo",
						displayField : 'itemName',
						valueField : 'itemId',
						triggerAction : 'all',
						store : new Ext.data.ArrayStore({
							url : __ctxPath
									+ "/creditFlow/financingAgency/getorderListPlManageMoneyPlanBuyinfo.do?keystr=mmproduce",
							fields : ['itemId', 'itemName'],
							autoLoad : false
						}),
						mode : 'remote',
						hiddenName : "orderId",
						//id : "orderId",
						editable : false,
						anchor : "96%",
						listeners : {
							scope : this,
							afterrender : function(combox) {
								var st = combox.getStore();
								st.on("load", function() {
											combox.setValue(combox.getValue());

										})
								combox.clearInvalid();
							}

						}

					}]
				}, {
					columnWidth : .19,
					labelAlign : 'right',
					xtype : 'container',
					layout : 'form',
					labelWidth : 120,
					defaults : {
						anchor : '100%'
					},
					items : [{
								fieldLabel : '查询开始日期',
								name : 'Q_matchingStartDate_D_EQ',
								flex : 1,
								xtype : 'datefield',
								value : this.endinInterestTime,
								format : 'Y-m-d'

							}]
				}, {
					columnWidth : .19,
					labelAlign : 'right',
					xtype : 'container',
					layout : 'form',
					labelWidth : 120,
					defaults : {
						anchor : '100%'
					},
					items : [{
								fieldLabel : '查询结束日期',
								name : 'Q_matchingEndDate_D_EQ',
								flex : 1,
								xtype : 'datefield',
								value : this.endinInterestTime,
								format : 'Y-m-d'

							}]
				}, {
					columnWidth : .05,
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
					columnWidth : .05,
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
								iconCls : 'btn-print',
								text : '导出',
								xtype : 'button',
								scope : this,
								handler : function(){
									var investPersonName=this.getCmpByName('investPersonName').getValue()
									var orderId=this.getCmpByName('orderId').getValue();
									var Q_matchingStartDate_D_EQ=this.getCmpByName('Q_matchingStartDate_D_EQ').getValue();
									Q_matchingStartDate_D_EQ=Ext.util.Format.date(Q_matchingStartDate_D_EQ, 'Y-m-d');
									var Q_matchingEndDate_D_EQ=this.getCmpByName('Q_matchingEndDate_D_EQ').getValue();
									Q_matchingEndDate_D_EQ=Ext.util.Format.date(Q_matchingEndDate_D_EQ, 'Y-m-d');
									window.open( __ctxPath + "/creditFlow/financingAgency/outToExcelPlMmOrderChildrenOr.do?orderId="+orderId+"&Q_matchingStartDate_D_EQ="+Q_matchingStartDate_D_EQ+"&Q_matchingEndDate_D_EQ="+Q_matchingEndDate_D_EQ+"&investPersonName="+encodeURIComponent(encodeURIComponent(investPersonName)),'_blank');
								}
							}]
				});
         var summary = new Ext.ux.grid.GridSummary();
				function totalMoney(v, params, data) {
					return '总计';
				}
		this.gridPanel = new HT.GridPanel({
			region : 'center',
			//tbar : this.topbar,
			// 使用RowActions
			id : 'matchingDetailGrid',
			//isautoLoad:false,
			hiddenCm :true,
			plugins : [summary],
			url : __ctxPath
					+ "/creditFlow/financingAgency/listPlMmOrderChildrenOr.do",
			fields : [{
						name : 'matchId',
						type : 'int'
					}, 'orderId', 'childrenorId', 'investPersonId',
					'investPersonName', 'mmplanId', 'mmName', 'parentOrBidId',
					'parentOrBidName', 'matchingMoney', 'childrenOrDayRate',
					'matchingStartDate', 'matchingEndDate', 'matchingLimit',
					'matchingEndDateType', 'matchingGetMoney', 'matchingState'],
			columns : [{
						header : 'matchId',
						dataIndex : 'matchId',
						hidden : true
					}, {
						header : '投资人名称',
						dataIndex : 'investPersonName'
					},{
						header : '债权的名称',
						summaryRenderer : totalMoney,
						dataIndex : 'parentOrBidName'
					}, {
						header : '匹配金额',
						dataIndex : 'matchingMoney',
						align : 'right',
						summaryType : 'sum',
						renderer : function(v) {
							return Ext.util.Format.number(v, ',000,000,000.00')
									+ "元";
						}
					}, {
						header : '债权的日化利率',
						align : 'right',
						renderer : function(v) {
					                 return v+"%";
				                   },
						dataIndex : 'childrenOrDayRate'
					}, {
						header : '匹配开始日',
						align : 'center',
						dataIndex : 'matchingStartDate'
					}, {
						header : '匹配结束日',
						align : 'center',
						dataIndex : 'matchingEndDate'
					}, {
						header : '匹配期限',
						align : 'center',
						summaryType : 'sum',
						renderer : function(v) {
					                 return v+"天";
				                   },
						dataIndex : 'matchingLimit',
						align : 'right'
					}, {
						header : '此匹配获得的收益',
						dataIndex : 'matchingGetMoney',
						align : 'right',
						renderer : function(v) {
							return Ext.util.Format.number(v, ',000,000,000.00')
									+ "元";
						}
					}/*
						 * , { header : 'matchingState', dataIndex :
						 * 'matchingState' }
						 */]
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
		new PlMmOrderChildrenOrForm().show();
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
