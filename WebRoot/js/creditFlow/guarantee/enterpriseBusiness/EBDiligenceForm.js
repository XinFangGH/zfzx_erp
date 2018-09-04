EBDiligenceForm = Ext.extend(Ext.Panel, {
	formPanel : null,
	projectId : '$!projectId',
	oppositeType : '$!oppositeType',
	constructor : function(_cfg) {
		if (_cfg == null) {
			_cfg = {};
		}
		Ext.applyIf(this, _cfg);
		this.initComponents();
		EBDiligenceForm.superclass.constructor.call(this, {
			iconCls : 'menu-profile-create',
			border : false
		});
	},
	initComponents : function() {

			var jsArr = [
			__ctxPath + '/js/creditFlow/guarantee/enterpriseBusiness/enterpriseBusinessUI.js',//项目基本信息
			 __ctxPath + '/js/commonFlow/ExtUD.Ext.js',
			  __ctxPath+'/js/commonFlow/EnterpriseShareequity.js',
			   __ctxPath+'/js/creditFlow/guarantee/enterpriseBusiness/bankInfoPanel.js'

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
				title : '计划收取费用',
				collapsible : true,
				autoHeight : true,
				items : []
			}, {
				xtype : 'fieldset',
				title : '担保准入原则',
				collapsible : true,
				autoHeight : true,
				items : []

			}, {
				xtype : 'fieldset',
				title : '反担保措施',
				collapsible : true,
				autoHeight : true,
				items : []
			}, {
				xtype : 'fieldset',
				collapsible : true,
				autoHeight : true,
				title : '贷款调查报告',
				layout : {
					type : 'hbox',
					pack : 'left'
				},
				defaults : {
					margins : '0 10 0 10'
				},
				items : [{
					xtype : 'button',
					text : '&nbsp;&nbsp;下载-贷款尽职调查报告模板',
					scope:this,
					handler : function(){
					
					}
					
				}, {
					xtype : 'button',
					text : '&nbsp;&nbsp;上传-贷款尽职调查报告'
//					handler : function() {
//						uploadReportForThisPanel('$!projectId',true)
//					}
				}]
			}, {
				xtype : 'fieldset',
				title : '调查说明',
				collapsible : true,
				autoHeight : true,
				items : [{
					xtype : "textarea",
					name : "comments",
					anchor : "100%",
					fieldLabel : "调查说明",
					blankText : "调查说明不能为空，请正确填写!",
					allowBlank : false
				}]
			}]
		})

		this.add(this.formPanel);
		this.doLayout();
	}
})
