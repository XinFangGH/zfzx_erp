/**
 * @author lisl
 * @class GuaranteeProjectInfo
 * @description 担保项目信息
 * @extends Ext.Panel
 */
LeaseFinanceProjectInfo = Ext.extend(Ext.Panel, {
	bmStatus:0,
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		this.initUIComponents();
		LeaseFinanceProjectInfo.superclass.constructor.call(this, {
			autoScroll : true,
			height : document.body.clientHeight - 245,
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
				__ctxPath + '/publicmodel/uploads/js/upload.js',
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
				__ctxPath+'/js/creditFlow/archives/PlArchivesMaterialsView.js',//归档材料清单
				__ctxPath + '/js/creditFlow/report/SlRiskReportView.js',// 风险综合分析报告
				__ctxPath + '/js/creditFlow/pawn/materials/PawnUploads.js',
				//租后管理控件
				__ctxPath + '/js/creditFlow/leaseFinance/project/flDesignSupervisionManagePlan.js',
				__ctxPath + '/js/creditFlow/smallLoan/finance/SlSuperviseRecordListView.js',// 展期记录
				__ctxPath + '/js/creditFlow/leaseFinance/project/LeaseFlEarlyrepaymentRecordView.js',// 提前还款记录
				__ctxPath + '/js/creditFlow/leaseFinance/project/LeaseFlEarlyrepaymentRecordEditInfoView.js',
				__ctxPath + '/js/creditFlow/leaseFinance/project/LeaseFlEarlyrepaymentRecordEditInfo.js',
				__ctxPath + '/js/creditFlow/leaseFinance/project/LeaseFlEarlyrepaymentRecordNavigation.js',
				__ctxPath + '/js/creditFlow/leaseFinance/project/LeaseFlEarlyrepaymentRecordSeeInfo.js',
				__ctxPath + '/js/creditFlow/smallLoan/finance/FinanceEarlyRepaymentPanel.js',
				__ctxPath + '/js/creditFlow/smallLoan/finance/EarlyRepaymentSlFundIntent.js',
				__ctxPath + '/js/creditFlow/finance/SlFundIntentViewVM.js',
				__ctxPath + '/js/creditFlow/leaseFinance/enterpriseBusiness/EnterpriseBusinessUI.js',//项目基本信息
				__ctxPath + '/js/creditFlow/leaseFinance/project/LeaseFlAlterAccrualRecordView.js',
				__ctxPath + '/js/creditFlow/leaseFinance/project/FlAlterAccrualRecordEditInfoView.js',
				__ctxPath + '/js/creditFlow/leaseFinance/project/FlAlterAccrualRecordNavigation.js',
				__ctxPath + '/js/creditFlow/leaseFinance/project/FlAlterAccrualRecordEditInfo.js',
				__ctxPath + '/js/creditFlow/leaseFinance/project/FlAlterAccrualRecordSeeInfo.js',
				__ctxPath + '/js/creditFlow/smallLoan/finance/FinanceAlterAccrualPanel.js',
				__ctxPath + '/js/creditFlow/smallLoan/finance/AlterAccrualSlFundIntent.js'
			];
		$ImportSimpleJs(jsArr, this.constructPanel, this);
	},// 初始化组件
	constructPanel : function() {
		this.businessType = this.record.data.businessType
		this.projectId = this.record.data.projectId;
		this.operationType = this.record.data.operationType; // not sure
		this.projectStatus = this.record.data.projectStatus;
//		this.bmStatus = this.record.data.bmStatus;
		this.taskId = this.record.data.taskId;
		this.flowType = this.record.data.flowType;
		this.oppositeType=this.record.data.oppositeType;
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
		this.LeaseObjectList = new LeaseObjectList({projectId:this.projectId,readOnly:true});//,readOnly:true
		
		
		/*this.financeInfo = new ProjectInfoLeaseFinanceFinancePanel({
			isAllReadOnly : true,
			isReadOnlyDateDueTime:true,
			isHiddenPanel : !isGranted('gtFinanceBaseInfo_fl' + this.projectStatus + this.bmStatus)
		});*/
		
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
			isHiddenResBtn1:true,//手动对账
			isHiddenseeqlideBtn:true,//差看单项流水记录
			isHiddenseesumqlideBtn:true,//项目总流水记录
			isHiddenExcel:true,//导出excel
			isHiddenOverdue:true,
			isHiddenautocreateBtn : true
		})
		// 经办费用清单  ok
		this.slActualToCharge = new SlActualToCharge({
			projId : this.projectId,
			isHidden:true,//隐藏
			businessType :this.businessType      //小贷
		});
		//业务材料  ok
		this.SlProcreditMaterialsView = new SlEnterPriseProcreditMaterialsView({
			     projectId : this.projectId,
			     isHidden_materials:true,//隐藏添加、隐藏 按钮
			     businessType:this.businessType,
			     isHiddenArchive : true,
			     operationType:this.operationType
		    });
		// 尽职调查报告  ok
		this.SlReportView = new SlReportView({
		    	projectId : this.projectId,
		    	businessType : this.businessType,
		    	isHiddenColumn : false,
		    	isShowBorder : true,
		    	isHidden : false,
		    	isHidden_report:false,
		    	hiddenDownLoad:true,
		    	hiddenUpLoad:true,
		    	isHiddenAffrim : true
		    });
		//合同列表  ok
		      this.slContractView = new SlContractView({
				projId : this.projectId,
				isSignHidden : false,
				isqsEdit : true,
				businessType : this.businessType,
//				HTLX : 'DBDQ',
				isHiddenAddBtn : true,
				isHiddenDelBtn : true,
				isHiddenEdiBtn : true,
				htType : "leaseFinanceContract",
				isHidden : true,
				isqsEdit:false,
				isLawCheckHidden:false, 
				isSignHidden : false
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
		this.manageMaterialsView =new PawnUploads({
							projectId : this.projectId,
					    	businessType : this.businessType,
					    	tableName : 'flLeaseFinanceProjectFile',
					    	typeisfile: 'flLeaseFinanceProjectFile',
					    	isHidden:true,//上传
					    	issubmitHidden:false,//提交时间，提交备注
					    	issubmitEdit:false,
					    	isgdHidden:true,//收到时间，收到备注
					    	isgdEdit:false,
					    	titleName : '办理文件'
						})
		this.mortgageView = new DZYMortgageView({
						projectId : this.projectId,
						titleText : '抵质押担保',
						businessType : this.businessType,
						isHiddenAddContractBtn:true,
						isHiddenDelContractBtn:true,
						isHiddenEdiContractBtn:true,
						isHiddenRelieve:true,
						isHiddenAddBtn:true,//增
						isHiddenDelBtn:true,//删
						isHiddenEdiBtn:true,//改  隐藏
						isRecieveHidden:true,
						isblHidden:true,
						isgdHidden:true
					}),
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
			//风险分析报告   ok 3
		this.SlRiskReportView = new SlRiskReportView({
			projectId : this.projectId,
				businessType : this.businessType,
				isHidden_riskReport : true
		    });
		//归档材料清单  ok3
		this.plArchivesMaterialsView = new PlArchivesMaterialsView({
							projectId : this.projectId,
							businessType:this.businessType,
							isHiddenAffrim : false,
							isEditAffrim : false,
							isAffrimEdit:false,
							affrimEditable:false,
							isHiddenRecive:false,
							isHidden_materials:true
						}); 
		//监管计划
		this.supervisionManagePanel = new flDesignSupervisionManagePlan({
							projectId : this.projectId,
							businessType:this.businessType,
							isHidden:true,
							isHeightNull:true
						}).gridPanel;
		//展期记录
		this.slSuperviseRecordView = new SlSuperviseRecordListView({
			projectId : this.projectId,
			projectRecord : this.record,
			businessType:this.businessType,
			isHidden: true//编辑删除查看不可用
		});
		//提前还款记录
		this.earlyrepaymentRecord=new LeaseFlEarlyrepaymentRecordView({
			projectId : this.projectId,
			businessType : this.record.data.businessType,
			isEditHidden: true,
			isDeleteHidden:true,
			projectStatus : this.projectStatus,
			oppositeType:this.oppositeType
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
				hidden : !(isGranted('_seeflProjectInfo_fl' + this.projectStatus + this.bmStatus)||isGranted('_editflProjectInfo_fl' + this.projectStatus + this.bmStatus)),
				items : [this.projectInfo]
			}, {
				title : '<a name ="fl_customerInfo_see' + this.projectId
						+ '"><font color="red">02</font>' + this.customerTitle
						+ '</a>',
				hidden : !(isGranted('_seeflCustomerInfo_fl' + this.projectStatus + this.bmStatus)||isGranted('_editflCustomerInfo_fl'+this.projectStatus + this.bmStatus)),
				items : [this.customerInfo]
			}, {
				title : '<a name="fl_leaseObject_see' + this.projectId
						+ '"><font color="red">03</font>租赁标的物信息</a>',
				hidden : !(isGranted('_seeflLeaseObject_fl' + this.projectStatus + this.bmStatus)||isGranted('_editflLeaseObject_fl' + this.projectStatus + this.bmStatus)),
				items : [this.LeaseObjectList]
			}, {
				title : '<a name="fl_financeInfo_see' + this.projectId
						+ '"><font color="red">04</font>资金款项信息</a>',
				hidden : !(isGranted('_seeflFinanceBaseInfo_fl' + this.projectStatus + this.bmStatus)||isGranted('_editflFinanceBaseInfo_fl' + this.projectStatus + this.bmStatus)),
				bodyStyle : 'padding-top:5px',
				items : [this.projectInfoFinance,this.leaseFundIntentView, {
					xtype : 'panel',
					hidden : !(isGranted('_seeflActualToCharge_fl' + this.projectStatus + this.bmStatus)||isGranted('_editflActualToCharge_fl' + this.projectStatus + this.bmStatus)),
					border : false,
					bodyStyle : 'margin-bottom:5px',
					html : '<br/><B><font class="x-myZW-fieldset-title"><a name="fl_actualToChargeInfo_see'
							+ this.projectId
							+ '">【<font color="red">05</font>手续收支清单】</a></font></B><font class="x-myZW-fieldset-title">（</font>颜色预警：<font color=red>逾期款项</font>&nbsp;&nbsp<font style="line-height:20px">未结清项</font>&nbsp;&nbsp<font color=gray>已结清项</font><font class="x-myZW-fieldset-title" >）：</font>'
				}, this.slActualToCharge]
			},{
				title : '<a name="fl_mortgage_see' + this.projectId
						+ '"><font color="red">06</font>担保措施管理</a>',
				hidden : !(isGranted('_seeCreditMaterials_fl' + this.projectStatus + this.bmStatus)||isGranted('_editCreditMaterials_fl' + this.projectStatus + this.bmStatus)),
				items : [this.mortgageView,this.baozMortgageView]
			}, {
				title : '<a name="fl_businessFiles_see' + this.projectId
						+ '"><font color="red">07</font>业务材料</a>',
				hidden : !(isGranted('_seeBusinessMaterials_fl' + this.projectStatus + this.bmStatus)||isGranted('_editBusinessMaterials_fl' + this.projectStatus + this.bmStatus)),
				items : [this.SlProcreditMaterialsView]
			}, {
				title : '<a name="fl_riskReportView_see' + this.projectId
						+ '"><font color="red">08</font>合同列表</a>',
				hidden : !(isGranted('_seeContract_fl' + this.projectStatus + this.bmStatus)||isGranted('_editContract_fl' + this.projectStatus + this.bmStatus)),//seeContract_fl  新增 ★★★add by gao
				items : [this.slContractView]
			},{
				title : '<a name="fl_manageFiles_see' + this.projectId
						+ '"><font color="red">09</font>办理文件</a>',
				hidden : !(isGranted('_seeManageFiles_fl' + this.projectStatus + this.bmStatus)||isGranted('_editManageFiles_fl' + this.projectStatus + this.bmStatus)),
				items : [this.manageMaterialsView]
			},{
				title : '<a name="fl_report_see' + this.projectId
						+ '"><font color="red">10</font>调查报告</a>',
				hidden : !(isGranted('_seeReport_fl' + this.projectStatus + this.bmStatus)||isGranted('_editReport_fl' + this.projectStatus + this.bmStatus)),
				items : [this.SlReportView]
			},{
				title : '<a name="fl_riskReport_see' + this.projectId
						+ '"><font color="red">11</font>风险分析报告</a>',
				hidden : !(isGranted('_seeRiskReport_fl' + this.projectStatus + this.bmStatus)||isGranted('_editReport_fl' + this.projectStatus + this.bmStatus)),
				items : [this.SlRiskReportView]
			},{
				title : '<a name="fl_archivesMaterials_see' + this.projectId
						+ '"><font color="red">12</font>归档材料清单</a>',
				hidden : !(isGranted('_seeArchivesMaterials_fl' + this.projectStatus + this.bmStatus)||isGranted('_editReport_fl' + this.projectStatus + this.bmStatus)),
				items : [this.plArchivesMaterialsView]
			},{
				title : '<a name="fl_supervisionManagement_see' + this.projectId
						+ '"><font color="red">13</font>监管记录</a>',
				hidden : !(isGranted('_seeSuperVisionManagement_fl' + this.projectStatus + this.bmStatus)||isGranted('_editSuperVisionManagement_fl'+this.projectStatus+this.bmStatus)),
				items : [this.supervisionManagePanel]
			},{
				title : '<a name="fl_superviseRecord_see' + this.projectId
						+ '"><font color="red">14</font>展期记录</a>',
				hidden : !(isGranted('_seeSuperviseRecord_fl' + this.projectStatus + this.bmStatus)||isGranted('_editSuperviseRecord_fl' + this.projectStatus + this.bmStatus)),
				items : [this.slSuperviseRecordView]
			},{
				title : '<a name="fl_earlyrepaymentRecord_see' + this.projectId
						+ '"><font color="red">15</font>提前还款记录</a>',
				hidden : !isGranted('_seeEarlyrepaymentRecord_fl' + this.projectStatus + this.bmStatus),
				items : [this.earlyrepaymentRecord]
			},{
				title : '<a name="fl_aflterAccrualRecord_see' + this.projectId
						+ '"><font color="red">16</font>利率变更记录</a>',
				hidden : !(isGranted('_seeAflterAccrualRecord_fl' + this.projectStatus + this.bmStatus)||isGranted('_editAflterAccrualRecord_fl' + this.projectStatus + this.bmStatus)),
				items : [new LeaseFlAlterAccrualRecordView({
							projectId : this.projectId,
							isEditHidden:true,
							oppositeType:this.oppositeType,
							projectStatus : this.projectStatus,
							bmStatus:this.bmStatus,
							businessType : this.record.data.businessType
						})]
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
		this.add(this.outPanel);
		this.doLayout();
	}// 初始化UI结束

});