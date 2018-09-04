

Ext.define('htmobile.customer.person.menudetail.BusinessContactList', {
    extend: 'mobile.List',
    
    name: 'BusinessContactList',

    constructor: function (config) {
		this.personId=config.data.id;
    	config = config || {};
    	var panel=Ext.create('tableHeader',{header:new Array("<span class='tablehead' >业务品种</span>",
    	                                                      "<span class='tablehead' >项目名称</span>",
    	                                                      "<span class='tablehead' >项目金额</span>")});
    	Ext.apply(config,{
    		modeltype:"BusinessContactList",
    		flex:1,
    		width:"100%",
		    height:"100%",
    		title:config.title,
    		items:[panel],
    		fields:[ {
				name : 'projectId',
				type : 'long'
			}, 'businessType',
			'projectName',
			'projectMoney',
			'startDate',
			'endDate',
			'status',
			'principalMoney',
			'interestMoney'
			],
    	    	url : __ctxPath + "/creditFlow/getBusinessCreditProject.do",
	    		root:'result',
	    	    totalProperty: 'totalCounts',
	    	    params : {
						customerId : this.personId,
						customerType:"person_customer"
			},
		    itemTpl: "<span  class='tablelist'><tpl if='businessType==\"SmallLoan\"'>小额贷款</tpl>" +
		    		                           "<tpl if='businessType==\"Guarantee\"'>担保</tpl>" +
		    		                           "<tpl if='businessType==\"Financing\"'>融资</tpl></span>&nbsp;&nbsp;" +
		    		"<span   class='tablelist' >{projectName}</span>&nbsp;&nbsp;" +
		    		"<span   class='tablelist' >{projectMoney}元</span>" +
		    		"<span class='tableDetail'>></span>",
		    		
 	        listeners : {
				itemsingletap : this.itemsingletap
			}
    	});

    	this.callParent([config]);

    },
	itemsingletap : function(obj, index, target, record) {
		    	
    	  var data=record.data;
    	  var label = new Array("业务品种","项目名称","项目金额(元)","开始日期",
    	  "结束日期","本金逾期金额","利息逾期金额","状态"
    	); 
    	  var value = new Array(data.businessType=="SmallLoan"?"小额贷款":(data.businessType=="Guarantee"?"金融担保":"资金融资"),data.projectName,data.projectMoney,data.startDate,
    	  data.endDate, data.principalMoney, data.interestMoney, data.status);  
            var xtype = new Array(null,"textareafield",null,null,null,null,null,null);
    	  getListDetail(label,value,xtype);
		    

}
});
