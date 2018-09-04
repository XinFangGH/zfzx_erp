Ext.define('mobile.usermanage.GesturePasswordSetting', {
    extend: 'Ext.form.Panel',
    name: 'GesturePasswordSetting',
    constructor: function (config) {
    	config = config || {};
    	var userName = localStorage.getItem("userName")!=null?localStorage.getItem("userName"):'';
    	Ext.apply(config,{
    		style:"background-color: #1069a5",
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
    			 html:'<div id="errorMsg" style="font-size:20px;text-align:center;padding-top:20px;">请绘制解锁图案</div></br>' 
 
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
	         listeners : {
				resize : function() {
					windowonload();
				}
			}
    	});
      
        	
    	this.callParent([config]);
    	 //<div >'+userName+'</div></br><div id="errorMsg">adfadfsdad</div></br>
    }
    
  

});