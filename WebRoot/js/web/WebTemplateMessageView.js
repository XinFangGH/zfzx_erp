/**
 * @author
 * @class WebTemplateMessageView
 * @extends Ext.Panel
 * @description [WebTemplateMessage]管理
 * @company 智维软件
 * @createtime:
 */
WebTemplateMessageView = Ext.extend(Ext.Panel, {
			type : 0,
			titlePrefix : "",
			tabIconCls : "",
			// 构造函数
			constructor : function(_cfg) {
				
				
					if (typeof(_cfg.type) != "undefined") {
						this.type = parseInt(_cfg.type);
					}
					switch (this.type) {
						
						case 1 :
							this.titlePrefix = "邮件模板";
							this.tabIconCls = "btn-tree-team18";
							
							break;
						case 2 :
							this.titlePrefix = "短信模板";
							this.tabIconCls = "btn-tree-team19";
							this.isHiddenCd = false;
							this.dateLabel = "完成时间";
							
							break;
						case 3 :
							this.titlePrefix = "站内信模板";
							this.tabIconCls = "btn-tree-team20";
							this.isHiddenSd = false;
							this.isHiddenAn = false;
							this.dateLabel = "终止时间";
							
							break;
					
					}
				
				Ext.applyIf(this, _cfg);
				// 初始化组件
				this.initUIComponents();
				// 调用父类构造
				WebTemplateMessageView.superclass.constructor.call(this, {
							id : 'WebTemplateMessageView'+this.type,
							title : this.titlePrefix+'管理',
							region : 'center',
							layout : 'border',
							items : [this.searchPanel, this.gridPanel]
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
										name : 'Q_type_N_EQ',
										xtype : 'hidden',
										value : this.type
									},{
										fieldLabel : '模板名称',
										name : 'Q_name_S_LK',
										flex : 1,
										xtype : 'textfield'
									}, {
										fieldLabel : '模板内容',
										name : 'Q_content_S_LK',
										flex : 1,
										xtype : 'textfield'
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
										text : '添加',
										xtype : 'button',
										scope : this,
										handler : this.createRs
									}, {
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
					id : 'WebTemplateMessageGrid',
					url : __ctxPath + "/web/listWebTemplateMessage.do",
					baseParams : {
						'Q_type_N_EQ' : this.type
					},
					fields : [{
								name : 'id',
								type : 'int'
							}, 'type', 'name', 'content', 'comment1',
							'comment2', 'comment3'],
					columns : [{
								header : 'id',
								dataIndex : 'id',
								hidden : true
							}, {
								header : '模板名称',
								dataIndex : 'name'
							}, {
								header : '模板内容',
								dataIndex : 'content'
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
							new WebTemplateMessageForm({
										id : rec.data.id
									}).show();
						});
			},
			// 创建记录
			createRs : function() {
				new WebTemplateMessageForm({
					type:this.type,
					titlePrefix:this.titlePrefix,
					panel : this.gridPanel
				}).show();
			},
			// 按ID删除记录
			removeRs : function(id) {
				$postDel({
							url : __ctxPath
									+ '/web/multiDelWebTemplateMessage.do',
							ids : id,
							grid : this.gridPanel
						});
			},
			// 把选中ID删除
			removeSelRs : function() {
				$delGridRs({
							url : __ctxPath
									+ '/web/multiDelWebTemplateMessage.do',
							grid : this.gridPanel,
							idName : 'id'
						});
			},
			// 编辑Rs
			editRs : function(record) {
				new WebTemplateMessageForm({
							id : record.data.id,
							type:this.type,
							titlePrefix:this.titlePrefix
						}).show();
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
