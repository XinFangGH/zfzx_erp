//PersonalInfoPanel
PersonalInfoPanel = Ext.extend(Ext.Panel, {
	layout : "form",
	autoHeight : true,
	labelAlign : 'right',
	isAllReadOnly : false,
	isDiligenceReadOnly : false,
	constructor : function(_cfg) {
		
		if (_cfg == null) {
			_cfg = {};
		}
		Ext.applyIf(this, _cfg);
		var leftlabel = 85;
		var centerlabel = 87;
		var rightlabel = 90;
		PersonalInfoPanel.superclass.constructor.call(this, {
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
							columnWidth : 0.5,
							layout : "form", // 从上往下的布局
							labelWidth : 70,
							labelAlign :'right',
							items:[{
										xtype : 'textfield',
										fieldLabel : '<font color=red>*</font>姓名',
										name : 'csInvestmentperson.investName',
										blankText : '姓名为必填内容',
										anchor : '100%',
										readOnly : this.isReadOnly,
										listeners : {
											'afterrender':function(com){
											    com.clearInvalid();
											}
										}}]
							
					},{
						columnWidth : .5, // 该列在整行中所占的百分比
						layout : "form", // 从上往下的布局
						labelWidth : 70,
						labelAlign :'right',
						items : [{
										xtype : "dickeycombo",
										nodeKey : 'sex_key',
										hiddenName : 'csInvestmentperson.sex',
										fieldLabel : "性别",
										allowBlank : false,
										anchor : '100%',
										editable : true,
										blankText : "性别不能为空，请正确填写!",
										readOnly : this.isReadOnly,
										value : personData == null? null: personData.sex,
										listeners : {
											afterrender : function(combox) {
												var st = combox.getStore();
												st.on("load", function() {
													if (combox.getValue() == 0
															|| combox
																	.getValue() == 1
															|| combox
																	.getValue() == ""
															|| combox
																	.getValue() == null) {
														combox.setValue("");
													} else {
														combox.setValue(combox
																.getValue());
													}
													combox.clearInvalid();
												})
											}
										}
									}, {
							xtype : "hidden",
							name : "csInvestmentperson.investId"
						}]
					},{
						columnWidth : .5, // 该列在整行中所占的百分比
						layout : "form", // 从上往下的布局
						labelWidth : 70,
						labelAlign :'right',
						items : [{
										xtype : "dickeycombo",
										nodeKey : 'card_type_key',
										hiddenName : "csInvestmentperson.cardtype",
										itemName : '证件类型', // xx代表分类名称
										fieldLabel : "证件类型",
										allowBlank : false,
										editable : true,
										readOnly : this.isReadOnly,
										anchor : '100%',
										// emptyText : "请选择",
										blankText : "证件类型不能为空，请正确填写!",
										listeners : {
											scope : this,
											afterrender : function(combox) {
												var st = combox.getStore();
												st.on("load", function() {
													if(combox.getValue()=='' || combox.getValue()==null){												
														combox.setValue(st.getAt(0).data.itemId);
														combox.clearInvalid();
													}else{
														combox.setValue(combox.getValue());
														combox.clearInvalid();
													}
												})
											}
										}
									}]
					},{
							columnWidth : .5, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : 70,
							labelAlign :'right',
							items : [{
										id : 'cardnumber',
										xtype : 'textfield',
										fieldLabel : '证件号码',
										name : 'csInvestmentperson.cardnumber',
										allowBlank : false,
										anchor : '100%',
										blankText : '证件号码为必填内容',
										readOnly : this.isReadOnly
									}]
				
					},{
				            	columnWidth:0.5,
				                layout: 'form',
				                labelWidth : 70,
				                labelAlign :'right',
				                items :[{
										xtype : 'textfield',
										fieldLabel : '电话号码',
										name : 'csInvestmentperson.cellphone',
										allowBlank : false,
										readOnly : this.isReadOnly,
										anchor : '100%',
										regex : /^[1][34578][0-9]{9}$/,
										regexText : '手机号码格式不正确'	,									
										listeners : {
											'afterrender':function(com){
											    com.clearInvalid();
											}
										}
										
									}]
				            },{
							columnWidth : .5, // 该列在整行中所占的百分比
									layout : "form", // 从上往下的布局
									labelWidth : 70,
									labelAlign :'right',
									items : [{
										xtype : 'textfield',
										fieldLabel : '电子邮箱',
										name : 'csInvestmentperson.selfemail',
										readOnly : this.isReadOnly,
										anchor : '100%',
										regex : /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+/,
										regexText : '电子邮箱格式不正确',
										listeners : {
											'afterrender':function(com){
											    com.clearInvalid();
											}
										}	

									}]
					},{
				            	columnWidth:1,
				                layout: 'form',
				                labelWidth : 70,
				                labelAlign :'right',
				                defaults : {anchor:anchor},
				                items :[{
										xtype : 'textfield',
										fieldLabel : '通讯地址',
										readOnly : this.isReadOnly,
										anchor : '100%',
										name : 'csInvestmentPerson.postaddress'
									}]
				            }]
			}]
		});
	}
});

BpCustPersonInfoPanel = Ext.extend(Ext.Panel, {
	layout : "form",
	autoHeight : true,
	labelAlign : 'right',
	isAllReadOnly : false,
	isDiligenceReadOnly : false,
	constructor : function(_cfg) {
		
		if (_cfg == null) {
			_cfg = {};
		}
		Ext.applyIf(this, _cfg);
		var leftlabel = 85;
		var centerlabel = 87;
		var rightlabel = 90;
		BpCustPersonInfoPanel.superclass.constructor.call(this, {
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
							columnWidth : 0.5,
							layout : "form", // 从上往下的布局
							labelWidth : 70,
							labelAlign :'right',
							items:[{
										xtype : 'textfield',
										fieldLabel : '<font color=red>*</font>姓名',
										name : 'bpCustMember.truename',
										blankText : '姓名为必填内容',
										anchor : '100%',
										readOnly : this.isReadOnly,
										listeners : {
											'afterrender':function(com){
											    com.clearInvalid();
											}
										}}]
							
					},{
						columnWidth : .5, // 该列在整行中所占的百分比
						layout : "form", // 从上往下的布局
						labelWidth : 70,
						labelAlign :'right',
						items : [{
										xtype : "dickeycombo",
										nodeKey : 'sex_key',
										hiddenName : 'bpCustMember.sex',
										fieldLabel : "性别",
										anchor : '100%',
										editable : true,
										blankText : "性别不能为空，请正确填写!",
										readOnly : this.isReadOnly,
										value : personData == null? null: personData.sex,
										listeners : {
											afterrender : function(combox) {
												var st = combox.getStore();
												st.on("load", function() {
													if (combox.getValue() == 0
															|| combox
																	.getValue() == 1
															|| combox
																	.getValue() == ""
															|| combox
																	.getValue() == null) {
														combox.setValue("");
													} else {
														combox.setValue(combox
																.getValue());
													}
													combox.clearInvalid();
												})
											}
										}
									}, {
							xtype : "hidden",
							name : "bpCustMember.id"
						}]
					},{
						columnWidth : .5, // 该列在整行中所占的百分比
						layout : "form", // 从上往下的布局
						labelWidth : 70,
						labelAlign :'right',
						items : [{
										xtype : "dickeycombo",
										nodeKey : 'card_type_key',
										hiddenName : "bpCustMember.cardtype",
										itemName : '证件类型', // xx代表分类名称
										fieldLabel : "证件类型",
										allowBlank : false,
										editable : true,
										readOnly : this.isReadOnly,
										anchor : '100%',
										// emptyText : "请选择",
										blankText : "证件类型不能为空，请正确填写!",
										listeners : {
											scope : this,
											afterrender : function(combox) {
												var st = combox.getStore();
												st.on("load", function() {
													if(combox.getValue()=='' || combox.getValue()==null){												
														combox.setValue(st.getAt(0).data.itemId);
														combox.clearInvalid();
													}else{
														combox.setValue(combox.getValue());
														combox.clearInvalid();
													}
												})
											}
										}
									}]
					},{
							columnWidth : .5, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : 70,
							labelAlign :'right',
							items : [{
										xtype : 'textfield',
										fieldLabel : '证件号码',
										name : 'bpCustMember.cardcode',
										allowBlank : false,
										anchor : '100%',
										blankText : '证件号码为必填内容',
										readOnly : this.isReadOnly
									}]
				
					},{
				            	columnWidth:0.5,
				                layout: 'form',
				                labelWidth : 70,
				                labelAlign :'right',
				                items :[{
										xtype : 'textfield',
										fieldLabel : '电话号码',
										name : 'bpCustMember.telphone',
										readOnly : this.isReadOnly,
										anchor : '100%',
										regex : /^[1][34578][0-9]{9}$/,
										regexText : '手机号码格式不正确'	,									
										listeners : {
											'afterrender':function(com){
											    com.clearInvalid();
											}
										}
										
									}]
				            },{
							columnWidth : .5, // 该列在整行中所占的百分比
									layout : "form", // 从上往下的布局
									labelWidth : 70,
									labelAlign :'right',
									items : [{
										xtype : 'textfield',
										fieldLabel : '电子邮箱',
										name : 'bpCustMember.email',
										readOnly : this.isReadOnly,
										anchor : '100%',
										regex : /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+/,
										regexText : '电子邮箱格式不正确',
										listeners : {
											'afterrender':function(com){
											    com.clearInvalid();
											}
										}	

									}]
					},{
				            	columnWidth:1,
				                layout: 'form',
				                labelWidth : 70,
				                labelAlign :'right',
				                defaults : {anchor:anchor},
				                items :[{
										xtype : 'textfield',
										fieldLabel : '通讯地址',
										readOnly : this.isReadOnly,
										anchor : '100%',
										name : 'bpCustMember.postCode'
									}]
				            }]
			}]
		});
	}
});

