Ext.ns('LeaseFinanceUploads');
/**
 * @author:chencc
 * @class uploads
 * @extends Ext.Panel
 * @description [uploads]管理
 * @company 互融时代
 * @createtime
 * 
 * VM引用例如：
 * __ctxPath + '/publicmodel/uploads/js/uploads.js',
 * this.uploads = new uploads({
			projectId : this.projectId,
			isHidden : false,
			isHiddenOnlineButton :true,
			isHiddenColumn : false,
			isDisabledButton : false,
			titleName :'银行解冻保证金凭证',
			tableName :'gl_bank_guaranteemoney',
			typeisfile :'typeisglbankguaranteemoney',
			uploadsSize : 15,
			isDisabledOnlineButton : true
		});
 * 、
 */
LeaseFinanceUploads = Ext.extend(Ext.Panel, {
	isDisabled : false,
	hiddenUpBtn:false,//隐藏上传按钮
	// 构造函数
	constructor : function(_cfg) {
		if(typeof(_cfg.projectId)!="undefined")
			{
			      this.projId=_cfg.projectId;
			      this.setname = typeof(_cfg.setname)!="undefined"?_cfg.setname:'未知类型';//setname为记录到数据库中文件的中文类型
			      this.isNotOnlyFile = typeof(_cfg.isNotOnlyFile)!="undefined"?_cfg.isNotOnlyFile:true;//是不是只允许上传一个文件，false为只能上传一个文件，true是可以上传多个文件
			      this.isHiddenOnlineButton =typeof(_cfg.isHiddenOnlineButton)!="undefined"?_cfg.isHiddenOnlineButton:true;//true为隐藏编辑按钮，false为不隐藏编辑按钮
			      this.businessType=typeof(_cfg.businessType)!="undefined"?_cfg.businessType:null;//业务品种businessType
			      this.isHiddenColumn=typeof(_cfg.isHiddenColumn)!="undefined"?_cfg.isHiddenColumn:false;//是否隐藏列 true为隐藏，false为不隐藏
			      this.isDisabledButton=typeof(_cfg.isDisabledButton)!="undefined"?_cfg.isDisabledButton:true;//上传按钮是否不可用，true为不可用，false为可用
			      this.titleName=typeof(_cfg.titleName)!="undefined"?_cfg.titleName:"";//按钮的名字
			      this.tableName=typeof(_cfg.tableName)!="undefined"?_cfg.tableName:"";//表名
			      this.typeisfile=typeof(_cfg.typeisfile)!="undefined"?_cfg.typeisfile:"typeisonlyfile";//上传的文件类型标识
			      this.uploadsSize=typeof(_cfg.uploadsSize)!="undefined"?_cfg.uploadsSize:null;//允许上传的文件的个数
			      this.mark="leaseObject"+'.'+this.projId;//唯一标识：表名.+项目id
			      this.setname = this.titleName!=''?this.titleName:this.setname;
			      this.setname = typeof(_cfg.setname)!="undefined" && typeof(_cfg.isJKDB)!="undefined" && _cfg.isJKDB == true?_cfg.setname:this.titleName;//目前只有客户借款合同及担保合同用到，isJKDB为true时，this.setname=_cfg.setname
			};
			if(typeof(_cfg.isDisabledOnlineButton)!="undefined")
			{
			      this.isDisabledOnlineButton=_cfg.isDisabledOnlineButton;
			}else{
				this.isDisabledOnlineButton = true
			}
			if(typeof(_cfg.isgdHidden)!="undefined")
			{
			      this.isgdHidden=_cfg.isgdHidden;//是否显示归档
			}else{
				this.isgdHidden = true
			}
			if(typeof(_cfg.isgdEdit)!="undefined")
			{
			      this.isgdEdit=_cfg.isgdEdit;//归档是否可以编辑
			}else{
				this.isgdEdit = false
			}
			if(typeof(_cfg.isfHidden)!="undefined")
			{
			      this.isfHidden =_cfg.isfHidden;//隐藏编辑相关操作
			}else{
				this.isfHidden = true;
			}
			//隐藏上传按钮
			if(typeof(_cfg.hiddenUpBtn)!="undefined"){
				this.hiddenUpBtn = _cfg.hiddenUpBtn;
			}

		Ext.applyIf(this, _cfg);
		// 初始化组件
		this.initUIComponents();
		// 调用父类构造
		LeaseFinanceUploads.superclass.constructor.call(this, {
					region : 'center',
					layout : 'anchor',
					items : [this.grid_UploadsPanel]
				});
	},// end of constructor
	// 初始化组件
	initUIComponents : function() {
		
		var checkColumn = new Ext.grid.CheckColumn({
			hidden : this.isgdHidden,
			editable : this.isgdEdit,
			header : '是否归档',
			dataIndex : 'isArchiveConfirm',
			fixed : true,
			width : 74
		});
				
		this.render_UploadsInfo = Ext.data.Record.create([{name : 'fileid'},{name : 'filename'}, {name : 'filepath'}, {name : 'extendname'}, {name : 'filesize'}, {name : 'createtime'}, {name : 'contentType'}, {name : 'webPath'},{name : 'projId'},{name : 'isArchiveConfirm'},{name : 'archiveConfirmRemark'}]);
		this.jStore_Uploads = new Ext.data.JsonStore({
			url : __ctxPath+ '/creditFlow/fileUploads/ajaxGetFilesListFileForm.do',
			totalProperty : 'totalProperty',
            root : 'topics',
            fields : this.render_UploadsInfo
            
        });
		this.jStore_Uploads.load({
			scope : this,
			params : {
				mark : this.mark,
				typeisfile : this.typeisfile
			}
		});
		//下载文件
		downFileCommon = function(fileid){
		 	window.open(__ctxPath+'/creditFlow/fileUploads/DownLoadFileForm.do?fileid='+fileid,'_blank');
			
		 };
		//上传文件
		uploadsFileCommon = function(titleName,typeisfile,mark,uploadsSize,projId,businessType,jStore,setname){
			var ss =this
			var container = Ext.getCmp('leaseObjectForm');
			if(projId>0){
				var objectId = projId;
			}else{
				var objectId = container.getCmpByName('flLeaseobjectInfo.id').getValue();
			}
			if(null == objectId || objectId ==''){
				Ext.ux.Toast.msg('操作信息', '上传文件前请先保存信息');
			}else{
			mark = "leaseObject."+objectId;
			
			var reloadjStore_Uploads = function(){
				var params1 ={
					mark : mark,
					typeisfile : this.typeisfile
				};
				jStore.on('beforeload', function(jStore, o) {
							
							Ext.apply(o.baseParams, params1);
						});
							var ss =Ext.encode (jStore.baseParams)
							
					
				jStore.reload()
				
			};
			uploadReportJS('上传/下载'+titleName+'文件',typeisfile,mark,uploadsSize,'',null,reloadjStore_Uploads,objectId,businessType,setname);
			}
		};
		this.uploadsTopBar = new Ext.Toolbar({
				items : [{
						iconCls : 'slupIcon',
						text : '上传',
						xtype : 'button',
						hidden:this.hiddenUpBtn,
						scope : this,
						handler : function() {
							uploadsFileCommon(this.titleName,this.typeisfile,this.mark,this.uploadsSize,this.projId,this.businessType,this.jStore_Uploads,this.setname)
						}
					}/*,
					new Ext.Toolbar.Separator({
						hidden : this.isDisabledOnlineButton
					}),
					{
						iconCls : 'btn-edit',
						text : '在线编辑',
						xtype : 'button',
						hidden : this.isDisabledOnlineButton,
						scope : this,
						handler : function() {
							onlineEditFileCommon(this.mark,this.typeisfile,this.projId,this.businessType,this.isDisabledOnlineButton)
						}
					}*/ ]
				});
		this.grid_UploadsPanel = new HT.EditorGridPanel({
			name:'uploads_'+this.typeisfile,
			hiddenCm : true,
			border : false,
			autoWidth : true,
			//rowActions : true,
			store : this.jStore_Uploads,
			autoExpandColumn : 'filename',
			autoScroll : true,
			//autoWidth : true,
			layout : 'anchor',
			clicksToEdit : 1,
			// anchor : fullanchor,
			viewConfig : {
				forceFit : true
			},
			plugins : [checkColumn],
			tbar : this.isHidden? null :this.uploadsTopBar,
			isShowTbar : this.isHidden?false : true,
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
				header : '文件名称(已上传)',
				dataIndex : 'filename'
			}, {
				header : '大小',
				fixed : true,
				width : 40,
				dataIndex : 'filesize',
				renderer : this.transition
			},{
				header : '类型',
				fixed : true,
				width : 40,
				dataIndex : 'extendname'
			}, {
				header : '上传时间',
				fixed : true,
				width : 76,
				dataIndex : 'createtime'
			}, {
				header : '下载',
				fixed : true,
				width : 40,
				dataIndex : 'fileid',
				scope : this,
				renderer : function(val, m, r) {
					return '<a title="下载'+this.titleName+'" style ="TEXT-DECORATION:underline;cursor:pointer" onclick="downFileCommon(\''+r.get('fileid')+'\')" >下载</a>';
				}
			},{
				header : '重新上传',
				fixed : true,
				width : 80,
				dataIndex : 'reloadCol',
				hidden :this.isHidden,
				scope : this,
				renderer : function(val, m, r) {
						return '<a title="重新上传'+this.titleName+'" style ="TEXT-DECORATION:underline;cursor:pointer" >重新上传</a>';
				}
			},{
				header : '预览',
				fixed : true,
				width : 40,
				dataIndex : 'readCol',
				scope : this,
				renderer : function(val, m, r) {
						return '<a title="预览'+r.get('filename')+'" style ="TEXT-DECORATION:underline;cursor:pointer" >预览</a>';
				}
			}/*,{
				header : '在线编辑',
				fixed : true,
				width : 80,
				hidden : this.isHidden,
				dataIndex : 'editCol',
				scope : this,
				renderer : function(val, m, r) {
						return '<a title="在线编辑'+r.get('filename')+'" style ="TEXT-DECORATION:underline;cursor:pointer" >编辑</a>';
				}
			}*/, {
				header : '删除',
				fixed : true,
				width : 40,
				dataIndex : 'deleteCol',
				hidden :this.isHidden,
				scope : this,
				renderer : function(val, m, r) {
						return '<a title="删除'+r.get('filename')+'" style ="TEXT-DECORATION:underline;cursor:pointer">删除</a>';
					
				}
			},checkColumn,{
				header : '归档备注',
				dataIndex : 'archiveConfirmRemark',
				hidden : this.isgdHidden,
				editable : this.isgdEdit,
				align : "center",
				width : 100,
				menuDisabled:true,
				editor : new Ext.form.TextField({
					selectOnFocus: true
				})
			}],
			listeners:{
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
					var container = Ext.getCmp('leaseObjectForm');
					var objectId = Ext.getCmp('leaseObjectForm').getCmpByName('flLeaseobjectInfo.id').getValue();
					if(null == objectId || objectId ==''){
						Ext.ux.Toast.msg('操作信息', '上传文件前请先保存信息');
					}else{
						var fieldName = grid.getColumnModel().getDataIndex(col); //Get field name
						var fileid = grid.getStore().getAt(row).get("fileid");
						var extendname = grid.getStore().getAt(row).get("extendname");
						var src = grid.getStore().getAt(row).get("webPath");
						if(fieldName == 'reloadCol'){//重新上传
							this.reUploadForThisPanel(this.titleName,this.typeisfile,objectId,this.businessType,true,fileid,this.mark,this.templettype,grid,this.setname)
						}else if(fieldName == 'readCol'){//预览
							this.seeFileCommon(fileid,extendname,src,grid);
						}else if(fieldName == 'deleteCol'){//删除
							this.deleteFileCommon(fileid,grid);
						}else if(fieldName == 'editCol'){//在线编辑
							this.onlineEditFileCommon(this.mark,this.typeisfile,objectId,this.businessType,this.isDisabledOnlineButton)
						}
					}
				}
			}
		});

		this.grid_UploadsPanel.addListener('rowdblclick', this.rowClick);

	},// end of the initComponents()
	
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
	
	deleteFileCommon : function(fileid,grid){
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
							Ext.Msg.alert('状态', '删除成功!');
							grid.getStore().reload();
							pbar.getDialog().close();
						},
						failure : function(result, action) {
							Ext.Msg.alert('状态','删除失败!');
							grid.getStore().reload();
							pbar.getDialog().close();
						}
					});
				}
			});
			
		},
		onlineEditFileCommon : function(mark,typeisfile,projId,businessType,bln){
			if(0==projId||''==projId){
				var container = Ext.getCmp('leaseObjectForm');
				var objectId = Ext.getCmp('leaseObjectForm').getCmpByName('flLeaseobjectInfo.id').getValue();
				if(null == objectId || objectId ==''){
					Ext.ux.Toast.msg('操作信息', '上传文件前请先保存信息');
					return ;
				}else{
					projId = objectId;
				}
			}
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
						Ext.Msg.alert('提示', '可能未上传office文件！');
					}
					
				},
				failure : function(response) {
					Ext.Msg.alert('提示', '操作失败，请重试');
				},
				params : {
					mark : mark,
					typeisfile : typeisfile,
					projId : projId,
					businessType :businessType
				}
			}); 	
		},
		rowClick : function(grid, rowindex, e) {
			/*grid.getSelectionModel().each(function(rec) {
						new SlMortgageForm({
									mortId : rec.data.mortId
								}).show();
					});*/
		},
		
		reUploadForThisPanel : function(titleName,typeisfile,projId,businessType,displayDel,fileid,mark,templettype,panel,setname){
			if(0==projId||''==projId){
				var container = Ext.getCmp('leaseObjectForm');
				var objectId = Ext.getCmp('leaseObjectForm').getCmpByName('flLeaseobjectInfo.id').getValue();
				if(null == objectId || objectId ==''){
					Ext.ux.Toast.msg('操作信息', '上传文件前请先保存信息');
					return ;
				}else{
					projId = objectId;
				}
			}
			var reloadjStore = function(){panel.getStore().reload()};
			reUploadReportJS('上传/下载"'+titleName+'"',typeisfile,mark,2,null,null,reloadjStore,projId,businessType,fileid,setname);
		}
		
	// 行的Action
		/*onRowAction : function(grid, record, action, row, col) {
			switch (action) {
				case 'btn-del' :
					this.deleteFileCommon.call(this, record.data.fileid,grid);
					break;
				default :
					break;
			}
		}*/

});