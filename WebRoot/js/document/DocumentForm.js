
DocumentForm=Ext.extend(Ext.Window,{
    formPanel:null,
    
    constructor:function(_cfg){
        Ext.applyIf(this,_cfg);
        this.initUI();
        DocumentForm.superclass.constructor.call(this,{
            id : 'DocumentFormWin',
			title : '文档详细信息',
			iconCls:'menu-personal-doc',
			width : 700,
			height : 500,
			modal : true,
			maximizable:true,
			layout : 'fit',
			items:this.formPanel,
			buttonAlign : 'center',
			buttons:this.buttons
        });
    },
    initUI:function(){
        var _url = __ctxPath + '/document/listDocFolder.do?method=1';// 不把根目录显示出来
		var folderSelector = new TreeSelector('folderSelect', _url, '文件夹*',
				'folderId');
		this.formPanel = new HT.FormPanel({
			url : __ctxPath + '/document/saveDocument.do',
			id : 'DocumentForm',
			formId : 'DocumentFormId',
			defaults : {
					anchor : '98%,98%',
					margins:{top:4, right:4, bottom:4, left:4}
			},
			items : [{
						name : 'folderId',
						id : 'DocumentForm.folderId',
						xtype : 'hidden'
					}, {
						name : 'document.docId',
						xtype : 'hidden',
						value : this.docId == null ? '' : this.docId
					},folderSelector, {
						xtype : 'textfield',
						fieldLabel : '文档名称',
						name : 'document.docName',
						anchor : '98%',
						allowBlank : false
					}, {
						height : 280,
						anchor : '98%',
						xtype : 'fckeditor',
						fieldLabel : '内容',
						name : 'document.content',
						allowBlank : false
					},{
					    xtype:'hidden',
					    name:'document.docType',
					    value:'文档'
					}, {
						xtype:'container',
						layout : 'column',
						border:false,
						defaults:{border:false},
						items : [{
									columnWidth : .7,
									layout : 'form',
									border:false,
									items : [{
												fieldLabel : '附件',
												xtype : 'panel',
												id : 'personFilePanel',
												frame : false,
												border:true,
												bodyStyle:'padding:4px 4px 4px 4px',
												height : 80,
												autoScroll : true,
												html : ''
											}]
								}, {
									columnWidth : .3,
									items : [{
										border:false,
										xtype : 'button',
										text : '添加附件',
										iconCls:'menu-attachment',
										handler : function() {
											var dialog = App.createUploadDialog({
												file_cat : 'document/privateDocument',
												callback : function(data) {
													var fileIds = Ext.getCmp("DocumentForm.fileIds");
													var filePanel = Ext.getCmp('personFilePanel');
	
													for (var i = 0; i < data.length; i++) {
														if (fileIds.getValue() != '') {
															fileIds.setValue(fileIds.getValue() + ',');
														}
														fileIds.setValue(fileIds.getValue() + data[i].fileId);
														Ext.DomHelper.append(filePanel.body,
															'<span><a href="#" onclick="FileAttachDetail.show('
																	+ data[i].fileId
																	+ ')">'
																	+ data[i].fileName
																	+ '</a> <img class="img-delete" src="'
																	+ __ctxPath
																	+ '/images/system/delete.gif" onclick="removeFile(this,'
																	+ data[i].fileId
																	+ ')"/>&nbsp;|&nbsp;</span>');
													}
												}
											});
											dialog.show(this);
										}
									}, {
										xtype : 'button',
										text : '清除附件',
										iconCls : 'reset',
										handler : function() {
											var fileAttaches = Ext.getCmp("DocumentForm.fileIds");
											var filePanel = Ext.getCmp('personFilePanel');
											filePanel.body.update('');
											fileAttaches.setValue('');
										}
									}, {
										xtype : 'hidden',
										id : 'DocumentForm.fileIds',
										name : 'fileIds'
									}]
								}]
					}]
		});
		if(this.folderId!=null&&this.folderId!=undefined&&this.folderName!=null&&this.folderName!=undefined){
		    Ext.getCmp('DocumentForm.folderId').setValue(this.folderId);
			Ext.getCmp('folderSelect').setValue(this.folderName);
		}
		
		if (this.docId != null && this.docId != 'undefined') {
			this.formPanel.loadData({
				url : __ctxPath + '/document/getDocument.do?docId=' + this.docId,		
				waitMsg : '正在载入数据...',
				preName:'document',
				root:'data',
				success : function(response,options) {
					var doc=Ext.util.JSON.decode(response.responseText).data;
					var folderId = doc.docFolder.folderId;
					var folderName = doc.docFolder.folderName;
					Ext.getCmp('DocumentForm.folderId').setValue(folderId);
					Ext.getCmp('folderSelect').setValue(folderName);
					var af = doc.attachFiles;
					var filePanel = Ext.getCmp('personFilePanel');
					var fileIds = Ext.getCmp("DocumentForm.fileIds");
					for (var i = 0; i < af.length; i++) {
						if (fileIds.getValue() != '') {
							fileIds.setValue(fileIds.getValue() + ',');
						}
						fileIds.setValue(fileIds.getValue() + af[i].fileId);
						Ext.DomHelper.append(filePanel.body,
							'<span><a href="#" onclick="FileAttachDetail.show('
									+ af[i].fileId
									+ ')">'
									+ af[i].fileName
									+ '</a><img class="img-delete" src="'
									+ __ctxPath
									+ '/images/system/delete.gif" onclick="removeFile(this,'
									+ af[i].fileId
									+ ')"/>&nbsp;|&nbsp;</span>');
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
		        this.close();
		    }
		}];
    },
    saveRecord:function(){
        var fp = this.formPanel;
        var win=this;
		var selectValue = Ext.getCmp('folderSelect').getValue();
		if (selectValue != null && selectValue != ''
				&& selectValue != 'undefined') {
			if (fp.getForm().isValid()) {
				fp.getForm().submit({
					method : 'post',
					waitMsg : '正在提交数据...',
					success : function(fp, action) {
						Ext.ux.Toast.msg('操作信息', '成功信息保存！');
						var grid=Ext.getCmp('DocumentGrid');
						if(grid!=null&&grid!=undefined){
						   grid.getStore().reload();
						}
						var view=Ext.getCmp('PrivateDocumentView');
						if(view!=null){
						   view.dataView.getStore().reload();
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
		} else {
			Ext.MessageBox.show({
						title : '操作信息',
						msg : '请选择文件夹！',
						buttons : Ext.MessageBox.OK,
						icon : 'ext-mb-error'
					});
		}
    }
});

function removeFile(obj, fileId) {
	var fileIds = Ext.getCmp("DocumentForm.fileIds");
	var value = fileIds.getValue();
	if (value.indexOf(',') < 0) {// 仅有一个附件
		fileIds.setValue('');
	} else {
		value = value.replace(',' + fileId, '').replace(fileId + ',', '');
		fileIds.setValue(value);
	}
	var el = Ext.get(obj.parentNode);
	el.remove();
};
