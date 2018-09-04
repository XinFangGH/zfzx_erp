

Ext.define('htmobile.customer.enterprise.EnterpriseMenu1', {
    extend: 'Ext.Panel',
    
    name: 'EnterpriseMenu1',

    constructor: function (config) {
      var bheight=47;
      this.data=config.data;
      this.persondata=config.persondata;
    	Ext.apply(config,{
    		
    	title:"客户详情",
    	fullscreen: true,
        layout: {
            type: 'vbox'
            
        },
        items: [{
            xtype: 'panel',
            margin: '1 0 0 0',
            defaults: {
                xtype: 'panel',
                layout: 'hbox'
          //      margin: '1 0 0 0',
          //      style: 'border: 1px solid #cccccc; border-radius: 5px;'
            },
            items: [ {
                defaults: {
                    xtype: 'button',
                    width:'50%' ,height:bheight
                //    margin: 10
            //        style: 'border: 1px solid #cccccc; border-radius: 5px;'
                },
                items: [{
                 text:'公司资料扫描件',
                   scope:this,
                   handler:function(){this.companyPhoto(this.data);}
                }, {
                   text:'法定代表人信息',
                    scope:this,
                   handler:function(){
                  
                   this.legalPerosnInfo(this.persondata);}
                }]
            },{
                defaults: {
                    xtype: 'button',
                    width:'50%' ,height:bheight
                //    margin: 10
            //        style: 'border: 1px solid #cccccc; border-radius: 5px;'
                },
                items: [{
            //        margin: 10,
                 text:'银行开户',
                     scope:this,
                   handler:function(){this.bankInfoList(this.data);}
                },{
              //      margin: 10,
                   text:'纳税情况',
                    scope:this,
                   handler:function(){this.paytaxList(this.data);}
                }]
            },{
                defaults: {
                    xtype: 'button',
                    width:'50%' ,height:bheight
                //    margin: 10
            //        style: 'border: 1px solid #cccccc; border-radius: 5px;'
                },
                items: [ {
                   text:'企业联系人信息',
                    scope:this,
                   handler:function(){this.relationEnterpriset(this.data);}
                },{
                 text:'管理团队',
                  scope:this,
                  handler:function(){this.leadteamList(this.data);}
                }]
            },{
                defaults: {
                   xtype: 'button',
                    width:'50%' ,height:bheight
             //       margin: 10
              //      style: 'border: 1px solid #cccccc; border-radius: 5px;'
                },
                items: [ {
                   text:'债务情况',
                    scope:this,
                  handler:function(){this.debtList(this.data);}
                },{
                 text:'债权情况',
                   scope:this,
                   handler:function(){this.creditorList(this.data);}
                }]
            },{
                defaults: {
                    xtype: 'button',
                    width:'50%' ,height:bheight
              //      margin: 10
             //      style: 'border: 1px solid #cccccc; border-radius: 5px;'
                },
                items: [{
                   text:'对外担保',
                    scope:this,
                   handler:function(){this.outassureList(this.data);}
                },{
               //     margin: 10,
                   xtype:'button',text:'对外投资',
                    scope:this,
                   handler:function(){this.outinvestList(this.data);}
                }]
            },{
                defaults: {
                   xtype: 'button',
                    width:'50%' ,height:bheight
             //       margin: 10
              //      style: 'border: 1px solid #cccccc; border-radius: 5px;'
                },
                items: [  {
            //        margin: 10,
                    xtype:'button',text:'获奖情况',
                     scope:this,
                   handler:function(){this.prizeList(this.data);}
                },{
            //        margin: 10,
                   xtype:'button',text:'企业资信评估',
                    scope:this,
                   handler:function(){this.creditRatingManageList(this.data);}
                }]
            }, {
                defaults: {
                	xtype: 'button',
                    width:'50%' ,height:bheight
               //     margin: 10
           //         style: 'border: 1px solid #cccccc; border-radius: 5px;'
                },
                items: [{
            //        margin: 10,
                 text:'业务往来',
                     scope:this,
                   handler:function(){this.businessContactTab(this.data);}
                },{
              //      margin: 10,
                   text:'公司实际控制人',
                    scope:this,
                   handler:function(){this.headerInfo(this.data);}
                }]
            }, {
                defaults: {
                    xtype: 'button',
                    width:'50%' ,height:bheight
              //      margin: 10
              //      style: 'border: 1px solid #cccccc; border-radius: 5px;'
                },
                items: [{
              //      margin: 10,
                   text:'上下游客户',
                    scope:this,
                   handler:function(){this.upAndDownStreamTab(this.data);}
                }, {
                //    margin: 10,
                    text:'上下游渠道合同',
                     scope:this,
                   handler:function(){this.upAndDownContractTab(this.data);}
                }]
            }, {
                defaults: {
                    xtype: 'button',
                    width:'50%' ,height:bheight
              //      margin: 10
              //      style: 'border: 1px solid #cccccc; border-radius: 5px;'
                },
                items: [{
              //      margin: 10,
                   text:'诉讼情况',
                    scope:this,
                   handler:function(){this.lawsuitList(this.data);}
                }, {
                //    margin: 10,
                    text:'关联企业',
                     scope:this,
                   handler:function(){this.affiliatedEnterprise(this.data);}
                }]
            }, {
                defaults: {
                    xtype: 'button',
                    width:'100%' ,height:bheight
              //      margin: 10
              //      style: 'border: 1px solid #cccccc; border-radius: 5px;'
                },
                items: [ {
                   text:'股东(投资人)信息',
                    scope:this,
                   handler:function(){this.shareholderList(this.data);}
                }]
            }, {
                defaults: {
                    xtype: 'button',
                    width:'100%' ,height:bheight
              //      margin: 10
              //      style: 'border: 1px solid #cccccc; border-radius: 5px;'
                },
                items: [{
               //     margin: 10,
               //    text:'<font size="3">企业现金流量及销售收入</font>',
                	text:"企业现金流量及销售收入",
                    scope:this,
                   handler:function(){this.cashflowandSaleIncomeList(this.data);}
                }]
            }]
        }/*, {
            xtype: 'titlebar',
            title: '客户详情',
            docked: 'top'
        }*/]
 /*       modal: true,
            hideOnMaskTap: true,
            centered: true*/
            });

    	this.callParent([config]);
    	
    }
     ,
  companyPhoto:function(data){
       mobileNavi.push(
		Ext.create('htmobile.customer.enterprise.menudetail.CompanyPhoto',{
			data:data
		        	})
		    	);
  }
    ,
  legalPerosnInfo:function(persondata){
       mobileNavi.push(
		Ext.create('htmobile.customer.enterprise.menudetail.LegalPerosnInfo',{
			persondata:persondata
		        	})
		    	);
  },
  shareholderList:function(data){
       mobileNavi.push(
		Ext.create('htmobile.customer.enterprise.menudetail.ShareholderList',{
			data:data
		        	})
		    	);
  },
  relationEnterpriset:function(data){
       mobileNavi.push(
		Ext.create('htmobile.customer.enterprise.menudetail.RelationEnterpriset',{
			data:data
		        	})
		    	);
  }
  ,
  leadteamList:function(data){
  
   mobileNavi.push(
		Ext.create('htmobile.customer.enterprise.menudetail.LeadteamList',{
			data:data
		        	})
		    	);
  }
  ,
  debtList:function(data){
   mobileNavi.push(
		Ext.create('htmobile.customer.enterprise.menudetail.DebtList',{
			data:data
		        	})
		    	);
  }
  ,
  creditorList:function(data){
   mobileNavi.push(
		Ext.create('htmobile.customer.enterprise.menudetail.CreditorList',{
			data:data
		        	})
		    	);
  }
   ,
  spouseForm:function(data){
  	var personId=data.id;
	Ext.Ajax.request({
					url : __ctxPath + '/creditFlow/customer/person/getInfoSpouse.do?personId=' + personId,
				    success : function(response) {
					var result = Ext.util.JSON.decode(response.responseText);
					data = result.data;
				    mobileNavi.push(
			             Ext.create('htmobile.customer.person.menudetail.SpouseForm',{
				          data:data
			        	})
			    	);
				}
			});

  }
   ,
  bankInfoList:function(data){
   mobileNavi.push(
		Ext.create('htmobile.customer.person.menudetail.BankInfoList',{
			data:data,
			type:0
		        	})
		    	);
  }
   ,
  outassureList:function(data){
   mobileNavi.push(
		Ext.create('htmobile.customer.enterprise.menudetail.OutassureList',{
			data:data
		        	})
		    	);
  }
  
   ,
  outinvestList:function(data){
   mobileNavi.push(
		Ext.create('htmobile.customer.enterprise.menudetail.OutinvestList',{
			data:data
		        	})
		    	);
  }
   ,
  prizeList:function(data){
   mobileNavi.push(
		Ext.create('htmobile.customer.enterprise.menudetail.PrizeList',{
			data:data
		        	})
		    	);
  } ,
  paytaxList:function(data){
   mobileNavi.push(
		Ext.create('htmobile.customer.enterprise.menudetail.PaytaxList',{
			data:data
		        	})
		    	);
  }
  ,
  lawsuitList:function(data){
   mobileNavi.push(
		Ext.create('htmobile.customer.enterprise.menudetail.LawsuitList',{
			data:data
		        	})
		    	);
  }
  ,
  creditRatingManageList:function(data){
   mobileNavi.push(
		Ext.create('htmobile.customer.person.menudetail.CreditRatingManageList',{
			data:data,
			type:"企业"
		        	})
		    	);
  }
    ,
  affiliatedEnterprise:function(data){
   mobileNavi.push(
		Ext.create('htmobile.customer.enterprise.menudetail.AffiliatedEnterprise',{
			data:data
		        	})
		    	);
  }
  ,
  cashflowandSaleIncomeList:function(data){
   mobileNavi.push(
		Ext.create('htmobile.customer.enterprise.menudetail.CashflowandSaleIncomeList',{
			data:data
		        	})
		    	);
  }
  ,
  upAndDownStreamTab:function(data){
  	Ext.Ajax.request({   
									    	url: __ctxPath + '/creditFlow/customer/enterprise/getbIdyEnterpriseIdBpCustEntUpanddownstream.do',   
									   	 	method:'post',   
									    	params:{enterpriseId:data.id},  
									    	  success : function(response) {
					                           var obj = Ext.util.JSON.decode(response.responseText);
									        	var upAndDownCustomId=obj.upAndDownCustomId;
									        	var remarks=obj.remarks;
									        	 mobileNavi.push(
													Ext.create('htmobile.customer.enterprise.menudetail.UpAndDownStreamTab',{
														data:data,
														upAndDownCustomId:upAndDownCustomId,
														remarks:remarks
													        	})
													    	);
									    	},   
									    	failure: function(response, option) {   
									        	return true;   
									        	Ext.MessageBox.alert('友情提示',"异步通讯失败，请于管理员联系！");   
									    	}   
										});
  }
  ,
  upAndDownContractTab:function(data){
  	Ext.Ajax.request({   
	    	url: __ctxPath + '/creditFlow/customer/enterprise/getbIdyEnterpriseIdBpCustEntUpanddowncontract.do',   
	   	 	method:'post',   
	    	params:{enterpriseId:data.id},  
	    	  success : function(response) {
               var obj = Ext.util.JSON.decode(response.responseText);
	        	var upAndDownContractId=obj.upAndDownContractId;
	        	var upremarks=obj.upremarks;
	        	var downremarks=obj.downremarks;
	        	 mobileNavi.push(
					Ext.create('htmobile.customer.enterprise.menudetail.UpAndDownContractTab',{
						data:data,
						upAndDownContractId:upAndDownContractId,
						downremarks:downremarks,
						upremarks:upremarks
					        	})
					    	);
	    	},   
	    	failure: function(response, option) {   
	        	return true;   
	        	Ext.MessageBox.alert('友情提示',"异步通讯失败，请于管理员联系！");   
	    	}   
		});
  }
   ,
  headerInfo:function(data){
  	Ext.Ajax.request({   
	    	url : __ctxPath + '/creditFlow/customer/enterprise/getHeaderInfoEnterprise.do',
	   	 	method:'post',   
	    	params:{enterpriseId:data.id},  
	    	  success : function(response) {
               var obj = Ext.util.JSON.decode(response.responseText);
	        	var data=obj.data;
	        	 mobileNavi.push(
					Ext.create('htmobile.customer.enterprise.menudetail.HeaderInfo',{
						data:data
					        	})
					    	);
	    	},   
	    	failure: function(response, option) {   
	        	return true;   
	        	Ext.MessageBox.alert('友情提示',"异步通讯失败，请于管理员联系！");   
	    	}   
		});
  }
    ,
  businessContactTab:function(data){
   mobileNavi.push(
		Ext.create('htmobile.customer.person.menudetail.BusinessContactTab',{
			data:data,
			type:"company_customer"
		        	})
		    	);
  }
});
