AddBaozMortgageWin = Ext.extend(Ext.Window, {
	layout : 'anchor',
	anchor : '100%',
	constructor : function(_cfg) {

		Ext.applyIf(this, _cfg);
		this.initUIComponents();
		AddBaozMortgageWin.superclass.constructor.call(this, {
			        buttonAlign:'center',
			        title:'新增保证担保',
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
					maximizable :true,
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
					
				})
	},
	initUIComponents : function() {
		var businessType=this.businessType
		function selectCustomer(obj) {
			
			if(Ext.getCmp('baozpersontype_id').getValue()==602){
				var setCustomerName=function setCustomerName(address,cciaa,legalpersonid,legalperson,tel){
					var phone;
					Ext.getCmp('address_en').setValue(address);//注册地址
					Ext.getCmp('cciaa_en').setValue(cciaa);//营业执照号码
					Ext.getCmp('legalperson_en').setValue(legalpersonid);//法人代表id
					Ext.getCmp('legalperson_en').setRawValue(legalperson);//法人代表name
					Ext.getCmp('legalpersonid_en').setValue(legalpersonid);
					Ext.getCmp('telephone').setValue(tel)
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
				}
				if(businessType=='Financing'){
				    Ext.getCmp('baozr').setValue(obj.companyMainId);
					Ext.getCmp('baozr').setRawValue(obj.corName) ;
					Ext.getCmp('mortgagename').setValue(obj.corName);
					setCustomerName(obj.sjjyAddress,obj.businessCode,obj.personMainId,obj.lawName,obj.tel);
				}else{
					Ext.getCmp('baozr').setValue(obj.id);
					Ext.getCmp('baozr').setRawValue(obj.enterprisename) ;
					Ext.getCmp('mortgagename').setValue(obj.enterprisename);
					setCustomerName(obj.area,obj.cciaa,obj.legalpersonid,obj.legalperson,obj.telephone);
				}
			}else if(Ext.getCmp('baozpersontype_id').getValue()==603){
				function setCustomerName(fullName,zhusuo,cardnumber,cellphone,ispublicservant,cardtype){		
					Ext.getCmp('person_ispublicservant').setValue(ispublicservant);//是否为公务员
					Ext.getCmp('mor_isCivilServant').setRawValue(ispublicservant);
					Ext.getCmp('card_number').setValue(cardnumber);//证件号码
					Ext.getCmp('person_phone').setValue(cellphone);//联系电话
					Ext.getCmp('person_address').setValue(zhusuo);//家庭住址
					Ext.getCmp('cardtype').setValue(cardtype);
				};
				if(businessType=='Financing'){
					Ext.getCmp('baozr').setValue(obj.personMainId);
					Ext.getCmp('baozr').setRawValue(obj.name) ;
					Ext.getCmp('mortgagename').setValue(obj.name);
					setCustomerName(obj.name,obj.home,obj.cardnum,obj.linktel,false,obj.cardtype);
				}else{
					Ext.getCmp('baozr').setValue(obj.id);
					Ext.getCmp('baozr').setRawValue(obj.name) ;
					Ext.getCmp('mortgagename').setValue(obj.name);
					setCustomerName(obj.name,obj.familyaddress,obj.cardnumber,obj.cellphone,obj.ispublicservant,obj.cardtype);
				}
			}
			
		}
		this.formPanel = new Ext.FormPanel( {
			url : __ctxPath +'/credit/mortgage/addMortgageOfBZ.do',
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
	            title: '填写<保证担保>基础信息',
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
		        		name : 'procreditMortgage.projid',
		        		value : this.projectId
		        	},{
		        		xtype : 'hidden',
		        		name : 'procreditMortgage.businessType',
		        		value : this.businessType
		        	},{
		        	    xtype :'hidden',
		        	    name:'procreditMortgage.assuretypeid',
		        	    value:'606'
		        	},{
		        		id:'mortgagename',
		        	    xtype :'hidden',
		        	    name:'procreditMortgage.mortgagename'
		        	},{
						id : 'baozpersontype_id',
						xtype : "dickeycombo",
						nodeKey : 'syrlx',
						hiddenName : "procreditMortgage.personType",
						fieldLabel : "保证人类型",
						itemName : '保证人类型', // xx代表分类名称
						allowBlank :false,
						listeners : {
							scope:this,
							afterrender : function(combox) {
								var st = combox.getStore();
								st.on("load", function() {
									combox.setValue(combox
											.getValue());
									combox.clearInvalid();
								})
							},
							'select' : function(combo,record, index){
								Ext.getCmp('baozr').setValue('')
								this.formPanel.remove(this.formPanel.items.last());
								if(combo.getValue()==602){
									this.getCmpByName('procreditMortgage.mortgagenametypeid').setValue('3');
									this.getCmpByName('procreditMortgage.mortgagepersontypeforvalue').setValue('无限连带责任-公司');
									this.formPanel.add(new CompanyForm({businessType:this.businessType}))
									this.formPanel.doLayout()
								}else if(combo.getValue()==603){
									this.getCmpByName('procreditMortgage.mortgagenametypeid').setValue('4');
									this.getCmpByName('procreditMortgage.mortgagepersontypeforvalue').setValue('无限连带责任-个人');
									this.formPanel.add(new DutypersonForm({businessType:this.businessType}))
									this.formPanel.doLayout()
								}
							}
						}
					},{
					    xtype:'hidden',
				   	    name:'procreditMortgage.mortgagenametypeid'
					},{
						xtype:'hidden',
					    name:'procreditMortgage.mortgagepersontypeforvalue'
					}]
				},{
					columnWidth : .5,
					labelWidth : 110,
					layout : 'form',
					defaults : {anchor : '95%'},
					items : [{
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
						id : 'baozr',
						xtype : 'combo',
						triggerClass :'x-form-search-trigger',
						hiddenName : 'customerEnterpriseName',
						fieldLabel : '<font color=red>*</font>保证人',
						editable:false,
						onTriggerClick : function(){
							if(businessType=='Financing'){
								if(Ext.getCmp('baozpersontype_id').getValue()==602){
									selectSlCompanyMain(selectCustomer);
								}else if(Ext.getCmp('baozpersontype_id').getValue()==603){
									selectSlPersonMain(selectCustomer)
								}
							}else{
								if(Ext.getCmp('baozpersontype_id').getValue()==602){
									selectEnterprise(selectCustomer);
								}else if(Ext.getCmp('baozpersontype_id').getValue()==603){
									selectPWName(selectCustomer);
								}
								
							}
	                   },
						mode : 'romote',
						lazyInit : true,
						allowBlank : false,
						typeAhead : true,
						forceSelection :true,
						minChars : 1,
						listWidth : 230,
						store: new Ext.data.JsonStore({}),
						/*store : getStoreByBusinessType(this.businessType,'enterprise'),
						displayField : businessType=="Financing"?'corName':'enterprisename',
						valueField : businessType=="Financing"?'companyMainId':'id',*/
						triggerAction : 'all'
					}]
				}/*,{
					columnWidth : 1,
					labelWidth : 105,
					layout : 'form',
					defaults : {anchor : '97.5%'},
					items : [{
					xtype:'textfield',
					fieldLabel : '与债务人的关系',
					name : 'procreditMortgage.relation',
					maxLength : 50,
					maxLengthText : '最大输入长度50'
				}]
				}*/,{
					columnWidth : 1,
					layout : 'form',
					labelWidth : 105,
					defaults : {anchor : '97.5%'},
					items : [{
						xtype : 'textarea',
						fieldLabel : '备注',
						maxLength : 100,
						maxLengthText : '最大输入长度100',
						name : 'procreditMortgage.mortgageRemarks'
					}]
				}]
			},{
			   anchor:'95%',
			   name:'otherPanel',
			   items:[new CompanyForm({businessType:this.businessType})]
			}]
		})

	},
	save:function(){
	  
	    var win=this
	    var gridPanel=this.gridPanel
	    if(this.formPanel.getForm().isValid()){
			this.formPanel.getForm().submit({
				method : 'POST',
				waitTitle : '连接',
				waitMsg : '消息发送中...',
				success : function(form, action) {
					Ext.ux.Toast.msg('操作信息', '保存成功!');
					win.destroy();
					gridPanel.getStore().reload()
				},
				failure : function(form, action) {
					Ext.ux.Toast.msg('操作信息', '保存失败!');
				}
			});				
	    }
	}
});
