

Ext.define('htmobile.customer.enterprise.EnterpriseDetail', {
    extend: 'Ext.Panel',
    name: 'EnterpriseDetail',
    constructor: function (config) {
    	//底部导航
    	var bottomBar= Ext.create('htmobile.public.bottomBarIndex',{
        });
/*		var toolbar = Ext.create('Ext.Toolbar', {
					docked : 'bottom',
					height:40,
				    layout: 'hbox',     
					items : [{                 
				       xtype: 'panel',    
				       layout: 'vbox', 
				       fullscreen: true,
				       	items : [{  
								xtype : 'spacer'
							}, {
								xtype : 'button',
								text : '家庭信息',
								iconMask : true,
								scope : this,
								flex : .5,
								handler : this.newEmail
							}, {
								xtype : 'button',
								text : '家庭经济情况',
								iconMask : true,
								scope : this,
								flex : .5,
								handler : this.newEmail
							}, {
								xtype : 'button',
								text : '期下公司',
								iconMask : true,
								scope : this,
								flex : .5,
								handler : this.newEmail
							}, {
								xtype : 'button',
								text : '征信记录',
								iconMask : true,
								scope : this,
								flex : .5,
								handler : this.newEmail
							}, {
								xtype : 'button',
								text : '关系人',
								iconMask : true,
								scope : this,
								flex : .5,
								handler : this.newEmail
							}, {
								xtype : 'button',
								text : '配偶信息',
								iconMask : true,
								scope : this,
								flex : .5,
								handler : this.newEmail
							}, {
								xtype : 'button',
								text : '资信评估',
								iconMask : true,
								scope : this,
								flex : .5,
								handler : this.newEmail
							}, {
								xtype : 'button',
								text : '银行开户',
								iconMask : true,
								scope : this,
								flex : .5,
								handler : this.newEmail
							}, {
								xtype : 'button',
								text : '负面调查',
								iconMask : true,
								scope : this,
								flex : .5,
								handler : this.newEmail
							}, {
								xtype : 'button',
								text : '教育情况',
								iconMask : true,
								scope : this,
								flex : .5,
								handler : this.newEmail
							}, {
								xtype : 'button',
								text : '工作经历',
								iconMask : true,
								scope : this,
								flex : .5,
								handler : this.newEmail
							}, {
								xtype : 'button',
								text : '社会活动',
								iconMask : true,
								scope : this,
								flex : .5,
								handler : this.newEmail
							}, {
								xtype : 'button',
								text : '业务往来',
								iconMask : true,
								scope : this,
								flex : .5,
								handler : this.newEmail
							}, {
								xtype : 'spacer'
							}]},{                 
				       xtype: 'panel',    
				       layout: 'vbox', 
				       fullscreen: true,
				       	items : [{  
								xtype : 'spacer'
							}, {
								xtype : 'button',
								text : '家庭信息',
								iconMask : true,
								scope : this,
								flex : .5,
								handler : this.newEmail
							}, {
								xtype : 'button',
								text : '家庭经济情况',
								iconMask : true,
								scope : this,
								flex : .5,
								handler : this.newEmail
							}, {
								xtype : 'button',
								text : '期下公司',
								iconMask : true,
								scope : this,
								flex : .5,
								handler : this.newEmail
							}, {
								xtype : 'button',
								text : '征信记录',
								iconMask : true,
								scope : this,
								flex : .5,
								handler : this.newEmail
							}, {
								xtype : 'button',
								text : '关系人',
								iconMask : true,
								scope : this,
								flex : .5,
								handler : this.newEmail
							}, {
								xtype : 'button',
								text : '配偶信息',
								iconMask : true,
								scope : this,
								flex : .5,
								handler : this.newEmail
							}, {
								xtype : 'button',
								text : '资信评估',
								iconMask : true,
								scope : this,
								flex : .5,
								handler : this.newEmail
							}, {
								xtype : 'button',
								text : '银行开户',
								iconMask : true,
								scope : this,
								flex : .5,
								handler : this.newEmail
							}, {
								xtype : 'button',
								text : '负面调查',
								iconMask : true,
								scope : this,
								flex : .5,
								handler : this.newEmail
							}, {
								xtype : 'button',
								text : '教育情况',
								iconMask : true,
								scope : this,
								flex : .5,
								handler : this.newEmail
							}, {
								xtype : 'button',
								text : '工作经历',
								iconMask : true,
								scope : this,
								flex : .5,
								handler : this.newEmail
							}, {
								xtype : 'button',
								text : '社会活动',
								iconMask : true,
								scope : this,
								flex : .5,
								handler : this.newEmail
							}, {
								xtype : 'button',
								text : '业务往来',
								iconMask : true,
								scope : this,
								flex : .5,
								handler : this.newEmail
							}, {
								xtype : 'spacer'
							}]},{                 
				       xtype: 'panel',    
				       layout: 'vbox', 
				       fullscreen: true,
				       	items : [{  
								xtype : 'spacer'
							}, {
								xtype : 'button',
								text : '家庭信息',
								iconMask : true,
								scope : this,
								flex : .5,
								handler : this.newEmail
							}, {
								xtype : 'button',
								text : '家庭经济情况',
								iconMask : true,
								scope : this,
								flex : .5,
								handler : this.newEmail
							}, {
								xtype : 'button',
								text : '期下公司',
								iconMask : true,
								scope : this,
								flex : .5,
								handler : this.newEmail
							}, {
								xtype : 'button',
								text : '征信记录',
								iconMask : true,
								scope : this,
								flex : .5,
								handler : this.newEmail
							}, {
								xtype : 'button',
								text : '关系人',
								iconMask : true,
								scope : this,
								flex : .5,
								handler : this.newEmail
							}, {
								xtype : 'button',
								text : '配偶信息',
								iconMask : true,
								scope : this,
								flex : .5,
								handler : this.newEmail
							}, {
								xtype : 'button',
								text : '资信评估',
								iconMask : true,
								scope : this,
								flex : .5,
								handler : this.newEmail
							}, {
								xtype : 'button',
								text : '银行开户',
								iconMask : true,
								scope : this,
								flex : .5,
								handler : this.newEmail
							}, {
								xtype : 'button',
								text : '负面调查',
								iconMask : true,
								scope : this,
								flex : .5,
								handler : this.newEmail
							}, {
								xtype : 'button',
								text : '教育情况',
								iconMask : true,
								scope : this,
								flex : .5,
								handler : this.newEmail
							}, {
								xtype : 'button',
								text : '工作经历',
								iconMask : true,
								scope : this,
								flex : .5,
								handler : this.newEmail
							}, {
								xtype : 'button',
								text : '社会活动',
								iconMask : true,
								scope : this,
								flex : .5,
								handler : this.newEmail
							}, {
								xtype : 'button',
								text : '业务往来',
								iconMask : true,
								scope : this,
								flex : .5,
								handler : this.newEmail
							}, {
								xtype : 'spacer'
							}]}]
				});*/
    	Ext.apply(this,config);
    	var data=this.data;
    	if(Ext.isEmpty(config.readOnly)){
    	   this.readOnly=false;
    	}else{
    		this.readOnly=config.readOnly;
    	}
    	Ext.apply(config,{
    		style:'background-color:white;',
		    fullscreen: true,
		    title:this.title,
		    scrollable:{
		    	direction: 'vertical'
		    },
		    items : [bottomBar,{
						xtype : 'fieldset',
						defaults : {
							xtype : 'textfield',
							readOnly : true,
							labelAlign : "left"
						},
						items : [{
									label : '企业名称',
									value : data.enterprisename
								},
								{
									label : '营业执照号码',
									value : data.cciaa
								}, {
									label : '组织机构代码',
									value : data.organizecode
								}, {
									label : '企业联系人',
									value : data.linkman
								}, {
									label : '联系电话',
									value : data.telephone
								}, {
									label : '通讯地址',
									value : data.area
								}, {
									xtype : 'button',
									text : '编辑',
									cls : 'submit-button',
									scope : this,
									handler : this.edit
								}/*
									 * ,{ xtype:'panel', html:'<div
									 * class=\"vmMain\">客户材料'+ '<span
									 * style=\"float:right;\"
									 * onclick=\"javascript:entmartialList();\">>&nbsp;&nbsp;&nbsp;&nbsp;</span></div>' }
									 */
						/*
						 * { label: '<div class="fieldlabel">传真:</div>',
						 * value: data.fax }, { label: '<div
						 * class="fieldlabel">注册资金币种:</div>', xtype :
						 * 'dickeycombo', nodeKey : 'capitalkind', value:
						 * data.capitalkind }, { label: '<div
						 * class="fieldlabel">模板名称:</div>', xtype :
						 * 'selectfield', store:Ext.create('StoreSelect', { url :
						 * __ctxPath +
						 * "/htmobile/getTypesVmInfo.do?customerType=0" }),
						 * value : data.templateId, name :
						 * 'enterprise.templateId' },{ label: '<div
						 * class="fieldlabel">工商注册资金(万):</div>', value:
						 * data.registermoney }, { label : '<div
						 * class="fieldlabel">所有制性质:</div>', xtype :
						 * 'dickeycombo', nodeKey : 'smallloan_haveCharcter',
						 * value: data.ownership }, { label : '<div
						 * class="fieldlabel">企业成立日期:</div>', value:
						 * data.opendate }, { label: '客户授权人:', value:
						 * data.belongedName }, { label: '<div
						 * class="fieldlabel">职工人数:</div>', xtype :
						 * "dickeycombo", nodeKey : 'workforce', value:
						 * data.employeetotal }, { label : '<div
						 * class="fieldlabel">经营场所:</div>', xtype :
						 * "dickeycombo", nodeKey : 'jycs', // xx代表分类名称 value :
						 * data.jyplace }, { label : '<div
						 * class="fieldlabel">面积(平米):</div>', name :
						 * 'person.areaMeasure', value : data.areaMeasure }, {
						 * label : '<div class="fieldlabel">房租(元):</div>',
						 * name : 'person.rent', value : data.rent }, { label : '<div
						 * class="fieldlabel">进/出口许可证:</div>', name :
						 * 'person.isLicense', xtype : 'togglefield', value :
						 * data.isLicense },{ label : '<div
						 * class="fieldlabel">经营所在地城市:</div>', name :
						 * 'person.managecityName', value : data.managecityName
						 * 
						 * },{ label : '<div class="fieldlabel">电子邮箱:</div>',
						 * name : 'person.email', value : data.email }, { label : '<div
						 * class="fieldlabel">收件人:</div>', value :
						 * data.receiveMail, name : 'person.receiveMail' }, {
						 * label : '<div class="fieldlabel">通讯地址:</div>',
						 * xtype : 'selectfield', value : data.area, name :
						 * 'person.area' }, { label : '<div
						 * class="fieldlabel">邮政编码:</div>', value :
						 * data.postcoding, name : 'person.postcoding' }, {
						 * label : '<div class="fieldlabel">客户级别:</div>',
						 * value : data.customerLevel, xtype : "dickeycombo",
						 * nodeKey : 'customerLevel', // xx代表分类名称 name :
						 * 'person.customerLevel' }, { label : '<div
						 * class="fieldlabel">实际经营地址:</div>', value :
						 * data.factaddress, name : 'person.factaddress' }, {
						 * label : '<div class="fieldlabel">企业网址:</div>',
						 * value : data.website, name : 'person.website' }, {
						 * label : '<div class="fieldlabel">税务登记号:</div>',
						 * value : data.taxnum, name : 'person.taxnum' }, {
						 * label : '<div class="fieldlabel">客户授权人:</div>',
						 * value : data.belongedName, name :
						 * 'person.belongedName' }
						 */

						]
					}]
    	});

    /*
	 * Ext.apply(config,{ fullscreen: true, scrollable:{ direction: 'vertical' },
	 * layout : 'vbox', defaults:{ xtype: 'textfield', readOnly: true,
	 * style:'font-size:12px;color:#a7573b', labelWidth:"20%" // labelAlign :
	 * "right" }, items: [{ label: '中文姓名', value: data.name }, { label:
	 * '拼音/英文姓名:', value: data.name }, { label: '证件类型:', value:
	 * data.cardtypevalue }, { label: '证件号码:', value: data.cardnumber }, {
	 * label: '性别:', value: data.sexvalue }, { label: '出生日期:', value:
	 * data.birthday } ,{ layout : 'vbox', xtype: 'panel', items: [ { label:
	 * '中文姓名', value: data.name }, { label: '拼音/英文姓名:', value: data.name }, {
	 * label: '证件类型:', value: data.cardtypevalue }, { label: '证件号码:', value:
	 * data.cardnumber }, { label: '性别:', value: data.sexvalue }, { label:
	 * '出生日期:', value: data.birthday } ]}]
	 * 
	 * });
	 */
       var readOnly=this.readOnly;
    	this.callParent([config]);
       entmartialList=function(){
    	  var	mark='cs_enterprise.';
         var	typeisfile=null;
         var	title='企业客户材料';
         mobileNavi.push(
         Ext.create('htmobile.creditFlow.public.file.FileList',
         	{ 
         	  mark:mark,
         	 readOnly: readOnly,
	         typeisfile:typeisfile,
	         tableid:data.id,
			 title:title}
         	));
    	 	
    	 /*	data.type="cs_person";
    	   mobileNavi.push(
    		  Ext.create('htmobile.InformationCollection.person.personMaterialsList',{
				          data:data
			        	})
    		);*/
    		
    	}
    },detail:function(){
       mobileNavi.push(
		Ext.create('htmobile.customer.enterprise.EnterpriseMenu',{
			        data:this.data
		        	})
		    	);
    },edit:function(){
    	 mobileNavi.push(
			             Ext.create('htmobile.InformationCollection.enterprise.editEnterpriseBbaseInfo',{
			        		data:this.data
			        	})
		    	);
    }

});
