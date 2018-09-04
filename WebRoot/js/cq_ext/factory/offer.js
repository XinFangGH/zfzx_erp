Ext.OfferView = Ext.extend(Ext.Panel, {
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		this.initUIComponents();
		Ext.OfferView.superclass.constructor.call(this, {
					id : 'EnterpriseView',
					title : '企业供货商管理',
					region : 'center',
					layout : 'border',
					iconCls : 'btn-team22',
					items : [this.gridPanel]
				});
	},
	initUIComponents : function() {
		this.pageSize = 25;
		this.jStore_enterprise = new Ext.data.JsonStore({
			url : __ctxPath
					+ '/creditFlow/customer/enterprise/ajaxQueryEnterprise.do?isGrantedSeeAllEnterprises='
					+ isGranted('_seeAll_qykh'),
			root : 'topics',
			totalProperty : 'totalProperty',
			remoteSort : true,
			fields : [{
						name : 'id'
					}, {
						name : 'enterprisename'
					}, {
						name : 'shortname'
					}, {
						name : 'ownership'
					}, {
						name : 'registermoney'
					}, {
						name : 'organizecode'
					}, {
						name : 'tradetypev'
					}, {
						name : 'ownershipv'
					}, {
						name : 'telephone'
					}, {
						name : 'legalperson'
					}, {
						name : 'postcoding'
					}, {
						name : 'cciaa'
					}, {
						name : 'managecityName'
					}, {
						name : 'area'
					}, {
						name : 'opendate'
					}, {
						name : 'hangyetypevalue'
					}, {
						name : 'hangyetypevalue'
					}, {
						name : 'orgName'
					}],
			listeners : {
				'loadexception' : function(proxy, options, response, err) {
					var data = Ext.decode(response.responseText);
					Ext.ux.Toast.msg('提示', '数据加载失败，请保证网络畅通！');
				}
			}
		});
		var jStore_enterprise = this.jStore_enterprise;
		// 快速录入
		var isShow = false;
		var itemwidth = 0.2;
		if (RoleType == "control") {
			isShow = true;
			itemwidth = 0.17;
		}
		this.topbar = new Ext.Toolbar({
			items : [{
						iconCls : 'btn-add',
						text : '增加',
						xtype : 'button',
						scope : this,
						handler : function() {
							addEnterpriseWin(this.jStore_enterprise);
						}
					}, new Ext.Toolbar.Separator({
								hidden : (isGranted('_edit_qykh')
										? false
										: true)
										|| (isGranted('_enterprise_ywwl')
												? false
												: true)
							}), {
						iconCls : 'btn-detail',
						text : '业务往来',
						xtype : 'button',
						scope : this,
						hidden : isGranted('_enterprise_ywwl') ? false : true,
						handler : function() {
							var ss = this.gridPanel.getSelectionModel()
									.getSelections();
							var len = ss.length;
							if (len > 1) {
								Ext.ux.Toast.msg('状态', '只能选择一条记录');
							} else if (0 == len) {
								Ext.ux.Toast.msg('状态', '请选择一条记录');
							} else {
								var organizecode = ss[0].data.organizecode;
								var enterpriseId = ss[0].data.id;
								Ext.Ajax.request({
									url : __ctxPath
											+ '/creditFlow/customer/enterprise/isEntEmptyEnterprise.do',
									method : 'post',
									params : {
										organizecode : organizecode
									},
									success : function(response, option) {
										var obj = Ext
												.decode(response.responseText);
										var enterpriseId = obj.data.id
										new EnterpriseAll({
											customerType : 'company_customer',
											customerId : enterpriseId,
											personType : 602,
											shareequityType : 'company_shareequity'
										}).show()
									},
									failure : function(response, option) {
										return true;
										Ext.MessageBox.alert('友情提示',
												"异步通讯失败，请于管理员联系！");
									}
								});
							}
						}

					}]
		});
		
		
		
		this.jStore_enterprise.load({
					params : {
						start : 0,
						limit : this.pageSize
					}
				});
		this.gridPanel = new HT.GridPanel({
					name : 'SmallProjectGrid',
					region : 'center',
					tbar : this.topbar,
					rowActions : false,// 不启用RowActions
					loadMask : true,
					store : this.jStore_enterprise,
					columns : [{
								header : "所属分公司",
								width : 160,
								sortable : true,
								hidden : RoleType == "control" ? false : true,
								dataIndex : 'orgName'
							}, {
								header : "企业名称",
								width : 160,
								sortable : true,
								dataIndex : 'enterprisename'
							}, {
								header : "企业简称",
								width : 120,
								sortable : true,
								dataIndex : 'shortname'
							}, {
								header : "组织机构代码",
								width : 120,
								sortable : true,
								dataIndex : 'organizecode'
							}, {
								header : "营业执照号码",
								width : 120,
								sortable : true,
								dataIndex : 'cciaa'
							}, {
								header : "所有制性质",
								width : 110,
								sortable : true,
								dataIndex : 'ownershipv'
							}, {
								header : "法人",
								width : 55,
								sortable : true,
								dataIndex : 'legalperson'
							}, {
								header : "注册资金",
								width : 80,
								sortable : true,
								dataIndex : 'registermoney',
								renderer : function(val) {
									if (val != "0" || val != null) {
										return val + '万元';
									} else {
										return '';
									}
								}
							}, {
								header : "联系电话",
								width : 100,
								sortable : true,
								dataIndex : 'telephone'
							}, {
								id : 'content',
								header : "企业成立日期",
								width : 90,
								sortable : true,
								dataIndex : 'opendate'
							}],
					listeners : {
						'rowdblclick' : function(grid, index, e) {
							enterpriseId = grid.getSelectionModel()
									.getSelected().get('id');
							seeEnterpriseCustomer(enterpriseId);
						},
						'sortchange' : function(grid, sortInfo) {
						}
					}
				});
	},
	searchByCondition : function() {
		this.jStore_enterprise.baseParams.organizecode = this.fPanel_searchEnterprise
				.getForm().findField('organizecode').getValue();
		this.jStore_enterprise.baseParams.enterprisename = this.fPanel_searchEnterprise
				.getForm().findField('enterprisename').getValue();
		this.jStore_enterprise.baseParams.ownership = this.fPanel_searchEnterprise
				.getForm().findField('ownership').getValue();
		// 新添加查询条件 营业执照号码 隐藏了客户级别
		this.jStore_enterprise.baseParams.cciaa = this.fPanel_searchEnterprise
				.getForm().findField('cciaa').getValue();
		// this.jStore_enterprise.baseParams.customerLevel =
		// this.fPanel_searchEnterprise.getForm().findField('customerLevel').getValue();
		if (null != this.fPanel_searchEnterprise.getForm()
				.findField('companyId')) {
			this.jStore_enterprise.baseParams.companyId = this.fPanel_searchEnterprise
					.getForm().findField('companyId').getValue();
		}
		this.jStore_enterprise.load({
					params : {
						start : 0,
						limit : this.pageSize
					}
				});
	},
	reset : function() {
		this.fPanel_searchEnterprise.getForm().reset();
	},
	addBlackList : function() {
		var selections = this.gridPanel.getSelectionModel().getSelections();
		var grid = this.gridPanel;
		var len = selections.length;
		if (len > 1) {
			Ext.ux.Toast.msg('状态', '只能选择一条记录');
			return;
		} else if (0 == len) {
			Ext.ux.Toast.msg('状态', '请选择一条记录');
			return;
		}
		var enterpriseId = selections[0].data.id;
		var fp = new Ext.FormPanel({
					frame : true,
					labelAlign : 'right',
					bodyStyle : 'padding:5px 5px 5px 5px',
					labelWidth : 60,
					border : false,
					url : __ctxPath
							+ '/creditFlow/customer/enterprise/addBlackEnterprise.do?id='
							+ enterpriseId,
					items : [{
								xtype : 'textarea',
								fieldLabel : '原因说明',
								allowBlank : false,
								name : 'blackReason',
								anchor : '100%'
							}]
				})
	}
});
