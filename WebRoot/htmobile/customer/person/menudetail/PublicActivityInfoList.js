

Ext.define('htmobile.customer.person.menudetail.PublicActivityInfoList', {
    extend: 'mobile.List',
    
    name: 'PublicActivityInfoList',

    constructor: function (config) {
		this.personId=config.data.id;
    	config = config || {};
    	var panel=Ext.create('tableHeader',{header:new Array("<span class='tablehead' >开始时间</span>",
    	                                                      "<span class='tablehead' >结束时间</span>",
    	                                                      "<span class='tablehead' >单位名称</span>")});
    	Ext.apply(config,{
    		modeltype:"PublicActivityInfoList",
    		flex:1,
    		title:"社会活动",
    		items:[panel],
    		fields:[
							{
								name : 'activityId'
							},
							{
								name : 'activityStartTime'
							},
							{
								name : 'activityEndTime'
							},
							{
								name : 'companyNature'
							},
							{
								name : 'companyName'
							},
							{
								name : 'duty'
							},
							{
								name : 'honorAndAchievement'
							},
							{
								name : 'personId'
							}
					],
    	    	url:__ctxPath+ '/creditFlow/customer/person/listBpCustPersonPublicActivity.do',
	    		root:'result',
	    	    totalProperty: 'totalCounts',
	    	    params : {
						personId : this.personId
			},
		    itemTpl: "<span  class='tablelist'>{activityStartTime}</span>&nbsp;&nbsp;" +
		    		"<span   class='tablelist' >{activityEndTime}</span>&nbsp;&nbsp;" +
		    		"<span   class='tablelist' >{companyName}</span>" +
		    		"<span class='tableDetail'>></span>",
		    		
 	        listeners : {
				itemsingletap : this.itemsingletap
			}
    	});

    	this.callParent([config]);

    },
	itemsingletap : function(obj, index, target, record) {
		    	
    	  var data=record.data;
    	  var label = new Array("开始时间","结束时间","单位性质","单位名称","职务","社会荣誉与学术成就"
    	); 
    	  var value = new Array(data.activityStartTime,data.activityEndTime,data.companyNature,
    	  data.companyName, data.duty, data.honorAndAchievement);  
          getListDetail(label,value);
		    

}
});
