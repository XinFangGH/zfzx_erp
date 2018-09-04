
//leadteamList.js
Ext.define('htmobile.customer.person.menudetail.LeadteamList', {
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
		    		"<span   class='tablelist' >{sex}</span>&nbsp;&nbsp;" +
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
    	  var label = new Array("开始时间","结束时间","就读学校","所获学位","所获奖励","备注"
    	); 
    	  var value = new Array(data.educationStartTime,data.educationEndTime,data.educationSchool,
    	  data.degreeAwarded, data.awards, data.remarks);  
          getListDetail(label,value);
		    

}
});
