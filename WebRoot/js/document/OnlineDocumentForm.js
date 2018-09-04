
OnlineDocumentForm=Ext.extend(Ext.Window,{
     formPanel:null,
     fileId:null,
     constructor:function(_cfg){
        Ext.applyIf(this,_cfg);
        this.initUI();
        OnlineDocumentForm.superclass.constructor.call(this,{
               width:700,
               title : '在线文档',
               width : 700,
			   height : 640,
               shim:false,
               modal : true,
               layout:'fit',
               iconCls:'menu-onlinedoc',
               maximizable:true,
               buttonAlign : 'center',
			   buttons:this.buttons,
               items:[
                  this.formPanel
               ]
        });
     },
     initUI:function(){
     	 Ext.useShims=true;
     	 this.docPanel=new NtkOfficePanel({showToolbar:true,height:500,doctype:this.docType,unshowMenuBar:false});
         this.formPanel=new HT.FormPanel({
         	  url : __ctxPath + '/document/saveOnlineDocument.do',
              defaults : {
					anchor : '98%,98%'
			  },
              items:[{
                 xtype:'hidden',
                 name:'document.docId'
              },{
				name : 'folderId',
				xtype : 'hidden',
				value:this.folderId
			},{
				xtype:'container',
				layout : 'column',
				height : 32,
				items : [{
							text : '选择目录:',
							xtype : 'label',
							width : 104
						}, {
							name : 'OnlineDocFolderName',
							xtype : 'textfield',
							width : 370,
							readOnly:true,
							allowBlank : false,
							value:this.folderName
						}, {
							xtype : 'button',
							text : '请选择目录',
							scope:this,
							iconCls:'menu-mail_folder',
							handler : function() {
								var fp=this.formPanel;
								DocFolderSelector.getView(
										function(id, name) {
											var docF =fp
													.getCmpByName('OnlineDocFolderName');
											var docFolderId = fp
													.getCmpByName('folderId');
											docF.setValue(name);
											docFolderId.setValue(id);
										},true).show();
							}
						}, {
							xtype : 'button',
							text : '清除目录',
							iconCls : 'reset',
							scope:this,
							handler : function() {
								var fp=this.formPanel;
								var docF = fp
													.getCmpByName('OnlineDocFolderName');
								var docFolderId = fp
													.getCmpByName('folderId');
								docF.setValue('');
								docFolderId.setValue('');
							}
						}]
			},{
              	 fieldLabel:'文档名称',
                 xtype:'textfield',
                 allowBlank:false,
                 name:'document.docName'
              },
              this.docPanel.panel,
              {
                 xtype:'hidden',
                 name:'documentFileId'
              },{
                 xtype:'hidden',
                 name:'document.docType',
                 value:this.docType
              }
              ]
         
         });
         var dPanel=this.docPanel;
         var formPanel=this.formPanel;
         if (this.docId != null && this.docId != 'undefined') {
			this.formPanel.loadData({
				url : __ctxPath + '/document/getDocument.do?docId=' + this.docId,		
				waitMsg : '正在载入数据...',
				preName:'document',
				root:'data',
				success : function(response,options) {
					var doc=Ext.util.JSON.decode(response.responseText).data;
					var af = doc.attachFiles;
					var folderId = doc.docFolder.folderId;
					var folderName = doc.docFolder.folderName;
					formPanel.getCmpByName('folderId').setValue(folderId);
					formPanel.getCmpByName('OnlineDocFolderName').setValue(folderName);
					if(af!=null){
					   var fileId=af[0].fileId;
					   formPanel.getCmpByName('documentFileId').setValue(fileId);
					   var filePath=af[0].filePath;
					   dPanel.openDoc(fileId);
					}
				},
				failure : function(response,options) {
					Ext.MessageBox.show({
								title : '操作信息',
								msg : '载入信息失败，请联系管理员！',
								buttons : Ext.MessageBox.OK,
								icon : 'ext-mb-error'
							});
				}
			});
		}
         this.buttons=[{
		    xtype:'button',
		    text:'保存',
		    iconCls:'btn-save',
		    handler:this.saveRecord,
		    scope:this
		},{
		    xtype:'button',
		    text:'关闭',
		    iconCls:'close',
		    scope:this,
		    handler:function(){
		    	this.docPanel.closeDoc();
		        this.close();
		    }
		}];
     },
     saveRecord:function(){
         var win=this;
     	 var fp=this.formPanel;
     	 var docName=fp.getCmpByName('document.docName').getValue();
     	 var fileIdField=fp.getCmpByName('documentFileId');
     	 var fileId='';
     	 fileId=fileIdField.getValue();
     	 var obj=null;
     	 var docPanel=this.docPanel;
     	 if(fileId!=''&&fileId!=undefined){
     	 	obj=docPanel.saveDoc({docName:docName,fileId:fileId,doctype:this.docType});
     	 }else{
     	    obj=docPanel.saveDoc({docName:docName,doctype:this.docType});
     	 }
         if(obj&&obj.success){
			var fileId=obj.fileId;
			fileIdField.setValue(fileId);
			if (fp.getForm().isValid()) {
				fp.getForm().submit({
							method : 'post',
							success : function(fp, action) {
								Ext.ux.Toast.msg('操作信息', '成功信息保存！');
								var grid=Ext.getCmp('OnlineDocumentGrid')
								if(grid!=null&&grid!=undefined){
								       grid.getStore()
										.reload();
								}
								docPanel.closeDoc();
								win.close();
							},
							failure : function(fp, action) {
								Ext.MessageBox.show({
											title : '操作信息',
											msg : '信息保存出错，请联系管理员！',
											buttons : Ext.MessageBox.OK,
											icon : 'ext-mb-error'
										});
								docPanel.closeDoc();
								win.close();
							}
						});
			}
		 }else{
		    Ext.MessageBox.show({
				title : '操作信息',
				msg : '保存信息失败，请联系管理员！',
				buttons : Ext.MessageBox.OK,
				icon : 'ext-mb-error'
			});
		 }
		 
     }
});