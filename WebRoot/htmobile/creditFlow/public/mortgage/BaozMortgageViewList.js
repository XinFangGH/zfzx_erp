

Ext.define('htmobile.creditFlow.public.mortgage.BaozMortgageViewList', {
    extend: 'mobile.List',
    name: 'BaozMortgageViewList',
    constructor: function (_cfg) {
    	this.projectId=_cfg.projectId;
    	this.businessType=_cfg.businessType;
		var	url = __ctxPath +'/credit/mortgage/getMortgageOfBZ.do?projectId='+this.projectId+'&businessType='+this.businessType+'&isReadOnly=true';
    	var panel=Ext.create('tableHeader',{style:"margin-left:10px;",header:new Array("<span class='tablehead' style='width:100%;font-size:15px;'>保证人</span>",
    	                                                      "<span class='tablehead' ><span>",
    	                                                      "<span class='tablehead' ></span>")});
    	Ext.apply(_cfg,{
    		modeltype:"DZYMortgageViewList",
    		flex:1,
    		width:"100%",
		    height:"100%",
		    title:'保证担保',
    		items:[panel],
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
 		        itemTpl: "<span   style='width:100%;font-size:15px;'>{assureofnameEnterOrPerson}</span>&nbsp;&nbsp;" +
 		        "<br/><br/><span  style='width:100%;font-size:12px;' >{personTypeValue}&nbsp;|&nbsp;{assuremodeidValue}</span>&nbsp;&nbsp;" +
		    		"<span class='tableDetail'>></span></div>",
 	        listeners : {
				itemsingletap : this.itemsingletap
			}
    	});
    	this.callParent([_cfg]);

    },
	itemsingletap : function(obj, index, target, record) {
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
			             Ext.create('htmobile.creditFlow.public.mortgage.company.SeeCompany',{
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
			             Ext.create('htmobile.creditFlow.public.mortgage.dutyperson.SeeDutyperson',{
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
    	/*  var data=record.data;
    	  var label = new Array("保证人","保证人类型","保证类型","与债务人关系",
    	  "合同个数","是否提交","提交时间人","提交备注",
    	   "是否收到","收到时间","收到备注"
    	 ); 
    	  var value = new Array(data.assureofnameEnterOrPerson,data.personTypeValue,data.assuremodeidValue,data.relation
          ,data.contractCount,data.isArchiveConfirm,data.unchaindate,data.remarks
         ,data.isRecieve,data.recieveDate,data.recieveRemarks);
          getListDetail(label,value);*/
		    

}
});
