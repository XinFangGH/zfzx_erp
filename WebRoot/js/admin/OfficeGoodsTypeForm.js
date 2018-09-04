var OfficeGoodsTypeForm = function(typeId) {
	this.typeId = typeId;
	var fp = this.setup();
	var window = new Ext.Window({
				id : 'OfficeGoodsTypeFormWin',
				title : '办公用品类型详细信息',
				iconCls:'goods-type',
				width : 300,
				height : 120,
				modal : true,
				layout : 'anchor',
				plain : true,
				border:false,
				bodyStyle : 'padding:5px;',
				buttonAlign : 'center',
				items : [this.setup()],
				buttons : [{
					text : '保存',
					iconCls:'btn-save',
					handler : function() {
						var fp = Ext.getCmp('OfficeGoodsTypeForm');
						if (fp.getForm().isValid()) {
							fp.getForm().submit({
								method : 'post',
								waitMsg : '正在提交数据...',
								success : function(fp, action) {
									Ext.ux.Toast.msg('操作信息', '成功保存信息！');
									Ext.getCmp('leftOfficeGoodManagePanel').root
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
					iconCls:'btn-cancel',
					handler : function() {
						window.close();
					}
				}]
			});
	window.show();
};

OfficeGoodsTypeForm.prototype.setup = function() {

	var formPanel = new Ext.FormPanel({
				url : __ctxPath + '/admin/saveOfficeGoodsType.do',
				layout : 'form',
				id : 'OfficeGoodsTypeForm',
				frame : false,
				bodyStyle : 'padding:5px;',
				defaults : {
					widht : 400,
					anchor : '96%,96%'
				},
				formId : 'OfficeGoodsTypeFormId',
				defaultType : 'textfield',
				items : [{
							name : 'officeGoodsType.typeId',
							id : 'typeId',
							xtype : 'hidden',
							value : this.typeId == null ? '' : this.typeId
						}, {
							fieldLabel : '分类名称',
							name : 'officeGoodsType.typeName',
							id : 'typeName',
							allowBlank:false
						}

				]
			});

	if (this.typeId != null && this.typeId != 'undefined') {
		formPanel.getForm().load({
			deferredRender : false,
			url : __ctxPath + '/admin/getOfficeGoodsType.do?typeId='
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
