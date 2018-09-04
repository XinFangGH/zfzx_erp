/**
 * @author 
 * @createtime 
 * @class SlFundDetailForm
 * @extends Ext.Window
 * @description SlFundDetail表单
 * @company 智维软件
 */
editQlideCheck = Ext.extend(Ext.Window, {
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
							if(typeof(_cfg.notMoney)!="undefined")
						{
						      this.notMoney=_cfg.notMoney;
						}
				Ext.applyIf(this, _cfg);
				//必须先初始化组件
				this.initUIComponents();
				editQlideCheck.superclass.constructor.call(this, {
				//			id : 'editQlideCheck',
							layout : 'fit',
							items : this.formPanel,
							modal : true,
							height : 150,
							width : 400,
							//maximizable : true,
							title : '填写金额',
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
				var editqlideMoney=this.editqlideMoney
				var notMoney=this.notMoney
		
				this.formPanel = new Ext.FormPanel({
							layout : 'form',
							bodyStyle : 'padding:10px',
							border : false,
							autoScroll:true,
							//id : 'SlFundDetailForm',
							defaults : {
								 // labelWidth:350
								//anchor : '96%,96%'
							},
							//defaultType : 'textfield',
							items : [{
								name : 'slFundQlide.fundQlideId',
								xtype : 'hidden',
								value : this.fundQlideId == null ? '' : this.fundQlideId
							},{                                
							                                    
								 								fieldLabel : '本流水划入本款项金额(元)',	
								 								labelWidth :'80%',
								 								labelAlign :'right',
								 								name : 'slFundQlide.notMoney',
								 								xtype:'numberfield',
								 								width:190,
								 								id : 'editqlied',
								 								value :this.editqlideMoney
//								 								listeners: {  
//																		  blur: function(p){
//																				if(p.getValue()<0){
//																					Ext.Msg.alert('状态', "金额不能为负数");
//																					}
//																						
//																				 if(p.getValue()>editqlideMoney){
//																						Ext.Msg.alert('状态', "金额不能大于流水的未对账金额");
//																				 }
//																				 if(p.getValue()>notMoney){
//																						Ext.Msg.alert('状态', "金额不能大于款项的未对账金额");
//																					}
//																					
//																		  }																									
//								 								}
								 								
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
				var tabflag=this.tabflag;
				var money = Ext.getCmp('editqlied');
				var otherPanel=this.otherPanel;
				if(money.getValue()>this.notMoney){
					Ext.Msg.alert('状态', "金额不能大于款项的未对账金额");
					return null;
				}
				if(money.getValue()>this.editqlideMoney){
					Ext.Msg.alert('状态', "金额不能大于流水的未对账金额");
					return null;
				}
				if(money.getValue()<0){
					Ext.Msg.alert('状态', "金额不能为负数");
					return null;
				}
						if(money.getValue()==0){
					Ext.Msg.alert('状态', "金额不能为0");
					return null;
				}
			this.formPanel.getForm().submit({
            scope : this,
            url :  __ctxPath + '/creditFlow/finance/editQlideCheckSlFundIntent.do',
            method : 'post',
            params : {
            fundQlideId : this.fundQlideId,
            fundIntentId : this.fundIntentId
            },
            waitMsg : '正在提交数据...',
            success : function(fp, action) {
                  Ext.ux.Toast.msg('操作信息', '对账成功！');
										var gridPanel5 = Ext.getCmp('SlFundQlideGridcheck');
											if (gridPanel5 != null) {
												gridPanel5.getStore().reload();
											}
											
											var gridPanel1 = Ext.getCmp('SlFundIntentGrid1'+tabflag);
										var gridPanel2 = Ext.getCmp('SlFundIntentGrid2'+tabflag);
										var gridPanel3 = Ext.getCmp('SlFundIntentGrid3'+tabflag);
										var gridPanel4 = Ext.getCmp('SlFundIntentGrid4'+tabflag);
										
                                        if (gridPanel1 != null) {
								         gridPanel1.getStore().reload();
						             	}
						             	if (gridPanel2 != null) {
								           gridPanel2.getStore().reload();
						             	}
						             	if (gridPanel3 != null) {
								             gridPanel3.getStore().reload();
						             	}
						             	if (gridPanel4 != null) {
								               gridPanel4.getStore().reload();
						             	}
						             	if(null!=otherPanel && typeof(otherPanel)!='undefined'){
						             		otherPanel.getStore().reload()
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