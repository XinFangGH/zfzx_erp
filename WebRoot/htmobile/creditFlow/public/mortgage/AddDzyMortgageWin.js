

Ext.define('htmobile.creditFlow.public.mortgage.AddDzyMortgageWin', {
    extend: 'Ext.Panel',
    
    name: 'AddDzyMortgageWin',

    constructor: function (config) {
    	config = config || {};
    	var data=config.data;
    	 this.data=config.data;
    	Ext.apply(config,{
		    fullscreen: true,
		    title:"<span style='font-size:16px;'>抵质押物</span>",
		    flex:1,
		    scrollable:{
		    	direction: 'vertical'
		    },
		    items: [
		            {
		                xtype: 'fieldset',
		                  flex:1,
		                title :'<span style="color:#000000;"><抵质押物>基础信息</span>',
		                defaults:{
		                	xtype: 'textfield',
		                	readOnly: true,
		                	width:"100%",
		                	labelAlign:"top"
		                	
		                },
		                items: [
		                    {
		                        label: '<div class="fieldlabel">担保类型:</div>',
		                        xtype : "dickeycombo",
								nodeKey : 'dblx',
								value : data.procreditMortgage.assuretypeid
		                    },
		                    {
		                        label: '<div class="fieldlabel">保证方式:</div>',
		                        xtype : "dickeycombo",
								nodeKey : 'syrlx',
								value : data.procreditMortgage.personType
		                    },
		                    {
		                        label:  '<div class="fieldlabel">抵质押物类型:</div>',
		                        value: data.procreditMortgage.mortgagenametypeid
		                    },
		                    {
		                        label:'<div class="fieldlabel">所有权人:</div>',
		                        value: data.procreditMortgage.relation
		                    },
		                    {
		                        label:  '<div class="fieldlabel">备注:</div>',
		                        xtype:'textareafield',
		                        value: data.procreditMortgage.finalprice
		                    }
		                    ,
		                    {
		                        label:  '<div class="fieldlabel">公允价值(元):</div>',
		                        xtype:'textareafield',
		                        value: data.procreditMortgage.assuremoney
		                    }
		                    
		                    ,
		                    {
		                        label:  '<div class="fieldlabel">公允价值获取方式:</div>',
		                        value: data.procreditMortgage.valuationMechanism
		                    },
		                    {
		                        label:  '<div class="fieldlabel">获取时间:</div>',
		                        value: data.procreditMortgage.valuationTime
		                    }
		                    ,
		                    {
		                        label:  '<div class="fieldlabel">备注:</div>',
		                        value: data.procreditMortgage.mortgageRemarks
		                    }
		                ]
		            },{
		                xtype: 'fieldset',
		                  flex:1,
		                 title :'<span style="color:#000000;">填写<无限连带责任-公司>详细信息</span>',
		                defaults:{
		                	xtype: 'textfield',
		                	readOnly: true,
		                	width:"100%",
		                	labelAlign:"top"
		                	
		                },
		                items: [
		                    {
		                        label: '<div class="fieldlabel">营业执照号码:</div>',
		                        value: data.enterpriseView.cciaa
		                    },
		                    {
		                        label: '<div class="fieldlabel">联系电话:</div>',
		                        value: data.enterpriseView.telephone
		                    },
		                    {
		                        label:  '<div class="fieldlabel">法人代表电话:</div>',
		                        value: data.tel
		                    }
		                    ,
		                    {
		                        label: '<div class="fieldlabel">公司地址:</div>',
		                        value: data.enterpriseView.area
		                    },
		                    {
		                        label:  '<div class="fieldlabel">主营业务:</div>',
		                        value: data.procreditMortgageEnterprise.business
		                    }
		                    ,
		                    {
		                        label: '<div class="fieldlabel">主要资产:</div>',
		                        value: data.procreditMortgageEnterprise.assets
		                    },
		                    {
		                        label:  '<div class="fieldlabel">资产价值.万元:</div>',
		                        value: data.procreditMortgageEnterprise.netassets
		                    }
		                    ,
		                    {
		                        label: '<div class="fieldlabel">月营业额:</div>',
		                        value: data.procreditMortgageEnterprise.monthlyIncome
		                    }
		                ]
		            }]
		            
    	});

    	this.callParent([config]);
    	
    }

});
