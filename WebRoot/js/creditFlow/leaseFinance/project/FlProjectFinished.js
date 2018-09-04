/**
 * @author lisl
 * @description 小额贷款结项
 * @extends Ext.Panel
 */
FlProjectFinished = Ext.extend(Ext.Panel, {
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		this.initUIComponents();
		FlProjectFinished.superclass.constructor.call(this, {
			id : 'FlProjectFinished_' + this.record.data.projectId,// id属性值前缀,通过是否包含"Edit"来区分查看和编辑页面
			title : "贷款结项-" + this.record.data.projectName,
			iconCls : '',
			tbar : this.toolbar,
			items : []
		});
	},
	initUIComponents : function() {
		var jsArr = [
				__ctxPath + '/js/commonFlow/ExtUD.Ext.js',//客户信息 项目基本信息
				__ctxPath + '/js/selector/UserDialog.js',
				__ctxPath + '/js/creditFlow/common/EnterpriseShareequity.js',//股东信息
				__ctxPath + '/js/creditFlow/leaseFinance/UpdateLeaseFinanceMaterialsWin.js',//抵质押担保更新  for  leasefinance
				__ctxPath + '/js/creditFlow/leaseFinance/enterpriseBusiness/EnterpriseBusinessUI.js',//项目基本信息
				__ctxPath + '/js/creditFlow/leaseFinance/LeaseFinanceMaterialsForm.js',//抵质押担保材料上传表单
				__ctxPath + '/js/creditFlow/leaseFinance/LeaseFinanceMaterialsView.js',//抵质押担保上传
				__ctxPath + '/js/creditFlow/leaseFinance/LeaseFinanceAddDzyMortgageWin.js',//抵质押担保上传
				/*租赁标的清单start*/
				__ctxPath + '/js/creditFlow/leaseFinance/leaseobject/LeaseObjectList.js',//租赁标的清单
				__ctxPath + '/js/creditFlow/leaseFinance/leaseobject/LeaseObjectInsuranceInfo.js',//租赁物保险信息
				__ctxPath + '/js/creditFlow/leaseFinance/leaseobject/AddLeaseObjectWin.js',//租赁标的增加
				__ctxPath + '/js/creditFlow/leaseFinance/leaseobject/LeaseFinanceSuppliorInfo.js',//供货方信息   必须要加载  在win中延迟加载，第一次访问不了  add  by gao
				__ctxPath + '/js/creditFlow/leaseFinance/leaseobject/selectSupplior.js',//供货方信息  弹窗
//				__ctxPath + '/publicmodel/uploads/js/upload.js',
//				__ctxPath + '/js/fileupload/FlexUploadDialog.js',//供货方信息  弹窗
				__ctxPath + '/js/creditFlow/leaseFinance/leaseobject/LeaseFinanceUploads.js',//供货方信息  弹窗
				/*租赁标的清单end*/
				__ctxPath + '/js/creditFlow/finance/SlActualToCharge.js',//实际收费项目
				__ctxPath + '/js/creditFlow/guarantee/materials/SlEnterpriseProcreditMaterialsForm.js',// 贷款材料
				__ctxPath + '/js/creditFlow/guarantee/materials/SlEnterPriseProcreditMaterialsView.js',// 贷款材料
				__ctxPath + '/js/creditFlow/report/SlReportView.js',// 调查报告
				__ctxPath + '/js/creditFlow/finance/SlActualToCharge.js',
				__ctxPath + '/js/creditFlow/customer/bankrelationperson/bankDaRelationPersonList.js',
				__ctxPath + '/js/creditFlow/smallLoan/contract/SeeThirdContractWindow.js',// 查看担保措施合同详情
				__ctxPath + '/js/creditFlow/smallLoan/contract/OperateThirdContractWindow.js',// 编辑反担保措施合同
//				__ctxPath + '/js/creditFlow/assuretenet/SlProcreditAssuretenetForm.js',// 贷款准入原则
//				__ctxPath + '/js/creditFlow/guarantee/enterpriseBusiness/EnterpriseEvaluation.js', //客户资信评估
				__ctxPath + '/js/creditFlow/leaseFinance/finance/LeaseFinanceInfoPanel.js',
				__ctxPath + '/js/creditFlow/leaseFinance/finance/LeaseFundIntentView.js',
				__ctxPath + '/js/creditFlow/leaseFinance/finance/LeaseLoadFinanceData.js',
				__ctxPath+'/js/creditFlow/smallLoan/contract/SlContractView.js',//合同制作
				__ctxPath + '/js/creditFlow/guarantee/contract/LetterAndBookView.js',// 担保意向书、对外担保承诺函
				__ctxPath + '/js/creditFlow/pawn/materials/PawnUploads.js',
				__ctxPath + '/js/creditFlow/document/LoanSettlementDocument.js'
			];
		$ImportSimpleJs(jsArr, this.constructPanel, this);
		this.toolbar = new Ext.Toolbar({
			items : [{
				iconCls : 'btn-save',
				text : '提交',
				scope : this,
				handler : function() {
					var projectId=this.record.data.projectId;
					var businessType = this.record.data.businessType;
//				var flag = 0;// 财务登记节点以及之前保存保证金
//				if (this.taskSequence > 2000) {
//					flag = 1;// 财务登记节点之后保存保证金
//				}
				//款项信息数据 获取start
		        var gridPanel=this.leaseFundIntentView;
		        var fundIntentJson=gridPanel.getGridDate()
		        //款项信息数据  获取end
		        //actualToCharge start 
		        var slActualToCharge=this.slActualToCharge
		        var slActualToChargeJson=slActualToCharge.getGridDate();
		        //actualToCharge end
				this.formPanel.getForm().submit({
					clientValidation : false,
					url : __ctxPath
							+ '/creditFlow/leaseFinance/project/loanFinishedFlLeaseFinanceProject.do',
					params : {
						projectId : projectId,
						businessType : businessType
					},
					method : 'post',
					scope : this,
					waitMsg : '数据正在提交，请稍后...',
					success : function(fp, action) {
						
						if (res.success) {
										Ext.getCmp('centerTabPanel').remove('SlProjectFinished_'+ SlProjectId);//这个是关闭当前页面
										Ext.getCmp('LeaseFinanceProjectManager_5').get(2).getStore().reload();
						}else{
										Ext.ux.Toast.msg('信息提示', '该项目有对账金额尚未结清,不能执行贷款结清操作！');
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
			}]
		})
	},// 初始化组件
	constructPanel : function() {
		this.businessType = this.record.data.businessType
		this.projectId = this.record.data.projectId;
		this.operationType = this.record.data.operationType; // not sure
		this.projectStatus = this.record.data.projectStatus;
//		this.bmStatus = this.record.data.bmStatus;
		this.taskId = this.record.data.taskId;
		this.flowType = this.record.data.flowType;
		this.isHiddenZM = true;
		this.index = 18;
		if (this.flowType == 'zmNormalFlow') {
			this.isHiddenZM = false;
			this.index = 20;
		}
		// 项目基本信息     ok
		 this.projectInfo = new EnterpriseBusinessProjectInfoPanel({
			   isAllReadOnly:true
		    });
		this.customerInfo = "";
		this.customerTitle = "企业客户信息";
		// 客户基本信息   ok
		if (this.record.data.oppositeType == 'person_customer') {
			this.customerInfo = new ExtUD.Ext.PeerPersonMainInfoPanel({
				isAllReadOnly : true,
				isHidden:true
			});
			this.customerTitle = "个人客户信息";
		} else if (this.record.data.oppositeType == 'company_customer') {
			this.customerInfo = new ExtUD.Ext.PeerMainInfoPanel({
				projectId : this.projectId,
				bussinessType : 'LeaseFinance',
				isHidden:true,
				isAllReadOnly : true
			});
		}
		 //租赁标的物信息  ok
		this.LeaseObjectList = new LeaseObjectList({projectId:this.projectId,readOnly:true,isBuyBackEditable:true});//,readOnly:true
		
		// 资金款项信息  ok
	    this.projectInfoFinance= new LeaseFinanceInfoPanel({
//				isDiligenceReadOnly : true,
				projectId : this.projectId,
				isStartDateReadOnly:false,
				isAllReadOnly:true,
				isStartDateReadOnly:true,
				idDefinition : 'leaseFinanceliucheng'+this.taskId ,
				isHiddencalculateBtn : true,
				isHiddenbackBtn : true ,
				isHiddenParams : true
			});
		this.leaseFundIntentView=new LeaseFundIntentView({
			projectId : this.projectId,
			object : this.projectInfoFinance,
			projectInfoObject : this.projectInfo,
			businessType : this.businessType,
			isHiddenAddBtn:true,
			isHiddenDelBtn:true,
			isHidden1:true,//不可编辑 时间
			isHiddenResBtn1:true,//手动对账
			isHiddenseeqlideBtn:false,//差看单项流水记录
			isHiddenseesumqlideBtn:false,//项目总流水记录
			isHiddenExcel:false,//导出excel
			isHiddenOverdue:true,
			isHiddenautocreateBtn : true
		})
		// 经办费用清单  ok
		this.slActualToCharge = new SlActualToCharge({
			projId : this.projectId,
			isHidden:true,//隐藏
			businessType :this.businessType      //小贷
		});
			
		//办理文件 ok
		/*this.manageMaterialsView =new SlReportView({
	    	projectId : this.projectId,
	    	businessType : 'LeaseFinance',
	    	isHiddenColumn : false,
	    	isShowBorder : true,
	    	ReportTemplate:'LeaseFinanceManageFileReport',
	    	isHidden : false,
	    	hiddenUpLoad:true,// 隐藏上传按钮
	    	hiddenDownLoad:true,//隐藏下载按钮
	    	isHidden_report:false,
	    	isHiddenAffrim : true
	    });*/
		this.mortgageView = new DZYMortgageView({
						projectId : this.projectId,
						titleText : '抵质押担保',
						businessType : this.businessType,
						isHiddenAddContractBtn:true,
						isHiddenDelContractBtn:true,
						isHiddenEdiContractBtn:true,
						isHiddenRelieve:false,
						isHiddenAddBtn:true,//增
						isHiddenDelBtn:true,//删
						isHiddenEdiBtn:true,//改  隐藏
						isRecieveHidden:true,
						isRelieveEdit:true,//解除抵质押物
						isblHidden:true,
						isgdHidden:true
					});
		this.baozMortgageView = new BaozMortgageView({
						projectId : this.projectId,
						titleText : '保证担保',
						businessType : this.businessType,
						isHiddenAddContractBtn:true,
						isHiddenDelContractBtn:true,
						isHiddenEdiContractBtn:true,
						isHiddenRelieve:true,
						isHiddenAddBtn:true,//增
						isHiddenDelBtn:true,//删
						isHiddenEdiBtn:true,//改  隐藏
						isblHidden:true,
						isgdHidden:true,
						isRecieveHidden:true
					});
		this.LoanSettlementDocument=new LoanSettlementDocument({
			businessType : 'LeaseFinance',
			LBTemplate : 'LoanSettlement',
			projectId: this.projectId,
			isHidden:false,
			titleText:'贷款结清文档'
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
				title : '<a name="fl_projectBaseInfo_see' + this.projectId
						+ '"><font color="red">01</font>项目控制信息</a>',
//				hidden : !(isGranted('_seeflProjectInfo_fl' + this.projectStatus + this.bmStatus)||isGranted('_editflProjectInfo_fl' + this.projectStatus + this.bmStatus)),
				items : [this.projectInfo]
			}, {
				title : '<a name ="fl_customerInfo_see' + this.projectId
						+ '"><font color="red">02</font>' + this.customerTitle
						+ '</a>',
//				hidden : !(isGranted('_seeflCustomerInfo_fl' + this.projectStatus + this.bmStatus)||isGranted('_editflCustomerInfo_fl'+this.projectStatus + this.bmStatus)),
				items : [this.customerInfo]
			}, {
				title : '<a name="fl_leaseObject_see' + this.projectId
						+ '"><font color="red">03</font>租赁标的物信息</a>',
//				hidden : !(isGranted('_seeflLeaseObject_fl' + this.projectStatus + this.bmStatus)||isGranted('_editflLeaseObject_fl' + this.projectStatus + this.bmStatus)),
				items : [this.LeaseObjectList]
			}, {
				title : '<a name="fl_financeInfo_see' + this.projectId
						+ '"><font color="red">04</font>资金款项信息</a>',
//				hidden : !(isGranted('_seeflFinanceBaseInfo_fl' + this.projectStatus + this.bmStatus)||isGranted('_editflFinanceBaseInfo_fl' + this.projectStatus + this.bmStatus)),
				bodyStyle : 'padding-top:5px',
				items : [this.projectInfoFinance,this.leaseFundIntentView, {
					xtype : 'panel',
//					hidden : !(isGranted('_seeflActualToCharge_fl' + this.projectStatus + this.bmStatus)||isGranted('_editflActualToCharge_fl' + this.projectStatus + this.bmStatus)),
					border : false,
					bodyStyle : 'margin-bottom:5px',
					html : '<br/><B><font class="x-myZW-fieldset-title"><a name="fl_actualToChargeInfo_see'
							+ this.projectId
							+ '">【<font color="red">05</font>手续收支清单】</a></font></B><font class="x-myZW-fieldset-title">（</font>颜色预警：<font color=red>逾期款项</font>&nbsp;&nbsp<font style="line-height:20px">未结清项</font>&nbsp;&nbsp<font color=gray>已结清项</font><font class="x-myZW-fieldset-title" >）：</font>'
				}, this.slActualToCharge]
			},{
				title : '<a name="fl_mortgage_see' + this.projectId
						+ '"><font color="red">06</font>担保措施管理</a>',
//				hidden : !(isGranted('_seeCreditMaterials_fl' + this.projectStatus + this.bmStatus)||isGranted('_editCreditMaterials_fl' + this.projectStatus + this.bmStatus)),
				items : [this.mortgageView,this.baozMortgageView]
			},{
				title : '贷款结清文档',
				items : [this.LoanSettlementDocument]
			}]
		});

			this.loadData({
    	 	url : __ctxPath + '/creditFlow/leaseFinance/project/getInfoFlLeaseFinanceProject.do?flProjectId='+this.projectId+'&flTaskId='+this.taskId,
			method : "POST",
			preName : ['enterprise', 'person', 'flLeaseFinanceProject','customerBankRelationPerson','enterpriseBank',"businessType"],
			root : 'data',
			success : function(response, options) {
		              var respText = response.responseText;  
				      var alarm_fields = Ext.util.JSON.decode(respText); 
				      this.getCmpByName('flLeaseFinanceProject.mineName').setValue(alarm_fields.data.mineName);
				     //回填款项信息数据 start
					this.getCmpByName('projectMoney1').setValue(Ext.util.Format.number(alarm_fields.data.flLeaseFinanceProject.projectMoney,'0,000.00'));
					this.getCmpByName('leaseDepositMoney1').setValue(Ext.util.Format.number(alarm_fields.data.flLeaseFinanceProject.leaseDepositMoney,'0,000.00'));
					this.getCmpByName('allMoney1').setValue(Ext.util.Format.number(alarm_fields.data.flLeaseFinanceProject.allMoney,'0,000.00'));
					this.getCmpByName('leaseRetentionFeeMoney1').setValue(Ext.util.Format.number(alarm_fields.data.flLeaseFinanceProject.leaseRetentionFeeMoney,'0,000.00'));
					this.getCmpByName('rentalFeeMoney1').setValue(Ext.util.Format.number(alarm_fields.data.flLeaseFinanceProject.rentalFeeMoney,'0,000.00'));
					this.getCmpByName('rentalMoney1').setValue(Ext.util.Format.number(alarm_fields.data.flLeaseFinanceProject.rentalMoney,'0,000.00'));
				    //回填款项信息数据 end
				     
				      // 初始化币种 ,贷款用途结束  
				      this.getCmpByName('appUsersOfA').clearInvalid();
				      fillLeaseData(this, alarm_fields, 'leaseFinanceliucheng'+this.taskId)
			}
				/*
			//url : __ctxPath + '/creditFlow/getInfoCreditProject.do?taskId=' + this.projectId + '&type=LeaseFinance',
			url : __ctxPath + '/creditFlow/guarantee/EnterpriseBusiness/getInfoGLLeaseFinanceloanProject.do?glProjectId='+this.projectId,
			preName : ['enterprise', 'person', 'gLLeaseFinanceloanProject','customerBankRelationPerson', "businessType"],
			root : 'data',
			success : function(response, options) {
				var respText = response.responseText;
				var alarm_fields = Ext.util.JSON.decode(respText);
				this.getCmpByName('projectMoney1').setValue(Ext.util.Format.number(alarm_fields.data.gLLeaseFinanceloanProject.projectMoney,'0,000.00'));
				this.getCmpByName('earnestmoney1').setValue(Ext.util.Format.number(alarm_fields.data.gLLeaseFinanceloanProject.earnestmoney,'0,000.00'));
			}
		*/})
		this.formPanel = new Ext.FormPanel();
		this.formPanel.add(this.outPanel);
		this.add(this.formPanel);
		this.doLayout();
	}// 初始化UI结束
});