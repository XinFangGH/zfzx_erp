//小额、担保企业信息索引
var slglEnterpriseStore_stock = new Ext.data.JsonStore({/*
	url : __ctxPath +'/credit/customer/enterprise/ajaxQueryEnterpriseForCombo.do',
	root : 'topics',
	autoLoad : true,
	fields : [{
				name : 'id'
			}, {
				name : 'enterprisename'
			}, {
				name : 'shortname'
			}],
	listeners : {
		'load' : function(s,r,o){
			if(s.getCount()==0){
				Ext.getCmp('enterpriseNameMortgage').markInvalid('没有查找到匹配的记录') ;
			}
		}
	}
*/});

//小额、担保个人信息索引
var slglPersonStore_stock = new Ext.data.JsonStore({/*
	url : __ctxPath +'/credit/customer/person/ajaxQueryPersonForCombo.do',
	root : 'topics',
	autoLoad : true,
	fields : [{
				name : 'id'
			}, {
				name : 'name'
			}],
	listeners : {
		'load' : function(s,r,o){
			if(s.getCount()==0){
				Ext.getCmp('personNameMortgage').markInvalid('没有查找到匹配的记录') ;
			}
		}
	}
*/});

//融资我方抵质押物-企业主体信息索引
var flCompanyMainStore_stock = new Ext.data.JsonStore({/*
	url : __ctxPath + "/creditFlow/ourmain/queryListForComboSlCompanyMain.do",
	root : 'topics',
	autoLoad : true,
	fields : [{
				name : 'companyMainId'
			}, {
				name : 'corName'
			}, {
				name : 'simpleName'
			}],
	listeners : {
		'load' : function(s,r,o){
			if(s.getCount()==0){
				Ext.getCmp('enterpriseNameMortgage').markInvalid('没有查找到匹配的记录') ;
			}
		}
	}
*/});

//融资我方抵质押物-个人主体信息索引
var flPersonMainStore_stock = new Ext.data.JsonStore({/*
	url : __ctxPath + "/creditFlow/ourmain/queryListForComboSlPersonMain.do",
	//url : __ctxPath +'/credit/customer/person/ajaxQueryPersonForCombo.do',
	root : 'topics',
	autoLoad : true,
	fields : [{
				name : 'personMainId'
			}, {
				name : 'name'
			}],
	listeners : {
		'load' : function(s,r,o){
			if(s.getCount()==0){
				Ext.getCmp('personNameMortgage').markInvalid('没有查找到匹配的记录') ;
			}
		}
	}
*/});
var addMortgageStockOwnerShip = function(piKey,personTypeParams,assuretypeidParams,refreshMortgageGridStore,businessType){
	var anchor = '100%';
	
	var showStockOwnerShipWin = function(){
		var topBar = new Ext.Toolbar({
			border : false,
			items : [{
				text : '保存',
				iconCls : 'btn-save',
				formBind : true,
				handler : function() {
					var mortgageName = Ext.getCmp('mortgageName').getValue();
					var personNameMortgage = Ext.getCmp('personNameMortgage').getValue();
					var enterpriseNameMortgage = Ext.getCmp('enterpriseNameMortgage').getValue();
					var registerdate_enStock = Ext.getCmp('registerdate_enStock').getValue();
					var targetEnterpriseName = Ext.getCmp('targetEnterpriseName').getValue();
					
					if(mortgageName == ""){
						Ext.ux.Toast.msg('状态','请输入<抵质押物名称>后再保存!');
					}else if(enterpriseNameMortgage == "" && personNameMortgage == ""){
						Ext.ux.Toast.msg('状态','请选择<所有权人>后再保存!');
					}else if(targetEnterpriseName==""){
						Ext.ux.Toast.msg('状态','请选择<目标公司名称>后再保存!');
					}else{
						panel_addStockownership.getForm().submit({
							method : 'POST',
							waitTitle : '连接',
							waitMsg : '消息发送中...',
							success : function(form, action) {
								Ext.ux.Toast.msg('操作信息', '保存成功!');
								Ext.getCmp('mortgage_add_win').destroy();
								Ext.getCmp('window_addStockOwnerShip').destroy();
								refreshMortgageGridStore();
							},
							failure : function(form, action) {
								Ext.ux.Toast.msg('操作信息', '保存失败!');
							}
						});
					}
				}
			}]
		})
		var panel_addStockownership = new Ext.form.FormPanel({
		id :'addStockownership',
		url : __ctxPath +'/credit/mortgage/addStockownership.do',
		monitorValid : true,
		bodyStyle:'padding:10px',
		autoScroll : true ,
		labelAlign : 'right',
		buttonAlign : 'center',
		frame : true ,
		border : false,
		items : [{
				layout : 'column',
				xtype:'fieldset',
	            title: '填写<抵质押物>基础信息',
	            collapsible: true,
	            autoHeight:true,
	            anchor : '95%',
				items : [{
					columnWidth : .5,
					layout : 'form',
					labelWidth : 105,
					defaults : {anchor : '100%'},
					items : [{
		        		xtype : 'hidden',
		        		name : 'projectId',
		        		value : piKey
		        	},{
		        		xtype : 'hidden',
		        		name : 'procreditMortgage.businessType',
		        		value : businessType
		        	},{
						xtype : 'textfield',
						fieldLabel : '抵质押物类型',
						value : '股权',
						readOnly : true
					},{
						id : 'persontype_id',
						xtype : "dickeycombo",
						nodeKey : 'syrlx',
						hiddenName : "procreditMortgage.personType",
						fieldLabel : "所有人类型",
						itemName : '所有人类型', // xx代表分类名称
						value : personTypeParams,
						listeners : {
							afterrender : function(combox) {
								var st = combox.getStore();
								st.on("load", function() {
									combox.setValue(combox
											.getValue());
									combox.clearInvalid();
								})
							},
							'select' : function(combo,record, index){
								if(combo.getValue()==602){
									hideField(Ext.getCmp('personNameMortgage')) ;
									Ext.getCmp('personNameMortgage').disable() ;
									showField(Ext.getCmp('enterpriseNameMortgage')) ;
									Ext.getCmp('enterpriseNameMortgage').enable() ;
								}else{
									hideField(Ext.getCmp('enterpriseNameMortgage')) ;
									Ext.getCmp('enterpriseNameMortgage').disable() ;
									showField(Ext.getCmp('personNameMortgage')) ;
									Ext.getCmp('personNameMortgage').enable() ;
								}
							}
						}
					}]
				},{
					columnWidth : .5,
					labelWidth : 110,
					layout : 'form',
					defaults : {anchor : '95%'},
					items : [{
						xtype : "dickeycombo",
						nodeKey : 'dblx',
						hiddenName : "procreditMortgage.assuretypeid",
						fieldLabel : "担保类型",
						itemName : '担保类型', // xx代表分类名称
						value : assuretypeidParams,
						listeners : {
							afterrender : function(combox) {
								var st = combox.getStore();
								st.on("load", function() {
									combox.setValue(combox
											.getValue());
									combox.clearInvalid();
								})
							}
						}
					},{
						xtype : "dickeycombo",
						nodeKey : 'bzfs',
						hiddenName : "procreditMortgage.assuremodeid",
						fieldLabel : "保证方式",
						itemName : '保证方式', // xx代表分类名称
						listeners : {
							afterrender : function(combox) {
								var st = combox.getStore();
								st.on("load", function() {
									combox.setValue(combox
											.getValue());
									combox.clearInvalid();
								})
							}
						}
					}]
				},{
					columnWidth : 1,
					labelWidth : 105,
					layout : 'form',
					defaults : {anchor : '97.5%'},
					items : [{
						id : 'enterpriseNameMortgage',
						xtype : 'combo',
						triggerClass :'x-form-search-trigger',
						hiddenName : 'customerEnterpriseName',
						fieldLabel : '<font color=red>*</font>所有权人',
						editable:false,
						onTriggerClick : function(){
							//selectEnterprise(selectCustomer);
							if(businessType=='Financing'){
								selectSlCompanyMain(selectSlCompanyStock);
							}else{
								selectEnterprise(selectCustomer);
							}
	                   },
						mode : 'romote',
						lazyInit : false,
						allowBlank : false,
						typeAhead : true,
						forceSelection :true,
						minChars : 1,
						listWidth : 230,
						store : getStoreByBusinessType(businessType,'enterprise'),
						//store : businessType=="Financing"?flCompanyMainStore_stock:slglEnterpriseStore_stock,
						/*store : new Ext.data.JsonStore({
							url : __ctxPath +'/credit/customer/enterprise/ajaxQueryEnterpriseForCombo.do',
							root : 'topics',
							autoLoad : true,
							fields : [{
										name : 'id'
									}, {
										name : 'enterprisename'
									}, {
										name : 'shortname'
									}],
							listeners : {
								'load' : function(s,r,o){
									if(s.getCount()==0){
										Ext.getCmp('enterpriseNameMortgage').markInvalid('没有查找到匹配的记录') ;
									}
								}
							}
						}),*/
						displayField : businessType=="Financing"?'corName':'enterprisename',
						valueField : businessType=="Financing"?'companyMainId':'id',
						triggerAction : 'all'
					},{
						id : 'personNameMortgage',
						xtype : 'combo',
						hiddenName : 'customerPersonName',
						triggerClass :'x-form-search-trigger',
						editable:false,
						onTriggerClick : function(){
							//selectPWName(selectCustomer);
							if(businessType=='Financing'){
								selectSlPersonMain(selectSlPersonStock1);
							}else{
								selectPWName(selectCustomer);
							}
		               	},
						fieldLabel : '<font color=red>*</font>所有权人',
						mode : 'romote',
						lazyInit : false,
						typeAhead : true,
						minChars : 1,
						listWidth : 230,
						store : getStoreByBusinessType(businessType,'person'),
						//store : businessType=="Financing"?flPersonMainStore_stock:slglPersonStore_stock,
						/*store : new Ext.data.JsonStore({
							url : __ctxPath +'/credit/customer/person/ajaxQueryPersonForCombo.do',
							root : 'topics',
							autoLoad : true,
							fields : [{
										name : 'id'
									}, {
										name : 'name'
									}],
							listeners : {
								'load' : function(s,r,o){
									if(s.getCount()==0){
										Ext.getCmp('personNameMortgage').markInvalid('没有查找到匹配的记录') ;
									}
								}
							}
						}),*/
						displayField : 'name',
						valueField : businessType=="Financing"?'personMainId':'id',
						triggerAction : 'all'
					}]
				},{
					columnWidth : 1,
					labelWidth : 105,
					layout : 'form',
					defaults : {anchor : '97.5%'},
					items : [{
						id : 'mortgageName',
						xtype : 'textfield',
						fieldLabel : '<font color=red>*</font>抵质押物名称',
						name : 'procreditMortgage.mortgagename',
						maxLength : 50,
						maxLengthText : '最大输入长度50',
						blankText : '为必填内容'
					}]
				},{
					columnWidth : .5,
					layout : 'form',
					labelWidth : 105,
					defaults : {xtype : 'numberfield',anchor : '100%'},
					items : [{
						fieldLabel : '最终估价.万元',
						maxLength : 23,
						maxLengthText : '最大输入长度23',
						name : 'procreditMortgage.finalprice'
					},{
						fieldLabel : '可担保额度.万元',
						width : 90,
						maxLength : 23,
						maxLengthText : '最大输入长度23',
						name : 'procreditMortgage.assuremoney'
					}]
				},{
					columnWidth : .5,
					layout : 'form',
					labelWidth : 160,
					defaults : {xtype : 'textfield',anchor : '95%'},
					items : [{
						fieldLabel : '所有权人与借款人的关系',
						name : 'procreditMortgage.relation',
						maxLength : 50,
						maxLengthText : '最大输入长度50'
					}]
				},{
					columnWidth : 1,
					layout : 'form',
					labelWidth : 105,
					defaults : {anchor : '97.5%'},
					items : [{
						xtype : 'textarea',
						fieldLabel : '备注',
						maxLength : 100,
						maxLengthText : '最大输入长度100',
						name : 'procreditMortgage.remarks'
					}]
				}]
			},{
			layout : 'column',
			xtype:'fieldset',
            title: '填写<股权>详细信息',
            collapsible: true,
            autoHeight:true,
            anchor : '97.5%',
            items : [{
				columnWidth : 1,
				labelWidth : 90,
				layout : 'form',
				defaults : {anchor : '99%'},
				items : [{
					id : 'targetEnterpriseName',
					xtype : 'combo',
					triggerClass :'x-form-search-trigger',
					hiddenName : 'targetEnterpriseName',
					fieldLabel : '<font color=red>*</font>目标公司名称',
					onTriggerClick : function(){
						//selectEnterprise(setEnterpriseNameStock);
						if(businessType=='Financing'){
							selectSlCompanyMain(selectSlCompanyTargetStock);
						}else{
							selectEnterprise(setEnterpriseNameStock);
						}
                   },
					mode : 'romote',
					lazyInit : false,
					typeAhead : true,
					forceSelection :true,
					minChars : 1,
					listWidth : 230,
					store : getStoreByBusinessTypeStock(businessType),
					/*store : new Ext.data.JsonStore({
						url : __ctxPath +'/credit/customer/enterprise/ajaxQueryEnterpriseForCombo.do',
						root : 'topics',
						autoLoad : true,
						fields : [{
									name : 'id'
								}, {
									name : 'enterprisename'
								}, {
									name : 'shortname'
								}],
						listeners : {
							'load' : function(s,r,o){
								if(s.getCount()==0){
									Ext.getCmp('targetEnterpriseName').markInvalid('没有查找到匹配的记录') ;
								}
							}
						}
					}),*/
					displayField : businessType=="Financing"?'corName':'enterprisename',
					valueField : businessType=="Financing"?'companyMainId':'id',
					triggerAction : 'all',
					listeners : {
						'select' : function(combo,record,index){
							var id;
							if(businessType=='Financing'){
								id = record.get('companyMainId');
							}else{
								id = record.get('id');
							}
							setCustomerNameEN(id);
						}
					}
				
				}]
            },{
            	columnWidth : .5,
				layout : 'form',
				labelWidth : 105,
				defaults : {xtype : 'numberfield',anchor : '100%'},
				items : [{
					id : 'cciaa_enStock',
					xtype : 'textfield',
					fieldLabel : '营业执照号码',
					name : 'enterprise.cciaa',
					maxLength : 50,
					maxLengthText : '最大输入长度50'
				},{
					id : 'registermoney_enStock',
					fieldLabel : '注册资本.万元',
					maxLength : 23,
					maxLengthText : '最大输入长度23',
					name : 'enterprise.registermoney'
				},{
					xtype : 'textfield',
					fieldLabel : '控制权人',
					name : 'procreditMortgageStockownership.stockownership',
					maxLength : 50,
					maxLengthText : '最大输入长度50'
				},{
					fieldLabel : '股权.%',
					maxLength : 23,
					maxLengthText : '最大输入长度23',
					name : 'procreditMortgageStockownership.stockownershippercent'
				},{
					fieldLabel : '净资产.万元',
					maxLength : 23,
					maxLengthText : '最大输入长度23',
					name : 'procreditMortgageStockownership.netassets'
				},{
					fieldLabel : '模型估价.万元',
					maxLength : 23,
					maxLengthText : '最大输入长度23',
					name : 'procreditMortgageStockownership.modelrangeprice'
				}]
            },{
            	columnWidth : .5,
				layout : 'form',
				labelWidth : 105,
				defaults : {xtype : 'textfield',anchor : '98%'},
				items : [{
						id : 'hangyeTypeValue',
						xtype : "combo",
						triggerClass : 'x-form-search-trigger',
						fieldLabel : "行业类别",
						scope : this,
						onTriggerClick : function(e) {
							var obj = this;
							var oobbj=Ext.getCmp('hangyeTypeId');
							selectTradeCategory(obj,oobbj);
						}
				},{
					id : 'hangyeTypeId',
					xtype : "hidden",
					name : 'enterprise.hangyeType'
				},{
					id : 'registerdate_enStock',
					xtype : 'datefield',
					fieldLabel : '注册时间',
					format : 'Y-m-d',
					name : 'enterprise.registerstartdate'
				},{
					id : 'legalperson_enStock',
					fieldLabel : '法人代表',
					maxLength : 50,
					xtype : "combo",
					maxLengthText : '最大输入长度50',
					triggerClass : 'x-form-search-trigger',
					onTriggerClick : function(e) {
						//selectPWName(selectLegalpersonStock);
						if(businessType=='Financing'){
							selectSlPersonMain(selectSlPersonStock);
						}else{
							selectPWName(selectLegalpersonStock);
						}
					}
				},{
					xtype : 'numberfield',
					fieldLabel : '经营时间.年',
					maxLength : 11,
					maxLengthText : '最大输入长度11',
					name : 'procreditMortgageStockownership.managementtime'
				},{
					xtype : "dickeycombo",
					nodeKey : 'jyzk',
					hiddenName : "procreditMortgageStockownership.managementstatusid",
					fieldLabel : "经营状况",
					itemName : '经营状况', // xx代表分类名称
					listeners : {
						afterrender : function(combox) {
							var st = combox.getStore();
							st.on("load", function() {
								combox.setValue(combox
										.getValue());
								combox.clearInvalid();
							})
						}
					}
				},{
					xtype : "dickeycombo",
					nodeKey : 'djqk',
					hiddenName : "procreditMortgageStockownership.registerinfoid",
					fieldLabel : "登记情况",
					itemName : '登记情况', // xx代表分类名称
					listeners : {
						afterrender : function(combox) {
							var st = combox.getStore();
							st.on("load", function() {
								combox.setValue(combox
										.getValue());
								combox.clearInvalid();
							})
						}
					}
				},{
					id : 'legalpersonid_enStock',
	            	name : 'enterprise.legalpersonid',
	            	xtype : 'hidden'
				}]
            }]
		}],
		tbar : topBar
	});
	
	var window_addStockOwnerShip = new Ext.Window({
		id : 'window_addStockOwnerShip',
		title :'新增股权信息',
		iconCls : 'btn-add',
		width : (screen.width-180)*0.63,
		height : 460,
		constrainHeader : true ,
		collapsible : true, 
		frame : true ,
		border : false ,
		resizable : true,
		layout:'fit',
		autoScroll : true ,
		bodyStyle:'overflowX:hidden',
		constrain : true ,
		closable : true,
		modal : true,
		buttonAlign: 'right',
		items : [panel_addStockownership],
		listeners : {
			'beforeclose' : function(){
				if(panel_addStockownership.getForm().isDirty()){
					Ext.Msg.confirm('操作提示','是否保存当前新添加的数据?',function(btn){
						if(btn=='yes'){
							panel_addStockownership.getTopToolbar().items.itemAt(0).handler.call() ;
						}else{
							panel_addStockownership.getForm().reset() ;
							window_addStockOwnerShip.destroy() ;
						}
					}) ;
					return false ;
				}
			}
		}
	});
	window_addStockOwnerShip.show();
	}
	
	showStockOwnerShipWin();
	
	//选择自然人-法人部分
	function selectCustomer(obj) {
		if (obj.shortname) {//企业
			Ext.getCmp('enterpriseNameMortgage').setValue(obj.id);
			Ext.getCmp('enterpriseNameMortgage').setRawValue(obj.enterprisename) ;
		} else if (obj.name) {//个人
			Ext.getCmp('personNameMortgage').setValue(obj.id);
			Ext.getCmp('personNameMortgage').setRawValue(obj.name) ;
		}
	}
	
	function setCustomerNameEN(enterpriseId){
		Ext.Ajax.request({
			url : __ctxPath +'/creditFlow/customer/enterprise/ajaxSeeEnterprise.do',
			method : 'POST',
			success : function(response,request) {
				var objEnStock = Ext.util.JSON.decode(response.responseText);
				var enObjectStock = objEnStock.data;
				var cciaa;
				var registerstartdate;
				var registermoney;
				var legalpersonid;
				var legalperson;
				var hangyetypevalue;
				var hangyetype;
				
				if(businessType=='Financing'){
					cciaa = enObjectStock.businessCode;
					registerstartdate = enObjectStock.registerStartDate;
					registermoney = enObjectStock.registerMoney;
					legalpersonid = enObjectStock.personMainId;
					legalperson = enObjectStock.lawName;
					hangyetypevalue = enObjectStock.hangyeTypeValue;
					hangyetype = enObjectStock.hangyeType;
				}else{
					cciaa = enObjectStock.cciaa;
					registerstartdate = enObjectStock.registerstartdate;
					registermoney = enObjectStock.registermoney;
					legalpersonid = enObjectStock.legalpersonid;
					legalperson = enObjectStock.legalperson;
					hangyetypevalue = enObjectStock.hangyetypevalue;
					hangyetype = enObjectStock.hangyetype;
				}
				Ext.getCmp('cciaa_enStock').setValue(cciaa);//营业执照号码
				Ext.getCmp('registerdate_enStock').setValue(registerstartdate);//注册时间
				Ext.getCmp('registermoney_enStock').setValue(registermoney);//注册资本
				Ext.getCmp('legalperson_enStock').setValue(legalpersonid) ;//法人代表id
				Ext.getCmp('legalperson_enStock').setRawValue(legalperson);//法人代表name
				Ext.getCmp('legalpersonid_enStock').setValue(legalpersonid);//传后台的参数
				
				Ext.getCmp('hangyeTypeValue').setValue(hangyetypevalue) ;//行业类型值
				Ext.getCmp('hangyeTypeId').setValue(hangyetype);//行业类型id
			},
			failure : function(response) {					
					Ext.Msg.alert('状态','操作失败，请重试');		
			},
			params: { id: enterpriseId,businessType : businessType }
		});	
	};
	
	//选择目标公司名称部分
	function setEnterpriseNameStock(obj) {
		Ext.getCmp('targetEnterpriseName').setValue(obj.id);
		Ext.getCmp('targetEnterpriseName').setRawValue(obj.enterprisename) ;
		setCustomerNameEN(obj.id);
	}
	
	//选择目标公司名称部分
	function selectSlCompanyTargetStock(obj) {
		Ext.getCmp('targetEnterpriseName').setValue(obj.companyMainId);
		Ext.getCmp('targetEnterpriseName').setRawValue(obj.corName) ;
		setCustomerNameEN(obj.companyMainId);
	}
	
	//法人代表
	var selectLegalpersonStock = function(obj){
		Ext.getCmp('legalperson_enStock').setValue(obj.id) ;
		Ext.getCmp('legalperson_enStock').setRawValue(obj.name);
		Ext.getCmp('legalpersonid_enStock').setValue(obj.id);
	}
	
	//法人代表-我方个人主体
	var selectSlPersonStock = function(obj){
		Ext.getCmp('legalperson_enStock').setValue(obj.personMainId) ;
		Ext.getCmp('legalperson_enStock').setRawValue(obj.name);
		Ext.getCmp('legalpersonid_enStock').setValue(obj.personMainId);
	}
	
	function selectSlCompanyStock(obj) {
		Ext.getCmp('enterpriseNameMortgage').setValue(obj.companyMainId);
		Ext.getCmp('enterpriseNameMortgage').setRawValue(obj.corName) ;
	}
	
	function selectSlPersonStock1(obj){
		Ext.getCmp('personNameMortgage').setValue(obj.personMainId);
		Ext.getCmp('personNameMortgage').setRawValue(obj.name) ;
	}
	
	if(personTypeParams == 602){
		hideField(Ext.getCmp('personNameMortgage')) ;
		showField(Ext.getCmp('enterpriseNameMortgage')) ;
	}else if(personTypeParams == 603){
		hideField(Ext.getCmp('enterpriseNameMortgage')) ;
		showField(Ext.getCmp('personNameMortgage')) ;
	}
}