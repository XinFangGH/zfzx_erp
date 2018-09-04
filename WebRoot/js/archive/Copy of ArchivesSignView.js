Ext.ns('ArchivesSignView');

/**
 * @author:
 * @class ArchivesSignView
 * @extends Ext.Panel
 * @description 拟稿管理
 * @company 北京互融时代软件有限公司
 * @createtime:2010-01-16
 */
ArchivesSignView = Ext.extend(Ext.Panel, {
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
		ArchivesSignView.superclass.constructor.call(this, {
					id : 'ArchivesSignView',
					iconCls : 'menu-archive-sign',
					title : '公文签收待办',
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
							}, {
								xtype : 'hidden',
								name : 'Q_archives.archStatus_SN_EQ',
								value : 1
								// 查找状态为已经部门审核的公文
							}, {
								xtype : 'hidden',
								name : 'Q_archives.archType_SN_EQ',
								value : 0
								// 查找发文的公文
						}]
				});// end of the searchPanel

		// 加载数据至store
		this.store = new Ext.data.JsonStore({
					url : __ctxPath + "/archive/listArchivesDep.do",
					root : 'result',
					baseParams : {
						'Q_archives.archType_SN_EQ' : 0, // 查询是发文的公文
						'Q_archives.archStatus_SN_EQ' : 1, // 查询已归档的公文
						'Q_status_SN_EQ' : 0
						// 查找未签收的公文
					},
					totalProperty : 'totalCounts',
					remoteSort : true,
					fields : [{
								name : 'archDepId',
								type : 'int'
							}, 'archives']
				});
		this.store.setDefaultSort('archDepId', 'desc');
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
			columns : [sm, new Ext.grid.RowNumberer(),

			{
						header : 'archDepId',
						dataIndex : 'archDepId',
						hidden : true
					}, {
						header : '公文类型名称',
						dataIndex : 'archives',
						renderer : function(value) {
							return value.typeName;
						}
					}, {
						header : '发文字号',
						dataIndex : 'archives',
						renderer : function(value) {
							return value.archivesNo;
						}
					}, {
						header : '发文机关或部门',
						dataIndex : 'archives',
						renderer : function(value) {
							return value.issueDep;
						}
					}, {
						header : '文件标题',
						dataIndex : 'archives',
						renderer : function(value) {
							return value.subject;
						}
					}, {
						header : '公文状态',
						dataIndex : 'archives',
						renderer : function(value) {
							if (value != null) {
								return value.status;
							} else {
								return '办结';
							}
						}
					}, {
						header : '秘密等级',
						dataIndex : 'archives',
						renderer : function(value) {
							return value.privacyLevel;
						}
					}, {
						header : '紧急程度',
						dataIndex : 'archives',
						renderer : function(value) {
							return value.urgentLevel;
						}
					}, {
						header : '发文时间',
						dataIndex : 'archives',
						renderer : function(value) {
							return value.createtime.substring(0, 10);
						}
					}, {
						header : '管理',
						width : 100,
						dataIndex : 'archives',
						sortable : false,
						renderer : function(value, metadata, record, rowIndex,
								colIndex) {
							var editId = value.archivesId;
							var status = value.status;
							var archDepId = record.data.archDepId;
							var str = '';
							if (isGranted('_ArchivesSignQuery')) {
								str += '<button title="查阅详情" value=" " class="btn-archives-detail" onclick="ArchivesSignView.detail('
										+ editId + ')">&nbsp;&nbsp;</button>';
							}
							if (isGranted('_ArchivesSignUp')) {
								str += '<button title="公文签收" value=" " class="btn-archive-sign" onclick="ArchivesSignView.attach('
										+ editId
										+ ','
										+ archDepId
										+ ')">&nbsp;&nbsp;</button>';
							}
							return str;
						}
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
					items : [
					// {
					// iconCls : 'btn-archives-remind',
					// text : '催办',
					// xtype : 'button'
					// }
					]
				});

		this.gridPanel = new Ext.grid.GridPanel({
					id : 'ArchivesSignGrid',
					region : 'center',
					stripeRows : true,
					tbar : this.topbar,
					store : this.store,
					trackMouseOver : true,
					disableSelection : false,
					loadMask : true,
					height : 600,
					cm : cm,
					sm : sm,
					plugins : this.rowActions,
					viewConfig : {
						forceFit : true,
						autoFill : true, // 自动填充
						forceFit : true
						// showPreview : false
					},
					bbar : new HT.PagingBar({store : this.store})
				});

		this.gridPanel.addListener('rowdblclick', function(grid, rowindex, e) {
					grid.getSelectionModel().each(function(rec) {
								// new ArchivesForm(rec.data.archivesId).show();
							});
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
	 * 添加记录
	 */
	createRecord : function() {
		new ArchivesForm().show();
	}

});
/**
 * 展示公文详细信息
 * 
 * @param {}
 *            editId
 */
ArchivesSignView.detail = function(editId) {
	new ArchivesDetailWin({
				archivesId : editId
			}).show();
}
/**
 * 校对公文附件
 * 
 * @param {}
 *            editId
 */
ArchivesSignView.attach = function(editId, archDepId) {
	// 只允许有一个编辑窗口
//	var tabs = Ext.getCmp('centerTabPanel');
//	var edit = Ext.getCmp('ArchivesRecForm');
//	if (edit == null) {
//		edit = new ArchivesRecForm({
//					archivesId : editId,
//					archDepId : archDepId,
//					isSign : true
//				});
//		tabs.add(edit);
//	} else {
//		tabs.remove('ArchivesRecForm');
//		edit = new ArchivesRecForm({
//					archivesId : editId,
//					archDepId : archDepId,
//					isSign : true
//				});
//		tabs.add(edit);
//	}
//	tabs.activate(edit);
	var contentPanel = App.getContentPanel();
	var startForm = contentPanel.getItem('ProcessRunStart' + node.attributes.defId);

	if (startForm == null) {
		startForm = new ProcessRunStart({
					id : 'ProcessRunStart' + node.attributes.defId,
					defId : node.attributes.defId,
					flowName : node.attributes.flowName
				});
		contentPanel.add(startForm);
	}
	contentPanel.activate(startForm);
}
