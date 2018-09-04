/**
 * @author:
 * @class ArchivesMonitor
 * @extends Ext.Panel
 * @description 拟稿管理
 * @company 北京互融时代软件有限公司
 * @createtime:2010-01-16
 */
ArchivesMonitor = Ext.extend(Ext.Panel, {
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		// 初始化组件
		this.initUIComponents();
		// 调用父类构造
		ArchivesMonitor.superclass.constructor.call(this, {
			id : 'ArchivesMonitor',
			iconCls : 'menu-archive-monitor',
			title : '流程监控',
			region : 'center',
			layout : 'border',
			items : [this.searchPanel, this.gridPanel, this.assigneeGrid]
		});
	},// end of constructor

	// 初始化组件
	initUIComponents : function() {
		// 初始化搜索条件Panel
		this.searchPanel = new Ext.FormPanel({
			layout : 'form',
			region : 'north',
			height : 80,
			width : '100%',
			keys : [{
				key : Ext.EventObject.ENTER,
				fn : this.search.createCallback(this),
				scope : this
			}, {
				key : Ext.EventObject.ESC,
				fn : this.reset.createCallback(this),
				scope : this
			}],
			items : [{
				xtype : 'container',
				layout : 'column',
				border : false,
				style : 'padding-left:5px;padding-right:5px;padding-top:5px;',
				layoutConfig : {
					align : 'middle',
					padding : '5'
				},
				items : [{
					columnWidth : .2,
					xtype : 'container',
					layout : 'form',
					items : [{
						anchor : '99%',
						fieldLabel : '公文类型',
						name : 'Q_typeName_S_LK',
						xtype : 'textfield',
						maxLength : 125
					}, {
						anchor : '99%',
						fieldLabel : '公文状态',
						name : 'Q_status_S_LK',
						xtype : 'textfield',
						maxLength : 125
					}]
				}, {
					columnWidth : .2,
					xtype : 'container',
					layout : 'form',
					items : [{
						anchor : '99%',
						fieldLabel : '发文字号',
						name : 'Q_archivesNo_S_LK',
						xtype : 'textfield',
						maxLength : 125
					}, {
						anchor : '99%',
						fieldLabel : '发文机关或部门',
						name : 'Q_issueDep_S_LK',
						xtype : 'textfield',
						maxLength : 125
					}]
				}, {
					columnWidth : .3,
					xtype : 'container',
					layout : 'form',
					items : [{
						anchor : '99%',
						fieldLabel : '文件标题',
						name : 'Q_subject_S_LK',
						xtype : 'textfield',
						maxLength : 125
					}, {
						xtype : 'container',
						layout : 'column',
						fieldLabel : '发文时间',
						border : false,
						items : [{
							columnWidth : .49,
							name : 'Q_issueDate_D_GE',
							xtype : 'datefield',
							format : 'Y-m-d'
						}, {
							xtype : 'label',
							text : '至',
							style : 'margin-top:3px;'
						}, {
							columnWidth : .49,
							name : 'Q_issueDate_D_LE',
							xtype : 'datefield',
							format : 'Y-m-d'
						}]
					}]
				}, {
					columnWidth : .2,
					xtype : 'container',
					layout : 'form',
					items : [{
						anchor : '99%',
						fieldLabel : '秘密等级',
						name : 'Q_privacyLevel_S_LK',
						xtype : 'textfield',
						maxLength : 125
					}, {
						anchor : '99%',
						fieldLabel : '紧急程度',
						name : 'Q_urgentLevel_S_LK',
						xtype : 'textfield',
						maxLength : 125
					}]
				}, {
					columnWidth : .1,
					xtype : 'container',
					layout : 'form',
					defaults : {
						xtype : 'button'
					},
					items : [{
						text : '查询',
						iconCls : 'search',
						handler : this.search.createCallback(this),
						scope : this
					}, {
						style : 'margin-top:3px;',
						text : '重置',
						iconCls : 'reset',
						handler : this.reset.createCallback(this),
						scope : this
					}]
				}]
			}]
		}); // end of this searchPanel
				
		this.gridPanel = new HT.GridPanel({
			region : 'center',
			rowActions : true,
			baseParams : {
				'sort' : 'createtime',
				'dir'	: 'desc'
			},
			url : __ctxPath + "/archive/listArchives.do",
			fields : [{
					name : 'archivesId',
					type : 'int'
				}, 'archivesType', 'archivesRecType', 'archivesNo',
				'issueDep', 'depId', 'subject', 'issueDate', 'status',
				'shortContent', 'fileCounts', 'privacyLevel',
				'urgentLevel', 'issuer', 'issuerId', 'keywords', 'sources',
				'archType', 'createtime', 'runId','tasks','archStatus', 'handlerStatus'],
			columns : [{
				header : 'archivesId',
				dataIndex : 'archivesId',
				hidden : true
			}, {
				header : '公文类型名称',
				dataIndex : 'archivesType',
				renderer : function(value, metadata, record) {
					if (value) {
						return value.typeName;
					} else if (record.data.archivesRecType) {
						return record.data.archivesRecType.typeName;
					}
				}
			}, {
				header : '发文字号',
				dataIndex : 'archivesNo'
			}, {
				header : '发文机关或部门',
				dataIndex : 'issueDep'
			}, {
				header : '文件标题',
				dataIndex : 'subject'
			}, {
				header : '公文状态',
				dataIndex : 'status'
			}, {
				header : '秘密等级',
				dataIndex : 'privacyLevel'
			}, {
				header : '紧急程度',
				dataIndex : 'urgentLevel'
			}, {
				header : '发文时间',
				dataIndex : 'createtime',
				renderer : function(value) {
					return value.substring(0, 10);
				}
			}, {
				header : '处理状态',
				dataIndex : 'handlerStatus',
				renderer : function(status){
					return status == '1' ? '<font color="red">流程结束</font>' : '<font color="green">处理中</font>';
				}
			}, new Ext.ux.grid.RowActions({
				header : '管理',
				width : 100,
				actions : [{
					iconCls : 'btn-archives-detail',
					qtip : '查阅详情',
					style : 'margin:0 3px 0 3px'
				}, {
					iconCls : 'btn-archives-remind',
					qtip : '催办',
					style : 'margin:0 3px 0 3px',
					fn : function(rs) {
						if (isGranted('_ArchivesIssueHasten'))
							return true;
						return false;
					}
				}],
				listeners : {
					scope : this,
					'action' : this.onRowAction
				}
			})]
		});
		this.eStore = new Ext.data.Store({
			proxy : new Ext.data.HttpProxy({
				url : __ctxPath + '/archive/getHanlderUsersArchives.do'
			}),
			reader : new Ext.data.JsonReader({
				root : 'result',
				totalProperty : 'totalCounts',
				id : 'id',
				fields : [{
					name : 'userId',
					type : 'int'
				},'fullname','task']
			})
		});
		var eCm = new Ext.grid.ColumnModel({
			columns : [new Ext.grid.RowNumberer(), {
				header : '编号',
				dataIndex : 'userId',
				hidden : true
			}, {
				header : '执行人名称',
				dataIndex : 'fullname'
			}, {
				header : '执行操作',
				dataIndex : 'task',
				renderer : function(task){
					var reVal = task.taskName + '(';
					if(curUserInfo.userId == task.userId){
						reVal+='<a href="#" onclick="App.MyDesktopClickTopTab(\'ProcessNextForm\',{taskId:'
						+task.taskId+',activityName:\''+task.taskName+'\'})">';
					}
					reVal+=task.fullname;
					if(curUserInfo.userId == task.userId){
						reVal+="</a>";
					}
					reVal+= ')&nbsp;&nbsp;';
					return reVal;
				}
			}]
		});
		this.assigneeGrid = new Ext.grid.EditorGridPanel({
			id : 'assigneeGridId',
			title : '公文执行人信息',
			height : 150,
			width : '100%',
			layout : 'form',
			region : 'south',
			autoScroll : true,
			border : false,
			store : this.eStore,
			trackMouseOver : true,
			cm : eCm,
			viewConfig : {
				forceFit : true,
				enableRowBody : false,
				showPreview : false
			}
		});
		
		this.gridPanel.addListener('rowdblclick', this.rowClick);
		this.gridPanel.addListener('rowclick', this.rowClicks, this);

	},// end of the initComponents()
	rowClick : function(grid, rowindex, e) {
		grid.getSelectionModel().each(function(rec) {
				new ArchivesDetailWin({
					archivesId : rec.data.archivesId,
					runId:rec.data.runId
				}).show();
		});
	},
	/**
	 * 行单击事件
	 */
	rowClicks : function(grid,rowIndex, e){		
		var selGrid = Ext.getCmp('assigneeGridId');
		var selStore = selGrid.getStore();
		grid.getSelectionModel().each(function(rec){
			selGrid.setTitle(rec.data.status + " -- 公文执行人信息");
			selStore.load({
				params : {
					runId : rec.data.runId,
					taskStatus : rec.data.status
				}
			});
		});
	},	
	/**
	 * 
	 * @param {}
	 *            self 当前窗体对象
	 */
	search : function(self) {
		if (self.searchPanel.getForm().isValid()) {// 如果合法
			$search({
				searchPanel : self.searchPanel,
				gridPanel : self.gridPanel
			});
		}
	},
	// 清空
	reset : function(self){
		self.searchPanel.getForm().reset();
	},
	/**
	 * 删除多条记录
	 */
	delRecords : function() {
		var gridPanel = this.gridPanel;
		var selectRecords = gridPanel.getSelectionModel().getSelections();
		if (selectRecords.length == 0) {
			Ext.ux.Toast.msg("信息", "请选择要删除的记录！");
			return;
		}
		var ids = Array();
		for (var i = 0; i < selectRecords.length; i++) {
			ids.push(selectRecords[i].data.archivesId);
		}
		this.delByIds(ids);
	},

	/**
	 * 查阅详情
	 * 
	 * @param {}
	 *            record
	 */
	detail : function(record) {
		new ArchivesDetailWin({
					archivesId : record.data.archivesId,
					runId:record.data.runId
				}).show();
	},
	/**
	 * 催办功能
	 * 
	 * @param {}
	 *            record
	 */
	remind : function(record) {
		var status = record.data.status;
		var archStatus = record.data.archStatus;
		var activityName = '';

		if (archStatus == '1') {
			Ext.ux.Toast.msg('提示信息', '公文已归档!');
			return;
		} else {
			new ArchHastenForm({
						archivesId : record.data.archivesId,
						archivesNo : record.data.archivesNo,
						activityName : status
					}).show();
		}
	},
	/**
	 * 公文任务审核
	 * 
	 * @param {}
	 *            record
	 */
	approvalTask : function(record) {
		var contentPanel=App.getContentPanel();
		var runId=record.data.runId;
		var detailView=contentPanel.getItem('ProcessRunDetail'+runId);
		if(detailView==null){
			detailView=new ProcessRunDetail(runId,null,null,record.data.subject);
			contentPanel.add(detailView);
		}
		contentPanel.activate(detailView);
	},
	/**
	 * 管理列中的事件处理
	 * 
	 * @param {}
	 *            grid
	 * @param {}
	 *            record
	 * @param {}
	 *            action
	 * @param {}
	 *            row
	 * @param {}
	 *            col
	 */
	onRowAction : function(gridPanel, record, action, row, col) {
		switch (action) {
			case 'btn-archives-detail' :
				this.detail(record);
				break;
			case 'btn-archives-remind' :
				this.remind(record);
				break;
			case 'btn-approvalTask' :
				this.approvalTask(record);
				break;
			default :
				break;
		}
	}
});
ArchivesMonitor.jumpFlowTask=function(taskId,taskName){
	App.MyDesktopClickTopTab('ProcessNextForm',{taskId:taskId,activityName:taskName});
};