Ext.ns('SearchMail');
/**
 * 新闻列表
 */
var SearchMail = function(_searchContent) {
	return this.getView(_searchContent);
}
/**
 * 显示列表
 * 
 * @return {}
 */
SearchMail.prototype.getView = function(_searchContent) {
	return new Ext.Panel({
		id : 'SearchMail',
		title : '搜索邮件',
		iconCls:'menu-mail_box',
		border:false,
		style:'padding-bottom:20px;',
		autoScroll : true,
		items : [{
					region : 'center',
					anchor :'100%',
					items : [new Ext.FormPanel({
							id : 'ALLMailSearchForm',
							height : 40,
			frame : false,
			border : false,
			layout : 'hbox',
			layoutConfig : {
				padding : '5',
				align : 'middle'
			},
			defaults : {
				xtype : 'label',
				margins : {
					top : 0,
					right : 4,
					bottom : 4,
					left : 4
				}
			},
							items : [{
										text : '请输入条件:'
									}, {
										xtype : 'textfield',
										name : 'searchContent',
										width : 150
									},{
										xtype : 'button',
										text : '查询',
										iconCls : 'search',
										handler : function() {
											var searchPanel = Ext.getCmp('ALLMailSearchForm');
											var gridPanel = Ext.getCmp('SearchMailGrid');
											if (searchPanel.getForm().isValid()) {
												$search({
													searchPanel :searchPanel,
													gridPanel : gridPanel
												});
											}

										}
									}, {
										xtype : 'button',
										text : '重置',
										iconCls : 'reset',
										handler : function() {
											var searchPanel = Ext
													.getCmp('ALLMailSearchForm');
											searchPanel.getForm().reset();
										}
									}]
						}),this.setup(_searchContent)]
				}]
	});
};
/**
 * 建立视图
 */
SearchMail.prototype.setup = function(_searchContent) {
	return this.grid(_searchContent);
};
/**
 * 建立DataGrid
 */
SearchMail.prototype.grid = function(_searchContent) {
	var expander = new Ext.ux.grid.RowExpander({
		tpl : new Ext.Template(
		    '<p style="padding:5px 5px 5px 62px;"><b>内容摘要:</b> {content}</p>'
		)
		
    });
	var cm = new Ext.grid.ColumnModel({
		columns : [new Ext.grid.RowNumberer(),expander, {
					header : 'mailId',
					dataIndex : 'mailId',
					hidden : true
				}, {
					header : '优先级',
					dataIndex : 'importantFlag',
					width : 55,
					renderer : function(value, metadata, record, rowIndex,
							colIndex) {
						var str = '';
						// 优先级标识
						if (value == 2) {
							str += '<img title="重要" src="' + __ctxPath
									+ '/images/flag/mail/important.png"/>'
						} else if (value == 3) {
							str += '<img title="非常重要" src="' + __ctxPath
									+ '/images/flag/mail/veryImportant.png"/>'
						} else {
							str += '<img title="一般" src="' + __ctxPath
									+ '/images/flag/mail/common.png"/>'
						}
						return str;// 考虑所有图标都在这里显示
					}
				}, {
					header : '阅读',
					dataIndex : 'mailStatus',
					width : 40,
					renderer : function(value, metadata, record, rowIndex,
							colIndex) {
						var str = '';
						// 阅读标识
						if (value != 0) {
							if (record.data.readFlag == 0) {
								str += '<img title="未读" src="' + __ctxPath
										+ '/images/flag/mail/mail.png"/>'
							} else {
								str += '<img title="已读" src="' + __ctxPath
										+ '/images/flag/mail/mail_open.png"/>'
							}
						} else {// 草稿
							str += '<img title="草稿" src="' + __ctxPath
									+ '/images/flag/mail/mail_draft.png"/>'
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
						if (value != '') {
							str += '<img title="附件" src="' + __ctxPath
									+ '/images/flag/attachment.png"/>'
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
									+ '/images/flag/mail/replyed.png" style="background: white;"/>'
						}
						return str;
					}
				}, {
					header : '邮件主题',
					dataIndex : 'subject',
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
						return value.substring(0, 10);
					}
				}],
		defaults : {
			sortable : true,
			menuDisabled : false,
			width : 100
		}
	});

	var store = this.store();
	store.load({
				params : {
					start : 0,
					limit : 25,
					searchContent:_searchContent
				}
			});
	
	var grid = new Ext.grid.GridPanel({
				id : 'SearchMailGrid',
				autoWidth : true,
				border : false,
				autoHeight : true,
				store : store,
				plugins: expander,
				trackMouseOver : true,
				disableSelection : false,
				loadMask : true,
				cm : cm,
				viewConfig : {
					forceFit : true,
					enableRowBody : false,
					showPreview : false
				},
				bbar : new HT.PagingBar({store : store,pageSize : 12})
			});

	grid.addListener('rowdblclick', function(grid, rowIndex, e) {
		grid.getSelectionModel().each(function(rec) {
			SearchMail.detail(rec.data.mailId, rec.data.boxId,rec.data.mailStatus, rowIndex);
		});
	});
	return grid;

};

/**
 * 初始化数据
 */
SearchMail.prototype.store = function() {
	var store = new Ext.data.Store({
				proxy : new Ext.data.HttpProxy({
							url : __ctxPath + '/communicate/searchMail.do'
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


SearchMail.detail = function(mailId, boxId, status, rowIndex) {
	var detailToolbar = new SearchMail.centerViewToolbar(mailId,boxId,rowIndex);
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
		var searchMail = Ext.getCmp('SearchMail');
		var grid = Ext.getCmp('SearchMailGrid');
		var searchForm = Ext.getCmp('ALLMailSearchForm');
		grid.hide();
		searchForm.hide();
		var showDetail = new Ext.Panel({
					id : 'HomeShowMailDetail',
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
				})
		searchMail.add(showDetail);
		searchMail.doLayout();
	}// else
}

/**
 * 显示邮件信息时功能栏
 * 
 * @param {}
 *            mailId
 * @param {}
 *            boxId
 * @return {}
 */
SearchMail.centerViewToolbar = function(mailId, boxId, rowIndex) {
	var toolbar = new Ext.Toolbar({
		height : 30,
		defaultType : 'button',
		bodyStyle : 'text-align:left',
		items : [{
					iconCls : 'btn-mail_back',
					text : '返回',
					handler : function() {
						var searchMail = Ext.getCmp('SearchMail');
						searchMail.remove('HomeShowMailDetail');
						var grid = Ext.getCmp('SearchMailGrid');
						var searchForm = Ext.getCmp('ALLMailSearchForm');
						grid.show();
						searchForm.show();
						searchMail.doLayout();
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
				}]
	})
	return toolbar;
}
