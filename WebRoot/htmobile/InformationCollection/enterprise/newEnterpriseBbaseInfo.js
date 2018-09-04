Ext.define('htmobile.InformationCollection.enterprise.newEnterpriseBbaseInfo', {
    extend: 'Ext.form.Panel',
    name: 'newEnterpriseBbaseInfo',
    constructor: function (config) {
    	var bottomBar= Ext.create('htmobile.public.bottomBarIndex',{
        });
    	Ext.apply(this,config);
    	config = config || {};
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
				            xtype: 'textfield',
				            labelWidth:'36%',
				            name: 'enterprise.enterprisename',
				            label: '<span class="posa_t">1</span><span>企业名称</span>',
				            placeHolder:'请输入企业名称',
				            listeners : {
								scope:this,
								'blur' : function(f) {
				        			
				        			var enterName = f.getValue();
									var msg = "企业添加重复";
									
									Ext.Ajax.request({
					                   url:  __ctxPath + '/creditFlow/customer/enterprise/validateEnterNameAjaxValidator.do',
					                   method : 'POST',
					                   params : {
												enterName:enterName,
												msg:msg
											},
					                  success : function(response,request) {
											var obj=Ext.util.JSON.decode(response.responseText);
		                            		if(obj.exsit=="true"){					                            			
		                            			Ext.ux.Toast.msg('操作信息',"该企业已存在，请重新输入");
		                            			f.setValue("");
		                            			// penal.setValue("");
		                            		}
				                      }
		                             });
								}
				        	}
				        },{
				            xtype: 'textfield',
				            labelWidth:'36%',
				            name: 'enterprise.cciaa',
				            label: '<span class="posa_t">2</span><span>营业执照号码</span>',
				            placeHolder:'请输入企业营业执照号码',
				            listeners : {
								'blur' : function(f) {
				        			var organizecode = f.getValue();
									var msg = "营业执照号码添加重复";
									/*if(Ext.isEmpty(organizecode)){
										alert("营业执照号码不能为空");
										return;
									}
									*/
									Ext.Ajax.request({
					                   url:  __ctxPath + '/creditFlow/customer/enterprise/validatorCciaaAjaxValidator.do',
					                   method : 'POST',
					                   params : {
												organizecode:organizecode,
												msg:msg
											},
					                  success : function(response,request) {
											var obj=Ext.util.JSON.decode(response.responseText);
		                            		if(obj.exsit=="true"){					                            			
		                            			Ext.ux.Toast.msg('操作信息',"营业执照号码已存在，请重新输入");
		                            			f.setValue("");
		                            			// penal.setValue("");
		                            		}
				                      }
		                             });
								}
				        	}
				        },
				        {
				            xtype: 'textfield',
				            labelWidth:'36%',
				            name: 'enterprise.organizecode',
				            label: '<span class="posa_t">3</span><span>组织机构代码</span>',
				            placeHolder:'请输入企业组织机构代码',
				            listeners : {
								'blur' : function(f) {
				        			var organizecode = f.getValue();
									var msg = "机构代码添加重复";
									/*if(Ext.isEmpty(organizecode)){
										alert("该机构代码不能为空");
										return;
									}*/
									if(isorganizecode(organizecode)){
										alert("该机构代码格式不正确");
										return;
										//Ext.ux.Toast.msg('操作信息',"该机构代码格式不正确");
									}
									
									Ext.Ajax.request({
					                   url:  __ctxPath + '/creditFlow/customer/enterprise/validatorEnterOrganizecodeAjaxValidator.do',
					                   method : 'POST',
					                   params : {
												organizecode:organizecode,
												msg:msg
											},
					                  success : function(response,request) {
											var obj=Ext.util.JSON.decode(response.responseText);
		                            		if(obj.exsit=="true"){					                            			
		                            			Ext.ux.Toast.msg('操作信息',"该机构代码已存在，请重新输入");
		                            			f.setValue("");
		                            			// penal.setValue("");
		                            		}
				                      }
		                             });
								}
				        	}
				        },
				        
				        {
				            xtype: 'textfield',
				            labelWidth:'36%',
				            name: 'enterprise.linkman',
				            label: '<span class="posa_t">4</span><span>企业联系人</span>',
				            placeHolder:'请输入企业企业联系人'
				        },
				        {
				            xtype: 'textfield',
				            labelWidth:'36%',
				            name: 'enterprise.telephone',
				            label: '<span class="posa_t">5</span><span>联系电话</span>',
				            placeHolder:'请输入企业联系电话'
				        },
				        
				        {
				            xtype: 'textfield',
				            labelWidth:'36%',
				            name: 'enterprise.area',
				            label: '<span class="posa_t">6</span><span>通讯地址</span>',
				            placeHolder:'请输入企业通讯地址'
				        },
				        {
				            labelWidth:'36%',
				            xtype: 'uxSelectfield',
				            name:'enterprise.shopId',
				            displayField: 'text',
           	                valueField: 'value',
				            store:Ext.create('StoreSelect', {
			          	         url : __ctxPath + "/system/getShopList1Organization.do?isMobile=1"
			                }),
				            label: '<span class="posa_t">7</span><span>分公司</span>',
				            placeHolder:'请输入企业分公司',
				            callback:function(record){
			                   var newbasepersonshopName= Ext.getCmp("newbaseenterpriseshopName");
			                    	Ext.Ajax.request({
								                   url : __ctxPath + "/system/getOrganization.do",
												  params:{
												    orgId:record.data.value
												    },
								                   method : 'POST',
								                  success : function(response,request) {
														 var obj = Ext.util.JSON.decode(response.responseText);
						                                 var data = obj.data;
						                                 newbasepersonshopName.setValue(data.orgName);
							                      }
					                             });
			                }
				        },{
				            xtype:"hiddenfield",
				            id:"newbaseenterpriseshopName",
				            name:"enterprise.shopName"
				        }, {
				            labelWidth:'36%',
				            xtype: 'textfield',
				            id:'applypersonname',
				            name:'person.name',
				            label: '<span class="posa_t">8</span><span>法人</span>',
				            placeHolder:'请输入企业法人',
				            readOnly:true,
				             listeners : {
								scope:this,
								'focus' : function(f) {
									
									   mobileNavi.push(Ext.create('htmobile.public.SelectPersonlist',{callback:function(data){
									   	   var applypersonid= Ext.getCmp("applypersonid");
									   	   var applypersonname= Ext.getCmp("applypersonname");
						                    applypersonid.setValue(data.id);
						                     applypersonname.setValue(data.name);
									   }}));
								}
								}
				        },{
				           id:'applypersonid',
				           name:'person.id',
				           xtype:'hiddenfield'
				        },{
				            xtype: 'textareafield',
				            labelWidth:'36%',
				            name: 'enterprise.managescope',
				            label: '<span class="posa_t">9</span><span>企业资格证照</span>',
				            placeHolder:'请输入企业资格证照'
				        }
			        ]
		        },
		        {
		            xtype: 'button',
		            style:'border-radius: 0.2em;color:#ffffff;font-family: 微软雅黑;',
		            name: 'submit',
		           	text:'提交保存',
		            cls : 'submit-button',
		            handler:this.formSubmit
		        }
	        ]
    	});

    	this.callParent([config]);
    },
    formSubmit:function(){
    	
    	var enterprisename=this.parent.getCmpByName("enterprise.enterprisename").getValue(); 
		  if(Ext.isEmpty(enterprisename)){
		    Ext.Msg.alert('','企业名字不能为空');
			return;
		  }
		
		 var organizecode=this.parent.getCmpByName("enterprise.organizecode").getValue(); 
		  if(Ext.isEmpty(organizecode)){
		    Ext.Msg.alert('','组织机构代码不能为空');
			return;
		  }
		  if(isorganizecode(organizecode)){
			    Ext.Msg.alert('','组织机构代码格式不正确');
				return;
			  }
		 
		  var cciaa=this.parent.getCmpByName("enterprise.cciaa").getValue(); 
		   if(Ext.isEmpty(cciaa)){
		    Ext.Msg.alert('','营业执照号码不能为空');
			return;
		  }
		 
		var linkman=this.parent.getCmpByName("enterprise.linkman").getValue(); 
        if(Ext.isEmpty(linkman)){
		    Ext.Msg.alert('','企业联系人不能为空');
			return;
		  }
        
        var telphone=this.parent.getCmpByName("enterprise.telephone").getValue(); 
  	  	if(Ext.isEmpty(telphone)){
			 Ext.Msg.alert('', "<font color='#fff'>企业联系电话不能为空</font>");
			  return;
			}
		 if(!isMobile(telphone)){
		   Ext.Msg.alert('', "<font color='#fff'>企业联系电话格式不正确</font>");
		   return;
		 }
        
        var area=this.parent.getCmpByName("enterprise.area").getValue(); 
        if(Ext.isEmpty(area)){
		    Ext.Msg.alert('','通讯地址不能为空');
			return;
		  }
        
      /*  var legalperson=this.parent.getCmpByName("person.legalperson").getValue(); 
        if(Ext.isEmpty(legalperson)){
		    Ext.Msg.alert('','法人不能为空');
			return;
		  }
		 */
		 var loginForm = this.up('formpanel');
       	loginForm.submit({
			    url: __ctxPath+'/creditFlow/customer/enterprise/ajaxAddEnterprise.do',
		        params: {
       				isMobile:1
		        },
		        method: 'POST',
		        success: function(form,action,response) {
		        	var obj = Ext.util.JSON.decode(response);
		        	if(obj.success==true){
		        		if(obj.exsit==false){
			        		 Ext.Msg.alert('',obj.msg);
			        			
		        		}else{
		        		 Ext.Msg.alert('',obj.msg);
		        		 mobileNavi.pop();
		             //    mobileNavi.push(Ext.create('htmobile.InformationCollection.person.uploadMaterial',{id:obj.id,mark:'cs_enterprise.',resouce:'newperson'}));
		        		}
		        	}else{
		        		  Ext.Msg.alert('',obj.msg);
		        		
		        	}
		        },
		        failure: function(form,action,response){ 
					var obj = Ext.util.JSON.decode(response);
					Ext.Msg.alert('', "企业添加失败");
		        }
			});
    }

});
