

Ext.define('htmobile.customer.person.menudetail.BankInfoList', {
    extend: 'mobile.List',
    
    name: 'bankInfoList',

    constructor: function (config) {
		this.personId=config.data.id;
		this.type=config.type;
    	config = config || {};
    	var panel=Ext.create('tableHeader',{style:"margin-left:10px;",header:new Array("<span class='tablehead' >开户类型</span>",
    	                                                      "<span class='tablehead' >账户类型</span>",
    	                                                      "<span class='tablehead' >银行名称</span>")});
    	Ext.apply(config,{
    		flex:1,
    		title:"银行开户",
    		items:[panel],
    		fields:[ {
					
				
					
					name : 'id'
				},{
					name : 'enterpriseid'
				}, {
					name : 'bankid'
				}, {
					name : 'bankname'
				}, {
					name : 'accountnum'
				}, {
					name : 'openType'
				},{
					name : 'accountType'
				},{
					name : 'iscredit'
				},{
					name : 'creditnum'
				},{
					name : 'creditpsw'
				},{
					name : 'remarks'
				},{
					name : 'isEnterprise'
				},{
					name : 'openCurrency'
				},{
					name : 'name'
				},{
				    name : 'outletsname'
				},{
					name : 'areaName'
				},{
					name : 'bankOutletsName'
				}],
    	      	url : __ctxPath + '/creditFlow/customer/common/queryListEnterpriseBank.do',
	    		root:'topics',
	    	    totalProperty: 'totalCounts',
	    	    modeltype:"bankInfoList",
	    	    params : {
						id : this.personId,
						  isEnterpriseStr:this.type,
						  isInvest:1
			},
		    itemTpl: "<span  class='tablelist'><tpl if='openType==0'>个人</tpl><tpl if='openType==1'>公司</tpl></span>&nbsp;&nbsp;" +
		    		"<span   class='tablelist' ><tpl if='accountType==0'>个人储蓄户</tpl><tpl if='accountType==1'>基本户</tpl><tpl if='accountType==2'>一般户</tpl></span>&nbsp;&nbsp;" +
		    		"<span   class='tablelist' >{bankname}</span>" +
		    		"<span class='tableDetail'>></span>",
		    		
 	        listeners : {
				itemsingletap : this.itemsingletap
			}
    	});

    	this.callParent([config]);

    },
	itemsingletap : function(obj, index, target, record) {
		    	
    	  var data=record.data;
    	  var label = new Array("开户类型","账户类型","银行名称","网点名称","开户地区",
    	 "银行开户类别","是否是放款账户","开户名称","贷款卡卡号"); 
    	  var value = new Array(data.openType==0?"个人":"公司", data.accountType==0?"个人储蓄户":(data.accountType==1?"基本户":"一般户"),data.bankname,data.bankOutletsName,data.areaName,
    	  data.openCurrency==0?"本币开户":"外币开户",data.iscredit==0?'是':'否',data.name,data.accountnum);  
          getListDetail(label,value);
		    

}
});
