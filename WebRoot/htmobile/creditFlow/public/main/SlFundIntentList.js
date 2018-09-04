
//creditorList.js
Ext.define('htmobile.creditFlow.public.main.SlFundIntentList', {
    extend: 'mobile.List',
    name: 'SlFundIntentList',
    constructor: function (_cfg) {
    	if (typeof(_cfg.projectId) != "undefined") {
			this.projectId = _cfg.projectId;
		}
		if (typeof(_cfg.businessType) != "undefined") {
			this.businessType = _cfg.businessType;
		}
    	var url = (this.isHaveLending=='yes'?__ctxPath + "/creditFlow/finance/list2SlFundIntent.do":__ctxPath + "/creditFlow/finance/listSlFundIntent.do");
		if (this.isThisSuperviseRecord != null) {
			url = __ctxPath
					+ "/creditFlow/finance/listbySuperviseRecordSlFundIntent.do?slSuperviseRecordId="
					+ this.slSuperviseRecordId + "&isThisSuperviseRecord="
					+ this.isThisSuperviseRecord + "&isUnLoadData="
					+ this.isUnLoadData;

		}
		if (this.isThisEarlyPaymentRecordId != null) {
			url = __ctxPath
					+ "/creditFlow/finance/listbyEarlyRepaymentRecordSlFundIntent.do?isThisEarlyPaymentRecordId="
					+ this.isThisEarlyPaymentRecordId
					+ "&isThisEarlyPaymentRecord="
					+ this.isThisEarlyPaymentRecord + "&isUnLoadData="
					+ this.isUnLoadData;

		}
		if (this.isThisAlterAccrualRecord != null) {
			url = __ctxPath
					+ "/creditFlow/finance/listbyAlterAccrualRecordSlFundIntent.do?isThisAlterAccrualRecordId="
					+ this.isThisAlterAccrualRecordId
					+ "&isThisAlterAccrualRecord="
					+ this.isThisAlterAccrualRecord + "&isUnLoadData="
					+ this.isUnLoadData;
		}
    	var panel=Ext.create('tableHeader',{style:"margin-left:10px;",header:new Array("<span class='tablehead' >债务人</span>",
    	                                                      "<span class='tablehead' >借款金额(万)</span>",
    	                                                      "<span class='tablehead' >已还金额(万)</span>")});
    	Ext.apply(_cfg,{
    		style:'background-color:white;',
    		modeltype:"SlFundIntentList",
    		flex:1,
    		title:"放款收息表",
    	//	items:[panel],
    		fields:[{
			name : 'fundIntentId'
		}, {
			name : 'fundType'
		}, {
			name : 'fundTypeName'
		}, {
			name : 'incomeMoney'
		}, {
			name : 'payMoney'
		}, {
			name : 'intentDate'
		}, {
			name : 'factDate'
		}, {
			name : 'afterMoney'
		}, {
			name : 'notMoney'
		}, {
			name : 'accrualMoney'
		}, {
			name : 'isValid'
		}, {
			name : 'flatMoney'
		}, {
			name : 'overdueRate'
		}, {
			name : 'isOverdue'
		}, {
			name : 'companyId'
		},{
			name : 'interestStarTime'
		},{
			name : 'interestEndTime'
		},{
			name : 'payintentPeriod'
		},{
			name :'slSuperviseRecordId'
		}],
        url : url,
		root:'result',
	    totalProperty: 'totalCounts',
	    params : {
		projectId : this.projectId,
 	     flag1:1,
		businessType : this.businessType
	   },
		  itemTpl: new Ext.XTemplate( "<span  class='tablelist'>第{payintentPeriod}期</span>&nbsp;&nbsp;" +
		    		"<span   class='tablelist' >{fundTypeName}</span>&nbsp;&nbsp;" +
		    		"<span   class='tablelist' >{intentDate}</span>" +
		    		"<span   class='tablelist' >+{incomeMoney}元</span>" +
		    		"<span   class='tablelist' >-{payMoney}元</span>" +
		    		"<span   class='tablelist' >{remark}</span>" +
		    		"<span class='tableDetail'>></span>",{
		    			numberFormat: function(num) {
		    				var num = new Number(num);
						  return (num.toFixed(2) + '').replace(/\d{1,3}(?=(\d{3})+(\.\d*)?$)/g, '$&,');
						}  
		    			
		    		}),
 	        listeners : {
				itemsingletap : this.itemsingletap
			}
    	});

    	this.callParent([_cfg]);

    },
	itemsingletap : function(obj, index, target, record) {
    	  var data=record.data;
    	  var label = new Array("期数","资金类型","计划收入金额(元)","计划支出金额(元)","计划到帐日",
    	 "实际到帐日","计息开始日期","计息结束日期","已对账金额(元)","未对账金额(元)","已平账金额(元)","逾期费率(%/日)","逾期违约金总额(元)"); 
    	  var value = new Array("第"+data.payintentPeriod+"期",data.fundTypeName,data.incomeMoney,data.payMoney, data.intentDate,
    	 data.factDate,data.interestStarTime,data.interestEndTime,data.afterMoney,data.notMoney,data.flatMoney,data.overdueRate,data.accrualMoney);  
          getListDetail(label,value);
		    

}
});
