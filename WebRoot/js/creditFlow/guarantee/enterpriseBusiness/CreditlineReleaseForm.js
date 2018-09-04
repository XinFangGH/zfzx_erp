//var upload = null;
//ReturnGuaranteeMoney=function(){
//	var tabs = Ext.getCmp('centerTabPanel');
//	if(upload == null) {
//		upload =new uploads({
//			projectId : 1,
//			isHiddenColumn : false,
//			isDisabledButton : false,
//			titleName :'银行解冻保证金凭证',
//			tableName :'gl_bank_guaranteemoney',
//			typeisfile :'typeisglbankguaranteemoney',
//			uploadsSize : 15
//			
//		});
//		tabs.add(upload);
//	}
//	tabs.setActiveTab(upload);
//}
CreditlineReleaseForm = Ext.extend(Ext.Panel, {
	formPanel : null,
	projectId : '$!projectId',
	oppositeType : '$!oppositeType',
	constructor : function(_cfg) {
		if (_cfg == null) {
			_cfg = {};
		}
		Ext.applyIf(this, _cfg);
		this.initComponents();
		CreditlineReleaseForm.superclass.constructor.call(this, {
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
			   __ctxPath + '/js/creditFlow/guarantee/enterpriseBusiness/guaranteeModel/CreditlineRelease.js'

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
	
		this.creditlineRelease = new creditlineRelease({
			projectId : this.projectId
		});
		this.formPanel = new Ext.Panel({
			modal : true,
			labelWidth : 100,
			buttonAlign : 'center',
			layout : 'form',
			border : false,
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
				title : '确认授信额度已释放',
				collapsible : true,
				autoHeight : true,
				items : [this.creditlineRelease]
			}]
		})

		this.add(this.formPanel);
		this.doLayout();
	}
})
