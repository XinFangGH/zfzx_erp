/**
 * @author 
 * @createtime 
 * @class BpFinanceApplyUserForm
 * @extends Ext.Window
 * @description BpFinanceApplyUser表单
 * @company 智维软件
 */
BpFinanceApplyUserForm = Ext.extend(Ext.Window, {
			//构造函数
			constructor : function(_cfg) {
				Ext.applyIf(this, _cfg);
				//必须先初始化组件
				this.initUIComponents();
				BpFinanceApplyUserForm.superclass.constructor.call(this, {
							id : 'BpFinanceApplyUserFormWin',
							layout : 'fit',
							items : this.formPanel,
							modal : true,
							height : 400,
							width : 500,
							maximizable : true,
							title : '[BpFinanceApplyUser]详细信息',
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
							//id : 'BpFinanceApplyUserForm',
							defaults : {
								anchor : '96%,96%'
							},
							defaultType : 'textfield',
							items : [{
								name : 'bpFinanceApplyUser.loanId',
								xtype : 'hidden',
								value : this.loanId == null ? '' : this.loanId
							}
																																																	,{
																fieldLabel : '贷款产品表ID',	
								 								name : 'bpFinanceApplyUser.productId'
								 																,xtype:'numberfield'
								 							}
																																										,{
																fieldLabel : '贷款产品名称',	
								 								name : 'bpFinanceApplyUser.productName'
								 																 								,maxLength: 255
								 							}
																																										,{
																fieldLabel : '注册用户ID',	
								 								name : 'bpFinanceApplyUser.userID'
								 																,xtype:'numberfield'
								 							}
																																										,{
																fieldLabel : '借款标题',	
								 								name : 'bpFinanceApplyUser.loanTitle'
								 																 								,maxLength: 255
								 							}
																																										,{
																fieldLabel : '借款金额',	
								 								name : 'bpFinanceApplyUser.loanMoney'
								 																 								,maxLength: 255
								 							}
																																										,{
																fieldLabel : '借款期限',	
								 								name : 'bpFinanceApplyUser.loanTimeLen'
								 																 								,maxLength: 255
								 							}
																																										,{
																fieldLabel : '借款描述',	
								 								name : 'bpFinanceApplyUser.remark'
								 																 								,maxLength: 255
								 							}
																																										,{
																fieldLabel : '年化利率',	
								 								name : 'bpFinanceApplyUser.expectAccrual'
								 																 								,maxLength: 255
								 							}
																																										,{
																fieldLabel : '还款方式',	
								 								name : 'bpFinanceApplyUser.payIntersetWay'
								 																 								,maxLength: 255
								 							}
																																										,{
																fieldLabel : '借款用途',	
								 								name : 'bpFinanceApplyUser.loanUse'
								 																 								,maxLength: 80
								 							}
																																										,{
																fieldLabel : '每月还本金及利息',	
								 								name : 'bpFinanceApplyUser.monthlyInterest'
								 																 								,maxLength: 255
								 							}
																																										,{
																fieldLabel : '每月交借款管理费',	
								 								name : 'bpFinanceApplyUser.monthlyCharge'
								 																 								,maxLength: 255
								 							}
																																										,{
																fieldLabel : '期初服务费',	
								 								name : 'bpFinanceApplyUser.startCharge'
								 																 								,maxLength: 255
								 							}
																																										,{
																fieldLabel : '申请状态',	
								 								name : 'bpFinanceApplyUser.state'
								 																 								,maxLength: 5
								 							}
																																										,{
																fieldLabel : '借款申请时间',	
								 								name : 'bpFinanceApplyUser.createTime'
								 																 								,maxLength: 50
								 							}
																																			]
						});
				//加载表单对应的数据	
				if (this.loanId != null && this.loanId != 'undefined') {
					this.formPanel.loadData({
								url : __ctxPath + '/p2p//getBpFinanceApplyUser.do?loanId='+ this.loanId,
								root : 'data',
								preName : 'bpFinanceApplyUser'
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
						url:__ctxPath + '/p2p//saveBpFinanceApplyUser.do',
						callback:function(fp,action){
							var gridPanel = Ext.getCmp('BpFinanceApplyUserGrid');
							if (gridPanel != null) {
								gridPanel.getStore().reload();
							}
							this.close();
						}
					}
				);
			}//end of save

		});