Ext.namespace("planMarket");
$(function () {
	 planMarket.saleDivId="#planMarket",
	 planMarket.url = __ctxPath + "/highchart/getPlanManageMarketHighchart.do",
	 planMarket.chart = {
	 	chart: {
            type: 'column'
        },
        title: {
            text: 'U/D债权转让金额'
        },
        xAxis: {
            categories: []
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
        tooltip: {
            headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
            pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
                '<td style="padding:0;width:20px;"><b>{point.y:.1f} 万</b></td></tr>',
            footerFormat: '</table>',
            shared: true,
            useHTML: true
        },
        plotOptions: {
            column: {
                pointPadding: 0.2,
                borderWidth: 0
            }
        },
        series: [{
            name: 'U债权',
            data: []

        } ,{
            name: 'D债权',
            data: []

        }],
        credits:{
	        	text:''//去掉右下角的Highcharts.com超链接
		 }
	};
	planMarket.ajax=function(dataState){
		planMarket.chart.series[0].data.clear();
		planMarket.chart.series[1].data.clear();
		  $.ajax({
	        type:"POST",
	        url:planMarket.url,//提供数据的Servlet
	        dataType : "JSON",
	        data:{dataState:dataState},
	        success:function(response,request){
	        		var list = response.result;
					var xatrnames = [];
		            for (var i =0;i<list.length;i++) {
		                xatrnames.push(list[i].searchDate);
				        planMarket.chart.series[0].data.push(list[i].moneyA);
				        planMarket.chart.series[1].data.push(list[i].moneyB);
		            }
		            planMarket.chart.xAxis.categories = xatrnames
	            $(planMarket.saleDivId).highcharts(planMarket.chart);
	        },
	        error:function(e){
	        	$(planMarket.saleDivId).highcharts(planMarket.chart);
	        }
	    });
	};
});