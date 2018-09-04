var NewPublicDocumentForm = function(dId, dName) {
	return this.setup(dId, dName);
};

NewPublicDocumentForm.prototype.setup = function(dId, dName) {
	var docId;
	this.docId = dId;
	var toolbar = this.initToolbar();
	var formPanel = new Ext.FormPanel({
		url : __ctxPath + '/document/saveDocument.do',
		layout : 'form',
		id : 'NewPublicDocumentFormPanel',
		autoScroll:true,
		border:false,
		bodyStyle:'padding:10px 10px 10px 10px;',
		formId : 'NewPublicDocumentFormId',
		reader : new Ext.data.JsonReader({
					root : 'data'
				}, [{
							name : 'NewPublicDocumentForm.docId',
							mapping : 'docId'
						}, {
							name : 'NewPublicDocumentForm.docName',
							mapping : 'docName'
						}, {
							name : 'NewPublicDocumentForm.content',
							mapping : 'content'
						}]),
		items : [{
					xtype : 'hidden',
					id : 'NewPublicDocumentForm.docId',
					name : 'document.docId'
				}, {
					xtype : 'hidden',
					id : 'NewPublicDocumentForm.folderId',
					name : 'folderId'
				}, {
					xtype:'container',
					layout : 'column',
					height : 32,
					items : [{
								text : '选择目录:',
								xtype : 'label',
								width : 104
							}, {
								name : 'docFolderName',
								id : 'NewPublicDocumentForm.docFolderName',
								xtype : 'textfield',
								width : 430,
								readOnly:true,
								allowBlank : false
							}, {
								xtype : 'button',
								text : '请选择目录',
								iconCls:'menu-mail_folder',
								handler : function() {
									DocFolderSelector.getView(
											function(id, name) {
												var docF = Ext
														.getCmp('NewPublicDocumentForm.docFolderName');
												var docFolderId = Ext
														.getCmp('NewPublicDocumentForm.folderId');
												docF.setValue(name);
												docFolderId.setValue(id);
											}).show();
								}
							}, {
								xtype : 'button',
								text : '清除目录',
								iconCls : 'reset',
								handler : function() {
									var docFolderName = Ext
											.getCmp('NewPublicDocumentForm.docFolderName');
									var folderId = Ext
											.getCmp('NewPublicDocumentForm.folderId');
									docFolderName.setValue('');
									folderId.setValue('');
								}
							}]
				}, {
					fieldLabel : '文档名称',
					name : 'document.docName',
					id : 'NewPublicDocumentForm.docName',
					xtype : 'textfield',
					anchor:'98%',
					allowBlank : false
				}, {
					height : 350,
					anchor:'96%',
					xtype : 'fckeditor',
					fieldLabel : '内容',
					name : 'document.content',
					allowBlank : false,
					id : 'NewPublicDocumentForm.content'
				}, {
					layout : 'column',
					border:false,
					anchor:'98%',
					items : [{
								columnWidth : .7,
								layout : 'form',
								border:false,
								items : [{
											fieldLabel : '附件',
											xtype : 'panel',
											id : 'NewPublicDocumentForm.filePanel',
											frame : false,
											height : 80,
											autoScroll : true,
											html : ''
										}]
							}, {
								columnWidth : .3,
								border:false,
								items : [{
									xtype : 'button',
									text : '添加附件',
									iconCls:'menu-attachment',
									handler : function() {
										var dialog = App.createUploadDialog({
											file_cat : 'document',
											callback : function(data) {
												var fileIds = Ext
														.getCmp("NewPublicDocumentForm.fileIds");
												var filePanel = Ext
														.getCmp('NewPublicDocumentForm.filePanel');

												for (var i = 0; i < data.length; i++) {
													if (fileIds.getValue() != '') {
														fileIds
																.setValue(fileIds
																		.getValue()
																		+ ',');
													}
													fileIds.setValue(fileIds
															.getValue()
															+ data[i].fileId);
													Ext.DomHelper
															.append(
																	filePanel.body,
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
										var fileAttaches = Ext
												.getCmp("NewPublicDocumentForm.fileIds");
										var filePanel = Ext
												.getCmp('NewPublicDocumentForm.filePanel');

										filePanel.body.update('');
										fileAttaches.setValue('');
									}
								}, {
									xtype : 'hidden',
									id : 'NewPublicDocumentForm.fileIds',
									name : 'fileIds'
								}]
							}]
				}]
	});
	if (this.docId != null && this.docId != 'undefined') {
		formPanel.getForm().load({
			deferredRender : false,
			url : __ctxPath + '/document/getDocument.do?docId=' + this.docId,
			waitMsg : '正在载入数据...',
			success : function(form, action) {
				var doc = Ext.util.JSON.decode(action.response.responseText).data[0];
				var folder = doc.docFolder;
				var af = doc.attachFiles;
				var filePanel = Ext.getCmp('NewPublicDocumentForm.filePanel');
				var fileIds = Ext.getCmp("NewPublicDocumentForm.fileIds");
				for (var i = 0; i < af.length; i++) {
					if (fileIds.getValue() != '') {
						fileIds.setValue(fileIds.getValue() + ',');
					}
					fileIds.setValue(fileIds.getValue() + af[i].fileId);
					Ext.DomHelper
							.append(
									filePanel.body,
									'<span><a href="#" onclick="FileAttachDetail.show('
											+ af[i].fileId
											+ ')">'
											+ af[i].fileName
											+ '</a><img class="img-delete" src="'
											+ __ctxPath
											+ '/images/system/delete.gif" onclick="removeFormFile(this,'
											+ af[i].fileId
											+ ')"/>&nbsp;|&nbsp;</span>');
				}
				var folderId = folder.folderId;
				var folderName = folder.folderName;
				var folderIdField = Ext
						.getCmp('NewPublicDocumentForm.folderId');
				var folderNameField = Ext
						.getCmp('NewPublicDocumentForm.docFolderName');
				folderIdField.setValue(folderId);
				folderNameField.setValue(folderName);

			},
			failure : function(form, action) {
				Ext.MessageBox.show({
							title : '操作信息',
							msg : '载入信息失败，请联系管理员！',
							buttons : Ext.MessageBox.OK,
							icon : 'ext-mb-error'
						});
			}
		});
	}
	
	var panel=new Ext.Panel({
	    id:'NewPublicDocumentForm',
	    title : dName == null ? '新建公共文档' : dName,
	    iconCls : 'menu-new-document',
	    layout:'fit',
	    tbar:toolbar,
	    items:[formPanel]
	});

	return panel;
};

/**
 * 
 * @return {}
 */
NewPublicDocumentForm.prototype.initToolbar = function() {

	var toolbar = new Ext.Toolbar({
				height : 30,
				items : [{
					text : '保存',
					iconCls : 'btn-save',
					handler : function() {
						var docForm = Ext.getCmp('NewPublicDocumentFormPanel');
						if (docForm.getForm().isValid()) {
							docForm.getForm().submit({
								waitMsg : '正在提交,请稍候...',
								success : function(docForm, o) {
									Ext.ux.Toast.msg('操作信息', '提交成功！');
									var docForm = Ext
											.getCmp('NewPublicDocumentFormPanel');
									docForm.getForm().reset();
									var fileAttaches = Ext
											.getCmp("NewPublicDocumentForm.fileIds");
									var filePanel = Ext
											.getCmp('NewPublicDocumentForm.filePanel');

									filePanel.body.update('');
									fileAttaches.setValue('');
								},
								failure : function(mailform, o) {
									Ext.ux.Toast.msg('错误信息', o.result.msg);
								}
							});
						}
					}

				}, {
					text : '重置',
					iconCls : 'reset',
					handler : function() {
						var docForm = Ext.getCmp('NewPublicDocumentFormPanel');
						docForm.getForm().reset();
						var fileAttaches = Ext
								.getCmp("NewPublicDocumentForm.fileIds");
						var filePanel = Ext
								.getCmp('NewPublicDocumentForm.filePanel');
						filePanel.body.update('');
						fileAttaches.setValue('');
					}
				}]
			});
	return toolbar;
};

function removeFormFile(obj, fileId) {
	var fileIds = Ext.getCmp("NewPublicDocumentForm.fileIds");
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
