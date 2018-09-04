
//creditorList.js
Ext.define('htmobile.creditFlow.public.SlContractList', {
    extend: 'mobile.List',
    
    name: 'SlContractList',

    constructor: function (_cfg) {
    	Ext.applyIf(this, _cfg);
    	this.type=null;
    	if (typeof(_cfg.bidPlanId) != "undefined") {
			this.type ="bidplan";
		}
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
 		        itemTpl: 
		    		"<span   class='tablelist' style='width:45%';>{contractCategoryTypeText}</span>&nbsp;&nbsp;" +
		    		"<span   class='tablelist' style='width:45%'>{contractCategoryText}</span>" +
		    		"<span   class='tablelist' style='width:20%;color:#a7573b;text-decoration:underline'>合同下载</span>" ,
		    		
		    		
 	        listeners : {
				itemsingletap : this.itemsingletap
			}
    	});
    	this.callParent([_cfg]);

    },
	itemsingletap : function(obj, index, target, record) {
    	  var data=record.data;
    	  var label = new Array("合同类型","合同名称","合同编号","合同对象"
    	 ,"是否法务确认"
    	  ,"是否签署","签署时间",
    	  "归档备注","归档备注"); 
    	  var value = new Array(data.contractCategoryTypeText,data.contractCategoryText,data.contractNumber,data.mortgagename
    	 ,data.isLegalCheck
    	  ,data.contractNumber,data.signDate
    	  ,data.contractNumber,data.recordRemark);
          getListDetail(label,value);
		    

}
});
