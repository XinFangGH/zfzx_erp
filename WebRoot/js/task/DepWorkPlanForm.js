/**
 * @description 个人工作计划表单
 */
DepWorkPlanForm = Ext.extend(Ext.Window, {
	formPanel : null,
	buttons : null,
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		this.initUI();
		DepWorkPlanForm.superclass.constructor.call(this, {
			id : 'DepWorkPlanWin',
			iconCls : 'menu-depplan',
			layout : 'border',
			region : 'center',
			title : '我的计划详细信息',
			items : this.formPanel,
			modal : true,
			width : 770,
			minWidth : 750,
			height : 600,
			minHeight : 600,
			maximizable : true,
			keys : {
				key : Ext.EventObject.ENTER,
				fn : this.saveRecord,
				scope : this
			},
			buttonAlign : 'center',
			buttons : this.buttons
		});
	},
	initUI : function() {
		this.formPanel = new Ext.FormPanel({
			url : __ctxPath + '/task/saveWorkPlan.do',
			frame : false,
			border : false,
			layout : 'form',
			region : 'center',
			id : 'DepWorkPlanForm',
			defaults : {
				padding : '5'
			},
			// bodyStyle : 'padding-left:8%;padding-bottom:20px;',
			formId : 'DepWorkPlanFormId',
			items : [{
						name : 'workPlan.planId',
						id : 'planId',
						xtype : 'hidden',
						value : this.planId == null ? '' : this.planId
					}, {
						name : 'issueScopeIds',
						id : 'issueScopeIds',
						xtype : 'hidden'
					}, {
						name : 'participantsIds',
						id : 'participantsIds',
						xtype : 'hidden'
					}, {
						name : 'principalIds',
						id : 'principalIds',
						xtype : 'hidden'
					}, {
						name : 'workPlan.isPersonal',
						value : 0,
						xtype : 'hidden'
					}, {
						name : 'workPlan.proTypeId',
						xtype : 'hidden',
						id : 'proTypeId'
					}, {
						xtype : 'panel',
						layout : 'form',
						border : false,
						width : 680,
						defaultType : 'textfield',
						bodyStyle : 'padding-top:10px',
						defaults : {
							border : false
						},
						items : [{
							xtype : 'compositefield',
							fieldLabel : '计划类型',
							items : [{
										name : 'workPlan.typeName',
										id : 'typeName',
										xtype : 'textfield',
										width : 445,
										readOnly : true,
										allowBlank : false
									}, {
										xtype : 'button',
										text : '选择类型',
										iconCls : 'btn-select',
										handler : function() {
											new GlobalTypeSelector({
												catKey : 'DEPWORKPLAN',
												isSingle : true,
												callback : function(typeId, typeName) {
													Ext.getCmp('proTypeId').setValue(typeId);
													Ext.getCmp('typeName').setValue(typeName);
												}
											}).show();
										}

									}]
						}, {
							fieldLabel : '计划名称',
							name : 'workPlan.planName',
							width : 520,
							allowBlank : false,
							id : 'planName'
						}, {
							xtype : 'container',
							border : false,
							layout : 'hbox',
							layoutConfig : {
								padding : '0',
								align : 'middle'
							},
							defaults : {
								xtype : 'label',
								margins : {
									top : 0,
									right : 4,
									bottom : 4,
									left : 0
								}
							},
							items : [{
										text : '时间范围:',
										width : 101,
										style : 'padding-left:0px;padding-top:3px;'
									}, {
										xtype : 'datetimefield',
										width : 250,
										format : 'Y-m-d H:i:s',
										allowBlank : false,
										editable : false,
										name : 'workPlan.startTime',
										id : 'startTime'
									}, {
										text : ' 至 ',
										style : 'padding-left:0px;padding-top:3px;',
										width : 8
									}, {
										xtype : 'datetimefield',
										width : 253,
										format : 'Y-m-d H:i:s',
										allowBlank : false,
										editable : false,
										name : 'workPlan.endTime',
										id : 'endTime'
									}]
						}, {
							fieldLabel : '计划内容',
							name : 'workPlan.planContent',
							xtype : 'htmleditor',
							height : 180,
							width : 520,
							allowBlank : false,
							id : 'planContent'
						}, {
							layout : 'column',
							style : 'padding-left:0px;',
							width : 670,
							border : false,
							xtype : 'container',
							items : [{
									columnWidth : .83,
									border : false,
									style : 'padding-left:0px;',
									layout : 'form',
									items : [{
										fieldLabel : '附件',
										xtype : 'panel',
										frame : false,
										id : 'planFilePanel',
										height : 45,
										autoScroll : true,
										html : ''
									}]
								}, {
									columnWidth : .17,
									border : false,
									items : [{
										xtype : 'button',
										iconCls : 'menu-attachment',
										text : '添加附件',
										handler : function() {
											var dialog = App.createUploadDialog({
												file_cat : 'task/plan/depWorkPlan',
												callback : function(data) {
													var fileIds = Ext.getCmp("planFileIds");
													var filePanel = Ext.getCmp('planFilePanel');
													for (var i = 0; i < data.length; i++) {
														if (fileIds.getValue() != '') {
															fileIds.setValue(fileIds.getValue() + ',');
														}
														fileIds.setValue(fileIds.getValue() + data[i].fileId);
														Ext.DomHelper.append(filePanel.body,
															'<span><a href="#" onclick="FileAttachDetail.show('
																	+ data[i].fileId
																	+ ')">'
																	+ data[i].fileName
																	+ '</a> <img class="img-delete" src="'
																	+ __ctxPath
																	+ '/images/system/delete.gif" onclick="removeResumeFile(this,'
																	+ data[i].fileId
																	+ ')"/>&nbsp;|&nbsp;</span>');
													}
												}
											});
											dialog.show(this);
											}
										}, {
											xtype : 'button',
											iconCls : 'reset',
											text : '清除附件',
											handler : function() {
												var fileAttaches = Ext.getCmp("planFileIds");
												var filePanel = Ext.getCmp('planFilePanel');
												filePanel.body.update('');
												fileAttaches.setValue('');
											}
										}, {
											xtype : 'hidden',
											id : 'planFileIds',
											name : 'planFileIds'
										}]
									}]
						}, {
							xtype : 'container',
							id : 'IssueScopeContainer',
							// hidden:true,
							style : 'padding-left:0px;padding-bottom:3px;',
							layout : 'column',
							items : [{
										xtype : 'label',
										style : 'padding-left:0px;',
										text : '发布范围:',
										width : 105
									}, {
										xtype : 'textfield',
										name : 'workPlan.issueScope',
										id : 'issueScope',
										readOnly : true,
										width : 402
									}, {
										xtype : 'button',
										text : '选择部门',
										iconCls : 'btn-select',
										handler : function() {
											DepSelector.getView(
												function(id, name) {
													Ext.getCmp('issueScope').setValue(name);
													Ext.getCmp('issueScopeIds').setValue(id);
												}).show();
										}
									}, {
										xtype : 'button',
										text : '清除记录'
									}]
						}, {
							xtype : 'container',
							id : 'AttendContainer',
							// hidden:true,
							layout : 'column',
							style : 'padding-left:0px;padding-bottom:3px;',
							items : [{
										xtype : 'label',
										text : '参与人:',
										style : 'padding-left:0px;',
										width : 105
									}, {
										xtype : 'textfield',
										name : 'workPlan.participants',
										id : 'participants',
										readOnly : true,
										width : 402
									}, {
										xtype : 'button',
										text : '选择人员',
										iconCls : 'btn-select',
										handler : function() {
											UserSelector.getView(
												function(id, name) {
													Ext.getCmp('participants').setValue(name);
													Ext.getCmp('participantsIds').setValue(id);
												}).show();
										}
									}, {
										xtype : 'button',
										text : '清除记录'

									}]
						}, {
							xtype : 'container',
							layout : 'column',
							// hidden:true,
							id : 'PrincipalContainer',
							style : 'padding-left:0px;padding-bottom:3px;',
							items : [{
										xtype : 'label',
										text : '负责人:',
										style : 'padding-left:0px;',
										width : 105
									}, {
										xtype : 'textfield',
										name : 'workPlan.principal',
										id : 'principal',
										readOnly : true,
										width : 402
									}, {
										xtype : 'button',
										text : '选择人员',
										iconCls : 'btn-select',
										handler : function() {
											UserSelector.getView(
												function(id, name) {
													Ext.getCmp('principal').setValue(name);
													Ext.getCmp('principalIds').setValue(id);
												}).show();
										}

									}, {
										xtype : 'button',
										text : '清除记录'

									}]
						}, {
							xtype : 'radiogroup',
							fieldLabel : '是否启用',
							autoHeight : true,
							columns : 2,
							width : 520,
							items : [{
										boxLabel : '是',
										name : 'workPlan.status',
										inputValue : 1,
										id : 'status1',
										checked : true
									}, {
										boxLabel : '否',
										name : 'workPlan.status',
										inputValue : 0,
										id : 'status0'
									}]
						}, {
							fieldLabel : '标识',
							hiddenName : 'workPlan.icon',
							id : 'icon',
							xtype : 'iconcomb',
							mode : 'local',
							allowBlank : false,
							width : 520,
							editable : false,
							store : new Ext.data.SimpleStore({
										fields : ['flagStyle', 'flagName'],
										data : [['ux-flag-blue', '日常计划'],
												['ux-flag-orange', '重要计划'],
												['ux-flag-green', '特殊计划'],
												['ux-flag-pink', '个人计划'],
												['ux-flag-red', '紧急计划'],
												['ux-flag-purple', '部门计划'],
												['ux-flag-yellow', '待定计划']]
									}),
							valueField : 'flagStyle',
							displayField : 'flagName',
							// iconClsField : 'flagClass',
							triggerAction : 'all',
							value : 'ux-flag-blue'
						}, {
							fieldLabel : '备注',
							name : 'workPlan.note',
							xtype : 'textarea',
							id : 'note',
							width : 520,
							height : 50
						}]
					}]
		});

		this.buttons = [{
					text : '保存',
					iconCls : 'btn-save',
					scope : this,
					handler : this.saveRecord
				}, {
					text : '关闭',
					iconCls : 'close',
					scope : this,
					handler : this.closeWin
				}];

		if (this.planId != '' && this.planId != null
				&& this.planId != undefined) {
			this.formPanel.getForm().load({
				url : __ctxPath + '/task/getWorkPlan.do?planId=' + this.planId,
				waitMsg : '正在载入数据...',
				success : function(form, action) {
					var json = Ext.util.JSON
							.decode(action.response.responseText);
					var workPlan = json.data;
					var typeId = json.proTypeId;
					Ext.getCmp('proTypeId').setValue(typeId);
					var startTime = workPlan.startTime;
					var status = workPlan.status;
					// var isPersonal = workPlan.isPersonal;
					Ext.getCmp('status' + status).setValue(true);
					// Ext.getCmp('isPersonal' + isPersonal).setValue(true);
					var endTime = workPlan.endTime;
					// var typeId = action.result.data.planType.typeId;
					// Ext.getCmp('typeId').setValue(typeId);
					var af = workPlan.planFiles;
					Ext.getCmp('startTime')
							.setValue(new Date(getDateFromFormat(startTime,
									"yyyy-MM-dd H:mm:ss")));
					Ext.getCmp('endTime').setValue(new Date(getDateFromFormat(
							endTime, "yyyy-MM-dd H:mm:ss")));
					// var af = workPlan.planFiles;
					//						
					var filePanel = Ext.getCmp('planFilePanel');
					var fileIds = Ext.getCmp("planFileIds");
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
									+ '/images/system/delete.gif" onclick="removeResumeFile(this,'
									+ af[i].fileId
									+ ')"/>&nbsp;|&nbsp;</span>');
					}

				},
				failure : function(form, action) {
					Ext.ux.Toast.msg('编辑', '载入失败');
				}
			});
		}
	},
	saveRecord : function() {
		var fp = this.formPanel;
		var win = this;
		var issueScope = Ext.getCmp('issueScope').getValue();// 发布范围
		var participants = Ext.getCmp('participants').getValue();// 参与人
		var principal = Ext.getCmp('principal').getValue();// 负责人
		if ((issueScope != '') || participants != '') {
			if (principal != '') {
				if (fp.getForm().isValid()) {
					fp.getForm().submit({
								method : 'post',
								waitMsg : '正在提交数据...',
								success : function(fp, action) {
									Ext.ux.Toast.msg('操作信息', '成功保存信息！');
									Ext.getCmp('DepWorkPlanGrid').getStore()
											.reload();
									win.close();
								},
								failure : function(fp, action) {
									Ext.MessageBox.show({
												title : '操作信息',
												msg : '信息保存出错，请联系管理员！',
												buttons : Ext.MessageBox.OK,
												icon : 'ext-mb-error'
											});
									win.close();
								}
							});
				}
			} else {
				Ext.ux.Toast.msg('操作提示', '负责人为必填!');
			}
		} else {
			Ext.ux.Toast.msg('操作提示', '发布范围，参与人至少填写一项!');
		};
	},// save method over
	closeWin : function() {
		this.close();
	}
});
/**
* 上传文件删除
*/
function removeResumeFile(obj, fileId) {
	var fileIds = Ext.getCmp("planFileIds");
	var value = fileIds.getValue();
	if (value.indexOf(',') < 0) {// 仅有一个附件
		fileIds.setValue('');
	} else {
		value = value.replace(',' + fileId, '').replace(fileId + ',', '');
		fileIds.setValue(value);
	}
	var el = Ext.get(obj.parentNode);
	el.remove();
};