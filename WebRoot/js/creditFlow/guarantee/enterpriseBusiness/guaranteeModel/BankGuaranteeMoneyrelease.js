//var formulatestore = new HT.JsonStore({
//	url : __ctxPath + "/finance/listSlFundIntent.do"	
//});

BankGuaranteeMoneyrelease = Ext.extend(Ext.Panel,
				{
	            projectId:null,
	            frame1:true,
	            isallowBlank:false,
				constructor : function(_cfg) {
	            if(typeof(_cfg.frame1)!="undefined"){
	            	this.frame1=_cfg.frame1
	            };
	            if(typeof(_cfg.isallowBlank)!="undefined"){
	            	this.isallowBlank=_cfg.isallowBlank
	            };
	             if(typeof(_cfg.projectId)!="undefined"){
	            	this.projectId=_cfg.projectId
	            };
	            if(typeof(_cfg.isReadOnlyFrozen) != "undefined"){
	            	this.isReadOnlyFrozen = _cfg.isReadOnlyFrozen;
	            } else (
	            	this.isReadOnlyFrozen = true
	            );
	            if(typeof(_cfg.isReadOnlyRelease) != "undefined"){
	            	this.isReadOnlyRelease = _cfg.isReadOnlyRelease;
	            } else (
	            	this.isReadOnlyRelease = false
	            );
				Ext.applyIf(this, _cfg);
				//必须先初始化组件
		         var labelWidth = 100;
				BankGuaranteeMoneyrelease.superclass.constructor.call(this, {
						frame : this.frame1,
						autoHeight : true,
                        layout:'column',
					   plain : true,
					  labelAlign : "right",
					   anchor : '100%',
						items : [{
							name : 'glBankGuaranteemoney.projectId',
							xtype : 'hidden',
							value : this.projectId
						},{
							name : 'glBankGuaranteemoney.accountId',
							xtype : 'hidden'
						}
						,{
							name : 'rawaccountId',  //记录一开始的id
							xtype : 'hidden'
						},{  
								  columnWidth : .5,
								  labelWidth : labelWidth,
							     layout : 'form',
								 items :[{
								fieldLabel : '银行名称',	
							 	name : 'glBankGuaranteemoney.guaranteebankName',
			                      xtype:'textfield',
								  anchor : '100%',
						        readOnly:this.isReadOnlyFrozen

								 }]
							 },{
							 columnWidth : .5,
							 labelWidth : labelWidth,
							 layout : 'form',
							 items :[{
									fieldLabel : '保证金账户',	
                                    name : 'glBankGuaranteemoney.guaranteeaccount',
                                    readOnly:this.isReadOnlyFrozen,
								    anchor : '100%',
								    flex:1,
			                        xtype:'trigger',
			                        allowBlank : this.isallowBlank,
								    triggerClass :'x-form-search-trigger',
							     	onTriggerClick : function(){
										var obj=this.ownerCt.ownerCt.ownerCt;
										var selectAccountkLinkMan2=function(obj11){
											 if(parseFloat(obj.getCmpByName('glBankGuaranteemoney.freezeMoney').getValue())> obj11.surplusMoney){
										    Ext.Msg.alert('状态','你填写的冻结保证金金额已经超出了此保证金账户的可用金额，请查证后再填!');
										  }else{
										     obj.getCmpByName('glBankGuaranteemoney.guaranteebankName').setValue(obj11.bankName) ;
										     obj.getCmpByName('glBankGuaranteemoney.accountId').setValue(obj11.id) ;
										     obj.getCmpByName('glBankGuaranteemoney.guaranteeaccount').setValue(obj11.accountname) ;
//										     if(obj.getCmpByName('rawaccountId').getValue()==obj11.id){
//             						            obj11.surplusMoney=obj.getCmpByName('rawsurplusMoney').getValue();
//	             						      }
											  obj.getCmpByName('xxvalue').setValue(obj11.surplusMoney) ;
											  
										 }
											
										}
                                              
									   selectAccountBankName1(selectAccountkLinkMan2);
							}
								    
								     
							 }]
							 },{
								 columnWidth : .25,
							     layout : 'form',
							      labelWidth : labelWidth,
							      items :[{
							    	xtype:'textfield',
							    	align : 'right',
									fieldLabel : '冻结金额(万元)',	
									readOnly:this.isReadOnlyFrozen,
									 allowBlank : this.isallowBlank,
                                    name : 'glBankGuaranteemoney.freezeMoney',
                                    fieldClass:'field-align',
								    anchor : '100%',	
								    listeners: {  
												       scope :this,
													  change: function(p){
//													  	var obj=this.ownerCt.ownerCt.ownerCt;
													  	var value1= p.getValue();
													  	var v =	  this.getCmpByName('xxvalue');
													  	var value=v.getValue();
													  	if(parseInt(value1) > parseInt(value)){
													  			Ext.Msg.alert('状态','你填写的冻结保证金金额已经超出了此保证金账户的可用金额，请查证后再填!');
														  	p.setValue(0);
													  	}
													  	
													  		
													  }																		  
																		  
																		  }
									 },{
												name : 'rawsurplusMoney',
												xtype : 'hidden'
							            //		fieldLabel : '账户可用金额.万元',
									}, {
							        xtype : 'hidden',
                                    name : 'xxvalue'
									 }]
							 }
							,{  
								  columnWidth : .25,
								   labelWidth : labelWidth,
							     layout : 'form',
								 items :[{
									 
							    fieldLabel : '冻结日期',
								  anchor : '100%',
								   allowBlank : this.isallowBlank,
								  readOnly:this.isReadOnlyFrozen,
								 name:'glBankGuaranteemoney.freezeDate',
								 
								 format:'Y-m-d',
								 xtype:'datefield'
								
									 
								 }]
							 }  ,{  
								  columnWidth : .25,
								   labelWidth : labelWidth,
							     layout : 'form',
								   items :[{
									
							                  	xtype:'combo',
										             mode : 'local',
									               displayField : 'name',
									               readOnly:this.isReadOnlyRelease,
									              valueField : 'id',
									              editable : false,
									                 width : 70,
									                 store : new Ext.data.SimpleStore({
											        fields : ["name", "id"],
										            data : [["否", "0"],
													     	["是", "1"]]
									              	}),
										             triggerAction : "all",
									                hiddenName:"glBankGuaranteemoney.isRelease",
								                	fieldLabel : '是否已释放',	
								                	anchor : '100%',
								                	allowBlank:this.isallowBlank,
										          	name : 'glBankGuaranteemoney.isRelease'
										 }]
							 } ,{  
								  columnWidth : .25,
								   labelWidth : labelWidth,
							     layout : 'form',
								 items :[{
									 
							    fieldLabel : '释放日期',
								  anchor : '100%',
								 name:'glBankGuaranteemoney.releaseDate',
								 readOnly:this.isReadOnlyRelease,
								  allowBlank : this.isallowBlank,
								 format:'Y-m-d',
								 xtype:'datefield'
								
									 
								 }]
							 }
						,{  
								  columnWidth : 0.25,
								  labelWidth : labelWidth,
							     layout : 'form',
								 items :[{
					                    		 
							    fieldLabel : '保证金凭证号',
							    allowBlank : this.isallowBlank,
							    readOnly:this.isReadOnlyFrozen,
								 anchor : '100%',
								 name:'glBankGuaranteemoney.certificateNum',
								 xtype:'textfield'
								 }]
							 },{
							 columnWidth : .37,
							  labelWidth : labelWidth,
							 layout : 'form',
							 items :[{
								 
								fieldLabel : '冻结备注',	
							 	name : 'glBankGuaranteemoney.remark',
							 	readOnly:this.isReadOnlyFrozen,
							    xtype:'textfield',
							    anchor : '100%'
							 }]
							 }
							 ,{
							 columnWidth : .38,
							  labelWidth : labelWidth,
							 layout : 'form',
							 items :[{
								 
								fieldLabel : '解冻备注',	
							 	name : 'glBankGuaranteemoney.releaseremark',
							 	readOnly:this.isReadOnlyRelease,
							    xtype:'textfield',
							    anchor : '100%'
							 }]
							 }
						]});
                    this.loadData({
						url : __ctxPath + '/creditFlow/guarantee/EnterpriseBusiness/getGlBankGuaranteemoney.do?projId='+this.projectId,
						root : 'data',
						preName : 'glBankGuaranteemoney',
						scope:this,
								success:function(resp,options){
										var result=Ext.decode(resp.responseText);
             				//		Ext.getCmp('glAccount').setValue(result.data.guaranteeaccount);
										var obj=this;
										obj.getCmpByName('glBankGuaranteemoney.guaranteeaccount').setValue(result.data.guaranteeaccount);
									//	obj.getCmpByName('glBankGuaranteemoneyfreezeMoney').setValue(Ext.util.Format.number(result.data.freezeMoney,'0,000.00'));
             						    obj.getCmpByName('xxvalue').setValue(result.data.maxfreezeMoney+result.data.freezeMoney);
             						    obj.getCmpByName('rawaccountId').setValue(result.data.accountId);
             						     obj.getCmpByName('rawsurplusMoney').setValue(result.data.maxfreezeMoney+result.data.freezeMoney);
             						
             						}
							});	
			},//end of the constructor
			//初始化组件
		
			save : function() {
				$postForm({
						formPanel:this.formPanel,
						scope:this,
						url:__ctxPath + '/creditFlow/guarantee/EnterpriseBusiness/save1GlBankGuaranteemoney.do?projId='+this.projectId,
						callback:function(fp,action){

						}
					}
				);
			}//end of save
				});
//	var selectAccountkLinkMan2=function(a,b,c,d,e){
//	Ext.getCmp('moneybankName').setValue(b) ;
//	Ext.getCmp('glAccountId').setValue(d) ;
//	Ext.getCmp('glAccount').setValue(a) ;
//	Ext.getCmp('glfreezeMoneyvlaue').setValue(e) ;
//	
//	
//}			