
//creditorList.js
Ext.define('htmobile.creditFlow.public.InvestPersonBpFundIntentList', {
    extend: 'mobile.List',
    
    name: 'InvestPersonBpFundIntentList',

    constructor: function (_cfg) {
    	this.orderNo=_cfg.orderNo;
		url=__ctxPath + "/creditFlow/finance/listOfInverstPersonBpFundIntent.do?orderNo="+this.orderNo
    	var panel=Ext.create('tableHeader',{style:"margin-left:10px;",header:new Array("<span class='tablehead' >期数</span>",
    	                                                      "<span class='tablehead' >计划到账日</span>",
    	                                                      "<span class='tablehead' >资金类型</span>")});
    	Ext.apply(_cfg,{
    		modeltype:"InvestPersonBpFundIntentList",
    		flex:1,
    		width:"100%",
		    height:"100%",
    	//	items:[panel],
    		fields:[{
				name : 'fundIntentId'
			}, {
				name : 'fundType'
			}, {
				name : 'fundTypeName'
			}, {
				name : 'incomeMoney'
			}, {
				name : 'payMoney'
			}, {
				name : 'intentDate'
			},{
				name : 'investPersonId'
			},{
				name : 'investPersonName'
			},{
				name : 'remark'
			},{
				name : 'payintentPeriod'
			},{
				name : 'isValid'
			},{
				name : 'factDate'
			},{
				name : 'afterMoney'
			},{
				name : 'notMoney'
			},{
				name : 'flatMoney'
			},{
				name : 'fundResource'
			},{
				name : 'preceptId'
			},{
			name : 'bidPlanId'
			},{
			name : 'orderNo'
			}],
    	        url : url,
	    		root:'result',
	    	    totalProperty: 'totalCounts',
 		        itemTpl: "<span   class='tablelist' >{investPersonName}</span>" +
 		        "<span  class='tablelist'>第{payintentPeriod}期"+
 		      
 		         " </span>&nbsp;&nbsp;" +
		    		"<span   class='tablelist' >{intentDate}</span>&nbsp;&nbsp;" +
		    		"<span   class='tablelist' >{fundTypeName}</span>" +
		    		"<span   class='tablelist' >+{incomeMoney}元</span>" +
		    		"<span   class='tablelist' >-{payMoney}元</span>" 
		    		
		    		
 	       /* listeners : {
				itemsingletap : this.itemsingletap
			}*/
    	});
    	this.callParent([_cfg]);

    },
	itemsingletap : function(obj, index, target, record) {
    	  var data=record.data;
    	  var label = new Array("资金来源","投资方","投资金额","投资比例"
    	 ); 
    	  var value = new Array(data.fundResource,data.investPersonName,data.investMoney,data.investPercent);
          getListDetail(label,value);
		    

}
});
