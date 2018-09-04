Ext.namespace("CustomerOverdueTrends");
$(function () {
	 CustomerOverdueTrends.saleDivId="#CustomerOverdueTrends",
	 CustomerOverdueTrends.url = __ctxPath + "/highchart/findOverdueMoneyHighchart.do",
	 CustomerOverdueTrends.chart = {
	 	chart: {
            type: 'pie'
        },
        colors: ['#7cb5ec', '#9acd32'],
        title: {
            text: ''
        },
        tooltip: {//光标锁定提示框信息
            pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b><b>金额:{point.y}</b>万元'
        },
        noData:"",
        plotOptions: {
            pie: {
                allowPointSelect: true,
                cursor: 'pointer',
                innerSize:110,
				size:'90%',
                dataLabels: {//静态提示框信息
                    enabled: true,
                   // format: '{point.name}:<b>{point.percentage:.1f}%</b>'
                    formatter:function(){
						if (this.percentage > 0){  
						        return '<b>' + this.point.name + '</b>: ' + this.percentage + ' %';
						}
					}
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
	CustomerOverdueTrends.ajax=function(v){
		/*if(typeof(v)=="undefined"){
			v='day';
		}*/
		//异步请求数据
	    $.ajax({
	        type:"POST",
	        url:CustomerOverdueTrends.url,//提供数据的Servlet
	        dataType : "JSON",
	        data:{type:v},
	        success:function(responseText, statusText){
	        	//数据源
				var list = JSON.parse(responseText.result);
				CustomerOverdueTrends.chart.series[0].data=[];
				//赋值
	            for (var i =0;i<list.length;i++) {
	            	var arr=[list[i].nameA,list[i].moneyA];
			        CustomerOverdueTrends.chart.series[0].data.push(arr);
	            	
	            }
	            $(CustomerOverdueTrends.saleDivId).highcharts(CustomerOverdueTrends.chart);
	        },
	        error:function(e){
	        	$(CustomerOverdueTrends.saleDivId).highcharts(CustomerOverdueTrends.chart);
	        }
	    });
	};
});