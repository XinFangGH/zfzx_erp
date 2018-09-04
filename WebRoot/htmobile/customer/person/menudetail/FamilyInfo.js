

Ext.define('htmobile.customer.person.menudetail.FamilyInfo', {
    extend: 'Ext.Panel',
    
    name: 'FamilyInfo',

    constructor: function (config) {
    	config = config || {};
    	var data=config.data;
    	Ext.apply(config,{
		    fullscreen: true,
		    title:"家庭信息",
		    scrollable:{
		    	direction: 'vertical'
		    },
		    items: [
		            {
		                xtype: 'fieldset',
		                defaults:{
		                	xtype: 'textfield',
		                	readOnly: true,
		                	width:"100%",
		                	labelAlign:"top"
		                	
		                },
		                items: [
		                    {
		                        label: '<div class="fieldlabel">是否户主:</div>',
		                        value: data.isheadoffamily==true?"是":"否"
		                    },
		                    {
		                        label: '<div class="fieldlabel">家庭地址:</div>',
		                        value: data.familyaddress
		                    },
		                    {
		                        label: '<div class="fieldlabel">家庭人数:</div>',
		                        value: data.personCount
		                    },
		                    {
		                        label: '<div class="fieldlabel">邮政编码:</div>',
		                        value: data.familypostcode
		                    },
		                    {
		                        label: '<div class="fieldlabel">社区名:</div>',
		                        value: data.familyaddress
		                    }/*,{
		                        label: '居住状况',
		                        value: data.isheadoffamily
		                    },
		                    {
		                        label: '现住宅形式',
		                        value: data.familyaddress
		                    },*/,{
		                        label: '<div class="fieldlabel">现住宅面积:</div>',
		                        value: data.housearea
		                    },
		                    {
		                        label: '<div class="fieldlabel">家庭税后月收入:</div>',
		                        value: data.homeincome
		                    }
		                    ,{
		                        label: '<div class="fieldlabel">家庭月可支配资金:</div>',
		                        value: data.disposableCapital
		                    },
		                    {
		                        label: '<div class="fieldlabel">信用记录情况及说明:</div>',
		                        xtype:'textareafield',
		                        value: data.recordAndRemarks
		                    }
		                ]
		            }
		        ]
    	});

    	this.callParent([config]);
    	
    },
    detail:function(){
       mobileNavi.push(
		Ext.create('htmobile.customer.person.PersonDetailMenu',{
		        	})
		    	);
    }

});
