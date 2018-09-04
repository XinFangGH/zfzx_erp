var ReportPreviewAverageDailyBalance = function(reportId, title,reportKey) {
	resetButton = new Ext.Button({
				style:'margin-left:30px',
				text : '重置',
				iconCls : 'btn-reset',
				width : 20,
				handler : this.reset
			});

	searchButton = new Ext.Button({
				style:'margin-left:30px',
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


	formPanel = new Ext.FormPanel({
		layout : 'column',
		border : false,
		region : 'north',
		height : 40,
		anchor : '100%',
		layoutConfig: {
           align:'middle'
        },
         bodyStyle : 'padding:10px 10px 10px 10px',
         labelWidth:60,
         labelAlign:'right',
         items:[{
         	columnWidth:.2,
         	layout:'form',
         	labelWidth:60,
         	labelAlign:'right',
         	border:false,
         	items:[{
         		xtype:'datefield',
         		name:'endDate',
         		anchor:'100%',
         		fieldLabel:'截止日期',
         		format:'Y-m-d',
         		value:new Date()
         	}]
         },searchButton,resetButton],
		scope : this,
		listeners : {
			'afterrender' : function(formPanel) {
			pdfButton.on('click', function(xlsButton) {

				
				if (formPanel.getForm().isValid()) {
					if(formPanel.getCmpByName('endDate').getValue()==''){
						Ext.Msg.alert('操作信息','截止日期不能为空');
						return;
					}
					formPanel.getForm().submit({
						waitMsg : '正在提交查询',
						url : __ctxPath + '/system/submitAverageDailyBalanceReportTemplate.do?reportKey='+reportKey,
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
						if(formPanel.getCmpByName('endDate').getValue()==''){
							Ext.Msg.alert('操作信息','截止日期不能为空');
							return;
						}
						formPanel.getForm().submit({
							waitMsg : '正在提交查询',
							url : __ctxPath + '/system/submitAverageDailyBalanceReportTemplate.do?reportKey='+reportKey,
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
						if(formPanel.getCmpByName('endDate').getValue()==''){
							Ext.Msg.alert('操作信息','截止日期不能为空');
							return;
						}
						formPanel.getForm().submit({
							waitMsg : '正在提交查询',
							url : __ctxPath + '/system/submitAverageDailyBalanceReportTemplate.do?reportKey='+reportKey,
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
						if(formPanel.getCmpByName('endDate').getValue()==''){
							Ext.Msg.alert('操作信息','截止日期不能为空');
							return;
						}
						formPanel.getForm().submit({
							waitMsg : '正在提交查询',
							url : __ctxPath + '/system/submitAverageDailyBalanceReportTemplate.do?reportKey='+reportKey,
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