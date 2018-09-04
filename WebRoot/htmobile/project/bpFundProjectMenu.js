

Ext.define('htmobile.project.bpFundProjectMenu', {
    extend: 'Ext.Panel',
    
    name: 'bpFundProjectMenu',

    constructor: function (config) {
    	var bheight=75;
      this.data=config.data;
      var data=config.data;
    	Ext.apply(config,{
		    fullscreen: true,
		    width:'100%',
		    height:'100%',
		    title:"项目详情",
		    scrollable:{
		    	direction: 'vertical'
		    },
		    items: [{
		    	xtype: 'fieldset',
		        items:[{
		           xtype:'panel',
		           html:'<div class=\"vmMain\">意见与说明记录  '+
		           '<span style=\"float:right;\" onclick=\"javascript:personSFZphoto();\">>&nbsp;&nbsp;&nbsp;&nbsp;</span></div>'
		          }]
		    }
		 ]
    	});

    	this.callParent([config]);
    	personSFZphoto=function(){
       mobileNavi.push(
		Ext.create('htmobile.creditFlow.public.processFomlist',{
			runId:data.runId,
			filterableNodeKeys:'onlineJudgement,filterableNodeKeys,ExaminationArrangement,xsps,resolutionOnlineReviewMeeting',
			safeLevel:"3"
		        	})
		    	);
    }

}
})
    
  
   
  


