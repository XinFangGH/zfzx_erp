var anchor = "98%" ;
var seeBankRelationPersonPanel = new Ext.form.FormPanel({
		id :'seeBankRelationPersonPanel',
		url : __ctxPath + '/creditFlow/customer/bankRelationPerson/seeBankRelationPersonCustomerBankRelationPerson.do',
		bodyStyle:'padding:10px',
		renderTo : 'seeBankRelationPersonDiv',
		autoScroll : true ,
		labelAlign : 'right',
		buttonAlign : 'center',
		layout : 'column',
		autoHeight : true,
		frame : true ,
		items : [{
			layout : 'column',
			xtype:'fieldset',
            title: '基本信息',
            collapsible: true,
            autoHeight:true,
            width : (screen.width-180)* 0.7 - 150 ,
            items : [{
            	columnWidth : .5,
				layout : 'form',
				labelWidth : 80,
				defaults : {anchor : anchor},
				items : [{
					xtype : 'textfield',
					fieldLabel : '客户姓名',
					value : bankRelationPersonData.name,
					readOnly : true
				},{
					xtype : 'textfield',
					fieldLabel : '所在银行',
					readOnly : true,
					value : bankRelationPersonData.bankname
				},{
					xtype : 'textfield',
					fieldLabel : '电话号码',
					value : bankRelationPersonData.blmtelephone,
					readOnly : true
				},{
					xtype : 'textfield',
					fieldLabel : '手机号码',
					value : bankRelationPersonData.blmphone,
					readOnly : true
				},{
					xtype : 'textfield',
					fieldLabel : '婚姻状况',
					value : bankRelationPersonData.marriagename,
					readOnly : true
				},{
					xtype : 'textfield',
					fieldLabel : '电子邮件',
					readOnly : true,
					value : bankRelationPersonData.email
				},{
					xtype : 'textfield',
					fieldLabel : '孩子1姓名',
					value : bankRelationPersonData.childname1,
					readOnly : true
				},{
					xtype : 'textfield',
					fieldLabel : '孩子2姓名',
					value : bankRelationPersonData.childname2,
					readOnly : true
				},{
					xtype : 'textfield',
					fieldLabel : '籍贯',
					//regex : /^[\u4e00-\u9fa5]{1,10}$/,
					//regexText : '只能输入中文',
					value : bankRelationPersonData.nativeplace,
					readOnly : true
				}]
            },{
            	columnWidth : .5,
				layout : 'form',
				labelWidth : 80,
				defaults : {anchor : anchor},
				items : [{
					xtype : 'textfield',
					fieldLabel : '性别',
					value : bankRelationPersonData.sexvalue,
					readOnly : true
				},{
					xtype : 'textfield',
					fieldLabel : '银行支行',
					value : bankRelationPersonData.fenbankvalue,
					readOnly : true
				},{
					xtype : 'textfield',
					fieldLabel : '职务',
					value : bankRelationPersonData.duty,
					readOnly : true
				},{
					xtype : 'textfield',
					fieldLabel : '传真号码',
					value : bankRelationPersonData.fax,
					readOnly : true
				},{
					xtype : 'datefield',
					fieldLabel : '联系人生日',
					format : 'Y-m-d',
					value : bankRelationPersonData.birthday,
					readOnly : true
				},{
					xtype : 'datefield',
					fieldLabel : '婚姻纪念日',
					format : 'Y-m-d',
					value : bankRelationPersonData.marriagedate,
					readOnly : true
				},{
					xtype : 'datefield',
					fieldLabel : '孩子1生日',
					format : 'Y-m-d',
					value : bankRelationPersonData.childbirthday1,
					readOnly : true
				},{
					xtype : 'datefield',
					fieldLabel : '孩子2生日',
					format : 'Y-m-d',
					value : bankRelationPersonData.childbirthday2,
					readOnly : true
				}]
            },{
            	columnWidth : 1,
				layout : 'form',
				labelWidth : 80,
				defaults : {anchor : anchor},
				items :[{
					xtype : 'textfield',
					fieldLabel : '家庭住址',
					value : bankRelationPersonData.homeaddress,
					readOnly : true
				},{
					xtype : 'textfield',
					fieldLabel : '户口所在地',
					value : bankRelationPersonData.hkszd,
					readOnly : true
				},{
					xtype : 'textfield',
					fieldLabel : '配偶信息',
					value : bankRelationPersonData.mate,
					readOnly : true
				},{
					xtype : 'textfield',
					fieldLabel : '父母信息',
					value : bankRelationPersonData.parents,
					readOnly : true
				}]
            }]
		},{
			layout : 'column',
			xtype:'fieldset',
            title: '兴趣爱好',
            collapsible: true,
            autoHeight:true,
           	width : (screen.width-180)* 0.7 - 150 ,
            items : [{
            	columnWidth : 1,
				layout : 'form',
				labelWidth : 80,
				defaults : {anchor : anchor},
				items :[{
					xtype : 'textarea',
					fieldLabel : '嗜好与兴趣',
					height : 40,
					value : bankRelationPersonData.interest1,
					readOnly : true
				},{
					xtype : 'textarea',
					fieldLabel : '喝酒或抽烟',
					height : 40,
					value : bankRelationPersonData.interest2,
					readOnly : true
				},{
					xtype : 'textarea',
					fieldLabel : '就餐口味地点',
					height : 40,
					value : bankRelationPersonData.interest3,
					readOnly : true
				},{
					xtype : 'textarea',
					fieldLabel : '聊天话题',
					height : 40,
					value : bankRelationPersonData.interest4,
					readOnly : true
				}]
			}]
		}],
		buttons : []
	})
