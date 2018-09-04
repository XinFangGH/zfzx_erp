loanBusiness={
	/**
	 * 个人贷款申请or企业贷款申请
	 * @type 
	 */
	CreateNewProjectFrom:[
		__ctxPath + '/js/commonFlow/CreateNewProjectFrom.js',
		__ctxPath + '/js/creditFlow/customer/enterprise/public.js',
		__ctxPath + '/js/creditFlow/common/EnterpriseShareequity.js',
		__ctxPath + '/js/commonFlow/NewProjectFormEnterPrise.js',
		__ctxPath + '/js/selector/UserDialog.js',
		__ctxPath + '/js/commonFlow/ExtUD.Ext.js',
		__ctxPath + '/js/creditFlow/report/SlCompanyInfoView.js',
		__ctxPath + '/js/creditFlow/creditAssignment/customer/shop.js'
	],
	/**
	 * 待办任务
	 * @type 
	 */
	ActivityTaskView : [
		__ctxPath + '/js/flow/ProcessNextForm.js',
		__ctxPath + '/js/creditFlow/smallLoan/project/SlProcessRunView.js',
		__ctxPath + '/js/creditFlow/common/ActivityTaskView.js'
	],
	/**
	 * 办结任务 
	 * @type 
	 */
	CompleteTaskView : [
		__ctxPath + '/js/creditFlow/smallLoan/project/SlProcessRunView.js',
		__ctxPath + '/js/creditFlow/common/CompleteTaskView.js'
	],
	/**
	 * 我参与的项目
	 * @type 
	 */
	MyProcessRunView_p : [
		__ctxPath + '/js/flow/MyProcessRunView.js',
		__ctxPath + '/js/flow/ProcessRunDetail.js'
	],
	/**
	 * 待审核项目查询or已通过项目查询or已终止项目查询
	 * @type 
	 */
	ApproveProjectManager : [
		__ctxPath + '/js/creditFlow/guarantee/meeting/SbhProjectInfo.js',
		__ctxPath + '/js/creditFlow/smallLoan/project/ApproveProjectManager.js',
		__ctxPath + '/js/creditFlow/report/ReportMenuSmallloanprojectdetail.js',
		__ctxPath + '/js/creditFlow/report/ReportPreviewSmallloanprojectdetail.js',
		__ctxPath + '/js/creditFlow/smallLoan/finance/StartDivertPanel.js',
		__ctxPath + '/js/creditFlow/smallLoan/finance/DivertList.js',
		__ctxPath + '/js/creditFlow/smallLoan/project/SlProcessRunView.js',
		__ctxPath + '/js/creditFlow/smallLoan/finance/EndDivertPanelDetail.js',
		__ctxPath + '/js/creditFlow/smallLoan/finance/EndDivertPanel.js'
	],
	/**
	 * 办理中项目查询or已终止项目查询or放款后综合查询or还款中项目查询
	 * or已展期项目查询or已违约项目查询or已完成项目查询
	 * @type 
	 */
	SmallLoanProjectManager : [
		__ctxPath + '/js/creditFlow/guarantee/meeting/SbhProjectInfo.js',
		__ctxPath + '/js/creditFlow/smallLoan/project/SmallLoanProjectManager.js',
		__ctxPath + '/js/creditFlow/report/ReportMenuSmallloanprojectdetail.js',
		__ctxPath + '/js/creditFlow/report/ReportPreviewSmallloanprojectdetail.js',
		__ctxPath + '/js/creditFlow/smallLoan/finance/StartDivertPanel.js',
		__ctxPath + '/js/creditFlow/smallLoan/finance/DivertList.js',
		__ctxPath + '/js/creditFlow/smallLoan/project/SlProcessRunView.js',
		__ctxPath + '/js/creditFlow/smallLoan/finance/EndDivertPanelDetail.js',
		__ctxPath + '/js/creditFlow/smallLoan/finance/EndDivertPanel.js'
	],
	/**
	 * 还款催收
	 * @type 
	 */
	SlFundintentUrgTab : [
		__ctxPath + '/js/creditFlow/finance/Urgeformpanel.js',
		__ctxPath + '/js/creditFlow/finance/SlFundintentUrgTab.js',
		__ctxPath + '/js/creditFlow/finance/SlFundintentUrgeView.js',
		__ctxPath + '/js/creditFlow/finance/SlFundintentUrgeForm.js',
		__ctxPath + '/js/creditFlow/finance/SlFundIntentViewVM.js',
		__ctxPath + '/js/creditFlow/finance/SlFundintentUrgeAllForm.js',
		__ctxPath + '/js/creditFlow/smallLoan/contract/OperateKxcsContractWindow.js',
		__ctxPath + '/js/creditFlow/finance/detailView.js'
	],
	/**
	 * 贷后监管or贷款展期or提前还款or利率变更or贷款结项or违约处理
	 * @type 
	 */
	FundLoanedProjectManager:[
		__ctxPath +"/js/creditFlow/fund/project/FundLoanedProjectManager.js",
		__ctxPath + '/js/creditFlow/smallLoan/project/DesignSupervisionManagePlan.js',
		__ctxPath + '/js/creditFlow/smallLoan/project/AddSupervisionRecord.js',
		__ctxPath + '/js/creditFlow/smallLoan/project/SlProjectFinished.js',
		__ctxPath + '/js/creditFlow/finance/SlFundIntentViewVM.js',
	    __ctxPath + '/js/creditFlow/leaseFinance/project/GlobalSupervisonRecordView.js'
	],
	/**
	 * 银行账户管理
	 * @type 
	 */
	SlBankAccountView : [
		__ctxPath + '/js/creditFlow/finance/SlBankAccountView.js',
		__ctxPath + '/js/creditFlow/finance/SlBankAccountForm.js',
		__ctxPath + '/js/creditFlow/finance/SlBankAccountQlide.js',
		__ctxPath + '/js/creditFlow/finance/SlBankAccountUpload.js',
		__ctxPath + '/js/creditFlow/finance/SelectBankList.js'
	],
	/**
	 * 银行流水录入
	 * @type 
	 */
	SlFundQlideView2 : [
		__ctxPath + '/js/creditFlow/finance/SlFundQlideView2.js',
		__ctxPath + '/js/creditFlow/finance/SlFundQlideForm.js',
		__ctxPath + '/js/creditFlow/finance/selectAccountlForm.js',
		__ctxPath + '/js/creditFlow/finance/SlFundQlideView.js',
		__ctxPath + '/js/creditFlow/finance/SlFundQlideAlotAdd.js',
		__ctxPath + '/js/creditFlow/finance/selectCustomAccount.js',
		__ctxPath + '/js/creditFlow/finance/SlFundQlideInternalForm.js'
	],
	/**
	 * 现金流水录入
	 * @type 
	 */
	CashQlideView : [
		__ctxPath + '/js/creditFlow/finance/CashQlideView.js',
		__ctxPath + '/js/creditFlow/finance/CashQlideForm.js',
		__ctxPath + '/js/creditFlow/finance/CashQlideAlotAdd.js'
	],
	/**
	 * 银行账户明细
	 * @type 
	 */
	SlBankAccountQlideView : [
		__ctxPath + '/js/creditFlow/finance/SlBankAccountQlideView.js',
		__ctxPath + '/js/creditFlow/finance/selectAccountlForm1.js'
	],
	/**
	 * 综合收款台账
	 * @type 
	 */
	synthesizeSlFundIntent:[
		__ctxPath+ '/js/creditFlow/finance/loanfinance/synthesizeSlFundIntent.js',
		__ctxPath + '/js/creditFlow/finance/SlFundIntentForm.js',
		__ctxPath + '/js/creditFlow/finance/SlFundIntentForm1.js',
		__ctxPath + '/js/creditFlow/finance/synthesizeditQlideCheck.js',
		__ctxPath + '/js/creditFlow/finance/editQlideCheck.js',
		__ctxPath + '/js/creditFlow/finance/SynthesizeSlFundIntentForm.js',
		__ctxPath + '/js/creditFlow/finance/detailView.js',
		__ctxPath + '/js/creditFlow/finance/selectAccountlForm.js'
	],
	/**
	 * 本金放款台账
	 * @type 
	 */
	TabSlFundIntentPrincipalPay : [
		__ctxPath + '/js/creditFlow/finance/loanfinance/TabSlFundIntentPrincipalPay.js',
		__ctxPath + '/js/creditFlow/finance/loanfinance/SlFundIntentPrincipalPayAlready.js',
		__ctxPath + '/js/creditFlow/finance/loanfinance/SlFundIntentPrincipalPayAll.js',
		__ctxPath + '/js/creditFlow/finance/loanfinance/SlFundIntentPrincipalPayShould.js',
		__ctxPath + '/js/creditFlow/finance/loanfinance/SlFundIntentPrincipalPayOwe.js',
		__ctxPath + '/js/creditFlow/finance/SlFundIntentForm.js',
		__ctxPath + '/js/creditFlow/finance/detailView.js',
		__ctxPath + '/js/creditFlow/finance/editAfterMoneyForm.js',
		__ctxPath + '/js/creditFlow/finance/editIsOverdueForm.js',
		__ctxPath + '/js/creditFlow/finance/CashCheck.js',
		__ctxPath + '/js/creditFlow/finance/editQlideCheck.js',
		__ctxPath + '/js/creditFlow/finance/SlFundIntentForm1.js',
		__ctxPath + '/js/creditFlow/finance/selectAccountlForm.js'
	],
	/**
	 * 本金收款台账
	 * @type 
	 */
	TabSlFundIntentprincipalIncome : [
		__ctxPath + '/js/creditFlow/finance/loanfinance/TabSlFundIntentprincipalIncome.js',
		__ctxPath + '/js/creditFlow/finance/loanfinance/SlFundIntentprincipalIncomeActual.js',
		__ctxPath + '/js/creditFlow/finance/loanfinance/SlFundIntentprincipalIncomeOwe.js',
		__ctxPath + '/js/creditFlow/finance/loanfinance/SlFundIntentprincipalIncomeAll.js',
		__ctxPath + '/js/creditFlow/finance/loanfinance/SlFundIntentprincipalIncomeShould.js',
		__ctxPath + '/js/creditFlow/finance/loanfinance/OverdueDetailByDay.js',
		__ctxPath + '/js/creditFlow/finance/loanfinance/OverdueDetailByPeriod.js',
		__ctxPath + '/js/creditFlow/finance/SlFundIntentForm.js',
		__ctxPath + '/js/creditFlow/finance/SlRepaymentView.js',
		__ctxPath + '/js/creditFlow/finance/detailView.js',
		__ctxPath + '/js/creditFlow/finance/editAfterMoneyForm.js',
		__ctxPath + '/js/creditFlow/finance/editIsOverdueForm.js',
		__ctxPath + '/js/creditFlow/finance/CashCheck.js',
		__ctxPath + '/js/creditFlow/finance/editQlideCheck.js',
		__ctxPath + '/js/creditFlow/finance/SlFundIntentForm1.js',
		__ctxPath + '/js/creditFlow/finance/selectAccountlForm.js',
		__ctxPath + '/js/creditFlow/finance/SlPunishInterestView.js',
		__ctxPath + '/js/creditFlow/finance/SlPunishInterestForm.js',
		__ctxPath + '/js/creditFlow/finance/SlPunishInterestForm1.js',
		__ctxPath + '/js/creditFlow/finance/punishEditQlideCheck.js',
		__ctxPath + '/js/creditFlow/finance/editPunishAfterMoneyForm.js',
		__ctxPath + '/js/creditFlow/finance/punishDetailView.js',
		__ctxPath+'/js/riskControl/creditInvestigation/person/InternalBadCreditForm.js'
	],
	/**
	 * 利息收款台账
	 * @type 
	 */
	TabSlFundIntentInterestIncome : [
		__ctxPath + '/js/creditFlow/finance/loanfinance/TabSlFundIntentInterestIncome.js',
		__ctxPath + '/js/creditFlow/finance/loanfinance/SlFundIntentInterestIncomeActual.js',
		__ctxPath + '/js/creditFlow/finance/loanfinance/SlFundIntentInterestIncomeShould.js',
		__ctxPath + '/js/creditFlow/finance/loanfinance/SlFundIntentInterestIncomeAll.js',
		__ctxPath + '/js/creditFlow/finance/loanfinance/SlFundIntentInterestIncomeOwe.js',
		__ctxPath + '/js/creditFlow/finance/SlFundIntentForm.js',
		__ctxPath + '/js/creditFlow/finance/SlRepaymentView.js',
		__ctxPath + '/js/creditFlow/finance/detailView.js',
		__ctxPath + '/js/creditFlow/finance/editAfterMoneyForm.js',
		__ctxPath + '/js/creditFlow/finance/editIsOverdueForm.js',
		__ctxPath + '/js/creditFlow/finance/CashCheck.js',
		__ctxPath + '/js/creditFlow/finance/editQlideCheck.js',
		__ctxPath + '/js/creditFlow/finance/SlFundIntentForm1.js',
		__ctxPath + '/js/creditFlow/finance/selectAccountlForm.js',
		__ctxPath + '/js/creditFlow/finance/SlPunishInterestView.js',
		__ctxPath + '/js/creditFlow/finance/SlPunishInterestForm.js',
		__ctxPath + '/js/creditFlow/finance/SlPunishInterestForm1.js',
		__ctxPath + '/js/creditFlow/finance/punishEditQlideCheck.js',
		__ctxPath + '/js/creditFlow/finance/editPunishAfterMoneyForm.js',
		__ctxPath + '/js/creditFlow/finance/punishDetailView.js',
		__ctxPath+'/js/riskControl/creditInvestigation/person/InternalBadCreditForm.js'
	],
	/**
	 * 管理咨询费台账
	 * @type 
	 */
	TabSlFundIntentconsultationMoney : [
		__ctxPath + '/js/creditFlow/finance/loanfinance/TabSlFundIntentconsultationMoney.js',
		__ctxPath + '/js/creditFlow/finance/loanfinance/SlFundIntentInterestIncomeActual.js',
		__ctxPath + '/js/creditFlow/finance/loanfinance/SlFundIntentInterestIncomeShould.js',
		__ctxPath + '/js/creditFlow/finance/loanfinance/SlFundIntentInterestIncomeAll.js',
		__ctxPath + '/js/creditFlow/finance/loanfinance/SlFundIntentInterestIncomeOwe.js',
		__ctxPath + '/js/creditFlow/finance/SlFundIntentForm.js',
		__ctxPath + '/js/creditFlow/finance/SlRepaymentView.js',
		__ctxPath + '/js/creditFlow/finance/detailView.js',
		__ctxPath + '/js/creditFlow/finance/editAfterMoneyForm.js',
		__ctxPath + '/js/creditFlow/finance/editIsOverdueForm.js',
		__ctxPath + '/js/creditFlow/finance/CashCheck.js',
		__ctxPath + '/js/creditFlow/finance/editQlideCheck.js',
		__ctxPath + '/js/creditFlow/finance/SlFundIntentForm1.js',
		__ctxPath + '/js/creditFlow/finance/selectAccountlForm.js',
		__ctxPath + '/js/creditFlow/finance/SlPunishInterestView.js',
		__ctxPath + '/js/creditFlow/finance/SlPunishInterestForm.js',
		__ctxPath + '/js/creditFlow/finance/SlPunishInterestForm1.js',
		__ctxPath + '/js/creditFlow/finance/punishEditQlideCheck.js',
		__ctxPath + '/js/creditFlow/finance/editPunishAfterMoneyForm.js',
		__ctxPath + '/js/creditFlow/finance/punishDetailView.js',
		__ctxPath+'/js/riskControl/creditInvestigation/person/InternalBadCreditForm.js'
	],
	/**
	 * 财务服务费台账
	 * @type 
	 */
	TabSlFundIntentServiceMoney : [
		__ctxPath + '/js/creditFlow/finance/loanfinance/TabSlFundIntentServiceMoney.js',
		__ctxPath + '/js/creditFlow/finance/loanfinance/SlFundIntentInterestIncomeActual.js',
		__ctxPath + '/js/creditFlow/finance/loanfinance/SlFundIntentInterestIncomeShould.js',
		__ctxPath + '/js/creditFlow/finance/loanfinance/SlFundIntentInterestIncomeAll.js',
		__ctxPath + '/js/creditFlow/finance/loanfinance/SlFundIntentInterestIncomeOwe.js',
		__ctxPath + '/js/creditFlow/finance/SlFundIntentForm.js',
		__ctxPath + '/js/creditFlow/finance/SlRepaymentView.js',
		__ctxPath + '/js/creditFlow/finance/detailView.js',
		__ctxPath + '/js/creditFlow/finance/editAfterMoneyForm.js',
		__ctxPath + '/js/creditFlow/finance/editIsOverdueForm.js',
		__ctxPath + '/js/creditFlow/finance/CashCheck.js',
		__ctxPath + '/js/creditFlow/finance/editQlideCheck.js',
		__ctxPath + '/js/creditFlow/finance/SlFundIntentForm1.js',
		__ctxPath + '/js/creditFlow/finance/selectAccountlForm.js',
		__ctxPath + '/js/creditFlow/finance/SlPunishInterestView.js',
		__ctxPath + '/js/creditFlow/finance/SlPunishInterestForm.js',
		__ctxPath + '/js/creditFlow/finance/SlPunishInterestForm1.js',
		__ctxPath + '/js/creditFlow/finance/punishEditQlideCheck.js',
		__ctxPath + '/js/creditFlow/finance/editPunishAfterMoneyForm.js',
		__ctxPath + '/js/creditFlow/finance/punishDetailView.js',
		__ctxPath+'/js/riskControl/creditInvestigation/person/InternalBadCreditForm.js'
	],
	/**
	 * 退费支出台账
	 * @type 
	 */
	TabSlFundIntentOtherPay : [
		__ctxPath + '/js/creditFlow/finance/loanfinance/TabSlFundIntentOtherPay.js',
		__ctxPath + '/js/creditFlow/finance/loanfinance/SlFundIntentOtherPayAlready.js',
		__ctxPath + '/js/creditFlow/finance/loanfinance/SlFundIntentOtherPayAll.js',
		__ctxPath + '/js/creditFlow/finance/loanfinance/SlFundIntentOtherPayShould.js',
		__ctxPath + '/js/creditFlow/finance/loanfinance/SlFundIntentOtherPayOwe.js',
		__ctxPath + '/js/creditFlow/finance/SlFundIntentForm.js',
		__ctxPath + '/js/creditFlow/finance/detailView.js',
		__ctxPath + '/js/creditFlow/finance/editAfterMoneyForm.js',
		__ctxPath + '/js/creditFlow/finance/editIsOverdueForm.js',
		__ctxPath + '/js/creditFlow/finance/CashCheck.js',
		__ctxPath + '/js/creditFlow/finance/editQlideCheck.js',
		__ctxPath + '/js/creditFlow/finance/SlFundIntentForm1.js',
		__ctxPath + '/js/creditFlow/finance/selectAccountlForm.js'
	],
	/**
	 * 费用收取台账
	 * @type 
	 */
	SlActualToChargeIncomeView : [
		__ctxPath + '/js/creditFlow/finance/loanfinance/SlActualToChargeIncomeView.js',
		__ctxPath + '/js/creditFlow/finance/SlActualToChargeForm.js',
		__ctxPath + '/js/creditFlow/finance/chargeDetailView.js',
		__ctxPath + '/js/creditFlow/finance/chargeeditAfterMoneyForm.js',
		__ctxPath + '/js/creditFlow/finance/chargeeditIsOverdueForm.js',
		__ctxPath + '/js/creditFlow/finance/chargeCashCheck.js',
		__ctxPath + '/js/creditFlow/finance/chargeeditQlideCheck.js',
		__ctxPath + '/js/creditFlow/finance/SlActualToChargeForm1.js',
		__ctxPath + '/js/creditFlow/finance/selectAccountlForm.js'
	],
	/**
	 * 本息收支对账日志
	 * @type 
	 */
	VFundDetail : [
		__ctxPath + '/js/creditFlow/finance/VFundDetail.js'
	],
	/**
	 * 费用收支对账日志
	 * @type 
	 */
	VChargeDetail : [
		__ctxPath + '/js/creditFlow/finance/VChargeDetail.js'
	],
	/**
	 * 合同模板制作
	 * @type 
	 */
	CreditDocumentManagerZW : [
		__ctxPath + '/js/creditFlow/document/CreditDocumentManagerZW.js'// developer
	 // __ctxPath +'/js/creditFlow/document/CreditDocumentManager.js'//customer
	],
	/**
	 * 合同管理
	 * @type 
	 */
	ContractManager : [
		__ctxPath + '/js/creditFlow/document/ContractManager.js',
		__ctxPath + '/js/creditFlow/smallLoan/contract/BatchSignThirdContractWindow.js',
		__ctxPath + '/js/creditFlow/mortgage/FinanceMortgageView.js',
		__ctxPath + '/js/creditFlow/mortgage/FinanceMortgageListView.js',
		__ctxPath + '/js/creditFlow/smallLoan/contract/mortgageContractView.js',
		__ctxPath + '/js/creditFlow/smallLoan/contract/SlGenerationContract.js',
		__ctxPath + '/js/creditFlow/smallLoan/contract/ContractMake.js',
		__ctxPath + '/js/creditFlow/smallLoan/contract/ContractMakeWay.js'
	],
	/**
	 * 合同查询
	 * @type 
	 */
	ContractQuery : [
		__ctxPath + '/js/creditFlow/document/ContractQuery.js'
	],
	/**
	 * 合同要素
	 * @type 
	 */
	CsElementCategoryView : [
    	__ctxPath+'/js/creditFlow/smallLoan/contract/CsElementCategoryView.js',
    	__ctxPath+'/js/creditFlow/smallLoan/contract/CsElementCategoryForm.js'
	],
	/**
	 * 押品手续办理
	 * @type 
	 */
	MortgageManagement : [
		__ctxPath + '/js/creditFlow/mortgage/MortgageManagement.js',
		__ctxPath + '/js/selector/UserDialog.js'
	],
	/**
	 * 项目归档管理
	 * @type 
	 */
	PlProjectArchivesView : [
		__ctxPath + '/js/creditFlow/archives/PlProjectArchivesView.js',
		__ctxPath + '/js/creditFlow/archives/PlProjectArchivesForm.js',
		__ctxPath + '/js/creditFlow/archives/selectArchiveschecker.js',
		__ctxPath + '/js/creditFlow/archives/GuaranteeArchives.js',
		__ctxPath + '/js/creditFlow/archives/LeaseFinanceArchives.js',
		__ctxPath + '/js/creditFlow/archives/PawnArchives.js',
		__ctxPath + '/js/creditFlow/archives/SmallLoanArchives.js'
	],
	/**
	 * 贷款产品设计
	 * @type 
	 */
	BpProductParameterView : [
       	__ctxPath+'/js/system/product/BpProductModule.js',
    	__ctxPath+'/js/system/product/loadDataCommonProduct.js',
    	__ctxPath+'/js/system/product/BpProductParameterView.js',
    	__ctxPath+'/js/system/product/BpProductParameterAddForm.js',
    	__ctxPath+'/js/system/product/BpProductParameterForm.js',
    	__ctxPath+'/js/system/product/BpProductParameterForm2.js',
    	__ctxPath+'/js/system/product/OurProcreditMaterialsViewProduct.js',
    	__ctxPath+'/js/system/product/addProductOwnMaterialForm.js',
    	__ctxPath+'/js/system/product/OurProcreditAssuretenetProductView.js',
    	__ctxPath+'/js/system/product/OurProcreditAssuretenetProductForm.js',
    	__ctxPath+'/js/system/product/AddProductOwnAssuretenetForm.js',
    	__ctxPath+'/js/system/product/SlPlanToChargeProduct.js',
    	__ctxPath+'/js/system/product/SlPlanToChargeProductForm.js',
    	__ctxPath+'/js/system/product/AddProductOwnPlanChargeForm.js',
    	__ctxPath+'/js/system/product/OurArchivesMaterialsProductView.js',
    	__ctxPath+'/js/system/product/OurArchivesMaterialProductForm.js',
    	__ctxPath+'/js/system/product/AddProductOwnArchivesFrom.js'
	],
	/**
	 * 基础贷款材料管理
	 * @type 
	 */
	OurSmallloanMaterialsView : [
		__ctxPath + '/js/creditFlow/smallLoan/materials/OurSmallloanMaterialsView.js',
		__ctxPath + '/js/creditFlow/smallLoan/materials/OurSmallloanMaterialsForm.js'
	]
};

Ext.applyIf(App.importJs,loanBusiness);