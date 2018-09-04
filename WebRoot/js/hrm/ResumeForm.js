var ResumeForm = function(resumeId) {
	this.resumeId = resumeId;
	var fp = this.setup();
	var toolbar = new Ext.Toolbar({
				id : 'ResumeFormToolbar',
				items : [{
					text : '保存',
					iconCls : 'btn-save',
					handler : function() {
						var fp = Ext.getCmp('ResumeForm');
						if (fp.getForm().isValid()) {
							fp.getForm().submit({
								method : 'post',
								waitMsg : '正在提交数据...',
								success : function(fp, action) {
									Ext.ux.Toast.msg('操作信息', '成功保存信息！');
									Ext.getCmp('ResumeGrid').getStore()
											.reload();
									AppUtil.removeTab('ResumeFormPanel');
								},
								failure : function(fp, action) {
									Ext.MessageBox.show({
												title : '操作信息',
												msg : '信息保存出错，请联系管理员！',
												buttons : Ext.MessageBox.OK,
												icon : Ext.MessageBox.ERROR
											});
									AppUtil.removeTab('ResumeFormPanel');
								}
							});
						}
					}
				}, {
					text : '取消',
					iconCls : 'btn-cancel',
					handler : function() {
						var tabs = Ext.getCmp('centerTabPanel');
						var ResumeFormPanel = Ext.getCmp('ResumeFormPanel');
						if (ResumeFormPanel != null) {
							tabs.remove('ResumeFormPanel');
						}
					}
				}]
			});
	var panel = new Ext.Panel({
				id : 'ResumeFormPanel',
				iconCls : 'menu-resume',
				title : '简历详细信息',
				width : 500,
				tbar : toolbar,
				height : 420,
				modal : true,
				layout : 'anchor',
				plain : true,
				bodyStyle : 'padding:5px;',
				buttonAlign : 'center',
				items : [this.setup()]
			});
	return panel;
};

ResumeForm.prototype.setup = function() {

	var formPanel = new Ext.FormPanel({
		url : __ctxPath + '/hrm/saveResume.do',
		layout : 'form',
		id : 'ResumeForm',
		defaults : {
			anchor : '98%,98%'
		},
		reader : new Ext.data.JsonReader({
					root : 'data'
				}, [{
							name : 'ResumeForm.resumeId',
							mapping : 'resumeId'
						}, {
							name : 'ResumeForm.registor',
							mapping : 'registor'
						}, {
							name : 'ResumeForm.regTime',
							mapping : 'regTime'
						}, {
							name : 'ResumeForm.status',
							mapping : 'status'
						}, {
							name : 'ResumeForm.fullname',
							mapping : 'fullname'
						}, {
							name : 'ResumeForm.age',
							mapping : 'age'
						}, {
							name : 'ResumeForm.birthday',
							mapping : 'birthday'
						}, {
							name : 'ResumeForm.birthPlace',
							mapping : 'birthPlace'
						}, {
							name : 'ResumeForm.startWorkDate',
							mapping : 'startWorkDate'
						}, {
							name : 'ResumeForm.idNo',
							mapping : 'idNo'
						}, {
							name : 'ResumeForm.religion',
							mapping : 'religion'
						}, {
							name : 'ResumeForm.party',
							mapping : 'party'
						}, {
							name : 'ResumeForm.nationality',
							mapping : 'nationality'
						}, {
							name : 'ResumeForm.race',
							mapping : 'race'
						}, {
							name : 'ResumeForm.sex',
							mapping : 'sex'
						}, {
							name : 'ResumeForm.position',
							mapping : 'position'
						}, {
							name : 'ResumeForm.photo',
							mapping : 'photo'
						}, {
							name : 'ResumeForm.photoId',
							mapping : 'photoId'
						}, {
							name : 'ResumeForm.address',
							mapping : 'address'
						}, {
							name : 'ResumeForm.zip',
							mapping : 'zip'
						}, {
							name : 'ResumeForm.email',
							mapping : 'email'
						}, {
							name : 'ResumeForm.phone',
							mapping : 'phone'
						}, {
							name : 'ResumeForm.mobile',
							mapping : 'mobile'
						}, {
							name : 'ResumeForm.eduCollege',
							mapping : 'eduCollege'
						}, {
							name : 'ResumeForm.eduDegree',
							mapping : 'eduDegree'
						}, {
							name : 'ResumeForm.eduMajor',
							mapping : 'eduMajor'
						}, {
							name : 'ResumeForm.hobby',
							mapping : 'hobby'
						}, {
							name : 'ResumeForm.workCase',
							mapping : 'workCase'
						}, {
							name : 'ResumeForm.trainCase',
							mapping : 'trainCase'
						}, {
							name : 'ResumeForm.projectCase',
							mapping : 'projectCase'
						}, {
							name : 'ResumeForm.memo',
							mapping : 'memo'
						}]),
		bodyStyle : 'padding-left:10px;',
		formId : 'ResumeFormId',
		defaultType : 'textfield',
		items : [{
					name : 'resume.resumeId',
					id : 'ResumeForm.resumeId',
					xtype : 'hidden',
					value : this.resumeId == null ? '' : this.resumeId
				}, {
					xtype : 'hidden',
					name : 'resume.registor',
					id : 'ResumeForm.registor'
				}, {
					xtype : 'hidden',
					name : 'resume.regTime',
					id : 'regTime'
				}, {
					xtype : 'container',
					layout : 'column',
					height : 40,
					items : [{
						xtype : 'label',
						style : 'padding-left:48%;',
						html : '<span style="font-size:28px;color:blue;">简历</span>'
					}, {
						xtype : 'container',
						width : 150,
						style : 'float:right;',
						layout : 'column',
						items : [{
									xtype : 'label',
									width : 30,
									text : '状态:',
									style : 'padding-top:3px;'
								}, {
									width : 100,
									xtype : 'combo',
									name : 'resume.status',
									id : 'ResumeForm.status',
									editable : false,
									mode : 'local',
									triggerAction : 'all',
									store : ['通过', '未通过', '准备安排面试', '通过面试'],
									value : '未通过'
								}]
					}]
				}, {
					xtype : 'fieldset',
					title : '基本资料',
					layout : 'column',
					items : [{
								xtype : 'container',
								columnWidth : .37,
								defaultType : 'textfield',
								layout : 'form',
								defaults : {
									anchor : '96%,96%'
								},
								items : [{
											fieldLabel : '姓名',
											name : 'resume.fullname',
											id : 'ResumeForm.fullname',
											allowBlank : false,
											blankText : '姓名不可为空!'
										}, {
											fieldLabel : '年龄',
											name : 'resume.age',
											id : 'ResumeForm.age',
											xtype : 'numberfield'
										}, {
											fieldLabel : '生日',
											name : 'resume.birthday',
											id : 'ResumeForm.birthday',
											xtype : 'datefield',
											format : 'Y-m-d'
										}, {
											fieldLabel : '籍贯',
											name : 'resume.birthPlace',
											id : 'ResumeForm.birthPlace'
										}, {
											fieldLabel : '参加工作时间',
											name : 'resume.startWorkDate',
											id : 'ResumeForm.startWorkDate',
											xtype : 'datefield',
											format : 'Y-m-d'
										}, {
											fieldLabel : '身份证',
											name : 'resume.idNo',
											id : 'ResumeForm.idNo'
										}]
							}, {
								xtype : 'container',
								columnWidth : .37,
								defaultType : 'textfield',
								layout : 'form',
								defaults : {
									anchor : '96%,96%'
								},
								items : [{
									fieldLabel : '宗教信仰',
									name : 'resume.religion',
									id : 'ResumeForm.religion',
									maxHeight : 200,
									xtype : 'combo',
									mode : 'local',
									editable : true,
									triggerAction : 'all',
									store : [],
									listeners : {
										focus : function(combo) {
											var _store = Ext
													.getCmp('ResumeForm.religion')
													.getStore();
											if (_store.getCount() <= 0)
												Ext.Ajax.request({
													url : __ctxPath
															+ '/system/loadDictionary.do',
													method : 'post',
													params : {
														itemName : '宗教信仰'
													},
													success : function(response) {
														var result = Ext.util.JSON
																.decode(response.responseText);
														_store.loadData(result);
													}
												});
										}
									}
								}, {
									fieldLabel : '政治面貌',
									name : 'resume.party',
									id : 'ResumeForm.party',
									maxHeight : 200,
									xtype : 'combo',
									mode : 'local',
									editable : true,
									triggerAction : 'all',
									store : [],
									listeners : {
										focus : function(combo) {
											var _store = Ext
													.getCmp('ResumeForm.party')
													.getStore();
											if (_store.getCount() <= 0)
												Ext.Ajax.request({
													url : __ctxPath
															+ '/system/loadDictionary.do',
													method : 'post',
													params : {
														itemName : '政治面貌'
													},
													success : function(response) {
														var result = Ext.util.JSON
																.decode(response.responseText);
														_store.loadData(result);
													}
												});
										}
									}
								}, {
									fieldLabel : '国籍',
									name : 'resume.nationality',
									id : 'ResumeForm.nationality',
									maxHeight : 200,
									xtype : 'combo',
									mode : 'local',
									editable : true,
									triggerAction : 'all',
									store : [],
									listeners : {
										focus : function(combo) {
											var _store = Ext
													.getCmp('ResumeForm.nationality')
													.getStore();
											if (_store.getCount() <= 0)
												Ext.Ajax.request({
													url : __ctxPath
															+ '/system/loadDictionary.do',
													method : 'post',
													params : {
														itemName : '国籍'
													},
													success : function(response) {
														var result = Ext.util.JSON
																.decode(response.responseText);
														_store.loadData(result);
													}
												});
										}
									}
								}, {
									fieldLabel : '民族',
									name : 'resume.race',
									id : 'ResumeForm.race',
									maxHeight : 200,
									xtype : 'combo',
									mode : 'local',
									editable : true,
									triggerAction : 'all',
									store : [],
									listeners : {
										focus : function(combo) {
											var _store = Ext
													.getCmp('ResumeForm.race')
													.getStore();
											if (_store.getCount() <= 0)
												Ext.Ajax.request({
													url : __ctxPath
															+ '/system/loadDictionary.do',
													method : 'post',
													params : {
														itemName : '民族'
													},
													success : function(response) {
														var result = Ext.util.JSON
																.decode(response.responseText);
														_store.loadData(result);
													}
												});
										}
									}
								}, {
									fieldLabel : '性别',
									name : 'resume.sex',
									id : 'ResumeForm.sex',
									xtype : 'combo',
									editable : false,
									mode : 'local',
									triggerAction : 'all',
									store : ['男', '女']
								}, {
									fieldLabel : '职位名称',
									name : 'resume.position',
									id : 'ResumeForm.position'
								}]
							}, {
								name : 'resume.photo',
								id : 'ResumeForm.photo',
								xtype : 'hidden'
							},  {
								name : 'resume.photoId',
								id : 'ResumeForm.photoId',
								xtype : 'hidden'
							},{
								xtype : 'container',
								columnWidth : .26,
								layout : 'column',
								items : [{
											xtype : 'label',
											text : '个人相片:',
											style : 'padding-left:0px;'
										}, {
											xtype : 'container',
											layout : 'form',
											width : 100,
											items : [{
												id : 'ResumePhotoPanel',
												height : 120,
												width : 88,
												xtype : 'panel',
												border : true,
												html : '<img src="' + __ctxPath
														+ '/images/default_person.gif" width="88" height="120"/>'
											}, {
												xtype : 'button',
												style : 'padding-top:3px;',
												iconCls : 'btn-upload',
												text : '上传照片',
												handler : function() {
													var photo = Ext.getCmp('ResumeForm.photo');
													var photoId = Ext.getCmp('ResumeForm.photoId');
													var dialog = App.createUploadDialog({
														file_cat : 'hrm/Resume',
														callback : uploadResumePhoto,
														permitted_extensions : ['jpg','png']
													});
													if (photo.getValue() != '' && photo.getValue() != null && photo.getValue() != 'undefined') {
														var msg = '再次上传需要先删除原有图片,';
														Ext.Msg.confirm('信息确认', msg + '是否删除？', function(btn) {
															if (btn == 'yes') {
																// 删除图片
																var resumeId=Ext.getCmp('ResumeForm.resumeId').getValue();
																Ext.Ajax.request({
																	url : __ctxPath + '/hrm/delphotoResume.do',
																	method : 'post',
																	params : {
																		resumeId : resumeId
																	},
																	success : function() {
																		var path=photo.value;
																		Ext.getCmp('ResumeForm.photo').setValue('');
																		Ext.getCmp('ResumePhotoPanel').body.update('<img src="'
																			+ __ctxPath+ '/images/default_person.gif" width="88" height="120"/>');
																		Ext.Ajax.request({
																			//url : __ctxPath + '/system/deleteFileAttach.do',
																			url : __ctxPath + '/system/deleteFileIdFileAttach.do',
																			method : 'post',
																			params : {
																				//filePath : path
																				photoId : photoId.value
																			},
																			success : function() {
																				dialog.show('queryBtn');
																			}
																		});

																	}
																});
															}
														});
													} else {
														dialog.show('queryBtn');
													}
												}

											}]
										}]
							}

					]

				}, {
					xtype : 'fieldset',
					title : '联系方式',
					defaultType : 'textfield',
					layout : 'form',
					items : [{
								fieldLabel : '地址',
								name : 'resume.address',
								id : 'ResumeForm.address',
								anchor : '100%'
							}, {
								layout : 'column',
								defaults : {
									anchor : '96%,96%'
								},
								style : 'padding-left:0px;padding-right:0px;',
								xtype : 'container',
								items : [{
											layout : 'form',
											xtype : 'container',
											style : 'padding-left:0px;',
											columnWidth : .5,
											defaults : {
												anchor : '96%,96%'
											},
											defaultType : 'textfield',
											items : [{
														fieldLabel : '邮编',
														name : 'resume.zip',
														id : 'ResumeForm.zip'
													}, {
														fieldLabel : '电子邮箱',
														name : 'resume.email',
														id : 'ResumeForm.email',
														vtype : 'email'
													}]
										}, {
											xtype : 'container',
											columnWidth : .5,
											style : 'padding-right:0px;',
											defaultType : 'textfield',
											layout : 'form',
											items : [{
														fieldLabel : '电话号码',
														name : 'resume.phone',
														id : 'ResumeForm.phone',
														anchor : '100%'
													}, {
														fieldLabel : '手机号码',
														name : 'resume.mobile',
														id : 'ResumeForm.mobile',
														anchor : '100%'
													}]
										}]
							}]
				}, {
					xtype : 'fieldset',
					title : '教育背景',
					defaultType : 'textfield',
					layout : 'form',
					items : [{
								fieldLabel : '毕业院校',
								name : 'resume.eduCollege',
								id : 'ResumeForm.eduCollege',
								anchor : '70%'
							}, {
								fieldLabel : '学历',
								name : 'resume.eduDegree',
								id : 'ResumeForm.eduDegree',
								anchor : '50%',
								maxHeight : 200,
								xtype : 'combo',
								mode : 'local',
								editable : true,
								triggerAction : 'all',
								store : [],
								listeners : {
									focus : function(combo) {
										var _store = Ext.getCmp('ResumeForm.eduDegree').getStore();
										if (_store.getCount() <= 0)
											Ext.Ajax.request({
												url : __ctxPath
														+ '/system/loadDictionary.do',
												method : 'post',
												params : {
													itemName : '学历'
												},
												success : function(response) {
													var result = Ext.util.JSON.decode(response.responseText);
													_store.loadData(result);
												}
											});
									}
								}
							}, {
								fieldLabel : '专业',
								name : 'resume.eduMajor',
								id : 'ResumeForm.eduMajor',
								anchor : '50%',
								maxHeight : 200,
								xtype : 'combo',
								mode : 'local',
								editable : true,
								triggerAction : 'all',
								store : [],
								listeners : {
									focus : function(combo) {
										var _store = Ext.getCmp('ResumeForm.eduMajor').getStore();
										if (_store.getCount() <= 0)
											Ext.Ajax.request({
												url : __ctxPath + '/system/loadDictionary.do',
												method : 'post',
												params : {
													itemName : '专业'
												},
												success : function(response) {
													var result = Ext.util.JSON.decode(response.responseText);
													_store.loadData(result);
												}
											});
									}
								}
							}]
				}, {
					xtype : 'fieldset',
					title : '爱好',
					layout : 'anchor',
					items : [{
								name : 'resume.hobby',
								id : 'ResumeForm.hobby',
								xtype : 'textarea',
								anchor : '100%'
							}]
				}, {
					xtype : 'fieldset',
					title : '工作经历',
					layout : 'anchor',
					items : [{
								name : 'resume.workCase',
								id : 'ResumeForm.workCase',
								xtype : 'textarea',
								anchor : '100%'
							}]
				},

				{
					xtype : 'fieldset',
					title : '培训经历',
					layout : 'anchor',
					items : [{
								name : 'resume.trainCase',
								id : 'ResumeForm.trainCase',
								xtype : 'textarea',
								anchor : '100%'
							}]
				}, {
					xtype : 'fieldset',
					title : '项目经验',
					layout : 'anchor',
					items : [{
								name : 'resume.projectCase',
								id : 'ResumeForm.projectCase',
								xtype : 'textarea',
								anchor : '100%'
							}]
				}, {
					xtype : 'fieldset',
					title : '备注',
					layout : 'anchor',
					items : [{
								name : 'resume.memo',
								id : 'ResumeForm.memo',
								xtype : 'textarea',
								anchor : '100%'
							}]
				}, {
					xtype : 'fieldset',
					title : '附件',
					layout : 'column',
					items : [{
								columnWidth : .8,
								layout : 'form',
								items : [{
											xtype : 'panel',
											id : 'resumeFilePanel',
											height : 50,
											border : false,
											autoScroll : true,
											html : ''
										}]
							}, {
								columnWidth : .2,
								border : false,
								items : [{
									xtype : 'button',
									text : '添加附件',
									iconCls : 'menu-attachment',
									handler : function() {
										var dialog = App.createUploadDialog({
											file_cat : 'hrm/resume',
											callback : function(data) {
												var fileIds = Ext.getCmp("resumefileIds");
												var filePanel = Ext.getCmp('resumeFilePanel');

												for (var i = 0; i < data.length; i++) {
													if (fileIds.getValue() != '') {
														fileIds.setValue(fileIds.getValue()+ ',');
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
									text : '清除附件',
									iconCls : 'reset',
									handler : function() {
										var fileAttaches = Ext.getCmp("resumefileIds");
										var filePanel = Ext.getCmp('resumeFilePanel');
										filePanel.body.update('');
										fileAttaches.setValue('');
									}
								}, {
									xtype : 'hidden',
									id : 'resumefileIds',
									name : 'fileIds'
								}]
							}]
				}

		]
	});

	if (this.resumeId != null && this.resumeId != 'undefined') {
		formPanel.getForm().load({
			// deferredRender : false,
			url : __ctxPath + '/hrm/getResume.do?resumeId=' + this.resumeId,
			waitMsg : '正在载入数据...',
			success : function(form, action) {
				var res = Ext.util.JSON.decode(action.response.responseText).data[0];
				var photo = res.photo;
				var af = res.resumeFiles;
				var startWorkDate = res.startWorkDate;
				var birthday = res.birthday;
				if (startWorkDate != null) {
					Ext.getCmp('ResumeForm.startWorkDate')
							.setValue(new Date(getDateFromFormat(startWorkDate,
									"yyyy-MM-dd HH:mm:ss")));
				}
				if (birthday != null) {
					Ext.getCmp('ResumeForm.birthday')
							.setValue(new Date(getDateFromFormat(birthday,
									"yyyy-MM-dd HH:mm:ss")));
				}
				var filePanel = Ext.getCmp('resumeFilePanel');
				var fileIds = Ext.getCmp("resumefileIds");
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
				if (photo != null && photo != '') {
					Ext.getCmp('ResumePhotoPanel').body.update('<img src="'
							+ __ctxPath + '/attachFiles/' + photo
							+ '" width="88" height="120"/>');
				}
				// Ext.Msg.alert('编辑', '载入成功！');
			},
			failure : function(form, action) {
				// Ext.Msg.alert('编辑', '载入失败');
			}
		});
	}

	function uploadResumePhoto(data) {
		var photo = Ext.getCmp('ResumeForm.photo');
		var photoId = Ext.getCmp('ResumeForm.photoId');
		var display = Ext.getCmp('ResumePhotoPanel');
		photo.setValue(data[0].filePath);
		photoId.setValue(data[0].fileId);
		display.body.update('<img src="' + __ctxPath + '/attachFiles/'
				+ data[0].filePath + '"  width="88" height="120"/>');
	};
	return formPanel;

};

function removeResumeFile(obj, fileId) {
		var fileIds = Ext.getCmp("resumefileIds");
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
