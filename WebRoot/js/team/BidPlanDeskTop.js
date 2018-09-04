/**
 * 散标个人桌面页面
 * @class BidPlanDeskTop
 * @extends Ext.Panel
 */
BidPlanDeskTop = Ext.extend(Ext.Panel, {
	info:null,
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		this.initUIComponents();
		BidPlanDeskTop.superclass.constructor.call(this, {
			title : '散标投资桌面',
			closable : false,
			id : 'BidPlanDeskTop',
			border : false,
			iconCls : 'menu-desktop',
			html:this.info
		});
	},
	initUIComponents : function() {
		this.info=readHTML();
		function readHTML(){
			var temp="敬请期待";
			$.ajax({
				async : false,
				url : "js/team/html/bidPlanDeskTop.html",
				success : function(result) {
					temp=result;
				}
			});
			return temp;
		}
		
		
		
		
		
		/**
		 * (1)加载散标异常情况统计图
		 */
		//查询条件查询
		bidExceptionMapData=function(year){
			BidExceptionMap.ajax1("#BidExceptionMap",year);
		}
		
		try{
			BidExceptionMap.ajax1("#BidExceptionMap");
		}catch(err){
		
		}
		
		/**
		 * (2)加载充值取现的趋势图
		 */
		
		//查询条件查询
		changeTrendData=function(year){
			BidPlanCharts.ajax2("#rechargeAndWithdrawTrend",year);
		}
		
		try{
			BidPlanCharts.ajax2("#rechargeAndWithdrawTrend");
		}catch(err){
		
		}
		
		
		
		/**
		 * (3)加载散标类型的饼图
		 */
		
		//查询条件查询
		changeBidTypeData=function(year){
			BidTypeProportion.ajax3("#BidTypeProportion",year);
		}
		
		
		//加载月度成交统计
		try{
			BidTypeProportion.ajax3("#BidTypeProportion");
		}catch(err){
		
		}
		
		
		/**
		 * (4)加载散标的销售趋势图
		 */
		
		bidSaleStatisticsData =function(year){
			BidSaleStatistics.ajax4("#BidSaleStatistics",year);
		}
		try{
			BidSaleStatistics.ajax4("#BidSaleStatistics");
		}catch(err){
		
		}
		
		
		test();
		function test(){
			Ext.Ajax.request({
				url : __ctxPath + '/highchart/getRegUserHighchart.do',
				method : 'POST',
				scope:this,
				success : function(response, request) {
						var dataJson = Ext.util.JSON.decode(response.responseText).result;
						var dataUser = dataJson[0];
						var isCheckCard = dataJson[1];
						//新增注册人数
						$("#adduser").empty().append(dataUser.sumA);
						//环比
						var useruserhb = $("#useruserhb");
						if(dataUser.percentA>0){
							useruserhb.attr("class","top-arrow");
							if(dataUser.percentA>999){
								useruserhb.empty().after("999+%");
							}else{
								useruserhb.empty().after(dataUser.percentA+"%");
							}
						}else if(dataUser.percentA==0){
							useruserhb.attr("class","flat-arrow");
						}else{
							useruserhb.attr("class","down-arrow");
							if(dataUser.percentA<-999){
								useruserhb.empty().after("999+%");
							}else{
								useruserhb.empty().after(dataUser.percentA+"%");
							}
						} 
						//实名认证人数
						$("#isCheckCard").empty().append(isCheckCard.sumB);
						//环比
						var isCheckCardhb = $("#isCheckCardhb");
						if(isCheckCard.percentB>0){
							isCheckCardhb.attr("class","top-arrow");
							if(isCheckCard.percentB>999){
								isCheckCardhb.empty().after("999+%");
							}else{
								isCheckCardhb.empty().after(isCheckCard.percentB+"%");
							}
							
						}else if(isCheckCard.percentB==0){
							isCheckCardhb.attr("class","flat-arrow");
						}else{
							isCheckCardhb.attr("class","down-arrow");
							if(isCheckCard.percentB<-999){
								isCheckCardhb.empty().after("999+%");
							}else{
								isCheckCardhb.empty().after(isCheckCard.percentB+"%");
							}
						} 
				},
				failure : function(response,request) {
					Ext.ux.Toast.msg('操作信息', '查询任务完成时限操作失败!');
				}
			});
			
			Ext.Ajax.request({
				url : __ctxPath + '/highchart/getObAccountDealHighchart.do',
				method:'POST',
				scope:this,
				success:function(response,request){
					var dataJson = Ext.util.JSON.decode(response.responseText).result;
					//充值
					var rechargeResult = dataJson[0];
					$("#rechargeMoney").empty().append(rechargeResult.nameA);
					var rechargePercent = $("#rechargePercent");
					if(rechargeResult.percentA>0){
						rechargePercent.attr("class","top-arrow");
						if(rechargeResult.percentA>999){
							rechargePercent.empty().after("999+%");
						}else{
							rechargePercent.empty().after(rechargeResult.percentA+"%");
						}
					}else if(rechargeResult.percentA==0){
						rechargePercent.attr("class","flat-arrow");
					}else{
						rechargePercent.attr("class","down-arrow");
						if(rechargeResult.percentA<-999){
							rechargePercent.empty().after("999+%");
						}else{
							rechargePercent.empty().after(rechargeResult.percentA+"%");
						}
					}
					//提现
					var withdrawResult = dataJson[1];
					$("#withdrawMoney").empty().append(withdrawResult.nameB);
					var withdrawPercent = $("#withdrawPercent");
					if(withdrawResult.percentB>0){
						withdrawPercent.attr("class","top-arrow");
						if(withdrawResult.percentB>999){
							withdrawPercent.empty().after("999+%");
						}else{
							withdrawPercent.empty().after(withdrawResult.percentB+"%");
						}
					}else if(withdrawResult.percentB==0){
						withdrawPercent.attr("class","flat-arrow");
					}else{
						withdrawPercent.attr("class","down-arrow");
						if(withdrawResult.percentB<-999){
							withdrawPercent.empty().after("999+%");
						}else{
							withdrawPercent.empty().after(withdrawResult.percentB+"%");
						}
					}
					//投资
					var investResult = dataJson[2];
					$("#investMoney").empty().append(investResult.nameC);
					var investPercent = $("#investPercent");
					if(investResult.percentC>0){
						investPercent.attr("class","top-arrow");
						if(investResult.percentC>999){
							investPercent.empty().after("999+%");
						}else{
							investPercent.empty().after(investResult.percentC+"%");
						}
					}else if(investResult.percentC==0){
						investPercent.attr("class","flat-arrow");
					}else{
						investPercent.attr("class","down-arrow");
						if(investResult.percentC<-999){
							investPercent.empty().after("999+%");
						}else{
							investPercent.empty().after(investResult.percentC+"%");
						}
					}
				}
			});
		}

	}
});