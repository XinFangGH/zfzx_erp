

Ext.define('htmobile.creditFlow.public.mortgage.ZbaozMortgage.updatePersonBaozMortgageInfo', {
    extend: 'Ext.form.Panel',
    
    name: 'updatePersonBaozMortgageInfo',

    constructor: function (config) {
    	config = config || {};
    	this.MortgageData=config.MortgageData;
    	this.type=config.type;
    	this.readOnly=config.readOnly;
    //	 this.data=config.data;
    	Ext.apply(config,{
		    fullscreen: true,
		    title:"保证担保",
		    flex:1,
		    scrollable:{
		    	direction: 'vertical'
		    },
		    items : [{
						xtype : 'fieldset',
						flex : 1,
						title : '保证担保基础信息',
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
															'htmobile.public.SelectPersonlist',
															{
																callback : function(
																		data) {
																	var cardtype = Ext
																			.getCmp("personcardtype");
																	var personId = Ext
																			.getCmp("personid");
																	personId
																			.setValue(data.id);
																	cardtype
																			.setValue(data.cardtype);
																}
															}));
								}
							}
						}, {
							xtype : "hiddenfield",
							label : '身份类型',
							id : 'personcardtype',
							name : 'person.cardtype',
							value : this.MortgageData.vProjMortDutyPerson.cardtype
						}, {
							xtype : "hiddenfield",
							label : '个人id',
							id : 'personid',
							name : 'person.id',
							value : this.MortgageData.vProcreditDictionary.assureofname
						}, {
							xtype : "hiddenfield",
							label : '担保类型',
							name : 'procreditMortgage.assuretypeid',
							value : this.MortgageData.vProcreditDictionary.assuretypeid
						}, {
							label : '备注',
							xtype : 'textareafield',
							name : 'procreditMortgage.mortgageRemarks',
							value : this.MortgageData.vProcreditDictionary.mortgageRemarks
						}, {
							label : '所有权人与借款人的关系',
							name : 'procreditMortgage.relation',
							value : this.MortgageData.vProcreditDictionary.relation
						}]
					}, {
						xtype : 'fieldset',
						flex : 1,
						title:`查看<${this.MortgageData.vProcreditDictionary.assuremodeidValue}-个人>详细信息`,
						defaults : {
							xtype : 'textfield',
							readOnly : this.readOnly,
							width : "100%",
							labelAlign : "left"
						},
						items : [{
							label : '证件号码',
							name : 'person.cardnumber',
							value : this.MortgageData.vProjMortDutyPerson.idcard
						}, {
							label : '家庭住址',
							name : 'person.familyaddress',
							value : this.MortgageData.vProjMortDutyPerson.zhusuo
						}, {
							label : '家庭电话',
							name : 'person.cellphone',
							value : this.MortgageData.vProjMortDutyPerson.phone
						}, {
							label : '是否为公务员',
							name : 'person.ispublicservant',
							xtype : 'togglefield',
							value : this.MortgageData.vProjMortDutyPerson.isCivilServant == true
									? "1"
									: "0"
						}, {
							label : '主营业务或职务',
							name : 'procreditMortgagePerson.business',
							value : this.MortgageData.vProjMortDutyPerson.business
						}, {
							label : '主要资产',
							name : 'procreditMortgagePerson.assets',
							value : this.MortgageData.vProjMortDutyPerson.assets
						}, {
							label : '资产价值',
							name : 'procreditMortgagePerson.assetvalue',
							value : this.MortgageData.vProjMortDutyPerson.assetvalue
						}, {
							label : '月收入',
							name : 'procreditMortgagePerson.monthlyIncome',
							value : this.MortgageData.vProjMortDutyPerson.monthlyIncome
						}, {
							xtype : "hiddenfield",
							name : 'customerEnterpriseName',
							value : this.MortgageData.vProcreditDictionary.assureofname
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
       mobileNavi.push(Ext.create('htmobile.customer.person.PersonMenu',{
			        data:this.data
		        	})
		    	);
    },
    formSubmit:function(){
		 var loginForm = this;
		 
		 var cardtype=loginForm.getCmpByName('person.cardtype').getValue();
		 var cardnumber=loginForm.getCmpByName("person.cardnumber").getValue(); 
		  if(Ext.isEmpty(cardnumber)){
		    Ext.Msg.alert('','证件号码不能为空');
			return;
		  }
       	 loginForm.submit({
            url:__ctxPath+'/credit/mortgage/addMortgageOfBZ.do',
        	method : 'POST',
        	params:{
        	 cardtype:cardtype
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
