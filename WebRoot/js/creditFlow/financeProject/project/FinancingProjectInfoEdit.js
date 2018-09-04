/**
 * @author lisl
 * @class FinancingProjectInfoEdit
 * @description 融资项目信息(编辑)
 * @extends Ext.Panel
 */
FinancingProjectInfoEdit = Ext.extend(Ext.Panel, {
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		this.initUIComponents();
		FinancingProjectInfoEdit.superclass.constructor.call(this, {
			autoScroll : true,
			height : document.body.clientHeight - 185,
			tbar : this.toolbar,
			border : false,
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
			__ctxPath + '/js/creditFlow/finance/FinanceCalculateFundIntent.js',
			__ctxPath + '/js/creditFlow/finance/FinanceCalulateFinancePanel.js',
			__ctxPath + '/js/creditFlow/finance/FinanceCaluateIntentGrid.js',
			__ctxPath + '/js/creditFlow/finance/FinanceCalulateloadDataCommon.js',
			__ctxPath + '/js/creditFlow/financeProject/project/loadDataCommon.js',
			__ctxPath + '/js/creditFlow/financeProject/project/FinancingEarlyRepaymentWin.js'
		];
		$ImportSimpleJs(jsArr, this.constructPanel, this);
		this.toolbar = new Ext.Toolbar({
			items : ['->', {
				iconCls : 'btn-save',
				text : '保存',
				scope : this,
				handler : this.saveAllDatas
			}]
		})
	},// 初始化组件
	constructPanel : function() {
		this.projectId = this.record.data.projectId;
		this.projectStatus = this.record.data.projectStatus;
		var isHiddenPanel = false;
		if(this.projectStatus == 0) {
			isHiddenPanel = true;
		}
		this.projectInfoFinance = new ExtUD.Ext.FinanceInfoPanel({
			projectId : this.projectId,
			idDefinition : "finance" + this.projectId
		})
		this.gridPanel = new Financing_FundIntent_formulate_editGrid({
			projId : this.projectId,
			object : this.projectInfoFinance,
			titleText : '偿还计划',
			isHidden : false,
			isHiddenPanel : isHiddenPanel,
			businessType : "Financing",
			calcutype : 1// 融资
		});
		this.outPanel = new Ext.Panel({
			modal : true,
			buttonAlign : 'center',
			layout : 'form',
			border : false,
			frame : true,
			defaults : {
				anchor : '100%',
				xtype : 'fieldset',
				columnWidth : 1,
				collapsible : true,
				autoHeight : true
			},
			labelAlign : "right",
			items : [{
				title : '<a name="fl_projectInfo_edit' + this.projectId + '"><font color="red">01</font>项目基本信息</a>',
				items : [new ExtUD.Ext.TypeSelectInfoThreeGradesReadOnlyPanel(),
						new ExtUD.Ext.TypeSelectInfoMineType()]
				//items : [new ExtUD.Ext.ProjectBaseInfo()]
			}, {
				title : '<a name ="fl_personInfo_edit' + this.projectId + '"><font color="red">02</font>投资人资料</a>',
				items : [new ExtUD.Ext.PersonInfo()]
			}, {
				title : '<a name="fl_accountInfo_edit' + this.projectId + '"><font color="red">03</font>收息账号信息</a>',
				items : [new ExtUD.Ext.PersonAccountInfo()]
			}, {
				title : '<a name="fl_financeInfo_edit' + this.projectId + '"><font color="red">04</font>资金款项信息</a>',
				items : [this.projectInfoFinance,this.gridPanel]
			},{
				title : '<a name="fl_earlyrepaymentRecord_edit' + this.projectId
						+ '"><font color="red">05</font>提前还款记录</a>',
				hidden : !(isGranted('earlyrepaymentRecord_' + this.projectStatus)||isGranted('seeearlyrepaymentRecord_' + this.projectStatus)),
				items : [new FlEarlyrepaymentRecordView({
							projectId : this.projectId,
							businessType : this.record.data.businessType,
							projectStatus : this.projectStatus,
							oppositeType:this.oppositeType
						})]
			}]
		});
		this.loadData({
			//url : __ctxPath + '/creditFlow/getInfoCreditProject.do?taskId=' + this.projectId + '&type=Financing',
			url : __ctxPath + "/flFinancing/getInfoFlFinancingProject.do?flProjectId="+this.projectId,
			preName : ['enterprise', 'investPerson', 'flFinancingProject','businessType'],
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
				var areaperId = alarm_fields.data.investPerson.areaId;
				var areaperName = alarm_fields.data.investPerson.areaText;
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
					fillData(this,alarm_fields,"finance" + this.projectId);
				}
			}
		});
		this.formPanel = new Ext.FormPanel();
		this.formPanel.add(this.outPanel);
		this.add(this.formPanel);
		this.doLayout();
	},// 初始化UI结束
	saveAllDatas : function() {
		var fundIntentJsonData=this.gridPanel.getGridDate();
		/*if (this.getCmpByName('person.cardtype').getValue() == 309) {
			if (validateIdCard(this.getCmpByName('person.cardnumber').getValue()) == 1) {
				Ext.Msg.alert('身份证号码验证', '证件号码不正确,请仔细核对')
				return;
			} else if (validateIdCard(this.getCmpByName('person.cardnumber').getValue()) == 2) {
				Ext.Msg.alert('身份证号码验证', '证件号码地区不正确,请仔细核对')
				return;
			} else if (validateIdCard(this.getCmpByName('person.cardnumber').getValue()) == 3) {
				Ext.Msg.alert('身份证号码验证', '证件号码生日日期不正确,请仔细核对')
				return;
			}
		}*/
		this.formPanel.getForm().submit({
			clientValidation : false,
//			url : __ctxPath + '/flFinancing/updateFlFinancingProject.do',
			url : __ctxPath + '/flFinancing/updateEditFlFinancingProject.do',
			params :{
				'fundIntentJson' : fundIntentJsonData,
				'isDeleteAllFundIntent' : 1
			},
			method : 'post',
			waitMsg : '数据正在提交，请稍后...',
			scope : this,
			success : function(fp, action) {
				var object = Ext.util.JSON.decode(action.response.responseText)
				ZW.refreshTaskPanelView();
				Ext.ux.Toast.msg('操作信息', '保存信息成功!');
			},
			failure : function(fp, action) {
				Ext.MessageBox.show({
					title : '操作信息',
					msg : '信息保存出错，请联系管理员！',
					buttons : Ext.MessageBox.OK,
					icon : 'ext-mb-error'
				});
			}
		});
	},
	complatePro : function() {
		var projectId = this.projectId;
		var gridPanel = this.gridPanel;
		Ext.Msg.confirm('信息确认', '您确认要完成所选项目吗？', function(btn) {
			if (btn == 'yes') {
				Ext.Ajax.request({
					url : __ctxPath + '/creditFlow/complateCreditProject.do',
					params : {
						projectId : projectId
					},
					scope : this,
					method : 'post',
					success : function(resp, op) {
						var res = Ext.util.JSON.decode(resp.responseText);
						if (res.success) {
							Ext.ux.Toast.msg('信息提示', '成功完成项目！');
							closeProjectInfoTab(gridPanel,"FinancingProjectInfo_","FinancingProjectInfoEdit_");
							gridPanel.getStore().reload();
							var tabs = Ext.getCmp('centerTabPanel');
							var projectGrid = tabs.getCmpByName('FinancingProjectGrid');
							projectGrid.getStore().reload();
						} else {
							Ext.ux.Toast.msg('信息提示', '出错，请联系管理员！');
						}
					},
					failure : function() {
						Ext.ux.Toast.msg('信息提示', '出错，请联系管理员！');
					}
				});
			}
		});
	}
});