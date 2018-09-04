/**
 * 
 * @class
 * @extends Ext.form.FormPanel
 * 设置参数  id:随机ID
 *           personData:add时null edit时为从数据库查询的对象
 */
var personObj = Ext.extend(Ext.form.FormPanel, {
	isRead : false,
	isflag : false,
	constructor : function(_cfg) {
		if (_cfg == null) {
			_cfg = {};
		};
		Ext.applyIf(this, _cfg);
		if(_cfg.isReadOnly)
		{
			this.isRead=_cfg.isReadOnly;
		};
		if(null!=_cfg.personData){
		    this.isflag=true;
		};
		this.initUIComponents();
		var personData = this.personData;
		var panelId=_cfg.id;
		var panel_add=this;
		var isEdit =true;//用来标示是否为添加页面   false表示添加页面
		if(personData==null||personData=="undefined"){
				isEdit=false;
		}
		
		personObj.superclass.constructor.call(this, {
			labelAlign : 'right',
			buttonAlign : 'center',
			frame : true,
			monitorValid : true,
			id:panelId,
			autoScroll : true,
			bodyStyle : 'overflowX:hidden',
			layout : 'form',
			url:this.url,
			border : false,
			items : [{
				layout : 'column',
				autoHeight : true,
				collapsible : false,
				anchor : '100%',
				items : [{
					columnWidth : 0.8,
					items : [{
						layout : 'column',
						xtype : 'fieldset',
						title : '个人基本信息',
						collapsible : true,
						autoHeight : true,
						anchor : '100%',
						items : [{
							columnWidth : 1,
							
							layout : 'column',
							items : [{
								columnWidth : .66,
								
								layout : 'column',
								items : [{
									columnWidth : 0.5,
									layout : 'form',
									defaults : {
										anchor : '100%'
									},
									labelWidth : 110,
									scope : this,
									items : [{
										xtype : "hidden",
										name : "person.creater",
										value : personData == null?null:personData.creater
									}, {
										xtype : "hidden",
										name : "person.createrId",
										value : personData == null? null: personData.createrId
									}, {
										xtype : "hidden",
										name : "person.createdate",
										value : personData == null?null:personData.createdate
									}, {
										xtype : "hidden",
										name : "person.companyId",
										value : personData == null?null:personData.companyId
									},{
										xtype : 'hidden',
										name : 'person.id',
										value : personData == null ? null : personData.id
									},{
										xtype : 'hidden',
										name : 'person.grossdebt',
										value : personData == null ? null : personData.grossdebt
									}, {
										xtype : 'textfield',
										fieldLabel : '<font color=red>*</font>中文姓名',
										allowBlank : false,
										name : 'person.name',
										blankText : '姓名为必填内容',
										readOnly : this.isRead,
										value : personData == null?null:personData.name,
										listeners : {
											'afterrender':function(com){
											    com.clearInvalid();
											}
										}
												
									}, {
										xtype : "dickeycombo",
										nodeKey : 'card_type_key',
										hiddenName : "person.cardtype",
										itemName : '证件类型', // xx代表分类名称
										fieldLabel : "证件类型",
										allowBlank : false,
										editable : true,
										readOnly : this.isRead || (personData == null?false:personData.isCardcodeReadOnly),
										value : personData == null
												? null
												: personData.cardtype,
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
									}, {
										xtype : "dickeycombo",
										nodeKey : 'sex_key',
										hiddenName : 'person.sex',
										fieldLabel : "性别",
										editable : true,
										readOnly : this.isRead,
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
											},
											select : function(combo, record,
													index) {
												var display = Ext
														.getCmp('displayProfilePhoto');
												if (combo.value == '313') {// 312
													display.body
															.update('<img src="'
																	+ __ctxPath
																	+ '/images/default_image_female.jpg" />');
												} else {
													display.body
															.update('<img src="'
																	+ __ctxPath
																	+ '/images/default_image_male.jpg" />');
												}
											}
										}
									}, /*{
										xtype : 'datefield',
										fieldLabel : '出生日期',
										name : 'person.birthday',
										format : 'Y-m-d',
										readOnly : this.isRead,
										value : personData == null
												? null
												: personData.birthday
									},*/{
										xtype : "dickeycombo",
										nodeKey : 'nationality',
										fieldLabel : '民族',
										// emptyText : '请选择民族',
										width : 80,
										hiddenName : 'person.nationality',
										readOnly : this.isRead,
										value : personData == null
												? null
												: personData.nationality,
										listWidth : 80,
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
										xtype : 'textfield',
										fieldLabel : '手机号码',
										name : 'person.cellphone',
										value : personData == null? null: personData.cellphone,
										allowBlank : false,
										readOnly : this.isRead,
										regex : /^[1][34578][0-9]{9}$/,
										regexText : '手机号码格式不正确'	,									
										listeners : {
											scope:this,
											'blur' : function(f) {
												var reg=/^[1][34578][0-9]{9}$/;
												var flag=reg.test(this.getCmpByName('person.cellphone').getValue());
												if(!flag){
													this.getCmpByName('person.cellphone').setValue(null);
												}
											
											}
										}
										
									}, {
										xtype : 'textfield',
										fieldLabel : '传真号码',
										name : 'person.fax',
										value : personData == null?null:personData.fax,
										readOnly : this.isRead,
										regex :/(\d{3,4})?(\-)?\d{7,8}/
									}, {
										xtype : 'textfield',
										fieldLabel : '电子邮箱',
										name : 'person.selfemail',
										readOnly : this.isRead,
										value : personData == null
												? null
												: personData.selfemail,
										regex : /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+/,
										regexText : '电子邮箱格式不正确',
										listeners : {
											'afterrender':function(com){
											    com.clearInvalid();
											}
										}	

									},{
									xtype : "combo",
									anchor : "100%",
									hiddenName : "homelandProvince",
									displayField : 'itemName',
									valueField : 'itemId',
									triggerAction : 'all',
									readOnly : this.isRead,
									store : new Ext.data.SimpleStore({
										autoLoad : true,
										url : __ctxPath
												+ '/creditFlow/multiLevelDic/listByParentIdAreaDic.do',
										fields : ['itemId', 'itemName'],
										baseParams:{parentId:6591}
									}),
									value : personData==null?null:personData.parentHomeland,
									fieldLabel : "籍贯:省",
									listeners : {
										scope : this,
										afterrender : function(combox) {
											var st = combox.getStore();
											st.on("load", function() {
														combox.setValue(combox.getValue());
													})
											combox.clearInvalid();
										},
										select : function(combox, record, index) {
											var v = record.data.itemId;
										var arrStore = new Ext.data.SimpleStore({
											url : __ctxPath
													+ '/creditFlow/multiLevelDic/listByParentIdAreaDic.do',
											fields : ['itemId', 'itemName'],
											baseParams:{parentId:v}
										})
										var opr_obj = this.getCmpByName('person.homeland')
										opr_obj.clearValue();
										opr_obj.store = arrStore;
										arrStore.load({
													"callback" : test
												});
										function test(r) {
											if (opr_obj.view) { // 刷新视图,避免视图值与实际值不相符
												opr_obj.view.setStore(arrStore);
											}
											
										}
										}
									}
							},{
									xtype : "combo",
									anchor : "100%",
									hiddenName : "parentLiveCity",
									displayField : 'itemName',
									valueField : 'itemId',
									triggerAction : 'all',
									readOnly : this.isRead,
									store : new Ext.data.SimpleStore({
										autoLoad : true,
										url : __ctxPath
												+ '/creditFlow/multiLevelDic/listByParentIdAreaDic.do',
										fields : ['itemId', 'itemName'],
										baseParams:{parentId:6591}
									}),
									value : personData==null?null:personData.parentLiveCity,
									fieldLabel : "居住城市:省",
									listeners : {
										scope : this,
										afterrender : function(combox) {
											var st = combox.getStore();
											st.on("load", function() {
														combox.setValue(combox.getValue());
													})
											combox.clearInvalid();
										},
										select : function(combox, record, index) {
											var v = record.data.itemId;
										var arrStore = new Ext.data.SimpleStore({
											url : __ctxPath
													+ '/creditFlow/multiLevelDic/listByParentIdAreaDic.do',
											fields : ['itemId', 'itemName'],
											baseParams:{parentId:v}
										})
										var opr_obj = this.getCmpByName('person.liveCity')
										opr_obj.clearValue();
										opr_obj.store = arrStore;
										arrStore.load({
													"callback" : test
												});
										function test(r) {
											if (opr_obj.view) { // 刷新视图,避免视图值与实际值不相符
												opr_obj.view.setStore(arrStore);
											}
											
										}
										}
									}
							},{
								xtype : 'numberfield',
								fieldLabel : '最高学历入学年份',
								name : 'person.collegeYear',
								readOnly : this.isRead,
								value : personData==null?null:personData.collegeYear
							},{
							    xtype:'textfield',
							    fieldLabel:'本地居住年限',
							    readOnly : this.isRead,
								value : personData == null
										? null
										: personData.livingLife,
							    name : 'person.livingLife'
							},{
								columnWidth : .33,
								layout : 'form',
								labelWidth : 100,
								defaults : {
									
									anchor : '100%'
								},
								items : [{
									xtype : "dickeycombo",
									nodeKey : 'dgree',
									fieldLabel : '最高学历',
									hiddenName : 'person.dgree',
									emptyText : '请选择学历',
									blankText : "客户学历不能为空，请正确填写!",
									width : 80,
									editable : false,
									value : personData == null? null: personData.dgree,
									readOnly : this.isRead,
									listeners : {
										afterrender : function(combox) {
											var st = combox.getStore();
											st.on("load", function() {
												if (combox.getValue() == 0
														|| combox.getValue() == 1
														|| combox.getValue() == ""
														|| combox.getValue() == null) {
													combox.setValue("");
												} else {
													combox.setValue(combox
															.getValue());
												}
												combox.clearInvalid();
											})
										}
									}
								}]
							}, {
									columnWidth : .33,
									layout : 'form',
									labelWidth : 100,
									defaults : {
										
										anchor : '100%'
									},
									items : [ {
										xtype : 'textfield',
										fieldLabel : 'QQ',
										readOnly : this.isRead,
										value : personData == null
												? null
												: personData.qq,
										name : 'person.qq'
									}]
								}, {
										xtype : 'hidden',
										name : 'personPhotoId',
										value : personData==null?null:personData.personPhotoId
									}, {
										xtype : 'hidden',
										name : 'personSFZZId',
										value : personData==null?null:personData.personSFZZId
									}, {
										xtype : 'hidden',
										name : 'personSFZFId',
										value : personData==null?null:personData.personSFZFId
									}]

								}, {
									columnWidth : 0.5,
									layout : 'form',
									defaults : {
										labelWidth : 95,
										anchor : '100%'
									},
									items : [{
										xtype : 'textfield',
										fieldLabel : '拼音/英文姓名',
										name : 'person.englishname',
										readOnly : this.isRead,
										value : personData == null ? null : personData.englishname
									}, {
										xtype : 'textfield',
										fieldLabel : '证件号码',
										name : 'person.cardnumber',
										allowBlank : false,
										blankText : '证件号码为必填内容',
										readOnly : this.isRead || (personData == null?false:personData.isCardcodeReadOnly),
										value : personData == null ? null : personData.cardnumber,	
										listeners : {
											scope:this,
											'blur' : function(f) {
												if(this.getCmpByName('person.cardtype').getValue()==309){
													if(validateIdCard(f.getValue())==1){
														Ext.Msg.alert('身份证号码验证','证件号码不正确,请仔细核对')
														f.setValue("");
														return;
													}else if(validateIdCard(f.getValue())==2){
														Ext.Msg.alert('身份证号码验证','证件号码地区不正确,请仔细核对')
														f.setValue("");
														return;
													}else if(validateIdCard(f.getValue())==3){
														Ext.Msg.alert('身份证号码验证','证件号码生日日期不正确,请仔细核对')
														f.setValue("");
														return;
													}
												}
												var penal=this.getCmpByName("person.birthday");
												var penalAge=this.getCmpByName("person.age");
												var sex=this.getCmpByName("person.sex");
												var cardNumber = f.getValue();
												var personId = (personData==null)?0:personData.id;
												Ext.Ajax.request({
								                   	url:  __ctxPath + '/creditFlow/customer/person/verificationPerson.do',
								                   	method : 'POST',
								                   	params : {
														cardNum : cardNumber,
														personId: personId
													},
								                  	success : function(response,request) {
														var obj=Ext.util.JSON.decode(response.responseText);
					                            		if(obj.msg == false){					                            			
					                            			Ext.ux.Toast.msg('操作信息',"该证件号码已存在，请重新输入");
					                            			f.setValue("");
					                            		}else{
					                            			if(cardNumber.split("").reverse()[1]%2==0){
					                            				sex.setValue(313);
					                            				sex.setRawValue("女")
					                            			}else{
					                            				sex.setValue(312);
					                            				sex.setRawValue("男")
					                            			}
					                            			//拆分身份证号码 ，拿出出生年月日  
//						                            			if(!isEdit && personData==null){//只有新增才需要默认加载身份证上的出生年月日
						                            			var brithday= cardNumber.substr(6,8);
																var formatBrithday = brithday.substr(0,4)+"-"+brithday.substr(4,2)+"-"+brithday.substr(6,2);
						                            			penal.setValue(formatBrithday)
						                            			var nowDate=new Date();
						                            			var nowYear=Ext.util.Format.date(nowDate,'Y-m-d').substr(0,4);
						                            			var brith=brithday.substr(0,4);
						                            			penalAge.setValue(Number(nowYear)-Number(brith))//年龄
//						                            			}
					                            		}
							                      	}
					                            });
												
											},
											afterrender:function(f){
												if(this.getCmpByName('person.cardtype').getValue()==309){
													var penalAge=this.getCmpByName("person.age");
													var cardNumber = f.getValue();
													var brithday= cardNumber.substr(6,8);
													var nowDate=new Date();
								                    var nowYear=Ext.util.Format.date(nowDate,'Y-m-d').substr(0,4);
								                    var brith=brithday.substr(0,4);
								                    penalAge.setValue(Number(nowYear)-Number(brith))//年龄
												}
											}		
										}
									}, {
										xtype : 'datefield',
										fieldLabel : '出生日期',
										name : 'person.birthday',
										//allowBlank : false,
										format : 'Y-m-d',
										blankText : "出生日期不能为空，请正确填写!",
										readOnly : this.isRead || (personData == null?false:personData.isCardcodeReadOnly),
										value : personData == null? null: personData.birthday,
										listeners : {
											scope : this,
											'change' : function(tf) {
												var age=this.getCmpByName('person.age');
												var birthStr = tf.value.replace(/-/g,'/');
												var birthDay = new Date(birthStr).getTime();
												var now = new Date().getTime();
												var hours = (now - birthDay)/1000/60/60;
												var year =  Math.floor(hours / (24 * 30 * 12));
												age.setValue(year);
											}
										}
									}, {
										xtype : "dickeycombo",
										nodeKey : '8',
										hiddenName : 'person.marry',
										fieldLabel : "婚姻状况",
										itemName : '婚姻状况', // xx代表分类名称
										
										editable : true,
										readOnly : this.isRead,
										value : personData == null
												? null
												: personData.marry,
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
										xtype : 'textfield',
										fieldLabel : '家庭电话',
										name : 'person.telphone',
										readOnly : this.isRead,
										value : personData == null
												? null
												: personData.telphone
									}, {
										xtype : 'combo',
										hiddenName : 'person.careerType',
										anchor : '100%',
										fieldLabel:'职业类型',
										readOnly : this.isRead,
										mode : 'local',
										forceSelection : true, 
										displayField : 'typeValue',
										valueField : 'typeId',
										editable : false,
										triggerAction : 'all',
										value : personData == null
												? null
												: personData.careerType,
										store : new Ext.data.SimpleStore({
											data : [['网商',0],['工薪',1],['私营企业主',2],['教师',3]],
											fields:['typeValue','typeId']
										})
									}, {
										xtype : 'textfield',
										fieldLabel : '邮政编码',
										name : 'person.postcode',
										readOnly : this.isRead,
										//allowBlank : false,
										blankText : '邮政编码为必填内容',
										regex : /^[0-9]{6}$/,
										regexText : '邮政编码格式不正确',
										value : personData == null
												? null: personData.postcode,
										listeners : {
											scope:this,
											'blur' : function(f) {
												var reg=/^[0-9]{6}$/;
												var flag=reg.test(this.getCmpByName('person.postcode').getValue());
												if(!flag){
													this.getCmpByName('person.postcode').setValue(null);
												}
											
											}
										}		
									},{
										xtype : "combo",
										anchor : "100%",
										hiddenName : "person.homeland",
										displayField : 'itemName',
										valueField : 'itemId',
										triggerAction : 'all',
										readOnly : this.isRead,
										store : new Ext.data.SimpleStore({
											url : __ctxPath
													+ '/creditFlow/multiLevelDic/listByParentIdAreaDic.do',
											fields : ['itemId', 'itemName']
										}),
										fieldLabel : "市",
										listeners : {
											scope : this,
											afterrender : function(opr_obj) {
												var v=(personData==null?null:personData.parentHomeland)
													var arrStore = new Ext.data.SimpleStore({
														url : __ctxPath
																+ '/creditFlow/multiLevelDic/listByParentIdAreaDic.do',
														fields : ['itemId', 'itemName'],
														baseParams:{parentId:v}
													})
													opr_obj.clearValue();
													opr_obj.store = arrStore;
													arrStore.load({
																"callback" : test
															});
													function test(r) {
														if (opr_obj.view) { // 刷新视图,避免视图值与实际值不相符
															opr_obj.view.setStore(arrStore);
														}
														opr_obj.setValue(personData==null?null:personData.homeland)
													}
											}
										}
							},{
										xtype : "combo",
										anchor : "100%",
										hiddenName : "person.liveCity",
										displayField : 'itemName',
										valueField : 'itemId',
										triggerAction : 'all',
										readOnly : this.isRead,
										store : new Ext.data.SimpleStore({
											url : __ctxPath
													+ '/creditFlow/multiLevelDic/listByParentIdAreaDic.do',
											fields : ['itemId', 'itemName']
										}),
										fieldLabel : "市",
										listeners : {
											scope : this,
											afterrender : function(opr_obj) {
												var v=(personData==null?null:personData.parentLiveCity)
													var arrStore = new Ext.data.SimpleStore({
														url : __ctxPath
																+ '/creditFlow/multiLevelDic/listByParentIdAreaDic.do',
														fields : ['itemId', 'itemName'],
														baseParams:{parentId:v}
													})
													opr_obj.clearValue();
													opr_obj.store = arrStore;
													arrStore.load({
																"callback" : test
															});
													function test(r) {
														if (opr_obj.view) { // 刷新视图,避免视图值与实际值不相符
															opr_obj.view.setStore(arrStore);
														}
														opr_obj.setValue(personData==null?null:personData.liveCity)
													}
											}
										}
							},{
								xtype : 'textfield',
								fieldLabel : '毕业院校',
								readOnly : this.isRead,
								value :personData==null?null:personData.graduationunversity,
								name : 'person.graduationunversity'
							}, {
								xtype : 'textfield',
								fieldLabel : '共同居住者',
								allowBlank : true,
								readOnly : this.isRead,
								value : personData == null
										? null
										: personData.relationname,
								name : 'person.relationname'
							},{
							columnWidth : .66,
							layout : 'form',
							align : 'left',
							labelWidth : 100,
							       /* xtype : "combo",
									anchor : "100%",
									hiddenName : "homelandProvince",
									displayField : 'itemName',
									valueField : 'itemId',
									triggerAction : 'all',*/
							items : [{
								xtype : "combo",
								anchor : "100%",
								hiddenName : "person.parenthukou",
								displayField : 'itemName',
								valueField : 'itemId',
								triggerAction : 'all',
								readOnly : this.isRead,
								store : new Ext.data.SimpleStore({
									autoLoad : true,
									url : __ctxPath
											+ '/creditFlow/multiLevelDic/listByParentIdAreaDic.do',
									fields : ['itemId', 'itemName'],
									baseParams:{parentId:6591}
								}),
								value : personData==null?null:personData.parenthukou,
								fieldLabel : "户口所在地:省",
								listeners : {
									scope : this,
									afterrender : function(combox) {
										var st = combox.getStore();
										st.on("load", function() {
													combox.setValue(combox.getValue());
												})
										combox.clearInvalid();
									},
									select : function(combox, record, index) {
										var v = record.data.itemId;
									var arrStore = new Ext.data.SimpleStore({
										url : __ctxPath
												+ '/creditFlow/multiLevelDic/listByParentIdAreaDic.do',
										fields : ['itemId', 'itemName'],
										baseParams:{parentId:v}
									})
									var opr_obj = this.getCmpByName('person.hukou')
									opr_obj.clearValue();
									opr_obj.store = arrStore;
									arrStore.load({
												"callback" : test
											});
									function test(r) {
										if (opr_obj.view) { // 刷新视图,避免视图值与实际值不相符
											opr_obj.view.setStore(arrStore);
										}
										
									}
									}
								}
							}]
						},{
							columnWidth : .33,
							layout : 'form',
							labelWidth : 40,
							defaults : {
								
								anchor : '100%'
							},
							items : [ {
								xtype : 'textfield',
								fieldLabel : '微信',
								//allowBlank : false,
								readOnly : this.isRead,
								value : personData == null
										? null
										: personData.microMessage,
								name : 'person.microMessage'
							}]
						}]
								}]
							}, {
								columnWidth : .33,
								labelWidth : 120,
								layout : 'column',
								items : [{
									columnWidth : 1,
									layout : 'form',
									defaults : {
										anchor : '100%'
									},
									scope : this,
									items : [{
										xtype : "hidden",
										name : 'person.belongedId',
										value : personData == null
												? null
												: personData.belongedId
									}, {
										hiddenName : 'belongedName',
										xtype : 'trigger',
										fieldLabel : '客户授权人',
										submitValue : true,
										triggerClass : 'x-form-search-trigger',
										editable : false,
										value : personData == null
												? null
												: personData.belongedName,
										scope : this,
										onTriggerClick : function() {
											var obj = this;
											var belongedObj = obj
													.previousSibling();
											var userIds = belongedObj
													.getValue();
											if (null == obj.getValue()
													|| "" == obj.getValue()) {
												userIds = "";
											}
											new UserDialog({
												userIds : userIds,
												userName : obj.getValue(),
												single : false,
												title : "客户授权人",
												callback : function(uId, uname) {
													if((!isEdit)&&((","+uId+",").indexOf(","+curUserInfo.userId+",")==-1)){
														uId=uId+","+curUserInfo.userId
														uname=uname+","+curUserInfo.fullname
													}
													obj.setRawValue(uname);
													belongedObj.setValue(uId);
												}
											}).show();
										}
									}, {
										xtype : 'datefield',
										fieldLabel : '证件有效期至',
										name : 'person.validity',
										format : 'Y-m-d',
										value : personData == null? null: personData.validity,
										readOnly : this.isRead
						
									}/*,{
										xtype : "dickeycombo",
										hiddenName : "person.customerLevel",
										nodeKey : 'customerLevel', // xx代表分类名称
										fieldLabel : "客户级别",
										readOnly : (this.isRead == false&&isGranted('_editCustomerLevel_grkh'))? false: true,
										value : personData == null? null: personData.customerLevel,
										emptyText : "请选择",
										listeners : {
											afterrender : function(combox) {
												var st = combox.getStore();
												st.on("load", function() {
													
													combox.setValue(combox.getValue());
													combox.clearInvalid();
												})
									       }
										}
									}*/,{
										xtype : "numberfield",
										name : "person.age",
										fieldLabel : "年龄",
										allowBlank : true,
										blankText : "客户年龄不能为空，请正确填写!",
										readOnly : this.isRead,
										editable:false,
										value : personData == null ? null : personData.age
									}, {
										name:'displayProfilePhoto',
										id:'displayProfilePhoto',
										xtype : "panel",
										width : 100,
										rowspan : 2,
										style : 'padding:3px 4px 10px 15px;',
										height : 180,
										title : "个人照片",
										html : function(){
											if(personData==null || null==personData.personPhotoId || ""==personData.personPhotoId || personData.personPhotoId==0){
												return '<img src="'
														+ __ctxPath
														+ '/images/default_image_male.jpg"/>'
											}else if(personData!=null && null!=personData.personPhotoId && ""!=personData.personPhotoId && personData.personPhotoId!=0 && (personData.personPhotoExtendName==".jpg" || personData.personPhotoExtendName==".jpeg")){
												return '<img src="'
														+ __ctxPath
														+ '/'
														+ personData.personPhotoUrl
														+ '" ondblclick=showPic("'
														+ personData.personPhotoUrl
														+ '") width="100%" height="100%"/>'
											}else if(personData!=null && null!=personData.personPhotoId && ""!=personData.personPhotoId && personData.personPhotoId!=0 && personData.personPhotoExtendName==".pdf"){
												return '<img src="'
														+ __ctxPath
														+ '//images//desktop//pdf_win.jpg" ondblclick=showPdf('
														+personData.personPhotoId+',"'
														+ personData.personPhotoUrl
														+ '") width="100%" height="100%"/>'
											}
										}(),
										
										/*(personData==null || null==personData.personPhotoId || ""==personData.personPhotoId || personData.personPhotoId==0)? '<img src="'
														+ __ctxPath
														+ '/images/default_image_male.jpg"/>'
												: '<img src="'
														+ __ctxPath
														+ '/'
														+ personData.personPhotoUrl
														+ '" ondblclick=showPic("'
														+ personData.personPhotoUrl
														+ '") width="100%" height="100%"/>',*/
										tbar : this.isRead
												? null
												: new Ext.Toolbar({
													height : 30,
													items : [{
														text : '上传',
														iconCls : 'slupIcon',
														handler : function() {
															uploadPhotoBtnPerson_new('个人照片','cs_person_tx','displayProfilePhoto','personPhotoId',panelId);
																	
														}
																
														
													}, '-', {
														text : '删除',
														iconCls : 'deleteIcon',
														handler : function() {
															delePhotoFile_new('personPhotoId','displayProfilePhoto',panelId);
														}
													}]
												})
									}, {
									xtype : 'numberfield',
									fieldLabel : '单张信用卡最高额度',
									name : 'person.befMonthBalance',
									value :personData==null?null:personData.befMonthBalance,
									readOnly : this.isRead
								}]

								}]
							},{
								columnWidth : .33,
								layout : 'form',
								labelWidth : 100,
								defaults : {
									
									anchor : '100%'
								},
								items : [{}]
							},{
							columnWidth : .33,
							layout : 'form',
							labelWidth :80,
							items : [{
								
								
										xtype : "combo",
										anchor : "100%",
										hiddenName : "person.hukou",
										displayField : 'itemName',
										valueField : 'itemId',
										triggerAction : 'all',
										readOnly : this.isRead,
										store : new Ext.data.SimpleStore({
											url : __ctxPath
													+ '/creditFlow/multiLevelDic/listByParentIdAreaDic.do',
											fields : ['itemId', 'itemName']
										
										}),
										fieldLabel : "市",
										listeners : {
											scope : this,
											afterrender : function(opr_obj) {
												var v=(personData==null?null:personData.parenthukou)
													var arrStore = new Ext.data.SimpleStore({
														url : __ctxPath
																+ '/creditFlow/multiLevelDic/listByParentIdAreaDic.do',
														fields : ['itemId', 'itemName'],
														baseParams:{parentId:v}
													})
													opr_obj.clearValue();
													opr_obj.store = arrStore;
													arrStore.load({
																"callback" : test
															});
													function test(r) {
														if (opr_obj.view) { // 刷新视图,避免视图值与实际值不相符
															opr_obj.view.setStore(arrStore);
														}
														opr_obj.setValue(personData==null?null:personData.hukou)
													}
											}
										}
							}]
						},{
									columnWidth : .33,
									layout : 'form',
									labelWidth : 80,
									labelAlign : 'right',
									items : [{
										xtype : "dickeycombo",
										hiddenName : "person.archivesBelonging",
										nodeKey : 'archivesBelonging', // xx代表分类名称
										fieldLabel : "档案归属地",
										value : personData == null
												? null
												: personData.archivesBelonging,
										readOnly : this.isRead,
										editable : false,
										anchor : "100%",
										listeners : {
											afterrender : function(combox) {
												var st = combox.getStore();
												st.on("load", function() {
													combox.setValue(personData.archivesBelonging);
													combox.clearInvalid();
												})
									       }
										}
				
									}]
								},{
									columnWidth :1,
									layout : 'form',
									labelWidth : 100,
									labelAlign : 'right',
									items : [{
										xtype : 'textfield',
										name : 'person.postaddress',
										readOnly : this.isRead,
										anchor : "99%",
										fieldLabel : '通讯地址',
										value : personData == null
												? null
												: personData.postaddress
									}]
								},{
									columnWidth : 1,
									layout : 'form',
									labelWidth : 100,
									labelAlign : 'right',
									items : [{
										fieldLabel : '门店名称',
										anchor : '99%',
										xtype : 'combo',
										readOnly : this.isRead,
										editable : false,
										triggerClass : 'x-form-search-trigger',
										hiddenName : "person.shopName",
										value : personData == null
												? null
												: personData.shopName,
										onTriggerClick : function() {
											var op = this.ownerCt.ownerCt.ownerCt.ownerCt;
											var EnterpriseNameStockUpdateNew = function(obj) {
												if (null != obj.orgName && "" != obj.orgName)
													op.getCmpByName('person.shopName').setValue(obj.orgName);
												if (null != obj.orgId && "" != obj.orgId)
													op.getCmpByName('person.shopId').setValue(obj.orgId);
											}
											selectShop(EnterpriseNameStockUpdateNew);
										}
									},{
										xtype:'hidden',
										name:'person.shopId',
										value : personData == null
												? null
												: personData.shopId
									}]
								}/*,{
										xtype : 'numberfield',
										name : 'person.age',
										hidden : true,
										readOnly : this.isRead,
										value : personData == null? null: personData.age
									}*/]
						}]
					}, {
						layout : 'column',
						xtype : 'fieldset',
						title : '身份证扫描件',
						labelWidth : 80,
						collapsible : true,
						autoHeight : true,
						anchor : '100%',
						items : [{
							columnWidth : 1,
							layout : 'column',
							labelWidth : 65,
							defaults : {
								anchor : '100%'
							},
							items : [{
								columnWidth : 0.33,
								layout : 'form',
								labelWidth : 65,
								defaults : {
									anchor : '100%'
								},
								scope : this,
								items : [{
									xtype : 'label',
									style : 'padding-left :20px',
									html : this.isRead
											? ''
											: '身份证正面&nbsp;&nbsp;&nbsp;<a href="#" onClick =uploadPhotoBtnPerson_new(\'身份证正面\',\'cs_person_sfzz\',\'shenfenzheng-z\',\'personSFZZId\',\''+ panelId+'\')>上传</a>&nbsp;&nbsp;&nbsp;<a href="#" onClick =delePhotoFile_new(\'personSFZZId\',\'shenfenzheng-z\',\''+ panelId+'\')>删除</a>'
								}, {
									xtype : 'label',
									style : 'padding-left :20px',
									name:'shenfenzheng-z',
									html :function(){
										if(personData==null || null==personData.personSFZZId || ""==personData.personSFZZId || personData.personSFZZId==0){
											return '<img src="'
													+ __ctxPath
													+ '/images/nopic.jpg" width =140 height=80 />'
										}else if(personData!=null && null!=personData.personSFZZId && ""!=personData.personSFZZId && personData.personSFZZId!=0 && (personData.personSFZZExtendName.toLowerCase()==".jpg" || personData.personSFZZExtendName.toLowerCase()==".jpeg")){
											return '<div style="width:144px; height:84px; margin:10px 0px 0px 20px; padding:1px 1px 1px 1px"><img src="'
													+ __ctxPath
													+ '/'
													+ personData.personSFZZUrl
													+ '" ondblclick=showPic("'
													+ personData.personSFZZUrl
													+ '") width =140 height=80  /></div>'
										}else if(personData!=null && null!=personData.personSFZZId && ""!=personData.personSFZZId && personData.personSFZZId!=0 && personData.personSFZZExtendName==".pdf"){
											return '<div style="width:144px; height:84px; margin:10px 0px 0px 20px; padding:1px 1px 1px 1px"><img src="'
													+ __ctxPath
													+ '//images//desktop//pdf_win.jpg" ondblclick=showPdf('
													+personData.personSFZZId+',"'
													+ personData.personSFZZUrl
													+ '") width =140 height=80  /></div>'
										}
									}()
									
									/*(personData==null || null==personData.personSFZZId || ""==personData.personSFZZId || personData.personSFZZId==0)?
											'<img src="'
													+ __ctxPath
													+ '/images/nopic.jpg" width =140 height=80 />'
											        : '<div style="width:144px; height:84px; margin:10px 0px 0px 20px; padding:1px 1px 1px 1px"><img src="'
													+ __ctxPath
													+ '/'
													+ personData.personSFZZUrl
													+ '" ondblclick=showPic("'
													+ personData.personSFZZUrl
													+ '") width =140 height=80  /></div>'*/
								}]
							}, {
								columnWidth : 0.33,
								layout : 'form',
								labelWidth : 65,
								defaults : {
									anchor : '100%'
								},
								scope : this,
								items : [{
									xtype : 'label',
									html : this.isRead?'': '身份证反面&nbsp;&nbsp;&nbsp;<a href="#" onClick =uploadPhotoBtnPerson_new(\'身份证反面\',\'cs_person_sfzf\',\'shenfenzheng-f\',\'personSFZFId\',\''+ panelId+'\')>上传</a>&nbsp;&nbsp;&nbsp;<a href="#" onClick =delePhotoFile_new(\'personSFZFId\',\'shenfenzheng-f\',\''+ panelId+'\')>删除</a>'
								}, {
									name:'shenfenzheng-f',
									xtype : 'label',
									html : function(){
										if(personData==null || null==personData.personSFZFId || ""==personData.personSFZFId || personData.personSFZFId==0){
											return '<img src="'+ __ctxPath+ '/images/nopic.jpg" width =140 height=80 />'
										}else if(personData!=null && null!=personData.personSFZFId && ""!=personData.personSFZFId && personData.personSFZFId!=0 && (personData.personSFZFExtendName.toLowerCase()==".jpg" || personData.personSFZFExtendName.toLowerCase()==".jpeg")){
											return '<div style="width:144px; height:84px; margin:10px 0px 0px 20px; padding:1px 1px 1px 1px;"><img src="'
												+ __ctxPath
												+ '/'
												+ personData.personSFZFUrl
												+ '" ondblclick=showPic("'
												+ personData.personSFZFUrl
												+ '") width =140 height=80  /></div>'
										}else if(personData!=null && null!=personData.personSFZFId && ""!=personData.personSFZFId && personData.personSFZFId!=0 && personData.personSFZFExtendName==".pdf"){
											return '<div style="width:144px; height:84px; margin:10px 0px 0px 20px; padding:1px 1px 1px 1px"><img src="'
													+ __ctxPath
													+ '//images//desktop//pdf_win.jpg" ondblclick=showPdf('
													+personData.personSFZFId+',"'
													+ personData.personSFZFUrl
													+ '") width =140 height=80  /></div>'
										}
									}()
									
									/*(personData==null || null==personData.personSFZFId || ""==personData.personSFZFId || personData.personSFZFId==0)? '<img src="'+ __ctxPath+ '/images/nopic.jpg" width =140 height=80 />'
										        : '<div style="width:144px; height:84px; margin:10px 0px 0px 20px; padding:1px 1px 1px 1px;"><img src="'
												+ __ctxPath
												+ '/'
												+ personData.personSFZFUrl
												+ '" ondblclick=showPic("'
												+ personData.personSFZFUrl
												+ '") width =140 height=80  /></div>'*/
								}]
							}]
						}]
					}/*,{
						layout : 'column',
						xtype : 'fieldset',
						title : '工作情况',
						labelWidth : 80,
						collapsible : true,
						autoHeight : true,
						anchor : '100%',
						items : [{
						columnWidth : .3,
						layout : 'form',
						defaults : {
							anchor : '100%'
						},
						labelWidth : 80,

						items : [{
									xtype : 'textfield',
									fieldLabel : '工作单位',
									allowBlank : this.companyHidden,
									readOnly : this.isRead,
									value : personData == null? null: personData.currentcompany,
									name : 'person.currentcompany'
								}, {
									xtype : 'textfield',
									fieldLabel : '传真',
									value : personData == null? null: personData.companyFax,
									name : 'person.companyFax',
									//regex : /^[0-9]{20}$/,
									//regexText : '传真号码不正确',
									//allowBlank : this.companyHidden,
									readOnly : this.isRead
								}]
					}, {
						columnWidth : .33,
						layout : 'form',
						defaults : {
							anchor : '90%'
						},
						labelWidth : 60,
						items : [{
							xtype : "dickeycombo",
							nodeKey : 'unitproperties',
							hiddenName : "person.unitproperties",
							fieldLabel : "单位性质",
							value : personData == null? null: personData.unitproperties,
							editable : false,
							readOnly : this.isRead,
							// blankText : "单位性质不能为空，请正确填写!",
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
									xtype : 'textfield',
									fieldLabel : '公司地址',
									name : 'person.unitaddress',
									value : personData == null? null: personData.unitaddress,
									allowBlank : this.companyHidden,
									readOnly : this.isRead

								} 
							 * ,{ xtype : "dickeycombo", nodeKey : 'zhiwujob',
							 * fieldLabel : '职务', hiddenName : 'spouse.job', //
							 * emptyText : '请选择职务', width : 80, editable :
							 * false, readOnly : this.isReadOnly, value :
							 * personData == null ? null : personData.job,
							 * listeners : { afterrender : function(combox) {
							 * var st = combox.getStore(); st.on("load",
							 * function() { if (combox.getValue() == 0 ||
							 * combox.getValue() == 1 || combox.getValue() == "" ||
							 * combox.getValue() == null) { combox.setValue(""); }
							 * else { combox.setValue(combox .getValue()); }
							 * combox.clearInvalid(); }) } } }
							 ]
					}, {
						columnWidth : .34,
						layout : 'form',
						defaults : {
							anchor : '100%'
						},
						labelWidth : 60,
						items : [{
									xtype : 'textfield',
									fieldLabel : '单位电话',
									name : 'person.unitphone',
									allowBlank : this.companyHidden,
									value : personData == null? null: personData.unitphone,
									readOnly : this.isRead
								}, {
							xtype : 'textfield',
							fieldLabel : '邮政编码',
							name : 'person.unitpostcode',
							regex : /^[0-9]{6}$/,
							regexText : '邮政编码格式不正确',
							value : personData == null? null: personData.unitpostcode,
							//allowBlank : this.companyHidden,
							readOnly : this.isRead
						}]
					},{
						columnWidth:1,
						layout:'column',
						items:[{
							layout:'form',
							columnWidth:.6,
							labelWidth:80,
							defaults:{
								anchor:'100%'
							},
							scope:this,
							items:[{
								xtype : "combo",
								triggerClass : 'x-form-search-trigger',
								fieldLabel : "行业类别",
								name : 'person.hangyeName',
							    hiddenName : 'person.hangyeName',
							    value : personData == null? null: personData.hangyeName,
								scope : this,
								emptyText : '请选择行业类别',
								readOnly : this.isRead,
								scope:true,
								onTriggerClick : function(e) {
											var obj = this;
											var oobbj=this.nextSibling();
											selectTradeCategory(obj,oobbj);
									}
								},{
									xtype:'hidden',
									value : personData == null? null: personData.hangyeType,
									name:'person.hangyeType'
								}]
						},{
							columnWidth:.4,
							layout : 'form',
							defaults : {
								labelWidth : 60
								
							},
							items:[{
								xtype : "dickeycombo",
								nodeKey : 'companyScale',
								hiddenName : 'person.companyScale',
								fieldLabel : "公司规模",
								editable : true,
								readOnly : this.isRead,
								value : personData == null
										? null
										: personData.companyScale,
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
								
							}]
						}]
					}, {
						
						columnWidth : 1,
						layout : 'column',
						items : [{
							columnWidth : .3,
							layout : 'form',
							labelWidth : 80,
							defaults : {
								anchor : '100%'
							},
							scope : this,
							items : [{
								xtype : "dickeycombo",
								nodeKey : 'zhiwujob',
								fieldLabel : '职务',
								hiddenName : 'person.job',
								allowBlank : this.companyHidden,
								// emptyText : '请选择职务',
								//width : 80,
								editable : false,
								readOnly : this.isRead,
								value : personData == null
										? null
										: personData.job,
								listeners : {
									afterrender : function(combox) {
										var st = combox.getStore();
										st.on("load", function() {
											if (combox.getValue() == 0
													|| combox.getValue() == 1
													|| combox.getValue() == ""
													|| combox.getValue() == null) {
												combox.setValue("");
											} else {
												combox.setValue(combox
														.getValue());
											}
											combox.clearInvalid();
										})
									}
								}

							}]

						}, {
							columnWidth : .33,
							layout : 'form',
							defaults : {
								anchor : '90%'
							},
							labelWidth : 60,
							scope : this,
							items : [{
										xtype : 'datefield',
										format : 'Y-m-d',
										fieldLabel : '入职时间',
										allowBlank : this.companyHidden,
										readOnly : this.isRead,
										 value : personData == null? null: personData.jobstarttime,
										name : 'person.jobstarttime'
									}]
						},{
							columnWidth : .34,
							layout : 'form',
							defaults : {
								anchor : '100%'
							},
							labelWidth : 60,
							scope : this,
							items : [{
								xtype : "textfield",
								//nodeKey : 'bm',
							//	hiddenName : "person.companyDepartment",
							//	itemName : '所属部门', // xx代表分类名称
								fieldLabel : "所属部门",
								name:'person.department',
								 value : personData == null? null: personData.department,
								//allowBlank : this.companyHidden,
								readOnly : this.isRead,
								editable : false
							}]
							
						},{
					columnWidth : 1,
						layout : 'column',
						items : [{
							columnWidth : .3,
							layout : 'form',
							labelWidth : 80,
							defaults : {
								anchor : '100%'
							},
							scope : this,
							items : [{
										xtype : 'numberfield',
										fieldLabel : '月收入',
								        blankText : "月收入不能为空，请正确填写!",
										readOnly : this.isRead,
										value : personData == null? null: personData.jobincome,
										name : 'person.jobincome'
									}]

						},{
							columnWidth : .33,
							layout : 'form',
							defaults : {
								anchor : '90%'
							},
							labelWidth : 60,
							scope : this,
							items : [{
//										xtype : 'datefield',
//										format : 'Y-m-d',
										fieldLabel : '发薪时间',
//										name : 'person.jobstarttime'
										readOnly : this.isRead,
										xtype : 'combo',
										mode : 'local',
										displayField : 'name',
										valueField : 'id',
										editable : false,
										value : personData == null? null: personData.payDate,
										store : new Ext.data.SimpleStore({
											fields : ["name", "id"],
											data : obstorepayintentPeriod
										}),
										triggerAction : "all",
										hiddenName : "person.payDate"
									}]
						},{
							columnWidth : .34,
							layout : 'form',
							labelWidth : 60,
							defaults : {
								anchor : '100%'
							},
							scope : this,
							items : [{
//								xtype : 'textfield',
								xtype:'combo',
								fieldLabel : '发薪形式',
								readOnly : this.isRead,
								name : 'person.wagebank',
								mode : 'local',
							    displayField : 'name',
							    valueField : 'value',
							    width : 70,
							    value : personData == null? null: personData.wagebank,
							    store :new Ext.data.SimpleStore({
										fields : ["name", "value"],
										data : [["打卡", "打卡"],["现金", "现金"]]
								}),
								triggerAction : "all"
							}]
						}]
					},{
							columnWidth : .3,
							layout : 'form',
							labelWidth : 80,
							items : [{
								xtype : "combo",
								anchor : "100%",
								hiddenName : "parentHireCity",
								displayField : 'itemName',
								valueField : 'itemId',
								triggerAction : 'all',
								readOnly : this.isRead,
								store : new Ext.data.SimpleStore({
									autoLoad : true,
									url : __ctxPath
											+ '/creditFlow/multiLevelDic/listByParentIdAreaDic.do',
									fields : ['itemId', 'itemName'],
									baseParams:{parentId:6591}
								}),
								value : personData==null?null:personData.parentHireCity,
								fieldLabel : "工作城市:省",
								listeners : {
									scope : this,
									afterrender : function(combox) {
										var st = combox.getStore();
										st.on("load", function() {
													combox.setValue(combox.getValue());
												})
										combox.clearInvalid();
									},
									select : function(combox, record, index) {
										var v = record.data.itemId;
									var arrStore = new Ext.data.SimpleStore({
										url : __ctxPath
												+ '/creditFlow/multiLevelDic/listByParentIdAreaDic.do',
										fields : ['itemId', 'itemName'],
										baseParams:{parentId:v}
									})
									var opr_obj = this.getCmpByName('person.hireCity')
									opr_obj.clearValue();
									opr_obj.store = arrStore;
									arrStore.load({
												"callback" : test
											});
									function test(r) {
										if (opr_obj.view) { // 刷新视图,避免视图值与实际值不相符
											opr_obj.view.setStore(arrStore);
										}
										
									}
									}
								}
							}]
						},{
							columnWidth : .33,
							layout : 'form',
							labelWidth :60,
							items : [{
										xtype : "combo",
										anchor : "90%",
										hiddenName : "person.hireCity",
										displayField : 'itemName',
										valueField : 'itemId',
										triggerAction : 'all',
										readOnly : this.isRead,
										store : new Ext.data.SimpleStore({
											url : __ctxPath
													+ '/creditFlow/multiLevelDic/listByParentIdAreaDic.do',
											fields : ['itemId', 'itemName']
										}),
										fieldLabel : "市",
										listeners : {
											scope : this,
											afterrender : function(opr_obj) {
												var v=(personData==null?null:personData.parentHireCity)
													var arrStore = new Ext.data.SimpleStore({
														url : __ctxPath
																+ '/creditFlow/multiLevelDic/listByParentIdAreaDic.do',
														fields : ['itemId', 'itemName'],
														baseParams:{parentId:v}
													})
													opr_obj.clearValue();
													opr_obj.store = arrStore;
													arrStore.load({
																"callback" : test
															});
													function test(r) {
														if (opr_obj.view) { // 刷新视图,避免视图值与实际值不相符
															opr_obj.view.setStore(arrStore);
														}
														opr_obj.setValue(personData==null?null:personData.hireCity)
													}
											}
										}
							}]
						},{
							columnWidth : .34,
							layout : 'form',
							labelWidth : 60,
							defaults : {
								anchor : '100%'
							},
							items : [{
								xtype : 'textfield',
								fieldLabel : '工作邮箱',
								readOnly : this.isRead,
								value :personData==null?null:personData.hireEmail,
								regex : /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+/,
									regexText : '电子邮箱格式不正确',
								name : 'person.hireEmail',
								anchor :'100%'
							}]
						},{
							columnWidth : .33,
							layout : 'form',
							defaults : {
								anchor : '90%'
							},
							labelWidth : 80,
							scope : this,
							items : [{
										xtype : 'textfield',
										//format : 'Y-m-d',
										fieldLabel : '网店经营年限',
										allowBlank : this.companyHidden,
										readOnly : this.isRead,
										 value : personData == null? null: personData.webstarttime,
										name : 'person.webstarttime'
									}]
						},{
							columnWidth : .33,
							layout : 'form',
							defaults : {
								anchor : '80%'
							},
							labelWidth : 50,
							scope : this,
							items : [{
										xtype : 'datefield',
										format : 'Y-m-d',
										fieldLabel : '执教起始时间',
										allowBlank : this.companyHidden,
										readOnly : this.isRead,
										 value : personData == null? null: personData.techstarttime,
										name : 'person.techstarttime'
									}]
						},{
							columnWidth : .34,
							layout : 'form',
							defaults : {
								anchor : '90%'
							},
							labelWidth : 50,
							scope : this,
							items : [{
										xtype : 'datefield',
										format : 'Y-m-d',
										fieldLabel : '经营起始时间',
										allowBlank : this.companyHidden,
										readOnly : this.isRead,
										 value : personData == null? null: personData.bossstarttime,
										name : 'person.bossstarttime'
									}]
						}]
					}]	
					}*//*{
						layout : 'column',
						xtype : 'fieldset',
						title : '教师工作信息',
						labelWidth : 80,
						collapsible : true,
						autoHeight : true,
						anchor : '100%',
						items : [{
						columnWidth : .3,
						layout : 'form',
						defaults : {
							anchor : '100%'
						},
						labelWidth : 80,

						items : [{
									xtype : 'textfield',
									fieldLabel : '工作单位',
									allowBlank : this.companyHidden,
									readOnly : this.isRead,
									value : personData == null? null: personData.teacherCompanyName,
									name : 'person.teacherCompanyName'
								}, {
									xtype : 'textfield',
									fieldLabel : '传真',
									value : personData == null? null: personData.companyFax,
									name : 'person.companyFax',
									//regex : /^[0-9]{20}$/,
									//regexText : '传真号码不正确',
									//allowBlank : this.companyHidden,
									readOnly : this.isRead
								},{
								xtype : "dickeycombo",
								nodeKey : 'zhiwujob',
								fieldLabel : '职务',
								hiddenName : 'person.teacherPosition',
								allowBlank : this.companyHidden,
								// emptyText : '请选择职务',
								//width : 80,
								editable : false,
								readOnly : this.isRead,
								value : personData == null
										? null
										: personData.teacherPosition,
								listeners : {
									afterrender : function(combox) {
										var st = combox.getStore();
										st.on("load", function() {
											if (combox.getValue() == 0
													|| combox.getValue() == 1
													|| combox.getValue() == ""
													|| combox.getValue() == null) {
												combox.setValue("");
											} else {
												combox.setValue(combox
														.getValue());
											}
											combox.clearInvalid();
										})
									}
								}

							}]
					}, {
						columnWidth : .33,
						layout : 'form',
						defaults : {
							anchor : '90%'
						},
						labelWidth : 60,
						items : [{
							xtype : "dickeycombo",
							nodeKey : 'unitproperties',
							hiddenName : "person.unitproperties",
							fieldLabel : "单位性质",
							value : personData == null? null: personData.unitproperties,
							editable : false,
							readOnly : this.isRead,
							// blankText : "单位性质不能为空，请正确填写!",
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
									xtype : 'textfield',
									fieldLabel : '公司地址',
									name : 'person.teacherAddress',
									value : personData == null? null: personData.teacherAddress,
									allowBlank : this.companyHidden,
									readOnly : this.isRead

								},{
										xtype : 'datefield',
										format : 'Y-m-d',
										fieldLabel : '执教时间',
										allowBlank : this.companyHidden,
										readOnly : this.isRead,
										 value : personData == null? null: personData.teacherStartYear,
										name : 'person.teacherStartYear'
									} 
							 * ,{ xtype : "dickeycombo", nodeKey : 'zhiwujob',
							 * fieldLabel : '职务', hiddenName : 'spouse.job', //
							 * emptyText : '请选择职务', width : 80, editable :
							 * false, readOnly : this.isReadOnly, value :
							 * personData == null ? null : personData.job,
							 * listeners : { afterrender : function(combox) {
							 * var st = combox.getStore(); st.on("load",
							 * function() { if (combox.getValue() == 0 ||
							 * combox.getValue() == 1 || combox.getValue() == "" ||
							 * combox.getValue() == null) { combox.setValue(""); }
							 * else { combox.setValue(combox .getValue()); }
							 * combox.clearInvalid(); }) } } }
							 ]
					}, {
						columnWidth : .34,
						layout : 'form',
						defaults : {
							anchor : '100%'
						},
						labelWidth : 60,
						items : [{
									xtype : 'textfield',
									fieldLabel : '单位电话',
									name : 'person.teacherCompanyPhone',
									allowBlank : this.companyHidden,
									value : personData == null? null: personData.teacherCompanyPhone,
									readOnly : this.isRead
								}, {
							xtype : 'textfield',
							fieldLabel : '邮政编码',
							name : 'person.unitpostcode',
							regex : /^[0-9]{6}$/,
							regexText : '邮政编码格式不正确',
							value : personData == null? null: personData.unitpostcode,
							//allowBlank : this.companyHidden,
							readOnly : this.isRead
						}]
					},{
						columnWidth:1,
						layout:'column',
						items:[{
							layout:'form',
							columnWidth:.6,
							labelWidth:80,
							defaults:{
								anchor:'100%'
							},
							scope:this,
							items:[{
								xtype : "combo",
								triggerClass : 'x-form-search-trigger',
								fieldLabel : "行业类别",
								name : 'person.hangyeName',
							    hiddenName : 'person.hangyeName',
							    value : personData == null? null: '教育',
								scope : this,
								allowBlank : false,
								emptyText : '请选择行业类别',
								readOnly : true,
								scope:true,
								onTriggerClick : function(e) {
											var obj = this;
											var oobbj=this.nextSibling();
											selectTradeCategory(obj,oobbj);
									}
								},{
									xtype:'hidden',
									value : personData == null? null: personData.hangyeType,
									name:'person.hangyeType'
								}]
						},{
							columnWidth:.4,
							layout : 'form',
							defaults : {
								labelWidth : 60
								
							},
							items:[{
								xtype : "dickeycombo",
								nodeKey : 'companyScale',
								hiddenName : 'person.companyScale',
								fieldLabel : "公司规模",
								editable : true,
								readOnly : this.isRead,
								value : personData == null
										? null
										: personData.companyScale,
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
								
							}]
						}]
					}, {
						
						columnWidth : 1,
						layout : 'column',
						items : [{
							columnWidth : .3,
							layout : 'form',
							labelWidth : 80,
							defaults : {
								anchor : '100%'
							},
							scope : this,
							items : []

						}, {
							columnWidth : .33,
							layout : 'form',
							defaults : {
								anchor : '90%'
							},
							labelWidth : 60,
							scope : this,
							items : []
						},{
							columnWidth : .34,
							layout : 'form',
							defaults : {
								anchor : '100%'
							},
							labelWidth : 60,
							scope : this,
							items : [{
								xtype : "textfield",
								//nodeKey : 'bm',
							//	hiddenName : "person.companyDepartment",
							//	itemName : '所属部门', // xx代表分类名称
								fieldLabel : "所属部门",
								name:'person.department',
								 value : personData == null? null: personData.department,
								//allowBlank : this.companyHidden,
								readOnly : this.isRead,
								editable : false
							}]
							
						},{
					columnWidth : 1,
						layout : 'column',
						items : [{
							columnWidth : .3,
							layout : 'form',
							labelWidth : 80,
							defaults : {
								anchor : '100%'
							},
							scope : this,
							items : [{
										xtype : 'numberfield',
										fieldLabel : '月收入',
										allowBlank : true,
								        blankText : "月收入不能为空，请正确填写!",
										readOnly : this.isRead,
										value : personData == null? null: personData.teacherMonthlyIncome,
										name : 'person.teacherMonthlyIncome'
									}]

						},{
							columnWidth : .33,
							layout : 'form',
							defaults : {
								anchor : '90%'
							},
							labelWidth : 60,
							scope : this,
							items : [{
//										xtype : 'datefield',
//										format : 'Y-m-d',
										fieldLabel : '发薪时间',
//										name : 'person.jobstarttime'
										readOnly : this.isRead,
										xtype : 'combo',
										mode : 'local',
										displayField : 'name',
										valueField : 'id',
										editable : false,
										value : personData == null? null: personData.payDate,
										store : new Ext.data.SimpleStore({
											fields : ["name", "id"],
											data : obstorepayintentPeriod
										}),
										triggerAction : "all",
										hiddenName : "person.payDate"
									}]
						},{
							columnWidth : .34,
							layout : 'form',
							labelWidth : 60,
							defaults : {
								anchor : '100%'
							},
							scope : this,
							items : [{
//								xtype : 'textfield',
								xtype:'combo',
								fieldLabel : '发薪形式',
								readOnly : this.isRead,
								name : 'person.wagebank',
								mode : 'local',
							    displayField : 'name',
							    valueField : 'value',
							    width : 70,
							    value : personData == null? null: personData.wagebank,
							    store :new Ext.data.SimpleStore({
										fields : ["name", "value"],
										data : [["打卡", "打卡"],["现金", "现金"]]
								}),
								triggerAction : "all"
							}]
						}]
					},{
							columnWidth : .3,
							layout : 'form',
							labelWidth : 80,
							items : [{
								xtype : "combo",
								anchor : "100%",
								hiddenName : "parentHireCity",
								displayField : 'itemName',
								valueField : 'itemId',
								triggerAction : 'all',
								readOnly : this.isRead,
								store : new Ext.data.SimpleStore({
									autoLoad : true,
									url : __ctxPath
											+ '/creditFlow/multiLevelDic/listByParentIdAreaDic.do',
									fields : ['itemId', 'itemName'],
									baseParams:{parentId:6591}
								}),
								value : personData==null?null:personData.parentHireCity,
								fieldLabel : "工作城市:省",
								listeners : {
									scope : this,
									afterrender : function(combox) {
										var st = combox.getStore();
										st.on("load", function() {
													combox.setValue(combox.getValue());
												})
										combox.clearInvalid();
									},
									select : function(combox, record, index) {
										var v = record.data.itemId;
									var arrStore = new Ext.data.SimpleStore({
										url : __ctxPath
												+ '/creditFlow/multiLevelDic/listByParentIdAreaDic.do',
										fields : ['itemId', 'itemName'],
										baseParams:{parentId:v}
									})
									var opr_obj = this.getCmpByName('person.teacherCity')
									opr_obj.clearValue();
									opr_obj.store = arrStore;
									arrStore.load({
												"callback" : test
											});
									function test(r) {
										if (opr_obj.view) { // 刷新视图,避免视图值与实际值不相符
											opr_obj.view.setStore(arrStore);
										}
										
									}
									}
								}
							}]
						},{
							columnWidth : .33,
							layout : 'form',
							labelWidth :60,
							items : [{
										xtype : "combo",
										anchor : "90%",
										hiddenName : "person.teacherCity",
										displayField : 'itemName',
										valueField : 'itemId',
										triggerAction : 'all',
										readOnly : this.isRead,
										store : new Ext.data.SimpleStore({
											url : __ctxPath
													+ '/creditFlow/multiLevelDic/listByParentIdAreaDic.do',
											fields : ['itemId', 'itemName']
										}),
										fieldLabel : "市",
										listeners : {
											scope : this,
											afterrender : function(opr_obj) {
												var v=(personData==null?null:personData.parentHireCity)
													var arrStore = new Ext.data.SimpleStore({
														url : __ctxPath
																+ '/creditFlow/multiLevelDic/listByParentIdAreaDic.do',
														fields : ['itemId', 'itemName'],
														baseParams:{parentId:v}
													})
													opr_obj.clearValue();
													opr_obj.store = arrStore;
													arrStore.load({
																"callback" : test
															});
													function test(r) {
														if (opr_obj.view) { // 刷新视图,避免视图值与实际值不相符
															opr_obj.view.setStore(arrStore);
														}
														opr_obj.setValue(personData==null?null:personData.teacherCity)
													}
											}
										}
							}]
						},{
							columnWidth : .34,
							layout : 'form',
							labelWidth : 60,
							defaults : {
								anchor : '100%'
							},
							items : [{
								xtype : 'textfield',
								fieldLabel : '工作邮箱',
								readOnly : this.isRead,
								value :personData==null?null:personData.teacherEmail,
								regex : /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+/,
									regexText : '电子邮箱格式不正确',
								name : 'person.teacherEmail',
								anchor :'100%'
							}]
						}]
					}]	
					
					}]
					},{
						layout : 'column',
						xtype : 'fieldset',
						title : '填写个人教育/工作信息',
						labelWidth : 80,
						collapsible : true,
						autoHeight : true,
						anchor : '100%',
						items : [{
							columnWidth : .33,
							layout : 'form',
							labelWidth : 65,
							defaults : {
								anchor : '100%'
							},
							scope : this,
							items : [{
								xtype : "dickeycombo",
								nodeKey : 'dgree',
								fieldLabel : '学历',
								hiddenName : 'person.dgree',
								emptyText : '请选择学历',
								width : 80,
								editable : false,
								value : personData == null
										? null
										: personData.dgree,
								readOnly : this.isRead,
								listeners : {
									afterrender : function(combox) {
										var st = combox.getStore();
										st.on("load", function() {
											if (combox.getValue() == 0
													|| combox.getValue() == 1
													|| combox.getValue() == ""
													|| combox.getValue() == null) {
												combox.setValue("");
											} else {
												combox.setValue(combox
														.getValue());
											}
											combox.clearInvalid();
										})
									}
								}
							}]
						},{
							columnWidth : .33,
							layout : 'form',
							labelWidth : 65,
							defaults : {
								anchor : '100%'
							},
							scope : this,
							items : [{
								xtype : 'textfield',
								fieldLabel : '毕业院校',
								name : 'person.graduationunversity',
								readOnly : this.isRead,
								value : personData == null
										? null
										: personData.graduationunversity
								//regex : /[^0-9]/, regexText : '输入错误'

							}]
						},{	
							columnWidth : .33,
							layout : 'form',
							labelWidth : 65,
							defaults : {
								anchor : '100%'
							},
							scope : this,
							items : [{
								xtype : "dickeycombo",
								hiddenName : "person.politicalStatus",
								nodeKey : 'zzmm', // xx代表分类名称
								fieldLabel : "政治面貌",
								emptyText : "请选择",
								readOnly : this.isRead,
								value:personData==null?null:personData.politicalStatus,
								listeners : {
									afterrender : function(combox) {
										var st = combox.getStore();
										st.on("load", function() {
											
											combox.setValue(combox.getValue());
											combox.clearInvalid();
										})
							       }
								}
							}]
						
						}, {
							columnWidth : 1,
							labelWidth : 65,
							layout : 'column',
							items : [{
								columnWidth : 0.33,
								layout : 'form',
								defaults : {
									anchor : '100%'
								},
								scope : this,
								items : [{
									xtype : "dickeycombo",
									nodeKey : 'degreewei',
									fieldLabel : '学位',
									hiddenName : 'person.degreewei',
									emptyText : '请选择学位',
									width : 80,
									editable : false,
									readOnly : this.isRead,
									value : personData == null
											? null
											: personData.degreewei,
									listeners : {
										afterrender : function(combox) {
											var st = combox.getStore();
											st.on("load", function() {
												if (combox.getValue() == 0
														|| combox.getValue() == 1
														|| combox.getValue() == ""
														|| combox.getValue() == null) {
													combox.setValue("");
												} else {
													combox.setValue(combox
															.getValue());
												}
												combox.clearInvalid();
											})
										}
									}
								}]
							}, {
								columnWidth : 0.33,
								layout : 'form',
								defaults : {
									anchor : '99.5%'
								},
								scope : this,
								items : [{
									xtype : "dickeycombo",
									nodeKey : 'unitproperties',
									fieldLabel : '单位性质',
									hiddenName : 'person.unitproperties',
									emptyText : '请选择单位性质',
									width : 80,
									editable : false,
									readOnly : this.isRead,
									value : personData == null
											? null
											: personData.unitproperties,
									listeners : {
										afterrender : function(combox) {
											var st = combox.getStore();
											st.on("load", function() {
												if (combox.getValue() == 0
														|| combox.getValue() == 1
														|| combox.getValue() == ""
														|| combox.getValue() == null) {
													combox.setValue("");
												} else {
													combox.setValue(combox
															.getValue());
												}
												combox.clearInvalid();
											})
										}
									}

								}]
							}, {
								columnWidth : 0.33,
								layout : 'form',
								labelWidth : 70,
								defaults : {
									anchor : '100%'
								},
								items : [{
									xtype : 'radiogroup',
									fieldLabel : '是否公务员',
									items : [{
										boxLabel : '是',
										name : 'person.ispublicservant',
										inputValue : true,
										checked : personData == null
												? null
												: personData.ispublicservant
									}, {
										boxLabel : '否',
										name : 'person.ispublicservant',
										inputValue : false,
										checked : !(personData == null
												? null
												: personData.ispublicservant)
									}]

								}]
							}]
						}, {
							columnWidth : .33,
							layout : 'form',
							labelWidth : 65,
							defaults : {
								anchor : '100%'
							},
							scope : this,
							items : [{
								xtype : "dickeycombo",
								nodeKey : 'zhiwujob',
								fieldLabel : '职务',
								hiddenName : 'person.job',
								emptyText : '请选择职务',
								width : 80,
								editable : false,
								readOnly : this.isRead,
								value : personData == null
										? null
										: personData.job,
								listeners : {
									afterrender : function(combox) {
										var st = combox.getStore();
										st.on("load", function() {
											if (combox.getValue() == 0
													|| combox.getValue() == 1
													|| combox.getValue() == ""
													|| combox.getValue() == null) {
												combox.setValue("");
											} else {
												combox.setValue(combox
														.getValue());
											}
											combox.clearInvalid();
										})
									}
								}
							}]
						}, {
							columnWidth : .66,
							layout : 'form',
							labelWidth : 65,
							defaults : {
								anchor : '100%'
							},
							scope : this,
							items : [{
								xtype : 'textfield',
								fieldLabel : '工作单位',
								name : 'person.currentcompany',
								//allowBlank:false,
								readOnly : this.isRead,
								value : personData == null
										? null
										: personData.currentcompany
							}]
						}, {
							columnWidth : .33,
							layout : 'form',
							labelWidth : 65,
							defaults : {
								anchor : '100%'
							},
							scope : this,
							items : [{
								xtype : "dickeycombo",
								nodeKey : 'techpersonnel',
								fieldLabel : '技术职称',
								hiddenName : 'person.techpersonnel',
								emptyText : '请选择技术职称',
								width : 80,
								editable : false,
								readOnly : this.isRead,
								value : personData == null
										? null
										: personData.techpersonnel,
								listeners : {
									afterrender : function(combox) {
										var st = combox.getStore();
										st.on("load", function() {
											if (combox.getValue() == 0
													|| combox.getValue() == 1
													|| combox.getValue() == ""
													|| combox.getValue() == null) {
												combox.setValue("");
											} else {
												combox.setValue(combox
														.getValue());
											}
											combox.clearInvalid();
										})
									}
								}

							}]
						}, {
							columnWidth : .6,
							layout : 'form',
							labelWidth : 150,
							defaults : {
								anchor : '100%'
							},
							scope : this,
							items : [{
								xtype : 'numberfield',
								fieldLabel : '工作单位注册资本',
								name : 'person.registeredcapital',
								readOnly : this.isRead,
								value : personData == null
										? null
										: personData.registeredcapital
							}]
						},{								
							columnWidth : .06, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							defaults : {
								anchor : anchor
							},
							labelWidth : 30,
							items : [{
								fieldLabel : "万元 ",
								labelSeparator : ''
							}]								
						}, {
							columnWidth : .40,
							layout : 'form',
							labelWidth : 95,
							defaults : {
								anchor : '100%'
							},
							scope : this,
							items : [{
								xtype : 'datefield',
								fieldLabel : '本工作开始日期',
								name : 'person.jobstarttime',
								format : 'Y-m-d',
								//regex : /^(\d{4})\-(\d{2})\-(\d{2})$/,
								//regexText : '日期格式不正确',
								readOnly : this.isRead,
								value : personData == null
										? null
										: personData.jobstarttime
							}]
						}, {
							columnWidth : .53,
							layout : 'form',
							labelWidth : 145,
							defaults : {
								anchor : '100%'
							},
							scope : this,
							items : [{
								xtype : 'numberfield',
								fieldLabel : '本工作税后月收入',
								name : 'person.jobincome',
								readOnly : this.isRead,
								value : personData == null
										? null
										: personData.jobincome
							}]
						},{								
							columnWidth : .07, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							defaults : {
								anchor : anchor
							},
							labelWidth : 30,
							items : [{
								fieldLabel : "万元 ",
								labelSeparator : ''
							}]								
						}, {
							columnWidth : .66,
							layout : 'form',
							labelWidth : 65,
							defaults : {
								anchor : '100%'
							},
							scope : this,
							items : [{
								xtype : 'textfield',
								fieldLabel : '单位地址',
								name : 'person.unitaddress',
								//allowBlank:false,
								readOnly : this.isRead,
								value : personData == null
										? null
										: personData.unitaddress
							}]
						}, {
							columnWidth : .33,
							layout : 'form',
							labelWidth : 65,
							defaults : {
								anchor : '100%'
							},
							scope : this,
							items : [{
								xtype : 'numberfield',
								fieldLabel : '邮政编码',
								name : 'person.unitpostcode',
								regex : /^[0-9]{6}$/,
								regexText : '邮政编码格式不正确',
								readOnly : this.isRead,
								value : personData == null? null: personData.unitpostcode,
							    listeners : {
											'afterrender':function(com){
											    com.clearInvalid();
											}
										}	
							}]
						}]
					},*/ /* {
						layout : 'column',
						xtype : 'fieldset',
						title : '个人家庭信息',
						labelWidth : 80,
						collapsible : true,
						autoHeight : true,
						anchor : '100%',
						items : []
					}*/]

				}, {
					columnWidth : .2,
					defaults : {
						xtype : 'button',
						style : 'margin:5px 0px 0px 5px;',
						iconCls : 'seeIcon',
						iconAlign : 'left'
					},
					items : [{
								text : '工作情况',
								tooltip : '工作情况',
								scope : this,
								handler : function() {
									var flag=panel_add.isflag;
									var read=panel_add.isRead;
									var cardNum = this.getCmpByName('person.cardnumber').getValue();
									var personId=this.getCmpByName('person.id').getValue()
									if (null!=personId && personId!="" ) 
									{
										if(read){
											
										    new addPersonWorkInfo({personId:personId,isLook:true,isReadOnly:true
										    }).show();
										    return false;
										}else{
											new addPersonWorkInfo({personId:personId
										    }).show();
											return false;
										}
										 
									} else {
										Ext.ux.Toast.msg("友情提示", "先保存个人信息");
									}
								}
							},{
								text : '个人家庭信息',
								tooltip : '个人家庭信息',
								scope : this,
								handler : function() {
									var flag=panel_add.isflag;
									var read=panel_add.isRead;
									var cardNum = this.getCmpByName('person.cardnumber').getValue();
									var personId=this.getCmpByName('person.id').getValue()
									if (null!=personId && personId!="" ) 
									{
										if(read){
											
										    new addFamilyInfo({personId:personId,isLook:true,isReadOnly:true
										    }).show();
										    return false;
										}else{
											new addFamilyInfo({personId:personId
										    }).show();
											return false;
										}
										 
									} else {
										Ext.ux.Toast.msg("友情提示", "先保存个人信息");
									}
								}
							},{
								text : '家庭经济情况',
								tooltip : '家庭经济情况信息',
								scope : this,
								handler : function() {
									var flag=panel_add.isflag;
									var read=panel_add.isRead;
									var cardNum = this.getCmpByName('person.cardnumber').getValue();
									var personId=this.getCmpByName('person.id').getValue()
									if (null!=personId && personId!="" ) 
									{
										if(read){
											
										    new addPersonFinanceInfo({personId:personId,isLook:true
										    }).show();
										    return false;
										}else{
											new addPersonFinanceInfo({personId:personId
										    }).show();
											return false;
										}
										 
									} else {
										Ext.ux.Toast.msg("友情提示", "先保存个人信息");
									}
								}
							}, {
								text : '旗下公司',
								tooltip : '旗下公司信息',
								scope : this,
								handler : function() {
									var flag=panel_add.isflag;
									var read=panel_add.isRead;
									var personId=this.getCmpByName('person.id').getValue()
									if (null==personId || personId=="") 
									{
										Ext.ux.Toast.msg("友情提示", "先保存个人信息");
										return;
									} else
										/*var cardNum = Ext.getCmp('cardnumber').getValue();
									    Ext.Ajax.request({
										url : __ctxPath	+ '/credit/customer/person/queryPersonByNameMessage.do',
										method : 'post',
										params : {
											perosnName : cardNum
										},
										success : function(response, option) {
											var obj = Ext.decode(response.responseText);
											var personIdValue = obj.data.id;*/
											new ThereunderView({personId :personId,isReadOnly:read}).show()
									/*				
										}
									});*/
									/*var flag=panel_add.isflag;
									var read=panel_add.isRead;
									if(flag){
									    var cardNum = Ext.getCmp('cardnumber').getValue();
									    thereunderListWin(cardNum,read);
									}else{
									   Ext.ux.Toast.msg("友情提示", "先保存个人信息");
									}*/
								}
							}, {
								text : '征信记录',
								tooltip : '征信记录信息',
								scope : this,
								handler : function() {
									var flag=panel_add.isflag;
									var read=panel_add.isRead;
									var personId=this.getCmpByName('person.id').getValue()
									if (null==personId || personId=="")
									{
										 Ext.ux.Toast.msg("友情提示", "先保存个人信息");
										 return;
									} else{
									    var cardNum = this.getCmpByName('person.cardnumber').getValue();
									    reditregistriesListWin(personId,read);
									}
								}
							}, /**{
								text : '与本担保公司的业务记录',
								tooltip : '与本担保公司的业务记录信息',
								scope : this,
								handler : function() {
									var flag=panel_add.isflag;
									var read=panel_add.isRead;
									
									if (!flag)
									{
										 Ext.ux.Toast.msg("友情提示", "先保存个人信息");
										 return false;
									} else{
										 var cardNum = Ext.getCmp('cardnumber').getValue();
									     businessRecordListWin(cardNum)
									}
								
								}
							}, **/{
								text : '关系人(非配偶)信息',
								tooltip : '关系人信息',
								scope : this,
								handler : function() {
									var flag=panel_add.isflag;
									var read=panel_add.isRead;
									var personId=this.getCmpByName('person.id').getValue()
									if (null==personId || personId==""){
										Ext.ux.Toast.msg("友情提示", "先保存个人信息");
										return false;
									} else
										var cardNum = this.getCmpByName('person.cardnumber').getValue();
									    selectRelationPersonList(personId,read);
								
								}
							}, {

								text : '配偶信息',
								tooltip : '配偶信息',
								scope : this,
								handler : function() {
									var flag=panel_add.isflag;
									var read=panel_add.isRead;
									var personId=this.getCmpByName('person.id').getValue()
									if (null==personId || personId=="") 
									{
										Ext.ux.Toast.msg("友情提示", "先保存个人信息");
										return;
									} else
										/*var cardNum = Ext.getCmp('cardnumber').getValue();
									    Ext.Ajax.request({
										url : __ctxPath	+ '/credit/customer/person/queryPersonByNameMessage.do',
										method : 'post',
										params : {
											perosnName : cardNum
										},
										success : function(response, option) {
											var obj = Ext.decode(response.responseText);
											var personIdValue = obj.data.id;*/
											new SpouseWin({personId :personId,isReadOnly:read}).show()
									/*				
										}
									});*/
								}
							}, {

								text : '资信评估',
								tooltip : '资信评估',
								scope : this,
								handler : function() {
									var flag=panel_add.isflag;
									var read=panel_add.isRead;
									var personId=this.getCmpByName('person.id').getValue();
									if (null==personId || personId=="")
									{
										Ext.ux.Toast.msg("友情提示", "先保存个人信息");
										return;
									} else
										/*var cardNum = Ext.getCmp('cardnumber').getValue();
									    Ext.Ajax.request({
										url : __ctxPath	+ '/credit/customer/person/queryPersonByNameMessage.do',
										method : 'post',
										params : {
											perosnName : cardNum
										},
										success : function(response, option) {
											var obj = Ext.decode(response.responseText);
											var personIdValue = obj.data.id;*/
											new EnterpriseEvaluationWin({customerId :personId,customerType : '个人',isReadonly:read}).show()
										/*			
										}
									});*/
								}
							},/*{
								text : '业务往来',
								tooltip : '业务往来',
								scope : this,
								handler : function() {
									var flag=panel_add.isflag;
									var read=panel_add.isRead;
									if (!flag) 
									{
										Ext.ux.Toast.msg("友情提示", "先保存个人信息");
										return;
									} else
										var cardNum = Ext.getCmp('cardnumber').getValue();
									    Ext.Ajax.request({
										url : __ctxPath	+ '/credit/customer/person/queryPersonByNameMessage.do',
										method : 'post',
										params : {
											perosnName : cardNum
										},
										success : function(response, option) {
											var obj = Ext.decode(response.responseText);
											var personIdValue = obj.data.id;
											new PersonAll({customerType:'person_customer',customerId :personIdValue,personType:603,shareequityType:'person_shareequity'}).show()
													
										}
									});
								}
							
							},*/ {
								text : '银行开户',
								tooltip : '银行开户信息',
								scope : this,
								handler : function() {
									var flag=panel_add.isflag;
									var read=panel_add.isRead;
									var personId=this.getCmpByName('person.id').getValue()
									if (null==personId || personId=="")
									{
										Ext.ux.Toast.msg("友情提示", "先保存个人信息");
										return;
									} else
										/*var cardNum = Ext.getCmp('cardnumber').getValue();
									    Ext.Ajax.request({
										url : __ctxPath	+ '/credit/customer/person/queryPersonByNameMessage.do',
										method : 'post',
										params : {
											perosnName : cardNum
										},
										success : function(response, option) {
											var obj = Ext.decode(response.responseText);
											var personIdValue = obj.data.id;*/
										 // bankInfoListPersonWin(personIdValue,read);
											bankInfoWin(personId, read,0,1);		
									/*	}
									});
									*/
									
								}
							},{
								xtype : 'button',
								text:'负面调查',
								tooltip : '负面调查信息',
								scope : this,
								handler : function() {
									var flag=panel_add.isflag;
									var read=panel_add.isRead;
									var personId=this.getCmpByName('person.id').getValue();
									var createrId=this.getCmpByName('person.createrId').getValue();
									var creater=this.getCmpByName('person.creater').getValue();
									if (null==personId || personId=="")
									{
										Ext.ux.Toast.msg("友情提示", "先保存个人信息");
										return;
									} else{
										//负面调查window
										new NegativeInfoWin({personId :personId,createrId:createrId,creater:creater,isRead:read}).show();
									}
								}
							},{
								xtype : 'button',
								text:'教育情况',
								tooltip : '教育情况信息',
								scope : this,
								handler : function() {
									var flag=panel_add.isflag;
									var read=panel_add.isRead;
									var personId=this.getCmpByName('person.id').getValue();
									if (null==personId || personId=="")
									{
										Ext.ux.Toast.msg("友情提示", "先保存个人信息");
										return;
									} else{
										//教育情况window
										new EducationInfoWin({personId :personId,isRead:read}).show();
									}
								}
							},{
								xtype : 'button',
								text:'工作经历',
								tooltip : '工作经历信息',
								scope : this,
								handler : function() {
									var flag=panel_add.isflag;
									var read=panel_add.isRead;
									var personId=this.getCmpByName('person.id').getValue();
									if (null==personId || personId=="")
									{
										Ext.ux.Toast.msg("友情提示", "先保存个人信息");
										return;
									} else{
										//工作经历window
										new WorkExperienceInfoWin({personId :personId,isRead:read}).show();
									}
								}
							},{
								xtype : 'button',
								text:'社会活动',
								tooltip : '社会活动信息',
								scope : this,
								handler : function() {
									var flag=panel_add.isflag;
									var read=panel_add.isRead;
									var personId=this.getCmpByName('person.id').getValue();
									if (null==personId || personId=="")
									{
										Ext.ux.Toast.msg("友情提示", "先保存个人信息");
										return;
									} else{
										//社会活动window
										new PublicActivityInfoWin({personId :personId,isRead:read}).show();
									}
								}
							},{
								text : '业务往来',
								tooltip : '业务往来记录信息',
								scope : this,
								handler : function() {
									var flag=panel_add.isflag;
									var read=panel_add.isRead;
									var personId=this.getCmpByName('person.id').getValue()
									if (!flag)
									{
										 Ext.ux.Toast.msg("友情提示", "先保存个人信息");
										 return false;
									} else{
									     new PersonAll({customerType:'person_customer',customerId :personId,personType:603,shareequityType:'person_shareequity',isReadOnly:read}).show()
									}
								
								}
							},{
								text : '私营业主补填',
								tooltip : '私营业主补填信息',
								scope : this,
								handler : function() {
									var flag=panel_add.isflag;
									var read=panel_add.isRead;
									var personId=this.getCmpByName('person.id').getValue();
									var grossdebt=this.getCmpByName('person.grossdebt').getValue();
									if (!flag){
										 Ext.ux.Toast.msg("友情提示", "先保存个人信息");
										 return false;
									} else{
										new workCompanyWin({
											customerId :personId,
											grossdebt :grossdebt,
											isReadOnly:read,
											isLook:read
										}).show();
									}
								}
							}]
				}]
			}]
		})
	},
	initUIComponents : function() {
	}

})