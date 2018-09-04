var DutySectionForm = function(sectionId) {
	this.sectionId = sectionId;
	var fp = this.setup();
	var window = new Ext.Window({
				id : 'DutySectionFormWin',
				iconCls:'menu-dutySection',
				title : '班次定义详细信息',
				width : 300,
				height : 280,
				modal : true,
				layout:'fit',
				buttonAlign : 'center',
				items : [this.setup()],
				buttons : [{
					text : '保存',
					iconCls : 'btn-save',
					handler : function() {
						var fp = Ext.getCmp('DutySectionForm');
						if (fp.getForm().isValid()) {
							fp.getForm().submit({
								method : 'post',
								waitMsg : '正在提交数据...',
								success : function(fp, action) {
									Ext.ux.Toast.msg('操作信息', '成功保存信息！');
									Ext.getCmp('DutySectionGrid').getStore().reload();
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

DutySectionForm.prototype.setup = function() {

	var formPanel = new Ext.FormPanel({
				url : __ctxPath + '/personal/saveDutySection.do',
				layout : 'form',
				id : 'DutySectionForm',
				bodyStyle : 'padding:5px;',
				border : false,
				defaults : {
					width : 400,
					anchor : '98%,98%'
				},
				formId : 'DutySectionFormId',
				defaultType : 'textfield',
				items : [{
							name : 'dutySection.sectionId',
							id : 'sectionId',
							xtype : 'hidden',
							value : this.sectionId == null
									? ''
									: this.sectionId
						}, {
							fieldLabel : '班次名称',
							name : 'dutySection.sectionName',
							id : 'sectionName',
							allowBlank:false
						},{
							fieldLabel : '开始签到',
							name : 'dutySection.startSignin1',
							xtype:'timefield',
							format: 'H:i',
							id : 'startSignin1',
							allowBlank:false
						}, {
							fieldLabel : '上班时间',
							name : 'dutySection.dutyStartTime1',
							id : 'dutyStartTime1',
							xtype:'timefield',
							format: 'H:i',
							allowBlank:false
						}, {
							fieldLabel : '签到结束时间',
							name : 'dutySection.endSignin1',
							id : 'endSignin1',
							xtype:'timefield',
							format: 'H:i',
							allowBlank:false
						}, {
							fieldLabel : '早退计时',
							name : 'dutySection.earlyOffTime1',
							id : 'earlyOffTime1',
							xtype:'timefield',
							format: 'H:i',
							allowBlank:false
						}, {
							fieldLabel : '下班时间',
							name : 'dutySection.dutyEndTime1',
							id : 'dutyEndTime1',
							xtype:'timefield',
							format: 'H:i',
							allowBlank:false
						}, {
							fieldLabel : '签退结束',
							name : 'dutySection.signOutTime1',
							id : 'signOutTime1',
							xtype:'timefield',
							format: 'H:i',
							allowBlank:false
						}
				]
			});

	if (this.sectionId != null && this.sectionId != 'undefined') {
		formPanel.getForm().load({
			deferredRender : false,
			url : __ctxPath + '/personal/getDutySection.do?sectionId='
					+ this.sectionId,
			waitMsg : '正在载入数据...',
			success : function(form, action) {
			},
			failure : function(form, action) {
			}
		});
	}
	return formPanel;

};
