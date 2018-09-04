

Ext.define('htmobile.customer.person.menudetail.photo', {
    extend: 'Ext.Panel',
    
    name: 'photo',

    constructor: function (config) {
    	config = config || {};
    	var data=config.data;
    	var personPhotoUrl=__ctxPath+"/"+((typeof(data) == "undefined" ||Ext.isEmpty(data.personPhotoUrl))?"/images/nopic.jpg":data.personPhotoUrl);
    	Ext.apply(config,{
		    fullscreen: true,
		    title:"个人照片",
		    scrollable:{
		    	direction: 'vertical'
		    },
		    items: [
		            {
		                xtype: 'fieldset',
		                defaults:{
		                	xtype: 'panel',
		                	readOnly: true,
		                	width:"100%",
		                	labelAlign:"top"
		                	
		                },
		                items: [
		                    {
		                        html:'<img src="'
														+ personPhotoUrl
														+ '" width="100%" height="100%"/>'
		                    }
		                ]
		            }
		        ]
    	});

    	this.callParent([config]);
    	
    },
    detail:function(){
       mobileNavi.push(
		Ext.create('htmobile.customer.person.PersonDetailMenu',{
		        	})
		    	);
    }

});
