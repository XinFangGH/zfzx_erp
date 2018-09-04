/**
 * @author
 * @createtime
 * @class InvestBankNoForm
 * @extends Ext.Window
 * @description InvestBankNo表单
 * @company 智维软件
 */
InvestBankNoForm = Ext.extend(Ext.Window, {
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		// 必须先初始化组件
		this.initUIComponents();
		InvestBankNoForm.superclass.constructor.call(this, {
					id : 'InvestBankNoFormWin',
					layout : 'fit',
					items : this.formPanel,
					modal : true,
					height : 400,
					width : 500,
					maximizable : true,
					title : '银行开户详细信息',
					buttonAlign : 'center',
					buttons : [{
								text : '保存',
								iconCls : 'btn-save',
								scope : this,
								handler : this.save
							}, {
								text : '重置',
								iconCls : 'btn-reset',
								scope : this,
								handler : this.reset
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
		this.formPanel = new Ext.FormPanel({
			layout : 'form',
			bodyStyle : 'padding:10px',
			border : false,
			autoScroll : true,
			// id : 'InvestBankNoForm',
			defaults : {
				anchor : '96%,96%'
			},
			defaultType : 'textfield',
			items : [{
						name : 'investBankNo.bankId',
						xtype : 'hidden',
						value : this.bankId == null ? '' : this.bankId
					}, {
						fieldLabel : '开户类型',
						name : 'investBankNo.accountType',
						xtype : 'combo',
						mode : 'local',
						displayField : 'name',
						valueField : 'name',
						editable : false,
						width : 70,
						store : new Ext.data.SimpleStore({
									fields : ["name", "id"],
									data : [["个人", "0"], ["公司", "1"]]
								}),
						triggerAction : "all",
						anchor : '96%',
						allowBlank : false,
						listeners : {
							scope : this,
							select : function(combox, record, index) {
								var v = record.data.id;
								var obj = Ext.getCmp('bankType');
								obj.enable();
								var arrStore = null;
								if (v == 0) {
									arrStore = new Ext.data.SimpleStore({
												fields : ["name", "id"],
												data : [["个人储蓄户", "0"]]
											});
								} else {
									arrStore = new Ext.data.SimpleStore({
												fields : ["name", "id"],
												data : [["基本户", "1"],
														["一般户", "2"]]
											});
								}
								obj.clearValue();
								obj.store = arrStore;
								obj.view.setStore(arrStore);
								// arrStore.load();
							}

						}
					}, {
						id : 'bankType',
						fieldLabel : '账户类别',
						hiddenName : 'investBankNo.bankType',
						xtype : 'combo',
						mode : 'local',
						displayField : 'name',
						valueField : 'id',
						editable : false,
						// value : '个人储蓄客户',
						width : 70,
						triggerAction : "all",
						anchor : '96%',
						store : new Ext.data.SimpleStore({
							fields : ["name", "id"],
							data : [["基本户", "1"], ["一般户", "2"], ["个人储蓄账户", "3"]]
						}),
						listeners : {
							afterrender : function(combo) {
								combo.setValue(3);
							}
						},
						allowBlank : false
					}, {
						id : 'bankName',
						fieldLabel : '银行名称',
						name : 'investBankNo.bankName',
						xtype : 'trigger',
						triggerClass : 'x-form-search-trigger',
						value : '中国银行',
						editable : false,
						allowBlank : false,
						onTriggerClick : function() {
							var selectBankLinkMan = function(array) {
								
								Ext.getCmp('bankName')
										.setValue(array[0].attributes.remarks);
							};
							selectDictionary('bank', selectBankLinkMan);
						},
						anchor : '98%'
					}, {
						fieldLabel : '网点名称',
						name : 'investBankNo.webType'
					}, {
						fieldLabel : '开户地区',
						name : 'investBankNo.bankAddress',
						maxLength : 255
					}, {
						id : 'bankAccountType',
						fieldLabel : '银行开户类型',
						xtype : 'radiogroup',
						name : 'investBankNo.bankAccountType',
						items : [{
							boxLabel : '本币开户',
							name : 'investBankNo.bankAccountType',
							inputValue : "521"
								// checked :
								// Ext.getCmp('bankAccountType').getValue()=='本币开户'?true:false
							}, {
							boxLabel : '外币开户',
							name : 'investBankNo.bankAccountType',
							inputValue : "522"
								// checked :
								// Ext.getCmp('bankAccountType').getValue()=='外币开户'?true:false
							}],
						maxLength : 255
					}, {
						
						id : 'isIncome',
						fieldLabel : '是否收息账户',
						xtype : 'radiogroup',
						//name : 'investBankNo.isIncome',
						items : [{
							boxLabel : '是',
							name : 'investBankNo.isIncome',
							inputValue : "1"
								// checked :
								// Ext.getCmp('bankAccountType').getValue()=='本币开户'?true:false
							}, {
							boxLabel : '否',
							name : 'investBankNo.isIncome',
							inputValue : "0"
								// checked :
								// Ext.getCmp('bankAccountType').getValue()=='外币开户'?true:false
							}],
						maxLength : 255
					
					}, {
						fieldLabel : '开户名称',
						name : 'investBankNo.accountName',
						allowBlank : false,
						maxLength : 255
					}, {
						fieldLabel : '账号',
						name : 'investBankNo.cardNomber',
						maxLength : 100,
						anchor : '96%',
						allowBlank : false
					}, {
						fieldLabel : '',
						name : 'investBankNo.perId',
						xtype : 'hidden',
						value : this.perId
					}]
		});
		// 加载表单对应的数据
		if (this.bankId != null && this.bankId != 'undefined') {
			this.formPanel.loadData({
						url : __ctxPath
								+ '/customer/getInvestBankNo.do?bankId='
								+ this.bankId,
						root : 'data',
						preName : 'investBankNo'
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
					url : __ctxPath + '/customer/saveInvestBankNo.do',
					callback : function(fp, action) {
						var gridPanel = Ext.getCmp('InvestBankNoGrid');
						if (gridPanel != null) {
							gridPanel.getStore().reload();
						}
						this.close();
					}
				});
	}// end of save

});