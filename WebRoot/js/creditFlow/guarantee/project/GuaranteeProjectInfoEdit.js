/**
 * @author lisl
 * @class GuaranteeProjectInfoEdit
 * @description 担保项目信息
 * @extends Ext.Panel
 */
GuaranteeProjectInfoEdit = Ext.extend(Ext.Panel, {
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		this.initUIComponents();
		GuaranteeProjectInfoEdit.superclass.constructor.call(this, {
			tbar : this.toolbar,
			border : false,
			autoScroll : true,
			height : document.body.clientHeight - 245,
			items : []
		});
	},
	initUIComponents : function() {
		var jsArr = [
				__ctxPath + '/js/creditFlow/guarantee/enterpriseBusiness/enterpriseBusinessUI.js',// 项目基本信息
				__ctxPath + '/js/creditFlow/guarantee/enterpriseBusiness/extEnterpriseBusinessUI.js',//集团版项目基本信息   add  by  gao
				__ctxPath + '/js/commonFlow/ExtUD.Ext.js',
				__ctxPath + '/js/creditFlow/finance/detailView.js',// 资金流水详细
				__ctxPath + '/js/creditFlow/finance/GuaranteeSlFundIntentViewVM.js',
				__ctxPath + '/js/creditFlow/guarantee/mortgage/GLOpponentMortgageTransact.js',// 反担保措施
			//	__ctxPath + '/js/creditFlow/mortgage/BaozMortgageView.js',// 反担保措施
				__ctxPath + '/js/creditFlow/finance/SlActualToChargeVM.js',// 经办费用清单
				__ctxPath + '/js/creditFlow/finance/SlActualToChargePremiumVM.js',// 保费
				__ctxPath + '/js/creditFlow/finance/SlActualToChargeForm.js',
				__ctxPath + '/js/creditFlow/finance/chargeeditQlideCheck.js',
				__ctxPath + '/js/creditFlow/guarantee/enterpriseBusiness/MiddleSupervision.js',// 保中监管
				__ctxPath + '/js/creditFlow/smallLoan/contract/BatchSignThirdContractWindow.js',// 批量签署反担保措施合同
				__ctxPath + '/js/creditFlow/assuretenet/SlProcreditAssuretenetedForm.js',// 担保准入原则
				__ctxPath + '/js/creditFlow/guarantee/materials/SlEnterPriseProcreditMaterialsView.js',// 材料担保
				__ctxPath + '/js/creditFlow/guarantee/enterpriseBusiness/EnterpriseEvaluation.js', // 企业评估报告
				__ctxPath + '/js/creditFlow/report/SlReportView.js',// 调查报告
				__ctxPath + '/js/creditFlow/report/SlRiskReportView.js',// 风险综合分析报告
				__ctxPath+'/js/creditFlow/mortgage/luoshiMortgageWindow.js',//落实反担保措施
				__ctxPath + '/js/creditFlow/guarantee/contract/LetterAndBookView.js',// 担保意向书、对外担保承诺函
				__ctxPath + '/js/creditFlow/guarantee/guaranteefinance/accountBankManage/accountBankManageTreeWin.js',
				__ctxPath + '/js/creditFlow/guarantee/enterpriseBusiness/guaranteeModel/BankGuaranteeMoneyrelease.js',// 保证金支付情况
		__ctxPath + '/js/creditFlow/finance/gt_FundIntent_formulate_editGrid.js'//银行放款收息
		];
		$ImportSimpleJs(jsArr, this.constructPanel, this);
		this.toolbar = new Ext.Toolbar({
			items : ['->', {
				iconCls : 'btn-save',
				text : '保存',
				scope : this,
				handler : this.saveAllDatas
			}]
		})
	},// 初始化组件
	constructPanel : function() {
		this.projectId = this.record.data.projectId;
		this.projectStatus = this.record.data.projectStatus;
		this.bmStatus = this.record.data.bmStatus;
		this.activityName = this.record.data.activityName;
		this.oppositeType = this.record.data.oppositeType;
		this.runId = this.record.data.runId;
		this.taskId = this.record.data.taskId;
		this.flowType = this.record.data.flowType;
		this.operationType = this.record.data.operationType;
		this.isHiddenZM = true;
		this.index = 18;
		if (this.flowType == 'zmNormalFlow') {
			this.isHiddenZM = false;
			this.index = 20;
		}
		// 项目控制信息授权
		if (isGranted('ediGtProjectInfo_gt' + this.projectStatus
				+ this.bmStatus)) {
			this.projectInfoGranted = {
				isDiligenceReadOnly : true
			};
		} else {
			this.projectInfoGranted = {
				isAllReadOnly : true
			};
		}
		// 项目控制信息
//		this.projectInfo = new enterpriseBusinessProjectInfoPanel(this.projectInfoGranted);
//		alert("isGroup=="+isGroup+"typeof(isGroup)=="+typeof(isGroup));
		if("true"==isGroup){//isGroup 为全局变量，在index.jsp中初始化赋值，add by gao
			this.projectInfo = new extEnterpriseBusinessProjectInfoPanel({
			   isDiligenceReadOnly : true,
			   isMineNameEditable:true
		    });
		}else{
			this.projectInfo = new enterpriseBusinessProjectInfoPanel(this.projectInfoGranted);
		}
		this.customerInfo = "";
		this.customerTitle = "企业客户信息";
		// 客户信息授权
		if (isGranted('ediGtCustomerInfo_gt' + this.projectStatus
				+ this.bmStatus)) {
			this.personInfoGranted = {
				isPersonNameReadOnly : true
			};
			this.enterpriseInfoGranted = {
				projectId : this.projectId,
				bussinessType : 'Guarantee',
				isHidden : true,
				isEnterprisenameReadonly : true,
				isEnterpriseShortNameReadonly : true
			};
		} else {
			this.personInfoGranted = {
				isAllReadOnly : true
			};
			this.enterpriseInfoGranted = {
				projectId : this.projectId,
				isHidden : true,
				bussinessType : 'Guarantee',
				isAllReadOnly : true
			};
		}
		// 客户信息
		if (this.oppositeType == 'person_customer') {
			this.customerInfo = new ExtUD.Ext.PeerPersonMainInfoPanel(this.personInfoGranted);
			this.customerTitle = "个人客户信息";
		} else if (this.oppositeType == 'company_customer') {
			this.customerInfo = new ExtUD.Ext.PeerMainInfoPanel(this.enterpriseInfoGranted);
		}
		this.bankInfo = new ExtUD.Ext.BankInfoPanel({
			isAllReadOnly : !isGranted('ediGtBankInfo_gt' + this.projectStatus
					+ this.bmStatus)
		});
		// 资金款项信息
		this.financeInfo = new ProjectInfoGuaranteeFinancePanel({
			isAllReadOnly : !isGranted('ediGtFinanceInfo_gt'
					+ this.projectStatus + this.bmStatus),
			isHiddenPanel : !isGranted('gtFinanceBaseInfo_gt' + this.projectStatus + this.bmStatus)
		});
		// 款项计划
		this.fundIntent = new GuaranteeSlFundIntentViewVM({
			projectId : this.projectId,
			businessType : 'Guarantee',
			isHiddenAddBtn : !isGranted('addGtFundInent_gt'
					+ this.projectStatus + this.bmStatus),
			isHiddenDelBtn : !isGranted('delGtFundInent_gt'
					+ this.projectStatus + this.bmStatus),
			isHiddenCanBtn : !isGranted('canGtFundInent_gt'
					+ this.projectStatus + this.bmStatus),
			isHiddenResBtn : !isGranted('resGtFundInent_gt'
					+ this.projectStatus + this.bmStatus),
			enableEdit : isGranted('ediGtFundInent_gt' + this.projectStatus
					+ this.bmStatus),
			isHiddenTitle : true,
			isHiddenPanel : !isGranted('gtActualToCharge_gt' + this.projectStatus + this.bmStatus)
		});
		// 经办费用清单
		this.actualToCharge = new SlActualToChargeVM({
			projId : this.projectId,
			businessType : 'Guarantee',
			isHiddenAddBtn : !isGranted('addGtActualToCharge_gt'
					+ this.projectStatus + this.bmStatus),
			isHiddenDelBtn : !isGranted('delGtActualToCharge_gt'
					+ this.projectStatus + this.bmStatus),
			enableEdit : isGranted('ediGtActualToCharge_gt'
					+ this.projectStatus + this.bmStatus),
			isHiddenTitle : true

		});
		//this.projectInfoFinance=new ProjectInfoGuaranteeFinancePanel({});
		this.slActualToChargePremiumVM=new SlActualToChargePremiumVM({
			projId : this.projectId,
			businessType : 'Guarantee',
			object : this.financeInfo
		});
			// 担保准入原则
		this.slProcreditAssuretenetedForm = new SlProcreditAssuretenetedForm({
			projectId : this.projectId,
			businessType : this.businessType,
			isEditRiskmanageropinion : isGranted('businessManagerAgreement_gt'
					+ this.projectStatus + this.bmStatus),
			isEditBbusinessmanageropinion : isGranted('riskManagerAgreement_gt'
					+ this.projectStatus + this.bmStatus)
		});
		// 担保材料清单
		this.slProcreditMaterialsView = new SlEnterPriseProcreditMaterialsView({
			projectId : this.projectId,
			businessType : 'Guarantee',
			isProjectInfoEdit : true,
			isHidden_materials : !isGranted('businessManagerOperation_gt'
					+ this.projectStatus + this.bmStatus),
			isHiddenArchive : !isGranted('archiveConfirmOperation_gt'
					+ this.projectStatus + this.bmStatus)
		});
		// 客户资信评估
		this.enterpriseEvaluation = new EnterpriseEvaluationGuarantee({
			projectId : this.projectId,
			isHidden : !isGranted('ediEnterpriseEvaluation_gt'
					+ this.projectStatus + this.bmStatus)
		});
		// 委托担保合同
		this.contractView = new SlContractView({
			projId : this.projectId,
			businessType : 'Guarantee',
			htType : 'guaranteeContract',
			isHiddenAffrim : false,
			isSignHidden : false,
			islcEdit : isGranted('legalCheckGt_gt' + this.projectStatus
					+ this.bmStatus),
			isqsEdit : isGranted('issignGt_gt' + this.projectStatus
					+ this.bmStatus),
			isgdEdit : isGranted('isRecordGt_' + this.projectStatus
					+ this.bmStatus),
			isHiddenAddBtn : !isGranted('addGtContract_gt' + this.projectStatus
					+ this.bmStatus),
			isHiddenEdiBtn : !isGranted('ediGtContract_gt' + this.projectStatus
					+ this.bmStatus),
			isHiddenDelBtn : !isGranted('delGtContract_gt' + this.projectStatus
					+ this.bmStatus)
		});

		// 保证金支付情况
		this.creditlineRelease = new BankGuaranteeMoneyrelease({
			projectId : this.projectId,
			isReadOnlyFrozen : !isGranted('editFrozenBankMargin_gt'
					+ this.projectStatus + this.bmStatus),
			isReadOnlyRelease : !isGranted('editReleaseBankMargin_gt'
					+ this.projectStatus + this.bmStatus),
			frame1 : false
		});
		//银行放款收息表
	/*	this.customerRepaymentPlan = new CustomerRepaymentPlan({
			projectId : this.projectId,
			isHidden : !isGranted('ediCustomerRepayment_gt'
					+ this.projectStatus + this.bmStatus)
		});*/
		
		this.gridPanel = new gt_FundIntent_formulate_editGrid({
			projectId : this.projectId,
			isHidden : !isGranted('ediCustomerRepayment_gt'
					+ this.projectStatus + this.bmStatus),
			businessType : 'Guarantee'
			
		});
		// 尽职调查报告
		this.slReportView = new SlReportView({
			projectId : this.projectId,
			businessType : 'Guarantee',
			isHiddenAffrim_report : false,
			isHidden_report : !isGranted('businessManagerOperation_report_gt'
					+ this.projectStatus + this.bmStatus),
			isgdEdit : isGranted('archiveConfirmOperation_report_gt'
					+ this.projectStatus + this.bmStatus)
		});
		// 风险分析报告
		this.slRiskReportView = new SlRiskReportView({
			projectId : this.projectId,
			businessType : 'Guarantee',
			isHidden_riskReport : !isGranted('riskManagerOperation_gt'
					+ this.projectStatus + this.bmStatus),
			isgdEdit_riskReport : isGranted('archiveConfirmOperation_riskReport_gt'
					+ this.projectStatus + this.bmStatus),
			isHiddenAffrim_riskReport : false
		});
		// 拟担保意向书
		this.letterAndBookView = new LetterAndBookView({
			projectId : this.projectId,
			businessType : 'Guarantee',
			LBTemplate : 'simulateEnterpriseBook',
			isHidden : !isGranted('LetterAndBook_gt' + this.projectStatus
					+ this.bmStatus),
			isRecordHidden : false,
			isGdEdit : isGranted('ArchiveFiles_gt' + this.projectStatus
					+ this.bmStatus)
		});
		// 对外担保承诺函
		this.letterAndBookView1 = new LetterAndBookView({
			projectId : this.projectId,
			businessType : 'Guarantee',
			LBTemplate : 'assureCommitmentLetter',
			isHidden : !isGranted('LetterAndBook1_gt' + this.projectStatus
					+ this.bmStatus),
			isRecordHidden : false,
			isGdEdit : isGranted('ArchiveFiles1_gt' + this.projectStatus
					+ this.bmStatus)
		});
		// 股东会决议文档
		this.letterAndBookView2 = new LetterAndBookView({
			projectId : this.projectId,
			businessType : 'Guarantee',
			LBTemplate : 'partnerMeetingResolution',
			isHiddenPanel : this.isHidden,
			isHidden : !isGranted('LetterAndBook2_gt' + this.projectStatus
					+ this.bmStatus),
			isRecordHidden : false,
			isGdEdit : isGranted('ArchiveFiles2_gt' + this.projectStatus
					+ this.bmStatus)
		});
		this.spsLetterAndBookView = new LetterAndBookView({
	    	projectId : this.projectId,
	    	businessType : 'Guarantee',
	    	LBTemplate : 'SPBMB',
	    	isHidden : false,
	    	isfwHidden : true,
	    	isfwEdit : true
	    });//审批表
		//客户借款合同
		this.uploads = new uploads({
			projectId : this.projectId,
			isgdHidden : false,
			isHidden : !isGranted('editFiles_gt' + this.projectStatus
					+ this.bmStatus),
			isgdEdit : isGranted('upload_archive1' + this.projectStatus
					+ this.bmStatus),
			isJKDB : true,// 标识是否客户借款合同及担保合同，true为uploads方法中上传的文件类型为setname，否则为titleName
			tableName : 'gl_borrow_guarantee',
			typeisfile : 'typeisglborrowguarantee',
			businessType : 'Guarantee',
			uploadsSize : 15
		})
		//银行担保合同
		this.uploads1 = new uploads({
			projectId : this.projectId,
			isgdHidden : false,
			isHidden : !isGranted('editDbContract_gt' + this.projectStatus
					+ this.bmStatus),
			isgdEdit : isGranted('recordDbContract_gt' + this.projectStatus
					+ this.bmStatus),
			isJKDB : true,// 标识是否客户借款合同及担保合同，true为uploads方法中上传的文件类型为setname，否则为titleName
			tableName : 'gl_db_guarantee',
			typeisfile : 'typeisgldbguarantee',
			businessType : 'Guarantee',
			uploadsSize : 15
		})
		this.uploads2 = new uploads({
			projectId : this.projectId,
			businessType : 'Guarantee',
			isgdHidden : false,
			isHidden : !isGranted('upload_edit_gt' + this.projectStatus
					+ this.bmStatus),
			isgdEdit : isGranted('upload_archive_gt' + this.projectStatus
					+ this.bmStatus),
			tableName : 'typeisdbzrjchsmj',
			uploadsSize : 1
		});
		this.uploads3 = new uploads({
			projectId : this.projectId,
			businessType : 'Guarantee',
			isNotOnlyFile : false,
			isHiddenColumn : false,
			isDisabledButton : false,
			isgdHidden : false,
			setname : '审保会决议文档',
			titleName : '审保会决议文档',
			tableName : 'typeisdbsbhjywd',
			uploadsSize : 1,
			isHidden : !isGranted('upload_edit_sbh_gt' + this.projectStatus
					+ this.bmStatus),
			isgdEdit : isGranted('upload_archive_sbh_gt' + this.projectStatus
					+ this.bmStatus)
		});
		// 保中监管
		this.middleSupervision = new MiddleSupervision({
			projectId : this.projectId,
			isHidden : !isGranted('middleSupervision_gt' + this.projectStatus
					+ this.bmStatus)
		});
		// 担保措施
		//this.mortgageView = new DZYMortgageView({
		this.mortgageView = new DZYMortgageView({
			titleText : '抵质押担保',
			projectId : this.projectId,
			businessType : 'Guarantee',
			isSignHidden : false,
			isSeeContractHidden : false,
			isAfterContract : true,
			isHiddenInArchiveConfirm : false,
			isHiddenAddBtn : !isGranted('addMortgage_gt' + this.projectStatus
					+ this.bmStatus),
			isHiddenDelBtn : !isGranted('delMortgage_gt' + this.projectStatus
					+ this.bmStatus),
			isHiddenEdiBtn : !isGranted('ediMortgage_gt' + this.projectStatus
					+ this.bmStatus),
			isHiddenDelContractBtn : !isGranted('delContract_gt'
					+ this.projectStatus + this.bmStatus),
			isHiddenEdiContractBtn : !isGranted('ediContract_gt'
					+ this.projectStatus + this.bmStatus),
			isHiddenAddContractBtn : !isGranted('addContract_gt'
					+ this.projectStatus + this.bmStatus),
			isblHidden : false,
			isblEdit : isGranted('mortgageIsTransact_gt' + this.projectStatus
					+ this.bmStatus),
			isKS : true,
			isHiddenQSBtn : false,
			isqsEdit : isGranted('mortgageIssign_gt' + this.projectStatus
					+ this.bmStatus),
			isgdHidden : false,
			isgdEdit : isGranted('mortgageArchiveConfirm_gt'
					+ this.projectStatus + this.bmStatus),
			isHiddenRelieve : false,
			isRelieveEdit : isGranted('mortgageUnchain_gt' + this.projectStatus
					+ this.bmStatus),
			isfwEdit : isGranted('mortgageLegalCheck_gt' + this.projectStatus
					+ this.bmStatus)
		});
		this.baozMortgageView = new BaozMortgageView({
			projectId : this.projectId,
			titleText : '保证担保',
			businessType : 'Guarantee',
			isHiddenAddContractBtn:false,
			isHiddenDelContractBtn:false,
			isHiddenEdiContractBtn:false,
			isHiddenRelieve:false,
			isblHidden:false,
			isgdHidden:false,
			isHiddenAddBtn:false,
			isHiddenDelBtn:false,
			isHiddenEdiBtn:false,
			isgdEdit : true
		});

		this.outPanel = new Ext.Panel({
			modal : true,
			buttonAlign : 'center',
			layout : 'form',
			border : false,
			frame : true,
			defaults : {
				anchor : '100%',
				xtype : 'fieldset',
				columnWidth : 1,
				collapsible : true,
				autoHeight : true
			},
			labelAlign : "right",
			items : [{
				title : '<a name="gl_projectBaseInfo_edit' + this.projectId
						+ '"><font color="red">01</font>项目控制信息</a>',
				hidden : !isGranted('gtProjectInfo_gt' + this.projectStatus + this.bmStatus),
				items : [this.projectInfo]
			}, {
				title : '<a name ="gl_customerInfo_edit' + this.projectId
						+ '"><font color="red">02</font>' + this.customerTitle
						+ '</a>',
				hidden : !isGranted('gtCustomerInfo_gt' + this.projectStatus + this.bmStatus),
				items : [this.customerInfo]
			}, {
				title : '<a name="gl_bankInfo_edit' + this.projectId
						+ '"><font color="red">03</font>对接银行信息</a>',
				hidden : !isGranted('gtBankInfo_gt' + this.projectStatus + this.bmStatus),
				items : [this.bankInfo]
			}, {
				title : '<a name="gl_financeInfo_edit' + this.projectId
						+ '"><font color="red">04</font>资金款项信息</a>',
				hidden : !isGranted('gtFinanceBaseInfo_gt' + this.projectStatus + this.bmStatus)&&!isGranted('gtActualToCharge_gt' + this.projectStatus + this.bmStatus),
				bodyStyle : 'padding-top:5px',
				items : [this.financeInfo, this.slActualToChargePremiumVM,{
					xtype : 'panel',
					hidden : !isGranted('gtActualToCharge_gt' + this.projectStatus + this.bmStatus),
					border : false,
					bodyStyle : 'margin-bottom:5px',
					html : '<br/><B><font class="x-myZW-fieldset-title"><a name="gl_actualToChargeInfo_edit'
							+ this.projectId
							+ '">【<font color="red">04</font>手续收支清单】</a></font></B><font class="x-myZW-fieldset-title">（</font>颜色预警：<font color=red>逾期款项</font>&nbsp;&nbsp<font style="line-height:20px">未结清项</font>&nbsp;&nbsp<font color=gray>已结清项</font><font class="x-myZW-fieldset-title" >）：</font>'
				}, this.actualToCharge]
			}, {
				title : '<a name="gl_creditlineRelease_edit' + this.projectId
						+ '"><font color="red">05</font>保证金支付情况</a>',
				hidden : !isGranted('seeBankMargin_gt' + this.projectStatus + this.bmStatus),
				items : [this.creditlineRelease]
			}, {
				title : '<a name="gl_customerRepaymentPlan_edit' + this.projectId
						+ '"><font color="red">06</font>银行放款收息表</a>',
				hidden : !isGranted('customerRepayment_gt' + this.projectStatus + this.bmStatus),
				items : [this.gridPanel]
			}, {
				title : '<a name="gl_assuretenetInfo_edit' + this.projectId
						+ '"><font color="red">07</font>担保准入原则</a>',
				hidden : !isGranted('seeAssuretenet_gt' + this.projectStatus + this.bmStatus),
				items : [this.slProcreditAssuretenetedForm]
			}, {
				title : '<a name="gl_materials_edit' + this.projectId
						+ '"><font color="red">08</font>担保材料清单</a>',
				hidden : !isGranted('seeCreditMaterials_gt' + this.projectStatus + this.bmStatus),
				items : [this.slProcreditMaterialsView]
			}, {
				title : '<a name="gl_evaluation_edit' + this.projectId
						+ '"><font color="red">09</font>客户资信评估</a>',
				hidden : !isGranted('enterpriseEvaluation_gt' + this.projectStatus + this.bmStatus),
				items : [this.enterpriseEvaluation]
			}, {
				title : '<a name="gl_mortgage_edit' + this.projectId
						+ '"><font color="red">10</font>反担保措施管理</a>',
				hidden : !isGranted('mortgageView_gt' + this.projectStatus + this.bmStatus),
				items : [this.mortgageView,this.baozMortgageView]
			}, {
				title : '<a name="gl_report_edit' + this.projectId
						+ '"><font color="red">11</font>尽职调查报告</a>',
				hidden : !isGranted('seeReport_gt' + this.projectStatus + this.bmStatus),
				items : [this.slReportView]
			}, {
				title : '<a name="gl_riskReportView_edit' + this.projectId
						+ '"><font color="red">12</font>风险分析报告</a>',
				hidden : !isGranted('seeRiskReport_gt' + this.projectStatus + this.bmStatus),
				items : [this.slRiskReportView]
			}, {
				title : '<a name="gl_middleSupervisionInfo_edit'
						+ this.projectId
						+ '"><font color="red">13</font>保中监管记录</a>',
				hidden : !isGranted('_middleSupervision_gt' + this.projectStatus + this.bmStatus),
				items : [this.middleSupervision],
				listeners : {
					scope : this,
					afterrender : function(com) {
						if (this.projectStatus == 0) {// 保前项目信息不显示保中监管信息
							com.setVisible(false);
						}
					}
				}
			}, /*{
				title : '<a name="gl_letterAndBook_edit' + this.projectId
						+ '"><font color="red">14</font>拟担保意向书</a>',
				hidden : !isGranted('seeNdbBook_gt' + this.projectStatus + this.bmStatus),
				items : [this.letterAndBookView]
			}, */{
				title : '<a name="gl_letterAndBook_edit' + this.projectId
						+ '"><font color="red">14</font>审批表下载</a>',
				hidden : !isGranted('seeNdbBook_gt' + this.projectStatus + this.bmStatus),
				items : [this.spsLetterAndBookView]
			},{
				title : '<a name="gl_letterAndBook1_edit' + this.projectId
						+ '"><font color="red">15</font>对外担保承诺函</a>',
				hidden : !isGranted('seeDwdbcnBook_gt' + this.projectStatus + this.bmStatus),
				items : [this.letterAndBookView1]
			}, {
				title : '<a name="gl_contractInfo_edit' + this.projectId
						+ '"><font color="red">16</font>项目合同</a>',
				hidden : !isGranted('seeDtdbContract_gt' + this.projectStatus + this.bmStatus),
				items : [this.contractView]
			}, {
				title : '<a name="gl_uploads1_edit' + this.projectId
						+ '"><font color="red">17</font>银行担保合同</a>',
				hidden : !isGranted('gtContract_gt' + this.projectStatus + this.bmStatus),
				items : [this.uploads1]
			}, {
				title : '<a name="gl_uploads_edit' + this.projectId
						+ '"><font color="red">18</font>客户借款合同</a>',
				hidden : !isGranted('seeKhjkContract_gt' + this.projectStatus + this.bmStatus),
				items : [this.uploads]
			}, {
				title : '<a name="gl_uploads2_edit' + this.projectId
						+ '"><font color="red">19</font>担保责任解除函</a>',
				hidden : !isGranted('seeDbzrjcBook_gt00' + this.projectStatus + this.bmStatus),
				items : [this.uploads2]
			}, {
				title : '<a name="gl_guaranteeToCharge_edit' + this.projectId
						+ '"><font color="red">21</font>收取保费凭证</a>',
				hidden : !(isGranted('seeSqbfpz_gt' + this.projectStatus + this.bmStatus)||isGranted('editSqbfpz_gt' + this.projectStatus + this.bmStatus)),
				items : [new uploads({
					projectId : this.projectId,
					isHidden : !isGranted('editSqbfpz_gt' + this.projectStatus + this.bmStatus),
					tableName :'sl_fund_intent_GuaranteeToCharge',
					typeisfile :'typeisGuaranteeToCharge',
					businessType:this.businessType,
					uploadsSize : 15
					
				})]
			}, {
				title : '<a name="gl_toCustomGuarantMoney_edit' + this.projectId
						+ '"><font color="red">22</font>收取客户保证金凭证</a>',
				hidden : !(isGranted('seeSqkhbzjpz_gt' + this.projectStatus + this.bmStatus)||isGranted('editSqkhbzjpz_gt' + this.projectStatus + this.bmStatus)),
				items : [new uploads({
					projectId : this.projectId,
					isHidden : !isGranted('editSqkhbzjpz_gt' + this.projectStatus + this.bmStatus),
					tableName :'sl_fund_intent_customGuarantMoney',
					typeisfile :'typeisToCustomGuarantMoney',
					businessType:this.businessType,
					uploadsSize : 15
					
				})]
			}, {
				title : '<a name="gl_bankguaranteemoney_edit' + this.projectId
						+ '"><font color="red">23</font>银行冻结保证金凭证</a>',
				hidden : !(isGranted('seeSqdjbzjpz_gt' + this.projectStatus + this.bmStatus)||isGranted('editSqdjbzjpz_gt' + this.projectStatus + this.bmStatus)),
				items : [new uploads({
					projectId : this.projectId,
					isHidden : !isGranted('editSqdjbzjpz_gt' + this.projectStatus + this.bmStatus),
					tableName :'gl_Bank_guaranteemoney',
					typeisfile :'typeisbankguaranteemoney',
					businessType:this.businessType,
					uploadsSize : 15
					
				})]
			}, {
				title : '<a name="gl_paymentCertificate_edit' + this.projectId
						+ '"><font color="red">24</font>款项凭证</a>',
				hidden : !(isGranted('seeKxpz_gt' + this.projectStatus + this.bmStatus)||isGranted('editKxpz_gt' + this.projectStatus + this.bmStatus)),
				items : [new uploads({
					projectId : this.projectId,
					isHidden : !isGranted('"editKxpz_gt' + this.projectStatus + this.bmStatus),
					titleName :'文件',
					isJKDB : true,//标识是否客户借款合同及担保合同，true为uploads方法中上传的文件类型为setname，否则为titleName
					setname :'款项凭证',
					tableName :'gl_paymentCer',
					typeisfile :'paymentCertificate',
					businessType:'Guarantee',
					uploadsSize : 15
				})]
			}, {
				title : '<a name="gl_tongzhidan_edit' + this.projectId
						+ '"><font color="red">25</font>放款通知单扫描件</a>',
				hidden : !(isGranted('seeTzd_gt' + this.projectStatus + this.bmStatus)||isGranted('editTzd_gt' + this.projectStatus + this.bmStatus)),
				items : [new uploads({
					projectId : this.projectId,
					isHidden : !isGranted('editTzd_gt' + this.projectStatus + this.bmStatus),
					titleName :'文件',
					isJKDB : true,//标识是否客户借款合同及担保合同，true为uploads方法中上传的文件类型为setname，否则为titleName
					setname :'放款通知单扫描件',
					tableName :'gl_db_guarantee',
					typeisfile :'tongzhidan',
					businessType:'Guarantee',
					uploadsSize : 15
				})]
			}, {
				title : '<a name="gl_breachInfo_edit' + this.projectId
						+ '"><font color="red">26</font>违约信息</a>',
				//hidden : !(isGranted('seeTzd_gt' + this.projectStatus + this.bmStatus)||isGranted('editTzd_gt' + this.projectStatus + this.bmStatus)),
				items : [new uploads({
						anchor : "100%",
						title_c : '上传违约处理说明文档',
						businessType : 'Guarantee',
						typeisfile : 'breachSmallloan',
						projectId : this.projectId,
						uploadsSize : 15
					}), {
						xtype : "textarea",
						fieldLabel : "违约说明",
						labelWidth : 80,
						name : "gLGuaranteeloanProject.breachComment",
						style : 'margin:10px 0px 0px 0px;',
						anchor : "100%"
					}]
			}]
		});
		this.loadData({
			//url : __ctxPath + '/creditFlow/getInfoCreditProject.do?taskId=' + this.projectId + '&type=Guarantee',
			url : __ctxPath + '/creditFlow/guarantee/EnterpriseBusiness/getInfoGLGuaranteeloanProject.do?glProjectId='+this.projectId,
			preName : ['enterprise', 'person', 'gLGuaranteeloanProject','customerBankRelationPerson', "businessType"],
			root : 'data',
			success : function(response, options) {
				var respText = response.responseText;
				var alarm_fields = Ext.util.JSON.decode(respText);
				this.getCmpByName('projectMoney1').setValue(Ext.util.Format.number(alarm_fields.data.gLGuaranteeloanProject.projectMoney,'0,000.00'));
				this.getCmpByName('earnestmoney1').setValue(Ext.util.Format.number(alarm_fields.data.gLGuaranteeloanProject.earnestmoney,'0,000.00'));
			}
		})

		this.formPanel = new Ext.FormPanel();
		this.formPanel.add(this.outPanel);
		this.add(this.formPanel);
		this.doLayout();
	},// 初始化UI结束
	saveAllDatas : function() {
		var oppType = this.oppositeType,projectId = this.projectId,operationType = this.operationType,vDates = "";
		if (oppType == "company_customer") {
			var eg = this.outPanel.getCmpByName('gudong_store').get(0).get(1);
			vDates = getGridDate(eg);
		}
		var flag = 0;// 财务登记节点以及之前保存保证金
		if (this.taskSequence > 2000) {
			flag = 1;// 财务登记节点之后保存保证金
		}
		var fundIntentJsonData = this.fundIntent.getGridDate(),actualToChargeJsonData = this.actualToCharge.getGridDate(), customerRepaymentPlanData = getCustomerGridDate(this.customerRepaymentPlan.grid_CustomerRepaymentPlan),actualToCharge = this.actualToCharge,activityName = this.activityName,runId = this.runId;
		this.formPanel.getForm().submit({
			clientValidation : false,
			url : __ctxPath
					+ '/creditFlow/guarantee/EnterpriseBusiness/updateGLGuaranteeloanProject.do',
			params : {
				gudongInfo : vDates,
				fundIntentJsonData : fundIntentJsonData,
				slActualToChargeJsonData : actualToChargeJsonData,
				customerRepaymentPlanData : customerRepaymentPlanData,
				isDeleteAllFundIntent : 0,
				projectId : projectId,
				operationType : operationType,
				flag : flag
			},
			method : 'post',
			scope : this,
			waitMsg : '数据正在提交，请稍后...',
			success : function(fp, action) {
				var kk = action.response.responseText;
				var obj = Ext.decode(kk);
				this.formPanel.getCmpByName('customerBankRelationPerson.id')
						.setValue(obj.result[0].bankId);
				actualToCharge.savereload();
				var tabs = Ext.getCmp('centerTabPanel');
				var projectGrid = tabs.getCmpByName('GuaranteeProjectGrid');
				projectGrid.getStore().reload();
				Ext.ux.Toast.msg('操作信息', '保存信息成功!');
				if (oppType == "company_customer") {
					eg.getStore().reload();
				}
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
});