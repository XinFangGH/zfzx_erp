/**
 * @createtime 2010-10-9 PM
 * @class ConferenceForm
 * @extends Ext.Window
 * @description 会议信息管理
 * @company 智维软件
 * @author YHZ
 */
ConferenceForm = Ext.extend(Ext.Window, {
	// store
	store : null,
	gridPanel : null,
	// end of 附件信息

	// 内嵌FormPanel
	formPanel : null,
	// 会议基本信息
	basePanel : null,
	// 会议内容
	contextPanel : null,
	// 参加人员
	jonerPanel : null,
	// 权限分配
	grantPanel : null,
	// 附件管理
	filePanel : null,
	buttons : null,

	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		// 必须先初始化组件
		this.initUIComponents();
		ConferenceForm.superclass.constructor.call(this, {
			id : 'ConferenceFormWin',
			layout : 'fit',
			iconCls : 'menu-conference',
			items : this.formPanel,
			modal : true,
			maximizable : true,
			minWidth : 800,
			width : 730,
			height : 650,
			minHeight : 650,
			autoScroll : false,
			title : '编辑会议内容',
			buttonAlign : 'center',
			buttons : this.buttons,
			keys : {
				key : Ext.EventObject.ENTER,
				fn : this.send.createCallback(this.formPanel, this),
				scope : this
			}
		});
	},// end of the constructor
	// 初始化组件
	initUIComponents : function() {

		// 会议信息设置
		this.basePanel = new Ext.form.FieldSet({
			id : 'conference.basePanel',
			title : '会议信息',
			layout : 'form',
			border : true,
			items : [ {
				layout : 'column',
				columnWidth : 1,
				border : false,
				defaults : {
					border : false
				},
				items : [{
					layout : 'form',
					columnWidth : .5,
					defaults : {
						width : '100%'
					},
					items : [{
						xtype : 'hidden',
						name : 'conference.confId',
						value : this.confId != null ? this.confId : ''
					}, {
						anchor : '99%',
						xtype : 'textfield',
						name : 'conference.confTopic',
						fieldLabel : '会议标题',
						allowBlank : false,
						maxLength : 128
					}, {
						xtype : 'hidden',
						name : 'conference.typeId'
					}, {
						anchor : '99%',
						xtype : 'combo',
						name : 'conference.confProperty',
						fieldLabel : '会议类型',
						valueField : 'typeId',
						displayField : 'typeName',
						mode : 'local',
						editable : false,
						triggerAction : 'all',
						forceSelection : true,
						allowBlank : false,
						store : new Ext.data.SimpleStore({
							url : __ctxPath + '/admin/getTypeAllConference.do',
							autoLoad : true,
							fields : ['typeId', 'typeName' ]
						}),
						listeners : {
							select : function(cbo,record,index) {
								var fm = Ext.getCmp('conference.basePanel');
								fm.getCmpByName('conference.typeId').setValue(cbo.getValue());
							}
						}
					}, {
						anchor : '99%',
						name : 'conference.feeBudget',
						fieldLabel : '经费(元)',
						text : '0',
						xtype : 'numberfield',
						maxLength : 21
					}, {
						anchor : '99%',
						fieldLabel : '留言方式',
						xtype : 'container',
						layout : 'column',
						border : false,
						defaults : {
							border : false,
							xtype : 'checkbox'
						},
						items : [{
							columnWidth : .5,
							boxLabel : '普通留言',
							name : 'conference.isEmail',
							inputValue : 1,
							checked : true
						}, {
							columnWidth : .5,
							boxLabel : '手机留言',
							name : 'conference.isMobile',
							inputValue : 1
						}]
					}]
				}, {
					layout : 'form',
					columnWidth : .5,
					defaults : {
						width : '100%'
					},
					items : [{
						xtype : 'hidden',
						name : 'conference.roomId'
					}, {
						anchor : '99%',
						xtype : 'combo',
						name : 'conference.roomName',
						fieldLabel : '会议室名称',
						valueField : 'roomId',
						displayField : 'roomName',
						mode : 'local',
						editable : false,
						triggerAction : 'all',
						forceSelection : true,
						allowBlank : false,
						store : new Ext.data.SimpleStore({
							url : __ctxPath + '/admin/getBoardrooConference.do',
							autoLoad : true,
							fields : [ 'roomId', 'roomName' ]
						}),
						listeners : {
							select : function(cbo,record,index) {
								var fm = Ext.getCmp('conference.basePanel');
								fm.getCmpByName('conference.roomId').setValue(cbo.getValue());
								fm.getCmpByName('roomName').setValue(cbo.getRawValue());
								fm.getCmpByName('conference.roomLocation').setValue(cbo.getRawValue());
							}
						}
					}, {
						anchor : '99%',
						xtype : 'textfield',
						readOnly : true,
						fieldLabel : '会议室',
						name : 'roomName'
					}, {
						anchor : '99%',
						xtype : 'textfield',
						fieldLabel : '地址',
						name : 'conference.roomLocation',
						allowBlank : false,
						maxLength : 128
					}, {
						anchor : '99%',
						xtype : 'radiogroup',
						fieldLabel : '重要级别',
						border : false,
						items : [{
							boxLabel : '普通',
							name : 'conference.importLevel',
							inputValue : 0,
							checked : true
						}, {
							boxLabel : '重要',
							name : 'conference.importLevel',
							inputValue : 1
						}]
					}]
				}]
			}]
		}); // end of this basePanel

		// 会议参加人员jonerPanel
		this.jonerPanel = new Ext.form.FieldSet({
			id : 'conference.jonerPanel',
			title : '参加人员',
			layout : 'form',
			border : true,
			items : [{
				anchor : '100%',
				fieldLabel : '主持人',
				xtype : 'container',
				layout : 'column',
				border : false,
				items : [{
					xtype : 'hidden',
					name : 'conference.compere'
				}, {
					columnWidth : 1,
					anchor : '90%',
					xtype : 'textfield',
					name : 'conference.compereName',
					readOnly : true,
					allowBlank : false,
					maxLength : 256
				}, {
					xtype : 'button',
					text : '请选择',
					iconCls : 'btn-user-sel',
					handler : function() {
						UserSelector.getView(
							function(userId,fullName) {
								var fm = Ext.getCmp('conference.jonerPanel');
								fm.getCmpByName('conference.compere').setValue(userId);
								fm.getCmpByName('conference.compereName').setValue(fullName);
						}).show();
					}
				}]
			}, {
				anchor : '100%',
				fieldLabel : '记录人',
				xtype : 'container',
				layout : 'column',
				border : false,
				items : [{
					xtype : 'hidden',
					name : 'conference.recorder'
				}, {
					columnWidth : 1,
					anchor : '90%',
					xtype : 'textfield',
					name : 'conference.recorderName',
					readOnly : true,
					allowBlank : false,
					maxLength : 256
				},{
					xtype : 'button',
					text : '请选择',
					iconCls : 'btn-user-sel',
					handler : function() {
						UserSelector.getView(
							function(userId,fullName) {
								var fm = Ext.getCmp('conference.jonerPanel');
								fm.getCmpByName('conference.recorder').setValue(userId);
								fm.getCmpByName('conference.recorderName').setValue(fullName);
						}).show();
					}
				}]
			}, {
				anchor : '100%',
				fieldLabel : '参加人',
				xtype : 'container',
				layout : 'column',
				border : false,
				items : [{
					xtype : 'hidden',
					name : 'conference.attendUsers'
				}, {
					columnWidth : 1,
					anchor : '90%',
					xtype : 'textfield',
					name : 'conference.attendUsersName',
					readOnly : true,
					allowBlank : false,
					maxLength : 4000
				},{
					xtype : 'button',
					text : '请选择',
					iconCls : 'btn-user-sel',
					handler : function() {
						UserSelector.getView(
							function(userId,fullName) {
								var fm = Ext.getCmp('conference.jonerPanel');
								fm.getCmpByName('conference.attendUsers').setValue(userId);
								fm.getCmpByName('conference.attendUsersName').setValue(fullName);
						}).show();
					}
				}]
			} ]
		}); // end of this jonerPanel

		// 权限grantPanel
		this.grantPanel = new Ext.form.FieldSet({
			id : 'conference.grantPanel',
			title : '权限设置',
			region : 'center',
			layout : 'form',
			border : true,
			items : [{
				anchor : '100%',
				fieldLabel : '修改人',
				xtype : 'container',
				layout : 'column',
				border : false,
				items : [{
					xtype : 'hidden',
					name : 'updater'
				}, {
					columnWidth : 1,
					anchor : '90%',
					xtype : 'textfield',
					name : 'updaters',
					readOnly : true,
					allowBlank : false,
					maxLength : 256
				}, {
					xtype : 'button',
					text : '请选择',
					iconCls : 'btn-user-sel',
					handler : function() {
						UserSelector.getView(
							function(userId,fullName) {
								var fm = Ext.getCmp('conference.grantPanel');
								fm.getCmpByName('updater').setValue(userId);
								fm.getCmpByName('updaters').setValue(fullName);
						}).show();
					}
				}]
			}, {
				anchor : '100%',
				fieldLabel : '审核人',
				xtype : 'container',
				layout : 'column',
				border : false,
				items : [{
					xtype : 'hidden',
					name : 'conference.checkUserId'
				}, {
					columnWidth : 1,
					anchor : '90%',
					xtype : 'textfield',
					name : 'conference.checkName',
					readOnly : true,
					allowBlank : false,
					maxLength : 64
				},{
					xtype : 'button',
					text : '请选择',
					iconCls : 'btn-user-sel',
					handler : function() {
					UserSelector.getView(
						function(userId,fullName) {
							var fm = Ext.getCmp('conference.grantPanel');
							fm.getCmpByName('conference.checkUserId').setValue(userId);
							fm.getCmpByName('conference.checkName').setValue(fullName);
						},true).show();
					}
				}]
			}, {
				layout : 'column',
				border : false,
				height : 26
			} ]
		}); // end of this grantPanel

		// 会议内容contextPanel
		this.contextPanel = new Ext.form.FieldSet( {
			title : '时间和内容设置',
			layout : 'column',
			columnWidth : 1,
			border : true,
			items : [ {
				columnWidth : .5,
				anchor : '100%',
				layout : 'form',
				border : false,
				items : [ {
					anchor : '99%',
					xtype : 'datetimefield',
					format : 'Y-m-d H:i:s',
					editable : false,
					name : 'conference.startTime',
					fieldLabel : '开始时间',
					allowBlank : false
				}]
			}, {
				columnWidth : .5,
				anchor : '100%',
				layout : 'form',
				border : false,
				items : [ {
					anchor : '99%',
					xtype : 'datetimefield',
					name : 'conference.endTime',
					format : 'Y-m-d H:i:s',
					editable : false,
					fieldLabel : '结束时间',
					allowBlank : false
				}]
			}, {
				columnWidth : 1,
				layout : 'form',
				height : 100,
				anchor : '100%',
				border : false,
				items : [ {
					anchor : '100%',
					name : 'conference.confContent',
					xtype : 'htmleditor',
					height : 100,
					fieldLabel : '会议内容',
					allowBlank : false,
					maxLength : 4000
				} ]
			} ]
		}); // end of this contextPanel

		// 附件
		this.filePanel = new Ext.form.FieldSet( {
			id : 'conferenceForm.filePath',
			layout : 'form',
			region : 'center',
			title : '附件信息',
			items : [ {
				fieldLabel : '附件信息',
				xtype : 'container',
				columnWidth : 1,
				layout : 'column',
				border : false,
				items : [ {
					xtype : 'hidden',
					name : 'filePath'
				}, {
					columnWidth : 1,
					anchor : '99%',
					xtype : 'panel',
					id : 'resumeFilePanel',
					height : 50,
					border : true,
					autoScroll : true,
					html : ''
				}, {
					width : 75,
					layout : 'form',
					defaultType : 'button',
					border : false,
					items : [{
						iconCls:'menu-attachment',
						text : '上传附件',
						handler : this.upLoadFile
					},{
						text : '清除附件',
						iconCls : 'reset',
						handler : this.delLoadFile	
					}]
				} ]
			} ]
		}); // end of this filePath
		
		this.formPanel = new Ext.FormPanel( {
			id : 'ConferenceFormPanel',
			autoScroll : false,
			layout : 'form',
			region : 'center',
			border : false,
			bodyStyle : 'padding:10px 10px 10px 10px;',
			defaults : {
				readOnly : true
			},
			items : [ this.basePanel, this.contextPanel, {
				layout : 'column',
				border : false,
				columnWidth : 1,
				defaults : {
					border : false
				},
				items : [ {
					columnWidth : .5,
					anchor : '100%',
					layout : 'form',
					items : [ this.jonerPanel ]
				}, {
					anchor : '100%',
					columnWidth : .5,
					layout : 'form',
					items : [ this.grantPanel ]
				}]
			}, this.filePanel ]
		});// end of this formPanel

		// 数据加载
		if (this.confId != null && this.confId != '' && this.confId != 'undenfied') {
			this.formPanel.loadData({
				url : __ctxPath + '/admin/getConference.do?confId=' + this.confId,
				root : 'data',
				preName : 'conference',
				success : function(response, options) {
					var action = Ext.util.JSON.decode(response.responseText);
					ConferenceForm.setGrantPanel(action);
					ConferenceForm.setFilePanel(action.data.attachFiles);
				},
				failure : function() {
					Ext.ux.Toast.msg('操作提示','数据加载失败！');
				}
			});
		}// end of this 数据加载

		this.buttons = [{
			text : '暂存会议信息',
			iconCls : 'temp',
			handler : this.save.createCallback(this.formPanel,this)
		}, {
			text : '发送会议通知',
			iconCls : 'btn-mail_send',
			handler : this.send.createCallback(this.formPanel,this)
		}, {
			text : '取消',
			iconCls : 'btn-cancel',
			handler : this.cancel.createCallback(this)
		}];
	}, // end of this initUIComponent
	/**
	 * 取消
	 */
	cancel : function(window) {
		window.close();
	},
	
	/**
	 * 保存
	 */
	save : function(formPanel,window) {
		if (formPanel.getForm().isValid()) {
			// 开会时间验证
			var fm = Ext.getCmp('ConferenceFormPanel');
			
			var dateTimeNow = new Date().format('Y-m-d H:i:s');
			if(dateTimeNow > fm.getCmpByName('conference.startTime').value){
				Ext.ux.Toast.msg('操作提示', '对不起，开会时间必须大于当前时间，请重新输入！');
				fm.getCmpByName('conference.startTime').focus(true);
				return;
			}			
			if (fm.getCmpByName('conference.startTime').value >= fm.getCmpByName('conference.endTime').value) {
				Ext.ux.Toast.msg('操作提示', '对不起，开会时间有误，请重新输入！');
				fm.getCmpByName('conference.startTime').focus(true);
				return;
			}
			// 会议主持人和记录人重复人员验证
			if (!ConferenceForm.validateCompereAndRecorder())
				return;
			formPanel.getForm().submit({
				url : __ctxPath + '/admin/tempConference.do',
				method : 'post',
				waitMsg : '数据正在保存，请稍后...',
				success : function(fp, action) {
					Ext.ux.Toast.msg('操作提示','成功保存信息！');
					Ext.getCmp('ZanCunConferenceGrid').getStore().reload( {
						params : {
							start : 0,
							limit : 25
						}
					});
					window.close();
				},
				failure : function(fp, action) {
					Ext.MessageBox.show( {
						title : '操作信息',
						msg : Ext.util.JSON.decode(action.response.responseText),
						buttons : Ext.MessageBox.OK,
						icon : 'ext-mb-error'
					});
				}
			});
		}
	},
	/**
	 * 发送
	 */
	send : function(formPanel,window) {
		if (formPanel.getForm().isValid()) {
			// 开会时间验证
			var fm = Ext.getCmp('ConferenceFormPanel');
			
			var dateTimeNow = new Date().format('Y-m-d H:i:s');
			if(dateTimeNow > fm.getCmpByName('conference.startTime').value){
				Ext.ux.Toast.msg('操作提示', '对不起，开会时间必须大于当前时间，请重新输入！');
				fm.getCmpByName('conference.startTime').focus(true);
				return;
			}
			if (fm.getCmpByName('conference.startTime').value >= fm.getCmpByName('conference.endTime').value) {
				Ext.ux.Toast.msg('操作提示', '对不起，开会时间有误，请重新输入！');
				fm.getCmpByName('conference.startTime').focus(true);
				return;
			}
			
			// 会议主持人和记录人重复人员验证
			if (!ConferenceForm.validateCompereAndRecorder())
				return;
			formPanel.getForm().submit({
				url : __ctxPath + '/admin/sendConference.do',
				method : 'post',
				waitMsg : '数据正在发送，请稍后...',
				success : function(fp, action) {
					Ext.ux.Toast.msg('操作提示','会议信息数据发送成功，等待审批！');
					Ext.getCmp('ZanCunConferenceGrid').getStore().reload( {
						params : {
							start : 0,
							limit : 25
						}
					});
					window.close();
				},
				failure : function(fp, action) {
					var res = Ext.util.JSON.decode(action.response.responseText);
					Ext.MessageBox.show( {
						title : '操作信息',
						msg : res.msg,
						buttons : Ext.MessageBox.OK,
						icon : 'ext-mb-error'
					});
				}
			});
		}
	},
	/**
	 * 文件上传
	 */
	upLoadFile : function() {
		var dialog = App.createUploadDialog( {
			file_cat : 'admin/conference',
			callback : function(arr) {
				var fm = Ext.getCmp('conferenceForm.filePath');
				var fileIds = '';
				fileIds = fm.getCmpByName('filePath').getValue()!='' ? fm.getCmpByName('filePath').getValue()+',' : '';
				var filePanel = Ext.getCmp('resumeFilePanel');
				for ( var i = 0; i < arr.length; i++) {
					fileIds += arr[i].fileId + ',';
					Ext.DomHelper.append(filePanel.body,
						'<span><a href="#" onclick="FileAttachDetail.show('
								+ arr[i].fileId + ')">' + arr[i].fileName
								+ '</a><img class="img-delete" src="' + __ctxPath
								+ '/images/system/delete.gif" onclick="removeResumeFile(this,'
								+ arr[i].fileId + ')"/>&nbsp;|&nbsp;</span>');
				}
				fm.getCmpByName('filePath').setValue(fileIds.substring(0,fileIds.length - 1));
			}
		});
		dialog.show('querybtn');
	},
					
	/**
	 * 删除上传文件
	 */
	delLoadFile : function() {
		var fm = Ext.getCmp('conferenceForm.filePath');
		var fileIds = fm.getCmpByName('filePath').value;
		if (fileIds != null && fileIds != '' && fileIds != 'undefined') {
			Ext.Msg.confirm('确认信息', '您真的要删除上传文件吗？', function(btn) {
				if (btn == 'yes') {
					Ext.Ajax.request( {
						url : __ctxPath + '/system/multiDelFileAttach.do',
						method : 'post',
						params : {
							ids : fileIds
						},
						success : function() {
							Ext.ux.Toast.msg('操作提示', '上传文件删除成功！');
							fm.getCmpByName('filePath').setValue('');
							Ext.getCmp('resumeFilePanel').update();
						},
						failure : function() {
							Ext.ux.Toast.msg('操作提示', '对不起，您上传文件删除失败！');
						}
					});
				}
			});
		} else {
			Ext.ux.Toast.msg('操作提示', '对不起，你还没有上传文件！');
		}
	}
	
});

/**
 * 显示上传文件列表
 */
ConferenceForm.setFilePanel = function(records) {
	var fileIds = '';
	var url = '';
	var filePanel = Ext.getCmp('resumeFilePanel');
	for ( var i = 0; i < records.length; i++) {
		fileIds += records[i].fileId + ',';
		var del = '</a><img class="img-delete" src="' + __ctxPath
				+ '/images/system/delete.gif" onclick="removeResumeFile(this,'
				+ records[i].fileId + ')"/>';
		url += '<span><a href="#" onclick="FileAttachDetail.show('
						+ records[i].fileId + ')">' + records[i].fileName + del + '&nbsp;|&nbsp;</span>';
	}
	Ext.DomHelper.append(filePanel.body,url);
	Ext.getCmp('conferenceForm.filePath').getCmpByName('filePath').setValue(fileIds.substring(0, fileIds.length - 1));
};

/**
 * 权限设置面板绑定数据
 */
ConferenceForm.setGrantPanel = function(action) {
	var cp = action.data.confPrivilege;
	var updater = '';
	var updaters = '';
	for ( var i = 0; i < cp.length; i++) {
		if (cp[i].rights == 2) {// 修改
			updater += cp[i].userId + ',';
			updaters += cp[i].fullname + ',';
		}
	}
	var fm = Ext.getCmp('conference.grantPanel');
	fm.getCmpByName('updater').setValue(updater.substring(0, updater.length - 1));
	fm.getCmpByName('updaters').setValue(updaters.substring(0, updaters.length - 1));
};

/**
 * 判断会议主持人和记录人是否存在重复,重复false
 */
ConferenceForm.validateCompereAndRecorder = function() {
	var fm = Ext.getCmp('conference.jonerPanel');
	var compere = fm.getCmpByName('conference.compere').value.split(',');
	var recorder = fm.getCmpByName('conference.recorder').value.split(',');
	var bo = true;
	if (compere.length == 1 && recorder.length == 1){	//一对一
		if(compere[0].search(recorder) >=0)
			bo = false;
	} else if (compere.length == 1 && recorder.length != 1){//一对多
		for(var i=0;i < recorder.length; i++){
			if(recorder[i].search(compere) >= 0)
				bo = false;
		}
	} else if (compere.length != 1 && recorder.length == 1){	//多对一
		for(var i = 0 ; i < compere.length ;i++){
			if(compere[i].search(recorder) >= 0)
				bo = false;
		}
	}
	if (bo == false){
		fm.getCmpByName('conference.compereName').focus(true);
		Ext.ux.Toast.msg('操作提示', '对不起，会议主持人和记录人不能重复出现，请重新选择！');
	}
	return bo;
};

/**
 * 上传文件删除
 */
function removeResumeFile(obj, fileId) {
	var fileIds = Ext.getCmp("conferenceFormFilePath");
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
