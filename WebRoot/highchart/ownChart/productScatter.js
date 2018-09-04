Ext.namespace("ProductScatter");
$(function () {
	 ProductScatter.productDivId="#factoring_productScatter",
	 ProductScatter.url = __ctxPath + "/factoring/findSomeInfoFactoringProduct.do",
	 ProductScatter.chart = {
	 	chart: {
            width:0,
            height:0
        },
        title: {
            text: ''
        },
        credits:{
        	text:''//去掉右下角的Highcharts.com超链接
        },
        tooltip: {
    	    pointFormat: '{series.name}:<b>{point.percentage:.2f}%</b><b>金额:{point.y}</b>元'
        },
        plotOptions: {
            pie: {
            	showInLegend: true,
                allowPointSelect: true,
                cursor: 'pointer',
                dataLabels: {
                    enabled: true,
                    color: '#000000',
                    connectorColor: '#000000',
                    format: '<b>{point.name}</b>: {point.percentage:.2f} %'
                }
            }
        },
        series: [{
            type: 'pie',
            name: '占比',
            data: []
        }]
	 }
	 ProductScatter.ajax=function(v){
		if(typeof(v)=="undefined"){
			v='day';
		}
		//异步请求数据
	    $.ajax({
	        type:"POST",
	        url:ProductScatter.url,//提供数据的Servlet
	        dataType : "JSON",
	        data:{type:v},
	        success:function(responseText, statusText){
	        	ProductScatter.chart.chart.width=$("#tb-box2").width()*0.973;
	        	ProductScatter.chart.chart.height=$(ProductScatter.productDivId).height()*0.98;
				var list = JSON.parse(responseText.result);
				ProductScatter.chart.series[0].data=[];
	            for (var i =0;i<list.length;i++) {
	            	if(i==0){
	            		ProductScatter.chart.series[0].data.push({
		                    name: list[0].productName,
		                    y: list[0].projectMoney,
		                    sliced: true,
		                    selected: true
		                });
	            	}else{
		            	var arr=[list[i].productName,list[i].projectMoney,list[i].projectMoney];
				        ProductScatter.chart.series[0].data.push(arr);
	            	}
	            }
	            if(list.length>0){
	            	$(ProductScatter.productDivId).highcharts(ProductScatter.chart);
	            }else{
	            	$(ProductScatter.productDivId).text("");
	            	$(ProductScatter.productDivId).append('<p style="margin:40% 0;font-size:20;">No data to display</p>');
	            }
	        },
	        error:function(e){
//	        	$(ProductScatter.productDivId).highcharts(ProductScatter.chart);
	        }
	    });
	};
});