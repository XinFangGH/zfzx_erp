
//creditorList.js
Ext.define('htmobile.creditFlow.public.main.OurProcreditAssuretenetProductList', {
    extend: 'mobile.List',
    
    name: 'OurProcreditAssuretenetProductList',

    constructor: function (_cfg) {
    	Ext.applyIf(this, _cfg);
    	var url="";
		if(this.projectId){
			url = __ctxPath+ "/assuretenet/listByProjectIdOurProcreditAssuretenet.do?projectId="+this.projectId
		}else{
			url = __ctxPath+ "/assuretenet/listByProjectIdOurProcreditAssuretenet.do?productId="+this.productId
		}
    	var panel=Ext.create('tableHeader',{style:"margin-left:10px;",header:new Array("<span class='tablehead' >归档材料名称</span>",
    	                                                      "<span class='tablehead' >线下份数</span>",
    	                                                      "<span class='tablehead' >是否归档</span>")});
    	Ext.apply(_cfg,{
    		modeltype:"OurProcreditAssuretenetProductList",
    		flex:1,
    		width:"100%",
		    height:"100%",
    		title:"贷款必备条件",
    		items:[panel],
    		fields:[{
								name : 'assuretenetId'
							},{
								name : 'assuretenet'
							},{
								name : 'outletopinion'
							},{
								name : 'isfile'
							},{
								name : 'xxnums'
							},{
								name : 'remark'
							},{
								name : 'platFormoutletopinion'
							}
						],
	        url :url,
    		root:'result',
    	    totalProperty: 'totalCounts',
	        itemTpl: new Ext.XTemplate("<span  class='tablelist'>{assuretenet}</span>&nbsp;&nbsp;" +
	    		"<span   class='tablelist' >{xxnums}份</span>&nbsp;&nbsp;" +
	    		"<span   class='tablelist' >{isfile}</span>" +
	    		"<span class='tableDetail'>></span>"),
 	        listeners : {
				itemsingletap : this.itemsingletap
			}
    	});
    	this.callParent([_cfg]);

    },
	itemsingletap : function(obj, index, target, record) {
    	  var data=record.data;
    	  var label = new Array("归档材料名称","线下份数","是否归档","备注"
    	 ); 
    	  var value = new Array(data.assuretenet,data.xxnums,data.isfile,data.remark
    	);  
    	 var xtype = new Array(null,null,null,"textareafield");
          getListDetail(label,value,xtype);
		    

}
});
