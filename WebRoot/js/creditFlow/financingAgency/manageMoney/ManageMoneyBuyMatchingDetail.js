/**
 * @author:
 * @class SlCompanyMainView
 * @extends Ext.Panel
 * @description [SlCompanyMain]管理
 * @company 北京互融时代软件有限公司
 * @createtime:
 */
ManageMoneyBuyMatchingDetail = Ext.extend(Ext.Panel, {
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		// 初始化组件
		this.initUIComponents();
		// 调用父类构造
		ManageMoneyBuyMatchingDetail.superclass.constructor.call(this, {
			
			items : [this.gridPanel ]
		});
	},// end of constructor
	// 初始化组件
	initUIComponents : function() {
			this.gridPanel = new HT.GridPanel({
				showPaging : false,
				hiddenCm:true,
				autoHeight : true,
			region : 'center',
			url : __ctxPath
					+ "/creditFlow/financingAgency/listPlMmOrderChildrenOr.do?orderId="+this.orderId,
			fields : [{
						name : 'matchId',
						type : 'int'
					}, 'orderId', 'childrenorId', 'investPersonId',
					'investPersonName', 'mmplanId', 'mmName', 'parentOrBidId',
					'parentOrBidName', 'matchingMoney', 'childrenOrDayRate',
					'matchingStartDate', 'matchingEndDate', 'matchingLimit',
					'matchingEndDateType', 'matchingGetMoney', 'matchingState', 'matchPersonId', 'matchPersonName'],
			columns : [{
						header : 'matchId',
						dataIndex : 'matchId',
						hidden : true
					}, {
						header : '债权的名称',
						dataIndex : 'parentOrBidName'
					}, {
						header : '匹配金额',
						dataIndex : 'matchingMoney',
						align : 'right',
						renderer:function(v){
							return Ext.util.Format.number(v,',000,000,000.00')+"元";
						}
					}, {
						header : '债权的日化利率',
						dataIndex : 'childrenOrDayRate',
						renderer:function(v){
								return v+"%";
						}
					}, {
						header : '匹配开始日',
						dataIndex : 'matchingStartDate'
					}, {
						header : '匹配结束日',
						dataIndex : 'matchingEndDate'
					}, {
						header : '匹配期限',
						dataIndex : 'matchingLimit',
						align : 'right',
						renderer:function(v){
								return v+"天";
						}
					}, {
						header : '匹配收益',
						dataIndex : 'matchingGetMoney',
						align : 'right',
						renderer:function(v){
							return Ext.util.Format.number(v,',000,000,000.00')+"元";
						}
					}, {
						header : '匹配人',
						dataIndex : 'matchPersonName',
						align : 'center',
						renderer:function(v){
							if(Ext.isEmpty(v)){
							   return "系统"
							}else{
							  return v;
							}
						}
					}]
				// end of columns
		});
	}// end of the initComponents()
	
});
