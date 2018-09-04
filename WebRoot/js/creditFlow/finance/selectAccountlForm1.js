/**
 * @author
 * @createtime
 * @class SlFundIntentForm
 * @extends Ext.Window
 * @description SlFundIntent表单
 * @company 智维软件
 */
selectAccountlForm1 = function(funname, parent) {
	var isShow = false;
	if (RoleType == "control") {
		isShow = true;
	}
	var searchPanel = new HT.SearchPanel({
		layout : 'column',
		region : 'north',
		height : 40,
		anchor : '98%',
		keys : [{
			key : Ext.EventObject.ENTER,
			fn : this.search,
			scope : this
		}, {
			key : Ext.EventObject.ESC,
			fn : this.reset,
			scope : this
		}],
		layoutConfig : {
			align : 'middle'
		},
		items : [isShow ? {
			columnWidth : 0.3,
			labelWidth : 70,
			layout : 'form',
			border : false,
			labelAlign : 'right',
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
			columnWidth : 0.3,
			layout : 'form',
			border : false,
			labelWidth : 60,
			labelAlign : 'right',
			items : [{
				fieldLabel : '银行名称',
				anchor : '100%',
				name : 'Q_bankBranchName_S_LK',
				xtype : 'textfield',
				anchor : '98%'
			}]
		}, {
			columnWidth : 0.3,
			layout : 'form',
			border : false,
			labelWidth : 60,
			labelAlign : 'right',
			items : [{
				anchor : '100%',
				fieldLabel : '账号',
				name : 'Q_account_S_LK',
				xtype : 'textfield'
			}]
		}, {
			columnWidth : 0.3,
			layout : 'form',
			border : false,
			labelWidth : 70,
			labelAlign : 'right',
			items : [{
				anchor : '100%',
				xtype : 'combo',
				mode : 'local',
				displayField : 'name',
				valueField : 'id',
				editable : false,
				store : new Ext.data.SimpleStore({
					fields : ["name", "id"],
					data : [["个人储蓄户", "0"], ["基本户", "1"],["一般户", "2"]]
				}),
				triggerAction : "all",
				hiddenName : "Q_accountType_SN_EQ",
				fieldLabel : '账户类型',
				name : 'Q_accountType_SN_EQ'
			}]
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
				iconCls : 'btn-search',
				handler : function() {
					$search({
						searchPanel : searchPanel,
						gridPanel : gridPanel
					});
				}
			}]
		}, {
			columnWidth : .08,
			xtype : 'container',
			layout : 'form',
			defaults : {
				xtype : 'button'
			},
			style : 'padding-left:10px;',
			items : [{
				text : '重置',
				iconCls : 'reset',
				handler : function() {
					searchPanel.getForm().reset();
				}
			}]
		}]
	});// end of searchPanel
	
	var gridPanel = new HT.GridPanel({
		isShowTbar : false,
		region : 'center',
		// 使用RowActions
		rowActions : false,
		id : 'selectAccountlGrid1',
		url : __ctxPath + "/creditFlow/finance/selectlist1SlBankAccount.do",
		baseParams : {
			start : 0,
			limit : 100
		},
		fields : [{
			name : 'bankAccountId',
			type : 'int'
		}, 'openType', 'accountType', 'bankName', 'name', 'account',
		'rawMoney', 'finalMoney', 'recordTimeString', 'sumbankname',
		'orgName'],
		columns : [{
			header : 'bankAccountId',
			dataIndex : 'bankAccountId',
			hidden : true
		}, {
			header : "所属分公司",
			sortable : true,
			hidden : RoleType == "control" ? false : true,
			dataIndex : 'orgName'
		}, {
			header : '开户银行',
			dataIndex : 'bankName',
			sortable : true
		}, {
			header : '账号',
			dataIndex : 'account',
			width : 50,
			sortable : true
		}, {
			header : '开户名称',
			dataIndex : 'name',
			width : 50,
			sortable : true
		}, {
			header : '账户类型',
			dataIndex : 'accountType',
			width : 40,
			sortable : true,
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
			dataIndex : 'openType',
			width : 40,
			sortable : true,
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
		}],
		listeners : {
			'rowdblclick' : function(grid, rowIndex, e) {
				grid.getSelectionModel().each(function(rec) {
					funname(rec.data.account, rec.data.bankName, rec.data.name,rec.data.bankAccountId, rec.data.finalMoney,rec.data.sumbankname,parent);
				});
				selectAccountlWindow.destroy();
			}
		}
	});
	var selectAccountlWindow = new Ext.Window({
		width : (screen.width - 180) * 0.7,
		title : '我方账户列表',
		height : 600,// 高度自by chencc
		collapsible : true,
		region : 'center',
		layout : 'border',
		modal : true,
		resizable : false,
		items : [searchPanel, gridPanel]
	});
	selectAccountlWindow.show();
}