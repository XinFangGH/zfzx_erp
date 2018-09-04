/**
 * @author
 * @createtime
 * @class SmallLoanEarlyRepaymentEditView
 * @extends Ext.Window
 * @description SlBankAccount表单
 * @company 智维软件
 */
SmallLoanEarlyRepaymentEditView = Ext.extend(Ext.Panel, {
	// 构造函数
	isAllReadOnly : false,
	isHidden : false,
	constructor : function(_cfg) {
		if (typeof(_cfg.businessType) != "undefined") {
			this.businessType = _cfg.businessType;
		}
		if (typeof(_cfg.projectId) != "undefined") {
			this.projectId = _cfg.projectId;
		}
		if (_cfg.isAllReadOnly) {
			this.isAllReadOnly = _cfg.isAllReadOnly;
		}
		if (_cfg.isHidden) {
			this.isHidden = _cfg.isHidden;
		}
		Ext.applyIf(this, _cfg);
		// 初始化组件
		this.initUIComponents();
		// 调用父类构造
		SmallLoanEarlyRepaymentEditView.superclass.constructor.call(this, {
			region : 'fit',
			height : 387,
			autoScroll : true,
			items : [this.formPanel]
		});
	},// end of constructor
	// 初始化组件
	initUIComponents : function() {
		var businessType = this.businessType;
		var projectId = this.projectId;
		var surplusnotMoney = this.surplusnotMoney;
		var intentDate = this.intentDate;
		var accrualtype = this.record.data.accrualtype;
		var slEarlyRepaymentId = this.record.data.slEarlyRepaymentId;
		
		var DeferApplyInfoPanel = new FinanceEarlyRepaymentPanel({
			surplusnotMoney : this.surplusnotMoney,
			payintentPeriod : this.payintentPeriod,
			idDefinition:'earlyRepayment',
			projectId : this.projectId,
			slAlterAccrualRecorddata : null,
			isDiligenceReadOnly : true,
			isAllReadOnly:true
		})
		this.earlyRepaymentSlFundIntent = new EarlyRepaymentSlFundIntent({
			object : DeferApplyInfoPanel,
			preaccrualtype : accrualtype,
			projId : projectId,
			titleText : '放款收息表',
			isHidden : this.isHidden,
			businessType : businessType,
			isThisEarlyPaymentRecordId : slEarlyRepaymentId,
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
		this.slEarlyrepaymentRecordVerification = new LetterAndBookView({
			projectId : this.slEarlyRepaymentId,
			businessType : this.businessType,
			LBTemplate : 'slEarlyrepaymentRecordVerification',
			isHidden : true,
		    isRecordHidden : false,
			isGdEdit : this.isGdEdit_seb
		})
		this.formPanel = new Ext.FormPanel({
			modal : true,
			labelWidth : 100,
			frame : true,
			buttonAlign : 'center',
			layout : 'form',
			border : false,
			// autoHeight : true,
			defaults : {
				anchor : '100%',
				xtype : 'fieldset',
				columnWidth : 1,
				labelAlign : 'right',
				collapsible : true,
				autoHeight : true
			},
			labelAlign : "right",
			items : [
					//														
					{
						title : '历史财务信息',
						xtype : 'fieldset',
						autoHeight : true,
						name : "historyfinance",
						collapsible : true,
						width : '100%',
						bodyStyle : 'padding-left:8px',
						items : [new SlFundIntentViewVM({
							isHiddenOperation : false,
							projectId : projectId,
							businessType : businessType,
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
			url : __ctxPath + '/smallLoan/finance/getSlEarlyRepaymentRecord.do?slEarlyRepaymentId='+slEarlyRepaymentId,
			method : "POST",
			preName : ['slEarlyRepaymentRecord'],
			root : 'data',
			success : function(response, options) {
				var obj = Ext.util.JSON.decode(response.responseText);
				this.getCmpByName('slEarlyRepaymentRecord.reason').setValue(obj.data.reason)
				this.getCmpByName('slEarlyRepaymentRecord.slEarlyRepaymentId').setValue(obj.data.slEarlyRepaymentId)
				this.getCmpByName('slEarlyRepaymentRecord.earlyProjectMoney').setValue(obj.data.earlyProjectMoney)
				this.getCmpByName('projectMoney1').setValue(obj.data.earlyProjectMoney)
				this.getCmpByName('slEarlyRepaymentRecord.prepayintentPeriod').setValue(obj.data.prepayintentPeriod)
				this.getCmpByName('slEarlyRepaymentRecord.accrualtype').setValue(obj.data.accrualtype)
				this.getCmpByName('slEarlyRepaymentRecord.earlyDate').setValue(obj.data.earlyDate)
				this.getCmpByName('prepayintentPeriod1').setValue(this.payintentPeriod+'.'+obj.data.prepayintentPeriod)
				if(this.payintentPeriod==0){
					this.getCmpByName('prepayintentPeriod1').setRawValue('第'+obj.data.prepayintentPeriod+'期')
				}else{
					this.getCmpByName('prepayintentPeriod1').setRawValue('展期第'+obj.data.prepayintentPeriod+'期')
				}
				if(obj.data.accrualtype=='ontTimeAccrual'){
					this.getCmpByName('bgks').hide()
					this.getCmpByName('bgks1').show()
				}else{
					this.getCmpByName('bgks1').hide()
					this.getCmpByName('bgks').show()
				}
			}
		})
	}// end of the initcomponents
});
