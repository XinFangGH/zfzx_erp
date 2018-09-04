

Ext.define('htmobile.customer.person.menudetail.BusinessCLegalPersonList', {
    extend: 'mobile.List',
    
    name: 'BusinessCLegalPersonList',

    constructor: function (config) {
		this.personId=config.data.id;
    	config = config || {};
    	var panel=Ext.create('tableHeader',{header:new Array("<span class='tablehead' >企业名称</span>",
    	                                                      "<span class='tablehead' >组织机构代码</span>",
    	                                                      "<span class='tablehead' >营业执照号码</span>")});
    	Ext.apply(config,{
    		modeltype:"BusinessCLegalPersonList",
    		flex:1,
    		width:"100%",
		    height:"100%",
    		title:config.title,
    		items:[panel],
    		fields:[{
				name : 'id'
			},{
				name : 'organizecode'
			},{
				name : 'cciaa'
			},{
				name : 'enterprisename'
			},{
				name : 'ownership'
			},{
				name : 'registermoney'
			}
			],
			url :  __ctxPath +"/creditFlow/common/getEntByLegalPersonIdShareequity.do",
			root:'result',
			totalProperty: 'totalCounts',
			params : {
				personid : this.personId
			},
		    itemTpl: "<span  class='tablelist'>{enterprisename}</span>&nbsp;&nbsp;" +
		    		"<span   class='tablelist' >{organizecode}</span>&nbsp;&nbsp;" +
		    		"<span   class='tablelist' >{cciaa}</span>" +
		    		"<span class='tableDetail'>></span>",
		    		
 	        listeners : {
				itemsingletap : this.itemsingletap
			}
    	});

    	this.callParent([config]);

    },
	itemsingletap : function(obj, index, target, record) {
		    	
    	  var data=record.data;
    	  var label = new Array("企业名称","组织机构代码","营业执照号码","所有制性质","注册资金"
    	); 
    	  var value = new Array(data.enterprisename,data.organizecode,data.cciaa,
    	  data.ownership, data.registermoney);  
          getListDetail(label,value);
		    

}
});
