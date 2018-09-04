//var formulatestore = new HT.JsonStore({
//	url : __ctxPath + "/finance/listSlFundIntent.do"	
//});

CustomerGuaranteeMoney = Ext.extend(Ext.Panel,
				{
	            projectId:null,
				constructor : function(_cfg) {
				 if(typeof(_cfg.projectId)!="undefined"){
			     	this.projectId=_cfg.projectId
			     }
				Ext.applyIf(this, _cfg);
				
				CustomerGuaranteeMoney.superclass.constructor.call(this, {
                      layout:'column',
					   anchor : '100%',
					  labelAlign : "right",
						items : [{
							name : 'glActualToCharge.actualChargeId',
							xtype : 'hidden',
							value : this.actualChargeId
						} ,{
							name : 'glActualToCharge.projectId',
							xtype : 'hidden'
						} ,{  
								  columnWidth : 0.5,
								  labelWidth : 135,
							     layout : 'form',
								 items :[{
								fieldLabel : '保证金收取方式',	
							 	name : 'glActualToCharge.earnestmoneyType',
							   anchor : '100%',
							//   readOnly:true,
							   xtype : 'csdiccombo',
								itemVale:premchargmodeDicId,
								hiddenName:'glActualToCharge.earnestmoneyType',
                                lazyInit : false,
								forceSelection : false,
								 readOnly:true,
								 listeners : {
										afterrender : function(combox) {
											var st = combox.getStore();
											st.on("load", function() {
												combox.setValue(combox.getValue());
												combox.clearInvalid();
											})
										}
			
									}
								
								 }]
							 },{
							 columnWidth : .5,
							 labelWidth : 135,
							 layout : 'form',
							 items :[{
									xtype:'numberfield',
									fieldLabel : '保证金比例(%)',	
                                 name : 'glActualToCharge.customerEarnestmoneyScale',
								    anchor : '100%',
								     readOnly:true
								     
							 }]
							 },{
								 columnWidth : .5,
							     layout : 'form',
							      labelWidth : 135,
							      items :[{
							    	xtype:'numberfield',
									fieldLabel : '计划收取金额(万元)',	
                                 name : 'glActualToCharge.customerMoney',
								    anchor : '100%',
								     readOnly:true
									 }]
							 }
							 ,{  
								  columnWidth : .5,
								   labelWidth : 135,
							     layout : 'form',
								 items :[{
									 
							    fieldLabel : '计划收取日期',
								  anchor : '100%',
								 name:'glActualToCharge.planChargeDate',
								 format:'Y-m-d',
								 xtype:'datefield'
								 }]
							 }
						,{
							 columnWidth : 1,
							  labelWidth : 135,
							 layout : 'form',
							 items :[{
								 
								fieldLabel : '备注',	
							 	name : 'glActualToCharge.remark',
							    xtype:'textfield',
							    anchor : '100%'
							 }]
							 }
						]
						
							
				});
				 this.loadData({
						url : __ctxPath + '/actualCharges/guaranteeMoneyProjectIdActualToCharge.do?projectId='+this.projectId,
						root : 'data',
						preName : 'glActualToCharge'
				 });
			},//end of the constructor
			save : function() {
				$postForm({
						formPanel:this.formPanel,
						scope:this,
						url : __ctxPath + '/actualCharges/glActualsaveActualToCharge.do',
						callback:function(fp,action){
							
						}
					}
				);
			}//end of save
				});
				