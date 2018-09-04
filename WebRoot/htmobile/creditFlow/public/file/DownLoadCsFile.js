
//creditorList.js
Ext.define('htmobile.creditFlow.public.file.DownLoadCsFile', {
    extend: 'mobile.List',
    
    name: 'DownLoadCsFile',

    constructor: function (_cfg) {
		this.mark=_cfg.mark;
	    this.typeisfile=_cfg.typeisfile;
	    this.title=_cfg.title;
	    var params={};
	    if(this.typeisfile==null){
	    	 params={ mark : _cfg.mark};
	    
	    }else{
	    params={
						mark : _cfg.mark,
						typeisfile:_cfg.typeisfile
			    };
	    }
    	var panel=Ext.create('tableHeader',{style:"margin-left:10px;",header:new Array("<span class='tableheadtwo' >文件名称(已上传)</span>",
    	                                                      "<span class='tableheadtwo' >下载</span>")});
    	Ext.apply(_cfg,{
    		modeltype:"DownLoadCsFile",
    		flex:1,
    		width:"100%",
		    height:"100%",
    		title:this.title,
   // 		items:[panel],
    		fields:[{
			name : 'fileid'
		},{
			name : 'recordid'
		},{
			name : 'setname'
		},{
			name : 'filename'
		},{
			name : 'filepath'
		},{
			name : 'extendname'
		},{
			name : 'filesize'
		},{
			name : 'creatorid'
		},{
			name : 'createtime'
		},{
			name : 'remark'
		},{
			name : 'modename'
		}],
    	        url : __ctxPath +'/creditFlow/fileUploads/getUploadedFileListFileForm.do',
    	          params : params,
	    		root:'topics',
	    	    totalProperty: 'totalCounts',
 		        itemTpl: new Ext.XTemplate( "<span  class='tablelistone' style='width:70%;'>{filename}</span>&nbsp;&nbsp;" +
 		        "<span   class='tablelist' style='width:20%;color:#a7573b;text-decoration:underline' onclick=\"javascript:downLoadFile('{filepath:this.filepathFormat}')\">下载</span>" +
 		        "<span  class='tablelist'  style='width:95%;'>大小{filesize/1024}KB&nbsp;&nbsp;扩展名{extendname}&nbsp;&nbsp;上传时间{createtime}</span>&nbsp;&nbsp;" 
		    		
		    		,{
		    		filepathFormat: function(filepath) {
		    			 	var reg = /\\/g;
                            var  filepath=filepath.replace(reg,"/");
                            return filepath;
						}  
		    		})
		    		
    	});
    	
    	this.callParent([_cfg]);

    }
});
