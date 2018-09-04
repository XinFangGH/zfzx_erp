

Ext.define('htmobile.customer.enterprise.menudetail.OutassureList', {
    extend: 'mobile.List',
    
    name: 'OutassureList',

    constructor: function (config) {
		this.enterpriseId=config.data.id;
    	config = config || {};
    	var panel=Ext.create('tableHeader',{header:new Array("<span class='tablehead' >担保对象</span>",
    	                                                      "<span class='tablehead' >担保方式</span>",
    	                                                      "<span class='tablehead' >担保金额(万)</span>")});
    	Ext.apply(config,{
    		modeltype:"OutassureList",
    		flex:1,
    		width:"100%",
		    height:"100%",
    		title:"对外担保",
    		items:[panel],
    		fields:[ {
					name : 'id'
				},{
					name : 'enterpriseid'
				}, {
					name : 'assureobject'
				}, {
					name : 'money'
				}, {
					name : 'startdate'
				}, {
					name : 'enddate'
				},{
					name : 'assureterm'
				},{
					name : 'objectstatus'
				},{
					name : 'dutyrate'
				},{
					name : 'dutymoney'
				},{
					name : 'remarks'
				},{name :　'assureway'},
				{name : 'dbfs'},
				{name :'pretotalearn'},{
					name : 'assurewayValue'
				}],
    	    	url : __ctxPath + '/creditFlow/customer/enterprise/queryEnterpriseOutassure.do',
	    		root:'topics',
	    	    totalProperty: 'totalProperty',
	    	    params : {
					id : this.enterpriseId
				},
		    itemTpl: "<span  class='tablelist'>{assureobject}</span>&nbsp;&nbsp;" +
		    		"<span   class='tablelist' >{assurewayValue}</span>&nbsp;&nbsp;" +
		    		"<span   class='tablelist' >{money}</span>" +
		    		"<span class='tableDetail'>></span>",
		    		
 	        listeners : {
				itemsingletap : this.itemsingletap
			}
    	});

    	this.callParent([config]);

    },
	itemsingletap : function(obj, index, target, record) {
		    	
    	  var data=record.data;
    	  var label = new Array("担保对象","担保方式","担保金额(万)","担保截止日期",
    	  "责任比例(%)","预计担保期内支付的担保金额(万)"
    	); 
    	  var value = new Array(data.assureobject,data.assurewayValue,data.money,
    	  data.enddate, data.dutyrate, data.pretotalearn);  
          getListDetail(label,value);
		    

}
});
