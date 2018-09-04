//cashflowandSaleIncomeList.js




Ext.define('htmobile.customer.enterprise.menudetail.CashflowandSaleIncomeList', {
    extend: 'mobile.List',
    
    name: 'CashflowandSaleIncomeList',

    constructor: function (config) {
		this.enterpriseId=config.data.id;
		this.type=config.type;
    	config = config || {};
    	var panel=Ext.create('tableHeader',{style:"margin-left:10px;",header:new Array("<span class='tablehead'> 月份 </span>",
    	                                                      "<span class='tablehead' >主营业务收入(元)</span>")});
    	Ext.apply(config,{
    		modeltype:"CashflowandSaleIncomeList",
    		flex:1,
    	    title:"<span style='font-size:13px;'>企业现金流量及销售收入</span>",
    		items:[panel],
    		fields:['month'
						,'mainBusinessIncomeMoney'
						,'cashflowincomeMoney'
						,'payGoodsMoney'
						,'cashflowpayMoney'
						,'enterpriseId'
																																	],
    	      url : __ctxPath + '/creditFlow/customer/enterprise/listBpCustEntCashflowAndSaleIncome.do',
	    		root:'result',
	    	    totalProperty: 'totalCounts',
	    	    params : {
						enterpriseId : this.enterpriseId
			},
		    itemTpl: "<span  class='tablelist'>{month}</span>&nbsp;&nbsp;" +
		    		"<span   class='tablelist' >{mainBusinessIncomeMoney}</span>&nbsp;&nbsp;" +
		    		"<span class='tableDetail'>></span>",
		    		
 	        listeners : {
				itemsingletap : this.itemsingletap
			}
    	});

    	this.callParent([config]);

    },
	itemsingletap : function(obj, index, target, record) {
		    	
    	  var data=record.data;
    	   var label = new Array("月份 ","主营业务收入(元)","现金流入额元)","支付货款金额{元)","现金流出额(元)"); 
    	  var value = new Array(data.month,data.mainBusinessIncomeMoney,data.cashflowincomeMoney,data.payGoodsMoney,
    	  data.cashflowpayMoney);  
          getListDetail(label,value);
		    

}
});
