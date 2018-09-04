var MailFolderForm = function(folderId,parentId) {
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
						var fp = Ext.getCmp('MailFolderForm');
						if (fp.getForm().isValid()) {
							fp.getForm().submit({
								method : 'post',
								waitMsg : '正在提交数据...',
								success : function(fp, action) {
									Ext.ux.Toast.msg('操作信息', '成功信息保存！');
									Ext.getCmp('leftMailBoxTree').root.reload();
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

MailFolderForm.prototype.setup = function() {

	var formPanel = new Ext.FormPanel({
		url : __ctxPath + '/communicate/saveMailFolder.do',
		layout : 'form',
		id : 'MailFolderForm',
		frame : true,
		defaults : {
			widht : 300,
			anchor : '96%,96%'
		},
		formId : 'MailFolderFormId',
		defaultType : 'textfield',
		items : [
				{
					name : 'mailFolder.folderId',
					id : 'folderId',
					xtype : 'hidden',
					value : this.folderId == null ? '' : this.folderId
				},
					{
					fieldLabel : '父目录',
					name : 'mailFolder.parentId',
					xtype:'hidden',
					id : 'parentId',
					value:this.parentId == null ? '' : this.parentId
				},{
					fieldLabel : '文件夹名称',
					name : 'mailFolder.folderName',
					id : 'folderName'
				},{
					fieldLabel :'是否共享', //'1=表示共享，则所有的员工均可以使用该文件夹 0=私人文件夹',
					name : 'mailFolder.isPublic',
					id : 'isPublic',
					xtype:'hidden',
					//mode : 'local',
					//editable : false,
					//triggerAction : 'all',
					//store:[['1','是'], ['0','否']],
					value:0
				}]
	});

	if (this.folderId != null && this.folderId != 'undefined') {
		formPanel.getForm().load({
			deferredRender : false,
			url : __ctxPath + '/communicate/getMailFolder.do?folderId='
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
