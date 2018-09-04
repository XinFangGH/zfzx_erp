Ext.namespace("OnLine");

OnLineReChargeWithDraw = Ext.extend(Ext.Panel, {
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		this.initUIComponents();
		OnLineReChargeWithDraw.superclass.constructor.call(this, {
			id:'OnLineReChargeWithDraw',
			title : '线上充值提现统计图',
			layout : 'border',
			autoScroll : true,
			items : [{
				region:'center',
				html:'<div id="OnLineRechargeChart"></div>',
				'afterRender': function () {
                     OnLine.createReport();
                }
			},{
				height : 60,
				bodyStyle : 'padding:7px 0px 7px 10px',
				border : true,
				width : '100%',
				monitorValid : true,
				layout : 'column',
				defaults : {
					layout : 'form',
					border : false
				},
				items : [{
					columnWidth : 0.18,
					labelWidth : 40,
					items : [{
						xtype : 'datefield',
						fieldLabel : '日期',
						name : 'searchDate',
						format:'Y-m-d',
						anchor : '90%'
					}]
				},{
					columnWidth : 0.07,
					items : [{
						id : 'searchButton',
						xtype : 'button',
						text : '查询',
						tooltip : '根据查询条件过滤',
						iconCls : 'btn-search',
						width : 60,
						formBind : true,
						scope : this,
						handler : OnLine.search
					}]
				}, {
					columnWidth : 0.75,
					items : [{
						xtype : 'button',
						text : '重置',
						scope : this,
						iconCls : 'btn-reset',
						handler : OnLine.reset,
						anchor : '7%'
					}]
				},{
					columnWidth : 1,
					labelWidth : 40,
					items : [{
						xtype : 'label',
						html:'<link rel="stylesheet" href="highchart/css/btn.css" type="text/css" /><p id="chart2" style="text-align: right;">图表主题：' +
							 '<a class="btn btn-primary" onclick="OnLine.changeTheme(\'default\',this)">默认</a>' +
							 '<a class="btn" onclick="OnLine.changeTheme(\'grid\',this)">网格 (grid)</a>'+
							 '<a class="btn" onclick="OnLine.changeTheme(\'sand-signika\',this)">sand-signika</a>' +
							 '</p>'
					}]
				}]
			}]
		});
	},
	initUIComponents:function(){
		OnLine.theme='default';
		OnLine.url=__ctxPath + "/creditFlow/creditAssignment/bank/findOffLineChartObAccountDealInfo.do?investPersionType=0";
		OnLine.search=function(){
			var pDate=this.getCmpByName('searchDate').getValue();
			var url= OnLine.url;
			if(pDate){
		    	url=url+"&searchDate="+formatDate(pDate,'yyyy-MM-dd');
			}
			OnLine.createReport(url,OnLine.theme);
		};
		
		OnLine.reset=function(){
			this.getCmpByName('searchDate').setValue();
		};
		
		OnLine.createReport=function(url,theme){
			 var chart ={
		        chart: {
		            type: 'column',
		            height:$("body").height()*0.76,
		            width:$("body").width()*0.862,
		            style:{
			            padding:'28 0'
		            }
		        },
		        title: {
		            text: '充值提现统计'
		        },
		        xAxis: {
		            categories: []//横坐标内容
		        },
		        yAxis: {
		            title: { 
		                text: '金额(人民币:元)' 
		            }
		        },
		        credits:{
		        	text:''//去掉右下角的Highcharts.com超链接
		        },
		        plotOptions: {//设置柱的样式
		            column: {
		                pointPadding: 0.2,
		                borderWidth: 0
		            }
		        },
		        series:[{
		        	name:'充值成功金额',
		        	data:[]
		        },{
		        	name:'提现成功金额',
		        	data:[]
		        }]
			};
		    //异步请求数据
		    OnLine.toAjax(chart,OnLine.url,theme);
		};
		
		OnLine.toAjax=function(chart,url,theme){
		 	$.ajax({
		        type:"POST",
		        url:url,//提供数据的Servlet
		        dataType : "JSON",
		        success:function(responseText, statusText){
					var list = JSON.parse(responseText.result);
					var xatrnames = [];
		            for (var i =0;i<list.length;i++) {
		                xatrnames.push(list[i].transferDate);
				        chart.series[0].data.push(list[i].incomMoney);
				        chart.series[1].data.push(list[i].payMoney);
		            }
		            chart.xAxis.categories = xatrnames
		            if(theme=="sand-signika"){
		            	$.extend(true, chart, Sand.theme);
					}else if(theme=="grid"){
						$.extend(true, chart, Grid.theme);
					}
		            $('#OnLineRechargeChart').highcharts(chart);
		        },
		        error:function(e){
		        	$('#OnLineRechargeChart').highcharts(chart);
		        }
		    });
		};
		
		OnLine.changeTheme=function(theme,v){
			var all= $("#chart2 a");
			for(var i=0;i<all.length;i++){
				all[i].className="btn "
			}
			v.className="btn btn-primary";
			var url = OnLine.url;
			var searchDate=Ext.getCmp('OnLineReChargeWithDraw').getCmpByName('searchDate').getValue();
			if(searchDate){
		    	url = url+"&searchDate="+formatDate(searchDate,'yyyy-MM-dd');
			}
			OnLine.createReport(url,theme);
			OnLine.theme=theme; 
		}
	}
});