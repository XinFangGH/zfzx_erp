
//creditorList.js
Ext.define('htmobile.creditFlow.public.SlActualToChargeList', {
    extend: 'mobile.List',
    
    name: 'SlActualToChargeList',

    constructor: function (_cfg) {
    	Ext.applyIf(this, _cfg);
    		if (typeof(_cfg.projId) != "undefined") {
			this.projectId = _cfg.projId;
		}
		if (_cfg.isHidden) {
			this.isHidden = _cfg.isHidden;
		}
		if (_cfg.serviceHidden) {
			this.serviceHidden = _cfg.serviceHidden;
		}
		if (_cfg.isHiddenPayMoney) {//add by gao
			this.isHiddenPayMoney = _cfg.isHiddenPayMoney;
		}
		if (typeof(_cfg.businessType) != "undefined") {
			this.businessType = _cfg.businessType;
		}
		if (typeof(_cfg.slSuperviseRecordId) != "undefined") {
			this.slSuperviseRecordId = _cfg.slSuperviseRecordId;
		}
		if (typeof(_cfg.isUnLoadData) != "undefined") {
			this.isUnLoadData = _cfg.isUnLoadData;
		}
		if (typeof(_cfg.isThisSuperviseRecord) != "undefined") {
			this.isThisSuperviseRecord = _cfg.isThisSuperviseRecord;
		}
		if(typeof(_cfg.isOutLay)!="undefined"){
			this.isOutLay = true;
		}
    	var url = __ctxPath
				+ "/creditFlow/finance/listbyprojectSlActualToCharge.do?projectId="
				+ this.projectId + "&isUnLoadData=" + this.isUnLoadData
				+ "&businessType=" + this.businessType;
		if (this.bidPlanId) {
			url = __ctxPath
					+ "/creditFlow/finance/listbyBidPlanIdSlActualToCharge.do?bidPlanId="
					+ this.bidPlanId + "&isUnLoadData=" + this.isUnLoadData;
		}
		
		if (this.isThisSuperviseRecord != null) {
			url = __ctxPath
					+ "/creditFlow/finance/listbySuperviseRecordSlActualToCharge.do?slSuperviseRecordId="
					+ this.slSuperviseRecordId + "&isThisSuperviseRecord="
					+ this.isThisSuperviseRecord + "&isUnLoadData="
					+ this.isUnLoadData + "&businessType=" + this.businessType
					+ "&projectId=" + this.projectId;

		}
    	var panel=Ext.create('tableHeader',{style:"margin-left:10px;",header:new Array("<span class='tablehead' >费用类型</span>",
    	                                                      "<span class='tablehead' >计划到账日</span>",
    	                                                      "<span class='tablehead' >计划收入金额</span>")});
    	Ext.apply(_cfg,{
    		modeltype:"SlActualToChargeList",
    		flex:1,
    		width:"100%",
		    height:"100%",
    		title:"费用明细",
    //		items:[panel],
    		fields:[{
						name : 'actualChargeId'
					}, {
						name : 'planChargeId'
					}
					, {
						name : 'typeName'
					},{
						name:'payMoney'
					}, {
						name : 'planCharges'
					}, {
						name : 'chargeStandard'
					}, {
						name : 'payMoney'
					}, {
						name : 'incomeMoney'
					}, {
						name : 'intentDate'
					}
					, {
						name : 'remark'
					}],
    	        url :url,
	    		root:'result',
	    	    totalProperty: 'totalCounts',
		        itemTpl: "<span  class='tablelist'>{typeName}</span>&nbsp;&nbsp;" +
		    		"<span   class='tablelist' >{chargeStandard}</span>&nbsp;&nbsp;" +
		    		"<span   class='tablelist' >{intentDate}</span>" +
		    		"<span   class='tablelist' >+{incomeMoney}元</span>" +
		    		"<span   class='tablelist' >-{payMoney}元</span>" +
		    		"<span   class='tablelist' >{remark}</span>" +
		    		"<span class='tableDetail'>></span>",
		    		
 	        listeners : {
				itemsingletap : this.itemsingletap
			}
    	});
    	this.callParent([_cfg]);

    },
	itemsingletap : function(obj, index, target, record) {
    	  var data=record.data;
    	  var label = new Array("费用类型","费用标准","计划到账日","计划收入金额(元)","计划支出金额(元)",
    	 "备注"); 
    	  var value = new Array(data.typeName,data.chargeStandard,data.intentDate,data.incomeMoney,
    	  data.payMoney,data.remark);  
          getListDetail(label,value);
		    

}
});
