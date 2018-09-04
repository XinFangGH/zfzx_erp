/**
 * 选择发布方式
 * @class PlMmOrderInfoPanel
 * @extends Ext.Panel
 */
SelectPublishPanel = Ext.extend(Ext.Panel, {
	layout : 'anchor',
	anchor : '100%',
	companyHidden : false,
	constructor : function(_cfg) {
		if (typeof(_cfg.spouseHidden) != "undefined") {
		}
		Ext.applyIf(this, _cfg);

		SelectPublishPanel.superclass.constructor.call(this, {
			layout : 'column',
			items : [{
						columnWidth : .3,
						layout : 'form',
						defaults : {
							anchor : '100%'
						},
						labelWidth : 100,
						items : [{
										xtype : 'hidden',
										name : 'slSmallloanProject.selectCreditorType'
								},{
										xtype : 'radio',
										boxLabel : '发布到平台转让标管理',
										anchor : "100%",
										name : 'r1',
										inputValue : "1",
										checked : true,
										listeners : {
											scope : this,
											check : function(obj, checked) {
												if(checked){
													this.getCmpByName("slSmallloanProject.selectCreditorType").setValue();
													obj.ownerCt.ownerCt.ownerCt.ownerCt.getCmpByName("PlCreditorPanel").hide();                
												//	obj.ownerCt.ownerCt.ownerCt.ownerCt.getCmpByName("receiverP2PAccountNumber").show();                
												}
											}
										 }
								  }]
					}, {
						columnWidth : .33,
						layout : 'form',
						defaults : {
							anchor : '90%'
						},
						labelWidth : 100,
						items : [{
										xtype : 'radio',
										boxLabel : '发布到平台理财计划债权库',
										anchor : "100%",
										name : 'r1',
										inputValue : "2",
									//	disabled : this.isAllReadOnly,
										listeners : {
											scope : this,
											check : function(obj, checked) {
												if(checked){
													this.getCmpByName("slSmallloanProject.selectCreditorType").setValue("mmplanOr");
													obj.ownerCt.ownerCt.ownerCt.ownerCt.getCmpByName("PlCreditorPanel").show(); 
													//obj.ownerCt.ownerCt.ownerCt.ownerCt.getCmpByName("receiverP2PAccountNumber").hide();   
												}
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
						items : [{
										xtype : 'radio',
										boxLabel : '发布到线下理财产品债权库',
										anchor : "100%",
										name : 'r1',
										inputValue : "3",
									//	disabled : this.isAllReadOnly,
										listeners : {
											scope : this,
											check : function(obj, checked) {
												if(checked){
													this.getCmpByName("slSmallloanProject.selectCreditorType").setValue("mmproduceOr");
													obj.ownerCt.ownerCt.ownerCt.ownerCt.getCmpByName("PlCreditorPanel").show();
												//	obj.ownerCt.ownerCt.ownerCt.ownerCt.getCmpByName("receiverP2PAccountNumber").hide();   
												}
										 	}
									    }
						  }]
					}]
		})
		
		 
	}

});
