Ext.onReady(function(){
	var form = new Ext.form.FormPanel({
		title: "灵活布局的表单",
		width: 650,
		autoHeight: true,
		frame: true,
		renderTo: "a",
		layout: "form",
		labelWidth: 65,
		labelAlign: "right",
		
		items:[{
			layout: "column",	//从左往右的布局
			items:[{
				columnWidth: .3, //该列有整行中所占百分比
				layout: "form",	//从上往下的布局
				items: [{
					name: "firstName",
					xtype: "textfield",
					fieldLabel: "姓",
					width: 120
				}]
			},{
				columnWidth: .3,
				layout: "form",
				items: [{
					name: "lastName",
					xtype: "textfield",
					fieldLabel: "名",
					width: 120
				}]
			},{
				columnWidth: .3,
				layout: "form",
				items: [{
					name: "enName",
					xtype: "textfield",
					fieldLabel: "英文名",
					width: 120
				}]
			}]
		},{
			layout: "column",
			items:[{
				columnWidth: .5,
				layout: "form",
				items:[{
					name: "motto1",
					xtype: "textfield",
					fieldLabel: "座右铭1",
					width: 220
				}]
			},{
				columnWidth: .5,
				layout: "form",
				items:[{
					name: "motto2",
					xtype: "textfield",
					fieldLabel: "座右铭2",
					width: 220
				}]
			}]
		},{
			layout: "form",
			items:[{
				name: "award",
				xtype: "textfield",
				fieldLabel: "奖励",
				width: 500
			},{
				name: "punish",
				xtype: "textfield",
				fieldLabel: "处罚",
				width: 500
			}]
		},{
			layout: "column",
			items:[{
				layout: "form",
				columnWidth: 0.2,
				items:[{
					name: "film",
					xtype: "textfield",
					fieldLabel: "电影最爱",
					width: 50
				}]
			},{
				layout: "form",
				columnWidth: 0.2,
				items:[{
					name: "music",
					xtype: "textfield",
					fieldLabel: "音乐最爱",
					width: 50
				}]			
			},{
				layout: "form",
				columnWidth: 0.2,
				items:[{
					name: "starer",
					xtype: "textfield",
					fieldLabel: "明星最爱",
					width: 50
				}]
			},{
				layout: "form",
				columnWidth: 0.2,
				items:[{
					name: "sports",
					xtype: "textfield",
					fieldLabel: "运动最爱",
					width: 50
				}]
			}]
		},{
			layout: "form",
			items:[{
				name: "articl",
				xtype: "htmleditor",
				fieldLabel: "获奖文章",
				enableLists: false,
				enableSourceEdit: false,
				height: 150
			}]
		}],
		buttonAlign: "center",
		buttons:[{
			text: "提交"
		},{
			text: "重置"
		},{
			text: "读取数据",
			handler: function(){
				var json = {
					firstName: "李",
					lastName: "赞红",
					enName: "LiZanhong",
					motto1: "走别人的路，让别人无路可走",
					motto2: "将天踩在脚下，将你放在心里",
					award: "小学的时候捡到一分钱，受到老师大力表扬",
					punish: "欺负女同学，在烈日下被罚站一小时",
					film: "我是一棵小草",
					music: "女人如烟",
					starer: "李开复",
					sports: "羽毛球",
					articl: "<b><i>暂时没有，获奖了再补充</i></b>"
				};
				form.getForm().setValues(json);
			}
		}]
	});
})