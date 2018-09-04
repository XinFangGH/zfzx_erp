
/**
 * 获取浏览器类型 by shendexuan
 * 
 * @return {}
 */

FinancingEarlyRepaymentPanel = Ext.extend(Ext.Panel, {
	layout : "form",
	autoHeight : true,
	labelAlign : 'right',
	idDefinition:'earlyRepayment',
	constructor : function(_cfg) {
		if (_cfg == null) {
			_cfg = {};
		}
		if (_cfg.idDefinition) {
			this.idDefinition = _cfg.idDefinition;
		}
		Ext.applyIf(this, _cfg);
		this.initComponents();
		this.idDefinition=this.projectId+this.idDefinition;
		var surplusnotMoney=this.surplusnotMoney;
		var leftlabel = 115;
		var centerlabel = 115;
		var rightlabel = 115;
	
	       FinancingEarlyRepaymentPanel.superclass.constructor.call(this, {
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
					name : 'slEarlyRepaymentRecord.slEarlyRepaymentId',
					xtype : 'hidden'
				},{
					name : 'slEarlyRepaymentRecord.creator',
					xtype : 'hidden'
				},{
					name : 'slEarlyRepaymentRecord.opTime',
					xtype : 'hidden'
				},{
					xtype : 'hidden',
					name : 'slEarlyRepaymentRecord.checkStatus'
				},{
					columnWidth : .95, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : leftlabel,
					items : [{
						xtype : "textarea",
						name : "slEarlyRepaymentRecord.reason",
						allowBlank : false,
						readOnly : this.isAllReadOnly,
						fieldLabel : '申请原因',
						value : this.record != null
								? this.record.data.reason
								: "",
						anchor : '100%'
					}]
				}, {
					columnWidth : .05, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : 20,
					items : [{
								fieldLabel : "",
								labelSeparator : '',
								anchor : "100%"
							}]
				},{
					columnWidth : .3, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : leftlabel,
					items : [{
						xtype : "textfield",
						labelWidth : rightlabel,
						fieldLabel : "提前还本金金额",
						fieldClass : 'field-align',
						name : "projectMoney1",
						readOnly : this.isAllReadOnly,
						value :0,
						allowNegative : false, // 允许负数
						style : {
							imeMode : 'disabled'
						},
						blankText : "提前还本金金额不能为空，请正确填写!",
						allowBlank : false,
						anchor : "100%",// 控制文本框的长度
						listeners : {
							scope : this,
							afterrender : function(obj) {
								obj.on("keyup")
							},
							change : function(nf) {
                                 var value = nf.getValue();
								var continuationMoneyObj = nf.nextSibling();
								var continuationMoneyObjintivalue = continuationMoneyObj
										.getValue();
								var index = value.indexOf(".");
								if (index <= 0) { // 如果第一次输入 没有进行格式化
									nf.setValue(Ext.util.Format.number(value,
											'0,000.00'))
									continuationMoneyObj.setValue(value);
								} else {

									if (value.indexOf(",") <= 0) {
										var ke = Ext.util.Format.number(value,
												'0,000.00')
										nf.setValue(ke);
										continuationMoneyObj.setValue(value);
									} else {
										var last = value.substring(index + 1,
												value.length);
										if (last == 0) {
											var temp = value
													.substring(0, index);
											temp = temp.replace(/,/g, "");
											continuationMoneyObj.setValue(temp);
										} else {
											var temp = value.replace(/,/g, "");
											continuationMoneyObj.setValue(temp);
										}
									}

								}
								var continuationMoneyObj1 = this.getCmpByName("shengyubenjin");
								/*if (eval(continuationMoneyObj.getValue()) > eval(continuationMoneyObj1.getValue())) {
									Ext.Msg.alert("操作信息", "不能大于未还本金金额");
									//continuationMoneyObj1.setValue(surplusnotMoney);
									continuationMoneyObj.setValue(0);
									nf.setValue(0);
								} else {

									continuationMoneyObj1.setValue(Ext.util.Format.number(surplusnotMoney- continuationMoneyObj.getValue(),'0,000.00'));
								}*/
							}
						}
					}, {
						xtype : "hidden",
						name : "slEarlyRepaymentRecord.earlyProjectMoney",
						value :0
					}]
				}, {
					columnWidth : .05, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : 20,
					items : [{
								fieldLabel : "元 ",
								labelSeparator : '',
								anchor : "100%"
							}]
				},{
					columnWidth : .28,
					layout : 'form',
					labelWidth : rightlabel,
					items :[{
						xtype :'numberfield',//textfield
						fieldLabel : '调整利率',
						name : 'slEarlyRepaymentRecord.accrual',// 利率
						fieldClass : 'field-align',
						anchor : '100%',
						value : 0
					}]
				}, {
					columnWidth : .05, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : 30,
					items : [{
								fieldLabel : "%/年 ",
								labelSeparator : '',
								anchor : "100%"
							}]
				},{
					columnWidth : .28, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : rightlabel,
					hidden:true,
					items : [ {
						fieldLabel : '剩余本金金额',
						xtype : "textfield",
						allowBlank : true,
//						anchor : '100%',
						readOnly : true,
						fieldClass : 'field-align',
						name:"shengyubenjin",
						value : surplusnotMoney
					}]
				}/*, {
					columnWidth : .05, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : 20,
					items : [{
								fieldLabel : "元 ",
								labelSeparator : '',
								anchor : "100%"
							}]
				}*/, {
					columnWidth : .275, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : centerlabel+21,
					name :"bgks",
					items : [{
						xtype : 'datefield',
						fieldLabel : '提前还款日期',
						format : 'Y-m-d',
						name : 'slEarlyRepaymentRecord.earlyDate',
						anchor : '100%'
					}
					
					/*{
						xtype : 'combo',
						mode : 'local',
						displayField : 'itemName',
						valueField : 'itemId',
						editable : false,
						width : 70,
						store : new Ext.data.SimpleStore({
							autoLoad : true,
							url :__ctxPath+ '/smallLoan/finance/getPrepayintentPeriodSlEarlyRepaymentRecord.do',
							fields : ['itemId', 'itemName'],
							baseParams : {projectId:this.projectId,businessType:this.businessType}
							//data : obstorepayintentPeriod1
						}),
						triggerAction : "all",
						fieldLabel : "<font color='red'>*</font>提前还款开始期数",
						readOnly : this.isAllReadOnly,
						hiddenName : "prepayintentPeriod1",
						name : "prepayintentPeriod1",
						anchor : '100%',
						listeners : {
							scope : this,
							'select' : function(combox,record,index){
								var value=combox.getValue()
								var value1=value.substring(value.lastIndexOf(".")+1,value.length)
								combox.nextSibling().setValue(value1)
								var value2=value.substring(0,value.lastIndexOf("."));
								var bgksPanel=this.getCmpByName('bgks')
								var bgksPanel1=this.getCmpByName('bgks1')
								var accrualtypePanel=this.getCmpByName('slEarlyRepaymentRecord.accrualtype')
								Ext.Ajax.request({
									url : __ctxPath
											+ '/smallLoan/finance/getAccrualTypeSlEarlyRepaymentRecord.do',
									method : 'post',
									params : {
										"projectId" : this.projectId,
										businessType:this.businessType,
										"slSuperviseRecordId" : value2
									},
									success : function(response,options) {
										var obj=Ext.util.JSON.decode(response.responseText);
										accrualtypePanel.setValue(obj.accrualType);
										if(obj.accrualType=='ontTimeAccrual'){
											Ext.Msg.alert('操作信息','该项目计息方式为一次性还本付息,需按日期方式提前还款')
											bgksPanel.hide()
											bgksPanel1.show()
										}
									}
								})
							}
						}
					}*/,{
					    xtype : 'hidden',
					    name : 'slEarlyRepaymentRecord.prepayintentPeriod'
					},{
						xtype : 'hidden',
						name :'slEarlyRepaymentRecord.accrualtype'
					}]
				}/*,{
					columnWidth : .275,
					layout : "form", // 从上往下的布局
					labelWidth : centerlabel+21,
					name :"bgks1",
					hidden:true,
					items : [{
						xtype : 'datefield',
						fieldLabel : '提前还款日期',
						name : 'slEarlyRepaymentRecord.earlyDate',
						anchor : '100%',
						format : 'Y-m-d',
						allowBlank : false,
						readOnly : this.isAllReadOnly,
						value : Ext.util.Format.date(new Date(),'Y-m-d')
					}]
				}*/


				]
			
			}]
		});
	},
	initComponents : function() {
	},
	cc : function() {

		// alert('ddd');
	}
});







  Ext.reg('FinanceEarlyRepaymentPanel', FinanceEarlyRepaymentPanel);

