var PlanTypeForm = function(typeId) {
	this.typeId = typeId;
	var fp = this.setup();
	var window = new Ext.Window({
				id : 'PlanTypeFormWin',
				title : '计划类型详细信息',
				iconCls:'menu-plantype',
				width : 400,
				height : 120,
				modal : true,
				layout : 'fit',
				buttonAlign : 'center',
				items : [this.setup()],
				buttons : [{
					text : '保存',
					iconCls : 'btn-save',
					handler : function() {
						var fp = Ext.getCmp('PlanTypeForm');
						if (fp.getForm().isValid()) {
							fp.getForm().submit({
								method : 'post',
								waitMsg : '正在提交数据...',
								success : function(fp, action) {
									Ext.ux.Toast.msg('操作信息', '成功保存信息！');
									Ext.getCmp('PlanTypeGrid').getStore()
											.reload();
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
					iconCls : 'btn-cancel',
					handler : function() {
						window.close();
					}
				}]
			});
	window.show();
};

PlanTypeForm.prototype.setup = function() {

	var formPanel = new Ext.FormPanel({
				url : __ctxPath + '/task/savePlanType.do',
				layout : 'form',
				id : 'PlanTypeForm',
				bodyStyle : 'padding:10px;',
				border : false,
				defaults : {
					width : 400,
					anchor : '98%,98%'
				},
				formId : 'PlanTypeFormId',
				defaultType : 'textfield',
				items : [{
							name : 'planType.typeId',
							id : 'typeId',
							xtype : 'hidden',
							value : this.typeId == null ? '' : this.typeId
						}, {
							fieldLabel : '类型名称',
							name : 'planType.typeName',
							id : 'typeName'
						}

				]
			});

	if (this.typeId != null && this.typeId != 'undefined') {
		formPanel.getForm().load({
					deferredRender : false,
					url : __ctxPath + '/task/getPlanType.do?typeId='
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
