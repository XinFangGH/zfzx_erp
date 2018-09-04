Ext.define('mobile.GesturePasswordLogin', {
    extend: 'Ext.form.Panel',
    name: 'GesturePasswordLogin',
    constructor: function (config) {
    	config = config || {};
    	var userName = localStorage.getItem("userName")!=null?localStorage.getItem("userName"):'';
    	Ext.apply(config,{
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
    			docked: 'top',
    			 xtype: 'panel',
    			 html:'<div id="myCanvasdiv" style="background:red;overflow-x:hidden;overflow-y:hidden"><canvas id="myCanvas"></canvas></div>' 
       
 
    			},
		        {
    			 xtype: 'panel',
    			 html:'<div >'+userName+'</div></br><div id="errorMsg"></div></br>' 
       
 
    			}
	        ]
    	});
      
        	
    	this.callParent([config]);
    	 window.onload =windowonload;
    	 //<div >'+userName+'</div></br><div id="errorMsg">adfadfsdad</div></br>
    }
    
  

});