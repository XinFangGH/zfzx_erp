/**
 * @author:
 * @class SlCompanyMainView
 * @extends Ext.Panel
 * @description [SlCompanyMain]管理
 * @company 北京互融时代软件有限公司
 * @createtime:
 */
EducationForm = Ext.extend(Ext.Panel, {
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		// 初始化组件
		this.initUIComponents();
		// 调用父类构造
		EducationForm.superclass.constructor.call(this, {
			anchor : '95%',
			items : [{
				layout : 'column',
				xtype : 'fieldset',
				title : '填写<教育用地>详细信息',
				collapsible : true,
				autoHeight : true,
				anchor : '95%',
				items : [{
							columnWidth : 1,
							labelWidth : 100,
							layout : 'form',
							defaults : {
								anchor : '97.5%'
							},
							items : [{
										xtype : 'textfield',
										fieldLabel : '土地地点',
										maxLength : 50,
										maxLengthText : '最大输入长度50',
										name : 'procreditMortgageEducation.address'
									}]
						}, {
							columnWidth : 1,
							labelWidth : 100,
							layout : 'form',
							defaults : {
								anchor : '97.5%'
							},
							items : [{
								xtype : 'textfield',
								fieldLabel : '产权人',
								maxLength : 50,
								maxLengthText : '最大输入长度50',
								name : 'procreditMortgageEducation.propertyperson'
							}]
						}, {
							columnWidth : .5,
							layout : 'form',
							labelWidth : 100,
							defaults : {
								xtype : 'numberfield',
								anchor : '100%'
							},
							items : [{
								xtype : 'textfield',
								fieldLabel : '权证编号',
								allowBlank : false,
								maxLength : 50,
								maxLengthText : '最大输入长度50',
								name : 'procreditMortgageEducation.certificatenumber'
							}, {
								fieldLabel : '占地面积.㎡',
								maxLength : 23,
								maxLengthText : '最大输入长度23',
								name : 'procreditMortgageEducation.acreage'
							}, {
								fieldLabel : '建筑物面积.㎡',
								maxLength : 23,
								maxLengthText : '最大输入长度23',
								name : 'procreditMortgageEducation.builacreage'
							}]
						}, {
							columnWidth : .5,
							layout : 'form',
							labelWidth : 110,
							defaults : {
								xtype : 'textfield',
								anchor : '95%'
							},
							items : [{
										xtype : 'datefield',
										fieldLabel : '购买年份',
										format : 'Y-m-d',
										name : 'procreditMortgageEducation.buytime'
									}, {
										xtype : 'numberfield',
										fieldLabel : '剩余年限.年',
										maxLength : 11,
										maxLengthText : '最大输入长度11',
										name : 'procreditMortgageEducation.residualyears'
									}, {
										xtype : "dickeycombo",
										nodeKey : 'tdxz',
										hiddenName : "procreditMortgageEducation.groundcharacterid",
										fieldLabel : "土地性质",
										itemName : '土地性质', // xx代表分类名称
										listeners : {
											afterrender : function(combox) {
												var st = combox.getStore();
												st.on("load", function() {
															combox
																	.setValue(combox
																			.getValue());
															combox
																	.clearInvalid();
														})
											}
										}
									}]
						}, {
							columnWidth : .5,
							layout : 'form',
							labelWidth : 150,
							defaults : {
								xtype : 'numberfield',
								anchor : '100%'
							},
							items : [{
								fieldLabel : '土地租赁模型估值.万元',
								maxLength : 23,
								maxLengthText : '最大输入长度23',
								name : 'procreditMortgageEducation.groundtenancyrangeprice'
							}, {
								fieldLabel : '建筑物租赁模型估值.万元',
								maxLength : 23,
								maxLengthText : '最大输入长度23',
								name : 'procreditMortgageEducation.buildtenancyrangeprice'
							}]
						}, {
							columnWidth : .5,
							layout : 'form',
							labelWidth : 110,
							defaults : {
								xtype : 'csRemoteCombo',
								anchor : '95%'
							},
							items : [{
								xtype : "dickeycombo",
								nodeKey : 'ddms',
								hiddenName : "procreditMortgageEducation.descriptionid",
								fieldLabel : "地段描述",
								itemName : '地段描述', // xx代表分类名称
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
								hiddenName : "procreditMortgageEducation.registerinfoid",
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
							}]
						}, {
							columnWidth : 1,
							layout : 'form',
							labelWidth : 210,
							defaults : {
								xtype : 'numberfield',
								anchor : '97.5%'
							},
							items : [{
								fieldLabel : '同等土地每月出租价格.元/月/㎡',
								maxLength : 23,
								maxLengthText : '最大输入长度23',
								name : 'procreditMortgageEducation.groundrentpriceformonth'
							}]
						}, {
							columnWidth : 1,
							layout : 'form',
							labelWidth : 210,
							defaults : {
								xtype : 'numberfield',
								anchor : '97.5%'
							},
							items : [{
								fieldLabel : '同等建筑物每月出租价格.元/月/㎡',
								maxLength : 23,
								maxLengthText : '最大输入长度23',
								name : 'procreditMortgageEducation.buildrentpriceformonth'
							},{
								xtype : 'hidden',
								name : 'procreditMortgageEducation.objectType',
								value : this.objectType
							},{
								xtype : 'hidden',
								name : 'procreditMortgageEducation.id'
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
				url :url,
				root : 'data',
				preName : ['procreditMortgage','procreditMortgageEducation']
			});
		}
	},// end of constructor
	// 初始化组件
	initUIComponents : function() {
		
	}
});
