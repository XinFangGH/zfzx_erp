/**
 * @author 
 * @createtime 
 * @class GlAccountRecordForm
 * @extends Ext.Window
 * @description GlAccountRecord表单
 * @company 智维软件
 */
GlAccountRecordForm = Ext.extend(Ext.Window, {
			//构造函数
			constructor : function(_cfg) {
				Ext.applyIf(this, _cfg);
				//必须先初始化组件
				this.initUIComponents();
				GlAccountRecordForm.superclass.constructor.call(this, {
							id : 'GlAccountRecordFormWin',
							layout : 'fit',
							items : this.formPanel,
							modal : true,
							height : 400,
							width : 500,
							maximizable : true,
							title : '[GlAccountRecord]详细信息',
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
							//id : 'GlAccountRecordForm',
							defaults : {
								anchor : '96%,96%'
							},
							defaultType : 'textfield',
							items : [{
								name : 'glAccountRecord.id',
								xtype : 'hidden',
								value : this.id == null ? '' : this.id
							}
																																																	,{
																fieldLabel : '',	
								 								name : 'glAccountRecord.cautionAccountId'
								 																,xtype:'numberfield'
								 							}
																																										,{
																fieldLabel : '',	
								 								name : 'glAccountRecord.capitalType'
								 																,xtype:'numberfield'
								 							}
																																										,{
																fieldLabel : '',	
								 								name : 'glAccountRecord.oprateMoney'
								 																,xtype:'numberfield'
								 							}
																																										,{
																fieldLabel : '',	
								 								name : 'glAccountRecord.oprateDate'
								 																,xtype:'datefield',
								format:'Y-m-d',
								value:new Date()
								 							}
																																										,{
																fieldLabel : '',	
								 								name : 'glAccountRecord.handlePerson'
								 																 								,maxLength: 30
								 							}
																																										,{
																fieldLabel : '',	
								 								name : 'glAccountRecord.remark'
								 																 								,maxLength: 255
								 							}
																																										,{
																fieldLabel : '',	
								 								name : 'glAccountRecord.servicetype'
								 																,xtype:'numberfield'
								 							}
																																										,{
																fieldLabel : '',	
								 								name : 'glAccountRecord.projectId'
								 																 								,maxLength: 100
								 							}
																																										,{
																fieldLabel : '',	
								 								name : 'glAccountRecord.csBankCautionMoneyId'
								 																 								,maxLength: 64
								 							}
																																			]
						});
				//加载表单对应的数据	
				if (this.id != null && this.id != 'undefined') {
					this.formPanel.loadData({
								url : __ctxPath + '/creditFlow.guarantee.guaranteefinance/getGlAccountRecord.do?id='+ this.id,
								root : 'data',
								preName : 'glAccountRecord'
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
						url:__ctxPath + '/creditFlow.guarantee.guaranteefinance/saveGlAccountRecord.do',
						callback:function(fp,action){
							var gridPanel = Ext.getCmp('GlAccountRecordGrid');
							if (gridPanel != null) {
								gridPanel.getStore().reload();
							}
							this.close();
						}
					}
				);
			}//end of save

		});