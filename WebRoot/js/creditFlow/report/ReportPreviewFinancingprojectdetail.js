var ReportPreviewFinancingprojectdetail = function(reportId, title,reportKey,projectMoney,projectMoneyl,
												
												projectNumber,
												oppositeTypeValue,
												customerName,
											businessManagerid ,
												businessManager ,
												startDateg,
												startDatel,
												projectStatus)
{
	
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
	var anchor='100%';
	var leftlabelwidth=90;
	var isShow=false;
	if(RoleType=="control"){
	  isShow=true;
	}
		var searchPanel =new Ext.FormPanel({
			layout : 'form',
			border : true,
			region : 'north',
			height : 40,
			anchor : '70%',
			items : [{
				border : false,
				layout : 'column',
				style : 'padding-left:5px;padding-right:5px;padding-top:5px;',
				layoutConfig : {
					align : 'middle',
					padding : '5px'
				},
				defaults : {
					xtype : 'label',
					anchor :anchor

				},
				items : [
				isShow?{
				   columnWidth : .25,
					labelAlign : 'right',
					xtype : 'container',
					layout : 'form',
					labelWidth : leftlabelwidth,
					defaults : {
						anchor : '100%'
					},
						border : false,
						items : [
						{
								xtype : "combo",
								anchor : "100%",
								fieldLabel : '所属分公司',
								hiddenName : "orgName",
								name : 'orgName',
								displayField : 'companyName',
								valueField : 'companyName',
								triggerAction : 'all',
								store : new Ext.data.SimpleStore({
									autoLoad : true,
									url : __ctxPath+'/system/getControlNameOrganization.do',
									fields : ['companyId', 'companyName']
								})
						}
					]}:{}, {
					columnWidth : .15,
					labelAlign : 'right',
					xtype : 'container',
					layout : 'form',
					labelWidth : leftlabelwidth,
					defaults : {
						anchor : anchor
					},
					items : [{
							fieldLabel:'未付息金额',
							name : 'notMoneyg',
							xtype : 'numberfield'
						}]
				}, {
					columnWidth : .1,
					labelAlign : 'right',
					xtype : 'container',
					layout : 'form',
					labelWidth : 20,
					defaults : {
						anchor : anchor
					},
				
					items : [{
							fieldLabel:'到',
							labelSeparator:"",
							name : 'notMoneyl',
							xtype : 'numberfield'
						}]
				}, {
					columnWidth : .16,
					labelAlign : 'right',
					xtype : 'container',
					layout : 'form',
					labelWidth : leftlabelwidth,
					defaults : {
						anchor : anchor
					},
					items : [{
							fieldLabel:'计划到账日',
							name : 'intentDateg',
							xtype : 'datefield',
							format : 'Y-m-d'
							
						}]
				}, {
					columnWidth : .1,
					labelAlign : 'right',
					xtype : 'container',
					layout : 'form',
					labelWidth : 20,
					defaults : {
						anchor :anchor
					},
					items : [{
							fieldLabel:'到',
							name : 'intentDatel',
							xtype : 'datefield',
							labelSeparator:"",
							format : 'Y-m-d'
						}]
				}, {
					columnWidth : .12,
					labelAlign : 'right',
					xtype : 'container',
					layout : 'form',
					labelWidth : 55,
					defaults : {
						xtype : 'button'
					},
					//style : 'padding-left:60px;',
					items : [{
								text:'查询',
								fieldLabel : ' ',
								labelSeparator:"",
								iconCls:'btn-search',
								scope :this,
								handler:function(){
									if (searchPanel.getForm().isValid()) {
				
									searchPanel.getForm().submit({
										waitMsg : '正在提交查询',
										url : __ctxPath + '/system/submit1ReportTemplate.do?reportKey='+reportKey,
										method : 'post',
										success : function(form, action) {
			
											var object = Ext.util.JSON
													.decode(action.response.responseText)
											var temp = panel.getCmpByName('reportTemp' + reportKey);
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
				}, {
					columnWidth : .08,
					labelAlign : 'right',
					xtype : 'container',
					layout : 'form',
					
					labelWidth :1,
					defaults : {
						xtype : 'button'
					},
					items : [{
								text:'重置',
								fieldLabel : '',
								labelSeparator:"",
								iconCls:'btn-reset',
								handler:function(){
								
								 searchPanel.getForm().reset();
								}
							}]
				}]
			}],
			scope : this,
			listeners : {
				'afterrender' : function(searchPanel) {
				pdfButton.on('click', function(xlsButton) {
	
					
					if (searchPanel.getForm().isValid()) {
						
						searchPanel.getForm().submit({
							waitMsg : '正在提交查询',
							url : __ctxPath + '/system/submit1ReportTemplate.do?reportKey='+reportKey,
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
								url : __ctxPath + '/system/submit1ReportTemplate.do?reportKey='+reportKey,
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
								url : __ctxPath + '/system/submit1ReportTemplate.do?reportKey='+reportKey,
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
				id : 'ReportPreview' + reportKey,
				title : '查看-付息明细报表',
				iconCls : 'menu-report',
				autoScroll : false,
				autoHeight : true,
				border : false,
				frame : false,
				listeners : {
					'afterrender' : function(panel) {
				//						var f_h = formPanel.getHeight();
						var t_h = toolbar.getHeight();
						var i_h = panel.getCmpByName('reportTemp' + reportKey)
								.getHeight();
						var a_h = Ext.getCmp('centerTabPanel').getInnerHeight();
						
						panel.getCmpByName('reportTemp' + reportKey).setHeight(a_h
								- (65 + t_h));
						
						panel.getCmpByName('reportTemp' + reportKey).doLayout();
						//自动加载
				//		searchButton.fireEvent('click');
						searchPanel.getForm().submit({
												waitMsg : '正在提交查询',
												url : __ctxPath + '/system/submit1ReportTemplate.do?reportKey='+reportKey,
												method : 'post',
												success : function(form, action) {
					
													var object = Ext.util.JSON
															.decode(action.response.responseText)
													var temp = panel.getCmpByName('reportTemp' + reportKey);
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
				},
				items : [searchPanel, toolbar, {
					name : 'reportTemp' + reportKey,
					// tbar : toolbar,
					autoHeight : false,
					autoWidth : false,
					autoScroll : false,
					border : false,
					xtype : 'panel',
					height : 498,
					autoLoad:false
				}

				]
			});

	return panel;
}