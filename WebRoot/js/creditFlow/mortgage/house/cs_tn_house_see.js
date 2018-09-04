var see_House = function(mortgageid,refreshMortgageGridStore,businessType){
	
	var anchor = '100%';
	var showHouseInformation = function(MortgageUpdateData){
		var panel_updateHouse = new Ext.form.FormPanel({
			id :'see_House',
			url : __ctxPath +'/credit/mortgage/updateHouse.do',
			monitorValid : true,
			bodyStyle:'padding:10px',
			autoScroll : true ,
			labelAlign : 'right',
			buttonAlign : 'center',
			frame : true ,
			border : false,
			items : [{
				layout : 'column',
				xtype:'fieldset',
	            title: '查看<抵质押物>基础信息',
	            collapsible: true,
	            autoHeight:true,
	            anchor : '95%',
				items : [{
	        		xtype : 'hidden',
	        		name : 'mortgageid',
	        		value : mortgageid
	        	},{
					columnWidth : .5,
					layout : 'form',
					labelWidth : 105,
					defaults : {anchor : '100%'},
					items : [{
						xtype : 'textfield',
						fieldLabel : '抵质押物类型',
						value : MortgageUpdateData.vProcreditDictionary.mortgagepersontypeforvalue,
						blankText : '为必填内容',
						readOnly : true,
			            cls : 'readOnlyClass'
					},{
						id : 'personType_select',
						xtype : "dickeycombo",
						nodeKey :'syrlx',
						hiddenName : "procreditMortgage.personType",
						value : MortgageUpdateData.vProcreditDictionary.personTypeId,
						fieldLabel : "所有人类型",
						readOnly : true,
						itemName : '所有人类型', // xx代表分类名称
						listeners : {
							afterrender : function(combox) {
								var st = combox.getStore();
								st.on("load", function() {
									if(combox.getValue() == 0||combox.getValue()==1){
										combox.setValue("");
									}else{
										combox.setValue(combox
											.getValue());
									}
									combox.clearInvalid();
								})
							},
							'select' : function(combo,record, index){
								if(combo.getValue()==602){
									hideField(Ext.getCmp('personNameMortgage')) ;
									Ext.getCmp('personNameMortgage').disable() ;
									showField(Ext.getCmp('enterpriseNameMortgage')) ;
									Ext.getCmp('enterpriseNameMortgage').enable() ;
								}else{
									hideField(Ext.getCmp('enterpriseNameMortgage')) ;
									Ext.getCmp('enterpriseNameMortgage').disable() ;
									showField(Ext.getCmp('personNameMortgage')) ;
									Ext.getCmp('personNameMortgage').enable() ;
								}
							}
						}
					}]
				},{
					columnWidth : .5,
					labelWidth : 110,
					layout : 'form',
					defaults : {anchor : '95%'},
					items : [{
						xtype : "dickeycombo",
						nodeKey :'dblx',
						hiddenName : "procreditMortgage.assuretypeid",
						value : MortgageUpdateData.vProcreditDictionary.assuretypeid,
						fieldLabel : "担保类型",
						readOnly : true,
						itemName : '担保类型', // xx代表分类名称
						listeners : {
							afterrender : function(combox) {
								var st = combox.getStore();
								st.on("load", function() {
									if(combox.getValue() == 0||combox.getValue()==1){
										combox.setValue("");
									}else{
										combox.setValue(combox
											.getValue());
									}
									combox.clearInvalid();
								})
							}
						}
					},{
						xtype : "dickeycombo",
						nodeKey :'bzfs',
						hiddenName : "procreditMortgage.assuremodeid",
						value : MortgageUpdateData.vProcreditDictionary.assuremodeid,
						fieldLabel : "保证方式",
						readOnly : true,
						itemName : '保证方式', // xx代表分类名称
						listeners : {
							afterrender : function(combox) {
								var st = combox.getStore();
								st.on("load", function() {
									if(combox.getValue() == 0||combox.getValue()==1){
										combox.setValue("");
									}else{
										combox.setValue(combox
											.getValue());
									}
									combox.clearInvalid();
								})
							}
						}
					}]
				},{
				columnWidth : 1,
				labelWidth : 105,
				layout : 'form',
				defaults : {anchor : '97.5%'},
				items : [{
					id : 'enterpriseNameMortgage',
					xtype : 'combo',
					triggerClass :'x-form-search-trigger',
					hiddenName : 'customerEnterpriseName',
					fieldLabel : '所有权人',
					readOnly : true,
					onTriggerClick : function(){
						if(businessType=='Financing'){
							selectSlCompanyMain(selectSlCompanyHouseUpdate);
						}else{
							selectEnterprise(selectCustomer);
						}
                   },
					mode : 'romote',
					lazyInit : false,
					typeAhead : true,
					forceSelection :true,
					minChars : 1,
					listWidth : 230,
					store : getStoreByBusinessType(businessType,'enterprise'),
					displayField : businessType=="Financing"?'corName':'enterprisename',
					valueField : businessType=="Financing"?'companyMainId':'id',
					triggerAction : 'all'
				},{
					id : 'personNameMortgage',
					xtype : 'combo',
					hiddenName : 'customerPersonName',
					triggerClass :'x-form-search-trigger',
					onTriggerClick : function(){
						if(businessType=='Financing'){
							selectSlPersonMain(selectSlPersonHouseUpdate);
						}else{
							selectPWName(selectCustomer);
						}
	               	},
					fieldLabel : '所有权人',
					readOnly : true,
					mode : 'romote',
					lazyInit : false,
					typeAhead : true,
					minChars : 1,
					listWidth : 230,
					store : getStoreByBusinessType(businessType,'person'),
					displayField : 'name',
					valueField : businessType=="Financing"?'personMainId':'id',
					triggerAction : 'all'
				}]
			},{
					columnWidth : 1,
					labelWidth : 105,
					layout : 'form',
					defaults : {anchor : '97.5%'},
					items : [{
						id : 'mortgageNameCar',
						xtype : 'textfield',
						fieldLabel : '抵质押物名称',
						readOnly : true,
						value : MortgageUpdateData.vProcreditDictionary.mortgagename,
						maxLength : 50,
						maxLengthText : '最大输入长度50',
						name : 'procreditMortgage.mortgagename',
						blankText : '为必填内容'
					}]
				},{
					columnWidth : .5,
					layout : 'form',
					labelWidth : 105,
					defaults : {xtype : 'numberfield',anchor : '100%'},
					items : [{
						fieldLabel : '最终估价.万元',
						readOnly : true,
						value : MortgageUpdateData.vProcreditDictionary.finalprice==0?'':MortgageUpdateData.vProcreditDictionary.finalprice,
						maxLength : 23,
						maxLengthText : '最大输入长度23',
						name : 'procreditMortgage.finalprice'
					},{
						fieldLabel : '可担保额度.万元',
						readOnly : true,
						value : MortgageUpdateData.vProcreditDictionary.assuremoney==0?'':MortgageUpdateData.vProcreditDictionary.assuremoney,
						maxLength : 23,
						maxLengthText : '最大输入长度23',
						name : 'procreditMortgage.assuremoney'
					}]
				},{
					columnWidth : .5,
					layout : 'form',
					labelWidth : 160,
					defaults : {xtype : 'textfield',anchor : '95%'},
					items : [{
						fieldLabel : '所有权人与借款人的关系',
						readOnly : true,
						name : 'procreditMortgage.relation',
						maxLength : 50,
						maxLengthText : '最大输入长度50',
						value : MortgageUpdateData.vProcreditDictionary.relation
					}]
				},{
					columnWidth : 1,
					layout : 'form',
					labelWidth : 105,
					defaults : {anchor : '97%'},
					items : [{
						xtype : 'textarea',
						fieldLabel : '备注',
						readOnly : true,
						value : MortgageUpdateData.vProcreditDictionary.remarks,
						maxLength : 100,
						maxLengthText : '最大输入长度100',
						name : 'procreditMortgage.remarks'
					}]
				}]
			},{
				layout : 'column',
				xtype:'fieldset',
	            title: '查看<住宅>详细信息',
	            collapsible: true,
	            autoHeight:true,
	            anchor : '95%',
	            items : [{
					columnWidth : 1,
					labelWidth : 100,
					layout : 'form',
					defaults : {anchor : '97.5%'},
					items : [{
						xtype : 'textfield',
						fieldLabel : '房地产地点',
						readOnly : true,
						maxLength : 50,
						maxLengthText : '最大输入长度50',
						value : MortgageUpdateData.vProjMortHouse.houseaddress,
						name : 'procreditMortgageHouse.houseaddress'
					}]
	            },{
	            	columnWidth : .5,
					layout : 'form',
					labelWidth : 100,
					defaults : {xtype : 'textfield',anchor : '100%'},
					items : [{
						fieldLabel : '证件号码',
						readOnly : true,
						maxLength : 50,
						maxLengthText : '最大输入长度50',
						value : MortgageUpdateData.vProjMortHouse.certificatenumber,
						name : 'procreditMortgageHouse.certificatenumber'
					},{
						fieldLabel : '产权人',
						readOnly : true,
						maxLength : 50,
						maxLengthText : '最大输入长度50',
						value : MortgageUpdateData.vProjMortHouse.propertyperson,
						name : 'procreditMortgageHouse.propertyperson'
					},{
						fieldLabel : '共有人',
						readOnly : true,
						maxLength : 50,
						maxLengthText : '最大输入长度50',
						value : MortgageUpdateData.vProjMortHouse.mutualofperson,
						name : 'procreditMortgageHouse.mutualofperson'
					},{
						xtype : 'numberfield',
						fieldLabel : '建筑面积.㎡',
						readOnly : true,
						maxLength : 23,
						maxLengthText : '最大输入长度23',
						value : MortgageUpdateData.vProjMortHouse.buildacreage==0?'':MortgageUpdateData.vProjMortHouse.buildacreage,
						name : 'procreditMortgageHouse.buildacreage'
					},{
						xtype : 'datefield',
						fieldLabel : '建成年份',
						readOnly : true,
						format     : 'Y-m-d',
						value : MortgageUpdateData.vProjMortHouse.buildtime,
						name : 'procreditMortgageHouse.buildtime'
					},{
						xtype : 'numberfield',
						fieldLabel : '剩余年限.年',
						readOnly : true,
						maxLength : 23,
						maxLengthText : '最大输入长度23',
						value : MortgageUpdateData.vProjMortHouse.residualyears==0?'':MortgageUpdateData.vProjMortHouse.residualyears,
						name : 'procreditMortgageHouse.residualyears'
					}]
	            },{
	            	columnWidth : .5,
					layout : 'form',
					labelWidth : 110,
					defaults : {xtype : 'combo',anchor : '95%'},
					items : [{
						xtype : "dickeycombo",
						nodeKey :'cqxz',
						hiddenName : "procreditMortgageHouse.propertyrightid",
						value : MortgageUpdateData.vProjMortHouse.propertyRightId,
						fieldLabel : "产权性质",
						readOnly : true,
						editable :false,
						itemName : '产权性质', // xx代表分类名称
						listeners : {
							afterrender : function(combox) {
								var st = combox.getStore();
								st.on("load", function() {
									if(combox.getValue() == 0||combox.getValue()==1){
										combox.setValue("");
									}else{
										combox.setValue(combox
											.getValue());
									}
									combox.clearInvalid();
								})
							}
						}
					},{
						xtype : "dickeycombo",
						nodeKey :'jzsy',
						hiddenName : "procreditMortgageHouse.constructiontypeid",
						value : MortgageUpdateData.vProjMortHouse.constructionTypeId,
						fieldLabel : "建筑式样",
						readOnly : true,
						editable :false,
						itemName : '建筑式样', // xx代表分类名称
						listeners : {
							afterrender : function(combox) {
								var st = combox.getStore();
								st.on("load", function() {
									if(combox.getValue() == 0||combox.getValue()==1){
										combox.setValue("");
									}else{
										combox.setValue(combox
											.getValue());
									}
									combox.clearInvalid();
								})
							}
						}
					},{
						xtype : "dickeycombo",
						nodeKey :'jzjg',
						hiddenName : "procreditMortgageHouse.constructionframeid",
						value : MortgageUpdateData.vProjMortHouse.constructionFrameId,
						fieldLabel : "建筑结构",
						readOnly : true,
						itemName : '建筑结构', // xx代表分类名称
						listeners : {
							afterrender : function(combox) {
								var st = combox.getStore();
								st.on("load", function() {
									if(combox.getValue() == 0||combox.getValue()==1){
										combox.setValue("");
									}else{
										combox.setValue(combox
											.getValue());
									}
									combox.clearInvalid();
								})
							}
						}
					},{
						xtype : "dickeycombo",
						nodeKey :'hxjg',
						hiddenName : "procreditMortgageHouse.housetypeid",
						value : MortgageUpdateData.vProjMortHouse.houseTypeId,
						fieldLabel : "户型结构",
						readOnly : true,
						itemName : '户型结构', // xx代表分类名称
						listeners : {
							afterrender : function(combox) {
								var st = combox.getStore();
								st.on("load", function() {
									if(combox.getValue() == 0||combox.getValue()==1){
										combox.setValue("");
									}else{
										combox.setValue(combox
											.getValue());
									}
									combox.clearInvalid();
								})
							}
						}
					},{
						xtype : "dickeycombo",
						nodeKey :'ddms',
						hiddenName : "procreditMortgageHouse.descriptionid",
						value : MortgageUpdateData.vProjMortHouse.descriptionId,
						fieldLabel : "地段描述",
						readOnly : true,
						itemName : '地段描述', // xx代表分类名称
						listeners : {
							afterrender : function(combox) {
								var st = combox.getStore();
								st.on("load", function() {
									if(combox.getValue() == 0||combox.getValue()==1){
										combox.setValue("");
									}else{
										combox.setValue(combox
											.getValue());
									}
									combox.clearInvalid();
								})
							}
						}
					},{
						xtype : "dickeycombo",
						nodeKey :'djqk',
						hiddenName : "procreditMortgageHouse.registerinfoid",
						value : MortgageUpdateData.vProjMortHouse.registerInfoId,
						fieldLabel : "登记情况",
						readOnly : true,
						itemName : '登记情况', // xx代表分类名称
						listeners : {
							afterrender : function(combox) {
								var st = combox.getStore();
								st.on("load", function() {
									if(combox.getValue() == 0||combox.getValue()==1){
										combox.setValue("");
									}else{
										combox.setValue(combox
											.getValue());
									}
									combox.clearInvalid();
								})
							}
						}
					}]
	            },{
	            	columnWidth : .5,
					layout : 'form',
					labelWidth : 180,
					defaults : {xtype : 'numberfield',anchor : '100%'},
					items : [{
						fieldLabel : '同等房产单位交易单价1.元/㎡',
						readOnly : true,
						maxLength : 23,
						maxLengthText : '最大输入长度23',
						value : MortgageUpdateData.vProjMortHouse.exchangepriceone==0?'':MortgageUpdateData.vProjMortHouse.exchangepriceone,
						name : 'procreditMortgageHouse.exchangepriceone'
					},{
						fieldLabel : '同等房产单位交易单价2.元/㎡',
						readOnly : true,
						maxLength : 23,
						maxLengthText : '最大输入长度23',
						value : MortgageUpdateData.vProjMortHouse.exchangepricetow==0?'':MortgageUpdateData.vProjMortHouse.exchangepricetow,
						name : 'procreditMortgageHouse.exchangepricetow'
					},{
						fieldLabel : '同等房产单位交易单价3.元/㎡',
						readOnly : true,
						maxLength : 23,
						maxLengthText : '最大输入长度23',
						value : MortgageUpdateData.vProjMortHouse.exchangepricethree==0?'':MortgageUpdateData.vProjMortHouse.exchangepricethree,
						name : 'procreditMortgageHouse.exchangepricethree'
					}]
	            },{
	            	columnWidth : .5,
					layout : 'form',
					labelWidth : 135,
					defaults : {xtype : 'numberfield',anchor : '95%'},
					items : [{
						fieldLabel : '按揭余额.万元',
						readOnly : true,
						maxLength : 23,
						maxLengthText : '最大输入长度23',
						value : MortgageUpdateData.vProjMortHouse.mortgagesbalance==0?'':MortgageUpdateData.vProjMortHouse.mortgagesbalance,
						name : 'procreditMortgageHouse.mortgagesbalance'
					},{
						fieldLabel : '新房交易单价.元/㎡',
						readOnly : true,
						maxLength : 23,
						maxLengthText : '最大输入长度23',
						value : MortgageUpdateData.vProjMortHouse.exchangefinalprice==0?'':MortgageUpdateData.vProjMortHouse.exchangefinalprice,
						name : 'procreditMortgageHouse.exchangefinalprice'
					},{
						fieldLabel : '模型估价.万元',
						readOnly : true,
						maxLength : 23,
						maxLengthText : '最大输入长度23',
						value : MortgageUpdateData.vProjMortHouse.modelrangeprice==0?'':MortgageUpdateData.vProjMortHouse.modelrangeprice,
						name : 'procreditMortgageHouse.modelrangeprice'
					},{
		            	name : 'procreditMortgageHouse.id',
		            	value : MortgageUpdateData.vProjMortHouse.id,
		            	xtype : 'hidden'
		            },{
		            	name : 'procreditMortgage.id',
		            	value : MortgageUpdateData.vProcreditDictionary.id,
		            	xtype : 'hidden'
		            }]
	            }]
			}],
			tbar : new Ext.Toolbar({
				border : false,
				items : [{
				text : '保存',
				iconCls : 'btn-save',
				formBind : true,
				handler : function() {
					var personType_select = Ext.getCmp('personType_select').getValue();
					var mortgageName = Ext.getCmp('mortgageNameCar').getValue();
					var personNameMortgage = Ext.getCmp('personNameMortgage').getValue();
					var enterpriseNameMortgage = Ext.getCmp('enterpriseNameMortgage').getValue();
					if(mortgageName == ""){
						Ext.Msg.alert('状态','请输入<抵质押物名称>后再保存!');
					}else if(enterpriseNameMortgage == "" && personNameMortgage == ""){
						Ext.Msg.alert('状态','请选择<所有权人>后再保存!');
					}else if(personType_select == 602 && enterpriseNameMortgage == ""){
						Ext.Msg.alert('状态','请选择<所有权人>后再保存!');
					}else if(personType_select == 603 && personNameMortgage == ""){
						Ext.Msg.alert('状态','请选择<所有权人>后再保存!');
					}else{
						panel_updateHouse.getForm().submit({
							method : 'POST',
							waitTitle : '连接',
							waitMsg : '消息发送中...',
							success : function(form, action) {
								Ext.ux.Toast.msg('操作信息', '保存成功!');
								Ext.getCmp('win_updateHouse').destroy();
								refreshMortgageGridStore();
							},
							failure : function(form, action) {
								Ext.ux.Toast.msg('操作信息', '保存失败!');								
							}
						});
					}
				}
			}]
			})
		});
		
		var window_updateHouse = new Ext.Window({
			id : 'win_see_House',
			title :'查看住宅信息',
			iconCls : 'btn-update',
			width : (screen.width-180)*0.6,
			height : 460,
			constrainHeader : true ,
			collapsible : true, 
			frame : true ,
			border : false ,
			resizable : true,
			layout:'fit',
			autoScroll : true ,
			bodyStyle:'overflowX:hidden',
			constrain : true ,
			closable : true,
			modal : true,
			buttonAlign: 'right',
			items : [panel_updateHouse],
			listeners : {
				
			}
		});
		window_updateHouse.show();
		if(MortgageUpdateData.vProcreditDictionary.personTypeId == 602){
			hideField(Ext.getCmp('personNameMortgage')) ;
			showField(Ext.getCmp('enterpriseNameMortgage')) ;
			Ext.getCmp('enterpriseNameMortgage').valueNotFoundText = MortgageUpdateData.vProcreditDictionary.assureofnameEnterOrPerson,
			Ext.getCmp('enterpriseNameMortgage').setValue(MortgageUpdateData.vProcreditDictionary.assureofname) ;
			Ext.getCmp('enterpriseNameMortgage').originalValue = Ext.getCmp('enterpriseNameMortgage').getValue();
		}else if(MortgageUpdateData.vProcreditDictionary.personTypeId == 603){
			hideField(Ext.getCmp('enterpriseNameMortgage')) ;
			showField(Ext.getCmp('personNameMortgage')) ;
			Ext.getCmp('personNameMortgage').valueNotFoundText = (MortgageUpdateData.vProcreditDictionary.assureofnameEnterOrPerson);
			Ext.getCmp('personNameMortgage').setValue(MortgageUpdateData.vProcreditDictionary.assureofname) ;
			Ext.getCmp('personNameMortgage').originalValue = Ext.getCmp('personNameMortgage').getValue();
		}
	}

	function selectCustomer(obj) {
		if (obj.shortname) {//企业
			Ext.getCmp('enterpriseNameMortgage').setValue(obj.id);
			Ext.getCmp('enterpriseNameMortgage').setRawValue(obj.enterprisename) ;
		} else if (obj.name) {//个人
			Ext.getCmp('personNameMortgage').setValue(obj.id);
			Ext.getCmp('personNameMortgage').setRawValue(obj.name) ;
		}
	}
	
	function selectSlCompanyHouseUpdate(obj) {
		Ext.getCmp('enterpriseNameMortgage').setValue(obj.companyMainId);
		Ext.getCmp('enterpriseNameMortgage').setRawValue(obj.corName) ;
	}
	
	function selectSlPersonHouseUpdate(obj){
		Ext.getCmp('personNameMortgage').setValue(obj.personMainId);
		Ext.getCmp('personNameMortgage').setRawValue(obj.name) ;
	}
	
	Ext.Ajax.request({
		url : __ctxPath +'/credit/mortgage/seeHouseForUpdate.do',
		method : 'POST',
		success : function(response, request) {
			obj = Ext.util.JSON.decode(response.responseText);
			if(obj.success==true){
				showHouseInformation(obj.data) ;
			}else{
				Ext.Msg.alert('状态', obj.msg);
			}
		},
		failure : function(response) {
			Ext.Msg.alert('状态', '操作失败，请重试');
		},
		params : {
			id : mortgageid,
			businessType : businessType
		}
	});
}