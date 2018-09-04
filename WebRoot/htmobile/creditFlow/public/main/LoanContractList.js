/**
 * 贷款合同
 */
Ext.define('htmobile.creditFlow.public.main.LoanContractList', {
    extend: 'mobile.List',
    name: 'LoanContractList',
    constructor: function (_cfg) {
    	Ext.applyIf(this, _cfg);
    	Ext.apply(_cfg,{
    		style:'background-color:white;',
    		modeltype:"LoanContractList",
    		flex:1,
    		width:"100%",
		    height:"100%",
    		title:"贷款合同",
    		items:[{
    		xtype:'panel',
    		docked:'top',
    		items:[{
    			html:`	
    					<div class="list-column">
    						<span>合同类型</span>
    						<span>合同名称</span>
    						<span>合同编号</span>
    					</div>
    				`
    			}]
    		}],
    		fields : [{
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
	        url : __ctxPath+ '/contract/getContractTreeProcreditContract.do',
	        params:{
	        	projId : this.projectId,
				businessType : this.businessType,
				htType : 'loanContract',
				type:''
	        },
    		root:'result',
    	    totalProperty: 'totalCounts',
	        itemTpl: `
	        	 	<div class="list-column-content">
    					<span>{contractCategoryTypeText}</span>
    					<span>{contractCategoryText}</span>
    					<span>{contractNumber}</span>
					</div>
	        		`,
 	        listeners : {
				itemsingletap : this.itemsingletap
			}
    	});
    	this.callParent([_cfg]);

    },
	itemsingletap : function(obj, index, target, record) {
	
	}
});
