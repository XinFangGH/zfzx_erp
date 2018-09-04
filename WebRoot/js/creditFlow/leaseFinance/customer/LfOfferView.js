/**
 * 供货商界面
 * 
 */
LfOfferView = Ext.extend(Ext.Panel, {
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		// 初始化组件
		this.initUIComponents();
		// 调用父类构造
		LfOfferView.superclass.constructor.call(this, {
					id : 'LfOfferView',
					title : '供货方管理',
					region : 'center',
					layout : 'border',
					iconCls : 'btn-tree-team23',
					items : [this.gridPanel]
				});
	},// end of constructor
	// 初始化组件
	initUIComponents : function() {
		// 初始化搜索条件Panel
		var itemwidth = 0.2;
		var isShow = false;
		if (RoleType == "control") {
			isShow = true;
		}

		this.topbar = new Ext.Toolbar({
			items : [{
						iconCls : 'btn-add',
						text : '添加',
						xtype : 'button',
						scope : this,
						hidden : isGranted('_create_grtzkh') ? false : true,
						handler : this.createRs
					}, new Ext.Toolbar.Separator({
								hidden : (isGranted('_create_grtzkh')
										? false
										: true)
										|| (isGranted('_See_grtzkh')
												? false
												: true)
							}), {
						iconCls : 'btn-customer',
						text : '查看',
						xtype : 'button',
						hidden : isGranted('_see_grtzkh') ? false : true,
						scope : this,
						handler : this.checkfindRs
					}, new Ext.Toolbar.Separator({
								hidden : (isGranted('_see_grtzkh')
										? false
										: true)
										|| (isGranted('_edit_grtzkh')
												? false
												: true)
							}), {
						iconCls : 'btn-edit',
						text : '编辑',
						xtype : 'button',
						hidden : isGranted('_edit_grtzkh') ? false : true,
						scope : this,
						handler : this.checkeditRs
					}, new Ext.Toolbar.Separator({
								hidden : (isGranted('_edit_grtzkh')
										? false
										: true)
										|| (isGranted('_remove_grtzkh')
												? false
												: true)
							}), {
						iconCls : 'btn-del',
						text : '删除',
						xtype : 'button',
						hidden : isGranted('_remove_grtzkh') ? false : true,
						scope : this,
						handler : this.removeSelRs
					}, new Ext.Toolbar.Separator({
								hidden : (isGranted('_remove_grtzkh')
										? false
										: true)
										|| (isGranted('_export_grtzkh')
												? false
												: true)
							}), {
						text : '导出Excel',
						iconCls : 'btn-xls',
						scope : this,
						hidden : isGranted('_export_grtzkh') ? false : true,
						handler : function() {
							window
									.open(
											__ctxPath
													+ '/customer/exportExcelInvestPerson.do?isAll='
													+ isGranted('_detail_sygrtzkh'),
											'_blank');
						}
					}]
		});
		var isShow = false;
		if (RoleType == "control") {
			isShow = true;
		}
		this.gridPanel = new HT.GridPanel({
			id : 'bigGrid',
			region : 'center',
			tbar : this.topbar,
			rowActions : false,

			url : __ctxPath
					+ "/creditFlow/leaseFinance/project/listFlObjectSupplior.do?isAll="
					+ isGranted('_detail_sygrtzkh'),
			fields : [{
						name : 'id',
						type : 'int'
					},

					{
						name : 'cname',
						mapping : 'Name'
					}, {
						name : 'mastername',
						mapping : 'legalPersonName'
					}, {
						name : 'cphone',
						mapping : 'companyPhoneNum'
					}, {
						name : 'cfax',
						mapping : 'companyFax'
					}, {
						name : 'newlinkmen',
						mapping : 'connectorName'
					}, {
						name : 'newlinkmenphone',
						mapping : 'connectorPhoneNum'
					}, {
						name : 'caddress',
						mapping : 'companyAddress'
					}, {
						name : 'remark',
						mapping : 'companyComment'
					}, {
						name : 'cposition',
						mapping : 'connectorPosition'
					}],
			columns : [/*{
						header : '序号',
						dataIndex : 'id'
					},*/ {
						header : '供货方名称',
						dataIndex : 'cname'
					}/*, {
						header : '法定代表人(负责人)',
						dataIndex : 'mastername'
					}*/, {
						header : '公司电话',
						dataIndex : 'cphone'
					}/*, {
						header : '传真',
						dataIndex : 'cfax'
					}*/, {
						header : '最近联系人',
						dataIndex : 'newlinkmen'
					}, {
						header : '联系人电话',
						dataIndex : 'newlinkmenphone'

					}, {
						header : '联系人职位',
						dataIndex : 'cposition'
					}, {
						header : '地址',
						dataIndex : 'caddress'
					}/*, {
						header : '备注',
						dataIndex : 'remark'
					}*/]
		});

	},// end of the initComponents()
	// GridPanel行点击处理事件
	rowClick : function(grid, rowindex, e) {
		grid.getSelectionModel().each(function(rec) {
					new InvestPersonForm({
								perId : rec.data.perId
							}).show();
				});
	},
	// 创建记录
	createRs : function() {
		new LfOfferForm({}).show();
	},
	// 按ID删除记录
	removeRs : function(id) {
		$postDel({
					url : __ctxPath
							+ '/creditFlow/leaseFinance/project/multiDelFlObjectSupplior.do',
					ids : id,
					grid : this.gridPanel
				});
	},
	// 把选中ID删除
	removeSelRs : function() {
		$delGridRs({
					url : __ctxPath
							+ '/creditFlow/leaseFinance/project/multiDelFlObjectSupplior.do',
					grid : this.gridPanel,
					idName : 'id'
				});
	},
	// 单选编辑
	checkeditRs : function(record) {
		var s = this.gridPanel.getSelectionModel().getSelections();
		if (s.length <= 0) {
			Ext.ux.Toast.msg('操作信息', '请选中任何一条记录');
			return false;
		} else if (s.length > 1) {
			Ext.ux.Toast.msg('操作信息', '只能选中一条记录');
			return false;
		} else {
			this.ajaxByid(s[0].data.id, false);
		}
	},
	// 查看
	checkfindRs : function() {
		var s = this.gridPanel.getSelectionModel().getSelections();
		if (s <= 0) {
			Ext.ux.Toast.msg('操作信息', '请选中任何一条记录');
			return false;
		} else if (s.length > 1) {
			Ext.ux.Toast.msg('操作信息', '只能选中一条记录');
			return false;
		} else {
			this.ajaxByid(s[0].data.id, true);
		}
	},
	ajaxByid : function(id, v) {
		Ext.Ajax.request({
			url : __ctxPath
					+ '/creditFlow/leaseFinance/project/getFlObjectSupplior.do?id=' + id,
			method : 'POST',
			scope : this,
			success : function(response, request) {
				obj = Ext.util.JSON.decode(response.responseText);
				var offer = obj.data;
				new LfOfferForm({
							Model : offer,
							isEdit : v
						}).show();
			},
			failure : function(response) {
				Ext.ux.Toast.msg('状态', '操作失败，请重试');
			}
		});
	}
});
