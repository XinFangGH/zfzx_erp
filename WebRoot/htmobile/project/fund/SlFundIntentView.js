
//creditorList.js
Ext.define('htmobile.project.fund.SlFundIntentView', {
    extend: 'mobile.List',
    
    name: 'SlFundIntentView',

    constructor: function (config) {
    	var search = Ext.create('Ext.Container', {
    		docked:'top',
    			
    		items:[{
    			html:
    				'<div class="g-body" style="background-color:#f0f1f5;">'+
				    '<header class="search-title dflex">'+
				       ' <input type="search" class="search-input flex" placeholder="请输入搜索内容" />'+
				        '<button type="button" title="搜索" class="w-button-s w-button w-button-org w-button-search">搜索</button>'+
				    '</header>'+
					'</div>'}]
    	});
		this.businessType="SmallLoan";
		this.tabflag="overdue";
		this.isGrantedShowAllProjects=isGranted('_seeAllPro_p1');
    	config = config || {};
    	var panel=Ext.create('tableHeader',{style:"margin-left:10px;",header:new Array("<span class='tablehead' >债务人</span>",
    	                                                      "<span class='tablehead' >借款金额(万)</span>",
    	                                                      "<span class='tablehead' >已还金额(万)</span>")});
    	Ext.apply(config,{
    		modeltype:"CreditorList",
    		items:[search],
    		itemsTpl:[search].join(""),
    		flex:1,
    		title:"逾期款项",
    	//	items:[panel],
    		fields:[{
					name : 'fundIntentId',
					type : 'int'
					}, 'projectName','projectNumber', 'incomeMoney','fundTypeName', 'intentDate',
							'payMoney', 'payInMoney', 'factDate','fundType',
							'afterMoney', 'notMoney','flatMoney', 'isOverdue',
							'overdueRate', 'accrualMoney', 'status','remark','businessType','projectId',
							'lastslFundintentUrgeTime','oppositeName','opposittelephone','projectStartDate','orgName'],
	    	        url: __ctxPath + "/creditFlow/finance/listbyurgeSlFundIntent.do",
		    		root:'result',
		    	    totalProperty: 'totalCounts',
		    	    params : {
						businessType : this.businessType,
						tabflag : this.tabflag,
						isGrantedShowAllProjects :this.isGrantedShowAllProjects,
						  Q_projNum_N_EQ:""  //搜索的名字
			},
		    itemTpl:  new Ext.XTemplate("<span style='font-size:14px;color:#412f1f;'>" +
					"项目编号：{projectNumber}<br/>" +
					"资金类型：{fundTypeName}<br/>" +
					"计划收入：{incomeMoney}元<br/>" +
					"计划到账日：{intentDate}<br/>" +
					"</span>" ),
//			searchCol:'Q_projNum_N_EQ',
//		    searchTip:'请输入项目编号',
		    pullRefresh: true,
		    listPaging: true/*,
		    		
 	        listeners : {
				itemsingletap : this.itemsingletap
			}*/
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
