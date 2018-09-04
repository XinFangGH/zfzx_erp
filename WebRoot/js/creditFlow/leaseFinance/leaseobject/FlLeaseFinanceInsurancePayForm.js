/**
 * @author 
 * @createtime 
 * @class FlLeaseFinanceInsurancePayForm
 * @extends Ext.Window
 * @description FlLeaseFinanceInsurancePay表单
 * @company 智维软件
 */
FlLeaseFinanceInsurancePayForm = Ext.extend(Ext.Window, {
			//构造函数
			constructor : function(_cfg) {
				Ext.applyIf(this, _cfg);
				//必须先初始化组件
				this.initUIComponents();
				FlLeaseFinanceInsurancePayForm.superclass.constructor.call(this, {
							id : 'FlLeaseFinanceInsurancePayFormWin',
							layout : 'fit',
							items : this.formPanel,
							modal : true,
							height : 400,
							width : 500,
							maximizable : true,
							title : '[FlLeaseFinanceInsurancePay]详细信息',
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
							//id : 'FlLeaseFinanceInsurancePayForm',
							defaults : {
								anchor : '96%,96%'
							},
							defaultType : 'textfield',
							items : [{
								name : 'flLeaseFinanceInsurancePay.id',
								xtype : 'hidden',
								value : this.id == null ? '' : this.id
							}
																																																	,{
																fieldLabel : '保单编号',	
								 								name : 'flLeaseFinanceInsurancePay.insuranceCode'
																,allowBlank:false
								 																 								,maxLength: 255
								 							}
																																										,{
																fieldLabel : '保险公司名称',	
								 								name : 'flLeaseFinanceInsurancePay.insuranceCompanyName'
								 																 								,maxLength: 255
								 							}
																																										,{
																fieldLabel : '设备型号',	
								 								name : 'flLeaseFinanceInsurancePay.standardSize'
								 																 								,maxLength: 255
								 							}
																																										,{
																fieldLabel : '出险时间',	
								 								name : 'flLeaseFinanceInsurancePay.outInsuranceDate'
								 																 								,maxLength: 255
								 							}
																																										,{
																fieldLabel : '报案时间',	
								 								name : 'flLeaseFinanceInsurancePay.submitDate'
								 																,xtype:'datefield',
								format:'Y-m-d',
								value:new Date()
								 							}
																																										,{
																fieldLabel : '出险原因',	
								 								name : 'flLeaseFinanceInsurancePay.outInsuranceReason'
								 																 								,maxLength: 255
								 							}
																																										,{
																fieldLabel : '定损金额（元）',	
								 								name : 'flLeaseFinanceInsurancePay.loseMoney'
								 								 							}
																																										,{
																fieldLabel : '偿付金额',	
								 								name : 'flLeaseFinanceInsurancePay.repayMoney'
								 								 							}
																																										,{
																fieldLabel : '理赔状态',	
								 								name : 'flLeaseFinanceInsurancePay.status'
								 																 								,maxLength: 255
								 							}
																																										,{
																fieldLabel : '赔付时间',	
								 								name : 'flLeaseFinanceInsurancePay.payDate'
								 																,xtype:'datefield',
								format:'Y-m-d',
								value:new Date()
								 							}
																																										,{
																fieldLabel : '租赁物Id',	
								 								name : 'flLeaseFinanceInsurancePay.leaseObjectId'
																,allowBlank:false
								 																,xtype:'numberfield'
								 							}
																																			]
						});
				//加载表单对应的数据	
				if (this.id != null && this.id != 'undefined') {
					this.formPanel.loadData({
								url : __ctxPath + '/creditFlow.leaseFinance.leaseobject/getFlLeaseFinanceInsurancePay.do?id='+ this.id,
								root : 'data',
								preName : 'flLeaseFinanceInsurancePay'
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
						url:__ctxPath + '/creditFlow.leaseFinance.leaseobject/saveFlLeaseFinanceInsurancePay.do',
						callback:function(fp,action){
							var gridPanel = Ext.getCmp('FlLeaseFinanceInsurancePayGrid');
							if (gridPanel != null) {
								gridPanel.getStore().reload();
							}
							this.close();
						}
					}
				);
			}//end of save

		});