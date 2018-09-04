var see_MortgageCar = function(mortgageid,refreshMortgageGridStore,businessType,isAllow){
	var mortgageData;
	
	var showCarInformation = function(MortgageUpdateData,processCarData){
		var panel_updateCar = new Ext.form.FormPanel({
			id :'see_MortgageCar',
			url : __ctxPath +'/credit/mortgage/updateVehicle.do',
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
						editable :false,
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
						editable :false,
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
						editable :false,
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
							selectSlCompanyMain(selectSlCompanyVehicleUpdate);
						}else{
							selectEnterprise(selectCustomer);
						}
                   },
					mode : 'romote',
					lazyInit : true,
					typeAhead : true,
					forceSelection :true,
					minChars : 1,
					listWidth : 230,
					readOnly : true,
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
						//selectPWName(selectCustomer);
						if(businessType=='Financing'){
							selectSlPersonMain(selectSlPersonVehicleUpdate);
						}else{
							selectPWName(selectCustomer);
						}
	               	},
					fieldLabel : '所有权人',
					readOnly : true,
					mode : 'romote',
					lazyInit : true,
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
	            title: '查看<车辆>详细信息',
	            collapsible: true,
	            autoHeight:true,
	            anchor : '95%',
	            items : [{
					columnWidth : 1,
					labelWidth : 120,
					layout : 'form',
					defaults : {anchor : '97.5%'},
					items : [{
						columnWidth : 1,
						labelWidth : 120,
						layout : 'form',
						defaults : {anchor : '100%'},
						items : [{
							id : 'factoryNameUpdate',
							xtype : 'combo',
							hiddenName : 'manufacturer',
							triggerClass :'x-form-search-trigger',
							onTriggerClick : function(){
								selectCarList(returnCarMessageInfo);
			               	},
							fieldLabel : '制造商',
							readOnly : true,
							mode : 'romote',
							lazyInit : false,
							//allowBlank : false,
							blankText : '为必填内容',
							typeAhead : true,
							minChars : 1,
							listWidth : 230,
							store : new Ext.data.JsonStore({
								url : __ctxPath +'/credit/mortgage/ajaxQueryCarFactoryForCombo.do',
								root : 'topics',
								autoLoad : true,
								fields : [{
											name : 'id'
										}, {
											name : 'carFirmName'
										}],
								listeners : {
									'load' : function(s,r,o){
										if(s.getCount()==0){
											Ext.getCmp('factoryNameUpdate').markInvalid('没有查找到匹配的记录') ;
										}
									}
								}
							}),
							displayField : 'carFirmName',
							valueField : 'id',
							triggerAction : 'all',
							value : MortgageUpdateData.vProjMortCar.manufacturer,
							valueNotFoundText : MortgageUpdateData.vProjMortCar.manufacturerValue
						}]
					}]
	            },{
	            	columnWidth : .5,
					layout : 'form',
					labelWidth : 120,
					defaults : {xtype : 'numberfield',anchor : '100%'},
					items : [{
						id : 'carNumberUpDate',
						xtype : 'textfield',
						fieldLabel : '车型编号',
						readOnly : true,
			            cls : 'readOnlyClass',
			            value : processCarData==1?'':processCarData.carNumber
					},{
						xtype : 'textfield',
						id : 'carStyleNameUpdate',
						fieldLabel : '车系',
						readOnly : true,
			            cls : 'readOnlyClass',
			            value : processCarData==1?'':processCarData.carStyleName
					},{
						id : 'carModelNameUpdate',
						xtype : 'textfield',
						fieldLabel : '车型',
						readOnly : true,
			            cls : 'readOnlyClass',
			            value : processCarData==1?'':processCarData.carModelName
					},{
						xtype : 'textfield',
						fieldLabel : '发动机号',
						readOnly : true,
						maxLength : 50,
						maxLengthText : '最大输入长度50',
						name : 'procreditMortgageCar.engineNo',
						value : MortgageUpdateData.vProjMortCar.engineNo
					},{
						xtype : 'textfield',
						fieldLabel : '车架号',
						readOnly : true,
						maxLength : 50,
						maxLengthText : '最大输入长度50',
						name : 'procreditMortgageCar.vin',
						value : MortgageUpdateData.vProjMortCar.vin
					},{
						fieldLabel : '新车价格.万元',
						readOnly : true,
						value : MortgageUpdateData.vProjMortCar.carprice==0?'':MortgageUpdateData.vProjMortCar.carprice,
						maxLength : 23,
						maxLengthText : '最大输入长度23',
						name : 'procreditMortgageCar.carprice'
					},{
						fieldLabel : '里程数.公里',
						readOnly : true,
						value : MortgageUpdateData.vProjMortCar.totalkilometres==0?'':MortgageUpdateData.vProjMortCar.totalkilometres,
						maxLength : 23,
						maxLengthText : '最大输入长度23',
						name : 'procreditMortgageCar.totalkilometres'
					},{
						fieldLabel : '事故次数',
						readOnly : true,
						value : MortgageUpdateData.vProjMortCar.accidenttimes==0?'':MortgageUpdateData.vProjMortCar.accidenttimes,
						maxLength : 11,
						maxLengthText : '最大输入长度11',
						name : 'procreditMortgageCar.accidenttimes'
					},{
						fieldLabel : '市场交易价格1.万元',
						readOnly : true,
						value : MortgageUpdateData.vProjMortCar.exchangepriceone==0?'':MortgageUpdateData.vProjMortCar.exchangepriceone,
						maxLength : 23,
						maxLengthText : '最大输入长度23',
						name : 'procreditMortgageCar.exchangepriceone'
					},{
						fieldLabel : '市场交易价格2.万元',
						readOnly : true,
						value : MortgageUpdateData.vProjMortCar.exchangepricetow==0?'':MortgageUpdateData.vProjMortCar.exchangepricetow,
						maxLength : 23,
						maxLengthText : '最大输入长度23',
						name : 'procreditMortgageCar.exchangepricetow'
					},{
		            	name : 'procreditMortgageCar.id',
		            	value : MortgageUpdateData.vProjMortCar.id,
		            	xtype : 'hidden'
		            },{
		            	name : 'procreditMortgage.id',
		            	value : MortgageUpdateData.vProcreditDictionary.id,
		            	xtype : 'hidden'
		            }]
	            },{
	            	columnWidth : .5,
					layout : 'form',
					labelWidth : 100,
					defaults : {xtype : 'textfield',anchor : '95%'},
					items : [{
						id : 'displacementUpdate',
						fieldLabel : '排量',
						readOnly : true,
						readOnly : true,
			            cls : 'readOnlyClass',
			            value : processCarData==1?'':processCarData.displacementValue
					},{
						id : 'configurationUpdate',
						readOnly : true,
						fieldLabel : '配置',
						readOnly : true,
			            cls : 'readOnlyClass',
			            value : processCarData==1?'':processCarData.configuration
					},{
						id : 'seatingUpdate',
						fieldLabel : '座位数',
						readOnly : true,
			            cls : 'readOnlyClass',
			            value : processCarData==1?'':processCarData.seatingValue
					},{
						xtype : "dickeycombo",
						nodeKey :'clys',
						hiddenName : "procreditMortgageCar.carColor",
						value : MortgageUpdateData.vProjMortCar.carColor,
						readOnly : true,
						fieldLabel : "车辆颜色",
						editable :false,
						itemName : '车辆颜色', // xx代表分类名称
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
						hiddenName : "procreditMortgageCar.enregisterinfoid",
						value : MortgageUpdateData.vProjMortCar.enregisterinfoid,
						fieldLabel : "登记情况",
						readOnly : true,
						editable :false,
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
					},{
						xtype : 'datefield',
						fieldLabel : '出厂日期',
						readOnly : true,
						format     : 'Y-m-d',
						value : MortgageUpdateData.vProjMortCar.leavefactorydate,
						name : 'procreditMortgageCar.leavefactorydate'
					},{
						xtype : 'numberfield',
						fieldLabel : '使用时间.年',
						readOnly : true,
						value : MortgageUpdateData.vProjMortCar.haveusedtime==0?'':MortgageUpdateData.vProjMortCar.haveusedtime,
						maxLength : 23,
						maxLengthText : '最大输入长度23',
						name : 'procreditMortgageCar.haveusedtime'
					},{
						xtype : "dickeycombo",
						nodeKey :'ck',
						hiddenName : "procreditMortgageCar.carinfoid",
						value : MortgageUpdateData.vProjMortCar.carinfoid,
						fieldLabel : "车况",
						readOnly : true,
						editable :false,
						itemName : '车况', // xx代表分类名称
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
						xtype : 'numberfield',
						fieldLabel : '模型估价.万元',
						readOnly : true,
						value : MortgageUpdateData.vProjMortCar.modelrangeprice==0?'':MortgageUpdateData.vProjMortCar.modelrangeprice,
						maxLength : 23,
						maxLengthText : '最大输入长度23',
						name : 'procreditMortgageCar.modelrangeprice'
					}]
	            }]
			}]
		});
		
		var window_updateCar = new Ext.Window({
			id : 'win_see_MortgageCar',
			title :'查看信息>>>车辆',
			iconCls : 'btn-update',
			y : 20,
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
			items : [panel_updateCar],
			listeners : {
		
			}
		});
		if(isAllow){
			window_updateCar.findById('factoryNameUpdate').fieldLabel = "<font color=red>*</font>制造商";
			window_updateCar.findById('factoryNameUpdate').allowBlank = true;
			window_updateCar.doLayout();
			window_updateCar.show();
		}else{
			window_updateCar.show();
		}
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
	
	function selectSlCompanyVehicleUpdate(obj) {
		Ext.getCmp('enterpriseNameMortgage').setValue(obj.companyMainId);
		Ext.getCmp('enterpriseNameMortgage').setRawValue(obj.corName) ;
	}
	
	function selectSlPersonVehicleUpdate(obj){
		Ext.getCmp('personNameMortgage').setValue(obj.personMainId);
		Ext.getCmp('personNameMortgage').setRawValue(obj.name) ;
	}
	
		//汽车参照
	var returnCarMessageInfo = function(obj){
		Ext.getCmp('factoryNameUpdate').setValue(obj.id);
		Ext.getCmp('factoryNameUpdate').setRawValue(obj.carFirmName);
		
		Ext.getCmp('carNumberUpDate').setValue(obj.carNumber);//车型编号
		Ext.getCmp('carStyleNameUpdate').setValue(obj.carStyleName);//车系
		Ext.getCmp('carModelNameUpdate').setValue(obj.carModelName);//车型
		Ext.getCmp('displacementUpdate').setValue(obj.displacementValue);//排量
		Ext.getCmp('configurationUpdate').setValue(obj.configuration);//配置
		Ext.getCmp('seatingUpdate').setValue(obj.seatingValue);//座位数
	}
	

	Ext.Ajax.request({
		url : __ctxPath +'/credit/mortgage/seeVehicleForUpdate.do',
		method : 'POST',
		success : function(response, request) {
			obj = Ext.util.JSON.decode(response.responseText);
			if(obj.success==true){
				var manufacturerId = obj.data.vProjMortCar.manufacturer;
				if(manufacturerId==0){
					showCarInformation(obj.data,1) ;
				}else{
					Ext.Ajax.request({
						url : __ctxPath +'/credit/mortgage/ajaxGetProcessCarData.do',
						method : 'POST',
						success : function(response, request) {
							objProcessCar = Ext.util.JSON.decode(response.responseText);
							var processCarData = objProcessCar.data;
							if(objProcessCar.success==true){
								showCarInformation(obj.data,processCarData) ;
							}else{
								Ext.Msg.alert('状态', objProcessCar.msg);
							}
						},
						failure : function(response) {
							Ext.Msg.alert('状态', '操作失败，请重试');
						},
						params : {
							manufacturer : manufacturerId
						}
					});
				}
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