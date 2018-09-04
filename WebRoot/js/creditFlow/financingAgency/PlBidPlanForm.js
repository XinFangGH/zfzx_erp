/**
 * @author 
 * @createtime 
 * @class PlBidPlanForm
 * @extends Ext.Window
 * @description PlBidPlan表单
 * @company 智维软件
 */
PlBidPlanForm = Ext.extend(Ext.Window, {
			//构造函数
			constructor : function(_cfg) {
				Ext.applyIf(this, _cfg);
				//必须先初始化组件
				this.initUIComponents();
				
				PlBidPlanForm.superclass.constructor.call(this, {
							id : 'PlBidPlanFormWin',
							layout : 'fit',
							items : this.formPanel,
							modal : true,
							height : 400,
							width : 500,
							maximizable : true,
							title : '[PlBidPlan]详细信息',
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
							//id : 'PlBidPlanForm',
							defaults : {
								anchor : '96%,96%'
							},
							defaultType : 'textfield',
							items : [{
								name : 'plBidPlan.bidId',
								xtype : 'hidden',
								value : this.bidId == null ? '' : this.bidId
							}
																																																	,{
																fieldLabel : '招标项目名称',	
								 								name : 'plBidPlan.bidProName'
								 																 								,maxLength: 255
								 							}
																																										,{
																fieldLabel : '招标项目编号',	
								 								name : 'plBidPlan.bidProNumber'
								 																 								,maxLength: 100
								 							}
																																										,{
																fieldLabel : '招标类型',	
								 								name : 'plBidPlan.biddingTypeId'
								 																,xtype:'numberfield'
								 							}
																																										,{
																fieldLabel : '项目类型  企业直投  B_Dir  企业 债权 B_Or  个人直投 P_Dir 个人债权 P_Or',	
								 								name : 'plBidPlan.proType'
								 																 								,maxLength: 50
								 							}
																																										,{
																fieldLabel : 'projId',	
								 								name : 'plBidPlan.proId'
								 																,xtype:'numberfield'
								 							}
																																										,{
																fieldLabel : '本招标金额',	
								 								name : 'plBidPlan.bidMoney'
								 								 							}
																																										,{
																fieldLabel : '本招标金额所在项目比率',	
								 								name : 'plBidPlan.bidMoneyScale'
								 																,xtype:'numberfield'
								 							}
																																										,{
																fieldLabel : '投资起点金额',	
								 								name : 'plBidPlan.startMoney'
								 								 							}
																																										,{
																fieldLabel : '递增金额',	
								 								name : 'plBidPlan.riseMoney'
								 								 							}
																																										,{
																fieldLabel : '',	
								 								name : 'plBidPlan.createtime'
								 																,xtype:'datefield',
								format:'Y-m-d',
								value:new Date()
								 							}
																																										,{
																fieldLabel : '',	
								 								name : 'plBidPlan.updatetime'
								 																,xtype:'datefield',
								format:'Y-m-d',
								value:new Date()
								 							}
																																										,{
																fieldLabel : '状态 （0 未发布 1招标中 2已齐标 3已流标-1手动关闭）',	
								 								name : 'plBidPlan.state'
								 																,xtype:'numberfield'
								 							}
																																										,{
																fieldLabel : '起息日类型（0投标日—+1 ；1投标截至日+1 ；2标满日+1）',	
								 								name : 'plBidPlan.startInterestType'
								 																,xtype:'numberfield'
								 							}
																																										,{
																fieldLabel : '招标开发期限（小时为单位）',	
								 								name : 'plBidPlan.bidStartTime'
								 																,xtype:'numberfield'
								 							}
																																										,{
																fieldLabel : '开始投标时间',	
								 								name : 'plBidPlan.publishSingeTime'
								 																,xtype:'datefield',
								format:'Y-m-d',
								value:new Date()
								 							}
																																										,{
																fieldLabel : '招标截至日期',	
								 								name : 'plBidPlan.bidEndTime'
								 																,xtype:'datefield',
								format:'Y-m-d',
								value:new Date()
								 							}
																																										,{
																fieldLabel : '招标说明',	
								 								name : 'plBidPlan.bidRemark'
								 																 								,xtype:'textarea'
								 								,maxLength: 65535
								 							}
																																										,{
																fieldLabel : '生成路径',	
								 								name : 'plBidPlan.htmlPath'
								 																 								,maxLength: 255
								 							}
																																			]
						});
				//加载表单对应的数据	
				if (this.bidId != null && this.bidId != 'undefined') {
					this.formPanel.loadData({
								url : __ctxPath + '/creditFlow.financingAgency/getPlBidPlan.do?bidId='+ this.bidId,
								root : 'data',
								preName : 'plBidPlan'
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
						url:__ctxPath + '/creditFlow.financingAgency/savePlBidPlan.do',
						callback:function(fp,action){
							var gridPanel = Ext.getCmp('PlBidPlanGrid');
							if (gridPanel != null) {
								gridPanel.getStore().reload();
							}
							this.close();
						}
					}
				);
			}//end of save

		});