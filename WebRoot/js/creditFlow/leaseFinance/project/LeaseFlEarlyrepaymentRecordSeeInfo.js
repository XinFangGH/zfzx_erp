/**
 * @author
 * @createtime
 * @class LeaseFlEarlyrepaymentRecordSeeInfo
 * @extends Ext.Window
 * @description 提前还款详情
 * @company 智维软件
 */
LeaseFlEarlyrepaymentRecordSeeInfo = Ext.extend(Ext.Panel, {
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		// 调用父类构造
		this.initComponents();
		
		LeaseFlEarlyrepaymentRecordSeeInfo.superclass.constructor.call(this, {
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
		this.projectInfo = new EnterpriseBusinessProjectInfoPanel({
			   isDiligenceReadOnly : true,
			   isMineNameEditable:false,
			   isAllReadOnly:true
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
				 bussinessType:"LeaseFinance",
				 isEditEnterprise : false,
				 isReadOnly:true,
				 isNameReadOnly:true,
				 isHidden:true
			});
		}
		var DeferApplyInfoPanel = new FinanceEarlyRepaymentPanel({
			idDefinition:'earlyRepayment',
			projectId : projectId,
			slAlterAccrualRecorddata : null,
			isDiligenceReadOnly : true,
			isAllReadOnly:true
		})
		this.earlyRepaymentSlFundIntent = new EarlyRepaymentSlFundIntent({
			object : DeferApplyInfoPanel,
			projId : projectId,
			titleText : '放款收息表',
			isHidden : true,
			businessType : "LeaseFinance",
			isThisEarlyPaymentRecordId : slEarlyRepaymentId,
			isUnLoadData : false,
			isThisEarlyPaymentRecord : 'yes'
		})
		this.uploads = new uploads({
			projectId : projectId,
			isHidden : true,
			tableName : 'sl_earlyrepayment_record',
			typeisfile : 'sl_earlyrepayment_record.'+slEarlyRepaymentId,
			businessType : "LeaseFinance",
			uploadsSize : 15
		})
		this.slEarlyrepaymentRecordVerification = new LetterAndBookView({
			projectId : projectId,
			businessType : "LeaseFinance",
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
							businessType : "LeaseFinance",
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
			url : __ctxPath + '/supervise/getInfoAheadRefundSlSuperviseRecord.do?projectId='+this.record.data.projectId+'&type=LeaseFinance&loanedTypeId='+this.record.data.slEarlyRepaymentId+'&loanedTypeKey=earlyReyment',
			method : "POST",
			preName : ['flLeaseFinanceProject','slEarlyRepaymentRecord','enterprise', 'person',"enterpriseBank","spouse",'payintentPeriod'],
			root : 'data',
			success : function(response, options) {
				var respText = response.responseText;
				var alarm_fields = Ext.util.JSON.decode(respText);
				var payintentPeriod=alarm_fields.data.payintentPeriod
				if(null!=payintentPeriod){
					this.getCmpByName('prepayintentPeriod1').setValue(payintentPeriod+'.'+alarm_fields.data.slEarlyRepaymentRecord.prepayintentPeriod)
					if(payintentPeriod==0){
						this.getCmpByName('prepayintentPeriod1').setRawValue('第'+alarm_fields.data.slEarlyRepaymentRecord.prepayintentPeriod+'期')
					}else{
						this.getCmpByName('prepayintentPeriod1').setRawValue('展期第'+alarm_fields.data.slEarlyRepaymentRecord.prepayintentPeriod+'期')
					}
				}
			}
		});
	}
});
