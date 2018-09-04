/**
 * @author
 * @class SmsinfoView
 * @extends Ext.Panel
 * @description [Smsinfo]管理
 * @company 智维软件
 * @createtime:
 */
SmsinfoView = Ext.extend(Ext.Panel, {
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		// 初始化组件
		this.initUIComponents();
		// 调用父类构造
		SmsinfoView.superclass.constructor.call(this, {
					id : 'SmsinfoView',
					title : '短信平台',
					region : 'center',
					layout : 'border',
					items : [this.searchPanel, this.gridPanel]
				});
	},// end of constructor
	// 初始化组件
	initUIComponents : function() {
		// 初始化搜索条件Panel
		this.searchPanel = new HT.SearchPanel({
					height : 40,
					border : false,
					region : 'north',
					layout : 'hbox',
					layoutConfig : {
						align : 'middle',
						pack : 'left'
					},
					style : 'background-color:white;padding:5px;',
					defaults : {
						margins : '0px 8px 0px 4px'
					},
					items : [{
								xtype : 'label',
								text : '起始日期:'
							}, {
								xtype : 'datefield',
								name : 'Q_sendTime_D_EQ',
								format : 'Y-m-d',
								width : 200
							}, {
								xtype : 'label',
								text : '截止日期:'
							}, {
								xtype : 'datefield',
								name : 'Q_sendTime_D_EQ',
								format : 'Y-m-d',
								width : 200
							}, {
								xtype : 'label',
								text : '手机号:'
							}, {
								xtype : 'textfield',
								name : 'Q_sendPhone_S_EQ',
								format : 'Y-m-d',
								width : 200
							}, {
								xtype : 'button',
								text : '查询',
								scope : this,
								iconCls : 'btn-search',
								handler : this.search
							}, {
								xtype : 'button',
								text : '重置',
								scope : this,
								iconCls : 'btn-reset',
								handler : this.reset
							}]
				});// end of searchPanel

		this.topbar = new Ext.Toolbar({
			items : [{
						iconCls : 'btn-add',
						text : '发送短信',
						xtype : 'button',
						scope : this,
						handler : this.createRs
					}, '->', {
						id:'smsCount',
						xtype : 'tbtext',
						text:"",
						width : 200
					}]
		});

		this.gridPanel = new HT.GridPanel({
			region : 'center',
			tbar : this.topbar,
			// 使用RowActions
			rowActions : true,
			id : 'SmsinfoGrid',
			url : __ctxPath + "/system/listSmsinfo.do",
			fields : [{
						name : 'id',
						type : 'int'
					}, 'sendTime', 'sendContent', 'sendFlg', 'sendPhone',
					'userId'],
			columns : [{
						header : 'id',
						dataIndex : 'id',
						hidden : true
					}, {
						header : '发送时间',
						dataIndex : 'sendTime'
					}, {
						header : '发送内容',
						dataIndex : 'sendContent'
					}, {
						header : '发送状态',
						dataIndex : 'sendFlg',
						renderer : function(v) {
							switch (v) {
								case 0 :
									return "成功"
									break;
								default :
									return "失败";

							}
						}
					}, {
						header : '发送手机号',
						dataIndex : 'sendPhone'
					}, {
						header : '发送人',
						dataIndex : 'userId',
						renderer : function(v) {

							return "管理员";

						}
					}, new Ext.ux.grid.RowActions({
								header : '管理',
								width : 100,
								actions : [{
											iconCls : 'btn-edit',
											qtip : '重发',
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
		
		 var obj=Ext.getCmp("smsCount");
	     ZW.refreshSmsCount(obj);
	    

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
					new SmsinfoForm({
								id : rec.data.id
							}).show();
				});
	},
	// 创建记录
	createRs : function() {
		new SmsinfoForm().show();
	},
	// 按ID删除记录
	removeRs : function(id) {
		$postDel({
					url : __ctxPath + '/system/multiDelSmsinfo.do',
					ids : id,
					grid : this.gridPanel
				});
	},
	// 把选中ID删除
	removeSelRs : function() {
		$delGridRs({
					url : __ctxPath + '/system/multiDelSmsinfo.do',
					grid : this.gridPanel,
					idName : 'id'
				});
	},
	// 编辑Rs
	editRs : function(record) {
		new SmsinfoForm({
					id : record.data.id
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
