
//relationPersonList.js
Ext.define('htmobile.customer.enterprise.menudetail.PrizeList', {
    extend: 'mobile.List',
    
    name: 'PrizeList',

    constructor: function (config) {
		this.enterpriseId=config.data.id;
    	config = config || {};
    	var panel=Ext.create('tableHeader',{header:new Array("<span class='tablehead' >证书名称</span>",
    	                                                      "<span class='tablehead' >证书编号</span>",
    	                                                      "<span class='tablehead' >颁发机构名称</span>")});
    	Ext.apply(config,{
    		modeltype:"PrizeList",
    		flex:1,
    		title:"获奖情况",
    		items:[panel],
    		fields:[ {
					name : 'id'  //获奖ID
				},{
					name : 'certificatename' //证书名称
				}, {
					name : 'certificatecode' //证书编号
				}, {
					name : 'organname' //颁发机构名称
				}, {
					name : 'prizerpname'  //获奖人
				}, {
					name : 'licencedate' //颁发时间
				},{
					name : 'remarks' //备注
				},{
					name :'bfevent'
				}],
    		url : __ctxPath + '/creditFlow/customer/enterprise/queryEnterprisePrize.do',
    	    params : {
					eid : this.enterpriseId
				},
    		
    		root:'topics',
    		totalProperty: 'totalProperty',
		    itemTpl: "<span  class='tablelist'>{certificatename}</span>&nbsp;&nbsp;" +
		    		"<span   class='tablelist' >{certificatecode}</span>&nbsp;&nbsp;" +
		    		"<span   class='tablelist' >{organname}</span>" +
		    		"<span class='tableDetail'>></span>",
		    		
 	        listeners : {
				itemsingletap : this.itemsingletap
			}
    	});

    	this.callParent([config]);

    },
	itemsingletap : function(obj, index, target, record) {

    	  var data=record.data;
    	  var label = new Array("证书名称","证书编号","颁发机构名称","获奖人","颁发事件","颁发时间","备注"); 
    	  var value = new Array(data.certificatename, data.certificatecode,data.organname,data.prizerpname,data.bfevent,data.licencedate,data.remarks);  
          getListDetail(label,value);
		    

}
});
