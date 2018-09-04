var ProcessRunForm = function(runId) {
	this.runId = runId;
	var fp = this.setup();
	var window = new Ext.Window({
				id : 'ProcessRunFormWin',
				title : 'ProcessRun详细信息',
				width : 500,
				height : 420,
				modal: true,
				layout : 'anchor',
				plain : true,
				bodyStyle : 'padding:5px;',
				buttonAlign : 'center',
				items : [this.setup()],
				buttons : [{
					text : '保存',
					handler : function() {
						var fp=Ext.getCmp('ProcessRunForm');
						if (fp.getForm().isValid()) {
							fp.getForm().submit({
										method: 'post',
										waitMsg : '正在提交数据...',
										success : function(fp,action) {
											Ext.ux.Toast.msg('操作信息', '成功保存信息！');
											Ext.getCmp('ProcessRunGrid').getStore().reload();
											window.close();
										},
										failure : function(fp,action) {
											 Ext.MessageBox.show({
										           title: '操作信息',
										           msg: '信息保存出错，请联系管理员！',
										           buttons: Ext.MessageBox.OK,
										           icon:'ext-mb-error' 
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

ProcessRunForm.prototype.setup = function() {

	var formPanel = new Ext.FormPanel({
				url : __ctxPath+ '/flow/saveProcessRun.do',
				layout : 'form',
				id:'ProcessRunForm',
				frame : true,
				defaults : {
					widht : 400,
					anchor : '96%,96%'
				},
				formId:'ProcessRunFormId',
	        	defaultType : 'textfield',
				items : [{
							name : 'processRun.runId',
							id : 'runId',
							xtype:'hidden',
							value : this.runId == null ? '' : this.runId
						}
,{
fieldLabel : '标题',	
name : 'processRun.subject',
id : 'subject'
	}
,{
fieldLabel : '创建人',	
name : 'processRun.creator',
id : 'creator'
	}
,{
fieldLabel : '所属用户',	
name : 'processRun.userId',
id : 'userId'
	}
,{
fieldLabel : '所属Jbpm发布ID',	
name : 'processRun.deployId',
id : 'deployId'
	}
,{
fieldLabel : '流程实例ID',	
name : 'processRun.piId',
id : 'piId'
	}

						]
			});

			if(this.runId!=null&&this.runId!='undefined'){
				formPanel.getForm().load({
					deferredRender :false,
					url : __ctxPath + '/flow/getProcessRun.do?runId=' + this.runId,
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
