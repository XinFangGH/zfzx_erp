/**
 * @author 
 * @createtime 
 * @class SettlementReviewerPayForm
 * @extends Ext.Window
 * @description SettlementReviewerPay表单
 * @company 智维软件
 */
SettlementReviewerPayForm = Ext.extend(Ext.Panel, {
	layout : 'anchor',
	anchor : '100%',
	companyHidden : true,
	constructor : function(_cfg) {
		if (typeof(_cfg.spouseHidden) != "undefined") {
			this.spouseHidden = _cfg.spouseHidden;
		}
		Ext.applyIf(this, _cfg);
		SettlementReviewerPayForm.superclass.constructor.call(this, {
			layout : 'column',
			items : [{
						columnWidth : .5,
						layout : 'form',
						defaults : {
							anchor : '90%'
						},
						labelWidth : 85,
						items : [{
									xtype : 'hidden',
									name : 'settlementReviewerPay.id'
								},{
									xtype : 'textfield',
									fieldLabel : '收款部门',
									allowBlank : false,
									readOnly : true,
									name : 'settlementReviewerPay.organName'
								}]
					}, {
						columnWidth : .5,
						layout : 'form',
						defaults : {
							anchor : '90%'
						},
						labelWidth : 80,
						items : [{
								xtype : 'combo',
								hiddenName : 'settlementReviewerPay.paymentMethod',
								displayField : 'itemName',
								valueField : 'itemValue',
								triggerAction : 'all',
								allowBlank : false,
								readOnly:this.isreadOnly,
								mode : 'local',
								store : new Ext.data.ArrayStore({
											fields : ['itemValue', 'itemName'],
											data : [/*['1', '线上支付'],*/
													['2', '线下银行转账'],
													['3', '线下现金支付']]
										}),
								fieldLabel : '支付方式',
								listeners : {
									scope : this,
									afterrender : function(combo) {
									},
									select : function(v) {
										var bankName = this.getCmpByName('settlementReviewerPay.bankName');
										var bankNum = this.getCmpByName('settlementReviewerPay.bankNum');
										if(v.getValue()=='2'){
											bankName.ownerCt.show();
											bankNum.ownerCt.show();
											bankName.allowBlank=false;	
											bankNum.allowBlank=false;	
										}else{
											bankName.ownerCt.hide();
											bankNum.ownerCt.hide();
											bankName.allowBlank=true;	
											bankNum.allowBlank=true;	
										}
										
									}
								}
							}]
					},{
						columnWidth : .5,
						layout : 'form',
						defaults : {
							anchor : '90%'
						},
						labelWidth : 80,
						name : 'bankName',
						hidden : true,
						items : [{
									xtype : 'textfield',
									fieldLabel : '银行名称',
									allowBlank : false,
									readOnly:this.isreadOnly,
									name : 'settlementReviewerPay.bankName'
								}]
					},{
						columnWidth : .5,
						layout : 'form',
						defaults : {
							anchor : '90%'
						},
						labelWidth : 80,
						hidden : true,
						name : 'bankNum',
						items : [{
									xtype : 'textfield',
									fieldLabel : '银行账号',
									allowBlank : false,
									readOnly:this.isreadOnly,
									name : 'settlementReviewerPay.bankNum'
								}]
					},{
						columnWidth : .5,
						layout : 'form',
						defaults : {
							anchor : '90%'
						},
						labelWidth : 80,
						items : [{
									xtype : 'textfield',
									fieldLabel : '经办人',
									allowBlank : true,
									readOnly : true,
									name : 'settlementReviewerPay.managerName'
								}]
					},{
						columnWidth : .5,
						layout : 'form',
						defaults : {
							anchor : '90%'
						},
						labelWidth : 80,
						items : [{
									xtype : 'datefield',
									fieldLabel : '经办日期',
									allowBlank : false,
									readOnly : true,
									format:'Y-m-d',
									value :new Date(),
									name : 'settlementReviewerPay.managerDate'
								}]
					},{
						columnWidth : .5,
						layout : 'form',
						defaults : {
							anchor : '90%'
						},
						labelWidth : 80,
						items : [{
									xtype : 'textfield',
									fieldLabel : '审核人',
									allowBlank : true,
									readOnly : true,
									name : 'settlementReviewerPay.reviewer'
								}]
					},{
						columnWidth : .5,
						layout : 'form',
						defaults : {
							anchor : '90%'
						},
						labelWidth : 80,
						items : [{
									xtype : 'textfield',
									fieldLabel : '支付人',
									allowBlank : true,
									readOnly : true,
									name : 'settlementReviewerPay.payer'
								}]
					},{
						columnWidth : .5,
						layout : 'form',
						defaults : {
							anchor : '90%'
						},
						labelWidth : 80,
						items : [{
									xtype : 'numberfield',
									fieldLabel : '应结算金额',
									allowBlank : true,
									readOnly : true,
									name : 'settlementReviewerPay.payMoney'
								}]
					},{
						columnWidth : .5,
						layout : 'form',
						defaults : {
							anchor : '90%'
						},
						labelWidth : 80,
						items : [{
									xtype : 'numberfield',
									fieldLabel : '实际结算金额',
									allowBlank : true,
									readOnly : true,
									name : 'settlementReviewerPay.factMoney'
								},{
									xtype : 'hidden',
									name : 'settlementReviewerPay.otherMoney'
								}]
					},{
						columnWidth : .15,
						layout : 'form',
						border : false,
						labelWidth : 60,
						items : [{
									text : '生成结算金额',
									xtype : 'button',
									scope : this,
									style : 'margin-left:30px',
									anchor : "90%",
									hidden:this.isreadOnly,
									iconCls : 'btn-form-design',
									handler : function(){
										debugger
										var change = this.changeObject;
										var infoObject = this.infoObject;
										var factPanel = this.getCmpByName('settlementReviewerPay.factMoney');
										var payPanel = this.getCmpByName('settlementReviewerPay.payMoney');
										var otherPanel = this.getCmpByName('settlementReviewerPay.otherMoney');
										var payMoney = 0;
										var factMoney = 0;
										var factPaymoney = 0;
										var changeDate = change.gridPanel.getStore().data.items;
										var infoDate = infoObject.gridPanel.getStore().data.items;
										if(infoDate.length>0){
											for(var i=0;i<infoDate.length;i++){
												payMoney = payMoney + parseFloat((infoDate[i].data.royaltyMoney==null||infoDate[i].data.royaltyMoney=='undefined'?0:infoDate[i].data.royaltyMoney));
											}
										}
										if(changeDate.length>0){
											for(var i=0;i<changeDate.length;i++){
												factMoney = factMoney + parseFloat((changeDate[i].data.incomeMoney==null||changeDate[i].data.incomeMoney=='undefined'?0:changeDate[i].data.incomeMoney));
												factPaymoney = factPaymoney + parseFloat((changeDate[i].data.payMoney==null||changeDate[i].data.payMoney=='undefined'?0:changeDate[i].data.payMoney));
											}
										}
										payPanel.setValue(payMoney);
										factPanel.setValue(payMoney+factMoney-factPaymoney);
										otherPanel.setValue(factMoney-factPaymoney);
									}
								}]
					},{
						columnWidth : .4,
						layout : 'form',
						border : false,
						hidden:this.isreadOnly,
						labelWidth : 10,
						items : [{
									html : "<font color=red>* 生成结算金额前，需要保证必要信息存在，否则会影响计算结果</font>",
									labelSeparator : '',
									anchor : "100%"
								}]
					}]
				})
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