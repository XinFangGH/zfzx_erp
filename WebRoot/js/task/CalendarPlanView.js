Ext.ns('CalendarPlanView');
/**
 * 日程列表
 */
var CalendarPlanView = function() {
	return new Ext.Panel({
		id : 'CalendarPlanView',
		layout:'border',
		iconCls:'menu-cal-plan-view',
		title : '日程列表',
		items : [new Ext.FormPanel({
			defaults:{border:false},
			region:'north',
			height:88,
			id : 'CalendarPlanSearchForm',
			bodyStyle:'padding:5px',
			items: [{
	            layout:'column',
	            defaults:{border:false},
	            items:[
	            	{
	                columnWidth:.3,
	                layout: 'form',
	                defaults:{border:false,anchor:'96%,96%'},
	                items: [{
	                    xtype:'datetimefield',
	                    fieldLabel: '开始时间',
	                    name: 'Q_startTime_D_GE',
	                    format: 'Y-m-d H:i:s'
	                }, {
	                    xtype:'textfield',
	                    fieldLabel: '内容',
	                    name: 'Q_content_S_LK'
	                }]
	            },{
	               columnWidth:.3,
	                layout: 'form',
	                defaults:{border:false,anchor:'96%,96%'},
	                items: [{
	                    xtype:'datetimefield',
	                    fieldLabel: '结束时间',
	                    format: 'Y-m-d H:i:s',
	                    name: 'Q_endTime_D_GE'
	                }, {
	                    xtype:'textfield',
	                    fieldLabel: '分配人名',
	                    name: 'Q_assignerName_S_LK'
	                }]
	            },{
	                columnWidth:.3,
	                layout: 'form',
	                defaults:{border:false,anchor:'90%,90%'},
	                items: [{
	                   xtype : 'combo',
	                   		fieldLabel: '紧急程度',
							triggerAction : 'all',
							hiddenName : 'Q_urgent_SN_EQ',
							editable : false,
							store :[['0','一般'],['1','重要'],['2','紧急']],
	                    anchor:'95%'
	                }, {
							xtype : 'combo',
							fieldLabel: '状态',
	                		triggerAction : 'all',
							hiddenName : 'Q_status_SN_EQ',
							editable : false,
							store :[['0','未完成 '],['1','完成']],
	                    	anchor:'95%'
	                }]
	            }]
			},
			{
	            	xtype:'panel',
	            	border:false,
	            	layout:'hbox',
	            	layoutConfig:{
	            		pack:'center',
	            		align:'middle'
	            	},
	            	items:[
							{
					    		xtype : 'button',
								text : '查询',
								iconCls : 'search',
								handler : function() {
									var searchPanel = Ext.getCmp('CalendarPlanSearchForm');
									var gridPanel = Ext.getCmp('CalendarPlanGrid');
									if (searchPanel.getForm().isValid()) {
										$search({
											searchPanel :searchPanel,
											gridPanel : gridPanel
										});
									}
								}
							},{
								xtype:'button',
								text:'重置',
								iconCls:'reset',
								handler:function(){
									var searchPanel = Ext.getCmp('CalendarPlanSearchForm');
									searchPanel.getForm().reset();
								}
							}
	            	]
	            }
			]
			
			
		}), this.setup()]
	});
};
/**
 * 建立视图
 */
CalendarPlanView.prototype.setup = function() {
	return this.grid();
};
/**
 * 建立DataGrid
 */
CalendarPlanView.prototype.grid = function() {
	var sm = new Ext.grid.CheckboxSelectionModel();
	var cm = new Ext.grid.ColumnModel({
		columns : [sm, new Ext.grid.RowNumberer(), {
					header : 'planId',
					dataIndex : 'planId',
					hidden : true
				},{
					header : '状态',
					width: 50,
					dataIndex : 'status',
					renderer:function(value){
						if(value==1){
							return '<img src="'+__ctxPath+'/images/flag/task/finish.png" title="完成"/>';
						}else{
							return '<img src="'+__ctxPath+'/images/flag/task/go.png" title="未完成"/>';
						}
					}
				}, {
					header : '开始时间',
					width:120,
					dataIndex : 'startTime'
				}, {
					header : '结束时间',
					width:120,
					dataIndex : 'endTime'
				}, {
					header : '紧急程度',
					width:60,
					dataIndex : 'urgent',
					renderer:function(value){
						if(value==0){
							return "一般";	
						}else if(value==1){
							return "<font color='green'>重要</font>";
						}else{
							return "<font color='red'>紧急</font>";
						}
					}
				}, {
					width:250,
					header : '内容',
					dataIndex : 'content',
					renderer:function(value, metadata, record){
						var status=record.data.status;
						if(status==1){
							return '<font style="text-decoration:line-through;color:red;">' + value + '</font>';
						}else{
							return value;
						}
					}
				},  {
					header : '执行人',
					width:60,
					dataIndex : 'fullname'
				}, {
					header : '分配人',
					width:60,
					dataIndex : 'assignerName'
				}, {
					header : '任务类型',
					width:60,
					dataIndex : 'taskType',
					renderer:function(value){
						if(value==1){
							return "<font color='red'>限期任务</font>";
						}else{
							return "<font color='green'>非限期任务</font>";
						}
					}
				}, {
					header : '管理',
					dataIndex : 'planId',
					width : 80,
					sortable : false,
					renderer : function(value, metadata, record, rowIndex,
							colIndex) {
						var editId = record.data.planId;
						var status=record.data.status;
						var assignerId=record.data.assignerId;
						var str = '<button title="删除" value=" " class="btn-del" onclick="CalendarPlanView.remove('+ editId + ')"></button>';
						if(status==0){
							if(assignerId==curUserInfo.userId){//本人的分配的任务可以修改
								str += '&nbsp;<button title="编辑" value=" " class="btn-edit" onclick="CalendarPlanView.edit('+ editId + ')"></button>';
							}
							str+='&nbsp;<button title="完成任务" value="" class="btn-task" onclick="CalendarPlanView.finished('+ editId + ')"></button>';
						}
						return str;
					}
				}],
		defaults : {
			sortable : true,
			menuDisabled : false,
			width : 100
		}
	});

	var store = this.store();
	store.load({
				params : {
					start : 0,
					limit : 25
				}
			});
	var grid = new Ext.grid.GridPanel({
				region:'center',
				id : 'CalendarPlanGrid',
				tbar : this.topbar(),
				store : store,
				trackMouseOver : true,
				disableSelection : false,
				loadMask : true,
				cm : cm,
				sm : sm,
				viewConfig : {
					forceFit : true,
					enableRowBody : false,
					showPreview : false
				},
				bbar : new Ext.PagingToolbar({
							pageSize : 25,
							store : store,
							displayInfo : true,
							displayMsg : '当前显示从{0}至{1}， 共{2}条记录',
							emptyMsg : "当前没有记录"
						})
			});
	return grid;

};

/**
 * 初始化数据
 */
CalendarPlanView.prototype.store = function() {
	var store = new Ext.data.Store({
		proxy : new Ext.data.HttpProxy({
					url : __ctxPath + '/task/listCalendarPlan.do'
				}),
		reader : new Ext.data.JsonReader({
					root : 'result',
					totalProperty : 'totalCounts',
					id : 'id',
					fields : [{
								name : 'planId',
								type : 'int'
							}

							, 'startTime', 'endTime', 'urgent', 'content',
							'status', 'userId', 'fullname', 'assignerId',
							'assignerName', 'feedback', 'showStyle', 'taskType']
				}),
		remoteSort : true
	});
	store.setDefaultSort('planId', 'desc');
	return store;
};

/**
 * 建立操作的Toolbar
 */
CalendarPlanView.prototype.topbar = function() {
	var toolbar = new Ext.Toolbar({
				id : 'CalendarPlanFootBar',
				height : 30,
				bodyStyle : 'text-align:left',
				items : [{
					iconCls : 'btn-add',
					text : '添加日程',
					xtype : 'button',
					handler : function() {
						new CalendarPlanForm();
					}
				}, '-', {
					iconCls : 'btn-del',
					text : '删除日程',
					xtype : 'button',
					handler : function() {

						var grid = Ext.getCmp("CalendarPlanGrid");

						var selectRecords = grid.getSelectionModel()
								.getSelections();

						if (selectRecords.length == 0) {
							Ext.ux.Toast.msg("信息", "请选择要删除的记录！");
							return;
						}
						var ids = Array();
						for (var i = 0; i < selectRecords.length; i++) {
							ids.push(selectRecords[i].data.planId);
						}

						CalendarPlanView.remove(ids);
					}
				}, '-', {
					text : '我分配的任务',
					xtype : 'button',
					iconCls : 'btn-myAssign',
					handler : function() {
						var form = Ext.getCmp('CalendarPlanSearchForm')
								.getForm();
						form.submit({
									waitMsg : '正在提交查询',
									url : __ctxPath
											+ '/task/listCalendarPlan.do',
									params : {
										'Q_assignerId_L_EQ' : curUserInfo.userId
									},
									success : function(formPanel, action) {
										var result = Ext.util.JSON
												.decode(action.response.responseText);
										Ext.getCmp('CalendarPlanGrid')
												.getStore().loadData(result);
									}
								});
					}
				}, '-', {
					text : '今日常务',
					xtype : 'button',
					iconCls : 'menu-cal-plan',
					handler : function() {
//						App.clickTopTab('MyPlanTaskView');
						var tabs = Ext.getCmp('centerTabPanel');
						tabs.add( {
							xtype : 'iframepanel',
							title : '今日常务',
							id : 'myCalendarPlanId',
							loadMask : {
								msg : '正在加载...,请稍等...'
							},
							iconCls : 'menu-cal-plan',
							defaultSrc : __ctxPath + '/pages/task/ILogCalendar/calendar.jsp?id='
									+ Math.random(),
							listeners : {
								domready : function(iframe) {
								}
							}
						});
						tabs.activate('myCalendarPlanId');
					}
				}]
			});
	return toolbar;
};

/**
 * 删除单个记录
 */
CalendarPlanView.remove = function(id) {
	var grid = Ext.getCmp("CalendarPlanGrid");
	Ext.Msg.confirm('信息确认', '您确认要删除该记录吗？', function(btn) {
				if (btn == 'yes') {
					Ext.Ajax.request({
								url : __ctxPath
										+ '/task/multiDelCalendarPlan.do',
								params : {
									ids : id
								},
								method : 'post',
								success : function() {
									Ext.ux.Toast.msg("信息提示", "成功删除所选记录！");
									grid.getStore().reload({
												params : {
													start : 0,
													limit : 25
												}
											});
								}
							});
				}
			});
};

/**
 * 
 */
CalendarPlanView.edit = function(id) {
	new CalendarPlanForm(id);
};
/**
 * 完成任务，填写反馈意见
 * @param {} id
 */
CalendarPlanView.finished=function(id){
	new CalendarPlanFinishForm(id);
};
