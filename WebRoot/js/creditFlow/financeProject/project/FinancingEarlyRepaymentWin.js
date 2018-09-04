/**
 * @author
 * @createtime
 * @class SlBankAccountForm
 * @extends Ext.Window
 * @description SlBankAccount表单
 * @company 智维软件
 */
FinancingEarlyRepaymentWin = Ext.extend(Ext.Window, {
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
		FinancingEarlyRepaymentWin.superclass.constructor.call(this, {
			id : 'FinancingEarlyRepaymentWin' + this.projectId,
			title : '提前还款查看',
			region : 'fit',
			iconCls : 'menu-flowWait',
			height : 600,
			width :1000,
			autoScroll : true,
			items : [this.formPanel],
			tbar : this.isHidden==true?null:this.toolbar,
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
		this.gridPanel = new SlFundIntentViewVM({
			isHiddenOperation : false,
			projectId :  this.projectId,
			businessType :  "Financing",
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
		this.deferApplyInfoPanel = new FinanceEarlyRepaymentPanel({
			surplusnotMoney : surplusnotMoney,
			isAllReadOnly:true,
			projectId : projectId,
			businessType : "Financing",
			idDefinition :'earlyRepayment',
			payintentPeriod:payintentPeriod
		})
		this.earlyRepaymentSlFundIntent = new EarlyRepaymentSlFundIntent({
			object : this.deferApplyInfoPanel,
			projId : projectId,
			isHidden:true,
			titleText : '还款计划表',
			businessType : "Financing",
			isThisEarlyPaymentRecordId : this.slEarlyRepaymentId,
			isUnLoadData : false,
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
						title : '提前还款登记信息',
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
		this.loadData({
			url : __ctxPath + '/smallLoan/finance/getSlEarlyRepaymentRecord.do?slEarlyRepaymentId='+this.slEarlyRepaymentId,
			method : "POST",
			preName : ['slEarlyRepaymentRecord'],
			root : 'data',
			success : function(response, options) {
	          
				
				var respText = response.responseText;
				var alarm_fields = Ext.util.JSON.decode(respText);
				this.getCmpByName('slEarlyRepaymentRecord.reason').setValue(alarm_fields.data.reason)
				this.getCmpByName('projectMoney1').setValue(Ext.util.Format.number(alarm_fields.data.earlyProjectMoney,'0,000.00'))
				this.getCmpByName('prepayintentPeriod1').setValue(payintentPeriod+'.'+alarm_fields.data.prepayintentPeriod)
				this.getCmpByName('prepayintentPeriod1').setRawValue('第'+alarm_fields.data.prepayintentPeriod+'期')
			}
		});
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
