

Ext.define('htmobile.customer.enterprise.menudetail.LegalPerosnInfo', {
    extend: 'Ext.Panel',
    
    name: 'LegalPerosnInfo',

    constructor: function (config) {
    	config = config || {};
    	var data=config.persondata;
    	var personSFZZUrl=__ctxPath+"/"+((typeof(data) == "undefined" ||Ext.isEmpty(data.personSFZZUrl))?"/images/nopic.jpg":data.personSFZZUrl);
    	var personSFZFUrl=__ctxPath+"/"+((typeof(data) == "undefined" ||Ext.isEmpty(data.personSFZFUrl))?"/images/nopic.jpg":data.personSFZFUrl);
    	Ext.apply(config,{
		    fullscreen: true,
		    title:"法定代表人信息",
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
		                        label: '<div class="fieldlabel">法定代表人姓名:</div>',
		                        value:(typeof(data) == "undefined")?"":data.name
		                    },
		                    {
		                        label: '<div class="fieldlabel">性别:</div>',
		                        value:(typeof(data) == "undefined")?"":data.sex
		                    },
		                    {
		                        label: '<div class="fieldlabel">证件类型:</div>',
		                         xtype : "dickeycombo",
								nodeKey : 'card_type_key',
		                       value:(typeof(data) == "undefined")?"":data.cardtypevalue
		                    },
		                    {
		                        label: '<div class="fieldlabel">证件号码:</div>',
		                       value:(typeof(data) == "undefined")?"":data.cardnumber
		                    },
		                    {
		                        label: '<div class="fieldlabel">手机号码:</div>',
		                      value:(typeof(data) == "undefined")?"":data.cellphone
		                    }/*,{
		                        label: '居住状况',
		                        value: data.isheadoffamily
		                    },
		                    {
		                        label: '现住宅形式',
		                        value: data.familyaddress
		                    },*/,{
		                        label: '<div class="fieldlabel">电子邮箱:</div>',
		                       value:(typeof(data) == "undefined")?"":data.selfemail
		                    }
		                   
		                ]
		            },
		            {
		                xtype: 'fieldset',
		                title:'法定代表人身份证扫描件',
		                defaults:{
		                	xtype: 'textfield',
		                	readOnly: true,
		                	width:"100%",
		                	labelAlign:"top"
		                	
		                },
		                items: [
		                     {
		                        html:'<div>身份证正面 </div><img src="'
														+ 
														personSFZZUrl
														+ '" width="100%" height="50%"/>'
		                    },
		                    {
		                        html:'<div>身份证反面 </div><img src="'
														+ 
													personSFZFUrl
														+ '" width="100%" height="50%"/>'
		                    }]
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
