var ReportPreviewLeaseFinanceDetail = function(reportId, title,reportKey) {
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

	// 参数Form
//	reset : function() {
//		      alert("sdfadf");
//				formPanel.getForm().reset();
//			};
	var isShow=false; //不显示分公司查询条件
	if(RoleType=="control"){
	    isShow=true; //显示分公司查询条件
	}
	formPanel = new Ext.FormPanel({
		layout : 'column',
		border : false,
		region : 'north',
		height : 80,
		anchor : '100%',
		layoutConfig: {
           align:'middle'
        },
         bodyStyle : 'padding:10px 10px 10px 10px',
         labelWidth:60,
         labelAlign:'right',
         items:[{
	         	columnWidth :1,
	         	layout : 'column',
	         	border : false,
	         	items : [{
	         	columnWidth : .2,
	         	layout : 'form',
	         	border : false,
	         	items : [{
	         		xtype : 'textfield',
	         		anchor : '100%',
	         		name : 'customerName',
	         		fieldLabel : '客户名称'
	         	}]
	         },{
	         	columnWidth : .2,
	         	layout : 'form',
         		border : false,
	         	items : [{
	         		xtype : 'textfield',
	         		anchor : '100%',
	         		name : 'projectNumber',
	         		fieldLabel : '项目编号'
	         	}]
	         
	         },{
	         	columnWidth : .2,
	         	layout : 'form',
         		border : false,
	         	items : [{
         			xtype : 'datefield',
         			anchor : '100%',
         			format : 'Y-m-d',
         			name : 'startDate1',
         			fieldLabel : '开始日期'
	         	}]
	         },{
	         	columnWidth : .18,
	         	layout : 'form',
	         	labelWidth : 20,
         		border : false,
	         	items : [{
	         		xtype : 'datefield',
	         		anchor : '100%',
	         		format : 'Y-m-d',
	         		name : 'startDate2',
	         		fieldLabel : '至'
	         	}]
	         },isShow?{
		    columnWidth : 0.2,
		    layout : 'form',
		    border:false,
		   	labelWidth : 80,
			labelAlign : 'right',
		    items : [
		    {
			      xtype : "combo",
			      anchor : "100%",
			      fieldLabel : '所属分公司',
			      hiddenName : "companyId",
			      displayField : 'companyName',
			      valueField : 'companyId',
			      editable:false,
			      triggerAction : 'all',
			      store : new Ext.data.SimpleStore({
			       autoLoad : true,
			       url : __ctxPath+'/system/getCompanysOrganization.do',
			       fields : ['companyId', 'companyName']
			      })
			    }
		   ]}:{
		    columnWidth:0.01,
		    border:false
		    },searchButton]
         },{
         	columnWidth : 1,
         	layout : 'column',
         	border : false,
         	items : [{
         		columnWidth : .2,
         		layout : 'form',
     			border : false,
         		items : [{
         			xtype:'combo',
					mode : 'local',
				    displayField : 'name',
				    valueField : 'id',
				    store : new Ext.data.SimpleStore({
							fields : ["name", "id"],
							data : [["办理中项目", "0"],
									["还款中项目", "1"],
									["已完成项目", "2"],
									["违约处置项目", "8"],
									["提前终止项目", "3"],
									["全部项目", ""]]
					}),
					triggerAction : "all",
					hiddenName:"projectStatus",
					fieldLabel : '项目状态',
			        anchor : "100%"
					
         		}]
         	},{
         		columnWidth : .2,
         		layout : 'form',
     			border : false,
         		items : [{
						xtype : "combo",
						triggerClass : 'x-form-search-trigger',
						hiddenName : "appUserId",
						editable : false,
						fieldLabel : "项目经理",
						labelWidth : 85,
						blankText : "项目经理不能为空，请正确填写!",
						anchor : "100%",
						onTriggerClick : function(cc) {
							var obj = this;
							var appuerIdObj = obj.nextSibling();
							var appUserNameObj=obj.nextSibling().nextSibling();
							var userIds = appuerIdObj.getValue();
							if ("" == obj.getValue()) {
								userIds = "";
							}
							new UserDialog({
								userIds : userIds,
								userName : obj.getValue(),
								single : false,
								title : "选择项目经理",
								callback : function(uId, uname) {
									obj.setValue(uId);
									obj.setRawValue(uname);
									appuerIdObj.setValue(uId);
									appUserNameObj.setValue(uname)
								}
							}).show();
						}
					}, {
						xtype : "hidden",
						value : ""
					},{
						xtype : 'hidden',
						name : 'appUserName'
					}]
         	},{
	         	columnWidth : .2,
	         	layout : 'form',
         		border : false,
	         	items : [{
         			xtype : 'datefield',
         			anchor : '100%',
         			format : 'Y-m-d',
         			name : 'intentDate1',
         			fieldLabel : '到期日期'
	         	}]
	         },{
	         	columnWidth : .18,
	         	layout : 'form',
	         	labelWidth : 20,
         		border : false,
	         	items : [{
	         		xtype : 'datefield',
	         		anchor : '100%',
	         		format : 'Y-m-d',
	         		name : 'intentDate2',
	         		fieldLabel : '至'
	         	}]
	         },isShow?{
		    columnWidth : 0.2,
		    layout : 'form',
		    border:false}:{
		    columnWidth:0.01,
		    border:false
		    },resetButton]
         }],
		scope : this,
		listeners : {
			'afterrender' : function(formPanel) {
			pdfButton.on('click', function(xlsButton) {

				if (formPanel.getForm().isValid()) {
				
					formPanel.getForm().submit({
						waitMsg : '正在提交查询',
						url : __ctxPath + '/system/submitLeaseFinanceDetailReportTemplate.do?reportKey='+reportKey,
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
							url : __ctxPath + '/system/submitLeaseFinanceDetailReportTemplate.do?reportKey='+reportKey,
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
							url : __ctxPath + '/system/submitLeaseFinanceDetailReportTemplate.do?reportKey='+reportKey,
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
							url : __ctxPath + '/system/submitLeaseFinanceDetailReportTemplate.do?reportKey='+reportKey,
							method : 'post',
							success : function(form, action) {

								var object = Ext.util.JSON
										.decode(action.response.responseText)
								var temp =  panel.getCmpByName('reportTemp' + reportKey);
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
				id : 'ReportPreview' + reportKey,
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
						var i_h = panel.getCmpByName('reportTemp' + reportKey)
								.getHeight();
						var a_h = Ext.getCmp('centerTabPanel').getInnerHeight();
						
						 panel.getCmpByName('reportTemp' + reportKey).setHeight(a_h
								- (f_h + t_h));
						
						 panel.getCmpByName('reportTemp' + reportKey).doLayout();
						//自动加载
						searchButton.fireEvent('click');
						
						
					}
				},
				items : [formPanel, toolbar, {
					name : 'reportTemp' + reportKey,
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