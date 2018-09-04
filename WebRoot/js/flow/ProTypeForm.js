var ProTypeForm = function(typeId) {
	this.typeId = typeId;
	var fp = this.setup();
	var window = new Ext.Window({
				id : 'ProTypeFormWin',
				title : '流程分类详细信息',
				width : 400,
				height : 120,
				modal : true,
				layout : 'anchor',
				plain : true,
				bodyStyle : 'padding:5px;',
				buttonAlign : 'center',
				items : [this.setup()],
				buttons : [{
					text : '保存',
					iconCls:'btn-save',
					handler : function() {
						var fp = Ext.getCmp('ProTypeForm');
						if (fp.getForm().isValid()) {
							fp.getForm().submit({
								method : 'post',
								waitMsg : '正在提交数据...',
								success : function(fp, action) {
									Ext.ux.Toast.msg('操作信息', '成功保存信息！');
									Ext.getCmp('proTypeLeftPanel').root.reload();
									window.close();
								},
								failure : function(fp, action) {
									Ext.MessageBox.show({
												title : '操作信息',
												msg : '信息保存出错，请联系管理员！',
												buttons : Ext.MessageBox.OK,
												icon : 'ext-mb-error'
											});
									window.close();
								}
							});
						}
					}
				}, {
					text : '取消',
					iconCls:'btn-cancel',
					handler : function() {
						window.close();
					}
				}]
			});
	window.show();
};

ProTypeForm.prototype.setup = function() {

	var formPanel = new Ext.FormPanel({
				url : __ctxPath + '/flow/saveProType.do',
				layout : 'form',
				id : 'ProTypeForm',
				frame : true,
				defaults : {
					widht : 300,
					anchor : '96%,96%'
				},
				formId : 'ProTypeFormId',
				defaultType : 'textfield',
				items : [{
							name : 'proType.typeId',
							id : 'typeId',
							xtype : 'hidden',
							value : this.typeId == null ? '' : this.typeId
						}, {
							fieldLabel : '分类名称',
							name : 'proType.typeName',
							id : 'typeName'
						}

				]
			});

	if (this.typeId != null && this.typeId != 'undefined') {
		formPanel.getForm().load({
					deferredRender : false,
					url : __ctxPath + '/flow/getProType.do?typeId='
							+ this.typeId,
					waitMsg : '正在载入数据...',
					success : function(form, action) {
					},
					failure : function(form, action) {
						Ext.ux.Toast.msg('编辑', '载入失败');
					}
				});
	}
	return formPanel;

};
