

Ext.define('htmobile.creditFlow.public.mortgage.house.SeeHouse', {
    extend: 'Ext.Panel',
    
    name: 'SeeHouse',

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
		                 title :'<span style="color:#000000;">查看<住宅>详细信息</span>',
		                 defaults:{
		                	xtype: 'textfield',
		                	readOnly: true,
		                	width:"100%",
		                	labelAlign:"top"
		                	
		                },
		                items: [
		                    {
		                        label: '<div class="fieldlabel">房屋坐落:</div>',
		                       value: MortgageData.vProjMortHouse.houseaddress
		                    }
		                    ,
		                    /*{
		                        label: '<div class="fieldlabel">产权性质:</div>',
		                         value:typeof(MortgageData.vProjMortHouse.propertyRightIdValue)!="undefined"?MortgageData.vProjMortHouse.propertyRightIdValue:''
		                    },*/
		                    {
		                        label:  '<div class="fieldlabel">权证编号:</div>',
		                       value: MortgageData.vProjMortHouse.certificatenumber
		                    }
		                    ,
		                    /*{
		                        label: '<div class="fieldlabel">建筑式样:</div>',
		                        value:typeof(MortgageData.vProjMortHouse.constructionTypeIdValue)!="undefined"?MortgageData.vProjMortHouse.constructionTypeIdValue:''
		                    },*/
		                    {
		                        label:  '<div class="fieldlabel">共有人:</div>',
		                        value: MortgageData.vProjMortHouse.mutualofperson
		                    }
		                    ,
		                   /* {
		                        label: '<div class="fieldlabel">建筑结构:</div>',
		                        value:typeof(MortgageData.vProjMortHouse.constructionFrameIdValue)!="undefined"?MortgageData.vProjMortHouse.constructionFrameIdValue:''
		                    },*/
		                    {
		                        label:  '<div class="fieldlabel">共有人权证编号:</div>',
		                        value: MortgageData.vProjMortHouse.mutualofCertificatenumber
		                    }
		                    ,{
		                        label:  '<div class="fieldlabel">建筑面积'+unitmeasurement(2)+':</div>',
		                        value: MortgageData.vProjMortHouse.buildacreage
		                    }
		                    ,
		                    {
		                        label: '<div class="fieldlabel">房屋结构:</div>',
		                        value:typeof(MortgageData.vProjMortHouse.houseTypeIdValue)!="undefined"?MortgageData.vProjMortHouse.houseTypeIdValue:''
		                    },
		                    {
		                        label:  '<div class="fieldlabel">登记日期:</div>',
		                        value: MortgageData.vProjMortHouse.buildtime
		                    }
		                   /* ,
		                    {
		                        label: '<div class="fieldlabel">地段描述:</div>',
		                        value: MortgageData.vProjMortHouse.descriptionIdValue
		                    }
		                ,
		                    {
		                        label:  '<div class="fieldlabel">剩余年限'+unitmeasurement(5)+':</div>',
		                        value: MortgageData.vProjMortHouse.residualyears
		                    }
		                    ,
		                    {
		                        label: '<div class="fieldlabel">登记情况:</div>',
		                        value:typeof(MortgageData.vProjMortHouse.registerInfoIdValue)!="undefined"?MortgageData.vProjMortHouse.registerInfoIdValue:''
		                    },
		                    {
		                        label:  '<div class="fieldlabel">新房单价'+unitmeasurement(3)+':</div>',
		                        value: MortgageData.vProjMortHouse.exchangefinalprice
		                    }
		                    ,
		                    {
		                        label: '<div class="fieldlabel">按揭余额'+unitmeasurement(1)+':</div>',
		                        value: MortgageData.vProjMortHouse.mortgagesbalance
		                    },
		                    {
		                        label:  '<div class="fieldlabel">模型估价'+unitmeasurement(1)+':</div>',
		                        value: MortgageData.vProjMortHouse.modelrangeprice
		                    }
		                    ,
		                    {
		                        label: '<div class="fieldlabel">同等房产单位单价1'+unitmeasurement(3)+':</div>',
		                        value:typeof(MortgageData.vProjMortHouse.exchangepriceone)!="undefined"?MortgageData.vProjMortHouse.exchangepriceone:''
		                    }
		                    ,
		                    {
		                        label: '<div class="fieldlabel">同等房产单位单价2'+unitmeasurement(3)+':</div>',
		                        value: MortgageData.vProjMortHouse.exchangepricetow
		                    },
		                    {
		                        label:  '<div class="fieldlabel">同等房产单位单价3'+unitmeasurement(3)+':</div>',
		                        value: MortgageData.vProjMortHouse.exchangepricethree
		                    }*/
		                ]
		            }]
		            
    	});

    	this.callParent([config]);
    	
    }

});
