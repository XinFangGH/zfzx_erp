
//ownFund.js
Ext.define('htmobile.creditFlow.public.customer.detail.BankInfoAdd', {
    extend: 'Ext.form.Panel',
    name: 'BankInfoAdd',
    constructor: function (config) {
    	config = config || {};
    	this.data=config.data;
    	this.isEnterprise=config.isEnterprise;
    	this.isInvest=config.isInvest;
    	this.personId = config.personId;
    	this.enterpriseBankid=config.enterpriseBankid;
    	this.businessType=config.businessType;
    	if(Ext.isEmpty( config.addBtreadOnly)){
    		this.addBtreadOnly=false;
    	}else{
    	  this.addBtreadOnly=config.addBtreadOnly;
    	}
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
		                	labelAlign:"top",
		                	readOnly:this.addBtreadOnly
		                },
		                items: [{
		                        xtype : "hiddenfield",
		                        name : 'enterpriseBank.enterpriseid',
		                        value:Ext.isEmpty(this.data)?this.personId:this.data.enterpriseid
		                    },{
		                        xtype : "hiddenfield",
		                        name : 'enterpriseBank.isEnterprise',
		                        value:Ext.isEmpty(this.data)?this.isEnterprise:this.data.isEnterprise
		                    },{
		                        xtype : "hiddenfield",
		                        name : 'enterpriseBank.isInvest',
		                        value:Ext.isEmpty(this.data)?this.isInvest:this.data.isInvest
		                    },{
		                        xtype : "hiddenfield",
		                        name : 'enterpriseBank.id'	,
		                        value:Ext.isEmpty(this.data)?null:this.enterpriseBankid
		                    },{
		                        xtype : "selectfield",
		                        label:"<div class='fieldlabel'>开户类型:</div>",
						        options : [{"text":"个人","value":"0"},{"text":"公司","value":"1"}],
		                        name : 'enterpriseBank.openType',
		                        value:Ext.isEmpty(this.data)?'个人':this.data.openType
		                    },{
		                        xtype : "selectfield",
		                        label:"<div class='fieldlabel'>账户类型:</div>",
		                        options : [{"text":"个人储蓄户","value":"0"},
		                        	       {"text":"基本户","value":"1"},
		                        	       {"text":"一般户","value":"2"}],
		                        name : 'enterpriseBank.accountType',
		                        value:Ext.isEmpty(this.data)?'个人储蓄户':this.data.accountType
		                    },{ 
		                    	xtype : "selectfield",
		                        label:"<div class='fieldlabel'>银行名称:</div>",
		                        store:Ext.create('StoreSelect', {
				          	        url :  __ctxPath + "/htmobile/getBankListVmInfo.do"
				                }),
		                        name : 'enterpriseBank.bankid',
		                        value:Ext.isEmpty(this.data)?null:this.data.bankid
		                   	},{
		                        xtype : "textfield",
		                        label:"<div class='fieldlabel'>开户地区:</div>",
		                        name : 'enterpriseBank.areaName',
		                        value:Ext.isEmpty(this.data)?null:this.data.areaName,
		                        id:"enterpriseBankareaName",
		                        listeners : {
								scope:this,
								'focus' : function(f) {
									
									   mobileNavi.push(Ext.create('htmobile.public.AreaSelectList',{
									   	node:"6591",
									   	count:1,
									   	textall:"",
									    callback:function(data){
									   	  var teamManagerName= Ext.getCmp("enterpriseBankareaName");
									   	   var teamManagerId= Ext.getCmp("enterpriseBankareaId");
									        teamManagerName.setValue(data.remarks); 
									         teamManagerId.setValue(data.id); 
									   }}));
									}
								}
		                    },{
		                    	xtype : "hiddenfield",
		                      id:"enterpriseBankareaId",
		                      name:"enterpriseBank.areaId"
		                      
		                    },{
		                        xtype : "textfield",
		                        label:"<div class='fieldlabel'>网点名称:</div>",
		                        name : 'enterpriseBank.bankOutletsName',
		                        value:Ext.isEmpty(this.data)?null:this.data.bankOutletsName
		                    },{
		                        xtype : "selectfield",
		                        label:"<div class='fieldlabel'>币种:</div>",
		                        name : 'enterpriseBank.openCurrency',
		                        options : [{"text":"本币开户","value":"0"},
		                        	       {"text":"外币开户","value":"1"}],
		                        value:Ext.isEmpty(this.data)?'本币开户':this.data.openCurrency
		                    },{
		                        label:"<div class='fieldlabel'>是否是放款账户:</div>",
		                        xtype: 'togglefield',
		                        name : 'enterpriseBank.iscredit',
		                        id:"enterpriseBankiscredit",
		                        value: Ext.isEmpty(this.data)?0:this.data.iscredit
		                    },{
		                        xtype : "textfield",
		                        label:"<div class='fieldlabel'>开户名称:</div>",
		                        name : 'enterpriseBank.name',
		                        value:Ext.isEmpty(this.data)?null:this.data.name
		                    },{
		                        xtype : "textfield",
		                        label:"<div class='fieldlabel'>货款卡卡号:</div>",
		                        name : 'enterpriseBank.accountnum',
		                        value:Ext.isEmpty(this.data)?null:this.data.accountnum
		                    },{
		                        xtype : "textfield",
		                        label:"<div class='fieldlabel'>备注:</div>",
		                        name : 'enterpriseBank.remarks',
		                        value:Ext.isEmpty(this.data)?null:this.data.remarks
		                    },
					        {
					            xtype: this.addBtreadOnly==true?'hiddenfield':'button',
					            name: 'submit',
					            text:'保存',
					            cls : 'buttonCls',
					            scope:this,
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
		 var loginForm = this;
		 var personId= this.personId;
		 var id=this.bankid;
		  var openType=loginForm.getCmpByName("enterpriseBank.openType").getValue(); 
		  if(Ext.isEmpty(openType)){
		    Ext.Msg.alert('','开户类型不能为空');
			return;
		  }
		   var accountType=loginForm.getCmpByName("enterpriseBank.accountType").getValue(); 
		  if(Ext.isEmpty(accountType)){
		    Ext.Msg.alert('','账户类型不能为空');
			return;
		  }
		   var bankid=loginForm.getCmpByName("enterpriseBank.bankid").getValue(); 
		  if(Ext.isEmpty(bankid)){
		    Ext.Msg.alert('','银行名称不能为空');
			return;
		  }
		   var areaName=loginForm.getCmpByName("enterpriseBank.areaName").getValue(); 
		  if(Ext.isEmpty(areaName)){
		    Ext.Msg.alert('','开户地区不能为空');
			return;
		  }
		 var bankOutletsName=loginForm.getCmpByName("enterpriseBank.bankOutletsName").getValue(); 
		  if(Ext.isEmpty(bankOutletsName)){
		    Ext.Msg.alert('','网点名称不能为空');
			return;
		  }
		   var name=loginForm.getCmpByName("enterpriseBank.name").getValue(); 
		  if(Ext.isEmpty(name)){
		    Ext.Msg.alert('','开户名称不能为空');
			return;
		  }
		   var accountnum=loginForm.getCmpByName("enterpriseBank.accountnum").getValue(); 
		  if(Ext.isEmpty(accountnum)){
		    Ext.Msg.alert('','货款卡卡号不能为空');
			return;
		  }
		  
		 var url=Ext.isEmpty(id)?( __ctxPath+'/creditFlow/customer/common/addEnterpriseBank.do'):
            (__ctxPath+'/creditFlow/customer/common/updateEnterpriseBank.do');
       	 loginForm.submit({
            url:url,
        	method : 'POST',
        	params:{
        	},
        	success : function(form,action,response) {
		        	var obj = Ext.util.JSON.decode(response);
		        	if(obj.success==true){
		        	       
		        		  Ext.Msg.alert('','保存成功');
		        		  var object= Ext.getCmp("BankInfoView");
		        		   mobileNavi.pop();
					      object.store.loadPage(1);
		        	}else{
		        		  Ext.Msg.alert('','保存失败');
		        		
		        	}
        	}
		});}

});