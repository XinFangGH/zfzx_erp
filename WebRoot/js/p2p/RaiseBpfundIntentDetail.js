/**
 * @author
 * @class SlFundIntentView
 * @extends Ext.Panel
 * @description [SlFundIntent]管理
 * @company 智维软件
 * @createtime:
 */
RaiseBpfundIntentDetail = Ext.extend(Ext.Window, {
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
				RaiseBpfundIntentDetail.superclass.constructor.call(this, {
							id : 'RaiseBpfundIntentDetail',
							layout : 'fit',
							items :  this.gridPanel,
							modal : true,
							height : 553,
							width : 1200,
							maximizable : true,
							title : '查看奖励明细'
							
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
					url : __ctxPath + "/creditFlow/finance/getRaiseRateFundInentDetailBpFundIntent.do?planId="+this.planId,
					fields : ['intentDate','factDate','receiverP2PName','incomeMoney','fundType','bidtime','startTime','days'],
					columns : [{
								header : '奖励用户',
								align : 'center',
								dataIndex : 'receiverP2PName',
								width : 70
							}, {
								header : "奖励金额",
								dataIndex : 'incomeMoney',
								align :'center',
								width:70,
								renderer:function(v){
									return Ext.util.Format.number(v,',000,000,000.00')+"元"
                         	     }
							
							} ,{
								header : "投资日期",
								dataIndex : 'bidtime',
								align :'center',
								width:112
							
							} ,{
								header : "起息日期",
								dataIndex : 'startTime',
								align :'center',
								width:112
							
							},{
								header : "计息天数",
								dataIndex : 'days',
								align :'center',
								width:70
							
							},{
								header : "资金类型",
								dataIndex : 'fundType',
								align :'center',
								width:70,
								renderer:function(v){
									
										return "利息奖励";
									
                         	     }
							
							}, {
								header : '计划奖励日期',
								width : 70,
								dataIndex : 'intentDate',
								align : 'center'
						//		sortable:true
							}, {
								header : '实际奖励日期',
								width : 70,
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