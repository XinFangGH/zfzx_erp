//var formulatestore = new HT.JsonStore({
//	url : __ctxPath + "/finance/listSlFundIntent.do"	
//});

BankGuaranteeMoney = Ext.extend(Ext.Panel,
				{
				constructor : function(_cfg) {
				Ext.applyIf(this, _cfg);
				//必须先初始化组件
				this.initUIComponents();
				BankGuaranteeMoney.superclass.constructor.call(this, {
				        	id : 'BankGuaranteeMoney',
				        	layout:'anchor',
		  				    anchor : '100%',
		  				    frame:true,
		  				    border : false,
							items : this.formPanel,
							title : '银行保证金',
							buttons : [
										{
											text : '保存',
											iconCls : 'btn-save',
											scope : this,
											handler : this.save
										}]
							
				});
			},//end of the constructor
			//初始化组件
			initUIComponents : function() {
				this.formPanel = new Ext.FormPanel({
                            layout:'column',
						   plain : true,
						  labelAlign : "right",
							items : [{
								name : 'glBankGuaranteemoney.bankGuaranteeId',
								xtype : 'hidden'
							} ,{
								name : 'glBankGuaranteemoney.projId',
								xtype : 'hidden'
							} ,{  
									  columnWidth : 0.5,
									  labelWidth : 110,
								     layout : 'form',
									 items :[{
									id:'moneybankName',	 
									fieldLabel : '银行名称',	
								 	name : 'glBankGuaranteemoney.bankName',
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
										fieldLabel : '保证金账户',	
	                                    name : 'glBankGuaranteemoney.accountId',
									    anchor : '100%'
									     
								 }]
								 },{
									 columnWidth : .25,
								     layout : 'form',
								      labelWidth : 110,
								      items :[{
								    	xtype:'numberfield',
										fieldLabel : '冻结金额(万元)',	
	                                    name : 'glBankGuaranteemoney.freezeMoney',
									    anchor : '100%'
										 }]
								 }
								 ,{  
									  columnWidth : .25,
									   labelWidth : 110,
								     layout : 'form',
									 items :[{
										 
								    fieldLabel : '冻结日期',
								     id:'5',
									  anchor : '100%',
									 name:'glBankGuaranteemoney.freezeDate',
									 format:'Y-m-d',
									 xtype:'datefield'
									
										 
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
									                hiddenName:"glBankGuaranteemoney.isRelease",
								                	fieldLabel : '是否已释放',	
								                	anchor : '100%',
								                	allowBlank:false,
										          	name : 'glBankGuaranteemoney.isRelease'
										 }]
								 }
								 ,{
								 columnWidth : .25,
								  labelWidth : 110,
								 layout : 'form',
								 items :[{
									 
										fieldLabel : '释放日期',	
								 	name : 'glBankGuaranteemoney.releaseDate',
								    xtype:'datefield',
								  anchor : '100%',
								   format:'Y-m-d',
								   allowBlank:false
								   
								 }]
								 }
								  ,{  
									  columnWidth : 0.5,
									  labelWidth : 110,
								     layout : 'form',
									 items :[{
						                    		 
								    fieldLabel : '保证金凭证号',
								     id:'6',
									 anchor : '100%',
									 name:'glBankGuaranteemoney.certificateNum',
									 xtype:'textfield'
									 }]
								 }
							,{
								 columnWidth : 0.5,
								  labelWidth : 110,
								 layout : 'form',
								 items :[{
									 
									fieldLabel : '备注',	
								 	name : 'glBankGuaranteemoney.remark',
								    xtype:'textfield',
								    anchor : '100%'
								 }]
								 }
							]
						});
		              this.formPanel.loadData({
						url : __ctxPath + '/creditFlow/guarantee/EnterpriseBusiness/getGlBankGuaranteemoney.do?projId=13',
						root : 'data',
						preName : 'glBankGuaranteemoney'
							});		
			},
			save : function() {
				$postForm({
						formPanel:this.formPanel,
						scope:this,
						url:__ctxPath + '/creditFlow/guarantee/EnterpriseBusiness/saveGlBankGuaranteemoney.do',
						callback:function(fp,action){
							var gridPanel = Ext.getCmp('SlBankAccountGrid');
							if (gridPanel != null) {
								gridPanel.getStore().reload();
							}
							this.close();
						}
					}
				);
			}//end of save
				});
				