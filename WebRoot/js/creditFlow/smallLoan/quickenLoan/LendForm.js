
LendForm = Ext.extend(Ext.Panel ,{
	layout : "form",
	autoHeight : true,
	isAllReadOnly : this.isAllReadOnly,
	disALLabled : this.disALLabled,
	isDiligenceReadOnly : true,
	dltcCustom:false,
	constructor:function(_cfg){
		if (_cfg == null) {
			_cfg = {};
		}
		if (_cfg.isAllReadOnly) {
			this.isAllReadOnly = _cfg.isAllReadOnly;
			this.isDiligenceReadOnly=false;
		}
		if (_cfg.isDiligenceReadOnly) {
			this.isDiligenceReadOnly = _cfg.isDiligenceReadOnly;
		}
		if (_cfg.dltcCustom) {
			this.dltcCustom = _cfg.dltcCustom;
		}else if(App.isCustomized4DLTC()){
			this.dltcCustom = true;
		}
		Ext.applyIf(this,_cfg);
		this.initUIComponents();
		LendForm.superclass.constructor.call(this,{
			items : [{
				layout : "column",
				border : false,
				scope : this,
				defaults : {
					anchor : '100%',
					columnWidth : 1,
					isFormField : true,
					labelWidth : 100
				},
				items : [{
							xtype : 'hidden',
							name : 'bpMoneyBorrowDemand.borrowid'	,
								onTriggerClick : function() {
									var op = this.ownerCt.ownerCt;
									var selectCustomer = function(obj) {
										op.getCmpByName('bpMoneyBorrowDemand.coment1')
												.setValue("");
									op.getCmpByName('bpMoneyBorrowDemand.borrowid')
									.setValue("");
									op.getCmpByName('bpMoneyBorrowDemand.quotaApplicationSmall')
									.setValue("");
									op.getCmpByName('bpMoneyBorrowDemand.quotaApplicationBig')
									.setValue("");
									op.getCmpByName('bpMoneyBorrowDemand.familyKnow')
									.setValue("");
									op.getCmpByName('bpMoneyBorrowDemand.longestRepaymentPeriod')
									.setValue("");
									op.getCmpByName('bpMoneyBorrowDemand.repaymentAmount')
									.setValue("");
									op.getCmpByName('bpMoneyBorrowDemand.projectID')
									.setValue("");
											
									if (obj.borrowid != 0 && obj.borrowid != "")
										op.getCmpByName('pbpMoneyBorrowDemand.borrowid').setValue(obj.borrowid);
									if (obj.coment1 != 0 && obj.coment1 != "")
										op.getCmpByName('pbpMoneyBorrowDemand.coment1').setValue(obj.coment1);
									if (obj.quotaApplicationSmall != 0 && obj.quotaApplicationSmall != "")
										op.getCmpByName('pbpMoneyBorrowDemand.quotaApplicationSmall').setValue(obj.quotaApplicationSmall);
									if (obj.quotaApplicationBig != 0 && obj.quotaApplicationBig != "")
										op.getCmpByName('pbpMoneyBorrowDemand.quotaApplicationBig').setValue(obj.quotaApplicationBig);
									if (obj.familyKnow != 0 && obj.familyKnow != "")
										op.getCmpByName('pbpMoneyBorrowDemand.familyKnow').setValue(obj.familyKnow);
									if (obj.longestRepaymentPeriod != 0 && obj.longestRepaymentPeriod != "")
										op.getCmpByName('pbpMoneyBorrowDemand.longestRepaymentPeriod').setValue(obj.longestRepaymentPeriod);
									if (obj.repaymentAmount != 0 && obj.repaymentAmount != "")
										op.getCmpByName('pbpMoneyBorrowDemand.repaymentAmount').setValue(obj.repaymentAmount);
									if (obj.projectID != 0 && obj.projectID != "")
										op.getCmpByName('pbpMoneyBorrowDemand.borrowid').setValue(obj.projectID);
									}
								}
						},{
							xtype : 'hidden',
							name : 'bpMoneyBorrowDemand.projectID'
						},/*{
							xtype : 'hidden',
							name : 'slSmallloanProject.projectMoney',
							value:0
						},*/{
							columnWidth : 1,
							layout : 'column',
							items :[{
							columnWidth : .3,
							layout : 'form',
							defaults : {
								anchor : '100%'
							},
							labelWidth : 85,
							items : [/*this.dltcCustom?{//大连天储要求手动录入，考虑到purposeType为long类型，以及合同要素的问题，新添加一个字段
								fieldLabel : "贷款用途",
								xtype : "textfield",
								readOnly : this.isAllReadOnly,
								name : 'slSmallloanProject.purposeTypeStr',
								emptyText : "请选择",
								anchor : "100%",
								allowBlank : false
							}:*/{
								fieldLabel : "贷款用途",
								xtype : "dickeycombo",
								hiddenName : 'slSmallloanProject.purposeType',
								displayField : 'itemName',
								readOnly : this.isAllReadOnly,
								itemName : '贷款用途',
								nodeKey : 'smallloan_purposeType',	
								emptyText : "请选择",
								editable : false,
								anchor : "100%",
								allowBlank : false,
								//value:(this.memObj==null?'':this.appUser.loanUse),
													listeners : {
														scope : this,
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
						},{
							columnWidth : .3,
							layout : 'form',
							defaults : {
								anchor : '100%'
							},
							labelWidth :  85+30,
							items : [ {
									fieldLabel : '最高月还款金额',
									xtype : 'numberfield',
									readOnly :this.isDiligenceReadOnly,
									readOnly : this.isAllReadOnly,
									allowBlank : true,
									anchor:'100%',
									name : 'bpMoneyBorrowDemand.repaymentAmount',
									maxLength : 50
							}]
						},{
							columnWidth : .03, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : 22,
							items : [{
								fieldLabel : "<span style='margin-left:1px'>元</span> ",
								labelSeparator : '',
								anchor : "50%"
							}]
						},{
							columnWidth : .37,
							layout : 'form',
							labelWidth : 87,
							labelAlign : 'right',
							items :[{
								xtype : 'radiogroup',
								fieldLabel : '联系人是否知晓',
								disabled :this.isAllReadOnly,
								name : 'linkmanKnownObj',
								items : [{
									boxLabel : '是',
									name : 'linkman',
									inputValue : 1
								}, {
									boxLabel : '否',
									name : 'linkman',
									inputValue : 0
								}],
								listeners : {
									scope : this,
									change  : function(rg,r){
										var linkmanKnown=this.getCmpByName('bpMoneyBorrowDemand.linkmanKnown')
										linkmanKnown.setValue(r.inputValue)
									}
								}
							},{
								xtype : 'hidden',
								name : 'bpMoneyBorrowDemand.linkmanKnown',
								listeners : {
									scope : this,
									loadData : function(com){
										 var linkmanKnown=this.getCmpByName('bpMoneyBorrowDemand.linkmanKnown').getValue()
										 var linkmanKnownObj=this.getCmpByName('linkmanKnownObj')
										 if(linkmanKnown==1){
										 	linkmanKnownObj.items[0].checked=true
										 	linkmanKnownObj.items[1].checked=false
										 }else{
										 	linkmanKnownObj.items[0].checked=false
										 	linkmanKnownObj.items[1].checked=true
										 }
									}
								}
							}]
						}]
						}/*,{
							columnWidth : .33,
							layout : 'form',
							defaults : {
								anchor : '100%'
							},
							labelWidth :  App.isCustomized4DLTC()?85+20:85,
							items : [ App.isCustomized4DLTC()?{
								fieldLabel : '联系人是否知晓',
								xtype : 'textfield',
								readOnly: this.isAllReadOnly,
								allowBlank : true,
								anchor:'100%',
								name : 'bpMoneyBorrowDemand.linkmanKnown'
							}:{
								fieldLabel : '家人是否知晓',
								xtype : 'radiogroup',
//								disabled : this.isAllReadOnly,
								allowBlank : true,
								anchor:'100%',
								name : 'bpMoneyBorrowDemand.familyKnow',
								items:[//如果setDisable了，那么提交时就不会向后台传值，如果后台有保存方法，那么此时的familyKnow为null，再没有使用copyNotNullProperties的话，那么原来保存的值就会被null覆盖，所以
								//改为如果radio被选中，那么disabled = false，如果没被选中  disabled true(当isAllReadOnly 为true的时候)
									{xtype:'radio',boxLabel:'是',name:'bpMoneyBorrowDemand.familyKnow',inputValue:1,listeners:{scope:this,loadData:function(com){
										var val = com.getValue();
										if(val==false&&this.isAllReadOnly){
											com.setDisabled(true);
										}
									}}},
									{boxLabel:'否',name:'bpMoneyBorrowDemand.familyKnow',inputValue:0,listeners:{scope:this,loadData:function(com){
										var val = com.getValue();
										if(val==false&&this.isAllReadOnly){
											com.setDisabled(true);
										}
									}}}
								]
							}]
						}*/,{
							columnWidth : .3,
							layout : 'form',
							defaults : {
								anchor : '100%'
							},
							labelWidth : 85,
							items : [{
								fieldLabel : '申请额度',
								readOnly :this.isDiligenceReadOnly,
								readOnly : this.isAllReadOnly,
								allowBlank : false,
								xtype : 'numberfield',
								anchor:'100%',
								name : 'bpMoneyBorrowDemand.quotaApplicationSmall'
							}]
						},{
							columnWidth : .03, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : 22,
							items : [{
								fieldLabel : "<span style='margin-left:1px'>元</span> ",
								labelSeparator : '',
								anchor : "100%"
							}]
						},{
							columnWidth : .27,
							layout : 'form',
							defaults : {
								anchor : '90%'
							},
							labelWidth :  82,
							items : [{
								fieldLabel:'至',
								anchor:'100%',
								readOnly :this.isDiligenceReadOnly,
								readOnly : this.isAllReadOnly,
								allowBlank : false,
								name : 'bpMoneyBorrowDemand.quotaApplicationBig',
								xtype : 'numberfield'
							}]
						},{
							columnWidth : .03, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : 22,
							items : [{
								fieldLabel : "<span style='margin-left:1px'>元</span> ",
								labelSeparator : '',
								anchor : "100%"
							}]
						},{
							columnWidth : .34,
							layout : 'form',
							defaults : {
								anchor : '100%'
							},
							labelWidth : 85,
							items : [{
								fieldLabel :  App.isCustomized4DLTC()?'贷款期限':'最长还款期限',
								readOnly : this.isAllReadOnly,
								allowBlank :this.dltcCustom?true:false,
								xtype : 'numberfield',
								anchor:'100%',
								name : 'bpMoneyBorrowDemand.longestRepaymentPeriod'
							}]
						},{
							columnWidth : .03, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : 22,
							items : [{
								fieldLabel : "<span style='margin-left:1px'>月</span> ",
								labelSeparator : '',
								anchor : "100%"
							}]
						},{
							columnWidth : .33,
							layout : 'form',
							labelWidth : 84,
							items : [{
								fieldLabel : '期望到位日期',
								readOnly :this.isDiligenceReadOnly,
								readOnly : this.isAllReadOnly,
								allowBlank : false,
								xtype : 'datefield',
								style : {
									imeMode : 'disabled'
								},
								format : 'Y-m-d',
								anchor:'91%',
								name : 'bpMoneyBorrowDemand.startDate'
							}]
						},{
							columnWidth : .27,
							layout : 'form',
							defaults : {
								anchor : '90%'
							},
							labelWidth :  82,
							items : [{
								fieldLabel: App.isCustomized4DLTC()?'贷款利率':'期望利率',
								anchor:'100%',
								readOnly :this.isDiligenceReadOnly,
								readOnly : this.isAllReadOnly,
								allowBlank :this.dltcCustom?true:false,
								name : 'bpMoneyBorrowDemand.accrual',
								xtype : 'numberfield'
							}]
					    },{
							columnWidth : .03, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth :35,
							items : [{
								fieldLabel : "%/月",
								labelSeparator : '',
								anchor : "100%"
							}]
						},{
							columnWidth : .37,
							layout : 'form',
							labelWidth : 85,
							items : [{
								fieldLabel : App.isCustomized4DLTC()?"贷款方式":"主担保方式",
								xtype : "dickeycombo",
								hiddenName : 'bpMoneyBorrowDemand.assuretypeid',
								displayField : 'itemName',
								readOnly : this.isAllReadOnly,
								nodeKey : 'zdbfs',
								emptyText : "请选择",
								editable :false,
								anchor : "92%",
								allowBlank:false,
								listeners : {
									afterrender : function(combox) {
										var st = combox.getStore();
										st.on("load", function() {
											combox.setValue(combox.getValue());
											combox.clearInvalid();
										})
									}
		
								}
							}]
						},{
							columnWidth : .3, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : 85,
							labelAlign : 'right',
							items : [{
								fieldLabel : "客户来源",
								xtype : "dickeycombo",
								hiddenName : 'slSmallloanProject.customerChannel',
								displayField : 'itemName',
								readOnly : this.isAllReadOnly,
								nodeKey : 'customer_channel',
								emptyText : "请选择",
								editable : false,
								anchor : "100%",
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
						},{
							columnWidth : 1,
							layout : 'form',
							defaults : {
								anchor : '100%'
							},
							labelWidth : 85,
							items : [{
								fieldLabel : "备注",
								xtype : "textarea",
								name : 'bpMoneyBorrowDemand.remark',
								readOnly : this.isAllReadOnly,
								anchor : "97%"
							}]
						}]
				}]
		});
	},
	initUIComponents:function(){
	}
	
});