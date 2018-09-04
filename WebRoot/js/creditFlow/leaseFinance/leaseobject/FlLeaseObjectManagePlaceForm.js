/**
 * @author 
 * @createtime 
 * @class FlLeaseObjectManagePlaceForm
 * @extends Ext.Window
 * @description FlLeaseObjectManagePlace表单
 * @company 智维软件
 */
FlLeaseObjectManagePlaceForm = Ext.extend(Ext.Window, {
			//构造函数
			constructor : function(_cfg) {
				Ext.applyIf(this, _cfg);
				//必须先初始化组件
				this.initUIComponents();
				FlLeaseObjectManagePlaceForm.superclass.constructor.call(this, {
							id : 'FlLeaseObjectManagePlaceFormWin',
							layout : 'fit',
							items : this.formPanel,
							modal : true,
							height : 400,
							width : 500,
							maximizable : true,
							title : '[FlLeaseObjectManagePlace]详细信息',
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
							//id : 'FlLeaseObjectManagePlaceForm',
							defaults : {
								anchor : '96%,96%'
							},
							defaultType : 'textfield',
							items : [{
								name : 'flLeaseObjectManagePlace.id',
								xtype : 'hidden',
								value : this.id == null ? '' : this.id
							}
																																																	,{
																fieldLabel : '租赁物id',	
								 								name : 'flLeaseObjectManagePlace.leaseObjectId'
								 																,xtype:'numberfield'
								 							}
																																										,{
																fieldLabel : '原始存放地点',	
								 								name : 'flLeaseObjectManagePlace.oldPlace'
								 																 								,maxLength: 255
								 							}
																																										,{
																fieldLabel : '转移地点',	
								 								name : 'flLeaseObjectManagePlace.destPlace'
								 																 								,maxLength: 255
								 							}
																																										,{
																fieldLabel : '转移时间',	
								 								name : 'flLeaseObjectManagePlace.moveDate'
								 																,xtype:'datefield',
								format:'Y-m-d',
								value:new Date()
								 							}
																																										,{
																fieldLabel : '操作人id',	
								 								name : 'flLeaseObjectManagePlace.operationPersonId'
								 																 								,maxLength: 255
								 							}
																																										,{
																fieldLabel : '操作时间',	
								 								name : 'flLeaseObjectManagePlace.operationDate'
								 																,xtype:'datefield',
								format:'Y-m-d',
								value:new Date()
								 							}
																																										,{
																fieldLabel : '规格型号',	
								 								name : 'flLeaseObjectManagePlace.standardSize'
								 																 								,maxLength: 255
								 							}
																																			]
						});
				//加载表单对应的数据	
				if (this.id != null && this.id != 'undefined') {
					this.formPanel.loadData({
								url : __ctxPath + '/creditFlow.leaseFinance.leaseobject/getFlLeaseObjectManagePlace.do?id='+ this.id,
								root : 'data',
								preName : 'flLeaseObjectManagePlace'
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
						url:__ctxPath + '/creditFlow.leaseFinance.leaseobject/saveFlLeaseObjectManagePlace.do',
						callback:function(fp,action){
							var gridPanel = Ext.getCmp('FlLeaseObjectManagePlaceGrid');
							if (gridPanel != null) {
								gridPanel.getStore().reload();
							}
							this.close();
						}
					}
				);
			}//end of save

		});