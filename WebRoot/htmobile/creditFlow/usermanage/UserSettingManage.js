

Ext.define('htmobile.usermanage.UserSettingManage', {
    extend: 'Ext.Panel',
    
    name: 'UserSettingManage',

    constructor: function (config) {
      
    	updateGesturePassword=function(){
    		var username = localStorage.getItem("userName")!=null?localStorage.getItem("userName"):'';
	    	localStorage.setItem(username+"updateGesturePassword","0");
	    	
	        localStorage.setItem(username+"settinggesturePasswordcount",1);
			 gesturePasswordSetting = Ext.create('mobile.usermanage.GesturePasswordSetting', {fullscreen: true});
			
	    	
    	}
    	config = config || {};
    	Ext.apply(config,{
		    fullscreen: true,
		     title:'用户设置',
		    scrollable:{
		    	direction: 'vertical'
		    },
		    items: [
		    {    xtype: 'fieldset',
		         items:[
		          {
		           xtype:"panel",
		           html:"<div style='background:white;height:50px;'>"+curUserInfo.fullname+"</div>"
		          }
		          
		         ]
		    },
		     {    xtype: 'fieldset',
		          items:[
		          {
		           xtype:"panel",
		           html:"<div style='background:white;height:50px;font-size:16px;padding: 15px;'>修改手势密码<span style='float:right;' onclick=\"javascript:updateGesturePassword();\">>&nbsp;&nbsp;&nbsp;&nbsp;</span></div>"
		          }
		         ]
		    }
		    ]
    	});

   

    	this.callParent([config]);
    	
    }

});
