/**
 * @author 
 * @createtime 
 * @class InverstPersonBpFundIntent
 * @extends Ext.Window
 * @description 投资人回款计划
 * @company 智维软件
 */
InverstPersonBackFund = Ext.extend(Ext.Window, {
		//构造函数
		constructor : function(_cfg) {
			Ext.applyIf(this, _cfg);
			//必须先初始化组件
			this.initUIComponents();
			InverstPersonBackFund.superclass.constructor.call(this, {
				 layout : 'fit',
				 items : this.gridPanel,
				 modal : true,
				 height : 500,
				 width : 1000,
				 autoScroll : true,
				 title : this.investPersonName+'回款计划'
			});
		},//end of the constructor
		//初始化组件
		initUIComponents : function() {
			url=__ctxPath + "/creditFlow/financingAgency/findListByOrderIdPlMmOrderAssignInterest.do?orderId="+this.orderId;
			var summary = new Ext.ux.grid.GridSummary();
			function totalMoney(v, params, data) {
				return '总计(元)';
			}
			this.projectFundsm = new Ext.grid.CheckboxSelectionModel({
				header : '序号'
			});
			this.gridPanel = new HT.GridPanel({
				bbar : false,
				showPaging : false,
				sm : this.projectFundsm,
				url:url,
				isautoLoad:true,
				plugins : [summary],
				fields : [{
						name : 'assignInterestId',
						type : 'int'
					}, 'orderId', 'fundType', 'incomeMoney','afterMoney','factDate','intentDate','periods','payMoney'],
				columns : [{
						header : 'assignInterestId',
						dataIndex : 'assignInterestId',
						hidden : true
					},{
						header : '期数',
						dataIndex : 'periods',
						renderer:function(v){
							if(v==null){
								return "";
							}else{
								return "第"+v+"期"
							}
						}
					}, {
						header : '资金类型',
						dataIndex : 'fundType',
						renderer:function(v){
							if(v=="loanInterest"){
							   return "利息"
							}else if(v=="riskRate"){
							   return "风险金"
							}else if(v=="liquidatedDamages"){
							   return "提前赎回违约金"
							}else {
							   return "本金";
							}
						},
						summaryRenderer : totalMoney
					}, {
						header : '收入金额',
						dataIndex : 'incomeMoney',
						align : 'right',
						summaryType : 'sum',
						renderer:function(v){
							return Ext.util.Format.number(v,',000,000,000.00')+"元";
						}
					}, {
						header : '支出金额',
						dataIndex : 'payMoney',
						align : 'right',
						summaryType : 'sum',
						renderer:function(v){
							return Ext.util.Format.number(v,',000,000,000.00')+"元";
						}
					}, {
						header : '计划派息日期',
						dataIndex : 'intentDate'
					}, {
						header : '已派金额',
						dataIndex : 'afterMoney',
						align : 'right',
						summaryType : 'sum',
						renderer:function(v){
							return Ext.util.Format.number(v,',000,000,000.00')+"元";
						}
					}, {
						header : '实际派息日期',
						dataIndex : 'factDate'
					}]
			});
		}
});