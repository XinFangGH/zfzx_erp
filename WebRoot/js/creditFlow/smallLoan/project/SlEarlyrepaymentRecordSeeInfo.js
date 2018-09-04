/**
 * @author
 * @createtime
 * @class SmallLoanAlterAccrualEditView
 * @extends Ext.Window
 * @description 利率变更详情
 * @company 智维软件
 */
SlEarlyrepaymentRecordSeeInfo = Ext.extend(Ext.Panel, {
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		// 调用父类构造
		this.initComponents();
		
		SlEarlyrepaymentRecordSeeInfo.superclass.constructor.call(this, {
			region : 'center',
			layout : 'anchor',
			autoScroll:true,
			height : document.body.clientHeight - 185,
			items : [this.formPanel]
		});
		
		
	},
	initComponents : function() {
		this.slEarlyRepaymentId=this.record.data.slEarlyRepaymentId
		var title="企业客户信息";
		if (this.oppositeType =="person_customer") {
			this.projectInfo = new ExtUD.Ext.CreditLoanProjectInfoPanel({readOnly:true,isCPLX:true});
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


		this.gridPanel=new OwnFundIntentView({
							isHiddenOperation : false,
							projectId :  this.projectId,
							businessType :  this.businessType,
							preceptId : this.fundProjectId,
							isHiddenAddBtn : true,
							isHiddenDelBtn : true,
							isHiddenCanBtn : true,
							isHiddenResBtn : true,
							isHiddenautocreateBtn:true,
							isHiddenResBtn1:true,
							isFinbtn:true,
							isHidden1 : true,
							isThisEarlyPaymentRecordId : this.slEarlyRepaymentId,
							isUnLoadData : false,
							isThisEarlyPaymentRecord : 'no'
						})
						
		this.DeferApplyInfoPanel = new FundFinancePrepaymentForm({
			projectId : this.projectId,
			businessType : this.businessType,
			readOnly:true,
			preceptId:this.fundProjectId
		})
		 this.earlyRepaymentSlFundIntent = new EarlyRepaymentSlFundIntent({
			object : this.DeferApplyInfoPanel,
			preceptId:this.fundProjectId,
			projId : this.projectId,
			titleText : '放款收息表',
			isHidden : true,
			businessType : this.businessType,
			isThisEarlyPaymentRecordId : this.slEarlyRepaymentId,
			isUnLoadData : false,
			isThisEarlyPaymentRecord : 'yes'
		})
		this.uploads = new uploads({
			projectId : this.projectId,
			isHidden : true,
			tableName : 'sl_earlyrepayment_record',
			typeisfile : 'sl_earlyrepayment_record.'+this.slEarlyRepaymentId,
			businessType : this.businessType,
			uploadsSize : 15
		})
		//提前还款审批表
		this.slEarlyrepaymentRecordVerification = new LetterAndBookView({
			projectId : this.projectId,
			businessType : this.businessType,
			titleText:'提前还款审批表',
			LBTemplate : 'slEarlyrepaymentRecordVerification',
			isHidden : true,
		    isRecordHidden : true,
			isGdEdit : true
		})
		this.formPanel = new Ext.Panel({
			modal : true,
			labelWidth : 100,
			buttonAlign : 'center',
			layout : 'form',
			border : false,
			defaults : {
				anchor : '100%',
				xtype : 'fieldset',
				labelAlign : 'left',
				collapsible : true,
				autoHeight : true
			},
			items : [{
				xtype : 'hidden',
				name : 'preHandler',
				value : 'slEarlyRepaymentRecordService.savePrepaymentInfoNextStep'
			}, {
				xtype : 'hidden',
				name : 'slEarlyRepaymentId',
				value:this.slEarlyRepaymentId
			},{
				xtype : 'hidden',
				name : 'fundProjectId',
				value : this.fundProjectId
			}, {
				name : 'earlyFundIntentJsonData',
				xtype : 'hidden'
			},{
				xtype : 'fieldset',
				name:'projectInfo',
				title : '项目基本信息 ',
				collapsible : true,
				autoHeight : true,
				labelAlign : 'right',
				items : [this.projectInfo]
			}, {
				xtype : 'fieldset',
				title :title,
				bodyStyle : 'padding-left:0px',
				collapsible : true,
				labelAlign : 'right',
				autoHeight : true,
				items : [this.perMain]
			}
			, {       
			    xtype : 'fieldset',
				title : '当前款项资金信息',
				bodyStyle : 'padding-left:0px',
				collapsible : true,
				labelAlign : 'right',
				name:'historyfinance',
				autoHeight : true,
				items : [
					this.gridPanel
				]
			},{
				xtype : 'fieldset',
				title : '提前还款登记信息',
				bodyStyle : 'padding-left:0px',
				collapsible : true,
				labelAlign : 'right',
				autoHeight : true,
				items : [
				    this.DeferApplyInfoPanel,
					this.earlyRepaymentSlFundIntent
				]
			}, {
				xtype : 'fieldset',
				collapsible : true,
				autoHeight : true,
				title : '提前还款申请表',
				items : [this.uploads]
			},{
				xtype : 'fieldset',
				collapsible : true,
				autoHeight : true,
				title : '提前还款审批表',
				items : this.slEarlyrepaymentRecordVerification
			}]
		})

		this.loadData({
			url : __ctxPath + '/creditFlow/getInfoLoanedCreditProject.do?slProjectId='+this.projectId+'&type='+this.businessType+'&loanedTypeId='+this.slEarlyRepaymentId+'&loanedTypeKey=earlyReyment',
			method : "POST",
			preName : ['slSmallloanProject','slEarlyRepaymentRecord','enterprise', 'person',"enterpriseBank","spouse",'payintentPeriod'],
			root : 'data',
			success : function(response, options) {
				var respText = response.responseText;
				var alarm_fields = Ext.util.JSON.decode(respText);
			   
				if(typeof(alarm_fields.data.enterpriseBank) != 'undefined' && this.getCmpByName('enterpriseBank.areaName') !=null) {
					this.getCmpByName('enterpriseBank.areaName').setValue(alarm_fields.data.enterpriseBank.areaName);
				}
		
		
			}
		});}
});
