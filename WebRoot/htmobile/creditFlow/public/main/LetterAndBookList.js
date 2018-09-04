
//creditorList.js
Ext.define('htmobile.creditFlow.public.main.LetterAndBookList', {
    extend: 'mobile.List',
    
    name: 'LetterAndBookList',

    constructor: function (_cfg) {
    	Ext.applyIf(this, _cfg);
    	if(typeof(_cfg.projectId)!="undefined") {
	        this.projId=_cfg.projectId;
		}
		if(typeof(_cfg.businessType)!="undefined") {
	        this.businessType=_cfg.businessType;
		}
		if (typeof(_cfg.LBTemplate) != 'undefined') {
		   this.LBTemplate = _cfg.LBTemplate;
		   if(this.businessType == 'Guarantee'&&this.LBTemplate=='assureCommitmentLetter'){//业务类别，企业贷
				this.LBTemplate="assureCommitmentLetter";//模板唯一标识
				this.titleText='对外担保承诺函'
		   }else if(this.businessType == 'Guarantee'&&this.LBTemplate=='simulateEnterpriseBook'){
		   		this.LBTemplate="simulateEnterpriseBook";//模板唯一标识
				this.titleText='拟担保意向书'
		   }else if(this.businessType == 'Guarantee'&&this.LBTemplate=='partnerMeetingResolution'){
		   		this.LBTemplate="partnerMeetingResolution";//模板唯一标识
				this.titleText='股东会决议'
		   }else if(this.businessType == 'SmallLoan'&&this.LBTemplate=='approvalForm'){
		   		this.LBTemplate="approvalForm";//模板唯一标识
				this.titleText='审批表'
		   }else if(this.businessType =='ExhibitionBusiness'&&this.LBTemplate=='superviseRecordVerification'){
		   		this.LBTemplate="superviseRecordVerification";//模板唯一标识
				this.titleText='展期审批表'
		   }else if (this.businessType =='ExhibitionBusiness'&&this.LBTemplate=='superviseRecordNote'){
		   		this.LBTemplate="superviseRecordNote";//模板唯一标识
				this.titleText='展期终审意见通知书'
		   }else if(this.businessType == 'SmallLoan'&&this.LBTemplate=='LoanNotice'){
		   		this.LBTemplate="LoanNotice";//模板唯一标识
				this.titleText='放款通知书文档'
		   }else  if(this.businessType == 'SmallLoan'&&this.LBTemplate=='slSmallloadReviewTable'){
		   		this.LBTemplate="slSmallloadReviewTable";//模板唯一标识
				this.titleText='放款审批表'
		   }else if(this.businessType == 'SmallLoan' && this.LBTemplate=='slAlteraccrualRecordVerification'){
		   		this.LBTemplate ="slAlteraccrualRecordVerification"//模板唯一标识
		   		this.titleText='利率变更审批表'
		   }else if(this.businessType == 'SmallLoan' && this.LBTemplate=='slEarlyrepaymentRecordVerification'){
		   		this.LBTemplate = "slEarlyrepaymentRecordVerification"//模板唯一标识
		   		this.titleText='提前还款审批表'
		   }else if(this.businessType == 'SmallLoan' &&this.LBTemplate=='SmallLoanConfirmLoanNotice'){
		   		this.LBTemplate = "SmallLoanConfirmLoanNotice"//模板唯一标识
		   		this.titleText='终审意见通知书'	
		   }else if(this.businessType == 'LeaseFinance' && this.LBTemplate=='LeaseEarlyrepaymentRecordVerification'){
		   		this.LBTemplate = "LeaseEarlyrepaymentRecordVerification"//模板唯一标识
		   		this.titleText='融资租赁提前还款审批表'
		   } else if(this.businessType == 'LeaseFinance' && this.LBTemplate=='LeaseAlteraccrualRecordVerification'){
		   		this.LBTemplate = "LeaseAlteraccrualRecordVerification"//模板唯一标识
		   		this.titleText='融资租赁利率变更审批表'
		   }else if(this.businessType == 'SmallLoan' && this.LBTemplate=='ZJDBXY'){
		   		this.LBTemplate = "ZJDBXY"//模板唯一标识
		   		this.titleText='中介担保协议'
		   }
	    }
		url = __ctxPath+ '/contract/getLetterAndBookTreeProcreditContract.do';
    	var panel=Ext.create('tableHeader',{style:"margin-left:10px;",header:new Array("<span class='tablehead' style='width:45%'>合同类型</span>",
    	                                                      "<span class='tablehead' style='width:45%'>合同名称</span>"
    	                                                    )});
    	Ext.apply(_cfg,{
    		modeltype:"LetterAndBookList",
    		flex:1,
    		width:"100%",
		    height:"100%",
    		items:[panel],
    		fields:[
			{
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
				},{name : 'issign'}
				,{name : 'signDate'}
				,{name : 'url'}
				,{name : 'isRecord'}
				,{name : 'recordRemark'}
		],
    	        url : url,
	    		totalProperty : 'totalProperty',
				root : 'topics',
				params:{
					htType : this.LBTemplate,
					projId : this.projId,
					businessType : this.businessType
				},
 		          itemTpl:new Ext.XTemplate( 
		    		"<span   class='tablelist' style='width:45%';>{contractCategoryTypeText}</span>&nbsp;&nbsp;" +
		    		"<span   class='tablelist' style='width:45%'>{contractCategoryText}</span>" +
		    		"<span   class='tablelist' style='width:20%;color:#a7573b;text-decoration:underline' onclick=\"javascript:downLoadFile('{url:this.filepathFormat}')\">下载</span>"+
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
	    	  var label = new Array("文件类型","文件名称(已上传)","上传时间"
	    	   ,Ext.isEmpty(data.isLegalCheck)?null:"是否法务确认"
	    	  ,Ext.isEmpty(data.issign)?null:"是否签署",Ext.isEmpty(data.issign)?null:"签署时间",
	    	  Ext.isEmpty(data.isRecord)?null:"是否归档",Ext.isEmpty(data.isRecord)?null:"归档备注"); 
	    	  var value = new Array(data.contractCategoryTypeText,data.contractCategoryText,data.draftDate
	    	   ,data.isLegalCheck=="false"?"否":"是"
	    	  ,data.issign=="false"?"否":"是",data.signDate
	    	  ,data.isRecord=="false"?"否":"是",data.recordRemark);
	          getListDetail(label,value);
          
    	}
    	
    	
    	this.callParent([_cfg]);

    	}	
		    

});
