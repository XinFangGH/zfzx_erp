

Ext.define('htmobile.creditFlow.public.mortgage.house.AddHouse', {
    extend: 'Ext.form.Panel',
    name: 'AddVehicle',
    constructor: function (config) {
    	config = config || {};
    	Ext.apply(this,config);
    	Ext.apply(config,{
    		style:'background-color:white;',
		    fullscreen: true,
		    scrollable:{
		    	direction: 'vertical'
		    },
		    title:"<span style='font-size:16px;'>抵质押物</span>",
		    items: [{
		                xtype: 'fieldset',
		                title :'<span style="color:#000000;">查看<住宅>详细信息</span>',
		                defaults:{
		                	xtype: 'textfield',
		                	readOnly:this.readOnly,
		                	labelWidth:document.body.clientWidth/3,
	    				    clearIcon : true
		                },
		                items: [{
				        		xtype : 'hiddenfield',
				        		name : 'procreditMortgageHouse.id',
				        		value:Ext.isEmpty(this.MortgageData)?null:this.MortgageData.vProjMortHouse.id
				        	},{
				        		xtype : 'hiddenfield',
				        		name : 'procreditMortgageHouse.objectType',
				        		value : "mortgage"
				        	},{
				        		xtype : 'hiddenfield',
				        		name : 'procreditMortgageHouse.type',
				        		value : "7"
				        	},{
				        		xtype : 'hiddenfield',
				        		name : 'procreditMortgage.projid',
				        		value : this.projectId
				        	},{
				        		xtype : 'hiddenfield',
				        		name : 'procreditMortgage.id',
				        		value : this.vProcreditDictionaryId
				        	},{
				        		xtype : 'hiddenfield',
				        		name : 'procreditMortgage.businessType',
				        		value : this.businessType
				        	},{
				        		xtype : 'hiddenfield',
				        		name : 'procreditMortgage.assuretypeid',
				        		value : this.assuretypeid
				        	},{
				        		xtype : 'hiddenfield',
				        		name : 'procreditMortgage.mortgagenametypeid',
				        		value : this.mortgagenametypeid
				        	},{
				        		xtype : 'hiddenfield',
				        		name : 'procreditMortgage.personType',
				        		value : this.personType
				        	},{
				        		xtype : 'hiddenfield',
				        		name : 'procreditMortgage.relation',
				        		value : this.relation
				        	},{
				        		xtype : 'hiddenfield',
				        		name : 'procreditMortgage.finalprice',
				        		value : this.finalprice
				        	},{
				        		xtype : 'hiddenfield',
				        		name : 'procreditMortgage.finalCertificationPrice',
				        		value : this.finalCertificationPrice
				        	},{
				        		xtype : 'hiddenfield',
				        		name : 'procreditMortgage.valuationTime',
				        		value : this.valuationTime
				        	},{
				        		xtype : 'hiddenfield',
				        		name : 'procreditMortgage.assuremoney',
				        		value : this.assuremoney
				        	},{
				        		xtype : 'hiddenfield',
				        		name : 'procreditMortgage.mortgageRemarks',
				        		 value:this.mortgageRemarks
				        	},{
				        		xtype : 'hiddenfield',
				        		name : 'customerEnterpriseName',
				        		value : this.customerEnterpriseName
				        	},{
				        		xtype : 'hiddenfield',
				        		name : 'procreditMortgage.mortgagepersontypeforvalue',
				        		 value:this.mortgagepersontypeforvalue
				        	},/*{
				        		xtype : 'hiddenfield',
				        		id:'personid',
				        		name : 'person.id',
				        		 value:this.mortgagepersontypeforvalue
				        	},*/
		                    {
		                        label:  '权证编号',
		                         name:"procreditMortgageHouse.certificatenumber",
		                         value: Ext.isEmpty(this.MortgageData)?null:this.MortgageData.vProjMortHouse.certificatenumber
		                    },
		                    {
		                        label: '共有人',
		                        id:'procreditMortgageHousemutualofperson',
		                         name:"procreditMortgageHouse.mutualofperson",
		                         value: Ext.isEmpty(this.MortgageData)?null:this.MortgageData.vProjMortHouse.mutualofperson,
		                          listeners : {
								scope:this,
								'focus' : function(f) {
									    //个人
									      mobileNavi.push(Ext.create('htmobile.public.SelectPersonlist',{
									      callback:function(data){
									   	   var mutualofperson= Ext.getCmp("procreditMortgageHousemutualofperson");
									   	   var personId= Ext.getCmp("personid");
									   	   var cardnumber= Ext.getCmp("personcardnumber");
						                    mutualofperson.setValue(data.name);
						                      personId.setValue(data.id);
						                     cardnumber.setValue(data.cardnumber);
									   }}));
								}
								}
		                    },
		                    {
		                        label:  '房屋坐落',
		                         name:"procreditMortgageHouse.houseaddress",
		                         id:'procreditMortgageHousehouseaddress',
		                         value: Ext.isEmpty(this.MortgageData)?null:this.MortgageData.vProjMortHouse.houseaddress
		                    },{
					              xtype: 'datepickerfield',
					              style:"width:100%;",
					              minWidth:20,
					              label: '登记日期',
					              name: 'procreditMortgageHouse.buildtime',
					              id:"procreditMortgageHousebuildtime",
					              dateFormat:'Y-m-d',
				                  picker: {
				                     xtype:'todaypickerux'
				                  },
		                          value: Ext.isEmpty(this.MortgageData)?null:new Date(this.MortgageData.vProjMortHouse.buildtime)
					        },
		                    {
		                        label:  '房屋结构',
		                        xtype : "dickeycombo",
		                         nodeKey : 'hxjg',
		                         name:"procreditMortgageHouse.housetypeid",
		                         id:'procreditMortgageHousehousetypeid',
		                         value: Ext.isEmpty(this.MortgageData)?null:this.MortgageData.vProjMortHouse.houseTypeIdValue,
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
		                    },
		                    {
		                        label:  '建筑面积.㎡',
		                         name:"procreditMortgageHouse.buildacreage",
		                         maxLength : 23,
								maxLengthText : '最大输入长度23',
		                         id:'procreditMortgageHousebuildacreage',
		                         value: Ext.isEmpty(this.MortgageData)?null:this.MortgageData.vProjMortHouse.buildacreage
		                    }
		               
		                
		                ]
		            },{
		            	docked:'bottom',
						cls : 'submit-button',
						xtype : 'button',
						text : "提交保存",
						handler : this.submitBtn
					}]
		            
    	});

    	this.callParent([config]);
    	
    },
    submitBtn:function(){
    
    
        var loginForm = this.up('formpanel');
        
        var certificatenumber=loginForm.getCmpByName('procreditMortgageHouse.certificatenumber').getValue();
         if(Ext.isEmpty(certificatenumber)){
		    Ext.Msg.alert('','权证编号不能为空');
			return;
		 }
		var houseaddress=loginForm.getCmpByName('procreditMortgageHouse.houseaddress').getValue();
         if(Ext.isEmpty(houseaddress)){
		   Ext.Msg.alert('','房屋坐落不能为空');
			return;
		 }
		var buildtime=loginForm.getCmpByName('procreditMortgageHouse.buildtime').getValue();
         if(Ext.isEmpty(buildtime)){
		    Ext.Msg.alert('','登记日期不能为空');
			return;
		 }
		 
		var time = formatTime(buildtime,"yyyy-MM-dd");
		var housetypeid=loginForm.getCmpByName('procreditMortgageHouse.housetypeid').getValue();
         if(Ext.isEmpty(housetypeid)){
		    Ext.Msg.alert('','房屋结构不能为空');
			return;
		 }
		var buildacreage=loginForm.getCmpByName('procreditMortgageHouse.buildacreage').getValue();
         if(Ext.isEmpty(buildacreage)){
		    Ext.Msg.alert('','建筑面积不能为空');
			return;
		 }

       	loginForm.submit({
				method : 'POST',
				url:__ctxPath+"/credit/mortgage/addMortgageOfDY.do",
				params:{
					"procreditMortgageHouse.buildtime":time
				},
				success : function(form, action) {
					  Ext.Msg.alert('','保存成功');
					  mobileNavi.pop(2);
					   var object= Ext.getCmp("DZYMortgageViewList");
					  object.store.loadPage(1);
				},
				failure : function(form, action) {
					  Ext.Msg.alert('','保存失败');
				}
			});		
    }

});
