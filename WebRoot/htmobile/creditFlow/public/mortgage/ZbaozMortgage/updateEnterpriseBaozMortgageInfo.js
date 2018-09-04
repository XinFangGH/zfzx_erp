

Ext.define('htmobile.creditFlow.public.mortgage.ZbaozMortgage.updateEnterpriseBaozMortgageInfo', {
    extend: 'Ext.form.Panel',
    
    name: 'updateEnterpriseBaozMortgageInfo',

    constructor: function (config) {
    	config = config || {};
    	this.MortgageData=config.MortgageData;
    	this.projectId = config.projectId;
    	this.type=config.type;
    	this.readOnly=config.readOnly;
    	Ext.apply(config,{
		    fullscreen: true,
		    title:"<span style='font-size:16px;'>保证担保</span>",
		    flex:1,
		    scrollable:{
		    	direction: 'vertical'
		    },
		    items : [{
						xtype : 'fieldset',
						flex : 1,
						title : '<span style="color:#000000;">保证担保基础信息</span>',
						defaults : {
							xtype : 'textfield',
							readOnly : this.readOnly,
							width : "100%",
							labelAlign : "left"
	
						},
						items : [{
							xtype : "hiddenfield",
							name : 'procreditMortgage.id',
							value : this.MortgageData.vProcreditDictionary.id
						}, {
							xtype : "hiddenfield",
							label : '担保类型',
							name : 'procreditMortgage.assuretypeid',
							value : this.MortgageData.vProcreditDictionary.assuretypeid
						}, {
							label : '保证人类型',
							readOnly : true,
							nodeKey : 'syrlx',
							xtype : "dickeycombo",
							name : 'procreditMortgage.personType',
							value : this.MortgageData.vProcreditDictionary.personTypeValue
						}, {
							label : '保证方式',
							xtype : "dickeycombo",
							nodeKey : 'bzfs',
							name : 'procreditMortgage.assuremodeid',
							value : this.MortgageData.vProcreditDictionary.assuremodeidValue
						}, {
							label : '保证人',
							name : 'procreditMortgage.mortgagename',
							value : this.MortgageData.vProcreditDictionary.assureofnameEnterOrPerson,
							listeners : {
								scope : this,
								'focus' : function(f) {
									mobileNavi
											.push(Ext
													.create(
															'htmobile.public.SelectEnterpriselist',
															{
																callback : function(
																		data) {
																	var enterpriseId = Ext
																			.getCmp("enterprise.id");
																	enterpriseId
																			.setValue(data.id);
																}
															}));
								}
							}
						}, {
							label : '备注',
							xtype : 'textareafield',
							name : 'procreditMortgage.mortgageRemarks',
							value : this.MortgageData.vProcreditDictionary.mortgageRemarks
						}, {
							label : '所有权人与借款人的关系',
							name : 'procreditMortgage.relation',
							value : this.MortgageData.vProcreditDictionary.relation
						}, {
							xtype : "hiddenfield",
							name : 'customerEnterpriseName',
							value : this.MortgageData.vProcreditDictionary.assureofname
						}
						]
					}, {
						xtype : 'fieldset',
						flex : 1,
						title:`修改<${this.MortgageData.vProcreditDictionary.assuremodeidValue}-公司>详细信息`,
						defaults : {
							xtype : 'textfield',
							readOnly : this.readOnly,
							width : "100%",
							labelAlign : "left"
						},
						items : [{
							label : 'ID',
							xtype : "hiddenfield",
							name : 'enterprise.id',
							value : this.MortgageData.vProcreditDictionary.assureofname
						}, {
							label : '营业执照号码',
							name : 'enterprise.cciaa',
							value : this.MortgageData.vProjMortCompany.licensenumber
						}, {
							label : '法人代表',
							name : 'enterprise.legalperson',
							value : this.MortgageData.vProjMortCompany.corporate,
							id : 'enterpriselegalperson',
							listeners : {
								scope : this,
								'focus' : function(f) {
									mobileNavi
											.push(Ext
													.create(
															'htmobile.public.SelectPersonlist',
															{
																callback : function(
																		data) {
																	var enterpriselegalperson = Ext
																			.getCmp("enterpriselegalperson");
																	enterpriselegalperson
																			.setValue(data.name);
																}
															}));
								}
							}
						}, {
							label : '联系电话',
							name : 'enterprise.telephone',
							value : this.MortgageData.vProjMortCompany.telephone
						}, {
							xtype : "hiddenfield",
							name : 'person.id',
							value : this.MortgageData.vProjMortCompany.legalpersonid
						}, {
							label : '法人代表电话',
							name : 'person.cellphone',
							value : this.MortgageData.vProjMortCompany.corporatetel
						}, {
							label : '公司地址',
							name : 'enterprise.area',
							value : this.MortgageData.vProjMortCompany.registeraddress
						}, {
							label : '主营业务',
							name : 'procreditMortgageEnterprise.business',
							value : this.MortgageData.vProjMortCompany.business
						}, {
							label : '资产价值',
							name : 'procreditMortgageEnterprise.netassets',
							value : this.MortgageData.vProjMortCompany.netassets
						}, {
							label : '主要资产',
							name : 'procreditMortgageEnterprise.assets',
							value : this.MortgageData.vProjMortCompany.assets
						}, {
							label : '月营业额',
							name : 'procreditMortgageEnterprise.monthlyIncome',
							value : this.MortgageData.vProjMortCompany.monthlyIncome
						}, {
							xtype : this.readOnly == true
									? 'hiddenfield'
									: 'button',
							name : 'submit',
							text : '保存',
							cls : 'submit-button',
							scope : this,
							handler : this.formSubmit
						}]
					}]
    	});
    	
    	this.callParent([config]);
    },
    detail:function(){
       mobileNavi.push(
		Ext.create('htmobile.customer.person.PersonMenu',{
			        data:this.data
		        	})
		    	);
    },
    formSubmit:function(){
		 var loginForm=this;
       	 loginForm.submit({
            url:__ctxPath+'/credit/mortgage/addMortgageOfBZ.do',
        	method : 'POST',
        	params:{
        	 legalpersonid:this.MortgageData.vProjMortCompany.legalpersonid
        	},
        	success : function(form,action,response) {
		        	var obj = Ext.util.JSON.decode(response);
		        	if(obj.success==true){
		        	
		        		  Ext.Msg.alert('','保存成功');
		        		    mobileNavi.pop();
		        		  var object= Ext.getCmp("BaozMortgageViewList");
		        		  object.store.loadPage(1);
		        	}else{
		        		  Ext.Msg.alert('','保存失败');
		        		
		        	}
        	}
		});}

});
