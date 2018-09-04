
//debtList.js
Ext.define('htmobile.customer.enterprise.menudetail.DebtList', {
    extend: 'mobile.List',
    
    name: 'DebtList',

    constructor: function (config) {
		this.enterpriseId=config.data.id;
		this.type=config.type;
    	config = config || {};
    	var panel=Ext.create('tableHeader',{style:"margin-left:10px;",header:new Array("<span class='tablehead' >债权人</span>",
    	                                                      "<span class='tablehead' >借款金额(万)</span>",
    	                                                      "<span class='tablehead' >已还金额(万)</span>")});
    	Ext.apply(config,{
    		modeltype:"DebtList",
    		flex:1,
    		title:"债务情况",
    		items:[panel],
    		fields:[{
							name : 'id'
						}, {
							name : 'enterpriseid'
						}, {
							name : 'zwrpid'
						},{
							name : 'zwrname'
						} ,{
							name : 'creditmoney'
						}, {
							name : 'creditstartdate'
						}, {
							name : 'creditenddate'
						}, {
							name : 'repayment'
						}, {
							name : 'borrowemoney'
						}, {
							name : 'lastpaydate'
						}, {
							name : 'repaymentway'
						}, {
							name : 'voucherway'
						}, {
							name : 'assurecondition'
						}, {
							name : 'remarks'
						}, {
							name : 'chfs'
						}, {
							name : 'dbqk'
						}, {
							name : 'pzfs'
						},{
							name : 'repaymentwayValue'
						},{
							name : 'voucherwayValue'
						}],
    	      	url : __ctxPath + '/creditFlow/customer/enterprise/queryEnterpriseDebt.do',
	    		root:'topics',
	    	    totalProperty: 'totalProperty',
	    	    params : {
						id : this.enterpriseId
			},
		    itemTpl: "<span  class='tablelist'>{zwrname}</span>&nbsp;&nbsp;" +
		    		"<span   class='tablelist' >{creditmoney}</span>&nbsp;&nbsp;" +
		    		"<span   class='tablelist' >{repayment}</span>" +
		    		"<span class='tableDetail'>></span>",
		    		
 	        listeners : {
				itemsingletap : this.itemsingletap
			}
    	});

    	this.callParent([config]);

    },
	itemsingletap : function(obj, index, target, record) {
    	  var data=record.data;
    	  var label = new Array("债务人","借款金额(万)","已还金额(万)","借款余额(万)","借款截止日期",
    	 "偿还方式","凭证方式"); 
    	  var value = new Array(data.zqrpname,data.creditmoney,data.repayment,data.bowmoney,
    	  data.creditenddate,data.repaywayValue,data.voucherwayValue);  
          getListDetail(label,value);
		    

}
});
