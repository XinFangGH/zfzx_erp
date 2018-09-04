/**
 * @author
 * @class PlMmOrderChildrenOrView
 * @extends Ext.Panel
 * @description [PlMmOrderChildrenOr]管理
 * @company 智维软件
 * @createtime:
 */
UPlanOrder = Ext.extend(Ext.Panel, {
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		// 初始化组件
		this.initUIComponents();
		// 调用父类构造
		UPlanOrder.superclass.constructor.call(this, {
					id : 'UPlanOrder' + this.keystr,
					title : '订单',
					layout : 'anchor',
					anchor : '100%',
					items : [this.gridPanel]
				});
	},// end of constructor
	// 初始化组件
	initUIComponents : function() {
		// 初始化搜索条件Panel
		
		//起息
		if(this.isHidden==false){
			var url=__ctxPath+ "/creditFlow/financingAgency/listbyPlanIdPlManageMoneyPlanBuyinfo.do?Q_keystr_S_EQ="
					+ this.keystr+"&state=1";
			if(typeof(this.mmplanId)!="undefined"){
				url=url+"&Q_plManageMoneyPlan.mmplanId_L_EQ="+this.mmplanId;
			}
		//查看
		}else{
			var url=__ctxPath+ "/creditFlow/financingAgency/listbyPlanIdPlManageMoneyPlanBuyinfo.do?Q_keystr_S_EQ="
					+ this.keystr+"&state=-2,1,2,7,8,10";
			if(typeof(this.mmplanId)!="undefined"){
				url=url+"&Q_plManageMoneyPlan.mmplanId_L_EQ="+this.mmplanId;
			}
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
					'currentGetedMoney', 'optimalDayRate', 'keystr', 'mmName','isAtuoMatch','firstProjectIdcount',
					'earlierOutDate','investType','couponsMoney'],
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
					},{
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
					},{
						header : '收益类型',
						dataIndex : 'investType',
						align : 'right',
						renderer : function(v) {
							var investType='';
							if(v=='1'){
								investType='收益再投资'
							}else{
								investType='提取主账户'
							}
							return investType;
									
						}
					},{
						header : '购买时间',
						dataIndex : 'buyDatetime',
						align : 'right',
						renderer : function(v) {
							return v;
									
						}
					},{
						header : '起息日期',
						dataIndex : 'startinInterestTime',
						align : 'right',
						renderer : function(v) {
							return v;
									
						}
					},{
						header : '退出日期',
						dataIndex : 'earlierOutDate',
						align : 'right',
						renderer : function(v) {
							return v;
									
						}
					},{
						header : '回款计划',
						dataIndex : 'mmName',
						sortable : true,
						align : "center",
						width : 100,
						renderer : function(data, metadata, record,rowIndex, columnIndex, store) {
			           		return '<a title="回款计划" style ="TEXT-DECORATION:underline;cursor:pointer" onclick="seeInvestFund(\''+ record.get('orderId') +'\',\''+record.get('investPersonName')+ '\')" >回款计划</a>';
			            }
					}]
				// end of columns
		});
	}
});

var seeInvestFund = function(orderId,investPersonName){
	new InverstPersonBackFund({
		orderId:orderId,
		investPersonName:investPersonName
	}).show();
};