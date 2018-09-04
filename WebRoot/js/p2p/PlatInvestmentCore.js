// investmentAccountView
/**
 * @author liny
 * @extends Ext.Panel
 * @description 投资人系统账户
 * @company 智维软件
 * @createtime:
 */
PlatInvestmentCore = Ext.extend(Ext.Panel, {

	// 构造函数
	constructor : function(_cfg) {
		
		Ext.applyIf(this, _cfg);
		if (this.accountType == "accountSee") {
			if(this.type==0){
				this.titlePrefix = "线上资金账户查询";
			}else if(this.type==1){
				this.titlePrefix = "线下资金账户查询";
			}
			
		}
		this.titlePrefix = "结算";
		
		// 初始化组件
		this.initUIComponents();

		// 调用父类构造
		PlatInvestmentCore.superclass.constructor.call(this, {
					id : 'PlatInvestmentCore'+this.type ,
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
					height : 100,
					anchor : '100%',
					layoutConfig : {
						align : 'middle'
					},
					bodyStyle : 'padding:15px 0px 0px 0px',
					items : [{
									columnWidth : .4,
									labelAlign : 'right',
									xtype : 'container',
									layout : 'form',
									labelWidth : 80,
									border : false,
									items : [{
												fieldLabel : "投资部门名称",
												xtype : "textfield",
												name : "departName",
												anchor : "95%"
											}]
								},{
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
			this.topbar = new Ext.Toolbar({
				items : [{
							iconCls : 'btn-anotherdetail',
							text : '提成结算',
							xtype : 'button',
							scope : this,
							handler :this.settle
						},{
							iconCls : 'btn-search',
							text : '结算记录',
							xtype : 'button',
							scope : this,
							handler :this.search1
						}]
			});
		var summary = new Ext.ux.grid.GridSummary();
		function totalMoney(v, params, data) {
					return '总计(元)';
		}
		
		var url = __ctxPath+ "/creditFlow/financingAgency/settleSelPlBidPlan.do?type="+this.type;
		
		this.gridPanel = new HT.GridPanel({
					name : 'PlatInvestmentCoreQueryGrid'+this.type,
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
							}, 'organId','organName','payStartDate','payEndDate','state','factMoney'],

					columns : [{
								header : 'id',
								dataIndex : 'id',
								hidden : true
							},{
								header : 'organId',
								dataIndex : 'organId',
								hidden : true
							}, {
								header : '部门',
								width : 80,
								align:'center',
								dataIndex : 'organName'
								/*renderer : function(value) {
									if (value ==1) {
										return "线下";
									} else if(value ==0){
										return "线上";
									}
								}*/
								
							}, {
								header : '最新结算开始日期',
								width : 80,
								align:'center',
								/*summaryType : 'count',
								summaryRenderer : totalMoney,*/
								dataIndex : 'payStartDate'
							}, {
								header : '最新结算截止日期',
								width : 80,
								align:'center',
								/*summaryType : 'count',
								summaryRenderer : totalMoney,*/
								dataIndex : 'payEndDate'
							}, {
								header : '当前状态',
								width : 60,
								align:'left',
								dataIndex : 'state',
								renderer : function(value) {
									if (value ==0) {
										return "待审核";
									} else if(value ==1){
										return "待支付";
									} else if(value ==2){
										return "已支付";
									}
								}
							}, {
								header : '金额',
								align : 'center',
								width : 110,
								dataIndex : 'factMoney'
							}/*,{
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
				}
		
	},
	/**
	 * 启动提成结算
	 */
	settle : function() {
		var selectRs = this.gridPanel.getSelectionModel().getSelections();
		var gridPanel = this.gridPanel;
				if (selectRs.length == 0) {
					Ext.ux.Toast.msg('操作信息', '请选择记录!');
					return;
				} else if (selectRs.length > 1) {
					Ext.ux.Toast.msg('操作信息', '只能选择一条记录!');
					return;
				} else {
					var record = selectRs[0];
					debugger
					if((record.data.state!=null&&record.data.state==0)&&record.data.payEndDate==null){
						Ext.ux.Toast.msg('操作信息','该提成结算未审核,请审核后再启动新的结算!');
						return ;
					}
					var today=new Date();
					var t=today.getTime()-1000*60*60*24;
					var yesterday=new Date(t);
					if(new Date(record.data.payEndDate)>yesterday){
						Ext.ux.Toast.msg('操作信息','已经是最新结算记录,不能再申请');
						return ;
					}
					Ext.Ajax.request( {
							url : __ctxPath +"/smallLoan/finance/investSettleSlEarlyRepaymentRecord.do",
							params : {id:record.data.id},
							method : 'POST',
							success : function(response) {
										//gridPanel.getStore().reload()
										var obj=Ext.util.JSON.decode(response.responseText)
										var contentPanel = App.getContentPanel();
										if(obj.taskId==1){
											Ext.ux.Toast.msg('操作信息','您不是提前还款流程中任务<提前还款申请>的处理人!');
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
		
	},
	search1:function(){
		var selectRs = this.gridPanel.getSelectionModel().getSelections();
		if (selectRs.length == 0) {
					Ext.ux.Toast.msg('操作信息', '请选择记录!');
					return;
				} else if (selectRs.length > 1) {
					Ext.ux.Toast.msg('操作信息', '只能选择一条记录!');
					return;
				} else {
					var record = selectRs[0];
					var organId = selectRs[0].data.organId;
					new SettlementReviewerPayView({organId:organId,type:this.type}).show();
				}
	}
});
