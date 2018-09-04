/**
 * @author
 * @createtime
 * @class SlBankAccountForm
 * @extends Ext.Window
 * @description SlBankAccount表单
 * @company 智维软件
 */
SmallLoanAlterAccrualView = Ext.extend(Ext.Panel, {
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
		SmallLoanAlterAccrualView.superclass.constructor.call(this, {
			id : 'SmallLoanAlterAccrualView' + this.projectId,
			title : '变更利率-' + this.projectName,
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
		var slAlterAccrualRecord = this.slAlterAccrualRecord;
		var DeferApplyInfoPanel = new FinanceAlterAccrualPanel({
			surplusnotMoney : surplusnotMoney,
			intentDate : intentDate,
			accrualtype : accrualtype,
			payintentPeriod : payintentPeriod,
			idDefinition:'alterAccrual',
			projectId : this.projectId,
			slAlterAccrualRecorddata : slAlterAccrualRecord
		})
		fillDataAlterAccrual(DeferApplyInfoPanel,slAlterAccrualRecord,'alterAccrual')
		this.alterAccrualSlFundIntent = new AlterAccrualSlFundIntent({
			object : DeferApplyInfoPanel,
			preaccrualtype : accrualtype,
			projId : projectId,
			titleText : '放款收息表',
			isHidden : false,
			businessType : businessType,
			isThisAlterAccrualRecordId : 'noid', 
			isUnLoadData : true, //加载空的列表，false，就要从数据库那数据
			isThisAlterAccrualRecord : 'yes'
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
							isHiddenautocreateBtn:true,
							isHiddenResBtn1:true,
							isFinbtn:true,
							isHidden1 : true,
							isThisAlterAccrualRecordId : 'noid',
							isUnLoadData : false,
							isThisAlterAccrualRecord : 'no' //yes加载新生成的款项，no原始的款项
						})
//						, new SlActualToChargeVM({
//							projId : projectId,
//							isHidden : true,
//							businessType : businessType,
//							isThisAlterAccrualRecordId : 'noid',
//							isUnLoadData : false,
//							isThisAlterAccrualRecord : 'no'
//						})
						]
					}, {
						title : '变更利率登记信息',
						xtype : 'fieldset',
						autoHeight : true,
						collapsible : true,
						name : "earlyRepaymentfinance",
						width : '100%',
						bodyStyle : 'padding-left:8px',
						items : [DeferApplyInfoPanel,
								this.alterAccrualSlFundIntent,
//								new SlActualToCharge({
//									isHiddenOperation : false,
//									projId : projectId,
//									isUnLoadData : true,
//									isHidden : false,
//									businessType : businessType,
//									isThisAlterAccrualRecordId : 'noid',
//									isUnLoadData : true,
//									isThisAlterAccrualRecord : 'yes'
//								}),
								{
									xtype : 'panel',
									border : false,
									bodyStyle : 'margin-bottom:5px',
									html : '<br/><B><font class="x-myZW-fieldset-title">【办理文书上传】:</font></B>'
								}
//						, this.uploads = new uploads({
//									projectId : projectId,
//									isHidden : false,
//									tableName : 'sl_alteraccrual_record',
//									typeisfile : 'sl_alteraccrual_record.10',
//									businessType : businessType,
//									uploadsSize : 15
//								})
						]
					}]
		})

	},// end of the initcomponents
	save : function() {
		var projectId = this.projectId;
		var fp = this.formPanel;
	//	var money_plan = fp.getCmpByName('historyfinance').get(1).getGridDate();
		var intent_plan = fp.getCmpByName('historyfinance').get(0)
				.getGridDate();
		var intent_plan_earlyRepayment = fp
				.getCmpByName('earlyRepaymentfinance').get(1).getGridDate();
	//	var money_plan_earlyRepayment = fp
	//			.getCmpByName('earlyRepaymentfinance').get(2).getGridDate();
	//	var contractids = fp.getCmpByName('earlyRepaymentfinance').get(4)
	//			.getGridDate();
		$postForm({
			formPanel : this.formPanel,
			params : {
				"projectId" : projectId,
			//	"money_plan" : money_plan,
				"intent_plan" : intent_plan,
				"intent_plan_earlyRepayment" : intent_plan_earlyRepayment
			//	"money_plan_earlyRepayment" : money_plan_earlyRepayment,
			//	"contractids" : contractids
			},// 传递的参数flag gaomimi加
			scope : this,
			url : __ctxPath
					+ '/project/askForAlterAccrualSlSmallloanProject.do',
			callback : function(fp, action) {
				var tabs = Ext.getCmp('centerTabPanel');
				tabs.remove('SmallLoanAlterAccrualView' + this.projectId);
			}
		});
	},// end of save
	cancel : function() {
		var tabs = Ext.getCmp('centerTabPanel');
		tabs.remove('SmallLoanAlterAccrualView' + this.projectId);

	}

});
