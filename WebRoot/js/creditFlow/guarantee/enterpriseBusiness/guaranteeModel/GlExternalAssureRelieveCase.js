Ext.ns('GlExternalAssureRelieveCase');
/**
 * @author:chencc
 * @class GlExternalAssureRelieveCase
 * @extends Ext.Panel
 * @description [GlExternalAssureRelieveCase]管理
 * @company 北京互融时代软件有限公司
 * @createtime:
 */
GlExternalAssureRelieveCase.jStore_ExternalAssureRelieveCase;
GlExternalAssureRelieveCase = Ext.extend(Ext.Panel, {
	isDisabled : false,
	isBorder : true,
	// 构造函数
	constructor : function(_cfg) {
		if(typeof(_cfg.projectId)!="undefined") {
	        this.projId=_cfg.projectId;
	        this.businessType=_cfg.businessType;
	        this.isHiddenColumn=_cfg.isHiddenColumn;
	        this.isNotHiddenColumn=!this.isHiddenColumn;
		}
		if(typeof(_cfg.isBorder)!="undefined") {
			this.isBorder = _cfg.isBorder;
		}
		Ext.applyIf(this, _cfg);
		// 初始化组件
		this.initUIComponents();
		// 调用父类构造
		GlExternalAssureRelieveCase.superclass.constructor.call(this, {
					//id : 'GlExternalAssureRelieveCase',
					border : this.isBorder,
					//id : 'GlExternalAssureRelieveCase',
					region : 'center',
					layout : 'anchor',
					items : [this.grid_ExternalAssure]
				});
	},// end of constructor
	// 初始化组件
	initUIComponents : function() {
		this.render_ReportInfo = Ext.data.Record.create([{name : 'fileid'},{name : 'filename'}, {name : 'filepath'}, {name : 'extendname'}, {name : 'filesize'}, {name : 'createtime'}, {name : 'contentType'}, {name : 'webPath'},{name : 'projId'}]);
		GlExternalAssureRelieveCase.jStore_ExternalAssureRelieveCase = new Ext.data.JsonStore({
			url : __ctxPath+ '/creditFlow/fileUploads/listFilesFileForm.do',
			totalProperty : 'totalProperty',
            root : 'topics',
            fields : this.render_ReportInfo
            
        });
		GlExternalAssureRelieveCase.jStore_ExternalAssureRelieveCase.load({
			params : {
				templettype : 1,
				mark : 'assureCommitmentLetter',
				typeisfile : 'typeisfile',
				projId : this.projId,
				businessType : this.businessType
			}
		});
		this.topBar = new Ext.Toolbar({
			items : [
				
				{
					text:'&nbsp;&nbsp;下载-对外担保承诺函模板&nbsp;&nbsp;',
					hidden : this.isHiddenColumn,
					iconCls : 'sldownIcon',
					scope:this,
					handler : GlExternalAssureRelieveCase.downloadReportTemplate
				},"-",{
					text:'&nbsp;&nbsp;上传-对外担保承诺函&nbsp;&nbsp;',
					hidden : this.isHiddenColumn,
					iconCls : 'slupIcon',
					disabled : this.isDisabled,
					scope:this,
					handler : function() {
						GlExternalAssureRelieveCase.uploadReportForThisPanel(this.projId,this.businessType,true)
					}
				},"-",{
					text:'&nbsp;&nbsp;下载-对外担保承诺函&nbsp;&nbsp;',
					scope:this,
					hidden : this.isNotHiddenColumn,
					handler : function() {
						GlExternalAssureRelieveCase.uploadReportForThisPanel(this.projId,this.businessType,false)
					}
				},"-",{
					text:'&nbsp;&nbsp;在线浏览-对外担保承诺函&nbsp;&nbsp;',
					scope:this,
					hidden : this.isNotHiddenColumn,
					handler : function() {
						GlExternalAssureRelieveCase.RunNtkOfficePanelForReport(this.projId,this.businessType,true)
					}
				}					
			
			]
		});
		
		this.grid_ExternalAssure = new HT.EditorGridPanel({
			//id : 'grid_ExternalAssure',
			border : this.isBorder,
			//width : 800,
			autoWidth : true,
			store : GlExternalAssureRelieveCase.jStore_ExternalAssureRelieveCase,
			autoExpandColumn : 'filename',
			autoScroll : true,
			//autoWidth : true,
			layout : 'anchor',
			clicksToEdit : 1,
			// anchor : fullanchor,
			viewConfig : {
				forceFit : true
			},
			tbar : this.isHidden?null : this.topBar,
			bbar : false,
			showPaging : false,
			stripeRows : true,
			plain : true,
			loadMask : true,
			autoHeight : true,
						
			columns : [{
				header : '',
				dataIndex : 'fileid',
				hidden :true,
				scope : this,
				renderer : this.isFile
			},{
				header : '文件名称(已上传)',
				width : 150,
				dataIndex : 'filename'
			}, {
				header : '大小',
				dataIndex : 'filesize',
				renderer : this.transition
			},{
				header : '类型',
				dataIndex : 'extendname'
			}, {
				header : '上传时间',
				dataIndex : 'createtime'
			}, {
				header : '下载',
				dataIndex : 'fileid',
				scope : this,
				renderer : function(val, m, r) {
					return '<a title="下载对外担保承诺函" style ="TEXT-DECORATION:underline;cursor:pointer" onclick="GlExternalAssureRelieveCase.uploadReportForThisPanel(\''+this.projId+'\',\''+this.businessType+'\','+false+')" >下载</a>';
				}
			}/*, {
				header : '在线编辑',
				dataIndex : 'fileid',
				hidden : this.isHiddenColumn,
				scope :this,
				renderer : function(val, m, r) {
					if(this.isHiddenColumn==false){
						return '<a title="在线编辑对外担保承诺函" style ="TEXT-DECORATION:underline;cursor:pointer" onclick="GlExternalAssureRelieveCase.RunNtkOfficePanelForReport(\''+this.projId+'\',\''+this.businessType+'\','+false+')" >编辑</a>';
					}else{
						return '';
					}
					
				}
			}, {
				header : '在线查看',
				dataIndex : 'fileid',
				hidden : this.isNotHiddenColumn,
				scope :this,
				renderer : function(val, m, r) {
					if(this.isNotHiddenColumn==false){
						return '<a title="在线编辑对外担保承诺函" style ="TEXT-DECORATION:underline;cursor:pointer" onclick="GlExternalAssureRelieveCase.RunNtkOfficePanelForReport(\''+this.projId+'\',\''+this.businessType+'\','+true+')" >查看</a>';
					}else{
						return '';
					}
					
				}
			}*/, {
				header : '重新上传',
				dataIndex : 'fileid',
				hidden : this.isHiddenColumn,
				scope : this,
				renderer : function(val, m, r) {
					if(this.isHiddenColumn==false){
						return '<a title="重新上传对外担保承诺函" style ="TEXT-DECORATION:underline;cursor:pointer" onclick="GlExternalAssureRelieveCase.reUploadReportForThisPanel(\''+this.projId+'\',\''+this.businessType+'\','+true+',\''+r.get('fileid')+'\')" >重新上传</a>';
					}else{
						return '';
					}
					
				}
			}, {
				header : '删除',
				dataIndex : 'fileid',
				hidden : this.isHiddenColumn,
				scope : this,
				renderer : function(val, m, r) {
					if(this.isHiddenColumn==false){
						return '<a title="删除对外担保承诺函" style ="TEXT-DECORATION:underline;cursor:pointer" onclick="GlExternalAssureRelieveCase.deleteReportFile(\''+r.get('fileid')+'\')" >删除</a>';
					}else{
						return '';
					}
					
				}
			}]
		});

		

	},// end of the initComponents()
	isFile : function(v){
		if(v!=null||v!=0||v!=''){
			this.isDisabled = true;
		}else{
			this.isDisabled = false;
		}
	},
	transition : function(v){
		return ((Math.floor(v/1024))>1024)?Math.floor((v/1024/1024))+'M':Math.floor(v/1024)+'K';
	}

});
 
GlExternalAssureRelieveCase.downIsFileJS = function(typeisfile,mark,projId,businessType){
 	Ext.Ajax.request({
		url : __ctxPath+'/creditFlow/fileUploads/AjaxGetFileForm.do',
		method : 'GET',
		success : function(response, request) {
			var obj = Ext.util.JSON.decode(response.responseText);
			if(obj.success==true){
//				window.location.href =__ctxPath+'/credit/document/DownLoadFile.do?fileid='+obj.data.fileid;
				window.open(__ctxPath+'/creditFlow/fileUploads/DownLoadFileForm.do?fileid='+obj.data.fileid,'_blank');
			}else{
				Ext.ux.Toast.msg('状态', '可能未上传对外担保承诺函');
			}
		},
		failure : function(response) {
			Ext.ux.Toast.msg('状态', '可能未上传对外担保承诺函');
		},
		params : {
			mark : mark,
			typeisfile : typeisfile,
			projId:projId,
			businessType:businessType
		}
	})
	
 };
 
GlExternalAssureRelieveCase.downloadReportTemplate = function(){
	
	var ReportTemplate="assureCommitmentLetter";//唯一标识
	var templettype = 1;
//	window.location.href =__ctxPath+'/credit/document/ajaxGetReportTemplate.do?mark='+ReportTemplate+'&templettype='+templettype;
	window.open(__ctxPath+'/contract/ajaxGetReportTemplateDocumentTemplet.do?mark='+ReportTemplate+'&templettype='+templettype,'_blank');

	Ext.Ajax.request({
	url : __ctxPath+'/contract/ajaxGetReportTemplateDocumentTemplet.do',
	method : 'GET',
	success : function(response, request) {
		var obj = Ext.util.JSON.decode(response.responseText);
		window.location.href = __ctxPath+"/"+(obj.data.webPath)
	},
	failure : function(response) {
		Ext.ux.Toast.msg('状态', '操作失败，请重试');
	},
	params : {
		mark : ReportTemplate,
		templettype : templettype
	}
})
};
GlExternalAssureRelieveCase.uploadReportForThisPanel = function(projId,businessType,displayDel){
	var reloadjStore_ExternalAssureRelieveCase = function(){GlExternalAssureRelieveCase.jStore_ExternalAssureRelieveCase.reload()};
	var ReportTemplate="assureCommitmentLetter";//调查报告模板唯一标识
	var templettype = 1;
	//var mark = "cs_document_templet";
	Ext.Ajax.request({
		url : __ctxPath+'/contract/getByOnlymarkDocumentTemplet.do',
		method : 'GET',
		success : function(response, request) {
			var obj = Ext.util.JSON.decode(response.responseText);
			if(obj.success==true){
				var olymark = "cs_document_templet."+obj.data.id;
				if(displayDel == true){
					uploadReportJS('上传/下载对外担保承诺函',"typeisfile",olymark,1,null,null,reloadjStore_ExternalAssureRelieveCase,projId,businessType);
				}else{
					GlExternalAssureRelieveCase.downIsFileJS("typeisfile",olymark,projId,businessType);
				}
			}else{
				Ext.ux.Toast.msg('状态', '未上传对外担保承诺函模板！');
			}
			
			
		},
		failure : function(response) {
			Ext.ux.Toast.msg('状态', '操作失败，请重试');
		},
		params : {
			mark : ReportTemplate,
			businessType : businessType
		}
	})
	
};

GlExternalAssureRelieveCase.reUploadReportForThisPanel = function(projId,businessType,displayDel,fileid){
	var reloadjStore_ExternalAssureRelieveCase = function(){GlExternalAssureRelieveCase.jStore_ExternalAssureRelieveCase.reload()};
	var ReportTemplate="assureCommitmentLetter";//调查报告模板唯一标识
	var templettype = 1;
	//var mark = "cs_document_templet";
	Ext.Ajax.request({
		url : __ctxPath+'/contract/getByOnlymarkDocumentTemplet.do',
		method : 'GET',
		success : function(response, request) {
			var obj = Ext.util.JSON.decode(response.responseText);
			if(obj.success==true){
				var olymark = "cs_document_templet."+obj.data.id;
				reUploadReportJS('上传/下载对外担保承诺函',"typeisfile",olymark,2,null,null,reloadjStore_ExternalAssureRelieveCase,projId,businessType,fileid);
				
			}else{
				Ext.ux.Toast.msg('状态', '未上传对外担保承诺函模板！');
			}
			
			
		},
		failure : function(response) {
			Ext.ux.Toast.msg('状态', '操作失败，请重试');
		},
		params : {
			mark : ReportTemplate,
			businessType : businessType
		}
	})
	
};

GlExternalAssureRelieveCase.deleteReportFile = function(fileid){
	Ext.Msg.confirm("提示!", '确定要删除吗？', function(btn) {
		if (btn == "yes") {
			var pbar = Ext.MessageBox.wait('数据删除中','请等待',{
				interval:200,
		    	increment:15
			});
			Ext.Ajax.request({
				url : __ctxPath+'/creditFlow/fileUploads/DeleRsFileForm.do?fileid='+fileid,
				method : 'POST',
				success : function() {
					Ext.ux.Toast.msg('状态', '删除成功!');
					pbar.getDialog().close();
					GlExternalAssureRelieveCase.jStore_ExternalAssureRelieveCase.reload();
				},
				failure : function(result, action) {
					Ext.ux.Toast.msg('状态','删除失败!');
					pbar.getDialog().close();
				}
			});
		}
	});
	
};
GlExternalAssureRelieveCase.RunNtkOfficePanelForReport = function(projId,businessType,bln){
			 	var ReportTemplate="assureCommitmentLetter";//调查报告模板唯一标识
				var templettype = 1;
				Ext.Ajax.request({
					url : __ctxPath+'/contract/getByOnlymarkDocumentTemplet.do',
					method : 'GET',
					success : function(response, request) {
						var obj = Ext.util.JSON.decode(response.responseText);
						if(obj.success==true){
							var olymark = "cs_document_templet."+obj.data.id;
							Ext.Ajax.request({
								url : __ctxPath+'/creditFlow/fileUploads/getFileAttachFileForm.do',
								method : 'GET',
								success : function(response, request) {
									var objfile = Ext.util.JSON.decode(response.responseText);
									if(objfile.success==true){
										var file_id =objfile.fileId;
										new OfficeTemplateView(file_id,null,bln,null);
									}else{
										Ext.ux.Toast.msg('提示', '可能还未上传对外担保承诺函！');
									}
									
								},
								failure : function(response) {
									Ext.ux.Toast.msg('提示', '操作失败，请重试');
								},
								params : {
									mark : olymark,
									typeisfile : "typeisfile",
									projId : projId,
									businessType :businessType
								}
							});
							
						}else{
							Ext.ux.Toast.msg('提示', '未上传对外担保承诺函模板！');
						}
						
					},
					failure : function(response) {
						Ext.ux.Toast.msg('状态', '操作失败，请重试');
					},
					params : {
						mark : ReportTemplate,
						businessType : businessType
					}
				});
			}


