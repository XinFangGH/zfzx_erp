/**
 * @author 
 * @createtime 
 * @class PlMmOrderChildrenOrForm
 * @extends Ext.Window
 * @description PlMmOrderChildrenOr表单
 * @company 智维软件
 */
PlMmOrderChildrenOrForm = Ext.extend(Ext.Window, {
			//构造函数
			constructor : function(_cfg) {
				Ext.applyIf(this, _cfg);
				//必须先初始化组件
				this.initUIComponents();
				PlMmOrderChildrenOrForm.superclass.constructor.call(this, {
							id : 'PlMmOrderChildrenOrFormWin',
							layout : 'fit',
							items : this.formPanel,
							modal : true,
							height : 400,
							width : 500,
							maximizable : true,
							title : '[PlMmOrderChildrenOr]详细信息',
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
							//id : 'PlMmOrderChildrenOrForm',
							defaults : {
								anchor : '96%,96%'
							},
							defaultType : 'textfield',
							items : [{
								name : 'plMmOrderChildrenOr.matchId',
								xtype : 'hidden',
								value : this.matchId == null ? '' : this.matchId
							}
																																																	,{
																fieldLabel : '订单id',	
								 								name : 'plMmOrderChildrenOr.orderId'
								 																,xtype:'numberfield'
								 							}
																																										,{
																fieldLabel : '子债权id',	
								 								name : 'plMmOrderChildrenOr.childrenorId'
								 																,xtype:'numberfield'
								 							}
																																										,{
																fieldLabel : 'id投资人',	
								 								name : 'plMmOrderChildrenOr.investPersonId'
								 																,xtype:'numberfield'
								 							}
																																										,{
																fieldLabel : '投资人姓名',	
								 								name : 'plMmOrderChildrenOr.investPersonName'
								 																 								,maxLength: 10
								 							}
																																										,{
																fieldLabel : '理财产品的id',	
								 								name : 'plMmOrderChildrenOr.mmplanId'
								 																,xtype:'numberfield'
								 							}
																																										,{
																fieldLabel : '理财产品的名称',	
								 								name : 'plMmOrderChildrenOr.mmName'
								 																 								,maxLength: 255
								 							}
																																										,{
																fieldLabel : '父债权的id',	
								 								name : 'plMmOrderChildrenOr.parentOrBidId'
								 																,xtype:'numberfield'
								 							}
																																										,{
																fieldLabel : '父债权的名称',	
								 								name : 'plMmOrderChildrenOr.parentOrBidName'
								 																 								,maxLength: 255
								 							}
																																										,{
																fieldLabel : '匹配金额',	
								 								name : 'plMmOrderChildrenOr.matchingMoney'
								 								 							}
																																										,{
																fieldLabel : '子债权的日化利率',	
								 								name : 'plMmOrderChildrenOr.childrenOrDayRate'
								 								 							}
																																										,{
																fieldLabel : '匹配成功日',	
								 								name : 'plMmOrderChildrenOr.matchingStartDate'
								 																,xtype:'datefield',
								format:'Y-m-d',
								value:new Date()
								 							}
																																										,{
																fieldLabel : '匹配到期日',	
								 								name : 'plMmOrderChildrenOr.matchingEndDate'
								 																,xtype:'datefield',
								format:'Y-m-d',
								value:new Date()
								 							}
																																										,{
																fieldLabel : '匹配期限',	
								 								name : 'plMmOrderChildrenOr.matchingLimit'
								 																,xtype:'numberfield'
								 							}
																																										,{
																fieldLabel : '到期日类型（1，理财订单先到期，2子债权先到期）',	
								 								name : 'plMmOrderChildrenOr.matchingEndDateType'
								 																,xtype:'numberfield'
								 							}
																																										,{
																fieldLabel : '此匹配可获得的收益',	
								 								name : 'plMmOrderChildrenOr.matchingGetMoney'
								 								 							}
																																										,{
																fieldLabel : '匹配状态（0，匹配中，1已过期已处理）',	
								 								name : 'plMmOrderChildrenOr.matchingState'
								 																,xtype:'numberfield'
								 							}
																																			]
						});
				//加载表单对应的数据	
				if (this.matchId != null && this.matchId != 'undefined') {
					this.formPanel.loadData({
								url : __ctxPath + '/creditFlow.financingAgency.manageMoney/getPlMmOrderChildrenOr.do?matchId='+ this.matchId,
								root : 'data',
								preName : 'plMmOrderChildrenOr'
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
						url:__ctxPath + '/creditFlow.financingAgency.manageMoney/savePlMmOrderChildrenOr.do',
						callback:function(fp,action){
							var gridPanel = Ext.getCmp('PlMmOrderChildrenOrGrid');
							if (gridPanel != null) {
								gridPanel.getStore().reload();
							}
							this.close();
						}
					}
				);
			}//end of save

		});