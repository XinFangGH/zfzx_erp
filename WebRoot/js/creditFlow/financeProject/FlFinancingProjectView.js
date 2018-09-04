
/**
 * 更多项目信息表（panel形式）
 * 
 * @class ExtUD.Ext.ProjectInfoMoreSet
 * @extends Ext.form.FieldSet
 */
FlFinancingProjectView = Ext.extend(Ext.Panel, {
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
		this.initComponents();
		var leftlabel = 85;
		var centerlabel = 95;
		var rightlabel = 90;
		FlFinancingProjectView.superclass.constructor.call(this, {
			layout : "form",
			labelAlign : 'right',
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
							xtype : 'hidden',
							name : 'flFinancingProject.projectId'
						}, {
							xtype : 'hidden',
							name : 'businessType'
						}, {
							columnWidth : .6, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : leftlabel,
							defaults : {
								anchor : '100%'
							},
							items : [{
										fieldLabel : "项目名称",
										xtype : "textfield",
										readOnly : this.isAllReadOnly,
										readOnly : this.isDiligenceReadOnly,
										name : "flFinancingProject.projectName",
										blankText : "项目名称不能为空，请正确填写!",
										allowBlank : false
									}]
						}, {
							columnWidth : .4, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : rightlabel,
							defaults : {
								anchor : '100%'
							},
							items : [{
										fieldLabel : "项目编号",
										xtype : "textfield",
										name : "flFinancingProject.projectNumber",
										allowBlank : false,
										readOnly : this.isAllReadOnly,
										readOnly : this.isDiligenceReadOnly,
										blankText : "项目编号不能为空，请正确填写!"
									}]
						} ,{
					columnWidth : .3, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth :leftlabel,
					defaults : {
							anchor : '100%'
						},
					items : [{
						xtype : "hidden",
						name : "flFinancingProject.operationType"
					},{
						xtype : "textfield",
						anchor : "100%",
						readOnly : this.isAllReadOnly,
						readOnly : this.isDiligenceReadOnly,
						name : "businessTypeKey",
						fieldLabel : "业务类别"
						/*xtype : "combo",
						anchor : "100%",
						hiddenName : "flFinancingProject.businessType",
						displayField : 'itemName',
					    valueField : 'itemId',
					    triggerAction : 'all',
					    readOnly : this.isAllReadOnly,
						readOnly : this.isDiligenceReadOnly,
					    store : new Ext.data.SimpleStore({
							autoLoad : true,
							baseParams : {
								parentId : slbusinesstype
							},
							url : __ctxPath+ '/system/getByParentIdDicAreaDynam.do',
							fields : ['itemId', 'itemName']
						}),
						fieldLabel : "业务类别",
//						allowBlank : false,
						blankText : "业务类别不能为空，请正确填写!",
						emptyText : "请选择",
					    listeners : {
							afterrender : function(combox) {
								var st = combox.getStore();
								st.on("load", function() {
									combox.setValue(combox.getValue());
									combox.clearInvalid();
								})
							}

						}*/
					}]
				}, {
							columnWidth : .3, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : centerlabel,
							defaults : {
								anchor : '100%'
							},
							items : [{
								fieldLabel : "业务品种",
								xtype : "textfield",
								name : "operationTypeKey",
								readOnly : this.isAllReadOnly,
								readOnly : this.isDiligenceReadOnly
								/*fieldLabel : "业务品种",
								xtype : "smdiccombo",
								itemValue : "6583",
								mode : 'local',
								hiddenName : "flFinancingProject.operationType",
								cid : 'childxilaid_liucheng',
								store : new Ext.data.SimpleStore({
											fields : ['displayText', 'value'],
											data : [['地区', 1], ['LAC', 2],
													['CI', 3], ['被叫用户', 4]]
										}),
								readOnly : this.isAllReadOnly,
								readOnly : this.isDiligenceReadOnly,
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

								}*/
							}]
						}, {
							columnWidth : .4, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : rightlabel,
							defaults : {
								anchor : '100%'
							},
							items : [{
								fieldLabel : "流程类别",
								xtype : "textfield",
								name:"flowTypeKey",
								readOnly : this.isAllReadOnly,
								readOnly : this.isDiligenceReadOnly,
								anchor : "100%"
								/*fieldLabel : "流程类别",
								xtype : "smdiccombo",
								itemValue : "6575",
								mode : 'local',
								displayField : 'itemName',// 显示字段值
								valueField : 'itemId',
								readOnly : this.isAllReadOnly,
								readOnly : this.isDiligenceReadOnly,
								store : new Ext.data.SimpleStore({
											fields : ['displayText', 'value'],
											data : [['地区', 1], ['LAC', 2],
													['CI', 3], ['被叫用户', 4]]
										}),
								hiddenName : "flFinancingProject.flowType",
								triggerAction : 'all',
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

								}*/

							}]
						}, {
							columnWidth : .3, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : leftlabel,
							defaults : {
								anchor : '100%'
							},
							items : [{
								fieldLabel : "我方主体类型",
								xtype : "textfield",
								name:"mineTypeKey",
								readOnly : this.isAllReadOnly,
								readOnly : this.isDiligenceReadOnly,
								anchor : "100%"
								/*xtype : "diccombo",
								fieldLabel : "我方主体类型",
								itemName : '我方主体类型', // xx代表分类名称
								hiddenName : "flFinancingProject.mineType",
								readOnly : this.isAllReadOnly,
								readOnly : this.isDiligenceReadOnly,
								// value : '企业',
								listeners : {
									afterrender : function(combox) {
										var st = combox.getStore();
										st.on("load", function() {
													combox.setValue(combox
															.getValue());
													combox.clearInvalid();
												})
									}
								}*/

							}]
						}, {
							columnWidth : .3, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : centerlabel,
							defaults : {
								anchor : '100%'
							},
							items : [{
								xtype : "textfield",
								name : "flFinancingProject.mineName",
								fieldLabel : "我方主体",
								readOnly : this.isAllReadOnly,
								readOnly : this.isDiligenceReadOnly,
								anchor : "100%"
								/*fieldLabel : "我方主体类型",
								xtype : "diccombo",
								itemName : '我方主体类型', // xx代表分类名称
								hiddenName : "flFinancingProject.mineType",
								readOnly : this.isAllReadOnly,
								readOnly : this.isDiligenceReadOnly,
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
								}*/

							}]
						}, {
							columnWidth : .4, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : rightlabel,
							defaults : {
								anchor : '100%'
							},
							items : [{
								xtype : "combo",
								triggerClass : 'x-form-search-trigger',
								hiddenName : "degree",
								fieldLabel : "项目经理",
								blankText : "项目经理不能为空，请正确填写!",
								allowBlank : false,
								readOnly : this.isAllReadOnly,
								anchor : "100%",
								onTriggerClick : function(cc) {
									var obj = this;
									new UserDialog({
												single : false,
												callback : function(uId, uname) {
													obj.setValue(uId);
													obj.setRawValue(uname);
												}
											}).show();

								}
									/*
									 * xtype : "combo", triggerClass :
									 * 'x-form-search-trigger', hiddenName :
									 * "degree", fieldLabel : "项目经理", readOnly :
									 * this.isAllReadOnly, anchor : "100%",
									 * onTriggerClick : function(cc) { var obj =
									 * this; new UserDialog({ single : true,
									 * callback : function(uId, uname) {
									 * obj.setValue(uId);
									 * obj.setRawValue(uname); } }).show();
									 *  }
									 */
							}]
						},{
								columnWidth : 1, // 该列在整行中所占的百分比
								layout : "form", // 从上往下的布局
								items : [{
									fieldLabel : "资金用途",
									xtype : "dickeycombo",
									hiddenName : 'flFinancingProject.purposeType',
									displayField : 'itemName',
									readOnly : this.isAllReadOnly,
									nodeKey : 'flFinancing_purposeType',
									emptyText : "请选择",
									value : null,
									anchor : "100%",
									listeners : {
										afterrender : function(combox) {						        
											combox.clearInvalid();
											var st = combox.getStore();
											st.on("load", function() {
												var record = st.getAt(0);
												var v = record.data.itemId;
												combox.setValue(v);
											})
										}

									}
								}]
							}]
			}]
		});
	},
	initComponents : function() {
	}
});

FlFinancingProjectInfo = Ext.extend(Ext.Panel, {
	layout : "form",
	autoHeight : true,
	labelAlign : 'right',
	isAllReadOnly : false,
	isDiligenceReadOnly : false,
	isHiddenPanel : false,
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
		if (_cfg.isHiddenPanel) {
			this.isHiddenPanel = _cfg.isHiddenPanel;
		}
		Ext.applyIf(this, _cfg);
		this.initComponents();
		var leftlabel = 85;
		var centerlabel = 85;
		var rightlabel = 85;
		FlFinancingProjectInfo.superclass.constructor.call(this, {
			hidden : this.isHiddenPanel,
			layout : "form",
			labelAlign : 'right',
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
				items : [ {
							columnWidth : .31, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : leftlabel,
							defaults : {
								anchor : '100%'
							},
							items : [{
								xtype : "textfield",
								labelWidth : leftlabel,
								fieldLabel : "融资金额",
								fieldClass:'field-align',
								name : "projectMoney1",
								readOnly : this.isAllReadOnly,
								allowNegative : false, // 允许负数
								style : {
									imeMode : 'disabled'
								},
								blankText : "融资金额不能为空，请正确填写!",
								allowBlank : false,
								anchor : "100%",// 控制文本框的长度
								listeners : {
									scope : this,
									afterrender : function(obj) {
										obj.on("keyup")
									},
									change : function(nf) {

										        var value= nf.getValue();
												var index=value.indexOf(".");
											    if(index<=0){ //如果第一次输入 没有进行格式化
											       nf.setValue(Ext.util.Format.number(value,'0,000.00'))
											       this.getCmpByName("flFinancingProject.projectMoney").setValue(value);
											    }
											    else{
											       
											       if(value.indexOf(",")<=0){
											       	     var ke=Ext.util.Format.number(value,'0,000.00')
											       	     nf.setValue(ke);
											             this.getCmpByName("flFinancingProject.projectMoney").setValue(value);
											       }
											       else{
											       	    var last=value.substring(index+1,value.length);
											       	    if(last==0){
											       	       var temp=value.substring(0,index);
											       	       temp=temp.replace(/,/g,"");
											       	       this.getCmpByName("flFinancingProject.projectMoney").setValue(temp);
											       	    }
											       	    else{
											       	      var temp=value.replace(/,/g,"");
											              this.getCmpByName("flFinancingProject.projectMoney").setValue(temp);
											       	    }
											       }
											    }
									}
								}
							}, {
								xtype : "hidden",
								name : "flFinancingProject.projectMoney"
							}]
						}, {
							columnWidth : .03, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : 20,
							items : [{
										fieldLabel : "元",
										labelSeparator : '',
										anchor : "100%"
									}]
						},{
						columnWidth : .16, // 该列在整行中所占的百分比
						layout : "form", // 从上往下的布局
						labelWidth : centerlabel,
						items : [{
							fieldLabel : "币种",
							xtype : "dickeycombo",
						    hiddenName : 'flFinancingProject.currency',
							displayField : 'itemName',
							readOnly : this.isAllReadOnly,
							itemName : '注册资金币种', // xx代表分类名称
							allowBlank : false,
							editable : false,
							blankText : "币种不能为空，请正确填写!",
							nodeKey : 'capitalkind',
							emptyText : "请选择",
							value : null,
							anchor : "100%",
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
				}, {
							columnWidth : .16, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : 65,
							defaults : {
								anchor : '100%'
							},
							items : [{
								fieldLabel : "计息方式",
								xtype : 'dicIndepCombo',
								editable : true,
		                        lazyInit : false,
		                        nodeKey : 'interestType',//独立数据字典的 计息方式 nodeKey
								allowBlank : false,
								readOnly : this.isAllReadOnly,
								hiddenName : "flFinancingProject.accrualtype",
								emptyText : "请选择",
								value : null,
								blankText : "计息方式不能为空，请正确填写!",
								anchor : "100%",
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
						}, {
							columnWidth : .16, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : 40,
							items : [{
								xtype : "checkbox",
								boxLabel : "是否前置付息",
								readOnly : this.isAllReadOnly,
								anchor : "100%",
								name : "isPreposePayAccrualCheck"
							}]
						}, {
							columnWidth : .18, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : 65,
							items : [{
								fieldLabel : '<span class="helpfield" onclick=openHelpWindow("付息方式")><u>付息方式</u></span >',
								xtype : 'dicIndepCombo',
								editable : true,
		                        lazyInit : false,
		                        nodeKey : 'payType',//独立数据字典的 付息方式 nodeKey
								//xtype : 'diccombo',
								//itemName : '付息方式', // xx代表分类名称
								allowBlank : false,
								readOnly : this.isAllReadOnly,
								hiddenName : "flFinancingProject.payaccrualType",
								emptyText : "请选择",
								value : null,
								blankText : "付息方式不能为空，请正确填写!",
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
						}, {
							columnWidth : .31, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : leftlabel,
							defaults : {
								anchor : '100%'
							},
							items : [{
										fieldLabel : "融资起始日",
										xtype : "datefield",
										name : "flFinancingProject.startDate",
										allowBlank : false,
										style : {
											imeMode : 'disabled'
										},
										readOnly : this.isAllReadOnly,
										blankText : "融资起始日不能为空，请正确填写!",
										format : 'Y-m-d'
								
							}]
						}, {
							columnWidth : .03, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : 20,
							items : [{
										fieldLabel : " ",
										labelSeparator : '',
										anchor : "100%"
									}]
						}, {
							columnWidth : .32, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : centerlabel,
							defaults : {
								anchor : '100%'
							},
							items : [{
									xtype : "datefield",
									name : "flFinancingProject.intentDate",
									allowBlank : false,
									readOnly : this.isAllReadOnly,
									fieldLabel : "融资还款日",
									blankText : "融资还款日不能为空，请正确填写!",
									format : 'Y-m-d'
									}]
						}, {
							columnWidth : .34, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : rightlabel,
							defaults : {
								anchor : '100%'
							},
							items : [{			
								xtype : 'dicIndepCombo',
								editable : true,
		                        lazyInit : false,
		                        nodeKey : 'dateModel',//独立数据字典的 日期模型nodeKeyxtype : 'diccombo',
								//xtype : 'diccombo',
								//itemName : '日期模型', // xx代表分类名称
								hiddenName : "flFinancingProject.dateMode",
								emptyText : "请选择",
								value : null,
								// readOnly : this.isAllReadOnly,
								fieldLabel : "日期模型",
								readOnly : this.isAllReadOnly,
								allowBlank : false,
								blankText : "日期模型不能为空，请正确填写!",
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
							columnWidth : .31, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : leftlabel,
							defaults : {
								anchor : '100%'
							},
							items : [{
										xtype : "textfield",
										name : "flFinancingProject.accrual",
										allowBlank : false,
										fieldLabel : "融资利率",
										fieldClass:'field-align',
										readOnly : this.isAllReadOnly,
										blankText : "融资利率不能为空，请正确填写!"
									}]
						}, {
							columnWidth : .03, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : 14,
							items : [{
										fieldLabel : "%",
										labelSeparator : '',
										anchor : "100%"
									}]
						},{
					columnWidth : .29, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : centerlabel,
					items : [{
						xtype : "numberfield",
						name : "flFinancingProject.managementConsultingOfRate",
						allowBlank : false,
						fieldLabel : "管理咨询费率",
						decimalPrecision:10,
						value:0,
						fieldClass:'field-align',
						style: {imeMode:'disabled'},
						readOnly : this.isAllReadOnly,
						blankText : "管理咨询费率不能为空，请正确填写!",
						anchor : "100%",
						listeners : {
					    	scope:this,
							change  :function(nf) {
								var value= nf.getValue();
								if(value==null||value=="")
								{
										nf.setValue(0)
								}
							}
					    }
					}]
				},{
					columnWidth : .03, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : 20,
					items : [{
						fieldLabel : "%",
						labelSeparator:'', 
						anchor : "100%"
					}]
				},{
					columnWidth : .31, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : rightlabel,
					items : [{
						xtype : "numberfield",
						name : "flFinancingProject.financeServiceOfRate",
						allowBlank : false,
						fieldLabel : "财务服务费率",
						value:0,
						decimalPrecision:10,
						fieldClass:'field-align',
						style: {imeMode:'disabled'},
						readOnly : this.isAllReadOnly,
						blankText : "财务服务费率不能为空，请正确填写!",
						anchor : "100%",
						listeners : {
					    	scope:this,
							change  :function(nf) {
								var value= nf.getValue();
								if(value==null||value=="")
								{
										nf.setValue(0)
								}
							}
					    }
					}]
				},{
					columnWidth : .03, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : 20,
					items : [{
						fieldLabel : "%",
						labelSeparator:'', 
						anchor : "100%"
					}]
				}, {
							columnWidth : .31, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : leftlabel,
							defaults : {
								anchor : '100%'
							},
							items : [{
										fieldLabel : "逾期费率",
										fieldClass:'field-align',
										xtype : "numberfield",
										name : "flFinancingProject.overdueRate",
										allowBlank : false,
										style : {
											imeMode : 'disabled'
										},
										readOnly : this.isAllReadOnly,
										blankText : "逾期费率不能为空，请正确填写!",
										anchor : "100%"

									}]
						}, {
							columnWidth : .03, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : 32,
							items : [{
										fieldLabel : "%/日",
										labelSeparator : '',
										anchor : "100%"
									}]
						}, {
							columnWidth : .29, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : centerlabel,
							defaults : {
								anchor : '100%'
							},
							items : [{
								xtype : "numberfield",
								name : "flFinancingProject.breachRate",
								readOnly : this.isAllReadOnly,
								fieldClass:'field-align',
								fieldLabel : "违约金比例",
								fieldClass:'field-align',
								style: {imeMode:'disabled'},
								anchor : "100%"
							}]
						},{
							columnWidth : .03, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : 20,
							items : [{
								fieldLabel : "%",
								labelSeparator:'', 
								anchor : "100%"
							}]
				}]
			}]
		});
	},
	initComponents : function() {
	}
});


/*
 * Ext.reg('projectNameTextField', ExtUD.Ext.ProjectNameTextField);
 * Ext.reg('typeSelectInfoPanel', ExtUD.Ext.TypeSelectInfoPanel);
 * Ext.reg('peerMainInfoPanel',ExtUD.Ext.PeerMainInfoPanel);
 * Ext.reg('peerPersonMainInfoPanel',ExtUD.Ext.PeerPersonMainInfoPanel);
 * Ext.reg('projectInfoPanel',ExtUD.Ext.ProjectInfoPanel);
 */

