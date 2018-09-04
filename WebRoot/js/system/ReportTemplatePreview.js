var ReportTemplatePreview = function(reportId, title,reportKey) {
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
					
					formPanel.getForm().submit({
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

				
					if (formPanel.getForm().isValid()) {
						
						formPanel.getForm().submit({
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

					
					if (formPanel.getForm().isValid()) {
						
						formPanel.getForm().submit({
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
				
				resetButton.on('click', function(resetButton) {
				   formPanel.getForm().reset();
					
				});
				searchButton.on('click', function(searchButton) {

					
					if (formPanel.getForm().isValid()) {
						
						formPanel.getForm().submit({
							waitMsg : '正在提交查询',
							url : __ctxPath + '/system/submit1ReportTemplate.do?reportKey='+reportKey,
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
			columns : 8,
			anchor : '96%'
		},
		defaults : {
			bodyStyle : 'padding:10px 10px 10px 10px'
			
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
						
									html : '<p align="right" style="margin-bottom:10px;margin-left:20px"><font size="2px">'+text + ':</font> </p>'
								
									
								});
                      
						switch (xtype) {
							case 'combo' :
								Ext.apply(paramTypeExt, {
											allowBlank : true,
											value : value,
											fieldLabel : '',
											hiddenName : name,
											id : name + new Date(),
											width : 163,
											
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
											width : 163
											
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
													width : 163
												
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
													width : 163
													
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
													width : 163
													
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
													width : 163
													
													
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
										    width :163
										});
								container.add(label);
								container.add(paramTypeExt);
						}
					}

					container.add(searchButton);
                   container.add(resetButton);
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
				items : [formPanel, toolbar, {
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