

Ext.define('htmobile.customer.person.menudetail.BusinessCPersonMortgageList', {
    extend: 'mobile.List',
    
    name: 'BusinessCPersonMortgageList',

    constructor: function (config) {
		this.personId=config.data.id;
    	config = config || {};
    	var panel=Ext.create('tableHeader',{header:new Array("<span class='tablehead' >项目名称</span>",
    	                                                      "<span class='tablehead' >担保类型</span>",
    	                                                      "<span class='tablehead' >抵质押物类型</span>")});
    	Ext.apply(config,{
    		modeltype:"BusinessCPersonMortgageList",
    		flex:1,
    		width:"100%",
		    height:"100%",
    		title:config.title,
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
    	    		url :  __ctxPath +"/credit/mortgage/getMorByPersonType.do",
	    		root:'result',
	    	    totalProperty: 'totalCounts',
	    	    params : {
						assureofname : this.personId,
						personType : 603
			},
		    itemTpl: "<span  class='tablelist'>{remarks}</span>&nbsp;&nbsp;" +
		    		"<span   class='tablelist' >{lessDatumRecord}</span>&nbsp;&nbsp;" +
		    		"<span   class='tablelist' >{mortgagepersontypeforvalue}</span>" +
		    		"<span class='tableDetail'>></span>",
		    		
 	        listeners : {
				itemsingletap : this.itemsingletap
			}
    	});

    	this.callParent([config]);

    },
	itemsingletap : function(obj, index, target, record) {
		    	
    	  var data=record.data;
    	  var label = new Array("项目名称","担保类型","抵质押物类型","抵质押物名称","最终估价","登记时间","状态"
    	); 
    	  var value = new Array(data.remarks,data.lessDatumRecord,data.mortgagepersontypeforvalue,
    	  data.mortgagename, data.finalprice, data.transactDate, data.needDatumList);  
          getListDetail(label,value);
		    

}
});
