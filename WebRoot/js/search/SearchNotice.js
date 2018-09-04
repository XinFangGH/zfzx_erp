Ext.ns('SearchNotice');
/**
 * 新闻列表
 */
var SearchNotice = function(_searchContent) {
	return this.getView(_searchContent);
}
/**
 * 显示列表
 * 
 * @return {}
 */
SearchNotice.prototype.getView = function(_searchContent) {
	return new Ext.Panel({
		id : 'SearchNotice',
		title : '搜索公告',
		iconCls:'menu-news',
		border:false,
		style:'padding-bottom:10px;',
		autoScroll : true,
		items : [{
					region : 'center',
					anchor :'100%',
					items : [new Ext.FormPanel({
							id : 'ALLNewsSearchForm',
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
										name : 'searchContent',
										width : 150
									},{
										xtype : 'button',
										text : '查询',
										iconCls : 'search',
										handler : function() {
											var searchPanel = Ext.getCmp('ALLNewsSearchForm');
											var gridPanel = Ext.getCmp('SearchNoticeGrid');
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
													.getCmp('ALLNewsSearchForm');
											searchPanel.getForm().reset();
										}
									},{
										name : 'isNotice',
										value : 1,
										xtype : 'hidden'
									}]
						}),this.setup(_searchContent)]
				}]
	});
};
/**
 * 建立视图
 */
SearchNotice.prototype.setup = function(_searchContent) {
	return this.grid(_searchContent);
};
/**
 * 建立DataGrid
 */
SearchNotice.prototype.grid = function(_searchContent) {
	var cm = new Ext.grid.ColumnModel({
		columns : [ new Ext.grid.RowNumberer(), {
					header : 'newsId',
					dataIndex : 'newsId',
					hidden : true
				},
				{
					header : '新闻标题',
					width : 120,
					dataIndex : 'subject'
				}, {
					header : '作者',
					width : 120,
					dataIndex : 'author'
				}, {
					header : '创建时间',
					dataIndex : 'createtime',
					renderer : function(value){
						if(value !=null){
							return value.substring(0,10);
						}
					}
				}, {
					header : '回复次数',
					width : 120,
					dataIndex : 'replyCounts'
				}, {
					header : '浏览数',
					width : 120,
					dataIndex : 'viewCounts'
				}, {
					header : '状态',
					width : 120,
					dataIndex : 'status',
					renderer : function(value) {
						if(value !=null && value == 0){
							return '<font color="red">禁用</font>';
						}else if(value == 1){
							return '<font color="green">激活</font>';
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
					limit : 7,
					searchContent:_searchContent
				}
			});
	var grid = new Ext.grid.GridPanel({
				id : 'SearchNoticeGrid',
				store : store,
				trackMouseOver : true,
				disableSelection : false,
				autoScroll : true,
				loadMask : true,
				autoHeight : true,
				sortable : false,
				cm : cm,
				viewConfig : {
					forceFit : true,
					enableRowBody : false,
					showPreview : false
				},
				bbar : new HT.PagingBar({store : store,pageSize : 7})
			});

	grid.addListener('rowdblclick', function(grid, rowindex, e) {
				grid.getSelectionModel().each(function(rec) {
							App.clickTopTab('NewsDetail',rec.data.newsId,function(){
										AppUtil.removeTab('NewsDetail');
									});
						});
			});
	return grid;

};

/**
 * 初始化数据
 */
SearchNotice.prototype.store = function() {
	var store = new Ext.data.Store({
				baseParams : {
								'isNotice' : 1
							},
				proxy : new Ext.data.HttpProxy({
							url : __ctxPath + '/info/searchNews.do'
						}),
				reader : new Ext.data.JsonReader({
							root : 'result',
							totalProperty : 'totalCounts',
							id : 'id',
							fields : [{
										name : 'newsId',
										type : 'int'
									},'sectionId', 'subjectIcon', 'subject', 'author',
									'createtime', 'expTime', 'replyCounts',
									'viewCounts', 'issuer', 'content', 'updateTime',
									'status', 'isDeskImage', 'isNotice', 'sn','section']
						}),
				remoteSort : true
			});
	store.setDefaultSort('newsId', 'desc');
	return store;
};
