/**
 * @description 小额贷款展期项目信息
 * @extends Ext.Panel
 */

flPostponedProjectInfoEdit = Ext.extend(Ext.Panel, {
	bmStatus:0,
	constructor : function(_cfg) {
		if (_cfg.record) {
			this.record = _cfg.record;
		}
		if (_cfg.projectRecord) {
			this.projectRecord = _cfg.projectRecord;
		}
		if (_cfg.projectStatus) {
			this.projectStatus = _cfg.projectStatus;
		}
		Ext.applyIf(this, _cfg);
		this.initUIComponents();
		flPostponedProjectInfoEdit.superclass.constructor.call(this, {
			autoScroll : true,
			height : document.body.clientHeight - 210,
			tbar : !(isGranted('_hiddenZQSaveTbar_' + this.projectStatus))?null:this.toolbar,
			border : false,
			items : []
		});
	},
	initUIComponents : function() {
		var jsArr = [
				__ctxPath + '/js/commonFlow/ExtUD.Ext.js',
				__ctxPath + '/js/creditFlow/smallLoan/finance/SuperviseFinancePanel.js',
				__ctxPath + '/js/creditFlow/smallLoan/finance/SuperviseFundIntent.js',
				__ctxPath + '/js/creditFlow/smallLoan/finance/SuperviseIntentGrid.js',
				__ctxPath + '/js/creditFlow/smallLoan/finance/SuperviseLoadDataCommon.js',
				__ctxPath + '/js/creditFlow/finance/SlFundIntentViewVM.js',
				__ctxPath + '/js/creditFlow/finance/SlActualToCharge.js',
				__ctxPath + '/js/creditFlow/finance/detailView.js',
				__ctxPath + '/js/creditFlow/finance/superviseSlFundIntentVM.js',
				__ctxPath + '/js/creditFlow/finance/detailView.js',
				__ctxPath + '/js/creditFlow/smallLoan/finance/FinanceExtensionlPanel.js',
				__ctxPath + '/js/creditFlow/smallLoan/finance/loadDataExtension.js',
				/*租赁标的清单start*/
				__ctxPath + '/js/creditFlow/leaseFinance/leaseobject/LeaseObjectList.js',//租赁标的清单
				__ctxPath + '/js/creditFlow/leaseFinance/leaseobject/LeaseObjectInsuranceInfo.js',//租赁物保险信息
				__ctxPath + '/js/creditFlow/leaseFinance/leaseobject/AddLeaseObjectWin.js',//租赁标的增加
				__ctxPath + '/js/creditFlow/leaseFinance/leaseobject/LeaseFinanceSuppliorInfo.js',//供货方信息   必须要加载  在win中延迟加载，第一次访问不了  add  by gao
				__ctxPath + '/js/creditFlow/leaseFinance/leaseobject/selectSupplior.js',//供货方信息  弹窗
				__ctxPath + '/js/creditFlow/leaseFinance/leaseobject/LeaseFinanceUploads.js',//供货方信息  弹窗
				/*租赁标的清单end*/
				__ctxPath + '/js/creditFlow/leaseFinance/LeaseFinanceAddDzyMortgageWin.js' ,
				__ctxPath + '/js/creditFlow/leaseFinance/LeaseFinanceMaterialsForm.js',//抵质押担保材料上传表单
				__ctxPath + '/js/creditFlow/leaseFinance/LeaseFinanceMaterialsView.js',//抵质押担保上传
				__ctxPath + '/js/creditFlow/leaseFinance/enterpriseBusiness/EnterpriseBusinessUI.js'//项目基本信息
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
		this.projectId = this.projectRecord.data.projectId;
		this.oppositeType = this.projectRecord.data.oppositeType;
		this.projectStatus = this.projectRecord.data.projectStatus;
		this.activityName = this.projectRecord.data.activityName;
		this.runId = this.projectRecord.data.runId;
		this.businessType = this.projectRecord.data.businessType;
		this.operationType = this.projectRecord.data.operationType;
		this.projectMoney = this.projectRecord.data.projectMoney;
		this.payProjectMoney = this.projectRecord.data.payProjectMoney;
		this.id=this.record.data.id;
		this.postponedCustomerTitle = "客户信息";
		if (this.oppositeType =="person_customer") {
			this.postponedCustomerInfo = new ExtUD.Ext.PeerPersonMainInfoPanel({
				isEditPerson : false,
				 isReadOnly : true,
				 isNameReadOnly : true,
				 isHidden : true,
				 isHideGudongInfo : true
			});
			postponedCustomerTitle="个人客户信息";
		} else if(this.oppositeType =="company_customer"){
			postponedCustomerTitle="企业客户信息"
		     this.postponedCustomerInfo = new ExtUD.Ext.PeerMainInfoPanel({
				 projectId : this.projectId,
				 bussinessType:this.businessType,
				 isEditEnterprise : false,
				  isReadOnly : true,
				  isHidden : true,
				  isHideGudongInfo : true,
				  isNameReadOnly : true
			});
		}
/*		// 项目基本信息授权
		if (isGranted('ediSlProjectInfo_' + this.projectStatus)) {
			this.projectInfoGranted = {
				isDiligenceReadOnly : true,
				isAllReadOnly : true
			};
		} else {
			this.projectInfoGranted = {
				isAllReadOnly : true
			};
		}*/
		this.postponedProjectInfo = new EnterpriseBusinessProjectInfoPanel({
			   isDiligenceReadOnly : true,
			   isAllReadOnly:true,
			   isMineNameEditable:false
		});
		// 客户基本信息授权
/*	if (isGranted('editslCustomerInfo_' + this.projectStatus)) {
		if(this.operationType=='MicroLoanBusiness'){
			this.postponedCustomerInfo = new ExtUD.Ext.CustomerInfoPanel({
					isAllReadOnly : false,
					isReadOnly : false,
					isHidden : true
			});	
			this.postponedCustomerTitle = "客户信息";
			//this.isHidden=false;
		}else{
			if (this.oppositeType =="person_customer") {
				this.postponedCustomerInfo = new ExtUD.Ext.PeerPersonMainInfoPanel({
					isAllReadOnly : false,
					isEditPerson : true,
					isNameReadOnly:true,
					isHidden : true,
					isHideGudongInfo : true
				});
				postponedCustomerTitle="个人客户信息";
			} else if(this.oppositeType =="company_customer"){
				postponedCustomerTitle="企业客户信息"
			     this.postponedCustomerInfo = new ExtUD.Ext.PeerMainInfoPanel({
					 projectId : this.projectId,
					 bussinessType:this.businessType,
					isAllReadOnly : true,
					 isEditEnterprise : false,
					 isNameReadOnly : true,
					 isHidden : false,
					isHideGudongInfo : true
				});
			}
			//this.isHidden=true;
		}
	}else{
		if(this.operationType=='MicroLoanBusiness'){
			this.postponedCustomerInfo = new ExtUD.Ext.CustomerInfoPanel({
					isAllReadOnly : true,
					isReadOnly : true,
					isHidden : true
			});	
			this.postponedCustomerTitle = "客户信息";
			//this.isHidden=false;
		}else{
			if (this.oppositeType =="person_customer") {
				this.postponedCustomerInfo = new ExtUD.Ext.PeerPersonMainInfoPanel({
					isEditPerson : false,
					 isReadOnly : true,
					 isNameReadOnly : true,
					 isHidden : false,
					 isHideGudongInfo : true
				});
				postponedCustomerTitle="个人客户信息";
			} else if(this.oppositeType =="company_customer"){
				postponedCustomerTitle="企业客户信息"
			     this.postponedCustomerInfo = new ExtUD.Ext.PeerMainInfoPanel({
					 projectId : this.projectId,
					 bussinessType:this.businessType,
					 isEditEnterprise : false,
					  isReadOnly : true,
					  isHidden : false,
					  isHideGudongInfo : true,
					  isNameReadOnly : true
				});
			}
			//this.isHidden=true;
		}*/
	//}
	
		//展期信息
		this.deferApplyInfoPanel =new FinanceExtensionlPanel({
		  	continuationMoney:(this.projectMoney-this.payProjectMoney),
		  	projectId :this.id,
		  	businessType:this.businessType,
		  	idDefinition:'extenstion_edit'+this.id,
		  	isAllReadOnly : false
		});
		
		//放款收息表
		this.slFundIntentView = new SlFundIntentViewVM({
        	isHiddenOperation : false,
			projectId : this.projectId,
			businessType : this.businessType,
			isHiddenAddBtn : true,
		    isHiddenDelBtn : true,
		    isHiddenCanBtn : true,
		    isHiddenResBtn : true,
		    isHiddenautocreateBtn:true,
			isHiddenResBtn1:true,
			isFinbtn:true,
			isHidden1:true,
		    slSuperviseRecordId : this.record.data.id,
		    isUnLoadData :false,
			isThisSuperviseRecord :'no'
		});
		this.superviseSlFundIntentVM=new superviseSlFundIntentVM({
			object :this.deferApplyInfoPanel,
			projId : this.projectId,
			isHidden : false,
			titleText:'还款收息表',
			businessType : this.businessType,
		    slSuperviseRecordId : this.record.data.id,
			isUnLoadData :false,
			isThisSuperviseRecord :'yes',
			isHiddenAdd:false,
			isHiddenDel:false
	  	})
		this.slActualToCharge = new SlActualToChargeVM({
			projId : this.projectId,
			isThisSuperviseRecord : "yes",
			slSuperviseRecordId : this.id,
			isHiddenAddBtn : false,
			isHiddenDelBtn : false,
			businessType :this.businessType     //小贷
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
				title : '项目基本信息',
				//hidden : !(isGranted('ediSlProjectInfo_' + this.projectStatus)||isGranted('seeSlProjectInfo_' + this.projectStatus)),
				items : [this.postponedProjectInfo]
			}, {
				title : this.postponedCustomerTitle,
				//hidden : !(isGranted('editslCustomerInfo_' + this.projectStatus)||isGranted('seeslCustomerInfo_' + this.projectStatus)),
				items : [this.postponedCustomerInfo]
			},{
				title : '历史资金款项信息',
				//hidden : !(isGranted('slSpouseInfo_' + this.projectStatus)||isGranted('seeslSpouseInfo_' + this.projectStatus)),
				items : [this.slFundIntentView]
			},{
				title : '展期登记信息',
				//hidden : !(isGranted('slSpouseInfo_' + this.projectStatus)||isGranted('seeslSpouseInfo_' + this.projectStatus)),
				//name : 'spousePanel',
				items : [this.deferApplyInfoPanel,this.superviseSlFundIntentVM,this.slActualToCharge]
			}/*,{
				title : '当前资金款项信息',
				//hidden : !(isGranted('slSpouseInfo_' + this.projectStatus)||isGranted('seeslSpouseInfo_' + this.projectStatus)),
				items : [this.superviseSlFundIntentVM]
			}*/,{
				title : '<a name="sl_applyForPostponed_edit' + this.projectId
						+ '"><font color="red">01</font>展期申请书</a>',
				//hidden : !(isGranted('slSpouseInfo_' + this.projectStatus)||isGranted('seeslSpouseInfo_' + this.projectStatus)),
				items : [new uploads({
			    	projectId : this.projectId,
			    	isHidden : !(isGranted('_hiddenZQsqsTbar_fl' + this.projectStatus)||isGranted('seehiddenZQsqsTbar_' + this.projectStatus)),
			    	businessType : this.businessType,
			    	isNotOnlyFile : false,
			    	isHiddenColumn : false,
			    	isDisabledButton : false,
			    	setname :'展期申请书',
			    	titleName :'展期申请书',
			    	tableName:'typeiszhanqiws',
			    	typeisfile :'typeiszhanqiws',
			    	isHiddenOnlineButton :false,
			    	isDisabledOnlineButton :false
			    })]
			},{
				title : '<a name="sl_postponedContract_edit' + this.projectId
						+ '"><font color="red">02</font>展期协议书</a>',
				//hidden : !(isGranted('slSpouseInfo_' + this.projectStatus)||isGranted('seeslSpouseInfo_' + this.projectStatus)),
				items : [new SlContractView({
					projectId :this.projectId,
	      			businessType:this.businessType,
			    	htType : 'leaseFinanceZQHT',
			    	HTLX : 'leaseFinanceZQHT',
			    	isHiddenAddBtn : false,
					isHiddenEdiBtn : false,
					isHiddenDelBtn : false,
					isHidden:false,
					isSignHidden : false,
					isqsEdit : true
				})]
			},{
				xtype : 'hidden',
				name : 'fundIntentJsonData'
			}]
		});
	
		this.loadData({
			url : __ctxPath + '/supervise/getFlInfoSlSuperviseRecord.do?projectId='+this.projectId+'&slSuperviseRecordId='+this.id,
			method : "POST",
			preName : ['enterprise', 'person', 'flLeaseFinanceProject','slSuperviseRecord','comments'],
			root : 'data',
			success : function(response, options) {
				var respText = response.responseText;
				var alarm_fields = Ext.util.JSON.decode(respText);
				if(null!=alarm_fields.data.slSuperviseRecord.dateMode){
					var dateModelCom=this.getCmpByName('slSuperviseRecord.dateMode');
					var st=dateModelCom.getStore();
					st.on('load',function(){
						dateModelCom.setValue(alarm_fields.data.slSuperviseRecord.dateMode)
					})
				}
				if(null!=alarm_fields.data.slSuperviseRecord.continuationMoney){
					this.getCmpByName('projectMoney1').setValue(alarm_fields.data.slSuperviseRecord.continuationMoney)
				}else{
					this.getCmpByName('projectMoney1').setValue(this.projectMoney-this.payProjectMoney)
					this.getCmpByName('slSuperviseRecord.continuationMoney').setValue(this.projectMoney-this.payProjectMoney)
				}
				if(this.ownerCt.ownerCt.getCmpByName('comments')){
					this.ownerCt.ownerCt.getCmpByName('comments').setValue(alarm_fields.data.comments);
				}
				fillDataExtension(this.deferApplyInfoPanel,alarm_fields,'extenstion_edit'+this.id)
			}
		});
		this.formPanel = new Ext.FormPanel();
		this.formPanel.add(this.outPanel);
		this.add(this.formPanel);
		this.doLayout();
	},// 初始化UI结束
	saveAllDatas : function() {
		var slActualToChargeJsonData=this.slActualToCharge.getGridDate();
		var superviseData=this.superviseSlFundIntentVM.getGridDate();
		var slActualToCharge=this.slActualToCharge;
		this.formPanel.getForm().submit({
		    clientValidation: false, 
			url : __ctxPath + '/supervise/updateFlInfoSlSuperviseRecord.do',
			params : {
				"slActualToChargeJsonData":slActualToChargeJsonData,
				'slSuperviseRecordId':this.id,
				fundIntentJsonData:superviseData,
				'projectId':this.projectId
			},
			method : 'post',
			waitMsg : '数据正在提交，请稍后...',
			scope: this,
			success : function(fp, action) {
				var object = Ext.util.JSON.decode(action.response.responseText);
				Ext.ux.Toast.msg('操作信息', '保存信息成功!');
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