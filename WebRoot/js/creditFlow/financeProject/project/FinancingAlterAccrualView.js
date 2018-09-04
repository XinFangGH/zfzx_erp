/**
 * @author
 * @createtime
 * @class SlBankAccountForm
 * @extends Ext.Window
 * @description SlBankAccount表单
 * @company 智维软件
 */
FinancingAlterAccrualView = Ext.extend(Ext.Panel, {
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
		FinancingAlterAccrualView.superclass.constructor.call(this, {
			id : 'FinancingAlterAccrualView' + this.projectId,
			title : '变更利率-' + this.projectName,
			region : 'fit',
			frame : true,
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
//		fillDataAlterAccrual(DeferApplyInfoPanel,slAlterAccrualRecord,'alterAccrual')
		this.alterAccrualSlFundIntent = new FinancingAlterAccrualSlFundIntent({
			object : DeferApplyInfoPanel,
			preaccrualtype : accrualtype,
			projId : projectId,
			titleText : '偿还计划',
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
						/*, new SlActualToChargeVM({
							projId : projectId,
							isHidden : true,
							businessType : businessType,
							isThisAlterAccrualRecordId : 'noid',
							isUnLoadData : false,
							isThisAlterAccrualRecord : 'no'
						})*/
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
								/*new SlActualToCharge({
									isHiddenOperation : false,
									projId : projectId,
									isUnLoadData : true,
									isHidden : false,
									businessType : businessType,
									isThisAlterAccrualRecordId : 'noid',
									isUnLoadData : true,
									isThisAlterAccrualRecord : 'yes'
								}),*/
								{
									xtype : 'panel',
									border : false,
									bodyStyle : 'margin-bottom:5px',
									html : '<br/><B><font class="x-myZW-fieldset-title">【办理文书上传】:</font></B>'
								}
						, this.uploads = new uploads({
									projectId : projectId,
									isHidden : false,
									tableName : 'fl_alteraccrual_record',
									typeisfile : 'fl_alteraccrual_record.10',
									businessType : businessType,
									uploadsSize : 15
								})
						]
					}]
		})
/*		this.formPanel.loadData({
			url : __ctxPath + '/smallLoan/finance/getInfoLoanedSlAlterAccrualRecord.do?taskId='+this.projectId+'&type='+this.businessType+'&task_id='+this.taskId+'&loanedTypeId='+this.slAlteraccrualRecordId+'&loanedTypeKey=slAlteraccrual',
			method : "POST",
			preName : ['slSmallloanProject','slAlterAccrualRecord','enterprise', 'person',"enterpriseBank","spouse","payintentPeriod"],
			root : 'data',
			success : function(response, options) {
				var respText = response.responseText;
				var alarm_fields = Ext.util.JSON.decode(respText);
				
				var payintentPeriod=alarm_fields.data.payintentPeriod
				if(null!=alarm_fields.data.slAlterAccrualRecord.alterpayintentPeriod){
					this.getCmpByName('alterpayintentPeriod').setValue(payintentPeriod+'.'+alarm_fields.data.slAlterAccrualRecord.alterpayintentPeriod)
					if(payintentPeriod==0){
						this.getCmpByName('alterpayintentPeriod').setRawValue('第'+alarm_fields.data.slAlterAccrualRecord.alterpayintentPeriod+'期')
					}else{
						this.getCmpByName('alterpayintentPeriod').setRawValue('展期第'+alarm_fields.data.slAlterAccrualRecord.alterpayintentPeriod+'期')
					}
				}
				//fillDataAlterAccrual(this,alarm_fields.data.slAlterAccrualRecord,'alterAccrual');
		
			}
		});*/

	},// end of the initcomponents
	save : function() {//====================提交利率变更方法==========================
		var projectId = this.projectId;
		var fp = this.formPanel;
		var intent_plan = fp.getCmpByName('earlyRepaymentfinance').get(1)
				.getGridDate();
		var count=fp.getCmpByName('earlyRepaymentfinance').get(3).grid_UploadsPanel.getStore().getCount();
		var contractids="";
		for(var i=0;i<count;i++){
			var record=fp.getCmpByName('earlyRepaymentfinance').get(3).grid_UploadsPanel.getStore().getAt(i);
			contractids=contractids+record.data.fileid+","
		}
		if(""!=contractids){
			contractids=contractids.substring(0,contractids.length-1)
		}
		if(undefined==intent_plan||""==intent_plan){
			Ext.Msg.alert('提示', '未生成偿还计划，请生成!');
		}else{
		$postForm({
			formPanel : this.formPanel,
			params : {
				"projectId_flow" : projectId,
				"fundIntentJsonData" : intent_plan,
				"contractids" : contractids
			},// 传递的参数flag gaomimi加
			scope : this,
			url : __ctxPath//================================================
//					+ '/project/askForAlterAccrualSlSmallloanProject.do',
			+ '/smallLoan/finance/savealterInfoSlAlterAccrualRecord.do',
			callback : function(fp, action) {
				var tabs = Ext.getCmp('centerTabPanel');
				tabs.remove('FinancingAlterAccrualView' + this.projectId);
			}
		});
		}
	},// end of save
	cancel : function() {
		var tabs = Ext.getCmp('centerTabPanel');
		tabs.remove('FinancingAlterAccrualView' + this.projectId);

	}

});
