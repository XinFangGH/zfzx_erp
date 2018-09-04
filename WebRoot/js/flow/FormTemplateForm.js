/**
 * @author 
 * @createtime 
 * @class FormTemplateForm
 * @extends Ext.Window
 * @description FormTemplate表单
 * @company 智维软件
 */
FormTemplateForm = Ext.extend(Ext.Window, {
			//构造函数
			constructor : function(_cfg) {
				Ext.applyIf(this, _cfg);
				//必须先初始化组件
				this.initUIComponents();
				FormTemplateForm.superclass.constructor.call(this, {
							id : 'FormTemplateFormWin',
							layout : 'fit',
							items : this.formPanel,
							modal : true,
							height : 400,
							width : 500,
							maximizable : true,
							title : '[FormTemplate]详细信息',
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
							//id : 'FormTemplateForm',
							defaults : {
								anchor : '96%,96%'
							},
							defaultType : 'textfield',
							items : [{
								name : 'formTemplate.templateId',
								xtype : 'hidden',
								value : this.templateId == null ? '' : this.templateId
							}
																																																	,{
																fieldLabel : '映射ID',	
								 								name : 'formTemplate.mappingId'
								 																,xtype:'numberfield'
								 							}
																																										,{
																fieldLabel : '节点名称',	
								 								name : 'formTemplate.nodeName'
																,allowBlank:false
								 																 								,maxLength: 128
								 							}
																																										,{
																fieldLabel : '模板内容',	
								 								name : 'formTemplate.tempContent'
								 																 								,xtype:'textarea'
								 								,maxLength: 65535
								 							}
																																										,{
																fieldLabel : '',	
								 								name : 'formTemplate.extDef'
								 																 								,xtype:'textarea'
								 								,maxLength: 65535
								 							}
																																			]
						});
				//加载表单对应的数据	
				if (this.templateId != null && this.templateId != 'undefined') {
					this.formPanel.loadData({
								url : __ctxPath + '/arch/getFormTemplate.do?templateId='+ this.templateId,
								root : 'data',
								preName : 'formTemplate'
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
						url:__ctxPath + '/flow/saveFormTemplate.do',
						callback:function(fp,action){
							var gridPanel = Ext.getCmp('FormTemplateGrid');
							if (gridPanel != null) {
								gridPanel.getStore().reload();
							}
							this.close();
						}
					}
				);
			}//end of save

		});