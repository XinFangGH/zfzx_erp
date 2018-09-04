/**
 * @author 
 * @createtime 
 * @class SlAccruedForm
 * @extends Ext.Window
 * @description SlAccrued表单
 * @company 智维软件
 */
SlAccruedForm = Ext.extend(Ext.Window, {
			//构造函数
			constructor : function(_cfg) {
				Ext.applyIf(this, _cfg);
				//必须先初始化组件
				this.initUIComponents();
				SlAccruedForm.superclass.constructor.call(this, {
							id : 'SlAccruedFormWin',
							layout : 'fit',
							items : this.formPanel,
							modal : true,
							height : 400,
							width : 500,
							maximizable : true,
							title : '[SlAccrued]详细信息',
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
							//id : 'SlAccruedForm',
							defaults : {
								anchor : '96%,96%'
							},
							defaultType : 'textfield',
							items : [{
								name : 'slAccrued.accruedId',
								xtype : 'hidden',
								value : this.accruedId == null ? '' : this.accruedId
							}
																																																	,{
																fieldLabel : '',	
								 								name : 'slAccrued.projectId'
								 																,xtype:'numberfield'
								 							}
																																										,{
																fieldLabel : '',	
								 								name : 'slAccrued.businessType'
								 																 								,maxLength: 30
								 							}
																																										,{
																fieldLabel : '实际到帐日/实际还款日',	
								 								name : 'slAccrued.factDate'
								 																,xtype:'datefield',
								format:'Y-m-d',
								value:new Date()
								 							}
																																										,{
																fieldLabel : '',	
								 								name : 'slAccrued.accruedDate'
								 																,xtype:'datefield',
								format:'Y-m-d',
								value:new Date()
								 							}
																																										,{
																fieldLabel : '',	
								 								name : 'slAccrued.accruedMoney'
								 								 							}
																																			]
						});
				//加载表单对应的数据	
				if (this.accruedId != null && this.accruedId != 'undefined') {
					this.formPanel.loadData({
								url : __ctxPath + '/creditFlow.finance/getSlAccrued.do?accruedId='+ this.accruedId,
								root : 'data',
								preName : 'slAccrued'
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
						url:__ctxPath + '/creditFlow.finance/saveSlAccrued.do',
						callback:function(fp,action){
							var gridPanel = Ext.getCmp('SlAccruedGrid');
							if (gridPanel != null) {
								gridPanel.getStore().reload();
							}
							this.close();
						}
					}
				);
			}//end of save

		});