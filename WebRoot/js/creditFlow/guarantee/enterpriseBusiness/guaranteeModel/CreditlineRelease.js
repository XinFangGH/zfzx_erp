//var formulatestore = new HT.JsonStore({
//	url : __ctxPath + "/finance/listSlFundIntent.do"	
//});

creditlineRelease  = Ext.extend(Ext.Panel,
				{
	            projectId:null,
				constructor : function(_cfg) {
	            if(typeof(_cfg.projectId)!="undefined"){
	            	this.projectId=_cfg.projectId
	            }
				Ext.applyIf(this, _cfg);
				//必须先初始化组件
		
				creditlineRelease.superclass.constructor.call(this, {

                        layout:'column',
					   plain : true,
					  labelAlign : "right",
					   anchor : '100%',
						items : [{
								name : 'glBankCreditline.bankCreditLineId',
								xtype : 'hidden',
								value : null
							},{
								name : 'glBankCreditline.bankId',
								xtype : 'hidden',
								value : this.bankId
							} ,{
								name : 'glBankCreditline.projId',
								xtype : 'hidden',
							    value:this.projId
							} ,{  
								  columnWidth : 0.5,
								  labelWidth : 110,
							     layout : 'form',
								  items :[{
					//				id:'creditbankName',	 
									fieldLabel : '银行名称',	
								 	name : 'glBankCreditline.bankname',
			                      xtype:'textfield',
								  anchor : '100%',
								   readOnly:true
									 }]
							 },{
							 columnWidth : .5,
							 labelWidth : 110,
							 layout : 'form',
							 items :[{
								    	xtype:'numberfield',
										fieldLabel : '占用额度(万元)',	
	                                    name : 'glBankCreditline.occupancyMoney',
									    anchor : '100%',
									     readOnly:true
										 }]
							 },{
								 columnWidth : .25,
							     layout : 'form',
							      labelWidth : 110,
							      items :[{
										 
								    fieldLabel : '冻结日期',
									  anchor : '100%',
									 name:'glBankCreditline.freezeDate',
									 format:'Y-m-d',
									 xtype:'datefield'
								//	  readOnly:true
									
										 
									 }]
							 }
							 ,{  
								  columnWidth : .25,
								   labelWidth : 110,
							     layout : 'form',
								   items :[{
									
							                  	xtype:'combo',
										             mode : 'local',
									               displayField : 'name',
									              valueField : 'id',
									              editable : false,
									                 width : 70,
									                 store : new Ext.data.SimpleStore({
											        fields : ["name", "id"],
										            data : [["是", "0"],
													     	["否", "1"]]
									              	}),
										             triggerAction : "all",
									                hiddenName:"glBankCreditline.isRelease",
								                	fieldLabel : '是否已释放',	
								                	anchor : '100%',
								                	allowBlank:false,
										          	name : 'glBankCreditline.isRelease'
										 }]
							 } ,{  
								  columnWidth : 0.5,
								  labelWidth : 110,
							     layout : 'form',
								items :[{
										fieldLabel : '释放日期',	
								 	name : 'glBankCreditline.releaseDate',
								    xtype:'datefield',
								  anchor : '100%',
								   format:'Y-m-d',
								   allowBlank:false
								   
								 }]
							 }
						,{
							 columnWidth : 1,
							  labelWidth : 110,
							 layout : 'form',
							 items :[{
								 
								fieldLabel : '备注',	
							 	name : 'glBankCreditline.remark',
							    xtype:'textfield',
							    anchor : '100%'
							 }]
							 }
						]
					});
                    this.loadData({
						url : '', //__ctxPath + '/creditFlow/guarantee/EnterpriseBusiness/getGlBankCreditline.do?projId='+this.projectId,
						root : 'data',
						preName : 'glBankCreditline'
							});	
			}//end of the constructor
			//初始化组件
		
				});
	var selectAccountkLinkMan2=function(a,b,c){
	Ext.getCmp('moneybankName').setValue(b) ;
	Ext.getCmp('glAccountId').setValue(c) ;
	Ext.getCmp('glAccount').setValue(a) ;
}			