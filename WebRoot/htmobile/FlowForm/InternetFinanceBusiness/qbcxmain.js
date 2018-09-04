

Ext.define('htmobile.FlowForm.InternetFinanceBusiness.qbcxmain', {
    extend: 'Ext.Panel',
    
    name: 'qbcxmain',

    constructor: function (config) {
         config = config || {};
    	var taskId=config.taskId;
		var defId=config.defId;
		var preTaskName=config.preTaskName;
		var isSignTask=config.isSignTask;
		var trans=config.trans;
		var taskName=config.taskName;
		var vars=config.vars[0];
		var activityName=config.activityName;
        creditLoanProjectInfo=function(){
	    var loginForm = this.up('formpanel');
       	loginForm.submit({
					url : __ctxPath + '/htmobile/getCreditLoanProjectInfoVmInfo.do',
					params:{
					   projectId:vars.projectId
					},
				    success : function(response) {
					var result = Ext.util.JSON.decode(response.responseText);
						data = result.data;
					     mobileNavi.push(
			             Ext.create('htmobile.creditFlow.public.CreditLoanProjectInfoPanel',{
				        data:data
			        	})
		    	);
				}
			})
    }

    getCustom=function(){
    	var personId=0;
	   Ext.Ajax.request({
					url : __ctxPath + '/htmobile/getPersonIdVmInfo.do',
					params:{
					    projectId:vars.projectId,
					    businessType:vars.businessType
					},
					async:false,
				    success : function(response) {
						var obj = Ext.util.JSON.decode(response.responseText);
					   personId=obj.oppositeID
				}
			});
          Ext.Ajax.request({
					url : __ctxPath + '/creditFlow/customer/person/seeInfoPerson.do',
					async:false,
					params:{
					    id:personId
					},
				    success : function(response) {
						var obj = Ext.util.JSON.decode(response.responseText);
						var data = obj.data;
					    mobileNavi.push(
			            Ext.create('htmobile.customer.person.PersonDetail',{
				        result:data
			        	})
		    	);
				}
			});
     
    }
					
       getFundInfo=function(){
	   Ext.Ajax.request({
					url : __ctxPath + '/htmobile/getCreditLoanProjectInfoVmInfo.do',
					params:{
					   projectId:vars.projectId,
					   isOwn:1
					},
				    success : function(response) {
					var result = Ext.util.JSON.decode(response.responseText);
						data = result.data;
					     mobileNavi.push(
			             Ext.create('htmobile.creditFlow.public.FundInfo',{
				         result:data
			        	})
		    	);
				}
			})
    }	
    
     getbidPlanFinanceInfo=function(){
	   Ext.Ajax.request({
					url : __ctxPath + '/htmobile/getPlBidPlanInfoVmInfo.do',
					params:{
					   bidPlanId:vars.bidPlanId
					},
				    success : function(response) {
					var result = Ext.util.JSON.decode(response.responseText);
						data = result.data;
					     mobileNavi.push(
			             Ext.create('htmobile.creditFlow.public.BidPlanFinanceInfo',{
				        result:data
			        	})
		    	);
				}
			})
    }
    	Ext.apply(config,{
		    fullscreen: true,
		    width:"100%",
		    height:"100%",
		    scrollable:{
		    	direction: 'vertical'
		    },
		    items: [
		            {
		                xtype: 'fieldset',
		                defaults:{
		                	xtype: 'panel',
		                	readOnly: true,
		                	labelAlign:"top"
		                },
		                items: [{
		                	   style:'margin-top: 20px;',
		                        html: '<div class="vmMain" onclick="javascript:creditLoanProjectInfo();">项目基本信息  ' +
		                        		'&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;></div>'
		                    },
		                    {
		                        html: '<div class="vmMain" onclick="javascript:getCustom();"   >客户信息    ' +
		                        		'&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;></div>'
		                    },
		                    {
		                        html: '<div class="vmMain" onclick="javascript:getFundInfo();">资金款项信息 ' +
		                        		'&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;></div>'
		                    },
		                    {
		                        html: '<div class="vmMain" onclick="javascript:getbidPlanFinanceInfo();">本次招标信息 ' +
		                        		'&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;></div>'
		                    },
		                    {
		                        html: '<div class="vmMain">贷款合同  ' +
		                        		'&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;></div>'
		                    }
		          
		          
		          ]}]
    	});

    	this.callParent([config]);
    	
    }

});
