/**
 * @author 
 * @createtime 
 * @class ProHandleCompForm
 * @extends Ext.Window
 * @description ProHandleComp表单
 * @company 智维软件
 */
ProHandleCompForm = Ext.extend(Ext.Window, {
			//构造函数
			constructor : function(_cfg) {
				Ext.applyIf(this, _cfg);
				//必须先初始化组件
				this.initUIComponents();
				ProHandleCompForm.superclass.constructor.call(this, {
							id : 'ProHandleCompFormWin',
							layout : 'fit',
							items : this.formPanel,
							modal : true,
							height : 400,
							width : 500,
							maximizable : true,
							title : '[ProHandleComp]详细信息',
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
							//id : 'ProHandleCompForm',
							defaults : {
								anchor : '96%,96%'
							},
							defaultType : 'textfield',
							items : [{
								name : 'proHandleComp.handleId',
								xtype : 'hidden',
								value : this.handleId == null ? '' : this.handleId
							}
																																																	,{
																fieldLabel : 'deployId',
								 								name : 'proHandleComp.deployId'
																,allowBlank:false
								 																 								,maxLength: 128
								 							}
																																										,{
																fieldLabel : 'activityName',
								 								name : 'proHandleComp.activityName'
								 																 								,maxLength: 128
								 							}
																																										,{
																fieldLabel : 'tranName',
								 								name : 'proHandleComp.tranName'
								 																 								,maxLength: 128
								 							}
																																										,{
																fieldLabel : 'eventName',
								 								name : 'proHandleComp.eventName'
								 																 								,maxLength: 128
								 							}
																																										,{
																fieldLabel : 'eventLevel',
								 								name : 'proHandleComp.eventLevel'
								 																,xtype:'numberfield'
								 							}
																																										,{
																fieldLabel : 'exeCode',
								 								name : 'proHandleComp.exeCode'
								 																 								,xtype:'textarea'
								 								,maxLength: 4000
								 							}
																																										,{
																fieldLabel : 'handleType',
								 								name : 'proHandleComp.handleType'
								 																,xtype:'numberfield'
								 							}
																																			]
						});
				//加载表单对应的数据	
				if (this.handleId != null && this.handleId != 'undefined') {
					this.formPanel.loadData({
								url : __ctxPath + '/flow/getProHandleComp.do?handleId='+ this.handleId,
								root : 'data',
								preName : 'proHandleComp'
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
						url:__ctxPath + '/flow/saveProHandleComp.do',
						callback:function(fp,action){
							var gridPanel = Ext.getCmp('ProHandleCompGrid');
							if (gridPanel != null) {
								gridPanel.getStore().reload();
							}
							this.close();
						}
					}
				);
			}//end of save

		});