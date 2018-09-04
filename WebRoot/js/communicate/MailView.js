Ext.ns('MailView');
/**
 * 邮件列表
 */
var MailView = function() {

};
MailView.prototype.getView = function() {
	return new Ext.Panel({
		id : 'MailView',
		title : '[收件箱]',
		//autoScroll : true,
		border : false,
		layout : 'border',
		items : [new Ext.FormPanel({
			height : 40,
			region : 'north',
			frame : false,
			id : 'MailSearchForm',
			layout : 'hbox',
			layoutConfig: {
                padding:'5',
                align:'middle'
            },
			defaults : {
				xtype : 'label',
				margins:{top:0, right:4, bottom:4, left:4}
			},
			items : [{
						text : '查询条件:'
					}, {
						text : '邮件标题'
					}, {
						width : 140,
						xtype : 'textfield',
						name : 'Q_mail.subject_S_LK'
					}, {
						text : '发件人'
					}, {
						width : 80,
						xtype : 'textfield',
						name : 'Q_mail.sender_S_LK'
					}, {
						text : '收件人'
					}, {
						width : 80,
						xtype : 'textfield',
						name : 'Q_appUser.fullname_S_LK'
					}, {
						xtype : 'button',
						text : '查询',
						iconCls : 'search',
						handler : function() {
							var searchPanel = Ext.getCmp('MailSearchForm');
							var gridPanel = Ext.getCmp('MailGrid');
							if (searchPanel.getForm().isValid()) {
								$search({
									searchPanel :searchPanel,
									gridPanel : gridPanel
								});
							}

						}
					}]
		}), this.setup()]
	});

};

MailView.prototype.setFolderId = function(folderId) {
	this.folderId = folderId;
	MailView.folderId = folderId;
};

MailView.prototype.getFolderId = function() {
	return this.folderId;
};

/**
 * 建立视图
 */
MailView.prototype.setup = function() {
	return this.grid();
};
/**
 * 建立DataGrid
 */
MailView.prototype.grid = function() {
	
	var expander = new Ext.ux.grid.RowExpander({
		tpl : new Ext.Template(
		    '<div style="padding:5px 5px 5px 62px;"><b>内容摘要:</b> {content}</div>'
		)
		
    });

	var sm = new Ext.grid.CheckboxSelectionModel();
	var cm = new Ext.grid.ColumnModel({
		columns : [sm, new Ext.grid.RowNumberer(),expander, {
					header : 'mailId',
					dataIndex : 'mailId',
					hidden : true
				}, {
					header : '优先级',
					sortable : false,
					dataIndex : 'importantFlag',
					width : 55,
					renderer : function(value, metadata, record, rowIndex,
							colIndex) {
						var str = '';
						// 优先级标识
						if (value == 2) {
							str += '<img title="重要" src="' + __ctxPath
									+ '/images/flag/mail/important.png"/>';
						} else if (value == 3) {
							str += '<img title="非常重要" src="' + __ctxPath
									+ '/images/flag/mail/veryImportant.png"/>';
						} else {
							str += '<img title="一般" src="' + __ctxPath
									+ '/images/flag/mail/common.png"/>';
						}
						return str;// 考虑所有图标都在这里显示
					}
				}, {
					header : '阅读',
					sortable : false,
					dataIndex : 'mailStatus',
					width : 40,
					renderer : function(value, metadata, record, rowIndex,
							colIndex) {
						var str = '';
						// 阅读标识
						if (value != 0) {
							if (record.data.readFlag == 0) {
								str += '<img title="未读" src="' + __ctxPath
										+ '/images/flag/mail/mail.png"/>';
							} else {
								str += '<img title="已读" src="' + __ctxPath
										+ '/images/flag/mail/mail_open.png"/>';
							}
						} else {// 草稿
							str += '<img title="草稿" src="' + __ctxPath
									+ '/images/flag/mail/mail_draft.png"/>';
						}
						return str;
					}
				}, {
					header : '附件',
					dataIndex : 'fileIds',
					sortable : false,
					width : 40,
					renderer : function(value, metadata, record, rowIndex,
							colIndex) {
						var str = '';
						// 附件标识
						if (value != '' && value!=null && value!='null') {
							str += '<img title="附件" src="' + __ctxPath
									+ '/images/flag/attachment.png"/>';
						}
						return str;
					}
				}, {
					header : '回复',
					dataIndex : 'replyFlag',
					width : 40,
					renderer : function(value, metadata, record, rowIndex,
							colIndex) {
						var str = '';
						// 回复标识
						if (value == 1) {
							str += '<img title="已回复" src="'
									+ __ctxPath
									+ '/images/flag/mail/replyed.png" style="background: white;"/>';
						}
						return str;
					}
				}, {
					header : '邮件主题',
					dataIndex : 'subject',
					sortable : false,
					width : 150
				}, {
					header : '发件人',
					dataIndex : 'sender',
					width : 80
				}, {
					header : '收件人',
					dataIndex : 'recipientNames',
					width : 80,
					renderer : function(value) {
						if (value != '') {
							return value;
						} else {
							return '(收信人未写)';
						}
					}
				}, {
					header : '发信时间',
					width : 80,
					dataIndex : 'sendTime',
					renderer : function(value) {
						return value.substring(0, 16);
					}
				}, {
					header : '管理',
					dataIndex : 'mailId',
					width : 40,
					renderer : function(value, metadata, record, rowIndex,
							colIndex) {
						var _folderId = MailView.folderId;
						var editId = value;
						var boxId = record.data.boxId;
						var status = record.data.mailStatus;
						var str = '<button title="删除" value=" " class="btn-del" onclick="MailView.remove('
								+ boxId + ',' + _folderId + ')">&nbsp</button>';
						str += '&nbsp;<button title="查看" value=" " class="btn-mail_edit" onclick="MailView.edit('
								+ editId
								+ ','
								+ boxId
								+ ','
								+ _folderId
								+ ','
								+ status + ',' + rowIndex + ')">&nbsp</button>';
						return str;
					}
				}],
		defaults : {
			sortable : false,
			menuDisabled : false,
			width : 100
		}
	});

	var store = this.store();
	store.load({
				params : {
					start : 0,
					limit : 25
				}
			});
	
	var grid = new Ext.grid.GridPanel({
				id : 'MailGrid',
				border : false,
				region : 'center',
				tbar : this.topbar(),
				store : store,
				plugins: expander,
				trackMouseOver : true,
				disableSelection : false,
				loadMask : true,
				stripeRows : true,
				cm : cm,
				sm : sm,
				viewConfig : {
					forceFit : true,
					autoFill : true, // 自动填充
					forceFit : true
				},
				bbar : new HT.PagingBar({store : store})
			});

	grid.addListener('rowdblclick', function(grid, rowIndex, e) {
		grid.getSelectionModel().each(function(rec) {
			var _folderId = MailView.folderId;
			MailView.edit(rec.data.mailId, rec.data.boxId,
					_folderId, rec.data.mailStatus, rowIndex);
		});
	});
	return grid;

};

/**
 * 初始化数据
 */
MailView.prototype.store = function() {
	var store = new Ext.data.Store({
				proxy : new Ext.data.HttpProxy({
							url : __ctxPath + '/communicate/listMail.do'
						}),
				reader : new Ext.data.JsonReader({
							root : 'result',
							totalProperty : 'totalCounts',
							id : 'id',
							fields : [
									{
										name : 'boxId',
										type : 'int'
									}, {
										name : 'mailId',
										type : 'int'
									}, {
										name : 'delFlag',
										type : 'int'
									}, {
										name : 'readFlag',
										type : 'int'
									}, {
										name : 'replyFlag',
										type : 'int'
									}, 'importantFlag', 'mailStatus',
									'sendTime','fileIds','subject',
									'recipientNames','content','sender']
						}),
				remoteSort : true
			});
	store.setDefaultSort('boxId', 'desc');
	return store;
};

/**
 * 建立操作的Toolbar
 */
MailView.prototype.topbar = function() {
	var toolbar = new Ext.Toolbar({
				id : 'MailFootBar',
				height : 30,
				bodyStyle : 'text-align:left',
				items : [{
					iconCls : 'btn-mail_remove',
					text : '删除',
					xtype : 'button',
					handler : function() {

						var grid = Ext.getCmp("MailGrid");

						var selectRecords = grid.getSelectionModel()
								.getSelections();

						if (selectRecords.length == 0) {
							Ext.ux.Toast.msg("信息", "请选择要删除的记录！");
							return;
						}
						var ids = Array();
						for (var i = 0; i < selectRecords.length; i++) {
							ids.push(selectRecords[i].data.boxId);
						}
						var _folderId = MailView.folderId;
						MailView.remove(ids, _folderId);
					}
				}, {
					iconCls : 'btn-mail_move',
					text : '移至',
					handler : function() {
						var grid = Ext.getCmp("MailGrid");
						var selectRecords = grid.getSelectionModel()
								.getSelections();
						if (selectRecords.length == 0) {
							Ext.ux.Toast.msg("信息", "请选择要移动的邮件！");
							return;
						}
						var ids = Array();
						for (var i = 0; i < selectRecords.length; i++) {
							ids.push(selectRecords[i].data.boxId);
						}
						MailView.moveTo(ids);
					}
				}, {
					iconCls : 'btn-mail_refresh',
					text : '刷新',
					handler : function() {
						Ext.getCmp('MailGrid').getStore().reload({
									params : {
										start : 0,
										limit : 25
									}
								});
					}
				}]
			});
	return toolbar;
};

/**
 * 删除单个记录
 */
MailView.remove = function(_id, _folderId) {
	if (_folderId == null || _folderId == 'undefined') {
		_folderId = 1;
	}
	var grid = Ext.getCmp("MailGrid");
	var msg = '';
	if (_folderId == 4) {
		msg = '删除箱的记录一旦删除,将不可恢复!';
	}
	Ext.Msg.confirm('信息确认', msg + '您确认要删除该记录吗？', function(btn) {
				if (btn == 'yes') {
					Ext.Ajax.request({
								url : __ctxPath
										+ '/communicate/multiDelMail.do',
								params : {
									ids : _id,
									folderId : _folderId
								},
								method : 'post',
								success : function() {
									Ext.ux.Toast.msg("信息提示", "成功删除所选记录！");
									grid.getStore().reload({
												params : {
													start : 0,
													limit : 25
												}
											});
								}
							});
					var mailCenterView = Ext.getCmp('MailCenterView');
					mailCenterView.remove('ShowMailDetail');
					var mailView = Ext.getCmp('MailView');
					mailView.show();
					mailCenterView.doLayout();
				}
			});
};

/**
 * 显示邮件,或载入草稿
 */
MailView.edit = function(mailId, boxId, folderId, status, rowIndex) {
	var detailToolbar = new MailView.centerViewToolbar(mailId, boxId, folderId,
			rowIndex);
	if (status == 0) {
		var tab = Ext.getCmp('centerTabPanel');
		var mailForm = Ext.getCmp('MailForm');
		if (mailForm == null) {
			mailForm = new MailForm(mailId, boxId, 'draft');
			tab.add(mailForm);
			tab.activate(mailForm);
		} else {
			tab.remove(mailForm);
			mailForm = new MailForm(mailId, boxId, 'draft');
			tab.add(mailForm);
			tab.activate(mailForm);
		}
	} else {
		var mailCenterView = Ext.getCmp('MailCenterView');
		var mailView = Ext.getCmp('MailView');
		mailView.hide();
		var showDetail = new Ext.Panel({
					id : 'ShowMailDetail',
					title : '[邮件信息]',
					border : false,
					tbar : detailToolbar,
					autoLoad : {
						url : __ctxPath + '/communicate/getMail.do?',
						params : {
							mailId : mailId,
							boxId : boxId
						},
						method : 'Post'
					}
				});
		mailCenterView.add(showDetail);
		mailCenterView.doLayout();
	}// else
};

/**
 * 显示邮件信息时功能栏
 * 
 * @param {}
 *            mailId
 * @param {}
 *            boxId
 * @return {}
 */
MailView.centerViewToolbar = function(mailId, boxId, _folderId, rowIndex) {
	var toolbar = new Ext.Toolbar({
		height : 30,
		defaultType : 'button',
		bodyStyle : 'text-align:left',
		items : [{
					iconCls : 'btn-mail_back',
					text : '返回',
					handler : function() {
						var mailCenterView = Ext.getCmp('MailCenterView');
						mailCenterView.remove('ShowMailDetail');
						var mailView = Ext.getCmp('MailView');
						mailView.show();
						mailCenterView.doLayout();
					}
				}, {
					iconCls : 'btn-mail_reply',
					text : '回复',
					handler : function() {
						var tab = Ext.getCmp('centerTabPanel');
						var mailForm = Ext.getCmp('MailForm');
						if (mailForm == null) {
							mailForm = new MailForm(mailId, boxId, 'reply');
							tab.add(mailForm);
							tab.activate(mailForm);
						} else {
							tab.remove(mailForm);
							mailForm = new MailForm(mailId, boxId, 'reply');
							tab.add(mailForm);
							tab.activate(mailForm);
						}
					}
				}, {
					iconCls : 'btn-mail_resend',
					text : '转发',
					handler : function() {
						var tab = Ext.getCmp('centerTabPanel');
						var mailForm = Ext.getCmp('MailForm');
						if (mailForm == null) {
							mailForm = new MailForm(mailId, boxId, 'forward');
							tab.add(mailForm);
							tab.activate(mailForm);
						} else {
							tab.remove(mailForm);
							mailForm = new MailForm(mailId, boxId, 'forward');
							tab.add(mailForm);
							tab.activate(mailForm);
						}
					}
				}, {
					iconCls : 'btn-mail_remove',
					text : '删除',
					handler : function() {
						MailView.remove(boxId, _folderId);
					}
				}, {
					iconCls : 'btn-mail_move',
					text : '移至',
					handler : function() {
						MailView.moveTo(boxId);
					}
				},'->', {
					iconCls : 'btn-up',
					text : '较旧一封',
					handler : function() {
						var haveNextMailFlag = document
								.getElementById('__haveNextMailFlag');
						if (haveNextMailFlag != null
								&& haveNextMailFlag.value != 'endPre') {
							var userId = curUserInfo.userId;
							var boxId = document.getElementById('__curBoxId').value;
							Ext.getCmp('ShowMailDetail').load({
										url : __ctxPath
												+ '/communicate/getMail.do?',
										params : {
											boxId : boxId,
											opt : '_pre',
											folderId : _folderId
										},
										method : 'Post'
									});
						} else {
							Ext.ux.Toast.msg('提示信息', '这里已经是第一封邮件了');
						}
					}
				}, {
					iconCls : 'btn-last',
					text : '较新一封',
					handler : function() {
						var haveNextMailFlag = document
								.getElementById('__haveNextMailFlag');
						if (haveNextMailFlag != null
								&& haveNextMailFlag.value != 'endNext') {
							var userId = curUserInfo.userId;
							var boxId = document.getElementById('__curBoxId').value;
							Ext.getCmp('ShowMailDetail').load({
										url : __ctxPath
												+ '/communicate/getMail.do?',
										params : {
											boxId : boxId,
											opt : '_next',
											folderId : _folderId
										},
										method : 'Post'
									});
						} else {
							Ext.ux.Toast.msg('提示信息', '这里已经是最后一封邮件了');
						}
					}
				}]
	});
	return toolbar;
};

MailView.moveTo = function(id) {
	var _moveFormPanel = new MailView.moveFormPanel(id);
	var selectFolder = new Ext.Window({
				x:350,
				y:100,
				width : 340,
				height : 300,
				title : '移动邮件',
				iconCls : 'btn-mail_move',
				modal : true,
				buttonAlign : 'center',
				plain : true,
				layout : 'fit',
				border : false,
				bodyStyle : 'padding:5px;',
				items : [_moveFormPanel],
				buttons : [{
					text : '确定移动',
					iconCls : 'btn-mail_move',
					handler : function() {
						var folderId = Ext.getCmp('folderId');
						if (folderId == '' && folderId != null
								&& folderId != 'undefined') {
							Ext.ux.Toast.msg('操作信息', '请先选择文件夹');
						} else {
							var moveFolderForm = Ext.getCmp("moveFolderForm");
							moveFolderForm.getForm().submit({
								waitMsg : '正在提交用户信息',
								success : function(moveFolderForm, o) {
									// 成功之后关闭窗口,显示邮件列表Panel,reload()
									Ext.ux.Toast.msg('操作信息', '移动成功！');
									selectFolder.close();
									var mailCenterView = Ext
											.getCmp('MailCenterView');
									mailCenterView.remove('ShowMailDetail');
									var mailView = Ext.getCmp('MailView');
									Ext.getCmp('MailGrid').getStore().reload({
												params : {
													start : 0,
													limit : 25
												}
											});
									mailView.show();
									mailCenterView.doLayout();
								},
								failure : function(moveFolderForm, o) {
									// 移动失败后提示失败原因
									Ext.ux.Toast.msg('提示信息', o.result.msg);
								}
							});
						}
					}
				}, {
					text : '取消',
					iconCls : 'btn-del',
					handler : function() {
						selectFolder.close();
					}
				}]
			});
	selectFolder.show();

};
MailView.moveFormPanel = function(_id) {
	var treePanel = new Ext.tree.TreePanel({
				// id:'',
				title : '请选择文件夹',
				loader : new Ext.tree.TreeLoader({
							url : __ctxPath + '/communicate/listMailFolder.do'
						}),
				root : new Ext.tree.AsyncTreeNode({
							expanded : true
						}),
				rootVisible : false,
				listeners : {
					'click' : function(node) {
						if (node != null && node.id != 0) {
							Ext.getCmp('dispalyFolderName').setValue(node.text);
							Ext.getCmp('folderId').setValue(node.id);
						}
					}
				}
			});
	var formPanel = new Ext.FormPanel({
				url : __ctxPath + '/communicate/moveMail.do',
				layout : 'table',
				id : 'moveFolderForm',
				frame : true,
				defaultType : 'textfield',
				layoutConfig : {
					columns : 1
				},
				defaults : {
					width : 296
				},
				items : [{
							xtype : 'label',
							text : '移至:'
						}, {
							id : 'dispalyFolderName',
							readOnly : true
						}, {
							xtype : 'hidden',
							id : 'folderId',
							name : 'folderId'
						}, {
							id : 'boxIds',
							name : 'boxIds',
							xtype : 'hidden',
							value : _id
						}, {
							xtype : 'panel',
							items : [treePanel]
						}]
			});
	return formPanel;
};
