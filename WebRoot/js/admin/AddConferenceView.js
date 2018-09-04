/**
 * @description 会议申请
 * @author YHZ
 * @company www.credit-software.com
 * @dateTime 2010-11-5PM
 */
AddConferenceView = Ext.extend(Ext.Panel, {
	// store
	store : null,
	gridPanel : null,
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

	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		// 必须先初始化组件
		this.initUIComponents();
		AddConferenceView.superclass.constructor.call(this, {
			id : 'AddConferenceViewWin',
			layout : 'form',
			region : 'center',
			title : '会议申请',
			iconCls : 'menu-conference_add',
			tbar : new Ext.Toolbar({
				heigth : 30,
				defaultType : 'button',
				items : [{
					text : '发送会议通知',
					iconCls : 'btn-mail_send',
					handler : this.send.createCallback(this)
				}, '-', {
					text : '暂存会议',
					iconCls : 'temp',
					handler : this.save.createCallback(this)
				}, '-', {
					text : '清空',
					iconCls : 'reset',
					handler : this.reset
				}, '-', {
					text : '取消',
					iconCls : 'btn-cancel',
					handler : this.close
				}]
			}),
			modal : true,
			maximizable : true,
			bodyStyle : 'padding : 5px 5px 5px 5px',
			autoScroll : false,
			items : this.formPanel,
			keys : {
				key : Ext.EventObject.ENTER,
				fn : this.send.createCallback(this),
				scope : this
			}
		});
	},// end of the constructor
	// 初始化组件
	initUIComponents : function() {

		// 会议信息设置
		this.basePanel = new Ext.form.FieldSet({
			id : 'addConference.basePanel',
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
						width : '80%'
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
						emptyText : '--请选择会议类型--',
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
								var fm = Ext.getCmp('addConference.basePanel');
								fm.getCmpByName('conference.typeId').setValue(cbo.value);
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
						defaults : {
							xtype : 'checkbox',
							margins : '0 20 0 10'
						},
						items : [{
							columnWidth : .5,
							anchor : '99%',
							boxLabel : '普通留言',
							name : 'conference.isEmail',
							inputValue : 1,
							checked : true
						}, {
							columnWidth : .5,
							anchor : '99%',
							boxLabel : '手机留言',
							name : 'conference.isMobile',
							inputValue : 1,
							width : 100
						} ]
					} ]
				},{
					anchor : '99%',
					layout : 'form',
					columnWidth : .5,
					items : [{
						anchor : '99%',
						xtype : 'combo',
						hiddenName : 'conference.roomId',
						fieldLabel : '会议室名称',
						valueField : 'roomId',
						displayField : 'roomName',
						mode : 'local',
						editable : false,
						emptyText : '--请选择会议室--',
						triggerAction : 'all',
						forceSelection : true,
						allowBlank : false,
						store : new Ext.data.SimpleStore({
							url : __ctxPath + '/admin/getBoardrooConference.do',
							autoLoad : true,
							fields : ['roomId', 'roomName' ]
						}),
						listeners : {
							select : function(cbo,record,index) {
								var fm = Ext.getCmp('addConference.basePanel');
								fm.getCmpByName('conference.roomName').setValue(cbo.getRawValue());
								fm.getCmpByName('conference.roomLocation').setValue(cbo.getRawValue());
							}
						}
					}, {
						anchor : '99%',
						xtype : 'textfield',
						fieldLabel : '会议室',
						name : 'conference.roomName'
					}, {
						anchor : '99%',
						xtype : 'textfield',
						fieldLabel : '地址',
						name : 'conference.roomLocation',
						allowBlank : false,
						maxLength : '128'
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
						} ]
					} ]
				} ]
			} ]
		}); // end of this basePanel
		
		// 会议参加人员jonerPanel
		this.jonerPanel = new Ext.form.FieldSet({
			id : 'addConference.jonerPanel',
			title : '参加人员',
			layout : 'form',
			autoScroll : true,
			border : true,
			items : [{
				anchor : '99%',
				fieldLabel : '主持人',
				xtype : 'container',
				layout : 'column',
				border : false,
				defaults : {
					border : false
				},
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
					width : 75,
					xtype : 'button',
					text : '请选择',
					iconCls : 'btn-user-sel',
					handler : function() {
						UserSelector.getView(
							function(userId,fullName) {
								var fm = Ext.getCmp('addConference.jonerPanel');
								fm.getCmpByName('conference.compere').setValue(userId);
								fm.getCmpByName('conference.compereName').setValue(fullName);
						},false).show();
					}
				}]
			},{
				anchor : '99%',
				fieldLabel : '记录人',
				xtype : 'container',
				layout : 'column',
				border : false,
				defaults : {
					border : false
				},
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
				}, {
					width : 75,
					xtype : 'button',
					text : '请选择',
					iconCls : 'btn-user-sel',
					handler : function() {
						UserSelector.getView(
							function(userId,fullName) {
								var fm = Ext.getCmp('addConference.jonerPanel');
								fm.getCmpByName('conference.recorder').setValue(userId);
								fm.getCmpByName('conference.recorderName').setValue(fullName);
						},false).show();
					}
				}]
			},{
				anchor : '99%',
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
					width : 75,
					xtype : 'button',
					text : '请选择',
					iconCls : 'btn-user-sel',
					handler : function() {
						UserSelector.getView(
							function(userId,fullName) {
								var fm = Ext.getCmp('addConference.jonerPanel');
								fm.getCmpByName('conference.attendUsers').setValue(userId);
								fm.getCmpByName('conference.attendUsersName').setValue(fullName);
						},false).show();
					}
				}]
			}]
		}); // end of this jonerPanel

		// 权限grantPanel
		this.grantPanel = new Ext.form.FieldSet({
			id : 'addConference.grantPanel',
			title : '权限设置',
			region : 'center',
			layout : 'form',
			border : true,
			items : [{
				anchor : '99%',
				xtype : 'container',
				fieldLabel : '修改人',
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
				},{
					width : 75,
					xtype : 'button',
					text : '请选择',
					iconCls : 'btn-user-sel',
					handler : function() {
						UserSelector.getView(
							function(userId,fullName) {
								var fm = Ext.getCmp('addConference.grantPanel');
								fm.getCmpByName('updater').setValue(userId);
								fm.getCmpByName('updaters').setValue(fullName);
						},false).show();
					}
				}]
			}, {
				anchor : '99%',
				fieldLabel : '审核人',
				xtype : 'container',
				layout : 'column',
				defaults : {
					border : false
				},
				border : false,
				items : [{
					xtype : 'hidden',
					name : 'conference.checkUserId'
				}, {
					columnWidth : 1,
					width : '90%',
					xtype : 'textfield',
					name : 'conference.checkName',
					readOnly : true,
					allowBlank : false,
					maxLength : 64
				},{
					width : 75,
					xtype : 'button',
					text : '请选择',
					iconCls : 'btn-user-sel',
					handler : function() {
					UserSelector.getView(
						function(userId,fullName) {
							var fm = Ext.getCmp('addConference.grantPanel');
							fm.getCmpByName('conference.checkUserId').setValue(userId);
							fm.getCmpByName('conference.checkName').setValue(fullName);
						},true).show();
					}
				}]
			}, {
				layout : 'column',
				height : 26,
				border : false
			}]
		}); // end of this grantPanel

		// 会议内容contextPanel
		this.contextPanel = new Ext.form.FieldSet( {
			title : '时间和内容设置',
			layout : 'column',
			columnWidth : 1,
			border : true,
			items : [ {
				columnWidth : .5,
				anchor : '99%',
				layout : 'form',
				border : false,
				items : [ {
					anchor : '99%',
					xtype : 'datetimefield',
					format : 'Y-m-d H:i:s',
					editable : false,
					name : 'conference.startTime',
					minValue : new Date(),
					fieldLabel : '开始时间',
					allowBlank : false
				} ]
			}, {
				columnWidth : .5,
				anchor : '99%',
				layout : 'form',
				border : false,
				items : [ {
					anchor : '99%',
					name : 'conference.endTime',
					xtype : 'datetimefield',
					format : 'Y-m-d H:i:s',
					minValue : new Date(),
					editable : false,
					fieldLabel : '结束时间',
					allowBlank : false
				} ]
			}, {
				columnWidth : 1,
				anchor : '100%',
				layout : 'form',
				height : 160,
				border : false,
				items : [ {
					anchor : '98%',
					name : 'conference.confContent',
					xtype : 'htmleditor',
					height : 150,
					fieldLabel : '会议内容',
					autoScroll : true,
					allowBlank : false,
					maxLength : 4000
				} ]
			} ]
		}); // end of this contextPanel

		// 附件
		this.filePanel = new Ext.form.FieldSet( {
			id : 'addConferenceView.filePath',
			layout : 'form',
			region : 'center',
			title : '附件信息',
			bodyStyle : 'padding : 5px 5px 5px 5px',
			items : [{
				xtype : 'hidden',
				name : 'filePath'
			}, {
				fieldLabel : '附件信息',
				xtype : 'container',
				columnWidth : 1,
				layout : 'column',
				border : false,
				items : [{
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
		}); // end of this filePanel

		this.formPanel = new Ext.FormPanel( {
			id : 'AddConferenceViewPanel',
			autoScroll : true,
			layout : 'form',
			region : 'center',
			bodyStyle : 'padding : 10px 10px 10px 10px;',
			border : false,
			defaults : {
				readOnly : true
			},
			items : [{
				id : 'addConferenceViewAllfieldset',
				xtype : 'fieldset',
				title : '会议申请',
				bodyStyle : 'padding:5px 5px 5px 5px',
				layout : 'form',
				buttonAlign : 'center',
				items : [this.basePanel, this.contextPanel,{
					layout : 'column',
					border : false,
					columnWidth : 1,
					defaults : {
						border : false
					},
					items : [ {
						columnWidth : .5,
						layout : 'form',
						items : [ this.jonerPanel ]
					}, {
						columnWidth : .5,
						layout : 'form',
						items : [ this.grantPanel]
					} ]
				}, this.filePanel ],
				buttons : [{
					text : '发送会议通知',
					iconCls : 'btn-mail_send',
					handler : this.send.createCallback(this)
				}, {
					text : '暂存会议',
					iconCls : 'temp',
					handler : this.save.createCallback(this)
				}, {
					text : '清空',
					iconCls : 'reset',
					handler : this.reset
				}]
			}]
		});// end of this formPanel
	}, // end of this initUIComponent
	/**
	 * 重置
	 */
	reset : function() {
		Ext.getCmp('AddConferenceViewPanel').getForm().reset();
		Ext.getCmp('resumeFilePanel').body.update('');
	},
	
	/**
	 * 保存
	 */
	save : function(window) {
		var fm = Ext.getCmp('AddConferenceViewPanel');
		if (fm.getForm().isValid()) {
			// 开会时间验证
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
			if (!AddConferenceView.validateCompereAndRecorder())
				return;
			fm.getForm().submit({
				url : __ctxPath + '/admin/tempConference.do',
				method : 'post',
				waitMsg : '数据正在保存，请稍后...',
				success : function(fp, action) {
					Ext.ux.Toast.msg('操作信息','成功保存信息！');
					Ext.getCmp('AddConferenceViewPanel').getForm().reset();
					Ext.getCmp('resumeFilePanel').body.update('');
					var tabs = Ext.getCmp('centerTabPanel');
					tabs.remove('AddConferenceViewWin');
					App.clickTopTab('ZanCunConferenceView');
				},
				failure : function(fp, action) {
					Ext.MessageBox.show( {
						title : '操作信息',
						msg : '对不起，会议申请失败！',
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
	send : function(window){
		var fm = Ext.getCmp('AddConferenceViewPanel');
		if (fm.getForm().isValid()) {
			// 开会时间验证
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
			if (!AddConferenceView.validateCompereAndRecorder())
				return;
			//提交
			fm.getForm().submit({
				url : __ctxPath + '/admin/sendConference.do',
				method : 'post',
				waitMsg : '数据正在发送，请稍后...',
				success : function(fp, action) {
					Ext.ux.Toast.msg('操作信息','成功发送会议申请信息,等待审批！');
					Ext.getCmp('resumeFilePanel').body.update('');
					Ext.getCmp('AddConferenceViewPanel').getForm().reset();
					var tabs = Ext.getCmp('centerTabPanel');
					tabs.remove('AddConferenceViewWin');
					App.clickTopTab('DaiConfApplyView');
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
				var fileIds = '';
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
				Ext.getCmp('addConferenceView.filePath').getCmpByName('filePath').setValue(fileIds.substring(0,fileIds.length - 1));
			}
		});
		dialog.show('querybtn');
	},
	
	/**
	 * 删除上传文件
	 */
	delLoadFile : function() {
		var fm = Ext.getCmp('addConferenceView.filePath');
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
	},
	
	/**
	 * 关闭
	 */
	close : function(){
		Ext.getCmp('centerTabPanel').remove('AddConferenceViewWin');
	}
});

/**
* 判断会议主持人和记录人是否存在重复,重复false
* 主持人---记录人,一对一，一对多,多对一时才会重复
*/
AddConferenceView.validateCompereAndRecorder = function() {
	var fm = Ext.getCmp('addConference.jonerPanel');
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
		Ext.ux.Toast.msg('操作提示', '对不起，会议主持人和记录人不能出现重复，请重新选择！');
	}
	return bo;
};


/**
* 上传文件删除
*/
function removeResumeFile(obj, fileId) {
	var fileIds = Ext.getCmp("addConferenceView.filePath").getCmpByName('filePath');
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