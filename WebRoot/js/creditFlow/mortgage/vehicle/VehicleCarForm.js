/**
 * @author
 * @class SlCarView
 * @extends Ext.Panel
 * @description [SlCar]管理
 * @company 智维软件
 * @createtime:
 */
VehicleCarForm = Ext.extend(Ext.Panel, {
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		// 初始化组件
		this.initUIComponents();
		// 调用父类构造
		VehicleCarForm.superclass.constructor.call(this, {
			anchor : '95%',
			items : [{
				layout : 'column',
				xtype : 'fieldset',
				title : '填写<车辆>详细信息',
				collapsible : true,
				autoHeight : true,				
				items : [{
					columnWidth : 1,
					labelWidth : 110,
					layout : 'form',
					defaults : {
						anchor : '97.5%'
					},
					items : [{
								xtype : 'textfield',
								fieldLabel : '制造商',
								allowBlank : false,
								name : 'procreditMortgageCar.carManufacturer'//accidenttimes
							}]
				}, {
					columnWidth : .5,
					layout : 'form',
					labelWidth : 110,
					defaults : {
						xtype : 'textfield',
						anchor : '100%'
					},
					items : [{
								xtype : 'textfield',
								fieldLabel : '车型编号',
								name : 'procreditMortgageCar.carNumber'
							}, {
								xtype : 'textfield',
								fieldLabel : '车系',
								allowBlank : false,
								name : 'procreditMortgageCar.carStyle'
							}, {
								xtype : 'textfield',
								fieldLabel : '车型',
								allowBlank : false,
								name : 'procreditMortgageCar.carModel'
							}, {
								fieldLabel : '发动机号',
								maxLength : 50,
								maxLengthText : '最大输入长度50',
								allowBlank : false,
								name : 'procreditMortgageCar.engineNo'
							}, {
								fieldLabel : '车架号',
								maxLength : 50,
								maxLengthText : '最大输入长度50',
								allowBlank : false,
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
							},{
								fieldLabel : '市场交易价格1.万元',
								maxLength : 23,
								maxLengthText : '最大输入长度23',
								name : 'procreditMortgageCar.exchangepriceone'
							},{
								fieldLabel : '市场交易价格2.万元',
								maxLength : 23,
								maxLengthText : '最大输入长度23',
								name : 'procreditMortgageCar.exchangepricetow'
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
								xtype : "dickeycombo",
								nodeKey : 'pl',
								hiddenName : "procreditMortgageCar.displacement",
								fieldLabel : "排量",
								itemName : '排量', // xx代表分类名称
								allowBlank : false,
							    blankText : '必填信息',
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
								xtype : 'textfield',
								fieldLabel : '配置',
								name : 'procreditMortgageCar.configuration'
							}, {
								xtype : 'textfield',
								fieldLabel : '车辆产地',
								allowBlank : false,
								name : 'procreditMortgageCar.carProduce'
							},{
								xtype : "dickeycombo",
								nodeKey : 'zws',
								hiddenName : "procreditMortgageCar.seating",
								fieldLabel : "座位数",
								itemName : '座位数', // xx代表分类名称
								allowBlank : false,
							    blankText : '必填信息',
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
								nodeKey : 'clys',
								hiddenName : "procreditMortgageCar.carColor",
								fieldLabel : "车辆颜色",
								itemName : '车辆颜色', // xx代表分类名称
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
								hiddenName : "procreditMortgageCar.enregisterinfoid",
								fieldLabel : "登记情况",
								itemName : '登记情况',
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
								name : 'procreditMortgageCar.leavefactorydate'
							}, {
								xtype : 'numberfield',
								fieldLabel : '使用时间.年',
								maxLength : 23,
								maxLengthText : '最大输入长度23',
								name : 'procreditMortgageCar.haveusedtime'
							},{
								xtype : "dickeycombo",
								nodeKey : 'ck',
								hiddenName : "procreditMortgageCar.carinfoid",
								fieldLabel : "车况",
								itemName : '车况',
								listeners : {
									afterrender : function(combox) {
										var st =combox.getStore();
										st.on("load", function() {
									 		combox.setValue(combox.getValue());
									 		combox.clearInvalid(); 
									 		})
									 	}
								}
							},{
								fieldLabel : '模型估价.万元',
								maxLength : 23,
								maxLengthText : '最大输入长度23',
								name : 'procreditMortgageCar.modelrangeprice'
							},{
								xtype : 'hidden',
								name : 'procreditMortgageCar.objectType',
								value : this.objectType
							},{
								xtype : 'hidden',
								name : 'procreditMortgageCar.id'
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
				preName : ['procreditMortgageCar','vCarDic'],
				scope : this,
				success : function(resp, options) {
					/*
					Ext.getCmp('factoryName').setValue(obj.data.vCarDic.id);//制造商id
					Ext.getCmp('factoryName').setRawValue(obj.data.vCarDic.carFirmName);	
					Ext.getCmp('carNumber').setValue(obj.data.vCarDic.carNumber);//车型编号
					Ext.getCmp('carStyleName').setValue(obj.data.vCarDic.carStyleName);//车系
					Ext.getCmp('carModelName').setValue(obj.data.vCarDic.carModelName);//车型
					Ext.getCmp('displacement').setValue(obj.data.vCarDic.displacementValue);//排量
					Ext.getCmp('configuration').setValue(obj.data.vCarDic.configuration);//配置
					Ext.getCmp('seating').setValue(obj.data.vCarDic.seatingValue);//座位数
*/				}
			});
		}
	},// end of constructor
	// 初始化组件
	initUIComponents : function() {
		

		//汽车参照
	returnCarMessageInfo = function(obj){
		/*Ext.getCmp('factoryName').setValue(obj.id);//制造商id
		Ext.getCmp('factoryName').setRawValue(obj.carFirmName);
		
		Ext.getCmp('carNumber').setValue(obj.carNumber);//车型编号
		Ext.getCmp('carStyleName').setValue(obj.carStyleName);//车系
		Ext.getCmp('carModelName').setValue(obj.carModelName);//车型
		Ext.getCmp('displacement').setValue(obj.displacementValue);//排量
		Ext.getCmp('configuration').setValue(obj.configuration);//配置
		Ext.getCmp('seating').setValue(obj.seatingValue);//座位数
*/	}
	}
});
