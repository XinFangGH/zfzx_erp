

Ext.define('htmobile.InformationCollection.person.bigMaterialsPhoto', {
    extend: 'Ext.Panel',
    
    name: 'personSFZphoto',

    constructor: function (config) {
    	config = config || {};
    	var filepath=config.filepath;
    	var filepathurl=__ctxPath+"/"+((typeof(filepath) == "undefined" ||Ext.isEmpty(filepath))?"/images/nopic.jpg":filepath);
    	Ext.apply(config,{
		    fullscreen: true,
		    title:"图片",
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
		                        html:'<div></div><img src="'
														+filepathurl
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
