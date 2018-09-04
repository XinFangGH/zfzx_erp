NewProjectFormEnter = Ext.extend(Ext.Panel, {
	formPanel : null,
	topbar : null,
	constructor : function(_cfg) {
		if (_cfg == null) {
			_cfg = {};
		}
		Ext.applyIf(this, _cfg);
		this.initComponents();
		NewProjectFormEnter.superclass.constructor.call(this, {
			id : 'NewProjectFormEnter',
			iconCls : 'btn-tree-team1',
			layout : 'anchor',
		    defaults:{
            	anchor : '98%'
            },
			items : this.formPanel,
			modal : true,
			border : false,
			frame : true,
			tbar : this.topbar,
			maximizable : true,
			title : '项目申请',
			buttonAlign : 'center',
			buttons : this.buttons
		});
	},
	initComponents : function() {

		this.formPanel = new Ext.FormPanel({
			border : false,
			monitorValid : true,
			url :  __ctxPath + "/flow/startCreditFlowExterPriseProjectProcessActivity.do",
			autoScroll : true,
			id : "createNewSLFunctionForm",
			bodyStyle : 'padding-left:0px;padding-top:14px',
			tbar : this.topbar,
			anchor : "100%",
			labelAlign : "left",
			items : [{
				xtype : 'hidden',
				name : 'preHandler',
				value : 'creditProjectService.startCreditFlowExterPriseProject'
			}, {
				xtype : 'fieldset',
				title : '项目基本信息',
				bodyStyle : 'padding-left:0px;padding-top:4px',
				collapsible : true,
				labelAlign : 'right',
				autoHeight : true,
				items : [{
					layout:'column',
					items:[{
						layout:'form',
						columnWidth:.4,
						items:[{
							xtype : "dicIndepCombo",
							labelWidth : 90,
							fieldLabel : "我方类型",
							emptyText : "请选择",
							nodeKey : 'ourmainType',
							allowBlank : false,
							anchor : "100%",
							editable : false,
							hiddenName : "mineType",
							scope : this,
							listeners : {
								change : function(combox, record, index) {
									var comboxValue = combox.getValue();
									var url = '';
									var store = null;
									var combo = Ext.getCmp('dd_Degree');
									combo.clearValue();
									if (comboxValue == "company_ourmain")// 企业
									{
										url = __ctxPath + '/creditFlow/ourmain/listSlCompanyMain.do';
										store = new Ext.data.Store({
											proxy : new Ext.data.HttpProxy({
												url : url
											}),
											reader : new Ext.data.JsonReader({
												root : 'result'
											}, [{
												name : 'itemName',
												mapping : 'corName'
											}, {
												name : 'itemValue',
												mapping : 'companyMainId'
											}])
										})
									} else if (comboxValue == "person_ourmain") // 个人
									{
										url = __ctxPath + '/creditFlow/ourmain/listSlPersonMain.do';
										store = new Ext.data.Store({
											proxy : new Ext.data.HttpProxy({
												url : url
											}),
											reader : new Ext.data.JsonReader({
												root : 'result'
											}, [{
												name : 'itemName',
												mapping : 'name'
											}, {
												name : 'itemValue',
												mapping : 'personMainId'
											}])
										})
									}
									combo.store = store;
									combo.valueField = "itemValue";
									store.load();
									if (combo.view) { // 刷新视图,避免视图值与实际值不相符
										combo.view.setStore(combo.store);
									}
									var obj_n = this.ownerCt.ownerCt.ownerCt.ownerCt.ownerCt;
									ressetProjuect(obj_n);
								},
								afterrender : function(combox) {
									combox.clearInvalid();
								}
	
							}
						}]
					},{
						layout:"form",
						columnWidth:.6,
						items:[{
							xtype : "combo",
							id : "dd_Degree",
							anchor : "100%",
							fieldLabel : "我方主体",
							displayField : 'itemName',
							valueField　: 'itemValue',
							store : new Ext.data.SimpleStore({
									fields : ['displayText', 'value'],
									data : [['地区', 1]]
							}),
							emptyText : "请选择",
							allowBlank : false,
							hiddenName : 'mineId',
							typeAhead : true,
							mode : 'local',
							editable : false,
							selectOnFocus : true,
							triggerAction : 'all',
							blankText : "我方主体不能为空，请正确填写!",
							listeners : {
								afterrender : function(combox){
									combox.clearInvalid();
								},
								change : function(combox) {
									var obj_n = combox.ownerCt.ownerCt.ownerCt.ownerCt.ownerCt;
									//ressetProjuect(obj_n);
								}
							}
	
						}]
					}]
				}]
			},{
				xtype : 'fieldset',
				title : '项目基本信息',
				bodyStyle : 'padding-left:0px;padding-top:4px',
				collapsible : true,
				labelAlign : 'right',
				autoHeight : true,
				items:[new ExtUD.Ext.PeerMainInfoPanel({
								isHiddenCustomerDetailBtn : true,
								isAllReadOnly : false,
								isEditEnterprise:true,
								isEnterpriseNameReadOnly : false,
								isHidden : true
				 })]
			},{
				xtype : 'fieldset',
				title : '项目名称',
				collapsible : true,
				bodyStyle : 'padding-left:0px;',
				labelAlign : 'right',
				layout : "column",
				defaults : {
					anchor : '100%',
					columnWidth : 1
				},
				items : [{
					columnWidth : .85, 
					layout : "form", 
					labelWidth : 85,
					items : [new ProjectNameTextField()]
				}, {
					columnWidth : .15, 
					layout : "form",
					bodyStyle : 'padding-left:17px;',
					items : [new ExtUD.Ext.ProjectBuildButton({hiddenMineType:isGroup=="true"})]
				}]
			}]
		});
		this.topbar = new Ext.Toolbar({
			height : 30,
			bodyStyle : 'text-align:left',
			defaultType : 'button',
			items : [{
				text : '启动项目',
				iconCls : 'btn-ok1',
				handler : this.save.createCallback(this.formPanel, this)
			},'-', {
				text : '重置',
				iconCls : 'btn-reset',
				handler : this.reset.createCallback(this.formPanel)
			},'-', {
				text : '取消',
				iconCls : 'btn-cancel',
				handler : this.cancel.createCallback(this)
			}]
		})
	},
	/**
	 * 重置
	 * 
	 * @param {}
	 *            formPanel
	 */
	reset : function(formPanel) {
		formPanel.getForm().reset();
		formPanel.getCmpByName('mineType').setValue('company_ourmain')
		 Ext.Ajax.request({
	          url : __ctxPath + '/creditFlow/getCurrentUserInfoCreditProject.do',
	           method : 'POST',
	          success : function(response,request) {
					
					var object=Ext.util.JSON.decode(response.responseText);
				   	formPanel.getCmpByName('mineId').setValue(object.companyId);
				   	formPanel.getCmpByName('mineName').setValue(object.companyName)
	          }
         });  
	},
	/**
	 * 取消
	 * 
	 * @param {}
	 *            window
	 */
	cancel : function(formPanel) {
		var tabs = Ext.getCmp('centerTabPanel');
		if (formPanel != null) {
			tabs.remove('NewProjectForm');
		}
	},
	/**
	 * 保存记录
	 */
	save : function(formPanel, window) {
		if (formPanel.getForm().isValid()) {
			var vDates = "";
			var businessType = "SmallLoan"//Ext.getCmp("NewProjectForm").getCmpByName('businessType').getValue();
			if(businessType!=null&&businessType!=1067){
				var mType = "company_customer";
				if(mType=="company_customer"){     
					var edGrid=formPanel.getCmpByName('gudong_store').get(0).get(1);
					vDates=getGridDate(edGrid);
				
				}
			}
			Ext.MessageBox.wait('正在提交数据, 请稍侯 ...', '提示');
			formPanel.getForm().submit({
				method : 'POST',
				params : {
					"gudongInfo" : vDates
				}, // 传递的参数
				success : function(fp, action) {
					Ext.MessageBox.hide();
					var data = action.result.data;
					var projectNum = data.projectNumber;
					var projectName = data.projectName;
					var businessType = data.businessType;
					var activityName = data.activityName;
					var operationType = data.operationType;
					var flowType = data.flowType;
					var projectId=data.projectId
					var jieName="";
					if(businessType=="SmallLoan"){    
						if(operationType=="SmallLoanBusiness"){
					        if(flowType=="smallHistoryRecordsFlow"){
					        	jieName="补录项目信息"
					        }else{
					      		 jieName="尽职调查";
					        }
					     }else if(operationType=="MicroLoanBusiness"){
					     	if(flowType=="mcroHistoryRecordsFlow"){
					        	jieName="补录项目信息"
					        }else{
					      		 jieName="尽职调查";
					        }
					     }
					}else if(businessType=="Financing"){
						if(operationType=="FinancingBusiness"){
							if(flowType=="FinancingBusinessFlow"){
								jieName="融资顾问录入业务";
							}
						}
					}else if(businessType=="Guarantee"){
						if(operationType=="CompanyBusiness"){
						     if(flowType=="GuaranteeNormalFlow"){
						          jieName="尽职调查";  //(定额贷款项目)
						     }else if(flowType=="zmNormalFlow"){
						          jieName="初步审核";   //定额贷款项目特批快速流程
						          
						          /**
						           * 异步生成担保意向书 add by chencc
						           * */
						          Ext.Ajax.request({
										url : __ctxPath+'/contract/createAssureIntentBookProduceHelper.do',
										method : 'GET',
										success : function(response, request) {
										},
										failure : function(response) {
										},
										params : {
											projId : data.projectId,
											businessType : businessType,
											mark : 'simulateEnterpriseBook',
											htType : 'simulateEnterpriseBook',
											categoryId :0,
											contractId :0
										}
									});
						     }else if(flowType==6587){
						         jieName="暂无信息";  //最高额贷款项目
						     }else if(flowType==6588){
						         jieName="暂无信息";  //最高额贷款特批快速流程
						     }
						}else if(operationType=="PersonalConsumeBusiness"){
							 if(flowType==6589){     //定额贷款项目
						         jieName="暂无信息";
						     }else if(flowType==6590){
						         jieName="暂无信息";  //定额贷款项目的特批快速流程
						     }
						}
					}
					Ext.Msg.alert('状态', '项目：' + projectName+ ' 启动成功!<br /><br />流转到下一个节点:'+activityName+'!');
					AppUtil.removeTab('NewProjectFormEnter');
					ZW.refreshTaskPanelView();
					var obj=document.getElementById("taskCount");//手动刷新右上角待办任务数目
					ZW.refreshTaskCount(obj);
				},
				failure : function(fp, action) {
					Ext.MessageBox.hide();
					alert('启动项目失败,请联系管理员!');
				}
			});
		}
	}
})
function ressetProjuect(){
	   var obj1=Ext.getCmp('createNewSLFunctionForm');
       var projectNameObject=obj1.getCmpByName('projectName');
       if(projectNameObject.getValue().trim()!=""){
           projectNameObject.setValue("");
           projectNameObject.clearInvalid(); 
       }
}

ProjectNameTextField = Ext.extend(Ext.form.TextField, {
	fieldLabel : "项目名称",
	name : "projectName",
	anchor : "100%",
	hiddenMineType:false,
	blankText : "项目名称不能为空，请正确填写!",
	allowBlank : false,
	constructor : function(_cfg) {
		if (_cfg == null) {
			_cfg = {};
		}
		if(_cfg.hiddenMineType){
			this.hiddenMineType = _cfg.hiddenMineType;		
		}
		Ext.applyIf(this, _cfg);
		ProjectNameTextField.superclass.constructor.call(this, {
			listeners : {
				render : function(p) {
					var hiddenMineType = this.hiddenMineType;
					p.getEl().on('click', function(p) {
					
						if (Ext.getCmp("NewProjectFormEnter").getCmpByName('projectName').getValue() != "") {
							return;
						}
						if(Ext.getCmp("NewProjectFormEnter").getCmpByName('mineType').getValue()==""||Ext.getCmp("NewProjectFormEnter").getCmpByName('mineType').getValue()=="请选择"){
							return;
						}
						if(Ext.getCmp("NewProjectFormEnter").getCmpByName('enterprise.enterprisename').getValue()==""||Ext.getCmp("NewProjectFormEnter").getCmpByName('enterprise.enterprisename').getValue()=="请选择"){
							return;
						}
						Ext.Ajax.request({
							url : __ctxPath + '/creditFlow/getEnterProjectNameCreditProject.do',
							params : {
								"cusName" : Ext.getCmp("NewProjectFormEnter").getCmpByName('enterprise.enterprisename').getValue()
							},
							method : 'post',
							success : function(response, options) {
								var obj = Ext.decode(response.responseText);
								Ext.getCmp("NewProjectFormEnter").getCmpByName('projectName').setValue(obj.data[0].dd);
							}
						});
					})
				}
			}
		})
	}
});