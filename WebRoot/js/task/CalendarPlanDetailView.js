/**
 * 显示计划任务中的详细信息
 * @param {} planId
 */
var CalendarPlanDetailView=function(planId){
	this.planId=planId;
	var detailPanell = new Ext.Panel({
				autoHeight : true,
				autoWidth:true,
				border : false,
				autoLoad : {
					url : __ctxPath
							+ '/pages/task/calendarplandetail.jsp?planId=' + planId
				}
	});
	var window = new Ext.Window({
				title:'任务信息',
				iconCls:'menu-cal-plan',
				id : 'CalendarPlanDetailView',
				width : 560,
				height : 350,
				modal : true,
				autoScroll:true,
				layout : 'form',
				buttonAlign : 'center',
				items : [detailPanell],
				buttons : [{
							text : '关闭',
							iconCls : 'close',
							handler : function() {
								window.close();
							}
						}]
	});
	
	$request({
		url:__ctxPath+'/task/getCalendarPlan.do?planId=' + planId,
		method:'post',
		success:function(resp,options){
			var result = Ext.util.JSON.decode(resp.responseText);
			var plan=result.data;
			if(plan.status=='0'){//尚未完成 
				window.addButton(new Ext.Button({
							text : '完成任务',
							iconCls : 'btn-save',
							handler : function() {
								new CalendarPlanFinishForm(planId);
							}
				}));
				window.doLayout();
			}
			window.show();
		},
		failure:function(response,options){
			window.show();
		}
	});

	
};