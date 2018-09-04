DocFolderForm=Ext.extend(Ext.Window,{
    formPanel:null,
	constructor:function(_cfg){
	    Ext.applyIf(this,_cfg);
	    this.initUI();
	    DocFolderForm.superclass.constructor.call(this,{
	            id : 'DocFolderFormWin',
				title : '目录详细信息',
				iconCls:'menu-mail_folder',
				width : 400,
				height : 220,
				modal : true,
				layout : 'fit',
				border:false,
				buttonAlign : 'center',
				items : [this.formPanel],
				buttons :this.buttons
	    });
	},
	initUI:function(){
	   this.formPanel= new Ext.FormPanel({
				url : __ctxPath + '/document/saveDocFolder.do',
				layout : 'form',
				id : 'DocFolderForm',
				defaults : {
					widht : 400,
					anchor : '96%,96%'
				},
				bodyStyle : 'padding:5px;',
				formId : 'DocFolderFormId',
				defaultType : 'textfield',
				items : [{
							name : 'docFolder.folderId',
							id : 'folderId',
							xtype : 'hidden',
							value : this.folderId == null ? '' : this.folderId
						}, {
							fieldLabel : '目录名称',
							name : 'docFolder.folderName',
							id : 'folderName',
							allowBlank:false
						},{
						     fieldLabel:'描述',
						     name : 'docFolder.descp',
							 id : 'descp',
							 xtype:'textarea'
						},{
							xtype:'hidden',
							name : 'docFolder.parentId',
							id : 'parentId',
							value:this.parentId
						},{
							xtype:'hidden',
							name : 'docFolder.isShared',
							id : 'isShared',
							value :this.isShared
						}

				]
			});
			
			this.buttons=[{
			    xtype:'button',
			    text:'保存',
			    iconCls:'btn-save',
			    scope:this,
			    handler:this.saveRecord
			},{
			   xtype:'button',
			   text:'关闭',
			   iconCls:'close',
			   scope:this,
			   handler:function(){
			      this.close();
			   }
			}];
			
			if (this.folderId != null && this.folderId != 'undefined') {
				this.formPanel.getForm().load({
					deferredRender : false,
					url : __ctxPath + '/document/getDocFolder.do?folderId='
							+ this.folderId,
					waitMsg : '正在载入数据...',
					success : function(form, action) {
					},
					failure : function(form, action) {
						Ext.ux.Toast.msg('编辑', '载入失败');
					}
				});
			}
	},
	saveRecord:function(){
	    var fp = this.formPanel;
	    var win=this;
		if (fp.getForm().isValid()) {
			fp.getForm().submit({
				method : 'post',
				waitMsg : '正在提交数据...',
				success : function(fp, action) {
					Ext.ux.Toast.msg('操作信息', '成功信息保存！');
					//左树重新reload
					var leftFolderPanel=Ext.getCmp("leftFolderPanel");
					var leftPublicFolder=Ext.getCmp('leftTreePanel');
					var grid=Ext.getCmp('DocFolderGrid');
					if(grid!=null){
					    grid.getStore().reload();
					}
//					var knowledgeGrid=Ext.getCmp('KnowledgeGrid');
//					if(knowledgeGrid!=null){
//					    knowledgeGrid.getStore().reload();
//					}
					if(leftFolderPanel!=null){
						leftFolderPanel.root.reload();
					}
					if(leftPublicFolder!=null){
						leftPublicFolder.root.reload();
					}
					
					var view=Ext.getCmp('PrivateDocumentView');
						if(view!=null){
						   view.dataView.getStore().reload();
						}
					if(win.callback){
						win.callback.call(win);
					}
					win.close();
				},
				failure : function(fp, action) {
					Ext.MessageBox.show({
								title : '操作信息',
								msg : '信息保存出错，请联系管理员！',
								buttons : Ext.MessageBox.OK,
								icon : 'ext-mb-error'
							});
					win.close();
				}
			});
		}
	}
});

//var DocFolderForm = function(folderId,parentId,isShared) {
//	this.folderId = folderId;
//	this.parentId=parentId;
//	this.isShared=isShared;
//	var fp = this.setup();
//	var win = new Ext.Window({
//				id : 'DocFolderFormWin',
//				title : '目录详细信息',
//				iconCls:'menu-mail_folder',
//				width : 400,
//				height : 120,
//				modal : true,
//				layout : 'fit',
//				border:false,
//				buttonAlign : 'center',
//				items : [this.setup()],
//				buttons : [{
//					text : '保存',
//					iconCls:'btn-save',
//					handler : function() {
//						var fp = Ext.getCmp('DocFolderForm');
//						if (fp.getForm().isValid()) {
//							fp.getForm().submit({
//								method : 'post',
//								waitMsg : '正在提交数据...',
//								success : function(fp, action) {
//									Ext.ux.Toast.msg('操作信息', '成功信息保存！');
//									//左树重新reload
//									var leftFolderPanel=Ext.getCmp("leftFolderPanel");
//									var leftPublicFolder=Ext.getCmp('leftDocFolderSharedPanel');
//									var grid=Ext.getCmp('DocFolderGrid');
//									if(grid!=null){
//									    grid.getStore().reload();
//									}
//									if(leftFolderPanel!=null){
//										leftFolderPanel.root.reload();
//									}
//									if(leftPublicFolder!=null){
//										leftPublicFolder.root.reload();
//									}
//									var view=Ext.getCmp('PrivateDocumentView');
//										if(view!=null){
//										   view.dataView.getStore().reload();
//										}
//									win.close();
//								},
//								failure : function(fp, action) {
//									Ext.MessageBox.show({
//												title : '操作信息',
//												msg : '信息保存出错，请联系管理员！',
//												buttons : Ext.MessageBox.OK,
//												icon : 'ext-mb-error'
//											});
//									win.close();
//								}
//							});
//						}
//					}
//				}, {
//					text : '取消',
//					iconCls:'reset',
//					handler : function() {
//						win.close();
//					}
//				}]
//			});
//	win.show();
//};
//
//DocFolderForm.prototype.setup = function() {
//
//	var formPanel = new Ext.FormPanel({
//				url : __ctxPath + '/document/saveDocFolder.do',
//				layout : 'form',
//				id : 'DocFolderForm',
//				defaults : {
//					widht : 400,
//					anchor : '96%,96%'
//				},
//				bodyStyle : 'padding:5px;',
//				formId : 'DocFolderFormId',
//				defaultType : 'textfield',
//				items : [{
//							name : 'docFolder.folderId',
//							id : 'folderId',
//							xtype : 'hidden',
//							value : this.folderId == null ? '' : this.folderId
//						}, {
//							fieldLabel : '目录名称',
//							name : 'docFolder.folderName',
//							id : 'folderName',
//							allowBlank:false
//						},{
//							xtype:'hidden',
//							name : 'docFolder.parentId',
//							id : 'parentId',
//							value:this.parentId
//						},{
//							xtype:'hidden',
//							name : 'docFolder.isShared',
//							id : 'isShared',
//							value :this.isShared
//						}
//
//				]
//			});
//	if (this.folderId != null && this.folderId != 'undefined') {
//		formPanel.getForm().load({
//			deferredRender : false,
//			url : __ctxPath + '/document/getDocFolder.do?folderId='
//					+ this.folderId,
//			waitMsg : '正在载入数据...',
//			success : function(form, action) {
//			},
//			failure : function(form, action) {
//				Ext.ux.Toast.msg('编辑', '载入失败');
//			}
//		});
//	}
//	return formPanel;
//
//};
