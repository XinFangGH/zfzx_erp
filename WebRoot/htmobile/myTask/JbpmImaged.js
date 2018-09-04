
/**
 * 查看流程图 贷后用的
 * by cjj
 */
Ext.define('htmobile.myTask.JbpmImaged', {
    extend: 'Ext.Panel',
    xtype:'JbpmImaged',
    name:'JbpmImaged',

    constructor:function(config){
    	config = config || {};
//    	/贷后用的runId;
    	this.runId=config.runId;
		Ext.apply(config,{
			title: '查看流程图',
			layout: 'hbox',
			fullscreen:true,
			items:[{
				xtype:'img',
				fullscreen:true,
				style:'background-size:100% 100%;',
				margin:'0,0,0,0',
				padding:'0,0,0,0',
				centered:true,
				src: __ctxPath+ '/jbpmImage?runId='+this.runId+ '&rand=' + Math.random(),
			    minHeight: (document.body.clientHeight),
			    minWidth: (document.body.clientWidth)
			}]
			});
		
		this.callParent([config]);
    }

});
    