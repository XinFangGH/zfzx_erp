/**
 * @author:
 * @class TidyFileView
 * @extends Ext.Panel
 * @description [RollFile]管理
 * @company 北京互融时代软件有限公司
 * @createtime:
 */
TidyFileView = Ext.extend(Ext.Panel, {
	// viewConfig:[],
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		// 初始化组件
		this.initUIComponents();
		// 调用父类构造
		TidyFileView.superclass.constructor.call(this, {
					id : 'TidyFileView',
					title : '归档文件管理',
					iconCls : "menu-tidyFile",
					region : 'center',
					layout : 'border',

					buttonAlign : 'center',
					items : [this.searchPanel, this.centerPanel, this.eastPanel]
					,
					listeners : {
						afterrender : function(View) {
							var vW=View.getInnerWidth();
							View.eastPanel.setWidth(vW/2);
						
						}

					}

				});
	},// end of constructor
	// 初始化组件
	initUIComponents : function() {
		// 初始化搜索条件Panel
		this.TidyFileViewSearchButton = new Ext.Button({
					text : '查询',
					id : 'TidyFileViewSearchButton',
					scope : this,
					iconCls : 'btn-search',
					handler : this.search
				});
		this.searchPanel = new HT.SearchPanel({
			listeners : {
				'afterlayout' : function(TidyFileView) {
					Ext.getCmp('TidyFileView').search();
				}
			},
			hidden : true,// |又隐藏
			forceLayout : true,// |又渲染
			id : 'TidyFileSearchPanel',
			layout : 'form',
			region : 'north',
			colNums : 4,
			items : [

					// {
					// fieldLabel : '案卷ID',
					// name : 'Q_rollId_L_EQ',
					// flex : 1,
					// xtype : 'numberfield'
					// },

					{
				fieldLabel : '全宗号',
				id : 'TidyFileView.Q_afNo_S_EQ',
				hiddenName : 'Q_archRoll.archFondId_L_EQ',
				flex : 1,
				xtype : 'combo',
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
				displayField : 'afNo',
				listeners : {
					scope : this,
					'select' : function(combo, record, index) {
						Ext.getCmp('TidyFileView.Q_rollNo_S_EQ').getStore()
								.load({
									params : {

										'Q_archFond.archFondId_L_EQ' : record.data.archFondId
									}
								});
						Ext.getCmp('TidyFileView.Q_rollNo_S_EQ').reset();
					}
				}
			}, {
				fieldLabel : '案卷号',
				id : 'TidyFileView.Q_rollNo_S_EQ',
				name : 'Q_archRoll.rollNo_S_EQ',
				flex : 1,
				xtype : 'combo',
				mode : 'local',
				editable : true,
				lazyInit : false,
				forceSelection : false,
				triggerAction : 'all',
				store : new Ext.data.JsonStore({
							url : __ctxPath + "/arch/listArchRoll.do",
							autoLoad : true,
							autoShow : true,
							// reader configs
							root : 'result',
							idProperty : 'rollId',
							fields : ['rollId', 'rollNo', 'afNo']
						}),
				valueField : 'rollId',
				displayField : 'rollNo',
				listeners : {}
			}, {
				fieldLabel : '文件题名',
				name : 'Q_fileName_S_EQ',
				flex : 1,
				xtype : 'textfield'
			}
					// , {
					// fieldLabel : '文件编号',
					// name : 'Q_fileNo_S_EQ',
					// flex : 1,
					// xtype : 'textfield'
					// }

					// , {
					// fieldLabel : '责任者',
					// name : 'Q_dutyPerson_S_EQ',
					// flex : 1,
					// xtype : 'textfield'
					// }

					, {
						fieldLabel : '目录号',
						name : 'Q_catNo_S_EQ',
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
						id : 'TidyFileView.Q_archStatus_SN_EQ',
						hiddenName : 'Q_archStatus_SN_EQ',
						flex : 1,
						xtype : 'combo',
						value : '0',
						mode : 'local',
						editable : false,
						triggerAction : 'all',
						store : [['', '全部'], ['0', '未归档'], ['1', '已归档']]
					},

					{
						fieldLabel : '分类Id',
						id : 'TidyFileView.proTypeId',
						name : 'Q_globalType.proTypeId_L_EQ',
						flex : 1,
						xtype : 'hidden'
					}

			// , {
			// fieldLabel : '分类Name',
			// id : 'TidyFileView.typeName',
			// name : 'Q_typeName_S_EQ',
			// flex : 1,
			// xtype : 'hidden'
			// }

			],
			buttons : [this.TidyFileViewSearchButton, {
						text : '重置',
						scope : this,
						iconCls : 'btn-reset',
						handler : this.reset
					}]
		});// end of searchPanel
		// 树形下拉框
		var comboxWithTree = new Ext.form.ComboBox({
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
			tpl : "<tpl for='.'><div style='height:200px;'><div id='TidyFileViewComBoxWithTreeDiv'></div></div></tpl>",
			selectedClass : ''
		});

		var leftPanel = new Ext.tree.TreePanel({
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

		// 用于解决下拉存在二级菜单的时候，点击自动收起的功能
		leftPanel.on('collapsenode', function(node) {
					comboxWithTree.expand();
				});
		leftPanel.on('expandnode', function(node) {
					comboxWithTree.expand();
				});

		leftPanel.on('click', function(node) {

					leftPanel.clickNodes = true;
					var proTypeId = node.id;
					var typeName = node.text;

					if (proTypeId == '0') {
						Ext.getCmp('TidyFileSearchPanel').getForm()
								.findField('TidyFileView.proTypeId')
								.setValue('');
						// Ext.getCmp('TidyFileSearchPanel').getForm()
						// .findField('TidyFileView.typeName')
						// .setValue('');

						comboxWithTree.setValue('');
						comboxWithTree.id = ''
						comboxWithTree.collapse();

						Ext.getCmp('TidyFileFormWin').store.clearFilter();
					} else {

						Ext.getCmp('TidyFileSearchPanel').getForm()
								.findField('TidyFileView.proTypeId')
								.setValue(proTypeId);
						// Ext.getCmp('TidyFileSearchPanel').getForm()
						// .findField('TidyFileView.typeName')
						// .setValue(typeName);

						comboxWithTree.setValue(typeName);
						comboxWithTree.id = proTypeId;
						comboxWithTree.collapse();
						Ext.getCmp('TidyFileFormWin').store.filter('proTypeId',
								proTypeId, true, false);

					}

					Ext.getCmp('TidyFileView').search();

				});

		comboxWithTree.on('expand', function() {
					leftPanel.render('TidyFileViewComBoxWithTreeDiv');
				});

		var afNo = new Ext.form.ComboBox({
					mode : 'local',
					lazyInit : false,
					forceSelection : true,
					editable : false,
					triggerAction : 'all',
					width : 50,
					store : new Ext.data.JsonStore({
								url : __ctxPath + "/arch/listArchFond.do",
								autoLoad : true,
								autoShow : true,
								// reader configs
								root : 'result',
								fields : ['archFondId', 'afNo'],
								listeners : {
									'load' : function(store, records, options) {
									}
								}
							}),
					valueField : 'archFondId',
					displayField : 'afNo'

				})
		afNo.on('select', function(combo, record, index) {
			Ext.getCmp('TidyFileSearchPanel').getForm()
					.findField('TidyFileView.Q_afNo_S_EQ').setValue(record
							.get('archFondId'));

			Ext.getCmp('TidyFileView.Search.rollNo.ComboBox').getStore().load({
						params : {

							'Q_archFond.archFondId_L_EQ' : record.data.archFondId
						}
					});
			Ext.getCmp('TidyFileView.Search.rollNo.ComboBox').reset();
			Ext.getCmp('TidyFileSearchPanel').getForm()
					.findField('TidyFileView.Q_rollNo_S_EQ').reset();

		});
		var rollNo = new Ext.form.ComboBox({
					id : 'TidyFileView.Search.rollNo.ComboBox',
					width : 50,
					mode : 'local',
					lazyInit : false,
					forceSelection : true,
					editable : false,
					triggerAction : 'all',
					store : new Ext.data.JsonStore({
								url : __ctxPath + "/arch/listArchRoll.do",
								autoLoad : true,
								autoShow : true,
								// reader configs
								root : 'result',
								idProperty : 'rollId',
								fields : ['rollId', 'rollNo', 'afNo']
							}),
					valueField : 'rollId',
					displayField : 'rollNo',
					listeners : {
						// TidyFileView.Q_rollNo_S_EQ
						'select' : function(combo, record, index) {
							Ext.getCmp('TidyFileSearchPanel').getForm()
									.findField('TidyFileView.Q_rollNo_S_EQ')
									.setValue(record.get('rollId'));

						}
					}
				});

		this.topbar = new Ext.Toolbar({
			items : [{
						xtype : 'tbtext',
						text : '分类'
					}, comboxWithTree, {
						xtype : 'tbtext',
						text : '全宗号'
					}, afNo, {
						xtype : 'tbtext',
						text : '案卷号'
					}, rollNo, {
						text : '查询',
						scope : this,
						iconCls : 'btn-search',
						handler : this.search
					}, {

						text : '高级查询>>',
						scope : this,
						// iconCls : 'btn-search',
						handler : function() {
							if (Ext.getCmp('TidyFileSearchPanel').isVisible()) {

								Ext.getCmp('TidyFileSearchPanel')
										.setVisible(false);
								Ext.getCmp('TidyFileView').doLayout(true, true);

							} else {
								Ext.getCmp('TidyFileSearchPanel')
										.setVisible(true);
								Ext.getCmp('TidyFileView').doLayout(true, true);
							}
						}

					}

			// {
			// xtype : 'tbtext',
			// text : ''
			// }

			// {
			//
			// iconCls : 'btn-beready-save',
			// text : '归档',
			// xtype : 'button',
			// scope : this,
			// handler : this.tidyRs
			//
			// }
			// ,{
			// iconCls : 'btn-add',
			// text : '添加',
			// xtype : 'button',
			// scope : this,
			// handler : this.createRs
			// }
			// , {
			// iconCls : 'btn-del',
			// text : '删除',
			// xtype : 'button',
			// scope : this,
			// handler : this.removeSelRs
			// }
			]
		});

		this.fileRecord = Ext.data.Record.create([{
					name : 'rollFileId',
					type : 'int'
				}, 'afNo', 'createTime', 'creatorName', 'creatorId',
				'archStatus', 'proTypeId', 'typeName', 'openStyle', 'rollNo',
				'fileName', 'fileNo', 'fileNo', 'catNo', 'seqNo', 'pageNo',
				'pageNums', 'secretLevel', 'timeLimit', 'keyWords', 'keyWords',
				'content', 'fileTime', 'notes', 'dutyPerson', 'archRoll',
				'globalType']);

		this.memoryProxy = new Ext.data.HttpProxy({
					url : __ctxPath + "/arch/listRollFile.do"
				});
		this.jsonReader = new Ext.data.JsonReader({
					root : 'result',
					totalProperty : 'totalCounts',
					idProperty : "rollFileId"
				}, this.fileRecord);
		this.mystore = new Ext.data.Store({
					proxy : this.memoryProxy,
					reader : this.jsonReader
				});

		this.rowAtction = new Ext.ux.grid.RowActions({
					header : '管理',
					width : 55,
					actions : [{
								iconCls : 'btn-readdocument',
								qtip : '预览附件',
								style : 'margin:0 3px 0 3px'
							}

							, {

								iconCls : 'btn-showDetail',
								qtip : '查看',
								style : 'margin:0 3px 0 3px'

							}

					],
					listeners : {
						scope : this,
						'action' : this.onRowAction
					}
				});
		var sm = new Ext.grid.CheckboxSelectionModel();
		this.filesGrid = new Ext.grid.GridPanel({
			frame : false,
			border : false,
			region : 'center',
			tbar : this.topbar,
			// 使用RowActions
			rowActions : true,
			id : 'TidyFileGrid',
			//height : 400,
			store : this.mystore,
			bbar : new HT.PagingBar({
						store : this.mystore
					}),
			sm : sm,
			plugins : this.rowAtction,
			viewConfig : {
				forceFit : true,
				autoFill : true
			},
			columns : [sm,
					// new Ext.grid.RowNumberer(),
					{
				header : 'rollFileId',
				dataIndex : 'rollFileId',
				sortable : true,
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
						sortable : true,
						width : 55,
						dataIndex : 'archRoll',
						renderer : function(archRoll) {
							if (archRoll) {
								return archRoll.archFond.afNo;
							}
						}
					}, {
						header : '案卷号',
						sortable : true,
						width : 55,
						dataIndex : 'archRoll',
						renderer : function(archRoll) {
							if (archRoll)
								return archRoll.rollNo;
						}
					}
					// , {
					// header : '所属分类',
					// dataIndex : 'typeName'
					// }

					, {
						header : '文件题名',
						sortable : true,
						dataIndex : 'fileName'
					}

					// , {
					// header : '文件编号',
					// dataIndex : 'fileNo'
					// }

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

					// , {
					// header : '密级',
					// dataIndex : 'secretLevel'
					// }, {
					// header : '保管期限',
					// dataIndex : 'timeLimit'
					// }, {
					// header : '开放形式',
					// dataIndex : 'openStyle'
					// }

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
						sortable : true,
						dataIndex : 'archStatus',
						width : 65,
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
					}, this.rowAtction]
				// end of columns
			});
		this.centerPanel = new Ext.Panel({
					title : '卷内文件详细信息',
					region : 'center',
					layout : 'fit',
					frame : false,
					border : true,
					items : [this.filesGrid],
					listeners : {
						afterlayout : function(centerPanel) {
							var ch = centerPanel.getInnerHeight();
							Ext.getCmp('TidyFileGrid').setHeight(ch);

						}

					}

				});

		this.selectPanel = new Ext.Panel({
					frame : true,
					border : false,
					hideBorders : true,
					width : 35,
					region : 'west',
					layout : {
						type : 'vbox',
						pack : 'center',
						align : 'stretch'
					},
					defaults : {
						margins : '0 0 5 0'
					},
					items : [{
								xtype : 'button',
								iconCls : 'btn-down',
								scope : this,
								handler : this.up
							}, {
								xtype : 'button',
								iconCls : 'btn-top',
								scope : this,
								handler : this.down
							}]
				})

		this.tidyFileForm = new TidyFileForm({
					frame : true,
					id : 'TidyFileFormWin',
					region : 'center'
				}).show();

		this.eastPanel = new Ext.Panel({
					frame : false,
					border : false,
					region : 'east',
					//width : 400,
					layout : 'border',
					id : 'TidyFileViewEastPanel',
					items : [this.selectPanel, this.tidyFileForm]

				});

		// this.filesGrid.addListener('rowdblclick', this.rowClick);

	},// end of the initComponents()
	up : function() {
		var filesGrid = Ext.getCmp('TidyFileGrid');
		var selectRecords = filesGrid.getSelectionModel().getSelections();
		if (selectRecords.length == 0) {
			Ext.ux.Toast.msg("信息", "请选择要移动的记录！");
			return;
		}
		for (i = 0; i < selectRecords.length; i++) {
			if (selectRecords[i].data.archStatus == '1') {
				selectRecords.splice(i, 1);
				i--;
			}
		}
		if (selectRecords.length == 0) {

			Ext.ux.Toast.msg("信息", "所选择的文件件已归档！");
			return;

		}

		// var upRecords=[];
		// for(i=0;i<selectRecords.length;i++){
		// upRecords.push(selectRecords[i].data);
		// filesGrid.getStore().remove(selectRecords[i]);
		// }
		// for(i=0;i<this.tidyFileForm.store.getTotalCount();i++){
		// var record=this.tidyFileForm.store.getAt(i);
		// upRecords.push(record.data);
		// }
		//		
		// this.tidyFileForm.store.loadData({
		// 'success':true,
		// 'totalProperty':upRecords.length,
		// 'result': upRecords
		// });
		for (i = 0; i < selectRecords.length; i++) {
			this.tidyFileForm.store.add(selectRecords[i]);

			filesGrid.getStore().remove(selectRecords[i]);
		}

		// this.tidyFileForm.store.commitChanges();
		// filesGrid.getStore().commitChanges();
		this.tidyFileForm.gridPanel.getBottomToolbar().moveFirst();

	},
	down : function() {
		var filesGrid = Ext.getCmp('TidyFileGrid');
		var gridPanel = this.tidyFileForm.gridPanel;
		var store = this.tidyFileForm.store;
		var selectRecords = gridPanel.getSelectionModel().getSelections();
		if (selectRecords.length == 0) {
			Ext.ux.Toast.msg("信息", "请选择要移动的记录！");
			return;
		}
		for (i = 0; i < selectRecords.length; i++) {

			store.remove(selectRecords[i]);
			filesGrid.getStore().add(selectRecords[i]);
		}
		// store.commitChanges();
		// filesGrid.getStore().commitChanges();
		// filesGrid.getBottomToolbar().moveFirst();

	},
	// 重置查询表单
	reset : function() {
		this.searchPanel.getForm().reset();
	},
	// 按条件搜索
	search : function() {
		$search({
					searchPanel : this.searchPanel,
					gridPanel : this.filesGrid
				});
	},
	viewFile : function(record) {

		Ext.Ajax.request({
					url : __ctxPath + '/arch/listRollFileList.do',
					method : 'POST',
					async : false,
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
				this.showFile.call(this, record);
				break;
			default :
				break;
		}
	}

		// GridPanel行点击处理事件
		// rowClick : function(grid, rowindex, e) {
		// grid.getSelectionModel().each(function(rec) {
		// new RollFileForm({
		// rollFileId : rec.data.rollFileId
		// }).show();
		// });
		// },
		// 创建记录
		// createRs : function() {
		// new RollFileForm({
		//
		// proTypeId : Ext.getCmp('TidyFileSearchPanel').getForm()
		// .findField('TidyFileView.proTypeId').getValue(),
		// typeName : Ext.getCmp('TidyFileSearchPanel').getForm()
		// .findField('TidyFileView.typeName').getValue()
		//
		// }).show();
		// },
		// 按ID删除记录
		// removeRs : function(id) {
		// $postDel({
		// url : __ctxPath + '/arch/multiDelRollFile.do',
		// ids : id,
		// grid : this.filesGrid
		// });
		// },
		// 把选中ID删除
		// removeSelRs : function() {
		// $delGridRs({
		// url : __ctxPath + '/arch/multiDelRollFile.do',
		// grid : this.filesGrid,
		// idName : 'rollFileId'
		// });
		// },
		// 编辑Rs
		// editRs : function(record) {
		// new RollFileForm({
		// rollFileId : record.data.rollFileId
		// }).show();
		// },

});
