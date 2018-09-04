
/**
 * 获取浏览器类型 by shendexuan
 * 
 * @return {}
 */

FinanceAlterAccrualPanel = Ext.extend(Ext.Panel, {
	layout : "form",
	autoHeight : true,
	labelAlign : 'right',
	idDefinition:'alterAccrual',
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
		var slAlterAccrualRecorddata = this.slAlterAccrualRecorddata;
		var leftlabel = 80;
		var centerlabel = 115;
		var rightlabel = 115;
			FinanceAlterAccrualPanel.superclass.constructor.call(this, {
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
					name : 'slAlterAccrualRecord.slAlteraccrualRecordId',
					xtype : 'hidden'
				},{
					name : 'slAlterAccrualRecord.creator',
					xtype : 'hidden'
				},{
					name : 'slAlterAccrualRecord.opTime',
					xtype : 'hidden'
				},{
					name : 'slAlterAccrualRecord.checkStatus',
					xtype : 'hidden'
				},{
					columnWidth : .95, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : leftlabel,
					items : [{
						xtype : "textarea",
						name : "slAlterAccrualRecord.reason",
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
					hidden:true,
					items : [{
						xtype : "textfield",
						labelWidth : rightlabel,
						fieldLabel : "剩余本金金额",
						fieldClass : 'field-align',
						name : "projectMoney1",
						readOnly : this.isAllReadOnly,
					    value : surplusnotMoney,
						allowNegative : false, // 允许负数
						style : {
							imeMode : 'disabled'
						},
						blankText : "剩余本金金额不能为空，请正确填写!",
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
									var continuationMoneyObj1 = nf.nextSibling()
										.nextSibling();
								if (eval(continuationMoneyObj.getValue()) > eval(surplusnotMoney)) {
									Ext.Msg.alert("", "不能大于未还本金金额");
									continuationMoneyObj1
											.setValue(surplusnotMoney);
									continuationMoneyObj.setValue(0);
									nf.setValue(0);
								} else {
	
									continuationMoneyObj1
											.setValue(Ext.util.Format
													.number(
															surplusnotMoney
																	- continuationMoneyObj
																			.getValue(),
															'0,000.00'));
								}
							}
						}
					}, {
						xtype : "hidden",
						value : surplusnotMoney,
						name : "slAlterAccrualRecord.alterProjectMoney"
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
				}*/,{
					columnWidth : .3, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : leftlabel,
					items : [{
						xtype : "numberfield",
						name : "slAlterAccrualRecord.accrual",
						value : slAlterAccrualRecorddata==null?null:slAlterAccrualRecorddata.accrual,
						fieldClass : 'field-align',
						allowBlank : false,
						readOnly : this.isAllReadOnly,
						anchor : "100%",
						fieldLabel : "贷款利率"
	
					}]
				}, {
					columnWidth : .05, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : 40,
					items : [{
						fieldLabel : "%/月",
						labelSeparator : '',
						anchor : "100%"
					}]
				},{
					columnWidth : .25,
					layout : 'form',
					labelWidth : 80,
					labelAlign : 'right',
					items : [{
						xtype : 'datefield',
						fieldLabel : '利率变更日期',
						format : 'Y-m-d',
						anchor : '100%',
						name : 'slAlterAccrualRecord.alelrtDate'
					}]
				}/*, {
					columnWidth : .25, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : centerlabel+21,
					name:"ShowOrHiddenAlterpayintentPeriod",
					items : [{
						xtype : 'combo',
						displayField : 'itemName',
						valueField : 'itemId',
						editable : false,
						width : 70,
						store : new Ext.data.SimpleStore({
							autoLoad : true,
							url :__ctxPath+ '/smallLoan/finance/getPrepayintentPeriodSlEarlyRepaymentRecord.do?projectId='+this.projectId+'&businessType=Financing',
							fields : ['itemId', 'itemName'],
							params : {projectId:this.projectId,businessType:"Financing"}
						}),
						triggerAction : "all",
						fieldLabel : "<font color='red'>*</font>变更开始期数",
						readOnly : this.isAllReadOnly,
						hiddenName : "alterpayintentPeriod",
						name : "alterpayintentPeriod",
						anchor : '100%',
						listeners : {
							scope : this,
							'select' : function(combox,record,index){
								var value=combox.getValue()
								value=value.substring(value.lastIndexOf(".")+1,value.length)
								combox.nextSibling().setValue(value) 
							}
						}
					},{
						xtype : 'hidden',
						name : 'slAlterAccrualRecord.alterpayintentPeriod'
					}]
				}*/, {
							columnWidth : .31, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : 126,
							items : [{
								xtype : "hidden",
								name : "slAlterAccrualRecord.financeServiceOfRate",
								value : 0
							
							}]
						}
	
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







  Ext.reg('FinanceAlterAccrualPanel', FinanceAlterAccrualPanel);

