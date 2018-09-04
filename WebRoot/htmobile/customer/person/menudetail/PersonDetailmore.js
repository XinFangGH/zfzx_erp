

Ext.define('htmobile.customer.person.menudetail.PersonDetailmore', {
    extend: 'Ext.Panel',
    
    name: 'PersonDetail',

    constructor: function (config) {

    	config = config || {};
    	var data=config.data;
    	this.data=config.data;
    	Ext.apply(config,{
		    fullscreen: true,
		     title:'个人信息',
		    scrollable:{
		    	direction: 'vertical'
		    },
		    items: [
		            {
		                xtype: 'fieldset',
		                defaults:{
		                	xtype: 'textfield',
		                	readOnly: true,
		                	labelAlign:"top"
		                },
		                items: [{
		                        label: '<div class="fieldlabel">中文姓名:</div>',
		                        value: data.name
		                    },
		                    {
		                        label: '<div class="fieldlabel">拼音/英文姓名:</div>',
		                        value: data.englishname
		                    },
		                    {
		                        label: '<div class="fieldlabel">客户授权人:</div>',
		                        value: data.belongedName
		                    },
		                    {
		                        label: '<div class="fieldlabel">证件类型:</div>',
		                        xtype : "dickeycombo",
								nodeKey : 'card_type_key',
		                        value: data.cardtypevalue
		                    },
		                    {
		                        label: '<div class="fieldlabel">证件号码:</div>',
		                        value: data.cardnumber
		                    },
		                    {
		                        label: '<div class="fieldlabel">证件有效期至:</div>',
		                        value: data.validity
		                    },
		                    {
		                        label: '<div class="fieldlabel">性别:</div>',
		                        xtype : "dickeycombo",
								nodeKey : 'sex_key',
		                        value: data.sex
		                    },
		                    {
		                        label: '<div class="fieldlabel">出生日期:</div>',
		                        value: data.birthday
		                    },{
		                        label: '<div class="fieldlabel">客户级别:</div>',
		                        xtype : "dickeycombo",
		                        nodeKey : 'customerLevel',
		                         value: data.customerLevel
		                    },
		                    {
		                       label : '<div class="fieldlabel">民族:</div>',
		                        xtype : "dickeycombo",
		                        nodeKey : 'nationality',
		                        value: data.nationality
		                    },
		                    {
		                       label : '<div class="fieldlabel">婚姻状况:</div>',
		                       xtype : "dickeycombo",
							   nodeKey : '8',
		                        value: data.marry
		                    },
		                   /* {
		                        label: '客户授权人:',
		                        value: data.belongedName
		                    },*/
		                    
		                    {
		                    	xtype:'tellphone',
		                    	label:'手机号码:',
		                    	value:data.cellphone
		                    	
		                    	}, {
							label : '<div class="fieldlabel">家庭电话:</div>',
							name : 'person.telphone',
							value : data.telphone
							}, {
							label : '<div class="fieldlabel">单位电话:</div>',
							name : 'person.unitphone',
							value : data.unitphone
							}, {
							label : '<div class="fieldlabel">传真号码:</div>',
							name : 'person.fax',
							value : data.fax
						}/*, {
							label : '<div class="fieldlabel">职业类型:</div>',
							name : 'person.careerType',
							xtype : 'selectfield',
							options : [{
										text : '网商',
										value : 0
									}, {
										text : '工薪',
										value : 1
									}, {
										text : '私营企业主',
										value : 2
									}, {
										text : '教师',
										value : 3
									}],
							value : data.careerType
						}*/,{
							label : '<div class="fieldlabel">电子邮箱:</div>',
							name : 'person.selfemail',
							value : data.selfemail

						},{
							label : '<div class="fieldlabel">邮政编码:</div>',
							name : 'person.postcode',
							value : data.postcode

						}/*, {
							label : '<div class="fieldlabel">籍贯:省:</div>',
							xtype : 'selectfield',
							store:Ext.create('StoreSelect', {
			          	         url : __ctxPath + "/htmobile/listByParentIdVmInfo.do?parentId=6591"
			                }),
							value : data.parentHomeland,
							name : 'person.parentHomeland'
						}, {
							label : '<div class="fieldlabel">市:</div>',
							xtype : 'selectfield',
							store:Ext.create('StoreSelect', {
			          	         url : __ctxPath + "/htmobile/listByParentIdVmInfo.do?parentId="+data.parentHomeland
			                }),
							value : data.homeland,
							name : 'person.homeland'
						}, {
							label : '<div class="fieldlabel">居住城市:省:</div>',
							xtype : 'selectfield',
							store:Ext.create('StoreSelect', {
			          	         url : __ctxPath + "/htmobile/listByParentIdVmInfo.do?parentId=6591"
			                }),
							value : data.parentLiveCity,
							name : 'person.parentLiveCity'
						}, {
							label : '<div class="fieldlabel">市:</div>',
							value : data.liveCity,
							xtype : 'selectfield',
							store:Ext.create('StoreSelect', {
			          	         url : __ctxPath + "/htmobile/listByParentIdVmInfo.do?parentId="+ data.parentLiveCity
			                }),
							name : 'person.liveCity'
						}*/, {
							label : '<div class="fieldlabel">最高学历入学年份:</div>',
							value : data.collegeYear,
							name : 'person.collegeYear'
						}, {
							label : '<div class="fieldlabel">毕业院校:</div>',
							value : data.graduationunversity,
							name : 'person.graduationunversity'
						}, {
							label : '<div class="fieldlabel">本地居住年限:</div>',
							value : data.livingLife,
							name : 'person.livingLife'
						}, {
							label : '<div class="fieldlabel">共同居住者:</div>',
							value : data.relationname,
							name : 'person.relationname'
						}
						, {
							label : '<div class="fieldlabel">单张信用卡最高额度:</div>',
							value : data.befMonthBalance,
							name : 'person.befMonthBalance'
						}/*, {
							label : '<div class="fieldlabel">户口所在地:省:</div>',
							xtype : 'selectfield',
							store:Ext.create('StoreSelect', {
			          	         url : __ctxPath + "/htmobile/listByParentIdVmInfo.do?parentId=6591"
			                }),
							value : data.parenthukou,
							name : 'person.parenthukou'
						}, {
							label : '<div class="fieldlabel">市:</div>',
							xtype : 'selectfield',
							store:Ext.create('StoreSelect', {
			          	         url : __ctxPath + "/htmobile/listByParentIdVmInfo.do?parentId="+data.parenthukou
			                }),
							value : data.hukou,
							name : 'person.hukou'
						}*/, {
							label : '<div class="fieldlabel">QQ:</div>',
							value : data.qq,
							name : 'person.qq'
						}, {
							label : '<div class="fieldlabel">微信:</div>',
							value : data.microMessage,
							name : 'person.microMessage'
						}
		          
		          ]}]
    	});

  

    	this.callParent([config]);
    	
    }

});
