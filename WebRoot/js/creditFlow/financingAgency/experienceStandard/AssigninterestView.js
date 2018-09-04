/**
 * @author
 * @class SlFundIntentView
 * @extends Ext.Panel
 * @description [SlFundIntent]管理
 * @company 智维软件
 * @createtime:
 */
AssigninterestView = Ext.extend(Ext.Window, {
			// 构造函数
			constructor : function(_cfg) {
				Ext.applyIf(this, _cfg);
				// 初始化组件
				this.initUIComponents();
				// 调用父类构造
				AssigninterestView.superclass.constructor.call(this, {
							id : 'AssigninterestView',
							layout : 'fit',
							items :  this.gridPanel,
							modal : true,
							height : 400,
							width : screen.width*0.5,
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
					id : 'AssigninterestViewGird',
					isautoLoad:true,
					url : __ctxPath + "/creditFlow/financingAgency/listPlMmOrderAssignInterest.do?Q_mmplanId_L_EQ="+this.mmplanId+"" +
							"&Q_isValid_SN_EQ=0&Q_isCheck_SN_EQ=0",
					fields : ['assignInterestId','investPersonName','intentDate','incomeMoney',
					    'factDate'],
					columns : [ {
								header : '账号',
								align : 'center',
								dataIndex : 'investPersonName'
							}, {
								header : '到期日期',
								dataIndex : 'intentDate',
								align : 'center'
							},{
								header : "利息",
								dataIndex : 'incomeMoney',
								align :'right',
								renderer:function(v){
									return Ext.util.Format.number(v,',000,000,000.00')+"元"
                         	     }
							
							} ,{
								header : '实际派息日',
								dataIndex : 'factDate',
								align : 'center'
							}
						]

					});
			}
	
});