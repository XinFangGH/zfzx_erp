/**
 * @author:
 * @class DictionaryView
 * @extends Ext.Panel
 * @description 数据字典
 * @company 北京互融时代软件有限公司
 * @createtime:2010-04-12
 */
DictionaryView = Ext.extend(Ext.Panel, {
	// 条件搜索Panel
	searchPanel : null,
	// 数据展示Panel
	gridPanel : null,
	// GridPanel的数据Store
	store : null,
	// 头部工具栏
	topbar : null,
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		// 初始化组件
		this.initUIComponents();
		// 调用父类构造
		DictionaryView.superclass.constructor.call(this, {
					id : 'DictionaryView',
					iconCls : 'menu-dictionary',
					title : '字典列表',
					region : 'center',
					layout : 'border',
					items : [this.searchPanel, this.gridPanel]
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
			id : 'DictionarySearchForm',
			defaults : {
				xtype : 'label',
				style : 'padding:0px 5px 0px 5px;'
			},
			items : [{
						text : '请输入查询条件:'
					}, {
						text : '条目'
					}, {
						id : 'DicSearchFormItemName',
						width : 120,
						name : 'Q_itemName_S_LK',
						maxHeight : 200,
						xtype : 'combo',
						mode : 'local',
						editable : true,
						triggerAction : 'all',
						store : [],
						listeners : {
							focus : function(combo) {
								var _store = Ext
										.getCmp('DicSearchFormItemName')
										.getStore();
								if (_store.getCount() <= 0)
									Ext.Ajax.request({
										url : __ctxPath
												+ '/system/itemsDictionary.do',
										method : 'post',
										success : function(response) {
											var result = Ext.util.JSON
													.decode(response.responseText);
											_store.loadData(result);
										}
									});
							},
							select : function(combo, rec, rowIndex) {
								var _store = Ext
										.getCmp('DicSearchFormItemValue')
										.getStore();
								Ext.Ajax.request({
											url : __ctxPath
													+ '/system/loadDictionary.do',
											method : 'post',
											params : {
												itemName : combo.value
											},
											success : function(response) {
												var result = Ext.util.JSON
														.decode(response.responseText);
												_store.loadData(result);
											}
										});
							}
						}
					}, {
						text : '数值'
					}, {
						id : 'DicSearchFormItemValue',
						width : 120,
						name : 'Q_itemValue_S_LK',
						maxHeight : 200,
						xtype : 'combo',
						mode : 'local',
						editable : true,
						triggerAction : 'all',
						store : []
					}, {
						xtype : 'button',
						text : '查询',
						iconCls : 'search',
						handler : this.search.createCallback(this)
					}]
		});// end formPanel

		this.store = new Ext.data.Store({
					proxy : new Ext.data.HttpProxy({
								url : __ctxPath + '/system/listDictionary.do'
							}),
					reader : new Ext.data.JsonReader({
								root : 'result',
								totalProperty : 'totalCounts',
								id : 'id',
								fields : [{
											name : 'dicId',
											type : 'int'
										}

										, 'itemName', 'itemValue', 'descp']
							}),
					remoteSort : true
				});
		this.store.setDefaultSort('dicId', 'desc');
		this.store.load({
					params : {
						start : 0,
						limit : 25
					}
				});

		var actions = [];
		if(isGranted('_DictionaryDel')){
			actions.push({
								iconCls : 'btn-del',
								qtip : '删除',
								style : 'margin:0 3px 0 3px'
							})
		}
		if(isGranted('_DictionaryEdit')){
			actions.push({
								iconCls : 'btn-edit',
								qtip : '编辑',
								style : 'margin:0 3px 0 3px'
							})
		}
		this.rowActions = new Ext.ux.grid.RowActions({
					header : '管理',
					width : 80,
					actions : actions
				});		
		
		var sm = new Ext.grid.CheckboxSelectionModel();
		var cm = new Ext.grid.ColumnModel({
			columns : [sm, new Ext.grid.RowNumberer(), {
						header : 'dicId',
						dataIndex : 'dicId',
						hidden : true
					}, {
						header : '条目',
						dataIndex : 'itemName'
					}, {
						header : '数值',
						dataIndex : 'itemValue'
					}, {
						header : '备注',
						dataIndex : 'descp',
						renderer : function(value) {
							if (value == null || value == ''
									|| value == 'undefined') {
								return '无';
							} else {
								return value;
							}
						}
					}, this.rowActions],
			defaults : {
				sortable : true,
				menuDisabled : false,
				width : 100
			}
		});

		this.topbar = new Ext.Toolbar({
					id : 'DictionaryFootBar',
					height : 30,
					bodyStyle : 'text-align:left',
					items : []
				});
		if (isGranted('_DictionaryAdd')) {
			this.topbar.add(new Ext.Button({
						iconCls : 'btn-add',
						text : '添加字典',
						handler : this.createRecord,
						scope : this
					}));
		}
		if (isGranted('_DictionaryDel')) {
			this.topbar.add(new Ext.Button({
						iconCls : 'btn-del',
						text : '删除字典',
						handler :  this.delRecords,
						scope : this
					}));
		}

		this.gridPanel = new Ext.grid.GridPanel({
					id : 'DictionaryGrid',
					region : 'center',
					tbar : this.topbar,
					store : this.store,
					trackMouseOver : true,
					disableSelection : false,
					loadMask : true,
					region : 'center',
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

		this.gridPanel.addListener('rowdblclick', function(grid, rowindex, e) {
					grid.getSelectionModel().each(function(rec) {
								if (isGranted('_DictionaryEdit')) {
									DictionaryView.edit(rec.data.dicId);
								}
							});
				});
		this.rowActions.on('action', this.onRowAction, this);
	},// end of init();
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
				new DictionaryForm().show();
			},
			/**
			 * 按IDS删除记录
			 * 
			 * @param {}
			 *            ids
			 */
			delByIds : function(ids) {
				Ext.Msg.confirm('信息确认', '您确认要删除所选记录吗？', function(btn) {
							if (btn == 'yes') {
								Ext.Ajax.request({
											url : __ctxPath
													+ '/system/multiDelDictionary.do',
											params : {
												ids : ids
											},
											method : 'POST',
											success : function(response,
													options) {
												Ext.ux.Toast.msg("信息提示", "成功删除所选记录！");
												Ext.getCmp('DictionaryGrid').getStore().reload();
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
			 * 删除多条记录
			 */
			delRecords : function() {
				var gridPanel = Ext.getCmp('DictionaryGrid');
				var selectRecords = gridPanel.getSelectionModel()
						.getSelections();
				if (selectRecords.length == 0) {
					Ext.ux.Toast.msg("信息", "请选择要删除的记录！");
					return;
				}
				var ids = Array();
				for (var i = 0; i < selectRecords.length; i++) {
					ids.push(selectRecords[i].data.dicId);
				}
				this.delByIds(ids);
			},

			/**
			 * 编辑记录
			 * 
			 * @param {}
			 *            record
			 */
			editRecord : function(record) {
				new DictionaryForm({dicId : record.data.dicId}).show();
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
					case 'btn-del' :
						this.delByIds(record.data.dicId);
						break;
					case 'btn-edit' :
						this.editRecord(record);
						break;
					default :
						break;
				}
			}
});