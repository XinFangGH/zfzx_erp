function ressetProjuect(){
	   /*var obj1=Ext.getCmp('createNewSLFunctionForm');
       var projectNameObject=obj1.getCmpByName('projectName');
       if(projectNameObject.getValue().trim()!=""){
           projectNameObject.setValue("");
           projectNameObject.clearInvalid(); 
       }*/
}
NewProjectFormEnterPrise = Ext.extend(Ext.Window, {
	formPanel : null,
	constructor : function(_cfg) {
		if (_cfg == null) {
			_cfg = {};
		}
		Ext.applyIf(this, _cfg);
		this.initComponents();
		NewProjectFormEnterPrise.superclass.constructor.call(this, {
			id : 'NewProjectFormEnterPrise',
			iconCls : 'btn-tree-team1',
			layout : 'form',
			items : this.formPanel,
			modal : true,
			frame:true,
			autoScroll:true,
			autoHeight:true,
			width : 1000,
			maximizable : true,
			frame:true,
			resizable:false,
			title : '企业项目申请',
			buttonAlign : 'center',
			buttons :[{
				text:'启动项目',
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
		});
	},
	initComponents : function() {
		this.formPanel = new Ext.form.FormPanel({
			id : "createNewSLFunctionForm",
			frame:true,
			border : false,
			monitorValid : true,
			autoScroll : true,
			anchor : "100%",
			url : __ctxPath + "/flow/startCreditFlowExterPriseProjectProcessActivity.do",
			bodyStyle : 'padding-left:0px;padding-top:14px',
			items:[{
				xtype : 'fieldset',
				title : '相关产品设置',
				bodyStyle : 'padding-left:0px;',
				collapsible : true,
				name:'bpProductParameter',
				labelAlign : 'right',
				autoHeight : true,
				items : [{
				     	labelWidth : 85,
						xtype : "combo",
						mode : 'local',
						anchor : '100%',
						disabled : this.product == null
								? false
								: !this.product,
						allowBlank : false,
						readOnly : this.isCPLX,
						triggerAction : 'all',
						hiddenName : "slSmallloanProject.productId",
						displayField : 'productName',
						valueField : 'id',
						fieldLabel : "产品类型",
						editable : false,
						store : new Ext.data.JsonStore({
							url : __ctxPath
									+ "/system/listBpProductParameter.do?Q_operationType_S_EQ=SmallLoan_SmallLoanBusiness",
							totalProperty : 'totalCounts',
							autoLoad : true,
							root : 'result',
							fields : [{
										name : 'id'
									}, {
										name : 'productName'
									}]													
						}),
						listeners : {
							scope : this,
							afterrender : function(combox) {
								var st = combox.getStore();
								st.on("load", function() {
											combox
													.setValue(combox
															.getValue());
											combox
													.clearInvalid();
										})
										
							  }
						}
				}]
			},{
				xtype : 'fieldset',
				title : '企业客户基本信息',
				bodyStyle : 'padding-left:0px;',
				collapsible : true,
				name:'customerInfo',
				labelAlign : 'right',
				autoHeight : true,
				items : [
					 new ExtUD.Ext.PeerMainInfoPanel({
					 	isHiddenCustomerDetailBtn : true,
					 	isAllReadOnly : false,
					 	isEditEnterprise:true,
					 	isEnterpriseNameReadOnly : false,
					 	isHidden : true
					 })]
			}/*,{
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
					items : [new ExtUD.Ext.ProjectNameTextField({hiddenMineType:isGroup=="true"})]
				}, {
					columnWidth : .15, 
					layout : "form",
					bodyStyle : 'padding-left:17px;',
					items : [new ExtUD.Ext.ProjectBuildButton({hiddenMineType:isGroup=="true"})]
				}]
			}*/]
		});
	},
	/**
	 * 启动项目
	 */
	save : function(formPanel, window) {
/*			if (formPanel.getForm().isValid()) {
				if(Ext.getCmp("NewProjectFormSSZZ").getCmpByName('person.cardtype').getValue()==309){
					if(validateIdCard(Ext.getCmp("NewProjectFormSSZZ").getCmpByName('person.cardnumber').getValue())==1){
						Ext.Msg.alert('身份证号码验证','证件号码不正确,请仔细核对')
						return;
					}else if(validateIdCard(Ext.getCmp("NewProjectFormSSZZ").getCmpByName('person.cardnumber').getValue())==2){
						Ext.Msg.alert('身份证号码验证','证件号码地区不正确,请仔细核对')
						return;
					}else if(validateIdCard(Ext.getCmp("NewProjectFormSSZZ").getCmpByName('person.cardnumber').getValue())==3){
						Ext.Msg.alert('身份证号码验证','证件号码生日日期不正确,请仔细核对')
						return;
					}
				}  
			}*/
			Ext.MessageBox.wait('正在提交数据, 请稍侯 ...', '提示');
			formPanel.getForm().submit({
				method : 'POST',
				success : function(fp, action) {
					Ext.MessageBox.hide();
					var data = action.result.data;
					var tabs = Ext.getCmp('centerTabPanel');
					var contentPanel = App.getContentPanel();
					var productId=Ext.getCmp("createNewSLFunctionForm").getCmpByName('slSmallloanProject.productId').getValue();
					var formView = contentPanel.getItem('ProcessNextForm' + data.taskId);
					if (formView == null) {
						formView = new ProcessNextForm({
							taskId : data.taskId,
							activityName : data.activityName,
							projectName : data.projectName,
							productId: productId,
							agentTask : true
						});
						contentPanel.add(formView);
					}
					contentPanel.activate(formView);
					ZW.refreshTaskPanelView();
					var obj=document.getElementById("taskCount");//手动刷新右上角待办任务数目
					ZW.refreshTaskCount(obj);
					window.close();
				},
				failure : function(fp, action) {
					Ext.MessageBox.hide();
					alert('启动项目失败,请联系管理员!');
				}
			});
	},
	reset : function(formPanel) {
		formPanel.getForm().reset();
	},
	cancel : function(formPanel) {
		var tabs = Ext.getCmp('centerTabPanel');
		if (formPanel != null) {
			tabs.remove('NewProjectFormEnterPrise');
		}
	}
})
