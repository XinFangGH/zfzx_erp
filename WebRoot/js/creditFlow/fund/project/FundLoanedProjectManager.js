/**
 * @author lisl
 * @extends Ext.Panel
 * @description 小贷项目管理
 * @company 智维软件
 * @createtime:
 */
FundLoanedProjectManager = Ext.extend(Ext.Panel, {
	// 构造函数
	constructor : function(_cfg) {
		if (typeof(_cfg.managerType) != "undefined") {
			this.managerType = parseInt(_cfg.managerType);
		}
		this.isGrantedShowAllProjects = isGranted('_seeAllPro_p1');// 是否授权显示所有项目记录
		switch (this.managerType) {
			case 1 :
				this.titlePrefix = "贷后监管";
				this.tabIconCls = "btn-tree-team2";
				break;
			case 13 :
				this.titlePrefix = "利率变更";
				this.tabIconCls = "btn-tree-team2";
				break;
			case 2 :
				this.titlePrefix = "贷款展期";
				this.tabIconCls = "btn-tree-team2";
				break;
			case 3 :
				this.titlePrefix = "提前还款";
				this.isCapitalUnexpired = true;
				this.tabIconCls = "btn-tree-team2";
				break;
			case 4 :
				this.titlePrefix = "违约处理";
				this.tabIconCls = "btn-tree-team2";
				break;
			case 5 :
				this.titlePrefix = "贷款结项";
				this.tabIconCls = "btn-tree-team2";
				break;
			
		}
		Ext.applyIf(this, _cfg);
		// 初始化组件
		this.initUIComponents();

		// 调用父类构造
		FundLoanedProjectManager.superclass.constructor.call(this, {
			id : 'FundLoanedProjectManager' + this.managerType,
			title : this.titlePrefix,
			region : 'center',
			layout : 'border',
			iconCls : this.tabIconCls,
			items : [{
				xtype : 'label',
				text : '对不起，您能查看的项目数为0。如有错误，请与系统管理员联系，看您的上下级关系是否设置正确!(查询授权规律为：您可以查看自己或您下属的项目)',
				hidden : true
			}, this.searchPanel, this.gridPanel]
		});
	},// end of constructor
	// 初始化组件
	initUIComponents : function() {
		var anchor = '100%';
		var startDate = {
			columnWidth : .14,
			labelAlign : 'right',
			xtype : 'container',
			layout : 'form',
			labelWidth : 60,
			defaults : {
				anchor : anchor
			},
			items : [{
				fieldLabel : this.dateLabel,
				name : this.startDateName,
				xtype : 'datefield',
				format : 'Y-m-d'

			}]
		};
		var endDate = {
			columnWidth : .112,
			labelAlign : 'right',
			xtype : 'container',
			layout : 'form',
			labelWidth : 25,
			defaults : {
				anchor : anchor
			},
			items : [{
				fieldLabel : '到',
				name : this.endDateName,
				xtype : 'datefield',
				labelSeparator : "",
				format : 'Y-m-d'
			}]
		};
		this.isHiddenBranch = true;
		if (RoleType == "control") {// 此用户角色为管控角色
			this.isHiddenBranch = false;
		}
		this.searchPanel = new Ext.FormPanel({
			layout : 'column',
			region : 'north',
			border : false,
			height : 70,
			anchor : '96%',
			layoutConfig: {
               align:'middle'
            },
             bodyStyle : 'padding:10px 10px 10px 10px',
            items : [{
					name : 'projectStatus',
					xtype : 'hidden',
					value : 1
				}, {
					name : 'isAll',
					xtype : 'hidden',
					value : this.isGrantedShowAllProjects
				},{
					columnWidth : 1,
					layout : 'column',
					border : false,
					items : [{
		     		columnWidth :.25,
					layout : 'form',
					labelWidth : 80,
					labelAlign : 'right',
					border : false,
					items : [{
						fieldLabel : '项目编号',
						name : 'projectNumber',
						anchor : "100%",
						xtype : 'textfield'
					}]
		     	},{
		     		columnWidth :.25,
					layout : 'form',
					labelWidth : 80,
					labelAlign : 'right',
					border : false,
					items :[{
						fieldLabel : '项目名称',
						name : 'projectName',
						anchor : "100%",
						xtype : 'textfield'
					}]
		     	},{
		     		columnWidth :.25,
					layout : 'form',
					labelWidth : 80,
					labelAlign : 'right',
					border : false,
					items:[{
						fieldLabel : "客户类别",
						xtype : "dicIndepCombo",
						displayField : 'itemName',
						nodeKey : 'customerType',
						editable : false,
						anchor : "100%",
						hiddenName : 'oppositeType'
						
					}]
	     		},{
	     			columnWidth :.07,
					layout : 'form',
					border : false,
					labelWidth :80,
					items :[{
						text : '查询',
						xtype : 'button',
						scope : this,
						style :'margin-left:30px',
						anchor : "90%",
						iconCls : 'btn-search',
						handler : this.search
					}]
	     		}]
				},{
	     			columnWidth :.14,
					layout : 'form',
					labelWidth : 80,
					labelAlign : 'right',
					border : false,
					items :[{
						fieldLabel : '金额范围',
						name : 'Q_projectMoney_BD_GE',
						anchor : "100%",
						xtype : 'numberfield'
					}]
	     		},{
	     			columnWidth :.11,
					layout : 'form',
					labelWidth : 20,
					labelAlign : 'right',
					border : false,
					items :[{
						fieldLabel : '到',
						labelSeparator : "",
						name : 'Q_projectMoney_BD_LE',
						anchor : "100%",
						xtype : 'numberfield'
					}]
	     		},{
	     			columnWidth :.25,
					layout : 'form',
					labelWidth : 80,
					labelAlign : 'right',
					border : false,
					items :[{
						name : 'businessManager',
						xtype : 'trigger',
						fieldLabel : '项目经理',
						submitValue : true,
						triggerClass : 'x-form-search-trigger',
						editable : false,
						scope : this,
						anchor : "100%",
						onTriggerClick : function() {
							var obj = this;
							var appuerIdObj = obj.nextSibling();
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
								}
							}).show();

						}
					}, {
						xtype : "hidden",
						name : 'Q_businessManager_S_LK'
					}]
	     		},{
					columnWidth :.25,
					layout : 'form',
					labelWidth : 80,
					labelAlign : 'right',
					border : false,
					items : [{
						fieldLabel : '所属门店',
						xtype : "combo",
						anchor : "100%",
						hiddenName : "orgUserId",
						displayField : 'orgUserName',
						name :"orgUserId",
						valueField : 'orgUserId',
						triggerAction : 'all',
						store : new Ext.data.SimpleStore({
							autoLoad : true,
							url : __ctxPath
									+ '/system/getOrgUserNameOrganization.do',
							fields : ['orgUserId', 'orgUserName']
						})
					}]
				}, this.isHiddenBranch == false ?{
					columnWidth :.18,
					layout : 'form',
					labelWidth : 80,
					labelAlign : 'right',
					border : false,
					items : [{
						fieldLabel : '所属分公司',
						xtype : "combo",
						anchor : "100%",
						hiddenName : "companyId",
						displayField : 'companyName',
						name :"companyId",
						valueField : 'companyId',
						triggerAction : 'all',
						store : new Ext.data.SimpleStore({
							autoLoad : true,
							url : __ctxPath
									+ '/system/getControlNameOrganization.do',
							fields : ['companyId', 'companyName']
						})
					}]
				}:{ 
					columnWidth:0.001,
			     	border:false
		     	},{
	     			columnWidth :this.isHiddenBranch ?.07:.25,
					layout : 'form',
					border : false,
					items :[{
						text : '重置',
						style :this.isHiddenBranch ?'margin-left:30px':'margin-left:245px',
						xtype : 'button',
						scope : this,
						width : 30,
						iconCls : 'btn-reset',
						handler : this.reset
					}]
	     		}]
		});
		if (this.managerType == 3) {
			this.topbar = new Ext.Toolbar({
				items : [{
					iconCls : 'menu-flowWait',
					text : '提前还款办理',
					xtype : 'button',
					scope : this,
					hidden : isGranted('handleFundAdvanceRepayment') ? false : true,
					handler : function() {
						var gridPanel = this.gridPanel;
						var s = this.gridPanel.getSelectionModel()
								.getSelections();
						if (s <= 0) {
							Ext.ux.Toast.msg('操作信息', '请选中任何一条记录');
							return false;
						} else if (s.length > 1) {
							Ext.ux.Toast.msg('操作信息', '只能选中一条记录');
							return false;
						} else {
							var record = s[0];
							var isOtherFlow =record.data.isOtherFlow;
							if(isOtherFlow==1){//isOtherFlow==1表示当前项目已经启动了展期流程
								Ext.ux.Toast.msg('操作信息', '正在进行展期流程，不能启动新的提前还款流程！');
								return false;
							}else if(isOtherFlow==2){//isOtherFlow==1表示当前项目已经启动了提前还款流程
								Ext.ux.Toast.msg('操作信息', '正在进行提前还款流程，不能启动新的提前还款流程！');
								return false;
							}else if(isOtherFlow==3){//isOtherFlow==1表示当前项目已经启动了利率变更流程
								Ext.ux.Toast.msg('操作信息', '正在进行利率变更流程，不能启动新的提前还款流程！');
								return false;
							}else{//isOtherFlow==0表示当前没有办理任何贷后流程

								var gridPanel=this.gridPanel
 								Ext.Msg.confirm('信息确认', '是否启动提前还款流程', function(btn) {
					       		 if (btn == 'yes') {
									Ext.Ajax.request( {
										url : __ctxPath +"/smallLoan/finance/startSlEarlyRepaymentProcessSlEarlyRepaymentRecord.do",
										params : {projectId:record.data.id,businessType:record.data.businessType},
										method : 'POST',
										success : function(response) {
													gridPanel.getStore().reload()
													var obj=Ext.util.JSON.decode(response.responseText)
													var contentPanel = App.getContentPanel();
													if(obj.taskId==1){
														Ext.ux.Toast.msg('操作信息','您不是提前还款流程中任务<提前还款申请>的处理人!');
														return;
													}else{
														var formView = contentPanel.getItem('ProcessNextForm' + obj.taskId);
														if (formView == null) {
															formView = new ProcessNextForm({
																taskId : obj.taskId,
																activityName : obj.activityName,
																projectName : obj.projectName,
																agentTask : true
															});
															contentPanel.add(formView);
														}
														contentPanel.activate(formView);
													}
													gridPanel.getStore().reload()
												},
									   failure : function(fp, action) {
												Ext.MessageBox.show({
													title : '操作信息',
													msg : '信息保存出错，请联系管理员！',
													buttons : Ext.MessageBox.OK,
													icon : 'ext-mb-error'
												});
										}
									});
					       		 }
 								})
							}
							
							
							}
						

					}
				}, new Ext.Toolbar.Separator({
					hidden : isGranted('handleAdvanceRepayment') ? false : true
				}), {

					iconCls : 'btn-detail',
					text : '贷款详细',
					xtype : 'button',
					scope : this,
					handler : function() {
						detailPro(this.gridPanel, 'SmallLoanProjectInfo_')
					}
				}]
			})
		}else if (this.managerType == 2) {
			this.topbar = new Ext.Toolbar({
				items : [{
					iconCls : 'menu-flowWaita',
					text : '展期办理',
					xtype : 'button',
					scope : this,
					hidden : isGranted('handleExtension') ? false : true,
					handler : function() {
						var gridPanel = this.gridPanel;
						var s = this.gridPanel.getSelectionModel()
								.getSelections();
						if (s <= 0) {
							Ext.ux.Toast.msg('操作信息', '请选中任何一条记录');
							return false;
						} else if (s.length > 1) {
							Ext.ux.Toast.msg('操作信息', '只能选中一条记录');
							return false;
						} else {
							var record = s[0];
							var isOtherFlow =record.data.isOtherFlow;
							if(isOtherFlow==1){//isOtherFlow==1表示当前项目已经启动了展期流程
								Ext.ux.Toast.msg('操作信息', '正在进行展期流程，不能启动新的展期流程！');
								return false;
							}else if(isOtherFlow==2){//isOtherFlow==1表示当前项目已经启动了提前还款流程
								Ext.ux.Toast.msg('操作信息', '正在进行提前还款流程，不能启动新的展期流程！');
								return false;
							}else if(isOtherFlow==3){//isOtherFlow==1表示当前项目已经启动了利率变更流程
								Ext.ux.Toast.msg('操作信息', '正在进行利率变更流程，不能启动新的展期流程！');
								return false;
							}else{//isOtherFlow==0表示当前没有办理任何贷后流程

								var gridPanel=this.gridPanel
								Ext.Msg.confirm('信息确认', '是否启动展期办理流程', function(btn) {
					       		 if (btn == 'yes') {
								Ext.Ajax.request( {
									url : __ctxPath +"/supervise/startRenewalProcessSlSuperviseRecord.do",
									params : {projectId:record.data.id,businessType:record.data.businessType},//◎
									method : 'POST',
									success : function(response) {
												gridPanel.getStore().reload()
												var obj=Ext.util.JSON.decode(response.responseText)
												var contentPanel = App.getContentPanel();
												if(obj.taskId==1){
													Ext.ux.Toast.msg('操作信息','您不是展期流程中任务<展期申请>的处理人!');
													return;
												}else{
													var formView = contentPanel.getItem('ProcessNextForm' + obj.taskId);
													if (formView == null) {
														formView = new ProcessNextForm({
																	taskId : obj.taskId,
																	activityName : obj.activityName,
																	projectName : obj.projectName,
																	agentTask : true
																});
														contentPanel.add(formView);
													}
													contentPanel.activate(formView);
												}
												gridPanel.getStore().reload()
											},
								   failure : function(fp, action) {
											Ext.MessageBox.show({
												title : '操作信息',
												msg : '信息保存出错，请联系管理员！',
												buttons : Ext.MessageBox.OK,
												icon : 'ext-mb-error'
											});
									}
								});
					       		 }
								})
							}
							
							
							}
						

					}
				}, new Ext.Toolbar.Separator({
					hidden : isGranted('handleExtension') ? false : true
				}), {

					iconCls : 'btn-detail',
					text : '贷款详细',
					xtype : 'button',
					scope : this,
					handler : function() {
						detailPro(this.gridPanel, 'SmallLoanProjectInfo_')
					}
				}]
			})
		}else if (this.managerType == 13) {
			this.topbar = new Ext.Toolbar({
				items : [{
					iconCls : 'menu-flowWait0',
					text : '启动利率变更流程',
					xtype : 'button',
					scope : this,
					hidden : isGranted('InterestRateChanges') ? false : true,
					handler : function() {
						var gridPanel = this.gridPanel;
						var s = this.gridPanel.getSelectionModel()
								.getSelections();
						if (s <= 0) {
							Ext.ux.Toast.msg('操作信息', '请选中任何一条记录');
							return false;
						} else if (s.length > 1) {
							Ext.ux.Toast.msg('操作信息', '只能选中一条记录');
							return false;
						} else {
							var record = s[0];
							var isOtherFlow =record.data.isOtherFlow;
							if(isOtherFlow==1){//isOtherFlow==1表示当前项目已经启动了展期流程
								Ext.ux.Toast.msg('操作信息', '正在进行展期流程，不能启动新的利率变更流程！');
								return false;
							}else if(isOtherFlow==2){//isOtherFlow==1表示当前项目已经启动了提前还款流程
								Ext.ux.Toast.msg('操作信息', '正在进行提前还款流程，不能启动新的利率变更流程！');
								return false;
							}else if(isOtherFlow==3){//isOtherFlow==1表示当前项目已经启动了利率变更流程
								Ext.ux.Toast.msg('操作信息', '正在进行利率变更流程，不能启动新的利率变更流程！');
								return false;
							}else{//isOtherFlow==0表示当前没有办理任何贷后流程

								var gridPanel=this.gridPanel
								Ext.Msg.confirm('信息确认', '是否启动利率变更流程', function(btn) {
					       		 if (btn == 'yes') {
								Ext.Ajax.request( {
									url : __ctxPath +"/smallLoan/finance/startSlAlteraccrualProcessSlAlterAccrualRecord.do",
									params : {projectId:record.data.id},
									method : 'POST',
									success : function(response) {
												gridPanel.getStore().reload()
												var obj=Ext.util.JSON.decode(response.responseText)
												var contentPanel = App.getContentPanel();
												if(obj.taskId==1){
													Ext.ux.Toast.msg('操作信息','您不是利率变更流程中任务<利率变更申请>的处理人!');
													return;
												}else{
													var formView = contentPanel.getItem('ProcessNextForm' + obj.taskId);
													if (formView == null) {
														formView = new ProcessNextForm({
															taskId : obj.taskId,
															activityName : obj.activityName,
															projectName : obj.projectName,
															agentTask : true
														});
														contentPanel.add(formView);
													}
													contentPanel.activate(formView);
												}
												gridPanel.getStore().reload()
											},
								   failure : function(fp, action) {
											Ext.MessageBox.show({
												title : '操作信息',
												msg : '信息保存出错，请联系管理员！',
												buttons : Ext.MessageBox.OK,
												icon : 'ext-mb-error'
											});
									}
								});
					       		 }
								})
							}
							
							
							}
						

					}
				}, new Ext.Toolbar.Separator({
					hidden : isGranted('InterestRateChanges') ? false : true
				}), {

					iconCls : 'btn-detail',
					text : '贷款详细',
					xtype : 'button',
					scope : this,
					handler : function() {
						detailPro(this.gridPanel, 'SmallLoanProjectInfo_')
					}
				}]
			})
		}else if (this.managerType == 1) {
			this.topbar = new Ext.Toolbar({
				items : [{
					iconCls : 'btn-add',
					text : '监管计划',
					xtype : 'button',
					scope : this,
					hidden : isGranted('addSuperviseRecord') ? false : true,
					handler : function() {
						var selRs = this.gridPanel.getSelectionModel()
								.getSelections();
						if (selRs.length == 0) {
							Ext.ux.Toast.msg('操作信息', '请选择一条记录！');
							return;
						}
						if (selRs.length > 1) {
							Ext.ux.Toast.msg('操作信息', '只能选择一条记录！');
							return;
						}
						var record = this.gridPanel.getSelectionModel()
								.getSelected();

						var projectId = record.data.id;

						new DesignSupervisionManagePlan({
							projectId : projectId,
							sprojectId :record.data.projectId,
							businessType:"SmallLoan"
							
							
						}).setVisible(true);
					}
				}, new Ext.Toolbar.Separator({
					hidden : isGranted('addSuperviseRecord') ? false : true
				}), {

					iconCls : 'btn-detail',
					text : '贷款详细',
					xtype : 'button',
					scope : this,
					handler : function() {
						detailPro(this.gridPanel, 'SmallLoanProjectInfo_')
					}
				}]
			})
		}else if (this.managerType == 5) {
			this.topbar = new Ext.Toolbar({
				items : [{
					iconCls : 'menu-flowWaitb',
					text : '办理结项',
					xtype : 'button',
					scope : this,
					hidden : isGranted('smallLoanProjectFinished') ? false : true,
					handler : function() {
						var s = this.gridPanel.getSelectionModel().getSelections();
						if (s <= 0) {
							Ext.ux.Toast.msg('操作信息', '请选中任何一条记录');
							return false;
						} else if (s.length > 1) {
							Ext.ux.Toast.msg('操作信息', '只能选中一条记录');
							return false;
						} else {
							var record = s[0];
							var tabs = Ext.getCmp('centerTabPanel');
							var gpObj = document.getElementById('SlProjectFinished_'	+ record.data.projectId);
							if (gpObj == null) {
								gpObj = new SlProjectFinished({
									record : record,
									projectGridPanel : this.gridPanel
								});
								tabs.add(gpObj);
							}
							tabs.setActiveTab("SlProjectFinished_"+ record.data.id);
						}
					}
				}, new Ext.Toolbar.Separator({
					hidden : isGranted('') ? false : true
				}), {

					iconCls : 'btn-detail',
					text : '贷款详细',
					xtype : 'button',
					scope : this,
					handler : function() {
						detailPro(this.gridPanel, 'SmallLoanProjectInfo_')
					}
				}]
			});		
		}else if (this.managerType == 4) {
			this.topbar = new Ext.Toolbar({
				items : [{
					iconCls : 'btn-tree-illegal',
					text : '违约处理',
					xtype : 'button',
					scope : this,
					hidden : isGranted('startBreach') ? false : true,
					handler : function() {
						var selRs = this.gridPanel.getSelectionModel()
								.getSelections();
						var gridPanel = this.gridPanel;
						if (selRs.length == 0) {
							Ext.ux.Toast.msg('操作信息', '请选择一条记录！');
							return;
						}
						if (selRs.length > 1) {
							Ext.ux.Toast.msg('操作信息', '只能选择一条记录！');
							return;
						}
						var record = selRs[0];
						var projectName = record.data.projectName;
						var projectId = record.data.projectId;
						var businessType = record.data.businessType;
						var window_add = new Ext.Window({
							title : '【' + projectName + '】违约处理',
							height : 428,
							autoScroll : true,
							frame : true,
							buttonAlign : 'center',
							width : 1125,
							maximizable : true,
							modal : true,
							items : [new Ext.form.FormPanel({
								frame : true,
								border : false,
								url : 'dispatch',
								plain : true,
								labelWidth : 120,
								bodyStyle : "padding:5px 5px 0",
								monitorValid : true,
								defaults : {
									xtype : 'textfield',
									width : 200
								},
								items : [new Ext.form.RadioGroup({
									fieldLabel : "项目是否被终止",
									items : [{
										boxLabel : '项目终止',
										inputValue : "1",
										name : "rg",
										checked : true
									}]
								}), {
									xtype : 'hidden',
									name : 'projectId_',
									value : projectId
								}, {
									xtype : 'hidden',
									name : 'bussinessType_',
									value : businessType
								}, {
									fieldLabel : "是否将此客户",
									name : "fcbm_ip",
									xtype : 'checkboxgroup',
									ctCls : "data_tab_tdr2",
									items : [{
										boxLabel : '加入黑名单',
										name : 'cb-col-1'
									}]
								} ,{
									xtype : 'panel',
									border : false,
									anchor : "100%",
									bodyStyle : 'margin-bottom:5px',
									html : '<B><font class="x-myZW-fieldset-title">'
											+ '【放款收息信息处理】</font></B><font class="x-myZW-fieldset-title">（</font>颜色预警：<font color=red>逾期款项</font>&nbsp;&nbsp<font style="line-height:20px">未结清项</font>&nbsp;&nbsp<font color=gray>已结清项</font><font class="x-myZW-fieldset-title" >）：</font>'
								} , new SlFundIntentViewVM({
									projectId : projectId,
									isHidden1 : true,
									businessType : 'SmallLoan',
									preceptId:record.data.id,
									isHiddenCanBtn : false,
									isHiddenResBtn : false,
									enableEdit : true,
									isFinbtn:true,
									isHiddenautocreateBtn:true,
									isHiddenResBtn1:true,
									isHiddenTitle : true
								}),new DZYMortgageView({
										projectId : projectId,
										titleText : '抵质押物处理',
										businessType : 'SmallLoan',
										isHiddenAddBtn : true,
										isHiddenDelBtn : true,
										isHiddenEdiBtn : true,
										isHiddenAddContractBtn:true,
										isHiddenDelContractBtn:true,
										isHiddenEdiContractBtn:true,
										isHiddenRelieve:true,
										isblHidden:true,
										isgdHidden:true,
										isSeeContractHidden : false,
										isHandleHidden : false,
										isHandleEdit : true
									}) 
								, {
									xtype : 'panel',
									border : false,
									anchor : "100%",
									bodyStyle : 'margin-bottom:5px;margin-top:15px',
									html : '<B><font class="x-myZW-fieldset-title">'
											+ '【违约处理说明文档】</font></B>'
								} , new uploads({
									anchor : "100%",
									title_c : '上传违约处理说明文档',
									businessType : businessType,
									typeisfile : 'breachSmallloan_'+record.data.id,
									projectId : projectId,
									uploadsSize : 15
								}), {
									xtype : "textarea",
									fieldLabel : "违约说明",
									name : "comments",
									style : 'margin-top:10px;',
									anchor : "100%"
								}]
								
							})],
							buttons : [{
									text : "确定",
									iconCls : "btn-ok",
									handler : function() {
										var myCheckboxGroup = window_add.get(0)
												.getCmpByName('fcbm_ip');
										var comments = window_add.get(0)
												.getCmpByName('comments');
										var cbitems = myCheckboxGroup.items;
										var isCheck = cbitems.itemAt(0).checked;
										var listed = 0;
										if (isCheck) {
											listed = 1;
										}
										Ext.Ajax.request({
											url : __ctxPath
													+ '/project/startBreachSlSmallloanProject.do',
											method : 'post',
											params : {
												"projectID_" : projectId,
												fundProjectId : record.data.id,
												"listed_" : listed,
												"comments" : comments
														.getValue(),
												"businessType_" : businessType
											},
											success : function(response,options) {
												Ext.ux.Toast.msg('操作信息','违约处理成功！');
												window_add.destroy();
												gridPanel.getStore().reload();
											}
										});
									},
									scope : this,
									formBind : true
								}, {
									text : "取消",
									iconCls : "btn-cancel",
									scope : this,
									handler : function() {
										this.reset;
										window_add.destroy();
									}
								}],
							listeners : {
								'beforeclose' : function(panel) {
									window_add.destroy();
								}
							}
						});
						window_add.show();
					}
				}, new Ext.Toolbar.Separator({
					hidden : isGranted('startBreach') ? false : true
				}), {
					iconCls : 'btn-detail',
					text : '贷款详细',
					xtype : 'button',
					scope : this,
					handler : function() {
						detailPro(this.gridPanel, 'SmallLoanProjectInfo_')
					}
				}]
			});
		}
		var summary = new Ext.ux.grid.GridSummary();
				function totalMoney(v, params, data) {
					return '总计(元)';
				}
		this.gridPanel = new HT.GridPanel({
			name : 'SmallProjectGrid',
			region : 'center',
			tbar : this.topbar,
			plugins : [summary],
			notmask : true,
			// 不适用RowActions
			rowActions : false,
			url : __ctxPath + "/fund/projectListBpFundProject.do",
			baseParams : {
				'projectStatus' : 1,
				'managerType':this.managerType,
				'isAll' : this.isGrantedShowAllProjects
			},
			fields : [{
				name : 'id',
				type : 'long'
			}, 'projectId','orgName', 'subject', 'creator', 'userId', 'projectName',
					'projectNumber', 'defId', 'runStatus', 'projectMoney',
					'payProjectMoney', 'oppositeType', 'oppositeTypeValue',
					'customerName', 'projectStatus', 'operationType',
					'operationTypeValue', 'taskId', 'activityName',
					'oppositeId', 'businessType', 'startDate', 'endDate',
					'superviseOpinionName', 'supervisionPersonName',
					'processName', 'appUserName', 'breachComment',
					'accrualtype', 'expectedRepaymentDate', 'payintentPeriod',
					'accrual','managementConsultingOfRate','executor','processName','intentDate','supEndDate','businessType',
					'departMentName','departId','ownJointMoney','productId','runId','isOtherFlow'],

			columns : [{
				header : '所属分公司',
				align:'center',
				hidden : this.isHiddenBranch?true : false,
				width : 100,
				dataIndex : 'orgName'
			},{
				header : '所属门店',
				width : 160,
				align:'center',
				dataIndex : 'departMentName'
			}, {
				header : '项目编号',
				width : 130,
				dataIndex : 'projectNumber'
			}, {
				header : '项目名称',
				summaryRenderer : totalMoney,
				width : this.isHiddenBranch?360 : 310,
				dataIndex : 'projectName'
			}, {
				header : '客户类别',
				align:'center',	
				width : 68,
				dataIndex : 'oppositeTypeValue'
			},{
				header : '业务状态',
				align:'center',
				width : 70,
				sortable : true,
				dataIndex : 'isOtherFlow',
				renderer : function(value) {
					if (value == "") {
						return "";
					} else if(value ==1) {
						return "展期中"
					}else if(value ==2) {
						return "提前还款中"
					}
					else if(value ==3) {
						return "利率变更中"
					}
				}
			},{
				header : '贷款金额(元 )',
				align : 'right',
				width : 110,
				summaryType : 'sum',
				sortable : true,
				dataIndex : 'ownJointMoney',
				renderer : function(value) {
					if (value == "") {
						return "0.00";
					} else {
						return Ext.util.Format.number(value, ',000,000,000.00');
					}
				}
			}, {
				header : '项目经理',
				width : 70,
				align:'center',
				dataIndex : 'appUserName'
			}/*,{
				header : '项目阶段',
				width : 80,
				hidden : (this.projectStatus==0||this.projectStatus==3)?false:true,
				//hidden : this.isHiddenAn,
				dataIndex : 'activityName'
			}, {
				header : '贷款状态',
				width : 80,
				hidden : this.isHiddenPs,
				dataIndex : 'projectStatus',
				renderer : function(v) {
					switch (v) {
						case 0 :
							return "办理中项目";
							break;
						case 1 :
							return "还款中项目";
							break;
						case 2 :
							return "结项项目";
							break;
						case 3 :
							return "提前终止项目";
							break;
						case 7 :
							return "全部贷款业务";
							break;
						case 8 :
							return "违约处理项目";
							break;
						case 5 :
							return "展期中项目";
							break;
					}
				}
			}, {
				header : '执行人',
				width : 70,
				hidden : (this.projectStatus==0||this.projectStatus==3)?false:true,
				dataIndex : 'executor'
			}, {
				header : '监管人',
				width : 98,
				hidden : this.isHiddenSuperviseIn,
				dataIndex : 'supervisionPersonName'
			}, {
				header : '监管意见',
				width : 118,
				hidden : this.isHiddenSuperviseIn,
				dataIndex : 'superviseOpinionName'
			}, {
				header : '违约说明',
				width : 118,
				hidden : this.isHiddenBreachComment,
				dataIndex : 'breachComment'
			}, {
				header : '项目开始日期',
				//width : 76,
				//hidden : this.isHiddenBd,
				dataIndex : 'startDate',
				sortable : true
			}, {
				header : '项目到期日期',
				//width : 76,
				//hidden : this.isHiddenCd,
				dataIndex : 'intentDate',
				sortable : true,
				renderer : function(data, metadata, record, rowIndex, columnIndex, store) {
	               if(null!=record.get('supEndDate') && record.get('supEndDate')!=''){
	               		data=record.get('supEndDate');
	               }
	                return data;
	            }
			}, {
				header : '终止时间',
				width : 76,
				hidden : this.isHiddenSd,
				dataIndex : 'endDate',
				sortable : true
			}*/]
		});
		this.gridPanel.addListener('afterrender', function() {
			this.loadMask1 = new Ext.LoadMask(this.gridPanel.getEl(), {
				msg : '正在加载数据中······,请稍候······',
				store : this.gridPanel.store,
				removeMask : true
					// 完成后移除
					});
			this.loadMask1.show(); // 显示

		}, this);
		/*if (!this.isGrantedShowAllProjects) {
			this.gridPanel.getStore().on("load", function(store) {
				if (store.getCount() < 1) {
					this.get(0).setVisible(true);
					this.searchPanel.setVisible(false);
					this.gridPanel.setVisible(false);
					this.doLayout();
				}
			}, this)
		}*/
	},// end of the initComponents()
	
		// 查看意见与说明记录 
	flowRecords : function() {
		var selRs = this.gridPanel.getSelectionModel().getSelections();
		if (selRs.length == 0) {
			Ext.ux.Toast.msg('操作信息', '请选择记录！');
			return;
		}
		if (selRs.length > 1) {
			Ext.ux.Toast.msg('操作信息', '只能选择一条记录！');
			return;
		}
		var runId =selRs[0].get('runId');
		var businessType=selRs[0].get('businessType');
		new SlProcessRunView({
						runId : runId,
						businessType : businessType
					}).show();
	},
	// 重置查询表单
	reset : function() {
		this.searchPanel.getForm().reset();
		/*this.searchPanel.getCmpByName('projectStatus').setValue(this.projectStatus);
		this.searchPanel.getCmpByName('isGrantedShowAllProjects').setValue(this.isGrantedShowAllProjects);*/
	},
	// 按条件搜索
	search : function() {
		$search({
			searchPanel : this.searchPanel,
			gridPanel : this.gridPanel
		});
	}
});
