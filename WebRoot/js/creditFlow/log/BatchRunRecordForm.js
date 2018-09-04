/**
 * @author 
 * @createtime 
 * @class BatchRunRecordForm
 * @extends Ext.Window
 * @description BatchRunRecord表单
 * @company 智维软件
 */
BatchRunRecordForm = Ext.extend(Ext.Window, {
			//构造函数
			constructor : function(_cfg) {
				Ext.applyIf(this, _cfg);
				//必须先初始化组件
				this.initUIComponents();
				BatchRunRecordForm.superclass.constructor.call(this, {
							id : 'BatchRunRecordFormWin',
							layout : 'fit',
							items : this.formPanel,
							modal : true,
							height : 400,
							width : 500,
							maximizable : true,
							title : '[BatchRunRecord]详细信息',
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
							//id : 'BatchRunRecordForm',
							defaults : {
								anchor : '96%,96%'
							},
							defaultType : 'textfield',
							items : [{
								name : 'batchRunRecord.id',
								xtype : 'hidden',
								value : this.id == null ? '' : this.id
							}
																																																	,{
																fieldLabel : '跑批类型',	
								 								name : 'batchRunRecord.runType'
								 																 								,maxLength: 255
								 							}
																																										,{
																fieldLabel : '操作人Id',	
								 								name : 'batchRunRecord.appUserId'
								 																,xtype:'numberfield'
								 							}
																																										,{
																fieldLabel : '操作人姓名',	
								 								name : 'batchRunRecord.appUserName'
								 																 								,maxLength: 255
								 							}
																																										,{
																fieldLabel : '开始跑批时间',	
								 								name : 'batchRunRecord.startRunDate'
								 																,xtype:'datefield',
								format:'Y-m-d',
								value:new Date()
								 							}
																																										,{
																fieldLabel : '结束跑批时间',	
								 								name : 'batchRunRecord.endRunDate'
								 																,xtype:'datefield',
								format:'Y-m-d',
								value:new Date()
								 							}
																																										,{
																fieldLabel : '是否出现异常',	
								 								name : 'batchRunRecord.happenAbnorma'
								 																 								,maxLength: 255
								 							}
																																										,{
																fieldLabel : '处理数据条数',	
								 								name : 'batchRunRecord.totalNumber'
								 																,xtype:'numberfield'
								 							}
																																			]
						});
				//加载表单对应的数据	
				if (this.id != null && this.id != 'undefined') {
					this.formPanel.loadData({
								url : __ctxPath + '/creditFlow.log/getBatchRunRecord.do?id='+ this.id,
								root : 'data',
								preName : 'batchRunRecord'
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
						url:__ctxPath + '/creditFlow.log/saveBatchRunRecord.do',
						callback:function(fp,action){
							var gridPanel = Ext.getCmp('BatchRunRecordGrid');
							if (gridPanel != null) {
								gridPanel.getStore().reload();
							}
							this.close();
						}
					}
				);
			}//end of save

		});