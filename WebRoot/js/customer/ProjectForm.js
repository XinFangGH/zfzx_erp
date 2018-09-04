Ext.ns('ProjectForm');
/**
 * @author
 * @createtime
 * @class ProjectForm
 * @extends Ext.Window
 * @description ProjectForm表单
 * @company 智维软件
 */
ProjectForm = Ext.extend(Ext.Window, {
	// 内嵌FormPanel
	formPanel : null,
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		// 必须先初始化组件
		this.initUIComponents();
		ProjectForm.superclass.constructor.call(this, {
			layout : 'fit',
			id : 'ProjectFormWin',
			title : '项目详细信息',
			iconCls : 'menu-project',
			items : this.formPanel,
			maximizable : true,
			border : false,
			width : 873,
			height : 485,
			minWidth : 872,
			minHeight : 484,
			modal : true,
			plain : true,
			bodyStyle : 'padding:5px;',
			buttonAlign : 'center',
			buttons : this.buttons
		});
	},// end of the constructor
	// 初始化组件
	initUIComponents : function() {
		this.formPanel = new Ext.FormPanel({
			url : __ctxPath + '/customer/saveProject.do',
			layout : 'hbox',
			layoutConfig : {
				padding : '5',
				align : 'middle'
			},
			defaults : {
				margins : '0 5 0 0'
			},
			id : 'ProjectForm',
			frame : false,
			formId : 'ProjectFormId',
			defaultType : 'textfield',
			items : [{
				xtype : 'fieldset',
				title : '项目信息',
				layout : 'form',
				width : 420,
				defaultType : 'textfield',
				labelWidth : 80,
				defaults : {
					width : 295
				},
				items : [{
					xtype : 'compositefield',
					fieldLabel : '项目编号*',
					items : [{
								xtype : 'textfield',
								width : 150,
								name : 'project.projectNo',
								id : 'projectNo',
								allowBlank : false,
								blankText : '项目编号不能为空!',
								listeners : {
									change : function(projectNo, newvalue, oldvalue) {
										ProjectForm.checkProjectNo(newvalue);
									}
								}
							}, {
								id : 'getNoButton',
								xtype : 'button',
								text : '系统生成',
								iconCls : 'btn-system-setting',
								handler : function() {
									Ext.Ajax.request({
										url : __ctxPath + '/customer/numberProject.do',
										success : function(response) {
											var result = Ext.util.JSON.decode(response.responseText);
											Ext.getCmp('projectNo').setValue(result.projectNo);
											Ext.getCmp('CheckMessage').body.update('');
										}
									});
								}
							}, {
								id : 'CheckMessage',
								xtype : 'panel',
								border : false,
								style : 'padding-top:3px'
							}, {
								name : 'project.projectId',
								id : 'projectId',
								xtype : 'hidden',
								value : this.projectId == null ? '' : this.projectId
							}]
				}, {
					fieldLabel : '项目名称*',
					name : 'project.projectName',
					id : 'projectName',
					allowBlank : false,
					blankText : '项目名称不能为空!'
				}, {
					fieldLabel : '需求描述',
					name : 'project.reqDesc',
					xtype : 'htmleditor',
					autoHeight : true,
					id : 'reqDesc'
				}, {
					fieldLabel : '是否签订合同',
					hiddenName : 'project.isContract',
					id : 'isContract',
					xtype : 'combo',
					mode : 'local',
					editable : true,
					triggerAction : 'all',
					store : [['0', '无'], ['1', '有']],
					value : 0
				}, {
					fieldLabel : '业务人员',
					name : 'project.userId',
					id : 'userId',
					xtype : 'hidden'
				}, {
					xtype : 'compositefield',
					fieldLabel : '业务人员*',
					items : [{
								xtype : 'textfield',
								width : 180,
								readOnly : true,
								allowBlank : false,
								blankText : '业务人员不能为空!',
								name : 'salesman',
								id : 'salesman'
							}, {
								xtype : 'button',
								width : 60,
								text : '业务人员',
								iconCls : 'btn-mail_recipient',
								handler : function() {
									UserSelector.getView(
										function(userIds, fullnames) {
											Ext.getCmp('salesman').setValue(fullnames);
											Ext.getCmp('userId').setValue(userIds);
										}, true).show();
								}
							}]
				}, {
					xtype : 'compositefield',
					fieldLabel : '项目附件',
					items : [{
								xtype : 'panel',
								id : 'displayProjectAttach',
								width : 215,
								height : 65,
								frame : false,
								autoScroll : true,
								style : 'padding-left:0px;padding-top:0px;',
								html : ''
							}, {
								xtype : 'button',
								iconCls : 'btn-upload',
								text : '上传',
								handler : function() {
									var dialog = App.createUploadDialog({
										file_cat : 'customer/project',
										callback : uploadProjectAttach
									});
									dialog.show('queryBtn');
								}
							}, {
								xtype : 'hidden',
								name : 'projectAttachIDs',
								id : 'projectAttachIDs'
							}]
				}]
			}, {
				xtype : 'fieldset',
				title : '负责人信息',
				layout : 'form',
				width : 420,
				labelWidth : 80,
				defaultType : 'textfield',
				defaults : {
					width : 295
				},
				items : [{
							fieldLabel : '所属客户',
							name : 'project.customerId',
							id : 'customerId',
							xtype : 'hidden'
						}, {
							xtype : 'compositefield',
							fieldLabel : '所属客户*',
							items : [{
										xtype : 'textfield',
										width : 180,
										readOnly : true,
										allowBlank : false,
										blankText : '所属客户为必选!',
										name : 'customerName',
										id : 'customerName'
									}, {
										xtype : 'button',
										text : '选择客户',
										iconCls : 'menu-customerView',
										handler : function() {
											CustomerSelector.getView(
												function(customerId, customerName) {
													Ext.getCmp('customerId').setValue(customerId);
													Ext.getCmp('customerName').setValue(customerName);
													Ext.getCmp('fullname').getStore().reload({
														params : {
															'Q_customer.customerId_L_EQ' : customerId
														}
													});
												}, true).show();
										}
									}]
						}, {
							fieldLabel : '联系人姓名',
							allowBlank : false,
							blankText : '联系人不能为空!',
							name : 'project.fullname',
							id : 'fullname',
							xtype : 'combo',
							mode : 'local',
							editable : true,
							triggerAction : 'all',
							store : new Ext.data.SimpleStore({
								method : 'post',
								url : __ctxPath + '/customer/findCusLinkman.do',
								fields : ['linkmanId', 'fullname']
							}),
							displayField : 'fullname',
							valueField : 'linkmanId',
							enableKeyEvent : true,
							listeners : {
								select : function(combo, record, index) {
									ProjectForm.selectLinkman(combo.value);
								}
							}
						}, {
							fieldLabel : '手机',
							name : 'project.mobile',
							id : 'mobile'
						}, {
							fieldLabel : '电话',
							name : 'project.phone',
							id : 'phone'
						}, {
							fieldLabel : '传真',
							name : 'project.fax',
							id : 'fax'
						}, {
							fieldLabel : '其他联系方式',
							xtype : 'htmleditor',
							height : 219,
							name : 'project.otherContacts',
							id : 'otherContacts'
						}]
			}]
		});// end of the formPanel

		if (this.projectId != null && this.projectId != 'undefined') {
			this.formPanel.getForm().load({
				deferredRender : false,
				url : __ctxPath + '/customer/getProject.do?projectId='
						+ this.projectId,
				waitMsg : '正在载入数据...',
				success : function(form, action) {
					Ext.getCmp('userId').setValue(action.result.userId);
					Ext.getCmp('customerName')
							.setValue(action.result.customerName);
					Ext.getCmp('salesman').setValue(action.result.salesman);
					Ext.getCmp('customerId').setValue(action.result.customerId);

					// 载入附件
					var af = action.result.data.projectFiles;
					var filePanel = Ext.getCmp('displayProjectAttach');
					var fileIds = Ext.getCmp("projectAttachIDs");
					for (var i = 0; i < af.length; i++) {
						if (fileIds.getValue() != '') {
							fileIds.setValue(fileIds.getValue() + ',');
						}
						fileIds.setValue(fileIds.getValue() + af[i].fileId);
						Ext.DomHelper.append(filePanel.body,
							'<span><a href="#" onclick="FileAttachDetail.show('
									+ af[i].fileId
									+ ')">'
									+ af[i].fileName
									+ '</a><img class="img-delete" src="'
									+ __ctxPath
									+ '/images/system/delete.gif" onclick="removeProjectAttach(this,'
									+ af[i].fileId
									+ ')"/>&nbsp;|&nbsp;</span>');
					}
				},
				failure : function(form, action) {
					Ext.ux.Toast.msg('编辑', '载入失败');
				}
			});
		}

		this.buttons = [{
			text : '保存',
			iconCls : 'btn-save',
			handler : function() {
				var fp = Ext.getCmp('ProjectForm');
				if (fp.getForm().isValid()) {
					fp.getForm().submit({
						method : 'post',
						waitMsg : '正在提交数据...',
						success : function(fp, action) {
							var isContract = Ext.getCmp('isContract').value;
							if (isContract == 0) {
								Ext.ux.Toast.msg('操作信息', '成功保存信息！');
							} else {
								if (Ext.getCmp('projectId').getValue() == null
										|| Ext.getCmp('projectId').getValue() == '') {
									Ext.Msg.confirm('操作信息','信息保存成功,是否现在录入合同信息？',
										function(btn) {
											if (btn == 'yes') {
												new ContractForm().show();
												Ext.getCmp('projectId').setValue(action.result.projectId);
												ContractForm.getProject(action.result.projectId);
											}
										});
								}
							}
							Ext.getCmp('ProjectGrid').getStore().reload();
							Ext.getCmp('ProjectFormWin').close();
						},
						failure : function(fp, action) {
							Ext.MessageBox.show({
										title : '操作信息',
										msg : action.result.msg,
										buttons : Ext.MessageBox.OK,
										icon : 'ext-mb-error'
									});
						}
					});
				}
			}
		}, {
			text : '取消',
			iconCls : 'btn-cancel',
			handler : function() {
				Ext.getCmp('ProjectFormWin').close();
			}
		}];// end of the buttons
	}// end of the initUIComponents
});

/**
 * 填写项目编号后,检测项目编号的方法
 * 
 * @param {}
 *            newvalue
 */
ProjectForm.checkProjectNo = function(newvalue) {
	if (newvalue != null && newvalue != '' && newvalue != 'undefined') {
		Ext.Ajax.request({
					url : __ctxPath + '/customer/checkProject.do',
					params : {
						projectNo : newvalue
					},
					method : 'post',
					success : function(response) {
						var result = Ext.util.JSON.decode(response.responseText);
						if (result.pass) {
							Ext.getCmp('CheckMessage').body.update('<img src="' + __ctxPath
											+ '/images/flag/customer/passcheck.png" />可用');
						} else {
							Ext.getCmp('CheckMessage').body.update('<img src="'
											+ __ctxPath + '/images/flag/customer/invalid.png" />不可用');
						}
					},
					failure : function() {
						// .......
					}
				});
	}
};
/**
 * 选择联系人时加载联系方式的方法
 * 
 * @param {}
 *            linkmanId
 */
ProjectForm.selectLinkman = function(linkmanId) {
	Ext.Ajax.request({
				url : __ctxPath + '/customer/getCusLinkman.do',
				method : 'post',
				params : {
					linkmanId : linkmanId
				},
				success : function(response) {
					var result = Ext.util.JSON.decode(response.responseText).data;
					Ext.getCmp('mobile').setValue(result.mobile);
					Ext.getCmp('phone').setValue(result.phone);
					Ext.getCmp('fax').setValue(result.fax);
					Ext.getCmp('otherContacts').setValue('E-mail:'
							+ result.email + '<br/>MSN:' + result.msn
							+ '<br/>QQ:' + result.qq + '<br/>联系地址:'
							+ result.homeAddress + '<br/>邮编:' + result.homeZip);// 这里往后改成换行的
				},
				failure : function() {
					Ext.ux.Toast.msg('错误信息', '载入出错!');
				}
			});
};

/**
 * 上传合同附件
 */
function uploadProjectAttach(data) {
	var fileIds = Ext.getCmp('projectAttachIDs');
	var display = Ext.getCmp('displayProjectAttach');
	for (var i = 0; i < data.length; i++) {
		if (fileIds.getValue() != '') {
			fileIds.setValue(fileIds.getValue() + ',');
		}
		fileIds.setValue(fileIds.getValue() + data[i].fileId);
		Ext.DomHelper.append(display.body,
			'<span><a href="#" onclick="FileAttachDetail.show('
					+ data[i].fileId
					+ ')">'
					+ data[i].fileName
					+ '</a><img class="img-delete" src="'
					+ __ctxPath
					+ '/images/system/delete.gif" onclick="removeProjectAttach(this,'
					+ data[i].fileId + ')"/>&nbsp;|&nbsp;</span>');
	}
}
/**
 * 删除合同附件
 * 
 * @param {}
 *            obj
 * @param {}
 *            _fileId
 */
function removeProjectAttach(obj, _fileId) {
	var fileIds = Ext.getCmp("projectAttachIDs");
	var value = fileIds.getValue();
	if (value.indexOf(',') < 0) {// 仅有一个附件
		fileIds.setValue('');
	} else {
		value = value.replace(',' + _fileId, '').replace(_fileId + ',', '');
		fileIds.setValue(value);
	}

	var el = Ext.get(obj.parentNode);
	el.remove();

	var projectId = Ext.getCmp('projectId').value;
	if (projectId != null && projectId != '' && projectId != 'undefined') {
		Ext.Ajax.request({
					url : __ctxPath + '/customer/removeFileProject.do',
					params : {
						projectId : projectId,
						projectAttachIDs : _fileId
					},
					method : 'post',
					success : function() {
						Ext.Ajax.request({
									url : __ctxPath
											+ '/system/multiDelFileAttach.do',
									params : {
										ids : _fileId
									},
									method : 'post',
									success : function() {
										Ext.ux.Toast.msg("信息提示", "成功删除所选记录！");
									}
								});
					}
				});
	} else {
		Ext.Ajax.request({
			url : __ctxPath + '/system/multiDelFileAttach.do',
			params : {
				ids : _fileId
			},
			method : 'post',
			success : function() {
				Ext.ux.Toast.msg("信息提示", "成功删除所选记录！");
			}
		});
	}

};