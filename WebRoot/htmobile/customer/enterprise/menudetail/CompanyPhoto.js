

Ext.define('htmobile.customer.enterprise.menudetail.CompanyPhoto', {
    extend: 'Ext.Panel',
    
    name: 'CompanyPhoto',

    constructor: function (config) {
    	config = config || {};
    	var data=config.data;
    	var enterpriseYyzzURL=__ctxPath+"/"+((typeof(data) == "undefined" ||Ext.isEmpty(data.enterpriseYyzzURL))?"images/nopic.jpg":+data.enterpriseYyzzURL);
    	var enterpriseZzjgURL=__ctxPath+"/"+((typeof(data) == "undefined" ||Ext.isEmpty(data.enterpriseZzjgURL))?"images/nopic.jpg":data.enterpriseZzjgURL);
    	var enterpriseGsdjzURL=__ctxPath+"/"+((typeof(data) == "undefined" ||Ext.isEmpty(data.enterpriseGsdjzURL))?"images/nopic.jpg":data.enterpriseGsdjzURL);
    	var enterpriseDsdjURL=__ctxPath+"/"+((typeof(data) == "undefined" ||Ext.isEmpty(data.enterpriseDsdjURL))?"images/nopic.jpg":data.enterpriseDsdjURL);
    	Ext.apply(config,{
		    fullscreen: true,
		    title:"身份证扫描件",
		    scrollable:{
		    	direction: 'vertical'
		    },
		    items: [
		            {
		                xtype: 'fieldset',
		                defaults:{
		                	xtype: 'panel',
		                	readOnly: true,
		                	width:"100%",
		                	labelAlign:"top"
		                	
		                },
		                items: [
		                    {
		                        html:'<div>营业执照副本 </div><img src="'
														+ enterpriseYyzzURL
														+ '" width="100%" height="50%"/>'
		                    },
		                    {
		                        html:'<div>组织机构代码证</div><img src="'
														+enterpriseZzjgURL
														+ '" width="100%" height="50%"/>'
		                    },
		                     {
		                        html:'<div>税务证扫描件 </div><img src="'
														+enterpriseGsdjzURL
														+ '" width="100%" height="50%"/>'
		                    },
		                    {
		                        html:'<div>贷款卡扫描件</div><img src="'
														+ enterpriseDsdjURL
														+ '" width="100%" height="50%"/>'
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
