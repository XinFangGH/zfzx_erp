/**
 * @author lisl
 * @class SmallLoanProjectInfoNavigation
 * @description 小贷项目信息页面导航
 * @extends Ext.Panel
 */
PlPawnProjectInfoNavigation = Ext.extend(Ext.Panel, {
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		this.initUIComponents();
		PlPawnProjectInfoNavigation.superclass.constructor.call(this, {
			region : 'center',
			layout : 'anchor',
			items : []
		});
	},
	initUIComponents : function() {
		var jsArr = [
				__ctxPath + '/js/creditFlow/smallLoan/project/SlVoteSignForm.js',// 会签投票
				__ctxPath + '/js/creditFlow/smallLoan/project/SlExecutiveOpinionForm.js',// 主管意见记录
				__ctxPath + '/js/creditFlow/smallLoan/project/SlMeetingSummary.js',// 上传纪要
				__ctxPath + '/js/creditFlow/smallLoan/project/SlProcessRunView.js',// 意见与说明记录
				__ctxPath + '/js/creditFlow/common/ProjectTaskHandler.js',// 项目换人
				__ctxPath + '/js/creditFlow/common/ProjectPathChange.js',// 流程跳转
				__ctxPath + '/js/creditFlow/guarantee/meeting/SbhProjectInfo.js'
				
				
		];
		$ImportSimpleJs(jsArr, this.constructPanel, this);
	},// 初始化组件
	constructPanel : function() {
		this.oppositeType = this.record.data.oppositeType;
		this.oppositeID = this.record.data.oppositeId;
		this.projectId = this.record.data.projectId;
		this.projectStatus = this.record.data.projectStatus;
		this.projectMoney = this.record.data.projectMoney;
		this.businessType = this.record.data.businessType;
		this.runId = this.record.data.runId;
		this.taskId = this.record.data.taskId;
		this.operationType=this.record.data.operationType

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
								text : '01项目基本信息',
								anchor : '100%',
								scope : this,
								disabled : !(isGranted('seePawnProjectInfo_' + this.projectStatus)||isGranted('editPawnProjectInfo_'+ this.projectStatus)),
								handler : function() {
									location.href = "#pl_projectInfo_"
											+ this.flag + this.projectId;
								}
							}]
						}, {
							layout : "form", // 从上往下的布局
							items : [{
								xtype : 'button',
								anchor : '100%',
								text : '02客户信息',
								scope : this,
								disabled : !(isGranted('seePawnCustomerInfo_' + this.projectStatus)||isGranted('editPawnCustomerInfo_'+ this.projectStatus)),
								handler : function() {
									location.href = "#pl_customerInfo_"
											+ this.flag + this.projectId;
								}
							}]
						}, {
							layout : "form", // 从上往下的布局
							items : [{
								xtype : 'button',
								anchor : '100%',
								text : '03典当信息',
								scope : this,
								disabled : !(isGranted('seePawnFinanceInfo_' + this.projectStatus)||isGranted('editPawnFinanceInfo_'+ this.projectStatus)),
								handler : function() {
									location.href = "#pl_financeInfo_"
											+ this.flag + this.projectId;
								}
							}]
						}, {
							layout : "form", // 从上往下的布局
							items : [{
								xtype : 'button',
								anchor : '100%',
								text : '04当物清单',
								scope : this,
								disabled : !(isGranted('seePawnItemsList_' + this.projectStatus)||isGranted('editPawnItemsList_'+ this.projectStatus)),
								handler : function() {
									location.href = "#pl_pawnItemsListInfo_"
											+ this.flag + this.projectId;
								}
							}]
						}, {
							layout : "form", // 从上往下的布局
							items : [{
								xtype : 'button',
								anchor : '100%',
								text : '05放款收息表',
								scope : this,
								disabled : !(isGranted('seePawnFundIntent_' + this.projectStatus)||isGranted('editPawnFundIntent_'+ this.projectStatus)),
								handler : function() {
									location.href = "#pl_fundIntentInfo_"
											+ this.flag + this.projectId;
								}
							}]
						}, {
							layout : "form", // 从上往下的布局
							items : [{
								xtype : 'button',
								anchor : '100%',
								text : '06合同列表',
								scope : this,
								disabled : !(isGranted('seePawnContractInfo_' + this.projectStatus)||isGranted('editPawnContractInfo_'+ this.projectStatus)),
								handler : function() {
									location.href = "#pl_contractInfo_"
											+ this.flag + this.projectId;
								}
							}]
						}, {
							layout : "form", // 从上往下的布局
							items : [{
								xtype : 'button',
								anchor : '100%',
								text : '07办理文件',
								scope : this,
								disabled : !(isGranted('seePawnUploads_' + this.projectStatus)||isGranted('editPawnUploads_'+ this.projectStatus)),
								handler : function() {
									location.href = "#pl_uploads_"
											+ this.flag + this.projectId;
								}
							}]
						}, {
							layout : "form", // 从上往下的布局
							items : [{
								xtype : 'button',
								anchor : '100%',
								text : '08预评估报告',
								scope : this,
								disabled : !(isGranted('seePawnPreAssessmentReportInfo_' + this.projectStatus)||isGranted('editPawnPreAssessmentReportInfo_'+ this.projectStatus)),
								handler : function() {
									location.href = "#pl_preAssessmentReportInfo_"
											+ this.flag + this.projectId;
								}
							}]
						}, {
							layout : "form", // 从上往下的布局
							items : [{
								xtype : 'button',
								anchor : '100%',
								text : '09归档材料清单',
								scope : this,
								disabled : !(isGranted('seePawnMaterials_' + this.projectStatus)||isGranted('editPawnMaterials_'+ this.projectStatus)),
								handler : function() {
									location.href = "#pl_materials_"
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
						items :[{
							layout : "form", // 从上往下的布局
							columnWidth : .08,
							items : [{
								xtype : 'panel',
								border : false,
								bodyStyle : 'margin-left:0px;margin-top:5px',
								width : 95,
								html : '<B><font class="x-myZW-fieldset-title"></font></B>'
							}]
						},{
							layout : "form", // 从上往下的布局
							items : [{
								xtype : 'button',
								iconCls : 'btn-detail',
								anchor : '100%',
								text : '评审会决议',
								scope : this,
								disabled : !(isGranted('seePawnOnlineReviewMeeting_' + this.projectStatus)||isGranted('editPawnOnlineReviewMeeting_'+ this.projectStatus)),
								handler : function() {
									new SbhProjectInfo({
										runId : this.runId,
										taskId : this.taskId,
										projectStatus : this.projectStatus,
										title : '评审会决议',
										//会签任务的key,如果一个流程有两个会签的情况,并且两个会签的记录都显示在当前列表,则传两个会签节点的key,如："'key1','key2'";
										//只显示一个会签的记录则传相应的会签节点的key。如没有会签，则当前按钮会隐藏。如下代码：
										countersignedTaskKey : 'resolutionOnlineReviewMeeting'
									}).show();
								}
							}]
						},{
								layout : "form", // 从上往下的布局
								items : [{
									xtype : 'button',
									iconCls : 'btn-detail',
									anchor : '100%',
									text : '线下评审会决议',
									scope : this,
									disabled : !(isGranted('seePawnReviewMeeting_' + this.projectStatus)||isGranted('editPawnReviewMeeting_'+ this.projectStatus)),
									handler : function() {
										new SlMeetingSummary({
											projectId : this.projectId,
											businessType : "Pawn"
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
								html : '<B><font class="x-myZW-fieldset-title">【当后记录】：</font></B>'
							}]
						},{
							layout : "form", // 从上往下的布局
							items : [{
								xtype : 'button',
								anchor : '100%',
								text : '10续当记录',
								scope : this,
								disabled : !(isGranted('seePawnContinuedInfo_' + this.projectStatus)||isGranted('editPawnContinuedInfo_'+ this.projectStatus)),
								handler : function() {
									location.href = "#pl_pawnContinued_"
											+ this.flag + this.projectId;
								}
							}]
						},{
							layout : "form", // 从上往下的布局
							items : [{
								xtype : 'button',
								anchor : '100%',
								text : '11赎当记录',
								scope : this,
								disabled : !(isGranted('seePawnRedeemInfo_' + this.projectStatus)||isGranted('editPawnRedeemInfo_'+ this.projectStatus)),
								handler : function() {
									location.href = "#pl_pawnRedeem_"
											+ this.flag + this.projectId;
								}
							}]
						},{
							layout : "form", // 从上往下的布局
							items : [{
								xtype : 'button',
								anchor : '100%',
								text : '12绝当记录',
								scope : this,
								disabled : !(isGranted('seePawnVastInfo_' + this.projectStatus)||isGranted('editPawnVastInfo_'+ this.projectStatus)),
								handler : function() {
									location.href = "#pl_pawnVast_"
											+ this.flag + this.projectId;
								}
							}]
						},{
							layout : "form", // 从上往下的布局
							items : [{
								xtype : 'button',
								anchor : '100%',
								text : '13挂失记录',
								scope : this,
								disabled : !(isGranted('seePawnTicketLoss_' + this.projectStatus)||isGranted('editPawnTicketLoss_'+ this.projectStatus)),
								handler : function() {
									location.href = "#pl_pawnTicketLoss_"
											+ this.flag + this.projectId;
								}
							}]
						},{
							layout : "form", // 从上往下的布局
							items : [{
								xtype : 'button',
								anchor : '100%',
								text : '14补发记录',
								scope : this,
								disabled : !(isGranted('seePawnTicketReissue_' + this.projectStatus)||isGranted('editPawnTicketReissue_'+ this.projectStatus)),
								handler : function() {
									location.href = "#pl_pawnTicketReissue_"
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
								disabled : !(isGranted('seePawnshowFlowImg_'+ this.projectStatus)||isGranted('editPawnShowFlowImg_'+ this.projectStatus)),
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
								disabled : !(isGranted('seePawnAgreements_'+ this.projectStatus)||isGranted('editPawnAgreements_'+ this.projectStatus)),
								handler : function() {
									new SlProcessRunView({
										runId : this.runId,
										projectStatus : this.projectStatus,
										businessType : "SmallLoan",
										isAutoHeight : false
									}).show();
								}
							}]
						}, {
							layout : "form", // 从上往下的布局
							hidden : (this.flag == 'edit'
									&& this.projectStatus != 2
									&& this.projectStatus != 3
									&& this.projectStatus != 8
									&& !isGranted('editPawnTaskHandler_'
									+ this.projectStatus) == false)
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
													idPrefix : "PlPawnProjectInfo_",
													idPrefix_edit : "PlPawnProjectInfoEdit_",
													isActivityComboEdit : this.isActivityComboEdit
												}).show();
											}
										});
									}
								}
							}]
						}, {
							layout : "form", // 从上往下的布局
							hidden : (this.flag == 'edit'
									&& this.projectStatus == 1
									&& this.projectStatus == 0
									&& !isGranted('editPawnPathChange_'
									+ this.projectStatus) == false)
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
										idPrefix : "PlPawnProjectInfo_",
										idPrefix_edit : "PlPawnProjectInfoEdit_"
									}).show();
								}
							}]
						}, {
							layout : "form", // 从上往下的布局
							hidden : (this.flag == 'edit'
								&& this.projectStatus == 1
									&& this.projectStatus == 0
									&& !isGranted('editPawnStopPro_'
									+ this.projectStatus) == false)
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
										idPrefix : "PlPawnProjectInfo_",
										idPrefix_edit : "PlPawnProjectInfoEdit_"
									}).show();
								}
							}]
						}]
					}]
				});
				this.add(this.panel);
				this.doLayout();
//			}
//		})
	}
});