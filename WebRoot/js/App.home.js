// 首页的设置
// 日志模块
DiaryPanelView = Ext.extend(Ext.ux.Portlet, {
	tools : null,
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		this.initTool();
		DiaryPanelView.superclass.constructor.call(this, {
			id : 'DiaryPanelView',
			title : '我的日志',
			iconCls : 'menu-diary',
			tools : this.tools,
			autoLoad : {
				url : __ctxPath + '/system/displayDiary.do?start=0&limit=8',
				scripts : true
			}
		});
	},
	initTool : function() {
		this.tools = [{
			id : 'refresh',
			handler : function() {
				Ext.getCmp('DiaryPanelView').getUpdater().update(__ctxPath
						+ '/system/displayDiary.do?start=0&limit=8');
			}
		}, {
			id : 'close',
			handler : function(e, target, panel) {
				Ext.Msg.confirm('提示信息', '确认删除此模块吗？', function(btn) {
					if (btn == 'yes') {
						panel.ownerCt.remove(panel, true);
					}
				});
			}
		}];
	}
});

MessagePanelView = Ext.extend(Ext.ux.Portlet, {
	tools : null,
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		this.initTool();
		MessagePanelView.superclass.constructor.call(this, {
			id : 'MessagePanelView',
			title : '个人消息',
			iconCls : 'menu-message',
			tools : this.tools,
			autoLoad : {
				url : __ctxPath + '/info/displayInMessage.do'
			}
		});
	},
	initTool : function() {
		this.tools = [{
			id : 'refresh',
			handler : function() {
				Ext.getCmp('MessagePanelView').getUpdater().update(__ctxPath
						+ '/info/displayInMessage.do');
			}
		}];
	}

});

//var processName="smallLoanFlow,smallLoanFast,FinancingFlow,guaranteeNormalFlow";
// 待办事项
TaskPanelView = Ext.extend(Ext.ux.Portlet, {
	tools : null,
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		this.initTool();
		TaskPanelView.superclass.constructor.call(this, {
			id : 'TaskPanelView',
			title : '项目待办事项',
			iconCls : 'menu-flowWait',
			tools : this.tools,
			autoLoad : {
				url : __ctxPath + '/flow/displayTask.do?processName='+processNameFlowKey,
				scripts : true
			}
		});
	},
	initTool : function() {
		this.tools = [{
			id : 'refresh',
			handler : function() {
				Ext.getCmp('TaskPanelView').getUpdater().update(__ctxPath
						+ '/flow/displayTask.do?processName='+processNameFlowKey);
			}
		}];
	}

});
// 我的约会
AppointmentPanelView = Ext.extend(Ext.ux.Portlet, {
	tools : null,
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		this.initTool();
		AppointmentPanelView.superclass.constructor.call(this, {
			id : 'AppointmentPanelView',
			title : '我的约会',
			iconCls : 'menu-appointment',
			tools : this.tools,
			autoLoad : {
				url : __ctxPath + '/task/displayAppointment.do?start=0&limit=8'
			}
		});
	},
	initTool : function() {
		this.tools = [{
			id : 'refresh',
			handler : function() {
				Ext
						.getCmp('AppointmentPanelView')
						.getUpdater()
						.update(__ctxPath
								+ '/task/displayAppointment.do?start=0&limit=8');
			}
		}, {
			id : 'close',
			handler : function(e, target, panel) {
				Ext.Msg.confirm('提示信息', '确认删除此模块吗？', function(btn) {
					if (btn == 'yes') {
						panel.ownerCt.remove(panel, true);
					}
				});
			}
		}];
	}

});
// 我的日程
CalendarPlanPanelView = Ext.extend(Ext.ux.Portlet, {
	tools : null,
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		this.initTool();
		CalendarPlanPanelView.superclass.constructor.call(this, {
			id : 'CalendarPlanPanelView',
			title : '我的日程',
			iconCls : 'menu-cal-plan-view',
			tools : this.tools,
			autoLoad : {
				url : __ctxPath
						+ '/task/displayCalendarPlan.do?start=0&limit=8'
			}
		});
	},
	initTool : function() {
		this.tools = [{
			id : 'refresh',
			handler : function() {
				Ext
						.getCmp('CalendarPlanPanelView')
						.getUpdater()
						.update(__ctxPath
								+ '/task/displayCalendarPlan.do?start=0&limit=8');
			}
		}, {
			id : 'close',
			handler : function(e, target, panel) {
				Ext.Msg.confirm('提示信息', '确认删除此模块吗？', function(btn) {
					if (btn == 'yes') {
						panel.ownerCt.remove(panel, true);
					}
				});
			}
		}];
	}

});
// 我的计划
MyPlanPanelView = Ext.extend(Ext.ux.Portlet, {
	tools : null,
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		this.initTool();
		MyPlanPanelView.superclass.constructor.call(this, {
			id : 'MyPlanPanelView',
			title : '我的计划',
			iconCls : 'menu-myplan',
			tools : this.tools,
			autoLoad : {
				url : __ctxPath + '/task/displayWorkPlan.do?start=0&limit=8',
				scripts : true
			}
		});
	},
	initTool : function() {
		this.tools = [{
			id : 'refresh',
			handler : function() {
				Ext.getCmp('MyPlanPanelView').getUpdater().update(__ctxPath
						+ '/task/displayWorkPlan.do?start=0&limit=8');
			}
		}, {
			id : 'close',
			handler : function(e, target, panel) {
				Ext.Msg.confirm('提示信息', '确认删除此模块吗？', function(btn) {
					if (btn == 'yes') {
						panel.ownerCt.remove(panel, true);
					}
				});
			}
		}];
	}

});

// 个人文档
MyDocumentPanelView = Ext.extend(Ext.ux.Portlet, {
	tools : null,
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		this.initTool();
		MyDocumentPanelView.superclass.constructor.call(this, {
			id : 'MyDocumentPanelView',
			title : '我的文档',
			iconCls : 'menu-document',
			tools : this.tools,
			autoLoad : {
				url : __ctxPath
						+ '/document/displayDocument.do?start=0&limit=8'
			}
		});
	},
	initTool : function() {
		this.tools = [{
			id : 'refresh',
			handler : function() {
				Ext.getCmp('MyDocumentPanelView').getUpdater().update(__ctxPath
						+ '/document/displayDocument.do?start=0&limit=8');
			}
		}, {
			id : 'close',
			handler : function(e, target, panel) {
				Ext.Msg.confirm('提示信息', '确认删除此模块吗？', function(btn) {
					if (btn == 'yes') {
						panel.ownerCt.remove(panel, true);
					}
				});
			}
		}];
	}

});
// 我的邮件
MyMailPanelView = Ext.extend(Ext.ux.Portlet, {
	tools : null,
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		this.initTool();
		MyMailPanelView.superclass.constructor.call(this, {
			id : 'MyMailPanelView',
			title : '我的邮件',
			iconCls : 'menu-mail',
			tools : this.tools,
			autoLoad : {
				url : __ctxPath + '/communicate/displayMail.do?start=0&limit=8'
			}
		});
	},
	initTool : function() {
		this.tools = [{
			id : 'refresh',
			handler : function() {
				Ext.getCmp('MyMailPanelView').getUpdater().update(__ctxPath
						+ '/communicate/displayMail.do?start=0&limit=8');
			}
		}, {
			id : 'close',
			handler : function(e, target, panel) {
				Ext.Msg.confirm('提示信息', '确认删除此模块吗？', function(btn) {
					if (btn == 'yes') {
						panel.ownerCt.remove(panel, true);
					}
				});
			}
		}];
	}

});
// 部门计划
DepPlanPanelView = Ext.extend(Ext.ux.Portlet, {
	tools : null,
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		this.initTool();
		DepPlanPanelView.superclass.constructor.call(this, {
			id : 'DepPlanPanelView',
			title : '部门计划',
			iconCls : 'menu-depplan',
			tools : this.tools,
			autoLoad : {
				url : __ctxPath + '/task/displayDepWorkPlan.do'// ,scripts:true
			}
		});
	},
	initTool : function() {
		this.tools = [{
			id : 'refresh',
			handler : function() {
				Ext.getCmp('DepPlanPanelView').getUpdater().update(__ctxPath
						+ '/task/displayDepWorkPlan.do');
			}
		}, {
			id : 'close',
			handler : function(e, target, panel) {
				Ext.Msg.confirm('提示信息', '确认删除此模块吗？', function(btn) {
					if (btn == 'yes') {
						panel.ownerCt.remove(panel, true);
					}
				});
			}
		}];
	}

});

// 模板选择器
PanelSelectorWin = Ext.extend(Ext.Window, {
	formPanel : null,
	buttons : null,
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		this.initUI();
		PanelSelectorWin.superclass.constructor.call(this, {
			id : 'PanelSelectorWin',
			title : '选择显示模块',
			layout : 'fit',
			height : 220,
			width : 300,
			modal : true,
			defaults : {
				padding : '5'
			},
			buttons : this.buttons,
			buttonAlign : 'center',
			items : this.formPanel
		});
	},
	initUI : function() {
		this.formPanel = new Ext.FormPanel({
			id : 'PanelSelectorForm',
			border : false,
			layout : 'column',
			items : [{
				layout : 'form',
				columnWidth : .5,
				border : false,
				items : [{
					xtype : 'checkbox',
					boxLabel : '待办事项',
					hideLabel : true,
					id : 'TaskPanelViewCheckBox',
					name : 'TaskPanelView'
				}, {
					xtype : 'checkbox',
					boxLabel : '我的日志',
					hideLabel : true,
					id : 'DiaryPanelViewCheckBox',
					name : 'DiaryPanelView'
				}, {
					xtype : 'checkbox',
					hideLabel : true,
					boxLabel : '我的约会',
					id : 'AppointmentPanelViewCheckBox',
					name : 'AppointmentPanelView'
				}, {
					xtype : 'checkbox',
					boxLabel : '我的日程',
					hideLabel : true,
					id : 'CalendarPlanPanelViewCheckBox',
					name : 'CalendarPlanPanelView'
				}, {
					xtype : 'checkbox',
					boxLabel : '部门计划',
					hideLabel : true,
					id : 'DepPlanPanelViewCheckBox',
					name : 'DepPlanPanelView'
				}]
			}, {
				layout : 'form',
				columnWidth : .5,
				border : false,
				items : [{
					xtype : 'checkbox',
					hideLabel : true,
					boxLabel : '我的计划',
					id : 'MyPlanPanelViewCheckBox',
					name : 'MyPlanPanelView'
				}, {
					xtype : 'checkbox',
					boxLabel : '我的文档',
					hideLabel : true,
					id : 'MyDocumentPanelViewCheckBox',
					name : 'MyDocumentPanelView'
				}, {
					xtype : 'checkbox',
					boxLabel : '我的邮件',
					hideLabel : true,
					id : 'MyMailPanelViewCheckBox',
					name : 'MyMailPanelView'
				}]
			}]
		});
		// 将已经显示的PORTALITEM勾上
		var portal = Ext.getCmp('Portal');
		curUserInfo.portalConfig = [];
		var items = portal.items;
		for (var i = 0; i < items.length; i++) {
			var v = items.itemAt(i);
			for (var j = 0; j < v.items.getCount(); j++) {
				var m = v.items.itemAt(j);
				var portalItem = new PortalItem(m.id, i, j);
				curUserInfo.portalConfig.push(portalItem);
			}
		}
		var confs = curUserInfo.portalConfig;
		for (var i = 0; i < confs.length; i++) {
			var panelView = confs[i].panelId;
			var panelCheck = Ext.getCmp(panelView + 'CheckBox');
			if (panelCheck != null) {
				panelCheck.setValue(true);
				panelCheck.disable();
			}
		}

		this.buttons = [{
			xtype : 'button',
			text : '确定',
			iconCls : 'btn-save',
			handler : function() {
				var fd = Ext.getCmp('PortalItemRight');
				var portal = Ext.getCmp('Portal');
				var array = ['DiaryPanelView', 'TaskPanelView',
						'AppointmentPanelView', 'CalendarPlanPanelView',
						'DepPlanPanelView', 'MyPlanPanelView',
						'MyDocumentPanelView', 'MyMailPanelView'];
				for (var v = 0; v < array.length; v++) {
					var check = Ext.getCmp(array[v] + 'CheckBox');
					if (check != null) {
						if (check.getValue() && Ext.getCmp(array[v]) == null) {
							var panel = eval('new ' + array[v] + '()');
							fd.add(panel);
						}
					}
				}
				fd.doLayout();
				portal.doLayout();
				Ext.getCmp('PanelSelectorWin').close();
			}
		}, {
			xtype : 'button',
			text : '取消',
			iconCls : 'btn-cancel',
			handler : function() {
				Ext.getCmp('PanelSelectorWin').close();
			}
		}];
	}
});

AppHome = Ext.extend(Ext.Panel, {
	portalPanel : null,
	toolbar : null,
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		this.initUIComponents();
		AppHome.superclass.constructor.call(this, {
			title : '个人桌面',
			closable : false,
			id : 'AppHome',
			border : false,
			iconCls : 'menu-desktop',
			layout : 'fit',
			defaults : {
				padding : '0 5 0 0'
			},
			tbar : this.toolbar,
			items : this.portalPanel
		});
	},
	initUIComponents : function() {
		this.toolbar = new Ext.Toolbar({
			height : 30,
			items : ['->',
					{
						text : '重置密码',
						iconCls : 'btn-password',
						handler : function() {
							new setPasswordForm(curUserInfo.userId);
						}
					},
					'-',{
						text : '在线用户',
						iconCls : 'btn-onlineUser',
						handler : function() {
							OnlineUserSelector.getView().show();
						}
					},'-',{
						text : '意见箱',
						iconCls : 'btn-suggest-box',
						handler : function(){
							App.clickTopTab('SuggestBoxView',{title:'我的意见箱',userId:curUserInfo.userId});
						}
					},'-',
					{
						id : 'messageTip',
						xtype:'button',
						hidden : true,
						width : 50,
						height : 20,
						handler : function() {
							var megBtn = Ext.getCmp('messageTip');
							var megWin = Ext.getCmp('win');
							var megSpr = Ext.getCmp('messageTipSeparator');
							if (megWin == null) {
								new MessageWin().show();
							}
							megBtn.hide();
							megSpr.hide();
						}
					},new Ext.Toolbar.Separator({
						id : 'messageTipSeparator',
						hidden : true
					}),{
					    pressed : false,
						text : '便签',
						iconCls:'tipsTile',
						handler:function(){
							App.clickTopTab('PersonalTipsView');
						}
					},'-', {
				xtype : 'button',
				text : '启动新项目',
				hidden : !isGranted('newProjectForm'),
				iconCls : 'menu-flow-start',
				handler : function() {
					// 新建项目事件
				ZW.startSLProject();
				}
			},new Ext.Toolbar.Separator({
				hidden : !isGranted('newProjectForm')
			}), {
				xtype : 'button',
				text : '添加模块',
				iconCls : 'btn-apply-add',
				handler : function() {
					new PanelSelectorWin().show();
				}
			},'-', {
				xtype : 'button',
				text : '保存',
				iconCls : 'btn-save',
				handler : function() {
					var portal = Ext.getCmp('Portal');
					curUserInfo.portalConfig = [];
					var items = portal.items;
					for (var i = 0; i < items.length; i++) {
						var v = items.itemAt(i);
						for (var j = 0; j < v.items.getCount(); j++) {
							var m = v.items.itemAt(j);
							var portalItem = new PortalItem(m.id, i, j);
							curUserInfo.portalConfig.push(portalItem);
						}
					}
					Ext.Ajax.request({
						method : 'post',
						url : __ctxPath + '/system/saveIndexDisplay.do',
						success : function(request) {
							Ext.ux.Toast.msg('操作信息', '保存成功');
						},
						failure : function(request) {
							Ext.MessageBox.show({
								title : '操作信息',
								msg : '信息保存出错，请联系管理员！',
								buttons : Ext.MessageBox.OK,
								icon : 'ext-mb-error'
							});
						},
						params : {
							items : Ext.encode(curUserInfo.portalConfig)
						}
					});

				}
			}]

		});

		var tools = [{
			id : 'gear',
			handler : function() {
				Ext.Msg.alert('Message', 'The Settings tool was clicked.');
			}
		}, {
			id : 'close',
			handler : function(e, target, panel) {
				panel.ownerCt.remove(panel, true);
			}
		}];

		var confs = curUserInfo.portalConfig;
        
		confs = new Array();
		var p1 = {
			panelId : 'MessagePanelView',
			column : 0,
			row : 1
		};
		var p2 = {
			panelId : 'TaskPanelView',
			column : 0,
			row : 0
		};
		var p3 = {
			panelId : 'CalendarPlanPanelView',
			column : 1,
			row : 0
		};
	    var p4 = {
			panelId : 'MyPlanPanelView',
			column : 1,
			row : 1
		};
		
		//MyMailPanelView
		var p5 = {
			panelId : 'MyMailPanelView',
			column : 1,
			row : 2
		};
		//MyDocumentPanelView
		var p6 = {
			panelId : 'MyDocumentPanelView',
			column : 1,
			row : 3
		};
		//DiaryPanelView
		var p7 = {
			panelId : 'DiaryPanelView',
			column : 1,
			row : 4
		};
		//AppointmentPanelView
		var p8 = {
			panelId : 'AppointmentPanelView',
			column : 1,
			row : 5
		};
		//DepPlanPanelView
		var p9 = {
			panelId : 'DepPlanPanelView',
			column : 1,
			row : 6
		};
		
		
		confs.push(p2);
		confs.push(p1);
		confs.push(p3);
		confs.push(p4);
		confs.push(p5);
		confs.push(p6);
		confs.push(p7);
		confs.push(p8);
		confs.push(p9);
		
		var column0 = [];
		var column1 = [];
		for (var v = 0; v < confs.length; v++) {
			if (confs[v].column==0)
			{
				column0.push(eval('new ' + confs[v].panelId + '()'));
			} else {
				column1.push(eval('new ' + confs[v].panelId + '()'));
			}
		}
		this.portalPanel = {
			id : 'Portal',
			xtype : 'portal',
			border : false,
			region : 'center',
			margins : '35 5 5 0',
			items : [{
				columnWidth : .65,
				style : 'padding:10px 0 10px 10px',
				id : 'PortalItem',
				items : column0
			}, {
				columnWidth : .35,
				style : 'padding:10px 10px 10px 10px',
				id:'PortalItemRight',
				items : column1
			}]
		};

	}

});
