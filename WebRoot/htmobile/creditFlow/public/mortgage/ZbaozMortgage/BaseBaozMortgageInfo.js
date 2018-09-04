

Ext.define('htmobile.creditFlow.public.mortgage.ZbaozMortgage.BaseBaozMortgageInfo', {
    extend: 'Ext.form.FieldSet',
    name: 'BaseBaozMortgageInfo',
    constructor: function (config) {
    	config = config || {};
    	this.personId = config.personId,
    	this.MortgageData=config.MortgageData;
    	this.projectId = config.projectId;
    	this.readOnly = config.readOnly;
    	this.businessType = config.businessType;
    	this.type=config.type;
    	Ext.apply(config,{
    		style:'background-color:white;',
		    fullscreen: true,
		    title:"<span style='font-size:16px;'>保证担保</span>",
		    flex:1,
		    scrollable:{
		    	direction: 'vertical'
		    },
		    items : [{
						xtype : 'fieldset',
						flex : 1,
						title : '保证担保基础信息',
						defaults : {
							xtype : 'textfield',
							width : "100%",
							labelAlign : "left"
						},
						items : [{
									xtype : "hiddenfield",
									name : 'id'
								}, {
									id : 'personid',
									name : 'person.id',
									xtype : 'hiddenfield'
								}, {
									id : 'enterpriseid',
									name : 'enterprise.id',
									xtype : 'hiddenfield'
								}, {
									id : 'enterprisecellphone',
									name : 'enterprise.cellphone',
									xtype : 'hiddenfield'
								}, {
									id : 'enterpriselegalpersonid',
									name : 'enterprise.legalpersonid',
									xtype : 'hiddenfield'
								}, {
									id : 'enterpriselegalperson',
									name : 'enterprise.legalperson',
									xtype : 'hiddenfield'
								}, {
									id : 'enterprisecciaa',
									name : 'enterprise.cciaa',
									xtype : 'hiddenfield'
								}, {
									id : 'enterprisetelephone',
									name : 'enterprise.telephone',
									xtype : 'hiddenfield'
								}, {
									id : 'enterprisearea',
									name : 'enterprise.area',
									xtype : 'hiddenfield'
								}, {
									id : 'personcardnumber',
									name : 'person.cardnumber',
									xtype : 'hiddenfield'
								}, {
									id : 'personcellphone',
									name : 'person.cellphone',
									xtype : 'hiddenfield'
								}, {
									id : 'enterpriseenterprisename',
									name : 'enterprise.enterprisename',
									xtype : 'hiddenfield'
								}, {
									id : 'personcardtype',
									name : 'person.cardtype',
									xtype : 'hiddenfield'
								}, {
									id : 'personname',
									name : 'person.name',
									xtype : 'hiddenfield'
								}, {
									id : 'procreditMortgagebusinessType',
									name : 'procreditMortgage.businessType',
									value : this.businessType,
									xtype : 'hiddenfield'
								}, {
									label : '保证人类型',
									nodeKey : 'syrlx',
									xtype : "dickeycombo",
									name : 'procreditMortgage.personType'
								}, {
									label : '保证方式',
									xtype : "dickeycombo",
									nodeKey : 'bzfs',
									name : 'procreditMortgage.assuremodeid'
								}, {
									xtype : 'textfield',
									label : "保证人",
									name : 'customerEnterpriseName',
									id : "customerEnterpriseName",
									value : this.customerEnterpriseName,
									listeners : {
										scope : this,
										'focus' : function(f) {
											if (this
													.getCmpByName('procreditMortgage.personType')
													.getValue() == 603) {
												mobileNavi
														.push(Ext
																.create(
																		'htmobile.public.SelectPersonlist',
																		{
																			callback : function(
																					data) {
																				var personname = Ext
																						.getCmp("personname");
																				var cardnumber = Ext
																						.getCmp("personcardnumber");
																				var cellphone = Ext
																						.getCmp("personcellphone");
																				var cardtype = Ext
																						.getCmp("personcardtype");
																				var personId = Ext
																						.getCmp("personid");
																				var customerEnterpriseName = Ext
																						.getCmp("customerEnterpriseName");
																				personname
																						.setValue(data.name);
																				cardnumber
																						.setValue(data.cardnumber);
																				cellphone
																						.setValue(data.cellphone);
																				cardtype
																						.setValue(data.cardtype);
																				personId
																						.setValue(data.id);
																				customerEnterpriseName
																						.setValue(data.name);
																			}
																		}));
											} else {
												mobileNavi
														.push(Ext
																.create(
																		'htmobile.public.SelectEnterpriselist',
																		{
																			callback : function(
																					data) {
																				var enterpriseenterprisename = Ext
																						.getCmp("enterpriseenterprisename");
																				var enterpriseId = Ext
																						.getCmp("enterpriseid");
																				var cellphone = Ext
																						.getCmp("enterprisecellphone");
																				var legalpersonid = Ext
																						.getCmp("enterpriselegalpersonid");
																				var legalperson = Ext
																						.getCmp("enterpriselegalperson");
																				var area = Ext
																						.getCmp("enterprisearea");
																				var cciaa = Ext
																						.getCmp("enterprisecciaa");
																				var telephone = Ext
																						.getCmp("enterprisetelephone");
																				var customerEnterpriseName = Ext
																						.getCmp("customerEnterpriseName");
																				customerEnterpriseName
																						.setValue(data.enterprisename);
																				enterpriseId
																						.setValue(data.id);
																				cellphone
																						.setValue(data.cellphone);
																				legalpersonid
																						.setValue(data.legalpersonid);
																				legalperson
																						.setValue(data.legalperson);
																				area
																						.setValue(data.area);
																				cciaa
																						.setValue(data.cciaa);
																				telephone
																						.setValue(data.telephone);
																				customerEnterpriseName
																						.setValue(data.enterprisename);
																			}
																		}));
											}
										}
									}

								}, {
									label : '备注',
									xtype : 'textareafield',
									name : 'procreditMortgage.mortgageRemarks'
								}, {
									label : '所有权人与借款人的关系',
									xtype : 'textareafield',
									name : 'procreditMortgage.relation'
								}]
							}, {
								xtype : 'button',
								cls : 'submit-button',
								text : "下一步",
								scope : this,
								handler : this.nextbtn
							}]
    	});
    	this.callParent([config]);
    },
	 nextbtn:function(){
	 	 var loginForm = this;
	 	 if(this.getCmpByName('procreditMortgage.personType').getValue()==603){
	     var personType=loginForm.getCmpByName('procreditMortgage.personType').getValue();
	     if(Ext.isEmpty(personType)){
		    Ext.Msg.alert('','保证人类型不能为空');
			return;
		  }
	     var customerEnterpriseName=loginForm.getCmpByName('customerEnterpriseName').getValue();
	      if(Ext.isEmpty(customerEnterpriseName)){
		    Ext.Msg.alert('','保证人不能为空');
			return;
		  }
	     var mortgageRemarks=loginForm.getCmpByName('procreditMortgage.mortgageRemarks').getValue();
	     var assuremodeid=loginForm.getCmpByName('procreditMortgage.assuremodeid').getValue();
	     var relation=loginForm.getCmpByName('procreditMortgage.relation').getValue();
	     var cardnumber=loginForm.getCmpByName('person.cardnumber').getValue();
	     var cellphone=loginForm.getCmpByName('person.cellphone').getValue();
	     var cardtype=loginForm.getCmpByName('person.cardtype').getValue();
	     var personId=loginForm.getCmpByName('person.id').getValue();
	     var businessType=loginForm.getCmpByName('procreditMortgage.businessType').getValue();
	     /*if(Ext.isEmpty(assuretypeid)){
			  note.setHtml("<div style='margin:10px;'><font>不能为空</font></div>");
			   return ;
			}*/
		//车辆	
		 
	     mobileNavi.push(Ext.create('htmobile.creditFlow.public.mortgage.ZbaozMortgage.AddPersonBaozMortgageWin',{
	                 		 customerEnterpriseName:customerEnterpriseName,
			                 mortgageRemarks:mortgageRemarks,
			                 personType:personType,
			                 assuremodeid:assuremodeid,
			                 relation:relation,
			                 cardnumber:cardnumber,
			                 cellphone:cellphone,
			                 cardtype:cardtype,
			                 businessType:this.businessType,
			                 MortgageData:this.MortgageData,
			                 projectId:this.projectId,
			                 personId:personId,
			                 readOnly:this.readOnly
		        	})
		    	);
	 	 }else{
		 	var personType=loginForm.getCmpByName('procreditMortgage.personType').getValue();
			 if(Ext.isEmpty(personType)){
			    Ext.Msg.alert('','保证人类型不能为空');
				return;
			 }
	 	 	var customerEnterpriseName=loginForm.getCmpByName('customerEnterpriseName').getValue();
	 	 	 if(Ext.isEmpty(customerEnterpriseName)){
		     	Ext.Msg.alert('','保证人不能为空');
			  	return;
		  	 }
		 	var mortgageRemarks=loginForm.getCmpByName('procreditMortgage.mortgageRemarks').getValue();
	        var assuremodeid=loginForm.getCmpByName('procreditMortgage.assuremodeid').getValue();
		 	var relation=loginForm.getCmpByName('procreditMortgage.relation').getValue();
		 	var enterpriseId=loginForm.getCmpByName('enterprise.id').getValue();
		 	var cellphone=loginForm.getCmpByName('enterprise.cellphone').getValue();
		 	var legalpersonid=loginForm.getCmpByName('enterprise.legalpersonid').getValue();
		 	var legalperson=loginForm.getCmpByName('enterprise.legalperson').getValue();
		 	var area=loginForm.getCmpByName('enterprise.area').getValue();
		 	var cciaa=loginForm.getCmpByName('enterprise.cciaa').getValue();
		 	var telephone=loginForm.getCmpByName('enterprise.telephone').getValue();
		 	var businessType=loginForm.getCmpByName('procreditMortgage.businessType').getValue();
		 	mobileNavi.push(
			             Ext.create('htmobile.creditFlow.public.mortgage.ZbaozMortgage.AddEnterpriseBaozMortgageWin',{
    	                 customerEnterpriseName:customerEnterpriseName,
    	                 mortgageRemarks:mortgageRemarks,
    	                 personType:personType,
    	                 assuremodeid:assuremodeid,
    	                 relation:relation,
    	                 cellphone:cellphone,
    	                 enterpriseId:enterpriseId,
    	                 legalpersonid:legalpersonid,
    	                 legalperson:legalperson,
    	                 area:area,
    	                 cciaa:cciaa,
    	                 telephone:telephone,
    	                 businessType:businessType,
    	                 MortgageData:this.MortgageData,
    	                 projectId:this.projectId,
    	                 readOnly:this.readOnly
			        	})
			    	);
		 
	 	 }
	 
	 }

});
