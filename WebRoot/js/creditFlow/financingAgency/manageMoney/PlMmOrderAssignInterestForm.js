/**
 * @author 
 * @createtime 
 * @class PlMmOrderAssignInterestForm
 * @extends Ext.Window
 * @description PlMmOrderAssignInterest表单
 * @company 智维软件
 */
PlMmOrderAssignInterestForm = Ext.extend(Ext.Window, {
			//构造函数
			constructor : function(_cfg) {
				Ext.applyIf(this, _cfg);
				//必须先初始化组件
				this.initUIComponents();
				PlMmOrderAssignInterestForm.superclass.constructor.call(this, {
							id : 'PlMmOrderAssignInterestFormWin',
							layout : 'fit',
							items : this.formPanel,
							modal : true,
							height : 400,
							width : 500,
							maximizable : true,
							title : '[PlMmOrderAssignInterest]详细信息',
							buttonAlign : 'center',
							buttons : [
										{
											text : '保存',
											iconCls : 'btn-save',
											scope : this,
											handler : this.save
										}, {
											text : '重置',
											iconCls : 'btn-reset',
											scope : this,
											handler : this.reset
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
				this.formPanel = new Ext.FormPanel({
							layout : 'form',
							bodyStyle : 'padding:10px',
							border : false,
							autoScroll:true,
							//id : 'PlMmOrderAssignInterestForm',
							defaults : {
								anchor : '96%,96%'
							},
							defaultType : 'textfield',
							items : [{
								name : 'plMmOrderAssignInterest.assignInterestId',
								xtype : 'hidden',
								value : this.assignInterestId == null ? '' : this.assignInterestId
							}
																																																	,{
																fieldLabel : '订单id',	
								 								name : 'plMmOrderAssignInterest.orderId'
								 																,xtype:'numberfield'
								 							}
																																										,{
																fieldLabel : 'id投资人',	
								 								name : 'plMmOrderAssignInterest.investPersonId'
								 																,xtype:'numberfield'
								 							}
																																										,{
																fieldLabel : '投资人姓名',	
								 								name : 'plMmOrderAssignInterest.investPersonName'
								 																 								,maxLength: 10
								 							}
																																										,{
																fieldLabel : '理财产品的id',	
								 								name : 'plMmOrderAssignInterest.mmplanId'
								 																,xtype:'numberfield'
								 							}
																																										,{
																fieldLabel : '理财产品的名称',	
								 								name : 'plMmOrderAssignInterest.mmName'
								 																 								,maxLength: 255
								 							}
																																										,{
																fieldLabel : '类型loanInterest，principalRepayment',	
								 								name : 'plMmOrderAssignInterest.fundType'
								 																 								,maxLength: 255
								 							}
																																										,{
																fieldLabel : '收入',	
								 								name : 'plMmOrderAssignInterest.incomeMoney'
								 								 							}
																																										,{
																fieldLabel : '日',	
								 								name : 'plMmOrderAssignInterest.intentDate'
								 																,xtype:'datefield',
								format:'Y-m-d',
								value:new Date()
								 							}
																																			]
						});
				//加载表单对应的数据	
				if (this.assignInterestId != null && this.assignInterestId != 'undefined') {
					this.formPanel.loadData({
								url : __ctxPath + '/creditFlow.financingAgency.manageMoney/getPlMmOrderAssignInterest.do?assignInterestId='+ this.assignInterestId,
								root : 'data',
								preName : 'plMmOrderAssignInterest'
							});
				}
				
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
				$postForm({
						formPanel:this.formPanel,
						scope:this,
						url:__ctxPath + '/creditFlow.financingAgency.manageMoney/savePlMmOrderAssignInterest.do',
						callback:function(fp,action){
							var gridPanel = Ext.getCmp('PlMmOrderAssignInterestGrid');
							if (gridPanel != null) {
								gridPanel.getStore().reload();
							}
							this.close();
						}
					}
				);
			}//end of save

		});