Ext.define('mobile.usermanage.GesturePasswordSetting', {
    extend: 'Ext.Panel',
    name: 'GesturePasswordSetting',
    constructor: function (config) {
    	config = config || {};
    	var userName = localStorage.getItem("userName")!=null?localStorage.getItem("userName"):'';
    	var errormsg=config.errormsg;
    	Ext.apply(config,{
    		style:"background-color: #0D1B38",
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
		   //     docked: 'top',
    			 xtype: 'panel',
    		//	  height:"100px",
    			 html:'<div  class="gestureLoginname" id="errorMsg" style="font-size:18px;text-align:center;color:#D9D5BA">'+errormsg+'</div></br>' 
       
 
    			}, {
    			docked: 'top',
    			 xtype: 'panel',
    			 html:'<div id="myCanvasdiv" ><canvas id="myCanvas"></canvas></div>' 
       
    			},
		        {
    			 xtype: 'panel',
    			 html:'&nbsp;&nbsp;' 
 
    			}
	        ],
	         listeners: {
	         
	        	 show: function() {
	        		 windowonload();

            }
	         }
    	});
      
        	
    	this.callParent([config]);
    	 //<div >'+userName+'</div></br><div id="errorMsg">adfadfsdad</div></br>
    }
    
  

});