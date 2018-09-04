//bplawsuit.js




Ext.define('htmobile.customer.enterprise.menudetail.AffiliatedEnterprise', {
    extend: 'mobile.List',
    
    name: 'AffiliatedEnterprise',

    constructor: function (config) {
		this.enterpriseId=config.data.id;
		this.type=config.type;
    	config = config || {};
    	var panel=Ext.create('tableHeader',{style:"margin-left:10px;",header:new Array("<span class='tablehead'> 企业名称</span>",
    	                                                      "<span class='tablehead' >简称</span>")});
    	Ext.apply(config,{
    		modeltype:"AffiliatedEnterprise",
    		flex:1,
    		title:"关联企业",
    		items:[panel],
    		fields:[{
				name : 'id'
			}, {
				name : 'enterprisename'
			}, {
				name : 'shortname'
			}, {
				name : 'ownership'
			}, {
				name : 'registermoney'
			}, {
				name : 'organizecode'
			}, {
				name : 'tradetypev'
			}, {
				name : 'ownershipv'
			}, {
				name : 'telephone'
			}, {
				name : 'legalperson'
			}, {
				name : 'postcoding'
			}, {
				name : 'cciaa'
			}, {
				name : 'managecityName'
			}, {
				name : 'area'
			}, {
				name : 'opendate'
			}, {
				name : 'hangyetypevalue'
			}, {
				name : 'hangyetypevalue'
			}, {
				name : 'orgName'
			},{
				name:'taxnum'
			}],
    	      	url : __ctxPath + '/creditFlow/customer/enterprise/getListEnterprise.do?',
	    		root:'topics',
	    	    totalProperty: 'totalProperty',
	    	    params : {
						enterId:this.enterpriseId,
				        type:""
			},
		    itemTpl: "<span  class='tablelist'>{enterprisename}</span>&nbsp;&nbsp;" +
		    		"<span   class='tablelist' >{shortname}</span>&nbsp;&nbsp;" +
		    		"<span class='tableDetail'>></span>",
		    		
 	        listeners : {
				itemsingletap : this.itemsingletap
			}
    	});

    	this.callParent([config]);

    },
	itemsingletap : function(obj, index, target, record) {
		    	
    	  var data=record.data;
    	   var label = new Array("企业名称","组织机构代码","营业执照号码","税务登记号","法人","注册资金","联系电话","企业成立日期"
    	 ); 
    	  var value = new Array(data.enterprisename,data.organizecode,data.cciaa,
    	  data.taxnum,data.legalperson,data.registermoney,data.telephone,data.opendate);  
    	
          getListDetail(label,value);
		    

}
});
