
//creditorList.js
Ext.define('htmobile.creditFlow.public.SlFundIntentMergeList', {
    extend: 'mobile.List',
    
    name: 'SlFundIntentMergeList',

    constructor: function (_cfg) {
    	 Ext.applyIf(this, _cfg);   
		var url=__ctxPath + "/creditFlow/finance/listloanBpFundIntent.do"
    	var panel=Ext.create('tableHeader',{style:"margin-left:10px;",header:new Array("<span class='tablehead' >期数</span>",
    	                                                      "<span class='tablehead' >计划到账日</span>",
    	                                                   "<span class='tablehead' >资金类型</span>")});
    
    	Ext.apply(_cfg,{
    		width:"100%",
		    height:"100%",
    		modeltype:"SlFundIntentMergeList",
    		flex:1,
    		width:"100%",
		    height:"100%",
    	//	items:[panel],
    		fields:[{
					name : 'payintentPeriod'
				}, {
					name : 'principal'
				}, {
					name : 'interest'
				}, {
					name : 'consultationMoney'
				}, {
					name : 'serviceMoney'
				}, {
					name : 'intentDate'
				}, {
					name : 'sumMoney'
				}],
    	        url : url,
	    		root:'result',
	    	    totalProperty: 'totalCounts',
	    	    params:{
	    	            projectId : this.projectId,
						flag1 : 1,
						bidPlanId:this.bidPlanId,
						preceptId:this.preceptId,
						slEarlyRepaymentId: this.slEarlyRepaymentId,
						businessType : this.businessType
	    	    },
 		        itemTpl: new Ext.XTemplate( "<span  class='tablelist' style='width:50%'>第<font style='color:#a7573b;'>{payintentPeriod}</font>期</span>&nbsp;&nbsp;"+
 		            "<span   class='tablelist' style='width:50%'>{intentDate}</span></span>&nbsp;&nbsp;" +
		    		"<span   class='tablelist' style='width:50%'>本金:<font style='color:#a7573b;'>{principal:this.numberFormat}</font>元</span>&nbsp;&nbsp;" +
		    		"<span   class='tablelist' style='width:50%'>利息:<font style='color:#a7573b;'>{interest:this.numberFormat}</font>元</span></span>&nbsp;&nbsp;" +
		    		"<span   class='tablelist' style='width:50%'>咨询管理:<font style='color:#a7573b;'>{consultationMoney:this.numberFormat}</font>元</span></span>&nbsp;&nbsp;" +
		    		"<span   class='tablelist' style='width:50%'>财务服务:<font style='color:#a7573b;'>{serviceMoney:this.numberFormat}</font>元</span></span>&nbsp;&nbsp;" +
		    		"<span   class='tablelist' style='width:50%'>总和:<font style='color:#a7573b;'>{sumMoney:this.numberFormat}</font>元</span>",{
		    			numberFormat: function(num) {
		    				var num = new Number(num);
						  return (num.toFixed(2) + '').replace(/\d{1,3}(?=(\d{3})+(\.\d*)?$)/g, '$&,');
						}  
		    			
		    		})
 		       
		    		
		    		
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
		    

},
 numberFormat :function(num){
//给数字加千分位
    return (num.toFixed(2) + '').replace(/\d{1,3}(?=(\d{3})+(\.\d*)?$)/g, '$&,');
}
});
