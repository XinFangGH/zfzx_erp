/**
 * @author
 * @createtime
 * @class ArchHastenForm
 * @extends Ext.Window
 * @description ArchHasten表单
 * @company 智维软件
 */
ArchHastenForm = Ext.extend(Ext.Window, {
			// 内嵌FormPanel
			formPanel : null,
			// 构造函数
			constructor : function(_cfg) {
				Ext.applyIf(this, _cfg);
				// 必须先初始化组件
				this.initUIComponents();
				ArchHastenForm.superclass.constructor.call(this, {
							id : 'ArchHastenFormWin',
							layout : 'fit',
							items : this.formPanel,
							modal : true,
							height : 200,
							width : 400,
							maximizable : true,
							title : '催办信息',
							buttonAlign : 'center',
							buttons : this.buttons
						});
			},// end of the constructor
			// 初始化组件
			initUIComponents : function() {
				this.formPanel = new Ext.FormPanel({
							layout : 'form',
							bodyStyle : 'padding:10px 10px 10px 10px',
							border : false,
							url : __ctxPath + '/archive/hastenArchives.do',
							id : 'ArchHastenForm',
							defaults : {
								anchor : '98%,98%'
							},
							items : [{
										xtype:'hidden',
										name : 'archivesId',
										value:this.archivesId
									}, {
										fieldLabel : '催办内容',
										name : 'content',
										xtype:'textarea',
										value:'你有['+this.archivesNo+'--'+this.activityName+']任务,请速去办理！'
									},{
									     xtype:'hidden',
									     name:'activityName',
									     value:this.activityName
									}

							]
						});
				// 初始化功能按钮
				this.buttons = [{
							text : '发送',
							
							iconCls : 'btn-save',
							handler : this.save.createCallback(this.formPanel,
									this)
						}, {
							text : '重置',
							iconCls : 'btn-reset',
							handler : this.reset.createCallback(this.formPanel)
						}, {
							text : '取消',
							iconCls : 'btn-cancel',
							handler : this.cancel.createCallback(this)
						}];
			},// end of the initcomponents

			/**
			 * 重置
			 * 
			 * @param {}
			 *            formPanel
			 */
			reset : function(formPanel) {
				formPanel.getForm().reset();
			},
			/**
			 * 取消
			 * 
			 * @param {}
			 *            window
			 */
			cancel : function(window) {
				window.close();
			},
			/**
			 * 保存记录
			 */
			save : function(formPanel, window) {
				if (formPanel.getForm().isValid()) {
					formPanel.getForm().submit({
								method : 'POST',
								waitMsg : '正在提交数据...',
								success : function(fp, action) {
									Ext.ux.Toast.msg('操作信息', '成功发送催办信息！');
									window.close();
								},
								failure : function(fp, action) {
									var message=action.result.message;
									Ext.MessageBox.show({
												title : '操作信息',
												msg :message!=''?message:'信息保存出错，请联系管理员！',
												buttons : Ext.MessageBox.OK,
												icon : Ext.MessageBox.ERROR
											});
									window.close();
								}
							});
				}
			}// end of save

		});