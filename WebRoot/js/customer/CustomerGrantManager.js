/**
 * @author lisl
 * @extends Ext.Panel
 * @description 客户授权管理
 * @company 智维软件
 * @createtime:
 */
CustomerGrantManager = Ext.extend(Ext.Panel, {
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		// 初始化组件
		this.initUIComponents();

		// 调用父类构造
		CustomerGrantManager.superclass.constructor.call(this, {
			title : "客户资源分配管理",
			id :"CustomerGrantManager",
			region : 'center',
			layout : 'border',
			iconCls : 'btn-tree-team23',
			items : [this.searchPanel, this.gridPanel]
		});
	},// end of constructor
	// 初始化组件
	initUIComponents : function() {
		var isShow = false;
		var rightValue =  isGranted('_customerGrantManage_see_All');
		var isShop = isGranted('_customerGrantManage_see_shop');
		if (RoleType == "control") {
			isShow = true;

		}
		var anchor = '100%';
		this.searchPanel = new Ext.FormPanel({
			layout : 'form',
			border : false,
			region : 'north',
			height : 43,
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
				style : 'padding-left:5px;padding-right:5px;padding-top:10px;',
				layoutConfig : {
					align : 'middle',
					padding : '5px'
				},
				defaults : {
					xtype : 'label',
					anchor : anchor

				},
				items : [ {
					columnWidth : .2,
					labelAlign : 'right',
					xtype : 'container',
					layout : 'form',
					labelWidth : 60,
					defaults : {
						anchor : anchor
					},
					items : [{
						fieldLabel : '客户名称',
						name : 'investName',
						xtype : 'textfield'
					}]
				}, {
					columnWidth : .2,
					labelAlign : 'right',
					xtype : 'container',
					layout : 'form',
					labelWidth : 60,
					defaults : {
						anchor : anchor
					},
					items : [{
						fieldLabel : '证件号码',
						name : 'cardnumber',
						xtype : 'textfield'
					}]
				}, {
					columnWidth : .2,
					labelAlign : 'right',
					xtype : 'container',
					layout : 'form',
					labelWidth : 70,
					defaults : {
						anchor : anchor
					},
					items : [{
						name : 'belonger',
						xtype : 'trigger',
						fieldLabel : '共享人',
						submitValue : true,
						triggerClass : 'x-form-search-trigger',
						editable : false,
						scope : this,
						onTriggerClick : function() {
							var obj = this;
							UserSelector.getView(function(id, name) {
								obj.setValue(name);
								obj.ownerCt.ownerCt
										.getCmpByName('belongedId')
										.setValue(id);
							}, true).show();// false为多选,true为单选。
						}
					}]
				}, {
					xtype : "hidden",
					name : "belongedId"
				}, {
					columnWidth : .1,
					labelAlign : 'right',
					xtype : 'container',
					layout : 'form',
					labelWidth : 5,
					defaults : {
						xtype : 'button'
					},
					items : [{
						text : '查询',
						fieldLabel : ' ',
						labelSeparator : "",
						scope : this,
						iconCls : 'btn-search',
						handler : this.search
					}]
				}, {
					columnWidth : .1,
					labelAlign : 'right',
					xtype : 'container',
					layout : 'form',
					labelWidth : 5,
					defaults : {
						xtype : 'button'
					},
					items : [{
						text : '重置',
						fieldLabel : '',
						labelSeparator : "",
						scope : this,
						iconCls : 'btn-reset',
						handler : this.reset
					}]
				}]
			}]
		});
		this.topbar = new Ext.Toolbar({
			items : [{
				iconCls : 'btn-detail',
				text : '客户详细',
				xtype : 'button',
				scope : this,
				 hidden : isGranted('_see_person_invest') ? false : true,
				handler : function() {
					var s = this.gridPanel.getSelectionModel().getSelections();
						if (s <= 0) {
							Ext.ux.Toast.msg('操作信息', '请选中任何一条记录');
							return false;
						} else if (s.length > 1) {
							Ext.ux.Toast.msg('操作信息', '只能选中一条记录');
							return false;
						} else {
				
						Ext.Ajax.request({
							url : __ctxPath
									+ '/creditFlow/creditAssignment/customer/seePersonCsInvestmentperson.do',
							method : 'POST',
							scope : this,
							success : function(response, request) {
								var obj = Ext.util.JSON.decode(response.responseText);
								var personData = obj.data;
								var randomId = rand(100000);
								var id = "see_person" + randomId;
								var anchor = '100%';
								var window_see = new Ext.Window({
											title : '查看个人客户详细信息',
											layout : 'fit',
											width : (screen.width - 180) * 0.7
													+ 160,
											maximizable : true,
											height : 460,
											closable : true,
											modal : true,
											plain : true,
											border : false,
											items : [new investmentObj({
														url : null,
														id : id,
														personData : personData,
														isReadOnly : true
													})],
											listeners : {
												'beforeclose' : function(panel) {
													window_see.destroy();
												}
											}
										});
								window_see.show();
							},
							failure : function(response) {
								Ext.ux.Toast.msg('状态', '操作失败，请重试');
							},
							params : {
								investId : s[0].data.investId
							}
						});
					
						}

				}
			}, new Ext.Toolbar.Separator({ hidden : isGranted('_see_person_invest') ? false : true}), {
				iconCls : 'menu-customer-manager',
				text : '授权共享人',
				xtype : 'button',
				scope : this,
				 hidden : isGranted('_person_skgxr_invest') ? false : true,
				handler : this.grant
			},'->',
			{xtype:'label',html : '【<font style="line-height:20px">注：</font>'},
			{xtype:'label',html : '<font color=red>&nbsp;&nbsp字体颜色为红色的用户为已失效用户</font>】'}
			]
		});
		
		this.store = new Ext.data.JsonStore({
				url : __ctxPath
						+ '/creditFlow/creditAssignment/customer/listCareAndGrantCsInvestmentperson.do?isAll='+rightValue+"&isShop="+isShop
						,
				totalProperty : 'totalProperty',
				root : 'topics',
				remoteSort : true,
				fields : [{
							name : 'investId'
						}, {
							name : 'investName'
						}, {
							name : 'sexvalue'
						}, {
							name : 'cardtypevalue'
						}, {
							name : 'cardnumber'
						}, {
							name : 'cellphone'
						}, {
							name : 'shopId'
						}, {
							name : 'companyId'
						}, {
							name : 'shopName'
						},  {
							name : 'orgName'
						}, {
							name : 'accountNumber'
						},  {
							name : 'contractStatus'
						}, {
							name : 'changeCardStatus'
						}, {
							name : 'birthDay'
						}, {
							name : 'belongedId'
						}, {
							name : 'belongedName'
						}, {
							name : 'createrId'
						}, {
							name : 'creator'
						}]
			});
			var person_store = this.store;
			// 加载数据
		this.store.load({
					scope : this,
					params : {
						start : 0,
						limit : this.pageSize,
						isAll : isGranted('_detail_sygrkh')
					}
				});
		this.gridPanel = new HT.GridPanel({
			region : 'center',
			tbar : this.topbar,
			// 不适用RowActions
			rowActions : false,
			loadMask : true,
			store : this.store,
			columns : [{
						header : 'investId',
						align:'center',
						dataIndex : 'investId',
						hidden : true
					},{
						header : 'investId',
						align:'center',
						dataIndex : 'investId',
						hidden : true
					}, {
						header : '姓名',
						align:'center',
						dataIndex : 'investName'
					}, {
						header : '性别',
						align:'center',
						dataIndex : 'sexvalue'
					},{
						header : '手机号码',
						align:'center',
						dataIndex : 'cellphone'
					},{
						header : '证件类型',
						align:'center',
						dataIndex : 'cardtypevalue'
					}, {
						header : '证件号码',
						align:'center',
						dataIndex : 'cardnumber'
					},{
						header : '出生日期',
						align:'center',
						dataIndex : 'birthDay',
						sortable : true
					},{
						header : '创建人',
						align:'center',
		//				width : 70,
						dataIndex : 'creator'
					}, {
						header : '共享人',
						align:'center',
		//				width : 260,
						dataIndex : 'belongedName'
					}],
			listeners : {
				'rowdblclick' : function(grid, index, e) {
					var record = grid.getSelectionModel().getSelected();
					/*seeCustomer(record.get('customerLevel'), record.get('perId'));*/
				}
			}
		});
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
	// 授权共享人
	grant : function() {
		var idCustomerTypeStr = "";
		var belongedIdStr = "";
		var belongerStr = "";
		var grid = this.gridPanel;
		var selRs = grid.getSelectionModel().getSelections();
		for (var i = 0; i < selRs.length; i++) {
			idCustomerTypeStr += selRs[i].data.investId + ";CsInvestmentperson,";
			belongedIdStr += selRs[i].data.belongedId + ",";
			belongerStr = belongerStr+selRs[i].data.belongedName + ",";
		}
		idCustomerTypeStr = idCustomerTypeStr.substring(0,idCustomerTypeStr.length - 1);
		belongedIdStr = belongedIdStr.substring(0, belongedIdStr.length - 1);
		belongerStr = belongerStr.substring(0, belongerStr.length - 1);
		new UserDialog({
			single : false,
			title : "授权共享人",
			userIds : selRs.length > 1?"":belongedIdStr,
			userName : selRs.length > 1?"":selRs[0].data.belongedName,
			callback : function(uId, uname) {
				Ext.Msg.confirm('信息确认', '确认为选中的'+selRs.length+'位客户授权共享人吗？', function(btn) {
					if (btn == 'yes') {
						Ext.Ajax.request({
							url :  __ctxPath+ "/customer/grantBelongerCustomer.do",
							method : "post",
							params : {
								idCustomerTypeStr : idCustomerTypeStr,
								userIdStr : uId,
								shopId : shopId
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

	}
});
