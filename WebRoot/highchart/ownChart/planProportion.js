Ext.namespace("planProportion");
$(function () {
	 planProportion.saleDivId="#planProportion",
	 planProportion.url = __ctxPath + "/highchart/getPlanProportionHighchart.do",
	 planProportion.chart = {chart: {
            plotBackgroundColor: null,
            plotBorderWidth: null,
            plotShadow: false
        },
        title: {
            text: ''
        },
        colors:['#7cb5ec','#f286a0',  '#fba115'],
        tooltip: {
    	    pointFormat: '{series.name}:<br><b>{point.percentage:.1f}%</b>'
        },
        plotOptions: {
            pie: {
                allowPointSelect: true,
                cursor: 'pointer',
                 showInLegend: true,
                 innerSize:80,
				size:'100%',
                dataLabels: {
                    enabled: true,
                    color: '#000000',
                    connectorColor: '#000000',
                    format: '<b>{point.name}</b><br>{point.percentage:.1f} %'
                }
            }
        },
        series: [{
            type: 'pie',
            name: '占比',
            data: []
        }],
        credits:{
	        	text:''//去掉右下角的Highcharts.com超链接
		 }};
	planProportion.ajax=function(dataState){
		planProportion.chart.series[0].data.clear();
		  $.ajax({
	        type:"POST",
	        url:planProportion.url,//提供数据的Servlet
	        dataType : "JSON",
	        data:{dataState:dataState},
	        success:function(response,request){	
	        	var list = response.result;
		            for (var i =0;i<list.length;i++) {
		        		var arr=[list[i].nameA,list[i].percentA];
				        planProportion.chart.series[0].data.push(arr);
		            }
	            $(planProportion.saleDivId).highcharts(planProportion.chart);
	        },
	        error:function(e){
	        	$(planProportion.saleDivId).highcharts(planProportion.chart);
	        }
	    });
	};
});