/**
 * @author
 * @createtime
 * @class SlFundDetailForm
 * @extends Ext.Window
 * @description SlFundDetail表单
 * @company 智维软件
 */
editAfterMoneyForm = Ext.extend(Ext.Window, {
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		// 必须先初始化组件
		this.initUIComponents();
		editAfterMoneyForm.superclass.constructor.call(this, {
					id : 'SlFundDetailFormWin',
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
			id : 'editAfterMoneyGrid',
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
						name : 'slFundIntent.fundIntentId',
						xtype : 'hidden',
						value : this.fundIntentId == null
								? ''
								: this.fundIntentId
					}, {

						fieldLabel : '未对账金额(元)',
						name : 'slFundIntent.notMoney',
						id : 'notMoney',
						readOnly : true

					}

					, {
						fieldLabel : '核销金额(元)',
						name : 'slFundIntent.flatMoney',
						id : 'flatMoney',
						// xtype : 'hidden',
						listeners : {
							// blur: function(p){
							// if(p.getValue()<0){
							// Ext.Msg.alert('状态', "核销金额不能为负数");
							// var notMoneyField = Ext.getCmp('flatMoney');
							// flatMoneyField.setValue(flatMoney);
							// var notMoneyField = Ext.getCmp('notMoney');
							// notMoneyField.setValue(notMoney);
							//																						
							// }else if(p.getValue()> notMoney){
							// Ext.Msg.alert('状态', "核销金额不能大于未对账金额");
							// var notMoneyField = Ext.getCmp('flatMoney');
							// flatMoneyField.setValue(flatMoney);
							// var notMoneyField = Ext.getCmp('notMoney');
							// notMoneyField.setValue(notMoney);
							// }
							// else{
							//																				   
							// var notMoneyField = Ext.getCmp('notMoney');
							// notMoneyField.setValue(notMoney-(p.getValue()-flatMoney));
							//																					
							//																					
							// }
							//																					
							// }
							blur : function(p) {
								if (p.getValue() < 0) {
									Ext.Msg.alert('状态', "核销金额不能为负数");
									var notMoneyField = Ext.getCmp('flatMoney');
									flatMoneyField.setValue(0);
									var notMoneyField = Ext.getCmp('notMoney');
									notMoneyField.setValue(notMoney);

								} else if (p.getValue() > notMoney) {
									Ext.Msg.alert('状态', "核销金额不能大于未对账金额");
									var notMoneyField = Ext.getCmp('flatMoney');
									flatMoneyField.setValue(0);
									var notMoneyField = Ext.getCmp('notMoney');
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
						name : 'premark',
						id:'premark',
						readOnly : false

					}

			]
		});
		// 加载表单对应的数据
		if (this.fundIntentId != null && this.fundIntentId != 'undefined') {
			this.formPanel.loadData({
				url : __ctxPath
						+ '/creditFlow/finance/getSlFundIntent.do?fundIntentId='
						+ this.fundIntentId,
				root : 'data',
				preName : 'slFundIntent',
				success : function(resp, options) {
					Ext.getCmp('flatMoney').setValue(0);

				}
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
							+ '/creditFlow/finance/editAfterMoneySlFundIntent.do',
					params:{premark:Ext.getCmp('premark').getValue()},		
					callback : function(fp, action) {
						var gridPanel = Ext.getCmp('SlFundIntentGrid');
						if (gridPanel != null) {
							gridPanel.getStore().reload();
						}
						this.close();
					}
				});
	}// end of save

});