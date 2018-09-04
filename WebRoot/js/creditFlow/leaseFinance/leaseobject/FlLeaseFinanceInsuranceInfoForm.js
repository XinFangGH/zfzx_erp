/**
 * @author 
 * @createtime 
 * @class FlLeaseFinanceInsuranceInfoForm
 * @extends Ext.Window
 * @description FlLeaseFinanceInsuranceInfo表单
 * @company 智维软件
 */
FlLeaseFinanceInsuranceInfoForm = Ext.extend(Ext.Window, {
			//构造函数
			constructor : function(_cfg) {
				Ext.applyIf(this, _cfg);
				//必须先初始化组件
				this.initUIComponents();
				FlLeaseFinanceInsuranceInfoForm.superclass.constructor.call(this, {
							id : 'FlLeaseFinanceInsuranceInfoFormWin',
							layout : 'fit',
							items : this.formPanel,
							modal : true,
							height : 400,
							width : 500,
							maximizable : true,
							title : '[FlLeaseFinanceInsuranceInfo]详细信息',
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
							//id : 'FlLeaseFinanceInsuranceInfoForm',
							defaults : {
								anchor : '96%,96%'
							},
							defaultType : 'textfield',
							items : [{
								name : 'flLeaseFinanceInsuranceInfo.insuranceId',
								xtype : 'hidden',
								value : this.insuranceId == null ? '' : this.insuranceId
							}
																																																	,{
																fieldLabel : '保险名称',	
								 								name : 'flLeaseFinanceInsuranceInfo.insuranceName'
																,allowBlank:false
								 																 								,maxLength: 255
								 							}
																																										,{
																fieldLabel : '保险公司名称',	
								 								name : 'flLeaseFinanceInsuranceInfo.insuranceCompanyName'
								 																 								,maxLength: 255
								 							}
																																										,{
																fieldLabel : '保单编号',	
								 								name : 'flLeaseFinanceInsuranceInfo.insuranceCode'
																,allowBlank:false
								 																 								,maxLength: 255
								 							}
																																										,{
																fieldLabel : '金额（万元）',	
								 								name : 'flLeaseFinanceInsuranceInfo.insuranceMoney'
								 								 							}
																																										,{
																fieldLabel : '保险起始日期',	
								 								name : 'flLeaseFinanceInsuranceInfo.startDate'
								 																,xtype:'datefield',
								format:'Y-m-d',
								value:new Date()
								 							}
																																										,{
																fieldLabel : '保险截止日期',	
								 								name : 'flLeaseFinanceInsuranceInfo.endDate'
								 																,xtype:'datefield',
								format:'Y-m-d',
								value:new Date()
								 							}
																																										,{
																fieldLabel : '保险受益人',	
								 								name : 'flLeaseFinanceInsuranceInfo.insurancePerson'
								 																 								,maxLength: 255
								 							}
																																										,{
																fieldLabel : '备注',	
								 								name : 'flLeaseFinanceInsuranceInfo.insuranceComment'
								 																 								,maxLength: 255
								 							}
																																										,{
																fieldLabel : '融资租赁项目id',	
								 								name : 'flLeaseFinanceInsuranceInfo.proejctId'
																,allowBlank:false
								 																,xtype:'numberfield'
								 							}
																																			]
						});
				//加载表单对应的数据	
				if (this.insuranceId != null && this.insuranceId != 'undefined') {
					this.formPanel.loadData({
								url : __ctxPath + '/creditFlow.leaseFinance.leaseobject/getFlLeaseFinanceInsuranceInfo.do?insuranceId='+ this.insuranceId,
								root : 'data',
								preName : 'flLeaseFinanceInsuranceInfo'
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
						url:__ctxPath + '/creditFlow.leaseFinance.leaseobject/saveFlLeaseFinanceInsuranceInfo.do',
						callback:function(fp,action){
							var gridPanel = Ext.getCmp('FlLeaseFinanceInsuranceInfoGrid');
							if (gridPanel != null) {
								gridPanel.getStore().reload();
							}
							this.close();
						}
					}
				);
			}//end of save

		});