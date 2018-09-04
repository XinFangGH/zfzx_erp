var NewWorkPlanForm = function(wId, wName) {
	
	var formPanel=this.setup(wId, wName);
	var toolbar = this.initToolbar();
	var topPanel=new Ext.Panel({
		layout:'fit',
		iconCls : 'menu-newplan',
		id : 'NewWorkPlanForm',
		title : wName == null ? '新建工作计划' : wName,
		tbar:toolbar,
		autoScroll:true,
		items:[formPanel]
	});
	
	return topPanel;
};

NewWorkPlanForm.prototype.setup = function(wId, wName) {
	this.planId = wId;
	var formPanel = new Ext.FormPanel({
		url : __ctxPath + '/task/saveWorkPlan.do',
		autoHeight:true,
		frame:false,
		border:false,
		layout : 'column',
		id:'NewWorkPlanFormPanel',
		bodyStyle : 'padding-left:8%;padding-bottom:20px;',		
		formId : 'NewWorkPlanFormId',
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
				},{
				xtype:'panel',
				layout:'form',
				border:false,
				width:680,
				defaultType : 'textfield',
				bodyStyle:'padding-top:10px',
				defaults:{border:false},
				items:[{
					xtype : 'radiogroup',
					id:'PersonalOr',
					fieldLabel : '是否为个人计划',
					autoHeight : true,
					columns : 2,
					width : 520,
					items : [{
								boxLabel : '个人',
								name : 'workPlan.isPersonal',
								inputValue : 1,
								id : 'isPersonal1',
								checked : true,
								handler:function(value){
								  if(value.getValue()==true){
								    Ext.getCmp('IssueScopeContainer').hide();
								    Ext.getCmp('AttendContainer').hide();
								    Ext.getCmp('PrincipalContainer').hide();
								  }else{
								    Ext.getCmp('IssueScopeContainer').show();
								    Ext.getCmp('AttendContainer').show();
								    Ext.getCmp('PrincipalContainer').show();
								  }
								}
							}
							, {
								boxLabel : '部门',
								name : 'workPlan.isPersonal',
								inputValue : 0,
								hidden:true,
								id : 'isPersonal0'
							}
							]
				},{
					fieldLabel : '计划类型',
					hiddenName : 'workPlan.typeId',
					xtype : 'combo',
					width : 520,
					editable : false,
					allowBlank:false,
					triggerAction : 'all',
					id : 'typeId',
					displayField : 'name',
					valueField : 'id',
					mode : 'local',
					store : new Ext.data.SimpleStore({
								autoLoad : true,
								url : __ctxPath + '/task/comboPlanType.do',
								fields : ['id', 'name']
							})
				},{
					fieldLabel : '计划名称',
					name : 'workPlan.planName',
					width : 520,
					allowBlank:false,
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
					width:550,
					items : [{
								text : '时间范围:',
								width : 101,
								style : 'padding-left:0px;padding-top:3px;'
							}, {
								text : '从',
								style : 'padding-left:0px;padding-top:3px;',
								width:8
							}, {
								xtype : 'datetimefield',
								width : 200,
								format : 'Y-m-d H:i:s',
								allowBlank:false,
								editable : false,
								name : 'workPlan.startTime',
								id : 'startTime'
							}, {
								text : '至',
								style : 'padding-left:0px;padding-top:3px;',
								width:8
							}, {
								xtype : 'datetimefield',
								width : 200,
								format : 'Y-m-d H:i:s',
								allowBlank:false,
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
					allowBlank:false,
					id : 'planContent'
				}, {
					layout : 'column',
					style : 'padding-left:0px;',
					width : 670,
					border:false,
					xtype : 'container',
					items : [{
								columnWidth : .7,
								border:false,
								style : 'padding-left:0px;',
								layout : 'form',
								items : [{
											fieldLabel : '附件',
											xtype : 'panel',
											frame:false,
											id : 'planFilePanel',
											height : 80,
											autoScroll : true,
											html : ''
										}]
							}, {
								columnWidth : .3,
								border:false,
								items : [{
									xtype : 'button',
									text : '添加附件',
									handler : function() {
										var dialog = App.createUploadDialog({
											file_cat : 'task',
											callback : function(data) {
												var fileIds = Ext
														.getCmp("planFileIds");
												var filePanel = Ext
														.getCmp('planFilePanel');

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
				}, {
					xtype : 'container',
					id:'IssueScopeContainer',
					hidden:true,
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
								readOnly : true,
								width:360
							}, {
								xtype : 'button',
								text : '选择部门',
								iconCls : 'btn-select',
								handler : function() {
									DepSelector.getView(function(id, name) {

										Ext.getCmp('issueScope').setValue(name);
										Ext.getCmp('issueScopeIds')
												.setValue(id);
									}).show();
								}
							}, {
								xtype : 'button',
								text : '清除记录'
							}]
				}, {
					xtype : 'container',
					id:'AttendContainer',
					hidden:true,
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
								readOnly : true,
								width:360
							}, {
								xtype : 'button',
								text : '选择人员',
								iconCls : 'btn-select',
								handler : function() {
									UserSelector.getView(function(id, name) {
										Ext.getCmp('participants')
												.setValue(name);
										Ext.getCmp('participantsIds')
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
					hidden:true,
					id:'PrincipalContainer',
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
								readOnly : true,
								width:360
							}, {
								xtype : 'button',
								text : '选择人员',
								iconCls : 'btn-select',
								handler : function() {
									UserSelector.getView(function(id, name) {
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
				},  {
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
										['ux-flag-pink','个人计划'],
										['ux-flag-red','紧急计划'],
										['ux-flag-purple','部门计划'],
										['ux-flag-yellow','待定计划']]
							}),
					valueField : 'flagStyle',
					displayField : 'flagName',
					//iconClsField : 'flagClass',
					triggerAction : 'all',
					value:'ux-flag-blue'
				}, {
					fieldLabel : '备注',
					name : 'workPlan.note',
					xtype : 'textarea',
					id : 'note',
					width : 520,
					height : 50
				}]}]
	}
	);
   if(isGranted('_NewDepPlan')){
	   Ext.getCmp('PersonalOr').items[1].hidden=false;
	}

	
	if (this.planId != null && this.planId != 'undefined') {
		formPanel.getForm().load({
			deferredRender : false,
			url : __ctxPath + '/task/getWorkPlan.do?planId=' + this.planId,

			waitMsg : '正在载入数据...',
			success : function(form, action) {
				var result = action.result.data;
				var status = result.status;
				var isPersonal = result.isPersonal;
				Ext.getCmp('status' + status).setValue(true);
				Ext.getCmp('isPersonal' + isPersonal).setValue(true);
				if(isPersonal==1){
				    Ext.getCmp('IssueScopeContainer').hide();
				    Ext.getCmp('AttendContainer').hide();
				    Ext.getCmp('PrincipalContainer').hide();
				  }else{
				    Ext.getCmp('IssueScopeContainer').show();
				    Ext.getCmp('AttendContainer').show();
				    Ext.getCmp('PrincipalContainer').show();
				}
				var typeId = result.planType.typeId;
				Ext.getCmp('typeId').setValue(typeId);
				var af = result.planFiles;
				var startTime = getDateFromFormat(result.startTime,'yyyy-MM-dd HH:mm:ss');
				var endTime = getDateFromFormat(result.endTime,'yyyy-MM-dd HH:mm:ss');
				Ext.getCmp('startTime').setValue(new Date(startTime));
				Ext.getCmp('endTime').setValue(new Date(endTime));					
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

/**
 * 
 * @return {}
 */
NewWorkPlanForm.prototype.initToolbar = function() {

	var toolbar = new Ext.Toolbar({
		height : 30,
		items : [{
					text : '保存',
					iconCls : 'btn-save',
					handler : function() {
						var fp = Ext.getCmp('NewWorkPlanFormPanel');
						var dep=Ext.getCmp('isPersonal0');
						if(dep!=null&&dep!='undefined'&&dep!=''){
						  if(dep.getValue()==true){
						     var issueScope=Ext.getCmp('issueScope').getValue();//发布范围
						     var participants=Ext.getCmp('participants').getValue();//参与人
						     var principal=Ext.getCmp('principal').getValue();//负责人
						     if((issueScope!='')||participants!=''){
						        if(principal!=''){
						           if (fp.getForm().isValid()) {
									fp.getForm().submit({
												method : 'post',
												waitMsg : '正在提交数据...',
												success : function(fp, action) {
													Ext.ux.Toast.msg('操作信息', '成功保存信息！');
													// Ext.getCmp('WorkPlanGrid').getStore()
													// .reload();
												},
												failure : function(fp, action) {
													Ext.MessageBox.show({
																title : '操作信息',
																msg : '信息保存出错，请联系管理员！',
																buttons : Ext.MessageBox.OK,
																icon : 'ext-mb-error'
															});
												}
											});
								}
						        }else{
						           Ext.ux.Toast.msg('操作提示','负责人为必填!');
						        }
						     }else{
						        Ext.ux.Toast.msg('操作提示','发布范围，参与人至少填写一项!');
						     }
						  }else{
						     if (fp.getForm().isValid()) {
									fp.getForm().submit({
												method : 'post',
												waitMsg : '正在提交数据...',
												success : function(fp, action) {
													Ext.ux.Toast.msg('操作信息', '成功保存信息！');
												},
												failure : function(fp, action) {
													Ext.MessageBox.show({
																title : '操作信息',
																msg : '信息保存出错，请联系管理员！',
																buttons : Ext.MessageBox.OK,
																icon : 'ext-mb-error'
															});
												}
											});
								}
						    
						  
						  }
						}
						
					}

				}, {
					text : '重置',
					iconCls : 'btn-reseted',
					handler : function() {
						 var docForm = Ext.getCmp('NewWorkPlanFormPanel');
						 docForm.getForm().reset();
						 var fileAttaches = Ext
						 .getCmp("planFileIds");
						 var filePanel = Ext
						 .getCmp('planFilePanel');
						 filePanel.body.update('');
						 fileAttaches.setValue('');
					}
				}]
	});
	return toolbar;
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