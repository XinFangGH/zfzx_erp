/**
 * @author
 * @createtime
 * @class SmallLoanAlterAccrualEditView
 * @extends Ext.Window
 * @description 利率变更详情
 * @company 智维软件
 */
SmallLoanAlterAccrualEditView = Ext.extend(Ext.Panel, {
	// 构造函数
	constructor : function(_cfg) {
		if (typeof(_cfg.businessType) != "undefined") {
			this.businessType = _cfg.businessType;
		}
		if (typeof(_cfg.projectId) != "undefined") {
			this.projectId = _cfg.projectId;
		}
		Ext.applyIf(this, _cfg);
		// 初始化组件
		this.initUIComponents();
		// 调用父类构造
		SmallLoanAlterAccrualEditView.superclass.constructor.call(this, {
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
		var accrualtype = this.accrualtype;
		var payintentPeriod = this.payintentPeriod;
		var slAlterAccrualRecord = this.slAlterAccrualRecord;
		var slAlteraccrualRecordId = slAlterAccrualRecord.data.slAlteraccrualRecordId;
		var DeferApplyInfoPanel = new FinanceAlterAccrualPanel({
			surplusnotMoney : this.surplusnotMoney,
			//intentDate : "",
			//accrualtype : accrualtype,
			payintentPeriod : 4,
			isAllReadOnly:true,
			idDefinition:'alterAccrual',
			projectId : this.projectId,
			slAlterAccrualRecorddata : null
		})
		this.alterAccrualSlFundIntent = new AlterAccrualSlFundIntent({
			object : DeferApplyInfoPanel,
			preaccrualtype : accrualtype,
			projId : projectId,
			titleText : '放款收息表',
			isHidden : true,
			businessType : businessType,
			isThisAlterAccrualRecordId : slAlteraccrualRecordId,
			isUnLoadData : false,
			isThisAlterAccrualRecord : 'yes'
		})
		/*this.uploads = new uploads({
			projectId : this.projectId,
			isHidden : true,
			tableName : 'sl_alteraccrual_record',
			typeisfile : 'sl_alteraccrual_record.'+this.slAlteraccrualRecordId,
			businessType : this.businessType,
			uploadsSize : 15
		})*/
		this.formPanel = new Ext.FormPanel({
			modal : true,
			labelWidth : 100,
			frame : true,
			buttonAlign : 'center',
			layout : 'form',
			border : false,
			autoHeight : true,
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
							isThisAlterAccrualRecordId : slAlteraccrualRecordId,
							isUnLoadData : false,
							isThisAlterAccrualRecord : 'no'
						})]
					}, {
						title : '变更利率登记信息',
						xtype : 'fieldset',
						autoHeight : true,
						collapsible : true,
						name : "earlyRepaymentfinance",
						width : '100%',
						bodyStyle : 'padding-left:8px',
						items : [DeferApplyInfoPanel,
								this.alterAccrualSlFundIntent
								]
					},{
						xtype : 'fieldset',
						title : '利率变更审批表',
		        		bodyStyle : 'padding-left:0px',
						collapsible : true,
						labelAlign : 'right',
						autoHeight : true,
		        		items : [
							new LetterAndBookView({
								projectId:this.projectId,
								businessType:this.businessType,
								LBTemplate:'slAlteraccrualRecordVerification',
								isHidden:true
							})
						]
					}]
		})
		this.loadData({
			url : __ctxPath + '/smallLoan/finance/getSlAlterAccrualRecord.do?slAlteraccrualRecordId='+slAlteraccrualRecordId,
			method : "POST",
			preName : ['slAlterAccrualRecord'],
			root : 'data',
			success : function(response, options) {
				var obj = Ext.util.JSON.decode(response.responseText);
				this.getCmpByName('slAlterAccrualRecord.reason').setValue(obj.data.reason)
				this.getCmpByName('slAlterAccrualRecord.slAlteraccrualRecordId').setValue(obj.data.slAlteraccrualRecordId)
				this.getCmpByName('slAlterAccrualRecord.alterProjectMoney').setValue(obj.data.alterProjectMoney)
				this.getCmpByName('projectMoney1').setValue(obj.data.alterProjectMoney)
				this.getCmpByName('slAlterAccrualRecord.alterpayintentPeriod').setValue(obj.data.alterpayintentPeriod)
				this.getCmpByName('slAlterAccrualRecord.accrual').setValue(obj.data.accrual)
				this.getCmpByName('alterpayintentPeriod').setValue(this.payintentPeriod+'.'+obj.data.alterpayintentPeriod)
				if(this.payintentPeriod==0){
					this.getCmpByName('alterpayintentPeriod').setRawValue('第'+obj.data.alterpayintentPeriod+'期')
				}else{
					this.getCmpByName('alterpayintentPeriod').setRawValue('展期第'+obj.data.alterpayintentPeriod+'期')
				}
				
			}
		})

	}
});
