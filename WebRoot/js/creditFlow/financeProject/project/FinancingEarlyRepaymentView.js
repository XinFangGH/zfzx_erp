/**
 * @author
 * @createtime
 * @class SlBankAccountForm
 * @extends Ext.Window
 * @description SlBankAccount表单
 * @company 智维软件
 */
FinancingEarlyRepaymentView = Ext.extend(Ext.Panel, {
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
		FinancingEarlyRepaymentView.superclass.constructor.call(this, {
			id : 'FinancingEarlyRepaymentView' + this.projectId,
			title : '提前支取办理-'+this.projectNumber,
			region : 'fit',
			iconCls : 'menu-flowWait',
			items : [this.formPanel],
			tbar : this.toolbar,
			border : false
		});
	},// end of constructor
	// 初始化组件
	initUIComponents : function() {
		
		var projectId = this.projectId;
		var surplusnotMoney = this.surplusnotMoney;
		var payintentPeriod = this.payintentPeriod;
		this.toolbar = new Ext.Toolbar({
			items : [{
				text : '提交',
				iconCls : 'btn-save',
				scope : this,
				handler : this.save
			},'-', {
				text : '取消',
				iconCls : 'btn-cancel',
				scope : this,
				handler : this.cancel
			}]
		})
		this.gridPanel = new Financing_FundIntent_formulate_editGrid({
			projId : this.projectId,
			titleText : '偿还计划',
			isHidden : true,
			isHiddenOperation : true,
			businessType : "Financing",
			calcutype : 1// 融资
		});
		this.deferApplyInfoPanel = new FinancingEarlyRepaymentPanel({
			surplusnotMoney : surplusnotMoney,
			projectId : projectId,
			businessType : this.businessType,
			idDefinition :'earlyRepayment',
			payintentPeriod:payintentPeriod
		})
		this.earlyRepaymentSlFundIntent = new FinanceEarlyRepaymentSlFundIntent({
			object : this.deferApplyInfoPanel,
			projId : projectId,
			titleText : '还款计划表',
			isHidden : false,
			businessType : "Financing",
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
					{
						title : '历史财务信息',
						xtype : 'fieldset',
						autoHeight : true,
						name : "historyfinance",
						collapsible : true,
						width : '100%',
						bodyStyle : 'padding-left:8px',
						items : [
						         this.gridPanel
						]
					}, {
						title : '提前支取登记信息',
						xtype : 'fieldset',
						autoHeight : true,
						name : "earlyRepaymentfinance",
						collapsible : true,
						items : [
						         this.deferApplyInfoPanel,
						         this.earlyRepaymentSlFundIntent
							]
					}]
		})

	},// end of the initcomponents
	save : function() {
		var projectId = this.projectId;
		var fp = this.formPanel;
		var intent_plan_earlyRepayment = fp.getCmpByName('earlyRepaymentfinance').get(1).getGridDate();
		if(intent_plan_earlyRepayment != null && intent_plan_earlyRepayment != "") {
			$postForm({
				formPanel : this.formPanel,
				params : {
					"projectId" : projectId,
					"intent_plan_earlyRepayment" : intent_plan_earlyRepayment
					},// 传递的参数flag gaomimi加
				scope : this,
				url : __ctxPath
						+ '/flFinancing/askForEarlyRepaymentFlFinancingProject.do',
				callback : function(fp, action) {

					var tabs = Ext.getCmp('centerTabPanel');
					tabs.remove('FinancingEarlyRepaymentView' + this.projectId);
					Ext.getCmp('AdvanceRepaymentGrid').getStore().reload();
				}
			});
		}else {
			Ext.ux.Toast.msg('操作信息', '请先生成还款计划表！');
			return;
		}
	},// end of save
	 cancel:function(){
              
              	 var tabs = Ext.getCmp('centerTabPanel');
				tabs.remove('FinancingEarlyRepaymentView' + this.projectId);
              	
     } 

});
