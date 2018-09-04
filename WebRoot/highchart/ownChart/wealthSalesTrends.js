Ext.namespace("WealthSalesTrends");
$(function () {
	 WealthSalesTrends.saleDivId="#WealthSalesTrends",
	 WealthSalesTrends.url = __ctxPath + "/highchart/findSomeYearStatisticsHighchart.do",
	 WealthSalesTrends.chart = {
	 	chart: {
            type: 'column'
        },
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
                borderWidth: 0,
                size:'100%'
            }
        },
        series: [{
            name: '销售额',
            data: []

        }],
        credits:{
	        	text:''//去掉右下角的Highcharts.com超链接
		        }
	};
	WealthSalesTrends.ajax=function(v){
		if(typeof(v)=="undefined"){
			v=new Date().getFullYear();
		}
		//异步请求数据
	    $.ajax({
	        type:"POST",
	        url:WealthSalesTrends.url,//提供数据的Servlet
	        dataType : "JSON",
	        data:{type:v},
	        success:function(responseText, statusText){
	        	
	        	//查询日期赋值
	        	$("#yearData").empty();
				for(var i=(-10) ; i<=10 ; i++){
					var newDate = new Date().getFullYear()
					if(v==(newDate+i)){
						$("#yearData").append(
						'<option selected="selected">'+(v)+'</option>'
						);
					}else{
						$("#yearData").append(
						'<option>'+(newDate+i)+'</option>'
						);
					}
				}
	        	
	        	//数据源
				var list = JSON.parse(responseText.result);
				WealthSalesTrends.chart.xAxis.categories=[];
				WealthSalesTrends.chart.series[0].data=[];
				//赋值
	            for (var i =0;i<list.length;i++) {
			        WealthSalesTrends.chart.xAxis.categories.push(list[i].searchDate);
			        WealthSalesTrends.chart.series[0].data.push(list[i].moneyA);
	            }
	            
	            $(WealthSalesTrends.saleDivId).highcharts(WealthSalesTrends.chart);
	        },
	        error:function(e){
	        	$(WealthSalesTrends.saleDivId).highcharts(WealthSalesTrends.chart);
	        }
	    });
	};
});