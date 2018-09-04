/**
 * @author lyy
 * @class MessageRecView
 * @extends Ext.Panel
 */
Ext.ns('MessageRecView');
MessageRecView = Ext.extend(Ext.Panel, {
	gridPanel : null,
	searchPanel : null,
	receiveStore : null,
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		this.initUIComponents();
		MessageRecView.superclass.constructor.call(this, {
					id : 'MessageRecView',
//					autoHeight : true,
					flex:1,
					layout : 'border',
					items : [this.searchPanel, this.gridPanel]
				});
		
	},// end of constructor
	initUIComponents : function() {
		this.searchPanel = new Ext.FormPanel({
			        region:'north',
					height : 60,
					title : '已收信息显示',
					id : 'receiveSearchForm',
					frame : false,
					border : false,
					url : __ctxPath + '/info/listShortMessage.do',
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
					keys : {
						key : Ext.EventObject.ESC,
						fn : this.reset.createCallback(this),
						scope : this
					},
					items : [{
								text : '类型',
								width:30
							}, {
								hiddenName : 'shortMessage.msgType',
								xtype : 'combo',
								mode : 'local',
								width : 100,
								editable : false,
								triggerAction : 'all',
								store : [['','全部'], ['1', '个人信息'], ['2', '日程安排'],
									['3', '计划任务'], ['4', '待办任务提醒'], 
									['5', '系统消息']],
								value : ''
							}, {
								text : '发送人',width:40
							}, {
								xtype : 'textfield',
								name : 'shortMessage.sender'
							}, {
								text : '从',width:20
							}, {
								xtype : 'datefield',
								format : 'Y-m-d',
								name : 'from',
								editable : false
							}, {
								text : '到',width:20
							}, {
								xtype : 'datefield',
								format : 'Y-m-d',
								name : 'to',
								editable : false
							},{
								text : '查询',
								xtype : 'button',
								iconCls : 'search',
								handler :this.search.createCallback(this)
							}, {
								xtype : 'button',
								text : '重置',
								iconCls : 'reset',
								handler :this.reset.createCallback(this)
							},{
								xtype : 'hidden',
								name : 'start',
								value : 0
							}, {
								xtype : 'hidden',
								name : 'limit',
								value : 11
							}
							]
				});

		this.receiveStore = new Ext.data.Store({
					proxy : new Ext.data.HttpProxy({
								url : __ctxPath + '/info/listShortMessage.do'
							}),
					reader : new Ext.data.JsonReader({
								root : 'result',
								totalProperty : 'totalCounts',
								id : 'id',
								fields : [{
											name : 'receiveId',
											type : 'int'
										}, {
											name : 'messageId',
											mapping : 'shortMessage.messageId',
											type : 'int'
										}, {
											name : 'msgType',
											mapping : 'shortMessage.msgType',
											type : 'int'
										}, {
											name : 'senderId',
											mapping : 'shortMessage.senderId',
											type : 'int'
										}, {
											name : 'sender',
											mapping : 'shortMessage.sender'
										}, {
											name : 'content',
											mapping : 'shortMessage.content'
										}, {
											name : 'sendTime',
											mapping : 'shortMessage.sendTime'
										}, {
											name : 'readFlag'
										}]
							}),
					remoteSort : true
				});
		this.receiveStore.setDefaultSort('id', 'desc');

		this.receiveStore.load({
					params : {
						start : 0,
						limit : 11
					}
				});

		var sm = new Ext.grid.CheckboxSelectionModel();

		var cm = new Ext.grid.ColumnModel({
			columns : [sm, new Ext.grid.RowNumberer(), {
				header : "状态",
				dataIndex : 'readFlag',
				width : 60,
				renderer : function(value) {
					return value == '1' ? "<img src='" + __ctxPath + "/images/btn/info/email_open.png'/>" : "<img src='" + __ctxPath + "/images/btn/info/email.png'/>";
				}
			}, {
				header : "类别",
				dataIndex : 'msgType',
				width : 60,
				renderer : function(value) {
					if (value == '1') {
						return "<p style='color:green;'>个人信息</p>";
					} else if (value == '2') {
						return "<p style='color:green;'>日程安排</p>";
					} else if (value == '3') {
						return "<p style='color:green;'>计划任务</p>";
					} else if (value == '4') {
						return "<p style='color:green;'>待办任务提醒</p>";
					} else if (value == '5') {
						return "<p style='color:green;'>系统消息</p>";
					} else {
						return "<p style='color:green;'>其他</p>";
					}
				}
			}, {
				header : "发送人",
				dataIndex : 'sender',
				width : 40
			}, {
				header : "内容",
				dataIndex : 'content',
				width : 300
			}, {
				header : "发送时间",
				dataIndex : 'sendTime',
				width : 90
			}, {
				header : '操作',
				dataIndex : 'receiveId',
				width : 120,
				renderer : function(value, metadata, record, rowIndex, colIndex) {
					var replyId = record.data.receiveId;
					var msgType = record.data.msgType;
					var str = '<button title="删除" value=" " class="btn-del" onclick="MessageRecView.removeReceiveMessage('
							+ replyId + ')">&nbsp;</button>';
					if (msgType == '1') { // 1为个人信息
						str += '&nbsp;<button title="回复" value=" " class="btn-update" onclick="MessageRecView. reply('
								+ replyId + ')">&nbsp;</button>';
					}
					return str;
				}
			}],
			defaults : {
				sortable : true,
				menuDisabled : true,
				width : 100
			},
			listeners : {
				hiddenchange : function(cm, colIndex, hidden) {
					saveConfig(colIndex, hidden);
				}
			}
		});

		this.gridPanel = new Ext.grid.GridPanel({
					id : 'ReceiveMessage',
//					height : 338,
					border:false,
					region:'center',
					tbar:new Ext.Toolbar({
					   items:[ {
								xtype:'button',
								text:'标记为已读',
								iconCls:'ux-flag-blue',
								handler:this.setReadFlag.createCallback(this)
							}, '-', {
								iconCls : 'btn-del',
								text : '删除信息',
								xtype : 'button',
								handler : this.mutDel
							}]
					}),
					store : this.receiveStore,
					shim : true,
					trackMouseOver : true,
					disableSelection : false,
					autoScroll : true,
					loadMask : true,
					// draggable : true,
					cm : cm,
					sm : sm,
					viewConfig : {
						forceFit : true,
						enableRowBody : false,
						showPreview : false
					},
					bbar : new HT.PagingBar({pageSize : 11,
								store : this.receiveStore })
				});
	},
	search:function(self){
	    var receiveGrid =self.gridPanel;
		var searchPanelR =self.searchPanel;
		searchPanelR.getForm().submit({
			waitMsg : '正在提交查询信息',
			success : function(searchPanelR, action) {
				var receiveStore = receiveGrid
						.getStore();
				var rsText = action.response.responseText
						.replace('success:true,',
								"");
				var result = Ext.util.JSON
						.decode(rsText);
				receiveStore
						.loadData(result, false);
			},
			failure : function(searchPanelR, action) {
			}

		});
	},
	reset:function(self){
	   self.searchPanel.getForm().reset();
	},
	setReadFlag:function(self){
		var grid = Ext.getCmp("ReceiveMessage");
		var selectRecords = grid.getSelectionModel().getSelections();

		if (selectRecords.length == 0) {
			Ext.ux.Toast.msg("信息", "请选择信息！");
			return;
		}
		var ids = Array();
		for (var i = 0; i < selectRecords.length; i++) {
			ids.push(selectRecords[i].data.receiveId);
		}
		Ext.Ajax.request({
					url : __ctxPath
							+ '/info/multiReadInMessage.do',
					params : {
						ids : ids
					},
					method : 'post',
					success : function() {
						Ext.ux.Toast.msg('操作信息', '成功标记所选信息为已读！');
						grid.getStore().reload();
					}
	  });
	},//标记为已读
	mutDel:function(){
	    var grid = Ext.getCmp("ReceiveMessage");
		var selectRecords = grid
				.getSelectionModel()
				.getSelections();

		if (selectRecords.length == 0) {
			Ext.ux.Toast.msg("信息", "请选择要删除的记录！");
			return;
		}
		var ids = Array();
		for (var i = 0; i < selectRecords.length; i++) {
			ids.push(selectRecords[i].data.receiveId);
		}
		MessageRecView.removeReceiveMessage(ids);
	}
});
MessageRecView.removeReceiveMessage=function(receiveId){
   var receive = Ext.getCmp('ReceiveMessage');
	Ext.Msg.confirm('删除操作', '你确定要删除该信息吗?', function(btn) {
				if (btn == 'yes') {
					Ext.Ajax.request({
								url : __ctxPath
										+ '/info/multiRemoveInMessage.do',
								params : {
									ids : receiveId
								},
								method : 'post',
								success : function() {
									Ext.ux.Toast.msg('操作信息', '删除信息成功！');
									receive.getStore().reload();
								}
							});
				}
			});
};

MessageRecView.reply = function(replyId) {
	var reView1 = Ext.getCmp('MessageManageView');
	reView1.removeAll(true);
	var message = new MessageForm();
	reView1.add(message);
	var reply = Ext.getCmp('mFormPanel');
	reView1.doLayout();
	reply.form.load({
				url : __ctxPath + '/info/replyInMessage.do',
				params : {
					receiveId : replyId
				},
				method : 'post',
				deferredRender : true,
				layoutOnTabChange : true,
				success : function() {
					Ext.Ajax.request({
								url : __ctxPath + '/info/knowInMessage.do',
								method : 'POST',
								params : {
									receiveId : replyId
								},
								success : function(response, options) {
								},
								failure : function(response, options) {
								},
								scope : this
							});
				},
				failure : function() {
				}
			});
};

/**
 * @author lyy
 * @class MessageSendView
 * @extends Ext.Panel
 */

MessageSendView = Ext.extend(Ext.Panel, {
	searchPanel : null,
	gridPanel : null,
	store : null,
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		// 初始化组件
		this.initUIComponents();
		MessageSendView.superclass.constructor.call(this, {
					id : 'MessageSendView',
//					autoHeight : true,
					flex:1,
					layout : 'border',
					items : [this.searchPanel, this.gridPanel]
				});
	},// end of constructor
	initUIComponents : function() {
		this.searchPanel = new Ext.FormPanel({
			        region:'north',
					height : 60,
					title : '已发信息显示',
					id : 'sendSearchForm',
					url : __ctxPath + '/info/listInMessage.do',
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
								text : '收信人',width:60
							}, {
								xtype : 'textfield',
								name : 'inMessage.userFullname'
							}, {
								text : '从',width:20
							}, {
								xtype : 'datefield',
								format : 'Y-m-d',
								name : 'from',
								editable : false
							}, {
								text : '到',width:20
							}, {
								xtype : 'datefield',
								format : 'Y-m-d',
								name : 'to',
								editable : false
							}, {
								text : '查询',
								xtype : 'button',
								iconCls : 'search',
								handler : this.search.createCallback(this)
							}, {
								text : '重置',
								xtype : 'button',
								iconCls : 'reset',
								handler :this.reset.createCallback(this)
							}, {
								xtype : 'hidden',
								name : 'start',
								value : 0
							}, {
								xtype : 'hidden',
								name : 'limit',
								value : 11
							}]
				});

		this.store = new Ext.data.Store({
					proxy : new Ext.data.HttpProxy({
								url : __ctxPath + '/info/listInMessage.do'
							}),
					reader : new Ext.data.JsonReader({
								root : 'result',
								totalProperty : 'totalCounts',
								id : 'id',
								fields : [{
											name : 'receiveId',
											type : 'int'
										}, {
											name : 'messageId',
											mapping : 'shortMessage.messageId',
											type : 'int'
										}, {
											name : 'msgType',
											mapping : 'shortMessage.msgType',
											type : 'int'
										}, {
											name : 'content',
											mapping : 'shortMessage.content'
										}, {
											name : 'userId',
											type : 'int'
										}, 'userFullname', {
											name : 'sendTime',
											mapping : 'shortMessage.sendTime'
										}]
							}),
					remoteSort : true
				});
		this.store.setDefaultSort('id', 'desc');
		this.store.load({
					params : {
						start : 0,
						limit : 11
					}
				});

		this.rowActions = new Ext.ux.grid.RowActions({
					header : '管理',
					width : 120,
					actions : [
						{
								iconCls : 'btn-update',
								qtip : '重发',
								style : 'margin:0 3px 0 3px'
							}]
				});		
		var sm = new Ext.grid.CheckboxSelectionModel();
		var cms = new Ext.grid.ColumnModel({
			columns : [sm, new Ext.grid.RowNumberer(), {
						header : "收信人",
						dataIndex : 'userFullname',
						width : 100
					}, {
						header : "内容",
						dataIndex : 'content',
						width : 250

					}, {
						header : "发送时间",
						dataIndex : 'sendTime',
						width : 90
					},this.rowActions],
			defaults : {
				sortable : true,
				menuDisabled : true,
				width : 100
			},
			listeners : {
				hiddenchange : function(cm, colIndex, hidden) {
					saveConfig(colIndex, hidden);
				}
			}
		});
		
		
		this.gridPanel = new Ext.grid.GridPanel({
					id : 'sendMessage',
					region:'center',
					height : 330,
					border:false,
					tbar:new Ext.Toolbar({
					    height:27
					}),
					store : this.store,
					shim : true,
					trackMouseOver : true,
					disableSelection : false,
					autoScroll : true,
					loadMask : true,
					cm : cms,
					sm : sm,
					plugins : this.rowActions,
					viewConfig : {
						forceFit : true,
						enableRowBody : false,
						showPreview : false
					},
					bbar : new HT.PagingBar({store : this.store,pageSize:11})
					
				});
		this.rowActions.on('action', this.onRowAction, this);

	},
	search:function(self){
	    var sendGrid =self.gridPanel;
		var searchPanelS =self.searchPanel;
		searchPanelS.getForm().submit({
			waitMsg : '正在提交查询信息',
			success : function(searchPanelS, action) {

				var sendStore = sendGrid.getStore();
				var rsText = action.response.responseText
						.replace('success:true,',
								"");
				var result = Ext.util.JSON
						.decode(rsText);
				sendStore.loadData(result, false);
			}
		});
		
	},
	reset:function(self){
	   self.searchPanel.getForm().reset();
	},
	reSend:function(record){
		var grid = Ext.getCmp('sendMessage');
		Ext.Ajax.request({
					url : __ctxPath + '/info/sendShortMessage.do',
					params : {
						userId : record.data.userId + ',',
						content : record.data.content
					},
					method : 'post',
					success : function() {
						Ext.ux.Toast.msg('操作信息', '重发成功！');
						grid.getStore().reload();
					}
				});
	},
	onRowAction : function(gridPanel, record, action, row, col) {
		switch (action) {
			case 'btn-update' :
				this.reSend(record);
				break;
			default :
				break;
	   }
	}
});
/**
 * @author lyy
 * @class MessageMange
 * @extends Ext.Panel
 */
MessageManageView = Ext.extend(Ext.Panel, {
			constructor : function(_cfg) {
				Ext.applyIf(this, _cfg);
				// 初始化组件
				this.initUIComponents();
				// 调用父类构造
				MessageManageView.superclass.constructor.call(this, {
							id : 'MessageManageView',
							region : 'center',
							iconCls : "menu-message",
							title : '我的消息',
							layout : 'vbox',
							layoutConfig:{
			            	    align:'stretch'
			            	},
							tbar : this.toolbar,
//							autoHeight : true,
							items : []
						});
				this.addRecPanel(this);
			},// end of constructor

			// 初始化组件
			initUIComponents : function() {
				this.toolbar = new Ext.Toolbar({
							height : 30,
							layout : 'column',
							items : [new Ext.Button({
										text : '发送信息',
										iconCls : 'btn-sendM',
										handler : this.addSendFormPanel
												.createCallback(this)
									}), {
								xtype : 'button',
								text : '已发信息',
								iconCls : 'btn-sendMessage',
								handler : this.addSendPanel
										.createCallback(this)
							}, {
								text : '已收信息',
								iconCls : 'btn-receiveMessage',
								handler : this.addRecPanel.createCallback(this)
							}]
						});
			},// end of initUIComponents
			addRecPanel : function(panel) {
				panel.removeAll(true);
				var recPanel = new MessageRecView();
				panel.add(recPanel);
				panel.doLayout();
			},
			addSendPanel : function(panel) {
				panel.removeAll(true);
				var sendPanel = new MessageSendView();
				panel.add(sendPanel);
				panel.doLayout();
			},
			addSendFormPanel : function(panel) {
				panel.removeAll(true);
				var sendForm = new MessageForm();
				panel.add(sendForm);
				panel.doLayout();
			}

		});