/**
 * @author 
 * @createtime 
 * @class WebFinanceApplyUploadsForm
 * @extends Ext.Window
 * @description WebFinanceApplyUploads表单
 * @company 智维软件
 */
WebFinanceApplyUploadsForm = Ext.extend(Ext.Window, {
			//构造函数
			constructor : function(_cfg) {
				Ext.applyIf(this, _cfg);
				//必须先初始化组件
				this.initUIComponents();
				WebFinanceApplyUploadsForm.superclass.constructor.call(this, {
							id : 'WebFinanceApplyUploadsFormWin',
							layout : 'fit',
							items : this.formPanel,
							modal : true,
							height : 400,
							width : 500,
							maximizable : true,
							title : '[WebFinanceApplyUploads]详细信息',
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
							//id : 'WebFinanceApplyUploadsForm',
							defaults : {
								anchor : '96%,96%'
							},
							defaultType : 'textfield',
							items : [{
								name : 'webFinanceApplyUploads.id',
								xtype : 'hidden',
								value : this.id == null ? '' : this.id
							}
																																																	,{
																fieldLabel : '',	
								 								name : 'webFinanceApplyUploads.userID'
								 																,xtype:'numberfield'
								 							}
																																										,{
																fieldLabel : '',	
								 								name : 'webFinanceApplyUploads.materialstype'
								 																 								,maxLength: 20
								 							}
																																										,{
																fieldLabel : '',	
								 								name : 'webFinanceApplyUploads.files'
								 																 								,xtype:'textarea'
								 								,maxLength: 1000
								 							}
																																										,{
																fieldLabel : '',	
								 								name : 'webFinanceApplyUploads.status'
								 																,xtype:'numberfield'
								 							}
																																										,{
																fieldLabel : '',	
								 								name : 'webFinanceApplyUploads.lastuploadtime'
								 																,xtype:'datefield',
								format:'Y-m-d',
								value:new Date()
								 							}
																																			]
						});
				//加载表单对应的数据	
				if (this.id != null && this.id != 'undefined') {
					this.formPanel.loadData({
								url : __ctxPath + '/p2p/getWebFinanceApplyUploads.do?id='+ this.id,
								root : 'data',
								preName : 'webFinanceApplyUploads'
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
						url:__ctxPath + '/p2p/saveWebFinanceApplyUploads.do',
						callback:function(fp,action){
							var gridPanel = Ext.getCmp('WebFinanceApplyUploadsGrid');
							if (gridPanel != null) {
								gridPanel.getStore().reload();
							}
							this.close();
						}
					}
				);
			}//end of save

		});