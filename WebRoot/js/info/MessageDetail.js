/**
 * 个人短信详情
 */
var MessageDetail = function(id) {
	var win = new Ext.Window({
		title : '个人短信详情',
		iconCls : 'menu-mail_box',
		height : 235,
		id : 'HomeMessageWindow',
		// autoWidth :true,
		width : 350,
		modal:true,
		layout : 'fit',
		buttonAlign : 'center',
		items : [new Ext.Panel({
					id : 'HomeMessageDetailPanel',
					border : false,
					autoLoad : {
						url : __ctxPath
								+ '/pages/info/messagedetail.jsp?receiveId='
								+ id,
						scripts:true
					}
				})],
		buttons : [{
			text : '上一条',
			iconCls : 'btn-previous-message',
			handler : function() {
				var haveNextMessageFlag = document
						.getElementById('__haveNextMessageFlag');
				if (haveNextMessageFlag != null
						&& haveNextMessageFlag.value != 'endPre') {
					var userId = curUserInfo.userId;
					var receiveId = document.getElementById('__curReceiveId').value;
					Ext.getCmp('HomeMessageDetailPanel').load({
						url : __ctxPath
								+ '/pages/info/messagedetail.jsp?opt=_pre&receiveId='
								+ receiveId + '&userId=' + userId,
						scripts:true
					});
				} else {
					Ext.ux.Toast.msg('提示信息', '这里已经是第一条了');
				}
			}
		}, {
			text : '下一条',
			iconCls : 'btn-next-message',
			handler : function() {
				var haveNextMessageFlag = document
						.getElementById('__haveNextMessageFlag');
				if (haveNextMessageFlag != null
						&& haveNextMessageFlag.value != 'endNext') {
					var userId = curUserInfo.userId;
					var receiveId = document.getElementById('__curReceiveId').value;
					Ext.getCmp('HomeMessageDetailPanel').load({
						url : __ctxPath
								+ '/pages/info/messagedetail.jsp?opt=_next&receiveId='
								+ receiveId + '&userId=' + userId,
						scripts:true
					});
				} else {
					Ext.ux.Toast.msg('提示信息', '这里已经是最后一条了.');
				}
			}
		}, {
			text : '回复',
			iconCls : 'btn-mail_reply',
			handler : function() {
				var type = document.getElementById('__ReplyFlag').value;
				var senderId=document.getElementById('__SENDID').value;
				var senderName=document.getElementById('__SENDERNAME').value;
				if (type == 1) {
					var replyWin = Ext.getCmp('ReplyWindow');
					if (replyWin != null) {
						replyWin.close();
						new ReMessageWin({id:senderId,name:senderName}).show();
					} else {
						new ReMessageWin({id:senderId,name:senderName}).show();
					}
					win.close();
				} else {
                    Ext.ux.Toast.msg('提示信息', '系统信息不能回复.');
				}
				
			}
		}, {
			text : '关闭',
			iconCls : 'close',
			handler : function() {
				win.close();
			}
		}]
	});
	
	win.show();
}