/**
 * @author:
 * @class SlCompanyMainView
 * @extends Ext.Panel
 * @description [SlCompanyMain]管理
 * @company 北京互融时代软件有限公司
 * @createtime:
 */
DroitForm = Ext.extend(Ext.Panel, {
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		// 初始化组件
		this.initUIComponents();
		// 调用父类构造
		DroitForm.superclass.constructor.call(this, {
			anchor : '95%',
			items : [{
				layout : 'column',
				xtype : 'fieldset',
				title : '填写<无形权利>详细信息',
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
										fieldLabel : '权利名称',
										maxLength : 50,
										maxLengthText : '最大输入长度50',
										name : 'procreditMortgageDroit.droitname'
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
										fieldLabel : '享有权利比重.%',
										maxLength : 23,
										maxLengthText : '最大输入长度23',
										name : 'procreditMortgageDroit.droitpercent'
									}, {
										fieldLabel : '已经经营权利时间.年',
										maxLength : 11,
										maxLengthText : '最大输入长度11',
										name : 'procreditMortgageDroit.dealdroittime'
									}, {
										fieldLabel : '享有权利剩余时间.年',
										maxLength : 11,
										maxLengthText : '最大输入长度11',
										name : 'procreditMortgageDroit.residualdroittime'
									}, {
										fieldLabel : '最近一年权利净收益.万元',
										maxLength : 23,
										maxLengthText : '最大输入长度23',
										name : 'procreditMortgageDroit.droitlucre'
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
								nodeKey : 'ltx',
								hiddenName : "procreditMortgageDroit.negotiabilityid",
								fieldLabel : "流通性",
								itemName : '流通性', // xx代表分类名称
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
								nodeKey : 'jyzk',
								hiddenName : "procreditMortgageDroit.dealstatusid",
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
								nodeKey : 'qlzl',
								hiddenName : "procreditMortgageDroit.droitmassid",
								fieldLabel : "权利质量",
								itemName : '权利质量', // xx代表分类名称
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
								hiddenName : "procreditMortgageDroit.registerinfoid",
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
							labelWidth : 100,
							defaults : {
								xtype : 'numberfield',
								anchor : '100%'
							},
							items : [{
								fieldLabel : '模型估价.万元',
								maxLength : 23,
								maxLengthText : '最大输入长度23',
								name : 'procreditMortgageDroit.modelrangeprice'
							},{
								xtype : 'hidden',
								name : 'procreditMortgageDroit.objectType',
								value : this.objectType
							},{
								xtype : 'hidden',
								name : 'procreditMortgageDroit.id'
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
				preName : ['procreditMortgage','procreditMortgageDroit']
			});
		}
	},// end of constructor
	// 初始化组件
	initUIComponents : function() {
		
	}
});
