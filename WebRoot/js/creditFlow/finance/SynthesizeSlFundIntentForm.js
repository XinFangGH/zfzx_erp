/**
 * @author
 * @createtime
 * @class SynthesizeSlFundIntentForm
 * @extends Ext.Window
 * @description SlFundIntent表单
 * @company 智维软件
 */
SynthesizeSlFundIntentForm = Ext.extend(Ext.Window, {
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		// 必须先初始化组件
		this.initUIComponents();
		SynthesizeSlFundIntentForm.superclass.constructor.call(this, {
			id : 'SynthesizeSlFundIntentFormWin',
			title : '综合收款台账资金流水',
			region : 'center',
			layout : 'border',
			modal : true,
			height : 553,
			width : screen.width * 0.9,
			maximizable : true,
			items : [this.searchPanel, this.gridPanel]
		});
	},// end of the constructor
	// 初始化组件

	initUIComponents : function() {
		var isShow=false; //不显示分公司查询条件
		if(RoleType=="control"){
		    isShow=true; //显示分公司查询条件
		}

		var flag = this.flag;
		this.searchPanel = new HT.SearchPanel({
			layout : 'column',
			region : 'north',
			height : 30,
			anchor : '100%',
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
				columnWidth : 0.15,
				layout : 'form',
				border : false,
				labelWidth : 60,
				labelAlign : 'right',
				items : [{
					fieldLabel : '我方账户',
					id : 'Q_dialAccounts_S_EQintentqlide',
					flex : 1,
					xtype : 'trigger',
					triggerClass : 'x-form-search-trigger',
					onTriggerClick : function() {
						selectAccountlForm(selectAccountkLinkManintent);
					},
					anchor : '100%'
				}, {
					name : 'Q_myAccount_S_EQ',
					id : 'Q_dialAccounts_S_EQintentqlideid',
					xtype : 'hidden'
				}]
			}, {
				columnWidth : 0.14,
				layout : 'form',
				border : false,
				labelWidth : 60,
				labelAlign : 'right',
				items : [{
					name : 'Q_OopenName_D_GE',
					xtype : 'textfield',
					fieldLabel : '对方户名',
					anchor : '100%'
				}]
			}, {
				columnWidth : 0.14,
				layout : 'form',
				border : false,
				labelWidth : 60,
				labelAlign : 'right',
				items : [{
					name : 'Q_factDate_D_GE',
					xtype : 'datefield',
					format : 'Y-m-d',
					fieldLabel : '到账日期',
					anchor : '100%'
				}]
			}, {
				columnWidth : 0.10,
				layout : 'form',
				border : false,
				labelWidth : 20,
				labelAlign : 'right',
				items : [{
					name : 'Q_factDate_D_LE',
					xtype : 'datefield',
					format : 'Y-m-d',
					fieldLabel : '到',
					anchor : '100%'
				}]
			}, {
				columnWidth : 0.13,
				layout : 'form',
				border : false,
				labelWidth : 60,
				labelAlign : 'right',
				items : [{
					name : 'Q_dialMoney_BD_GE',
					xtype : 'textfield',
					width : 97,
					fieldLabel : '交易金额',
					anchor : '100%'
				}]
			}, {
				columnWidth : 0.10,
				layout : 'form',
				border : false,
				labelWidth : 20,
				labelAlign : 'right',
				items : [{
					name : 'Q_dialMoney_BD_LE',
					xtype : 'textfield',
					width : 97,
					fieldLabel : '到',
					anchor : '100%'
				}, {
					name : 'qlidetype',
					id : 'qlidetype',
					xtype : 'textfield',
					hidden : true,
					value : 'bankqlide'
				}]
			},isShow?{
			    columnWidth : 0.15,
			    layout : 'form',
			    border:false,
			   	labelWidth : 70,
				labelAlign : 'right',
			    items : [{
			      	xtype : "combo",
			      	anchor : "100%",
			      	fieldLabel : '所属分公司',
			      	hiddenName : "companyId",
			      	displayField : 'companyName',
			      	valueField : 'companyId',
			      	editable:false,
			      	triggerAction : 'all',
			      	store : new Ext.data.SimpleStore({
				       autoLoad : true,
				       url : __ctxPath+'/system/getControlNameOrganization.do',
				       fields : ['companyId', 'companyName']
			      	})
			    }]
			  }:{
			    	columnWidth:0.01,
			    	border:false
			  }, {
				columnWidth : .046,
				xtype : 'container',
				layout : 'form',
				defaults : {
					xtype : 'button'
				},
				style : 'padding-left:10px;',
				items : [{
					text : '查询',
					scope : this,
					iconCls : 'btn-search',
					handler : this.search
				}]
			},{
				columnWidth : .046,
				xtype : 'container',
				layout : 'form',
				defaults : {
					xtype : 'button'
				},
				style : 'padding-left:10px;',
				items : [{
					text : '重置',
					scope : this,
					iconCls : 'reset',
					handler : this.reset
				}]
			}]
		});// end of searchPanel
		
		this.topbar = new Ext.Toolbar({
			items : [{
				iconCls : 'btn-user-sel',
				text : '单笔对账',
				xtype : 'button',
				scope : this,
				handler : this.editqlidecheckRs
			}, '->', {
				xtype : 'radio',
				scope : this,
				boxLabel : '银行流水',
				id : "bankqlide",
				name : 'qlide',
				inputValue : true,
				checked : true,
				listeners : {
					scope : this,
					check : function() {
						var flag = Ext.getCmp("bankqlide").getValue();
						if (flag == true) {
							Ext.getCmp("qlidetype").setValue("bankqlide");
							Ext.getCmp("Q_dialAccounts_S_EQintentqlide").setReadOnly(false);
							Ext.getCmp("Q_dialAccounts_S_EQintentqlide").setValue("");
							this.search();
						}
					}
				}
			}, ' ', ' ', {
				xtype : 'radio',
				boxLabel : '现金流水',
				id : "cashqlide",
				name : 'qlide',
				inputValue : false,
				listeners : {
					scope : this,
					check : function() {
						var flag = Ext.getCmp("cashqlide").getValue();
						if (flag == true) {
							Ext.getCmp("qlidetype").setValue("cashqlide");
							Ext.getCmp("Q_dialAccounts_S_EQintentqlide").setReadOnly(true);
							Ext.getCmp("Q_dialAccounts_S_EQintentqlide").setValue("现金账户");
							Ext.getCmp("Q_dialAccounts_S_EQintentqlideid").setValue(null);
							this.search();
						}
					}
				}
			}]
		});

		this.gridPanel = new HT.GridPanel({
			rowActions : false,
			region : 'center',
			bodyStyle : "width : 100%",
			id : 'SlFundQlideGridcheck',
			tbar : this.topbar,
			url : __ctxPath+ "/creditFlow/finance/listSlFundQlide.do?fundType="+ this.flag + "&companyId=" + this.companyId,
			fields : [{
					name : 'fundQlideId',
					type : 'int'
				}, 'myAccount', 'dialMoney', 'afterMoney', 'notMoney',
				'factDate', 'opAccount', 'fundType', 'currency',
				'transactionType', 'bankName', 'openName', 'incomeMoney',
				'payMoney', 'opOpenName', 'opBankName', 'isProject'],
			columns : [{
					header : 'fundQlideId',
					dataIndex : 'fundQlideId',
					hidden : true
				}, {
					header : '我方账户',
					dataIndex : 'bankName',
					width : 300
				}, {
					header : '币种',
					dataIndex : 'currency',
					width : 60
				}, {
					header : '到账时间',
					dataIndex : 'factDate',
					sortable : true,
					width : 90
				}, {
					header : '收入金额',
					dataIndex : 'incomeMoney',
					align : 'right',
					renderer : function(v) {
						if (v != null) {
							return Ext.util.Format.number(v,',000,000,000.00')+ "元"
						} else {
							return v
						}
					}
				}, {
					header : '支出金额',
					dataIndex : 'payMoney',
					align : 'right',
					renderer : function(v) {
						if (v != null) {
							return Ext.util.Format.number(v,',000,000,000.00')+ "元"
							return v + "元"
						} else {
							return v
						}
					}
				}, {
					header : '已对账金额',
					align : 'right',
					dataIndex : 'afterMoney',
					renderer : function(v) {
						if (v != null) {
							return Ext.util.Format.number(v,',000,000,000.00')+ "元"
						} else {
							return v
						}
					}
				}, {
					header : '未对账金额',
					align : 'right',
					dataIndex : 'notMoney',
					renderer : function(v) {
						if (v != null) {
							return Ext.util.Format.number(v,',000,000,000.00')+ "元"
						} else {
							return v
						}
					}
				}, {
					header : "对方账号",
					dataIndex : "opAccount"
				}, {
					header : "对方开户账名称",
					dataIndex : "opOpenName"
				}, {
					header : '交易摘要',
					dataIndex : 'transactionType'
				}]
			});
		},
		editqlidecheckRs : function() {
			var otherPanel = this.otherPanel;
			var s = this.gridPanel.getSelectionModel().getSelections();
			var tabflag = this.tabflag;
			if (s <= 0) {
				Ext.ux.Toast.msg('操作信息', '请选中一条记录');
				return false;
			} else if (s.length > 1) {
				Ext.ux.Toast.msg('操作信息', '只能选中一条记录');
				return false;
			} else {
				var record = s[0];
				var projectId = this.projectId;
				var payintentPeriod = this.payintentPeriod;
				
				var notMoney = record.data.notMoney;//流水的未对账金额
				var notSynthesizeMoney = this.data.notSynthesizeMoney;//未对账金额
				var punishMoney = this.data.punishMoney;//原有罚息金额
				//修改款项未对账金额
				notSynthesizeMoney=notSynthesizeMoney-punishMoney;
				
				Ext.Ajax.request({
					url :  __ctxPath + '/creditFlow/finance/reSignOverMoneyToSlFundIntent.do',
					method : 'POST',
					scope : this,
					params : {
						projectId : projectId,
						payintentPeriod : payintentPeriod,
						fundQlideId : record.data.fundQlideId
					},
					success : function(response, request) {
						var respText = response.responseText;
						var alarm = Ext.util.JSON.decode(respText);
						
						var bjMoney=alarm.data.bjMoney;//本金逾期金额
						var lxMoney=alarm.data.lxMoney;//利息逾期金额
						var glMoney=alarm.data.glMoney;//管理咨询费逾期金额
						var cwMoney=alarm.data.cwMoney;//财务服务费逾期金额
						notSynthesizeMoney+=(bjMoney+lxMoney+glMoney+cwMoney);
						
						var overDay=alarm.data.overDay;//逾期天数
						
						if(notMoney<notSynthesizeMoney){
							Ext.ux.Toast.msg('操作信息', '银行流水资金不足不能完成对账，请核实银行流水！');
							return false;
						}else{
							Ext.MessageBox.confirm('确认', '确定对账吗', function(btn) {
								if (btn == 'yes') {
									Ext.MessageBox.wait('正在进行对账, 请稍侯 ...', '提示');
									Ext.Ajax.request({
										url :  __ctxPath + '/creditFlow/finance/complexCheckSlFundIntent.do',
										method : 'POST',
										scope : this,
										params : {
											projectId : projectId,
											payintentPeriod : payintentPeriod,
											fundQlideId : record.data.fundQlideId,
											bjMoney:bjMoney,
											lxMoney:lxMoney,
											glMoney:glMoney,
											cwMoney:cwMoney,
											overDay:overDay
										},
										success : function(response, request) {
											Ext.MessageBox.hide();
											Ext.ux.Toast.msg('操作信息', '对账成功！');
											Ext.getCmp('SlFundQlideGridcheck').getStore().reload();
											var gridPanel1=Ext.getCmp('synthesizeSlFundIntent1');
											if(null!=gridPanel1){
												gridPanel1.getStore().reload();
											}
										},
										checkfail : function(response, request) {
											Ext.Msg.alert('状态', "对账失败");
										}
									});
								}
							})
						}
					}
				});
			}
		},
		reset : function() {
			var flag = Ext.getCmp("qlidetype").getValue();
			this.searchPanel.getForm().reset();
			Ext.getCmp("qlidetype").setValue(flag);
			if (flag == "cashqlide") {
				Ext.getCmp("Q_dialAccounts_S_EQintentqlide").setValue("现金账户");
			}
		},
		// 按条件搜索
		search : function() {
			$search({
				searchPanel : this.searchPanel,
				gridPanel : this.gridPanel
			});
		}
});
var selectAccountkLinkManintent = function(a, b, c, d, e, f) {
	Ext.getCmp('Q_dialAccounts_S_EQintentqlide').setValue(b + "-" + c + "-" + a);
	Ext.getCmp('Q_dialAccounts_S_EQintentqlideid').setValue(a);
}