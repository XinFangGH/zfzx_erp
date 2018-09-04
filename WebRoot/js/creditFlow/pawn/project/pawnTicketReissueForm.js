/**
 * @author
 * @createtime
 * @class SlCompanyMainForm
 * @extends Ext.Window
 * @description SlCompanyMain表单
 * @company 北京互融时代软件有限公司
 */
PawnTicketReissueForm = Ext.extend(Ext.Panel, {
	// 构造函数
	constructor : function(_cfg) {
	
		Ext.applyIf(this, _cfg);
		PawnTicketReissueForm.superclass.constructor.call(this, {
			autoHeight : true,
			items :[{
					layout : 'column',
					border : false,
					items : [{
						xtype : 'hidden',
						name : 'pawnTicketReissueRecord.projectId',
						value : this.projectId
					},{
						xtype : 'hidden',
						name : 'pawnTicketReissueRecord.businessType',
						value : this.businessType
					},{
						xtype : 'hidden',
						name : 'pawnTicketReissueRecord.reissueRecordId'
					},{
						columnWidth : .33,
						layout : 'form',
						labelWidth : 85,
						labelAlign : 'right',
						items : [{
							xtype : 'datefield',
							fieldLabel : '补发时间',
							format : 'Y-m-d',
							anchor : '100%',
							allowBlank : false,
							readOnly : this.isAllReadOnly,
							name : 'pawnTicketReissueRecord.reissueTime'
						}]
					},{
						columnWidth : .33,
						layout : 'form',
						labelWidth : 60,
						labelAlign : 'right',
						items : [{
							xtype : 'textfield',
							fieldLabel : '补发人',
							anchor : '100%',
							readOnly : this.isAllReadOnly,
							allowBlank : false,
							name :'pawnTicketReissueRecord.reissuePerson'
						}]
					},{
						columnWidth : .34,
						layout : 'form',
						labelWidth : 60,
						labelAlign : 'right',
						items : [{
								xtype : "combo",
								triggerClass : 'x-form-search-trigger',
								hiddenName : "pawnTicketReissueRecord.operatorId",
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
								name : 'pawnTicketReissueRecord.operatorName'
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
								name : 'pawnTicketReissueRecord.remarks'
							}]
						}]
					}]
				});
		if (typeof(this.reissueRecordId) != 'undefined' && this.reissueRecordId != null) {
			this.loadData({
						url : __ctxPath
								+ '/creditFlow/pawn/project/getPawnTicketReissueRecord.do?reissueRecordId='
								+ this.reissueRecordId,
						root : 'data',
						preName : 'pawnTicketReissueRecord',
						scope : this,
						success : function(resp, options) {
							var result = Ext.decode(resp.responseText);
							this.getCmpByName('pawnTicketReissueRecord.operatorId').setValue(result.data.operatorName)
						}
					});
		}
	}
	// 初始化组件
	

});
