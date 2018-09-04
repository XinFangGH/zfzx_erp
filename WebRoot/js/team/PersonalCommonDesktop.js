PersonalCommonDesktop = Ext.extend(Ext.Panel, {
	info:null,
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		this.initUIComponents();
		PersonalCommonDesktop.superclass.constructor.call(this, {
			title : '通用桌面',
			closable : false,
			id : 'PersonalCommonDesktop',
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
				url : "js/team/html/personalCommonDesktop.html",
				success : function(result) {
					temp=result;
				}
			});
			return temp;
		}
		
		
		
		//1.查询客户贷款数据
		Ext.Ajax.request({
			url : __ctxPath + '/highchart/findCommonLoanDataHighchart.do',
			method : 'POST',
			scope:this,
			success : function(response, request) {
				var dataJson = Ext.util.JSON.decode(response.responseText).result;
				
				var dNode1 = $("#cyxkh1");
				var dNode3 = $("#czskh1");
				var dNode5 = $("#cxzje1");
				var dNode7 = $("#cbhje1");
				var dNode9 = $("#cxzbs1");
				var dNode11 = $("#cbhbs1");
				
				$("#cyxkh2").empty();
				if(dataJson.percentA>0){
					if(dataJson.percentA>999){
						$("#cyxkh2").append(
						 '<i class="top-arrow"></i>999+%'+
					  	 ' <em>环比</em>'
					    );
					}else{
						$("#cyxkh2").append(
						 '<i class="top-arrow"></i>'+dataJson.percentA+'%'+
					  	 ' <em>环比</em>'
					    );
					}
					
	            
	               
				}else if(dataJson.percentA==0){
					$("#cyxkh2").append(
					 '<i class="flat-arrow"></i>'+
				  	 ' <em>环比</em>'
	                );
				}else{
					if(dataJson.percentA<(-999)){
						$("#cyxkh2").append(
						 '<i class="down-arrow"></i>999+%'+
					  	 ' <em>环比</em>'
		                );
					}else{
						$("#cyxkh2").append(
						 '<i class="down-arrow"></i>'+(-dataJson.percentA)+'%'+
					  	 ' <em>环比</em>'
		                );
					}
					
				}
				dNode1[0].innerHTML=dataJson.sumA;
				
				$("#czskh2").empty();
				if(dataJson.percentB>0){
					if(dataJson.percentB>999){
						$("#czskh2").append(
						 '<i class="top-arrow"></i>999+%'+
					  	 ' <em>环比</em>'
		            
		                );
					}else{
						$("#czskh2").append(
						 '<i class="top-arrow"></i>'+dataJson.percentB+'%'+
					  	 ' <em>环比</em>'
		            
		                );
					}
					
				}else if(dataJson.percentB==0){
					$("#czskh2").append(
					 '<i class="flat-arrow"></i>'+
				  	 ' <em>环比</em>'
	                );
				}else{
					if(dataJson.percentB<(-999)){
						$("#czskh2").append(
						 '<i class="down-arrow"></i>999+%'+
					  	 ' <em>环比</em>'
		                );
					}else{
						$("#czskh2").append(
						 '<i class="down-arrow"></i>'+(-dataJson.percentB)+'%'+
					  	 ' <em>环比</em>'
		                );
					}
					
				}
				dNode3[0].innerHTML=dataJson.sumB;
				
				$("#cxzje2").empty();
				if(dataJson.percentC>0){
					if(dataJson.percentC>999){
						$("#cxzje2").append(
						 '<i class="top-arrow"></i>999+%'+
					  	 ' <em>环比</em>'
		            
		                );
					}else{
						$("#cxzje2").append(
						 '<i class="top-arrow"></i>'+dataJson.percentC+'%'+
					  	 ' <em>环比</em>'
		            
		                );
					}
					
				}else if(dataJson.percentC==0){
					$("#cxzje2").append(
					 '<i class="flat-arrow"></i>'+
				  	 ' <em>环比</em>'
	                );
				}else{
					if(dataJson.percentC<(-999)){
						$("#cxzje2").append(
						 '<i class="down-arrow"></i>999+%'+
					  	 ' <em>环比</em>'
		                );
					}else{
						$("#cxzje2").append(
						 '<i class="down-arrow"></i>'+(-dataJson.percentC)+'%'+
					  	 ' <em>环比</em>'
		                );
					}
					
				}
				dNode5[0].innerHTML=dataJson.nameA;
				
				$("#cbhje2").empty();
				if(dataJson.percentD>0){
					if(dataJson.percentD>999){
						$("#cbhje2").append(
						 '<i class="top-arrow"></i>999+%'+
					  	 ' <em>环比</em>'
		            
		                );
					}else{
						$("#cbhje2").append(
						 '<i class="top-arrow"></i>'+dataJson.percentD+'%'+
					  	 ' <em>环比</em>'
		            
		                );
					}
					
				}else if(dataJson.percentD==0){
					$("#cbhje2").append(
					 '<i class="flat-arrow"></i>'+
				  	 ' <em>环比</em>'
	                );
				}else{
					if(dataJson.percentD<(-999)){
						$("#cbhje2").append(
						 '<i class="down-arrow"></i>999+%'+
					  	 ' <em>环比</em>'
		                );
					}else{
						$("#cbhje2").append(
						 '<i class="down-arrow"></i>'+(-dataJson.percentD)+'%'+
					  	 ' <em>环比</em>'
		                );
					}
					
				}
				dNode7[0].innerHTML=dataJson.nameB;
				
				$("#cxzbs2").empty();
				if(dataJson.percentE>0){
					if(dataJson.percentE>999){
						$("#cxzbs2").append(
						 '<i class="top-arrow"></i>999+%'+
					  	 ' <em>环比</em>'
		            
		                );
					}else{
						$("#cxzbs2").append(
						 '<i class="top-arrow"></i>'+dataJson.percentE+'%'+
					  	 ' <em>环比</em>'
		            
		                );
					}
					
				}else if(dataJson.percentE==0){
					$("#cxzbs2").append(
					 '<i class="flat-arrow"></i>'+
				  	 ' <em>环比</em>'
	                );
				}else{
					if(dataJson.percentE<(-999)){
						$("#cxzbs2").append(
						 '<i class="down-arrow"></i>999+%'+
					  	 ' <em>环比</em>'
		                );
					}else{
						$("#cxzbs2").append(
						 '<i class="down-arrow"></i>'+(-dataJson.percentE)+'%'+
					  	 ' <em>环比</em>'
		                );
					}
				}
				dNode9[0].innerHTML=dataJson.sumE;
				
				$("#cbhbs2").empty();
				if(dataJson.percentF>0){
					if(dataJson.percentF>999){
						$("#cbhbs2").append(
						 '<i class="top-arrow"></i>999+%'+
					  	 ' <em>环比</em>'
		            
		                );
					}else{
						$("#cbhbs2").append(
						 '<i class="top-arrow"></i>'+dataJson.percentF+'%'+
					  	 ' <em>环比</em>'
		                );
					}
					
				}else if(dataJson.percentF==0){
					$("#cbhbs2").append(
					 '<i class="flat-arrow"></i>'+
				  	 ' <em>环比</em>'
	                );
				}else{
					if(dataJson.percentF<(-999)){
						$("#cbhbs2").append(
						 '<i class="down-arrow"></i>999+%'+
					  	 ' <em>环比</em>'
		                );
						
					}else{
						$("#cbhbs2").append(
						 '<i class="down-arrow"></i>'+(-dataJson.percentF)+'%'+
					  	 ' <em>环比</em>'
		                );
					}
					
				}
				dNode11[0].innerHTML=dataJson.sumF;
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
				$("#cyqtx").empty();
				$("#cyqtx").append('<tr>'+
                         '<th align="center" valign="middle">借款人</th>'+
				  	     '<th align="center" valign="middle">还款金额</th>'+
				  	   	 '<th align="center" valign="middle">计划日期</th>'+
                 '</tr>');
				for(var i=0;i<taskList.result.length;i++){
				//	if(i==0){
						$("#cyqtx").append(
						'<tr>'+
                             '<td align="center" valign="middle">'+(taskList.result[i].nameA.length>8?taskList.result[i].nameA.substring(0,2):taskList.result[i].nameA)+'</td>'+
                             '<td align="center" valign="middle">'+taskList.result[i].moneyA+'万 </td>'+
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
	 //3.项目放款通知
  /*	Ext.Ajax.request({
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
                        '<td align="center" valign="middle">'+taskList.result[i].moneyA+'万<img src="highchart/images/wealth/hot.png"/></td>'+
                        '<td align="center" valign="middle">'+taskList.result[i].searchDate+'</td>'+
              	        '</tr>'
						);
					}else{
						$("#yqtx").append(
						'<tr>'+
                	    '<td align="center" valign="middle">'+(i+1)+'</td>'+
                	    '<td align="center" valign="middle">'+taskList.result[i].nameA+'</td>'+
                	    '<td align="center" valign="middle">'+(taskList.result[i].nameB.length>8?taskList.result[i].nameB.substring(0,7):taskList.result[i].nameB)+'</td>'+
                        '<td align="center" valign="middle">'+taskList.result[i].moneyA+'万</td>'+
              	        '</tr>'
						);
					}
					if(i==4){
						break;
					}
	        	}
			},
			failure : function(response,request) {
				Ext.ux.Toast.msg('操作信息', '查询任务完成时限操作失败!');
			}
		});*/
	//5.月度总业务排名
 	Ext.Ajax.request({
			url : __ctxPath + '/highchart/findLoneRankingHighchart.do',
			method : 'POST',
			scope:this,
			success : function(response, request) {
				var taskList = Ext.util.JSON.decode(response.responseText);
				$("#cydpm").empty();
				$("#cydpm").append('<tr>'+
                           '<th align="center" valign="middle">排名</th>'+
				  	       '<th align="center" valign="middle">姓名</th>'+
				  	       '<th align="center" valign="middle">金额</th>'+
                 '</tr>');
				for(var i=0;i<taskList.result.length;i++){
					if(i==0){
						$("#cydpm").append(
						'<tr>'+
						'<td align="center" valign="middle">'+(i+1)+'</td>'+
                	    '<td align="center" valign="middle">'+(taskList.result[i].nameA)+'</td>'+
                        '<td align="center" valign="middle">'+Ext.util.Format.number(taskList.result[i].moneyA, ',000,000,000.00')+'万<img src="highchart/images/wealth/hot.png"/></td>'+
              	        '</tr>'
						);
					}else{
						$("#cydpm").append(
						'<tr>'+
						'<td align="center" valign="middle">'+(i+1)+'</td>'+
                	    '<td align="center" valign="middle">'+(taskList.result[i].nameA)+'</td>'+
                        '<td align="center" valign="middle">'+taskList.result[i].moneyA+'万</td>'+
              	        '</tr>'
						);
					}
						
					if(i==7){
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
		
		//加载年度交易额统计
		try{
			LoanMoneyYearTrends.ajax("",2);
		}catch(err){
		
		}
			//风险等级统计
		try{
			RiskControlTrends.ajax();
		}catch(err){
		
		}
			//加载年度交易额统计
		try{
			LoanOverdueTrends.ajax();
		}catch(err){
		
		}
		
		
		/*this.info='<div class="container-all">'+
				  	   '<div class="content">'+
				  	   		'<div class="row">'+
				  	   		  '<div class="info-box w-15">'+
				  	   		      '<span class="info-box-icon bg-color-1" id="yxkh2" >'+
				  	   		          '<i class="top-arrow"></i>10%'+
				  	   		          ' <em>环比</em>'+
				  	   		      '</span>'+
				  	   		      '<div class="info-box-content">'+
				  	   		          '<span class="info-box-text colr-one" style="font-size:20px;"><em style="font-size:26px;" id="yxkh1" >200</em>人</span>'+
				  	   		          '<span class="info-box-number">新增意向客户</span>'+
				  	   		      '</div>'+
				  	   		    '</div>'+
				  	   		    '<div class="info-box w-15 mr-none">'+
				  	   		      '<span class="info-box-icon bg-color-2" id="zskh2" >'+
				  	   		          '<i  class="down-arrow"></i>10%'+
				  	   		          '<em>环比</em>'+
				  	   		      '</span>'+
				  	   		      '<div class="info-box-content">'+
				  	   		          '<span class="info-box-text colr-two" style="font-size:20px;"><em style="font-size:26px;"id="zskh1" >198</em>人</span>'+
				  	   		          '<span class="info-box-number">新增正式客户</span>'+
				  	   		       '</div>'+
				  	   		    '</div>'+
				  	   		    '<div class="info-box w-15 info-box-0 mr-15">'+
				  	   		      '<span class="info-box-icon bg-color-3" id="xzje2">'+
				  	   		          '<i  class="down-arrow"></i>10%'+
				  	   		          '<em>环比</em>'+
				  	   		      '</span>'+
				  	   		      '<div class="info-box-content">'+
				  	   		          '<span class="info-box-text colr-three" style="font-size:20px;"><em style="font-size:26px;" id="xzje1">200</em>万</span>'+
				  	   		          '<span class="info-box-number">新增贷款金额</span>'+
				  	   		       '</div>'+
				  	   		    '</div>'+
				  	   		    '<div class="info-box w-15 mr-none">'+
				  	   		      '<span class="info-box-icon bg-color-4" id="bhje2">'+
				  	   		          '<i  class="down-arrow"></i>10%'+
				  	   		          '<em>环比</em>'+
				  	   		      '</span>'+
				  	   		      '<div class="info-box-content">'+
				  	   		          '<span class="info-box-text colr-four" style="font-size:20px;"><em style="font-size:26px;" id="bhje1">200</em>万</span>'+
				  	   		          '<span class="info-box-number">驳回项目金额</span>'+
				  	   		       '</div>'+
				  	   		    '</div>'+
				  	   		    '<div class="info-box w-15">'+
				  	   		      '<span class="info-box-icon bg-color-5" id="xzbs2" >'+
				  	   		          '<i  class="down-arrow"></i>10%'+
				  	   		          '<em>环比</em>'+
				  	   		      '</span>'+
				  	   		      '<div class="info-box-content">'+
				  	   		          '<span class="info-box-text colr-five" style="font-size:20px;"><em style="font-size:26px;" id="xzbs1">200</em>笔</span>'+
				  	   		          '<span class="info-box-number">新增贷款笔数</span>'+
				  	   		       '</div>'+
				  	   		    '</div>'+
				  	   		    '<div class="info-box w-15 mr-0 info-box-0 mr-none">'+
				  	   		      '<span class="info-box-icon bg-color-4" id="bhbs2">'+
				  	   		          '<i  class="down-arrow"></i>10%'+
				  	   		          '<em>环比</em>'+
				  	   		      '</span>'+
				  	   		      '<div class="info-box-content">'+
				  	   		          '<span class="info-box-text colr-six" style="font-size:20px;"><em style="font-size:26px;" id="bhbs1">200</em>笔</span>'+
				  	   		          '<span class="info-box-number">驳回贷款笔数</span>'+
				  	   		       '</div>'+
				  	   		    '</div>'+
				  	   		 '</div>'+
				  	   		 '<div class="clearfix"></div>'+
				  	   		    '<div class="data-left-all">'+
				  	   		        '<div class="data-left">'+
				  	   		            '<div class="data-list01">'+
				  	   		                '<h2>客户逾期统计</h2>'+
				  	   		                '<div class="dynamic-graph txt-alg" id="CustomerOverdueTrends"><img src="highchart/images/deskImages/yw-pic1.png"/></div>'+
				  	   		            '</div>'+
				  	   		            ' <div class="data-list01 data-list02">'+
				  	   		                '<h2>业务统计图</h2>'+
				  	   		                      '<p class="select-box">'+
				                        	      '<select id="yearLoanData" onchange="changeDate(this.options[this.options.selectedIndex].value)">'+
					                        	  '<option>2016</option>'+
					                         '</select>'+
				                        '</p>'+
				  	   		                '<div class="dynamic-graph txt-alg" id="LoanMoneyYearTrends"><img src="highchart/images/deskImages/pic1.png"/></div>'+
				  	   		            '</div>'+
				  	   		            '<div class="clearfix"></div>'+
				  	   		            '<div class="data-list01">'+
				  	   		                '<h2>逾期款项提醒</h2>'+
				  	   		                '<div class="best-list">'+
				  	   		                    '<table width="100%" border="0" cellspacing="0" cellpadding="0" id="yqtx">'+
				  	   		                        '<tr>'+
				  	   		                             '<th align="center" valign="middle">借款人</th>'+
				  	   		                             '<th align="center" valign="middle">借款金额</th>'+
				  	   		                             '<th align="center" valign="middle">还款时间</th>'+
				  	   		                        '</tr>'+
				  	   		                        '<tr>'+
				  	   		                             '<td align="center" valign="middle">张三</td>'+
				  	   		                             '<td align="center" valign="middle">1.3万 <img src="highchart/images/deskImages/hot.png" /></td>'+
				  	   		                             '<td align="center" valign="middle">2016-05-01</td>'+
				  	   		                        '</tr>'+
				  	   		                        '<tr>'+
				  	   		                             '<td align="center" valign="middle">张三</td>'+
				  	   		                             '<td align="center" valign="middle">1.3万 <img src="highchart/images/deskImages/hot.png" /></td>'+
				  	   		                             '<td align="center" valign="middle">2016-05-01</td>'+
				  	   		                        '</tr>'+
				  	   		                        '<tr>'+
				  	   		                             '<td align="center" valign="middle">张三</td>'+
				  	   		                             '<td align="center" valign="middle">1.3万 <img src="highchart/images/deskImages/hot.png" /></td>'+
				  	   		                             '<td align="center" valign="middle">2016-05-01</td>'+
				  	   		                        '</tr>'+
				  	   		                     '</table>'+
				  	   		                   '</div>'+
				  	   		              '</div>'+
				  	   		              '<div class="data-list01 data-list02">'+
				  	   		                ' <h2>项目放款通知</h2>'+
				  	   		                '<div class="best-list">'+
				  	   		                    '<table width="100%" border="0" cellspacing="0" cellpadding="0" id="fktz">'+
				  	   		                        '<tr>'+
				  	   		                             '<th align="center" valign="middle">借款人</th>'+
				  	   		                             '<th align="center" valign="middle">借款金额</th>'+
				  	   		                             '<th align="center" valign="middle">还款时间</th>'+
				  	   		                        '</tr>'+
				  	   		                        '<tr>'+
				  	   		                             '<td align="center" valign="middle">张三</td>'+
				  	   		                             '<td align="center" valign="middle">1.3万 <img src="highchart/images/deskImages/hot.png" /></td>'+
				  	   		                             '<td align="center" valign="middle">2016-05-01</td>'+
				  	   		                        '</tr>'+
				  	   		                        '<tr>'+
				  	   		                             '<td align="center" valign="middle">张三</td>'+
				  	   		                             '<td align="center" valign="middle">1.3万 <img src="highchart/images/deskImages/hot.png" /></td>'+
				  	   		                             '<td align="center" valign="middle">2016-05-01</td>'+
				  	   		                        '</tr>'+
				  	   		                        '<tr>'+
				  	   		                             '<td align="center" valign="middle">张三</td>'+
				  	   		                             '<td align="center" valign="middle">1.3万 <img src="highchart/images/deskImages/hot.png" /></td>'+
				  	   		                             '<td align="center" valign="middle">2016-05-01</td>'+
				  	   		                        '</tr>'+
				  	   		                     '</table>'+
				  	   		                   '</div>'+
				  	   		              '</div>'+
				  	   		          '</div>'+
				  	   		          '<div class="data-right">'+
				  	   		              '<div class="best-seller">'+
				  	   		                  '<h2>我的排名</h2>'+
				  	   		                  '<div class="Ranking-all">'+
				  	   		                       '<dl class="king-list">'+
				  	   		                             '<dt>15名</dt>'+
				  	   		                             '<dd>月度排名</dd>'+
				  	   		                       '</dl>'+
				  	   		                       '<dl class="king-list">'+
				  	   		                             '<dt>20名</dt>'+
				  	   		                             '<dd>年度排名</dd>'+
				  	   		                       '</dl>'+
				  	   		                  '</div>'+
				  	   		                  '<h2>月度总业务排名</h2>'+
				  	   		                  '<div class="best-list">'+
				  	   		                       '<table width="100%" border="0" cellspacing="0" cellpadding="0" id="ydpm">'+
				  	   		                            '<tr>'+
				  	   		                                  '<th align="center" valign="middle">排名</th>'+
				  	   		                                  '<th align="center" valign="middle">姓名</th>'+
				  	   		                                  '<th align="center" valign="middle">金额</th>'+
				  	   		                            '</tr>'+
				  	   		                            '<tr>'+
				  	   		                                  '<td align="center" valign="middle">1 </td>'+
				  	   		                                  '<td align="center" valign="middle">张三</td>'+
				  	   		                                  '<td align="center" valign="middle">200万<img src="highchart/images/deskImages/hot.png" /></td>'+
				  	   		                            '</tr>'+
				  	   		                            '<tr>'+
				  	   		                                  '<td align="center" valign="middle">2 </td>'+
				  	   		                                  '<td align="center" valign="middle">张三</td>'+
				  	   		                                  '<td align="center" valign="middle">200万<img src="highchart/images/deskImages/hot.png" /></td>'+
				  	   		                            '</tr>'+
				  	   		                            '<tr>'+
				  	   		                                  '<td align="center" valign="middle">3 </td>'+
				  	   		                                  '<td align="center" valign="middle">张三</td>'+
				  	   		                                  '<td align="center" valign="middle">200万<img src="highchart/images/deskImages/hot.png" /></td>'+
				  	   		                            '</tr>'+
				  	   		                        '</table>'+
				  	   		                   '</div>'+
				  	   		               '</div>'+
				  	   		          '</div>'+
				  	   		   '</div>'+
				  	   '</div>'+
		          '</div>';*/
	}
});