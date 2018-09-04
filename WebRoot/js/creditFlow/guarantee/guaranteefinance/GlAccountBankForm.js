/**
 * @author 
 * @createtime 
 * @class GlAccountBankForm
 * @extends Ext.Window
 * @description GlAccountBank表单
 * @company 智维软件
 */
GlAccountBankForm = Ext.extend(Ext.Window, {
			//构造函数
			constructor : function(_cfg) {
				Ext.applyIf(this, _cfg);
				//必须先初始化组件
				this.initUIComponents();
				GlAccountBankForm.superclass.constructor.call(this, {
							id : 'GlAccountBankFormWin',
							layout : 'fit',
							items : this.formPanel,
							modal : true,
							height : 400,
							width : 500,
							maximizable : true,
							title : '[GlAccountBank]详细信息',
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
							//id : 'GlAccountBankForm',
							defaults : {
								anchor : '96%,96%'
							},
							defaultType : 'textfield',
							items : [{
								name : 'glAccountBank.id',
								xtype : 'hidden',
								value : this.id == null ? '' : this.id
							}
																																																	,{
																fieldLabel : '授信银行最上级id',	
								 								name : 'glAccountBank.bankParentId'
								 																,xtype:'numberfield'
								 							}
																																										,{
																fieldLabel : '授信额度(万元)',	
								 								name : 'glAccountBank.authorizationMoney'
								 																,xtype:'numberfield'
								 							}
																																										,{
																fieldLabel : '创建时间',	
								 								name : 'glAccountBank.createDate'
								 																,xtype:'datefield',
								format:'Y-m-d',
								value:new Date()
								 							}
																																										,{
																fieldLabel : '备注',	
								 								name : 'glAccountBank.remark'
								 																 								,maxLength: 255
								 							}
																																										,{
																fieldLabel : '',	
								 								name : 'glAccountBank.leaf'
								 																,xtype:'numberfield'
								 							}
																																										,{
																fieldLabel : '',	
								 								name : 'glAccountBank.text'
								 																 								,maxLength: 255
								 							}
																																										,{
																fieldLabel : '',	
								 								name : 'glAccountBank.serviceTypeBank'
								 																 								,maxLength: 100
								 							}
																																			]
						});
				//加载表单对应的数据	
				if (this.id != null && this.id != 'undefined') {
					this.formPanel.loadData({
								url : __ctxPath + '/creditFlow.guarantee.guaranteefinance/getGlAccountBank.do?id='+ this.id,
								root : 'data',
								preName : 'glAccountBank'
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
						url:__ctxPath + '/creditFlow.guarantee.guaranteefinance/saveGlAccountBank.do',
						callback:function(fp,action){
							var gridPanel = Ext.getCmp('GlAccountBankGrid');
							if (gridPanel != null) {
								gridPanel.getStore().reload();
							}
							this.close();
						}
					}
				);
			}//end of save

		});