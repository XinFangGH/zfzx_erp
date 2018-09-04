

Ext.define('htmobile.customer.person.EditPersonMenu', {
    extend: 'Ext.Panel',
    
    name: 'PersonMenu',

    constructor: function (config) {
    	var bheight=75;
      this.data=config.data;
      var data=config.data;
    	Ext.apply(config,{
		    fullscreen: true,
		    width:'100%',
		    height:'100%',
		    title:"客户详情",
		    scrollable:{
		    	direction: 'vertical'
		    },
		    items: [{
		    	xtype: 'fieldset',
		        items:[{
		           xtype:'panel',
		           html:'<div class=\"vmMain\">基本信息  '+
		           '<span style=\"float:right;\" onclick=\"javascript:personbaseinfo();\">>&nbsp;&nbsp;&nbsp;&nbsp;</span></div>'
		          }]
		    },{
		    	xtype: 'fieldset',
		        items:[{
		           xtype:'panel',
		           html:'<div class=\"vmMain\">上传图片资料 '+
		           '<span style=\"float:right;\" onclick=\"javascript:personuploadphoto();\">>&nbsp;&nbsp;&nbsp;&nbsp;</span></div>'
		          }]
		    }
		 ]
    	});

    	this.callParent([config]);
    	  
personbaseinfo=function(){
       mobileNavi.push(
		Ext.create('htmobile.customer.person.menudetail.personSFZphoto',{
			data:data
		        	})
		    	);
  }
    
  personuploadphoto=function(){
       mobileNavi.push(
		Ext.create('htmobile.customer.person.menudetail.photo',{
			data:data
		        	})
		    	);
  }
  
 

    }
});

   
  


