Ext.namespace("LoanOverdueTrends");
$(function () {
	 LoanOverdueTrends.saleDivId="#LoanOverdueTrends",
	 LoanOverdueTrends.url = __ctxPath + "/highchart/findLoanOverdueStatisticsHighchart.do",
	 LoanOverdueTrends.chart = {
	 	chart: {
            type: 'column'
        },
        colors: [ "#6ca30f"  ,  '#f59311',   '#fa4343'],
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
                stacking: 'normal'
            }
        },
        series: [{
            name: '正常贷款',
            data: []

        },{
            name: '逾期贷款',
            data: []

        },{
            name: '已还逾期贷款',
            data: []

        }],
        credits:{
	        	text:''//去掉右下角的Highcharts.com超链接
		        }
	};
	LoanOverdueTrends.ajax=function(v){
		if(typeof(v)=="undefined" || v==""){
			v=new Date().getFullYear();
		}
		//异步请求数据
	    $.ajax({
	        type:"POST",
	        url:LoanOverdueTrends.url,//提供数据的Servlet
	        dataType : "JSON",
	        data:{type:v},
	        success:function(responseText, statusText){
	     /*   	//查询日期赋值
	        	$("#yearLoanData"+f).empty();
				for(var i=(-10) ; i<=10 ; i++){
					var newDate = new Date().getFullYear()
					if(v==(newDate+i)){
						$("#yearLoanData"+f).append(
						'<option selected="selected">'+(v)+'</option>'
						);
					}else{
						$("#yearLoanData"+f).append(
						'<option>'+(newDate+i)+'</option>'
						);
					}
				}*/
	        	
	            //调整图宽、图高
	        	/*WealthSalesTrends.chart.chart.width=$("#tb-box1").width()*0.973;
	        	WealthSalesTrends.chart.chart.height=$(WealthSalesTrends.saleDivId).height()*0.98;*/
	        	//数据源
				var list = JSON.parse(responseText.result);
				LoanOverdueTrends.chart.xAxis.categories=[];
				LoanOverdueTrends.chart.series[0].data=[];
				LoanOverdueTrends.chart.series[1].data=[];
				LoanOverdueTrends.chart.series[2].data=[];
				 
				//赋值
	            for (var i =0;i<list.length;i++) {
			        LoanOverdueTrends.chart.xAxis.categories.push(list[i].searchDate);
			       LoanOverdueTrends.chart.series[2].data.push(list[i].moneyC);
			        LoanOverdueTrends.chart.series[0].data.push(list[i].moneyB);
			        LoanOverdueTrends.chart.series[1].data.push(list[i].moneyA);
	            }
	           
	            $(LoanOverdueTrends.saleDivId).highcharts(LoanOverdueTrends.chart);
	        },
	        error:function(e){
	        	$(LoanOverdueTrends.saleDivId).highcharts(LoanOverdueTrends.chart);
	        }
	    });
	};
});