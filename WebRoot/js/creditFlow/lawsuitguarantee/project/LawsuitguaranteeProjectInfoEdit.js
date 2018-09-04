/**
 * @author lisl
 * @description 诉讼担保项目信息
 * @extends Ext.Panel
 */
LawsuitguaranteeProjectInfoEdit = Ext.extend(Ext.Panel, {
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		this.initUIComponents();
		LawsuitguaranteeProjectInfoEdit.superclass.constructor.call(this, {
			id : 'LawsuitguaranteeProjectInfoEdit_' + this.record.data.projectId,// id属性值前缀,通过是否包含"Edit"来区分查看和编辑页面
			title : "项目编辑-" + this.record.data.projectName,
			iconCls : 'btn-edit',
			tbar : this.toolbar,
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
				__ctxPath + '/js/creditFlow/smallLoan/contract/SlContractView.js'
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
		this.taskId = this.record.data.taskId;
		this.projectStatus = this.record.data.projectStatus;
		this.oppositeType = this.record.data.oppositeType;
		// 项目基本信息授权
		if (isGranted('lgProjectInfo_edit_' + this.projectStatus)) {
			this.projectInfoGranted = {
				isDiligenceReadOnly : true
			};
		} else {
			this.projectInfoGranted = {
				isAllReadOnly : true
			};
		}
		this.projectInfo = new lawsuitBusinessProjectInfoPanel(this.projectInfoGranted);
		var customerTitle = "企业客户信息";
		this.perMain = "";
		// 客户基本信息授权
		if (isGranted('lgCustomerInfo_edit_' + this.projectStatus)) {
			this.personInfoGranted = {
				isPersonNameReadOnly : true
			};
			this.enterpriseInfoGranted = {
				projectId : this.projectId,
				bussinessType : 'BeNotFinancing',
				isEnterprisenameReadonly : true,
				isEnterpriseShortNameReadonly : true
			};
		} else {
			this.personInfoGranted = {
				isAllReadOnly : true
			};
			this.enterpriseInfoGranted = {
				projectId : this.projectId,
				businessType : 'BeNotFinancing',
				isAllReadOnly : true
			};
		}
		if (this.oppositeType == "person_customer") {
			this.perMain = new ExtUD.Ext.PeerPersonMainInfoPanel(this.personInfoGranted);
			customerTitle = "个人客户信息";
		} else if (this.oppositeType == "company_customer") {
			this.perMain = new ExtUD.Ext.PeerMainInfoPanel(this.enterpriseInfoGranted);
		};
		this.projectInfoFinance = new ProjectInfoLawsuitFinancePanel({
			isAllReadOnly : !isGranted('lgFinanceBaseInfo_edit_' + this.projectStatus),
			isHiddenPanel : !isGranted('lgFinanceBaseInfo_' + this.projectStatus)
		});

		this.GlActualToChargePanel = new SlActualToChargelawsuit({
			projId : this.projectId,
			businessType : 'BeNotFinancing',
			isHidden : !isGranted('lgActualToCharge_edit_' + this.projectStatus),
			isHiddenPanel : !isGranted('lgActualToCharge_' + this.projectStatus)
		});
		
		this.SlProcreditMaterialsView = new SlEnterPriseProcreditMaterialsView({
			projectId : this.projectId,
			businessType : 'BeNotFinancing',
			isHiddenAffrim : false,
			isHidden_materials : !isGranted('businessManagerOperation_l'
							+ this.projectStatus),
			isEditAffrim : isGranted('archiveConfirmOperation_l'
							+ this.projectStatus)
		});

		this.slContractView = new SlContractView({
			projId : this.projectId,
			businessType : "BeNotFinancing",
			HTLX : 'DBDQ',
			isHiddenAddBtn : !isGranted('lgContract_edit_' + this.projectStatus),
			isHiddenDelBtn : !isGranted('lgContract_edit_' + this.projectStatus),
			isHiddenEdiBtn : !isGranted('lgContract_edit_' + this.projectStatus),
			isHiddenAffrim : false,
			isSignHidden : false,
			isqsEdit : isGranted('lgContract_edit_' + this.projectStatus),
			isgdEdit : isGranted('lgContract_edit_' + this.projectStatus),
			isqsEdit : isGranted('lgContract_edit_' + this.projectStatus)
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
				title : customerTitle,
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
		})

		this.loadData({

			url : __ctxPath + '/creditFlow/getInfoCreditProject.do?taskId='
					+ this.projectId + '&type=BeNotFinancing'
					+ '&task_id=' + this.taskId,
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
		this.formPanel = new Ext.FormPanel();
		this.formPanel.add(this.outPanel);
		this.add(this.formPanel);
		this.doLayout();
	},
	saveAllDatas : function() {
        var oppType=this.oppositeType;
        var vDates="";
        if(this.oppositeType=="company_customer")
        {
           var eg=this.perMain.getCmpByName('gudong_store').get(0).get(1);
           vDates=getGridDate(eg);
           if(vDates!=""){
              var arrStr=vDates.split("@");
			  for(var i=0;i<arrStr.length;i++){
				  var str=arrStr[i];
				  var object = Ext.util.JSON.decode(str)
				 if(object.personid==""){
					 Ext.ux.Toast.msg('操作信息','股东名称不能为空，请选择股东名称');
					 return;
				 }
				  if(object.shareholdertype==""){
						 Ext.ux.Toast.msg('操作信息','股东类别不能为空，请选择股东类别');
						 return;
					 }
			  }
		  }
        
        }
	      var slActualToChargeJsonData=this.GlActualToChargePanel.getGridDate();
		  var slActualToCharge=this.GlActualToChargePanel;
		   
		this.formPanel.getForm().submit({
		    clientValidation: false, 
			url : __ctxPath + '/creditFlow/lawsuitguarantee/updateSgLawsuitguaranteeProject.do',
			params : {
				"gudongInfo" : vDates,
				"slActualToChargeJsonData":slActualToChargeJsonData
			},
			method : 'post',
			waitMsg : '数据正在提交，请稍后...',
			success : function(fp, action) {
				 slActualToCharge.savereload();
				 Ext.ux.Toast.msg('操作信息', '保存信息成功!');
				 if(oppType=="company_customer"){
				   eg.getStore().reload();
				}
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
	}
});