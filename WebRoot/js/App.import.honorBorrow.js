honorBorrow={
	/**
	 * 基础材料配置
	 * @type 
	 */
	P2pLoanBasisMaterialView:[
       __ctxPath+'/js/p2p/loan/P2pLoanBasisMaterialView.js',
       __ctxPath+'/js/p2p/loan/P2pAddBasisMaterial.js'
	],
	/**
	 * 新增信贷产品
	 * @type 
	 */
	P2pAddProduct:[
		__ctxPath+'/js/p2p/loan/P2pAddProduct.js'
	],
	/**
	 * 产品参数配置or申请步骤配置or信贷产品发布or信贷产品关闭or信贷产品启用or信贷产品查询
	 * @type 
	 */
	P2pLoanProductView:[
       __ctxPath+'/js/p2p/loan/P2pLoanProductView.js',
       __ctxPath+'/js/p2p/loan/P2pLoanFlowSetView.js',
       __ctxPath+'/js/p2p/loan/P2pAddProduct.js',
       __ctxPath+'/js/p2p/loan/P2pUpdateProduct.js',
       __ctxPath+'/js/p2p/loan/P2pDeployProductForm.js',
       __ctxPath+'/js/system/product/OurProcreditMaterialsViewProduct.js',
       __ctxPath+'/js/system/product/OurProcreditAssuretenetProductView.js',
       __ctxPath+'/js/system/product/OurProcreditAssuretenetProductForm.js',
       __ctxPath+'/js/system/product/OurArchivesMaterialsProductView.js',
       __ctxPath+'/js/system/product/SlPlanToChargeProduct.js',
       __ctxPath+'/js/p2p/loan/P2pLoanProductModule.js',
       __ctxPath+'/js/p2p/loan/P2pLoanRateView.js',
       __ctxPath+'/js/p2p/loan/P2pLoanConditionOrMaterialProductView.js', 
       __ctxPath+'/js/p2p/loan/P2pLoanConditionOrMaterialProductForm.js', 
       __ctxPath+'/js/p2p/loan/P2pAddBasisMaterialProduct.js', 
       __ctxPath+'/js/p2p/loan/loadDataP2pProduct.js', 
       __ctxPath+'/js/p2p/loan/P2pLoanRateForm.js'
	],
	/**
	 * 借款申请受理or借款额度审批or已打回借款申请or已终止借款申请or已通过借款申请
	 * @type 
	 */
	BpFinanceApplyUserTypeView:[
	__ctxPath+'/js/p2p/BpFinanceApplyUserTypeView.js',
	__ctxPath+'/js/p2p/P2PBpCustMemberForm.js',
	__ctxPath + '/js/commonFlow/CreateNewProjectFrom.js',
	__ctxPath + '/js/creditFlow/customer/enterprise/public.js',
	__ctxPath + '/js/creditFlow/common/EnterpriseShareequity.js',
	__ctxPath + '/js/commonFlow/NewProjectFormEnterPrise.js',
	__ctxPath + '/js/selector/UserDialog.js',
	__ctxPath + '/js/commonFlow/ExtUD.Ext.js',
	__ctxPath + '/js/creditFlow/report/SlCompanyInfoView.js',
	__ctxPath + '/js/creditFlow/customer/enterprise/public.js',
	__ctxPath + '/js/creditFlow/common/EnterpriseShareequity.js',
	__ctxPath + '/js/creditFlow/creditAssignment/customer/shop.js',
	__ctxPath + '/js/p2p/BpFinanceApplyUserTypeForm.js'
	],
	/**
	 * 客户资料审核
	 * @type  
	 */
	WebFinanceApplyUploadsView : [
    	__ctxPath+'/js/p2p/WebFinanceApplyUploadsView.js',
    	__ctxPath+'/js/p2p/WebFinanceApplyUploadsForm.js',
    	__ctxPath+'/js/p2p/materials/BpCustMemberPicView.js',
    	__ctxPath+'/js/p2p/alerdyUpload.js',
    	__ctxPath+'/js/p2p/uploadCertification.js',
    	__ctxPath+'/js/p2p/uploadReject.js',
    	__ctxPath+'/js/p2p/appPicView.js'
	],
	/**
	 * 用户档案同步
	 * @type 
	 */
	BpCustMemberToPersonView : [
    	__ctxPath+'/js/p2p/BpCustMemberToPersonView.js',
    	__ctxPath+'/js/p2p/UpdatePassword.js',
    	__ctxPath+'/js/p2p/BpCustMemberForm.js'
	] 
	/**
	 * P2P------>借款项目管理or贷后信息披露
	 * @type 
	 */
	,BpPersionDirProView : [
    	__ctxPath+'/js/creditFlow/financingAgency/persion/BpPersionDirProView.js',
    	__ctxPath+'/js/creditFlow/financingAgency/persion/BpPersionDirProForm.js'
	],
	/**
	 * 公示信息包装
	 * @type 
	 */
	PlPersionProKeepTab : [
    	__ctxPath+'/js/creditFlow/financingAgency/persion/BpPersionDirProStat0View.js',
    	__ctxPath+'/js/creditFlow/financingAgency/persion/BpPersionDirProAllView.js',
    	__ctxPath+'/js/creditFlow/financingAgency/persion/PlPersionDirProKeepView.js',
    	__ctxPath+'/js/creditFlow/financingAgency/persion/PlPersionDirProKeepForm.js',
    	__ctxPath+'/js/creditFlow/financingAgency/persion/PlPersionDirProKeepOnlineForm.js',
    	__ctxPath+'/js/creditFlow/financingAgency/persion/P2POnlineUploadView.js',
    	__ctxPath+'/js/creditFlow/financingAgency/persion/P2pShowOnlineMaterialsForm.js',
    	__ctxPath+'/js/selector/OrOrzSelector.js',
    	__ctxPath + '/js/creditFlow/guarantee/materials/SlEnterPriseProcreditMaterialsView.js',
    	__ctxPath+'/js/creditFlow/financingAgency/persion/PlPersionProKeepTab.js',
    	__ctxPath+'/js/creditFlow/financingAgency/persion/BpPersionOrProAllView.js',
    	__ctxPath+'/js/creditFlow/financingAgency/persion/BpPersionOrProStat0View.js',
    	__ctxPath+'/js/creditFlow/financingAgency/persion/PlPersionOrProKeepView.js',
    	__ctxPath+'/js/p2p/materials/P2pShowProcreditMaterialsView.js',
    	__ctxPath+'/js/p2p/materials/P2pShowOnlineMaterialsView.js',
    	__ctxPath+'/js/p2p/materials/P2pFileUpload.js',
    	__ctxPath+'/js/p2p/materials/P2pMaterialsDownLoad.js',
    	__ctxPath+'/js/p2p/materials/SlSubjectLogoUpload.js'
	],
	/**
	 * 拆标方案制定or拆标方案制定
	 * @type 
	 */
	PlPersionDirPlanView : [
		__ctxPath+'/js/creditFlow/financingAgency/persion/PlPersionDirPlanView.js',
		__ctxPath+'/js/creditFlow/financingAgency/persion/PlPersionDirPlanForm.js',
		__ctxPath+'/js/creditFlow/financingAgency/persion/PlPersionDirBidListForm.js',
		__ctxPath+'/js/creditFlow/financingAgency/BidPlanInfoEditForm.js'
	],
	/**
	 * 招标页面预览or正式对外发布or等待预售清单or正在预售清单or自动投标管理or招标关闭处理or贷款结清管理or
	 * 招标中标的台账or已满标标的台账or已到期标的台账or起息中标的台账or还款中标的台账or已完成标的台账or
	 * 已流标标的台账or已关闭标的台账
	 * @type 
	 */
	PlPersionDirBidPublish:[
		__ctxPath+'/js/creditFlow/financingAgency/persion/PlPersionOrBidPublish.js',
		__ctxPath+'/js/creditFlow/financingAgency/persion/PlPersionDirBidPublish.js',
		__ctxPath+'/js/creditFlow/financingAgency/persion/PlPersionBidPublishTab.js',
		__ctxPath+'/js/creditFlow/financingAgency/business/PlBusinessOrBidPublish.js',
		__ctxPath+'/js/creditFlow/finance/CusterFundIntentView.js'
	],
	/**
	 * 标的流标处理or满标放款申请or标的状态查询
	 * @type 
	 */
	PlPersionDirBidPlanView:[
		__ctxPath+'/js/creditFlow/financingAgency/persion/PlPersionDirBidPlanView.js',
		__ctxPath+'/js/creditFlow/finance/CusterFundIntentView.js'
	],
	/**
	 * 收费方案制定or制作法律文书or合同放款确认
	 * @type 
	 */
	TaskManager : [
		__ctxPath + '/js/flow/TaskDueDateWindow.js',
		__ctxPath + '/js/flow/TaskHandlerWindow.js',
		__ctxPath + '/js/flow/TaskManager.js',
		__ctxPath + '/js/flow/ProcessNextForm.js',
		__ctxPath + '/js/flow/PathChangeWindow.js'
	],
	/**
	 * 授权还款管理or还款情况查询
	 * @type 
	 */
	SlFundIntentPeriodView : [
    	__ctxPath+'/js/creditFlow/finance/fundintentmerge/SlFundIntentPeriodView.js',
    	__ctxPath+'/js/creditFlow/finance/fundintentmerge/BpFundIntentPeriodView.js'
	],
	/**
	 * 逾期还款提醒or逾期代偿办理
	 * @type 
	 */
	compensatoryManager:[
    	__ctxPath+'/js/creditFlow/finance/overdueManager/compensatoryManager.js'
	],
	/**
	 * 代偿追偿提醒or代偿回收登记or代偿回收查询
	 * @type 
	 */
	compensatoryFiance:[
    	__ctxPath+'/js/creditFlow/finance/overdueManager/compensatoryFiance.js',
    	__ctxPath+'/js/creditFlow/finance/overdueManager/backCompensatoryRecord.js',
    	__ctxPath+'/js/creditFlow/finance/overdueManager/compensatoryCommon.js',
    	__ctxPath+'/js/creditFlow/finance/overdueManager/backCompensatoryFlow.js'
	],
	/**
	 * 成功追偿查询
	 * @type 
	 */
	backCompencsatoryFiance:[
		__ctxPath+'/js/creditFlow/finance/overdueManager/backCompencsatoryFiance.js',
    	__ctxPath+'/js/creditFlow/finance/overdueManager/compensatoryCommon.js',
    	__ctxPath+'/js/creditFlow/finance/overdueManager/backCompensatoryFlow.js'
	],
	/**
	 * 提前还款查询
	 * @type 
	 */
	BidPrepaymentListView:[
	    __ctxPath + '/js/creditFlow/financingAgency/loanedManagement/BidPrepaymentListView.js',
	    __ctxPath+"/js/creditFlow/finance/SeeFundIntentView.js",
		__ctxPath + '/js/creditFlow/finance/BpFundIntentFapView.js',
		__ctxPath + '/js/creditFlow/finance/CusterFundIntentView.js'
	]
	/**
	 * 散标返款管理
	 * @type 
	 */
	,SlFundIntentPeriodInvestView:[
		__ctxPath+'/js/creditFlow/finance/fundintentmerge/SlFundIntentPeriodInvestView.js'
	],
	/**
	 * C2P------->借款项目管理or贷后信息披露
	 * @type 
	 */
	BpPersionOrProView : [
    	__ctxPath+'/js/creditFlow/financingAgency/persion/BpPersionOrProView.js',
    	__ctxPath+'/js/creditFlow/financingAgency/persion/BpPersionOrProForm.js'
	],
	/**
	 * 招标页面预览or正式对外发布or等待预售清单or正在预售清单or招标关闭处理or自动投标管理
	 * 招标中标的台账or已满标标的台账or已到期标的台账or起息中标的台账or还款中标的台账or已完成标的台账or
	 * 已流标标的台账or已关闭标的台账
	 * @type 
	 */
	PlPersionOrBidPublish:[
		__ctxPath+'/js/creditFlow/financingAgency/persion/PlPersionOrBidPublish.js',
		__ctxPath+'/js/creditFlow/financingAgency/persion/PlPersionDirBidPublish.js',
		__ctxPath+'/js/creditFlow/financingAgency/persion/PlPersionBidPublishTab.js',
		__ctxPath+'/js/creditFlow/financingAgency/business/PlBusinessOrBidPublish.js',
		__ctxPath+'/js/creditFlow/finance/CusterFundIntentView.js'
	],
	/**
	 * PA2P----->借款项目管理or贷后信息披露
	 * @type 
	 */
	BpBusinessDirProView : [
    	__ctxPath+'/js/creditFlow/financingAgency/business/BpBusinessDirProView.js',
    	__ctxPath+'/js/creditFlow/financingAgency/business/BpBusinessDirProForm.js'
	],
	/**
	 * 公示信息包装
	 * @type 
	 */
	PlBusinessProKeepTab : [
    	__ctxPath+'/js/creditFlow/financingAgency/business/BpBusinessDirProStat0View.js',
    	__ctxPath+'/js/creditFlow/financingAgency/business/BpBusinessDirProAllView.js',
    	__ctxPath+'/js/creditFlow/financingAgency/business/BpOperateKxcsContractWindow.js',
    	__ctxPath+'/js/creditFlow/financingAgency/business/PlBusinessDirProKeepView.js',
    	__ctxPath+'/js/creditFlow/financingAgency/business/PlBusinessDirProKeepForm.js',
    	__ctxPath+'/js/selector/OrOrzSelector.js',
    	__ctxPath + '/js/creditFlow/guarantee/materials/SlEnterPriseProcreditMaterialsView.js',
    	__ctxPath+'/js/creditFlow/financingAgency/business/PlBusinessProKeepTab.js',
    	__ctxPath+'/js/creditFlow/financingAgency/business/BpBusinessOrProStat0View.js',
    	__ctxPath+'/js/creditFlow/financingAgency/business/BpBusinessOrProAllView.js',
    	__ctxPath+'/js/creditFlow/financingAgency/business/PlBusinessOrProKeepView.js',
    	__ctxPath+'/js/p2p/materials/P2pShowProcreditMaterialsView.js',
    	__ctxPath+'/js/p2p/materials/P2pFileUpload.js',
    	__ctxPath+'/js/p2p/materials/P2pMaterialsDownLoad.js',
    	__ctxPath+'/js/p2p/materials/SlSubjectLogoUpload.js'
	]
	/**
	 * 招标页面预览or正式对外发布or等待预售清单or正在预售清单or自动投标管理or招标关闭处理or贷款结清管理or招标中标的台账
	 * 已满标标的台账or已到期标的台账or起息中标的台账or还款中标的台账or已流标标的台账or已关闭标的台账or已完成标的台账
	 * @type 
	 */
	,PlBusinessDirBidPublish:[
		__ctxPath+'/js/creditFlow/financingAgency/business/PlBusinessDirBidPublish.js',
		__ctxPath+'/js/creditFlow/finance/CusterFundIntentView.js'
	]
	/**
	 * CA2P----->借款项目管理or贷后信息披露
	 * @type 
	 */
	,BpBusinessOrProView : [
    	__ctxPath+'/js/creditFlow/financingAgency/business/BpBusinessOrProView.js',
    	__ctxPath+'/js/creditFlow/financingAgency/business/BpBusinessOrProForm.js'
	]
	/**
	 * 招标页面预览or正式对外发布or等待预售清单or正在预售清单or自动投标管理or招标关闭处理or贷款结清管理or招标中标的台账
	 * 已满标标的台账or已到期标的台账or起息中标的台账or还款中标的台账or已流标标的台账or已关闭标的台账or已完成标的台账
	 * @type 
	 */
	,PlBusinessOrBidPublish:[
		__ctxPath+'/js/creditFlow/financingAgency/business/PlBusinessOrBidPublish.js',
		__ctxPath+'/js/creditFlow/finance/CusterFundIntentView.js'
	]

};

Ext.applyIf(App.importJs,honorBorrow);