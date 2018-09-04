//BidSaleStatistics
/**
	 * 散标销售统计图
	 * @param {} v
	 */
	  Ext.namespace("BidSaleStatistics");
	BidSaleStatistics.ajax4=function(id,v){
			if(typeof(v)=="undefined"){
				v=2;
			}
			
	
			 BidSaleStatistics.saleDivId=id,
			 BidSaleStatistics.url = __ctxPath + "/highchart/bidSaleStatisticsHighchart.do",
			 BidSaleStatistics.chart = {
			 	
		        title: {
		            text: ''
		        },
		        colors: [ "#6ca30f",'#f59311','#fa4343','#7cb5ec','#f286a0', '#b23438', '#b9884d','#fba115','#2f7ed8', '#0d233a'],
		        xAxis: {
					
		        },
		        yAxis: {
		            title: {
		                text: '金额（元）'
		            },
		            plotLines: [{
		                value: 0,
		                width: 1,
		                color: '#808080'
		            }]
		        },
		        legend: {
		            layout: 'vertical',
		            align: 'right',
		            verticalAlign: 'middle',
		            borderWidth: 0
		        },
		        series: [],
		        credits:{
			        	text:''//去掉右下角的Highcharts.com超链接
				        }
			};
			
			//异步请求数据
		    $.ajax({
		        type:"POST",
		        url:BidSaleStatistics.url,//提供数据的Servlet
		        dataType : "JSON",
		        data:{type:v},
		        success:function(responseText, statusText){
		        	//数据源
					var list = JSON.parse(responseText.result);
		        	BidSaleStatistics.chart.xAxis.categories=list.xAxis;
					//赋值
		            BidSaleStatistics.chart.series=list.series;
		            
		            $(BidSaleStatistics.saleDivId).highcharts(BidSaleStatistics.chart);
		        },
		        error:function(e){
		        	$(BidSaleStatistics.saleDivId).highcharts(BidSaleStatistics.chart);
		        }
		    });
		};