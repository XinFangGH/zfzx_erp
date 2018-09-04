var ReportTemplatePreview1 = function(reportId, title) {
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

	// 参数Form
//	reset : function() {
//		      alert("sdfadf");
//				formPanel.getForm().reset();
//			};
	var isShow=false; //不显示分公司查询条件
	if(RoleType=="control"){
	    isShow=true; //显示分公司查询条件
	}
	    searchPanel=new Ext.FormPanel({
	    	layout : 'column',
	    	region : 'north',
	    	height:40,
	    	layoutConfig: {
	            align:'middle'
	        },
	        border:false,
	        bodyStyle : 'padding:10px 10px 10px 10px',
	    	items:[
		    	{
		    	   columnWidth:.3,
		    	   layout:'form',
		    	   labelWidth:80,
		    	   labelAlign:'right',
		    	   border:false,
		    	   items:[{
				      xtype : "combo",
				      anchor : "98%",
				      fieldLabel : '所属分公司',
				      hiddenName : "companyId",
				      displayField : 'companyName',
				      valueField : 'companyId',
				      triggerAction : 'all',				     
				      store : new Ext.data.SimpleStore({
				       autoLoad :isShow,
				       url : __ctxPath+'/system/getControlNameOrganization.do',
				       fields : ['companyId', 'companyName']
				      })
				    }]
		    	},{
		    		columnWidth:.1,
		    		layout:'form',
		    		border:false,
		    		items:[{
		    		    xtype:'button',
		    		    iconCls : 'search',
						width : '80%',
		    		    text:'查询',
		    		    handler:function(){
		    		    	if (searchPanel.getForm().isValid()) {
						
								searchPanel.getForm().submit({
									waitMsg : '正在提交查询',
									url : __ctxPath + '/system/submit2ReportTemplate.do',
									method : 'post',
									success : function(form, action) {
		
										var object = Ext.util.JSON
												.decode(action.response.responseText)
										var temp = panel.getCmpByName('reportTemp' + reportId);
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
		    		    }
		    		
		    		}]
		    	},{
		    		columnWidth:.1,
		    		layout:'form',
		    		border:false,
		    		items:[{
		    		    xtype:'button',
		    		    iconCls : 'btn-reset',
						width : '80%',
		    		    text:'重置'
		    		
		    		}]
		    	}
			   
			],
			listeners:{
				scope:this,
				
			'afterrender' : function(searchPanel) {
			pdfButton.on('click', function(xlsButton) {
				if (searchPanel.getForm().isValid()) {
					
					searchPanel.getForm().submit({
						waitMsg : '正在提交查询',
						url : __ctxPath + '/system/submit2ReportTemplate.do',
						method : 'post',
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
				}

			}, this);
			
				htmlButton.on('click', function(htmlButton) {

				
					if (searchPanel.getForm().isValid()) {
						
						searchPanel.getForm().submit({
							waitMsg : '正在提交查询',
							url : __ctxPath + '/system/submit2ReportTemplate.do',
							method : 'post',
							success : function(form, action) {
								var object = Ext.util.JSON
										.decode(action.response.responseText)
								window
										.open(
												__ctxPath
														+ '/report/report.jsp?reportId='
														+ reportId
														+ encodeURI(encodeURI(object.data))
														+ '&reportType=html',
												'blank');
								
							},
							failure : function(form, action) {
								
							}
						});
					}

				}, this);
				xlsButton.on('click', function(xlsButton) {

					
					if (searchPanel.getForm().isValid()) {
						
						searchPanel.getForm().submit({
							waitMsg : '正在提交查询',
							url : __ctxPath + '/system/submit2ReportTemplate.do',
							method : 'post',
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
					}

				}, this);
				searchButton.on('click', function(searchButton) {

					
					if (searchPanel.getForm().isValid()) {
						
						searchPanel.getForm().submit({
							waitMsg : '正在提交查询',
							url : __ctxPath + '/system/submit2ReportTemplate.do',
							method : 'post',
							success : function(form, action) {

								var object = Ext.util.JSON
										.decode(action.response.responseText)
								var temp = panel.getCmpByName('reportTemp' + reportId);
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
			
    })

	formPanel = new Ext.FormPanel({

		defaults : {

			anchor : '10%,10%'
		},
		frame : false,
		border : false,
		height:0,
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
					
					formPanel.getForm().submit({
						waitMsg : '正在提交查询',
						url : __ctxPath + '/system/submit2ReportTemplate.do',
						method : 'post',
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
				}

			}, this);
			
				htmlButton.on('click', function(htmlButton) {

				
					if (formPanel.getForm().isValid()) {
						
						formPanel.getForm().submit({
							waitMsg : '正在提交查询',
							url : __ctxPath + '/system/submit2ReportTemplate.do',
							method : 'post',
							success : function(form, action) {
								var object = Ext.util.JSON
										.decode(action.response.responseText)
								window
										.open(
												__ctxPath
														+ '/report/report.jsp?reportId='
														+ reportId
														+ encodeURI(encodeURI(object.data))
														+ '&reportType=html',
												'blank');
								
							},
							failure : function(form, action) {
								
							}
						});
					}

				}, this);
				xlsButton.on('click', function(xlsButton) {

					
					if (formPanel.getForm().isValid()) {
						
						formPanel.getForm().submit({
							waitMsg : '正在提交查询',
							url : __ctxPath + '/system/submit2ReportTemplate.do?',
							method : 'post',
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
					}

				}, this);
				
				resetButton.on('click', function(resetButton) {
				   formPanel.getForm().reset();
					
				});
				searchButton.on('click', function(searchButton) {

					
					if (formPanel.getForm().isValid()) {
						
						formPanel.getForm().submit({
							waitMsg : '正在提交查询',
							url : __ctxPath + '/system/submit2ReportTemplate.do',
							method : 'post',
							success : function(form, action) {

								var object = Ext.util.JSON
										.decode(action.response.responseText)
								var temp = panel.getCmpByName('reportTemp' + reportId);
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
	})


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
						var f_h=0;
						if(RoleType=='control'){
						   f_h = searchPanel .getHeight();
						}
						var t_h = toolbar.getHeight();
						var i_h = panel.getCmpByName('reportTemp' + reportId)
								.getHeight();
						var a_h = Ext.getCmp('centerTabPanel').getInnerHeight();
						
						panel.getCmpByName('reportTemp' + reportId).setHeight(a_h
								- (f_h + t_h));
						
						panel.getCmpByName('reportTemp' + reportId).doLayout();
						//自动加载
						searchButton.fireEvent('click');
						
						
					}
				},
				items : [isShow?searchPanel:formPanel, toolbar, {
					name : 'reportTemp' + reportId,
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