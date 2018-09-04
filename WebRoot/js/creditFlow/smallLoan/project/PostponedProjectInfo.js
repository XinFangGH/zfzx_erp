/**
 * @description 小额贷款展期项目信息
 * @extends Ext.Panel
 */

PostponedProjectInfo = Ext.extend(Ext.Panel, {
	constructor : function(_cfg) {
		if (_cfg.record) {
			this.record = _cfg.record;
		}
		if (_cfg.projectRecord) {
			this.projectRecord = _cfg.projectRecord;
		}
		Ext.applyIf(this, _cfg);
		this.initUIComponents();
		PostponedProjectInfo.superclass.constructor.call(this, {
			autoScroll : true,
			height : document.body.clientHeight - 210,
			//tbar : !(isGranted('_hiddenZQSaveTbar_' + this.projectStatus))?null:this.toolbar,
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
				__ctxPath + '/js/creditFlow/finance/superviseSlFundIntentVM.js',
				__ctxPath + '/js/creditFlow/finance/SlActualToChargeVM.js',// 经办费用清单
				__ctxPath + '/js/creditFlow/finance/detailView.js',
				__ctxPath + '/js/creditFlow/smallLoan/finance/FinanceExtensionlPanel.js',
				__ctxPath + '/js/creditFlow/smallLoan/finance/loadDataExtension.js',
				__ctxPath + '/js/creditFlow/smallLoan/contract/SlContractView.js'// 合同确认
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
		this.fundProjectId=this.projectRecord.data.id
		this.oppositeType = this.projectRecord.data.oppositeType;
		this.projectStatus = this.projectRecord.data.projectStatus;
		this.activityName = this.projectRecord.data.activityName;
		this.runId = this.projectRecord.data.runId;
		this.businessType = this.projectRecord.data.businessType;
		this.operationType = this.projectRecord.data.operationType;
		this.id=this.record.data.id;
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
				 bussinessType:this.businessType,
				 isEditEnterprise : false,
				 isReadOnly:true,
				 isNameReadOnly:true
			});
		}


	
		//展期信息
		this.deferApplyInfoPanel =new FinanceExtensionlPanel({
		  	projectId :this.id,
		  	businessType:this.businessType,
		  	isAllReadOnly : true,
		  	idDefinition:'extenstion_see'+this.id
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
			object :this.DeferApplyInfoPanel,
			projId : this.projectId,
			preceptId:this.fundProjectId,
			isHidden : false,
			titleText:'还款收息表',
			businessType : this.businessType,
		    slSuperviseRecordId : this.id,
			isUnLoadData :false,
			isThisSuperviseRecord :'yes',
			isHiddenAuto:true,
			isHiddenAdd:true,
			isHiddenDel:true,
			isHiddenCre : true
	  	});
		this.slActualToCharge = new SlActualToChargeVM({
			projId : this.projectId,
			isThisSuperviseRecord : "yes",
			slSuperviseRecordId : this.id,
			businessType :'SmallLoan'      //小贷
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
				items : [this.projectInfo]
			}, {
				title : title,
				items : [this.perMain]
			},{
				title : '历史资金款项信息',
				items : [this.slFundIntentView]
			},{
				title : '展期登记信息',
				name : 'spousePanel',
				items : [this.deferApplyInfoPanel,this.superviseSlFundIntentVM,this.slActualToCharge]
			},{
				title : '<a name="sl_applyForPostponed_see' + this.projectId
						+ '"><font color="red">01</font>展期申请书</a>',
				items : [new uploads({
			    	projectId : this.projectId,
			    	isHidden : true,
			    	businessType : this.businessType,
			    	isNotOnlyFile : false,
			    	isHiddenColumn : false,
			    	isDisabledButton : false,
			    	setname :'展期申请书',
			    	titleName :'展期申请书',
			    	tableName:'typeiszhanqiws'+this.id,
			    	typeisfile :'typeiszhanqiws'+this.id,
			    	isHiddenOnlineButton :false,
			    	isDisabledOnlineButton :false
			    })]
			},{
				title : '<a name="sl_postponedContract_see' + this.projectId
						+ '"><font color="red">02</font>展期协议书</a>',
				items : [new SlContractView({
					projectId :this.fundProjectId,
	      			businessType:this.businessType,
			    	htType : 'extenstionContract',
			    	HTLX : 'XEDZZQ',
			    	isApply:true,
			    	isHiddenAddBtn : false,
					isHiddenEdiBtn : false,
					isHiddenDelBtn : false,
					isHidden:false,
					isHiddenTbar : true
				})]
			}]
		});
		this.loadData({
			url : __ctxPath + '/supervise/getCreditLoanProjectInfoSlSuperviseRecord.do?projectId='
					+ this.projectId+'&slSuperviseRecordId='+this.record.data.id,
			preName : ['enterprise', 'person', 'slSmallloanProject',
					'businessType'],
			root : 'data',
			success : function(response, options) {
					var respText = response.responseText;
				var alarm_fields = Ext.util.JSON.decode(respText);
				if(null!=alarm_fields.data.slSuperviseRecord.continuationMoney){
					this.getCmpByName('projectMoney1').setValue(Ext.util.Format.number(alarm_fields.data.slSuperviseRecord.continuationMoney, ',000,000,000.00'))
				}
			    if(typeof(alarm_fields.data.comments)!="undefined"){this.ownerCt.ownerCt.getCmpByName('comments').setValue(alarm_fields.data.comments);	}else{this.ownerCt.ownerCt.getCmpByName('comments').setValue("");}
			    if(typeof(alarm_fields.data.enterpriseBank) != 'undefined' && this.getCmpByName('enterpriseBank.areaName') !=null) {
					this.getCmpByName('enterpriseBank.areaName').setValue(alarm_fields.data.enterpriseBank.areaName);
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
		/*var oppType = this.oppositeType;
		var vDates = "";
		//var source = "";
		//var repaySource = this.repaymentSource.get(0);
		//source = getSourceGridDate(this.repaymentSource.get(0));
		var oppType = this.oppositeType;
		var fundIntentJsonData = this.slFundIntentView.getGridDate();
		//var slActualToChargeJsonData = this.slActualToCharge.getGridDate();
		var gridPanel = this.slFundIntentView;
		//var slActualToCharge = this.slActualToCharge;
		// var ischeck=0;
		var borrowerInfo=getBorrowerInfoData(this.borrowerInfo.get(0))
		var borrowerInfoGrid=this.borrowerInfo.get(0);
		var runId = this.runId;
		var operationType=this.operationType;
		var enterpriseBank=this.getCmpByName("enterpriseBank.id");
		if(operationType=='MicroLoanBusiness'){
			var financeInfo=this.getCmpByName("financeInfo.financeInfoId");
			var personMarry=this.getCmpByName('person.marry').getValue();
			
			if(personMarry!=null && personMarry!="" && personMarry==317){
				var spousePanel=this.getCmpByName('spouse.spouseId')
			}
		}else{
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
		        }else{
		        	var personMarry=this.getCmpByName('person.marry').getValue();		        	
					if(personMarry!=null && personMarry!="" && personMarry==317){
						var spousePanel=this.getCmpByName('spouse.spouseId')
					}
		        }
		}*/
		this.formPanel.getForm().submit({
			clientValidation : false,
			url : __ctxPath + '/project/updatePostponedInfoSlSmallloanProject.do',
			params : {
				projectId : this.projectRecord.data.projectId,
				slSuperviseRecordId : this.record.data.id
			},
			method : 'post',
			waitMsg : '数据正在提交，请稍后...',
			success : function(fp, action) {
				var object = Ext.util.JSON.decode(action.response.responseText)
				Ext.ux.Toast.msg('操作信息', '保存信息成功!');
				
				/*enterpriseBank.setValue(object.enterpriseBankId);
				if(operationType=='MicroLoanBusiness'){
					financeInfo.setValue(object.financeInfoId)
					if(personMarry!=null && personMarry!="" && personMarry==317){
						spousePanel.setValue(object.spouseId)
					}
				}else{
					if (oppType == 'company_customer') {
						eg.getStore().reload();
						op.setValue(object.legalpersonid)
					}else{
						if(personMarry!=null && personMarry!="" && personMarry==317){
							spousePanel.setValue(object.spouseId)
						}
					}
				}
				gridPanel.save();
				//slActualToCharge.savereload();
				borrowerInfoGrid.getStore().reload();*/
				
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
	/*	this.formPanel.getForm().submit({
			clientValidation : false,
			url : __ctxPath + '/project/updateintentSlSmallloanProject.do',
			params : {
				"fundIntentJsonData" : fundIntentJsonData,
				"slActualToChargeJsonData" : slActualToChargeJsonData,
				"activityName" : activityName,
				"runId" : runId

			},
			method : 'post',
			waitMsg : '数据正在提交，请稍后...',
			success : function(fp, action) {
				Ext.ux.Toast.msg('操作信息', '保存信息成功!');
				gridPanel.save();
				slActualToCharge.savereload();
				var tabs = Ext.getCmp('centerTabPanel');
				var projectGrid = tabs.getCmpByName('SmallProjectGrid');
				projectGrid.getStore().reload();
			},
			failure : function(fp, action) {
				Ext.MessageBox.show({
					title : '操作信息',
					msg : '信息保存出错，请联系管理员！',
					buttons : Ext.MessageBox.OK,
					icon : 'ext-mb-error'
				});
			}
		});*/
	}
});