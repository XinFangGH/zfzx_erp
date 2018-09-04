

Ext.define('htmobile.customer.person.menudetail.ReditregistriesList', {
    extend: 'mobile.List',
    
    name: 'ReditregistriesList',

    constructor: function (config) {
		this.personId=config.data.id;
    	config = config || {};
    	var panel=Ext.create('tableHeader',{header:new Array("<span class='tablehead' >征信报告编号</span>",
    	                                                      "<span class='tablehead' >查询日期</span>",
    	                                                      "<span class='tablehead' >报告查询日期</span>")});
    	Ext.apply(config,{
    		modeltype:"ReditregistriesList",
    		flex:1,
    		title:"征信记录",
    		items:[panel],
    		fields:[ {name : 'id'},
    		{name : 'creditregistriesNo'}, 
    			   {name : 'queryTime'},
    			   {name:'reportQueryTime'}, 
    				{name:'bankAccountNum'},
    				{name : 'bankCreditBalance'},
    				{name:'foreHouseNumber'},
    				{name:'forePostcode'},
    				{name:'foreProvince'} ],
    		url : __ctxPath+'/creditFlow/customer/person/queryListProcessPersonCarReditregistries.do?personId='+ this.personId,
    		root:'topics',
    	    totalProperty: 'totalCounts',
		    itemTpl: "<span  class='tablelist'>{creditregistriesNo}</span>&nbsp;&nbsp;" +
		    		"<span   class='tablelist' >{queryTime}</span>&nbsp;&nbsp;" +
		    		"<span   class='tablelist' >{reportQueryTime}</span>" +
		    		"<span class='tableDetail'>></span>",
		    		
 	        listeners : {
				itemsingletap : this.itemsingletap
			}
    	});

    	this.callParent([config]);

    },
	itemsingletap : function(obj, index, target, record) {
		    	
    	  var data=record.data;
    	  var label = new Array("征信报告编号","查询日期","报告查询日期","银行信贷账户数","银行信贷余额"
    	); 
    	  var value = new Array(data.creditregistriesNo, data.queryTime,data.reportQueryTime,data.bankAccountNum,data.bankCreditBalance
    	 );  
          getListDetail(label,value);
		    

}
});
