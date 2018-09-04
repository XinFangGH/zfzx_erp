/**
 * @author
 * @class PlMmOrderChildrenOrView
 * @extends Ext.Panel
 * @description [PlMmOrderChildrenOr]管理
 * @company 智维软件
 * @createtime:
 */
PlManageMoneyPlanOrder = Ext.extend(Ext.Panel, {
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		// 初始化组件
		this.initUIComponents();
		// 调用父类构造
		PlManageMoneyPlanOrder.superclass.constructor.call(this, {
					id : 'PlManageMoneyPlanOrder' + this.keystr,
					title : '订单',
					layout : 'anchor',
					anchor : '100%',
					items : [this.gridPanel]
				});
	},// end of constructor
	// 初始化组件
	initUIComponents : function() {
		// 初始化搜索条件Panel
		
		var url=__ctxPath+ "/creditFlow/financingAgency/listbyPlanIdPlManageMoneyPlanBuyinfo.do?Q_keystr_S_EQ="
				+ this.keystr+"&Q_state_SN_GE=1";
		if(typeof(this.mmplanId)!="undefined"){
			url=url+"&Q_plManageMoneyPlan.mmplanId_L_EQ="+this.mmplanId;
		}
		
		this.gridPanel = new HT.GridPanel({
			region : 'center',
			tbar : this.topbar,
	//		singleSelect : true,
			// 使用RowActions
			id : 'PlManageMoneyPlanOrder' + this.keystr,
			height : 180,
			showPaging:false,
			url : url,
			fields : [{
						name : 'orderId',
						type : 'int'
					}, 'mmplanId', 'buyDatetime', 'investPersonId',
					'investPersonName', 'buyMoney', 'startinInterestTime',
					'endinInterestTime', 'orderlimit', 'promisDayRate',
					'promisIncomeSum', 'currentMatchingMoney',
					'currentGetedMoney', 'optimalDayRate', 'keystr', 'mmName','isAtuoMatch','firstProjectIdcount','couponsMoney'],
			columns : [{
						header : 'matchId',
						dataIndex : 'matchId',
						hidden : true
					}, {
						header : '投资人',
						summaryType : 'count',
						dataIndex : 'investPersonName'
					}, {
						header : '购买金额',
						dataIndex : 'buyMoney',
						align : 'right',
						renderer : function(v) {
							return Ext.util.Format.number(v, ',000,000,000.00')
									+ "元";
						}
					}, {
						header : '优惠券面值',
						dataIndex : 'couponsMoney',
						align : 'right',
						renderer : function(v) {
							if(v!=""&&v!=null){
								return Ext.util.Format.number(v, ',000,000,000.00')+ "元/%";
							}else{
								return "---";
							}
						}
					}, {
						header : '购买时间',
						dataIndex : 'buyDatetime',
						align : 'right',
						renderer : function(v) {
							return v;
									
						}
					}]
				// end of columns
		});

	}

	
});
