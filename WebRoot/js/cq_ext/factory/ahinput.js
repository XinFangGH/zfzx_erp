CQProject = Ext.extend(Ext.Panel, {
	name : "baseinfo",
	layout : "form",
	autoHeight : true,
	labelAlign : 'right',
	isAllReadOnly : false,
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		var leftlabel = 100;
		var centerlabel = 100;
		var rightlabel = 85;
		CQProject.superclass.constructor.call(this, {
					reader : this.getReader(),
					items : this.getItems()
				});
	},
	getReader : function() {
		var reader = new Ext.data.JsonReader({
					root : 'data',
					fields : [{
								name : 'project.id'
							}, {
								name : 'project.projectId'
							}, {
								name : 'project.ctype'
							}, {
								name : 'project.cid'
							}, {
								name : 'project.companyId'
							}, {
								name : 'project.projectName',
								mapping : 'project.projectName'
							}, {
								name : 'project.projectNumber',
								mapping : 'project.projectNumber'
							}, {
								name : 'project.businessType',
								mapping : 'project.businessType'
							}, {
								name : 'project.operationType',
								mapping : 'project.operationType'
							}, {
								name : 'project.flowType',
								mapping : 'project.flowType'
							},
							// 3
							{
								name : 'baseinfo.mineName',
								mapping : 'baseinfo.mineName'
							}, {
								name : 'baseinfo.mineType',
								mapping : 'baseinfo.mineType'
							}, {
								name : 'baseinfo.appUserName',
								mapping : 'baseinfo.appUserName'
							}// 4
							, {
								name : 'baseinfo.managementConsultingMineType',
								mapping : 'baseinfo.managementConsultingMineType'
							}, {
								name : 'baseinfo.managementConsultingMineName',
								mapping : 'baseinfo.managementConsultingMineName'
							}, {
								name : 'baseinfo.purposeName',
								mapping : 'baseinfo.purposeName'
							}
							// 5
							, {
								name : 'baseinfo.financeServiceMineType',
								mapping : 'baseinfo.financeServiceMineType'
							}, {
								name : 'baseinfo.financeServiceMineName',
								mapping : 'baseinfo.financeServiceMineName'
							}, {
								name : 'baseinfo.assuretype',
								mapping : 'baseinfo.assuretype'
							}// 6
							, {
								name : 'baseinfo.productType',
								mapping : 'baseinfo.productType'
							}, {
								name : 'baseinfo.customerChannel',
								mapping : 'baseinfo.customerChannel'
							}, {
								name : 'baseinfo.recommendUser',
								mapping : 'baseinfo.recommendUser'
							}, {
								name : 'baseinfo.projectProperties',
								mapping : 'baseinfo.projectProperties'
							}, {
								name : 'baseinfo.id'
							}, {
								name : 'baseinfo.projectId'
							}]
				});
		return reader;
	},
	getItems : function() {
		var leftlabel = this.leftlabel;
		var money1 = {
			xtype : "dicIndepCombo",
			fieldLabel : "贷款利息收取单位",
			readOnly : this.isDiligenceReadOnly,
			emptyText : "请选择",
			hiddenName : 'baseinfo.mineType',
			nodeKey : 'ourmainType',
			value : null,
			anchor : "98%",
			editable : false,
			scope : this,
			listeners : {
				select : function(combox, record, index) {
					var comboxValue = combox.getValue();
					var url = '';
					var store = null;
					var combo = combox.ownerCt.nextSibling().get(1);
					combo.clearValue();
					if (comboxValue == "company_ourmain")// 企业
					{
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
					} else if (comboxValue == "person_ourmain") // 个人
					{
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
					combo.valueField = "itemName";
					store.load();
					if (combo.view) { // 刷新视图,避免视图值与实际值不相符
						combo.view.setStore(combo.store);
					}
				}
			}
		};

		var money2 = {
			xtype : "dicIndepCombo",
			fieldLabel : "咨询管理费收取单位",
			labelWidth : 150,
			emptyText : "请选择",
			editable : false,
			readOnly : this.isDiligenceReadOnly,
			hiddenName : 'baseinfo.managementConsultingMineType',
			nodeKey : 'ourmainType',
			value : null,
			anchor : "98%",
			scope : this,
			listeners : {
				change : function(combox, record, index) {
					var comboxValue = combox.getValue();
					var url = '';
					var store = null;
					var combo = combox.ownerCt.nextSibling().get(2);
					combo.clearValue();
					if (comboxValue == "company_ourmain")// 企业
					{
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
					} else if (comboxValue == "person_ourmain") // 个人
					{
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
					combo.valueField = "itemName";
					store.load();
					if (combo.view) { // 刷新视图,避免视图值与实际值不相符
						combo.view.setStore(combo.store);
					}
				}
			}
		};

		var money3 = {
			editable : false,
			xtype : "dicIndepCombo",
			fieldLabel : "财务服务费收取单位",
			emptyText : "请选择",
			hiddenName : 'baseinfo.financeServiceMineType',
			nodeKey : 'ourmainType',
			value : null,
			anchor : "98%",
			readOnly : this.isDiligenceReadOnly,
			scope : this,
			listeners : {
				change : function(combox, record, index) {
					var comboxValue = combox.getValue();
					var url = '';
					var store = null;
					var combo = combox.ownerCt.nextSibling().get(3);
					combo.clearValue();
					if (comboxValue == "company_ourmain")// 企业
					{
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
					} else if (comboxValue == "person_ourmain") // 个人
					{
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
					combo.valueField = "itemName";
					store.load();
					if (combo.view) { // 刷新视图,避免视图值与实际值不相符
						combo.view.setStore(combo.store);
					}
				}
			}
		};
		var moneyName3 = {
			editable : false,
			xtype : "combo",
			anchor : "98%",
			hideLabel : 1,
			readOnly : this.isDiligenceReadOnly,
			store : new Ext.data.SimpleStore({
						fields : ['displayText', 'value'],
						data : [['地区', 1]]
					}),
			emptyText : "请选择",
			displayField : 'itemName',
			typeAhead : true,
			mode : 'local',
			hiddenName : 'baseinfo.financeServiceMineName',
			name : 'baseinfo.financeServiceMineName',
			value : null,
			selectOnFocus : true,
			triggerAction : 'all',
			blankText : "我方主体不能为空，请正确填写!"

		};

		var combo3 = {
			readOnly : this.isDiligenceReadOnly,
			isDisplayItemName : true,
			fieldLabel : "主担保方式",
			xtype : "dickeycombo",
			hiddenName : 'baseinfo.assuretype',
			displayField : 'itemName',
			nodeKey : 'zdbfs',
			emptyText : "请选择",
			editable : false,
			anchor : "95%",
			allowBlank : false
		};
		var column1 = {
			columnWidth : .3,
			layout : 'form',
			labelWidth : 150,
			items : [{
						xtype : "textfield",
						fieldLabel : "业务类别",
						anchor : "100%",
						readOnly : true,
						name : "project.businessType"
					}, money1, money2, money3]
		};

		var moneyName1 = {
			xtype : "combo",
			anchor : "98%",
			editable : false,
			hideLabel : 1,
			store : new Ext.data.SimpleStore({
						fields : ['displayText', 'value'],
						data : [['地区', 1]]
					}),
			emptyText : "请选择",
			hiddenName : 'baseinfo.mineName',
			displayField : 'itemName',
			typeAhead : true,
			readOnly : this.isDiligenceReadOnly,
			mode : 'local',
			value : null,
			selectOnFocus : true,
			triggerAction : 'all',
			blankText : "我方主体不能为空，请正确填写!"
		};
		var moneyName2 = {
			readOnly : this.isDiligenceReadOnly,
			editable : false,
			hideLabel : 1,
			xtype : "combo",
			anchor : "98%",
			store : new Ext.data.SimpleStore({
						fields : ['displayText', 'value'],
						data : [['地区', 1]]
					}),
			emptyText : "请选择",
			hiddenName : 'baseinfo.managementConsultingMineName',
			displayField : 'itemName',
			typeAhead : true,
			readOnly : this.isDiligenceReadOnly,
			mode : 'local',
			value : null,
			selectOnFocus : true,
			triggerAction : 'all',
			blankText : "我方主体不能为空，请正确填写!"

		};
		var column2 = {
			columnWidth : .3,
			layout : 'form',
			items : [{
						xtype : "textfield",
						fieldLabel : "业务品种",
						anchor : "98.5%",
						readOnly : true,
						name : "project.operationType"
					}, moneyName1, moneyName2, moneyName3]
		};

		var combo2 = {
			isDisplayItemName : true,
			fieldLabel : "贷款用途",
			xtype : "dickeycombo",
			hiddenName : 'baseinfo.purposeName',
			displayField : 'itemName',
			readOnly : this.isDiligenceReadOnly,
			itemName : '贷款用途',
			nodeKey : 'smallloan_purposeType',
			emptyText : "请选择",
			editable : false,
			anchor : "95%",
			allowBlank : false
		};
		var combo1 = [{
					xtype : "combo",
					readOnly : this.isDiligenceReadOnly,
					triggerClass : 'x-form-search-trigger',
					hiddenName : "baseinfo.appUserName",
					editable : false,
					fieldLabel : "项目经理",
					blankText : "项目经理不能为空，请正确填写!",
					allowBlank : false,
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
										obj.setValue(uname);
										obj.setRawValue(uname);
										appuerIdObj.setValue(uname);
									}
								}).show();
					}
				}, {
					xtype : "hidden",
					value : ""
				}];
		var column3 = {
			columnWidth : .4,
			layout : 'form',
			items : [{
						xtype : "textfield",
						fieldLabel : "流程类别",
						name : "project.flowType",
						readOnly : true,
						anchor : "99%"
					}, combo1, combo2, combo3]
		};
		var _items = {

			layout : "column",
			border : false,
			scope : this,
			defaults : {
				anchor : '100%',
				columnWidth : 1,
				isFormField : true,
				labelWidth : leftlabel
			},
			items : [{
						xtype : "hidden",
						name : 'project.id'
					}, {
						xtype : "hidden",
						name : 'project.projectId'
					}, {
						xtype : "hidden",
						name : 'project.ctype'
					}, {
						xtype : "hidden",
						name : 'project.cid'
					}, {
						xtype : "hidden",
						name : 'project.companyId'
					}, {
						xtype : "hidden",
						name : "baseinfo.id"
					}, {
						xtype : "hidden",
						name : "baseinfo.projectId"
					}, {
						columnWidth : 0.6, // 该列在整行中所占的百分比
						layout : "form", // 从上往下的布局
						labelWidth : 80,
						items : [{
									fieldLabel : "项目名称",
									xtype : "textfield",
									readOnly : true,
									name : "project.projectName",
									blankText : "项目名称不能为空，请正确填写!",
									anchor : "99%"// 控制文本框的长度

								}]
					}, {
						columnWidth : .4, // 该列在整行中所占的百分比
						layout : "form", // 从上往下的布局
						labelWidth : 80,
						labelAlign : 'right',
						items : [{
									fieldLabel : "项目编号",
									xtype : "textfield",
									name : "project.projectNumber",
									readOnly : true,
									blankText : "项目编号不能为空，请正确填写!",
									anchor : "95%"

								}]
					},

					{
						layout : 'column',
						items : [column1, column2, column3]

					}, {

						columnWidth : .3, // 该列在整行中所占的百分比
						layout : "form", // 从上往下的布局
						labelWidth : 80,
						labelAlign : 'right',
						items : [{
									readOnly : this.isDiligenceReadOnly,
									fieldLabel : "产品类型",
									isDisplayItemName : true,
									xtype : "dickeycombo",
									hiddenName : 'baseinfo.productType',
									displayField : 'itemName',
									nodeKey : 'ProductType',
									emptyText : "请选择",
									editable : false,
									anchor : "98%"
								}]

					}, {
						columnWidth : .3, // 该列在整行中所占的百分比
						layout : "form", // 从上往下的布局
						labelWidth : 90,
						labelAlign : 'right',
						items : [{
									isDisplayItemName : true,
									fieldLabel : "项目来源",
									xtype : "dickeycombo",
									hiddenName : 'baseinfo.customerChannel',
									displayField : 'itemName',
									readOnly : this.isDiligenceReadOnly,
									nodeKey : 'customer_channel',
									emptyText : "请选择",
									editable : false,
									anchor : "98%"
								}]
					}, {
						columnWidth : .2, // 该列在整行中所占的百分比
						layout : "form", // 从上往下的布局
						labelWidth : 80,
						labelAlign : 'right',
						items : [{
									fieldLabel : "推荐人",
									xtype : "textfield",
									name : "baseinfo.recommendUser",
									readOnly : this.isDiligenceReadOnly,
									anchor : "95%"

								}]

					}, {
						columnWidth : .2,
						layout : 'form',
						labelWidth : 60,
						labelAlign : 'right',
						items : [{
									isDisplayItemName : true,
									fieldLabel : "项目属性",
									xtype : "dicIndepCombo",
									hiddenName : 'baseinfo.projectProperties',
									displayField : 'itemName',
									readOnly : this.isDiligenceReadOnly,
									nodeKey : 'projectProperties',
									emptyText : "请选择",
									editable : false,
									anchor : "90%"
								}]
					}]
		}
		return _items;

	}
});

EntForm = Ext.extend(Ext.FormPanel, {
	layout : "form",
	border : false,
	autoHeight : true,
	labelAlign : 'right',
	projectId : null,
	isAllReadOnly : true,
	bussinessType : null,
	isHideGudongInfo : false,
	isLoadShareequity : false,
	isHiddenCustomerDetailBtn : false,
	isEditEnterprise : false,
	isEnterpriseNameReadOnly : true,
	isGudongDiseditable : true,// 高庆瑞新加,与isAllReadOnly相与，不影响之前配置的
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		var leftlabel = 100;
		var rightlabel = 90
		EntForm.superclass.constructor.call(this, {
			reader : new Ext.data.JsonReader({
						root : 'data',
						fields : [{
									name : 'enterprise.id'
								}]
					}),
			items : [{
				layout : "column", // 定义该元素为布局为列布局方式
				defaults : {
					anchor : '100%',
					columnWidth : 1,
					isFormField : true
				},
				border : false,
				scope : this,
				items : [{
					bodyStyle : 'padding-right:10px',
					columnWidth : .6, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					items : [{
						xtype : 'fieldset',
						title : '基本信息',
						layout : "column",
						defaults : {
							anchor : '100%',
							columnWidth : 1,
							isFormField : true,
							labelWidth : leftlabel
						},
						items : [{
							columnWidth : 1, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : leftlabel,
							items : [{
										xtype : 'hidden',
										name : 'enterprise.id',
										value : 0
									}, {
										xtype : 'combo',
										anchor : '100%',
										triggerClass : 'x-form-search-trigger',
										fieldLabel : "企业名称",
										name : "enterprise.enterprisename",
										readOnly : this.isEnterpriseNameReadOnly,
										blankText : "企业名称不能为空，请正确填写!",
										allowBlank : false,
										scope : this,
										onTriggerClick : function() {
											ressetProjuect(Ext
													.getCmp('createNewSLFunctionForm'));
											var op = this.ownerCt.ownerCt.ownerCt.ownerCt;
											var EnterpriseNameStockUpdateNew = function(
													obj) {
												op
														.getCmpByName('enterprise.enterprisename')
														.setValue("");
												op
														.getCmpByName('enterprise.id')
														.setValue("");
												op
														.getCmpByName('enterprise.shortname')
														.setValue("");
												op
														.getCmpByName('enterprise.area')
														.setValue("");
												op
														.getCmpByName('enterprise.cciaa')
														.setValue("");
												op
														.getCmpByName('enterprise.organizecode')
														.setValue("");
												op
														.getCmpByName('enterprise.telephone')
														.setValue("");
												op
														.getCmpByName('enterprise.postcoding')
														.setValue("");
												op
														.getCmpByName('enterprise.hangyeType')
														.setValue("");
												op
														.getCmpByName('enterprise.hangyeName')
														.setValue("");
												op.getCmpByName('person.id')
														.setValue("");
												op.getCmpByName('person.name')
														.setValue("");
												op.getCmpByName('person.sex')
														.setValue("");
												op
														.getCmpByName('person.cardtype')
														.setValue("");
												op
														.getCmpByName('person.cardnumber')
														.setValue("");
												op
														.getCmpByName('person.cellphone')
														.setValue("");
												op
														.getCmpByName('person.selfemail')
														.setValue("");

												if (obj.enterprisename != 0
														&& obj.enterprisename != "")
													op
															.getCmpByName('enterprise.enterprisename')
															.setValue(obj.enterprisename);
												if (obj.id != 0 && obj.id != "")
													op
															.getCmpByName('enterprise.id')
															.setValue(obj.id);
												if (obj.shortname != 0
														&& obj.shortname != "")
													op
															.getCmpByName('enterprise.shortname')
															.setValue(obj.shortname);
												if (obj.area != 0
														&& obj.area != "")
													op
															.getCmpByName('enterprise.area')
															.setValue(obj.area);
												if (obj.cciaa != 0
														&& obj.cciaa != "")
													op
															.getCmpByName('enterprise.cciaa')
															.setValue(obj.cciaa);
												if (obj.organizecode != 0
														&& obj.organizecode != "")
													op
															.getCmpByName('enterprise.organizecode')
															.setValue(obj.organizecode);
												if (obj.telephone != 0
														&& obj.telephone != "")
													op
															.getCmpByName('enterprise.telephone')
															.setValue(obj.telephone);
												if (obj.postcoding != 0
														&& obj.postcoding != "")
													op
															.getCmpByName('enterprise.postcoding')
															.setValue(obj.postcoding);
												if (obj.hangyetype != 0
														&& obj.hangyetype != "") {
													op
															.getCmpByName('enterprise.hangyeType')
															.setValue(obj.hangyetype);
													op
															.getCmpByName('enterprise.hangyeName')
															.setValue(obj.hangyetypevalue);
												}

												Ext.Ajax.request({
													url : __ctxPath
															+ '/creditFlow/customer/person/seeInfoPerson.do',
													method : "post",
													params : {
														id : obj.legalpersonid
													},
													success : function(d) {

														var c = Ext.util.JSON
																.decode(d.responseText);
														var id = c.data.id;
														var name = c.data.name;
														var sex = c.data.sex;
														var cardtype = c.data.cardtype;
														var cardnumber = c.data.cardnumber;
														var cellphone = c.data.cellphone;
														var selfemail = c.data.selfemail;
														if (id != 0 && id != "")
															op
																	.getCmpByName('person.id')
																	.setValue(id);
														if (name != 0
																&& name != "")
															op
																	.getCmpByName('person.name')
																	.setValue(name);
														if (sex != 0
																&& sex != "")
															op
																	.getCmpByName('person.sex')
																	.setValue(sex);
														if (cardtype != 0
																&& cardtype != "")
															op
																	.getCmpByName('person.cardtype')
																	.setValue(cardtype);
														if (cardnumber != 0
																&& cardnumber != "")
															op
																	.getCmpByName('person.cardnumber')
																	.setValue(cardnumber);
														if (cellphone != 0
																&& cellphone != "")
															op
																	.getCmpByName('person.cellphone')
																	.setValue(cellphone);
														if (selfemail != 0
																&& selfemail != "")
															op
																	.getCmpByName('person.selfemail')
																	.setValue(selfemail);
													}

												})
												var edGrid = Ext
														.getCmp('createNewSLFunctionForm')
														.getCmpByName('gudong_store')
														.get(0).get(1);
												var store = edGrid.getStore();
												var url = __ctxPath
														+ '/creditFlow/common/getShareequity.do?enterpriseId='
														+ obj.id;
												store.proxy.conn.url = url;
												store.load();
											}
											selectEnterprise(EnterpriseNameStockUpdateNew);

										},
										resizable : true,
										mode : 'romote',
										// editable : false,
										lazyInit : false,
										typeAhead : true,
										minChars : 1,
										/*
										 * store : new Ext.data.JsonStore({ url :
										 * __ctxPath +
										 * '/credit/customer/enterprise/ajaxQueryEnterpriseForCombo.do',
										 * root : 'topics', autoLoad : true,
										 * fields : [{ name : 'id' }, { name :
										 * 'enterprisename' }, { name :
										 * 'shortname' }, { name :
										 * 'hangyetypevalue' }, { name :
										 * 'hangyetype' }, { name :
										 * 'organizecode' }, { name : 'cciaa' }, {
										 * name : 'postcoding' }, { name :
										 * 'address' }, { name : 'telephone' }, {
										 * name : 'legalpersonid' }], listeners : {
										 * scope : this, 'load' : function(s, r,
										 * o) { if (s.getCount() == 0) { this
										 * .getCmpByName('enterprise.enterprisename')
										 * .markInvalid('没有查找到匹配的记录'); } } } }),
										 */
										/*
										 * displayField : 'enterprisename',
										 * valueField : 'id',
										 */
										triggerAction : 'all'
									}]
						}, {
							columnWidth : 0.5, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : leftlabel,
							items : [{
										xtype : "textfield",
										anchor : '100%',
										name : "enterprise.shortname",
										fieldLabel : "企业简称",
										readOnly : this.isAllReadOnly,
										blankText : "企业简称不能为空，请正确填写!"

									}]
						}, {
							columnWidth : 0.5, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : rightlabel,
							items : [{
								xtype : "combo",
								triggerClass : 'x-form-search-trigger',
								name : "enterprise.hangyeName",
								fieldLabel : "行业类别",
								anchor : '100%',
								scope : this,
								readOnly : this.isAllReadOnly,
								onTriggerClick : function(e) {
									var obj = this;
									var oobbj = obj.ownerCt.ownerCt
											.getCmpByName("enterprise.hangyeType");
									selectTradeCategory(obj, oobbj);
								}
							}]
								/*
								 * columnWidth : 0.5, // 该列在整行中所占的百分比 layout :
								 * "form", // 从上往下的布局 labelWidth : rightlabel,
								 * items : [{ xtype : "combo", triggerClass :
								 * 'x-form-search-trigger', name :
								 * "enterprise.hangyeName", fieldLabel : "行业类别",
								 * anchor : '100%', scope : this, allowBlank :
								 * false,//客户添加页面这个为必填项 blankText :
								 * "行业类别不能为空，请正确填写!", readOnly :
								 * this.isAllReadOnly, onTriggerClick :
								 * function(e) { var obj = this; var oobbj =
								 * obj.ownerCt.ownerCt.getCmpByName("enterprise.hangyeType");
								 * var rootId
								 * =obj.ownerCt.ownerCt.getCmpByName("enterprise.rootHangYeType");
								 * var selectHangYe = function(array){
								 * oobbj.setValue(array[0].id)
								 * obj.setValue(array[0].text);
								 * rootId.setValue(array[array.length-1].id) };
								 * selectDictionary('hangye',selectHangYe); } }]
								 */
						}, {
							columnWidth : 0.5, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : leftlabel,
							items : [{
								xtype : "textfield",
								name : "enterprise.organizecode",
								allowBlank : false,
								fieldLabel : "组织机构代码",
								regex : /^[A-Za-z0-9]{8}-[A-Za-z0-9]{1}/,
								regexText : '组织机构代码格式不正确',
								readOnly : this.isAllReadOnly,
								blankText : "组织机构代码不能为空，请正确填写!",
								anchor : "100%",
								listeners : {
									scope : this,
									'blur' : function(tf) {
										var organizecode = tf.getValue();
										var enterpriseId = this
												.getCmpByName("enterprise.id")
												.getValue();
										Ext.Ajax.request({
											url : __ctxPath
													+ '/credit/customer/enterprise/verificationOrganizecode.do',
											method : 'POST',
											params : {
												organizecode : organizecode,
												enterpriseId : enterpriseId
											},
											success : function(response,
													request) {

												var obj = Ext.util.JSON
														.decode(response.responseText);
												if (obj.msg = "false") {

													Ext.ux.Toast.msg('操作信息',
															"该组织机构代码已存在，请重新输入");
													tf.setValue("");
												}
											}
										});
									}
								}
							}]
						}, {
							xtype : "hidden",
							name : 'enterprise.hangyeType'
						}, {
							xtype : "hidden",
							name : 'enterprise.rootHangYeType'
						}, {
							columnWidth : 0.5, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : rightlabel,
							items : [{
										xtype : "textfield",
										name : "enterprise.cciaa",
										fieldLabel : "营业执照号码",
										readOnly : this.isAllReadOnly,
										blankText : "营业执照号码不能为空，请正确填写!",
										allowBlank : false,
										anchor : "100%"
									}]
						}, {
							columnWidth : 0.5, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : leftlabel,
							items : [{
								xtype : "textfield",
								name : "enterprise.telephone",
								allowBlank : false,
								fieldLabel : "联系电话",
								readOnly : this.isAllReadOnly,
								blankText : "联系电话不能为空，请正确填写!",
								regex : /((\d{11})|^((\d{7,8})|(\d{4}|\d{3})-(\d{7,8})|(\d{4}|\d{3})-(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1})|(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1}))$)/,
								regexText : '联系电话格式不正确',
								anchor : "100%"
							}]
						}, {
							columnWidth : 0.5, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : rightlabel,
							items : [{
										xtype : "textfield",
										allowBlank : false,
										name : "enterprise.postcoding",
										fieldLabel : "邮政编码",
										readOnly : this.isAllReadOnly,
										blankText : "邮政编码不能为空，请正确填写!",
										regex : /^[0-9]{6}$/,
										regexText : '邮政编码格式不正确',
										anchor : "100%"
									}]
						}, {
							columnWidth : 1, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : leftlabel,
							items : [{
										xtype : "textfield",
										fieldLabel : "通讯地址",
										readOnly : this.isAllReadOnly,
										allowBlank : false,
										name : "enterprise.area",
										anchor : '100%'
									}]
						}]
					}]

				}, {
					columnWidth : .4, // 该列在整行中所占的百分比
					bodyStyle : 'padding-right:2px',
					layout : "form", // 从上往下的布局
					items : [{
						layout : "form", // 从上往下的布局
						xtype : 'fieldset',
						title : '法定代表人信息',
						items : [{
							layout : "column",
							defaults : {
								anchor : '100%',
								columnWidth : 1,
								isFormField : true,
								labelWidth : 75
							},
							items : [/*
										 * { xtype : 'hidden', name :
										 * 'person.marry' },
										 */{
										xtype : 'hidden',
										name : 'person.id'
									}, {
										columnWidth : 1, // 该列在整行中所占的百分比
										layout : "form", // 从上往下的布局
										labelWidth : 110,
										items : [{
													xtype : "textfield",
													fieldLabel : "法定代表人姓名",
													name : "person.name",
													readOnly : this.isAllReadOnly,
													allowBlank : false,
													anchor : '100%',
													blankText : "法定代表人姓名不能为空，请正确填写!"
												}]
									}, {
										columnWidth : 0.5, // 该列在整行中所占的百分比
										layout : "form", // 从上往下的布局
										labelWidth : 110,
										items : [{
											fieldLabel : "法定代表人性别",
											xtype : "diccombo",
											hiddenName : 'person.sex',
											displayField : 'itemName',
											readOnly : this.isAllReadOnly,
											itemName : '性别', // xx代表分类名称
											allowBlank : false,
											// emptyText : "请选择",
											editable : false,
											blankText : "性别不能为空，请正确填写!",
											anchor : "100%",
											listeners : {
												afterrender : function(combox) {
													var st = combox.getStore();
													st.on("load", function() {
																combox
																		.setValue(combox
																				.getValue());
																combox
																		.clearInvalid();
															})
												}
											}
										}]
									}, {
										columnWidth : 0.5, // 该列在整行中所占的百分比
										layout : "form", // 从上往下的布局
										labelWidth : 65,
										items : [{
											xtype : "diccombo",
											hiddenName : "person.cardtype",
											fieldLabel : "证件类型",
											readOnly : this.isAllReadOnly,
											itemName : '证件类型', // xx代表分类名称
											allowBlank : false,
											editable : false,
											// emptyText : "请选择",
											blankText : "证件类型不能为空，请正确填写!",
											anchor : "100%",
											listeners : {
												scope : this,
												afterrender : function(combox) {
													var st = combox.getStore();
													st.on("load", function() {
														if (combox.getValue() == ''
																|| combox
																		.getValue() == null) {
															combox
																	.setValue(st
																			.getAt(0).data.itemId);
															combox
																	.clearInvalid();
														} else {
															combox
																	.setValue(combox
																			.getValue());
														}
													})
												}
											}
										}]
									}, {
										columnWidth : 1, // 该列在整行中所占的百分比
										layout : "form", // 从上往下的布局
										labelWidth : 110,
										items : [{
											xtype : "textfield",
											name : "person.cardnumber",
											allowBlank : false,
											fieldLabel : "证件号码",
											readOnly : this.isAllReadOnly,
											blankText : "证件号码不能为空，请正确填写!",
											anchor : "100%",
											listeners : {
												scope : this,
												'blur' : function(com) {
													if (this
															.getCmpByName('person.cardtype')
															.getValue() == 309) {
														if (validateIdCard(com
																.getValue()) == 1) {
															Ext.Msg
																	.alert(
																			'身份证号码验证',
																			'法定代表人证件号码不正确,请仔细核对')
															return;
														} else if (validateIdCard(com
																.getValue()) == 2) {
															Ext.Msg
																	.alert(
																			'身份证号码验证',
																			'法定代表人证件号码地区不正确,请仔细核对')
															return;
														} else if (validateIdCard(com
																.getValue()) == 3) {
															Ext.Msg
																	.alert(
																			'身份证号码验证',
																			'法定代表人证件号码生日日期不正确,请仔细核对')
															return;
														}
													}

												}
											}
										}]
									}, {
										columnWidth : this.isHiddenCustomerDetailBtn
												? 1
												: .5, // 该列在整行中所占的百分比
										layout : "form", // 从上往下的布局
										labelWidth : 110,
										items : [{
													xtype : "textfield",
													name : "person.cellphone",
													readOnly : this.isAllReadOnly,
													fieldLabel : "手机号码",
													anchor : "100%",
													regex : /^[1][34578][0-9]{9}$/,
													regexText : '手机号码格式不正确'
												}]
									}, {
										columnWidth : this.isHiddenCustomerDetailBtn
												? 1
												: .5, // 该列在整行中所占的百分比
										layout : "form", // 从上往下的布局
										labelWidth : this.isHiddenCustomerDetailBtn
												? 110
												: 65,
										items : [{
											xtype : "textfield",
											name : "person.selfemail",
											readOnly : this.isAllReadOnly,
											fieldLabel : "电子邮箱",
											anchor : "100%",
											regex : /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+/,
											regexText : '电子邮箱格式不正确',
											listeners : {/*
															 * afterrender :
															 * function(obj) {
															 * if
															 * (obj.getValue() == "" ||
															 * obj.getValue() ==
															 * null) {
															 * Ext.apply(obj, {
															 * vtype : "" });
															 * }else{
															 * Ext.apply(obj, {
															 * vtype : 'email'
															 * }); //obj.regex
															 * ="/^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/";
															 * //obj.regexText="'电子邮箱格式不正确'"; } },
															 * blur :
															 * function(obj) {
															 * 
															 * if
															 * (obj.getValue() == "" ||
															 * obj.getValue() ==
															 * null) {
															 * Ext.apply(obj, {
															 * vtype : "" }); }
															 * else {
															 * Ext.apply(obj, {
															 * vtype : 'email'
															 * }); } }
															 * 
															 */}
										}]
									}]
						}]
					}, {
						border : false,
						layout : "form",
						scope : this,
						items : [{
							xtype : 'button',
							text : this.isEditEnterprise ? '编辑客户资料' : '查看客户资料',
							iconCls : 'btn-customer',
							width : 110,
							hidden : this.isHiddenCustomerDetailBtn,
							scope : this,
							handler : function() {
								var oppositeId = this
										.getCmpByName('enterprise.id')
										.getValue();
								var flag = "detail";
								if (this.isEditEnterprise == true) {
									flag = "edit";
								}
								if (flag == "detail") {
									seeCustomer("company_customer", oppositeId);
								} else if (flag == "edit") {
									editCustomer("company_customer",
											oppositeId, this);
								}
							}
						}]
					}]
				}, {
					columnWidth : 1, // 该列在整行中所占的百分比
					bodyStyle : 'padding-right:2px',
					layout : "form", // 从上往下的布局
					hidden : this.isHidden,
					items : [{
						layout : "form", // 从上往下的布局
						xtype : 'fieldset',
						title : '贷款账户信息',
						items : [{
							layout : "column",
							defaults : {
								anchor : '100%',
								columnWidth : 1,
								isFormField : true,
								labelWidth : 75
							},
							items : [{
								columnWidth : 0.5, // 该列在整行中所占的百分比
								layout : "form", // 从上往下的布局
								labelWidth : 80,
								items : [{
									xtype : 'combo',
									mode : 'local',
									displayField : 'name',
									valueField : 'id',
									editable : false,
									anchor : "100%",
									store : new Ext.data.SimpleStore({
												fields : ["name", "id"],
												data : [["个人", "0"],
														["公司", "1"]]
											}),
									triggerAction : "all",
									hiddenName : "enterpriseBank.openType",
									fieldLabel : '开户类型',
									allowBlank : this.isHidden,
									readOnly : true,
									hidden : this.isHidden,
									hideLabel : this.isHidden,
									name : 'enterpriseBank.openType',
									listeners : {
										scope : this,
										afterrender : function(combox) {
											var st = combox.getStore();
											combox
													.setValue(st.getAt(1).data.id);
											combox.fireEvent("select", combox,
													st.getAt(1), 0);
										},
										select : function(combox, record, index) {
											var v = record.data.id;
											var obj = this
													.getCmpByName('enterpriseBank.accountType');
											obj.enable();
											obj.setValue();
											obj.store.removeAll()
											if (v == 0) {
												arrStore = new Ext.data.SimpleStore(
														{
															fields : ["name",
																	"id"],
															data : [
																	["个人储蓄户",
																			"0"],
																	["基本户", "1"],
																	["一般户", "2"]]
														});
												obj.store.insert(0, arrStore
																.getAt(0));
											} else {
												arrStore = new Ext.data.SimpleStore(
														{
															fields : ["name",
																	"id"],
															data : [
																	["个人储蓄户",
																			"0"],
																	["基本户", "1"],
																	["一般户", "2"]]
														});
												obj.store.insert(0, arrStore
																.getAt(1));
												obj.store.insert(1, arrStore
																.getAt(2));
											}
										}

									}
								}]
							}, {
								columnWidth : 0.5, // 该列在整行中所占的百分比
								layout : "form", // 从上往下的布局
								labelWidth : 80,
								items : [{
									xtype : 'combo',
									mode : 'local',
									displayField : 'name',
									valueField : 'id',
									editable : false,
									width : 70,
									triggerAction : "all",
									hiddenName : "enterpriseBank.accountType",
									fieldLabel : '账户类型',
									allowBlank : this.isHidden,
									readOnly : this.isReadOnly,
									hidden : this.isHidden,
									hideLabel : this.isHidden,
									store : new Ext.data.SimpleStore({
												fields : ["name", "id"],
												data : [["个人储蓄户", "0"],
														["基本户", "1"],
														["一般户", "2"]]
											}),
									name : 'enterpriseBank.accountType',
									anchor : "100%"
								}]
							}, {
								columnWidth : 0.5, // 该列在整行中所占的百分比
								layout : "form", // 从上往下的布局
								labelWidth : 80,
								items : [{
											fieldLabel : '开户名称',
											name : 'enterpriseBank.name',
											xtype : 'textfield',
											readOnly : this.isReadOnly,
											hidden : this.isHidden,
											hideLabel : this.isHidden,
											allowBlank : this.isHidden,
											anchor : "100%"
										}]
							}, {
								columnWidth : 0.5, // 该列在整行中所占的百分比
								layout : "form", // 从上往下的布局
								labelWidth : 80,
								items : [{
											xtype : 'textfield',
											fieldLabel : '账号',
											hidden : this.isHidden,
											hideLabel : this.isHidden,
											allowBlank : this.isHidden,
											name : 'enterpriseBank.accountnum',
											readOnly : this.isReadOnly,
											anchor : "100%"
										}]
							}, {
								columnWidth : .33,
								layout : 'form',
								labelWidth : 80,
								items : [{
											name : 'enterpriseBank.id',
											xtype : 'hidden'
										}, {
											fieldLabel : "银行名称",
											xtype : "combo",
											displayField : 'itemName',
											valueField : 'itemId',
											triggerAction : 'all',
											allowBlank : this.isHidden,
											readOnly : this.isReadOnly,
											hidden : this.isHidden,
											hideLabel : this.isHidden,
											store : new Ext.data.ArrayStore({
												url : __ctxPath
														+ '/creditFlow/common/getBankListCsBank.do',
												fields : ['itemId', 'itemName'],
												autoLoad : true
											}),
											mode : 'remote',
											hiddenName : "enterpriseBank.bankid",
											editable : false,
											blankText : "银行名称不能为空，请正确填写!",
											anchor : "100%",
											listeners : {
												scope : this,
												afterrender : function(combox) {
													var st = combox.getStore();
													st.on("load", function() {
																combox
																		.setValue(combox
																				.getValue());

															})
													combox.clearInvalid();
												}

											}

										}]
							}, {
								columnWidth : .34,
								layout : 'form',
								items : [{
										fieldLabel : "网点名称",
	                                    name : 'enterpriseBank.bankOutletsName',
									       xtype:'textfield',
									allowBlank : this.isHidden,
									readOnly : this.isReadOnly,
									hidden : this.isHidden,
									hideLabel : this.isHidden,
									anchor : "100%"
									

								}]
							}, {
								columnWidth : .33,
								layout : 'form',
								items : [{
											name : 'enterpriseBank.areaId',
											xtype : 'hidden'
										}, {
											// id:'bankName',
											name : 'enterpriseBank.areaName',
											hiddenName : 'enterpriseBank.areaName',
											fieldLabel : '开户地区',
											anchor : '100%',
											// value : '中国银行',
											// submitValue:false,
											xtype : 'trigger',
											triggerClass : 'x-form-search-trigger',
											editable : false,
											allowBlank : this.isHidden,
											readOnly : this.isReadOnly,
											hidden : this.isHidden,
											hideLabel : this.isHidden,
											scope : this,
											onTriggerClick : function() {
												var com = this
												var selectBankLinkMan = function(
														array) {
													var str = "";
													var idStr = ""
													for (var i = array.length
															- 1; i >= 0; i--) {
														str = str
																+ array[i].text
																+ "-"
														idStr = idStr
																+ array[i].id
																+ ","
													}
													if (str != "") {
														str = str.substring(0,
																str.length - 1);
													}
													if (idStr != "") {
														idStr = idStr
																.substring(
																		0,
																		idStr.length
																				- 1)
													}
													com.previousSibling()
															.setValue(idStr)
													com.setValue(str);
												};
												dictionaryNotLastNodeTree(
														'area',
														selectBankLinkMan);
											}

										}]
							}]
						}]
					}]
				}, {
					columnWidth : 1, // 该列占用的宽度，标识为50％
					border : false,
					name : "gudong_store",
					layout : "form",
					style : 'margin-top:10px',
					bodyStyle : 'padding-left: 0px;text-align:left;',
					hidden : this.isHideGudongInfo,
					scope : this,
					items : [new EnterpriseShareequity({
								type : this.isLoadShareequity,
								bussinessType : this.bussinessType,
								projectId : this.projectId,
								isHidden : this.isGudongDiseditable
										&& this.isAllReadOnly
							})]

				}]

			}]
		});
	}
})

PersonForm = Ext.extend(Ext.FormPanel, {
	layout : "form",
	autoHeight : true,
	labelAlign : 'right',
	isAllReadOnly : true,
	isLoadShareequity : false,
	isHiddenCustomerDetailBtn : false,
	isPersonNameReadOnly : true,
	isEditPerson : false,
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		PersonForm.superclass.constructor.call(this, {
			items : [{
				columnWidth : 1,
				layout : "form",
				style : 'padding-left:17px',
				scope : this,
				items : [{
					layout : "form", // 从上往下的布局
					xtype : 'fieldset',
					title : '客户基本信息',
					items : [{
						layout : "column",
						defaults : {
							anchor : '100%',
							isFormField : true,
							labelWidth : 75
						},
						items : [{
									xtype : 'hidden',
									name : 'person.id',
									value : 0
								}, {
									columnWidth : .33, // 该列在整行中所占的百分比
									layout : "form", // 从上往下的布局
									labelWidth : 70,
									items : [{
										xtype : 'combo',
										triggerClass : 'x-form-search-trigger',
										fieldLabel : "客户姓名",
										name : "person.name",
										readOnly : this.isPersonNameReadOnly,
										allowBlank : false,
										blankText : "客户名称不能为空，请正确填写!",
										anchor : "100%",
										onTriggerClick : function() {
											ressetProjuect(Ext
													.getCmp('createNewSLFunctionForm'));
											var op = this.ownerCt.ownerCt.ownerCt.ownerCt;
											var selectCusNew = function(obj) {
												op.getCmpByName('person.id')
														.setValue("");
												op.getCmpByName('person.name')
														.setValue("");
												op.getCmpByName('person.sex')
														.setValue("");
												op
														.getCmpByName('person.cardtype')
														.setValue("");
												op
														.getCmpByName('person.cardnumber')
														.setValue("");
												op.getCmpByName('person.marry')
														.setValue("");
												op
														.getCmpByName('person.cellphone')
														.setValue("");
												op
														.getCmpByName('person.postcode')
														.setValue("");
												op
														.getCmpByName('person.postaddress')
														.setValue("");

												if (obj.id != 0 && obj.id != "")
													op
															.getCmpByName('person.id')
															.setValue(obj.id);
												if (obj.name != 0
														&& obj.name != "")
													op
															.getCmpByName('person.name')
															.setValue(obj.name);
												if (obj.sex != 0
														&& obj.sex != "")
													op
															.getCmpByName('person.sex')
															.setValue(obj.sex);
												if (obj.cardtype != 0
														&& obj.cardtype != "")
													op
															.getCmpByName('person.cardtype')
															.setValue(obj.cardtype);
												if (obj.cardnumber != 0
														&& obj.cardnumber != "")
													op
															.getCmpByName('person.cardnumber')
															.setValue(obj.cardnumber);
												if (obj.marry != 0
														&& obj.marry != "")
													op
															.getCmpByName('person.marry')
															.setValue(obj.marry);
												if (obj.cellphone != 0
														&& obj.cellphone != "")
													op
															.getCmpByName('person.cellphone')
															.setValue(obj.cellphone);
												if (obj.familypostcode != 0
														&& obj.familypostcode != "")
													op
															.getCmpByName('person.postcode')
															.setValue(obj.familypostcode);
												if (obj.selfemail != 0
														&& obj.selfemail != "")
													op
															.getCmpByName('person.selfemail')
															.setValue(obj.selfemail);
												if (obj.postaddress != 0
														&& obj.postaddress != "")
													op
															.getCmpByName('person.postaddress')
															.setValue(obj.postaddress);

											}
											selectPWName(selectCusNew);
										},
										resizable : true,
										mode : 'romote',
										// editable : false,
										lazyInit : false,
										typeAhead : true,
										minChars : 1,
										/*
										 * store : new Ext.data.JsonStore({}),
										 * displayField : 'name', valueField :
										 * 'id',
										 */
										triggerAction : 'all',
										listeners : {
											scope : this,
											'select' : function(combo, record,
													index) {
												selectCusCombo(record);
											},
											'blur' : function(f) {
											},
											'change' : function(combox, record,
													index) {
												var obj_n = combox.ownerCt.ownerCt.ownerCt.ownerCt.ownerCt;
												ressetProjuect(obj_n);
											}

										}

									}]
								}, {
									columnWidth : .33, // 该列在整行中所占的百分比
									layout : "form", // 从上往下的布局
									labelWidth : 70,
									items : [{
										fieldLabel : "客户性别",
										xtype : "diccombo",
										hiddenName : 'person.sex',
										readOnly : this.isAllReadOnly,
										itemName : '性别', // xx代表分类名称
										allowBlank : false,
										emptyText : "请选择",
										editable : false,
										blankText : "性别不能为空，请正确填写!",
										anchor : "100%",
										listeners : {
											afterrender : function(combox) {
												combox.clearInvalid();
												var st = combox.getStore();
												st.on("load", function() {
															combox
																	.setValue(combox
																			.getValue());
														})
											}
										}

									}]
								}, {
									columnWidth : .33, // 该列在整行中所占的百分比
									layout : "form", // 从上往下的布局
									labelWidth : 70,
									items : [{
										xtype : "diccombo",
										hiddenName : "person.marry",
										fieldLabel : "婚姻状况",
										itemName : '婚姻状况', // xx代表分类名称
										allowBlank : false,
										editable : false,
										readOnly : this.isAllReadOnly,
										blankText : "婚姻状况不能为空，请正确填写!",
										anchor : "100%",
										listeners : {
											scope : this,
											afterrender : function(combox) {
												combox.clearInvalid();

												var st = combox.getStore();
												st.on("load", function() {
													if (combox.getValue() == 0) {

														combox.setValue(null);
														return false;
													}
													combox.setValue(combox
															.getValue());

												})
											},
											'select' : function(combox) {
												if (combox.getValue() == 317) {
													if (this.ownerCt.ownerCt
															.getCmpByName('spousePanel') != null) {
														this.ownerCt.ownerCt
																.getCmpByName('spousePanel')
																.show()
														this.ownerCt.ownerCt
																.doLayout();
													}
												} else {
													if (this.ownerCt.ownerCt
															.getCmpByName('spousePanel') != null) {
														this.ownerCt.ownerCt
																.getCmpByName('spousePanel')
																.hide()
														this.ownerCt.ownerCt
																.doLayout();
													}
												}
											}
										}
									}]
								}, {
									columnWidth : .33, // 该列在整行中所占的百分比
									layout : "form", // 从上往下的布局
									labelWidth : 70,
									items : [{
										xtype : "diccombo",
										hiddenName : "person.cardtype",
										itemName : '证件类型', // xx代表分类名称
										fieldLabel : "证件类型",
										readOnly : this.isAllReadOnly,
										allowBlank : false,
										editable : false,
										emptyText : "请选择",
										blankText : "证件类型不能为空，请正确填写!",
										anchor : "100%",
										listeners : {
											scope : this,
											afterrender : function(combox) {
												combox.clearInvalid();
												var st = combox.getStore();
												st.on("load", function() {
															combox
																	.setValue(combox
																			.getValue());
														})
											}
										}

									}]
								}, {
									columnWidth : .33, // 该列在整行中所占的百分比
									layout : "form", // 从上往下的布局
									labelWidth : 70,
									items : [{
										xtype : "textfield",
										name : "person.cardnumber",
										allowBlank : false,
										fieldLabel : "证件号码",
										readOnly : this.isAllReadOnly,
										readOnly : this.isAllReadOnly,
										blankText : "证件号码不能为空，请正确填写!",
										anchor : "100%",
										listeners : {
											scope : this,
											'blur' : function(tf) {
												var cardNum = tf.getValue();
												var personId = this
														.getCmpByName("person.id")
														.getValue();
												Ext.Ajax.request({
													url : __ctxPath
															+ '/creditFlow/customer/person/verificationPerson.do',
													method : 'POST',
													params : {
														cardNum : cardNum,
														personId : personId
													},
													success : function(
															response, request) {

														var obj = Ext.util.JSON
																.decode(response.responseText);
														if (obj.msg = "false") {
															Ext.ux.Toast
																	.msg(
																			'操作信息',
																			"该证件号码已存在，请重新输入");
															tf.setValue("")
														}
													}
												});
											},
											'blur' : function(com) {
												if (this
														.getCmpByName('person.cardtype')
														.getValue() == 309) {
													if (validateIdCard(com
															.getValue()) == 1) {
														Ext.Msg
																.alert(
																		'身份证号码验证',
																		'证件号码不正确,请仔细核对')
														return;
													} else if (validateIdCard(com
															.getValue()) == 2) {
														Ext.Msg
																.alert(
																		'身份证号码验证',
																		'证件号码地区不正确,请仔细核对')
														return;
													} else if (validateIdCard(com
															.getValue()) == 3) {
														Ext.Msg
																.alert(
																		'身份证号码验证',
																		'证件号码生日日期不正确,请仔细核对')
														return;
													}
												}

											}
										}
									}]
								}, {
									columnWidth : .33, // 该列在整行中所占的百分比
									layout : "form", // 从上往下的布局
									labelWidth : 70,
									items : [{
												xtype : "textfield",
												fieldLabel : "手机号码",
												name : "person.cellphone",
												allowBlank : false,
												readOnly : this.isAllReadOnly,
												anchor : "100%",
												regex : /^[1][34578][0-9]{9}$/,
												regexText : '手机号码格式不正确'
											}]
								}, {
									columnWidth : .33, // 该列在整行中所占的百分比
									layout : "form", // 从上往下的布局
									labelWidth : 70,
									items : [{
												xtype : "textfield",
												name : "person.postcode",
												fieldLabel : "邮政编码",
												allowBlank : false,
												readOnly : this.isAllReadOnly,
												blankText : "邮政编码不能为空，请正确填写!",
												regex : /^[0-9]{6}$/,
												regexText : '邮政编码格式不正确',
												anchor : "100%"
											}]
								}, {
									columnWidth : .33, // 该列在整行中所占的百分比
									layout : "form", // 从上往下的布局
									labelWidth : 70,
									items : [{
										xtype : "textfield",
										name : "person.selfemail",
										fieldLabel : "电子邮箱",
										anchor : "100%",
										readOnly : this.isAllReadOnly,
										regex : /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+/,
										regexText : '电子邮箱格式不正确'
									}]
								}, {
									columnWidth : this.isHiddenCustomerDetailBtn
											? 0.33
											: 0.21, // 该列在整行中所占的百分比
									layout : "form", // 从上往下的布局
									labelWidth : 70,
									items : [{ // 上面是第三行
										xtype : "textfield",
										fieldLabel : "通讯地址",
										readOnly : this.isAllReadOnly,
										allowBlank : false,
										name : "person.postaddress",
										anchor : "100%"// 控制文本框的长度

									}]
								}, {
									columnWidth : .12, // 该列在整行中所占的百分比
									layout : "form", // 从上往下的布局
									style : 'padding-left:17px',
									items : [{ // 上面是第三行
										xtype : 'button',
										text : this.isEditPerson
												? '编辑客户资料'
												: '查看客户资料',
										iconCls : 'btn-customer',
										width : 110,
										hidden : this.isHiddenCustomerDetailBtn,
										scope : this,
										handler : function() {
											var oppositeId = this
													.getCmpByName('person.id')
													.getValue();
											var flag = "detail";
											if (this.isEditPerson == true) {
												flag = "edit";
											}
											if (flag == "detail") {
												seeCustomer("person_customer",
														oppositeId);
											} else if (flag == "edit") {
												editCustomer("person_customer",
														oppositeId, this);
											}
										}
									}]
								}]
					}]
				}]
			}, {
				columnWidth : .1, // 该列在整行中所占的百分比
				layout : "form", // 从上往下的布局
				style : 'padding-left:17px',
				hidden : this.isHidden,
				items : [{
					layout : "form", // 从上往下的布局
					xtype : 'fieldset',
					title : '贷款账户信息',
					items : [{
						layout : "column",
						defaults : {
							anchor : '95%',
							columnWidth : 1,
							isFormField : true,
							labelWidth : 70
						},
						items : [{
							columnWidth : 0.5, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : 70,
							items : [{
								xtype : 'combo',
								mode : 'local',
								displayField : 'name',
								valueField : 'id',
								editable : false,
								anchor : "100%",
								store : new Ext.data.SimpleStore({
											fields : ["name", "id"],
											data : [["个人", "0"], ["公司", "1"]]
										}),
								triggerAction : "all",
								hiddenName : "enterpriseBank.openType",
								fieldLabel : '开户类型',
								allowBlank : this.isHidden,
								readOnly : true,
								hidden : this.isHidden,
								hideLabel : this.isHidden,
								name : 'enterpriseBank.openType',
								listeners : {
									scope : this,
									afterrender : function(combox) {
										var st = combox.getStore();
										combox.setValue(st.getAt(0).data.id);
										combox.fireEvent("select", combox, st
														.getAt(0), 0);
									},
									select : function(combox, record, index) {
										var v = record.data.id;
										var obj = this
												.getCmpByName('enterpriseBank.accountType');
										obj.enable();
										obj.setValue();
										obj.store.removeAll()
										if (v == 0) {
											arrStore = new Ext.data.SimpleStore(
													{
														fields : ["name", "id"],
														data : [["个人储蓄户", "0"],
																["基本户", "1"],
																["一般户", "2"]]
													});
											obj.store.insert(0, arrStore
															.getAt(0));
										} else {
											arrStore = new Ext.data.SimpleStore(
													{
														fields : ["name", "id"],
														data : [["个人储蓄户", "0"],
																["基本户", "1"],
																["一般户", "2"]]
													});
											obj.store.insert(0, arrStore
															.getAt(1));
											obj.store.insert(1, arrStore
															.getAt(2));
										}
									}

								}
							}]
						}, {
							columnWidth : 0.5, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : 70,
							items : [{
								xtype : 'combo',
								mode : 'local',
								displayField : 'name',
								valueField : 'id',
								editable : false,
								width : 70,
								triggerAction : "all",
								hiddenName : "enterpriseBank.accountType",
								fieldLabel : '账户类型',
								allowBlank : this.isHidden,
								readOnly : this.isReadOnly,
								hidden : this.isHidden,
								hideLabel : this.isHidden,
								store : new Ext.data.SimpleStore({
											fields : ["name", "id"],
											data : [["个人储蓄户", "0"],
													["基本户", "1"], ["一般户", "2"]]
										}),
								name : 'enterpriseBank.accountType',
								anchor : "100%"
							}]
						}, {
							columnWidth : 0.5, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : 70,
							items : [{
										fieldLabel : '开户名称',
										name : 'enterpriseBank.name',
										xtype : 'textfield',
										readOnly : this.isReadOnly,
										hidden : this.isHidden,
										hideLabel : this.isHidden,
										allowBlank : this.isHidden,
										anchor : "100%"
									}]
						}, {
							columnWidth : 0.5, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : 70,
							items : [{
										xtype : 'textfield',
										fieldLabel : '账号',
										hidden : this.isHidden,
										hideLabel : this.isHidden,
										allowBlank : this.isHidden,
										name : 'enterpriseBank.accountnum',
										readOnly : this.isReadOnly,
										anchor : "100%"
									}]
						}, {
							columnWidth : .33,
							layout : 'form',
							labelWidth : 70,
							items : [{
										name : 'enterpriseBank.id',
										xtype : 'hidden'
									}, {
										fieldLabel : "银行名称",
										xtype : "combo",
										displayField : 'itemName',
										valueField : 'itemId',
										triggerAction : 'all',
										allowBlank : this.isHidden,
										readOnly : this.isReadOnly,
										hidden : this.isHidden,
										hideLabel : this.isHidden,
										store : new Ext.data.ArrayStore({
											url : __ctxPath
													+ '/creditFlow/common/getBankListCsBank.do',
											fields : ['itemId', 'itemName'],
											autoLoad : true
										}),
										mode : 'remote',
										hiddenName : "enterpriseBank.bankid",
										editable : false,
										blankText : "银行名称不能为空，请正确填写!",
										anchor : "100%",
										listeners : {
											scope : this,
											afterrender : function(combox) {
												var st = combox.getStore();
												st.on("load", function() {
															combox
																	.setValue(combox
																			.getValue());

														})
												combox.clearInvalid();
											}

										}

									}]
						}, {
							columnWidth : .34,
							layout : 'form',
							items : [{
									fieldLabel : "网点名称",
	                                    name : 'enterpriseBank.bankOutletsName',
									       xtype:'textfield',
								allowBlank : this.isHidden,
								readOnly : this.isReadOnly,
								hidden : this.isHidden,
								hideLabel : this.isHidden,
								anchor : "100%"
						

							}]
						}, {
							columnWidth : .33,
							layout : 'form',
							items : [{
										name : 'enterpriseBank.areaId',
										xtype : 'hidden'
									}, {
										// id:'bankName',
										name : 'enterpriseBank.areaName',
										hiddenName : 'enterpriseBank.areaName',
										fieldLabel : '开户地区',
										anchor : '100%',
										// value : '中国银行',
										// submitValue:false,
										xtype : 'trigger',
										triggerClass : 'x-form-search-trigger',
										editable : false,
										allowBlank : this.isHidden,
										readOnly : this.isReadOnly,
										hidden : this.isHidden,
										hideLabel : this.isHidden,
										scope : this,
										onTriggerClick : function() {
											var com = this
											var selectBankLinkMan = function(
													array) {
												var str = "";
												var idStr = ""
												for (var i = array.length - 1; i >= 0; i--) {
													str = str + array[i].text
															+ "-"
													idStr = idStr + array[i].id
															+ ","
												}
												if (str != "") {
													str = str.substring(0,
															str.length - 1);
												}
												if (idStr != "") {
													idStr = idStr.substring(0,
															idStr.length - 1)
												}
												com.previousSibling()
														.setValue(idStr)
												com.setValue(str);
											};
											selectDictionary('area',
													selectBankLinkMan);
										}

									}]
						}]
					}]
				}]
			}]
		});
	}
})

SXProject = Ext.extend(Ext.Panel, {
			constructor : function(_cfg) {
				Ext.applyIf(this, _cfg);
				var a = {
					layout : "column",
					border : false,
					defaults : {
						anchor : '100%',
						columnWidth : .25,
						labelWidth : 60,
						labelAlign : 'right'
					},
					items : [{
								columnWidth : .33,
								layout : 'form',
								items : [{
											fieldLabel : "综合授信金额",
											xtype : "numberfield",
											name : "baseinfo.projectMoney",
											value : 0
										}]
							}, {
								columnWidth : .33,
								layout : 'form',
								items : [{
											fieldLabel : "起始时间",
											xtype : "datefield",
											format : 'Y-m-d',
											anchor : '100%',
											name : "baseinfo.beginDate"
										}]
							}, {
								columnWidth : .33,
								layout : 'form',
								items : [{

											fieldLabel : "结束时间",
											xtype : "datefield",
											format : 'Y-m-d',
											anchor : '100%',
											name : "baseinfo.endDate"
										}]
							}]
				};
				SXProject.superclass.constructor.call(this, {
							items : a
						});
			}
		});
Ext.reg('cqBaseinfo', CQProject);
