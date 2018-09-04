applyRechargeWindow = Ext.extend(Ext.Window, {
	isLook : false,
	isRead : false,
	isflag : false,
	// 构造函数
	investPersonPanel : null,
	constructor : function(_cfg) {
		if (_cfg == null) {
			_cfg = {};
		};
		if (typeof(_cfg.isReadOnly) != "undefined") {
			this.isRead = _cfg.isReadOnly;
		};
		if (null != _cfg.personData) {
			this.isflag = true;
		};
		if (typeof(_cfg.isLook) != "undefined") {
			this.isLook = _cfg.isLook;
		}
		Ext.applyIf(this, _cfg);
		/*alert("this.personData==="+this.personData)
		var personData = this.personData;*/
		this.initUIComponents();
		applyRechargeWindow.superclass.constructor.call(this, {
					id : 'applyRechargeWindow',
					layout : 'form',
					items : [{
								xtype : 'fieldset',
								title : '投资人基本信息',
								collapsible : true,
								autoHeight : true,
								bodyStyle : 'padding-left: 0px',
								items : [this.persoormPanel]
							},{
								xtype : 'fieldset',
								title : '贷款账户信息',
								collapsible : true,
								autoHeight : true,
								bodyStyle : 'padding-left: 0px',
								items : [this.accountPanel]
							}, {
								xtype : 'fieldset',
								title : '计划',
								collapsible : true,
								autoHeight : true,
								bodyStyle : 'padding-left: 0px',
								items : [this.formPanel]
							}],
					modal : true,
					autoHeight : true,
					width : 1000,
					maximizable : true,
					title : '申请充值',
					buttonAlign : 'center',
					buttons : [{
								text : '提交申请',
								iconCls : 'btn-save',
								hidden : this.isLook,
								scope : this,
								handler : this.save
							}, {
								text : '重置',
								iconCls : 'btn-reset',
								hidden : this.isLook,
								scope : this,
								handler : this.reset
							}, {
								text : '取消',
								iconCls : 'btn-cancel',
								hidden : this.isLook,
								scope : this,
								handler : this.cancel
							}]
				});
	},// end of the constructor
	// 初始化组件
	initUIComponents : function() {
		this.persoormPanel = new Ext.form.FormPanel({
			autoHeight : true,
			autoWidth : true,
			layout : 'column',
			bodyStyle : 'padding:10px',
			border : false,
			autoScroll : true,
			frame : true,
			labelAlign : 'right',
			defaults : {
				anchor : '96%',
				labelWidth : 60
			},
			items : [{
						columnWidth : 0.5,
						layout : "form", // 从上往下的布局
						labelWidth : 70,
						labelAlign : 'right',
						items : [{
									xtype : 'textfield',
									fieldLabel : '<font color=red>*</font>姓名',
									name : 'csInvestmentPerson.investName',
									blankText : '姓名为必填内容',
									anchor : '100%',
									readOnly : this.isReadOnly,
									listeners : {
										'afterrender' : function(com) {
											com.clearInvalid();
										}
									}
								}]

					}, {
						columnWidth : .5, // 该列在整行中所占的百分比
						layout : "form", // 从上往下的布局
						labelWidth : 70,
						labelAlign : 'right',
						items : [{
							xtype : "dickeycombo",
							nodeKey : 'sex_key',
							hiddenName : 'csInvestmentPerson.sex',
							fieldLabel : "性别",
							allowBlank : false,
							anchor : '97.3%',
							editable : true,
							blankText : "性别不能为空，请正确填写!",
							readOnly : this.isReadOnly,
							//value : personData == null ? null : personData.sex,
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
						}, {
							xtype : "hidden",
							name : "csInvestmentPerson.investPersonId",
							value : this.investPeronId
						}]
					}, {
						columnWidth : .5, // 该列在整行中所占的百分比
						layout : "form", // 从上往下的布局
						labelWidth : 70,
						labelAlign : 'right',
						items : [{
							xtype : "dickeycombo",
							nodeKey : 'card_type_key',
							hiddenName : "csInvestmentPerson.cardtype",
							itemName : '证件类型', // xx代表分类名称
							fieldLabel : "证件类型",
							allowBlank : false,
							editable : true,
							readOnly : this.isReadOnly,
							anchor : '100%',
							// emptyText : "请选择",
							blankText : "证件类型不能为空，请正确填写!",
							listeners : {
								scope : this,
								afterrender : function(combox) {
									var st = combox.getStore();
									st.on("load", function() {
										if (combox.getValue() == ''
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
						}]
					}, {
						columnWidth : .5, // 该列在整行中所占的百分比
						layout : "form", // 从上往下的布局
						labelWidth : 70,
						labelAlign : 'right',
						items : [{
									id : 'cardnumber',
									xtype : 'textfield',
									fieldLabel : '证件号码',
									name : 'csInvestmentPerson.cardnumber',
									allowBlank : false,
									anchor : '97.3%',
									blankText : '证件号码为必填内容',
									readOnly : this.isReadOnly
								}]

					}, {
						columnWidth : 0.5,
						layout : 'form',
						labelWidth : 70,
						labelAlign : 'right',
						items : [{
									xtype : 'textfield',
									fieldLabel : '电话号码',
									name : 'csInvestmentPerson.cellphone',
									allowBlank : false,
									readOnly : this.isReadOnly,
									anchor : '100%',
									regex : /^[1][34578][0-9]{9}$/,
									regexText : '手机号码格式不正确',
									listeners : {
										'afterrender' : function(com) {
											com.clearInvalid();
										}
									}

								}]
					}, {
						columnWidth : .5, // 该列在整行中所占的百分比
						layout : "form", // 从上往下的布局
						labelWidth : 70,
						labelAlign : 'right',
						items : [{
							xtype : 'textfield',
							fieldLabel : '电子邮箱',
							name : 'csInvestmentPerson.selfemail',
							readOnly : this.isReadOnly,
							anchor : '97.3%',
							regex : /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+/,
							regexText : '电子邮箱格式不正确',
							listeners : {
								'afterrender' : function(com) {
									com.clearInvalid();
								}
							}

						}]
					}, {
						columnWidth : 1,
						layout : 'form',
						labelWidth : 70,
						labelAlign : 'right',
						defaults : {
							anchor : anchor
						},
						items : [{
									xtype : 'textfield',
									fieldLabel : '通讯地址',
									allowBlank : false,
									readOnly : this.isReadOnly,
									anchor : '98.5%',
									name : 'csInvestmentPerson.postaddress'
								}]
					}]
		});
		var leftlabel = 85;
		var centerlabel = 87;
		var rightlabel = 90;
		this.accountPanel = new Ext.form.FormPanel({
			autoHeight : true,
			autoWidth : true,
			layout : 'column',
			bodyStyle : 'padding:10px',
			border : false,
			autoScroll : true,
			frame : true,
			labelAlign : 'right',
			defaults : {
				anchor : '96%',
				labelWidth : 60
			},
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
				items : [{
					columnWidth : 1,
					layout : "form", // 从上往下的布局
					labelWidth : 70,
					labelAlign : 'right',
					items : [{
						fieldLabel : "开户行",
						xtype : "combo",
						displayField : 'itemName',
						valueField : 'itemId',
						allowBlank : false,
						readOnly : true,
						triggerAction : 'all',
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
						anchor : "98.5%",
						listeners : {
							scope : this,
							afterrender : function(combox) {
								var st = combox.getStore();
								st.on("load", function() {
											combox.setValue(combox.getValue());
	
										})
								combox.clearInvalid();
							}
	
						}
					}]
	
				},{
					columnWidth : .5,
					layout : "form", // 从上往下的布局
					labelWidth : 70,
					labelAlign : 'right',
					items : [{
							xtype : "hidden",
							name : "enterpriseBank.areaId"
							
						},{					
							name : 'enterpriseBank.areaName',
							fieldLabel : '开户地区',	
							anchor : '100%',
							readOnly : true,
							allowBlank:false,
							xtype:'combo',
							triggerClass :'x-form-search-trigger',
							editable : false,
							scope : this,
							onTriggerClick : function(){
								var com=this
								var selectBankLinkMan = function(array){
										var str="";
										var idStr=""
										for(var i=array.length-1;i>=0;i--){
												str=str+array[i].text+"-"
												idStr=idStr+array[i].id+","
										}
										if(str!=""){
												str=str.substring(0,str.length-1);
										}
										if(idStr!=""){
												idStr=idStr.substring(0,idStr.length-1)
										}
										com.previousSibling().setValue(idStr)
										com.setValue(str);
								};
								selectDictionary('area',selectBankLinkMan);
							}
					}]
				},{
					columnWidth : .5,
					layout : "form", // 从上往下的布局
					labelWidth : 70,
					labelAlign : 'right',
					items : [{
						fieldLabel : "网点名称",
		                name : 'enterpriseBank.bankOutletsName',
						xtype:'textfield',
						allowBlank:false,
						readOnly : true,
						anchor : "97.3%"
					}]
				}, {
					columnWidth : 0.5,
					layout : "form", // 从上往下的布局
					labelWidth : 70,
					labelAlign : 'right',
					items : [{
								xtype : 'textfield',
								border : false,
								readOnly : true,
								fieldLabel : "账户名",
								blankText : '账户名',
								name : "enterpriseBank.name",
								anchor : '100%',
								hidden : this.isHidden
							}]
	
				},  {
					columnWidth : .5, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : 70,
					labelAlign : 'right',
					items : [{
								xtype : "textfield",
								name : "accountNumber",
								readOnly : true,
								fieldLabel : '账号',
								name : "enterpriseBank.accountnum",
								anchor : '97.3%'
	
							}]
				},{
				
					columnWidth : .5, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : 70,
					labelAlign : 'right',
					items : [{ // 上面是第三行
						xtype : 'button',
						text : '编辑账户信息',
						iconCls : 'btn-customer',
						width : 110,
						scope : this,
						handler : function() {
								new CustomerBank({
								id:this.investPeronId,
								gridPanel:this.accountPanel
								}).show();
						}
					}]
										
			}]
			}]
		});

		this.formPanel = new Ext.form.FormPanel({
			autoHeight : true,
			autoWidth : true,
			layout : 'column',
			bodyStyle : 'padding:10px',
			border : false,
			autoScroll : true,
			frame : true,
			labelAlign : 'right',
			defaults : {
				anchor : '96%',
				labelWidth : 60
			},
			items : [{
						columnWidth : 0.5,
						layout : "form", // 从上往下的布局
						labelWidth : 70,
						labelAlign : 'right',
						items : [{
									xtype : 'textfield',
									border : false,
									allowBlank : false,
									fieldLabel : "系统账户名",
									blankText : '系统账户名',
									readOnly : this.isReadOnly,
									name : "systemAccountName",
									value : this.systemAccountName,
									anchor : '94%',
									hidden : this.isHidden
								}]

					}, {
						columnWidth : .5, // 该列在整行中所占的百分比
						layout : "form", // 从上往下的布局
						labelWidth : 70,
						labelAlign : 'right',
						items : [{
									xtype : "textfield",
									name : "accountNumber",
									allowBlank : false,
									readOnly : this.isReadOnly,
									fieldLabel : '系统账号',
									name : "systemAccountNumber",
									value : this.systemAccountNumber,
									blankText : '系统账号不能为空，请正确填写系统账号！',
									anchor : '97.3%'

								}, {
									xtype : "hidden",
									name : "obAccountDealInfo.accountId",
									value : this.accountId
								}]
					}, {
						columnWidth : .47, // 该列在整行中所占的百分比
						layout : "form", // 从上往下的布局
						labelWidth : 70,
						labelAlign : 'right',
						items : [{
							xtype : "textfield",
							name : "prospectusMoney",
							allowBlank : false,
							style : {
								imeMode : 'disabled'
							},
							fieldClass : 'field-align',
							fieldLabel : '充值金额',
							name : "incomMoney",
							blankText : '充值金额不能为空，请正确填写充值金额！',
							anchor : '100%',
							listeners : {
								change : function(nf) {
									var value = nf.getValue();
									var continuationMoneyObj = nf.nextSibling();
									var index = value.indexOf(".");
									if (index <= 0) { // 如果第一次输入 没有进行格式化
										nf.setValue(Ext.util.Format.number(
												value, '0,000.00'))
										continuationMoneyObj.setValue(value);
									} else {
										if (value.indexOf(",") <= 0) {
											var ke = Ext.util.Format.number(
													value, '0,000.00')
											nf.setValue(ke);
											continuationMoneyObj
													.setValue(value);
										} else {
											var last = value.substring(index
															+ 1, value.length);
											if (last == 0) {
												var temp = value.substring(0,
														index);
												temp = temp.replace(/,/g, "");
												continuationMoneyObj
														.setValue(temp);
											} else {
												var temp = value.replace(/,/g,
														"");
												continuationMoneyObj
														.setValue(temp);
											}
										}
									}

								}
							}
						}, {
							xtype : "hidden",
							name : "obAccountDealInfo.incomMoney"

						}, {
							xtype : "hidden",
							name : "obAccountDealInfo.investPersonId",
							value : this.investPeronId
						}, {
							xtype : "hidden",
							name : "obAccountDealInfo.transferType",
							value : 1

						}, {
							xtype : "hidden",
							name : "obAccountDealInfo.rechargeLevel",
							value : this.rechargeLevel

						}]
					}, {
						columnWidth : .03, // 该列在整行中所占的百分比
						layout : "form", // 从上往下的布局
						labelWidth : 20,
						items : [{
									fieldLabel : "元 ",
									labelSeparator : '',
									anchor : "90%"
								}]
					}, {
						columnWidth : .5, // 该列在整行中所占的百分比
						layout : "form", // 从上往下的布局
						labelWidth : 70,
						labelAlign : 'right',
						items : [{
									xtype : 'datefield',
									fieldLabel : '充值日期',
									allowBlank : false,
									//readOnly : this.isAllReadOnly,
									name : "obAccountDealInfo.transferDate",
									anchor : '97.3%',
									format : 'Y-m-d'
								}]

					}, {
						columnWidth : 1,
						layout : 'form',
						labelWidth : 70,
						labelAlign : 'right',
						defaults : {
							anchor : anchor
						},
						items : [{
									xtype : 'radiogroup',
									fieldLabel : '充值方式',
									anchor : '100%',
									items : [{
												boxLabel : '现金充值',
												name : 'obAccountDealInfo.dealType',
												inputValue : "1",
												checked : true
											}, {
												boxLabel : '银行卡充值',
												name : 'obAccountDealInfo.dealType',
												inputValue : "2"
											}]
								}]
					}, {
						columnWidth : .5, // 该列在整行中所占的百分比
						layout : "form", // 从上往下的布局
						labelWidth : 70,
						labelAlign : 'right',
						items : [{
							fieldLabel : '充值门店',
							allowBlank : false,
							//readOnly : true,
							name : "obAccountDealInfo.shopName",
							anchor : '94%',
							xtype : 'trigger',
							triggerClass : 'x-form-search-trigger',
							value : personData == null
									? null
									: personData.shopName,
							hiddenName : 'obAccountDealInfo.shopName',
							onTriggerClick : function() {
								var op = this.ownerCt.ownerCt.ownerCt.ownerCt;
								var EnterpriseNameStockUpdateNew = function(obj) {
									if (null != obj.orgName
											&& "" != obj.orgName)
										op
												.getCmpByName('obAccountDealInfo.shopName')
												.setValue(obj.orgName);
									if (null != obj.orgId && "" != obj.orgId)
										op
												.getCmpByName('obAccountDealInfo.shopId')
												.setValue(obj.orgId);
								}
								selectShop(EnterpriseNameStockUpdateNew);
							}
						}]
					}, {
						xtype : 'hidden',
						name : 'obAccountDealInfo.shopId'
					}, {
						columnWidth : .5,
						layout : 'form',
						labelWidth : 70,
						labelAlign : 'right',
						defaults : {
							anchor : anchor
						},
						items : [{
							
				            	columnWidth:.5,
				                layout: 'form',
				                labelWidth : 70,
				                labelAlign :'right',
				                defaults : {anchor:anchor},
				                items :[{
				                		xtype : "textfield",
										fieldLabel : "操作人员",
										blankText : "操作人员不能为空，请正确填写!",
										allowBlank : false,
										anchor : "100%",
										readOnly:true,
										name:'obAccountDealInfo.createName',
										value:curUserInfo.fullname
					},{
								xtype : 'hidden',
								name : 'obAccountDealInfo.createId',
								value:curUserInfo.userId
					}]
				            
							
							/*
									xtype : "combo",
									triggerClass : 'x-form-search-trigger',
									hiddenName : "degree",
									editable : false,
									fieldLabel : "操作人员",
									blankText : "操作人员不能为空，请正确填写!",
									allowBlank : false,
									anchor : "100%",
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
													title : "选择操作人员",
													callback : function(uId,
															uname) {
														obj.setValue(uId);
														obj.setRawValue(uname);
														appuerIdObj
																.setValue(uId);
													}
												}).show();
									}
								*/}, {
									xtype : "hidden",
									value : ""
								}]
									}]
								},{
								xtype : 'hidden',
								name : 'obAccountDealInfo.shopId'
							},{
				            	columnWidth:.5,
				                layout: 'form',
				                labelWidth : 70,
				                labelAlign :'right',
				                defaults : {anchor:anchor},
				                items :[{
										xtype : "textfield",
										//triggerClass : 'x-form-search-trigger',
										//hiddenName : "degree",
									//	editable : false,
										fieldLabel : "操作人员",
										blankText : "操作人员不能为空，请正确填写!",
										allowBlank : false,
										anchor : "100%",
										readOnly:true,
										value:curUserInfo.fullname
										/*onTriggerClick : function(cc) {
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
												title : "选择操作人员",
												callback : function(uId, uname) {
													obj.setValue(uId);
													obj.setRawValue(uname);
													appuerIdObj.setValue(uId);
												}
											}).show();
						}*/
					}, {
						xtype : "hidden",
						value : ""
					},{
						xtype:"hidden",
						name:'degree',
						value:curUserInfo.userId
					}]
		});
		// this.gridPanel.addListener('rowdblclick', this.rowClick);
		// 加载表单对应的数据
		if (this.investPeronId != null && this.investPeronId != 'undefined') {
			this.persoormPanel.loadData({
				url : __ctxPath
						+ '/creditFlow/creditAssignment/customer/getCsInvestmentperson.do?investId='
						+ this.investPeronId,
				root : 'data',
				preName : 'csInvestmentPerson'
			});
			this.accountPanel.loadData({
				url : __ctxPath
						+ '/creditFlow/customer/common/getAccountBankEnterpriseBank.do?id='
						+ this.investPeronId,
				root : 'data',
				preName : 'enterpriseBank'
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
		var investpersonName = this
				.getCmpByName("csInvestmentPerson.investName").getValue();
		$postForm({
			formPanel : this.formPanel,
			scope : this,
			url : __ctxPath
					+ '/creditFlow/creditAssignment/bank/saveDealInfoObAccountDealInfo.do',
			params : {
				'investpersonName' : investpersonName
			},
			callback : function(fp, action) {
				alert(action.result.msg);
				this.close();
			}
		});

	}// end of save
});