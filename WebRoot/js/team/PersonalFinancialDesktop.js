PersonalFinancialDesktop = Ext.extend(Ext.Panel, {
	info:null,
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		this.initUIComponents();
		PersonalFinancialDesktop.superclass.constructor.call(this, {
			title : '财务专用桌面',
			closable : false,
			id : 'PersonalFinancialDesktop',
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
				url : "js/team/html/PersonalFinancialDesktop.html",
				success : function(result) {
					temp=result;
				}
			});
			return temp;
		}
		
		//1.查询贷款数据
		Ext.Ajax.request({
			url : __ctxPath + '/highchart/findFinancialDataHighchart.do',
			method : 'POST',
			scope:this,
			success : function(response, request) {
				var dataJson = Ext.util.JSON.decode(response.responseText).result;
				
				var dNode1 = $("#ysje1");
				var dNode3 = $("#ssje1");
				var dNode5 = $("#yqsje1");
				var dNode7 = $("#xqsje1");
				
				$("#ysje2").empty();
				if(dataJson.percentA>0){
					if(dataJson.percentA>999){
						$("#ysje2").append(
						 '<i class="top-arrow"></i>'+999+'+%'+
					  	 ' <em>环比</em>'
		            
		                );
					}else{
						$("#ysje2").append(
						 '<i class="top-arrow"></i>'+dataJson.percentA+'%'+
					  	 ' <em>环比</em>'
		            
		                );
					}
					
				}else if(dataJson.percentA==0){
					$("#ysje2").append(
					 '<i class="flat-arrow"></i>'+
				  	 ' <em>环比</em>'
	                );
				}else{
					if(dataJson.percentA<-999){
						$("#ysje2").append(
						 '<i class="down-arrow"></i>999+%'+
					  	 ' <em>环比</em>'
		                );
					}else{
						$("#ysje2").append(
						 '<i class="down-arrow"></i>'+(-dataJson.percentA)+'%'+
					  	 ' <em>环比</em>'
		                );
					}
					
				}
				dNode1[0].innerHTML=dataJson.nameA;
				
				$("#ssje2").empty();
				if(dataJson.percentB>0){
					if(dataJson.percentB>999){
						$("#ssje2").append(
						 '<i class="top-arrow"></i>999+%'+
					  	 ' <em>环比</em>'
		            
		                );
					}else{
						$("#ssje2").append(
						 '<i class="top-arrow"></i>'+dataJson.percentB+'%'+
					  	 ' <em>环比</em>'
		            
		                );
					}
					
				}else if(dataJson.percentB==0){
					$("#ssje2").append(
					 '<i class="flat-arrow"></i>'+
				  	 ' <em>环比</em>'
	                );
				}else{
					if(dataJson.percentB<-999){
						$("#ssje2").append(
						 '<i class="down-arrow"></i>999+%'+
					  	 ' <em>环比</em>'
		                );
					}else{
						$("#ssje2").append(
						 '<i class="down-arrow"></i>'+(-dataJson.percentB)+'%'+
					  	 ' <em>环比</em>'
		                );
					}
					
				}
				dNode3[0].innerHTML=dataJson.nameB;
				
				$("#yqsje2").empty();
				if(dataJson.percentC>0){
					if(dataJson.percentC>999){
						$("#yqsje2").append(
						 '<i class="top-arrow"></i>999+%'+
					  	 ' <em>环比</em>'
		            
		                );
					}else{
						$("#yqsje2").append(
						 '<i class="top-arrow"></i>'+dataJson.percentC+'%'+
					  	 ' <em>环比</em>'
		            
		                );
					}
					
				}else if(dataJson.percentC==0){
					$("#yqsje2").append(
					 '<i class="flat-arrow"></i>'+
				  	 ' <em>环比</em>'
	                );
				}else{
					if(dataJson.percentC<-999){
						$("#yqsje2").append(
						 '<i class="down-arrow"></i>999+%'+
					  	 ' <em>环比</em>'
		                );
					}else{
						$("#yqsje2").append(
						 '<i class="down-arrow"></i>'+(-dataJson.percentC)+'%'+
					  	 ' <em>环比</em>'
		                );
					}
					
				}
				dNode5[0].innerHTML=dataJson.nameC;
				
				$("#xqsje2").empty();
				if(dataJson.percentD>0){
					if(dataJson.percentD>999){
						$("#xqsje2").append(
						 '<i class="top-arrow"></i>999%'+
					  	 ' <em>环比</em>'
		                );
					}else{
						$("#xqsje2").append(
						 '<i class="top-arrow"></i>'+dataJson.percentD+'%'+
					  	 ' <em>环比</em>'
		            
		                );
					}
					
				}else if(dataJson.percentD==0){
					$("#xqsje2").append(
					 '<i class="flat-arrow"></i>'+
				  	 ' <em>环比</em>'
	                );
				}else{
					if(dataJson.percentD<-999){
						$("#xqsje2").append(
						 '<i class="down-arrow"></i>999+%'+
					  	 ' <em>环比</em>'
		                );
					}else{
						$("#xqsje2").append(
						 '<i class="down-arrow"></i>'+(-dataJson.percentD)+'%'+
					  	 ' <em>环比</em>'
		                );
					}
					
				}
				dNode7[0].innerHTML=dataJson.nameD;
			},
			failure : function(response,request) {
				Ext.ux.Toast.msg('操作信息', '查询任务完成时限操作失败!');
			}
		});

			//2.逾期款项提醒
  	Ext.Ajax.request({
			url : __ctxPath + '/highchart/findCommonOverdueRemindHighchart.do',
			method : 'POST',
			scope:this,
			success : function(response, request) {
				var taskList = Ext.util.JSON.decode(response.responseText);
				$("#fyqtx").empty();
				$("#fyqtx").append('<tr>'+
                         '<th align="center" valign="middle">借款人</th>'+
				  	     '<th align="center" valign="middle">还款金额</th>'+
				  	   	 '<th align="center" valign="middle">计划日期</th>'+
                 '</tr>');
				for(var i=0;i<taskList.result.length;i++){
				//	if(i==0){
						$("#fyqtx").append(
						'<tr>'+
                             '<td align="center" valign="middle">'+(taskList.result[i].nameA.length>8?taskList.result[i].nameA.substring(0,2):taskList.result[i].nameA)+'</td>'+
                             '<td align="center" valign="middle">'+taskList.result[i].moneyA+'万 </td>'+
                             '<td align="center" valign="middle">'+taskList.result[i].searchDate+'</td>'+
                        '</tr>'
						);
					if(i==9){
						break;
					}
	        	}
			},
			failure : function(response,request) {
				Ext.ux.Toast.msg('操作信息', '查询任务完成时限操作失败!');
			}
		});
	//3.未对账流水
  	Ext.Ajax.request({
			url : __ctxPath + '/highchart/findFundQlideHighchart.do',
			method : 'POST',
			scope:this,
			success : function(response, request) {
				var taskList = Ext.util.JSON.decode(response.responseText);
				$("#wdzls").empty();
				$("#wdzls").append('<tr>'+
                         '<th align="center" valign="middle">流水类型</th>'+
				  	     ' <th align="center" valign="middle">未对账金额</th>'+
				  	   	 '<th align="center" valign="middle">资金类型</th>'+
				  	   	 '<th align="center" valign="middle">到账时间</th>'+
                 '</tr>');
				for(var i=0;i<taskList.result.length;i++){
				//	if(i==0){
						$("#wdzls").append(
						'<tr>'+
                             '<td align="center" valign="middle">'+(taskList.result[i].nameA)+'</td>'+
                             '<td align="center" valign="middle">'+taskList.result[i].moneyA+'万 </td>'+
                             '<td align="center" valign="middle">'+(taskList.result[i].nameB)+'</td>'+
                             '<td align="center" valign="middle">'+taskList.result[i].searchDate+'</td>'+
                        '</tr>'
						);
					if(i==4){
						break;
					}
	        	}
			},
			failure : function(response,request) {
				Ext.ux.Toast.msg('操作信息', '查询任务完成时限操作失败!');
			}
		});
	
		changeDate=function(year){
			LoanMoneyYearTrends.ajax(year,2);
		}
			//加载线下放款额统计
		try{
			LoanMoneyYearTrends.ajax("",2);
		}catch(err){
		
		}
			//加载年度交易额统计
		try{
			LoanOverdueTrends.ajax();
		}catch(err){
		
		}
		//收支款项总览
		changeDateInPay=function(year){
			LoanIncomePayTrends.ajax(year);
		}
			//加载线下放款额统计
		try{
			LoanIncomePayTrends.ajax();
		}catch(err){
		
		}
	}
});