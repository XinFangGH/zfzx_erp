Ext.ns('SearchDocument');
/**
 * 新闻列表
 */
var SearchDocument = function(_searchContent) {
	return this.getView(_searchContent);
}
/**
 * 显示列表
 * 
 * @return {}
 */
SearchDocument.prototype.getView = function(_searchContent) {
	return new Ext.Panel({
		id : 'SearchDocument',
		title : '搜索文档',
		iconCls : 'menu-document',
		border : false,
		style : 'padding-bottom:10px;',
		autoScroll : true,
		items : [{
			region : 'center',
			anchor : '100%',
			items : [new Ext.FormPanel({
				id : 'ALLDocumentSearchForm',
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
							text : '请输入条件:'
						}, {
							xtype : 'textfield',
							name : 'content',
							width : 150
						}, {
							xtype : 'button',
							text : '查询',
							iconCls : 'search',
							handler : function() {
								var searchPanel = Ext
										.getCmp('ALLDocumentSearchForm');
								var gridPanel = Ext.getCmp('SearchDocumentGrid');
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
								var searchPanel = Ext
										.getCmp('ALLDocumentSearchForm');
								searchPanel.getForm().reset();
							}
						}]
			}), this.setup(_searchContent)]
		}]
	});
};
/**
 * 建立视图
 */
SearchDocument.prototype.setup = function(_searchContent) {
	return this.grid(_searchContent);
};
/**
 * 建立DataGrid
 */
SearchDocument.prototype.grid = function(_searchContent) {
	var cm = new Ext.grid.ColumnModel({
		columns : [new Ext.grid.RowNumberer(), {
					header : 'docId',
					dataIndex : 'docId',
					hidden : true
				}, {
					header : '文档名称',
					dataIndex : 'docName',
					width : 120
				},
				// {
				// header : '内容',
				// dataIndex : 'content',
				// width:120
				// },
				{
					header : '创建人',
					dataIndex : 'fullname'
				}, {
					header : '创建时间',
					dataIndex : 'createtime'
				}, {
					header : '属性',
					width : 40,
					dataIndex : 'isShared',
					renderer : function(value, metadata, record) {
						var isPublic = record.data.isPublic;
						if (value == 1) {
							return '<img src="'
									+ __ctxPath
									+ '/images/flag/shared.png" alt="共享" title="共享文档" />';
						} else {
							if (isPublic == '0') {
								return '<img src="'
										+ __ctxPath
										+ '/images/flag/lock.png" alt="私有" title="私有文档" />';
							} else {
								return '<img src="'
										+ __ctxPath
										+ '/images/btn/flow/unlockTask.png" alt="公共" title="公共文档" />';
							}
						}
					}
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
				}],
		defaults : {
			sortable : true,
			menuDisabled : false,
			width : 100
		}
	});

	var store = this.store();
	store.load({
				params : {
					start : 0,
					limit : 25,
					content : _searchContent
				}
			});
	var grid = new Ext.grid.GridPanel({
				id : 'SearchDocumentGrid',
				store : store,
				trackMouseOver : true,
				disableSelection : false,
				loadMask : true,
				autoHeight : true,
				maxHeight : 600,
				cm : cm,
				viewConfig : {
					forceFit : true,
					enableRowBody : false,
					showPreview : false
				},
				bbar : new HT.PagingBar({store : store,pageSize : 12})
			});

	grid.addListener('rowdblclick', function(grid, rowindex, e) {
				grid.getSelectionModel().each(function(rec) {
							var id = rec.data.docId;
							var docName = rec.data.docName;
							var tabs = Ext.getCmp('centerTabPanel');
							var panel = Ext.getCmp('PulicDocumentDetail');
							if (panel == null) {
								panel = new PublicDocumentDetail({docId:id, docName:docName});
								Ext.getCmp('PublicDocumentTopBar').hide();
								tabs.add(panel);
								tabs.activate(panel);
							} else {
								tabs.remove('PulicDocumentDetail');
								panel = new PublicDocumentDetail({docId:id, docName:docName});
								Ext.getCmp('PublicDocumentTopBar').hide();
								tabs.add(panel);
								tabs.activate(panel);
							}
						});
			});
	return grid;

};

/**
 * 初始化数据
 */
SearchDocument.prototype.store = function() {
	var store = new Ext.data.Store({
				proxy : new Ext.data.HttpProxy({
							url : __ctxPath + '/document/searchDocument.do'
						}),
				reader : new Ext.data.JsonReader({
							root : 'result',
							totalProperty : 'totalCounts',
							id : 'id',
							fields : [{
										name : 'docId',
										type : 'int'
									}, 'docName', 'fullname', {
										name : 'isPublic',
										mapping : 'docFolder.isShared'
									}, 'content', 'createtime', 'haveAttach',
									'attachFiles', 'isShared']
						}),
				remoteSort : true
			});
	store.setDefaultSort('docId', 'desc');
	return store;
};
