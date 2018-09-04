var see_Company = function(mortgageid,refreshMortgageGridStore,businessType){
	
	var showCompanyInformation = function(MortgageUpdateData){
		var panel_updateCompany = new Ext.form.FormPanel({
			id :'see_Company',
			url : __ctxPath +'/credit/mortgage/updateCompany.do',
			monitorValid : true,
			bodyStyle:'padding:10px',
			autoScroll : true ,
			labelAlign : 'right',
			buttonAlign : 'center',
			frame : true ,
			border : false,
			items : [{
				layout : 'column',
				xtype:'fieldset',
	            title: '查看<抵质押物>基础信息',
	            collapsible: true,
	            autoHeight:true,
	            anchor : '95%',
				items : [{
	        		xtype : 'hidden',
	        		name : 'mortgageid',
	        		value : mortgageid
	        	},{
	        		xtype : 'hidden',
	        		name : 'procreditMortgage.businessType',
	        		value : MortgageUpdateData.vProcreditDictionary.businessType
	        	},{
					columnWidth : .5,
					layout : 'form',
					labelWidth : 105,
					defaults : {anchor : '100%'},
					items : [{
						xtype : 'textfield',
						fieldLabel : '抵质押物类型',
						value : MortgageUpdateData.vProcreditDictionary.mortgagepersontypeforvalue,
						readOnly : true,
			            cls : 'readOnlyClass'
					},{
						id : 'personType_select',
						xtype : "dickeycombo",
						nodeKey :'syrlx',
						hiddenName : "procreditMortgage.personType",
						value : MortgageUpdateData.vProcreditDictionary.personTypeId,
						fieldLabel : "所有人类型",
						itemName : '所有人类型', // xx代表分类名称
						readOnly : true,
						listeners : {
							afterrender : function(combox) {
								var st = combox.getStore();
								st.on("load", function() {
									if(combox.getValue() == 0||combox.getValue()==1){
										combox.setValue("");
									}else{
										combox.setValue(combox
											.getValue());
									}
									combox.clearInvalid();
								})
							}
						}
					}]
				},{
					columnWidth : .5,
					labelWidth : 110,
					layout : 'form',
					defaults : {anchor : '95%'},
					items : [{
						xtype : "dickeycombo",
						nodeKey :'dblx',
						hiddenName : "procreditMortgage.assuretypeid",
						value : MortgageUpdateData.vProcreditDictionary.assuretypeid,
						fieldLabel : "担保类型",
						readOnly : true,
						itemName : '担保类型', // xx代表分类名称
						listeners : {
							afterrender : function(combox) {
								var st = combox.getStore();
								st.on("load", function() {
									if(combox.getValue() == 0||combox.getValue()==1){
										combox.setValue("");
									}else{
										combox.setValue(combox
											.getValue());
									}
									combox.clearInvalid();
								})
							}
						}
					},{
						xtype : "dickeycombo",
						nodeKey :'bzfs',
						hiddenName : "procreditMortgage.assuremodeid",
						value : MortgageUpdateData.vProcreditDictionary.assuremodeid,
						fieldLabel : "保证方式",
						readOnly : true,
						itemName : '保证方式', // xx代表分类名称
						listeners : {
							afterrender : function(combox) {
								var st = combox.getStore();
								st.on("load", function() {
									if(combox.getValue() == 0||combox.getValue()==1){
										combox.setValue("");
									}else{
										combox.setValue(combox
											.getValue());
									}
									combox.clearInvalid();
								})
							}
						}
					}]
				},{
				columnWidth : 1,
				labelWidth : 105,
				layout : 'form',
				id : 'customerNameCarOne',
				defaults : {anchor : '97.5%'},
				items : [{
					id : 'enterpriseNameMortgage',
					xtype : 'combo',
					triggerClass :'x-form-search-trigger',
					hiddenName : 'customerEnterpriseName',
					fieldLabel : '所有权人',
					readOnly : true,
					onTriggerClick : function(){
						if(businessType=='Financing'){
							selectSlCompanyMain(selectSlCompanyComUpdate);
						}else{
							selectEnterprise(selectCustomer);
						}
                   },
					mode : 'romote',
					lazyInit : false,
					typeAhead : true,
					forceSelection :true,
					minChars : 1,
					listWidth : 230,
					store : getStoreByBusinessType(businessType,'enterprise'),
					displayField : businessType=="Financing"?'corName':'enterprisename',
					valueField : businessType=="Financing"?'companyMainId':'id',
					triggerAction : 'all',
					value : MortgageUpdateData.vProcreditDictionary.assureofname,
					valueNotFoundText : MortgageUpdateData.vProcreditDictionary.assureofnameEnterOrPerson,
					listeners : {
						'select' : function(combo,record,index){
							var address;
							var cciaa;
							var legalpersonid;
							var legalperson;
							if(businessType=='Financing'){
								address = record.get('messageAddress');
								cciaa = record.get('businessCode');
								legalpersonid = record.get('personMainId');
								legalperson = record.get('lawName');
							}else{
								address = record.get('address');
								cciaa = record.get('cciaa');
								legalpersonid = record.get('legalpersonid');
								legalperson = record.get('legalperson');
							}
							setCustomerName(address,cciaa,legalpersonid,legalperson);
							/*var address = record.get('address');
							var cciaa = record.get('cciaa');
							var legalpersonid = record.get('legalpersonid');
							var legalperson = record.get('legalperson');
							setCustomerName(address,cciaa,legalpersonid,legalperson);*/
						}
					}
				}]
			},{
					columnWidth : 1,
					labelWidth : 105,
					layout : 'form',
					defaults : {anchor : '97.5%'},
					items : [{
						id : 'mortgageNameCar',
						xtype : 'textfield',
						fieldLabel : '抵质押物名称',
						readOnly : true,
						value : MortgageUpdateData.vProcreditDictionary.mortgagename,
						maxLength : 50,
						maxLengthText : '最大输入长度50',
						name : 'procreditMortgage.mortgagename',
						blankText : '为必填内容'
					}]
				},{
					columnWidth : .5,
					layout : 'form',
					labelWidth : 105,
					defaults : {xtype : 'numberfield',anchor : '100%'},
					items : [{
						fieldLabel : '最终估价.万元',
						readOnly : true,
						value : MortgageUpdateData.vProcreditDictionary.finalprice==0?'':MortgageUpdateData.vProcreditDictionary.finalprice,
						maxLength : 23,
						maxLengthText : '最大输入长度23',
						name : 'procreditMortgage.finalprice'
					},{
						fieldLabel : '可担保额度.万元',
						readOnly : true,
						value : MortgageUpdateData.vProcreditDictionary.assuremoney==0?'':MortgageUpdateData.vProcreditDictionary.assuremoney,
						maxLength : 23,
						maxLengthText : '最大输入长度23',
						name : 'procreditMortgage.assuremoney'
					}]
				},{
					columnWidth : .5,
					layout : 'form',
					labelWidth : 160,
					defaults : {xtype : 'textfield',anchor : '95%'},
					items : [{
						fieldLabel : '所有权人与借款人的关系',
						readOnly : true,
						name : 'procreditMortgage.relation',
						maxLength : 50,
						maxLengthText : '最大输入长度50',
						value : MortgageUpdateData.vProcreditDictionary.relation
					}]
				},{
					columnWidth : 1,
					layout : 'form',
					labelWidth : 105,
					defaults : {anchor : '97%'},
					items : [{
						xtype : 'textarea',
						fieldLabel : '备注',
					   readOnly : true,
						value : MortgageUpdateData.vProcreditDictionary.remarks,
						maxLength : 100,
						maxLengthText : '最大输入长度100',
						name : 'procreditMortgage.remarks'
					}]
				}]
			},{
				layout : 'column',
				xtype:'fieldset',
	            title: '查看<无限连带责任-公司>详细信息',
	            collapsible: true,
	            autoHeight:true,
	            anchor : '95%',
	            items : [{
					columnWidth : 1,
					labelWidth : 100,
					layout : 'form',
					defaults : {anchor : '97.5%'},
					items : [{
						id : 'updateAddress_en',
						xtype : 'textfield',
						fieldLabel : '注册地址',
						readOnly : true,
						maxLength : 50,
						maxLengthText : '最大输入长度50',
						value : MortgageUpdateData.vProjMortCompany.registeraddress,
						name : 'enterprise.address'
					}]
	            },{
	            	columnWidth : .5,
					layout : 'form',
					labelWidth : 100,
					defaults : {xtype : 'numberfield',anchor : '100%'},
					items : [{
						id : 'updateCciaa_en',
						xtype : 'textfield',
						fieldLabel : '营业执照号码',
						readOnly : true,
						maxLength : 50,
						maxLengthText : '最大输入长度50',
						value : MortgageUpdateData.vProjMortCompany.licensenumber,
						name : 'enterprise.cciaa'
					},{
						fieldLabel : '净资产.万元',
						readOnly : true,
						maxLength : 23,
						maxLengthText : '最大输入长度23',
						value : MortgageUpdateData.vProjMortCompany.netassets==0?'':MortgageUpdateData.vProjMortCompany.netassets,
						name : 'procreditMortgageEnterprise.netassets'
					},{
						fieldLabel : '模型估价.万元',
						readOnly : true,
						maxLength : 23,
						maxLengthText : '最大输入长度23',
						value : MortgageUpdateData.vProjMortCompany.modelrangeprice==0?'':MortgageUpdateData.vProjMortCompany.modelrangeprice,
						name : 'procreditMortgageEnterprise.modelrangeprice'
					}]
	            },{
	            	columnWidth : .5,
					layout : 'form',
					labelWidth : 110,
					defaults : {xtype : 'textfield',anchor : '95%'},
					items : [{
						id : 'updateLegalperson_en',
						fieldLabel : '法人代表',
						readOnly : true,
						maxLength : 50,
						maxLengthText : '最大输入长度50',
						value : MortgageUpdateData.vProjMortCompany.corporate,
						xtype : "combo",
						triggerClass : 'x-form-search-trigger',
						onTriggerClick : function(e) {
							//selectPWName(selectLegalpersonCompanyForUpdate);
							if(businessType=='Financing'){
								selectSlPersonMain(selectSlPersonFarenUpdate);
							}else{
								selectPWName(selectLegalpersonCompanyForUpdate);
							}
						}
					},{
						id : 'updatePhone_en',
						fieldLabel : '法人代表电话',
						readOnly : true,
						maxLength : 50,
						maxLengthText : '最大输入长度50',
						value : MortgageUpdateData.vProjMortCompany.corporatetel,
						name : 'person.cellphone'
					},{
						xtype : "dickeycombo",
						nodeKey :'shgx',
						hiddenName : "procreditMortgageEnterprise.societynexusid",
						value : MortgageUpdateData.vProjMortCompany.societyNexusId,
						fieldLabel : "社会关系",
						readOnly : true,
						itemName : '社会关系', // xx代表分类名称
						listeners : {
							afterrender : function(combox) {
								var st = combox.getStore();
								st.on("load", function() {
									if(combox.getValue() == 0||combox.getValue()==1){
										combox.setValue("");
									}else{
										combox.setValue(combox
											.getValue());
									}
									combox.clearInvalid();
								})
							}
						}
					},{
		            	name : 'procreditMortgageEnterprise.id',
		            	value : MortgageUpdateData.vProjMortCompany.id,
		            	xtype : 'hidden'
		            },{
		            	name : 'procreditMortgage.id',
		            	value : MortgageUpdateData.vProcreditDictionary.id,
		            	xtype : 'hidden'
		            },{
						id : 'updateLegalpersonid_en',
		            	name : 'legalpersonid',
		            	xtype : 'hidden',
		            	value : MortgageUpdateData.vProjMortCompany.legalpersonid
					}]
	            }]
			}]
		});
		
		var window_updateCompany = new Ext.Window({
			id : 'win_see_Company',
			title :'查看无限连带责任-公司信息',
			iconCls : 'btn-add',
			width : (screen.width-180)*0.6,
			height : 460,
			constrainHeader : true ,
			collapsible : true, 
			frame : true ,
			border : false ,
			resizable : true,
			layout:'fit',
			autoScroll : true ,
			bodyStyle:'overflowX:hidden',
			constrain : true ,
			closable : true,
			modal : true,
			buttonAlign: 'right',
			items : [panel_updateCompany],
			listeners : {
			
			}
		});
		window_updateCompany.show();
	}
	
	function setCustomerName(address,cciaa,legalpersonid,legalperson){
		
		Ext.getCmp('updateAddress_en').setValue(address);//注册地址
		Ext.getCmp('updateCciaa_en').setValue(cciaa);//营业执照号码
		Ext.getCmp('updateLegalperson_en').setValue(legalpersonid);//法人代表id
		Ext.getCmp('updateLegalperson_en').setRawValue(legalperson);//法人代表name
		Ext.getCmp('updateLegalpersonid_en').setValue(legalpersonid);
		var phoneUpdate;
		Ext.Ajax.request({
			url : __ctxPath +'/creditFlow/customer/person/seeInfoPerson.do',
			method : 'POST',
			success : function(response,request) {
				var objPersonCompanyUpdate = Ext.util.JSON.decode(response.responseText);
				var personObjectCompanyUpdate = objPersonCompanyUpdate.data;
				if(businessType=='Financing'){
					if(personObjectCompany.linktel != null || personObjectCompany.linktel != ""){
						phoneUpdate = personObjectCompany.linktel;
					}else{
						phoneUpdate = "";
					}
				}else{
					if(personObjectCompanyUpdate.cellphone != null || personObjectCompanyUpdate.cellphone != ""){
						phoneUpdate = personObjectCompanyUpdate.cellphone;
					}else{
						phoneUpdate = "";
					}
				}
				alert("phoneUpdate=="+phoneUpdate);
				Ext.getCmp('updatePhone_en').setValue(phoneUpdate);//法人代表电话
			},
			failure : function(response) {					
					Ext.Msg.alert('状态','操作失败，请重试');		
			},
			params: { id: legalpersonid,businessType: businessType }
		});	
	};
	
	function selectCustomer(obj) {
		Ext.getCmp('enterpriseNameMortgage').setValue(obj.id);
		Ext.getCmp('enterpriseNameMortgage').setRawValue(obj.enterprisename) ;
		setCustomerName(obj.address,obj.cciaa,obj.legalpersonid,obj.legalperson);
	}
	
	function selectSlCompanyComUpdate(obj) {
		Ext.getCmp('enterpriseNameMortgage').setValue(obj.companyMainId);
		Ext.getCmp('enterpriseNameMortgage').setRawValue(obj.corName) ;
		setCustomerName(obj.messageAddress,obj.businessCode,obj.personMainId,obj.lawName);
	}
	
	//法人代表
	var selectLegalpersonCompanyForUpdate = function(obj){
		Ext.getCmp('updateLegalperson_en').setValue(obj.id) ;
		Ext.getCmp('updateLegalperson_en').setRawValue(obj.name);
		Ext.getCmp('updatePhone_en').setValue(obj.cellphone);//法人代表电话
		Ext.getCmp('updateLegalpersonid_en').setValue(obj.id);
	}
	
	var selectSlPersonFarenUpdate = function(obj){
		Ext.getCmp('updateLegalperson_en').setValue(obj.personMainId) ;
		Ext.getCmp('updateLegalperson_en').setRawValue(obj.name);
		Ext.getCmp('updatePhone_en').setValue(obj.linktel);//法人代表电话
		Ext.getCmp('updateLegalpersonid_en').setValue(obj.personMainId);
	}
	
	Ext.Ajax.request({
		url : __ctxPath +'/credit/mortgage/seeCompanyForUpdate.do',
		method : 'POST',
		success : function(response, request) {
			obj = Ext.util.JSON.decode(response.responseText);
			if(obj.success==true){
				showCompanyInformation(obj.data) ;
			}else{
				Ext.Msg.alert('状态', obj.msg);
			}
		},
		failure : function(response) {
			Ext.Msg.alert('状态', '操作失败，请重试');
		},
		params : {
			id : mortgageid,
			businessType : businessType
		}
	});
}