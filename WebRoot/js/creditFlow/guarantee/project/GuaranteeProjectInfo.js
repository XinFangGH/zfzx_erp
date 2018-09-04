/**
 * @author lisl
 * @class GuaranteeProjectInfo
 * @description 担保项目信息
 * @extends Ext.Panel
 */
GuaranteeProjectInfo = Ext.extend(Ext.Panel, {
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		this.initUIComponents();
		GuaranteeProjectInfo.superclass.constructor.call(this, {
			autoScroll : true,
			height : document.body.clientHeight - 245,
			items : []
		});
	},
	initUIComponents : function() {
		var jsArr = [
				__ctxPath + '/js/creditFlow/guarantee/enterpriseBusiness/enterpriseBusinessUI.js',// 项目基本信息
				__ctxPath + '/js/commonFlow/ExtUD.Ext.js',
				__ctxPath + '/js/creditFlow/finance/detailView.js',// 资金流水详细
				__ctxPath + '/js/creditFlow/finance/GuaranteeSlFundIntentViewVM.js',
				__ctxPath + '/js/creditFlow/guarantee/mortgage/GLOpponentMortgageTransact.js',// 反担保措施
				__ctxPath + '/js/creditFlow/finance/SlActualToChargeVM.js',// 经办费用清单
				__ctxPath + '/js/creditFlow/guarantee/enterpriseBusiness/MiddleSupervision.js',// 保中监管
				__ctxPath + '/js/creditFlow/smallLoan/contract/BatchSignThirdContractWindow.js',// 批量签署反担保措施合同
				__ctxPath + '/js/creditFlow/assuretenet/SlProcreditAssuretenetedForm.js',// 担保准入原则
				__ctxPath + '/js/creditFlow/guarantee/materials/SlEnterPriseProcreditMaterialsView.js',// 材料担保
				__ctxPath + '/js/creditFlow/guarantee/enterpriseBusiness/EnterpriseEvaluation.js', // 企业评估报告
				__ctxPath + '/js/creditFlow/report/SlReportView.js',// 调查报告
				__ctxPath + '/js/creditFlow/report/SlRiskReportView.js',// 风险综合分析报告
				__ctxPath + '/js/creditFlow/guarantee/contract/LetterAndBookView.js',// 担保意向书、对外担保承诺函
				__ctxPath+'/js/creditFlow/guarantee/guaranteefinance/accountBankManage/accountBankManageTreeWin.js',
				__ctxPath+'/js/creditFlow/guarantee/enterpriseBusiness/guaranteeModel/BankGuaranteeMoneyrelease.js',//保证金支付情况
				__ctxPath + '/js/creditFlow/finance/gt_FundIntent_formulate_editGrid.js',//银行放款收息
			];
		$ImportSimpleJs(jsArr, this.constructPanel, this);
	},// 初始化组件
	constructPanel : function() {

		this.projectId = this.record.data.projectId;
		this.projectStatus = this.record.data.projectStatus;
		this.bmStatus = this.record.data.bmStatus;
		this.taskId = this.record.data.taskId;
		this.flowType = this.record.data.flowType;
		this.isHiddenZM = true;
		this.index = 18;
		if (this.flowType == 'zmNormalFlow') {
			this.isHiddenZM = false;
			this.index = 20;
		}
		// 项目基本信息
		this.projectInfo = new enterpriseBusinessProjectInfoPanel({
			isAllReadOnly : true
		});
		this.customerInfo = "";
		this.customerTitle = "企业客户信息";
		// 客户基本信息
		if (this.record.data.oppositeType == 'person_customer') {
			this.customerInfo = new ExtUD.Ext.PeerPersonMainInfoPanel({
				isAllReadOnly : true
			});
			this.customerTitle = "个人客户信息";
		} else if (this.record.data.oppositeType == 'company_customer') {
			this.customerInfo = new ExtUD.Ext.PeerMainInfoPanel({
				projectId : this.projectId,
				bussinessType : 'Guarantee',
				isAllReadOnly : true
			});
		}
		this.bankInfo = new ExtUD.Ext.BankInfoPanel({
			isAllReadOnly : true
		});
		// 资金款项信息
		this.financeInfo = new ProjectInfoGuaranteeFinancePanel({
			isAllReadOnly : true,
			isReadOnlyDateDueTime:true,
			isHiddenPanel : !isGranted('gtFinanceBaseInfo_gt' + this.projectStatus + this.bmStatus)
		});
		// 经办费用清单
		this.actualToCharge = new SlActualToChargeVM({
			projId : this.projectId,
			businessType : 'Guarantee',
			isHiddenTitle : true,
			isHiddenPanel : !isGranted('gtActualToCharge_gt' + this.projectStatus + this.bmStatus)
		});
		// 担保准入原则
		this.slProcreditAssuretenetedForm = new SlProcreditAssuretenetedForm({
			projectId : this.projectId,
			businessType : this.businessType,
			isEditRiskmanageropinion : false,
			isEditBbusinessmanageropinion : false
		});
		// 担保材料清单
		this.slProcreditMaterialsView = new SlEnterPriseProcreditMaterialsView({
			projectId : this.projectId,
			businessType : 'Guarantee',
			isProjectInfoEdit : true,
			isHidden_materials : true,
			isHiddenArchive : false
		});
		
		// 客户资信评估
		this.enterpriseEvaluation = new EnterpriseEvaluationGuarantee({
			projectId : this.projectId,
			isHidden : true
		});
		 //保证金支付情况
		 this.creditlineRelease = new BankGuaranteeMoneyrelease({
			 projectId : this.projectId,
			 isReadOnlyFrozen : true,
			 isReadOnlyRelease : true,
			 frame1 : false
		 });
		 //银行放款收息表
			this.customerRepaymentPlan = new gt_FundIntent_formulate_editGrid({
				projId : this.projectId,
				businessType : 'Guarantee'
				
			});
		// 尽职调查报告
		this.slReportView = new SlReportView({
			projectId : this.projectId,
			businessType : 'Guarantee',
			isHiddenAffrim_report : false,
			isHidden_report : true,
			isgdEdit : false
		});
		// 风险分析报告
		this.slRiskReportView = new SlRiskReportView({
			projectId : this.projectId,
			businessType : 'Guarantee',
			isHidden_riskReport : true,
			isgdEdit_riskReport : false,
			isHiddenAffrim_riskReport : false
		});
		// 拟担保意向书
		this.letterAndBookView = new LetterAndBookView({
			projectId : this.projectId,
			businessType : 'Guarantee',
			LBTemplate : 'simulateEnterpriseBook',
			isHidden : true,
			isRecordHidden : false,
			isGdEdit : false
		});
		// 对外担保承诺函
		this.letterAndBookView1 = new LetterAndBookView({
			projectId : this.projectId,
			businessType : 'Guarantee',
			LBTemplate : 'assureCommitmentLetter',
			isHidden : true,
			isRecordHidden : false,
			isGdEdit : false
		});
		// 股东会决议
		this.letterAndBookView2 = new LetterAndBookView({
			projectId : this.projectId,
			businessType : 'Guarantee',
			LBTemplate : 'partnerMeetingResolution',
			isHiddenPanel : this.isHidden,
			isHidden : true,
			isRecordHidden : false,
			isGdEdit : false
		});
		//客户借款合同
		this.uploads = new uploads({
			projectId : this.projectId,
			isgdHidden : false,
			isHidden : true,
			isgdEdit : false,
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
			isHidden : true,
			isgdEdit : false,
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
			isHidden : true,
			isgdEdit : false,
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
			isHidden : true,
			isgdEdit : false
		});
		this.spsLetterAndBookView = new LetterAndBookView({
	    	projectId : this.projectId,
	    	businessType : 'Guarantee',
	    	LBTemplate : 'SPBMB',
	    	isHidden : true,
	    	isfwHidden : true,
	    	isfwEdit : true
	    });//审批表
		// 委托担保合同
		this.contractView = new SlContractView({
			projId : this.projectId,
			businessType : 'Guarantee',
			HTLX : 'DBDQ',
			isHiddenAffrim : false,
			isgdEdit : true,
			isSignHidden : false,
			islcEdit : true,
			isgdEdit : false,
			isHiddenTbar : true
		});
		// 保中监管
		this.middleSupervision = new MiddleSupervision({
			projectId : this.projectId,
			isHidden : true
		});
		//this.mortgageView = new GuaranteeMortgageView({
		this.mortgageView = new DZYMortgageView({
			titleText : '抵质押担保',
			projectId : this.projectId,
			businessType : 'Guarantee',
			isHiddenAddBtn : true,
			isHiddenDelBtn : true,
			isHiddenEdiBtn : true,
			isblEdit : false,
			isRelieveEdit : false,
			isHiddenAddContractBtn : true,
			isHiddenDelContractBtn : true,
			isHiddenEdiContractBtn : true,
			isfwEdit : false,
			isKS : true,
			isHiddenGDBtn : true
			/*isSignHidden : true,
			isSeeContractHidden : true,
			isAfterContract : true,
			isHiddenInArchiveConfirm : true,
			isblHidden : true,
			isgdHidden : true*/
		});
		this.baozMortgageView = new BaozMortgageView({
			projectId : this.projectId,
			titleText : '保证担保',
			businessType : 'Guarantee',
			isHiddenAddContractBtn:true,
			isHiddenDelContractBtn:true,
			isHiddenEdiContractBtn:true,
			isHiddenRelieve:true,
			isblHidden:false,
			isgdHidden:false,
			isgdEdit : false,
			isblEdit : false,
			isHiddenAddBtn:true,
			isHiddenDelBtn:true,
			isHiddenEdiBtn:true
		});
		this.outPanel = new Ext.Panel({
			modal : true,
			buttonAlign : 'center',
			layout : 'form',
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
				title : '<a name="gl_projectBaseInfo_see' + this.projectId
						+ '"><font color="red">01</font>项目控制信息</a>',
				hidden : !isGranted('gtProjectInfo_gt' + this.projectStatus + this.bmStatus),
				items : [this.projectInfo]
			}, {
				title : '<a name ="gl_customerInfo_see' + this.projectId
						+ '"><font color="red">02</font>' + this.customerTitle
						+ '</a>',
				hidden : !isGranted('gtCustomerInfo_gt' + this.projectStatus + this.bmStatus),
				items : [this.customerInfo]
			}, {
				title : '<a name="gl_bankInfo_see' + this.projectId
						+ '"><font color="red">03</font>对接银行信息</a>',
				hidden : !isGranted('gtBankInfo_gt' + this.projectStatus + this.bmStatus),
				items : [this.bankInfo]
			}, {
				title : '<a name="gl_financeInfo_see' + this.projectId
						+ '"><font color="red">04</font>资金款项信息</a>',
				hidden : !isGranted('gtFinanceBaseInfo_gt' + this.projectStatus + this.bmStatus)&&!isGranted('gtActualToCharge_gt' + this.projectStatus + this.bmStatus),
				bodyStyle : 'padding-top:5px',
				items : [this.financeInfo, {
					xtype : 'panel',
					hidden : !isGranted('gtActualToCharge_gt' + this.projectStatus + this.bmStatus),
					border : false,
					bodyStyle : 'margin-bottom:5px',
					html : '<br/><B><font class="x-myZW-fieldset-title"><a name="gl_actualToChargeInfo_see'
							+ this.projectId
							+ '">【<font color="red">04</font>手续收支清单】</a></font></B><font class="x-myZW-fieldset-title">（</font>颜色预警：<font color=red>逾期款项</font>&nbsp;&nbsp<font style="line-height:20px">未结清项</font>&nbsp;&nbsp<font color=gray>已结清项</font><font class="x-myZW-fieldset-title" >）：</font>'
				}, this.actualToCharge]
			}, {
				title : '<a name="gl_creditlineRelease_see' + this.projectId
						+ '"><font color="red">05</font>保证金支付情况</a>',
				hidden : !isGranted('seeBankMargin_gt' + this.projectStatus + this.bmStatus),
				items : [this.creditlineRelease]
			}, {
				title : '<a name="gl_customerRepaymentPlan_see' + this.projectId
						+ '"><font color="red">06</font>银行放款收息表</a>',
				hidden : !isGranted('customerRepayment_gt' + this.projectStatus + this.bmStatus),
				items : [this.customerRepaymentPlan]
			}, {
				title : '<a name="gl_assuretenetInfo_see' + this.projectId
						+ '"><font color="red">07</font>担保准入原则</a>',
				hidden : !isGranted('seeAssuretenet_gt' + this.projectStatus + this.bmStatus),
				items : [this.slProcreditAssuretenetedForm]
			}, {
				title : '<a name="gl_materials_see' + this.projectId
						+ '"><font color="red">08</font>担保材料清单</a>',
				hidden : !isGranted('seeCreditMaterials_gt' + this.projectStatus + this.bmStatus),
				items : [this.slProcreditMaterialsView]
			}, {
				title : '<a name="gl_evaluation_see' + this.projectId
						+ '"><font color="red">09</font>客户资信评估</a>',
				hidden : !isGranted('enterpriseEvaluation_gt' + this.projectStatus + this.bmStatus),
				items : [this.enterpriseEvaluation]
			}, {
				title : '<a name="gl_mortgage_see' + this.projectId
						+ '"><font color="red">10</font>反担保措施管理</a>',
				hidden : !isGranted('mortgageView_gt' + this.projectStatus + this.bmStatus),
				items : [this.mortgageView,this.baozMortgageView]
			}, {
				title : '<a name="gl_report_see' + this.projectId
						+ '"><font color="red">11</font>尽职调查报告</a>',
				hidden : !isGranted('seeReport_gt' + this.projectStatus + this.bmStatus),
				items : [this.slReportView]
			}, {
				title : '<a name="gl_riskReportView_see' + this.projectId
						+ '"><font color="red">12</font>风险分析报告</a>',
				hidden : !isGranted('seeRiskReport_gt' + this.projectStatus + this.bmStatus),
				items : [this.slRiskReportView]
			}, {
				title : '<a name="gl_middleSupervisionInfo_see'
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
				title : '<a name="gl_letterAndBook_see' + this.projectId
						+ '"><font color="red">14</font>拟担保意向书</a>',
				hidden : !isGranted('seeNdbBook_gt' + this.projectStatus + this.bmStatus),
				items : [this.letterAndBookView]
			}, */{
				title : '<a name="gl_letterAndBook_see' + this.projectId
						+ '"><font color="red">14</font>审批表下载</a>',
				hidden : !isGranted('seeNdbBook_gt' + this.projectStatus + this.bmStatus),
				items : [this.spsLetterAndBookView]
			},{
				title : '<a name="gl_letterAndBook1_see' + this.projectId
						+ '"><font color="red">15</font>对外担保承诺函</a>',
				hidden : !isGranted('seeDwdbcnBook_gt' + this.projectStatus + this.bmStatus),
				items : [this.letterAndBookView1]
			}, {
				title : '<a name="gl_contractInfo_see' + this.projectId
						+ '"><font color="red">16</font>项目合同</a>',
				hidden : !isGranted('seeDtdbContract_gt' + this.projectStatus + this.bmStatus),
				items : [this.contractView]
			}, {
				title : '<a name="gl_uploads1_see' + this.projectId
						+ '"><font color="red">17</font>银行担保合同</a>',
				hidden : !isGranted('gtContract_gt' + this.projectStatus + this.bmStatus),
				items : [this.uploads1]
			}, {
				title : '<a name="gl_uploads_see' + this.projectId
						+ '"><font color="red">18</font>客户借款合同</a>',
				hidden : !isGranted('seeKhjkContract_gt' + this.projectStatus + this.bmStatus),						
				items : [this.uploads]
			}, {
				title : '<a name="gl_uploads2_see' + this.projectId
						+ '"><font color="red">19</font>担保责任解除函</a>',
				hidden : !isGranted('seeDbzrjcBook_gt' + this.projectStatus + this.bmStatus),
				items : [this.uploads2]
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
		this.add(this.outPanel);
		this.doLayout();
	}// 初始化UI结束

});