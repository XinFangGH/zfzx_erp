
//ExtUD.Ext.BidPlanFinanceInfo
Ext.define('hrmobile.public.myhome.main', {
    extend: 'Ext.Panel',
    name: 'BidPlanFinanceInfo',
    constructor: function (config) {
    	config = config || {};
    	this.data=config.result;
    	Ext.apply(config,{
    		width:"100%",
		    height:"100%",
		    fullscreen: true,
		    scrollable:{
		    	direction: 'vertical'
		    },
		    items: [
		            {
		                xtype: 'fieldset',
		                defaults:{
		                	xtype: 'textfield',
		                	readOnly: true,
		                	labelAlign:"top"
		                },
		                items: [{
		                        label: "<div class='fieldlabel'>本次招标金额(元):</div>",
		                        value: data.bidMoney
		                    },
		                    {
		                        label: "<div class='fieldlabel'>起息日期:</div>",
		                        value: data.startIntentDate
		                    },{
		                        label:"<div class='fieldlabel'>还款日期:</div>",
		                         value: data.endIntentDate
		                    }
		                   
		          
		          ]}]
    	});

  

    	this.callParent([config]);
    	
    },
    detail:function(){
       mobileNavi.push(
		Ext.create('htmobile.customer.person.PersonMenu',{
			        data:this.data
		        	})
		    	);
    }

});
