DocumentTemplateForm = Ext.extend(Ext.Window, {
			docPanel : null,
			formPanel : null,
			constructor : function(_cfg) {
				Ext.applyIf(this, _cfg);
				this.initUI();
				DocumentTemplateForm.superclass.constructor.call(this, {
							width : 780,
							title : '模板详细信息',
							height : 680,
							iconCls:'menu-template',
							shim : false,
							modal : true,
							layout : 'fit',
							maximizable : true,
							buttonAlign : 'center',
							buttons : this.buttons,
							items : [this.formPanel]

						});
			},
			initUI : function() {
				Ext.useShims = true;
				this.docPanel = new NtkOfficePanel({
							showToolbar : false,
							height : 520
						});

				this.formPanel = new Ext.FormPanel({
							url : __ctxPath + '/document/savePaintTemplate.do',
							layout : 'form',
							bodyStyle : 'padding:10px',
							border : false,
							autoScroll : true,
							// id : 'PaintTemplateForm',
							defaults : {
								anchor : '96%,96%'
							},
							defaultType : 'textfield',
							items : [{
								name : 'paintTemplate.ptemplateId',
								xtype : 'hidden',
								value : this.ptemplateId == null
										? ''
										: this.ptemplateId
							}, {
								name : 'paintTemplate.fileId',
								xtype : 'hidden'
							}, {
								fieldLabel : '模板名称',
								name : 'paintTemplate.templateName',
								allowBlank : false,
								maxLength : 64
							}, {
								fieldLabel : '模板Key',
								name : 'paintTemplate.templateKey',
								allowBlank : false,
								maxLength : 64
							}, this.docPanel.panel, {
								fieldLabel : '是否启用',// 0为启动，1为禁用
								name : 'paintTemplate.isActivate',
								allowBlank : false,
								xtype : 'hidden',
								value : 0
							}, {
								xtype : 'hidden',
								fieldLabel : '模板文件',
								name : 'paintTemplate.path',
								readOnly : true,
								anchor : '98%,98%'
							}]
						});
				// 加载表单对应的数据
				if (this.ptemplateId != null && this.ptemplateId != 'undefined') {
					var dPanel = this.docPanel;
					var fp=this.formPanel;
					this.formPanel.loadData({
								url : __ctxPath
										+ '/document/getPaintTemplate.do?ptemplateId='
										+ this.ptemplateId,
								root : 'data',
								preName : 'paintTemplate',
								success : function(response, options) {
									var temp=Ext.util.JSON.decode(response.responseText).data;
									var af = temp.fileAttach;
									if(af!=null){
									   var fileId=af.fileId;
									   fp.getCmpByName('paintTemplate.fileId').setValue(fileId);
									   dPanel.openDoc(fileId);
									}
								}
							});
				}

				this.buttons = [{
							xtype : 'button',
							text : '保存',
							iconCls : 'btn-save',
							scope : this,
							handler : this.saveTemplate
						}, {
							xtype : 'button',
							text : '关闭',
							iconCls : 'close',
							scope : this,
							handler : function() {
								this.docPanel.closeDoc();
								this.close();
							}
						}];
			},
			saveTemplate : function() {
				var win = this;
				var fp = this.formPanel;
				if (fp.getForm().isValid()) {
					var fileId=fp.getCmpByName('paintTemplate.fileId').getValue();
					var docName = fp.getCmpByName('paintTemplate.templateName').getValue();
					var obj = this.docPanel.saveDoc({
								docName : docName,
								fileId : fileId,
								doctype : 'doc'
							});
					if (obj && obj.success) {
						var docPanel = this.docPanel;
						var fileField = fp.getCmpByName('paintTemplate.fileId');
						var PathField = fp.getCmpByName('paintTemplate.path');
						var fileId = obj.fileId;
						var path = obj.filePath;
						fileField.setValue(fileId);
						PathField.setValue(path);

						fp.getForm().submit({
									method : 'post',
									success : function(fp, action) {
										Ext.ux.Toast.msg('操作信息', '成功信息保存！');
										var grid = Ext
												.getCmp('PaintTemplateGrid');
										if (grid != null && grid != undefined) {
											grid.getStore().reload();
										}
										docPanel.closeDoc();
										win.close();
									},
									failure : function(fp, action) {
										
										var result=Ext.decode(action.response.responseText);
										
										if(result.success==false && (result.msg!=null && result.msg !=undefined)){
											Ext.ux.Toast.msg('操作信息','该Key已经存在!');
											return;
										}else{
											Ext.MessageBox.show({
														title : '操作信息',
														msg : '信息保存出错，请联系管理员！',
														buttons : Ext.MessageBox.OK,
														icon : 'ext-mb-error'
													});
											docPanel.closeDoc();
											win.close();
										}
									}
								});
						
					}else{
						Ext.MessageBox.show({
									title : '操作信息',
									msg : '保存信息失败，请联系管理员！',
									buttons : Ext.MessageBox.OK,
									icon : 'ext-mb-error'
								});
					}
				}
			}
		});