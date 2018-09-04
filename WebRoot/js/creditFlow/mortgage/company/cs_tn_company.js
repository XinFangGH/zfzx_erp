var addMortgageCompany = function(piKey,personTypeParams,assuretypeidParams,refreshMortgageGridStore,businessType){
	var anchor = '100%';
	var topBar = new Ext.Toolbar({
			border : false,
			items : [{
				text : '保存',
				iconCls : 'btn-save',
				formBind : true,
				handler : function() {
					var mortgageName = Ext.getCmp('mortgageName').getValue();
					var enterpriseNameMortgage = Ext.getCmp('enterpriseNameMortgage').getValue();
					
					var address_en = Ext.getCmp('address_en').getValue();
					var cciaa_en = Ext.getCmp('cciaa_en').getValue();
					var legalperson_en = Ext.getCmp('legalperson_en').getValue();
					var phone_en = Ext.getCmp('phone_en').getValue();
					var legalpersonid_en = Ext.getCmp('legalpersonid_en').getValue();
					
					if(mortgageName == ""){
						Ext.ux.Toast.msg('状态','请输入<抵质押物名称>后再保存!');
					}else if(enterpriseNameMortgage == ""){
						Ext.ux.Toast.msg('状态','请选择<所有权人>后再保存!');
					}else{
						panel_addCompany.getForm().submit({
							method : 'POST',
							waitTitle : '连接',
							waitMsg : '消息发送中...',
							success : function(form, action) {
								Ext.ux.Toast.msg('操作信息', '保存成功!');
								Ext.getCmp('win_addCompany').destroy();
								Ext.getCmp('mortgage_add_win').destroy();
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
	var panel_addCompany = new Ext.form.FormPanel({
		id :'addCompany',
		url : __ctxPath +'/credit/mortgage/addCompany.do',
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
            title: '填写<抵质押物>基础信息',
            collapsible: true,
            autoHeight:true,
            anchor : '95%',
			items : [{
				columnWidth : .5,
				layout : 'form',
				labelWidth : 105,
				defaults : {anchor : '100%'},
				items : [{
	        		xtype : 'hidden',
	        		name : 'projectId',
	        		value : piKey
	        	},{
	        		xtype : 'hidden',
	        		name : 'procreditMortgage.businessType',
	        		value : businessType
	        	},{
					xtype : 'textfield',
					fieldLabel : '抵质押物类型',
					value : '无限连带责任-公司',
					allowBlank : false,
					blankText : '为必填内容',
					readOnly : true,
		            cls : 'readOnlyClass'
				},{
					id : 'persontype_id',
					xtype : "dickeycombo",
					nodeKey : 'syrlx',
					hiddenName : "procreditMortgage.personType",
					fieldLabel : "所有人类型",
					itemName : '所有人类型', // xx代表分类名称
					value : personTypeParams,
					readOnly : true,
					listeners : {
						afterrender : function(combox) {
							var st = combox.getStore();
							st.on("load", function() {
								combox.setValue(combox
										.getValue());
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
					nodeKey : 'dblx',
					hiddenName : "procreditMortgage.assuretypeid",
					fieldLabel : "担保类型",
					itemName : '担保类型', // xx代表分类名称
					value : assuretypeidParams,
					listeners : {
						afterrender : function(combox) {
							var st = combox.getStore();
							st.on("load", function() {
								combox.setValue(combox
										.getValue());
								combox.clearInvalid();
							})
						}
					}
				},{
					xtype : "dickeycombo",
					nodeKey : 'bzfs',
					hiddenName : "procreditMortgage.assuremodeid",
					fieldLabel : "保证方式",
					itemName : '保证方式', // xx代表分类名称
					listeners : {
						afterrender : function(combox) {
							var st = combox.getStore();
							st.on("load", function() {
								combox.setValue(combox
										.getValue());
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
							selectSlCompanyMain(selectSlCompanyCom);
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
					triggerAction : 'all',
					listeners : {
						'select' : function(combo,record,index){
							var address;
							var cciaa;
							var legalpersonid;
							var legalperson;
							if(businessType=='Financing'){
								address = record.get('messageAddress');
								cciaa = record.get('businessCode');
								legalpersonid = record.get('personMainId');
								legalperson = record.get('lawName');
							}else{
								address = record.get('address');
								cciaa = record.get('cciaa');
								legalpersonid = record.get('legalpersonid');
								legalperson = record.get('legalperson');
							}
							setCustomerName(address,cciaa,legalpersonid,legalperson);
						}
					}
				}]
			},{
				columnWidth : 1,
				labelWidth : 105,
				layout : 'form',
				defaults : {anchor : '97.5%'},
				items : [{
					id : 'mortgageName',
					xtype : 'textfield',
					fieldLabel : '抵质押物名称',
					name : 'procreditMortgage.mortgagename',
					maxLength : 50,
					maxLengthText : '最大输入长度50',
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
					maxLength : 23,
					maxLengthText : '最大输入长度23',
					name : 'procreditMortgage.finalprice'
				},{
					fieldLabel : '可担保额度.万元',
					width : 90,
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
					maxLengthText : '最大输入长度50'
				}]
			},{
				columnWidth : 1,
				layout : 'form',
				labelWidth : 105,
				defaults : {anchor : '97.5%'},
				items : [{
					xtype : 'textarea',
					fieldLabel : '备注',
					maxLength : 100,
					maxLengthText : '最大输入长度100',
					name : 'procreditMortgage.remarks'
				}]
			}]
		},{
			layout : 'column',
			xtype:'fieldset',
            title: '填写<无限连带责任-公司>详细信息',
            collapsible: true,
            autoHeight:true,
            anchor : '95%',
            items : [{
				columnWidth : 1,
				labelWidth : 100,
				layout : 'form',
				defaults : {anchor : '97.5%'},
				items : [{
					id : 'address_en',
					xtype : 'textfield',
					fieldLabel : '注册地址',
					maxLength : 50,
					maxLengthText : '最大输入长度50',
					name : 'enterprise.address'
				}]
            },{
            	columnWidth : .5,
				layout : 'form',
				labelWidth : 100,
				defaults : {xtype : 'numberfield',anchor : '100%'},
				items : [{
					id : 'cciaa_en',
					xtype : 'textfield',
					fieldLabel : '营业执照号码',
					maxLength : 50,
					maxLengthText : '最大输入长度50',
					name : 'enterprise.cciaa'
				},{
					fieldLabel : '净资产.万元',
					maxLength : 23,
					maxLengthText : '最大输入长度23',
					name : 'procreditMortgageEnterprise.netassets'
				},{
					fieldLabel : '模型估价.万元',
					maxLength : 23,
					maxLengthText : '最大输入长度23',
					name : 'procreditMortgageEnterprise.modelrangeprice'
				}]
            },{
            	columnWidth : .5,
				layout : 'form',
				labelWidth : 110,
				defaults : {xtype : 'textfield',anchor : '95%'},
				items : [{
					id : 'legalperson_en',
					fieldLabel : '法人代表',
					maxLength : 50,
					maxLengthText : '最大输入长度50',
					xtype : "combo",
					triggerClass : 'x-form-search-trigger',
					onTriggerClick : function(e) {
						//selectPWName(selectLegalpersonCompany);
						if(businessType=='Financing'){
							selectSlPersonMain(selectSlPersonFaren);
						}else{
							selectPWName(selectLegalpersonCompany);
						}
					}
				},{
					id : 'phone_en',
					fieldLabel : '法人代表电话',
					maxLength : 50,
					maxLengthText : '最大输入长度50',
					name : 'person.cellphone'
				},{
					xtype : "dickeycombo",
					nodeKey : 'shgx',
					hiddenName : "procreditMortgageEnterprise.societynexusid",
					fieldLabel : "社会关系",
					itemName : '社会关系', // xx代表分类名称
					listeners : {
						afterrender : function(combox) {
							var st = combox.getStore();
							st.on("load", function() {
								combox.setValue(combox
										.getValue());
								combox.clearInvalid();
							})
						}
					}
				},{
					id : 'legalpersonid_en',
	            	name : 'legalpersonid',
	            	xtype : 'hidden'
				}]
            }]
		}],
		tbar : topBar
	});
	
	var window_addCompany = new Ext.Window({
		id : 'win_addCompany',
		title :'新增无限连带责任-公司信息',
		iconCls : 'btn-add',
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
		items : [panel_addCompany],
		listeners : {
			'beforeclose' : function(){
				if(panel_addCompany.getForm().isDirty()){
					Ext.Msg.confirm('操作提示','是否保存当前新添加的数据?',function(btn){
						if(btn=='yes'){
							panel_addCompany.getTopToolbar().items.itemAt(0).handler.call() ;
						}else{
							panel_addCompany.getForm().reset() ;
							window_addCompany.destroy() ;
						}
					}) ;
					return false ;
				}
			}
		}
	});
	window_addCompany.show();
	
	function setCustomerName(address,cciaa,legalpersonid,legalperson){
		var phone;
		Ext.getCmp('address_en').setValue(address);//注册地址
		Ext.getCmp('cciaa_en').setValue(cciaa);//营业执照号码
		Ext.getCmp('legalperson_en').setValue(legalpersonid);//法人代表id
		Ext.getCmp('legalperson_en').setRawValue(legalperson);//法人代表name
		Ext.getCmp('legalpersonid_en').setValue(legalpersonid);
		
		Ext.Ajax.request({
			url : __ctxPath +'/creditFlow/customer/person/seeInfoPerson.do',
			method : 'POST',
			success : function(response,request) {
				var objPersonCompany = Ext.util.JSON.decode(response.responseText);
				var personObjectCompany = objPersonCompany.data;
				if(businessType=='Financing'){
					if(personObjectCompany.linktel != null || personObjectCompany.linktel != ""){
						phone = personObjectCompany.linktel;
					}else{
						phone = "";
					}
				}else{
					if(personObjectCompany.cellphone != null || personObjectCompany.cellphone != ""){
						phone = personObjectCompany.cellphone;
					}else{
						phone = "";
					}
				}
				Ext.getCmp('phone_en').setValue(phone);//法人代表电话
			},
			failure : function(response) {					
					Ext.Msg.alert('状态','操作失败，请重试');		
			},
			params: { id: legalpersonid,businessType: businessType }
		});
		/*if(businessType=='Financing'){
			phone = tel;
			alert("tel=="+phone);
			Ext.getCmp('phone_en').setValue(phone);//法人代表电话
		}else{
			
		}*/
	};
	
	function selectCustomer(obj) {
		Ext.getCmp('enterpriseNameMortgage').setValue(obj.id);
		Ext.getCmp('enterpriseNameMortgage').setRawValue(obj.enterprisename) ;
		setCustomerName(obj.address,obj.cciaa,obj.legalpersonid,obj.legalperson);
	}
	
	function selectSlCompanyCom(obj) {
		Ext.getCmp('enterpriseNameMortgage').setValue(obj.companyMainId);
		Ext.getCmp('enterpriseNameMortgage').setRawValue(obj.corName) ;
		setCustomerName(obj.messageAddress,obj.businessCode,obj.personMainId,obj.lawName);
	}
	
	//法人代表
	var selectLegalpersonCompany = function(obj){
		Ext.getCmp('legalperson_en').setValue(obj.id) ;
		Ext.getCmp('legalperson_en').setRawValue(obj.name);
		Ext.getCmp('phone_en').setValue(obj.cellphone);//法人代表电话
		Ext.getCmp('legalpersonid_en').setValue(obj.id);
	}
	
	var selectSlPersonFaren = function(obj){
		Ext.getCmp('legalperson_en').setValue(obj.personMainId) ;
		Ext.getCmp('legalperson_en').setRawValue(obj.name);
		Ext.getCmp('phone_en').setValue(obj.linktel);//法人代表电话
		Ext.getCmp('legalpersonid_en').setValue(obj.personMainId);
	}
}