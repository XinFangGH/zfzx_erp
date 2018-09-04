
//BidExceptionMap.js

Ext.namespace("BidExceptionMap");
	/**
	 * 散标异常情况管理图
	 * @param {} v
	 */
	BidExceptionMap.ajax1=function(id,v){
		if(typeof(v)=="undefined"){
			v=2;
		}
		 BidExceptionMap.saleDivId="#BidExceptionMap",
		 BidExceptionMap.url = __ctxPath + "/highchart/bidExceptionMapHighchart.do",
		 BidExceptionMap.chart = {
		 	chart: {
	            type: 'column'
	        },
	        title: {
	            text: ''
	        },
	        colors: [ "#6ca30f",'#f9a63e','#7cb5ec','#fa4343','#f286a0', '#b23438', '#b9884d','#fba115','#2f7ed8', '#0d233a'],
	        xAxis: {
	        	categories:['p2p','c2p','pa2p','ca2p']
	        },
	        yAxis: {
	            title: {
                	text: '金额：元'
	            }
	        },
	        series: [],
	        credits:{
		        	text:''//去掉右下角的Highcharts.com超链接
			        }
		};
		
		//异步请求数据
	    $.ajax({
	        type:"POST",
	        url:BidExceptionMap.url,//提供数据的Servlet
	        dataType : "JSON",
	        data:{type:v},
	        success:function(responseText, statusText){
	        	//数据源
				var list = JSON.parse(responseText.result);
				// BidExceptionMap.chart.xAxis.categories=list.xAxis;
				//赋值
	            BidExceptionMap.chart.series=list.series;
	            $(BidExceptionMap.saleDivId).highcharts(BidExceptionMap.chart);
	        },
	        error:function(e){
	        	$(BidExceptionMap.saleDivId).highcharts(BidExceptionMap.chart);
	        }
	    });
	}