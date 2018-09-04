var CalendarPlanFinishForm = function(planId) {
	this.planId = planId;
	var fp = this.setup();
	var detailPanell = new Ext.Panel({
			autoHeight : true,
			width:530,
			border : false,
			autoLoad : {
				url : __ctxPath
						+ '/pages/task/calendarplandetail.jsp?planId=' + planId
			}
	});
	var window = new Ext.Window({
				id : 'CalendarPlanFinishFormWin',
				title : '完成任务',
				width : 560,
				height:420,
				iconCls:'menu-cal-plan',
				modal : true,
				layout : 'anchor',
				plain : true,
				bodyStyle : 'padding:5px;',
				buttonAlign : 'center',
				items : [detailPanell,this.setup()],
				buttons : [{
					text : '完成任务',
					id:'calendarPlanFinishBtn',
					iconCls: 'btn-save',
					handler : function() {
						var fp = Ext.getCmp('CalendarPlanFinishForm');
						if (fp.getForm().isValid()) {
							fp.getForm().submit({
								method : 'post',
								params:{'calendarPlan.status':1},
								waitMsg : '正在提交数据...',
								success : function(fp, action) {
									Ext.ux.Toast.msg('操作信息', '成功保存信息！');
									var grid=Ext.getCmp('CalendarPlanGrid');
									if(grid!=null){
									grid.getStore().reload();
									}
									window.close();
								},
								failure : function(fp, action) {
									Ext.MessageBox.show({
												title : '操作信息',
												msg : '信息保存出错，请联系管理员！',
												buttons : Ext.MessageBox.OK,
												icon : 'ext-mb-error'
											});
									window.close();
								}
							});
						}
					}
				}, {
					text : '取消',
					iconCls: 'btn-cancel',
					handler : function() {
						window.close();
					}
				}]
			});
	window.show();
};

CalendarPlanFinishForm.prototype.setup = function() {

	var formPanel = new Ext.FormPanel({
				url : __ctxPath + '/task/magSaveCalendarPlan.do',
				layout : 'form',
				id : 'CalendarPlanFinishForm',
				frame : true,
				
				defaults : {
					anchor : '98%,98%'
				},
				items : [{
							name : 'calendarPlan.planId',
							id : 'planId',
							xtype : 'hidden',
							value : this.planId == null ? '' : this.planId
						},
						{
							xtype:'textarea',
							fieldLabel:'反馈',
							allowBlank:false,
							name:'calendarPlan.feedback',
							id:'feedback'
						}

				]
			});

	if (this.planId != null && this.planId != undefined) {
//		formPanel.getForm().load({
//					deferredRender : false,
//					url : __ctxPath + '/task/getCalendarPlan.do?planId='+ this.planId,
//					waitMsg : '正在载入数据...',
//					success : function(form, action) {
//						var plan=action.result.data;
//						if(plan.status==1){
//							Ext.getCmp("calendarPlanFinishBtn").setDisabled(true);
//						}
//					},
//					failure : function(form, action) {
//						
//					}
//				});
		Ext.Ajax.request({
			url : __ctxPath + '/task/getCalendarPlan.do',
			method : 'post',
			params : {
				planId : this.planId
			},
			waitMsg : '正在载入数据...',
			success : function(result, request) {
				var respText = Ext.util.JSON.decode(result.responseText);
				if(respText.data.status==1){
					Ext.getCmp("calendarPlanFinishBtn").setDisabled(true);
				}
			},
			failure : function(form, action) {
				
			}
		});
	}
	return formPanel;

};
