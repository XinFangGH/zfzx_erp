PersonalTeamDesktop = Ext.extend(Ext.Panel, {
	info:null,
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		this.initUIComponents();
		PersonalTeamDesktop.superclass.constructor.call(this, {
			title : '业务专用桌面',
			closable : false,
			id : 'PersonalTeamDesktop',
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
				url : "js/team/html/PersonalTeamDesktop.html",
				success : function(result) {
					temp=result;
				}
			});
			return temp;
		}
		
		//1.查询客户贷款数据
		Ext.Ajax.request({
			url : __ctxPath + '/highchart/findLoanDataHighchart.do',
			method : 'POST',
			scope:this,
			success : function(response, request) {
				var dataJson = Ext.util.JSON.decode(response.responseText).result;
				
				var dNode1 = $("#yxkh1");
				var dNode3 = $("#zskh1");
				var dNode5 = $("#xzje1");
				var dNode7 = $("#bhje1");
				var dNode9 = $("#xzbs1");
				var dNode11 = $("#bhbs1");
				
				$("#yxkh2").empty();
				if(dataJson.percentA>0){
					if(dataJson.percentA>999){
						$("#yxkh2").append(
						 '<i class="top-arrow"></i>999+%'+
					  	 ' <em>环比</em>'
		            
		                );
					}else{
						$("#yxkh2").append(
						 '<i class="top-arrow"></i>'+dataJson.percentA+'%'+
					  	 ' <em>环比</em>'
		            
		                );
					}
					
				}else if(dataJson.percentA==0){
					$("#yxkh2").append(
					 '<i class="flat-arrow"></i>'+
				  	 ' <em>环比</em>'
	                );
				}else{
					if(dataJson.percentA<(-999)){
						$("#yxkh2").append(
						 '<i class="down-arrow"></i>999+%'+
					  	 ' <em>环比</em>'
		                );
					}else{
						$("#yxkh2").append(
						 '<i class="down-arrow"></i>'+(-dataJson.percentA)+'%'+
					  	 ' <em>环比</em>'
		                );
					}
					
				}
				dNode1[0].innerHTML=dataJson.sumA;
				
				$("#zskh2").empty();
				if(dataJson.percentB>0){
					if(dataJson.percentB>999){
						$("#zskh2").append(
						 '<i class="top-arrow"></i>999+%'+
					  	 ' <em>环比</em>'
		                );
					}else{
						$("#zskh2").append(
						 '<i class="top-arrow"></i>'+dataJson.percentB+'%'+
					  	 ' <em>环比</em>'
		                );
					}
					
				}else if(dataJson.percentB==0){
					$("#zskh2").append(
					 '<i class="flat-arrow"></i>'+
				  	 ' <em>环比</em>'
	                );
				}else{
					if(dataJson.percentB<(-999)){
						$("#zskh2").append(
						 '<i class="down-arrow"></i>999+%'+
					  	 ' <em>环比</em>'
		                );
					}else{
						$("#zskh2").append(
						 '<i class="down-arrow"></i>'+(-dataJson.percentB)+'%'+
					  	 ' <em>环比</em>'
		                );
					}
					
				}
				dNode3[0].innerHTML=dataJson.sumB;
				
				$("#xzje2").empty();
				if(dataJson.percentC>0){
					if(dataJson.percentC>999){
						$("#xzje2").append(
						 '<i class="top-arrow"></i>999+%'+
					  	 ' <em>环比</em>'
		            
		                );
					}else{
						$("#xzje2").append(
						 '<i class="top-arrow"></i>'+dataJson.percentC+'%'+
					  	 ' <em>环比</em>'
		            
		                );
					}
					
				}else if(dataJson.percentC==0){
					$("#xzje2").append(
					 '<i class="flat-arrow"></i>'+
				  	 ' <em>环比</em>'
	                );
				}else{
					if(dataJson.percentC<(-999)){
						$("#xzje2").append(
						 '<i class="down-arrow"></i>999+%'+
					  	 ' <em>环比</em>'
		                );
					}else{
						$("#xzje2").append(
						 '<i class="down-arrow"></i>'+(-dataJson.percentC)+'%'+
					  	 ' <em>环比</em>'
		                );
					}
					
				}
				dNode5[0].innerHTML=dataJson.nameA;
				
				$("#bhje2").empty();
				if(dataJson.percentD>0){
					if(dataJson.percentD<(-999)){
						$("#bhje2").append(
						'<i class="top-arrow"></i>'+dataJson.percentD+'%'+
					  	' <em>环比</em>'
		                );
					}else{
						$("#bhje2").append(
						'<i class="top-arrow"></i>'+dataJson.percentD+'%'+
					  	' <em>环比</em>'
		                );
					}
					
				}else if(dataJson.percentD==0){
					$("#bhje2").append(
					 '<i class="flat-arrow"></i>'+
				  	 ' <em>环比</em>'
	                );
				}else{
					if(dataJson.percentD<(-999)){
						$("#bhje2").append(
						 '<i class="down-arrow"></i>999+%'+
					  	 ' <em>环比</em>'
		                );
					}else{
						$("#bhje2").append(
						 '<i class="down-arrow"></i>'+(-dataJson.percentD)+'%'+
					  	 ' <em>环比</em>'
		                );
					}
					
				}
				dNode7[0].innerHTML=dataJson.nameB;
				
				$("#xzbs2").empty();
				if(dataJson.percentE>0){
					$("#xzbs2").append(
					 '<i class="top-arrow"></i>'+dataJson.percentE+'%'+
				  	 ' <em>环比</em>'
	            
	                );
				}else if(dataJson.percentE==0){
					$("#xzbs2").append(
					 '<i class="flat-arrow"></i>'+
				  	 ' <em>环比</em>'
	                );
				}else{
					if(dataJson.percentE<(-999)){
					
					}
					$("#xzbs2").append(
					 '<i class="down-arrow"></i>'+(-dataJson.percentE)+'%'+
				  	 ' <em>环比</em>'
	                );
				}
				dNode9[0].innerHTML=dataJson.sumE;
				
				$("#bhbs2").empty();
				if(dataJson.percentF>0){
					$("#bhbs2").append(
					 '<i class="top-arrow"></i>'+dataJson.percentF+'%'+
				  	 ' <em>环比</em>'
	            
	                );
				}else if(dataJson.percentF==0){
					$("#bhbs2").append(
					 '<i class="flat-arrow"></i>'+
				  	 ' <em>环比</em>'
	                );
				}else{
					$("#bhbs2").append(
					 '<i class="down-arrow"></i>'+(-dataJson.percentF)+'%'+
				  	 ' <em>环比</em>'
	                );
				}
				dNode11[0].innerHTML=dataJson.sumF;
			},
			failure : function(response,request) {
				Ext.ux.Toast.msg('操作信息', '查询任务完成时限操作失败!');
			}
		});

		//2.逾期款项提醒
  	Ext.Ajax.request({
			url : __ctxPath + '/highchart/findOverdueRemindHighchart.do',
			method : 'POST',
			scope:this,
			success : function(response, request) {
				var taskList = Ext.util.JSON.decode(response.responseText);
				$("#yqtx").empty();
				$("#yqtx").append('<tr>'+
                         '<th align="center" valign="middle">借款人</th>'+
				  	     '<th align="center" valign="middle">还款金额</th>'+
				  	   	 '<th align="center" valign="middle">计划日期</th>'+
                 '</tr>');
				for(var i=0;i<taskList.result.length;i++){
				//	if(i==0){
						$("#yqtx").append(
						'<tr>'+
                             '<td align="center" valign="middle">'+(taskList.result[i].nameA.length>8?taskList.result[i].nameA.substring(0,2):taskList.result[i].nameA)+'</td>'+
                             '<td align="center" valign="middle">'+Ext.util.Format.number(taskList.result[i].moneyA, ',000,000,000.00')+'万 </td>'+
                             '<td align="center" valign="middle">'+taskList.result[i].searchDate+'</td>'+
                        '</tr>'
						
						/*'<tr>'+
                	    '<td align="center" valign="middle">'+taskList.result[i].nameA.length>8?taskList.result[i].nameA.substring(0,7):taskList.result[i].nameA+'</td>'+
                        '<td align="center" valign="middle">'+taskList.result[i].moneyA+'万<img src="highchart/images/wealth/hot.png"/></td>'+
                        '<td align="center" valign="middle">'+taskList.result[i].searchDate+'</td>'+
              	        '</tr>'*/
						);
					/*}else{
						$("#yqtx").append(
						'<tr>'+
                	    '<td align="center" valign="middle">'+(i+1)+'</td>'+
                	    '<td align="center" valign="middle">'+taskList.result[i].nameA+'</td>'+
                	    '<td align="center" valign="middle">'+(taskList.result[i].nameB.length>8?taskList.result[i].nameB.substring(0,7):taskList.result[i].nameB)+'</td>'+
                        '<td align="center" valign="middle">'+taskList.result[i].moneyA+'万</td>'+
              	        '</tr>'
						);
					}*/
					if(i==4){
						break;
					}
	        	}
			},
			failure : function(response,request) {
				Ext.ux.Toast.msg('操作信息', '查询任务完成时限操作失败!');
			}
		});
	 //3.项目放款通知
  	Ext.Ajax.request({
			url : __ctxPath + '/highchart/findLoneRemindHighchart.do',
			method : 'POST',
			scope:this,
			success : function(response, request) {
				var taskList = Ext.util.JSON.decode(response.responseText);
				$("#fktz").empty();
				$("#fktz").append('<tr>'+
                         '<th align="center" valign="middle">借款人</th>'+
				  	     '<th align="center" valign="middle">借款金额</th>'+
				  	   	 '<th align="center" valign="middle">还款日期</th>'+
                 '</tr>');
				for(var i=0;i<taskList.result.length;i++){
				//	if(i==0){
						$("#fktz").append(
						'<tr>'+
                	    '<td align="center" valign="middle">'+(taskList.result[i].nameA.length>8?taskList.result[i].nameA.substring(0,7):taskList.result[i].nameA)+'</td>'+
                        '<td align="center" valign="middle">'+Ext.util.Format.number(taskList.result[i].moneyA, ',000,000,000.00')+'万</td>'+
                        '<td align="center" valign="middle">'+taskList.result[i].searchDate+'</td>'+
              	        '</tr>'
						);
				/*	}else{
						$("#yqtx").append(
						'<tr>'+
                	    '<td align="center" valign="middle">'+(i+1)+'</td>'+
                	    '<td align="center" valign="middle">'+taskList.result[i].nameA+'</td>'+
                	    '<td align="center" valign="middle">'+(taskList.result[i].nameB.length>8?taskList.result[i].nameB.substring(0,7):taskList.result[i].nameB)+'</td>'+
                        '<td align="center" valign="middle">'+taskList.result[i].moneyA+'万</td>'+
              	        '</tr>'
						);
					}*/
					if(i==4){
						break;
					}
	        	}
			},
			failure : function(response,request) {
				Ext.ux.Toast.msg('操作信息', '查询任务完成时限操作失败!');
			}
		});
		//4.查询贷个人业绩排名
		Ext.Ajax.request({
			url : __ctxPath + '/highchart/findOwnRankingHighchart.do',
			method : 'POST',
			scope:this,
			success : function(response, request) {
				var dataJson = Ext.util.JSON.decode(response.responseText).result;
				$("#grpm").empty();
					$("#grpm").append(
					    '<dl class="king-list">'+
				  	   		 '<dt>'+dataJson.rankingA+'名</dt>'+
				  	   		 '<dd>月度排名</dd>'+
				  	     '</dl>'+
				  	     '<dl class="king-list">'+
				  	   		  '<dt>'+dataJson.rankingB+'名</dt>'+
				  	   		  '<dd>年度排名</dd>'+
				  	     '</dl>'
	                );
			},
			failure : function(response,request) {
				Ext.ux.Toast.msg('操作信息', '查询任务完成时限操作失败!');
			}
		});
		
	//5.月度总业务排名
  	Ext.Ajax.request({
			url : __ctxPath + '/highchart/findLoneRankingHighchart.do',
			method : 'POST',
			scope:this,
			success : function(response, request) {
				var taskList = Ext.util.JSON.decode(response.responseText);
				$("#ydpm").empty();
				$("#ydpm").append('<tr>'+
                           '<th align="center" valign="middle">排名</th>'+
				  	       '<th align="center" valign="middle">姓名</th>'+
				  	       '<th align="center" valign="middle">金额</th>'+
                 '</tr>');
				for(var i=0;i<taskList.result.length;i++){
					if(i==0)
						{		
						$("#ydpm").append(
						'<tr>'+
						'<td align="center" valign="middle">'+(i+1)+'</td>'+
                	    '<td align="center" valign="middle">'+(taskList.result[i].nameA)+'</td>'+
                        '<td align="center" valign="middle">'+Ext.util.Format.number(taskList.result[i].moneyA, ',000,000,000.00')+'万<img src="highchart/images/wealth/hot.png"/></td>'+
              	        '</tr>'
						);
						}else{
					     $("#ydpm").append(
						'<tr>'+
						'<td align="center" valign="middle">'+(i+1)+'</td>'+
                	    '<td align="center" valign="middle">'+(taskList.result[i].nameA)+'</td>'+
                        '<td align="center" valign="middle">'+Ext.util.Format.number(taskList.result[i].moneyA, ',000,000,000.00')+'万</td>'+
              	        '</tr>'
						);
					}
					if(i==5){
						break;
					}
	        	}
			},
			failure : function(response,request) {
				Ext.ux.Toast.msg('操作信息', '查询任务完成时限操作失败!');
			}
		});
			//客户逾期统计
		try{
			CustomerOverdueTrends.ajax();
		}catch(err){
		
		}
		
		changeDate=function(year){
			LoanMoneyYearTrends.ajax(year,1);
		}
		
		//加载年度交易额统计
		try{
			LoanMoneyYearTrends.ajax("",1);
		}catch(err){
		
		}
	}
});