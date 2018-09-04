/**
 * @author
 * @createtime
 * @class SlCompanyMainForm
 * @extends Ext.Window
 * @description SlCompanyMain表单
 * @company 北京互融时代软件有限公司
 */
PawnTicketLossForm = Ext.extend(Ext.Panel, {
	// 构造函数
	constructor : function(_cfg) {
	
		Ext.applyIf(this, _cfg);
		PawnTicketLossForm.superclass.constructor.call(this, {
			autoHeight : true,
			items :[{
					layout : 'column',
					border : false,
					items : [{
						xtype : 'hidden',
						name : 'pawnTicketLossRecord.projectId',
						value : this.projectId
					},{
						xtype : 'hidden',
						name : 'pawnTicketLossRecord.businessType',
						value : this.businessType
					},{
						xtype : 'hidden',
						name : 'pawnTicketLossRecord.lossRecordId'
					},{
						columnWidth : .5,
						layout : 'form',
						labelWidth : 85,
						labelAlign : 'right',
						items : [{
							xtype : 'datefield',
							fieldLabel : '挂失时间',
							format : 'Y-m-d',
							anchor : '100%',
							allowBlank : false,
							readOnly : this.isAllReadOnly,
							name : 'pawnTicketLossRecord.lossTime'
						}]
					},{
						columnWidth : .5,
						layout : 'form',
						labelWidth : 85,
						labelAlign : 'right',
						items : [{
							xtype : 'textfield',
							fieldLabel : '挂失人',
							anchor : '100%',
							readOnly : this.isAllReadOnly,
							allowBlank : false,
							name :'pawnTicketLossRecord.lossPerson'
						}]
					},{
						columnWidth : .47,
						layout : 'form',
						labelWidth : 85,
						labelAlign : 'right',
						items : [{
							xtype : "textfield",
							fieldLabel : "挂失费用",
							fieldClass : 'field-align',
							name : "lossCost1",
							readOnly : this.isAllReadOnly,
							allowNegative : false, // 允许负数
							style : {
								imeMode : 'disabled'
							},
							blankText : "挂失费用不能为空，请正确填写!",
							allowBlank : false,
							anchor : "100%",// 控制文本框的长度
							listeners : {
								scope : this,
								afterrender : function(obj) {
									obj.on("keyup")
								},
								change : function(nf) {
	
									var value = nf.getValue();
									var index = value.indexOf(".");
									if (index <= 0) { // 如果第一次输入 没有进行格式化
										nf.setValue(Ext.util.Format.number(value,
												'0,000.00'))
										this
												.getCmpByName("pawnTicketLossRecord.lossCost")
												.setValue(value);
									} else {
	
										if (value.indexOf(",") <= 0) {
											var ke = Ext.util.Format.number(value,
													'0,000.00')
											nf.setValue(ke);
											this
													.getCmpByName("pawnTicketLossRecord.lossCost")
													.setValue(value);
										} else {
											var last = value.substring(index + 1,
													value.length);
											if (last == 0) {
												var temp = value
														.substring(0, index);
												temp = temp.replace(/,/g, "");
												this
														.getCmpByName("pawnTicketLossRecord.lossCost")
														.setValue(temp);
														nf.setValue(Ext.util.Format.number(temp,
												            '0,000.00'))
											} else {
												var temp = value.replace(/,/g, "");
												this
														.getCmpByName("pawnTicketLossRecord.lossCost")
														.setValue(temp);
														nf.setValue(Ext.util.Format.number(temp,
												            '0,000.00'))
											}
										}
	
									}
								}
							}
						},{
							xtype : 'hidden',
							name : 'pawnTicketLossRecord.lossCost'
						}]
					}, {
						columnWidth : .03, 
						layout : "form", 
						labelWidth : 20,
						items : [{
									fieldLabel : "元 ",
									labelSeparator : '',
									anchor : "100%"
								}]
					},{
						columnWidth : .5,
						layout : 'form',
						labelWidth : 85,
						labelAlign : 'right',
						items : [{
								xtype : "combo",
								triggerClass : 'x-form-search-trigger',
								hiddenName : "pawnTicketLossRecord.operatorId",
								editable : false,
								fieldLabel : "经办人",
								blankText : "经办人不能为空，请正确填写!",
								allowBlank : false,
								readOnly : this.isAllReadOnly,
								anchor : "100%",
								onTriggerClick : function(cc) {
									var obj = this;
									var appuerIdObj = obj.nextSibling();
									var userIds = appuerIdObj.getValue();
									var managerName=obj.nextSibling().nextSibling()
									if ("" == obj.getValue()) {
										userIds = "";
									}
									new UserDialog({
										userIds : userIds,
										userName : obj.getValue(),
										single : false,
										title : "选择经办人",
										callback : function(uId, uname) {
											obj.setValue(uId);
											obj.setRawValue(uname);
											managerName.setValue(uname);
											appuerIdObj.setValue(uId);
										}
									}).show();
								}
							}, {
								xtype : "hidden",
								value : ""
							},{
								xtype : 'hidden',
								name : 'pawnTicketLossRecord.operatorName'
							}]
					},{
							columnWidth : 1,
							layout : 'form',
							labelWidth : 85,
							labelAlign : 'right',
							items : [{
								xtype : 'textarea',
								fieldLabel : '备注',
								anchor : '100%',
								readOnly : this.isAllReadOnly,
								name : 'pawnTicketLossRecord.remarks'
							}]
						}]
					}]
				});
		if (typeof(this.lossRecordId) != 'undefined' && this.lossRecordId != null) {
			this.loadData({
						url : __ctxPath
								+ '/creditFlow/pawn/project/getPawnTicketLossRecord.do?lossRecordId='
								+ this.lossRecordId,
						root : 'data',
						preName : 'pawnTicketLossRecord',
						scope : this,
						success : function(resp, options) {
							var result = Ext.decode(resp.responseText);
							this.getCmpByName('lossCost1').setValue(Ext.util.Format.number(result.data.lossCost,'0,000.00'))
							this.getCmpByName('pawnTicketLossRecord.operatorId').setRawValue(result.data.operatorName)
						}
					});
		}
	}
	// 初始化组件
	

});
