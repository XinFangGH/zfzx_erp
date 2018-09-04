Ext.namespace("planFundAnalyze");
$(function () {
	 planFundAnalyze.saleDivId="#planFundAnalyze",
	 planFundAnalyze.url = __ctxPath + "/highchart/getPlanFundAnalyzeHighchart.do",
	 planFundAnalyze.chart = {chart: {
            type: 'column'
        },
        title: {
            text: ''
        },
        xAxis: {
            categories: ['一月份', '二月份', '三月份', '四月份', '五月份', '六月份', '七月份', '八月份', '九月份', '十月份', '十一月份', '十二月份']
        }, 
        yAxis: {
			            labels: {
			                format: '{value}万',
			                style: {
			                    color: '#4572A7'
			                }
			            },
			            title: {
			                text: '金额/万元',
			                style: {
			                    color: '#4572A7'
			                }
			            }
		},
        credits: {
            enabled: false
        },
        series: [{
            name: '理财金额',
            data: []
        }, {
            name: '差额',
            data: []
        }, {
            name: '债权金额',
            data: []
        }],
          credits:{
	        	text:''//去掉右下角的Highcharts.com超链接
		 }
	};
	planFundAnalyze.ajax=function(dataState){
		planFundAnalyze.chart.series[0].data.clear();
		planFundAnalyze.chart.series[1].data.clear();
		planFundAnalyze.chart.series[2].data.clear();
		  $.ajax({
	        type:"POST",
	        url:planFundAnalyze.url,//提供数据的Servlet
	        dataType : "JSON",
	        data:{dataState:dataState},
	        success:function(response,request){	
	        	var list = response.result;
		            for (var i =0;i<list.length;i++) {
				        planFundAnalyze.chart.series[0].data.push(list[i].moneyA);
				        planFundAnalyze.chart.series[1].data.push(list[i].moneyB);
				        planFundAnalyze.chart.series[2].data.push(list[i].moneyC);
		            }
	            $(planFundAnalyze.saleDivId).highcharts(planFundAnalyze.chart);
	        },
	        error:function(e){
	        	$(planFundAnalyze.saleDivId).highcharts(planFundAnalyze.chart);
	        }
	    });
	};
});