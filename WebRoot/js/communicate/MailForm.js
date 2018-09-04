var MailForm = function(mailId, boxId, opt) {
	return this.setup(mailId, boxId, opt);
};

MailForm.prototype.setup = function(mailId, boxId, opt) {
	var toolbar = this.initToolbar();
	var copyFieldItem = new copyFieldItems();
	
//	var reader=new Ext.data.JsonReader({
//					root : 'data'
//				}, [{
//							name : 'mail.recipientIDs',
//							mapping : 'recipientIDs'
//						}, {
//							name : 'mail.copyToIDs',
//							mapping : 'copyToIDs'
//						}, {
//							name : 'mail.mailStatus',
//							mapping : 'mailStatus'
//						}, {
//							name : 'mail.fileIds',
//							mapping : 'fileIds'
//						}, {
//							name : 'mail.mailId',
//							mapping : 'mailId'
//						}, {
//							name : 'mail.recipientNames',
//							mapping : 'recipientNames'
//						}, {
//							name : 'mail.subject',
//							mapping : 'subject'
//						}, {
//							name : 'mailImportantFlag',
//							mapping : 'importantFlag'
//						}, {
//							name : 'mail.filenames',
//							mapping : 'filenames'
//						}, {
//							name : 'mail.content',
//							mapping : 'content'
//						}, {
//							name : 'mail.copyToNames',
//							mapping : 'copyToNames'
//						}]);
	
				var formPanel = new Ext.FormPanel({
					url : __ctxPath + '/communicate/saveMail.do',
					id:'mailFormPanel',
					border:false,
					//autoHeight:true,
//					defaults:{
//						anchor : ''
//					},
					bodyStyle : 'padding-left : 6%;padding-top:10px;',
//					reader : reader,
					items : [
				// -----------------------------------------hidden 的属性
					{
						fieldLabel : '收件人ID列表用,分隔',
						name : 'mail.recipientIDs',
						id : 'mail.recipientIDs',
						xtype : 'hidden'
					}, {
						fieldLabel : '抄送人ID列表用,分开',
						name : 'mail.copyToIDs',
						id : 'mail.copyToIDs',
						xtype : 'hidden'
					}, {
						fieldLabel : '邮件状态',
						name : 'mail.mailStatus',
						id : 'mail.mailStatus',
						xtype : 'hidden',
						value : 1
					}, {
						fieldLabel : '附件IDs',
						name : 'mail.fileIds',
						xtype : 'hidden',
						id : 'mail.fileIds'
					}, {
						fieldLabel : 'BOXID',
						name : 'boxId',
						xtype : 'hidden',
						id : 'mailBoxId'
					}, {
						fieldLabel : 'MailId',
						name : 'mail.mailId',
						xtype : 'hidden',
						id : 'mail.mailId'
					}, {
						fieldLabel : '操作',
						name : 'replyBoxId',
						xtype : 'hidden',
						id : 'mail.replyBoxId'
					}, {
						fieldLabel : '附件名称列表',
						name : 'mail.filenames',
						xtype : 'hidden',
						id : 'mail.filenames'
					},
					// ------------------------------------------ hidden end

							{
								fieldLabel : '主题',
								xtype : 'textfield',
								name : 'mail.subject',
								id : 'mail.subject',
								allowBlank : false,
								width : 400,
								blankText : '邮件主题为必填'
							}, 
							{
								xtype : 'compositefield',
								autoWidth : true,
								fieldLabel : '收件人',
								items : [{
									width : 400,
									height : 21,
									name : 'mail.recipientNames',
									id : 'mail.recipientNames',
									xtype : 'textfield',
									allowBlank : false,
									blankText : '请选择收件人',
									readOnly : true
								}, {
									xtype : 'button',
									text : '选择收件人',
									iconCls : 'btn-mail_recipient',
									scope : this,
//									handler : function() {
//										var fPanel = formPanel;
//										new UserDialog({
//													single : false,
//													isAll : true,
//													callback : function(ids, names) {
//														fPanel.getCmpByName('mail.recipientIDs').setValue(ids);
//														fPanel.getCmpByName('mail.recipientNames').setValue(names);
//													}
//												}).show();
																			
									handler : function() {
										UserSelector.getView(
											function(userIds, fullnames) {
												var recipientIDs = Ext.getCmp('mail.recipientIDs');
												var recipientNames = Ext.getCmp('mail.recipientNames');
												recipientIDs.setValue(userIds);
												recipientNames.setValue(fullnames);
											},false
										).show();
									}
								}, {
									xtype : 'button',
									text : '我要抄送',
									iconCls : 'btn-mail_copy',
									handler : function() {
										var copyField = Ext.getCmp('copyField');
										copyField.show();
									}
								}]
							}, 
							{
								xtype : 'container',// 抄送人container
								id : 'copyField',
								layout : 'column',
								height:32,
								hidden : true,
								defaultType : 'textfield',
								items : [copyFieldItem]
							}, {
								xtype : 'compositefield',
								autoWidth : true,
								fieldLabel : '优先级',
								items : [{
									width : 400,
									fieldLabel : '邮件优先级',
									hiddenName : 'mail.importantFlag',
									id : 'mailImportantFlag',
									xtype : 'combo',
									mode : 'local',
									editable : false,
									value : '1',
									triggerAction : 'all',
									store : [['1', '一般'], ['2', '重要'],
											['3', '非常重要']]
								}, {
									xtype : 'checkbox',
									name : 'sendMessage',
									boxLabel : '告诉他有信'
								}]
							}, {
								xtype : 'attachpanel',
								name : 'mailAttachPanel',
								fieldLabel : '附件',
								leftWidth : 400,
								fileCat : 'mail'
							},
								{
								fieldLabel : '内容',
								name : 'mail.content',
								id : 'mail.content',
								xtype : 'fckeditor',
								height : 300,
								width : '85%'
							}

					]
			// form items
	});
	if (mailId != null && mailId != 'undefined') {
		var _mailId = Ext.getCmp('mail.mailId');
		var mailAttachPanel = formPanel.getCmpByName('mailAttachPanel');
		if (opt == 'draft') {// 重载草稿
			_mailId.setValue(mailId);//草稿才需要在表单记录邮件ID
			formPanel.loadData({
				url : __ctxPath + '/communicate/getMail.do',
				method : 'post',
				preName : 'mail',
				params : {
					mailId : mailId,
					folderId : 3,
					boxId : boxId
				},
				waitMsg : '正在载入数据...',
				success : function(response, option) {
					var copyToIDs = Ext.getCmp('mail.copyToIDs');
					if (copyToIDs.getValue() != '') {// 假如草稿有抄送人,激活抄送人控件
						var copyField = Ext.getCmp('copyField');
						copyField.show();
					}
					
					var results = Ext.util.JSON.decode(response.responseText).data;
					mailAttachPanel.loadByIds(results.fileIds);
				}
			});
		} else if (opt == 'reply') {// 回复
			formPanel.loadData({
				url : __ctxPath + '/communicate/optMail.do',
				method : 'post',
				preName : 'mail',
				params : {
					mailId : mailId,
					boxId : boxId,
					opt : '回复'
				},
				waitMsg : '正在载入数据...',
				success : function(response, option) {
					Ext.getCmp('mail.replyBoxId').setValue(boxId);
				}
			});
		} else if (opt == 'forward') {
			formPanel.loadData({
				deferredRender : false,
				url : __ctxPath + '/communicate/optMail.do',
				method : 'post',
				preName : 'mail',
				params : {
					mailId : mailId,
					opt : '转发'
				},
				waitMsg : '正在载入数据...',
				success : function(response, option) {
					var results = Ext.util.JSON.decode(response.responseText).data;
					mailAttachPanel.loadByIds(results.fileIds);
				}
			});
		}
	}
	if (boxId != null && boxId != 'undefined') {
		var mailBoxId = Ext.getCmp('mailBoxId');
		mailBoxId.setValue(boxId);
	}
	
	var mailFormPanel=new Ext.Panel({
		title : '发送邮件',
		iconCls : 'menu-mail_send',
		autoScroll:true,
		tbar : toolbar,
		id:'MailForm',
		layout : 'anchor',
		plain : true,
		bodyStyle : 'padding:5px;',
        items:[formPanel]
	});
	
	return mailFormPanel;

};

/**
 * 
 * @return {}
 */
MailForm.prototype.initToolbar = function() {

	var toolbar = new Ext.Toolbar({
		height : 30,
		items : [{
			text : '立即发送',
			iconCls : 'btn-finish',
			handler : function() {
				var mailform = Ext.getCmp('mailFormPanel');
				var mailStatus = Ext.getCmp('mail.mailStatus');
				if(mailform.getForm().isValid()){
					mailStatus.setValue(1);
					
					//附件处理
					var mailAttachPanel = mailform.getCmpByName('mailAttachPanel');
					mailform.getCmpByName('mail.fileIds').setValue(mailAttachPanel.getFileIds());
					mailform.getCmpByName('mail.filenames').setValue(mailAttachPanel.getFileNames());
					
					mailform.getForm().submit({
						waitMsg : '正在发送邮件,请稍候...',
						success : function(fp, o) {
							Ext.ux.Toast.msg('操作信息', '邮件发送成功！');
//							Ext.Msg.confirm('操作信息', '邮件发送成功！继续发邮件?', function(btn) {
//								if (btn == 'yes') {
//									mailAttachPanel.clearFile();
//									mailform.getCmpByName('mail.recipientIDs').setValue('');
//									mailform.getCmpByName('mail.copyToIDs').setValue('');
//									mailform.getCmpByName('mail.fileIds').setValue('');
//									mailform.getCmpByName('mail.mailId').setValue('');
//									mailform.getCmpByName('mail.filenames').setValue('');
//									mailform.getCmpByName('mail.subject').setValue('');
//									mailform.getCmpByName('mail.recipientNames').setValue('');
//									mailform.getCmpByName('mail.copyToNames').setValue('');
//									mailform.getCmpByName('mail.content').setValue('');
//								} else {
//									var tabs = Ext.getCmp('centerTabPanel');
//									tabs.remove('MailForm');
//								}
//							});
							var tabs = Ext.getCmp('centerTabPanel');
							tabs.remove('MailForm');
						},
						failure : function(mailform, o) {
							Ext.ux.Toast.msg('错误信息', o.result.msg);
						}
					});
				}
			}

		}, {
			text : '存草稿',
			iconCls : 'btn-mail_save',
			handler : function() {
				var mailStatus = Ext.getCmp('mail.mailStatus');
				mailStatus.setValue(0);
				var mailform = Ext.getCmp('mailFormPanel');
				if(mailform.getForm().isValid()){
					//附件处理
					var mailAttachPanel = mailform.getCmpByName('mailAttachPanel');
					mailform.getCmpByName('mail.fileIds').setValue(mailAttachPanel.getFileIds());
					mailform.getCmpByName('mail.filenames').setValue(mailAttachPanel.getFileNames());
					
					mailform.getForm().submit({
						waitMsg : '正在保存草稿,请稍候...',
						success : function(mailform, o) {
							Ext.ux.Toast.msg('操作信息', '草稿保存成功！');
//							Ext.Msg.confirm('操作信息', '草稿保存成功！继续发邮件?', function(btn) {
//								if (btn == 'yes') {
//									mailAttachPanel.clearFile();
//									mailform.getCmpByName('mail.recipientIDs').setValue('');
//									mailform.getCmpByName('mail.copyToIDs').setValue('');
//									mailform.getCmpByName('mail.fileIds').setValue('');
//									mailform.getCmpByName('mail.mailId').setValue('');
//									mailform.getCmpByName('mail.filenames').setValue('');
//									mailform.getCmpByName('mail.subject').setValue('');
//									mailform.getCmpByName('mail.recipientNames').setValue('');
//									mailform.getCmpByName('mail.copyToNames').setValue('');
//									mailform.getCmpByName('mail.content').setValue('');
//								} else {
//									var tabs = Ext.getCmp('centerTabPanel');
//									tabs.remove('MailForm');
//								}
//							});
							var tabs = Ext.getCmp('centerTabPanel');
							tabs.remove('MailForm');
						},
						failure : function(mailform, o) {
							Ext.ux.Toast.msg('错误信息', o.result.msg);
						}
					});
				}
			}
		}, {
			text : '重置',
			iconCls : 'reset',
			handler : function() {
				var mailForm = Ext.getCmp('MailFormPanel');
				mailForm.getForm().reset();
			}
		}, {
			text : '取消',
			iconCls : 'btn-mail_remove',
			handler : function() {
				var tabs = Ext.getCmp('centerTabPanel');
				tabs.remove('MailForm');
			}
		}]
	});
	return toolbar;
};
/**
 * 增加抄送控件
 * 
 * @return {}
 */
function copyFieldItems() {
	var items = [{
				xtype : 'label',
				text : '抄送人:',
				style : 'padding-left:0px;padding-top:3px;',
				width : 105
			}, {
				width : 405,
				fieldLabel : '抄送人姓名列表',
				name : 'mail.copyToNames',
				id : 'mail.copyToNames',
				readOnly : true
			}, {
				xtype : 'button',
				text : '选择抄送人',
				style : 'padding-left:5px;',
				iconCls : 'btn-mail_recipient',
				handler : function() {
					UserSelector.getView(function(userIds, fullnames) {
						var copyToIDs = Ext.getCmp('mail.copyToIDs');
						var copyToNames = Ext.getCmp('mail.copyToNames');
						copyToIDs.setValue(userIds);
						copyToNames.setValue(fullnames);
					},false).show();
				}
			}, {
				xtype : 'button',
				text : '取消抄送',
				style : 'padding-left:5px;',
				iconCls : 'btn-delete_copy',
				handler : function() {// 取消抄送时设置为空
					var copyField = Ext.getCmp('copyField');
					var mailForm = Ext.getCmp('mailFormPanel');
					mailForm.getForm().findField('mail.copyToIDs').setValue('');
					mailForm.getForm().findField('mail.copyToNames').setValue('');
					copyField.hide();
				}
			}];
	return items;
}
