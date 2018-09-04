var addMortgageDutyPerson = function(piKey,personTypeParams,assuretypeidParams,refreshMortgageGridStore,businessType){
	var anchor = '100%';
	var topBar = new Ext.Toolbar({
		border : false,
		items : [{
			text : '保存',
			iconCls : 'btn-save',
			formBind : true,
			handler : function() {
				var card_number = Ext.getCmp('card_number').getValue();
				var mortgageName = Ext.getCmp('mortgageName').getValue();
				var personNameMortgage = Ext.getCmp('personNameMortgage').getValue();
				
				if(mortgageName == ""){
					Ext.ux.Toast.msg('状态','请输入<抵质押物名称>后再保存!');
				}else if(personNameMortgage == ""){
					Ext.ux.Toast.msg('状态','请选择<所有权人>后再保存!');
				}else if(card_number == ""){
					Ext.ux.Toast.msg('状态','<证件号码>不能为空!');
				}else{
					panel_addDutyPerson.getForm().submit({
						method : 'POST',
						waitTitle : '连接',
						waitMsg : '消息发送中...',
						success : function(form, action) {
							Ext.ux.Toast.msg('操作信息', '保存成功!');
							Ext.getCmp('win_addDutyPerson').destroy();
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
	var panel_addDutyPerson = new Ext.form.FormPanel({
		id :'addDutyPerson',
		url : __ctxPath +'/credit/mortgage/addDutyperson.do',
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
					value : '无限连带责任-个人',
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
					id : 'personNameMortgage',
					xtype : 'combo',
					hiddenName : 'customerPersonName',
					triggerClass :'x-form-search-trigger',
					editable:false,
					onTriggerClick : function(){
						if(businessType=='Financing'){
							selectSlPersonMain(selectSlPersonPer);
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
            title: '填写<无限连带责任-个人>详细信息',
            collapsible: true,
            autoHeight:true,
            anchor : '95%',
            items : [{
            	columnWidth : .5,
				layout : 'form',
				labelWidth : 100,
				defaults : {xtype : 'textfield',anchor : '100%'},
				items : [{
						id : 'cardtype_id',
						xtype : "dickeycombo",
						nodeKey :'card_type_key',
						//hiddenName : "person.cardtype",
						itemName : '证件类型', // xx代表分类名称
						fieldLabel : "证件类型",
						allowBlank : false,
						editable : true,
						//emptyText : "请选择",
						blankText : "证件类型不能为空，请正确填写!",
						listeners : {
							'change' : function(field,newValue,oldValue){
								Ext.getCmp('cardtype_person').setValue(newValue);//证件类型id--传后台
							}
						}
						/*,
						listeners : {
							afterrender : function(combox) {
								var st = combox.getStore();
								st.on("load", function() {
									if(combox.getValue() == 0||combox.getValue()==1||combox.getValue()==""||combox.getValue()==null){
										combox.setValue("");
									}else{
										combox.setValue(combox
											.getValue());
									}
									combox.clearInvalid();
								})
							}
						}*/
					/*id : 'cardtype_id',
					xtype : 'csRemoteCombo',
					fieldLabel : '证件类型',
					width : 80,
					listWidth : 80,
					allowBlank : false,
					blankText : '证件类型为必填内容',
					dicId : cardtypeDicId,
					listeners : {
						'change' : function(field,newValue,oldValue){
							if(newValue == 628){
								Ext.getCmp('card_number').index = 628;
								Ext.getCmp('card_number').regex = /^\d{15}(\d{2}[A-Za-z0-9])?$/;
								Ext.getCmp('card_number').regexText = '身份证号码无效';
								Ext.getCmp('cardtype_person').setValue(628);//证件类型id--传后台
							}else if(newValue == 629){
								Ext.getCmp('card_number').index = 629;
								Ext.getCmp('card_number').regex  ;
								Ext.getCmp('card_number').regexText = '军官证号码无效';
								Ext.getCmp('cardtype_person').setValue(629);//证件类型id--传后台
							}else if(newValue == 630){
								Ext.getCmp('card_number').index = 630;
								Ext.getCmp('card_number').regex ;
								Ext.getCmp('card_number').regexText = '护照号码无效';
								Ext.getCmp('cardtype_person').setValue(630);//证件类型id--传后台
							}else {
								Ext.getCmp('card_number').index = 0;
								Ext.getCmp('cardtype_person').setValue(631);//证件类型id--传后台
							}
						}
					}*/
				},{
					id : 'card_number',
					xtype : 'textfield',
					fieldLabel : '证件号码',
					name : 'person.cardnumber',
					allowBlank : false,
					blankText : '证件号码为必填内容'
				},{
					xtype : 'numberfield',
					fieldLabel : '资产价值.万元',
					maxLength : 23,
					maxLengthText : '最大输入长度23',
					name : 'procreditMortgagePerson.assetvalue'
				}]
            },{
            	columnWidth : .5,
				layout : 'form',
				labelWidth : 110,
				defaults : {xtype : 'textfield',anchor : '95%'},
				items : [{
					id : 'person_phone',
					fieldLabel : '联系电话',
					maxLength : 50,
					maxLengthText : '最大输入长度50',
					name : 'person.cellphone'
				},{
					xtype : "dickeycombo",
					nodeKey : 'shgx',
					hiddenName : "procreditMortgagePerson.societynexusid",
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
					xtype : 'numberfield',
					fieldLabel : '模型估价.万元',
					maxLength : 23,
					maxLengthText : '最大输入长度23',
					name : 'procreditMortgagePerson.modelrangeprice'
				},{
					id : 'cardtype_person',
	            	name : 'cardtype',
	            	xtype : 'hidden'
				}]
            }]
		}],
		tbar : topBar
	});
	
	var window_addDutyPerson = new Ext.Window({
		id : 'win_addDutyPerson',
		title :'新增无限连带责任-个人信息',
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
		items : [panel_addDutyPerson],
		listeners : {
			'beforeclose' : function(){
				if(panel_addDutyPerson.getForm().isDirty()){
					Ext.Msg.confirm('操作提示','是否保存当前新添加的数据?',function(btn){
						if(btn=='yes'){
							panel_addDutyPerson.getTopToolbar().items.itemAt(0).handler.call() ;
						}else{
							panel_addDutyPerson.getForm().reset() ;
							window_addDutyPerson.destroy() ;
						}
					}) ;
					return false ;
				}
			}
		}
	});
	window_addDutyPerson.show();
	
	function setCustomerName(fullName,cardtype,cardtypevalue,cardnumber,cellphone){
		/*global_customerName = fullName;//客户全称
		//如果 个人没有简称的话，就是全称
		if(Ext.isEmpty(shortName)){
			_customerShortName = fullName;
		}else{
			_customerShortName = fullName;//客户简称
		}*/
		
		Ext.getCmp('cardtype_id').setValue(cardtype);//证件类型id
		Ext.getCmp('cardtype_id').setRawValue(cardtypevalue);//证件类型值
		Ext.getCmp('card_number').setValue(cardnumber);//证件号码
		Ext.getCmp('person_phone').setValue(cellphone);//联系电话
		Ext.getCmp('cardtype_person').setValue(cardtype);//证件类型id--传后台
	};
	
	function selectCustomer(obj) {
		Ext.getCmp('personNameMortgage').setValue(obj.id);
		Ext.getCmp('personNameMortgage').setRawValue(obj.name) ;
		setCustomerName(obj.name,obj.cardtype,obj.cardtypevalue,obj.cardnumber,obj.cellphone);
	}
	
	function selectSlPersonPer(obj){
		Ext.getCmp('personNameMortgage').setValue(obj.personMainId);
		Ext.getCmp('personNameMortgage').setRawValue(obj.name) ;
		setCustomerName(obj.name,obj.cardtype,obj.cardtypevalue,obj.cardnum,obj.linktel);
	}
}
