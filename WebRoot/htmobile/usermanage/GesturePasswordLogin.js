Ext.define('mobile.usermanage.GesturePasswordLogin', {
    extend: 'Ext.Panel',
    name: 'GesturePasswordLogin',
    constructor: function (config) {
    	loginOther=function(){
    	     gesturePasswordLogin.destroy();
    	     mobileLogin = Ext.create('mobile.Login', {fullscreen: true});
    	}
    	forgetGesturePassword=function(){
    		
    		var username = localStorage.getItem("userName")!=null?localStorage.getItem("userName"):'';
    	   localStorage.setItem(username+"isGesturePassword","false");
    	     gesturePasswordLogin.destroy();
    	    mobileLogin = Ext.create('mobile.Login', {fullscreen: true});
    	
    	}
    	config = config || {};
    	var userName = localStorage.getItem("userName")!=null?localStorage.getItem("userName"):'';
    	Ext.apply(config,{
    		style:"background-color:#0D1B38",
    		items : [
    	/*		{
	        	    docked: 'top',
	        	     title: '金智万维ERP管理系统',
	        	    xtype: 'titlebar',
	        	    items:[{
	        	    	align : 'right',
			            xtype: 'button',
			            name: 'submit',
			            text:'设置',
			            cls : 'buttonCls',
			            handler:this.setConfig
	        	    }]
	        	},*/
		        {
		      //   docked: 'top',
    			 xtype: 'panel',
    		//	 height:"100px",
    			 html:'<div class="gestureLoginname" style="font-size:16px;text-align:center;padding-top:20px">'+userName+'</div></br><div id="errorMsg"  style="font-size:16px;text-align:center;color:#D9D5BA"></div></br>' 
       
 
    			}
    			,{
    			 docked: 'top',
    			 xtype: 'panel',
    			 html:'<div id="myCanvasdiv" ><canvas id="myCanvas"></canvas></div>' 
       
 
    			},
		        {
    			 xtype: 'panel',
    			 html:'<div class="gestureLogin"><span  onclick="javascript:loginOther();"  style="font-size:16px;">登录其它账户</span>'+ 
                       '&nbsp;&nbsp;&nbsp;&nbsp;<span  onclick="javascript:forgetGesturePassword();" style="font-size:16px;">忘记手势密码</span></div>'
    			 //&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
 
    			}
	        ],
	        listeners: {
		         
	        	show: function() {
	                windowonload();
	
	            }
	         }
    	});
      
        	
    	this.callParent([config]);
    	/* window.onload =windowonload;*/
    	 //<div >'+userName+'</div></br><div id="errorMsg">adfadfsdad</div></br>
    }
    
  

});