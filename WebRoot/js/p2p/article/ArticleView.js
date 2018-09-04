/**
 * @author
 * @class ArticleView
 * @extends Ext.Panel
 * @description [Article]管理
 * @company 智维软件
 * @createtime:
 */
ArticleView = Ext.extend(Ext.Panel, {
			// 构造函数
			constructor : function(_cfg) {
				Ext.applyIf(this, _cfg);
				// 初始化组件
				this.initUIComponents();
				// 调用父类构造
				ArticleView.superclass.constructor.call(this, {
							id : 'ArticleView',
							title : '文章分类管理',
							region : 'center',
							layout : 'border',
							iconCls:"menu-finance",
							items : [/* this.searchPanel, */this.gridPanel]
						});
			},// end of constructor
			// 初始化组件
			initUIComponents : function() {
				// 初始化搜索条件Panel
				this.searchPanel = new HT.SearchPanel({
							layout : 'form',
							region : 'north',
							colNums : 3,
							items : [{
										fieldLabel : 'createDate',
										name : 'Q_createDate_D_EQ',
										flex : 1,
										xtype : 'datefield',
										format : 'Y-m-d'
									}, {
										fieldLabel : 'modifyDate',
										name : 'Q_modifyDate_D_EQ',
										flex : 1,
										xtype : 'datefield',
										format : 'Y-m-d'
									}, {
										fieldLabel : 'author',
										name : 'Q_author_S_EQ',
										flex : 1,
										xtype : 'textfield'
									}, {
										fieldLabel : 'content',
										name : 'Q_content_S_EQ',
										flex : 1,
										xtype : 'textfield'
									}, {
										fieldLabel : 'hits',
										name : 'Q_hits_N_EQ',
										flex : 1,
										xtype : 'numberfield'
									}, {
										fieldLabel : 'htmlFilePath',
										name : 'Q_htmlFilePath_S_EQ',
										flex : 1,
										xtype : 'textfield'
									}, {
										fieldLabel : 'isPublication',
										name : 'Q_isPublication_S_EQ',
										flex : 1,
										xtype : 'textfield'
									}, {
										fieldLabel : 'isRecommend',
										name : 'Q_isRecommend_S_EQ',
										flex : 1,
										xtype : 'textfield'
									}, {
										fieldLabel : 'isTop',
										name : 'Q_isTop_S_EQ',
										flex : 1,
										xtype : 'textfield'
									}, {
										fieldLabel : 'metaDescription',
										name : 'Q_metaDescription_S_EQ',
										flex : 1,
										xtype : 'textfield'
									}, {
										fieldLabel : 'metaKeywords',
										name : 'Q_metaKeywords_S_EQ',
										flex : 1,
										xtype : 'textfield'
									}, {
										fieldLabel : 'pageCount',
										name : 'Q_pageCount_N_EQ',
										flex : 1,
										xtype : 'numberfield'
									}, {
										fieldLabel : 'title',
										name : 'Q_title_S_EQ',
										flex : 1,
										xtype : 'textfield'
									}, {
										fieldLabel : 'articleCategoryId',
										name : 'Q_articleCategoryId_L_EQ',
										flex : 1,
										xtype : 'numberfield'
									}],
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

				this.topbar = new Ext.Toolbar({
							items : [{
										iconCls : 'btn-add',
										text : '添加文章',
										xtype : 'button',
										scope : this,
										handler : this.createRs
									}, {
										iconCls : 'btn-del',
										text : '删除文章',
										xtype : 'button',
										scope : this,
										handler : this.removeSelRs
									}]
						});
              var summary = new Ext.ux.grid.GridSummary();
				function totalMoney(v, params, data) {
					return '总计';
				}
				this.gridPanel = new HT.GridPanel({
					region : 'center',
					tbar : this.topbar,
					plugins : [summary],
					// 使用RowActions
					rowActions : true,
					id : 'ArticleGrid',
					url : __ctxPath + "/p2p/listArticle.do",
					fields : [{
								name : 'id',
								type : 'int'
							}, {
								name : 'name',
								mapping : 'articlecategory.name'
							}, {
								name : 'webKey',
								mapping : 'articlecategory.webKey'
							}, 'createDate', 'modifyDate', 'author', 'content',
							'hits', 'htmlFilePath', 'isPublication',
							'isRecommend', 'isTop', 'metaDescription',
							'metaKeywords', 'pageCount', 'title',
							'articleCategoryId'],
					columns : [{
								header : 'id',
								dataIndex : 'id',
								hidden : true,
                        		sortable : true
							}, {
								header : '站点类别',
								align:'center',
								dataIndex : 'webKey',
								renderer:function(value){
									if(value=="YunGou"){
										return "云购";
									}else if(value=="P2P"){
										return "互联网金融";
									}else if(value=="Crowdfunding"){
										return "众筹";
									}else{
										return value;
									}
								}
							}, {
								header : '分类名称',
								align:'center',
								dataIndex : 'name'
							}, {
								header : '标题',
								align:'center',
								summaryRenderer : totalMoney,
								dataIndex : 'title'
							}, {
								header : '创建时间',
								align:'center',
								dataIndex : 'createDate',
								sortable : true
							}, {
								header : '修改时间',
								align:'center',
								dataIndex : 'modifyDate',
								sortable : true
							}, {
								header : '作者',
								align:'center',
								dataIndex : 'author'
							}, {
								header : '点击数',
								align:'center',
								summaryType : 'sum',
								renderer : function(v) {
					                 return v+"次";
				                   },
								dataIndex : 'hits'
							}, {
								header : '是否发布',
								align:'center',
								dataIndex : 'isPublication',
								renderer : function(v) {
									if (v == "0")
										return "否";
									if (v == "1")
										return "是";
								}
							}, {
								header : '是否推荐',
								align:'center',
								dataIndex : 'isRecommend',
								renderer : function(v) {
									if (v == "0")
										return "否";
									if (v == "1")
										return "是";
								}
							}, {
								header : '是否置顶',
								align:'center',
								dataIndex : 'isTop',
								renderer : function(v) {
									if (v == "0")
										return "否";
									if (v == "1")
										return "是";
								}
							}, new Ext.ux.grid.RowActions({
										header : '管理',
										align:'center',
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
							new ArticleForm({
										id : rec.data.id
									}).show();
						});
			},
			// 创建记录
			createRs : function( ) {
				new ArticleForm({
				type:this.type,
				single:this.single
				}
				).show();
			},
			// 按ID删除记录
			removeRs : function(id) {
				$postDel({
							url : __ctxPath + '/p2p/multiDelArticle.do',
							ids : id,
							grid : this.gridPanel
						});
			},
			// 把选中ID删除
			removeSelRs : function() {
				$delGridRs({
							url : __ctxPath + '/p2p/multiDelArticle.do',
							grid : this.gridPanel,
							idName : 'id'
						});
			},
			// 编辑Rs
			editRs : function(record) {
				new ArticleForm({
							id : record.data.id,
							name : record.data.name,
							type:this.type,
				            single:this.single
						}).show();
			},
			preview : function(record) {
				window.open(__p2pPath + record.data.htmlFilePath);
			},
			// 行的Action
			onRowAction : function(grid, record, action, row, col) {
				switch (action) {
					case 'btn-del' :
						this.removeRs.call(this, record.data.id);
						break;
					case 'btn-edit' :
						this.editRs.call(this, record);
						break;
					default :
						break;
				}
			}
		});
