//addPersonWorkInfo.js

addPersonWorkInfo = Ext.extend(Ext.Window, {
	isLook : false,
	isRead : false,
	isflag : false,
	// 构造函数
	investPersonPanel : null,
	constructor : function(_cfg) {
		
		if (_cfg == null) {
			_cfg = {};
		};
		if(typeof(_cfg.isReadOnly) != "undefined")
		{
			this.isRead=_cfg.isReadOnly;
		};
		if(null!=_cfg.personData){
		    this.isflag=true;
		};
		if (typeof(_cfg.isLook) != "undefined") {
			this.isLook = _cfg.isLook;
		}
		Ext.applyIf(this, _cfg);
		this.initUIComponents();
		addPersonWorkInfo.superclass.constructor.call(this, {
					id : 'addPersonWorkInfo',
					layout : 'fit',
					autoScroll:true,
					items : [this.formPanel],
					modal : true,
					height : 300,
					width : 600,
					maximizable : true,
					title : '工作情况',
					buttonAlign : 'center',
					buttons : [{
								text : '保存',
								iconCls : 'btn-save',
								hidden : this.isLook,
								scope : this,
								handler : this.save
							}, {
								text : '取消',
								iconCls : 'btn-cancel',
								hidden : this.isLook,
								scope : this,
								handler : this.cancel
							}]
				});
	},// end of the constructor
	// 初始化组件
	initUIComponents : function() {
		var storepayintentPeriod="[";
	    for (var i = 1; i < 31; i++) {
			storepayintentPeriod = storepayintentPeriod + "[" + i+ ", " + i + "],";
		}
		storepayintentPeriod = storepayintentPeriod.substring(0,storepayintentPeriod.length - 1);
		storepayintentPeriod = storepayintentPeriod + "]";
		var obstorepayintentPeriod = Ext.decode(storepayintentPeriod);
		
		this.formPanel = new Ext.FormPanel( {
			//url :  __ctxPath + '/credit/customer/person/addSpouse.do',
			monitorValid : true,
			bodyStyle:'padding:10px',
			autoScroll : true ,
			labelAlign : 'right',
			buttonAlign : 'center',
			frame : true ,
			border : false,
			layout : 'column',
			labelWidth:100,
			items : [{
						columnWidth : .33,
						layout : 'form',
						defaults : {
							anchor : '90%'
						},
						labelWidth : 80,
						items : [{
									xtype : "hidden",
									name : "person.id"
								},{
									xtype : 'textfield',
									fieldLabel : '工作单位',
									readOnly : this.isRead,
									name : 'person.currentcompany'
								}, {
									xtype : 'textfield',
									fieldLabel : '传真',
									name : 'person.companyFax',
									readOnly : this.isRead
								}]
					}, {
						columnWidth : .33,
						layout : 'form',
						defaults : {
							anchor : '90%'
						},
						labelWidth : 80,
						items : [{
							xtype : "dickeycombo",
							nodeKey : 'unitproperties',
							hiddenName : "person.unitproperties",
							fieldLabel : "单位性质",
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
									allowBlank : this.companyHidden,
									readOnly : this.isRead

								}]
					}, {
						columnWidth : .34,
						layout : 'form',
						defaults : {
							anchor : '90%'
						},
						labelWidth : 80,
						items : [{
									xtype : 'textfield',
									fieldLabel : '单位电话',
									name : 'person.unitphone',
									allowBlank : this.companyHidden,
									readOnly : this.isRead
								}, {
							xtype : 'textfield',
							fieldLabel : '邮政编码',
							name : 'person.unitpostcode',
							regex : /^[0-9]{6}$/,
							regexText : '邮政编码格式不正确',
							//allowBlank : this.companyHidden,
							readOnly : this.isRead
						}]
					},{
						columnWidth:1,
						layout:'column',
						items:[{
							layout:'form',
							columnWidth:.66,
							labelWidth:80,
							defaults:{
								anchor:'95%'
							},
							scope:this,
							items:[{
								xtype : "combo",
								triggerClass : 'x-form-search-trigger',
								fieldLabel : "行业类别",
								name : 'person.hangyeName',
							    hiddenName : 'person.hangyeName',
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
									name:'person.hangyeType'
								}]
						},{
							columnWidth : .34,
							layout : 'form',
							defaults : {
								anchor : '90%'
							},
							labelWidth : 80,
							scope : this,
							items : [{
								xtype : "dickeycombo",
								nodeKey : 'companyScale',
								hiddenName : 'person.companyScale',
								fieldLabel : "公司规模",
								editable : true,
								readOnly : this.isRead,
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
							columnWidth : .33,
							layout : 'form',
							labelWidth : 80,
							defaults : {
								anchor : '90%'
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
							labelWidth : 80,
							scope : this,
							items : [{
										xtype : 'datefield',
										format : 'Y-m-d',
										fieldLabel : '入职时间',
										allowBlank : this.companyHidden,
										readOnly : this.isRead,
										name : 'person.jobstarttime'
									}]
						},{
							columnWidth : .34,
							layout : 'form',
							defaults : {
								anchor : '90%'
							},
							labelWidth : 80,
							scope : this,
							items : [{
								xtype : "textfield",
								//nodeKey : 'bm',
							//	hiddenName : "person.companyDepartment",
							//	itemName : '所属部门', // xx代表分类名称
								fieldLabel : "所属部门",
								name:'person.department',
								//allowBlank : this.companyHidden,
								readOnly : this.isRead,
								editable : false
							}]
							
						},{
						columnWidth : 1,
						layout : 'column',
						items : [{
							columnWidth : .33,
							layout : 'form',
							labelWidth : 80,
							defaults : {
								anchor : '90%'
							},
							scope : this,
							items : [{
										xtype : 'numberfield',
										fieldLabel : '月收入',
								        blankText : "月收入不能为空，请正确填写!",
										readOnly : this.isRead,
										name : 'person.jobincome'
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
							labelWidth : 80,
							defaults : {
								anchor : '90%'
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
							    store :new Ext.data.SimpleStore({
										fields : ["name", "value"],
										data : [["打卡", "打卡"],["现金", "现金"]]
								}),
								triggerAction : "all"
							}]
						}]
					},{
							columnWidth : .33,
							layout : 'form',
							labelWidth : 80,
							items : [{
								xtype : "combo",
								anchor : "90%",
								hiddenName : "person.parentHireCity",
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
								fieldLabel : "工作城市:省",
								listeners : {
									scope : this,
									afterrender : function(combox) {
										var st = combox.getStore();
										st.on("load", function() {
											combox.setValue(combox.getValue());
											if(combox.getValue()){
												combox.fireEvent('select',combox);
											}
										})
										combox.clearInvalid();
									},
									select : function(combox, record, index) {
										var v = record.data.itemId;
										var arrStore = new Ext.data.SimpleStore({
											url : __ctxPath+ '/creditFlow/multiLevelDic/listByParentIdAreaDic.do',
											fields : ['itemId', 'itemName'],
											baseParams:{parentId:v}
										})
									
										var opr_obj = this.getCmpByName('person.hireCity')
										opr_obj.clearValue();
										opr_obj.store = arrStore;
									
										opr_obj.getStore().load({
											"callback" : test
										});
										function test(r) {
											opr_obj.fireEvent('afterrender',opr_obj);
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
							labelWidth :80,
							items : [{
										xtype : "combo",
										anchor : "90%",
										id : "aahireCity",
										hiddenName : "person.hireCity",
										displayField : 'itemName',
										valueField : 'itemId',
										triggerAction : 'all',
										readOnly : this.isRead,
										store : new Ext.data.SimpleStore({
											url : __ctxPath+ '/creditFlow/multiLevelDic/listByParentIdAreaDic.do',
											fields : ['itemId', 'itemName']
										}),
										fieldLabel : "市",
										listeners : {
											scope : this,
												afterrender : function(combox) {
										var st = combox.getStore();
										st.on("load", function() {
										
													var record = st.getAt(0);
													var v = record.data.itemId;
													combox.setValue(v);
													combox.fireEvent("select",
															combox, record, 0);
												})
										combox.clearInvalid();
									}
								/*			afterrender : function(opr_obj) {
												var st = opr_obj.getStore();
												st.on("load", function() {
													opr_obj.setValue(opr_obj.getValue());
												})
												opr_obj.clearInvalid();
												if(opr_obj.getValue()){
													var arrStore = new Ext.data.SimpleStore({
														url : __ctxPath
																+ '/creditFlow/multiLevelDic/listByParentIdAreaDic.do',
														fields : ['itemId', 'itemName'],
														baseParams:{parentId:opr_obj.getValue()}
													});
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
											}*/
										}
							}]
						},{
							columnWidth : .34,
							layout : 'form',
							labelWidth : 80,
							defaults : {
								anchor : '90%'
							},
							items : [{
								xtype : 'textfield',
								fieldLabel : '工作邮箱',
								readOnly : this.isRead,
								value :personData==null?null:personData.hireEmail,
								regex : /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+/,
								regexText : '电子邮箱格式不正确',
								name : 'person.hireEmail',
								anchor :'90%'
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
										name : 'person.webstarttime'
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
										xtype : 'datefield',
										format : 'Y-m-d',
										fieldLabel : '执教起始时间',
										allowBlank : this.companyHidden,
										readOnly : this.isRead,
										name : 'person.techstarttime'
									}]
						},{
							columnWidth : .34,
							layout : 'form',
							defaults : {
								anchor : '90%'
							},
							labelWidth : 80,
							scope : this,
							items : [{
										xtype : 'datefield',
										format : 'Y-m-d',
										fieldLabel : '经营起始时间',
										allowBlank : this.companyHidden,
										readOnly : this.isRead,
										name : 'person.bossstarttime'
									}]
						}]
					}]
		})
		
		// this.gridPanel.addListener('rowdblclick', this.rowClick);
		// 加载表单对应的数据
		if (this.personId != null && this.personId != 'undefined') {
			var   panel =this;
			
			this.formPanel.loadData({
						url : __ctxPath + '/creditFlow/customer/person/getByIdPerson.do?personId='+ this.personId,
						root : 'data',
						preName : 'person',
						scorp:this,
						success : function(response, options) {
							var respText = response.responseText;
								var alarm_fields = Ext.util.JSON.decode(respText);
								var name = alarm_fields.data.hireCityName;
							    Ext.getCmp('aahireCity').setValue(name)
								//panel.getCmpByName('person.grossasset').setValue(Ext.util.Format.number(alarm_fields.data.grossasset,'0,000.00'));
							}
					});
		}

	},// end of the initcomponents

	/**
	 * 重置
	 * 
	 * @param {}
	 *            formPanel
	 */
	reset : function() {
		this.formPanel.getForm().reset();
		this.getCmpByName('obObligationInvestInfo.investPersonName').setValue(this.investPersonName);
	},
	/**
	 * 取消
	 * 
	 * @param {}
	 *            window
	 */
	cancel : function() {
		this.close();
	},
	/**
	 * 保存记录
	 */
	save : function() {
		var win=this;
			var personId=this.getCmpByName("person.id").getValue();
			$postForm({
						formPanel : this.formPanel,
						scope : this,
						url : __ctxPath + '/creditFlow/customer/person/savePartInfoPerson.do',
						params : {
								'id':personId
							},
						callback : function(fp, action) {
							win.close();
						}
					});
		}// end of save

});