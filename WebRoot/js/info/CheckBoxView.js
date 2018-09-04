Ext.ns("CheckBoxView");
/**
 *论坛连接 
 **/

var CheckBoxView  = function(){
	return new Ext.Panel({
		title:"论坛管理",
		iconCls:'menu-appuser',
		
	    items:[{
	    	xtype : 'panel',
			border : false,
			background_image:'menu-appuser',
			bodyStyle : 'font-size:30px;padding-left:80px;padding-top:20px;',
			html : '<img src="'+__ctxPath+'/images/logo.jpg"/><a  href="/jforum" target="_blank">论坛连接</a>'
	    }]
	});
	
};