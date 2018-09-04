workCompanyPrivateForm = Ext.extend(Ext.Panel, {
	layout : 'anchor',
	anchor : '100%',
	companyHidden : false,
	constructor : function(_cfg) {
		if (typeof(_cfg.spouseHidden) != "undefined") {
			this.spouseHidden = _cfg.spouseHidden;
		}
		Ext.applyIf(this, _cfg);
		this.initUIComponents();
		workCompanyPrivateForm.superclass.constructor.call(this, {
			layout : 'column',
			items : [{
						columnWidth : .3,
						layout : 'form',
						defaults : {
							anchor : '100%'
						},
						labelWidth : 85,

						items : [{
									xtype : 'hidden',
									name : 'workCompany.id'
								}, {
							xtype : "dickeycombo",
							nodeKey : 'unitproperties',
							hiddenName : "workCompany.companyType",
							itemName : '企业类型', // xx代表分类名称
							fieldLabel : "企业类型",
							//allowBlank : this.companyHidden,
							editable : false,
							readOnly : this.isReadOnly,
							// blankText : "企业类型不能为空，请正确填写!",
							listeners : {
								scope : this,
								afterrender : function(combox) {
									var st = combox.getStore();
									st.on("load", function() {
										if (combox.getValue == ''
												|| combox.getValue() == null) {
											combox
													.setValue(st.getAt(0).data.itemId);
											combox.clearInvalid();
										} else {
											combox.setValue(combox.getValue());
											combox.clearInvalid();
										}
									})
								}
							}
						}, {
							xtype:'combo',
							fieldLabel : '经营场所',
							readOnly : this.isReadOnly,
							name : 'workCompany.companyAddress',
							mode : 'local',
						    displayField : 'name',
						    valueField : 'value',
						    width : 70,
						    store :new Ext.data.SimpleStore({
								fields : ["name", "value"],
								data : [["租赁", "租赁"],["自有资产", "自有资产"]]
							}),
							triggerAction : "all"
						}]
					}, {
						columnWidth : .33,
						layout : 'form',
						defaults : {
							anchor : '90%'
						},
						labelWidth : 115,
						items : [{
								xtype : 'datefield',
								format : 'Y-m-d',
								fieldLabel : '注册时间',
								readOnly : this.isReadOnly,
								name : 'workCompany.onCompanyDate'
							}, {
								xtype : 'numberfield',
								fieldLabel : '员工人数',
								readOnly : this.isReadOnly,
								name : 'workCompany.employeeTotal'
							}]
					}, {
						columnWidth : .34,
						layout : 'form',
						defaults : {
							anchor : '100%'
						},
						labelWidth : 85,
						items : [{
									xtype : 'numberfield',
									fieldLabel : '持股比例',
									name : 'person.grossdebt',
									//allowBlank : this.companyHidden,
									readOnly : this.isReadOnly
								}]
					},{
						columnWidth : .03, // 该列在整行中所占的百分比
						layout : "form", // 从上往下的布局
						labelWidth : 20,
						items : [{
							fieldLabel : "<span style='margin-left:1px'>%</span> ",
							labelSeparator : ''
						}]
					}]
		})

	},
	initUIComponents : function() {
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
