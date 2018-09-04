controlCentre={
	/**
	 * 流程任务综合监控
	 * @type 
	 */
	ProcessTaskComplexControl:[
		__ctxPath+'/js/riskControl/processTask/ProcessTaskComplexControl.js'
	],
	/**
	 * 流程任务合规性监控
	 * @type 
	 */
	ProcessTaskComplianceControl:[
		__ctxPath+'/js/riskControl/processTask/ProcessTaskComplianceControl.js'
	],
	/**
	 * 流程任务工作台监控
	 * @type 
	 */
	ProcessTaskWorkbenchControl:[
			__ctxPath+'/js/riskControl/processTask/ProcessTaskWorkbenchControl.js'
	],
	/**
	 * 黑名单客户
	 * @type 
	 */
	BlackList : [
		__ctxPath + '/js/creditFlow/customer/enterprise/BlackList.js'
	],
	/**
	 * 不良征信登记
	 * @type 
	 */
	BadCreditRecord : [
    	__ctxPath+'/js/riskControl/creditInvestigation/person/BadCreditRecord.js',
    	__ctxPath+'/js/riskControl/creditInvestigation/person/BadCreditForm.js'
	],
	/**
	 * 主借款人登记
	 * @type 
	 */
	PersonBrowerRecord : [
    	__ctxPath+'/js/riskControl/creditInvestigation/person/PersonBrowerRecord.js'
	],
	/**
	 * 保证人登记
	 * @type 
	 */
	PersonAssureRecord : [
    	__ctxPath+'/js/riskControl/creditInvestigation/person/PersonAssureRecord.js'
	],
	/**
	 * 法人借款登记
	 * @type 
	 */
	PersonLegalRecord : [
    	__ctxPath+'/js/riskControl/creditInvestigation/person/PersonLegalRecord.js'
	],
	/**
	 * 外部借贷登记
	 * @type 
	 */
	LoneExternalRecord : [
    	__ctxPath+'/js/riskControl/creditInvestigation/person/LoneExternalRecord.js',
    	__ctxPath+'/js/riskControl/creditInvestigation/person/LoneExternalForm.js'
	],
	/**
	 * 主借款人登记
	 * @type 
	 */
	EnterPriseBrowerRecord:[
		__ctxPath +"/js/riskControl/creditInvestigation/enterprise/EnterPriseBrowerRecord.js"
	],
	/**
	 * 保证人登记
	 * @type 
	 */
	EnterPriseAssureRecord:[
		__ctxPath +"/js/riskControl/creditInvestigation/enterprise/EnterPriseAssureRecord.js"
	],
	/**
	 * 定性指标管理
	 * @type 
	 */
	IndicatorManagement2 : [
		__ctxPath + '/js/creditFlow/guarantee/creditManagement/indicatorManagement2.js',
		__ctxPath + '/js/creditFlow/guarantee/creditManagement/optionsList2.js',
		__ctxPath + '/js/creditFlow/guarantee/creditManagement/indicatorstoreTree2.js'
	],
	/**
	 * 信用等级管理
	 * @type 
	 */
	ClassTypeView : [
		__ctxPath + '/js/creditFlow/guarantee/creditManagement/classTypeView.js',
		__ctxPath + '/js/creditFlow/guarantee/creditManagement/scoreGradeForm.js'
	],
	/**
	 * 评估模板管理
	 * @type 
	 */
	TemplateManagement : [
		__ctxPath + '/js/creditFlow/guarantee/creditManagement/optionManagement.js',
		__ctxPath+ '/js/creditFlow/guarantee/creditManagement/templateManagement.js'
	],
	/**
	 * 客户资信评估
	 * @type 
	 */
	CreditRatingManage : [
		__ctxPath + '/js/creditFlow/guarantee/creditManagement/creditRatingManage.js',
		__ctxPath + '/js/selector/UserDialog.js',
		__ctxPath + '/js/creditFlow/guarantee/creditManagement/creditRating.js'
	],
	/**
	 * 贷款五级分类
	 * @type 
	 */
	LoanFiveLevelClassification :[
  		__ctxPath +"/js/creditFlow/windControlCenter/loanControl/LoanFiveLevelClassification.js"
	],
	/**
	 * 贷后监管制定
	 * @type 
	 */
	PostLoanSupervision:[
		__ctxPath +"/js/creditFlow/windControlCenter/loanControl/PostLoanSupervision.js",
		__ctxPath + '/js/creditFlow/smallLoan/project/DesignSupervisionManagePlan.js',
		__ctxPath + '/js/creditFlow/smallLoan/project/AddSupervisionRecord.js',
		__ctxPath + '/js/creditFlow/smallLoan/project/SlProjectFinished.js',
		__ctxPath + '/js/creditFlow/finance/SlFundIntentViewVM.js',
	    __ctxPath + '/js/creditFlow/leaseFinance/project/GlobalSupervisonRecordView.js'
	],
	/**
	 * 贷后逾期预警
	 * @type 
	 */
	OverdueEarlyWarning :[
	   	__ctxPath +"/js/creditFlow/windControlCenter/loanControl/OverdueEarlyWarning.js",
	   	__ctxPath + '/js/creditFlow/smallLoan/project/DesignSupervisionManagePlan.js'
	],
	/**
	 * 行业预警
	 * @type 
	 */
	IndustryEarlyWarning :[
		__ctxPath +"/js/creditFlow/windControlCenter/loanControl/IndustryEarlyWarning.js"
	],
	/**
	 * 抵质押物
	 * @type 
	 */
	MortgageManagementQuery : [
		__ctxPath + '/js/creditFlow/mortgage/MortgageManagementQuery.js',
		__ctxPath + '/js/selector/UserDialog.js'
	],
	/**
	 * 贷款材料管理
	 * @type 
	 */
	RiskMaterialManager : [
    	__ctxPath+'/js/riskControl/materialManage/RiskMaterialManager.js',
		__ctxPath+'/js/riskControl/materialManage/ProjectListWin.js'
	],
	/**
	 * 归档材料管理
	 * @type 
	 */
	PlArchivesMaterialManager:[
		__ctxPath+'/js/riskControl/materialManage/PlArchivesMaterialManager.js',
		__ctxPath+'/js/riskControl/materialManage/ProjectListWin.js'
	]
};

Ext.applyIf(App.importJs,controlCentre);