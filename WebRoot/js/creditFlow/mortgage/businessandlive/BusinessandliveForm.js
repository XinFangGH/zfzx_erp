/**
 * @author:
 * @class SlCompanyMainView
 * @extends Ext.Panel
 * @description [SlCompanyMain]管理
 * @company 北京互融时代软件有限公司
 * @createtime:
 */
BusinessandliveForm = Ext.extend(Ext.Panel, {
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		// 初始化组件
		this.initUIComponents();
		// 调用父类构造
		BusinessandliveForm.superclass.constructor.call(this, {
			anchor : '95%',
			items : [{
				layout : 'column',
				xtype : 'fieldset',
				title : '填写<商住用地>详细信息',
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
								name : 'procreditMortgageBusinessandlive.address'
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
						name : 'procreditMortgageBusinessandlive.certificatenumber'
					}, {
						fieldLabel : '产权人',
						maxLength : 50,
						maxLengthText : '最大输入长度50',
						name : 'procreditMortgageBusinessandlive.propertyperson'
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
						hiddenName : "procreditMortgageBusinessandlive.descriptionid",
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
						hiddenName : "procreditMortgageBusinessandlive.registerinfoid",
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
					labelWidth : 150,
					defaults : {
						xtype : 'numberfield',
						anchor : '100%'
					},
					items : [{
						fieldLabel : '预规划住宅面积.㎡',
						maxLength : 23,
						maxLengthText : '最大输入长度23',
						name : 'procreditMortgageBusinessandlive.anticipateacreage'
					}, {
						fieldLabel : '土地抵质押贷款余额.万元',
						maxLength : 23,
						maxLengthText : '最大输入长度23',
						name : 'procreditMortgageBusinessandlive.mortgagesbalance'
					}, {
						fieldLabel : '同等土地单价.元/㎡',
						maxLength : 23,
						maxLengthText : '最大输入长度23',
						name : 'procreditMortgageBusinessandlive.exchangepriceground'
					}, {
						fieldLabel : '同等住宅单价.元/㎡',
						maxLength : 23,
						maxLengthText : '最大输入长度23',
						name : 'procreditMortgageBusinessandlive.exchangepricehouse'
					}]
				}, {
					columnWidth : .5,
					layout : 'form',
					labelWidth : 125,
					defaults : {
						xtype : 'numberfield',
						anchor : '95%'
					},
					items : [{
								fieldLabel : '占地面积.㎡',
								maxLength : 23,
								maxLengthText : '最大输入长度23',
								name : 'procreditMortgageBusinessandlive.acreage'
							}, {
								fieldLabel : '租赁模型估值.万元',
								maxLength : 23,
								maxLengthText : '最大输入长度23',
								name : 'procreditMortgageBusinessandlive.tenancyrangeprice'
							}, {
								fieldLabel : '销售模型估值.万元',
								maxLength : 23,
								maxLengthText : '最大输入长度23',
								name : 'procreditMortgageBusinessandlive.venditionrangeprice'
							}, {
								fieldLabel : '模型估价.万元',
								maxLength : 23,
								maxLengthText : '最大输入长度23',
								name : 'procreditMortgageBusinessandlive.modelrangeprice'
							}]
				}, {
					columnWidth : 1,
					layout : 'form',
					labelWidth : 230,
					defaults : {
						xtype : 'numberfield',
						anchor : '97.5%'
					},
					items : [{
						fieldLabel : '同等商业房屋每月出租价格.元/月/㎡',
						maxLength : 23,
						maxLengthText : '最大输入长度23',
						name : 'procreditMortgageBusinessandlive.rentpriceformonth'
					}]
				}, {
					columnWidth : 1,
					layout : 'form',
					labelWidth : 230,
					defaults : {
						xtype : 'numberfield',
						anchor : '97.5%'
					},
					items : [{
						fieldLabel : '同等商业房屋价格.元/㎡',
						maxLength : 23,
						maxLengthText : '最大输入长度23',
						name : 'procreditMortgageBusinessandlive.exchangepricebusiness'
					},{
						xtype : 'hidden',
						name : 'procreditMortgageBusinessandlive.objectType',
						value : this.objectType
					},{
						xtype : 'hidden',
						name : 'procreditMortgageBusinessandlive.id'
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
				preName : ['procreditMortgage','procreditMortgageBusinessandlive']
			});
		}
	},// end of constructor
	// 初始化组件
	initUIComponents : function() {
		
	}
});
