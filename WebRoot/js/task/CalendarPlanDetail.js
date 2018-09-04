

/**
 * 日程详情
 */
var CalendarPlanDetail = function(id) {
	return new Ext.Panel({
				title : '日程详情',
				iconCls : 'menu-cal-plan-view',
				id : 'CalendarPlanDetail',
				autoScroll : true,
				//autoHeight : true,
				autoWidth : true,
				tbar : new Ext.Toolbar({
							//id : '',
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
												.getItem('CalendarPlanDetail');
										centerTabPanel.remove(oldDetailPanel);
									}
								},{
									text : '上一条',
									iconCls:'btn-up',
									handler : function(){
										var haveNextPlanFlag = document.getElementById('__haveNextPlanFlag');
										if(haveNextPlanFlag !=null && haveNextPlanFlag.value !='endPre'){
											var userId = curUserInfo.userId;
											var planId = document.getElementById('__curPlanId').value;
											Ext.getCmp('HomePlanDetailPanel').load({
												url : __ctxPath+ '/pages/task/calendarplandetail.jsp?opt=_pre&planId='
												+ planId +'&userId='+userId
											});
										}else{
											Ext.ux.Toast.msg('提示信息','这里已经是第一条了.');
										}
									}
								},{
									text : '下一条',
									iconCls:'btn-last',
									handler : function(){
										var haveNextPlanFlag = document.getElementById('__haveNextPlanFlag');
										if(haveNextPlanFlag !=null && haveNextPlanFlag.value !='endNext'){
											var userId = curUserInfo.userId;
											var planId = document.getElementById('__curPlanId').value;
											Ext.getCmp('HomePlanDetailPanel').load({
												url : __ctxPath+ '/pages/task/calendarplandetail.jsp?opt=_next&planId='
												+ planId +'&userId='+userId
											});
										}else{
											Ext.ux.Toast.msg('提示信息','这里已经是最后一条了.');
										}
									}
								}]
						}),
				items : [new Ext.Panel({
							width : 650,
							id:'HomePlanDetailPanel',
							autoScroll : true,
							style : 'padding-left:10%;padding-top:10px;',
							autoHeight : true,
							border:false,
							autoLoad : {
								url:__ctxPath+'/pages/task/calendarplandetail.jsp?planId='+id
							}
						})]
			});
}