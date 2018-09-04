/**
 * @author lisl
 * @description 小额贷款项目信息
 * @extends Ext.Panel
 */

SmallLoanProjectInfo = Ext.extend(Ext.Panel, {
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		this.initUIComponents();
		SmallLoanProjectInfo.superclass.constructor.call(this, {
			autoScroll : true,
			height : document.body.clientHeight - 350,
			tbar : this.toolbar,
			border : false,
			items : []
		});
	},
	initUIComponents : function() {
		var jsArr = [
			
				__ctxPath + '/js/commonFlow/ExtUD.Ext.js',
				 __ctxPath + '/js/creditFlow/smallLoan/quickenLoan/LendForm.js',//借款需求
				 __ctxPath + '/js/creditFlow/customer/person/workcompany/workCompanyForm1.js',//个人所在公司信息
			    __ctxPath + '/js/creditFlow/customer/person/workcompany/workCompanyPrivateForm.js',//私营业主补填信息
			    __ctxPath + '/js/creditFlow/customer/person/person_branch_company.js',//个人旗下公司信息
			    __ctxPath + '/js/creditFlow/customer/person/relationPerson/RelationPersonView.js',//联系人信息
			    __ctxPath + '/js/creditFlow/finance/OwnFundIntentView.js',
			     __ctxPath+'/js/creditFlow/assuretenet/OurProcreditAssuretenetProductView.js',
			    __ctxPath+'/js/creditFlow/smallLoan/materials/OurProcreditMaterialsView.js',
			    __ctxPath + '/js/creditFlow/smallLoan/finance/BorrowerInfo.js',
			    __ctxPath + '/js/creditFlow/smallLoan/project/SlEarlyrepaymentRecordView.js',// 提前还款记录
			    __ctxPath + '/js/creditFlow/report/SlReportView.js',// 调查报告
			    __ctxPath + '/js/creditFlow/fund/project/ownFund.js',
			    __ctxPath+'/js/creditFlow/repaymentSource/RepaymentSource.js', //第一还款来源
			     __ctxPath + '/js/creditFlow/smallLoan/project/SlEarlyrepaymentRecordView.js',// 提前还款记录
			    __ctxPath + '/js/creditFlow/smallLoan/project/SlAlterAccrualRecordView.js',// 利率变更记录
			    __ctxPath + '/js/creditFlow/smallLoan/finance/SlSuperviseRecordListView.js',
			    __ctxPath + '/js/creditFlow/finance/SlActualToCharge.js',// 经办费用清单
			    __ctxPath + '/js/creditFlow/smallLoan/project/loadDataCommonCredit.js'// 加载数据JS
				]
		$ImportSimpleJs(jsArr, this.constructPanel, this);
	
	},// 初始化组件
	constructPanel : function() {
	    this.projectId = this.record.data.projectId;
		this.oppositeType = this.record.data.oppositeType;
		this.oppositeID = this.record.data.oppositeID;
		this.projectStatus = this.record.data.projectStatus;
		this.activityName = this.record.data.activityName;
		this.runId = this.record.data.runId;
		this.taskId = this.record.data.taskId;
		this.operationType = this.record.data.operationType;
		this.fundProjectId=this.record.data.id
		this.productId=this.record.data.productId
		this.businessType=this.record.data.businessType
		this.mainInfo = "";
		this.customerTitle = "客户信息";
		
		// 客户基本信息授权
	//借款需求
		this.LendForm = new LendForm({
			projectID : this.projectId,
			isAllReadOnly : true,
			isReadOnly:true
		});
			if (this.oppositeType =="person_customer") {
				//项目基本信息
				this.projectInfo =new  ExtUD.Ext.CreditLoanProjectInfoPanel({readOnly:true,product:true,isCPLX:true});
					//个人客户信息
				this.customerInfo = new ExtUD.Ext.CustomerInfoFastPanel({
					isEditPerson : true,
					isAllReadOnly:true,
					isRead:true,
					isHidden:true,
					isSpouseReadOnly: true,
					isNameReadOnly:true,
					isHiddenCustomerDetailBtn:true
				});
				customerTitle="个人客户信息";
						//个人所在公司信息
			this.workCompany = new 	workCompanyForm1({
				isAllReadOnly : true,
				isReadOnly:true
			});
			//私营业主补填信息
			this.workCompanyPrivate = new workCompanyPrivateForm({
				isAllReadOnly : true,
				isReadOnly:true
			});
			//个人旗下公司信息
			this.branch_company = new person_branch_company_v({
				legalpersonid:this.oppositeID,
				isHiddenAddBtn : true,
				isHiddenDelBtn : true,
				isHiddenEdiBtn : true,
				projectId:this.projectId
			});
			
				
		
			//联系人信息(配偶或直系亲属)   PersonRelation  VPersonRelationperson
			this.relationPersonInfo0 = new RelationPersonView({
				isHiddenAddBtn:true,
				isHiddenDelBtn:true,
				isHiddenEdiBtn:true,
				personIdValue:this.oppositeID,
				projectId:this.projectId,
				relationPersonType:575,
				flag:0
			});
			this.relationPersonInfo1 = new RelationPersonView({
				isHiddenAddBtn:true,
				isHiddenDelBtn:true,
				isHiddenEdiBtn:true,
				personIdValue:this.oppositeID,
				projectId:this.projectId,
				relationPersonType:577,
				flag:1
			});
			this.relationPersonInfo2 = new RelationPersonView({
				isHiddenAddBtn:true,
				isHiddenDelBtn:true,
				isHiddenEdiBtn:true,
				personIdValue:this.oppositeID,
				projectId:this.projectId,
				relationPersonType:576,
				flag:2
			});
			this.panel=new Ext.Panel({
				items : [this.customerInfo,{
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
				},{
					xtype : 'fieldset',
					title :'家庭联系人',
					bodyStyle : 'padding-left:0px',
					collapsible : true,
					labelAlign : 'right',
					autoHeight : true,
					items : [this.relationPersonInfo0]
				},{
					xtype : 'fieldset',
					title :'工作证明人',
					bodyStyle : 'padding-left:0px',
					collapsible : true,
					labelAlign : 'right',
					autoHeight : true,
					items : [this.relationPersonInfo1]
				},{
					xtype : 'fieldset',
					title :'紧急联系人',
					bodyStyle : 'padding-left:0px',
					collapsible : true,
					labelAlign : 'right',
					autoHeight : true,
					items : [this.relationPersonInfo2]
				}]
			})
			} else if(this.oppositeType =="company_customer"){
				//项目基本信息
				this.projectInfo =new ExtUD.Ext.McroLoanProjectInfoPanel({
					isDiligenceReadOnly : true,
					isAllReadOnly:true
				})
				customerTitle="企业客户信息"
			     this.customerInfo = new ExtUD.Ext.PeerMainInfoPanel({
					 projectId : this.projectId,
					 bussinessType:this.businessType,
					isAllReadOnly : true,
					 isEditEnterprise : false,
					 isHidden:true,
					 isNameReadOnly : true
				});
				
			}
	
	this.SlProcreditMaterialsView = new SlEnterPriseProcreditMaterialsView(
		{
			projectId : this.projectId,
			businessType : this.businessType,
			isHidden:true,
			isHiddenArchive:false,
			isarchives:false,
			isHidden_materials : true,
			operationType : "SmallLoanBusiness"
		});
		//资金款项
		this.projectInfoFinance= new ownFund({
			isAllReadOnly:true,
			isReadOnly:true,
			isHiddencalculateBtn:true,
		 	projectId:this.projectId,
		 	isStartDateReadOnly:true,
			idDefinition:'tongyongliucheng1See'+this.taskId
		});
		this.gridPanel = new OwnFundIntentView({
			projectId : this.projectId,
			preceptId:this.fundProjectId,
			object : this.projectInfoFinance,
			isHiddenTitle:true,
			isHiddenAddBtn:true,
			isHiddenDelBtn:true,
			isHiddenExcel:false,
			isHiddenResBtn1:true,
			isHiddenseeqlideBtn:true,
			isHiddenseesumqlideBtn:true,
			isHiddenautocreateBtn:true,
			isFastOr:false,
			businessType : 'SmallLoan'
		});
		this.slActualToCharge = new SlActualToCharge({
			projId : this.projectId,
			businessType :'SmallLoan',
			isHiddenTitle:true,
			isHidden:true,
			productId : this.productId//小贷
			
		});
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
			isHiddenAddContractBtn:true,
			isHiddenDelContractBtn:true,
			isHiddenEdiContractBtn:true,
			isHiddenAddBtn:true,
			isHiddenDelBtn:true,
			isHiddenEdiBtn:true,
			isHiddenRelieve:true,
			isblHidden:false,
			isblEdit:false,
			isBl:false,
			isRecieveHidden:true,
			isgdHidden:true,
			formPanel:this.LendForm
		}),
		//保证担保
		this.baozMortgageView= new BaozMortgageView({
			projectId : this.projectId,
			titleText : '保证担保',
			businessType : this.businessType,
			isHiddenAddContractBtn:true,
			isHiddenDelContractBtn:true,
			isHiddenEdiContractBtn:true,
			isHiddenAddBtn:true,
			isHiddenDelBtn:true,
			isHiddenEdiBtn:true,
			isHiddenRelieve:true,
			isRecieveHidden:true,
			isblHidden:false,
			isblEdit:false,
			isBl:false,
			formPanel:this.LendForm
		})
		this.slContractView=new SlContractView({
		    projectId:this.fundProjectId,
		    isHiddenTbar:true,
		    contractUploadHidden:true,
		    isSignHidden:true,
		    isHiddenAffrim:true,
		     isHidden:false,
		     islcEdit:true,
	    	htType : 'loanContract',
	    	HTLX : 'loanContract',
		    businessType : this.businessType,
		     sprojectId:this.projectId,
		    isqsEdit:false,
		    isHiddenTbar:false
		 });
this.repaymentSource=new RepaymentSource({
					isHidden:true,
				    projectId:this.projectId
				})
		this.borrowerInfo=new BorrowerInfo({
			projectId : this.projectId,
			isHidden : true,
			isHiddenAddBtn : true,
			isHiddenDelBtn:true
		});
		this.SlReportView = new SlReportView({
			projectId : this.projectId,
			businessType : 'SmallLoan',
	    	isHidden_report : true
		});
		this.SlEarlyrepaymentRecordView=new SlEarlyrepaymentRecordView({
				businessType : 'SmallLoan',
				projectId : this.projectId,
				fundProjectId:this.fundProjectId,
				oppositeType:this.oppositeType,
				projectStatus:this.projectStatus,
				isEditHidden:true
		})
		this.slSuperviseRecordListView=new SlSuperviseRecordListView({
			businessType : 'SmallLoan',
			projectId : this.projectId,
			fundProjectId:this.fundProjectId,
			projectRecord:this.record,
			isHidden:true
		})
		this.SlAlterAccrualRecordView=new SlAlterAccrualRecordView({
			businessType : 'SmallLoan',
			projectId : this.projectId,
			oppositeType:this.oppositeType,
			fundProjectId:this.fundProjectId,
			isEditHidden : true
		})
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
				xtype:'hidden',
				name:'slSmallloanProject.projectStatus'//添加它为了通过loadData前台赋值，保存时直接获取，否则报错 by gao
			},{
				title : '<a name="sl_projectInfo_see' + this.projectId
						+ '"><font color="red">01</font>项目基本信息</a>',
				hidden : !(isGranted('ediSlProjectInfo_' + this.projectStatus)||isGranted('seeSlProjectInfo_' + this.projectStatus)),
				items : [this.projectInfo]
			},{
				title : '<a name="sl_lendForm_see' + this.projectId
						+ '"><font color="red">02</font>借款需求</a>',
				hidden : !((isGranted('editLendForm_' + this.projectStatus)||isGranted('seeLendForm_' + this.projectStatus)) && this.oppositeType =="person_customer"),
				items : [this.LendForm]
			},{
				title : '<a name="sl_customerInfo_see' + this.projectId
						+ '"><font color="red">03</font>'+customerTitle+'</a>',
				hidden : !(isGranted('editslCustomerInfo_' + this.projectStatus)||isGranted('seeslCustomerInfo_' + this.projectStatus)),
				items : [this.oppositeType =="person_customer"?this.panel:this.customerInfo]
			},{
				title : '<a name="sl_financeInfo_see' + this.projectId
						+ '"><font color="red">04</font>资金款项信息</a>',
				hidden : !(isGranted('editslFinanceInfo_' + this.projectStatus)||isGranted('seeslFinanceInfo_' + this.projectStatus)),
				name : 'ownFinanceInfoFieldset',
				items : [this.projectInfoFinance, {
							xtype : 'panel',
							hidden :  !(isGranted('editslFundInentInfo_' + this.projectStatus)||isGranted('seeslFundInentInfo_' + this.projectStatus)),
							border : false,
							bodyStyle : 'margin-bottom:5px',
							html : '<br/><B><font class="x-myZW-fieldset-title"><a name="sl_fundIntentInfo_edit'+ this.projectId
									+ '">【<font color="red">05</font>放款收息表】</a></font></B><font class="x-myZW-fieldset-title">（</font>颜色预警：<font color=red>逾期款项</font>&nbsp;&nbsp<font style="line-height:20px">未结清项</font>&nbsp;&nbsp<font color=gray>已结清项</font><font class="x-myZW-fieldset-title" >）：</font>'
						}, this.gridPanel, {
							xtype : 'panel',
							hidden : !(isGranted('editslActualToCharge_' + this.projectStatus)||isGranted('seeslActualToCharge_' + this.projectStatus)),
							border : false,
							bodyStyle : 'margin-bottom:5px',
							html : '<br/><B><font class="x-myZW-fieldset-title"><a name="sl_actualToChargeInfo_edit'
									+ this.projectId
									+ '">【<font color="red">06</font>手续费用收支清单】</a></font></B><font class="x-myZW-fieldset-title">（</font>颜色预警：<font color=red>逾期款项</font>&nbsp;&nbsp<font style="line-height:20px">未结清项</font>&nbsp;&nbsp<font color=gray>已结清项</font><font class="x-myZW-fieldset-title" >）：</font>'
						},this.slActualToCharge]
			},{
				title : '<a name="sl_procreditAssuretenet_see' + this.projectId
						+ '"><font color="red">07</font>贷款必备条件</a>',
				hidden : !(isGranted('editAssuretenet_' + this.projectStatus)||isGranted('seeAssuretenet_' + this.projectStatus)),
				items : [this.ourProcreditAssuretenetProductView]
			},{
				title : '<a name="sl_procreditMaterials_see' + this.projectId
						+ '"><font color="red">08</font>贷款材料清单</a>',
				hidden : !(isGranted('editCreditMaterials_' + this.projectStatus)||isGranted('seeCreditMaterials_' + this.projectStatus)),
				items : [this.SlProcreditMaterialsView]
			},{
				title : '<a name="sl_mortgage_see' + this.projectId
						+ '"><font color="red">09</font>担保措施</a>',
				hidden : !(isGranted('editmortgageView_' + this.projectStatus)||isGranted('seemortgageView_' + this.projectStatus)),
				items : [this.dYMortgageViewProduct,this.baozMortgageView]
			},{
				title : '<a name="sl_loanContract_see' + this.projectId
						+ '"><font color="red">10</font>借款合同</a>',
				hidden : !(isGranted('editslLoanContract_' + this.projectStatus)||isGranted('seeslLoanContract_' + this.projectStatus)),
				items : [this.slContractView]
			},{
				title : '<a name="sl_repaymentSource_see' + this.projectId
						+ '"><font color="red">11</font>第一还款来源</a>',
				hidden : !((isGranted('editrepaymentSource_' + this.projectStatus)||isGranted('seerepaymentSource_' + this.projectStatus)) && (this.oppositeType =="company_customer")),
				items : [this.repaymentSource]
			},{
				title : '<a name="sl_borrowerInfo_see' + this.projectId
						+ '"><font color="red">12</font>共同借款人</a>',
				hidden : !((isGranted('editborrowerInfo_' + this.projectStatus)||isGranted('seeborrowerInfo_' + this.projectStatus)) && (this.oppositeType =="company_customer")),
				items : [this.borrowerInfo]
			},{
				title : '<a name="sl_report_see' + this.projectId
						+ '"><font color="red">13</font>尽职调查报告</a>',
				hidden : !((isGranted('editReport_' + this.projectStatus)||isGranted('seeReport_' + this.projectStatus)) && (this.oppositeType =="company_customer")),
				items : [this.SlReportView]
			},{
				title : '<a name="sl_superviseRecord_see' + this.projectId
						+ '"><font color="red">20</font>展期记录</a>',
				hidden : !((isGranted('records_' + this.projectStatus)||isGranted('SeeRecords_' + this.projectStatus))),
				items : [this.slSuperviseRecordListView]
			},{
				title : '<a name="sl_earlyrepaymentRecord_see' + this.projectId
						+ '"><font color="red">21</font>提前还款记录</a>',
				hidden : !((isGranted('earlyrepaymentRecord_' + this.projectStatus)||isGranted('seeearlyrepaymentRecord_' + this.projectStatus))),
				items : [this.SlEarlyrepaymentRecordView]
			},{
				title : '<a name="sl_aflterAccrualRecord_see' + this.projectId
						+ '"><font color="red">22</font>利率变更记录</a>',
				hidden : !((isGranted('aflterAccrualRecord_' + this.projectStatus)||isGranted('seeaflterAccrualRecord_' + this.projectStatus))),
				items : [this.SlAlterAccrualRecordView]
			}]
		});
		this.loadData({
		
			url : __ctxPath + '/project/getCreditProjectInfoSlSmallloanProject.do?slProjectId='+this.projectId,
			preName : ['enterprise', 'person', 'slSmallloanProject',
					'businessType',"enterpriseBank","spouse","financeInfo"],
			root : 'data',
			success : function(response, options) {
				var respText = response.responseText;
				var alarm_fields = Ext.util.JSON.decode(respText);
				if(typeof(alarm_fields.data.enterpriseBank) != 'undefined'&&this.getCmpByName('enterpriseBank.areaName')!=null) {
					this.getCmpByName('enterpriseBank.areaName').setValue(alarm_fields.data.enterpriseBank.areaName);
				}
				if(alarm_fields.data.ownBpFundProject){
					this.getCmpByName('ownBpFundProjectMoney').setValue(Ext.util.Format.number(alarm_fields.data.ownBpFundProject.ownJointMoney,'0,000.00'));
					fillFundData(this,alarm_fields,'tongyongliucheng1See'+this.taskId);
				}
			}
		});
		this.formPanel = new Ext.FormPanel();
		this.formPanel.add(this.outPanel);
		this.add(this.formPanel);
		this.doLayout();
	},// 初始化UI结束
	saveAllDatas : function() {
		var oppType = this.oppositeType;
		var vDates = "";
		var source = "";
		var borrowerInfo="";
		var oppType = this.oppositeType;
		var fundIntentJsonData = this.gridPanel.getGridDate();
		var gridPanel=this.gridPanel
		var  slActualToChargeJsonData = this.slActualToCharge.getGridDate();
		var slActualToCharge=this.slActualToCharge
		 if(this.oppositeType=="company_customer")
		        {
		           var eg=this.customerInfo.getCmpByName('gudong_store').get(0).get(1);
		           vDates=getGridDate(eg);
		           if(vDates!=""){
		              var arrStr=vDates.split("@");
					  for(var i=0;i<arrStr.length;i++){
						  var str=arrStr[i];
						  var object = Ext.util.JSON.decode(str)
						 if(object.personid==""){
							 Ext.ux.Toast.msg('操作信息','股东名称不能为空，请选择股东名称');
							 return;
						 }
						  if(object.shareholdertype==""){
								 Ext.ux.Toast.msg('操作信息','股东类别不能为空，请选择股东类别');
								 return;
							 }
					  }
				  }
				  var op=this.getCmpByName('person.id')
				  source = getSourceGridDate(this.repaymentSource.get(0));
				  var repaymentSource=this.repaymentSource
				  borrowerInfo=getBorrowerInfoData(this.borrowerInfo.get(0));
				  var borrowerInfoGrid=this.borrowerInfo.get(0)
				  var enterpriseBank=this.getCmpByName("enterpriseBank.id")
		        }else{
		        	var workCompany=this.getCmpByName('workCompany.id');
					var bpMoneyBorrowDemand=this.getCmpByName('bpMoneyBorrowDemand.borrowid');
					var personMarry=this.getCmpByName('person.marry');
					if(personMarry!=null && personMarry!="" && personMarry==317){
						var spouse=this.getCmpByName('spouse.spouseId')
					}
		        }
		this.formPanel.getForm().submit({
			clientValidation : false,
			url : __ctxPath + '/project/updateAllSlSmallloanProject.do',
			params : {
				"gudongInfo" : vDates,
				"fundIntentJsonData" : fundIntentJsonData,
				"slActualToChargeJsonData" : slActualToChargeJsonData,
				"fundProjectId":this.fundProjectId,
				"borrowerInfo" : borrowerInfo,
				"repaymentSource" : source
			},
			method : 'post',
			waitMsg : '数据正在提交，请稍后...',
			success : function(fp, action) {
				var object = Ext.util.JSON.decode(action.response.responseText)
				Ext.ux.Toast.msg('操作信息', '保存信息成功!');
				
				
				if (oppType == 'company_customer') {
					eg.getStore().reload();
					op.setValue(object.legalpersonid)
					repaymentSource.grid_RepaymentSource.getStore().reload();
					enterpriseBank.setValue(object.enterpriseBankId);
					
					borrowerInfoGrid.getStore().reload();
				}else{
					if(personMarry!=null && personMarry!="" && personMarry==317){
						spouse.setValue(object.spouseId)
					}
					workCompany.setValue(object.workCompanyId);
					bpMoneyBorrowDemand.setValue(object.borrowid);
				}
				
				gridPanel.save();
				slActualToCharge.savereload();
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