/**
 * @author
 * @class SlFundIntentView
 * @extends Ext.Panel
 * @description [SlFundIntent]管理
 * @company 智维软件
 * @createtime:
 */
BpFundIntentPeriodView = Ext.extend(Ext.Window, {
			// 构造函数
			constructor : function(_cfg) {
				if(typeof(_cfg.businessType)!="undefined")
				{
				      this.businessType=_cfg.businessType;
				}
				Ext.applyIf(this, _cfg);
				// 初始化组件
				this.initUIComponents();
				// 调用父类构造
				BpFundIntentPeriodView.superclass.constructor.call(this, {
							id : 'BpFundIntentPeriodView',
							layout : 'fit',
							items :  this.gridPanel,
							modal : true,
							height : 553,
							width : screen.width*0.9,
							maximizable : true,
							title : '查看还款明细'
							
						});
			},// end of constructor
			// 初始化组件
			initUIComponents : function() {


				var summary = new Ext.ux.grid.GridSummary();
				function totalMoney(v, params, data) {
					return '总计';
				}
				this.gridPanel = new HT.GridPanel({
					bodyStyle : "width : 100%",
					region : 'center',
					tbar : this.topbar,
					plugins : [summary],
					/*viewConfig: {  
		            	forceFit:false  
		            },*/
					// 使用RowActions
					rowActions : false,
					id : 'BpFundIntentPeriodGird',
					isautoLoad:true,
					url : __ctxPath + "/creditFlow/finance/listPeriodbyOneLedgerBpFundIntent.do?intentDate="+this.intentDate+"&planId="+this.planId,
					fields : ['intentDate',
							'principalRepayment.afterMoney','principalRepayment.incomeMoney','principalRepayment.accrualMoney',
							'loanInterest.afterMoney','loanInterest.incomeMoney','loanInterest.accrualMoney',					
							'consultationMoney.afterMoney','consultationMoney.incomeMoney','consultationMoney.accrualMoney',							
							'serviceMoney.afterMoney','serviceMoney.incomeMoney','serviceMoney.accrualMoney',
							  'interestPenalty.afterMoney','interestPenalty.incomeMoney','interestPenalty.accrualMoney',
							 'factDate','accrualMoney', 'bidPlanName','bidPlanProjectNum'
							,'borrowName','punishDays','repaySource','investPersonName',
							'payintentPeriod'],
					columns : [ {
								header : '期数',
								align :'center',
								dataIndex : 'payintentPeriod',
								align : 'center',
								width : 70,
								renderer:function(v){
								  return "第"+v+"期";
								}
							
							}
							, {
								header : '资金来源',
								dataIndex : 'bidPlanName',
								width :70,
								align : 'center',
								renderer:function(v){
								  if(v==1){
								    return "企业";
								  }else
								    return "个人";
								}
							}, {
								header : '投资人',
								align : 'center',
								dataIndex : 'investPersonName',
								width : 70
							}, {
								header : "本金",
								dataIndex : 'principalRepayment.incomeMoney',
								align :'right',
								width:110,
								renderer:function(v){
									return Ext.util.Format.number(v,',000,000,000.00')+"元"
                         	     }
							
							} ,{
								header : "利息",
								dataIndex : 'loanInterest.incomeMoney',
								align :'right',
								width:110,
								renderer:function(v){
									return Ext.util.Format.number(v,',000,000,000.00')+"元"
                         	     }
							
							} ,{
								header : "管理咨询费",
								dataIndex : 'consultationMoney.incomeMoney',
								align :'right',
								width:110,
								renderer:function(v){
									return Ext.util.Format.number(v,',000,000,000.00')+"元"
                         	     }
							
							} ,{
								header : "财务服务费",
								dataIndex : 'serviceMoney.incomeMoney',
								align :'right',
								width:110,
								renderer:function(v){
									return Ext.util.Format.number(v,',000,000,000.00')+"元"
                         	     }
							
							},{
								header : "补偿息",
								dataIndex : 'interestPenalty.incomeMoney',
								align :'right',
								width:110,
								renderer:function(v){
									return Ext.util.Format.number(v,',000,000,000.00')+"元"
                         	     }
							
							}, {
								header : "逾期天数",
								dataIndex : 'punishDays',
								align :'right',
								width:80,
								renderer:function(v){
									return Ext.util.Format.number(v,',000,000,000')+"天";
                         	     }
							
							}, {
								header : "罚息",
								dataIndex : 'loanInterest.incomeMoney',
								align :'right',
								width:80,
								renderer:function(v,a,r){
								return Ext.util.Format.number((r.get("principalRepayment.accrualMoney")+
									r.get("loanInterest.accrualMoney")+r.get("consultationMoney.accrualMoney")+r.get("serviceMoney.accrualMoney")),',000,000,000.00')+"元"
                         	     }

							
							}, {
								header : "合计",
								dataIndex : 'loanInterest.incomeMoney',
								align :'right',
								width:110,
								renderer:function(v,a,r){
									return Ext.util.Format.number((r.get("principalRepayment.incomeMoney")+
									r.get("loanInterest.incomeMoney")+r.get("consultationMoney.incomeMoney")+r.get("serviceMoney.incomeMoney")+r.get("interestPenalty.incomeMoney")),',000,000,000.00')+"元"
                         	     }
							
							}, {
								header : '计划还款日',
								dataIndex : 'intentDate',
								width:80,
								align : 'center'
							} ,{
								header : "实际到账金额",
								dataIndex : 'loanInterest.incomeMoney',
								align :'right',
								width:100,
								renderer:function(v,a,r){
								return Ext.util.Format.number((r.get("principalRepayment.afterMoney")+
										r.get("loanInterest.afterMoney")+r.get("consultationMoney.afterMoney")+r.get("serviceMoney.afterMoney")+r.get("interestPenalty.afterMoney")),',000,000,000.00')+"元"
                         	     }
							
							}, {
								header : '实际到账日期',
								width : 100,
								dataIndex : 'factDate',
								align : 'center'
						//		sortable:true
							}
						]

						// end of columns
					});

				this.gridPanel.addListener('cellclick', this.cellClick);

			},// end of the initComponents()
			// 重置查询表单
//			rowClick : function(grid, rowindex, e) {
//				grid.getSelectionModel().each(function(rec) {
//							new editAfterMoneyForm({
//								fundIntentId : rec.data.fundIntentId,
//								afterMoney : rec.data.afterMoney,
//								notMoney : rec.data.notMoney,
//								flatMoney : rec.data.flatMoney
//									}).show();
//						});
//				
//			},
			reset : function() {
				this.searchPanel.getForm().reset();
				var obj = Ext.getCmp('Q_fundType_N_EQ'+tabflag);
			//	obj.setEditable(false);
				var arrStore= new Ext.data.SimpleStore({});
				obj.clearValue();
                obj.store = arrStore;
			    arrStore.load({"callback":test});
			    function test(r){
			    	if (obj.view) { // 刷新视图,避免视图值与实际值不相符
			    		obj.view.setStore(arrStore);
			        }
								       
								    }
			},
			// 按条件搜索
			search : function() {
				$search({
							searchPanel : this.searchPanel,
							gridPanel : this.gridPanel
						});
			}
	
});