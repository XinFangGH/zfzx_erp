/**
 * @author 
 * @createtime 
 * @class PlThirdInterfaceLogForm
 * @extends Ext.Window
 * @description PlThirdInterfaceLog表单
 * @company 智维软件
 */
PlThirdInterfaceLogForm = Ext.extend(Ext.Window, {
			//构造函数
			constructor : function(_cfg) {
				Ext.applyIf(this, _cfg);
				//必须先初始化组件
				this.initUIComponents();
				PlThirdInterfaceLogForm.superclass.constructor.call(this, {
							id : 'PlThirdInterfaceLogFormWin',
							layout : 'fit',
							items : this.formPanel,
							modal : true,
							height : 400,
							width : 500,
							maximizable : true,
							title : '[PlThirdInterfaceLog]详细信息',
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
							//id : 'PlThirdInterfaceLogForm',
							defaults : {
								anchor : '96%,96%'
							},
							defaultType : 'textfield',
							items : [{
								name : 'plThirdInterfaceLog.id',
								xtype : 'hidden',
								value : this.id == null ? '' : this.id
							}
																																																	,{
																fieldLabel : '接口类型 1 第三方支付 2 短信',	
								 								name : 'plThirdInterfaceLog.typeName'
								 																 								,maxLength: 50
								 							}
																																										,{
																fieldLabel : '类型id 1第三方支付 2 短信',	
								 								name : 'plThirdInterfaceLog.typeId'
								 																,xtype:'numberfield'
								 							}
																																										,{
																fieldLabel : '返回代码',	
								 								name : 'plThirdInterfaceLog.code'
								 																 								,maxLength: 10
								 							}
																																										,{
																fieldLabel : '代码对应 说明',	
								 								name : 'plThirdInterfaceLog.codeMsg'
								 																 								,maxLength: 100
								 							}
																																										,{
																fieldLabel : '返回的完整信息',	
								 								name : 'plThirdInterfaceLog.bigMsg'
								 																 								,xtype:'textarea'
								 								,maxLength: 65535
								 							}
																																										,{
																fieldLabel : '创建时间',	
								 								name : 'plThirdInterfaceLog.createTime'
								 																,xtype:'datefield',
								format:'Y-m-d',
								value:new Date()
								 							}
																																										,{
																fieldLabel : '接口名称',	
								 								name : 'plThirdInterfaceLog.interfaceName'
								 																 								,maxLength: 255
								 							}
																																										,{
																fieldLabel : '操作人',	
								 								name : 'plThirdInterfaceLog.memberId'
								 																,xtype:'numberfield'
								 							}
																																										,{
																fieldLabel : '操作人类型 0 线下 1 线上',	
								 								name : 'plThirdInterfaceLog.memberType'
								 																,xtype:'numberfield'
								 							}
																																			]
						});
				//加载表单对应的数据	
				if (this.id != null && this.id != 'undefined') {
					this.formPanel.loadData({
								url : __ctxPath + '/thirdInterface/getPlThirdInterfaceLog.do?id='+ this.id,
								root : 'data',
								preName : 'plThirdInterfaceLog'
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
						url:__ctxPath + '/thirdInterface/savePlThirdInterfaceLog.do',
						callback:function(fp,action){
							var gridPanel = Ext.getCmp('PlThirdInterfaceLogGrid');
							if (gridPanel != null) {
								gridPanel.getStore().reload();
							}
							this.close();
						}
					}
				);
			}//end of save

		});