var jStoreBankRelationPersonRefer;
var bankRelationPersonData;
var mark6 = true;    //
var anchor = '100%';
var selectBankRelationPerson = function(funName ,bankId){
var bankIdVal =  bankId
Ext.onReady(function() {
	Ext.BLANK_IMAGE_URL = __ctxPath+ '/ext3/resources/images/default/s.gif';
	Ext.form.Field.prototype.msgTarget = 'qtip';
	Ext.QuickTips.init();
	jStoreBankRelationPersonRefer = new Ext.data.JsonStore( {
		url : __ctxPath + '/creditFlow/customer/bankRelationPerson/queryBankRelationPersonCustomerBankRelationPerson.do?isAll='+isGranted('_detail_syyhkh'),
		totalProperty : 'totalProperty',
		root : 'topics',
		baseParams : {bankId : (bankId == null) ? 0 : bankId},
		fields : [ {name : 'id'}, {name : 'name'}, {name : 'number'}, {name : 'duty'}, {name : 'blmtelephone'}, {name : 'marriage'}, {name : 'marriagename'}, {name : 'birthday'}, {name : 'bankname'}, {name : 'email'}, {name : 'address'}, {name : 'blmphone'},{name : 'bankaddress'},
		           {name:'fenbankvalue'},{name :'bankname'},{name :'sexvalue'}]
	});
	jStoreBankRelationPersonRefer.load({
		params : {
			start : 0,
			limit : 15
		}
	});
	var button_add = new Ext.Button({
		text : '增加',
		tooltip : '增加一条银行联系人信息',
		iconCls : 'btn-add',
		scope : this,
		handler : function() {
			Ext.Ajax.request({
				url : __ctxPath + '/creditFlow/customer/bankRelationPerson/findByBankCustomerBankRelationPerson.do',
				method : 'POST',
				success : function(response,request) {
					var obj = Ext.util.JSON.decode(response.responseText);
					var addBankRelationPersonPanel = new Ext.form.FormPanel({
						id :'addBankRelationPersonPanela',
						url : __ctxPath + '/creditFlow/customer/bankRelationPerson/addBankRelationPersonCustomerBankRelationPerson.do',
						//monitorValid : true,
						bodyStyle:'padding:10px',
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
									name : 'bankRelationPerson.name'
									//allowBlank : false,
								    //blankText : '必填信息'
								    //regex : /^[\u4e00-\u9fa5]{1,10}$/,
									//regexText : '只能输入中文'
								},{
									id : 'addbanknamerefer' ,
									xtype : 'textfield',
									fieldLabel : '所在银行',
									readOnly : true,
									name : 'bankname',
									value : obj.topics[0].text//update by chencc topics[1]->topics[0]
								},{
									xtype : 'textfield',
									fieldLabel : '电话号码',
									name : 'bankRelationPerson.blmtelephone'
								},{
									xtype : 'textfield',
									fieldLabel : '手机号码',
									name : 'bankRelationPerson.blmphone'
								},{
									xtype : 'textfield',
									fieldLabel : '电子邮件',
									name : 'bankRelationPerson.email',
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
								    emptyText:'请选择婚姻状况',
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
									//allowBlank : false,
								    //blankText : '必填信息',
								    //emptyText:'请选性别',
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
									id : 'addzhibanknamerefer',
									xtype : 'trigger',
									triggerClass :'x-form-search-trigger',
									fieldLabel : '银行支行',
									onTriggerClick : function(){
										selectDictionary('bank',getObjArrayRefer);
									},
									editable : false,
									anchor:'100%',
									allowBlank : false,
									value : obj.topics[0].text
									/*id : 'addzhibankname' ,
									xtype : 'textfield',
									fieldLabel : '银行支行',
									readOnly : true,
									name : 'bankname',
									value : obj.topics[0].text*/
								},{
									xtype : 'textfield',
									fieldLabel : '职务',
									name : 'bankRelationPerson.duty',
									//allowBlank : false,
									maxLength : 200
								    //blankText : '必填信息'
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
							id : 'addbankidrefer' ,
							xtype : 'hidden',
							fieldLabel : '所在银行id',
							name : 'bankRelationPerson.bankid',
							value : obj.topics[0].id//update by chencc topics[1]->topics[0]
						},{
							id : 'addzhibankidrefer' ,
							xtype : 'hidden',
							fieldLabel : '所在银行支行id',
							name : 'bankRelationPerson.fenbankid',
							value : obj.topics[0].id
						}]
					})
					var addBankRelationPersonWinRefer = new Ext.Window({
						id : 'addBankRelationPersonWinRefer',
						title : '新增银行联系人',
						layout : 'fit',
						width : (screen.width-180)*0.7 - 100,
						height : 410,
						closable : true,
						constrainHeader : true ,
						collapsible : true,
						resizable : true,
						plain : true,
						border : false,
						autoScroll : true ,
						modal : true,
						bodyStyle:'overflowX:hidden',
						buttonAlign : 'right',
						tbar :[new Ext.Button({text : '保存',tooltip : '保存基本信息',iconCls : 'submitIcon',hideMode:'offsets',
								handler :function(){
									formSave('addBankRelationPersonPanela', addBankRelationPersonWinRefer ,jStoreBankRelationPersonRefer);
								}
						})],
						items:[addBankRelationPersonPanel]
					}).show();
				},
				failure : function(response) {					
						Ext.ux.Toast.msg('状态','操作失败，请重试');		
				},
				params: { bankId: bankId }
			});	
			
			
		}
	});
	var button_update = new Ext.Button({
		text : '修改',
		tooltip : '修改选中的银行联系人信息',
		iconCls : 'updateIcon',
		scope : this,
		handler : function() {
			var selected = gPanelBankRelationPersonRefer.getSelectionModel().getSelected();
			if (null == selected) {
				Ext.ux.Toast.msg('状态', '请选择一条记录!');
			}else{
				var id = selected.get('id');
				Ext.Ajax.request({
					url : __ctxPath + '/creditFlow/customer/bankRelationPerson/seeBankRelationPersonCustomerBankRelationPerson.do',
					method : 'POST',
					success : function(response,request) {
						obj = Ext.util.JSON.decode(response.responseText);
							bankRelationPersonData = obj.data;
							var updateBankRelationPersonPanel = new Ext.form.FormPanel({
								id :'updateBankRelationPersonPanelb',
								url : __ctxPath + '/creditFlow/customer/bankRelationPerson/updateCustomerBankRelationPerson.do',
								//monitorValid : false,
								bodyStyle:'padding:10px',
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
											//allowBlank : false,
										    //blankText : '必填信息',
										    //regex : /^[\u4e00-\u9fa5]{1,10}$/,
											//regexText : '只能输入中文',
											value : bankRelationPersonData.name
										},{
											id : 'upbanknamerefer' ,
											xtype : 'textfield',
											fieldLabel : '所在银行',
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
											xtype : "dickeycombo",
					nodeKey :'8',
											fieldLabel : '婚姻状况',
											hiddenName : 'bankRelationPerson.marriage',
											//mode : 'romote',
											width : 80 ,
											editable : false,
										    emptyText:'请选择婚姻状况',
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
											fieldLabel : '电子邮件',
											name : 'bankRelationPerson.email',
											regex : /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/,
											regexText : '电子邮件格式不正确或无效的电子邮件地址',
											value : bankRelationPersonData.email
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
											nodeKey : 'sex_key',
											fieldLabel : "性别",
											hiddenName : 'bankRelationPerson.sex',
											width : 80 ,
											triggerAction : 'all',
											value : bankRelationPersonData.sex,
											valueNotFoundText : bankRelationPersonData.sexvalue
										},{
											id : 'upzhibanknamerefer',
											xtype : 'trigger',
											triggerClass :'x-form-search-trigger',
											fieldLabel : '银行支行',
											onTriggerClick : function(){
												selectDictionary('bank',getObjArrayReferUp);
											},
											editable : false,
											anchor:'100%',
											allowBlank : false,
											blankText : '必填信息',
											value : bankRelationPersonData.fenbankvalue
											/*xtype : 'textfield',
											fieldLabel : '银行支行',
											name : 'bankname', 
											readOnly : true,
											value : bankRelationPersonData.fenbankvalue*/
										},{
											xtype : 'textfield',
											fieldLabel : '职务',
											name : 'bankRelationPerson.duty',
											//allowBlank : false,
										   // blankText : '必填信息',
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
									id : 'upbankidrefer' ,
									xtype : 'hidden',
									name : 'bankRelationPerson.bankid',
									value : bankRelationPersonData.bankid
								},{
									id : 'upzhibankidrefer' ,
									xtype : 'hidden',
									name : 'bankRelationPerson.fenbankid',
									value : bankRelationPersonData.fenbankid
								},{
									xtype : 'hidden',
									name : 'bankRelationPerson.id',
									value : bankRelationPersonData.id
								}]
							})
							var updateBankRelationPersonWinRefer = new Ext.Window({
								id : 'updateBankRelationPersonWinRefer',
								title: '修改银行联系人信息',
								layout : 'fit',
								width : (screen.width-180)*0.7 - 100,
								height : 410,
								constrainHeader : true ,
								closable : true,
								resizable : true,
								plain : true,
								border : false,
								modal : true,
								buttonAlign: 'right',
								tbar :[new Ext.Button({text : '保存',tooltip : '保存基本信息',iconCls : 'submitIcon',hideMode:'offsets',
										handler :function(){
											formSave('updateBankRelationPersonPanelb', updateBankRelationPersonWinRefer ,jStoreBankRelationPersonRefer);
										}
								})],
						       	items :[updateBankRelationPersonPanel]
							}).show();			
					},
					failure : function(response) {					
							Ext.ux.Toast.msg('状态','操作失败，请重试');		
					},
					params: { id: id }
				});	
			}
		
		}
	});
	var button_see = new Ext.Button({
		text : '查看',
		tooltip : '查看选中的银行联系人信息',
		iconCls : 'seeIcon',
		scope : this,
		handler : function() {
			var selected = gPanelBankRelationPersonRefer.getSelectionModel().getSelected();
			if (null == selected) {
				Ext.ux.Toast.msg('状态', '请选择一条记录!');
			}else{
				var id = selected.get('id');
				Ext.Ajax.request({
					url : __ctxPath + '/creditFlow/customer/bankRelationPerson/seeBankRelationPersonCustomerBankRelationPerson.do',
					method : 'POST',
					success : function(response,request){
						obj = Ext.util.JSON.decode(response.responseText);
						bankRelationPersonData = obj.data;	
						var seeBankRelationPersonPanel = new Ext.form.FormPanel({
							id :'seeBankRelationPersonPanel',
							url : __ctxPath + '/creditFlow/customer/bankRelationPerson/seeBankRelationPersonCustomerBankRelationPerson.do',
							bodyStyle:'padding:10px',
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
										fieldLabel : '电子邮件',
										readOnly : true,
										value : bankRelationPersonData.email
									},{
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
							}]
						})
						var seeBankRelationPersonWinRefer = new Ext.Window({
							id : 'seeBankRelationPersonWinRefer',
							title: '查看银行联系人信息',
							layout : 'fit',
							width : (screen.width-180)*0.7 - 100,
							height : 410,
							closable : true,
							collapsible : true,
							resizable : true,
							plain : true,
							border : false,
							autoScroll : true ,
							modal : true,
							buttonAlign: 'right',
							bodyStyle:'overflowX:hidden',
					        items :[seeBankRelationPersonPanel]
						}).show();
						},
						failure : function(response) {					
								Ext.ux.Toast.msg('状态','操作失败，请重试');		
						},
						params: { id: id }
				});	
			}
		}
	}); 
	var cModelBankRelationPersonRefer = new Ext.grid.ColumnModel(
		[
			new Ext.grid.RowNumberer( {
				header : '序号',
				width : 35
			}),
			{
				header : "银行名称",
				width : 140,
				sortable : true,
				dataIndex : 'bankname'
			},{
				header : "支行名称",
				width : 140,
				sortable : true,
				dataIndex : 'fenbankvalue'
			},{
				header : "联系人姓名",
				width : 100,
				sortable : true,
				dataIndex : 'name'
			}, {
				header : "性别",
				width : 60,
				sortable : true,
				dataIndex : 'sexvalue'
			},{
				header : "职务",
				width : 90,
				sortable : true,
				dataIndex : 'duty'
			},{
				header : "办公电话",
				width : 100,
				sortable : true,
				dataIndex : 'blmtelephone'
			},{
				header : "手机",
				width : 100,
				sortable : true,
				dataIndex : 'blmphone'
			},{
				header : "电子邮箱",
				width : 120,
				sortable : true,
				dataIndex : 'email'
			} ]);

	var pagingBar = new Ext.PagingToolbar( {
		pageSize : 15,
		store : jStoreBankRelationPersonRefer,
		autoWidth : true,
		hideBorders : true,
		displayInfo : true,
		displayMsg : '当前第{0} - {1}条，共 {2} 条记录',
		emptyMsg : "没有符合条件的记录······"
	});
	var myMask = new Ext.LoadMask(Ext.getBody(), {
		msg : "加载数据中······,请稍后······"
	});
	
	var gPanelBankRelationPersonRefer = new Ext.grid.GridPanel( {
		id : 'gPanelBankRelationPersonRefer',
		store : jStoreBankRelationPersonRefer,
		colModel : cModelBankRelationPersonRefer,
		selModel : new Ext.grid.RowSelectionModel(),
		stripeRows : true,
		loadMask : myMask,
		autoWidth : true,
		height : Ext.getBody().getHeight()-100,
		bbar : pagingBar,
		tbar : [button_add,button_see,button_update ,'-' ,new Ext.form.Label({text : '联系人姓名：'}),new Ext.form.TextField({id:'paramCnNameBankRefer',width:100}), new Ext.form.Label({text : '职务：'}),new Ext.form.TextField({id:'paramCnDutyBankRefer',width:100}),{text:'查找'}],
		listeners : {
			'rowdblclick' : function(grid, index, e) {
				var selected = grid.getStore().getAt(index) ;
				callbackFun(selected,funName);
				winBankRelationPersonRefer.close();
			}
		}
	});
	Ext.getCmp('paramCnNameBankRefer').on('blur',function(){
		var nameValue = Ext.get('paramCnNameBankRefer').dom.value;
		jStoreBankRelationPersonRefer.baseParams.name = nameValue ;
		jStoreBankRelationPersonRefer.load({
			params : {
				start : 0,
				limit : 15,
				bankId : (bankId == null) ? 0 : bankId
			}
		});
	});
	Ext.getCmp('paramCnDutyBankRefer').on('blur',function(){
		var dutyValue = Ext.get('paramCnDutyBankRefer').dom.value;
		jStoreBankRelationPersonRefer.baseParams.duty = dutyValue ;
		jStoreBankRelationPersonRefer.load({
			params : {
				start : 0,
				limit : 15,
				bankId : (bankId == null) ? 0 : bankId
			}
		});
	});
	var winBankRelationPersonRefer = new Ext.Window({
		title : '银行联系人列表',
		width :(screen.width-180)*0.7 -150,
		height : 440,
		constrainHeader : true ,
		layout : 'fit',
		buttonAlign : 'center',
		items : [gPanelBankRelationPersonRefer],
		modal : true 
	}).show();
/*		
	var searchByCondition = function() {
		jStoreBankRelationPersonRefer.load({
			params : {
				start : 0,
				limit : 15
			}
		});
	}*/
	var callbackFun = function(selected,funName){
		bankRelationPersonJsonObj = {
				id : selected.get('id'),
				name : selected.get('name'),
				duty : selected.get('duty'),
				blmtelephone : selected.get('blmtelephone'),
				blmphone : selected.get('blmphone')
				}
		funName(bankRelationPersonJsonObj);
	}
});
	var formSave = function(formPanelId ,winObj ,storeObj){
		var formObj = Ext.getCmp(formPanelId);
		formObj.getForm().submit({
			method : 'POST',
			waitTitle : '连接',
			waitMsg : '消息发送中...',
			//formBind : true,
			success : function(form ,action) {
				Ext.ux.Toast.msg('状态', '保存成功!');
					//storeObj.reload();
					
					storeObj.load({
						params : {
							bankId : bankIdVal
						}
					});
					if(null != winObj){
						winObj.destroy();
					}
			},
			failure : function(form, action) {
				Ext.ux.Toast.msg('状态','保存失败!可能数据没有填写完整');
			}
		})
	}
	var getObjArrayRefer = function(objArray){
		Ext.getCmp('addbanknamerefer').setValue(objArray[(objArray.length)-1].text);
		Ext.getCmp('addbankidrefer').setValue(objArray[(objArray.length)-1].id);
		bankIdVal = objArray[0].id ;
		Ext.getCmp('addzhibanknamerefer').setValue(objArray[0].text);
		Ext.getCmp('addzhibankidrefer').setValue(objArray[0].id);
		Ext.getCmp('addzhibanknamerefer').readOnly = true
	}
	var getObjArrayReferUp = function(objArray){
		Ext.getCmp('upbanknamerefer').setValue(objArray[(objArray.length)-1].text);
		Ext.getCmp('upbankidrefer').setValue(objArray[(objArray.length)-1].id);
		bankIdVal = objArray[0].id ;
		Ext.getCmp('upzhibanknamerefer').setValue(objArray[0].text);
		Ext.getCmp('upzhibankidrefer').setValue(objArray[0].id);
		Ext.getCmp('upzhibanknamerefer').readOnly = true
	}
}
