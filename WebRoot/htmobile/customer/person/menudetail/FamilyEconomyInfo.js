

Ext.define('htmobile.customer.person.menudetail.FamilyEconomyInfo', {
    extend: 'Ext.Panel',
    
    name: 'FamilyEconomyInfo',

    constructor: function (config) {
    /*		var toolbar = Ext.create('Ext.Toolbar', {
					docked : 'bottom',
					items : [{
								xtype : 'spacer'
							}, {
								xtype : 'button',
								text : '房产信息',
								width:100,
								iconMask : true,
								scope : this,
								handler : this.detailhouse
							}, {
								xtype : 'button',
								text : '车辆信息',
								iconMask : true,
								width:100,
								scope : this,
								handler : this.detailcar
							}, {
								xtype : 'spacer'
							}]
				});*/
    	config = config || {};
    	var data=config.data;
    	 this.data=config.data;
    	Ext.apply(config,{
		    fullscreen: true,
		    title:"<span style='font-size:16px;'>家庭经济情况</span>",
		    flex:1,
		    scrollable:{
		    	direction: 'vertical'
		    },
		    items: [
		            {
		                xtype: 'fieldset',
		                  flex:1,
		                title :'<span style="color:#000000;">资产信息</span>',
		                defaults:{
		                	xtype: 'textfield',
		                	readOnly: true,
		                	width:"100%",
		                	labelAlign:"top"
		                	
		                },
		                items: [
		                    {
		                        label: '<div class="fieldlabel">总资产(万元):</div>',
		                        value: data.grossasset
		                    },
		                    {
		                        label: '<div class="fieldlabel">家庭财产(万元):</div>',
		                        value: data.homeasset
		                    },
		                    {
		                        label:  '<div class="fieldlabel">总负债(万元):</div>',
		                        value: data.grossdebt
		                    },
		                    {
		                        label:'<div class="fieldlabel">年总支出(万元):</div>',
		                        value: data.yeargrossexpend
		                    }
		                ]
		            },{
		                xtype: 'fieldset',
		                  flex:1,
		                 title :'<span style="color:#000000;">本人工资代发信息</span>',
		                defaults:{
		                	xtype: 'textfield',
		                	readOnly: true,
		                	width:"100%",
		                	labelAlign:"top"
		                	
		                },
		                items: [
		                    {
		                        label: '<div class="fieldlabel">工资开户行:</div>',
		                        value: data.wagebank
		                    },
		                    {
		                        label: '<div class="fieldlabel">工资开户人:</div>',
		                        value: data.wageperson
		                    },
		                    {
		                        label:  '<div class="fieldlabel">工资开户账号:</div>',
		                        value: data.wageaccount
		                    }
		                ]
		            },
		                    {
				                xtype: 'fieldset',
				                  flex:1,
				                 title : '<span style="color:#000000;">配偶工资代发信息</span>',
				                defaults:{
				                	xtype: 'textfield',
				                	readOnly: true,
				                	width:"100%",
				                	labelAlign:"top"
				                	
				                },
				                items: [
				                    {
				                        label: '<div class="fieldlabel">工资开户行:</div>',
				                        value: data.matebank
				                    },
				                    {
				                        label: '<div class="fieldlabel">工资开户人:</div>',
				                        value: data.mateperson
				                    },
				                    {
				                        label:  '<div class="fieldlabel">工资开户账号:</div>',
				                        value: data.mateaccount
				                    }
				              ]
		            }
		            
		      
		    	/* ,toolbar */]
    	});

    	this.callParent([config]);
    	
    },
    detailhouse:function(){
       mobileNavi.push(
		Ext.create('htmobile.customer.person.menudetail.FamilyEconomyHouseList', {
						data : this.data
					})
		    	);
    },
    detailcar:function(){
       mobileNavi.push(
		Ext.create('htmobile.customer.person.menudetail.FamilyEconomyCarList', {
						data : this.data
					})
		    	);
    }

});
