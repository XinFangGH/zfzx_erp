/**
 * @author:
 * @class NoticeView
 * @extends Ext.Panel
 * @description 公告管理
 * @company 北京互融时代软件有限公司
 * @createtime:
 */
NoticeView = Ext.extend(Ext.Panel, {
			// 构造函数
			constructor : function(_cfg) {
				Ext.applyIf(this, _cfg);
				// 初始化组件
				this.initUIComponents();
				// 调用父类构造
				NoticeView.superclass.constructor.call(this, {
							id : 'NoticeView',
							title : '公告管理',
							region : 'center',
							iconCls : 'menu-notice',
							layout : 'border',
							items : [this.searchPanel, this.gridPanel]
						});
			},// end of constructor
			// 初始化组件
			initUIComponents : function() {
				// 初始化搜索条件Panel
				this.searchPanel = new Ext.FormPanel({
					id : 'newsSearchPanel',
					layout : 'form',
					region : 'north',
					width : '100%',
					height : 60,
					keys : [{
						key : Ext.EventObject.ENTER,
						fn : this.search,
						scope : this
					}, {
						key : Ext.EventObject.ESC,
						fn : this.reset,
						scope : this
					}],
					items : [{
						border : false,
						width : '100%',
						layout : 'column',
						autoScroll : true,
						style : 'padding-top:5px;padding-left:5px;padding-right:5px;',
						layoutConfig : {
							align : 'middle',
							padding : '5'
						},
						items : [{
							columnWidth : .2,
							xtype : 'container',
							layout : 'form',
							items : [{
								width : '90%',
								fieldLabel : '所属栏目',
								name : 'Q_section.sectionName_S_LK',
								xtype : 'textfield',
								maxLength : 125
							}, {
								width : '90%',
								fieldLabel : '新闻标题',
								name : 'Q_subject_S_LK',
								xtype : 'textfield',
								maxLength : 125
							}]
						}, {
							columnWidth : .3,
							xtype : 'container',
							layout : 'form',
							items : [{
								border : false,
								xtype : 'container',
								layout : 'column',
								fieldLabel : '创建时间',
								items : [{
									columnWidth : .49,
									name : 'Q_createtime_D_GE',
									xtype : 'datefield',
									format : 'Y-m-d'
								}, {
									xtype : 'label',
									text : '至',
									style : 'margin-top:3px;'
								}, {
									columnWidth : .49,
									name : 'Q_createtime_D_LE',
									xtype : 'datefield',
									format : 'Y-m-d'
								}]
							}, {
								xtype : 'container',
								layout : 'column',
								border : false,
								fieldLabel : '失效时间',
								items : [{
									columnWidth : .49,
									name : 'Q_expTime_D_GE',
									xtype : 'datefield',
									format : 'Y-m-d'
								}, {
									xtype : 'label',
									text : '至',
									style : 'margin-top:3px'
								}, {
									columnWidth : .49,
									name : 'Q_expTime_D_LE',
									xtype : 'datefield',
									format : 'Y-m-d'
								}]
							}]
						}, {
							columnWidth : .2,
							xtype : 'container',
							layout : 'form',
							items : [{
								width : '90%',
								fieldLabel : '作者',
								name : 'Q_author_S_LK',
								xtype : 'textfield',
								maxLength : 125
							}, {
								width : '90%',
								fieldLabel : '发布人',
								name : 'Q_issuer_S_LK',
								xtype : 'textfield',
								maxLength : 125
							}]
						}, {
							columnWidth : .2,
							xtype : 'container',
							layout : 'form',
							items : [{
								anchor : '99%',
								fieldLabel : '状态',
								hiddenName : 'Q_status_SN_EQ',
								xtype : 'combo',
								mode : 'local',
								triggerAction : 'all',
								editable : false,
								triggerAction : 'all',
								forceSelection : true,
								store : [['','全部'], ['0','禁用'], ['1','激活']]
							}, {
								xtype : 'label',
								text : '   '
							}]
						}, {
							columnWidth : .1,
							xtype : 'container',
							layout : 'form',
							defaults : {
								xtype : 'button'
							},
							items : [{
								text : '查询',
								iconCls : 'search',
								handler : this.search,
								scope : this
							}, {
								text : '重置',
								iconCls : 'reset',
								handler : this.reset,
								scope : this
							}]
						}]
					}]
				});

				this.topbar = new Ext.Toolbar({
					items : [{
						iconCls : 'btn-add',
						text : '添加',
						xtype : 'button',
						scope : this,
						handler : this.createRs
					}, '-', {
						iconCls : 'btn-del',
						text : '删除',
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
					id : 'NewsGrid',
					url : __ctxPath + "/info/listNews.do?Q_isNotice_SN_EQ=1",
					fields : [{
								name : 'newsId',
								type : 'int'
							}, 'sectionId', 'subjectIcon', 'subject', 'author',
							'createtime', 'expTime', 'replyCounts',
							'viewCounts', 'issuer', 'content', 'updateTime',
							'status', 'isDeskImage', 'isNotice', 'sn','section'],
					columns : [{
								header : 'newsId',
								dataIndex : 'newsId',
								hidden : true
							}, {
								header : '所在栏目',
								dataIndex : 'section',
								renderer : function(value){
									return value.sectionName;
								}
							}, {
								header : '公告标题',
								dataIndex : 'subject'
							}, {
								header : '作者',
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
								header : '失效时间',
								dataIndex : 'expTime',
								renderer : function(value){
									if(value !=null){
										return value.substring(0,10);
									}
								}
							}, {header : '发布人',
								dataIndex : 'issuer'
							}, {
								header : '公告状态',
								dataIndex : 'status',
								renderer : function(value){
									if(value !=null && value == 0){
										return '<font color="red">禁用</font>';
									}else if(value == 1){
										return '<font color="green">激活</font>';
									}
								}
							}, new Ext.ux.grid.RowActions({
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
							new NoticeForm({
										newsId : rec.data.newsId
									}).show();
						});
			},
			// 创建记录
			createRs : function() {
				new NoticeForm().show();
			},
			// 按ID删除记录
			removeRs : function(id) {
				$postDel({
							url : __ctxPath + '/info/multiDelNews.do',
							ids : id,
							grid : this.gridPanel
						});
			},
			// 把选中ID删除
			removeSelRs : function() {
				$delGridRs({
							url : __ctxPath + '/info/multiDelNews.do',
							grid : this.gridPanel,
							idName : 'newsId'
						});
			},
			// 编辑Rs
			editRs : function(record) {
				new NoticeForm({
							newsId : record.data.newsId
						}).show();
			},
			// 行的Action
			onRowAction : function(grid, record, action, row, col) {
				switch (action) {
					case 'btn-del' :
						this.removeRs.call(this, record.data.newsId);
						break;
					case 'btn-edit' :
						this.editRs.call(this, record);
						break;
					default :
						break;
				}
			}
		});
