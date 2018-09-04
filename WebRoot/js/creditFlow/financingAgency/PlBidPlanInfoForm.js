PlBidPlanInfoForm = Ext.extend(Ext.Panel, {

	constructor : function(_cfg) {
		if (_cfg == null) {
			_cfg = {};
		}
		Ext.applyIf(this, _cfg);
		this.initComponents();
		PlBidPlanInfoForm.superclass.constructor.call(this, {
			id : 'PlBidPlanInfoForm_'+this.bidPlanId,
			title : '【'+this.bidProName+'】详情',
			border : false
		});
	},
	initComponents : function() {
			var jsArr = [
				__ctxPath + '/js/commonFlow/ExtUD.Ext.js',//客户信息 项目基本信息
				__ctxPath + '/js/ah_ext/factory/ahinput.js',//客户信息 项目基本信息
			    __ctxPath + '/js/creditFlow/smallLoan/quickenLoan/LendForm.js',//借款需求
		    	__ctxPath + '/js/creditFlow/fund/project/ownFund.js',
		    	__ctxPath + '/js/creditFlow/fund/project/platFormFund.js',
		    	__ctxPath + '/js/creditFlow/finance/BpFundIntentFapView.js',
		    	__ctxPath + '/js/creditFlow/finance/CusterFundIntentView.js',
		    	__ctxPath + '/js/customer/CustomeLinkmanGridPanel.js',
		    	__ctxPath + '/js/customer/InvestPersonWindowObjList.js',
		    	__ctxPath + '/js/customer/InvestEnterpriseView.js',
		    	__ctxPath + '/js/creditFlow/finance/SlActualToCharge.js',
		    	__ctxPath + '/js/customer/InvestEnterpriseForm.js',
		    	__ctxPath + '/js/customer/InvestPersonInfoPanelView.js',
		    	__ctxPath + '/js/creditFlow/fund/project/ownFund.js',
		    	__ctxPath + '/js/creditFlow/smallLoan/contract/SlContractView.js',//合同
		    	__ctxPath + '/js/creditFlow/smallLoan/project/loadDataCommonCredit.js',// 加载数据JS
		    	__ctxPath + '/js/creditFlow/finance/ptp/InverstPersonBpFundIntent.js',
	    		__ctxPath + '/js/creditFlow/financingAgency/PlBidPlanBaseInfoForm.js',
	    		__ctxPath +'/js/creditFlow/finance/InterestFundIntentView.js'
		];
		$ImportSimpleJs(jsArr, this.constructPanel, this);
	},
	constructPanel : function() {
		
		if(this.proType=='B_Dir'){
			this.htType1='C2PTZRHT';
			this.htType2='C2PJKRHT';
		}else if(this.proType=='P_Dir'){
			this.htType1='P2PTZRHT';
			this.htType2='P2PJKRHT';
		}else if(this.proType=='P_Or'){
			this.htType1='PA2PTZRZQHT';
			this.htType2='PA2PJKRZQHT';
		}else if(this.proType=='B_Or'){
			this.htType1='CA2PTZRZQHT';
			this.htType2='CA2PJKRZQHT';
		}
		
		
		if(this.subType=='onlineopen' && this.proType=='P_Dir'){
			this.htType1='CREDITTZRHT';
			this.htType2='CREDITJKRHT';
		}

		this.perMain = "";
		var title="企业客户信息";
		
		if (this.oppositeType =="person_customer") {
			 this.projectInfo = new ExtUD.Ext.PerCreditLoanProjectInfoPanel({readOnly:true,product:true,isCPLX:true});
			this.perMain =new ExtUD.Ext.CustomerInfoFastPanel({
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
		/*	this.projectInfo = new ProjectInfoPanel({
			isDiligenceReadOnly : true,
			isAllReadOnly:true
		});*/
			this.projectInfo = new ExtUD.Ext.McroLoanProjectInfoPanel({
			isDiligenceReadOnly : true,
			isAllReadOnly:true,
			isOnlineApply:true
		});
			     this.perMain = new ExtUD.Ext.PeerMainInfoPanel({
				 projectId : this.projectId,
				 bussinessType:this.businessType,
				 isAllReadOnly : true,
				 isReadOnly:true,
				 isNameReadOnly:true,
				 isHidden : false,
				 isEditEnterprise : false
			});
		}
		this.plBidPlanBaseInfoForm=new PlBidPlanBaseInfoForm({
			bidId : this.bidPlanId
		});

		if(this.proType=='P_Dir' || this.proType=='B_Dir'){
			//平台资金款项
			this.projectInfoFinance= new platFormFund({
				isAllReadOnly:true,
				isReadOnly:true,
				isHiddencalculateBtn:true,
				isStartDateReadOnly:true,
			 	projectId:this.projectId,
				idDefinition:'tongyongliucheng2'+this.taskId
			});
		}else{
			this.projectInfoFinance= new ownFund({
				isAllReadOnly:true,
				isReadOnly:true,
				isHiddencalculateBtn:true,
				isStartDateReadOnly:true,
			 	projectId:this.projectId,
				idDefinition:'tongyongliucheng1'+this.taskId
			});
		}
		
		
     //投资人信息
		this.investPersonInfoPanelView = new InvestPersonInfoPanelView({
			fundProjectId:this.fundProjectId,
			projectId : this.projectId,
			bidPlanId:this.bidPlanId,
			isHidden : true,
			isHiddenHT : true,
			bussinessType : this.businessType,
			object:this.projectInfoFinance,
			isFlow:false,
			htType : this.htType1
		});
	    //投资人奖励台账
		this.InterestFundIntentView = new InterestFundIntentView({
			bidPlanId:this.bidPlanId,
			preceptId:this.fundProjectId,
			projectId :this.projectId,
			object : this.projectInfoFinance,
			bidPlanFinanceInfo:this.BidPlanFinanceInfo,
			businessType : this.businessType,
			isHaveLending:'yes',
			isHiddenAddBtn : true,// 生成
			isHiddenDelBtn : false,// 删除
			isHiddenCanBtn : true,// 取消
			isHiddenResBtn : true,// 还原
			isHiddenResBtn1 : true,// 手动对账
			isHiddenseeqlideBtn : true,// 查看流水单项订单
			isHiddenseesumqlideBtn : true,
			isHiddenautocreateBtn : false,
			InterestFundIntentView : this.InterestFundIntentView
		});
	//合同
		debugger
//	this.slContractView=new SlContractView({
//		 projectId:this.fundProjectId,
//		    bidPlanId:this.bidPlanId,
//		    isHiddenAddBtn : false,
//		    isHiddenDelBtn : false,
//		    isHiddenEdiBtn : false,
//		    isHiddenBZ:true,
//		    isHiddenDZY:true,
//		    isSignHidden:true,
//		    isHiddenAffrim:true,
//		    contractUploadHidden:true,
//		    isHidden:true,
//	    	htType : this.htType2,
//	    	HTLX : this.htType2,
//		    businessType : this.businessType,
//		    isqsEdit:false,
//		    isHiddenTbar:true,
//		    type:'bidplan',
//		    isChange:true
//		 });
		 
		 //合同
	this.slContractView=new SlContractView({
		 projectId:this.fundProjectId,
		    bidPlanId:this.bidPlanId,
		    isHiddenAddBtn : true,
		    isHiddenDelBtn : true,
		    isHiddenEdiBtn : true,
		    isHiddenBZ:true,
		    isHiddenDZY:true,
		    isSignHidden:true,
		    isHiddenAffrim:true,
		    contractUploadHidden:true,
		    isHidden:true,
		    isHiddenToFtp : true,
	    	htType :this.htType2,
	    	HTLX : this.htType2,
		    businessType : 'InternetFinance',
		    isqsEdit:false,
		    isChange:true,//@HT_version3.0除小贷业务，其他业务的借款合同读取的都是p2p要素，生成方式也要和投资人合同相同
		    isHiddenTbar:false,
		    type:'bidplan'
		 });
		
		//财务服务费
		this.actualToChange = new SlActualToCharge({
				projId : this.projectId,
				bidPlanId:this.bidPlanId,
				businessType : 'SmallLoan',// 小贷
				editor:true,
				serviceHidden:true,
				isHidden : true
		});
		this.BidPlanFinanceInfo=new ExtUD.Ext.BidPlanFinanceInfo({
			objectInfo : this.projectInfoFinance,
			proType:this.proType,
			readOnly : true
		})
		//放款收息表，平台
		/*this.gridPanel2 = new BpFundIntentFapView({
			projectId : this.projectId,
			object : this.projectInfoFinance,
			bidPlanFinanceInfo:this.BidPlanFinanceInfo,
			isHidden : true,
			isHiddenAdd:true,
			isHiddenDel:true,
			isHiddenAutoCreate:true,
			hiddenCheck:false,
			calcutype : 1 ,    
			isHiddenExcel:true,//贷款
			isHiddenseeqlideBtn:true,
			inverstPersonId:null,
			isHiddenTitle:true,
			isOwnBpFundProject:((this.proType=='P_Dir' || this.proType=='B_Dir')?false:true),
			businessType : this.businessType,
			preceptId:this.fundProjectId,
			bidPlanId:this.bidPlanId,
			fundResource:1,
			isFlow:false
		});*/
		this.SlFundIntentViewVM = new CusterFundIntentView({
			projectId :this.projectId,
			bidPlanId:this.bidPlanId,
			object : this.projectInfoFinance,
			bidPlanFinanceInfo:this.BidPlanFinanceInfo,
			businessType : this.businessType,
			isHiddenautocreateBtn:true,
			isHiddenExcel:true,
			isFactHidden:false,
			isHaveLending:'yes',
			isHiddenAddBtn : true,// 生成
			isHiddenDelBtn : true,// 删除
			isHiddenCanBtn : true,// 取消
			isHiddenResBtn : true,// 还原
			isHiddenResBtn1 : true,// 手动对账
			isHiddenseeqlideBtn : true,// 查看流水单项订单
			isHiddenseesumqlideBtn : true,
			isHidden1:true
		});
		this.creditBaseInfo=new ExtUD.Ext.CreditBaseInfo({
			isAllReadOnly : true
		})
		this.formPanel = new Ext.Panel({
			modal : true,
			labelWidth : 100,
			buttonAlign : 'center',
			layout : 'form',
			border : false,
			frame :true,
			defaults : {
				anchor : '100%',
				labelAlign : 'left',
//				collapsible : true,
				autoHeight : true
			},
			items : [{
				xtype : 'fieldset',
				name:'projectInfo',
				title : '项目基本信息 ',
				collapsible : true,
				autoHeight : true,
				labelAlign : 'right',
				items : [this.projectInfo]
			},{
				xtype : 'fieldset',
				title : '招标基本信息 ',
				collapsible : true,
				autoHeight : true,
				labelAlign : 'right',
				items : [this.plBidPlanBaseInfoForm]
			}, {
				xtype : 'fieldset',
				title :((this.proType=='B_Dir' || this.proType=='P_Dir')?title:'原始借款人信息'),
				bodyStyle : 'padding-left:0px',
				collapsible : true,
				labelAlign : 'right',
				autoHeight : true,
				items : [this.perMain]
			},{
				xtype : 'fieldset',
				title :'原始债权人信息',
				bodyStyle : 'padding-left:0px',
				collapsible : true,
				labelAlign : 'right',
				autoHeight : true,
				hidden : ((this.proType=='B_Dir' || this.proType=='P_Dir')?true:false),
				items : [this.creditBaseInfo]
			}, {
				xtype : 'fieldset',
				title :'资金款项信息',
				name:((this.proType=='B_Dir' || this.proType=='P_Dir')?'platFormfinanceInfoFieldset':'ownFinanceInfoFieldset'),
				bodyStyle : 'padding-left:0px',
				collapsible : true,
				labelAlign : 'right',
				autoHeight : true,
				items : [this.projectInfoFinance]
			},{
				xtype : 'fieldset',
				title :'本次招标信息',
				bodyStyle : 'padding-left:0px',
				collapsible : true,
				labelAlign : 'right',
				autoHeight : true,
				items : [this.BidPlanFinanceInfo,this.investPersonInfoPanelView,this.actualToChange/*,this.gridPanel2*/,this.SlFundIntentViewVM,this.InterestFundIntentView]
			}, {
				xtype : 'fieldset',
				collapsible : true,
				autoHeight : true,
				title : '贷款合同',
				items : [this.slContractView]
			}]
		})
		this.loadData({
			url : __ctxPath + '/project/getCreditLoanProjectInfoSlSmallloanProject.do?slProjectId='+this.projectId+'&bidPlanId='+this.bidPlanId,
			method : "POST",
			preName : ['person', 'slSmallloanProject','bpProductParameter','platFormBpFundProject','ownBpFundProject','slAlterAccrualRecord','enterprise', "enterpriseBank","spouse","payintentPeriod","plBidPlan"],
			root : 'data',
			success : function(response, options) {
				var respText = response.responseText;
				var alarm_fields = Ext.util.JSON.decode(respText);
			
				if(alarm_fields.data.platFormBpFundProject){
					if(this.getCmpByName('platFormBpFundProjectMoney')){
						this.getCmpByName('platFormBpFundProjectMoney').setValue(Ext.util.Format.number(alarm_fields.data.platFormBpFundProject.projectMoney,'0,000.00'));
						fillFundData(this,alarm_fields,'tongyongliucheng2'+this.taskId);
					}
				}
				if(alarm_fields.data.ownBpFundProject){
					if(this.getCmpByName('ownBpFundProjectMoney')){
						this.getCmpByName('ownBpFundProjectMoney').setValue(Ext.util.Format.number(alarm_fields.data.ownBpFundProject.projectMoney,'0,000.00'));
						this.getCmpByName('plBidPlan.endIntentDate').setValue(alarm_fields.data.ownBpFundProject.intentDate)
						fillFundData(this,alarm_fields,'tongyongliucheng1'+this.taskId);
					}
				}
				if(typeof(alarm_fields.data.enterpriseBank)!="undefined"){
					this.getCmpByName('enterpriseBank.areaName').setValue(alarm_fields.data.enterpriseBank.areaName)
				}
				
				if(this.bidPlanId!=null&&""!=this.bidPlanId&&typeof(this.bidPlanId)!="undefined"){
					this.investPersonInfoPanelView.grid_sharteequity.getStore().load({
						params :{Q_bidPlanId_L_EQ:this.bidPlanId}
					});
				}else{
					this.investPersonInfoPanelView.grid_sharteequity.getStore().load({
						params :{Q_moneyPlanId_L_EQ:alarm_fields.data.platFormBpFundProject.id}
					});
				}
				
				if(alarm_fields.data.plBidPlan.rebateType==1){
				  	this.getCmpByName("check_e").setValue(true);
				}else if(alarm_fields.data.plBidPlan.rebateType==2){
				  	this.getCmpByName("check_f").setValue(true);
				}else if(alarm_fields.data.plBidPlan.rebateType==3){
				  	this.getCmpByName("check_g").setValue(true);
				}else if(alarm_fields.data.plBidPlan.rebateType==4){
				  	this.getCmpByName("check_h").setValue(true);
				}
				
				if(alarm_fields.data.plBidPlan.rebateWay==1){
				 	this.getCmpByName("check_ee").setValue(true);
				}else if(alarm_fields.data.plBidPlan.rebateWay==2){
				 	this.getCmpByName("check_ff").setValue(true);
				}else if(alarm_fields.data.plBidPlan.rebateWay==3){
				 	this.getCmpByName("check_ii").setValue(true);
				}
			}
		});
		this.add(this.formPanel);
		this.doLayout();
	}
	
})
