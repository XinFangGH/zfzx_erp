//LendForm.js
Ext.define('htmobile.project.menudetail.SlSmallloanProjectVM', {
    extend: 'Ext.Panel',
    name: 'SlSmallloanProjectVM',
    constructor: function (config) {
    	var bottomBar= Ext.create('htmobile.public.bottomBarIndex',{
        });
        config = config || {};
    	var taskId=config.taskId;
		var defId=config.defId;
		var preTaskName=config.preTaskName;
		var isSignTask=config.isSignTask;
		var trans=config.trans;
		var taskName=config.taskName;
		var activityName=config.activityName;
		var projectId= config.projectId;
		var personId=config.personId;
		var businessType=config.businessType;
		var oppositeType=config.OppositeType;
		var oppositeID=config.oppositeID;
		var customerName=config.customerName;
		var projectMoney=config.projectMoney;
       	creditLoanProjectInfo=function(){
	   	Ext.Ajax.request({
					url : __ctxPath + '/htmobile/getCreditLoanProjectInfoVmInfo.do',
					params:{
					   projectId:projectId
					},
				    success : function(response) {
					var result = Ext.util.JSON.decode(response.responseText);
						data = result.data;
					     mobileNavi.push(
			             Ext.create('htmobile.creditFlow.public.CreditLoanProjectInfoPanel',{
				        data:data,
				         readOnly:true
			        	})
		    	);
				}
			})
    }
  //接口需求
  getLendForm=function(){
	   Ext.Ajax.request({
					url : __ctxPath + '/htmobile/lendNeedVmInfo.do',
					params:{
					  projectId:vars.projectId
					},
				    success : function(response) {
					var result = Ext.util.JSON.decode(response.responseText);
						data = result.data;
					     mobileNavi.push(
			             Ext.create('htmobile.creditFlow.public.main.LendForm',{
				        result:data,
				        projectId:vars.projectId,
				         readOnly:true
			        	})
		    	);
				}
			})
    }
  getLendForm=function(){
	   Ext.Ajax.request({
					url : __ctxPath + '/htmobile/lendNeedVmInfo.do',
					params:{
					  projectId:projectId
					},
				    success : function(response) {
					var result = Ext.util.JSON.decode(response.responseText);
						data = result.data;
					     mobileNavi.push(
			             Ext.create('htmobile.creditFlow.public.main.LendForm',{
				        result:data,
				         readOnly:true
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
					    projectId:projectId,
					    businessType:businessType
					},
					async:false,
				    success : function(response) {
						var obj = Ext.util.JSON.decode(response.responseText);
					   personId=obj.oppositeID
				}
			});
    	if(oppositeType=='person_customer'){
    
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
				    //    result:data.vPersonDic,
				        result:data,
				         readOnly:true
			        	})
		    	);
				}
			});
     
    	
    	}else{
    
                Ext.Ajax.request({
					url : __ctxPath+ '/creditFlow/customer/enterprise/loadInfoEnterprise.do',
					params:{
					    id:personId
					},
				    success : function(response) {
						var obj = Ext.util.JSON.decode(response.responseText);
						var data = obj.data.enterprise;
						var persondata= obj.data.person;
					    mobileNavi.push(
			            Ext.create('htmobile.customer.enterprise.EnterpriseDetail',{
				        result:data,
				        persondata:persondata,
				         readOnly:true
			        	})
		    	);
				}
			});
    	
    	
    	}
    	
	  
    }
      getCustomerartialList=function(){
    	 var personId=0;
    	  Ext.Ajax.request({
					url : __ctxPath + '/htmobile/getPersonIdVmInfo.do',
					params:{
					    projectId:projectId,
					    businessType:businessType
					},
					async:false,
				    success : function(response) {
						var obj = Ext.util.JSON.decode(response.responseText);
					   personId=obj.oppositeID
				}
			});
    	if(oppositeType=='person_customer'){
    
          Ext.Ajax.request({
					url : __ctxPath + '/creditFlow/customer/person/seeInfoPerson.do',
					async:false,
					params:{
					    id:personId
					},
				    success : function(response) {
						var obj = Ext.util.JSON.decode(response.responseText);
						var data = obj.data;
					   	data.type='cs_person';
				    	   mobileNavi.push(
				    		  Ext.create('htmobile.InformationCollection.person.personMaterialsList',{
								          data:data,
				         readOnly:true
							        	})
				    		);
				}
			});
     
    	
    	}else{
    
                Ext.Ajax.request({
					url : __ctxPath+ '/creditFlow/customer/enterprise/loadInfoEnterprise.do',
					params:{
					    id:personId
					},
				    success : function(response) {
						var obj = Ext.util.JSON.decode(response.responseText);
						var data = obj.data.enterprise;
						var persondata= obj.data.person;
							data.type='cs_person';
				    	   mobileNavi.push(
				    		  Ext.create('htmobile.InformationCollection.person.personMaterialsList',{
								          result:data,
								        persondata:persondata,
				         readOnly:true
							        	})
				    		);
				}
			});
    	
    	
    	}
    	
	  
    }
    //联系人列表
    getRelationPersonView=function(){
       if(oppositeType=='person_customer'){
					   mobileNavi.push(
			             Ext.create('htmobile.creditFlow.public.customer.RelationPersonView',{
				          personId:oppositeID,
				          flag : 2 , //0 家庭联系人  1工作证明人 2紧急联系人
				          readOnly:true
				          
							})
							);
			}				
					     
    }
    //银行开户列表
    getBankInfoView=function(){
           if(oppositeType=='person_customer'){
					   mobileNavi.push(
			             Ext.create('htmobile.creditFlow.public.customer.BankInfoView',{
				          personId:oppositeID,
				          isInvest : 0,      //是否是投资客户(0是,1否)
				          isEnterprise : 1 ,   //个人
				           readOnly:true
				          }));
				          
         }else{
    
                     mobileNavi.push(
			             Ext.create('htmobile.creditFlow.public.customer.BankInfoView',{
				          personId:oppositeID,
				          isInvest : 0,        //是否是投资客户(0是,1否)
				          isEnterprise : 0 ,   //企业
				          readOnly:true
                             }));
    
         }
  
					     
    }
      getFundInfo=function(){
	   Ext.Ajax.request({
					url : __ctxPath + '/htmobile/getCreditLoanProjectInfoVmInfo.do',
					params:{
					   projectId:projectId,
					   isOwn:1
					},
				    success : function(response) {
					var result = Ext.util.JSON.decode(response.responseText);
						data = result.data;
					     mobileNavi.push(
			             Ext.create('htmobile.creditFlow.public.FundInfo',{
				         result:data,
				         type:'daikuan',
				         readOnly:true
			        	})
		    	);
				}
			})
    }	
    getSlActualToChargeProduct=function(){
    
    mobileNavi.push(
			             Ext.create('htmobile.creditFlow.public.main.SlActualToChargeProduct',{
				       projectId : projectId,
						isProduct:false,
						isAllReadOnly:true,
						isHiddenAdd:true,
						isHiddenDel:true,
						isReadOnly:true,
						isHidden:true,
						isFlow:true,
						titleText:'手续费用清单'
					  	})
		    	);
     }
	 getBaozMortgageViewList=function(){
		 mobileNavi.push(
		Ext.create('htmobile.creditFlow.public.mortgage.BaozMortgageViewList',{
	                    projectId : projectId,
						businessType :businessType
						})
		);}
	//保证担保
	 getBaozMortgageViewList=function(){
		 mobileNavi.push(
		Ext.create('htmobile.creditFlow.public.mortgage.ZbaozMortgage.BaozMortgageViewList',{
	                    projectId : projectId,
	                    personId:this.personId,
						businessType :businessType,
						readOnly:true
						})
		);}
	//抵押担保
	getDZYMortgageViewList=function(){
		Ext.Ajax.request({
					url : __ctxPath + '/credit/mortgage/getPersonName.do',
					params:{
					    projectId:projectId
					},
				    success : function(response) {
						var result = Ext.util.JSON.decode(response.responseText);
						var personName=result.personName;
						var personId = result.personId;
						var personType = result.personType;
						var data = result.slSmallloanProject;
					   	result.type='sl_smallloan_project';
						 mobileNavi.push(
						Ext.create('htmobile.creditFlow.public.mortgage.DZYMortgageViewList',{
											result:data,
					                        projectId : projectId,
					                        personId : personId,
											businessType :businessType,// 小贷
											personName:personName,
											personType:personType,
											readOnly:true,
											banliEditHidden:true
							 })
							);
					}
			});
	}
	 vehicleInformation=function(){
    
          Ext.Ajax.request({
					url : __ctxPath + '/htmobile/getCreditLoanProjectInfoVmInfo.do',
					async:false,
					params:{
					    projectId:projectId
					},
				    success : function(response) {
						var obj = Ext.util.JSON.decode(response.responseText);
						var data = obj.data;
					   	data.type='sl_smallloan_project';
				    	   mobileNavi.push(
				    		  Ext.create('htmobile.creditFlow.public.other.carLoan.VehicleInformation',{
								          result:data,
								          readOnly:true
							        	})
				    		);
				}
			});
     
    	
    	
	  
    }
     vehicleStorage=function(){
    	
          Ext.Ajax.request({
					url : __ctxPath + '/htmobile/getCreditLoanProjectInfoVmInfo.do',
					async:false,
					params:{
					    projectId:projectId
					},
				    success : function(response) {
						var obj = Ext.util.JSON.decode(response.responseText);
						var data = obj.data;
					   	data.type='sl_smallloan_project';
				    	   mobileNavi.push(
				    		  Ext.create('htmobile.creditFlow.public.other.carLoan.VehicleStorage',{
								          result:data,
								          readOnly:true
							        	})
				    		);
				}
			});
    }
	getRepaymentSourceList=function(){
		
		 mobileNavi.push(
		Ext.create('htmobile.creditFlow.public.main.RepaymentSourceList',{
	                       projectId : projectId,
							businessType :businessType// 小贷
		 })
		);
		}
 
    
 
    getBorrowerInfoList=function(){
		
		 mobileNavi.push(
		Ext.create('htmobile.creditFlow.public.main.BorrowerInfoList',{
	                       projectId : projectId,
							businessType :businessType// 小贷
		 })
		);
		}

       getSlEnterPriseProcreditMaterialsView=function(){
					     mobileNavi.push(
			             Ext.create('htmobile.creditFlow.public.main.SlEnterPriseProcreditMaterialsView',{
						    projectId : projectId,
							businessType : businessType,
							isHiddenEdit : true,
							isHidden:false,
							isHidden_materials : true,
							operationType : 'SmallLoanBusiness'
			        	})
		    	);
		    	
    }
           getSlEnterPriseProcreditMaterialsView=function(){
					     mobileNavi.push(
			             Ext.create('htmobile.creditFlow.public.main.SlEnterPriseProcreditMaterialsView',{
						    projectId : projectId,
							businessType : businessType,
							isHiddenEdit : true,
							isHidden:false,
							isHidden_materials : true,
							operationType : 'SmallLoanBusiness',
				         readOnly:true
			        	})
		    	);
           }
       // 放款收息表
       getSlFundIntentList=function(){
       mobileNavi.push(
			             Ext.create('htmobile.creditFlow.public.main.SlFundIntentList',{
				       projectId : projectId,
				       businessType:businessType,
				       readOnly:true
					  	})
		    	);
    
    }
    	Ext.apply(config,{
		    fullscreen: true,
		    style:'background-color:white;',
		    width:'100%',
		    height:'100%',
		    scrollable:{
		    	direction: 'vertical'
		    },
		    items: [bottomBar,{
		    	html:
		    		'<div class=\"vmMain\"   style=\"background-image:url(htmobile/resources/images/pro_manage1.png)\"  onclick=\"javascript:creditLoanProjectInfo();\">' +
		    	   	'<span>项目基本信息</span>'+
		    	   	'<b id=\"click_btn\" class=\"\"></b>'+
		           '</div>'+
		           
		           '<div class=\"vmMain\"   style=\"background-image:url(htmobile/resources/images/pro_manage2.png)\"  onclick=\"javascript:getLendForm();\">' +
		    	   	'<span>借款需求</span>'+
		    	   	'<b id=\"click_btn\" class=\"\"></b>'+
		           '</div>'+
		           
		           '<div class=\"vmMain\"   style=\"background-image:url(htmobile/resources/images/pro_manage3.png)\"  onclick=\"javascript:getCustom();\">' +
		    	   	'<span>'+(oppositeType=='person_customer'?'个人客户信息':'企业客户信息')+'</span>'+
		    	   	'<b id=\"click_btn\" class=\"\"></b>'+
		           '</div>'+
		           
		           '<div class=\"vmMain\"   style=\"background-image:url(htmobile/resources/images/pro_manage4.png)\"  onclick=\"javascript:getRelationPersonView();\">' +
		    	   	'<span>联系人信息</span>'+
		    	   	'<b id=\"click_btn\" class=\"\"></b>'+
		           '</div>'+
		           
		           '<div class=\"vmMain\"   style=\"background-image:url(htmobile/resources/images/pro_manage5.png)\"  onclick=\"javascript:getBankInfoView();\">' +
		    	   	'<span>银行开户</span>'+
		    	   	'<b id=\"click_btn\" class=\"\"></b>'+
		           '</div>'+
		           
		           '<div class=\"vmMain\"   style=\"background-image:url(htmobile/resources/images/pro_manage6.png)\"  onclick=\"javascript:getDZYMortgageViewList();\">' +
		    	   	'<span>抵质押担保</span>'+
		    	   	'<b id=\"click_btn\" class=\"\"></b>'+
		           '</div>'+
		           
		           '<div class=\"vmMain\"   style=\"background-image:url(htmobile/resources/images/pro_manage7.png)\"  onclick=\"javascript:getBaozMortgageViewList();\">' +
		    	   	'<span>保证担保</span>'+
		    	   	'<b id=\"click_btn\" class=\"\"></b>'+
		           '</div>'+
		           
		           '<div class=\"vmMain\"   style=\"background-image:url(htmobile/resources/images/pro_manage8.png)\"  onclick=\"javascript:vehicleInformation();\">' +
		    	   	'<span>评估核档</span>'+
		    	   	'<b id=\"click_btn\" class=\"\"></b>'+
		           '</div>'+
		           
		            '<div class=\"vmMain\"   style=\"background-image:url(htmobile/resources/images/pro_manage9.png)\"  onclick=\"javascript:vehicleStorage();\">' +
		    	   	'<span>车辆入库信息</span>'+
		    	   	'<b id=\"click_btn\" class=\"\"></b>'+
		           '</div>'+
		           
		           '<div class=\"vmMain\"   style=\"background-image:url(htmobile/resources/images/pro_manage10.png)\"  onclick=\"javascript:getFundInfo();\">' +
		    	   	'<span>资金款项信息</span>'+
		    	   	'<b id=\"click_btn\" class=\"\"></b>'+
		           '</div>'+
		           
		           '<div class=\"vmMain\"   style=\"background-image:url(htmobile/resources/images/pro_manage11.png)\"  onclick=\"javascript:getSlFundIntentList();\">' +
		    	   	'<span>放款收息表</span>'+
		    	   	'<b id=\"click_btn\" class=\"\"></b>'+
		           '</div>'+
		           
		           '<div class=\"vmMain\"   style=\"background-image:url(htmobile/resources/images/pro_manage12.png)\"  onclick=\"javascript:getSlEnterPriseProcreditMaterialsView();\">' +
		    	   	'<span>贷款材料清单</span>'+
		    	   	'<b id=\"click_btn\" class=\"\"></b>'+
		           '</div>'
		    }
		 ]
    	});
    	this.callParent([config]);
    }

});
