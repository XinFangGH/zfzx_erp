Ext.ns('SlReportView');
/**
 * @author:chencc
 * @class SlContractQSView
 * @extends Ext.Panel
 * @description [SlReportView]管理
 * @company 广州宏天软件有限公司
 * @createtime:
 */
//SlReportView.jStore_Report;
ApproveTableView = Ext.extend(Ext.Panel, {
	isHidden_report : true,
	isHiddenAffrim_report : true,
	isgdEdit : false,
	isDisabled : false,
	// 构造函数
	constructor : function(_cfg) {
		if(typeof(_cfg.projectId)!="undefined") {
	        this.projId=_cfg.projectId;
		}
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
		Ext.applyIf(this, _cfg);
		// 初始化组件
		this.initUIComponents();
		// 调用父类构造
		 ApproveTableView.superclass.constructor.call(this, {
					region : 'center',
					layout : 'anchor',
				    title:false,
					items : [this.grid_ReportPanel]
				});
	},// end of constructor
	// 初始化组件
	initUIComponents : function() {
		downReportFileJS = function(fileid){
			window.open(__ctxPath+'/creditFlow/fileUploads/DownLoadFileForm.do?fileid='+fileid,'_blank');
		 };
		downIsFileJS = function(typeisfile,mark,projId,businessType){
		 	Ext.Ajax.request({
				url : __ctxPath+'/creditFlow/fileUploads/AjaxGetFileForm.do',
				method : 'GET',
				success : function(response, request) {
					var obj = Ext.util.JSON.decode(response.responseText);
					if(obj.success==true){
		//				window.location.href =__ctxPath+'/credit/document/DownLoadFile.do?fileid='+obj.data.fileid;
						window.open(__ctxPath+'/creditFlow/fileUploads/DownLoadFileForm.do?fileid='+obj.data.fileid,'_blank');
					}else{
						Ext.ux.Toast.msg('状态', '可能未上传报告文件！');
					}
				},
				failure : function(response) {
					Ext.ux.Toast.msg('状态', '可能未上传报告文件！');
				},
				params : {
					mark : mark,
					typeisfile : typeisfile,
					projId:projId,
					businessType:businessType
				}
			})
			
		 };
		RunNtkOfficePanelForReport = function(projId,businessType,bln,ReportTemplate,templettype){
			Ext.Ajax.request({
				url : __ctxPath+'/contract/getByOnlymarkForUploadDocumentTemplet.do',
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
									if(objfile.ext=="pdf"){
									   new PdfTemplateView(file_id,objfile.filePath,bln,null)
									}else{
										new OfficeTemplateView(file_id,null,bln,null);
									}
								}else{
									Ext.ux.Toast.msg('状态', '可能还未上传调查报告！');
								}
								
							},
							failure : function(response) {
								Ext.ux.Toast.msg('状态', '操作失败，请重试！');
							},
							params : {
								mark : olymark,
								typeisfile : "typeisfile",
								projId : projId,
								businessType :businessType
							}
						});
						
					}else{
						Ext.ux.Toast.msg('状态', '可能还未上传调查报告模板！');
					}
					
				},
				failure : function(response) {
					Ext.ux.Toast.msg('状态', '操作失败，请重试！');
				},
				params : {
					mark : ReportTemplate,
					businessType : businessType
				}
			});
		};
		 
		
		this.render_ReportInfo = Ext.data.Record.create([{name : 'fileid'},{name : 'filename'}, {name : 'filepath'}, {name : 'extendname'}, {name : 'filesize'}, {name : 'createtime'}, {name : 'contentType'}, {name : 'webPath'},{name : 'projId'},{name : 'isArchiveConfirm'},{name : 'archiveConfirmRemark'}]);
		this.jStore_Report = new Ext.data.JsonStore({
			url : __ctxPath+ '/creditFlow/fileUploads/listFilesFileForm.do',
			totalProperty : 'totalProperty',
            root : 'topics',
            fields : this.render_ReportInfo
            
        });
		this.jStore_Report.load({
			params : {
				templettype : this.templettype,
				mark : this.ReportTemplate,
				typeisfile : 'typeisfile',
				projId : this.projId,
				businessType : this.businessType
			}
		});
		this.topBar = new Ext.Toolbar({
			items : [
				{
					iconCls : 'sldownIcon',
					text:'生成审批表',
					scope:this,
					handler : function(){
				//		this.downloadReportTemplate(this.ReportTemplate,this.businessType)
					}
				}
			]
		});
		var checkColumn = new Ext.grid.CheckColumn({
			hidden : this.isHiddenAffrim_report,
			editable : this.isgdEdit,
			header : '是否归档',
			dataIndex : 'isArchiveConfirm',
			fixed : true,
			width : 74
		});
		this.grid_ReportPanel = new HT.EditorGridPanel({
			plugins : [checkColumn],
			hiddenCm : this.isHidden_report,
			border : false,
			autoWidth : true,
			store : this.jStore_Report,
			autoExpandColumn : 'filename',
			autoScroll : true,
			tbar : this.isHidden_report?null : this.topBar,
			isShowTbar : this.isHidden_report? false : true,
			layout : 'anchor',
			clicksToEdit : 1,
			viewConfig : {
				forceFit : true
			},
			bbar : false,
			showPaging : false,
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
				menuDisabled:true,
				dataIndex : 'filename'
			}, {
				header : '大小',
				fixed : true,
				width : 40,
				menuDisabled:true,
				dataIndex : 'filesize',
				renderer : this.transition
			},{
				header : '类型',
				fixed : true,
				width : 40,
				menuDisabled:true,
				dataIndex : 'extendname'
			}, {
				header : '下载',
				fixed : true,
				width : 40,
				dataIndex : 'fileid',
				menuDisabled:true,
				scope : this,
				renderer : function(val, m, r) {
//					return '<a title="下载调查报告" style ="TEXT-DECORATION:underline;cursor:pointer" onclick="SlReportView.uploadReportForThisPanel(\''+this.projId+'\',\''+this.businessType+'\','+false+',\''+this.ReportTemplate+'\','+this.templettype+')" >下载</a>';
					return '<a title="下载调查报告" style ="TEXT-DECORATION:underline;cursor:pointer" onclick="downReportFileJS('+r.get('fileid')+')" >下载</a>';
				}
			},checkColumn,{
				header : '归档备注',
				dataIndex : 'archiveConfirmRemark',
				hidden : this.isHiddenAffrim_report,
				editable : this.isgdEdit,
				align : "center",
				width : 100,
				menuDisabled:true,
				/*renderer : function(v){
					if(v == "" || v == null) {
						return "<font color=red>点击编辑备注</font>";
					}else{
						return v;
					}
				},*/
				editor : new Ext.form.TextField({
					selectOnFocus: true
				})
			}],
			listeners : {
				scope : this,
				afteredit : function(e) {
					var args;
					var value = e.value;
					var id = e.record.data['fileid'];
					var grid=this.grid_ReportPanel
					if (e.originalValue != e.value) {//编辑行数据发生改变
						if(e.field == 'isArchiveConfirm') {
							args = {'fileinfo.isArchiveConfirm': value,'fileinfo.fileid': id};
						}
						if(e.field == 'archiveConfirmRemark') {
							args = {'fileinfo.archiveConfirmRemark': value,'fileinfo.fileid': id};
						}
						Ext.Ajax.request({
							url : __ctxPath+'/creditFlow/fileUploads/ajaxArchiveConfirmFileForm.do',
							method : 'POST',
							scope : this,
							success : function(response, request) {
								//grid.getStore().reload()
								e.record.commit();
							},
							failure : function(response) {
								Ext.ux.Toast.msg('状态', '操作失败，请重试！');
							},
							params : args
						})
					}
				},
				'cellclick' : function(grid,row,col,e){
					var fileid = grid.getStore().getAt(row).get("fileid");
					if(col == 13){
						this.deleteReportFile(fileid,grid);
					}else if(col == 12){
						this.reUploadReportForThisPanel(this.projId,this.businessType,true,fileid,this.ReportTemplate,this.templettype,grid,this.setname)
					}
				}
			}
		});

		

	},// end of the initComponents()
	reportAffrimRenderer : function(v){
		return v == true?'<font color=green>是</font>' : '<font color=red>否</font>';
	},
	isFile : function(v){
		if(v!=null||v!=0||v!=''){
			this.isDisabled = true;
		}else{
			this.isDisabled = false;
		}
	},
	transition : function(v){
		var size = "";
		if(Math.floor(v/1024)>1024){
			size = Math.floor((v/1024/1024))+'M';
		}else if(Math.floor(v)<1024){
			size = Math.floor(v)+'B';
		}else if(Math.floor(v/1024)<1024){
			size = Math.floor(v/1024)+'K'
		}else{
			size = '未知';
		}
		return size;
		//return ((Math.floor(v/1024))>1024)?Math.floor((v/1024/1024))+'M':Math.floor(v/1024)+'K';
	},
	deleteReportFile : function(fileid,grid){
			Ext.Msg.confirm("提示!", '确定要删除吗？', function(btn) {
				if (btn == "yes") {
					var pbar = Ext.MessageBox.wait('数据删除中','请等待',{
						interval:200,
				    	increment:15
					});
					Ext.Ajax.request({
						url : __ctxPath+'/creditFlow/fileUploads/DeleRsFileForm.do?fileid='+fileid,
						method : 'POST',
						scope : this,
						success : function() {
							Ext.ux.Toast.msg('状态', '删除成功！');
							pbar.getDialog().close();
							grid.getStore().reload();
						},
						failure : function(result, action) {
							Ext.ux.Toast.msg('状态', '删除失败，请重试！');
							pbar.getDialog().close();
						}
					});
				}
			});
		
	},
	uploadReportForThisPanel : function(projId,businessType,displayDel,ReportTemplate,templettype,panel,setname){
		var reloadjStore_Report = function(){panel.getStore().reload()};
		//var mark = "cs_document_templet";
		Ext.Ajax.request({
			url : __ctxPath+'/contract/getByOnlymarkForUploadDocumentTemplet.do',
			method : 'GET',
			success : function(response, request) {
				var obj = Ext.util.JSON.decode(response.responseText);
				if(obj.success==true){
					var olymark = "cs_document_templet."+obj.data.id;
					if(displayDel == true){
						uploadReportJS('上传/下载调查报告',"typeisfile",olymark,1,null,null,reloadjStore_Report,projId,businessType,setname);
					}else{
						downIsFileJS("typeisfile",olymark,projId,businessType);
					}
				}else{
					Ext.ux.Toast.msg('状态', '未上传调查报告模板！');
				}
				
				
			},
			failure : function(response) {
				Ext.ux.Toast.msg('状态', '操作失败，请重试！');
			},
			params : {
				mark : ReportTemplate,
				businessType : businessType
			}
		})
		
	},
	reUploadReportForThisPanel : function(projId,businessType,displayDel,fileid,ReportTemplate,templettype,panel,setname){
		var reloadjStore_Report = function(){panel.getStore().reload()};
		//var mark = "cs_document_templet";
		Ext.Ajax.request({
			url : __ctxPath+'/contract/getByOnlymarkForUploadDocumentTemplet.do',
			method : 'GET',
			success : function(response, request) {
				var obj = Ext.util.JSON.decode(response.responseText);
				if(obj.success==true){
					var olymark = "cs_document_templet."+obj.data.id;
					reUploadReportJS('上传/下载调查报告',"typeisfile",olymark,2,null,null,reloadjStore_Report,projId,businessType,fileid,setname);
					
				}else{
					Ext.ux.Toast.msg('状态', '未上传调查报告模板！');
				}
				
				
			},
			failure : function(response) {
				Ext.ux.Toast.msg('状态', '操作失败，请重试！');
			},
			params : {
				mark : ReportTemplate,
				businessType : businessType
			}
		})
		
	},
	downloadReportTemplate : function(ReportTemplate,businessType){
		//var mark = "cs_document_templet";
		Ext.Ajax.request({
			url : __ctxPath+'/contract/getByOnlymarkDocumentTemplet.do',
			method : 'GET',
			success : function(response, request) {
				var obj = Ext.util.JSON.decode(response.responseText);
				if(obj.success==true){
					window.open(__ctxPath+'/contract/ajaxGetReportTemplateDocumentTemplet.do?mark='+ReportTemplate+'&businessType='+businessType,'_blank');
				}else{
					Ext.ux.Toast.msg('状态', '未上传调查报告模板！');
				}
				
				
			},
			failure : function(response) {
				Ext.ux.Toast.msg('状态', '操作失败，请重试！');
			},
			params : {
				mark : ReportTemplate,
				businessType : businessType
			}
		})
		//	window.location.href =__ctxPath+'/credit/document/ajaxGetReportTemplate.do?mark='+ReportTemplate+'&templettype='+templettype;
			//window.open(__ctxPath+'/credit/document/ajaxGetReportTemplate.do?mark='+ReportTemplate+'&businessType='+businessType,'_blank');
	}
	

})







