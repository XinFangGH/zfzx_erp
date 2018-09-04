var ReportTemplatePreviewCapital = function(reportId, title) {

	resetButton = new Ext.Button({
				text : '重置',
				iconCls : 'btn-reset',
				width : 20,
				handler : this.reset
			});

	searchButton = new Ext.Button({
				text : '查询',
				iconCls : 'search',
				width : 20
			});
	xlsButton = new Ext.Button({

				text : 'excel',
				iconCls : 'btn-xls'
			});
	htmlButton = new Ext.Button({

				text : 'html',
				iconCls : 'btn-ie'
			});
	
	pdfButton = new Ext.Button({

		text : 'pdf',
		iconCls : 'btn-pdf'
	});
	
	var state='';
    searchPanel=new Ext.FormPanel({
    	layout : 'column',
    	region : 'north',
    	height:40,
    	anchor : '96%', 
    	layoutConfig: {
            align:'middle'
        },
        border:false,
        bodyStyle : 'padding:10px 10px 10px 10px',
    	items:[{
    		columnWidth:0.15,
    		layout:'form',
    		border:false,
    		labelWidth:60,
    		labelAlign:'right',
    		items:[{
    			xtype:'combo',
				mode : 'local',
				anchor : '96%',
			    displayField : 'name',
			    valueField : 'id',
			    width : 70,
			    value:'0',
			    id:'categoryType',
			    store : new Ext.data.SimpleStore({
						fields : ["name", "id"],
						data : [["日", "0"],
								["月", "1"]]
				}),
				triggerAction : "all",
    			fieldLabel:'预测类别',
    			listeners:{
    			    'select':function(combox,record,index){
    			          var r=combox.getValue();
    			          
    			          if(r=='0'){
    			        	  var items = searchPanel.items;
								var item1 = items.get(1);
								var item2 = items.get(2);
								var item3 = items.get(3);
								var item4 = items.get(4);
								
								item1.show()
								item2.show()
								item3.hide()
								item4.hide()
								
    			          }else{
    			        	  var items = searchPanel.items;
								var item1 = items.get(1);
								var item2 = items.get(2);
								var item3 = items.get(3);
								var item4 = items.get(4);
								
								item3.show()
								item4.show()
								item1.hide()
								item2.hide()
    			          }
    		         }
    		    }
    			
    		}]
    	},{
    		columnWidth:0.2,
    		layout:'form',
    		border:false,
    		labelWidth:60,
    		labelAlign:'right',
    		items:[{
    			xtype:'datefield',
    			format:'Y-m-d',
    			anchor : '96%',
    			id:'dayStartTime',
    			fieldLabel:'开始时间'
    			
    		}]
    	},{
    		columnWidth:0.2,
    		layout:'form',
    		border:false,
    		labelWidth:60,
    		labelAlign:'right',
    		items:[{
    			xtype:'datefield',
    			format:'Y-m-d',
    			anchor : '96%',
    			id:'dayEndTime',
    			fieldLabel:'截止时间'
    			
    		}]
    	},{
    		columnWidth:0.2,
    		layout:'form',
    		border:false,
    		labelWidth:60,
    		labelAlign:'right',
    		hidden:true,
    		items:[{
    			xtype:'datefield',
    			format:'Y-m',
    			anchor : '96%',
    			id:'monthStartTime',
    			fieldLabel:'开始时间'
    			
    		}]
    	},{
    		columnWidth:0.2,
    		layout:'form',
    		border:false,
    		labelWidth:60,
    		labelAlign:'right',
    		hidden:true,
    		items:[{
    			xtype:'datefield',
    			format:'Y-m',
    			anchor : '96%',
    			id:'monthEndTime',
    			fieldLabel:'截止时间'
    			
    		}]
    	},{
    		columnWidth:0.19,
    		layout:'form',
    		border:false,
    		labelWidth:140,
    		labelAlign:'right',
    		items:[{
    			xtype:'combo',
				mode : 'local',
			    displayField : 'name',
			    valueField : 'name',
			    anchor : '96%',
			    id:'recoveryRate',
			    value:'100%',
			    store : new Ext.data.SimpleStore({
						fields : ["name", "id"],
						data : [["100%", "0"],
								["75%", "1"],
								["50%", "2"],
								["25%", "3"],
								["0%","4"],
								["其他","5"]]
				}),
				triggerAction : "all",
    			fieldLabel:'资金本金+利息  回收率',
    			listeners:{
    			    'select':function(combox,record,index){
    			         var x=combox.getValue();
    			         if(x=='其他'){
    			        	 var items = searchPanel.items;
								var item1 = items.get(6);
								var item2 = items.get(7);
								item1.show()
								item2.show()
    			         }else{
    			        	    var items = searchPanel.items;
								var item1 = items.get(6);
								var item2 = items.get(7);
								item1.hide()
								item2.hide()
    			         }
    	        	}
    	    	}
    			
    		}]
    	},{
    		columnWidth:0.05,
    		layout:'form',
    		border:false,
    		width:10,
    		hidden:true,
    		items:[{
    			xtype:'numberfield',
    			fieldClass:'field-align',
    			style: {imeMode:'disabled'},
    			hideLabel:true,
    			id:'otherRates',
    			labelWidth:0,
    			width:50	
    		}]
    	},{
			columnWidth : .02, // 该列在整行中所占的百分比
			layout : "form", // 从上往下的布局
			labelWidth : 14,
			border:false,
			hidden:true,
			items : [{
				fieldLabel : "%",
				labelSeparator:'', 
				anchor : "100%"
			}]
		}
    	,{
    		columnWidth:0.15,
    		layout:'form',
    		border:false,
    		items:[{
    			xtype:'button',
    			iconCls : 'btn-add',
    			text:'生成分析图',
    			scope:this,
    			handler:function(){
    			state='0'
    			if(Ext.getCmp("categoryType").getValue()=='0'){
    				var curDate=new Date();
    				var date=curDate.format("Y-m-d");	
    				var dayStartTime=Ext.getCmp("dayStartTime").getValue().format("Y-m-d");
    				var dayEndTime=Ext.getCmp("dayEndTime").getValue().format("Y-m-d")
    				 if(dayStartTime<date || dayEndTime<date){
    					 Ext.ux.Toast.msg('操作信息', '所选日期不能为历史时期!');
    					 return;
    				 }else{
    					
    					 if((Ext.getCmp("dayEndTime").getValue()-Ext.getCmp("dayStartTime").getValue())/24/60/60/1000>90){
    						 Ext.ux.Toast.msg('操作信息', '开始日期和截止日期相隔天数不能超过90天!');
    						 return;
                         }   					
    				 }
    			}else{
    				var curDate=new Date();
    				var date=curDate.format("Y-m");	
    				var monthStartTime=Ext.getCmp("monthStartTime").getValue().format("Y-m");
    				var monthEndTime=Ext.getCmp("monthEndTime").getValue().format("Y-m")
   
    				 if(monthStartTime<date || monthEndTime<date){
    					 Ext.ux.Toast.msg('操作信息', '所选日期不能为历史时期!');
    					 return;
    				 }else{
    					
    					 if((Ext.getCmp("monthEndTime").getValue().getFullYear()-Ext.getCmp("monthStartTime").getValue().getFullYear())*12+Ext.getCmp("monthEndTime").getValue().getMonth()-Ext.getCmp("monthStartTime").getValue().getMonth()>12){
    						 Ext.ux.Toast.msg('操作信息', '开始日期和截止日期相隔月数不能超过12个月!');
    						 return;
                         }   					
    				 }
    			}
    			formPanel.getForm().submit({
					waitMsg : '正在提交查询',
					url : '',// __ctxPath + '/creditFlow/finance/searchSlCapitalFlowtemp.do',
					method : 'post',
					 params : {
						categoryType : Ext.getCmp("categoryType").getValue(),
						dayStartTime:Ext.getCmp("dayStartTime").getValue(),
						dayEndTime:Ext.getCmp("dayEndTime").getValue(),
						monthStartTime:Ext.getCmp("monthStartTime").getValue(),
						monthEndTime:Ext.getCmp("monthEndTime").getValue(),
						recoveryRate:Ext.getCmp("recoveryRate").getValue(),
						otherRates:Ext.getCmp("otherRates").getValue()
					},
					success : function(form, action) {

						var object = Ext.util.JSON
								.decode(action.response.responseText)
						var temp = Ext.getCmp('reportTemp' + reportId);
						temp.body
								.update('<iframe src="'
										+ __ctxPath
										+ '/report/report.jsp?reportId='
										+ reportId
										+ encodeURI(encodeURI(object.data))
										+ '" height="98%" width="98%" scrolling="auto"></iframe>');

						

					},
					failure : function(form, action) {
					

					}
				});
    		
    		    }
    		}]
    	}]
    })
    
    searchPanel1=new Ext.FormPanel({
    	layout : 'column',
    	region : 'north',
    	height:25,
    	anchor : '96%', 
    	layoutConfig: {
            align:'middle'
        },
        border:false,
    	items:[{
    		columnWidth:0.13,
    		layout:'form',
    		border:false,
    		labelWidth:60,
    		labelAlign:'right',
    		items:[{
    			xtype:'numberfield',
    			fieldClass:'field-align',
    			style: {imeMode:'disabled'},
    			id:'money',
                fieldLabel:'需求资金',
                anchor : '96%'
    		}]
    	},{
			columnWidth : .02, // 该列在整行中所占的百分比
			layout : "form", // 从上往下的布局
			labelWidth : 14,
			border:false,
			items : [{
				fieldLabel : "元",
				labelSeparator:'', 
				anchor : "100%"
			}]
		},{
    		columnWidth:0.2,
    		layout:'form',
    		border:false,
    		labelWidth:80,
    		labelAlign:'right',
    		items:[{
    			xtype:'datefield',
    			id:'demandDate',
    			anchor : '96%',
    			format:'Y-m-d',
    			fieldLabel:'需求资金时间'
    			
    		}]
    	},{
    		columnWidth:0.19,
    		layout:'form',
    		border:false,
    		labelWidth:140,
    		labelAlign:'right',
    		items:[{
    			xtype:'combo',
				mode : 'local',
			    displayField : 'name',
			    valueField : 'name',
			    width : 70,
			    id:'recoveryRate1',
			    value:'100%',
			    store : new Ext.data.SimpleStore({
						fields : ["name", "id"],
						data : [["100%", "0"],
								["75%", "1"],
								["50%", "2"],
								["25%", "3"],
								["0%","4"],
								["其他","5"]]
				}),
				triggerAction : "all",
    			fieldLabel:'资金本金+利息  回收率',
    			listeners:{
    			    'select':function(combox,record,index){
    			         var x=combox.getValue();
    			         if(x=='其他'){
    			        	 var items = searchPanel1.items;
								var item1 = items.get(4);
								var item2 = items.get(5);
								item1.show()
								item2.show()
    			         }else{
    			        	    var items = searchPanel1.items;
								var item1 = items.get(4);
								var item2 = items.get(5);
								item1.hide()
								item2.hide()
    			         }
    	        	}
    	    	}
    			
    		}]
    	},{
    		columnWidth:0.05,
    		layout:'form',
    		border:false,
    		width:10,
    		hidden:true,
    		items:[{
    			xtype:'numberfield',
    			fieldClass:'field-align',
    			style: {imeMode:'disabled'},
    			id:'otherRates1',
    			hideLabel:true,
    			labelWidth:0,
    			width:50	
    		}]
    	},{
			columnWidth : .02, // 该列在整行中所占的百分比
			layout : "form", // 从上往下的布局
			labelWidth : 14,
			border:false,
			hidden:true,
			items : [{
				fieldLabel : "%",
				labelSeparator:'', 
				anchor : "100%"
			}]
		}
    	,{
    		columnWidth:0.1,
    		layout:'form',
    		border:false,
    		items:[{
    			xtype:'button',
    			iconCls : 'search',
    			text:'查询结果',
    			scope:this,
    			handler:function(){
    			state='1'
    			var curDate=new Date();
				var date=curDate.format("Y-m-d");	
			
				var demandDate=Ext.getCmp("demandDate").getValue().format("Y-m-d")
				 if(demandDate<date){
					 Ext.ux.Toast.msg('操作信息', '所选日期不能为历史时期!');
					 return;
				 }
    			formPanel.getForm().submit({
					waitMsg : '正在提交查询',
					url : '',// __ctxPath + '/finance/submitSlCapitalFlowtemp.do',
					method : 'post',
					 params : {
    				    demandDate : Ext.getCmp("demandDate").getValue(),
						money:Ext.getCmp("money").getValue(),
						recoveryRate:Ext.getCmp("recoveryRate1").getValue(),
						otherRates:Ext.getCmp("otherRates1").getValue()
					},
					success : function(form, action) {
                       
						var object = Ext.util.JSON
								.decode(action.response.responseText)
						if(object.msg>=0){
							var items = searchPanel1.items;
							var item1 = items.get(7);
							var item2 = items.get(8);
							item1.show();
							item2.hide();
							Ext.getCmp("yue").setText(object.msg+"元")
						}else{
							var items = searchPanel1.items;
							var item1 = items.get(7);
							var item2 = items.get(8);
							item1.hide();
							item2.show();
							var str=object.msg.toString();
							str=str.substring(1,str.length);
							Ext.getCmp("chae").setText(str+"元")
						};
						var temp = Ext.getCmp('reportTemp' + reportId);
						temp.body
								.update('<iframe src="'
										+ __ctxPath
										+ '/report/report.jsp?reportId='
										+ reportId
										+ encodeURI(encodeURI(object.data))
										+ '" height="98%" width="98%" scrolling="auto"></iframe>');

						

					},
					failure : function(form, action) {
					

					}
				});
    		    }
    		}]
    	},{
    		columnWidth:0.15,
    		layout:'form',
    		border:false,
    		labelWidth:100,
    		labelAlign:'right',
    		hidden:true,
    		items:[{
    			xtype:'label',
    			format:'Y-m-d',
    			id:'yue',
    			fieldLabel:'<font color="red">达到，余额为：</font>',
    			text:''
    			
    		}]
    	},{
    		columnWidth:0.15,
    		layout:'form',
    		border:false,
    		labelWidth:100,
    		labelAlign:'right',
    		hidden:true,
    		items:[{
    			xtype:'label',
    			format:'Y-m-d',
    			id:'chae',
    			fieldLabel:'<font color="red">未达到，差额为：</font>',
    			text:''
    			
    		}]
    	}]
    })
	// 参数Form
//	reset : function() {
//		      alert("sdfadf");
//				formPanel.getForm().reset();
//			};
	formPanel = new Ext.FormPanel({

		defaults : {

			anchor : '10%,10%'
		},
		frame : false,
		border : false,
		layout : 'hbox',
		// style : 'padding:50px 50px 50px 50px',

		layoutConfig : {
			padding : '5',
			align : 'middle'
		},
		defaults : {
			xtype : 'label',
			margins : {
				top : 0,
				right : 4,
				bottom : 4,
				left : 4
			}
		},
		items : [],
		scope : this,
		listeners : {
			'afterrender' : function(formPanel) {
			pdfButton.on('click', function(xlsButton) {
               
				
				if (formPanel.getForm().isValid()) {
					
					if(state=='0'){
						if(Ext.getCmp("categoryType").getValue()=='0'){
		    				var curDate=new Date();
		    				var date=curDate.format("Y-m-d");	
		    				var dayStartTime=Ext.getCmp("dayStartTime").getValue().format("Y-m-d");
		    				var dayEndTime=Ext.getCmp("dayEndTime").getValue().format("Y-m-d")
		    				 if(dayStartTime<date || dayEndTime<date){
		    					 Ext.ux.Toast.msg('操作信息', '所选日期不能为历史时期!');
		    					 return;
		    				 }else{
		    					
		    					 if((Ext.getCmp("dayEndTime").getValue()-Ext.getCmp("dayStartTime").getValue())/24/60/60/1000>90){
		    						 Ext.ux.Toast.msg('操作信息', '开始日期和截止日期相隔天数不能超过90天!');
		    						 return;
		                         }   					
		    				 }
		    			}else{
		    				var curDate=new Date();
		    				var date=curDate.format("Y-m");	
		    				var monthStartTime=Ext.getCmp("monthStartTime").getValue().format("Y-m");
		    				var monthEndTime=Ext.getCmp("monthEndTime").getValue().format("Y-m")

		    				 if(monthStartTime<date || monthEndTime<date){
		    					 Ext.ux.Toast.msg('操作信息', '所选日期不能为历史时期!');
		    					 return;
		    				 }else{
		    					
		    					 if((Ext.getCmp("monthEndTime").getValue().getFullYear()-Ext.getCmp("monthStartTime").getValue().getFullYear())*12+Ext.getCmp("monthEndTime").getValue().getMonth()-Ext.getCmp("monthStartTime").getValue().getMonth()>12){
		    						 Ext.ux.Toast.msg('操作信息', '开始日期和截止日期相隔月数不能超过12个月!');
		    						 return;
		                         }   					
		    				 }
		    			}
					
	
						formPanel.getForm().submit({
							waitMsg : '正在提交查询',
							url :  '',//__ctxPath + '/creditFlow/finance/searchSlCapitalFlowtemp.do',
							method : 'post',
							 params : {
								categoryType : Ext.getCmp("categoryType").getValue(),
								dayStartTime:Ext.getCmp("dayStartTime").getValue(),
								dayEndTime:Ext.getCmp("dayEndTime").getValue(),
								monthStartTime:Ext.getCmp("monthStartTime").getValue(),
								monthEndTime:Ext.getCmp("monthEndTime").getValue(),
								recoveryRate:Ext.getCmp("recoveryRate").getValue(),
								otherRates:Ext.getCmp("otherRates").getValue()
							},
							success : function(form, action) {
								var object = Ext.util.JSON
										.decode(action.response.responseText)
								document.location.href = __ctxPath
										+ '/report/report.jsp?reportId='
										+ reportId
										+ encodeURI(encodeURI(object.data))
										+ '&reportType=pdf';
								
							},
							failure : function(form, action) {
								
							}
						});
					}else if(state=='1'){
						var curDate=new Date();
						var date=curDate.format("Y-m-d");	
					
						var demandDate=Ext.getCmp("demandDate").getValue().format("Y-m-d")
						 if(demandDate<date){
							 Ext.ux.Toast.msg('操作信息', '所选日期不能为历史时期!');
							 return;
						 }
		    			formPanel.getForm().submit({
							waitMsg : '正在提交查询',
							url : '',// __ctxPath + '/creditFlow/finance/submitSlCapitalFlowtemp.do',
							method : 'post',
							 params : {
		    				    demandDate : Ext.getCmp("demandDate").getValue(),
								money:Ext.getCmp("money").getValue(),
								recoveryRate:Ext.getCmp("recoveryRate1").getValue(),
								otherRates:Ext.getCmp("otherRates1").getValue()
							},
							success : function(form, action) {
		                       
								var object = Ext.util.JSON
										.decode(action.response.responseText)
								if(object.msg>=0){
									var items = searchPanel1.items;
									var item1 = items.get(7);
									var item2 = items.get(8);
									item1.show();
									item2.hide();
									Ext.getCmp("yue").setText(object.msg+"元")
								}else{
									var items = searchPanel1.items;
									var item1 = items.get(7);
									var item2 = items.get(8);
									item1.hide();
									item2.show();
									var str=object.msg.toString();
									str=str.substring(1,str.length);
									Ext.getCmp("chae").setText(str+"元")
								};
								document.location.href = __ctxPath
								+ '/report/report.jsp?reportId='
								+ reportId
								+ encodeURI(encodeURI(object.data))
								+ '&reportType=pdf';
								

							},
							failure : function(form, action) {
							

							}
						});
					}
				}

			}, this);
			
				htmlButton.on('click', function(htmlButton) {

					if (formPanel.getForm().isValid()) {
						
						if(state=='0'){
							if(Ext.getCmp("categoryType").getValue()=='0'){
			    				var curDate=new Date();
			    				var date=curDate.format("Y-m-d");	
			    				var dayStartTime=Ext.getCmp("dayStartTime").getValue().format("Y-m-d");
			    				var dayEndTime=Ext.getCmp("dayEndTime").getValue().format("Y-m-d")
			    				 if(dayStartTime<date || dayEndTime<date){
			    					 Ext.ux.Toast.msg('操作信息', '所选日期不能为历史时期!');
			    					 return;
			    				 }else{
			    					
			    					 if((Ext.getCmp("dayEndTime").getValue()-Ext.getCmp("dayStartTime").getValue())/24/60/60/1000>90){
			    						 Ext.ux.Toast.msg('操作信息', '开始日期和截止日期相隔天数不能超过90天!');
			    						 return;
			                         }   					
			    				 }
			    			}else{
			    				var curDate=new Date();
			    				var date=curDate.format("Y-m");	
			    				var monthStartTime=Ext.getCmp("monthStartTime").getValue().format("Y-m");
			    				var monthEndTime=Ext.getCmp("monthEndTime").getValue().format("Y-m")
			    		
			    				 if(monthStartTime<date || monthEndTime<date){
			    					 Ext.ux.Toast.msg('操作信息', '所选日期不能为历史时期!');
			    					 return;
			    				 }else{
			    					
			    					 if((Ext.getCmp("monthEndTime").getValue().getFullYear()-Ext.getCmp("monthStartTime").getValue().getFullYear())*12+Ext.getCmp("monthEndTime").getValue().getMonth()-Ext.getCmp("monthStartTime").getValue().getMonth()>12){
			    						 Ext.ux.Toast.msg('操作信息', '开始日期和截止日期相隔月数不能超过12个月!');
			    						 return;
			                         }   					
			    				 }
			    			}
						
		
							formPanel.getForm().submit({
								waitMsg : '正在提交查询',
								url : '',// __ctxPath + '/creditFlow/finance/searchSlCapitalFlowtemp.do',
								method : 'post',
								 params : {
									categoryType : Ext.getCmp("categoryType").getValue(),
									dayStartTime:Ext.getCmp("dayStartTime").getValue(),
									dayEndTime:Ext.getCmp("dayEndTime").getValue(),
									monthStartTime:Ext.getCmp("monthStartTime").getValue(),
									monthEndTime:Ext.getCmp("monthEndTime").getValue(),
									recoveryRate:Ext.getCmp("recoveryRate").getValue(),
									otherRates:Ext.getCmp("otherRates").getValue()
								},
								success : function(form, action) {
									var object = Ext.util.JSON
											.decode(action.response.responseText)
									document.location.href = __ctxPath
											+ '/report/report.jsp?reportId='
											+ reportId
											+ encodeURI(encodeURI(object.data))
											+ '&reportType=html';
									
								},
								failure : function(form, action) {
									
								}
							});
						}else if(state=='1'){
							var curDate=new Date();
							var date=curDate.format("Y-m-d");	
						
							var demandDate=Ext.getCmp("demandDate").getValue().format("Y-m-d")
							 if(demandDate<date){
								 Ext.ux.Toast.msg('操作信息', '所选日期不能为历史时期!');
								 return;
							 }
			    			formPanel.getForm().submit({
								waitMsg : '正在提交查询',
								url : '',// __ctxPath + '/creditFlow/finance/submitSlCapitalFlowtemp.do',
								method : 'post',
								 params : {
			    				    demandDate : Ext.getCmp("demandDate").getValue(),
									money:Ext.getCmp("money").getValue(),
									recoveryRate:Ext.getCmp("recoveryRate1").getValue(),
									otherRates:Ext.getCmp("otherRates1").getValue()
								},
								success : function(form, action) {
			                       
									var object = Ext.util.JSON
											.decode(action.response.responseText)
									if(object.msg>=0){
										var items = searchPanel1.items;
										var item1 = items.get(7);
										var item2 = items.get(8);
										item1.show();
										item2.hide();
										Ext.getCmp("yue").setText(object.msg+"元")
									}else{
										var items = searchPanel1.items;
										var item1 = items.get(7);
										var item2 = items.get(8);
										item1.hide();
										item2.show();
										var str=object.msg.toString();
										str=str.substring(1,str.length);
										Ext.getCmp("chae").setText(str+"元")
									};
									document.location.href = __ctxPath
									+ '/report/report.jsp?reportId='
									+ reportId
									+ encodeURI(encodeURI(object.data))
									+ '&reportType=html';
									

								},
								failure : function(form, action) {
								

								}
							});
						}
					}

				}, this);
				xlsButton.on('click', function(xlsButton) {
					if (formPanel.getForm().isValid()) {
						
						if(state=='0'){
							if(Ext.getCmp("categoryType").getValue()=='0'){
			    				var curDate=new Date();
			    				var date=curDate.format("Y-m-d");	
			    				var dayStartTime=Ext.getCmp("dayStartTime").getValue().format("Y-m-d");
			    				var dayEndTime=Ext.getCmp("dayEndTime").getValue().format("Y-m-d")
			    				 if(dayStartTime<date || dayEndTime<date){
			    					 Ext.ux.Toast.msg('操作信息', '所选日期不能为历史时期!');
			    					 return;
			    				 }else{
			    					
			    					 if((Ext.getCmp("dayEndTime").getValue()-Ext.getCmp("dayStartTime").getValue())/24/60/60/1000>90){
			    						 Ext.ux.Toast.msg('操作信息', '开始日期和截止日期相隔天数不能超过90天!');
			    						 return;
			                         }   					
			    				 }
			    			}else{
			    				var curDate=new Date();
			    				var date=curDate.format("Y-m");	
			    				var monthStartTime=Ext.getCmp("monthStartTime").getValue().format("Y-m");
			    				var monthEndTime=Ext.getCmp("monthEndTime").getValue().format("Y-m")
			    				 if(monthStartTime<date || monthEndTime<date){
			    					 Ext.ux.Toast.msg('操作信息', '所选日期不能为历史时期!');
			    					 return;
			    				 }else{
			    					
			    					 if((Ext.getCmp("monthEndTime").getValue().getFullYear()-Ext.getCmp("monthStartTime").getValue().getFullYear())*12+Ext.getCmp("monthEndTime").getValue().getMonth()-Ext.getCmp("monthStartTime").getValue().getMonth()>12){
			    						 Ext.ux.Toast.msg('操作信息', '开始日期和截止日期相隔月数不能超过12个月!');
			    						 return;
			                         }   					
			    				 }
			    			}
						
		
							formPanel.getForm().submit({
								waitMsg : '正在提交查询',
								url : '',// __ctxPath + '/creditFlow/finance/searchSlCapitalFlowtemp.do',
								method : 'post',
								 params : {
									categoryType : Ext.getCmp("categoryType").getValue(),
									dayStartTime:Ext.getCmp("dayStartTime").getValue(),
									dayEndTime:Ext.getCmp("dayEndTime").getValue(),
									monthStartTime:Ext.getCmp("monthStartTime").getValue(),
									monthEndTime:Ext.getCmp("monthEndTime").getValue(),
									recoveryRate:Ext.getCmp("recoveryRate").getValue(),
									otherRates:Ext.getCmp("otherRates").getValue()
								},
								success : function(form, action) {
									var object = Ext.util.JSON
											.decode(action.response.responseText)
									document.location.href = __ctxPath
											+ '/report/report.jsp?reportId='
											+ reportId
											+ encodeURI(encodeURI(object.data))
											+ '&reportType=xls';
									
								},
								failure : function(form, action) {
									
								}
							});
						}else if(state=='1'){
							var curDate=new Date();
							var date=curDate.format("Y-m-d");	
						
							var demandDate=Ext.getCmp("demandDate").getValue().format("Y-m-d")
							 if(demandDate<date){
								 Ext.ux.Toast.msg('操作信息', '所选日期不能为历史时期!');
								 return;
							 }
			    			formPanel.getForm().submit({
								waitMsg : '正在提交查询',
								url :  '',//__ctxPath + '/creditFlow/finance/submitSlCapitalFlowtemp.do',
								method : 'post',
								 params : {
			    				    demandDate : Ext.getCmp("demandDate").getValue(),
									money:Ext.getCmp("money").getValue(),
									recoveryRate:Ext.getCmp("recoveryRate1").getValue(),
									otherRates:Ext.getCmp("otherRates1").getValue()
								},
								success : function(form, action) {
			                       
									var object = Ext.util.JSON
											.decode(action.response.responseText)
									if(object.msg>=0){
										var items = searchPanel1.items;
										var item1 = items.get(7);
										var item2 = items.get(8);
										item1.show();
										item2.hide();
										Ext.getCmp("yue").setText(object.msg+"元")
									}else{
										var items = searchPanel1.items;
										var item1 = items.get(7);
										var item2 = items.get(8);
										item1.hide();
										item2.show();
										var str=object.msg.toString();
										str=str.substring(1,str.length);
										Ext.getCmp("chae").setText(str+"元")
									};
									document.location.href = __ctxPath
									+ '/report/report.jsp?reportId='
									+ reportId
									+ encodeURI(encodeURI(object.data))
									+ '&reportType=xls';
									

								},
								failure : function(form, action) {
								

								}
							});
						}
					}

				}, this);
				
				resetButton.on('click', function(resetButton) {
				   formPanel.getForm().reset();
					
				});
				searchButton.on('click', function(searchButton) {

					
					if (formPanel.getForm().isValid()) {
						
						formPanel.getForm().submit({
							waitMsg : '正在提交查询',
							url : __ctxPath + '/system/submit1ReportTemplate.do',
							method : 'post',
							success : function(form, action) {

								var object = Ext.util.JSON
										.decode(action.response.responseText)
								var temp = Ext.getCmp('reportTemp' + reportId);
								temp.body
										.update('<iframe src="'
												+ __ctxPath
												+ '/report/report.jsp?reportId='
												+ reportId
												+ encodeURI(encodeURI(object.data))
												+ '" height="98%" width="98%" scrolling="auto"></iframe>');

								

							},
							failure : function(form, action) {
							

							}
						});
					}

				}, this);
			}
		}
	}).show();
	// 参数容器
	var container = new Ext.Container({
				layout : 'table',
				layoutConfig : {
					columns : 8
				},
				defaults : {
					bodyStyle : 'padding:20px'
				}

			});

	// 取得查询字段
	Ext.Ajax.request({
				url : __ctxPath + "/system/loadReportTemplate.do?reportId="
						+ reportId,
				method : 'POST',
				async : false,
				success : function(response, options) {
					var dateSort = 0;// 日期序列
					var thisDate = new Date();
					var thisMon = new Date(thisDate.getFullYear(), thisDate
									.getMonth(), 1);
					var nextMon = new Date(thisDate.getFullYear(), (thisDate
									.getMonth())
									+ 1, 1);
					

					formPanel.removeAll(true);
					var object = Ext.util.JSON.decode(response.responseText)
					for (var i = 0; i < object.data.length; i++) {

						var text = object.data[i].paramName;
						var xtype = object.data[i].paramType;
						var name = object.data[i].paramKey;
						var value = object.data[i].defaultVal;
						var paramTypeStr = object.data[i].paramTypeStr;
						var paramTypeExt = Ext.decode(paramTypeStr);

						var label = new Ext.form.Label({
									text : text + ': '
								});

						switch (xtype) {
							case 'combo' :
								Ext.apply(paramTypeExt, {
											allowBlank : true,
											value : value,
											fieldLabel : '',
											hiddenName : name,
											id : name + new Date(),
											width : 100,
											editable:false
										});
								container.add(label);
								var combo = new Ext.form.ComboBox(paramTypeExt);
								container.add(combo);
								break;
							case 'diccombo' :
								Ext.apply(paramTypeExt, {
											allowBlank : true,
											value : value,
											fieldLabel : '',
											name : name,
											id : name + new Date(),
											width : 100
										});
								container.add(label);
								var diccombo = new DicCombo(paramTypeExt);
								container.add(diccombo);
								break;
							case 'datetimefield' :	
							
								var s = dateSort % 2;
								switch (s) {
									case 0 :
										Ext.apply(paramTypeExt, {
													allowBlank : false,
													value : thisMon,
													format : 'Y-m-d H:i:s',
													fieldLabel : '',
													name : name,
													id : name + new Date(),
													width : 200
												});
										container.add(label);
										container.add(paramTypeExt);
										dateSort++;
										break;
									case 1 :
										Ext.apply(paramTypeExt, {
													allowBlank : false,
													value : nextMon,
													format : 'Y-m-d H:i:s',
													fieldLabel : '',
													name : name,
													id : name + new Date(),
													width : 200
												});
										container.add(label);
										container.add(paramTypeExt);
										dateSort++;
										break;
								}

								break;
							case 'datefield' :
								var s = dateSort % 2;
								switch (s) {
									case 0 :
										Ext.apply(paramTypeExt, {
													allowBlank : true,
													//value : thisMon,
													format : 'Y-m-d',
													fieldLabel : '',
													name : name,
													id : name + new Date(),
													width : 100
												});
										container.add(label);
										container.add(paramTypeExt);
										dateSort++;
										break;
									case 1 :
										Ext.apply(paramTypeExt, {
													allowBlank : true,
												//	value : nextMon,
													format : 'Y-m-d',
													fieldLabel : '',
													name : name,
													id : name + new Date(),
													width : 100
												});
										container.add(label);
										container.add(paramTypeExt);
										dateSort++;
										break;
								}

								break;
							default :
								Ext.apply(paramTypeExt, {
											allowBlank : true,
											value : value,
											fieldLabel : '',
											name : name,
											id : name + new Date(),
											width : 100
										});
								container.add(label);
								container.add(paramTypeExt);
						}
					}

					//container.add(searchButton);
                  // container.add(resetButton);
					formPanel.add(container);
					container.doLayout(true);
					formPanel.doLayout(true);
				}
			});
	var toolbar = new Ext.Toolbar({
				// id : 'createToolbar',
				autoWidth : true,
				layout : 'hbox',
				defaults : {

					anchor : '10%,10%'
				},
				items : [
						
						pdfButton,xlsButton, htmlButton]
			});

	var panel = new Ext.Panel({
				id : 'ReportPreview' + reportId,
				title : '查看-' + title,
				iconCls : 'menu-report',
				
				autoScroll : false,
				autoHeight : true,
				
				border : false,
				frame : false,
				listeners : {
					'afterrender' : function(panel) {
						//高度布局
						var f_h = formPanel.getHeight();
						var t_h = toolbar.getHeight();
						var i_h = Ext.getCmp('reportTemp' + reportId)
								.getHeight();
						var a_h = Ext.getCmp('centerTabPanel').getInnerHeight();
						
						Ext.getCmp('reportTemp' + reportId).setHeight(a_h
								- (f_h + t_h));
						
						Ext.getCmp('reportTemp' + reportId).doLayout();
						//自动加载
						searchButton.fireEvent('click');
						
						
					}
				},
				items : [searchPanel,searchPanel1,formPanel, toolbar, {
					id : 'reportTemp' + reportId,
					// tbar : toolbar,
					autoHeight : false,
					autoWidth : false,
					autoScroll : false,
					border : false,
					xtype : 'panel',
					height : 415,
					autoLoad:false
//					,
//					// 根据页面传来reportId，动态加载报表模版路径，显示报表
//					html : '<iframe src="'
//							+ __ctxPath
//							+ '/report/report.jsp?reportId='
//							+ reportId
//							+ '" height="98%" width="98%" scrolling="auto"></iframe>'
				}

				]
			});

	return panel;
}