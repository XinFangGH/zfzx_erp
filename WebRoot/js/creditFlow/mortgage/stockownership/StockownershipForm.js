/**
 * @author:
 * @class SlCompanyMainView
 * @extends Ext.Panel
 * @description [SlCompanyMain]管理
 * @company 北京互融时代软件有限公司
 * @createtime:
 */
StockownershipForm = Ext.extend(Ext.Panel, {
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		// 初始化组件
		this.initUIComponents();
		var businessType=this.businessType;
		// 调用父类构造
		StockownershipForm.superclass.constructor.call(this, {
			anchor : '95%',
			items : [{
				layout : 'column',
				xtype : 'fieldset',
				title : '填写<股权>详细信息',
				collapsible : true,
				autoHeight : true,
				anchor : '97.5%',
				items : [{
					columnWidth : 1,
					labelWidth : 90,
					layout : 'form',
					defaults : {
						anchor : '99%'
					},
					items : [{
						id : 'targetEnterpriseName',
						xtype : 'combo',
						triggerClass : 'x-form-search-trigger',
						hiddenName : 'targetEnterpriseName',
						fieldLabel : '<font color=red>*</font>目标公司名称',
						editable:false,
						onTriggerClick : function() {
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
							if (businessType == 'Financing') {
								var selectSlCompanyTargetStock=function selectSlCompanyTargetStock(obj) {
									Ext.getCmp('targetEnterpriseName').setValue(obj.companyMainId);
									Ext.getCmp('targetEnterpriseName').setRawValue(obj.corName) ;
									setCustomerNameEN(obj.companyMainId);
								}
								selectSlCompanyMain(selectSlCompanyTargetStock);
							} else {
	
								var  setEnterpriseNameStock=function setEnterpriseNameStock(obj) {
									Ext.getCmp('targetEnterpriseName').setValue(obj.id);
									Ext.getCmp('targetEnterpriseName').setRawValue(obj.enterprisename) ;
									setCustomerNameEN(obj.id);
								}
								selectEnterprise(setEnterpriseNameStock);
							}
						},
						mode : 'romote',
						lazyInit : false,
						typeAhead : true,
						forceSelection : true,
						minChars : 1,
						listWidth : 230,
						store : getStoreByBusinessTypeStock(businessType),
						/*
						 * store : new Ext.data.JsonStore({ url : __ctxPath
						 * +'/credit/customer/enterprise/ajaxQueryEnterpriseForCombo.do',
						 * root : 'topics', autoLoad : true, fields : [{ name :
						 * 'id' }, { name : 'enterprisename' }, { name :
						 * 'shortname' }], listeners : { 'load' :
						 * function(s,r,o){ if(s.getCount()==0){
						 * Ext.getCmp('targetEnterpriseName').markInvalid('没有查找到匹配的记录') ; } } }
						 * }),
						 */
						displayField : businessType == "Financing"
								? 'corName'
								: 'enterprisename',
						valueField : businessType == "Financing"
								? 'companyMainId'
								: 'id',
						triggerAction : 'all',
						listeners : {
							'select' : function(combo, record, index) {
								var id;
								if (businessType == 'Financing') {
									id = record.get('companyMainId');
								} else {
									id = record.get('id');
								}
								setCustomerNameEN(id);
							}
						}

					}]
				}, {
					columnWidth : .5,
					layout : 'form',
					labelWidth : 105,
					defaults : {
						xtype : 'numberfield',
						anchor : '100%'
					},
					items : [{
								id : 'cciaa_enStock',
								xtype : 'textfield',
								fieldLabel : '营业执照号码',
								name : 'enterprise.cciaa',
								maxLength : 50,
								maxLengthText : '最大输入长度50'
							}, {
								id : 'registermoney_enStock',
								fieldLabel : '注册资本.万元',
								maxLength : 23,
								maxLengthText : '最大输入长度23',
								name : 'enterprise.registermoney'
							}, {
								xtype : 'textfield',
								fieldLabel : '控制权人',
								name : 'procreditMortgageStockownership.stockownership',
								maxLength : 50,
								maxLengthText : '最大输入长度50'
							}, {
								fieldLabel : '股权.%',
								maxLength : 23,
								maxLengthText : '最大输入长度23',
								name : 'procreditMortgageStockownership.stockownershippercent'
							}, {
								fieldLabel : '净资产.万元',
								maxLength : 23,
								maxLengthText : '最大输入长度23',
								name : 'procreditMortgageStockownership.netassets'
							}, {
								fieldLabel : '模型估价.万元',
								maxLength : 23,
								maxLengthText : '最大输入长度23',
								name : 'procreditMortgageStockownership.modelrangeprice'
							}]
				}, {
					columnWidth : .5,
					layout : 'form',
					labelWidth : 105,
					defaults : {
						xtype : 'textfield',
						anchor : '98%'
					},
					items : [{
								id : 'hangyeTypeValue',
								xtype : "combo",
								triggerClass : 'x-form-search-trigger',
								fieldLabel : "行业类别",
								scope : this,
								onTriggerClick : function(e) {
									var obj = this;
									var oobbj = Ext.getCmp('hangyeTypeId');
									selectTradeCategory(obj, oobbj);
								}
							}, {
								id : 'hangyeTypeId',
								xtype : "hidden",
								name : 'enterprise.hangyeType'
							}, {
								id : 'registerdate_enStock',
								xtype : 'datefield',
								fieldLabel : '注册时间',
								format : 'Y-m-d',
								name : 'enterprise.registerstartdate'
							}, {
								id : 'legalperson_enStock',
								fieldLabel : '法人代表',
								maxLength : 50,
								xtype : "combo",
								maxLengthText : '最大输入长度50',
								triggerClass : 'x-form-search-trigger',
								onTriggerClick : function(e) {
									if (businessType == 'Financing') {
										selectSlPersonMain(selectSlPersonStock);
									} else {
										selectPWName(selectLegalpersonStock);
									}
								}
							}, {
								xtype : 'numberfield',
								fieldLabel : '经营时间.年',
								maxLength : 11,
								maxLengthText : '最大输入长度11',
								name : 'procreditMortgageStockownership.managementtime'
							}, {
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
							}, {
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
							}, {
								id : 'legalpersonid_enStock',
								name : 'enterprise.legalpersonid',
								xtype : 'hidden'
							},{
								xtype : 'hidden',
								name : 'procreditMortgageStockownership.objectType',
								value : this.objectType
							},{
								xtype : 'hidden',
								name : 'procreditMortgageStockownership.id'
							}]
				}]
			}]
		});
			if(null!=this.id && null!=this.type){
				var url=__ctxPath +'/credit/mortgage/getMortgageByType.do?mortgageid='+ this.id+'&typeid='+this.type+'&objectType='+this.objectType
			if(this.objectType=='pawn'){
				url=__ctxPath +'/creditFlow/pawn/pawnItems/getPawnItemTypePawnItemsList.do?mortgageid='+ this.id+'&typeid='+this.type+'&objectType='+this.objectType
			}
			this.loadData({
				url : url,
				root : 'data',
				preName : ['procreditMortgageStockownership','enterprise'],
				scope : this,
				success : function(resp, options) {
					var obj=Ext.util.JSON.decode(resp.responseText)
					Ext.getCmp('targetEnterpriseName').setValue(obj.data.enterprise.id);
					Ext.getCmp('targetEnterpriseName').setRawValue(obj.data.enterprise.enterprisename) ;
					Ext.getCmp('cciaa_enStock').setValue(obj.data.enterprise.cciaa);//营业执照号码
					Ext.getCmp('registerdate_enStock').setValue(obj.data.enterprise.registerstartdate);//注册时间
					Ext.getCmp('registermoney_enStock').setValue(obj.data.enterprise.registermoney);//注册资本
					Ext.getCmp('legalperson_enStock').setValue(obj.data.enterprise.legalpersonid) ;//法人代表id
					Ext.getCmp('legalperson_enStock').setRawValue(obj.data.enterprise.legalperson);//法人代表name
					Ext.getCmp('legalpersonid_enStock').setValue(obj.data.enterprise.legalpersonid);//传后台的参数
					
					Ext.getCmp('hangyeTypeValue').setValue(obj.data.enterprise.hangyetypevalue) ;//行业类型值
					Ext.getCmp('hangyeTypeId').setValue(obj.data.enterprise.hangyetype);//行业类型id
				}
			});
		}
	},// end of constructor
	// 初始化组件
	initUIComponents : function() {
	
		
	
	//选择目标公司名称部分
	
	
	//选择目标公司名称部分
	
	
	//法人代表
	selectLegalpersonStock = function(obj){
		Ext.getCmp('legalperson_enStock').setValue(obj.id) ;
		Ext.getCmp('legalperson_enStock').setRawValue(obj.name);
		Ext.getCmp('legalpersonid_enStock').setValue(obj.id);
	}
	
	//法人代表-我方个人主体
	selectSlPersonStock = function(obj){
		Ext.getCmp('legalperson_enStock').setValue(obj.personMainId) ;
		Ext.getCmp('legalperson_enStock').setRawValue(obj.name);
		Ext.getCmp('legalpersonid_enStock').setValue(obj.personMainId);
	}
	}
});
