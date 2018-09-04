/**
 * @author:
 * @class SlCompanyMainView
 * @extends Ext.Panel
 * @description [SlCompanyMain]管理
 * @company 北京互融时代软件有限公司
 * @createtime:
 */
OfficeBuildingForm = Ext.extend(Ext.Panel, {
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		// 初始化组件
		this.initUIComponents();
		// 调用父类构造
		OfficeBuildingForm.superclass.constructor.call(this, {
			anchor : '95%',
			items : [{
				layout : 'column',
				xtype : 'fieldset',
				title : '填写<商铺写字楼>详细信息',
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
								name : 'procreditMortgageOfficebuilding.houseaddress'
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
						name : 'procreditMortgageOfficebuilding.certificatenumber'
					}, {
						fieldLabel : '产权人',
						maxLength : 50,
						maxLengthText : '最大输入长度50',
						name : 'procreditMortgageOfficebuilding.propertyperson'
					}, {
						fieldLabel : '共有人',
						maxLength : 50,
						maxLengthText : '最大输入长度50',
						name : 'procreditMortgageOfficebuilding.mutualofperson'
					}, {
						xtype : 'numberfield',
						fieldLabel : '层高',
						maxLength : 11,
						maxLengthText : '最大输入长度11',
						name : 'procreditMortgageOfficebuilding.floors'
					}, {
						xtype : 'numberfield',
						fieldLabel : '建筑面积.㎡',
						maxLength : 23,
						maxLengthText : '最大输入长度23',
						name : 'procreditMortgageOfficebuilding.buildacreage'
					}, {
						xtype : 'numberfield',
						fieldLabel : '按揭余额.万元',
						maxLength : 23,
						maxLengthText : '最大输入长度23',
						name : 'procreditMortgageOfficebuilding.mortgagesbalance'
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
						hiddenName : "procreditMortgageOfficebuilding.propertyrightid",
						fieldLabel : "产权性质",
						itemName : '产权性质', // xx代表分类名称
						listeners : {
							afterrender : function(combox) {
								var st = combox.getStore();
								st.on("load", function() {
											combox.setValue(combox.getValue());
											combox.clearInvalid();
										})
							}
						}
					}, {
						xtype : "dickeycombo",
						nodeKey : 'jzsy',
						hiddenName : "procreditMortgageOfficebuilding.constructiontypeid",
						fieldLabel : "建筑式样",
						itemName : '建筑式样', // xx代表分类名称
						listeners : {
							afterrender : function(combox) {
								var st = combox.getStore();
								st.on("load", function() {
											combox.setValue(combox.getValue());
											combox.clearInvalid();
										})
							}
						}
					}, {
						xtype : "dickeycombo",
						nodeKey : 'jzjg',
						hiddenName : "procreditMortgageOfficebuilding.constructionframeid",
						fieldLabel : "建筑结构",
						itemName : '建筑结构', // xx代表分类名称
						listeners : {
							afterrender : function(combox) {
								var st = combox.getStore();
								st.on("load", function() {
											combox.setValue(combox.getValue());
											combox.clearInvalid();
										})
							}
						}
					}, {
						xtype : "dickeycombo",
						nodeKey : 'hxjg',
						hiddenName : "procreditMortgageOfficebuilding.housetypeid",
						fieldLabel : "户型结构",
						itemName : '户型结构', // xx代表分类名称
						listeners : {
							afterrender : function(combox) {
								var st = combox.getStore();
								st.on("load", function() {
											combox.setValue(combox.getValue());
											combox.clearInvalid();
										})
							}
						}
					}, {
						xtype : "dickeycombo",
						nodeKey : 'ddms',
						hiddenName : "procreditMortgageOfficebuilding.descriptionid",
						fieldLabel : "地段描述",
						itemName : '地段描述', // xx代表分类名称
						listeners : {
							afterrender : function(combox) {
								var st = combox.getStore();
								st.on("load", function() {
											combox.setValue(combox.getValue());
											combox.clearInvalid();
										})
							}
						}
					}, {
						xtype : "dickeycombo",
						nodeKey : 'djqk',
						hiddenName : "procreditMortgageOfficebuilding.registerinfoid",
						fieldLabel : "登记情况",
						itemName : '登记情况', // xx代表分类名称
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
				}, {
					columnWidth : .5,
					layout : 'form',
					labelWidth : 180,
					defaults : {
						xtype : 'numberfield',
						anchor : '100%'
					},
					items : [{
								fieldLabel : '同等房产租金1.元/月/㎡',
								maxLength : 23,
								maxLengthText : '最大输入长度23',
								name : 'procreditMortgageOfficebuilding.rentone'
							}, {
								fieldLabel : '同等房产租金2.元/月/㎡',
								maxLength : 23,
								maxLengthText : '最大输入长度23',
								name : 'procreditMortgageOfficebuilding.renttow'
							}, {
								fieldLabel : '同等房产单位单价1.元/㎡',
								maxLength : 23,
								maxLengthText : '最大输入长度23',
								name : 'procreditMortgageOfficebuilding.exchangepriceone'
							}, {
								fieldLabel : '同等房产单位单价2.元/㎡',
								maxLength : 23,
								maxLengthText : '最大输入长度23',
								name : 'procreditMortgageOfficebuilding.exchangepricetow'
							}, {
								fieldLabel : '同等房产单位单价3.元/㎡',
								maxLength : 23,
								maxLengthText : '最大输入长度23',
								name : 'procreditMortgageOfficebuilding.exchangepricethree'
							}]
				}, {
					columnWidth : .5,
					layout : 'form',
					labelWidth : 130,
					defaults : {
						xtype : 'numberfield',
						anchor : '95%'
					},
					items : [{
								xtype : 'datefield',
								fieldLabel : '建成年份',
								format : 'Y-m-d',
								name : 'procreditMortgageOfficebuilding.buildtime'
							}, {
								fieldLabel : '剩余年限.年',
								maxLength : 23,
								maxLengthText : '最大输入长度23',
								name : 'procreditMortgageOfficebuilding.residualyears'
							}, {
								fieldLabel : '新房单价.元/㎡',
								maxLength : 23,
								maxLengthText : '最大输入长度23',
								name : 'procreditMortgageOfficebuilding.exchangefinalprice'
							}, {
								fieldLabel : '租赁模型估算.万元',
								maxLength : 23,
								maxLengthText : '最大输入长度23',
								name : 'procreditMortgageOfficebuilding.leaseholdrangeprice'
							}, {
								fieldLabel : '模型估价.万元',
								maxLength : 23,
								maxLengthText : '最大输入长度23',
								name : 'procreditMortgageOfficebuilding.modelrangeprice'
							},{
								xtype : 'hidden',
								name : 'procreditMortgageOfficebuilding.objectType',
								value : this.objectType
							},{
								xtype : 'hidden',
								name : 'procreditMortgageOfficebuilding.id'
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
				preName : ['procreditMortgage','procreditMortgageOfficebuilding']
			});
		}
	},// end of constructor
	// 初始化组件
	initUIComponents : function() {
		
	}
});
