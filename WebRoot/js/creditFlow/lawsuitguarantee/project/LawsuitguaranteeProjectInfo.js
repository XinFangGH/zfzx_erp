/**
 * @author lisl
 * @description 诉讼担保项目信息
 * @extends Ext.Panel
 */
LawsuitguaranteeProjectInfo = Ext.extend(Ext.Panel, {
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		this.initUIComponents();
		LawsuitguaranteeProjectInfo.superclass.constructor.call(this, {
			id : 'LawsuitguaranteeProjectInfo_' + this.record.data.projectId,// id属性值前缀,通过是否包含"Edit"来区分查看和编辑页面
			title : "项目详细-" + this.record.data.projectName,
			iconCls : 'btn-detail',
			items : []
		});
	},
	initUIComponents : function() {
		var jsArr = [
				__ctxPath + '/js/commonFlow/ExtUD.Ext.js',// 客户信息 项目基本信息
				__ctxPath + '/js/selector/UserDialog.js',
				__ctxPath + '/js/creditFlow/common/EnterpriseShareequity.js',// 股东信息
				__ctxPath + '/js/creditFlow/lawsuitguarantee/project/LawsuitBusinessUI.js',// 项目基本信息
				__ctxPath + '/js/creditFlow/finance/SlActualToChargelawsuit.js',// 实际收费项目
						__ctxPath + '/js/creditFlow/guarantee/materials/SlEnterPriseProcreditMaterialsView.js',// 贷款材料
				__ctxPath + '/js/creditFlow/finance/SlActualToCharge.js',
				__ctxPath + '/js/creditFlow/smallLoan/contract/SlContractView.js'];
		$ImportSimpleJs(jsArr, this.constructPanel, this);
	},// 初始化组件
	constructPanel : function() {
		this.projectId = this.record.data.projectId;
		this.taskId = this.record.data.taskId;
		this.projectStatus = this.record.data.projectStatus;
		this.oppositeType = this.record.data.oppositeType;
		this.projectInfo = new lawsuitBusinessProjectInfoPanel({
			isAllReadOnly : true
		});

		this.projectInfoFinance = new ProjectInfoLawsuitFinancePanel({
			isAllReadOnly : true,
			isHiddenPanel : !isGranted('lgFinanceBaseInfo_' + this.projectStatus)
		});

		this.GlActualToChargePanel = new SlActualToChargelawsuit({
			projId : this.projectId,
			businessType : 'BeNotFinancing',
			isHidden : true,
			isHiddenPanel : !isGranted('lgActualToCharge_' + this.projectStatus)
		});
		var cusTitle = "企业客户信息";
		this.perMain = "";
		if (this.oppositeType == "person_customer") {
			this.perMain = new ExtUD.Ext.PeerPersonMainInfoPanel({
				isAllReadOnly : true
			});
			cusTitle = "个人客户信息";
		} else if (this.oppositeType == "company_customer") {
			
			this.perMain = new ExtUD.Ext.PeerMainInfoPanel({
				projectId : this.projectId,
				bussinessType : 'BeNotFinancing',
				isAllReadOnly : true
			});
		};
		this.slProcreditMaterialsView = new SlEnterPriseProcreditMaterialsView({
			     projectId : this.projectId,
			     isHidden_materials:false,
			     businessType:this.businessType,
			     isHiddenArchive : false,
			     operationType:this.operationType
		    });
		this.slContractView = new SlContractView({
			projId : this.projectId,
			isHiddenAffrim : false,
			businessType : 'BeNotFinancing',
			HTLX : 'XEDQ',
			isSignHidden : false,
			islcEdit : true
		});
		this.outPanel = new Ext.Panel({
			frame : true,
			modal : true,
			labelWidth : 100,
			buttonAlign : 'center',
			layout : 'form',
			border : false,
			defaults : {
				anchor : '100%',
				xtype : 'fieldset',
				columnWidth : 1,
				labelAlign : 'right',
				collapsible : true,
				autoHeight : true
			},
			labelAlign : "right",
			items : [{
				title : '项目控制信息',
				name : 'projectInfo',
				hidden : !isGranted('lgProjectInfo_' + this.projectStatus),
				items : [this.projectInfo]
			}, {
				title : cusTitle,
				hidden : !isGranted('lgCustomerInfo_' + this.projectStatus),
				items : [this.perMain]
			}, {
				title : '资金款项信息',
				bodyStyle : 'padding-left:0px',
				labelAlign : 'right',
				hidden : !isGranted('lgFinanceBaseInfo_' + this.projectStatus)&&!isGranted('lgActualToCharge_' + this.projectStatus),
				items : [this.projectInfoFinance, this.GlActualToChargePanel]
			}, {
				title : '业务资料',
				hidden : !isGranted('seeCreditMaterials_l' + this.projectStatus),
				items : [this.SlProcreditMaterialsView]
			}, {
				title : '合同',
				hidden : !isGranted('lgContract_' + this.projectStatus),
				items : [this.slContractView]
			}]
		});

		this.loadData({

			url : __ctxPath + '/creditFlow/getInfoCreditProject.do?taskId='
					+ this.projectId + '&type=BeNotFinancing' + '&task_id='
					+ this.taskId,
			method : "POST",
			preName : ['enterprise', 'person', 'sgLawsuitguaranteeProject',
					"businessType"],
			root : 'data',
			success : function(response, options) {
				var respText = response.responseText;
				var alarm_fields = Ext.util.JSON.decode(respText);
				var businessType = alarm_fields.data.sgLawsuitguaranteeProject.businessType;
				this
						.getCmpByName('projectMoney1')
						.setValue(Ext.util.Format
								.number(
										alarm_fields.data.sgLawsuitguaranteeProject.projectMoney,
										'0,000.00'))

				// 初始化币种 ,贷款用途开始
				var currencyType = alarm_fields.data.sgLawsuitguaranteeProject.currencyType;
				if (currencyType == null || currencyType == "") {
					var currencyTypeObj = this
							.getCmpByName('sgLawsuitguaranteeProject.currencyType');
					if (null != currencyTypeObj) {
						var st = currencyTypeObj.getStore();
						st.load({
							"callback" : function() {
								if (st.getCount() > 0) {
									var record = st.getAt(0);
									var v = record.data.itemId;
									currencyTypeObj.setValue(v);
								}
							}
						});
					}
				}
			}
		});
		this.add(this.outPanel);
		this.doLayout();
	}
});