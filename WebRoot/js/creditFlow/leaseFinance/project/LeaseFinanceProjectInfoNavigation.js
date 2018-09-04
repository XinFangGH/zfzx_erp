/**
 * @author lisl
 * @class ProjectInfoNavigation
 * @description 担保项目信息页面导航
 * @extends Ext.Panel
 */
LeaseFinanceProjectInfoNavigation = Ext.extend(Ext.Panel, {
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		this.initUIComponents();
		LeaseFinanceProjectInfoNavigation.superclass.constructor.call(this, {
			region : 'center',
			layout : 'anchor',
			items : []
		});
	},
	initUIComponents : function() {
		var jsArr = [
				__ctxPath + '/js/creditFlow/guarantee/enterpriseBusiness/enterpriseBusinessUI.js',// 项目基本信息
				__ctxPath + '/js/creditFlow/guarantee/meeting/SbhProjectInfo.js',// 线上审保会打分情况
				__ctxPath + '/js/creditFlow/smallLoan/project/SlProcessRunView.js',// 意见与说明记录
				__ctxPath + '/js/creditFlow/smallLoan/project/SlFilingRecordView.js',// 归档记录
				__ctxPath + '/js/creditFlow/common/ProjectTaskChangeAssign.js',// 项目换人
				__ctxPath + '/js/creditFlow/common/ProjectTaskHandler.js',// 任务换人
				__ctxPath + '/js/creditFlow/common/ProjectPathChange.js',// 流程跳转
				__ctxPath + '/js/creditFlow/guarantee/project/Compensatory.js',//代偿
				__ctxPath + '/js/creditFlow/leaseFinance/project/flPostponedProjectInfoPanel.js',//展期记录			
				__ctxPath + '/js/creditFlow/leaseFinance/project/flPostponedProjectInfoEdit.js',
				__ctxPath + '/js/creditFlow/leaseFinance/project/flPostponedProjectInfoEdit.js',
				__ctxPath + '/js/creditFlow/leaseFinance/project/flPostponedProjectInfoEditPanel.js',
				__ctxPath + '/js/creditFlow/leaseFinance/project/flPostponedProjectInfoNavigation.js',//展期编辑
				__ctxPath + '/js/creditFlow/guarantee/project/CompensatoryRecords.js',//追偿
				__ctxPath + '/js/creditFlow/guarantee/project/Recovery.js',
				__ctxPath + '/js/creditFlow/guarantee/project/RecoveryRecords.js'
		];
		$ImportSimpleJs(jsArr, this.constructPanel, this);
	},// 初始化组件
	constructPanel : function() {
		this.projectId = this.record.data.projectId;
		this.projectStatus = this.record.data.projectStatus;
		this.bmStatus = this.record.data.bmStatus;
		this.runId = this.record.data.runId;
		this.taskId = this.record.data.taskId;
		this.flowType = this.record.data.flowType;
		this.activityName = this.record.data.activityName;
		this.index = 18;
		this.isHidden = true;
		if (this.flowType == 'zmNormalFlow') {
			this.isHidden = false;
			this.index = 20;
		}
		this.panel = new Ext.Panel({
			border : false,
			layout : 'column',
			defaults : {
				anchor : '100%'
			},
			items : [{
				columnWidth : 1,
				layout : 'column',
				defaults : {
					anchor : '100%',
					style : "margin-left:5px;margin-top:5px",
					columnWidth : .1
				},
				items : [{
					layout : "form", // 从上往下的布局
					columnWidth : .08,
					items : [{
						xtype : 'panel',
						border : false,
						bodyStyle : 'margin-left:0px;margin-top:5px',
						width : 95,
						html : '<B><font class="x-myZW-fieldset-title">【业务信息】：</font></B>'
					}]
				}, {
					layout : "form", // 从上往下的布局
					items : [{
						xtype : 'button',
						text : '01项目控制信息',
						anchor : '100%',
						scope : this,
						disabled : !(isGranted('_seeflProjectInfo_fl'
								+ this.projectStatus + this.bmStatus)||isGranted('_editflProjectInfo_fl'
								+ this.projectStatus + this.bmStatus)),
						handler : function() {
							location.href = "#fl_projectBaseInfo_" + this.flag
									+ this.projectId;
						}
					}]
				}, {
					layout : "form", // 从上往下的布局
					items : [{
						xtype : 'button',
						anchor : '100%',
						text : '02客户信息',
						scope : this,
						disabled : !(isGranted('_seeflCustomerInfo_fl'
								+ this.projectStatus + this.bmStatus)||isGranted('_editflCustomerInfo_fl'
								+ this.projectStatus + this.bmStatus)),
						handler : function() {
							location.href = "#fl_customerInfo_" + this.flag
									+ this.projectId;
						}
					}]
				}, {
					layout : "form", // 从上往下的布局
					items : [{
						xtype : 'button',
						anchor : '100%',
						text : '03租赁物清单',
						scope : this,
						disabled : !(isGranted('_seeflLeaseObject_fl'
								+ this.projectStatus + this.bmStatus)||isGranted('_editflLeaseObject_fl'
								+ this.projectStatus + this.bmStatus)),
						handler : function() {
							location.href = "#fl_leaseObject_" + this.flag
									+ this.projectId;
						}
					}]
				},{
					layout : "form", // 从上往下的布局
					items : [{
						xtype : 'button',
						anchor : '100%',
						text : '04资金款项信息',
						scope : this,
						disabled : !(isGranted('_seeflFinanceBaseInfo_fl'
								+ this.projectStatus + this.bmStatus)||isGranted('_editflFinanceBaseInfo_fl'
								+ this.projectStatus + this.bmStatus)),
						handler : function() {
							location.href = "#fl_financeInfo_" + this.flag
									+ this.projectId;
						}
					}]
				}, {
					layout : "form", // 从上往下的布局
					items : [{
						xtype : 'button',
						anchor : '100%',
						text : '05手续收支清单',
						scope : this,
						disabled : !(isGranted('_seeflActualToCharge_fl'
								+ this.projectStatus + this.bmStatus)||isGranted('_editflActualToCharge_fl'
								+ this.projectStatus + this.bmStatus)),
						handler : function() {
							location.href = "#fl_actualToChargeInfo_"
									+ this.flag + this.projectId;
						}
					}]
				},{
					layout : "form", // 从上往下的布局
					items : [{
						xtype : 'button',
						anchor : '100%',
						text : '06担保措施管理',
						scope : this,
						disabled : !(isGranted('_seeCreditMaterials_fl'
								+ this.projectStatus + this.bmStatus)||isGranted('_editCreditMaterials_fl'
								+ this.projectStatus + this.bmStatus)),
						handler : function() {
							location.href = "#fl_mortgage_" + this.flag
									+ this.projectId;
						}
					}]
				}, {
					layout : "form", // 从上往下的布局
					items : [{
						xtype : 'button',
						anchor : '100%',
						text : '07业务材料',
						scope : this,
						disabled : !(isGranted('_seeBusinessMaterials_fl'
								+ this.projectStatus + this.bmStatus)||isGranted('_editBusinessMaterials_fl'
								+ this.projectStatus + this.bmStatus)),
						handler : function() {
							location.href = "#fl_businessFiles_" + this.flag
									+ this.projectId;
						}
					}]
				}]
			}, {
				columnWidth : 1,
				layout : 'column',
				defaults : {
					anchor : '100%',
					style : "margin-left:5px;margin-top:5px",
					columnWidth : .1
				},
				items : [{
					layout : "form", // 从上往下的布局
					columnWidth : .08,
					items : [{
						xtype : 'panel',
						border : false,
						bodyStyle : 'margin-left:0px;margin-top:5px',
						width : 95,
						html : '<B><font class="x-myZW-fieldset-title">【合同文件】：</font></B>'
					}]
				}, {
					layout : "form", // 从上往下的布局
					items : [{
						xtype : 'button',
						anchor : '100%',
						text : '08项目合同',
						scope : this,
						disabled : !(isGranted('_seeContract_fl'
								+ this.projectStatus + this.bmStatus)||isGranted('_editContract_fl'
								+ this.projectStatus + this.bmStatus)),
						handler : function() {
							location.href = "#fl_contract_" + this.flag
									+ this.projectId;
						}
					}]
				},{
					layout : "form", // 从上往下的布局
					items : [{
						xtype : 'button',
						anchor : '100%',
						text : '09办理文件',
						scope : this,
						disabled : !(isGranted('_seeManageFiles_fl'
								+ this.projectStatus + this.bmStatus)||isGranted('_editManageFiles_fl'
								+ this.projectStatus + this.bmStatus)),
						handler : function() {
							location.href = "#fl_manageFiles_" + this.flag
									+ this.projectId;
						}
					}]
				}, {
					layout : "form", // 从上往下的布局
					items : [{
						xtype : 'button',
						anchor : '100%',
						text : '10调查报告',
						scope : this,
						disabled : !(isGranted('_seeReport_fl'
								+ this.projectStatus + this.bmStatus)||isGranted('_editReport_fl'
								+ this.projectStatus + this.bmStatus)),
						height : 22,
						handler : function() {
							location.href = "#fl_report_"
									+ this.flag + this.projectId;
						}
					}]
				},{
					layout : "form", // 从上往下的布局
					items : [{
						xtype : 'button',
						anchor : '100%',
						text : '11风险分析报告',
						scope : this,
						disabled : !(isGranted('_seeRiskReport_fl'
								+ this.projectStatus + this.bmStatus)||isGranted('_editRiskReport_fl'
								+ this.projectStatus + this.bmStatus)),
						height : 22,
						handler : function() {
							location.href = "#fl_riskReport_"
									+ this.flag + this.projectId;
						}
					}]
				},{
					layout : "form", // 从上往下的布局
					items : [{
						xtype : 'button',
						anchor : '100%',
						text : '12归档材料清单',
						scope : this,
						disabled : !(isGranted('_seeArchivesMaterials_fl'
								+ this.projectStatus + this.bmStatus)||isGranted('_editArchivesMaterials_fl'
								+ this.projectStatus + this.bmStatus)),
						height : 22,
						handler : function() {
							location.href = "#fl_archivesMaterials_"
									+ this.flag + this.projectId;
						}
					}]
				}]
			}, {
						columnWidth : 1,
						layout : 'column',
						defaults : {
							anchor : '100%',
							style : "margin-left:5px;margin-top:5px",
							columnWidth : .1
						},
						items : [{
							layout : "form", // 从上往下的布局
							columnWidth : .08,
							items : [{
								xtype : 'panel',
								border : false,
								bodyStyle : 'margin-left:0px;margin-top:5px',
								width : 95,
								html : '<B><font class="x-myZW-fieldset-title">【租后记录】：</font></B>'
							}]
						},{
					layout : "form", // 从上往下的布局
					items : [{
						xtype : 'button',
						anchor : '100%',
						text : '13监管记录',
						scope : this,
						disabled : !(isGranted('_seeSupervisionManagement_fl'
								+ this.projectStatus + this.bmStatus)||isGranted('_editSupervisionManagement_fl'
								+ this.projectStatus + this.bmStatus)),
						height : 22,
						handler : function() {
							location.href = "#fl_supervisionManagement_"
									+ this.flag + this.projectId;
						}
					}]
				},{
					layout : "form", // 从上往下的布局
					items : [{
						xtype : 'button',
						anchor : '100%',
						text : '14展期记录',
						scope : this,
						disabled : !(isGranted('_seeSuperviseRecord_fl'
								+ this.projectStatus + this.bmStatus)||isGranted('_editSuperviseRecord_fl'
								+ this.projectStatus + this.bmStatus)),           
						height : 22,
						handler : function() {
							location.href = "#fl_superviseRecord_"
									+ this.flag + this.projectId;
						}
					}]
				},{
					layout : "form", // 从上往下的布局
					items : [{
						xtype : 'button',
						anchor : '100%',
						text : '15提前还款记录',
						scope : this,
						disabled : !(isGranted('_seeEarlyrepaymentRecord_fl'
								+ this.projectStatus + this.bmStatus)||isGranted('_editEarlyrepaymentRecord_fl'
								+ this.projectStatus + this.bmStatus)),
						height : 22,
						handler : function() {
							location.href = "#fl_earlyrepaymentRecord_"
									+ this.flag + this.projectId;
						}
					}]
				},{
					layout : "form", // 从上往下的布局
					items : [{
						xtype : 'button',
						anchor : '100%',
						text : '16利率变更记录',
						scope : this,
						disabled : !(isGranted('_seeAflterAccrualRecord_fl'
								+ this.projectStatus + this.bmStatus)||isGranted('_editAflterAccrualRecord_fl'
								+ this.projectStatus + this.bmStatus)),
						height : 22,
						handler : function() {
							location.href = "#fl_aflterAccrualRecord_"
									+ this.flag + this.projectId;
						}
					}]
				}]
					},{
				columnWidth : 1,
				layout : 'column',
				defaults : {
					anchor : '100%',
					style : "margin-left:5px;margin-top:5px",
					columnWidth : .1
				},
				items : [{
					layout : "form", // 从上往下的布局
					columnWidth : .08,
					items : [{
						xtype : 'panel',
						border : false,
						bodyStyle : 'margin-left:0px;margin-top:5px',
						width : 95,
						html : '<B><font class="x-myZW-fieldset-title">【流程控制】：</font></B>'
					}]
				}, {
					layout : "form", // 从上往下的布局
					items : [{
						xtype : 'button',
						iconCls : 'btn-detail',
						anchor : '100%',
						text : '流程示意图',
						scope : this,
						disabled : isGranted('_showFlowImg_fl'
								+ this.projectStatus + this.bmStatus)
								? false
								: true,
						handler : function() {
							showFlowImgWin(this.record.data.runId);
						}
					}]
				}, {
					layout : "form", // 从上往下的布局
					items : [{
						xtype : 'button',
						iconCls : 'btn-advice',
						anchor : '100%',
						text : '意见与说明记录',
						scope : this,
						disabled : !isGranted('seeAgreements_fl'
								+ this.projectStatus + this.bmStatus),
						handler : function() {
							new SlProcessRunView({
								runId : this.record.data.runId,
								projectStatus : this.record.data.projectStatus,
								bmStatus : this.bmStatus,
								businessType : "LeaseFinance",
								isAutoHeight : false
							}).show();
						}
					}]
				}, {
					layout : "form", // 从上往下的布局
					hidden : (this.enabled == true && this.flag == 'edit' && !isGranted('taskHandler_fl'
							+ this.projectStatus + this.bmStatus) == false)
							? false
							: true,
					items : [{
						xtype : 'button',
						iconCls : 'btn-detail',
						anchor : '100%',
						text : '任务换人',
						scope : this,
						handler : function() {
							var taskName = this.record.data.activityName;
							if(taskName!=null&&taskName!=""&&taskName!="null"&&taskName!="undefined"){
								Ext.Ajax.request({
									url : __ctxPath + '/flow/usersProcessActivity.do',
									scope : this,
									params : {
										taskId : this.record.data.taskId,
										isGetCurrent : "true"
									},
									success : function(response, options) {
										var result = Ext.decode(response.responseText);
										var userId = result.userNames;
										new ProjectTaskHandler({
											record : this.record,
											userId : userId,
											idPrefix : "LeaseFinanceProjectInfo_",
											idPrefix_edit : "LeaseFinanceProjectInfoEdit_",
											isActivityComboEdit : this.isActivityComboEdit
										}).show();
									}
								});
							}
							/*new ProjectTaskHandler({
								record : this.record,
								userName : this.userName,
								idPrefix : "LeaseFinanceProjectInfo_",
								idPrefix_edit : "LeaseFinanceProjectInfoEdit_",
								isActivityComboEdit : this.isActivityComboEdit
							}).show();*/
						}
					}]
				}, {
					layout : "form", // 从上往下的布局
					hidden : (this.enabled == true && this.flag == 'edit' && !isGranted('projectChangeAssign_fl'
							+ this.projectStatus + this.bmStatus) == false)
							? false
							: true,
					items : [{
						xtype : 'button',
						iconCls : 'btn-detail',
						anchor : '100%',
						text : '项目换人',
						scope : this,
						handler : function() {
							var taskIdNew = this.taskId;
							var projectIdNew = this.projectId;
							var runIdNew = this.runId;
							var acrivityName = this.activityName;
							Ext.Ajax.request({
								url : __ctxPath
										+ '/creditFlow/isProjectTaskChangeAssignCreditProject.do?',
								params : {
									taskId : this.taskId,
									runId : this.runId,
									activityName : this.activityName
								},
								method : 'post',
								success : function(resp, op) {
									var res = Ext.util.JSON
											.decode(resp.responseText);
									if (res.success) {
										var startUserId = res.data.startUserId;
										var startUserName = res.data.startUserName;
										new ProjectTaskChangeAssign({
											taskId : taskIdNew,
											runId : runIdNew,
											startUserId : startUserId,
											startUserName : startUserName
										/*
										 * , projectId : projectIdNew, idPrefix :
										 * "LeaseFinanceProjectInfo_",
										 * idPrefix_edit :
										 * "LeaseFinanceProjectInfoEdit_",
										 * isActivityComboEdit :
										 * this.isActivityComboEdit
										 */
										}).show();
									} else {
										Ext.ux.Toast.msg('信息提示', '当前节点<'
												+ this.activityName
												+ '>不能进行任务换人操作!');
									}
								},
								failure : function() {
									Ext.ux.Toast.msg('信息提示', '出错，请联系管理员！');
								}
							});
						}
					}]
				}, {
					layout : "form", // 从上往下的布局
					hidden : (this.enabled == true && this.flag == 'edit' && !isGranted('pathChange_fl'
							+ this.projectStatus + this.bmStatus) == false)
							? false
							: true,
					items : [{
						xtype : 'button',
						iconCls : 'btn-detail',
						anchor : '100%',
						text : '流程跳转',
						scope : this,
						handler : function() {
							new ProjectPathChange({
								record : this.record,
								idPrefix : "LeaseFinanceProjectInfo_",
								idPrefix_edit : "LeaseFinanceProjectInfoEdit_"
							}).show();
						}
					}]
				}, {
					layout : "form", // 从上往下的布局
					hidden : (this.enabled == true && this.flag == 'edit' && !isGranted('stopPro_fl'
							+ this.projectStatus + this.bmStatus) == false)
							? false
							: true,
					items : [{
						xtype : 'button',
						iconCls : 'btn-close1',
						anchor : '100%',
						text : '终止项目',
						scope : this,
						handler : function() {
							new ProjectStop({
								record : this.record,
								idPrefix : "LeaseFinanceProjectInfo_",
								idPrefix_edit : "LeaseFinanceProjectInfoEdit_"
							}).show();
						}
					}]
				}]
			}]
		});
		this.add(this.panel);
		this.doLayout();
	}
});