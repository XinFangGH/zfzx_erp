Ext.onReady(function(){
	var form = new Ext.form.FormPanel({
		title: "表单初始化-用户注册-Radio扩展版",
		width: 300,
		autoHeight: true,
		frame: true,
		renderTo: "a",
		labelWidth: 65,
		labelAlign: "right",
		defaultType: "textfield",
		
		items:[{
			id: "a.userName",
			fieldLabel: "用户名",
			width: 200
		},{
			id: "a.password",
			fieldLabel: "密码",
			inputType: "password",
			width: 200
		},{
			id: "a.birthday",
			fieldLabel: "出生日期",
			xtype: "datefield",
			format: "Y-m-d",
			width: 150
		},{
			name: "a.sexGroup",
			fieldLabel: "性别",
			xtype: "ux-radiogroup",
			horizontal:true, //水平放置
			radios:[{
					name: "sex",
					boxLabel: "男",
					value: "男"
				},{
					name: "sex",
					boxLabel: "女",
					value: "女"
				}
			]
		}],
		buttons:[{
			text: "提交"
		},{
				text: "本地读取",
				handler: function(){
					var json = {
						"a.userName": "张海军xxxx",
						"a.password": "adminxxxx",
						"a.birthday": "1980-08-08",
						sexGroup: "女"
					};
					form.getForm().setValues(json);
					alert(form.getForm().findField("sexGroup").getValue());
				}
			},{
				text: "远程读取",
				handler: function(){
					form.getForm().reader = reader;
					form.load({url: "../InitValuesServlet"});
				}
		}]
	});
	
	var reader = new Ext.data.JsonReader({},[
		{name: "userName", type: "string", mappging: "userName"},
		{name: "password", type: "string", mappging: "password"},
		{name: "birthday", type: "string", mappging: "birthday"},
		{name: "sexGroup", type: "string", mappging: "sexGroup"}
	]);
})