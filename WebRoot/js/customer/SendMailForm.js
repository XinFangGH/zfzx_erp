/**
 * @author
 * @createtime
 * @class SendMailForm
 * @extends Ext.Window
 *  发送邮件
 * @param {} ids 
 * @param {} names
 * @param {} type  0＝给客户发，1＝发供应商发
 * @description SendMailForm表单
 * @company 智维软件
 */
SendMailForm = Ext.extend(Ext.Window, {
	// 内嵌FormPanel
	formPanel : null,
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		// 必须先初始化组件
		this.initUIComponents();
		SendMailForm.superclass.constructor.call(this, {
					layout : 'fit',
					title : '邮件发送',
					iconCls:'btn-mail_send',
					id : 'SendMailFormWin',
					width : 650,
					height : 530,
					minWidth : 649,
					minHeight : 529,
					items:this.formPanel,
					maximizable : true,
					border : false,
					modal : true,
					plain : true,
					buttonAlign : 'center',
					buttons : this.buttons
				});
	},// end of the constructor
	// 初始化组件
	initUIComponents : function() {
		//初始化form表单
		this.formPanel = new Ext.FormPanel({
		id : 'CmailForm',
		frame : false,
		bodyStyle : 'padding:5px;',
		defaultType : 'textarea',
		url : __ctxPath + '/customer/send.do',
		method : 'post',
		modal : true,
		layout : 'form',
		plain : true,
		scope : this,
		labelWidth : 60,
		defaults : {
			labelWidth : 60
		},
		items : [{
					xtype : 'hidden',
					id : 'provideIds',
					name : 'customerMail.ids',
					value : this.ids
				},{
				   xtype : 'hidden',
				   id : 'type',
				   name : 'customerMail.type',
				   value : this.type
				}, {
					xtype : 'textfield',
					id : 'receivedName',
					anchor : '98%',
					name : 'customerMail.names',
					fieldLabel : '收件人',
					readOnly:true,
					value : this.names
				}, {
					xtype : 'textfield',
					id : 'subject',
					name : 'customerMail.subject',
					anchor : '98%',
					allowBlank:false,
					fieldLabel : '主题'
				}, {
					xtype : 'htmleditor',
					id : 'content',
					name : 'customerMail.mailContent',
					anchor : '98%',
					height : 300,
					fieldLabel : '内容'
				},{
					layout : 'column',
					xtype : 'container',
					border : false,
					items : [{
								columnWidth : .7,
								border : false,
								layout : 'form',
								items : [{
											fieldLabel : '附件',
											xtype : 'panel',
											id : 'providerFilePanel',
											frame : false,
											height : 80,
											autoScroll : true,
											html : ''
										}]
							}, {
								columnWidth : .3,
								border : false,
								items : [{
									xtype : 'button',
									iconCls : 'btn-add',
									text : '添加附件',
									handler : function() {
										var dialog = App.createUploadDialog({
											file_cat : 'customer',
											callback : function(data) {
												var fileIds = Ext
														.getCmp("providerfileIds");
												var filePanel = Ext
														.getCmp('providerFilePanel');

												for (var i = 0; i < data.length; i++) {
													if (fileIds.getValue() != '') {
														fileIds
																.setValue(fileIds
																		.getValue()
																		+ ',');
													}
													fileIds.setValue(fileIds
															.getValue()
															+ data[i].fileId);
													Ext.DomHelper
															.append(
																	filePanel.body,
																	'<span><a href="#" onclick="FileAttachDetail.show('
																			+ data[i].fileId
																			+ ')">'
																			+ data[i].fileName
																			+ '</a> <img class="img-delete" src="'
																			+ __ctxPath
																			+ '/images/system/delete.gif" onclick="removeFile(this,'
																			+ data[i].fileId
																			+ ')"/>&nbsp;|&nbsp;</span>');
												}
											}
										});
										dialog.show(this);
									}
								}, {
									xtype : 'button',
									iconCls : 'btn-del',
									text : '清除附件',
									handler : function() {
										var fileAttaches = Ext
												.getCmp("providerfileIds");
										var filePanel = Ext
												.getCmp('providerFilePanel');

										filePanel.body.update('');
										fileAttaches.setValue('');
									}
								}]
							}]
				}, {
					xtype : 'hidden',
					id : 'providerfileIds',
					name : 'customerMail.files'
				}]
				
	});

	this.formPanel.getForm().load({
		deferredRender : false,
		url : __ctxPath + '/customer/loadVm.do',
		waitMsg : '正在载入数据...',
		success : function(form, action) {
			var htmlcontent = Ext.util.JSON.decode(action.response.responseText).data;
			Ext.getCmp('content').setValue(htmlcontent);
		},
		failure : function(form, action) {
			var result = action.result;
			Ext.ux.Toast.msg("信息提示",result.message);
			Ext.getCmp('SendMailFormWin').close();
		}
	});
		
		this.buttons = [ {
	 			text : '发送',
	 			iconCls:'btn-mail_send',
	 			handler : function() {
	 				var message = Ext.getCmp('CmailForm');
	 				if (message.getForm().isValid()) {
	 					message.getForm().submit( {
	 						waitMsg : '正在 发送信息',
	 						success : function(message, o) {
	 						    var win = Ext.getCmp('SendMailFormWin');
	 						    win.close();
	 							Ext.ux.Toast.msg('操作信息', '邮件发送成功！');
	 						},
	 						failure :function(message, o){
	 							Ext.ux.Toast.msg('错误信息',o.result.message);
	 						}
	 					});
	 				}
	 			}

	 		}, {
	 			text : '关闭',
	 			iconCls:'close',
	 			handler : function() {
	 				var win = Ext.getCmp('SendMailFormWin');
	 				win.close();
	 			}
	 		} ];//end of the buttons
	}
});