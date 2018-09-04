Ext.namespace("LoanIncomePayTrends");
$(function () {
	 LoanIncomePayTrends.saleDivId="#LoanIncomePayTrends",
	 LoanIncomePayTrends.url = __ctxPath + "/highchart/findIncomePayStatisticsHighchart.do",
	 LoanIncomePayTrends.chart = {
	 	chart: {
            type: 'column'
        },
        colors: ['#f67254', '#9acd32'],
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
                borderWidth: 0
            }
        },
        series: [{
            name: '收入金额',
            data: []

        },{
            name: '支出金额',
            data: []

        }],
        credits:{
	        	text:''//去掉右下角的Highcharts.com超链接
		        }
	};
	LoanIncomePayTrends.ajax=function(v){
		if(typeof(v)=="undefined" || v==""){
			v=new Date().getFullYear();
		}
		//异步请求数据
	    $.ajax({
	        type:"POST",
	        url:LoanIncomePayTrends.url,//提供数据的Servlet
	        dataType : "JSON",
	        data:{type:v},
	        success:function(responseText, statusText){
	     	//查询日期赋值
	        	$("#yearInPayData").empty();
				for(var i=(-10) ; i<=10 ; i++){
					var newDate = new Date().getFullYear()
					if(v==(newDate+i)){
						$("#yearInPayData").append(
						'<option selected="selected">'+(v)+'</option>'
						);
					}else{
						$("#yearInPayData").append(
						'<option>'+(newDate+i)+'</option>'
						);
					}
				}
	        	
	            //调整图宽、图高
	        	/*WealthSalesTrends.chart.chart.width=$("#tb-box1").width()*0.973;
	        	WealthSalesTrends.chart.chart.height=$(WealthSalesTrends.saleDivId).height()*0.98;*/
	        	//数据源
				var list = JSON.parse(responseText.result);
				LoanIncomePayTrends.chart.xAxis.categories=[];
				LoanIncomePayTrends.chart.series[0].data=[];
				LoanIncomePayTrends.chart.series[1].data=[];
				
				//赋值
	            for (var i =0;i<list.length;i++) {
			        LoanIncomePayTrends.chart.xAxis.categories.push(list[i].searchDate);
			        LoanIncomePayTrends.chart.series[0].data.push(list[i].moneyA);
			        LoanIncomePayTrends.chart.series[1].data.push(list[i].moneyB);
	            }
	            
	            $(LoanIncomePayTrends.saleDivId).highcharts(LoanIncomePayTrends.chart);
	        },
	        error:function(e){
	        	$(LoanIncomePayTrends.saleDivId).highcharts(LoanIncomePayTrends.chart);
	        }
	    });
	};
});