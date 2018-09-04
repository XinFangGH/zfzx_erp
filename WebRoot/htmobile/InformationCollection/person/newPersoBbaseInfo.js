Ext.define('htmobile.InformationCollection.person.newPersoBbaseInfo', {
    extend: 'Ext.form.Panel',
    name: 'newPersoBbaseInfo',
    constructor: function (config) {
    	var bottomBar= Ext.create('htmobile.public.bottomBarIndex',{
        });
        config = config || {};
    	Ext.apply(this,config);
    	var personData={};
    	Ext.apply(config,{
		    fullscreen: true,
		    title:this.title,
		    scrollable:{
		    	direction: 'vertical'
		    },
		    items: [bottomBar,{
	    			xtype: 'fieldset',
	    			defaults:{
	    				 labelWidth:document.body.clientWidth/3,
	    				 clearIcon : true
	    			},
	    			items:[
	    					{
		    			       xtype:'hiddenfield',
		    			       name: 'person.cardtype'
		    			    },
					        {
					            xtype: 'textfield',
					            labelWidth:'27%',
					            name: 'person.name',
					            label: '<span class="posa_t">1</span><span>姓名</span>',
					            placeHolder:'请输入客户姓名'
					        },
					        {
					            xtype: 'textfield',
					            labelWidth:'27%',
					            name: 'person.cardnumber',
					            label: '<span class="posa_t">2</span><span>身份证号码</span>',
					            placeHolder:'请输入客户身份证号码',
					            scope:this,
					            listeners : {
								'blur' : function(f) {
									var cardNumber = f.getValue();
									if (validateIdCard(cardNumber) == 1) {
										Ext.Msg.alert('身份证号码验证',
												'证件号码不正确,请仔细核对');
										return;
									} else if (validateIdCard(cardNumber) == 2) {
										Ext.Msg.alert('身份证号码验证',
												'证件号码地区不正确,请仔细核对');
										return;

									} else if (validateIdCard(cardNumber) == 3) {
										Ext.Msg.alert('身份证号码验证',
												'证件号码生日日期不正确,请仔细核对');
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
												Ext.ux.Toast.msg('操作信息',
														"该证件号码已存在，请重新输入");
												f.setValue("");
												// penal.setValue("");
											}
										}
									});
								}
					            } },
					        {
					            xtype: 'hiddenfield',
					            name: 'person.cardtype',
					            value:'309'
					        },{
					            xtype: 'textfield',
					            labelWidth:'27%',
					            name: 'person.cellphone',
					            label: '<span class="posa_t">3</span><span>手机号码</span>',
					            placeHolder:'请输入手机号码'
					        },
					        {
					        	name:'person.sex',
					            labelWidth:'27%',
					            xtype : "dickeycombo",
								nodeKey : 'sex_key',
					        	hiddenName : 'person.sex',
					        	label: '<span class="posa_t">4</span><span>性别</span>',
					        	placeHolder:'请选择客户性别'
		            		},
					        /*{
								xtype : 'uxSelectfield',
								labelWidth : '27%',
								label : '业务类型',
								hiddenName : 'slSmallloanProject.productTypeId',
								store : Ext.create('StoreSelect', {
									url : __ctxPath
											+ "/htmobile/bpProductParameterlistVmInfo.do"
								})
							},*/ {
								labelWidth : '27%',
								xtype : 'uxSelectfield',
								name : 'person.shopId',
								value : curUserInfo.depId,
								hiddenName : 'person.shopId',
								store : Ext.create('StoreSelect', {
									url : __ctxPath+ "/system/getShopList1Organization.do?isMobile=1"
								}),
								label : '<span class="posa_t">5</span><span>分公司</span>',
								placeHolder:'请选择客户分公司',
								callback : function(record) {
									var newbasepersonshopName = Ext
											.getCmp("newbasepersonshopName");
									Ext.Ajax.request({
												url : __ctxPath
														+ "/system/getOrganization.do",
												params : {
													orgId : record.data.value
												},
												method : 'POST',
												success : function(response,
														request) {
													var obj = Ext.util.JSON
															.decode(response.responseText);
													var data = obj.data;
													newbasepersonshopName
															.setValue(data.orgName);
												}
											});
								}
							},{
					            xtype:"hiddenfield",
					            id:"newbasepersonshopName",
					            name:"person.shopName"
					        },{
					            xtype: 'textareafield',
					            labelWidth:'27%',
					            name: 'person.postaddress',
					            label: '<span class="posa_t">6</span><span>通讯地址</span>',
					            placeHolder:'请输入客户通讯地址'
					        },{
					            xtype: 'button',
					            name: 'submit',
					            text:'提交保存',
					            cls : 'submit-button',
					            handler:this.formSubmit
					        }
	        			]
	        }]
    });

    	this.callParent([config]);
    },
    formSubmit:function(){
    	
    		/*var  telphone=this.parent.getCmpByName("person.telphone").getValue(); 
    	  if(Ext.isEmpty(telphone)){
			 Ext.Msg.alert('', "<font color='#fff'>手机号不能为空</font>");
			  return;
			}
		 if(!isMobile(telphone)){
		   Ext.Msg.alert('', "<font color='#fff'>手机号格式不正确</font>");
		   return;
		 }
    	var loginForm = this.up('formpanel');
    	var boj=loginForm.getCmpByName("person.shopId1");
       	loginForm.submit({
			    url: __ctxPath+'/creditFlow/customer/person/addInfoPerson.do',
		        params: {
		        },
		        method: 'POST',
		        success: function(form,action,response) {
		        	var obj = Ext.util.JSON.decode(response);
		        	if(obj.success==true){
                         mobileNavi.push(Ext.create('htmobile.InformationCollection.person.selectUploadMaterial',{}));
		        	}else{
		        		
		        	}
		        },
		        failure: function(form,action,response){
					var obj = Ext.util.JSON.decode(response);
					Ext.Msg.alert('', obj.msg);
		        }
			});*/
    	var name=this.parent.getCmpByName("person.name").getValue(); 
		  if(Ext.isEmpty(name)){
		    Ext.Msg.alert('','名字不能为空');
			return;
		  }
    	 var cellphone=this.parent.getCmpByName("person.cellphone").getValue(); 
    	  if(Ext.isEmpty(cellphone)){
			 Ext.Msg.alert('', "<font color='#ccc'>手机号不能为空</font>");
			  return;
			}
		 if(isMobile(cellphone)==0){
		   Ext.Msg.alert('', "<font color='#ccc'>手机号格式不正确</font>");
		   return;
		 }
		  var cardNumber=this.parent.getCmpByName("person.cardnumber").getValue(); 
    	if(validateIdCard(cardNumber)==1){
			Ext.Msg.alert('身份证号码验证','证件号码不正确,请仔细核对');
			return;
		}else if(validateIdCard(cardNumber)==2){
			Ext.Msg.alert('身份证号码验证','证件号码地区不正确,请仔细核对');
			return;
			
		}else if(validateIdCard(cardNumber)==3){
			Ext.Msg.alert('身份证号码验证','证件号码生日日期不正确,请仔细核对');
			return;
		}
		
		 var sex=this.parent.getCmpByName("person.sex").getValue(); 
		  if(Ext.isEmpty(sex)){
		    Ext.Msg.alert('','性别不能为空');
			return;
		  }
		 
//		  var marry=this.parent.getCmpByName("person.marry").getValue(); 
//		   if(Ext.isEmpty(marry)){
//		    Ext.Msg.alert('','婚姻状况不能为空');
//			return;
//		  }
		 
//		 var shopId=this.parent.getCmpByName("person.shopId").getValue(); 
//        if(Ext.isEmpty(shopId)){
//		    Ext.Msg.alert('','分公司不能为空');
//			return;
//		  }
		 
		 var loginForm = this.up('formpanel');
    	var boj=loginForm.getCmpByName("person.shopId1");
       	loginForm.submit({
			    url: __ctxPath+'/creditFlow/customer/person/addInfoPerson.do',
		        params: {
		        },
		        method: 'POST',
		        success: function(form,action,response) {
		        	var obj = Ext.util.JSON.decode(response);
		        	if(obj.success==true){
		        		if(obj.exsit==false){
		        		 Ext.Msg.alert('','身份证号码已经存在');
		        			
		        		}else{
		        			mobileNavi.pop();
		        			Ext.Msg.alert('','添加成功');
			              //   mobileNavi.push(Ext.create('htmobile.InformationCollection.person.uploadMaterial',{id:obj.id,mark:'cs_person.',resouce:'newperson'}));
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
