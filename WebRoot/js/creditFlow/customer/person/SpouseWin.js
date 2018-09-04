SpouseWin = Ext.extend(Ext.Window, {
	layout : 'anchor',
	anchor : '100%',
	constructor : function(_cfg) {

		Ext.applyIf(this, _cfg);
		this.initUIComponents();
		SpouseWin.superclass.constructor.call(this, {
					id:'SpouseWin',
			        buttonAlign:'center',
			        title:'配偶信息',
			        iconCls : 'btn-add',
					width : 600,
					height : 250,
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
						handler : this.save
					},{
					    text : '关闭',
						iconCls : 'close',
						scope : this,
						handler : function(){
							this.close();
						}
					}]
					
				});
	},
	initUIComponents : function() {

		this.formPanel = new Ext.FormPanel( {
			url :  __ctxPath + '/creditFlow/customer/person/addSpouse.do',
			monitorValid : true,
			bodyStyle:'padding:10px',
			autoScroll : true ,
			labelAlign : 'right',
			buttonAlign : 'center',
			frame : true ,
			border : false,
			layout : 'column',
			labelWidth:60,
			items : [{
				columnWidth:.33,
				layout : 'form',
				defaults : {
					anchor : '100%'
				},
				items:[{
					xtype:'hidden',
					name : 'spouse.personId',
					value : this.personId
				},{
					xtype:'hidden',
					name : 'spouse.spouseId'
				},{
					xtype : 'textfield',
					fieldLabel : '配偶姓名',
					allowBlank : false,
					readOnly : this.isReadOnly,
					name : 'spouse.name'
				},{					
					xtype : "dickeycombo",
					nodeKey : 'dgree',
					fieldLabel : '学历',
					hiddenName : 'spouse.dgree',
					//emptyText : '请选择学历',
					width : 80,
					editable : false,
					readOnly : this.isReadOnly,
					listeners : {
						afterrender : function(combox) {
							var st = combox.getStore();
							st.on("load", function() {
								if (combox.getValue() == 0
										|| combox.getValue() == 1
										|| combox.getValue() == ""
										|| combox.getValue() == null) {
									combox.setValue("");
								} else {
									combox.setValue(combox
											.getValue());
								}
								combox.clearInvalid();
							});
						}
					}
							
				},{
					xtype : 'textfield',
					fieldLabel : '联系电话',
					readOnly : this.isReadOnly,
					name : 'spouse.linkTel',
					regex : /((\d{11})|^((\d{7,8})|(\d{4}|\d{3})-(\d{7,8})|(\d{4}|\d{3})-(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1})|(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1}))$)/,
					regexText : '联系电话格式不正确'	
				}]
			},{
				columnWidth : .33,
				layout : 'form',
				defaults : {
					anchor : '100%'
				},
				items : [{				
					xtype : "dickeycombo",
					nodeKey : 'card_type_key',
					hiddenName : "spouse.cardtype",
					itemName : '证件类型', // xx代表分类名称
					fieldLabel : "证件类型",
					allowBlank : false,
					editable : false,
					readOnly : this.isReadOnly,
					blankText : "证件类型不能为空，请正确填写!",
					listeners : {
						scope : this,
						afterrender : function(combox) {
							var st = combox.getStore();
							st.on("load", function() {
								if(combox.getValue=='' || combox.getValue()==null){												
									combox.setValue(st.getAt(0).data.itemId);
									combox.clearInvalid();
								}else{
									combox.setValue(combox.getValue());
									combox.clearInvalid();
								}
							});
						}
					}									
				},{				
					xtype : "dickeycombo",
					nodeKey : 'zhiwujob',
					fieldLabel : '职务',
					hiddenName : 'spouse.job',
					//emptyText : '请选择职务',
					width : 80,
					editable : false,
					readOnly : this.isReadOnly,
					value : personData == null
							? null
							: personData.job,
					listeners : {
						afterrender : function(combox) {
							var st = combox.getStore();
							st.on("load", function() {
								if (combox.getValue() == 0
										|| combox.getValue() == 1
										|| combox.getValue() == ""
										|| combox.getValue() == null) {
									combox.setValue("");
								} else {
									combox.setValue(combox
											.getValue());
								}
								combox.clearInvalid();
							});
						}
					}							
				}]
			},{
				columnWidth : 0.33,
				layout : 'form',
				defaults : {
					anchor : '100%'
				},
				items : [{
					xtype : 'textfield',
					fieldLabel : '证件号码',
					allowBlank : false,
					readOnly : this.isReadOnly,
					name : 'spouse.cardnumber',
					listeners : {
						scope:this,
						'blur':function(com){
							if(this.getCmpByName('spouse.cardtype').getValue()==309){
								if(validateIdCard(com.getValue())==1){
									Ext.Msg.alert('身份证号码验证','证件号码不正确,请仔细核对');
									return;
								}else if(validateIdCard(com.getValue())==2){
									Ext.Msg.alert('身份证号码验证','证件号码地区不正确,请仔细核对');
									return;
								}else if(validateIdCard(com.getValue())==3){
									Ext.Msg.alert('身份证号码验证','证件号码生日日期不正确,请仔细核对');
									return;
								}
							}
						}
					}
				},{				
					xtype : "dickeycombo",
					hiddenName : "spouse.politicalStatus",
					nodeKey : 'zzmm', // xx代表分类名称
					fieldLabel : "政治面貌",
					//emptyText : "请选择",
					readOnly : this.isReadOnly,
					listeners : {
						afterrender : function(combox) {
							var st = combox.getStore();
							st.on("load", function() {
								
								combox.setValue(combox.getValue());
								combox.clearInvalid();
							});
				       }
					}							
				}]
			},{
				columnWidth : .66,
				layout : 'form',
				defaults : {
					anchor : '100%'
				},
				items:[{
					xtype : 'textfield',
					fieldLabel : '工作单位',
					readOnly : this.isReadOnly,
					name : 'spouse.currentcompany'
				}]
			},{
				columnWidth : .33,
				layout : 'form',
				defaults : {
					anchor : '100%'
				},
				items:[{
					xtype : 'numberfield',
					fieldLabel : '税后收入',
					readOnly : this.isReadOnly,
					name : 'spouse.incomeAfterTax'
				}]
			},{
				columnWidth : .33,
				layout : 'form',
				defaults : {
					anchor : '100%'
				},
				items:[{
						xtype : "dickeycombo",
						nodeKey : 'unitproperties',
						fieldLabel : '单位性质',
						// emptyText : '请选择单位性质',
						hiddenName : 'spouse.unitProperty',
						readOnly : this.isReadOnly,
						listWidth : 120,
						width : 80,
						editable : true,
						listeners : {
							afterrender : function(combox) {
								var st = combox.getStore();
								st.on("load", function() {
											if (combox.getValue() == 0
													|| combox.getValue() == 1
													|| combox.getValue() == ""
													|| combox.getValue() == null) {
												combox.setValue("");
											} else {
												combox.setValue(combox
														.getValue());
											}
											combox.clearInvalid();
										});
							}
						}
					}]
			},{
				columnWidth : .33,
				layout : 'form',
				defaults : {
					anchor : '100%'
				},
				items:[{
					xtype : 'textfield',
					fieldLabel : '单位电话',
					readOnly : this.isReadOnly,
					name : 'spouse.unitPhoneNO'
				}]
			},{
				columnWidth : 1,
				layout : 'form',
				defaults : {
					anchor : '100%'
				},
				items:[{
					xtype : 'textfield',
					fieldLabel : '单位地址',
					readOnly : this.isReadOnly,
					name : 'spouse.unitAddress'
				}]
			}]
		});
		if (this.personId != null && this.personId != 'undefined') {
			this.formPanel.loadData( {
				url : __ctxPath + '/creditFlow/customer/person/getInfoSpouse.do?personId=' + this.personId,
				root : 'data',
				preName : 'spouse'
			});
		}
	},
	save:function(){
			var win=this;
			if(this.formPanel.getCmpByName('spouse.cardtype').getValue()==309){
				if(validateIdCard(this.formPanel.getCmpByName('spouse.cardnumber').getValue())==1){
					Ext.Msg.alert('身份证号码验证','证件号码不正确,请仔细核对');
					return;
				}else if(validateIdCard(this.formPanel.getCmpByName('spouse.cardnumber').getValue())==2){
					Ext.Msg.alert('身份证号码验证','证件号码地区不正确,请仔细核对');
					return;
				}else if(validateIdCard(this.formPanel.getCmpByName('spouse.cardnumber').getValue())==3){
					Ext.Msg.alert('身份证号码验证','证件号码生日日期不正确,请仔细核对');
					return;
				}
			}
			
			this.formPanel.getForm().submit({
				method : 'POST',
				waitTitle : '连接',
				waitMsg : '消息发送中...',
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
