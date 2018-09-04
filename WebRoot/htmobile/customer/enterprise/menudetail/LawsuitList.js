//bplawsuitList.js




Ext.define('htmobile.customer.enterprise.menudetail.LawsuitList', {
    extend: 'mobile.List',
    
    name: 'LawsuitList',

    constructor: function (config) {
		this.enterpriseId=config.data.id;
		this.type=config.type;
    	config = config || {};
    	var panel=Ext.create('tableHeader',{style:"margin-left:10px;",header:new Array("<span class='tablehead'> 立案时间</span>",
    	                                                      "<span class='tablehead' >角色</span>")});
    	Ext.apply(config,{
    		modeltype:"LawsuitList",
    		flex:1,
    		title:"诉讼情况",
    		items:[panel],
    		fields:[
						'registerDate'
						,'registerReason'
						,'relatedMoney'
						,'role'
						,'recordUser'
						,'recordTime'
				],
    	        url : __ctxPath + '/creditFlow/customer/enterprise/listBpCustEntLawsuit.do',
	    		root:'result',
	    	    totalProperty: 'totalCounts',
	    	    params : {
						enterpriseId : this.enterpriseId
			},
		    itemTpl: "<span  class='tablelist'>{registerDate}</span>&nbsp;&nbsp;" +
		    		"<span   class='tablelist' >{role}</span>&nbsp;&nbsp;" +
		    		"<span class='tableDetail'>></span>",
		    		
 	        listeners : {
				itemsingletap : this.itemsingletap
			}
    	});

    	this.callParent([config]);

    },
	itemsingletap : function(obj, index, target, record) {
		    	
    	  var data=record.data;
    	   var label = new Array("立案时间","立案原因","金额(元)","角色","录入人","录入时间"
    	 ); 
    	  var value = new Array(data.registerDate,data.registerReason,data.relatedMoney,
    	  data.role,data.recordUser,data.recordTime);  
    	
          getListDetail(label,value);
		    

}
});
