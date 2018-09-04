// 第一个filedset
ExtUD.Ext.TypeSelectInfoPanel = Ext.extend(Ext.Panel, {
	layout : "form",
	autoHeight : true,
	bodyStyle : 'padding-right:18px;',
	constructor : function(_cfg) {
		if (_cfg == null) {
			_cfg = {};
		}
		Ext.applyIf(this, _cfg);
		this.initComponents();
		ExtUD.Ext.TypeSelectInfoPanel.superclass.constructor.call(this, {
			items : [{
				layout : "column",
				defaults : {
					anchor : '100%',
					columnWidth : 1,
					isFormField : true
				},
				items : [{
					columnWidth : .25, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : 85,
					items : [{
						//columnWidth : .295, // 该列在整行中所占的百分比
						layout : "form", // 从上往下的布局
						labelWidth : 100,
						items : [{
							xtype : "textfield",
							fieldLabel : "拟贷款金额",
							fieldClass : 'field-align',
							name : "projectMoney1",
							readOnly : this.isAllReadOnly,
							allowNegative : false, // 允许负数
							style : {
								imeMode : 'disabled'
							},
							blankText : "贷款金额不能为空，请正确填写!",
							allowBlank : false,
							anchor : "100%",// 控制文本框的长度
							listeners : {
								scope : this,
								afterrender : function(obj) {
									obj.on("keyup")
								},
								change : function(nf) {

									var value = nf.getValue();
									var index = value.indexOf(".");
									if (index <= 0) { // 如果第一次输入
										// 没有进行格式化
										nf.setValue(Ext.util.Format.number(
												value, '0,000.00'))
										this
												.getCmpByName("slSmallloanProject.projectMoney")
												.setValue(value);
									} else {

										if (value.indexOf(",") <= 0) {
											var ke = Ext.util.Format.number(
													value, '0,000.00')
											nf.setValue(ke);
											this
													.getCmpByName("slSmallloanProject.projectMoney")
													.setValue(value);
										} else {
											var last = value.substring(index
															+ 1, value.length);
											if (last == 0) {
												var temp = value.substring(0,
														index);
												temp = temp.replace(/,/g, "");
												this
														.getCmpByName("slSmallloanProject.projectMoney")
														.setValue(temp);
											} else {
												var temp = value.replace(/,/g,
														"");
												this
														.getCmpByName("slSmallloanProject.projectMoney")
														.setValue(temp);
											}
										}

									}
								}
							}
						}, {
							xtype : "hidden",
							name : "slSmallloanProject.projectMoney"
						}]
					}]
				},{
					columnWidth : .05, // 该列在整行中所占的百分比
									layout : "form", // 从上往下的布局
									labelWidth : 20,
									items : [{
												fieldLabel : "元 ",
												labelSeparator : '',
												anchor : "100%"
											}]
				}, {
					columnWidth : .4, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : 120,
					items : [{
								fieldLabel : "期望资金到位日期",
								xtype : "datefield",
								style : {
									imeMode : 'disabled'
								},
								name : "slSmallloanProject.startDate",
								allowBlank : false,
								readOnly : this.isAllReadOnly,
								blankText : "期望资金到位日期不能为空，请正确填写!",
								anchor : "100%",
								format : 'Y-m-d'
							}]
				}, {
					columnWidth : .3, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : 85,
					items : [{
						xtype : "combo",
						triggerClass : 'x-form-search-trigger',
						hiddenName : "degree",
						editable : false,
						fieldLabel : "项目经理",
						blankText : "项目经理不能为空，请正确填写!",
						allowBlank : false,
						readOnly : this.isAllReadOnly,
						anchor : "100%",
						onTriggerClick : function(cc) {
							var obj = this;
							var appuerIdObj = obj.nextSibling();
							var userIds = appuerIdObj.getValue();
							if (null == obj.getValue() || "" == obj.getValue()) {
								userIds = "";
							}
							new UserDialog({
										userIds : userIds,
										userName : obj.getValue(),
										single : false,
										type : 'branch',
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
				}]
			},{
				xtype:'textfield',
				name:'',
				fieldLabel:'贷款用途',
				labelWidth : 85,
				anchor:'100%'
			},{
				layout:'column',
				items:[{
					layout:'column',
					columnWidth:.3,
					items:[{
						layout:'form',
						columnWidth:.6,
						items:[{
							xtype:'textfield',
							fieldLabel:'期望利率',
							name:'slSmallloanProject.intentRateStart',
							anchor:'100%'
						}]
					},{
						layout:'form',
						style:'margin-left:3px;',
						columnWidth:.03,
						items:[{
							html:'~',
							anchor:'100%'
						}]
					},{
						layout:'form',
						columnWidth:.27,
						items:[{
							xtype:'textfield',
							name:'slSmallloanProject.intentRateEnd',
							fieldLabel:'',
							labelSeparator :'',
							labelWidth :0,
							hideLabel :true,
							anchor:'100%'
						}]
					},{
						layout:'form',
						columnWidth:.1,
						items:[{
							html:'%/期',
							style:'margin-top:3px;'
							
						}]
					}]
				},{
					layout:'form',
					columnWidth:.4,
					labelWidth : 120,
					items:[{
						xtype : "dickeycombo",
						nodeKey : '9',
						fieldLabel : '可提供的主担保方式',
						anchor : '100%',
						hiddenName : 'slSmallloanProject.loanType',
						listeners : {
							afterrender : function(combox) {
								var st = combox.getStore();
								st.on("load", function() {
											if (combox.getValue() == 0
													|| combox.getValue() == 1) {
												combox.setValue("");
											} else {
												combox.setValue(combox
														.getValue());
											}
											combox.clearInvalid();
										})
							}
						}
					}]
				},{
					layout:'column',
					columnWidth:.3,
					items:[{
						layout:'form',
						columnWidth:.95,
						items:[{
							xtype:'textfield',
							fieldLabel:'贷款期限',
							name:'slSmallloanProject.payintentPeriod',
							anchor:'100%'
						}]
					},{
						layout:'form',
						columnWidth:.05,
						items:[{
							html:'月',
							style:'margin-left:3px;margin-top:3px;'
						}]
					}]
				}]
			}/*, {
				layout : "column",
				defaults : {
					anchor : '100%',
					columnWidth : 1,
					isFormField : true,
					labelWidth : 110
				},
				items : [{
					columnWidth : .3, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : 120,
					items : [{
						xtype : "dickeycombo",
						nodeKey : '9',
						fieldLabel : '可提供的主担保方式',
						anchor : '100%',
						hiddenName : 'slSmallloanProject.loanType',
						listeners : {
							afterrender : function(combox) {
								var st = combox.getStore();
								st.on("load", function() {
											if (combox.getValue() == 0
													|| combox.getValue() == 1) {
												combox.setValue("");
											} else {
												combox.setValue(combox
														.getValue());
											}
											combox.clearInvalid();
										})
							}
						}
					}]
				}, {
					columnWidth : .4, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : 170,
					items : [{
								fieldLabel : "资金用途",
								xtype : "dickeycombo",
								hiddenName : 'slSmallloanProject.purposeType',
								displayField : 'itemName',
								readOnly : this.isAllReadOnly,
								itemName : '资金用途',
								width : 203,
								nodeKey : 'smallloan_purposeType',
								emptyText : "请选择",
								//anchor : "100%",
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

			}*/]
		});

	},
	initComponents : function() {
	}
})

// 每二个fieldset
ExtUD.Ext.PeerMainInfoPanel = Ext.extend(Ext.Panel, {
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
	constructor : function(_cfg) {
		if (_cfg == null) {
			_cfg = {};
		}
		if (typeof(_cfg.title) != "undefined") {
			this.title = _cfg.title;
		}
		if (typeof(_cfg.isAllReadOnly) != "undefined") {
			this.isAllReadOnly = _cfg.isAllReadOnly;
		}
		if (typeof(_cfg.bussinessType) != "undefined") {
			this.bussinessType = _cfg.bussinessType;
		}
		if (typeof(_cfg.projectId) != "undefined") {
			this.projectId = _cfg.projectId;
			this.isLoadShareequity = true;
			this.isEnterpriseShortNameReadonly = true;
		}
		if (_cfg.isHideGudongInfo) {
			this.isHideGudongInfo = _cfg.isHideGudongInfo;
		}
		if (_cfg.isEnterprisenameReadonly) {
			this.isEnterprisenameReadonly = _cfg.isEnterprisenameReadonly;
		}
		if (_cfg.isEnterpriseShortNameReadonly) {
			this.isEnterpriseShortNameReadonly = _cfg.isEnterpriseShortNameReadonly;
		}
		if (_cfg.isHiddenCustomerDetailBtn) {
			this.isHiddenCustomerDetailBtn = _cfg.isHiddenCustomerDetailBtn;
		}
		if (typeof(_cfg.isEditEnterprise) != "undefined") {
			this.isEditEnterprise = _cfg.isEditEnterprise;
		}
		Ext.applyIf(this, _cfg);
		ExtUD.Ext.PeerMainInfoPanel.superclass.constructor.call(this, {
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
							labelWidth : 75
						},
						items : [{
							columnWidth : 1, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
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
										readOnly : this.isAllReadOnly,
										blankText : "企业名称不能为空，请正确填写!",
										allowBlank : false,
										scope : this,
										onTriggerClick : function() {
											var win = Ext
													.getCmp("addThridCompany_win");
											if (typeof(win) != "undefined") {
												selectEnterprise(setEnterpriseNameStockUpdate);
											} else {
												selectEnterprise(setEnterpriseNameStockUpdateNew);
											}
										},
										resizable : true,
										mode : 'romote',
										editable : true,
										lazyInit : false,
										typeAhead : true,
										minChars : 1,
										store : new Ext.data.JsonStore({
											url : __ctxPath
													+ '/credit/customer/enterprise/ajaxQueryEnterpriseForCombo.do',
											root : 'topics',
											autoLoad : true,
											fields : [{
														name : 'id'
													}, {
														name : 'enterprisename'
													}, {
														name : 'shortname'
													}, {
														name : 'hangyetypevalue'
													}, {
														name : 'hangyetype'
													}, {
														name : 'organizecode'
													}, {
														name : 'cciaa'
													}, {
														name : 'postcoding'
													}, {
														name : 'address'
													}, {
														name : 'telephone'
													}, {
														name : 'legalpersonid'
													}],
											listeners : {
												scope : this,
												'load' : function(s, r, o) {
													if (s.getCount() == 0) {
														this
																.getCmpByName('enterprise.enterprisename')
																.markInvalid('没有查找到匹配的记录');
													}
												}
											}
										}),
										displayField : 'enterprisename',
										valueField : 'id',
										triggerAction : 'all',
										listeners : {
											scope : this,
											'select' : function(combo, record,
													index) {
												setEnterpriseNameStockUpdateCombo(record);
											},
											'blur' : function(f) {
												if (f.getValue() != null
														&& f.getValue() != '') {
													this
															.getCmpByName('enterprise.id')
															.setValue(f
																	.getValue());
												}
											}

										}
									}]
						}, {
							columnWidth : 0.5, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							items : [{
										xtype : "textfield",
										anchor : '100%',
										name : "enterprise.shortname",
										allowBlank : false,
										fieldLabel : "企业简称",
										readOnly : this.isAllReadOnly,
										blankText : "企业简称不能为空，请正确填写!"

									}]
						}, {
							columnWidth : 0.5, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : 70,
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
						}, {
							columnWidth : 0.5, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							items : [{
										xtype : "textfield",
										name : "enterprise.organizecode",
										allowBlank : false,
										fieldLabel : "机构代码",
										// emptyText : "请填写组织机构代码",
										readOnly : this.isAllReadOnly,
										blankText : "组织机构代码不能为空，请正确填写!",
										anchor : "100%"
									}]
						}, {
							xtype : "hidden",
							name : 'enterprise.hangyeType'
						}, {
							columnWidth : 0.5, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : 70,
							items : [{
										xtype : "textfield",
										name : "enterprise.cciaa",
										fieldLabel : "营业执照",
										readOnly : this.isAllReadOnly,
										blankText : "营业执照号码不能为空，请正确填写!",
										allowBlank : false,
										anchor : "100%"
									}]
						}, {
							columnWidth : 0.5, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							items : [{
										xtype : "textfield",
										name : "enterprise.telephone",
										allowBlank : false,
										fieldLabel : "公司电话",
										readOnly : this.isAllReadOnly,
										blankText : "公司电话不能为空，请正确填写!",
										anchor : "100%"
									}]
						}, {
							columnWidth : 0.5, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : 70,
							items : [{
										xtype : "textfield",
										allowBlank : false,
										name : "enterprise.postcoding",
										fieldLabel : "邮政编码",
										readOnly : this.isAllReadOnly,
										blankText : "邮政编码不能为空，请正确填写!",
										anchor : "100%"
									}]
						}, {
							columnWidth : 1, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							items : [{
										xtype : "textfield",
										fieldLabel : "通讯地址",
										readOnly : this.isAllReadOnly,
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
						title : '法定代表人（负责人）信息',
						items : [{
							layout : "column",
							defaults : {
								anchor : '100%',
								columnWidth : 1,
								isFormField : true,
								labelWidth : 75
							},
							items : [{
										xtype : 'hidden',
										name : 'person.id',
										value : 0
									}, {
										columnWidth : 1, // 该列在整行中所占的百分比
										layout : "form", // 从上往下的布局
										items : [{
													xtype : "textfield",
													fieldLabel : "法人姓名",
													name : "person.name",
													readOnly : this.isAllReadOnly,
													allowBlank : false,
													anchor : '100%',
													blankText : "法人姓名不能为空，请正确填写!"
												}]
									}, {
										columnWidth : 0.5, // 该列在整行中所占的百分比
										layout : "form", // 从上往下的布局
										items : [{
											fieldLabel : "法人性别",
											xtype : "diccombo",
											hiddenName : 'person.sex',
											displayField : 'itemName',
											readOnly : this.isAllReadOnly,
											itemName : '性别', // xx代表分类名称
											allowBlank : false,
											emptyText : "请选择",
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
											emptyText : "请选择",
											blankText : "证件类型不能为空，请正确填写!",
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
										columnWidth : 1, // 该列在整行中所占的百分比
										layout : "form", // 从上往下的布局
										items : [{
													xtype : "textfield",
													name : "person.cardnumber",
													allowBlank : false,
													fieldLabel : "证件号码",
													readOnly : this.isAllReadOnly,
													blankText : "证件号码不能为空，请正确填写!",
													anchor : "100%"
												}]
									}, {
										columnWidth : this.isHiddenCustomerDetailBtn
												? 1
												: .5, // 该列在整行中所占的百分比
										layout : "form", // 从上往下的布局
										items : [{
													xtype : "textfield",
													name : "person.cellphone",
													readOnly : this.isAllReadOnly,
													fieldLabel : "手机号码",
													anchor : "100%"
												}]
									}, {
										columnWidth : this.isHiddenCustomerDetailBtn
												? 1
												: .5, // 该列在整行中所占的百分比
										layout : "form", // 从上往下的布局
										labelWidth : this.isHiddenCustomerDetailBtn
												? 75
												: 65,
										items : [{
											xtype : "textfield",
											name : "person.selfemail",
											readOnly : this.isAllReadOnly,
											fieldLabel : "电子邮箱",
											anchor : "100%",
											vtype : 'email',
											vtypeText : '邮箱格式不正确!',
											listeners : {
												afterrender : function(obj) {

													if (obj.getValue() == ""
															|| obj.getValue() == null) {
														Ext.apply(obj, {
																	vtype : ""
																});
													}
												},
												blur : function(obj) {

													if (obj.getValue() == ""
															|| obj.getValue() == null) {
														Ext.apply(obj, {
																	vtype : ""
																});
													} else {
														Ext.apply(obj, {
																	vtype : 'email'
																});
													}
												}

											}
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
								isHidden : this.isAllReadOnly
							})]

				}]
			}]
		});
	}
})
Ext.reg('typeSelectInfoPanel', ExtUD.Ext.TypeSelectInfoPanel);