/**
 * @author 
 * @createtime 
 * @class GlAccountBankCautionmoneyForm
 * @extends Ext.Window
 * @description GlAccountBankCautionmoney表单
 * @company 智维软件
 */
GlAccountBankCautionmoneyForm = Ext.extend(Ext.Window, {
			//构造函数
			constructor : function(_cfg) {
				Ext.applyIf(this, _cfg);
				//必须先初始化组件
				this.initUIComponents();
				GlAccountBankCautionmoneyForm.superclass.constructor.call(this, {
							id : 'GlAccountBankCautionmoneyFormWin',
							layout : 'fit',
							items : this.formPanel,
							modal : true,
							height : 400,
							width : 500,
							maximizable : true,
							title : '[GlAccountBankCautionmoney]详细信息',
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
							//id : 'GlAccountBankCautionmoneyForm',
							defaults : {
								anchor : '96%,96%'
							},
							defaultType : 'textfield',
							items : [{
								name : 'glAccountBankCautionmoney.id',
								xtype : 'hidden',
								value : this.id == null ? '' : this.id
							}
																																																	,{
																fieldLabel : '授信银行表主id',	
								 								name : 'glAccountBankCautionmoney.parentId'
								 																 								,maxLength: 40
								 							}
																																										,{
																fieldLabel : '开户支行id',	
								 								name : 'glAccountBankCautionmoney.bankBranchId'
								 																,xtype:'numberfield'
								 							}
																																										,{
																fieldLabel : '保证金账户id',	
								 								name : 'glAccountBankCautionmoney.accountId'
								 																 								,maxLength: 40
								 							}
																																										,{
																fieldLabel : '保证金账户名称',	
								 								name : 'glAccountBankCautionmoney.text'
								 																 								,maxLength: 255
								 							}
																																										,{
																fieldLabel : '账户创建时间',	
								 								name : 'glAccountBankCautionmoney.createDate'
								 																,xtype:'datefield',
								format:'Y-m-d',
								value:new Date()
								 							}
																																										,{
																fieldLabel : '备注',	
								 								name : 'glAccountBankCautionmoney.remark'
								 																 								,maxLength: 255
								 							}
																																										,{
																fieldLabel : '',	
								 								name : 'glAccountBankCautionmoney.leaf'
								 																,xtype:'numberfield'
								 							}
																																										,{
																fieldLabel : '',	
								 								name : 'glAccountBankCautionmoney.bankBranchName'
								 																 								,maxLength: 50
								 							}
																																										,{
																fieldLabel : '',	
								 								name : 'glAccountBankCautionmoney.serviceTypeAccount'
								 																 								,maxLength: 100
								 							}
																																			]
						});
				//加载表单对应的数据	
				if (this.id != null && this.id != 'undefined') {
					this.formPanel.loadData({
								url : __ctxPath + '/creditFlow.guarantee.guaranteefinance/getGlAccountBankCautionmoney.do?id='+ this.id,
								root : 'data',
								preName : 'glAccountBankCautionmoney'
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
						url:__ctxPath + '/creditFlow.guarantee.guaranteefinance/saveGlAccountBankCautionmoney.do',
						callback:function(fp,action){
							var gridPanel = Ext.getCmp('GlAccountBankCautionmoneyGrid');
							if (gridPanel != null) {
								gridPanel.getStore().reload();
							}
							this.close();
						}
					}
				);
			}//end of save

		});