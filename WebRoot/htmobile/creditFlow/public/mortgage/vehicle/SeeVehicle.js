

Ext.define('htmobile.creditFlow.public.mortgage.vehicle.SeeVehicle', {
    extend: 'Ext.Panel',
    
    name: 'SeeVehicle',

    constructor: function (config) {
    	config = config || {};
    	var MortgageData=config.MortgageData;
    	this.type=config.type;
    //	 this.data=config.data;
    	Ext.apply(config,{
		    fullscreen: true,
		    scrollable:{
		    	direction: 'vertical'
		    },
		    title:"<span style='font-size:16px;'>抵质押物</span>",
		    items: [Ext.create('htmobile.creditFlow.public.mortgage.BaseMortgageInfo',{
				          MortgageData:MortgageData
			        	}),{
		                xtype: 'fieldset',
		                 title :'<span style="color:#000000;">查看<车辆>详细信息</span>',
		                defaults:{
		                	xtype: 'textfield',
		                	readOnly: true,
		                	width:"100%",
		                	labelAlign:"top"
		                	
		                },
		                items: [
		                 {
		                        label: '<div class="fieldlabel">车辆品牌:</div>',
		                        name : 'procreditMortgageCar.carManufacturer',
		                         value:MortgageData.vProjMortCar.carManufacturer
		                    },
		                    {
		                        label: '<div class="fieldlabel">车架号:</div>',
		                        name : 'procreditMortgageCar.vin',
		                         value:MortgageData.vProjMortCar.vin
		                    },
		                    {
		                        label: '<div class="fieldlabel">首次登记日:</div>',
		                        name : 'procreditMortgageCar.firstRegister',
		                         value:MortgageData.vProjMortCar.firstRegister
		                    },
		                    {
		                        label: '<div class="fieldlabel">发票全价:</div>',
		                        name : 'procreditMortgageCar.fullInvoiceValue',
		                         value:MortgageData.vProjMortCar.fullInvoiceValue
		                    },
		                    {
		                        label: '<div class="fieldlabel">车牌号:</div>',
		                        name : 'procreditMortgageCar.licensePlate',
		                         value:MortgageData.vProjMortCar.licensePlate
		                    }/*,
		                    {
		                        label: '<div class="fieldlabel">制造商:</div>',
		                        value: MortgageData.vProjMortCar.carManufacturer
		                    },
		                    {
		                        label: '<div class="fieldlabel">车型编号:</div>',
		                        value: MortgageData.vProjMortCar.carNumber
		                    }*/,
		                    {
		                        label: '<div class="fieldlabel">车辆型号:</div>',
		                        value: MortgageData.vProjMortCar.carNumber
		                    },
		                    {
		                        label: '<div class="fieldlabel">首付金额:</div>',
		                        value: MortgageData.vProjMortCar.downPayment
		                    },
		                    {
		                        label: '<div class="fieldlabel">月供金额:</div>',
		                        value: MortgageData.vProjMortCar.monthlySupply
		                    },
		                    {
		                        label: '<div class="fieldlabel">分期月数:</div>',
		                        value: MortgageData.vProjMortCar.numberOfMonths
		                    }/*,
		                    {
		                        label:  '<div class="fieldlabel">发动机号:</div>',
		                       value:  MortgageData.vProjMortCar.engineNo
		                    }
		                    ,
		                    {
		                        label: '<div class="fieldlabel">车架号:</div>',
		                        value: MortgageData.vProjMortCar.vin
		                    },
		                    {
		                        label:  '<div class="fieldlabel">新车价格'+unitmeasurement(1)+':</div>',
		                        value: MortgageData.vProjMortCar.carprice
		                    }
		                    ,
		                    {
		                        label: '<div class="fieldlabel">使用时间'+unitmeasurement(5)+':</div>',
		                        value: MortgageData.vProjMortCar.haveusedtime
		                    },
		                    {
		                        label:  '<div class="fieldlabel">里程数:</div>',
		                        value: MortgageData.vProjMortCar.totalkilometres
		                    }
		                    ,
		                    {
		                        label: '<div class="fieldlabel">模型估价'+unitmeasurement(1)+':</div>',
		                        value:MortgageData.vProjMortCar.modelrangeprice
		                    },
		                    {
		                        label:  '<div class="fieldlabel">市场交易价格1'+unitmeasurement(1)+':</div>',
		                        value: MortgageData.vProjMortCar.exchangepriceone
		                    }
		                    ,
		                    {
		                        label: '<div class="fieldlabel">市场交易价格2'+unitmeasurement(1)+':</div>',
		                        value: MortgageData.vProjMortCar.exchangepricetow
		                    },
		                    {
		                        label: '<div class="fieldlabel">车系:</div>',
		                        value: MortgageData.vProjMortCar.carStyle
		                    },
		                    {
		                        label: '<div class="fieldlabel">车型:</div>',
		                        value:  MortgageData.vProjMortCar.carModel
		                    },
		                    {
		                        label:  '<div class="fieldlabel">排量:</div>',
		                       value:  MortgageData.vProjMortCar.displacementValue
		                    }
		                    ,
		                    {
		                        label: '<div class="fieldlabel">配置:</div>',
		                        value: MortgageData.vProjMortCar.configuration
		                    },
		                    {
		                        label:  '<div class="fieldlabel">座位数:</div>',
		                        value: MortgageData.vProjMortCar.seatingValue
		                    }
		                    ,
		                    {
		                        label: '<div class="fieldlabel">登记情况:</div>',
		                        value: MortgageData.vProjMortCar.enregisterInfoIdValue!='undefined'?MortgageData.vProjMortCar.enregisterInfoIdValue:""
		                    },
		                    {
		                        label:  '<div class="fieldlabel">出厂日期:</div>',
		                        value: MortgageData.vProjMortCar.leavefactorydate
		                    }
		                    ,
		                    {
		                        label: '<div class="fieldlabel">事故次数:</div>',
		                        value: MortgageData.vProjMortCar.accidenttimes
		                    },
		                    {
		                        label:  '<div class="fieldlabel">车况:</div>',
		                        value:typeof(MortgageData.vProjMortCar.carInfoIdValue)!='undefined'?MortgageData.vProjMortCar.carInfoIdValue:""
		                    }*/
		                    
		                
		                ]
		            }]
		            
    	});

    	this.callParent([config]);
    	
    }

});
