
//creditorList.js
Ext.define('htmobile.creditFlow.public.main.SlRiskReportView', {
    extend: 'mobile.List',
    
    name: 'SlRiskReportView',

    constructor: function (_cfg) {
      if(typeof(_cfg.projectId)!="undefined"){
			this.projId=_cfg.projectId;
		}
		if(typeof(_cfg.businessType)!="undefined"){
			this.businessType=_cfg.businessType;
			if(this.businessType == 'SmallLoan'){//业务种类，小贷
				this.RiskReportTemplate="RiskReportTemplate";//报告模板唯一标识
				this.templettype = 5;
				this.titleText='风险综合分析报告';
				this.setname = '风险综合分析报告';
			}else if(this.businessType == 'Guarantee'){//业务种类，企业贷
				this.RiskReportTemplate="GuaranteeRiskReport";//报告模板唯一标识
				this.templettype = 11;
				this.titleText='风险综合分析报告';
				this.setname = '风险综合分析报告';
			}
		}
    	var panel=Ext.create('tableHeader',{style:"margin-left:10px;",header:new Array("<span class='tableheadtwo' >文件名称(已上传)</span>"
    	                                                    )});
    	Ext.apply(_cfg,{
    		modeltype:"SlRiskReportView",
    		flex:1,
    		title:"<div style='font-size:15px;'>"+this.titleText+"</div>",
    		items:[panel],
    		fields:[{name : 'fileid'},{name : 'filename'}, {name : 'filepath'}, {name : 'extendname'}, {name : 'filesize'}, {name : 'createtime'}, {name : 'contentType'}, {name : 'webPath'},{name : 'projId'},{name : 'isArchiveConfirm'},{name : 'archiveConfirmRemark'}],
    	       	url : __ctxPath+ '/creditFlow/fileUploads/listFilesFileForm.do',
	    		root:'topics',
	    	    totalProperty: 'totalProperty',
	    	    params : {
						templettype : this.templettype,
						mark : this.RiskReportTemplate,
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
		    		})/*,
		    		
 	        listeners : {
				itemsingletap : this.itemsingletap
			}*/
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
