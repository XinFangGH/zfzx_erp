/**
 * @author:
 * @class BorrowRecordView
 * @extends Ext.Panel
 * @description [RollFile]管理
 * @company 北京互融时代软件有限公司
 * @createtime:
 */
BorrowRecordView = Ext.extend(Ext.Panel, {
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		// 初始化组件
		this.initUIComponents();
		// 调用父类构造
		BorrowRecordView.superclass.constructor.call(this, {
					id : 'BorrowRecordView',
					title : '借阅申请',
					region : 'center',
					layout : 'border',
					defaults : {
						anchor : '96%,96%'
					},
					items : [this.searchPanel, this.gridPanel]

				});
	},// end of constructor
	// 初始化组件
	initUIComponents : function() {
		// 初始化搜索条件Panel
		this.searchPanel = new HT.SearchPanel({
					id : 'BorrowRecordSearchPanel',
					layout : 'form',
					region : 'north',
					defaults : {
						anchor : '96%'
					},
					colNums : 6,
					items : [{

								fieldLabel : '借阅目的',
								name : 'Q_borrowReason_S_LK',
								flex : 1,
								editable : true,
								lazyInit : false,
								forceSelection : false,
								xtype : 'diccombo',
								itemName : '借阅目的'

							}, {
								fieldLabel : '归还状态',
								hiddenName : 'Q_returnStatus_SN_EQ',
								flex : 1,
								xtype : 'combo',
								mode : 'local',
								editable : false,
								triggerAction : 'all',
								store : [['', '全部'], ['0', '申请'], ['1', '通过'],
										['-1', '驳回'], ['2', '归还']]
							}, {
								fieldLabel : '借阅时间  从',
								name : 'Q_borrowDate_D_GE',
								flex : 1,
								xtype : 'datefield',
								format : 'Y-m-d'
							}, {
								fieldLabel : '到',
								name : 'Q_borrowDate_D_LE',
								flex : 1,
								xtype : 'datefield',
								format : 'Y-m-d'
							}, {

								fieldLabel : '应还日期  从',
								name : 'Q_returnDate_D_GE',
								flex : 1,
								xtype : 'datefield',
								format : 'Y-m-d'

							}, {

								fieldLabel : '到',
								name : 'Q_returnDate_D_LE',
								flex : 1,
								xtype : 'datefield',
								format : 'Y-m-d'

							}

					],
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
					rowActions : true,
					id : 'BorrowRecordGrid',
					viewConfig : {
						forceFit : true,
						autoFill : true
					},
					defaults : {
						anchor : '96%'
					},
					url : __ctxPath + "/arch/listBorrowRecord.do",
					fields : [{
								name : 'recordId',
								type : 'int'
							}, 'borrowDate', 'borrowType', 'borrowReason',
							'checkUserId', 'checkUserName', 'checkDate',
							'returnDate', 
							{
								name:'returnStatus',type:'int'
							}
							, 'borrowNum',
							'checkUserName'],
					columns : [{
								header : 'recordId',
								dataIndex : 'recordId',
								hidden : true
							}, {
								header : '借阅编号',
								dataIndex : 'borrowNum'
							}, {
								header : '借阅人',
								dataIndex : 'checkUserName'
							}

							, {
								header : '借阅日期',
								width : 60,
								dataIndex : 'borrowDate'
							}, {
								header : '应还日期',
								width : 60,
								dataIndex : 'returnDate'
							},{
								header : '借阅方式',
								width : 60,
								dataIndex : 'borrowType'
							}, {
								header : '借阅目的',
								width : 60,
								dataIndex : 'borrowReason'
							},

							 {
								header : '归还状态',
								width : 60,
								dataIndex : 'returnStatus',
								renderer : function(returnStatus) {
									switch (returnStatus) {
										case 0 :
											return '申请';
											break;
										case 1 :
											return '通过';
											break;
										case -1 :
											return '驳回';
											break;
										case 2 :
											return '归还';
											break;

									}
								}

							}, new Ext.ux.grid.RowActions({
										header : '管理',
										width : 100,
										actions : [{
													iconCls : 'btn-edit',
													qtip : '编辑',
													style : 'margin:0 3px 0 3px'
												}, {}],
										listeners : {
											scope : this,
											'action' : this.onRowAction
										}
									})]

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
					new BorrowRecordForm({
								recordId : rec.data.recordId,
								returnStatus : rec.data.returnStatus
							}).show();

				});
	},
	// 创建记录
	createRs : function() {
		new BorrowRecordForm({
					returnStatus : 0
				}).show();
	},
	// 按ID删除记录
	removeRs : function(id) {
		$postDel({
					url : __ctxPath + '/arch/multiDelBorrowRecord.do',
					ids : id,
					grid : this.gridPanel
				});
	},
	// 把选中ID删除
	removeSelRs : function() {
		$delGridRs({
					url : __ctxPath + '/arch/multiDelBorrowRecord.do',
					grid : this.gridPanel,
					idName : 'recordId'
				});
	},
	// 编辑Rs
	editRs : function(record) {

		new BorrowRecordForm({
					recordId : record.data.recordId,
					returnStatus : record.data.returnStatus
				}).show();

	},

	// 行的Action
	onRowAction : function(grid, record, action, row, col) {
		switch (action) {

			case 'btn-edit' :
				this.editRs.call(this, record);
				break;
			default :
				break;
		}
	}
});
