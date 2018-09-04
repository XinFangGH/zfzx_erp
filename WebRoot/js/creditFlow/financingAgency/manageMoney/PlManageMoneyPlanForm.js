/**
 * @author
 * @createtime
 * @class SlBankAccountForm
 * @extends Ext.Window
 * @description SlBankAccount表单
 * @company 智维软件
 */
PlManageMoneyPlanForm = Ext.extend(Ext.Window, {
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		// 必须先初始化组件
		this.initUIComponents();
		PlManageMoneyPlanForm.superclass.constructor.call(this, {
					id : 'PlManageMoneyPlanFormWin',
					layout : 'fit',
					border : false,
					items : [this.formPanel],
					modal : true,
					height : 600,
					width : 1000,
					// maximizable : true,
					title : '理财计划详细信息',
					buttonAlign : 'center',
					buttons : [{
								text :this.isAllReadOnly?"启动":'保存',
								iconCls : 'btn-save',
								hidden : this.isHidden,
								scope : this,
								handler : this.save
							}, {
								text :"提交",
								iconCls : 'btn-transition',
								hidden : this.isAllReadOnly,
								scope : this,
								handler : this.submit
							}, {
								text : '重置',
								iconCls : 'btn-reset',
								scope : this,
								hidden : this.isAllReadOnly,
								handler : this.reset
							}, {
								text : '取消',
								iconCls : 'btn-cancel',
								scope : this,
								hidden : this.isAllReadOnly,
								handler : this.cancel
							}]
				});
	},// end of the constructor
	// 初始化组件
	initUIComponents : function() {
		var leffwidth = 100;
		this.mark = this.mmplanId;
		this.uploads = new uploads({
					projectId : this.mark,
					isHidden : this.isAllReadOnly,
					businessType : "fsdf",
					isNotOnlyFile : false,
					isHiddenColumn : false,
					isDisabledButton : false,
					setname : '理财协议',
					titleName : '理财协议',
					tableName : 'typeislcjhxy',
					uploadsSize : 1,
					isHiddenOnlineButton : true,
					isDisabledOnlineButton : true
				})
	  //投资理财记录			
	  this.PlManageMoneyPlanOrder=	new PlManageMoneyPlanOrder({
	      mmplanId: this.mmplanId,
	      keystr:"mmplan"
	  
	  });		
	  //投资人优惠券生成
	    this.PlManageMoneyPlanCoupons=	new PlManageMoneyPlanCoupons({
	      mmplanId: this.mmplanId,
	      keystr:"mmplan",
	      isAllReadOnly:this.seeHidden
	  
	  });	
		this.formPanel = new Ext.FormPanel({
			name : 'PlManageMoneyPlanForm',
			layout : 'column',
			bodyStyle : 'padding:10px',
			autoScroll : true,
			monitorValid : true,
			frame : true,
			plain : true,
			labelAlign : "right",
			// id : 'SlPersonMainForm',
			defaults : {
				anchor : '96%',
				labelWidth : 80,
				columnWidth : 1,
				layout : 'column'
			},
			// defaultType : 'textfield',
			items : [{
				columnWidth : .33,
				labelWidth : leffwidth,
				layout : 'form',
				items : [{
							xtype : 'hidden',
							name : 'plManageMoneyPlan.isStartDatePay',
							value:'2'
						},{
							xtype : 'hidden',
							name : 'plManageMoneyPlan.payaccrualType',
							value:'monthPay'
						},{
							xtype : 'hidden',
							id : 'plManageMoneyPlan.mmplanId',
							name : 'plManageMoneyPlan.mmplanId'
						}, {
							fieldLabel : '理财计划类型',
							xtype : 'textfield',
							hiddenName : 'plManageMoneyPlan.manageMoneyTypeId',
							name:"plManageMoneyPlan.manageMoneyTypeId",
							readOnly : this.isAllReadOnly,
							allowBlank : false,
							anchor : '100%',
							xtype : "combo",
							displayField : 'itemName',
							valueField : 'itemId',
							triggerAction : 'all',
							store : new Ext.data.ArrayStore({
								url : __ctxPath
										+ '/creditFlow/financingAgency/getListbykeystrPlManageMoneyType.do?keystr=mmplan',
								fields : ['itemId', 'itemName'],
								autoLoad : true
							}),
							mode : 'remote',
							editable : false,
							blankText : "理财计划类型不能为空，请正确填写!",
							anchor : "100%",
							listeners : {
								scope : this,
								afterrender : function(combox) {
									var st = combox.getStore();
									st.on("load", function() {
												combox.setValue(combox
														.getValue());

											})
									combox.clearInvalid();
								}

							}

						}, {
							xtype : "hidden",
							name : 'plManageMoneyPlan.mmName'

						}]
			}, {
				columnWidth : .33,
				layout : 'form',
				labelWidth : leffwidth,
				items : [{
							fieldLabel : '理财编号',
							name : 'plManageMoneyPlan.mmNumber',
							readOnly : this.isAllReadOnly,
							allowBlank : false,
							xtype : 'textfield',
							anchor : '100%',	
							listeners : {
								scope:this,
								'blur' : function(f) {
										var manageMoneyTypeId=this.getCmpByName("plManageMoneyPlan.manageMoneyTypeId").getValue();
										var mmNumber = f.getValue();
										Ext.Ajax.request({
						                   url:  __ctxPath + '/creditFlow/financingAgency/CheckNumDoublePlManageMoneyPlan.do?manageMoneyTypeId='+manageMoneyTypeId+"&mmNumber="+mmNumber,
						                   method : 'POST',
						                  success : function(response,request) {
												var obj=Ext.util.JSON.decode(response.responseText);
			                            		if(obj.flag=="1"){					                            			
			                            			Ext.ux.Toast.msg('操作信息',"该类型的编号已经重复，请重新输入");
			                            			f.setValue("");
			                            		}
					                      }
			                             });
								}
							}
						}]
			}, {
				columnWidth : .31,
				layout : 'form',
				labelWidth : leffwidth,
				items : [{
							fieldLabel : '招标金额',
							name : 'plManageMoneyPlan.sumMoney',
							xtype : 'moneyfield',
							fieldClass : 'field-align',
							readOnly : this.isAllReadOnly,
							allowBlank : false,
							anchor : '100%'
						}]
			}, {
				columnWidth : .03, // 该列在整行中所占的百分比
				layout : "form", // 从上往下的布局
				labelWidth : 18,
				items : [{
							fieldLabel : "<span style='margin-left:1px'>元</span> ",
							labelSeparator : '',
							anchor : "100%"

						}]
			}, {
				columnWidth : .33,
				layout : 'form',
				labelWidth : leffwidth,
				items : [{
							fieldLabel : '收益方式',
						xtype : 'combo',
						mode : 'local',
						displayField : 'name',
						valueField : 'id',
						id : 'accountTypeid',
						editable : false,
						readOnly : this.isAllReadOnly,
						width : 70,
						store : new Ext.data.SimpleStore({
									fields : ["name", "id"],
									data : [["一次性支付利息", "1"],
											["按期付息,到期还本", "0"]]
								}),
						triggerAction : "all",
						hiddenName : "plManageMoneyPlan.isOne",
						anchor : '100%',
						name : 'keystr',
						value:"1",
						listeners : {
							afterrender : function(combox) {
								var st = combox.getStore();
								combox.setValue(combox.getValue());
							}

						}
					
						}]
			}, {
				columnWidth : .33,
				layout : 'form',
				labelWidth : leffwidth,
				items : [{
							fieldLabel : '购买开放时间',
							name : 'plManageMoneyPlan.buyStartTime',
							xtype : 'datetimefield',
							readOnly : this.isAllReadOnly,
							allowBlank : false,
							format : 'Y-m-d H:i:s',
							// format : 'Y-m-d',
							value : new Date(),
							anchor : '100%'
						}]
			}, {
				columnWidth : .34,
				layout : 'form',
				labelWidth : leffwidth,
				items : [{
							fieldLabel : '购买结束时间',
							name : 'plManageMoneyPlan.buyEndTime',
							readOnly : this.isAllReadOnly,
							allowBlank : false,
							xtype : 'datetimefield',
							format : 'Y-m-d H:i:s',
							// format : 'Y-m-d',
							//value : new Date(),
							anchor : '100%',
							listeners : {
								scope : this,
								'blur' : function(nf){
									if(nf.getValue()!=null && nf.getValue()!=""){
										var buyStartTime=this.getCmpByName("plManageMoneyPlan.buyStartTime").getValue();
										if(buyStartTime!=null){
											var formatbuyStartTime=new Date(buyStartTime).getTime();
											var formatbuyEndTime=new Date(nf.getValue()).getTime();
											if(formatbuyStartTime>=formatbuyEndTime){
												Ext.ux.Toast.msg('操作信息',"购买开放时间不能小于等于购买结束时间，请重新填写!");
										 		nf.setValue(null);
											}
										}else{
										 	Ext.ux.Toast.msg('操作信息',"请先填写购买开放时间!");
										 	nf.setValue(null);
										 }
									}else{
										Ext.ux.Toast.msg('操作信息',"请先填写购买结束时间!");
									}
									
								}
							}
						}]
			}, {
				columnWidth : .3,
				labelWidth : leffwidth,
				layout : 'form',
				items : [{
							fieldLabel : '投资起点金额',
							name : 'plManageMoneyPlan.startMoney',
							readOnly : this.isAllReadOnly,
							allowBlank : false,
							fieldClass : 'field-align',
							xtype : 'moneyfield',
							anchor : '100%'
						}]
			}, {
				columnWidth : .03, // 该列在整行中所占的百分比
				layout : "form", // 从上往下的布局
				labelWidth : 18,
				items : [{
							fieldLabel : "<span style='margin-left:1px'>元</span> ",
							labelSeparator : '',
							anchor : "100%"

						}]
			}, {
				columnWidth : .3,
				labelWidth : leffwidth,
				layout : 'form',
				items : [{
							fieldLabel : '递增金额',
							fieldClass : 'field-align',
							xtype : 'moneyfield',
							name : 'plManageMoneyPlan.riseMoney',
							readOnly : this.isAllReadOnly,
							allowBlank : false,
							anchor : '100%'
						}]
			}, {
				columnWidth : .03, // 该列在整行中所占的百分比
				layout : "form", // 从上往下的布局
				labelWidth : 18,
				items : [{
							fieldLabel : "<span style='margin-left:1px'>元</span> ",
							labelSeparator : '',
							anchor : "100%"

						}]
			}, {
				columnWidth : .31,
				labelWidth : leffwidth,
				layout : 'form',
				items : [{
							fieldLabel : '单笔投资上限',
							xtype : 'moneyfield',
							name : 'plManageMoneyPlan.limitMoney',
							readOnly : this.isAllReadOnly,
							allowBlank : false,
							fieldClass : 'field-align',
							anchor : '100%'
						}]
			}, {
				columnWidth : .03, // 该列在整行中所占的百分比
				layout : "form", // 从上往下的布局
				labelWidth : 18,
				items : [{
							fieldLabel : "<span style='margin-left:1px'>元</span> ",
							labelSeparator : '',
							anchor : "100%"

						}]
			}, {
				columnWidth : .33,
				labelWidth : leffwidth,
				layout : 'form',
				items : [{
							fieldLabel : '起息条件',
							xtype : 'textfield',
							name : 'plManageMoneyPlan.startinInterestCondition',
							allowBlank : false,
							readOnly : this.isAllReadOnly,
							anchor : '100%'
						}]
			}, {
				columnWidth : .33,
				labelWidth : leffwidth,
				layout : 'form',
				items : [{
							fieldLabel : '到期赎回方式',
							xtype : 'textfield',
							name : 'plManageMoneyPlan.expireRedemptionWay',
							allowBlank : false,
							readOnly : this.isAllReadOnly,
							anchor : '100%'
						}]
			}, {
				columnWidth : .34,
				labelWidth : leffwidth,
				layout : 'form',
				items : [{
							fieldLabel : '费用收取方式',
							xtype : 'textfield',
							name : 'plManageMoneyPlan.chargeGetway',
							readOnly : this.isAllReadOnly,
							anchor : '100%'
						}]
			}, {
				columnWidth : .33,
				labelWidth : leffwidth,
				layout : 'form',
				items : [{
							fieldLabel : '保障方式',
							xtype : 'textfield',
							name : 'plManageMoneyPlan.guaranteeWay',
							readOnly : this.isAllReadOnly,
							allowBlank : false,
							anchor : '100%'
						}]
			}, {
				columnWidth : .20,
				labelWidth : leffwidth,
				layout : 'form',
				items : [{
							fieldLabel : '年化收益率',
							xtype : 'numberfield',
							allowBlank : false,
							name : 'plManageMoneyPlan.yeaRate',
							value:7,
							readOnly : this.isAllReadOnly,
							fieldClass : 'field-align',
							anchor : '100%'
						}]
			}, {
				columnWidth : .10,
				labelWidth:20,
				layout : 'form',
				items : [{  
							fieldLabel : '-',
							xtype : 'numberfield',
							value:10,
							allowBlank : false,
							name : 'plManageMoneyPlan.maxYearRate',
							readOnly : this.isAllReadOnly,
							fieldClass : 'field-align',
							anchor : '100%'
						}]
			}, {
				columnWidth : .03, // 该列在整行中所占的百分比
				layout : "form", // 从上往下的布局
				labelWidth : 18,
				items : [{
							fieldLabel : "<span style='margin-left:1px'>%</span> ",
							labelSeparator : '',
							anchor : "100%"

						}]
			}, {
				columnWidth : .31,
				labelWidth : leffwidth,
				layout : 'form',
				items : [{
							fieldLabel : '投资期限',
							name : 'plManageMoneyPlan.investlimit',
							id:"plManageMoneyPlan.investlimit",
							readOnly : this.isAllReadOnly,
							allowBlank : false,
							fieldClass : 'field-align',
							xtype : 'numberfield',
							anchor : '100%'

						}]
			}, {
				columnWidth : .03, // 该列在整行中所占的百分比
				layout : "form", // 从上往下的布局
				labelWidth : 18,
				items : [{
							fieldLabel : "<span style='margin-left:1px'>月</span> ",
							labelSeparator : '',
							anchor : "100%"

						}]
			}, {
				columnWidth : .3,
				layout : 'form',
				labelWidth : leffwidth,
				items : [{
				columnWidth : .31,
				labelWidth : leffwidth,
				layout : 'form',
				items : [{
							fieldLabel : '锁定期限',
							name : 'plManageMoneyPlan.lockingLimit',
							readOnly : this.isAllReadOnly,
							id:"plManageMoneyPlan.lockingLimit",
							allowBlank : false,
							fieldClass : 'field-align',
							xtype : 'numberfield',
							anchor : '100%'

						}]
			}]
			}, {
				columnWidth : .03, // 该列在整行中所占的百分比
				layout : "form", // 从上往下的布局
				labelWidth : 18,
				items : [{
							fieldLabel : "<span style='margin-left:1px'>月</span> ",
							labelSeparator : '',
							anchor : "100%"

						}]
			}, {
				columnWidth : .3,
				labelWidth : leffwidth,
				layout : 'form',
				items : [{
							fieldLabel : '提前退出费率',
							xtype : 'numberfield',
							allowBlank : false,
							name : 'plManageMoneyPlan.earlierOutRate',
							readOnly : this.isAllReadOnly,
							fieldClass : 'field-align',
							anchor : '100%'
						}]
			}, {
				columnWidth : .03, // 该列在整行中所占的百分比
				layout : "form", // 从上往下的布局
				labelWidth : 18,
				items : [{
							fieldLabel : "<span style='margin-left:1px'>%</span> ",
							labelSeparator : '',
							anchor : "100%"

						}]
			}, {
				columnWidth : .34,
				layout : 'form',
				labelWidth : leffwidth,
				items : [{
							fieldLabel : '收款用户',
							name : 'plManageMoneyPlan.moneyReceiver',
							readOnly : this.isAllReadOnly,
							allowBlank : false,
							xtype : 'textfield',
							anchor : '100%'

						}]
			
			}, {
				columnWidth : 1,
				layout : 'form',
				labelWidth : leffwidth,
				items : [{
							fieldLabel : '投资范围',
							name : 'plManageMoneyPlan.investScope',
							readOnly : this.isAllReadOnly,
							xtype : 'textfield',
							anchor : '100%'
						}]
			},{
				columnWidth : .22,
				layout : 'form',
				labelWidth : leffwidth,
				items : [{
									fieldLabel : '可用优惠券',
									name : 'plBidPlan.coupon',
									id : 'check_coupon',
									xtype : 'checkbox',
									boxLabel : '是',
									allowBlank : false,
									readOnly : this.readOnly,
									value :0,
									anchor : '100%',
										listeners : {
											scope : this,
											check : function(obj, checked) {
												if(!checked){
													Ext.getCmp("plBidPlan.coupon").setValue("");
													Ext.getCmp("maxMoney_text").setValue("");
													Ext.getCmp("ratio_text").setValue("");
													Ext.getCmp('plBidPlan.rebateType').setValue("");
													Ext.getCmp('plBidPlan.rebateWay').setValue("");
													Ext.getCmp("hide_a").hide();
													Ext.getCmp("hide_b").hide();
													Ext.getCmp("hide_f").hide();
													Ext.getCmp("hide_g").hide();
													Ext.getCmp("hide_h").hide();
													Ext.getCmp("check_f").setValue(false);
													Ext.getCmp("check_g").setValue(false);
													Ext.getCmp("check_h").setValue(false);
													Ext.getCmp("check_e").setValue(false);
													Ext.getCmp("check_ff").setValue(false);
													Ext.getCmp("check_ee").setValue(false);
													Ext.getCmp("check_ii").setValue(false);
													
												}else{
													Ext.getCmp("plBidPlan.coupon").setValue("1");
													Ext.getCmp("hide_a").show();
													Ext.getCmp("hide_b").show();
													Ext.getCmp("hide_f").show();
													Ext.getCmp("hide_g").show();
													Ext.getCmp("hide_h").show();
													Ext.getCmp("check_addRate").setValue(false);
													Ext.getCmp("addRate_text").setValue("");
													Ext.getCmp("hide_addRate_1").hide();
													Ext.getCmp("hide_addRate_2").hide();
													Ext.getCmp('plBidPlan.rebateType').setValue("");
													Ext.getCmp('plBidPlan.rebateWay').setValue("");
												}
											}
										}

								}]
			
			}, {
				columnWidth : .22,
				layout : 'form',
				labelWidth : leffwidth,
				items : [{
									fieldLabel : '普通加息',
									name : 'checkaddRate',
									id : 'check_addRate',
									xtype : 'checkbox',
									boxLabel : '是',
									allowBlank : false,
									readOnly : this.readOnly,
									value :0,
									anchor : '100%',
											listeners : {
											scope : this,
											check : function(obj, checked) {
												if(!checked){
													Ext.getCmp("hide_addRate_1").hide();
													Ext.getCmp("hide_addRate_2").hide();
													Ext.getCmp("addRate_text").setValue("");
													Ext.getCmp('plBidPlan.rebateType').setValue("");
													Ext.getCmp('plBidPlan.rebateWay').setValue("");
												}else{
													Ext.getCmp("plBidPlan.coupon").setValue("");
													Ext.getCmp("hide_f").hide();
													Ext.getCmp("hide_g").hide();
													Ext.getCmp("hide_h").hide();
													Ext.getCmp("check_f").setValue(false);
													Ext.getCmp("check_g").setValue(false);
													Ext.getCmp("check_h").setValue(false);
													Ext.getCmp("check_e").setValue(false);
													Ext.getCmp("check_ff").setValue(false);
													Ext.getCmp("check_ee").setValue(false);
													Ext.getCmp("check_ii").setValue(false);
													Ext.getCmp("check_coupon").setValue(false);
													Ext.getCmp("maxMoney_text").setValue("");
													Ext.getCmp("ratio_text").setValue("");
													Ext.getCmp("ratio_text").removeClass("readOnlyClass");
													Ext.getCmp("ratio_text").setReadOnly(false);
													Ext.getCmp("maxMoney_text").removeClass("readOnlyClass");
													Ext.getCmp("maxMoney_text").setReadOnly(false);
													Ext.getCmp("hide_addRate_1").show();
													Ext.getCmp("hide_addRate_2").show();
													//(系统默认)rebateType=0普通加息
													//(可选)rebateWay=1,立还。rebateWay=2随期。rebateWay=3到期
													Ext.getCmp('plBidPlan.rebateType').setValue("0");
													Ext.getCmp('plBidPlan.rebateWay').setValue("2");
												}
											}
										}

								}]
			
			},{
				columnWidth : .22,
				layout : 'form',
				labelWidth : leffwidth,
				items : [{
									fieldLabel : '是否新手专享',
									name : 'plBidPlan.novice',
									xtype : 'checkbox',
									boxLabel : '是',
									allowBlank : false,
									id : 'check_novice',
									value :0,
									anchor : '100%',
									handler : function(ck, checked) {
										if(!checked){
											Ext.getCmp("plBidPlan.novice").setValue("");
										}else{
											Ext.getCmp("plBidPlan.novice").setValue("1");
										}
									}

								}]
			
			}, {
				columnWidth : .31,
				labelWidth : leffwidth,
				layout : 'form',
				items : [{
							fieldLabel : '募集期利率',
							xtype : 'numberfield',
							name : 'plManageMoneyPlan.raiseRate',
							readOnly : this.isAllReadOnly,
							fieldClass : 'field-align',
							anchor : '100%'
						}]
			}, {
				columnWidth : .03, // 该列在整行中所占的百分比
				layout : "form", // 从上往下的布局
				labelWidth : 18,
				items : [{
							fieldLabel : "<span style='margin-left:1px'>%</span> ",
							labelSeparator : '',
							anchor : "100%"

						}]
			},{
						columnWidth : .31, // 该列在整行中所占的百分比
						layout : "form", // 从上往下的布局
						labelWidth : leffwidth,
						id : 'hide_addRate_1',
						hidden: true,
						allowBlank : false,
						labelAlign : 'right',
						items : [{
									fieldLabel : '<font color="red">*</font>普通加息利率',
									name : 'plManageMoneyPlan.addRate',
									id : 'addRate_text',
									xtype : 'numberfield',
									anchor : '100%'

								}]
					}, {
						columnWidth : .69, // 该列在整行中所占的百分比
						layout : "form", // 从上往下的布局
						labelWidth : 310,
						id : 'hide_addRate_2',
						hidden: true,
						labelAlign : 'right',
						items : [{
									fieldLabel : "%/年 (普通用户加息，随期奖励，不需要使用优惠券)",
									labelSeparator : '',
									anchor : "100%"
								}]
					}, {
				columnWidth : .31,
				labelWidth : leffwidth,
				id : 'hide_a',
				hidden: true,
				layout : 'form',
				items : [{
									fieldLabel : '<font color="red">*</font>面值折现比',
									name : 'plManageMoneyPlan.returnRatio',
									id :'ratio_text',
									xtype : 'numberfield',
									anchor : '100%'

								},{
									fieldLabel : '<font color="red">*</font>单笔最大面值',
									name : 'plManageMoneyPlan.maxCouponMoney',
									id : 'maxMoney_text',
									xtype : 'numberfield',
									anchor : '100%'

								},{
									fieldLabel : '返利类型',
									name : 'check_e',
									xtype : 'checkbox',
									boxLabel : '返现',
									id : 'check_e',
									allowBlank : false,
									readOnly : this.readOnly,
									anchor : '100%',
									listeners : {
										"check" : function(el, checked) {
										 if (checked) {
										 	 Ext.getCmp('plBidPlan.rebateType').setValue("1");
												Ext.getCmp("check_f").setValue(false);
												Ext.getCmp("check_g").setValue(false);
												Ext.getCmp("check_h").setValue(false);
												Ext.getCmp("hide_a").show();
												Ext.getCmp("hide_b").show();
												Ext.getCmp("ratio_text").removeClass("readOnlyClass");
												Ext.getCmp("ratio_text").setReadOnly(false);
												Ext.getCmp("maxMoney_text").removeClass("readOnlyClass");
												Ext.getCmp("maxMoney_text").setReadOnly(false);
										    }else{
										}
										}
									
									}

								},{
									fieldLabel : '返利方式',
									name : 'check_ee',
									xtype : 'checkbox',
									id : 'check_ee',
									boxLabel : '立返',
									allowBlank : false,
									readOnly : this.readOnly,
									anchor : '100%',
									listeners : {
										"check" : function(el, checked) {
										 if (checked) {
										 	Ext.getCmp('plBidPlan.rebateWay').setValue("1");
												Ext.getCmp("check_ff").setValue(false);
												Ext.getCmp("check_ii").setValue(false);
												Ext.getCmp("hide_a").show();
												Ext.getCmp("hide_b").show();
										    }else{
										}
										}
									
									}
								}]
			}, {
				columnWidth : .69,
				labelWidth : 385,
				id : 'hide_b',
				hidden: true,
				layout : 'form',
				items : [{
									fieldLabel : "% (例如：如果优惠券面值为1000元折现比例10%则有效面值为100元)",
									labelSeparator : '',
									anchor : "100%"
								},{
									fieldLabel : "元 (例如：单笔最大面值1000元此标不能使用1000元以上面值的优惠券)",
									labelSeparator : '',
									anchor : "100%"
								}]
			},{
						columnWidth : .2, // 该列在整行中所占的百分比
						layout : "form", // 从上往下的布局
						labelWidth : 55,
						id : 'hide_f',
						hidden: true,
						labelAlign : 'right',
						items : [{
							fieldLabel : '',
							name : 'check_f',
							xtype : 'checkbox',
							id : 'check_f',
							boxLabel : '返息',
							anchor : '100%',
							listeners : {
										"check" : function(el, checked) {
										 if (checked) {
										 	 	Ext.getCmp('plBidPlan.rebateType').setValue("2");
												Ext.getCmp("check_e").setValue(false);
												Ext.getCmp("check_g").setValue(false);
												Ext.getCmp("check_h").setValue(false);
												Ext.getCmp("hide_a").show();
												Ext.getCmp("hide_b").show();
												
												Ext.getCmp("ratio_text").removeClass("readOnlyClass");
												Ext.getCmp("ratio_text").setReadOnly(false);
												Ext.getCmp("maxMoney_text").removeClass("readOnlyClass");
												Ext.getCmp("maxMoney_text").setReadOnly(false);
										    }else{
										}
										}
									
									}
						},{
							fieldLabel : '',
							name : 'check_ff',
							xtype : 'checkbox',
							id : 'check_ff',
							boxLabel : '随期',
							anchor : '100%',
							listeners : {
										"check" : function(el, checked) {
										 if (checked) {
										 	Ext.getCmp('plBidPlan.rebateWay').setValue("2");
												Ext.getCmp("check_ee").setValue(false);
												Ext.getCmp("check_ii").setValue(false);
												Ext.getCmp("hide_a").show();
												Ext.getCmp("hide_b").show();
										    }else{
										}
										}
									
									}
						}]
					},{
						columnWidth : .2, // 该列在整行中所占的百分比
						layout : "form", // 从上往下的布局
						labelWidth : 55,
						id : 'hide_g',
						hidden: true,
						labelAlign : 'right',
						items : [{
									fieldLabel : '',
									name : 'check_g',
									xtype : 'checkbox',
									id : 'check_g',
									boxLabel : '返息现',
									anchor : '100%',
									listeners : {
										"check" : function(el, checked) {
										 if (checked) {
										 	 Ext.getCmp('plBidPlan.rebateType').setValue("3");
												Ext.getCmp("check_e").setValue(false);
												Ext.getCmp("check_f").setValue(false);
												Ext.getCmp("check_h").setValue(false);
												Ext.getCmp("hide_a").show();
												Ext.getCmp("hide_b").show();
												
												Ext.getCmp("ratio_text").removeClass("readOnlyClass");
												Ext.getCmp("ratio_text").setReadOnly(false);
												Ext.getCmp("maxMoney_text").removeClass("readOnlyClass");
												Ext.getCmp("maxMoney_text").setReadOnly(false);
												
										    }else{
										}
										}
									
									}

								},{
									fieldLabel : '',
									name : 'check_ii',
									xtype : 'checkbox',
									id : 'check_ii',
									boxLabel : '到期',
									anchor : '100%',
									listeners : {
												"check" : function(el, checked) {
												 if (checked) {
												 	Ext.getCmp('plBidPlan.rebateWay').setValue("3");
														Ext.getCmp("check_ee").setValue(false);
														Ext.getCmp("check_ff").setValue(false);
														Ext.getCmp("hide_a").show();
														Ext.getCmp("hide_b").show();
												    }else{
												}
												}
											
											}
								}]
					},{
						columnWidth : .2, // 该列在整行中所占的百分比
						layout : "form", // 从上往下的布局
						labelWidth : 55,
						id : 'hide_h',
						hidden: true,
						labelAlign : 'right',
						items : [{
									fieldLabel : '',
									name : 'check_h',
									xtype : 'checkbox',
									boxLabel : '加息',
									id : 'check_h',
									anchor : '100%',
									listeners : {
										"check" : function(el, checked) {
										 if (checked) {
										 	    Ext.getCmp('plBidPlan.rebateType').setValue("4");
										 	    Ext.getCmp("maxMoney_text").setValue("");
										 	    Ext.getCmp("maxMoney_text").addClass("readOnlyClass");
										 	    Ext.getCmp("maxMoney_text").setReadOnly(true);
										 	          
												Ext.getCmp("ratio_text").setValue("");
												Ext.getCmp("ratio_text").addClass("readOnlyClass");
												Ext.getCmp("ratio_text").setReadOnly(true);
												
												Ext.getCmp("check_e").setValue(false);
												Ext.getCmp("check_f").setValue(false);
												Ext.getCmp("check_g").setValue(false);
												
										    }else{
										}
										}
									
									}


								}]
					},{
				columnWidth : .33,
				layout : 'form',
				labelWidth : leffwidth,
				items : [{
							fieldLabel : '计息起日',
							name : 'plManageMoneyPlan.startinInterestTime',
							xtype : 'datefield',
							hidden : !this.isAllReadOnly,
							hideLabel : !this.isAllReadOnly,
							allowBlank:this.isAllReadOnly?false:true,
							format : 'Y-m-d H:i:s',
							anchor : '100%',
							readOnly:this.seeHidden,//用来查看理财计划使用的限制
							listeners : {
							scope : this,
							'change' : function(nf){
								var dtstr = Ext.util.Format.date(nf.getValue(), 'Y-m-d');
								var month=Ext.getCmp("plManageMoneyPlan.investlimit").getValue();
								var n=parseInt(month);
								 var s=dtstr.split("-");
									   var yy=parseInt(s[0]);
									   var mm=parseInt(s[1])-1;
									   var dd=parseInt(s[2]);
									   var dt=new Date(yy,mm,dd);
									   dt.setMonth(dt.getMonth()+n);
									   if( (dt.getYear()*12+dt.getMonth()) > (yy*12+mm + n) )
									   	{dt=new Date(dt.getYear(),dt.getMonth(),0);}
								     Ext.getCmp("plManageMoneyPlan.endinInterestTime").setValue(dt);
								     
								     
								     
								       var month1=Ext.getCmp("plManageMoneyPlan.lockingLimit").getValue();
								       var n=parseInt(month1);
								       var s=dtstr.split("-");
									   var yy=parseInt(s[0]);
									   var mm=parseInt(s[1])-1;
									   var dd=parseInt(s[2]);
									   var dt=new Date(yy,mm,dd);
									   dt.setMonth(dt.getMonth()+n);
									   if( (dt.getYear()*12+dt.getMonth()) > (yy*12+mm + n) )
									   	{dt=new Date(dt.getYear(),dt.getMonth(),0);}
								     
								      Ext.getCmp("plManageMoneyPlan.lockingEndDate").setValue(dt);
							}
							}
						}]
			}, {
				columnWidth : .34,
				layout : 'form',
				labelWidth : leffwidth,
				hidden :true,
				items : [{
							fieldLabel : '锁定截至日期',
						name : 'plManageMoneyPlan.lockingEndDate',
				          id:'plManageMoneyPlan.lockingEndDate',
							xtype : 'datefield',
							 format : 'Y-m-d H:i:s',
							 readOnly:true,
							anchor : '100%'
					
						}]
			}, {
				columnWidth : .34,
				layout : 'form',
				labelWidth : leffwidth,
				items : [{
							fieldLabel : '计息止日',
							name : 'plManageMoneyPlan.endinInterestTime',
							id:"plManageMoneyPlan.endinInterestTime",
							hidden : !this.isAllReadOnly,
							hideLabel : !this.isAllReadOnly,
							allowBlank:this.isAllReadOnly?false:true,
							xtype : 'datefield',
						     format : 'Y-m-d H:i:s',
							readOnly:true,
							anchor : '100%'
						 }]
			}/*, {
				columnWidth : .31,
				labelWidth : leffwidth,
				layout : 'form',
				items : [{
							fieldLabel : '风险保证金比例',
							xtype : 'numberfield',
							name : 'plManageMoneyPlan.riskProtectionRate',
							hidden : !this.isAllReadOnly,
							hideLabel : !this.isAllReadOnly,
							fieldClass : 'field-align',
							anchor : '100%'
						}]
			}*/, {
				columnWidth : 1,
				labelWidth : leffwidth,
				layout : 'form',
				items : [this.isAllReadOnly==true?{
							fieldLabel : '招标说明',
							name : 'plManageMoneyPlan.bidRemark',
							readOnly : this.isAllReadOnly,
							xtype : 'textarea',
							anchor : '100%'
							}:{
							fieldLabel : '招标说明',
							name : 'plManageMoneyPlan.bidRemark',
							xtype : 'fckeditor',
							anchor : '100%'
						},{
									xtype:"hidden",
									id : 'plBidPlan.rebateType',
								    name : 'plManageMoneyPlan.rebateType'
								},{
									xtype:"hidden",
									id : 'plBidPlan.rebateWay',
								    name : 'plManageMoneyPlan.rebateWay'
								},{
									xtype:"hidden",
									id : 'plBidPlan.coupon',
								    name : 'plManageMoneyPlan.coupon'
								},{
									xtype:"hidden",
									id : 'plBidPlan.novice',
								    name : 'plManageMoneyPlan.novice'
								}]
			}, { 
				columnWidth : 1,
				labelWidth : leffwidth,
				layout : 'form',
				items : [this.uploads]
			}, { 
				columnWidth : 1,
				hidden : this.seeHidden,
				labelWidth : leffwidth,
				layout : 'form',
				items : [this.PlManageMoneyPlanOrder,this.PlManageMoneyPlanCoupons]
			}]
		});

		// 加载表单对应的数据
		if (this.mmplanId != null && this.mmplanId != 'undefined') {
			this.formPanel.loadData({
				url : __ctxPath
						+ '/creditFlow/financingAgency/getPlManageMoneyPlan.do?mmplanId='
						+ this.mmplanId,
				root : 'data',
				preName : 'plManageMoneyPlanfsdfsd',
				success : function(response, options) {
					 /*var respText = response.responseText;  
				     var alarm_fields = Ext.util.JSON.decode(respText); */
					var respText = response.responseText;
					var result = Ext.util.JSON.decode(respText);
					
					 if(result.data.plManageMoneyPlan.novice==1){
					 	Ext.getCmp("check_novice").setValue(true);
					 		if(result.data.plManageMoneyPlan.state!=0){
							 	Ext.getCmp("check_novice").setDisabled(true);
					 		}
					 }
					 if(result.data.plManageMoneyPlan.addRate>0){
					 	Ext.getCmp("check_addRate").setValue(true);
					 		if(result.data.plManageMoneyPlan.state!=0){
							 	Ext.getCmp("check_addRate").setDisabled(true);
							 	Ext.getCmp("addRate_text").addClass("readOnlyClass");
							 	Ext.getCmp("addRate_text").setReadOnly(true);
					 		}
						}else{
							 if(result.data.plManageMoneyPlan.rebateType==1){
							  	Ext.getCmp("check_e").setValue(true);
							 }else if(result.data.plManageMoneyPlan.rebateType==2){
							  	Ext.getCmp("check_f").setValue(true);
							 }else if(result.data.plManageMoneyPlan.rebateType==3){
							  	Ext.getCmp("check_g").setValue(true);
							 }else if(result.data.plManageMoneyPlan.rebateType==4){
							  	Ext.getCmp("check_h").setValue(true);
							 }
							 if(result.data.plManageMoneyPlan.rebateWay==1){
							 	Ext.getCmp("check_ee").setValue(true);
							 
							 }else if(result.data.plManageMoneyPlan.rebateWay==2){
							 	Ext.getCmp("check_ff").setValue(true);
							 	
							 }else if(result.data.plManageMoneyPlan.rebateWay==3){
							 	Ext.getCmp("check_ii").setValue(true);
							 	
							 }
							 if(result.data.plManageMoneyPlan.coupon==1){
							 	Ext.getCmp("check_coupon").setValue(true);
							 		if(result.data.plManageMoneyPlan.state!=0){
									 	Ext.getCmp("check_coupon").setDisabled(true);
							 		}
							 }
							 }
							 	if(result.data.plManageMoneyPlan.state!=0){
									 Ext.getCmp("check_e").setDisabled(true);
									 Ext.getCmp("check_f").setDisabled(true);
									 Ext.getCmp("check_g").setDisabled(true);
									 Ext.getCmp("check_h").setDisabled(true);
									 Ext.getCmp("check_ii").setDisabled(true);
									 Ext.getCmp("check_ff").setDisabled(true);
									 Ext.getCmp("check_ee").setDisabled(true);
									 Ext.getCmp("check_coupon").setDisabled(true);
									 Ext.getCmp("check_novice").setDisabled(true);
						 			 Ext.getCmp("check_addRate").setDisabled(true);
									 Ext.getCmp("maxMoney_text").addClass("readOnlyClass");
									 Ext.getCmp("maxMoney_text").setReadOnly(true);
									 Ext.getCmp("ratio_text").addClass("readOnlyClass");
									 Ext.getCmp("ratio_text").setReadOnly(true);
							 	}
				
				   
				}
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
		var fpl = this.formPanel;
		var flag=true;
		if(this.isAllReadOnly){
			var order=this.PlManageMoneyPlanOrder.gridPanel.store.data;
			var date=null;
			if(order){
				for(var i=0;i<order.length;i++){
					date=order.items[0].data;
					if(date.couponsMoney){//先判断有无使用优惠券   有使用优惠券
						var couponsOrder=this.PlManageMoneyPlanCoupons.gridPanel.store.data;
						if(couponsOrder.length<1){
							flag=false;
							break;
						}
					}
				}
			}
			
			if(flag){
				$postForm({
					formPanel : this.formPanel,
					scope : this,
					url : __ctxPath
							+ '/creditFlow/financingAgency/savePlanPlManageMoneyPlan.do?keystr=mmplan',
					callback : function(fp, action) {
						 this.close();
					}
				});
			}else{
				Ext.Msg.alert('操作信息','<font color=red>请先生成投资人奖励明细台账！<font>')
			}
			
		}else{
			var coupon = this.getCmpByName('plManageMoneyPlan.coupon').getValue();
			var checkaddRate = this.getCmpByName('checkaddRate').getValue();
			if(coupon==true){
				var rebateType=this.getCmpByName('plManageMoneyPlan.rebateType').getValue();
				if(rebateType==""){
					Ext.Msg.alert('操作信息','请选择返利类型');
					return;
				}
				if(rebateType!=4){
					var returnRatio = this.getCmpByName('plManageMoneyPlan.returnRatio').getValue();
					var maxCouponMoney = this.getCmpByName('plManageMoneyPlan.maxCouponMoney').getValue();
					if(returnRatio==""){
						Ext.Msg.alert('操作信息','面值折现例不能为空');
						return;
					}
					if(maxCouponMoney==""){
						Ext.Msg.alert('操作信息','单笔最大面值不能为空');
						return;
					}
				}
				var rebateWay=this.getCmpByName('plManageMoneyPlan.rebateWay').getValue();
				if(rebateWay==""){
					Ext.Msg.alert('操作信息','请选择返利方式');
					return;
				}
			}
			if(checkaddRate==true){
				var addRate = this.getCmpByName('plManageMoneyPlan.addRate').getValue();
				if(addRate==""){
					Ext.Msg.alert('操作信息','请填写普通加息利率');
					return;
				}
			}
			$postForm({
				formPanel : this.formPanel,
				scope : this,
				url : __ctxPath
						+ '/creditFlow/financingAgency/savePlManageMoneyPlan.do?keystr=mmplan',
				callback : function(fp, action) {
					var obj = Ext.util.JSON.decode(action.response.responseText);
					if(obj.id=="0"){
						Ext.ux.Toast.msg('操作信息', ' 未找到该收款用户');
						return false;
					}else{
						fpl.getCmpByName("plManageMoneyPlan.mmplanId").setValue(obj.id);
					this.uploads.projId = obj.id;
					this.uploads.mark=this.uploads.tableName+'.'+obj.id;
					this.uploads.typeisfile="typeisonlyfile";
					
					var mark=this.uploads.tableName+'.'+obj.id;
					var typeisfile="typeisonlyfile";
					var gridstore =this.uploads.grid_UploadsPanel.getStore()
					gridstore.on('beforeload', function(gridstore, o) {
						var new_params = {
							mark : mark,
							typeisfile : typeisfile
						};
						Ext.apply(o.params, new_params);
					});
					var gridPanel = Ext.getCmp('PlManageMoneyPlanGrid');
					if (gridPanel != null) {
						gridPanel.getStore().reload();
					}
					 this.close();
					}
					
				}
			});
		}
		

	},// end of save
    submit:function(){
var coupon = this.getCmpByName('plManageMoneyPlan.coupon').getValue();
			var checkaddRate = this.getCmpByName('checkaddRate').getValue();
			if(coupon==true){
				var rebateType=this.getCmpByName('plManageMoneyPlan.rebateType').getValue();
				if(rebateType==""){
					Ext.Msg.alert('操作信息','请选择返利类型');
					return;
				}
				if(rebateType!=4){
					var returnRatio = this.getCmpByName('plManageMoneyPlan.returnRatio').getValue();
					var maxCouponMoney = this.getCmpByName('plManageMoneyPlan.maxCouponMoney').getValue();
					if(returnRatio==""){
						Ext.Msg.alert('操作信息','面值折现例不能为空');
						return;
					}
					if(maxCouponMoney==""){
						Ext.Msg.alert('操作信息','单笔最大面值不能为空');
						return;
					}
				}
				var rebateWay=this.getCmpByName('plManageMoneyPlan.rebateWay').getValue();
				if(rebateWay==""){
					Ext.Msg.alert('操作信息','请选择返利方式');
					return;
				}
			}
			if(checkaddRate==true){
				var addRate = this.getCmpByName('plManageMoneyPlan.addRate').getValue();
				if(addRate==""){
					Ext.Msg.alert('操作信息','请填写普通加息利率');
					return;
				}
			}
	$postForm({
			formPanel : this.formPanel,
			scope : this,
			url : __ctxPath
					+ '/creditFlow/financingAgency/savePlManageMoneyPlan.do?keystr=mmplan',
			callback : function(fp, action) {
				
				var gridPanel = Ext.getCmp('PlManageMoneyPlanGrid');
				if (gridPanel != null) {
					gridPanel.getStore().reload();
				}
				 this.close();
			}
		});
	
	
}
});