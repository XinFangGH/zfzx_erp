//var formulatestore = new HT.JsonStore({
//	url : __ctxPath + "/finance/listSlFundIntent.do"	
//});

BankGuaranteeMoney = Ext.extend(Ext.Panel,
				{
	            projectId:null,
	            isonlyread:false,
				constructor : function(_cfg) {
	            if(typeof(_cfg.projectId)!="undefined"){
	            	this.projectId=_cfg.projectId
	            }
	             if(typeof(_cfg.isonlyread)!="undefined"){
	            	this.isonlyread=_cfg.isonlyread
	            }
				Ext.applyIf(this, _cfg);
				//必须先初始化组件
				BankGuaranteeMoney.superclass.constructor.call(this, {

                        layout:'column',
					  labelAlign : "right",
					   anchor : '100%',
						items : [{
							name : 'glBankGuaranteemoney.bankGuaranteeId',
							xtype : 'hidden',
							value : this.bankGuaranteeId == null ? '' : this.bankGuaranteeId
						} ,{
							name : 'glBankGuaranteemoney.projectId',
							xtype : 'hidden'
						},{
							name : 'glBankGuaranteemoney.accountId',
							xtype : 'hidden'
						},{
							name : 'rawaccountId',  //记录一开始的id
							xtype : 'hidden'
						},{  
								  columnWidth : 0.5,
								  labelWidth : 110,
							     layout : 'form',
								 items :[{
								fieldLabel : '银行名称',	
							 	name : 'glBankGuaranteemoney.guaranteebankName',
			                      xtype:'textfield',
								  anchor : '100%',
						        readOnly:true

								 }]
							 },{
							 columnWidth : .5,
							 labelWidth : 110,
							 layout : 'form',
							 items :[{
									fieldLabel : '保证金账户',	
                                    name : 'glBankGuaranteemoney.guaranteeaccount',
								    anchor : '100%',
								    readOnly : this.isonlyread,
								    flex:1,
			                        xtype:'trigger',
			             //        allowBlank : false,
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
								 columnWidth : .21,
							     layout : 'form',
							      labelWidth : 110,
							      items :[{
							    	xtype:'textfield',
							    	align : 'right',
									fieldLabel : '冻结金额',	
					//				allowBlank : false,
									  readOnly : this.isonlyread,
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
													  	if(p.getValue()==null ||p.getValue()==""){
													  		p.setValue(this.getCmpByName('chuschixxvalue').getValue());
													  	}else{
													  	if(parseFloat(value1) > parseFloat(value)){
													  			Ext.Msg.alert('状态','你填写的冻结保证金金额已经超出了此保证金账户的可用金额，请查证后再填!');
														  	p.setValue(this.getCmpByName('chuschixxvalue').getValue());
													  	}
													  	}
													  		
													  }																		  
																		  
																		  }
									 },{
												name : 'rawsurplusMoney',
												xtype : 'hidden'
							            //		fieldLabel : '账户可用金额.万元',
									}, {
							        xtype : 'hidden',
                                    name : 'chuschixxvalue'
									 },
//									
									  {
							        xtype : 'hidden',
                                    name : 'xxvalue'
									 }]
							 },{
								 columnWidth : .1, // 该列在整行中所占的百分比
								layout : "form", // 从上往下的布局
								labelWidth :37,
								items : [{
									fieldLabel : "<span style='margin-left:1px'>万元</span> ",
									labelSeparator : '',
									anchor : "100%"
								}]
							 }
							 ,{  
								  columnWidth : .19,
								   labelWidth : 65,
							     layout : 'form',
								 items :[{
									 
							    fieldLabel : '冻结日期',
							      readOnly : this.isonlyread,
						//	    allowBlank : false,
								  anchor : '100%',
								 name:'glBankGuaranteemoney.freezeDate',
								 format:'Y-m-d',
								 xtype:'datefield'
								
									 
								 }]
							 } ,{  
								  columnWidth : 0.5,
								  labelWidth : 110,
							     layout : 'form',
								 items :[{
					                    		 
							    fieldLabel : '保证金凭证号',
							      readOnly : this.isonlyread,
						//	    allowBlank : false,
								 anchor : '100%',
								 name:'glBankGuaranteemoney.certificateNum',
								 xtype:'textfield'
								 }]
							 }
						,{
							 columnWidth : 1,
							  labelWidth : 110,
							 layout : 'form',
							 items :[{
								 
								fieldLabel : '备注',	
								  readOnly : this.isonlyread,
							 	name : 'glBankGuaranteemoney.remark',
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
								//		obj.getCmpByName('glBankGuaranteemoneyfreezeMoney').setValue(Ext.util.Format.number(result.data.freezeMoney,'0,000.00'));
             						    obj.getCmpByName('xxvalue').setValue(result.data.maxfreezeMoney);
             						     obj.getCmpByName('rawaccountId').setValue(result.data.accountId);
             						     obj.getCmpByName('rawsurplusMoney').setValue(result.data.maxfreezeMoney+result.data.freezeMoney);
             						      obj.getCmpByName('chuschixxvalue').setValue(result.data.freezeMoney);
             						
             						}
							});	
			},//end of the constructor
			//初始化组件
		
			save : function() {
				$postForm({
						formPanel:this.formPanel,
						scope:this,
						url:__ctxPath + '/creditFlow/guarantee/EnterpriseBusiness/saveGlBankGuaranteemoney.do?projId='+this.projectId,
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