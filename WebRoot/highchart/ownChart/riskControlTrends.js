Ext.namespace("RiskControlTrends");
$(function () {
	 RiskControlTrends.saleDivId="#RiskControlTrends",
	 RiskControlTrends.url = __ctxPath + "/highchart/findRiskControlHighchart.do",
	 RiskControlTrends.chart = {
	 	chart: {
            type: 'pie'
        },
        title: {
            text: ''
        },
        tooltip: {//光标锁定提示框信息
            pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b><b>金额:{point.y}</b>万元'
        },
        plotOptions: {
            pie: {
                allowPointSelect: true,
                cursor: 'pointer',
                innerSize:80,
				size:'100%',
                dataLabels: {//静态提示框信息
                    enabled: true,
                    format: '{point.name}:<b>{point.percentage:.1f}%</b>'
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
	RiskControlTrends.ajax=function(v){
		/*if(typeof(v)=="undefined"){
			v='day';
		}*/
		//异步请求数据
	    $.ajax({
	        type:"POST",
	        url:RiskControlTrends.url,//提供数据的Servlet
	        dataType : "JSON",
	        data:{type:v},
	        success:function(responseText, statusText){
	        	//数据源
				var list = JSON.parse(responseText.result);
				RiskControlTrends.chart.series[0].data=[];
				//赋值
	            for (var i =0;i<list.length;i++) {
	            	var arr=[list[i].nameA,list[i].moneyA];
			        RiskControlTrends.chart.series[0].data.push(arr);
	            }
	            $(RiskControlTrends.saleDivId).highcharts(RiskControlTrends.chart);
	        },
	        error:function(e){
	        	$(RiskControlTrends.saleDivId).highcharts(RiskControlTrends.chart);
	        }
	    });
	};
});