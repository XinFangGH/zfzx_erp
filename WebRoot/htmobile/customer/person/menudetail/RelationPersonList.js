

Ext.define('htmobile.customer.person.menudetail.RelationPersonList', {
    extend: 'mobile.List',
    
    name: 'RelationPersonList',

    constructor: function (config) {
		this.personId=config.data.id;
    	config = config || {};
    	var panel=Ext.create('tableHeader',{header:new Array("<span class='tablehead' >姓名</span>",
    	                                                      "<span class='tablehead' >关系</span>",
    	                                                      "<span class='tablehead' >电话</span>")});
    	Ext.apply(config,{
    		modeltype:"RelationPersonList",
    		flex:1,
    		title:"关系人",
    		items:[panel],
    		fields:[ {
					name : 'id' 
				},{
					name : 'relationName'
				},{
					name : 'relationShip'
				}, {
					name : 'relationPhone'
				},{
					name : 'relationCellPhone'
				}, {
					name : 'relationShipValue'
				}, {
					name : 'personId'
				}],
    		url : __ctxPath+'/creditFlow/customer/person/ajaxQueryPersonRelation.do?personId='+ this.personId,
    		root:'topics',
    	    totalProperty: 'totalCounts',
		    itemTpl: "<span  class='tablelist'>{relationName}</span>&nbsp;&nbsp;" +
		    		"<span   class='tablelist' >{relationShipValue}</span>&nbsp;&nbsp;" +
		    		"<span   class='tablelist' >{relationPhone}</span>" +
		    		"<span class='tableDetail'>></span>",
		    		
 	        listeners : {
				itemsingletap : this.itemsingletap
			}
    	});

    	this.callParent([config]);

    },
	itemsingletap : function(obj, index, target, record) {
		    	
    	  var data=record.data;
    	  var label = new Array("姓名","关系","电话","手机"); 
    	  var value = new Array(data.relationName, data.relationShipValue,data.relationPhone,data.relationCellPhone);  
          getListDetail(label,value);
		    

}
});
