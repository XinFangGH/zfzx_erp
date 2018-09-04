/**
 * @author
 * @createtime
 * @class BorrowFileListView
 * @extends Ext.Window
 * @description RollFile表单
 * @company 智维软件
 */

Ext.ns('BorrowFileListView');

BorrowFileListView = Ext.extend(Ext.Panel, {
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		// 必须先初始化组件
		this.initUIComponents();
		BorrowFileListView.superclass.constructor.call(this, {
					layout : 'fit',
					border : true,
					items : [this.gridPanel],
					// title : '',
					frame : false,
					listeners : {
						afterlayout : function(Panel) {

							Panel.store.on('load', function(mystore,
											selectRecords) {
										// 将远程数据转化为本地数据
										// for (i = 0; i < r.length; i++) {
										// Panel.pageingStore.add(r[i]);
										// }
										// Panel.pageingStore.commitChanges();
										// Panel.gridPanel
										// .doLayout(true, true);
										// Panel.gridPanel.getView().refresh();

										var upRecords = [];
										for (i = 0; i < selectRecords.length; i++) {
											upRecords
													.push(selectRecords[i].data);

										}

										Panel.pageingStore.loadData({
													'success' : true,
													'totalProperty' : upRecords.length,
													'result' : upRecords
												});
										Panel.gridPanel.getBottomToolbar()
												.moveFirst();

									});

						}

					}
				});
	},// end of the constructor
	// 初始化组件
	initUIComponents : function() {

		var comonFields = [{
					name : 'listId',
					type : 'int'
				}, 'recordId', 'listType', 'archFond', 'afNo', 'afName',
				'archRoll', 'rollNo', 'rolllName', 'rollFile', 'fileNo',
				'fileName'];
		// 用于分于的本地数据源
		this.pageingStore = new Ext.ux.data.JsonPagingStore({
					root : 'result',
					totalProperty : 'totalCounts',
					remoteSort : false,
					idProperty : "listId",
					fields : comonFields
				});
		// 远程数据源
		this.store = new Ext.data.Store({
					proxy : new Ext.data.HttpProxy({
								url : __ctxPath
										+ "/arch/listBorrowFileList.do?sno="
										+ new Date(),
								method : 'GET'
							}),
					root : 'result',
					reader : new Ext.data.JsonReader({
								root : 'result',
								fields : comonFields
							})

				});

		var sm = new Ext.grid.CheckboxSelectionModel();
		var cm = new Ext.grid.ColumnModel({
					columns : [sm, new Ext.grid.RowNumberer(), {
								header : 'listId',
								dataIndex : 'listId',
								hidden : true
							}, {
								header : '借阅单位',
								dataIndex : 'listType'
							}, {
								header : '全宗ID',
								dataIndex : 'archFond',
								hidden : true,
								renderer : function(archFond) {
									if (archFond)
										return archFond.archFondId;
								}
							}, {
								header : '全宗号',
								dataIndex : 'archFond',
								renderer : function(archFond) {

									if (archFond)
										return archFond.afNo;
								}

							}, {
								header : '全宗名',
								dataIndex : 'archFond',
								renderer : function(archFond) {
									if (archFond)
										return archFond.afName;
								}
							}, {
								header : '案卷ID',
								hidden : true,
								dataIndex : 'archRoll',
								renderer : function(archRoll) {
									if (archRoll)
										return archRoll.rollId;
								}
							}, {
								header : '案卷号',
								dataIndex : 'archRoll',
								renderer : function(archRoll) {
									if (archRoll)
										return archRoll.rollNo;
								}
							}, {
								header : '案卷名',
								dataIndex : 'archRoll',
								renderer : function(archRoll) {
									if (archRoll)
										return archRoll.rolllName;
								}
							}, {
								header : '文件ID',
								hidden : true,
								dataIndex : 'rollFile',
								renderer : function(rollFile) {
									if (rollFile)
										return rollFile.rollFileId;
								}
							}, {
								header : '文件号',
								dataIndex : 'fileNo'
							}, {
								header : '文件题名',
								dataIndex : 'fileName'
							}

					],
					defaults : {
						sortable : true,
						menuDisabled : false
					}
				});

		this.topbar = new Ext.Toolbar({
					items : [{
								xtype : 'tbtext',
								text : '借阅清单'
							}, {
								xtype : 'tbseparator'
							}, {
								iconCls : 'btn-add',
								text : '全宗',
								hidden : (this.returnStatus == 1)
										? true
										: false,
								xtype : 'button',
								scope : this,
								handler : this.createFond
							}, {
								iconCls : 'btn-add',
								text : '案卷',
								hidden : (this.returnStatus == 1)
										? true
										: false,
								xtype : 'button',
								scope : this,
								handler : this.createRoll
							}, {
								iconCls : 'btn-add',
								text : '文件',
								hidden : (this.returnStatus == 1)
										? true
										: false,
								xtype : 'button',
								scope : this,
								handler : this.createFile
							}, {
								iconCls : 'btn-del',
								text : '删除',
								hidden : (this.returnStatus == 1)
										? true
										: false,
								xtype : 'button',
								scope : this,
								handler : this.removeSelRs
							}

					]

				});

		this.gridPanel = new Ext.grid.GridPanel({
					frame : false,
					border : false,
					tbar : this.topbar,
					store : this.pageingStore,
					cm : cm,
					sm : sm,
					// autoExpandColumn : 'fileName',
					viewConfig : {
						forceFit : true,
						autoFill : true
					},
					bbar : new HT.PagingBar({
								store : this.pageingStore
							}),
					listeners : {}

				});

	},
	// 选择全宗
	createFond : function() {
		var pageingStore = this.pageingStore;
		var gridPanel = this.gridPanel;
		var removeByIds=this.removeByIds;
		new SelectFondWindow({
			callBack : function(rs) {
				// 判断重覆
				if (pageingStore.allData) {

					var ids = Array();
					for (i = 0; i < rs.length; i++) {

						// 对比案卷（重覆）
						for (j = 0; j < pageingStore.allData.items.length; j++) {
							if (pageingStore.allData.items[j].data.archRoll
									&& pageingStore.allData.items[j].data.archFond
									&& rs[i].data.archFondId == pageingStore.allData.items[j].data.archFond.archFondId
									&& pageingStore.allData.items[j].data.listType == '案卷') {
								if (pageingStore.allData.items[j].data.listId != ''
										&& pageingStore.allData.items[j].data.listId != null) {
									ids
											.push(pageingStore.allData.items[j].data.listId);
								}
								pageingStore.removeAt(j);
								pageingStore.commitChanges();
								j--;
								Ext.ux.Toast.msg('操作信息', '所选案卷重覆！');
							}
						}

						// 对比文件（重覆）
						for (j = 0; j < pageingStore.allData.items.length; j++) {
							if (pageingStore.allData.items[j].data.rollFile
									&& pageingStore.allData.items[j].data.archFond
									&& rs[i].data.archFondId == pageingStore.allData.items[j].data.archFond.archFondId
									&& pageingStore.allData.items[j].data.listType == '文件') {
								if (pageingStore.allData.items[j].data.listId != ''
										&& pageingStore.allData.items[j].data.listId != null) {
									ids
											.push(pageingStore.allData.items[j].data.listId);
								}
								pageingStore.removeAt(j);
								pageingStore.commitChanges();
								j--;
								Ext.ux.Toast.msg('操作信息', '所选文件重覆！');

							}
						}

						// 对比全宗（自已)重覆
						for (j = 0; j < pageingStore.allData.items.length; j++) {
							if (pageingStore.allData.items[j].data.archFond
									&& rs[i].data.archFondId == pageingStore.allData.items[j].data.archFond.archFondId
									&& pageingStore.allData.items[j].data.listType == '全宗') {
								rs.splice(i, 1);
								i--;
								Ext.ux.Toast.msg('操作信息', '所选全宗重覆！');
								break;
							}
						}

					}
					pageingStore.commitChanges();
					if (ids.length > 0) {
						removeByIds(ids);
					}
				}

				// 插入
				for (i = 0; i < rs.length; i++) {

					var recrod = new pageingStore.recordType();
					recrod.data = {};
					recrod.data.listType = '全宗';
					var archFond = {};
					Ext.apply(archFond, {
								archFondId : rs[i].data.archFondId,
								afNo : rs[i].data.afNo,
								afName : rs[i].data.afName

							});
					Ext.apply(recrod.data, {
								archFond : archFond
							});

					recrod.data.afNo = rs[i].data.afNo;
					recrod.data.afName = rs[i].data.afName;

					pageingStore.add(recrod);

				}

				pageingStore.commitChanges();
				gridPanel.getBottomToolbar().moveFirst();
				gridPanel.getView().refresh();
				gridPanel.doLayout(true, true);
			}
		}).show();

	},
	// 选择案卷
	createRoll : function() {
		var pageingStore = this.pageingStore;
		var gridPanel = this.gridPanel;
		var removeByIds=this.removeByIds;
		new SelectRollWindow({
			callBack : function(rs) {
				// 判断重覆
				if (pageingStore.allData) {
					var ids = Array();
					for (i = 0; i < rs.length; i++) {

						// 对比文件
						for (j = 0; j < pageingStore.allData.items.length; j++) {

							if (pageingStore.allData.items[j].data.rollFile
									&& pageingStore.allData.items[j].data.archRoll
									&& rs[i].data.rollId == pageingStore.allData.items[j].data.archRoll.rollId
									&& pageingStore.allData.items[j].data.listType == '文件') {
								if (pageingStore.allData.items[j].data.listId != ''
										&& pageingStore.allData.items[j].data.listId != null) {
									ids
											.push(pageingStore.allData.items[j].data.listId);
								}
								
								pageingStore.removeAt(j);
								pageingStore.commitChanges();
								j--;
								Ext.ux.Toast.msg('操作信息', '所选文件重覆！');

							}
						}

						// 对比案卷（自已）
						for (j = 0; j < pageingStore.allData.items.length; j++) {
							if (pageingStore.allData.items[j].data.archRoll
									&& rs[i].data.rollId == pageingStore.allData.items[j].data.archRoll.rollId
									&& pageingStore.allData.items[j].data.listType == '案卷') {

								rs.splice(i, 1);
								i--;
								Ext.ux.Toast.msg('操作信息', '所选案卷重覆！');
								break;
							}
						}

						// 对比全宗

						for (j = 0; j < pageingStore.allData.items.length; j++) {
							if (pageingStore.allData.items[j].data.archFond
									&& rs[i].data.archFond.archFondId == pageingStore.allData.items[j].data.archFond.archFondId
									&& pageingStore.allData.items[j].data.listType == '全宗') {

								rs.splice(i, 1);
								i--;
								Ext.ux.Toast.msg('操作信息', '所选案卷重覆！');
								break;
							}
						}

					}
					pageingStore.commitChanges();
					if (ids.length > 0) {
						removeByIds(ids);
					}
				}

				// 插入
				for (i = 0; i < rs.length; i++) {

					var recrod = new pageingStore.recordType();
					recrod.data = {};
					recrod.data.listType = '案卷';

					var archFond = {};
					Ext.apply(archFond, {
								archFondId : rs[i].data.archFond.archFondId,
								afNo : rs[i].data.archFond.afNo,
								afName : rs[i].data.archFond.afName
							});
					Ext.apply(recrod.data, {
								archFond : archFond
							});
					recrod.data.afNo = rs[i].data.archFond.afNo;
					recrod.data.afName = rs[i].data.archFond.afName;

					var archRoll = {};
					Ext.apply(archRoll, {
								rollId : rs[i].data.rollId,
								rollNo : rs[i].data.rollNo,
								rolllName : rs[i].data.rollNo
							});

					Ext.apply(recrod.data, {
								archRoll : archRoll
							});
					recrod.data.rollNo = rs[i].data.rollNo;
					recrod.data.rolllName = rs[i].data.rollNo;
					pageingStore.add(recrod);

				}

				pageingStore.commitChanges();
				gridPanel.getBottomToolbar().moveFirst();
				gridPanel.getView().refresh();
				gridPanel.doLayout(true, true);
			}
		}).show();

	},
	// 选择文件
	createFile : function() {
		var pageingStore = this.pageingStore;
		var gridPanel = this.gridPanel
		new SelectFileWindow({
			callBack : function(rs) {
				// 判断重覆
				if (pageingStore.allData) {
					for (i = 0; i < rs.length; i++) {
						// 对比文件（自已）
						for (j = 0; j < pageingStore.allData.items.length; j++) {

							if (pageingStore.allData.items[j].data.rollFile
									&& rs[i].data.rollFileId == pageingStore.allData.items[j].data.rollFile.rollFileId
									&& pageingStore.allData.items[j].data.listType == '文件') {
								rs.splice(i, 1);
								i--;
								Ext.ux.Toast.msg('操作信息', '所选文件重覆！');
								break;
							}
						}
						// 对比全宗
						for (j = 0; j < pageingStore.allData.items.length; j++) {
							if (pageingStore.allData.items[j].data.archFond
									&& rs[i].data.archRoll.archFond.archFondId == pageingStore.allData.items[j].data.archFond.archFondId
									&& pageingStore.allData.items[j].data.listType == '全宗') {

								rs.splice(i, 1);
								i--;
								Ext.ux.Toast.msg('操作信息', '所选案卷重覆！');
								break;
							}
						}
						// 对比案卷
						for (j = 0; j < pageingStore.allData.items.length; j++) {
							if (pageingStore.allData.items[j].data.archRoll
									&& rs[i].data.archRoll.rollId == pageingStore.allData.items[j].data.archRoll.rollId
									&& pageingStore.allData.items[j].data.listType == '案卷') {

								rs.splice(i, 1);
								i--;
								Ext.ux.Toast.msg('操作信息', '所选案卷重覆！');
								break;
							}
						}

					}
					pageingStore.commitChanges();
				}

				// 插入
				for (i = 0; i < rs.length; i++) {

					var recrod = new pageingStore.recordType();
					recrod.data = {};
					recrod.data.listType = '文件';

					if (rs[i].data.archRoll) {

						var archRoll = {};
						Ext.apply(archRoll, {
									rollId : rs[i].data.archRoll.rollId,
									rollNo : rs[i].data.archRoll.rollNo,
									rolllName : rs[i].data.archRoll.rolllName
								});

						Ext.apply(recrod.data, {
									archRoll : archRoll
								});
						recrod.data.rollNo = rs[i].data.archRoll.rollNo;
						recrod.data.rolllName = rs[i].data.archRoll.rolllName;

						if (rs[i].data.archRoll.archFond) {
							var archFond = {};
							Ext.apply(archFond, {
								archFondId : rs[i].data.archRoll.archFond.archFondId,
								afNo : rs[i].data.archRoll.archFond.afNo,
								afName : rs[i].data.archRoll.archFond.afName
							});
							Ext.apply(recrod.data, {
										archFond : archFond
									});
							recrod.data.afNo = rs[i].data.archRoll.archFond.afNo;
							recrod.data.afName = rs[i].data.archRoll.archFond.afName;
						}
					}

					var rollFile = {};
					Ext.apply(rollFile, {
								rollFileId : rs[i].data.rollFileId
							});

					Ext.apply(recrod.data, {
								rollFile : rollFile
							});
					recrod.data.fileNo = rs[i].data.fileNo;
					recrod.data.fileName = rs[i].data.fileName;

					pageingStore.add(recrod);

				}

				pageingStore.commitChanges();
				gridPanel.getBottomToolbar().moveFirst();
				gridPanel.getView().refresh();
				gridPanel.doLayout(true, true);
			}
		}).show();

	},

	// 按ID删除记录
	removeByIds : function(ids) {

		Ext.Ajax.request({
					url : __ctxPath + '/arch/multiDelBorrowFileList.do',
					params : {
						ids : ids
					},
					method : 'POST',
					success : function(response, options) {
						Ext.ux.Toast.msg('操作信息', '成功删除该明细！');

					},
					failure : function(response, options) {
						Ext.ux.Toast.msg('操作信息', '操作出错，请联系管理员！');
					}
				});

	},
	// 把选中ID删除
	removeSelRs : function() {
		Ext.Msg.confirm('信息确认', '您确认要删除所选记录吗？', function(btn) {
					if (btn == 'yes') {
						var gridPanel = this.gridPanel;// Ext.getCmp('BorrowFileListView');
						var pageingStore = gridPanel.getStore();
						var selectRecords = gridPanel.getSelectionModel()
								.getSelections();
						if (selectRecords.length == 0) {
							Ext.ux.Toast.msg("信息", "请选择要删除的记录！");
							return;
						}

						var ids = Array();
						for (var i = 0; i < selectRecords.length; i++) {
							if (selectRecords[i].data.listId != ''
									&& selectRecords[i].data.listId != null) {
								ids.push(selectRecords[i].data.listId);
								pageingStore.remove(selectRecords[i]);
							} else {
								pageingStore.remove(selectRecords[i]);
							}
						}
						if (ids.length > 0) {
							this.removeByIds(ids);
						}
						gridPanel.getBottomToolbar().moveFirst();
					}
				}, this);// end of comfirm

	}

});