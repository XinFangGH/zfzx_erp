Ext.namespace("planAssignment");
$(function () {
	 planAssignment.saleDivId="#planAssignment",
	 planAssignment.url = __ctxPath + "/highchart/getPlanManageStateHighchart.do",
	 planAssignment.chart = {
		        chart: {
		            zoomType: 'xy'
		        },
		        colors:['#f88139','#f88139'],
		        xAxis: {
		            categories: ['待预售','预售中','招标中','已满标','已到期','还款中','已完成','已关闭']//横坐标内容
		        }, title: {
            		text: ''
        		},
		        yAxis: [{
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
			        }, { 
			            labels: {
			                format: '{value}笔',
			                style: {
			                    color: '#4572A7'
			                }
			            },
			            title: {
			                text: '数量',
			                style: {
			                    color: '#4572A7'
			                }
			            },
			            opposite: true
			        }
		        ],
		        tooltip: {//当提示框被共享时，整个绘图区都将捕捉鼠标指针的移动,在提示框中按顺序提示柱状图点值与折线图点值
		            shared: true
		        },
		        legend: {//设置图表中数列标志和名称的容器
		            //layout: 'vertical',
		            align: 'center',
		            verticalAlign: 'top',
		            floating: true,
		            backgroundColor: '#FFFFFF'
		        },
		        
		        credits:{
		        	text:''//去掉右下角的Highcharts.com超链接
		        },
		        //数据填充
		        series: [{
		            name: '数量',
		            color: '#f88139',
		            type: 'column',
		            yAxis: 1,
		            data: [],
		            tooltip: {	
		                valueSuffix: '笔'
		            }
		
		        }, {
		            name: '金额/万元',
		            color: '#93de86',
		            type: 'spline',
		            data: [],
		            tooltip: {
		                valueSuffix: '万'
		            }
		        }]
			};
	planAssignment.ajax=function(dataState){
		planAssignment.chart.series[0].data.clear();
		planAssignment.chart.series[1].data.clear();
		//异步请求数据
	    $.ajax({
	        type:"POST",
	        url:planAssignment.url,//提供数据的Servlet
	        dataType : "JSON",
	        data:{dataState:dataState},
	        success:function(response,request){	
	        		var dataJson = response.result[0];
				    planAssignment.chart.series[0].data.push(dataJson.sumA,dataJson.sumB,dataJson.sumC,dataJson.sumD,dataJson.sumE,dataJson.sumF,dataJson.sumG,dataJson.sumH);
				    planAssignment.chart.series[1].data.push(dataJson.moneyA,dataJson.moneyB,dataJson.moneyC,dataJson.moneyD,dataJson.moneyE,dataJson.moneyF,dataJson.moneyG,dataJson.moneyH);
	            $(planAssignment.saleDivId).highcharts(planAssignment.chart);
	        },
	        error:function(e){
	        	$(planAssignment.saleDivId).highcharts(planAssignment.chart);
	        }
	    });
	};
});