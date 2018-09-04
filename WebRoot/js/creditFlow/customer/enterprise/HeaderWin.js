HeaderWin = Ext.extend(Ext.Window, {
	layout : 'anchor',
	anchor : '100%',
	constructor : function(_cfg) {

		Ext.applyIf(this, _cfg);
		this.initUIComponents();
		HeaderWin.superclass.constructor.call(this, {
					id:'HeaderWin',
			        buttonAlign:'center',
			        title:'负责人信息',
			        iconCls : 'btn-add',
					height : 500,
					width : 1000,
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
					items : [this.formPanel],
					buttons:[{
					    text : '保存',
						iconCls : 'btn-save',
						scope : this,
						hidden : this.isReadOnly,
						handler : this.save
					},{
					    text : '关闭',
						iconCls : 'close',
						scope : this,
						handler : function(){
							this.close();
						}
					}]
					
				})
	},
	initUIComponents : function() {   
		this.PersonHouseView =new PersonHouseInfo({
			personId:this.personId,
			isHiddenAddBtn:this.isReadOnly,
			isHiddenDelBtn:this.isReadOnly,
			isHiddenSeeBtn:false,
			isReadOnly:this.isReadOnly
		})
		
		this.PersonCarView =new PersonCarInfo({
			personId:this.personId,
			isHiddenAddBtn:this.isReadOnly,
			isHiddenDelBtn:this.isReadOnly,
			isHiddenSeeBtn:false,
			isReadOnly:this.isReadOnly
		})
		this.formPanel = new Ext.FormPanel( {
			url :  __ctxPath + '/creditFlow/customer/enterprise/addHeaderEnterprise.do',
			monitorValid : true,
			bodyStyle:'padding:10px',
			autoScroll : true ,
			labelAlign : 'right',
			buttonAlign : 'center',
			frame : true ,
			border : false,
			layout : 'form',
			labelWidth:75,
			items : [{
					xtype : 'fieldset',
					title : '资产信息',
					collapsible : true,
					autoHeight : true,
					layout : 'column',
					bodyStyle : 'padding-left: 0px',
					items : [{
					columnWidth : .5,
					layout : 'form',
					//labelWidth : 70,
					defaults : {
						anchor : anchor
					},
					items : [{
						xtype : 'combo',
						fieldLabel : '负责人姓名',
						allowBlank : false,
						triggerClass : 'x-form-search-trigger',
						hiddenName : 'person.name',
						readOnly:this.isReadOnly,
						scope : this,
						onTriggerClick : function(com) {
							var panel_add=this.ownerCt.ownerCt;
						   var amselectLegalperson1 = function(obj) {
						   	    panel_add.getCmpByName('person.selfemail').setValue(obj.selfemail);
								panel_add.getCmpByName('person.name').setValue(obj.name);
								panel_add.getCmpByName('person.cellphone').setValue(obj.cellphone);
								panel_add.getCmpByName('person.cardtype').setValue(obj.cardtype);
								panel_add.getCmpByName('person.sex').setValue(obj.sex);
								panel_add.getCmpByName('person.cardnumber').setValue(obj.cardnumber);
								panel_add.getCmpByName('person.id').setValue(obj.id);
								panel_add.getCmpByName('person.grossasset').setValue(obj.grossasset);
								panel_add.getCmpByName('grossasset').setValue(Ext.util.Format.number(obj.grossasset,'0,000.00'));
								var personHouseStore= panel_add.ownerCt.ownerCt.PersonHouseView.getCmpByName("grid_personHouseInfo").getStore();
								personHouseStore.proxy.setUrl(__ctxPath+ '/creditFlow/customer/person/listCsPersonHouse.do?personId='+ obj.id);
								personHouseStore.reload();
								panel_add.ownerCt.ownerCt.PersonHouseView.getCmpByName("grid_personHouseInfo").getView().refresh();
								var personCarStore= panel_add.ownerCt.ownerCt.PersonCarView.getCmpByName("grid_PersonCarInfo").getStore();
								personCarStore.proxy.setUrl(__ctxPath+ '/creditFlow/customer/person/listCsPersonCar.do?personId='+ obj.id);
								personCarStore.reload();
							}
							selectPWName(amselectLegalperson1);
						},
						resizable : true,
						mode : 'romote',
						editable : true,
						lazyInit : false,
						typeAhead : true,
						minChars : 1,
						anchor : '100%',
						store : new Ext.data.JsonStore({
							url : getRootPath()	+ '/creditFlow/customer/person/ajaxQueryForComboPerson.do?isAll='+isGranted('_detail_sygrkh'),
							root : 'topics',
							autoLoad : true,
							fields : [{
										name : 'id'
									}, {
										name : 'name'
									}],
							listeners : {
								'load' : function(s, r, o) {
									if (s.getCount() == 0) {
										Ext.getCmp('amlegalpersonName')
												.markInvalid('没有查找到匹配的记录');
									}
								}
							}
						}),
						displayField : 'name',
						valueField : 'id',
						triggerAction : 'all',
						listeners : {
							'select' : function(combo, record, index) {
								Ext.getCmp('amlegalpersonId').setValue(record.get('id'));
								Ext.getCmp('amlegalpersonName').setValue(record.get('name'));
										
							},
							'blur' : function(f) {
								if (f.getValue() != null
										&& f.getValue() != '') {
									Ext.getCmp('amlegalpersonId')
											.setValue(f.getValue());
								}
							},
						   'afterrender':function(com){
									    com.clearInvalid();
									}
								
						}
					}, {
						name : 'person.id',
						xtype : 'hidden'
					},{
						name : 'person.companyId',
						xtype : 'hidden'
					}]
				}, {
					columnWidth : 0.5, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					items : [{
						fieldLabel : "性别",
						xtype : "diccombo",
						hiddenName : 'person.sex',
						displayField : 'itemName',
						readOnly:this.isReadOnly,
						itemName : '性别', // xx代表分类名称
						allowBlank : false,
						emptyText : "请选择",
						editable : false,
						blankText : "性别不能为空，请正确填写!",
						anchor : "100%",
						listeners : {
							afterrender : function(combox) {
								var st = combox.getStore();
								st.on("load", function() {
											combox
													.setValue(combox
															.getValue());
											combox
													.clearInvalid();
										})
							}
						}
					}]
				},{
					columnWidth : 0.5, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					items : [{
						xtype : "diccombo",
						hiddenName : "person.cardtype",
						fieldLabel : "证件类型",
						readOnly:this.isReadOnly,
						itemName : '证件类型', // xx代表分类名称
						allowBlank : false,
						editable : false,
						//emptyText : "请选择",
						blankText : "证件类型不能为空，请正确填写!",
						anchor : "100%",
						listeners : {
							scope : this,
							afterrender : function(combox) {
								var st = combox.getStore();
								st.on("load", function() {
									if(combox.getValue()=='' || combox.getValue()==null){												
										combox.setValue(st.getAt(0).data.itemId);
										combox.clearInvalid();
									}else{
										combox.setValue(combox.getValue());
										combox.clearInvalid();
									}
								})
							}
						}
					}]
				}, {
					columnWidth : 0.5, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					items : [{
								xtype : "textfield",
								name : "person.cardnumber",
								allowBlank : false,
								fieldLabel : "证件号码",
								readOnly:this.isReadOnly,
								blankText : "证件号码不能为空，请正确填写!",
								anchor : "100%"	,
								listeners : {
									scope:this,
									'blur':function(com){
										if(this.getCmpByName('person.cardtype').getValue()==309){
											if(validateIdCard(com.getValue())==1){
												Ext.Msg.alert('身份证号码验证','证件号码不正确,请仔细核对')
												return;
											}else if(validateIdCard(com.getValue())==2){
												Ext.Msg.alert('身份证号码验证','证件号码地区不正确,请仔细核对')
												return;
											}else if(validateIdCard(com.getValue())==3){
												Ext.Msg.alert('身份证号码验证','证件号码生日日期不正确,请仔细核对')
												return;
											}
										}
									}
								}
							}]
				},{
					columnWidth : 0.5, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					items : [{
								xtype : "textfield",
								name : "person.cellphone",
								readOnly:this.isReadOnly,
								fieldLabel : "手机号码",
								anchor : "100%",
								regex : /^[1][34578][0-9]{9}$/,
								regexText : '手机号码格式不正确'
								/*listeners : {
									'afterrender':function(com){
									    com.clearInvalid();
									}
								}*/
							}]
				}, {
					columnWidth : 0.5, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					items : [{
						xtype : "textfield",
						name : "person.selfemail",
						readOnly:this.isReadOnly,
						fieldLabel : "电子邮箱",
						anchor : "100%",
						regex : /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+/,
						regexText : '电子邮箱格式不正确',
						listeners : {
							afterrender : function(obj) {
	
							if (obj.getValue() == ""
									|| obj.getValue() == null) {
								Ext.apply(obj, {
											vtype : ""
										});
							}
						},
						'afterrender':function(com){
								    com.clearInvalid();
						},
						blur : function(obj) {
	
							if (obj.getValue() == ""
									|| obj.getValue() == null) {
								Ext.apply(obj, {
											vtype : ""
										});
							} else {
								Ext.apply(obj, {
											vtype : 'email'
										});
							}
						}
	
					}
				}]
			},{
					columnWidth : 0.5, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					items : [{
									xtype : "textfield",
									name : "grossasset",
									readOnly : true,
									fieldLabel : '资产总值',
									anchor : '100%'
													
								},{
									xtype : "hidden",
									name : "person.grossasset"
													
								}]
				},{
						columnWidth : .1,
						layout : 'form',
						
						items : [{
									fieldLabel : "<span style='margin-left:1px'>万元</span> ",
									labelSeparator : '',
									anchor : "100%"
								}]
					}]
				},{
					xtype : 'fieldset',
					title : '房产信息',
					name:"personHouseInfo",
					collapsible : true,
					autoHeight : true,
					bodyStyle : 'padding-left: 0px',
					items : [this.PersonHouseView]
				},{
					xtype : 'fieldset',
					title : '车辆信息',
					name:"personCarInfo",
					collapsible : true,
					autoHeight : true,
					bodyStyle : 'padding-left: 0px',
					items : [this.PersonCarView]
				}]
			/*,*/
		})
		if (this.enterpriseId != null && this.enterpriseId != 'undefined') {
			var panel=this;
			this.formPanel.loadData( {
				url : __ctxPath + '/creditFlow/customer/enterprise/getHeaderInfoEnterprise.do?enterpriseId=' + this.enterpriseId,
				root : 'data',
				preName : 'person',
				success : function(response, options) {
					//Ext.util.JSON.decode(response.responseText).data.grossasset
							var respText = response.responseText;
							var alarm_fields = Ext.util.JSON.decode(respText);
							panel.getCmpByName('grossasset').setValue(Ext.util.Format.number(alarm_fields.data.grossasset,'0,000.00'));
					}
			});
		}
	},
	save:function(){
			var win=this;
			if(this.formPanel.getCmpByName('person.cardtype').getValue()==309){
				if(validateIdCard(this.formPanel.getCmpByName('person.cardnumber').getValue())==1){
					Ext.Msg.alert('身份证号码验证','证件号码不正确,请仔细核对')
					return;
				}else if(validateIdCard(this.formPanel.getCmpByName('person.cardnumber').getValue())==2){
					Ext.Msg.alert('身份证号码验证','证件号码地区不正确,请仔细核对')
					return;
				}else if(validateIdCard(this.formPanel.getCmpByName('person.cardnumber').getValue())==3){
					Ext.Msg.alert('身份证号码验证','证件号码生日日期不正确,请仔细核对')
					return;
				}
			}
			var personId=this.formPanel.getCmpByName("person.id").getValue();
			var personCarInfos=this.PersonCarView;
			var personcarDate=getPersonFinanceInfoData(personCarInfos);
			var personHouseInfos=this.PersonHouseView;
			var personHouseDate=getPersonFinanceInfoData(personHouseInfos);
			this.formPanel.getForm().submit({
				method : 'POST',
				waitTitle : '连接',
				waitMsg : '消息发送中...',
				params : {enterpriseId:this.enterpriseId,'personId':personId,
							'personCarInfo':personcarDate,
							'personHouseInfo':personHouseDate},
				success : function(form, action) {
					Ext.ux.Toast.msg('操作信息', '保存成功!');
					win.destroy();
				},
				failure : function(form, action) {
					Ext.ux.Toast.msg('操作信息', '保存失败!');
				}
			});				
		}		    
	
});
