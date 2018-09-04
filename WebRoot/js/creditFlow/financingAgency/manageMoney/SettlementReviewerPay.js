/**
 * @author
 * @class PlManageMoneyTypeView
 * @extends Ext.Panel
 * @description [PlManageMoneyType]管理
 * @company 智维软件
 * @createtime:
 */
SettlementReviewerPay = Ext.extend(Ext.Panel, {
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		var leftwidth=80;
		// 初始化组件
		SettlementReviewerPay.superclass.constructor.call(this, {
			layout : 'column',
			items : [{
				columnWidth : .3,
				labelWidth : leftwidth,
				layout : 'form',
				items : [{
					fieldLabel : '结算开始日期',
					readOnly:true,
					name : "settlementReviewerPay.payStartDate",
					allowBlank : false,
					xtype : 'datefield',
					format:'Y-m-d',
					anchor : '91%'
				}]
			}, {
				columnWidth : .3,
				layout : 'form',
				labelWidth : leftwidth,
				items : [{
							fieldLabel : '结算截止日期',
							name : 'settlementReviewerPay.payEndDate',
							xtype : 'datefield',
							maxValue: new Date(),
							readOnly:this.isreadOnly,
							format : 'Y-m-d',
							anchor : '100%'
						}]
			},{
				columnWidth : .15,
				layout : 'form',
				border : false,
				labelWidth : 60,
				hidden:this.isreadOnly,
				items : [{
							text : '查询',
							xtype : 'button',
							scope : this,
							style : 'margin-left:30px',
							anchor : "90%",
							iconCls : 'btn-search',
							handler : function(){
								var payStartDate = this.getCmpByName('settlementReviewerPay.payStartDate').getValue();
								var payEndDate = this.getCmpByName('settlementReviewerPay.payEndDate').getValue();
								if(payStartDate&&payEndDate){
									var store = this.object.gridPanel.getStore();
									store.reload({
										params : {
											orgId : this.organizationId,
											startDate:formatDate(payStartDate,'yyyy-MM-dd'),
											endDate:formatDate(payEndDate,'yyyy-MM-dd')
										}
									});
								}else{
									Ext.ux.Toast.msg('操作提醒', '请填写开始时间与结束时间！');
								}
							}
						}]
			},{
				columnWidth : .15,
				layout : 'form',
				border : false,
//				hidden:this.isreadOnly,
				labelWidth : 60,
				items : [{
							text : '导出pdf',
							xtype : 'button',
							scope : this,
							style : 'margin-left:30px',
							anchor : "90%",
							iconCls : 'excel-cls',
							handler : function(){
								var id = 
								window.open(
										__ctxPath + '/contract/createInvestSettleContractContractHelper.do?id='+this.settlementReviewerPayId,
										'_blank');
							}
						}]
			}]
		});
	}
});
