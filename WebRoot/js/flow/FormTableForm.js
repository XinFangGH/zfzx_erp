/**
 * @author 
 * @createtime 
 * @class FormTableForm
 * @extends Ext.Window
 * @description FormTable表单
 * @company 智维软件
 */
FormTableForm = Ext.extend(Ext.Window, {
			//构造函数
			constructor : function(_cfg) {
				Ext.applyIf(this, _cfg);
				//必须先初始化组件
				this.initUIComponents();
				FormTableForm.superclass.constructor.call(this, {
							id : 'FormTableFormWin',
							layout : 'fit',
							items : this.formPanel,
							modal : true,
							height : 400,
							width : 500,
							maximizable : true,
							title : '[FormTable]详细信息',
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
							//id : 'FormTableForm',
							defaults : {
								anchor : '96%,96%'
							},
							defaultType : 'textfield',
							items : [{
								name : 'formTable.tableId',
								xtype : 'hidden',
								value : this.tableId == null ? '' : this.tableId
							}
																																																	,{
																fieldLabel : '表单ID',	
								 								name : 'formTable.formDefId'
								 																,xtype:'numberfield'
								 							}
																																										,{
																fieldLabel : '',	
								 								name : 'formTable.tableName'
																,allowBlank:false
								 																 								,maxLength: 128
								 							}
																																										,{
																fieldLabel : '',	
								 								name : 'formTable.tableKey'
																,allowBlank:false
								 																 								,maxLength: 128
								 							}
																																			]
						});
				//加载表单对应的数据	
				if (this.tableId != null && this.tableId != 'undefined') {
					this.formPanel.loadData({
								url : __ctxPath + '/arch/getFormTable.do?tableId='+ this.tableId,
								root : 'data',
								preName : 'formTable'
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
						url:__ctxPath + '/flow/saveFormTable.do',
						callback:function(fp,action){
							var gridPanel = Ext.getCmp('FormTableGrid');
							if (gridPanel != null) {
								gridPanel.getStore().reload();
							}
							this.close();
						}
					}
				);
			}//end of save

		});