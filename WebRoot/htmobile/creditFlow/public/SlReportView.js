
//creditorList.js
Ext.define('htmobile.creditFlow.public.SlReportView', {
    extend: 'mobile.List',
    
    name: 'SlReportView',

    constructor: function (_cfg) {
	if(typeof(_cfg.projectId)!="undefined") {
	        this.projId=_cfg.projectId;
		}
		if(typeof(_cfg.hiddenDownLoad)!='undefined'){
			this.hiddenDownLoad = _cfg.hiddenDownLoad;
		}
		if(typeof(_cfg.hiddenUpLoad)!='undefined'){
			this.hiddenUpLoad= _cfg.hiddenUpLoad;
		}
		Ext.applyIf(this, _cfg);
		if(typeof(_cfg.businessType)!="undefined") {
	        this.businessType=_cfg.businessType;
			if(this.businessType == 'SmallLoan'){//业务类别，小贷
				this.ReportTemplate="ReportTemplate";//调查报告模板唯一标识
				this.templettype = 5;
				this.titleText='贷款尽职调查报告';
				this.setname = '贷款尽职调查报告';
			}else if(this.businessType == 'Guarantee'){//业务类别，企业贷
				this.ReportTemplate="GuaranteeReport";//调查报告模板唯一标识
				this.templettype = 11;
				this.titleText='担保调查报告';
				this.setname = '担保调查报告';
			}else if(this.businessType == 'Pawn'){
				this.ReportTemplate="PreAssessmentReport";//调查报告模板唯一标识
				this.templettype = 11;
				this.titleText='预评估报告';
				this.setname = '预评估报告';
			}else if(this.businessType == 'LeaseFinance' && typeof(this.ReportTemplate)=="undefined"){//融资租赁 尽职调查报告   默认
				this.ReportTemplate="LeaseFinanceReport";//调查报告模板唯一标识
				this.templettype = 11;
				this.titleText='融资租赁调查报告';
				this.setname = '融资租赁调查报告';
			}else if(this.businessType == 'LeaseFinance'&&this.ReportTemplate=="LeaseFinanceRiskReport"){   //融资租赁 风险分析报告材料
				//this.ReportTemplate="LeaseFinanceRiskReport";//调查报告模板唯一标识
				this.templettype = 11;
				this.titleText='融资租赁风险分析报告';
				this.setname = '融资租赁风险分析报告';
			}else if(this.businessType == 'LeaseFinance'&&this.ReportTemplate=="LeaseFinanceArgumentReport"){//融资租赁 讨论会会议纪要报告材料
				//this.ReportTemplate="LeaseFinanceRiskReport";//调查报告模板唯一标识
				this.templettype = 11;
				this.titleText='融资租赁讨论会会议纪要';
				this.setname = '融资租赁讨论会会议纪要';
			}else if(this.businessType == 'LeaseFinance'&&this.ReportTemplate=="LeaseFinanceManageFileReport"){//融资租赁 办理文件材料
				//this.ReportTemplate="LeaseFinanceRiskReport";//调查报告模板唯一标识
				this.templettype = 11;
				this.titleText='融资租赁办理文件材料';
				this.setname = '融资租赁办理文件材料';
			}else if(this.businessType == 'LeaseFinance'&&this.ReportTemplate=="LeaseFinanceOtherFilesReport"){//融资租赁 其他归档材料
				//this.ReportTemplate="LeaseFinanceRiskReport";//调查报告模板唯一标识
				this.templettype = 11;
				this.titleText='融资租赁其他归档材料';
				this.setname = '融资租赁其他归档材料';
			}
			if(this.businessType == 'SmallLoan' && this.Template=="AssessReport"){
				this.ReportTemplate="AssessReport"
				this.templettype = 11;
				this.titleText='评估报告';
				this.setname = '评估报告';
			}else if(this.businessType == 'SmallLoan' && this.Template=="IndeedCreditReport"){
				this.ReportTemplate="IndeedCreditReport"
				this.templettype = 11;
				this.titleText='实地征信报告';
				this.setname = '实地征信报告';
			}else if(this.businessType == 'SmallLoan' && this.Template=="FirstCheckReport"){
				this.ReportTemplate="FirstCheckReport"
				this.templettype = 11;
				this.titleText='客服录件报告';
				this.setname = '客服录件报告';
			}else if(this.businessType == 'SmallLoan' && this.Template=="OnceCheckReport"){
				this.ReportTemplate="OnceCheckReport"
				this.templettype = 11;
				this.titleText='初审报告';
				this.setname = '初审报告';
			}else if(this.businessType == 'SmallLoan' && this.Template=="LastCheckReport"){
				this.ReportTemplate="LastCheckReport"
				this.templettype = 11;
				this.titleText='终审报告';
				this.setname = '终审报告';
			}else if(this.businessType == 'SmallLoan' && this.Template=="LoanMoneyNotice"){
				this.ReportTemplate="LoanMoneyNotice"
				this.templettype = 11;
				this.titleText='上传划款凭证';
				this.setname = '上传划款凭证';
				
			}
		
		}
		if (typeof(_cfg.isHidden_report) != 'undefined') {
		   this.isHidden_report = _cfg.isHidden_report;
	    }
	    if(typeof(_cfg.isHiddenAffrim_report) != "undefined") {
	    	this.isHiddenAffrim_report = _cfg.isHiddenAffrim_report;
	    }
	    if(typeof(_cfg.isgdEdit) != "undefined") {
	    	this.isgdEdit = _cfg.isgdEdit;
	    }
    	var panel=Ext.create('tableHeader',{style:"margin-left:10px;",header:new Array("<span class='tableheadtwo' >文件名称(已上传)</span>"
    	                                                    )});
    	Ext.apply(_cfg,{
    		modeltype:"CreditorList",
    		flex:1,
    		title:this.titleText,
    		items:[panel],
    		fields:[{name : 'fileid'},{name : 'filename'}, {name : 'filepath'}, {name : 'extendname'}, {name : 'filesize'}, {name : 'createtime'}, {name : 'contentType'}, {name : 'webPath'},{name : 'projId'},{name : 'isArchiveConfirm'},{name : 'archiveConfirmRemark'}],
    	        url : __ctxPath+ '/creditFlow/fileUploads/listFilesFileForm.do',
	    		root:'topics',
	    	    totalProperty: 'totalProperty',
	    	    params : {
						templettype : this.templettype,
						mark : this.ReportTemplate,
						typeisfile : 'typeisfile',
						projId : this.projId,
						businessType : this.businessType
			},
		    itemTpl: new Ext.XTemplate(
		    		"<span  class='tablelistone' style='width:70%;'>{filename}</span>&nbsp;&nbsp;" +
 		        "<span   class='tablelist' style='width:20%;color:#a7573b;text-decoration:underline' onclick=\"javascript:downLoadFile('{filepath:this.filepathFormat}')\">下载</span>" +
 		        "<span  class='tablelist'  style='width:95%;'>大小{filesize/1024}KB&nbsp;&nbsp;扩展名{extendname}&nbsp;&nbsp;上传时间{createtime}</span>&nbsp;&nbsp;" 
		    		,{
		    		filepathFormat: function(filepath) {
		    			 	var reg = /\\/g;
                            var  filepath=filepath.replace(reg,"/");
                            return filepath;
						}  
		    		}),
		    		
 	        listeners : {
				itemsingletap : this.itemsingletap
			}
    	});

    	this.callParent([_cfg]);
    	

    },
	itemsingletap : function(obj, index, target, record) {
    	  var data=record.data;
    	  var label = new Array("文件名称(已上传)","大小","类型","上传时间"
    	); 
    	  var value = new Array(data.filename,data.filesize,data.extendname,data.createtime
    	 );  
          getListDetail(label,value);
		    

}
});
