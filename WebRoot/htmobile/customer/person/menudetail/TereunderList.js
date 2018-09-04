

Ext.define('htmobile.customer.person.menudetail.TereunderList', {
    extend: 'mobile.List',
    name: 'TereunderList',
    constructor: function (config) {
		this.personId=config.data.id;
    	config = config || {};
    	/*var panel=Ext.create('Ext.Panel',{docked:'top',laout:'hbox',height:50,items:[{html:"<span style='font-size:20px;float:left;margin-left:12px;width:100px'  >企业名称</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" +
			    		"<span style='font-size:20px;float:left;margin-left:12px;width:100px' >营业执照</span>"}]});*/
    	var panel=Ext.create('tableHeader',{header:new Array("<span class='tablehead' >企业名称</span>&nbsp;&nbsp;&nbsp;&nbsp;",
    	                                                      "<span class='tablehead' >营业执照</span>&nbsp;&nbsp;&nbsp;&nbsp;")});
    	Ext.apply(config,{
    		modeltype:"TereunderList",
    		flex:1,
    		title:"旗下公司",
    		  items:[panel],
    		  fields:[ {
					name : 'id'
				}, {
					name : 'companyname'
				}, {
					name : 'licensenum'
				}, {
					name : 'value'
				}, {
					name : 'registerdate'  
				}, {
					name : 'registercapital'  
				}, {
					name : 'address'  
				}, {
					name : 'lnpname'  
				}, {
					name : 'phone'
				},{
					name : 'shortname'
				},{
					name : 'name'
				},{
					name : 'personid'
				},{
					name : 'relateValue'
				}],
	    		url : __ctxPath+'/creditFlow/customer/person/queryPersonThereunder.do?pid='+ this.personId,
	    		root:'topics',
	    	    totalProperty: 'totalCounts',
			    itemTpl: "<span class='tablelist' >{shortname}</span>&nbsp;&nbsp;" +
			    		"<span class='tablelist' >{licensenum}</span>&nbsp;&nbsp;" +
			    		"<span class='tableDetail'>></span>",
	 	        listeners : {
					itemsingletap : this.itemsingletap
				}
    	});

    	this.callParent([config]);

    },
	itemsingletap : function(obj, index, target, record) {
		    	
    	  var data=record.data;
    	  var label = new Array("公司名称","营业执照号码","关系","联系人","联系人电话",
    	 "注册时间","注册资本"); 
    	  var value = new Array(data.shortname, data.licensenum,data.relateValue,data.relateValue,data.phone,
    	  data.registerdate,data.registercapital);  
          getListDetail(label,value);
		    

}
});
