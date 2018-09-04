/**
 * @author
 * @class SettlementReviewerPayView
 * @extends Ext.Panel
 * @description [SettlementReviewerPay]管理
 * @company 智维软件
 * @createtime:
 */
SettlementReviewerPayView = Ext.extend(Ext.Window, {
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		// 初始化组件
		this.initUIComponents();
		// 调用父类构造
		SettlementReviewerPayView.superclass.constructor.call(this, {
					id : 'SettlementReviewerPayView',
					title : '结算列表',
					layout : 'fit',
					modal : true,
					height : 800,
					width : 1200,
					maximizable : true,
					buttonAlign : 'center',
					items : [this.gridPanel]
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
								fieldLabel : 'flowType',
								name : 'Q_flowType_S_EQ',
								flex : 1,
								xtype : 'textfield'
							}, {
								fieldLabel : 'settlementStartDate',
								name : 'Q_settlementStartDate_S_EQ',
								flex : 1,
								xtype : 'textfield'
							}, {
								fieldLabel : 'settlementEndDate',
								name : 'Q_settlementEndDate_S_EQ',
								flex : 1,
								xtype : 'textfield'
							}, {
								fieldLabel : 'collectionDepartment',
								name : 'Q_collectionDepartment_S_EQ',
								flex : 1,
								xtype : 'textfield'
							}, {
								fieldLabel : 'paymentMethod',
								name : 'Q_paymentMethod_S_EQ',
								flex : 1,
								xtype : 'textfield'
							}, {
								fieldLabel : 'settlementMoney',
								name : 'Q_settlementMoney_S_EQ',
								flex : 1,
								xtype : 'textfield'
							}, {
								fieldLabel : 'otherSettlementMoney',
								name : 'Q_otherSettlementMoney_S_EQ',
								flex : 1,
								xtype : 'textfield'
							}, {
								fieldLabel : 'actualSettlementMoney',
								name : 'Q_actualSettlementMoney_S_EQ',
								flex : 1,
								xtype : 'textfield'
							}, {
								fieldLabel : 'bankName',
								name : 'Q_bankName_S_EQ',
								flex : 1,
								xtype : 'textfield'
							}, {
								fieldLabel : 'bankNum',
								name : 'Q_bankNum_S_EQ',
								flex : 1,
								xtype : 'textfield'
							}, {
								fieldLabel : 'managerName',
								name : 'Q_managerName_S_EQ',
								flex : 1,
								xtype : 'textfield'
							}, {
								fieldLabel : 'reviewer',
								name : 'Q_reviewer_S_EQ',
								flex : 1,
								xtype : 'textfield'
							}, {
								fieldLabel : 'payer',
								name : 'Q_payer_S_EQ',
								flex : 1,
								xtype : 'textfield'
							}, {
								fieldLabel : 'managerDate',
								name : 'Q_managerDate_S_EQ',
								flex : 1,
								xtype : 'textfield'
							}, {
								fieldLabel : 'remark1',
								name : 'Q_remark1_S_EQ',
								flex : 1,
								xtype : 'textfield'
							}, {
								fieldLabel : 'remark2',
								name : 'Q_remark2_S_EQ',
								flex : 1,
								xtype : 'textfield'
							}, {
								fieldLabel : 'remark3',
								name : 'Q_remark3_S_EQ',
								flex : 1,
								xtype : 'textfield'
							}, {
								fieldLabel : 'remark4',
								name : 'Q_remark4_S_EQ',
								flex : 1,
								xtype : 'textfield'
							}, {
								fieldLabel : 'remark5',
								name : 'Q_remark5_S_EQ',
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
								iconCls : 'btn-search',
								text : '查看结算明细',
								xtype : 'button',
								scope : this,
								handler : this.seeRs
							}]
				});

		this.gridPanel = new HT.GridPanel({
			region : 'center',
			tbar : this.topbar,
			//使用RowActions
			rowActions : false,
			id : 'SettlementReviewerPayGrid',
			url : __ctxPath
					+ "/web/listSettlementReviewerPay.do?Q_collectionDepartment_S_EQ="+this.organId,
			fields : [{
						name : 'id',
						type : 'int'
					},'organId','organName','projectNumber','factMoney','otherMoney','payMoney','payStartDate','payEndDate','state','paymentMethod','projectNumber'],
			columns : [{
						header : 'id',
						dataIndex : 'id',
						hidden : true
					},{
						header : 'organId',
						dataIndex : 'organId',
						hidden : true
					}, {
						header : '部门名称',
						dataIndex : 'organName'
					}, {
						header : '项目编号',
						dataIndex : 'projectNumber'
					},{
						header : '应结算金额',
						dataIndex : 'payMoney'
					},{
						header : '其他金额',
						dataIndex : 'otherMoney'
					}, {
						header : '实际结算金额',
						dataIndex : 'factMoney'
					}, {
						header : '结算开始日期',
						dataIndex : 'payStartDate'
					}, {
						header : '结算结束日期',
						dataIndex : 'payEndDate'
					}, {
						header : '结算状态',
						dataIndex : 'state',
						renderer : function(value) {
									if (value ==0) {
										return "待审核";
									} else if(value ==1){
										return "待支付";
									} else if(value ==2){
										return "已支付";
									}
								}
					},{
						header : '支付方式',
						dataIndex : 'paymentMethod',
						renderer : function(value) {
									if (value ==2) {
										return "线下银行转账";
									} else if(value ==3){
										return "线下现金支付";
									} else {
										return "其他";
									}
								}
					}]
				//end of columns
			});

//		this.gridPanel.addListener('rowdblclick', this.rowClick);

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
					new SettlementReviewerPayForm({
								id : rec.data.id
							}).show();
				});
	},
	//创建记录
	seeRs : function() {
		var selectRs = this.gridPanel.getSelectionModel().getSelections();
		if (selectRs.length == 0) {
					Ext.ux.Toast.msg('操作信息', '请选择记录!');
					return;
				} else if (selectRs.length > 1) {
					Ext.ux.Toast.msg('操作信息', '只能选择一条记录!');
					return;
				} else {
					var record = selectRs[0];
					if(!record.data.payEndDate){
						Ext.ux.Toast.msg('操作信息', '该结算信息还在待审核状态，暂时不能查询明细');
						return;
					}
					type = this.type=='2'?"3":this.type;
					var organizationId = this.organId;
					var id = record.data.id;
					var projectNumber = record.data.projectNumber;
//					var window = new SettlementInfoView1({type:this.type})
					var window = new SettlementReviewerPayView1({
						organizationId:organizationId,
						settlementReviewerPayId:id,
						businessType:'InvestSettle',
						title:projectNumber,
						isreadOnly:true,
						type:this.type
					})
					window.show();
					//查询提成明细
//					var payStartDate = record.data.payStartDate;
//					var payEndDate = record.data.payEndDate;
//					var organId = record.data.organId;
//					if(payStartDate&&payEndDate){
//						var store = window.gridPanel.getStore();
//						store.reload({
//							params : {
//								orgId : organId,
//								startDate:payStartDate,
//								endDate:payEndDate
//							}
//						});
//					}
					}
	},
	//按ID删除记录
	removeRs : function(id) {
		$postDel({
			url : __ctxPath
					+ '/creditFlow.fund.project/multiDelSettlementReviewerPay.do',
			ids : id,
			grid : this.gridPanel
		});
	},
	//把选中ID删除
	removeSelRs : function() {
		$delGridRs({
			url : __ctxPath
					+ '/creditFlow.fund.project/multiDelSettlementReviewerPay.do',
			grid : this.gridPanel,
			idName : 'id'
		});
	},
	//编辑Rs
	editRs : function(record) {
		new SettlementReviewerPayForm({
					id : record.data.id
				}).show();
	},
	//行的Action
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
