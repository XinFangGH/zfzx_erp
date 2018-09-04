Ext.namespace("LoanMoneyYearTrends");

	LoanMoneyYearTrends.ajax=function(v,f){
		LoanMoneyYearTrends.saleDivId="#LoanMoneyYearTrends"+f;
		
		if(typeof(v)=="undefined" || v==""){
			v=new Date().getFullYear();
		}
		
		 LoanMoneyYearTrends.url = __ctxPath + "/highchart/findLoanYearStatisticsHighchart.do",
		 LoanMoneyYearTrends.chart = {
		 	chart: {
	            type: 'column'
	        },
	       colors: [ "#6ca30f",'#f59311','#fa4343','#7cb5ec','#f286a0', '#b23438', '#b9884d','#fba115','#2f7ed8', '#0d233a'],
	        title: {
	            text: ''
	        },
	        xAxis: {
	            categories: []
	        },
	        yAxis: {
	            min: 0,
	            title: {
	                text: '金额(人民币:万元)'
	            }
	        },
	        plotOptions: {
	            column: {
	                pointPadding: 0.2,
	                borderWidth: 0
	            }
	        },
	        series: [{
	            name: '放款额',
	            data: []
	
	        }],
	        credits:{
		        	text:''//去掉右下角的Highcharts.com超链接
			        }
		};
		
		//异步请求数据
	    $.ajax({
	        type:"POST",
	        url:LoanMoneyYearTrends.url,//提供数据的Servlet
	        dataType : "JSON",
	        data:{type:v,flag:f},
	        success:function(responseText, statusText){
	        	
	        	//查询日期赋值
	        	$("#yearLoanData"+f).empty();
				for(var i=(-10) ; i<=10 ; i++){
					var newDate = new Date().getFullYear()
					if(v==(newDate+i)){
						$("#yearLoanData"+f).append(
						'<option selected="selected">'+(v)+'</option>'
						);
					}else{
						$("#yearLoanData"+f).append(
						'<option>'+(newDate+i)+'</option>'
						);
					}
				}
	        	
	            //调整图宽、图高
	        	/*WealthSalesTrends.chart.chart.width=$("#tb-box1").width()*0.973;
	        	WealthSalesTrends.chart.chart.height=$(WealthSalesTrends.saleDivId).height()*0.98;*/
	        	//数据源
				var list = JSON.parse(responseText.result);
				LoanMoneyYearTrends.chart.xAxis.categories=[];
				LoanMoneyYearTrends.chart.series[0].data=[];
				//赋值
	            for (var i =0;i<list.length;i++) {
			        LoanMoneyYearTrends.chart.xAxis.categories.push(list[i].searchDate);
			        LoanMoneyYearTrends.chart.series[0].data.push(list[i].moneyA);
	            }
	            $(LoanMoneyYearTrends.saleDivId).highcharts(LoanMoneyYearTrends.chart);
	        },
	        error:function(e){
	        	$(LoanMoneyYearTrends.saleDivId).highcharts(LoanMoneyYearTrends.chart);
	        }
	    });
	};
