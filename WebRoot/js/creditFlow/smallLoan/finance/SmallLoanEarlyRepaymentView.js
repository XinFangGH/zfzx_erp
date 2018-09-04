/**
 * @author
 * @createtime
 * @class SlBankAccountForm
 * @extends Ext.Window
 * @description SlBankAccount表单
 * @company 智维软件
 */
SmallLoanEarlyRepaymentView = Ext.extend(Ext.Panel, {
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
		SmallLoanEarlyRepaymentView.superclass.constructor.call(this, {
			id : 'SmallLoanEarlyRepaymentView' + this.projectId,
			title : '提前还款办理-'+this.projectName,
			region : 'fit',
			iconCls : 'menu-flowWait',
			items : [this.formPanel],
			buttonAlign : 'center',
			buttons : [{
				text : '提交',
				iconCls : 'btn-save',
				scope : this,
				handler : this.save
			}, {
				text : '取消',
				iconCls : 'btn-cancel',
				scope : this,
				handler : this.cancel
			}]
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
		var DeferApplyInfoPanel = new FinanceEarlyRepaymentPanel({
			surplusnotMoney : surplusnotMoney,
			projectId : projectId,
			idDefinition :'earlyRepayment',
			payintentPeriod:payintentPeriod,
			accrualtype:accrualtype
			
			
		})
	//	fillDataEarlyRepay(DeferApplyInfoPanel,null,'earlyRepayment')
		this.earlyRepaymentSlFundIntent = new EarlyRepaymentSlFundIntent({
			object : DeferApplyInfoPanel,
			preaccrualtype : accrualtype,
			projId : projectId,
			titleText : '放款收息表',
			isHidden : false,
			businessType : businessType,
			isThisEarlyPaymentRecordId : 'noid',
			isUnLoadData : true,
			isThisEarlyPaymentRecord : 'yes'
		})
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
							isHidden1 : true,
							isThisEarlyPaymentRecordId : 'noid',
							isUnLoadData : false,
							isThisEarlyPaymentRecord : 'no'
						}), new SlActualToChargeVM({
							projId : projectId,
							isHidden : true,
							businessType : businessType,
							isThisEarlyPaymentRecordId : 'noid',
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
					}]
		})

	},// end of the initcomponents
	save : function() {
		var projectId = this.projectId;
		var fp = this.formPanel;
//		var money_plan = fp.getCmpByName('historyfinance').get(1).getGridDate();
//		var intent_plan = fp.getCmpByName('historyfinance').get(0)
//				.getGridDate();
		var intent_plan_earlyRepayment = fp
				.getCmpByName('earlyRepaymentfinance').get(1).getGridDate();
	//	var money_plan_earlyRepayment = fp
	//			.getCmpByName('earlyRepaymentfinance').get(2).getGridDate();
				
	  //  var contractids=fp.getCmpByName('earlyRepaymentfinance').get(4).getGridDate();
		$postForm({
			formPanel : this.formPanel,
			params : {
				"projectId" : projectId,
		//		"money_plan" : money_plan,
	//			"intent_plan" : intent_plan,
				"intent_plan_earlyRepayment" : intent_plan_earlyRepayment
	//			"money_plan_earlyRepayment" : money_plan_earlyRepayment,
	//		"contractids":contractids
				},// 传递的参数flag gaomimi加
			scope : this,
			url : __ctxPath
					+ '/project/askForEarlyRepaymentSlSmallloanProject.do',
			callback : function(fp, action) {

				var tabs = Ext.getCmp('centerTabPanel');
				tabs.remove('SmallLoanEarlyRepaymentView' + this.projectId);
			}
		});
	},// end of save
	 cancel:function(){
              
              	 var tabs = Ext.getCmp('centerTabPanel');
				tabs.remove('SmallLoanEarlyRepaymentView' + this.projectId);
              	
     } 

});
