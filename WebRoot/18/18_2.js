Ext.onReady(function(){
	var form = new Ext.form.FormPanel({
		title: "表单初始化-用户注册",
		width: 300,
		autoHeight: true,
		frame: true,
		renderTo: "a",
		labelWidth: 65,
		labelAlign: "right",
		defaultType: "textfield",
		
		items:[{
			name: "userName",
			fieldLabel: "用户名",
			width: 200
		},{
			name: "password",
			fieldLabel: "密码",
			inputType: "password",
			width: 200
		},{
			name: "birthday",
			fieldLabel: "出生日期",
			xtype: "datefield",
			format: "Y-m-d",
			width: 150
		},{
			name: "sexGroup",
			fieldLabel: "性别",
			xtype: "radiogroup",
			width: 100,
			items:[{
					name: "sex",
					xtype: "radio",
					boxLabel: "男",
					inputValue: "男"
				},{
					name: "sex",
					xtype: "radio",
					boxLabel: "女",
					inputValue: "女"
				}
			]
		}],
		buttons:[{
			text: "提交"
		},{
			text: "本地读取",
			handler: function(){
				var json = {
					userName: "张海军",
					password: "admin",
					birthday: "1980-08-08",
					sex: "女"
				};
				form.getForm().setValues(json);
			}
		},{
			text: "远程读取"
		}]
	});
})