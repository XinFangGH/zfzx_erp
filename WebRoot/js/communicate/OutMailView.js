Ext.ns('OutMailView');
/**
 * 邮件列表
 */

var OutMailView = function() {

};
OutMailView.prototype.getView = function() {
	return new Ext.Panel({
		id : 'OutMailView',
		title : '[收件箱]',
		autoScroll : true,
		border : false,
		layout : 'border',
		items : [new Ext.FormPanel({
			id : 'OutMailSearchForm',
			height : 40,
			region : 'north',
			frame : false,
			layout : 'column',
			layoutConfig: {
                
                align:'middle'
            },
            bodyStyle : 'padding:10px 10px 10px 10px',
			defaults : {
				xtype : 'label'//,
				//margins:{top:4, right:4, bottom:4, left:4}
			},
			items : [{
						text : '查询条件:'
					}, {
						text : '邮件标题'
					}, {
						width : 80,
						xtype : 'textfield',
						name : 'Q_title_S_LK'
					}, {
						text : '发件人'
					}, {
						width : 80,
						xtype : 'textfield',
						name : 'Q_senderName_S_LK'
					}, {
						text : '收件人'
					}, {
						width : 80,
						xtype : 'textfield',
						name : 'Q_receiverNames_S_LK'
					}, {
						
						xtype : 'hidden',
						id:'Q_outMailFolder.folderId_L_EQ',
						name : 'Q_outMailFolder.folderId_L_EQ'
					}, {
						xtype : 'button',
						text : '查询',
						iconCls : 'search',
						handler : function() {
							var searchPanel = Ext.getCmp('OutMailSearchForm');
							var grid = Ext.getCmp('OutMailGrid');
							if (searchPanel.getForm().isValid()) {
								
								searchPanel.getForm().submit({
									waitMsg : '正在提交查询',
									url : __ctxPath
											+ '/communicate/listOutMail_.do',
									params: {
										folderId: Ext.getCmp('OutMailSearchForm').getForm().findField("Q_outMailFolder.folderId_L_EQ").getValue()
									},
	
									success : function(formPanel, action) {
										var result = Ext.util.JSON
												.decode(action.response.responseText);
										grid.getStore().loadData(result);
									}
								});
							}

						}
					}]
		}), this.setup()]
	});

};

OutMailView.prototype.setFolderId = function(folderId) {
	
	this.folderId = folderId;
	OutMailView.folderId = folderId;
};

OutMailView.prototype.getFolderId = function() {
	return this.folderId;
};

/**
 * 建立视图
 */
OutMailView.prototype.setup = function() {
	return this.grid();
};
/**
 * 建立DataGrid
 */
OutMailView.prototype.grid = function() {
	
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
				}, 
				
				{
					header : '阅读',
					dataIndex : 'readFlag',
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
					width : 40,
					renderer : function(value, metadata, record, rowIndex,
							colIndex) {
						var str = '';
						
						// 附件标识
						if (value != ''&& value != 'null') {
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
					dataIndex : 'title',
					width : 150
				}, {
					header : '发件人',
					dataIndex : 'senderName',
					width : 80
				}, {
					header : '收件人',
					dataIndex : 'receiverNames',
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
					dataIndex : 'mailDate',
					renderer : function(value) {
						return value.substring(0, 10);
					}
				}, {
					header : '管理',
					dataIndex : 'mailId',
					width : 60,
					renderer : function(value, metadata, record, rowIndex,
							colIndex) {
						var _folderId = OutMailView.folderId;
						var editId = value;
						var mailId = record.data.mailId;
						
						var str = '<button title="删除" value=" " class="btn-del" onclick="OutMailView.remove('
								+ mailId + ',' + _folderId + ')">&nbsp</button>';
						str += '&nbsp;<button title="查看" value=" " class="btn-mail_edit" onclick="OutMailView.edit('
								+ editId
								+ ','
								+ _folderId
								+',' + rowIndex + ')">&nbsp</button>';
						return str;
					}
				}],
		defaults : {
			sortable : true,
			menuDisabled : false
		}
	});

	var store = this.store();
	store.load({
				params : {
					isFetch :null,
					start : 0,
					limit : 25
				}
			});
	
	var grid = new Ext.grid.GridPanel({
				id : 'OutMailGrid',
				//autoWidth : true,
				//border : false,
				region : 'center',
				tbar : this.topbar(),
				store : store,
				plugins: expander,
				trackMouseOver : true,
				disableSelection : false,
				loadMask : true,
				stripeRows : true,
				// autoHeight : true,
				cm : cm,
				sm : sm,
				viewConfig : {
					forceFit : true,
					autoFill : true, // 自动填充
					forceFit : true
				},
				bbar : new Ext.PagingToolbar({
					pageSize : 25,
					displayInfo : true,
					displayMsg : '当前显示从{0}至{1}， 共{2}条记录',
					emptyMsg : "当前没有记录",
					store:store
				})
			});

	grid.addListener('rowdblclick', function(grid, rowIndex, e) {
		grid.getSelectionModel().each(function(rec) {
			var _folderId = OutMailView.folderId;
			OutMailView.edit(rec.data.mailId, 
					_folderId, rowIndex);
		});
	});
	return grid;

};

/**
 * 初始化数据
 */
OutMailView.prototype.store = function() {
	var store = new Ext.data.Store({
				proxy : new Ext.data.HttpProxy({
							url : __ctxPath + '/communicate/listOutMail_.do'
						}),
				reader : new Ext.data.JsonReader({
							root : 'result',
							totalProperty : 'totalCounts',
							id : 'id',
							fields : [
									 {
										name : 'mailId',
										type : 'int'
									},  {
										name : 'readFlag',
										type : 'int'
									}, {
										name : 'replyFlag',
										type : 'int'
									},  
									'mailDate','fileIds','title',
									'content','senderName','receiverNames','senderAddresses']
						}),
				remoteSort : true
			});
	store.setDefaultSort('mailId', 'desc');
	return store;
};

/**
 * 建立操作的Toolbar
 */
OutMailView.prototype.topbar = function() {
	var toolbar = new Ext.Toolbar({
				id : 'OutMailFootBar',
				height : 30,
				bodyStyle : 'text-align:left',
				items : [{
					iconCls : 'btn-mail_remove',
					text : '删除',
					xtype : 'button',
					handler : function() {

						var grid = Ext.getCmp("OutMailGrid");

						var selectRecords = grid.getSelectionModel()
								.getSelections();

						if (selectRecords.length == 0) {
							Ext.ux.Toast.msg("信息", "请选择要删除的记录！");
							return;
						}
						var ids = Array();
						for (var i = 0; i < selectRecords.length; i++) {
							ids.push(selectRecords[i].data.mailId);
						}
						var _folderId = OutMailView.folderId;
						OutMailView.remove(ids, _folderId);
					}
				}, '-', {
					iconCls : 'btn-mail_move',
					text : '移至',
					handler : function() {
						var grid = Ext.getCmp("OutMailGrid");
						var selectRecords = grid.getSelectionModel()
								.getSelections();
						if (selectRecords.length == 0) {
							Ext.ux.Toast.msg("信息", "请选择要移动的邮件！");
							return;
						}
						var ids = Array();
						for (var i = 0; i < selectRecords.length; i++) {
							
							ids.push(selectRecords[i].data.mailId);
						}
						OutMailView.moveTo(ids);
					}
				}, '-', {
					iconCls : 'btn-mail_refresh',
					text : '刷新',
					handler : function() {
								Ext.getCmp('OutMailGrid').getStore().reload({
									params : {
										isFetch :null,
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
OutMailView.remove = function(_id, _folderId) {
	if (_folderId == null || _folderId == undefined) {
		_folderId = 1;
	}
	var grid = Ext.getCmp("OutMailGrid");
	var msg = '';
	if (_folderId == 4) {
		msg = '删除箱的记录一旦删除,将不可恢复!';
	}
	Ext.Msg.confirm('信息确认', msg + '您确认要删除该记录吗？', function(btn) {
				if (btn == 'yes') {
					Ext.Ajax.request({
								url : __ctxPath
										+ '/communicate/multiDelOutMail_.do',
								params : {
									ids : _id,
									folderId : _folderId
								},
								method : 'post',
								success : function() {
									Ext.ux.Toast.msg("信息提示", "成功删除所选记录！");
									grid.getStore().reload({
												params : {
													isFetch :null,
													start : 0,
													limit : 25
												}
											});
								}
							});
					var mailCenterView = Ext.getCmp('OutMailCenterView');
					mailCenterView.remove('ShowOutMailDetail');
					var outMailView = Ext.getCmp('OutMailView');
					outMailView.show();
					mailCenterView.doLayout();
				}
			});
};

/**
 * 显示邮件,或载入草稿
 */
OutMailView.edit = function(mailId,  folderId, rowIndex) {
	var detailToolbar = new OutMailView.centerViewToolbar(mailId, folderId,
			rowIndex);
	
	if (folderId == 3) {
		var tab = Ext.getCmp('centerTabPanel');
		var outMailForm = Ext.getCmp('OutMailForm');
		if (outMailForm == null) {
			outMailForm = new OutMailForm(mailId,  'draft');
			tab.add(outMailForm);
			tab.activate(outMailForm);
		} else {
			tab.remove(outMailForm);
			outMailForm = new OutMailForm(mailId,  'draft');
			tab.add(outMailForm);
			tab.activate(outMailForm);
		}
	} else {
		var mailCenterView = Ext.getCmp('OutMailCenterView');
		var outMailView = Ext.getCmp('OutMailView');
		outMailView.hide();
		var showDetail = new Ext.Panel({
					id : 'ShowOutMailDetail',
					title : '[邮件信息]',
					border : false,
					tbar : detailToolbar,
					//width:mailCenterView.getInnerWidth(),
					autoLoad : {
						url : __ctxPath + '/communicate/getOutMail_.do?',
						params : {
							mailId : mailId	
						},
						method : 'Post'
					}
				});
		mailCenterView.add(showDetail);
		mailCenterView.doLayout();
	}
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
OutMailView.centerViewToolbar = function(mailId,  _folderId, rowIndex) {
	var toolbar = new Ext.Toolbar({
		height : 30,
		defaultType : 'button',
		bodyStyle : 'text-align:left',
		items : [{
					iconCls : 'btn-mail_back',
					text : '返回',
					handler : function() {
						var mailCenterView = Ext.getCmp('OutMailCenterView');
						mailCenterView.remove('ShowOutMailDetail');
						var outMailView = Ext.getCmp('OutMailView');
						outMailView.show();
						mailCenterView.doLayout();
					}
				}, {
					iconCls : 'btn-mail_reply',
					text : '回复',
					handler : function() {
						var tab = Ext.getCmp('centerTabPanel');
						var outMailForm = Ext.getCmp('OutMailForm');
						if (outMailForm == null) {
							outMailForm = new OutMailForm(mailId,  'reply');
							tab.add(outMailForm);
							tab.activate(outMailForm);
						} else {
							tab.remove(outMailForm);
							outMailForm = new OutMailForm(mailId,  'reply');
							tab.add(outMailForm);
							tab.activate(outMailForm);
						}
					}
				}, {
					iconCls : 'btn-mail_resend',
					text : '转发',
					handler : function() {
						var tab = Ext.getCmp('centerTabPanel');
						var outMailForm = Ext.getCmp('OutMailForm');
						if (outMailForm == null) {
							outMailForm = new OutMailForm(mailId,  'forward');
							tab.add(outMailForm);
							tab.activate(outMailForm);
						} else {
							tab.remove(outMailForm);
							outMailForm = new OutMailForm(mailId,  'forward');
							tab.add(outMailForm);
							tab.activate(outMailForm);
						}
					}
				}, {
					iconCls : 'btn-mail_remove',
					text : '删除',
					handler : function() {
						OutMailView.remove(mailId, _folderId);
					}
				}, {
					iconCls : 'btn-mail_move',
					text : '移至',
					handler : function() {
						OutMailView.moveTo(mailId);
					}
				},'->', {
					iconCls : 'btn-up',
					text : '较旧一封',
					handler : function() {
						var haveNextMailFlag = document
								.getElementById('__haveNextOutMailFlag');
						if (haveNextMailFlag != null
								&& haveNextMailFlag.value != 'endPre') {
							var userId = curUserInfo.userId;
							var __curOutMailId = document.getElementById('__curOutMailId').value;
							Ext.getCmp('ShowOutMailDetail').load({
										url : __ctxPath
												+ '/communicate/getOutMail_.do?',
										params : {
											mailId : __curOutMailId,
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
								.getElementById('__haveNextOutMailFlag');
						if (haveNextMailFlag != null
								&& haveNextMailFlag.value != 'endNext') {
							var userId = curUserInfo.userId;
							var __curOutMailId = document.getElementById('__curOutMailId').value;
							Ext.getCmp('ShowOutMailDetail').load({
										url : __ctxPath
												+ '/communicate/getOutMail_.do?',
										params : {
											mailId : __curOutMailId,
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

OutMailView.moveTo = function(id) {
	var _moveFormPanel = new OutMailView.moveFormPanel(id);
	var selectFolder = new Ext.Window({
				x:350,
				y:100,
				width : 340,
				height : 300,
				autoHeight : true,
				title : '移动邮件',
				iconCls : 'btn-mail_move',
				modal : true,
				buttonAlign : 'center',
				plain : true,
				bodyStyle : 'padding:5px;',
				items : [_moveFormPanel],
				buttons : [{
					text : '确定移动',
					handler : function() {
						var folderId = Ext.getCmp('folderId');
						if (folderId == '' || folderId == null
								|| folderId == undefined) {
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
											.getCmp('OutMailCenterView');
									mailCenterView.remove('ShowOutMailDetail');
									var outMailView = Ext.getCmp('OutMailView');
									Ext.getCmp('OutMailGrid').getStore().reload({
												params : {
													isFetch :null,
													start : 0,
													limit : 25
												}
											});
									outMailView.show();
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
					handler : function() {
						selectFolder.close();
					}
				}]
			});
	selectFolder.show();

};
OutMailView.moveFormPanel = function(_id) {
	var treePanel = new Ext.tree.TreePanel({
				// id:'',
				title : '请选择文件夹',
				loader : new Ext.tree.TreeLoader({
							url : __ctxPath + '/communicate/listOutMailFolder_.do'
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
				url : __ctxPath + '/communicate/moveOutMail_.do',
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
							id : 'outMailIds',
							name : 'outMailIds',
							xtype : 'hidden',
							value : _id
						}, {
							xtype : 'panel',
							items : [treePanel]
						}]
			});
	return formPanel;
};
