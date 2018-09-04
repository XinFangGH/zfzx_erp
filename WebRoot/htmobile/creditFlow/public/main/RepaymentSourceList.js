
//creditorList.js
Ext.define('htmobile.creditFlow.public.main.RepaymentSourceList', {
    extend: 'mobile.List',
    
    name: 'RepaymentSourceList',

    constructor: function (_cfg) {
    	Ext.applyIf(this, _cfg);
    	var url =__ctxPath+ '/creditFlow/repaymentSource/listSlRepaymentSource.do?projectId='+ this.projectId
    	var panel=Ext.create('tableHeader',{style:"margin-left:10px;",header:new Array("<span class='tablehead' >来源类型</span>",
    	                                                      "<span class='tablehead' >资金规模</span>",
    	                                                      "<span class='tablehead' >资金到位时间</span>")});
    	Ext.apply(_cfg,{
    		modeltype:"RepaymentSourceList",
    		flex:1,
    		width:"100%",
		    height:"100%",
    		title:"第一还款来源",
    		items:[panel],
    		fields:[{
										name : 'sourceId'
									}, {
										name : 'typeId'
									}/*
										 * , { name : 'objectName' }
										 */, {
										name : 'money'
									}, {
										name : 'repaySourceDate'
									}, {
										name : 'remarks'
									}, {
										name : 'typeName'
									}

							],
    	        url :url,
	    		root:'result',
	    	    totalProperty: 'totalCounts',
		        itemTpl: new Ext.XTemplate(  "<span  class='tablelist'>{typeName}</span>&nbsp;&nbsp;" +
		    		"<span   class='tablelist' >{money:this.numberFormat}元</span>&nbsp;&nbsp;" +
		    		"<span   class='tablelist' >{repaySourceDate}</span>" +
		    		"<span class='tableDetail'>></span>",{
		    			numberFormat: function(num) {
		    				var num = new Number(num);
						  return (num.toFixed(2) + '').replace(/\d{1,3}(?=(\d{3})+(\.\d*)?$)/g, '$&,');
						}  
		    			
		    		}),
 	        listeners : {
				itemsingletap : this.itemsingletap
			}
    	});
    	this.callParent([_cfg]);

    },
	itemsingletap : function(obj, index, target, record) {
    	  var data=record.data;
    	  var label = new Array("来源类型","资金规模(元)","资金到位时间","备注"
    	 ); 
    	  var value = new Array(data.typeName,data.money,data.repaySourceDate,data.remarks
    	);  
    	 var xtype = new Array(null,null,null,"textareafield");
          getListDetail(label,value,xtype);
		    

}
});
