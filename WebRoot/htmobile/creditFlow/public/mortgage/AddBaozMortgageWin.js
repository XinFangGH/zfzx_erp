

Ext.define('htmobile.creditFlow.public.mortgage.AddBaozMortgageWin', {
    extend: 'Ext.Panel',
    
    name: 'AddBaozMortgageWin',

    constructor: function (config) {
    	config = config || {};
    	var MortgageData=config.MortgageData;
    	this.type=config.type;
    //	 this.data=config.data;
    	Ext.apply(config,{
		    fullscreen: true,
		    title:"<span style='font-size:16px;'>保证担保</span>",
		    flex:1,
		    scrollable:{
		    	direction: 'vertical'
		    },
		    items: [
		            {
		                xtype: 'fieldset',
		                  flex:1,
		                title :'<span style="color:#000000;">保证担保基础信息</span>',
		                defaults:{
		                	xtype: 'textfield',
		                	readOnly: true,
		                	width:"100%",
		                	labelAlign:"top"
		                	
		                },
		                items: [
		                    {
		                        label: '<div class="fieldlabel">项目编号:</div>',
		                        xtype : "dickeycombo",
								nodeKey : 'syrlx',
								value :  MortgageData.vProcreditDictionary.projnum
		                    },
		                    {
		                        label: '<div class="fieldlabel">项目名称:</div>',
		                        xtype : "textareafield",
								value :  MortgageData.vProcreditDictionary.projname
		                    },
		                    {
		                        label:  '<div class="fieldlabel">客户名称:</div>',
		                        value: MortgageData.vProcreditDictionary.enterprisename
		                    },
		                    {
		                        label:'<div class="fieldlabel">保证人:</div>',
		                        value:  MortgageData.vProcreditDictionary.assureofnameEnterOrPerson
		                    },
		                    {
		                        label:  '<div class="fieldlabel">备注:</div>',
		                        xtype:'textareafield',
		                        value: MortgageData.vProcreditDictionary.mortgageRemarks
		                    },{
		                        label: '<div class="fieldlabel">保证人类型:</div>',
		                        value: MortgageData.vProcreditDictionary.personTypeValue
		                    },{
		                        label: '<div class="fieldlabel">保证方式:</div>',
		                        value:  MortgageData.vProcreditDictionary.assuremodeidValue
		                    },
		                    {
		                        label: '<div class="fieldlabel">所有权人与借款人的关系:</div>',
		                        value: MortgageData.vProcreditDictionary.relation
		                    },
		                    {
		                        label:  '<div class="fieldlabel">是否落实:</div>'
		                     //   value:  null==data?"":data.tel
		                    },
		                    {
		                        label:  '<div class="fieldlabel">落实时间:</div>',
		                        value: null!=MortgageData.vProcreditDictionary.transactDate? MortgageData.vProcreditDictionary.transactDate:""
		                    }
		                    ,
		                    {
		                        label: '<div class="fieldlabel">经办人:</div>',
		                        value: MortgageData.vProcreditDictionary.transactPerson
		                    },
		                    {
		                        label:  '<div class="fieldlabel">落实文件:</div>'
		                      //     value: data.procreditMortgageEnterprise.business
		                    },
		                    {
		                        label:  '<div class="fieldlabel">落实备注:</div>',
		                        value:MortgageData.vProcreditDictionary.transactRemarks
		                    }
		                    ,
		                    {
		                        label: '<div class="fieldlabel">是否归档:</div>',
		                        value:MortgageData.vProcreditDictionary.isArchiveConfirm==false?'否':'是' 
		                    },
		                    {
		                        label:  '<div class="fieldlabel">归档备注:</div>',
		                        value:  MortgageData.vProcreditDictionary.remarks
		                    }
		                    ,
		                    {
		                        label:  '<div class="fieldlabel">要求材料清单:</div>',
		                        value:  MortgageData.vProcreditDictionary.needDatumList
		                    }
		                ]
		            },this.type==0?{
		                xtype: 'fieldset',
		                  flex:1,
		                 title :'<span style="color:#000000;">查看<无限连带责任-公司>详细信息</span>',
		                defaults:{
		                	xtype: 'textfield',
		                	readOnly: true,
		                	width:"100%",
		                	labelAlign:"top"
		                	
		                },
		                items: [
		                    {
		                        label: '<div class="fieldlabel">>营业执照号码:</div>',
		                        value:  MortgageData.vProjMortCompany.licensenumber
		                    },
		                    {
		                        label: '<div class="fieldlabel">法人代表:</div>',
		                        value:   MortgageData.vProjMortCompany.corporate
		                    },
		                    {
		                        label:  '<div class="fieldlabel">联系电话:</div>',
		                       value:  MortgageData.vProjMortCompany.telephone
		                    }
		                    ,
		                    {
		                        label: '<div class="fieldlabel">法人代表电话:</div>',
		                        value: MortgageData.vProjMortCompany.corporatetel
		                    },
		                    {
		                        label:  '<div class="fieldlabel">公司地址:</div>',
		                        value:  MortgageData.vProjMortCompany.registeraddress
		                    }
		                    ,
		                    {
		                        label: '<div class="fieldlabel">主营业务:</div>',
		                        value:  MortgageData.vProjMortCompany.business
		                    },
		                    {
		                        label:  '<div class="fieldlabel">资产价值:</div>',
		                        value:  MortgageData.vProjMortCompany.netassets
		                    }
		                    ,
		                    {
		                        label: '<div class="fieldlabel">月营业额:</div>',
		                        value:  MortgageData.vProjMortCompany.monthlyIncome
		                    }
		                ]
		            }:{
		                xtype: 'fieldset',
		                  flex:1,
		                 title :'<span style="color:#000000;">查看<无限连带责任-公司>详细信息</span>',
		                defaults:{
		                	xtype: 'textfield',
		                	readOnly: true,
		                	width:"100%",
		                	labelAlign:"top"
		                	
		                },
		                items: [
		                    {
		                        label: '<div class="fieldlabel">>营业执照号码:</div>',
		                        value:  MortgageData.vProjMortCompany.licensenumber
		                    },
		                    {
		                        label: '<div class="fieldlabel">法人代表:</div>',
		                        value:   MortgageData.vProjMortCompany.corporate
		                    },
		                    {
		                        label:  '<div class="fieldlabel">联系电话:</div>',
		                       value:  MortgageData.vProjMortCompany.telephone
		                    }
		                    ,
		                    {
		                        label: '<div class="fieldlabel">法人代表电话:</div>',
		                        value: MortgageData.vProjMortCompany.corporatetel
		                    },
		                    {
		                        label:  '<div class="fieldlabel">公司地址:</div>',
		                        value:  MortgageData.vProjMortCompany.registeraddress
		                    }
		                    ,
		                    {
		                        label: '<div class="fieldlabel">主营业务:</div>',
		                        value:  MortgageData.vProjMortCompany.business
		                    },
		                    {
		                        label:  '<div class="fieldlabel">资产价值:</div>',
		                        value:  MortgageData.vProjMortCompany.netassets
		                    }
		                    ,
		                    {
		                        label: '<div class="fieldlabel">月营业额:</div>',
		                        value:  MortgageData.vProjMortCompany.monthlyIncome
		                    }
		                ]
		            }]
		            
    	});

    	this.callParent([config]);
    	
    }

});
