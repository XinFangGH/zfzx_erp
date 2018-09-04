/**
 * @author
 * @createtime
 * @class TidyFileForm
 * @extends Ext.Window
 * @description RollFile表单
 * @company 智维软件
 */

Ext.ns('TidyFileForm');

TidyFileForm = Ext.extend(Ext.Panel, {
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		// 必须先初始化组件
		this.initUIComponents();
		TidyFileForm.superclass.constructor.call(this, {
					layout : 'form',
					border : true,
					items : [this.gridPanel],
					title : '归档文件详细信息',
					frame : false,
					listeners : {
						afterlayout : function(window) {
							var wh = window.getInnerHeight();
							window.gridPanel.setHeight(wh);

						}

					}
				});
	},// end of the constructor
	// 初始化组件
	initUIComponents : function() {

		// 本地分页数据源
		this.store = new Ext.ux.data.JsonPagingStore({
					root : 'result',
					totalProperty : 'totalCounts',
					remoteSort : false,
					idProperty : "rollFileId",
					fields : [{
								name : 'rollFileId',
								type : 'int'
							}, 'afNo', 'createTime', 'creatorName',
							'creatorId', 'archStatus', 'proTypeId', 'typeName',
							'openStyle', 'rollNo', 'fileName', 'fileNo',
							'fileNo', 'catNo', 'seqNo', 'pageNo', 'pageNums',
							'secretLevel', 'timeLimit', 'keyWords', 'keyWords',
							'content', 'fileTime', 'notes', 'dutyPerson',
							'archRoll', 'globalType']
				});

		this.rowActions = new Ext.ux.grid.RowActions({
					header : '管理',
					width : 55,
					actions : [{
								iconCls : 'btn-readdocument',
								qtip : '预览附件',
								style : 'margin:0 3px 0 3px'
							}, {

								iconCls : 'btn-showDetail',
								qtip : '查看',
								style : 'margin:0 3px 0 3px'

							}]
				});

		var sm = new Ext.grid.CheckboxSelectionModel();
		var cm = new Ext.grid.ColumnModel({
					columns : [sm, new Ext.grid.RowNumberer(), {
								header : 'rollFileId',
								dataIndex : 'rollFileId',
								hidden : true
							}

							, {
								header : '全宗号',
								sortable : true,
								dataIndex : 'archRoll',
								renderer : function(archRoll) {
									if (archRoll)
										return archRoll.afNo;
								}
							}, {
								header : '案卷号',
								sortable : true,
								dataIndex : 'archRoll',
								renderer : function(archRoll) {

									if (archRoll)
										return archRoll.rollNo;
								}
							}

							, {
								header : '文件题名',
								sortable : true,
								dataIndex : 'fileName'
							}

							, this.rowActions],
					defaults : {
						sortable : true,
						menuDisabled : false
					}
				});

				
				
				// 树形下拉框
		var comboxWithTree = new Ext.form.ComboBox({
			store : new Ext.data.SimpleStore({
						fields : [],
						data : [[]]
					}),
			editable : false,
			mode : 'local',
			fieldLabel : '文件分类',
			allowBlank : false,
			triggerAction : 'all',
			maxHeight : 200,
			width : 100,
			selectOnFocus : false,
			tpl : "<tpl for='.'><div style='height:200px;'><div id='TidyFileFormComBoxWithTreeDiv'></div></div></tpl>",
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
						Ext.getCmp('tidyFile.proTypeId')
										.setValue('');
						Ext.getCmp('tidyFile.typeName')
										.setValue('');

						comboxWithTree.setValue('');
						comboxWithTree.id = ''
						comboxWithTree.collapse();

						
					} else {

						Ext.getCmp('tidyFile.proTypeId')
										.setValue(proTypeId);
						Ext.getCmp('tidyFile.typeName')
										.setValue(typeName);
										
						

						comboxWithTree.setValue(typeName);
						comboxWithTree.id = proTypeId;
						comboxWithTree.collapse();
						

					}

					comboxWithTree.validate();

				});

		comboxWithTree.on('expand', function() {
					leftPanel.render('TidyFileFormComBoxWithTreeDiv');
				});

				
				
		this.topbar = new Ext.Toolbar({
			items : [{
						xtype : 'tbtext',
						text : '分类'
					},comboxWithTree,  {
						xtype : 'tbtext',
						text : '全宗号'
					}, {
						width : 50,
						name : 'rollFile.afNo',
						id : 'tidyFile.afNo',
						xtype : 'combo',
						mode : 'local',
						allowBlank : false,
						lazyInit : false,
						forceSelection : true,
						editable : false,
						triggerAction : 'all',
						store : new Ext.data.JsonStore({
									url : __ctxPath + "/arch/listArchFond.do",
									autoLoad : true,
									autoShow : true,
									root : 'result',
									fields : ['archFondId', 'afNo'],
									listeners : {
										'load' : function(store, records,
												options) {
										}
									}
								}),
						valueField : 'afNo',
						displayField : 'afNo',
						listeners : {
							scope : this,
							'select' : function(combo, record, index) {
								Ext.getCmp('tidyFile.rollNo').getStore().load({
									params : {

										'Q_archFond.archFondId_L_EQ' : record.data.archFondId
									}
								});
								Ext.getCmp('tidyFile.rollNo').reset();
							}
						}
					}, {
						xtype : 'tbtext',
						text : '案卷号'
					}, {
						width : 50,
						id : 'tidyFile.rollNo',
						name : 'rollFile.rollNo',
						xtype : 'textfield',
						allowBlank : false,
						xtype : 'combo',
						mode : 'local',
						editable : false,
						triggerAction : 'all',
						store : new Ext.data.JsonStore({
									url : __ctxPath + "/arch/listArchRoll.do",
									autoLoad : true,
									autoShow : true,
									root : 'result',
									idProperty : 'rollId',
									fields : ['rollId', 'rollNo', 'afNo']
								}),
						valueField : 'rollId',
						displayField : 'rollNo',
						listeners : {
							scope : this,
							'select' : function(combo, record, index) {

								Ext.getCmp('tidyFile.rollId').setValue(record
										.get('rollId'));

							}
						}

					}, {
						xtype : 'button',
						text : '保存',
						iconCls : 'btn-save',
						scope : this,
						handler : this.save
					}, {
						// fieldLabel : '文件分类ID',
						width : 100,
						id : 'tidyFile.proTypeId',
						value : this.proTypeId == null ? '' : this.proTypeId,
						name : 'rollFile.proTypeId',
						xtype : 'hidden'
					}, {
						// fieldLabel : '案卷ID',
						width : 100,
						id : 'tidyFile.rollId',
						name : 'rollFile.rollId',
						xtype : 'hidden'
					},{
						// fieldLabel : '文件分类名称',
						id : 'tidyFile.typeName',
						name : 'rollFile.typeName',
						width : 100,
						xtype : 'hidden'
					}]

		});

		this.gridPanel = new Ext.grid.GridPanel({
					frame : false,
					border : false,
					region : 'center',
					height : 400,
					tbar : this.topbar,
					store : this.store,
					cm : cm,
					sm : sm,
					autoExpandColumn : 'fileName',
					plugins : this.rowActions,
					viewConfig : {
						forceFit : true,
						autoFill : true
					},
					bbar : new HT.PagingBar({
								store : this.store
							}),
					listeners : {}

				});
		this.rowActions.on('action', this.onRowAction, this);
	},// end of the initcomponents

	/**
	 * 保存记录
	 */
	save : function() {

		var params = [];
		var store = this.store;
		var gridPanel = this.gridPanel;
		var cnt = store.getTotalCount();

		if (Ext.getCmp('tidyFile.proTypeId').getValue() == ''
				|| Ext.getCmp('tidyFile.proTypeId').getValue() == null
				|| Ext.getCmp('tidyFile.rollId').getValue() == ''
				|| Ext.getCmp('tidyFile.rollId').getValue() == null) {
			return;
		}

		for (i = 0; i < cnt; i++) {

			var record = store.allData.items[i];

			// 外键
			Ext.apply(record.data, {
						typeName : Ext.get('tidyFile.typeName').dom.value,
						afNo : Ext.getCmp('tidyFile.afNo').getValue(),
						rollNo : Ext.get('tidyFile.rollNo').dom.value,
						archStatus : 1
					});
			if (record.data.globalType) {
				Ext.apply(record.data.globalType, {
							proTypeId : Ext.getCmp('tidyFile.proTypeId')
									.getValue()
						});
			} else {
				var globalType = {};
				Ext.apply(globalType, {
							proTypeId : Ext.getCmp('tidyFile.proTypeId')
									.getValue()
						});
				Ext.apply(record.data, {
							globalType : globalType
						});

			}
			if (record.data.archRoll) {
				Ext.apply(record.data.archRoll, {
							rollId : Ext.getCmp('tidyFile.rollId').getValue()
						});
			} else {
				var archRoll = {};
				Ext.apply(archRoll, {
							rollId : Ext.getCmp('tidyFile.rollId').getValue()
						});
				Ext.apply(record.data, {
							archRoll : archRoll
						});

			}
			
			// 时间
			/*
			if (record.data.archRoll) {
				if (record.data.archRoll.startTime) {
					Ext.apply(record.data.archRoll, {
								startTime : new Date(record.data.archRoll.startTime)
										.format('Y-m-d')

							});
				}
				if (record.data.archRoll.endTime) {
					Ext.apply(record.data.archRoll, {
								endTime : new Date(record.data.archRoll.endTime)
										.format('Y-m-d')

							});
				}

				if (record.data.archRoll.setupTime) {
					Ext.apply(record.data.archRoll, {
								setupTime : new Date(record.data.archRoll.setupTime)
										.format('Y-m-d')

							});
				}

				if (record.data.archRoll.createTime) {
					Ext.apply(record.data.archRoll, {
								createTime : new Date(record.data.archRoll.createTime)
										.format('Y-m-d')
							});
				}
			}
			if (record.data.archRoll && record.data.archRoll.archFond) {
				if (record.data.archRoll.updateTime) {
					Ext.apply(record.data.archRoll.archFond, {

								updateTime : new Date(record.data.archRoll.updateTime)
										.format('Y-m-d')
							});
				}
			}*/

			params.push(record.data);

		}
		if (params.length == 0) {
			Ext.ux.Toast.msg("信息", "请选择要归档的文件！");
			return;
		}
		
		
		Ext.Ajax.request({
					url : __ctxPath + '/arch/tidyRollFile.do',
					method : 'POST',
					async : true,
					success : function(response, opts) {
						Ext.ux.Toast.msg('操作信息', '保存成功!');
						store.removeAll();
						gridPanel.getView().refresh();
						store.commitChanges();

					},

					failure : function(response, opts) {
						Ext.MessageBox.show({
									title : '操作信息',
									msg : '信息保存出错，请联系管理员！',
									buttons : Ext.MessageBox.OK,
									icon : Ext.MessageBox.ERROR
								});
					},
					params : {
						params : Ext.encode(params)
					}
				});
				

	}// end of save
	,
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
		showFile:function(record){
			
			new MyBorrowFileViewWindow({
				rollFileId:record.data.rollFileId,
				archStatus:record.data.archStatus
			}).show();
		},
	onRowAction : function(gridPanel, record, action, row, col) {
		switch (action) {
			case 'btn-readdocument' :
				this.viewFile(record);
				break;
			case 'btn-showDetail':
			this.showFile.call(this, record);
			break;
			default :
				break;
		}
	}

});