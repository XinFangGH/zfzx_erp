// var formulatestore = new HT.JsonStore({
// url : __ctxPath + "/finance/listSlFundIntent.do"
// });

SlActualToChargeVM = Ext.extend(Ext.Panel, {
	// 构造函数
	isHidden : false,
	ttbar : null,
	isHiddenAddBtn : true,
	isHiddenDelBtn : true,
	isHiddenCheckBtn : true,
	slSuperviseRecordId : null,
	isUnLoadData : false,
	isThisSuperviseRecord : null,
	isHiddenTitle : false,
	isThisEarlyPaymentRecord : null,
	isThisEarlyPaymentRecordId : null,
	isThisAlterAccrualRecord : null,
	isThisAlterAccrualRecordId : null,
	isHiddenPanel : false,
	constructor : function(_cfg) {
		if (typeof(_cfg.projId) != "undefined") {
			this.projectId = _cfg.projId;
		}
		if (_cfg.isHidden) {
			this.isHidden = _cfg.isHidden;
		}
		if (typeof(_cfg.businessType) != "undefined") {
			this.businessType = _cfg.businessType;
		}
		if (typeof(_cfg.isHiddenAddBtn) != "undefined") {
			this.isHiddenAddBtn = _cfg.isHiddenAddBtn;
		}
		if (typeof(_cfg.isHiddenDelBtn) != "undefined") {
			this.isHiddenDelBtn = _cfg.isHiddenDelBtn;
		}
		if (typeof(_cfg.enableEdit) != "undefined") {
			this.enableEdit = _cfg.enableEdit;
		}
		if (typeof(_cfg.slSuperviseRecordId) != "undefined") {
			this.slSuperviseRecordId = _cfg.slSuperviseRecordId;
		}
		if (typeof(_cfg.isUnLoadData) != "undefined") {
			this.isUnLoadData = _cfg.isUnLoadData;
		}
		if (typeof(_cfg.isHiddenCheckBtn) != "undefined") {
			this.isHiddenCheckBtn = _cfg.isHiddenCheckBtn;
		}
		if (typeof(_cfg.isThisSuperviseRecord) != "undefined") {
			this.isThisSuperviseRecord = _cfg.isThisSuperviseRecord;
		}
		if (typeof(_cfg.isHiddenTitle) != "undefined") {
			this.isHiddenTitle = _cfg.isHiddenTitle;
		}
		if (typeof(_cfg.isThisEarlyPaymentRecord) != "undefined") {
			this.isThisEarlyPaymentRecord = _cfg.isThisEarlyPaymentRecord;
		}
		if (typeof(_cfg.isThisEarlyPaymentRecordId) != "undefined") {
			this.isThisEarlyPaymentRecordId = _cfg.isThisEarlyPaymentRecordId;
		}
		if (typeof(_cfg.isThisAlterAccrualRecord) != "undefined") {
			this.isThisAlterAccrualRecord = _cfg.isThisAlterAccrualRecord;
		}
		if (typeof(_cfg.isThisAlterAccrualRecordId) != "undefined") {
			this.isThisAlterAccrualRecordId = _cfg.isThisAlterAccrualRecordId;
		}
		if (_cfg.isHiddenPanel) {
			this.isHiddenPanel = _cfg.isHiddenPanel;
		}
		Ext.applyIf(this, _cfg);
		this.initUIComponents();
		SlActualToChargeVM.superclass.constructor.call(this, {
			name : "SlActualToChargeVM_panel",
			hidden : this.isHiddenPanel,
			layout : 'anchor',
			anchor : '100%',
			items : [{
				xtype : 'panel',
				border : false,
				bodyStyle : 'margin-bottom:5px',
				html : '<br/><B><font class="x-myZW-fieldset-title">【手续费用收取清单】</font></B><font class="x-myZW-fieldset-title">（</font>颜色预警：<font color=red>逾期款项</font>&nbsp;&nbsp<font style="line-height:20px">未结清项</font>&nbsp;&nbsp<font color=gray>已结清项</font><font class="x-myZW-fieldset-title" >）：</font>',
				hidden : this.isHiddenTitle
			}, this.gridPanel]
		});

	},
	initUIComponents : function() {
		var summary = new Ext.ux.grid.GridSummary();
		function totalMoney(v, params, data) {
			return '总计(元)';
		}
		this.ttbar = new Ext.Toolbar({
			items : [{
				text : '增加',
				iconCls : 'btn-add',
				scope : this,
				hidden : this.isHiddenAddBtn,
				handler : this.createRs
			}, new Ext.Toolbar.Separator({
				hidden : this.isHiddenAddBtn
			}), {
				iconCls : 'btn-del',
				hidden : this.isHiddenDelBtn,
				scope : this,
				text : '删除',
				handler : this.removeSelRs
			}, new Ext.Toolbar.Separator({
				hidden : this.isHiddenDelBtn
			}), {
				iconCls : 'btn-user-sel',
				hidden : this.isHiddenCheckBtn,
				scope : this,
				text : '对账',
				handler : this.openliushuiwin
			}, new Ext.Toolbar.Separator({
				hidden : this.isHiddenCheckBtn
			}), {
				iconCls : 'btn-detail',
				text : '查看单项流水记录',
				xtype : 'button',
				hidden:this.isHiddenseeqlideBtn,
				scope : this,
				handler : function() {
					var selRs = this.gridPanel.getSelectionModel()
							.getSelections();
					if (selRs <= 0) {
						Ext.ux.Toast.msg('操作信息', '请选中任何一条记录');
						return;
					} else if (selRs.length > 1) {
						Ext.ux.Toast.msg('操作信息', '只能选中一条记录');
						return;
					}
					this.chargeqlideInfo.call(this, selRs[0], 1);
				}
			}]
		})
		this.datafield = new Ext.form.DateField({
			format : 'Y-m-d',
			readOnly : this.isHidden,
			allowBlank : false
		})
		this.comboType1 = new Ext.form.ComboBox({
			mode : 'local',
			displayField : 'name',
			valueField : 'id',
			editable : false,
			readOnly : this.isHidden,
			triggerAction : 'all',
			width : 70,
			autoload : false,
			store : new Ext.data.SimpleStore({
				fields : ["name", "id"],
				data : [["百分比 (%)", "0"], ["固定金额", "1"]]
			})
		})
		this.comboType2 = new Ext.form.ComboBox({
			displayField : 'name',
			valueField : 'plansTochargeId',
			triggerAction : 'all',
			readOnly : this.isHidden,
			store : new Ext.data.JsonStore({
				autoLoad : true,
				url : __ctxPath
						+ '/creditFlow/finance/getallSlPlansToCharge.do?businessType='
						+ this.businessType,
				fields : [{
					name : 'plansTochargeId'
				}, {
					name : 'name'
				}, {
					name : 'chargeStandard'
				}],
				root : 'root',
				totalProperty : 'totalCounts'
			}),
			listeners : {
				'select' : function(combo, record, index) {
					var grid = Ext.getCmp("SlActualToChargeVM");
					grid.getSelectionModel().getSelected().data['chargeStandard'] = record.data.chargeStandard;
				}

			}

		})
		this.comboType2.store.reload();
		// this.comboType2.store.load();
		var url = __ctxPath
				+ "/creditFlow/finance/listbyprojectSlActualToCharge.do?projectId="
				+ this.projectId + "&isUnLoadData=" + this.isUnLoadData
				+ "&businessType=" + this.businessType;
		if (this.isThisSuperviseRecord != null) {
			url = __ctxPath
					+ "/creditFlow/finance/listbySuperviseRecordSlActualToCharge.do?slSuperviseRecordId="
					+ this.slSuperviseRecordId + "&isThisSuperviseRecord="
					+ this.isThisSuperviseRecord + "&isUnLoadData="
					+ this.isUnLoadData + "&businessType=" + this.businessType
					+ "&projectId=" + this.projectId;
		}
		if (this.isThisEarlyPaymentRecord != null) {
			url = __ctxPath
					+ "/creditFlow/finance/listbyEarlyRepaymentRecordSlActualToCharge.do?isThisEarlyPaymentRecordId="
					+ this.isThisEarlyPaymentRecordId
					+ "&isThisEarlyPaymentRecord="
					+ this.isThisEarlyPaymentRecord + "&isUnLoadData="
					+ this.isUnLoadData + "&businessType=" + this.businessType
					+ "&projectId=" + this.projectId;
		}
		if (this.isThisAlterAccrualRecord != null) {
			url = __ctxPath
					+ "/creditFlow/finance/listbyAlterAccrualRecordSlActualToCharge.do?isThisAlterAccrualRecordId="
					+ this.isThisAlterAccrualRecordId
					+ "&isThisAlterAccrualRecord="
					+ this.isThisAlterAccrualRecord + "&isUnLoadData="
					+ this.isUnLoadData + "&businessType=" + this.businessType
					+ "&projectId=" + this.projectId;
		}
		this.gridPanel = new HT.EditorGridPanel({
			rowActions : false,
			border : false,
			clicksToEdit : 1,
			tbar : this.ttbar,
			showPaging : false,
			autoHeight : true,
			id : 'SlActualToChargeVM',
			plugins : [summary],
			url : url,
			fields : [{
				name : 'actualChargeId'
			}, {
				name : 'planChargeId'
			}
					// , {
					// name : 'actualChargeType'
					// }
					, {
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
					}
					// , {
					// name : 'factDate'
					// }
					, {
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
					}, {
						name : 'companyId'
					}],
			columns : [{
				header : '手续费用收取类型',
				dataIndex : 'planChargeId',
				editor : this.comboType2,
				summaryType : 'count',
				summaryRenderer : totalMoney,
				width : 163,
				renderer : function(value, metaData, record, rowIndex,
						colIndex, store) {
					var flag1 = 0; // incomeMoney
					if (record.data.payMoney != 0) { // payMoney
						flag1 = 1;
					}
					var objcom = this.editor;
					var objStore = objcom.getStore();
					var idx = objStore.find("plansTochargeId", value);

					if (idx != "-1") {
						if (record.data.notMoney == 0) {
							return '<font style="color:gray">'
									+ objStore.getAt(idx).data.name + "</font>";
						}
						if (record.data.isOverdue == "是" && flag1 != 1) {

							return '<font style="color:red">'
									+ objStore.getAt(idx).data.name + "</font>";
						}
						return objStore.getAt(idx).data.name;

					} else {

						if (record.data.typeName == "") {

							record.data.typeName = value

						} else {
							var x = store.getModifiedRecords();
							if (x != null && x != "") {
								record.data.typeName = value
							}
						}
						if (record.data.notMoney == 0) {
							return '<font style="color:gray">'
									+ record.get("typeName") + "</font>";
						}
						if (record.data.isOverdue == "是" && flag1 != 1) {

							return '<font style="color:red">'
									+ record.get("typeName") + "</font>";
						}
						return record.get("typeName");

					}
				}/* ZW.ux.comboBoxRenderer(this.comboType2) */

			}, {
				header : '手续费用收取标准',
				dataIndex : 'chargeStandard',
				width : 146,
				editor : new Ext.form.TextField({
					readOnly : this.isHidden
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
									+ "%</font>";
						}
						if (record.data.isOverdue == "是" && flag1 != 1) {

							return '<font style="color:red">' + value
									+ "%</font>";
						}
						return value;

					} else {
						return "";

					}
				}
			}, {
				header : '计划收取金额',
				dataIndex : 'incomeMoney',
				align : 'right',
				summaryType : 'sum',
				width : 115,
				editor : {
					readOnly : this.isHidden,
					xtype : 'numberfield',
					listeners : {
						blur : function(v) {
							var grid = Ext.getCmp("SlActualToChargeVM");
							if (v.getValue() == "") {
								v.setValue("0.00")
							}
							if (v.getValue() != 0.00) {
								grid.getSelectionModel().getSelected().data['payMoney'] = 0;
							}
						}

					}
				},
				renderer : function(value, metaData, record, rowIndex,
						colIndex, store) {
					var flag1 = 0; // incomeMoney
					if (record.data.payMoney != 0) { // payMoney
						flag1 = 1;
					}

					if (record.data.notMoney == 0) {
						return '<font style="color:gray">'
								+ Ext.util.Format.number(value,
										',000,000,000.00') + "元" + "</font>";
					}
					if (record.data.isOverdue == "是" && flag1 != 1) {

						return '<font style="color:red">'
								+ Ext.util.Format.number(value,
										',000,000,000.00') + "元" + "</font>";
					}
					return Ext.util.Format.number(value, ',000,000,000.00')
							+ "元"

				}
			}
			, 
				{
				header : '计划支出金额',
				dataIndex : 'payMoney',
				summaryType : 'sum',
				scope : this,
				align : 'right',
				width : 115,
				editor : {
					readOnly : this.isHidden,
					xtype : 'numberfield',
					listeners : {
						blur : function(v) {
							var grid = Ext.getCmp("SlActualToChargeVM");
							if (v.getValue() == "") {
								v.setValue("0.00")
							}
							if (v.getValue() != 0.00) {
								grid.getSelectionModel().getSelected().data['incomeMoney'] = 0;
							}
						}

					}
				},
				renderer : function(value, metaData, record, rowIndex,
						colIndex, store) {
					var flag1 = 0; // incomeMoney
					if (record.data.payMoney != 0) { // payMoney
						flag1 = 1;
					}

					if (record.data.notMoney == 0) {
						return '<font style="color:gray">'
								+ Ext.util.Format.number(value,
										',000,000,000.00') + "元" + "</font>";
					}
					if (record.data.isOverdue == "是" && flag1 != 1) {

						return '<font style="color:red">'
								+ Ext.util.Format.number(value,
										',000,000,000.00') + "元" + "</font>";
					}
					return Ext.util.Format.number(value, ',000,000,000.00')
							+ "元"

				}
			}
			, {
				header : '计划到账日',
				// xtype : 'datecolumn',
				format : 'Y-m-d',
				dataIndex : 'intentDate',
				editor : this.datafield,
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
						return Ext.util.Format.date(value, 'Y-m-d');
					} catch (err) {
						v = value;
					}
					if (record.data.notMoney == 0) {
						return '<font style="color:gray">' + v + "</font>";
					}
					if (record.data.isOverdue == "是" && flag1 != 1) {

						return '<font style="color:red">' + v + "</font>";
					}
					return v;

				}
			}
			, {
				header : '实际到账日',
				// xtype : 'datecolumn',
				format : 'Y-m-d',
				dataIndex : 'factDate',
				width : 80,
				// renderer : ZW.ux.dateRenderer(this.datafield)
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
							return v;
						}
						return Ext.util.Format.date(value, 'Y-m-d');
					} catch (err) {
						v = value;
						return v;
					}
					if (v != null) {
						if (record.data.notMoney == 0) {
							return '<font style="color:gray">' + v + "</font>";
						}
						if (record.data.isOverdue == "是" && flag1 != 1) {

							return '<font style="color:red">' + v + "</font>";
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
						return Ext.util.Format.number(value, ',000,000,000.00')
								+ "元"

					} else {
						return "";

					}

				}
			}, {
				header : '未对帐金额',
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

						return Ext.util.Format.number(value, ',000,000,000.00')
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
						return Ext.util.Format.number(value, ',000,000,000.00')
								+ "元"

					} else {
						return "";

					}

				}
			}, {
				header : '备注',
				dataIndex : 'remark',
				align : "center",
				width : 170,
				editor : new Ext.form.TextField({
					readOnly : this.isHidden,
					allowBlank : false
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

			}],
			listeners : {
				scope : this,
				beforeedit : function(e) {
					if (e.record.data['afterMoney'] != null
							&& e.record.data['afterMoney'] != 0) {
						e.cancel = true;
					}
					if (e.record.data['actualChargeId'] != "") {
						if (this.enableEdit == true) {
							e.cancel = false;
						} else if (this.enableEdit == false) {
							e.cancel = true;
						}
					} else if (e.record.data['actualChargeId'] == "") {
						e.cancel = false;
					}
				}
			}
		});

	},
	onRowAction : function(grid, record, action, row, col) {
		this.chargeqlideInfo.call(this, record, 1);
	},
	// 查看费用流水对账
	chargeqlideInfo : function(record, flag) {
		new chargeDetailView({
			actualChargeId : record.get("actualChargeId"),
			flag : 1,
			hidden2 : true
		}).show();
	},
	createRs : function() {
		this.datafield.setValue('');
		var newRecord = this.gridPanel.getStore().recordType;
		var newData = new newRecord({
			actualChargeId : '',
			planChargeId : '',
			planCharges : 0,
			planChargeId : '',
			payMoney : 0,
			incomeMoney : 0,
			intentDate : new Date(),
			// factDate : '',
			remark : ''
		});
		this.gridPanel.stopEditing();
		this.gridPanel.getStore().insert(this.gridPanel.getStore().getCount(),
				newData);
		this.gridPanel.getView().refresh();
		this.gridPanel.getSelectionModel().selectRow((this.gridPanel.getStore()
				.getCount() - 1));
		this.gridPanel.startEditing(0, 0);

	},

	createRsNew : function() {
		this.datafield.setValue('');
		var gridadd = this.gridPanel;
		var storeadd = this.gridPanel.getStore();
		var keys = storeadd.fields.keys;
		var p = new Ext.data.Record();
		p.data = {};
		for (var i = 1; i < keys.length; i++) {
			p.data[keys[i]] = null
		}
		var count = storeadd.getCount() + 1;
		gridadd.stopEditing();
		storeadd.addSorted(p);
		gridadd.getView().refresh();

	},
	getGridDate : function() {
		var vRecords = this.gridPanel.getStore().getRange(0,
				this.gridPanel.getStore().getCount()); // 得到修改的数据（记录对象）
		var vCount = vRecords.length; // 得到记录长度
		var vDatas = '';
		if (vCount > 0) {
			// begin 将记录对象转换为字符串（json格式的字符串）
			for (var i = 0; i < vCount; i++) {
			/*	if (vRecords[i].data.planChargeId != null
						&& vRecords[i].data.planChargeId != "") {
					if (vRecords[i].data.actualChargeId == null
							|| vRecords[i].data.actualChargeId == "") {

						var st = "";
						if (vRecords[i].data.factDate == null
								|| vRecords[i].data.factDate == "") {
							st = {
								"planChargeId" : vRecords[i].data.planChargeId,
								"chargeStandard" : vRecords[i].data.chargeStandard,
								"incomeMoney" : vRecords[i].data.incomeMoney,
								"payMoney" : vRecords[i].data.payMoney,
								"intentDate" : vRecords[i].data.intentDate,
								"remark" : vRecords[i].data.remark,
								"slSuperviseRecordId":this.slSuperviseRecordId
							};
						} else {
							st = {
								"planChargeId" : vRecords[i].data.planChargeId,
								"chargeStandard" : vRecords[i].data.chargeStandard,
								"incomeMoney" : vRecords[i].data.incomeMoney,
								"payMoney" : vRecords[i].data.payMoney,
								"intentDate" : vRecords[i].data.intentDate,
								"remark" : vRecords[i].data.remark,
								"slSuperviseRecordId":this.slSuperviseRecordId
							};
						}
						vDatas += Ext.util.JSON.encode(st) + '@';
					} else {
						if (vRecords[i].data.afterMoney == 0) {
							var st = "";
							if (vRecords[i].data.factDate == null
									|| vRecords[i].data.factDate == "") {
								st = {
									"actualChargeId" : vRecords[i].data.actualChargeId,
									"planChargeId" : vRecords[i].data.planChargeId,
									"chargeStandard" : vRecords[i].data.chargeStandard,
									"incomeMoney" : vRecords[i].data.incomeMoney,
									"payMoney" : vRecords[i].data.payMoney,
									"intentDate" : vRecords[i].data.intentDate,
									"remark" : vRecords[i].data.remark,
									"slSuperviseRecordId":this.slSuperviseRecordId
								};
							} else {*/
								st = {
									"planChargeId" : vRecords[i].data.planChargeId,
									"actualChargeId" :( vRecords[i].data.actualChargeId==''?null:vRecords[i].data.actualChargeId),
									"chargeStandard" : vRecords[i].data.chargeStandard,
									"incomeMoney" : vRecords[i].data.incomeMoney,
									"payMoney" : vRecords[i].data.payMoney,
									"intentDate" : vRecords[i].data.intentDate,
									"remark" : vRecords[i].data.remark,
									"slSuperviseRecordId":this.slSuperviseRecordId
								};
							//}
							vDatas += Ext.util.JSON.encode(st) + '@';
/*
						}
					}
				}*/
			}
			vDatas = vDatas.substr(0, vDatas.length - 1);
		}
		// Ext.getCmp('fundIntentJsonData2').setValue(vDatas);
		// this.gridPanel.getColumnsgetEditor().getCmpByName('fundIntentJsonData').setValue(vDatas);
		// alert(this.gridPanel.getEditor().getCmpByName('fundIntentJsonData').getValue());
		return vDatas;
	},
	saveRs : function(flag) {
		var data = this.getGridDate();
		Ext.Ajax.request({
			url : __ctxPath + '/creditFlow/finance/savejsonSlActualToCharge.do',
			method : 'POST',
			scope : this,
			params : {
				slPlansToChargeJson : data,
				projectId : this.projectId,
				businessType : "SmallLoan", // 小贷
				flag : flag
			},
			success : function(response, request) {
				this.comboType2.store.reload();
				this.gridPanel.getStore().reload();

			}
		});

	},
	comboTypereload : function() {
		this.comboType2.store.reload();
	},
	savereload : function() {
		this.comboType2.store.reload();
		this.gridPanel.getStore().reload();
	},	
	openliushuiwin : function() {
				
				var s = this.gridPanel.getSelectionModel().getSelections();
				if (s <= 0) {
					Ext.ux.Toast.msg('操作信息','请选中记录');
					return false;
				}else if(s.length>1){
					this.manyInntentopenliushuiwin();
					
				}else if(s.length==1){
				   this.oneopenliushuiwin();
				}
				
			},
			oneopenliushuiwin :function(){
				var s = this.gridPanel.getSelectionModel().getSelections();
				var	record=s[0];
			     var flag=0;            //incomeMoney
			     if(record.data.payMoney !=0){   //payMoney
			     	flag=1;
			     } 
			     if(record.data.actualChargeId ==null){
	     		Ext.ux.Toast.msg('操作信息','请先保存');
	           }else{
					new SlActualToChargeForm({
						parentGrid:this.gridPanel,
						actualChargeId : record.data.actualChargeId,
						fundType : record.data.planChargeId,
		  			    flag:flag,
						businessType :record.data.businessType,
						notMoney : record.data.notMoney,
						companyId:record.data.companyId
				}).show();
	           }
			
			},
			manyInntentopenliushuiwin :function(){
			
			var s = this.gridPanel.getSelectionModel().getSelections();
				if (s <= 0) {
					Ext.ux.Toast.msg('操作信息','请选中记录');
					return false;
				}else{
			    var a=0;
			    var b=0;
			    var sumnotMoney=0;
			    for(var i=0;i<s.length;i++){
			    	if(s[i].data.payMoney >0)
			    	a++;
			    	if(s[i].data.incomeMoney >0)
				    b++;
			    	sumnotMoney=sumnotMoney+s[i].data.notMoney;
			    }
			    if(a>0 && b>0){
			    	Ext.ux.Toast.msg('操作信息','请选中的记录支出保持一致');
					return false;
			    	
			    }
			    
				var ids = $getGdSelectedIds(this.gridPanel,'actualChargeId');
				var	record=s[0];
			      var flag=0;            //incomeMoney
			     if(record.data.payMoney !=0){   //payMoney
			     	flag=1;
			     }
			      var count=0;
		     for(var i=0;i<ids.length;i++){
		       if(s[i].data.actualChargeId ==null)count++;
		     }
		     
		     if(count>0){
		     		Ext.ux.Toast.msg('操作信息','请先保存');
		     }else{

					new SlActualToChargeForm1({
						parentGrid:this.gridPanel,
						ids : ids,
						fundType : record.data.fundType,
						sumnotMoney :sumnotMoney,
						flag : flag,
						companyId:record.data.companyId
					}).show();
		     }
				}	
			},
	chargecheck : function() {
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
				url : __ctxPath + '/creditFlow/finance/getSlPlansToCharge.do',
				method : 'POST',
				scope : this,
				params : {
					plansTochargeId : record.data.planChargeId
				},
				success : function(response, request) {
					var obj = Ext.util.JSON.decode(response.responseText);
					var name = obj.data.name;
					if (record.data.actualChargeId == "") {
						Ext.ux.Toast.msg('操作信息', '请先保存再对账');

					} else if (record.data.notMoney == 0) {
						Ext.ux.Toast.msg('操作信息', '没有未对账金额');

					} else {
						new CashQlideAndCheckForm({
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
						}).show();
					}

				}
			});

		}

	},
	notcheck : function() {
		this.gridPanel.getStore().reload();
		var vRecords = this.gridPanel.getStore().getRange(0,
				this.gridPanel.getStore().getCount());
		var vCount = vRecords.length;
		var chargenotMoney = 0;
		if (vCount > 0) {
			for (var i = 0; i < vCount; i++) {
				chargenotMoney += vRecords[i].data.notMoney;
			}
		}
		return chargenotMoney;
	},
	removeSelRs : function() {
		var fundIntentGridPanel = this.gridPanel;
		var deleteFun = function(url, prame, sucessFun, i, j) {
			Ext.Ajax.request({
				url : url,
				method : 'POST',
				success : function(response) {
					if (response.responseText.trim() == '{success:true}') {
						if (i == (j - 1)) {
							Ext.ux.Toast.msg('操作信息', '删除成功!');
						}
						sucessFun();
					} else if (response.responseText.trim() == '{success:false}') {
						Ext.ux.Toast.msg('操作信息', '删除失败!');
					}
				},
				params : prame
			});
		};
		var a = fundIntentGridPanel.getSelectionModel().getSelections();
		if (a <= 0) {
			Ext.ux.Toast.msg('操作信息', '请选择要删除的记录');
			return false;
		};
		Ext.Msg.confirm("提示!", '确定要删除吗？', function(btn) {

			if (btn == "yes") {
				// grid_sharteequity.stopEditing();
				var s = fundIntentGridPanel.getSelectionModel().getSelections();

				for (var i = 0; i < s.length; i++) {
					var row = s[i];
					if (row.data.actualChargeId == null
							|| row.data.actualChargeId == '') {
						fundIntentGridPanel.getStore().remove(row);
					} else {
						deleteFun(
								__ctxPath
										+ '/creditFlow/finance/deleteSlActualToCharge.do',
								{
									actualChargeId : row.data.actualChargeId,
									businessType : this.businessType
								}, function() {
									fundIntentGridPanel.getStore().remove(row);
									fundIntentGridPanel.getStore().reload();
								}, i, s.length);

					}

				}

			}

		});

	}
});