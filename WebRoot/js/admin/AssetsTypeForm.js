var AssetsTypeForm = function(assetsTypeId) {
	this.assetsTypeId = assetsTypeId;
	var fp = this.setup();
	var window = new Ext.Window({
		id : 'AssetsTypeFormWin',
		title : '资产分类详细信息',
		iconCls : 'assets-type',
		width : 300,
		height : 130,
		modal : true,
		layout : 'fit',				
		buttonAlign : 'center',
		items : [this.setup()],
		buttons : [{
			text : '保存',
			iconCls : 'btn-save',
			handler : function(){
				AssetsTypeForm.save();
			}
		}, {
			text : '取消',
			iconCls : 'btn-cancel',
			handler : function() {
				window.close();
			}
		}],
		keys : {
			key : Ext.EventObject.ENTER,
			fn : function(){
				AssetsTypeForm.save();
			},
			scope : this
		}
	});
	window.show();
};

/**
 * 数据保存操作
 */
AssetsTypeForm.save = function() {
	var fp = Ext.getCmp('AssetsTypeForm');
	if (fp.getForm().isValid()) {
		fp.getForm().submit({
			method : 'post',
			waitMsg : '正在提交数据...',
			success : function(fp, action) {
				Ext.ux.Toast.msg('操作信息', '成功保存信息！');
				Ext.getCmp('leftFixedAssetsManagePanel').root.reload();
				Ext.getCmp('AssetsTypeFormWin').close();
			},
			failure : function(fp, action) {
				Ext.MessageBox.show({
					title : '操作信息',
					msg : '信息保存出错，请联系管理员！',
					buttons : Ext.MessageBox.OK,
					icon : 'ext-mb-error'
				});
				Ext.getCmp('AssetsTypeFormWin').close();
			}
		});
	}
};

AssetsTypeForm.prototype.setup = function() {

	var formPanel = new Ext.FormPanel({
				url : __ctxPath + '/admin/saveAssetsType.do',
				layout : 'form',
				id : 'AssetsTypeForm',
				bodyStyle : 'padding:5px;',
				border:false,
				defaults : {
					width : 400,
					anchor : '98%,98%'
				},
				formId : 'AssetsTypeFormId',
				defaultType : 'textfield',
				items : [{
							name : 'assetsType.assetsTypeId',
							id : 'assetsTypeId',
							xtype : 'hidden',
							value : this.assetsTypeId == null
									? ''
									: this.assetsTypeId
						}, {
							fieldLabel : '分类名称',
							name : 'assetsType.typeName',
							id : 'typeName',
							allowBlank:false
						}

				]
			});

	if (this.assetsTypeId != null && this.assetsTypeId != 'undefined') {
		formPanel.getForm().load({
			deferredRender : false,
			url : __ctxPath + '/admin/getAssetsType.do?assetsTypeId='
					+ this.assetsTypeId,
			waitMsg : '正在载入数据...',
			success : function(form, action) {
			},
			failure : function(form, action) {
			}
		});
	}
	return formPanel;

};
