//1.此处定义的nodeObj是系统桌面所有的功能点(nodeId必须和个人桌面显示的对象的名称相同,nodeName代表菜单树节点名称)
var rootObj={
	'root':[{
			nodeId:'TaskPanelViewNew',
			nodeName:'待办任务'
		},{
			nodeId:'SystemAccountIsException',
			nodeName:'用户资金异常账单'
		},{
			nodeId:'LateView',
			nodeName:'逾期追偿提醒'
		},{
			nodeId:'PlBidPlanView',
			nodeName:'已齐标提醒'
		}
	]
};
var nodeObj={
	//命名规则：业务id_任意字符串(必须包含Desk)!功能名称
	'team':[{
			nodeId:'team_task1',
			nodeName:'team1'
		},{
			nodeId:'team_task2',
			nodeName:'team2'
		},{
			nodeId:'team_task3',
			nodeName:'team3'
		},{
			nodeId:'team_task4',
			nodeName:'team4'
		},{
			nodeId:'team_task5',
			nodeName:'team5'
		},{
			nodeId:'team_task6',
			nodeName:'team6'
		},{
			nodeId:'team_task7',
			nodeName:'team7'
		},{
			nodeId:'team_task8',
			nodeName:'team8'
		},{
			nodeId:'team_task9',
			nodeName:'team9'
		},{
			nodeId:'team_task10',
			nodeName:'team10'
		}
	],
	'factoring':[{
			nodeId:'factoring_task1',
			nodeName:'factoring1'
		},{
			nodeId:'factoring_task2',
			nodeName:'factoring2'
		},{
			nodeId:'factoring_task3',
			nodeName:'factoring3'
		},{
			nodeId:'factoring_task4',
			nodeName:'factoring4'
		},{
			nodeId:'factoring_task5',
			nodeName:'factoring5'
		},{
			nodeId:'factoring_task6',
			nodeName:'factoring6'
		},{
			nodeId:'factoring_task7',
			nodeName:'factoring7'
		},{
			nodeId:'factoring_task8',
			nodeName:'factoring8'
		},{
			nodeId:'factoring_task9',
			nodeName:'factoring9'
		},{
			nodeId:'factoring_task10',
			nodeName:'factoring10'
		},{
			nodeId:'factoring_salesTrends',
			nodeName:'销售趋势图'
		},{
			nodeId:'factoring_productScatter',
			nodeName:'产品发布图'
		}
	],
	'investcstmanger':[{
			nodeId:'investcstmanger_task1',
			nodeName:'investcstmanger1'
		},{
			nodeId:'investcstmanger_task2',
			nodeName:'investcstmanger2'
		},{
			nodeId:'investcstmanger_task3',
			nodeName:'investcstmanger3'
		},{
			nodeId:'investcstmanger_task4',
			nodeName:'investcstmanger4'
		},{
			nodeId:'investcstmanger_task5',
			nodeName:'investcstmanger5'
		},{
			nodeId:'investcstmanger_task6',
			nodeName:'investcstmanger6'
		},{
			nodeId:'investcstmanger_task7',
			nodeName:'investcstmanger7'
		},{
			nodeId:'investcstmanger_task8',
			nodeName:'investcstmanger8'
		},{
			nodeId:'investcstmanger_task9',
			nodeName:'investcstmanger9'
		},{
			nodeId:'investcstmanger_task10',
			nodeName:'investcstmanger10'
		}
	]
};

//公有功能
//============================================================================
TaskPanelViewNew = Ext.extend(Ext.ux.Portlet, {
	tools : null,
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		this.initTool();
		TaskPanelViewNew.superclass.constructor.call(this, {
			id : 'TaskPanelViewNew',
			title : '待办任务',
			iconCls : 'menu-flowWait',
			height:280,
			tools : this.tools,
			autoLoad : {
				url : __ctxPath + '/flow/displayTask.do?processName='+processNameFlowKey+"&limitCount=7",
				scripts : true
			}
		});
	},
	initTool : function() {
		this.tools = [{
			id : 'refresh',
			handler : function() {
				Ext.getCmp('TaskPanelViewNew').getUpdater().update(__ctxPath+ '/flow/displayTask.do?processName='+processNameFlowKey+"&limitCount=7");
			}
		}];
	}
});

SystemAccountIsException = Ext.extend(Ext.ux.Portlet, {
	tools : null,
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		this.initTool();
		SystemAccountIsException.superclass.constructor.call(this, {
			id : 'SystemAccountIsException',
			title : '用户资金异常账单',
			iconCls : 'menu-message',
			height:280,
			tools : this.tools,
			autoLoad : {
				url : __ctxPath + '/creditFlow/creditAssignment/bank/desktopisExceptionAccountListObSystemAccount.do?isException=1'
			}
		});
	},
	initTool : function() {
		this.tools = [{
			id : 'refresh',
			handler : function() {
				Ext.getCmp('SystemAccountIsException').getUpdater().update(__ctxPath+ '/creditFlow/creditAssignment/bank/desktopisExceptionAccountListObSystemAccount.do?isException=1');
			}
		}];
	}
});

// 逾期追偿提醒
LateView = Ext.extend(Ext.ux.Portlet, {
	tools : null,
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		this.initTool();
		LateView.superclass.constructor.call(this, {
			id : 'LateView',
			title : '逾期追偿提醒',
			iconCls : 'menu-flowWait',
			height:280,
			tools : this.tools,
			autoLoad : {
				url : __ctxPath + '/compensatory/displayPlBidCompensatory.do',
				scripts : true
			}
		});
	},
	initTool : function() {
		this.tools = [{
			id : 'refresh',
			handler : function() {
				Ext.getCmp('LateView').getUpdater().update(__ctxPath+ '/compensatory/displayPlBidCompensatory.do');
			}
		}];
	}
});

// 已齐标
PlBidPlanView = Ext.extend(Ext.ux.Portlet, {
	tools : null,
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		this.initTool();
		PlBidPlanView.superclass.constructor.call(this, {
			id : 'PlBidPlanView',
			title : '已齐标提醒',
			iconCls : 'menu-flowWait',
			height:280,
			tools : this.tools,
			autoLoad : {
			url : __ctxPath +'/creditFlow/financingAgency/displayPlBidPlan.do',
				scripts : true
			}
		});
	},
	initTool : function() {
		this.tools = [{
			id : 'refresh',
			handler : function() {
				Ext.getCmp('PlBidPlanView').getUpdater().update(__ctxPath+ '/creditFlow/financingAgency/displayPlBidPlan.do');
			}
		}];
	}
});
/*MessagePanelView = Ext.extend(Ext.ux.Portlet, {
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
				Ext.getCmp('MessagePanelView').getUpdater().update(__ctxPath+ '/info/displayInMessage.do');
			}
		}];
	}
});*/
//============================================================================

//业务功能
//----------------------------------------------------------------------------
//以下定义的是个人桌面每一个功能对象(注意：对象名称必须和菜单树的节点id相同)
team_task1 = Ext.extend(Ext.ux.Portlet, {
	tools : null,
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		this.initTool();
		team_task1.superclass.constructor.call(this, {
			id : 'team_task1',
			title : getFeatureName('team','team_task1'),
			iconCls : 'menu-message',
			tools : this.tools
		});
	},
	initTool : function() {
		this.tools = [{
			id : 'refresh',
			handler : function() {
			}
		}];
	}
});

team_task2 = Ext.extend(Ext.ux.Portlet, {
	tools : null,
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		this.initTool();
		team_task2.superclass.constructor.call(this, {
			id : 'team_task2',
			title : getFeatureName('team','team_task2'),
			iconCls : 'menu-message',
			tools : this.tools
		});
	},
	initTool : function() {
		this.tools = [{
			id : 'refresh',
			handler : function() {
			}
		}];
	}
});

team_task3 = Ext.extend(Ext.ux.Portlet, {
	tools : null,
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		this.initTool();
		team_task3.superclass.constructor.call(this, {
			id : 'team_task3',
			title : getFeatureName('team','team_task3'),
			iconCls : 'menu-message',
			tools : this.tools
		});
	},
	initTool : function() {
		this.tools = [{
			id : 'refresh',
			handler : function() {
			}
		}];
	}
});

team_task4 = Ext.extend(Ext.ux.Portlet, {
	tools : null,
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		this.initTool();
		team_task4.superclass.constructor.call(this, {
			id : 'team_task4',
			title : getFeatureName('team','team_task4'),
			iconCls : 'menu-message',
			tools : this.tools
		});
	},
	initTool : function() {
		this.tools = [{
			id : 'refresh',
			handler : function() {
			}
		}];
	}
});

factoring_salesTrends = Ext.extend(Ext.ux.Portlet, {
	tools : null,
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		this.initTool();
		factoring_salesTrends.superclass.constructor.call(this, {
//			id : 'team_reChargeDesk', //如果是图表的话这个id不需要
			title : getFeatureName('team','factoring_salesTrends'),
			iconCls : 'menu-message',
			tools : this.tools,
			html:'<div id="factoring_salesTrends"></div>'//如果是图表的话必须定义html
		});
	},
	initTool : function() {
		this.tools = [{
			id : 'refresh',
			handler : function() {
			}
		}];
	}
});

factoring_productScatter = Ext.extend(Ext.ux.Portlet, {
	tools : null,
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		this.initTool();
		factoring_productScatter.superclass.constructor.call(this, {
			title : getFeatureName('team','factoring_productScatter'),
			iconCls : 'menu-message',
			tools : this.tools,
			html:'<div id="factoring_productScatter"></div>'//如果是图表的话必须定义html
		});
	},
	initTool : function() {
		this.tools = [{
			id : 'refresh',
			handler : function() {
			}
		}];
	}
});
//----------------------------------------------------------------------------
//3.该方法用来为功能的title赋值
//type:业务id  , id:节点id
function getFeatureName(type,id){
	var result="";
	var temp=nodeObj[type];
	if(temp){
		for(var j=0;j<temp.length;j++){
			var childId=temp[j].nodeId;//功能id
			if(id==childId){
				result=temp[j].nodeName;//功能名称
				break;
			}
		}
	}
	return result;
}