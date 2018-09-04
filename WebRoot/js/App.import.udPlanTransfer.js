udPlanTransfer={
	/**
	 * 计划类型配置
	 * @type 
	 */
	UPlanTypeView:[
        __ctxPath+'/js/creditFlow/financingAgency/UPlan/UPlanTypeView.js',
        __ctxPath+'/js/creditFlow/financingAgency/UPlan/UPlanTypeForm.js'
	],
	/**
	 * 计划招标制定
	 * @type 
	 */
	UPlanView:[
        __ctxPath+'/js/creditFlow/financingAgency/UPlan/UPlanView.js',
        __ctxPath+'/js/creditFlow/financingAgency/UPlan/UPlanForm.js',
        __ctxPath+'/js/creditFlow/financingAgency/UPlan/UPlanOrder.js',
        __ctxPath+'/js/creditFlow/financingAgency/manageMoney/PlManageMoneyPlanCoupons.js',
        __ctxPath+'/js/p2p/materials/SlSubjectLogoUpload.js'
	],
	/**
	 * 计划招标预览or待预售计划or预售中计划or招标中计划台账or已满标计划台账or已到期计划台账or还款中计划台账or已完成计划台账or已关闭计划台账
	 * @type 
	 */
	UPlanBidPublish:[
	   __ctxPath+'/js/creditFlow/financingAgency/UPlan/UPlanBidPublish.js',
       __ctxPath+'/js/creditFlow/financingAgency/UPlan/UPlanForm.js',
       __ctxPath+'/js/creditFlow/financingAgency/UPlan/UPlanOrder.js',
       __ctxPath+'/js/creditFlow/financingAgency/manageMoney/PlManageMoneyPlanCoupons.js',
       __ctxPath+'/js/p2p/materials/SlSubjectLogoUpload.js'
	],
	/**
	 * 计划招标发布or理财计划关闭or手动起息办理or派息还款授权or理财结清管理or全部理财计划查询
	 * @type 
	 */
	UPlanBidPublishManage : [
       __ctxPath+'/js/creditFlow/financingAgency/UPlan/UPlanBidPublishManage.js',
       __ctxPath+'/js/creditFlow/financingAgency/UPlan/UPlanForm.js',
       __ctxPath+'/js/creditFlow/financingAgency/UPlan/UPlanOrder.js',
       __ctxPath+'/js/creditFlow/financingAgency/manageMoney/PlManageMoneyPlanCoupons.js',
       __ctxPath+'/js/p2p/materials/SlSubjectLogoUpload.js',
       __ctxPath+'/js/creditFlow/finance/ptp/InverstPersonBackFund.js'
	],
	/**
	 * 待审核申请or审核通过返款or已通过申请or已驳回申请
	 * @type 
	 */
	EarlyOutList:[
		__ctxPath+'/js/creditFlow/financingAgency/manageMoney/EarlyOutList.js',
		__ctxPath + '/js/creditFlow/financingAgency/manageMoney/EarlyOutDetail.js'
	],
	/**
	 * 投标记录查询
	 * @type 
	 */
	UPlanInfo :[
        __ctxPath+'/js/creditFlow/financingAgency/UPlan/UPlanInfo.js'
	],
	/**
	 * 债权余额查询
	 * @type 
	 */
	ObligatoryrightBalance:[
      __ctxPath+'/js/creditFlow/financingAgency/manageMoney/ObligatoryrightBalance.js'
    ]
};

Ext.applyIf(App.importJs,udPlanTransfer);