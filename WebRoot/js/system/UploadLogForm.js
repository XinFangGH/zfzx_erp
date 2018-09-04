/**
 * @author 
 * @createtime 
 * @class UploadLogForm
 * @extends Ext.Window
 * @description UploadLog表单
 * @company 智维软件
 */
UploadLogForm = Ext.extend(Ext.Window, {
			//构造函数
			constructor : function(_cfg) {
				Ext.applyIf(this, _cfg);
				//必须先初始化组件
				this.initUIComponents();
				UploadLogForm.superclass.constructor.call(this, {
							id : 'UploadLogFormWin',
							layout : 'fit',
							items : this.formPanel,
							modal : true,
							height : 400,
							width : 500,
							maximizable : true,
							title : '[UploadLog]详细信息',
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
							//id : 'UploadLogForm',
							defaults : {
								anchor : '96%,96%'
							},
							defaultType : 'textfield',
							items : [{
								name : 'uploadLog.logId',
								xtype : 'hidden',
								value : this.logId == null ? '' : this.logId
							}
																																																	,{
																fieldLabel : '标的主键',	
								 								name : 'uploadLog.bidId'
								 																 								,maxLength: 100
								 							}
																																										,{
																fieldLabel : '标的名称',	
								 								name : 'uploadLog.bidName'
								 																 								,maxLength: 250
								 							}
																																										,{
																fieldLabel : '创建时间',	
								 								name : 'uploadLog.createDate'
								 																,xtype:'datefield',
								format:'Y-m-d',
								value:new Date()
								 							}
																																										,{
																fieldLabel : '上传详情',	
								 								name : 'uploadLog.msg'
								 																 								,xtype:'textarea'
								 								,maxLength: 1025
								 							}
																																			]
						});
				//加载表单对应的数据	
				if (this.logId != null && this.logId != 'undefined') {
					this.formPanel.loadData({
								url : __ctxPath + '/system/getUploadLog.do?logId='+ this.logId,
								root : 'data',
								preName : 'uploadLog'
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
						url:__ctxPath + '/system/saveUploadLog.do',
						callback:function(fp,action){
							var gridPanel = Ext.getCmp('UploadLogGrid');
							if (gridPanel != null) {
								gridPanel.getStore().reload();
							}
							this.close();
						}
					}
				);
			}//end of save

		});