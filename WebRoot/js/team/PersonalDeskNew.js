PersonalDeskNew = Ext.extend(Ext.Panel, {
	info:null,
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		this.initUIComponents();
		PersonalDeskNew.superclass.constructor.call(this, {
			title : '财富业务桌面',
			closable : false,
			id : 'PersonalDeskNew',
			border : false,
			iconCls : 'menu-desktop',
			html:this.info
		});
	},
	initUIComponents : function() {
		//1.查询单一统计类数据（订单类）
		Ext.Ajax.request({
			url : __ctxPath + '/highchart/findSomeInfoWealthHighchart.do',
			method : 'POST',
			scope:this,
			success : function(response, request) {
				
				var dataJson = Ext.util.JSON.decode(response.responseText).result;
				
				var dNode2 = $("#je2");
				var dNode4 = $("#bs2");
				
				//本月交易环比
				$("#je1").empty();
				if(dataJson.percentA>0){
					$("#je1").append(
					'<i class="top-arrow"></i> '+(dataJson.percentA > 1000 ? "999+" : dataJson.percentA)+'%'+
				    '<em>环比</em>'
	                );
				}else if(dataJson.percentA==0){
					$("#je1").append(
	                '<i class="flat-arrow"></i>'+
				    '<em>环比</em>'
	                );
				}else{
					$("#je1").append(
					'<i class="down-arrow"></i> '+(dataJson.percentA < (-1000) ? "999+" : Math.abs(dataJson.percentA))+'%'+
				    '<em>环比</em>'
	                );
				}
				
				dNode2[0].innerHTML=dataJson.nameA;
				
				//本月成单环比
				$("#bs1").empty();
				if(dataJson.percentB>0){
					$("#bs1").append(
					'<i class="top-arrow"></i>'+(dataJson.percentB > 1000 ? "999+" : dataJson.percentB)+'%'+
				    '<em>环比</em>'
	                );
				}else if(dataJson.percentB==0){
					$("#bs1").append(
					'<i class="flat-arrow"></i>'+
				    '<em>环比</em>'
	                );
				}else{
					$("#bs1").append(
					'<i class="down-arrow"></i>'+(dataJson.percentB < (-1000) ? "999+" : Math.abs(dataJson.percentB))+'%'+
				    '<em>环比</em>'
	                );
				}
				
				dNode4[0].innerHTML=dataJson.sumA;
				
			},
			failure : function(response,request) {
				Ext.ux.Toast.msg('操作信息', '查询任务完成时限操作失败!');
			}
		});
		//2.查询单一统计类数据（客户类）
		Ext.Ajax.request({
			url : __ctxPath + '/highchart/findSomePersonHighchart.do',
			method : 'POST',
			scope:this,
			success : function(response, request) {
				
				var dataJson = Ext.util.JSON.decode(response.responseText).result;
				
				var dNode2 = $("#zs2");
				var dNode4 = $("#yx2");
				var dNode6 = $("#jh2");
				
				$("#zs1").empty();
				if(dataJson.percentA>0){
					$("#zs1").append(
				    '<div class="red-arrow">环比&nbsp;&nbsp;'+(dataJson.percentA > 1000 ? "999+" : Math.abs(dataJson.percentA))+'%<em><img src="highchart/images/deskImages/red-top.png"/></em></div>'
					);
				}else if(dataJson.percentA==0){
					$("#zs1").append(
				    '<div class="red-arrow">环比&nbsp;&nbsp;<em><img src="highchart/images/deskImages/red_flat.png"/></em></div>'
					);
				}else{
					$("#zs1").append(
				    '<div class="red-arrow">环比&nbsp;&nbsp;'+(dataJson.percentA < (-1000) ? "999+" : Math.abs(dataJson.percentA))+'%<em><img src="highchart/images/deskImages/red-arrow.png"/></em></div>'
					);
				}
				
				dNode2[0].innerHTML=dataJson.sumA;
				
				if(dataJson.percentB>0){
					$("#zs1").append(
				    '<div class="red-arrow green">环比&nbsp;&nbsp;'+(dataJson.percentB > 1000 ? "999+" : Math.abs(dataJson.percentB))+'%<em><img src="highchart/images/deskImages/green-arrow.png"/></em></div>'
					);
				}else if(dataJson.percentB==0){
					$("#zs1").append(
				    '<div class="red-arrow green">环比&nbsp;&nbsp;<em><img src="highchart/images/deskImages/green_flat.png"/></em></div>'
					);
				}else{
					$("#zs1").append(
				    '<div class="red-arrow green">环比&nbsp;&nbsp;'+(dataJson.percentB < (-1000) ? "999+" : Math.abs(dataJson.percentB))+'%<em><img src="highchart/images/deskImages/green-down.png"/></em></div>'
					);
				}
				
				dNode4[0].innerHTML=dataJson.sumB;
				
				$("#jh1").empty();
				if(dataJson.percentC>0){
					$("#jh1").append(
					'<i class="top-arrow"></i>'+(dataJson.percentC > 1000 ? "999+" : Math.abs(dataJson.percentC))+'%'+
				    '<em>环比</em>'
					);
				}else if(dataJson.percentC==0){
					$("#jh1").append(
				    '<i class="flat-arrow"></i>'+
				    '<em>环比</em>'
					);
				}else{
					$("#jh1").append(
				    '<i class="down-arrow"></i>'+(dataJson.percentC < (-1000) ? "999+" : Math.abs(dataJson.percentC))+'%'+
				    '<em>环比</em>'
					);
				}
				
				dNode6[0].innerHTML=dataJson.sumC;
				
			},
			failure : function(response,request) {
				Ext.ux.Toast.msg('操作信息', '查询任务完成时限操作失败!');
			}
		});
		//3.查询业务排行榜
		Ext.Ajax.request({
			url : __ctxPath + '/highchart/findSomeRankingHighchart.do',
			method : 'POST',
			scope:this,
			success : function(response, request) {
				var taskList = Ext.util.JSON.decode(response.responseText);
				$("#myTr").empty();
				$("#myTr").append('<tr>'+
                      '<th align="center" valign="middle">排名</th>'+
                      '<th align="center" valign="middle">姓名</th>'+
                      '<th align="center" valign="middle">部门</th>'+
                      '<th align="center" valign="middle">销售额</th>'+
                 '</tr>');
				for(var i=0;i<taskList.result.length;i++){
					if(i==0){
						$("#myTr").append(
						'<tr class="cur">'+
                	    '<td align="center" valign="middle">'+(i+1)+'</td>'+
                	    '<td align="center" valign="middle">'+taskList.result[i].nameA+'</td>'+
                	    '<td align="center" valign="middle">'+(taskList.result[i].nameB.length>8?taskList.result[i].nameB.substring(0,7):taskList.result[i].nameB)+'</td>'+
                        '<td align="center" valign="middle">'+taskList.result[i].moneyA+'万<img src="highchart/images/deskImages/hot.png"/></td>'+
              	        '</tr>'
						);
					}else{
						$("#myTr").append(
						'<tr>'+
                	    '<td align="center" valign="middle">'+(i+1)+'</td>'+
                	    '<td align="center" valign="middle">'+taskList.result[i].nameA+'</td>'+
                	    '<td align="center" valign="middle">'+(taskList.result[i].nameB.length>8?taskList.result[i].nameB.substring(0,7):taskList.result[i].nameB)+'</td>'+
                        '<td align="center" valign="middle">'+taskList.result[i].moneyA+'万</td>'+
              	        '</tr>'
						);
					}
					if(i==8){
						break;
					}
	        	}
			},
			failure : function(response,request) {
				Ext.ux.Toast.msg('操作信息', '查询任务完成时限操作失败!');
			}
		});
		//4.查询往期销售冠军
		Ext.Ajax.request({
			url : __ctxPath + '/highchart/findSomeToChampionHighchart.do',
			method : 'POST',
			scope:this,
			success : function(response, request) {
				var taskList = Ext.util.JSON.decode(response.responseText);
				$("#myGj").empty();
				$("#myGj").append(
				'<tr>'+
    	        '<th align="center" valign="middle">姓名</th>'+
    	        '<th align="center" valign="middle">部门</th>'+
                '<th align="center" valign="middle">销售额</th>'+
                '<th align="center" valign="middle">销售月度</th>'+
	            '</tr>');
				for(var i=0;i<taskList.result.length;i++){
					$("#myGj").append(
						'<tr>'+
                	    '<td align="center" valign="middle">'+taskList.result[i].nameA+'</td>'+
                	    '<td align="center" valign="middle">'+(taskList.result[i].nameB.length>8?taskList.result[i].nameB.substring(0,7):taskList.result[i].nameB)+'</td>'+
                        '<td align="center" valign="middle">'+taskList.result[i].moneyA+'万<img src="highchart/images/deskImages/hot.png"/></td>'+
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
		//4.查询提前赎回项目
		Ext.Ajax.request({
			url : __ctxPath + '/highchart/findSomeEarlyRedemptionHighchart.do',
			method : 'POST',
			scope:this,
			success : function(response, request) {
				var taskList = Ext.util.JSON.decode(response.responseText);
				$("#mySh").empty();
				$("#mySh").append(
				'<tr>'+
                '<th align="center" valign="middle">投资人</th>'+
                '<th align="center" valign="middle">申请赎回日期</th>'+
                '<th align="center" valign="middle">办理状态</th>'+
	            '</tr>');
				for(var i=0;i<taskList.result.length;i++){
					$("#mySh").append(
						'<tr>'+
                	    '<td align="center" valign="middle" title="'+taskList.result[i].nameA+'">'+(taskList.result[i].nameA.length>10?taskList.result[i].nameA.substring(0,9):taskList.result[i].nameA)+'</td>'+
              	        '<td align="center" valign="middle" title="'+taskList.result[i].nameA+'">'+taskList.result[i].searchDate+'</td>'+
                	    '<td align="center" valign="middle" title="'+taskList.result[i].nameA+'">'+taskList.result[i].nameB+'</td>'+
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
			WealthSalesTrends.ajax(year);
		}
		
		//加载年度交易额统计
		try{
			WealthSalesTrends.ajax();
		}catch(err){
		
		}
		//加载月度成交统计
		try{
			MonthTypeSalesTrends.ajax();
		}catch(err){
		
		}
		
		
		this.info='<div class="container-all">'+
    				'<div class="content">'+
				  	   '<div class="data-left-all">'+
				  	   		'<div class="data-left">'+
				  	   			'<div class="row">'+
				  	   				'<div class="info-box w-30">'+
				                       '<span class="info-box-icon bg-color-1" id="je1">'+
				                           '<i class="top-arrow"></i> 10%'+
				                            '<em>环比</em>'+
				                        '</span>'+
				                        '<div class="info-box-content">'+
				                            '<span class="info-box-text colr-one"><em id="je2">2363.35</em></span>'+
				                            '<span class="info-box-number">本月交易</span>'+
				                        '</div>'+
				                    '</div>'+
				                    '<div class="info-box w-30 mr-0-cf">'+
				                        '<span class="info-box-icon bg-color-2" id="bs1">'+
				                            '<i class="down-arrow"></i>10%'+
				                            '<em>环比</em>'+
				                        '</span>'+
				                        '<div class="info-box-content">'+
				                           ' <span class="info-box-text colr-two" ><em id="bs2">58</em>笔</span>'+
				                            '<span class="info-box-number">本月成单</span>'+
				                        '</div>'+
				                    '</div>'+
				                    '<div class="info-box w-30 mr-0 ">'+
				                       ' <span class="info-box-icon bg-color-3" id="jh1">'+
				                            '<i class="down-arrow"></i>10%'+
				                            '<em>环比</em>'+
				                       ' </span>'+
				                        '<div class="info-box-content">'+
				                            '<span class="info-box-text colr-three" ><em id="jh2">11</em>人</span>'+
				                            '<span class="info-box-number">本月转化</span>'+
				                        '</div>'+
				                    '</div>'+
				                  '</div>'+ 
				                  '<div class="clearfix"></div>'+
				                  '<div class="data-list01">'+
				                    '<h2>年度交易额统计'+
				                        '<p class="select-box">'+
				                        	'<select id="yearData" onchange="changeDate(this.options[this.options.selectedIndex].value)">'+
					                        	'<option>2010</option>'+
					                         '</select>'+
				                        '</p>'+
				                    '</h2>'+
				                    '<div class="dynamic-graph txt-alg" id="WealthSalesTrends" ><img src="highchart/images/deskImages/pic1.png"/></div>'+
				                '</div>'+
				                '<div class="data-list01 data-list02">'+
				                    '<h2>月度成交统计</h2>'+
				                    '<div class="dynamic-graph txt-alg" id="MonthTypeSalesTrends" >'+
				                        '<img src="highchart/images/deskImages/pic2.png"/>'+
				                    '</div>'+
				                '</div>'+
				                '<div class="clearfix"></div>'+
				                '<div class="data-list01">'+
				                    '<h2>本月新增投资客户</h2>'+
				                    '<div class="dynamic-graph" style="height:138px; padding:20px 0;">'+
				                        '<div class="raise">'+
				                            '<p class="f14"><em></em><span id="zs2">25</span>人</p>'+
				                            '<p class="f16">本月新增正式客户</p>'+
				                        '</div>'+
				                        '<div class="raise orange">'+
				                            '<p class="f14"><em></em><span id="yx2">39</span>人</p>'+
				                            '<p class="f16">本月新增意向客户</p>'+
				                        '</div>'+
				                    '</div>'+
				                    '<div class="raise-data" id="zs1">'+
				                        '<div class="red-arrow">环比&nbsp;&nbsp;4%<em><img src="highchart/images/deskImages/red-arrow.png"/></em></div>'+
				                        '<div class="red-arrow green">环比&nbsp;&nbsp;4%<em><img src="highchart/images/deskImages/green-arrow.png"/></em></div>'+
				                    '</div>'+
				                '</div>'+
				                '<div class="data-list01 data-list02" style="height:270px;">'+
                                 	  '<h2>提前赎回项目</h2>'+
                                      '<div class="best-list">'+
                                           '<table width="100%" border="0" cellspacing="0" cellpadding="0" id="mySh">'+
                                                 /* '<tr>'+
                                                       '<th align="center" valign="middle">投资人</th>'+
                                                       '<th align="center" valign="middle">申请赎回日期</th>'+
                                                       '<th align="center" valign="middle">办理状态</th>'+
                                                  '</tr>'+
                                                  '<tr>'+
                                                       '<td align="center" valign="middle">111</td>'+
                                                       '<td align="center" valign="middle">222</td>'+
                                                       '<td align="center" valign="middle">333</td>'+
                                                  '</tr>'+*/
                                           '</table>'+
                                      '</div>'+
                                 '</div>'+
                           '</div>'+
				  	   			 /*'<div class="data-list">'+
				  	   			 	  '<dl>'+
                	                       '<dt id="je1">'+
                    	                        '<p class="f28"><em class="top-arrow"></em><span class="f28" >0</span>%</p>'+
                        						'<p class="f16">环比</p>'+
                                           '</dt>'+
                                           '<dd>'+
                    							'<p class="f28"><span class="f28" id="je2">0.00</span>万</p>'+
                        						'<p class="f16">本月交易</p>'+
                                           '</dd>'+
                                      '</dl>'+
				  	   			 '</div>'+
				  	   			 '<div class="data-list list-two">'+
            	                      '<dl>'+
                	                       '<dt id="bs1">'+
                    	                        '<p class="f28"><em class="down-arrow"></em><span class="f28" id="bs1">0</span>%</p>'+
                                                '<p class="f16">环比</p>'+
                                           '</dt>'+
                                           '<dd>'+
                    	                        '<p class="f28"><span class="f28" id="bs2">0</span>笔</p>'+
                                                '<p class="f16">本月成单</p>'+
                                           '</dd>'+
                                      '</dl>'+
                                 '</div>'+
                                 '<div class="data-list list-three">'+
            	                      '<dl>'+
                	                       '<dt id="jh1">'+
                    	                        '<p class="f28"><em class="down-arrow"></em>10%</p>'+
                                                '<p class="f16">环比</p>'+
                                           '</dt>'+
                                           '<dd>'+
                    	                        '<p class="f28"><span class="f28" id="jh2">11</span>人</p>'+
                                                '<p class="f16">本月新增客户</p>'+
                                           '</dd>'+
                                      '</dl>'+
                                 '</div>'+
				  	   		'</div>'+
				  	   		'<div class="data-left">'+
				  	   			 '<div class="data-list01">'+
                                      '<h2>年度交易额统计'+
                	                       '<p class="select-box">'+
                                              '<select id="yearData" onchange="changeDate(this.options[this.options.selectedIndex].value)">'+
					                        	'<option>2010</option>'+
					                          '</select>'+
                                           '</p>'+
                                      '</h2>'+
              	                      '<div style="height:200px;" class="dynamic-graph txt-alg" id="WealthSalesTrends">' +
              	                      '<img src="highchart/images/wealth/pic1.png"/>' +
              	                      '</div>'+
                                 '</div>'+
                                 '<div class="data-list01 data-list02">'+
                                      '<h2>本月新增投资客户</h2>'+
				                      '<div class="dynamic-graph">'+
                	                       '<div class="raise">'+
                    	                        '<p class="f14"><em></em><span id="zs2">25</span>人</p>'+
                                                '<p class="f16">本月新增正式客户</p>'+
                                           '</div>'+
                                           '<div class="raise orange">'+
                    	                        '<p class="f14"><em></em><span id="yx2">39</span>人</p>'+
                                                '<p class="f16">本月新增意向客户</p>'+
                                           '</div>'+
                                      '</div>'+
                                      '<div class="raise-data" id="zs1">'+
                	                       '<div class="red-arrow">环比&nbsp;&nbsp;4%<em><img src="highchart/images/wealth/red-arrow.png"/></em></div>'+
                                           '<div class="red-arrow green">环比&nbsp;&nbsp;4%<em><img src="highchart/images/wealth/green-arrow.png"/></em></div>'+
                                      '</div>'+
                                 '</div>'+
				  	   		'</div>'+
				  	   		'<div class="data-left">'+
				  	   			 '<div class="data-list01">'+
                                      '<h2>月度成交统计</h2>'+
                                      '<div style="height:200px;" class="dynamic-graph txt-alg" id="MonthTypeSalesTrends">'+
                                           '<img src="highchart/images/wealth/pic2.png"/>'+
                                      '</div>'+
                                 '</div>'+
                                 '<div class="data-list01 data-list02">'+
                                 	  '<h2>提前赎回项目</h2>'+
                                      '<div class="sapn-list">'+
                                           '<table width="100%" border="0" cellspacing="0" cellpadding="0" id="mySh">'+
                                                  '<tr>'+
                                                       '<th align="center" valign="middle">项目名称</th>'+
                                                       '<th align="center" valign="middle">提前赎回时间</th>'+
                                                       '<th align="center" valign="middle">办理状态</th>'+
                                                  '</tr>'+
                                                  '<tr>'+
                                                       '<td align="center" valign="middle">111</td>'+
                                                       '<td align="center" valign="middle">222</td>'+
                                                       '<td align="center" valign="middle">333</td>'+
                                                  '</tr>'+
                                           '</table>'+
                                      '</div>'+
                                 '</div>'+
				  	   		'</div>'+
				  	   '</div>'+*/
				  	   '<div class="data-right">'+
				  	        '<div class="best-seller">'+
				  	             '<h2>业务排行榜</h2>'+
				  	             '<div class="best-list" style="height:310px;">'+
				  	                  '<table width="100%" border="0" cellspacing="0" cellpadding="0" id="myTr">'+
				  	                         /*'<tr>'+
                	                              '<th align="center" valign="middle">排名</th>'+
                	                              '<th align="center" valign="middle">姓名</th>'+
                	                              '<th align="center" valign="middle">部门</th>'+
                                                  '<th align="center" valign="middle">销售额</th>'+
              	                             '</tr>'+*/
				  	                         /*'<tr class="cur" id="myTr">'+
                	                             '<td align="center" valign="middle">1</td>'+
                	                             '<td align="center" valign="middle">杜拉拉</td>'+
                	                             '<td align="center" valign="middle">业务部</td>'+
                                                 '<td align="center" valign="middle">3000万<img src="highchart/images/wealth/hot.png"/></td>'+
              	                             '</tr>'+*/
				  	                         
				  	                  '</table>'+
				  	             '</div>'+
				  	             '<div class="line"></div>'+
				  	             '<h2>往期销售冠军</h2>'+
				  	             '<div class="best-list" style="height:310px;">'+
				  	                  '<table width="100%" border="0" cellspacing="0" cellpadding="0" id="myGj">'+
				  	                        /* '<tr>'+
						                	      '<th align="center" valign="middle">姓名</th>'+
						                	      '<th align="center" valign="middle">部门</th>'+
						                          '<th align="center" valign="middle">销售额</th>'+
						                          '<th align="center" valign="middle">销售月度</th>'+
						              	     '</tr>'+
						              	     '<tr>'+
                	                              '<td align="center" valign="middle">杜拉拉</td>'+
                	                              '<td align="center" valign="middle">业务部</td>'+
                                                  '<td align="center" valign="middle">200万<img src="highchart/images/wealth/hot.png"/></td>'+
                	                              '<td align="center" valign="middle">2016-3</td>'+
              	                             '</tr>'+*/
				  	                  '</table>'+
				  	             '</div>'+
				  	        '</div>'+
				  	   '</div>'+
				  	   '</div>'+
				  	   '</div>'+
		          '</div>';
	}
});