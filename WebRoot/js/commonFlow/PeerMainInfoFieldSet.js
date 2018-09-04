/**
 * 对方主体信息-新建项目
 */
var PeerMainInfoFieldSet =new Ext.form.FieldSet({

	columnWidth : .8,
	title : '对方主体信息',
	collapsible : true,
	autoHeight : true,
	bodyStyle : 'padding-left: 10%;',
	items : [{
		xtype : "panel",
		border : false,
		layout : "column",
		bodyStyle : 'padding-left: 0px;text-align:left;',
		html : '<font style="font-weight:bold;">基本信息</font><br/><hr style="width:610px" align="left"/>'
	}, {
		xtype : "textfield",
		fieldLabel : "企业名称",
		name : "name1",
		anchor : "58.5%",
		blankText : "企业名称不能为空，请正确填写!",
		allowBlank : false
	}, {
		xtype : "panel",
		layout : "column",
		fieldLabel : "企业简称",
		isFormField : true,
		items : [{
					columnWidth : 0.24,
					xtype : "textfield",
					name : "name2",
					allowBlank : false,
					blankText : "企业简称不能为空，请正确填写!",
					anchor : "10%"
				}, {
					columnWidth : 0.3,
					layout : "form",
					labelWidth : 80,//注意，这个参数在这里可以调整简单fieldLabel的布局位置
					items : [{
								xtype : "combo",
								name : "Degree",
								fieldLabel : "行业类型",
								store : ["网络IT行业", "餐饮行业"],
								emptyText : "请选择",
								anchor : "100%"
							}]
				}]
	}, {	//上面是第一行
				xtype : "panel",
				layout : "column",
				fieldLabel : "组织机构代码",
				isFormField : true,
				items : [{
							columnWidth : .24,
							xtype : "textfield",
							name : "name2",
							allowBlank : false,
							blankText : "组织机构代码不能为空，请正确填写!",
							anchor : "20%"
						}, {
							columnWidth : .3,
							layout : "form",
							labelWidth : 80,//注意，这个参数在这里可以调整简单fieldLabel的布局位置
							items : [{
										xtype : "textfield",
										name : "Degree",
										allowBlank : false,
										fieldLabel : "营业执照号码",
										blankText : "营业执照号码不能为空，请正确填写!",
										anchor : "100%"
									}]
						}]
			}, {
				xtype : "panel",
				layout : "column",
				fieldLabel : "联系电话",
				isFormField : true,
				items : [{
							columnWidth : .24,
							xtype : "textfield",
							name : "name2",
							allowBlank : false,
							blankText : "联系电话不能为空，请正确填写!",
							anchor : "20%"
						}, {
							columnWidth : .3,
							layout : "form",
							labelWidth : 80,//注意，这个参数在这里可以调整简单fieldLabel的布局位置
							items : [{
										xtype : "textfield",
										name : "Degree",
										allowBlank : false,
										fieldLabel : "邮政编码",
										blankText : "邮政编码不能为空，请正确填写!",
										anchor : "100%"
									}]
						}]
			}, {//上面是第三行
				xtype : "textfield",
				fieldLabel : "通讯地址",
				name : "Email",
				anchor : "58.5%"//控制文本框的长度

			}, {
				xtype : "panel",
				border : false,
				layout : "column",
				html : '<font style="font-weight:bold">法定代表人（负责人）信息</font><br/><hr style="width:610px" align="left"/>'
			}, {
				xtype : "panel",
				layout : "column",
				fieldLabel : "法人姓名",
				isFormField : true,
				items : [{
							columnWidth : .24,
							xtype : "textfield",
							name : "name2",
							allowBlank : false,
							blankText : "法人姓名不能为空，请正确填写!",
							anchor : "20%"
						}, {
							columnWidth : .3,
							layout : "form",
							labelWidth : 80,//注意，这个参数在这里可以调整简单fieldLabel的布局位置
							items : [{
										xtype : "combo",
										name : "Degree",
										fieldLabel : "性别",
										store : ["男", "女"],
										allowBlank : false,
										emptyText : "请选择",
										blankText : "性别不能为空，请正确填写!",
										anchor : "100%"
									}]
						}]
			}, {
				xtype : "panel",
				layout : "column",
				fieldLabel : "证件类型",
				isFormField : true,
				items : [{
							columnWidth : .24,
							xtype : "combo",
							name : "name2",
							store : ["身份证", "军官证"],
							allowBlank : false,
							emptyText : "请选择",
							blankText : "证件类型不能为空，请正确填写!",
							anchor : "20%"
						}, {
							columnWidth : .3,
							layout : "form",
							labelWidth : 80,//注意，这个参数在这里可以调整简单fieldLabel的布局位置
							items : [{
										xtype : "textfield",
										name : "Degree",
										allowBlank : false,
										fieldLabel : "证件号码",
										blankText : "证件号码不能为空，请正确填写!",
										anchor : "100%"
									}]
						}]
			}, {
				xtype : "panel",
				layout : "column",
				fieldLabel : "联系电话",
				isFormField : true,
				items : [{
							columnWidth : .24,
							xtype : "textfield",
							name : "name2",
							anchor : "20%"
						}, {
							columnWidth : .3,
							layout : "form",
							labelWidth : 80,//注意，这个参数在这里可以调整简单fieldLabel的布局位置
							items : [{
										xtype : "textfield",
										name : "Degree",
										fieldLabel : "电子邮箱",
										anchor : "100%"
									}]
						}]
			}, {
				xtype : "panel",
				border : false,
				layout : "column",
				html : '<font style="font-weight:bold">股东(投资人)信息</font><br/><hr style="width:610px" align="left"/>'
		   }]

})