/**
 * 手续费用收取清单
 */
Ext.define('htmobile.creditFlow.public.main.SlActualToChargeProduct', {
    extend: 'mobile.List',
    name: 'SlActualToChargeProduct',
    constructor: function (_cfg) {
    	Ext.applyIf(this, _cfg);
    	Ext.apply(_cfg,{
    		style:'background-color:white;',
    		modeltype:"SlActualToChargeProduct",
    		flex:1,
    		width:"100%",
		    height:"100%",
    		title:"手续费用收取清单",
    		items:[{
		    		xtype:'panel',
		    		docked:'top',
		    		items:[{
			    			html:`	
			    					<div class="list-column">
				    					<span>费用类型</span>
				    					<span>费用标准</span>
				    					<span>备注</span>
			    					</div>
			    				`
		    				}]
    	}],
    		fields:[{
						name : 'actualChargeId'
					},{
						name : 'costType'
					},{
						name : 'chargeStandard'
					},{
						name : 'remark'
					}],
    	        url :__ctxPath +'/creditFlow/finance/getByProductIdSlActualToCharge.do?productId='+this.productId,
	    		root:'result',
	    	    totalProperty: 'totalCounts',
		        itemTpl: `
 		        	 	<div class="list-column-content">
	    					<span>{costType}</span>
	    					<span>{chargeStandard}</span>
	    					<span>{remark}</span>
    					</div>
 		        		`,
 	        listeners : {
				itemsingletap : this.itemsingletap
			}
    	});
    	this.callParent([_cfg]);
    },
	itemsingletap : function(obj, index, target, record) {/*
    	  var data=record.data;
    	  var label = new Array("费用类型","费用标准","备注"); 
    	  var value = new Array(data.typeName,data.chargeStandard,data.remark);  
          getListDetail(label,value);
*/}
});
