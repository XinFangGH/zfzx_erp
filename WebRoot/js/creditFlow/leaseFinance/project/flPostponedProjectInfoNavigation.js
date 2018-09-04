/**
 * @class PostponedProjectInfoNavigation
 * @description 小贷展期项目信息页面导航
 * @extends Ext.Panel
 */
flPostponedProjectInfoNavigation = Ext.extend(Ext.Panel, {
	bmStatus:0,
	constructor : function(_cfg) {
		if (_cfg.flag) {
			this.flag = _cfg.flag;
		}
		if (_cfg.record) {
			this.record = _cfg.record;
		}
		if (_cfg.slSuperviseRecordId) {
			this.slSuperviseRecordId = _cfg.slSuperviseRecordId;
		}
		Ext.applyIf(this, _cfg);
		this.initUIComponents();
		flPostponedProjectInfoNavigation.superclass.constructor.call(this, {
			region : 'center',
			layout : 'anchor',
			items : []
		});
	},
	initUIComponents : function() {
		var jsArr = [
				__ctxPath + '/js/creditFlow/smallLoan/project/SlVoteSignForm.js',// 会签投票
				/*__ctxPath + '/js/creditFlow/smallLoan/project/SlExecutiveOpinionForm.js',// 主管意见记录
				__ctxPath + '/js/creditFlow/smallLoan/project/SlMeetingSummary.js',// 上传纪要
*/				//__ctxPath + '/js/creditFlow/smallLoan/project/SlProcessRunView.js',// 意见与说明记录
				//__ctxPath + '/js/creditFlow/smallLoan/project/SlFilingRecordView.js',// 归档记录
				__ctxPath + '/js/creditFlow/common/ProjectTaskHandler.js',// 项目换人
				__ctxPath + '/js/creditFlow/common/ProjectPathChange.js',// 流程跳转
				/*__ctxPath + '/js/creditFlow/smallLoan/project/SlBreachDetailView.js',// 违约处理详情
				__ctxPath + '/js/creditFlow/smallLoan/project/SlEarlyrepaymentRecordView.js',// 提前还款记录
				__ctxPath + '/js/creditFlow/smallLoan/finance/EarlyRepaymentSlFundIntent.js',// 提前还款
				__ctxPath + '/js/creditFlow/smallLoan/project/SlEarlyrepaymentDetailView.js',*/
				__ctxPath + '/js/creditFlow/finance/SlActualToChargeVM.js',// 经办费用清单
				/*__ctxPath + '/js/creditFlow/smallLoan/project/SlAlterAccrualRecordView.js',// 利率变更记录
				__ctxPath + '/js/creditFlow/smallLoan/project/SlAlterAccrualDetailView.js',// 利率变更详情
				__ctxPath + '/js/creditFlow/smallLoan/finance/AlterAccrualSlFundIntent.js',// 利率变更款项
*/				//__ctxPath + '/js/creditFlow/smallLoan/project/DesignSupervisionManagePlan.js',// 监管计划
				__ctxPath + '/js/creditFlow/smallLoan/project/SlSupervisonRecordView.js'// 监管
				/*__ctxPath + '/js/creditFlow/smallLoan/finance/BorrowerInfo.js',//共同借款人信息
				__ctxPath + '/js/creditFlow/smallLoan/finance/FinanceEarlyRepaymentPanel.js',
				__ctxPath + '/js/creditFlow/smallLoan/finance/FinanceAlterAccrualPanel.js',
				__ctxPath + '/js/creditFlow/guarantee/contract/LetterAndBookView.js'*/
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
		this.operationType=this.record.data.operationType
		if(this.operationType=='MicroLoanBusiness'||this.operationType=="DirectLeaseFinance"){
			this.isHidden=false;
		}else{
			this.isHidden=true;
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
							columnWidth : .12
						},
						items : [{
							layout : "form", // 从上往下的布局
							columnWidth : .08,
							items : [{
								xtype : 'panel',
								border : false,
								bodyStyle : 'margin-left:0px;margin-top:5px',
								width : 95,
								html : '<B><font class="x-myZW-fieldset-title">【展期文书】：</font></B>'
							}]
						}, /*{
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
						}, */{
							layout : "form", // 从上往下的布局
							items : [{
								xtype : 'button',
								anchor : '100%',
								text : '01展期审批书',
								scope : this,
								disabled : !(isGranted('_applyForPostponed_fl'+ this.projectStatus+this.bmStatus)||isGranted('_seeapplyForPostponed_fl'+ this.projectStatus+this.bmStatus)),
								handler : function() {
									location.href = "#sl_applyForPostponed_"
											+ this.flag + this.projectId;
								}
							}]
						}, {
							layout : "form", // 从上往下的布局
							items : [{
								xtype : 'button',
								anchor : '100%',
								text : '02展期协议书',
								scope : this,
								//hidden : this.isHidden,
								disabled : !(isGranted('_postponedContract_fl'+ this.projectStatus+this.bmStatus)||isGranted('_seepostponedContract_fl'+ this.projectStatus+this.bmStatus)),
								handler : function() {
									location.href = "#sl_postponedContract_"
											+ this.flag + this.projectId;
								}
							}]
						}]
					}, {
						columnWidth : 1,
						layout : 'column',
						hidden : true,
						defaults : {
							anchor : '100%',
							style : "margin-left:5px;margin-top:5px",
							columnWidth : .12
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
							hidden : true,
							items : [{
								xtype : 'button',
								iconCls : 'btn-detail',
								anchor : '100%',
								text : '展期审贷会集体决议',
								scope : this,
								disabled : !(isGranted('_zqsdhjtjy_fl'+ this.projectStatus+this.bmStatus)||isGranted('_seezqsdhjtjy_fl'+ this.projectStatus+this.bmStatus)),
								handler : function() {
									var slSuperviseRecordId = this.slSuperviseRecordId;
									Ext.Ajax.request({
										url : __ctxPath + '/flow/getByBusinessTypeProcessNameTableIdProcessRun.do',
										params : {
											slSuperviseRecordId : slSuperviseRecordId,
											businessType : 'FlExhibitionBusiness',
											processName : 'leaseFinancePostponedFlow'
										},
										method : 'post',
										success : function(response, request) {
											obj = Ext.util.JSON.decode(response.responseText);
											var runId = obj.data.runId;
											var piId = obj.data.piId;
											if(obj.success){
												new SlVoteSignForm({
													runId : runId
												}).show();
											}else{
												Ext.ux.Toast.msg('操作信息', '查询runId操作失败!');
											}
										},
										failure : function() {
											Ext.ux.Toast.msg('信息提示', '出错，请联系管理员！');
										}
									})
									/*new SlVoteSignForm({
										runId : this.runId
									}).show();*/
								}
							}]
						}]
					}, {
						columnWidth : 1,
						layout : 'column',
						defaults : {
							anchor : '100%',
							style : "margin-left:5px;margin-top:5px",
							columnWidth : .12
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
								text : '展期流程示意图',
								scope : this,
								disabled : !(isGranted('_showZQFlowImg_fl'+ this.projectStatus+this.bmStatus)||isGranted('_seeshowZQFlowImg_fl'+ this.projectStatus+this.bmStatus)),
								handler : function() {
									var slSuperviseRecordId = this.slSuperviseRecordId;
									Ext.Ajax.request({
										url : __ctxPath + '/flow/getByBusinessTypeProcessNameTableIdProcessRun.do',
										params : {
											slSuperviseRecordId : slSuperviseRecordId,
											businessType : 'FlExhibitionBusiness',
											processName : 'leaseFinancePostponedFlow'
										},
										method : 'post',
										success : function(response, request) {
											obj = Ext.util.JSON.decode(response.responseText);
											var runId = obj.data.runId;
											var piId = obj.data.piId;
											if(obj.success){
												showFlowImgWin(runId);
											}else{
												Ext.ux.Toast.msg('操作信息', '查询runId操作失败!');
											}
										},
										failure : function() {
											Ext.ux.Toast.msg('信息提示', '出错，请联系管理员！');
										}
									})
									//showFlowImgWin(this.record.data.runId);
								}
							}]
						}, {
							layout : "form", // 从上往下的布局
							items : [{
								xtype : 'button',
								iconCls : 'btn-advice',
								anchor : '100%',
								text : '展期意见与说明记录',
								scope : this,
								disabled : !(isGranted('_zqRecords_fl'+ this.projectStatus+this.bmStatus)||isGranted('_seezqRecords_fl'+ this.projectStatus+this.bmStatus)),
								handler : function() {
									var slSuperviseRecordId = this.slSuperviseRecordId;
									Ext.Ajax.request({
										url : __ctxPath + '/flow/getByBusinessTypeProcessNameTableIdProcessRun.do',
										params : {
											slSuperviseRecordId : slSuperviseRecordId,
											businessType : 'FlExhibitionBusiness',
											processName : 'leaseFinancePostponedFlow'
										},
										method : 'post',
										success : function(response, request) {
											obj = Ext.util.JSON.decode(response.responseText);
											var runId = obj.data.runId;
											var piId = obj.data.piId;
											if(obj.success){
												new SlProcessRunView({
													runId : runId,
													projectStatus : this.projectStatus,
													businessType : this.businessType,
													isAutoHeight : false
												}).show();
											}else{
												Ext.ux.Toast.msg('操作信息', '查询runId操作失败!');
											}
										},
										failure : function() {
											Ext.ux.Toast.msg('信息提示', '出错，请联系管理员！');
										}
									})
									/*new SlProcessRunView({
										runId : this.runId,
										projectStatus : this.projectStatus,
										businessType : "SmallLoan",
										isAutoHeight : false
									}).show();*/
								}
							}]
						}, {
							layout : "form", // 从上往下的布局
							/*hidden : (this.flag == 'edit'
									&& this.projectStatus != 2
									&& this.projectStatus != 3
									&& this.projectStatus != 8
									&& !isGranted('taskHandler_'
									+ this.projectStatus) == false)
									? false
									: true,*/
							items : [{
								xtype : 'button',
								iconCls : 'btn-detail',
								anchor : '100%',
								text : '展期任务换人',
								scope : this,
								hidden : this.flag=='see'?true:false,
								disabled : !(isGranted('_zqTaskChange_fl'+ this.projectStatus + this.bmStatus)||isGranted('_seezqTaskChange_fl'+ this.projectStatus+this.bmStatus)),
								handler : function() {
									var slSuperviseRecordId = this.slSuperviseRecordId;
									Ext.Ajax.request({
										url : __ctxPath + '/flow/getByBusinessTypeProcessNameTableIdProcessRun.do',
										params : {
											slSuperviseRecordId : slSuperviseRecordId,
											businessType : 'FlExhibitionBusiness',
											processName : 'leaseFinancePostponedFlow'
										},
										method : 'post',
										success : function(response, request) {
											obj = Ext.util.JSON.decode(response.responseText);
											var runId = obj.data.runId;
											var piId = obj.data.piId;
											if(obj.success){
												if(piId==null||piId==''||piId=='null'){
													Ext.ux.Toast.msg('提示信息', '该展期流程已经结束,不能进行展期任务换人操作!');
													return;
												}else{
													new ProjectTaskHandler({
														record : obj,
														userName : this.userName,
														idPrefix : "PostponedProjectInfo_",
														idPrefix_edit : "PostponedProjectInfoEdit_",
														isActivityComboEdit : false
													}).show();
												}
											}else{
												Ext.ux.Toast.msg('操作信息', '查询runId操作失败!');
											}
										},
										failure : function() {
											Ext.ux.Toast.msg('信息提示', '出错，请联系管理员！');
										}
									})
									/*new ProjectTaskHandler({
										record : this.record,
										userName : this.userName,
										idPrefix : "PostponedProjectInfo_",
										idPrefix_edit : "PostponedProjectInfoEdit_",
										isActivityComboEdit : this.isActivityComboEdit
									}).show();*/
								}
							}]
						}, {
							layout : "form", // 从上往下的布局
							/*hidden : (this.flag == 'edit'
									&& this.projectStatus != 2
									&& this.projectStatus != 3
									&& this.projectStatus != 5
									&& this.projectStatus != 8
									&& this.projectStatus != 7
									&& !isGranted('pathChange_'
									+ this.projectStatus) == false)
									? false
									: true,*/
							items : [{
								xtype : 'button',
								iconCls : 'btn-detail',
								anchor : '100%',
								text : '展期流程跳转',
								hidden : this.flag=='see'?true:false,
								disabled : !(isGranted('_zqTaskJump_fl'+ this.projectStatus+this.bmStatus)||isGranted('_seezqTaskJump_fl'+ this.projectStatus+this.bmStatus)),
								scope : this,
								handler : function() {
									var slSuperviseRecordId = this.slSuperviseRecordId;
									Ext.Ajax.request({
										url : __ctxPath + '/flow/getByBusinessTypeProcessNameTableIdProcessRun.do',
										params : {
											slSuperviseRecordId : slSuperviseRecordId,
											businessType : 'ExhibitionBusiness',
											processName : 'smallLoanPostponedFlow'
										},
										method : 'post',
										success : function(response, request) {
											obj = Ext.util.JSON.decode(response.responseText);
											var runId = obj.data.runId;
											var piId = obj.data.piId;
											if(obj.success){
												if(piId==null||piId==''||piId=='null'){
													Ext.ux.Toast.msg('提示信息', '该展期流程已经结束,不能进行展期流程跳转操作!');
													return;
												}else{
													new ProjectPathChange({
														record : obj,
														idPrefix : "PostponedProjectInfo_",
														idPrefix_edit : "PostponedProjectInfoEdit_"
													}).show();
												}
											}else{
												Ext.ux.Toast.msg('操作信息', '查询runId操作失败!');
											}
										},
										failure : function() {
											Ext.ux.Toast.msg('信息提示', '出错，请联系管理员！');
										}
									})
									/*new ProjectPathChange({
										record : this.record,
										idPrefix : "PostponedProjectInfo_",
										idPrefix_edit : "PostponedProjectInfoEdit_"
									}).show();*/
								}
							}]
						}, {
							layout : "form", // 从上往下的布局
							/*hidden : (this.flag == 'edit'
									&& this.projectStatus != 2
									&& this.projectStatus != 3
									&& this.projectStatus != 8
									&& this.projectStatus != 7
									&& !isGranted('stopPro_'
									+ this.projectStatus) == false)
									? false
									: true,*/
							items : [{
								xtype : 'button',
								iconCls : 'btn-detail',
								anchor : '100%',
								text : '展期项目终止',
								hidden : this.flag=='see'?true:false,
								disabled : !(isGranted('_zqTaskStop_fl'+ this.projectStatus+this.bmStatus)||isGranted('_seezqTaskStop_fl'+ this.projectStatus+this.bmStatus)),
								scope : this,
								handler : function() {
									var slSuperviseRecordId = this.slSuperviseRecordId;
									Ext.Ajax.request({
										url : __ctxPath + '/flow/getByBusinessTypeProcessNameTableIdProcessRun.do',
										params : {
											slSuperviseRecordId : slSuperviseRecordId,
											businessType : 'FlExhibitionBusiness',
											processName : 'leaseFinancePostponedFlow'
										},
										method : 'post',
										success : function(response, request) {
											obj = Ext.util.JSON.decode(response.responseText);
											var runId = obj.data.runId;
											var piId = obj.data.piId;
											if(obj.success){
												if(piId==null||piId==''||piId=='null'){
													Ext.ux.Toast.msg('提示信息', '该展期流程已经结束,不能进行展期项目终止操作!');
													return;
												}else{
													new ProjectStop({
														record : obj,
														idPrefix : "PostponedProjectInfo_",
														idPrefix_edit : "PostponedProjectInfoEdit_"
													}).show();
												}
											}else{
												Ext.ux.Toast.msg('操作信息', '查询runId操作失败!');
											}
										},
										failure : function() {
											Ext.ux.Toast.msg('信息提示', '出错，请联系管理员！');
										}
									})
									/*new ProjectStop({
										record : this.record,
										idPrefix : "PostponedProjectInfo_",
										idPrefix_edit : "PostponedProjectInfoEdit_"
									}).show();*/
								}
							}]
						}]
					}]
				});
				this.add(this.panel);
				this.doLayout();
	}
});