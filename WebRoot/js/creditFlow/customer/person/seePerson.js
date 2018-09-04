var flag = false;
	var anchor = '96.5%';
	var personId = personData.id;
	var personPhotoId = personData.personPhotoId;
	var personPhotoUrl=personData.personPhotoUrl;
	var personSFZZId = personData.personSFZZId;
	var personSFZZUrl=personData.personSFZZUrl;
	var personSFZFId = personData.personSFZFId;
	var personSFZFUrl=personData.personSFZFUrl;
	var borderLayout = new Ext.form.FormPanel({
		autoScroll : true ,
		width :(screen.width-180)*0.7 + 130,
		renderTo : 'seePersonDiv',
		labelAlign : 'right',
		buttonAlign : 'center',
		frame : true ,
		layout : 'column',
		bodyStyle:'padding:10px',
		items : [{
			columnWidth : 0.83,
			items : [{
			xtype:'fieldset',
            title: '填写个人基本信息',
            collapsible: true,
            autoHeight:true,
            anchor:'100%',
			items :[{
				columnWidth : 1,
				labelWidth : 95,
				layout : 'column',
				items : [{
					columnWidth : .66,
					labelWidth : 95,
					layout : 'column',
					items : [{
						columnWidth : 0.5,
						layout : 'form',
						defaults : {
							anchor : '100%'
						},
						items : [{
									xtype : 'textfield',
									id : 'personName',
									fieldLabel : '<font color=red>*</font>中文姓名',
									//allowBlank : false,
									name : 'person.name',
									blankText : '姓名为必填内容',
									regex : /^[\u4e00-\u9fa5]+$/,
									regexText : "必须输入汉字",
									cls : 'readOnlyClass',
									readOnly : true,
									value : personData.name
								}, {
									id : 'personsex',
									xtype : "dickeycombo",
									nodeKey : 'sex_key',
									hiddenName : 'person.sex',
									fieldLabel : "性别",
									// itemName : '性别', // xx代表分类名称
									allowBlank : false,
									// emptyText : "请选择",
									editable : true,
									blankText : "性别不能为空，请正确填写!",
									cls : 'readOnlyClass',
									readOnly : true,
									value : personData.sex,
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
										},
										select : function(combo, record, index) {
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
								}, {
									xtype : "dickeycombo",
									nodeKey : '8',
									hiddenName : 'person.marry',
									fieldLabel : "婚姻状况",
									itemName : '婚姻状况', // xx代表分类名称
									// allowBlank : false,
									editable : true,
									blankText : "婚姻状况不能为空，请正确填写!",
									cls : 'readOnlyClass',
									readOnly : true,
									value : personData.marry,
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
								}, {
									xtype : 'datefield',
									fieldLabel : '出生日期',
									name : 'person.birthday',
									format : 'Y-m-d',
									cls : 'readOnlyClass',
									readOnly : true,
									value : personData.birthday
								}, {
									xtype : "dickeycombo",
									nodeKey : 'card_type_key',
									hiddenName : "person.cardtype",
									itemName : '证件类型', // xx代表分类名称
									fieldLabel : "证件类型",
									//allowBlank : false,
									editable : true,
									value : personData.cardtype,
									cls : 'readOnlyClass',
									readOnly : true,
									// emptyText : "请选择",
									blankText : "证件类型不能为空，请正确填写!",
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
								}, {
									xtype : 'numberfield',
									fieldLabel : '手机号码',
									name : 'person.cellphone',
									regex : /^1[358]\d{9}$/,
									cls : 'readOnlyClass',
									readOnly : true,
									value : personData.cellphone,
									regexText : '手机号码格式不正确或者无效的号码'
								}, {
									xtype : 'numberfield',
									fieldLabel : '传真号码',
									name : 'person.fax',
									cls : 'readOnlyClass',
									readOnly : true,
									regex : /^[+]{0,1}(\d){1,3}[ ]?([-]?((\d)|[ ]){1,12})+$/,
									value : personData.fax,
									regexText : '传真号码格式不正确'
								}, {
									id :'personPhotoId',
									xtype : 'hidden',
									name : 'personPhotoId',
									value : personData.personPhotoId
								}]

					}, {
						columnWidth : 0.5,
						layout : 'form',
						defaults : {
							anchor : '100%'
						},
						items : [{
							xtype : 'textfield',
							fieldLabel : '拼音/英文姓名',
							/*
							 * fieldLabel : '拼音/英文姓名', allowBlank : false,
							 * blankText : '拼音/英文姓名为必填内容',
							 */
							name : 'person.englishname',
							cls : 'readOnlyClass',
							readOnly : true,
							value : personData.englishname
								/*
								 * regex : /^[A-Za-z]+$/i, regexText : '只能输入英文'
								 */
							}, {
							xtype : 'textfield',
							fieldLabel : '籍贯',
							name : 'person.homeland',
							cls : 'readOnlyClass',
							readOnly : true,
							value : personData.homeland
								/*
								 * regex : /^[\u4e00-\u9fa5]{1,10}$/, regexText :
								 * '籍贯必须为中文'
								 */
							}, {
							xtype : "dickeycombo",
							nodeKey : 'nationality',
							fieldLabel : '民族',
							// emptyText : '请选择民族',
							width : 80,
							hiddenName : 'person.nationality',
							cls : 'readOnlyClass',
							readOnly : true,
							value : personData.nationality,
							listWidth : 80,
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
						}, {
							xtype : 'textfield',
							fieldLabel : '家庭电话',
							name : 'person.telphone',
							cls : 'readOnlyClass',
							readOnly : true,
							value : personData.telphone
								// regex : /^(\d{3,4})-(\d{7,8})/,
								// regexText : '电话号码格式不正确或者无效的号码'
							}, {
							id : 'cardnumber',
							xtype : 'textfield',
							fieldLabel : '证件号码',
							name : 'person.cardnumber',
							//allowBlank : false,
							readOnly : true,
							cls : 'readOnlyClass',
							blankText : '证件号码为必填内容',
							value : personData.cardnumber,
							listeners : {
								'blur' : function(f) {
									// var typeid = f.index;
									ajaxUniquenessValidator(this,
											"validatorPersonCardPersonAjaxValidator", "该人员已存在！");
								}
							}
						}, {
							xtype : 'textfield',
							fieldLabel : '单位电话',
							name : 'person.unitphone',
							cls : 'readOnlyClass',
							readOnly : true,
							value : personData.unitphone
								// regex : /^(\d{3,4})-(\d{7,8})/,
								// regexText : '电话号码格式不正确或者无效的号码'
							}, {
							xtype : 'numberfield',
							fieldLabel : '邮政编码',
							name : 'person.postcode',
							cls : 'readOnlyClass',
							readOnly : true,
							value : personData.postcode,
							regex : /^[0-9]{6}$/,
							regexText : '邮政编码格式不正确'
						}]

					}, {
						columnWidth : 1,
						layout : 'form',
						defaults : {
							anchor : '100%'
						},
						items : [{
							xtype : 'textfield',
							fieldLabel : '住所地址',
							readOnly : true,
							name : 'person.zhusuo',
							value : personData.zhusuo
						},{
							xtype : 'textfield',
							fieldLabel : '电子邮箱',
							name : 'person.selfemail',
							cls : 'readOnlyClass',
							readOnly : true,
							value : personData.selfemail,
							regex : /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+/,
							regexText : '电子邮箱格式不正确'

						}, {
							xtype : 'textfield',
							fieldLabel : '户口所在地',
							value : personData.hukou,
							readOnly : true,
							cls : 'readOnlyClass',
							name : 'person.hukou'
						}, {
							xtype : 'textfield',
							fieldLabel : '通信地址',
							cls : 'readOnlyClass',
							readOnly : true,
							value : personData.postaddress,
							name : 'person.postaddress'
						}]

					}]
				}, {
					columnWidth : .33,
					labelWidth : 95,
					layout : 'column',
					items : [{
						columnWidth : 1,
						layout : 'form',
						defaults : {
							anchor : '100%'
						},
						items : [{
							id : 'displayProfilePhoto',
							xtype : "panel",
							width : 100,
							rowspan : 2,
							style : 'padding:3px 4px 25px 15px;',
							height : 280,
							title : "个人照片",
							html : '<img src="' + __ctxPath
									+ personData.personPhotoUrl+'" ondblclick=showPic("'+personData.personPhotoUrl+'") width="100%" height="100%"/>'/*,
							tbar : new Ext.Toolbar({
										height : 30,
										items : [{
													text : '上传',
													iconCls : 'slupIcon',
													handler : function() {
														uploadPhotoBtnPerson(personId);
													}
												}, '-', {
													text : '删除',
													iconCls : 'btn-del',
													handler : function() {
														deleteUserPhotoPerson(personPhotoId);
													}
												}]
									})*/
						}]

					}]
				}]
			}]
		},{
			layout : 'column',
			xtype : 'fieldset',
			title : '身份证扫描件',
			labelWidth : 80,
			collapsible : true,
			autoHeight : true,
			anchor : '100%',
			items : [{
				columnWidth :1,
				layout : 'column',
				labelWidth : 65,
				defaults : {
					anchor : '100%'
				},
				items : [{
					columnWidth :0.33,
					layout : 'form',
					labelWidth : 65,
					defaults : {
						anchor : '100%'
					},
					items : [{
							xtype :'label',
							style :'padding-left :20px',
							html : '身份证正面&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;'
						},{
							id :'displayProfilePhoto2',
							xtype :'label',
							style :'padding-left :20px',
							html : '<div style="width:144px; height:84px; margin:10px 0px 0px 20px; padding:1px 1px 1px 1px;border:1px solid #46A900;"><img src="'+ __ctxPath+ personSFZZUrl+'" ondblclick=showPic("'+personSFZZUrl+'") width =140 height=80  /></div>'
						}]
				},{
					columnWidth :0.33,
					layout : 'form',
					labelWidth : 65,
					defaults : {
						anchor : '100%'
					},
					items : [{
							xtype :'label',
							html : '身份证反面&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;'
						},{
							id :'displayProfilePhoto3',
							xtype :'label',
							html : '<div style="width:144px; height:84px; margin:10px 0px 0px 20px; padding:1px 1px 1px 1px;border:1px solid #46A900;"><img src="'+ __ctxPath+ personSFZFUrl+'" ondblclick=showPic("'+personSFZFUrl+'") width =140 height=80  /></div>'
						}]
				}]
			}]
		},{
			layout : 'column',
			xtype:'fieldset',
            title: '填写个人教育/工作信息',
            labelWidth : 80,
            collapsible: true,
            autoHeight:true,
            anchor:'100%',
			items :[{
				columnWidth : .33,
				layout : 'form',
				labelWidth : 65,
				defaults : {anchor : '100%'},
				items : [{
					xtype : "dickeycombo",
					nodeKey :'dgree',
					fieldLabel : '学历',
					width : 80,
					editable : false,
					value: personData.dgree,
					readOnly : true,
		            cls : 'readOnlyClass',
		            listeners : {
							afterrender : function(combox) {
								var st = combox.getStore();
								st.on("load", function() {
									if(combox.getValue() == 0||combox.getValue()==1||combox.getValue()==""||combox.getValue()==null){
										combox.setValue("");
									}else{
										combox.setValue(combox
											.getValue());
									}
									combox.clearInvalid();
								})
							}
						}
				}]
			},{
				columnWidth : .66,
				layout : 'form',
				labelWidth : 65,
				defaults : {anchor : '100%'},
				items :[{
					xtype : 'textfield',
					fieldLabel : '毕业院校',
					value : personData.graduationunversity,
					readOnly : true,
		            cls : 'readOnlyClass'
				}]
			},{
				columnWidth : 1,
				labelWidth : 65,
				layout : 'column',
				items :[{
					columnWidth : 0.33,
					layout : 'form',
					defaults : {anchor : '100%'},
					items :[{
						xtype : "dickeycombo",
						nodeKey :'degreewei',
						fieldLabel : '学位',
						width : 80,
						editable : false,
						value : personData.degreewei,
						readOnly : true,
		            cls : 'readOnlyClass',
		            listeners : {
							afterrender : function(combox) {
								var st = combox.getStore();
								st.on("load", function() {
									if(combox.getValue() == 0||combox.getValue()==1||combox.getValue()==""||combox.getValue()==null){
										combox.setValue("");
									}else{
										combox.setValue(combox
											.getValue());
									}
									combox.clearInvalid();
								})
							}
						}
				
					}]
				},{
					columnWidth : 0.33,
					layout : 'form',
					defaults : {anchor : '99.5%'},
					items :[{
						xtype : "dickeycombo",
						nodeKey :'unitproperties',
						fieldLabel : '单位性质',
						editable : false,
						value : personData.unitproperties,
						width : 80,
						readOnly : true,
		            cls : 'readOnlyClass',
		            listeners : {
							afterrender : function(combox) {
								var st = combox.getStore();
								st.on("load", function() {
									if(combox.getValue() == 0||combox.getValue()==1||combox.getValue()==""||combox.getValue()==null){
										combox.setValue("");
									}else{
										combox.setValue(combox
											.getValue());
									}
									combox.clearInvalid();
								})
							}
						}
					}]
				},{
					columnWidth : 0.33,
					layout : 'form',
					labelWidth : 70,
					defaults : {anchor : '100%'},
					items :[{
                		xtype : 'textfield',
						fieldLabel : '是否公务员',
						value : (personData.ispublicservant)==true ? "是" : "否",
						readOnly : true,
		            cls : 'readOnlyClass'
                		
					}]
				}]
			},{
				columnWidth : .33,
				layout : 'form',
				labelWidth : 65,
				defaults : {anchor : '100%'},
				items : [{
						xtype : "dickeycombo",
						nodeKey :'zhiwujob',
						fieldLabel : '职务',
						editable : false,
						width : 80,
						value : personData.job,
						readOnly : true,
		            cls : 'readOnlyClass',
		             listeners : {
							afterrender : function(combox) {
								var st = combox.getStore();
								st.on("load", function() {
									if(combox.getValue() == 0||combox.getValue()==1||combox.getValue()==""||combox.getValue()==null){
										combox.setValue("");
									}else{
										combox.setValue(combox
											.getValue());
									}
									combox.clearInvalid();
								})
							}
						}
				}]
			},{
				columnWidth : .66,
				layout : 'form',
				labelWidth : 65,
				defaults : {anchor : '100%'},
				items :[{
					xtype : 'textfield',
					fieldLabel : '工作单位',
					readOnly : true,
					value : personData.currentcompany,
					readOnly : true,
		            cls : 'readOnlyClass'
				}]
			},{
				columnWidth : .33,
				layout : 'form',
				labelWidth : 65,
				defaults : {anchor : '100%'},
				items :[{
					xtype : "dickeycombo",
					nodeKey :'techpersonnel',
					fieldLabel : '技术职称',
					width : 80,
					editable : false,
					value : personData.techpersonnel,
					readOnly : true,
		            cls : 'readOnlyClass',
		            listeners : {
						afterrender : function(combox) {
							var st = combox.getStore();
							st.on("load", function() {
								if(combox.getValue() == 0||combox.getValue()==1||combox.getValue()==""||combox.getValue()==null){
									combox.setValue("");
								}else{
									combox.setValue(combox
										.getValue());
								}
								combox.clearInvalid();
							})
						}
					}
				
				}]
			},{
				columnWidth : .66,
				layout : 'form',
				labelWidth : 150,
				defaults : {anchor : '100%'},
				items :[{
					xtype : 'numberfield',
					fieldLabel : '工作单位注册资本(万元)',
					value : personData.registeredcapital,
					readOnly : true,
		            cls : 'readOnlyClass'
				}]
			},{
				columnWidth : .40,
				layout : 'form',
				labelWidth : 95,
				defaults : {anchor : '100%'},
				items :[{
					xtype : 'datefield',
					fieldLabel : '本工作开始日期',
					value : personData.jobstarttime,
					readOnly : true,
		            cls : 'readOnlyClass'
				}]
			},{
				columnWidth : .60,
				layout : 'form',
				labelWidth : 145,
				defaults : {anchor : '98.5%'},
				items:[{
					xtype : 'numberfield',
					fieldLabel : '本工作税后月收入(万元)',
					value : personData.jobincome,
					readOnly : true,
		            cls : 'readOnlyClass'
				}]
			},{
				columnWidth : .66,
				layout : 'form',
				labelWidth : 65,
				defaults : {anchor : '100%'},
				items :[{
					xtype : 'textfield',
					fieldLabel : '单位地址',
					value : personData.unitaddress,
					readOnly : true,
		            cls : 'readOnlyClass'
				}]
			},{
				columnWidth : .33,
				layout : 'form',
				labelWidth : 65,
				defaults : {anchor : '100%'},
				items:[{
					xtype : 'numberfield',
					fieldLabel : '邮政编码',
					value : personData.unitpostcode,
					readOnly : true,
		            cls : 'readOnlyClass'
				}]
			}]
		},{
			layout : 'column',
			xtype:'fieldset',
            title: '填写个人家庭信息',
            labelWidth : 80,
            collapsible: true,
            autoHeight:true,
            anchor:'100%',
			items :[{
				columnWidth : .40,
				layout : 'form',
				labelWidth : 65,
				defaults : {anchor : '100%'},
				items :[{
            		xtype : 'textfield',
					fieldLabel : '是否户主',
					value : (personData.isheadoffamily)==true ? "是" : "否",
					readOnly : true,
		            cls : 'readOnlyClass'
            		
				}]
			},{
				columnWidth : .59,
				layout : 'form',
				labelWidth : 65,
				defaults : {anchor : '100%'},
				items :[{
					xtype : 'textfield',
					fieldLabel : '配偶姓名',
					readOnly : true,
		            value : personData.mateValue
				}]
			},{
				columnWidth : 1,
				layout : 'column',
				items :[{
					columnWidth : 1,
					layout : 'form',
					labelWidth : 65,
					defaults : {anchor : '100%'},
					items :[{
						//选省
		            	xtype : 'combo',
						fieldLabel : '家庭住址',
						editable : false,
						value : personData.familyaddress,
						width : 80,
						readOnly : true,
		            cls : 'readOnlyClass'
					}]
				}/*,{
					columnWidth : 0.28,
					layout : 'form',
					labelWidth : 20,
					defaults : {anchor : '100%'},
					items :[{
						//选市
						xtype : 'combo',
						labelSeparator :'',
						editable : false,
						fieldLabel : '省',
						width : 80,
						value : personData.shivalue,
						readOnly : true,
		            cls : 'readOnlyClass'
					}]
				},{
					columnWidth : 0.28,
					layout : 'form',
					labelWidth : 20,
					defaults : {anchor : '100%'},
					items :[{
						//选区县
						xtype : 'combo',
						labelSeparator :'',
						editable : false,
						fieldLabel : '市',
						width : 80,
						value : personData.xianvalue,
						readOnly : true,
		                cls : 'readOnlyClass'
					}]
				},{
					columnWidth : 0.08,
					layout : 'form',
					defaults : {anchor : '100%'},
					items :[{
						xtype : 'label',
						labelSeparator :'',
						text : ' 区/县'
					}]
				
				
				}*/]
			},{
				columnWidth : 1,
				layout : 'column',
				labelWidth : 65,
				items :[{
					columnWidth :0.33,
					layout : 'form',
					defaults : {anchor : '100%'},
					items :[{
						xtype : 'textfield',
						fieldLabel : '路名',
						value : personData.roadname,
						readOnly : true,
		            cls : 'readOnlyClass'
					}]
				},{
					columnWidth : 0.33,
					layout : 'form',
					defaults : {anchor : '100%'},
					items :[{
						xtype : 'numberfield',
						fieldLabel : '路号',
						value : personData.roadnum,
						readOnly : true,
		            cls : 'readOnlyClass'
					}]
				
				},{
					columnWidth : 0.33,
					layout : 'form',
					defaults : {anchor : '100%'},
					items :[{
						xtype : 'textfield',
						fieldLabel : '社区名',
						value : personData.communityname,
						readOnly : true,
		            cls : 'readOnlyClass'
					}]
				}]
			},{
				columnWidth : 1,
				layout : 'column',
				labelWidth : 65,
				items :[{
					columnWidth :0.60,
					layout : 'form',
					defaults : {anchor : '100%'},
					items :[{
						xtype : 'textfield',
						fieldLabel : '门牌号',
						value : personData.doorplatenum,
						readOnly : true,
		            cls : 'readOnlyClass'
					}]
				},{
					columnWidth :0.39,
					layout : 'form',
					defaults : {anchor : '100%'},
					items :[{
						xtype : 'numberfield',
						fieldLabel : '邮政编码',
						readOnly : true,
						value : personData.familypostcode,
						readOnly : true,
		            cls : 'readOnlyClass'
					}]
				}]
			},{
				columnWidth : 1,
				layout : 'column',
				items :[{
					columnWidth : 0.33,
					layout :'form',
					labelWidth : 65,
					defaults :{anchor : '100%'},
					items :[{
						xtype : "dickeycombo",
						nodeKey :'employway',
						fieldLabel : '占用方式',
						editable : false,
						readOnly : true,
						width : 80,
						value : personData.employway,
						readOnly : true,
		            cls : 'readOnlyClass',
		            listeners : {
						afterrender : function(combox) {
							var st = combox.getStore();
							st.on("load", function() {
								if(combox.getValue() == 0||combox.getValue()==1||combox.getValue()==""||combox.getValue()==null){
									combox.setValue("");
								}else{
									combox.setValue(combox
										.getValue());
								}
								combox.clearInvalid();
							})
						}
					}
					}]
				
				},{
					columnWidth : 0.33,
					layout :'form',
					labelWidth : 80,
					defaults :{anchor : '100%'},
					items :[{
						xtype : "dickeycombo",
						nodeKey :'homeshape',
						fieldLabel : '现住宅形式',
						editable : false,
						width : 80,
						value : personData.homeshape,
						readOnly : true,
		            cls : 'readOnlyClass',
		             listeners : {
						afterrender : function(combox) {
							var st = combox.getStore();
							st.on("load", function() {
								if(combox.getValue() == 0||combox.getValue()==1||combox.getValue()==""||combox.getValue()==null){
									combox.setValue("");
								}else{
									combox.setValue(combox
										.getValue());
								}
								combox.clearInvalid();
							})
						}
					}
					}]
				},{
					columnWidth : 0.33,
					layout :'form',
					labelWidth : 120,
					defaults :{anchor : '100%'},
					items :[{
						xtype : 'numberfield',
						fieldLabel : '现住宅面积(平方米)',
						value : personData.housearea,
						readOnly : true,
		            cls : 'readOnlyClass'
					}]
				}]
			},{
				columnWidth : 1,
				layout : 'column',
				items:[{
					columnWidth : 0.50,
					layout :'form',
					labelWidth : 120,
					defaults :{anchor : '100%'},
					items:[{
						xtype : 'numberfield',
						fieldLabel : '家庭税后月收入(元)',
						value : personData.homeincome,
						readOnly : true,
		            cls : 'readOnlyClass'
					}]
				},{
					columnWidth : 0.49,
					layout :'form',
					labelWidth : 130,
					defaults :{anchor : '100%'},
					items :[{
						xtype : 'numberfield',
						fieldLabel : '家庭月非贷款支出(元)',
						value : personData.homeexpend,
						readOnly : true,
		            cls : 'readOnlyClass'
					}]
				}]
			},{
				columnWidth : 0.99,
				layout : 'form',
				labelWidth : 130,
					defaults :{anchor : '100%'},
				items :[{
					xtype : 'numberfield',
					fieldLabel : '家庭月贷款支出(元)',
					value : personData.homecreditexpend,
					readOnly : true,
		            cls : 'readOnlyClass'
				}]
			}]
		}]
		},{
			columnWidth : 0.17,
			defaults : {xtype:'button',style:'margin:5px 0px 0px 5px;',iconCls:'seeIcon',iconAlign: 'left'},
			/*items : [{
				layout : 'form',
				xtype : 'label',
				html :'<br><li><span style="font-size: 14px;"><a href="#" onclick="addPersonMate()">添加配偶信息</a><span></li><br>' +
					'<li><span style="font-size: 14px;"><a href="#" onclick="addPersonFamily()">家庭经济情况</a></span></li><br>' +
					'<li><span style="font-size: 14px;"><a href="#" onclick="addPersonThereunder()">旗下公司</a></span></li><br>' +
					'<li><span style="font-size: 14px;"><a href="#" onclick="addPersonReditregistries()">征信记录</a></span></li><br>' +
					'<li><span style="font-size: 14px;"><a href="#" onclick="addPersonAttachflie()">个人信息附件</a></span></li><br>'+
					'<li><span style="font-size: 14px;"><a href="#" onclick="showBusinessRecord()" title="与本担保公司的业务记录" >与本担保公...</a></span></li><br>'
			}]*/
			items:[{
				id : 'seePersonMateButton',
				text : '配偶信息',
				tooltip : '配偶信息',
				scope : this,
				handler : function() {
					var cardNum = Ext.getCmp('cardnumber').getValue();
					seePersonMate(cardNum);
				}
			},{
				text : '家庭经济情况',
				tooltip : '家庭经济情况信息',
				scope : this,
				handler : function() {
					addPersonFamily("see");
				}
			},{
				text : '旗下公司',
				tooltip : '旗下公司信息',
				scope : this,
				handler : function() {
					var cardNum = Ext.getCmp('cardnumber').getValue();
					thereunderListWin(cardNum);
				}
			},{
				text : '征信记录',
				tooltip : '征信记录信息',
				scope : this,
				handler : function() {
					var cardNum = Ext.getCmp('cardnumber').getValue();
					reditregistriesListWin(cardNum);
				}
				
			},/*{
				text : '个人信息附件',
				tooltip : '个人信息附件信息',
				scope : this,
				handler : function() {
					var attachfileWin = new Ext.Window({
							title : '个人信息附件',
							width : (screen.width-180)*0.7 - 50,
							height: 430,
							border : false,
							layout : 'fit',
							modal : true,
							constrainHeader : true ,
							collapsible : true, 
							autoLoad : {
								url : 'attachfile/listAttachfile.jsp',
								scripts : true
							}
						}).show();
				}
			},*/{
				text : '与本担保公司的业务记录',
				tooltip : '与本担保公司的业务记录信息',
				scope : this,
				handler : function() {
					var cardNum = Ext.getCmp('cardnumber').getValue();
					businessRecordListWin(cardNum);
				}
			},{
				text : '关系人(非配偶)信息',
				tooltip : '关系人信息',
				scope : this,
				handler : function() {
					var cardNum = Ext.getCmp('cardnumber').getValue();
					selectRelationPersonList(cardNum);
				}
			},{
			
					text : '资信评估',
					tooltip : '资信评估',
					scope : this,
					handler : function() {
							var cardNum = Ext.getCmp('cardnumber').getValue();
							Ext.Ajax.request({   
						    	url: __ctxPath+'/credit/customer/person/queryPersonByNameMessage.do',   
						   	 	method:'post',   
						    	params:{perosnName: cardNum},   
						    	success: function(response, option) {   
						        	var obj = Ext.decode(response.responseText);
						        	var personIdValue = obj.data.id;
									new EnterpriseEvaluationWin({customerId:personIdValue,customerType:'个人'}).show()
						    	}
							});
					}
				
				
			}]
		}],
		listeners : {
			render : function (){
				flag = true ;
			}
		}
	});
	if(personData.personPhotoId == 0){
		var display2 = Ext
			.getCmp('displayProfilePhoto');
		var personSelect = Ext.getCmp('personsex');
		if (personSelect.getValue == '313') {// 312
			display2.body
					.update('<img src="'
							+ __ctxPath
							+ '/images/default_image_female.jpg" />');
		} else {
			display2.body
					.update('<img src="'
							+ __ctxPath
							+ '/images/default_image_male.jpg" />');
		}
	}

	if(personSFZZId == 0){
		
		var display2 = Ext
			.getCmp('displayProfilePhoto2');
		display2.setText('<img src="'
								+ __ctxPath
								+ '/images/nopic.jpg" width =140 height=80 />',false);
	}
	if(personSFZFId == 0){
		var display2 = Ext
			.getCmp('displayProfilePhoto3');
		display2.setText('<img src="'
								+ __ctxPath
								+ '/images/nopic.jpg" width =140 height=80 />',false);
	}
	if(personData.marry!='648'){
		Ext.getCmp('seePersonMateButton').hide();
	}else{
		Ext.getCmp('seePersonMateButton').show();
	}
	
