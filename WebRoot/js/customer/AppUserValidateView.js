/**
 * @author lisl
 * @extends Ext.Panel
 * @description 客户有效性验证
 * @company 智维软件
 * @createtime:
 */
AppUserValidateView = Ext.extend(Ext.Panel, {
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		// 初始化组件
		this.initUIComponents();

		// 调用父类构造
		AppUserValidateView.superclass.constructor.call(this, {
			id : 'AppUserValidateView',
			title : "客户共享人字段验证详情",
			region : 'center',
			layout : 'border',
			iconCls : "",
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
					name : 'isAppUserValidate',
					xtype : "hidden",
					value : true
				},{
					columnWidth : .2,
					labelAlign : 'right',
					xtype : 'container',
					layout : 'form',
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
		});
		this.gridPanel = new HT.GridPanel({
			region : 'center',
			isShowTbar : false,
			// 不适用RowActions
			rowActions : false,
			loadMask : true,
			hiddenCm : true,
			url : __ctxPath + "/customer/customerGrantManageListCustomer.do",
			baseParams : {
				isAppUserValidate : true
			},
			fields : [{
				name : 'id',
				type : 'int'
			}, 'customerType', 'customerTypeValue', 'customerName', 'code',
					'creator', 'belongedId', 'belonger', 'depName','validateDetail'],
			columns : [{
				header : 'id',
				dataIndex : 'id',
				hidden : true
			}, {
				header : 'belongedId',
				dataIndex : 'belongedId',
				hidden : true
			}, {
				header : 'customerType',
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
				dataIndex : 'code'
			}, {
				header : '创建人',
				width : 50,
				dataIndex : 'creator'
			}, {
				header : '共享人',
				width : 330,
				dataIndex : 'belonger'
			}, {
				header : '共享人验证详情',
				width : 190,
				dataIndex : 'validateDetail'
			}],
			listeners : {
				'rowdblclick' : function(grid, index, e) {
					var record = grid.getSelectionModel().getSelected();
					seeCustomer(record.get('customerType'), record.get('id'));
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
			idCustomerTypeStr += selRs[i].data.id + ";" + selRs[i].data.customerType + ",";
			belongedIdStr += selRs[i].data.belongedId + ",";
			belongerStr += selRs[i].data.belonger + ",";
		}
		idCustomerTypeStr = idCustomerTypeStr.substring(0,idCustomerTypeStr.length - 1);
		belongedIdStr = belongedIdStr.substring(0, belongedIdStr.length - 1);
		belongerStr = belongerStr.substring(0, belongerStr.length - 1);
		new UserDialog({
			single : false,
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
