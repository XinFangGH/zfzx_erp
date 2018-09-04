/**
 * @author
 * @class BpCustMemberBorrow
 * @extends Ext.Panel
 * @description [PlBidInfo]管理
 * @company 智维软件
 * @createtime:
 */
BpCustMemberBorrow = Ext.extend(Ext.Panel, {
	// 构造函数
	constructor : function(_cfg) {
		
		Ext.applyIf(this, _cfg);
		// 初始化组件
		this.initUIComponents();
		// 调用父类构造
		this.title="理财产品购买查询"
		if(this.userType==1&&this.Type==1){this.title="提前赎回处理"}
		if(this.userType==0){this.title="会员借款查询"}
		
		BpCustMemberBorrow.superclass.constructor.call(this, {
					id : 'BpCustMemberBorrow_'+this.userType+this.Type,
					title : this.title,
					iconCls : this.userType==0?"menu-finance":"menu-finance",
					region : 'center',
					layout : 'border',
					items : [this.searchPanel, this.gridPanel]
				});
	},// end of constructor
	// 初始化组件
	initUIComponents : function() {
		// 初始化搜索条件Panel

		this.searchPanel = new HT.SearchPanel({
			layout : 'column',
			region : 'north',
			border : false,
			height : 50,
			anchor : '96%',
			layoutConfig: {
               align:'middle'
            },
             bodyStyle : 'padding:10px 10px 10px 10px',
            items : [{
		     		columnWidth :.2,
					layout : 'form',
					labelWidth : 80,
					labelAlign : 'right',
					border : false,
					items : [{
						fieldLabel : '借款人',
						name : 'userName',
						anchor : "100%",
						xtype : 'textfield'
					}]
		     	},{
		     		columnWidth :.25,
					layout : 'form',
					labelWidth : 80,
					labelAlign : 'right',
					border : false,
					items : [{
						fieldLabel : '招标项目名称',
						name : 'bidName',
						anchor : "100%",
						xtype : 'textfield'
					}]
		     	},{
		     		columnWidth :.25,
					layout : 'form',
					labelWidth : 80,
					labelAlign : 'right',
					border : false,
					items : [{
						fieldLabel : '招标项目编号',
						name : 'bidProNumber',
						anchor : "100%",
						xtype : 'textfield'
					}]
		     	},{
	     			columnWidth :.15,
					layout : 'form',
					border : false,
					labelWidth :50,
					items :[{
						text : '查询',
						xtype : 'button',
						scope : this,
						style :'margin-left:30px',
						anchor : "90%",
						iconCls : 'btn-search',
						handler : this.search
					}]
	     		},{
	     			columnWidth :.15,
					layout : 'form',
					border : false,
					labelWidth :50,
					items :[{
						text : '重置',
						style :'margin-left:30px',
						xtype : 'button',
						scope : this,
						//width : 40,
						anchor : "90%",
						iconCls : 'btn-reset',
						handler : this.reset
					}]
	     		}]
		});
		if (this.userType == 0) {
			this.topbar = new Ext.Toolbar({
				items : [{
						iconCls : 'btn-xls',
						text : '导出到Excel',
						xtype : 'button',
						scope : this,
						handler : function() {
							var userName=this.getCmpByName('userName').getValue();
							var bidName=this.getCmpByName('bidName').getValue();
							var bidProNumber=this.getCmpByName('bidProNumber').getRawValue();
						
							window.open(__ctxPath + "/creditFlow/financingAgency/borrowPlbidPlanToexcelPlBidPlan.do?userName="+userName+"" +
									"&bidName="+bidName+"&bidProNumber="+bidProNumber+"",'_blank');
			
					
						}
					},{
								iconCls : 'btn-detail',
								text : '查看投标详情',
								xtype : 'button',
								scope : this,
								handler : this.seeRs
							},{
								iconCls : 'btn-detail',
								text : '查看招标网页',
								xtype : 'button',
								scope : this,
								handler : this.preview
							},{
								iconCls : 'btn-detail',
								text : '查看还款计划表',
								xtype : 'button',
								scope : this,
								handler : this.seeCusterFundIntent
							},{
								iconCls : 'btn-xls',
								text : '导出项目投资列表',
								xtype : 'button',
								scope : this,
								handler : this.toExcel
							}]
			});
		} else if(this.userType == 1){
			this.topbar = new Ext.Toolbar({
				items : [{
					iconCls : 'btn-detail',
					text : '查看收益',
					xtype : 'button',
					scope : this,
					handler : this.assignLixi
				},{
					iconCls : 'btn-tree-team15',
					text : '提前赎回',
					hidden:this.Type==1?false:true,
					xtype : 'button',
					scope : this,
					handler : this.earlyRedemption
				},{
					iconCls : 'btn-detail',
					text : '查看详细信息',
					xtype : 'button',
					scope : this,
					handler : this.seeInfo
				},{
					iconCls : 'btn-tree-team22',
					text : '打印对账单',
					xtype : 'button',
					scope : this,
					handler : this.print
				}]
			});
		}
       var summary = new Ext.ux.grid.GridSummary();
				function totalMoney(v, params, data) {
					return '总计';
				}
		this.gridPanel = new HT.GridPanel({
			tbar : this.topbar,
			plugins : [summary],
			region : 'center',
//			id : 'PlBidInfoGrid',
			url : __ctxPath + "/creditFlow/financingAgency/borrowPlbidPlanPlBidPlan.do",
			fields : [{
						name : 'bidId',
						type : 'int'
					}, {
						name : 'proName',
						mapping : 'bpPersionDirPro.proName'
					}, {
						name : 'biddingTypeId',
						mapping : 'plBiddingType.name'
					}, 'loginName','proId', 'bidProName', 'bidProNumber',
					'bidMoney', 'bidMoneyScale', 'startMoney', 'riseMoney',
					'createtime', 'updatetime', 'state', 'startInterestType',
					'bidStartTime','prepareSellTime', 'publishSingeTime', 'bidEndTime','projId','moneyPlanId','oppositeType','proType',
					'bidRemark','isStart','bidSchedule','startIntentDate','interestPeriod','yearInterestRate'],
			columns : [{
						header : 'bidId',
						dataIndex : 'bidId',
						hidden : true
					},{
						header : 'projId',
						dataIndex : 'projId',
						hidden : true
					},{
						header : 'moneyPlanId',
						dataIndex : 'moneyPlanId',
						hidden : true
					},{
						header : 'oppositeType',
						dataIndex : 'oppositeType',
						hidden : true
					},{
						header : '借款人',
						align:'center',
						dataIndex : 'loginName'
					},{
						header : '投资名称',
						dataIndex : 'bidProName'
					},{
						header : '项目编号',
						align:'center',
						summaryRenderer : totalMoney,
						dataIndex : 'bidProNumber'
					},{
						header : '起息模式',
                        align:'center',
						dataIndex : 'biddingTypeId'
					},{
						header : '招标金额',
						dataIndex : 'bidMoney',
						summaryType : 'sum',
						align : 'right',
						renderer : function(v){
							return Ext.util.Format.number(v,',000,000,000.00')+'元'
						}
					},{
						header : '年利率',
						dataIndex : 'yearInterestRate',
						align : 'right',
						renderer : function(v){
							return v+'%'
						}
					},{
						header : '招标截至时间',
						align:'center',
						dataIndex : 'bidEndTime',
						renderer : function(value){
							var date2 = new Date().format("Y-m-d h:i:s");
							if(value<date2){
								return "<font style=\"color:red;\">"+value+"</font>";
							}else{
								return value;
							}
						}
					},{
						header:'招标状态',
						align:'center',
						dataIndex:'state',
						renderer:function(value){
							if(null==value){
							
							}else if(value==0){
								return "待发标";
							}else if(value==1){
								return "招标中";
							}else if(value==2){
								return "已齐标";
							}else if(value==3){
								return "已流标";
							}else if(value==4){
								return "已过期";
							}else if(value==5){
								return "起息办理中";
							}else if(value==6){
								return "起息办理中";
							}else if(value==7){
								return "还款中";
							}else if(value==10){
								return "已完成";
							}else if(value==-1){
								return "已关闭";
							}
						}
					},{
						header : '起息日',
						align:'right',
						dataIndex : 'startIntentDate'
					},{
						header : '项目期限(月)',
						align:'right',
						dataIndex : 'interestPeriod'
					}]
				// end of columns
			});

		//this.gridPanel.addListener('rowdblclick', this.rowClick);

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
					new PlBidInfoForm({
								id : rec.data.id
							}).show();
				});
	},
	// 创建记录
	createRs : function() {
		new PlBidInfoForm().show();
	},
	// 按ID删除记录
	removeRs : function(id) {
		$postDel({
					url : __ctxPath
							+ '/creditFlow.financingAgency/multiDelPlBidInfo.do',
					ids : id,
					grid : this.gridPanel
				});
	},
	// 把选中ID删除
	removeSelRs : function() {
		$delGridRs({
					url : __ctxPath
							+ '/creditFlow.financingAgency/multiDelPlBidInfo.do',
					grid : this.gridPanel,
					idName : 'id'
				});
	},
	// 编辑Rs
	editRs : function(record) {
		new PlBidInfoForm({
					id : record.data.id
				}).show();
	},
	// 行的Action
	onRowAction : function(grid, record, action, row, col) {
		switch (action) {
			case 'btn-del' :
				this.removeRs.call(this, record.data.id);
				break;
			case 'btn-edit' :
				this.editRs.call(this, record);
				break;
			default :
				break;
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
			var record =s[0];
			if(record.data.proType=="mmproduce"||record.data.proType=="mmplan"){
				new PlMmOrderAssignInterestView({
						orderId : s[0].data.infoId,
						investPersonName : s[0].data.userName,
						mmName : s[0].data.bidName,
						buyMoney : s[0].data.userMoney,
						orderlimit : s[0].data.periodTime*30,
						promisIncomeSum : null,
						currentGetedMoney :null

					}).show();
			}else{
				new PerInvestFundIntentWindow({
						investpersonId : s[0].data.userId,
						planId:s[0].data.planId,
						investPersonName : s[0].data.userName,
						mmName : s[0].data.bidName,
						buyMoney : s[0].data.userMoney,
						orderlimit : s[0].data.periodTime*30,
						promisIncomeSum : null,
						currentGetedMoney :null,
						isAssignInterRest:true,
						isPrint:true,
						isOwnBpFundProject:false
				}).show()
			}
		}
	},
	seeprofit:function(){
		var s = this.gridPanel.getSelectionModel().getSelections();
		if (s <= 0) {
			Ext.ux.Toast.msg('操作信息', '请选中任何一条记录');
			return false;
		} else if (s.length > 1) {
			Ext.ux.Toast.msg('操作信息', '只能选中一条记录');
			return false;
		} else {
			var record =s[0];
			if(record.data.proType=="mmproduce"||record.data.proType=="mmplan"){
				new PlMmOrderAssignInterestView({
						orderId : s[0].data.infoId,
						investPersonName : s[0].data.userName,
						mmName : s[0].data.bidName,
						buyMoney : s[0].data.userMoney,
						orderlimit : s[0].data.periodTime*30,
						promisIncomeSum : null,
						currentGetedMoney :null

					}).show();
			}else{
				new PerInvestFundIntentWindow({
						investpersonId : s[0].data.userId,
						planId:s[0].data.planId,
						investPersonName : s[0].data.userName,
						mmName : s[0].data.bidName,
						buyMoney : s[0].data.userMoney,
						orderlimit : s[0].data.periodTime*30,
						promisIncomeSum : null,
						currentGetedMoney :null,
						isAssignInterRest:true,
						isPrint:true,
						isOwnBpFundProject:false
				}).show()
			}
			
		}
	},
	earlyRedemption : function(){
		var s = this.gridPanel.getSelectionModel().getSelections();
		if (s <= 0) {
			Ext.ux.Toast.msg('操作信息', '请选中任何一条记录');
			return false;
		} else if (s.length > 1) {
			Ext.ux.Toast.msg('操作信息', '只能选中一条记录');
			return false;
		} else {
			var record =s[0];
			var isOtherFlow =record.data.isOtherFlow;
			if(isOtherFlow==1){
				Ext.ux.Toast.msg('操作信息', '正在进行提前赎回流程，不能启动新的提前赎回流程！');
				return false;
			}else{
				var gridPanel=this.gridPanel
				Ext.Ajax.request( {
					url : __ctxPath +"/creditFlow/financingAgency/startEarlyRedemptionProcessPlEarlyRedemption.do",
					params : {orderId : record.data.infoId},
					method : 'POST',
					success : function(response) {
						gridPanel.getStore().reload()
						var obj=Ext.util.JSON.decode(response.responseText)
						var contentPanel = App.getContentPanel();
						if(obj.taskId==1){
							Ext.ux.Toast.msg('操作信息','您不是提前赎回流程中任务<提前赎回申请>的处理人!');
							return;
						}else{
							var formView = contentPanel.getItem('ProcessNextForm' + obj.taskId);
							if (formView == null) {
								formView = new ProcessNextForm({
									taskId : obj.taskId,
									activityName : obj.activityName,
									projectName : obj.projectName,
									agentTask : true
								});
								contentPanel.add(formView);
							}
							contentPanel.activate(formView);
						}
						gridPanel.getStore().reload()
					},
				   failure : function(fp, action) {
							Ext.MessageBox.show({
								title : '操作信息',
								msg : '信息保存出错，请联系管理员！',
								buttons : Ext.MessageBox.OK,
								icon : 'ext-mb-error'
							});
					}
				});
			}
		}
	},
	seeInfo : function(){
		var s = this.gridPanel.getSelectionModel().getSelections();
		if (s <= 0) {
			Ext.ux.Toast.msg('操作信息', '请选中任何一条记录');
			return false;
		} else if (s.length > 1) {
			Ext.ux.Toast.msg('操作信息', '只能选中一条记录');
			return false;
		} else {
			var record =s[0];
			var contentPanel = App.getContentPanel();
			if(record.data.proType=="mmproduce"||record.data.proType=="mmplan"){
					var seeManageMoneyInfo = new SeeManageMoneyInfo({
						orderId : record.data.infoId,
						userName: record.data.userName,
						bidName : record.data.bidName
					})
					contentPanel.add(seeManageMoneyInfo);
				 contentPanel.add(seeManageMoneyInfo);
				contentPanel.activate('SeeManageMoneyInfo_'+record.data.infoId);
			}
				
		}
	},
	print : function(){
		var s = this.gridPanel.getSelectionModel().getSelections();
		if (s <= 0) {
			Ext.ux.Toast.msg('操作信息', '请选中任何一条记录');
			return false;
		} else if (s.length > 1) {
			Ext.ux.Toast.msg('操作信息', '只能选中一条记录');
			return false;
		} else {
			var record =s[0];
			window.open (__ctxPath +"/creditFlow/financingAgency/printOrdersPlManageMoneyPlanBuyinfo.do?orderId="+record.data.infoId ,"_blank" ) ;
				
		}
	},
	seeRs : function(){
		var s = this.gridPanel.getSelectionModel().getSelections();
		if (s <= 0) {
			Ext.ux.Toast.msg('操作信息','请选中任何一条记录');
			return false;
		}else if(s.length>1){
			Ext.ux.Toast.msg('操作信息','只能选中一条记录');
			return false;
		}else{	
			record=s[0]
		
			Ext.Ajax.request({
			url : __ctxPath + "/fund/getInfoBpFundProject.do?bidPlanId="+record.data.bidId,
			method : 'POST',
			scope:this,
			success : function(response, request) {
					obj = Ext.util.JSON.decode(response.responseText);
					var contentPanel = Ext.getCmp('centerTabPanel');
					var  bidPlanInfoForm=new PlBidPlanInfoForm({
						bidPlanId : record.data.bidId,
						projectId:record.data.projId,
						fundProjectId:record.data.moneyPlanId,
						proType:record.data.proType,
						bidProName:record.data.bidProName,
						oppositeType:record.data.oppositeType,
						businessType:'SmallLoan'
					})
					
					 contentPanel.add(bidPlanInfoForm);
					contentPanel.activate('PlBidPlanInfoForm_'+record.data.bidId);
				},
				failure : function(response,request) {
					Ext.ux.Toast.msg('操作信息', '查询任务完成时限操作失败!');
				}
			});
			
		}	
	},
		// 预览
	preview : function() {
		var selectRs = this.gridPanel.getSelectionModel().getSelections();
		if (selectRs.length == 0) {
			Ext.ux.Toast.msg('操作信息', '请选择记录!');
			return;
		} else if (selectRs.length > 1) {
			Ext.ux.Toast.msg('操作信息', '只能选择一条记录!');
			return;
		} else {
			var record = selectRs[0];
			window.open(__p2pPath + "/creditFlow/financingAgency/bidPlanDetailPlBidPlan.do?bidId="+record.data.bidId,'_blank');
		}

	},
		seeCusterFundIntent: function(){
		var s = this.gridPanel.getSelectionModel().getSelections();
		if (s <= 0) {
			Ext.ux.Toast.msg('操作信息','请选中任何一条记录');
			return false;
		}else if(s.length>1){
			Ext.ux.Toast.msg('操作信息','只能选中一条记录');
			return false;
		}else{	
			record=s[0]
			Ext.Ajax.request({
			url : __ctxPath + "/fund/getInfoBpFundProject.do?bidPlanId="+record.data.bidId,
			method : 'POST',
			scope:this,
			success : function(response, request) {
					obj = Ext.util.JSON.decode(response.responseText);
					var contentPanel = Ext.getCmp('centerTabPanel');
					var SlFundIntentViewVM = new CusterFundIntentView({
						projectId :record.data.projId,
						bidPlanId:record.data.bidId,
						object : this.projectInfoFinance,
						isFactHidden:false,
						bidPlanFinanceInfo:this.BidPlanFinanceInfo,
						businessType : 'SmallLoan',
						isHiddenTitle : true,
						isHiddenautocreateBtn:true,
						isHiddenExcel:true,
						isHaveLending:'yes',
						isHiddenAddBtn : true,// 生成
						isHiddenDelBtn : true,// 删除
						isHiddenCanBtn : true,// 取消
						isHiddenResBtn : true,// 还原
						isHiddenResBtn1 : true,// 手动对账
						isHiddenseeqlideBtn : true,// 查看流水单项订单
						isHiddenseesumqlideBtn : true,
						isHidden1:true
					});
					var Win = new Ext.Window({
						title : '还款计划',
						width : 1000,
						height : 400,
						buttonAlign :'center',
						maximizable : true,
						autoScroll : true,
						modal : true,
						items :[SlFundIntentViewVM]
					});
					Win.show();
				},
				failure : function(response,request) {
					Ext.ux.Toast.msg('操作信息', '查询任务完成时限操作失败!');
				}
			});
			
		}	
	},
	toExcel : function(){
		
		var s = this.gridPanel.getSelectionModel().getSelections();
		if (s <= 0) {
			Ext.ux.Toast.msg('操作信息','请选中任何一条记录');
			return false;
		}else if(s.length>1){
			Ext.ux.Toast.msg('操作信息','只能选中一条记录');
			return false;
		}else{	
			record=s[0]
			var bidPlanId  =  record.data.bidId;
			window.open(__ctxPath+ '/customer/toExcelInvestPersonInfo.do?Q_bidPlanId_L_EQ='+bidPlanId,'_blank');
		}	
	}
});
