Ext.namespace("OverdueLoan");

overdueLoanStatistics = Ext.extend(Ext.Panel, {
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		this.initUIComponents();
		overdueLoanStatistics.superclass.constructor.call(this, {
			id:'overdueLoanStatistics',
			iconCls:"btn-tree-team39",
			title : '逾期借款统计图',
			layout : 'border',
			autoScroll : true,
			items : [{
				region:'center',
				html:'<div id="overdueChart"></div>',
				'afterRender': function () {
					OverdueLoan.search();
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
					columnWidth : 0.2,
					labelWidth : 70,
					items : [{
						xtype : 'datefield',
						fieldLabel : '查询日期起',
						name : 'start_searchDate',
						format:'Y-m-d',
						anchor : '90%',
						maxValue:new Date(),
						value : new Date().add(Date.DAY, -11),
						listeners : {
							scope : this,
							'select' : function(nf){
								HighchartUtil.setDate(this.getCmpByName('dateType').getValue(),nf,this.getCmpByName("end_searchDate"));
							}
						}
					}]
				},{
					columnWidth : 0.12,
					labelWidth : 20,
					items : [{
						xtype : 'datefield',
						fieldLabel : '至',
						name : 'end_searchDate',
						format:'Y-m-d',
						anchor : '100%',
						readOnly : true,
						value : new Date(),
						maxValue:new Date()
					}]
				},{
					xtype : 'hidden',
					name : 'dateType',
					value : 1
				},{
					columnWidth : 0.05,
					labelWidth : 5,
					items : [{
						xtype : 'radio',
						boxLabel : '按日',
						name : 'type',
						checked : true,
						listeners : {
							scope : this,
							'focus' : function(nf){
								HighchartUtil.setRadio(1,this.getCmpByName('dateType'),this.getCmpByName('start_searchDate'),this.getCmpByName('end_searchDate'));
								OverdueLoan.search();
							}
						}
					}]
				},{
					columnWidth : 0.05,
					labelWidth : 5,
					items : [{
						xtype : 'radio',
						boxLabel : '按月',
						name : 'type',
						listeners : {
							scope : this,
							'focus' : function(nf){
								HighchartUtil.setRadio(2,this.getCmpByName('dateType'),this.getCmpByName('start_searchDate'),this.getCmpByName('end_searchDate'));
								OverdueLoan.search();
							}
						}
					}]
				},{
					columnWidth : 0.05,
					labelWidth : 5,
					items : [{
						xtype : 'radio',
						boxLabel : '按季',
						name : 'type',
						listeners : {
							scope : this,
							'focus' : function(nf){
								HighchartUtil.setRadio(3,this.getCmpByName('dateType'),this.getCmpByName('start_searchDate'),this.getCmpByName('end_searchDate'));
								OverdueLoan.search();
							}
						}
					}]
				},{
					columnWidth : 0.05,
					labelWidth : 5,
					items : [{
						xtype : 'radio',
						boxLabel : '按年',
						name : 'type',
						listeners : {
							scope : this,
							'focus' : function(nf){
								HighchartUtil.setRadio(4,this.getCmpByName('dateType'),this.getCmpByName('start_searchDate'),this.getCmpByName('end_searchDate'));
								OverdueLoan.search();
							}
						}
					}]
				},{
					columnWidth : 0.07,
					items : [{
						xtype : 'button',
						text : '查询',
						tooltip : '根据查询条件过滤',
						iconCls : 'btn-search',
						width : 60,
						formBind : true,
						scope : this,
						handler : OverdueLoan.search
					}]
				},{
					columnWidth : 0.41,
					items : [{
						xtype : 'button',
						text : '重置',
						scope : this,
						iconCls : 'btn-reset',
						handler : OverdueLoan.reset,
						anchor : '9%'
					}]
				},{
					columnWidth : 1,
					labelWidth : 40,
					items : [{
						xtype : 'label',
						html:'<link rel="stylesheet" href="highchart/css/btn.css" type="text/css" /><p id="chart4" style="text-align: right;">图表主题：' +
							 '<a class="btn btn-primary" onclick="OverdueLoan.changeTheme(\'default\',this)">默认</a>' +
							 '<a class="btn" onclick="OverdueLoan.changeTheme(\'grid\',this)">网格 (grid)</a>'+
							 '<a class="btn" onclick="OverdueLoan.changeTheme(\'skies\',this)">天空(skies)</a>' +
							 '<a class="btn" onclick="OverdueLoan.changeTheme(\'sand-signika\',this)">sand-signika</a>' +
							 '</p>'
					}]
				}]
			}]
		});
	},
	initUIComponents:function(){
		OverdueLoan.theme='default';
		OverdueLoan.url=__ctxPath + "/highchart/findOverdueHighchart.do?1=1";
		OverdueLoan.search=function(){
			var start_searchDate=Ext.getCmp('overdueLoanStatistics').getCmpByName('start_searchDate').getValue();
			var end_searchDate=Ext.getCmp('overdueLoanStatistics').getCmpByName('end_searchDate').getValue();
			var dateType = Ext.getCmp('overdueLoanStatistics').getCmpByName('dateType').getValue();
			var url= OverdueLoan.url;
			//起日不为null，止日不为null
			if(start_searchDate && end_searchDate){
		    	url=url+"&start_searchDate="+formatDate(start_searchDate,'yyyy-MM-dd')+"&end_searchDate="+formatDate(end_searchDate,'yyyy-MM-dd');
			}
			if(dateType){
				url=url+"&dateType="+dateType;
			}
			OverdueLoan.createReport(url,OverdueLoan.theme);
		};
		
		OverdueLoan.reset=function(){
			this.getCmpByName('start_searchDate').setValue(new Date().add(Date.DAY, -11));
			this.getCmpByName('end_searchDate').setValue(new Date());
			this.getCmpByName('dateType').setValue(1);
			this.getCmpByName('type').setValue(true);
		};
		
		OverdueLoan.createReport=function(url,theme){
			 var chart ={
		        chart: {
		            zoomType: 'xy',
		            height:$("body").height()*0.76,
		            width:$("body").width()*0.862,
		            style:{
			            padding:'28 0'
		            }
		        },
		        title: {
		            text: '逾期借款统计'
		        },
		        xAxis: {
		            categories: []//横坐标内容
		        },
		        yAxis: [{
			            labels: {
			                format: '{value}元',
			                style: {
			                    color: '#89A54E'
			                }
			            },
			            title: {
			                text: '涉及本息',
			                style: {
			                    color: '#89A54E'
			                }
			            }
			        }, { 
			            labels: {
			                format: '{value}个',
			                style: {
			                    color: '#4572A7'
			                }
			            },
			            title: {
			                text: '逾期数量',
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
		            layout: 'vertical',
		            align: 'left',
		            x: 120,
		            verticalAlign: 'top',
		            y: 100,
		            floating: true,
		            backgroundColor: '#FFFFFF'
		        },
		        
		        credits:{
		        	text:''//去掉右下角的Highcharts.com超链接
		        },
		        //数据填充
		        series: [{
		            name: '逾期数量',
		            color: '#4572A7',
		            type: 'column',
		            yAxis: 1,
		            data: [],
		            tooltip: {
		                valueSuffix: '个'
		            }
		
		        }, {
		            name: '涉及本息',
		            color: '#89A54E',
		            type: 'spline',
		            data: [],
		            tooltip: {
		                valueSuffix: '元'
		            }
		        }]
			};
		    //异步请求数据
		    OverdueLoan.toAjax(chart,url,theme);
		};
		
		OverdueLoan.toAjax=function(chart,url,theme){
		 	$.ajax({
		        type:"POST",
		        url:url,//提供数据的Servlet
		        dataType : "JSON",
		        success:function(responseText, statusText){
					var list = JSON.parse(responseText.result);
					var xatrnames = [];
		            for (var i =0;i<list.length;i++) {
		                xatrnames.push(list[i].searchDate);
				        chart.series[0].data.push(list[i].sumA);
				        chart.series[1].data.push(list[i].moneyA);
		            }
		            chart.xAxis.categories = xatrnames
		            if(theme=="sand-signika"){
		            	$.extend(true, chart, Sand.theme2);
					}else if(theme=="grid"){
						$.extend(true, chart, Grid.theme2);
					}else if(theme=="skies"){
						$.extend(true, chart, Skies.theme2);
					}
		            $('#overdueChart').highcharts(chart);
		        },
		        error:function(e){
		        	$('#overdueChart').highcharts(chart);
		        }
		    });
		};
		
		OverdueLoan.changeTheme=function(theme,v){
			var all= $("#chart4 a");
			for(var i=0;i<all.length;i++){
				all[i].className="btn "
			}
			v.className="btn btn-primary";
			var url = OverdueLoan.url;
			var start_searchDate=Ext.getCmp('overdueLoanStatistics').getCmpByName('start_searchDate').getValue();
			var end_searchDate=Ext.getCmp('overdueLoanStatistics').getCmpByName('end_searchDate').getValue();
			var dateType = Ext.getCmp('overdueLoanStatistics').getCmpByName('dateType').getValue();
			//起日不为null，止日不为null
			if(start_searchDate && end_searchDate){
		    	url=url+"&start_searchDate="+formatDate(start_searchDate,'yyyy-MM-dd')+"&end_searchDate="+formatDate(end_searchDate,'yyyy-MM-dd');
			}
			if(dateType){
				url=url+"&dateType="+dateType;
			}
			OverdueLoan.createReport(url,theme);
			OverdueLoan.theme=theme; 
		}
	}
});