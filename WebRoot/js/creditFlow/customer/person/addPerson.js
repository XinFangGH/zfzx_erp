var anchor = '96.5%';
var requiredStar = '<font color="red">*</font>';
var personPhotoId = '';
var personSFZZId;
var personSFZFId;

var addPersonPanel = new Ext.form.FormPanel({
	id : 'addPersonPanel',
	url : __ctxPath + '/creditFlow/customer/person/addInfoPerson.do',
	monitorValid : true,
	width : (screen.width - 180) * 0.7 + 130,
	bodyStyle : 'padding:10px',
	renderTo : 'addPersonDiv',
	autoScroll : true,
	isflag : false,
	labelAlign : 'right',
	buttonAlign : 'center',
	frame : true,
	layout : 'column',
	items : [{
		columnWidth : 0.83,
		items : [{
			layout : 'column',
			xtype : 'fieldset',
			title : '填写个人基本信息',
			collapsible : true,
			autoHeight : true,
			anchor : '100%',
			items : [{
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
									allowBlank : false,
									name : 'person.name',
									blankText : '姓名为必填内容',
									regex : /^[\u4e00-\u9fa5]+$/,
									regexText : "必须输入汉字"
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
									format : 'Y-m-d'
								}, {
									xtype : "dickeycombo",
									nodeKey : 'card_type_key',
									hiddenName : "person.cardtype",
									itemName : '证件类型', // xx代表分类名称
									fieldLabel : "证件类型",
									allowBlank : false,
									editable : true,
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
									allowBlank : false,
									name : 'person.cellphone',
									regex : /^1[358]\d{9}$/,
									regexText : '手机号码格式不正确或者无效的号码'
								}, {
									xtype : 'numberfield',
									fieldLabel : '传真号码',
									name : 'person.fax',
									regex : /^[+]{0,1}(\d){1,3}[ ]?([-]?((\d)|[ ]){1,12})+$/,
									regexText : '传真号码格式不正确'
								}, {
									id :'personPhotoId',
									xtype : 'hidden',
									name : 'personPhotoId',
									value : personPhotoId
								}, {
									id :'personSFZZId',
									xtype : 'hidden',
									name : 'personSFZZId',
									value : personSFZZId
								}, {
									id :'personSFZFId',
									xtype : 'hidden',
									name : 'personSFZFId',
									value : personSFZFId
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
							name : 'person.englishname'
								/*
								 * regex : /^[A-Za-z]+$/i, regexText : '只能输入英文'
								 */
							}, {
							xtype : 'textfield',
							fieldLabel : '籍贯',
							name : 'person.homeland'
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
							name : 'person.telphone'
								// regex : /^(\d{3,4})-(\d{7,8})/,
								// regexText : '电话号码格式不正确或者无效的号码'
							}, {
							id : 'cardnumber',
							xtype : 'textfield',
							fieldLabel : '证件号码',
							name : 'person.cardnumber',
							allowBlank : false,
							blankText : '证件号码为必填内容',
							listeners : {
								'blur' : function(f) {
									// var typeid = f.index;
									ajaxUniquenessValidator(this,
											"ajaxValidatorCardPersonAjaxValidator", "该人员已存在！");
								}
							}
						}, {
							xtype : 'textfield',
							fieldLabel : '单位电话',
							name : 'person.unitphone'
								// regex : /^(\d{3,4})-(\d{7,8})/,
								// regexText : '电话号码格式不正确或者无效的号码'
							}, {
							xtype : 'numberfield',
							fieldLabel : '邮政编码',
							name : 'person.postcode',
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
							allowBlank : false,
							name : 'person.zhusuo'
						},{
							xtype : 'textfield',
							fieldLabel : '电子邮箱',
							name : 'person.selfemail',
							regex : /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+/,
							regexText : '电子邮箱格式不正确'

						}, {
							xtype : 'textfield',
							fieldLabel : '户口所在地',
							name : 'person.hukou'
						}, {
							xtype : 'textfield',
							fieldLabel : '通信地址',
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
									+ '/images/default_image_male.jpg"/>',
							tbar : new Ext.Toolbar({
										height : 30,
										items : [{
													text : '上传',
													iconCls : 'slupIcon',
													handler : function() {
														uploadPhotoBtnPerson('个人照片','cs_person_tx','displayProfilePhoto','personPhotoId');
													}
												}, '-', {
													text : '删除',
													iconCls : 'deleteIcon',
													handler : function() {
														deleteUserPhotoPerson();
													}
												}]
									})
						}]

					}]
				}]
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
							html : '身份证正面&nbsp;&nbsp;&nbsp;<a href="#" onClick =uploadPhotoBtnPerson(\'身份证正面\',\'cs_person_sfzz\',\'displayProfilePhoto2\',\'personSFZZId\')>上传</a>&nbsp;&nbsp;&nbsp;<a href="#" onClick =deleteUserPhotoPerson2()>删除</a>'
						},{
							id :'displayProfilePhoto2',
							xtype :'label',
							style :'padding-left :20px',
							html : '<img src="'
								+ __ctxPath
								+ '/images/nopic.jpg" width =140 height=80/>'
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
							html : '身份证反面&nbsp;&nbsp;&nbsp;<a href="#" onClick =uploadPhotoBtnPerson(\'身份证反面\',\'cs_person_sfzf\',\'displayProfilePhoto3\',\'personSFZFId\')>上传</a>&nbsp;&nbsp;&nbsp;<a href="#" onClick =deleteUserPhotoPerson3()>删除</a>'
						},{
							id :'displayProfilePhoto3',
							xtype :'label',
							html : '<img src="'
								+ __ctxPath
								+ '/images/nopic.jpg"  width =140 height=80/>'
						}]
				}]
			}]
		}, {
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
				items : [{
					xtype : "dickeycombo",
					nodeKey : 'dgree',
					fieldLabel : '学历',
					// emptyText : '请选择学历',
					width : 80,
					hiddenName : 'person.dgree',
					listWidth : 80,
					editable : true,
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
											combox.setValue(combox.getValue());
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
				items : [{
					xtype : 'textfield',
					fieldLabel : '毕业院校',
					name : 'person.graduationunversity'
						/*
						 * regex : /[^0-9]/, regexText : '输入错误'
						 */

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
					items : [{
						xtype : "dickeycombo",
						nodeKey : 'degreewei',
						fieldLabel : '学位',
						// emptyText : '请选择学位',
						width : 80,
						hiddenName : 'person.degreewei',
						listWidth : 80,
						editable : true,
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
					items : [{
						xtype : "dickeycombo",
						nodeKey : 'unitproperties',
						fieldLabel : '单位性质',
						// emptyText : '请选择单位性质',
						hiddenName : 'person.unitproperties',
						listWidth : 120,
						width : 80,
						editable : true,
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
											inputValue : true
										}, {
											boxLabel : '否',
											name : 'person.ispublicservant',
											inputValue : false
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
				items : [{
					xtype : "dickeycombo",
					nodeKey : 'zhiwujob',
					fieldLabel : '职务',
					// emptyText : '请选择职务',
					hiddenName : 'person.job',
					listWidth : 80,
					width : 80,
					editable : true,
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
											combox.setValue(combox.getValue());
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
				items : [{
							xtype : 'textfield',
							fieldLabel : '工作单位',
							name : 'person.currentcompany'
						}]
			}, {
				columnWidth : .33,
				layout : 'form',
				labelWidth : 65,
				defaults : {
					anchor : '100%'
				},
				items : [{
					xtype : "dickeycombo",
					nodeKey : 'techpersonnel',
					fieldLabel : '技术职称',
					// emptyText : '请选择技术职称',
					hiddenName : 'person.techpersonnel',
					listWidth : 80,
					width : 80,
					editable : true,
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
											combox.setValue(combox.getValue());
										}
										combox.clearInvalid();
									})
						}
					}
				}]
			}, {
				columnWidth : .66,
				layout : 'form',
				labelWidth : 150,
				defaults : {
					anchor : '100%'
				},
				items : [{
							xtype : 'numberfield',
							fieldLabel : '工作单位注册资本(万元)',
							name : 'person.registeredcapital'
						}]
			}, {
				columnWidth : .40,
				layout : 'form',
				labelWidth : 95,
				defaults : {
					anchor : '100%'
				},
				items : [{
					xtype : 'datefield',
					fieldLabel : '本工作开始日期',
					name : 'person.jobstarttime',
					format : 'Y-m-d'
						/*
						 * regex : /^(\d{4})\-(\d{2})\-(\d{2})$/, regexText :
						 * '日期格式不正确'
						 */
					}]
			}, {
				columnWidth : .60,
				layout : 'form',
				labelWidth : 145,
				defaults : {
					anchor : '98.5%'
				},
				items : [{
							xtype : 'numberfield',
							fieldLabel : '本工作税后月收入(万元)',
							name : 'person.jobincome'
						}]
			}, {
				columnWidth : .66,
				layout : 'form',
				labelWidth : 65,
				defaults : {
					anchor : '100%'
				},
				items : [{
							xtype : 'textfield',
							fieldLabel : '单位地址',
							name : 'person.unitaddress'
						}]
			}, {
				columnWidth : .33,
				layout : 'form',
				labelWidth : 65,
				defaults : {
					anchor : '100%'
				},
				items : [{
							xtype : 'numberfield',
							fieldLabel : '邮政编码',
							name : 'person.unitpostcode',
							regex : /^[0-9]{6}$/,
							regexText : '邮政编码格式不正确'
						}]
			}]
		}, {
			layout : 'column',
			xtype : 'fieldset',
			title : '填写个人家庭信息',
			labelWidth : 80,
			collapsible : true,
			autoHeight : true,
			anchor : '100%',
			items : [{
						columnWidth : .40,
						layout : 'form',
						labelWidth : 65,
						defaults : {
							anchor : '100%'
						},
						items : [{
									xtype : 'radiogroup',
									fieldLabel : '是否户主',
									items : [{
												boxLabel : '是',
												name : 'person.isheadoffamily',
												inputValue : true
											}, {
												boxLabel : '否',
												name : 'person.isheadoffamily',
												inputValue : false
											}]
								}]
					}, {
						columnWidth : .60,
						id : 'matenamefiled',
						layout : 'form',
						labelWidth : 65,
						defaults : {
							anchor : '100%'
						},
						items : [{
							id : 'poxm',
							xtype : 'combo',
							fieldLabel : '配偶姓名',
							triggerClass : 'x-form-search-trigger',
							hiddenName : 'matename',
							onTriggerClick : function() {
								selectPWNameTwo(selectSpousePerson);
							},
							resizable : true,
							mode : 'romote',
							editable : true,
							lazyInit : false,
							// allowBlank : false,
							typeAhead : true,
							minChars : 1,
							listWidth : 150,
							store : new Ext.data.JsonStore({
								url : __ctxPath
										+ '/creditFlow/customer/person/ajaxQueryForComboPerson.do?isAll='+isGranted('_detail_sygrkh'),
								root : 'topics',
								autoLoad : true,
								fields : [{
											name : 'id'
										}, {
											name : 'name'
										}],
								listeners : {
									'load' : function(s, r, o) {
										if (s.getCount() == 0) {
											Ext.getCmp('poxm')
													.markInvalid('没有查找到匹配的记录');
										}
									}
								}
							}),
							displayField : 'name',
							valueField : 'id',
							triggerAction : 'all',
							listeners : {
								'select' : function(combo, record, index) {
									Ext.getCmp('poid').setValue(record
											.get('id'));
									Ext.getCmp('poxm').setValue(record
											.get('name'));
								},
								'blur' : function(f) {
									if (f.getValue() != null
											&& f.getValue() != '') {
										Ext.getCmp('poid').setValue(f
												.getValue());
									}
								}
							}
						}, {
							id : 'poid',
							name : 'person.mateid',
							xtype : 'hidden'
						}]
					}, {
						columnWidth : 1,
						layout : 'column',
						items : [{
							columnWidth : 1,
							layout : 'form',
							labelWidth : 65,
							defaults : {
								anchor : '100%'
							},
							items : [{
										id : 'familyaddress',
										xtype : 'textfield',
										fieldLabel : '家庭住址',
										name : 'person.familyaddress',
										listeners : {
											'focus' : function() {
												selectDictionary('area',
														getObjArray);
											}
										}
									}/*
										 * ,{ id :'familyshengid', xtype :
										 * 'hidden', name : 'person.familysheng' }
										 */]
						}/*
							 * ,{ columnWidth : 0.28, layout : 'form',
							 * labelWidth : 20, defaults : {anchor : '100%'},
							 * items :[{ id : 'familyshi', xtype : 'textfield',
							 * fieldLabel : '省', labelSeparator :'', name :
							 * 'familyshi', readOnly : true },{ id :
							 * 'familyshiid', xtype : 'hidden', name :
							 * 'person.familyshi' }] },{ columnWidth : 0.28,
							 * layout : 'form', labelWidth : 20, defaults :
							 * {anchor : '100%'}, items :[{ id : 'familyxian',
							 * xtype : 'textfield', fieldLabel : '县',
							 * labelSeparator :'', name : 'familyxian', readOnly :
							 * true },{ id : 'familyxianid', xtype : 'hidden',
							 * name : 'person.familyxian' }] },{ columnWidth :
							 * 0.08, layout : 'form', defaults : {anchor :
							 * '100%'}, items :[{ xtype : 'label',
							 * labelSeparator :'', text : ' 区/县' }]
							 * 
							 *  }
							 */]
					}, {
						columnWidth : 1,
						layout : 'column',
						labelWidth : 65,
						items : [{
									columnWidth : 0.33,
									layout : 'form',
									defaults : {
										anchor : '100%'
									},
									items : [{
										xtype : 'textfield',
										fieldLabel : '路名',
										name : 'person.roadname'
											/*
											 * regex : /^[\u4e00-\u9fa5]{1,10}$/ ,
											 * regexText : '只能输入中文'
											 */
										}]
								}, {
									columnWidth : 0.33,
									layout : 'form',
									defaults : {
										anchor : '100%'
									},
									items : [{
												xtype : 'numberfield',
												fieldLabel : '路号',
												name : 'person.roadnum'
											}]

								}, {
									columnWidth : 0.33,
									layout : 'form',
									defaults : {
										anchor : '100%'
									},
									items : [{
										xtype : 'textfield',
										fieldLabel : '社区名',
										name : 'person.communityname'
											/*
											 * regex : /^[\u4e00-\u9fa5]{1,10}$/ ,
											 * regexText : '只能输入中文'
											 */
										}]
								}]
					}, {
						columnWidth : 1,
						layout : 'column',
						labelWidth : 65,
						items : [{
									columnWidth : 0.60,
									layout : 'form',
									defaults : {
										anchor : '100%'
									},
									items : [{
												xtype : 'textfield',
												fieldLabel : '门牌号',
												name : 'person.doorplatenum'
											}]
								}, {
									columnWidth : 0.39,
									layout : 'form',
									defaults : {
										anchor : '100%'
									},
									items : [{
										xtype : 'numberfield',
										fieldLabel : '邮政编码',
										name : 'person.familypostcode'
											/*
											 * regex : /^[0-9]{6}$/, regexText :
											 * '邮编格式不正确'
											 */
										}]
								}]
					}, {
						columnWidth : 1,
						layout : 'column',
						items : [{
							columnWidth : 0.33,
							layout : 'form',
							labelWidth : 65,
							defaults : {
								anchor : '100%'
							},
							items : [{
								xtype : "dickeycombo",
								nodeKey : 'employway',
								// emptyText : '请选择占用方式',
								fieldLabel : '占用方式',
								hiddenName : 'person.employway',
								listWidth : 100,
								width : 80,
								editable : true,
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
							labelWidth : 80,
							defaults : {
								anchor : '100%'
							},
							items : [{
								xtype : "dickeycombo",
								nodeKey : 'homeshape',
								// emptyText : '请选择现住宅形式',
								fieldLabel : '现住宅形式',
								hiddenName : 'person.homeshape',
								listWidth : 100,
								width : 80,
								editable : true,
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
							labelWidth : 120,
							defaults : {
								anchor : '100%'
							},
							items : [{
										xtype : 'numberfield',
										fieldLabel : '现住宅面积(平方米)',
										name : 'person.housearea'
									}]
						}]
					}, {
						columnWidth : 1,
						layout : 'column',
						items : [{
									columnWidth : 0.50,
									layout : 'form',
									labelWidth : 120,
									defaults : {
										anchor : '100%'
									},
									items : [{
												xtype : 'numberfield',
												fieldLabel : '家庭税后月收入(元)',
												name : 'person.homeincome'
											}]
								}, {
									columnWidth : 0.49,
									layout : 'form',
									labelWidth : 130,
									defaults : {
										anchor : '100%'
									},
									items : [{
												xtype : 'numberfield',
												fieldLabel : '家庭月非贷款支出(元)',
												name : 'person.homeexpend'
											}]
								}]
					}, {
						columnWidth : 0.99,
						layout : 'form',
						labelWidth : 130,
						defaults : {
							anchor : '100%'
						},
						items : [{
									xtype : 'numberfield',
									fieldLabel : '家庭月贷款支出(元)',
									name : 'person.homecreditexpend'
								}, {
									id : 'personId',
									xtype : 'hidden',
									name : 'person.id',
									value : null
								}]
					}]
		}]
	}, {
		columnWidth : .17,
		defaults : {
			xtype : 'button',
			style : 'margin:5px 0px 0px 5px;',
			iconCls : 'seeIcon',
			iconAlign : 'left'
		},
		/*
		 * items :[{ layout : 'form', xtype : 'label', html :'<br><li><span
		 * style="font-size: 14px;"><a href="#"
		 * onclick="addPersonMate()">添加配偶信息</a><span></li><br>' + '<li><span
		 * style="font-size: 14px;"><a href="#"
		 * onclick="addPersonFamily()">家庭经济情况</a></span></li><br>' + '<li><span
		 * style="font-size: 14px;"><a href="#"
		 * onclick="addPersonThereunder()">旗下公司</a></span></li><br>' + '<li><span
		 * style="font-size: 14px;"><a href="#"
		 * onclick="addPersonReditregistries()">征信记录</a></span></li><br>' + '<li><span
		 * style="font-size: 14px;"><a href="#"
		 * onclick="addPersonAttachflie()">个人信息附件</a></span></li><br>'+ '<li><span
		 * style="font-size: 14px;"><a href="#" onclick="showBusinessRecord()"
		 * title="与本担保公司的业务记录" >与本担保公...</a></span></li><br>' }]
		 */
		items : [/*
					 * { id : 'selectmate', xtype : 'button', text : '添加配偶信息',
					 * tooltip : '添加配偶信息', scope : this, handler : function() {
					 * if(!addPersonPanel.isflag){
					 * Ext.MessageBox.alert("友情提示","先保存个人信息"); return; }else{
					 * //addPersonMate(); } } },
					 */{
					xtype : 'button',
					text : '家庭经济情况',
					tooltip : '家庭经济情况信息',
					scope : this,
					handler : function() {
						if (!addPersonPanel.isflag) {
							Ext.ux.Toast.msg("友情提示", "先保存个人信息");
							return;
						} else {
							addPersonFamily("add");
						}
					}
				}, {
					xtype : 'button',
					text : '旗下公司',
					tooltip : '旗下公司信息',
					scope : this,
					handler : function() {
						if (!addPersonPanel.isflag) {
							Ext.ux.Toast.msg("友情提示", "先保存个人信息");
							return;
						} else
							var cardNum = Ext.getCmp('cardnumber').getValue();
						thereunderListWin(cardNum);
					}
				}, {
					xtype : 'button',
					text : '征信记录',
					tooltip : '征信记录信息',
					scope : this,
					handler : function() {
						if (!addPersonPanel.isflag) {
							Ext.ux.Toast.msg("友情提示", "先保存个人信息");
							return;
						} else
							var cardNum = Ext.getCmp('cardnumber').getValue();
						reditregistriesListWin(cardNum);
					}
				}, {
					xtype : 'button',
					text : '与本担保公司的业务记录',
					tooltip : '与本担保公司的业务记录信息',
					scope : this,
					handler : function() {
						if (!addPersonPanel.isflag) {
							Ext.ux.Toast.msg("友情提示", "先保存个人信息");
							return;
						} else
							var cardNum = Ext.getCmp('cardnumber').getValue();
						businessRecordListWin(cardNum);
					}
				}, {
					text : '关系人(非配偶)信息',
					tooltip : '关系人信息',
					scope : this,
					handler : function() {
						if (!addPersonPanel.isflag) {
							Ext.ux.Toast.msg("友情提示", "先保存个人信息");
							return;
						} else
							var cardNum = Ext.getCmp('cardnumber').getValue();
						selectRelationPersonList(cardNum);
					}
				},{
					text : '资信评估',
					tooltip : '资信评估',
					scope : this,
					handler : function() {
						if (!addPersonPanel.isflag) {
							Ext.MessageBox.alert("友情提示", "先保存个人信息");
							return;
						} else
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
	}]
});
var selectSpousePerson = function(obj) {
	Ext.getCmp('poxm').setValue(obj.name);
	Ext.getCmp('poid').setValue(obj.id);
}
var getObjArray = function(objArray) {
	var familyaddressValue = objArray[(objArray.length) - 1].text + '_'
			+ objArray[(objArray.length) - 2].text + '_'
			+ objArray[(objArray.length) - 3].text + '_' + objArray[0].text;
	// var familyaddressId =
	// objArray[(objArray.length)-1].id+'_'+objArray[(objArray.length)-2].id+'_'+objArray[(objArray.length)-3].id+'_'+objArray[0].id;
	Ext.getCmp('familyaddress').setValue(familyaddressValue);
	// Ext.getCmp('familyshengid').setValue(familyaddressId);
	/*
	 * Ext.getCmp('familysheng').setValue(objArray[(objArray.length)-1].text);
	 * Ext.getCmp('familysheng').setValue(objArray[(objArray.length)-1].text);
	 * Ext.getCmp('familyshengid').setValue(objArray[(objArray.length)-1].id);
	 * 
	 * Ext.getCmp('familyshi').setValue(objArray[(objArray.length)-2].text);
	 * Ext.getCmp('familyshiid').setValue(objArray[(objArray.length)-2].id);
	 * 
	 * Ext.getCmp('familyxian').setValue(objArray[0].text);
	 * Ext.getCmp('familyxianid').setValue(objArray[0].id);
	 */

}

uploadPhotoBtnPerson = function(title,mark,displayProfilePhoto,personPId) {
	new Ext.Window({
			id : 'uploadPhotoWin',
			title : title,
			layout : 'fit',
			width : (screen.width-180)*0.6,
			height : 130,
			closable : true,
			resizable : true,
			plain : false,
			bodyBorder : false,
			border : false,
			modal : true,
			constrainHeader : true ,
			bodyStyle:'overflowX:hidden',
			buttonAlign : 'right',
			items:[new Ext.form.FormPanel({
				id : 'uploadPhotoFrom',
				url : __ctxPath+'/contract/uploadPhotoProduceHelper.do',
				monitorValid : true,
				labelAlign : 'right',
				buttonAlign : 'center',
				enctype : 'multipart/form-data',
				fileUpload: true, 
				layout : 'column',
				frame : true ,
				items : [{
					columnWidth : 1,
					layout : 'form',
					labelWidth : 140,
					defaults : {anchor : '95%'},
					items :[{
						xtype : 'textfield',
						fieldLabel : title,
						allowBlank : false,
						blankText : title+'不能为空',
						id : 'fileUpload',
						name: 'fileUpload',
	    				inputType: 'file'
					},{
						xtype : 'hidden',
						name: 'mark',
						value :mark
					}]
				}],
				buttons : [{
					text : '上传',
					iconCls : 'uploadIcon',
					formBind : true,
					handler : function() {
						Ext.getCmp('uploadPhotoFrom').getForm().submit({
							method : 'POST',
							waitTitle : '连接',
							waitMsg : '消息发送中...',
							success : function(form ,action) {
								Ext.ux.Toast.msg('提示',title+'上传成功！',
								Ext.getCmp('uploadPhotoWin').destroy(),
									function(btn, text) {
								});
								var fileid = action.result.fileid;
								var webPath = action.result.webPath;
								var display = Ext.getCmp(displayProfilePhoto);
								if(displayProfilePhoto=='displayProfilePhoto'){
									personPhotoId = fileid;
									display.body.update('<img src="' + __ctxPath + webPath
										+ '" ondblclick=showPic("'+webPath+'") width="100%" height="100%"/>');
									Ext.getCmp('personPhotoId').setValue(fileid);
								}else if(displayProfilePhoto=='displayProfilePhoto2'){
									personSFZZId = fileid;
									display.setText('<div style="width:144px; height:84px; margin:10px 0px 0px 20px; padding:1px 1px 1px 1px;border:1px solid #46A900;"><img src="'+ __ctxPath+ webPath+'" ondblclick=showPic("'+webPath+'") width ="140" height="80"/></div>',false);
									Ext.getCmp('personSFZZId').setValue(fileid);
								}else if(displayProfilePhoto=='displayProfilePhoto3'){
									personSFZFId = fileid;
									display.setText('<div style="width:144px; height:84px; margin:10px 0px 0px 20px; padding:1px 1px 1px 1px;border:1px solid #46A900;"><img src="'+ __ctxPath+ webPath+'" ondblclick=showPic("'+webPath+'") width ="140" height="80"  /></div>',false);
									Ext.getCmp('personSFZFId').setValue(fileid);
								}
								
							},
							failure : function(form, action) {
								Ext.ux.Toast.msg('提示',title+'上传失败！');		
							}
						});
					}
				}]
			})]
		}).show();
};
deleteUserPhotoPerson = function() {
	var photoID = personPhotoId;
	Ext.Ajax.request({
		url : __ctxPath+'/creditFlow/fileUploads/DeleRsFileForm.do?fileid='+photoID,
		method : 'POST',
		scope : this,
		success : function() {
			Ext.ux.Toast.msg('状态', '头像删除成功！');
			var display = Ext.getCmp('displayProfilePhoto');
			var personsex = Ext.getCmp('personsex');
			if (personsex.getValue == '313') {// 312
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
			Ext.getCmp('personPhotoId').setValue('');
		},
		failure : function(result, action) {
			Ext.ux.Toast.msg('状态', '头像删除失败，请重试！');
		}
	});
};
deleteUserPhotoPerson2 = function() {
	var photoID = personSFZZId;
	Ext.Ajax.request({
		url : __ctxPath+'/creditFlow/fileUploads/DeleRsFileForm.do?fileid='+photoID,
		method : 'POST',
		scope : this,
		success : function() {
			Ext.ux.Toast.msg('状态', '删除成功！');
			var display = Ext.getCmp('displayProfilePhoto2');
			display.setText('<img src="'
								+ __ctxPath
								+ '/images/nopic.jpg" width =140 height=80 />',false);
			Ext.getCmp('personSFZZId').setValue('');
		},
		failure : function(result, action) {
			Ext.ux.Toast.msg('状态', '删除失败，请重试！');
		}
	});
};
deleteUserPhotoPerson3 = function() {
	var photoID = personSFZFId;
	Ext.Ajax.request({
		url : __ctxPath+'/creditFlow/fileUploads/DeleRsFileForm.do?fileid='+photoID,
		method : 'POST',
		scope : this,
		success : function() {
			Ext.ux.Toast.msg('状态', '删除成功！');
			var display = Ext.getCmp('displayProfilePhoto3');
			display.setText('<img src="'
								+ __ctxPath
								+ '/images/nopic.jpg" width =140 height=80 />',false);
			Ext.getCmp('personSFZFId').setValue('');
		},
		failure : function(result, action) {
			Ext.ux.Toast.msg('状态', '删除失败，请重试！');
		}
	});
};
