
//leadteamList.js
Ext.define('htmobile.customer.enterprise.menudetail.LeadteamList', {
    extend: 'mobile.List',
    
    name: 'LeadteamList',

    constructor: function (config) {
		this.enterpriseId=config.data.id;
    	config = config || {};
    	var panel=Ext.create('tableHeader',{header:new Array("<span class='tablehead' >姓名</span>",
    	                                                      "<span class='tablehead' >性别</span>",
    	                                                      "<span class='tablehead' >职务</span>")});
    	Ext.apply(config,{
    		modeltype:"LeadteamList",
    		flex:1,
    		title:"管理团队",
    		items:[panel],
    		fields:[ {
					name : 'id'
				},{
					name : 'enterpriseid'
				}, {
					name : 'personid'
				}, {
					name : 'sex'
				}, {
					name : 'cardtype'
				}, {
					name : 'cardnum'
				},{
					name : 'duty'
				},{
					name : 'techtitle'
				},{
					name : 'director'
				},{
					name : 'phone'
				},{
					name : 'name'
				},{
					name : 'remarks'
				},{
					name : 'jszc'		
				},{
					name : 'zjlx'
				},{
					name : 'cardtypeValue'
				},{
					name : 'techtitleValue'
				}],
    	       url : __ctxPath + '/creditFlow/customer/enterprise/queryEnterpriseLeadteam.do',
	    		root:'topics',
	    	    totalProperty: 'totalProperty',
	    	    params : {
						id : this.enterpriseId
			},
		    itemTpl: "<span  class='tablelist'>{name}</span>&nbsp;&nbsp;" +
		    		"<span   class='tablelist' ><tpl if='sex==true'>男</tpl><tpl if='sex==false'>女</tpl></span>&nbsp;&nbsp;" +
		    		"<span   class='tablelist' >{duty}</span>" +
		    		"<span class='tableDetail'>></span>",
		    		
 	        listeners : {
				itemsingletap : this.itemsingletap
			}
    	});

    	this.callParent([config]);

    },
	itemsingletap : function(obj, index, target, record) {
    	  var data=record.data;
    	  var label = new Array("姓名","性别","证件类型","证件号码","职务","技术职称"
    	  ,"是否董事成员","联系电话","备注"
    	); 
    	  var value = new Array(data.name,data.sex==true?"男":"女",data.cardtypeValue,
    	  data.cardnum, data.duty, data.techtitleValue,data.director, data.phone, data.remarks);  
          getListDetail(label,value);
		    

}
});
