Ext.ns('AddConfSummaryView');

/**
 * @description 新建会议纪要
 * @author YHZ
 * @data 2010-9-29 PM
 */
AddConfSummaryView = Ext.extend(Ext.Panel, {
	// top工具栏
	topbar : null,
	form : null,
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		// 加载组件
		this.initUIComponent();
		AddConfSummaryView.superclass.constructor.call(this, {
			id : 'AddConfSummaryViewPanel',
			title : '创建会议纪要',
			iconCls : 'menu-confSummary_add',
			header : true,
			region : 'center',
			layout : 'border',
			bodyStyle : 'padding:5px 5px 5px 5px',
			items : [ this.form ]
		});
	},// end of constructor
	initUIComponent : function() {
		this.topbar = new Ext.Toolbar( {
			id : 'AddConfSummaryViewTool',
			heigth : 30,
			defaultType : 'button',
			items : [ {
				iconCls : 'btn-mail_send',
				text : '发送',
				handler : this.send
			}, '-', {
				iconCls : 'btn-save',
				text : '保存',
				handler : this.save
			}, '-', {
				text : '取消',
				iconCls : 'btn-cancel',
				handler : this.close,
				scope : this
			}]
		});
		this.form = new Ext.form.FormPanel( {
			id : 'AddConfSummaryViewForm',
			region : 'center',
			layout : 'form',
			tbar : this.topbar,
			bodyStyle : 'padding:10px 10px 0 10px;',
			frame : false,
			border : true,
			defaultType : 'texfield',
			items : [ {
				xtype : 'fieldset',
				title : '新增会议纪要信息',
				width : '100%',
				anchor : '100%',
				layout : 'form',
				buttonAlign : 'center',
				defaults : {
					margins : {
						top : 5,
						left : 5,
						bottom : 5,
						right : 5
					}
				},
				items : [ {
					xtype : 'hidden',
					name : 'confSummary.confId.confId'
				},{
					anchor : '99%',
					fieldLabel : '会议标题',
					xtype : 'container',
					layout : 'column',
					style : 'margin-bottom:5px;',
					items : [{
						columnWidth : 1,
						anchor : '99%',
						xtype : 'textfield',
						name : 'confTopic',
						readOnly : true,
						allowBlank : false,
						maxLength : 128
					}, {
						xtype : 'button',
						text : '请选择',
						iconCls : 'btn-user-sel',
						handler : function() {
							ConferenceSelector.getView(function(confId,confTopic) {
								var fm = Ext.getCmp('AddConfSummaryViewForm');
								fm.getCmpByName('confSummary.confId.confId').setValue(confId);
								fm.getCmpByName('confTopic').setValue(confTopic);
							},true).show();
						}
					}]
				}, {
					anchor : '99%',
					xtype : 'htmleditor',
					fieldLabel : '纪要内容',
					id : 'sumContent',
					name : 'confSummary.sumContent',
					height : 200,
					allowBlank : false,
					maxLength : 4000
				}, {
					xtype : 'hidden',
					name : 'fileIds'
				}, {
					anchor : '99%',
					fieldLabel : '附件',
					xtype : 'container',
					layout : 'column',
					defaults : {
						border : false
					},
					border : false,
					items : [{
						columnWidth : 1,
						anchor : '100%',
						layout : 'form',
						border : false,
						items : [{
							anchor : '100%',
							xtype : 'panel',
							id : 'filePathPanel',
							frame : false,
							border : true,
							height : 50,
							autoScroll : true,
							html : ''
						}]
					}, {
						width : 75,
						defaultType : 'button',
						items : [{
							text : '添加附件',
							iconCls : 'menu-attachment',
							handler : this.upLoadFile
						}, {
							text : '清除附件',
							iconCls : 'reset',
							handler : this.delLoadFile
						}]
					}]
				}],
				buttons : [{
					text : '发送',
					iconCls : 'btn-mail_send',
					handler : this.send
				}, {
					text : '保存',
					iconCls : 'btn-save',
					handler : this.save
				}, {
					text : '取消',
					iconCls : 'btn-cancel',
					handler : this.close,
					scope : this
				}]
			} ]
		});
	},// end of initUIComponent

	/**
	 * 保存
	 */
	save : function() {
		var fm = Ext.getCmp('AddConfSummaryViewForm');
		if (fm.getForm().isValid()) {
			fm.getForm().submit( {
				url : __ctxPath + '/admin/saveConfSummary.do',
				method : 'post',
				success : function(form, action) {
					Ext.ux.Toast.msg('操作提示', '恭喜您，保存会议纪要成功！');
					fm.getForm().reset();
					Ext.getCmp('filePathPanel').update();
					var tabs = Ext.getCmp('centerTabPanel');
					tabs.remove('AddConfSummaryViewPanel');
					App.clickTopTab('ConfSummaryView');
					Ext.getCmp('ConfSummaryGrid').getStore().reload();
				},
				failure : function(fm, action) {
					Ext.ux.Toast.msg('操作提示', action.result.msg);
					Ext.getCmp('sumContent').focus(true);
				}
			});
		}
	},

	/**
	 * 发送
	 */
	send : function() {
		var fm = Ext.getCmp('AddConfSummaryViewForm');
		if (fm.getForm().isValid()) {
			fm.getForm().submit( {
				url : __ctxPath + '/admin/sendConfSummary.do',
				method : 'post',
				success : function() {
					Ext.ux.Toast.msg('操作提示', '恭喜您，会议纪要发送成功！');
					fm.getForm().reset();
					Ext.getCmp('filePathPanel').update();
					var tabs = Ext.getCmp('centerTabPanel');
					tabs.remove('AddConfSummaryViewPanel');
					App.clickTopTab('ConfSummaryView');
					Ext.getCmp('ConfSummaryGrid').getStore().reload();
				},
				failure : function(fm, action) {
					Ext.ux.Toast.msg('操作提示', action.result.msg);
					Ext.getCmp('sumContent').focus(true);
				}
			});
		}
	},

	/**
	 * 文件上传
	 */
	upLoadFile : function() {
		var dialog = App.createUploadDialog( {
			url : __ctxPath+'/file-upload',
			file_cat : 'admin/confSummary',
			callback : function(arr) {
				var str = '';
				var filePath = '';
				for ( var i = 0; i < arr.length; i++) {
					str += arr[i].fileId + ",";
					filePath += '<span><a href="#" onclick="FileAttachDetail.show('
						+ arr[i].fileId + ')">'
						+ arr[i].fileName + '</a> <img class="img-delete" src="' + __ctxPath
						+ '/images/system/delete.gif" onclick="removeContractAttach(this,'
						+ arr[i].fileId + ')"/>&nbsp;|&nbsp;</span>';
				}
				str = str.substring(0, str.length - 1);
				var fm = Ext.getCmp('AddConfSummaryViewForm');
				fm.getCmpByName('fileIds').setValue(str);
				var filePathPanel = Ext.getCmp('filePathPanel');
				Ext.DomHelper.append(filePathPanel.body,filePath);
			}
		});
		dialog.show('querybtn');
	},

	/**
	 * 删除上传文件
	 */
	delLoadFile : function() {
		var fm = Ext.getCmp('AddConfSummaryViewForm');
		var fileIds = fm.getCmpByName('fileIds').value;
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
							fm.getCmpByName('fileIds').setValue('');
							Ext.getCmp('filePathPanel').update();
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
		Ext.getCmp('centerTabPanel').remove('AddConfSummaryViewPanel');
	}
});

function removeContractAttach(obj, fileId) {
	var fileIds = Ext.getCmp("AddConfSummaryViewForm").getCmpByName('fileIds');
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