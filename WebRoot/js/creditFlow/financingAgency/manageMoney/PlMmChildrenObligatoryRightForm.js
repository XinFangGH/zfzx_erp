/**
 * @author 
 * @createtime 
 * @class SlFundDetailForm
 * @extends Ext.Window
 * @description SlFundDetail表单
 * @company 智维软件
 */
PlMmChildrenObligatoryRightForm = Ext.extend(Ext.Window, {
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
				PlMmChildrenObligatoryRightForm.superclass.constructor.call(this, {
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
											text : '匹配',
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
							                                    
								 								fieldLabel : '匹配金额',	
								 								labelWidth :'80%',
								 								labelAlign :'right',
								 								name : 'matchingMoney',
								 								xtype:'numberfield',
								 								width:190,
								 								id : 'editqlied',
								 								value :this.editqlideMoney

								 								
								 								}

													
																
													
																																			]
						});
				
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
				var keystr=this.keystr;
				var money = Ext.getCmp('editqlied');
				var otherPanel=this.otherPanel;
				if(money.getValue()>this.availableMoney){
					Ext.Msg.alert('状态', "金额不能大与债权可转让金额");
					return null;
				}
				if(money.getValue()>this.currentMatchingMoney){
					Ext.Msg.alert('状态', "金额不能大于订单可匹配金额");
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
				var matchingmoney=money.getValue();
				var orderId=this.orderId;
				var childrenorId=this.childrenorId;
				var seachDate=this.seachDate;
				var obj=this;
                Ext.Ajax.request({
				url : __ctxPath + "/creditFlow/financingAgency/matchingPlMmOrderChildrenOr.do",
				method : 'post',
				success : function(response, request) {
					var object = Ext.util.JSON.decode(response.responseText);
						if(object.matchingdayisunused==true){
							Ext.ux.Toast.msg('操作信息','时间匹配不上，请重新选择债权');							
						} else if(object.earlierOutDate==true){
							Ext.ux.Toast.msg('操作信息', '此订单已提前赎回，不能再匹配了');
						} else {
							var count=object.count;
						    Ext.ux.Toast.msg('操作信息', '匹配成功,成功匹配'+count+'条记录');
						  	obj.close();
						    Ext.getCmp("PlMmChildrenObligatoryRightView").close();
                            Ext.getCmp("PlMmOrderChildrenOrGrid"+keystr).getStore().reload();
						}
					
                      
				},
				params:{
				orderId:orderId,
				childrenorId:childrenorId,
				seachDate:seachDate,
				matchingmoney:matchingmoney
				
				}
			});
			}//end of save

		});