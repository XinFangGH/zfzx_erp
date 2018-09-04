

Ext.define('htmobile.customer.person.PersonDetail', {
    extend: 'Ext.Panel',
    name: 'PersonDetail',
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
		var data=this.result;
    	Ext.apply(config,{
    		title:'个人客户信息',
    		style:'background-color:white;',
		    fullscreen: true,
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
							label : '姓名',
							value : data.name
						}, {
							label : '证件号码',
							value : data.cardnumber
						}, {
							label : '手机号码',
							value : data.cellphone
						}, {
							label : '性别',
							xtype : "dickeycombo",
							nodeKey : 'sex_key',
							value : data.sex
						}, {
							label : '婚姻状况',
							xtype : "dickeycombo",
							nodeKey : '8',
							value : data.marry
						}, {
							label : '分公司',
							xtype : 'selectfield',
							store : Ext.create('StoreSelect', {
								url : __ctxPath
										+ "/system/getShopList1Organization.do?isMobile=1"
							}),
							value : data.shopId
						}, {
							xtype : 'textareafield',
							value : data.postaddress,
							label : '通讯地址'
						},{
							xtype:'button',
							text:'编辑',
							scope:this,
							handler:this.edit,
							cls:'submit-button'
						}/*, {
							xtype : 'textareafield',
							value : data.postaddress,
							label : '身份证照片'
						}*//*
							 * ,{ xtype:'panel', html:'<div
							 * class=\"vmMain\">客户材料'+ '<span
							 * style=\"float:right;\"
							 * onclick=\"javascript:martialList();\">>&nbsp;&nbsp;&nbsp;&nbsp;</span></div>' }
							 *//*
							 * { label: '<div class="fieldlabel">拼音/英文姓名:</div>',
							 * value: data.englishname }, { label: '<div
							 * class="fieldlabel">客户授权人:</div>', value:
							 * data.belongedName }, { label: '<div
							 * class="fieldlabel">证件类型:</div>', xtype :
							 * "dickeycombo", nodeKey : 'card_type_key', value:
							 * data.cardtypevalue },
							 */
				/*
				 * { label: '<div class="fieldlabel">证件有效期至:</div>', value:
				 * data.validity },
				 */
				/*
				 * , { label: '<div class="fieldlabel">出生日期:</div>', value:
				 * data.birthday },{ label: '<div class="fieldlabel">客户级别:</div>',
				 * xtype : "dickeycombo", nodeKey : 'customerLevel', value:
				 * data.customerLevel }, { label : '<div class="fieldlabel">民族:</div>',
				 * xtype : "dickeycombo", nodeKey : 'nationality', value:
				 * data.nationality }
				 *//*
					 * , { label: '客户授权人:', value: data.belongedName }, {
					 * xtype:'tellphone', label:'手机号码:', value:data.cellphone }, {
					 * label : '<div class="fieldlabel">家庭电话:</div>', name :
					 * 'person.telphone', value : data.telphone }, { label : '<div
					 * class="fieldlabel">单位电话:</div>', name :
					 * 'person.unitphone', value : data.unitphone }, { label : '<div
					 * class="fieldlabel">传真号码:</div>', name : 'person.fax',
					 * value : data.fax }, { label : '<div
					 * class="fieldlabel">职业类型:</div>', name :
					 * 'person.careerType', xtype : 'selectfield', options : [{
					 * text : '网商', value : 0 }, { text : '工薪', value : 1 }, {
					 * text : '私营企业主', value : 2 }, { text : '教师', value : 3 }],
					 * value : data.careerType },{ label : '<div
					 * class="fieldlabel">电子邮箱:</div>', name :
					 * 'person.selfemail', value : data.selfemail
					 * 
					 * },{ label : '<div class="fieldlabel">邮政编码:</div>', name :
					 * 'person.postcode', value : data.postcode }, { label : '<div
					 * class="fieldlabel">籍贯:省:</div>', xtype : 'selectfield',
					 * store:Ext.create('StoreSelect', { url : __ctxPath +
					 * "/htmobile/listByParentIdVmInfo.do?parentId=6591" }),
					 * value : data.parentHomeland, name :
					 * 'person.parentHomeland' }, { label : '<div
					 * class="fieldlabel">市:</div>', xtype : 'selectfield',
					 * store:Ext.create('StoreSelect', { url : __ctxPath +
					 * "/htmobile/listByParentIdVmInfo.do?parentId="+data.parentHomeland
					 * }), value : data.homeland, name : 'person.homeland' }, {
					 * label : '<div class="fieldlabel">居住城市:省:</div>', xtype :
					 * 'selectfield', store:Ext.create('StoreSelect', { url :
					 * __ctxPath +
					 * "/htmobile/listByParentIdVmInfo.do?parentId=6591" }),
					 * value : data.parentLiveCity, name :
					 * 'person.parentLiveCity' }, { label : '<div
					 * class="fieldlabel">市:</div>', value : data.liveCity,
					 * xtype : 'selectfield', store:Ext.create('StoreSelect', {
					 * url : __ctxPath +
					 * "/htmobile/listByParentIdVmInfo.do?parentId="+
					 * data.parentLiveCity }), name : 'person.liveCity' }, {
					 * label : '<div class="fieldlabel">最高学历入学年份:</div>',
					 * value : data.collegeYear, name : 'person.collegeYear' }, {
					 * label : '<div class="fieldlabel">毕业院校:</div>', value :
					 * data.graduationunversity, name :
					 * 'person.graduationunversity' }, { label : '<div
					 * class="fieldlabel">本地居住年限:</div>', value :
					 * data.livingLife, name : 'person.livingLife' }, { label : '<div
					 * class="fieldlabel">共同居住者:</div>', value :
					 * data.relationname, name : 'person.relationname' } , {
					 * label : '<div class="fieldlabel">单张信用卡最高额度:</div>',
					 * value : data.befMonthBalance, name :
					 * 'person.befMonthBalance' }, { label : '<div
					 * class="fieldlabel">户口所在地:省:</div>', xtype :
					 * 'selectfield', store:Ext.create('StoreSelect', { url :
					 * __ctxPath +
					 * "/htmobile/listByParentIdVmInfo.do?parentId=6591" }),
					 * value : data.parenthukou, name : 'person.parenthukou' }, {
					 * label : '<div class="fieldlabel">市:</div>', xtype :
					 * 'selectfield', store:Ext.create('StoreSelect', { url :
					 * __ctxPath +
					 * "/htmobile/listByParentIdVmInfo.do?parentId="+data.parenthukou
					 * }), value : data.hukou, name : 'person.hukou' }, { label : '<div
					 * class="fieldlabel">QQ:</div>', value : data.qq, name :
					 * 'person.qq' }, { label : '<div class="fieldlabel">微信:</div>',
					 * value : data.microMessage, name : 'person.microMessage' }
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

    	 this.callParent([config]);
    	 
    	 var readOnly=this.readOnly;
    	 martialList=function(){
    	 var	mark='cs_person.';
         var	typeisfile=null;
         var	title='个人客户材料';
       mobileNavi.push(
         Ext.create('htmobile.creditFlow.public.file.FileList',
         	{ 
         	  mark:mark,
         	 readOnly: readOnly,
	         typeisfile:typeisfile,
	         tableid:data.id,
			 title:title}
         	));
    	 	
    	 	/*data.type="cs_person.";
    	   mobileNavi.push(
    		  Ext.create('htmobile.InformationCollection.person.personMaterialsList',{
				          data:data
			        	})
    		);*/
    		
    	}
    },detail:function(){
       mobileNavi.push(
		Ext.create('htmobile.customer.person.PersonMenu',{
			        data:this.result
		        	})
		    	);
    },edit:function(){
   	    mobileNavi.push(Ext.create('htmobile.InformationCollection.person.editPersoBbaseInfo',{
				          data:this.result
			        	}));
   
    }
});
