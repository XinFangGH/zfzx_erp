/**
 * @author lisl
 * @class SmallLoanProjectInfoNavigation
 * @description 小贷项目信息页面导航
 * @extends Ext.Panel
 */
SmallLoanProjectInfoNavigation = Ext.extend(Ext.Panel, {
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		this.initUIComponents();
		SmallLoanProjectInfoNavigation.superclass.constructor.call(this, {
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
				__ctxPath + '/js/creditFlow/smallLoan/project/SlFilingRecordView.js',// 归档记录
				__ctxPath + '/js/creditFlow/common/ProjectTaskHandler.js',// 项目换人
				__ctxPath + '/js/creditFlow/common/ProjectPathChange.js',// 流程跳转
				__ctxPath + '/js/creditFlow/smallLoan/project/SlBreachDetailView.js',// 违约处理详情
				__ctxPath + '/js/creditFlow/smallLoan/project/SlEarlyrepaymentRecordView.js',// 提前还款记录
				__ctxPath + '/js/creditFlow/smallLoan/finance/EarlyRepaymentSlFundIntent.js',// 提前还款
				__ctxPath + '/js/creditFlow/smallLoan/project/SlEarlyrepaymentDetailView.js',
				__ctxPath + '/js/creditFlow/finance/SlActualToCharge.js',// 经办费用清单
				__ctxPath + '/js/creditFlow/finance/SlFundIntentViewVM.js',// 款项信息,
				__ctxPath + '/js/creditFlow/finance/detailView.js',
				__ctxPath + '/js/creditFlow/smallLoan/project/SlAlterAccrualRecordView.js',// 利率变更记录
				__ctxPath + '/js/creditFlow/smallLoan/project/SlAlterAccrualDetailView.js',// 利率变更详情
				__ctxPath + '/js/creditFlow/smallLoan/finance/AlterAccrualSlFundIntent.js',// 利率变更款项
				__ctxPath + '/js/creditFlow/smallLoan/project/DesignSupervisionManagePlan.js',// 监管计划
				__ctxPath + '/js/creditFlow/smallLoan/project/SlSupervisonRecordView.js',// 监管
				__ctxPath + '/js/creditFlow/smallLoan/finance/BorrowerInfo.js',//共同借款人信息
				__ctxPath + '/js/creditFlow/smallLoan/materials/SlProcreditSmallLoanMaterialsView.js',// 贷款材料清单
				
				__ctxPath + '/js/creditFlow/smallLoan/finance/FinanceEarlyRepaymentPanel.js',
				__ctxPath + '/js/creditFlow/smallLoan/finance/FinanceAlterAccrualPanel.js',
				__ctxPath + '/js/creditFlow/guarantee/contract/LetterAndBookView.js',
				__ctxPath + '/js/creditFlow/smallLoan/finance/SlSuperviseRecordListView.js',
				__ctxPath + '/js/creditFlow/smallLoan/project/PostponedProjectInfo.js',
				__ctxPath + '/js/creditFlow/smallLoan/project/PostponedProjectInfoPanel.js',
				__ctxPath + '/js/creditFlow/smallLoan/project/PostponedProjectInfoEdit.js',
				__ctxPath + '/js/creditFlow/smallLoan/project/PostponedProjectInfoEditPanel.js',
				__ctxPath + '/js/creditFlow/smallLoan/project/PostponedProjectInfoNavigation.js',//展期编辑
				__ctxPath + '/js/creditFlow/smallLoan/project/SlAlterAccrualRecordEditInfo.js',
				__ctxPath + '/js/creditFlow/smallLoan/project/SlAlterAccrualRecordSeeInfo.js',
				__ctxPath + '/js/creditFlow/smallLoan/project/SlAlterAccrualRecordEditInfoView.js',
				__ctxPath + '/js/creditFlow/smallLoan/project/SlAlterAccrualRecordNavigation.js',
				__ctxPath + '/js/creditFlow/finance/OwnFundIntentView.js',// 款项信息,
				__ctxPath + '/js/creditFlow/smallLoan/finance/EarlyRepaymentSlFundIntent.js',
				__ctxPath + '/js/creditFlow/fund/project/FundFinancePrepaymentForm.js',
				__ctxPath + '/js/creditFlow/smallLoan/project/SlEarlyrepaymentRecordEditInfo.js',
				__ctxPath + '/js/creditFlow/smallLoan/project/SlEarlyrepaymentRecordSeeInfo.js',
				__ctxPath + '/js/creditFlow/smallLoan/project/SlEarlyrepaymentRecordEditInfoView.js',
				__ctxPath + '/js/creditFlow/smallLoan/project/SlEarlyrepaymentRecordNavigation.js',
				__ctxPath + '/js/creditFlow/smallLoan/project/StatisticalElementsSmallloan.js',
				__ctxPath + '/js/creditFlow/smallLoan/project/SlMeetingSummary.js'
		];
		$ImportSimpleJs(jsArr, this.constructPanel, this);
	},// 初始化组件
	constructPanel : function() {
		this.oppositeType = this.record.data.oppositeType;
		this.oppositeID = this.record.data.oppositeId;
		this.projectId = this.record.data.projectId;
		this.projectStatus = this.record.data.projectStatus;
		this.projectMoney = this.record.data.projectMoney;
		this.payProjectMoney = this.record.data.payProjectMoney;
		this.expectedRepaymentDate = this.record.data.expectedRepaymentDate;
		this.payintentPeriod = this.record.data.payintentPeriod;
		this.businessType = this.record.data.businessType;
		this.runId = this.record.data.runId;
		this.taskId = this.record.data.taskId;
		this.operationType=this.record.data.operationType
		if(this.operationType=='MicroLoanBusiness'){
			this.isHidden=false;
		}else{
			this.isHidden=true;
		}
//		Ext.Ajax.request({
//			url : __ctxPath
//					+ "/creditFlow/getMeetingTypeCreditProject.do?runId="
//					+ this.runId,
//			method : 'POST',
//			scope : this,
//			success : function(response) {
//				var result = Ext.util.JSON.decode(response.responseText);
//				switch (parseInt(result.type)) {
//					case 1 :
//						this.isHiddenVote = false;
//						break;
//					case 2 :
//						this.isHiddenMeeting = false;
//						break;
//					case 3 :
//						this.isHiddenVote = false;
//						this.isHiddenMeeting = false;
//						break;
//				};
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
								disabled : !(isGranted('ediSlProjectInfo_' + this.projectStatus)||isGranted('seeSlProjectInfo_'+ this.projectStatus)),
								handler : function() {
									location.href = "#sl_projectInfo_"
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
								disabled : !(isGranted('editslCustomerInfo_'+ this.projectStatus)||isGranted('seeslCustomerInfo_'+ this.projectStatus)),
								handler : function() {
									location.href = "#sl_customerInfo_"
											+ this.flag + this.projectId;
								}
							}]
						}, {
							layout : "form", // 从上往下的布局
							items : [{
								xtype : 'button',
								anchor : '100%',
								text : '03资金款项信息',
								scope : this,
								disabled : !(isGranted('slFinanceInfo_'+ this.projectStatus)||isGranted('seeslFinanceInfo_'+ this.projectStatus)),
								handler : function() {
									location.href = "#sl_financeInfo_"
											+ this.flag + this.projectId;
								}
							}]
						}, {
							layout : "form", // 从上往下的布局
							items : [{
								xtype : 'button',
								anchor : '100%',
								text : '04放款收息表',
								scope : this,
								disabled : !(isGranted('slFundInentInfo_'+ this.projectStatus)||isGranted('seeslFundInentInfo_'+ this.projectStatus)),
								handler : function() {
									location.href = "#sl_fundIntentInfo_"
											+ this.flag + this.projectId;
								}
							}]
						}, {
							layout : "form", // 从上往下的布局
							items : [{
								xtype : 'button',
								anchor : '100%',
								text : '05手续费用收取清单',
								scope : this,
								disabled : !(isGranted('slActualToCharge_'+ this.projectStatus)|| isGranted('seeslActualToCharge_'+ this.projectStatus)),
								handler : function() {
									location.href = "#sl_actualToChargeInfo_"
											+ this.flag + this.projectId;
								}
							}]
						}, {
							layout : "form", // 从上往下的布局
							items : [{
								xtype : 'button',
								anchor : '100%',
								text : '06配偶信息',
								scope : this,
								hidden : this.isHidden,
								disabled : !(isGranted('slSpouseInfo_'+ this.projectStatus)||isGranted('seeslSpouseInfo_'+ this.projectStatus)),
								handler : function() {
									location.href = "#sl_spouseInfo_"
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
						},{
							layout : "form", // 从上往下的布局
							items : [{
								xtype : 'button',
								iconCls : 'btn-detail',
								anchor : '100%',
								text : '审贷会集体决议',
								scope : this,
								disabled : !(isGranted('voteSign_'+ this.projectStatus)||isGranted('seevoteSign_'+ this.projectStatus)),
								handler : function() {
									new SbhProjectInfo({
										runId : this.runId,
										taskId : this.taskId,
										projectStatus : this.projectStatus,
										title : '线上审贷会决议',
										//会签任务的key,如果一个流程有两个会签的情况,并且两个会签的记录都显示在当前列表,则传两个会签节点的key,如："'key1','key2'";
										//只显示一个会签的记录则传相应的会签节点的key。如没有会签，则当前按钮会隐藏。如下代码：
										countersignedTaskKey : 'slcMeettingInPublic'
									}).show();
									/*new SlVoteSignForm({
										runId : this.runId
									}).show();*/
								}
							}]
						},{
							layout : "form", // 从上往下的布局
							items : [{
								xtype : 'button',
								anchor : '100%',
								text : '07第一还款来源',
								scope : this,
								disabled : !(isGranted('repaymentSource_'+ this.projectStatus)||isGranted('seerepaymentSource_'+ this.projectStatus)),
								handler : function() {
									location.href = "#sl_repaymentSource_" + this.flag + this.projectId;
								}
							}]
						},{
							layout : "form", // 从上往下的布局
							items : [{
								xtype : 'button',
								anchor : '100%',
								text : '08共同借款人信息',
								scope : this,
								disabled : !(isGranted('borrowerInfo_'+ this.projectStatus)||isGranted('seeborrowerInfo_'+ this.projectStatus)),
								handler : function() {
									location.href = "#sl_borrowerInfo_"
											+ this.flag + this.projectId;
								}
							}]
						},{
							layout : "form", // 从上往下的布局
							items : [{
								xtype : 'button',
								anchor : '100%',
								text : '09贷款准入原则',
								scope : this,
								disabled : !(isGranted('seeAssuretenet_'+ this.projectStatus)||isGranted('Assuretenet_'+ this.projectStatus)),
								handler : function() {
									location.href = "#sl_procreditAssuretenet_"
											+ this.flag + this.projectId;
								}
							}]
						},{
							layout : "form", // 从上往下的布局
							items : [{
								xtype : 'button',
								anchor : '100%',
								text : '10贷款资料清单',
								scope : this,
								disabled : !(isGranted('CreditMaterials_'+ this.projectStatus)||isGranted('seeCreditMaterials_'+ this.projectStatus)),
								handler : function() {
									location.href = "#sl_procreditMaterials_"
											+ this.flag + this.projectId;
								}
							}]
						}, /*{
							layout : "form", // 从上往下的布局
							items : [{
								xtype : 'button',
								anchor : '100%',
								text : '08第一还款来源',
								scope : this,
								disabled : !isGranted('repaymentSource_'+ this.projectStatus),
								handler : function() {
									location.href = "#sl_repaymentSource_"+ this.flag + this.projectId;
								}
							}]
						},*/ {
							layout : "form", // 从上往下的布局
							items : [{
								xtype : 'button',
								anchor : '100%',
								text : '11担保措施管理',
								scope : this,
								disabled : !(isGranted('mortgageView_'+ this.projectStatus)||isGranted('seemortgageView_'+ this.projectStatus)),
								handler : function() {
									location.href = "#sl_mortgage_" + this.flag
											+ this.projectId;
								}
							}]
						},{
							layout : "form", // 从上往下的布局
							items : [{
								xtype : 'button',
								anchor : '100%',
								text : '18贷款审查审批表',
								scope : this,
								disabled : !(isGranted('mortgageView_'+ this.projectStatus)||isGranted('seemortgageView_'+ this.projectStatus)),
								handler : function() {
									location.href = "#sl_letterAndBookView_" + this.flag
											+ this.projectId;
								}
							}]
						},{
							layout : "form", // 从上往下的布局
							items : [{
								xtype : 'button',
								anchor : '100%',
								text : '19终审意见通知书',
								scope : this,
								disabled : !(isGranted('mortgageView_'+ this.projectStatus)||isGranted('seemortgageView_'+ this.projectStatus)),
								handler : function() {
									location.href = "#sl_smallLoanConfirmNote_" + this.flag
											+ this.projectId;
								}
							}]
						},{
							layout : "form", // 从上往下的布局
							items : [{
								xtype : 'button',
								anchor : '100%',
								text : '20放款通知书',
								scope : this,
								disabled : !(isGranted('mortgageView_'+ this.projectStatus)||isGranted('seemortgageView_'+ this.projectStatus)),
								handler : function() {
									location.href = "#sl_loanLetterAndBookView_" + this.flag
											+ this.projectId;
								}
							}]
						},{
							layout : "form", // 从上往下的布局
							items : [{
								xtype : 'button',
								anchor : '100%',
								text : '13旗下公司',
								scope : this,
								hidden : this.isHidden,
								disabled : !(isGranted('thereunderPanelView_'+ this.projectStatus)||isGranted('seethereunderPanelView_'+ this.projectStatus)),
								handler : function() {
									location.href = "#sl_thereunderPanelView_"
											+ this.flag + this.projectId;
								}
							}]
						}, {
							
							layout : "form", // 从上往下的布局
							items : [{
								xtype : 'button',
								anchor : '100%',
								text : '14财务信息',
								scope : this,
								hidden : this.isHidden,
								disabled : !(isGranted('financeInfo1_'+ this.projectStatus)||isGranted('seefinanceInfo1_'+ this.projectStatus)),
								handler : function() {
									location.href = "#sl_financeInfo1_"
											+ this.flag + this.projectId;
								}
							}]
						}/*, {
							layout : "form", // 从上往下的布局
							items : [{
								xtype : 'button',
								anchor : '100%',
								text : '09风险分析报告',
								scope : this,
								disabled : !isGranted('seeRiskReport_'
										+ this.projectStatus),
								handler : function() {
									location.href = "#sl_riskReport_"
											+ this.flag + this.projectId;
								}
							}]
						},*//*,{
							layout : "form", // 从上往下的布局
							items : [{
								xtype : 'button',
								iconCls : 'btn-detail',
								anchor : '100%',
								text : '主管意见记录',
								scope : this,
								disabled : !isGranted('executiveOpinion_'
												+ this.projectStatus),
								height : 22,
								handler : function() {
									new SlExecutiveOpinionForm({
										projectId : this.projectId,
										businessType : "SmallLoan"
									}).show();
								}
							}]
						}*/]
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
								width : 95
							}]
						}, {
							layout : "form", // 从上往下的布局
							items : [{
								xtype : 'button',
								text : '20展期记录',
								anchor : '100%',
								scope : this,
								disabled : !(isGranted('records_'+ this.projectStatus)||isGranted('SeeRecords_'+ this.projectStatus)),
								handler : function() {
									location.href = "#sl_superviseRecord_"
											+ this.flag + this.projectId;
								}
							}]
						}, {
							layout : "form", // 从上往下的布局
							items : [{
								xtype : 'button',
								text : '21提前还款记录',
								anchor : '100%',
								disabled : !(isGranted('earlyrepaymentRecord_'+ this.projectStatus)||isGranted('seeearlyrepaymentRecord_'+ this.projectStatus)),
								scope : this,
								handler : function() {
									location.href = "#sl_earlyrepaymentRecord_"+ this.flag + this.projectId;
									/*new SlEarlyrepaymentRecordView({
										projectId : this.projectId,
										projectMoney : this.projectMoney,
										payProjectMoney : this.payProjectMoney,
										payintentPeriod : this.payintentPeriod
									}).setVisible(true);*/
								}
							}]
						}, {
							layout : "form", // 从上往下的布局
							items : [{
								xtype : 'button',
								//iconCls : 'btn-detail',
								text : '22利率变更记录',
								anchor : '100%',
								disabled : !(isGranted('aflterAccrualRecord_'+ this.projectStatus)||isGranted('seeaflterAccrualRecord_'+ this.projectStatus)),
								scope : this,
								handler : function() {
									location.href = "#sl_aflterAccrualRecord_"
											+ this.flag + this.projectId;
									/*new SlAlterAccrualRecordView({
										projectId : this.projectId,
										projectMoney : this.projectMoney,
										payProjectMoney : this.payProjectMoney,
										payintentPeriod : this.payintentPeriod
									}).setVisible(true);*/
								}
							}]
						}, {
							layout : "form", // 从上往下的布局
							items : [{
								xtype : 'button',
								//iconCls : 'btn-detail',
								text : '23项目属性分类',
								anchor : '100%',
								disabled : !(isGranted('editProjectPropertyClassification_'+ this.projectStatus)||isGranted('seeProjectPropertyClassification_'+ this.projectStatus)),
								scope : this,
								handler : function() {
									location.href = "#sl_projectPropertyClassification_"
											+ this.flag + this.projectId;
								}
							}]
						} , {
							layout : "form", // 从上往下的布局
							items : [{
								xtype : 'button',
								iconCls : 'btn-detail',
								text : '贷后监管记录',
								anchor : '100%',
								scope : this,
								disabled : !(isGranted('SuperviseIn_'+ this.projectStatus)||isGranted('seeSuperviseIn_'+ this.projectStatus)),
								handler : function() {
									new DesignSupervisionManagePlan({
										projectId : this.projectId,
										isHidden : true
									}).setVisible(true);
								}
							}]
						}, {
							layout : "form", // 从上往下的布局
							items : [{
								xtype : 'button',
								iconCls : 'btn-detail',
								text : '项目归档记录',
								anchor : '100%',
								disabled : !(isGranted('FilingRecord_'+ this.projectStatus)||isGranted('seeFilingRecord_'+ this.projectStatus)),
								scope : this,
								handler : function() {
									new SlFilingRecordView({projectId:this.projectId}).show()
								}
							}]
						}, {
							layout : "form", // 从上往下的布局
							items : [{
								xtype : 'button',
								iconCls : 'btn-detail',
								anchor : '100%',
								text : '线下审贷会情况',
								scope : this,
								disabled : !(isGranted('uploadMeetingSummary_'+ this.projectStatus)||isGranted('seeuploadMeetingSummary_'+ this.projectStatus)),
								handler : function() {
									new SlMeetingSummary({
										projectId : this.projectId,
										businessType : "SmallLoan",
									//  isReadOnly : !isGranted('uploadMeetingSummary_'+ this.projectStatus),flag
										isReadOnly : this.flag=='see' ? true : false,
									//	isHidden : !isGranted('uploadMeetingSummary_'+ this.projectStatus)
										isHidden : this.flag=='see' ? true : false
									}).show();
								}
							}]
						}, {
							layout : "form", // 从上往下的布局
							hidden : this.projectStatus == 8 ? false : true,
							items : [{
								xtype : 'button',
								iconCls : 'btn-detail',
								text : '违约处理详情',
								anchor : '100%',
								disabled : !(isGranted('no_promise_handle_'+ this.projectStatus)||isGranted('seeno_promise_handle_'+ this.projectStatus)),
								scope : this,
								handler : function() {
									var oppositeType = this.oppositeType;
									var oppositeID = this.oppositeID;
									var projectId = this.projectId;
									var isListed = "";
									Ext.Ajax.request({
										url : __ctxPath
												+ '/project/isListedSlSmallloanProject.do',
										params : {
											oppositeType : oppositeType,
											oppositeID : oppositeID,
											projectId : projectId
										},
										method : 'post',
										success : function(resp, op) {
											var obj = Ext.util.JSON
													.decode(resp.responseText);// JSON对象，root为data,通过record对象取数据必须符合"record.data.name"格式
											var isListed = obj.isListed;
											var breachComment = obj.breachComment;
											new SlBreachDetailView({
												isListed : isListed,
												breachComment : breachComment,
												projectId : projectId
											}).show();
										},
										failure : function() {
											Ext.ux.Toast.msg('信息提示',
													'出错，请联系管理员！');
										}
									})

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
						}/*, {
							layout : "form", // 从上往下的布局
							items : [{
								xtype : 'button',
								anchor : '100%',
								text : '12担保合同',
								scope : this,
								disabled : !isGranted('mortgageView_'
										+ this.projectStatus),
								handler : function() {
									location.href = "#sl_mortgage_" + this.flag
											+ this.projectId;
								}
							}]
						}, {
							layout : "form", // 从上往下的布局
							items : [{
								xtype : 'button',
								anchor : '100%',
								text : '13贷款合同',
								scope : this,
								disabled : !isGranted('slContract_'
										+ this.projectStatus),
								handler : function() {
									location.href = "#sl_contract_" + this.flag
											+ this.projectId;
								}
							}]
						}, {
							layout : "form", // 从上往下的布局
							items : [{
								xtype : 'button',
								anchor : '100%',
								text : '10其他文件',
								scope : this,
								disabled : !(isGranted('slOtherFiles_'+ this.projectStatus)||isGranted('seeslOtherFiles_'+ this.projectStatus)),
								handler : function() {
									location.href = "#sl_otherFiles_" + this.flag
											+ this.projectId;
								}
							}]
						}*/, {
							layout : "form", // 从上往下的布局
							items : [{
								xtype : 'button',
								anchor : '100%',
								text : '10借款合同',
								scope : this,
								disabled : !(isGranted('slLoanContract_'+ this.projectStatus)||isGranted('seeslLoanContract_'+ this.projectStatus)),
								handler : function() {
									location.href = "#sl_loanContract_" + this.flag
											+ this.projectId;
								}
							}]
						}, {
							layout : "form", // 从上往下的布局
							items : [{
								xtype : 'button',
								anchor : '100%',
								text : '16尽职调查报告',
								scope : this,
								disabled : !(isGranted('Report_'+ this.projectStatus)||isGranted('seeReport_'+ this.projectStatus)),
								handler : function() {
									location.href = "#sl_report_" + this.flag
											+ this.projectId;
								}
							}]
						},{
							layout : "form", // 从上往下的布局
							items : [{
								xtype : 'button',
								anchor : '100%',
								text : '17风险分析报告',
								scope : this,
								disabled : !(isGranted('riskReport_' + this.projectStatus)||isGranted('seeRiskReport_' + this.projectStatus)),
								handler : function() {
									location.href = "#sl_riskReport_"
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
								disabled : !(isGranted('showFlowImg_'+ this.projectStatus)||isGranted('seeshowFlowImg_'+ this.projectStatus)),
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
								disabled : !(isGranted('seeAgreements_'+ this.projectStatus)||isGranted('Agreements_'+ this.projectStatus)),
								handler : function() {
									new SlProcessRunView({
										runId : this.runId,
										projectStatus : this.projectStatus,
										businessType : "SmallLoan",
										isAutoHeight : false
									}).show();
								}
							}]
						}, /*{
							layout : "form", // 从上往下的布局
							hidden : (this.flag == 'edit'
									&& this.projectStatus != 0
									&& this.projectStatus != 2
									&& this.projectStatus != 3
									&& this.projectStatus != 8
									&& this.projectStatus != 7
									&& isGranted('applyForExtension_'
									+ this.projectStatus) == true)
									? false
									: true,
							items : [{
								xtype : 'button',
								iconCls : 'btn-detail',
								anchor : '100%',
								text : '申请展期',
								scope : this,

								handler : function() {
									var projectId = this.projectId;
									var superviseInFieldset = this.panel.ownerCt.ownerCt.ownerCt
											.getCmpByName("superviseInFieldset");
									var gridPanel = superviseInFieldset.get(0);
									gridPanel.applyfor();
								}
							}]
						},*/ {
							layout : "form", // 从上往下的布局
							hidden : (this.flag == 'edit'
									&& this.projectStatus != 2
									&& this.projectStatus != 3
									&& this.projectStatus != 8
									&& !isGranted('taskHandler_'
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
													idPrefix : "SmallLoanProjectInfo_",
													idPrefix_edit : "SmallLoanProjectInfoEdit_",
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
									&& this.projectStatus != 2
									&& this.projectStatus != 3
									&& this.projectStatus != 5
									&& this.projectStatus != 8
									&& this.projectStatus != 7
									&& !isGranted('pathChange_'
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
										idPrefix : "SmallLoanProjectInfo_",
										idPrefix_edit : "SmallLoanProjectInfoEdit_"
									}).show();
								}
							}]
						}, {
							layout : "form", // 从上往下的布局
							hidden : (this.flag == 'edit'
									&& this.projectStatus != 2
									&& this.projectStatus != 3
									&& this.projectStatus != 8
									&& this.projectStatus != 7
									&& !isGranted('stopPro_'
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
										idPrefix : "SmallLoanProjectInfo_",
										idPrefix_edit : "SmallLoanProjectInfoEdit_"
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