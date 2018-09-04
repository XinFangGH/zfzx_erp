var see_StockOwnerShip = function(mortgageid,refreshMortgageGridStore,businessType){
	
	var showStockownershipInformation = function(MortgageUpdateData){
		var panel_updateStockownership = new Ext.form.FormPanel({
			id :'see_StockOwnerShip',
			url : __ctxPath +'/credit/mortgage/updateStockownership.do',
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
					xtype : "hidden",
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
						blankText : '为必填内容',
						readOnly : true,
			            cls : 'readOnlyClass'
					},{
						id : 'personType_select',
						xtype : "dickeycombo",
						nodeKey :'syrlx',
						hiddenName : "procreditMortgage.personType",
						value : MortgageUpdateData.vProcreditDictionary.personTypeId,
						fieldLabel : "所有人类型",
						readOnly : true,
						itemName : '所有人类型', // xx代表分类名称
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
							},
							'select' : function(combo,record, index){
								if(combo.getValue()==602){
									hideField(Ext.getCmp('personNameMortgage')) ;
									Ext.getCmp('personNameMortgage').disable() ;
									showField(Ext.getCmp('enterpriseNameMortgage')) ;
									Ext.getCmp('enterpriseNameMortgage').enable() ;
								}else{
									hideField(Ext.getCmp('enterpriseNameMortgage')) ;
									Ext.getCmp('enterpriseNameMortgage').disable() ;
									showField(Ext.getCmp('personNameMortgage')) ;
									Ext.getCmp('personNameMortgage').enable() ;
								}
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
							selectSlCompanyMain(selectSlCompanyStockUpdate);
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
					triggerAction : 'all'
				},{
					id : 'personNameMortgage',
					xtype : 'combo',
					hiddenName : 'customerPersonName',
					triggerClass :'x-form-search-trigger',
					onTriggerClick : function(){
						if(businessType=='Financing'){
							selectSlPersonMain(selectSlPersonStockUpdate1);
						}else{
							selectPWName(selectCustomer);
						}
	               	},
					fieldLabel : '所有权人',
					readOnly : true,
					mode : 'romote',
					lazyInit : false,
					typeAhead : true,
					minChars : 1,
					listWidth : 230,
					store : getStoreByBusinessType(businessType,'person'),
					displayField : 'name',
					valueField : businessType=="Financing"?'personMainId':'id',
					triggerAction : 'all'
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
	            title: '查看<股权>详细信息',
	            collapsible: true,
	            autoHeight:true,
	            anchor : '95%',
	            items : [{
	            	columnWidth : 1,
					labelWidth : 90,
					layout : 'form',
					defaults : {anchor : '97.5%'},
					items : [{
						id : 'targetEnterpriseName',
						xtype : 'combo',
						triggerClass :'x-form-search-trigger',
						hiddenName : 'targetEnterpriseName',
						fieldLabel : '<font color=red>*</font>目标公司名称',
						readOnly : true,
						onTriggerClick : function(){
							if(businessType=='Financing'){
								selectSlCompanyMain(selectSlCompanyTargetStockUpdate);
							}else{
								selectEnterprise(setEnterpriseNameStockUpdate);
							}
	                   },
						mode : 'romote',
						lazyInit : false,
						typeAhead : true,
						forceSelection :true,
						minChars : 1,
						listWidth : 230,
						value : MortgageUpdateData.vProjMortStockOwnerShip.corporationname,
						valueNotFoundText : MortgageUpdateData.vProjMortStockOwnerShip.enterprisename,
						store : getStoreByBusinessTypeStock(businessType),
						/*store : new Ext.data.JsonStore({
							url : __ctxPath +'/credit/customer/enterprise/ajaxQueryEnterpriseForCombo.do',
							root : 'topics',
							autoLoad : true,
							fields : [{
										name : 'id'
									}, {
										name : 'enterprisename'
									}, {
										name : 'shortname'
									}],
							listeners : {
								'load' : function(s,r,o){
									if(s.getCount()==0){
										Ext.getCmp('targetEnterpriseName').markInvalid('没有查找到匹配的记录') ;
									}
								}
							}
						}),*/
						displayField : businessType=="Financing"?'corName':'enterprisename',
						valueField : businessType=="Financing"?'companyMainId':'id',
						triggerAction : 'all',
						listeners : {
							'select' : function(combo,record,index){
								var id;
								if(businessType=='Financing'){
									id = record.get('companyMainId');
								}else{
									id = record.get('id');
								}
								setenterpriseNameMortgageUpdate(id);
							}
						}
					}]
	            },{
	            	columnWidth : .5,
					layout : 'form',
					labelWidth : 105,
					defaults : {xtype : 'numberfield',anchor : '100%'},
					items : [{
						id : 'cciaa_enStockUpdate',
						xtype : 'textfield',
						fieldLabel : '营业执照号码',
						readOnly : true,
						name : 'enterprise.cciaa',
						value : MortgageUpdateData.vProjMortStockOwnerShip.licencenumber,
						maxLength : 50,
						maxLengthText : '最大输入长度50'
					},{
						id : 'registermoney_enStockUpdate',
						fieldLabel : '注册资本.万元',
						readOnly : true,
						value : MortgageUpdateData.vProjMortStockOwnerShip.enrolcapital==0?'':MortgageUpdateData.vProjMortStockOwnerShip.enrolcapital,
						maxLength : 23,
						maxLengthText : '最大输入长度23',
						name : 'enterprise.registermoney'
					},{
						xtype : 'textfield',
						fieldLabel : '控制权人',
						readOnly : true,
						name : 'procreditMortgageStockownership.stockownership',
						value : MortgageUpdateData.vProjMortStockOwnerShip.stockownership,
						maxLength : 50,
						maxLengthText : '最大输入长度50'
					},{
						fieldLabel : '股权.%',
						readOnly : true,
						value : MortgageUpdateData.vProjMortStockOwnerShip.stockownershippercent==0?'':MortgageUpdateData.vProjMortStockOwnerShip.stockownershippercent,
						maxLength : 23,
						maxLengthText : '最大输入长度23',
						name : 'procreditMortgageStockownership.stockownershippercent'
					},{
						fieldLabel : '净资产.万元',
						readOnly : true,
						value : MortgageUpdateData.vProjMortStockOwnerShip.netassets==0?'':MortgageUpdateData.vProjMortStockOwnerShip.netassets,
						maxLength : 23,
						maxLengthText : '最大输入长度23',
						name : 'procreditMortgageStockownership.netassets'
					},{
						fieldLabel : '模型估价.万元',
						readOnly : true,
						value : MortgageUpdateData.vProjMortStockOwnerShip.modelrangeprice==0?'':MortgageUpdateData.vProjMortStockOwnerShip.modelrangeprice,
						maxLength : 23,
						maxLengthText : '最大输入长度23',
						name : 'procreditMortgageStockownership.modelrangeprice'
					}]
	            },{
	            	columnWidth : .5,
					layout : 'form',
					labelWidth : 105,
					defaults : {xtype : 'textfield',anchor : '95%'},
					items : [{
						id : 'hangyeTypeValue_update',
						xtype : "combo",
						triggerClass : 'x-form-search-trigger',
						fieldLabel : "行业类别",
						readOnly : true,
						scope : this,
						onTriggerClick : function(e) {
							var objupdate = this;
							var oobbjupdate=Ext.getCmp('hangyeTypeId_update');
							selectTradeCategory(objupdate,oobbjupdate);
						}
					},{
						id : 'hangyeTypeId_update',
						xtype : "hidden",
						name : 'enterprise.hangyeType'
					},{
						id : 'registerdate_enStockUpdate',
						xtype : 'datefield',
						fieldLabel : '注册时间',
						readOnly : true,
						format : 'Y-m-d',
						value : MortgageUpdateData.vProjMortStockOwnerShip.registerdate,
						name : 'enterprise.registerstartdate'
					},{
						id : 'legalperson_enStockUpdate',
						fieldLabel : '法人代表',
						readOnly : true,
						xtype : "combo",
						maxLength : 50,
						maxLengthText : '最大输入长度50',
						triggerClass : 'x-form-search-trigger',
						value : MortgageUpdateData.vProjMortStockOwnerShip.legalperson,
						onTriggerClick : function(e) {
							//selectPWName(selectLegalpersonStockUpdate);
							if(businessType=='Financing'){
								selectSlPersonMain(selectSlPersonStockUpdate);
							}else{
								selectPWName(selectLegalpersonStockUpdate);
							}
						}
					},{
						xtype : 'numberfield',
						fieldLabel : '经营时间.年',
						readOnly : true,
						value : MortgageUpdateData.vProjMortStockOwnerShip.managementtime==0?'':MortgageUpdateData.vProjMortStockOwnerShip.managementtime,
						maxLength : 11,
						maxLengthText : '最大输入长度11',
						name : 'procreditMortgageStockownership.managementtime'
					},{
						xtype : "dickeycombo",
						nodeKey :'jyzk',
						hiddenName : "procreditMortgageStockownership.managementstatusid",
						value : MortgageUpdateData.vProjMortStockOwnerShip.managementStatusId,
						fieldLabel : "经营状况",
						readOnly : true,
						editable :false,
						itemName : '经营状况', // xx代表分类名称
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
						nodeKey :'djqk',
						hiddenName : "procreditMortgageStockownership.registerinfoid",
						value : MortgageUpdateData.vProjMortStockOwnerShip.registerInfoId,
						fieldLabel : "登记情况",
						readOnly : true,
						itemName : '登记情况', // xx代表分类名称
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
		            	name : 'procreditMortgageStockownership.id',
		            	value : MortgageUpdateData.vProjMortStockOwnerShip.id,
		            	xtype : 'hidden'
		            },{
		            	name : 'procreditMortgage.id',
		            	value : MortgageUpdateData.vProcreditDictionary.id,
		            	xtype : 'hidden'
		            },{
						id : 'legalpersonid_enStockUpdate',
		            	name : 'enterprise.legalpersonid',
		            	value : MortgageUpdateData.vProjMortStockOwnerShip.legalpersonid,
		            	xtype : 'hidden'
					}]
	            }]
			
			}]
		});
		
		var window_updateStockownership = new Ext.Window({
			id : 'win_see_StockOwnerShip',
			title :'查看股权信息',
			iconCls : 'btn-update',
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
			items : [panel_updateStockownership],
			listeners : {
			
			}
		});
		window_updateStockownership.show();
		if(MortgageUpdateData.vProcreditDictionary.personTypeId == 602){
			hideField(Ext.getCmp('personNameMortgage')) ;
			showField(Ext.getCmp('enterpriseNameMortgage')) ;
			Ext.getCmp('enterpriseNameMortgage').valueNotFoundText = MortgageUpdateData.vProcreditDictionary.assureofnameEnterOrPerson,
			Ext.getCmp('enterpriseNameMortgage').setValue(MortgageUpdateData.vProcreditDictionary.assureofname) ;
			Ext.getCmp('enterpriseNameMortgage').originalValue = Ext.getCmp('enterpriseNameMortgage').getValue();
		}else if(MortgageUpdateData.vProcreditDictionary.personTypeId == 603){
			hideField(Ext.getCmp('enterpriseNameMortgage')) ;
			showField(Ext.getCmp('personNameMortgage')) ;
			Ext.getCmp('personNameMortgage').valueNotFoundText = (MortgageUpdateData.vProcreditDictionary.assureofnameEnterOrPerson);
			Ext.getCmp('personNameMortgage').setValue(MortgageUpdateData.vProcreditDictionary.assureofname) ;
			Ext.getCmp('personNameMortgage').originalValue = Ext.getCmp('personNameMortgage').getValue();
		}
	}
	
	function selectCustomer(obj) {
		if (obj.shortname) {//企业
			Ext.getCmp('enterpriseNameMortgage').setValue(obj.id);
			Ext.getCmp('enterpriseNameMortgage').setRawValue(obj.enterprisename) ;
		} else if (obj.name) {//个人
			Ext.getCmp('personNameMortgage').setValue(obj.id);
			Ext.getCmp('personNameMortgage').setRawValue(obj.name) ;
		}
	}
	
	function selectSlCompanyStockUpdate(obj) {
		Ext.getCmp('enterpriseNameMortgage').setValue(obj.companyMainId);
		Ext.getCmp('enterpriseNameMortgage').setRawValue(obj.corName) ;
	}
	
	function selectSlPersonStockUpdate1(obj){
		Ext.getCmp('personNameMortgage').setValue(obj.personMainId);
		Ext.getCmp('personNameMortgage').setRawValue(obj.name) ;
	}
	
	function setenterpriseNameMortgageUpdate(enterpriseId){
		Ext.Ajax.request({
			url : __ctxPath +'/creditFlow/customer/enterprise/ajaxSeeEnterprise.do',
			method : 'POST',
			success : function(response,request) {
				var objEnStockUpdate = Ext.util.JSON.decode(response.responseText);
				var enObjectStockUpdate = objEnStockUpdate.data;
				var cciaa;
				var registerstartdate;
				var registermoney;
				var legalpersonid;
				var legalperson;
				var hangyetypevalue;
				var hangyetype;
				
				if(businessType=='Financing'){
					cciaa = enObjectStockUpdate.businessCode;
					registerstartdate = enObjectStockUpdate.registerStartDate;
					registermoney = enObjectStockUpdate.registerMoney;
					legalpersonid = enObjectStockUpdate.personMainId;
					legalperson = enObjectStockUpdate.lawName;
					hangyetypevalue = enObjectStockUpdate.hangyeTypeValue;
					hangyetype = enObjectStockUpdate.hangyeType;
				}else{
					cciaa = enObjectStockUpdate.cciaa;
					registerstartdate = enObjectStockUpdate.registerstartdate;
					registermoney = enObjectStockUpdate.registermoney;
					legalpersonid = enObjectStockUpdate.legalpersonid;
					legalperson = enObjectStockUpdate.legalperson;
					hangyetypevalue = enObjectStockUpdate.hangyetypevalue;
					hangyetype = enObjectStockUpdate.hangyetype;
				}
				Ext.getCmp('cciaa_enStockUpdate').setValue(cciaa);//营业执照号码
				Ext.getCmp('registerdate_enStockUpdate').setValue(registerstartdate);//注册时间
				Ext.getCmp('registermoney_enStockUpdate').setValue(registermoney);//注册资本
				Ext.getCmp('legalperson_enStockUpdate').setValue(legalpersonid) ;//法人代表id
				Ext.getCmp('legalperson_enStockUpdate').setRawValue(legalperson);//法人代表name
				Ext.getCmp('legalpersonid_enStockUpdate').setValue(legalpersonid);//传后台的参数
				
				Ext.getCmp('hangyeTypeValue_update').setValue(hangyetypevalue) ;//行业类型值
				Ext.getCmp('hangyeTypeId_update').setValue(hangyetype);//行业类型id
			},
			failure : function(response) {					
					Ext.Msg.alert('状态','操作失败，请重试');		
			},
			params: { id: enterpriseId,businessType : businessType }
		});	
	};
	
	//选择目标公司名称部分selectSlCompanyTargetStockUpdate
	function setEnterpriseNameStockUpdate(obj) {
		Ext.getCmp('targetEnterpriseName').setValue(obj.id);
		Ext.getCmp('targetEnterpriseName').setRawValue(obj.enterprisename) ;
		setenterpriseNameMortgageUpdate(obj.id);
	}
	
	//选择目标公司名称部分-我方企业主体
	function selectSlCompanyTargetStockUpdate(obj) {
		Ext.getCmp('targetEnterpriseName').setValue(obj.companyMainId);
		Ext.getCmp('targetEnterpriseName').setRawValue(obj.corName) ;
		setenterpriseNameMortgageUpdate(obj.companyMainId);
	}
	
	//法人代表
	var selectLegalpersonStockUpdate = function(obj){
		Ext.getCmp('legalperson_enStockUpdate').setValue(obj.id) ;
		Ext.getCmp('legalperson_enStockUpdate').setRawValue(obj.name);
		Ext.getCmp('legalpersonid_enStockUpdate').setValue(obj.id);
	}
	
	//法人代表-我方个人主体
	var selectSlPersonStockUpdate = function(obj){
		Ext.getCmp('legalperson_enStockUpdate').setValue(obj.personMainId) ;
		Ext.getCmp('legalperson_enStockUpdate').setRawValue(obj.name);
		Ext.getCmp('legalpersonid_enStockUpdate').setValue(obj.personMainId);
	}
	
	Ext.Ajax.request({
		url : __ctxPath +'/credit/mortgage/seeStockownershipForUpdate.do',
		method : 'POST',
		success : function(response, request) {
			obj = Ext.util.JSON.decode(response.responseText);
			if(obj.success==true){
				showStockownershipInformation(obj.data) ;
				Ext.getCmp('hangyeTypeValue_update').setValue(obj.data.vProjMortStockOwnerShip.hangyeTypeValue) ;//行业类型值
				Ext.getCmp('hangyeTypeId_update').setValue(obj.data.vProjMortStockOwnerShip.hangyeType);//行业类型id
				//var enterpriseId = obj.data.vProjMortStockOwnerShip.corporationname;
				/*Ext.Ajax.request({
					url : __ctxPath +'/credit/customer/enterprise/ajaxSeeEnterprise.do',
					method : 'POST',
					success : function(response, request) {
						objEnterprise = Ext.util.JSON.decode(response.responseText);
						var enterpriseDataStock = objEnterprise.data;
						if(objEnterprise.success==true){
							
						}else{
							Ext.Msg.alert('状态', objEnterprise.msg);
						}
					},
					failure : function(response) {
						Ext.Msg.alert('状态', '操作失败，请重试');
					},
					params : {
						id : enterpriseId
					}
				});*/
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