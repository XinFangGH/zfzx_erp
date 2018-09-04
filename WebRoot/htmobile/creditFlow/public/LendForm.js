
//ownFund.js
Ext.define('htmobile.creditFlow.public.LendForm', {
    extend: 'Ext.form.Panel',
    name: 'LendForm',
    constructor: function (config) {
    	config = config || {};
    	this.data=config.result;
    	Ext.apply(config,{
		    fullscreen: true,
		    scrollable:{
		    	direction: 'vertical'
		    },
		    items: [
		            {
		                xtype: 'fieldset',
		                defaults:{
		                	xtype: 'textfield',
		                	labelAlign:"top"
		                },
		                items: [{
		                        label: "<div class='fieldlabel'>贷款用途:</div>",
		                        xtype : "dickeycombo",
		                        nodeKey : 'smallloan_purposeType',	
		                        name : 'slSmallloanProject.purposeType',
		                        value: data.purposeType
		                    },
		                    {  
		                        label: "<div class='fieldlabel'>最高月还款金额(元):</div>",
		                        name : 'bpMoneyBorrowDemand.repaymentAmount',
		                        value: data.repaymentAmount
		                    },{
		                        label:"<div class='fieldlabel'>联系人是否知晓:</div>",
		                        xtype: 'togglefield',
		                        name : 'linkmanKnownObj',
		                        value: data.linkmanKnown
		                    },
		                    {
		                        label:  "<div class='fieldlabel'>申请额度(元) </div>",
		                        name : 'bpMoneyBorrowDemand.quotaApplicationSmall',
		                        value: data.quotaApplicationSmall
		                    },
		                    {
		                        label: "<div class='fieldlabel'>至:</div>",
		                        name : 'bpMoneyBorrowDemand.quotaApplicationBig',
		                       value: data.quotaApplicationBig
		                   
		                    },
		                /*    {
		                        label: "<div class='fieldlabel'>还款周期(日/期):</div>",
		                        hidden:data.payaccrualType=='owerPay'?false:true,
		                        value: data.payaccrualType=='owerPay'?data.dayOfEveryPeriod:"-"
		                   
		                    },*/
		                    {
		                        label: "<div class='fieldlabel'>贷款期限(月):</div>",
		                        name : 'bpMoneyBorrowDemand.longestRepaymentPeriod',
		                        value: data.longestRepaymentPeriod
		                    },
		                    {
		                        label: "<div class='fieldlabel'>期望到位日期:</div>",
		                        name : 'bpMoneyBorrowDemand.startDate',
		                        value: data.startDate
		                    }
		                    ,{
		                        label:"<div class='fieldlabel'>贷款利率(%/期):</div>",
		                        name : 'bpMoneyBorrowDemand.accrual',
		                         value: data.accrual
		                    },
		                    {
		                    	xtype : "dickeycombo",
		                    	nodeKey : 'zdbfs',
		                        label: "<div class='fieldlabel'>贷款方式:</div>",
		                        name : 'bpMoneyBorrowDemand.assuretypeid',
		                        value: data.assuretypeid
		                    }
		                   ,{
		                   	    xtype : "dickeycombo",
		                   	    nodeKey : 'customer_channel',
		                        label:"<div class='fieldlabel'>客户来源:</div>",
		                        name : 'slSmallloanProject.customerChannel',
		                        value: data.customerChannel
		                    },
		                    {    
		                    	xtype : "textareafield",
		                        label:  "<div class='fieldlabel'>备注:</div>",
		                        name : 'bpMoneyBorrowDemand.remark',
		                        value: data.remark
		                    },
					        {
					            xtype: 'button',
					            style:'border-radius: 0.2em;color:#ffffff;font-family: 微软雅黑;',
					            name: 'submit',
					            text:'保存',
					            cls : 'buttonCls',
					            handler:this.formSubmit
					        }
		          
		          ]}]
    	});

  

    	this.callParent([config]);
    	
    },
    detail:function(){
       mobileNavi.push(
		Ext.create('htmobile.customer.person.PersonMenu',{
			        data:this.data
		        	})
		    	);
    },
    formSubmit:function(){	
		 var loginForm = this.up('formpanel');
       	loginForm.submit({
            url: __ctxPath+'/creditFlow/customer/person/updateInfoPerson.do',
        	method : 'POST',
        	success : function(response, request) {
		        	var obj = Ext.util.JSON.decode(response);
		        	if(obj.success==true){
		        	
		        		  Ext.Msg.alert('','保存成功');
		        	}else{
		        		  Ext.Msg.alert('','保存失败');
		        		
		        	}
        	}
		});}

});
