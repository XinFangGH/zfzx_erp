Ext.zwFieldSet.BaseInfo = Ext.extend(Ext.form.FieldSet, {
	layout : "form",
	autoHeight : true,
	labelAlign : 'right',
	isAllReadOnly : false,
	// isDiligenceReadOnly : true,
	constructor : function(_cfg) {
		if (_cfg == null) {
			_cfg = {};
		}
		if (_cfg.isAllReadOnly) {
			this.isAllReadOnly = _cfg.isAllReadOnly;
			// this.isDiligenceReadOnly = true;
		}
		if (typeof(_cfg.isDiligenceReadOnly) != "undefined") {
			this.isDiligenceReadOnly = _cfg.isDiligenceReadOnly;
		}
		Ext.applyIf(this, _cfg);
		// 绑定数据
		this.initComponents();
		var leftlabel = 85;
		var centerlabel = 100;
		var rightlabel = 85;
		Ext.zwFieldSet.BaseInfo.superclass.constructor.call(this, {
			items : [{
				layout : "column",
				border : false,
				scope : this,
				defaults : {
					anchor : '100%',
					columnWidth : 1,
					isFormField : true,
					labelWidth : leftlabel
				},
				items : [
						{
							xtype : 'hidden',
							name : 'slSmallloanProject.projectId'
						},
						{
							xtype : 'hidden',
							name : 'slSmallloanProject.companyId'
						},
						{
							xtype : 'hidden',
							name : 'slSmallloanProject.businessType'
						},
						{
							xtype : 'hidden',
							name : 'slSmallloanProject.operationType'
						},
						{
							xtype : 'hidden',
							name : 'slSmallloanProject.flowType'
						},
						{
							xtype : 'hidden',
							name : 'slSmallloanProject.mineTypeKey'
						},
						{
							xtype : 'hidden',
							name : 'slSmallloanProject.loanLimit'
						},
						{
							columnWidth : 0.6, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : 80,
							items : [{
										fieldLabel : "项目名称",
										xtype : "textfield",
										readOnly : true,
										name : "slSmallloanProject.projectName",
										blankText : "项目名称不能为空，请正确填写!",
										// allowBlank : false,
										anchor : "99%"// 控制文本框的长度

									}]
						},
						{
							columnWidth : .4, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : 80,
							labelAlign : 'right',
							items : [{
										fieldLabel : "项目编号",
										xtype : "textfield",
										name : "slSmallloanProject.projectNumber",
										// allowBlank : false,
										readOnly : true,
										blankText : "项目编号不能为空，请正确填写!",
										anchor : "95%"

									}]
						},
						new ExtUD.Ext.TypeSelectInfoThreeGradesReadOnlyPanel(),/*
																				 * new
																				 * ExtUD.Ext.TypeSelectInfoTwoGradesReadOnlyPanel()
																				 * ，new
																				 * ExtUD.Ext.TypeSelectInfoThreeGradesReadOnlyPanel(),new
																				 * ExtUD.Ext.TypeSelectInfoFourGradesReadOnlyPanel()这里用不同的级别分类填充
																				 */
						/*
						 * new
						 * ExtUD.Ext.TypeSelectInfoMineType({isAllReadOnly:this.isAllReadOnly}),
						 *//*
							 * { columnWidth : .3, // 该列在整行中所占的百分比 layout :
							 * "form", // 从上往下的布局 labelWidth : 90, labelAlign :
							 * 'right', defaults : { anchor : '100%' }, items : [{
							 * xtype : "textfield", anchor : "100%", readOnly :
							 * true, name : "businessTypeKey", fieldLabel :
							 * "业务类别" }] }, { columnWidth : .3, // 该列在整行中所占的百分比
							 * layout : "form", // 从上往下的布局 labelWidth : 80,
							 * labelAlign : 'right', defaults : { anchor :
							 * '100%' }, items : [{ fieldLabel : "业务品种", xtype :
							 * "textfield", name : "operationTypeKey", readOnly
							 * :true }] }, { columnWidth : .4, // 该列在整行中所占的百分比
							 * layout : "form", // 从上往下的布局 labelWidth : 80,
							 * labelAlign : 'right', items : [{ fieldLabel :
							 * "流程类别", xtype : "textfield", name :
							 * "flowTypeKey", readOnly : true, anchor : "100%" }] }, {
							 * columnWidth : .3, // 该列在整行中所占的百分比 layout :
							 * "form", // 从上往下的布局 labelWidth : 90, labelAlign :
							 * 'right', hidden : true, items : [{ fieldLabel :
							 * "我方主体类型", xtype : "textfield", name :
							 * "mineTypeKey", readOnly : true, anchor : "100%" }] }, {
							 * columnWidth : .3, // 该列在整行中所占的百分比 labelWidth :90,
							 * layout : "form", // 从上往下的布局 items : [{ fieldLabel :
							 * '我方主体名称', xtype : 'textfield', name : 'mineName',
							 * anchor : "100%", readOnly : true }] }, {
							 * columnWidth : .4, // 该列在整行中所占的百分比 labelWidth :
							 * 80, layout : "form", // 从上往下的布局 items :
							 * [{fieldLabel : "项目经理", xtype : "textfield", name :
							 * "slSmallloanProject.appUsers", readOnly : true,
							 * anchor : "100%" }, { xtype : "hidden", name :
							 * 'slSmallloanProject.appUserId' }] },
							 *//*
							 * { columnWidth : .3, // 该列在整行中所占的百分比 layout :
							 * "form", // 从上往下的布局 labelWidth : leftlabel+50,
							 * items : [{ fieldLabel : "贷款利息收取单位", xtype :
							 * "textfield", name : "mineTypeKey", readOnly :
							 * this.isAllReadOnly, anchor : "98%" }] }, {
							 * columnWidth : .3, // 该列在整行中所占的百分比 labelWidth :10,
							 * layout : "form", // 从上往下的布局 items : [{ xtype :
							 * "textfield", name :
							 * "slSmallloanProject.mineName", //fieldLabel :
							 * "我方主体(贷款利息)", readOnly : this.isAllReadOnly,
							 * anchor : "98%" }] },
							 */{
							columnWidth : .3, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : leftlabel + 50,
							items : [{
								xtype : "dicIndepCombo",
								fieldLabel : "贷款利息收取单位",
								emptyText : "请选择",
								hiddenName : 'slSmallloanProject.mineType',
								nodeKey : 'ourmainType',
								readOnly : this.isDiligenceReadOnly,
								// allowBlank : false,
								value : null,
								anchor : "98%",
								scope : this,
								listeners : {
									change : function(combox, record, index) {
										var comboxValue = combox.getValue();
										var url = '';
										var store = null;
										var combo = combox.ownerCt
												.nextSibling().get(0);
										combo.clearValue();
										if (comboxValue == "company_ourmain")// 企业
										{
											url = __ctxPath
													+ '/creditFlow/ourmain/listSlCompanyMain.do';
											store = new Ext.data.Store({
												proxy : new Ext.data.HttpProxy(
														{
															url : url
														}),
												reader : new Ext.data.JsonReader(
														{
															root : 'result'
														}, [{
																	name : 'itemName',
																	mapping : 'corName'
																}, {
																	name : 'itemValue',
																	mapping : 'companyMainId'
																}])
											})
										} else if (comboxValue == "person_ourmain") // 个人
										{
											url = __ctxPath
													+ '/creditFlow/ourmain/listSlPersonMain.do';
											store = new Ext.data.Store({
												proxy : new Ext.data.HttpProxy(
														{
															url : url
														}),
												reader : new Ext.data.JsonReader(
														{
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
									},
									afterrender : function(combox) {
										var st = combox.getStore();
										st.on("load", function() {
													var v = combox.getValue();

													combox.setValue(v);
												})
										combox.clearInvalid();
									}
								}
							}]
						}, {
							columnWidth : .3, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : 10,
							items : [this.isDiligenceReadOnly ? {
								xtype : "textfield",
								name : "mineName",
								readOnly : this.isAllReadOnly,
								readOnly : this.isDiligenceReadOnly,
								anchor : "98%"

							} : {
								xtype : "combo",
								anchor : "98%",
								// fieldLabel : "我方主体(咨询管理费)",
								store : new Ext.data.SimpleStore({
											fields : ['displayText', 'value'],
											data : [['地区', 1]]
										}),
								emptyText : "请选择",
								id : "slsmallmineid",
								hiddenName : 'slSmallloanProject.mineId',
								// allowBlank : false,
								displayField : 'itemName',
								typeAhead : true,
								readOnly : this.isDiligenceReadOnly,
								mode : 'local',
								value : null,
								selectOnFocus : true,
								triggerAction : 'all',
								blankText : "我方主体不能为空，请正确填写!",
								listeners : {
									afterrender : function(combox) {
										var st = combox.getStore();
										st.on("load", function() {
													var v = combox.getValue();
													combox.setValue(v);
												})
									}
								}

							}]
						}, {
							columnWidth : .4, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : 80,
							items : [{
								xtype : "combo",
								triggerClass : 'x-form-search-trigger',
								hiddenName : "degree",
								editable : false,
								fieldLabel : "项目经理",
								blankText : "项目经理不能为空，请正确填写!",
								allowBlank : false,
								readOnly : this.isAllReadOnly,
								anchor : "95%",
								onTriggerClick : function(cc) {
									var obj = this;
									var appuerIdObj = obj.nextSibling();
									var userIds = appuerIdObj.getValue();
									if ("" == obj.getValue()) {
										userIds = "";
									}
									new UserDialog({
												userIds : userIds,
												userName : obj.getValue(),
												single : false,
												title : "选择项目经理",
												callback : function(uId, uname) {
													obj.setValue(uId);
													obj.setRawValue(uname);
													appuerIdObj.setValue(uId);
												}
											}).show();
								}
							}, {
								xtype : "hidden",
								value : ""
							}]
						}, {
							columnWidth : .3, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : leftlabel + 50,
							items : [{
								xtype : "dicIndepCombo",
								fieldLabel : "咨询管理费收取单位",
								emptyText : "请选择",
								hiddenName : 'slSmallloanProject.managementConsultingMineType',
								nodeKey : 'ourmainType',
								readOnly : this.isDiligenceReadOnly,
								// allowBlank : false,
								value : null,
								anchor : "98%",
								scope : this,
								listeners : {
									change : function(combox, record, index) {
										var comboxValue = combox.getValue();
										var url = '';
										var store = null;
										var combo = combox.ownerCt
												.nextSibling().get(0);
										combo.clearValue();
										if (comboxValue == "company_ourmain")// 企业
										{
											url = __ctxPath
													+ '/creditFlow/ourmain/listSlCompanyMain.do';
											store = new Ext.data.Store({
												proxy : new Ext.data.HttpProxy(
														{
															url : url
														}),
												reader : new Ext.data.JsonReader(
														{
															root : 'result'
														}, [{
																	name : 'itemName',
																	mapping : 'corName'
																}, {
																	name : 'itemValue',
																	mapping : 'companyMainId'
																}])
											})
										} else if (comboxValue == "person_ourmain") // 个人
										{
											url = __ctxPath
													+ '/creditFlow/ourmain/listSlPersonMain.do';
											store = new Ext.data.Store({
												proxy : new Ext.data.HttpProxy(
														{
															url : url
														}),
												reader : new Ext.data.JsonReader(
														{
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
									},
									afterrender : function(combox) {
										var st = combox.getStore();
										st.on("load", function() {
													var v = combox.getValue();

													combox.setValue(v);
												})
										combox.clearInvalid();
									}
								}
							}]
						}, {
							columnWidth : .3, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : 10,
							items : [this.isDiligenceReadOnly ? {
								xtype : "textfield",
								name : "managementConsultingMineName",
								readOnly : this.isAllReadOnly,
								readOnly : this.isDiligenceReadOnly,
								anchor : "98%"

							} : {
								xtype : "combo",
								anchor : "98%",
								// fieldLabel : "我方主体(咨询管理费)",
								store : new Ext.data.SimpleStore({
											fields : ['displayText', 'value'],
											data : [['地区', 1]]
										}),
								emptyText : "请选择",
								hiddenName : 'slSmallloanProject.managementConsultingMineId',
								// allowBlank : false,
								displayField : 'itemName',
								typeAhead : true,
								readOnly : this.isDiligenceReadOnly,
								mode : 'local',
								value : null,
								selectOnFocus : true,
								triggerAction : 'all',
								blankText : "我方主体不能为空，请正确填写!",
								listeners : {
									afterrender : function(combox) {
										var st = combox.getStore();
										st.on("load", function() {
													var v = combox.getValue();
													combox.setValue(v);
												})
									}
								}

							}]
						}, {
							columnWidth : .4, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : 80,
							labelAlign : 'right',
							items : [{
								fieldLabel : "贷款用途",
								xtype : "dickeycombo",
								hiddenName : 'slSmallloanProject.purposeType',
								displayField : 'itemName',
								readOnly : this.isAllReadOnly,
								itemName : '贷款用途',
								nodeKey : 'smallloan_purposeType',
								emptyText : "请选择",
								editable : false,
								anchor : "95%",
								allowBlank : false,
								listeners : {
									afterrender : function(combox) {
										var st = combox.getStore();
										st.on("load", function() {

													combox.setValue(combox
															.getValue());
													combox.clearInvalid();
												})
									}

								}
							}]
						}, {
							columnWidth : .3, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : leftlabel + 50,
							items : [{
								xtype : "dicIndepCombo",
								fieldLabel : "财务服务费收取单位",
								emptyText : "请选择",
								hiddenName : 'slSmallloanProject.financeServiceMineType',
								nodeKey : 'ourmainType',
								// allowBlank : false,
								value : null,
								anchor : "98%",
								readOnly : this.isDiligenceReadOnly,
								scope : this,
								listeners : {
									change : function(combox, record, index) {
										var comboxValue = combox.getValue();
										var url = '';
										var store = null;
										var combo = combox.ownerCt
												.nextSibling().get(0);
										combo.clearValue();
										if (comboxValue == "company_ourmain")// 企业
										{
											url = __ctxPath
													+ '/creditFlow/ourmain/listSlCompanyMain.do';
											store = new Ext.data.Store({
												proxy : new Ext.data.HttpProxy(
														{
															url : url
														}),
												reader : new Ext.data.JsonReader(
														{
															root : 'result'
														}, [{
																	name : 'itemName',
																	mapping : 'corName'
																}, {
																	name : 'itemValue',
																	mapping : 'companyMainId'
																}])
											})
										} else if (comboxValue == "person_ourmain") // 个人
										{
											url = __ctxPath
													+ '/creditFlow/ourmain/listSlPersonMain.do';
											store = new Ext.data.Store({
												proxy : new Ext.data.HttpProxy(
														{
															url : url
														}),
												reader : new Ext.data.JsonReader(
														{
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
									},
									afterrender : function(combox) {
										var st = combox.getStore();
										st.on("load", function() {
													var v = combox.getValue();
													combox.setValue(v);
												})
										combox.clearInvalid();
									}
								}
							}]
						}, {
							columnWidth : .3, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : 10,
							items : [this.isDiligenceReadOnly ? {
								xtype : "textfield",
								name : "financeServiceMineName",
								readOnly : this.isDiligenceReadOnly,
								readOnly : this.isDiligenceReadOnly,
								anchor : "98%"

							} : {
								xtype : "combo",
								anchor : "98%",
								// fieldLabel : "我方主体(财务服务费)",
								store : new Ext.data.SimpleStore({
											fields : ['displayText', 'value'],
											data : [['地区', 1]]
										}),
								emptyText : "请选择",
								// allowBlank : false,
								displayField : 'itemName',
								typeAhead : true,
								mode : 'local',
								readOnly : this.isDiligenceReadOnly,
								hiddenName : 'slSmallloanProject.financeServiceMineId',
								name : 'slSmallloanProject.financeServiceMineId',
								value : null,
								selectOnFocus : true,
								triggerAction : 'all',
								blankText : "我方主体不能为空，请正确填写!",
								listeners : {
									afterrender : function(combox) {
										combox.clearInvalid();
									}
								}

							}]
						}, {
							columnWidth : .4, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : 80,
							labelAlign : 'right',
							items : [{
								fieldLabel : "主担保方式",
								xtype : "dickeycombo",
								hiddenName : 'slSmallloanProject.assuretypeid',
								displayField : 'itemName',
								readOnly : this.isAllReadOnly,
								nodeKey : 'zdbfs',
								emptyText : "请选择",
								editable : false,
								anchor : "95%",
								allowBlank : false,
								listeners : {
									afterrender : function(combox) {
										var st = combox.getStore();
										st.on("load", function() {
													combox.setValue(combox
															.getValue());
													combox.clearInvalid();
												})
									}

								}
							}]

						}, {

							columnWidth : .3, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : 80,
							labelAlign : 'right',
							items : [{
								fieldLabel : "产品类型",
								xtype : "dickeycombo",
								hiddenName : 'slSmallloanProject.productTypeId',
								displayField : 'itemName',
								readOnly : this.isAllReadOnly,
								nodeKey : 'ProductType',
								emptyText : "请选择",
								editable : false,
								anchor : "98%",
								listeners : {
									afterrender : function(combox) {
										var st = combox.getStore();
										st.on("load", function() {
													combox.setValue(combox
															.getValue());
													combox.clearInvalid();
												})
									}

								}
							}]

						}, {
							columnWidth : .3, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : 90,
							labelAlign : 'right',
							items : [{
								fieldLabel : "项目来源",
								xtype : "dickeycombo",
								hiddenName : 'slSmallloanProject.customerChannel',
								displayField : 'itemName',
								readOnly : this.isAllReadOnly,
								nodeKey : 'customer_channel',
								emptyText : "请选择",
								editable : false,
								anchor : "98%",
								listeners : {
									afterrender : function(combox) {
										var st = combox.getStore();
										st.on("load", function() {
													combox.setValue(combox
															.getValue());
													combox.clearInvalid();
												})
									},
									"change" : function(combox, record, index) {
										alert("x");
									}
						
								}
							}]

						}, {
							columnWidth : .2, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : 80,
							labelAlign : 'right',
							items : [{
										fieldLabel : "推荐人",
										xtype : "textfield",
										name : "slSmallloanProject.recommendUser",
										readOnly : this.isAllReadOnly,
										anchor : "95%"

									}]

						}, {
							columnWidth : .2,
							layout : 'form',
							labelWidth : 60,
							labelAlign : 'right',
							items : [{
								fieldLabel : "项目属性",
								xtype : "dicIndepCombo",
								hiddenName : 'slSmallloanProject.projectProperties',
								displayField : 'itemName',
								readOnly : this.isAllReadOnly,
								nodeKey : 'projectProperties',
								emptyText : "请选择",
								editable : false,
								anchor : "90%",
								listeners : {
									afterrender : function(combox) {
										var st = combox.getStore();
										st.on("load", function() {
													combox.setValue(combox
															.getValue());
													combox.clearInvalid();
												})
									}

								}
							}]
						}]

			}]
		});
	},
	initComponents : function() {
	},
	cc : function() {

		// alert('ddd');
	}
});