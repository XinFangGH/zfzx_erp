//PlateFormAccountDetailFrom
PlateFormAccountDetailFrom = Ext.extend(Ext.Panel, {
	layout : "form",
	autoHeight : true,
	labelAlign : 'right',
	isAllReadOnly : true,
	isWAD:true,
	constructor : function(_cfg) {
		if (_cfg == null) {
			_cfg = {};
		}
		if (typeof(_cfg.isAllReadOnly) != "undefined") {
			this.isAllReadOnly = _cfg.isAllReadOnly;
		}
		if (typeof(_cfg.isWAD) != "undefined") {
			this.isWAD = _cfg.isWAD;
		}
		Ext.applyIf(this, _cfg);
		PlateFormAccountDetailFrom.superclass.constructor.call(this, {
			items : [{
							layout : "column",
							border : false,
							scope : this,
							defaults : {
								anchor : '100%',
								columnWidth : 1,
								isFormField : true,
								labelWidth : 80
							},
							items : [{  
									columnWidth : 0.5,
								     layout : 'form',
									 items :[{
											xtype : 'textfield',
											fieldLabel : '第三方支付名称',
											allowBlank : false,
											name : 'thirdPayName',
											anchor : '98%',
											readOnly:true,
											value:this.thirdPayName
											
										  
									}]
								},{  
									 columnWidth : 0.5,
								     layout : 'form',
									 items :[{
											xtype : 'textfield',
											fieldLabel : '第三方支付类型',
											allowBlank : false,
											name : 'thirdPayType',
											anchor : '98%',
											readOnly:true,
											value:this.thirdPayType
										  
									}]
								},{  
									 columnWidth : 0.5,
								     layout : 'form',
									 items :[{
											xtype : 'textfield',
											fieldLabel : '商户号',
											allowBlank : false,
											name : 'plateFormnumber',
											anchor : '98%',
											readOnly:true,
											value:this.plateFormnumber
										  
									}]
								},{  
									 columnWidth : 0.5,
								     layout : 'form',
									 items :[{
									 	    fieldLabel : '账户类型',
											xtype : 'combo',
											allowBlank : false,
											hiddenName : "obSystemAccount.accountType",
											mode : 'local',
											displayField : 'name',
											valueField : 'id',
											readOnly:true,
											editable : false,
											store : new Ext.data.SimpleStore({
													fields : ["name", "id"],
													data : [["平台普通资金账户", "plateFormAccount"],
															["平台风险保证金账户", "plateFormRiskAccount"],
															["担保账户", "plateFormRiskAccount"]]
												}),
											anchor :'98%',
											value:this.accountType
											
								     }]
								},{  
									 columnWidth : 0.5,
								     layout : 'form',
									 items :[{
											xtype : 'textfield',
											fieldLabel : '账户名',
											allowBlank : false,
											name : 'obSystemAccount.accountName',
											anchor : '98%'
										  
									 },{
									    xtype:'hidden',
									    name:'obSystemAccount.id'
									 }]
								},{  
									 columnWidth : .45,
								     layout : 'form',
									 items :[{
											xtype : 'numberfield',
											fieldLabel : '账户余额',
											allowBlank : false,
											readOnly:true,
											name : 'obSystemAccount.totalMoney',
											anchor : '98%',
											value:0
									 }]
								},{
									 columnWidth : .05, // 该列在整行中所占的百分比
									 layout : "form", // 从上往下的布局
									 labelWidth :13,
									 items : [{
											fieldLabel : "<span style='margin-left:1px'>元</span> ",
											labelSeparator : '',
											anchor : "100%"
								     }]
							   },{  
									 columnWidth : 0.5,
								     layout : 'form',
								     hidden:this.isHidden,
									 items :[{
									 	    fieldLabel : '支付方式',
											xtype : 'combo',
											allowBlank :this.isHidden?true: false,
											hiddenName : "payType",
											mode : 'local',
											displayField : 'itemName',
											triggerAction : "all",
											valueField : 'itemId',
											editable : false,
											store : new Ext.data.SimpleStore({
												fields : ['itemName','itemId'],
													data : [["企业网银", "B2BBANK"],
															["个人借记卡网银", "B2CDEBITBANK"]]
												}),
											anchor :'98%',
											listeners : {
												afterrender : function(combox) {
													var st = combox.getStore();
													st.on("load", function() {
																var record = st.getAt(0);
																var v = record.data.itemId;
																combox.setValue(v);
																combox.fireEvent("select",combox, record, 0);
															})
													combox.clearInvalid();
												},
												select : function(combox, record, index) {
													var v = record.data.itemId;
													var arrStore = new Ext.data.ArrayStore({
														url : __ctxPath+ '/creditFlow/creditAssignment/bank/getBankListObSystemAccount.do',
														baseParams:{payType : v},
														fields : ['itemId','itemName'],
														autoLoad : true
													});
													var opr_obj = this.ownerCt.ownerCt.getCmpByName('bankcode')
													opr_obj.enable();
													opr_obj.setValue();
													opr_obj.store.removeAll()
													opr_obj.store=arrStore;
													arrStore.load({"callback" : test});
													function test(r) {
														if (opr_obj.view) { // 刷新视图,避免视图值与实际值不相符
															opr_obj.view.setStore(arrStore);
														}
														if (typeof(arrStore.getAt(0)) != "undefined") {
															var itmeId = arrStore.getAt(0).data.itemId;
															var itemName = arrStore.getAt(0).data.itemName;
															opr_obj.setRawValue(itemName);
															opr_obj.setValue(itmeId);
															var recordN = arrStore.getAt(0);
															opr_obj.fireEvent("select",opr_obj, arrStore.getAt(0), 0);
														}
													}
												}
											}
								     }]
								},{  
									 columnWidth :0.5,
								     layout : 'form',
								     hidden:this.isHidden,
									 items :[{
											xtype :'combo',
											fieldLabel : '支付银行',
											displayField : 'itemName',
											allowBlank :this.isHidden?true: false,
											valueField : 'itemId',
											triggerAction : 'all',
											mode : 'remote',
											disable : true,
											hiddenName : "bankcode",
											editable : false,
											store: new Ext.data.ArrayStore({
														url : __ctxPath+ '/creditFlow/creditAssignment/bank/getBankListObSystemAccount.do',
														fields : ['itemId','itemName'],
														autoLoad : true
													}),
											anchor : "100%",
											listeners : {
												scope : this,
												select : function(combox, record, index) {
													var v = record.data.itemId;
													var arrStore = new Ext.data.ArrayStore({
																url : __ctxPath+ '/creditFlow/creditAssignment/bank/getBankListObSystemAccount.do',
																fields : ['itemId','itemName'],
																baseParams : {
																	payType : v
																}
															});
													var opr_obj = this.ownerCt.get(0).getCmpByName('payType');
													opr_obj.store = arrStore;
													arrStore.load({"callback" : test});
													function test() {
														if (opr_obj.view) { // 刷新视图,避免视图值与实际值不相符
															opr_obj.view.setStore(arrStore);
														}
														if (typeof(arrStore.getAt(0)) != "undefined") {
															var itmeId = arrStore.getAt(0).data.itemId;
															var itemName = arrStore.getAt(0).data.itemName;
															opr_obj.setRawValue(itemName);
															opr_obj.setValue(itmeId);
														}
													}
												}
											}
										  
									}]
								},{  
									 columnWidth : .95,
								     layout : 'form',
									 items :[{
											xtype : 'numberfield',
											fieldLabel : '充值金额',
											allowBlank : false,
											readOnly:true,
											name : 'rechargeMoney',
											anchor : '98%',
											value:0
									 }]
								},{
									 columnWidth : .05, // 该列在整行中所占的百分比
									 layout : "form", // 从上往下的布局
									 labelWidth :13,
									 items : [{
											fieldLabel : "<span style='margin-left:1px'>元</span> ",
											labelSeparator : '',
											anchor : "100%"
								     }]
							   }]
						}]
		});
	}
});


PlateFormAccountDealInfoFrom = Ext.extend(Ext.Panel, {
	layout : "form",
	autoHeight : true,
	labelAlign : 'right',
	isAllReadOnly : true,
	isWAD:true,
	constructor : function(_cfg) {
		if (_cfg == null) {
			_cfg = {};
		}
		Ext.applyIf(this, _cfg);
		PlateFormAccountDealInfoFrom.superclass.constructor.call(this, {
			items : [{
							layout : "column",
							border : false,
							scope : this,
							defaults : {
								anchor : '100%',
								columnWidth : 1,
								isFormField : true,
								labelWidth : 80
							},
							items : []
						}]
		});
	}
});

