/**
 * @author
 * @class PlManageMoneyTypeView
 * @extends Ext.Panel
 * @description [PlManageMoneyType]管理
 * @company 智维软件
 * @createtime:
 */
PlEarlyRedemptionForm = Ext.extend(Ext.Panel, {
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		var leftwidth=80;
		PlEarlyRedemptionForm.superclass.constructor.call(this, {
			layout : 'column',
			items : [{
				columnWidth : 1,
				layout : 'column',
				items :[{
					columnWidth : .33,
					layout : 'form',
					labelWidth : 120,
					labelAlign : 'right',
					items : [{
						xtype : 'hidden',
						name : 'plEarlyRedemption.earlyRedemptionId'
					},{
						xtype : 'hidden',
						name : 'dayRate'
					},{
						xtype : 'datefield',
						fieldLabel : '提前赎回日期',
						format : 'Y-m-d',
						readOnly : this.isReadOnly,
						allowBlank : false,
						name : 'plEarlyRedemption.earlyDate',
						anchor : '91%'
						
					}]
				},{
					columnWidth : .3,
					layout : 'form',
					labelWidth : 80,
					labelAlign : 'right',
					items : [{
						xtype : 'moneyfield',
						fieldLabel : '提前赎回本金',
						fieldClass : 'field-align',
						readOnly : true,
						allowBlank : false,
						name : 'plEarlyRedemption.earlyMoney',
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
				},{
					columnWidth : .3,
					layout : 'form',
					labelWidth : 80,
					labelAlign : 'right',
					items : [{
						xtype : 'numberfield',
						fieldLabel : '罚息天数',
						fieldClass : 'field-align',
						readOnly : this.isReadOnly,
						allowBlank : false,
						name : 'plEarlyRedemption.penaltyDays',
						anchor : '100%',
						listeners : {
							scope : this,
							change : function(nf){
								var earlyMoney=this.getCmpByName('earlyMoney1').hiddenField.value
								var penaltyDays=nf.getValue()
								if(null!=earlyMoney && earlyMoney!=''){
									var money=0;
									if(null!=penaltyDays && penaltyDays!=''){
										var dayRate=this.getCmpByName('dayRate').getValue()
										money=money+earlyMoney*dayRate*penaltyDays
									}
									var liquidatedDamagesRate=this.getCmpByName('plEarlyRedemption.liquidatedDamagesRate').getValue()
									if(null!=liquidatedDamagesRate && liquidatedDamagesRate!=''){
										money=money+earlyMoney*liquidatedDamagesRate/100
									}
								}
								this.getCmpByName('liquidatedDamages1').setValue(Ext.util.Format.number(money, '0,000.00'));
								this.getCmpByName('liquidatedDamages1').hiddenField.value=money;
							}
						}
					}]
				}, {
					columnWidth : .03, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : 18,
					items : [{
						fieldLabel : "<span style='margin-left:1px'>天</span> ",
						labelSeparator : '',
						anchor : "100%"

					}]
				}]
				
			},{
				columnWidth : 1,
				layout : 'column',
				items : [{
					columnWidth : .3,
					layout : 'form',
					labelWidth : 120,
					labelAlign : 'right',
					items : [{
						xtype : 'numberfield',
						fieldLabel : '提前赎回违约金比例',
						fieldClass : 'field-align',
						readOnly : this.isReadOnly,
						allowBlank : false,
						name : 'plEarlyRedemption.liquidatedDamagesRate',
						anchor : '100%',
						listeners : {
							scope : this,
							change : function(nf){
								var earlyMoney=this.getCmpByName('earlyMoney1').hiddenField.value
								var penaltyDays=this.getCmpByName('plEarlyRedemption.penaltyDays').getValue()
								if(null!=earlyMoney && earlyMoney!=''){
									var money=0;
									if(null!=penaltyDays && penaltyDays!=''){
										var dayRate=this.getCmpByName('dayRate').getValue()
										money=money+earlyMoney*dayRate*penaltyDays
									}
									var liquidatedDamagesRate=nf.getValue()
									if(null!=liquidatedDamagesRate && liquidatedDamagesRate!=''){
										money=money+earlyMoney*liquidatedDamagesRate/100
									}
								}
								this.getCmpByName('liquidatedDamages1').setValue(Ext.util.Format.number(money, '0,000.00'));
								this.getCmpByName('liquidatedDamages1').hiddenField.value=money;
							}
						}
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
					columnWidth : .3,
					layout : 'form',
					labelWidth : 80,
					labelAlign : 'right',
					items : [{
						xtype : 'moneyfield',
						fieldLabel : '违约金总额',
						fieldClass : 'field-align',
						readOnly : true,
						allowBlank : false,
						name : 'plEarlyRedemption.liquidatedDamages',
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
				}]
			}]
		});
	}
	
	
});
