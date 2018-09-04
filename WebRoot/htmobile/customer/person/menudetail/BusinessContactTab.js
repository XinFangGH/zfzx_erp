

Ext.define('htmobile.customer.person.menudetail.BusinessContactTab', {
	extend: 'Ext.TabPanel',
    
    name: 'BusinessContactTab',

    constructor: function (config) {
    	var data=config.data;
    	var type=config.type;
	    var BusinessContactList=Ext.create('htmobile.customer.person.menudetail.BusinessContactList',{data:data,type:type}); 
	//    var BusinessCcreditLoanHistoryList=Ext.create('htmobile.customer.person.menudetail.BusinessCcreditLoanHistoryList',{data:data}); 
	    var BusinessCLegalPersonList=Ext.create('htmobile.customer.person.menudetail.BusinessCLegalPersonList',{data:data}); 
	    var BusinessCPersonMortgageList=Ext.create('htmobile.customer.person.menudetail.BusinessCPersonMortgageList',{data:data,type:type}); 
		config = config || {};
	    Ext.apply(config,{
	    	
        	title:'业务往来',
            layoutOnTabChange: true,
            items: type=="person_customer"?[
	            {
	            	title: '<div style="font-size:10px;">业务往来</div>',
	                items:[{
	    	            xtype: 'fieldset',
	    	            items:BusinessContactList
	    	        }]
	            },
	            {
	            	title: '<div style="font-size:10px;">作为第三方保证</div>',
	                items:[{
	    	            xtype: 'fieldset',
	    	            items:BusinessCPersonMortgageList
	    	        }]
	            },
	            {
	            	title: '<div style="font-size:10px;">作为法人</div>',
	                items:[{
	    	            xtype: 'fieldset',
	    	            items:BusinessCLegalPersonList
	    	            
	    	        }]
	            }/*,
	           {
	            	title: '<div style="font-size:10px;">信贷历史记录</div>',
	                items:[{
	    	            xtype: 'fieldset',
	    	            items:BusinessCcreditLoanHistoryList
	    	        }]
	            }*/
            ]:[
	            {
	            	title: '<div style="font-size:10px;">业务往来</div>',
	                items:[{
	    	            xtype: 'fieldset',
	    	            items:BusinessContactList
	    	        }]
	            },
	            {
	            	title: '<div style="font-size:10px;">作为第三方保证</div>',
	                items:[{
	    	            xtype: 'fieldset',
	    	            items:BusinessCPersonMortgageList
	    	        }]
	            }
            ]
        });
    
        this.callParent([config]);
	}

});

