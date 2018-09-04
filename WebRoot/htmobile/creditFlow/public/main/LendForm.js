
//ownFund.js
Ext.define('htmobile.creditFlow.public.main.LendForm', {
    extend: 'Ext.form.Panel',
    name: 'LendForm',
    constructor: function (config) {
    	config = config || {};
    	Ext.apply(this,config);
    	Ext.apply(config,{
		    fullscreen: true,
		    title:'借款需求',
		    scrollable:{
		    	direction: 'vertical'
		    },
		    items: [{
		                xtype: 'fieldset',
		                defaults:{
		                	xtype: 'textfield',
		                	readOnly:this.readOnly,
		                	labelWidth:"35%"
		                },
		                items: [{
		                        xtype : "hiddenfield",
		                        name : 'bpMoneyBorrowDemand.borrowid',
		                        value: this.data.borrowid
		                    },{
		                        xtype : "hiddenfield",
		                        name : 'projectId',
		                        value: this.projectId
		                    },{
		                        label: "贷款用途",
		                        xtype : "dickeycombo",
		                        nodeKey : 'smallloan_purposeType',	
		                        name : 'slSmallloanProject.purposeType',
		                        hiddenName:'slSmallloanProject.purposeType',
		                        value: this.data.purposeType
		                    }/*,
		                    {  
		                        label: "<div class='fieldlabel'>最高月还款金额(元):</div>",
		                        name : 'bpMoneyBorrowDemand.repaymentAmount',
		                        value: data.repaymentAmount
		                    },{
		                        label:"<div class='fieldlabel'>联系人是否知晓:</div>",
		                        xtype: 'togglefield',
		                        name : 'bpMoneyBorrowDemand.linkmanKnown',
		                        id:"bpMoneyBorrowDemand.linkmanKnown",
		                        value: Ext.isEmpty(data.linkmanKnown)?"0":data.linkmanKnown
		                    }*/,
		                    {	
		                        label:  "申请额度(元)",
		                        name : 'bpMoneyBorrowDemand.quotaApplicationSmall',
		                        value: this.data.quotaApplicationSmall
		                    },
		                    {	xtype: 'hiddenfield',
		                        label: "申请额度(元)",
		                        name : 'bpMoneyBorrowDemand.quotaApplicationBig',
		                       value: this.data.quotaApplicationSmall
		                   
		                    },
		                   {
		                        label: "还款周期(日/期)",
		                        hidden:this.data.payaccrualType=='owerPay'?false:true,
		                        value: this.data.payaccrualType=='owerPay'?this.data.dayOfEveryPeriod:"-"
		                   
		                    },
	                        {   label:"贷款期限(月)",
	                        	name : 'bpMoneyBorrowDemand.longestRepaymentPeriod',
	                            xtype:'textfield',
	                            readOnly:this.readOnly,
	                            value: this.data.longestRepaymentPeriod,
	                            flex:1  
	                        },{
					              xtype: 'datepickerfield',
					              minWidth:20,
					              label: "期望到位日期",
					              name: 'bpMoneyBorrowDemand.startDate',
					              id:"bpMoneyBorrowDemandstartDate",
				                  dateFormat:'Y-m-d',
				                  value: new Date(),
				                  picker: {
				                     xtype:'todaypickerux'
				                  }
					        }
		                    ,{
		                        label:"贷款利率(%/月)",
		                        name : 'bpMoneyBorrowDemand.accrual',
		                         value: this.data.accrual
		                    },
		                    {
		                    	xtype : "dickeycombo",
		                    	nodeKey : 'zdbfs',
		                        label: "贷款方式",
		                        name : 'bpMoneyBorrowDemand.assuretypeid',
		                        value: this.data.assuretypeid
		                    }
		                   ,{
		                   	    xtype : "dickeycombo",
		                   	    nodeKey : 'customer_channel',
		                        label:"客户来源",
		                        name : 'slSmallloanProject.customerChannel',
		                        value: this.data.customerChannel
		                    },
		                    {    
		                    	xtype : "textareafield",
		                        label:  "备注",
		                        name : 'bpMoneyBorrowDemand.remark',
		                        value: this.data.remark
		                    },
					       {
					            xtype: this.readOnly==true?'hiddenfield':'button',
					            name: 'submit',
					            text:'保存',
					            cls : 'submit-button',
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

		 var purposeType=loginForm.getCmpByName("slSmallloanProject.purposeType").getValue(); 
		  if(Ext.isEmpty(purposeType)){
		    Ext.Msg.alert('','贷款用途不能为空');
			return;
		  }
		   var quotaApplicationSmall=loginForm.getCmpByName("bpMoneyBorrowDemand.quotaApplicationSmall").getValue(); 
		  if(Ext.isEmpty(quotaApplicationSmall)){
		    Ext.Msg.alert('','申请额度不能为空');
			return;
		  }
		   var longestRepaymentPeriod=loginForm.getCmpByName("bpMoneyBorrowDemand.longestRepaymentPeriod").getValue(); 
		  if(Ext.isEmpty(longestRepaymentPeriod)){
		    Ext.Msg.alert('','货款期限不能为空');
			return;
		  }
		  
		   var assuretypeid=loginForm.getCmpByName("bpMoneyBorrowDemand.assuretypeid").getValue(); 
		  if(Ext.isEmpty(assuretypeid)){
		    Ext.Msg.alert('','贷款方式不能为空');
			return;
		  }
       	 loginForm.submit({
            url: __ctxPath+'/htmobile/lendFormVmInfo.do',
        	method : 'POST',
        	success : function(form,action,response) {
		        	var obj = Ext.util.JSON.decode(response);
		        	if(obj.success==true){
		        	
		        		  Ext.Msg.alert('','提交成功');
		        		  mobileNavi.pop();
		        	}else{
		        		  Ext.Msg.alert('','提交失败');
		        		
		        	}
        	}
		});}

});