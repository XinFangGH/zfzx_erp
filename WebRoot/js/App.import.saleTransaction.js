saleTransaction={
	/**
	 * 全部债权查询
	 * @type 
	 */
	allPlBidSaleList : [
		__ctxPath+'/js/creditFlow/financingAgency/sale/allPlBidSaleList.js'
	],
	/**
	 * 交易中债权查询 or 取消挂牌交易
	 * @type 
	 */
	transferingList : [
		__ctxPath+'/js/creditFlow/financingAgency/sale/transferingList.js'
	]
	/**
	 * 已关闭债权查询
	 * @type 
	 */
	,closeedList : [
		__ctxPath+'/js/creditFlow/financingAgency/sale/closeedList.js'
	],
	/**
	 * 正在交易债权查询
	 * @type 
	 */
	ingTransferList : [
		__ctxPath+'/js/creditFlow/financingAgency/sale/ingTransferList.js'
	],
	/**
	 * 交易成功债权查询or确认实收手续费or交易服务费记录
	 * @type 
	 */
	transferedList : [
    	__ctxPath+'/js/creditFlow/financingAgency/sale/transferedList.js',
    	__ctxPath+'/js/creditFlow/financingAgency/sale/ConfirmTransferFeeWindow.js'
	],
	/**
	 * 已转到债权库查询
	 * @type 
	 */
	transfereToOrList : [
		__ctxPath+'/js/creditFlow/financingAgency/sale/transfereToOrList.js',
		__ctxPath+'/js/creditFlow/financingAgency/sale/ConfirmTransferFeeWindow.js'
	],
	/**
	 * 债权交易返款
	 * @type 
	 */
	returnTransfered : [
    	__ctxPath+'/js/creditFlow/financingAgency/sale/returnTransfered.js'
	]
	
};

Ext.applyIf(App.importJs,saleTransaction);