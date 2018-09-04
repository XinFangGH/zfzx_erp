/**
 * @author 
 * @createtime 
 * @class FormFieldForm
 * @extends Ext.Window
 * @description FormField表单
 * @company 智维软件
 */
FormFieldForm = Ext.extend(Ext.Window, {
			//构造函数
			constructor : function(_cfg) {
				Ext.applyIf(this, _cfg);
				//必须先初始化组件
				this.initUIComponents();
				FormFieldForm.superclass.constructor.call(this, {
							id : 'FormFieldFormWin',
							layout : 'fit',
							items : this.formPanel,
							modal : true,
							height : 400,
							width : 500,
							maximizable : true,
							title : '[FormField]详细信息',
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
							//id : 'FormFieldForm',
							defaults : {
								anchor : '96%,96%'
							},
							defaultType : 'textfield',
							items : [{
								name : 'formField.fieldId',
								xtype : 'hidden',
								value : this.fieldId == null ? '' : this.fieldId
							}
																																																	,{
																fieldLabel : '',	
								 								name : 'formField.tableId'
								 																,xtype:'numberfield'
								 							}
																																										,{
																fieldLabel : '',	
								 								name : 'formField.fieldName'
								 																 								,maxLength: 128
								 							}
																																										,{
																fieldLabel : '',	
								 								name : 'formField.fieldType'
								 																 								,maxLength: 128
								 							}
																																										,{
																fieldLabel : '',	
								 								name : 'formField.isRequired'
								 																,xtype:'numberfield'
								 							}
																																										,{
																fieldLabel : '',	
								 								name : 'formField.fieldSize'
								 																,xtype:'numberfield'
								 							}
																																										,{
																fieldLabel : '',	
								 								name : 'formField.fieldDscp'
								 																 								,xtype:'textarea'
								 								,maxLength: 1024
								 							}
																																										,{
																fieldLabel : '',	
								 								name : 'formField.isPrimary'
								 																,xtype:'numberfield'
								 							}
																																										,{
																fieldLabel : '',	
								 								name : 'formField.foreignKey'
								 																 								,maxLength: 64
								 							}
																																										,{
																fieldLabel : '',	
								 								name : 'formField.foreignTable'
								 																 								,maxLength: 64
								 							}
																																										,{
																fieldLabel : '',	
								 								name : 'formField.isList'
								 																,xtype:'numberfield'
								 							}
																																										,{
																fieldLabel : '',	
								 								name : 'formField.isQuery'
								 																,xtype:'numberfield'
								 							}
																																			]
						});
				//加载表单对应的数据	
				if (this.fieldId != null && this.fieldId != 'undefined') {
					this.formPanel.loadData({
								url : __ctxPath + '/arch/getFormField.do?fieldId='+ this.fieldId,
								root : 'data',
								preName : 'formField'
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
						url:__ctxPath + '/flow/saveFormField.do',
						callback:function(fp,action){
							var gridPanel = Ext.getCmp('FormFieldGrid');
							if (gridPanel != null) {
								gridPanel.getStore().reload();
							}
							this.close();
						}
					}
				);
			}//end of save

		});