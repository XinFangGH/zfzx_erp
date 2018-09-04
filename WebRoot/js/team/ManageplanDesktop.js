ManageplanDesktop = Ext.extend(Ext.Panel, {
	info:null,
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		this.initUIComponents();
		ManageplanDesktop.superclass.constructor.call(this, {
			title : '理财业务桌面',
			closable : false,
			id : 'ManageplanDesktop',
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
				url : "js/team/html/manageplanDesktop.html",
				success : function(result) {
					temp=result;
				}
			});
			return temp;
		}
		
		
		test();
		function test(){
			Ext.Ajax.request({
			 	url : __ctxPath + '/highchart/getPlearlyRedemptionListHighchart.do',
			 	method : 'POST',
			 	scope:this,
			 	success:function(response,request){
			 		var dataJson = Ext.util.JSON.decode(response.responseText).result;
			 		for(var i=0;i<dataJson.length;i++){
				 		var dataResult = dataJson[i];
				 		var nameA = "";
				 		if(dataResult.nameA.length>8){
				 			nameA=dataResult.nameA.substring(0,8);
				 		}else{
				 			nameA=dataResult.nameA;
				 		}
				 		$("#plearlyredemption").after("<tr><td align='center' valign='middle' title='"+dataResult.nameA+"'>"+nameA+"</td>" +
				 													"<td align='center' valign='middle'>"+dataResult.remarkA+"</td></tr>");
			 		}
			 	}
			})
			Ext.Ajax.request({
			 	url : __ctxPath + '/highchart/getPlearlyRedemptionNumHighchart.do',
			 	method : 'POST',
			 	scope:this,
			 	success:function(response,request){
			 		var dataJson = Ext.util.JSON.decode(response.responseText).result;
			 		var dataResult = dataJson[0];
			 		//办理中
			 		var sumA = dataResult.sumA;
			 		var moneyA = dataResult.moneyA;
			 		if(sumA==undefined||sumA==""){
			 			sumA=0;
			 		}
			 		if(moneyA==undefined||moneyA==""){
			 			moneyA=0;
			 		}
			 		$("#blzNum").empty().append(sumA+"笔");
			 		$("#blzMoney").empty().append(moneyA);
			 		//已通过
			 		var sumB = dataResult.sumB;
			 		var moneyB = dataResult.moneyB;
			 		if(sumB==""||sumB==undefined){
			 			sumB=0;
			 		}
			 		if(moneyB==""||moneyB==undefined){
			 			moneyB=0;
			 		}
			 		$("#ytgNum").empty().append(sumB+"笔");
			 		$("#ytgMoney").empty().append(moneyB);
			 		//已驳回
			 		var sumC = dataResult.sumC;
			 		var moneyC = dataResult.moneyC;
			 		if(sumC==""||sumC==undefined){
			 			sumC=0;
			 		}
			 		if(moneyC==""||moneyC==undefined){
			 			moneyC=0;
			 		}
			 		$("#ybhNum").empty().append(sumC+"笔");
			 		$("#ybhMoney").empty().append(moneyC);
			 	}
			})
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
		//批量债权转让销售统计图
		changePlanMarket = function(dataState){
			planMarket.ajax(dataState);
		}
		try{
			planMarket.ajax();
		}catch(err){
		
		}
		
		//批量债权转让状态统计图
		changePlanAssignment = function(dataState){
			planAssignment.ajax(dataState);
		}
		try{
			planAssignment.ajax();
		}catch(err){
		
		}
		//资金债权分析图
		changePlanFundAnalyze = function(dataState){
			planFundAnalyze.ajax(dataState);
		}
		try{
			planFundAnalyze.ajax();
		}catch(err){
		
		}
		//批量债权转让类型占比统计图
		changePlanProportion= function(dataState){
			planProportion.ajax(dataState);
		}
		try{
			planProportion.ajax();
		}catch(err){
		
		}
		/*this.info='<div class="container-all">'+
				  	   '<div class="content">'+
				  	   		'<div class="row">'+
				  	   			 '<div class="info-box w-20">'+
				  	   			 	  '<span class="info-box-icon bg-color-1"><i class="top-arrow"></i>10% <em>环比</em></span>'+
						  	   			 '<div class="info-box-content">'+
							  	   			 '<span class="info-box-text colr-one"><em>200</em>人</span> ' +
							  	   			 '<span class="info-box-number">新增注册人数</span>'+
		            	                 '</div>'+
		            	            '</div>'+
                	                ' <div class="info-box w-20 info-box-1">'+
                	                	'<span class="info-box-icon bg-color-2">'+
                	                		'<i  class="down-arrow"></i>10%<em>环比</em>'+
                	                	'</span>'+
                	                	'<div class="info-box-content">'+
	                	                	'<span class="info-box-text colr-two"><em>198</em>笔</span>'+
	                	                	'<span class="info-box-number">实名认证人数</span>'+
                	                	' </div>'+
                	                ' </div>'+
                	                '<div class="info-box w-20 info-box-0 mr-15">'+
                	                	'<span class="info-box-icon bg-color-3">'+
                	                		'<i  class="down-arrow"></i>10%<em>环比</em>'+
                	                	'</span>'+
                	                	'<div class="info-box-content">'+
	                	                	'<span class="info-box-text colr-three"><em>2000</em>万</span>'+
	                	                	'<span class="info-box-number">新增投资金额</span>'+
                	                	'</div>'+
                	                '</div>'+
                	                '<div class="info-box w-20 info-box-1">'+
                	                	'<span class="info-box-icon bg-color-4">'+
                	                		'<i  class="down-arrow"></i>10%<em>环比</em>'+
                	                	'</span>'+
                	                	'<div class="info-box-content">'+
                	                		'<span class="info-box-text colr-four"><em>2000</em>万</span>'+
                	                		'<span class="info-box-number">新增充值金额</span>'+
                	                	'</div>'+
                	                '</div>'+
                	                '<div class="info-box w-20 mr-0">'+
                	                	'<span class="info-box-icon bg-color-5">'+
                	                		'<i class="top-arrow"></i>10%<em>环比</em>'+
                	                	'</span>'+
                	                    '<div class="info-box-content">'+
                	                    	'<span class="info-box-text colr-five"><em>2000</em>万</span>'+
                	                    	'<span class="info-box-number">新增提现金额</span>'+
                	                    '</div>'+
                	                  '</div>'+
                	                 '</div>'+
                	                 
                	           '<div class="clearfix"></div>'+
                	           		'<div class="data-left-all">'+
                	           			'<div class="data-left">'+
                	          				 '<div class="data-list01">'+
                	          				 	'<h2>批量债权转让状态统计图'+
                	          				 		'<p class="select-box">'+
                	          				 			'<select>'+
                	          				 				'<option>请选择时间</option>'+
                	          				 				'<option>按年显示</option>'+
                	          				 				'<option>按季显示</option>'+
                	          				 				'<option>按月显示</option>'+
                	          				 				'<option>按天显示</option>'+
                	          				 			'</select>'+
                	          				 		'</p>'+
                	                			'</h2>'+
                	                		'<div class="dynamic-graph txt-alg"><img src="highchart/images/deskImages/11.png"/></div>'+
                	                	'</div>'+
                	                		
                	              '<div class="data-list01 data-list02">'+
                	              	'<h2>资金债权分析图'+
                	              		'<p class="select-box">'+
                	              			'<select>'+
                	          				 	'<option>请选择时间</option>'+
                	          				 	'<option>按年显示</option>'+
                	          				 	'<option>按季显示</option>'+
                	          				 	'<option>按月显示</option>'+
                	          				 	'<option>按天显示</option>'+
                	          				 '</select>'+
                	          				'</p>'+
                	                	'</h2>'+
                	                '<div class="dynamic-graph txt-alg"><img src="highchart/images/deskImages/22.png"/></div>'+		
                	               '</div>'+
                	               
                	              '<div class="clearfix"></div>'+
                	              	'<div class="data-list01">'+
                	              		'<h2>批量债权转让类型占比统计图'+
                	              			'<p class="select-box">'+
	                	                		'<select>'+
	                	          				 	'<option>请选择时间</option>'+
	                	          				 	'<option>按年显示</option>'+
	                	          				 	'<option>按季显示</option>'+
	                	          				 	'<option>按月显示</option>'+
	                	          				 	'<option>按天显示</option>'+
	                	          				 '</select>'+
                	          				'</p>'+
                	                	'</h2>'+
                	                '<div class="dynamic-graph txt-alg"><img src="highchart/images/deskImages/33.png"/></div>'+
                	              '</div>'+
                	              
                	             '<div class="data-list01 data-list02">'+
                	             	'<h2>批量债权转让销售统计图'+
                	             		'<p class="select-box">'+
                	             			 '<select>'+
	                	          				 	'<option>请选择时间</option>'+
	                	          				 	'<option>按年显示</option>'+
	                	          				 	'<option>按季显示</option>'+
	                	          				 	'<option>按月显示</option>'+
	                	          				 	'<option>按天显示</option>'+
	                	          				 '</select>'+
                	          				'</p>'+
                	                	'</h2>'+
                	                '<div class="dynamic-graph txt-alg"><img src="highchart/images/deskImages/44.png"/></div>'+
                	              '</div>'+
                	             '</div>'+
                	             
                	          '<div class="data-right">'+
                	          	'<div class="best-seller">'+
                	          		'<h2>提前赎回项目</h2>'+
                	          			'<div class="best-list">'+
                	          				'<div class="dl-list">'+
                	          					'<dl>'+
                	          						'<dt><img src="highchart/images/deskImages/clock-icon.png"/></dt>'+
                	          							'<dd>'+
                	          								'<h5>提前赎回项目</h5>'+
                	          									'<p><span>总计：<em>12 笔 </em></span><span class="pdr-0">金额：<em>8888 </em>元</span></p>'+
                	          							'</dd>'+
                	          						'</dl>'+
                	          						'<dl>'+
                	          							'<dt><img src="highchart/images/deskImages/clock-icon.png"/></dt>'+
                	          								'<dd>'+
                	          									'<h5>提前赎回项目</h5>'+
                	          										'<p><span>总计：<em>12 笔 </em></span><span class="pdr-0">金额：<em>8888 </em>元</span></p>'+
                	              							'</dd>'+	
                	              						'</dl>'+
                	              						'<dl>'+
                	              							'<dt><img src="highchart/images/deskImages/clock-icon.png"/></dt>'+
                	              								'<dd>'+
                	              									'<h5>提前赎回项目</h5>'+
                	              										'<p><span>总计：<em>12 笔 </em></span><span class="pdr-0">金额：<em>8888 </em>元</span></p>'+
                	              								'</dd>'+
                	              						'</dl>'+	
                	              					'</div>'+
                	              				'</div>'+
                	              				
                	              		'<div class="best-list">'+
                	              			'<table width="100%" border="0" cellspacing="0" cellpadding="0">'+
                	              				'<tr>'+
                	              					'<th align="center" valign="middle">项目名称</th>'+
                	              					'<th align="center" valign="middle">提前赎回时间</th>'+
                	              					'<th align="center" valign="middle">办理状态</th>'+
                	              				'</tr>'+
                	              				'<tr>'+
                	              					'<td align="center" valign="middle">互融时代宝</td>'+
                	              					'<td align="center" valign="middle">2个月</td>'+
                	              					'<td align="center" valign="middle">正在赎回</td>'+
                	              				'</tr>'+
                	              				'<tr>'+
                	              					'<td align="center" valign="middle">互融时代宝</td>'+
                	              					'<td align="center" valign="middle">3个月</td>'+
                	              					'<td align="center" valign="middle">正在赎回</td>'+
                	              				'</tr>'+
                	             				'<tr>'+
                	              					'<td align="center" valign="middle">互融时代宝</td>'+
                	              					'<td align="center" valign="middle">4个月</td>'+
                	              					'<td align="center" valign="middle">正在赎回</td>'+
                	              				'</tr>'+
                	              			'</table>'+
                	              '</div>'+
                	        '</div>'+
                	   '</div>'+
                	'</div>';*/
	}
});

