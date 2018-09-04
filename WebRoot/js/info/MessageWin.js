/**
 * @author lyy 查看消息窗口
 * @class MessageWin
 * @extends Ext.Window
 */
MessageWin = Ext.extend(Ext.Window, {
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		MessageWin.superclass.constructor.call(this, {
					id : 'win',
					title : '',
					iconCls : 'btn-replyM',
					region : 'west',
					width : 300,
					height : 200,
					x : 0,
					y : 398,
					layout : 'fit',
					border : false,
					bodyStyle : 'padding:5px;',
					buttonAlign : 'center',
					items : [],
					buttons : []
				});

		this.addUI();
	},
	addUI : function() {
		Ext.Ajax.request({
			url : __ctxPath + '/info/readInMessage.do',
			method : 'Get',
			success : function(response, options) {
				var object = Ext.util.JSON.decode(response.responseText);
				var message = object.message;
				var win = Ext.getCmp('win');
				if (message != null) {
					win.setTitle(message.sender + '--发送的消息');
					win.add({
								id : 'pp',
								xtype : 'panel',
								height : 150,
								width : 160,
								html : '<p>  ' + message.sender + '  '
										+ message.sendTime
										+ '</p><p style="color:blue;">'
										+ message.content + '</p>'

							});
					if(message.haveNext){
						win.addButton({
						   text : '下一条',
									iconCls : 'btn-down',
									id : 'nextMessage',
									handler : function() {
										var win = Ext.getCmp('win');
										if (win != null) {
											win.close();
										}
										new MessageWin().show();
									}
						
						});
					}
					if(message.msgType==1){
						win.addButton({
									text : '回复',
									iconCls : 'btn-replyM',
									id : 'replyMessage',
									handler : function() {
										var win = Ext.getCmp('win');
										win.close();
										new ReMessageWin({id:message.senderId,
												name:message.sender}).show();
									}});
					}
					win.addButton({
								text : '删除',
								iconCls : 'btn-del',
								handler : function() {
									Ext.Ajax.request({
										url : __ctxPath
												+ '/info/multiRemoveInMessage.do',
										method : 'POST',
										params : {
											ids : message.receiveId
										},
										success : function(response, options) {
											var win = Ext.getCmp('win');
											win.close();
											Ext.ux.Toast.msg('操作信息', '信息删除成功！');

										},
										failure : function(response, options) {
											Ext.ux.Toast.msg('操作信息', '信息删除失败！');
										},
										scope : this
									});
								}
							});
					win.doLayout();
				    var messagePanel=Ext.getCmp('MessagePanelView');
				    if(messagePanel!=null){
					  messagePanel.getUpdater().update(__ctxPath+ '/info/displayInMessage.do');
				    }
				} else {
					if (win != null) {
						win.close();
					}

				}
			}
		});

	}

});
