/**
 * @author lisl
 * @class ProjectInfoNavigation
 * @description 担保项目信息页面导航
 * @extends Ext.Panel
 */
ProjectInfoNavigation = Ext.extend(Ext.Panel, {
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		this.initUIComponents();
		ProjectInfoNavigation.superclass.constructor.call(this, {
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
						disabled : !isGranted('gtProjectInfo_gt'
								+ this.projectStatus + this.bmStatus),
						handler : function() {
							location.href = "#gl_projectBaseInfo_" + this.flag
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
						disabled : !isGranted('gtCustomerInfo_gt'
								+ this.projectStatus + this.bmStatus),
						handler : function() {
							location.href = "#gl_customerInfo_" + this.flag
									+ this.projectId;
						}
					}]
				}, {
					layout : "form", // 从上往下的布局
					items : [{
						xtype : 'button',
						anchor : '100%',
						text : '03对接银行信息',
						scope : this,
						disabled : !isGranted('gtBankInfo_gt'
								+ this.projectStatus + this.bmStatus),
						handler : function() {
							location.href = "#gl_bankInfo_" + this.flag
									+ this.projectId;
						}
					}]
				}, {
					layout : "form", // 从上往下的布局
					items : [{
						xtype : 'button',
						anchor : '100%',
						text : '04资金款项信息',
						scope : this,
						disabled : !isGranted('gtFinanceBaseInfo_gt'
								+ this.projectStatus + this.bmStatus),
						handler : function() {
							location.href = "#gl_financeInfo_" + this.flag
									+ this.projectId;
						}
					}]
				}, {
					layout : "form", // 从上往下的布局
					items : [{
						xtype : 'button',
						anchor : '100%',
						text : '04手续收支清单',
						scope : this,
						disabled : !isGranted('gtActualToCharge_gt'
								+ this.projectStatus + this.bmStatus),
						handler : function() {
							location.href = "#gl_actualToChargeInfo_"
									+ this.flag + this.projectId;
						}
					}]
				}, {
					layout : "form", // 从上往下的布局
					items : [{
						xtype : 'button',
						anchor : '100%',
						text : '05保证金支付情况',
						scope : this,
						disabled : !isGranted('seeBankMargin_gt'
								+ this.projectStatus + this.bmStatus),
						handler : function() {
							location.href = "#gl_creditlineRelease_"
									+ this.flag + this.projectId;
						}
					}]
				}, {
					layout : "form", // 从上往下的布局
					items : [{
						xtype : 'button',
						anchor : '100%',
						text : '06银行放款收息表',
						scope : this,
						disabled : !isGranted('customerRepayment_gt'
								+ this.projectStatus + this.bmStatus),
						handler : function() {
							location.href = "#gl_customerRepaymentPlan_"
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
						html : '<B><font class="x-myZW-fieldset-title">【风险控制】：</font></B>'
					}]
				}, {
					layout : "form", // 从上往下的布局
					items : [{
						xtype : 'button',
						anchor : '100%',
						text : '07担保准入原则',
						scope : this,
						disabled : !isGranted('seeAssuretenet_gt'
								+ this.projectStatus + this.bmStatus),
						handler : function() {
							location.href = "#gl_assuretenetInfo_" + this.flag
									+ this.projectId;
						}
					}]
				}, {
					layout : "form", // 从上往下的布局
					items : [{
						xtype : 'button',
						anchor : '100%',
						text : '08担保材料清单',
						scope : this,
						disabled : !isGranted('seeCreditMaterials_gt'
								+ this.projectStatus + this.bmStatus),
						handler : function() {
							location.href = "#gl_materials_" + this.flag
									+ this.projectId;
						}
					}]
				}, {
					layout : "form", // 从上往下的布局
					items : [{
						xtype : 'button',
						anchor : '100%',
						text : '09客户资信评估',
						scope : this,
						disabled : !isGranted('enterpriseEvaluation_gt'
								+ this.projectStatus + this.bmStatus),
						handler : function() {
							location.href = "#gl_evaluation_" + this.flag
									+ this.projectId;
						}
					}]
				}, {
					layout : "form", // 从上往下的布局
					items : [{
						xtype : 'button',
						anchor : '100%',
						text : '11反担保措施管理',
						scope : this,
						disabled : !isGranted('mortgageView_gt'
								+ this.projectStatus + this.bmStatus),
						handler : function() {
							location.href = "#gl_mortgage_" + this.flag
									+ this.projectId;
						}
					}]
				}, {
					layout : "form", // 从上往下的布局
					items : [{
						xtype : 'button',
						anchor : '100%',
						text : '12尽职调查报告',
						scope : this,
						disabled : !isGranted('seeReport_gt'
								+ this.projectStatus + this.bmStatus),
						handler : function() {
							location.href = "#gl_report_" + this.flag
									+ this.projectId;
						}
					}]
				}, {
					layout : "form", // 从上往下的布局
					items : [{
						xtype : 'button',
						anchor : '100%',
						text : '13风险分析报告',
						scope : this,
						disabled : !isGranted('seeRiskReport_gt'
								+ this.projectStatus + this.bmStatus),
						handler : function() {
							location.href = "#gl_riskReportView_" + this.flag
									+ this.projectId;
						}
					}]
				}, {
					layout : "form", // 从上往下的布局
					items : [{
						xtype : 'button',
						iconCls : 'btn-detail',
						anchor : '100%',
						text : '审保会决议情况',
						scope : this,
						disabled : !isGranted('_seeSbh_gt' + this.projectStatus + this.bmStatus) || !isGranted('_seeSomeSbh_gt' + this.projectStatus + this.bmStatus),
						handler : function() {
							new SbhProjectInfo({
								runId : this.runId,
								taskId : this.taskId,
								projectStatus : this.projectStatus,
								bmStatus : this.bmStatus,
								title : '审保会集体决议会签情况',
								//会签任务的key,如果一个流程有两个会签的情况,并且两个会签的记录都显示在当前列表,则传两个会签节点的key,如："'key1','key2'";
								//只显示一个会签的记录则传相应的会签节点的key。如没有会签，则当前按钮会隐藏。如下代码：
								countersignedTaskKey : 'gnDirectorCheck,gnExaminationArrangement'
							}).show();
						}
					}]
				}, {
					layout : "form", // 从上往下的布局
					items : [{
						xtype : 'button',
						anchor : '100%',
						text : '14保中监管记录',
						scope : this,
						disabled : !isGranted('_middleSupervision_gt'
								+ this.projectStatus + this.bmStatus),
						height : 22,
						handler : function() {
							location.href = "#gl_middleSupervisionInfo_"
									+ this.flag + this.projectId;
						}
					}]
				}, {
					layout : "form", // 从上往下的布局
					items : [{
						xtype : 'button',
						iconCls : 'btn-detail',
						anchor : '100%',
						text : '项目归档记录',
						scope : this,
						disabled : !isGranted('seeFilingRecord_gt'
								+ this.projectStatus + this.bmStatus),
						handler : function() {
							new SlFilingRecordView({
								projectId : this.projectId
							}).show();
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
						html : '<B><font class="x-myZW-fieldset-title">【合同文书】：</font></B>'
					}]
				}, {
					layout : "form", // 从上往下的布局
					items : [{
						xtype : 'button',
						anchor : '100%',
						text : '15拟担保意向书',
						scope : this,
						disabled : !isGranted('seeNdbBook_gt'
								+ this.projectStatus + this.bmStatus),
						handler : function() {
							location.href = "#gl_letterAndBook_" + this.flag
									+ this.projectId;
						}
					}]
				}, {
					layout : "form", // 从上往下的布局
					items : [{
						xtype : 'button',
						anchor : '100%',
						text : '16对外担保承诺函',
						scope : this,
						disabled : !isGranted('seeDwdbcnBook_gt'
								+ this.projectStatus + this.bmStatus),
						handler : function() {
							location.href = "#gl_letterAndBook1_" + this.flag
									+ this.projectId;
						}
					}]
				}, {
					layout : "form", // 从上往下的布局
					items : [{
						xtype : 'button',
						anchor : '100%',
						text : '17委托担保合同',
						scope : this,
						disabled : !isGranted('seeDtdbContract_gt'
								+ this.projectStatus + this.bmStatus),
						handler : function() {
							location.href = "#gl_contractInfo_" + this.flag
									+ this.projectId;
						}
					}]
				}, {
					layout : "form", // 从上往下的布局
					items : [{
						xtype : 'button',
						anchor : '100%',
						text : '11反担保合同',
						scope : this,
						disabled : !isGranted('mortgageView_gt'
								+ this.projectStatus + this.bmStatus),
						handler : function() {
							location.href = "#gl_mortgage_" + this.flag
									+ this.projectId;
						}
					}]
				},  {
					layout : "form", // 从上往下的布局
					items : [{
						xtype : 'button',
						anchor : '100%',
						text : '10客户借款合同',
						scope : this,
						disabled : !isGranted('seeKhjkContract_gt'
								+ this.projectStatus + this.bmStatus),
						handler : function() {
							location.href = "#gl_uploads_" + this.flag
									+ this.projectId;
						}
					}]
				}, {
					layout : "form", // 从上往下的布局
					items : [{
						xtype : 'button',
						anchor : '100%',
						text : '18银行担保合同',
						scope : this,
						disabled : !isGranted('gtContract_gt'
								+ this.projectStatus + this.bmStatus),
						handler : function() {
							location.href = "#gl_uploads1_" + this.flag
									+ this.projectId;
						}
					}]
				}, {
					layout : "form", // 从上往下的布局
					items : [{
						xtype : 'button',
						anchor : '100%',
						text : '19担保责任解除函',
						scope : this,
						disabled : !isGranted('seeDbzrjcBook_gt'
								+ this.projectStatus + this.bmStatus),
						handler : function() {
							location.href = "#gl_uploads2_" + this.flag
									+ this.projectId;
						}
					}]
				}, {
					layout : "form", // 从上往下的布局
					items : [{
						xtype : 'button',
						anchor : '100%',
						text : '21收取保费凭证',
						scope : this,
						disabled : !(isGranted('seeSqbfpz_gt' + this.projectStatus + this.bmStatus)||isGranted('editSqbfpz_gt' + this.projectStatus + this.bmStatus)),
						handler : function() {
							location.href = "#gl_guaranteeToCharge_" + this.flag
									+ this.projectId;
						}
					}]
				}]
			}, {
				columnWidth : 1,
				layout : 'column',
				items:[{
					layout :'form',
					columnWidth : .084,
					items:[{
						xtype : 'panel',
						border : false,
						bodyStyle : 'margin-left:0px;margin-top:5px',
						width : 95,
						html : ''
					}]
				}, {
					layout : "form", // 从上往下的布局
					columnWidth : .1,
					items : [{
						xtype : 'button',
						anchor : '100%',
						text : '22客户保证金凭证',
						style : 'margin-top:5px',
						scope : this,
						disabled : !(isGranted('seeSqkhbzjpz_gt' + this.projectStatus + this.bmStatus)||isGranted('editSqkhbzjpz_gt' + this.projectStatus + this.bmStatus)),
						handler : function() {
							location.href = "#gl_toCustomGuarantMoney_" + this.flag
									+ this.projectId;
						}
					}]
				}, {
					layout : "form", // 从上往下的布局
					columnWidth : .1,
					items : [{
						xtype : 'button',
						anchor : '100%',
						text : '23冻结保证金凭证',
						style : 'margin-top:5px;margin-left:5px',
						scope : this,
						disabled : !(isGranted('seeSqdjbzjpz_gt' + this.projectStatus + this.bmStatus)||isGranted('editSqdjbzjpz_gt' + this.projectStatus + this.bmStatus)),
						handler : function() {
							location.href = "#gl_bankguaranteemoney_" + this.flag
									+ this.projectId;
						}
					}]
				}, {
					layout : "form", // 从上往下的布局
					columnWidth : .096,
					items : [{
						xtype : 'button',
						anchor : '100%',
						text : '24款项凭证',
						style : 'margin-top:5px;margin-left:5px',
						scope : this,
						disabled : !(isGranted('seeKxpz_gt' + this.projectStatus + this.bmStatus)||isGranted('editSqdjbzjpz_gt' + this.projectStatus + this.bmStatus)),
						handler : function() {
							location.href = "#gl_paymentCertificate_" + this.flag
									+ this.projectId;
						}
					}]
				}, {
					layout : "form", // 从上往下的布局
					columnWidth : .1,
					items : [{
						xtype : 'button',
						anchor : '100%',
						text : '25放款通知单',
						style : 'margin-top:5px;margin-left:5px',
						scope : this,
						disabled : !(isGranted('seeTzd_gt' + this.projectStatus + this.bmStatus)||isGranted('editTzd_gt' + this.projectStatus + this.bmStatus)),
						handler : function() {
							location.href = "#gl_tongzhidan_" + this.flag
									+ this.projectId;
						}
					}]
				}, {
					layout : "form", // 从上往下的布局
					columnWidth : .1,
					items : [{
						xtype : 'button',
						anchor : '100%',
						text : '26违约信息',
						style : 'margin-top:5px;margin-left:5px',
						scope : this,
						//disabled : !(isGranted('seeTzd_gt' + this.projectStatus + this.bmStatus)||isGranted('editTzd_gt' + this.projectStatus + this.bmStatus)),
						handler : function() {
							location.href = "#gl_breachInfo_" + this.flag
									+ this.projectId;
						}
					}]
				}, {
					layout : "form", // 从上往下的布局
					columnWidth : .1,
					items : [{
						xtype : 'button',
						anchor : '100%',
						text : '代偿',
						iconCls : 'btn-detail',
						style : 'margin-top:5px;margin-left:5px',
						scope : this,
						//disabled : !(isGranted('seeTzd_gt' + this.projectStatus + this.bmStatus)||isGranted('editTzd_gt' + this.projectStatus + this.bmStatus)),
						handler : function() {
							var CompensatorynewGuarantee=new Compensatory({
							   	projectId : this.projectId,
							   	businessType : "Guarantee",
							   	isHidden : this.flag=='see'?true:false
						   	});
							CompensatorynewGuarantee.show();
						}
					}]
				}, {
					layout : "form", // 从上往下的布局
					columnWidth : .1,
					items : [{
						xtype : 'button',
						iconCls : 'btn-detail',
						anchor : '100%',
						text : '追偿',
						style : 'margin-top:5px;margin-left:5px',
						scope : this,
						//disabled : !(isGranted('seeTzd_gt' + this.projectStatus + this.bmStatus)||isGranted('editTzd_gt' + this.projectStatus + this.bmStatus)),
						handler : function() {
							var RecoveryGuarantee=new Recovery({
							   	projectId : this.projectId,
							   	businessType : "Guarantee",
							   	isHidden : this.flag=='see'?true:false
						   	});
						   	RecoveryGuarantee.show();
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
						disabled : isGranted('_showFlowImg_gt'
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
						disabled : !isGranted('seeAgreements_gt'
								+ this.projectStatus + this.bmStatus),
						handler : function() {
							new SlProcessRunView({
								runId : this.runId,
								projectStatus : this.projectStatus,
								bmStatus : this.bmStatus,
								businessType : "Guarantee",
								isAutoHeight : false
							}).show();
						}
					}]
				}, {
					layout : "form", // 从上往下的布局
					hidden : (this.enabled == true && this.flag == 'edit' && !isGranted('taskHandler_gt'
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
											idPrefix : "GuaranteeProjectInfo_",
											idPrefix_edit : "GuaranteeProjectInfoEdit_",
											isActivityComboEdit : this.isActivityComboEdit
										}).show();
									}
								});
							}
							/*new ProjectTaskHandler({
								record : this.record,
								userName : this.userName,
								idPrefix : "GuaranteeProjectInfo_",
								idPrefix_edit : "GuaranteeProjectInfoEdit_",
								isActivityComboEdit : this.isActivityComboEdit
							}).show();*/
						}
					}]
				}, {
					layout : "form", // 从上往下的布局
					hidden : (this.enabled == true && this.flag == 'edit' && !isGranted('projectChangeAssign_gt'
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
										 * "GuaranteeProjectInfo_",
										 * idPrefix_edit :
										 * "GuaranteeProjectInfoEdit_",
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
					hidden : (this.enabled == true && this.flag == 'edit' && !isGranted('pathChange_gt'
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
								idPrefix : "GuaranteeProjectInfo_",
								idPrefix_edit : "GuaranteeProjectInfoEdit_"
							}).show();
						}
					}]
				}, {
					layout : "form", // 从上往下的布局
					hidden : (this.enabled == true && this.flag == 'edit' && !isGranted('stopPro_gt'
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
								idPrefix : "GuaranteeProjectInfo_",
								idPrefix_edit : "GuaranteeProjectInfoEdit_"
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