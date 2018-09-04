

Ext.define('htmobile.customer.enterprise.menudetail.EnterpriseDetailmore', {
    extend: 'Ext.Panel',
    
    name: 'EnterpriseDetail',

    constructor: function (config) {
    	config = config || {};
    	var data=config.data;
    	this.data=config.data;
    	this.persondata=config.persondata;
    	Ext.apply(config,{
		    fullscreen: true,
		    title:'企业信息',
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
		                        label: '<div class="fieldlabel">企业名称:</div>',
		                        value: data.enterprisename
		                    },
		                    {
		                        label: '<div class="fieldlabel">企业简称:</div>',
		                        value: data.shortname
		                    },
		                    {
		                        label: '<div class="fieldlabel">行业类别:</div>',
		                        value: data.hangyeName
		                    },
		                    {
		                        label: '<div class="fieldlabel">营业执照号码:</div>',
		                        value: data.cciaa
		                    },
		                    {
		                        label: '<div class="fieldlabel">组织机构代码:</div>',
		                        value: data.organizecode
		                    },
		                    {
		                        label: '<div class="fieldlabel">企业联系人:</div>',
		                        value: data.linkman
		                    },
		                    {
		                        label: '<div class="fieldlabel">联系电话:</div>',
		                        xtype:'tellphone',
		                        value: data.telephone
		                    },
		                    {
		                        label: '<div class="fieldlabel">传真:</div>',
		                        value: data.fax
		                    },
		                    {
		                        label: '<div class="fieldlabel">注册资金币种:</div>',
		                        xtype : 'dickeycombo',
								nodeKey : 'capitalkind',
		                        value: data.capitalkind
		                    },
		                    {
		                        label: '<div class="fieldlabel">模板名称:</div>',
		                        xtype : 'selectfield',
								store:Ext.create('StoreSelect', {
				          	        url :  __ctxPath + "/htmobile/getTypesVmInfo.do?customerType=0"
				                }),
								value : data.templateId,
								name : 'enterprise.templateId'
		                    },{
		                        label: '<div class="fieldlabel">工商注册资金(万):</div>',
		                         value: data.registermoney
		                    },
		                    {
		                       label : '<div class="fieldlabel">所有制性质:</div>',
		                        xtype : 'dickeycombo',
								nodeKey : 'smallloan_haveCharcter',
		                        value: data.ownership
		                    },
		                    {
		                       label : '<div class="fieldlabel">企业成立日期:</div>',
		                        value: data.opendate
		                    },
		                   /* {
		                        label: '客户授权人:',
		                        value: data.belongedName
		                    },*/
		                    
		                    {
		                        label: '<div class="fieldlabel">职工人数:</div>',
		                        xtype : "dickeycombo",
								nodeKey : 'workforce',
		                        value: data.employeetotal
		                    }, {
							label : '<div class="fieldlabel">经营场所:</div>',
							xtype : "dickeycombo",
							nodeKey : 'jycs', // xx代表分类名称
							value : data.jyplace
							}, {
							label : '<div class="fieldlabel">面积(平米):</div>',
							name : 'person.areaMeasure',
							value : data.areaMeasure
							}, {
							label : '<div class="fieldlabel">房租(元):</div>',
							name : 'person.rent',
							value : data.rent
						}, {
							label : '<div class="fieldlabel">进/出口许可证:</div>',
							name : 'person.isLicense',
							xtype : 'togglefield',
							value : data.isLicense
						},{
							label : '<div class="fieldlabel">经营所在地城市:</div>',
							name : 'person.managecityName',
							value : data.managecityName

						},{
							label : '<div class="fieldlabel">电子邮箱:</div>',
							name : 'person.email',
							value : data.email

						}, {
							label : '<div class="fieldlabel">收件人:</div>',
							value : data.receiveMail,
							name : 'person.receiveMail'
						}, {
							label : '<div class="fieldlabel">通讯地址:</div>',
							xtype : 'selectfield',
							value : data.area,
							name : 'person.area'
						}, {
							label : '<div class="fieldlabel">邮政编码:</div>',
							value : data.postcoding,
							name : 'person.postcoding'
						}, {
							label : '<div class="fieldlabel">客户级别:</div>',
							value : data.customerLevel,
							xtype : "dickeycombo",
							nodeKey : 'customerLevel', // xx代表分类名称
							name : 'person.customerLevel'
						}, {
							label : '<div class="fieldlabel">实际经营地址:</div>',
							value : data.factaddress,
							name : 'person.factaddress'
						}, {
							label : '<div class="fieldlabel">企业网址:</div>',
							value : data.website,
							name : 'person.website'
						}, {
							label : '<div class="fieldlabel">税务登记号:</div>',
							value : data.taxnum,
							name : 'person.taxnum'
						}, {
							label : '<div class="fieldlabel">客户授权人:</div>',
							value : data.belongedName,
							name : 'person.belongedName'
						}
						
		          
		          ]}]
    	});


    	this.callParent([config]);
    	
    },
    detail:function(){
       mobileNavi.push(
		Ext.create('htmobile.customer.enterprise.EnterpriseMenu',{
			        data:this.data,
			        persondata:this.persondata
		        	})
		    	);
    }

});
