NewProjectFormSSZZ = Ext.extend(Ext.Window, {
	formPanel : null,
	constructor : function(_cfg) {
		if (_cfg == null) {
			_cfg = {};
		}
		Ext.applyIf(this, _cfg);
		this.initComponents();
		NewProjectFormSSZZ.superclass.constructor.call(this, {
			id : 'NewProjectFormSSZZ',
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
		
		this.productStore = new Ext.data.JsonStore( {
			url : __ctxPath+ "/system/listBpProductParameter.do",
			totalProperty : 'totalCounts',
			root : 'result',
			fields : [{
				name : 'id'
			},{
				name : 'productName'
			}]
		});
		this.productStore.load();
		
		this.formPanel = new Ext.form.FormPanel({
			id : "createNewProjectFormSSZZ",
			frame:true,
			border : false,
			monitorValid : true,
			autoScroll : true,
			anchor : "100%",
			url : __ctxPath + "/flow/startCreditLoanFlowProcessActivity.do",
			bodyStyle : 'padding-left:0px;padding-top:14px',
			items:[new ExtUD.Ext.PersonMainInfoPanelSSZZ({
				isAllReadOnly:false,
				isWAD:false
			})]
		});
	},
	
	/**
	 * 启动项目
	 */
	save : function(formPanel, window) {

			if (formPanel.getForm().isValid()) {
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
			}
			var cardNum = Ext.getCmp("NewProjectFormSSZZ").getCmpByName('person.cardnumber').getValue();
			var personId = Ext.getCmp("NewProjectFormSSZZ").getCmpByName('person.id').getValue();
			var productId=Ext.getCmp("NewProjectFormSSZZ").getCmpByName('slSmallloanProject.productId').getValue();
			var bool = false;
			Ext.Ajax.request({
					 url:  __ctxPath + '/creditFlow/customer/person/verificationPerson.do',
					 method : 'POST',
					 async:false,
					params : {
							cardNum : cardNum,
							personId: personId,
							productId: productId
					},
					success : function(response,request) {
								var obj=Ext.util.JSON.decode(response.responseText);
						        if(obj.msg==false){					                            			
						               Ext.ux.Toast.msg('操作信息',"该证件号码已存在，请重新输入");
						               Ext.getCmp("NewProjectFormSSZZ").getCmpByName('person.cardnumber').setValue("");
						               bool = true;
						               return;
						} 
					}
			 });
		 if(bool){
		 	return;
		 }
			Ext.MessageBox.wait('正在提交数据, 请稍侯 ...', '提示');
			formPanel.getForm().submit({
				method : 'POST',
				clientValidation: true,
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
							productId : data.productId,
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
				},
				baseParams :{
					history:this.type
				}
			});
	}
})
