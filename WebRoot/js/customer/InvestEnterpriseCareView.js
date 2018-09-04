InvestEnterpriseCareView = Ext.extend(Ext.Panel, {
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		this.initUIComponents();
		InvestEnterpriseCareView.superclass.constructor.call(this, {
					id : 'InvestEnterpriseCareView',
					title : '企业客户管理',
					region : 'center',
					layout : 'border',
					iconCls : 'btn-team22',
					items : [this.fPanel_searchEnterprise, this.gridPanel]
				});
	},
	initUIComponents : function() {
		
		var isShow = false;
		var itemwidth = 0.2;
		if (RoleType == "control") {
			isShow = true;
			itemwidth = 0.17;
		}
		this.fPanel_searchEnterprise = new Ext.FormPanel({
			frame : false,
			region : 'north',
			height : 35,
			monitorValid : true,
			layout : 'column',
			bodyStyle : 'padding:0px 0px 0px 0px',
			border : false,
			defaults : {
				layout : 'form',
				border : false,
				labelWidth : 80,
				bodyStyle : 'padding:5px 0px 0px 5px'
			},
			labelAlign : "right",
			keys : [{
						key : Ext.EventObject.ENTER,
						fn : this.searchByCondition,
						scope : this
					}, {
						key : Ext.EventObject.ESC,
						fn : this.reset,
						scope : this
					}],
			items : [isShow ? {
				columnWidth : itemwidth,
				bodyStyle : 'padding:5px 0px 0px 0px',
				items : [{
					xtype : "combo",
					labelWidth : "65",
					anchor : "100%",
					fieldLabel : '所属分公司',
					hiddenName : "companyId",

					displayField : 'companyName',
					valueField : 'companyId',
					triggerAction : 'all',
					store : new Ext.data.SimpleStore({
								autoLoad : true,
								url : __ctxPath
										+ '/system/getControlNameOrganization.do',
								fields : ['companyId', 'companyName']
							})
				}]
			}
					: {
						columnWidth : 0.01
					}, {
				columnWidth : itemwidth,
				bodyStyle : 'padding:5px 0px 0px 0px',
				items : [{
							xtype : 'textfield',
							fieldLabel : '企业名称',
							name : 'enterprisename',
							maxLength : 50,
							maxLengthText : '长度不能超过50',
							labelWidth : "65",
							anchor : "100%"
						}]
			}, {
				columnWidth : itemwidth,
				items : [{
							xtype : 'textfield',
							fieldLabel : '组织机构代码',
							name : 'organizecode',
							labelWidth : "65",
							anchor : "100%"
						}]
			}, {
				columnWidth : itemwidth,
				items : [{
							xtype : "combo",
							triggerClass : 'x-form-search-trigger',
							name : "hangyeType",
							fieldLabel : "行业类别",
							anchor : '100%',
							scope : this,
							readOnly : this.isAllReadOnly,
							onTriggerClick : function(e) {
								var obj = this;
								var oobbj = obj.ownerCt.ownerCt
										.getCmpByName("hangye");
								selectTradeCategory(obj, oobbj);
							}
						},{
							xtype : 'hidden',
							name : 'hangye'
						}]
			}, {
				columnWidth : itemwidth,
				items : [{
							xtype : 'textfield',
							fieldLabel : '营业执照号码',
							name : 'cciaa',
							labelWidth : "65",
							anchor : "100%"
						}]

			}, {
				columnWidth : 0.07,
				items : [{
							id : 'searchButton',
							xtype : 'button',
							text : '查询',
							iconCls : 'btn-search',
							width : 60,
							// labelWidth : "30",
							formBind : true,
							labelWidth : 20,
							bodyStyle : 'padding:5px 0px 0px 0px',
							scope : this,
							handler : this.search
						}]
			}, {

				columnWidth : 0.07,
				items : [{
							xtype : 'button',
							text : '重置',
							width : 60,
							scope : this,
							iconCls : 'btn-reset',
							handler : this.reset
						}]

			}]
		});
		this.topbar = new Ext.Toolbar({
					items : [{
								iconCls : 'btn-add',
								text : '新增客户关怀记录',
								xtype : 'button',
								scope : this,
								hidden : isGranted('_create_tzghqykh_invest')
										? false
										: true,
								handler : this.createRs
							}, new Ext.Toolbar.Separator({
										hidden : (isGranted('_create_tzghqykh_invest')
												? false
												: true)
												
									}), {
								iconCls : 'btn-customer',
								text : '查看客户关怀记录',
								xtype : 'button',
								hidden : isGranted('_see_tzghqykh_invest')
										? false
										: true,
								scope : this,
								handler : this.seeRs
							}, new Ext.Toolbar.Separator({
								hidden : (isGranted('_see_tzghqykh_invest')
										? false
										: true)
									// ||(isGranted('_edit_tzghkh') ? false :
									// true)
								}), {
								iconCls : 'btn-edit',
								text : '编辑客户关怀记录',
								xtype : 'button',
								hidden : isGranted('_edit_tzghqykh_invest')
										? false
										: true,
								scope : this,
								handler : this.editRs
							}]
				});
		this.gridPanel = new HT.GridPanel({
					region : 'center',
					tbar : this.topbar,
					rowActions : false,// 不启用RowActions
					loadMask : true,
					url : __ctxPath + "/customer/listInvestEnterprise.do",
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
						name : 'mainHangye'
					}, {
						name : 'mainperson'
					}],
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
								header : "行业类别",
								width : 100,
								sortable : true,
								dataIndex : 'mainHangye'
							}, {
								header : "法人",
								width : 100,
								sortable : true,
								dataIndex : 'mainperson'
							}, {
								header : "联系电话",
								width : 100,
								sortable : true,
								dataIndex : 'telephone'
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
	search : function() {
		$search( {
			searchPanel : this.fPanel_searchEnterprise,
			gridPanel : this.gridPanel
		});
	},
	reset : function() {
		this.fPanel_searchEnterprise.getForm().reset();
	},
	createRs : function(){
		var s = this.gridPanel.getSelectionModel().getSelections();
		if (s <= 0) {
			Ext.ux.Toast.msg('操作信息', '请选中任何一条记录');
			return false;
		} else if (s.length > 1) {
			Ext.ux.Toast.msg('操作信息', '只能选中一条记录');
			return false;
		} else {
			record = s[0]
			new InvestEnterpriseCareForm({
				enterpriseId:record.data.id
			}).show();
		}
	},
	editRs : function(){
		var s = this.gridPanel.getSelectionModel().getSelections();
		if (s <= 0) {
			Ext.ux.Toast.msg('操作信息','请选中任何一条记录');
			return false;
		}else if(s.length>1){
			Ext.ux.Toast.msg('操作信息','只能选中一条记录');
			return false;
		}else{	
			record=s[0]
			new InvestEnterpriseCareForm({
				enterpriseId:record.data.id,
				careHidden :true
			}).show();
		}	
	},
	seeRs : function(){
		var s = this.gridPanel.getSelectionModel().getSelections();
		if (s <= 0) {
			Ext.ux.Toast.msg('操作信息','请选中任何一条记录');
			return false;
		}else if(s.length>1){
			Ext.ux.Toast.msg('操作信息','只能选中一条记录');
			return false;
		}else{	
			record=s[0]
			new InvestEnterpriseCareForm({
				enterpriseId:record.data.id,
				careHidden :true,
				isHiddenEdit:true
			}).show();
		}	
	}
});



