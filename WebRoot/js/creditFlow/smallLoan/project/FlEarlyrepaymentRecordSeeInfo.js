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
		if (_cfg.businessType) {
			this.businessType = _cfg.businessType;
		}
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
		var projectId = this.record.data.projectId;
		var slEarlyRepaymentId = this.record.data.slEarlyRepaymentId;
		this.perMain = "";
		this.projectInfo = new ExtUD.Ext.McroLoanProjectInfoPanel({
			isAllReadOnly : true,
			isDiligenceReadOnly:true
		});
	
		var title="企业客户信息";
		if (this.oppositeType =="person_customer") {
			this.perMain = new ExtUD.Ext.PeerPersonMainInfoPanel({
				isEditPerson : false,
				isReadOnly:true,
				isSpouseReadOnly: true,
				isNameReadOnly:true,
				isHideGudongInfo:true
			});
			title="个人客户信息";
		} else if(this.oppositeType =="company_customer"){
			
			     this.perMain = new ExtUD.Ext.PeerMainInfoPanel({
				 projectId : projectId,
				 bussinessType:this.businessType,
				 isEditEnterprise : false,
				 isReadOnly:true,
				 isNameReadOnly:true
			});
		}
		var DeferApplyInfoPanel = new FinanceEarlyRepaymentPanel({
			//surplusnotMoney : this.surplusnotMoney,
		//	payintentPeriod : this.payintentPeriod,
			idDefinition:'earlyRepayment',
			projectId : projectId,
			slAlterAccrualRecorddata : null,
			isDiligenceReadOnly : true,
			isAllReadOnly:true
		})
		this.earlyRepaymentSlFundIntent = new EarlyRepaymentSlFundIntent({
			object : DeferApplyInfoPanel,
		//	preaccrualtype : accrualtype,
			projId : projectId,
			titleText : '放款收息表',
			isHidden : true,
			businessType : this.businessType,
			isThisEarlyPaymentRecordId : slEarlyRepaymentId,
			isUnLoadData : false,
			isThisEarlyPaymentRecord : 'yes'

		})
		this.uploads = new uploads({
			projectId : projectId,
			isHidden : true,
			tableName : 'sl_earlyrepayment_record',
			typeisfile : 'sl_earlyrepayment_record.'+slEarlyRepaymentId,
			businessType : this.businessType,
			uploadsSize : 15
		})
		this.slEarlyrepaymentRecordVerification = new LetterAndBookView({
			projectId : projectId,
			businessType : this.businessType,
			LBTemplate : 'slEarlyrepaymentRecordVerification',
			isHidden : true,
		    isRecordHidden : false,
			isGdEdit : this.isGdEdit_seb
		})
		this.formPanel=new Ext.FormPanel({
			autoWidth : true,
			items : [{
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
			},{
				title : '历史财务信息',
				xtype : 'fieldset',
				autoHeight : true,
				name : "historyfinance",
				collapsible : true,
				autoWidth : true,
				bodyStyle : 'padding-left:8px',
				items : [new SlFundIntentViewVM({
							isHiddenOperation : false,
							projectId : projectId,
							businessType : this.businessType,
							isHiddenAddBtn : true,
							isHiddenDelBtn : true,
							isHiddenCanBtn : true,
							isHiddenResBtn : true,
							isHiddenautocreateBtn:true,
							isHiddenResBtn1:true,
							isFinbtn:true,
							isHidden1 : true,
							isThisEarlyPaymentRecordId : slEarlyRepaymentId,
							isUnLoadData : false,
							isThisEarlyPaymentRecord : 'no'

						})]
			}, {
				title : '提前还款登记信息',
				xtype : 'fieldset',
				autoHeight : true,
				name : "earlyRepaymentfinance",
				collapsible : true,
				items : [DeferApplyInfoPanel,
						this.earlyRepaymentSlFundIntent
						]
			},{
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
			url : __ctxPath + '/creditFlow/getInfoLoanedCreditProject.do?taskId='+projectId+'&type=SmallLoan&loanedTypeId='+slEarlyRepaymentId+'&loanedTypeKey=earlyReyment',
			method : "POST",
			preName : ['slSmallloanProject','slEarlyRepaymentRecord','enterprise', 'person',"enterpriseBank","spouse",'payintentPeriod'],
			root : 'data',
			success : function(response, options) {
	          
				
				var respText = response.responseText;
				var alarm_fields = Ext.util.JSON.decode(respText);
				this.infosourceId=alarm_fields.data.slSmallloanProject.infosourceId
				this.getCmpByName('projectMoney1').setValue(Ext.util.Format.number(alarm_fields.data.slEarlyRepaymentRecord.earlyProjectMoney,'0,000.00'))
				this.getCmpByName('shengyubenjin').setValue(Ext.util.Format.number((alarm_fields.data.slSmallloanProject.projectMoney-alarm_fields.data.slEarlyRepaymentRecord.earlyProjectMoney),'0,000.00'))
				if(typeof(alarm_fields.data.enterpriseBank) != 'undefined') {
					this.getCmpByName('enterpriseBank.areaName').setValue(alarm_fields.data.enterpriseBank.areaName);
				}
				var payintentPeriod=alarm_fields.data.payintentPeriod
				if(null!=payintentPeriod){
					this.getCmpByName('prepayintentPeriod1').setValue(payintentPeriod+'.'+alarm_fields.data.slEarlyRepaymentRecord.prepayintentPeriod)
					if(payintentPeriod==0){
						this.getCmpByName('prepayintentPeriod1').setRawValue('第'+alarm_fields.data.slEarlyRepaymentRecord.prepayintentPeriod+'期')
					}else{
						this.getCmpByName('prepayintentPeriod1').setRawValue('展期第'+alarm_fields.data.slEarlyRepaymentRecord.prepayintentPeriod+'期')
					}
				}
				/*if(alarm_fields.data.slEarlyRepaymentRecord.accrualtype=='ontTimeAccrual'){
					this.getCmpByName('bgks').hide()
					this.getCmpByName('bgks1').show()
				}else{
					this.getCmpByName('bgks1').hide()
					this.getCmpByName('bgks').show()
				}*/
				var appUsers= alarm_fields.data.slSmallloanProject.appUsers;
				var appUserId= alarm_fields.data.slSmallloanProject.appUserId;
				if(""!=appUserId &&  null!=appUserId){
				
				   this.getCmpByName('degree').setValue(appUserId);
				   this.getCmpByName('degree').setRawValue(appUsers);
				   this.getCmpByName('degree').nextSibling().setValue(appUserId);
				}
				if(this.oppositeType =="person_customer"){
					if(alarm_fields.data.person.marry==317){
						this.getCmpByName('spousePanelSmall').show()
					}else{
						this.getCmpByName('spousePanelSmall').hide()
					}
				}
		
			}
		});
	}
});
