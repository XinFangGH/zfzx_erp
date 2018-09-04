/**
 * @author
 * @createtime
 * @class SlFundDetailForm
 * @extends Ext.Window
 * @description SlFundDetail表单
 * @company 智维软件
 */
chargeeditAfterMoneyForm = Ext.extend(Ext.Window, {
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		// 必须先初始化组件
		this.initUIComponents();
		chargeeditAfterMoneyForm.superclass.constructor.call(this, {
					id : 'chargeeditAfterMoneyForm',
					layout : 'fit',
					items : this.formPanel,
					modal : true,
					height : 250,
					width : 400,
					title : '核销',
					buttonAlign : 'center',
					buttons : [{
								text : '保存',
								iconCls : 'btn-save',
								scope : this,
								handler : this.save
							}, {
								text : '取消',
								iconCls : 'btn-cancel',
								scope : this,
								handler : this.cancel
							}]
				});
	},// end of the constructor
	// 初始化组件
	initUIComponents : function() {
		var afterMoney = this.afterMoney;
		var notMoney = this.notMoney;
		var flatMoney = this.flatMoney;
		this.formPanel = new Ext.FormPanel({
					id : 'chargeeditAfterMoneyGrid',
					layout : 'form',
					bodyStyle : 'padding:10px',
					border : false,
					autoScroll : true,
					// id : 'SlFundDetailForm',
					defaults : {
						anchor : '96%,96%'
					},
					defaultType : 'textfield',
					items : [{
						name : 'slActualToCharge.actualChargeId',
						xtype : 'hidden',
						value : this.actualChargeId == null
								? ''
								: this.actualChargeId
					}, {

						fieldLabel : '未对账金额(元)',
						name : 'slActualToCharge.notMoney',
						id : 'chargenotMoney',
						readOnly : true

					}

					, {
						fieldLabel : '核销金额(元)',
						name : 'slActualToCharge.flatMoney',
						id : 'chargeflatMoney',
						// xtype : 'hidden',
						listeners : {
							// blur: function(p){
							// if(p.getValue()<0){
							// Ext.Msg.alert('状态', "核销金额不能为负数");
							// var flatMoneyField =
							// Ext.getCmp('chargeflatMoney');
							// flatMoneyField.setValue(flatMoney);
							// var notMoneyField = Ext.getCmp('chargenotMoney');
							// notMoneyField.setValue(notMoney);
							//																						
							// }else if(p.getValue()> notMoney){
							// Ext.Msg.alert('状态', "核销金额不能大于未对账金额");
							// var flatMoneyField =
							// Ext.getCmp('chargeflatMoney');
							// flatMoneyField.setValue(flatMoney);
							// var notMoneyField = Ext.getCmp('chargenotMoney');
							// notMoneyField.setValue(notMoney);
							// }else{
							//																				   
							// var notMoneyField = Ext.getCmp('chargenotMoney');
							// notMoneyField.setValue(notMoney-(p.getValue()-flatMoney));
							//																					
							//																					
							// }
							//																					
							// }
							blur : function(p) {
								if (p.getValue() < 0) {
									Ext.Msg.alert('状态', "核销金额不能为负数");
									var notMoneyField = Ext
											.getCmp('chargeflatMoney');
									flatMoneyField.setValue(0);
									var notMoneyField = Ext
											.getCmp('chargenotMoney');
									notMoneyField.setValue(notMoney);

								} else if (p.getValue() > notMoney) {
									Ext.Msg.alert('状态', "核销金额不能大于未对账金额");
									var notMoneyField = Ext
											.getCmp('chargeflatMoney');
									flatMoneyField.setValue(0);
									var notMoneyField = Ext
											.getCmp('chargenotMoney');
									notMoneyField.setValue(notMoney);
								} else {

									var notMoneyField = Ext.getCmp('notMoney');
									notMoneyField.setValue(notMoney
											- p.getValue());

								}

							}
						}

					}, {
                        xtype : 'textarea',
						fieldLabel : '核销备注',
						name : 'slActualToCharge.premark',
						readOnly : false

					}

					]
				});
		// 加载表单对应的数据
		if (this.actualChargeId != null && this.actualChargeId != 'undefined') {
			this.formPanel.loadData({
				url : __ctxPath
						+ '/creditFlow/finance/getSlActualToCharge.do?actualChargeId='
						+ this.actualChargeId,
				root : 'data',
				preName : 'slActualToCharge'
			});
		}

	},// end of the initcomponents

	/**
	 * 重置
	 * 
	 * @param {}
	 *            formPanel
	 */
	reset : function() {
		this.formPanel.getForm().reset();
	},
	/**
	 * 取消
	 * 
	 * @param {}
	 *            window
	 */
	cancel : function() {
		this.close();
	},
	/**
	 * 保存记录
	 */
	save : function() {
		$postForm({
					formPanel : this.formPanel,
					scope : this,
					url : __ctxPath
							+ '/creditFlow/finance/editAfterMoneySlActualToCharge.do',
					callback : function(fp, action) {
						var gridPanel = Ext.getCmp('SlActualToChargeGrid');
						if (gridPanel != null) {
							gridPanel.getStore().reload();
						}
						this.close();
					}
				});
	}// end of save

});