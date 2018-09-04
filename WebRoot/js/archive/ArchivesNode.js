/**
 * @author:
 * @class ArchivesNode
 * @extends Ext.Panel
 * @description 拟稿管理
 * @company 北京互融时代软件有限公司
 * @createtime:2010-01-16
 */
ArchivesNode = Ext.extend(Ext.Panel, {
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		// 初始化组件
		this.initUIComponents();
		// 调用父类构造
		ArchivesNode.superclass.constructor.call(this, {
					id : this.id ? this.id : '',
					iconCls : '',
					title : this.title ? this.title : '流程结点',
					region : 'center',
					layout : 'border',
					items : [this.searchPanel, this.gridPanel, this.assigneeGrid]
				});
	},// end of constructor

	// 初始化组件
	initUIComponents : function() {
		// 初始化搜索条件Panel
		this.searchPanel = new Ext.FormPanel({
					height : 35,
					region : 'north',
					frame : false,
					border : false,
					layout : 'hbox',
					layoutConfig : {
						padding : '5',
						align : 'middle'
					},
					defaults : {
						style : 'padding:0px 5px 0px 5px;',
						border : false,
						anchor : '98%,98%',
						labelWidth : 75,
						xtype : 'label'
					},
					items : [{
								text : '类型名称'
							}, {
								name : 'Q_typeName_S_LK',
								width : 100,
								xtype : 'textfield'
							}, {
								text : '发文字号'
							}, {
								width : 100,
								name : 'Q_archivesNo_S_LK',
								xtype : 'textfield'
							}, {
								text : '文件标题'
							}, {
								width : 100,
								name : 'Q_subject_S_LK',
								xtype : 'textfield'
							}, {
								xtype : 'button',
								text : '查询',
								iconCls : 'search',
								handler : this.search.createCallback(this)
							}]
				});// end of the searchPanel
				
		this.gridPanel = new HT.GridPanel({
			region : 'center',
			rowActions : true,
			url : __ctxPath + "/archive/listArchives.do",
			baseParams : {
				'Q_status_S_EQ' : this.title?this.title:''
			},
			fields : [{
						name : 'archivesId',
						type : 'int'
					}, 'archivesType', 'archivesRecType', 'archivesNo',
					'issueDep', 'depId', 'subject', 'issueDate', 'status',
					'shortContent', 'fileCounts', 'privacyLevel',
					'urgentLevel', 'issuer', 'issuerId', 'keywords', 'sources',
					'archType', 'createtime', 'runId','tasks','archStatus'],
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
					},{
						header:'工作流',
						dataIndex:'tasks',
						renderer:function(tasks){
							var reVal='';
							if(tasks.length>0){
								
								for(var i=0;i<tasks.length;i++){
									reVal+=tasks[i].taskName;
									if(tasks[i].userId){
										reVal+='(';
										if(curUserInfo.userId==tasks[i].userId){
											reVal+='<a href="#" onclick="App.MyDesktopClickTopTab(\'ProcessNextForm\',{taskId:'
											+tasks[i].taskId+',activityName:\''+tasks[i].taskName+'\'})">';
										}
										reVal+=tasks[i].fullname
										if(curUserInfo.userId==tasks[i].userId){
											reVal+="</a>";
										}
										reVal+= ')&nbsp;&nbsp;';
									}
								}
							}
							return reVal;
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
			id : 'editArchivesNodesGridId',
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
	
	/**
	 * 行单击事件
	 */
	rowClicks : function(grid,rowIndex, e){		
		var selGrid = Ext.getCmp('editArchivesNodesGridId');
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
	
	rowClick : function(grid, rowindex, e) {
		grid.getSelectionModel().each(function(rec) {
			new ArchivesDetailWin({
				archivesId : rec.data.archivesId,
				runId:rec.data.runId
			}).show();
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
		var archStatus =record.data.archStatus;
		var activityName = '';
		if (archStatus == '1') {
			Ext.ux.Toast.msg('提示信息', '发文已归档!');
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
ArchivesNode.jumpFlowTask=function(taskId,taskName){
	App.MyDesktopClickTopTab('ProcessNextForm',{taskId:taskId,activityName:taskName});
};