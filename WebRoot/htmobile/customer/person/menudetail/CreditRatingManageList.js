

Ext.define('htmobile.customer.person.menudetail.CreditRatingManageList', {
    extend: 'mobile.List',
    
    name: 'CreditRatingManageList',

    constructor: function (config) {
		this.personId=config.data.id;
		this.type=config.type;
    	config = config || {};
    	var panel=Ext.create('tableHeader',{header:new Array("<span class='tablehead' >所客户名称</span>",
    	                                                      "<span class='tablehead' >客户类型</span>",
    	                                                      "<span class='tablehead' >所有评估模板</span>")});
    	Ext.apply(config,{
    		modeltype:"CreditRatingManageList",
    		flex:1,
    		title:"资信评估",
    		items:[panel],
    		fields:[ {
					name : 'id'
				},{
					name : 'customerName'
				}, {
					name : 'customerType'
				}, {
					name : 'creditTemplate'
				},{
					name : 'ratingScore'
				},{
					name : 'templateScore'
				},{
					name : 'creditRegister'
				},{
					name : 'ratingMan'
				},{
					name : 'ratingTime'
				},{
					name:'advise_sb'
				},{
				    name:'pgtime'
				}],
    	        url : __ctxPath+'/creditFlow/creditmanagement/listOfJDCreditRating.do',
	    		root:'topics',
	    	    totalProperty: 'totalCounts',
	    	    params : {
					customerId:this.personId,
					customerType:this.type
			},
		    itemTpl: "<span  class='tablelist'>{customerName}</span>&nbsp;&nbsp;" +
		    		"<span   class='tablelist' >{customerType}</span>&nbsp;&nbsp;" +
		    		"<span   class='tablelist' >{creditTemplate}</span>" +
		    		"<span class='tableDetail'>></span>",
		    		
 	        listeners : {
				itemsingletap : this.itemsingletap
			}
    	});

    	this.callParent([config]);

    },
	itemsingletap : function(obj, index, target, record) {
		    	
    	  var data=record.data;
    	  var label = new Array("客户名称","客户类型","所用评估模板","评估百分制总分","资信评级",
    	 "评级含义","评估人","评估日期","耗时"); 
    	  var value = new Array(data.customerName, data.customerType,data.creditTemplate,data.ratingScore,data.creditRegister,
    	  data.advise_sb,data.ratingMan,data.ratingTime,data.pgtime);  
          getListDetail(label,value);
		    

}
});
