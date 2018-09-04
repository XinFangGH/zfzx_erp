Ext.namespace("BidPlanCharts");

	/**
	 * 充值取现趋势图
	 * @param {} v
	 */
	BidPlanCharts.ajax2=function(id,v){
		if(typeof(v)=="undefined"){
			v=2;
		}
		

		 BidPlanCharts.saleDivId=id,
		 BidPlanCharts.url = __ctxPath + "/highchart/onlineRechargeWithDrawChartHighchart.do",
		 BidPlanCharts.chart = {
		 	chart: {
	            type: 'area'
	        },
	        title: {
	            text: ''
	        },
	        colors: [ "#6ca30f",'#f286a0','#fa4343','#7cb5ec','#f286a0', '#b23438', '#b9884d','#fba115','#2f7ed8', '#0d233a'],
	        xAxis: {
				
	        },
	        yAxis: {
	            title: {
                	text: '金额：元'
	            },
	            labels: {
	                formatter: function() {
	                    return this.value/1000 +"k";
	                }
	            }
	        },
	        plotOptions: {
	            area: {
	                marker: {
                    enabled: false,
                    symbol: 'circle',
                    radius: 2,
                    states: {
                        hover: {
                            enabled: true
                        }
                    }
                }
	            }
	        },
	        series: [],
	        credits:{
		        	text:''//去掉右下角的Highcharts.com超链接
			        }
		};
		
		//异步请求数据
	    $.ajax({
	        type:"POST",
	        url:BidPlanCharts.url,//提供数据的Servlet
	        dataType : "JSON",
	        data:{type:v},
	        success:function(responseText, statusText){
	        	
	        	/*//查询日期赋值
	        	$("#yearData").empty();
				for(var i=(-10) ; i<=10 ; i++){
					var newDate = new Date().getFullYear()
					if(v==(newDate+i)){
						$("#yearData").append(
						'<option selected="selected">'+(v)+'</option>'
						);
					}else{
						$("#yearData").append(
						'<option>'+(newDate+i)+'</option>'
						);
					}
				}*/
	        	
	        	//数据源
				var list = JSON.parse(responseText.result);
				BidPlanCharts.chart.xAxis.categories=list.xAxis;
				//赋值
	            BidPlanCharts.chart.series=list.series;
	            
	            $(BidPlanCharts.saleDivId).highcharts(BidPlanCharts.chart);
	        },
	        error:function(e){
	        	$(BidPlanCharts.saleDivId).highcharts(BidPlanCharts.chart);
	        }
	    });
	};
	
	
	
	