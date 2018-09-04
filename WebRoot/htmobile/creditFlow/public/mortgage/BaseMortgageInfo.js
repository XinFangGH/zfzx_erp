

Ext.define('htmobile.creditFlow.public.mortgage.BaseMortgageInfo', {
    extend: 'Ext.form.FieldSet',
    
    name: 'BaseMortgageInfo',

    constructor: function (config) {
    	config = config || {};
    	var MortgageData=config.MortgageData;
    	this.type=config.type;
    //	 this.data=config.data;
    	Ext.apply(config,{
		    title:"查看<抵质押物>基础信息",
		     defaults:{
		                	xtype: 'textfield',
		                	readOnly: true,
		                	width:"100%",
		                	labelAlign:"top"
		                	
		                },
		   items: [
		                    {
		                        label: '<div class="fieldlabel">项目编号:</div>',
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
		                        label:'<div class="fieldlabel">所有权人:</div>',
		                        value:  MortgageData.vProcreditDictionary.assureofnameEnterOrPerson
		                    },
		                    {
		                        label:  '<div class="fieldlabel">备注:</div>',
		                        xtype:'textareafield',
		                        value: MortgageData.vProcreditDictionary.mortgageRemarks
		                    },{
		                        label: '<div class="fieldlabel">抵质押物类型:</div>',
		                        value:  MortgageData.vProcreditDictionary.mortgagepersontypeforvalue
		                    }, {
		                        label:  '<div class="fieldlabel">担保类型:</div>',
		                        value: MortgageData.vProcreditDictionary.assuretypeidValue
		                    }
		                    ,{
		                        label: '<div class="fieldlabel">所有人类型:</div>',
		                        value:  MortgageData.vProcreditDictionary.personTypeValue
		                    }, 
		                    {
		                        label: '<div class="fieldlabel">与债务人的关系:</div>',
		                        value:  MortgageData.vProcreditDictionary.relation
		                    }
		                    /*{
		                        label:  '<div class="fieldlabel">抵质押率:</div>',
		                        value:MortgageData.vProcreditDictionary.assuremoney
		                    }
		                    ,
		                    {
		                        label: '<div class="fieldlabel">评估价值:</div>',
		                        value:MortgageData.vProcreditDictionary.finalprice
		                    },{
		                        label:  '<div class="fieldlabel">公允价值:</div>',
		                        value:MortgageData.vProcreditDictionary.finalCertificationPrice
		                    }
		                    ,{
		                        label: '<div class="fieldlabel">公允价值获取方式:</div>',
		                        value: MortgageData.vProcreditDictionary.valuationMechanism
		                    } , {
		                        label:  '<div class="fieldlabel">获取时间:</div>',
		                        value:MortgageData.vProcreditDictionary.valuationTime!=null? MortgageData.vProcreditDictionary.valuationTime:""
		                    }
		                    ,
		                    {
		                        label: '<div class="fieldlabel">是否办理:</div>',
		                        value: MortgageData.vProcreditDictionary.isTransact==false?'否':'是' 
		                    }
		                    ,{
		                        label: '<div class="fieldlabel">办理时间:</div>',
		                        value: null!=MortgageData.vProcreditDictionary.transactDate?MortgageData.vProcreditDictionary.transactDate:""
		                    },{
		                        label: '<div class="fieldlabel">经办人:</div>',
		                        value: MortgageData.vProcreditDictionary.transactPerson
		                    }
		                    ,
		                    {
		                        label: '<div class="fieldlabel">办理文件:</div>'
		                      //  value: MortgageData.vProcreditDictionary.transactPerson
		                    },{
		                        label: '<div class="fieldlabel">登记号:</div>',
		                        value: MortgageData.vProcreditDictionary.enregisterDepartment
		                    }
		                    ,
		                    {
		                        label: '<div class="fieldlabel">登记机关:</div>',
		                        value: MortgageData.vProcreditDictionary.transactPerson
		                    }
		                    ,
		                    {
		                        label: '<div class="fieldlabel">办理备注:</div>',
		                        value: MortgageData.vProcreditDictionary.transactRemarks
		                    },
		                    
		                    
		                    {
		                        label:  '<div class="fieldlabel">是否解除:</div>',
		                        value: MortgageData.vProcreditDictionary.isunchain==false?'否':'是' 
		                    },
		                    {
		                        label:  '<div class="fieldlabel">解除时间:</div>',
		                        value:  MortgageData.vProcreditDictionary.unchaindate?MortgageData.vProcreditDictionary.unchaindate:""
		                    },
		                    {
		                        label:  '<div class="fieldlabel">经办人:</div>',
		                        value: MortgageData.vProcreditDictionary.unchainPerson
		                    },
		                    {
		                        label:  '<div class="fieldlabel">解除文件:</div>'
		                      //     value: data.procreditMortgageEnterprise.business
		                    },
		                    {
		                        label:  '<div class="fieldlabel">解除备注:</div>',
		                        value: MortgageData.vProcreditDictionary.unchainremark
		                    },
		                    
		                    
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
		                        xtype:"textarea",
		                        value:MortgageData.vProcreditDictionary.needDatumList
		                    }*/
		                ]
		            
    	});

    	this.callParent([config]);
    	
    }

});
