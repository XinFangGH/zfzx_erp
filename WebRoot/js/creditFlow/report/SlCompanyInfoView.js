Ext.ns('SlCompanyInfoView');
/**
 * @author:chencc
 * @class SlContractQSView
 * @extends Ext.Panel
 * @description [SlCompanyInfoView]管理
 * @company 北京互融时代软件有限公司
 * @createtime:
 */
//SlCompanyInfoView.jStore_RiskReport;
SlCompanyInfoView = Ext.extend(Ext.Panel, {
	isHidden_riskReport : true,
	isHiddenAffrim_riskReport : true,
	isgdEdit_riskReport : false,
	isHiddenPanel : false,
	// 构造函数
	constructor : function(_cfg) {
		if(typeof(_cfg.projectId)!="undefined"){
			this.projId=_cfg.projectId;
		}
		if(typeof(_cfg.businessType)!="undefined"){
			this.businessType=_cfg.businessType;
			
			if(this.businessType == 'CompanyInfo'){//业务种类，小贷
				this.RiskReportTemplate="companyInfo";//报告模板唯一标识
				this.templettype = 5;
				this.titleText='公司材料';
				this.setname = '公司材料';
			}else if(this.businessType == 'Guarantee'){//业务种类，企业贷
				this.RiskReportTemplate="GuaranteeRiskReport";//报告模板唯一标识
				this.templettype = 11;
				this.titleText='公司材料';
				this.setname = '公司材料';
			}
		}
		if(typeof(_cfg.isHiddenColumn)!="undefined"){
			this.isHiddenColumn=_cfg.isHiddenColumn;
			this.isNotHiddenColumn=!this.isHiddenColumn;
		}
		if (typeof(_cfg.isHidden_riskReport) != 'undefined') {
		   this.isHidden_riskReport = _cfg.isHidden_riskReport;
	    }
	    if(typeof(_cfg.isHiddenAffrim_riskReport) != "undefined") {
	    	this.isHiddenAffrim_riskReport = _cfg.isHiddenAffrim_riskReport;
	    }
	    if(typeof(_cfg.isgdEdit_riskReport) != "undefined") {
	    	this.isgdEdit_riskReport = _cfg.isgdEdit_riskReport;
	    }
	    if(_cfg.isHiddenPanel) {
			this.isHiddenPanel = _cfg.isHiddenPanel;
		}
		Ext.applyIf(this, _cfg);
		// 初始化组件
		this.initUIComponents();
		// 调用父类构造
		SlCompanyInfoView.superclass.constructor.call(this, {
					hidden : this.isHiddenPanel,
					region : 'center',
					layout : 'anchor',
					items : [this.grid_RiskReportPanel]
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
						Ext.ux.Toast.msg('状态', '可能未上传公司材料！');
					}
				},
				failure : function(response) {
					Ext.ux.Toast.msg('状态', '可能未上传公司材料！');
				},
				params : {
					mark : mark,
					typeisfile : typeisfile,
					projId:projId,
					businessType:businessType
				}
			})
			
		 };
		RunNtkOfficePanelForRiskReport = function(projId,businessType,bln,RiskReportTemplate,templettype){
			Ext.Ajax.request({
				url : __ctxPath+'/contract/getByOnlymarkForUploadDocumentTemplet.do',
				method : 'GET',
				success : function(response, request) {
					var objRisk = Ext.util.JSON.decode(response.responseText);
					if(objRisk.success==true){
						var olymarkRisk = "cs_document_companyInfotemplet."+projId;
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
									Ext.ux.Toast.msg('提示', '可能还未上传公司材料！');
								}
								
							},
							failure : function(response) {
								Ext.ux.Toast.msg('状态', '操作失败，请重试');
							},
							params : {
								mark : olymarkRisk,
								typeisfile : "typeisfile",
								projId : projId,
								businessType:businessType
							}
						});
					}else{
						Ext.ux.Toast.msg('状态', '未上传公司材料！');
					}
					
				},
				failure : function(response) {
					Ext.ux.Toast.msg('状态', '操作失败，请重试');
				},
				params : {
					mark : RiskReportTemplate,
					businessType : businessType
				}
			});
		};		
		this.render_RiskReportInfo = Ext.data.Record.create([{name : 'fileid'},{name : 'filename'}, {name : 'filepath'}, {name : 'extendname'}, {name : 'filesize'}, {name : 'createtime'}, {name : 'contentType'}, {name : 'webPath'},{name : 'projId'},{name : 'isArchiveConfirm'},{name : 'archiveConfirmRemark'}]);
	
		this.jStore_RiskReport = new Ext.data.JsonStore({
			
			url : __ctxPath+ '/creditFlow/fileUploads/listFilesFileForm.do',
			totalProperty : 'totalProperty',
            root : 'topics',
            fields : this.render_RiskReportInfo
            
        });
		this.jStore_RiskReport.load({
			params : {
				templettype : this.templettype,
				mark : this.RiskReportTemplate,
				typeisfile : 'typeisfile',
				projId : this.projId,
				businessType : this.businessType
			}
		});
		this.topBar = new Ext.Toolbar({
			items : [
				{
					iconCls : 'sldownIcon',
					text:'下载模板',
					scope:this,
					hidden:true,
					handler : function(){
						this.downloadRiskReportTemplate(this.RiskReportTemplate,this.businessType)
					}
				},'-',{
					text:'上传',
					iconCls : 'slupIcon',
					scope:this,
					handler : function() {
						if(this.projId==0){
				Ext.ux.Toast.msg('状态', '请先保存企业信息，然后再上传！');
			}else{
						this.uploadRiskReportForThisPanel(this.projId,this.businessType,true,this.RiskReportTemplate,this.templettype,this.grid_RiskReportPanel,this.setname)
			}
					}
				}
			]
		});
		var checkColumn = new Ext.grid.CheckColumn({
			hidden : this.isHiddenAffrim_riskReport,
			editable : this.isgdEdit_riskReport,
			header : '是否归档',
			dataIndex : 'isArchiveConfirm',
			fixed : true,
			width : 74
		});
		this.grid_RiskReportPanel = new HT.EditorGridPanel({
			plugins : [checkColumn],
			hiddenCm : this.isHidden_riskReport,
			border : false,
			autoWidth : true,
			tbar : this.isHidden_riskReport?null : this.topBar,
			isShowTbar : this.isHidden_riskReport? false : true,
			store : this.jStore_RiskReport,
			autoExpandColumn : 'filename',
			autoScroll : true,
			layout : 'anchor',
			clicksToEdit : 1,
			viewConfig : {
				forceFit : true
			},
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
				menuDisabled:true,
				dataIndex : 'filename'
			}, {
				header : '大小',
				dataIndex : 'filesize',
				fixed : true,
				width : 40,
				menuDisabled:true,
				renderer : this.transition
			},{
				header : '类型',
				menuDisabled:true,
				fixed : true,
				width : 40,
				dataIndex : 'extendname'
			}, {
				header : '上传时间',
				menuDisabled:true,
				fixed : true,
				width : 77,
				dataIndex : 'createtime'
			}, {
				header : '下载',
				dataIndex : 'fileid',
				fixed : true,
				width : 40,
				menuDisabled:true,
				scope : this,
				renderer : function(val, m, r) {
//					return '<a title="下载综合分析报告" style ="TEXT-DECORATION:underline;cursor:pointer" onclick="SlCompanyInfoView.uploadRiskReportForThisPanel(\''+this.projId+'\',\''+this.businessType+'\','+false+',\''+this.RiskReportTemplate+'\','+this.templettype+')" >下载</a>';
					return '<a title="下载综合分析报告" style ="TEXT-DECORATION:underline;cursor:pointer" onclick="downReportFileJS('+r.get('fileid')+')" >下载</a>';
				}
			}/*, {
				header : '在线编辑',
				dataIndex : 'fileid',
				fixed : true,
				width : 64,
				menuDisabled:true,
				hidden : this.isHidden_riskReport,
				scope :this,
				renderer : function(val, m, r) {
					return '<a title="在线编辑公司材料" style ="TEXT-DECORATION:underline;cursor:pointer" onclick="RunNtkOfficePanelForRiskReport(\''+this.projId+'\',\''+this.businessType+'\','+false+',\''+this.RiskReportTemplate+'\','+this.templettype+')" >编辑</a>';
				}
			}*/,{
									header : '预览',
									fixed : true,
									width : 60,
									dataIndex:'viewPic',
									hidden : this.isHidden_riskReport,
									renderer:function(){
									return "<img src='"+__ctxPath+"/images/btn/read_document.png'>";
									}
								},
								checkColumn, {
				header : '归档备注',
				dataIndex : 'archiveConfirmRemark',
				menuDisabled:true,
				width : 100,
				align : "center",
				hidden : this.isHiddenAffrim_riskReport,
				editable : this.isgdEdit_riskReport,
				/*renderer : function(v){
					if(v == "" || v == null) {
						return "<font color = red>点击编辑备注</font>";
					}else {
						return v;
					}
				},*/
				editor : new Ext.form.TextField({
					selectOnFocus: true
				})
			},{
				header : '重新上传',
				dataIndex : 'fileid',
				fixed : true,
				width : 64,
				hidden : this.isHidden_riskReport,
				menuDisabled:true,
				scope : this,
				renderer : function(val, m, r) {
					return '<a title="重新上传公司材料" style ="TEXT-DECORATION:underline;cursor:pointer">重新上传</a>';
				}
			}, {
				header : '删除',
				dataIndex : 'fileid',
				fixed : true,
				width : 40,
				hidden : this.isHidden_riskReport,
				menuDisabled:true,
				renderer : function(val, m, r) {
					return '<a title="删除公司材料" style ="TEXT-DECORATION:underline;cursor:pointer">删除</a>';
				}
			}],
			listeners : {
				scope : this,
				afteredit : function(e) {
					var args;
					var value = e.value;
					var id = e.record.data['fileid'];
					var grid=this.grid_RiskReportPanel
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
								grid.getStore().reload()
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
					 var fieldName = grid.getColumnModel().getDataIndex(col); //Get field name
					var extendname = grid.getStore().getAt(row).get("extendname");
					var src = grid.getStore().getAt(row).get("webPath");
					if(col == 13){
						this.deleteRiskReportFile(fileid,grid);
					}else if(col == 12){
						this.reUploadRiskReportForThisPanel(this.projId,this.businessType,true,fileid,this.RiskReportTemplate,this.templettype,grid,this.setname)
					}
					
					if("viewPic"==fieldName)
											{   
												 this.seeFileCommon(fileid,extendname,src,grid);
											}
				}
			}
		});

		

	},// end of the initComponents()
	riskReportAffrimRenderer : function(v){
		return v == true?'<font color=green>是</font>' : '<font color=red>否</font>';
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
	uploadRiskReportForThisPanel : function(projId,businessType,displayDel,RiskReportTemplate,templettype,panel,setname){
		var reloadjStore_RiskReport = function(){
			panel.getStore().reload()};
		//var mark = "cs_document_templet";
		Ext.Ajax.request({
			url : __ctxPath+'/contract/getByOnlymarkForUploadDocumentTemplet.do',
			method : 'GET',
			success : function(response, request) {
				var obj = Ext.util.JSON.decode(response.responseText);
				if(obj.success==true){
					var olymark = "cs_document_companyInfotemplet."+projId;
					if(displayDel == true){
						uploadReportJS('上传/下载公司材料',"typeisfile",olymark,null,null,null,reloadjStore_RiskReport,projId,businessType,setname);
					}else{
						downIsFileJS("typeisfile",olymark,projId,businessType);
					}
					
				}else{
					Ext.ux.Toast.msg('状态', '可能未上传公司材料！');
				}
				
			},
			failure : function(response) {
				Ext.ux.Toast.msg('状态', '操作失败，请重试');
			},
			params : {
				mark : RiskReportTemplate,
				businessType : businessType
			}
		});
		
	},
	deleteRiskReportFile : function(fileid,panel){
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
						panel.getStore().reload();
						pbar.getDialog().close();
					},
					failure : function(result, action) {
						Ext.ux.Toast.msg('状态','删除失败!');
						pbar.getDialog().close();
					}
				});
			}
		});
		
	},
	//预览
		seeFileCommon : function(fileid,extendname,src,grid){
		if(extendname==".doc" || extendname==".docx" || extendname==".xls" || extendname==".xlsx" || extendname==".pdf"){
			Ext.Ajax.request({
				url : __ctxPath+'/creditFlow/fileUploads/getfileAttachBycsfileidFileForm.do',
				method : 'POST',
				success : function(response, request) {
					var fileAttach = Ext.util.JSON.decode(response.responseText);
					if(extendname==".pdf"){
						new PdfTemplateView(fileAttach.data.fileId,"attachFiles/"+fileAttach.data.filePath,false,null)
					}else{
					    new OfficeTemplateView(fileAttach.data.fileId,null,false,null);
					}
				},
				failure : function(result, action) {
					Ext.Msg.alert('状态','预览失败!');
				},
				params : {
					fileid : fileid
				}
			});
			
		}else if(extendname==".jpg"||extendname==".gif"||extendname==".bmp"||extendname==".png"){
			var pic_panel;
			var pic_win;
			
			//下载图片
			downloadPic = function(fileid){
				window.open(__ctxPath+'/creditFlow/fileUploads/DownLoadFileForm.do?fileid='+fileid,'_blank');
			};
			
			pic_panel = new Ext.Panel({
				
				//id:'id_pic_panel',
		        frame:true,
		        autoHeight : true,
		        autoWidth: true,
		        layout:'fit',
		        items:[{
		        	html: '<div align="center"><img src="'+src+'" ondblclick="downloadPic('+fileid+'); " alt ="双击下载此文件"/></div>'
		        }]
				
			});
			
			pic_win = new Ext.Window({
				//id : 'id_pic_win',
				title: '文件预览',
				autoScroll : true,
				layout : 'fit',
				width : 550,
				height :405,
	//			animCollapse : true,
				//animateTarget : win.getEl(),
	//			closeAction : 'hide',
				maximizable : true,
				closable : true,
				resizable : true,
				plain : true,
				border : false,
				modal : true,
				buttonAlign: 'center',
				bodyStyle : 'padding: 0',
				items : [pic_panel]
			});
			
			pic_win.show();
		
		}else{
			window.open(__ctxPath+'/creditFlow/fileUploads/DownLoadFileForm.do?fileid='+fileid,'_blank');
		}
			
			
	},
	
	downloadRiskReportTemplate : function(RiskReportTemplate,businessType){
		Ext.Ajax.request({
			url : __ctxPath+'/contract/getByOnlymarkDocumentTemplet.do',
			method : 'GET',
			success : function(response, request) {
				var obj = Ext.util.JSON.decode(response.responseText);
				if(obj.success==true){
					window.open(__ctxPath+'/contract/ajaxGetReportTemplateDocumentTemplet.do?mark='+RiskReportTemplate+'&businessType='+businessType,'_blank');
					
				}else{
					Ext.ux.Toast.msg('状态', '可能未上传公司材料！');
				}
				
			},
			failure : function(response) {
				Ext.ux.Toast.msg('状态', '操作失败，请重试');
			},
			params : {
				mark : RiskReportTemplate,
				businessType : businessType
			}
		});
	//		window.location.href =__ctxPath+'/credit/document/ajaxGetReportTemplate.do?mark='+RiskReportTemplate+'&templettype='+templettype;
			//window.open(__ctxPath+'/credit/document/ajaxGetReportTemplate.do?mark='+RiskReportTemplate+'&businessType='+businessType,'_blank');
		},
	reUploadRiskReportForThisPanel : function(projId,businessType,displayDel,fileid,RiskReportTemplate,templettype,panel,setname){
		var reloadjStore_RiskReport = function(){panel.getStore().reload()};
		//var mark = "cs_document_templet";
		Ext.Ajax.request({
			url : __ctxPath+'/contract/getByOnlymarkForUploadDocumentTemplet.do',
			method : 'GET',
			success : function(response, request) {
				var obj = Ext.util.JSON.decode(response.responseText);
				if(obj.success==true){
					var olymark = "cs_document_companyInfotemplet."+projId;
					reUploadReportJS('上传/下载公司材料',"typeisfile",olymark,2,null,null,reloadjStore_RiskReport,projId,businessType,fileid,setname);
					
				}else{
					Ext.ux.Toast.msg('状态', '未上传公司材料！');
				}
				
				
			},
			failure : function(response) {
				Ext.ux.Toast.msg('状态', '操作失败，请重试');
			},
			params : {
				mark : RiskReportTemplate,
				businessType : businessType
			}
		})
		
	}

})