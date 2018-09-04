
//creditorList.js
Ext.define('htmobile.creditFlow.public.InvestPersonInfoList', {
    extend: 'mobile.List',
    
    name: 'InvestPersonInfoList',

    constructor: function (_cfg) {
				if (typeof (_cfg.projectId) != "undefined") {
				this.projectId = _cfg.projectId;
			}
			if (typeof (_cfg.bussinessType) != "undefined") {
				this.bussinessType = _cfg.bussinessType;
			}
		Ext.applyIf(this, _cfg);
		if(this.bidPlanId!=null&&""!=this.bidPlanId&&typeof(this.bidPlanId)!="undefined"){
		    url =  __ctxPath + "/customer/listByMoneyPlanIdInvestPersonInfo.do?Q_bidPlanId_L_EQ="+this.bidPlanId;
		}else if(this.object.getCmpByName('platFormBpFundProject.id')!=null &&null!=this.object.getCmpByName('platFormBpFundProject.id').getValue()&&""!=this.object.getCmpByName('platFormBpFundProject.id').getValue()){
			url =  __ctxPath + "/customer/listByMoneyPlanIdInvestPersonInfo.do?Q_moneyPlanId_L_EQ="+this.object.getCmpByName('platFormBpFundProject.id').getValue();
		}else if(this.projectId==null && this.bussinessType==null && this.enId!=null){
			url =  __ctxPath + "/customer/listByProjectIdInvestPersonInfo.do?projectId="+this.projectId+"&businessType="+this.bussinessType;
		}		
		else{
			url =  __ctxPath + "/customer/listByProjectIdInvestPersonInfo.do?projectId="+this.projectId+"&businessType="+this.bussinessType;
		}
	
    	var panel=Ext.create('tableHeader',{style:"margin-left:10px;",header:new Array("<span class='tablehead' >资金来源</span>",
    	                                                      "<span class='tablehead' sytle='width:35%'>投资方</span>",
    	                                                      "<span class='tablehead' sytle='width:23%'>投资金额</span>")});
    	Ext.apply(_cfg,{
    		modeltype:"InvestPersonInfoList",
    		flex:1,
    		width:"100%",
		    height:"100%",
    		title:"投资人信息",
   // 		items:[panel],
    		fields:[
									{
										name : 'investId'
									},
									{
										name : 'investPersonId'
									},
									{
										name : 'investPersonName'
									},
									{
										name : 'investMoney'
									},
									{
										name : 'investPercent'
									},{
										name : 'remark'
									},{
										name : 'fundResource'
									},{
										name : 'orderNo'
									},{
										name : 'contractUrls'
									}
							],
    	        url : url,
	    		root:'result',
	    	    totalProperty: 'totalCounts',
 		        itemTpl: "<span  class='tablelist'>"+
 		                 " <tpl if='fundResource==0'>个人</tpl>" +
 		                 " <tpl if='fundResource==1'>企业</tpl>" +
 		         " </span>&nbsp;&nbsp;" +
		    		"<span   class='tablelist' sytle='width:35%;'>{investPersonName}</span>&nbsp;&nbsp;" +
		    		"<span   class='tablelist' sytle='width:29%;'>投资{investMoney}元</span>" +
		    		"<span   class='tablelist'  >投资比例{investPercent}%</span>" +
		    		"<span   class='tablelist' style='color:#a7573b;text-decoration:underline' onclick='javascript:seeIntent(\"{orderNo}\",\"{investPersonName}\");'>款项计划表</span>" +
		    		"<span   class='tablelist' style='color:#a7573b;text-decoration:underline' >合同下载</span>" 
		    	
		    		
 	       /* listeners : {
				itemsingletap : this.itemsingletap
			}*/
    	});
    	
    	seeIntent=function(orderNo,investPersonName){
    		 mobileNavi.push(
    		  Ext.create('htmobile.creditFlow.public.InvestPersonBpFundIntentList',{
				        investPersonName:investPersonName,
					    orderNo:orderNo
			        	})
			        	);
    		
    	}
    	this.callParent([_cfg]);

    },
	itemsingletap : function(obj, index, target, record) {
    	  var data=record.data;
    	  var label = new Array("资金来源","投资方","投资金额","投资比例"
    	 ); 
    	  var value = new Array(data.fundResource,data.investPersonName,data.investMoney,data.investPercent);
          getListDetail(label,value);
		    

}
});
