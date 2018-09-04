

Ext.define('htmobile.customer.person.menudetail.personSFZphoto', {
    extend: 'Ext.Panel',
    
    name: 'personSFZphoto',

    constructor: function (config) {
    	config = config || {};
    	var data=config.data;
    	var personSFZZUrl=__ctxPath+"/"+((typeof(data) == "undefined" ||Ext.isEmpty(data.personSFZZUrl))?"/images/nopic.jpg":data.personSFZZUrl);
    	var personSFZFUrl=__ctxPath+"/"+((typeof(data) == "undefined" ||Ext.isEmpty(data.personSFZFUrl))?"/images/nopic.jpg":data.personSFZFUrl);
    	Ext.apply(config,{
		    fullscreen: true,
		    title:"身份证扫描件",
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
		                        html:'<div>身份证正面</div><img src="'
														+personSFZZUrl
														+ '" width="100%" height="50%"/>'
		                    },
		                    {
		                        html:'<div>身份证反面</div><img src="'
														+personSFZFUrl
														+ '" width="100%" height="50%"/>'
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
