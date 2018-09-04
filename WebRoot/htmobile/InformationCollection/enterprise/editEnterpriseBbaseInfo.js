

Ext.define('htmobile.InformationCollection.enterprise.editEnterpriseBbaseInfo', {
    extend: 'Ext.form.Panel',
    name: 'editEnterpriseBbaseInfo',
    constructor: function (config) {
    	Ext.apply(this,config);
    	var data=this.data;
    	Ext.apply(config,{
		    fullscreen: true,
		    title:"企业基本信息",
		    scrollable:{
		    	direction: 'vertical'
		    },
		    items: [{
	    			xtype: 'fieldset',
	    			defaults:{
	    				 labelWidth:document.body.clientWidth/3,
	    				 clearIcon : true
	    			},
	    			items:[
	    				{
						    xtype: 'hiddenfield',
						    labelWidth:'36%',
						    name: 'enterprise.id',
						    value: data.id,
						    label: '企业id'
						},
				        {
				            xtype: 'textfield',
				            labelWidth:'36%',
				            name: 'enterprise.enterprisename',
				            label: '企业名称',
				            value: data.enterprisename,
				            listeners : {
								scope:this,
								'change' : function(newValue, oldValue, eOpts ) {
				        			var enterName = newValue.getValue();
				        			if(enterName!=""){
										var msg = "企业添加重复";
										Ext.Ajax.request({
						                   url:  __ctxPath + '/creditFlow/customer/enterprise/validateEnterNameAjaxValidator.do',
						                   method : 'POST',
						                   params : {
													enterName:enterName,
													msg:msg
												},
						                  success : function(response,request) {
						                  		if(response.responseText.trim()!=""){
													var obj=Ext.util.JSON.decode(response.responseText);
				                            		if(obj.exsit=="true"){					                            			
				                            			Ext.Msg.alert('操作信息',"该企业已存在，请重新输入");
				                            			f.setValue("");
				                            		}					                  			
						                  		}
					                      }
			                             });				        				
				        			}
									
								}
				        	}
				        },{
				            xtype: 'textfield',
				            labelWidth:'36%',
				            name: 'enterprise.organizecode',
				            label: '组织机构代码',
				            value: data.organizecode,
				            listeners : {
								'change' : function(newValue, oldValue, eOpts ) {
				        			var organizecode = newValue.getValue();
				        			if(organizecode!=""){
				        				var msg = "机构代码添加重复";
										if(isorganizecode(organizecode)){
											return;
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
			                            			Ext.Msg.alert('操作信息',"该机构代码已存在，请重新输入");
			                            			f.setValue("");
			                            			// penal.setValue("");
			                            		}
					                      }
			                             });
				        			}
								}
				        	}
				        },
				        {
				            xtype: 'textfield',
				            labelWidth:'36%',
				            name: 'enterprise.cciaa',
				            label: '营业执照号码',
				            value: data.cciaa,
				            listeners : {
								'change' : function(newValue, oldValue, eOpts ) {
				        			var organizecode = newValue.getValue();
				        			if(organizecode!=""){
				        				var msg = "营业执照号码添加重复";
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
			                            			Ext.Msg.alert('操作信息',"营业执照号码已存在，请重新输入");
			                            			f.setValue("");
			                            		}
					                      }
			                             });
				        			}
								}
				        	}
				        },
				        {
				            xtype: 'textfield',
				            labelWidth:'36%',
				            name: 'enterprise.linkman',
				            value: data.linkman,
				            label: '企业联系人'
				        },
				        
				        {
				            xtype: 'textfield',
				            labelWidth:'36%',
				            name: 'enterprise.telephone',
				            value: data.telephone,
				            label: '联系电话'
				        },
				        {
				            xtype: 'textfield',
				            labelWidth:'36%',
				            name: 'enterprise.area',
				            value: data.area,
				            label: '通讯地址'
				        },
				        {
				          labelWidth:'36%',
				            value:data.shopId,
				            xtype: 'selectfield',
				            name:'enterprise.shopId',
				            displayField: 'text',
           	                valueField: 'value',
                            hiddenName:'enterprise.shopId',
				            store:Ext.create('StoreSelect', {
			          	         url : __ctxPath + "/system/getShopList1Organization.do?isMobile=1",
			          	           value: data.shopId
			                }),
				            label: '分公司'
				        }, {
							xtype : 'button',
							name : 'submit',
							text : '编辑',
							cls : 'submit-button',
							scope:this,
							handler : this.formSubmit
						}/*
									 * ,{ xtype:"hiddenfield",
									 * id:"newbaseenterpriseshopName",
									 * name:"enterprise.shopName"
									 * 
									 *  }, { xtype: 'textfield',
									 * labelWidth:'36%', name:
									 * 'person.legalperson', label: '法人' }
									 */
			        ]
		        }
	        ]
    	});

    	this.callParent([config]);
    },formSubmit:function(){
    	
    	var enterprisename=this.parent.getCmpByName("enterprise.enterprisename").getValue(); 
		  if(Ext.isEmpty(enterprisename)){
		    Ext.Msg.alert('',"<font color='red'>企业名字不能为空</font>");
			return;
		  }
		
		 var organizecode=this.parent.getCmpByName("enterprise.organizecode").getValue(); 
		  if(Ext.isEmpty(organizecode)){
		    Ext.Msg.alert('',"<font color='red'>组织机构代码不能为空</font>");
			return;
		  }
		  if(isorganizecode(organizecode)){
			    Ext.Msg.alert('',"<font color='red'>组织机构代码格式不正确</font>");
				return;
			  }
		 
		  var cciaa=this.parent.getCmpByName("enterprise.cciaa").getValue(); 
		   if(Ext.isEmpty(cciaa)){
		    Ext.Msg.alert('',"<font color='red'>营业执照号码不能为空</font>");
			return;
		  }
		 
		var linkman=this.parent.getCmpByName("enterprise.linkman").getValue(); 
        if(Ext.isEmpty(linkman)){
		    Ext.Msg.alert('',"<font color='red'>企业联系人不能为空</font>");
			return;
		  }
        
        var telphone=this.parent.getCmpByName("enterprise.telephone").getValue(); 
  	  	if(Ext.isEmpty(telphone)){
			 Ext.Msg.alert('', "<font color='red'>企业联系电话不能为空</font>");
			  return;
			}
		 if(!isMobile(telphone)){
		   Ext.Msg.alert('', "<font color='red'>企业联系电话格式不正确</font>");
		   return;
		 }
        
        var area=this.parent.getCmpByName("enterprise.area").getValue(); 
        if(Ext.isEmpty(area)){
		    Ext.Msg.alert('',"<font color='red'>通讯地址不能为空</font>");
			return;
		  }
        
      /*  var legalperson=this.parent.getCmpByName("person.legalperson").getValue(); 
        if(Ext.isEmpty(legalperson)){
		    Ext.Msg.alert('','法人不能为空');
			return;
		  }
		 */
       	this.submit({
			    url: __ctxPath+'/creditFlow/customer/enterprise/ajaxUpdateEnterprise.do',
		        method: 'POST',
		        success: function(form,action,response) {
		        	var obj = Ext.util.JSON.decode(response);
		        	if(obj.success==true){
		        		if(obj.exsit==false){
			        		 Ext.Msg.alert('',"<font color='red'>营业执照号码已存在</font>");
		        		}else{
		        		 Ext.Msg.alert('',"编辑成功");
		        		 mobileNavi.pop(2);
		        		}
		        	}else{
		        		  Ext.Msg.alert('',"<font color='red'>编辑失败</font>");
		        	}
		        }
			});
    }

});
