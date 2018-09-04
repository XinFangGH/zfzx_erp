/**
 * @author YungLocke
 * @class FlowEntityView
 * @extends Ext.Panel
 */
ProInstanceMgr = Ext.extend(Ext.Panel, {
			// 构造函数
			constructor : function(_cfg) {
				Ext.applyIf(this, _cfg);
				// 初始化组件
				this.initUIComponents();
				// 调用父类构造
				ProInstanceMgr.superclass.constructor.call(this, {
							id : 'ProInstanceMgr',
							iconCls:'menu-instance',
							title : '流程实例管理',
							region : 'center',
							layout : 'border',
							items : [this.searchPanel, this.gridPanel]
						});
			},// end of constructor
			// 初始化组件
			initUIComponents : function() {
				// 初始化搜索条件Panel
				this.searchPanel = new Ext.FormPanel({
					id : 'proInstanceMgrSearchPanel',
					layout : 'form',
					region : 'north',
					width : '100%',
					height : 40,
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
						xtype : 'container',
						layout : 'column',
						style : 'padding-left:5px;padding-right:5px;padding-top:5px;',
						layoutConfig : {
							align : 'middle',
							padding : '5px'
						},
						items : [{
							columnWidth : .3,
							xtype : 'container',
							layout : 'form',
							items : [{
								width : '95%',
								fieldLabel : '流程名称',
								name : 'proDefinition.name',
								xtype : 'textfield'
							}]
						}, {
							columnWidth : .3,
							width : 100,
							xtype : 'container',
							layout : 'column',
							defaults : {
								xtype : 'button'
							},
							items : [{
								text : '查询',
								scope : this,
								iconCls : 'btn-search',
								handler : this.search
							}, {
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
						iconCls : 'btn-refresh',
						text : '刷新',
						xtype : 'button',
						scope : this,
						handler : this.refreshRs
					}, '-', {
						iconCls : 'btn-detail',
						text : '查看',
						xtype : 'button',
						scope : this,
						handler : this.detailRsM
					}]
				});

				this.gridPanel = new HT.GridPanel({
					region : 'center',
					tbar : this.topbar,
					// 使用RowActions
					rowActions : true,
					url : __ctxPath + "/flow/inListProDefinition.do",
					fields : [{
										name : 'defId',
										type : 'int'
									}, 'typeName', 'name','subTotal',
									'createtime', 'deployId','status'],
					columns : [{
								header : 'defId',
								dataIndex : 'defId',
								hidden : true
							},{
								header : '分类名称',
								dataIndex : 'typeName',
								renderer : function(value) {
									if (value != null&&value != '')
										return value;
									else
										return '<font color=\'red\'>未定义</font>';
								}
							},{
							    header:'流程名称',
							    dataIndex:'name'
							},{
							    header:'正在运行的实例数目',
							    dataIndex:'subTotal',
							    renderer :function(value){
							         return '<font color="red">'+value+'</font>';
							    }
							},{
							    header:'创建时间',
							    dataIndex:'createtime'
							},  new Ext.ux.grid.RowActions({
										header : '管理',
										width : 32,
										fixed:true,
					    				resizable:false,
										actions : [{
													iconCls : 'btn-detail',
													qtip : '查看',
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
				grid.getSelectionModel().each(function(record) {
							var defId=record.data.defId;
							var name=record.data.name;
						    var contentPanel = App.getContentPanel();
							var startForm = contentPanel.getItem('ProcessRunList' + defId);
						
							if (startForm == null) {
								startForm = new ProInstanceView({
											id : 'ProcessRunList' + defId,
											defId : defId,
											flowName : name
										});
								contentPanel.add(startForm);
							}
							contentPanel.activate(startForm);
						});
			},
			// 刷新记录
			refreshRs : function() {
				this.gridPanel.getStore().reload();
			},
			detailRsM : function() {
				var selRs = this.gridPanel.getSelectionModel().getSelections();
				if(selRs.length==0){
				   Ext.ux.Toast.msg('操作信息','请选择记录！');
				   return;
				}
				if(selRs.length>1){
				   Ext.ux.Toast.msg('操作信息','只能选择一条记录！');
				   return;
				}
				this.detailRs(selRs[0]);
			},
			detailRs:function(record){
				var defId=record.data.defId;
				var name=record.data.name;
			    var contentPanel = App.getContentPanel();
				var startForm = contentPanel.getItem('ProcessRunList' + defId);
			
				if (startForm == null) {
					startForm = new ProInstanceView({
								id : 'ProcessRunList' + defId,
								defId : defId,
								flowName : name
							});
					contentPanel.add(startForm);
				}
				contentPanel.activate(startForm);
			},
			// 行的Action
			onRowAction : function(grid, record, action, row, col) {
				switch (action) {
					case 'btn-detail' :
						this.detailRs.call(this, record);
						break;
					default :
						break;
				}
			}
		});