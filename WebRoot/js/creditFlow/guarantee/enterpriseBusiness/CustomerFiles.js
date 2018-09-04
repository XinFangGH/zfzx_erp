/**
 * @author:
 * @class CustomerFiles
 * @extends Ext.Panel
 * @description [CustomerFiles]管理
 * @company 北京互融时代软件有限公司
 * @createtime:
 */
CustomerFiles = Ext.extend(Ext.Panel, {
	// 构造函数
	projectId:1,
	businessType:'',
	constructor : function(_cfg) {
		if(typeof(_cfg.projectId)!="undifined"){
			this.projectId=_cfg.projectId
		}
		if(typeof(_cfg.businessType)!="undifined"){
			this.businessType=_cfg.businessType
		}
		if(typeof(_cfg.isgdHidden)!="undifined"){
			this.isgdHidden=_cfg.isgdHidden
		}else{
			this.isgdHidden=true
		}
		if(typeof(_cfg.isgdEdit)!="undifined"){
			this.isgdEdit=_cfg.isgdEdit
		}else{
			this.isgdEdit=false
		}
		Ext.applyIf(this, _cfg);
		// 初始化组件
		this.initUIComponents();
		// 调用父类构造
		CustomerFiles.superclass.constructor.call(this, {
		
			items : [this.gridPanel ]
		});
	},// end of constructor
	// 初始化组件
	initUIComponents : function() {
		downFileJS = function(fileid,mark){
			if(mark!=''){
			   window.open(__ctxPath+'/creditFlow/fileUploads/DownLoadFileForm.do?fileid='+fileid,'_blank');
			}else{
			   window.open(__ctxPath+"/contract/lookUploadContractProduceHelper.do?conId="+fileid,'_blank');
			}
		 };
		 
		 RunNtkOfficePanel = function(contract_Id){
				Ext.Ajax.request({
					url : __ctxPath+'/creditFlow/fileUploads/getFileAttachContractFileForm.do',
					method : 'GET',
					success : function(response, request) {
						var objfile = Ext.util.JSON.decode(response.responseText);
						if(objfile.success == true){
							var file_id =objfile.fileId;
							new OfficeTemplateView(file_id,null,true,null);
						}else{
							Ext.ux.Toast.msg('状态', '合同未生成或上传成功，请先生成或上传合同！');
						}
						
					},
					failure : function(response) {
						Ext.ux.Toast.msg('状态', '合同未上传，请先上传合同');
					},
					params : {
						contractId :contract_Id
					}
				})
			
		};
		seeFile = function(fileid,extendname,src){
			
			if(extendname==".doc" || extendname==".docx" || extendname==".xls" || extendname==".xlsx"){
				Ext.Ajax.request({
					url : __ctxPath+'/creditFlow/fileUploads/getfileAttachBycsfileidFileForm.do',
					method : 'POST',
					success : function(response, request) {
						var fileAttach = Ext.util.JSON.decode(response.responseText);
						new OfficeTemplateView(fileAttach.fileId,null,true,null);
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
				
				
		};
		var gdCheckColumn = new Ext.grid.CheckColumn({
					hidden : this.isgdHidden,
					header : '是否归档',
					dataIndex : 'isArchiveConfirm',
					editable : this.isgdEdit,
					width : 70
		});
		this.gridPanel = new HT.EditorGridPanel( {
			border : false,
			region : 'center',
			autoHeight : true,
			hiddenCm:true,
			url : __ctxPath + "/creditFlow/fileUploads/listFileForm.do?projectId="+this.projectId+"&businessType="+this.businessType,
			fields : [ {
				name : 'fileid',
				type : 'int'
			}, 'filename', 'extendname', 'setname', 'filesize','createtime','webPath','mark','isArchiveConfirm','archiveConfirmRemark'],
			isShowTbar : false,
			bbar:false,
			plugins : [gdCheckColumn],
			columns : [ {
				header : 'id',
				dataIndex : 'fileid',
				hidden : true
			}, {
				header : '类型',
				dataIndex : 'setname'
			}

			, {
				header : '名称',
				dataIndex : 'filename'
			}, {
				header : '大小',
				width : 60,
				dataIndex : 'filesize',
				renderer : this.transition
				/*renderer : function(v) {
					switch (v) {
						case 0 :
						return '--';
						break;
					}
			   }*/
			}, {
				header : '扩展名',
				width : 50,
				dataIndex : 'extendname'
			}, {
				header : '上传时间',
				width : 76,
				dataIndex : 'createtime',
				renderer : function(v){
					v=v.trim();
					return v.substring(0,v.lastIndexOf(" "))
				}
			}, {
				header : '下载',
				width : 66,
				dataIndex : '',
				renderer : function(val, m, r) {

					return '<a title="下载" style ="TEXT-DECORATION:underline;cursor:pointer" onclick="downFileJS('+r.get('fileid')+',\''+r.get('mark')+'\')" >下载</a>';
				}
			}, {
				header : '在线预览',
				width : 66,
				dataIndex : 'lawName',
				renderer : function(val, m, r) {
					if(r.get('mark')!=''){
						return '<a title="在线查看" style ="TEXT-DECORATION:underline;cursor:pointer" onclick="seeFile('+r.get('fileid')+',\''+r.get('extendname')+'\',\''+r.get('webPath')+'\')" >查看</a>';

						
					}else{
						 
						return '<a title="在线查看" style ="TEXT-DECORATION:underline;cursor:pointer" onclick="RunNtkOfficePanel('+r.get('fileid')+')" >查看</a>';
					}
					
				}
			},gdCheckColumn
			, {
				header : '备注',
				dataIndex : 'archiveConfirmRemark',
				hidden : this.isgdHidden,
				editor : new Ext.form.TextField({})
			}],
			listeners : {
				scope : this,
				afteredit : function(e) {
					var args;
					var value = e.value;
					var id = e.record.data['fileid'];
					var mark=e.record.data['mark'];
					var gridPanel=this.gridPanel;
					if (e.originalValue != e.value) {//编辑行数据发生改变
						if(mark!=''){
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
								e.record.commit();
							},
							failure : function(response) {
								Ext.Msg.alert('状态', '操作失败，请重试');
							},
							params : args
						})
					}else{
						if(e.field == 'isArchiveConfirm') {
							args = {'procreditContract.isRecord': value,'procreditContract.id': id};
						}
						if(e.field == 'archiveConfirmRemark') {
							args = {'procreditContract.recordRemark': value,'procreditContract.id': id};
						}
						Ext.Ajax.request({
							url : __ctxPath+'/contract/updateProcreditContractByIdProduceHelper.do',
							method : 'POST',
							scope : this,
							success : function(response, request) {
								e.record.commit();
							},
							failure : function(response) {
								Ext.Msg.alert('状态', '操作失败，请重试');
							},
							params : args
						})
					}
					}
				}
			}
		//end of columns
				});

	},
	transition : function(v){
		var size = "";
		if(v==0||v==''||v==null){
			size = '--';
		}else{
			if(Math.floor(v/1024)>1024){
				size = Math.floor((v/1024/1024))+'M';
			}else if(Math.floor(v)<1024){
				size = Math.floor(v)+'B';
			}else if(Math.floor(v/1024)<1024){
				size = Math.floor(v/1024)+'K'
			}else{
				size = '未知';
			}
		}
		return size;
	}
	
});
