

Ext.define('htmobile.creditFlow.public.mortgage.houseground.SeeHouseground', {
    extend: 'Ext.Panel',
    
    name: 'SeeHouseground',

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
		                 title :'<span style="color:#000000;">查看<住宅用地>详细信息</span>',
		                 defaults:{
		                	xtype: 'textfield',
		                	readOnly: true,
		                	width:"100%",
		                	labelAlign:"top"
		                	
		                },
		                items: [
		                    {
		                        label: '<div class="fieldlabel">土地地点:</div>',
		                        value:MortgageData.vProjMortHouseGround.address
		                    },
		                    {
		                        label: '<div class="fieldlabel">产权人:</div>',
		                        value: MortgageData.vProjMortHouseGround.propertyperson
		                    },
		                    {
		                        label:  '<div class="fieldlabel">权证编号:</div>',
		                       value: MortgageData.vProjMortHouseGround.certificatenumber
		                    }
		                    ,
		                    {
		                        label: '<div class="fieldlabel">地段描述:</div>',
		                        value:MortgageData.vProjMortHouseGround.descriptionIdValue
		                    },
		                    {
		                        label:  '<div class="fieldlabel">占地面积'+unitmeasurement(2)+':</div>',
		                        value: MortgageData.vProjMortHouseGround.acreage
		                    }
		                    ,
		                    {
		                        label: '<div class="fieldlabel">登记情况:</div>',
		                        value: MortgageData.vProjMortHouseGround.registerInfoIdValue
		                    } ,
		                    {
		                        label: '<div class="fieldlabel">预规划住宅面积'+unitmeasurement(2)+':</div>',
		                        value:MortgageData.vProjMortHouseGround.modelrangepricetow
		                    },
		                    {
		                        label:  '<div class="fieldlabel">模型估值1'+unitmeasurement(1)+':</div>',
		                        value: MortgageData.vProjMortHouseGround.anticipateacreage
		                    }
		                    ,
		                    {
		                        label: '<div class="fieldlabel">模型估值2'+unitmeasurement(1)+':</div>',
		                        value:MortgageData.vProjMortHouseGround.modelrangepricetow
		                    },
		                    {
		                        label:  '<div class="fieldlabel">土地抵质押贷款余额'+unitmeasurement(1)+':</div>',
		                        value: MortgageData.vProjMortHouseGround.mortgagesbalance
		                    }
		                    ,
		                    {
		                        label: '<div class="fieldlabel">同等土地单价1'+unitmeasurement(3)+':</div>',
		                        value: MortgageData.vProjMortHouseGround.exchangeprice
		                    },
		                    {
		                        label: '<div class="fieldlabel">同等土地单价2'+unitmeasurement(3)+':</div>',
		                        value: MortgageData.vProjMortHouseGround.mortgagesbalance
		                    },
		                    {
		                        label: '<div class="fieldlabel">同等土地单价3'+unitmeasurement(3)+':</div>',
		                        value:  MortgageData.vProjMortHouseGround.exchangepricetow
		                    }
		                    
		                
		                ]
		            }]
		            
    	});

    	this.callParent([config]);
    	
    }

});
