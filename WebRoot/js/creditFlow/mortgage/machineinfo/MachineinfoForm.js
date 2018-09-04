/**
 * @author:
 * @class SlCompanyMainView
 * @extends Ext.Panel
 * @description [SlCompanyMain]管理
 * @company 北京互融时代软件有限公司
 * @createtime:
 */
MachineinfoForm = Ext.extend(Ext.Panel, {
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		// 初始化组件
		this.initUIComponents();
		// 调用父类构造
		MachineinfoForm.superclass.constructor.call(this, {
			anchor : '95%',
			items : [{
				layout : 'column',
				xtype : 'fieldset',
				title : '填写<机器设备>详细信息',
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
								fieldLabel : '设备名称',
								maxLength : 50,
								maxLengthText : '最大输入长度50',
								name : 'procreditMortgageMachineinfo.machinename'
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
								fieldLabel : '控制权人',
								maxLength : 50,
								maxLengthText : '最大输入长度50',
								name : 'procreditMortgageMachineinfo.controller'
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
								fieldLabel : '设备型号',
								maxLength : 50,
								maxLengthText : '最大输入长度50',
								name : 'procreditMortgageMachineinfo.machinetype'
							}, {
								fieldLabel : '新货价格.万元',
								maxLength : 23,
								maxLengthText : '最大输入长度23',
								name : 'procreditMortgageMachineinfo.newcarprice'
							}, {
								fieldLabel : '使用时间.年',
								maxLength : 11,
								maxLengthText : '最大输入长度11',
								name : 'procreditMortgageMachineinfo.havedusedtime'
							}, {
								fieldLabel : '二手价值1.万元',
								maxLength : 23,
								maxLengthText : '最大输入长度23',
								name : 'procreditMortgageMachineinfo.secondaryvalueone'
							}, {
								fieldLabel : '二手价值2.万元',
								maxLength : 23,
								maxLengthText : '最大输入长度23',
								name : 'procreditMortgageMachineinfo.secondaryvaluetow'
							}, {
								fieldLabel : '模型估价.万元',
								maxLength : 23,
								maxLengthText : '最大输入长度23',
								name : 'procreditMortgageMachineinfo.modelrangeprice'
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
						nodeKey : 'kzqfs',
						hiddenName : "procreditMortgageMachineinfo.controllertypeid",
						fieldLabel : "控制权方式",
						itemName : '控制权方式', // xx代表分类名称
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
						nodeKey : 'tycd',
						hiddenName : "procreditMortgageMachineinfo.commongradeid",
						fieldLabel : "通用程度",
						itemName : '通用程度', // xx代表分类名称
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
						nodeKey : 'xnzk',
						hiddenName : "procreditMortgageMachineinfo.capabilitystatusid",
						fieldLabel : "性能状况",
						itemName : '性能状况', // xx代表分类名称
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
						nodeKey : 'bxnl',
						hiddenName : "procreditMortgageMachineinfo.cashabilityid",
						fieldLabel : "变现能力",
						itemName : '变现能力', // xx代表分类名称
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
						hiddenName : "procreditMortgageMachineinfo.registerinfoid",
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
					}, {
						xtype : 'datefield',
						fieldLabel : '出厂日期',
						format : 'Y-m-d',
						name : 'procreditMortgageMachineinfo.leavefactorydate'
					},{
						xtype : 'hidden',
						name : 'procreditMortgageMachineinfo.objectType',
						value : this.objectType
					},{
						xtype : 'hidden',
						name : 'procreditMortgageMachineinfo.id'
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
				preName : ['procreditMortgage','procreditMortgageMachineinfo']
			});
		}
	},// end of constructor
	// 初始化组件
	initUIComponents : function() {
		
	
	}
	
	
});
