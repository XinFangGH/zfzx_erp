
//creditorList.js
Ext.define('htmobile.creditFlow.public.main.SlContractList', {
    extend: 'mobile.List',
    
    name: 'SlContractList',

    constructor: function (_cfg) {
    	Ext.applyIf(this, _cfg);
		url=__ctxPath +  '/contract/getContractTreeProcreditContract.do'
    	var panel=Ext.create('tableHeader',{style:"margin-left:10px;",header:new Array("<span class='tablehead' style='width:45%'>合同类型</span>",
    	                                                      "<span class='tablehead' style='width:45%'>合同名称</span>"
    	                                                    )});
    	Ext.apply(_cfg,{
    		modeltype:"SlContractList",
    		flex:1,
    		width:"100%",
		    height:"100%",
    		items:[panel],
    		fields:[{
					name : 'id'
				}, {
					name : 'parentId'
				}, {
					name : 'contractCategoryTypeText'
				}, {
					name : 'number'
				}, {
					name : 'projId'
				}, {
					name : 'mortgageId'
				}, {
					name : 'isOld'
				}, {
					name : 'remark'
				}, {
					name : 'isUpload'
				}, {
					name : 'issign'
				}, {
					name : 'isAgreeLetter'
				}, {
					name : 'isSeal'
				}, {
					name : 'contractCategoryText'
				}, {
					name : 'contractId'
				}, {
					name : 'contractName'
				}, {
					name : 'contractNumber'
				}, {
					name : 'text'
				}, {
					name : 'draftName'
				}, {
					name : 'draftDate'
				}, {
					name : 'localParentId'
				}, {
					name : 'templateId'
				}, {
					name : 'isLegalCheck'
				}, {
					name : 'verifyExplain'
				}, {
					name : 'orderNum'
				}, {
					name : 'fileCount'
				}, {
					name : 'temptId'
				},{
					name : 'issign'
				},{
					name : 'signDate'
				},{
				    name : 'isRecord'
				},{
				    name : 'recordRemark'
				},{
				    name : 'searchRemark'
				},{
					name : 'htType'
				},{
					name : 'mortgagename'
				},{
					name : 'mortgageTypeValue'
				},{
					name : 'leaseObjectId'
				},{
					name : 'url'
				}],
    	        url : url,
	    		totalProperty : 'totalProperty',
				root : 'topics',
				params:{
				
				        projId : this.type==null?this.projectId:this.bidPlanId,
						businessType : this.businessType,
						htType : this.htType,
						type:this.type
				},
 		          itemTpl:new Ext.XTemplate( 
		    		"<span   class='tablelist' style='width:45%';>{contractCategoryTypeText}</span>&nbsp;&nbsp;" +
		    		"<span   class='tablelist' style='width:45%'>{contractCategoryText}</span>" +
		    		"<span   class='tablelist' style='width:20%;color:#a7573b;text-decoration:underline' onclick=\"javascript:downLoadFile('{url:this.filepathFormat}')\">合同下载</span>"+
		    		"<span   class='tablelist' style='width:20%;color:#a7573b;text-decoration:underline' onclick=\"javascript:downFileJS({id})\")\">附件下载</span>"+
		    		"<span class='tableDetail'  onclick='javascript:itemsingletap({" +
		    		"contractCategoryTypeText:\"{contractCategoryTypeText}\",contractCategoryText:\"{contractCategoryText}\",contractNumber:\"{contractNumber}\",mortgagename:\"{mortgagename}\"," +
		    		"issign:\"{issign}\",signDate:\"{signDate}\"" +
		    		",isRecord:\"{isRecord}\",recordRemark:\"{recordRemark}\",isLegalCheck:\"{isLegalCheck}\"});'>></span>"
		    		,{
		    		filepathFormat: function(filepath) {
		    			 	var reg = /\\/g;
                            var  filepath=filepath.replace(reg,"/");
                            return filepath;
						}  
		    		})
		    		
		    		
 	       /* listeners : {
				itemsingletap : this.itemsingletap
			}*/
    	});
    	 downFileJS=function(contractId){
	     	var mark = "cs_procredit_contract."+contractId;
	       var	typeisfile=null;
	        var	title='附件下载';
	        mobileNavi.push(
				            Ext.create('htmobile.creditFlow.public.DownLoadCsFile',{
					         mark:mark,
					         typeisfile:typeisfile,
							 title:title
				        	}));
      }
      
    	 itemsingletap =function(data) {
	    	  var label = new Array("合同类型","合同名称","合同编号","合同对象"
	    	   ,Ext.isEmpty(data.isLegalCheck)?null:"是否法务确认"
	    	  ,Ext.isEmpty(data.issign)?null:"是否签署",Ext.isEmpty(data.issign)?null:"签署时间",
	    	  Ext.isEmpty(data.isRecord)?null:"是否归档",Ext.isEmpty(data.isRecord)?null:"归档备注"); 
	    	  var value = new Array(data.contractCategoryTypeText,data.contractCategoryText,data.contractNumber,data.mortgagename
	    	   ,data.isLegalCheck=="false"?"否":"是"
	    	  ,data.issign=="false"?"否":"是",data.signDate
	    	  ,data.isRecord=="false"?"否":"是",data.recordRemark);
	          getListDetail(label,value);
          
    	}
    	
    	
    	this.callParent([_cfg]);

    	}	
		    

});
