//var formulatestore = new HT.JsonStore({
//	url : __ctxPath + "/finance/listSlFundIntent.do"	
//});

GuaranteeMoney = Ext.extend(Ext.Panel,
				{
	            projectId:null,
				constructor : function(_cfg) {
				 if(typeof(_cfg.projectId)!="undefined"){
			     	this.projectId=_cfg.projectId
			     }
				Ext.applyIf(this, _cfg);

				GuaranteeMoney.superclass.constructor.call(this, {
				    layout:'column',
					   plain : true,
					  labelAlign : "right",
					  border:false,
					  anchor : '100%',
						items : [{
							name : 'glActualToCharge1.actualChargeId',
							xtype : 'hidden'
						
						} ,{
							name : 'glActualToCharge1.projectId',
							xtype : 'hidden'
						} ,{  
								  columnWidth : 0.5,
								  labelWidth : 135,
							     layout : 'form',
								 items :[{
								id:'bankName',	 
								fieldLabel : '项目金额(万元)',	
							 	name : 'glActualToCharge1.customerMoney',
		                      xtype:'textfield',
							  anchor : '100%',
							   readOnly:true

								 }]
							 },{
							 columnWidth : .5,
							 labelWidth : 135,
							 layout : 'form',
							 items :[{
									xtype:'numberfield',
									fieldLabel : '保费费率(%)',	
                                 name : 'glActualToCharge1.actualCharge',
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
                                 name : 'glActualToCharge1.customerEarnestmoneyScale',
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
								 name:'glActualToCharge1.planChargeDate',
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
							 	name : 'glActualToCharge1.remark',
							    xtype:'textfield',
							    anchor : '100%'
							 }]
							 }
						]
							
				});
				 this.loadData({
						url : __ctxPath + '/actualCharges/guaranteeChargeProjectIdActualToCharge.do?projectId='+this.projectId,
						root : 'data',
						preName : 'glActualToCharge1'
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
				