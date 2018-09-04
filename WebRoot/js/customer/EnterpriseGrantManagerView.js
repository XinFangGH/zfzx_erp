EnterpriseGrantManagerView = Ext.extend(Ext.Panel, {
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		this.initUIComponents();
		EnterpriseGrantManagerView.superclass.constructor.call(this, {
					id : 'EnterpriseGrantManagerView',
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
						iconCls : 'btn-detail',
						text : '查看客户详细',
						xtype : 'button',
						scope : this,// handler作用域,must
						 hidden : isGranted('_see_enterprise_invest') ? false :
						 true,
						handler : this.seeRs
					}, new Ext.Toolbar.Separator({
							 hidden : isGranted('_see_enterprise_invest')
							 ? false
							 : true
							}), {
						iconCls : 'menu-customer-manager',
						text : '授权共享人',
						xtype : 'button',
						scope : this,
						 hidden : isGranted('_enterprise_skgxr_invest') ? false : true,
						handler : this.grant
					}, '->', {
						xtype : 'label',
						html : '【<font style="line-height:20px">注：</font>'
					}, {
						xtype : 'label',
						html : '<font color=red>&nbsp;&nbsp字体颜色为红色的用户为已失效用户</font>】'
					}]
		})
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
					},{
						name : 'creater'
					},{
						name : 'belongedName'
					},{
						name : 'depName'
					},{
						name : 'belongedId'
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
							},{
								header : '创建人',
				//				width : 70,
								dataIndex : 'creater'
							}, {
								header : '共享人',
				//				width : 260,
								dataIndex : 'belongedName'
							}, {
								header : '共享人所在部门',
				//				width : 260,
								dataIndex : 'depName'
							}],
					listeners : {
						'rowdblclick' : function(grid, index, e) {
							var enterpriseId = grid.getSelectionModel()
									.getSelected().get('id');
							new InvestEnterpriseForm( {
								id : enterpriseId,isAllReadOnly : true,gridpanel : this.gridPanel
							}).show();
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
	grant : function() {
		var idCustomerTypeStr = "";
		var belongedIdStr = "";
		var belongerStr = "";
		var grid = this.gridPanel;
		var selRs = grid.getSelectionModel().getSelections();
		for (var i = 0; i < selRs.length; i++) {
			idCustomerTypeStr += selRs[i].data.id + ";InvestEnterprise,";
			belongedIdStr += selRs[i].data.belongedId + ",";
			belongerStr += selRs[i].data.belongedName + ",";
		}
		idCustomerTypeStr = idCustomerTypeStr.substring(0,idCustomerTypeStr.length - 1);
		belongedIdStr = belongedIdStr.substring(0, belongedIdStr.length - 1);
		belongerStr = belongerStr.substring(0, belongerStr.length - 1);
		new UserDialog({
			single : false,
			title : "授权共享人",
			userIds : selRs.length > 1?"":belongedIdStr,
			userName : selRs.length > 1?"":belongerStr,
			callback : function(uId, uname) {
				Ext.Msg.confirm('信息确认', '确认为选中的'+selRs.length+'位客户授权共享人吗？', function(btn) {
					if (btn == 'yes') {
						Ext.Ajax.request({
							url : __ctxPath
									+ "/customer/grantBelongerCustomer.do",
							method : "post",
							params : {
								idCustomerTypeStr : idCustomerTypeStr,
								userIdStr : uId
							},
							scope : this,
							success : function(response) {
								grid.getStore().reload();
								Ext.ux.Toast.msg('操作信息', '授权成功！');
							},
							failure : function() {
								Ext.ux.Toast.msg('操作信息', '授权失败！');
							}
						})
					}
				})
			}
		}).show();

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
			new InvestEnterpriseForm( {
				id : record.data.id,isAllReadOnly : true,gridpanel : this.gridPanel
			}).show();
		}	
	}
});



