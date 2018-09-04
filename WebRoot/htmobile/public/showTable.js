Ext.define('htmobile.public.showTable', {
    extend: 'Ext.Panel',
    name: 'showTable',
    constructor: function (config) {
    	var bottomBar= Ext.create('htmobile.public.bottomBarIndex',{
        });
    	var categoriesValue=[];
    	var dataValue=[];
    	config = config || {};
    	this.result1=config.result1;
    	var data = config.data;
    	Ext.Ajax.request({
						    url: __ctxPath+ "/highchart/myRankHighchart.do",
					        params: {
					        },
					        method: 'POST',
					    	success : function(response) {
					        	var obj = Ext.util.JSON.decode(response.responseText);
					        	if(obj.success==true){
					        		data = obj.result;
					        		 if(data!=null && data.length>=1){
					        		 	if(data[0]!=null){
					        		 		$('#name1').text(data[0].searchDate);
					        		 		$('#money1').text(formatCurrency(data[0].moneyA/10000)+"万元");
					        		 	}
					        		 	if(data[1]!=null){
					        		 		$('#name2').text(data[1].searchDate);
					        		 		$('#money2').text(formatCurrency(data[1].moneyA/10000)+"万元");
					        		 	}
					        		 	if(data[2]!=null){
					        		 		$('#name3').text(data[2].searchDate);
					        		 		$('#money3').text(formatCurrency(data[2].moneyA/10000)+"万元");
					        		 	}
								    }
					        	}else{
					        		isShow_share=0;
					        	}	
					    	}
			  		});
    	Ext.apply(config,{
		    fullscreen: true,
		    tplWriteMode:'overwrite',
		    border:2,
		    bubbleEvents:'shangxl',
		    title:'我的业绩',
		    scrollable:{
		    	direction: 'vertical'
		    },
		    tpl:'<div>hello world!</div>',
		    items:[bottomBar],
		    html:'<div class="g-body login">'+
				    '<table border="0" cellspacing="0" cellpadding="0" class="w-table w-table-rank">'+
				        '<thead>'+
				        '<tr>'+
				            '<td class="txt-l txt-dark">排名</td>'+
				            '<td class="txt-c txt-dark">姓名</td>'+
				            '<td class="txt-r txt-dark">业绩</td>'+
				        '</tr>'+
				        '</thead>'+
				        '<tbody>'+
				        '<tr>'+
				            '<td class="txt-l">'+
				                '<i class="winpic"><img src='+__ctxPath+'/htmobile/resources/images/yjph_01.png></i>'+
				                '<span class="posr txtrank">第一名</span>'+
				            '</td>'+
				            '<td class="txt-c" id="name1"></td>'+
				            '<td class="txt-r" id="money1"></td>'+
				        '</tr>'+
				        '<tr>'+
				            '<td class="txt-l">'+
				                '<i class="winpic"><img src='+__ctxPath+'/htmobile/resources/images/yjph_02.png></i>'+
				                '<span class="posr txtrank">第二名</span>'+
				            '</td>'+
				            '<td class="txt-c" id="name2">暂无</td>'+
				            '<td class="txt-r" id="money2">0万</td>'+
				        '</tr>'+
				        '<tr>'+
				            '<td class="txt-l">'+
				                '<i class="winpic"><img src='+__ctxPath+'/htmobile/resources/images/yjph_03.png></i>'+
				                '<span class="posr txtrank">第三名</span>'+
				            '</td>'+
				            '<td class="txt-c" id="name3">暂无</td>'+
				            '<td class="txt-r" id="money3">0万</td>'+
				        '</tr>'+
				        '</tbody>'+
				    '</table>'+
				    '<section class="perfor-chart">'+
				        '<header class="chart-title clearfix">'+
				            '<p class="fr search-time">'+
				                '<label style="font-weight: bold;">查询时限：</label>'+
				                '<select id="test" onchange="javascript:selelab()">'+
				                    '<option>按月</option>'+
				                    '<option>按年</option>'+
				                    '<option>按季</option>'+
				                '</select>'+
				            '</p>'+
				        '</header>'+
				        '<div class="charts"></div>'+
				    '</section>'+
				'</div>'
    	});
    	
    	 this.callParent([config]);
    	 var flag="byMoney"//默认按月统计
    	 selelab=function(){
    	 	options=$("#test option:selected");
    	 	if(options.val().indexOf("按年")!=-1){
    	 		flag="byYear"
    	 	}else if(options.val().indexOf("按季")!=-1){
				flag="bySeason"
    	 	}else if(options.val().indexOf("按月")!=-1){
				 flag="byMoney"
    	 	}
		 	 Ext.Ajax.request({
							    async:false,
								url : __ctxPath+'/highchart/businessRankByMonthHighchart.do',
						        params: {
						        	flag:flag
						        },
						        method: 'POST',
						    	success : function(response) {
						        	var obj = Ext.util.JSON.decode(response.responseText);
						        	var result=obj.result;
						        	//初始化数据
						        	if(dataValue.length!=0){
						        		dataValue=[];
						        	}
						        	if(categoriesValue.length!=0){
						        		categoriesValue=[];
						        	}
						        	for(var i=0;i<result.length;i++){
						        		dataValue.push(result[i].moneyA);
						        		categoriesValue.push(result[i].searchDate);
						        	}
						        }
					  		});
			 $('.charts').highcharts({
									chart : {
										type : 'line',
										width:(document.body.clientWidth)*0.95,
										height:(document.body.clientHeight)*0.5,
										zoomType:'xy'
									},
									legend: {
										align:'center',
										verticalAlign:'top'
									},
									credits: {
									     enabled: false
									},
									title : {
										text : ''
									},
									subtitle : {
										text : ''
									},
									xAxis : {
										categories : categoriesValue
									},
									yAxis : {
										title : {
											text : '放款金额(元)'
										}
									},
									plotOptions : {
										line : {
											dataLabels : {
												enabled : true
											},
											enableMouseTracking : false
										}
									},
									series : [{
										name : '放款金额',
										color:'#12b7f5',
										data : dataValue
									}]
								});
    	 }
    	 //初始值为按月
    	 selelab();
    	 
    }
});
