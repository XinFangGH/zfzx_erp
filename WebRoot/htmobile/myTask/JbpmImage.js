
/**
 * 查看流程图
 * by cjj
 */
Ext.define('htmobile.myTask.JbpmImage', {
    extend: 'Ext.Panel',
    xtype:'jbpmImage',
    name:'jbpmImage',
    constructor:function(config){
    	config = config || {};
    	//贷签用的taskId;
    	var taskId=config.taskId;
		Ext.apply(config,{
			title: '查看流程图',
			layout: 'hbox',
			fullscreen:true,
			items:[{
				xtype:'img',
				style:'background-size:100% 100%;',
				margin:'0,0,0,0',
				padding:'0,0,0,0',
				centered:true,
				src: __ctxPath+ '/jbpmImage?taskId='+taskId+ '&rand=' + Math.random(),
			    minHeight: (document.body.clientHeight),
			    minWidth: (document.body.clientWidth)
			}]
		});
		this.callParent([config]);
    }
});
    