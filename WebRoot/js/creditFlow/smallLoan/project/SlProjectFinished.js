/**
 * @author lisl
 * @description 小额贷款结项
 * @extends Ext.Panel
 */
SlProjectFinished = Ext.extend(Ext.Panel, {
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		this.initUIComponents();
		SlProjectFinished.superclass.constructor.call(this, {
			id : 'SlProjectFinished_' + this.record.data.id,// id属性值前缀,通过是否包含"Edit"来区分查看和编辑页面
			title : "贷款结项-" + this.record.data.projectName,
			iconCls : '',
			tbar : this.toolbar,
			items : []
		});
	},
	initUIComponents : function() {
		var jsArr = [
				__ctxPath + '/js/commonFlow/ExtUD.Ext.js',
				__ctxPath + '/js/creditFlow/smallLoan/project/loadDataCommon.js',// 加载数据JS
				__ctxPath + '/js/creditFlow/common/EnterpriseShareequity.js',
				__ctxPath + '/js/selector/UserDialog.js',
				//__ctxPath + '/js/creditFlow/guarantee/materials/SlEnterPriseProcreditMaterialsView.js',// 贷款材料清单
				__ctxPath + '/js/creditFlow/report/SlReportView.js',
				__ctxPath + '/js/creditFlow/finance/SlActualToCharge.js',
				__ctxPath + '/js/creditFlow/finance/SlFundIntentForm.js',
				__ctxPath + '/js/creditFlow/finance/SlActualToChargeVM.js',// 计划收支费用
				__ctxPath + '/js/creditFlow/finance/detailView.js',
				__ctxPath + '/js/creditFlow/finance/chargeDetailView.js',
				__ctxPath + '/js/creditFlow/document/LoanSettlementDocument.js',
				__ctxPath + '/js/ah_ext/factory/ahinput.js',
				__ctxPath + '/js/creditFlow/fund/project/ownFund.js',
					__ctxPath + '/js/creditFlow/finance/SlFundIntentForm.js',
				__ctxPath + '/js/creditFlow/finance/SlFundIntentForm1.js',
					__ctxPath + '/js/creditFlow/finance/selectAccountlForm.js',
				__ctxPath + '/js/creditFlow/finance/selectAccountlForm1.js',
		    	__ctxPath + '/js/creditFlow/finance/OwnFundIntentView.js',
		    	__ctxPath + '/js/creditFlow/smallLoan/project/loadDataCommonCredit.js',
				__ctxPath + '/js/creditFlow/smallLoan/contract/SlContractView.js'
		];
		$ImportSimpleJs(jsArr, this.constructPanel, this);
		this.toolbar = new Ext.Toolbar({
			items : [{
				iconCls : 'btn-save',
				text : '提交',
				scope : this,
				handler : function() {
					var SlProjectId=this.record.data.projectId;
					var businessType = this.record.data.businessType;
					var fundProjectId=this.record.data.id
					var projectGridPanel=this.projectGridPanel
					Ext.Ajax.request({
						url : __ctxPath + '/project/loanFinishedSlSmallloanProject.do',
						params : {
							projectId : SlProjectId,
							businessType : businessType,
							fundProjectId:fundProjectId,
							fundType : 'principalRepayment'
						},
						method : 'post',
						success : function(resp, op) {
							var res = Ext.util.JSON.decode(resp.responseText);
							if (res.success) {
								Ext.ux.Toast.msg('信息提示', '结项成功');
								Ext.getCmp('centerTabPanel').remove('SlProjectFinished_'+ fundProjectId);//这个是关闭当前页面
								projectGridPanel.getStore().reload();
							}else{
								Ext.ux.Toast.msg('信息提示', '该项目有对账金额尚未结清,不能执行贷款结清操作！');
							}
							//ZW.refreshTaskPanelView();
						},
						failure : function() {
							Ext.ux.Toast.msg('信息提示', '出错，请联系管理员！');
						}
					})
				}
			}]
		})
	},// 初始化组件
	constructPanel : function() {
		this.projectId = this.record.data.projectId;
		this.fundProjectId = this.record.data.id;
		this.oppositeType = this.record.data.oppositeType;//小贷项目用来判断是企业客户还是个人客户
		this.operationType = this.record.data.operationType;//用来判断是小贷项目还是微贷项目
		this.mainInfo = "";
		var isMicroLoan=true;
		
	this.perMain = "";
		
		var title="企业客户信息";
		if (this.oppositeType =="person_customer") {
			//项目基本信息
		this.projectInfo = new ExtUD.Ext.CreditLoanProjectInfoPanel({readOnly:true,product:true,isCPLX:true});
	
		//个人客户信息
		this.perMain = new ExtUD.Ext.CustomerInfoFastPanel({
			isEditPerson : false,
			isAllReadOnly:true,
			isRead:true,
			isHidden:true,
			isSpouseReadOnly: true,
			isNameReadOnly:true,
			isHiddenCustomerDetailBtn:false
		});
			title="个人客户信息";
		} else if(this.oppositeType =="company_customer"){
			this.projectInfo = new ExtUD.Ext.McroLoanProjectInfoPanel({
			isDiligenceReadOnly : true,
			isAllReadOnly:true
		});
			     this.perMain = new ExtUD.Ext.PeerMainInfoPanel({
				 projectId : this.projectId,
				 bussinessType:this.baseBusinessType,
				 isEditEnterprise : false,
				 isReadOnly:true,
				 isNameReadOnly:true
			});
		}
		
		this.projectInfoFinance= new ownFund({
			isAllReadOnly:true,
			isReadOnly:true,
			isHiddencalculateBtn:true,
		 	projectId:this.projectId,
		 	isStartDateReadOnly:true,
		 	projectInfoFinance : this.projectInfoFinance,
			idDefinition:'tongyongliucheng1'+this.taskId
		});
		this.gridPanel =new OwnFundIntentView({
					projectId : this.projectId,
					object : this.projectInfoOwnFinance,
					preceptId:this.fundProjectId,
					isHiddenAddBtn:true,
					isHiddenDelBtn:true,
					isHiddenExcel:false,
					isHiddenResBtn1:true,
					isHiddenautocreateBtn:true,
					isHiddenseeqlideBtn:false,
					isHiddenseesumqlideBtn:false,
					businessType : 'SmallLoan'
				});
		
		this.slActualToCharge = new SlActualToChargeVM({
			projId : this.projectId,
			isHidden : true,
			businessType : 'SmallLoan' // 小贷
		});
/*
		this.repaymentSource = new RepaymentSource({
			projectId : this.projectId,
			isHidden : true
		})*/
		//初审报告
	/*	this.firstCheckReportView = new SlReportView({
			projectId : this.projectId,
			businessType : 'SmallLoan',
			Template:'FirstCheckReport',
			isHidden_report : true
		});*/
		 /* this.slProcreditMaterialsView = new SlEnterPriseProcreditMaterialsView({
				projectId : this.projectId,
			isHiddenAffrim : false,
			isEditAffrim : false,
			businessType : 'SmallLoan'
		});*/
		this.creditContractView =new SlContractView({
							projectId : this.fundProjectId,
							businessType : 'SmallLoan',
							htType : 'loanContract',
							HTLX : 'loanContract',
							isHiddenAddBtn : true,
							isHiddenEdiBtn : true,
							isHiddenDelBtn : true,
							isHidden : true,
							isSignHidden:true,
							isqsEdit:false,
							isHiddenRZZLHT:true,
							isHiddenTbar:true
						});
		/*this.slReportView = new SlReportView({
			projectId : this.projectId,
			businessType : 'SmallLoan',
			isHiddenAffrim_report : true
		});*/
		/*this.slRiskReportView = new SlRiskReportView({
			projectId : this.projectId,
			businessType : 'SmallLoan',
			isHiddenAffrim_riskReport : true
		});*/
		
	
	/*	//旗下公司
		this.thereunderPanelView=new ThereunderPanelView({
			projectId : this.projectId,
			operationType : this.operationType,
			isHiddenAddBtn : true,
			isHiddenEditBtn : true,
			isHiddenDelBtn : true,
			businessType : 'SmallLoan'
		});
		// 贷款准入原则
		this.SlProcreditAssuretenetedForm = new SlProcreditAssuretenetedForm({
			projectId : this.projectId,
			businessType : 'SmallLoan'
		});*/
		this.LoanSettlementDocument=new SlReportView({
			businessType : 'SmallLoan',
			LBTemplate : 'LoanSettlement',
			projectId: this.projectId,
			isHidden:false,
			isHidden_report : false,
			titleText:'贷款结清文档'
		})
		this.outPanel = new Ext.Panel({
			frame : true,
			modal : true,
			labelWidth : 100,
			buttonAlign : 'center',
			layout : 'form',
			border : false,
			defaults : {
				anchor : '100%',
				xtype : 'fieldset',
				columnWidth : 1,
				collapsible : true,
				autoHeight : true,
				autoWidth : true
			},
			labelAlign : "right",
			items : [{
				title : '项目基本信息',
				name : 'projectInfo',
				items : [this.projectInfo]
			}, {
				title : title,
				collapsed : true,
				items : [this.perMain]
			}, {
				xtype : 'fieldset',
				title : '资金款项信息',
				bodyStyle : 'padding-left:0px',
				name : 'ownFinanceInfoFieldset',
				collapsible : true,
				labelAlign : 'right',
				autoHeight : true,
				items : [this.projectInfoFinance, this.gridPanel,this.slActualToCharge
						]
			}, /*{
				title : '第一还款来源',
				items : [this.repaymentSource]
			}, {
				title : '贷款准入原则',
				items : [this.SlProcreditAssuretenetedForm]
			}, {
				title : '贷款资料清单',
				items : [this.slProcreditMaterialsView]
			}, */{
				title : '担保措施',
				name : 'guaranteeMeasures',
				items : [new DZYMortgageView({
					projectId : this.projectId,
					titleText : '抵质押物',
					businessType : 'SmallLoan',
					isHiddenAddContractBtn : true,
					isHiddenDelContractBtn : true,
					isHiddenEdiContractBtn : true,
					isHiddenRelieve : false,
					isblHidden : true,
					isHiddenAddBtn : true,
					isHiddenDelBtn : true,
					isHiddenEdiBtn : true,
					isfwEdit : false,
					isSeeContractHidden : false,
					isKS : true,
					isSignHidden : false,
					isblEdit : false,
					isblHidden : true,
					isHiddenGDBtn : true,
					isgdHidden : true,
					isRelieveEdit : true,
					isRecieveHidden:true
				}), new BaozMortgageView({
					projectId : this.projectId,
					titleText : '保证担保',
					businessType : 'SmallLoan',
					isHiddenAddContractBtn : true,
					isHiddenDelContractBtn : true,
					isHiddenEdiContractBtn : true,
					isblHidden : true,
					isHiddenAddBtn : true,
					isHiddenDelBtn : true,
					isHiddenEdiBtn : true,
					isfwEdit : false,
					isSeeContractHidden : false,
					isKS : true,
					isSignHidden : false,
					isblEdit : false,
					isblHidden : false,
					isHiddenGDBtn : true,
					isgdHidden : true,
					isRecieveHidden:true
				})]
			}/*,{
				title : '<a name="sl_thereunderPanelView_see' + this.projectId
						+ '"><font color="red">07</font>旗下公司</a>',
				hidden : isMicroLoan?true:false,
				items : [this.thereunderPanelView]
			},{
				title : '<a name="sl_financeInfo1_see' + this.projectId
						+ '"><font color="red">08</font>财务信息</a>',
				hidden : isMicroLoan?true:false,
				items : [this.financeInfo]
			}, {
				title : '调查报告',
				items : [this.slReportView]
			}*//*{
				title : '风险分析报告',
				items : [this.slRiskReportView]
			}, ,{
				xtype : 'fieldset',
				title :'客服录件报告',
				bodyStyle : 'padding-left:0px',
				collapsible : true,
				labelAlign : 'right',
				autoHeight : true,
				items : [this.firstCheckReportView]
			}*/,{
				title : '贷款合同',
				items : [this.creditContractView]
			}, {
				title : '贷款结清文档',
				items : [this.LoanSettlementDocument]
			}]

		});
		this.loadData({
			url : __ctxPath + '/creditFlow/getAllInfoCreditProject.do?fundProjectId='
					+ this.fundProjectId,
			preName : ['enterprise', 'person', 'slSmallloanProject',
					'businessType',"enterpriseBank","spouse","financeInfo"],
			root : 'data',
			success : function(response, options) {
				var respText = response.responseText;
				var alarm_fields = Ext.util.JSON.decode(respText);
				expandFieldSet(this.outPanel)
				if(alarm_fields.data.ownBpFundProject){
					this.getCmpByName('ownBpFundProjectMoney').setValue(Ext.util.Format.number(alarm_fields.data.ownBpFundProject.ownJointMoney,'0,000.00'));
					fillFundData(this,alarm_fields,'tongyongliucheng1'+this.taskId);
				}
				if(typeof(alarm_fields.data.enterpriseBank)!="undefined"){
					this.getCmpByName('enterpriseBank.areaName').setValue(alarm_fields.data.enterpriseBank.areaName)
				}
		
			}
		});
		this.add(this.outPanel);
		this.doLayout();
	}// 初始化UI结束
});