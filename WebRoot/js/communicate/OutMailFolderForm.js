var OutMailFolderForm = function(folderId,parentId) {
	this.folderId = folderId;
	this.parentId = parentId;
	var fp = this.setup();
	var window = new Ext.Window({
				id : 'MailFolderFormWin',
				title : '邮件文件夹详细信息',
				iconCls:'menu-mail_folder',
				width : 300,
				height : 160,
				modal : true,
				minWidth : 200,
				minHeight : 100,
				layout : 'anchor',
				plain : true,
				bodyStyle : 'padding:5px;',
				buttonAlign : 'center',
				items : [this.setup()],
				buttons : [{
					text : '保存',
					handler : function() {
						var fp = Ext.getCmp('OutMailFolderForm');
						if (fp.getForm().isValid()) {
							fp.getForm().submit({
								method : 'post',
								waitMsg : '正在提交数据...',
								success : function(fp, action) {
									Ext.ux.Toast.msg('操作信息', '成功信息保存！');
									Ext.getCmp('leftOutMailBoxTree').root.reload();
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
					handler : function() {
						window.close();
					}
				}]
			});
	window.show();
};

OutMailFolderForm.prototype.setup = function() {

	var formPanel = new Ext.FormPanel({
		url : __ctxPath + '/communicate/saveOutMailFolder_.do',
		layout : 'form',
		id : 'OutMailFolderForm',
		frame : true,
		defaults : {
			widht : 300,
			anchor : '96%,96%'
		},
		formId : 'MailFolderFormId',
		defaultType : 'textfield',
		items : [
				{
					name : 'outMailFolder.folderId',
					id : 'folderId',
					xtype : 'hidden',
					value : this.folderId == null ? '' : this.folderId
				},
					{
					fieldLabel : '父目录',
					name : 'outMailFolder.parentId',
					xtype:'hidden',
					id : 'parentId',
					value:this.parentId == null ? '' : this.parentId
				},{
					fieldLabel : '文件夹名称',
					name : 'outMailFolder.folderName',
					id : 'folderName'
				}]
	});

	if (this.folderId != null && this.folderId != 'undefined') {
		formPanel.getForm().load({
			deferredRender : false,
			url : __ctxPath + '/communicate/getOutMailFolder_.do?folderId='
					+ this.folderId,
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
