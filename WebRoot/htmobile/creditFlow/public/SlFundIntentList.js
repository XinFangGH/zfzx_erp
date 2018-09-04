
//creditorList.js
Ext.define('htmobile.creditFlow.public.SlFundIntentList', {
    extend: 'mobile.List',
    
    name: 'SlFundIntentList',

    constructor: function (config) {
		this.enterpriseId=config.data.id;
		this.type=config.type;
    	config = config || {};
    	var panel=Ext.create('tableHeader',{style:"margin-left:10px;",header:new Array("<span class='tablehead' >债务人</span>",
    	                                                      "<span class='tablehead' >借款金额(万)</span>",
    	                                                      "<span class='tablehead' >已还金额(万)</span>")});
    	Ext.apply(config,{
    		modeltype:"CreditorList",
    		flex:1,
    		title:"债权情况",
    		items:[panel],
    		fields:[ {
						name : 'id'
					},{
						name : 'zqrpname' //债权人
					}, {
						name : 'creditmoney' //借款金额(万元) 
					}, {
						name : 'repayment' //已还金额(万元)
					}, {
						name : 'bowmoney'  //借款余额(万元)
					}, {
						name : 'davalue' //偿还方式	
					},{
						name : 'dbvalue'  //凭证方式 
					},{
						name : 'creditstartdate' // 借款起始日期
					},{
						name : 'creditenddate'//借款截止日期
					},{
						name : 'lastdate' //最后一次还款日期
					},{
						name : 'repaywayValue'
					},{
						name : 'voucherwayValue'
					}],
    	        url : __ctxPath + '/creditFlow/customer/enterprise/getAllEnterpriseCreditor.do',
	    		root:'topics',
	    	    totalProperty: 'totalProperty',
	    	    params : {
						eid : this.enterpriseId
			},
		    itemTpl: "<span  class='tablelist'>{zqrpname}</span>&nbsp;&nbsp;" +
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
