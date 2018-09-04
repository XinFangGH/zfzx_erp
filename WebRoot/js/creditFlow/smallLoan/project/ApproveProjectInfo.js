/**
 * 导航－金融中介管理
 * 	   ---审批项目查看页面详细信息
 * @extends Ext.Panel
 */
/**
 * @author lisl
 * @description 小额贷款项目信息
 * @extends Ext.Panel
 */
ApproveProjectInfo = Ext.extend(Ext.Panel, {
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		this.initUIComponents();
		ApproveProjectInfo.superclass.constructor.call(this, {
			autoScroll : true,
			height : document.body.clientHeight - 200,
			tbar : this.readOnly?null:this.toolbar,
			items : []
		});
	},
	initUIComponents : function() {
		var jsArr = [
				__ctxPath + '/js/commonFlow/ExtUD.Ext.js',//客户信息 项目基本信息
			    __ctxPath + '/js/creditFlow/smallLoan/quickenLoan/LendForm.js',//借款需求
			    __ctxPath + '/js/creditFlow/customer/person/PersonFinanceInfo.js',//房产信息
			    __ctxPath + '/js/creditFlow/customer/person/workcompany/workCompanyForm1.js',//个人所在公司信息
			    __ctxPath + '/js/creditFlow/customer/person/workcompany/workCompanyPrivateForm.js',//私营业主补填信息
			    __ctxPath + '/js/creditFlow/customer/person/person_branch_company.js',//个人旗下公司信息
			    __ctxPath + '/js/creditFlow/customer/person/relationPerson/RelationPersonView.js',//联系人信息
			    __ctxPath + '/js/creditFlow/report/SlReportView.js',
			    __ctxPath + '/js/creditFlow/finance/SlActualToCharge.js',
			    __ctxPath+'/js/creditFlow/assuretenet/OurProcreditAssuretenetProductView.js',
			    __ctxPath+'/js/creditFlow/smallLoan/materials/OurProcreditMaterialsView.js',
			    __ctxPath+'/js/creditFlow/smallLoan/project/loadDataCommon.js',
			    __ctxPath + '/js/creditFlow/customer/enterprise/addEnterprise.js',
			    __ctxPath + '/js/creditFlow/guarantee/materials/SlEnterPriseProcreditMaterialsView.js',// 贷
				__ctxPath + '/js/commonFlow/NewProjectForm.js',
				__ctxPath + '/js/creditFlow/personrelation/netcheck/BpPersonNetCheckInfoView.js',//网审信息

				
				__ctxPath + '/js/creditFlow/smallLoan/contract/OperateContractWindow.js',
				__ctxPath + '/js/commonFlow/ExtUD.Ext.js',
				__ctxPath + '/js/creditFlow/finance/detailView.js',// 资金流水详细
				__ctxPath + '/js/creditFlow/finance/SlFundIntentViewVM.js',// 款项信息
				__ctxPath + '/js/creditFlow/finance/SlActualToChargeVM.js',// 经办费用清单
				__ctxPath + '/js/creditFlow/repaymentSource/RepaymentSource.js',// 第一还款来源
				__ctxPath + '/js/creditFlow/smallLoan/finance/SlSuperviseRecordListView.js',// 展期记录
				__ctxPath + '/js/creditFlow/smallLoan/contract/SlClauseContractView.js',// 展期合同
				__ctxPath + '/js/creditFlow/smallLoan/project/loadDataCommon.js',// 加载页面表单数据JS
				__ctxPath + '/js/creditFlow/smallLoan/contract/BatchSignThirdContractWindow.js',// 批量签署反担保措施合同
				__ctxPath + '/js/creditFlow/assuretenet/SlProcreditAssuretenetedForm.js',// 贷款准入原则
				__ctxPath + '/js/creditFlow/report/SlReportView.js',// 尽职调查报告
				__ctxPath + '/js/creditFlow/report/SlRiskReportView.js',// 风险分析报告
				__ctxPath + '/js/creditFlow/finance/superviseSlFundIntent.js',
				__ctxPath + '/js/creditFlow/finance/SlActualToCharge.js',
				__ctxPath + '/js/creditFlow/finance/SlFundIntentForm.js',
				__ctxPath + '/js/creditFlow/finance/chargeDetailView.js',
				__ctxPath + '/js/creditFlow/smallLoan/finance/BorrowerInfo.js',//共同借款人信息
				__ctxPath + '/js/creditFlow/smallLoan/finance/loadDataExtension.js',
				__ctxPath + '/js/creditFlow/smallLoan/finance/FinanceExtensionlPanel.js',
				__ctxPath + '/js/creditFlow/smallLoan/project/SlEarlyrepaymentDetailView.js',
				__ctxPath + '/js/creditFlow/smallLoan/project/SlAlterAccrualRecordView.js',// 利率变更记录
				__ctxPath + '/js/creditFlow/smallLoan/finance/SuperviseFinancePanel.js',
				__ctxPath + '/js/creditFlow/smallLoan/finance/SuperviseFundIntent.js',
				__ctxPath + '/js/creditFlow/smallLoan/finance/SuperviseIntentGrid.js',
				__ctxPath + '/js/creditFlow/smallLoan/finance/SuperviseLoadDataCommon.js',
				__ctxPath + '/js/creditFlow/finance/calculateFundIntent.js',
				__ctxPath + '/js/creditFlow/finance/calulateFinancePanel.js',
				__ctxPath + '/js/creditFlow/finance/caluateIntentGrid.js',
				__ctxPath + '/js/creditFlow/finance/calulateloadDataCommon.js',
				__ctxPath + '/js/creditFlow/guarantee/contract/LetterAndBookView.js'//贷款审查审批表
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
		this.businessType = this.record.data.businessType;
		this.oppositeType = this.record.data.OppositeType;
		if(typeof(this.record.data.OppositeType)=='undefined' || null==this.record.data.OppositeType){
			this.oppositeType=this.record.data.oppositeType;
		}
		this.projectStatus = this.record.data.projectStatus;
		this.operationType = this.record.data.operationType;
		this.taskId = this.record.data.taskId;
		this.personId = this.record.data.oppositeID;
		this.customerInfo = "";
		this.customerTitle = "客户信息";
		
		
		
		//借款需求
		this.LendForm = new LendForm({
			projectID : this.projectId,
			isAllReadOnly : this.readOnly,
			isReadOnly:this.readOnly
		});
		
		if(this.oppositeType=="person_customer"){
			//项目基本信息
		this.projectInfo = new ExtUD.Ext.CreditLoanProjectInfoPanel({readOnly:this.readOnly,product:true,isCPLX:true});
			//个人客户信息
			this.perMain = new ExtUD.Ext.CustomerInfoFastPanel({
				isEditPerson : true,
				isAllReadOnly:this.readOnly,
				isRead:this.readOnly,
				isHidden:true,
				isSpouseReadOnly: true,
				isNameReadOnly:true,
				isHiddenCustomerDetailBtn:false
			});	
				//个人所在公司信息
		this.workCompany = new 	workCompanyForm1({
			isAllReadOnly : this.readOnly,
			isReadOnly:this.readOnly
		});
		//私营业主补填信息
		this.workCompanyPrivate = new workCompanyPrivateForm({
			isAllReadOnly : this.readOnly,
			isReadOnly:this.readOnly
		});
		//个人旗下公司信息
		this.branch_company = new person_branch_company_v({
			legalpersonid:this.personId,
			isHiddenAddBtn : this.readOnly,
			isHiddenDelBtn : this.readOnly,
			isHiddenEdiBtn : this.readOnly,
			projectId:this.projectId
		});
			this.panel=new Ext.Panel({
				items : [this.perMain,{
					xtype : 'fieldset',
					title :'【公司信息】个人所在公司信息',
					bodyStyle : 'padding-left:0px',
					collapsible : true,
					labelAlign : 'right',
					autoHeight : true,
					items : [this.workCompany]
				},{
					xtype : 'fieldset',
					title :'【公司信息】私营业主请补填',
					bodyStyle : 'padding-left:0px',
					collapsible : true,
					labelAlign : 'right',
					autoHeight : true,
					items : [this.workCompanyPrivate]
				},{
					xtype : 'fieldset',
					title : '【公司信息】个人旗下公司信息',
					bodyStyle : 'padding-left:0px',
					collapsible : true,
					labelAlign : 'right',
					autoHeight:true,
					items : [this.branch_company]
				}]
			})
		}else{
			//项目基本信息
				this.projectInfo =new ExtUD.Ext.McroLoanProjectInfoPanel({
					isDiligenceReadOnly : this.readOnly,
					isAllReadOnly:this.readOnly
				})
			//企业信息
		    this.perMain = new ExtUD.Ext.PeerMainInfoPanel({
				 projectId : this.projectId,
				 bussinessType:this.businessType,
				 isAllReadOnly : true,
				 isNameReadOnly:true,
				 isReadOnly:true,
				 isHidden : false,
				 isEditEnterprise : true
			});
		}
	
		
		//联系人信息(配偶或直系亲属)   PersonRelation  VPersonRelationperson
		this.relationPersonInfo0 = new RelationPersonView({
			personIdValue:this.personId,
			projectId:this.projectId,
			relationPersonType:575,
			flag:0,
			isHiddenAddBtn:this.readOnly,
			isHiddenDelBtn:this.readOnly,
			isHiddenEdiBtn:this.readOnly
		});
		this.relationPersonInfo1 = new RelationPersonView({
			personIdValue:this.personId,
			projectId:this.projectId,
			relationPersonType:577,
			flag:1,
			isHiddenAddBtn:this.readOnly,
			isHiddenDelBtn:this.readOnly,
			isHiddenEdiBtn:this.readOnly
		});
		this.relationPersonInfo2 = new RelationPersonView({
			personIdValue:this.personId,
			projectId:this.projectId,
			relationPersonType:576,
			flag:2,
			isHiddenAddBtn:this.readOnly,
			isHiddenDelBtn:this.readOnly,
			isHiddenEdiBtn:this.readOnly
		});
		
		//贷款材料清单
   		this.SlProcreditMaterialsView = new SlEnterPriseProcreditMaterialsView({
			projectId : this.projectId,
			businessType : this.businessType,
			isHiddenEdit : false,
			isHidden:false,
			isHidden_materials : true,
			operationType : "SmallLoanBusiness"
		});
		//贷款必备条件	
		this.ourProcreditAssuretenetProductView = new OurProcreditAssuretenetProductView({
			isProduct:true,
			productId:this.productId,
			isAllReadOnly:true,
			isReadOnly:true,
			hiddenAdd:true,
			isShowBar:false,
			isHiddentbar:true,
			hiddenDel:true
		});
		//抵质押物
		this.dYMortgageViewProduct=new DZYMortgageView({
			projectId : this.projectId,
			titleText : '抵质押担保',
			businessType : this.businessType,
			isHiddenAddContractBtn:this.readOnly,
			isHiddenDelContractBtn:this.readOnly,
			isHiddenEdiContractBtn:this.readOnly,
			isHiddenRelieve:this.readOnly,
			isblHidden:this.readOnly,
			isRecieveHidden:this.readOnly,
			isgdHidden:this.readOnly,
			isHiddenAddBtn : this.readOnly,
			isHiddenDelBtn : this.readOnly,
			isHiddenEdiBtn : this.readOnly,
			formPanel:this.LendForm
		}),
		//保证担保
		this.baozMortgageView= new BaozMortgageView({
			projectId : this.projectId,
			titleText : '保证担保',
			businessType : this.businessType,
			isHiddenAddContractBtn:this.readOnly,
			isHiddenDelContractBtn:this.readOnly,
			isHiddenEdiContractBtn:this.readOnly,
			isHiddenRelieve:this.readOnly,
			isRecieveHidden:this.readOnly,
			isblHidden:this.readOnly,
			isgdHidden:this.readOnly,
			isHiddenAddBtn : this.readOnly,
			isHiddenDelBtn : this.readOnly,
			isHiddenEdiBtn : this.readOnly,
			formPanel:this.LendForm
		})
		//网审信息
		this.netCheckInfoView = new BpPersonNetCheckInfoView({
			projectId:this.projectId,
			personId:this.personId,
			isReadOnly : this.readOnly
		});
		//初审报告
		this.onceCheckReportView = new SlReportView({
			projectId : this.projectId,
			businessType : 'SmallLoan',
			Template:'OnceCheckReport',
			isHidden_report : this.readOnly
		});
		
		//资金款项
		this.projectInfoFinance= new ExtUD.Ext.newProjectInfoFinancePanel({
			isAllReadOnly:this.readOnly,
			isHiddencalculateBtn:true,
			isStartDateReadOnly:!this.readOnly,
			isStartDateReadOnly:this.readOnly,
		 	projectId:this.projectId,
			idDefinition:'personLoanFlow'+this.flag
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
				title : '<a name="sl_projectInfo_' +  this.flag + this.projectId
						+ '"><font color="red">01</font>项目基本信息</a>',
//				hidden : !(isGranted('seeSlProjectInfo_' + this.projectStatus)),
				items : [this.projectInfo]
			},{
				title : '<a name="sl_loandemand_' +  this.flag + this.projectId
						+ '"><font color="red">02</font>借款需求</a>',
//				hidden : !(isGranted('sl_loandemand_' + this.projectStatus)),
				items : [this.LendForm]
			},{
				title : '<a name="sl_personalCustomerInfo_' +  this.flag + this.projectId
						+ '"><font color="red">03</font>'+((this.oppositeType=="person_customer")?'个人':'企业') +'客户信息</a>',
//				hidden : !(isGranted('seeSlProjectInfo_' + this.projectStatus)),
				items : [(this.oppositeType=="company_customer")?this.perMain:this.panel]
			},(this.oppositeType=="company_customer")?{xtype:'hidden'}:{
				title : '<a name ="sl_contactPersonInfo_' + this.flag + this.projectId
						+ '"><font color="red">04</font>联系人信息</a>',
				items : [{
					xtype : 'fieldset',
					title :'【联系人信息】家庭联系人',
					bodyStyle : 'padding-left:0px',
					collapsible : true,
					labelAlign : 'right',
					autoHeight : true,
					items : [this.relationPersonInfo0]
				},{
					xtype : 'fieldset',
					title :'【联系人信息】工作证明人',
					bodyStyle : 'padding-left:0px',
					collapsible : true,
					labelAlign : 'right',
					autoHeight : true,
					items : [this.relationPersonInfo1]
				},{
					xtype : 'fieldset',
					title :'【联系人信息】紧急联系人',
					bodyStyle : 'padding-left:0px',
					collapsible : true,
					labelAlign : 'right',
					autoHeight : true,
					items : [this.relationPersonInfo2]
				}]
			},{
				title : '<a name="sl_loanmaterial_' + this.flag + this.projectId
						+ '"><font color="red">05</font>贷款材料清单</a>',
//				hidden : !(isGranted('seeSlProjectInfo_' + this.projectStatus)),
				items : [this.SlProcreditMaterialsView]
			},{
				title : '<a name="sl_loanrequire_' + this.flag + this.projectId
						+ '"><font color="red">06</font>贷款必备条件</a>',
//				hidden : !(isGranted('seeSlProjectInfo_' + this.projectStatus)),
				items : [this.ourProcreditAssuretenetProductView]
			},{
				title : '<a name="sl_guaranteeInfo_' + this.flag + this.projectId
						+ '"><font color="red">07</font>担保措施</a>',
//				hidden : !(isGranted('seeSlProjectInfo_' + this.projectStatus)),
				items : [this.dYMortgageViewProduct,this.baozMortgageView]
			},{
				title : '<a name="sl_networkaudit_' + this.flag + this.projectId
						+ '"><font color="red">08</font>网审信息</a>',
//				hidden : !(isGranted('seeSlProjectInfo_' + this.projectStatus)),
				items : [this.netCheckInfoView]
			},{
				title : '<a name="sl_checkreport_' + this.flag + this.projectId
						+ '"><font color="red">09</font>初审报告</a>',
//				hidden : !(isGranted('seeSlProjectInfo_' + this.projectStatus)),
				items : [this.onceCheckReportView]
			},{
				title : '<a name="sl_financeInfo_' + this.flag + this.projectId
						+ '"><font color="red">10</font>资金款项信息</a>',
//				hidden : !(isGranted('seeSlProjectInfo_' + this.projectStatus)),
				items : [this.projectInfoFinance]
			}]
		});

		this.loadData({
			url : __ctxPath + '/project/getSmallLoanProjectInfoSlSmallloanProject.do?slProjectId='+this.projectId,
			method : "POST",
			preName : ['person', 'slSmallloanProject','bpProductParameter','workCompany','bpMoneyBorrowDemand','creditRating'],
			root : 'data',
			success : function(response, options) {
				var respText = response.responseText;
				var alarm_fields = Ext.util.JSON.decode(respText);
			//	expandFieldSet(this.formPanel)

				this.getCmpByName('projectMoney1').setValue(Ext.util.Format.number(alarm_fields.data.slSmallloanProject.projectMoney, '0,000.00'))
				fillData(this,alarm_fields,'personLoanFlow'+this.flag);

			}
		});
		this.formPanel = new Ext.FormPanel();
		this.formPanel.add(this.outPanel);
		this.add(this.formPanel);
		this.doLayout();
	}// 初始化UI结束
	,
	saveAllDatas : function() {
		var oppType = this.oppositeType;
		var vDates = "";
		var source = "";

		this.formPanel.getForm().submit({
			clientValidation : false,
			url : __ctxPath + '/project/saveApproveProjectInfoSlSmallloanProject.do',
			params : {
			},
			method : 'post',
			waitMsg : '数据正在提交，请稍后...',
			success : function(fp, action) {
				var object = Ext.util.JSON.decode(action.response.responseText)
				Ext.ux.Toast.msg('操作信息', '保存信息成功!');
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