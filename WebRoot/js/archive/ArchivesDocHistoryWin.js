/**
 * @author:
 * @class ArchivesDocHistoryWin
 * @extends Ext.Panel
 * @description [DocHistory]管理
 * @company 北京互融时代软件有限公司
 * @createtime:2010-01-16
 */
ArchivesDocHistoryWin = Ext.extend(Ext.Window, {
			// 条件搜索Panel
			searchPanel : null,
			// 数据展示Panel
			gridPanel : null,
			// GridPanel的数据Store
			store : null,
			// 头部工具栏
			buttons : null,
			// 构造函数
			constructor : function(_cfg) {
				Ext.applyIf(this, _cfg);
				// 初始化组件
				this.initUIComponents();
				// 调用父类构造
				ArchivesDocHistoryWin.superclass.constructor.call(this, {
							id : 'ArchivesDocHistoryWin',
							iconCls : 'menu-archive-history',
							title : '版本管理',
							layout : 'border',
							modal : true,
							height : 400,
							border :false,
							width : 600,
							bottons : this.bottons,
							buttonAlign : 'center',
							items : [this.gridPanel]
						});
			},// end of constructor

			// 初始化组件
			initUIComponents : function() {
				
				// 加载数据至store
				this.store = new Ext.data.JsonStore({
							url : __ctxPath + "/archive/listDocHistory.do",
							root : 'result',
							baseParams : {
								'Q_archivesDoc.docId_L_EQ' : this.docId
							},
							totalProperty : 'totalCounts',
							remoteSort : true,
							fields : [{
										name : 'historyId',
										type : 'int'
									}, 'archivesDoc', 'fileAttach', 'docName', 'path',
									'version','updatetime', 'mender']
						});
				this.store.setDefaultSort('historyId', 'desc');
				// 加载数据
				this.store.load({
							params : {
								start : 0,
								limit : 25
							}
						});

				// 初始化ColumnModel
				var sm = new Ext.grid.CheckboxSelectionModel({
					singleSelect : true
				});
				var cm = new Ext.grid.ColumnModel({
							columns : [sm, new Ext.grid.RowNumberer(), {
										header : 'historyId',
										dataIndex : 'historyId',
										hidden : true
									}, {
										header : '所属附件',
										dataIndex : 'fileAttach',
										renderer : function(value){
											return value.fileName;
										}
									}, {
										header : '文档名称',
										dataIndex : 'docName'
									}, {
										header : '版本',
										dataIndex : 'version'
									},{
										header : '路径',
										dataIndex : 'path'
									}, {
										header : '更新时间',
										dataIndex : 'updatetime'
									}, {
										header : '修改人',
										dataIndex : 'mender'
									}],
							defaults : {
								sortable : true,
								menuDisabled : false,
								width : 100
							}
						});
				// 初始化工具栏
				

				this.gridPanel = new Ext.grid.GridPanel({
							id : 'DocHistoryGrid',
							region : 'center',
							stripeRows : true,
							//tbar : this.topbar,
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
								// showPreview : false
							},
							bbar : new Ext.PagingToolbar({
										pageSize : 25,
										store : this.store,
										displayInfo : true,
										displayMsg : '当前页记录索引{0}-{1}， 共{2}条记录',
										emptyMsg : "当前没有记录"
									})
						});

				this.gridPanel.addListener('rowdblclick', function(grid,
								rowindex, e) {
							grid.getSelectionModel().each(function(rec) {
										new DocHistoryForm(rec.data.historyId)
												.show();
									});
						});
				this.buttons =  [{
									iconCls : 'btn-archive-history',
									text : '直接覆盖',
									xtype : 'button',
									handler : this.copy.createCallback(this.gridPanel,this),
									scope : this
								},{
									iconCls : 'btn-archive-copy',
									text : '修改覆盖',
									xtype : 'button',
									handler :this.modify.createCallback(this.gridPanel,this),
									scope : this
								},{
									iconCls : 'close',
									text  : '关闭',
									xtype : 'button',
									handler : this.colseWIn.createCallback(this),
									scope : this
								}];
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
			 * 添加记录
			 */
			createRecord : function() {
				new DocHistoryForm().show();
			},
			
			/**
			 *  把旧版本覆盖为新版本
			 */
			copy : function(grid,win) {
				var row = grid.getSelectionModel().getSelections();
				var callback = win.callback;
				Ext.Ajax.request({
					url: __ctxPath + '/archive/copyArchivesDoc.do',
					params : {
						historyId : row[0].data.historyId
					},
					scope : this,
					method : 'post',
					success : function(response){
						var result = Ext.util.JSON.decode(response.responseText)
						callback.call(this,result.data);
						win.close();
						Ext.ux.Toast.msg('操作信息','success');
					},
					failure : function(){
						Ext.ux.Toast.msg('操作信息','操作出错,请联系管理员');
					}
				});
				
			},
			/**
			 * 关闭窗口
			 */
			colseWIn : function(win){
				win.close();
			},
			/**
			 * 查看旧版本
			 */
			modify : function(grid,win){
				var selectRecords = grid.getSelectionModel().getSelections();

				if (selectRecords.length == 0) {
					Ext.Msg.alert("信息", "请选择要查看的文档！");
					return;
				}
				var record = selectRecords[0];
				var path = record.data.path;
				var docId = record.data.archivesDoc.docId;
				var store = Ext.getCmp('archiveDocGrid').getStore();
				var fileId = record.data.fileAttach.fileId;
				var curView = Ext.getCmp('ArchivesDraftWin');
				// 返回文档附加记录
				var callback = function(archivesDoc) {
					store.reload();
					win.close();
				};
				new ArchivesDocForm({
							docId : docId,
							docPath : path,
							fileId : fileId,
							callback : callback
						}).show();
			}
		});
