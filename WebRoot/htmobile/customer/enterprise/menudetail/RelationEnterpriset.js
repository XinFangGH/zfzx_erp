
//relationPersonList.js
Ext.define('htmobile.customer.enterprise.menudetail.RelationEnterpriset', {
    extend: 'mobile.List',
    
    name: 'RelationEnterpriset',

    constructor: function (config) {
		this.enterpriseId=config.data.id;
    	config = config || {};
    	var panel=Ext.create('tableHeader',{header:new Array("<span class='tablehead' >联系人姓名</span>",
    	                                                      "<span class='tablehead' >职务</span>",
    	                                                      "<span class='tablehead' >固定电话</span>")});
    	Ext.apply(config,{
    		modeltype:"RelationEnterpriset",
    		flex:1,
    		title:"关系人",
    		items:[panel],
    		fields:[{name : 'id'},{name : 'relationName'},{name : 'relationJob'},{name : 'relationFixedPhone'},{name : 'relationMovePhone'},{name : 'relationFamilyAddress'},{name : 'bossName'},{name : 'bossPhone'},{name : 'remarks'},{name : 'mark'}],
    		url : __ctxPath + '/creditFlow/customer/enterprise/queryListRelationPersonEnterpriseRelationPerson.do',
    		root:'topics',
    		totalProperty: 'totalProperty',
    	    params : {
					eid : this.enterpriseId
				},
		    itemTpl: "<span  class='tablelist'>{relationName}</span>&nbsp;&nbsp;" +
		    		"<span   class='tablelist' >{relationJob}</span>&nbsp;&nbsp;" +
		    		"<span   class='tablelist' >{relationFixedPhone}</span>" +
		    		"<span class='tableDetail'>></span>",
		    		
 	        listeners : {
				itemsingletap : this.itemsingletap
			}
    	});

    	this.callParent([config]);

    },
	itemsingletap : function(obj, index, target, record) {
		 
    	  var data=record.data;
    	  var label = new Array("联系人姓名","职务","固定电话","移动电话","是否主联系人","家庭住址"); 
    	  var value = new Array(data.relationName, data.relationJob,data.relationFixedPhone,data.relationMovePhone,data.mark==true?"是":"否",data.relationFamilyAddress);  
          getListDetail(label,value);
		    

}
});
