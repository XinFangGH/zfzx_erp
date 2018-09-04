//BidTypeProportion
	/**
	 * 散标类型占比图
	 * @param {} v
	 */
	 
	 Ext.namespace("BidTypeProportion");
	BidTypeProportion.ajax3=function(id,v){
			/*if(typeof(v)=="undefined"){
				v=2;
			}*/
			 BidTypeProportion.saleDivId=id,
			 BidTypeProportion.url = __ctxPath + "/highchart/bidTypeProportionHighchart.do",
			 BidTypeProportion.chart = {
			 	chart: {
		            type: 'pie'
		        },
		        title: {
		            text: ''
		        },
		         tooltip: {
		    	    pointFormat: '{series.name}: <b>{point.percentage:.2f}%</b>'
		        },
		        colors: [ "#6ca30f",'#f59311','#fa4343','#7cb5ec','#f286a0', '#b23438', '#b9884d','#fba115','#2f7ed8', '#0d233a'],
		         plotOptions: {
		            pie: {
		                allowPointSelect: true,
		                cursor: 'pointer',
		                innerSize:110,
				        size:'100%',
		                dataLabels: {
		                    enabled: true,
		                    color: '#000000',
		                    connectorColor: '#000000',
		                    format: '<b>{point.name}</b>: {point.percentage:.2f} %'
		                },
		                 showInLegend: true
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
		        url:BidTypeProportion.url,//提供数据的Servlet
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
					
					//赋值
		            BidTypeProportion.chart.series=list.series;
		            
		            $(BidTypeProportion.saleDivId).highcharts(BidTypeProportion.chart);
		        },
		        error:function(e){
		        	$(BidTypeProportion.saleDivId).highcharts(BidTypeProportion.chart);
		        }
		    });
		};
	