var DepreRecordForm = function(recordId) {
	this.recordId = recordId;
	var fp = this.setup();
	var window = new Ext.Window({
				id : 'DepreRecordFormWin',
				title : '固定资产折旧记录详细信息',
				width : 500,
				height : 420,
				modal : true,
				layout : 'anchor',
				plain : true,
				bodyStyle : 'padding:5px;',
				buttonAlign : 'center',
				items : [this.setup()],
				buttons : [{
					text : '保存',
					iconCls : 'btn-save',
					handler : function() {
						var fp = Ext.getCmp('DepreRecordForm');
						if (fp.getForm().isValid()) {
							fp.getForm().submit({
								method : 'post',
								waitMsg : '正在提交数据...',
								success : function(fp, action) {
									Ext.ux.Toast.msg('操作信息', '成功保存信息！');
									Ext.getCmp('DepreRecordGrid').getStore()
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

DepreRecordForm.prototype.setup = function() {

	var formPanel = new Ext.FormPanel({
				url : __ctxPath + '/admin/saveDepreRecord.do',
				layout : 'form',
				id : 'DepreRecordForm',
				frame : true,
				defaults : {
					width : 400,
					anchor : '98%,98%'
				},
				formId : 'DepreRecordFormId',
				defaultType : 'textfield',
				items : [{
							name : 'depreRecord.recordId',
							id : 'recordId',
							xtype : 'hidden',
							value : this.recordId == null ? '' : this.recordId
						}, {
							fieldLabel : '',
							name : 'depreRecord.assetsId',
							id : 'assetsId'
						}, {
							fieldLabel : '',
							name : 'depreRecord.workCapacity',
							id : 'workCapacity'
						}, {
							fieldLabel : '',
							name : 'depreRecord.depreAmount',
							id : 'depreAmount'
						}, {
							fieldLabel : '',
							name : 'depreRecord.calTime',
							id : 'calTime'
						}

				]
			});

	if (this.recordId != null && this.recordId != 'undefined') {
		formPanel.getForm().load({
			deferredRender : false,
			url : __ctxPath + '/admin/getDepreRecord.do?recordId='
					+ this.recordId,
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
