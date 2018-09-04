/**
 * @author csx
 * @createtime 2010-10-11
 * @class ArchTemplateForm
 * @extends Ext.Window
 * @description 公文模板表单
 * @company 智维软件
 */
ArchTemplateForm = Ext.extend(Ext.Window, {
	// 内嵌FormPanel
	formPanel : null,
	// 构造函数
	constructor : function(_cfg) {
		if (_cfg == null) {
			_cfg = {};
		}
		Ext.apply(this, _cfg);
		// 必须先初始化组件
		this.initComponents();
		ArchTemplateForm.superclass.constructor.call(this, {
					id : 'ArchTemplateFormWin',
					layout : 'fit',
					items : this.formPanel,
					modal : true,
					height : 180,
					width : 560,
					title : '公文模板详细信息',
					iconCls : 'menu-archive-template',
					buttonAlign : 'center',
					buttons : this.buttons
				});
	},// end of the constructor
	// 初始化组件
	initComponents : function() {
		this.formPanel = new Ext.FormPanel({
			layout : 'form',
			bodyStyle : 'padding:10px 10px 10px 10px',
			border : false,
			url : __ctxPath + '/archive/saveArchTemplate.do',
			id : 'ArchTemplateForm',
			defaults : {
				anchor : '98%,98%'
			},
			defaultType : 'textfield',
			items : [{
						name : 'archTemplate.templateId',
						id : 'templateId',
						xtype : 'hidden',
						value : this.templateId == null ? '' : this.templateId
					}, {
						xtype : 'compositefield',
						fieldLabel : '所属类型',
						items : [{
									name : 'archTemplate.archivesType.typeName',
									xtype : 'textfield',
									width : 250,
									value : this.typeName
											? this.typeName
											: null,
									readOnly : true,
									allowBlank : false
								}, {
									xtype : 'button',
									text : '选择类型',
									iconCls : 'btn-select',
									scope : this,
									handler : function() {
										var fPanel = this.formPanel;
										new GlobalTypeSelector({
											catKey : 'ARC_TEM_TYPE',
											isSingle : true,
											callback : function(typeId,
													typeName) {
												fPanel
														.getCmpByName('archTemplate.archivesType.proTypeId')
														.setValue(typeId);
												fPanel
														.getCmpByName('archTemplate.archivesType.typeName')
														.setValue(typeName);
											}
										}).show();
									}

								}]
					}, {
						fieldLabel : '模板名称',
						name : 'archTemplate.tempName',
						id : 'tempName',
						allowBlank : false
					}, {
						xtype : 'container',
						layout : 'column',
						style : 'padding-left:0px;margin-left:0px;',
						defaults : {
							border : false
						},
						items : [{
									width : 280,
									height : 36,
									style : 'padding-left:0px;',
									layout : 'form',
									items : {
										xtype : 'textfield',
										fieldLabel : '模板文件',
										name : 'archTemplate.tempPath',
										readOnly : true,
										id : 'tempPath',
										anchor : '98%,98%'
									}
								}, {
									xtype : 'button',
									text : '上传模板',
									iconCls : 'btn-upload',
									handler : function() {
										var dialog = App.createUploadDialog({
											file_cat : 'archive',
											callback : function(data) {
												for (var i = 0; i < data.length; i++) {
													Ext
															.getCmp('fileId')
															.setValue(data[i].fileId);
													Ext
															.getCmp('tempPath')
															.setValue(data[i].filePath);
												}
											}
										});
										dialog.show();
									}
								}, {
									xtype : 'button',
									text : '在线编辑',
									iconCls : 'btn-edit-online',
									handler : function() {
										var path = Ext.getCmp('tempPath')
												.getValue();

										new OfficeTemplateView(null, path,
												false, function(fileId,
														filePath) {
													Ext.getCmp('fileId')
															.setValue(fileId);
													Ext.getCmp('tempPath')
															.setValue(filePath);
												});
									}
								}]
					}, {
						xtype : 'hidden',
						name : 'archTemplate.fileId',
						id : 'fileId'
					}, {
						xtype : 'hidden',
						name : 'archTemplate.archivesType.proTypeId',
						id : 'proTypeId',
						value : this.typeId ? this.typeId : null
					}]
		});
		// 加载表单对应的数据
		if (this.templateId != null && this.templateId != 'undefined') {
			var fPanel = this.formPanel;
			this.formPanel.getForm().load({
				deferredRender : false,
				url : __ctxPath + '/archive/getArchTemplate.do?templateId='
						+ this.templateId,
				waitMsg : '正在载入数据...',
				success : function(form, action) {
					var result = action.result;
					if (result) {
						var proType = result.data.archivesType;
						if (proType != null) {
							fPanel
									.getCmpByName('archTemplate.archivesType.typeName')
									.setValue(proType.typeName);
							fPanel
									.getCmpByName('archTemplate.archivesType.proTypeId')
									.setValue(proType.typeId);
						}
					}
				},
				failure : function(form, action) {
				}
			});
		}
		// 初始化功能按钮
		this.buttons = [{
					text : '保存',
					iconCls : 'btn-save',
					handler : this.save.createCallback(this.formPanel, this)
				}, {
					text : '重置',
					iconCls : 'btn-reset',
					handler : this.reset.createCallback(this.formPanel)
				}, {
					text : '取消',
					iconCls : 'btn-cancel',
					handler : this.cancel.createCallback(this)
				}];
	},// end of the initcomponents

	/**
	 * 重置
	 * 
	 * @param {}
	 *            formPanel
	 */
	reset : function(formPanel) {
		formPanel.getForm().reset();
	},
	/**
	 * 取消
	 * 
	 * @param {}
	 *            window
	 */
	cancel : function(window) {
		window.close();
	},
	/**
	 * 保存记录
	 */
	save : function(formPanel, window) {
		if (formPanel.getForm().isValid()) {
			formPanel.getForm().submit({
						method : 'POST',
						waitMsg : '正在提交数据...',
						success : function(fp, action) {
							Ext.ux.Toast.msg('操作信息', '成功保存信息！');
							var gridPanel = Ext.getCmp('ArchTemplateGrid');
							if (gridPanel != null) {
								gridPanel.getStore().reload();
							}
							window.close();
						},
						failure : function(fp, action) {
							Ext.MessageBox.show({
										title : '操作信息',
										msg : '信息保存出错，请联系管理员！',
										buttons : Ext.MessageBox.OK,
										icon : Ext.MessageBox.ERROR
									});
							window.close();
						}
					});
		}
	}// end of save

});