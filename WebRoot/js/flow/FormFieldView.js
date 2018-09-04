/**
 * @author:
 * @class FormFieldView
 * @extends Ext.Panel
 * @description [FormField]管理
 * @company 北京互融时代软件有限公司
 * @createtime:
 */
FormFieldView = Ext.extend(Ext.Panel, {
			// 构造函数
			constructor : function(_cfg) {
				Ext.applyIf(this, _cfg);
				// 初始化组件
				this.initUIComponents();
				// 调用父类构造
				FormFieldView.superclass.constructor.call(this, {
							id : 'FormFieldView',
							title : '[FormField]管理',
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
										fieldLabel : '',
										name : 'Q_tableId_L_EQ',
										flex : 1,
										xtype : 'numberfield'
									}, {
										fieldLabel : '',
										name : 'Q_fieldName_S_EQ',
										flex : 1,
										xtype : 'textfield'
									}, {
										fieldLabel : '',
										name : 'Q_fieldType_S_EQ',
										flex : 1,
										xtype : 'textfield'
									}, {
										fieldLabel : '',
										name : 'Q_isRequired_SN_EQ',
										flex : 1,
										xtype : 'numberfield'
									}, {
										fieldLabel : '',
										name : 'Q_fieldSize_N_EQ',
										flex : 1,
										xtype : 'numberfield'
									}, {
										fieldLabel : '',
										name : 'Q_fieldDscp_S_EQ',
										flex : 1,
										xtype : 'textfield'
									}, {
										fieldLabel : '',
										name : 'Q_isPrimary_SN_EQ',
										flex : 1,
										xtype : 'numberfield'
									}, {
										fieldLabel : '',
										name : 'Q_foreignKey_S_EQ',
										flex : 1,
										xtype : 'textfield'
									}, {
										fieldLabel : '',
										name : 'Q_foreignTable_S_EQ',
										flex : 1,
										xtype : 'textfield'
									}, {
										fieldLabel : '',
										name : 'Q_isList_SN_EQ',
										flex : 1,
										xtype : 'numberfield'
									}, {
										fieldLabel : '',
										name : 'Q_isQuery_SN_EQ',
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
										text : '添加[FormField]',
										xtype : 'button',
										scope : this,
										handler : this.createRs
									}, {
										iconCls : 'btn-del',
										text : '删除[FormField]',
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
					id : 'FormFieldGrid',
					url : __ctxPath + "/flow/listFormField.do",
					fields : [{
								name : 'fieldId',
								type : 'int'
							}, 'tableId', 'fieldName', 'fieldType',
							'isRequired', 'fieldSize', 'fieldDscp',
							'isPrimary', 'foreignKey', 'foreignTable',
							'isList', 'isQuery'],
					columns : [{
								header : 'fieldId',
								dataIndex : 'fieldId',
								hidden : true
							}, {
								header : '',
								dataIndex : 'tableId'
							}, {
								header : '',
								dataIndex : 'fieldName'
							}, {
								header : '',
								dataIndex : 'fieldType'
							}, {
								header : '',
								dataIndex : 'isRequired'
							}, {
								header : '',
								dataIndex : 'fieldSize'
							}, {
								header : '',
								dataIndex : 'fieldDscp'
							}, {
								header : '',
								dataIndex : 'isPrimary'
							}, {
								header : '',
								dataIndex : 'foreignKey'
							}, {
								header : '',
								dataIndex : 'foreignTable'
							}, {
								header : '',
								dataIndex : 'isList'
							}, {
								header : '',
								dataIndex : 'isQuery'
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
							new FormFieldForm({
										fieldId : rec.data.fieldId
									}).show();
						});
			},
			// 创建记录
			createRs : function() {
				new FormFieldForm().show();
			},
			// 按ID删除记录
			removeRs : function(id) {
				$postDel({
							url : __ctxPath + '/flow/multiDelFormField.do',
							ids : id,
							grid : this.gridPanel
						});
			},
			// 把选中ID删除
			removeSelRs : function() {
				$delGridRs({
							url : __ctxPath + '/flow/multiDelFormField.do',
							grid : this.gridPanel,
							idName : 'fieldId'
						});
			},
			// 编辑Rs
			editRs : function(record) {
				new FormFieldForm({
							fieldId : record.data.fieldId
						}).show();
			},
			// 行的Action
			onRowAction : function(grid, record, action, row, col) {
				switch (action) {
					case 'btn-del' :
						this.removeRs.call(this, record.data.fieldId);
						break;
					case 'btn-edit' :
						this.editRs.call(this, record);
						break;
					default :
						break;
				}
			}
		});
