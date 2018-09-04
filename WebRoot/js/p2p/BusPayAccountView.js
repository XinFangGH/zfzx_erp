/**
 * @author
 * @class BusPayAccountView
 * @extends Ext.Panel
 * @description 云购支付账户管理
 * @company 智维软件
 * @createtime:
 */
BusPayAccountView = Ext.extend(Ext.Panel, {
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		// 初始化组件
		this.initUIComponents();
		// 调用父类构造
		BusPayAccountView.superclass.constructor.call(this, {
			id : 'BusPayAccountView',
			title : '云购支付账户管理',
			region : 'center',
			layout : 'border',
			iconCls:"menu-flowManager",
			items : [this.searchPanel, this.gridPanel]
		});
	},// end of constructor
	// 初始化组件
	initUIComponents : function() {
		
		// 初始化搜索条件Panel
		// 查询面板
		this.searchPanel = new Ext.form.FormPanel({
			height : 45,
			region : "north",
			bodyStyle : 'padding:7px 0px 7px 10px',
			border : false,
			width : '100%',
			monitorValid : true,
			layout : 'column',
			defaults : {
				layout : 'form',
				border : false,
				bodyStyle : 'padding:5px 0px 0px 20px'
			},
			items : [{
				columnWidth : .2,
				layout : 'form',
				labelWidth : 80,
				labelAlign : 'right',
				border : false,
				items : [{
					xtype : "dickeycombo",
					hiddenName : "Q_accountType_S_EQ",
					nodeKey : 'yun_accountType',
					fieldLabel : "支付账户类型",
					editable : false,
					anchor : "100%",
					listeners : {
						afterrender : function(combox) {
							var st = combox.getStore();
							st.on("load", function() {
								combox.setValue(combox.getValue());
								combox.clearInvalid();
							})
				       }
					}
				}]
			}, {
     			columnWidth :.2,
				layout : 'form',
				labelWidth : 80,
				labelAlign : 'right',
				border : false,
				items : [{
					fieldLabel : '账户名称',
					name : 'Q_accountName_S_LK',
					anchor : "100%",
					xtype : 'textfield'
				}]
			},{
				columnWidth : 0.07,
				items : [{
					xtype : 'button',
					text : '查询',
					tooltip : '根据查询条件过滤',
					iconCls : 'btn-search',
					width : 60,
					formBind : true,
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
		}); // 查询面板结束
		
		this.comboType = new DicKeyCombo({
			editable : false,
			lazyInit : false,
			forceSelection : false,
			nodeKey : 'yun_accountType',
			readOnly :true
		})

		this.topbar = new Ext.Toolbar({
			items : [{
				iconCls : 'btn-add',
				text : '添加',
				xtype : 'button',
				scope : this,
				handler : this.createRs
			},{
				iconCls : 'btn-edit',
				text : '编辑',
				xtype : 'button',
				scope : this,
				handler : this.editRs
			}, {
				iconCls : 'btn-del',
				text : '删除',
				xtype : 'button',
				scope : this,
				handler : this.removeRs
			}]
		});

		this.gridPanel = new HT.GridPanel({
			region : 'center',
			tbar : this.topbar,
			rowActions : false,
			isautoLoad:false,
			id : 'BusPayAccountGrid',
			url : __ctxPath + "/p2p/listBusPayAccount.do",
			fields : [{
					name : 'id',
					type : 'int'
				}, 'accountType', 'trueName', 'account','accountName'],
			columns : [{
				header : 'id',
				dataIndex : 'id',
				hidden : true
			}, {
				header : '支付账户类型',
				editor : this.comboType,
				width:20,
				dataIndex : 'accountType',
				renderer : function(value, metaData, record, rowIndex,colIndex, store) {
					var objcom = this.editor;
					var objStore = objcom.getStore();
					var idx = objStore.find("itemId",value);
					if (idx != -1) {
						return objStore.getAt(idx).data.itemName;
					}
				}
			}, {
				header : '账户名称',
				dataIndex : 'accountName'
			}]
		});
	},
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
	// 创建记录
	createRs : function() {
		new BusPayAccountForm().show();
	},
	// 按ID删除记录
	removeRs : function(id) {
		var thisPanel= this.gridPanel;
		var selected = this.gridPanel.getSelectionModel().getSelections();
		var len = selected.length;
		var list = "";
		for (var j = 0; j < len; j++) {
			if (j == (len - 1)) {
				list += selected[j].id;
			} else{
				list += selected[j].id + ",";
			}
		}
		if (0 == len) {
			Ext.MessageBox.alert('状态', '请选择一条记录!');
		} else {
			Ext.MessageBox.confirm('确认删除', '升升投一元夺宝系统提示您:',
				function(btn) {
					if (btn == 'yes') {
						Ext.Ajax.request({
							url : __ctxPath+ '/p2p/multiDelBusPayAccount.do',
							method : 'POST',
							scope : this,
							success : function(response,request) {
								var object = Ext.util.JSON.decode(response.responseText)
								Ext.ux.Toast.msg('状态','删除成功!');
								thisPanel.getStore().reload();
							},
							failure : function(result, action) {
								Ext.ux.Toast.msg('状态', '删除失败!');
							},
							params : {
								ids : list
							}
						});
					}
				});
		}
	},
	// 编辑Rs
	editRs : function() {
		var selected = this.gridPanel.getSelectionModel().getSelections();
		if (selected.length==0 || selected.length>1) {
			Ext.MessageBox.alert('状态', '请选择一条记录!');
		}else{
			new BusPayAccountForm({
				accountId : selected[0].data.id
			}).show();
		}
	}
});
