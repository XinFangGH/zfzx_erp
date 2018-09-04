/**
 * 投资流程信息
 * @class plMmOrderInfoPanel
 * @extends Ext.Panel
 */
PlMmOrderInfoPanel = Ext.extend(Ext.Panel, {
	layout : 'anchor',
	anchor : '100%',
	companyHidden : false,
	constructor : function(_cfg) {
		if (typeof(_cfg.spouseHidden) != "undefined") {
		}
		Ext.applyIf(this, _cfg);
		PlMmOrderInfoPanel.superclass.constructor.call(this, {
			layout : 'column',
			items : [{
						columnWidth : .3,
						layout : 'form',
						defaults : {
							anchor : '100%'
						},
						labelWidth : 100,
						items : [/*{
									xtype : 'textfield',
									fieldLabel : '理财产品名称',
									name : 'plMmOrderInfo.mmplanName',
									readOnly : true
								}, {
									xtype : 'textfield',
									fieldLabel : '产品编号',
									name : 'plMmOrderInfo.mmplanNumber',
									readOnly : true
								},*/{
									xtype : 'hidden',
									name : 'plMmOrderInfo.orderId'
								}, {
									xtype : 'textfield',
									fieldLabel : '订单编号',
									name : 'plMmOrderInfo.recordNumber',
									readOnly : true
								},{
									name : 'plMmOrderInfo.departUser',
									xtype : 'combo',
									fieldLabel : '门店经理',
									submitValue : true,
									triggerClass : 'x-form-search-trigger',
									readOnly : this.isReadOnly,
									editable : false,
									scope : this,
									onTriggerClick : function() {
										var obj = this;
										var nextObj = obj.nextSibling();
										var userIds = nextObj.getValue();
										if ("" == obj.getValue()) {
											userIds = "";
										}
										new UserDialog({
											userIds : userIds,
											userName : obj.getValue(),
											single : false,
											title : "选择门店经理",
											callback : function(uId, uname) {
												obj.setValue(uname);
												nextObj.setValue(uId);
											}
										}).show();
									}
								},{
									xtype : "hidden",
									name : 'plMmOrderInfo.departUserId'
								},{
				                  	xtype : 'hidden',
				                  	name : 'plMmOrderInfo.id'
				                },{
				                  	xtype : 'hidden',
				                  	name : 'plMmOrderInfo.mmplanId'
				                }
				                ]
					}, {
						columnWidth : .33,
						layout : 'form',
						defaults : {
							anchor : '90%'
						},
						labelWidth : 100,
						items : [/*{
									xtype : 'textfield',
									fieldLabel : '合同编号',
									name : 'plMmOrderInfo.orderNumber',
									readOnly : this.isReadOnly
								},*/{
									name : 'plMmOrderInfo.customerManagerName',
									xtype : 'combo',
									fieldLabel : '客户经理',
									submitValue : true,
									triggerClass : 'x-form-search-trigger',
									readOnly : this.isReadOnly,
									editable : false,
									value : 'null' == currentUserFullName ? '' : currentUserFullName,
									scope : this,
									allowBlank : false,
									onTriggerClick : function() {
										var obj = this;
										var nextObj = obj.nextSibling();
										var userIds = nextObj.getValue();
										if ("" == obj.getValue()) {
											userIds = "";
										}
										new UserDialog({
											userIds : userIds,
											userName : obj.getValue(),
											single : false,
											title : "选择客户经理",
											callback : function(uId, uname) {
												obj.setValue(uname);
												nextObj.setValue(uId);
											}
										}).show();
									}
								},{
									xtype : "hidden",
									name : 'plMmOrderInfo.customerManagerNameId',
									value : "null" == currentUserId ? '' : currentUserId
								},{
									xtype : "dickeycombo",
									nodeKey : 'plMmOrderInfo_orderpostType',
									hiddenName : "plMmOrderInfo.orderpostType",
									itemName : '账单寄送形式', // xx代表分类名称
									fieldLabel : "账单寄送形式",
									editable : false,
									readOnly : this.isReadOnly,
									listeners : {
										scope : this,
										afterrender : function(combox) {
											var st = combox.getStore();
											st.on("load", function() {
												if (combox.getValue() == 0
														|| combox.getValue() == 1
														|| combox.getValue() == ""
														|| combox.getValue() == null) {
													combox.setValue("");
												} else {
													combox.setValue(combox
															.getValue());
												}
												combox.clearInvalid();
											})
										}
									}
									
								}]
					}, {
						columnWidth : .34,
						layout : 'form',
						defaults : {
							anchor : '100%'
						},
						labelWidth : 100,
						items : [ /*{
									xtype : 'textfield',
									fieldLabel : '合同金额',
									name : 'plMmOrderInfo.buyMoney',
									readOnly : this.isReadOnly
								}, */ {
									name : 'plMmOrderInfo.teamManageName',
									xtype : 'combo',
									fieldLabel : '团队经理',
									submitValue : true,
									triggerClass : 'x-form-search-trigger',
									readOnly : this.isReadOnly,
									editable : false,
									scope : this,
									onTriggerClick : function() {
										var obj = this;
										var nextObj = obj.nextSibling();
										var userIds = nextObj.getValue();
										if ("" == obj.getValue()) {
											userIds = "";
										}
										new UserDialog({
											userIds : userIds,
											userName : obj.getValue(),
											single : false,
											title : "选择团队经理",
											callback : function(uId, uname) {
												obj.setValue(uname);
												nextObj.setValue(uId);
											}
										}).show();
									}
								},{
									xtype : "hidden",
									name : 'plMmOrderInfo.teamManageNameId'
								}]
					},{
						columnWidth : .34,
						layout : 'form',
						defaults : {
							anchor : '100%'
						},
						labelWidth : 100,
						items : [ {
									xtype : 'textfield',
									fieldLabel : '投资说明',
									name : 'plMmOrderInfo.investDescription',
									readOnly : this.isReadOnly
								}]
					},{
						columnWidth : 1, // 该列在整行中所占的百分比
						layout : "form", // 从上往下的布局
						labelWidth : 100,
						items : [{
							fieldLabel : '门店名称',
							anchor : '97%',
							xtype : 'combo',
							triggerClass : 'x-form-search-trigger',
							editable : false,
							readOnly : this.isReadOnly,
							value:"null"==shopName?'':shopName,
							hiddenName : "plMmOrderInfo.departMentName",
							allowBlank : false,
							onTriggerClick : function() {
								var op = this.ownerCt.ownerCt.ownerCt.ownerCt;
								var EnterpriseNameStockUpdateNew = function(obj) {
									if (null != obj.orgName && "" != obj.orgName)
										op.getCmpByName('plMmOrderInfo.departMentName').setValue(obj.orgName);
									if (null != obj.orgId && "" != obj.orgId)
										op.getCmpByName('plMmOrderInfo.departId').setValue(obj.orgId);
								}
								selectShop(EnterpriseNameStockUpdateNew);
							}
					},{
						xtype:'hidden',
						name:'plMmOrderInfo.departId',
						value:"null"==shopId?'':shopId
					}]
					}]
		})
		
		  if (this.projectId != null && this.projectId != 'undefined') {
		  	  var panel = this;
			  this.loadData( {
				  url : __ctxPath + '/creditFlow/financingAgency/getPlMmOrderInfo.do?id=' + this.projectId,
				  root : 'data', 
				  preName : 'plMmOrderInfo' ,
				  success : function (response,obj){
				  		var	result = Ext.util.JSON.decode(response.responseText);
				  		/*
				  		panel.getCmpByName('plMmOrderInfo.customerManagerName').setValue(result.data.customerManagerName);
				  		panel.getCmpByName('plMmOrderInfo.teamManageName').setValue(result.data.teamManageName);
				  		panel.getCmpByName('plMmOrderInfo.departUser').setValue(result.data.departUser);*/
				  }
			  }); 
		  }
		 
	}

});
