
BidFinancePrepaymentForm = Ext.extend(Ext.Panel, {
	layout : "form",
	constructor : function(_cfg) {
		if (_cfg == null) {
			_cfg = {};
		}

		Ext.applyIf(this, _cfg);
	       BidFinancePrepaymentForm.superclass.constructor.call(this, {
	       	layout : 'column',
			items : [{
				columnWidth : .33,
				layout : 'form',
				labelWidth : 80,
				labelAlign : 'right',
				items  : [{
					name : 'slEarlyRepaymentRecord.slEarlyRepaymentId',
					xtype : 'hidden'
				},{
					xtype : 'datefield',
					fieldLabel : '提前还款日期',
					allowBlank : false,
					name : 'slEarlyRepaymentRecord.earlyDate',
					readOnly : this.readOnly,
					format : 'Y-m-d',
					anchor : '91.5%',
					listeners : {
						scope : this,
						change : function(nf){
							var surplusProjectMoney=this.getCmpByName('surplusProjectMoney1')
							surplusProjectMoney.setValue()
							surplusProjectMoney.hiddenField.value=null;
							var penaltyMoney=this.getCmpByName('penaltyMoney1');
							penaltyMoney.setValue()
							penaltyMoney.hiddenField.value=null;
						}
					}
				}]
			},{
				columnWidth : .3,
				layout : 'form',
				labelWidth : 80,
				labelAlign : 'right',
				items : [{
					xtype : 'moneyfield',
					fieldLabel : '提前还款本金',
					allowBlank : false,
					readOnly : this.readOnly,
					fieldClass : 'field-align',
					name : 'slEarlyRepaymentRecord.earlyProjectMoney',
					anchor : '100%',
					listeners : {
						scope : this,
						change : function(nf){
							var surplusProjectMoney=this.getCmpByName('surplusProjectMoney1')
							surplusProjectMoney.setValue()
							surplusProjectMoney.hiddenField.value=null;
							var penaltyMoney=this.getCmpByName('penaltyMoney1');
							penaltyMoney.setValue()
							penaltyMoney.hiddenField.value=null;
						}
					
					}
				}]
			},{
				columnWidth : .03, // 该列在整行中所占的百分比
				layout : "form", // 从上往下的布局
				labelWidth : 20,
				items : [{
							fieldLabel : "元 ",
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
					fieldLabel : '剩余本金',
					allowBlank : false,
					readOnly : true,
					fieldClass : 'field-align',
					name : 'slEarlyRepaymentRecord.surplusProjectMoney',
					anchor : '100%'
				}]
			},{
				columnWidth : .04, // 该列在整行中所占的百分比
				layout : "form", // 从上往下的布局
				labelWidth : 20,
				items : [{
							fieldLabel : "元 ",
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
					fieldLabel : '补偿天数',
					allowBlank : false,
					readOnly : this.readOnly,
					fieldClass : 'field-align',
					name : 'slEarlyRepaymentRecord.penaltyDays',
					anchor : '100%',
					listeners : {
						scope : this,
						change : function(nf){
							var penaltyMoney=this.getCmpByName('penaltyMoney1');
							penaltyMoney.setValue()
							penaltyMoney.hiddenField.value=null;
						}
					}
				}]
			},{
				columnWidth : .03, // 该列在整行中所占的百分比
				layout : "form", // 从上往下的布局
				labelWidth : 20,
				items : [{
							fieldLabel : "日",
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
					fieldLabel : '补偿息金额',
					allowBlank : false,
					readOnly : true,
					fieldClass : 'field-align',
					name : 'slEarlyRepaymentRecord.penaltyMoney',
					anchor : '100%'
				}]
			},{
				columnWidth : .04, // 该列在整行中所占的百分比
				layout : "form", // 从上往下的布局
				labelWidth : 20,
				items : [{
							fieldLabel : "元 ",
							labelSeparator : '',
							anchor : "100%"
						}]
			},{
				columnWidth : .3,
				layout : 'form',
				style : 'marginl-left:20px',
				hidden : this.readOnly,
				items : [{
					xtype : 'button',
					iconCls : 'btn-reset',
					text : '重新计算',
					scope : this,
					handler : function(){
							var earlyProjectMoney=this.getCmpByName('earlyProjectMoney1').hiddenField.value
							var earlyDate=this.getCmpByName('slEarlyRepaymentRecord.earlyDate').getValue()
							var penaltyDays=this.getCmpByName('slEarlyRepaymentRecord.penaltyDays').getValue()
							var surplusProjectMoney=this.getCmpByName('surplusProjectMoney1')
							var penaltyMoney=this.getCmpByName('penaltyMoney1')
							if(null!=earlyProjectMoney && earlyProjectMoney!='' && null!=earlyDate && earlyDate!=''){
									Ext.Ajax.request({
									url : __ctxPath + "/creditFlow/financingAgency/getSurplusProjectMoneyPlBidPlan.do",
									method : 'POST',
									scope:this,
									success : function(response, request) {
										obj = Ext.util.JSON.decode(response.responseText);
										if(typeof(obj.surplusProjectMoney)!='undefined' && obj.surplusProjectMoney!=null){
											surplusProjectMoney.setValue(Ext.util.Format.number(obj.surplusProjectMoney,',000,000,000.00'))
											surplusProjectMoney.hiddenField.value=obj.surplusProjectMoney
										}
										if(typeof(obj.penaltyMoney)!='undefined' && obj.penaltyMoney!=null){
											penaltyMoney.setValue(Ext.util.Format.number(obj.penaltyMoney,',000,000,000.00'))
											penaltyMoney.hiddenField.value=obj.penaltyMoney
										}
									},
									failure : function(response,request) {
										Ext.ux.Toast.msg('操作信息', '查询任务完成时限操作失败!');
									},
									params : {
										bidId : this.bidPlanId,
										earlyDate : earlyDate,
										earlyProjectMoney:earlyProjectMoney,
										penaltyDays:penaltyDays
									}
								});
							}
					}
				}]
			}]
		});
	}
});

  Ext.reg('BidFinancePrepaymentForm', BidFinancePrepaymentForm);

