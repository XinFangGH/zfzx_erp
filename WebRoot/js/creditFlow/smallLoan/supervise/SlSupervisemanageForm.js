/**
 * @author 
 * @createtime 
 * @class SlSupervisemanageForm
 * @extends Ext.Window
 * @description SlSupervisemanage表单
 * @company 智维软件
 */
SlSupervisemanageForm = Ext.extend(Ext.Window, {
			//构造函数
			constructor : function(_cfg) {
				Ext.applyIf(this, _cfg);
				//必须先初始化组件
				this.initUIComponents();
				SlSupervisemanageForm.superclass.constructor.call(this, {
							id : 'SlSupervisemanageFormWin',
							layout : 'fit',
							items : this.formPanel,
							modal : true,
							height : 400,
							width : 500,
							maximizable : true,
							title : '[SlSupervisemanage]详细信息',
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
							//id : 'SlSupervisemanageForm',
							defaults : {
								anchor : '96%,96%'
							},
							defaultType : 'textfield',
							items : [{
								name : 'slSupervisemanage.superviseManageId',
								xtype : 'hidden',
								value : this.superviseManageId == null ? '' : this.superviseManageId
							}
																																																	,{
																fieldLabel : '划计监管人',	
								 								name : 'slSupervisemanage.designSuperviseManagers'
								 																 								,maxLength: 200
								 							}
																																										,{
																fieldLabel : '划计监管时间',	
								 								name : 'slSupervisemanage.designSuperviseManageTime'
								 																,xtype:'datefield',
								format:'Y-m-d',
								value:new Date()
								 							}
																																										,{
																fieldLabel : '划计监管备注',	
								 								name : 'slSupervisemanage.designSuperviseManageRemark'
								 																 								,xtype:'textarea'
								 								,maxLength: 65535
								 							}
																																										,{
																fieldLabel : '指派任务人',	
								 								name : 'slSupervisemanage.Designee'
								 																,xtype:'numberfield'
								 							}
																																										,{
																fieldLabel : '监管人',	
								 								name : 'slSupervisemanage.SuperviseManager'
								 																,xtype:'numberfield'
								 							}
																																										,{
																fieldLabel : '监管时间',	
								 								name : 'slSupervisemanage.SuperviseManageTime'
								 																,xtype:'datefield',
								format:'Y-m-d',
								value:new Date()
								 							}
																																										,{
																fieldLabel : '管监方式',	
								 								name : 'slSupervisemanage.SuperviseManageMode'
								 																,xtype:'numberfield'
								 							}
																																										,{
																fieldLabel : '管监意见',	
								 								name : 'slSupervisemanage.SuperviseManageOpinion'
								 																,xtype:'numberfield'
								 							}
																																										,{
																fieldLabel : '监管备注',	
								 								name : 'slSupervisemanage.SuperviseManageRemark'
								 																 								,xtype:'textarea'
								 								,maxLength: 65535
								 							}
																																			]
						});
				//加载表单对应的数据	
				if (this.superviseManageId != null && this.superviseManageId != 'undefined') {
					this.formPanel.loadData({
								url : __ctxPath + '/creditFlow.smallLoan.supervise/getSlSupervisemanage.do?superviseManageId='+ this.superviseManageId,
								root : 'data',
								preName : 'slSupervisemanage'
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
						url:__ctxPath + '/creditFlow.smallLoan.supervise/saveSlSupervisemanage.do',
						callback:function(fp,action){
							var gridPanel = Ext.getCmp('SlSupervisemanageGrid');
							if (gridPanel != null) {
								gridPanel.getStore().reload();
							}
							this.close();
						}
					}
				);
			}//end of save

		});