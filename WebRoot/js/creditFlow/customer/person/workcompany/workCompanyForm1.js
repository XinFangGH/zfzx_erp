workCompanyForm1 = Ext.extend(Ext.Panel, {
	layout : 'anchor',
	anchor : '100%',
	companyHidden : false,
	constructor : function(_cfg) {
		if (typeof(_cfg.spouseHidden) != "undefined") {
			this.spouseHidden = _cfg.spouseHidden;
		}
		Ext.applyIf(this, _cfg);
		
		var storepayintentPeriod="[";
	    for (var i = 1; i < 31; i++) {
			storepayintentPeriod = storepayintentPeriod + "[" + i+ ", " + i + "],";
		}
		storepayintentPeriod = storepayintentPeriod.substring(0,storepayintentPeriod.length - 1);
		storepayintentPeriod = storepayintentPeriod + "]";
		var obstorepayintentPeriod = Ext.decode(storepayintentPeriod);
		workCompanyForm1.superclass.constructor.call(this, {
			layout : 'column',
			items : [{
						columnWidth : .3,
						layout : 'form',
						defaults : {
							anchor : '100%'
						},
						labelWidth : 85,

						items : [/*{
									xtype : 'hidden',
									name : 'workCompany.personId'
								}, {
									xtype : 'hidden',
									name : 'workCompany.id'
								},*/ {
									xtype : 'textfield',
									fieldLabel : '工作单位',
									allowBlank : this.companyHidden,
									readOnly : this.isReadOnly,
									name : 'person.currentcompany'
								}, {
									xtype : 'textfield',
									fieldLabel : '传真',
									name : 'person.companyFax',
									//regex : /^[0-9]{20}$/,
									//regexText : '传真号码不正确',
									//allowBlank : this.companyHidden,
									readOnly : this.isReadOnly
								}]
					}, {
						columnWidth : .33,
						layout : 'form',
						defaults : {
							anchor : '90%'
						},
						labelWidth : 115,
						items : [{
							xtype : "dickeycombo",
							nodeKey : 'unitproperties',
							hiddenName : "person.unitproperties",
							itemName : '单位性质', // xx代表分类名称
							fieldLabel : "单位性质",
							//allowBlank : this.companyHidden,
							editable : false,
							readOnly : this.isReadOnly,
							// blankText : "单位性质不能为空，请正确填写!",
							listeners : {
								scope : this,
								afterrender : function(combox) {
									var st = combox.getStore();
									st.on("load", function() {
										if (combox.getValue == ''
												|| combox.getValue() == null) {
											combox
													.setValue(st.getAt(0).data.itemId);
											combox.clearInvalid();
										} else {
											combox.setValue(combox.getValue());
											combox.clearInvalid();
										}
									})
								}
							}
						}, {
									xtype : 'textfield',
									fieldLabel : '公司地址',
									name : 'person.unitaddress',
									allowBlank : this.companyHidden,
									readOnly : this.isReadOnly

								} /*
							 * ,{ xtype : "dickeycombo", nodeKey : 'zhiwujob',
							 * fieldLabel : '职务', hiddenName : 'spouse.job', //
							 * emptyText : '请选择职务', width : 80, editable :
							 * false, readOnly : this.isReadOnly, value :
							 * personData == null ? null : personData.job,
							 * listeners : { afterrender : function(combox) {
							 * var st = combox.getStore(); st.on("load",
							 * function() { if (combox.getValue() == 0 ||
							 * combox.getValue() == 1 || combox.getValue() == "" ||
							 * combox.getValue() == null) { combox.setValue(""); }
							 * else { combox.setValue(combox .getValue()); }
							 * combox.clearInvalid(); }) } } }
							 */]
					}, {
						columnWidth : .34,
						layout : 'form',
						defaults : {
							anchor : '100%'
						},
						labelWidth : 85,
						items : [{
									xtype : 'textfield',
									fieldLabel : '单位电话',
									name : 'person.unitphone',
									allowBlank : this.companyHidden,
									readOnly : this.isReadOnly
								}, {
							xtype : 'textfield',
							fieldLabel : '邮政编码',
							name : 'person.unitpostcode',
							regex : /^[0-9]{6}$/,
							regexText : '邮政编码格式不正确',
							//allowBlank : this.companyHidden,
							readOnly : this.isReadOnly
						}]
					}/*, {
						
						 * columnWidth : 0.33, layout : 'form', defaults : {
						 * anchor : '100%' }, labelWidth : 70,
						 
						// items : [{
						columnWidth : 1,
						layout : 'column',
						items : [{
							columnWidth : 0.25,
							layout : 'form',
							defaults : {
								anchor : '100%'
							},
							scope : this,
							labelWidth : 70,
							items : [{
								xtype : 'textfield',
								fieldLabel : '营业执照',
								
								blankText : "营业执照号码不能为空，请正确填写!",
								emptyText : '请输入营业执照号码',
								allowBlank : this.companyHidden,
								readOnly : this.isReadOnly,
								name : 'workCompany.cciaa',
								listeners : {
									'blur' : function() {
										existenterNameAction(this,
												"validatorCciaa", "营业执照号码不能重复！");
									},
									'afterrender' : function(com) {
										com.clearInvalid();
									}
								}
							}]

						}, {
							columnWidth : 0.25,
							layout : 'form',
							defaults : {
								anchor : '100%'
							},
							scope : this,
							items : [{
								xtype : 'datefield',
								format : 'Y-m-d',
								fieldLabel : '营业时间',
								readOnly : this.isReadOnly,
								name : 'workCompany.businessDate'
							}]
						},{
							columnWidth : 0.25,
							layout : 'form',
							labelWidth : 70,
							defaults : {
								anchor : '100%'
							},
							scope : this,
							items : [{
								xtype : 'textfield',
								fieldLabel : '主营业务',
								name : 'workCompany.primaryBusinessType',
								//allowBlank : this.companyHidden,
								readOnly : this.isReadOnly
							}]

								
								 * }, { xtype : 'numberfield', fieldLabel :
								 * '雇员人数', allowBlank : this.spouseHidden,
								 * readOnly : this.isReadOnly, name :
								 * 'workCompany.employeeTotal' }]
								 
						}, {
							columnWidth : 0.25,
							layout : 'form',
							defaults : {
								anchor : '100%'
							},
							scope : this,
							items : [{
								xtype : 'numberfield',
								fieldLabel : '雇员人数',
								readOnly : this.isReadOnly,
								name : 'workCompany.employeeTotal'
							}]
						}]
						
						 * , { columnWidth : 0.33, layout : 'form', defaults : {
						 * anchor : '100%' }, labelWidth : 70, items : [ { xtype :
						 * 'datefield', format : 'Y-m-d', fieldLabel : '营业时间',
						 * name : 'workCompany.businessDate' }, { xtype :
						 * 'textfield', fieldLabel : '主营业务', name :
						 * 'workCompany.primaryBusinessType', allowBlank :
						 * this.companyHidden, readOnly : this.isReadOnly }, {
						 * xtype : 'numberfield', fieldLabel : '雇员人数',
						 * allowBlank : this.spouseHidden, readOnly :
						 * this.isReadOnly, name : 'workCompany.employeeTotal' }] }
						 
					}*/,{
						columnWidth:1,
						layout:'column',
						items:[{
							layout:'form',
							columnWidth:.63,
							labelWidth:85,
							defaults:{
								anchor:'90%'
							},
							scope:this,
							items:[{
								xtype : "combo",
								triggerClass : 'x-form-search-trigger',
								fieldLabel : "行业类别",
								name : 'person.hangyeName',
							    hiddenName : 'person.hangyeName',
								scope : this,
								allowBlank : false,
								emptyText : '请选择行业类别',
								readOnly : this.isReadOnly,
								scope:true,
								onTriggerClick : function(e) {
											var obj = this;
											var oobbj=this.nextSibling();
											selectTradeCategory(obj,oobbj);
									}
								},{
									xtype:'hidden',
									name:'person.hangyeType'
								}]
						},{
							layout:'form',
							columnWidth:.34,
							labelWidth:85,
							defaults:{
								anchor:'100%'							
							},
							scope:this,
							items:[{
								xtype : "dickeycombo"/*"textfield"*/,
								nodeKey : 'companyScale',
								hiddenName : 'person.companyScale',
								fieldLabel : "公司规模",
								editable : false,
								allowBlank : this.companyHidden,
								readOnly : this.isReadOnly,
								value : null,
								listeners : {
									afterrender : function(combox) {
										var st = combox.getStore();
										st.on("load", function() {
											if (combox.getValue() == 0
													|| combox
															.getValue() == 1
													|| combox
															.getValue() == ""
													|| combox
															.getValue() == null) {
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
						}]
					}, {
						
						columnWidth : 1,
						layout : 'column',
						items : [{
							columnWidth : .3,
							layout : 'form',
							labelWidth : 85,
							defaults : {
								anchor : '100%'
							},
							scope : this,
							items : [{
								xtype : "dickeycombo",
								nodeKey : 'zhiwujob',
								fieldLabel : '职务',
								hiddenName : 'person.job',
								allowBlank : this.companyHidden,
								// emptyText : '请选择职务',
								//width : 80,
								editable : false,
								readOnly : this.isReadOnly,
								value : personData == null
										? null
										: personData.job,
								listeners : {
									afterrender : function(combox) {
										var st = combox.getStore();
										st.on("load", function() {
											if (combox.getValue() == 0
													|| combox.getValue() == 1
													|| combox.getValue() == ""
													|| combox.getValue() == null) {
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
							columnWidth : .33,
							layout : 'form',
							defaults : {
								anchor : '90%'
							},
							labelWidth : 115,
							scope : this,
							items : [{
										xtype : 'datefield',
										format : 'Y-m-d',
										fieldLabel : '入职时间',
										allowBlank : this.companyHidden,
										readOnly : this.isReadOnly,
										name : 'person.jobstarttime'
									}]
						},{
							columnWidth : .34,
							layout : 'form',
							defaults : {
								anchor : '100%'
							},
							labelWidth : 85,
							scope : this,
							items : [{
								xtype : "textfield",
								//nodeKey : 'bm',
							//	hiddenName : "person.companyDepartment",
							//	itemName : '所属部门', // xx代表分类名称
								fieldLabel : "所属部门",
								name:'person.department',
								//allowBlank : this.companyHidden,
								readOnly : this.isReadOnly,
								editable : false
							}]
							
						}]
						/*
						 * , { columnWidth : 0.33, layout : 'form', defaults : {
						 * anchor : '100%' }, labelWidth : 70, items : [ { xtype :
						 * 'datefield', format : 'Y-m-d', fieldLabel : '营业时间',
						 * name : 'workCompany.businessDate' }, { xtype :
						 * 'textfield', fieldLabel : '主营业务', name :
						 * 'workCompany.primaryBusinessType', allowBlank :
						 * this.companyHidden, readOnly : this.isReadOnly }, {
						 * xtype : 'numberfield', fieldLabel : '雇员人数',
						 * allowBlank : this.spouseHidden, readOnly :
						 * this.isReadOnly, name : 'workCompany.employeeTotal' }] }
						 */
					},{
					columnWidth : 1,
						layout : 'column',
						items : [{
							columnWidth : .3,
							layout : 'form',
							labelWidth : 85,
							defaults : {
								anchor : '100%'
							},
							scope : this,
							items : [{
										xtype : 'numberfield',
										fieldLabel : '月收入',
										allowBlank : false,
								        blankText : "月收入不能为空，请正确填写!",
										readOnly : this.isReadOnly,
										name : 'person.jobincome'
									}]

						},{
							columnWidth : .33,
							layout : 'form',
							defaults : {
								anchor : '90%'
							},
							labelWidth : 115,
							scope : this,
							items : [{
//										xtype : 'datefield',
//										format : 'Y-m-d',
										fieldLabel : '发薪时间',
//										name : 'person.jobstarttime'
										readOnly : this.isReadOnly,
										xtype : 'combo',
										mode : 'local',
										displayField : 'name',
										valueField : 'id',
										editable : false,
										store : new Ext.data.SimpleStore({
											fields : ["name", "id"],
											data : obstorepayintentPeriod
										}),
										triggerAction : "all",
										hiddenName : "person.payDate"
									}]
						},{
							columnWidth : .34,
							layout : 'form',
							labelWidth : 85,
							defaults : {
								anchor : '100%'
							},
							scope : this,
							items : [{
//								xtype : 'textfield',
								xtype:'combo',
								fieldLabel : '发薪形式',
								readOnly : this.isReadOnly,
								name : 'person.wagebank',
								mode : 'local',
							    displayField : 'name',
							    valueField : 'value',
							    width : 70,
							    store :new Ext.data.SimpleStore({
										fields : ["name", "value"],
										data : [["打卡", "打卡"],["现金", "现金"]]
								}),
								triggerAction : "all"
							}]
						}]
					}]
		})
		/*
		 * if (this.personId != null && this.personId != 'undefined') {
		 * this.loadData( { url : __ctxPath +
		 * '/credit/customer/person/getSpouseInfo.do?personId=' + this.personId,
		 * root : 'data', preName : 'spouse' }); }
		 */
	},
	save : function() {
		var win = this;
		this.formPanel.getForm().submit({
					method : 'POST',
					waitTitle : '连接',
					waitMsg : '消息发送中...',
					success : function(form, action) {
						Ext.ux.Toast.msg('操作信息', '保存成功!');
						win.destroy();
					},
					failure : function(form, action) {
						Ext.ux.Toast.msg('操作信息', '保存失败!');
					}
				});
	}

});
