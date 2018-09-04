/**
 * @author:
 * @class SlCompanyMainView
 * @extends Ext.Panel
 * @description [SlCompanyMain]管理
 * @company 北京互融时代软件有限公司
 * @createtime:
 */
CompanyForm = Ext.extend(Ext.Panel, {
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		// 初始化组件
		this.initUIComponents();
		// 调用父类构造
		var businessType=this.businessType
		CompanyForm.superclass.constructor.call(this, {
			anchor : '95%',
			items : [{
				layout : 'column',
				xtype : 'fieldset',
				title : '填写<无限连带责任-公司>详细信息',
				collapsible : true,
				autoHeight : true,
				anchor : '95%',
				items : [{
							columnWidth : .5,
							layout : 'form',
							labelWidth : 100,
							defaults : {
								xtype : 'numberfield',
								anchor : '100%'
							},
							items : [{
										id : 'cciaa_en',
										xtype : 'textfield',
										fieldLabel : '营业执照号码',
										maxLength : 50,
										maxLengthText : '最大输入长度50',
										name : 'enterprise.cciaa'
									},{
										id : 'legalperson_en',
										fieldLabel : '法人代表',
										maxLength : 50,
										maxLengthText : '最大输入长度50',
										xtype : "combo",
										triggerClass : 'x-form-search-trigger',
										onTriggerClick : function(e) {
											// selectPWName(selectLegalpersonCompany);
											if (businessType == 'Financing') {
												selectSlPersonMain(selectSlPersonFaren);
											} else {
												selectPWName(selectLegalpersonCompany);
											}
										}
									}]
						}, {
							columnWidth : .5,
							layout : 'form',
							labelWidth : 110,
							defaults : {
								xtype : 'textfield',
								anchor:'100%'
							},
							items : [ {
								id:'telephone',
							    fieldLabel : '联系电话',
							    name:'enterprise.telephone'
							},{
								id : 'phone_en',
								fieldLabel : '法人代表电话',
								maxLength : 50,
								maxLengthText : '最大输入长度50',
								name : 'person.cellphone'
							}, {
								id : 'legalpersonid_en',
								name : 'legalpersonid',
								xtype : 'hidden'
							}]
						},{
							columnWidth : 1,
							labelWidth : 100,
							layout : 'form',
							defaults : {
								anchor:'100%'
							},
							items : [{
								id : 'address_en',
								xtype : 'textfield',
								fieldLabel : '公司地址',
								maxLength : 50,
								maxLengthText : '最大输入长度50',
								name : 'enterprise.area'
							}]
						},{
						   columnWidth :1,
						   layout :'form',
						   items:[{
						       xtype:'textfield',
						       fieldLabel:'主营业务',
						       anchor:'100%',
						       name : 'procreditMortgageEnterprise.business'
						   }]
						},{
						   columnWidth:.6,
						   layout:'form',
						   items : [{
						       xtype:'textfield',
						       fieldLabel:'主要资产',
						       anchor:'100%',
						       name:'procreditMortgageEnterprise.assets'
						   }]
						},{
						   columnWidth:.4,
						   layout:'form',
						   items:[{
						       xtype:'numberfield',
						       fieldLabel : '资产价值.万元',
							   maxLength : 23,
							   maxLengthText : '最大输入长度23',
							   anchor:'100%',
							   name : 'procreditMortgageEnterprise.netassets'
						   }]
						},{
						   	columnWidth:1,
						   	layout:'form',
						   	items:[{
						   	    xtype:'textfield',
						   	    fieldLabel:'月营业额',
						   	    anchor:'100%',
						   	    name:'procreditMortgageEnterprise.monthlyIncome'
						   	}]
						}]
			}]
		});
		if(null!=this.id && null!=this.type){
			this.loadData({
				url : __ctxPath +'/credit/mortgage/getMortgageByType.do?mortgageid='
						+ this.id+'&typeid='+this.type,
				root : 'data',
				preName : ['procreditMortgageEnterprise','enterpriseView','tel'],
				scope : this,
				success : function(resp, options) {
					var obj=Ext.util.JSON.decode(resp.responseText)
					if(businessType=='Financing'){
						Ext.getCmp('address_en').setValue(obj.data.enterpriseView.sjjyAddress);//注册地址
						Ext.getCmp('cciaa_en').setValue(obj.data.enterpriseView.businessCode);//营业执照号码
						Ext.getCmp('legalperson_en').setValue(obj.data.enterpriseView.personMainId);//法人代表id
						Ext.getCmp('legalperson_en').setRawValue(obj.data.enterpriseView.lawName);//法人代表name
						Ext.getCmp('legalpersonid_en').setValue(obj.data.enterpriseView.personMainId);
						Ext.getCmp('phone_en').setValue(obj.data.tel);//法人代表电话
						Ext.getCmp('telephone').setValue(obj.data.enterpriseView.tel)

					}else{
						Ext.getCmp('address_en').setValue(obj.data.enterpriseView.area);//注册地址
						Ext.getCmp('cciaa_en').setValue(obj.data.enterpriseView.cciaa);//营业执照号码
						Ext.getCmp('legalperson_en').setValue(obj.data.enterpriseView.legalpersonid);//法人代表id
						Ext.getCmp('legalperson_en').setRawValue(obj.data.enterpriseView.legalperson);//法人代表name
						Ext.getCmp('legalpersonid_en').setValue(obj.data.enterpriseView.legalpersonid);
						Ext.getCmp('phone_en').setValue(obj.data.tel);//法人代表电话
						Ext.getCmp('telephone').setValue(obj.data.enterpriseView.telephone)
					}
				}
			});
		}
	},// end of constructor
	// 初始化组件
	initUIComponents : function() {
		
		var businessType=this.businessType
		function setCustomerName(address,cciaa,legalpersonid,legalperson){
		var phone;
		Ext.getCmp('address_en').setValue(address);//注册地址
		Ext.getCmp('cciaa_en').setValue(cciaa);//营业执照号码
		Ext.getCmp('legalperson_en').setValue(legalpersonid);//法人代表id
		Ext.getCmp('legalperson_en').setRawValue(legalperson);//法人代表name
		Ext.getCmp('legalpersonid_en').setValue(legalpersonid);
		
		Ext.Ajax.request({
			url : __ctxPath +'/creditFlow/customer/person/seeInfoPerson.do',
			method : 'POST',
			success : function(response,request) {
				var objPersonCompany = Ext.util.JSON.decode(response.responseText);
				var personObjectCompany = objPersonCompany.data;
				if(businessType=='Financing'){
					if(personObjectCompany.linktel != null || personObjectCompany.linktel != ""){
						phone = personObjectCompany.linktel;
					}else{
						phone = "";
					}
				}else{
					if(personObjectCompany.cellphone != null || personObjectCompany.cellphone != ""){
						phone = personObjectCompany.cellphone;
					}else{
						phone = "";
					}
				}
				Ext.getCmp('phone_en').setValue(phone);//法人代表电话
			},
			failure : function(response) {					
					Ext.Msg.alert('状态','操作失败，请重试');		
			},
			params: { id: legalpersonid,businessType: businessType }
		});
		/*if(businessType=='Financing'){
			phone = tel;
			alert("tel=="+phone);
			Ext.getCmp('phone_en').setValue(phone);//法人代表电话
		}else{
			
		}*/
	};
	
	function selectCustomer(obj) {
		Ext.getCmp('enterpriseNameMortgage').setValue(obj.id);
		Ext.getCmp('enterpriseNameMortgage').setRawValue(obj.enterprisename) ;
		setCustomerName(obj.address,obj.cciaa,obj.legalpersonid,obj.legalperson);
	}
	
	function selectSlCompanyCom(obj) {
		Ext.getCmp('enterpriseNameMortgage').setValue(obj.companyMainId);
		Ext.getCmp('enterpriseNameMortgage').setRawValue(obj.corName) ;
		setCustomerName(obj.messageAddress,obj.businessCode,obj.personMainId,obj.lawName);
	}
	
	//法人代表
	selectLegalpersonCompany = function(obj){
		Ext.getCmp('legalperson_en').setValue(obj.id) ;
		Ext.getCmp('legalperson_en').setRawValue(obj.name);
		Ext.getCmp('phone_en').setValue(obj.cellphone);//法人代表电话
		Ext.getCmp('legalpersonid_en').setValue(obj.id);
	}
	
	selectSlPersonFaren = function(obj){
		Ext.getCmp('legalperson_en').setValue(obj.personMainId) ;
		Ext.getCmp('legalperson_en').setRawValue(obj.name);
		Ext.getCmp('phone_en').setValue(obj.linktel);//法人代表电话
		Ext.getCmp('legalpersonid_en').setValue(obj.personMainId);
	}
	}
});
