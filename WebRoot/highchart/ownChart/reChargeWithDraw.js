Ext.namespace("OffLine");

reChargeWithDraw = Ext.extend(Ext.Panel, {
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		this.initUIComponents();
		reChargeWithDraw.superclass.constructor.call(this, {
			id:'reChargeWithDraw',
			iconCls:"btn-tree-team39",
			title : '充值提现统计图',
			layout : 'border',
			autoScroll : true,
			items : [{
				region:'center',
				html:'<div id="rechargeChart"></div>',
				'afterRender': function () {
					OffLine.search();
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
								OffLine.search();
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
								OffLine.search();
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
								OffLine.search();
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
								OffLine.search();
							}
						}
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
						handler : OffLine.search
					}]
				}, {
					columnWidth : 0.41,
					items : [{
						xtype : 'button',
						text : '重置',
						scope : this,
						iconCls : 'btn-reset',
						handler : OffLine.reset,
						anchor : '9%'
					}]
				},{
					columnWidth : 1,
					labelWidth : 40,
					items : [{
						xtype : 'label',
						html:'<link rel="stylesheet" href="highchart/css/btn.css" type="text/css" /><p id="chart1" style="text-align: right;">图表主题：' +
							 '<a class="btn btn-primary" onclick="OffLine.changeTheme(\'default\',this)">默认</a>' +
							 '<a class="btn" onclick="OffLine.changeTheme(\'grid\',this)">网格 (grid)</a>' +
							 '<a class="btn" onclick="OffLine.changeTheme(\'skies\',this)">天空(skies)</a>' +
							 '<a class="btn" onclick="OffLine.changeTheme(\'sand-signika\',this)">sand-signika</a>' +
							 '</p>'
					}]
				}]
			}]
		});
	},
	initUIComponents:function(){
		OffLine.theme='default';
		OffLine.url=__ctxPath + "/highchart/findOffLineChartHighchart.do?1=1";
		OffLine.search=function(){
			var start_searchDate=Ext.getCmp('reChargeWithDraw').getCmpByName('start_searchDate').getValue();
			var end_searchDate=Ext.getCmp('reChargeWithDraw').getCmpByName('end_searchDate').getValue();
			var dateType = Ext.getCmp('reChargeWithDraw').getCmpByName('dateType').getValue();
			var url= OffLine.url;
			//起日不为null，止日不为null
			if(start_searchDate && end_searchDate){
		    	url=url+"&start_searchDate="+formatDate(start_searchDate,'yyyy-MM-dd')+"&end_searchDate="+formatDate(end_searchDate,'yyyy-MM-dd');
			}
			if(dateType){
				url=url+"&dateType="+dateType;
			}
			OffLine.createReport(url,OffLine.theme);
		};
		
		OffLine.reset=function(){
			this.getCmpByName('start_searchDate').setValue(new Date().add(Date.DAY, -11));
			this.getCmpByName('end_searchDate').setValue(new Date());
			this.getCmpByName('dateType').setValue(1);
			this.getCmpByName('type').setValue(true);
		};
		
		
		OffLine.createReport=function(url,theme){
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
		        	name:'线下充值成功金额',
		        	data:[]
		        },{
		        	name:'线下提现成功金额',
		        	data:[]
		        },{
		        	name:'线上充值成功金额',
		        	data:[]
		        },{
		        	name:'线上提现成功金额',
		        	data:[]
		        }]
			};
		    //异步请求数据
		    OffLine.toAjax(chart,url,theme);
		};
		
		OffLine.toAjax=function(chart,url,theme){
		 	$.ajax({
		        type:"POST",
		        url:url,//提供数据的Servlet
		        dataType : "JSON",
		        success:function(responseText, statusText){
					var list = JSON.parse(responseText.result);
					var xatrnames = [];
		            for (var i =0;i<list.length;i++) {
		                xatrnames.push(list[i].searchDate);
				        chart.series[0].data.push(list[i].moneyA);
				        chart.series[1].data.push(list[i].moneyB);
				        chart.series[2].data.push(list[i].moneyC);
				        chart.series[3].data.push(list[i].moneyD);
		            }
		            chart.xAxis.categories = xatrnames
		            if(theme=="sand-signika"){
		            	$.extend(true, chart, Sand.theme);
					}else if(theme=="grid"){
						$.extend(true, chart, Grid.theme);
					}else if(theme=="skies"){
						$.extend(true, chart, Skies.theme);
					}
		            $('#rechargeChart').highcharts(chart);
		        },
		        error:function(e){
		        	$('#rechargeChart').highcharts(chart);
		        }
		    });
		};
		
		OffLine.changeTheme=function(theme,v){
			var all= $("#chart1 a");
			for(var i=0;i<all.length;i++){
				all[i].className="btn "
			}
			v.className="btn btn-primary";
			var url = OffLine.url;
			var start_searchDate=Ext.getCmp('reChargeWithDraw').getCmpByName('start_searchDate').getValue();
			var end_searchDate=Ext.getCmp('reChargeWithDraw').getCmpByName('end_searchDate').getValue();
			var dateType = Ext.getCmp('reChargeWithDraw').getCmpByName('dateType').getValue();
			//起日不为null，止日不为null
			if(start_searchDate && end_searchDate){
		    	url=url+"&start_searchDate="+formatDate(start_searchDate,'yyyy-MM-dd')+"&end_searchDate="+formatDate(end_searchDate,'yyyy-MM-dd');
			}
			if(dateType){
				url=url+"&dateType="+dateType;
			}
			OffLine.createReport(url,theme);
			OffLine.theme=theme; 
		}
	}
});