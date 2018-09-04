/**
 * @author:
 * @class SlCompanyMainView
 * @extends Ext.Panel
 * @description [SlCompanyMain]管理
 * @company 北京互融时代软件有限公司
 * @createtime:
 */
ProductForm = Ext.extend(Ext.Panel, {
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		// 初始化组件
		this.initUIComponents();
		// 调用父类构造
		ProductForm.superclass.constructor.call(this, {
			anchor : '95%',
			items : [{
				layout : 'column',
				xtype : 'fieldset',
				title : '填写<存货/商品>详细信息',
				collapsible : true,
				autoHeight : true,
				anchor : '95%',
				items : [{
							columnWidth : 1,
							labelWidth : 105,
							layout : 'form',
							defaults : {
								anchor : '97.5%'
							},
							items : [{
										xtype : 'textfield',
										fieldLabel : '商品名称',
										maxLength : 50,
										maxLengthText : '最大输入长度50',
										name : 'procreditMortgageProduct.name'
									}]
						}, {
							columnWidth : 1,
							labelWidth : 105,
							layout : 'form',
							defaults : {
								anchor : '97.5%'
							},
							items : [{
										xtype : 'textfield',
										fieldLabel : '存放地点',
										maxLength : 50,
										maxLengthText : '最大输入长度50',
										name : 'procreditMortgageProduct.depositary'
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
										xtype : 'textfield',
										fieldLabel : '控制权人',
										maxLength : 50,
										maxLengthText : '最大输入长度50',
										name : 'procreditMortgageProduct.controller'
									}, {
										xtype : 'textfield',
										fieldLabel : '数量',
										maxLength : 11,
										maxLengthText : '最大输入长度11',
										name : 'procreditMortgageProduct.amount'
									}, {
										fieldLabel : '市场单价1.元',
										maxLength : 23,
										maxLengthText : '最大输入长度23',
										name : 'procreditMortgageProduct.priceone'
									}, {
										fieldLabel : '市场单价2.元',
										maxLength : 23,
										maxLengthText : '最大输入长度23',
										name : 'procreditMortgageProduct.pricetow'
									}, {
										fieldLabel : '模型估价.万元',
										maxLength : 23,
										maxLengthText : '最大输入长度23',
										name : 'procreditMortgageProduct.modelrangeprice'
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
										fieldLabel : '品牌',
										maxLength : 50,
										maxLengthText : '最大输入长度50',
										name : 'procreditMortgageProduct.brand'
									}, {
										fieldLabel : '型号',
										maxLength : 50,
										maxLengthText : '最大输入长度50',
										name : 'procreditMortgageProduct.type'
									}, {
										xtype : "dickeycombo",
										nodeKey : 'kzqfs',
										hiddenName : "procreditMortgageProduct.controllertypeid",
										fieldLabel : "控制权方式",
										itemName : '控制权方式', // xx代表分类名称
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
									}, {
										xtype : "dickeycombo",
										nodeKey : 'tycd',
										hiddenName : "procreditMortgageProduct.commongradeid",
										fieldLabel : "通用程度",
										itemName : '通用程度', // xx代表分类名称
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
									}, {
										xtype : "dickeycombo",
										nodeKey : 'bxnl',
										hiddenName : "procreditMortgageProduct.cashabilityid",
										fieldLabel : "变现能力",
										itemName : '变现能力', // xx代表分类名称
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
									},{
										xtype : 'hidden',
										name : 'procreditMortgageProduct.objectType',
										value : this.objectType
									},{
										xtype : 'hidden',
										name : 'procreditMortgageProduct.id'
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
				preName : ['procreditMortgage','procreditMortgageProduct']
			});
		}
	},// end of constructor
	// 初始化组件
	initUIComponents : function() {
		
	}
});
