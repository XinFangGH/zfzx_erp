/**
 * @author:
 * @class PositionView
 * @extends Ext.Panel
 * @description [Position]管理
 * @company 北京互融时代软件有限公司
 * @createtime:
 */
PositionView = Ext.extend(Ext.Panel, {
			// 构造函数
			constructor : function(_cfg) {
				Ext.applyIf(this, _cfg);
				// 初始化组件
				this.initUIComponents();
				// 调用父类构造
				PositionView.superclass.constructor.call(this, {
							id : 'PositionView',
							iconCls : 'menu-position',
							title : '[Position]管理',
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
										fieldLabel : 'posName',
										name : 'Q_posName_S_EQ',
										flex : 1,
										xtype : 'textfield'
									}, {
										fieldLabel : 'posDesc',
										name : 'Q_posDesc_S_EQ',
										flex : 1,
										xtype : 'textfield'
									}, {
										fieldLabel : 'posSupId',
										name : 'Q_posSupId_L_EQ',
										flex : 1,
										xtype : 'numberfield'
									}, {
										fieldLabel : 'path',
										name : 'Q_path_S_EQ',
										flex : 1,
										xtype : 'textfield'
									}, {
										fieldLabel : 'depth',
										name : 'Q_depth_L_EQ',
										flex : 1,
										xtype : 'numberfield'
									}, {
										fieldLabel : 'orgId',
										name : 'Q_orgId_L_EQ',
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
										text : '添加[Position]',
										xtype : 'button',
										scope : this,
										handler : this.createRs
									}, {
										iconCls : 'btn-del',
										text : '删除[Position]',
										xtype : 'button',
										scope : this,
										handler : this.removeSelRs
									}]
						});

				this.gridPanel = new HT.GridPanel({
					region : 'center',
					tbar : this.topbar,
					//使用RowActions
					rowActions : true,
					id : 'PositionGrid',
					url : __ctxPath + "/system/listPosition.do",
					fields : [{
								name : 'posId',
								type : 'int'
							}, 'posName', 'posDesc', 'posSupId', 'path', 'depth', 'orgId'],
					columns : [{
								header : 'posId',
								dataIndex : 'posId',
								hidden : true
							}, {
								header : 'posName',
								dataIndex : 'posName'
							}, {
								header : 'posDesc',
								dataIndex : 'posDesc'
							}, {
								header : 'posSupId',
								dataIndex : 'posSupId'
							}, {
								header : 'path',
								dataIndex : 'path'
							}, {
								header : 'depth',
								dataIndex : 'depth'
							}, {
								header : 'orgId',
								dataIndex : 'orgId'
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
						//end of columns
					});

				this.gridPanel.addListener('rowdblclick', this.rowClick);

			},// end of the initComponents()
			//重置查询表单
			reset : function() {
				this.searchPanel.getForm().reset();
			},
			//按条件搜索
			search : function() {
				$search({
							searchPanel : this.searchPanel,
							gridPanel : this.gridPanel
						});
			},
			//GridPanel行点击处理事件
			rowClick : function(grid, rowindex, e) {
				grid.getSelectionModel().each(function(rec) {
							new PositionForm({
										posId : rec.data.posId
									}).show();
						});
			},
			//创建记录
			createRs : function() {
				new PositionForm().show();
			},
			//按ID删除记录
			removeRs : function(id) {
				$postDel({
							url : __ctxPath + '/system/multiDelPosition.do',
							ids : id,
							grid : this.gridPanel
						});
			},
			//把选中ID删除
			removeSelRs : function() {
				$delGridRs({
							url : __ctxPath + '/system/multiDelPosition.do',
							grid : this.gridPanel,
							idName : 'posId'
						});
			},
			//编辑Rs
			editRs : function(record) {
				new PositionForm({
							posId : record.data.posId
						}).show();
			},
			//行的Action
			onRowAction : function(grid, record, action, row, col) {
				switch (action) {
					case 'btn-del' :
						this.removeRs.call(this, record.data.posId);
						break;
					case 'btn-edit' :
						this.editRs.call(this, record);
						break;
					default :
						break;
				}
			}
		});
