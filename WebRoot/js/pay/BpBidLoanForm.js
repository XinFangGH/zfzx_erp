/**
 * @author 
 * @createtime 
 * @class BpBidLoanForm
 * @extends Ext.Window
 * @description BpBidLoan表单
 * @company 智维软件
 */
BpBidLoanForm = Ext.extend(Ext.Window, {
			//构造函数
			constructor : function(_cfg) {
				Ext.applyIf(this, _cfg);
				//必须先初始化组件
				this.initUIComponents();
				BpBidLoanForm.superclass.constructor.call(this, {
							id : 'BpBidLoanFormWin',
							layout : 'fit',
							items : this.formPanel,
							modal : true,
							height : 400,
							width : 500,
							maximizable : true,
							title : '[BpBidLoan]详细信息',
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
							//id : 'BpBidLoanForm',
							defaults : {
								anchor : '96%,96%'
							},
							defaultType : 'textfield',
							items : [{
								name : 'bpBidLoan.bidLoanId',
								xtype : 'hidden',
								value : this.bidLoanId == null ? '' : this.bidLoanId
							}
																																																	,{
																fieldLabel : '第三方支付平台id',	
								 								name : 'bpBidLoan.pId'
								 																,xtype:'numberfield'
								 							}
																																										,{
																fieldLabel : '乾多多流水号',	
								 								name : 'bpBidLoan.loanNo'
								 																 								,maxLength: 50
								 							}
																																										,{
																fieldLabel : '网贷平台订单号',	
								 								name : 'bpBidLoan.orderNo'
								 																 								,maxLength: 50
								 							}
																																										,{
																fieldLabel : '网贷平台标号',	
								 								name : 'bpBidLoan.batchNo'
								 																 								,maxLength: 50
								 							}
																																										,{
																fieldLabel : '金额',	
								 								name : 'bpBidLoan.amount'
								 																,xtype:'numberfield'
								 							}
																																										,{
																fieldLabel : '用途',	
								 								name : 'bpBidLoan.transferName'
								 																 								,maxLength: 200
								 							}
																																										,{
																fieldLabel : '备注',	
								 								name : 'bpBidLoan.remark'
								 																 								,maxLength: 200
								 							}
																																										,{
																fieldLabel : '付款人标识',	
								 								name : 'bpBidLoan.loanOutFlag'
								 																 								,maxLength: 10
								 							}
																																										,{
																fieldLabel : '收款人标识',	
								 								name : 'bpBidLoan.loanInFlag'
								 																 								,maxLength: 10
								 							}
																																										,{
																fieldLabel : '状态 （1资金冻结中 2 已完成付款3付款失败）',	
								 								name : 'bpBidLoan.state'
								 																,xtype:'numberfield'
								 							}
																																			]
						});
				//加载表单对应的数据	
				if (this.bidLoanId != null && this.bidLoanId != 'undefined') {
					this.formPanel.loadData({
								url : __ctxPath + '/pay/getBpBidLoan.do?bidLoanId='+ this.bidLoanId,
								root : 'data',
								preName : 'bpBidLoan'
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
						url:__ctxPath + '/pay/saveBpBidLoan.do',
						callback:function(fp,action){
							var gridPanel = Ext.getCmp('BpBidLoanGrid');
							if (gridPanel != null) {
								gridPanel.getStore().reload();
							}
							this.close();
						}
					}
				);
			}//end of save

		});