var CalendarPlanForm = function(planId,callback) {
	this.planId = planId;
	
	var fp = this.setup();
	var window = new Ext.Window({
				id : 'CalendarPlanFormWin',
				iconCls : 'menu-cal-plan-view',
				title : '日程详细信息',
				border : false,
				width : 660,
				minWidth : 450,
				height : 500,
				minHeight : 500,
				maximizable : true,
				modal : true,
			    layout : 'fit',
				buttonAlign : 'center',
				items : [fp],
				buttons : [{
					text : '保存',
					iconCls: 'btn-save',
					handler : function() {
						var fp = Ext.getCmp('CalendarPlanForm');
						var startTime=Ext.getCmp('startTime').getValue();
						var endTime=Ext.getCmp('endTime').getValue();
						var type=Ext.getCmp('taskType1').checked;
						var summary = fp.getCmpByName('calendarPlan.summary').getValue();
						var ctt = fp.getCmpByName('calendarPlan.content').getValue();
						if((!type)||(type&&startTime!=null&&startTime!=''&&endTime!=null&&endTime!='')){
							if(summary == null || summary.replace(/(^\s*)|(\s*$)/g, "") == ''){
								Ext.ux.Toast.msg('操作提示', '请输入日程概要！');
								return;
							}
							if(ctt == null || ctt == '' || ctt == ' '){
								Ext.ux.Toast.msg('操作提示', '请输入日程内容！');
								return;
							}
							if (fp.getForm().isValid()) {
								fp.getForm().submit({
									method : 'post',
									waitMsg : '正在提交数据...',
									success : function(fp, action) {
										Ext.ux.Toast.msg('操作信息', '成功保存信息！');
										var calenarPlanGrid=Ext.getCmp('CalendarPlanGrid');
										if(calenarPlanGrid!=null){
											calenarPlanGrid.getStore().reload();
										}
										if(callback!=null){
											callback.call(this);
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
						}else{
							Ext.ux.Toast.msg('操作信息', '请填写完整开始结束时间！');
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

CalendarPlanForm.prototype.setup = function() {

	var formPanel = new Ext.FormPanel({
				url : __ctxPath + '/task/magSaveCalendarPlan.do',
				layout : 'form',
				id : 'CalendarPlanForm',
				bodyStyle : 'padding:5px;',
				frame : false,
				border:0,
				defaults : {
					anchor : '98%,98%'
				},
				formId : 'CalendarPlanFormId',
				items : [{
							name : 'calendarPlan.planId',
							id : 'planId',
							xtype : 'hidden',
							value : this.planId == null ? '' : this.planId
						},{
							xtype:'radiogroup',
							fieldLabel : '紧急程度',
							autoHeight: true,
							columns : 3,
							items : [{
										boxLabel : '一般',
										name : 'calendarPlan.urgent',
										inputValue : 0,
										id:'urgent0',
										checked : true
									},{
										boxLabel : '重要',
										name : 'calendarPlan.urgent',
										inputValue : 1,
										id:'urgent1'
									},{
										boxLabel : '紧急',
										name : 'calendarPlan.urgent',
										inputValue : 2,
										id:'urgent2'
									}]
						}, {
							fieldLabel : '概要',
							xtype:'textfield',
							name : 'calendarPlan.summary',
							id : 'summary',
							allowBlank : false
						},{
							fieldLabel : '内容',
							xtype:'htmleditor',
							height:200,
							name : 'calendarPlan.content',
							id : 'content',
							allowBlank : false
						}, {
							fieldLabel : '员工ID',
							xtype:'hidden',
							name : 'calendarPlan.userId',
							id : 'userId',
							value:curUserInfo.userId
						}
						, {
							layout:'column',
							autoHeight: true,
							border:false,
							items:[
								{   
									columnWidth:.995,
								    layout : 'form',
								    border:false,
									style:'padding-left:0px;',
										items : [
											{
												fieldLabel:'分配给',
												style:'padding-left:0px;',
												xtype:'textfield',
												anchor : '96%,96%',
												name : 'calendarPlan.fullname',
												id : 'fullname',
												value:curUserInfo.fullname
											}]
									
								},{
									width : 100,
									xtype:'button',
									border:false,
									iconCls:'btn-user-sel',
									text:'选择员工',
									handler:function(){
										UserSelector.getView(function(userId,fullname){
											Ext.getCmp("userId").setValue(userId);
											Ext.getCmp("fullname").setValue(fullname);
										},true).show();
									}
								}
							]
						}, {
							xtype:'radiogroup',
							id:'planTypeGroup',
							fieldLabel : '任务类型',
							autoHeight: true,
							columns : 2,
							items : [{
										boxLabel : '限期任务',
										name : 'calendarPlan.taskType',
										inputValue : 1,
										id:'taskType1',
										checked : true,
										listeners:{
											check:function(ck,bval){
								                if(bval){
													Ext.getCmp("timeDuration").setVisible(true);
													Ext.getCmp('CalenderDisplayType').setVisible(true);
												}
											}
										}
									}, {
										boxLabel : '非限期任务',
										name : 'calendarPlan.taskType',
										id:'taskType2',
										inputValue : 2,
										listeners:{
											check:function(ck,bval){
												if(bval){
													Ext.getCmp("timeDuration").setVisible(false);
													Ext.getCmp('CalenderDisplayType').setVisible(false);
													Ext.getCmp("startTime").setValue('');
													Ext.getCmp("endTime").setValue('');
												}
											}
										}
									}]
						},{
							xtype:'fieldset',
							border:false,
							layout:'form',
							id:'timeDuration',
							autoHeight:true,
							style:'padding-left:0px;',
							items:[
								{
									fieldLabel : '开始时间',
									style:'padding-left:0px;',
									name : 'calendarPlan.startTime',
									xtype:'datetimefield',
									id : 'startTime',
									anchor : '98%,98%',
									format: 'Y-m-d H:i:s'
								}, {
									fieldLabel : '结束时间',
									style:'padding-left:0px;',
									name : 'calendarPlan.endTime',
									xtype:'datetimefield',
									id : 'endTime',
									anchor : '98%,98%',
									format: 'Y-m-d H:i:s'
								}
							]
						},{
						xtype:'fieldset',
						id:'CalenderDisplayType',
						layout:'form',
						autoHeight:true,
						border:false,
						style:'padding-left:0px;',
						items:[{
							xtype : 'radiogroup',
							fieldLabel : '显示方式',
							autoHeight: true,
							columns : 2,
							items : [{
										boxLabel : '仅在任务中显示',
										name : 'calendarPlan.showStyle',
										inputValue : 1,
										id: 'showStyle1',
										checked : true
									}, {
										boxLabel : '在日程与任务中显示',
										name : 'calendarPlan.showStyle',
										id: 'showStyle2',
										inputValue : 2
									}]
						}]
						}

				]
			});

	if (this.planId != null && this.planId != 'undefined') {
		formPanel.getForm().load({
					deferredRender : false,
					url : __ctxPath + '/task/getCalendarPlan.do?planId='
							+ this.planId,
					waitMsg : '正在载入数据...',
					success : function(form, action) {
						var plan=action.result.data;
						Ext.getCmp("urgent" + plan.urgent).setValue(true);
						Ext.getCmp("showStyle" + plan.showStyle).setValue(true);
//						Ext.getCmp("taskType" + plan.taskType).setValue(true);
						Ext.getCmp('planTypeGroup').setValue("taskType" + plan.taskType,true);
					},
					failure : function(form, action) {
						
					}
				});
	}
	return formPanel;

};
