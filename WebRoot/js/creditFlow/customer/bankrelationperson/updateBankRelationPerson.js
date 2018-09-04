var anchor = "98%" ;
var updateBankRelationPersonPanel = new Ext.form.FormPanel({
		id :'updateBankRelationPersonPanel',
		url : __ctxPath + '/creditFlow/customer/bankRelationPerson/updateCustomerBankRelationPerson.do',
		monitorValid : true,
		bodyStyle:'padding:10px',
		renderTo : 'updateBankRelationPersonDiv',
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
					name : 'bankRelationPerson.name',
					allowBlank : false,
				    blankText : '必填信息',
				    regex : /^[\u4e00-\u9fa5]{1,10}$/,
					regexText : '只能输入中文',
					value : bankRelationPersonData.name
				},{
					id : 'updatebankname' ,
					xtype : 'textfield',
					fieldLabel : '所在银行',
					allowBlank : false,
				    blankText : '必填信息',
					name : 'bankname',
					readOnly : true,
					value : bankRelationPersonData.bankname
				},{
					xtype : 'textfield',
					fieldLabel : '电话号码',
					name : 'bankRelationPerson.blmtelephone',
					value : bankRelationPersonData.blmtelephone
				},{
					xtype : 'textfield',
					fieldLabel : '手机号码',
					name : 'bankRelationPerson.blmphone',
					value : bankRelationPersonData.blmphone
				},{
					xtype : 'textfield',
					fieldLabel : '电子邮件',
					name : 'bankRelationPerson.email',
					regex : /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/,
					regexText : '电子邮件格式不正确或无效的电子邮件地址',
					value : bankRelationPersonData.email
				},{
					xtype : "dickeycombo",
					nodeKey :'8',
					fieldLabel : '婚姻状况',
					hiddenName : 'bankRelationPerson.marriage',
					//mode : 'romote',
					width : 80 ,
					editable : false,
				    //emptyText:'请选择婚姻状况',
					value : bankRelationPersonData.marriage,
					/*valueNotFoundText : bankRelationPersonData.marriagename*/
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
					name : 'bankRelationPerson.childname1',
					value : bankRelationPersonData.childname1
				},{
					xtype : 'textfield',
					fieldLabel : '孩子2姓名',
					name : 'bankRelationPerson.childname2',
					value : bankRelationPersonData.childname2
				},{
					xtype : 'textfield',
					fieldLabel : '籍贯',
					name : 'bankRelationPerson.nativeplace',
					//regex : /^[\u4e00-\u9fa5]{1,10}$/,
					//regexText : '只能输入中文',
					value : bankRelationPersonData.nativeplace
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
					allowBlank : false,
				    blankText : '必填信息',
					editable : false,
				    emptyText:'请选性别',
					value : bankRelationPersonData.sex,
/*					valueNotFoundText : bankRelationPersonData.sexvalue
*/	
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
					id : 'updatezhibankname',
					xtype : 'trigger',
					triggerClass :'x-form-search-trigger',
					fieldLabel : '银行支行',
					onTriggerClick : function(){
						selectDictionary('bank',getObjArray);
					},
					editable : false,
					anchor:anchor,
					allowBlank : false,
					blankText : '必填信息',
					value : bankRelationPersonData.fenbankvalue
				
				},{
					xtype : 'textfield',
					fieldLabel : '职务',
					name : 'bankRelationPerson.duty',
					allowBlank : false,
				    blankText : '必填信息',
					/*regex : /^[\u4e00-\u9fa5]{1,10}$/,
					regexText : '只能输入中文',*/
					value : bankRelationPersonData.duty
				},{
					xtype : 'textfield',
					fieldLabel : '传真号码',
					name : 'bankRelationPerson.fax',
					value : bankRelationPersonData.fax
				},{
					xtype : 'datefield',
					fieldLabel : '联系人生日',
					name : 'bankRelationPerson.birthday',
					format : 'Y-m-d',
					value : bankRelationPersonData.birthday
				},{
					xtype : 'datefield',
					fieldLabel : '婚姻纪念日',
					name : 'bankRelationPerson.marriagedate',
					format : 'Y-m-d',
					value : bankRelationPersonData.marriagedate
				},{
					xtype : 'datefield',
					fieldLabel : '孩子1生日',
					name : 'bankRelationPerson.childbirthday1',
					format : 'Y-m-d',
					value : bankRelationPersonData.childbirthday1
				},{
					xtype : 'datefield',
					fieldLabel : '孩子2生日',
					name : 'bankRelationPerson.childbirthday2',
					format : 'Y-m-d',
					value : bankRelationPersonData.childbirthday2
				}]
            },{
            	columnWidth : 1,
				layout : 'form',
				labelWidth : 80,
				defaults : {anchor : anchor},
				items :[{
					xtype : 'textfield',
					fieldLabel : '家庭住址',
					name : 'bankRelationPerson.homeaddress',
					value : bankRelationPersonData.homeaddress
				},{
					xtype : 'textfield',
					fieldLabel : '户口所在地',
					name : 'bankRelationPerson.hkszd',
					value : bankRelationPersonData.hkszd
				},{
					xtype : 'textfield',
					fieldLabel : '配偶信息',
					name : 'bankRelationPerson.mate',
					value : bankRelationPersonData.mate
				},{
					xtype : 'textfield',
					fieldLabel : '父母信息',
					name : 'bankRelationPerson.parents',
					value : bankRelationPersonData.parents
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
					name : 'bankRelationPerson.interest1',
					value : bankRelationPersonData.interest1
				},{
					xtype : 'textarea',
					fieldLabel : '喝酒或抽烟',
					height : 40,
					name : 'bankRelationPerson.interest2',
					value : bankRelationPersonData.interest2
				},{
					xtype : 'textarea',
					fieldLabel : '就餐口味地点',
					height : 40,
					name : 'bankRelationPerson.interest3',
					value : bankRelationPersonData.interest3
				},{
					xtype : 'textarea',
					fieldLabel : '聊天话题',
					height : 40,
					name : 'bankRelationPerson.interest4',
					value : bankRelationPersonData.interest4
				}]
			}]
		},{
			id : 'updatebankid' ,
			xtype : 'hidden',
			name : 'bankRelationPerson.bankid',
			value : bankRelationPersonData.bankid
		},{
			
			id : 'updatezhibankid' ,
			xtype : 'hidden',
			name : 'bankRelationPerson.fenbankid',
			value : bankRelationPersonData.fenbankid
		},{
			xtype : 'hidden',
			name : 'bankRelationPerson.id',
			value : bankRelationPersonData.id
		}]
		/*buttons : [{
			id : 'submit',
			text : '提交',
			iconCls : 'submitIcon',
			formBind : true,
			handler : function() {
				updateBankRelationPersonPanel.getForm().submit({
					method : 'POST',
					waitTitle : '连接',
					waitMsg : '消息发送中...',
					success : function(form ,action) {
						Ext.Msg.alert('状态', '修改成功!',
							function(btn, text) {
								if(typeof(mark5) != "undefined"){
									jStoreBankRelationPerson.reload();
									Ext.getCmp('updateBankRelationPersonWin').destroy();
								}if(typeof(mark6) != "undefined"){
									jStoreBankRelationPersonRefer.reload();
									Ext.getCmp('updateBankRelationPersonWinRefer').destroy();
								}
						});
					},
					
					failure : function(form, action) {
						if(action.response.status==0){
							Ext.ux.Toast.msg('状态','连接失败，请保证服务已开启');
						}else if(action.response.status==-1){
							Ext.ux.Toast.msg('状态','连接超时，请重试!');
						}else{
							Ext.ux.Toast.msg('状态','添加失败!');		
						}
					}
				});
			}
		},{
			text : '取消',
			iconCls : 'cancelIcon',
			handler : function(){
				if(typeof(mark5) != "undefined"){
					Ext.getCmp('updateBankRelationPersonWin').destroy();
				}if(typeof(mark6) != "undefined"){
					Ext.getCmp('updateBankRelationPersonWinRefer').destroy();
				}
			}
		}]*/
	})
	var getObjArray = function(objArray){
		Ext.getCmp('updatebankname').setValue(objArray[(objArray.length)-1].text);
		Ext.getCmp('updatebankid').setValue(objArray[(objArray.length)-1].id);
		
		Ext.getCmp('updatezhibankname').setValue(objArray[0].text);
		Ext.getCmp('updatezhibankid').setValue(objArray[0].id);
		Ext.getCmp('updatezhibankname').readOnly = true
	}
	