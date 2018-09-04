/**
 * @author:
 * @class SlCompanyMainView
 * @extends Ext.Panel
 * @description [SlCompanyMain]管理
 * @company 北京互融时代软件有限公司
 * @createtime:
 */
HousegroundForm = Ext.extend(Ext.Panel, {
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		// 初始化组件
		this.initUIComponents();
		// 调用父类构造
		HousegroundForm.superclass.constructor.call(this, {
			anchor : '95%',
			items : [{
				layout : 'column',
				xtype : 'fieldset',
				title : '填写<住宅用地>详细信息',
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
										name : 'procreditMortgageHouseground.address'
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
								name : 'procreditMortgageHouseground.certificatenumber'
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
								hiddenName : "procreditMortgageHouseground.descriptionid",
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
							}]
						}, {
							columnWidth : .5,
							layout : 'form',
							labelWidth : 160,
							defaults : {
								xtype : 'numberfield',
								anchor : '100%'
							},
							items : [{
								fieldLabel : '预规划住宅面积.㎡',
								maxLength : 23,
								maxLengthText : '最大输入长度23',
								name : 'procreditMortgageHouseground.anticipateacreage'
							}, {
								fieldLabel : '土地抵质押贷款余额.万元',
								maxLength : 23,
								maxLengthText : '最大输入长度23',
								name : 'procreditMortgageHouseground.mortgagesbalance'
							}, {
								fieldLabel : '同等土地单价1.元/㎡',
								maxLength : 23,
								maxLengthText : '最大输入长度23',
								name : 'procreditMortgageHouseground.exchangeprice'
							}, {
								fieldLabel : '同等土地单价2.元/㎡',
								maxLength : 23,
								maxLengthText : '最大输入长度23',
								name : 'procreditMortgageHouseground.exchangepriceone'
							}, {
								fieldLabel : '同等土地单价3.元/㎡',
								maxLength : 23,
								maxLengthText : '最大输入长度23',
								name : 'procreditMortgageHouseground.exchangepricetow'
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
								xtype : "dickeycombo",
								nodeKey : 'djqk',
								hiddenName : "procreditMortgageHouseground.registerinfoid",
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
								xtype : 'textfield',
								fieldLabel : '产权人',
								maxLength : 50,
								maxLengthText : '最大输入长度50',
								name : 'procreditMortgageHouseground.propertyperson'
							}, {
								fieldLabel : '占地面积.㎡',
								maxLength : 23,
								maxLengthText : '最大输入长度23',
								name : 'procreditMortgageHouseground.acreage'
							}, {
								fieldLabel : '模型估价1.万元',
								maxLength : 23,
								maxLengthText : '最大输入长度23',
								name : 'procreditMortgageHouseground.modelrangepriceone'
							}, {
								fieldLabel : '模型估价2.万元',
								maxLength : 23,
								maxLengthText : '最大输入长度23',
								name : 'procreditMortgageHouseground.modelrangepricetow'
							},{
								xtype : 'hidden',
								name : 'procreditMortgageHouseground.objectType',
								value : this.objectType
							},{
								xtype : 'hidden',
								name : 'procreditMortgageHouseground.id'
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
				preName : ['procreditMortgage','procreditMortgageHouseground']
			});
		}
	},// end of constructor
	// 初始化组件
	initUIComponents : function() {
		
	}
});
