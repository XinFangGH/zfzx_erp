/**
 * @author 
 * @createtime 
 * @class SlPunishDetailForm
 * @extends Ext.Window
 * @description SlPunishDetail表单
 * @company 智维软件
 */
SlPunishDetailForm = Ext.extend(Ext.Window, {
			//构造函数
			constructor : function(_cfg) {
				Ext.applyIf(this, _cfg);
				//必须先初始化组件
				this.initUIComponents();
				SlPunishDetailForm.superclass.constructor.call(this, {
							id : 'SlPunishDetailFormWin',
							layout : 'fit',
							items : this.formPanel,
							modal : true,
							height : 400,
							width : 500,
							maximizable : true,
							title : '[SlPunishDetail]详细信息',
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
							//id : 'SlPunishDetailForm',
							defaults : {
								anchor : '96%,96%'
							},
							defaultType : 'textfield',
							items : [{
								name : 'slPunishDetail.punishDetailId',
								xtype : 'hidden',
								value : this.punishDetailId == null ? '' : this.punishDetailId
							}
																																																	,{
																fieldLabel : '资金流水表id',	
								 								name : 'slPunishDetail.fundQlideId'
								 																,xtype:'numberfield'
								 							}
																																										,{
																fieldLabel : '',	
								 								name : 'slPunishDetail.punishInterestId'
								 																,xtype:'numberfield'
								 							}
																																										,{
																fieldLabel : '操作时间',	
								 								name : 'slPunishDetail.operTime'
								 																,xtype:'datefield',
								format:'Y-m-d',
								value:new Date()
								 							}
																																										,{
																fieldLabel : '',	
								 								name : 'slPunishDetail.afterMoney'
								 								 							}
																																										,{
																fieldLabel : '',	
								 								name : 'slPunishDetail.factDate'
								 																,xtype:'datefield',
								format:'Y-m-d',
								value:new Date()
								 							}
																																										,{
																fieldLabel : '',	
								 								name : 'slPunishDetail.transactionType'
								 																 								,maxLength: 50
								 							}
																																										,{
																fieldLabel : '',	
								 								name : 'slPunishDetail.checkuser'
								 																 								,maxLength: 50
								 							}
																																										,{
																fieldLabel : '',	
								 								name : 'slPunishDetail.iscancel'
								 																,xtype:'numberfield'
								 							}
																																										,{
																fieldLabel : '',	
								 								name : 'slPunishDetail.cancelremark'
								 																 								,maxLength: 255
								 							}
																																			]
						});
				//加载表单对应的数据	
				if (this.punishDetailId != null && this.punishDetailId != 'undefined') {
					this.formPanel.loadData({
								url : __ctxPath + '/creditFlow.finance/getSlPunishDetail.do?punishDetailId='+ this.punishDetailId,
								root : 'data',
								preName : 'slPunishDetail'
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
						url:__ctxPath + '/creditFlow.finance/saveSlPunishDetail.do',
						callback:function(fp,action){
							var gridPanel = Ext.getCmp('SlPunishDetailGrid');
							if (gridPanel != null) {
								gridPanel.getStore().reload();
							}
							this.close();
						}
					}
				);
			}//end of save

		});