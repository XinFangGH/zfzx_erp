/**
 * @author:
 * @class MyBorrowRecordView
 * @extends Ext.Panel
 * @description [RollFile]管理
 * @company 北京互融时代软件有限公司
 * @createtime:
 */
MyBorrowRecordView = Ext.extend(Ext.Panel, {
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		// 初始化组件
		this.initUIComponents();
		// 调用父类构造
		MyBorrowRecordView.superclass.constructor.call(this, {
			id : 'MyBorrowRecordView',
			title : '我的借阅',
			iconCls : 'menu-myBorrowRecord',
			region : 'center',
			layout : 'border',
			defaults : {
				anchor : '96%,96%'
			},
			listeners : {
				'afterlayout' : function(MyBorrowRecordView) {
					MyBorrowRecordView.search();
				}
			},
			items : [this.searchPanel, this.gridPanel],
			keys : {
				key : Ext.EventObject.ENTER,
				fn : this.search,
				scope : this
			}
		});
	},// end of constructor
	// 初始化组件
	initUIComponents : function() {
		// 初始化搜索条件Panel
		this.searchPanel = new Ext.FormPanel({
					id : 'MyBorrowRecordViewSearchPanel',
					layout : 'hbox',
					region : 'north',
					height : 38,
					border : false,
					layoutConfig : {
						align : 'middle',
						padding : '5'
					},
					defaults : {
						anchor : '96%, 99%',
						border : false,
						xtype : 'label',
						style : 'padding-left:5px;padding-right:5px;'
					},
					items : [{
						text : '借阅目的:'
					}, {
						width : 80,
						name : 'Q_borrowReason_S_LK',
						editable : true,
						lazyInit : false,
						forceSelection : false,
						xtype : 'diccombo',
						itemName : '借阅目的'
					}, {
						text : '借阅状态:'
					}, {
						width : 80,
						hiddenName : 'Q_returnStatus_SN_EQ',
						xtype : 'combo',
						mode : 'local',
						editable : false,
						triggerAction : 'all',
						store : [['', '全部'], ['0', '申请'], ['1', '通过'],
								['-1', '驳回'], ['2', '归还']]
					}, {
						text :'借阅时间 :从'
					}, {
						width : 80,
						name : 'Q_borrowDate_D_GE',
						xtype : 'datefield',
						format : 'Y-m-d'
					}, {
						text : '至'
					}, {
						width : 80,
						name : 'Q_borrowDate_D_LE',
						xtype : 'datefield',
						format : 'Y-m-d'
					}, {
						text : '应还日期:从'
					}, {
						width : 80,
						name : 'Q_returnDate_D_GE',
						xtype : 'datefield',
						format : 'Y-m-d'
					}, {
						text : '至'
					}, {
						width : 80,
						name : 'Q_returnDate_D_LE',
						xtype : 'datefield',
						format : 'Y-m-d'
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
						iconCls : 'reset',
						handler : this.reset
					}, {
						name : 'Q_appUser.userId_L_EQ',
						value : curUserInfo.userId,
						xtype : 'hidden'
					}]
				});// end of searchPanel

		this.topbar = new Ext.Toolbar({
					items : [{
						iconCls : 'btn-add',
						text : '添加',
						xtype : 'button',
						scope : this,
						handler : this.createRs
					}, '-', {
						iconCls : 'btn-edit',
						text : '编辑',
						xtype : 'button',
						scope : this,
						handler : this.editRs

					}, '-', {
						iconCls : 'btn-del',
						text : '删除',
						xtype : 'button',
						scope : this,
						handler : this.removeSelRs
					}]
				});

		this.fileRecord = Ext.data.Record.create([{
					name : 'recordId',
					type : 'int'
				}, 'borrowDate', 'borrowType', 'borrowReason', 'checkUserId',
				'checkUserName', 'checkDate', 'returnDate', 'returnStatus',
				'borrowNum', 'checkUserName','viewFlag']);

		this.memoryProxy = new Ext.data.HttpProxy({
					url : __ctxPath + "/arch/listBorrowRecord.do"
				});
		this.jsonReader = new Ext.data.JsonReader({
					root : 'result',
					totalProperty : 'totalCounts',
					idProperty : "rollFileId"
				}, this.fileRecord);
		this.mystore = new Ext.data.Store({
					proxy : this.memoryProxy,
					reader : this.jsonReader
				});
		this.mystore.on('load',function(store , records, options ){
		
			Ext.each(records,function(record){
				if(record.get('returnStatus')==1){
					record.set('viewFlag',false);
				}else{
					record.set('viewFlag',true);
				}
				record.commit(true);
			},this);
			
			store.commitChanges();
		});
			
		

		this.gridPanel = new HT.GridPanel({
					region : 'center',
					tbar : this.topbar,
					rowActions : true,
					id : 'MyBorrowRecordGrid',
					viewConfig : {
						forceFit : true,
						autoFill : true
					},
					defaults : {
						anchor : '96%'
					},

					store : this.mystore,
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
							}, {
								header : '借阅方式',
								width : 60,
								dataIndex : 'borrowType'
							}, {
								header : '借阅目的',
								width : 60,
								dataIndex : 'borrowReason'
							}, {
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
											iconCls : 'btn-showDetail',
											qtip : '查看',
											hideIndex:'viewFlag',
											style : 'margin:0 3px 0 3px'
										}, {}],
								listeners : {
									scope : this,
									'action' : this.onRowAction
								}
							})]
				});

		//this.gridPanel.addListener('rowdblclick', this.rowClick);

	},// end of the initComponents()
	// 创建记录
	createRs : function() {
		new BorrowRecordForm({
			returnStatus : 0
		}).show();
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
		var selectRecords = this.gridPanel.getSelectionModel().getSelections();
		if (selectRecords.length == 0) {
			Ext.ux.Toast.msg("信息", "请选择要编辑的记录！");
			return;
		}

		new BorrowRecordForm({
					recordId : selectRecords[0].data.recordId,
					returnStatus : selectRecords[0].data.returnStatus
				}).show();

	},

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

					var centerPanel = Ext.getCmp('centerTabPanel');
					var myBorrowFilePanel = centerPanel
							.add(new MyBorrowFilePanel({
										id : rec.data.borrowNum,
										title : '我的借阅>>编号:'
												+ rec.data.borrowNum,
										recordId : rec.data.recordId,
										borrowNum : rec.data.borrowNum,
										showFlag:'view'
									}));
					centerPanel.activate(myBorrowFilePanel);

				});
	},

	atcionViewDetail : function(rec) {
		var centerPanel = Ext.getCmp('centerTabPanel');
		var myBorrowFilePanel = centerPanel.add(new MyBorrowFilePanel({
					id : rec.data.borrowNum,
					title : '我的借阅>>编号:' + rec.data.borrowNum,
					recordId : rec.data.recordId,
					borrowNum : rec.data.borrowNum,
										showFlag:'view'
				}));
		centerPanel.activate(myBorrowFilePanel);
	},

	// 行的Action
	onRowAction : function(grid, record, action, row, col) {
		switch (action) {
			case 'btn-showDetail' :
				this.atcionViewDetail.call(this, record);
				break;
			default :
				break;
		}
	}
});
