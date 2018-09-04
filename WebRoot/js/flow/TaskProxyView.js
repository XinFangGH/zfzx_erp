/**
 * @author
 * @class TaskProxyView
 * @extends Ext.Panel
 * @description [TaskProxy]管理
 * @company 智维软件
 * @createtime:
 */
TaskProxyView = Ext.extend(Ext.Panel, {
		// 构造函数
		constructor : function(_cfg) {
			Ext.applyIf(this, _cfg);
			// 初始化组件
			this.initUIComponents();
			// 调用父类构造
			TaskProxyView.superclass.constructor.call(this, {
				title : '流程任务代理',
				region : 'center',
				layout : 'border',
				iconCls:"menu-flowManager",
				items : [this.searchPanel, this.gridPanel]
			});
		},// end of constructor
		// 初始化组件
		initUIComponents : function() {
			// 初始化搜索条件Panel
			this.searchPanel = new HT.SearchPanel({
				layout : 'column',
				region : 'north',
				border : false,
				height : 60,
				anchor : '100%',
				layoutConfig : {
					align : 'middle'
				},
				items : [{
					columnWidth : .2,
					layout : 'form',
					labelWidth : 80,
					labelAlign : 'right',
					border : false,
					items : [{
						xtype : 'textfield',
						fieldLabel : '被代理人',
						name : 'principalName'
					}/*{
						xtype : 'trigger',
						fieldLabel : '被代理人',
						name : 'principalName',
						triggerClass : 'x-form-search-trigger',
						editable : false,
						scope : this,
						onTriggerClick : function() {
							var obj = this;
							UserLevelSelector.getView(
								function(id, name) {
									obj.setValue(name);
									obj.nextSibling().setValue(id);
							}, true).show();// false为多选,true为单选。
						}
					},{
						xtype:'hidden',
						name:'principalId'
					}*/]
				}, {
					columnWidth : .2,
					layout : 'form',
					labelWidth : 80,
					labelAlign : 'right',
					border : false,
					items : [{
						xtype : 'textfield',
						fieldLabel : '代理人',
						name : 'agentName'
					}/*{
						xtype : 'trigger',
						fieldLabel : '代理人',
						name : 'agentName',
						triggerClass : 'x-form-search-trigger',
						editable : false,
						scope : this,
						onTriggerClick : function() {
							var obj = this;
							UserLevelSelector.getView(
								function(id, name) {
									obj.setValue(name);
									obj.nextSibling().setValue(id);
							}, true).show();// false为多选,true为单选。
						}
					},{
						xtype:'hidden',
						name:'agentId'
					}*/]
				}, {
					columnWidth : .15,
					layout : 'form',
					labelWidth : 80,
					labelAlign : 'right',
					border : false,
					items : [{
						xtype : 'combo',
						fieldLabel : '状态',
						hiddenName : 'status',
						displayField : 'displayText',
						valueField : 'value',
						triggerAction : 'all',
						mode : 'local',
						anchor:'100%',
						store : new Ext.data.SimpleStore({
							fields : ['displayText','value'],
							data : [['未过期',0],['已过期',1]]
						})
					}]
				},{
					columnWidth : .07,
					layout : 'form',
					border : false,
					items : [{
						text : '查询',
						xtype : 'button',
						scope : this,
						style : 'margin-left:20px',
						anchor : "100%",
						iconCls : 'btn-search',
						handler : this.search
					}]
				},{
					columnWidth : .07,
					layout : 'form',
					border : false,
					items : [{
						text : '重置',
						style : 'margin-left:20px',
						xtype : 'button',
						scope : this,
						anchor : "100%",
						iconCls : 'btn-reset',
						handler : this.reset
					}]
				}]
			});// end of searchPanel

			this.topbar = new Ext.Toolbar({
				items : [{
					iconCls : 'btn-add',
					text : '添加',
					xtype : 'button',
					scope : this,
					handler : this.createRs
				}, new Ext.Toolbar.Separator({
					hidden : (isGranted('_task_proxy_add') && isGranted('_task_proxy_modify')) ? false : true
				}),{
					iconCls : 'btn-edit',
					text : '修改',
					xtype : 'button',
					scope : this,
					readOnly:false,
					handler : this.editRs
				}, new Ext.Toolbar.Separator({
					hidden : (isGranted('_task_proxy_modify') && isGranted('_task_proxy_delete')) ? false : true
				}),{
					iconCls : 'btn-del',
					text : '删除',
					xtype : 'button',
					scope : this,
					handler : this.removeRs
				}, new Ext.Toolbar.Separator({
					hidden : (isGranted('_task_proxy_delete') && isGranted('_task_proxy_export')) ? false : true
				}),{
					iconCls : 'btn-xls',
					text : '导出Excel',
					xtype : 'button',
					scope : this,
					readOnly:true,
					handler : function() {
						var principalName=this.getCmpByName("principalName").getValue();//被代理人
						var agentName = this.getCmpByName("agentName").getValue();//代理人
						var status = this.getCmpByName("status").getValue();//状态
						window.open(__ctxPath+ '/flow/outputExcelTaskProxy.do?principalName='+principalName+'&agentName='+agentName
									+'&status='+status,'_blank');
					}
				}, new Ext.Toolbar.Separator({
					hidden : (isGranted('_task_proxy_export') && isGranted('_task_proxy_see')) ? false : true
				}),{
					iconCls : 'btn-readdocument',
					text : '查看',
					xtype : 'button',
					scope : this,
					readOnly:true,
					handler : this.editRs
				}]
			});

			this.gridPanel = new HT.GridPanel({
				region : 'center',
				tbar : this.topbar,
				url : __ctxPath + "/flow/listTaskProxy.do",
				fields : [{
					name : 'id',
					type : 'int'
				}, 'principalName', 'agentName', 'startDate','endDate', 'createName', 'createDate', 'status'],
				columns : [{
					header : 'id',
					dataIndex : 'id',
					hidden : true
				}, {
					header : '被代理人',
					dataIndex : 'principalName',
					sortable:true
				}, {
					header : '代理人',
					dataIndex : 'agentName',
					sortable:true
				}, {
					header : '代理开始时间',
					align:'center',
					dataIndex : 'startDate',
					format:'Y-m-d',
					sortable:true
				}, {
					header : '代理结束时间',
					align:'center',
					dataIndex : 'endDate',
					format:'Y-m-d',
					sortable:true
				}, {
					header : '指定人',
					dataIndex : 'createName',
					sortable:true
				}, {
					header : '指定时间',
					align:'center',
					dataIndex : 'createDate',
					format:'Y-m-d',
					sortable:true
				}, {
					header : '状态',
					align:'center',
					dataIndex : 'status',
					sortable:true,
					renderer : function(value, metaData, record, rowIndex,colIndex, store) {
						if(value==0){
							return '未过期';
						}else if(value==1){
							return '已过期';
						}
					}
				}]
			});
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
		// 创建记录
		createRs : function() {
			new TaskProxyForm({
				parentGridPanel:this.gridPanel
			}).show();
		},
		// 按ID删除记录
		removeRs : function() {
			var rows = this.gridPanel.getSelectionModel().getSelections();
			if (rows.length == 0) {
				Ext.ux.Toast.msg('操作信息', '请选择记录!');
				return;
			} else if (rows.length > 1) {
				Ext.ux.Toast.msg('操作信息', '只能选择一条记录!');
				return;
			} else {
				$postDel({
					url : __ctxPath + '/flow/multiDelTaskProxy.do',
					ids : rows[0].data.id,
					grid : this.gridPanel
				});
			}
		},
		// 编辑Rs
		editRs : function(v) {
			var rows = this.gridPanel.getSelectionModel().getSelections();
			if (rows.length == 0) {
				Ext.ux.Toast.msg('操作信息', '请选择记录!');
				return;
			} else if (rows.length > 1) {
				Ext.ux.Toast.msg('操作信息', '只能选择一条记录!');
				return;
			} else {
				new TaskProxyForm({
					id : rows[0].data.id,
					agentName : rows[0].data.agentName,
					principalName : rows[0].data.principalName,
					readOnly:v.readOnly,
					parentGridPanel:this.gridPanel
				}).show();
			}
		}
});