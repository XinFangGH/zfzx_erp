//EnchashmentApplyForm.js
EnchashmentApplyForm = Ext.extend(Ext.Panel, {
	layout : "form",
	autoHeight : true,
	labelAlign : 'right',
	isAllReadOnly : false,
	isDiligenceReadOnly : false,
	constructor : function(_cfg) {
		if (_cfg == null) {
			_cfg = {};
		}
		if (_cfg.isAllReadOnly) {
			this.isAllReadOnly = _cfg.isAllReadOnly;
			this.isDiligenceReadOnly = true;
		}
		if (_cfg.isDiligenceReadOnly) {
			this.isDiligenceReadOnly = _cfg.isDiligenceReadOnly;
		}
		Ext.applyIf(this, _cfg);
		var leftlabel = 85;
		var centerlabel = 87;
		var rightlabel = 90;
		EnchashmentApplyForm.superclass.constructor.call(this, {
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
							columnWidth : 0.5,
							layout : "form", // 从上往下的布局
							labelWidth : 70,
							labelAlign :'right',
							items:[{
								xtype:'textfield',
								border:false,
								allowBlank : false,
								fieldLabel : "系统账户名",
								blankText :'系统账户名',
								readOnly : this.isReadOnly,
								name:"obSystemAccount.accountName",
								anchor : '100%',
								hidden : this.isHidden
							}]
							
					},{
						columnWidth : .5, // 该列在整行中所占的百分比
						layout : "form", // 从上往下的布局
						labelWidth : 70,
						labelAlign :'right',
						items : [{
							xtype : "textfield",
							name : "obSystemAccount.accountNumber",
							allowBlank : false,
							readOnly : this.isReadOnly,
							fieldLabel : '系统账号',
							blankText :'系统账号不能为空，请正确填写系统账号！',
							anchor : '100%'
							
						}, {
							xtype : "hidden",
							name : "obSystemAccount.id"
							
						}]
					},{
						columnWidth : .47, // 该列在整行中所占的百分比
						layout : "form", // 从上往下的布局
						labelWidth : 70,
						name:"blance",
						labelAlign :'right',
						items : [{
							xtype : "textfield",
							allowBlank : false,
							style : {
								imeMode : 'disabled'
							},
							fieldClass : 'field-align',
							fieldLabel : '可取现金额',
							readOnly : this.isReadOnly,
							name:"availableInvestMoney1",
							blankText :'取现金额不能为空，请正确填写取现金额！',
							anchor : '100%',
							listeners : {
								change : function(nf) {
									var value = nf.getValue();
									var continuationMoneyObj = nf.nextSibling();
									var index = value.indexOf(".");
									if (index <= 0) { // 如果第一次输入 没有进行格式化
										nf.setValue(Ext.util.Format.number(value,'0,000.00'))
										continuationMoneyObj.setValue(value);
									} else {
										if (value.indexOf(",") <= 0) {
											var ke = Ext.util.Format.number(value,'0,000.00')
											nf.setValue(ke);
											continuationMoneyObj.setValue(value);
										} else {
											var last = value.substring(index + 1,value.length);
											if (last == 0) {
												var temp = value.substring(0, index);
												temp = temp.replace(/,/g, "");
												continuationMoneyObj.setValue(temp);
											} else {
												var temp = value.replace(/,/g, "");
												continuationMoneyObj.setValue(temp);
											}
										}
									}
									
								}
							}
						},{
							xtype : "hidden",
							name : "obSystemAccount.availableInvestMoney"
							
						},{
							xtype : "hidden",
							name : "obAccountDealInfo.transferType",
							value:2
							
						},{
							xtype : "hidden",
							name : "obAccountDealInfo.rechargeLevel",
							value:this.rechargeLevel
							
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
				},{
						columnWidth : .47, // 该列在整行中所占的百分比
						layout : "form", // 从上往下的布局
						labelWidth : 70,
						labelAlign :'right',
						items : [{
							xtype : "textfield",
							allowBlank : false,
							style : {
								imeMode : 'disabled'
							},
							fieldClass : 'field-align',
							fieldLabel : '取现金额',
							name:"payMoney1",
							readOnly : this.isAllReadOnly,
							blankText :'取现金额不能为空，请正确填写取现金额！',
							anchor : '100%',
							listeners : {
								change : function(nf) {
									var value = nf.getValue();
									var blance = this.ownerCt.ownerCt.getCmpByName('obSystemAccount.availableInvestMoney').getValue();
									if (eval(value) > eval(blance)) {
										Ext.ux.Toast.msg('操作信息', '取现金额不能大于账户可用余额!');
										nf.setValue(0);
									} else {
										var continuationMoneyObj = nf.nextSibling();
										continuationMoneyObj.setValue(value);
										var index = value.indexOf(".");
										if (index <= 0) { // 如果第一次输入 没有进行格式化
											nf.setValue(Ext.util.Format.number(value,'0,000.00'))
											
										} else {
											if (value.indexOf(",") <= 0) {
												var ke = Ext.util.Format.number(value,
														'0,000.00')
												nf.setValue(ke);
											} else {
												var last = value.substring(index + 1,
														value.length);
												if (last == 0) {
													var temp = value
															.substring(0, index);
													temp = temp.replace(/,/g, "");
												} else {
													var temp = value.replace(/,/g, "");
												}
											}
										}
									}
		
								}
							}
						},{
							xtype : "hidden",
							name : "obAccountDealInfo.payMoney"
							
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
				},{
							columnWidth : .5, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : 70,
							labelAlign :'right',
							items : [{
								xtype : 'datefield',
								fieldLabel : '取现日期',
								allowBlank : false,
								readOnly : this.isAllReadOnly,
								name : "obAccountDealInfo.transferDate",
								anchor : '100%',
								format : 'Y-m-d'
							}]
				
				},{
				            	columnWidth:1,
				                layout: 'form',
				                labelWidth : 70,
				                labelAlign :'right',
				                defaults : {anchor:anchor},
				                items :[{
				                	xtype: 'radiogroup',
					                fieldLabel: '取现方式',
					                anchor : '100%',
					                disabled :this.hiddenRead==false?false:this.isAllReadOnly,
					                items: [{
						                		boxLabel: '现金取现',
						                		name: 'obAccountDealInfo.dealType',
						                		inputValue: "1",
						                		disabled :this.hiddenRead==false?false:this.isAllReadOnly,
						                		checked:true
					                		},{
						                		boxLabel: '银行卡取现',
						                		name: 'obAccountDealInfo.dealType', 
						                		disabled :this.hiddenRead==false?false:this.isAllReadOnly,
						                		inputValue: "2"
					                		},{
						                		boxLabel: '第三方支付',
						                		name: 'obAccountDealInfo.dealType', 
						                		disabled :this.hiddenRead==false?false:this.isAllReadOnly,
						                		inputValue: "3"
					                		}]
				                }]
				            },{
								columnWidth : .5, // 该列在整行中所占的百分比
									layout : "form", // 从上往下的布局
									labelWidth : 70,
									labelAlign :'right',
									items : [{
										fieldLabel : '取现门店',
										readOnly : this.isAllReadOnly,
										name : "obAccountDealInfo.shopName",
										anchor : '100%',
										xtype : 'combo',
										triggerClass : 'x-form-search-trigger',
										hiddenName : 'obAccountDealInfo.shopName',
										onTriggerClick : function() {
											var op = this.ownerCt.ownerCt.ownerCt.ownerCt;
											var EnterpriseNameStockUpdateNew = function(obj) {
												if (null != obj.orgName && "" != obj.orgName)
													op.getCmpByName('obAccountDealInfo.shopName')
															.setValue(obj.orgName);
												if (null != obj.orgId && "" != obj.orgId)
													op.getCmpByName('obAccountDealInfo.shopId')
															.setValue(obj.orgId);
											}
											selectShop(EnterpriseNameStockUpdateNew);
										}
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
										name:'obAccountDealInfo.createName',
										value:curUserInfo.fullname
										/*xtype : "combo",
										triggerClass : 'x-form-search-trigger',
										name : "obAccountDealInfo.createName",
										editable : false,
										fieldLabel : "操作人员",
										blankText : "操作人员不能为空，请正确填写!",
										readOnly : this.isAllReadOnly,
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
												callback : function(uId, uname) {
													obj.setValue(uId);
													obj.setRawValue(uname);
													appuerIdObj.setValue(uId);
												}
											}).show();
						}*/
					},{
								xtype : 'hidden',
								name : 'obAccountDealInfo.createId',
								value:curUserInfo.userId
					}]
				            }]
			}]
		});
	}
});


OnlineEnchashmentBankForm = Ext.extend(Ext.Panel, {
	layout : "form",
	autoHeight : true,
	labelAlign : 'right',
	isAllReadOnly : false,
	isDiligenceReadOnly : false,
	constructor : function(_cfg) {
		if (_cfg == null) {
			_cfg = {};
		}
		if (_cfg.isAllReadOnly) {
			this.isAllReadOnly = _cfg.isAllReadOnly;
			this.isDiligenceReadOnly = true;
		}
		if (_cfg.isDiligenceReadOnly) {
			this.isDiligenceReadOnly = _cfg.isDiligenceReadOnly;
		}
		Ext.applyIf(this, _cfg);
		var leftlabel = 85;
		var centerlabel = 87;
		var rightlabel = 90;
		OnlineEnchashmentBankForm.superclass.constructor.call(this, {
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
							labelAlign :'right',
							items:[{
								xtype:'textfield',
								border:false,
								fieldLabel : "开户银行",
								blankText :'开户银行',
								readOnly : this.isReadOnly,
								name:"webBankcard.bankname",
								anchor : '100%',
								hidden : this.isHidden
							}]
							
					},{
						columnWidth : .5, // 该列在整行中所占的百分比
						layout : "form", // 从上往下的布局
						labelWidth : 70,
						labelAlign :'right',
						items : [{
							xtype : "textfield",
							name : "webBankcard.provinceName",
							readOnly : this.isReadOnly,
							fieldLabel : '开户省份',
							blankText :'系统账号不能为空，请正确填写系统账号！',
							anchor : '100%'
							
						}, {
							xtype : "hidden",
							name : "webBankcard.provinceId"
							
						}]
					},{
						columnWidth : .5, // 该列在整行中所占的百分比
						layout : "form", // 从上往下的布局
						labelWidth : 70,
						labelAlign :'right',
						items : [{
							xtype : "textfield",
							name : "webBankcard.cityName",
							readOnly : this.isReadOnly,
							fieldLabel : '开户城市',
							blankText :'系统账号不能为空，请正确填写系统账号！',
							anchor : '100%'
							
						}, {
							xtype : "hidden",
							name : "webBankcard.cityId"
							
						}]
					},{
					columnWidth : .5,
					layout : "form", // 从上往下的布局
					labelWidth : 70,
					labelAlign : 'right',
					items : [{
						fieldLabel : "分行名称",
		                name : 'webBankcard.branchbank',
						xtype:'textfield',
						readOnly : true,
						anchor : "100%"
					}]
				},{
					columnWidth : .5,
					layout : "form", // 从上往下的布局
					labelWidth : 70,
					labelAlign : 'right',
					items : [{
						fieldLabel : "支行名称",
		                name : 'webBankcard.subbranchbank',
						xtype:'textfield',
						readOnly : true,
						anchor : "100%"
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
								name : "webBankcard.accountname",
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
								name : "webBankcard.cardNum",
								anchor : '100%'
	
							}]
				}]
			}]
		});
	}
});

EnchashmentBankForm = Ext.extend(Ext.Panel, {
	layout : "form",
	autoHeight : true,
	labelAlign : 'right',
	isAllReadOnly : false,
	isDiligenceReadOnly : false,
	constructor : function(_cfg) {
		if (_cfg == null) {
			_cfg = {};
		}
		if (_cfg.isAllReadOnly) {
			this.isAllReadOnly = _cfg.isAllReadOnly;
			this.isDiligenceReadOnly = true;
		}
		if (_cfg.isDiligenceReadOnly) {
			this.isDiligenceReadOnly = _cfg.isDiligenceReadOnly;
		}
		Ext.applyIf(this, _cfg);
		var leftlabel = 85;
		var centerlabel = 87;
		var rightlabel = 90;
		EnchashmentBankForm.superclass.constructor.call(this, {
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
						anchor : "100%",
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
						anchor : "100%"
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
								anchor : '100%'
	
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
									id:this.investPersonId,
									gridPanel:this.gridPanel,
									type:2
								}).show();
						}
					}]
										
			}]
			}]
		});
	}
});