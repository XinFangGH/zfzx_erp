//ThirdPayAccountInfoQuery.js
ThirdPayAccountInfoQuery = Ext.extend(Ext.Window, {
	isLook : false,
	isRead : false,
	isflag : false,
	constructor : function(_cfg) {
		if (_cfg == null) {
			_cfg = {};
		};
		
		Ext.applyIf(this, _cfg);
		this.initUIComponents();
		ThirdPayAccountInfoQuery.superclass.constructor.call(this, {
					id : 'ThirdPayAccountInfoQuery',
					layout : 'form',
					items : [this.formPanel],
					modal : true,
					autoHeight : true,
					width : 1000,
					maximizable : true,
					title : '第三方支付账户信息',
					buttonAlign : 'center'
				});
	},// end of the constructor
	// 初始化组件
	initUIComponents : function() {
		this.formPanel = new Ext.FormPanel({
							layout:'column',
							bodyStyle : 'padding:10px',
							autoScroll : true,
							monitorValid : true,
							frame : true,
						    plain : true,
						    labelAlign : "right",
							defaults : {
								anchor : '96%',
								labelWidth : 110,
								columnWidth : 1,
							    layout : 'column'
							},
							items : [/*{  
									columnWidth :0.5,
								     layout : 'form',
									 items :[{
											xtype : 'textfield',
											fieldLabel : '第三方支付名称',
											name : 'thirdPayConfigName',
											anchor : '98%',
											readOnly:true
										}]
								},{  
									 columnWidth :0.5,
								     layout : 'form',
									 items :[{
											xtype : 'textfield',
											fieldLabel : '第三方支付类型',
											name : 'thirdPayMentType',
											anchor : '98%',
											readOnly:true
										  
									}]
								},*/{  
									 columnWidth : 0.5,
								     layout : 'form',
									 items :[{
											xtype : 'textfield',
											fieldLabel : '支付会员账号',
											name : 'plateFormUserNo',
											anchor : '98%',
											readOnly:true
										  
									}]
								},{  
									 columnWidth : 0.5,
								     layout : 'form',
									 items :[{
											xtype : 'textfield',
											fieldLabel : '支付会员类型',
											name : 'plateFormUserType',
											anchor : '98%',
											readOnly:true
										  
									}]
								},{  
									 columnWidth :0.5,
								     layout : 'form',
									 items :[{
											xtype : 'textfield',
											fieldLabel : '支付账号状态',
											name : 'thirdAccountStatus',
											anchor : '98%',
											readOnly:true
										  
									}]
								},{  
									 columnWidth : 0.47,
								     layout : 'form',
									 items :[{
											xtype : 'textfield',
											fieldLabel : '支付账号可用余额',
											name : 'accountMoney',
											anchor : '98%',
											readOnly:true
										  
									}]
								},{
										columnWidth : .03, // 该列在整行中所占的百分比
										layout : "form", // 从上往下的布局
										labelWidth : 20,
										items : [{
													fieldLabel : "元 ",
													labelSeparator : '',
													anchor : "98%"
												}]
								},{  
									 columnWidth : 0.47,
								     layout : 'form',
									 items :[{
											xtype : 'textfield',
											fieldLabel : '支付账号可提现余额',
											name : 'accountAvailableMoney',
											anchor : '98%',
											readOnly:true
										  
									}]
								},{
										columnWidth : .03, // 该列在整行中所占的百分比
										layout : "form", // 从上往下的布局
										labelWidth : 20,
										items : [{
													fieldLabel : "元 ",
													labelSeparator : '',
													anchor : "98%"
												}]
								},{  
									 columnWidth : 0.47,
								     layout : 'form',
									 items :[{
											xtype : 'textfield',
											fieldLabel : '支付账号冻结金额',
											name : 'accountFreezeMoney',
											anchor : '98%',
											readOnly:true
										  
									}]
								},{
										columnWidth : .03, // 该列在整行中所占的百分比
										layout : "form", // 从上往下的布局
										labelWidth : 20,
										items : [{
													fieldLabel : "元 ",
													labelSeparator : '',
													anchor : "90%"
												}]
								}/*,{  
									 columnWidth : 0.5,
								     layout : 'form',
									 items :[{
											xtype : 'textfield',
											fieldLabel : '取现银行卡状态',
											name : 'withdrawBankStatus',
											anchor : '98%',
											readOnly:true
										  
									}]
								},{  
									 columnWidth : 0.5,
								     layout : 'form',
									 items :[{
											xtype : 'textfield',
											fieldLabel : '取现银行',
											name : 'withdrawBankName',
											anchor : '98%',
											readOnly:true
										  
									}]
								},{  
									 columnWidth : 0.5,
								     layout : 'form',
									 items :[{
											xtype : 'textfield',
											fieldLabel : '取现银行卡号',
											name : 'withdrawBankNumber',
											anchor : '98%',
											readOnly:true
										  
									}]
								}*/]
						});
		// this.gridPanel.addListener('rowdblclick', this.rowClick);
		// 加载表单对应的数据
		if (this.accountId != null && this.accountId != 'undefined') {
			var   panel =this;
			this.formPanel.loadData({
						url : __ctxPath + "/creditFlow/creditAssignment/bank/getThirdPayAccountDealInfoObSystemAccount.do?accountId="+ this.accountId ,
						root : 'data',
						success : function(response, options) {
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
	save : function() {}// end of save
});