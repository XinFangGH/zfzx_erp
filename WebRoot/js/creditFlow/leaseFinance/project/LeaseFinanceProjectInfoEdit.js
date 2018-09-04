/**
 * @author lisl
 * @class GuaranteeProjectInfoEdit
 * @description 担保项目信息
 * @extends Ext.Panel
 */
LeaseFinanceProjectInfoEdit = Ext.extend(Ext.Panel, {
	bmStatus:0,
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		this.initUIComponents();
		LeaseFinanceProjectInfoEdit.superclass.constructor.call(this, {
			tbar : this.toolbar,
			border : false,
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
//				__ctxPath + '/publicmodel/uploads/js/upload.js',
//				__ctxPath + '/js/fileupload/FlexUploadDialog.js',//供货方信息  弹窗
				__ctxPath + '/js/creditFlow/leaseFinance/leaseobject/LeaseFinanceUploads.js',//供货方信息  弹窗
				/*租赁标的清单end*/
				__ctxPath + '/js/creditFlow/finance/SlActualToCharge.js',//实际收费项目
				__ctxPath + '/js/creditFlow/guarantee/materials/SlEnterpriseProcreditMaterialsForm.js',// 贷款材料
				__ctxPath + '/js/creditFlow/guarantee/materials/SlEnterPriseProcreditMaterialsView.js',// 贷款材料
				__ctxPath + '/js/creditFlow/report/SlReportView.js',// 调查报告
				__ctxPath + '/js/creditFlow/finance/SlActualToCharge.js',
				__ctxPath + '/js/creditFlow/report/SlRiskReportView.js',// 风险综合分析报告
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
		// 项目基本信息     ok2  ok3
		this.projectInfo = new EnterpriseBusinessProjectInfoPanel({
			   isDiligenceReadOnly : isGranted('_editflProjectInfo_fl'+this.projectStatus+this.bmStatus),//为true则为与尽职调查编辑内容相同,
			   isMineNameEditable:true
		    });
		this.customerInfo = "";
		this.customerTitle = "企业客户信息";
		// 客户基本信息   ok2   ok3
		if (this.record.data.oppositeType == 'person_customer') {
			this.customerInfo = new ExtUD.Ext.PeerPersonMainInfoPanel({
				isAllReadOnly : true,
				isHidden:true
			});
			this.customerTitle = "个人客户信息";
		} else if (this.record.data.oppositeType == 'company_customer') {
			this.customerInfo = new ExtUD.Ext.PeerMainInfoPanel({
				projectId : this.projectId,
			    bussinessType:this.businessType,
			    isHidden:true,
			    isAllReadOnly:false,
				isEnterpriseShortNameReadonly : true,
				isEditEnterprise : isGranted('_editflCustomerInfo1_fl'+this.projectStaus+this.bmStatus),
				isGudongDiseditable : isGranted('_editflGudongInfo_fl'+this.projectStatus + this.bmStaus)
			 });
		}
		 //租赁标的物信息  ok2   ok3
		this.LeaseObjectList = new LeaseObjectList({
					projectId : this.projectId,
					manageLeaseObject : false,
					readOnly:!isGranted('_editflLeaseObjectInfo_fl'+this.projectStatus+this.bmStatus)
				});//,readOnly:true
		
		
		// 资金款项信息  ok2   ok3
	    this.projectInfoFinance= new LeaseFinanceInfoPanel({
//				isDiligenceReadOnly : true,
				projectId : this.projectId,
				isAllReadOnly:!isGranted('_editflFinanceData_fl'+this.projectStatus+this.bmStatus),
				isStartDateReadOnly:!isGranted('_editflFinanceStartDate_fl'+this.projectStatus+this.bmStatus),
				idDefinition : 'leaseFinanceliucheng'+this.taskId ,
				isHiddencalculateBtn : true,//
				isHiddenbackBtn : false ,
				isHiddenParams : false
			});
		this.leaseFundIntentView=new LeaseFundIntentView({
			projectId : this.projectId,
			object : this.projectInfoFinance,
			projectInfoObject : this.projectInfo,
			businessType : this.businessType,
			isHiddenAddBtn:!isGranted('_editflFinance_addRecord_fl'+this.projectStatus+this.bmStatus),
			isHiddenDelBtn:!isGranted('_editflFinance_delRecord_fl'+this.projectStatus+this.bmStatus),
			isHiddenResBtn1:!isGranted('_editflFinanceManualReset_fl'+this.projectStatus+this.bmStatus),//手动对账
			isHiddenseeqlideBtn:!isGranted('_editflFinanceSeeFlow_fl'+this.projectStatus+this.bmStatus),//差看单项流水记录
			isHiddenseesumqlideBtn:!isGranted('_editflFinanceSeeAllFlow_fl'+this.projectStatus+this.bmStatus),//项目总流水记录
			isHiddenExcel:!isGranted('_editflFinanceExtractExcel_fl'+this.projectStatus+this.bmStatus),//导出excel
			isHiddenOverdue:false,
			isHiddenautocreateBtn : !isGranted('_editflFinanceAutoCreate_fl'+this.projectStatus+this.bmStatus)
		})
		// 经办费用清单  ok  ok3
		this.slActualToCharge = new SlActualToCharge({
			projId : this.projectId,
			isHidden:!isGranted('_editflActualToCharge_fl'+this.projectStatus+this.bmStatus),
			businessType :this.businessType      //小贷
		});
		//业务材料  ok2  ok3
		this.SlProcreditMaterialsView = new SlEnterPriseProcreditMaterialsView({
			     projectId : this.projectId,
			     isHidden_materials:!isGranted('_editBusinessMaterials_fl'+this.projectStatus+this.bmStatus),
			     businessType:this.businessType,
			     isHiddenArchive : !isGranted('_editBusinessMaterialsArchive_fl'+this.projectStatus+this.bmStatus),
			     operationType:this.operationType
		    });
		// 尽职调查报告  ok   ok3
		this.SlReportView = new SlReportView({
		    	projectId : this.projectId,
		    	businessType : this.businessType,
		    	isHiddenColumn : false,
		    	isShowBorder : true,
		    	isHidden : false,
		    	isHidden_report:!isGranted('_editReport_fl'+this.projectStatus+this.bmStatus),
		    	isHiddenAffrim : !isGranted('_editReportAffrim_fl'+this.projectStatus+this.bmStatus)
		    });
		//合同列表  ok  ok3
		      this.slContractView = new SlContractView({
				projId : this.projectId,
				isSignHidden : false,
				isqsEdit : true,
				businessType : this.businessType,
//				HTLX : 'DBDQ',
				isHiddenAddBtn : !isGranted('_editContract_add_fl'+this.projectStatus+this.bmStatus),
				isHiddenDelBtn : !isGranted('_editContract_del_fl'+this.projectStatus+this.bmStatus),
				isHiddenEdiBtn : !isGranted('_editContract_edit_fl'+this.projectStatus+this.bmStatus),
				isqsEdit:!isGranted('_editContract_isqs_fl'+this.projectStatus+this.bmStatus),
				htType : "leaseFinanceContract",
				isHidden : true,
//				isLawCheckHidden:false, 
				isSignHidden : false
			});
			
		//办理文件 ok   ok3
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
		this.manageMaterialsView = new PawnUploads({
							projectId : this.projectId,
					    	businessType : this.businessType,
					    	tableName : 'flLeaseFinanceProjectFile',
					    	typeisfile: 'flLeaseFinanceProjectFile',
					    	isHidden:!isGranted('_editManageFiles_fl'+this.projectStatus+this.bmStatus),//上传
					    	issubmitHidden:!isGranted('_editManageFilesSubmit_fl'+this.projectStatus+this.bmStatus),//提交时间，提交备注  显示
					    	issubmitEdit:isGranted('_editManageFilesSubmit_fl'+this.projectStatus+this.bmStatus),
					    	isgdHidden:!isGranted('_editManageFilesReceive_fl'+this.projectStatus+this.bmStatus),//收到时间，收到备注 显示
					    	isgdEdit:isGranted('_editManageFilesReceive_fl'+this.projectStatus+this.bmStatus),
					    	titleName : '办理文件'
						});
		//ok3
		this.mortgageView = new DZYMortgageView({
						projectId : this.projectId,
						titleText : '抵质押担保',
						businessType : this.businessType,
						isHiddenAddContractBtn:true,
						isHiddenDelContractBtn:true,
						isHiddenEdiContractBtn:true,
						isHiddenAddBtn:!isGranted('_editCreditMaterials_add_fl'+this.projectStatus+this.bmStatus),//增
						isHiddenDelBtn:!isGranted('_editCreditMaterials_del_fl'+this.projectStatus+this.bmStatus),//删
						isHiddenEdiBtn:!isGranted('_editCreditMaterials_edit_fl'+this.projectStatus+this.bmStatus),//改  隐藏
						isHiddenRelieve:true,
						isRecieveHidden:true,
						object:this.projectInfoFinance,
						isblHidden:true,
						isgdHidden:true
					});
		//ok3
		this.baozMortgageView = new BaozMortgageView({
					projectId : this.projectId,
					titleText : '保证担保',
					businessType : this.businessType,
					isHiddenAddContractBtn:true,
					isHiddenDelContractBtn:true,
					isHiddenEdiContractBtn:true,
					isHiddenAddBtn:!isGranted('_editDZYCreditMaterials_add_fl'+this.projectStatus+this.bmStatus),//增
					isHiddenDelBtn:!isGranted('_editDZYCreditMaterials_del_fl'+this.projectStatus+this.bmStatus),//
					isHiddenEdiBtn:!isGranted('_editDZYCreditMaterials_edit_fl'+this.projectStatus+this.bmStatus),//
					isHiddenRelieve:true,
					isblHidden:true,
					isgdHidden:true,
					isRecieveHidden:true
			});
		//风险分析报告   ok 3
		this.SlRiskReportView = new SlRiskReportView({
				projectId : this.projectId,
				businessType : this.businessType,
				isHidden_riskReport : !isGranted('_editRiskReportBtns_fl'+this.projectStatus+this.bmStatus)
		    });
		//归档材料清单  ok3
		this.plArchivesMaterialsView = new PlArchivesMaterialsView({
							projectId : this.projectId,
							businessType:this.businessType,
							isHiddenAffrim : !isGranted('_editArchivesMaterials_affrim_fl'+this.projectStatus+this.bmStatus),
							isEditAffrim : isGranted('_editArchivesMaterials_affrim_fl'+this.projectStatus+this.bmStatus),
							isHiddenRecive:!isGranted('_editArchivesMaterials_recive_fl'+this.projectStatus+this.bmStatus),
							isHidden_materials:!isGranted('_editArchivesMaterials_fl'+this.projectStatus+this.bmStatus)
						}); 
		//监管计划
		this.supervisionManagePanel = new flDesignSupervisionManagePlan({
							projectId : this.projectId,
							businessType:this.businessType,
							isHidden:!isGranted('_editSuperVisionManagement_editBtn_fl'+this.projectStatus+this.bmStatus),
							isHeightNull:true
						}).gridPanel;
		//展期记录
		this.slSuperviseRecordView = new SlSuperviseRecordListView({
			projectId : this.projectId,
			projectRecord : this.record,
			businessType:this.businessType,
			isHidden: !isGranted('_editSupserviseRecord_editBtn_fl' + this.projectStatus+this.bmStatus)
		});
		//提前还款记录
		this.earlyrepaymentRecord=new LeaseFlEarlyrepaymentRecordView({
			projectId : this.projectId,
			businessType : this.record.data.businessType,
			isEditHidden: !isGranted('_editEarlyrepaymentRecord_editBtn_f1' + this.projectStatus+this.bmStatus),
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
				title : '<a name="fl_projectBaseInfo_edit' + this.projectId
						+ '"><font color="red">01</font>项目控制信息</a>',
				hidden : !isGranted('_editflProjectInfo_fl' + this.projectStatus + this.bmStatus),
				items : [this.projectInfo]
			}, {
				title : '<a name ="fl_customerInfo_edit' + this.projectId
						+ '"><font color="red">02</font>' + this.customerTitle
						+ '</a>',
				hidden : !isGranted('_editflCustomerInfo_fl' + this.projectStatus + this.bmStatus),
				items : [this.customerInfo]
			}, {
				title : '<a name="fl_leaseObject_edit' + this.projectId
						+ '"><font color="red">03</font>租赁标的物信息</a>',
				hidden : !isGranted('_editflLeaseObject_fl' + this.projectStatus + this.bmStatus),
				items : [this.LeaseObjectList]
			}, {
				title : '<a name="fl_financeInfo_edit' + this.projectId
						+ '"><font color="red">04</font>资金款项信息</a>',
				hidden : !isGranted('_editflFinanceBaseInfo_fl' + this.projectStatus + this.bmStatus)&&!isGranted('gtActualToCharge_fl' + this.projectStatus + this.bmStatus),
				bodyStyle : 'padding-top:5px',
				items : [this.projectInfoFinance,this.leaseFundIntentView, {
					xtype : 'panel',
					hidden : !isGranted('_editflActualToCharge_fl' + this.projectStatus + this.bmStatus),
					border : false,
					bodyStyle : 'margin-bottom:5px',
					html : '<br/><B><font class="x-myZW-fieldset-title"><a name="fl_actualToChargeInfo_edit'
							+ this.projectId
							+ '">【<font color="red">05</font>手续收支清单】</a></font></B><font class="x-myZW-fieldset-title">（</font>颜色预警：<font color=red>逾期款项</font>&nbsp;&nbsp<font style="line-height:20px">未结清项</font>&nbsp;&nbsp<font color=gray>已结清项</font><font class="x-myZW-fieldset-title" >）：</font>'
				}, this.slActualToCharge]
			},{
				title : '<a name="fl_mortgage_edit' + this.projectId
						+ '"><font color="red">06</font>担保措施管理</a>',
				hidden : !isGranted('_editCreditMaterials_fl' + this.projectStatus + this.bmStatus),
				items : [this.mortgageView,this.baozMortgageView]
			}, {
				title : '<a name="fl_businessFiles_edit' + this.projectId
						+ '"><font color="red">07</font>业务材料</a>',
				hidden : !isGranted('_editBusinessMaterials_fl' + this.projectStatus + this.bmStatus),
				items : [this.SlProcreditMaterialsView]
			}, {
				title : '<a name="fl_contract_edit' + this.projectId
						+ '"><font color="red">8</font>合同列表</a>',
				hidden : !isGranted('_editContract_fl' + this.projectStatus + this.bmStatus),//seeContract_fl  新增 ★★★add by gao
				items : [this.slContractView]
			},{
				title : '<a name="fl_manageFiles_edit' + this.projectId
						+ '"><font color="red">9</font>办理文件</a>',
				hidden : !isGranted('_editManageFiles_fl' + this.projectStatus + this.bmStatus),
				items : [this.manageMaterialsView]
			},{
				title : '<a name="fl_report_edit' + this.projectId
						+ '"><font color="red">10</font>调查报告</a>',
				hidden : !isGranted('_editReport_fl' + this.projectStatus + this.bmStatus),
				items : [this.SlReportView]
			},{
				title : '<a name="fl_riskReport_edit' + this.projectId
						+ '"><font color="red">11</font>风险分析报告</a>',
				hidden : !isGranted('_editReport_fl' + this.projectStatus + this.bmStatus),
				items : [this.SlRiskReportView]
			},{
				title : '<a name="fl_archivesMaterials_edit' + this.projectId
						+ '"><font color="red">12</font>归档材料清单</a>',
				hidden : !isGranted('_editArchivesMaterials_fl' + this.projectStatus + this.bmStatus),
				items : [this.plArchivesMaterialsView]
			},{
				title : '<a name="fl_supervisionManagement_edit' + this.projectId
						+ '"><font color="red">13</font>监管记录</a>',
				hidden : !isGranted('_editSuperVisionManagement_fl' + this.projectStatus + this.bmStatus),
				items : [this.supervisionManagePanel]
			},{
				title : '<a name="fl_superviseRecord_edit' + this.projectId
						+ '"><font color="red">14</font>展期记录</a>',
				hidden : !isGranted('_editSupserviseRecord_fl' + this.projectStatus + this.bmStatus),
				items : [this.slSuperviseRecordView]
			},{
				title : '<a name="fl_earlyrepaymentRecord_edit' + this.projectId
						+ '"><font color="red">15</font>提前还款记录</a>',
				hidden : !isGranted('_editEarlyrepaymentRecord_fl' + this.projectStatus + this.bmStatus),
				items : [this.earlyrepaymentRecord]
			},{
				title : '<a name="fl_aflterAccrualRecord_edit' + this.projectId
						+ '"><font color="red">16</font>利率变更记录</a>',
				hidden : !(isGranted('_seeAflterAccrualRecord_fl' + this.projectStatus + this.bmStatus)||isGranted('_editAflterAccrualRecord_fl' + this.projectStatus + this.bmStatus)),
				items : [new LeaseFlAlterAccrualRecordView({
							projectId : this.projectId,
							isEditHidden:false,
							businessType : this.record.data.businessType,
							projectStatus : this.projectStatus,
							oppositeType:this.oppositeType,
							bmStatus:this.bmStatus
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
					+ '/creditFlow/leaseFinance/project/updateFlLeaseFinanceProject.do',
			params : {
				gudongInfo : vDates,
				'fundIntentJson' : fundIntentJson,
				'slActualToChargeJson':slActualToChargeJson,
				isDeleteAllFundIntent : 0,
				flTaskId:this.taskId,
				flprojectId : this.projectId,
				operationType : operationType,
				flag : flag
			},
			method : 'post',
			scope : this,
			waitMsg : '数据正在提交，请稍后...',
			success : function(fp, action) {
				var kk = action.response.responseText;
				var obj = Ext.decode(kk);
				slActualToCharge.savereload();
				var tabs = Ext.getCmp('centerTabPanel');
				var projectGrid = tabs.getCmpByName('LeaseFinanceProjectGrid');
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