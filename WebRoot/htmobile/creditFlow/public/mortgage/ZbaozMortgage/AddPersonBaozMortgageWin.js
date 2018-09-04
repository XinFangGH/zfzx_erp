

Ext.define('htmobile.creditFlow.public.mortgage.ZbaozMortgage.AddPersonBaozMortgageWin', {
    extend: 'Ext.form.Panel',
    name: 'AddPersonBaozMortgageWin',
    constructor: function (config) {
    	config = config || {};
    	this.businessType=config.businessType;
     	this.MortgageData=config.MortgageData;
    	this.customerEnterpriseName = config.customerEnterpriseName;
    	this.mortgageRemarks = config.mortgageRemarks;
    	this.personType = config.personType;
    	this.assuremodeid = config.assuremodeid;
    	this.relation=config.relation;
    	this.projectId = config.projectId;
    	this.readOnly = config.readOnly;
    	this.cardnumber = config.cardnumber;
    	this.cellphone = config.cellphone;
    	this.cardtype = config.cardtype;
    	this.personId=config.personId;
    	Ext.apply(config,{
    		style:'background-color:white;',
		    fullscreen: true,
		    title:"保证担保",
		    flex:1,
		    scrollable:{
		    	direction: 'vertical'
		    },
		    items : [{
						xtype : 'fieldset',
						flex : 1,
						title : '查看<无限连带责任-个人>详细信息',
						defaults : {
							xtype : 'textfield',
							readOnly : this.readOnly,
							width : "100%",
							labelAlign : "left"
						},
						items : [{
									xtype : "hiddenfield",
									label : '担保类型',
									name : 'procreditMortgage.assuretypeid',
									value : '606'
								}, {
									xtype : "hiddenfield",
									label : '反担保物名称',
									name : 'procreditMortgage.mortgagename',
									value : this.customerEnterpriseName
								}, {
									xtype : "hiddenfield",
									label : '项目ID',
									name : 'procreditMortgage.projid',
									value : this.projectId
								}, {
									xtype : "hiddenfield",
									label : '保证人',
									name : 'customerPersonName',
									value : this.customerEnterpriseName
								}, {
									xtype : "hiddenfield",
									label : '备注',
									name : 'procreditMortgage.mortgageRemarks',
									value : this.mortgageRemarks
								}, {
									xtype : "hiddenfield",
									label : '保证人类型',
									value : this.personType,
									name : 'procreditMortgage.personType'
								}, {
									xtype : "hiddenfield",
									label : '业务类别',
									value : this.businessType,
									name : 'procreditMortgage.businessType'
								}, {
									xtype : "hiddenfield",
									label : '保证方式',
									value : this.assuremodeid,
									name : 'procreditMortgage.assuremodeid'
								}, {
									xtype : "hiddenfield",
									label : '所有权人与借款人的关系',
									name : 'procreditMortgage.relation',
									value : this.relation
								}, {
									xtype : "hiddenfield",
									name : 'procreditMortgage.mortgagenametypeid',
									value : '4'
								}, {
									xtype : "hiddenfield",
									name : 'procreditMortgage.mortgagepersontypeforvalue',
									value : this.assuremodeid == '607'
											? '无限一般责任-个人'
											: '无限连带责任-个人'
								}, {
									xtype : 'textfield',
									label : '证件号码',
									name : 'person.cardnumber',
									value : this.cardnumber
								}, {
									label : '家庭住址',
									name : 'person.familyaddress'
								}, {
									label : '家庭电话',
									name : 'person.cellphone',
									value : this.cellphone
								}, {
									label : '是否为公务员',
									name : 'person.ispublicservant'

								}, {
									label : '主营业务或职务',
									name : 'procreditMortgagePerson.business'
								}, {
									label : '主要资产',
									name : 'procreditMortgagePerson.assets'
								}, {
									xtype : 'numberfield',
									label : '资产价值.万元',
									name : 'procreditMortgagePerson.assetvalue'
								}, {
									xtype : 'numberfield',
									label : '月营业额',
									name : 'procreditMortgagePerson.monthlyIncome'
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
		 var loginForm = this;
		 var cardnumber=loginForm.getCmpByName("person.cardnumber").getValue(); 
		  if(Ext.isEmpty(cardnumber)){
		    Ext.Msg.alert('','证件号码不能为空');
			return;
		  }
		  var assetvalue=loginForm.getCmpByName("procreditMortgagePerson.assetvalue").getValue(); 
		  if(Ext.isEmpty(assetvalue)){
		    Ext.Msg.alert('','资产价值不能为空');
			return;
		  }
		 var id=this.id;
		 var isCivilServant=loginForm.getCmpByName('person.ispublicservant').getValue();
       	 loginForm.submit({
            url:__ctxPath+'/credit/mortgage/addMortgageOfBZ.do',
        	method : 'POST',
        	params:{
        	 customerEnterpriseName:this.personId,
        	 projectId:this.projectId,
        	 cardtype:this.cardtype,
        	 'procreditMortgagePerson.isCivilServant':isCivilServant
        	},
        	success : function(form,action,response) {
		        	var obj = Ext.util.JSON.decode(response);
		        	if(obj.success==true){
		        	
		        		  Ext.Msg.alert('','保存成功');
		        		  mobileNavi.pop(2);
		        		  var object= Ext.getCmp("BaozMortgageViewList");
		        		  object.store.loadPage(1);
		        		  
		        	}else{
		        		  Ext.Msg.alert('','保存失败');
		        		
		        	}
        	}
		});}


});
