/**
 * @author
 * @class PlManageMoneyTypeView
 * @extends Ext.Panel
 * @description [PlManageMoneyType]管理
 * @company 智维软件
 * @createtime:
 */
PlManageMoneyProductInfoForm = Ext.extend(Ext.Panel, {
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		var leftwidth=80;
		PlManageMoneyProductInfoForm.superclass.constructor.call(this, {
			layout : 'column',
			items : [{
				columnWidth : .33,
				labelWidth : leftwidth,
				layout : 'form',
				items : [{          
					xtype : 'hidden',
                    id:'plManageMoneyPlan.mmplanId',
					name : 'plManageMoneyPlan.mmplanId'
				},
				   {					
					fieldLabel : '理财产品类型',
					hiddenName : 'plManageMoneyPlan.manageMoneyTypeId',
					readOnly:true,
					allowBlank : false,
					anchor : '91%',
					xtype : "combo",
					displayField : 'itemName',
					valueField : 'itemId',
					triggerAction : 'all',
					store : new Ext.data.ArrayStore({
						url : __ctxPath
										+ '/creditFlow/financingAgency/getListbykeystrPlManageMoneyType.do?keystr=mmproduce',
								fields : ['itemId', 'itemName'],
								autoLoad : true
					}),
					mode : 'remote',
					editable : false,
					blankText : "理财计划类型不能为空，请正确填写!",
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
				
				
			
		},{
		  xtype:"hidden",
		 name : 'plManageMoneyPlan.mmName'
		
		}]
			}, {
				columnWidth : .33,
				layout : 'form',
				labelWidth : leftwidth,
				items : [{
							fieldLabel : '产品编号',
							name : 'plManageMoneyPlan.mmNumber',
								readOnly:true,
							allowBlank : false,
							xtype : 'textfield',
							anchor : '91%'
						}]
			}, {
				columnWidth : .31,
				labelWidth : leftwidth,
				layout : 'form',
				items : [{
							fieldLabel : '投资期限',
							name : 'plManageMoneyPlan.investlimit',
								readOnly:true,
							fieldClass : 'field-align',
							allowBlank : false,
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
			},  {
				columnWidth : .33,
				layout : 'form',
				labelWidth : leftwidth,
				items : [{
							fieldLabel : '收益方式',
							name : 'plManageMoneyPlan.benefitWay',
								readOnly:true,
							xtype : 'textfield',
							anchor : '91%'
						}]
			}, {
				columnWidth : .33,
				layout : 'form',
				labelWidth : leftwidth,
				items : [{
					fieldLabel : '购买开放时间',
					name : 'plManageMoneyPlan.buyStartTime',
					readOnly:true,
					xtype : 'datefield',
					format : 'Y-m-d H:i:s',
					anchor : '91%'
				}]
			}, {
				columnWidth : .31,
				layout : 'form',
				labelWidth : leftwidth,
				items : [{
							fieldLabel : '购买结束时间',
							name : 'plManageMoneyPlan.buyEndTime',
							xtype : 'datefield',
							format : 'Y-m-d H:i:s',
								readOnly:true,
							anchor : '100%'
						}]
			}, {
				columnWidth : .3,
				labelWidth : leftwidth,
				layout : 'form',
				items : [{
							fieldLabel : '投资起点金额',
							name : 'plManageMoneyPlan.startMoney',
							fieldClass : 'field-align',
							xtype : 'moneyfield',
								readOnly:true,
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
				labelWidth : leftwidth,
				layout : 'form',
				items : [{
							fieldLabel : '递增金额',
							fieldClass : 'field-align',
							xtype : 'moneyfield',
							readOnly:true,
							name : 'plManageMoneyPlan.riseMoney',
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
				labelWidth : leftwidth,
				layout : 'form',
				items : [{
							fieldLabel : '单笔投资上限',
							xtype : 'moneyfield',
							name : 'plManageMoneyPlan.limitMoney',
							fieldClass : 'field-align',
								readOnly:true,
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
				labelWidth : leftwidth,
				layout : 'form',
				items : [{
					fieldLabel : '起息条件',
					xtype : 'textfield',
					name : 'plManageMoneyPlan.startinInterestCondition',
						readOnly:true,
					anchor : '91%'
				}]
			}, {
				columnWidth : .33,
				labelWidth : leftwidth,
				layout : 'form',
				items : [{
							fieldLabel : '到期赎回方式',
							xtype : 'textfield',
							name : 'plManageMoneyPlan.expireRedemptionWay',
								readOnly:true,
							anchor : '91%'
						}]
			}, {
				columnWidth : .31,
				labelWidth : leftwidth,
				layout : 'form',
				items : [{
							fieldLabel : '费用收取方式',
							xtype : 'textfield',
							name : 'plManageMoneyPlan.chargeGetway',
								readOnly:true,
							anchor : '100%'
						}]
			}, {
				columnWidth : .33,
				labelWidth : leftwidth,
				layout : 'form',
				items : [{
							fieldLabel : '保障方式',
							xtype : 'textfield',
							name : 'plManageMoneyPlan.guaranteeWay',
								readOnly:true,
							anchor : '91%'
						}]
			}, {
				columnWidth : .3,
				labelWidth : leftwidth,
				layout : 'form',
				items : [{
							fieldLabel : '年化收益率',
							xtype : 'numberfield',
							allowBlank : false,
							name : 'plManageMoneyPlan.yeaRate',
							fieldClass : 'field-align',
								readOnly:true,
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
		columnWidth : .17,
		layout : 'form',
		labelWidth : leftwidth,
		items : [{
							fieldLabel : '是否循环出借',
							xtype : 'zwcheckbox',
							name : 'isCyclingLend',
							preName:'plManageMoneyPlan',
							disabled:true,
							anchor : '100%'}
							,{
						xtype : 'hidden',
						name : 'plManageMoneyPlan.isCyclingLend',
						value : 0
					
			}]
	}, {
		columnWidth : .17,
		layout : 'form',
		labelWidth : leftwidth,
		items : [{
							fieldLabel : '是否一次性',
							xtype : 'zwcheckbox',
							name : 'isOne',
							preName:'plManageMoneyPlan',
							disabled:true,
							anchor : '100%'}
							,{
						xtype : 'hidden',
						name : 'plManageMoneyPlan.isOne',
						value : 0
					
			}]
	}, {
				columnWidth : 1,
				layout : 'form',
				labelWidth : leftwidth,
				items : [{
							fieldLabel : '投资范围',
							name : 'plManageMoneyPlan.investScope',
								readOnly:true,
							xtype : 'textfield',
							anchor : '97%'
						}]
			},  {
				columnWidth : 1,
				labelWidth : leftwidth,
				layout : 'form',
				items : [{
							fieldLabel : '产品说明',
							name : 'plManageMoneyPlan.bidRemark',
								readOnly:true,
							xtype : 'textarea',
							anchor : '97%'
						}]
			}]
		});
	}
	
	
});
