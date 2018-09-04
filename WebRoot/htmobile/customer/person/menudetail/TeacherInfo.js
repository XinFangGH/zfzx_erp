

Ext.define('htmobile.customer.person.menudetail.TeacherInfo', {
    extend: 'Ext.Panel',
    
    name: 'TeacherInfo',

    constructor: function (config) {
    	config = config || {};
    	var data=config.data;
    	Ext.apply(config,{
		    fullscreen: true,
		    title:"教师工作信息",
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
		                        value: data.teacherCompanyName
		                    },
		                    {
		                        label: '<div class="fieldlabel">职务:</div>',
		                        value: data.teacherPosition,
		                        xtype : "dickeycombo",
								nodeKey : 'zhiwujob'
		                    },
		                    {
		                        label: '<div class="fieldlabel">公司地址:</div>',
		                        value: data.teacherAddress
		                    },
		                    {
		                        label: '<div class="fieldlabel">执教时间:</div>',
		                        value: data.teacherStartYear
		                    },
		                    {
		                        label: '<div class="fieldlabel">单位电话:</div>',
		                        value: data.teacherCompanyPhone
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
		                        label: '<div class="fieldlabel">月收入:</div>',
		                        value: data.teacherMonthlyIncome
		                    }
		                    ,{
		                        label: '<div class="fieldlabel">发薪时间:</div>',
		                        value: data.payDate
		                    },
		                    {
		                        label: '<div class="fieldlabel">发薪形式:</div>',
		                        value: data.wagebank
		                    },
		                    {
							label : '<div class="fieldlabel">工作城市:省:</div>',
							xtype : 'selectfield',
							store:Ext.create('StoreSelect', {
			          	         url : __ctxPath + "/htmobile/listByParentIdVmInfo.do?parentId=6591"
			                }),
							value : data.parentHireCity,
							name : 'person.parentHireCity'
						}, {
							label : '<div class="fieldlabel">市:</div>',
							xtype : 'selectfield',
							store:Ext.create('StoreSelect', {
			          	         url : __ctxPath + "/htmobile/listByParentIdVmInfo.do?parentId="+data.parentHireCity
			                }),
							value : data.teacherCity,
							name : 'person.teacherCity'
						},
		                    {
		                        label: '<div class="fieldlabel">工作邮箱:</div>',
		                        value: data.teacherEmail
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
