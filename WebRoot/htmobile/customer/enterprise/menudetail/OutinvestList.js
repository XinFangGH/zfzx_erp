

Ext.define('htmobile.customer.enterprise.menudetail.OutinvestList', {
    extend: 'mobile.List',
    
    name: 'OutinvestList',

    constructor: function (config) {
		this.enterpriseId=config.data.id;
    	config = config || {};
    	var panel=Ext.create('tableHeader',{header:new Array("<span class='tablehead' >投资对象</span>",
    	                                                      "<span class='tablehead' >投资方式</span>",
    	                                                      "<span class='tablehead' >投资金额(万)</span>")});
    	Ext.apply(config,{
    		modeltype:"OutinvestList",
    		flex:1,
    		width:"100%",
		    height:"100%",
    		title:"对外投资",
    		items:[panel],
    		fields:[ {
					name : 'id'
				},{
					name : 'enterpriseid'
				}, {
					name : 'investobject'
				}, {
					name : 'money'
				}, {
					name : 'startdate'
				}, {
					name : 'enddate'
				},{
					name : 'expectincome'
				},{
					name : 'factincome'
				},{
					name : 'investway'
				},{
					name : 'pretotalearn'
				},{
					name :　'investincome'
				},{
					name : 'remarks'
				},{
					name : 'investvalue'
				},{
					name : 'investwayValue'
				}],
    	    	url : __ctxPath + '/creditFlow/customer/enterprise/queryEnterpriseOutinvest.do',
	    		root:'topics',
	    	    totalProperty: 'totalProperty',
	    	    params : {
					id : this.enterpriseId
				},
		    itemTpl: "<span  class='tablelist'>{investobject}</span>&nbsp;&nbsp;" +
		    		"<span   class='tablelist' >{investwayValue}</span>&nbsp;&nbsp;" +
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
    	  var label = new Array("投资对象","投资方式","投资金额(万)","投资起始日期",
    	  "投资截止日期","预计担保期内企业支出的投资金额(万)","预计担保期内企业支出的投资金额(万)"
    	); 
    	  var value = new Array(data.investobject,data.investwayValue,data.money,
    	  data.startdate, data.enddate, data.pretotalearn, data.investincome);  
          getListDetail(label,value);
		    

}
});
