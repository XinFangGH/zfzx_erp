/**
 * @author
 * @class PlMmOrderChildrenOrView
 * @extends Ext.Panel
 * @description [PlMmOrderChildrenOr]管理
 * @company 智维软件
 * @createtime:
 */
plMmOrderView = Ext.extend(Ext.Panel, {
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		// 初始化组件
		this.initUIComponents();
		// 调用父类构造
		plMmOrderView.superclass.constructor.call(this, {
					id : 'plMmOrderView' + this.keystr,
					title : this.keystr=='mmproduce'?'债权匹配':'投资债权匹配',
					region : 'center',
					layout : 'border',
					iconCls : 'btn-tree-team30',
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
									columnWidth : .25,
									labelAlign : 'right',
									xtype : 'container',
									layout : 'form',
									labelWidth : 120,
									defaults : {
										anchor : '100%'
									},
									items : [{
												fieldLabel : this.keystr=='UPlan'? 'U计划名称':(this.keystr=='mmplan'? 'D计划名称' :'理财产品名称'),
												name : 'mmName',
												xtype : 'textfield'

											}]
								}, {
									columnWidth : .2,
									labelAlign : 'right',
									xtype : 'container',
									layout : 'form',
									labelWidth : 100,
									defaults : {
										anchor : '100%'
									},
									items : [{
												fieldLabel : '匹配日期',
												name : 'seachDate',
												id : 'seachDate' + this.keystr,
												xtype : 'datefield',
												format : "Y-m-d",
												maxValue : new Date(),
												value : new Date()

											}]
								},{
									columnWidth : .2,
									labelAlign : 'right',
									xtype : 'container',
									layout : 'form',
									labelWidth : 120,
									defaults : {
										anchor : '100%'
									},
									items : [{
												fieldLabel : '起息日期从',
												name : 'startinInterestTimeF',
												xtype : 'datefield',
												format : "Y-m-d",
												value : new Date()

											}]
								},{
									columnWidth : .2,
									labelAlign : 'right',
									xtype : 'container',
									layout : 'form',
									labelWidth : 30,
									defaults : {
										anchor : '100%'
									},
									items : [{
												fieldLabel : '到',
												name : 'startinInterestTimeT',
												xtype : 'datefield',
												format : "Y-m-d",
												value : new Date()

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
					items : [{
								iconCls : 'btn-fastini',
								text : '初始快速匹配',
								xtype : 'button',
								scope : this,
								handler : this.halfAutomatching
							}, new Ext.Toolbar.Separator({
							}),{
								iconCls : 'btn-handle',
								text : '手动匹配',
								xtype : 'button',
								scope : this,
								handler : this.createRs
							}, new Ext.Toolbar.Separator({
							}), {
								iconCls : 'btn-auto',
								text : '全部自动匹配',
								xtype : 'button',
								scope : this,
								handler : this.autoMatching
							}, new Ext.Toolbar.Separator({
							}), {
								iconCls : 'btn-state',
								text : '改变托管状态',
								xtype : 'button',
								scope : this,
								handler : this.changeIsAutoMatcing
							}, new Ext.Toolbar.Separator({
							}),/* {
								iconCls : 'btn-user-sel',
								text : '预测债权清单',
								xtype : 'button',
								scope : this,
								hidden:this.keystr=='mmproduce'?false:true,
								handler : this.matchForecast
							}, this.keystr=='mmproduce'?new Ext.Toolbar.Separator({
							}):"",*/ {
								iconCls : 'btn-user-list',
								text : '实际债权清单',
								xtype : 'button',
								scope : this,
								handler : this.matchinglist
							}, new Ext.Toolbar.Separator({
							}), {
								iconCls : 'btn-user-profit',
								text : '收益清单',
								xtype : 'button',
								scope : this,
								handler : this.assignLixi
							}/*, {
								iconCls : 'btn-print',
								text : '打印实际债权',
								xtype : 'button',
								scope : this,
								handler : this.print
							}*//*, {
								iconCls : 'mod-obligationProductmanage',
								text : '导出实际债权到word',
								xtype : 'button',
								scope : this,
								handler : this.printword
							}*/]
				});
		var summary = new Ext.ux.grid.GridSummary();
		function totalMoney(v, params, data) {
					return '总计';
		}
		this.gridPanel = new HT.GridPanel({
			region : 'center',
			tbar : this.topbar,
	//		singleSelect : true,
			// 使用RowActions
			id : 'PlMmOrderChildrenOrGrid' + this.keystr,
			plugins : [summary],
			url : __ctxPath
					+ "/creditFlow/financingAgency/listPlManageMoneyPlanBuyinfo.do?keystr="
					+ this.keystr,
			fields : [{
						name : 'orderId',
						type : 'int'
					}, 'mmplanId', 'buyDatetime', 'investPersonId',
					'investPersonName', 'buyMoney', 'startinInterestTime',
					'endinInterestTime', 'orderlimit', 'promisDayRate',
					'promisIncomeSum', 'currentMatchingMoney',
					'currentGetedMoney', 'recordNumber','optimalDayRate', 'keystr', 'mmName','isAtuoMatch','firstProjectIdcount','earlierOutDate','dealInfoNumber'],
			columns : [{
						header : 'id',
						align:'center',
						dataIndex : 'orderId',
						hidden : true
					}, {
						header : '投资人',
						align:'center',
						summaryType : 'count',
						summaryRenderer : totalMoney,
						dataIndex : 'investPersonName'
					},  {
						header : '订单编号',
						width : 130,
						dataIndex : 'dealInfoNumber',
						hidden : this.keystr=='mmproduce' ? true : false
					},{
						header : this.keystr=='UPlan'? 'U计划名称' : (this.keystr=='mmplan'? 'D计划名称' :'理财产品名称'),
						dataIndex : 'mmName',
						width : 130
					}, {
						header : '购买金额',
						dataIndex : 'buyMoney',
						summaryType : 'sum',
						align : 'right',
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
						align : 'right',
						summaryType : 'sum',
						renderer : function(v) {
							return v + "天";
						}
					}, {
						header : '承诺日化利率',
						dataIndex : 'promisDayRate',
						align : 'right',
						renderer : function(v) {
							return v
									+ "%";
						},
						hidden : true
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
						header : '可匹配金额',
						dataIndex : 'currentMatchingMoney',
						summaryType : 'sum',
						align : 'right',
						renderer : function(v) {
							return Ext.util.Format.number(v, ',000,000,000.00')
									+ "元";
						}
					}, {
						header : '当期已实收益',
						dataIndex : 'currentGetedMoney',
						summaryType : 'sum',
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
							return v
									+ "%";
						},
						hidden : true
					}, {
						header : '提前退出日期',
						align:'center',
						dataIndex : 'earlierOutDate'
					}, {
						header : '已匹配不同债权个数',
						summaryType : 'sum',
						dataIndex : 'firstProjectIdcount',
						align:'center',
						renderer : function(v) {
							return v
									+ "个";
						}
					}, {
						header : '托管状态',
						align:'center',
						dataIndex : 'isAtuoMatch',
						renderer : function(v) {
							var a=v==0? "否":"是";
							return a;
						},
						hidden : true
					}]
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
			if (s[0].data.currentMatchingMoney <= 0) {
				Ext.ux.Toast.msg('操作信息', '匹配金额已经为0，不能再匹配了');
			} else if(!Ext.isEmpty(s[0].data.earlierOutDate)){
				Ext.ux.Toast.msg('操作信息', '此订单已提前赎回，不能再匹配了');
			}else{
			 new PlMmChildrenObligatoryRightView({
							orderId : s[0].data.orderId,
							seachDate : seachDate,
							keystr : this.keystr,
							currentMatchingMoney : s[0].data.currentMatchingMoney,
							childType : this.childType

						}).show();
			}
		}
	},
	halfAutomatching:function(){
		var s = this.gridPanel.getSelectionModel().getSelections();
		if (s <= 0) {
			Ext.ux.Toast.msg('操作信息', '请选中记录');
			return false;
		} else {
		var keystr = this.keystr;
		var sum=$getGdSelectedSum(this.gridPanel,'currentGetedMoney');
		var ids=$getGdSelectedIds(this.gridPanel,'orderId');
		var seachDate = Ext.getCmp("seachDate" + this.keystr).getValue();
	
		var s = this.gridPanel.getSelectionModel().getSelections();
		for(var i=0; i<s.length; i++){
			if(!Ext.isEmpty(s[i].data.earlierOutDate)){
				Ext.ux.Toast.msg('操作信息', '选中的订单中存在已提前赎回记录，不能进行匹配');
				return false;
		    }
		}
	    if (sum > 0) {
			Ext.ux.Toast.msg('操作信息', '有订单已经匹配过，不能进行初始匹配，请重新选择');
		} else {
			var loadMask1 = new Ext.LoadMask(Ext.getBody(), {
			msg : '正在匹配中······,请稍候······',
			removeMask : true
				// 完成后移除
				});
		    loadMask1.show(); // 显示
			Ext.Ajax.request({
		    	url : __ctxPath
					+ "/creditFlow/financingAgency/halfAutomatchingPlMmOrderChildrenOr.do",
			    method : 'post',
			    success : function(response, request) {
			    	loadMask1.hide();
				Ext.getCmp("PlMmOrderChildrenOrGrid" + keystr).getStore().reload();
				Ext.ux.Toast.msg('操作信息', '初始快速匹配成功');
				},
				params : {
					ids : ids,
					seachDate : seachDate,
					childType : this.childType
				}
			});
	    }
		}
	},
	autoMatching : function() {
		var seachDate = Ext.getCmp("seachDate" + this.keystr).getValue();
		var keystr = this.keystr;
		var loadMask1 = new Ext.LoadMask(Ext.getBody(), {
				msg : '正在匹配中······,请稍候······',
				removeMask : true
					// 完成后移除
					});
			loadMask1.show(); // 显示
		Ext.Ajax.request({
			url : __ctxPath
					+ "/creditFlow/financingAgency/automatchingPlMmOrderChildrenOr.do",
			method : 'post',
			timeout: 360000,
			success : function(response, request) {
				loadMask1.hide();
				Ext.getCmp("PlMmOrderChildrenOrGrid" + keystr).getStore().reload();
				Ext.ux.Toast.msg('操作信息', '系统自动匹配成功');
			},
			params : {
				seachDate : seachDate,
				keystr : this.keystr
			}
		});
	},
	changeIsAutoMatcing:function(){
		var s = this.gridPanel.getSelectionModel().getSelections();
		if (s <= 0) {
			Ext.ux.Toast.msg('操作信息', '请选中记录');
			return false;
		} else {
			var keystr = this.keystr;
			var ids=$getGdSelectedIds(this.gridPanel,'orderId');
		    Ext.Ajax.request({
				url : __ctxPath
						+ "/creditFlow/financingAgency/changeIsAutoMatcingPlManageMoneyPlanBuyinfo.do",
				method : 'post',
				success : function(response, request) {
					Ext.getCmp("PlMmOrderChildrenOrGrid" + keystr).getStore().reload();
					Ext.ux.Toast.msg('操作信息', '改变成功');
				},
				params : {
					ids : ids
				}
			});
		}
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
	printword:function(){
	//	new PlMmOrderChildrenOrForm().show();
		var s = this.gridPanel.getSelectionModel().getSelections();
		if (s <= 0) {
			Ext.ux.Toast.msg('操作信息', '请选中记录');
			return false;
		}else{
       var  orderId=s[0].data.orderId;
       var orderIds = $getGdSelectedIds(this.gridPanel,'orderId');
					
		Ext.Ajax.request({
							waitMsg : '正在提交查询',
							url : __ctxPath + '/system/printcommReportTemplate.do?reportKey=obligatoryrightchildren&orderIds='+orderIds,
							method : 'post',
							success : function(response, opts) {
									var respText = response.responseText;
			                 	var object = Ext.util.JSON.decode(respText);
			                 	
									document.location.href = __ctxPath
											+ '/report/report.jsp?reportId='+object.reportId
											
										//	+ encodeURI(encodeURI(object.data))
											+ '&reportType=word';;
							},
							failure : function(form, action) {
								
							}
						});
		}
	},
	// 创建记录
	print : function() {
	//	new PlMmOrderChildrenOrForm().show();
		var s = this.gridPanel.getSelectionModel().getSelections();
		if (s <= 0) {
			Ext.ux.Toast.msg('操作信息', '请选中记录');
			return false;
		}  else {
        var orderIds = $getGdSelectedIds(this.gridPanel,'orderId');
		Ext.Ajax.request({
							waitMsg : '正在提交查询',
							url : __ctxPath + '/system/printcommReportTemplate.do?reportKey=obligatoryrightchildren&orderIds='+orderIds,
							method : 'post',
							success : function(response, opts) {
								var respText = response.responseText;
			                 	var object = Ext.util.JSON.decode(respText);
								window.open(__ctxPath+ '/report/swf.html','blank');
							},
							failure : function(form, action) {
								
							}
						});
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
