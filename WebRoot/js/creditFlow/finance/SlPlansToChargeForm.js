/**
 * @author 
 * @createtime 
 * @class SlPlansToChargeForm
 * @extends Ext.Window
 * @description SlPlansToCharge表单
 * @company 智维软件
 */
SlPlansToChargeForm = Ext.extend(Ext.Window, {
			//构造函数
			constructor : function(_cfg) {
				Ext.applyIf(this, _cfg);
				//必须先初始化组件
				this.initUIComponents();
				SlPlansToChargeForm.superclass.constructor.call(this, {
							id : 'SlPlansToChargeFormWin',
							layout : 'fit',
							items : this.formPanel,
							modal : true,
							height : 400,
							width : 500,
							maximizable : true,
							title : '[SlPlansToCharge]详细信息',
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
							//id : 'SlPlansToChargeForm',
							defaults : {
								anchor : '96%,96%'
							},
							defaultType : 'textfield',
							items : [{
								name : 'slPlansToCharge.plansTochargeId',
								xtype : 'hidden',
								value : this.plansTochargeId == null ? '' : this.plansTochargeId
							}
																																																	,{
																fieldLabel : '',	
								 								name : 'slPlansToCharge.name'
								 																 								,maxLength: 255
								 							}
																																										,{
																fieldLabel : '0,公有，1私有',	
								 								name : 'slPlansToCharge.isType'
								 																,xtype:'numberfield'
								 							}
																																										,{
																fieldLabel : '',	
								 								name : 'slPlansToCharge.chargesStandard'
								 								 							}
																																			]
						});
				//加载表单对应的数据	
				if (this.plansTochargeId != null && this.plansTochargeId != 'undefined') {
					this.formPanel.loadData({
								url : __ctxPath + '/creditFlow/finance/getSlPlansToCharge.do?plansTochargeId='+ this.plansTochargeId,
								root : 'data',
								preName : 'slPlansToCharge'
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
						url:__ctxPath + '/creditFlow/finance/saveSlPlansToCharge.do',
						callback:function(fp,action){
							var gridPanel = Ext.getCmp('SlPlansToChargeGrid');
							if (gridPanel != null) {
								gridPanel.getStore().reload();
							}
							this.close();
						}
					}
				);
			}//end of save

		});