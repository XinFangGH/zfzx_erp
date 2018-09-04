

Ext.define('htmobile.approve.person.ApplyPersonloan', {
    extend: 'Ext.form.Panel',
    name: 'ApplyPersonloan',
    constructor: function (config) {
    	Ext.apply(this,config);
    	config = config || {};
    	Ext.apply(config,{
		    fullscreen: true,
		    title:this.title,
		    scrollable:{
		    	direction: 'vertical'
		    },
		    items: [{
	    			xtype: 'fieldset',
	    			defaults:{
	    				 labelWidth:'30%',
	    				 clearIcon : true
	    			},
	    			items : [
						/*
						 * { labelWidth:'36%', xtype: 'uxSelectfield',
						 * name:'person.shopId', displayField: 'text',
						 * valueField: 'value', value:"",
						 * hiddenName:'person.shopId',
						 * store:Ext.create('StoreSelect', { url : __ctxPath +
						 * '/creditFlow/customer/person/perQueryListPerson.do?isAll='+
						 * isGranted('_detail_sygrkh')+"&isMobile=1" }),
						 * callback:function(record){ var applypersoncardnumber=
						 * Ext.getCmp("applypersoncardnumber"); var
						 * applypersontelphone=
						 * Ext.getCmp("applypersontelphone"); Ext.Ajax.request({
						 * url : __ctxPath +
						 * '/creditFlow/customer/person/seeInfoPerson.do',
						 * params:{ id:record.data.value }, method : 'POST',
						 * success : function(response,request) { var obj =
						 * Ext.util.JSON.decode(response.responseText); var data =
						 * obj.data;
						 * applypersoncardnumber.setValue(data.cardnumber);
						 * applypersontelphone.setValue(data.telphone); } }); },
						 * label: '姓名' },
						 */
						{
					xtype : 'textfield',
					id : 'applypersonname',
					name : 'person.name',
					label : '<span class="posa_t">1</span><span>姓名</span>',
					placeHolder : '请输入姓名',
					readOnly : true,
					listeners : {
						scope : this,
						'focus' : function(f) {
							mobileNavi.push(Ext.create(
									'htmobile.public.SelectPersonlist', {
										callback : function(data) {
											var applypersonid = Ext
													.getCmp("applypersonid");
											var applypersoncardnumber = Ext
													.getCmp("applypersoncardnumber");
											var applypersontelphone = Ext
													.getCmp("applypersontelphone");
											var applypersonname = Ext
													.getCmp("applypersonname");
											applypersontelphone
													.setValue(data.cellphone);
											applypersoncardnumber
													.setValue(data.cardnumber);
											applypersonid.setValue(data.id);
											applypersonname.setValue(data.name);
										}
									}));
						}
					}
				}, {

					id : 'applypersonid',
					name : 'person.id',
					xtype : 'hiddenfield'

				}, {
					xtype : 'hiddenfield',
					name : 'history',
					value : this.history
				}, {
					xtype : 'textfield',
					id : "applypersoncardnumber",
					name : 'person.cardnumber',
					readOnly : true,
					label : '<span class="posa_t">2</span><span>身份证号码</span>',
					placeHolder : '请输入身份证号码'
				}, {
					xtype : 'textfield',
					readOnly : true,
					id : "applypersontelphone",
					name : 'person.cellphone',
					label : '<span class="posa_t">3</span><span>手机号码</span>',
					placeHolder : '请输入手机号码'
				}, {
					xtype : 'uxSelectfield',
					name : 'slSmallloanProject.productId',
					displayField : 'text',
					valueField : 'value',
					hiddenName : 'slSmallloanProject.productId',
					store : Ext.create('StoreSelect', {
						url : __ctxPath
								+ "/system/listBpProductParameter.do?isMobile=1&Q_operationType_S_EQ=SmallLoan_PersonalCreditLoanBusiness"
					}),
					label : '<span class="posa_t">4</span><span>业务类型</span>',
					placeHolder : '请选择业务类型'
				}, {
					xtype : 'textfield',
					id : 'slSmallloanProjectappUserName',
					value : curUserInfo.fullname,
					name : 'slSmallloanProject.appUserName',
					label : '<span class="posa_t">5</span><span>客户经理</span>',
					placeHolder:'请输入客户经理',
					readOnly : true,
					listeners : {
						scope : this,
						'focus' : function(f) {

							mobileNavi.push(Ext.create(
									'htmobile.public.AppuserList', {
										callback : function(data) {
											var appUserName = Ext
													.getCmp("slSmallloanProjectappUserName");
											var appUserId = Ext
													.getCmp("slSmallloanProjectappUserId");
											appUserName
													.setValue(data.fullname);
											appUserId
													.setValue(data.userId);
										}
									}));
						}
					}
				}, {
					id : 'slSmallloanProjectappUserId',
					name : 'slSmallloanProject.appUserId',
					xtype : 'hiddenfield',
					value : (this.appUserId == null || this.appUserId == "")
							? curUserInfo.userId
							: this.appUserId
				}, {
					xtype : 'selectfield',
					name : 'slSmallloanProject.departId',
					displayField : 'text',
					valueField : 'value',
					hiddenName : 'slSmallloanProject.departId',
					store : Ext.create('StoreSelect', {
						url : __ctxPath
								+ "/system/getShopList1Organization.do?isMobile=1"
					}),
					label : '<span class="posa_t">6</span><span>分公司</span>',
					placeHolder:'请输入分公司',
					value : curUserInfo.depId,
					callback : function(record) {
						Ext.Ajax.request({
							url : __ctxPath
									+ "/system/getOrganization.do",
							params : {
								orgId : record.data.value
							},
							method : 'POST',
							success : function(response, request) {
								var detail = Ext.util.JSON
										.decode(response.responseText);
								var newbasepersonshopName = Ext
										.getCmp("slSmallloanProjectdepartMentName");
								newbasepersonshopName
										.setValue(detail.data.orgName);
							}
						});
					}
				}, {
					xtype : "hiddenfield",
					id : "slSmallloanProjectdepartMentName",
					name : "slSmallloanProject.departMentName",
					value : this.newbasepersonshopName

				}, {
					xtype : 'hiddenfield',
					name : 'preHandler',
					value : 'creditProjectService.startCreditP2PProjectFlow'
				}, {
					xtype : 'hiddenfield',
					name : "operationType",
					value : "SmallLoan_PersonalCreditLoanBusiness"
				}/*
					 * ,{ labelWidth:'22%', xtype : "textfield", label :
					 * "贷款金额", name : "projectMoney", listeners : {
					 * scope : this, blur : function(obj, e, eOpts) {
					 * 
					 * var value = obj.getValue(); var
					 * projectMoney=this.getCmpByName("slSmallloanProject.projectMoney");
					 * projectMoney.setValue(parseFloat(value)); } } }
					 */, {
					xtype : "hiddenfield",
					name : "slSmallloanProject.projectMoney"
				}/*
							 * ,{ xtype : 'hiddenfield', name : 'history', value :
							 * "personSmall" }
							 */
				]
		        },
		        {
		            xtype: 'button',
		            name: 'submit',
		            text:'提交',
		            cls : 'submit-button',
		            handler:this.formSubmit
		        }
	        ]
    	});

    	this.callParent([config]);
    },
    formSubmit:function(){
    	var history = this.history;
    	var name=this.parent.getCmpByName("person.name").getValue(); 
		  if(Ext.isEmpty(name)){
		    Ext.Msg.alert('','名字不能为空');
			return;
		  }
		  
		  var productId=this.parent.getCmpByName("slSmallloanProject.productId").getValue(); 
		  if(Ext.isEmpty(productId)){
		    Ext.Msg.alert('','业务类型不能为空');
			return;
		  }
		  
		  var appUserName=this.parent.getCmpByName("slSmallloanProject.appUserName").getValue(); 
		  if(Ext.isEmpty(appUserName)){
		    Ext.Msg.alert('','客户经理不能为空');
			return;
		  }
		  var departId=this.parent.getCmpByName("slSmallloanProject.departId").getValue(); 
		  if(Ext.isEmpty(departId)){
			    Ext.Msg.alert('','分公司不能为空');
				return;
		  }
		  
		/*var projectMoney=this.parent.getCmpByName("projectMoney").getValue();
    	if(Ext.isEmpty(projectMoney)){
    		 Ext.Msg.alert('','金额不能为空');
    	     return ;
    	}
    	if(parseFloat(projectMoney)==0){
    		 Ext.Msg.alert('','金额要大于0');
    	     return ;
    	}*/
    	
    	
		  
    	/*var flag=false;
    	var remainder=0;
    	
		Ext.Ajax.request({
            url :  __ctxPath + '/creditFlow/generalCredit/project/getRemainderPlCreditOrganization.do',
            params : {
				departId:departId
			},
        	method : 'POST',
        	async: false,
        	success : function(response, request) {
        	obj = Ext.util.JSON.decode(response.responseText);
        	 remainder = obj.remainder;
        	if(remainder-projectMoney<=0){
        			flag=true
        		}
        
			}

		});
		if(flag){
	        Ext.Msg.alert('操作信息','该分子公司的剩余授信额度为'+remainder+'元，小于'+projectMoney+'元,请重新填写项目金额');
	        return;
		}
		
		var cflag=false;*/
		
	/*	var personId =this.parent.getCmpByName('person.id').getValue();
		Ext.Ajax.request({
            url :  __ctxPath + '/creditFlow/customer/person/getRemainderPerson.do',
            params : {
				personId:personId
			},
			async: false,
        	method : 'POST',
        	success : function(response, request) {
				var obj = Ext.util.JSON.decode(response.responseText);
        	 	cremainder = obj.remainder;
	        	if(cremainder-projectMoney<0){
	    			cflag=true;
	        	}
        
			}
			
		});
		if(cflag){
	        Ext.Msg.alert('操作信息','该客户的剩余授信额度为'+cremainder+'元，小于'+projectMoney+'元,请重新填写项目金额');
	        return;
		}*/
  	 /*var telphone=this.parent.getCmpByName("person.telphone").getValue(); 
  	  if(Ext.isEmpty(telphone)){
			 Ext.Msg.alert('', "<font color='#fff'>手机号不能为空</font>");
			  return;
			}
		 if(!isMobile(telphone)){
		   Ext.Msg.alert('', "<font color='#fff'>手机号格式不正确</font>");
		   return;
		 }
		 
		 var cardnumber=this.parent.getCmpByName("person.cardnumber").getValue(); 
		  if(Ext.isEmpty(cardnumber)){
		    Ext.Msg.alert('','身份证号码不能为空');
			return;
		  }*/
		 
    	
    	 var loginForm = this.up('formpanel');
       	loginForm.submit({
			   url : __ctxPath + "/flow/saveProcessActivity.do",
		       /* params: {
		        	history:history
		        },*/
		        method: 'POST',
		        success: function(form,action,response) {
		          var obj = Ext.util.JSON.decode(response);
		        	if(obj.success==true){
						Ext.Msg.alert('状态', '项目：'+obj.data.projectNumber+'启动成功!');
						mobileNavi.pop(2);
		        	}else{
		        	   Ext.Msg.alert('状态', '项目：启动失败!');
		        	   mobileNavi.pop();
		        	}
		        },
		        failure: function(form,action,response){
					alert('启动项目失败,请联系管理员!');
		        }
			});
    }

});
