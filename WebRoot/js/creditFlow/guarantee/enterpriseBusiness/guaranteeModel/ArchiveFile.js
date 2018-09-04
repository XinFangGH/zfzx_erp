Ext.ns('uploads');
uploads.jStore_Uploads;
ArchiveFile = Ext.extend(Ext.Panel, {
	isDisabled : false,
	// 构造函数
	constructor : function(_cfg) {
//		if(typeof(_cfg.projectId)!="undefined")
//			{
//			      this.projId=_cfg.projectId;
//			      this.isHidden=_cfg.isHidden;
//			      this.isHiddenOnlineButton =typeof(_cfg.isHiddenOnlineButton)!="undefined"?_cfg.isHiddenOnlineButton:true;//true为隐藏编辑按钮，false为不隐藏编辑按钮
//			      this.businessType=typeof(_cfg.businessType)!="undefined"?_cfg.businessType:null;
//			      this.isHiddenColumn=typeof(_cfg.isHiddenColumn)!="undefined"?_cfg.isHiddenColumn:false;//是否隐藏列 true为隐藏，false为不隐藏
//			      this.isDisabledButton=typeof(_cfg.isDisabledButton)!="undefined"?_cfg.isDisabledButton:true;//上传按钮是否不可用，true为不可用，false为可用
//			      this.titleName=typeof(_cfg.titleName)!="undefined"?_cfg.titleName:"";//按钮的名字
//			      this.tableName=typeof(_cfg.tableName)!="undefined"?_cfg.tableName:"";//表名
//			      this.typeisfile=typeof(_cfg.typeisfile)!="undefined"?_cfg.typeisfile:"typeisonlyfile";//上传的文件类型
//			      this.uploadsSize=typeof(_cfg.uploadsSize)!="undefined"?_cfg.uploadsSize:1;//允许上传的文件的个数
//			      this.mark=this.tableName+'.'+this.projId;//唯一标识：表名.+项目id
//			};
//			if(typeof(_cfg.isDisabledOnlineButton)!="undefined")
//			{
//			      this.isDisabledOnlineButton=_cfg.isDisabledOnlineButton;
//			}else{
//				isDisabledOnlineButton = true
//			}

		Ext.applyIf(this, _cfg);
		// 初始化组件
		this.initUIComponents();
		// 调用父类构造
		ArchiveFile.superclass.constructor.call(this, {
			//		id : 'uploads',
					region : 'center',
					layout : 'anchor',
					items : [/*this.button_Uploads,*/this.grid_UploadsPanel]
				});
	},// end of constructor
	// 初始化组件
	initUIComponents : function() {
		var gdCheckColumn = new Ext.grid.CheckColumn({
					hidden : this.isgdHidden,
					header : '是否归档',
					dataIndex : 'isArchiveConfirm',
					editable : this.isgdEdit,
					width : 70
				});
				
		this.render_UploadsInfo = Ext.data.Record.create([{name : 'fileid'},{name : 'filename'}, {name : 'filepath'}, {name : 'isArchiveConfirm'}, {name : 'archiveConfirmRemark'}, {name : 'createtime'}, {name : 'contentType'}, {name : 'webPath'},{name : 'projId'},{name:'setname'},{name:'filesize'},{name:'extendname'},{name:'mark'}]);
		uploads.jStore_Uploads = new Ext.data.JsonStore({
			url : __ctxPath+ '/creditFlow/fileUploads/getlistbyprojIdFileForm.do',
			totalProperty : 'totalProperty',
            root : 'topics',
            fields : this.render_UploadsInfo
            
        });
		uploads.jStore_Uploads.load({
			scope : this,
			params : {
				projId : this.projectId,
				businessType : this.businessType
			}
		});
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
		this.grid_UploadsPanel = new HT.EditorGridPanel({
			//id : 'grid_UploadsPanel',
			//width : 800,
			isShowTbar : false,
			autoWidth : true,
			store : uploads.jStore_Uploads,
			autoExpandColumn : 'filename',
			autoScroll : true,
			hiddenCm:true,
			//autoWidth : true,
			layout : 'anchor',
			clicksToEdit : 1,
			// anchor : fullanchor,
			viewConfig : {
				forceFit : true
			},
			plugins : [gdCheckColumn],
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
				scope : this
			},{
				header : '文件类型',
				width : 150,
				dataIndex : 'setname'
			},{
				header : '文件名称',
				width : 150,
				dataIndex : 'filename'
			}, {
				header : '大小',
				width : 60,
				dataIndex : 'filesize',
				renderer : this.transition
			}, {
				header : '扩展名',
				width : 50,
				dataIndex : 'extendname'
			}, {
				header : '上传时间',
				width : 76,
				dataIndex : 'createtime'

			}, {
				header : '下载',
				dataIndex : 'fileid',
				scope : this,
				width : 66,
				align:'center',
				renderer : function(val, m, r) {
					return '<a title="下载调查报告" style ="TEXT-DECORATION:underline;cursor:pointer" onclick="uploads.downFileCommon(\''+r.get('fileid')+'\')" >下载</a>';
					
				}
			}, {
				header : '在线预览',
				width : 66,
				dataIndex : 'lawName',
				align:'center',
				renderer : function(val, m, r) {
					
					    return '<a title="在线查看" style ="TEXT-DECORATION:underline;cursor:pointer" onclick="seeFile('+r.get('fileid')+',\''+r.get('extendname')+'\',\''+r.get('webPath')+'\')" >查看</a>';
					
					
				}
			}, gdCheckColumn,
//				{
//				header : '是否归档',
//				dataIndex : 'isArchiveConfirm',
//		//		hidden : this.isHiddenAffrim,
//			//	renderer : this.reportAffrimRenderer,
//				editor : new Ext.form.ComboBox({
//					header : '是否归档',
//					dataIndex : 'isArchiveConfirm',
//					editable : this.isgdEdit,
//					
//					width : 70
//				})
//			}, 
				{
				header : '备注',
				dataIndex : 'archiveConfirmRemark',
		//		hidden : this.isHiddenAffrim,
				editor : new Ext.form.TextField({})
			}],
			listeners : {
				scope : this,
				afteredit : function(e) {
					var args;
					var value = e.value;
					var uploadsPanel=this.grid_UploadsPanel
					var id = e.record.data['fileid'];
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
								uploadsPanel.getStore().reload()
							},
							failure : function(response) {
								Ext.Msg.alert('状态', '操作失败，请重试');
							},
							params : args
						})
					}
				}
			}
		});

		

	},// end of the initComponents()
	reportAffrimRenderer : function(v){
		return v == true?'<font color=green>是</font>' : '<font color=red>否</font>';
	},
	savereload:function(){
		this.grid_UploadsPanel.getStore().reload();
	},
	transition : function(v){
		return ((Math.floor(v/1024))>1024)?Math.floor((v/1024/1024))+'M':Math.floor(v/1024)+'K';
	}

});
//下载文件
uploads.downFileCommon = function(fileid){
 	window.open(__ctxPath+'/creditFlow/fileUploads/DownLoadFileForm.do?fileid='+fileid,'_blank');
	
 };






