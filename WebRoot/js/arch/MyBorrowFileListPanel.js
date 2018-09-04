/**
 * @author:
 * @class MyBorrowFileListPanel
 * @extends Ext.Panel
 * @description [RollFile]管理
 * @company 北京互融时代软件有限公司
 * @createtime:
 */
MyBorrowFileListPanel = Ext.extend(Ext.Panel, {
	viewConfig : [],
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		// 初始化组件
		this.initUIComponents();
		// 调用父类构造
		MyBorrowFileListPanel.superclass.constructor.call(this, {
			region : 'center',
			layout : 'border',
			buttonAlign : 'center',
			items : [this.searchPanel, this.leftPanel, this.gridPanel],
			listeners : {
				'afterlayout' : function(Panel) {

					Panel.typePanel.on('click', function(node) {

								Panel.typePanel.clickNodes = true;
								var proTypeId = node.id;
								var typeName = node.text;

								if (proTypeId == '0') {

									Panel.SearchProTypeId.setValue('');

									Panel.comboxWithTree.setValue('');
									Panel.comboxWithTree.id = ''
									Panel.comboxWithTree.collapse();

								} else {

									Panel.SearchProTypeId.setValue(proTypeId);

									Panel.comboxWithTree.setValue(typeName);
									Panel.comboxWithTree.id = proTypeId;
									Panel.comboxWithTree.collapse();

								}

							});

					// 用于解决下拉存在二级菜单的时候，点击自动收起的功能
					Panel.typePanel.on('collapsenode', function(node) {
								Panel.comboxWithTree.expand();
							});
					Panel.typePanel.on('expandnode', function(node) {
								Panel.comboxWithTree.expand();
							});

					Panel.comboxWithTree.on('expand', function() {
								Panel.typePanel
										.render(Panel.borrowNum
												+ 'MyBorrowFileListPanelComBoxWithTreeDiv');
							});
				},
				'afterrender' : function(Panel) {
					Panel.leftPanel.findByType('treepanel')[0].on('click',
							function(node) {

								var rollNo = node.id;

								if (rollNo == '0') {

									Panel.rollNo.setValue('');
								} else {

									Panel.rollNo.setValue(rollNo);
								}

								Panel.search();
							});

				}
			}

		});
	},// end of constructor
	// 初始化组件
	initUIComponents : function() {

		this.afNo = new Ext.form.ComboBox({
					// fieldLabel : '全宗号',
					hidden : true,
					hiddenName : 'Q_archRoll.archFondId_L_EQ',
					flex : 1,
					// xtype : 'combo',
					mode : 'local',
					editable : true,
					lazyInit : false,
					forceSelection : false,
					triggerAction : 'all',
					store : new Ext.data.JsonStore({
								url : __ctxPath + "/arch/listArchFond.do",
								autoLoad : true,
								autoShow : true,
								// reader configs
								root : 'result',

								fields : ['archFondId', 'afNo']
							}),
					valueField : 'archFondId',
					displayField : 'afNo'
				});
		this.rollNo = new Ext.form.ComboBox({
					// fieldLabel : '案卷号',
					hidden : true,
					name : 'Q_archRoll.rollNo_S_EQ',
					allowBlank : true,
					flex : 1,
					mode : 'local',
					editable : true,
					lazyInit : false,
					forceSelection : false,
					triggerAction : 'all',
					store : new Ext.data.JsonStore({
								url : __ctxPath + "/arch/listArchRoll.do",
								autoLoad : false,
								autoShow : true,
								// reader configs
								root : 'result',
								idProperty : 'rollId',
								fields : ['rollNo', 'rollNo']
							}),
					valueField : 'rollNo',
					displayField : 'rollNo',
					listeners : {}
				});

		// 树形下拉框
		this.comboxWithTree = new Ext.form.ComboBox({
			store : new Ext.data.SimpleStore({
						fields : [],
						data : [[]]
					}),
			editable : false,
			mode : 'local',
			fieldLabel : '文件分类',
			allowBlank : true,
			triggerAction : 'all',
			maxHeight : 200,
			width : 100,
			selectOnFocus : false,
			tpl : "<tpl for='.'><div style='height:200px;'><div id='"
					+ this.borrowNum
					+ "MyBorrowFileListPanelComBoxWithTreeDiv'></div></div></tpl>",
			selectedClass : ''
		});

		this.typePanel = new Ext.tree.TreePanel({
					height : 200,
					width : 100,
					autoScroll : true,
					split : true,
					loader : new Ext.tree.TreeLoader({
								url : __ctxPath
										+ '/system/treeGlobalType.do?catKey=AR_RLF'
							}),
					root : new Ext.tree.AsyncTreeNode({
								expanded : true
							}),
					rootVisible : false
				}

		);

		this.SearchProTypeId = new Ext.form.Hidden({
					// fieldLabel : '分类Id',
					name : 'Q_globalType.proTypeId_L_EQ',
					flex : 1
				});

		// 初始化搜索条件Panel
		this.searchPanel = new HT.SearchPanel({
					method : 'POST',
					layout : 'form',
					region : 'north',
					colNums : 5,
					items : [

							// {
							// fieldLabel : '案卷ID',
							// name : 'Q_rollId_L_EQ',
							// flex : 1,
							// xtype : 'numberfield'
							// },

							this.comboxWithTree, {
								fieldLabel : '文件题名',
								name : 'Q_fileName_S_LK',
								flex : 1,
								xtype : 'textfield'
							}, {
								fieldLabel : '文件编号',
								name : 'Q_fileNo_S_LK',
								flex : 1,
								xtype : 'textfield'
							}

							// , {
							// fieldLabel : '责任者',
							// name : 'Q_dutyPerson_S_EQ',
							// flex : 1,
							// xtype : 'textfield'
							// }

							, {
								fieldLabel : '目录号',
								name : 'Q_catNo_S_LK',
								flex : 1,
								xtype : 'textfield'
							}
							// , {
							// fieldLabel : '顺序号',
							// name : 'Q_seqNo_N_EQ',
							// flex : 1,
							// xtype : 'numberfield'
							// }

							// , {
							// fieldLabel : '页号',
							// name : 'Q_pageNo_N_EQ',
							// flex : 1,
							// xtype : 'numberfield'
							// }, {
							// fieldLabel : '页数',
							// name : 'Q_pageNums_N_EQ',
							// flex : 1,
							// xtype : 'numberfield'
							// }

							, {
								fieldLabel : '密级',
								name : 'Q_secretLevel_S_LK',
								flex : 1,
								editable : true,
								lazyInit : false,
								forceSelection : false,
								xtype : 'diccombo',
								itemName : '密级'
							}, {
								fieldLabel : '保管期限',
								name : 'Q_timeLimit_S_LK',
								flex : 1,
								editable : true,
								lazyInit : false,
								forceSelection : false,
								xtype : 'diccombo',
								itemName : '保管期限'
							}, {
								fieldLabel : '开放形式',
								name : 'Q_openStyle_S_LK',
								flex : 1,
								editable : true,
								lazyInit : false,
								forceSelection : false,
								xtype : 'diccombo',
								itemName : '开放形式'
							}

							// , {
							// fieldLabel : '主题词',
							// name : 'Q_keywords_S_EQ',
							// flex : 1,
							// xtype : 'textfield'
							// }, {
							// fieldLabel : '附注',
							// name : 'Q_notes_S_EQ',
							// flex : 1,
							// xtype : 'textfield'
							// }, {
							// fieldLabel : '内容',
							// name : 'Q_content_S_EQ',
							// flex : 1,
							// xtype : 'textfield'
							// }, {
							// fieldLabel : '文件时间',
							// name : 'Q_fileTime_D_EQ',
							// flex : 1,
							// xtype : 'datefield',
							// format : 'Y-m-d'
							// }, {
							// fieldLabel : '录入人',
							// name : 'Q_creator_S_EQ',
							// flex : 1,
							// xtype : 'textfield'
							// }, {
							// fieldLabel : '录入时间',
							// name : 'Q_createtime_D_EQ',
							// flex : 1,
							// xtype : 'datefield',
							// format : 'Y-m-d'
							// }

							, {
								fieldLabel : '归档状态',
								hiddenName : 'Q_archStatus_SN_EQ',
								flex : 1,
								xtype : 'combo',
								mode : 'local',
								editable : false,
								triggerAction : 'all',
								store : [['', '全部'], ['0', '未归档'], ['1', '已归档']]
							},

							this.SearchProTypeId, this.afNo, this.rollNo

					],
					buttons : [{
								text : '查询',
								scope : this,
								iconCls : 'btn-search',
								handler : this.search
							}, {
								text : '重置',
								scope : this,
								iconCls : 'btn-reset',
								handler : this.reset
							}]
				});// end of searchPanel

		this.leftPanel = new Ext.Panel({
					region : 'west',
					layout : 'fit',
					collapsible : true,
					split : true,
					frame : false,
					title:'全宗/案卷',
					width : 200,
					border : true,
					items : [

					{
						xtype : 'treepanel',

						loader : new Ext.tree.TreeLoader({
									url : __ctxPath
											+ '/arch/listRollTreeArchFond.do'
								}),
						root : new Ext.tree.AsyncTreeNode({
									expanded : true
								}),
						rootVisible : false,
						split : true,
						border : false,
						frame : false,
						autoShow : true,
						autoScroll : true,
						scope : this

					}]
				}

		);

		this.leftPanel.findByType('treepanel')[0].getRootNode().expand();

		// 远程数据源
		this.store = new Ext.data.Store({
			proxy : new Ext.data.HttpProxy({
						url : __ctxPath + "/arch/listRollFile.do",
						method : 'POST'
					}),

			reader : new Ext.data.JsonReader({
						root : 'result',
						totalProperty : 'totalCounts',
						remoteSort : false,
						idProperty : "listId",
						fields : [{
									name : 'rollFileId',
									type : 'int'
								}, 'afNo', 'createTime', 'creatorName',
								'creatorId', 'archStatus', 'proTypeId',
								'typeName', 'openStyle', 'archRoll', 'rollNo',
								'fileName', 'fileNo', 'fileNo', 'catNo',
								'seqNo', 'pageNo', 'pageNums', 'secretLevel',
								'timeLimit', 'keyWords', 'keyWords', 'content',
								'fileTime', 'notes', 'dutyPerson', 'globalType']
					})

		});

		this.rowActions = new Ext.ux.grid.RowActions({
					header : '管理',
					width : 100,
					actions : [{
								iconCls : 'btn-readdocument',
								qtip : '预览附件',
								style : 'margin:0 3px 0 3px'
							}, {

								iconCls : 'btn-showDetail',
								qtip : '查看',
								style : 'margin:0 3px 0 3px'

							}],
					listeners : {
						scope : this,
						'action' : this.onRowAction
					}
				})

		this.gridPanel = new Ext.grid.GridPanel({
			region : 'center',
			store : this.store,
			// 使用RowActions
			plugins : this.rowActions,
			height : 200,
			defaults : {
				anchor : '96%,96%'
			},
			viewConfig : {
				forceFit : true,
				enableRowBody : false,
				showPreview : false
			},
			bbar : new HT.PagingBar({
						store : this.store
					}),
			columns : [{
						header : 'rollFileId',
						dataIndex : 'rollFileId',
						hidden : true
					}

					// , {
					// header : '分类ID',
					// dataIndex : 'proTypeId'
					// }

					// , {
					// header : '案卷ID',
					// dataIndex : 'archRoll',
					// renderer:function(archRoll){
					// return archRoll.rollId;
					// }
					// }

					, {
						header : '全宗号',
						dataIndex : 'archRoll',
						renderer : function(archRoll) {
							if (archRoll)
								return archRoll.afNo;
						}
					}, {
						header : '案卷号',
						dataIndex : 'archRoll',
						renderer : function(archRoll) {
							if (archRoll)
								return archRoll.rollNo;
						}
					}, {
						header : '所属分类',
						dataIndex : 'globalType',
						renderer : function(globalType) {
							if (globalType) {
								return globalType.typeName;
							}
						}
					}

					, {
						header : '文件题名',
						dataIndex : 'fileName'
					}, {
						header : '文件编号',
						dataIndex : 'fileNo'
					}

					// , {
					// header : '责任者',
					// dataIndex : 'dutyPerson'
					// }

					// , {
					// header : '目录号',
					// dataIndex : 'catNo'
					// }

					// , {
					// header : '顺序号',
					// dataIndex : 'seqNo'
					// }, {
					// header : '页号',
					// dataIndex : 'pageNo'
					// }, {
					// header : '页数',
					// dataIndex : 'pageNums'
					// }

					, {
						header : '密级',
						dataIndex : 'secretLevel'
					}, {
						header : '保管期限',
						dataIndex : 'timeLimit'
					}, {
						header : '开放形式',
						dataIndex : 'openStyle'
					}

					// , {
					// header : '主题词',
					// dataIndex : 'keyWords'
					// }, {
					// header : '附注',
					// dataIndex : 'notes'
					// }, {
					// header : '内容',
					// dataIndex : 'content'
					// }, {
					// header : '文件时间',
					// dataIndex : 'fileTime'
					// }, {
					// header : '录入人',
					// dataIndex : 'creatorName'
					// }, {
					// header : '录入时间',
					// dataIndex : 'createTime'
					// }

					, {
						header : '归档状态',
						dataIndex : 'archStatus',
						renderer : function(v) {
							switch (v) {
								case 0 :
									return '未归档';
									break;
								case 1 :
									return '已归档';
									break;
							}
						}
					}, this.rowActions]
				// end of columns
			});

		this.gridPanel.addListener('rowdblclick', this.rowClick);

	},// end of the initComponents()
	// 重置查询表单
	reset : function() {
		this.searchPanel.getForm().reset();
	},
	// 按条件搜索
	search : function() {
		$search({
					searchPanel : this.searchPanel,
					gridPanel : this.gridPanel
				});
	},
	// GridPanel行点击处理事件
	rowClick : function(grid, rowindex, e) {
		grid.getSelectionModel().each(function(rec) {

					new MyBorrowFileViewWindow({
								rollFileId : rec.data.rollFileId,
								archStatus : rec.data.archStatus
							}).show();
				});
	},

	viewFile : function(record) {
		Ext.Ajax.request({
					url : __ctxPath + '/arch/listRollFileList.do',
					method : 'POST',
					// async : false,
					success : function(response, opts) {

						var obj = Ext.decode(response.responseText);
						var viewConfig = [];
						for (i = 0; i < obj.result.length; i++) {
							viewConfig.push({
										fileName : obj.result[i].fileAttach.fileName,
										filePath : obj.result[i].fileAttach.filePath
									});
						}

						new ViewFileWindow({
									viewConfig : viewConfig,
									startIndex : 0
								}).show();
					},

					failure : function(response, opts) {

					},
					params : {

						'Q_rollFile.rollFileId_L_EQ' : record.data.rollFileId,
						dir : 'ASC',
						sort : 'sn'
					}
				});

	},
	showFile : function(record) {

		new MyBorrowFileViewWindow({
					rollFileId : record.data.rollFileId,
					archStatus : record.data.archStatus
				}).show();
	},
	// 行的Action
	onRowAction : function(grid, record, action, row, col) {
		switch (action) {
			case 'btn-readdocument' :
				this.viewFile.call(this, record);
				break;
			case 'btn-showDetail' :
				this.showFile(record);
				break;
			default :
				break;
		}
	}
});
