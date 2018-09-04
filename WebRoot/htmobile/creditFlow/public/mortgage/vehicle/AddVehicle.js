

Ext.define('htmobile.creditFlow.public.mortgage.vehicle.AddVehicle', {
    extend: 'Ext.form.Panel',
    name: 'AddVehicle',
    constructor: function (config) {
    	config = config || {};
    	Ext.apply(this,config);
    	Ext.apply(config,{
		    fullscreen: true,
		    style:'background-color:white;',
		    scrollable:{
		    	direction: 'vertical'
		    },
		    title:"<span style='font-size:16px;'>抵质押物</span>",
		    items: [{
		                xtype: 'fieldset',
		                title :'<span style="color:#000000;">查看<车辆>详细信息</span>',
		                defaults:{
		                	xtype: 'textfield',
		                	labelWidth:document.body.clientWidth/3,
	    				    clearIcon : true
		                	
		                },
		                items: [{
				        		xtype : 'hiddenfield',
				        		name : 'procreditMortgageCar.id',
				        		value:Ext.isEmpty(this.MortgageData)?null:this.MortgageData.vProjMortCar.id
				        	},{
				        		xtype : 'hiddenfield',
				        		name : 'procreditMortgageCar.objectType',
				        		value : "mortgage"
				        	},{
				        		xtype : 'hiddenfield',
				        		name : 'procreditMortgageCar.type',
				        		value : "1"
				        	},{
				        		xtype : 'hiddenfield',
				        		name : 'procreditMortgage.id',
				        		value : this.vProcreditDictionaryId
				        	},{
				        		xtype : 'hiddenfield',
				        		name : 'procreditMortgage.projid',
				        		value : this.projectId
				        	},{
				        		xtype : 'hiddenfield',
				        		name : 'procreditMortgage.businessType',
				        		value : this.businessType
				        	},{
				        		xtype : 'hiddenfield',
				        		name : 'procreditMortgage.assuretypeid',
				        		value : this.assuretypeid
				        	},{
				        		xtype : 'hiddenfield',
				        		name : 'procreditMortgage.mortgagenametypeid',
				        		value : this.mortgagenametypeid
				        	},{
				        		xtype : 'hiddenfield',
				        		name : 'procreditMortgage.personType',
				        		value : this.personType
				        	},{
				        		xtype : 'hiddenfield',
				        		name : 'procreditMortgage.relation',
				        		value : this.relation
				        	}/*,{
				        		label: '<div class="fieldlabel">最终估价:</div>',
				        		name : 'procreditMortgage.finalprice',
				        		value:Ext.isEmpty(this.MortgageData)?null:this.MortgageData.vProcreditDictionary.finalprice
				        	},{
				        		label: '<div class="fieldlabel">最终认证价格:</div>',
				        		name : 'procreditMortgage.finalCertificationPrice',
				        		value:Ext.isEmpty(this.MortgageData)?null:this.MortgageData.vProcreditDictionary.finalCertificationPrice
				        	},{
				        		label: '<div class="fieldlabel">评估时间:</div>',
				        		name : 'procreditMortgage.valuationTime',
				        		value:Ext.isEmpty(this.MortgageData)?null:this.MortgageData.vProcreditDictionary.valuationTime
				        	},{
				        		label: '<div class="fieldlabel">可担保额度:</div>',
				        		name : 'procreditMortgage.assuremoney',
				        		value:Ext.isEmpty(this.MortgageData)?null:this.MortgageData.vProcreditDictionary.assuremoney
				        	}*/,{
				        		xtype : 'hiddenfield',
				        		name : 'procreditMortgage.mortgageRemarks',
				        		 value:this.mortgageRemarks
				        	},{
				        		xtype : 'hiddenfield',
				        		name : 'customerEnterpriseName',
				        		value : this.customerEnterpriseName
				        	},{
				        		xtype : 'hiddenfield',
				        		name : 'procreditMortgage.mortgagepersontypeforvalue',
				        		 value:this.mortgagepersontypeforvalue
				        	}, {
		                        label: '车辆品牌',
		                        name : 'procreditMortgageCar.carManufacturer',
		                         value:Ext.isEmpty(this.MortgageData)?null:this.MortgageData.vProjMortCar.carManufacturer
		                    }, {
		                        label: '车架号',
		                        name : 'procreditMortgageCar.vin',
		                         value:Ext.isEmpty(this.MortgageData)?null:   this.MortgageData.vProjMortCar.vin
		                    }, {
		                        label: '车辆型号',
		                        name : 'procreditMortgageCar.carNumber',
		                         value:Ext.isEmpty(this.MortgageData)?null:this.MortgageData.vProjMortCar.carNumber
		                    }, {
		                        label:  '新车价格'+unitmeasurement(1),
		                        name : 'procreditMortgageCar.carprice',
		                         value:Ext.isEmpty(this.MortgageData)?null:   this.MortgageData.vProjMortCar.carprice
		                    }, {
		                        label: '使用时间'+unitmeasurement(5),
		                        name : 'procreditMortgageCar.haveusedtime',
		                         value:Ext.isEmpty(this.MortgageData)?null:  this.MortgageData.vProjMortCar.haveusedtime
		                    }, {
		                        label:  '里程数',
		                        name : 'procreditMortgageCar.totalkilometres',
		                         value:Ext.isEmpty(this.MortgageData)?null:   this.MortgageData.vProjMortCar.totalkilometres
		                    }, {
		                        label: '模型估价'+unitmeasurement(1),
		                        name : 'procreditMortgageCar.modelrangeprice',
		                        value:Ext.isEmpty(this.MortgageData)?null:  this.MortgageData.vProjMortCar.modelrangeprice
		                    }, {
		                        label:  '市场交易价格1'+unitmeasurement(1),
		                        name : 'procreditMortgageCar.exchangepriceone',
		                         value:Ext.isEmpty(this.MortgageData)?null:   this.MortgageData.vProjMortCar.exchangepriceone
		                    }, {
		                        label: '市场交易价格2'+unitmeasurement(1),
		                        name : 'procreditMortgageCar.exchangepricetow',
		                         value:Ext.isEmpty(this.MortgageData)?null:   this.MortgageData.vProjMortCar.exchangepricetow
		                    }, {
		                        label: '车系',
		                        name : 'procreditMortgageCar.carStyle',
		                         value:Ext.isEmpty(this.MortgageData)?null:   this.MortgageData.vProjMortCar.carStyle
		                    }, {
		                        label: '车型',
		                        name : 'procreditMortgageCar.carModel',
		                         value:Ext.isEmpty(this.MortgageData)?null:   this.MortgageData.vProjMortCar.carModel
		                    }, {
		                        label:  '排量',
		                        name : 'procreditMortgageCar.displacement',
		                        xtype:'dickeycombo',
		                        nodeKey:'pl',
		                        clearIcon : false,
		                        value:Ext.isEmpty(this.MortgageData)?null:    this.MortgageData.vProjMortCar.displacement
		                    }, {
		                        label: '配置',
		                        name : 'procreditMortgageCar.configuration',
		                         value:Ext.isEmpty(this.MortgageData)?null:   this.MortgageData.vProjMortCar.configuration
		                    }, {
		                        label:  '座位数',
		                        xtype:'dickeycombo',
		                        nodeKey:'zws',
		                        clearIcon : false,
		                        name : 'procreditMortgageCar.seating',
		                         value:Ext.isEmpty(this.MortgageData)?null:   this.MortgageData.vProjMortCar.seating
		                    },{
		                        label:  '车辆颜色',
		                        xtype:'dickeycombo',
		                        nodeKey:'clys',
		                        clearIcon : false,
		                        name : 'procreditMortgageCar.carColor',
		                        value:Ext.isEmpty(this.MortgageData)?null:   this.MortgageData.vProjMortCar.carColor
		                    }, {
		                        label: '登记情况',
		                        xtype:'dickeycombo',
		                        nodeKey:'djqk',
		                        clearIcon : false,
		                        name : 'procreditMortgageCar.enregisterinfoid',
		                        value:Ext.isEmpty(this.MortgageData)?null:   this.MortgageData.vProjMortCar.enregisterInfoIdValue!='undefined'?this.MortgageData.vProjMortCar.enregisterinfoid:""
		                    }, {
		                        label:  '出厂日期',
		                        xtype: 'datepickerfield',
		                        dateFormat:'Y-m-d',
			                  	picker: {
			                     xtype:'todaypickerux'
			                  	},
		                        name : 'procreditMortgageCar.leavefactorydate',
		                        value: Ext.isEmpty(this.MortgageData)?new Date():new Date(this.MortgageData.vProjMortCar.leavefactorydate)
		                    }, {
		                        label: '事故次数',
		                        name : 'procreditMortgageCar.accidenttimes',
		                        value:Ext.isEmpty(this.MortgageData)?null:this.MortgageData.vProjMortCar.accidenttimes
		                    }, {
		                        label:  '车况',
		                        xtype:'dickeycombo',
		                        nodeKey:'ck',
		                        clearIcon : false,
		                        name : 'procreditMortgageCar.carinfoid',
		                        value:Ext.isEmpty(this.MortgageData)?null:typeof(this.MortgageData.vProjMortCar.carinfoid)!='undefined'?this.MortgageData.vProjMortCar.carinfoid:""
		                    }
		                ]
		            }, {
		            	docked:'bottom',
						xtype : 'button',
						cls : 'submit-button',
						text : "提交保存",
						scope:this,
						handler : this.submitBtn
					}]
		            
    	});

    	this.callParent([config]);
    	
    },
    submitBtn:function(){
		var vin=this.getCmpByName('procreditMortgageCar.vin').getValue();
         if(Ext.isEmpty(vin)){
		    Ext.Msg.alert('','车架号不能为空');
			return;
		  }
		  var d=new Date(this.getCmpByName('procreditMortgageCar.leavefactorydate').getValue());
		  var leavefactorydate=d.getFullYear()+"-"+(d.getMonth()+1)+"-"+d.getDate();
       	this.submit({
				method : 'POST',
				url:__ctxPath+"/credit/mortgage/addMortgageOfDY.do",
				params:{
					'procreditMortgageCar.leavefactorydate':leavefactorydate
				},
				success : function(form, action) {
					  Ext.Msg.alert('','保存成功');
					   mobileNavi.pop(2);
					   var object= Ext.getCmp("DZYMortgageViewList");
					  object.store.loadPage(1);
				},
				failure : function(form, action) {
					  Ext.Msg.alert('','保存失败');
				}
			});		
    }

});
