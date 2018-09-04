/*function ressetSuppliorName(obj){
       var suppliorName=obj.getCmpByName('flObjectSupplior.Name');
       if(projectNameObject.getValue().trim()!=""){
           projectNameObject.setValue("");
           projectNameObject.clearInvalid(); 
       }
}*/

LeaseFinanceSuppliorInfo = Ext.extend(Ext.Panel, {
	layout : "form",
	autoHeight : true,
	labelAlign : 'right',
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		LeaseFinanceSuppliorInfo.superclass.constructor.call(this, {
				//autoScroll : true,
				labelAlign : 'right',
				buttonAlign : 'center',
				border : false ,
				//frame : true,
					layout : 'column',
					//title : '供货方信息',
					//collapsible : true,
					autoHeight : true,
					anchor : '100%',
					items : [{
						layout : "column",
						defaults : {
							anchor : '100%',
							//columnWidth : 1,
							isFormField : true,
							labelWidth : 75
						},
						items : [{
									name : 'flObjectSupplior.id',
									xtype : 'hidden',
									value : this.id == null ? '' : this.id
								}, {
									columnWidth : 1, // 该列在整行中所占的百分比
									layout : "form", // 从上往下的布局
									labelWidth : 70,
									items : [{
										xtype : 'combo',
										triggerClass : 'x-form-search-trigger',
										fieldLabel : "供货方名称",
										name : "flObjectSupplior.Name", //大写，工具生成有误，暂定大写   by gao
										readOnly : false,
										allowBlank : false,
										blankText : "供货方名称不能为空，请正确填写!",
										anchor : "100%",
										scope : this,
										onTriggerClick : function() {
											var op = this.ownerCt.ownerCt.ownerCt.ownerCt;
											var Supplior = function(obj) {
													op.getCmpByName('flObjectSupplior.id').setValue("");
												    op.getCmpByName('flObjectSupplior.Name').setValue("");
												    op.getCmpByName('flObjectSupplior.legalPersonName').setValue("");
													op.getCmpByName('flObjectSupplior.companyPhoneNum').setValue("");
													op.getCmpByName('flObjectSupplior.connectorName').setValue("");
													op.getCmpByName('flObjectSupplior.connectorPosition').setValue("");
													op.getCmpByName('flObjectSupplior.connectorPhoneNum').setValue("");
													op.getCmpByName('flObjectSupplior.companyFax').setValue("");
													op.getCmpByName('flObjectSupplior.companyAddress').setValue("");
													op.getCmpByName('flObjectSupplior.companyComment').setValue("");

													if(obj.id!=0 && obj.id!="")	
													op.getCmpByName('flObjectSupplior.id').setValue(obj.id);
													if(obj.Name!=0 && obj.Name!="")	
													op.getCmpByName('flObjectSupplior.Name').setValue(obj.Name);
													if(obj.legalPersonName!=0 && obj.legalPersonName!="")	
													op.getCmpByName('flObjectSupplior.legalPersonName').setValue(obj.legalPersonName);
													if(obj.companyPhoneNum!=0 && obj.companyPhoneNum!="")	
													op.getCmpByName('flObjectSupplior.companyPhoneNum').setValue(obj.companyPhoneNum);
													if(obj.connectorName!=0 && obj.connectorName!="")	
													op.getCmpByName('flObjectSupplior.connectorName').setValue(obj.connectorName);
													if(obj.connectorPosition!=0 && obj.connectorPosition!="")	
													op.getCmpByName('flObjectSupplior.connectorPosition').setValue(obj.connectorPosition);
													if(obj.connectorPhoneNum!=0 && obj.connectorPhoneNum!="")	
													op.getCmpByName('flObjectSupplior.connectorPhoneNum').setValue(obj.connectorPhoneNum);
													if(obj.companyFax!=0 && obj.companyFax!="")	
													op.getCmpByName('flObjectSupplior.companyFax').setValue(obj.companyFax);
													if(obj.companyAddress!=0 && obj.companyAddress!="")	
													op.getCmpByName('flObjectSupplior.companyAddress').setValue(obj.companyAddress);
													if(obj.companyComment!=0 && obj.companyComment!="")	
													op.getCmpByName('flObjectSupplior.companyComment').setValue(obj.companyComment);
											}
											selectSupplior(Supplior);
										},
										resizable : true,
										mode : 'romote',
										//editable : false,
										lazyInit : false,
										typeAhead : true,
										minChars : 1,
										/*store : new Ext.data.JsonStore({}),
										displayField : 'name',
										valueField : 'id',*/
										triggerAction : 'all',
										listeners : {
											scope : this,
											'select' : function(combo, record,
													index) {
												selectCusCombo(record);
											},
											'blur' : function(f) {
											},
											'change' : function(combox, record,
													index) {
												var obj_n = combox.ownerCt.ownerCt.ownerCt.ownerCt.ownerCt;
												ressetProjuect(obj_n);
											}
										}

									}]
								}, {
									columnWidth : .5, // 该列在整行中所占的百分比
									layout : "form", // 从上往下的布局
									labelWidth : 130,
									items : [{
										fieldLabel : "法人代表(负责人)姓名",
										xtype : "textfield",
										name : 'flObjectSupplior.legalPersonName',
										//readOnly : this.isAllReadOnly,
//										editable : false,
										anchor : "100%"
									}]
								}, {
									columnWidth : .5, // 该列在整行中所占的百分比
									layout : "form", // 从上往下的布局
									labelWidth : 70,
									items : [{
										fieldLabel : "公司电话",
										xtype : "textfield",
										name : "flObjectSupplior.companyPhoneNum",
										//readOnly : this.isAllReadOnly,
//										editable : false,
										anchor : "100%"
									}
								]
								}, {
									columnWidth : .5, // 该列在整行中所占的百分比
									layout : "form", // 从上往下的布局
									labelWidth : 70,
									items : [ {
										fieldLabel : "联系人姓名",
										xtype : "textfield",
										name : 'flObjectSupplior.connectorName',
//										editable : false,
										anchor : "100%"
									}, {
										fieldLabel : "联系人职位",
										xtype : "textfield",
										name : 'flObjectSupplior.connectorPosition',
										editable : false,
										anchor : "100%"
									}]
								}, {
									columnWidth : .5, // 该列在整行中所占的百分比
									layout : "form", // 从上往下的布局
									labelWidth : 70,
									items : [ {
										fieldLabel : "联系人电话",
										xtype : "numberfield",
										name : 'flObjectSupplior.connectorPhoneNum',
										editable : true,
										anchor : "100%"
									}, {
										xtype : "numberfield",
										fieldLabel : '公司传真',
										name : 'flObjectSupplior.companyFax',
										maxLength : 255,
										anchor : "100%"
									}]
								}, {
									columnWidth : 1, // 该列在整行中所占的百分比
									layout : "form", // 从上往下的布局
									labelWidth : 70,
									items : [{
										xtype : 'textfield',
										fieldLabel : '供货方地址',
										name : 'flObjectSupplior.companyAddress',
										maxLength : 255,
										anchor : "100%"
									}]
								}, {
									columnWidth : 1,
									layout : 'form',
									labelWidth : 70,
									defaults : {
										anchor : '100%'
									},
									items : [{
										xtype : 'textarea',
										fieldLabel : '备注',
										maxLength : 100,
										maxLengthText : '最大输入长度100',
										name : 'flObjectSupplior.companyComment'
									}]
								}]
					}]
		});
	}
})