/**
 * @author lisl
 * @class ProjectFinished
 * @description 项目结项
 * @extends Ext.Panel
 */
ProjectFinished = Ext.extend(Ext.Panel, {
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		this.initUIComponents();
		ProjectFinished.superclass.constructor.call(this, {
			id : "ProjectFinishedPanel_" + this.record.data.projectId,
			title : "项目结项-" + this.record.data.projectName,
			iconCls : 'btn-ok',
			border : false,
			tbar : this.toolbar,
			items : []
		});
	},	
	initUIComponents : function() {
		var jsArr = [
                     __ctxPath + '/js/commonFlow/ExtUD.Ext.js',// 客户信息 项目基本信息
                     __ctxPath + '/js/selector/UserDialog.js',
                     __ctxPath + '/js/creditFlow/common/EnterpriseShareequity.js',// 股东信息
                     __ctxPath + '/js/creditFlow/guarantee/enterpriseBusiness/enterpriseBusinessUI.js',// 项目基本信息
                     __ctxPath + '/js/creditFlow/finance/SlActualToCharge.js',// 实际收费项目
                     __ctxPath + '/js/creditFlow/smallLoan/contract/SeeThirdContractWindow.js',// 查看担保措施合同详情
                     __ctxPath + '/js/creditFlow/smallLoan/contract/OperateThirdContractWindow.js',// 编辑反担保措施合同
                     __ctxPath + '/js/creditFlow/guarantee/contract/LetterAndBookView.js',// 担保意向书、对外担保承诺函
                     __ctxPath + '/js/creditFlow/finance/SlActualToChargeVM.js',//实际收费项目
                     __ctxPath + '/js/creditFlow/finance/SlActualToChargePremiumVM.js',//保费列表
                     __ctxPath + '/js/creditFlow/mortgage/DZYMortgageView.js',// 抵质押物相关
                     __ctxPath + '/js/creditFlow/mortgage/BaozMortgageView.js',// 抵质押物相关                                
                     __ctxPath + '/publicmodel/uploads/js/uploads.js',// 获取担保责任解除函
                     __ctxPath + '/js/creditFlow/guarantee/enterpriseBusiness/guaranteeModel/BankGuaranteeMoneyrelease.js'//保证金支付情况
			];
		$ImportSimpleJs(jsArr, this.constructPanel, this);
		this.toolbar = new Ext.Toolbar({
			items : [{
				iconCls : 'btn-ok',
				text : '提交',
				scope : this,
				handler : function() {
//					this.saveAllDatas(this.outPanel);
					
					var projectId = this.record.data.projectId;
					Ext.Msg.confirm('信息确认',"确定项目相关财务业务处理完毕，办理项目结项吗?", function(btn) {
						if (btn == 'yes') {
							Ext.Ajax.request({
								url : __ctxPath + '/creditFlow/guarantee/EnterpriseBusiness/completeGLGuaranteeloanZmNormalFlowProject.do',
								params : {
									projectId : projectId
								},
								method : 'post',
								scope : this,
								success : function(resp, op) {
									var res = Ext.util.JSON.decode(resp.responseText);
									if (res.success) {
										Ext.ux.Toast.msg('信息提示', '项目结项成功！');
										Ext.getCmp('centerTabPanel').remove("ProjectFinishedForm_" + projectId);
										ZW.refreshTaskPanelView();
									} else {
										Ext.ux.Toast.msg('信息提示', '出错，请联系管理员！');
									}
								},
								failure : function() {
									Ext.ux.Toast.msg('信息提示', '出错，请联系管理员！');
								}
							});
						}
					})
				}
			}]
		})
	},// 初始化组件
	constructPanel : function() {		
		this.projectId = this.record.data.projectId;
		this.taskId = this.record.data.taskId;
		this.runId = this.record.data.runId;
		this.businessType = this.record.data.businessType;
		this.oppositeType = this.record.data.oppositeType;
		this.projectInfo = new ExtUD.Ext.ZmNormalBasicProjectInfo({isAllReadOnly : true});
		this.projectInfoFinance = new ProjectInfoGuaranteeFinancePanel({isAllReadOnly : true,isHiddenRepaymentDate:false});
		this.bankInfo = new ExtUD.Ext.BankInfoPanel({isAllReadOnly : true});
		this.slActualToChargePremiumVM=new SlActualToChargePremiumVM({
			projId : this.projectId,
			isHiddenBtn : true,
			isHiddenDZBtn : true,
			businessType :'Guarantee'      
		});
		this.glActualToChargePanelVM=new SlActualToChargeVM({
			projId : this.projectId,
			isHidden : true,
			isHiddenDuiZhangBtn : true,
			businessType : this.businessType
		});
		this.slactual = new SlActualToCharge({
		     projId : this.projectId,
		     businessType :'Guarantee'  
		 });
		this.uploads = new uploads({
			projectId : this.projectId,
			isHidden : false,
			businessType :this.businessType,
			isNotOnlyFile : false,
			isHiddenColumn : false,
			isDisabledButton : false,
			setname :'还款凭证',
			titleName :'还款凭证',
			tableName :'typeisdbzrjchsmj',
			isHiddenOnlineButton :true,
			isDisabledOnlineButton :true
		});
		this.BankGuaranteeMoneyrelease=new BankGuaranteeMoneyrelease({
			projectId:this.projectId,
			isReadOnlyFrozen:true,
			isReadOnlyRelease:false,
			frame1 :false,
			isallowBlank:true    
		});
		var title="企业客户信息";
        this.perMain = "";
	    if(this.oppositeType =="person_customer") 
	    {
		    this.perMain = new ExtUD.Ext.PeerPersonMainInfoPanel({
		    	isEditPerson : true,
		    	isHidden:true,
		    	isGlHidden:true
		    });
		    title="个人客户信息";
	    } 
	    else if(this.oppositeType =="company_customer")
	    {
		    this.perMain = new ExtUD.Ext.PeerMainInfoPanel({
			projectId : this.projectId,
		    bussinessType:this.businessType,
		    isHidden:true,
			isEnterpriseShortNameReadonly : true,
			isEditEnterprise : false,
			isGudongDiseditable : true
		    });
	    };
        this.outPanel = new Ext.Panel({
                modal : true,
                frame : true,
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
                        xtype : 'fieldset',
                        title : '项目基本信息',
                        name : 'projectInfo',
                        collapsible : true,
                        autoHeight : true,
                        labelAlign : 'right',
                        items : [this.projectInfo]
                }, {
                        title : title,
                        items : [this.perMain]
                }, {
                        xtype : 'fieldset',
                        title : '对接银行信息',
                        collapsible : true,
                        autoHeight : true,
                        items : [this.bankInfo]
                }, {
                        xtype : 'fieldset',
                        title : '资金款项信息',
                        bodyStyle : 'padding-left:0px',
                        collapsible : true,
                        labelAlign : 'right',
                        autoHeight : true,
						allowBlank : false,
                        items : [
                                this.projectInfoFinance,
                                this.slActualToChargePremiumVM,
                                this.glActualToChargePanelVM
                        ]
                }, {
    				xtype : 'fieldset',
    				collapsible : true,
    				autoHeight : true,
    				title : '反担保措施',
    				items : [
    					new DZYMortgageView({
    						projectId : this.projectId,
    						titleText : '抵质押担保',
    						businessType : "Guarantee",    						
    						isHiddenAddContractBtn:true,
    						isHiddenDelContractBtn:true,
    						isHiddenEdiContractBtn:true,
    						isHiddenRelieve:true,
    						isgdHidden:true,
    						isHiddenAddBtn:true,
    						isHiddenDelBtn:true,
    						isHiddenEdiBtn:true,
    						isfwEdit:false,
    						isSeeContractHidden:true,
    						isKS:true,
    						isSignHidden:false,
    						isblEdit:false,
    						isblHidden:true,
    						isRelieveEdit : true
    					}),
    					new BaozMortgageView({
    						projectId : this.projectId,
    						titleText : '保证担保',
    						businessType : "Guarantee",
    						isHiddenAddContractBtn:true,
    						isHiddenDelContractBtn:true,
    						isHiddenEdiContractBtn:true,
    						isHiddenRelieve:true,
    						isgdHidden:true,
    						isHiddenAddBtn:true,
    						isHiddenDelBtn:true,
    						isHiddenEdiBtn:true,
    						isfwEdit:false,
    						isSeeContractHidden:true,
    						isKS:true,
    						isSignHidden:false,
    						isblEdit:false,
    						isblHidden:true
    					})
    				]
    			},{
                    xtype : 'fieldset',
                    title : '保证金支付情况',
                    collapsible : true,
                    autoHeight : true,
                    items : [this.BankGuaranteeMoneyrelease]
                }, {
                    xtype : 'fieldset',
                    title : '还款凭证',
                    collapsible : true,
                    autoHeight : true,
                    name:'zeren',
                    items : [this.uploads]
                },{
					name : 'supervisionJsonData',
					xtype : 'hidden'
				}]
        });
		
		this.loadData({
//			 url : __ctxPath + '/creditFlow/getInfoCreditProject.do?taskId='+this.projectId+'&type='+this.businessType+'&task_id='+this.taskId,
			 url : __ctxPath + '/creditFlow/guarantee/EnterpriseBusiness/getInfoGLGuaranteeloanProject.do?glProjectId='+this.projectId+'&glTaskId='+this.taskId,
             method : "POST",
             preName : ['enterprise', 'person', 'gLGuaranteeloanProject','customerBankRelationPerson', "businessType"],
             root : 'data',
			 success : function(response, options) {
				var respText = response.responseText;
				var alarm_fields = Ext.util.JSON.decode(respText);
				this.getCmpByName('projectMoney1').setValue(Ext.util.Format.number(alarm_fields.data.gLGuaranteeloanProject.projectMoney,'0,000.00'))
				//资金款项——向银行支付的保证金总额（有值）
				if (null != alarm_fields.data.gLGuaranteeloanProject.earnestmoney&& "" != alarm_fields.data.gLGuaranteeloanProject.earnestmoney) {
					this.getCmpByName('earnestmoney1').setValue(Ext.util.Format.number(alarm_fields.data.gLGuaranteeloanProject.earnestmoney,'0,000.00'))
				}
				if(alarm_fields.data.comments){
					this.ownerCt.ownerCt.getCmpByName('comments').setValue();
				}
				var projectMoney = alarm_fields.data.gLGuaranteeloanProject.projectMoney;
			}
		});
		this.add(this.outPanel);
		this.doLayout();
	},
	// 初始化UI结束
	saveAllDatas : function(outPanel) {
		var repaymentDate = this.getCmpByName('gLGuaranteeloanProject.repaymentDate').getValue();
		var projectId = this.projectId;
		var runId = this.runId;
		var gridPanel = this.gridPanel;
		if(repaymentDate==null||repaymentDate==''){
			Ext.ux.Toast.msg('操作信息','还款日期不能为空！');
			return;
		}
		Ext.Msg.confirm('信息确认', '提交会将此项目归为已完成项目,您确认要提交吗？', function(btn) {
			if (btn == 'yes') {
				var zeren = outPanel.getCmpByName("zeren");
				var store=zeren.get(0).get(0).getStore();
				if(store.getCount()<=0){
					Ext.ux.Toast.msg('操作信息','必须上传还款凭证项目才能正常结项');
					return;
				}
				Ext.Ajax.request({
					url : __ctxPath + '/creditFlow/guarantee/EnterpriseBusiness/projectFinishedGLGuaranteeloanZmNormalFlowProject.do',
					method : 'post',
					params : {
						projectId : projectId,
						repaymentDate : repaymentDate
					},
					success : function(response,options) {
						var res = Ext.util.JSON.decode(response.responseText);
						if(res.success){
							Ext.ux.Toast.msg('操作信息','项目结项成功！');
							Ext.getCmp('centerTabPanel').remove('ProjectFinishedPanel_' + projectId);
							ZW.refreshTaskPanelView();
							gridPanel.getStore().reload();
						}else{
							Ext.ux.Toast.msg('操作信息','信息保存出错，请联系管理员！！');
						}
					}
				});
			}
		})
	}
});