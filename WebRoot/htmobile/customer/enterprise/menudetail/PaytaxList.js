//bppaytaxList.js



Ext.define('htmobile.customer.enterprise.menudetail.PaytaxList', {
    extend: 'mobile.List',
    
    name: 'PaytaxList',

    constructor: function (config) {
		this.enterpriseId=config.data.id;
		this.type=config.type;
    	config = config || {};
    	var panel=Ext.create('tableHeader',{style:"margin-left:10px;",header:new Array("<span class='tablehead'> 立案时间 </span>",
    	                                                      "<span class='tablehead' >立案原因</span>")});
    	Ext.apply(config,{
    		modeltype:"PaytaxList",
    		flex:1,
    		title:"纳税情况",
    		items:[panel],
    		fields:[ 
						'year'
						,'increaseTaxe'
						,'salesTax'
						,'incomeTax'
						,'customersTax'
						,'additionTax'
						,'ohterTax'
						,'sumTax'
					
				],
    	      	url : __ctxPath + '/creditFlow/customer/enterprise/listBpCustEntPaytax.do',
	    		root:'result',
	    	    totalProperty: 'totalCounts',
	    	    params : {
						enterpriseId : this.enterpriseId
			},
		    itemTpl: "<span  class='tablelist'>{year}</span>&nbsp;&nbsp;" +
		    		"<span   class='tablelist' >{sumTax}</span>&nbsp;&nbsp;" +
		    		"<span class='tableDetail'>></span>",
		    		
 	        listeners : {
				itemsingletap : this.itemsingletap
			}
    	});

    	this.callParent([config]);

    },
	itemsingletap : function(obj, index, target, record) {
		    	
    	  var data=record.data;
    	   var label = new Array("年度","增值税(元)","营业税(元)","所得税(元)","关税(元)",
    	 "附加税(元)","其他(元)","合计(元)"); 
    	  var value = new Array(data.year,data.increaseTaxe,data.salesTax,
    	  data.incomeTax,  data.customersTax,data.additionTax,data.ohterTax,data.sumTax);  
          getListDetail(label,value);
		    

}
});
