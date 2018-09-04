
//creditorList.js
Ext.define('htmobile.creditFlow.public.mortgage.DZYMortgageViewList', {
    extend: 'mobile.List',
    id:'DZYMortgageViewList',
    name: 'DZYMortgageViewList',
    constructor: function (_cfg) {
    	this.data=_cfg.result;
    	Ext.apply(this,_cfg);
    	if(Ext.isEmpty(_cfg.banliEditHidden)){
    	  this.banliEditHidden=true;
    	}else{
    	   this.banliEditHidden=_cfg.banliEditHidden;
    	}
		var url = __ctxPath +'/credit/mortgage/getMortgageOfDY.do?productId=105';
		//列名
    	var panel={
    		xtype:'panel',
    		docked:'top',
    		items:[{
    			html:`	
    					<div class="list-column">
    					<span>抵质押类型</span>
    					<span>担保类型</span>
    					<span>所有权人</span>
    					</div>
    				`
    		}]
    	}
    	Ext.apply(_cfg,{
    		style:'background-color:white;',
    		title:'抵质押担保',
    		modeltype:"DZYMortgageViewList",
    		flex:1,
    		width:"100%",
		    height:"100%",
    		items:this.readOnly?[panel]:[{
						style : "margin:auto;margin-top:10px;",
						width:'50%',
						cls : 'submit-button',
						xtype : 'button',
						docked:'top',
						handler : this.addbtn,
						scope : this,
						text : '添加'
					},panel],
	        plugins:[ {
	            type:"listopt",
	            itemFilter:function(list,action, record){
	            	if(action=="See"){
	            		
	            	   return true;
	            	}
	               if(action=="Edit"){
	               	  //加权限
		               	if(list.readOnly){
		               	   return false;
		               	}else{
		               		return true;
		               	}
	            	}
	            	if(action=="Remove"){
	            		 //加权限
	            	    if(list.readOnly){
		               	   return false;
		               	}else{
		               		return true;
		               	}
	            	}
	            	
	            	if(action=="banli"){
	            		 //加权限
	            	    if(list.banliEditHidden){
		               	   return false;
		               	}else{
		               		return true;
		               	}
	            	}
	            },
	            items:[{
	                action:"See",
	                cls:"write",
	                color:"grey",
	                text:"查看"
	            },  {
	                action:"Edit",
	                cls:"write",
	                color:"yellow",
	                text:"编辑"
	            }, {
	                action:"Remove",
	                cls:"trash",
	                color:"red",	
	                text:"删除"
	            } ]
	        } ],
    		fields:[{
						name : 'id',
						type : 'int'
					}, 'mortgagename','mortgagepersontypeforvalue','assureofname', 
			           'assuremoney','finalprice','finalCertificationPrice','remarks','assuretypeidValue','personTypeValue',
			           'mortgagepersontypeidValue','assuremodeidValue','enregisterDatumList',
			           'pledgenumber','bargainNum','isAuditingPassValue','planCompleteDate',
			           'statusidValue','enregisterperson','unchainofperson','enregisterdate','isunchain',
			           'unchaindate','typeid','others', 'assureofnameEnterOrPerson','relation','needDatumList',
			          'receiveDatumList','lessDatumRecord','businessPromise','replenishTimeLimit',
			           'isReplenish','replenishDate','personTypeId','projnum','projname','enterprisename','isArchiveConfirm','businessType',
			           'contractCategoryTypeText','contractCategoryText','isLegalCheck','thirdRalationId','contractId','categoryId','temptId',
			           'issign','signDate','fileCount','isTransact','transactDate','fileCounts','contractContent','contractCount','enregisterDepartment','mortgageRemarks',
			           'isHandle','isRecieve','recieveDate','recieveRemarks','havingTransactFile','havingUnchainFile'],
    	        url : url,
    	        params:{
    	        projectId:this.projectId,
    	        businessType:this.businessType
    	        },
	    		root:'result',
	    	    totalProperty: 'totalCounts',
 		        itemTpl:`
 		        	 	<div class="list-column-content">
	    					<span>{mortgagepersontypeforvalue}</span>
	    					<span>{assuretypeidValue}</span>
	    					<span>{assureofnameEnterOrPerson}</span>
    					</div>
 		        		`
 	       /* listeners : {
				itemsingletap : this.itemsingletap
			}*/
    	});
    	this.callParent([_cfg]);
    	
    		

   
	itemsingletap = function(data) {}
	
	 },
    initialize:function() {
        this.callParent(arguments);
        this.on({
            listoptiontap:"optTap",
            scope:this
        });
    },
    optTap:function(action, list, record) {
    	var obj=this;
       var id=  record.data.id;
       var data=record.data;
        if (action == "Edit") {
        	var mortgageId =data.id;
		    var typeId = data.typeid;
		    if(typeId==1){
			    Ext.Ajax.request({
					url : __ctxPath +'/credit/mortgage/seeVehicleForUpdate.do',
					params : {
						id : mortgageId,
						businessType :  this.businessType
					},
				    success : function(response) {
					var result = Ext.util.JSON.decode(response.responseText);
				    mobileNavi.push(Ext.create('htmobile.creditFlow.public.mortgage.DZYMortgageFormBaseAdd',{
				          MortgageData:result.data,
				          productId:this.productId,
				          typeId:typeId
			        	})
			    	);
				}
			});
		}else if(typeId==7){	
			    Ext.Ajax.request({
					url : __ctxPath +'/credit/mortgage/seeHouseForUpdate.do',
					params : {
						id : mortgageId,
						businessType :  this.businessType
					},
				    success : function(response) {
					var result = Ext.util.JSON.decode(response.responseText);
				    mobileNavi.push(Ext.create('htmobile.creditFlow.public.mortgage.DZYMortgageFormBaseAdd',{
				          MortgageData:result.data,
				          productId:this.productId,
				          typeId:typeId
			        	}));
				}
			});
			
		}
        } else if (action == "Remove") {
        	Ext.Ajax.request({
				url : __ctxPath + '/credit/mortgage/deleteMortgage.do',
				params:{
					mortgageIds:id
				},
			   	success : function(response) {
			       var responseText = Ext.util.JSON.decode(response.responseText);
				   if (responseText.success==true) {
				   	  Ext.Msg.alert("", responseText.data);
				      obj.store.loadPage(1);
				   }else{
						Ext.Msg.alert("", "删除失败");
						return;
					}
			}
		});  	
        }else if (action == "See") {
					var mortgageId =data.id;
					var typeId = data.typeid;
					var businessType = data.businessType;
					if(typeId==1){	
						Ext.Ajax.request({
								url : __ctxPath +'/credit/mortgage/seeVehicleForUpdate.do',
								params : {
									id : mortgageId,
									businessType :  businessType
								},
							    success : function(response) {
								var result = Ext.util.JSON.decode(response.responseText);
							    mobileNavi.push(
						             Ext.create('htmobile.creditFlow.public.mortgage.vehicle.SeeVehicle',{
							          MortgageData:result.data
						        	})
						    	);
							}
						});
						
					}else if(typeId==2){	
						Ext.Ajax.request({
								url : __ctxPath +'/credit/mortgage/seeStockownershipForUpdate.do',
								params : {
									id : mortgageId,
									businessType :  businessType
								},
							    success : function(response) {
								var result = Ext.util.JSON.decode(response.responseText);
							    mobileNavi.push(
						             Ext.create('htmobile.creditFlow.public.mortgage.stockownership.SeeStockownership',{
							          MortgageData:result.data
						        	})
						    	);
							}
						});
						
					}else if(typeId==3){
							seeCompanyInfo(mortgageId,businessType);//fixed ok by gao
						}else if(typeId==4){
							seeDutyPersonInfo(mortgageId,businessType); //fixed ok by gao
						}else if(typeId==5){	
						Ext.Ajax.request({
								url : __ctxPath +'/credit/mortgage/seeMachineinfoForUpdate.do',
								params : {
									id : mortgageId,
									businessType :  businessType
								},
							    success : function(response) {
								var result = Ext.util.JSON.decode(response.responseText);
							    mobileNavi.push(
						             Ext.create('htmobile.creditFlow.public.mortgage.machineinfo.SeeMachineinfo',{
							          MortgageData:result.data
						        	})
						    	);
							}
						});
						
					}else if(typeId==6){	
						Ext.Ajax.request({
								url : __ctxPath +'/credit/mortgage/seeProductForUpdate.do',
								params : {
									id : mortgageId,
									businessType :  businessType
								},
							    success : function(response) {
								var result = Ext.util.JSON.decode(response.responseText);
							    mobileNavi.push(
						             Ext.create('htmobile.creditFlow.public.mortgage.Product.SeeProduct',{
							          MortgageData:result.data
						        	})
						    	);
							}
						});
						
					}else if(typeId==7 || typeId==15 || typeId==16 || typeId==17){	
						Ext.Ajax.request({
								url : __ctxPath +'/credit/mortgage/seeHouseForUpdate.do',
								params : {
									id : mortgageId,
									businessType :  businessType
								},
							    success : function(response) {
								var result = Ext.util.JSON.decode(response.responseText);
							    mobileNavi.push(
						             Ext.create('htmobile.creditFlow.public.mortgage.house.SeeHouse',{
							          MortgageData:result.data
						        	})
						    	);
							}
						});
						
					}else if(typeId==8){	
						Ext.Ajax.request({
								url : __ctxPath +'/credit/mortgage/seeOfficebuildingForUpdate.do',
								params : {
									id : mortgageId,
									businessType :  businessType
								},
							    success : function(response) {
								var result = Ext.util.JSON.decode(response.responseText);
							    mobileNavi.push(
						             Ext.create('htmobile.creditFlow.public.mortgage.officebuilding.SeeOfficebuilding',{
							          MortgageData:result.data
						        	})
						    	);
							}
						});
						
					}else if(typeId==9){	
						Ext.Ajax.request({
							url : __ctxPath +'/credit/mortgage/seeHousegroundForUpdate.do',
								params : {
									id : mortgageId,
									businessType :  businessType
								},
							    success : function(response) {
								var result = Ext.util.JSON.decode(response.responseText);
							    mobileNavi.push(
						             Ext.create('htmobile.creditFlow.public.mortgage.houseground.SeeHouseground',{
							          MortgageData:result.data
						        	})
						    	);
							}
						});
						
					}else if(typeId==10){	
						Ext.Ajax.request({
								url : __ctxPath +'/credit/mortgage/seeBusinessForUpdate.do',
								params : {
									id : mortgageId,
									businessType :  businessType
								},
							    success : function(response) {
								var result = Ext.util.JSON.decode(response.responseText);
							    mobileNavi.push(
						             Ext.create('htmobile.creditFlow.public.mortgage.business.SeeBusiness',{
							          MortgageData:result.data
						        	})
						    	);
							}
						});
						
					}else if(typeId==11){	
						Ext.Ajax.request({
								url : __ctxPath +'/credit/mortgage/seeBusinessAndLiveForUpdate.do',
								params : {
									id : mortgageId,
									businessType :  businessType
								},
							    success : function(response) {
								var result = Ext.util.JSON.decode(response.responseText);
							    mobileNavi.push(
						             Ext.create('htmobile.creditFlow.public.mortgage.businessandlive.SeeBusinessandlive',{
							          MortgageData:result.data
						        	})
						    	);
							}
						});
						
					}else if(typeId==12){
							seeEducationInfo(mortgageId,businessType);//fixed ok by gao untested
					}else if(typeId==13){	
						Ext.Ajax.request({
							url : __ctxPath +'/credit/mortgage/seeIndustryForUpdate.do',
								params : {
									id : mortgageId,
									businessType :  businessType
								},
							    success : function(response) {
								var result = Ext.util.JSON.decode(response.responseText);
							    mobileNavi.push(
						             Ext.create('htmobile.creditFlow.public.mortgage.industry.SeeIndustry',{
							          MortgageData:result.data
						        	})
						    	);
							}
						});
						
					}else if(typeId==14){	
						Ext.Ajax.request({
									url : __ctxPath +'/credit/mortgage/seeDroitForUpdate.do',
								params : {
									id : mortgageId,
									businessType :  businessType
								},
							    success : function(response) {
								var result = Ext.util.JSON.decode(response.responseText);
							    mobileNavi.push(
						             Ext.create('htmobile.creditFlow.public.mortgage.droit.SeeDroit',{
							          MortgageData:result.data
						        	})
						    	);
							}
						});
						
					}else{
							window.location.href="/error.jsp";
						}
				}
    },
	addbtn:function(){
	   mobileNavi.push(
			             Ext.create('htmobile.creditFlow.public.mortgage.DZYMortgageFormBaseAdd',{
			             productId:this.productId,
				         projectId: this.projectId,
    	                 businessType:this.businessType,
    	                 personId:this.personId,
    	                 readOnly:this.readOnly
			        	})
			    	);
	 
	 }
});
