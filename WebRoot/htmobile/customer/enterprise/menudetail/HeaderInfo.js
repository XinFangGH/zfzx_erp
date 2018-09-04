

Ext.define('htmobile.customer.enterprise.menudetail.HeaderInfo', {
    extend: 'Ext.Panel',
    
    name: 'HeaderInfo',

    constructor: function (config) {
    		var toolbar = Ext.create('Ext.Toolbar', {
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
				});
    	config = config || {};
    	var data=config.data;
    	 this.data=config.data;
    	Ext.apply(config,{
		    fullscreen: true,
		    title:"实际控制人",
		    flex:1,
		    scrollable:{
		    	direction: 'vertical'
		    },
		    items: [
		            {
		                xtype: 'fieldset',
		                  flex:1,
		                defaults:{
		                	xtype: 'textfield',
		                	readOnly: true,
		                	width:"100%",
		                	labelAlign:"top"
		                	
		                },
		                items: [
		                    {
		                        label: '<div class="fieldlabel">负责人姓名:</div>',
		                      value:data==null?"":data.name
		                    },
		                    {
		                        label: '<div class="fieldlabel">性别:</div>',
		                        xtype : "dickeycombo",
								nodeKey : 'sex_key',
		                         value:data==null?"":data.sex
		                    },
		                    {
		                        label: '<div class="fieldlabel">证件类型:</div>',
		                        xtype : "dickeycombo",
								nodeKey : 'card_type_key',
		                        value:data==null?"":data.cardtypevalue
		                    },
		                    {
		                        label: '<div class="fieldlabel">证件号码:</div>',
		                       value:data==null?"":data.cardnumber
		                    }
		                    ,
		                    {
		                        label: '<div class="fieldlabel">手机号码:</div>',
		                        value:data==null?"":data.cellphone
		                    },
		                    {
		                        label: '<div class="fieldlabel">电子邮箱:</div>',
		                         value:data==null?"":data.selfemail
		                    },
		                    {
		                        label: '<div class="fieldlabel">资产总值（万元）:</div>',
		                        value:data==null?"":data.grossasset
		                    }
		                ]
		            }
		            
		      
		    	 ,toolbar ]
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
