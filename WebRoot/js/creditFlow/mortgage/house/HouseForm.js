/**
 * @author:
 * @class SlCompanyMainView
 * @extends Ext.Panel
 * @description [SlCompanyMain]管理
 * @company 北京互融时代软件有限公司
 * @createtime:
 */
HouseForm = Ext.extend(Ext.Panel, {
	// 构造函数
	constructor : function(_cfg) {
		if(typeof(_cfg.id)!='undefined'){
			this.mortgageId = _cfg.id;
		}
		Ext.applyIf(this, _cfg);
		// 初始化组件
		this.initUIComponents();
		// 调用父类构造
		HouseForm.superclass.constructor.call(this, {
			anchor : '95%',
			items : [{
				layout : 'column',
				xtype : 'fieldset',
				title : '填写<住宅>详细信息',
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
										fieldLabel : '房地产地点',
										maxLength : 50,
										maxLengthText : '最大输入长度50',
										name : 'procreditMortgageHouse.houseaddress'
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
								name : 'procreditMortgageHouse.certificatenumber'
							}/*, {
								fieldLabel : '产权人',
								maxLength : 50,
								maxLengthText : '最大输入长度50',
								name : 'procreditMortgageHouse.propertyperson'
							}*/, {
								fieldLabel : '共有人',
								maxLength : 50,
								maxLengthText : '最大输入长度50',
								name : 'procreditMortgageHouse.mutualofperson'
							}, {
								xtype : 'numberfield',
								fieldLabel : '建筑面积.㎡',
								maxLength : 23,
								maxLengthText : '最大输入长度23',
								name : 'procreditMortgageHouse.buildacreage'
							}, {
								xtype : 'datefield',
								fieldLabel : '建成年份',
								format : 'Y-m-d',
								name : 'procreditMortgageHouse.buildtime'
							}, {
								xtype : 'numberfield',
								fieldLabel : '剩余年限.年',
								maxLength : 23,
								maxLengthText : '最大输入长度23',
								name : 'procreditMortgageHouse.residualyears'
							}, {
								fieldLabel : '模型估价.万元',
								maxLength : 23,
								maxLengthText : '最大输入长度23',
								name : 'procreditMortgageHouse.modelrangeprice'
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
								nodeKey : 'cqxz',
								hiddenName : "procreditMortgageHouse.propertyrightid",
								fieldLabel : "产权性质",
								itemName : '产权性质', // xx代表分类名称
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
								nodeKey : 'jzsy',
								hiddenName : "procreditMortgageHouse.constructiontypeid",
								fieldLabel : "建筑式样",
								itemName : '建筑式样', // xx代表分类名称
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
								nodeKey : 'jzjg',
								hiddenName : "procreditMortgageHouse.constructionframeid",
								fieldLabel : "建筑结构",
								itemName : '建筑结构', // xx代表分类名称
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
								nodeKey : 'hxjg',
								hiddenName : "procreditMortgageHouse.housetypeid",
								fieldLabel : "户型结构",
								itemName : '户型结构', // xx代表分类名称
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
								hiddenName : "procreditMortgageHouse.descriptionid",
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
								hiddenName : "procreditMortgageHouse.registerinfoid",
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
							labelWidth : 180,
							defaults : {
								xtype : 'numberfield',
								anchor : '100%'
							},
							items : [{
								fieldLabel : '同等房产单位单价1.元/㎡',
								maxLength : 23,
								maxLengthText : '最大输入长度23',
								name : 'procreditMortgageHouse.exchangepriceone'
							}, {
								fieldLabel : '同等房产单位单价2.元/㎡',
								maxLength : 23,
								maxLengthText : '最大输入长度23',
								name : 'procreditMortgageHouse.exchangepricetow'
							}, {
								fieldLabel : '同等房产单位单价3.元/㎡',
								maxLength : 23,
								maxLengthText : '最大输入长度23',
								name : 'procreditMortgageHouse.exchangepricethree'
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
								fieldLabel : '按揭余额.万元',
								maxLength : 23,
								maxLengthText : '最大输入长度23',
								name : 'procreditMortgageHouse.mortgagesbalance'
							}, {
								fieldLabel : '新房单价.元/㎡',
								maxLength : 23,
								maxLengthText : '最大输入长度23',
								name : 'procreditMortgageHouse.exchangefinalprice'
							},{
								xtype :'hidden',
								name : 'procreditMortgageHouse.type',
								value : this.type
							},{
								xtype : 'hidden',
								name : 'procreditMortgageHouse.objectType',
								value : this.objectType
							},{
								xtype : 'hidden',
								name : 'procreditMortgageHouse.id'
							}]
						}]
			}]
		});
		if(null!=this.mortgageId && typeof(this.mortgageId)!="undefined" && null!=this.type && typeof(this.type)!="undefined"){
			
			var url=__ctxPath +'/credit/mortgage/getMortgageByType.do?mortgageid='+ this.mortgageId+'&typeid='+this.type+'&objectType='+this.objectType
			if(this.objectType=='pawn'){
				url=__ctxPath +'/creditFlow/pawn/pawnItems/getPawnItemTypePawnItemsList.do?mortgageid='+ this.mortgageId+'&typeid='+this.type+'&objectType='+this.objectType
			}
			this.loadData({
				url :url,
				root : 'data',
				preName : ['procreditMortgage','procreditMortgageHouse']
			});
		}
	},// end of constructor
	// 初始化组件
	initUIComponents : function() {
		
	}
});
