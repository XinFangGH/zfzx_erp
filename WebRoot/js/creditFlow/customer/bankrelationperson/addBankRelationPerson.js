var anchor = "97%" ;
var addBankRelationPersonPanel = new Ext.form.FormPanel({
		id :'addBankRelationPersonPanel',
		url : __ctxPath + '/creditFlow/customer/bankRelationPerson/addBankRelationPersonCustomerBankRelationPerson.do',
		monitorValid : true,
		bodyStyle:'padding:10px',
		renderTo : 'addBankRelationPersonDiv',
		autoScroll : true ,
		labelAlign : 'right',
		width : (screen.width-180)*0.7 - 140,
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
            width : (screen.width-180)* 0.7 - 160 ,
            items : [{
            	columnWidth : .5,
				layout : 'form',
				labelWidth : 80,
				defaults : {anchor : anchor},
				items : [{
					xtype : 'textfield',
					fieldLabel : '客户姓名',
					name : 'bankRelationPerson.name',
					allowBlank : false,
				    blankText : '必填信息'
				    //regex : /^[\u4e00-\u9fa5]{1,10}$/,
					//regexText : '只能输入中文'
				},{
					id : 'addbankname' ,
					xtype : 'textfield',
					fieldLabel : '所在银行',
					allowBlank : false,
					readOnly : true,
				    blankText : '必填信息',
					name : 'bankname'
				},{
					xtype : 'textfield',
					fieldLabel : '电话号码',
					allowBlank : false,
					name : 'bankRelationPerson.blmtelephone'
				},{
					xtype : 'textfield',
					fieldLabel : '手机号码',
					allowBlank : false,
					name : 'bankRelationPerson.blmphone'
				},{
					xtype : 'textfield',
					fieldLabel : '电子邮件',
					name : 'bankRelationPerson.email',
					allowBlank : false,
					regex : /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/,
					regexText : '电子邮件格式不正确或无效的电子邮件地址'
				},{
					xtype : "dickeycombo",
					nodeKey :'8',
					fieldLabel : '婚姻状况',
					hiddenName : 'bankRelationPerson.marriage',
					//mode : 'romote',
					width : 80 ,
					editable : false,
				    //emptyText:'请选择婚姻状况',
					
				    listeners : {
						afterrender : function(combox) {
							var st = combox.getStore();
							st.on("load", function() {
								if(combox.getValue() == 0||combox.getValue()==1){
									combox.setValue("");
								}else{
									combox.setValue(combox
										.getValue());
								}
								combox.clearInvalid();
							})
						}
					}
				},{
					xtype : 'textfield',
					fieldLabel : '孩子1姓名',
					name : 'bankRelationPerson.childname1'
				},{
					xtype : 'textfield',
					fieldLabel : '孩子2姓名',
					name : 'bankRelationPerson.childname2'
				},{
					xtype : 'textfield',
					fieldLabel : '籍贯',
					name : 'bankRelationPerson.nativeplace'
					//regex : /^[\u4e00-\u9fa5]{1,10}$/,
					//regexText : '只能输入中文'
				}]
            },{
            	columnWidth : .5,
				layout : 'form',
				labelWidth : 80,
				defaults : {anchor : anchor},
				items : [{
					xtype : "dickeycombo",
					nodeKey :'sex_key',
					fieldLabel : '性别',
					hiddenName : 'bankRelationPerson.sex',
					width : 80 ,
					//mode : 'romote',
					editable : false,
					allowBlank : false,
				    blankText : '必填信息',
				    emptyText:'请选性别',
				    listeners : {
						afterrender : function(combox) {
							var st = combox.getStore();
							st.on("load", function() {
								if(combox.getValue() == 0||combox.getValue()==1){
									combox.setValue("");
								}else{
									combox.setValue(combox
										.getValue());
								}
								combox.clearInvalid();
							})
						}
					}
				},{
					id : 'addzhibankname',
					xtype : 'trigger',
					triggerClass :'x-form-search-trigger',
					fieldLabel : '银行支行',
					onTriggerClick : function(){
						selectDictionary('bank',getObjArray);
					},
					editable : false,
					anchor: anchor,
					allowBlank : false
				},{
					xtype : 'textfield',
					fieldLabel : '职务',
					name : 'bankRelationPerson.duty',
					allowBlank : false,
					maxLength : 200 ,
				    blankText : '必填信息'
					/*regex : /^[\u4e00-\u9fa5]{1,10}$/,
					regexText : '只能输入中文'*/
				},{
					xtype : 'textfield',
					fieldLabel : '传真号码',
					name : 'bankRelationPerson.fax'
				},{
					xtype : 'datefield',
					fieldLabel : '联系人生日',
					name : 'bankRelationPerson.birthday',
					format : 'Y-m-d'
				},{
					xtype : 'datefield',
					fieldLabel : '婚姻纪念日',
					name : 'bankRelationPerson.marriagedate',
					format : 'Y-m-d'
				},{
					xtype : 'datefield',
					fieldLabel : '孩子1生日',
					name : 'bankRelationPerson.childbirthday1',
					format : 'Y-m-d'
				},{
					xtype : 'datefield',
					fieldLabel : '孩子2生日',
					name : 'bankRelationPerson.childbirthday2',
					format : 'Y-m-d'
				}]
            },{
            	columnWidth : 1,
				layout : 'form',
				labelWidth : 80,
				defaults : {anchor : anchor},
				items :[{
					xtype : 'textfield',
					fieldLabel : '家庭住址',
					name : 'bankRelationPerson.homeaddress'
				},{
					xtype : 'textfield',
					fieldLabel : '户口所在地',
					name : 'bankRelationPerson.hkszd'
				},{
					xtype : 'textfield',
					fieldLabel : '配偶信息',
					name : 'bankRelationPerson.mate'
				},{
					xtype : 'textfield',
					fieldLabel : '父母信息',
					name : 'bankRelationPerson.parents'
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
					name : 'bankRelationPerson.interest1'
				},{
					xtype : 'textarea',
					fieldLabel : '喝酒或抽烟',
					height : 40,
					name : 'bankRelationPerson.interest2'
				},{
					xtype : 'textarea',
					fieldLabel : '就餐口味地点',
					height : 40,
					name : 'bankRelationPerson.interest3'
				},{
					xtype : 'textarea',
					fieldLabel : '聊天话题',
					height : 40,
					name : 'bankRelationPerson.interest4'
				}]
			}]
		},{
			id : 'addbankid' ,
			xtype : 'hidden',
			fieldLabel : '所在银行id',
			name : 'bankRelationPerson.bankid'
		},{
			id : 'addzhibankid' ,
			xtype : 'hidden',
			fieldLabel : '所在银行支行id',
			name : 'bankRelationPerson.fenbankid'
		}]
})
	var getObjArray = function(objArray){
		Ext.getCmp('addbankname').setValue(objArray[(objArray.length)-1].text);
		Ext.getCmp('addbankid').setValue(objArray[(objArray.length)-1].id);
		
		Ext.getCmp('addzhibankname').setValue(objArray[0].text);
		Ext.getCmp('addzhibankid').setValue(objArray[0].id);
		Ext.getCmp('addzhibankname').readOnly = true
	}