/**
 * @author
 * @createtime
 * @class SlFundIntentForm
 * @extends Ext.Window
 * @description SlFundIntent表单
 * @company 智维软件
 */
selectAccountBankCautionlForm = function(funname) {
	var searchPanel = new HT.SearchPanel({
		border : false,
		layout : 'column',
		region : 'north',
		height : 40,
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
		layoutConfig : {
			align : 'middle'
		},
		items : [{
			columnWidth : 0.45,
			layout : 'form',
			border : false,
			labelWidth : 60,
			labelAlign : 'right',

			items : [{
				fieldLabel : '银行名称',
				name : 'Q_bankBranchName_S_LK',
				xtype : 'textfield',
				anchor : '98%'
			}, {
				xtype : 'hidden',
				id : 'bankId'
					//	name :'bankId'
					}, {
						xtype : 'hidden',
						id : 'bankLeaf'
					//	name :'bankLeaf'
					}]

		}, {
			columnWidth : 0.32,
			layout : 'form',
			border : false,
			labelWidth : 80,
			labelAlign : 'right',
			items : [{
				name : 'Q_accountname_S_LK',
				xtype : 'textfield',
				fieldLabel : '账户名称',
				anchor : '99%'
			}]

		}

		, {
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
			columnWidth : .11,
			xtype : 'container',
			layout : 'form',
			defaults : {
				xtype : 'button'
			},
			style : 'padding-left:10px;',
			items : [{
				style : 'padding-top:3px;',
				text : '重置',
				iconCls : 'reset',
				handler : function() {
					searchPanel.getForm().reset();
				}
			}]
		}

		]
	});// end of searchPanel
	var gridPanel = new HT.GridPanel({
		isShowTbar : false,
		region : 'center',
		// 使用RowActions
		rowActions : false,
		bodyStyle : "width : 100%",
		width : (screen.width - 180) * 0.7,
		id : 'selectAccountBankCautionlGrid',
		url : __ctxPath
				+ '/creditFlow/guarantee/guaranteefinance/listGlAccountBankCautionmoney.do',
		fields : [{
			name : 'id',
			type : 'Long'
		}, 'accountname', 'bankName', 'bankAddress', 'surplusMoney',
				'accountFrozenMoney', 'remark', 'createDate'],
		columns : [{
			header : 'id',
			dataIndex : 'id',
			hidden : true
		}, {
			header : '银行名称',
			dataIndex : 'bankName'

		}, {
			header : '账户名称',
			dataIndex : 'accountname'

		}, {
			header : '银行地址',
			dataIndex : 'bankAddress'

		}, {
			header : '可用金额(万元)',
			dataIndex : 'surplusMoney'
		}, {
			header : '冻结金额(万元)',
			dataIndex : 'accountFrozenMoney'
		}, {
			header : '备注',
			dataIndex : 'remark'
		}

		],
		listeners : {
			'rowdblclick' : function(grid, rowIndex, e) {

				grid.getSelectionModel().each(function(rec) {
					funname(rec.data.id, rec.data.accountname,
							rec.data.bankName, rec.data.surplusMoney);
				});
				selectAccountlWindow.destroy();
			}
		}
			// end of columns
	});
	var selectAccountlWindow = new Ext.Window({
		width : (screen.width - 180) * 0.7,
		title : '保证金账户列表',
		height : 350,//高度自by chencc
		collapsible : true,
		region : 'center',
		layout : 'border',
		modal : true,
		resizable : false,
		items : [searchPanel, gridPanel]
	});
	selectAccountlWindow.show();

	var selectBankLinkMan = function(array) {
		Ext.getCmp('bankId').setValue(array[0].id);
		Ext.getCmp('bankName').setValue(array[0].attributes.remarks);
		Ext.getCmp('bankLeaf').setValue(array[0].leaf);

	};
}
