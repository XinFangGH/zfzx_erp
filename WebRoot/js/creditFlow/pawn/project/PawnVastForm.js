/**
 * @author
 * @createtime
 * @class SlCompanyMainForm
 * @extends Ext.Window
 * @description SlCompanyMain表单
 * @company 北京互融时代软件有限公司
 */
PawnVastForm = Ext.extend(Ext.Panel, {
	// 构造函数
	constructor : function(_cfg) {
	
		Ext.applyIf(this, _cfg);
		PawnVastForm.superclass.constructor.call(this, {
			layout : 'form',
			autoHeight : true,
			items :[{
					layout : 'column',
					border : false,
					items : [{
						xtype : 'hidden',
						name : 'pawnVastMaragement.projectId',
						value : this.projectId
					},{
						xtype : 'hidden',
						name : 'pawnVastMaragement.businessType',
						value : this.businessType
					},{
						xtype : 'hidden',
						name : 'pawnVastMaragement.vastId'
					},{
						xtype : 'hidden',
						name : 'plPawnProject.payintentPeriod'
					},{
						columnWidth : .5,
						layout : 'form',
						labelWidth : 85,
						labelAlign : 'right',
						items : [{
							xtype : 'textfield',
							fieldLabel : '当票号',
							anchor : '100%',
							readOnly : true,
							name :'phnumber'
						},{
							xtype : 'textfield',
							fieldLabel : '证件类型',
							readOnly : true,
							anchor : '100%',
							name : 'cardType'
						},{
							xtype : 'textfield',
							fieldLabel : '绝当凭证号',
							anchor : '100%',
							allowBlank : false,
							readOnly : this.isAllReadOnly,
							name : 'pawnVastMaragement.vastNumber'
						},{
							columnWidth : .5,
							layout : 'form',
							labelWidth : 85,
							labelAlign : 'right',
							items : [{
								xtype : "combo",
								triggerClass : 'x-form-search-trigger',
								hiddenName : "pawnVastMaragement.managerId",
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
								name : 'pawnVastMaragement.managerName'
							}]
						}]
					},{
						columnWidth : .5,
						layout : 'form',
						labelWidth : 85,
						labelAlign : 'right',
						items : [{
							xtype : 'textfield',
							fieldLabel : '客户名称',
							anchor : '100%',
							readOnly : true,
							name :'customerName'
						},{
							xtype : 'textfield',
							fieldLabel : '证件号码',
							readOnly : true,
							anchor : '100%',
							name : 'cardNumber'
						},{
							xtype : "dickeycombo",
							hiddenName : "pawnVastMaragement.vastWay",
							nodeKey : 'vastWay', // xx代表分类名称
							fieldLabel : "绝当方式",
							allowBlank : false,
							editable : false,
							emptyText : "请选择",
							readOnly : this.isAllReadOnly,
							blankText : "绝当类型不能为空，请正确填写!",
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
						},{
							xtype : 'datefield',
							fieldLabel : '受理日期',
							format : 'Y-m-d',
							anchor : '100%',
							allowBlank : false,
							readOnly : this.isAllReadOnly,
							name : 'pawnVastMaragement.vastDate'
						}]
					},{
							columnWidth : 1,
							layout : 'form',
							labelWidth : 85,
							labelAlign : 'right',
							items : [{
								xtype : 'textarea',
								fieldLabel : '绝当备注',
								anchor : '100%',
								readOnly : this.isAllReadOnly,
								name : 'pawnVastMaragement.remarks'
							}]
						}]
					}]
				});
	}
	// 初始化组件
	

});
