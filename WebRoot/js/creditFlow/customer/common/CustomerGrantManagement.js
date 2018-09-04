/**
 * @author lisl
 * @extends Ext.Panel
 * @description 客户授权管理
 * @company 智维软件
 * @createtime:
 */
CustomerGrantManagement = Ext.extend(Ext.Panel, {
	// 构造函数
	querySql:"",//查询条件sql 默认无查询sql
	queryParms:false,//查询条件中是否包含，默认是包含
	constructor : function(_cfg) {
		if (_cfg == null) {
			_cfg = {};
		}
		if (typeof(_cfg.singleType) != "undefined" && _cfg.singleType=="person") {
			this.querySql = "?Q_customerType_S_EQ=person_customer";
			this.queryParms = true;
		}else if(typeof(_cfg.singleType) != "undefined" && _cfg.singleType=="enterprise"){
			this.querySql ="?Q_customerType_S_EQ=company_customer";
			this.queryParms =true;
		}
		
		Ext.applyIf(this, _cfg);
		
		// 初始化组件
		this.initUIComponents();                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                          

		// 调用父类构造
		CustomerGrantManagement.superclass.constructor.call(this, {
			id :"CustomerGrantManagement"+this.singleType,
			title : '客户资源分配管理',
			region : 'center',
			layout : 'border',
			iconCls : 'btn-tree-team23',
			items : [this.searchPanel, this.gridPanel]
		});
	},// end of constructor
	// 初始化组件
	initUIComponents : function() {
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
				items : [{
					columnWidth : .2,
					labelAlign : 'right',
					xtype : 'container',
					layout : 'form',
					hidden:this.queryParms,
					labelWidth : 60,
					defaults : {
						anchor : anchor
					},
					items : [{
						xtype : 'combo',
						mode : 'local',
						valueField : 'value',
						editable : false,
						displayField : 'value',
						store : new Ext.data.SimpleStore({
							fields : ["value"],
							data : [["企业"], ["个人"]]
						}),
						triggerAction : "all",
						name : 'Q_customerTypeValue_S_EQ',
						fieldLabel : '客户类型'

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
						fieldLabel : '客户名称',
						name : 'Q_customerName_S_LK',
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
						name : 'Q_code_S_LK',
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
										.getCmpByName('Q_belongedId_S_LK')
										.setValue(id);
							}, true).show();// false为多选,true为单选。
						}
					}]
				}, {
					xtype : "hidden",
					name : "Q_belongedId_S_LK"
				}, {
					columnWidth : .05,
					labelAlign : 'right',
					xtype : 'container',
					layout : 'form',
					labelWidth : 5,
					defaults : {
						xtype : 'button'
					},
					// style : 'padding-left:60px;',
					items : [{
						text : '查询',
						fieldLabel : ' ',
						labelSeparator : "",
						scope : this,
						iconCls : 'btn-search',
						handler : this.search
					}]
				}, {
					columnWidth : .05,
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
				handler : function() {
					var selRs = this.gridPanel.getSelectionModel()
							.getSelections();
					if (selRs.length == 0) {
						Ext.ux.Toast.msg('操作信息', '请选择一条记录！');
						return;
					}
					if (selRs.length > 1) {
						Ext.ux.Toast.msg('操作信息', '只能选择一条记录！');
						return;
					}
					var record = this.gridPanel.getSelectionModel()
							.getSelected();
					seeCustomer(record.get('customerType'), record.get('idStr'));
				}
			}, new Ext.Toolbar.Separator({}), {
				iconCls : 'menu-customer-manager',
				text : '授权共享人',
				xtype : 'button',
				scope : this,
				handler : this.grant
			},'->',
			{xtype:'label',html : '【<font style="line-height:20px">注：</font>'},
			{xtype:'label',html : '<font color=red>&nbsp;&nbsp字体颜色为红色的用户为已失效用户</font>】'}
			]
		});
		this.gridPanel = new HT.GridPanel({
			region : 'center',
			tbar : this.topbar,
			// 不适用RowActions
			rowActions : false,
			isautoLoad:false,
			loadMask : true,
			url : __ctxPath + "/customer/customerGrantManageListCustomer.do"+this.querySql,
			fields : ['idStr','customerType', 'customerTypeValue', 'customerName', 'code',
					'creator', 'belongedId', 'belonger', 'depName','validateDetail'],
			columns : [ {
				header : 'idStr',
				dataIndex : 'idStr',
				align:'center',
				hidden : true
			},{
				header : 'belongedId',
				align:'center',
				dataIndex : 'belongedId',
				hidden : true
			}, {
				header : 'customerType',
				align:'center',
				dataIndex : 'customerType',
				hidden : true
			}, {
				header : '客户类型',
				width : 65,
				dataIndex : 'customerTypeValue'
			}, {
				header : '客户名称',
				width : 280,
				dataIndex : 'customerName'
			}, {
				header : '证件号码',
				width : 190,
				align:'center',
				dataIndex : 'code'
			}, {
				header : '创建人',
				width : 70,
				align:'center',
				dataIndex : 'creator'
			}, {
				header : '共享人',
				width : 260,
				align:'center',
				dataIndex : 'belonger'
			}/*, {
				header : '共享人所在部门',
				width : 260,
				dataIndex : 'depName'
			}*/],
			listeners : {
				'rowdblclick' : function(grid, index, e) {
					var record = grid.getSelectionModel().getSelected();
					seeCustomer(record.get('customerType'), record.get('idStr'));
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
			idCustomerTypeStr += selRs[i].data.idStr + ";" + selRs[i].data.customerType + ",";
			belongedIdStr += selRs[i].data.belongedId + ",";
			belongerStr += selRs[i].data.belonger + ",";
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

	}
});
