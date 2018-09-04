

Ext.define('htmobile.customer.person.menudetail.BusinessCcreditLoanHistoryList', {
    extend: 'mobile.List',
    
    name: 'BusinessCcreditLoanHistoryList',

    constructor: function (config) {
		this.personId=config.data.id;
    	config = config || {};
    	var panel=Ext.create('tableHeader',{header:new Array("<span class='tablehead' >借款机构</span>",
    	                                                      "<span class='tablehead' >借款金额</span>",
    	                                                      "<span class='tablehead' >是否有逾期</span>")});
    	Ext.apply(config,{
    		modeltype:"BusinessCcreditLoanHistoryList",
    		flex:1,
    		width:"100%",
		    height:"100%",
    		title:config.title,
    		items:[panel],
    		fields:[{
				name:'id'
			},{
				name:'bankName'
			},{
				name:'loanMoney'
			},{
				name:'loanPeriod'
			},{
				name:'isHaveOverDue'
			},{
				name:'loanStartDate'
			},{
				name:'loanEndDate'
			},{
				name:'loanState'
			},{
				name:'personId'
			}],
    	    	url : __ctxPath+'/creditFlow/customer/person/ajaxQueryPersonCreditLoanHistory.do',
	    		root:'topics',
	    	    totalProperty: 'totalProperty',
	    	    params : {
						personId : this.personId
			},
		    itemTpl: "<span  class='tablelist'>{bankName}</span>&nbsp;&nbsp;" +
		    		"<span   class='tablelist' >{loanMoney}</span>&nbsp;&nbsp;" +
		    		"<span   class='tablelist' >{isHaveOverDue}</span>" +
		    		"<span class='tableDetail'>></span>",
		    		
 	        listeners : {
				itemsingletap : this.itemsingletap
			}
    	});

    	this.callParent([config]);

    },
	itemsingletap : function(obj, index, target, record) {
    	  var data=record.data;
    	  var label = new Array("借款机构","借款金额","是否有逾期","开始日期","结束日期","状态"
    	); 
    	  var value = new Array(data.bankName,data.loanMoney,data.isHaveOverDue==true?"是":"否",data.loanStartDate,data.loanEndDate
    	  ,data.loanState);  
          getListDetail(label,value);
		    

}
});
