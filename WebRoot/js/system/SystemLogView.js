/**
 * @author:
 * @class SystemLogView
 * @extends Ext.Panel
 * @description 系统日志管理
 * @company 北京互融时代软件有限公司
 * @createtime:2010-01-16
 */
SystemLogView = Ext.extend(Ext.Panel, {
	//条件搜索Panel
	searchPanel : null,
	//数据展示Panel
	gridPanel : null,
	//GridPanel的数据Store
	store : null,
	//头部工具栏
	topbar : null,
	//构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		//初始化组件
		this.initUIComponents();
		//调用父类构造
		SystemLogView.superclass.constructor.call(this, {
					id : 'SystemLogView',
					title : '系统操作日志',
//					height:30,
					layout : 'border',
					region : 'center',
					iconCls:"menu-flowManager",
					items : [this.searchPanel, this.gridPanel]
				});
	},//end of constructor

	//初始化组件
	initUIComponents : function() {
		//初始化搜索条件Panel
		this.searchPanel = new Ext.FormPanel({
					layout : 'hbox',
					bodyStyle : 'padding:6px 10px 6px 10px',
					layoutConfigs:{
						align:'middle'
					},
					defaultType:'label',
					border : false,
					defaults : {
						margins:' 2 2 2 2'
					},
					items : [
							{
								text:'用户名'
							},
							{
									name : 'Q_username_S_LK',
									xtype : 'textfield'
							}, {
								text:'执行时间  从'
							},{
									name : 'Q_createtime_D_GT',
									xtype : 'datefield',
									format:'Y-m-d'
							}, {text:'至'},
							{
									name : 'Q_createtime_D_LT',
									xtype : 'datefield',
									format:'Y-m-d'
							},	
							{
								text:'执行操作'
							},{
									name : 'Q_exeOperation_S_LK',
									xtype : 'textfield'
							}, {
									xtype : 'button',
									text : '查询',
									iconCls : 'search',
									handler : this.search.createCallback(this)
							}]
				});//end of the searchPanel

		//加载数据至store
		this.store = new Ext.data.JsonStore({
					url : __ctxPath + "/system/listSystemLog.do",
					root : 'result',
					totalProperty : 'totalCounts',
					remoteSort : true,
					fields : [{
								name : 'logId',
								type : 'int'
							}, 'username', 'userId', 'createtime',
							'exeOperation','className','methodName','params','ip','err']
				});
		this.store.setDefaultSort('logId', 'desc');
		//加载数据
		this.store.load({
					params : {
						start : 0,
						limit : 25
					}
				});

		this.rowActions = new Ext.ux.grid.RowActions({
					header : '管理',
					width : 80,
					actions : [
							{
								iconCls : 'btn-del',
								qtip : '删除',
								style : 'margin:0 3px 0 3px'
							}
							]
				});

		//初始化ColumnModel
		var sm = new Ext.grid.CheckboxSelectionModel();
		var cm = new Ext.grid.ColumnModel({
					columns : [sm, new Ext.grid.RowNumberer(), {
								header : 'logId',
								dataIndex : 'logId',
								hidden : true
							}, {
								header : '用户名',
								dataIndex : 'username'
							}, {
								header : '用户ID',
								hidden:true,
								align:'center',
								dataIndex : 'userId'
							}, {
								header : '执行时间',
								format:'Y-m-d',
								align:'center',
								dataIndex : 'createtime'
							}, {
								header : '执行类名称',
								dataIndex : 'className'
							}, {
								header : '执行方法',
								dataIndex : 'methodName'
							}, {
								header : '参数',
								dataIndex : 'params'
							}, {
								header : 'ip',
								align:'center',
								dataIndex : 'ip'
							}, {
								header : '执行操作',
								dataIndex : 'exeOperation'
							}, {
								header : '错误信息',
								dataIndex : 'err'
							}/*, this.rowActions*/],
					defaults : {
						sortable : true,
						menuDisabled : false,
						width : 100
					}
				});
		//初始化工具栏
		this.topbar = new Ext.Toolbar({
					height : 30,
					bodyStyle : 'text-align:left',
					items : [{
								iconCls : 'btn-del',
								text : '删除系统日志',
								xtype : 'button',
								handler : this.delRecords,
								scope : this
							}]
				});

		this.gridPanel = new Ext.grid.GridPanel({
					id : 'SystemLogGrid',
					stripeRows : true,
					//tbar : this.topbar,
					store : this.store,
					trackMouseOver : true,
					disableSelection : false,
					loadMask : true,
					region : 'center',
					cm : cm,
					sm : sm,
					plugins : this.rowActions,
					viewConfig : {
						forceFit : true,
						autoFill : true, //自动填充
						forceFit : true
						//showPreview : false
					},
					bbar : new HT.PagingBar({store : this.store})
				});

		this.gridPanel.addListener('rowdblclick', function(grid, rowindex, e) {
					grid.getSelectionModel().each(function(rec) {
								new SystemLogForm(rec.data.logId).show();
							});
				});
		this.rowActions.on('action', this.onRowAction, this);
	},//end of the initComponents()

	/**
	 * 
	 * @param {} self 当前窗体对象
	 */
	search : function(self) {
		if (self.searchPanel.getForm().isValid()) {//如果合法
			$search({
				searchPanel :self.searchPanel,
				gridPanel : self.gridPanel
			});
		}
	},

	/**
	 * 添加记录
	 */
	createRecord : function() {
		new SystemLogForm().show();
	},
	/**
	 * 按IDS删除记录
	 * @param {} ids
	 */
	delByIds : function(ids) {
		Ext.Msg.confirm('信息确认', '您确认要删除所选记录吗？', function(btn) {
			if (btn == 'yes') {
				Ext.Ajax.request({
							url : __ctxPath + '/system/multiDelSystemLog.do',
							params : {
								ids : ids
							},
							method : 'POST',
							success : function(response, options) {
								Ext.ux.Toast.msg('操作信息', '成功删除该系统日志！');
								Ext.getCmp('SystemLogGrid').getStore().reload();
							},
							failure : function(response, options) {
								Ext.ux.Toast.msg('操作信息', '操作出错，请联系管理员！');
							}
						});
			}
		});//end of comfirm
	},
	/**
	 * 删除多条记录
	 */
	delRecords : function() {
		var gridPanel = Ext.getCmp('SystemLogGrid');
		var selectRecords = gridPanel.getSelectionModel().getSelections();
		if (selectRecords.length == 0) {
			Ext.ux.Toast.msg("信息", "请选择要删除的记录！");
			return;
		}
		var ids = Array();
		for (var i = 0; i < selectRecords.length; i++) {
			ids.push(selectRecords[i].data.logId);
		}
		this.delByIds(ids);
	},

	/**
	 * 编辑记录
	 * @param {} record
	 */
	editRecord : function(record) {
		new SystemLogForm({
					logId : record.data.logId
				}).show();
	},
	/**
	 * 管理列中的事件处理
	 * @param {} grid
	 * @param {} record
	 * @param {} action
	 * @param {} row
	 * @param {} col
	 */
	onRowAction : function(gridPanel, record, action, row, col) {
		switch (action) {
			case 'btn-del' :
				this.delByIds(record.data.logId);
				break;
			case 'btn-edit' :
				this.editRecord(record);
				break;
			default :
				break;
		}
	}
});
