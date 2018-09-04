/**
 * @author:
 * @class SlCompanyMainView
 * @extends Ext.Panel
 * @description [SlCompanyMain]管理
 * @company 北京互融时代软件有限公司
 * @createtime:
 */
IndustryForm = Ext.extend(Ext.Panel, {
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		// 初始化组件
		this.initUIComponents();
		// 调用父类构造
		IndustryForm.superclass.constructor.call(this, {
			anchor : '95%',
			items : [{
				layout : 'column',
				xtype : 'fieldset',
				title : '填写<工业用地>详细信息',
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
										name : 'procreditMortgageIndustry.address'
									}]
						}, {
							columnWidth : .5,
							layout : 'form',
							labelWidth : 100,
							defaults : {
								xtype : 'textfield',
								anchor : '100%'
							},
							items : [{
								fieldLabel : '权证编号',
								allowBlank : false,
								maxLength : 50,
								maxLengthText : '最大输入长度50',
								name : 'procreditMortgageIndustry.certificatenumber'
							}, {
								fieldLabel : '产权人',
								maxLength : 50,
								maxLengthText : '最大输入长度50',
								name : 'procreditMortgageIndustry.propertyperson'
							}, {
								xtype : 'numberfield',
								fieldLabel : '占地面积.㎡',
								maxLength : 23,
								maxLengthText : '最大输入长度23',
								name : 'procreditMortgageIndustry.occupyacreage'
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
								nodeKey : 'tdxz',
								hiddenName : "procreditMortgageIndustry.groundcharacterid",
								fieldLabel : "土地性质",
								itemName : '土地性质', // xx代表分类名称
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
								nodeKey : 'ddms',
								hiddenName : "procreditMortgageIndustry.descriptionid",
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
								hiddenName : "procreditMortgageIndustry.registerinfoid",
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
							columnWidth : .5,
							layout : 'form',
							labelWidth : 155,
							defaults : {
								xtype : 'numberfield',
								anchor : '100%'
							},
							items : [{
								fieldLabel : '同等土地价格1.元/㎡',
								maxLength : 23,
								maxLengthText : '最大输入长度23',
								name : 'procreditMortgageIndustry.exchangepriceone'
							}, {
								fieldLabel : '同等土地价格2.元/㎡',
								maxLength : 23,
								maxLengthText : '最大输入长度23',
								name : 'procreditMortgageIndustry.exchangepricetow'
							}]
						}, {
							columnWidth : .5,
							layout : 'form',
							labelWidth : 110,
							defaults : {
								xtype : 'numberfield',
								anchor : '95%'
							},
							items : [{
										xtype : 'datefield',
										fieldLabel : '购买年份',
										format : 'Y-m-d',
										name : 'procreditMortgageIndustry.buytime'
									}, {
										fieldLabel : '剩余年限.年',
										maxLength : 23,
										maxLengthText : '最大输入长度23',
										name : 'procreditMortgageIndustry.residualyears'
									}]
						}, {
							columnWidth : .5,
							layout : 'form',
							labelWidth : 155,
							defaults : {
								xtype : 'numberfield',
								anchor : '100%'
							},
							items : [{
								fieldLabel : '土地抵质押贷款余额.万元',
								maxLength : 23,
								maxLengthText : '最大输入长度23',
								name : 'procreditMortgageIndustry.mortgagesbalance'
							}, {
								fieldLabel : '每月出租价格1.元/月/㎡',
								maxLength : 23,
								maxLengthText : '最大输入长度23',
								name : 'procreditMortgageIndustry.rentpriceformonthone'
							}, {
								fieldLabel : '每月出租价格2.元/月/㎡',
								maxLength : 23,
								maxLengthText : '最大输入长度23',
								name : 'procreditMortgageIndustry.rentpriceformonthtow'
							}]
						}, {
							columnWidth : .5,
							layout : 'form',
							labelWidth : 135,
							defaults : {
								xtype : 'numberfield',
								anchor : '95%'
							},
							items : [{
								fieldLabel : '市场交易价格.元/㎡',
								maxLength : 23,
								maxLengthText : '最大输入长度23',
								name : 'procreditMortgageIndustry.exchangeprice'
							}, {
								fieldLabel : '租赁模型估值.万元',
								maxLength : 23,
								maxLengthText : '最大输入长度23',
								name : 'procreditMortgageIndustry.tenancyrangeprice'
							}, {
								fieldLabel : '销售模型估值.万元',
								maxLength : 23,
								maxLengthText : '最大输入长度23',
								name : 'procreditMortgageIndustry.venditionrangeprice'
							},{
								xtype : 'hidden',
								name : 'procreditMortgageIndustry.objectType',
								value : this.objectType
							},{
								xtype : 'hidden',
								name : 'procreditMortgageIndustry.id'
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
				preName : ['procreditMortgage','procreditMortgageIndustry']
			});
		}
	},// end of constructor
	// 初始化组件
	initUIComponents : function() {
		
	}
});
