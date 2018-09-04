/**
 * @author 
 * @createtime 
 * @class CsDicAreaDynamForm
 * @extends Ext.Window
 * @description CsDicAreaDynam表单
 * @company 智维软件
 */
CsDicAreaDynamForm = Ext.extend(Ext.Window, {
			//构造函数
			constructor : function(_cfg) {
				Ext.applyIf(this, _cfg);
				//必须先初始化组件
				this.initUIComponents();
				CsDicAreaDynamForm.superclass.constructor.call(this, {
							id : 'CsDicAreaDynamFormWin',
							layout : 'fit',
							items : this.formPanel,
							modal : true,
							height : 400,
							width : 500,
							maximizable : true,
							title : '[CsDicAreaDynam]详细信息',
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
							//id : 'CsDicAreaDynamForm',
							defaults : {
								anchor : '96%,96%'
							},
							defaultType : 'textfield',
							items : [{
								name : 'csDicAreaDynam.id',
								xtype : 'hidden',
								value : this.id == null ? '' : this.id
							}
																																																	,{
																fieldLabel : '父节点ID',	
								 								name : 'csDicAreaDynam.parentId'
								 																,xtype:'numberfield'
								 							}
																																										,{
																fieldLabel : '标题',	
								 								name : 'csDicAreaDynam.title'
								 																 								,maxLength: 255
								 							}
																																										,{
																fieldLabel : '排序',	
								 								name : 'csDicAreaDynam.number'
								 																,xtype:'numberfield'
								 							}
																																										,{
																fieldLabel : '叶子节点',	
								 								name : 'csDicAreaDynam.leaf'
								 																,xtype:'numberfield'
								 							}
																																										,{
																fieldLabel : '',	
								 								name : 'csDicAreaDynam.imgUrl'
								 																 								,maxLength: 100
								 							}
																																										,{
																fieldLabel : '唯一标识',	
								 								name : 'csDicAreaDynam.lable'
								 																 								,maxLength: 100
								 							}
																																										,{
																fieldLabel : '是否过期',	
								 								name : 'csDicAreaDynam.isOld'
								 																,xtype:'numberfield'
								 							}
																																										,{
																fieldLabel : '',	
								 								name : 'csDicAreaDynam.remarks'
								 																 								,maxLength: 255
								 							}
																																										,{
																fieldLabel : '',	
								 								name : 'csDicAreaDynam.orderid'
								 																,xtype:'numberfield'
								 							}
																																										,{
																fieldLabel : '',	
								 								name : 'csDicAreaDynam.remarks1'
								 																 								,maxLength: 225
								 							}
																																										,{
																fieldLabel : '',	
								 								name : 'csDicAreaDynam.remarks2'
								 																 								,maxLength: 225
								 							}
																																										,{
																fieldLabel : '',	
								 								name : 'csDicAreaDynam.remarks3'
								 																 								,maxLength: 225
								 							}
																																										,{
																fieldLabel : '',	
								 								name : 'csDicAreaDynam.remarks4'
								 																,xtype:'numberfield'
								 							}
																																										,{
																fieldLabel : '',	
								 								name : 'csDicAreaDynam.remarks5'
								 																 								,maxLength: 225
								 							}
																																			]
						});
				//加载表单对应的数据	
				if (this.id != null && this.id != 'undefined') {
					this.formPanel.loadData({
								url : __ctxPath + '/system/getCsDicAreaDynam.do?id='+ this.id,
								root : 'data',
								preName : 'csDicAreaDynam'
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
						url:__ctxPath + '/system/saveCsDicAreaDynam.do',
						callback:function(fp,action){
							var gridPanel = Ext.getCmp('CsDicAreaDynamGrid');
							if (gridPanel != null) {
								gridPanel.getStore().reload();
							}
							this.close();
						}
					}
				);
			}//end of save

		});