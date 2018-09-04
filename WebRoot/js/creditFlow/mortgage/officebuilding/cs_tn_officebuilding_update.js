var updateOfficeBuilding = function(mortgageid,refreshMortgageGridStore,businessType){
	
	var anchor = '100%';
	var showOfficebuildingInformation = function(MortgageUpdateData){
		var panel_updateOfficebuilding = new Ext.form.FormPanel({
			id :'updateOfficebuilding',
			url : __ctxPath +'/credit/mortgage/updateOfficebuilding.do',
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
	            title: '修改<抵质押物>基础信息',
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
						allowBlank : false,
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
					fieldLabel : '<font color=red>*</font>所有权人',
					editable:false,
					onTriggerClick : function(){
						if(businessType=='Financing'){
							selectSlCompanyMain(selectSlCompanyOfficeUpdate);
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
					editable:false,
					onTriggerClick : function(){
						if(businessType=='Financing'){
							selectSlPersonMain(selectSlPersonOfficeUpdate);
						}else{
							selectPWName(selectCustomer);
						}
	               	},
					fieldLabel : '<font color=red>*</font>所有权人',
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
						value : MortgageUpdateData.vProcreditDictionary.mortgagename,
						maxLength : 50,
						maxLengthText : '最大输入长度50',
						name : 'procreditMortgage.mortgagename',
						allowBlank : false,
						blankText : '为必填内容'
					}]
				},{
					columnWidth : .5,
					layout : 'form',
					labelWidth : 105,
					defaults : {xtype : 'numberfield',anchor : '100%'},
					items : [{
						fieldLabel : '最终估价.万元',
						value : MortgageUpdateData.vProcreditDictionary.finalprice==0?'':MortgageUpdateData.vProcreditDictionary.finalprice,
						maxLength : 23,
						maxLengthText : '最大输入长度23',
						name : 'procreditMortgage.finalprice'
					},{
						fieldLabel : '可担保额度.万元',
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
						value : MortgageUpdateData.vProcreditDictionary.remarks,
						maxLength : 100,
						maxLengthText : '最大输入长度100',
						name : 'procreditMortgage.remarks'
					}]
				}]
			},{
				layout : 'column',
				xtype:'fieldset',
	            title: '修改<商铺写字楼>详细信息',
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
						value : MortgageUpdateData.vProjMortOfficeBuilding.houseaddress,
						maxLength : 50,
						maxLengthText : '最大输入长度50',
						name : 'procreditMortgageOfficebuilding.houseaddress'
					}]
	            },{
	            	columnWidth : .5,
					layout : 'form',
					labelWidth : 100,
					defaults : {xtype : 'textfield',anchor : '100%'},
					items : [{
						fieldLabel : '证件号码',
						value : MortgageUpdateData.vProjMortOfficeBuilding.certificatenumber,
						maxLength : 50,
						maxLengthText : '最大输入长度50',
						name : 'procreditMortgageOfficebuilding.certificatenumber'
					},{
						fieldLabel : '产权人',
						value : MortgageUpdateData.vProjMortOfficeBuilding.propertyperson,
						maxLength : 50,
						maxLengthText : '最大输入长度50',
						name : 'procreditMortgageOfficebuilding.propertyperson'
					},{
						fieldLabel : '共有人',
						value : MortgageUpdateData.vProjMortOfficeBuilding.mutualofperson,
						maxLength : 50,
						maxLengthText : '最大输入长度50',
						name : 'procreditMortgageOfficebuilding.mutualofperson'
					},{
						xtype : 'numberfield',
						fieldLabel : '层高',
						value : MortgageUpdateData.vProjMortOfficeBuilding.floors==0?'':MortgageUpdateData.vProjMortOfficeBuilding.floors,
						maxLength : 11,
						maxLengthText : '最大输入长度11',
						name : 'procreditMortgageOfficebuilding.floors'
					},{
						xtype : 'numberfield',
						fieldLabel : '建筑面积.㎡',
						value : MortgageUpdateData.vProjMortOfficeBuilding.buildacreage==0?'':MortgageUpdateData.vProjMortOfficeBuilding.buildacreage,
						maxLength : 23,
						maxLengthText : '最大输入长度23',
						name : 'procreditMortgageOfficebuilding.buildacreage'
					},{
						xtype : 'numberfield',
						fieldLabel : '按揭余额.万元',
						value : MortgageUpdateData.vProjMortOfficeBuilding.mortgagesbalance==0?'':MortgageUpdateData.vProjMortOfficeBuilding.mortgagesbalance,
						maxLength : 23,
						maxLengthText : '最大输入长度23',
						name : 'procreditMortgageOfficebuilding.mortgagesbalance'
					}]
	            },{
	            	columnWidth : .5,
					layout : 'form',
					labelWidth : 110,
					defaults : {xtype : 'combo',anchor : '95%'},
					items : [{
						xtype : "dickeycombo",
						nodeKey :'cqxz',
						hiddenName : "procreditMortgageOfficebuilding.propertyrightid",
						value : MortgageUpdateData.vProjMortOfficeBuilding.propertyRightId,
						fieldLabel : "产权性质",
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
						hiddenName : "procreditMortgageOfficebuilding.constructiontypeid",
						value : MortgageUpdateData.vProjMortOfficeBuilding.constructionTypeId,
						fieldLabel : "建筑式样",
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
						hiddenName : "procreditMortgageOfficebuilding.constructionframeid",
						value : MortgageUpdateData.vProjMortOfficeBuilding.constructionFrameId,
						fieldLabel : "建筑结构",
						editable :false,
						itemName : '建筑结构', // xx代表分类名称
						//anchor : "100%",
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
						hiddenName : "procreditMortgageOfficebuilding.housetypeid",
						value : MortgageUpdateData.vProjMortOfficeBuilding.houseTypeId,
						fieldLabel : "户型结构",
						editable :false,
						itemName : '户型结构', // xx代表分类名称
						//anchor : "100%",
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
						hiddenName : "procreditMortgageOfficebuilding.descriptionid",
						value : MortgageUpdateData.vProjMortOfficeBuilding.descriptionId,
						fieldLabel : "地段描述",
						editable :false,
						itemName : '地段描述', // xx代表分类名称
						//anchor : "100%",
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
						hiddenName : "procreditMortgageOfficebuilding.registerinfoid",
						value : MortgageUpdateData.vProjMortOfficeBuilding.registerInfoId,
						fieldLabel : "登记情况",
						editable :false,
						itemName : '登记情况', // xx代表分类名称
						//anchor : "100%",
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
						fieldLabel : '同等房产租金1.元/月/㎡',
						value : MortgageUpdateData.vProjMortOfficeBuilding.rentone,
						value : MortgageUpdateData.vProjMortOfficeBuilding.rentone==0?'':MortgageUpdateData.vProjMortOfficeBuilding.rentone,
						maxLength : 23,
						maxLengthText : '最大输入长度23',
						name : 'procreditMortgageOfficebuilding.rentone'
					},{
						fieldLabel : '同等房产租金2.元/月/㎡',
						value : MortgageUpdateData.vProjMortOfficeBuilding.renttow==0?'':MortgageUpdateData.vProjMortOfficeBuilding.renttow,
						maxLength : 23,
						maxLengthText : '最大输入长度23',
						name : 'procreditMortgageOfficebuilding.renttow'
					},{
						fieldLabel : '同等房产单位交易单价1.元/㎡',
						value : MortgageUpdateData.vProjMortOfficeBuilding.exchangepriceone==0?'':MortgageUpdateData.vProjMortOfficeBuilding.exchangepriceone,
						maxLength : 23,
						maxLengthText : '最大输入长度23',
						name : 'procreditMortgageOfficebuilding.exchangepriceone'
					},{
						fieldLabel : '同等房产单位交易单价2.元/㎡',
						value : MortgageUpdateData.vProjMortOfficeBuilding.exchangepricetow==0?'':MortgageUpdateData.vProjMortOfficeBuilding.exchangepricetow,
						maxLength : 23,
						maxLengthText : '最大输入长度23',
						name : 'procreditMortgageOfficebuilding.exchangepricetow'
					},{
						fieldLabel : '同等房产单位交易单价3.元/㎡',
						value : MortgageUpdateData.vProjMortOfficeBuilding.exchangepricethree==0?'':MortgageUpdateData.vProjMortOfficeBuilding.exchangepricethree,
						maxLength : 23,
						maxLengthText : '最大输入长度23',
						name : 'procreditMortgageOfficebuilding.exchangepricethree'
					}]
	            },{
	            	columnWidth : .5,
					layout : 'form',
					labelWidth : 130,
					defaults : {xtype : 'numberfield',anchor : '95%'},
					items : [{
							xtype : 'datefield',
							fieldLabel : '建成年份',
							value : MortgageUpdateData.vProjMortOfficeBuilding.buildtime,
							format     : 'Y-m-d',
							name : 'procreditMortgageOfficebuilding.buildtime'
					},{
						fieldLabel : '剩余年限.年',
						value : MortgageUpdateData.vProjMortOfficeBuilding.residualyears==0?'':MortgageUpdateData.vProjMortOfficeBuilding.residualyears,
						maxLength : 23,
						maxLengthText : '最大输入长度23',
						name : 'procreditMortgageOfficebuilding.residualyears'
					},{
						fieldLabel : '新房交易单价.元/㎡',
						value : MortgageUpdateData.vProjMortOfficeBuilding.exchangefinalprice==0?'':MortgageUpdateData.vProjMortOfficeBuilding.exchangefinalprice,
						maxLength : 23,
						maxLengthText : '最大输入长度23',
						name : 'procreditMortgageOfficebuilding.exchangefinalprice'
					},{
						fieldLabel : '租赁模型估算.万元',
						value : MortgageUpdateData.vProjMortOfficeBuilding.leaseholdrangeprice==0?'':MortgageUpdateData.vProjMortOfficeBuilding.leaseholdrangeprice,
						maxLength : 23,
						maxLengthText : '最大输入长度23',
						name : 'procreditMortgageOfficebuilding.leaseholdrangeprice'
					},{
						fieldLabel : '模型估价.万元',
						value : MortgageUpdateData.vProjMortOfficeBuilding.modelrangeprice==0?'':MortgageUpdateData.vProjMortOfficeBuilding.modelrangeprice,
						maxLength : 23,
						maxLengthText : '最大输入长度23',
						name : 'procreditMortgageOfficebuilding.modelrangeprice'
					},{
		            	name : 'procreditMortgageOfficebuilding.id',
		            	value : MortgageUpdateData.vProjMortOfficeBuilding.id,
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
						panel_updateOfficebuilding.getForm().submit({
							method : 'POST',
							waitTitle : '连接',
							waitMsg : '消息发送中...',
							success : function(form, action) {
								Ext.ux.Toast.msg('操作信息', '保存成功!');
								Ext.getCmp('win_updateOfficebuilding').destroy();
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
		
		var window_updateOfficebuilding = new Ext.Window({
			id : 'win_updateOfficebuilding',
			title :'修改商铺写字楼信息',
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
			items : [panel_updateOfficebuilding],
			listeners : {
				'beforeclose' : function(){
					if(panel_updateOfficebuilding.getForm().isDirty()){
						Ext.Msg.confirm('操作提示','数据被修改过,是否保存?',function(btn){
							if(btn=='yes'){
								panel_updateOfficebuilding.getTopToolbar().items.itemAt(0).handler.call() ;
							}else{
								panel_updateOfficebuilding.getForm().reset() ;
								window_updateOfficebuilding.destroy() ;
							}
						}) ;
						return false ;
					}
				}
			}
		});
		window_updateOfficebuilding.show();
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
	
	function selectSlCompanyOfficeUpdate(obj) {
		Ext.getCmp('enterpriseNameMortgage').setValue(obj.companyMainId);
		Ext.getCmp('enterpriseNameMortgage').setRawValue(obj.corName) ;
	}
	
	function selectSlPersonOfficeUpdate(obj){
		Ext.getCmp('personNameMortgage').setValue(obj.personMainId);
		Ext.getCmp('personNameMortgage').setRawValue(obj.name) ;
	}
	
	Ext.Ajax.request({
		url : __ctxPath +'/credit/mortgage/seeOfficebuildingForUpdate.do',
		method : 'POST',
		success : function(response, request) {
			obj = Ext.util.JSON.decode(response.responseText);
			if(obj.success==true){
				showOfficebuildingInformation(obj.data) ;
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