//upAndDownContract.js


Ext.define('htmobile.customer.enterprise.menudetail.UpStreamContract', {
    extend: 'mobile.List',
    
    name: 'UpStreamContract',

    constructor: function (config) {
		this.enterpriseId=config.data.id;
		this.upAndDownContractId=config.upAndDownContractId;
		this.type=config.type;
    	config = config || {};
    	var panel=Ext.create('tableHeader',{style:"margin-left:10px;",header:new Array("<span class='tablehead'> 主要采购对象  </span>",
    	                                                      "<span class='tablehead' >采购合同金额</span>","<span class='tablehead' >合同期限</span>")});
    	Ext.apply(config,{
    		modeltype:"UpStreamContract",
    		flex:1,
    		width:"100%",
		    height:"100%",
    	    title:"<span style='font-size:16px;'>上游渠道合同</span>",
    		items:[panel],
    		fields:['mainBuyer'
						,'buyerContractMoney'
						,'buyerContractDuration'
						,'contractPolicy'
						
																																	],
    	    		url : __ctxPath + '/creditFlow/customer/enterprise/UpstreamlistBpCustEntUpanddowncontract.do',
	    		root:'result',
	    	    totalProperty: 'totalCounts',
	    	    params : {
						upAndDownContractId : this.upAndDownContractId
			},
		    itemTpl: "<span  class='tablelist'>{mainBuyer}</span>&nbsp;&nbsp;" +
		    		"<span   class='tablelist' >{buyerContractMoney}</span>&nbsp;&nbsp;" +
		    		"<span   class='tablelist' >{buyerContractDuration}</span>&nbsp;&nbsp;" +
		    		"<span class='tableDetail'>></span>",
		    		
 	        listeners : {
				itemsingletap : this.itemsingletap
			}
    	});

    	this.callParent([config]);

    },
	itemsingletap : function(obj, index, target, record) {
		    	
    	  var data=record.data;
    	   var label = new Array("主要采购对象","采购合同金额","合同期限","合同政策"); 
    	  var value = new Array(data.mainBuyer,data.buyerContractMoney,data.buyerContractDuration,data.contractPolicy
    	  );  
          getListDetail(label,value);
		    

}
});
Ext.define('htmobile.customer.enterprise.menudetail.DownStreamContract', {
    extend: 'mobile.List',
    
    name: 'DownStreamContract',

    constructor: function (config) {
		this.enterpriseId=config.data.id;
		this.upAndDownContractId=config.upAndDownContractId;
		this.type=config.type;
    	config = config || {};
    	var panel=Ext.create('tableHeader',{style:"margin-left:10px;",header:new Array("<span class='tablehead'> 日期模型  </span>",
    	                                                      "<span class='tablehead' >件数</span>","<span class='tablehead' >合同金额</span>")});
    	Ext.apply(config,{
    		modeltype:"DownStreamContract",
    		flex:1,
    		width:"100%",
		    height:"100%",
    	    title:"<span style='font-size:16px;'>下游渠道合同</span>",
    		items:[panel],
    		fields:['dateMode'
							,'count'
							,'contractMoney'
							,'saleIncomeyMoney'
							,'noContractSaleMoney'
							
																																	],
    	    	url : __ctxPath + '/creditFlow/customer/enterprise/DownstreamlistBpCustEntUpanddowncontract.do',
	    		root:'result',
	    	    totalProperty: 'totalCounts',
	    	    params : {
						upAndDownContractId : this.upAndDownContractId
			},
		    itemTpl: "<span  class='tablelist'>{dateMode}</span>&nbsp;&nbsp;" +
		    		"<span   class='tablelist' >{count}</span>&nbsp;&nbsp;" +
		    		"<span   class='tablelist' >{contractMoney}</span>&nbsp;&nbsp;" +
		    		"<span class='tableDetail'>></span>",
		    		
 	        listeners : {
				itemsingletap : this.itemsingletap
			}
    	});

    	this.callParent([config]);

    },
	itemsingletap : function(obj, index, target, record) {
		    	
    	  var data=record.data;
    	   var label = new Array("日期模型 ","件数","合同金额","销售收入实现金额","无合同销售额"); 
    	  var value = new Array(data.dateMode,data.count,data.contractMoney,data.saleIncomeyMoney,
    	  data.noContractSaleMoney);  
          getListDetail(label,value);
		    

}
});

Ext.define('htmobile.customer.enterprise.menudetail.UpAndDownContractTab', {
	extend: 'Ext.TabPanel',
    
    name: 'UpAndDownContractTab',

    constructor: function (config) {
    	var data=config.data;
    	var upremarks=config.upremarks;
	    var downremarks=config.downremarks;
    	this.upAndDownContractId=config.upAndDownContractId;
	    var UpStreamCustom=Ext.create('htmobile.customer.enterprise.menudetail.UpStreamContract',{data:data,upAndDownContractId:this.upAndDownContractId}); 
	    var DownStreamCustom=Ext.create('htmobile.customer.enterprise.menudetail.DownStreamContract',{data:data,upAndDownContractId:this.upAndDownContractId}); 
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
		                    label: '<div class="fieldlabel">上游渠道分析:</div>',
		                	readOnly: true,
		                	value:upremarks,
		                	width:"100%"
		                	
		                },{
	    	            	labelAlign:"top",
		                	xtype: 'textareafield',
		                    label: '<div class="fieldlabel">下游渠道分析:</div>',
		                	readOnly: true,
		                	value:downremarks,
		                	width:"100%"
		                	
		                }]
	    	        }]
	            },
	            {
	            	title: '<div style="font-size:10px;">上游渠道合同</div>',
	                items:[{
	    	            xtype: 'fieldset',
	    	            items:UpStreamCustom
	    	        }]
	            },
	            {
	            	title: '<div style="font-size:10px;">下游渠道合同</div>',
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

