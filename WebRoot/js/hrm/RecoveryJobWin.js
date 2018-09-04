/**
 * @author:
 * @class RecoveryJobWin
 * @extends Ext.Panel
 * @description 职位管理
 * @company 北京互融时代软件有限公司
 * @createtime:2010-01-16
 */
RecoveryJobWin = Ext.extend(Ext.Window, {
			// 构造函数
			constructor : function(_cfg) {
				if (_cfg == null) {
					_cfg = {};
				}
				Ext.apply(this, _cfg);
				// 初始化组件
				this.initComponents();
				// 调用父类构造
				RecoveryJobWin.superclass.constructor.call(this, {
							id : 'RecoveryJobWin',
							iconCls : 'menu-job',
							title : '职位恢复',
							border : false,
							region : 'center',
							width : 720,
							modal : true,
							height : 450,
							layout : 'border',
							items : [this.searchPanel, this.gridPanel],
							bottons : this.buttons,
							buttonAlign : 'center'
						});
			},// end of constructor

			// 职位分类ID
			typeId : null,

			// 条件搜索Panel
			searchPanel : null,

			// 数据展示Panel
			gridPanel : null,

			// GridPanel的数据Store
			store : null,

			// 头部工具栏
			topbar : null,

			// 初始化组件
			initComponents : function() {
				this.buttons = [{
							text : '取消',
							iconCls : 'btn-cancel',
							handler : this.cancel.createCallback(this)
						}];
				// 初始化搜索条件Panel
				this.searchPanel = new Ext.FormPanel({
							columnWidth : 1,
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
								labelWidth : 70,
								xtype : 'label'
							},
							items : [{
										text : '职位名称'
									}, {
										name : 'Q_jobName_S_LK',
										xtype : 'textfield'
									}, {
										text : '所属部门'
									}, {
										name : 'Q_depId_S_LK',
										xtype : 'textfield'
									}, {
										text : '备注'
									}, {
										name : 'Q_memo_S_LK',
										xtype : 'textfield'
									}, {
										xtype : 'button',
										text : '查询',
										iconCls : 'search',
										handler : this.search
												.createCallback(this)
									}, {
										name : 'Q_delFlag_SN_EQ',
										width : 80,
										xtype : 'hidden',
										value : 1
										// 状态为删除的
								}]
						});// end of the searchPanel

				// 加载数据至store
				this.store = new Ext.data.JsonStore({
							url : __ctxPath + "/hrm/listJob.do",
							baseParams : {
								"Q_delFlag_SN_EQ" : 1
							},// 只查询未被删除的职位
							root : 'result',
							totalProperty : 'totalCounts',
							remoteSort : true,
							fields : [{
										name : 'jobId',
										type : 'int'
									}, 'jobName', 'department', 'memo']
						});
				this.store.setDefaultSort('jobId', 'desc');
				// 加载数据
				this.store.load({
							params : {
								start : 0,
								limit : 25
							}
						});

				// 初始化ColumnModel
				var sm = new Ext.grid.CheckboxSelectionModel();
				var cm = new Ext.grid.ColumnModel({
							columns : [sm, new Ext.grid.RowNumberer(), {
										header : 'jobId',
										dataIndex : 'jobId',
										hidden : true
									}, {
										header : '职位名称',
										dataIndex : 'jobName'
									}, {
										header : '所属部门',
										dataIndex : 'department',
										renderer : function(value) {
											return value.depName;
										}
									}, {
										header : '备注',
										dataIndex : 'memo'
									}],
							defaults : {
								sortable : true,
								menuDisabled : false,
								width : 100
							}
						});
				// 初始化工具栏
				this.topbar = new Ext.Toolbar({
							height : 30,
							bodyStyle : 'text-align:left',
							items : [{
										iconCls : 'btn-empProfile-recovery',
										text : '恢复职位',
										xtype : 'button',
										handler : this.recoveryRecord
									}]
						});

				this.gridPanel = new Ext.grid.GridPanel({
							id : 'RecoveryJobGrid',
							height : 300,
							columnWidth : 1,
							stripeRows : true,
							region : 'center',
							tbar : this.topbar,
							autoScroll : true,
							store : this.store,
							trackMouseOver : true,
							disableSelection : false,
							loadMask : true,
							cm : cm,
							sm : sm,
							plugins : this.rowActions,
							viewConfig : {
								forceFit : true,
								autoFill : true, // 自动填充
								forceFit : true
							},
							bbar : new HT.PagingBar({store : this.store})
						});
			},// end of the initComponents()

			/**
			 * 
			 * @param {}
			 *            self 当前窗体对象
			 */
			search : function(self) {
				if (self.searchPanel.getForm().isValid()) {// 如果合法
					$search({
						searchPanel :self.searchPanel,
						gridPanel : self.gridPanel
					});
				}
			},
			/**
			 * 恢复记录
			 * 
			 * @param {}
			 *            record
			 */
			recoveryRecord : function() {
				var grid = Ext.getCmp('RecoveryJobGrid');

				var selectRecords = grid.getSelectionModel().getSelections();

				if (selectRecords.length == 0) {
					Ext.Msg.alert("信息", "请选择要恢复的职位记录！");
					return;
				}
				var ids = Array();
				for (var i = 0; i < selectRecords.length; i++) {
					ids.push(selectRecords[i].data.jobId);
				}
				Ext.Msg.confirm('信息确认', '您确认要恢复该职位记录吗？', function(btn) {
							if (btn == 'yes') {
								Ext.Ajax.request({
											url : __ctxPath
													+ '/hrm/recoveryJob.do',
											params : {
												ids : ids
											},
											method : 'POST',
											success : function(response,
													options) {
												Ext.ux.Toast.msg('操作信息',
														'成功恢复职位！');
												Ext.getCmp('JobGrid')
														.getStore().reload();
												Ext.getCmp('RecoveryJobGrid')
														.getStore().reload();
											},
											failure : function(response,
													options) {
												Ext.ux.Toast.msg('操作信息',
														'操作出错，请联系管理员！');
											}
										});
							}
						});// end of comfirm
			},
			/**
			 * 关闭窗口事件
			 * 
			 * @param {}
			 *            window
			 */
			cancel : function(window) {
				window.close();
			}
		});
