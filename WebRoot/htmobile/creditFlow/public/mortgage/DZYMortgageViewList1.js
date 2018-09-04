
//creditorList.js
Ext.define('htmobile.creditFlow.public.mortgage.DZYMortgageViewList', {
    extend: 'mobile.List',
    id:'DZYMortgageViewList',
    name: 'DZYMortgageViewList',
    constructor: function (_cfg) {
    	this.projectId=_cfg.projectId;
    	this.businessType=_cfg.businessType;
		var url = __ctxPath +'/credit/mortgage/getMortgageOfDY.do';
		var button=Ext.create('Ext.Panel',{
			docked:'top',
		      items:[
		      {
		       style:"margin:15px 100px  ",
		       xtype:'button',
		       handler:this.addbtn,
		       scope:this,
		       text:'添加'
		       		
		      }
		      ]
			
		});
    	var panel=Ext.create('tableHeader',{style:"margin-left:10px;",header:new Array("<span class='tablehead' style='width:25%;' >抵质押类型</span>",
    	                                                      "<span class='tablehead' style='width:25%;'>担保类型</span>",
    	                                                      "<span class='tablehead' >所有权人</span>")});
    	Ext.apply(_cfg,{
    		modeltype:"DZYMortgageViewList",
    		flex:1,
    		width:"100%",
		    height:"100%",
    		items:[button,panel],
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
			           'isHandle','isRecieve','recieveDate','recieveRemarks','havingTransactFile','havingUnchainFile','datumNums'],
    	        url : url,
    	        params:{
    	        projectId:this.projectId,
    	        businessType:this.businessType
    	        },
	    		root:'result',
	    	    totalProperty: 'totalCounts',
 		        itemTpl: "<span   class='tablelist' style='width:25%;'>{mortgagepersontypeforvalue}</span>&nbsp;&nbsp;" +
 		        "<span   class='tablelist' style='width:24%;'>{assuretypeidValue}</span>&nbsp;&nbsp;" +
		    	"<span   class='tablelist' style='width:38%;'>{assureofnameEnterOrPerson}</span>&nbsp;&nbsp;" +
		   // 	"<br/><span   class='tablelisttwo' onclick='javascript:downReportFileJS({id})'><span style='color:#a7573b;text-decoration:underline;'>下载(<tpl if='datumNums==null'>0</tpl><tpl if='datumNums!=null'>{datumNums}</tpl>份)</span></span>" +
		    	
		    	"<span class='tableDetail'  onclick='javascript:itemsingletap({id:{id},typeid:{typeid},businessType:\"{businessType}\",})'>></span>"
		    	
		    		
		    		
 	       /* listeners : {
				itemsingletap : this.itemsingletap
			}*/
    	});
    	this.callParent([_cfg]);
    	
    		
      downReportFileJS=function(id){
       var	mark='cs_procredit_mortgage.'+id;
       var	typeisfile=null;
        var	title='下载';
        mobileNavi
        mobileNavi.push(
			            Ext.create('htmobile.creditFlow.public.file.DownLoadCsFile',{
				         mark:mark,
				         typeisfile:typeisfile,
						 title:title
			        	}));
      }

   
	itemsingletap = function(data) {
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
					data = result.data;
				    mobileNavi.push(
			             Ext.create('htmobile.creditFlow.public.mortgage.vehicle.SeeVehicle',{
				          MortgageData:data
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
					data = result.data;
				    mobileNavi.push(
			             Ext.create('htmobile.creditFlow.public.mortgage.stockownership.SeeStockownership',{
				          MortgageData:data
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
					data = result.data;
				    mobileNavi.push(
			             Ext.create('htmobile.creditFlow.public.mortgage.machineinfo.SeeMachineinfo',{
				          MortgageData:data
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
					data = result.data;
				    mobileNavi.push(
			             Ext.create('htmobile.creditFlow.public.mortgage.Product.SeeProduct',{
				          MortgageData:data
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
					data = result.data;
				    mobileNavi.push(
			             Ext.create('htmobile.creditFlow.public.mortgage.house.SeeHouse',{
				          MortgageData:data
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
					data = result.data;
				    mobileNavi.push(
			             Ext.create('htmobile.creditFlow.public.mortgage.officebuilding.SeeOfficebuilding',{
				          MortgageData:data
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
					data = result.data;
				    mobileNavi.push(
			             Ext.create('htmobile.creditFlow.public.mortgage.houseground.SeeHouseground',{
				          MortgageData:data
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
					data = result.data;
				    mobileNavi.push(
			             Ext.create('htmobile.creditFlow.public.mortgage.business.SeeBusiness',{
				          MortgageData:data
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
					data = result.data;
				    mobileNavi.push(
			             Ext.create('htmobile.creditFlow.public.mortgage.businessandlive.SeeBusinessandlive',{
				          MortgageData:data
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
					data = result.data;
				    mobileNavi.push(
			             Ext.create('htmobile.creditFlow.public.mortgage.industry.SeeIndustry',{
				          MortgageData:data
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
					data = result.data;
				    mobileNavi.push(
			             Ext.create('htmobile.creditFlow.public.mortgage.droit.SeeDroit',{
				          MortgageData:data
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
				         projectId: this.projectId,
    	                 businessType:this.businessType
			        	})
			    	);
	 
	 }
});
