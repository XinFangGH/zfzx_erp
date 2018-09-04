customerbaseInfo = Ext.extend(Ext.Panel, {
	layout:"form",
	autoHeight : true,
	isRead : false,
	isDiligenceReadOnly : false,
	labelAlign : 'right',
	constructor : function(_cfg) {
		if (_cfg == null) {
			_cfg = {};
		}
		if (typeof(_cfg.isRead) != 'undefined') {
			this.isRead = _cfg.isRead;
		}
		Ext.applyIf(this, _cfg);
		this.initComponents();
		var leftlabel = 105;
			customerbaseInfo.superclass.constructor.call(this, {
				layout : "column",
				border : false,
				scope : this,
				defaults : {
					anchor : '100%',
					columnWidth : 1,
					isFormField : true,
					labelAlign : 'right',
					labelWidth : leftlabel
				},
				items : [{
							xtype : "hidden",
							name : "bpCustProsperctive.perId"
						},{
							xtype : "hidden",
							name : "bpCustProsperctive.createrId"
						}, {
							xtype : "hidden",
							name : "bpCustProsperctive.createdate"
						}, {
							xtype : "hidden",
							name : "bpCustProsperctive.companyId"
						},{
							xtype : "hidden",
							name : "bpCustProsperctive.ProsperctiveType"
						},{
							xtype : "hidden",
							name : "bpCustProsperctive.personType",
							value:this.personType
						},/*{
							columnWidth : .3, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth :leftlabel,
							labelAlign : 'right',
							hidden : true,
							items : [{
										xtype : 'combo',
										fieldLabel : '客户类型',
										allowBlank : false,
										hiddenName : 'bpCustProsperctive.customerType',
										editable : false,
										displayField : 'name',
									    valueField : 'id',
									    triggerAction : 'all',
									     mode : 'local',
									    readOnly : this.isRead,
										autoload : true,
										store : new Ext.data.SimpleStore({
											fields : ["name", "id"],
											data : [["企业", "1"],["个人", "2"]]
													}),
										anchor : '100%'
												
									}]
						},*/{
							columnWidth : .3, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth :leftlabel,
							labelAlign : 'right',
							items : [{
										xtype : 'textfield',
										fieldLabel : '客户名称',
										allowBlank : false,
										name : 'bpCustProsperctive.customerName',
										anchor : '100%',
										blankText : '中文姓名为必填内容',
										readOnly : this.isRead,
										listeners : {
											'afterrender':function(com){
											    com.clearInvalid();
											}
										}
												
									}]
						},{
							columnWidth : .3, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth :leftlabel,
							labelAlign : 'right',
							items : [{
								xtype : "dickeycombo",
								nodeKey : 'sex_key',
								hiddenName : 'bpCustProsperctive.sex',
								fieldLabel : "性别",
								anchor : '100%',
								editable : false,
								blankText : "性别不能为空，请正确填写!",
								readOnly : this.isRead,
								value : personData == null? null: personData.sex,
								listeners : {
									afterrender : function(combox) {
										var st = combox.getStore();
										st.on("load", function() {
											if (combox.getValue() == 0|| combox.getValue() == 1
													|| combox.getValue() == ""|| combox.getValue() == null) {
												combox.setValue("");
											} else {
												combox.setValue(combox.getValue());
											}
											combox.clearInvalid();
										})
									}
								}
							}]
						},{
							columnWidth : .3, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth :leftlabel,
							labelAlign : 'right',
							items : [{
								xtype : 'numberfield',
								fieldLabel : '联系电话',
								name : 'bpCustProsperctive.telephoneNumber',
								//value : personData == null? null: personData.cellphone,
								allowBlank : false,
								anchor : '100%',
								readOnly : this.isRead/*,
								regex : /^[1][3578][0-9]{9}$/,
								regexText : '联系电话1格式不正确',
								listeners : {
									scope : this,
									'blur' : function(tf){
										var telephoneNumber=tf.getValue();
										var perId=this.getCmpByName('bpCustProsperctive.perId');
										Ext.Ajax.request({
						                   url:  __ctxPath + '/creditFlow/customer/customerProsperctive/verificationBpCustProsperctive.do',
						                   method : 'POST',
						                   params : {
												telephoneNumber : telephoneNumber,
												companyId:CompanyId,
												perId:perId.getValue()
											},
						                    success : function(response,request) {
												var obj=Ext.util.JSON.decode(response.responseText);
			                            		if(!obj.msg){					                            			
			                            			Ext.ux.Toast.msg('操作信息',"该手机号码已存在，请重新输入");
			                            			tf.setValue("")
			                            		} 
					                      	}
			                             });
									}
								}*/
							}]
					}, {
							columnWidth : .3, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth :leftlabel,
							labelAlign : 'right',
							items : [{
								fieldLabel : "客户来源",
								xtype : "dickeycombo",
								hiddenName : 'bpCustProsperctive.customerChannel',
								displayField : 'itemName',
								readOnly : this.isRead,
								nodeKey : 'customer_channel',
								editable :false,
								anchor : "100%",
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
						},{
						columnWidth : .3,
						layout : 'form',
						labelWidth :leftlabel,
						labelAlign : 'right',
						items : [{
								xtype : 'textfield',
								fieldLabel : '地区',
								readOnly : this.isRead,
								name : 'bpCustProsperctive.area',
								scope:this,
								anchor : "100%",
								listeners : {
									'focus' : function() { 
									      var obj=this;
									      var getEnterAreaObjArray1=function(objArray){
									    	    
									    	   	obj.setValue(objArray[(objArray.length) - 1].text+ "_" + objArray[(objArray.length) - 2].text + "_"+ objArray[0].text);
											    obj.nextSibling().setValue(objArray[(objArray.length) - 1].id+ "," + objArray[(objArray.length) - 2].id + "," + objArray[0].id);
									    	   
									       }
										   selectDictionary('area',getEnterAreaObjArray1);
										}
									}
								}, {
									name : 'bpCustProsperctive.areaId',
									//value:enterprise==null?null:enterprise.area,
									xtype : 'hidden'
								}]
							},{
								columnWidth : .3,
								layout : 'form',
								labelWidth :leftlabel,
								labelAlign : 'right',
								items : [{
											xtype : 'textfield',
											fieldLabel : '电子邮箱',
											readOnly : this.isRead,
											anchor : "100%",
											name : 'bpCustProsperctive.email',
											regex : /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+/,
											regexText : '电子邮箱格式不正确'
										}]
							},{
								columnWidth : .6,
								layout : 'form',
								labelWidth  :leftlabel,
								labelAlign : 'right',
								items  : [{
									xtype  :'textfield',
									fieldLabel  : '所属门店',
									readOnly : true,
									anchor  : "100%",
									name  : 'bpCustProsperctive.department'
								},{
									xtype : 'hidden',
									name : 'bpCustProsperctive.departmentId'
								}]
							},{
								columnWidth  : .3,
								layout : 'form',
								labelWidth : leftlabel,
								labelAlign : 'right',
								items : [{
									xtype : 'textfield',
									fieldLabel : '邮政编码',
									readOnly : this.isRead,
									blankText : '邮政编码为必填内容',
									regex : /^[0-9]{6}$/,
									regexText : '邮政编码格式不正确',
									anchor  :'100%',
									name  :'bpCustProsperctive.postalcode'
								}]
							},/*{
							columnWidth : .5, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth :leftlabel,
							labelAlign : 'right',
							items : [{
								xtype : 'numberfield',
								fieldLabel : '年龄',
								anchor : '100%',
								name : 'bpCustProsperctive.age',
								readOnly : this.isRead
								
							}]
						},*//*{
									columnWidth : .5, // 该列在整行中所占的百分比
									layout : "form", // 从上往下的布局
									labelWidth :leftlabel,
									labelAlign : 'right',
									items : [{
										xtype : 'numberfield',
										fieldLabel : '联系电话2',
										name : 'bpCustProsperctive.telephoneNumber2',
										anchor : '100%',
										readOnly : this.isRead,
										regex : /^[1][358][0-9]{9}$/,
										regexText : '联系电话2格式不正确'										
									}]
								},*//*{
									columnWidth : .5, // 该列在整行中所占的百分比
									layout : "form", // 从上往下的布局
									labelWidth :leftlabel,
									labelAlign : 'right',
									items : [{
										xtype : 'numberfield',
										fieldLabel : '固定电话',
										anchor : '100%',
										name : 'bpCustProsperctive.fixedTelephone',
										allowBlank : true,
										readOnly : this.isRead
									}]
								},*/{
									columnWidth : .9, // 该列在整行中所占的百分比
									layout : "form", // 从上往下的布局
									labelWidth :leftlabel,
									labelAlign : 'right',
									items : [{
												xtype : 'textfield',
												fieldLabel : '通讯地址',
												anchor : '100%',
												readOnly : this.isRead,
												name : 'bpCustProsperctive.postaddress'
													}]
								}/*,{
									columnWidth : 1, // 该列在整行中所占的百分比
									layout : "form", // 从上往下的布局
									labelWidth :leftlabel,
									labelAlign : 'right',
									items : [{
										xtype : "combo",
										triggerClass : 'x-form-search-trigger',
										fieldLabel : "行业类别",
								        hiddenName : 'bpCustProsperctive.hangyeName',
										scope : this,
										anchor : '100%',
										editable :false,
										readOnly : this.isRead,
										scope:true,
										//value:enterprise==null?null:enterprise.hangyeName,
										onTriggerClick : function(e) {
											var obj = this;
											var oobbj=this.nextSibling();
											selectTradeCategory(obj,oobbj);
										}
									},{
										//id:'hangyeType',
										xtype : "hidden",
										//value:enterprise==null?null:enterprise.hangyeType,
										name : 'bpCustProsperctive.hangyeType'
										
									}]
								}*/]
			/*}]*/
		});
	},
	initComponents : function() {
	},
	cc : function() {
	}
});

/**
 * 借款客户--客户信息
 * @class customerbaseInfo
 * @extends Ext.Panel
 */
customerbaseInfoLoan = Ext.extend(Ext.Panel, {
	layout:"form",
	autoHeight : true,
	isRead : false,
	isDiligenceReadOnly : false,
	labelAlign : 'right',
	constructor : function(_cfg) {
		if (_cfg == null) {
			_cfg = {};
		}
		if (typeof(_cfg.isRead) != 'undefined') {
			this.isRead = _cfg.isRead;
		}
		Ext.applyIf(this, _cfg);
		this.initComponents();
		var leftlabel = 105;
			customerbaseInfoLoan.superclass.constructor.call(this, {
				layout : "column",
				border : false,
				scope : this,
				defaults : {
					anchor : '100%',
					columnWidth : 1,
					isFormField : true,
					labelAlign : 'right',
					labelWidth : leftlabel
				},
				items : [{
							xtype : "hidden",
							name : "bpCustProsperctive.perId"
						},{
							xtype : "hidden",
							name : "bpCustProsperctive.createrId"
						}, {
							xtype : "hidden",
							name : "bpCustProsperctive.createdate"
						}, {
							xtype : "hidden",
							name : "bpCustProsperctive.companyId"
						},{
							xtype : "hidden",
							name : "bpCustProsperctive.ProsperctiveType"
						},{
							xtype : "hidden",
							name : "bpCustProsperctive.personType",
							value:this.personType
						},{
							columnWidth : .3, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth :leftlabel,
							labelAlign : 'right',
					//		hidden : true,
							items : [{
										xtype : 'combo',
										fieldLabel : '客户类型',
										allowBlank : false,
										hiddenName : 'bpCustProsperctive.customerType',
										editable : false,
										displayField : 'name',
									    valueField : 'id',
									    triggerAction : 'all',
									     mode : 'local',
									    readOnly : this.isRead,
										autoload : true,
										store : new Ext.data.SimpleStore({
											fields : ["name", "id"],
											data : [["企业", "1"],["个人", "2"]]
													}),
										anchor : '100%'
												
									}]
						},{
							columnWidth : .3, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth :leftlabel,
							labelAlign : 'right',
							items : [{
										xtype : 'textfield',
										fieldLabel : '客户名称',
										allowBlank : false,
										name : 'bpCustProsperctive.customerName',
										anchor : '100%',
										blankText : '中文姓名为必填内容',
										readOnly : this.isRead,
										listeners : {
											'afterrender':function(com){
											    com.clearInvalid();
											}
										}
												
									}]
						},{
							columnWidth : .3, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth :leftlabel,
							labelAlign : 'right',
							items : [{
								xtype : 'textfield',
								fieldLabel : '联系电话',
								name : 'bpCustProsperctive.telephoneNumber',
								//value : personData == null? null: personData.cellphone,
								allowBlank : false,
								anchor : '100%',
								readOnly : this.isRead
						/*		regex : /^[1][3578][0-9]{9}$/,
								regexText : '联系电话1格式不正确',*/
								/*listeners : {
									scope : this,
									'blur' : function(tf){
										var telephoneNumber=tf.getValue();
										var perId=this.getCmpByName('bpCustProsperctive.perId');
										Ext.Ajax.request({
						                   url:  __ctxPath + '/creditFlow/customer/customerProsperctive/verificationBpCustProsperctive.do',
						                   method : 'POST',
						                   params : {
												telephoneNumber : telephoneNumber,
												companyId:CompanyId,
												perId:perId.getValue()
											},
						                    success : function(response,request) {
												var obj=Ext.util.JSON.decode(response.responseText);
			                            		if(!obj.msg){					                            			
			                            			Ext.ux.Toast.msg('操作信息',"该手机号码已存在，请重新输入");
			                            			tf.setValue("")
			                            		} 
					                      	}
			                             });
									}
								}*/
							}]
					}, {
							columnWidth : .3, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth :leftlabel,
							labelAlign : 'right',
							items : [{
								fieldLabel : "客户来源",
								xtype : "dickeycombo",
								hiddenName : 'bpCustProsperctive.customerChannel',
								displayField : 'itemName',
								readOnly : this.isRead,
								nodeKey : 'customer_channel',
								editable :false,
								anchor : "100%",
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
						},{
						columnWidth : .3,
						layout : 'form',
						labelWidth :leftlabel,
						labelAlign : 'right',
						items : [{
								xtype : 'textfield',
								fieldLabel : '地区',
								readOnly : this.isRead,
								name : 'bpCustProsperctive.area',
								scope:this,
								anchor : "100%",
								listeners : {
									'focus' : function() { 
									      var obj=this;
									      var getEnterAreaObjArray1=function(objArray){
									    	    
									    	   	obj.setValue(objArray[(objArray.length) - 1].text+ "_" + objArray[(objArray.length) - 2].text + "_"+ objArray[0].text);
											    obj.nextSibling().setValue(objArray[(objArray.length) - 1].id+ "," + objArray[(objArray.length) - 2].id + "," + objArray[0].id);
									    	   
									       }
										   selectDictionary('area',getEnterAreaObjArray1);
										}
									}
								}, {
									name : 'bpCustProsperctive.areaId',
									//value:enterprise==null?null:enterprise.area,
									xtype : 'hidden'
								}]
							},{
								columnWidth : .3,
								layout : 'form',
								labelWidth :leftlabel,
								labelAlign : 'right',
								items : [{
											xtype : 'textfield',
											fieldLabel : '电子邮箱',
											readOnly : this.isRead,
											anchor : "100%",
											name : 'bpCustProsperctive.email',
											regex : /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+/,
											regexText : '电子邮箱格式不正确'
										}]
							},{
								columnWidth : .6,
								layout : 'form',
								labelWidth  :leftlabel,
								labelAlign : 'right',
								items  : [{
									xtype  :'textfield',
									fieldLabel  : '所属门店',
									readOnly : true,
									anchor  : "100%",
									name  : 'bpCustProsperctive.department'
								},{
									xtype : 'hidden',
									name : 'bpCustProsperctive.departmentId'
								}]
							},{
								columnWidth  : .3,
								layout : 'form',
								labelWidth : leftlabel,
								labelAlign : 'right',
								items : [{
									xtype : 'textfield',
									fieldLabel : '邮政编码',
									readOnly : this.isRead,
									blankText : '邮政编码为必填内容',
									regex : /^[0-9]{6}$/,
									regexText : '邮政编码格式不正确',
									anchor  :'100%',
									name  :'bpCustProsperctive.postalcode'
								}]
							},/*{
							columnWidth : .5, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth :leftlabel,
							labelAlign : 'right',
							items : [{
								xtype : 'numberfield',
								fieldLabel : '年龄',
								anchor : '100%',
								name : 'bpCustProsperctive.age',
								readOnly : this.isRead
								
							}]
						},*//*{
									columnWidth : .5, // 该列在整行中所占的百分比
									layout : "form", // 从上往下的布局
									labelWidth :leftlabel,
									labelAlign : 'right',
									items : [{
										xtype : 'numberfield',
										fieldLabel : '联系电话2',
										name : 'bpCustProsperctive.telephoneNumber2',
										anchor : '100%',
										readOnly : this.isRead,
										regex : /^[1][358][0-9]{9}$/,
										regexText : '联系电话2格式不正确'										
									}]
								},*//*{
									columnWidth : .5, // 该列在整行中所占的百分比
									layout : "form", // 从上往下的布局
									labelWidth :leftlabel,
									labelAlign : 'right',
									items : [{
										xtype : 'numberfield',
										fieldLabel : '固定电话',
										anchor : '100%',
										name : 'bpCustProsperctive.fixedTelephone',
										allowBlank : true,
										readOnly : this.isRead
									}]
								},*/{
									columnWidth : .9, // 该列在整行中所占的百分比
									layout : "form", // 从上往下的布局
									labelWidth :leftlabel,
									labelAlign : 'right',
									items : [{
												xtype : 'textfield',
												fieldLabel : '通讯地址',
												anchor : '100%',
												readOnly : this.isRead,
												name : 'bpCustProsperctive.postaddress'
													}]
								}/*,{
									columnWidth : 1, // 该列在整行中所占的百分比
									layout : "form", // 从上往下的布局
									labelWidth :leftlabel,
									labelAlign : 'right',
									items : [{
										xtype : "combo",
										triggerClass : 'x-form-search-trigger',
										fieldLabel : "行业类别",
								        hiddenName : 'bpCustProsperctive.hangyeName',
										scope : this,
										anchor : '100%',
										editable :false,
										readOnly : this.isRead,
										scope:true,
										//value:enterprise==null?null:enterprise.hangyeName,
										onTriggerClick : function(e) {
											var obj = this;
											var oobbj=this.nextSibling();
											selectTradeCategory(obj,oobbj);
										}
									},{
										//id:'hangyeType',
										xtype : "hidden",
										//value:enterprise==null?null:enterprise.hangyeType,
										name : 'bpCustProsperctive.hangyeType'
										
									}]
								}*/]
			/*}]*/
		});
	},
	initComponents : function() {
	},
	cc : function() {
	}
});


salesInfo = Ext.extend(Ext.Panel, {
	layout:"form",
	autoHeight : true,
	isRead : false,
	isDiligenceReadOnly : false,
	labelAlign : 'right',
	constructor : function(_cfg) {
		if (_cfg == null) {
			_cfg = {};
		}
		if (typeof(_cfg.isRead) != 'undefined') {
			this.isRead = _cfg.isRead;
		}
		Ext.applyIf(this, _cfg);
		this.initComponents();
		var leftlabel = 105;
			salesInfo.superclass.constructor.call(this, {
				layout : "column",
				border : false,
				scope : this,
				defaults : {
					anchor : '100%',
					columnWidth : 1,
					isFormField : true,
					labelAlign : 'right',
					labelWidth : leftlabel
				},
				items : [{
							columnWidth : .45, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : leftlabel,
							items : [{
										xtype : 'numberfield',
										fieldLabel : '用款金额',
										name : 'bpCustProsperctive.loanMoney',
										anchor : '100%',
										readOnly : this.isRead,
										listeners : {
											'afterrender':function(com){
											    com.clearInvalid();
											}
										}
												
									}]
						},{
									columnWidth : .05, // 该列在整行中所占的百分比
									layout : "form", // 从上往下的布局
									labelWidth : 20,
									items : [{
												fieldLabel : "元",
												labelSeparator : '',
												anchor : "100%"
											}]
						},{
									columnWidth : .5, // 该列在整行中所占的百分比
									layout : "form", // 从上往下的布局
									labelWidth : leftlabel,
									items : [{
												xtype : 'datefield',
												fieldLabel : '用款时间',
												format : 'Y-m-d',
												name : 'bpCustProsperctive.loanDate',
												//value : personData == null? null: personData.cellphone,
												anchor : '100%',
												editable : false,
												readOnly : this.isRead									
												/*listeners : {
													'afterrender':function(com){
													    com.clearInvalid();
													}
												}*/
												
											}]
						},{
									columnWidth : .45,
									layout : 'form',
									labelWidth : leftlabel,
									defaults : {
										anchor : "100%"
									},
									items : [{
												xtype : 'textfield',
												fieldLabel : '用款期限',
												readOnly : this.isRead,
												name : 'bpCustProsperctive.loanPeriod'
											}]
						},{
									columnWidth : .05, // 该列在整行中所占的百分比
									layout : "form", // 从上往下的布局
									labelWidth : 20,
									items : [{
												fieldLabel : "月 ",
												labelSeparator : '',
												anchor : "100%"
											}]
						},{
							columnWidth : .5, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : leftlabel,
							labelAlign : 'right',
							items : [{
								fieldLabel : "贷款方式",
								xtype : "dickeycombo",
								hiddenName : 'bpCustProsperctive.loanType',
								displayField : 'itemName',
								readOnly : this.isRead,
								nodeKey : 'loan_type',
								emptyText : "请选择",
								editable :false,
								anchor : "100%",
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
						},/* {
				            	columnWidth:.5,
				                layout: 'form',
				                defaults : {anchor:anchor},
				                items :[{
				                	xtype: 'radiogroup',
					                fieldLabel: '是否有抵质押物',
					                items: [
					                    {boxLabel: '是', disabled : this.isRead, name: 'bpCustProsperctive.isMortgage', inputValue: "1",checked:true},
					                    {boxLabel: '否', disabled : this.isRead, name: 'bpCustProsperctive.isMortgage', inputValue: "2",checked:false}
					                ]
				                }]
				       },{
									columnWidth : .5,
									layout : 'form',
									labelWidth : leftlabel,
									defaults : {
										anchor : anchor
									},
									items : [{
												xtype : 'textfield',
												hideLabel:true,
												readOnly : this.isRead,
												//value:enterprise==null?null:enterprise.email,
												name : 'bpCustProsperctive.mortgageRemark'
											}]
								},*/{
									columnWidth : 1, // 该列在整行中所占的百分比
									layout : "form", // 从上往下的布局
									labelWidth : leftlabel,
									items : [{
												xtype : 'textarea',
												fieldLabel : '备注说明',
												anchor : '100%',
												readOnly : this.isRead,
												name : 'bpCustProsperctive.remark'
													}]
								}]
			/*}]*/
		});
	},
	initComponents : function() {
	},
	cc : function() {
	}
});

/**
 * 借款客户--投资信息
 * @class InvestSalesInfo
 * @extends Ext.Panel
 */
InvestSalesInfo = Ext.extend(Ext.Panel, {
	layout:"form",
	autoHeight : true,
	isRead : false,
	isDiligenceReadOnly : false,
	labelAlign : 'right',
	constructor : function(_cfg) {
		if (_cfg == null) {
			_cfg = {};
		}
		if (typeof(_cfg.isRead) != 'undefined') {
			this.isRead = _cfg.isRead;
		}
		Ext.applyIf(this, _cfg);
		var leftlabel = 105;
			InvestSalesInfo.superclass.constructor.call(this, {
				layout : "column",
				border : false,
				scope : this,
				defaults : {
					anchor : '100%',
					columnWidth : 1,
					isFormField : true,
					labelAlign : 'right',
					labelWidth : leftlabel
				},
				items : [{
							columnWidth : .3,
							layout : 'form',
							labelWidth : leftlabel,
							defaults : {
								anchor : anchor
							},
							items : [{
								xtype : 'numberfield',
								fieldLabel : '资金量(元)',
								readOnly : this.isRead,
								name : 'bpCustProsperctive.amountMoney'
							}]
						},/*{
							columnWidth : .05, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : 20,
							items : [{
								fieldLabel : "元 ",
								labelSeparator : '',
								anchor : "100%"
							}]
						},*//*{
							columnWidth : .5, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : leftlabel,
							labelAlign : 'right',
							items : [{
								xtype:'textfield',
								fieldLabel : "意向投资产品",
								readOnly : this.isRead,
								anchor : "100%",
								name:'bpCustProsperctive.intentionProduct'
							}]
						},*//*{
							columnWidth : 1,
							layout : 'form',
							labelWidth : leftlabel,
							items : [{
								xtype : 'textfield',
								fieldLabel : "投资经验",
								readOnly : this.isRead,
								anchor : '100%',
								name : 'bpCustProsperctive.investExperience'
							}]
						},*/{
							columnWidth : .3,
							layout : "form",
							labelWidth : leftlabel,
							items :[{
								xtype :'textfield',
								fieldLabel : '投资期限(月)',
								anchor : '100%',
								readOnly : this.isRead,
								name : 'bpCustProsperctive.investMonth'
							}]
						},{
							columnWidth : .3,
							layout : "form",
							labelWidth : leftlabel,
							items :[{
								xtype :'datefield',
								fieldLabel : '有效截止日',
								anchor : '100%',
								editable : false,
								readOnly : this.isRead,
								format : 'Y-m-d',
								name : 'bpCustProsperctive.validEnddate'
							}]
						},{
							columnWidth : .22,
							layout : "form",
						//	labelWidth : leftlabel,
							items :[{
								html : "有无单笔投资最低或最高额度限制:",
								anchor : "100%"
							}]
						},{
							columnWidth : .08,
							layout : "form",
							labelWidth : 1,
							items :[{
								xtype :'combo',
								fieldLabel : '',
								labelSeparator : '',
								editable : false,
								anchor : '88%',
								readOnly : this.isRead,
								triggerAction : 'all',
								hiddenName : 'bpCustProsperctive.isHaveinvest',
							    mode: 'local',
							    displayField: 'name',
							    valueField: 'id',
							    store : new Ext.data.SimpleStore({
									fields : ["name", "id"],
									data : [["有", "0"],["无", "1"]]
								})
							//	,name : 'bpCustProsperctive.isHaveinvest'
							}]
						},{
							columnWidth : .3,
							layout : "form",
							labelWidth : leftlabel,
							items :[{
								xtype :'textfield',
								fieldLabel : '风险保障要求',
								anchor : '100%',
								readOnly : this.isRead,
								name : 'bpCustProsperctive.riskRequire'
							}]
						},{
							columnWidth : .3,
							layout : "form",
							labelWidth : leftlabel,
							items :[{
								xtype :'combo',
								fieldLabel : '有无投资经验',
								anchor : '100%',
								editable : false,
								readOnly : this.isRead,
								triggerAction : 'all',
								hiddenName : 'bpCustProsperctive.isHaveinvestExperience',
							    mode: 'local',
							    displayField: 'name',
							    valueField: 'id',
							    store : new Ext.data.SimpleStore({
									fields : ["name", "id"],
									data : [["有", "0"],["无", "1"]]
								})
							//	,name : 'bpCustProsperctive.isHaveinvestExperience'
							}]
						},{
							columnWidth : .9, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : leftlabel,
							items : [{
								xtype : 'textarea',
								fieldLabel : '备注说明',
								anchor : '100%',
								readOnly : this.isRead,
								name : 'bpCustProsperctive.remark'
							}]
						}]
		});
	}
});

ProspectiveFollowView = Ext.extend(Ext.Panel, {
	layout:"form",
	autoHeight : true,
	isRead : false,
	isDiligenceReadOnly : false,
	labelAlign : 'right',
	constructor : function(_cfg) {
		if (_cfg == null) {
			_cfg = {};
		}
		if (typeof(_cfg.isRead) != 'undefined') {
			this.isRead = _cfg.isRead;
		}
		Ext.applyIf(this, _cfg);
		this.initComponents();
		var leftlabel = 105;
			CsProspectivePersonFollowView.superclass.constructor.call(this, {
				layout : "column",
				border : false,
				scope : this,
				defaults : {
					anchor : '100%',
					columnWidth : 1,
					isFormField : true,
					labelAlign : 'right',
					labelWidth : leftlabel
				},
				items : [{
									columnWidth : .5, // 该列在整行中所占的百分比
									layout : "form", // 从上往下的布局
									labelWidth : leftlabel,
									items : [{
												xtype : 'datetimefield',
												fieldLabel : '下次跟进时间',
												name : 'bpCustProsperctive.nextFollowDate',
												anchor : '100%',
												format  : 'Y-m-d H:i:s',
												readOnly : this.isRead,
												editable : false,
												minValue : new Date()
												
												
											}]
						},{
							columnWidth : .5, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : leftlabel,
							labelAlign : 'right',
							items : [{
								fieldLabel : "跟进状态",
								xtype : "dickeycombo",
								hiddenName : 'bpCustProsperctive.followUpType',
								displayField : 'itemName',
								readOnly : this.isRead,
								nodeKey : 'customer_followUpStatus',
								emptyText : "请选择",
								editable :false,
								anchor : "100%",
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
						}]
			/*}]*/
		});
	},
	initComponents : function() {
	},
	cc : function() {
	}
});

PersonRelationView = Ext.extend(Ext.Panel,{
		layout : 'anchor',
		anchor : '100%',
		name:"PersonRelationView_info",
		projectId :null,
		isHidden:false,
		isHiddenAddBtn : false,
		isHiddenDelBtn : false,
		perId:null,
		constructor : function(_cfg) {
	        if (typeof(_cfg.isHiddenAddBtn) != "undefined") {
				this.isHiddenAddBtn = _cfg.isHiddenAddBtn;
			}
			if (typeof(_cfg.isHiddenDelBtn) != "undefined") {
				this.isHiddenDelBtn = _cfg.isHiddenDelBtn;
			}
			if (typeof (_cfg.perId) != "undefined") {
				this.perId = _cfg.perId;
			}
			Ext.applyIf(this, _cfg);

			this.initUIComponents();
			PersonRelationView.superclass.constructor.call(this,
					{
						items : [ this.grid_PersonRelation ]
					})
		},
		initUIComponents : function() {
			var winObj=this;
			if(this.isHiddenDelBtn == true && this.isHiddenAddBtn == true) {
				this.isHidden = true;
			}
			var summary = new Ext.ux.grid.GridSummary();
			function totalMoney(v, params, data) {
				return '总计';
			}
			
			this.addInvestmentBar = new Ext.Toolbar({
				items : [
					{
						iconCls : 'btn-add',
						text : '增加',
						xtype : 'button',
						scope : this,
						hidden : this.isHiddenAddBtn,
						handler : this.createPersonRelation
					},new Ext.Toolbar.Separator({
						hidden : this.isHiddenDelBtn
					}), {
						iconCls : 'btn-del',
						text : '删除',
						xtype : 'button',			
						scope : this,
						hidden : this.isHiddenDelBtn,
						handler : this.deletePersonRelation
					}]
				})
	this.grid_PersonRelation = new Ext.grid.EditorGridPanel({
				border : false,
				tbar :this.addInvestmentBar ,
				id:"BpCustProspectiveRelation"+this.perId,
				autoHeight : true,
				clicksToEdit :1,
				stripeRows : true,
				enableDragDrop : false,
				viewConfig : {
					forceFit : true
				},
				sm : new Ext.grid.CheckboxSelectionModel({}),
				store : new Ext.data.Store({
							proxy : new Ext.data.HttpProxy(
									{
										url : __ctxPath+'/creditFlow/customer/customerProsperctiveRelation/listByPerIdBpCustProspectiveRelation.do?perId='+this.perId,
										method : "POST"
									}),
							reader : new Ext.data.JsonReader({
								fields : Ext.data.Record.create( [
									{
										name : 'relateId'
									},{
										name : 'relateName'
									},{
										name : 'relateship'
									},{
										name : 'relationCellPhone'
									},{
										name : 'relationShipValue'
									},{
										name : 'relateHouseDress'
									},{
										name : 'relatejob'
									},{
										name : 'isMain'
									},{
										name:'relateSex'
									}
							]),
							root : 'result'
						})
			}),
			columns : [
						new Ext.grid.CheckboxSelectionModel({hidden:this.isHidden}),
						new Ext.grid.RowNumberer(),
					{
						header : '联系人姓名',
						dataIndex : 'relateName',
						align : 'right',
						width : 127,
						editor : {
								xtype : 'textfield',
								readOnly:this.isReadOnly,
								scope:this
						}
					},{
						header : '性别',
						dataIndex : 'relateSex',
						align : 'right',
						width : 127,
						xtype:'combocolumn',
                		gridId:"BpCustProspectiveRelation"+this.perId, 
						editor : new DicKeyCombo(
								{
									allowBlank : false,
									maxLength : 128,
									editable : true,
									nodeKey : 'sex_key',
									lazyInit : true,
									lazyRender : true,
									editable:false,
									readOnly : this.isReadOnly
									//width : 200
								})
					},{
						header : '关系',
						dataIndex : 'relateship',
						align : 'right',
						width : 127,
						xtype:'combocolumn',
                		gridId:"BpCustProspectiveRelation"+this.perId,
						editor : new DicKeyCombo(
								{
									allowBlank : false,
									maxLength : 128,
									editable : true,
									nodeKey : 'gxrgx',//原来为capitaltype。两个key下的子项一样。使用czfs。
									lazyInit : true,
									lazyRender : true,
									editable:false,
									readOnly : this.isReadOnly
									//width : 200
								})
					},{
						header : '联系电话',
						dataIndex : 'relationCellPhone',
						align : 'right',
						width : 127,
						summaryType: 'sum',
						editor : {
								xtype : 'textfield',
								readOnly:this.isReadOnly
						}
					},{
						header : '职务',
						dataIndex : 'relatejob',
						align : 'right',
						width : 127,
						xtype:'combocolumn',
                		gridId:"BpCustProspectiveRelation"+this.perId,
						editor : new DicKeyCombo(
								{
									allowBlank : false,
									maxLength : 128,
									editable : true,
									nodeKey : 'zhiwujob',
									lazyInit : true,
									lazyRender : true,
									editable:false,
									readOnly : this.isReadOnly
									//width : 200
								})
					},{
						header : '是否为主联系人',
						dataIndex : 'isMain',
						align : 'right',
						width : 127,
						xtype:'combocolumn',
                		gridId:"BpCustProspectiveRelation"+this.perId,
						editor : {
								xtype:'combo',
								mode : 'local',
							    displayField : 'name',
							    valueField : 'id',
							    width : 70,
							    readOnly : this.isReadOnly,
							    store : new Ext.data.SimpleStore({
										fields : ["name", "id"],
										data : [["否", "1"],
												["是", "2"]]
								}),
								triggerAction : "all",
								listeners : {
									scope : this,
									'select' : function(combox,record,index){}
								}
						}
						
					},{
						header : '家庭住址',
						dataIndex : 'relateHouseDress',
						align : 'right',
						width : 127,
						editor : {
								xtype : 'textfield',
								readOnly:this.isReadOnly
								
						}
					}],
					listeners : {
						scope : this,
						beforeedit : function(e) {}
				    }
              });
				this.grid_PersonRelation.getStore().load();
		
		},
		createPersonRelation : function() {
			
				var gridadd = this.grid_PersonRelation;
					var storeadd = this.grid_PersonRelation.getStore();
					var count =storeadd.getCount();
				
					var keys = storeadd.fields.keys;
					var p = new Ext.data.Record();
					p.data = {};
					for ( var i = 1; i < keys.length; i++) {
						p.data[keys[2]] ='';
						
					}
					var count = storeadd.getCount() + 1;
					gridadd.stopEditing();
					storeadd.addSorted(p);
					gridadd.getView().refresh();
					gridadd.startEditing(0, 1);
			
			
			
			
		},
		deletePersonRelation:function(){
			var griddel = this.grid_PersonRelation;
			var storedel = griddel.getStore();
			var s = griddel.getSelectionModel().getSelections();
			if (s <= 0) {
				Ext.ux.Toast.msg('操作信息','请选中任何一条记录');
				return false;
			}else if(s > 1){
				Ext.ux.Toast.msg('操作信息','请选中任何一条记录');
				return false;
			}else{
				griddel.stopEditing();
				var row = s[0];
				
				Ext.Msg.confirm("提示!",'确定要删除吗？',
						function(btn) {
							if (btn == "yes") {
								griddel.stopEditing();
									var row = s[0];
									if (row.data.relateId == null || row.data.relateId == '') {
										storedel.remove(row);
										griddel.getView().refresh();
									} else {
										deleteFun(
												__ctxPath+'/creditFlow/customer/customerProsperctiveRelation/multiDelBpCustProspectiveRelation.do',
												{
													ids :row.data.relateId
													
												},
												function() {
												},0,1)
									}
									storedel.remove(row);
									griddel.getView().refresh();
								}
							}
						)
				
			}
		
		},
		getSourceGridDate: function() {
			var grid = this.grid_PersonRelation;
			var vRecords = grid.getStore().getRange(0, grid.getStore().getCount()); // 得到修改的数据（记录对象）
			var vCount = vRecords.length; // 得到记录长度
			var vDatas = '';
			if (vCount > 0) {
				// begin 将记录对象转换为字符串（json格式的字符串）
				for (var i = 0; i < vCount; i++) {
					var str = Ext.util.JSON.encode(vRecords[i].data);
					var index = str.lastIndexOf(",");
					vDatas += str + '@';
				}
				vDatas = vDatas.substr(0, vDatas.length - 1);
			}
			return vDatas;
		}
	});
	
	
BpCustProspectiveFollowRecordInfo = Ext.extend(Ext.Panel, {
	layout:"form",
	autoHeight : true,
	isRead : false,
	isDiligenceReadOnly : false,
	labelAlign : 'right',
	constructor : function(_cfg) {
		if (_cfg == null) {
			_cfg = {};
		}
		if (typeof(_cfg.isRead) != 'undefined') {
			this.isRead = _cfg.isRead;
		}
		Ext.applyIf(this, _cfg);
		this.initComponents();
		var leftlabel = 75;
			BpCustProspectiveFollowRecordInfo.superclass.constructor.call(this, {
				layout : "column",
				border : false,
				scope : this,
				defaults : {
					anchor : '100%',
					columnWidth : 1,
					isFormField : true,
					labelAlign : 'right',
					labelWidth : leftlabel
				},
				items : [{
					xtype:'hidden',
					name:'bpCustProspectiveFollowup.personType',
					value:this.personType
				},{
							columnWidth : .5, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : leftlabel,
							items : [{
								xtype : "combo",
								triggerClass : 'x-form-search-trigger',
								hiddenName : "followPersonId1",
								editable : false,
								fieldLabel : "跟进人",
								blankText : "跟进人不能为空，请正确填写!",
								allowBlank : false,
								anchor : "100%",
								readOnly :true,
								onTriggerClick : function(cc) {
									var obj = this;
									var appuerIdObj = obj.nextSibling();
									var userIds = appuerIdObj.getValue();
									if ("" == obj.getValue()) {
										userIds = "";
									}
									new UserDialog({
										userIds : userIds,
										userName : obj.getValue(),
										single : false,
										title : "选择跟进人",
										callback : function(uId, uname) {
											obj.setValue(uId);
											obj.setRawValue(uname);
											appuerIdObj.setValue(uId);
										}
									}).show();
								},
								listeners:{
									scope:this,
									'afterRender': function(combo) {
										combo.setValue(currentUserId);
										combo.setRawValue(currentUserFullName);
										var appuerIdObj = this.getCmpByName('bpCustProspectiveFollowup.followPersonId');
										appuerIdObj.setValue(currentUserId);
									}
								}
							},{
	                       	 	xtype : 'hidden',
	                        	name : 'bpCustProspectiveFollowup.followPersonId'
							}]
						}, {
							columnWidth : .5, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : leftlabel,
							items : [{
										xtype : "dickeycombo",
										nodeKey : 'comm_type',
										hiddenName : 'bpCustProspectiveFollowup.followType',
										fieldLabel : "跟进方式",
										anchor : '100%',
										allowBlank : false,
										editable : false,
										blankText : "跟进方式不能为空，请正确填写!",
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
											},
											select : function(combo, record,
													index) {}
										}
									}]
						},{
							columnWidth : .5, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : leftlabel,
							items : [{
										xtype : 'datetimefield',
										format : 'Y-m-d H:i:s',
										fieldLabel : '跟进时间',
										name : 'bpCustProspectiveFollowup.followDate',
										allowBlank : false,
										anchor : '100%',
										editable : false,
										readOnly : this.isRead,
										maxValue : new Date()
										
										
									}]
				},{
							columnWidth : .25, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : leftlabel,
							items : [{
								xtype : "dickeycombo",
								nodeKey : 'bpCustProspectiveFollowup_successRate',
								hiddenName : 'bpCustProspectiveFollowup.successRate',
								fieldLabel : "跟进成功率",
								anchor : '100%',
								allowBlank : false,
								editable : false,
								blankText : "跟进成功率不能为空，请正确填写!",
								readOnly : this.isRead,
								listeners : {
									afterrender : function(combox) {
										var st = combox.getStore();
										st.on("load", function() {
											if (combox.getValue() == 0|| combox.getValue() == 1
													|| combox.getValue() == ""|| combox.getValue() == null) {
												combox.setValue("");
											} else {
												combox.setValue(combox.getValue());
											}
											combox.clearInvalid();
										})
									},
									select : function(combo, record,index) {}
								}
							}]
				},{
					columnWidth : .25, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : leftlabel,
					items : [{
						xtype : "dickeycombo",
						nodeKey : 'bpCustProspectiveFollowup_customerSystematics',
						hiddenName : 'bpCustProspectiveFollowup.customerSystematics',
						fieldLabel : "意向客户分级",
						anchor : '100%',
						editable : false,
						readOnly : this.isRead,
						listeners : {
							afterrender : function(combox) {
								var st = combox.getStore();
								st.on("load", function() {
									if (combox.getValue() == 0|| combox.getValue() == 1
											|| combox.getValue() == ""|| combox.getValue() == null) {
										combox.setValue("");
									} else {
										combox.setValue(combox.getValue());
									}
									combox.clearInvalid();
								})
							},
							select : function(combo, record,index) {}
						}
					}]
				},{
					columnWidth : 1, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : leftlabel,
					items : [{
								xtype : 'textfield',
								fieldLabel : '跟进标题',
								anchor : '100%',
								readOnly : this.isRead,
								name : 'bpCustProspectiveFollowup.followTitle'
									}]
				},{
					columnWidth : 1, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : leftlabel,
					items : [{
								xtype : 'textarea',
								fieldLabel : '跟进内容',
								anchor : '100%',
								readOnly : this.isRead,
								name : 'bpCustProspectiveFollowup.followInfo'
									}]
				}]
		});
	},
	

	initComponents : function() {
	},
	cc : function() {
	}
});