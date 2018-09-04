NewProjectFormHistory = Ext.extend(Ext.Window, {
	formPanel : null,
	constructor : function(_cfg) {
		if (_cfg == null) {
			_cfg = {};
		}
		Ext.applyIf(this, _cfg);
		this.initComponents();
		NewProjectFormHistory.superclass.constructor.call(this, {
			id : 'NewProjectFormHistory',
			iconCls : 'btn-tree-team1',
			layout : 'form',
			items : this.formPanel,
			modal : true,
			frame:true,
			autoScroll:true,
			autoHeight:true,
			width : 700,
			maximizable : true,
			frame:true,
			resizable:false,
			title : '个人项目申请',
			buttonAlign : 'center',
			buttons :[{
				text:'启动项目',
				iconCls : 'btn-ok1',
				handler : this.save.createCallback(this.formPanel, this)
			}]
		});
	},
	initComponents : function() {
		this.formPanel = new Ext.form.FormPanel({
			id : "createNewProjectFormHistory",
			frame:true,
			border : false,
			monitorValid : true,
			autoScroll : true,
			anchor : "100%",
			url : __ctxPath + "/flow/startCreditLoanFlowProcessActivity.do",
			bodyStyle : 'padding-left:0px;padding-top:14px',
			items:[new ExtUD.Ext.PersonMainInfoPanelSSZZ({
				isAllReadOnly:false
			}),{
				xtype:'hidden',
				name:'history',
				value:'historyCreditFlowFlow'
			}]
		});
	},
	/**
	 * 启动项目
	 */
	save : function(formPanel, window) {
			if (formPanel.getForm().isValid()) {
				if(Ext.getCmp("NewProjectFormHistory").getCmpByName('person.cardtype').getValue()==309){
					if(validateIdCard(Ext.getCmp("NewProjectFormHistory").getCmpByName('person.cardnumber').getValue())==1){
						Ext.Msg.alert('身份证号码验证','证件号码不正确,请仔细核对')
						return;
					}else if(validateIdCard(Ext.getCmp("NewProjectFormHistory").getCmpByName('person.cardnumber').getValue())==2){
						Ext.Msg.alert('身份证号码验证','证件号码地区不正确,请仔细核对')
						return;
					}else if(validateIdCard(Ext.getCmp("NewProjectFormHistory").getCmpByName('person.cardnumber').getValue())==3){
						Ext.Msg.alert('身份证号码验证','证件号码生日日期不正确,请仔细核对')
						return;
					}
				}  
			}
			Ext.MessageBox.wait('正在提交数据, 请稍侯 ...', '提示');
			formPanel.getForm().submit({
				method : 'POST',
				success : function(fp, action) {
					Ext.MessageBox.hide();
					var data = action.result.data;
					/*var projectNum = data.projectNumber;
					var projectName = data.projectName;
					var operationType = data.operationType;
					var flowType = data.flowType;
					var jieName="";
					if(operationType=="creditLoanFlow"){
						if(flowType=="creditLoanFlow"){
							jieName="客户资料录入";
						}
					}*/
					var tabs = Ext.getCmp('centerTabPanel');
					var contentPanel = App.getContentPanel();
					var formView = contentPanel.getItem('ProcessNextForm' + data.taskId);
					if (formView == null) {
						formView = new ProcessNextForm({
							taskId : data.taskId,
							activityName : data.activityName,
							projectName : data.projectName,
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
	}
})
