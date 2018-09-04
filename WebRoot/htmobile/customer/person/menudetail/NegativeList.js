

Ext.define('htmobile.customer.person.menudetail.NegativeList', {
    extend: 'mobile.List',
    
    name: 'NegativeList',

    constructor: function (config) {
		this.personId=config.data.id;
    	config = config || {};
    	var panel=Ext.create('tableHeader',{header:new Array("<span class='tablehead' >负面时间</span>",
    	                                                      "<span class='tablehead' >录入人</span>",
    	                                                      "<span class='tablehead' >录入时间</span>")});
    	Ext.apply(config,{
    		modeltype:"NegativeList",
    		flex:1,
    		title:"负面调查",
    		items:[panel],
    		fields:[
							{
								name : 'negativeId'
							},
							{
								name : 'negativeTime'
							},
							{
								name : 'negativeExplain'
							},
							{
								name : 'negativeOperator'
							},
							{
								name : 'negativeEnteringTime'
							},
							{
								name : 'userId'
							},
							{
								name : 'personId'
							}
					],
    	      url:__ctxPath+ '/creditFlow/customer/person/listBpCustPersonNegativeSurvey.do',
	    		root:'result',
	    	    totalProperty: 'totalCounts',
	    	    params : {
						personId : this.personId
			},
		    itemTpl: "<span  class='tablelist'>{negativeTime}</span>&nbsp;&nbsp;" +
		    		"<span   class='tablelist' >{negativeOperator}</span>&nbsp;&nbsp;" +
		    		"<span   class='tablelist' >{negativeEnteringTime}</span>" +
		    		"<span class='tableDetail'>></span>",
		    		
 	        listeners : {
				itemsingletap : this.itemsingletap
			}
    	});

    	this.callParent([config]);

    },
	itemsingletap : function(obj, index, target, record) {
		    	
    	  var data=record.data;
    	  var label = new Array("负面时间","情况说明","录入人","录入时间"
    	); 
    	  var value = new Array(data.negativeTime,data.negativeExplain,data.negativeOperator,
    	  data.negativeEnteringTime);  
          getListDetail(label,value);
		    

}
});
