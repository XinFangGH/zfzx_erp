/**
 * @author 
 * @createtime 
 * @class SlChargeDetailForm
 * @extends Ext.Window
 * @description SlChargeDetail表单
 * @company 智维软件
 */
SlChargeDetailForm = Ext.extend(Ext.Window, {
			//构造函数
			constructor : function(_cfg) {
				Ext.applyIf(this, _cfg);
				//必须先初始化组件
				this.initUIComponents();
				SlChargeDetailForm.superclass.constructor.call(this, {
							id : 'SlChargeDetailFormWin',
							layout : 'fit',
							items : this.formPanel,
							modal : true,
							height : 400,
							width : 500,
							maximizable : true,
							title : '[SlChargeDetail]详细信息',
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
							//id : 'SlChargeDetailForm',
							defaults : {
								anchor : '96%,96%'
							},
							defaultType : 'textfield',
							items : [{
								name : 'slChargeDetail.chargeDetailId',
								xtype : 'hidden',
								value : this.chargeDetailId == null ? '' : this.chargeDetailId
							}
																																																	,{
																fieldLabel : '款项表id',	
								 								name : 'slChargeDetail.fundIntentId'
								 																,xtype:'numberfield'
								 							}
																																										,{
																fieldLabel : '资金流水表id',	
								 								name : 'slChargeDetail.fundQlideId'
								 																,xtype:'numberfield'
								 							}
																																										,{
																fieldLabel : '逾期天数',	
								 								name : 'slChargeDetail.overdueNum'
								 																,xtype:'numberfield'
								 							}
																																										,{
																fieldLabel : '逾期利息',	
								 								name : 'slChargeDetail.overdueAccrual'
								 								 							}
																																										,{
																fieldLabel : '操作时间',	
								 								name : 'slChargeDetail.operTime'
								 																,xtype:'datefield',
								format:'Y-m-d',
								value:new Date()
								 							}
																																										,{
																fieldLabel : '',	
								 								name : 'slChargeDetail.afterMoney'
								 								 							}
																																			]
						});
				//加载表单对应的数据	
				if (this.chargeDetailId != null && this.chargeDetailId != 'undefined') {
					this.formPanel.loadData({
								url : __ctxPath + '/creditFlow/finance/getSlChargeDetail.do?chargeDetailId='+ this.chargeDetailId,
								root : 'data',
								preName : 'slChargeDetail'
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
						url:__ctxPath + '/creditFlow/finance/saveSlChargeDetail.do',
						callback:function(fp,action){
							var gridPanel = Ext.getCmp('SlChargeDetailGrid');
							if (gridPanel != null) {
								gridPanel.getStore().reload();
							}
							this.close();
						}
					}
				);
			}//end of save

		});