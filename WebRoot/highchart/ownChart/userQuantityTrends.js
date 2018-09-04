Ext.namespace("UserTrend");

userQuantityTrends = Ext.extend(Ext.Panel, {
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		this.initUIComponents();
		userQuantityTrends.superclass.constructor.call(this, {
			id:'userQuantityTrends',
			iconCls:"btn-tree-team39",
			title : '用户数量趋势分析',
			layout : 'border',
			autoScroll : true,
			items : [{
				region:'center',
				html:'<div id="userChart"></div>',
				'afterRender': function () {
					UserTrend.search();
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
								UserTrend.search();
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
								UserTrend.search();
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
								UserTrend.search();
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
								UserTrend.search();
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
						handler : UserTrend.search
					}]
				},{
					columnWidth : 0.41,
					items : [{
						xtype : 'button',
						text : '重置',
						scope : this,
						iconCls : 'btn-reset',
						handler : UserTrend.reset,
						anchor : '9%'
					}]
				},{
					columnWidth : 1,
					labelWidth : 40,
					items : [{
						xtype : 'label',
						html:'<link rel="stylesheet" href="highchart/css/btn.css" type="text/css" /><p id="chart3" style="text-align: right;">图表主题：' +
							 '<a class="btn btn-primary" onclick="UserTrend.changeTheme(\'default\',this)">默认</a>' +
							 '<a class="btn" onclick="UserTrend.changeTheme(\'grid\',this)">网格 (grid)</a>'+
							 '<a class="btn" onclick="UserTrend.changeTheme(\'skies\',this)">天空(skies)</a>' +
							 '<a class="btn" onclick="UserTrend.changeTheme(\'sand-signika\',this)">sand-signika</a>' +
							 '</p>'
					}]
				}]
			}]
		});
	},
	initUIComponents:function(){
		UserTrend.theme='default';
		UserTrend.url=__ctxPath + "/highchart/findUserTrendHighchart.do?1=1";
		UserTrend.search=function(){
			var start_searchDate=Ext.getCmp('userQuantityTrends').getCmpByName('start_searchDate').getValue();
			var end_searchDate=Ext.getCmp('userQuantityTrends').getCmpByName('end_searchDate').getValue();
			var dateType = Ext.getCmp('userQuantityTrends').getCmpByName('dateType').getValue();
			var url= UserTrend.url;
			//起日不为null，止日不为null
			if(start_searchDate && end_searchDate){
		    	url=url+"&start_searchDate="+formatDate(start_searchDate,'yyyy-MM-dd')+"&end_searchDate="+formatDate(end_searchDate,'yyyy-MM-dd');
			}
			if(dateType){
				url=url+"&dateType="+dateType;
			}
			UserTrend.createReport(url,UserTrend.theme);
		};
		
		UserTrend.reset=function(){
			this.getCmpByName('start_searchDate').setValue(new Date().add(Date.DAY, -11));
			this.getCmpByName('end_searchDate').setValue(new Date());
			this.getCmpByName('dateType').setValue(1);
			this.getCmpByName('type').setValue(true);
		};
		
		UserTrend.createReport=function(url,theme){
			 var chart ={
		        chart: {
		            type: 'line',
		            height:$("body").height()*0.76,
		            width:$("body").width()*0.862,
		            style:{
			            padding:'28 0'
		            }
		        },
		        title: {
		            text: '用户数量趋势分析'
		        },
		        xAxis: {
		            categories: []//横坐标内容
		        },
		        yAxis: {
		            title: { 
		                text: '用户数量(位)' 
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
		        	name:'注册用户数',
		        	data:[]
		        },{
		        	name:'实名用户数',
		        	data:[]
		        },{
		        	name:'开通资金托管用户数',
		        	data:[]
		        },{
		        	name:'投资用户数',
		        	data:[]
		        }]
			};
		    //异步请求数据
		    UserTrend.toAjax(chart,url,theme);
		};
		
		UserTrend.toAjax=function(chart,url,theme){
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
				        chart.series[1].data.push(list[i].sumB);
				        chart.series[2].data.push(list[i].sumC);
				        chart.series[3].data.push(list[i].sumD);
		            }
		            chart.xAxis.categories = xatrnames
		            if(theme=="sand-signika"){
		            	$.extend(true, chart, Sand.theme);
					}else if(theme=="grid"){
						$.extend(true, chart, Grid.theme);
					}else if(theme=="skies"){
						$.extend(true, chart, Skies.theme);
					}
		            $('#userChart').highcharts(chart);
		        },
		        error:function(e){
		        	$('#userChart').highcharts(chart);
		        }
		    });
		};
		
		UserTrend.changeTheme=function(theme,v){
			var all= $("#chart3 a");
			for(var i=0;i<all.length;i++){
				all[i].className="btn "
			}
			v.className="btn btn-primary";
			var url = UserTrend.url;
			var start_searchDate=Ext.getCmp('userQuantityTrends').getCmpByName('start_searchDate').getValue();
			var end_searchDate=Ext.getCmp('userQuantityTrends').getCmpByName('end_searchDate').getValue();
			var dateType = Ext.getCmp('userQuantityTrends').getCmpByName('dateType').getValue();
			//起日不为null，止日不为null
			if(start_searchDate && end_searchDate){
		    	url=url+"&start_searchDate="+formatDate(start_searchDate,'yyyy-MM-dd')+"&end_searchDate="+formatDate(end_searchDate,'yyyy-MM-dd');
			}
			if(dateType){
				url=url+"&dateType="+dateType;
			}
			UserTrend.createReport(url,theme);
			UserTrend.theme=theme; 
		}
	}
});