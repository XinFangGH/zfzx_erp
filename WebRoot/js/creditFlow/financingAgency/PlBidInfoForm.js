/**
 * @author 
 * @createtime 
 * @class PlBidInfoForm
 * @extends Ext.Window
 * @description PlBidInfo表单
 * @company 智维软件
 */
PlBidInfoForm = Ext.extend(Ext.Window, {
			//构造函数
			constructor : function(_cfg) {
				Ext.applyIf(this, _cfg);
				//必须先初始化组件
				this.initUIComponents();
				PlBidInfoForm.superclass.constructor.call(this, {
							id : 'PlBidInfoFormWin',
							layout : 'fit',
							items : this.formPanel,
							modal : true,
							height : 400,
							width : 500,
							maximizable : true,
							title : '[PlBidInfo]详细信息',
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
							//id : 'PlBidInfoForm',
							defaults : {
								anchor : '96%,96%'
							},
							defaultType : 'textfield',
							items : [{
								name : 'plBidInfo.id',
								xtype : 'hidden',
								value : this.id == null ? '' : this.id
							}
																																																	,{
																fieldLabel : '投资人账号',	
								 								name : 'plBidInfo.userName'
								 																 								,maxLength: 50
								 							}
																																										,{
																fieldLabel : '投资人id',	
								 								name : 'plBidInfo.userId'
								 																,xtype:'numberfield'
								 							}
																																										,{
																fieldLabel : '招标计划id',	
								 								name : 'plBidInfo.bidId'
								 																,xtype:'numberfield'
								 							}
																																										,{
																fieldLabel : '招标计划名称',	
								 								name : 'plBidInfo.bidName'
								 																 								,maxLength: 150
								 							}
																																										,{
																fieldLabel : '投标金额',	
								 								name : 'plBidInfo.userMoney'
								 								 							}
																																										,{
																fieldLabel : '投标日期',	
								 								name : 'plBidInfo.bidtime'
								 																,xtype:'datefield',
								format:'Y-m-d',
								value:new Date()
								 							}
																																			]
						});
				//加载表单对应的数据	
				if (this.id != null && this.id != 'undefined') {
					this.formPanel.loadData({
								url : __ctxPath + '/creditFlow.financingAgency/getPlBidInfo.do?id='+ this.id,
								root : 'data',
								preName : 'plBidInfo'
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
						url:__ctxPath + '/creditFlow.financingAgency/savePlBidInfo.do',
						callback:function(fp,action){
							var gridPanel = Ext.getCmp('PlBidInfoGrid');
							if (gridPanel != null) {
								gridPanel.getStore().reload();
							}
							this.close();
						}
					}
				);
			}//end of save

		});