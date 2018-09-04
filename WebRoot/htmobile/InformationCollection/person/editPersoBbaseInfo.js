

Ext.define('htmobile.InformationCollection.person.editPersoBbaseInfo', {
    extend: 'Ext.form.Panel',
    
    name: 'editPersoBbaseInfo',

    constructor: function (config) {
    	config = config || {};
    	var data=config.data;
    	Ext.apply(config,{
		    fullscreen: true,
		    title:"客户基本信息",
		    scrollable:{
		    	direction: 'vertical'
		    },
		    items: [
    			{
	    			xtype: 'fieldset',
	    			defaults:{
	    				 labelWidth:document.body.clientWidth/3,
	    				 clearIcon : true
	    			},
	    			items : [{
							xtype : 'hiddenfield',
							name : 'person.id',
							label : '名字',
							value : data.id
						}, {
							xtype : 'textfield',
							labelWidth : '22%',
							name : 'person.name',
							label : '名字',
							value : data.name
						}, {
							xtype : 'textfield',
							labelWidth : '22%',
							name : 'person.cardnumber',
							label : '身份证号码',
							value : data.cardnumber,
							listeners : {
								scope : this,
								'blur' : function(f) {

									var cardNumber = f.getValue();
									if (validateIdCard(cardNumber) == 1) {
										Ext.Msg
												.alert('',
														"<font color='#12b7f5'>证件号码不正确,请仔细核对</font>");
										return;
									} else if (validateIdCard(cardNumber) == 2) {
										Ext.Msg
												.alert('',
														"<font color='#12b7f5'>证件号码地区不正确,请仔细核对</font>");
										return;

									} else if (validateIdCard(cardNumber) == 3) {
										Ext.Msg
												.alert('',
														"<font color='#12b7f5'>证件号码生日日期不正确,请仔细核对</font>");
										return;
									}
									var personId = (personData == null)
											? 0
											: personData.personId;
									Ext.Ajax.request({
										url : __ctxPath
												+ '/creditFlow/customer/person/verificationPerson.do',
										method : 'POST',
										params : {
											cardNum : cardNumber,
											personId : personId
										},
										success : function(response, request) {
											var obj = Ext.util.JSON
													.decode(response.responseText);
											if (obj.msg == "false") {
												Ext.ux.Toast
														.msg('',
																"<font color='#12b7f5'>该证件号码已存在，请重新输入</font>");
												f.setValue("");
												// penal.setValue("");
											}
										}
									});
								}
							}
						}, {
							xtype : 'textfield',
							labelWidth : '22%',
							value : data.cellphone,
							name : 'person.cellphone',
							label : '手机号码'
						}, {
							name : 'person.sex',
							value : data.sex,
							labelWidth : '22%',
							xtype : "dickeycombo",
							nodeKey : 'sex_key',
							hiddenName : 'person.sex',
							label : '性别'
						}, {
							name : 'person.marry',
							value : data.marry,
							labelWidth : '22%',
							xtype : "dickeycombo",
							nodeKey : '8',
							hiddenName : 'person.marry',
							label : '婚姻状况'
						}, {
							labelWidth : '22%',
							value : data.shopId,
							xtype : 'selectfield',
							name : 'person.shopId',
							displayField : 'text',
							valueField : 'value',
							hiddenName : 'person.shopId',
							store : Ext.create('StoreSelect', {
								url : __ctxPath
										+ "/system/getShopList1Organization.do?isMobile=1",
								value : data.shopId
							}),
							label : '分公司'
						}, {
							xtype : 'textareafield',
							value : data.postaddress,
							labelWidth : '22%',
							name : 'person.postaddress',
							label : '通讯地址'
						}, {
							xtype : 'button',
							name : 'submit',
							text : '编辑提交',
							cls : 'submit-button',
							scope : this,
							handler : this.formSubmit
						}/*
							 * , { layout: { type: 'hbox', align: 'middle' },
							 * defaults:{ height: '8px' }, items:[ { xtype:
							 * 'label', html:'&nbsp;&nbsp;&nbsp;性别' }, { xtype:
							 * 'radiofield', name: 'person.sex',
							 * labelWidth:'70%', width: '60%', label:
							 * '性别:&nbsp;&nbsp;&nbsp;&nbsp; 男', value: '0',
							 * checked: true }, { xtype: 'radiofield', name:
							 * 'person.sex', labelWidth:'60%', width: '40%',
							 * label: '女', value: '1' } ] }, { layout: { type:
							 * 'hbox', align: 'middle' }, defaults:{ height:
							 * '8px', width: '50%' }, items:[ { xtype: 'label',
							 * html:'&nbsp;&nbsp;&nbsp;婚姻状况' }, { xtype:
							 * 'radiofield', name: 'person.marry',
							 * labelWidth:'70%', width: '60%', label: '婚姻状况:已婚',
							 * value: '0', checked: true }, { xtype:
							 * 'radiofield', name: 'person.marry',
							 * labelWidth:'60%', width: '40%', label: '未婚',
							 * value: '1' } ] }
							 */
				]
		        }
		        
	        ]
    	});

    	this.callParent([config]);
    },
    formSubmit:function(){
    	
    	var name=this.getCmpByName("person.name").getValue(); 
		  if(Ext.isEmpty(name)){
		    Ext.Msg.alert('',"<font color='#12b7f5'>名字不能为空</font>");
			return;
		  }
    	 var telphone=this.getCmpByName("person.cellphone").getValue(); 
    	  if(Ext.isEmpty(telphone)){
			 Ext.Msg.alert('', "<font color='#12b7f5'>手机号不能为空</font>");
			  return;
			}
		 if(!isMobile(telphone)){
		   Ext.Msg.alert('', "<font color='#12b7f5'>手机号格式不正确</font>");
		   return;
		 }
		  var cardNumber=this.getCmpByName("person.cardnumber").getValue(); 
    	if(validateIdCard(cardNumber)==1){
			Ext.Msg.alert('', "<font color='#12b7f5'>证件号码不正确,请仔细核对</font>");
			return;
		}else if(validateIdCard(cardNumber)==2){
			Ext.Msg.alert('', "<font color='#12b7f5'>证件号码地区不正确,请仔细核对</font>");
			return;
			
		}else if(validateIdCard(cardNumber)==3){
			Ext.Msg.alert('', "<font color='#12b7f5'>证件号码生日日期不正确,请仔细核对</font>");
			return;
		}
		
		 var sex=this.getCmpByName("person.sex").getValue(); 
		  if(Ext.isEmpty(sex)){
		    Ext.Msg.alert('',"<font color='#12b7f5'>性别不能为空</font>");
			return;
		  }
		 
		  var marry=this.getCmpByName("person.marry").getValue(); 
		   if(Ext.isEmpty(marry)){
		    Ext.Msg.alert('',"<font color='#12b7f5'>婚姻状况不能为空</font>");
			return;
		  }
		 
		 var shopId=this.getCmpByName("person.shopId").getValue(); 
        if(Ext.isEmpty(shopId)){
		    Ext.Msg.alert('',"<font color='#12b7f5'>分公司不能为空</font>");
			return;
		  }
		 
       	this.submit({
			    url: __ctxPath+'/creditFlow/customer/person/updateInfoPerson.do',
		        params: {
		        },
		        method: 'POST',
		        success: function(form,action,response) {
		        	var obj = Ext.util.JSON.decode(response);
		        	if(obj.success==true){
		        		if(obj.exsit==false){
		        		 Ext.Msg.alert('','身份证号码已经存在');
		        			
		        		}else{
		        			  Ext.Msg.alert('','编辑成功');
		        			  mobileNavi.pop(3);
		        		}
		        	}else{
		        		  Ext.Msg.alert('','添加失败');
		        		
		        	}
		        },
		        failure: function(form,action,response){
					var obj = Ext.util.JSON.decode(response);
					Ext.Msg.alert('', obj.msg);
		        }
			});
    }

});
