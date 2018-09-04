Ext.namespace("SalesTrends");
$(function () {
	 SalesTrends.saleDivId="#factoring_salesTrends",
	 SalesTrends.url = __ctxPath + "/factoring/findSomeInfoFactoringProject.do",
	 SalesTrends.chart = {
	 	chart: {
            type: 'area',
            width:0,
            height:0
        },
        title: {
            text: ''
        },
        xAxis: {
        	categories:[]
        },
        yAxis: {
            title: {
                text: '金额(人民币:元)'
            },
            labels: {
	           formatter: function () {
	              return this.value / 1000 + 'k';
	           }
	        }
        },
        tooltip: {
            pointFormat: '金额: <b>{point.y:,.0f}元</b>'
        },
        credits:{
        	text:''//去掉右下角的Highcharts.com超链接
        },
        series: [{
        	name:'日期',
        	data:[]
        }]
	};
	SalesTrends.ajax=function(v){
		if(typeof(v)=="undefined"){
			v='day';
		}
		//异步请求数据
	    $.ajax({
	        type:"POST",
	        url:SalesTrends.url,//提供数据的Servlet
	        dataType : "JSON",
	        data:{type:v},
	        success:function(responseText, statusText){
	        	SalesTrends.chart.chart.width=$("#tb-box1").width()*0.973;
	        	SalesTrends.chart.chart.height=$(SalesTrends.saleDivId).height()*0.98;
				var list = JSON.parse(responseText.result);
				SalesTrends.chart.xAxis.categories=[];
				SalesTrends.chart.series[0].data=[];
	            for (var i =0;i<list.length;i++) {
			        SalesTrends.chart.xAxis.categories.push(list[i].countDate);
			        if(list[i].projectMoney==0){
				        SalesTrends.chart.series[0].data.push(null);
			        }else{
				        SalesTrends.chart.series[0].data.push(list[i].projectMoney);
			        }
	            }
	            SalesTrends.chart.xAxis.categories.push("");
	            SalesTrends.chart.series[0].data.push(null);
	            $(SalesTrends.saleDivId).highcharts(SalesTrends.chart);
	        },
	        error:function(e){
	        	$(SalesTrends.saleDivId).highcharts(SalesTrends.chart);
	        }
	    });
	};
});