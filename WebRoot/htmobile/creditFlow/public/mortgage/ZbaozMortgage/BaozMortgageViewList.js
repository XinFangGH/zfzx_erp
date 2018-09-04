

Ext.define('htmobile.creditFlow.public.mortgage.ZbaozMortgage.BaozMortgageViewList', {
    extend: 'mobile.List',
    name: 'BaozMortgageViewList',
    id:"BaozMortgageViewList",
    constructor: function (_cfg) {
    	this.projectId=_cfg.projectId;
    	this.businessType=_cfg.businessType;
    	this.readOnly=_cfg.readOnly;
    	if(Ext.isEmpty(_cfg.banliEditHidden)){
    	  this.banliEditHidden=true;
    	}else{
    	   this.banliEditHidden=_cfg.banliEditHidden;
    	}
    	this.customerPersonName = _cfg.customerPersonName;
		var	url = __ctxPath +'/credit/mortgage/getMortgageOfBZ.do?projectId='+this.projectId+'&businessType='+this.businessType+'&isReadOnly=true';
    	var button=this.readOnly?{}:Ext.create('Ext.Panel',{
			docked:'top',
		    items:[{
		       style:'margin:15px 100px;border-radius: 0.2em;color:#ffffff;font-family: 微软雅黑;',
		       xtype: 'button',
		       handler:this.addbtn,
		       scope:this,
		       text:'添加'
		      }]
		});
    	var panel={
    		xtype:'panel',
    		docked:'top',
    		items:[{
    			html:`	
    					<div class="list-column">
    					<span>保证人</span>
    					<span>保证人类型</span>
    					<span>保证类型</span>
    					</div>
    				`
    		}]
    	};
    	Ext.apply(_cfg,{
    		style:'background-color:white;',
    		modeltype:"DZYMortgageViewList",
    		flex:1,
    		width:"100%",
		    height:"100%",
		    title:'保证担保',
    		items:this.readOnly?[panel]:[button,panel],
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
			           'assuremoney','finalprice','remarks','assuretypeidValue','personTypeValue',
			           'mortgagepersontypeidValue','assuremodeidValue','enregisterDatumList',
			           'pledgenumber','bargainNum','isAuditingPassValue','planCompleteDate',
			           'statusidValue','enregisterperson','unchainofperson','enregisterdate','isunchain',
			           'unchaindate','typeid','others', 'assureofnameEnterOrPerson','relation','needDatumList',
			          'receiveDatumList','lessDatumRecord','businessPromise','replenishTimeLimit',
			           'isReplenish','replenishDate','personTypeId','projnum','projname','enterprisename','isArchiveConfirm','businessType',
			           'contractCategoryTypeText','contractCategoryText','isLegalCheck','thirdRalationId','contractId','categoryId','temptId',
			           'issign','signDate','fileCount','isTransact','transactDate','fileCounts','contractContent','contractCount','enregisterDepartment','mortgageRemarks','isRecieve','recieveDate','recieveRemarks'],
    	        url : url,
	    		root:'result',
	    	    totalProperty: 'totalCounts',
 		        itemTpl:`
 		        	 	<div class="list-column-content">
	    					<span>{assureofnameEnterOrPerson}</span>
	    					<span>{personTypeValue}</span>
	    					<span>{assuremodeidValue}</span>
    					</div>
 		        		`,
 	        listeners : {
				itemsingletap : this.itemsingletap
			}
    	});
    	this.callParent([_cfg]);

    },
	itemsingletap : function(obj, index, target, record) {
			var typeId =  record.data.personTypeId;
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
       var data=record.data;
        if (action == "Edit") {
        	
        	var mortgageId =data.id;
			var businessType = data.businessType;
			var personId = data.personId;
			var projectId = data.projectId;
			var typeId =  data.personTypeId;
			if(typeId==602){
			Ext.Ajax.request({
					url : __ctxPath +'/credit/mortgage/seeCompanyForUpdate.do',
					params : {
						id : mortgageId,
						businessType :  businessType
					},
				    success : function(response) {
					var result = Ext.util.JSON.decode(response.responseText);
					data = result.data;
				    mobileNavi.push(
			             Ext.create('htmobile.creditFlow.public.mortgage.ZbaozMortgage.updateEnterpriseBaozMortgageInfo',{
				          MortgageData:data,
				          projectId:projectId,
				          readOnly:false
			        	})
			    	);
				}
			});
			}else if(typeId==603){
				Ext.Ajax.request({
					url : __ctxPath +'/credit/mortgage/seeDutypersonForUpdate.do',
					params : {
						id : mortgageId,
						businessType :  businessType
					},
				    success : function(response) {
					var result = Ext.util.JSON.decode(response.responseText);
					data = result.data;
				    mobileNavi.push(Ext.create('htmobile.creditFlow.public.mortgage.ZbaozMortgage.updatePersonBaozMortgageInfo',{
				          MortgageData:data,
				          readOnly:false
			        	})
			    	);
				}
			});
			}
        	
        } else if (action == "Remove") {
        	var mortgageIds =data.id;
        	Ext.Ajax.request({
				url : __ctxPath + '/credit/mortgage/deleteMortgage.do',
				params:{
					mortgageIds:mortgageIds
				},
			   	success : function(response) {
			        var responseText = Ext.util.JSON.decode(response.responseText);
			        
				   if (responseText.success==true) {
				   	  Ext.Msg.alert("", "删除成功");
				      obj.store.loadPage(1);
				   	
				   }else{
			
						Ext.Msg.alert("", "删除失败");
						return;
					
				}
			}
		});  	
        }else if (action == "See") {
        	var obj=this;
       var data=this.data;
       var businessType = this.businessType;
       var typeId =  record.data.personTypeId;
		if(typeId==602){	
			Ext.Ajax.request({
					url : __ctxPath +'/credit/mortgage/seeCompanyForUpdate.do',
					params : {
						id : record.data.id,
						businessType :  record.data.businessType
				//		mfinancingId :  record.data.mfinancingId
					},
				    success : function(response) {
					var result = Ext.util.JSON.decode(response.responseText);
					data = result.data;
				    mobileNavi.push(
			             Ext.create('htmobile.creditFlow.public.mortgage.ZbaozMortgage.SeeCompany',{
				          readOnly:true,
				          MortgageData:data
			        	})
			    	);
				}
			});
			
		}else if(typeId==603){
			Ext.Ajax.request({
				url : __ctxPath +'/credit/mortgage/seeDutypersonForUpdate.do',
				method : 'POST',
				success : function(response, request) {
					var result = Ext.util.JSON.decode(response.responseText);
					data = result.data;
				    mobileNavi.push(
			             Ext.create('htmobile.creditFlow.public.mortgage.ZbaozMortgage.SeeDutyperson',{
				          readOnly:true,
				          MortgageData:data
			        	})
			    	);
				},
				failure : function(response) {
					Ext.Msg.alert('状态', '操作失败，请重试');
				},
				params : {
					id : record.data.id,
					businessType :  record.data.businessType
				}
			});
		}
		}
    },
	 addbtn:function(){
	 personId=this.personId;
	 projectId = this.projectId;
	 var readOnly = false;
	 flag=this.flag;
	   mobileNavi.push(
			             Ext.create('htmobile.creditFlow.public.mortgage.ZbaozMortgage.BaseBaozMortgageInfo',{
				         personId: personId,
				         projectId:projectId,
				         flag: flag,
    	                 businessType:this.businessType,
    	                 readOnly:readOnly
			        	})
			    	);
	 
	 }
});
