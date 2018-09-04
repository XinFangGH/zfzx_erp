

Ext.define('htmobile.customer.person.menudetail.EducationInfoList', {
    extend: 'mobile.List',
    
    name: 'EducationInfoList',

    constructor: function (config) {
		this.personId=config.data.id;
    	config = config || {};
    	var panel=Ext.create('tableHeader',{header:new Array("<span class='tablehead' >开始时间</span>",
    	                                                      "<span class='tablehead' >结束时间</span>",
    	                                                      "<span class='tablehead' >就读学校</span>")});
    	Ext.apply(config,{
    		modeltype:"EducationInfoList",
    		flex:1,
    		title:"教育情况",
    		items:[panel],
    		fields:[
							{
								name : 'educationId'
							},
							{
								name : 'educationStartTime'
							},
							{
								name : 'educationEndTime'
							},
							{
								name : 'educationSchool'
							},
							{
								name : 'degreeAwarded'
							},
							{
								name : 'awards'
							},
							{
								name : 'remarks'
							},
							{
								name : 'personId'
							}
					],
    	     url:__ctxPath+ '/creditFlow/customer/person/listBpCustPersonEducation.do',
	    		root:'result',
	    	    totalProperty: 'totalCounts',
	    	    params : {
						personId : this.personId
			},
		    itemTpl: "<span  class='tablelist'>{educationStartTime}</span>&nbsp;&nbsp;" +
		    		"<span   class='tablelist' >{educationEndTime}</span>&nbsp;&nbsp;" +
		    		"<span   class='tablelist' >{educationSchool}</span>" +
		    		"<span class='tableDetail'>></span>",
		    		
 	        listeners : {
				itemsingletap : this.itemsingletap
			}
    	});

    	this.callParent([config]);

    },
	itemsingletap : function(obj, index, target, record) {
		    	
    	  var data=record.data;
    	  var label = new Array("开始时间","结束时间","就读学校","所获学位","所获奖励","备注"
    	); 
    	  var value = new Array(data.educationStartTime,data.educationEndTime,data.educationSchool,
    	  data.degreeAwarded, data.awards, data.remarks);  
          getListDetail(label,value);
		    

}
});
