/**
 *论坛连接 
 **/

var JforumView  = function(){
	return new Ext.Panel({
		id:'JforumView',
		title:"论坛管理",
		iconCls:'menu-appuser',
	    items:[{
	    	xtype : 'panel',
			border : false,
			background_image:'menu-appuser',
			bodyStyle : 'font-size:30px;padding-left:80px;padding-top:20px;',
			html : '<img src="'+__ctxPath+'/images/logo.jpg"/><a href="javascript:toJforum()">论坛连接</a>'
	    }]
	});
	
};
var store = new Ext.data.Store({
	reader : new Ext.data.JsonReader({
		root : 'result',
		fields : [{
					name : 'userId',
					type : 'int'
				},'username','fullname','email']
		}),
   remoteSort : true
});
function toJforum() {
	window.open(__ctxPath + '/info/jforum.do?userId='+this.userId, '_blank');
}