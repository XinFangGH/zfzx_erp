/**
 * @author YHZ
 * @createtime 2010-11-13
 * @class ConfSummaryForm
 * @extends Ext.Window
 * @description ConfSummary表单
 * @company 智维软件
 */
ConfSummaryForm = Ext.extend(Ext.Window,{
	// 内嵌FormPanel
	formPanel : null,
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		// 必须先初始化组件
		this.initUIComponents();
		ConfSummaryForm.superclass.constructor.call(this, {
			id : 'ConfSummaryFormWin',
			iconCls : 'menu-confSummary',
			layout : 'fit',
			items : this.formPanel,
			modal : true,
			height : 350,
			minHeight : 350,
			width : 420,
			minWidth : 420,
			maximizable : true,
			title : '编辑会议纪要',
			buttonAlign : 'center',
			buttons : this.buttons,
			keys : {
				key : Ext.EventObject.ENTER,
				fn : this.save.createCallback(this.formPanel, this, 0),
				scope : this
			}
		});
	},// end of the constructor
	// 初始化组件
	initUIComponents : function() {
		this.formPanel = new Ext.FormPanel({
			layout : 'form',
			bodyStyle : 'padding:10px 10px 10px 10px',
			border : false,
			id : 'ConfSummaryForm',
			defaults : {
				anchor : '98%,98%'
			},
			items : [{
				name : 'confSummary.sumId',
				xtype : 'hidden',
				value : this.sumId == null ? '' : this.sumId
			}, {
				anchor : '99%',
				fieldLabel : '会议议题',
				name : 'confSummary.confId.confTopic',
				xtype : 'textfield',
				readOnly : true
			}, {
				xtype : 'hidden',
				name : 'confSummary.confId.confId'
			}, {
				anchor : '99%',
				fieldLabel : '纪要创建时间',
				name : 'confSummary.createtime',
				xtype : 'textfield',
				readOnly : true
			}, {
				anchor : '99%',
				xtype : 'container',
				layout : 'column',
				fieldLabel : '纪要人',
				items : [{
					columnWidth : 1,
					anchor : '100%',
					xtype : 'textfield',
					name : 'confSummary.creator',
					readOnly : true,
					allowBlank : false,
					maxLength : 256
				}, {
					xtype : 'button',
					text : '请选择',
					iconCls : 'btn-user-sel',
					handler : function() {
						UserSelector.getView(function(userId,fullName) {
								Ext.getCmp('ConfSummaryForm').getCmpByName('confSummary.creator').setValue(fullName);
						}).show();
					}
				} ]
			}, {
				anchor : '99%',
				xtype : 'textarea',
				fieldLabel : '纪要内容',
				name : 'confSummary.sumContent',
				allowBlank : false,
				maxLength : 4000
			}, {
				anchor : '99%',
				fieldLabel : '状态',
				hiddenName : 'confSummary.status',
				xtype : 'combo',
				mode : 'local',
				readOnly : true,
				editable : false,
				triggerAction : 'all',
				store : [ [ '0', '未发送' ], [ '1', '发送' ] ]
			}, {
				anchor : '99%',
				fieldLabel : '附件',
				xtype : 'container',
				layout : 'column',
				border : false,
				defaults : { 
					border : false 
				},
				items : [{
					columnWidth : 1,
					layout : 'form',
					border : false,
					items : [{
						anchor : '100%',
						xtype : 'panel',
						id : 'confSummaryFormFilePanel',
						frame : false,
						border : true,
						bodyStyle : 'padding:4px 4px 4px 4px',
						height : 50,
						autoScroll : true,
						html : ''
					}, {
						xtype : 'hidden',
						name : 'fileIds'
					}]
				}, {
					items : [{
						xtype : 'button',
						text : '添加附件',
						iconCls:'menu-attachment',
						handler : this.upLoadFile
					}, {
						xtype : 'button',
						text : '清除附件',
						iconCls : 'reset',
						handler : this.delLoadFile
					}]
				}]
			} ]
		});

		// 加载表单对应的数据
		if (this.sumId != null && this.sumId != 'undefined') {
			this.formPanel.loadData({
				url : __ctxPath + '/admin/getConfSummary.do?sumId=' + this.sumId,
				root : 'data',
				preName : 'confSummary',
				success : function(response, obj) {
					var cf = Ext.util.JSON.decode(response.responseText);
					ConfSummaryForm.setFilePanel(cf.data.attachFiles);
				},
				failure : function(form, action) {
					Ext.ux.Toast.msg('操作提示','对不起，数据加载有误！');
				}
			});
		}
		// 初始化功能按钮
		this.buttons = [{
			text : '发送',
			iconCls : 'btn-mail_send',
			handler : this.save.createCallback(this.formPanel, this, 1)
		}, {
			text : '保存',
			iconCls : 'btn-save',
			handler : this.save.createCallback(this.formPanel, this, 0)
		}, {
			text : '取消',
			iconCls : 'btn-cancel',
			handler : this.cancel.createCallback(this)
		} ];
	},// end of the initcomponents

	/**
	 * 取消
	 * 
	 * @param {}
	 *            window
	 */
	cancel : function(window) {
		window.close();
	},
	/**
	 * 保存记录,type=0保存，否则，发送
	 */
	save : function(formPanel, window, type) {
		var url = type == 0 ? __ctxPath + '/admin/editConfSummary.do'
				: __ctxPath + '/admin/sendConfSummary.do';
		if (formPanel.getForm().isValid()) {
			formPanel.getForm().submit( {
				url : url,
				method : 'post',
				waitMsg : '正在提交数据，请稍后...',
				success : function(fp, action) {
					Ext.ux.Toast.msg('操作信息', type == 0 ? '会议纪要保存成功！' : '会议纪要发送成功！');
					var gridPanel = Ext.getCmp('ConfSummaryGrid');
					if (gridPanel != null) {
						gridPanel.getStore().reload();
					}
					window.close();
				},
				failure : function(fp, action) {
					Ext.MessageBox.show( {
						title : '操作信息',
						msg : '信息发送出错，请联系管理员！',
						buttons : Ext.MessageBox.OK,
						icon : Ext.MessageBox.ERROR
					});
					window.close();
				}
			});
		}
	},// end of save
			
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
				var fm = Ext.getCmp('ConfSummaryForm');
				fm.getCmpByName('fileIds').setValue(str);
				var filePathPanel = Ext.getCmp('confSummaryFormFilePanel');
				Ext.DomHelper.append(filePathPanel.body,filePath);
			}
		});
		dialog.show('querybtn');
	},
	
	/**
	 * 删除上传文件
	 */
	delLoadFile : function() {
		var fm = Ext.getCmp('ConfSummaryForm');
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
							Ext.getCmp('confSummaryFormFilePanel').update();
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
 * 上传文件删除
 */
function removeResumeFile(obj, fileId) {
	var fileIds = Ext.getCmp('ConfSummaryForm').getCmpByName('fileIds');
	var value = fileIds.getValue();
	if (value.indexOf(',') < 0) {// 仅有一个附件
		fileIds.setValue('');
	} else {
		value = value.replace(',' + fileId, '').replace(fileId + ',', '');
		fileIds.setValue(value);
	}
	alert(fileIds);
	var el = Ext.get(obj.parentNode);
	el.remove();
};
/**
 * 显示附件列表
 */
ConfSummaryForm.setFilePanel = function(records) {
	var fileIds = '';
	var filePanel = Ext.getCmp('confSummaryFormFilePanel');
	for ( var i = 0; i < records.length; i++) {
		fileIds += records[i].fileId + ',';
		var del = '<img class="img-delete" src="'+ __ctxPath
				+ '/images/system/delete.gif" onclick="removeResumeFile(this,'
				+ records[i].fileId + ')"/>';
		Ext.DomHelper.append(filePanel.body,
				'<span><a href="#" onclick="FileAttachDetail.show('
						+ records[i].fileId + ')">' + records[i].fileName + '</a>'
						+ del + '&nbsp;|&nbsp;</span>');
	}
	Ext.getCmp('ConfSummaryForm').getCmpByName('fileIds').setValue(fileIds.substring(0, fileIds.length - 1));
};