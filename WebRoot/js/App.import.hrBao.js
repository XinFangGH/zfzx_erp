hrBao={
	/**
	 * 理财参数设置
	 * @type 
	 */
	PlFinanceProductForm:[
		__ctxPath+'/js/financeProduct/PlFinanceProductForm.js'
	],
	/**
	 * 基金收益率设置
	 * @type 
	 */
	PlFinanceProductRate:[
		__ctxPath+'/js/financeProduct/PlFinanceProductRate.js',
		__ctxPath+'/js/financeProduct/PlFinanceProductRateForm.js',
		__ctxPath+'/js/financeProduct/PlFinanceProductRateUpdateForm.js',
		__ctxPath+'/js/financeProduct/PlFianceProductIntentDay.js'
	],
	/**
	 * 活期业务账户查询or期业务账户锁定or活期业务账户解锁
	 * @type 
	 */
	PlFinanceProductUserAccount:[
	    __ctxPath+'/js/financeProduct/PlFinanceProductUserAccount.js'
	],
	/**
	 * 账户交易查询or转入记录台账or转出交易台账
	 * @type 
	 */
	PlFinanceProductUserAccountInfo:[
		__ctxPath+'/js/financeProduct/PlFinanceProductUserAccountInfo.js'
	],
	/**
	 * 异常交易台账
	 * @type 
	 */	
	PlFinanceProductExceptionDealView:[
		__ctxPath+'/js/financeProduct/PlFinanceProductExceptionDealView.js'
	],
	/**
	 * 派息记录查询
	 * @type 
	 */
	PlFinanceProductIntentView:[
		__ctxPath+'/js/financeProduct/PlFinanceProductIntentView.js',
		__ctxPath+'/js/financeProduct/PlFianceProductIntentDay.js'
	]
};

Ext.applyIf(App.importJs,hrBao);