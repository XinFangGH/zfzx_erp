/**
 * 初审报告
 */
Ext.define('htmobile.creditFlow.public.main.PreliminaryReportList', {
    extend: 'mobile.List',
    name: 'PreliminaryReportList',
    constructor: function (_cfg) {
    	Ext.applyIf(this, _cfg);
    	Ext.apply(_cfg,{
    		style:'background-color:white;',
    		modeltype:"PreliminaryReportList",
    		flex:1,
    		width:"100%",
		    height:"100%",
    		title:"初审报告",
    		items:[{
    		xtype:'panel',
    		docked:'top',
    		items:[{
    			html:`	
    					<div class="list-column">
	    					<span>文件名称</span>
	    					<span>大小</span>
	    					<span>类型</span>
    					</div>
    				`
    		}]
    	}],
    		fields : [{name : 'fileid'},{name : 'filename'}, {name : 'filepath'}, {name : 'extendname'}, {name : 'filesize'}, {name : 'createtime'}, {name : 'contentType'}, {name : 'webPath'},{name : 'projId'},{name : 'isArchiveConfirm'},{name : 'archiveConfirmRemark'}],
    	        url : __ctxPath+ '/creditFlow/fileUploads/listFilesFileForm.do',
    	        params:{
    	        	templettype : this.Template,
					mark : this.ReportTemplate,
					typeisfile : 'typeisfile',
					projId : this.projectId,
					businessType : this.businessType
    	        },
	    		root:'result',
	    	    totalProperty: 'totalCounts',
		        itemTpl: `
 		        	 	<div class="list-column-content">
	    					<span>{filename}</span>
	    					<span>{filesize}</span>
	    					<span>{extendname}</span>
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
