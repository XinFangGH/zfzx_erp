report={
	/**
	 * 充值提现统计图
	 * @type 
	 */
	reChargeWithDraw:[
		__ctxPath +'/highchart/ownChart/reChargeWithDraw.js',
		__ctxPath +'/highchart/ownChart/highchartUtil.js',
		__ctxPath +'/highchart/themes/grid.js',
		__ctxPath +'/highchart/themes/sand-signika.js',
		__ctxPath +'/highchart/themes/skies.js'
	],
	/**
	 * 用户数量趋势分析图
	 * @type 
	 */
	userQuantityTrends:[
		__ctxPath +'/highchart/ownChart/userQuantityTrends.js',
		__ctxPath +'/highchart/ownChart/highchartUtil.js',
		__ctxPath +'/highchart/themes/grid.js',
		__ctxPath +'/highchart/themes/sand-signika.js',
		__ctxPath +'/highchart/themes/skies.js'
	],
	/**
	 * 逾期借款统计图
	 * @type 
	 */
	overdueLoanStatistics:[
		__ctxPath +'/highchart/ownChart/overdueLoanStatistics.js',
		__ctxPath +'/highchart/ownChart/highchartUtil.js',
		__ctxPath +'/highchart/themes/grid.js',
		__ctxPath +'/highchart/themes/sand-signika.js',
		__ctxPath +'/highchart/themes/skies.js'
	],
	/**
	 * 正式个人客户明细表 or 正式企业客户明细表
	 * @type 
	 */
	ReportFormalDetail:[
		__ctxPath + '/js/creditFlow/report/LoanReport/CustomerCountType/ReportFormalDetail.js',
		__ctxPath + '/js/creditFlow/creditAssignment/customer/shop.js'
	],
	/**
	 * 即将生日客户明细表
	 * @type 
	 */
	CustomerBirthdayAnalysis:[
		__ctxPath + '/js/creditFlow/report/LoanReport/CustomerCountType/CustomerBirthdayAnalysis.js',
		__ctxPath + '/js/creditFlow/creditAssignment/customer/shop.js'
	],
	/**
	 * 贷款客户明细表
	 * @type 
	 */
	LoanCustomerDetail:[
		__ctxPath + '/js/creditFlow/report/LoanReport/CustomerCountType/LoanCustomerDetail.js',
		__ctxPath + '/js/creditFlow/creditAssignment/customer/shop.js'
	],
	/**
	 * 单笔贷款最大十户统计表
	 * @type 
	 */
	TenMaxLoanCustomer:[
		__ctxPath + '/js/creditFlow/report/LoanReport/CustomerCountType/TenMaxLoanCustomer.js',
		__ctxPath + '/js/creditFlow/creditAssignment/customer/shop.js'
	],
	/**
	 * 十大借款报表(个人)or十大借款报表(企业)or客户借款统计表(个人)or客户借款统计表(企业)or贷款本息应收统计表or贷款本息实收明细表
	 * 贷款到期明细表or贷款额度区间分析表or贷款即将还款明细表（不含逾期）
	 * @type 
	 */
	ReportPAIPaidDetail:[
		__ctxPath + '/js/creditFlow/report/LoanReport/FinanceEarning/ReportPAIPaidDetail.js'
	],
	/**
	 * 贷款信息明细表
	 * @type 
	 */
	ReportLoanInfoDetail:[
		__ctxPath + '/js/creditFlow/report/LoanReport/ProjectBestowType/ReportLoanInfoDetail.js'
	],
	/**
	 * 贷款发放明细表or贷款发放明细表2
	 * @type 
	 */
	ReportLoanAllotDetail:[
		__ctxPath + '/js/creditFlow/report/LoanReport/ProjectBestowType/ReportLoanAllotDetail.js'
	],
	/**
	 * 贷款发放汇总表（月报）or贷款发放汇总表（季报）or贷款财务综合汇总表or贷款情况汇总表（月报）or贷款情况汇总表（季报）
	 * 贷款情况汇总表（年报）or贷款项目月报表（个贷）or贷款项目月报表（企贷）or收回逾期贷款明细or风险分类汇总表（月报）
	 * 风险分类汇总表（季报）
	 * @type 
	 */
	ReportPAIComplexDetail:[
		__ctxPath + '/js/creditFlow/report/LoanReport/FinanceEarning/ReportPAIComplexDetail.js'
	],
	/**
	 * 合同登记簿 or 贷款还款明细表
	 * @type 
	 */
	ReportLoanAfterBackDetail:[
		__ctxPath + '/js/creditFlow/report/LoanReport/LoanAfterType/ReportLoanAfterBackDetail.js'
	],
	/**
	 * 合同到期统计表
	 * @type 
	 */
	ReportContractDetail:[
		__ctxPath + '/js/creditFlow/report/LoanReport/LoanAfterType/ReportContractDetail.js'
	],
	/**
	 * 风险分类明细表
	 * @type 
	 */
	ReportRiskTypeDetail:[
		__ctxPath + '/js/creditFlow/report/LoanReport/LoanAfterType/ReportRiskTypeDetail.js',
		__ctxPath + '/js/creditFlow/creditAssignment/customer/shop.js'
	],
	/**
	 * 发放贷款汇总表
	 * @type 
	 */
	ReportFFLoanSumDetail:[
		__ctxPath + '/js/creditFlow/report/LoanReport/FinanceEarning/ReportFFLoanSumDetail.js'
	],
	/**
	 * 贷款收入明细表
	 * @type 
	 */
	ReportLoanIncomeDetail:[
		__ctxPath + '/js/creditFlow/report/LoanReport/FinanceEarning/ReportLoanIncomeDetail.js'
	],
	/**
	 * 投资客户明细表
	 * @type 
	 */
	CsInvestmentpersonDetailInfo:[
		__ctxPath + '/js/creditFlow/report/WealthReport/CsInvestPersonStandardReport/CsInvestmentpersonDetailInfo.js',
		__ctxPath + '/js/creditFlow/creditAssignment/customer/shop.js'
	],
	/**
	 * 新增投资客户月度统计表
	 * @type 
	 */
	CsInvestmentpersonNewlyAdded:[
		__ctxPath + '/js/creditFlow/report/WealthReport/CsInvestPersonStandardReport/CsInvestmentpersonNewlyAdded.js',
		__ctxPath + '/js/creditFlow/creditAssignment/customer/shop.js'
	],
	/**
	 * 投资客户即将生日表
	 * @type 
	 */
	CsInvestmentpersonBirthday:[
		__ctxPath + '/js/creditFlow/report/WealthReport/CsInvestPersonStandardReport/CsInvestmentpersonBirthday.js',
		__ctxPath + '/js/creditFlow/creditAssignment/customer/shop.js'
	],
	/**
	 * 投资客户累计投资统计表 or 投资客户累计投资统计表
	 * @type 
	 */
	CsInvestmentpersonSumInvest:[
		__ctxPath + '/js/creditFlow/report/WealthReport/CsInvestPersonStandardReport/CsInvestmentpersonSumInvest.js',
		__ctxPath + '/js/creditFlow/creditAssignment/customer/shop.js'
	],
	/**
	 * 投资大客户累计投资统计表
	 * @type 
	 */
	CsInvestmentpersonCount:[
		__ctxPath + '/js/creditFlow/report/WealthReport/CsInvestPersonStandardReport/CsInvestmentpersonCount.js'
	],
	/**
	 * 理财产品购买明细表
	 * @type 
	 */
	FinancialProductsDetail:[
		__ctxPath + '/js/creditFlow/report/WealthReport/FinancialProductsBuy/FinancialProductsDetail.js'
	],
	/**
	 * 理财产品派息预算表
	 * @type 
	 */
	FinancialProductsDividendMonth:[
		__ctxPath + '/js/creditFlow/report/WealthReport/FinancialProductsBuy/FinancialProductsDividendMonth.js'
	],
	/**
	 * 理财产品派息预算明细表
	 * @type 
	 */
	FinancialProductsDividend:[
		__ctxPath + '/js/creditFlow/report/WealthReport/FinancialProductsBuy/FinancialProductsDividend.js'
	],
	/**
	 * 理财产品派息明细表
	 * @type 
	 */
	FinancialProductsDividendAlready:[
		__ctxPath + '/js/creditFlow/report/WealthReport/FinancialProductsBuy/FinancialProductsDividendAlready.js'
	],
	/**
	 * 门店理财产品购买明细表
	 * @type 
	 */
	FinancialProductsDetailShop:[
		__ctxPath + '/js/creditFlow/report/WealthReport/FinancialProductsBuy/FinancialProductsDetailShop.js',
		__ctxPath + '/js/creditFlow/creditAssignment/customer/shop.js'
	],
	/**
	 * 门店理财产品派息预算表
	 * @type 
	 */
	FinancialProductsDetailDividendShop:[
		__ctxPath + '/js/creditFlow/report/WealthReport/FinancialProductsBuy/FinancialProductsDetailDividendShop.js',
		__ctxPath + '/js/creditFlow/creditAssignment/customer/shop.js'
	],
	/**
	 * 门店理财产品派息明细表
	 * @type 
	 */
	FinancialProductsDetailAlreadyShop:[
		__ctxPath + '/js/creditFlow/report/WealthReport/FinancialProductsBuy/FinancialProductsDetailAlreadyShop.js',
		__ctxPath + '/js/creditFlow/creditAssignment/customer/shop.js'
	],
	/**
	 * 会员新增明细表
	 * @type 
	 */
	ReportMemberAdd:[
		__ctxPath + '/js/creditFlow/report/InternetReport/member/ReportMemberAdd.js'
	],
	/**
	 * 会员推荐明细表
	 * @type 
	 */
	ReportMemberRecommend:[
		__ctxPath + '/js/creditFlow/report/InternetReport/member/ReportMemberRecommend.js'
	],
	/**
	 * 推荐投资统计表
	 * @type 
	 */
	ReportMemberRecommendInvest:[
		__ctxPath + '/js/creditFlow/report/InternetReport/member/ReportMemberRecommendInvest.js'
	],
	/**
	 * 会员收益统计表
	 * @type 
	 */
	ReportMemberIncome:[
		__ctxPath + '/js/creditFlow/report/InternetReport/member/ReportMemberIncome.js'
	],
	/**
	 * 投资收益统计表
	 * @type 
	 */
	ReportMemberInvest:[
		__ctxPath + '/js/creditFlow/report/InternetReport/member/ReportMemberInvest.js'
	],
	/**
	 * 满标时间分析表
	 * @type 
	 */
	ReportPlBidFullDate:[
		__ctxPath + '/js/creditFlow/report/InternetReport/loosePlPlanBid/ReportPlBidFullDate.js'
	],
	/**
	 * 直投标放款明细表
	 * @type 
	 */
	ReportPlBidPlanDir:[
		__ctxPath + '/js/creditFlow/report/InternetReport/loosePlPlanBid/ReportPlBidPlanDir.js'
	],
	/**
	 * 转让标放款明细表
	 * @type 
	 */
	ReportPlBidPlanOr:[
		__ctxPath + '/js/creditFlow/report/InternetReport/loosePlPlanBid/ReportPlBidPlanOr.js'
	],
	/**
	 * 直投标放款统计表
	 * @type 
	 */
	ReportPlBidDirIntentExpire:[
		__ctxPath + '/js/creditFlow/report/InternetReport/loosePlPlanBid/ReportPlBidDirIntentExpire.js'
	]
	,
	/**
	 * 债权标放款统计表
	 * @type 
	 */
	ReportPlBidOrIntentExpire:[
		__ctxPath + '/js/creditFlow/report/InternetReport/loosePlPlanBid/ReportPlBidOrIntentExpire.js'
	],
	/**
	 * 债权交易明细表
	 * @type 
	 */
	ReportCreditorDeal:[
		__ctxPath + '/js/creditFlow/report/InternetReport/loosePlPlanBid/ReportCreditorDeal.js'
	],
	/**
	 * 到期款项明细表
	 * @type 
	 */
	ReportPlBidExpire:[
		__ctxPath + '/js/creditFlow/report/InternetReport/loosePlPlanBid/ReportPlBidExpire.js'
	],
	/**
	 * 到期款项统计表
	 * @type 
	 */
	ReportPlBidIntentExpire:[
		__ctxPath + '/js/creditFlow/report/InternetReport/loosePlPlanBid/ReportPlBidIntentExpire.js'
	],
	/**
	 * 逾期款项明细表
	 * @type 
	 */
	ReportPlBidOverdue:[
		__ctxPath + '/js/creditFlow/report/InternetReport/loosePlPlanBid/ReportPlBidOverdue.js'
	],
	/**
	 * 投资起息明细表
	 * @type 
	 */
	ReportManagePlan:[
		__ctxPath + '/js/creditFlow/report/InternetReport/managePlan/ReportManagePlan.js'
	],
	/**
	 * 款项到期统计表
	 * @type 
	 */
	ReportAssigninterestExpire:[
		__ctxPath + '/js/creditFlow/report/InternetReport/managePlan/ReportAssigninterestExpire.js'
	],
	/**
	 * 交易年度统计表
	 * @type 
	 */
	ReportplatformDeail:[
		__ctxPath + '/js/creditFlow/report/InternetReport/platformReport/ReportplatformDeail.js'
	],
	/**
	 * 平台收费明细表
	 * @type 
	 */
	ReportTerraceIncome:[
		__ctxPath + '/js/creditFlow/report/InternetReport/loosePlPlanBid/ReportTerraceIncome.js'
	],
	/**
	 * 平台随息费汇总表
	 * @type 
	 */
	ReportPlatformWithInterest:[
		__ctxPath + '/js/creditFlow/report/InternetReport/platformReport/ReportPlatformWithInterest.js'
	],
	/**
	 * 一级业绩统计表 or 二级业绩统计表 or 一级类型统计表 or 二级类型统计表
	 * @type 
	 */
	Recommend:[
		__ctxPath +'/js/creditFlow/report/OfflineRecommendReoprt/Recommend.js'
	],
	/**
	 * 红包发放台账
	 * @type 
	 */
	PlateFormFianceRedEnvelopeDetailView:[
		__ctxPath + '/js/creditFlow/finance/plateFormFinanceManage/PlateFormAccountDetailFormManager/PlateFormFianceRedEnvelopeDetailView.js'
	],
	/**
	 * 平台随息费台账
	 * @type 
	 */
	NewPlateFormFianceBidIncomeDetailView:[
		__ctxPath + '/js/creditFlow/finance/plateFormFinanceManage/PlateFormAccountDetailFormManager/NewPlateFormFianceBidIncomeDetailView.js'
	],
	/**
	 * 平台其他收费台账
	 * @type 
	 */
	PlateFormFianceRiskAccountIncomeDetailView:[
       __ctxPath + '/js/creditFlow/finance/plateFormFinanceManage/PlateFormAccountDetailFormManager/PlateFormFianceRiskAccountIncomeDetailView.js'
	],
	/**
	 * 债权交易手续费台账
	 * @type 
	 */
	PlateFormFianceTransferFeeDetailView:[
		__ctxPath + '/js/creditFlow/finance/plateFormFinanceManage/PlateFormAccountDetailFormManager/PlateFormFianceTransferFeeDetailView.js'
	],
	/**
	 * 非业务相关台账
	 * @type 
	 */
	PlateFormFianceOtherDetailView:[
		__ctxPath + '/js/creditFlow/finance/plateFormFinanceManage/PlateFormAccountDetailFormManager/PlateFormFianceOtherDetailView.js'
	]
	/**
	 * 平台交易总览
	 * @type 
	 */
	,plateDeal : [
    	__ctxPath+'/js/p2p/plateFormDeal/plateDeal.js'
	],
	/**
	 * 平台充值台账
	 * @type 
	 */
	plateRechargeDeal : [
    	__ctxPath+'/js/p2p/plateFormDeal/plateRechargeDeal.js'
	]
	/**
	 * 平台取现台账
	 * @type 
	 */
	,plateWithdrawDeal : [
    	__ctxPath+'/js/p2p/plateFormDeal/plateWithdrawDeal.js'
	],
	/**
	 * 募集期奖励台账
	 * @type 
	 */
	RaiseRateBpFundIntent : [
    	__ctxPath+'/js/p2p/RaiseRateBpFundIntent.js',
    	__ctxPath+'/js/p2p/RaiseBpfundIntentDetail.js'
	],
	/**
	 * 优惠券奖励台账
	 * @type 
	 */
	CouponsBpFundIntent : [
    	__ctxPath+'/js/p2p/CouponsBpFundIntent.js',
    	__ctxPath+'/js/p2p/CouponsBpfundIntentDetail.js'
	]
	/**
	 * 普通加息奖励台账
	 * @type 
	 */
	,AddrateBpFundIntent : [
    	__ctxPath+'/js/p2p/AddrateBpFundIntent.js',
    	__ctxPath+'/js/p2p/CouponsBpfundIntentDetail.js'
	]
	/**
	 * 募集期奖励台账
	 * @type 
	 */
	,RaiseRateAssignIntent : [
    	__ctxPath+'/js/p2p/RaiseRateAssignIntent.js',
    	__ctxPath+'/js/p2p/RaiseAssignIntentDetail.js'
	]
	/**
	 * 优惠券奖励台账
	 * @type 
	 */
	,CouponsAssignIntent : [
    	__ctxPath+'/js/p2p/CouponsAssignIntent.js',
    	__ctxPath+'/js/p2p/CouponsAssignIntentDetail.js'
	],
	/**
	 * 普通加息奖励台账
	 * @type 
	 */
	AddrateAssignIntent : [
    	__ctxPath+'/js/p2p/AddrateAssignIntent.js',
    	__ctxPath+'/js/p2p/CouponsAssignIntentDetail.js'
	],
	/**
	 * 会员账户对账单
	 * @type 
	 */
	SystemAccountTotalCheckFile:[
		__ctxPath+'/js/creditFlow/finance/PlateFormFianceCheck/SystemAccountTotalCheckFile.js',
		__ctxPath + '/ext3/ux/ColumnHeaderGroup.js'
	],
	/**
	 * 会员资金异常账单
	 * @type 
	 */
	SystemAccountIsException:[
		__ctxPath+'/js/creditFlow/finance/PlateFormFianceCheck/SystemAccountIsException.js',
		__ctxPath + '/ext3/ux/ColumnHeaderGroup.js'
	],
	/**
	 * 充值对账
	 * @type 
	 */
	RechargeReconciliation:[
		__ctxPath+'/js/creditFlow/finance/PlateFormFianceCheck/RechargeReconciliation.js',
		__ctxPath + '/ext3/ux/ColumnHeaderGroup.js'
	],
	/**
	 * 提现对账
	 * @type 
	 */
	WithdrawalsReconciliation:[
		__ctxPath+'/js/creditFlow/finance/PlateFormFianceCheck/WithdrawalsReconciliation.js',
		__ctxPath + '/ext3/ux/ColumnHeaderGroup.js'
	],
	/**
	 * 标的对账
	 * @type 
	 */
	StandardReconciliation:[
		__ctxPath+'/js/creditFlow/finance/PlateFormFianceCheck/StandardReconciliation.js',
		__ctxPath+'/js/creditFlow/finance/PlateFormFianceCheck/StandardReconciliationView.js',
		__ctxPath + '/ext3/ux/ColumnHeaderGroup.js'
	],
	/**
	 * 标的转账
	 * @type 
	 */
	BidTransferReconciliation:[
		__ctxPath+'/js/creditFlow/finance/PlateFormFianceCheck/BidTransferReconciliation.js',
		__ctxPath + '/ext3/ux/ColumnHeaderGroup.js'
	],
	/**
	 * 转账对账
	 * @type 
	 */
	StandardReconciliationTransfer:[
		__ctxPath+'/js/creditFlow/finance/PlateFormFianceCheck/StandardReconciliationTransfer.js',
		__ctxPath + '/ext3/ux/ColumnHeaderGroup.js'
	],
	/**
	 * 充值、放款、还款、取现 记录查询 
	 * @type 
	 */
	YeePaySingleQueryView:[
		__ctxPath+'/js/thirdInterface/YeePaySingleQueryView.js'
	],
	/**
	 * 标的账户查询
	 * @type 
	 */
	UMPayBidFlowQuery:[
		__ctxPath+'/js/thirdInterface/UMPayBidFlowQuery.js'
	],
	/**
	 * 总览记录查询
	 * @type 
	 */
	YeePayQueryView:[
		__ctxPath+'/js/thirdInterface/YeePayQueryView.js'
	]
};

Ext.applyIf(App.importJs,report);