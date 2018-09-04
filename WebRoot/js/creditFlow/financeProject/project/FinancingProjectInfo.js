/**
 * @author lisl
 * @class FinancingProjectInfo
 * @description 融资项目信息
 * @extends Ext.Panel
 */
FinancingProjectInfo = Ext.extend(Ext.Panel, {
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		this.initUIComponents();
		FinancingProjectInfo.superclass.constructor.call(this, {
			autoScroll : true,
			height : document.body.clientHeight - 185,
			items : []
		});
	},
	initUIComponents : function() {
		var jsArr = [
			__ctxPath + '/js/commonFlow/ExtUD.Ext.js',
			__ctxPath + '/js/selector/UserDialog.js',
			__ctxPath + '/js/creditFlow/finance/Financing_FundIntent_formulate_editGrid.js',
			__ctxPath + '/js/creditFlow/finance/detailView.js',
			__ctxPath + '/js/creditFlow/finance/CashQlideAndCheckForm.js',
			__ctxPath + '/js/creditFlow/financeProject/project/FlEarlyrepaymentRecordView.js',
			__ctxPath + '/js/creditFlow/finance/SlFundIntentViewVM.js',
			__ctxPath + '/js/creditFlow/smallLoan/finance/FinanceEarlyRepaymentPanel.js',
		 	__ctxPath + '/js/creditFlow/smallLoan/finance/EarlyRepaymentSlFundIntent.js',
			__ctxPath + '/js/creditFlow/financeProject/project/FinancingEarlyRepaymentWin.js',
	    	__ctxPath + '/js/customer/InvestPersonCareForm.js',
	    	__ctxPath + '/js/customer/CustomerCareRecords.js',
	    	__ctxPath + '/js/customer/CareEditForm.js',
			__ctxPath + '/js/creditFlow/financeProject/project/loadDataCommon.js'
		];
		$ImportSimpleJs(jsArr, this.constructPanel, this);
	},// 初始化组件
	constructPanel : function() {
		this.taskId = this.record.data.taskId;
		this.projectId = this.record.data.projectId;
		this.projectStatus = this.record.data.projectStatus;
		var isHiddenPanel = false;
		if(this.projectStatus == 0) {
			isHiddenPanel = true;
		}
		this.gridPanel = new Financing_FundIntent_formulate_editGrid({
			projId : this.projectId,
			titleText : '偿还计划',
			isHidden : true,
			isHiddenPanel : isHiddenPanel,
			isHiddenOperation : true,
			businessType : "Financing",
			calcutype : 1// 融资
		});
		this.outPanel = new Ext.Panel({
			modal : true,
			labelWidth : 100,
			layout : 'form',
			border : false,
			defaults : {
				anchor : '100%',
				xtype : 'fieldset',
				labelAlign : 'left',
				collapsible : true,
				autoHeight : true
			},
			labelAlign : "right",
			items : [{
				xtype : 'hidden',
				name : 'preHandler',
				value : 'flFinancingProjectService.companyManagersReview'
			}, {
				title : '<a name="fl_projectInfo_see' + this.projectId + '"><font color="red">01</font>项目基本信息</a>',
				name : 'projectInfo',
				items : [new ExtUD.Ext.TypeSelectInfoThreeGradesReadOnlyPanel({isAllReadOnly:true}),
						new ExtUD.Ext.TypeSelectInfoMineType({isAllReadOnly : true})]
				//items : [new ExtUD.Ext.ProjectBaseInfo({isReadOnly : true})]
			}, {
				title : '<a name="fl_personInfo_see' + this.projectId + '"><font color="red">02</font>投资人资料</a>',
				items : [new ExtUD.Ext.PersonInfo({isAllReadOnly : true})]
			}, {
				title : '<a name="fl_accountInfo_see' + this.projectId + '"><font color="red">03</font>收息账号信息</a>',
				items : [new ExtUD.Ext.PersonAccountInfo({isAllReadOnly : true})]
			}, {
				title : '<a name="fl_financeInfo_see' + this.projectId + '"><font color="red">04</font>资金款项信息</a>',
				items : [new ExtUD.Ext.FinanceInfoPanel({idDefinition : "finance" + this.projectId,isAllReadOnly : true,isStartDateReadOnly:true}),this.gridPanel]
			},{
				title : '<a name="fl_earlyrepaymentRecord_see' + this.projectId
						+ '"><font color="red">05</font>提前还款记录</a>',
				hidden : !(isGranted('earlyrepaymentRecord_' + this.projectStatus)||isGranted('seeearlyrepaymentRecord_' + this.projectStatus)),
				items : [new FlEarlyrepaymentRecordView({
							projectId : this.projectId,
							businessType : this.record.data.businessType,
							isHiddenRemove:true,
							projectStatus : this.projectStatus,
							oppositeType:this.oppositeType
						})]
			}]
		})
		this.loadData({
			//url : __ctxPath + '/creditFlow/getInfoCreditProject.do?taskId=' + this.projectId + '&type=Financing',
			url : __ctxPath + "/flFinancing/getInfoFlFinancingProject.do?flProjectId="+this.projectId,
			method : "POST",
			preName : ['investPerson', 'flFinancingProject',"enterpriseBank"],
			root : 'data',
			success : function(response, options) {
				var respText = response.responseText;
				var alarm_fields = Ext.util.JSON.decode(respText);
				this.getCmpByName('projectMoney1').setValue(Ext.util.Format.number(alarm_fields.data.flFinancingProject.projectMoney,'0,000.00'))
				var appUsers = alarm_fields.data.flFinancingProject.appUsers;
				var appUserId = alarm_fields.data.flFinancingProject.appUserId;
				if ("" != appUserId && null != appUserId) {
					this.getCmpByName('degree').setValue(appUserId);
					this.getCmpByName('degree').setRawValue(appUsers);
					this.getCmpByName('degree').nextSibling().setValue(appUserId);
				}
				var areaperName = alarm_fields.data.investPerson.areaText;
				var areaperId = alarm_fields.data.investPerson.areaId;
				if(null !=areaperId && areaperId !='undefined'){
					this.getCmpByName('investPerson.areaId').setValue(areaperId);
					this.getCmpByName('areaName').setValue(areaperName);
				}
				if(alarm_fields.data.enterpriseBank != null && typeof(alarm_fields.data.enterpriseBank) != 'undefined') {
					var openTypeValue = alarm_fields.data.enterpriseBank.openType;
					var accountTypeValue = alarm_fields.data.enterpriseBank.accountType;
					var accountType = this.getCmpByName('enterpriseBank.accountType');
					var arrStore = null;
				    if(openTypeValue == 0) {
						arrStore = new Ext.data.SimpleStore({
							fields : ["name", "id"],
							data : [["个人储蓄户", "0"]]
						});
					}else if(openTypeValue == 1) {
						arrStore = new Ext.data.SimpleStore({
							fields : ["name", "id"],
							data : [["基本户", "1"], ["一般户", "2"]]
						});
					}
					accountType.clearValue();
					accountType.store = arrStore;
					accountType.setValue(accountTypeValue);
					if(accountTypeValue == 0) {
						accountType.setRawValue("个人储蓄户");
					}else if(accountTypeValue == 1) {
						accountType.setRawValue("基本户");
					}else {
						accountType.setRawValue("一般户");
					}
					var areaName = alarm_fields.data.enterpriseBank.areaName;
					var areaId = alarm_fields.data.enterpriseBank.areaId;
					this.getCmpByName('enterpriseBank.areaName').setValue(areaName);
					this.getCmpByName('enterpriseBank.areaName').setRawValue(areaName);
					this.getCmpByName('enterpriseBank.areaId').setValue(areaId);
					this.getCmpByName('enterpriseBank.id').setValue(alarm_fields.data.enterpriseBank.id);
				}
			}
		});
		this.formPanel = new Ext.FormPanel();
		this.formPanel.add(this.outPanel);
		this.add(this.formPanel);
		this.doLayout();
	}
});