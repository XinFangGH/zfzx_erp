/**
 * @author 
 * @createtime 
 * @class SettlementInfoForm
 * @extends Ext.Window
 * @description SettlementInfo表单
 * @company 智维软件
 */
SettlementInfoForm = Ext.extend(Ext.Window, {
			//构造函数
			constructor : function(_cfg) {
				Ext.applyIf(this, _cfg);
				//必须先初始化组件
				this.initUIComponents();
				SettlementInfoForm.superclass.constructor.call(this, {
							id : 'SettlementInfoFormWin',
							layout : 'fit',
							items : this.formPanel,
							modal : true,
							height : 400,
							width : 500,
							maximizable : true,
							title : '[SettlementInfo]详细信息',
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
							//id : 'SettlementInfoForm',
							defaults : {
								anchor : '96%,96%'
							},
							defaultType : 'textfield',
							items : [{
								name : 'settlementInfo.infoId',
								xtype : 'hidden',
								value : this.infoId == null ? '' : this.infoId
							}
																																																	,{
																fieldLabel : '保有量金额',	
								 								name : 'settlementInfo.settleMoney'
								 								 							}
																																										,{
																fieldLabel : '提成比例',	
								 								name : 'settlementInfo.royaltyRatio'
								 								 							}
																																										,{
																fieldLabel : '提成金额',	
								 								name : 'settlementInfo.royaltyMoney'
								 								 							}
																																										,{
																fieldLabel : '生成时间',	
								 								name : 'settlementInfo.createDate'
								 																,xtype:'datefield',
								format:'Y-m-d',
								value:new Date()
								 							}
																																										,{
																fieldLabel : '部门ID',	
								 								name : 'settlementInfo.orgId'
								 																,xtype:'numberfield'
								 							}
																																			]
						});
				//加载表单对应的数据	
				if (this.infoId != null && this.infoId != 'undefined') {
					this.formPanel.loadData({
								url : __ctxPath + '/creditFlow.fund.project/getSettlementInfo.do?infoId='+ this.infoId,
								root : 'data',
								preName : 'settlementInfo'
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
						url:__ctxPath + '/creditFlow.fund.project/saveSettlementInfo.do',
						callback:function(fp,action){
							var gridPanel = Ext.getCmp('SettlementInfoGrid');
							if (gridPanel != null) {
								gridPanel.getStore().reload();
							}
							this.close();
						}
					}
				);
			}//end of save

		});