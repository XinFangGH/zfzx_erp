
/**
 * 手机表单
 * by cjj
 */

Ext.define('htmobile.project.SlSmallloanProjectDetail', {
	extend: 'Ext.TabPanel',
    
    name: 'SlSmallloanProjectDetail',

    constructor: function (config) {
		config = config || {};
		 this.taskId=config.data.taskId ;	 
		 this.runId=config.data.runId;
         this.SlSmallloanProjectVM=Ext.create('htmobile.project.menudetail.SlSmallloanProjectVM',config.data)
    	 this.processFomlist = Ext.create('htmobile.creditFlow.public.processFomlist', {
							taskId : config.data.taskId
						});
        Ext.apply(config,{
        	title:'项目详情',
            layoutOnTabChange: true,
            items : [{
						title : '任务内容',
						items : this.SlSmallloanProjectVM,
						scrollable : {
							direction : 'vertical',
							directionLock : true
						}
					}, {
						title : '流程图',
						items : Ext.create(
								'htmobile.myTask.JbpmImaged', {
									runId : this.runId
								}),
						scrollable : {
							direction : 'both',//Possible values: 'auto', 'vertical', 'horizontal', or 'both'.
							directionLock : true
						}
					}, {
						title : '意见与说明',
						layout : 'fit',
						items : this.processFomlist,
						scrollable : {
							direction : 'vertical',
							directionLock : true
						}
					}]
        });
    
        this.callParent([config]);
	}

});

