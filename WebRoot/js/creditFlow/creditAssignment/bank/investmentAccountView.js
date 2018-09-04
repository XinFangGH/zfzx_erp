// investmentAccountView
/**
 * @author liny
 * @extends Ext.Panel
 * @description 投资人系统账户
 * @company 智维软件
 * @createtime:
 */
investmentAccountView = Ext.extend(Ext.Panel, {

	// 构造函数
	constructor : function(_cfg) {
		
		Ext.applyIf(this, _cfg);
		if (this.accountType == "accountSee") {
			if(this.type==0){
				this.titlePrefix = "线上资金账户查询";
			}else if(this.type==1){
				this.titlePrefix = "线下资金账户查询";
			}
			
		}else if (this.accountType == "account") {
			this.titlePrefix = "线下资金账户管理";
		}else if (this.accountType == "accountInfo") {
			this.titlePrefix = "投资人账户收支查询";
		} else if (this.accountType == "obligation") {
			this.titlePrefix = "投资人债权查询";
		}
		else{this.titlePrefix = "资金账户管理";}
		
		// 初始化组件
		this.initUIComponents();

		// 调用父类构造
		investmentAccountView.superclass.constructor.call(this, {
					id : 'investmentAccountView_' + this.accountType+"_"+this.type,
					title : this.titlePrefix,
					region : 'center',
					layout : 'border',
					iconCls:"menu-finance",
					items : [this.searchPanel, this.gridPanel]
				});
	},// end of constructor
	// 初始化组件
	initUIComponents : function() {
		var anchor = '100%';
		var startDate = {};
		var endDate = {};

		this.searchPanel = new Ext.FormPanel({
					autoWhith : true,
					layout : 'column',
					region : 'north',
					border : false,
					height : 50,
					anchor : '100%',
					layoutConfig : {
						align : 'middle'
					},
					bodyStyle : 'padding:15px 0px 0px 0px',
					items : [/*
								 * { name : 'projectStatus', xtype : 'hidden',
								 * value : this.projectStatus }, { name :
								 * 'isGrantedShowAllProjects', xtype : 'hidden',
								 * value : this.isGrantedShowAllProjects },
								 */{
								columnWidth : .25,
								layout : 'form',
								labelWidth : 80,
								labelAlign : 'right',
								border : false,
								items : [{
											fieldLabel : '投资人',
											name : 'investPersonName',
											anchor : "100%",
											xtype : 'textfield'
										}]
							}, {
								columnWidth : .125,
								layout : 'form',
								border : false,
								labelWidth : 60,
								items : [{
											text : '查询',
											xtype : 'button',
											scope : this,
											style : 'margin-left:30px',
											anchor : "90%",
											iconCls : 'btn-search',
											handler : this.search
										}]
							}, {
								columnWidth : .125,
								layout : 'form',
								border : false,
								labelWidth : 60,
								items : [{
											text : '重置',
											style : 'margin-left:30px',
											xtype : 'button',
											scope : this,
											anchor : "90%",
											iconCls : 'btn-reset',
											handler : this.reset
										}]
							}]
				});
		if (this.accountType == "account") {
			this.topbar = new Ext.Toolbar({
				items : [/*{//天储在用方法
							iconCls : 'btn-detail',
							text : '申请充值',
							xtype : 'button',
							scope : this,
							hidden : isGranted('_apply_recharge')
									? false
									: true,
							// hidden : isGranted('_seePro_p' +
							// this.projectStatus)? false: true,
							handler :  function(){
								this.applyRecharge(1);
							} 
						}, new Ext.Toolbar.Separator({
							hidden : isGranted('_apply_recharge')? false:true
						}),{
							iconCls : 'btn-edit',
							text : '申请取现',
							xtype : 'button',
							hidden : isGranted('_apply_getMoney')? false: true,
							scope : this,
							handler : function() {
										var s = this.gridPanel.getSelectionModel().getSelections();
										if (s <= 0) {
											Ext.ux.Toast.msg('操作信息', '请选中任何一条记录');
											return false;
										} else if (s.length > 1) {
											Ext.ux.Toast.msg('操作信息', '只能选中一条记录');
											return false;
										} else {
											var record = s[0];
											new getMoney({
												accountId:record.data.id,
												investPersonId:record.data.investmentPersonId,
												investPersonName : record.data.investPersonName,
												accountNumber:record.data.accountNumber,
												gridPanel:this.gridPanel,
												companyId :record.data.companyId,
												name:record.data.accountName
											}).show()
										}
							}
							
						},*/{
							iconCls : 'btn-addmoney',
							text : '充值',
							xtype : 'button',
							scope : this,
							hidden : isGranted('_apply_recharge21')
									? false
									: true,
							// hidden : isGranted('_seePro_p' +
							// this.projectStatus)? false: true,
							handler :  function(){
								this.applyRecharge(2);
							} 
						}, new Ext.Toolbar.Separator({
							 hidden : isGranted('_apply_recharge21')
											? false
											: true
					}), {
							iconCls : 'btn-edit1',
							text : '取现',
							xtype : 'button',
							hidden : isGranted('_apply_getMoney21')? false: true,
							scope : this,
							handler : function() {
										var s = this.gridPanel.getSelectionModel().getSelections();
										if (s <= 0) {
											Ext.ux.Toast.msg('操作信息', '请选中任何一条记录');
											return false;
										} else if (s.length > 1) {
											Ext.ux.Toast.msg('操作信息', '只能选中一条记录');
											return false;
										} else {
											var record = s[0];
											if(eval(record.data.accountStatus)==eval(1)){
												Ext.ux.Toast.msg('操作信息', '目前正在进行取现审批，尚不能提交新的申请');
												return false;
											}else{
												var  investPersonType =record.data.investPersionType;
												if(eval(investPersonType)==eval(0)){
													Ext.ux.Toast.msg('操作信息', '这里只能为线下客户进行取现操作!');
												    return;
												}else{
													new getMoney({
																	investPersonId:record.data.investmentPersonId,
																	rechargeLevel:2,
																	flashTargat:this.gridPanel
																}).show()
												}
																			
											}
											
										}
							}
							
						}]
			});
		} else if (this.accountType == "accountSee" && this.type!=1){
			this.topbar = new Ext.Toolbar({
				items : [{
							iconCls : 'btn-anotherdetail',
							text : '第三方支付账户信息',
							xtype : 'button',
							scope : this,
							handler : function() {
								var s = this.gridPanel.getSelectionModel()
										.getSelections();
								if (s <= 0) {
									Ext.ux.Toast.msg('操作信息', '请选中任何一条记录');
									return false;
								} else if (s.length > 1) {
									Ext.ux.Toast.msg('操作信息', '只能选中一条记录');
									return false;
								} else {
									var record = s[0];
									var window_see = new ThirdPayAccountInfoQuery({
										accountId:record.data.id
									});
									window_see.show();
								}
							}
						},{
							iconCls : 'btn-addmoney',
							text : '平台转账',
							xtype : 'button',
							scope : this,
							handler : this.Transfer
						},{
							iconCls : 'btn-anotherdetail',
							text : '平台资金查询',
							xtype : 'button',
							scope : this,
							handler : function() {
								var window_see = new ThirdPayAccountInfoQuery({
									accountId:-1
								});
								window_see.show();
							}
						}]
			});
		}else if (this.accountType == "accountInfo") {
			this.topbar = new Ext.Toolbar({
				items : [/*{
							iconCls : 'btn-detail',
							text : '账户收支查询',
							xtype : 'button',
							scope : this,
							hidden : isGranted('_see_accountInOut')
									? false
									: true,
							handler : function() {
								// detailPro(this.gridPanel,
								// 'SmallLoanProjectInfo_')
							}
						}, new Ext.Toolbar.Separator({
									hidden : isGranted('_see_accountInOut')
											? false
											: true
								}),*/ {
							iconCls : 'btn-edit',
							text : '账户明细查询',
							xtype : 'button',
							hidden : isGranted('_see_accountDetail')
									? false
									: true,
							scope : this,
							handler : function() {
								var s = this.gridPanel.getSelectionModel()
										.getSelections();
								if (s <= 0) {
									Ext.ux.Toast.msg('操作信息', '请选中任何一条记录');
									return false;
								} else if (s.length > 1) {
									Ext.ux.Toast.msg('操作信息', '只能选中一条记录');
									return false;
								} else {
									var record = s[0];
									var window_see = new Ext.Window({
										title : '账户明细查询',
										layout : 'fit',
										width : (screen.width ) * 0.7,
										maximizable : true,
										height : 560,
										closable : true,
										modal : true,
										plain : true,
										border : false,
										items : [new accountListQuery({
											accountId : record.data.id
										})],
										listeners : {
											'beforeclose' : function(panel) {
												window_see.destroy();
											}
										}
									});
									window_see.show();
								}
							}
						}]
			});
		} else if (this.accountType == "obligation") {
			this.topbar = new Ext.Toolbar({
				items : [{
					iconCls : 'btn-detail',
					text : '债权详情',
					xtype : 'button',
					scope : this,
					hidden : isGranted('_see_obligation_0') ? false : true,
					handler : function() {
						var s = this.gridPanel.getSelectionModel()
								.getSelections();
						if (s <= 0) {
							Ext.ux.Toast.msg('操作信息', '请选中任何一条记录');
							return false;
						} else if (s.length > 1) {
							Ext.ux.Toast.msg('操作信息', '只能选中一条记录');
							return false;
						} else {
									var window_see = new Ext.Window({
												
												title : '债权详情',
												layout : 'fit',
												width : (screen.width - 180)
														* 0.7 + 160,
												maximizable : true,
												autoScroll : true,
												height : 500,
												closable : true,
												modal : true,
												plain : true,
												border : false,
												items : [new seeObligation({
													investId : s[0].data.investmentPersonId,
													record:s[0]
														})],
												listeners : {
													'beforeclose' : function(
															panel) {
														window_see.destroy();
													}
												}
											});
									window_see.show();
						}
					}
				}]
			});
		}
		var summary = new Ext.ux.grid.GridSummary();
		function totalMoney(v, params, data) {
					return '总计(元)';
		}
		
		var isShow = false;
		var rightValue =  isGranted('_investmentAccountView_'+this.accountType+'_see_All');
		var isShop = isGranted('_investmentAccountView_'+this.accountType+'_see_shop');
		if (RoleType == "control") {
			isShow = true;
		}
		var url=""
		if(this.type==1){//线下客户
			url = __ctxPath+ "/creditFlow/creditAssignment/bank/systemAccountDownListObSystemAccount.do?isAll="+rightValue+"&isShop="+isShop
		}else if(this.type==0){//线上客户
			url = __ctxPath+ "/creditFlow/creditAssignment/bank/systemAccountListObSystemAccount.do?accountType="+this.type
		}
		this.gridPanel = new HT.GridPanel({
					name : 'investmentAccountGrid',
					region : 'center',
					tbar : this.topbar,
					notmask : true,
					plugins : [summary],
					// 不适用RowActions
					rowActions : false,
					url : url,
					fields : [{
								name : 'id',
								type : 'int'
							}, 'accountName', 'accountNumber',
							'investmentPersonId', 'investPersonName',
							'totalMoney', 'accountStatus', 'totalInvestMoney','truename','loginname',
							'currentInvestMoney', 'availableInvestMoney','freezeMoney',
							'unPrincipalRepayment','allInterest','companyId','investPersionType'],

					columns : [{
								header : 'id',
								dataIndex : 'id',
								hidden : true
							}, {
								header : 'investmentPersonId',
								dataIndex : 'investmentPersonId',
								hidden : true
							}, {
								header : '投资客户类型',
								width : 80,
								align:'center',
								dataIndex : 'investPersionType',
								renderer : function(value) {
									if (value ==1) {
										return "线下";
									} else if(value ==0){
										return "线上";
									}
								}
								
							}, {
								header : '投资人姓名',
								width : 100,
								align:'center',
								summaryType : 'count',
								summaryRenderer : totalMoney,
								dataIndex : 'truename'
							}, {
								header : '投资人账号',
								width : 110,
								align:'left',
								dataIndex : 'loginname'
							}/*, {
								header : '账户余额(元)',
								align : 'right',
								width : 110,
								sortable : true,
								dataIndex : 'totalMoney',
								summaryType : 'sum',
								renderer : function(value) {
									if (value == "") {
										return "0.00";
									} else {
										return Ext.util.Format.number(value,
												',000,000,000.00');
									}
								}
							},{
								header : '可投资额(元)',
								align : 'right',
								width : 110,
								sortable : true,
								dataIndex : 'availableInvestMoney',
								summaryType : 'sum',
								renderer : function(value) {
									if (value == "") {
										return "0.00";
									} else {
										return Ext.util.Format.number(value,
												',000,000,000.00');
									}
								}
							}*/, {
								header : '预冻结金额(元)',
								align : 'right',
								width : 110,
								summaryType : 'sum',
								sortable : true,
								dataIndex : 'freezeMoney',
								renderer : function(value) {
									if (value == "") {
										return "0.00";
									} else {
										return Ext.util.Format.number(value,
												',000,000,000.00');
									}
								}
							}, {
								header : '累计投资额(元)',
								width : 130,
								align : 'right',
								dataIndex : 'totalInvestMoney',
								summaryType : 'sum',
								renderer : function(value) {
									if (value == "") {
										return "0.00";
									} else {
										return Ext.util.Format.number(value,
												',000,000,000.00');
									}
								}
							},{
								header : '累计收益(元)',
								width : 110,
								align : 'right',
								dataIndex : 'allInterest',
								summaryType : 'sum',
								renderer : function(value) {
									if (value == "") {
										return "0.00";
									} else {
										return Ext.util.Format.number(value,
												',000,000,000.00');
									}
								}
							}, {
								header : '累计收回本金(元)',
								width : 100,
								align : 'right',
								dataIndex : 'unPrincipalRepayment',
								summaryType : 'sum',
								renderer : function(value) {
									if (value == "") {
										return "0.00";
									} else {
										return Ext.util.Format.number(value,
												',000,000,000.00');
									}
								}
							}/*, {
								header : '待回本金',
								width : 70,
								hidden:true,
								dataIndex : 'unPrincipalRepayment',
								renderer : function(value) {
									if (value == "") {
										return "0.00元";
									} else {
										return Ext.util.Format.number(value,
												',000,000,000.00')
												+ "元";
									}
								}
							}, {
								header : '待回利息',
								width : 70,
								hidden:true,
								dataIndex : 'unInterest',
								renderer : function(value) {
									if (value == "") {
										return "0.00元";
									} else {
										return Ext.util.Format.number(value,
												',000,000,000.00')
												+ "元";
									}
								}
							}, {
								header : '累计收益收息',
								width : 70,
								hidden:true,
								dataIndex : 'allInterest',
								renderer : function(value) {
									if (value == "") {
										return "0.00元";
									} else {
										return Ext.util.Format.number(value,
												',000,000,000.00')
												+ "元";
									}
								}
							}, {
								header : '预期',
								width : 70,
								dataIndex : 'expectAllInterest',
								renderer : function(value) {
									if (value == "") {
										return "0.00元";
									} else {
										return Ext.util.Format.number(value,
												',000,000,000.00')
												+ "元";
									}
								}
							}, {
								header : '收益率',
								width : 70,
								hidden:true,
								dataIndex : 'personInterestRate',
								renderer : function(value) {
									if (value == "") {
										return "0.00元";
									} else {
										return Ext.util.Format.number(value,
												',000,000,000.00')
												+ "元";
									}
								}
							}*/]
				});
		this.gridPanel.addListener('afterrender', function() {
					this.loadMask1 = new Ext.LoadMask(this.gridPanel.getEl(), {
						msg : '正在加载数据中······,请稍候······',
						store : this.gridPanel.store,
						removeMask : true
							// 完成后移除
						});
					this.loadMask1.show(); // 显示

				}, this);
		/*
		 * if (!this.isGrantedShowAllProjects) {
		 * this.gridPanel.getStore().on("load", function(store) { if
		 * (store.getCount() < 1) { this.get(0).setVisible(true);
		 * this.searchPanel.setVisible(false); this.gridPanel.setVisible(false);
		 * this.doLayout(); } }, this) }
		 */
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
	// 申请充值button 方法
	applyRecharge : function(distinguish) {
				var selectRs = this.gridPanel.getSelectionModel().getSelections();
				if (selectRs.length == 0) {
					Ext.ux.Toast.msg('操作信息', '请选择记录!');
					return;
				} else if (selectRs.length > 1) {
					Ext.ux.Toast.msg('操作信息', '只能选择一条记录!');
					return;
				} else {
					var record = selectRs[0];
					var  investPersonType =record.data.investPersionType;
					if(eval(investPersonType)==eval(0)){
						Ext.ux.Toast.msg('操作信息', '这里只能为线下客户进行充值!');
					    return;
					}else{
						var window_see = new applyRechargeWindow({
										investPeronId : record.data.investmentPersonId,
										systemAccountName : record.data.accountName,
										systemAccountNumber : record.data.accountNumber,
										accountId : record.data.id,
										rechargeLevel:distinguish,
										isReadOnly : true
									});
							window_see.show();
					}
				/*	Ext.Msg.confirm('信息确认', '是否确认申请充值', function(btn) {
						if (btn == 'yes') {*/
							
							
							/*	}
					})*/
				}
		
	},// 平台给商户转账
	Transfer : function() {
		new transferAccount().show();
	}
});
