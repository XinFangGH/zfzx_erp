/**
 * @author:
 * @class SlActualToChargePremium
 * @extends Ext.Panel
 * @description [SlActualToChargePremium]管理
 * @company 广州宏天软件有限公司
 * @createtime:
 */
SlActualToChargePremiumVM = Ext.extend(Ext.Panel, {
	// 构造函数
	constructor : function(_cfg) {
		if (typeof(_cfg.projId) != "undefined") {
			this.projectId = _cfg.projId;
		}
		if (typeof(_cfg.businessType) != "undefined") {
			this.businessType = _cfg.businessType;
		}
		if (typeof(_cfg.isUnLoadData) != "undefined") {
			this.isUnLoadData = _cfg.isUnLoadData;
		}
		if (typeof(_cfg.object) != "undefined") {
			this.object = _cfg.object;
		}
		if (typeof(_cfg.isHiddenBtn) != "undefined") {
			this.isHiddenBtn = _cfg.isHiddenBtn;
		}
		if (typeof(_cfg.isHiddenDZBtn) != "undefined") {
			this.isHiddenDZBtn = _cfg.isHiddenDZBtn;
		}
		Ext.applyIf(this, _cfg);
		// 初始化组件
		this.initUIComponents();
		// 调用父类构造
		SlActualToChargePremiumVM.superclass.constructor.call(this, {
			layout : 'anchor',
			anchor : '100%',
			items : [{
				xtype : 'panel',
				border : false,
				bodyStyle : 'margin-bottom:5px',
				html : '<br/><B><font class="x-myZW-fieldset-title">【业务收费】</font></B><font class="x-myZW-fieldset-title">（</font>颜色预警：<font color=red>逾期款项</font>&nbsp;&nbsp<font style="line-height:20px">未结清项</font>&nbsp;&nbsp<font color=gray>已结清项</font><font class="x-myZW-fieldset-title" >）：</font>'
			}, this.gridPanel]
		});
	},// end of constructor
	// 初始化组件
	initUIComponents : function() {
		var summary = new Ext.ux.grid.GridSummary();
		function totalMoney(v, params, data) {
			return '总计(元)';
		}
		this.topbar = new Ext.Toolbar({
					items : [{
								text : '刷新应收保费',
								iconCls : 'btn-reset',
								scope : this,
								hidden : this.isHiddenBtn,
								handler : this.createRs
							}, {
								iconCls : 'btn-user-sel',
								hidden : this.isHiddenDZBtn,
								scope : this,
								text : '对账',
								handler : this.chargecheck
							}, /*
								 * new Ext.Toolbar.Separator({ hidden :
								 * this.isHiddenBtn }),
								 */{
								iconCls : 'btn-detail',
								text : '查看单项流水记录',
								xtype : 'button',
								scope : this,
								handler : function() {
									var selRs = this.gridPanel
											.getSelectionModel()
											.getSelections();
									if (selRs <= 0) {
										Ext.ux.Toast.msg('操作信息', '请选中任何一条记录');
										return;
									} else if (selRs.length > 1) {
										Ext.ux.Toast.msg('操作信息', '只能选中一条记录');
										return;
									}
									this.chargeqlideInfo
											.call(this, selRs[0], 1);
								}
							}]
				});

		this.gridPanel = new HT.EditorGridPanel({
			border : false,
			clicksToEdit : 1,
			showPaging : false,
			autoHeight : true,
			region : 'center',
			plugins : [summary],
			tbar : this.topbar,
			// 使用RowActions
			rowActions : true,
			id : 'SlActualToChargePremiumVM',
			url : __ctxPath
					+ "/creditFlow/finance/listbyprojectSlActualToCharge.do?projectId="
					+ this.projectId + "&isUnLoadData=" + this.isUnLoadData
					+ "&businessType=" + this.businessType
					+ "&chargeKey='premiumMoney','earnestMoney'",
			fields : [{
						name : 'actualChargeId'
					}, {
						name : 'planChargeId'
					}, {
						name : 'typeName'
					}, {
						name : 'planCharges'
					}, {
						name : 'chargeStandard'
					}, {
						name : 'payMoney'
					}, {
						name : 'incomeMoney'
					}, {
						name : 'intentDate'
					}, {
						name : 'remark'
					}, {
						name : 'factDate'
					}, {
						name : 'afterMoney'
					}, {
						name : 'notMoney'
					}, {
						name : 'flatMoney'
					}, {
						name : 'isOverdue'
					}, {
						name : 'projectName'
					}/*
						 * , { name : 'computeMoney' }
						 */],
			columns : [{
						header : 'actualChargeId',
						dataIndex : 'actualChargeId',
						hidden : true
					}, {
						header : "收入类型",
						width : 170,
						dataIndex : 'typeName',
						summaryType : 'count',
						summaryRenderer : totalMoney,
						renderer : function(value, metaData, record, rowIndex, colIndex, store) {
							var flag1 = 0; // incomeMoney
							if (record.data.payMoney != 0) { // payMoney
								flag1 = 1;
							}
							if (record.data.notMoney == 0) {
								return '<font style="color:gray">'+ value+ "</font>";
							}
							if (record.data.isOverdue == "是" && flag1 != 1) {
								return '<font style="color:red">'+ value+ "</font>";
							}
							return value;
						}
					}, /*
						 * { header : "系统自动计算保费", width : 100, dataIndex :
						 * 'computeMoney', renderer : function(value) { return '<font
						 * style="color:gray">' + Ext.util.Format.number(value,
						 * ',000,000,000.00') + "元" + "</font>";
						 * 
						 * if (v == null || v == '') { return ''; } else {
						 * return v + '元'; } } },
						 */{
						header : "金额",
						width : 100,
						summaryType : 'sum',
						align : 'right',
						dataIndex : 'incomeMoney',
						editor : {
							xtype : 'numberfield',
							readOnly : this.isHiddenBtn
						},
						renderer : function(value, metaData, record, rowIndex,
								colIndex, store) {
							var flag1 = 0; // incomeMoney
							if (record.data.payMoney != 0) { // payMoney
								flag1 = 1;
							}
							if (value != null) {
								if (record.data.notMoney == 0) {
									return '<font style="color:gray">'
											+ Ext.util.Format.number(value,
													',000,000,000.00') + "元"
											+ "</font>";
								}
								if (record.data.isOverdue == "是" && flag1 != 1) {

									return '<font style="color:red">'
											+ Ext.util.Format.number(value,
													',000,000,000.00') + "元"
											+ "</font>";
								}
								return Ext.util.Format.number(value,
										',000,000,000.00')
										+ "元"

							} else {
								return "";

							}

						}
						/*
						 * renderer : function(value) { return '<font
						 * style="color:gray">' + Ext.util.Format.number(value,
						 * ',000,000,000.00') + "元" + "</font>";
						 * 
						 * if (v == null || v == '') { return ''; } else {
						 * return v + '元'; } }
						 */
					}, {
						header : '实际到账日',
						// xtype : 'datecolumn',
						format : 'Y-m-d',
						dataIndex : 'factDate',
						fixed : true,
						width : 80,
						renderer : function(value, metaData, record, rowIndex,
								colIndex, store) {
							var flag1 = 0; // incomeMoney
							if (record.data.payMoney != 0) { // payMoney
								flag1 = 1;
							}
							var v;
							try {
								if (typeof(value) == "string") {
									v = value;
								}
								return value.format("Y-m-d");
							} catch (err) {
								v = value;
							}
							if (v != null) {
								if (record.data.notMoney == 0) {
									return '<font style="color:gray">' + v
											+ "</font>";
								}
								if (record.data.isOverdue == "是" && flag1 != 1) {

									return '<font style="color:red">' + v
											+ "</font>";
								}
								return v;
							} else {
								return "";
							}

						}

					}, {
						header : '已对账金额',
						dataIndex : 'afterMoney',
						align : 'right',
						summaryType : 'sum',
						width : 115,
						renderer : function(value, metaData, record, rowIndex,
								colIndex, store) {
							var flag1 = 0; // incomeMoney
							if (record.data.payMoney != 0) { // payMoney
								flag1 = 1;
							}
							if (value != null) {
								if (record.data.notMoney == 0) {
									return '<font style="color:gray">'
											+ Ext.util.Format.number(value,
													',000,000,000.00') + "元"
											+ "</font>";
								}
								if (record.data.isOverdue == "是" && flag1 != 1) {

									return '<font style="color:red">'
											+ Ext.util.Format.number(value,
													',000,000,000.00') + "元"
											+ "</font>";
								}
								return Ext.util.Format.number(value,
										',000,000,000.00')
										+ "元"

							} else {
								return "";

							}

						}
					}, {
						header : '未对账金额',
						dataIndex : 'notMoney',
						width : 115,
						align : 'right',
						summaryType : 'sum',
						renderer : function(value, metaData, record, rowIndex,
								colIndex, store) {
							var flag1 = 0; // incomeMoney
							if (record.data.payMoney != 0) { // payMoney
								flag1 = 1;
							}

							if (value != null) {
								if (record.data.notMoney == 0) {
									return '<font style="color:gray">'
											+ Ext.util.Format.number(value,
													',000,000,000.00') + "元"
											+ "</font>";
								}
								if (record.data.isOverdue == "是" && flag1 != 1) {

									return '<font style="color:red">'
											+ Ext.util.Format.number(value,
													',000,000,000.00') + "元"
											+ "</font>";
								}

								return Ext.util.Format.number(value,
										',000,000,000.00')
										+ "元"

							} else {
								return "";

							}

						}
					}, {
						header : '核销金额',
						dataIndex : 'flatMoney',
						summaryType : 'sum',
						align : 'right',
						width : 100,
						renderer : function(value, metaData, record, rowIndex,
								colIndex, store) {
							var flag1 = 0; // incomeMoney
							if (record.data.payMoney != 0) { // payMoney
								flag1 = 1;
							}

							if (value != null) {
								if (record.data.notMoney == 0) {
									return '<font style="color:gray">'
											+ Ext.util.Format.number(value,
													',000,000,000.00') + "元"
											+ "</font>";
								}
								if (record.data.isOverdue == "是" && flag1 != 1) {

									return '<font style="color:red">'
											+ Ext.util.Format.number(value,
													',000,000,000.00') + "元"
											+ "</font>";
								}
								return Ext.util.Format.number(value,
										',000,000,000.00')
										+ "元"

							} else {
								return "";

							}

						}
					}, {
						header : "备注",
						width : 180,
						dataIndex : 'remark',
						align : "center",
						editor : new Ext.form.TextField({
									readOnly : this.isHiddenBtn
								}),
						renderer : function(value, metaData, record, rowIndex,
								colIndex, store) {
							var flag1 = 0; // incomeMoney
							if (record.data.payMoney != 0) { // payMoney
								flag1 = 1;
							}
							if (value != null) {
								if (record.data.notMoney == 0) {
									return '<font style="color:gray">' + value
											+ "</font>";
								}
								if (record.data.isOverdue == "是" && flag1 != 1) {

									return '<font style="color:red">' + value
											+ "</font>";
								}
								return value;

							} else {
								return "";

							}

						}
					}, new Ext.ux.grid.RowActions({
								header : '管理',
								width : 100,
								hidden : true,
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
							})],
			listeners : {
				scope : this,
				afteredit : function(e) {
					var value = e.value;
					var actualChargeId = e.record.data['actualChargeId'];
					if (e.originalValue != e.value) {// 编辑行数据发生改变
						if (e.field == 'remark') {
							args = {
								'slActualToCharge.actualChargeId' : actualChargeId,
								'slActualToCharge.remark' : e.value,
								oprationType : 'manually'
							}
						}
						if (e.field == 'incomeMoney') {
							args = {
								'slActualToCharge.actualChargeId' : actualChargeId,
								'slActualToCharge.incomeMoney' : e.value,
								oprationType : 'manually'
							}
						}
						Ext.Ajax.request({
							url : __ctxPath
									+ '/creditFlow/finance/computeMoneySlActualToCharge.do',
							method : 'POST',
							scope : this,
							success : function(response, request) {
								this.gridPanel.getStore().reload();
								e.record.commit();
							},
							failure : function(response) {
								Ext.ux.Toast.msg('状态', '操作失败，请重试');
							},
							params : args
						})
					}
				},
				'cellclick' : function(grid, rowIndex, columnIndex, e) {
				},
				rowdblclick : function(grid, rowIndex, e) {
				}
			}
				// end of columns
		});

		this.gridPanel.addListener('rowdblclick', this.rowClick);

	},// end of the initComponents()

	onRowAction : function(grid, record, action, row, col) {
		this.chargeqlideInfo.call(this, record, 1);
	},

	// 创建记录
	createRs : function() {
		var customerEarnestmoneyScale = this.object.getCmpByName("gLGuaranteeloanProject.customerEarnestmoneyScale").getValue();
		var premiumRate = this.object.getCmpByName("gLGuaranteeloanProject.premiumRate").getValue();
		var projectMoney = this.object.getCmpByName("gLGuaranteeloanProject.projectMoney").getValue();
		if (projectMoney == null || projectMoney == '') {
			Ext.ux.Toast.msg('提示信息', '请先输入贷款金额后再刷新');
			return;
		}
		if (premiumRate == null || premiumRate == '') {
			Ext.ux.Toast.msg('提示信息', '请先输入保费费率后再刷新');
			return;
		}
		if (customerEarnestmoneyScale == null || customerEarnestmoneyScale == '') {
			Ext.ux.Toast.msg('提示信息', '请先输入保证金费率后再刷新');
			return;
		}
		Ext.Ajax.request({
					url : __ctxPath
							+ '/creditFlow/finance/computeMoneySlActualToCharge.do',
					method : 'POST',
					scope : this,
					success : function(response, request) {
						// e.record.commit();
						this.gridPanel.getStore().reload();
					},
					failure : function(response) {
						Ext.ux.Toast.msg('状态', '操作失败，请重试');
					},
					params : {
						businessType : this.businessType,
						projectId : this.projectId,
						computePremiumMoney : premiumRate * projectMoney / 100,
						computeEarnestMoney : customerEarnestmoneyScale * projectMoney / 100,
						oprationType : 'system',
						chargeKey : "'premiumMoney','earnestMoney'",
						isCheck:(this.isCheck==0?0:1)
					}
				});
	},

	// 查看费用流水对账
	chargeqlideInfo : function(record, flag) {
		new chargeDetailView({
					actualChargeId : record.get("actualChargeId"),
					flag : 1,
					hidden2 : true
				}).show();
	},
	// GridPanel行点击处理事件
	rowClick : function(grid, rowindex, e) {
		grid.getSelectionModel().each(function(rec) {
					/*
					 * new SlMortgageForm({ mortId : rec.data.mortId }).show();
					 */
				});
	},
	// 创建记录
	/*
	 * createRs : function() { var premiumRate = this.object
	 * .getCmpByName("gLGuaranteeloanProject.premiumRate").getValue(); var
	 * projectMoney = this.object
	 * .getCmpByName("gLGuaranteeloanProject.projectMoney").getValue(); if
	 * (projectMoney == null || projectMoney == '') { Ext.ux.Toast.msg('提示信息',
	 * '请先输入贷款金额后再刷新'); return; } if (premiumRate == null || premiumRate == '') {
	 * Ext.ux.Toast.msg('提示信息', '请先输入保费费率后再刷新'); return; } Ext.Ajax.request({
	 * url : __ctxPath + '/creditFlow/finance/computeMoneySlActualToCharge.do',
	 * method : 'POST', scope : this, success : function(response, request) { //
	 * e.record.commit(); this.gridPanel.getStore().reload(); }, failure :
	 * function(response) { Ext.ux.Toast.msg('状态', '操作失败，请重试'); }, params : {
	 * businessType : this.businessType, projectId : this.projectId, money :
	 * premiumRate * projectMoney / 100, oprationType : 'system' } }); },
	 */

	chargecheck : function() {
		var gridPanelRecord = this.gridPanel;
		var s = this.gridPanel.getSelectionModel().getSelections();
		if (s <= 0) {
			Ext.ux.Toast.msg('操作信息', '请选中一条记录');
			return false;
		} else if (s.length > 1) {
			Ext.ux.Toast.msg('操作信息', '只能选中一条记录');
			return false;
		} else {
			var record = s[0];
			Ext.Ajax.request({
						url : __ctxPath
								+ '/creditFlow/finance/getSlPlansToCharge.do',
						method : 'POST',
						scope : this,
						params : {
							plansTochargeId : record.data.planChargeId
						},
						success : function(response, request) {
							var obj = Ext.util.JSON
									.decode(response.responseText);
							var name = obj.data.name;
							if (record.data.actualChargeId == "") {
								Ext.ux.Toast.msg('操作信息', '请先保存再对账');

							} else if (record.data.notMoney == 0) {
								Ext.ux.Toast.msg('操作信息', '没有未对账金额');

							} else {
								var flag=0;            //incomeMoney
							     if(record.data.payMoney !=0){   //payMoney
							     	flag=1;
							     }
								new SlActualToChargeForm({
									actualChargeId : record.data.actualChargeId,
									fundType : record.data.planChargeId,
					  			    flag:flag,
									businessType :record.data.businessType,
									notMoney : record.data.notMoney,
									gridPanelRecord : gridPanelRecord
								}).show();
								/*new CashQlideAndCheckForm({
											obj : this.gridPanel,
											projectName : record.data.projectName,
											actualChargeType : name,
											payMoney : record.data.payMoney == 0
													? null
													: record.data.payMoney,
											incomeMoney : record.data.incomeMoney == 0
													? null
													: record.data.incomeMoney,
											actualChargeId : record.data.actualChargeId
										}).show();*/
							}

						}
					});

		}

	},

	// 行的Action
	onRowAction : function(grid, record, action, row, col) {
		switch (action) {
			case 'btn-del' :
				this.removeRs.call(this, record.data.mortId);
				break;
			case 'btn-edit' :
				this.editRs.call(this, record);
				break;
			default :
				break;
		}
	}
});
