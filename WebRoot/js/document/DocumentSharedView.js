Ext.ns('DocumentSharedView');

var DocumentSharedView = function() {
	return new Ext.Panel({
		id : 'DocumentSharedView',
		title : '共享文档列表',
		layout:'border',
		iconCls : 'menu-folder-shared',
		autoScroll : true,
		items : [new Ext.FormPanel({
			id : 'SharedDocumentForm',
			region:'north',
			height : 40,
			frame : false,
			border : false,
			layout : 'hbox',
			layoutConfig : {
				padding : '5',
				align : 'middle'
			},
			defaults : {
				xtype : 'label',
				margins : {
					top : 0,
					right : 4,
					bottom : 4,
					left : 4
				}
			},
			items : [{
						text : '文档名称'
					}, {
						xtype : 'textfield',
						name : 'document.docName'
					}, {
						text : '共享人'
					}, {
						xtype : 'textfield',
						name : 'fullname'
					}, {
						text : '创建时间 从'
					}, {
						xtype : 'datefield',
						format : 'Y-m-d',
						name : 'from'
					}, {
						text : '至'
					}, {
						xtype : 'datefield',
						format : 'Y-m-d',
						name : 'to'
					}, {
						xtype : 'button',
						text : '查询',
						iconCls : 'search',
						handler : function() {
							var searchPanel = Ext.getCmp('SharedDocumentForm');
							var gridPanel = Ext.getCmp('DocumentSharedGrid');
							if (searchPanel.getForm().isValid()) {
								$search({
									searchPanel :searchPanel,
									gridPanel : gridPanel
								});
							}

						}
					}, {
						xtype : 'button',
						text : '重置',
						iconCls : 'reset',
						handler : function() {
							var searchPanel = Ext.getCmp('SharedDocumentForm');
							searchPanel.getForm().reset();
						}
					}]
		}), this.setup()]
	});
};
/**
 * 建立视图
 */
DocumentSharedView.prototype.setup = function() {
	return this.grid();
};

/**
 * 建立DataGrid
 */
DocumentSharedView.prototype.grid = function() {
	var sm = new Ext.grid.CheckboxSelectionModel();
	var cm = new Ext.grid.ColumnModel({
		columns : [sm, new Ext.grid.RowNumberer(), {
					header : 'docId',
					dataIndex : 'docId',
					hidden : true
				}, {
					header : '文档名称',
					dataIndex : 'docName',
					width : 120,
					renderer:function(value, metadata, record){
					   return value+'--'+'由 <font color="green">'+record.data.fullname+'</font> 共享';
					}
				}, {
					header : '创建时间',
					dataIndex : 'createtime'
				}, {
					header : '共享人',
					dataIndex : 'fullname'
				}, {
					header : '附件',
					dataIndex : 'haveAttach',
					renderer : function(value, metadata, record) {

						if (value == '' || value == '0') {
							return '无附件';
						} else {
							var attachFiles = record.data.attachFiles;
							var str = '';
							for (var i = 0; i < attachFiles.length; i++) {
								str += '<a href="#" onclick="FileAttachDetail.show('
										+ attachFiles[i].fileId
										+ ');" class="attachment">'
										+ attachFiles[i].fileName + '</a>';
								str += '&nbsp;';
							}

							return str;
						}
					}
				}, {
					header : '查看',
					dataIndex : 'docId',
					width : 50,
					renderer : function(value, metadata, record, rowIndex,
							colIndex) {
						var editId = record.data.docId;
						var editName = record.data.docName;
						var str = '<button title="查看" value=" " class="btn-readdocument" onclick="DocumentSharedView.read('
								+ editId
								+ ',\''
								+ editName
								+ '\')">&nbsp;</button>';
						str += '<button title="flex在线查看" value=" " class="btn-flex-view" onclick="DocumentSharedView.flexRead('
							+ editId + ')">&nbsp;</button>';
						return str;
					}
				}],
		defaults : {
			// sortable : true,
			menuDisabled : false,
			width : 100
		}
	});

	var store = this.store();
	store.load({
				params : {
					start : 0,
					limit : 25
				}
			});
	var grid = new Ext.grid.GridPanel({
				id : 'DocumentSharedGrid',
				region:'center',
				store : store,
				trackMouseOver : true,
				disableSelection : false,
				loadMask : true,
				tbar:new Ext.Toolbar({height:27}),
//				autoHeight : true,
				maxHeight : 600,
				cm : cm,
				sm : sm,
				viewConfig : {
					forceFit : true,
					enableRowBody : false,
					showPreview : false
				},
				bbar : new HT.PagingBar({store : store})
			});

	grid.addListener('rowdblclick', function(grid, rowindex, e) {
				grid.getSelectionModel().each(function(rec) {
							DocumentSharedView.read(rec.data.docId,
									rec.data.docName);
						});
			});
	return grid;

};

/**
 * 初始化数据
 */
DocumentSharedView.prototype.store = function() {
	var store = new Ext.data.Store({
				proxy : new Ext.data.HttpProxy({
							url : __ctxPath + '/document/shareListDocument.do'
						}),
				reader : new Ext.data.JsonReader({
							root : 'result',
							totalProperty : 'totalCounts',
							id : 'id',
							fields : [{
										name : 'docId',
										type : 'int'
									}, 'docName', 'fullname', 'content',
									'createtime', 'haveAttach', 'attachFiles']
						}),
				remoteSort : true
			});
	// store.setDefaultSort('docId', 'desc');
	return store;
};

DocumentSharedView.read = function(id, name) {
	var tabs = Ext.getCmp('centerTabPanel');
	var panel = Ext.getCmp('DocumentShared');
	if (panel == null) {
		panel = new DocumentSharedPanel(id, name);
		tabs.add(panel);
		tabs.activate(panel);
	} else {
		tabs.remove('DocumentShared');
		panel = new DocumentSharedPanel(id, name);
		tabs.add(panel);
		tabs.activate(panel);
	}
};

DocumentSharedView.flexRead = function(docId){
	 window.open(__ctxPath + '/iText/flexPaper.do?docId=' + docId);
};
