

Ext.define('htmobile.creditFlow.public.mortgage.DZYAndBZTab', {
	extend: 'Ext.TabPanel',
    
    name: 'DZYAndBZTab',

    constructor: function (config) {
	    var BaozMortgageViewList=Ext.create('htmobile.creditFlow.public.mortgage.BaozMortgageViewList',{
	                    projectId : config.projectId,
						businessType : config.businessType}); 
	    var DZYMortgageViewList=Ext.create('htmobile.creditFlow.public.mortgage.DZYMortgageViewList',{
	                       projId : config.projectId,
							businessType :config.businessType// 小贷
	    });
	    Ext.apply(config,{
	    	
        	title:'担保措施',
            layoutOnTabChange: true,
            items: [
	{
	            	title: '<div style="font-size:10px;">抵质押担保</div>',
	                items:[{
	    	            xtype: 'fieldset',
	    	            items:DZYMortgageViewList
	    	        }]
	            },
	            {
	            	title: '<div style="font-size:10px;">保证担保</div>',
	                items:[{
	    	            xtype: 'fieldset',
	    	            items:BaozMortgageViewList
	    	        }]
	            }
            ]
        });
    
        this.callParent([config]);
	}

});

