

Ext.define('htmobile.customer.DefineConfig', {
    extend: 'Ext.form.Panel',
    
    name: 'DefineConfig',

    constructor: function (config) {
    	config = config || {};
    	var personData={};
    	Ext.apply(config,{
		    fullscreen: true,
		    title:"客户编辑",
		    scrollable:{
		    	direction: 'vertical'
		    },
    	
            items:[
		          {
		           xtype:"panel",
		           html: "<div class='x-unsized x-button-normal x-button buttonCls' style='border-radius: 0; text-align: center; height:50px;'onclick='javascript:lol();'>个人客户编辑</div>"
		          }, {
		           xtype:"panel",
		           html: "<div class='x-unsized x-button-normal x-button buttonCls' style='border-radius: 0; text-align: center; height:50px;'onclick='javascript:lpl();'>企业客户编辑</div>"
		          }
		          
		         ]

    	});
	 lol =function(data) {

		  mobileNavi.push(Ext.create('htmobile.customer.person.PersonList'));
    	            
    	}
	 lpl =function(data) {

		  mobileNavi.push(Ext.create('htmobile.customer.enterprise.EnterpriseList'));
    	            
    	}
    	this.callParent([config]);
    }
    

});
