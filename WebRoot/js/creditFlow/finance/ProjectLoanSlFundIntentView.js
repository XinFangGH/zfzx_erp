/**
 * @author
 * @class ProjectLoanSlFundIntentView
 * @extends Ext.Panel
 * @description 项目放款款项计划
 * @company 智维软件
 * @createtime:
 */
ProjectLoanSlFundIntentView = Ext.extend(Ext.Panel, {
			// 构造函数
			constructor : function(_cfg) {
				if (_cfg == null) {
					_cfg = {};
				}
				if (typeof (_cfg.projectId) != "undefined") {
					this.projId = _cfg.projectId;
				}
				Ext.applyIf(this, _cfg);
				// 初始化组件
				this.initUIComponents();
				// 调用父类构造
				ProjectLoanSlFundIntentView.superclass.constructor.call(this, {
//							title : '项目放款',
							id : 'ProjectLoanSlFundIntentView',
							layout : 'anchor',
							anchor : '100%',
							items : [this.slFundIntentGridPanel]
						});
			},// end of constructor
			// 初始化组件
			initUIComponents : function() {
				var incomeMoney = 0;
				this.topbar = new Ext.Toolbar({
						items : [{
									iconCls : 'btn-detail',
									text : '查看详细',
									xtype : 'button',
									scope : this,
									handler : function() {
										var record = this.slFundIntentGridPanel.getSelectionModel().getSelected();
										if(record != null) {
											this.waterReconciliationInfo(record);
										}else {
											Ext.ux.Toast.msg('操作信息','请选择记录！');
											return;
										}
									}
								}]
				});
				this.slFundIntentGridPanel = new HT.GridPanel({
					tbar:this.topbar,
					region : 'center',
					// 使用RowActions
					rowActions : true,
					showPaging : false,
					autoHeight:true,
					id : 'slFundIntentGridPanel',
					url : __ctxPath + "/creditFlow/finance/fundPlanListSlFundIntent.do",
					baseParams : {
						projectId : this.projId
					},
					fields : [{
								name : 'fundIntentId',
								type : 'int'
							}, 'fundType', 'incomeMoney',
							'payMoney', 'intentDate', 'factDate',
							'afterMoney', 'notMoney', 'accrualMoney'],
					columns : [{
								header : 'fundIntentId',
								dataIndex : 'fundIntentId',
								hidden : true
							}, {
								header : 'incomeMoney',
								dataIndex : 'incomeMoney',
								hidden : true,
								renderer : function(v) {
									incomeMoney = v;
								}
							}, {
								header : '资金类型',
								dataIndex : 'fundType',
								renderer : function(v) {
									switch (v) {
										case 1744 :
										return '货款收息';
										break;
										case 1747 :
										return '货款收本';
										break;
										case 1748 :
										return '货款贷出';
										break;
										case 1749 :
										return '货款佣金支付';
										break;
									}
								}
							}, {
								header : '金额(元)',
								dataIndex : 'payMoney',
								renderer : function(v) {
									if(v == 0) {
										return incomeMoney;
									}else {
										return v;
									}
								}
							}, {
								header : '计划到帐日',
								dataIndex : 'intentDate'
							},  {
								header : '实际到帐日',
								dataIndex : 'factDate'
							}, {
								header : '已对账金额(元)',
								dataIndex : 'afterMoney'
							}, {
								header : '未对账金额(元)',
								dataIndex : 'notMoney'
							}, {
								header : '逾期费用总额',
								dataIndex : 'accrualMoney'
							}, new Ext.ux.grid.RowActions({
										header : '操作',
										width : 50,
										actions : [
											{
												iconCls : 'btn-detail',
									        	qtip:'查看详细',
									        	style : 'margin:0 3px 0 3px'
											}],
										listeners : {
											scope : this,
											'action' : this.onRowAction
										}
									})]
						// end of columns
					});
			},// end of the initComponents()
			
			//查看流水对账
			waterReconciliationInfo : function(record) {
				var flag=1;
				new detailView({
					fundIntentId : record.data.fundIntentId,
					flag:flag
				}).show();
			},
			
			onRowAction : function(grid, record, action, row, col) {
				this.waterReconciliationInfo.call(this,record);
			}
});
