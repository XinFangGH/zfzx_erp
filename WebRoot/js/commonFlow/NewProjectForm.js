/**
 * 新建项目时 选择个人参照 选中个人后执行操作  by shendexuan
 * @param {} obj
 */
function selectCusNew(obj) {
	var op = Ext.getCmp('createNewSLFunctionForm').get(2).get(1);
	op.getCmpByName('person.id').setValue("");
    op.getCmpByName('person.name').setValue("");
    op.getCmpByName('person.sex').setValue("");
	op.getCmpByName('person.cardtype').setValue("");
	op.getCmpByName('person.cardnumber').setValue("");
	op.getCmpByName('person.marry').setValue("");
	op.getCmpByName('person.cellphone').setValue("");
	//op.getCmpByName('person.birthday').setValue("");
	op.getCmpByName('person.postcode').setValue("");
	op.getCmpByName('person.postaddress').setValue("");
	//op.getCmpByName('person.currentcompany').setValue("");
	//op.getCmpByName('person.unitaddress').setValue("");
	//op.getCmpByName("person.familyaddress").setValue("");
	//op.getCmpByName('person.hukou').setValue("");
	/*op.getCmpByName('enterpriseBank.bankname').setValue("");
	op.getCmpByName('enterpriseBank.accountnum').setValue("");
	op.getCmpByName('enterpriseBank.openType').setValue("");
	op.getCmpByName('enterpriseBank.name').setValue("");
	op.getCmpByName('enterpriseBank.accountType').setValue("");
	op.getCmpByName('enterpriseBank.bankid').setValue("");
	op.getCmpByName('enterpriseBank.id').setValue("");*/
	
	if(obj.id!=0 && obj.id!="")	
	op.getCmpByName('person.id').setValue(obj.id);
	if(obj.name!=0 && obj.name!="")	
	op.getCmpByName('person.name').setValue(obj.name);
	if(obj.sex!=0 && obj.sex!="")	
	op.getCmpByName('person.sex').setValue(obj.sex);
	if(obj.cardtype!=0 && obj.cardtype!="")	
	op.getCmpByName('person.cardtype').setValue(obj.cardtype);
	if(obj.cardnumber!=0 && obj.cardnumber!="")	
	op.getCmpByName('person.cardnumber').setValue(obj.cardnumber);
	if(obj.marry!=0 && obj.marry!="")	
	op.getCmpByName('person.marry').setValue(obj.marry);
	if(obj.cellphone!=0 && obj.cellphone!="")	
	op.getCmpByName('person.cellphone').setValue(obj.cellphone);
	//if(obj.birthday!=0 && obj.birthday!="")	
	//op.getCmpByName('person.birthday').setValue(obj.birthday);
	//if(obj.familypostcode!=0 && obj.familypostcode!="")	
	//op.getCmpByName('person.postcode').setValue(obj.familypostcode);
	if(obj.postaddress!=0 && obj.postaddress!="")	
	op.getCmpByName('person.postaddress').setValue(obj.postaddress);
	//if(obj.currentcompany!=0 && obj.currentcompany!="")	
	//op.getCmpByName('person.currentcompany').setValue(obj.currentcompany);
	//if(obj.unitaddress!=0 && obj.unitaddress!="")	
	//op.getCmpByName('person.unitaddress').setValue(obj.unitaddress);
	//if(obj.familyaddress!=0 && obj.familyaddress!="")	
	//op.getCmpByName('person.familyaddress').setValue(obj.familyaddress);
	//if(obj.hukou!=0 && obj.hukou!="")	
	//op.getCmpByName('person.hukou').setValue(obj.hukou);
	/*if(obj.bankName!=0 && obj.bankName!="")	
	op.getCmpByName('enterpriseBank.bankname').setValue(obj.bankName);
	if(obj.bankNum!=0 && obj.bankNum!="")	
	op.getCmpByName('enterpriseBank.accountnum').setValue(obj.bankNum);	
	
	op.getCmpByName('enterpriseBank.openType').setValue(obj.openType);

	if(obj.khname!=0 && obj.khname!="")	
	op.getCmpByName('enterpriseBank.name').setValue(obj.khname);
	op.getCmpByName('enterpriseBank.accountType').setValue(obj.accountType);
	if(obj.bankId!="")	
	op.getCmpByName('enterpriseBank.bankid').setValue(obj.bankId);
	if(obj.enterpriseBankId!="")	
	op.getCmpByName('enterpriseBank.id').setValue(obj.enterpriseBankId);*/
	
	
	ressetProjuect(Ext.getCmp('createNewSLFunctionForm'));
}
/**
 * 新建项目时 选择企业参照 选中企业后执行操作  by shendexuan
 * @param {} obj
 */
function setEnterpriseNameStockUpdateNew(obj) {

	ressetProjuect(Ext.getCmp('createNewSLFunctionForm'));
	var op = Ext.getCmp('createNewSLFunctionForm').getCmpByName('customerInfo').get(1);
	op.getCmpByName('enterprise.enterprisename').setValue("");
	op.getCmpByName('enterprise.id').setValue("");
	op.getCmpByName('enterprise.shortname').setValue("");
	op.getCmpByName('enterprise.area').setValue("");
	op.getCmpByName('enterprise.cciaa').setValue("");
	op.getCmpByName('enterprise.organizecode').setValue("");
	op.getCmpByName('enterprise.telephone').setValue("");
	op.getCmpByName('enterprise.postcoding').setValue("");
	op.getCmpByName('enterprise.hangyeType').setValue("");
	op.getCmpByName('enterprise.hangyeName').setValue("");
	op.getCmpByName('person.id').setValue("");
    op.getCmpByName('person.name').setValue("");
    op.getCmpByName('person.sex').setValue("");
	op.getCmpByName('person.cardtype').setValue("");
	op.getCmpByName('person.cardnumber').setValue("");
	op.getCmpByName('person.cellphone').setValue("");
	op.getCmpByName('person.selfemail').setValue("");
	
	
	
	if(obj.enterprisename!=0 && obj.enterprisename!="")	
	op.getCmpByName('enterprise.enterprisename').setValue(obj.enterprisename);
	if(obj.id!=0 && obj.id!="")	
	op.getCmpByName('enterprise.id').setValue(obj.id);
	if(obj.shortname!=0 && obj.shortname!="")	
	op.getCmpByName('enterprise.shortname').setValue(obj.shortname);
	if(obj.area!=0 && obj.area!="")	
	op.getCmpByName('enterprise.area').setValue(obj.area);
	if(obj.cciaa!=0 && obj.cciaa!="")	
	op.getCmpByName('enterprise.cciaa').setValue(obj.cciaa);
	if(obj.organizecode!=0 && obj.organizecode!="")	
	op.getCmpByName('enterprise.organizecode').setValue(obj.organizecode);
	if(obj.telephone!=0 && obj.telephone!="")
	op.getCmpByName('enterprise.telephone').setValue(obj.telephone);
	if(obj.postcoding!=0 && obj.postcoding!="")
	op.getCmpByName('enterprise.postcoding').setValue(obj.postcoding);
	if(obj.hangyetype!=0 && obj.hangyetype!="")
	{
	   op.getCmpByName('enterprise.hangyeType').setValue(obj.hangyetype);
	   op.getCmpByName('enterprise.hangyeName').setValue(obj.hangyetypevalue);
	}
	
	Ext.Ajax.request({
		url : __ctxPath + '/creditFlow/customer/person/seeInfoPerson.do',
		method : "post",
		params : {
			id : obj.legalpersonid
		},
		success : function(d) {

			var c = Ext.util.JSON.decode(d.responseText);
			var id = c.data.id;
			var name = c.data.name;
			var sex = c.data.sex;
			var cardtype = c.data.cardtype;
			var cardnumber = c.data.cardnumber;
			var telphone = c.data.telphone;
			var selfemail = c.data.selfemail;
			if(id!=0 && id!="")
			op.getCmpByName('person.id').setValue(id);
			if(name!=0 && name!="")
			op.getCmpByName('person.name').setValue(name);
			if(sex!=0 && sex!="")
			op.getCmpByName('person.sex').setValue(sex);
			if(cardtype!=0 && cardtype!="")
			op.getCmpByName('person.cardtype').setValue(cardtype);
			if(cardnumber!=0 && cardnumber!="")
			op.getCmpByName('person.cardnumber').setValue(cardnumber);
			if(telphone!=0 && telphone!="")
			op.getCmpByName('person.cellphone').setValue(telphone);
			if(selfemail!=0 && selfemail!="")
			op.getCmpByName('person.selfemail').setValue(selfemail);
		}

	})
		var edGrid=Ext.getCmp('createNewSLFunctionForm').getCmpByName('gudong_store').get(0).get(1);
		var store = edGrid.getStore();
		var url = __ctxPath + '/creditFlow/common/getShareequity.do?enterpriseId='+ obj.id;
		store.proxy.conn.url = url;
		store.load();
}
/**
 * 新建项目时 个人参照中输入姓名执行操作  by chencc
 * @param {} obj
 */
function selectCusCombo(obj) {
	var op = Ext.getCmp('createNewSLFunctionForm').get(2).get(1);
	op.getCmpByName('person.id').setValue("");
    op.getCmpByName('person.name').setValue("");
    op.getCmpByName('person.sex').setValue("");
	op.getCmpByName('person.cardtype').setValue("");
	op.getCmpByName('person.cardnumber').setValue("");
	op.getCmpByName('person.marry').setValue("");
	op.getCmpByName('person.cellphone').setValue("");
	op.getCmpByName('person.selfemail').setValue("");
	op.getCmpByName('person.postcode').setValue("");
	op.getCmpByName('person.postaddress').setValue("");
	
	if(obj.get('id')!=0 && obj.get('id')!="")	
	op.getCmpByName('person.id').setValue(obj.get('id'));
	if(obj.get('name')!=0 && obj.get('name')!="")	
	op.getCmpByName('person.name').setValue(obj.get('name'));
	if(obj.get('sex')!=0 && obj.get('sex')!="")	
	op.getCmpByName('person.sex').setValue(obj.get('sex'));
	if(obj.get('cardtype')!=0 && obj.get('cardtype')!="")	
	op.getCmpByName('person.cardtype').setValue(obj.get('cardtype'));
	if(obj.get('cardnumber')!=0 && obj.get('cardnumber')!="")	
	op.getCmpByName('person.cardnumber').setValue(obj.get('cardnumber'));
	if(obj.get('marry')!=0 && obj.get('marry')!="")	
	op.getCmpByName('person.marry').setValue(obj.get('marry'));
	if(obj.get('telphone')!=0 && obj.get('telphone')!="")	
	op.getCmpByName('person.cellphone').setValue(obj.get('telphone'));
	if(obj.get('selfemail')!=0 && obj.get('selfemail')!="")	
	op.getCmpByName('person.selfemail').setValue(obj.get('selfemail'));
	if(obj.get('postcode')!=0 && obj.get('postcode')!="")	
	op.getCmpByName('person.postcode').setValue(obj.get('postcode'));
	if(obj.get('postaddress')!=0 && obj.get('postaddress')!="")	
	op.getCmpByName('person.postaddress').setValue(obj.get('postaddress'));
	ressetProjuect(Ext.getCmp('createNewSLFunctionForm'));
}
/**
 * 新建项目时 企业参照中输入企业名称  by chencc
 * @param {} obj
 */
function setEnterpriseNameStockUpdateCombo(obj) {

	ressetProjuect(Ext.getCmp('createNewSLFunctionForm'));
	var op = Ext.getCmp('createNewSLFunctionForm').getCmpByName('customerInfo').get(1);

	op.getCmpByName('enterprise.enterprisename').setValue("");
	op.getCmpByName('enterprise.id').setValue("");
	op.getCmpByName('enterprise.surplusMoney').setValue("");
	op.getCmpByName('enterprise.shortname').setValue("");
	op.getCmpByName('enterprise.area').setValue("");
	op.getCmpByName('enterprise.cciaa').setValue("");
	op.getCmpByName('enterprise.organizecode').setValue("");
	op.getCmpByName('enterprise.telephone').setValue("");
	op.getCmpByName('enterprise.postcoding').setValue("");
	op.getCmpByName('enterprise.hangyeType').setValue("");
	op.getCmpByName('enterprise.hangyeName').setValue("");
	op.getCmpByName('person.id').setValue("");
    op.getCmpByName('person.name').setValue("");
    op.getCmpByName('person.sex').setValue("");
	op.getCmpByName('person.cardtype').setValue("");
	op.getCmpByName('person.cardnumber').setValue("");
	op.getCmpByName('person.cellphone').setValue("");
	op.getCmpByName('person.selfemail').setValue("");
	
	
	if(obj.get('enterprisename')!=0 &&obj.get('enterprisename')!="")	
	op.getCmpByName('enterprise.enterprisename').setValue(obj.get('enterprisename'));
	if(obj.get('id')!=0 && obj.get('id')!="")	
	op.getCmpByName('enterprise.id').setValue(obj.get('id'));
	op.getCmpByName('enterprise.surplusMoney').setValue(obj.get('surplusMoney'));
	if(obj.get('shortname')!=0 && obj.get('shortname')!="")
	op.getCmpByName('enterprise.shortname').setValue(obj.get('shortname'));
    if(obj.get('address')!=0 && obj.get('address')!="")	
	op.getCmpByName('enterprise.address').setValue(obj.get('address'));
	if(obj.get('cciaa')!=0 && obj.get('cciaa')!="")	
	op.getCmpByName('enterprise.cciaa').setValue(obj.get('cciaa'));
	if(obj.get('organizecode')!=0 && obj.get('organizecode')!="")	
	op.getCmpByName('enterprise.organizecode').setValue(obj.get('organizecode'));
	if(obj.get('telephone')!=0 && obj.get('telephone')!="")
	op.getCmpByName('enterprise.telephone').setValue(obj.get('telephone'));
	if(obj.get('postcoding')!=0 && obj.get('postcoding')!="")
	op.getCmpByName('enterprise.postcoding').setValue(obj.get('postcoding'));
	if(obj.get('hangyetype')!=0 && obj.get('hangyetype')!="")
	{
	   op.getCmpByName('enterprise.hangyeType').setValue(obj.get('hangyetype'));
	   op.getCmpByName('enterprise.hangyeName').setValue(obj.get('hangyetypevalue'));
	}
	
	Ext.Ajax.request({
		url : __ctxPath + '/creditFlow/customer/person/seeInfoPerson.do',
		method : "post",
		params : {
			id : obj.get('legalpersonid')
		},
		success : function(d) {

			var c = Ext.util.JSON.decode(d.responseText);
			var id = c.data.id;
			var name = c.data.name;
			var sex = c.data.sex;
			var cardtype = c.data.cardtype;
			var cardnumber = c.data.cardnumber;
			var telphone = c.data.telphone;
			var selfemail = c.data.selfemail;
			if(id!=0 && id!="")
			op.getCmpByName('person.id').setValue(id);
			if(name!=0 && name!="")
			op.getCmpByName('person.name').setValue(name);
			if(sex!=0 && sex!="")
			op.getCmpByName('person.sex').setValue(sex);
			if(cardtype!=0 && cardtype!="")
			op.getCmpByName('person.cardtype').setValue(cardtype);
			if(cardnumber!=0 && cardnumber!="")
			op.getCmpByName('person.cardnumber').setValue(cardnumber);
			if(telphone!=0 && telphone!="")
			op.getCmpByName('person.cellphone').setValue(telphone);
			if(selfemail!=0 && selfemail!="")
			op.getCmpByName('person.selfemail').setValue(selfemail);
		}

	})
		var edGrid=Ext.getCmp('createNewSLFunctionForm').getCmpByName('gudong_store').get(0).get(1);
		var store = edGrid.getStore();
		var url = __ctxPath + '/creditFlow/common/getShareequity.do?enterpriseId='+ obj.get('id');
		store.proxy.conn.url = url;
		store.load();
}
/**
 * 重置项目名称
 * @param {} obj
 */
/**
 * 重置项目名称  
 * @param {} obj 项目名称对象 by shendexuan
 */
function ressetProjuect(){
	   var obj1=Ext.getCmp('createNewSLFunctionForm');
       var projectNameObject=obj1.getCmpByName('projectName');
       if(projectNameObject.getValue().trim()!=""){
           projectNameObject.setValue("");
           projectNameObject.clearInvalid(); 
       }
}
newProjectForm = Ext.extend(Ext.Panel, {
	formPanel : null,
	topbar : null,
	constructor : function(_cfg) {
		if (_cfg == null) {
			_cfg = {};
		}
		Ext.applyIf(this, _cfg);
		this.initComponents();
		newProjectForm.superclass.constructor.call(this, {
			id : 'NewProjectForm',
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
			url : __ctxPath + "/flow/saveProcessActivity.do",
			autoScroll : true,
			id : "createNewSLFunctionForm",
			bodyStyle : 'padding-left:0px;padding-top:14px',
			tbar : this.topbar,
			anchor : "100%",
			labelAlign : "left",
			items : [{
				xtype : 'hidden',
				name : 'preHandler',
				value : 'creditProjectService.startCreditFlowProject'
			}, {
				xtype : 'fieldset',
				title : '项目基本信息',
				bodyStyle : 'padding-left:0px;padding-top:4px',
				collapsible : true,
				labelAlign : 'right',
				autoHeight : true,
				//items : [new ExtUD.Ext.TypeSelectInfoTwoGradesPanel()]//2级分类
				//add by gao 
				items : [
				{
					xtype:'fieldset',
					title:'项目信息',
					items:[new ExtUD.Ext.TypeSelectInfoThreeGradesPanel({isGroup:'false',pType:this.type=='person'?false:true})]
				},{
					xtype:'fieldset',
					title:'借款意向',
					items:[new LendForm()]
				}
				
					
				]//3级分类,原来的名字为：TypeSelectInfoPanel 
				//items : [new ExtUD.Ext.TypeSelectInfoFourGradesPanel()]//4级分类
			},{
				xtype : 'fieldset',
				title : '客户基本信息',
				bodyStyle : 'padding-left:0px;',
				collapsible : false,
				name:'customerInfo',
				labelAlign : 'right',
				autoHeight : true,
				//disabled : true,
				items : []
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
//					id :"",
					items : [new ExtUD.Ext.ProjectNameTextField({hiddenMineType:isGroup=="true"})
					]
				}, {
					columnWidth : .15, 
					layout : "form",
					bodyStyle : 'padding-left:17px;',
					items : [new ExtUD.Ext.ProjectBuildButton({hiddenMineType:isGroup=="true"})]
				}]
			}
			]
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
/*		var oppositeTypeObj=formPanel.getCmpByName('oppositeType');
		oppositeTypeObj.setValue('company_customer')*/
		/*function loadData(){
			
			var record = oppositeTypeObj.getStore().getAt(0);
			var v = record.data.dicKey;;
			oppositeTypeObj.setValue(v);
		    
		    oppositeTypeObj.fireEvent("select",oppositeTypeObj,oppositeTypeObj.getStore().getAt(0),0);
		}
		oppositeTypeObj.getStore().load({"callback":loadData()});*/
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
			var businessType = Ext.getCmp("NewProjectForm").getCmpByName('businessType').getValue();
			if(businessType!=null&&businessType!=1067){
				var mType = Ext.getCmp("NewProjectForm").getCmpByName('oppositeType').getValue();
				if(mType=="company_customer"){     
					var edGrid=formPanel.getCmpByName('gudong_store').get(0).get(1);
					vDates=getGridDate(edGrid);
				
				}
				
				var operationType=Ext.getCmp("NewProjectForm").getCmpByName('operationType').getValue();
				if(operationType==1153){
					if(Ext.getCmp("NewProjectForm").getCmpByName('person.cardtype').getValue()==309){
						if(validateIdCard(Ext.getCmp("NewProjectForm").getCmpByName('person.cardnumber').getValue())==1){
							Ext.Msg.alert('身份证号码验证','证件号码不正确,请仔细核对')
							return;
						}else if(validateIdCard(Ext.getCmp("NewProjectForm").getCmpByName('person.cardnumber').getValue())==2){
							Ext.Msg.alert('身份证号码验证','证件号码地区不正确,请仔细核对')
							return;
						}else if(validateIdCard(Ext.getCmp("NewProjectForm").getCmpByName('person.cardnumber').getValue())==3){
							Ext.Msg.alert('身份证号码验证','证件号码生日日期不正确,请仔细核对')
							return;
						}
					}  
				}else{
					var customerype = Ext.getCmp("NewProjectForm").getCmpByName('oppositeType').getValue();			
					if(customerype=="person_customer"){     
						if(Ext.getCmp("NewProjectForm").getCmpByName('person.cardtype').getValue()==309){
							if(validateIdCard(Ext.getCmp("NewProjectForm").getCmpByName('person.cardnumber').getValue())==1){
								Ext.Msg.alert('身份证号码验证','证件号码不正确,请仔细核对')
								return;
							}else if(validateIdCard(Ext.getCmp("NewProjectForm").getCmpByName('person.cardnumber').getValue())==2){
								Ext.Msg.alert('身份证号码验证','证件号码地区不正确,请仔细核对')
								return;
							}else if(validateIdCard(Ext.getCmp("NewProjectForm").getCmpByName('person.cardnumber').getValue())==3){
								Ext.Msg.alert('身份证号码验证','证件号码生日日期不正确,请仔细核对')
								return;
							}
						}  
					}
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
					Ext.Msg.alert('状态', '项目：' + projectName+ ' 启动成功!<br /><br /> 项目编号：' + projectNum+ '<br /><br />流转到下一个节点:'+jieName+'!');
					AppUtil.removeTab('NewProjectForm');
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
