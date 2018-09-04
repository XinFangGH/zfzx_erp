//upAndDownStream.js





Ext.define('htmobile.customer.enterprise.menudetail.UpStreamCustom', {
    extend: 'mobile.List',
    
    name: 'UpStreamCustom',

    constructor: function (config) {
		this.enterpriseId=config.data.id;
		this.upAndDownCustomId=config.upAndDownCustomId;
		this.type=config.type;
    	config = config || {};
    	var panel=Ext.create('tableHeader',{style:"margin-left:10px;",header:new Array("<span class='tablehead'> 原材料供应商  </span>",
    	                                                      "<span class='tablehead' >合作年限</span>","<span class='tablehead' >供货商品</span>")});
    	Ext.apply(config,{
    		modeltype:"UpStreamCustom",
    		flex:1,
    		width:"100%",
		    height:"100%",
    	    title:"<span style='font-size:16px;'>主要上游客户及结算方式</span>",
    		items:[panel],
    		fields:['materialSupplier'
						,'cooperativeDuration'
						,'supplyGoods'
						,'yearSupplyNumber'
						,'marketPrice'
						,'settleType'
																																	],
    	    	url : __ctxPath + '/creditFlow/customer/enterprise/UpstreamlistBpCustEntUpanddownstream.do',
	    		root:'result',
	    	    totalProperty: 'totalCounts',
	    	    params : {
						upAndDownCustomId : this.upAndDownCustomId
			},
		    itemTpl: "<span  class='tablelist'>{materialSupplier}</span>&nbsp;&nbsp;" +
		    		"<span   class='tablelist' >{cooperativeDuration}</span>&nbsp;&nbsp;" +
		    		"<span   class='tablelist' >{supplyGoods}</span>&nbsp;&nbsp;" +
		    		"<span class='tableDetail'>></span>",
		    		
 	        listeners : {
				itemsingletap : this.itemsingletap
			}
    	});

    	this.callParent([config]);

    },
	itemsingletap : function(obj, index, target, record) {
		    	
    	  var data=record.data;
    	   var label = new Array("原材料供应商  ","合作年限","供货商品","销售数量","市场价格","货款结算方式"); 
    	  var value = new Array(data.materialSupplier,data.cooperativeDuration,data.supplyGoods,data.yearSupplyNumber,
    	  data.marketPrice, data.settleType);  
          getListDetail(label,value);
		    

}
});
Ext.define('htmobile.customer.enterprise.menudetail.DownStreamCustom', {
    extend: 'mobile.List',
    
    name: 'DownStreamCustom',

    constructor: function (config) {
		this.enterpriseId=config.data.id;
		this.upAndDownCustomId=config.upAndDownCustomId;
		this.type=config.type;
    	config = config || {};
    	var panel=Ext.create('tableHeader',{style:"margin-left:10px;",header:new Array("<span class='tablehead'> 原材料供应商  </span>",
    	                                                      "<span class='tablehead' >合作年限</span>","<span class='tablehead' >供货商品</span>")});
    	Ext.apply(config,{
    		modeltype:"DownStreamCustom",
    		flex:1,
    		width:"100%",
		    height:"100%",
    	    title:"<span style='font-size:16px;'>主要下游客户及结算方式</span>",
    		items:[panel],
    		fields:['customName'
							,'cooperativeDuration'
							,'saleGoods'
							,'yearOrderNumber'
							,'salePrice'
							,'settleType'
																																	],
    	    	url : __ctxPath + '/creditFlow/customer/enterprise/DownstreamlistBpCustEntUpanddownstream.do',
	    		root:'result',
	    	    totalProperty: 'totalCounts',
	    	    params : {
						upAndDownCustomId : this.upAndDownCustomId
			},
		    itemTpl: "<span  class='tablelist'>{customName}</span>&nbsp;&nbsp;" +
		    		"<span   class='tablelist' >{cooperativeDuration}</span>&nbsp;&nbsp;" +
		    		"<span   class='tablelist' >{saleGoods}</span>&nbsp;&nbsp;" +
		    		"<span class='tableDetail'>></span>",
		    		
 	        listeners : {
				itemsingletap : this.itemsingletap
			}
    	});

    	this.callParent([config]);

    },
	itemsingletap : function(obj, index, target, record) {
		    	
    	  var data=record.data;
    	   var label = new Array("客户名称 ","合作年限","供货商品","销售数量","市场价格","货款结算方式"); 
    	  var value = new Array(data.customName,data.cooperativeDuration,data.yearOrderNumber,data.saleGoods,
    	  data.salePrice, data.settleType);  
          getListDetail(label,value);
		    

}
});

Ext.define('htmobile.customer.enterprise.menudetail.UpAndDownStreamTab', {
	extend: 'Ext.TabPanel',
    
    name: 'UpAndDownStreamTab',

    constructor: function (config) {
    	var data=config.data;
    	var remarks=config.remarks;
    		this.upAndDownCustomId=config.upAndDownCustomId;
	    var UpStreamCustom=Ext.create('htmobile.customer.enterprise.menudetail.UpStreamCustom',{data:data,upAndDownCustomId:this.upAndDownCustomId}); 
	    var DownStreamCustom=Ext.create('htmobile.customer.enterprise.menudetail.DownStreamCustom',{data:data,upAndDownCustomId:this.upAndDownCustomId}); 
		config = config || {};
	    Ext.apply(config,{
        	title:'上下游客户',
            layoutOnTabChange: true,
            items: [
	            {
	            	title: '<div style="font-size:10px;">说明</div>',
	                items:[{
	    	            xtype: 'fieldset',
	    	            items:[{
	    	            	labelAlign:"top",
		                	xtype: 'textareafield',
		                    label: '<div class="fieldlabel">备注:</div>',
		                	readOnly: true,
		                	value:remarks,
		                	width:"100%"
		                	
		                }]
	    	        }]
	            },
	            {
	            	title: '<div style="font-size:10px;">主要上游客户及结算方式</div>',
	                items:[{
	    	            xtype: 'fieldset',
	    	            items:UpStreamCustom
	    	        }]
	            },
	            {
	            	title: '<div style="font-size:10px;">主要下游客户及结算方式</div>',
	                items:[{
	    	            xtype: 'fieldset',
	    	            items:DownStreamCustom
	    	        }]
	            }
            ]
        });
    
        this.callParent([config]);
	}

});

