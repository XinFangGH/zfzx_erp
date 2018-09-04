/**
 * 公告详情
 */
var NoticeDetail = function(id) {
	return new Ext.Panel({
				title : '公告详情',
				iconCls : 'menu-notice',
				id : 'NoticeDetail',
				autoScroll : true,
				autoWidth : true,
				tbar : new Ext.Toolbar({
							height : 30,
							bodyStyle : 'text-align:left',
							defaultType:'button',
							items : [{
									text : '关闭',
									iconCls:'close',
									handler : function() {
										var centerTabPanel = Ext
												.getCmp('centerTabPanel');
										var oldDetailPanel = centerTabPanel
												.getItem('NoticeDetail');
										centerTabPanel.remove(oldDetailPanel);
									}
								},{
									text : '上一条',
									iconCls:'btn-up',
									handler : function(){
										var haveNextNewsFlag = document.getElementById('__haveNextNoticeFlag');
										if(haveNextNewsFlag !=null && haveNextNewsFlag.value !='endPre'){
											var noticeId = document.getElementById('__curNoticeId').value;
											Ext.getCmp('HomeNoticeDetailPanel').load({
												url : __ctxPath+ '/pages/info/noticedetail.jsp?opt=_pre&noticeId='+ noticeId
											});
										}else{
											Ext.ux.Toast.msg('提示信息','这里已经是第一条了.');
										}
									}
								},{
									text : '下一条',
									iconCls:'btn-last',
									handler : function(){
										var haveNextNewsFlag = document.getElementById('__haveNextNoticeFlag');
										if(haveNextNewsFlag !=null && haveNextNewsFlag.value !='endNext'){
											var noticeId = document.getElementById('__curNoticeId').value;
											Ext.getCmp('HomeNoticeDetailPanel').load({
												url : __ctxPath+ '/pages/info/noticedetail.jsp?opt=_next&noticeId='+ noticeId
											});
										}else{
											Ext.ux.Toast.msg('提示信息','这里已经是最后一条了.');
										}
									}
								}]
						}),
				items : [new Ext.Panel({
							width : 650,
							id : 'HomeNoticeDetailPanel',
							autoScroll : true,
							style : 'padding-left:10%;padding-top:10px;',
							autoHeight : true,
							border:false,
							autoLoad : {
								url : __ctxPath
										+ '/pages/info/noticedetail.jsp?noticeId='
										+ id
							}
						})]
			});
	
}