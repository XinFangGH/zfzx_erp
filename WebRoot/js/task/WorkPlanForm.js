var WorkPlanForm = function(planId) {
	this.planId = planId;
	var fp = this.setup();
	var window = new Ext.Window({
				id : 'WorkPlanFormWin',
				title : '工作计划详细信息',
				width : 880,
				height : 450,
				modal : true,
				layout : 'anchor',
				plain : true,
				bodyStyle : 'padding:5px;',
				buttonAlign : 'center',
				items : [this.setup()],
				buttons : [{
					text : '保存',
					iconCls : 'btn-save',
					handler : function() {
						var fp = Ext.getCmp('WorkPlanForm');
						if (fp.getForm().isValid()) {
							fp.getForm().submit({
								method : 'post',
								waitMsg : '正在提交数据...',
								success : function(fp, action) {
									Ext.ux.Toast.msg('操作信息', '成功保存信息！');
									Ext.getCmp('WorkPlanGrid').getStore()
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
					iconCls : 'btn-cancel',
					handler : function() {
						window.close();
					}
				}]
			});
	window.show();
};

WorkPlanForm.prototype.setup = function() {
	var formPanel = new Ext.FormPanel({
		url : __ctxPath + '/task/saveWorkPlan.do',
		layout : 'column',
		id : 'WorkPlanForm',
		frame : true,
		defaults : {
			// width : 400,
			anchor : '98%,98%'
		},
		formId : 'WorkPlanFormId',
		defaultType : 'textfield',
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
					layout : 'form',
					defaultType : 'textfield',
					columnWidth : .5,
					xtype : 'container',
					labelWidth : 60,
					items : [{
								fieldLabel : '计划名称',
								name : 'workPlan.planName',
								width : 348,
								id : 'planName'
							}, {
								xtype : 'container',
								height : 26,
								layout : 'column',
								style : 'padding-left:0px;',
								defaultType : 'label',
								items : [{
											text : '时间范围:',
											width : 61,
											style : 'padding-left:0px;padding-top:3px;'
										}, {
											text : '从',
											style : 'padding-left:0px;padding-top:3px;'
										}, {
											xtype : 'datefield',
											width : 149,
											format : 'Y-m-d',
											editable : false,
											name : 'workPlan.startTime',
											id : 'startTime'
										}, {
											text : '至',
											style : 'padding-left:0px;padding-top:3px;'
										}, {
											xtype : 'datefield',
											width : 149,
											format : 'Y-m-d',
											editable : false,
											name : 'workPlan.endTime',
											id : 'endTime'
										}]
							}, {
								fieldLabel : '计划内容',
								name : 'workPlan.planContent',
								xtype : 'htmleditor',
								height : 250,
								width : 348,
								id : 'planContent'
							}, {
								layout : 'column',
								style : 'padding-left:0px;',
								width : 500,
								xtype : 'container',
								items : [{
											columnWidth : .7,
											style : 'padding-left:0px;',
											layout : 'form',
											items : [{
														fieldLabel : '附件',
														xtype : 'panel',
														id : 'planFilePanel',
														frame : true,
														height : 80,
														autoScroll : true,
														html : ''
													}]
										}, {
											columnWidth : .3,
											items : [{
												xtype : 'button',
												text : '添加附件',
												handler : function() {
													var dialog = App
															.createUploadDialog(
																	{
																		file_cat : 'task',
																		callback : function(
																				data) {
																			var fileIds = Ext
																					.getCmp("planFileIds");
																			var filePanel = Ext
																					.getCmp('planFilePanel');

																			for (var i = 0; i < data.length; i++) {
																				if (fileIds
																						.getValue() != '') {
																					fileIds
																							.setValue(fileIds
																									.getValue()
																									+ ',');
																				}
																				fileIds
																						.setValue(fileIds
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
																										+ '/images/system/delete.gif" onclick="removePlanFile(this,'
																										+ data[i].fileId
																										+ ')"/>&nbsp;|&nbsp;</span>');
																			}
																		}
																	});
													dialog.show(this);
												}
											}, {
												xtype : 'button',
												text : '清除附件',
												handler : function() {
													var fileAttaches = Ext
															.getCmp("planFileIds");
													var filePanel = Ext
															.getCmp('planFilePanel');

													filePanel.body.update('');
													fileAttaches.setValue('');
												}
											}, {
												xtype : 'hidden',
												id : 'planFileIds',
												name : 'planFileIds'
											}]
										}]
							}]
				}, {
					layout : 'form',
					columnWidth : .5,
					xtype : 'container',
					items : [{
								fieldLabel : '计划类型',
								hiddenName : 'workPlan.planType.typeId',
								xtype : 'combo',
								width : 273,
								editable : false,
								triggerAction : 'all',
								id : 'typeId',
								displayField : 'name',
								valueField : 'id',
								mode : 'local',
								store : new Ext.data.SimpleStore({
											autoLoad : true,
											url : __ctxPath
													+ '/task/comboPlanType.do',
											fields : ['id', 'name']
										})
							}, {
								xtype : 'container',
								style : 'padding-left:0px;padding-bottom:3px;',
								layout : 'column',
								items : [{
											xtype : 'label',
											style : 'padding-left:0px;',
											text : '发布范围:',
											width : 101
										}, {
											xtype : 'textfield',
											name : 'workPlan.issueScope',
											id : 'issueScope',
											readOnly : true
										}, {
											xtype : 'button',
											text : '选择部门',
											iconCls : 'btn-select',
											handler : function() {
												DepSelector.getView(
														function(id, name) {

															Ext
																	.getCmp('issueScope')
																	.setValue(name);
															Ext
																	.getCmp('issueScopeIds')
																	.setValue(id);
														}).show();
											}
										}, {
											xtype : 'button',
											text : '清除记录'
										}]
							}, {
								xtype : 'container',
								layout : 'column',
								style : 'padding-left:0px;padding-bottom:3px;',
								items : [{
											xtype : 'label',
											text : '参与人:',
											style : 'padding-left:0px;',
											width : 101
										}, {
											xtype : 'textfield',
											name : 'workPlan.participants',
											id : 'participants',
											readOnly : true
										}, {
											xtype : 'button',
											text : '选择人员',
											iconCls : 'btn-select',
											handler : function() {
												UserSelector.getView(
														function(id, name) {
															Ext
																	.getCmp('participants')
																	.setValue(name);
															Ext
																	.getCmp('participantsIds')
																	.setValue(id);
														}).show();
											}
										}, {
											xtype : 'button',
											text : '清除记录'

										}]
							}, {
								xtype : 'container',
								layout : 'column',
								style : 'padding-left:0px;padding-bottom:3px;',
								items : [{
											xtype : 'label',
											text : '负责人:',
											style : 'padding-left:0px;',
											width : 101
										}, {
											xtype : 'textfield',
											name : 'workPlan.principal',
											id : 'principal',
											readOnly : true
										}, {
											xtype : 'button',
											text : '选择人员',
											iconCls : 'btn-select',
											handler : function() {
												UserSelector.getView(
														function(id, name) {
															Ext
																	.getCmp('principal')
																	.setValue(name);
															Ext
																	.getCmp('principalIds')
																	.setValue(id);
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
								xtype : 'radiogroup',
								fieldLabel : '是否为个人计划',
								autoHeight : true,
								columns : 2,
								items : [{
											boxLabel : '个人',
											name : 'workPlan.isPersonal',
											inputValue : 0,
											id : 'isPersonal0',
											checked : true
										}, {
											boxLabel : '部门',
											name : 'workPlan.isPersonal',
											inputValue : 1,
											id : 'isPersonal1'
										}]
							}, {
								fieldLabel : '图标',
								hiddenName : 'workPlan.icon',
								id : 'icon',
								xtype : 'iconcomb',
								mode : 'local',
								anchor : '92.5%',
								allowBlank : false,
								editable : false,
								store : new Ext.data.SimpleStore({
									fields : ['countryCode', 'countryName'],
									data : [
											['ux-flag-de', '日常计划'],
											['ux-flag-us', '重要计划'],

											['ux-flag-fr', '特殊计划']]
								}),
								valueField : 'countryCode',
								displayField : 'countryName',
								triggerAction : 'all',
								mode : 'local'
							}, {
								fieldLabel : '备注',
								name : 'workPlan.note',
								xtype : 'textarea',
								id : 'note',
								width : 273,
								height : 180
							}]
				}]
	});

	if (this.planId != null && this.planId != 'undefined') {
		formPanel.getForm().load({
			deferredRender : false,
			url : __ctxPath + '/task/getWorkPlan.do?planId=' + this.planId,

			waitMsg : '正在载入数据...',
			success : function(form, action) {
				// var
				// workPlan=Ext.util.JSON.decode(action.response.responseText).data[0];
				var startTime = action.result.data.startTime;
				var status = action.result.data.status;
				var isPersonal = action.result.data.isPersonal;
				Ext.getCmp('status' + status).setValue(true);
				Ext.getCmp('isPersonal' + isPersonal).setValue(true);
				var endTime = action.result.data.endTime;
				var typeId = action.result.data.planType.typeId;
				Ext.getCmp('typeId').setValue(typeId);
				var af = action.result.data.planFiles;
				Ext.getCmp('startTime').setValue(new Date(getDateFromFormat(
						startTime, "yyyy-MM-dd H:mm:ss")));
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
					Ext.DomHelper
							.append(
									filePanel.body,
									'<span><a href="#" onclick="FileAttachDetail.show('
											+ af[i].fileId
											+ ')">'
											+ af[i].fileName
											+ '</a><img class="img-delete" src="'
											+ __ctxPath
											+ '/images/system/delete.gif" onclick="removePlanFile(this,'
											+ af[i].fileId
											+ ')"/>&nbsp;|&nbsp;</span>');
				}

			},
			failure : function(form, action) {
				Ext.ux.Toast.msg('编辑', '载入失败');
			}
		});
	}
	return formPanel;

};

function removePlanFile(obj, fileId) {
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