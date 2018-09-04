
RemoveAntiguarantStep = Ext.extend(Ext.Panel, {
	formPanel : null,
	projectId : '$!projectId',
	oppositeType : '$!oppositeType',
	constructor : function(_cfg) {
		if (_cfg == null) {
			_cfg = {};
		}
		Ext.applyIf(this, _cfg);
		this.initComponents();
		RemoveAntiguarantStep.superclass.constructor.call(this, {
			iconCls : 'menu-profile-create',
			border : false
		});
	},
	initComponents : function() {

			var jsArr = [
			__ctxPath + '/js/creditFlow/guarantee/enterpriseBusiness/enterpriseBusinessUI.js',//项目基本信息
			 __ctxPath + '/js/commonFlow/ExtUD.Ext.js',
			  __ctxPath+'/js/commonFlow/EnterpriseShareequity.js',
			   __ctxPath+'/js/creditFlow/guarantee/enterpriseBusiness/bankInfoPanel.js',
			    __ctxPath+'/js/creditFlow/guarantee/enterpriseBusiness/guaranteeModel/RemoveOpponentMortgageCheck.js'
			 

		];
		$ImportSimpleJs(jsArr, this.constructPanel, this);
	},
	constructPanel : function() {
		this.perMain = "";
		this.projectInfo = new enterpriseBusinessProjectInfoPanel({
			isDiligenceReadOnly : true
		});
		var title="企业客户基本信息";
		if (this.oppositeType == 307) {
			    this.perMain = new ExtUD.Ext.PeerPersonMainInfoPanel({
				isPersonNameReadOnly : true
			});
			title="个人客户基本信息";
		} else {
			this.perMain = new ExtUD.Ext.PeerMainInfoPanel({
				projectId : this.projectId,
				isEnterprisenameReadonly : true,
				isEnterpriseShortNameReadonly : true
			});
		}
		this.bankInfoPanel=new bankInfoPanel()
		this.RemoveOpponentMortgageCheck = new RemoveOpponentMortgageCheck({
					projectId : 389,
					businessType : 6584,
					isHiddenAffrim : true
				});
	

		this.formPanel = new Ext.Panel({
			modal : true,
			labelWidth : 100,
			buttonAlign : 'center',
			layout : 'form',
			border : false,
			frame:true,
			defaults : {
				anchor : '100%',
				xtype : 'fieldset',
				labelAlign : 'left',
				collapsible : true,
				autoHeight : true
			},
			items : [{
				xtype : 'hidden',
				name : 'preHandler',
				value : 'slSmallloanProjectService.updateDiligenceCreditFlowProject'
			}, {
				xtype : 'hidden',
				name : 'gudongInfo'
			}, {
				xtype : 'fieldset',
				title :title,
				collapsible : true,
				labelAlign : 'right',
				autoHeight : true,
				items : [this.perMain ]
			}, {
				xtype : 'fieldset',
				title : '项目基本信息',
				collapsible : true,
				autoHeight : true,
				labelAlign : 'right',
				bodyStyle : 'padding-left:8px',
				items : [this.projectInfo]
			}, {
				xtype : 'fieldset',
				title : '银行信息',
				collapsible : true,
				autoHeight : true,
				items : [this.bankInfoPanel]
			}, {
				xtype : 'fieldset',
				title : '解除反担保措施',
				collapsible : true,
				autoHeight : true,
				items : [this.RemoveOpponentMortgageCheck]
			}, {
				title : '意见与说明',
				layout : "form",
				items : [{
					xtype : "textarea",
					fieldLabel : "说明",
					name : "comments",
					anchor : "100%",
					blankText : "说明不能为空，请正确填写!",
					allowBlank : false
				}]
							}]
		})
         
		this.add(this.formPanel);
		this.doLayout();
	}
	,saveBusDatas : function(formPanel) {
		
		Ext.ux.Toast.msg('操作信息', '保存信息成功!');
	}
	
})
