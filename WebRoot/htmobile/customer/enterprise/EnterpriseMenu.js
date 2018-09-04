

Ext.define('htmobile.customer.enterprise.EnterpriseMenu', {
    extend: 'Ext.Panel',
    
    name: 'EnterpriseMenu',

    constructor: function (config) {
      var bheight=47;
      this.data=config.data;
      this.persondata=config.persondata;
      var data=config.data;
      var persondata=config.persondata;
    	Ext.apply(config,{
		    fullscreen: true,
		    width:'100%',
		    height:'100%',
		    title:"客户详情",
		    scrollable:{
		    	direction: 'vertical'
		    },
		    items: [/*{
		    	xtype: 'fieldset',
		        items:[{
		           xtype:'panel',
		           html:'<div class=\"vmMain\">公司资料扫描件 '+
		           '<span style=\"float:right;\" onclick=\"javascript:companyPhoto();\">>&nbsp;&nbsp;&nbsp;&nbsp;</span></div>'
		          }]
		    },*/{
		    	xtype: 'fieldset',
		        items:[{
		           xtype:'panel',
		           html:'<div class=\"vmMain\">公司详细信息 '+
		           '<span style=\"float:right;\" onclick=\"javascript:entpriseDetailmore();\">>&nbsp;&nbsp;&nbsp;&nbsp;</span><hr style=\"height:2px;\"> </div>'+
		           '<div class=\"vmMain\">公司资料扫描件 '+
		           '<span style=\"float:right;\" onclick=\"javascript:companyPhoto();\">>&nbsp;&nbsp;&nbsp;&nbsp;</span><hr style=\"height:2px;\"> </div>'+
		           '<div class=\"vmMain\">法定代表人信息'+
		           '<span style=\"float:right;\" onclick=\"javascript:legalPerosnInfo();\">>&nbsp;&nbsp;&nbsp;&nbsp;</span><hr style=\"height:2px;\"> </hr></div>'+
		           '<div class=\"vmMain\">企业联系人信息'+
		           '<span style=\"float:right;\" onclick=\"javascript:relationEnterpriset();\">>&nbsp;&nbsp;&nbsp;&nbsp;</span><hr style=\"height:2px;\"> </hr></div>'+
		           '<div class=\"vmMain\">公司实际控制人'+
		           '<span style=\"float:right;\" onclick=\"javascript:headerInfo();\">>&nbsp;&nbsp;&nbsp;&nbsp;</span><hr style=\"height:2px;\"> </hr></div>'+
		           '<div class=\"vmMain\">股东(投资人)信息'+
		           '<span style=\"float:right;\" onclick=\"javascript:shareholderList();\">>&nbsp;&nbsp;&nbsp;&nbsp;</span><hr style=\"height:2px;\"> </hr></div>'+
		           '<div class=\"vmMain\">管理团队'+
		           '<span style=\"float:right;\" onclick=\"javascript:leadteamList();\">>&nbsp;&nbsp;&nbsp;&nbsp;</span></div>'
		          }]
		    }/*,{
		    	xtype: 'fieldset',
		        items:[{
		           xtype:'panel',
		           html:'<div class=\"vmMain\">银行开户'+
		           '<span style=\"float:right;\" onclick=\"javascript:bankInfoList();\">>&nbsp;&nbsp;&nbsp;&nbsp;</span></div>'
		       }]
		    },{
		    	xtype: 'fieldset',
		        items:[{
		           xtype:'panel',
		           html:'<div class=\"vmMain\">纳税情况'+
		           '<span style=\"float:right;\" onclick=\"javascript:paytaxList();\">>&nbsp;&nbsp;&nbsp;&nbsp;</span></div>'
		          }]
		    }*//*,{
		    	xtype: 'fieldset',
		        items:[{
		           xtype:'panel',
		           html:'<div class=\"vmMain\">企业联系人信息'+
		           '<span style=\"float:right;\" onclick=\"javascript:relationEnterpriset();\">>&nbsp;&nbsp;&nbsp;&nbsp;</span></div>'
		       }]
		    }*//*,{
		    	xtype: 'fieldset',
		        items:[{
		           xtype:'panel',
		           html:'<div class=\"vmMain\">管理团队'+
		           '<span style=\"float:right;\" onclick=\"javascript:leadteamList();\">>&nbsp;&nbsp;&nbsp;&nbsp;</span></div>'
		          }]
		    }*/,{
		    	xtype: 'fieldset',
		        items:[{
		           xtype:'panel',
		           html:'<div class=\"vmMain\">债务情况'+
		           '<span style=\"float:right;\" onclick=\"javascript:debtList();\">>&nbsp;&nbsp;&nbsp;&nbsp;</span><hr style=\"height:2px;\"> </hr></div>'+
		           '<div class=\"vmMain\">债权情况'+
		           '<span style=\"float:right;\" onclick=\"javascript:creditorList();\">>&nbsp;&nbsp;&nbsp;&nbsp;</span></div>'
		       }]
		    }/*,{
		    	xtype: 'fieldset',
		        items:[{
		           xtype:'panel',
		           html:'<div class=\"vmMain\">债权情况'+
		           '<span style=\"float:right;\" onclick=\"javascript:creditorList();\">>&nbsp;&nbsp;&nbsp;&nbsp;</span></div>'
		          }]
		    }*/,{
		    	xtype: 'fieldset',
		        items:[{
		           xtype:'panel',
		           html:'<div class=\"vmMain\">对外担保'+
		           '<span style=\"float:right;\" onclick=\"javascript:outassureList();\">>&nbsp;&nbsp;&nbsp;&nbsp;</span><hr style=\"height:2px;\"> </hr></div>'+
		           '<div class=\"vmMain\">对外投资'+
		           '<span style=\"float:right;\" onclick=\"javascript:outinvestList();\">>&nbsp;&nbsp;&nbsp;&nbsp;</span></div>'
		       }]
		    },/*{
		    	xtype: 'fieldset',
		        items:[{
		           xtype:'panel',
		           html:'<div class=\"vmMain\">对外投资'+
		           '<span style=\"float:right;\" onclick=\"javascript:outinvestList();\">>&nbsp;&nbsp;&nbsp;&nbsp;</span></div>'
		          }]
		    },*/{
		    	xtype: 'fieldset',
		        items:[{
		           xtype:'panel',
		           html:'<div class=\"vmMain\">获奖情况'+
		           '<span style=\"float:right;\" onclick=\"javascript:prizeList();\">>&nbsp;&nbsp;&nbsp;&nbsp;</span><hr style=\"height:2px;\"> </hr></div>'+
		           '<div class=\"vmMain\">企业资信评估'+
		           '<span style=\"float:right;\" onclick=\"javascript:creditRatingManageList();\">>&nbsp;&nbsp;&nbsp;&nbsp;</span></div>'
		       }]
		    }/*,{
		    	xtype: 'fieldset',
		        items:[{
		           xtype:'panel',
		           html:'<div class=\"vmMain\">企业资信评估'+
		           '<span style=\"float:right;\" onclick=\"javascript:creditRatingManageList();\">>&nbsp;&nbsp;&nbsp;&nbsp;</span></div>'
		       }]
		    }*/,{
		    	xtype: 'fieldset',
		        items:[{
		           xtype:'panel',
		           html:'<div class=\"vmMain\">业务往来'+
		           '<span style=\"float:right;\" onclick=\"javascript:businessContactTab();\">>&nbsp;&nbsp;&nbsp;&nbsp;</span></div>'
		          }]
		    }/*,{
		    	xtype: 'fieldset',
		        items:[{
		           xtype:'panel',
		           html:'<div class=\"vmMain\">公司实际控制人'+
		           '<span style=\"float:right;\" onclick=\"javascript:headerInfo();\">>&nbsp;&nbsp;&nbsp;&nbsp;</span></div>'
		       }]
		    }*/,{
		    	xtype: 'fieldset',
		        items:[{
		           xtype:'panel',
		           html:'<div class=\"vmMain\">上下游客户'+
		           '<span style=\"float:right;\" onclick=\"javascript:upAndDownStreamTab();\">>&nbsp;&nbsp;&nbsp;&nbsp;</span><hr style=\"height:2px;\"> </hr></div>'+
		           '<div class=\"vmMain\">上下游渠道合同'+
		           '<span style=\"float:right;\" onclick=\"javascript:upAndDownContractTab();\">>&nbsp;&nbsp;&nbsp;&nbsp;</span><hr style=\"height:2px;\"> </hr></div>'+
		           '<div class=\"vmMain\">企业现金流量及销售收入'+
		           '<span style=\"float:right;\" onclick=\"javascript:cashflowandSaleIncomeList();\">>&nbsp;&nbsp;&nbsp;&nbsp;</span></div>'
		       }]
		    }/*,{
		    	xtype: 'fieldset',
		        items:[{
		           xtype:'panel',
		           html:'<div class=\"vmMain\">上下游渠道合同'+
		           '<span style=\"float:right;\" onclick=\"javascript:upAndDownContractTab();\">>&nbsp;&nbsp;&nbsp;&nbsp;</span></div>'
		          }]
		    }*/,{
		    	xtype: 'fieldset',
		        items:[{
		           xtype:'panel',
		           html:'<div class=\"vmMain\">纳税情况'+
		           '<span style=\"float:right;\" onclick=\"javascript:paytaxList();\">>&nbsp;&nbsp;&nbsp;&nbsp;</span><hr style=\"height:2px;\"> </hr></div>'+
		           '<div class=\"vmMain\">诉讼情况'+
		           '<span style=\"float:right;\" onclick=\"javascript:lawsuitList();\">>&nbsp;&nbsp;&nbsp;&nbsp;</span><hr style=\"height:2px;\"> </hr></div>'+
		           '<div class=\"vmMain\">银行开户'+
		           '<span style=\"float:right;\" onclick=\"javascript:bankInfoList();\">>&nbsp;&nbsp;&nbsp;&nbsp;</span></div>'
		       }]
		    },{
		    	xtype: 'fieldset',
		        items:[{
		           xtype:'panel',
		           html:'<div class=\"vmMain\">关联企业'+
		           '<span style=\"float:right;\" onclick=\"javascript:affiliatedEnterprise();\">>&nbsp;&nbsp;&nbsp;&nbsp;</span></div>'
		       }]
		    }/*,{
		    	xtype: 'fieldset',
		        items:[{
		           xtype:'panel',
		           html:'<div class=\"vmMain\">股东(投资人)信息'+
		           '<span style=\"float:right;\" onclick=\"javascript:shareholderList();\">>&nbsp;&nbsp;&nbsp;&nbsp;</span></div>'
		          }]
		    }*//*,{
		    	xtype: 'fieldset',
		        items:[{
		           xtype:'panel',
		           html:'<div class=\"vmMain\">企业现金流量及销售收入'+
		           '<span style=\"float:right;\" onclick=\"javascript:cashflowandSaleIncomeList();\">>&nbsp;&nbsp;&nbsp;&nbsp;</span></div>'
		       }]
		    }*/
		 ]
    	});

    	this.callParent([config]);
   entpriseDetailmore=function(){
       mobileNavi.push(
		Ext.create('htmobile.customer.enterprise.menudetail.EnterpriseDetailmore',{
			data:data
		        	})
		    	);
  } 	  
  companyPhoto=function(){
       mobileNavi.push(
		Ext.create('htmobile.customer.enterprise.menudetail.CompanyPhoto',{
			data:data
		        	})
		    	);
  }
  legalPerosnInfo=function(){
       mobileNavi.push(
		Ext.create('htmobile.customer.enterprise.menudetail.LegalPerosnInfo',{
			persondata:persondata
		        	})
		    	);
  }
  shareholderList=function(){
       mobileNavi.push(
		Ext.create('htmobile.customer.enterprise.menudetail.ShareholderList',{
			data:data
		        	})
		    	);
  }
  relationEnterpriset=function(){
       mobileNavi.push(
		Ext.create('htmobile.customer.enterprise.menudetail.RelationEnterpriset',{
			data:data
		        	})
		    	);
  }
  leadteamList=function(){
  
   mobileNavi.push(
		Ext.create('htmobile.customer.enterprise.menudetail.LeadteamList',{
			data:data
		        	})
		    	);
  }
  debtList=function(){
   mobileNavi.push(
		Ext.create('htmobile.customer.enterprise.menudetail.DebtList',{
			data:data
		        	})
		    	);
  }
  creditorList=function(data){
   mobileNavi.push(
		Ext.create('htmobile.customer.enterprise.menudetail.CreditorList',{
			data:data
		        	})
		    	);
  }
  spouseForm=function(){
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
  bankInfoList=function(){
   mobileNavi.push(
		Ext.create('htmobile.customer.person.menudetail.BankInfoList',{
			data:data,
			type:0
		        	})
		    	);
  }
  outassureList=function(){
   mobileNavi.push(
		Ext.create('htmobile.customer.enterprise.menudetail.OutassureList',{
			data:data
		        	})
		    	);
  }
  
  outinvestList=function(){
   mobileNavi.push(
		Ext.create('htmobile.customer.enterprise.menudetail.OutinvestList',{
			data:data
		        	})
		    	);
  }
  prizeList=function(){
   mobileNavi.push(
		Ext.create('htmobile.customer.enterprise.menudetail.PrizeList',{
			data:data
		        	})
		    	);
  } 
  paytaxList=function(){
   mobileNavi.push(
		Ext.create('htmobile.customer.enterprise.menudetail.PaytaxList',{
			data:data
		        	})
		    	);
  }
  lawsuitList=function(data){
   mobileNavi.push(
		Ext.create('htmobile.customer.enterprise.menudetail.LawsuitList',{
			data:data
		        	})
		    	);
  }
  creditRatingManageList=function(){
   mobileNavi.push(
		Ext.create('htmobile.customer.person.menudetail.CreditRatingManageList',{
			data:data,
			type:"企业"
		        	})
		    	);
  }
  affiliatedEnterprise=function(data){
   mobileNavi.push(
		Ext.create('htmobile.customer.enterprise.menudetail.AffiliatedEnterprise',{
			data:data
		        	})
		    	);
  }
  cashflowandSaleIncomeList=function(){
   mobileNavi.push(
		Ext.create('htmobile.customer.enterprise.menudetail.CashflowandSaleIncomeList',{
			data:data
		        	})
		    	);
  }
  upAndDownStreamTab=function(){
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
  upAndDownContractTab=function(){
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
  headerInfo=function(){
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
  businessContactTab=function(){
   mobileNavi.push(
		Ext.create('htmobile.customer.person.menudetail.BusinessContactTab',{
			data:data,
			type:"company_customer"
		        	})
		    	);
  }
  
  }
});

   
  


