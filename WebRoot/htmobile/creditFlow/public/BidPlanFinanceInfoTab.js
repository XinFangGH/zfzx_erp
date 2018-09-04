

Ext.define('htmobile.creditFlow.public.BidPlanFinanceInfoTab', {
	extend: 'Ext.TabPanel',
    
    name: 'BidPlanFinanceInfoTab',

    constructor: function (config) {
    	var data=config.data;
	    var SlFundIntentMergeList=Ext.create('htmobile.creditFlow.public.SlFundIntentMergeList',{
	                    projectId : config.projId,
						flag1 : 1,
						bidPlanId:config.bidPlanId,
						preceptId:config.preceptId,
						slEarlyRepaymentId: config.slEarlyRepaymentId,
						businessType : config.businessType}); 
	    var SlActualToChargeList=Ext.create('htmobile.creditFlow.public.SlActualToChargeList',{
	                       projId : config.projId,
							bidPlanId:config.bidPlanId,
							businessType :config.businessType,// 小贷
							editor:config.editor,
							serviceHidden:config.serviceHidden,
							isHidden : config.isHidden}); 
	    var InvestPersonInfoList=Ext.create('htmobile.creditFlow.public.InvestPersonInfoList',{
						    projectId : config.projId,
								bidPlanId:config.bidPlanId,
								isHidden : true,
								bussinessType : config.businessType,
								isFlow:false}); 
	    var BidPlanFinanceInfo=Ext.create('htmobile.creditFlow.public.BidPlanFinanceInfo',{data:data}); 
	    Ext.apply(config,{
	    	
        	title:'招标信息',
            layoutOnTabChange: true,
            items: [
	{
	            	title: '<div style="font-size:10px;">招标信息</div>',
	                items:[{
	    	            xtype: 'fieldset',
	    	            items:BidPlanFinanceInfo
	    	        }]
	            },
	            {
	            	title: '<div style="font-size:10px;">投资人信息</div>',
	                items:[{
	    	            xtype: 'fieldset',
	    	            items:InvestPersonInfoList
	    	        }]
	            },
	            {
	            	title: '<div style="font-size:10px;">费用明细</div>',
	                items:[{
	    	            xtype: 'fieldset',
	    	            items:SlActualToChargeList
	    	            
	    	        }]
	            },
	           {
	            	title: '<div style="font-size:10px;">借款人款项</div>',
	                items:[{
	    	            xtype: 'fieldset',
	    	            items:SlFundIntentMergeList
	    	        }]
	            }
            ]
        });
    
        this.callParent([config]);
	}

});

