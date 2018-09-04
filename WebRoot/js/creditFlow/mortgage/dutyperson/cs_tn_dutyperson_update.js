var updateDutyPerson = function(mortgageid,refreshMortgageGridStore,businessType){
	var anchor = '100%';
	
	var showDutyPersonInformation = function(MortgageUpdateData){
		var panel_updateDutyPerson = new Ext.form.FormPanel({
			id :'updateDutyPerson',
			url : __ctxPath +'/credit/mortgage/updateDutyperson.do',
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
	        		xtype : 'hidden',
	        		name : 'procreditMortgage.businessType',
	        		value : MortgageUpdateData.vProcreditDictionary.businessType
	        	},{
					columnWidth : .5,
					layout : 'form',
					labelWidth : 105,
					defaults : {anchor : '100%'},
					items : [{
						xtype : 'textfield',
						fieldLabel : '抵质押物类型',
						value : MortgageUpdateData.vProcreditDictionary.mortgagepersontypeforvalue,
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
						readOnly : true,
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
					id : 'personNameMortgage',
					xtype : 'combo',
					hiddenName : 'customerPersonName',
					triggerClass :'x-form-search-trigger',
					editable:false,
					onTriggerClick : function(){
						if(businessType=='Financing'){
							selectSlPersonMain(selectSlPersonPerUpdate);
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
					triggerAction : 'all',
					value : MortgageUpdateData.vProcreditDictionary.assureofname,
					valueNotFoundText : MortgageUpdateData.vProcreditDictionary.assureofnameEnterOrPerson,
					listeners : {
						'select' : function(combo,record,index){
							var name;
							var cardtype;
							var cardtypevalue;
							var cardnumber;
							var cellphone;
							if(businessType=='Financing'){
								name = record.get('name');
								cardtype = record.get('cardtype');
								cardtypevalue = record.get('cardtypevalue');
								cardnumber = record.get('cardnum');
								cellphone = record.get('linktel');
							}else{
								name = record.get('name');
								cardtype = record.get('cardtype');
								cardtypevalue = record.get('cardtypevalue');
								cardnumber = record.get('cardnumber');
								cellphone = record.get('cellphone');
							}
							setCustomerName(name,cardtype,cardtypevalue,cardnumber,cellphone);
						}
					}
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
	            title: '修改<无限连带责任-个人>详细信息',
	            collapsible: true,
	            autoHeight:true,
	            anchor : '95%',
	            items : [{
	            	columnWidth : .5,
					layout : 'form',
					labelWidth : 100,
					defaults : {xtype : 'textfield',anchor : '100%'},
					items : [{
						id : 'cardtypeUpdate_id',
						xtype : "dickeycombo",
						nodeKey :'card_type_key',
						//hiddenName : "person.cardtype",
						itemName : '证件类型', // xx代表分类名称
						fieldLabel : "证件类型",
						allowBlank : false,
						editable : true,
						value : MortgageUpdateData.vProjMortDutyPerson.cardtype,
						//emptyText : "请选择",
						blankText : "证件类型不能为空，请正确填写!",
						listeners : {
							afterrender : function(combox) {
								var st = combox.getStore();
								st.on("load", function() {
									if(combox.getValue() == 0||combox.getValue()==1||combox.getValue()==""||combox.getValue()==null){
										combox.setValue("");
									}else{
										combox.setValue(combox.getValue());
									}
									combox.clearInvalid();
								})
							},
							'change' : function(field,newValue,oldValue){
								Ext.getCmp('cardtype_personUpdate').setValue(newValue);//证件类型id--传后台
							}
						}
						/*
						id : 'cardtypeUpdate_id',
						xtype : 'csRemoteCombo',
						fieldLabel : '证件类型',
						width : 80,
						listWidth : 80,
						allowBlank : false,
						blankText : '证件类型为必填内容',
						dicId : cardtypeDicId,
						value : MortgageUpdateData.vProjMortDutyPerson.cardtypevalue,
						listeners : {
							'change' : function(field,newValue,oldValue){
								if(newValue == 628){
									Ext.getCmp('card_numberUpdate').index = 628;
									Ext.getCmp('card_numberUpdate').regex = /^\d{15}(\d{2}[A-Za-z0-9])?$/;
									Ext.getCmp('card_numberUpdate').regexText = '身份证号码无效';
									Ext.getCmp('cardtype_personUpdate').setValue(628);//证件类型id--传后台
								}else if(newValue == 629){
									Ext.getCmp('card_numberUpdate').index = 629;
									Ext.getCmp('card_numberUpdate').regex  ;
									Ext.getCmp('card_numberUpdate').regexText = '军官证号码无效';
									Ext.getCmp('cardtype_personUpdate').setValue(629);//证件类型id--传后台
								}else if(newValue == 630){
									Ext.getCmp('card_numberUpdate').index = 630;
									Ext.getCmp('card_numberUpdate').regex ;
									Ext.getCmp('card_numberUpdate').regexText = '护照号码无效';
									Ext.getCmp('cardtype_personUpdate').setValue(630);//证件类型id--传后台
								}else {
									Ext.getCmp('card_numberUpdate').index = 0;
									Ext.getCmp('cardtype_personUpdate').setValue(631);//证件类型id--传后台
								}
							}
						}
					*/},{
						id : 'card_numberUpdate',
						xtype : 'textfield',
						fieldLabel : '证件号码',
						name : 'person.cardnumber',
						value : MortgageUpdateData.vProjMortDutyPerson.idcard,
						allowBlank : false,
						blankText : '证件号码为必填内容'
					},{
						xtype : 'numberfield',
						fieldLabel : '资产价值.万元',
						maxLength : 23,
						maxLengthText : '最大输入长度23',
						value : MortgageUpdateData.vProjMortDutyPerson.assetvalue==0?'':MortgageUpdateData.vProjMortDutyPerson.assetvalue,
						name : 'procreditMortgagePerson.assetvalue'
					}]
	            },{
	            	columnWidth : .5,
					layout : 'form',
					labelWidth : 110,
					defaults : {xtype : 'textfield',anchor : '95%'},
					items : [{
						id : 'person_phoneUpdate',
						fieldLabel : '联系电话',
						maxLength : 50,
						maxLengthText : '最大输入长度50',
						value : MortgageUpdateData.vProjMortDutyPerson.phone,
						name : 'person.cellphone'
					},{
						xtype : "dickeycombo",
						nodeKey :'shgx',
						hiddenName : "procreditMortgagePerson.societynexusid",
						value : MortgageUpdateData.vProjMortDutyPerson.societyNexusId,
						fieldLabel : "社会关系",
						editable :false,
						itemName : '社会关系', // xx代表分类名称
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
						maxLength : 23,
						maxLengthText : '最大输入长度23',
						value : MortgageUpdateData.vProjMortDutyPerson.modelrangeprice==0?'':MortgageUpdateData.vProjMortDutyPerson.modelrangeprice,
						name : 'procreditMortgagePerson.modelrangeprice'
					},{
		            	name : 'procreditMortgagePerson.id',
		            	value : MortgageUpdateData.vProjMortDutyPerson.id,
		            	xtype : 'hidden'
		            },{
		            	name : 'procreditMortgage.id',
		            	value : MortgageUpdateData.vProcreditDictionary.id,
		            	xtype : 'hidden'
		            },{
						id : 'cardtype_personUpdate',
		            	name : 'cardtype',
		            	xtype : 'hidden',
		            	value : MortgageUpdateData.vProjMortDutyPerson.cardtype
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
						var card_numberUpdate = Ext.getCmp('card_numberUpdate').getValue();
						var mortgageName = Ext.getCmp('mortgageNameCar').getValue();
						var personNameMortgage = Ext.getCmp('personNameMortgage').getValue();
						if(mortgageName == ""){
							Ext.Msg.alert('状态','请输入<抵质押物名称>后再保存!');
						}else if(personNameMortgage == ""){
							Ext.Msg.alert('状态','请选择<所有权人>后再保存!');
						}else if(card_numberUpdate == ""){
							Ext.Msg.alert('状态','<证件号码>不能为空!');
						}else{
							panel_updateDutyPerson.getForm().submit({
								method : 'POST',
								waitTitle : '连接',
								waitMsg : '消息发送中...',
								success : function(form, action) {
									Ext.ux.Toast.msg('操作信息', '保存成功!');
									Ext.getCmp('win_updateDutyPerson').destroy();
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
		
		var window_updateDutyPerson = new Ext.Window({
			id : 'win_updateDutyPerson',
			title :'修改无限连带责任-个人信息',
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
			items : [panel_updateDutyPerson],
			listeners : {
				'beforeclose' : function(){
					if(panel_updateDutyPerson.getForm().isDirty()){
						Ext.Msg.confirm('操作提示','数据被修改过,是否保存?',function(btn){
							if(btn=='yes'){
								panel_updateDutyPerson.getTopToolbar().items.itemAt(0).handler.call() ;
							}else{
								panel_updateDutyPerson.getForm().reset() ;
								window_updateDutyPerson.destroy() ;
							}
						}) ;
						return false ;
					}
				}
			}
		});
		window_updateDutyPerson.show();
	}
	
	function setCustomerName(fullName,cardtype,cardtypevalue,cardnumber,cellphone){
		
		Ext.getCmp('cardtypeUpdate_id').setValue(cardtype);//证件类型id
		Ext.getCmp('cardtypeUpdate_id').setRawValue(cardtypevalue);//证件类型值
		Ext.getCmp('card_numberUpdate').setValue(cardnumber);//证件号码
		Ext.getCmp('person_phoneUpdate').setValue(cellphone);//联系电话
		Ext.getCmp('cardtype_personUpdate').setValue(cardtype);//证件类型id--传后台
	};
	
	function selectCustomer(obj) {
		Ext.getCmp('personNameMortgage').setValue(obj.id);
		Ext.getCmp('personNameMortgage').setRawValue(obj.name) ;
		setCustomerName(obj.name,obj.cardtype,obj.cardtypevalue,obj.cardnumber,obj.cellphone);
	}
	
	function selectSlPersonPerUpdate(obj){
		Ext.getCmp('personNameMortgage').setValue(obj.personMainId);
		Ext.getCmp('personNameMortgage').setRawValue(obj.name) ;
		setCustomerName(obj.name,obj.cardtype,obj.cardtypevalue,obj.cardnum,obj.linktel);
	}
	
	Ext.Ajax.request({
		url : __ctxPath +'/credit/mortgage/seeDutypersonForUpdate.do',
		method : 'POST',
		success : function(response, request) {
			obj = Ext.util.JSON.decode(response.responseText);
			if(obj.success==true){
				showDutyPersonInformation(obj.data) ;
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