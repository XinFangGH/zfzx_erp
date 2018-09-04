

Ext.define('htmobile.customer.person.menudetail.SpouseForm', {
    extend: 'Ext.Panel',
    
    name: 'SpouseForm',

    constructor: function (config) {
    	config = config || {};
    	var data=config.data;
    	Ext.apply(config,{
		    fullscreen: true,
		    title:"配偶信息",
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
		                        label: '<div class="fieldlabel">配偶姓名:</div>',
		                        value: data == null? null: data.name
		                    },
		                    {
		                        label: '<div class="fieldlabel">学历:</div>',
		                        xtype : "dickeycombo",
					            nodeKey : 'dgree',
		                        value: data == null? null:  data.dgree
		                    },
		                    {
		                        label: '<div class="fieldlabel">联系电话:</div>',
		                         value: data == null? null:  data.linkTel
		                    },
		                    {
		                        label: '<div class="fieldlabel">证件类型:</div>',
		                        xtype : "dickeycombo",
					            nodeKey : 'card_type_key',
		                         value: data == null? null: data.cardtype
		                    },
		                    {
		                        label: '<div class="fieldlabel">证件号码:</div>',
		                         value: data == null? null:  data.cardnumber
		                    }/*,{
		                        label: '居住状况',
		                        value: data.isheadoffamily
		                    },
		                    {
		                        label: '现住宅形式',
		                        value: data.familyaddress
		                    },*/,{
		                        label: '<div class="fieldlabel">职务:</div>',
		                        xtype : "dickeycombo",
					             nodeKey : 'zhiwujob',
		                         value: data == null? null:  data.job
		                    },
		                    {
		                        label: '<div class="fieldlabel">政治面貌:</div>',
		                        xtype : "dickeycombo",
								nodeKey : 'zzmm', // xx代表分类名称
		                         value: data == null? null: data.politicalStatus
		                    }
		                    ,{
		                        label: '<div class="fieldlabel">税后收入:</div>',
		                        value: data == null? null: data.currentcompany
		                    },
		                    {
		                        label: '<div class="fieldlabel">信用记录情况及说明:</div>',
		                         value: data == null? null:  data.incomeAfterTax
		                    }
		                    ,{
		                        label: '<div class="fieldlabel">单位性质:</div>',
		                        xtype : "dickeycombo",
						        nodeKey : 'unitproperties',
		                         value: data == null? null:  data.unitProperty
		                    },
		                    {
		                        label: '<div class="fieldlabel">单位电话:</div>',
		                         value: data == null? null:  data.unitPhoneNO
		                    },
		                    {
		                        label: '<div class="fieldlabel">单位地址:</div>',
		                         value: data == null? null:  data.unitAddress
		                    }
		                ]
		            }
		        ]
    	});

    	this.callParent([config]);
    	
    }

});
