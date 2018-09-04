

KnowledgeForm=Ext.extend(Ext.Window,{
    formPanel:null,
	constructor:function(_cfg){
	    Ext.applyIf(this,_cfg);
	    this.initUI();
	    KnowledgeForm.superclass.constructor.call(this,{
	        id:'KnowledgeFormWin',
	        title : '知识详细信息',
			iconCls:'menu-personal-doc',
			width : 700,
			height : 560,
			modal : true,
			maximizable:true,
			layout : 'fit',
			items:this.formPanel,
			buttonAlign : 'center',
			buttons:this.buttons
	    });
	},
	initUI:function(){
	    this.formPanel = new HT.FormPanel({
			url : __ctxPath + '/document/saveDocument.do',
			id : 'KnowledgeForm',
			formId : 'KnowledgeFormId',
			defaults : {
					anchor : '98%,98%',
					margins:{top:4, right:4, bottom:4, left:4}
			},
			items : [{
						name : 'folderId',
						xtype : 'hidden',
						value:this.folderId
					}, {
						name : 'document.docId',
						xtype : 'hidden',
						value : this.docId == null ? '' : this.docId
					}, {
						xtype : 'textfield',
						fieldLabel : '文档名称',
						name : 'document.docName',
						anchor : '98%',
						allowBlank : false
					},  {
					xtype:'container',
					layout : 'column',
					height : 32,
					items : [{
								text : '选择目录:',
								xtype : 'label',
								width : 104
							}, {
								name : 'docFolderName',
								xtype : 'textfield',
								width : 200,
								readOnly:true,
								allowBlank : false,
								value:this.folderName
							}, {
								xtype : 'button',
								text : '请选择目录',
								scope:this,
								iconCls:'menu-mail_folder',
								handler : function() {
									DocFolderSelector.getView(
											function(id, name) {
												var formPanel=Ext.getCmp('KnowledgeForm');
												var docF = formPanel
														.getCmpByName('docFolderName');
												var docFolderId = formPanel
														.getCmpByName('folderId');
												docF.setValue(name);
												docFolderId.setValue(id);
											}).show();
								}
							}, {
								xtype : 'button',
								text : '清除目录',
								iconCls : 'reset',
								scope:this,
								handler : function() {
									var formPanel=Ext.getCmp('KnowledgeForm');
									var docF = formPanel
														.getCmpByName('docFolderName');
									var docFolderId = formPanel
														.getCmpByName('folderId');
									docF.setValue('');
									docFolderId.setValue('');
								}
							}]
				},{
						xtype : 'textfield',
						fieldLabel : '作者',
						name : 'document.author',
						anchor : '98%'
					},{
						xtype : 'textfield',
						fieldLabel : '关键字',
						name : 'document.keywords',
						anchor : '98%'
					}, {
						height : 280,
						anchor : '96%',
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
												file_cat : 'document/knowledgeManage',
												callback : function(data) {
													var fileIds = Ext.getCmp("KnowledgeFormFileIds");
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
											var fileAttaches = Ext.getCmp("KnowledgeFormFileIds");
											var filePanel = Ext.getCmp('personFilePanel');
											filePanel.body.update('');
											fileAttaches.setValue('');
										}
									}, {
										xtype : 'hidden',
										name : 'fileIds',
										id:'KnowledgeFormFileIds'
									}]
								}]
					}]
		});
		
		if (this.docId != null && this.docId != 'undefined') {
			var formPanel=this.formPanel;
			this.formPanel.loadData({
				url : __ctxPath + '/document/getDocument.do?docId=' + this.docId,		
				waitMsg : '正在载入数据...',
				preName:'document',
		        root:'data',
				success : function(response,options) {
					var doc=Ext.util.JSON.decode(response.responseText).data;
					var folderId = doc.docFolder.folderId;
					var folderName = doc.docFolder.folderName;
					formPanel.getCmpByName('folderId').setValue(folderId);
					formPanel.getCmpByName('docFolderName').setValue(folderName);
					var af = doc.attachFiles;
					var filePanel = Ext.getCmp('personFilePanel');
					var fileIds = Ext.getCmp("KnowledgeFormFileIds");
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
			if (fp.getForm().isValid()) {
				fp.getForm().submit({
							method : 'post',
							waitMsg : '正在提交数据...',
							success : function(fp, action) {
								Ext.ux.Toast.msg('操作信息', '成功信息保存！');
								var grid=Ext.getCmp('KnowledgeGrid');
								if(grid!=null&&grid!=undefined){
								       grid.getStore().reload();
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

function removeFile(obj, fileId) {
	var fileIds = Ext.getCmp("KnowledgeFormFileIds");
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
