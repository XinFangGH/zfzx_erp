
plMmOrderViewOrderFlow = Ext.extend(Ext.Panel, {
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		// 初始化组件
		this.initUIComponents();
		// 调用父类构造
		plMmOrderViewOrderFlow.superclass.constructor.call(this, {
				id : 'plMmOrderViewOrderFlow' + this.keystr,
				name : "PlMmOrderAssigninterestVM",
				hidden : this.isHiddenPanel,
				region : 'center',
				layout : 'anchor',
				anchor : '100%',
				items : [ {
									columnWidth : .3,
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
												id : 'seachDateFlow' + this.keystr,
												xtype : 'datefield',
												format : "Y-m-d",
												editable : false,
												maxValue : new Date(),
												value : new Date()

											}]
						 },this.gridPanel]
		});
		
	},// end of constructor
	// 初始化组件
	initUIComponents : function() {

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
							}]
				});
		var summary = new Ext.ux.grid.GridSummary();
		function totalMoney(v, params, data) {
					return '总计(元)';
		}
		this.gridPanel = new HT.GridPanel({
			showPaging : false,
			region : 'center',
			tbar : this.topbar,
			autoHeight : true,
	//		singleSelect : true,
			// 使用RowActions
			id : 'PlMmOrderChildrenOrGridOrderFlow' + this.keystr,
	//		plugins : [summary],
			url : __ctxPath + "/creditFlow/financingAgency/listPlManageMoneyPlanBuyinfo.do?keystr=" + this.keystr +"&plManageMoneyPlanBuyinfoId="+this.projectId,
			fields : [{
						name : 'orderId',
						type : 'int'
					}, 'mmplanId', 'buyDatetime', 'investPersonId',
					'investPersonName', 'buyMoney', 'startinInterestTime',
					'endinInterestTime', 'orderlimit', 'promisDayRate',
					'promisIncomeSum', 'currentMatchingMoney',
					'currentGetedMoney', 'optimalDayRate', 'keystr', 'mmName','isAtuoMatch','firstProjectIdcount','earlierOutDate'],
			columns : [{
						header : 'matchId',
						dataIndex : 'matchId',
						hidden : true
					}, {
						header : '投资人',
						summaryType : 'count',
						summaryRenderer : totalMoney,
						dataIndex : 'investPersonName'
					}, {
						header : '理财产品名称',
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
						dataIndex : 'startinInterestTime'
					}, {
						header : '计息止日',
						dataIndex : 'endinInterestTime'
					}, {
						header : '订单期限',
						dataIndex : 'orderlimit',
						align : 'right',
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
						}
					}, {
						header : '提前退出日期',
						dataIndex : 'earlierOutDate'
					}/*, {
						header : '已匹配不同债权个数',
						dataIndex : 'firstProjectIdcount',
						align : 'right',
						renderer : function(v) {
							return v
									+ "个";
						}
					}*/, {
						header : '托管状态',
						dataIndex : 'isAtuoMatch',
						renderer : function(v) {
							var a=v==0? "否":"是";
							return a;
						}
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
		var seachDate = Ext.getCmp("seachDateFlow" + this.keystr).getValue();
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
			} else if(!Ext.isEmpty(s[0].data.earlierOutDate)){
				Ext.ux.Toast.msg('操作信息', '此订单已提前赎回，不能再匹配了');
			}else{
			 new PlMmChildrenObligatoryRightView({

							orderId : s[0].data.orderId,
							seachDate : seachDate,
							keystr : this.keystr,
							currentMatchingMoney : s[0].data.currentMatchingMoney

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
		var seachDate = Ext.getCmp("seachDateFlow" + this.keystr).getValue();
	
		var s = this.gridPanel.getSelectionModel().getSelections();
		   if (sum > 0) {
				Ext.ux.Toast.msg('操作信息', '有订单已经匹配过，不能进行初始匹配，请重新选择');
			} else {
				if(!Ext.isEmpty(s[0].data.earlierOutDate)){
				Ext.ux.Toast.msg('操作信息', '此订单已提前赎回，不能再匹配了');
			   }
				
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
					seachDate : seachDate
				}
		});
		}
		}
	},
	autoMatching : function() {
		var seachDate = Ext.getCmp("seachDateFlow" + this.keystr).getValue();
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
				seachDate : seachDate
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
