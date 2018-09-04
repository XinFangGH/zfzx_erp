
//new ExtUD.Ext.PerCreditLoanProjectInfoPanel
Ext.define('htmobile.creditFlow.public.CreditLoanProjectInfoPanel', {
    extend: 'Ext.form.Panel',
    name: 'CreditLoanProjectInfoPanel',
    constructor: function (config) {
    	config = config || {};
    	Ext.apply(this,config);
    	Ext.apply(config,{
    		title:'项目基本信息',
		    fullscreen: true,
		    scrollable:{
		    	direction: 'vertical'
		    },
		    items : [{
				xtype : 'fieldset',
				defaults : {
					xtype : 'textfield',
					labelAlign : "left",
					readOnly : this.readOnly
				},
				items : [{
							xtype : "hiddenfield",
							name : 'projectId',
							value : this.data.projectId
						}, {
							value : this.data.departId,
							xtype : 'selectfield',
							readOnly : true,
							name : 'enterprise.shopId',
							displayField : 'text',
							valueField : 'value',
							hiddenName : 'enterprise.shopId',
							store : Ext.create('StoreSelect', {
								url : __ctxPath
										+ "/system/getShopList1Organization.do?isMobile=1"
							}),
							label : '分公司'
						}, {
							label : "项目名称",
							name : "slSmallloanProject.projectName",
							readOnly : true,
							value : this.data.projectName
						}, {
							label : "项目编号",
							name : "slSmallloanProject.projectNumber",
							readOnly : true,
							value : this.data.projectNumber
						}, {
							xtype : 'selectfield',
							label : "产品类型",
							hiddenName : 'slSmallloanProject.productTypeId',
							readOnly : true,
							store : Ext.create('StoreSelect', {
								url : __ctxPath
										+ "/htmobile/bpProductParameterlistVmInfo.do"
							}),
							value : this.data.productId
						}, {
							xtype : 'textfield',
							label : "项目经理",
							name : 'slSmallloanProject.appUserName',
							id : "slSmallloanProjectappUserName",
							value : this.data.appUserName,
							readOnly : this.readOnly,
							listeners : {
								scope : this,
								'focus' : function(f) {
									if (!this.readOnly) {
										mobileNavi.push(Ext.create(
												'htmobile.public.AppuserList',
												{
													callback : function(data) {
														var appUserName = Ext
																.getCmp("slSmallloanProjectappUserName");
														var appUserId = Ext
																.getCmp("slSmallloanProjectappUserId");
														appUserName
																.setValue(data.fullname);
														appUserId
																.setValue(data.userId);
														Ext.Ajax.request({
															url : __ctxPath
																	+ '/system/upUserIdAppUser.do',
															params : {
																userId : appUserId
															},
															success : function(
																	response) {
																var result = Ext.util.JSON
																		.decode(response.responseText);
																if (!Ext
																		.isEmpty(result.userId)) {
																	var teamManagerName = Ext
																			.getCmp("slSmallloanProjectteamManagerName");
																	var teamManagerId = Ext
																			.getCmp("slSmallloanProjectteamManagerId");
																	teamManagerName
																			.setValue(result.fullName);
																	teamManagerId
																			.setValue(result.userId);

																}

															}
														});

													}
												}));
									}

								}
							}

						}, {
							id : 'slSmallloanProjectappUserId',
							name : 'slSmallloanProject.appUserId',
							xtype : 'hiddenfield',
							value : this.data.appUserId
						}, {
							xtype : 'textfield',
							label : "项目主管",
							name : 'slSmallloanProject.teamManagerName',
							id : "slSmallloanProjectteamManagerName",
							value : this.data.teamManagerName,
							readOnly : this.readOnly,
							listeners : {
								scope : this,
								'focus' : function(f) {
									if (!this.readOnly) {
										mobileNavi.push(Ext.create(
												'htmobile.public.AppuserList',
												{
													callback : function(data) {
														var teamManagerName = Ext
																.getCmp("slSmallloanProjectteamManagerName");
														var teamManagerId = Ext
																.getCmp("slSmallloanProjectteamManagerId");
														teamManagerName
																.setValue(data.fullname);
														teamManagerId
																.setValue(data.userId);
													}
												}));
									}
								}

							}

						}, {
							id : 'slSmallloanProjectteamManagerId',
							name : 'slSmallloanProject.teamManagerId',
							xtype : 'hiddenfield',
							value : this.data.teamManagerId
						}

				]
			}, this.readOnly ? {} : {
				xtype : 'button',
				name : 'submit',
				text : '保存',
				cls : 'submit-button',
				handler : this.formSubmit
			}]
    	});

  

    	this.callParent([config]);
    	
    },
    detail:function(){
       mobileNavi.push(
		Ext.create('htmobile.customer.person.PersonMenu',{
			        data:this.data
		        	})
		    	);
    },
    formSubmit:function(){
		 var loginForm = this.up('formpanel');
// var
// teamManagerName=loginForm.getCmpByName("slSmallloanProject.teamManagerName").getValue();
// if(Ext.isEmpty(teamManagerName)){
// Ext.Msg.alert('','业务主管不能为空');
// return;
// }
		 var appUserName=loginForm.getCmpByName("slSmallloanProject.appUserName").getValue(); 
		  if(Ext.isEmpty(appUserName)){
		    Ext.Msg.alert('','客户经理不能为空');
			return;
		  }
       	loginForm.submit({
            url: __ctxPath+'/htmobile/creditLoanProjectVmInfo.do',
        	method : 'POST',
        	success : function(form,action,response) {
		        	var obj = Ext.util.JSON.decode(response);
		        	if(obj.success==true){
		        	
		        		  Ext.Msg.alert('','提交成功');
		        		  mobileNavi.pop(1);
		        	}else{
		        		  Ext.Msg.alert('','提交失败');
		        		
		        	}
        	}
		});}

});
