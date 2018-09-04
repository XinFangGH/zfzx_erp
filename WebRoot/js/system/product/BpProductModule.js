BpProductModule_ProductDiscribe = Ext.extend(Ext.Panel, {
	layout : "form",
	autoHeight : true,
	frame : true,
	labelAlign : 'right',
	isAllReadOnly : false,
	isFlow : true,
	constructor : function(_cfg) {
		if (_cfg == null) {
			_cfg = {};
		}
		if (_cfg.isAllReadOnly) {
			this.isAllReadOnly = _cfg.isAllReadOnly;
		}
		Ext.applyIf(this, _cfg);
		BpProductModule_ProductDiscribe.superclass.constructor.call(this, {
			layout : 'anchor',
			frame : true,
			border : false,
			scope : this,
			defaults : {
				anchor : '98%',
				columnWidth : 1,
				isFormField : true,
				labelWidth : '115'
			},
			items : [{
				layout : "column",
				border : false,
				scope : this,
				defaults : {
					anchor : '100%',
					columnWidth : 1,
					isFormField : true,
					labelWidth : 90
				},
				items : [{
					columnWidth : .3, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : 90,
					items : [{
								xtype : "combo",
								anchor : "100%",
								hiddenName : "bpProductParameter.businessType",
								displayField : 'itemName',
								valueField : 'itemId',
								triggerAction : 'all',
								readOnly : true,
								disable : true,
								store : new Ext.data.SimpleStore({
									autoLoad : true,
									url :  __ctxPath+ '/system/getTypeJsonByNodeKeyGlobalType.do?nodeKey=Business',
								  fields : ['itemId', 'itemName']
								}),
								fieldLabel : "业务类别",
								blankText : "业务类别不能为空，请正确填写!",
								listeners : {
									afterrender : function(combox) {
										var st = combox.getStore();
										st.on("load", function() {
													var record = st.getAt(0);
													var v = record.data.itemId;
													combox.setValue(v);
													
													//combox.fireEvent("select",combox, record);
													
												})
										combox.clearInvalid();
									},
									select : function(combox, record, index) {
										var v = record.data.itemId;
										var arrStore = new Ext.data.ArrayStore({
													
													url : __ctxPath+ '/system/getTypeJsonByNodeKeyGlobalType.do',
													fields : ['itemId','itemName'],
													baseParams : {
														nodeKey : v
													},
													autoLoad : true
												});
										var opr_obj = this.ownerCt.ownerCt.getCmpByName('bpProductParameter.operationType')
										opr_obj.clearValue();
										opr_obj.store = arrStore;
										arrStore.load({
													"callback" : test
												});
										function test(r) {
											if (opr_obj.view) { // 刷新视图,避免视图值与实际值不相符
												opr_obj.view.setStore(arrStore);
											}
											if (typeof(arrStore.getAt(0)) != "undefined") {
												var itmeId = arrStore.getAt(0).data.itemId;
												var itemName = arrStore
														.getAt(0).data.itemName;
												opr_obj.setRawValue(itemName);
												opr_obj.setValue(itmeId);
												var recordN = arrStore.getAt(0);
												opr_obj.fireEvent("select",
														opr_obj, arrStore
																.getAt(0), 0);
											}
										}

									}
									
								}
							}]
				}, {
					columnWidth : .3, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : 90,
					items : [{
								fieldLabel : "业务品种",
								xtype : "combo",
								displayField : 'itemName',
								valueField : 'itemId',
								triggerAction : 'all',
								mode : 'remote',
								disable : true,
								readOnly : this.isEditReadOnly,
								store : new Ext.data.ArrayStore({
									url : __ctxPath+ '/system/getTypeJsonByNodeKeyGlobalType.do',
									fields : ['itemId','itemName'],
									autoLoad : true
								}),
								hiddenName : "bpProductParameter.operationType",
								editable : false,
								blankText : "业务品种不能为空，请正确填写!",
								anchor : "100%",
								listeners : {
									scope : this,
									loadData : function(opr_obj){
										var v = this.getCmpByName('bpProductParameter.businessType').getValue();
										var value=this.getCmpByName('bpProductParameter.operationType').getValue();
										var arrStore = new Ext.data.ArrayStore({
													
													url : __ctxPath+ '/system/getTypeJsonByNodeKeyGlobalType.do',
													fields : ['itemId','itemName'],
													baseParams : {
														nodeKey : v
													}
												});
										opr_obj.clearValue();
										opr_obj.store = arrStore;
										arrStore.load({
											"callback" : test
										});
										function test(r) {
											if (opr_obj.view) { // 刷新视图,避免视图值与实际值不相符
												opr_obj.view.setStore(arrStore);
											}
													opr_obj.setValue(value);
											
										}

									}
								}
							}]
				},{
					columnWidth : 0.4, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : 90,
					items : [{
								name : 'bpProductParameter.id',
								xtype : 'hidden',
								value : this.id == null ? '' : this.id
							}, {
								fieldLabel : '产品名称',
								name : 'bpProductParameter.productName',
								xtype : 'textfield',
								allowBlank : false,
								readOnly : this.isEditReadOnly,
								anchor : '100%',
								maxLength : 255
							}]
				},{
					columnWidth : .3, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : 90,
					items : [{
						xtype : "dicIndepCombo",
						fieldLabel : "我方主体类型",
						emptyText : "请选择",
						nodeKey : 'ourmainType',
						anchor : "100%",
						allowBlank : false,
						readOnly : this.isAllReadOnly,
						editable : false,
						value : null,
						hiddenName : "bpProductParameter.mineType",
						scope : this,
						listeners : {
							change : function(combox, record, index) {
								var comboxValue = combox.getValue();
								var url = '';
								var store = null;
								var combo = this.ownerCt.ownerCt.getCmpByName('bpProductParameter.mineId');
								combo.clearValue();
								if (comboxValue == "company_ourmain") {// 企业
									url = __ctxPath
											+ '/creditFlow/ourmain/listSlCompanyMain.do';
									store = new Ext.data.Store({
										proxy : new Ext.data.HttpProxy({
													url : url
												}),
										reader : new Ext.data.JsonReader({
													root : 'result'
												}, [{
															name : 'itemName',
															mapping : 'corName'
														}, {
															name : 'itemValue',
															mapping : 'companyMainId'
														}])
									})
								} else if (comboxValue == "person_ourmain") { // 个人
									url = __ctxPath
											+ '/creditFlow/ourmain/listSlPersonMain.do';
									store = new Ext.data.Store({
										proxy : new Ext.data.HttpProxy({
													url : url
												}),
										reader : new Ext.data.JsonReader({
													root : 'result'
												}, [{
															name : 'itemName',
															mapping : 'name'
														}, {
															name : 'itemValue',
															mapping : 'personMainId'
														}])
									})
								}
								combo.store = store;
								combo.valueField = "itemValue";
								store.load();
								if (combo.view) { // 刷新视图,避免视图值与实际值不相符
									combo.view.setStore(combo.store);
								}
								this.fireEvent("afterrender", combo);
								/*
								 * var obj_n =
								 * this.ownerCt.ownerCt.ownerCt.ownerCt.ownerCt;
								 * ressetProjuect(obj_n);
								 */
							},
							afterrender : function(combox) {
								var st = combox.getStore();
								st.on("load", function() {
											var v = combox.getValue();
											combox.setValue(v);
										});
								combox.clearInvalid();
							},
							loadData : function(comp) {
								var st = comp.getStore();
								st.on("load", function() {
											comp.setValue(comp.getValue());
											comp.clearInvalid();
										})

								this.fireEvent("change", comp);
							}
						}
					}]
				}, {
					columnWidth : .7, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : 90,
					items : [{
								fieldLabel : '我方主体名称',
								xtype : "combo",
								anchor : "100%",
								displayField : 'itemName',
								valueField : 'itemValue',
								emptyText : "请选择",
								allowBlank : false,
								readOnly : this.isAllReadOnly,
								editable : true,
								hiddenName : 'bpProductParameter.mineId',
								typeAhead : true,
								mode : 'local',
								editable : false,
								selectOnFocus : true,
								value : null,
								store : new Ext.data.JsonStore({}),
								triggerAction : 'all',
								blankText : "我方主体不能为空，请正确填写!",
								listeners : {
									afterrender : function(combox) {
										var st = combox.getStore();
										st.on("load", function() {
													var v = combox.getValue();
													combox.setValue(v);
												});
										combox.clearInvalid();
									}
								}
							}]
				}, {
					columnWidth : 1, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : 90,
					items : [{
								xtype : 'textarea',
								anchor : '100%',
								fieldLabel : '产品描述',
								readOnly : this.isAllReadOnly,
								name : 'bpProductParameter.productDescribe'
							}]
				}]
			}]
		});
	}
});

BpProductModule_ProductFiancial = Ext.extend(Ext.Panel, {
	layout : "form",
	autoHeight : true,
	frame : true,
	labelAlign : 'right',
	isAllReadOnly : false,
	productId : 0,
	defination:"_productDefination_",
	constructor : function(_cfg) {
		if (_cfg == null) {
			_cfg = {};
		}
		if (_cfg.isAllReadOnly) {
			this.isAllReadOnly = _cfg.isAllReadOnly;
		}
		if (_cfg.productId) {
			this.productId = _cfg.productId;
		}
		if (_cfg.defination) {
			this.defination = _cfg.defination;
		}
		Ext.applyIf(this, _cfg);
		// 每期还款日固定在
		var storepayintentPeriod = "[";
		for (var i = 1; i < 31; i++) {
			storepayintentPeriod = storepayintentPeriod + "[" + i + ", " + i
					+ "],";
		}
		storepayintentPeriod = storepayintentPeriod.substring(0,
				storepayintentPeriod.length - 1);
		storepayintentPeriod = storepayintentPeriod + "]";
		var obstorepayintentPeriod = Ext.decode(storepayintentPeriod);
		// 贷款期数
		var payintentPeriods = "[";
		for (var i = 1; i <= 36; i++) {
			payintentPeriods = payintentPeriods + "[" + i + ", " + i + "],";
		}
		payintentPeriods = payintentPeriods.substring(0,
				payintentPeriods.length - 1);
		payintentPeriods = payintentPeriods + "]";
		var obpayintentPeriods = Ext.decode(payintentPeriods);

		BpProductModule_ProductFiancial.superclass.constructor.call(this, {
			items : [{
				layout : "column",
				border : false,
				scope : this,
				defaults : {
					anchor : '100%',
					columnWidth : 1,
					isFormField : true,
					labelWidth : '110'
				},
				items : [{
					columnWidth : 1,
					layout : 'column',
					items : [{
								columnWidth : .1, // 该列在整行中所占的百分比
								layout : "form", // 从上往下的布局
								labelWidth : 85,
								labelAlign : 'right',
								items : [{
											fieldLabel : "还款方式 ",
											fieldClass : 'field-align',
											anchor : "100%"
										}]
							}, {
								columnWidth : 0.12,
								labelWidth : 1,
								layout : 'form',
								items : [{
									xtype : 'radio',
									boxLabel : '等额本金',
									name : 'f1',
									id : "jixifs1"+this.defination+this.productId,
									inputValue : false,
									anchor : "100%",
									disabled : this.isAllReadOnly,
									listeners : {
										scope : this,
										check : function(obj, checked) {
											var flag = Ext.getCmp("jixifs1"+this.defination+this.productId).getValue();
											if (flag == true) {
												Ext.getCmp("jixizq1"+this.defination+this.productId).setDisabled(true);
												Ext.getCmp("jixizq2"+this.defination+this.productId).setDisabled(true);
												Ext.getCmp("jixizq3"+this.defination+this.productId).setDisabled(true);
												Ext.getCmp("jixizq4"+this.defination+this.productId).setDisabled(true);
												Ext.getCmp("jixizq6"+this.defination+this.productId).setDisabled(true);
												this.getCmpByName('bpProductParameter.dayOfEveryPeriod').setDisabled(true);
												Ext.getCmp("jixizq2"+this.defination+this.productId).setValue(true);
												Ext.getCmp("jixizq1"+this.defination+this.productId).setValue(false);
												this.getCmpByName('bpProductParameter.payaccrualType').setValue("monthPay");
												this.getCmpByName('bpProductParameter.payintentPeriod').setDisabled(false);
												Ext.getCmp("jixifs2"+this.defination+this.productId).setValue(false);
												Ext.getCmp("jixifs5"+this.defination+this.productId).setValue(false);
												Ext.getCmp("jixifs3"+this.defination+this.productId).setValue(false);
												Ext.getCmp("jixifs6"+this.defination+this.productId).setValue(false);
												Ext.getCmp("meiqihkrq1"+this.defination+this.productId).setDisabled(false);
												Ext.getCmp("meiqihkrq2"+this.defination+this.productId).setDisabled(false);
											}
										}
									}
								}]
							}, {
								columnWidth : 0.12,
								labelWidth : 1,
								layout : 'form',
								items : [{
									xtype : 'radio',
									boxLabel : '等额本息',
									anchor : "100%",
									name : 'f1',
									id : "jixifs2"+this.defination+this.productId,
									inputValue : false,
									disabled : this.isAllReadOnly,
									listeners : {
										scope : this,
										check : function(obj, checked) {
											var flag = Ext.getCmp("jixifs2"+this.defination+this.productId).getValue();
											if (flag == true) {
												this.getCmpByName('bpProductParameter.accrualtype').setValue("sameprincipalandInterest");
												Ext.getCmp("jixizq1"+this.defination+this.productId).setDisabled(true);
												Ext.getCmp("jixizq2"+this.defination+this.productId).setDisabled(true);
												Ext.getCmp("jixizq3"+this.defination+this.productId).setDisabled(true);
												Ext.getCmp("jixizq4"+this.defination+this.productId).setDisabled(true);
												Ext.getCmp("jixizq6"+this.defination+this.productId).setDisabled(true);
												this.getCmpByName('bpProductParameter.dayOfEveryPeriod').setDisabled(true);
												Ext.getCmp("jixizq2"+this.defination+this.productId).setValue(true);
												Ext.getCmp("jixizq1"+this.defination+this.productId).setValue(false);
												this.getCmpByName('bpProductParameter.payaccrualType').setValue("monthPay");
												this.getCmpByName('bpProductParameter.payintentPeriod').setDisabled(false);
												Ext.getCmp("jixifs1"+this.defination+this.productId).setValue(false);
												Ext.getCmp("jixifs5"+this.defination+this.productId).setValue(false);
												Ext.getCmp("jixifs3"+this.defination+this.productId).setValue(false);
												Ext.getCmp("jixifs6"+this.defination+this.productId).setValue(false);
												Ext.getCmp("meiqihkrq1"+this.defination+this.productId).setDisabled(false);
												Ext.getCmp("meiqihkrq2"+this.defination+this.productId).setDisabled(false);
											}
										}
									}
								}]
							}, {
								columnWidth : 0.12,
								labelWidth : 1,
								layout : 'form',
								items : [{
									xtype : 'radio',
									boxLabel : '等本等息',
									anchor : "100%",
									name : 'f1',
									id : "jixifs5"+this.defination+this.productId,
									inputValue : false,
									disabled : this.isAllReadOnly,
									listeners : {
										scope : this,
										check : function(obj, checked) {
											var flag = Ext.getCmp("jixifs5"+this.defination+this.productId).getValue();
											if (flag == true) {
												this.getCmpByName('bpProductParameter.accrualtype').setValue("sameprincipalsameInterest");
												if (this.isAllReadOnly == true) {
													Ext.getCmp("jixizq1"+this.defination+this.productId).setDisabled(true);
													Ext.getCmp("jixizq2"+this.defination+this.productId).setDisabled(true);
													Ext.getCmp("jixizq3"+this.defination+this.productId).setDisabled(true);
													Ext.getCmp("jixizq4"+this.defination+this.productId).setDisabled(true);
													Ext.getCmp("jixizq6"+this.defination+this.productId).setDisabled(true);
												} else {
													Ext.getCmp("jixizq1"+this.defination+this.productId).setDisabled(false);
													Ext.getCmp("jixizq2"+this.defination+this.productId).setDisabled(false);
													Ext.getCmp("jixizq3"+this.defination+this.productId).setDisabled(false);
													Ext.getCmp("jixizq4"+this.defination+this.productId).setDisabled(false);
													Ext.getCmp("jixizq6"+this.defination+this.productId).setDisabled(false);
												}
												this.getCmpByName('bpProductParameter.dayOfEveryPeriod').setDisabled(true);
												this.getCmpByName('bpProductParameter.payaccrualType').setValue("monthPay");
												this.getCmpByName('bpProductParameter.payintentPeriod').setDisabled(false);
												this.getCmpByName('bpProductParameter.payintentPerioDate').setDisabled(true);
												Ext.getCmp("jixifs2"+this.defination+this.productId).setValue(false);
												Ext.getCmp("jixifs1"+this.defination+this.productId).setValue(false);
												Ext.getCmp("jixifs3"+this.defination+this.productId).setValue(false);
												Ext.getCmp("jixifs6"+this.defination+this.productId).setValue(false);
												Ext.getCmp("meiqihkrq1"+this.defination+this.productId).setDisabled(true);
												Ext.getCmp("meiqihkrq1"+this.defination+this.productId).setValue(false);
												Ext.getCmp("meiqihkrq2"+this.defination+this.productId).setDisabled(true);
												Ext.getCmp("meiqihkrq2"+this.defination+this.productId).setValue(true)
											}
										}
									}
								}]
							}, {
								columnWidth : 0.15,
								labelWidth : 1,
								layout : 'form',
								items : [{
									xtype : 'radio',
									boxLabel : '按期收息,到期还本',
									name : 'f1',
									id : "jixifs3"+this.defination+this.productId,
									inputValue : false,
									checked : true,
									anchor : "100%",
									disabled : this.isAllReadOnly,
									listeners : {
										scope : this,
										check : function(obj, checked) {
											var flag = Ext.getCmp("jixifs3"+this.defination+this.productId)
													.getValue();
											if (flag == true) {
												this.getCmpByName('bpProductParameter.accrualtype').setValue("singleInterest");
												Ext.getCmp("jixizq1"+this.defination+this.productId).setDisabled(false);
												Ext.getCmp("jixizq2"+this.defination+this.productId).setDisabled(false);
												Ext.getCmp("jixizq3"+this.defination+this.productId).setDisabled(false);
												Ext.getCmp("jixizq4"+this.defination+this.productId).setDisabled(false);
												Ext.getCmp("jixizq6"+this.defination+this.productId).setDisabled(false);
												this.getCmpByName('bpProductParameter.dayOfEveryPeriod').setDisabled(false);
												Ext.getCmp("jixifs2"+this.defination+this.productId).setValue(false);
												Ext.getCmp("jixifs5"+this.defination+this.productId).setValue(false);
												Ext.getCmp("jixifs1"+this.defination+this.productId).setValue(false);
												Ext.getCmp("jixifs6"+this.defination+this.productId).setValue(false);
												Ext.getCmp("meiqihkrq1"+this.defination+this.productId).setDisabled(false);
												Ext.getCmp("meiqihkrq2"+this.defination+this.productId).setDisabled(false);
											}
										}
									}
								}, {
									xtype : "hidden",
									name : "bpProductParameter.accrualtype",
									value : "singleInterest"
								}]
							}, {
								columnWidth : 0.15,
								labelWidth : 1,
								layout : 'form',
								items : [{
									xtype : 'radio',
									boxLabel : '其他还款方式',
									anchor : "100%",
									name : 'f1',
									id : "jixifs6"+this.defination+this.productId,
									inputValue : false,
									disabled : this.isAllReadOnly,
									listeners : {
										scope : this,
										check : function(obj, checked) {
											var flag = Ext.getCmp("jixifs6"+this.defination+this.productId).getValue();
											if (flag == true) {
												this.getCmpByName('bpProductParameter.accrualtype').setValue("otherMothod");
												if (this.isAllReadOnly == true) {
													Ext.getCmp("jixizq1"+this.defination+this.productId).setDisabled(true);
													Ext.getCmp("jixizq2"+this.defination+this.productId).setDisabled(true);
													Ext.getCmp("jixizq3"+this.defination+this.productId).setDisabled(true);
													Ext.getCmp("jixizq4"+this.defination+this.productId).setDisabled(true);
													Ext.getCmp("jixizq6"+this.defination+this.productId).setDisabled(true);
												} else {
													Ext.getCmp("jixizq1"+this.defination+this.productId).setDisabled(false);
													Ext.getCmp("jixizq2"+this.defination+this.productId).setDisabled(false);
													Ext.getCmp("jixizq3"+this.defination+this.productId).setDisabled(false);
													Ext.getCmp("jixizq4"+this.defination+this.productId).setDisabled(false);
													Ext.getCmp("jixizq6"+this.defination+this.productId).setDisabled(false);
												}
												this.getCmpByName('bpProductParameter.dayOfEveryPeriod').setDisabled(true);
												Ext.getCmp("jixizq2"+this.defination+this.productId).setValue(true);
												Ext.getCmp("jixizq1"+this.defination+this.productId).setValue(false);
												this.getCmpByName('bpProductParameter.payaccrualType').setValue("monthPay");
												this.getCmpByName('bpProductParameter.payintentPeriod').setDisabled(false);
												Ext.getCmp("jixifs2"+this.defination+this.productId).setValue(false);
												Ext.getCmp("jixifs5"+this.defination+this.productId).setValue(false);
												Ext.getCmp("jixifs3"+this.defination+this.productId).setValue(false);
												Ext.getCmp("jixifs1"+this.defination+this.productId).setValue(false);
												Ext.getCmp("meiqihkrq1"+this.defination+this.productId).setDisabled(false);
												Ext.getCmp("meiqihkrq2"+this.defination+this.productId).setDisabled(false);
											}
										}
									}

								}]
							}]
				}, {
					columnWidth : 1,
					layout : 'column',
					items : [{
								columnWidth : .1, // 该列在整行中所占的百分比
								layout : "form", // 从上往下的布局
								labelWidth : 85,
								labelAlign : 'right',
								items : [{
											fieldLabel : "还款周期 "
										}]
							}, {
								columnWidth : 0.12,
								labelWidth : 1,
								layout : 'form',
								items : [{
									xtype : 'radio',
									boxLabel : '日',
									name : 'z1',
									id : "jixizq1"+this.defination+this.productId,
									inputValue : true,
									anchor : "100%",
									disabled : this.isAllReadOnly,
									listeners : {
										scope : this,
										check : function(obj, checked) {
											var flag = Ext.getCmp("jixizq1"+this.defination+this.productId).getValue();
											if (flag == true) {
												this.getCmpByName('bpProductParameter.payaccrualType').setValue("dayPay");
												Ext.getCmp("meiqihkrq1"+this.defination+this.productId).setDisabled(true);
												Ext.getCmp("meiqihkrq1"+this.defination+this.productId).setValue(false);
												Ext.getCmp("meiqihkrq2"+this.defination+this.productId).setDisabled(true);
												Ext.getCmp("meiqihkrq2"+this.defination+this.productId).setValue(true);
												Ext.getCmp("jixizq2"+this.defination+this.productId).setValue(false);
												Ext.getCmp("jixizq3"+this.defination+this.productId).setValue(false);
												Ext.getCmp("jixizq4"+this.defination+this.productId).setValue(false);
												Ext.getCmp("jixizq6"+this.defination+this.productId).setValue(false);
												this.getCmpByName('bpProductParameter.isStartDatePay').setValue("2");
											} else {
												Ext.getCmp("meiqihkrq1"+this.defination+this.productId).setDisabled(false);
												Ext.getCmp("meiqihkrq2"+this.defination+this.productId).setDisabled(false);
												if (Ext.getCmp("meiqihkrq1"+this.defination+this.productId).getValue() == true) {
													this.getCmpByName('bpProductParameter.payintentPerioDate').setDisabled(false);
												}
											}
										}
									}
								}]
							}, {
								columnWidth : 0.12,
								labelWidth : 1,
								layout : 'form',
								items : [{
									xtype : 'radio',
									boxLabel : '月',
									name : 'z1',
									id : "jixizq2"+this.defination+this.productId,
									inputValue : false,
									checked : true,
									anchor : "100%",
									listeners : {
										scope : this,
										check : function(obj, checked) {
											var flag = Ext.getCmp("jixizq2"+this.defination+this.productId).getValue();
											if (flag == true) {
												this.getCmpByName('bpProductParameter.payaccrualType').setValue("monthPay");
												Ext.getCmp("jixizq1"+this.defination+this.productId).setValue(false);
												Ext.getCmp("jixizq3"+this.defination+this.productId).setValue(false);
												Ext.getCmp("jixizq4"+this.defination+this.productId).setValue(false);
												Ext.getCmp("jixizq6"+this.defination+this.productId).setValue(false);
											}
										}
									}
								}, {
									xtype : 'hidden',
									name : "bpProductParameter.payaccrualType",
									value : "monthPay"
								}]
							}, {
								columnWidth : 0.12,
								labelWidth : 1,
								layout : 'form',
								items : [{
									xtype : 'radio',
									boxLabel : '季',
									name : 'z1',
									id : "jixizq3"+this.defination+this.productId,
									inputValue : false,
									anchor : "100%",
									disabled : this.isAllReadOnly,
									listeners : {
										scope : this,
										check : function(obj, checked) {
											var flag = Ext.getCmp("jixizq3"+this.defination+this.productId).getValue();
											if (flag == true) {
												this.getCmpByName('bpProductParameter.payaccrualType').setValue("seasonPay");
												Ext.getCmp("jixizq1"+this.defination+this.productId).setValue(false);
												Ext.getCmp("jixizq2"+this.defination+this.productId).setValue(false);
												Ext.getCmp("jixizq4"+this.defination+this.productId).setValue(false);
												Ext.getCmp("jixizq6"+this.defination+this.productId).setValue(false);
											}
										}
									}
								}]
							}, {
								columnWidth : 0.12,
								labelWidth : 1,
								layout : 'form',
								items : [{
									xtype : 'radio',
									boxLabel : '年',
									name : 'z1',
									id : "jixizq4"+this.defination+this.productId,
									inputValue : false,
									anchor : "100%",
									disabled : this.isAllReadOnly,
									listeners : {
										scope : this,
										check : function(obj, checked) {
											var flag = Ext.getCmp("jixizq4"+this.defination+this.productId).getValue();
											if (flag == true) {
												this.getCmpByName('bpProductParameter.payaccrualType').setValue("yearPay");
												Ext.getCmp("jixizq1"+this.defination+this.productId).setValue(false);
												Ext.getCmp("jixizq3"+this.defination+this.productId).setValue(false);
												Ext.getCmp("jixizq2"+this.defination+this.productId).setValue(false);
												Ext.getCmp("jixizq6"+this.defination+this.productId).setValue(false);
											}
										}
									}
								}]
							}, {
								columnWidth : .13,
								labelWidth : 1,
								layout : 'form',
								items : [{
									xtype : 'radio',
									boxLabel : '自定义周期',
									name : 'z1',
									id : "jixizq6"+this.defination+this.productId,
									inputValue : false,
									anchor : "100%",
									disabled : this.isAllReadOnly,
									listeners : {
										scope : this,
										check : function(obj, checked) {
											var flag = Ext.getCmp("jixizq6"+this.defination+this.productId).getValue();
											if (flag == true) {
												this.getCmpByName('bpProductParameter.payaccrualType').setValue("owerPay");
												this.getCmpByName('bpProductParameter.dayOfEveryPeriod').setDisabled(false);
												Ext.getCmp("meiqihkrq1"+this.defination+this.productId).setDisabled(true);
												Ext.getCmp("meiqihkrq1"+this.defination+this.productId).setValue(false);
												Ext.getCmp("meiqihkrq2"+this.defination+this.productId).setDisabled(true);
												Ext.getCmp("meiqihkrq2"+this.defination+this.productId).setValue(true);
												Ext.getCmp("jixizq1"+this.defination+this.productId).setValue(false);
												Ext.getCmp("jixizq3"+this.defination+this.productId).setValue(false);
												Ext.getCmp("jixizq4"+this.defination+this.productId).setValue(false);
												Ext.getCmp("jixizq2"+this.defination+this.productId).setValue(false);
											} else {
												this.getCmpByName('bpProductParameter.dayOfEveryPeriod').setDisabled(true);
												this.getCmpByName('bpProductParameter.dayOfEveryPeriod').setValue("");
												if (Ext.getCmp("jixizq1"+this.defination+this.productId).getValue() == false) {
													Ext.getCmp("meiqihkrq1"+this.defination+this.productId).setDisabled(false);
													Ext.getCmp("meiqihkrq2"+this.defination+this.productId).setDisabled(false);
													if (Ext.getCmp("meiqihkrq1"+this.defination+this.productId).getValue() == true) {
														this.getCmpByName('bpProductParameter.payintentPerioDate').setDisabled(false);
													}
												}
											}
										}
									}
								}]
							}, {
								columnWidth : 0.08,
								labelWidth : 1,
								layout : 'form',
								items : [{
									xtype : 'textfield',
									anchor : "100%",
									readOnly : this.isAllReadOnly,
									fieldClass : 'field-align',
									name : 'bpProductParameter.dayOfEveryPeriod'
								}]
							}, {
								columnWidth : 0.07,
								labelWidth : 40,
								layout : 'form',
								items : [{
											fieldLabel : "日/期",
											labelSeparator : '',
											anchor : "100%"
										}]
							}]
				},{
					columnWidth : 1,
					layout : 'column',
					items : [{
								columnWidth : .1,
								layout : 'form',
								labelWidth : 85,
								labelAlign : 'right',
								items : [{
											fieldLabel : '还款选项'
										}]
							}, {
								columnWidth : .1, // 该列在整行中所占的百分比
								layout : "form", // 从上往下的布局
								labelWidth : 1,
								items : [{
									xtype : "checkbox",
									boxLabel : "前置付息",
									disabled : this.isAllReadOnly,
									anchor : "100%",
									name : "isPreposePayAccrualCheck",
									checked : this.record == null
											|| this.record.data.isPreposePayAccrual == 0
											? null
											: true,
									listeners : {
										scope : this,
										'check' : function(box, value) {
											if (value == true) {
												this.getCmpByName('bpProductParameter.isPreposePayAccrual').setValue(1);
											} else {
												this.getCmpByName('bpProductParameter.isPreposePayAccrual').setValue(0);
											}
										}
									}
								}, {
									xtype : 'hidden',
									name : 'bpProductParameter.isPreposePayAccrual',
									value : 0
								}]
							}, {
								columnWidth : .16, // 该列在整行中所占的百分比
								layout : "form", // 从上往下的布局
								labelWidth : 1,
								items : [{
									xtype : "checkbox",
									boxLabel : "一次性支付全部利息",
									disabled : this.isAllReadOnly,
									anchor : "100%",
									name : "isInterestByOneTimeCheck",
									checked : this.record == null
											|| this.record.data.isInterestByOneTime == 0
											? null
											: true,
									listeners : {
										scope : this,
										'check' : function(box, value) {
											if (value == true) {
												this.getCmpByName('bpProductParameter.isInterestByOneTime').setValue(1);
											} else {
												this.getCmpByName('bpProductParameter.isInterestByOneTime').setValue(0);
											}
										}
									}
								}, {
									xtype : 'hidden',
									name : 'bpProductParameter.isInterestByOneTime',
									value : 0
								}]
							}, {
								columnWidth : .6,
								name : "mqhkri",
								layout : "column",
								items : [{
									columnWidth : 0.27,
									labelWidth : 80,
									layout : 'form',
									items : [{
										xtype : 'radio',
										fieldLabel : '每期还款日',
										boxLabel : '固定在',
										name : 'q1',
										id : "meiqihkrq1"+this.defination+this.productId,
										inputValue : true,
										anchor : "100%",
										disabled : this.isAllReadOnly,
										listeners : {
											scope : this,
											check : function(obj, checked) {
												var flag = Ext.getCmp("meiqihkrq1"+this.defination+this.productId).getValue();
												if (flag == true) {
													this.getCmpByName('bpProductParameter.isStartDatePay').setValue("1");
													this.getCmpByName('bpProductParameter.payintentPerioDate').setDisabled(false);
												}
											}
										}
									}, {
										xtype : "hidden",
										name : "bpProductParameter.isStartDatePay"
									}]
								}, {
									columnWidth : 0.1,
									labelWidth : 1,
									layout : 'form',
									items : [{
										readOnly : this.isAllReadOnly,
										xtype : 'combo',
										mode : 'local',
										disabled : true,
										displayField : 'name',
										valueField : 'id',
										editable : true,
										store : new Ext.data.SimpleStore({
													fields : ["name", "id"],
													data : obstorepayintentPeriod
												}),
										triggerAction : "all",
										hiddenName : "bpProductParameter.payintentPerioDate",
										fieldLabel : "",
										anchor : '100%'
									}]
								}, {
									columnWidth : 0.12,
									labelWidth : 45,
									layout : 'form',
									items : [{
												fieldLabel : "号还款",
												labelSeparator : '',
												anchor : "100%"
											}]
								}, {
									columnWidth : 0.45,
									labelWidth : 10,
									layout : 'form',
									items : [{
										xtype : 'radio',
										boxLabel : '按实际放款日对日还款',
										name : 'q1',
										id : "meiqihkrq2"+this.defination+this.productId,
										inputValue : true,
										checked : true,
										anchor : "100%",
										disabled : this.isAllReadOnly,
										listeners : {
											scope : this,
											check : function(obj, checked) {
												var flag = Ext.getCmp("meiqihkrq2"+this.defination+this.productId).getValue();
												if (flag == true) {
													this.getCmpByName('bpProductParameter.isStartDatePay').setValue("2");
													this.getCmpByName('bpProductParameter.payintentPerioDate').setValue(null);
													this.getCmpByName('bpProductParameter.payintentPerioDate').setDisabled(true);
												}
											}
										}
									}]
								}]
							}]
				},  {
					columnWidth : 1,
					layout : 'column',
					items : [{
								columnWidth : .1,
								layout : 'form',
								labelWidth : 85,
								labelAlign : 'right',
								items : [{
											fieldLabel : '贷款信息'
										}]
							}, {
								columnWidth : .07,
								layout : 'form',
								labelWidth : 60,
								labelAlign : 'right',
								items : [{
											fieldLabel : "贷款期限",
											labelSeparator : ''
										}]
							},{
								columnWidth : .15, // 该列在整行中所占的百分比
								layout : "form", // 从上往下的布局
								labelWidth : 85,
								labelAlign : 'right',
								items : [{
											hideLabel : true,
											xtype : "combo",
											readOnly : this.isAllReadOnly,
											hiddenName : "bpProductParameter.payintentPeriod",
											mode : 'local',
											displayField : 'name',
											valueField : 'id',
											editable : false,
											store : new Ext.data.SimpleStore({
														fields : ["name", "id"],
														data : obpayintentPeriods
													}),
											triggerAction : "all",
											anchor : "100%"
										}]
							}, {
							columnWidth : .05, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : 20,
							items : [{
										fieldLabel : "期",
										labelSeparator : '',
										anchor : "100%"
									}]
						}]
				}, {
					columnWidth : 1,
					layout : 'column',
					items : [{
								columnWidth : .1,
								layout : 'form',
								labelWidth : 85,
								labelAlign : 'right',
								items : [{
											fieldLabel : '贷款利率'
										}]
							}, {
								columnWidth : .07,
								layout : 'form',
								labelWidth : 60,
								labelAlign : 'right',
								items : [{
											fieldLabel : "年化利率",
											labelSeparator : ''
										}]
							}, {
								columnWidth : .15,
								layout : 'form',
								items : [{
									hideLabel : true,
									xtype : 'numberfield',
									name : "bpProductParameter.yearAccrualRate",
									readOnly : this.isAllReadOnly,
									fieldClass : 'field-align',
									decimalPrecision : 8,
									anchor : '100%',
									style : {
										imeMode : 'disabled'
									},
									value : 0,
									listeners : {
										scope : this,
										'blur' : function(nf) {
											var accrualmonth = this.getCmpByName('bpProductParameter.accrual')
											var dayAccrualRate = this.getCmpByName('bpProductParameter.dayAccrualRate')
											var payintentPeriod = this.getCmpByName('bpProductParameter.payintentPeriod');
											accrualmonth.setValue(nf.getValue()/ 12);
											dayAccrualRate.setValue(nf.getValue()/ 360);
										}
									}
								}]
							}, {
								columnWidth : .05,
								layout : 'form',
								labelWidth : 20,
								labelAlign : 'left',
								items : [{
											fieldLabel : "%",
											labelSeparator : '',
											anchor : "100%"
										}]
							}, {
								columnWidth : .07,
								layout : 'form',
								labelWidth : 60,
								labelAlgin : 'right',
								items : [{
											fieldLabel : "月化利率",
											labelSeparator : ''
										}]
							}, {
								columnWidth : .15,
								layout : 'form',
								items : [{
									hideLabel : true,
									xtype : 'numberfield',
									name : "bpProductParameter.accrual",
									readOnly : this.isAllReadOnly,
									fieldClass : 'field-align',
									style : {
										imeMode : 'disabled'
									},
									anchor : '100%',
									decimalPrecision : 8,
									value : 0,
									listeners : {
										scope : this,
										'blur' : function(nf) {
											var yearAccrualRate = this.getCmpByName('bpProductParameter.yearAccrualRate')
											var dayAccrualRate = this.getCmpByName('bpProductParameter.dayAccrualRate')
											var payintentPeriod = this.getCmpByName('bpProductParameter.payintentPeriod');
											yearAccrualRate.setValue(nf.getValue()* 12);
											dayAccrualRate.setValue(nf.getValue()/ 30);
										}
									}
								}]
							}, {
								columnWidth : .05,
								layout : 'form',
								labelWidth : 20,
								labelAlgin : 'left',
								items : [{
											fieldLabel : "%",
											labelSeparator : '',
											anchor : "100%"
										}]
							}, {
								columnWidth : .07,
								layout : 'form',
								labelWidth : 60,
								labelAlgin : 'right',
								items : [{
											fieldLabel : "日化利率",
											labelSeparator : ''
										}]
							}, {
								columnWidth : .15,
								layout : 'form',
								items : [{
									hideLabel : true,
									xtype : 'numberfield',
									name : "bpProductParameter.dayAccrualRate",
									readOnly : this.isAllReadOnly,
									fieldClass : 'field-align',
									anchor : '100%',
									decimalPrecision : 8,
									style : {
										imeMode : 'disabled'
									},
									value : 0,
									listeners : {
										scope : this,
										'blur' : function(nf) {
											var accrualmonth = this.getCmpByName('bpProductParameter.accrual')
											var yearAccrualRate = this.getCmpByName('bpProductParameter.yearAccrualRate')
											var payintentPeriod = this.getCmpByName('bpProductParameter.payintentPeriod');
											yearAccrualRate.setValue(nf.getValue()* 360);
											accrualmonth.setValue(nf.getValue()* 30);
										}
									}
								}]
							}, {
								columnWidth : .05,
								layout : 'form',
								labelWidth : 20,
								labelAlgin : 'left',
								items : [{
											fieldLabel : "%",
											labelSeparator : '',
											anchor : "100%"
										}]
							}]
				}, {
					columnWidth : 1,
					layout : 'column',
					items : [{
								columnWidth : .1,
								layout : 'form',
								labelWidth : 85,
								labelAlign : 'right',
								items : [{
											fieldLabel : '管理咨询费率'
										}]
							}, {
								columnWidth : .07,
								layout : 'form',
								labelWidth : 60,
								labelAlign : 'right',
								items : [{
											fieldLabel : "年化利率",
											labelSeparator : ''
										}]
							}, {
								columnWidth : .15,
								layout : 'form',
								items : [{
									hideLabel : true,
									xtype : 'numberfield',
									name : "bpProductParameter.yearManagementConsultingOfRate",
									readOnly : this.isAllReadOnly,
									fieldClass : 'field-align',
									anchor : '100%',
									decimalPrecision : 8,
									style : {
										imeMode : 'disabled'
									},
									value : 0,
									listeners : {
										scope : this,
										'change' : function(nf) {
											var accrualnf = this.getCmpByName('bpProductParameter.managementConsultingOfRate')
											var dayAccrualRatenf = this.getCmpByName('bpProductParameter.dayManagementConsultingOfRate')
											accrualnf.setValue(nf.getValue()/ 12);
											dayAccrualRatenf.setValue(nf.getValue()/ 360);
										}
									}
								}]
							}, {
								columnWidth : .05,
								layout : 'form',
								labelWidth : 20,
								labelAlign : 'left',
								items : [{
											fieldLabel : "%",
											labelSeparator : '',
											anchor : "100%"
										}]
							}, {
								columnWidth : .07,
								layout : 'form',
								labelWidth : 60,
								labelAlign : 'right',
								items : [{
											fieldLabel : "月化利率",
											labelSeparator : ''
										}]
							}, {
								columnWidth : .15,
								layout : 'form',
								items : [{
									hideLabel : true,
									xtype : 'numberfield',
									name : "bpProductParameter.managementConsultingOfRate",
									readOnly : this.isAllReadOnly,
									fieldClass : 'field-align',
									anchor : '100%',
									decimalPrecision : 8,
									style : {
										imeMode : 'disabled'
									},
									value : 0,
									listeners : {
										scope : this,
										'change' : function(nf) {
											var yearAccrualRatenf = this.getCmpByName('bpProductParameter.yearManagementConsultingOfRate')
											var dayAccrualRatenf = this.getCmpByName('bpProductParameter.dayManagementConsultingOfRate')
											yearAccrualRatenf.setValue(nf.getValue()* 12);
											dayAccrualRatenf.setValue(nf.getValue()/ 30);
										}
									}

								}]
							}, {
								columnWidth : .05,
								layout : 'form',
								labelWidth : 20,
								labelAlgin : 'left',
								items : [{
											fieldLabel : "%",
											labelSeparator : '',
											anchor : "100%"
										}]
							}, {
								columnWidth : .07,
								layout : 'form',
								labelWidth : 60,
								labelAlign : 'right',
								items : [{
											fieldLabel : "日化利率",
											labelSeparator : ''
										}]
							}, {
								columnWidth : .15,
								layout : 'form',
								items : [{
									hideLabel : true,
									xtype : 'numberfield',
									name : "bpProductParameter.dayManagementConsultingOfRate",
									readOnly : this.isAllReadOnly,
									anchor : '100%',
									decimalPrecision : 8,
									fieldClass : 'field-align',
									style : {
										imeMode : 'disabled'
									},
									value : 0,
									listeners : {
										scope : this,
										'change' : function(nf) {
											var accrualnf = this.getCmpByName('bpProductParameter.managementConsultingOfRate')
											var yearAccrualRatenf = this.getCmpByName('bpProductParameter.yearManagementConsultingOfRate')
											yearAccrualRatenf.setValue(nf.getValue()* 360);
											accrualnf.setValue(nf.getValue()* 30);
										}
									}
								}]
							}, {
								columnWidth : .05,
								layout : 'form',
								labelWidth : 20,
								labelAlgin : 'left',
								items : [{
											fieldLabel : "%",
											labelSeparator : '',
											anchor : "100%"
										}]
							}]
				}, {
					columnWidth : 1,
					layout : 'column',
					items : [{
								columnWidth : .1,
								layout : 'form',
								labelWidth : 85,
								labelAlign : 'right',
								items : [{
											fieldLabel : '财务服务费率'
										}]
							}, {
								columnWidth : .07,
								layout : 'form',
								labelWidth : 60,
								labelAlign : 'right',
								items : [{
											fieldLabel : "年化利率",
											labelSeparator : ''
										}]
							}, {
								columnWidth : .15,
								layout : 'form',
								items : [{
									hideLabel : true,
									xtype : 'numberfield',
									name : "bpProductParameter.yearFinanceServiceOfRate",
									readOnly : this.isAllReadOnly,
									decimalPrecision : 8,
									anchor : '100%',
									fieldClass : 'field-align',
									style : {
										imeMode : 'disabled'
									},
									value : 0,
									listeners : {
										scope : this,
										'change' : function(nf) {
											var accrualnf = this.getCmpByName('bpProductParameter.financeServiceOfRate')
											var dayAccrualRatenf = this.getCmpByName('bpProductParameter.dayFinanceServiceOfRate')
											accrualnf.setValue(nf.getValue()/ 12);
											dayAccrualRatenf.setValue(nf.getValue()/ 360);
										}
									}
								}]
							}, {
								columnWidth : .05,
								layout : 'form',
								labelWidth : 20,
								labelAlgin : 'left',
								items : [{
											fieldLabel : "%",
											labelSeparator : '',
											anchor : "100%"
										}]
							}, {
								columnWidth : .07,
								layout : 'form',
								labelWidth : 60,
								labelAlign : 'right',
								items : [{
											fieldLabel : "月化利率",
											labelSeparator : ''
										}]
							}, {
								columnWidth : .15,
								layout : 'form',
								items : [{
									hideLabel : true,
									xtype : 'numberfield',
									name : "bpProductParameter.financeServiceOfRate",
									readOnly : this.isAllReadOnly,
									decimalPrecision : 8,
									anchor : '100%',
									fieldClass : 'field-align',
									style : {
										imeMode : 'disabled'
									},
									value : 0,
									listeners : {
										scope : this,
										'change' : function(nf) {
											var yearAccrualRatenf = this.getCmpByName('bpProductParameter.yearFinanceServiceOfRate')
											var dayAccrualRatenf = this.getCmpByName('bpProductParameter.dayFinanceServiceOfRate')
											yearAccrualRatenf.setValue(nf.getValue()* 12)
											dayAccrualRatenf.setValue(nf.getValue()/ 30)
										}
									}

								}]
							}, {
								columnWidth : .05,
								layout : 'form',
								labelWidth : 20,
								labelAlgin : 'left',
								items : [{
											fieldLabel : "%",
											labelSeparator : '',
											anchor : "100%"
										}]
							}, {
								columnWidth : .07,
								layout : 'form',
								labelWidth : 60,
								labelAlign : 'right',
								items : [{
											fieldLabel : "日化利率",
											labelSeparator : ''
										}]
							}, {
								columnWidth : .15,
								layout : 'form',
								items : [{
									hideLabel : true,
									xtype : 'numberfield',
									anchor : '100%',
									name : "bpProductParameter.dayFinanceServiceOfRate",
									readOnly : this.isAllReadOnly,
									decimalPrecision : 8,
									fieldClass : 'field-align',
									style : {
										imeMode : 'disabled'
									},
									value : 0,
									listeners : {
										scope : this,
										'change' : function(nf) {
											var accrualnf = this.getCmpByName('bpProductParameter.financeServiceOfRate')
											var yearAccrualRatenf = this.getCmpByName('bpProductParameter.yearFinanceServiceOfRate')
											yearAccrualRatenf.setValue(nf.getValue()* 360);
											accrualnf.setValue(nf.getValue()* 30);
										}
									}
								}]
							}, {
								columnWidth : .05,
								layout : 'form',
								labelWidth : 20,
								labelAlgin : 'left',
								items : [{
											fieldLabel : "%",
											labelSeparator : '',
											anchor : "100%"
										}]
							}]
				}]
			}]
		});
	}
});