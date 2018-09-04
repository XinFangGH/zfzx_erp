/**
 * @author 
 * @createtime 
 * @class SlFundDetailForm
 * @extends Ext.Window
 * @description SlFundDetail表单
 * @company 智维软件
 */
CashCheck = Ext.extend(Ext.Window, {
			//构造函数
			constructor : function(_cfg) {
				if(typeof(_cfg.fundIntentId)!="undefined")
						{
						      this.fundIntentId=_cfg.fundIntentId;
						}
						if(typeof(_cfg.editqlideMoney)!="undefined")
						{
						      this.editqlideMoney=_cfg.editqlideMoney;
						}
						
				Ext.applyIf(this, _cfg);
				//必须先初始化组件
				this.initUIComponents();
				CashCheck.superclass.constructor.call(this, {
							id : 'CashCheck',
							layout : 'fit',
							items : this.formPanel,
							modal : true,
							height : 240,
							width : 360,
							maximizable : true,
							title : '现金对账',
							buttonAlign : 'center',
							buttons : [
										{
											text : '对帐',
											iconCls : 'btn-save',
											scope : this,
											handler : this.save
										}, {
											text : '取消',
											iconCls : 'btn-cancel',
											scope : this,
											handler : this.cancel
										}
							         ]
				});
			},//end of the constructor
			//初始化组件
			initUIComponents : function() {
				var notMoney=this.notMoney
				this.formPanel = new Ext.FormPanel({
							layout : 'form',
							bodyStyle : 'padding:10px',
							labelAlign : 'right',
							border : false,
							autoScroll:true,
							//id : 'SlFundDetailForm',
							defaults : {
								anchor : '96%,96%'
							},
							defaultType : 'textfield',
							items : [{
								name : 'slFundQlide.fundQlideId',
								xtype : 'hidden',
								value : this.fundQlideId == null ? '' : this.fundQlideId
							},{
														
							                                   
								 							   fieldLabel : '到账时间',	
								 								name : 'slFundQlide.factDate',
								 								xtype : 'datefield',
								 								format: 'Y-m-d',
								 								labelAlign : 'right',
								 								allowBlank : false
								 								
								 																																
								 								},{
								 								fieldLabel : '到账金额(元)',	
								 								name : 'slFundQlide.notMoney',
								 								xtype:'numberfield',
								 								allowBlank : false,
								 								labelAlign : 'right',
								 								value : notMoney,
								 								listeners: {  
																		  blur: function(p){
																						if(p.getValue()<0){
																							Ext.Msg.alert('状态', "金额不能为负数");
																						
																					}else if(p.getValue()>notMoney){
																						Ext.Msg.alert('状态', "金额不能大于未对账金额");
																					}
																					
																		  }																									
								 								}
								 								
								 								
								 								},{
														
							                                   
								 							   fieldLabel : '交易摘要',	
								 								name : 'slFundQlide.transactionType',
								 								xtype : 'textarea',
								 								labelAlign : 'right'
								 								
								 																																
								 								}

													
																
													
																																			]
						});
				//加载表单对应的数据	
//				if (this.fundIntentId != null && this.fundIntentId != 'undefined') {
//					this.formPanel.loadData({
//								url : __ctxPath + '/finance/getSlFundIntent.do?fundIntentId='+ this.fundIntentId,
//								root : 'data',
//								preName : 'slFundIntent'
//							});
//				}
				
			},//end of the initcomponents
              
			/**
			 * 重置
			 * @param {} formPanel
			 */
			reset : function() {
				this.formPanel.getForm().reset();
			},
			/**
			 * 取消
			 * @param {} window
			 */
			cancel : function() {
				this.close();
			},
			/**
			 * 保存记录
			 */
			save : function() {
				this.formPanel.getForm().submit({
            scope : this,
            url :  __ctxPath + '/creditFlow/finance/cashCheckSlFundIntent.do',
            method : 'post',
            params : {
            fundIntentId : this.fundIntentId
            },
            success : function(fp, action) {
                  Ext.ux.Toast.msg('操作信息', '对账成功！');
										var gridPanel = Ext.getCmp('SlFundIntentGrid');
											if (gridPanel != null) {
												gridPanel.getStore().reload();
											}
											this.close()
            }})
				 
//				$postForm({
//						formPanel:this.formPanel,
//						scope:this,
//						url:__ctxPath + '/finance/cashCheckSlFundIntent.do?fundIntentId='+this.fundIntentId,
//						callback:function(fp,action){
//							var gridPanel = Ext.getCmp('SlFundIntentGrid');
//							if (gridPanel != null) {
//								gridPanel.getStore().reload();
//							}
//							this.close();
//						}
//					}
//				);
			}//end of save

		});