
Ext.define('htmobile.customer.DanganConfig', {
    extend: 'Ext.form.Panel',
    name: 'DanganConfig',
    constructor: function (config) {
    	Ext.apply(this,config);
    	config = config || {};
    	var personData={};
    	Ext.apply(config,{
		    fullscreen: true,
		    title:this.title,
		    scrollable:{
		    	direction: 'vertical'
		    },
            items:[
		          {
		           xtype:"button",
		           text:"新建个人客户",
		           handler:this.Danganloll11
		          }, {
		           xtype:"button",
		           text:"新建企业客户",
		           handler:this.Danganlpll11
		          }
		         ]
    	});
	
    	this.callParent([config]);
    },
     Danganloll11 :function() {
		  mobileNavi.push(Ext.create('htmobile.InformationCollection.person.newPersoBbaseInfo',{
					title : "新建个人客户"
				}));
    	},
	 Danganlpll11 :function() {
		  mobileNavi.push(Ext.create('htmobile.InformationCollection.enterprise.newEnterpriseBbaseInfo',{
		  			title:"新建企业客户"
		  		}));
    	}
    

});
