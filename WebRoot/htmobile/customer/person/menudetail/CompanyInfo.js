

Ext.define('htmobile.customer.person.menudetail.CompanyInfo', {
    extend: 'Ext.Panel',
    
    name: 'CompanyInfo',

    constructor: function (config) {
    	config = config || {};
    	var data=config.data;
    	Ext.apply(config,{
		    fullscreen: true,
		    title:"个人所在公司信息",
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
		                        label: '<div class="fieldlabel">工作单位:</div>',
		                        value: data.currentcompany
		                    },
		                    {
		                        label: '<div class="fieldlabel">传真:</div>',
		                        value: data.companyFax
		                    },
		                    {
		                        label: '<div class="fieldlabel">单位性质:</div>',
		                        xtype : "dickeycombo",
							    nodeKey : 'unitproperties',
		                        value: data.unitproperties
		                    },
		                    {
		                        label: '<div class="fieldlabel">公司地址:</div>',
		                        value: data.unitaddress
		                    },
		                    {
		                        label: '<div class="fieldlabel">单位电话:</div>',
		                        value: data.unitphone
		                    }/*,{
		                        label: '居住状况',
		                        value: data.isheadoffamily
		                    },
		                    {
		                        label: '现住宅形式',
		                        value: data.familyaddress
		                    },*/,{
		                        label: '<div class="fieldlabel">邮政编码:</div>',
		                        value: data.unitpostcode
		                    },
		                    {
		                        label: '<div class="fieldlabel">行业类别:</div>',
		                        value: data.hangyeName
		                    }
		                    ,{
		                        label: '<div class="fieldlabel">公司规模:</div>',
		                        xtype : "dickeycombo",
								nodeKey : 'companyScale',
		                        value: data.companyScale
		                    },
		                    {
		                        label: '<div class="fieldlabel">职务:</div>',
		                        	xtype : "dickeycombo",
								nodeKey : 'zhiwujob',
		                        value: data.recordAndRemarks
		                    }
		                    
		                    ,
		                    {
		                        label: '<div class="fieldlabel">入职时间:</div>',
		                        value: data.jobstarttime
		                    }
		                    ,{
		                        label: '<div class="fieldlabel">所属部门:</div>',
		                        value: data.department
		                    },
		                    {
		                        label: '<div class="fieldlabel">发薪时间:</div>',
		                        value: data.payDate
		                    }
		                    ,
		                    {
		                        label: '<div class="fieldlabel">发薪形式:</div>',
		                        value: data.wagebank
		                    }
		                    ,{
		                        label: '<div class="fieldlabel">工作城市:省</div>',
		                        xtype : 'selectfield',
								store:Ext.create('StoreSelect', {
				          	         url : __ctxPath + "/htmobile/listByParentIdVmInfo.do?parentId=6591"
				                }),
		                        value: data.parentHireCity
		                    },
		                    {
		                    	  label: '<div class="fieldlabel">市</div>',
		                         xtype : 'selectfield',
								store:Ext.create('StoreSelect', {
				          	         url : __ctxPath + "/htmobile/listByParentIdVmInfo.do?parentId="+data.parentHireCity
				                }),
		                        value: data.hireCity
		                    }
		                    ,
		                    {
		                        label: '<div class="fieldlabel">工作邮箱:</div>',
		                        value: data.hireEmail
		                    }
		                    ,{
		                        label: '<div class="fieldlabel">网店经营年限:</div>',
		                        value: data.webstarttime
		                    },
		                    {
		                        label: '<div class="fieldlabel">经营起始时间:</div>',
		                        value: data.bossstarttime
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
