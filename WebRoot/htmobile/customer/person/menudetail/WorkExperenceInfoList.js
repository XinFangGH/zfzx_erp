

Ext.define('htmobile.customer.person.menudetail.WorkExperenceInfoList', {
    extend: 'mobile.List',
    
    name: 'WorkExperenceInfoList',

    constructor: function (config) {
		this.personId=config.data.id;
    	config = config || {};
    	var panel=Ext.create('tableHeader',{header:new Array("<span class='tablehead' >开始时间</span>",
    	                                                      "<span class='tablehead' >结束时间</span>",
    	                                                      "<span class='tablehead' >单位名称</span>")});
    	Ext.apply(config,{
    		modeltype:"WorkExperenceInfoList",
    		flex:1,
    		title:"工作经历",
    		items:[panel],
    		fields:[
							{
								name : 'workId'
							},
							{
								name : 'workStartTime'
							},
							{
								name : 'workEndTime'
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
								name : 'companyBackground'
							},
							{
								name : 'personId'
							}
					],
    	    	url:__ctxPath+ '/creditFlow/customer/person/listBpCustPersonWorkExperience.do',
	    		root:'result',
	    	    totalProperty: 'totalCounts',
	    	    params : {
						personId : this.personId
			},
		    itemTpl: "<span  class='tablelist'>{workStartTime}</span>&nbsp;&nbsp;" +
		    		"<span   class='tablelist' >{workEndTime}</span>&nbsp;&nbsp;" +
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
    	  var label = new Array("开始时间","结束时间","单位性质","单位名称","职务","单位背景"
    	); 
    	  var value = new Array(data.workStartTime,data.workEndTime,data.companyNature,
    	  data.companyName, data.duty, data.companyBackground);  
          getListDetail(label,value);
		    

}
});
