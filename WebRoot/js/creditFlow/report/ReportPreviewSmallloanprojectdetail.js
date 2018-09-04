var ReportPreviewSmallloanprojectdetail = function(reportId, title,reportKey,projectMoney,projectMoneyl,
												
												projectNumber,
												projectName,
												oppositeTypeValue,
												customerName,
											businessManagerid ,
												businessManager ,
												startDateg,
												startDatel,
												companyId,
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
	var	searchPanel =new Ext.FormPanel({
					layout : 'form',
					border : true,
					region : 'north',
					height : 90,
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
										}),
										value:companyId
								}
							]}:{},{
							columnWidth : .25,
							labelAlign : 'right',
							xtype : 'container',
							layout : 'form',
							labelWidth : leftlabelwidth,
							defaults : {
								anchor : anchor
							},
							items : [{
									fieldLabel:'项目编号',
									name : 'projectNumber',
									xtype : 'textfield',
									value :projectNumber
								}]
						}, {
							columnWidth : .25,
							labelAlign : 'right',
							xtype : 'container',
							layout : 'form',
							labelWidth : leftlabelwidth,
							defaults : {
								anchor :anchor
							},
							items : [{
									fieldLabel:'项目名称',
									name : 'projectName',
									xtype : 'textfield',
									value:projectName
									
								}]
						}, {
							columnWidth : .25,
							labelAlign : 'right',
							xtype : 'container',
							layout : 'form',
							labelWidth : leftlabelwidth,
							defaults : {
								anchor : anchor
							},
							items : [{
									xtype : 'combo',
									mode : 'local',
									valueField : 'value',
									editable : false,
									displayField : 'value',
									store : new Ext.data.SimpleStore({
												fields : ["value"],
												data : [["企业"],
														["个人"]]
											}),
									triggerAction : "all",
									name : 'oppositeTypeValue',
									fieldLabel : '客户类别',
									value :oppositeTypeValue
									
								}]
						}, {
							columnWidth : .25,
							labelAlign : 'right',
							xtype : 'container',
							layout : 'form',
							labelWidth : leftlabelwidth,
							defaults : {
								anchor : anchor
							},
							items : [{
									fieldLabel:'客户',
									name : 'oppositeName',
									xtype : 'textfield',
									value:customerName
								}]
						}, {
							columnWidth : .14,
							labelAlign : 'right',
							xtype : 'container',
							layout : 'form',
							labelWidth : leftlabelwidth,
							defaults : {
								anchor : anchor
							},
							items : [{
									fieldLabel:'贷款金额',
									name : 'projectMoney',
									xtype : 'numberfield',
									value :projectMoney
								}]
						}, {
							columnWidth : .11,
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
									name : 'projectMoneyl',
									xtype : 'numberfield',
									value :projectMoneyl
								}]
						}, {
							columnWidth : .15,
							labelAlign : 'right',
							xtype : 'container',
							layout : 'form',
							labelWidth : leftlabelwidth,
							defaults : {
								anchor : anchor
							},
							items : [{
									fieldLabel:'贷款开始时间',
									name : 'startDateg',
									xtype : 'datefield',
									format : 'Y-m-d',
									value :startDateg
									
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
									name : 'startDatel',
									xtype : 'datefield',
									labelSeparator:"",
									format : 'Y-m-d',
									value :startDatel
								}]
						}, {
							columnWidth : .25,
							labelAlign : 'right',
							xtype : 'container',
							layout : 'form',
							labelWidth : leftlabelwidth,
							defaults : {
								anchor : anchor
							},
							items : [{
									name : 'appUserIdOfA',
									xtype:'trigger',
									fieldLabel : '项目经理',
									submitValue : true,
									triggerClass :'x-form-search-trigger',
									editable : false,
									scope : this,
									onTriggerClick : function(){
									    var obj = this;
										UserSelector.getView(function(id, name) {
											obj.setValue(name);
							//				obj.ownerCt.ownerCt.getCmpByName('appUserIdOfA').setValue(id);
										}, true).show();//false为多选,true为单选。
									},
									value :businessManager
								}]
						}
                     , {
							columnWidth : .23,
							labelAlign : 'right',
							xtype : 'container',
							layout : 'form',
							labelWidth : leftlabelwidth,
							defaults : {
								anchor : anchor
							},
							items : [{
									fieldLabel:'年化利率>=',
									name : 'annualNetProfitg',
									fieldClass:'field-align',
									xtype : 'numberfield'
								}]
						}, {
							columnWidth : .02,
							labelAlign : 'left',
							xtype : 'container',
							layout : 'form',
							labelWidth : 26.1,
							defaults : {
								anchor : anchor
							},
							items : [{
									fieldLabel : "%",
									labelSeparator : '',
									anchor : "100%"
								}]
						}
						, {
							columnWidth : .25,
							labelAlign : 'right',
							xtype : 'container',
							layout : 'form',
							labelWidth : leftlabelwidth,
							defaults : {
								anchor : anchor
							},
							items : [{
									xtype : 'combo',
									mode : 'local',
									valueField : 'value',
									editable : false,
									displayField : 'value',
									store : new Ext.data.SimpleStore({
												fields : ["value"],
												data : [["办理中贷款"],
														["放款后贷款"],
														["展期中贷款"],
														["已完成贷款"],
														["提前终止贷款"]	,
														["违约贷款"],
														["全部贷款业务"]
														
														]
											}),
									triggerAction : "all",
									name : 'projectStatus',
									fieldLabel : '项目状态',
									value :projectStatus
									
								}]
						}, {
							columnWidth : .1,
							labelAlign : 'right',
							xtype : 'container',
							layout : 'form',
							labelWidth : 30,
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
													var temp = Ext.getCmp('reportTemp' + reportKey);
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
				title : '查看-贷款信息明细表',
				iconCls : 'menu-report',
				autoScroll : false,
				autoHeight : true,
				border : false,
				frame : false,
				listeners : {
					'afterrender' : function(panel) {
						//高度布局
		//				var f_h = formPanel.getHeight();
						var t_h = toolbar.getHeight();
						var i_h = Ext.getCmp('reportTemp' + reportKey)
								.getHeight();
						var a_h = Ext.getCmp('centerTabPanel').getInnerHeight();
						
						Ext.getCmp('reportTemp' + reportKey).setHeight(a_h
								- (90 + t_h));
						
						Ext.getCmp('reportTemp' + reportKey).doLayout();
						//自动加载
				//		searchButton.fireEvent('click');
						searchPanel.getForm().submit({
												waitMsg : '正在提交查询',
												url : __ctxPath + '/system/submit1ReportTemplate.do?reportKey='+reportKey+'&reportId='+reportId+'&isinti=1',
												method : 'post',
												success : function(form, action) {
					
													var object = Ext.util.JSON
															.decode(action.response.responseText)
													var temp = Ext.getCmp('reportTemp' + reportKey);
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
					id : 'reportTemp' + reportKey,
					// tbar : toolbar,
					autoHeight : false,
					autoWidth : false,
					autoScroll : false,
					border : false,
					xtype : 'panel',
					height : 478,
					autoLoad:false
				}

				]
			});

	return panel;
}