/**
 * @author:
 * @class SlBankAccountView
 * @extends Ext.Panel
 * @description [SlBankAccount]管理
 * @company 北京互融时代软件有限公司
 * @createtime:
 */
SlBankAccountView = Ext.extend(Ext.Panel, {
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		// 初始化组件
		this.initUIComponents();
		// 调用父类构造
		SlBankAccountView.superclass.constructor.call(this, {
			id : 'SlBankAccountView_' + this.type,
			title : '银行账户管理',
			region : 'center',
			layout : 'border',
			iconCls : 'btn-team43',
			items : [this.searchPanel, this.gridPanel]
		});
	},// end of constructor
	// 初始化组件
	initUIComponents : function() {
		var summary = new Ext.ux.grid.GridSummary();
		function totalMoney(v, params, data) {
			return '总计(元)';
		}
		// 初始化搜索条件Panel

		var isShow = false;
		if (RoleType == "control") {
			isShow = true;
		}
		this.searchPanel = new HT.SearchPanel({
			id : 'processModuleSearchPanel_' + this.type,
			layout : 'form',
			border : false,
			region : 'north',
			height : 65,
			anchor : '70%',
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
				style : 'padding-left:5px;padding-right:0px;padding-top:5px;',
				layoutConfig : {
					align : 'middle',
					padding : '5px'
				},
				defaults : {
					xtype : 'label',
					anchor : '95%'
				},
				items : [{
					columnWidth : .2,
					labelAlign : 'right',
					xtype : 'container',
					layout : 'form',
					labelWidth : 60,
					defaults : {
						anchor : '100%'
					},
					items : [{
						xtype : 'combo',
						mode : 'local',
						displayField : 'name',
						valueField : 'id',
						editable : false,
						store : new Ext.data.SimpleStore({
							fields : ["name", "id"],
							data : [["个人", "0"], ["公司", "1"]]
						}),
						triggerAction : "all",
						hiddenName : "Q_openType_SN_EQ",
						fieldLabel : '开户类型',
						name : 'Q_openType_SN_EQ',
						listeners : {
							scope : this,
							select : function(combox, record, index) {
								var v = record.data.id;
								var obj = this.getCmpByName('Q_accountType_SN_EQ');
								obj.enable();
								var arrStore = null;
								if (v == 0) {
									arrStore = new Ext.data.SimpleStore({
										fields : ["name", "id"],
										data : [["个人储蓄户", "0"]]
									});
								} else {
									arrStore = new Ext.data.SimpleStore({
										fields : ["name", "id"],
										data : [["基本户", "1"],["一般户", "2"]]
									});
								}
								obj.clearValue();
								obj.store = arrStore;
								obj.view.setStore(arrStore);
							}
						}
					}, {
						fieldLabel : '账号',
						name : 'Q_account_S_LK',
						xtype : 'textfield'
					}]
				}, {
					columnWidth : .2,
					labelAlign : 'right',
					xtype : 'container',
					layout : 'form',
					labelWidth : 60,
					defaults : {
						anchor : '100%'
					},
					items : [{
						xtype : 'combo',
						mode : 'local',
						displayField : 'name',
						valueField : 'id',
						editable : false,
						store : new Ext.data.SimpleStore({}),
						triggerAction : "all",
						hiddenName : "Q_accountType_SN_EQ",
						fieldLabel : '账户类型',
						name : 'Q_accountType_SN_EQ'
					}, {
						fieldLabel : '开户名称',
						name : 'Q_name_S_LK',
						xtype : 'textfield'
					}]
				}, {
					columnWidth : .2,
					labelAlign : 'right',
					xtype : 'container',
					layout : 'form',
					labelWidth : 80,
					defaults : {
						anchor : '99%'
					},
					items : [{
						fieldLabel : '期初金额: 从',
						name : 'Q_rawMoney_BD_GE',
						labelSeparator : '',
						xtype : 'numberfield'
					}, {
						fieldLabel : '开户时间: 从',
						name : 'Q_recordTime_D_GE',
						labelSeparator : '',
						xtype : 'datefield',
						format : 'Y-m-d'
					}]
				}, {
					columnWidth : .15,
					labelAlign : 'right',
					xtype : 'container',
					layout : 'form',
					labelWidth : 7,
					defaults : {
						anchor : '95%'
					},
					items : [{
						fieldLabel : '到',
						labelSeparator : '',
						name : 'Q_rawMoney_BD_LE',
						xtype : 'numberfield'
					}, {
						fieldLabel : '到',
						labelSeparator : '',
						name : 'Q_recordTime_D_LE',
						xtype : 'datefield',
						format : 'Y-m-d'
					}]
				}, isShow ? {
					columnWidth : .16,
					labelAlign : 'right',
					xtype : 'container',
					layout : 'form',
					labelWidth : 70,
					defaults : {
						anchor : '100%'
					},
					border : false,
					items : [{
						xtype : "combo",
						anchor : "100%",
						fieldLabel : '所属分公司',
						hiddenName : "companyId",
						displayField : 'companyName',
						valueField : 'companyId',
						triggerAction : 'all',
						store : new Ext.data.SimpleStore({
							autoLoad : true,
							url : __ctxPath+ '/system/getControlNameOrganization.do',
							fields : ['companyId', 'companyName']
						})
					}]
				}: {
					columnWidth : 0.001,
					border : false
				}, {
					columnWidth : .08,
					xtype : 'container',
					layout : 'form',
					defaults : {
						xtype : 'button'
					},
					style : 'padding-left:10px;',
					items : [{
						text : '查询',
						scope : this,
						iconCls : 'btn-search',
						handler : this.search
					}, {
						style : 'padding-top:3px;',
						text : '重置',
						scope : this,
						iconCls : 'btn-reset',
						handler : this.reset
					}]
				}]
			}]
		})

		this.topbar = new Ext.Toolbar({
			items : [{
				iconCls : 'btn-add',
				text : '添加',
				xtype : 'button',
				scope : this,
				hidden : isGranted('_createAccount_b_' + this.type)? false: true,
				handler : this.createRs
			}, new Ext.Toolbar.Separator({
				hidden : isGranted('_createAccount_b_' + this.type)? false: true
			}), {
				iconCls : 'btn-del',
				text : '删除',
				xtype : 'button',
				scope : this,
				hidden : isGranted('_remove_b_' + this.type) ? false : true,
				handler : this.removeSelRs
			}, new Ext.Toolbar.Separator({hidden : isGranted('_remove_b_' + this.type)? false: true
			}), {
				iconCls : 'btn-detail',
				text : '查看',
				xtype : 'button',
				scope : this,
				hidden : isGranted('_see_b_' + this.type) ? false : true,
				handler : this.seeRs
			}, new Ext.Toolbar.Separator({hidden : isGranted('_see_b_' + this.type)? false: true
			}), {
				iconCls : 'btn-edit',
				text : '编辑',
				xtype : 'button',
				scope : this,
				hidden : isGranted('_edit_b_' + this.type) ? false : true,
				handler : this.editRs
			}, new Ext.Toolbar.Separator({
				hidden : isGranted('_edit_b_' + this.type)? false: true
			}), {
				iconCls : 'btn-detail',
				text : '账户流水明细',
				xtype : 'button',
				scope : this,
				hidden : isGranted('_zhlsdetail_b_' + this.type) ? false : true,
				handler : this.qlidedetail
			}, new Ext.Toolbar.Separator({
				hidden : (isGranted('_zhlsdetail_b_' + this.type)? false: true)|| (isGranted('_uploadyhzh_b_' + this.type)? false: true)
			})]
		});

		this.gridPanel = new HT.GridPanel({
			region : 'center',
			tbar : this.topbar,
			// 使用RowActions
			rowActions : false,
			id : 'SlBankAccountGrid_' + this.type,
			plugins : [summary],
			viewConfig : {
				forceFit : true
				// 横向滚动条
			},
			url : __ctxPath + "/creditFlow/finance/listSlBankAccount.do",
			fields : [{
						name : 'bankAccountId',
						type : 'int'
					}, 'openType', 'accountType', 'bankName', 'name',
					'account', 'rawMoney', 'finalMoney', 'recordTimeString',
					'remarks', 'address', 'finalDate', 'frozenfinalMoney',
					'surplusfinalMoney', 'orgName'],
			columns : [{
				header : 'bankAccountId',
				align:'center',
				dataIndex : 'bankAccountId',
				hidden : true
			}, {
				header : "所属分公司",
				align:'center',
				sortable : true,
				width : 120,
				hidden : RoleType == "control" ? false : true,
				dataIndex : 'orgName'
			}, {
				header : '开户银行',
				align:'center',
				dataIndex : 'bankName',
				summaryType : 'count',
				width : 200,
				summaryRenderer : totalMoney

			}, {
				header : '账号',
				width : 120,
				dataIndex : 'account',
				renderer : function(v) {
					var card = v;
					var len = v.length;
					var reg = /\s{1,}/g;
					var card_ = "";
					// 去除空格
					card = card.replace(reg, "");
					for (var i = 0; i < len; i++) {
						if (i == 3 || i == 7 || i == 11 || i == 15
								|| i == 19 || i == 23 || i == 27
								|| i == 31 || i == 34 || i == 37) {
							card_ = card_ + card.charAt(i) + " ";
						} else {
							card_ = card_ + card.charAt(i);
						}
					}
					return card_
				}
			}, {
				header : '开户名称',
				width : 120,
				align:'center',
				dataIndex : 'name'
			}, {
				header : '账户类型',
				align:'center',
				dataIndex : 'accountType',
				width : 72,
				renderer : function(v) {
					switch (v) {
						case 0 :
							return '个人储蓄户';
							break;
						case 1 :
							return '基本户';
							break;
						case 2 :
							return '一般户';
							break;
					}
				}
			}, {
				header : '开户类型',
				align:'center',
				dataIndex : 'openType',
				width : 70,
				renderer : function(v) {
					switch (v) {
						case 0 :
							return '个人';
							break;
						case 1 :
							return '公司';
							break;
					}
				}

			}, {
				header : '期初金额(元)',
				dataIndex : 'rawMoney',
				align : 'right',
				summaryType : 'sum',
				width : 150,
				sortable : true,
				renderer : function(v) {
					return Ext.util.Format.number(v, ',000,000,000.00');
				}
			}, {
				header : '期末总余额(元)',
				dataIndex : 'finalMoney',
				width : 150,
				align : 'right',
				summaryType : 'sum',
				sortable : true,
				renderer : function(v) {
					return Ext.util.Format.number(v, ',000,000,000.00');
				}
			}, {
				header : '期末统计时间',
				dataIndex : 'finalDate',
				align : 'center',
				width : 106,
				sortable : true
			}, {
				header : '银行地址',
				dataIndex : 'address',
				align : 'left',
				width : 76
			}, {
				header : '备注',
				dataIndex : 'remarks',
				align : 'left',
				width : 76

			}]
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
			new SlBankAccountForm({
				bankAccountId : rec.data.bankAccountId,
				isAllReadOnly : false
			}).show();
		});
	},
	// 创建记录
	createRs : function() {
		new SlBankAccountForm().show();
	},
	// 按ID删除记录
	removeRs : function(id) {
		$postDel({
			url : __ctxPath+ '/creditFlow/finance/multiDelSlBankAccount.do',
			ids : id,
			grid : this.gridPanel,
			error : '确定要删除？'
		});
	},
	// 把选中ID删除
	removeSelRs : function() {
		$delGridRs({
			url : __ctxPath+ '/creditFlow/finance/multiDelSlBankAccount.do',
			grid : this.gridPanel,
			idName : 'bankAccountId',
			error : '确定要删除？'
		});
	},
	// 查看Rs
	seeRs : function() {
		var s = this.gridPanel.getSelectionModel().getSelections();
		if (s <= 0) {
			Ext.ux.Toast.msg('操作信息', '请选中任何一条记录');
			return false;
		} else if (s.length > 1) {
			Ext.ux.Toast.msg('操作信息', '只能选中一条记录');
			return false;
		} else {
			record = s[0]
			new SlBankAccountForm({
				bankAccountId : record.data.bankAccountId,
				bankName : record.data.bankName,
				isAllReadOnly : true
			}).show();
		}
	},
	// 编辑Rs
	editRs : function() {
		var s = this.gridPanel.getSelectionModel().getSelections();
		if (s <= 0) {
			Ext.ux.Toast.msg('操作信息', '请选中任何一条记录');
			return false;
		} else if (s.length > 1) {
			Ext.ux.Toast.msg('操作信息', '只能选中一条记录');
			return false;
		} else {
			record = s[0]
			new SlBankAccountForm({
				bankAccountId : record.data.bankAccountId,
				bankName : record.data.bankName,
				isAllReadOnly : false
			}).show();
		}
	},
	qlidedetail : function() {
		var s = this.gridPanel.getSelectionModel().getSelections();
		if (s <= 0) {
			Ext.ux.Toast.msg('操作信息', '请选中任何一条记录');
			return false;
		} else if (s.length > 1) {
			Ext.ux.Toast.msg('操作信息', '只能选中一条记录');
			return false;
		} else {
			record = s[0]
			new SlBankAccountQlide({
				bankName : record.data.bankName,
				name : record.data.name,
				account : record.data.account
			}).show();
		}
	},
	upload : function() {
		new SlBankAccountUpload({
			gridPanel : this.gridPanel,
			flag : "bankaccountupload"
		}).show();
	},
	// 行的Action
	onRowAction : function(grid, record, action, row, col) {
		switch (action) {
			case 'btn-del' :
				this.removeRs.call(this, record.data.bankAccountId);
				break;
			case 'btn-edit' :
				this.editRs.call(this, record);
				break;
			default :
				break;
		}
	}
});