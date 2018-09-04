Ext.namespace("MonthTypeSalesTrends");
$(function () {
	 MonthTypeSalesTrends.saleDivId="#MonthTypeSalesTrends",
	 MonthTypeSalesTrends.url = __ctxPath + "/highchart/findSomeMonthTypeStatisticsHighchart.do",
	 MonthTypeSalesTrends.chart = {
	 	chart: {
            type: 'pie'
        },
        title: {
            text: ''
        },
        colors: [ "#6ca30f",'#f59311','#fa4343','#7cb5ec','#f286a0', '#b23438', '#b9884d','#fba115','#2f7ed8', '#0d233a'],
        tooltip: {//光标锁定提示框信息
            pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b><b>金额:{point.y}</b>万元'
        },
        plotOptions: {
            pie: {
                allowPointSelect: true,
                cursor: 'pointer',
                innerSize:110,
				size:'90%',
                dataLabels: {//静态提示框信息
                    enabled: true,
                    format: '{point.name}:<br><b>{point.percentage:.1f}%</b>'
                },
                showInLegend: true
            }
        },
        series: [{
            type: 'pie',
            name: '占比',
            data: []
        }],
        credits:{
	        	text:''//去掉右下角的Highcharts.com超链接
		        }
	};
	MonthTypeSalesTrends.ajax=function(v){
		/*if(typeof(v)=="undefined"){
			v='day';
		}*/
		//异步请求数据
	    $.ajax({
	        type:"POST",
	        url:MonthTypeSalesTrends.url,//提供数据的Servlet
	        dataType : "JSON",
	        data:{type:v},
	        success:function(responseText, statusText){
	        	//数据源
				var list = JSON.parse(responseText.result);
				MonthTypeSalesTrends.chart.series[0].data=[];
				//赋值
	            for (var i =0;i<list.length;i++) {
	            	if(typeof(list[i].nameA) != 'undefined'){
		            	var arr=[list[i].nameA,list[i].moneyA];
				        MonthTypeSalesTrends.chart.series[0].data.push(arr);
	            	}
	            }
	            $(MonthTypeSalesTrends.saleDivId).highcharts(MonthTypeSalesTrends.chart);
	        },
	        error:function(e){
	        	$(MonthTypeSalesTrends.saleDivId).highcharts(MonthTypeSalesTrends.chart);
	        }
	    });
	};
});