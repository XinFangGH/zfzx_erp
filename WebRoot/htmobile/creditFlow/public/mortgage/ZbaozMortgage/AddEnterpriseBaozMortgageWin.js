

Ext.define('htmobile.creditFlow.public.mortgage.ZbaozMortgage.AddEnterpriseBaozMortgageWin', {
    extend: 'Ext.form.Panel',
    
    name: 'AddEnterpriseBaozMortgageWin',

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
    	this.cellphone = config.cellphone;
    	this.personId=config.personId;
    	this.telephone=config.telephone;
    	this.enterpriseId=config.enterpriseId;
    	this.legalpersonid=config.legalpersonid;
    	this.legalperson=config.legalperson;
    	this.cciaa=config.cciaa;
    	this.area=config.area;
    //	 this.data=config.data;
    	Ext.apply(config,{
		    fullscreen: true,
		    title:"<span style='font-size:16px;'>保证担保</span>",
		    flex:1,
		    scrollable:{
		    	direction: 'vertical'
		    },
		    items: [
		            {
		                xtype: 'fieldset',
		                  flex:1,
		                 title :'<span style="color:#000000;">查看<无限一般责任-公司>详细信息</span>',
		                defaults:{
		                	xtype: 'textfield',
		                	readOnly: this.readOnly,
		                	width:"100%",
		                	labelAlign:"top"
		                	
		                },
		                items: [
		                	{    xtype:"hiddenfield",
		                        label:'<div class="fieldlabel">担保类型:</div>',
		                        name : 'procreditMortgage.assuretypeid',
		                        value:  '606'
		                    }
		                	,
		                	{    xtype:"hiddenfield",
		                        name : 'person.id',
		                        value:  this.legalpersonid
		                    }
		                	,
		                	{    xtype:"hiddenfield",
		                        label:'<div class="fieldlabel">反担保物名称:</div>',
		                        name : 'procreditMortgage.mortgagename',
		                        value:  this.customerEnterpriseName
		                    }
		                	,
		                    {    xtype:"hiddenfield",
		                        label:'<div class="fieldlabel">项目ID:</div>',
		                        name : 'procreditMortgage.projid',
		                        value:  this.projectId
		                    }
		                	,
		                    {    xtype:"hiddenfield",
		                        label:'<div class="fieldlabel">保证人:</div>',
		                        name : 'customerPersonName',
		                        value:  this.customerEnterpriseName
		                    }
		                    ,
		                    {
		                    	 xtype:"hiddenfield",
		                        label: '<div class="fieldlabel">业务类别:</div>',
		                        value:  this.businessType,
		                        name : 'procreditMortgage.businessType'
		                    }
		                	,
		                    {    xtype:"hiddenfield",
		                        label:  '<div class="fieldlabel">备注:</div>',
		                        name : 'procreditMortgage.mortgageRemarks',
		                        value: this.mortgageRemarks
		                    }
		                	,
		                	{
		                    	xtype:"hiddenfield",
		                        label: '<div class="fieldlabel">保证人类型:</div>',
		                        value:  this.personType,
		                        name : 'procreditMortgage.personType'
		                    }
		                    ,
		                    {    
		                    	 xtype:"hiddenfield",
		                         name : 'procreditMortgage.mortgagepersontypeforvalue',
		                         value: this.assuremodeid=='607'?'无限一般责任-公司':'无限连带责任-公司'
		                    }
		                    ,
		                    {    
		                    	 xtype:"hiddenfield",
		                         name : 'procreditMortgage.mortgagenametypeid',
		                         value: '3'
		                    }
		                	,
		                	{
		                    	 xtype:"hiddenfield",
		                        label: '<div class="fieldlabel">保证方式:</div>',
		                        value:  this.assuremodeid,
		                        name : 'procreditMortgage.assuremodeid'
		                    }
		                	,
		                    {    
		                    	 xtype:"hiddenfield",
		                         label: '<div class="fieldlabel">所有权人与借款人的关系:</div>',
		                         name : 'procreditMortgage.relation',
		                         value: this.relation
		                    }
		                    ,
		                	{    xtype:"hiddenfield",
		                        name : 'legalpersonid',
		                        value: this.legalpersonid
		                    }
		                	,
		                    {
		                        label: '<div class="fieldlabel">营业执照号码:</div>',
		                        name : 'enterprise.cciaa',
		                        value: this.cciaa
		                    }
		                	,
		                    {
		                        label: '<div class="fieldlabel">法人代表:</div>',
		                        name : 'enterpriselegal.person',
		                        id:'enterpriselegalperson',
		                        value:this.enterpriselegalperson,
		                        listeners : {
								scope:this,
		                        'focus' : function(f) {
		                        	 mobileNavi.push(Ext.create('htmobile.public.SelectPersonlist',{
									   callback:function(data){
									   	var enterpriselegalperson = Ext.getCmp("enterpriselegalperson");
									    enterpriselegalperson.setValue(data.name);
									   }}));
								}
		                    }
		                    }
		                	,
		                    {
		                        label:  '<div class="fieldlabel">联系电话:</div>',
		                        name : 'enterprise.telephone',
		                        value: this.telephone
		                    }
		                    ,
		                    {
		                        label: '<div class="fieldlabel">法人代表电话:</div>',
		                        name : 'person.cellphone',
		                		value: this.cellphone
		                    }
		                    ,
		                    {
		                        label:  '<div class="fieldlabel">公司地址:</div>',
		                        name : 'enterprise.area',
		                        value: this.area
		                    }
		                    ,
		                    {
		                        label: '<div class="fieldlabel">主营业务:</div>',
		                        name : 'procreditMortgageEnterprise.business'
		                    }
		                	,
		                    {
		                        label:  '<div class="fieldlabel">主要资产:</div>',
		                        name : 'procreditMortgageEnterprise.assets'
		                    }
		                	,
		                    {
		                        label:  '<div class="fieldlabel">资产价值:</div>',
		                        name : 'procreditMortgageEnterprise.netassets'
		                    }
		                    ,
		                    {
		                        label: '<div class="fieldlabel">月营业额:</div>',
		                        name : 'procreditMortgageEnterprise.monthlyIncome'
		                    }
		                	,
					        {
					        	
					            xtype: this.readOnly==true?'hiddenfield':'button',
					            name: 'submit',
					            text:'保存',
					            cls : 'buttonCls',
					            scope:this,
					            handler:this.formSubmit
					        }
		                ]
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
       	 loginForm.submit({
            url:__ctxPath+'/credit/mortgage/addMortgageOfBZ.do',
        	method : 'POST',
        	params:{
        	 customerEnterpriseName:this.enterpriseId,
        	 projectId:this.projectId
        	  
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
