/**
 * @author:
 * @class ProcessModuleView
 * @extends Ext.Panel
 * @description 流程模块管理
 * @company 北京互融时代软件有限公司
 * @createtime:
 */
ProcessModuleView = Ext.extend(Ext.Panel, {
			// 构造函数
			constructor : function(_cfg) {
				Ext.applyIf(this, _cfg);
				// 初始化组件
				this.initUIComponents();
				// 调用父类构造
				ProcessModuleView.superclass.constructor.call(this, {
					id : 'ProcessModuleView',
					title : '流程模块管理',
					iconCls:'menu-proModule',
					region : 'center',
					layout : 'border',
					items : [this.searchPanel, this.gridPanel]
				});
			},// end of constructor
			// 初始化组件
			initUIComponents : function() {
				// 初始化搜索条件Panel
				this.searchPanel = new Ext.FormPanel({
					id : 'processModuleSearchPanel',
					layout : 'form',
					region : 'north',
					height : 60,
					width : '100%',
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
						layout : 'column',
						style : 'padding-left:5px;padding-right:5px;padding-top:5px;',
						layoutConfig : {
							align : 'middle',
							padding : '5px'
						},
						defaults : {
							xtype : 'label'
						},
						items : [{
							columnWidth : .3,
							xtype : 'container',
							layout : 'form',
							items : [{
								width : '95%',
								fieldLabel : '模块名称',
								name : 'Q_modulename_S_LK',
								xtype : 'textfield',
								maxLength : 125
							}, {
								width : '95%',
								fieldLabel : '模块KEY',
								name : 'Q_modulekey_S_LK',
								xtype : 'textfield',
								maxLength : 125
							}]
						}, {
							columnWidth : .3,
							xtype : 'container',
							layout : 'form',
							items : [{
								width : '95%',
								fieldLabel : '流程KEY',
								name : 'Q_processkey_S_LK',
								xtype : 'textfield',
								maxLength : 125
							}, {
								xtype : 'container',
								layout : 'column',
								border : false,
								fieldLabel : '创建时间',
								items : [{
									columnWidth : .49,
									name : 'Q_createtime_D_GE',
									xtype : 'datefield',
									format : 'Y-m-d'
								}, {
									xtype : 'label',
									text : '至',
									style : 'padding-top:3px'
								}, {
									columnWidth : .49,
									name : 'Q_createtime_D_LE',
									xtype : 'datefield',
									format : 'Y-m-d'
								}]
							}]
						}, {
							columnWidth : .3,
							xtype : 'container',
							layout : 'form',
							items : [{
								width : '95%',
								fieldLabel : '创建人',
								name : 'Q_creator_S_LK',
								xtype : 'textfield',
								maxLength : 125
							}, {
								width : '95%',
								fieldLabel : '描述',
								name : 'Q_descp_S_LK',
								xtype : 'textfield',
								maxLength : 125
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
								scope : this,
								iconCls : 'btn-search',
								handler : this.search
							}, {
								style : 'padding-top:3px;',
								text : '重置',
								scope : this,
								iconCls : 'reset',
								handler : this.reset
							}]
						}]
					}]
				});
				
				this.topbar = new Ext.Toolbar({
					items : [{
						iconCls : 'btn-add',
						text : '添加',
						scope : this,
						hidden : isGranted('_addlcmk')?false:true,
						handler : this.createRs
					}, '-', {
						iconCls : 'btn-del',
						text : '删除',
						scope : this,
						hidden : isGranted('_removelcmk')?false:true,
						handler : this.removeSelRs
					}]
				});

				this.gridPanel = new HT.GridPanel({
					region : 'center',
					tbar : this.topbar,
					// 使用RowActions
					rowActions : true,
					id : 'ProcessModuleGrid',
					url : __ctxPath + "/flow/listProcessModule.do",
					fields : [{
								name : 'moduleid',
								type : 'int'
							}, 'modulename', 'modulekey', 'descp', 'proDefinition',
							'processkey', 'creator', 'createtime'],
					columns : [{
								header : 'moduleid',
								dataIndex : 'moduleid',
								hidden : true
							}, {
								header : '模块名称',
								dataIndex : 'modulename'
							}, {
								header : '模块key',
								dataIndex : 'modulekey'
							}, {
								header : '描述',
								dataIndex : 'descp'
							}, {
								header : 'defId',
								dataIndex : 'proDefinition',
								renderer : function(value){
									if(value){
										return value.defId;
									}
									return '';
								}
							}, {
								header : '流程key',
								dataIndex : 'processkey'
							}, {
								header : '创建人',
								dataIndex : 'creator'
							}, {
								header : '创建时间',
								dataIndex : 'createtime'
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
							new ProcessModuleForm({
										moduleid : rec.data.moduleid
									}).show();
						});
			},
			// 创建记录
			createRs : function() {
				new ProcessModuleForm().show();
			},
			// 按ID删除记录
			removeRs : function(id) {
				$postDel({
							url : __ctxPath + '/flow/multiDelProcessModule.do',
							ids : id,
							grid : this.gridPanel
						});
			},
			// 把选中ID删除
			removeSelRs : function() {
				$delGridRs({
							url : __ctxPath + '/flow/multiDelProcessModule.do',
							grid : this.gridPanel,
							idName : 'moduleid'
						});
			},
			// 编辑Rs
			editRs : function(record) {
				new ProcessModuleForm({
							moduleid : record.data.moduleid
						}).show();
			},
			// 行的Action
			onRowAction : function(grid, record, action, row, col) {
				switch (action) {
					case 'btn-del' :
						this.removeRs.call(this, record.data.moduleid);
						break;
					case 'btn-edit' :
						this.editRs.call(this, record);
						break;
					default :
						break;
				}
			}
		});
