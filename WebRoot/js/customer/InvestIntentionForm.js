/**
 * @author
 * @createtime
 * @class InvestIntentionForm
 * @extends Ext.Window
 * @description InvestIntention表单
 * @company 智维软件
 */
InvestIntentionForm = Ext.extend(Ext.Window, {
	isAllreadOnly : false,
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		if (typeof (_cfg.isAllreadOnly) != "undefined") {
			this.isAllreadOnly = _cfg.isAllreadOnly;
		}
		// 必须先初始化组件
		this.initUIComponents();
		InvestIntentionForm.superclass.constructor.call(this, {
					id : 'InvestIntentionFormWin',
					layout : 'fit',
					items : this.formPanel,
					modal : true,
					height : 400,
					width : 900,
					maximizable : true,
					title : '投资意向详细信息',
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
		this.formPanel = new Ext.form.FormPanel({
					autoHeight : true,
			autoWidth : true,
			layout : 'form',
			bodyStyle : 'padding:10px',
			border : false,
			autoScroll : true,
			frame : true,
			labelAlign : 'right',
			defaults : {
					anchor : '96%',
					columnWidth : 1,
					labelWidth : 60
				},
			items : [{
				xtype : 'fieldset',
				title : '填写个人基本信息',
				collapsible : true,
				columnWidth : 1,
				items : [{

					layout : "column",
					columnWidth : 1,
					items : [{
						layout : 'form',
						columnWidth : .33,
						labelWidth : 60,
						items : [{
									id : 'investPerName',
									fieldLabel : '姓名',
									readOnly : this.isAllreadOnly,
									editable : false,
									//allowBlank : false,
									xtype : this.isAllreadOnly?'textfield':'trigger',
									value:this.pername,
									triggerClass : 'x-form-search-trigger',
									blankText : "性名不能为空，请正确填写!",
									name : 'csInvestmentperson.investName',
									maxLength : 15,
									onTriggerClick : function() {
										new InvestPersonList({
											formPanel : Ext.getCmp('InvestIntentionFormWin')
										}).show();
									},
									anchor : '100%'
								}, {
									id : 'investCardType',
									xtype : "dickeycombo",
									nodeKey : 'card_type_key',
									fieldLabel : '证件类型',
									readOnly : this.isAllreadOnly,
									name : 'csInvestmentperson.cardtype',
									allowBlank : false,
									editable : false,
									anchor : '100%',
									blankText : "证件类型不能为空，请正确填写!",
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
						layout : 'form',
						columnWidth : .33,
						labelWidth : 60,
						items : [{
							xtype : "dickeycombo",
							nodeKey : 'sex_key',
							id : 'investPerSex',
							fieldLabel : '性别',
							readOnly : this.isAllreadOnly,
							anchor : '100%',
							columnWidth : 3,
							hiddenName : 'csInvestmentperson.sex',
							allowBlank : false,
							editable : false,
							blankText : "性别不能为空，请正确填写!",
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
								},
								select : function(combo, record, index) {
									var display = Ext
											.getCmp('displayProfilePhoto');
									if (combo.value == '313') {// 312
										display.body
												.update('<img src="'
														+ __ctxPath
														+ '/images/default_image_female.jpg" />');
									} else {
										display.body
												.update('<img src="'
														+ __ctxPath
														+ '/images/default_image_male.jpg" />');
									}
								}
							}
						}, {
							id : 'investCardNumber',
							fieldLabel : '证件号码',
							readOnly : this.isAllreadOnly,
							name : 'csInvestmentperson.cardnumber',
							allowBlank : false,
							anchor : '100%',
							xtype : 'textfield',
							blankText : "证件号码不能为空，请正确填写!",
							maxLength : 25
						}]
					}, {
						layout : 'form',
						columnWidth : .33,
						labelWidth : 60,
						items : [{
									id : 'investPhoneNumber',
									fieldLabel : '手机号码',
									xtype : 'textfield',
									readOnly : this.isAllreadOnly,
									name : 'csInvestmentperson.cellphone',
									allowBlank : false,
									anchor : '100%',
									blankText : "手机号码不能为空，请正确填写!",
									maxLength : 11
								}, {
									id : 'investPerBirthday',
									fieldLabel : '出生日期',
									readOnly : this.isAllreadOnly,
									anchor : '100%',
									name : 'csInvestmentperson.birthDay',
									xtype : 'datefield',
									format : 'Y-m-d'
									// value : new Date()
								}]
					}, {
						id : 'investId',
						name : 'csInvestmentperson.investId',
						xtype : 'hidden'
					}, {
						id : 'investPersonId',
						name : 'investIntention.investPersonId',
						xtype : 'hidden'
					}]

				}, {
					layout : "column",
					columnWidth : 1,
					items : [{
						layout : 'form',
						columnWidth : .66,
						labelWidth : 60,
						items : [{
									id : 'investPostAddress',
									fieldLabel : '通信地址',
									readOnly : this.isAllreadOnly,
									anchor : '100%',
									colspan : 2,
									name : 'csInvestmentperson.postaddress',
									xtype : 'textfield',
									width : 343
								}]
					}, {
						layout : 'form',
						columnWidth : .33,
						labelWidth : 60,
						items : [{
									id : 'investPostCode',
									fieldLabel : '电子邮箱',
									readOnly : this.isAllreadOnly,
									anchor : '100%',
									name : 'csInvestmentperson.selfemail',
									xtype : 'textfield',
									vtype : 'email',
									maxLength : 255
								}]
					}]
				}]
			}, {
				xtype : 'fieldset',
				title : this.isAllreadOnly?'投资意向':'投资意向登记',
				collapsible : true,
				columnWidth : 1,
				items : [{
					layout : "column",
					columnWidth : 1,
					items : [{
								layout : 'form',
								columnWidth : .33,
								labelWidth : 100,
								items : [{
											fieldLabel : '意向投资额度(元)',
											readOnly : this.isAllreadOnly,
											name : 'investIntention.intentMoney',
											xtype : 'textfield',
											anchor : '100%',
											allowBlank : false,
											blankText : "意向投资额度不能为空，请正确填写!",
											maxLength : 255
										}, {
											xtype : 'combo',
											triggerClass : 'x-form-search-trigger',
											name : "investIntention.findPerson",
											editable : false,
											fieldLabel : "采集人",
											blankText : "采集人不能为空，请正确填写!",
											allowBlank : false,
											readOnly : this.isAllreadOnly,
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
															title : "选择采集人",
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
								layout : 'form',
								columnWidth : .33,
								labelWidth : 80,
								items : [{
											fieldLabel : '投资期限(月)',
											readOnly : this.isAllreadOnly,
											xtype : 'numberfield',
											name : 'investIntention.intentDate',
											allowBlank : false,
											anchor : '100%',
											blankText : "投资期限不能为空，请正确填写!",
											maxLength : 10
										}, {
											fieldLabel : '有效截止日',
											readOnly : this.isAllreadOnly,
											anchor : '100%',
											allowBlank : false,
											blankText : "有效截止日不能为空，请正确填写!",
											name : 'investIntention.endDate',
											xtype : 'datefield',
											format : 'Y-m-d'
											// value : new Date()
										}]
							}, {
								layout : 'form',
								columnWidth : .33,
								labelWidth : 60,
								items : [{
											fieldLabel : '地区要求',
											readOnly : this.isAllreadOnly,
											xtype : 'textfield',
											anchor : '100%',
											name : 'investIntention.addrDemand',
											maxLength : 255
										}, {
											fieldLabel : '状态',
											readOnly : this.isAllreadOnly,
											xtype : 'combo',
											hiddenName : 'investIntention.status',
											mode : 'local',
											displayField : 'name',
											valueField : 'id',
											blankText : "状态不能为空，请正确填写!",
											allowBlank : false,
											editable : false,
											width : 50,
											triggerAction : "all",
											anchor : '100%',
											store : new Ext.data.SimpleStore({//
														fields : ["name", "id"],
														data : [
																["有效", "1"],
																["已匹配", "2"],
																["已过期", "3"]
															   ]
													})
										}/*{
											fieldLabel : '状态 ',
											xtype : 'combo',
											anchor : '100%',
											name : 'investIntention.status',
											mode : 'local',
											displayField : 'itemName',
											valueField : 'itemId',
											editable : false,
											triggerAction : "all",
											blankText : "状态不能为空，请正确填写!",
											allowBlank : false,
											store : new Ext.data.SimpleStore({
												fields : ["itemName", "itemId"],
												data : [["有效", "1"],["已匹配", "2"],["已过期", "3"]]
											}),
											maxLength : 255
										}*/]
							}]

				}, {
					layout : "column",
					columnWidth : 1,
					items : [{
						layout : 'form',
						columnWidth : .4,
						labelWidth : 200,
						items : [{
							fieldLabel : '有无单笔投资最低或最高额度限制',
							readOnly : this.isAllreadOnly,
							xtype : 'radiogroup',
							items : [{
										boxLabel : '有',
										name : 'investIntention.issingleDemand',
										inputValue : "有"
									}, {
										boxLabel : '无',
										name : 'investIntention.issingleDemand',
										inputValue : "无"
									}]
						}]
					}, {
						layout : 'form',
						columnWidth : .6,
						labelWidth : 80,
						items : [{
							fieldLabel : '风险保障要求',
							readOnly : this.isAllreadOnly,
							xtype : 'combo',
							name : 'investIntention.dangerDemand',
							mode : 'local',
							displayField : 'name',
							valueField : 'id',
							editable : false,
							width : 50,
							triggerAction : "all",
							anchor : '100%',
							store : new Ext.data.SimpleStore({
										fields : ["name", "id"],
										data : [
												["保守(要求提供足值资产抵押等)", "1"],
												["中等(要求提供信用担保,第三方担保或专业担保公司担保等)","2"],
												["灵活(可根据情况接受灵活的保障措施)", "3"]]
									})
						}]
					}]
				}, {
					layout : "column",
					columnWidth : 1,
					items : [{
								layout : 'form',
								columnWidth : 1,
								labelWidth : 80,
								items : [{
											fieldLabel : '其他投资意向',
											readOnly : this.isAllreadOnly,
											xtype : 'textarea',
											width : 672,
											name : 'investIntention.otherInvest',
											maxLength : 255
										}]
							}]
				}]
			}, {
				name : 'investIntention.intentId',
				xtype : 'hidden',
				value : this.intentId == null ? '' : this.intentId
			}, {
				fieldLabel : '状态',
				name : 'investIntention.status',
				readOnly : this.isAllreadOnly,
				maxLength : 255,
				xtype : 'hidden'
			}]
		});
		// 加载表单对应的数据
		if (this.intentId != null && this.intentId != 'undefined') {

			this.formPanel.loadData({
						url : __ctxPath
								+ '/customer/getInvestIntention.do?intentId='
								+ this.intentId,
						root : 'data',
						preName : ['investIntention','csInvestmentperson']
					});
		}
		if (this.pername != null && this.pername != 'undefined') {
			var perName = Ext.getCmp('investPerName');
			perName.setValue(this.pername);
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
					url : __ctxPath + '/customer/saveInvestIntention.do',
					callback : function(fp, action) {
						var gridPanel = Ext.getCmp('InvestIntentionGrid');
						if (gridPanel != null) {
							gridPanel.getStore().reload();
						}
						this.close();
					}
				});
	}// end of save

});