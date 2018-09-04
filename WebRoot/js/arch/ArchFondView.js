/**
 * @author:
 * @class ArchFondView
 * @extends Ext.Panel
 * @description 全宗管理
 * @company 北京互融时代软件有限公司
 * @createTime:
 */
ArchFondView = Ext.extend(Ext.Panel, {
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		// 初始化组件
		this.initUIComponents();
		// 调用父类构造
		ArchFondView.superclass.constructor.call(this, {
					id : 'ArchFondView',
					title : '全宗管理',
					iconCls:'menu-archFond',
					region : 'center',
					layout : 'border',
					items : [this.searchPanel, this.leftPanel, this.gridPanel]
				});
	},// end of constructor
	// 初始化组件
	initUIComponents : function() {
		// 初始化搜索条件Panel
		this.searchPanel = new HT.SearchPanel({
					id : 'ArchFondSearchPanel',
					layout : 'form',
					region : 'north',
					width : '100%',
					height : 80,
					keys : {
						key : Ext.EventObject.ENTER,
						fn : this.search.createCallback(this),
						scope : this
					},
					items : [{
						border : false,
						layout : 'column',
						layoutConfig : {
							padding : '5',
							align : 'middle'
						},
						defaults : {
							xtype : 'label'
						},
						items:[{
							columnWidth : .3,
							style : 'margin-top:8px;',
							xtype : 'container',
							layout : 'form',
							items:[{
								anchor : '99%',
								fieldLabel : '全宗号',
								name : 'Q_afNo_S_LK',
								flex : 1,
								xtype : 'textfield'
							},
							{
								anchor : '99%',
								fieldLabel : '全宗名',
								name : 'Q_afName_S_LK',
								flex : 1,
								xtype : 'textfield'
							}]
						},{
							columnWidth : .3,
							style : 'margin-top:8px;',
							xtype : 'container',
							layout : 'form',
							items:[{
								anchor : '99%',
								fieldLabel : '状态',
								hiddenName : 'Q_status_SN_EQ',
								flex : 1,
								xtype : 'combo',
								mode : 'local',
								editable : false,
								triggerAction : 'all',
								store : [['', '全部'],['0', '草稿'], ['1', '启用'], ['-1', '禁用']]
							},
							{
								anchor : '99%',
								fieldLabel : '开放形式',
								name : 'Q_openStyle_S_EQ',
								flex : 1,
								editable : true,
								lazyInit : false,
								forceSelection : false,
								xtype : 'diccombo',
								itemName : '全宗开放形式'
							}]
						},{
							columnWidth : .3,
							style : 'margin-top:8px;',
							xtype : 'container',
							layout : 'form',
							items:[{
								xtype : 'container',
								layout : 'column',
								fieldLabel : '创建时间',
								items:[{
									columnWidth : .49,
									name : 'Q_createTime_D_GE',
									flex : 1,
									xtype : 'datefield',
									format : 'Y-m-d'
								},{
									xtype : 'label',
									style : 'margin-top:3px;',
									text : ' 至 '
								}, {
									columnWidth : .49,
									anchor : '99%',
									xtype : 'datefield',
									name : 'Q_createTime_D_LE',
									format : 'Y-m-d'
								}]
							},{
								xtype : 'container',
								layout : 'column',
								fieldLabel : '最后更新时间',
								items:[{
									columnWidth : .49,
									name : 'Q_updatetime_D_EQ',
									flex : 1,
									xtype : 'datefield',
									format : 'Y-m-d'
								},{
									xtype : 'label',
									style : 'margin-top:3px;',
									text : ' 至 '
								}, {
									columnWidth : .49,
									anchor : '99%',
									xtype : 'datefield',
									name : 'Q_updatetime_D_LE',
									format : 'Y-m-d'
								}]
								
							}]
						},{
							items:[{
								fieldLabel : '全宗号	Id',
								id : 'ArchFondView.proTypeId',
								name : 'Q_globalType.proTypeId_L_EQ',
								flex : 1,
								xtype : 'hidden',
								hideLabel : true
								
							},{
								fieldLabel : '全宗分类',
								id : 'ArchFondView.typeName',
								name : 'Q_typeName_S_LK',
								flex : 1,
								xtype : 'hidden',
								hideLabel : true
							}]
						},{
							items:[{
								fieldLabel : '',
								id : 'ArchFondView.proTypeId',
								name : 'Q_globalType.proTypeId_L_EQ',
								xtype : 'hidden',
								hideLabel : true,
								flex : 1
							},{
								fieldLabel : '',
								id : 'ArchFondView.typeName',
								name : 'Q_typeName_S_LK',
								xtype : 'hidden',
								hideLabel : true,
								flex : 1
							}]
						},{
							style : 'margin-top:8px;margin-left:5px;',
							layout : 'form',
							xtype : 'container',
							defaultType : 'button',
							items : [{
								iconCls : 'search',
								text : '查询',
								scope : this,
								handler : this.search
							}, {
								iconCls : 'reset',
								style : 'margin-top:5px;',
								text : '重置',
								scope : this,
								handler : this.reset
							}]
						}]
					}]
				});// end of searchPanel
		this.leftPanel = new Ext.Panel({
			title:'全宗分类',
			region : 'west',
			layout : 'anchor',
			collapsible : true,
			split : true,
			width : 200,
			items : [

			{
				xtype : 'treePanelEditor',
				id : 'ArchFondGlobalTypeTree',
				split : true,
				rootVisible : false,
				border : false,
				height : 380,
				autoScroll : true,
				scope : this,
				url : __ctxPath + '/system/treeGlobalType.do?catKey=AR_FD',
				onclick : function(node) {

					var proTypeId = node.id;
					var typeName = node.text;

					if (proTypeId == '0') {
						Ext.getCmp('ArchFondSearchPanel').getForm()
								.findField('ArchFondView.proTypeId')
								.setValue('');
						Ext.getCmp('ArchFondSearchPanel').getForm()
								.findField('ArchFondView.typeName')
								.setValue('');

					} else {
						Ext.getCmp('ArchFondSearchPanel').getForm()
								.findField('ArchFondView.proTypeId')
								.setValue(proTypeId);
						Ext.getCmp('ArchFondSearchPanel').getForm()
								.findField('ArchFondView.typeName')
								.setValue(typeName);

					}

					Ext.getCmp('ArchFondView').search();

				},
				contextMenuItems : [{
					text : '新建分类',
					scope : this,
					iconCls : 'btn-add',
					handler : function() {
						var globalTypeTree = Ext
								.getCmp('ArchFondGlobalTypeTree');
						var parentId = globalTypeTree.selectedNode.id;
						var globalTypeForm = new GlobalTypeForm({
									parentId : parentId,
									catKey : 'AR_FD',
									callback : function() {
										Ext.getCmp('ArchFondGlobalTypeTree').root
												.reload();
									}
								});
						globalTypeForm.show();

					}
				}, {
					text : '修改分类',
					scope : this,
					iconCls : 'btn-edit',
					handler : function() {
						var globalTypeTree = Ext
								.getCmp('ArchFondGlobalTypeTree');
						var proTypeId = globalTypeTree.selectedNode.id;

						var globalTypeForm = new GlobalTypeForm({
									proTypeId : proTypeId,
									callback : function() {
										Ext.getCmp('ArchFondGlobalTypeTree').root
												.reload();
									}
								});
						globalTypeForm.show();
					}
				}, {

					text : '删除分类',
					scope : this,
					iconCls : 'btn-del',
					handler : function() {
						var globalTypeTree = Ext
								.getCmp('ArchFondGlobalTypeTree');
						var proTypeId = globalTypeTree.selectedNode.id;
						var ids = Array();
						ids.push(proTypeId);
						Ext.Msg.confirm('信息确认', '您确认要删除所选记录吗？', function(btn) {
							if (btn == 'yes') {
								Ext.Ajax.request({
									url : __ctxPath
											+ '/system/multiDelGlobalType.do',
									params : {
										ids : ids
									},
									method : 'POST',
									success : function(response, options) {
										Ext.ux.Toast.msg('操作信息', '成功删除该产品分类！');
										Ext.getCmp('ArchFondGlobalTypeTree').root
												.reload();
										Ext.getCmp('ArchFondGrid').getStore()
												.reload();
									},
									failure : function(response, options) {
										Ext.ux.Toast
												.msg('操作信息', '操作出错，请联系管理员！');
									}
								});
							}
						});

					}

				}]
			}]
		}

		);
		this.topbar = new Ext.Toolbar({
					items : [{
								iconCls : 'btn-add',
								text : '添加全宗',
								xtype : 'button',
								scope : this,
								handler : this.createRs
							}, {
								iconCls : 'btn-del',
								text : '删除全宗',
								xtype : 'button',
								scope : this,
								handler : this.removeSelRs
							}]
				});

		this.gridPanel = new HT.GridPanel({
			region : 'center',
			tbar : this.topbar,
			// 使用RowActions
			rowActions : true,
			id : 'ArchFondGrid',
			url : __ctxPath + "/arch/listArchFond.do",
			fields : [{
						name : 'archFondId',
						type : 'int'
					}, 'afNo', 'afName', 'shortDesc', 'descp', 'clearupDesc',
					'createTime', 'updateTime', 'creatorName', 'creatorId',
					'caseNums', 'status', 'globalType', 
					'typeName', 'openStyle'],
			columns : [{
						header : 'archFondId',
						dataIndex : 'archFondId',
						hidden : true
					}, {
						header : '全宗号',
						dataIndex : 'afNo'
					}, {
						header : '全宗名',
						dataIndex : 'afName'
					},
					{
						header : '案卷数',
						dataIndex : 'caseNums'
					},

					{
						header : '状态',
						dataIndex : 'status',
						renderer : function(v) {
							switch (v) {
								case 0 :
									return '草稿';
									break;
								case 1 :
									return '启用';
									break;
								case -1 :
									return '禁用';
									break;

							}
						}
					},
					{
						header : '全宗分类',
						dataIndex : 'typeName'
					}, {
						header : '开放形式',
						dataIndex : 'openStyle'
					}, {
						header : '创建人',
						dataIndex : 'creatorName'
					}, {
						header : '创建时间',
						dataIndex : 'createTime'
					},
					new Ext.ux.grid.RowActions({
								header : '管理',
								width : 100,
								actions : [{
											iconCls : 'btn-del',
											qtip : '删除',
											style : 'margin:0 3px 0 3px'
										}, {
											iconCls : 'btn-edit',
											qtip : '编辑',
											style : 'margin:0 3px 0 3px'
										}],
								listeners : {
									scope : this,
									'action' : this.onRowAction
								}
							})]
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
					new ArchFondForm({
								archFondId : rec.data.archFondId
							}).show();
				});
	},
	// 创建记录
	createRs : function() {
		new ArchFondForm().show();
	},
	// 按ID删除记录
	removeRs : function(id) {
		$postDel({
					url : __ctxPath + '/arch/multiDelArchFond.do',
					ids : id,
					grid : this.gridPanel
				});
	},
	// 把选中ID删除
	removeSelRs : function() {
		$delGridRs({
					url : __ctxPath + '/arch/multiDelArchFond.do',
					grid : this.gridPanel,
					idName : 'archFondId'
				});
	},
	// 编辑Rs
	editRs : function(record) {
		new ArchFondForm({
					archFondId : record.data.archFondId
				}).show();
	},
	// 行的Action
	onRowAction : function(grid, record, action, row, col) {
		switch (action) {
			case 'btn-del' :
				this.removeRs.call(this, record.data.archFondId);
				break;
			case 'btn-edit' :
				this.editRs.call(this, record);
				break;
			default :
				break;
		}
	}
});
