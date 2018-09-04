/**
 * @author:
 * @class SlCompanyMainView
 * @extends Ext.Panel
 * @description [SlCompanyMain]管理
 * @company 北京互融时代软件有限公司
 * @createtime:
 */
VehicleForm = Ext.extend(Ext.Panel, {
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		// 初始化组件
		this.initUIComponents();
		// 调用父类构造
		VehicleForm.superclass.constructor.call(this, {
			anchor : '95%',
			items : [{
				layout : 'column',
				xtype : 'fieldset',
				title : '填写<车辆>详细信息',
				collapsible : true,
				autoHeight : true,				
				items : [{
					columnWidth : 1,
					labelWidth : 105,
					layout : 'form',
					defaults : {
						anchor : '97.5%'
					},
					items : [{
						id : 'factoryName',
						xtype : 'combo',
						hiddenName : 'manufacturer',
						triggerClass : 'x-form-search-trigger',
						onTriggerClick : function() {
							selectCarList(returnCarMessageInfo);
						},
						fieldLabel : '制造商',
						mode : 'romote',
						lazyInit : false,
						typeAhead : true,
						allowBlank : false,
						blankText : '为必填内容',
						minChars : 1,
						listWidth : 230,
						store : new Ext.data.JsonStore({
							url : __ctxPath
									+ '/credit/mortgage/ajaxQueryCarFactoryForCombo.do',
							root : 'topics',
							autoLoad : true,
							fields : [{
										name : 'id'
									}, {
										name : 'carFirmName'
									}],
							listeners : {
								'load' : function(s, r, o) {
									if (s.getCount() == 0) {
										Ext.getCmp('factoryName')
												.markInvalid('没有查找到匹配的记录');
									}
								}
							}
						}),
						displayField : 'carFirmName',
						valueField : 'id',
						triggerAction : 'all'
					}]
				}, {
					columnWidth : .5,
					layout : 'form',
					labelWidth : 105,
					defaults : {
						xtype : 'textfield',
						anchor : '100%'
					},
					items : [{
								id : 'carNumber',
								fieldLabel : '车型编号',
								readOnly : true
							}, {
								id : 'carStyleName',
								fieldLabel : '车系',
								readOnly : true
							}, {
								id : 'carModelName',
								fieldLabel : '车型',
								readOnly : true
							}, {
								fieldLabel : '发动机号',
								maxLength : 50,
								maxLengthText : '最大输入长度50',
								name : 'procreditMortgageCar.engineNo'
							}, {
								fieldLabel : '车架号',
								maxLength : 50,
								maxLengthText : '最大输入长度50',
								name : 'procreditMortgageCar.vin'
							}, {
								xtype : 'numberfield',
								fieldLabel : '新车价格.万元',
								maxLength : 23,
								maxLengthText : '最大输入长度23',
								name : 'procreditMortgageCar.carprice'
							}, {
								xtype : 'numberfield',
								fieldLabel : '里程数.公里',
								maxLength : 23,
								maxLengthText : '最大输入长度23',
								name : 'procreditMortgageCar.totalkilometres'
							}, {
								xtype : 'numberfield',
								fieldLabel : '事故次数',
								maxLength : 11,
								maxLengthText : '最大输入长度11',
								name : 'procreditMortgageCar.accidenttimes'
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
								id : 'displacement',
								fieldLabel : '排量',
								readOnly : true
							}, {
								id : 'configuration',
								fieldLabel : '配置',
								readOnly : true
							}, {
								id : 'seating',
								fieldLabel : '座位数',
								readOnly : true
							}, {
								xtype : "dickeycombo",
								nodeKey : 'clys',
								hiddenName : "procreditMortgageCar.carColor",
								fieldLabel : "车辆颜色",
								itemName : '车辆颜色', // xx代表分类名称
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
								hiddenName : "procreditMortgageCar.enregisterinfoid",
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
								xtype : 'datefield',
								fieldLabel : '出厂日期',
								format : 'Y-m-d',
								name : 'procreditMortgageCar.leavefactorydate'
							}, {
								xtype : 'numberfield',
								fieldLabel : '使用时间.年',
								maxLength : 23,
								maxLengthText : '最大输入长度23',
								name : 'procreditMortgageCar.haveusedtime'
							}, {
								xtype : "dickeycombo",
								nodeKey : 'ck',
								hiddenName : "procreditMortgageCar.carinfoid",
								fieldLabel : "车况",
								itemName : '车况', // xx代表分类名称
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
					labelWidth : 120,
					defaults : {
						xtype : 'numberfield',
						anchor : '100%'
					},
					items : [{
								fieldLabel : '市场交易价格1.万元',
								maxLength : 23,
								maxLengthText : '最大输入长度23',
								name : 'procreditMortgageCar.exchangepriceone'
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
								fieldLabel : '模型估价.万元',
								maxLength : 23,
								maxLengthText : '最大输入长度23',
								name : 'procreditMortgageCar.modelrangeprice'
							}]
				}, {
					columnWidth : .5,
					layout : 'form',
					labelWidth : 120,
					defaults : {
						xtype : 'numberfield',
						anchor : '100%'
					},
					items : [{
								fieldLabel : '市场交易价格2.万元',
								maxLength : 23,
								maxLengthText : '最大输入长度23',
								name : 'procreditMortgageCar.exchangepricetow'
							}]
				}]
			}]
		});
		if(null!=this.id && null!=this.type){
			this.loadData({
				url : __ctxPath +'/credit/mortgage/getMortgageByType.do?mortgageid='
						+ this.id+'&typeid='+this.type,
				root : 'data',
				preName : ['procreditMortgageCar','vCarDic'],
				scope : this,
				success : function(resp, options) {
					var obj=Ext.util.JSON.decode(resp.responseText)
					Ext.getCmp('factoryName').setValue(obj.data.vCarDic.id);//制造商id
					Ext.getCmp('factoryName').setRawValue(obj.data.vCarDic.carFirmName);	
					Ext.getCmp('carNumber').setValue(obj.data.vCarDic.carNumber);//车型编号
					Ext.getCmp('carStyleName').setValue(obj.data.vCarDic.carStyleName);//车系
					Ext.getCmp('carModelName').setValue(obj.data.vCarDic.carModelName);//车型
					Ext.getCmp('displacement').setValue(obj.data.vCarDic.displacementValue);//排量
					Ext.getCmp('configuration').setValue(obj.data.vCarDic.configuration);//配置
					Ext.getCmp('seating').setValue(obj.data.vCarDic.seatingValue);//座位数
				}
			});
		}
	},// end of constructor
	// 初始化组件
	initUIComponents : function() {
		

		//汽车参照
	returnCarMessageInfo = function(obj){
		Ext.getCmp('factoryName').setValue(obj.id);//制造商id
		Ext.getCmp('factoryName').setRawValue(obj.carFirmName);
		
		Ext.getCmp('carNumber').setValue(obj.carNumber);//车型编号
		Ext.getCmp('carStyleName').setValue(obj.carStyleName);//车系
		Ext.getCmp('carModelName').setValue(obj.carModelName);//车型
		Ext.getCmp('displacement').setValue(obj.displacementValue);//排量
		Ext.getCmp('configuration').setValue(obj.configuration);//配置
		Ext.getCmp('seating').setValue(obj.seatingValue);//座位数
	}
	}
});
